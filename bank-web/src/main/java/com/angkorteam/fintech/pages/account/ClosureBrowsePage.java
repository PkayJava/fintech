//package com.angkorteam.fintech.pages.account;
//
//import java.util.ArrayList;
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
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.AccGLClosure;
//import com.angkorteam.fintech.ddl.MAppUser;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.AccountingClosureHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.AccountingPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
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
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 7/12/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClosureBrowsePage extends Page {
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
//            breadcrumb.setLabel("Accounting");
//            breadcrumb.setPage(AccountingPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Account Closure");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.dataProvider = new JdbcProvider(AccGLClosure.NAME);
//        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + AccGLClosure.NAME + "." + AccGLClosure.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
//        this.dataProvider.applyJoin("m_appuser", "LEFT JOIN " + MAppUser.NAME + " ON " + AccGLClosure.NAME + "." + AccGLClosure.Field.CREATED_BY_ID + " = " + MAppUser.NAME + "." + MAppUser.Field.ID);
//        this.dataProvider.boardField(AccGLClosure.NAME + "." + AccGLClosure.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
//        this.dataProvider.boardField(AccGLClosure.NAME + "." + AccGLClosure.Field.CLOSING_DATE, "closing_date", Date.class);
//        this.dataProvider.boardField(AccGLClosure.NAME + "." + AccGLClosure.Field.COMMENTS, "comment", String.class);
//        this.dataProvider.boardField(MAppUser.NAME + "." + MAppUser.Field.USERNAME, "created_by", String.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = new ArrayList<>();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Accounting Closure Date"), "closing_date", "closing_date", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Comments"), "comment", "comment", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Created By"), "created_by", "created_by", this::dataColumn));
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
//        this.dataBlock.add(this.dataIContainer);
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form));
//        this.dataIContainer.add(this.dataTable);
//
//        this.createLink = new BookmarkablePageLink<>("createLink", ClosureCreatePage.class);
//        add(this.createLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
//        Long value = (Long) model.get("id");
//        JsonNode node = AccountingClosureHelper.delete((Session) getSession(), String.valueOf(value));
//
//        reportError(node, target);
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("office".equals(column) || "comment".equals(column) || "created_by".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("closing_date".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yyyy");
//        }
//        throw new WicketRuntimeException("Unknown" + column);
//    }
//
//}
