//package com.angkorteam.fintech.pages.account;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
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
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.helper.GLAccountHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.AccountingPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
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
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/27/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class AccountBrowsePage extends Page {
//
//    protected FilterForm<Map<String, String>> form;
//
//    protected UIRow row1;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected JdbcProvider dataProvider;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    protected BookmarkablePageLink<Void> hierarchyLink;
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
//            breadcrumb.setLabel("Chart of Accounts");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        List<String> classification_enum = Lists.newArrayList();
//        for (AccountType accountType : AccountType.values()) {
//            classification_enum.add("WHEN " + accountType.getLiteral() + " THEN '" + accountType.getDescription() + "'");
//        }
//        this.dataProvider = new JdbcProvider(AccGLAccount.NAME);
//        this.dataProvider.boardField(AccGLAccount.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(AccGLAccount.Field.NAME, "name", String.class);
//        this.dataProvider.boardField(AccGLAccount.Field.GL_CODE, "gl_code", String.class);
//        this.dataProvider.boardField("CASE " + AccGLAccount.Field.CLASSIFICATION_ENUM + " " + StringUtils.join(classification_enum, " ") + " END", "classification_enum", String.class);
//        this.dataProvider.boardField(AccGLAccount.Field.ACCOUNT_USAGE, "account_usage", Long.class);
//        this.dataProvider.boardField(AccGLAccount.Field.DISABLED, "disabled", Boolean.class);
//        this.dataProvider.boardField(AccGLAccount.Field.MANUAL_JOURNAL_ENTRIES_ALLOWED, "manual_journal_entries_allowed", Boolean.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Account"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("GL Code"), "gl_code", "gl_code", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account Type"), "classification_enum", "classification_enum", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Is Disabled ?"), "disabled", "disabled", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Is Manual Entries Allowed ?"), "manual_journal_entries_allowed", "manual_journal_entries_allowed", this::dataColumn));
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
//        this.hierarchyLink = new BookmarkablePageLink<>("hierarchyLink", AccountHierarchyPage.class);
//        add(this.hierarchyLink);
//
//        this.createLink = new BookmarkablePageLink<>("createLink", AccountCreatePage.class);
//        add(this.createLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void dataClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        if ("delete".equals(link)) {
//            JsonNode node = GLAccountHelper.delete((Session) getSession(), String.valueOf(id));
//
//            reportError(node, target);
//            target.add(this.dataTable);
//        } else if ("enable".equals(link)) {
//            JsonNode node = GLAccountHelper.enable((Session) getSession(), String.valueOf(id));
//
//            reportError(node, target);
//            target.add(this.dataTable);
//        } else if ("disable".equals(link)) {
//            JsonNode node = GLAccountHelper.disable((Session) getSession(), String.valueOf(id));
//
//            reportError(node, target);
//            target.add(this.dataTable);
//        }
//    }
//
//    protected List<ActionItem> dataAction(String link, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        Boolean disabled = (Boolean) model.get("disabled");
//        if (disabled == null || disabled) {
//            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
//        } else {
//            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
//        }
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column) || "classification_enum".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("gl_code".equals(column)) {
//            String value = (String) model.get(column);
//            PageParameters parameters = new PageParameters();
//            parameters.add("accountId", model.get("id"));
//            return new LinkCell(AccountModifyPage.class, parameters, value);
//        } else if ("disabled".equals(column)) {
//            Boolean value = (Boolean) model.get(column);
//            if (value == null || value) {
//                return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Success, Model.of("No"));
//            }
//        } else if ("manual_journal_entries_allowed".equals(column)) {
//            Boolean manualEntry = (Boolean) model.get(column);
//            if (manualEntry != null && manualEntry) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        } else {
//            throw new WicketRuntimeException("unknow " + column);
//        }
//    }
//
//}
