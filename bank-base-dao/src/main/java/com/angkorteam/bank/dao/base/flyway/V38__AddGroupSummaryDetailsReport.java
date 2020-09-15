package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V38__AddGroupSummaryDetailsReport extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V38__AddGroupSummaryDetailsReport;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
            insert_stretchy_report(named, stretchy_report, "GroupSummaryDetails", "Table", 1, 0, "Utility query for getting group summary details for a group_id", "/*\nActive Client is a client linked to the 'group' via m_group_client\nand with an active status_enum (not sure what it means if the group is inactive)\n\nActive Borrowers - Borrow may be a client or a 'group'\n\nCurrency is an outstanding issue\n\nCouldn't set up group savings for a client\n(as opposed to opening a individual clients savings account)\n*/\nselect x.*\nfrom m_office o,\nm_group g,\n\n(select a.activeClients,\n(b.activeClientLoans + c.activeGroupLoans) as activeLoans,\nb.activeClientLoans, c.activeGroupLoans,\n(b.activeClientBorrowers + c.activeGroupBorrowers) as activeBorrowers,\nb.activeClientBorrowers, c.activeGroupBorrowers, d.*,\n0 as activeSavings, 0 as totalSavingsAmount\nfrom\n(select count(*) as activeClients\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_group_client gc on gc.group_id = g.id\njoin m_client c on c.id = gc.client_id\nwhere topgroup.id = ${groupId}\nand c.status_enum = 300) a,\n\n(select count(*) as activeClientLoans,\ncount(distinct(l.client_id)) as activeClientBorrowers\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id and l.client_id is not null\nwhere topgroup.id = ${groupId}\nand l.loan_status_id = 300) b,\n\n(select count(*) as activeGroupLoans,\ncount(distinct(l.group_id)) as activeGroupBorrowers\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id and l.client_id is null\nwhere topgroup.id = ${groupId}\nand l.loan_status_id = 300) c,\n\n(select ifnull(sum(l.principal_disbursed_derived),0) as totalDisbursedAmount,\nifnull(sum(l.principal_outstanding_derived),0) as totalLoanOutstandingAmount,\ncount(laa.loan_id) as overdueLoans, ifnull(sum(laa.total_overdue_derived), 0) as totalLoanOverdueAmount\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\nwhere topgroup.id = ${groupId}\nand l.disbursedon_date is not null) d) x\n\nwhere g.id = ${groupId}\nand o.id = g.office_id\nand o.hierarchy like concat('${currentUserHierarchy}', '%')");
        }
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            insert_m_permission(named, m_permission, "report", "READ_GroupSummaryDetails", "GroupSummaryDetails", "READ", 0);
        }
    }

    protected void insert_stretchy_report(NamedParameterJdbcTemplate named, Table table, String report_name, String report_type, long core_report, long use_report, String description, String report_sql) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("report_name").getName(), report_name);
        insertQuery.addValue(table.getColumnByName("report_type").getName(), report_type);
        insertQuery.addValue(table.getColumnByName("core_report").getName(), core_report);
        insertQuery.addValue(table.getColumnByName("use_report").getName(), use_report);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        insertQuery.addValue(table.getColumnByName("report_sql").getName(), report_sql);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getQualifiedLabel(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        insertQuery.addValue(table.getColumnByName("can_maker_checker").getName(), can_maker_checker);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
