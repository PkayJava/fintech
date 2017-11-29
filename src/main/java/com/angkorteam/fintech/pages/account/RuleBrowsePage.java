package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/5/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleBrowsePage extends Page {

    public static String DEBIT = "1";

    public static String CREDIT = "2";

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataTable();

        this.createLink = new BookmarkablePageLink<>("createLink", RuleCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initDataTable() {
        this.dataBlock = new WebMarkupBlock("dataBlock", WebMarkupBlock.Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("acc_accounting_rule");
        this.dataProvider.applyJoin("m_office", "LEFT JOIN m_office ON acc_accounting_rule.office_id = m_office.id");
        this.dataProvider.applyJoin("acc_gl_account_debit", "LEFT JOIN acc_gl_account debit ON acc_accounting_rule.debit_account_id = debit.id");
        this.dataProvider.applyJoin("acc_gl_account_credit", "LEFT JOIN acc_gl_account credit ON acc_accounting_rule.credit_account_id = credit.id");
        this.dataProvider.applyJoin("acc_rule", "LEFT JOIN acc_rule_tags debit_tag on acc_accounting_rule.id = debit_tag.acc_rule_id and debit_tag.acc_type_enum = " + DEBIT);
        this.dataProvider.applyJoin("m_code_value_debit", "LEFT JOIN m_code_value debit_tag_code on debit_tag.tag_id = debit_tag_code.id");
        this.dataProvider.applyJoin("acc_rule_tags", "LEFT JOIN acc_rule_tags credit_tag on acc_accounting_rule.id = credit_tag.acc_rule_id and credit_tag.acc_type_enum = " + CREDIT);
        this.dataProvider.applyJoin("m_code_value_credit", "LEFT JOIN m_code_value credit_tag_code on credit_tag.tag_id = credit_tag_code.id");
        this.dataProvider.setGroupBy("acc_accounting_rule.id");

        this.dataProvider.boardField("max(acc_accounting_rule.id)", "id", Long.class);
        this.dataProvider.boardField("max(acc_accounting_rule.name)", "rule_name", String.class);
        this.dataProvider.boardField("max(m_office.name)", "office_name", String.class);
        this.dataProvider.boardField("group_concat(debit_tag_code.code_value SEPARATOR ', ')", "debit_tags", String.class);
        this.dataProvider.boardField("max(debit.name)", "debit_account", String.class);
        this.dataProvider.boardField("group_concat(credit_tag_code.code_value SEPARATOR ', ')", "credit_tags", String.class);
        this.dataProvider.boardField("max(credit.name)", "credit_account", String.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "rule_name", "rule_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office_name", "office_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Debit Tags"), "debit_tags", "debit_tags", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Debit Account"), "debit_account", "debit_account", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Credit Tags"), "credit_tags", "credit_tags", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Credit Amount"), "credit_account", "credit_account", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(column)) {
            Long value = (Long) model.get("id");
            JsonNode node = null;
            try {
                node = AccountingRuleHelper.delete((Session) getSession(), String.valueOf(value));
            } catch (UnirestException e) {
            }
            reportError(node, target);
            target.add(this.dataTable);
        } else if ("post".equals(column)) {
            Long value = (Long) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("ruleId", value);
            setResponsePage(FrequentPostPage.class, parameters);
        }
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        actions.add(new ActionItem("post", Model.of("Post"), ItemCss.INFO));
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("rule_name".equals(column)) {
            String ruleName = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("ruleId", model.get("id"));
            return new LinkCell(RuleModifyPage.class, parameters, Model.of(ruleName));
        } else if ("office_name".equals(column) || "debit_tags".equals(column) || "debit_account".equals(column) || "credit_account".equals(column) || "credit_tags".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
