package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V90__ClientPerformanceHistoryReports extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V90__ClientPerformanceHistoryReports;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        dataContext.refreshSchemas();
        Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
        insert_stretchy_report(named, stretchy_report, "ClientSummary ", "Table", null, null, "SELECT x.* FROM m_client c, m_office o, \n(\n       SELECT a.loanCycle, a.activeLoans, b.lastLoanAmount, d.activeSavings, d.totalSavings FROM \n	(SELECT IFNULL(MAX(l.loan_counter),0) AS loanCycle, COUNT(l.id) AS activeLoans FROM m_loan l WHERE l.loan_status_id=300 AND l.client_id=${clientId}) a, \n	(SELECT count(l.id), IFNULL(l.principal_amount,0) AS 'lastLoanAmount' FROM m_loan l WHERE l.client_id=${clientId} AND l.disbursedon_date = (SELECT IFNULL(MAX(disbursedon_date),NOW()) FROM m_loan where client_id=${clientId} and loan_status_id=300) group by l.principal_amount) b, \n	(SELECT COUNT(s.id) AS 'activeSavings', IFNULL(SUM(s.account_balance_derived),0) AS 'totalSavings' FROM m_savings_account s WHERE s.status_enum=300 AND s.client_id=${clientId}) d\n) x\nWHERE c.id=${clientId} AND o.id = c.office_id AND o.hierarchy LIKE CONCAT('${currentUserHierarchy}', '%')", "Utility query for getting the client summary details", 1, 0);
        insert_stretchy_report(named, stretchy_report, "LoanCyclePerProduct", "Table", null, null, "SELECT lp.name AS 'productName', MAX(l.loan_product_counter) AS 'loanProductCycle' FROM m_loan l JOIN m_product_loan lp ON l.product_id=lp.id WHERE lp.include_in_borrower_cycle=1 AND l.loan_product_counter IS NOT NULL AND l.client_id=${clientId} GROUP BY l.product_id", "Utility query for getting the client loan cycle details", 1, 0);
    }

    protected void insert_stretchy_report(NamedParameterJdbcTemplate named, Table table, String report_name, String report_type, String report_subtype, String report_category, String report_sql, String description, long core_report, long use_report) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("report_name").getName(), report_name);
        insertQuery.addValue(table.getColumnByName("core_report").getName(), core_report);
        insertQuery.addValue(table.getColumnByName("use_report").getName(), use_report);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        insertQuery.addValue(table.getColumnByName("report_sql").getName(), report_sql);
        insertQuery.addValue(table.getColumnByName("report_type").getName(), report_type);
        insertQuery.addValue(table.getColumnByName("report_subtype").getName(), report_subtype);
        insertQuery.addValue(table.getColumnByName("report_category").getName(), report_category);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}