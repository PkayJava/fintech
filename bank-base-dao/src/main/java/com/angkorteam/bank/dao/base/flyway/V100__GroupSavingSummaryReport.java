package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V100__GroupSavingSummaryReport extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V100__GroupSavingSummaryReport;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
        insert_stretchy_report(named, stretchy_report, "GroupSavingSummary", "Table", null, null, "select ifnull(cur.display_symbol, sa.currency_code) as currency,\ncount(sa.id) as totalSavingAccounts, ifnull(sum(sa.account_balance_derived),0) as totalSavings\nfrom m_group topgroup\njoin m_office o on o.id = topgroup.office_id and o.hierarchy like concat(\'${currentUserHierarchy}\', \'%\')\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, \'%\')\njoin m_savings_account sa on sa.group_id = g.id\nleft join m_currency cur on cur.code = sa.currency_code\nwhere topgroup.id = ${groupId}\nand sa.activatedon_date is not null\ngroup by sa.currency_code", "Utility query for getting group or center saving summary details for a group_id", 1, 0);
    }

    protected void insert_stretchy_report(NamedParameterJdbcTemplate named, Table table, String report_name, String report_type, String report_subtype, String report_category, String report_sql, String description, long core_report, long use_report) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("report_name").getName(), report_name);
        insertQuery.addValue(table.getColumnByName("report_type").getName(), report_type);
        insertQuery.addValue(table.getColumnByName("report_subtype").getName(), report_subtype);
        insertQuery.addValue(table.getColumnByName("use_report").getName(), use_report);
        insertQuery.addValue(table.getColumnByName("report_sql").getName(), report_sql);
        insertQuery.addValue(table.getColumnByName("report_category").getName(), report_category);
        insertQuery.addValue(table.getColumnByName("core_report").getName(), core_report);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}