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
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MPermission;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.RoleHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.provider.MultipleChoiceProvider;
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
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class MakerCheckerPage extends Page {
//
//    protected Form<Void> form1;
//    protected Button addButton;
//
//    protected UIRow row1;
//
//    protected UIBlock permissionBlock;
//    protected UIContainer permissionIContainer;
//    protected MultipleChoiceProvider permissionProvider;
//    protected List<Option> permissionValue;
//    protected Select2MultipleChoice<Option> permissionField;
//
//    protected UIRow row2;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//    protected FilterForm<Map<String, String>> form2;
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
//            breadcrumb.setLabel("Maker Checker Setting");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.permissionProvider = new MultipleChoiceProvider(MPermission.NAME, MPermission.Field.CODE, MPermission.Field.CODE, "concat(" + MPermission.Field.CODE + ",' ', '[', " + MPermission.Field.GROUPING + ",']',  ' ', '[', IF(" + MPermission.Field.ENTITY_NAME + " is NULL , 'N/A', " + MPermission.Field.ENTITY_NAME + "), ']',' ', '[' , IF(" + MPermission.Field.ACTION_NAME + " IS NULL , 'N/A', " + MPermission.Field.ACTION_NAME + "),']')");
//        this.permissionProvider.applyWhere("maker", MPermission.Field.CAN_MAKER_CHECKER + " = 0");
//
//        this.dataProvider = new JdbcProvider(MPermission.NAME);
//        this.dataProvider.boardField(MPermission.Field.GROUPING, "grouping", String.class);
//        this.dataProvider.boardField(MPermission.Field.CODE, "code", String.class);
//        this.dataProvider.boardField(MPermission.Field.ENTITY_NAME, "entity_name", String.class);
//        this.dataProvider.boardField(MPermission.Field.ACTION_NAME, "action_name", String.class);
//        this.dataProvider.boardField(MPermission.Field.CAN_MAKER_CHECKER, "can_maker_checker", String.class);
//        this.dataProvider.setSort("grouping", SortOrder.ASCENDING);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Grouping"), "grouping", "grouping", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code"), "code", "code", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Entity"), "entity_name", "entity_name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Operation"), "action_name", "action_name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Is Enabled ?"), "can_maker_checker", "can_maker_checker", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form1 = new Form<>("form1");
//        add(this.form1);
//
//        this.addButton = new Button("addButton");
//        this.addButton.setOnSubmit(this::addButtonSubmit);
//        this.form1.add(this.addButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form1);
//
//        this.permissionBlock = this.row1.newUIBlock("permissionBlock", Size.Twelve_12);
//        this.permissionIContainer = this.permissionBlock.newUIContainer("permissionIContainer");
//        this.permissionField = new Select2MultipleChoice<>("permissionField", new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
//        this.permissionIContainer.add(this.permissionField);
//        this.permissionIContainer.newFeedback("permissionFeedback", this.permissionField);
//
//        this.form2 = new FilterForm<>("form2", this.dataProvider);
//        add(this.form2);
//
//        this.row2 = UIRow.newUIRow("row2", this.form2);
//
//        this.dataBlock = this.row2.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
//        this.dataIContainer.add(this.dataTable);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.permissionField.setRequired(true);
//    }
//
//    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        String code = (String) model.get("code");
//        Map<String, Boolean> permissions = Maps.newHashMap();
//        if ("disable".equals(s)) {
//            permissions.put(code, false);
//        } else if ("enable".equals(s)) {
//            permissions.put(code, true);
//        }
//
//        JsonNode node = RoleHelper.makerCheckerPermission((Session) getSession(), permissions);
//
//        if (reportError(node)) {
//            setResponsePage(MakerCheckerPage.class);
//            return;
//        }
//        if (target != null) {
//            target.add(this.dataTable);
//        }
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        Boolean enabled = (Boolean) model.get("can_maker_checker");
//        if (enabled != null && enabled) {
//            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
//        } else {
//            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
//        }
//        return actions;
//
//    }
//
//    protected void addButtonSubmit(Button button) {
//        Map<String, Boolean> permissions = Maps.newHashMap();
//        if (this.permissionValue != null) {
//            for (Option option : this.permissionValue) {
//                permissions.put(option.getId(), true);
//            }
//        }
//
//        JsonNode node = RoleHelper.makerCheckerPermission((Session) getSession(), permissions);
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(MakerCheckerPage.class);
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("grouping".equals(column) || "action_name".equals(column) || "code".equals(column) || "entity_name".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("can_maker_checker".equals(column)) {
//            Boolean value = (Boolean) model.get(column);
//            if (value != null && value) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//}
