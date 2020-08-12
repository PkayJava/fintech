//package com.angkorteam.fintech.pages.teller;
//
//import java.util.Date;
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
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MTellers;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.TellerHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.OrganizationDashboardPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.table.LinkCell;
//import com.angkorteam.fintech.table.TextCell;
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
// * Created by socheatkhauv on 7/13/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class TellerBrowsePage extends Page {
//
//    protected FilterForm<Map<String, String>> form;
//
//    protected UIRow row1;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    protected BookmarkablePageLink<Void> createLink;
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
//            breadcrumb.setLabel("Teller");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.dataProvider = new JdbcProvider(MTellers.NAME);
//        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " on " + MTellers.NAME + "." + MTellers.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
//        this.dataProvider.boardField(MTellers.NAME + "." + MTellers.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MTellers.NAME + "." + MTellers.Field.NAME, "name", String.class);
//        this.dataProvider.boardField("case " + MTellers.NAME + "." + MTellers.Field.STATE + " when 300 then 'Active' when 400 then 'Inactive' end", "state", Long.class);
//        this.dataProvider.boardField(MTellers.NAME + "." + MTellers.Field.VALID_FROM, "valid_from", Date.class);
//        this.dataProvider.boardField(MTellers.NAME + "." + MTellers.Field.VALID_TO, "valid_to", Date.class);
//        this.dataProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "branch", String.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Branch"), "branch", "branch", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("State"), "state", "state", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid From"), "valid_from", "valid_from", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid To"), "valid_to", "valid_to", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));
//
//    }
//
//    @Override
//    protected void initComponent() {
//        this.createLink = new BookmarkablePageLink<>("createLink", TellerCreatePage.class);
//        add(this.createLink);
//
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
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        TellerHelper.delete((Session) getSession(), String.valueOf(id));
//
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> actionItem(String s, Map<String, Object> model) {
//        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("branch".equals(column) || "state".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("name".equals(column)) {
//            String value = (String) model.get(column);
//            PageParameters parameters = new PageParameters();
//            parameters.add("tellerId", model.get("id"));
//            return new LinkCell(TellerModifyPage.class, parameters, value);
//        } else if ("valid_from".equals(column) || "valid_to".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yyyy");
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
