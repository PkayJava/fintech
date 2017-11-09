package com.angkorteam.fintech.widget.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.WorkingPage;
import com.angkorteam.fintech.dto.builder.NoteBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.mashape.unirest.http.exceptions.UnirestException;

public class CenterPreviewNotePanel extends Panel {

    protected String centerId;

    protected Page itemPage;

    protected Form<Void> form;
    protected Button addButton;

    private String noteValue;
    private TextField<String> noteField;
    private TextFeedbackPanel noteFeedback;

    protected DataTable<Map<String, Object>, String> noteTable;
    protected JdbcProvider noteProvider;

    public CenterPreviewNotePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.centerId = new PropertyModel<String>(this.itemPage, "centerId").getObject();

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
        this.noteProvider.addJoin("LEFT JOIN m_appuser on m_note.createdby_id = m_appuser.id");
        this.noteProvider.boardField("m_note.note", "comment", String.class);
        this.noteProvider.boardField("m_appuser.username", "createdBy", String.class);
        this.noteProvider.boardField("m_note.created_date", "createdOn", String.class);
        this.noteProvider.applyWhere("note_type_enum", "m_note.note_type_enum = 600");
        this.noteProvider.applyWhere("group_id", "m_note.group_id = " + this.centerId);

        this.noteProvider.setSort("createdOn", SortOrder.DESCENDING);

        List<IColumn<Map<String, Object>, String>> noteColumns = Lists.newArrayList();
        noteColumns.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Comment"), "comment", "comment", this::commentNoteColumn));
        noteColumns.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Created By"), "createdBy", "createdBy", this::createdByNoteColumn));
        noteColumns.add(new TextFilterColumn(this.noteProvider, ItemClass.String, Model.of("Created On"), "createdOn", "createdOn", this::createdOnNoteColumn));

        this.noteTable = new DefaultDataTable<>("noteTable", noteColumns, this.noteProvider, 20);
        add(this.noteTable);
    }

    protected ItemPanel commentNoteColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel createdByNoteColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel createdOnNoteColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd MMM yyyy HH:mm:ss");
    }

    protected void addButtonSubmit(Button button) {
        NoteBuilder builder = new NoteBuilder();
        builder.withCenterId(this.centerId);
        builder.withNote(this.noteValue);

        JsonNode node = null;
        try {
            node = ClientHelper.postCenterNote((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (itemPage instanceof com.angkorteam.fintech.Page) {
            if (((com.angkorteam.fintech.Page) itemPage).reportError(node)) {
                return;
            }
        }
        if (itemPage instanceof DeprecatedPage) {
            if (((com.angkorteam.fintech.DeprecatedPage) itemPage).reportError(node)) {
                return;
            }
        }
        if (itemPage instanceof WorkingPage) {
            if (((com.angkorteam.fintech.WorkingPage) itemPage).reportError(node)) {
                return;
            }
        }
    }

}
