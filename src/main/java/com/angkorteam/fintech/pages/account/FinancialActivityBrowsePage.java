package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.FinancialActivityType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
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
 * Created by socheatkhauv on 7/12/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FinancialActivityBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Financial Activity Mapping");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("acc_gl_financial_activity_account");
        this.provider.addJoin("LEFT JOIN acc_gl_account ON acc_gl_financial_activity_account.gl_account_id = acc_gl_account.id");
        this.provider.boardField("acc_gl_financial_activity_account.id", "id", Long.class);
        this.provider.boardField("acc_gl_account.name", "account", String.class);
        this.provider.boardField("acc_gl_financial_activity_account.financial_activity_type", "type", Integer.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Financial Activity"), "type", "type", this::typeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Account Name"), "account", "account", this::accountColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", FinancialActivityCreatePage.class);
        add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        if ("modify".equals(s)) {
            PageParameters parameters = new PageParameters();
            parameters.add("financialActivityId", id);
            setResponsePage(FinancialActivityModifyPage.class, parameters);
        } else if ("delete".equals(s)) {
            JsonNode node = null;
            try {
                FinancialActivityHelper.delete((Session) getSession(), String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, target);
            target.add(this.dataTable);
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel typeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        String text = null;
        if (FinancialActivityType.AssetTransfer.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.AssetTransfer.getDescription();
        } else if (FinancialActivityType.CashAtTellersCashiers.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.CashAtTellersCashiers.getDescription();
        } else if (FinancialActivityType.FundSource.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.FundSource.getDescription();
        } else if (FinancialActivityType.MainCashAccountOrCashAtVault.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.MainCashAccountOrCashAtVault.getDescription();
        } else if (FinancialActivityType.OpeningBalancesTransferContra.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.OpeningBalancesTransferContra.getDescription();
        } else if (FinancialActivityType.PayableDividends.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.PayableDividends.getDescription();
        } else if (FinancialActivityType.LiabilityTransfer.getLiteral().equals(String.valueOf(value))) {
            text = value + "." + FinancialActivityType.LiabilityTransfer.getDescription();
        }
        return new TextCell(text);
    }

    private ItemPanel accountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
