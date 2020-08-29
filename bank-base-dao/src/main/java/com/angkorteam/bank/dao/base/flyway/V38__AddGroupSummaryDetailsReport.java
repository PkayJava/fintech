package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V38__AddGroupSummaryDetailsReport extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V38__AddGroupSummaryDetailsReport;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
            dataContext.executeUpdate(callback -> {
                insert_stretchy_report(stretchy_report, callback, "GroupSummaryDetails", "Table", 1, 0, "Utility query for getting group summary details for a group_id", "/*\nActive Client is a client linked to the 'group' via m_group_client\nand with an active status_enum (not sure what it means if the group is inactive)\n\nActive Borrowers - Borrow may be a client or a 'group'\n\nCurrency is an outstanding issue\n\nCouldn't set up group savings for a client\n(as opposed to opening a individual clients savings account)\n*/\nselect x.*\nfrom m_office o,\nm_group g,\n\n(select a.activeClients,\n(b.activeClientLoans + c.activeGroupLoans) as activeLoans,\nb.activeClientLoans, c.activeGroupLoans,\n(b.activeClientBorrowers + c.activeGroupBorrowers) as activeBorrowers,\nb.activeClientBorrowers, c.activeGroupBorrowers, d.*,\n0 as activeSavings, 0 as totalSavingsAmount\nfrom\n(select count(*) as activeClients\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_group_client gc on gc.group_id = g.id\njoin m_client c on c.id = gc.client_id\nwhere topgroup.id = ${groupId}\nand c.status_enum = 300) a,\n\n(select count(*) as activeClientLoans,\ncount(distinct(l.client_id)) as activeClientBorrowers\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id and l.client_id is not null\nwhere topgroup.id = ${groupId}\nand l.loan_status_id = 300) b,\n\n(select count(*) as activeGroupLoans,\ncount(distinct(l.group_id)) as activeGroupBorrowers\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id and l.client_id is null\nwhere topgroup.id = ${groupId}\nand l.loan_status_id = 300) c,\n\n(select ifnull(sum(l.principal_disbursed_derived),0) as totalDisbursedAmount,\nifnull(sum(l.principal_outstanding_derived),0) as totalLoanOutstandingAmount,\ncount(laa.loan_id) as overdueLoans, ifnull(sum(laa.total_overdue_derived), 0) as totalLoanOverdueAmount\nfrom m_group topgroup\njoin m_group g on g.hierarchy like concat(topgroup.hierarchy, '%')\njoin m_loan l on l.group_id = g.id\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\nwhere topgroup.id = ${groupId}\nand l.disbursedon_date is not null) d) x\n\nwhere g.id = ${groupId}\nand o.id = g.office_id\nand o.hierarchy like concat('${currentUserHierarchy}', '%')");
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "report", "READ_GroupSummaryDetails", "GroupSummaryDetails", "READ", 0);
            });
        }
    }

    protected void insert_stretchy_report(Table table, RowInsertable callback, String report_name, String report_type, long core_report, long use_report, String description, String report_sql) {
        callback.insertInto(table)
                .value(table.getColumnByName("report_name"), report_name)
                .value(table.getColumnByName("report_type"), report_type)
                .value(table.getColumnByName("core_report"), core_report)
                .value(table.getColumnByName("use_report"), use_report)
                .value(table.getColumnByName("description"), description)
                .value(table.getColumnByName("report_sql"), report_sql)
                .execute();
    }

    protected void insert_m_permission(Table table, RowInsertable callback, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        callback.insertInto(table)
                .value(table.getColumnByName("grouping"), grouping)
                .value(table.getColumnByName("code"), code)
                .value(table.getColumnByName("entity_name"), entity_name)
                .value(table.getColumnByName("action_name"), action_name)
                .value(table.getColumnByName("can_maker_checker"), can_maker_checker)
                .execute();
    }

}
