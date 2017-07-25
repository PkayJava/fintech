package com.angkorteam.fintech.pages.code;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.CodeValueBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class ValueBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private String codeId;

    private JdbcProvider provider;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String descriptionValue;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private Integer positionValue;
    private TextField<Integer> positionField;
    private TextFeedbackPanel positionFeedback;

    private Boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private Form<Void> form;
    private Button addButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.codeId = parameters.get("codeId").toString("");

        this.provider = new JdbcProvider("m_code_value");
        this.provider.applyWhere("code", "code_id = " + this.codeId);
        this.provider.boardField("id", "id", Integer.class);
        this.provider.boardField("code_value", "code_value", String.class);
        this.provider.boardField("code_description", "code_description", String.class);
        this.provider.boardField("order_position", "order_position", Integer.class);
        this.provider.boardField("is_active", "active", Boolean.class);

        this.provider.setSort("code_value", SortOrder.ASCENDING);

        this.provider.selectField("id", Integer.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "code_value", "code_value", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Description"), "code_description", "code_description", this::descriptionColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Position"), "order_position", "order_position", this::positionColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Active ?"), "active", "active", this::activeColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionField.setRequired(true);
        this.form.add(this.positionField);
        this.positionFeedback = new TextFeedbackPanel("positionFeedback", this.positionField);
        this.form.add(this.positionFeedback);

        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.form.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.form.add(this.activeFeedback);
    }

    private void addButtonSubmit(Button button) {
        CodeValueBuilder builder = new CodeValueBuilder();
        builder.withCodeId(this.codeId);
        builder.withDescription(this.descriptionValue);
        builder.withName(this.nameValue);
        builder.withPosition(this.positionValue);
        builder.withActive(this.activeValue);

        JsonNode node = null;
        try {
            node = CodeHelper.createValue(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);

        setResponsePage(ValueBrowsePage.class, parameters);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Integer id = (Integer) stringObjectMap.get("id");
        try {
            if ("disable".equals(s)) {
                CodeValueBuilder builder = new CodeValueBuilder();
                builder.withId(String.valueOf(id));
                builder.withCodeId(this.codeId);
                builder.withActive(false);
                CodeHelper.updateValue(builder.build());
            } else if ("enable".equals(s)) {
                CodeValueBuilder builder = new CodeValueBuilder();
                builder.withId(String.valueOf(id));
                builder.withCodeId(this.codeId);
                builder.withActive(true);
                CodeHelper.updateValue(builder.build());
            }
        } catch (UnirestException e) {
        }
        ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean active = (Boolean) stringObjectMap.get("active");
        if (active != null && active) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
        }
        return actions;
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(name));
    }

    private ItemPanel descriptionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String description = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(description));
    }

    private ItemPanel positionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer position = (Integer) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(position)));
    }

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean active = (Boolean) model.get(jdbcColumn);
        if (active != null && active) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

}
