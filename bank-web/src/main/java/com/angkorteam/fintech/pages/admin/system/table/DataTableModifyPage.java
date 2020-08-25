package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.PutDataTableRequest;
import com.angkorteam.fintech.client.enums.ColumnType;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.angkorteam.fintech.meta.tenant.XRegisteredTable;
import com.angkorteam.fintech.provider.AppTableOptionProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.ListDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilteredColumn;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.Column;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.schema.Table;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

import java.util.*;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/datatable/modify")
public class DataTableModifyPage extends MasterPage {

    protected String tableName;

    protected Form<Void> modifyForm;

    protected UIRow row1;

    protected UIColumn tableNameColumn;
    protected UIContainer tableNameContainer;
    protected TextField<String> tableNameField;
    protected String tableNameValue;

    protected UIColumn associatedTableColumn;
    protected UIContainer associatedTableContainer;
    protected Select2SingleChoice associatedTableField;
    protected AppTableOptionProvider associatedTableProvider;
    protected Option associatedTableValue;

    protected UIColumn multiRowColumn;
    protected UIContainer multiRowContainer;
    protected TextField<String> multiRowField;
    protected String multiRowValue;

    protected UIRow row2;

    protected UIColumn fieldColumn;
    protected UIContainer fieldContainer;
    protected List<Map<String, Object>> fieldValue;
    protected AjaxLink<Void> fieldAddMoreLink;

    protected DataTable<Map<String, Object>, String> fieldBrowseTable;
    protected ListDataProvider fieldBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> fieldBrowseColumn;

    protected ColumnCreatePopup columnCreatePopup;
    protected ColumnModifyPopup columnModifyPopup;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.tableName = getPageParameters().get("tableName").toString();
        this.tableNameValue = tableName;
        this.associatedTableProvider = new AppTableOptionProvider();

        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        XRegisteredTable xRegisteredTable = XRegisteredTable.staticInitialize(dataContext);
        String appTableName = null;
        try (DataSet rows = dataContext.query().from(xRegisteredTable).select(xRegisteredTable.APPLICATION_TABLE_NAME).where(xRegisteredTable.REGISTERED_TABLE_NAME).eq(this.tableName).execute()) {
            rows.next();
            appTableName = (String) rows.getRow().getValue(xRegisteredTable.APPLICATION_TABLE_NAME);
        }
        for (TableTypeEnum o : TableTypeEnum.values()) {
            if (o.getLiteral().equals(appTableName)) {
                this.associatedTableValue = o.toOption();
                break;
            }
        }
        this.multiRowValue = "No";
        dataContext.refreshSchemas();
        Table table = dataContext.getDefaultSchema().getTableByName(this.tableName);

        this.fieldValue = Lists.newArrayList();
        for (org.apache.metamodel.schema.Column column : table.getColumns()) {
            if (column.getName().equals("id") || column.getName().equals(appTableName.substring(2) + "_id")) {
                if (column.getName().equals("id")) {
                    this.multiRowValue = "Yes";
                }
                continue;
            }
            Map<String, Object> f = new HashMap<>();
            String name = column.getName();
            String type = null;
            f.put("uuid", UUID.randomUUID().toString());
            f.put("mandatory", column.isNullable() ? "No" : "Yes");
            f.put("newMandatory", f.get("mandatory"));
            if (column.getNativeType().equals("VARCHAR")) {
                type = "String";
                f.put("length", column.getColumnSize());
                f.put("newLength", f.get("length"));
            } else if (column.getNativeType().equals("INT")) {
                if (column.getName().contains("_")) {
                    String temp = column.getName().substring(0, column.getName().lastIndexOf("_"));
                    f.put("code", temp.substring(0, temp.lastIndexOf("_")));
                    f.put("newCode", f.get("code"));
                    name = column.getName().substring(column.getName().lastIndexOf("_") + 1);
                    type = "DropDown";
                } else {
                    type = "Number";
                }
            } else if (column.getNativeType().equals("DECIMAL")) {
                type = "Decimal";
            } else if (column.getNativeType().equals("BIT")) {
                type = "Boolean";
            } else if (column.getNativeType().equals("DATE")) {
                type = "Date";
            } else if (column.getNativeType().equals("DATETIME")) {
                type = "DateTime";
            } else if (column.getNativeType().equals("TEXT")) {
                type = "Text";
            } else {
                type = "N/A";
            }
            f.put("name", name);
            f.put("newName", f.get("name"));
            f.put("type", type);
            this.fieldValue.add(f);
        }
        this.fieldBrowseProvider = new ListDataProvider(this.fieldValue);

        this.fieldBrowseColumn = Lists.newArrayList();
        this.fieldBrowseColumn.add(Column.text(Model.of("Will"), "will", "will", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Name"), "newName", "newName", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Mandatory"), "newMandatory", "newMandatory", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Type"), "type", "type", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.number(Model.of("Length"), "newLength", "newLength", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Code"), "newCode", "newCode", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::fieldBrowseAction, this::fieldBrowseClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.modifyForm = new Form<>("modifyForm");
        body.add(this.modifyForm);

        this.row1 = UIRow.newUIRow("row1", this.modifyForm);

        this.tableNameColumn = this.row1.newUIColumn("tableNameColumn", Size.Four_4);
        this.tableNameContainer = this.tableNameColumn.newUIContainer("tableNameContainer");
        this.tableNameField = new TextField<>("tableNameField", new PropertyModel<>(this, "tableNameValue"));
        this.tableNameField.setLabel(Model.of("Table Name"));
        this.tableNameField.setEnabled(false);
        this.tableNameContainer.add(this.tableNameField);
        this.tableNameContainer.newFeedback("tableNameFeedback", this.tableNameField);

        this.associatedTableColumn = this.row1.newUIColumn("associatedTableColumn", Size.Four_4);
        this.associatedTableContainer = this.associatedTableColumn.newUIContainer("associatedTableContainer");
        this.associatedTableField = new Select2SingleChoice("associatedTableField", new PropertyModel<>(this, "associatedTableValue"), this.associatedTableProvider);
        this.associatedTableField.setLabel(Model.of("Associated Table"));
        this.associatedTableField.setRequired(true);
        this.associatedTableContainer.add(this.associatedTableField);
        this.associatedTableContainer.newFeedback("associatedTableFeedback", this.associatedTableField);

        this.multiRowColumn = this.row1.newUIColumn("multiRowColumn", Size.Four_4);
        this.multiRowContainer = this.multiRowColumn.newUIContainer("multiRowContainer");
        this.multiRowField = new TextField<>("multiRowField", new PropertyModel<>(this, "multiRowValue"));
        this.multiRowField.setLabel(Model.of("Multi Row"));
        this.multiRowField.setEnabled(false);
        this.multiRowContainer.add(this.multiRowField);
        this.multiRowContainer.newFeedback("multiRowFeedback", this.multiRowField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.modifyForm);

        this.fieldColumn = this.row2.newUIColumn("fieldColumn", Size.Twelve_12);
        this.fieldContainer = this.fieldColumn.newUIContainer("fieldContainer");
        this.fieldBrowseTable = new DataTable<>("fieldBrowseTable", this.fieldBrowseColumn, this.fieldBrowseProvider, 20);
        this.fieldContainer.add(this.fieldBrowseTable);
        this.fieldAddMoreLink = new AjaxLink<Void>("fieldAddMoreLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                fieldAddMoreLinkClick(target);
            }
        };
        this.fieldContainer.add(this.fieldAddMoreLink);

        this.row2.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", DataTableBrowsePage.class);
        this.modifyForm.add(this.cancelButton);

        this.columnCreatePopup = new ColumnCreatePopup("columnCreatePopup", this::columnCreatePopupEvent);
        this.columnCreatePopup.setOutputMarkupId(true);
        this.columnCreatePopup.setVisibleContent(false);
        body.add(this.columnCreatePopup);

        this.columnModifyPopup = new ColumnModifyPopup("columnModifyPopup", this::columnModifyPopupEvent);
        this.columnModifyPopup.setOutputMarkupId(true);
        this.columnModifyPopup.setVisibleContent(false);
        body.add(this.columnModifyPopup);
    }

    private void columnModifyPopupEvent(String name, Map<String, Object> data, AjaxRequestTarget target) {
        String uuid = (String) data.get("uuid");
        target.add(getMessageContainer());
        for (Map<String, Object> v : this.fieldValue) {
            String nameValue = (String) data.get("nameValue");
            if (v.get("name").equals(nameValue) && !uuid.equals(v.get("uuid"))) {
                setMessage(nameValue + " is not available");
                return;
            }
        }
        for (int i = 0; i < this.fieldValue.size(); i++) {
            Map<String, Object> column = this.fieldValue.get(i);
            if (uuid.equals(column.get("uuid"))) {
                column.put("newName", data.get("nameValue"));
                ColumnType columnType = null;
                if (data.get("typeValue") != null) {
                    columnType = ColumnType.valueOf((String) data.get("typeValue"));
                }
                if (columnType == ColumnType.String) {
                    column.put("newLength", data.get("lengthValue"));
                }
                column.put("newMandatory", ((Option) data.get("mandatoryValue")).getId());
                if (columnType == ColumnType.DropDown && data.get("codeValue") != null) {
                    column.put("newCode", ((Option) data.get("codeValue")).getText());
                }
                column.put("will", "Change");
                target.add(this.fieldBrowseTable);
                break;
            }
        }
    }

    private void columnCreatePopupEvent(String name, Map<String, Object> data, AjaxRequestTarget target) {
        target.add(getMessageContainer());
        for (Map<String, Object> v : this.fieldValue) {
            String nameValue = (String) data.get("nameValue");
            if (v.get("name").equals(nameValue)) {
                setMessage(nameValue + " is not available");
                return;
            }
        }
        ApplicationContext context = WicketFactory.getApplicationContext();
        StringGenerator generator = context.getBean(StringGenerator.class);
        Map<String, Object> column = Maps.newHashMap();
        column.put("uuid", generator.externalId());
        column.put("name", data.get("nameValue"));
        column.put("newName", data.get("nameValue"));
        ColumnType columnType = null;
        if (data.get("typeValue") != null) {
            columnType = ColumnType.valueOf(((Option) data.get("typeValue")).getId());
        }
        if (columnType != null) {
            column.put("type", columnType.getLiteral());
        }
        if (columnType == ColumnType.String) {
            column.put("length", data.get("lengthValue"));
            column.put("newLength", data.get("lengthValue"));
        }
        column.put("mandatory", ((Option) data.get("mandatoryValue")).getId());
        column.put("newMandatory", ((Option) data.get("mandatoryValue")).getId());
        if (columnType == ColumnType.DropDown && data.get("codeValue") != null) {
            column.put("code", ((Option) data.get("codeValue")).getText());
            column.put("newCode", ((Option) data.get("codeValue")).getText());
        }
        column.put("will", "Create");
        this.fieldValue.add(column);
        target.add(this.fieldBrowseTable);
    }

    protected List<ActionItem> fieldBrowseAction(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        String will = (String) model.get("will");
        if (will == null || "".equals(will)) {
            actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
            actions.add(new ActionItem("Change", Model.of("Change"), ItemCss.DANGER));
        } else if ("Create".equals(will)) {
            actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        } else if (will.equals("Delete") || will.equals("Change")) {
            actions.add(new ActionItem("Undo", Model.of("Undo"), ItemCss.DANGER));
        }
        return actions;
    }

    protected void fieldBrowseClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        String uuid = (String) model.get("uuid");
        boolean changed = false;
        if ("Delete".equals(link)) {
            String will = (String) model.get("will");
            if ("Create".equals(will)) {
                int index = -1;
                for (int i = 0; i < this.fieldValue.size(); i++) {
                    Map<String, Object> column = this.fieldValue.get(i);
                    if (uuid.equals(column.get("uuid"))) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    this.fieldValue.remove(index);
                    changed = true;
                }
            } else {
                for (int i = 0; i < this.fieldValue.size(); i++) {
                    Map<String, Object> column = this.fieldValue.get(i);
                    if (uuid.equals(column.get("uuid"))) {
                        column.put("will", "Delete");
                        changed = true;
                        break;
                    }
                }
            }
        } else if ("Undo".equals(link)) {
            for (int i = 0; i < this.fieldValue.size(); i++) {
                Map<String, Object> column = this.fieldValue.get(i);
                if (uuid.equals(column.get("uuid"))) {
                    column.put("will", "");
                    column.put("newCode", column.get("code"));
                    column.put("newName", column.get("name"));
                    column.put("newLength", column.get("length"));
                    column.put("newMandatory", column.get("mandatory"));
                    changed = true;
                    break;
                }
            }
        } else if ("Change".equals(link)) {
            Map<String, Object> data = new HashMap<>();
            data.put("uuid", uuid);
            data.put("nameValue", model.get("newName"));
            data.put("codeValue", new Option((String) model.get("newCode"), (String) model.get("newCode")));
            data.put("lengthValue", model.get("newLength"));
            data.put("mandatoryValue", new Option((String) model.get("newMandatory"), (String) model.get("newMandatory")));
            data.put("typeValue", model.get("type"));
            this.columnModifyPopup.show(target, data);
        }
        if (changed) {
            target.add(this.fieldBrowseTable);
        }
    }

    protected void fieldAddMoreLinkClick(AjaxRequestTarget target) {
        Map<String, Object> data = new HashMap<>();
        this.columnCreatePopup.show(target, data);
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        PutDataTableRequest request = new PutDataTableRequest();
        request.setApptableName(TableTypeEnum.valueOf(this.associatedTableValue.getId()));

        for (Map<String, Object> c : this.fieldValue) {
            if ("Create".equals(c.get("will"))) {
                String type = (String) c.get("type");
                if ("String".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.string((String) c.get("name"), "Yes".equals(c.get("mandatory")), (int) c.get("length")));
                } else if ("Number".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.number((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("Decimal".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.decimal((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("Boolean".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.yesno((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("Date".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.date((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("DateTime".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.datetime((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("Text".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.text((String) c.get("name"), "Yes".equals(c.get("mandatory"))));
                } else if ("DropDown".equals(type)) {
                    request.getAddColumns().add(PutDataTableRequest.AddColumn.dropdown((String) c.get("name"), "Yes".equals(c.get("mandatory")), (String) c.get("code")));
                }
            } else if ("Delete".equals(c.get("will"))) {
                request.getDropColumns().add(new PutDataTableRequest.DropColumn((String) c.get("name")));
            } else if ("Change".equals(c.get("will"))) {
                request.getChangeColumns().add(new PutDataTableRequest.ChangeColumn((String) c.get("type"), (String) c.get("name"), (String) c.get("newName"), (String) c.get("code"), (String) c.get("newCode"), (int) c.get("newLength"), "Yes".equals(c.get("newMandatory"))));
            }
        }

        client.datatableUpdate(getSession().getIdentifier(), getSession().getToken(), this.tableNameValue, request);

        setResponsePage(DataTableBrowsePage.class);
    }
}
