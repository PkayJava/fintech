//package com.angkorteam.fintech.pages.account;
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
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.AccAccountingRule;
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.ddl.AccRuleTags;
//import com.angkorteam.fintech.ddl.MCodeValue;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.helper.AccountingRuleHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.AccountingPage;
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
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 7/5/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class RuleBrowsePage extends Page {
//
//    public static String DEBIT = "2";
//
//    public static String CREDIT = "1";
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
//            breadcrumb.setLabel("Accounting Rule");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.dataProvider = new JdbcProvider(AccAccountingRule.NAME);
//        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + AccAccountingRule.NAME + "." + AccAccountingRule.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
//        this.dataProvider.applyJoin("acc_gl_account_debit", "LEFT JOIN " + AccGLAccount.NAME + " debit ON " + AccAccountingRule.NAME + "." + AccAccountingRule.Field.DEBIT_ACCOUNT_ID + " = debit." + AccGLAccount.Field.ID);
//        this.dataProvider.applyJoin("acc_gl_account_credit", "LEFT JOIN " + AccGLAccount.NAME + " credit ON " + AccAccountingRule.NAME + "." + AccAccountingRule.Field.CREDIT_ACCOUNT_ID + " = credit." + AccGLAccount.Field.ID);
//        this.dataProvider.applyJoin("acc_rule_debit", "LEFT JOIN " + AccRuleTags.NAME + " debit_tag ON " + AccAccountingRule.NAME + "." + AccAccountingRule.Field.ID + " = debit_tag." + AccRuleTags.Field.ACC_RULE_ID + " and debit_tag." + AccRuleTags.Field.ACC_TYPE_ENUM + " = " + DEBIT);
//        this.dataProvider.applyJoin("acc_rule_credit", "LEFT JOIN " + AccRuleTags.NAME + " credit_tag ON " + AccAccountingRule.NAME + "." + AccAccountingRule.Field.ID + " = credit_tag." + AccRuleTags.Field.ACC_RULE_ID + " and credit_tag." + AccRuleTags.Field.ACC_TYPE_ENUM + " = " + CREDIT);
//        this.dataProvider.applyJoin("m_code_value_debit", "LEFT JOIN " + MCodeValue.NAME + " debit_tag_code ON debit_tag." + AccRuleTags.Field.TAG_ID + " = debit_tag_code." + MCodeValue.Field.ID);
//        this.dataProvider.applyJoin("m_code_value_credit", "LEFT JOIN " + MCodeValue.NAME + " credit_tag_code ON credit_tag." + AccRuleTags.Field.TAG_ID + " = credit_tag_code." + MCodeValue.Field.ID);
//        this.dataProvider.setGroupBy(AccAccountingRule.NAME + "." + AccAccountingRule.Field.ID);
//        this.dataProvider.boardField("max(" + AccAccountingRule.NAME + "." + AccAccountingRule.Field.ID + ")", "id", Long.class);
//        this.dataProvider.boardField("max(" + AccAccountingRule.NAME + "." + AccAccountingRule.Field.NAME + ")", "rule_name", String.class);
//        this.dataProvider.boardField("max(" + MOffice.NAME + "." + MOffice.Field.NAME + ")", "office_name", String.class);
//        this.dataProvider.boardField("group_concat(debit_tag_code." + MCodeValue.Field.CODE_VALUE + " SEPARATOR ', ')", "debit_tags", String.class);
//        this.dataProvider.boardField("max(debit." + AccGLAccount.Field.NAME + ")", "debit_account", String.class);
//        this.dataProvider.boardField("group_concat(credit_tag_code." + MCodeValue.Field.CODE_VALUE + " SEPARATOR ', ')", "credit_tags", String.class);
//        this.dataProvider.boardField("max(credit." + AccGLAccount.Field.NAME + ")", "credit_account", String.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "rule_name", "rule_name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office_name", "office_name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Debit Tags"), "debit_tags", "debit_tags", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Debit Account"), "debit_account", "debit_account", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Credit Tags"), "credit_tags", "credit_tags", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Credit Account"), "credit_account", "credit_account", this::dataColumn));
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
//        this.createLink = new BookmarkablePageLink<>("createLink", RuleCreatePage.class);
//        add(this.createLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void initDataTable() {
//
//    }
//
//    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
//        if ("delete".equals(column)) {
//            Long value = (Long) model.get("id");
//            JsonNode node = AccountingRuleHelper.delete((Session) getSession(), String.valueOf(value));
//            reportError(node, target);
//            target.add(this.dataTable);
//        } else if ("post".equals(column)) {
//            Long value = (Long) model.get("id");
//            PageParameters parameters = new PageParameters();
//            parameters.add("ruleId", value);
//            setResponsePage(FrequentPostPage.class, parameters);
//        }
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        actions.add(new ActionItem("post", Model.of("Post"), ItemCss.INFO));
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("rule_name".equals(column)) {
//            String ruleName = (String) model.get(column);
//            PageParameters parameters = new PageParameters();
//            parameters.add("ruleId", model.get("id"));
//            return new LinkCell(RuleModifyPage.class, parameters, Model.of(ruleName));
//        } else if ("office_name".equals(column) || "debit_tags".equals(column) || "debit_account".equals(column) || "credit_account".equals(column) || "credit_tags".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
