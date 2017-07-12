package com.angkorteam.fintech.pages.table;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.TableType;
import com.angkorteam.fintech.dto.request.DataTableBuilder;
import com.angkorteam.fintech.helper.DataTableHelper;
import com.angkorteam.fintech.provider.AppTableOptionProvider;
import com.angkorteam.fintech.provider.ColumnTypeOptionProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class DataTableCreatePage extends Page {

    private Form<Void> tableForm;
    private Button createButton;

    private String dataTableValue;
    private TextField<String> dataTableField;
    private TextFeedbackPanel dataTableFeedback;

    private Boolean multiRowValue;
    private CheckBox multiRowField;
    private TextFeedbackPanel multiRowFeedback;

    private AppTableOptionProvider appTableProvider;
    private Option appTableValue;
    private Select2SingleChoice<Option> appTableField;
    private TextFeedbackPanel appTableFeedback;

    private Form<Void> columnForm;
    private AjaxButton addButton;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Integer lengthValue;
    private TextField<Integer> lengthField;
    private TextFeedbackPanel lengthFeedback;

    private Boolean mandatoryValue;
    private CheckBox mandatoryField;
    private TextFeedbackPanel mandatoryFeedback;

    private ColumnTypeOptionProvider typeProvider;
    private Option typeValue;
    private Select2SingleChoice<Option> typeField;
    private TextFeedbackPanel typeFeedback;

    private OptionSingleChoiceProvider codeProvider;
    private Option codeValue;
    private Select2SingleChoice<Option> codeField;
    private TextFeedbackPanel codeFeedback;

    private List<Map<String, Object>> columnValue;
    private DataTable<Map<String, Object>, String> columnTable;
    private ListDataProvider columnProvider;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.columnForm = new Form<>("columnForm");
        add(this.columnForm);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.columnForm.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.columnForm.add(this.nameFeedback);

        this.lengthField = new TextField<>("lengthField", new PropertyModel<>(this, "lengthValue"));
        this.columnForm.add(this.lengthField);
        this.lengthFeedback = new TextFeedbackPanel("lengthFeedback", this.lengthField);
        this.columnForm.add(this.lengthFeedback);

        this.mandatoryField = new CheckBox("mandatoryField", new PropertyModel<>(this, "mandatoryValue"));
        this.mandatoryField.setRequired(true);
        this.columnForm.add(this.mandatoryField);
        this.mandatoryFeedback = new TextFeedbackPanel("mandatoryFeedback", this.mandatoryField);
        this.columnForm.add(this.mandatoryFeedback);

        this.typeProvider = new ColumnTypeOptionProvider();
        this.typeField = new Select2SingleChoice<>("typeField", 0, new PropertyModel<>(this, "typeValue"), this.typeProvider);
        this.typeField.setRequired(true);
        this.columnForm.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.columnForm.add(this.typeFeedback);

        this.codeProvider = new OptionSingleChoiceProvider("m_code", "code_name", "code_name");
        this.codeField = new Select2SingleChoice<>("codeField", 0, new PropertyModel<>(this, "codeValue"), this.codeProvider);
        this.columnForm.add(this.codeField);
        this.codeFeedback = new TextFeedbackPanel("codeFeedback", this.codeField);
        this.columnForm.add(this.codeFeedback);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.addButton.setOnError(this::addButtonError);
        this.columnForm.add(this.addButton);

        this.tableForm = new Form<>("tableForm");
        add(this.tableForm);

        this.createButton = new Button("createButton");
        this.createButton.setOnSubmit(this::createButtonSubmit);
        this.tableForm.add(this.createButton);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextColumn(Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextColumn(Model.of("Mandatory"), "mandatory", "mandatory", this::mandatoryColumn));
        columns.add(new TextColumn(Model.of("Type"), "type", "type", this::typeColumn));
        columns.add(new TextColumn(Model.of("Length"), "length", "length", this::lengthColumn));
        columns.add(new TextColumn(Model.of("Code"), "code", "code", this::codeColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));
        this.columnValue = Lists.newArrayList();
        this.columnProvider = new ListDataProvider(this.columnValue);
        this.columnTable = new DataTable<>("columnTable", columns, this.columnProvider, 20);
        this.tableForm.add(this.columnTable);
        this.columnTable.addTopToolbar(new HeadersToolbar<>(this.columnTable, this.columnProvider));
        this.columnTable.addBottomToolbar(new NoRecordsToolbar(this.columnTable));

        this.dataTableField = new TextField<>("dataTableField", new PropertyModel<>(this, "dataTableValue"));
        this.dataTableField.setRequired(true);
        this.tableForm.add(this.dataTableField);
        this.dataTableFeedback = new TextFeedbackPanel("dataTableFeedback", this.dataTableField);
        this.tableForm.add(this.dataTableFeedback);

        this.multiRowField = new CheckBox("multiRowField", new PropertyModel<>(this, "multiRowValue"));
        this.multiRowField.setRequired(true);
        this.tableForm.add(this.multiRowField);
        this.multiRowFeedback = new TextFeedbackPanel("multiRowFeedback", this.multiRowField);
        this.tableForm.add(this.multiRowFeedback);

        this.appTableProvider = new AppTableOptionProvider();
        this.appTableField = new Select2SingleChoice<>("appTableField", 0, new PropertyModel<>(this, "appTableValue"), this.appTableProvider);
        this.appTableField.setRequired(true);
        this.tableForm.add(this.appTableField);
        this.appTableFeedback = new TextFeedbackPanel("appTableFeedback", this.appTableField);
        this.tableForm.add(this.appTableFeedback);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.columnValue.size(); i++) {
            Map<String, Object> column = this.columnValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.columnValue.remove(index);
        }
        ajaxRequestTarget.add(this.columnTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void createButtonSubmit(Button button) {
        DataTableBuilder builder = new DataTableBuilder();
        builder.withAppTableName(TableType.valueOf(this.appTableValue.getId()));
        builder.withDataTableName(this.dataTableValue);
        builder.withMultiRow(this.multiRowValue);
        for (Map<String, Object> column : columnValue) {
            String type = (String) column.get("type");
            if ("String".equals(type)) {
                builder.withColumnString((String) column.get("name"), (Boolean) column.get("mandatory"), (Integer) column.get("length"));
            } else if ("Number".equals(type)) {
                builder.withColumnNumber((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("Decimal".equals(type)) {
                builder.withColumnDecimal((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("Boolean".equals(type)) {
                builder.withColumnBoolean((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("Date".equals(type)) {
                builder.withColumnDate((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("DateTime".equals(type)) {
                builder.withColumnDateTime((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("Text".equals(type)) {
                builder.withColumnText((String) column.get("name"), (Boolean) column.get("mandatory"));
            } else if ("DropDown".equals(type)) {
                builder.withColumnDropDown((String) column.get("name"), (Boolean) column.get("mandatory"), (String) column.get("code"));
            }
        }

        JsonNode node = null;
        try {
            node = DataTableHelper.createDatatable(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(DataTableBrowsePage.class);
    }

    private void addButtonError(AjaxButton button, AjaxRequestTarget target) {
        target.add(this.columnForm);
    }

    private void addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        Map<String, Object> column = Maps.newHashMap();
        column.put("uuid", UUID.randomUUID().toString());
        column.put("name", this.nameValue);
        if (this.typeValue != null) {
            column.put("type", this.typeValue.getId());
        }
        column.put("mandatory", this.mandatoryValue);
        column.put("length", this.lengthValue);
        if (this.codeValue != null) {
            column.put("code", this.codeValue.getId());
        }
        this.columnValue.add(column);
        this.nameValue = "";
        target.add(this.columnForm);
        target.add(this.columnTable);
    }

    private ItemPanel codeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String code = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(code));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(name));
    }

    private ItemPanel typeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String type = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(type));
    }

    private ItemPanel lengthColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer length = (Integer) model.get(jdbcColumn);
        return new TextCell(length == null ? Model.of("") : Model.of(String.valueOf(length)));
    }

    private ItemPanel mandatoryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean mandatory = (Boolean) model.get(jdbcColumn);
        if (mandatory != null && mandatory) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

}
