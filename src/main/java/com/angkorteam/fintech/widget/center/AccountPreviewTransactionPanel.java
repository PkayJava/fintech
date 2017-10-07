package com.angkorteam.fintech.widget.center;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.client.center.TransactionPreviewPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class AccountPreviewTransactionPanel extends Panel {

    public static final String DEPOSIT = "1";
    public static final String WITHDRAW = "2";
    public static final String PAY_CHARGE = "3";

    protected String accountId;

    protected Page itemPage;

    protected DataTable<Map<String, Object>, String> dataTable;

    protected JdbcProvider dataProvider;

    public AccountPreviewTransactionPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.dataProvider = new JdbcProvider("m_savings_account_transaction");
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.boardField("transaction_date", "transactionDate", Date.class);
        this.dataProvider.boardField("case transaction_type_enum when 1 then 'Deposit' when 2 then 'Withdraw' when 7  then 'Fee Charge' else concat(transaction_type_enum,'') end", "transactionType", String.class);
        this.dataProvider.boardField("IF(transaction_type_enum in (1), amount, null)", "credit", Double.class);
        this.dataProvider.boardField("IF(transaction_type_enum in (2,7), amount, null)", "debit", Double.class);
        this.dataProvider.boardField("running_balance_derived", "balance", Double.class);
        this.dataProvider.applyWhere("savings_account_id", "savings_account_id = " + this.accountId);
        this.dataProvider.setSort("id", SortOrder.DESCENDING);

        List<IColumn<Map<String, Object>, String>> dataColumns = Lists.newArrayList();
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::idDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Transaction Date"), "transactionDate", "transactionDate", this::transactionDateDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Transaction Type"), "transactionType", "transactionType", this::transactionTypeDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Debit"), "debit", "debit", this::debitDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Credit"), "credit", "credit", this::creditDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::balanceDataColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.dataProvider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", dataColumns, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

    }

    protected void initData() {
        this.accountId = new PropertyModel<String>(this.itemPage, "accountId").getObject();
    }

    protected ItemPanel transactionDateDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd/MM/yyyy");
    }

    protected ItemPanel balanceDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel creditDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel debitDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel transactionTypeDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel idDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long name = (Long) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("transactionId", model.get("id"));
        return new LinkCell(TransactionPreviewPage.class, parameters, Model.of(name));
    }

}
