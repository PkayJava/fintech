//package com.angkorteam.fintech.pages;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MPasswordValidationPolicy;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.PasswordPreferencesHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.JdbcProvider;
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
//import com.google.common.collect.Lists;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class PasswordPreferencesPage extends Page {
//
//    protected FilterForm<Map<String, String>> form;
//
//    protected UIRow row1;
//
//    protected JdbcProvider dataProvider;
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
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
//            breadcrumb.setLabel("Organization");
//            breadcrumb.setPage(OrganizationDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Password Preference");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.dataProvider = new JdbcProvider(MPasswordValidationPolicy.NAME);
//        this.dataProvider.boardField(MPasswordValidationPolicy.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MPasswordValidationPolicy.Field.ACTIVE, "active", Boolean.class);
//        this.dataProvider.boardField(MPasswordValidationPolicy.Field.DESCRIPTION, "description", String.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Active"), "active", "active", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "description", "description", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new FilterForm<>("form", this.dataProvider);
//        add(this.form);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.dataBlock = this.row1.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form));
//        this.dataIContainer.add(this.dataTable);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
//        add(this.closeLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        PasswordPreferencesHelper.update((Session) getSession(), String.valueOf(id));
//
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        Long active = (Long) model.get("active");
//        if (active == null || active != 1) {
//            actions.add(new ActionItem("activate", Model.of("Activate"), ItemCss.PRIMARY));
//        }
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("active".equals(column)) {
//            Long value = (Long) model.get(column);
//            if (value != null && value == 1) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        } else if ("description".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknow " + column);
//    }
//
//}
