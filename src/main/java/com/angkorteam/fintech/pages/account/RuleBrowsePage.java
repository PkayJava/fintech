package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/5/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleBrowsePage extends Page {

    public static String DEBIT = "1";

    public static String CREDIT = "2";

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("acc_accounting_rule");
        this.provider.addJoin("LEFT JOIN m_office ON acc_accounting_rule.office_id = m_office.id");
        this.provider.addJoin("LEFT JOIN acc_gl_account debit ON acc_accounting_rule.debit_account_id = debit.id");
        this.provider.addJoin("LEFT JOIN acc_gl_account credit ON acc_accounting_rule.credit_account_id = credit.id");
        this.provider.addJoin(
                "LEFT JOIN acc_rule_tags debit_tag on acc_accounting_rule.id = debit_tag.acc_rule_id and debit_tag.acc_type_enum = "
                        + DEBIT);
        this.provider.addJoin("LEFT JOIN m_code_value debit_tag_code on debit_tag.tag_id = debit_tag_code.id");
        this.provider.addJoin(
                "LEFT JOIN acc_rule_tags credit_tag on acc_accounting_rule.id = credit_tag.acc_rule_id and credit_tag.acc_type_enum = "
                        + CREDIT);
        this.provider.addJoin("LEFT JOIN m_code_value credit_tag_code on credit_tag.tag_id = credit_tag_code.id");
        this.provider.setGroupBy("acc_accounting_rule.id");

        this.provider.boardField("max(acc_accounting_rule.id)", "id", Long.class);
        this.provider.boardField("max(acc_accounting_rule.name)", "rule_name", String.class);
        this.provider.boardField("max(m_office.name)", "office_name", String.class);
        this.provider.boardField("group_concat(debit_tag_code.code_value SEPARATOR ', ')", "debit_tags", String.class);
        this.provider.boardField("max(debit.name)", "debit_account", String.class);
        this.provider.boardField("group_concat(credit_tag_code.code_value SEPARATOR ', ')", "credit_tags",
                String.class);
        this.provider.boardField("max(credit.name)", "credit_account", String.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new ActionColumn<>(Model.of(""), this::postItem, this::postClick));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "rule_name", "rule_name",
                this::ruleNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Office"), "office_name",
                "office_name", this::officeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Debit Tags"), "debit_tags",
                "debit_tags", this::debitTagsColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Debit Account"), "debit_account",
                "debit_account", this::debitAccountColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Credit Tags"), "credit_tags",
                "credit_tags", this::creditTagsColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Credit Amount"), "credit_account",
                "credit_account", this::creditAccountColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", RuleCreatePage.class);
        add(this.createLink);
    }

    private void postClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Long id = (Long) stringObjectMap.get("id");
        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", id);
        setResponsePage(FrequentPostPage.class, parameters);
    }

    private List<ActionItem> postItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("post", Model.of("Post"), ItemCss.INFO));
        return actions;
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Long id = (Long) stringObjectMap.get("id");
        if ("delete".equals(s)) {
            JsonNode node = null;
            try {
                node = AccountingRuleHelper.delete((Session) getSession(), String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, ajaxRequestTarget);
            ajaxRequestTarget.add(this.dataTable);
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel ruleNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String ruleName = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", model.get("id"));
        return new LinkCell(RuleModifyPage.class, parameters, Model.of(ruleName));
    }

    private ItemPanel officeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String office = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(office));
    }

    private ItemPanel debitTagsColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String debitTags = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(debitTags));
    }

    private ItemPanel debitAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String debitAccount = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(debitAccount));
    }

    private ItemPanel creditAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String creditAccount = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(creditAccount));
    }

    private ItemPanel creditTagsColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String creditTags = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(creditTags));
    }

}
