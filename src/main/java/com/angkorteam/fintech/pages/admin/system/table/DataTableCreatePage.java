package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.enums.ColumnType;
import com.angkorteam.fintech.provider.AppTableOptionProvider;
import com.angkorteam.fintech.provider.YesNoOptionProvider;
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
import io.github.openunirest.http.JsonNode;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/datatable/create")
public class DataTableCreatePage extends MasterPage {

    protected Form<Void> createForm;

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
    protected Select2SingleChoice multiRowField;
    protected YesNoOptionProvider multiRowProvider;
    protected Option multiRowValue;

    protected UIRow row2;

    protected UIColumn fieldColumn;
    protected UIContainer fieldContainer;
    protected List<Map<String, Object>> fieldValue;
    protected AjaxLink<Void> fieldAddMoreLink;

    protected DataTable<Map<String, Object>, String> fieldBrowseTable;
    protected ListDataProvider fieldBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> fieldBrowseColumn;

    protected ColumnPopup popup;

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.multiRowProvider = new YesNoOptionProvider();
        this.associatedTableProvider = new AppTableOptionProvider();

        this.fieldValue = Lists.newArrayList();
        this.fieldBrowseProvider = new ListDataProvider(this.fieldValue);

        this.fieldBrowseColumn = Lists.newArrayList();
        this.fieldBrowseColumn.add(Column.text(Model.of("Name"), "name", "name", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Mandatory"), "mandatory", "mandatory", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Type"), "type", "type", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.number(Model.of("Length"), "length", "length", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(Column.text(Model.of("Code"), "code", "code", this.fieldBrowseProvider));
        this.fieldBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::fieldBrowseAction, this::fieldBrowseClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.tableNameColumn = this.row1.newUIColumn("tableNameColumn", Size.Four_4);
        this.tableNameContainer = this.tableNameColumn.newUIContainer("tableNameContainer");
        this.tableNameField = new TextField<>("tableNameField", new PropertyModel<>(this, "tableNameValue"));
        this.tableNameField.setLabel(Model.of("Table Name"));
        this.tableNameField.setRequired(true);
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
        this.multiRowField = new Select2SingleChoice("multiRowField", new PropertyModel<>(this, "multiRowValue"), this.multiRowProvider);
        this.multiRowField.setLabel(Model.of("Multi Row"));
        this.multiRowField.setRequired(true);
        this.multiRowContainer.add(this.multiRowField);
        this.multiRowContainer.newFeedback("multiRowFeedback", this.multiRowField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

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

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", DataTableBrowsePage.class);
        this.createForm.add(this.cancelButton);

        this.popup = new ColumnPopup("popup", this::popupEvent);
        this.popup.setOutputMarkupId(true);
        this.popup.setVisibleContent(false);
        body.add(this.popup);
    }

    private void popupEvent(String name, Map<String, Object> data, AjaxRequestTarget target) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        StringGenerator generator = context.getBean(StringGenerator.class);
        Map<String, Object> column = Maps.newHashMap();
        column.put("uuid", generator.externalId());
        column.put("name", data.get("nameValue"));
        ColumnType columnType = null;
        if (data.get("typeValue") != null) {
            columnType = ColumnType.valueOf(((Option) data.get("typeValue")).getId());
        }
        if (columnType != null) {
            column.put("type", columnType.getLiteral());
        }
        if (columnType == ColumnType.String) {
            column.put("length", data.get("lengthValue"));
        }
        column.put("mandatory", ((Option) data.get("mandatoryValue")).getId());
        if (columnType == ColumnType.DropDown && data.get("codeValue") != null) {
            column.put("code", ((Option) data.get("codeValue")).getId());
        }
        this.fieldValue.add(column);
        target.add(this.fieldBrowseTable);
    }

    protected List<ActionItem> fieldBrowseAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void fieldBrowseClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.fieldValue.size(); i++) {
            Map<String, Object> column = this.fieldValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.fieldValue.remove(index);
        }
        target.add(this.fieldBrowseTable);
    }

    protected void fieldAddMoreLinkClick(AjaxRequestTarget target) {
        Map<String, Object> data = new HashMap<>();
        this.popup.show(target, data);
    }

    protected void createButtonSubmit() {
        JsonNode node = null;
        // node = GlobalConfigurationHelper.updateValueForGlobalConfiguration(getSession(), this.nameValue.getId(), String.valueOf(this.valueValue));

        if (reportError(node)) {
            return;
        }
        setResponsePage(DataTableBrowsePage.class);
    }
}
