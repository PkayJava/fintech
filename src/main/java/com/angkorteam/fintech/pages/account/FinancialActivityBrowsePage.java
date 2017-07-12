package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.FinancialActivityType;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class FinancialActivityBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

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

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Long id = (Long) stringObjectMap.get("id");
        if ("modify".equals(s)) {
            PageParameters parameters = new PageParameters();
            parameters.add("financialActivityId", id);
            setResponsePage(FinancialActivityModifyPage.class, parameters);
        } else if ("delete".equals(s)) {
            JsonNode node = null;
            try {
                FinancialActivityHelper.deleteFinancialActivity(String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, ajaxRequestTarget);
            ajaxRequestTarget.add(this.dataTable);
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel typeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer type = (Integer) model.get(jdbcColumn);
        if (FinancialActivityType.AssetTransfer.getLiteral().equals(String.valueOf(type))) {
            return new TextCell(Model.of(type + "." + FinancialActivityType.AssetTransfer.getDescription()));
//        } else if (FinancialActivityType.CashAtTellersCashiers.getLiteral().equals(String.valueOf(type))) {
//            return new TextCell(Model.of(type + "." + FinancialActivityType.CashAtTellersCashiers.getDescription()));
        } else if (FinancialActivityType.FundSource.getLiteral().equals(String.valueOf(type))) {
            return new TextCell(Model.of(type + "." + FinancialActivityType.FundSource.getDescription()));
//        } else if (FinancialActivityType.MainCashAccountOrCashAtVault.getLiteral().equals(String.valueOf(type))) {
//            return new TextCell(Model.of(type + "." + FinancialActivityType.MainCashAccountOrCashAtVault.getDescription()));
        } else if (FinancialActivityType.OpeningBalancesTransferContra.getLiteral().equals(String.valueOf(type))) {
            return new TextCell(Model.of(type + "." + FinancialActivityType.OpeningBalancesTransferContra.getDescription()));
        } else if (FinancialActivityType.PayableDividends.getLiteral().equals(String.valueOf(type))) {
            return new TextCell(Model.of(type + "." + FinancialActivityType.PayableDividends.getDescription()));
        } else if (FinancialActivityType.LiabilityTransfer.getLiteral().equals(String.valueOf(type))) {
            return new TextCell(Model.of(type + "." + FinancialActivityType.LiabilityTransfer.getDescription()));
        } else {
            return new TextCell(Model.of(""));
        }
    }

    private ItemPanel accountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String account = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(account));
    }

}
