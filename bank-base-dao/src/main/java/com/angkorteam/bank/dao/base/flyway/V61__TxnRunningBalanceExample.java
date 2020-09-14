package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;

public class V61__TxnRunningBalanceExample extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V61__TxnRunningBalanceExample;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
        DeleteQuery deleteQuery = null;
        SelectQuery selectQuery = null;

        Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
        Table stretchy_report_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_report_parameter");
        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
        Table stretchy_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_parameter");

        selectQuery = new SelectQuery(stretchy_report.getName());
        selectQuery.addField(stretchy_report.getColumnByName("id").getName());
        selectQuery.addWhere(stretchy_report.getColumnByName("report_name").getName() + " = :name", "TxnRunningBalances");
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Long.class);
        if (id != null) {
            deleteQuery = new DeleteQuery(stretchy_report_parameter.getName());
            deleteQuery.addWhere(stretchy_report_parameter.getColumnByName("report_id").getName() + " = :id", id);
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }

        deleteQuery = new DeleteQuery(stretchy_report.getName());
        deleteQuery.addWhere(stretchy_report.getColumnByName("report_name").getName() + " = :name", "TxnRunningBalances");
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        deleteQuery = new DeleteQuery(m_permission.getName());
        deleteQuery.addWhere(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "TxnRunningBalances");
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        insert_stretchy_report(named, stretchy_report, "TxnRunningBalances", "Table", "Transaction", false, false, "Running Balance Txn report for Individual Lending.\nSuitable for small MFI's.  Larger could use it using the branch or other parameters.\nBasically, suck it and see if its quick enough for you out-of-te box or whether it needs performance work in your situation.\n", "\nselect date('${startDate}') as 'Transaction Date', 'Opening Balance' as `Transaction Type`, null as Office,\n    null as 'Loan Officer', null as `Loan Account No`, null as `Loan Product`, null as `Currency`,\n    null as `Client Account No`, null as Client,\n    null as Amount, null as Principal, null as Interest,\n@totalOutstandingPrincipal :=\nifnull(round(sum(\n    if (txn.transaction_type_enum = 1 /* disbursement */,\n        ifnull(txn.amount,0.00),\n        ifnull(txn.principal_portion_derived,0.00) * -1))\n            ,2),0.00)  as 'Outstanding Principal',\n\n@totalInterestIncome :=\nifnull(round(sum(\n    if (txn.transaction_type_enum in (2,5,8) /* repayment, repayment at disbursal, recovery repayment */,\n        ifnull(txn.interest_portion_derived,0.00),\n        0))\n            ,2),0.00) as 'Interest Income',\n\n@totalWriteOff :=\nifnull(round(sum(\n    if (txn.transaction_type_enum = 6 /* write-off */,\n        ifnull(txn.principal_portion_derived,0.00),\n        0))\n            ,2),0.00) as 'Principal Write Off'\nfrom m_office o\njoin m_office ounder on ounder.hierarchy like concat(o.hierarchy, '%')\n                          and ounder.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_client c on c.office_id = ounder.id\njoin m_loan l on l.client_id = c.id\njoin m_product_loan lp on lp.id = l.product_id\njoin m_loan_transaction txn on txn.loan_id = l.id\nleft join m_currency cur on cur.code = l.currency_code\nwhere txn.is_reversed = false\nand txn.transaction_type_enum not in (10,11)\nand o.id = ${officeId}\nand txn.transaction_date < date('${startDate}')\n\nunion all\n\nselect x.`Transaction Date`, x.`Transaction Type`, x.Office, x.`Loan Officer`, x.`Loan Account No`, x.`Loan Product`, x.`Currency`,\n    x.`Client Account No`, x.Client, x.Amount, x.Principal, x.Interest,\ncast(round(\n    if (x.transaction_type_enum = 1 /* disbursement */,\n        @totalOutstandingPrincipal := @totalOutstandingPrincipal + x.`Amount`,\n        @totalOutstandingPrincipal := @totalOutstandingPrincipal - x.`Principal`)\n            ,2) as decimal(19,2)) as 'Outstanding Principal',\ncast(round(\n    if (x.transaction_type_enum in (2,5,8) /* repayment, repayment at disbursal, recovery repayment */,\n        @totalInterestIncome := @totalInterestIncome + x.`Interest`,\n        @totalInterestIncome)\n            ,2) as decimal(19,2)) as 'Interest Income',\ncast(round(\n    if (x.transaction_type_enum = 6 /* write-off */,\n        @totalWriteOff := @totalWriteOff + x.`Principal`,\n        @totalWriteOff)\n            ,2) as decimal(19,2)) as 'Principal Write Off'\nfrom\n(select txn.transaction_type_enum, txn.id as txn_id, txn.transaction_date as 'Transaction Date',\ncast(\n    ifnull(re.enum_message_property, concat('Unknown Transaction Type Value: ' , txn.transaction_type_enum))\n    as char) as 'Transaction Type',\nounder.`name` as Office, lo.display_name as 'Loan Officer',\nl.account_no  as 'Loan Account No', lp.`name` as 'Loan Product',\nifnull(cur.display_symbol, l.currency_code) as Currency,\nc.account_no as 'Client Account No', c.display_name as 'Client',\nifnull(txn.amount,0.00) as Amount,\nifnull(txn.principal_portion_derived,0.00) as Principal,\nifnull(txn.interest_portion_derived,0.00) as Interest\nfrom m_office o\njoin m_office ounder on ounder.hierarchy like concat(o.hierarchy, '%')\n                          and ounder.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_client c on c.office_id = ounder.id\njoin m_loan l on l.client_id = c.id\nleft join m_staff lo on lo.id = l.loan_officer_id\njoin m_product_loan lp on lp.id = l.product_id\njoin m_loan_transaction txn on txn.loan_id = l.id\nleft join m_currency cur on cur.code = l.currency_code\nleft join r_enum_value re on re.enum_name = 'transaction_type_enum'\n                        and re.enum_id = txn.transaction_type_enum\nwhere txn.is_reversed = false\nand txn.transaction_type_enum not in (10,11)\nand (ifnull(l.loan_officer_id, -10) = '${loanOfficerId}' or '-1' = '${loanOfficerId}')\nand o.id = ${officeId}\nand txn.transaction_date >= date('${startDate}')\nand txn.transaction_date <= date('${endDate}')\norder by txn.transaction_date, txn.id) x\n");

        insert_m_permission(named, m_permission, "report", "READ_TxnRunningBalances", "TxnRunningBalances", "READ", 0);

        id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Long.class);
        selectQuery = new SelectQuery(stretchy_parameter.getName());
        selectQuery.addField(stretchy_parameter.getColumnByName("id").getName());
        selectQuery.addWhere(stretchy_parameter.getColumnByName("parameter_name").getName() + " IN (:name)", "name", String.class, Arrays.asList("startDateSelect", "endDateSelect", "OfficeIdSelectOne", "loanOfficerIdSelectAll"));
        for (Long pid : named.queryForList(selectQuery.toSQL(), selectQuery.toParam(), Long.class)) {
            InsertQuery insertQuery = new InsertQuery(stretchy_report_parameter.getName());
            insertQuery.addValue(stretchy_report_parameter.getColumnByName("report_id").getName(), id);
            insertQuery.addValue(stretchy_report_parameter.getColumnByName("parameter_id").getName(), pid);
            named.update(insertQuery.toSQL(), insertQuery.toParam());
        }
    }

    protected void insert_stretchy_report(NamedParameterJdbcTemplate named, Table table, String report_name, String report_type, String report_category, boolean core_report, boolean use_report, String description, String report_sql) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("report_name").getName(), report_name);
        insertQuery.addValue(table.getColumnByName("report_type").getName(), report_type);
        insertQuery.addValue(table.getColumnByName("report_category").getName(), report_category);
        insertQuery.addValue(table.getColumnByName("core_report").getName(), core_report);
        insertQuery.addValue(table.getColumnByName("use_report").getName(), use_report);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        insertQuery.addValue(table.getColumnByName("report_sql").getName(), report_sql);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getName(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        insertQuery.addValue(table.getColumnByName("can_maker_checker").getName(), can_maker_checker);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
