package com.angkorteam.fintech.pages.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
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
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.DataTableBuilder;
import com.angkorteam.fintech.dto.enums.ColumnType;
import com.angkorteam.fintech.dto.enums.TableType;
import com.angkorteam.fintech.helper.DataTableHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.popup.ColumnPopup;
import com.angkorteam.fintech.provider.AppTableOptionProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class DataTableCreatePage extends Page {

    protected Form<Void> form;
    protected Button createButton;

    protected WebMarkupBlock dataTableBlock;
    protected WebMarkupContainer dataTableIContainer;
    protected String dataTableValue;
    protected TextField<String> dataTableField;
    protected TextFeedbackPanel dataTableFeedback;

    protected WebMarkupBlock multiRowBlock;
    protected WebMarkupContainer multiRowIContainer;
    protected Boolean multiRowValue;
    protected CheckBox multiRowField;
    protected TextFeedbackPanel multiRowFeedback;

    protected WebMarkupBlock appTableBlock;
    protected WebMarkupContainer appTableIContainer;
    protected AppTableOptionProvider appTableProvider;
    protected Option appTableValue;
    protected Select2SingleChoice<Option> appTableField;
    protected TextFeedbackPanel appTableFeedback;

    protected WebMarkupBlock columnBlock;
    protected WebMarkupContainer columnIContainer;
    protected List<Map<String, Object>> columnValue;
    protected DataTable<Map<String, Object>, String> columnTable;
    protected ListDataProvider columnProvider;
    protected List<IColumn<Map<String, Object>, String>> columnColumn;

    protected AjaxLink<Void> columnAddLink;

    protected ModalWindow columnPopup;
    protected Map<String, Object> popupModel;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.popupModel = new HashMap<>();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.createButton = new Button("createButton");
        this.createButton.setOnSubmit(this::createButtonSubmit);
        this.form.add(this.createButton);

        initColumnBlock();

        initDataTableBlock();

        initMultiRowBlock();

        initAppTableBlock();

        this.columnPopup = new ModalWindow("columnPopup");
        add(this.columnPopup);
        this.columnPopup.setOnClose(this::columnPopupClose);
    }

    protected void columnPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> column = Maps.newHashMap();
        column.put("uuid", generator.externalId());
        column.put("name", this.popupModel.get("nameValue"));
        ColumnType columnType = null;
        if (this.popupModel.get("typeValue") != null) {
            columnType = ColumnType.valueOf(((Option) this.popupModel.get("typeValue")).getId());
        }
        if (columnType != null) {
            column.put("type", columnType.getLiteral());
        }
        if (columnType == ColumnType.String) {
            column.put("length", this.popupModel.get("lengthValue"));
        }
        column.put("mandatory", this.popupModel.get("mandatoryValue"));
        if (columnType == ColumnType.DropDown && this.popupModel.get("codeValue") != null) {
            column.put("code", ((Option) this.popupModel.get("codeValue")).getId());
        }
        this.columnValue.add(column);
        target.add(this.columnTable);
    }

    protected void initAppTableBlock() {
        this.appTableBlock = new WebMarkupBlock("appTableBlock", Size.Six_6);
        this.form.add(this.appTableBlock);
        this.appTableIContainer = new WebMarkupContainer("appTableIContainer");
        this.appTableBlock.add(this.appTableIContainer);
        this.appTableProvider = new AppTableOptionProvider();
        this.appTableField = new Select2SingleChoice<>("appTableField", new PropertyModel<>(this, "appTableValue"), this.appTableProvider);
        this.appTableField.setRequired(true);
        this.appTableIContainer.add(this.appTableField);
        this.appTableFeedback = new TextFeedbackPanel("appTableFeedback", this.appTableField);
        this.appTableIContainer.add(this.appTableFeedback);
    }

    protected void initMultiRowBlock() {
        this.multiRowBlock = new WebMarkupBlock("multiRowBlock", Size.Twelve_12);
        this.form.add(this.multiRowBlock);
        this.multiRowIContainer = new WebMarkupContainer("multiRowIContainer");
        this.multiRowBlock.add(this.multiRowIContainer);
        this.multiRowField = new CheckBox("multiRowField", new PropertyModel<>(this, "multiRowValue"));
        this.multiRowField.setRequired(true);
        this.multiRowIContainer.add(this.multiRowField);
        this.multiRowFeedback = new TextFeedbackPanel("multiRowFeedback", this.multiRowField);
        this.multiRowIContainer.add(this.multiRowFeedback);
    }

    protected void initDataTableBlock() {
        this.dataTableBlock = new WebMarkupBlock("dataTableBlock", Size.Six_6);
        this.form.add(this.dataTableBlock);
        this.dataTableIContainer = new WebMarkupContainer("dataTableIContainer");
        this.dataTableBlock.add(this.dataTableIContainer);
        this.dataTableField = new TextField<>("dataTableField", new PropertyModel<>(this, "dataTableValue"));
        this.dataTableField.setRequired(true);
        this.dataTableIContainer.add(this.dataTableField);
        this.dataTableFeedback = new TextFeedbackPanel("dataTableFeedback", this.dataTableField);
        this.dataTableIContainer.add(this.dataTableFeedback);
    }

    protected void initColumnBlock() {
        this.columnBlock = new WebMarkupBlock("columnBlock", Size.Twelve_12);
        this.form.add(this.columnBlock);
        this.columnIContainer = new WebMarkupContainer("columnIContainer");
        this.columnBlock.add(this.columnIContainer);
        this.columnColumn = Lists.newArrayList();
        this.columnColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::columnColumn));
        this.columnColumn.add(new TextColumn(Model.of("Mandatory"), "mandatory", "mandatory", this::columnColumn));
        this.columnColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::columnColumn));
        this.columnColumn.add(new TextColumn(Model.of("Length"), "length", "length", this::columnColumn));
        this.columnColumn.add(new TextColumn(Model.of("Code"), "code", "code", this::columnColumn));
        this.columnColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::columnAction, this::columnClick));
        this.columnValue = Lists.newArrayList();
        this.columnProvider = new ListDataProvider(this.columnValue);
        this.columnTable = new DataTable<>("columnTable", this.columnColumn, this.columnProvider, 20);
        this.columnIContainer.add(this.columnTable);
        this.columnTable.addTopToolbar(new HeadersToolbar<>(this.columnTable, this.columnProvider));
        this.columnTable.addBottomToolbar(new NoRecordsToolbar(this.columnTable));

        this.columnAddLink = new AjaxLink<>("columnAddLink");
        this.columnAddLink.setOnClick(this::columnAddLinkClick);
        this.columnIContainer.add(this.columnAddLink);
    }

    protected boolean columnAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.columnPopup.setContent(new ColumnPopup("columnPopup", this.popupModel));
        this.columnPopup.show(target);
        return false;
    }

    @Override
    protected void configureMetaData() {

    }

    protected void columnClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> columnAction(String s, Map<String, Object> model) {
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
                builder.withColumnString((String) column.get("name"), (Boolean) column.get("mandatory"), (Long) column.get("length"));
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

        JsonNode node = DataTableHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(DataTableBrowsePage.class);
    }

    protected ItemPanel columnColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "type".equals(column) || "code".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("mandatory".equals(column)) {
            Boolean mandatory = (Boolean) model.get(column);
            if (mandatory != null && mandatory) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        } else if ("length".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
