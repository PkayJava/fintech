package com.angkorteam.fintech.widget.client.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.ddl.MSavingsAccountTransaction;
import com.angkorteam.fintech.pages.client.center.TransactionPreviewPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class SavingAccountPreviewTransactionPanel extends Panel {

    public static final String DEPOSIT = "1";
    public static final String WITHDRAW = "2";
    public static final String PAY_CHARGE = "3";

    protected String savingId;

    protected Page itemPage;

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    public SavingAccountPreviewTransactionPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initComponent() {
        this.dataProvider = new JdbcProvider(MSavingsAccountTransaction.NAME);
        this.dataProvider.boardField(MSavingsAccountTransaction.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MSavingsAccountTransaction.Field.TRANSACTION_DATE, "transactionDate", Date.class);
        this.dataProvider.boardField("CASE " + MSavingsAccountTransaction.Field.TRANSACTION_TYPE_ENUM + " WHEN 1 THEN 'Deposit' WHEN 2 THEN 'Withdraw' WHEN 7 THEN 'Fee Charge' ELSE CONCAT(" + MSavingsAccountTransaction.Field.TRANSACTION_TYPE_ENUM + ",'') END", "transactionType", String.class);
        this.dataProvider.boardField("IF(" + MSavingsAccountTransaction.Field.TRANSACTION_TYPE_ENUM + " IN (1), " + MSavingsAccountTransaction.Field.AMOUNT + ", null)", "credit", Double.class);
        this.dataProvider.boardField("IF(" + MSavingsAccountTransaction.Field.TRANSACTION_TYPE_ENUM + " IN (2,7), " + MSavingsAccountTransaction.Field.AMOUNT + ", null)", "debit", Double.class);
        this.dataProvider.boardField(MSavingsAccountTransaction.Field.RUNNING_BALANCE_DERIVED, "balance", Double.class);
        this.dataProvider.applyWhere("savings_account_id", MSavingsAccountTransaction.Field.SAVINGS_ACCOUNT_ID + " = " + this.savingId);
        this.dataProvider.setSort("id", SortOrder.DESCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Transaction Date"), "transactionDate", "transactionDate", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Transaction Type"), "transactionType", "transactionType", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Debit"), "debit", "debit", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Credit"), "credit", "credit", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.savingId = new PropertyModel<String>(this.itemPage, "savingId").getObject();
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long name = (Long) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("transactionId", model.get("id"));
            return new LinkCell(TransactionPreviewPage.class, parameters, name);
        } else if ("transactionDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        } else if ("transactionType".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("debit".equals(column) || "credit".equals(column) || "balance".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
