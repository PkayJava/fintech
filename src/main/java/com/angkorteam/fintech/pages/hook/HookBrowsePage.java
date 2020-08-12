//package com.angkorteam.fintech.pages.hook;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MHook;
//import com.angkorteam.fintech.ddl.MHookTemplates;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.HookHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.SystemDashboardPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.table.BadgeCell;
//import com.angkorteam.fintech.table.LinkCell;
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
///**
// * Created by socheatkhauv on 6/27/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class HookBrowsePage extends Page {
//
//    protected Form<Void> form1;
//    protected Button createButton;
//
//    protected UIRow row1;
//
//    protected UIBlock templateBlock;
//    protected UIContainer templateIContainer;
//    protected SingleChoiceProvider templateProvider;
//    protected Option templateValue;
//    protected Select2SingleChoice<Option> templateField;
//
//    protected FilterForm<Map<String, String>> form2;
//
//    protected UIRow row2;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
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
//            breadcrumb.setLabel("Hook");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.templateProvider = new SingleChoiceProvider(MHookTemplates.NAME, MHookTemplates.Field.ID, MHookTemplates.Field.NAME);
//
//        this.dataProvider = new JdbcProvider(MHook.NAME);
//        this.dataProvider.applyJoin("m_hook_templates", "LEFT JOIN " + MHookTemplates.NAME + " ON " + MHook.NAME + "." + MHook.Field.TEMPLATE_ID + " = " + MHookTemplates.NAME + "." + MHookTemplates.Field.ID);
//        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.NAME, "name", String.class);
//        this.dataProvider.boardField(MHookTemplates.NAME + "." + MHookTemplates.Field.NAME, "template", String.class);
//        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.IS_ACTIVE, "active", Long.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Template"), "template", "template", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Is Active ?"), "active", "active", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//
//        this.form1 = new Form<>("form1");
//        add(this.form1);
//
//        this.createButton = new Button("createButton");
//        this.createButton.setOnSubmit(this::createButtonSubmit);
//        this.form1.add(this.createButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form1);
//
//        this.templateBlock = this.row1.newUIBlock("templateBlock", Size.Twelve_12);
//        this.templateIContainer = this.templateBlock.newUIContainer("templateIContainer");
//        this.templateField = new Select2SingleChoice<>("templateField", new PropertyModel<>(this, "templateValue"), this.templateProvider);
//        this.templateIContainer.add(this.templateField);
//        this.templateIContainer.newFeedback("templateFeedback", this.templateField);
//
//        this.form2 = new FilterForm<>("form2", this.dataProvider);
//        add(this.form2);
//
//        this.row2 = UIRow.newUIRow("row2", this.form2);
//        this.dataBlock = this.row2.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
//        this.dataIContainer.add(this.dataTable);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.templateField.setRequired(true);
//    }
//
//    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        HookHelper.delete((Session) getSession(), String.valueOf(id));
//
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//    }
//
//    protected void createButtonSubmit(Button button) {
//        PageParameters parameters = new PageParameters();
//        parameters.add("templateId", this.templateValue.getId());
//        setResponsePage(HookCreatePage.class, parameters);
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column)) {
//            String value = (String) model.get(column);
//            PageParameters parameters = new PageParameters();
//            parameters.add("hookId", model.get("id"));
//            return new LinkCell(HookModifyPage.class, parameters, value);
//        } else if ("template".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("active".equals(column)) {
//            Long value = (Long) model.get(column);
//            if (value != null && value == 1) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
