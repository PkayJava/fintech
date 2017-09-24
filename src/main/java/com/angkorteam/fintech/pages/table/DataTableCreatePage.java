package com.angkorteam.fintech.pages.table;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ColumnType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.TableType;
import com.angkorteam.fintech.dto.request.DataTableBuilder;
import com.angkorteam.fintech.helper.DataTableHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.AppTableOptionProvider;
import com.angkorteam.fintech.provider.ColumnTypeOptionProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class DataTableCreatePage extends Page {

    protected Form<Void> tableForm;
    protected Button createButton;

    protected String dataTableValue;
    protected TextField<String> dataTableField;
    protected TextFeedbackPanel dataTableFeedback;

    protected Boolean multiRowValue;
    protected CheckBox multiRowField;
    protected TextFeedbackPanel multiRowFeedback;

    protected AppTableOptionProvider appTableProvider;
    protected Option appTableValue;
    protected Select2SingleChoice<Option> appTableField;
    protected TextFeedbackPanel appTableFeedback;

    protected Form<Void> columnForm;
    protected AjaxButton addButton;

    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupContainer lengthBlock;
    protected WebMarkupContainer lengthContainer;
    protected Integer lengthValue;
    protected TextField<Integer> lengthField;
    protected TextFeedbackPanel lengthFeedback;

    protected Boolean mandatoryValue;
    protected CheckBox mandatoryField;
    protected TextFeedbackPanel mandatoryFeedback;

    protected WebMarkupContainer codeBlock;
    protected WebMarkupContainer codeContainer;
    protected ColumnTypeOptionProvider typeProvider;
    protected Option typeValue;
    protected Select2SingleChoice<Option> typeField;
    protected TextFeedbackPanel typeFeedback;

    protected SingleChoiceProvider codeProvider;
    protected Option codeValue;
    protected Select2SingleChoice<Option> codeField;
    protected TextFeedbackPanel codeFeedback;

    protected List<Map<String, Object>> columnValue;
    protected DataTable<Map<String, Object>, String> columnTable;
    protected ListDataProvider columnProvider;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Data Table");
            breadcrumb.setPage(DataTableBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Data Table Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

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

        this.lengthBlock = new WebMarkupContainer("lengthBlock");
        this.lengthBlock.setOutputMarkupId(true);
        this.columnForm.add(this.lengthBlock);
        this.lengthContainer = new WebMarkupContainer("lengthContainer");
        this.lengthBlock.add(this.lengthContainer);
        this.lengthField = new TextField<>("lengthField", new PropertyModel<>(this, "lengthValue"));
        this.lengthContainer.add(this.lengthField);
        this.lengthFeedback = new TextFeedbackPanel("lengthFeedback", this.lengthField);
        this.lengthContainer.add(this.lengthFeedback);

        this.mandatoryField = new CheckBox("mandatoryField", new PropertyModel<>(this, "mandatoryValue"));
        this.mandatoryField.setRequired(true);
        this.columnForm.add(this.mandatoryField);
        this.mandatoryFeedback = new TextFeedbackPanel("mandatoryFeedback", this.mandatoryField);
        this.columnForm.add(this.mandatoryFeedback);

        this.typeProvider = new ColumnTypeOptionProvider();
        this.typeField = new Select2SingleChoice<>("typeField", 0, new PropertyModel<>(this, "typeValue"), this.typeProvider);
        this.typeField.add(new OnChangeAjaxBehavior(this::typeFieldUpdate));
        this.typeField.setRequired(true);
        this.columnForm.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.columnForm.add(this.typeFeedback);

        this.codeBlock = new WebMarkupContainer("codeBlock");
        this.codeBlock.setOutputMarkupId(true);
        this.columnForm.add(this.codeBlock);
        this.codeContainer = new WebMarkupContainer("codeContainer");
        this.codeBlock.add(this.codeContainer);
        this.codeProvider = new SingleChoiceProvider("m_code", "code_name", "code_name");
        this.codeField = new Select2SingleChoice<>("codeField", 0, new PropertyModel<>(this, "codeValue"), this.codeProvider);
        this.codeContainer.add(this.codeField);
        this.codeFeedback = new TextFeedbackPanel("codeFeedback", this.codeField);
        this.codeContainer.add(this.codeFeedback);

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

        initDefault();
    }

    protected void initDefault() {
        typeFieldUpdate(null);
    }

    protected boolean typeFieldUpdate(AjaxRequestTarget target) {
        ColumnType columnType = null;
        if (this.typeValue != null) {
            columnType = ColumnType.valueOf(this.typeValue.getId());
        }
        boolean codeVisible = false;
        boolean lengthVisible = false;
        if (columnType != null) {
            codeVisible = columnType == ColumnType.DropDown;
            lengthVisible = columnType == ColumnType.String;
        }

        this.codeContainer.setVisible(codeVisible);
        this.lengthContainer.setVisible(lengthVisible);
        if (target != null) {
            target.add(this.codeBlock);
            target.add(this.lengthBlock);
        }
        return false;
    }

    protected void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.columnValue.size(); i++) {
            Map<String, Object> column = this.columnValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.columnValue.remove(index);
        }
        target.add(this.columnTable);
    }

    protected List<ActionItem> actionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void createButtonSubmit(Button button) {
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
            node = DataTableHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(DataTableBrowsePage.class);
    }

    protected boolean addButtonError(AjaxButton button, AjaxRequestTarget target) {
        target.add(this.columnForm);
        return false;
    }

    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        Map<String, Object> column = Maps.newHashMap();
        column.put("uuid", UUID.randomUUID().toString());
        column.put("name", this.nameValue);
        if (this.typeValue != null) {
            column.put("type", ColumnType.valueOf(this.typeValue.getId()).getLiteral());
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
        return false;
    }

    protected ItemPanel codeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel typeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel lengthColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel mandatoryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean mandatory = (Boolean) model.get(jdbcColumn);
        if (mandatory != null && mandatory) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

}
