package com.angkorteam.fintech.widget.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.WorkingPage;
import com.angkorteam.fintech.dto.builder.client.center.CenterNoteBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

public class CenterPreviewNotePanel extends Panel {

    protected String centerId;

    protected Page itemPage;

    protected Form<Void> form;
    protected Button addButton;

    protected List<IColumn<Map<String, Object>, String>> noteColumn;
    protected String noteValue;
    protected TextField<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    protected DataTable<Map<String, Object>, String> noteTable;
    protected JdbcProvider noteProvider;

    public CenterPreviewNotePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.centerId = new PropertyModel<String>(this.itemPage, "centerId").getObject();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.noteField = new TextField<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setRequired(true);
        this.form.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.form.add(this.noteFeedback);

        this.noteProvider = new JdbcProvider("m_note");
        this.noteProvider.applyJoin("m_appuser", "LEFT JOIN m_appuser on m_note.createdby_id = m_appuser.id");
        this.noteProvider.boardField("m_note.note", "comment", String.class);
        this.noteProvider.boardField("m_appuser.username", "createdBy", String.class);
        this.noteProvider.boardField("m_note.created_date", "createdOn", String.class);
        this.noteProvider.applyWhere("note_type_enum", "m_note.note_type_enum = 600");
        this.noteProvider.applyWhere("group_id", "m_note.group_id = " + this.centerId);

        this.noteProvider.setSort("createdOn", SortOrder.DESCENDING);

        this.noteColumn = Lists.newArrayList();
        this.noteColumn.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Comment"), "comment", "comment", this::noteColumn));
        this.noteColumn.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Created By"), "createdBy", "createdBy", this::noteColumn));
        this.noteColumn.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Created On"), "createdOn", "createdOn", this::noteColumn));

        this.noteTable = new DefaultDataTable<>("noteTable", this.noteColumn, this.noteProvider, 20);
        add(this.noteTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel noteColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("comment".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("createdBy".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("createdOn".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd MMM yyyy HH:mm:ss");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void addButtonSubmit(Button button) {
        CenterNoteBuilder builder = new CenterNoteBuilder();
        builder.withCenterId(this.centerId);
        builder.withNote(this.noteValue);

        JsonNode node = ClientHelper.postCenterNote((Session) getSession(), builder.build());

        if (itemPage instanceof com.angkorteam.fintech.Page) {
            if (((com.angkorteam.fintech.Page) itemPage).reportError(node)) {
                return;
            }
        }
        if (itemPage instanceof WorkingPage) {
            if (((com.angkorteam.fintech.WorkingPage) itemPage).reportError(node)) {
                return;
            }
        }
        this.noteValue = "";
    }

}
