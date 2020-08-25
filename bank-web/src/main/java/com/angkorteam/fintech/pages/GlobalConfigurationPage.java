//package com.angkorteam.fintech.pages;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.CConfiguration;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.GlobalConfigurationHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.table.BadgeCell;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.framework.BadgeType;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class GlobalConfigurationPage extends Page {
//
//    protected Form<Void> form1;
//    protected Button saveButton;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected SingleChoiceProvider nameProvider;
//    protected Option nameValue;
//    protected Select2SingleChoice<Option> nameField;
//
//    protected UIRow row2;
//
//    protected UIBlock valueBlock;
//    protected UIContainer valueIContainer;
//    protected Long valueValue;
//    protected TextField<Long> valueField;
//
//    protected FilterForm<Map<String, String>> form2;
//
//    protected UIRow row3;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    protected BookmarkablePageLink<Void> closeLink;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("System");
//            breadcrumb.setPage(SystemDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Global Configuration");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.nameProvider = new SingleChoiceProvider(CConfiguration.NAME, CConfiguration.Field.ID, CConfiguration.Field.NAME);
//
//        this.dataProvider = new JdbcProvider(CConfiguration.NAME);
//        this.dataProvider.boardField(CConfiguration.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(CConfiguration.Field.NAME, "name", String.class);
//        this.dataProvider.boardField(CConfiguration.Field.ENABLED, "enabled", Boolean.class);
//        this.dataProvider.boardField(CConfiguration.Field.VALUE, "value", Long.class);
//        this.dataProvider.setSort("name", SortOrder.ASCENDING);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Enabled ?"), "enabled", "enabled", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Value"), "value", "value", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//
//        this.form1 = new Form<>("form1");
//        add(this.form1);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form1.add(this.saveButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form1);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Twelve_12);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new Select2SingleChoice<>("nameField", new PropertyModel<>(this, "nameValue"), this.nameProvider);
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form1);
//
//        this.valueBlock = this.row2.newUIBlock("valueBlock", Size.Twelve_12);
//        this.valueIContainer = this.valueBlock.newUIContainer("valueIContainer");
//        this.valueField = new TextField<>("valueField", new PropertyModel<>(this, "valueValue"));
//        this.valueIContainer.add(this.valueField);
//        this.valueIContainer.newFeedback("valueFeedback", this.valueField);
//
//        this.form2 = new FilterForm<>("form2", this.dataProvider);
//        add(this.form2);
//
//        this.row3 = UIRow.newUIRow("row3", this.form2);
//
//        this.dataBlock = this.row3.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
//        this.dataIContainer.add(this.dataTable);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", SystemDashboardPage.class);
//        add(this.closeLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.nameField.setRequired(true);
//        this.valueField.setRequired(true);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        JsonNode node = GlobalConfigurationHelper.updateValueForGlobalConfiguration((Session) getSession(), this.nameValue.getId(), String.valueOf(this.valueValue));
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(GlobalConfigurationPage.class);
//    }
//
//    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        if ("enable".equals(s)) {
//            GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(), String.valueOf(id), true);
//            target.add(this.dataTable);
//        } else if ("disable".equals(s)) {
//            GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(), String.valueOf(id), false);
//            target.add(this.dataTable);
//        }
//
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        Boolean enabled = (Boolean) model.get("enabled");
//        if (enabled != null && enabled) {
//            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.PRIMARY));
//        } else {
//            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.PRIMARY));
//        }
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("enabled".equals(column)) {
//            Boolean value = (Boolean) model.get(column);
//            if (value != null && value) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        } else if ("value".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
