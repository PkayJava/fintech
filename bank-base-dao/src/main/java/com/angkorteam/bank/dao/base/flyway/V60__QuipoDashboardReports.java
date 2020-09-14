package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;

public class V60__QuipoDashboardReports extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V60__QuipoDashboardReports;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        NamedParameterJdbcTemplate named = lookJdbcTemplate(context);

        DeleteQuery deleteQuery = null;

        Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
        deleteQuery = new DeleteQuery(stretchy_report.getName());
        deleteQuery.addWhere(stretchy_report.getColumnByName("report_name").getName() + " in (:report_name)", "report_name", String.class,
                Arrays.asList("FieldAgentStats", "FieldAgentPrograms", "ProgramDetails", "ChildrenStaffList", "CoordinatorStats", "BranchManagerStats", "ProgramDirectorStats", "ProgramStats"));
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
        deleteQuery = new DeleteQuery(m_permission.getName());
        deleteQuery.addWhere(m_permission.getColumnByName("entity_name").getName() + " in (:entity_name)", "entity_name", String.class,
                Arrays.asList("FieldAgentStats", "FieldAgentPrograms", "ProgramDetails", "ChildrenStaffList", "CoordinatorStats", "BranchManagerStats", "ProgramDirectorStats", "ProgramStats"));

        dataContext.refreshSchemas();
        insert_stretchy_report(named, stretchy_report, "FieldAgentStats", "Table", "Quipo", false, false, "Field Agent Statistics", "\nselect ifnull(cur.display_symbol, l.currency_code) as Currency,\n/*This query will return more than one entry if more than one currency is used */\ncount(distinct(c.id)) as activeClients, count(*) as activeLoans,\nsum(l.principal_disbursed_derived) as disbursedAmount,\nsum(l.principal_outstanding_derived) as loanOutstandingAmount,\nround((sum(l.principal_outstanding_derived) * 100) /  sum(l.principal_disbursed_derived),2) as loanOutstandingPC,\n'???' as loanOverPaymentAmount,\nsum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) as portfolioAtRisk,\n\nround((sum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) * 100) / sum(l.principal_outstanding_derived), 2) as portfolioAtRiskPC,\n\ncount(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) as clientsInDefault,\nround((count(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) * 100) / count(distinct(c.id)),2) as clientsInDefaultPC,\n(sum(l.principal_disbursed_derived) / count(*))  as averageLoanAmount\nfrom m_staff fa\njoin m_office o on o.id = fa.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_group pgm on pgm.staff_id = fa.id\njoin m_loan l on l.group_id = pgm.id and l.client_id is not null\nleft join m_currency cur on cur.code = l.currency_code\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\njoin m_client c on c.id = l.client_id\nwhere fa.id = ${staffId}\nand l.loan_status_id = 300\ngroup  by l.currency_code\n");
        insert_stretchy_report(named, stretchy_report, "FieldAgentPrograms", "Table", "Quipo", false, false, "List of Field Agent Programs", "\nselect pgm.id, pgm.display_name as `name`, sts.enum_message_property as status\n from m_group pgm\n join m_office o on o.id = pgm.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\n left join r_enum_value sts on sts.enum_name = 'status_enum' and sts.enum_id = pgm.status_enum\n where pgm.staff_id = ${staffId}\n");
        insert_stretchy_report(named, stretchy_report, "ProgramDetails", "Table", "Quipo", false, false, "List of Loans in a Program", "\n select l.id as loanId, l.account_no as loanAccountNo, c.id as clientId, c.account_no as clientAccountNo,\n pgm.display_name as programName,\n\n(select count(*)\nfrom m_loan cy\nwhere cy.group_id = pgm.id and cy.client_id =c.id\nand cy.disbursedon_date <= l.disbursedon_date) as loanCycleNo,\n\nc.display_name as clientDisplayName,\n ifnull(cur.display_symbol, l.currency_code) as Currency,\nifnull(l.principal_repaid_derived,0.0) as loanRepaidAmount,\nifnull(l.principal_outstanding_derived, 0.0) as loanOutstandingAmount,\n '???' as loanInAdvanceAmount,\n\nifnull(laa.principal_overdue_derived, 0.0) as loanInArrearsAmount,\nif(ifnull(laa.principal_overdue_derived, 0.00) > 0, 'Yes', 'No') as inDefault,\n\nif(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)  as portfolioAtRisk\n\n from m_group pgm\n join m_office o on o.id = pgm.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\n join m_loan l on l.group_id = pgm.id and l.client_id is not null\n left join m_currency cur on cur.code = l.currency_code\n join m_client c on c.id = l.client_id\n left join m_loan_arrears_aging laa on laa.loan_id = l.id\n where pgm.id = ${programId}\n and l.loan_status_id = 300\norder by c.display_name, l.account_no\n\n");
        insert_stretchy_report(named, stretchy_report, "ChildrenStaffList", "Table", "Quipo", false, false, "Get Next Level Down Staff", "\n select s.id, s.display_name,\ns.firstname, s.lastname, s.organisational_role_enum,\ns.organisational_role_parent_staff_id,\nsp.display_name as `organisational_role_parent_staff_display_name`\nfrom m_staff s\njoin m_staff sp on s.organisational_role_parent_staff_id = sp.id\nwhere s.organisational_role_parent_staff_id = ${staffId}\n");
        insert_stretchy_report(named, stretchy_report, "CoordinatorStats", "Table", "Quipo", false, false, "Coordinator Statistics", "\nselect ifnull(cur.display_symbol, l.currency_code) as Currency,\n/*This query will return more than one entry if more than one currency is used */\ncount(distinct(c.id)) as activeClients, count(*) as activeLoans,\nsum(l.principal_disbursed_derived) as disbursedAmount,\nsum(l.principal_outstanding_derived) as loanOutstandingAmount,\nround((sum(l.principal_outstanding_derived) * 100) /  sum(l.principal_disbursed_derived),2) as loanOutstandingPC,\n'???' as loanOverPaymentAmount,\nsum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) as portfolioAtRisk,\n\nround((sum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) * 100) / sum(l.principal_outstanding_derived), 2) as portfolioAtRiskPC,\n\ncount(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) as clientsInDefault,\nround((count(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) * 100) / count(distinct(c.id)),2) as clientsInDefaultPC,\n(sum(l.principal_disbursed_derived) / count(*))  as averageLoanAmount\nfrom m_staff coord\njoin m_staff fa on fa.organisational_role_parent_staff_id = coord.id\njoin m_office o on o.id = fa.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_group pgm on pgm.staff_id = fa.id\njoin m_loan l on l.group_id = pgm.id and l.client_id is not null\nleft join m_currency cur on cur.code = l.currency_code\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\njoin m_client c on c.id = l.client_id\nwhere coord.id = ${staffId}\nand l.loan_status_id = 300\ngroup  by l.currency_code\n");
        insert_stretchy_report(named, stretchy_report, "BranchManagerStats", "Table", "Quipo", false, false, "Branch Manager Statistics", "\nselect ifnull(cur.display_symbol, l.currency_code) as Currency,\n/*This query will return more than one entry if more than one currency is used */\ncount(distinct(c.id)) as activeClients, count(*) as activeLoans,\nsum(l.principal_disbursed_derived) as disbursedAmount,\nsum(l.principal_outstanding_derived) as loanOutstandingAmount,\nround((sum(l.principal_outstanding_derived) * 100) /  sum(l.principal_disbursed_derived),2) as loanOutstandingPC,\n'???' as loanOverPaymentAmount,\nsum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) as portfolioAtRisk,\n\nround((sum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) * 100) / sum(l.principal_outstanding_derived), 2) as portfolioAtRiskPC,\n\ncount(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) as clientsInDefault,\nround((count(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) * 100) / count(distinct(c.id)),2) as clientsInDefaultPC,\n(sum(l.principal_disbursed_derived) / count(*))  as averageLoanAmount\nfrom m_staff bm\njoin m_staff coord on coord.organisational_role_parent_staff_id = bm.id\njoin m_staff fa on fa.organisational_role_parent_staff_id = coord.id\njoin m_office o on o.id = fa.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_group pgm on pgm.staff_id = fa.id\njoin m_loan l on l.group_id = pgm.id and l.client_id is not null\nleft join m_currency cur on cur.code = l.currency_code\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\njoin m_client c on c.id = l.client_id\nwhere bm.id = ${staffId}\nand l.loan_status_id = 300\ngroup  by l.currency_code\n");
        insert_stretchy_report(named, stretchy_report, "ProgramDirectorStats", "Table", "Quipo", false, false, "Program DirectorStatistics", "\nselect ifnull(cur.display_symbol, l.currency_code) as Currency,\n/*This query will return more than one entry if more than one currency is used */\ncount(distinct(c.id)) as activeClients, count(*) as activeLoans,\nsum(l.principal_disbursed_derived) as disbursedAmount,\nsum(l.principal_outstanding_derived) as loanOutstandingAmount,\nround((sum(l.principal_outstanding_derived) * 100) /  sum(l.principal_disbursed_derived),2) as loanOutstandingPC,\n'???' as loanOverPaymentAmount,\nsum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) as portfolioAtRisk,\n\nround((sum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) * 100) / sum(l.principal_outstanding_derived), 2) as portfolioAtRiskPC,\n\ncount(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) as clientsInDefault,\nround((count(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) * 100) / count(distinct(c.id)),2) as clientsInDefaultPC,\n(sum(l.principal_disbursed_derived) / count(*))  as averageLoanAmount\nfrom m_staff pd\njoin m_staff bm on bm.organisational_role_parent_staff_id = pd.id\njoin m_staff coord on coord.organisational_role_parent_staff_id = bm.id\njoin m_staff fa on fa.organisational_role_parent_staff_id = coord.id\njoin m_office o on o.id = fa.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_group pgm on pgm.staff_id = fa.id\njoin m_loan l on l.group_id = pgm.id and l.client_id is not null\nleft join m_currency cur on cur.code = l.currency_code\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\njoin m_client c on c.id = l.client_id\nwhere pd.id = ${staffId}\nand l.loan_status_id = 300\ngroup  by l.currency_code\n");
        insert_stretchy_report(named, stretchy_report, "ProgramStats", "Table", "Quipo", false, false, "Program Statistics", "\nselect ifnull(cur.display_symbol, l.currency_code) as Currency,\n/*This query will return more than one entry if more than one currency is used */\ncount(distinct(c.id)) as activeClients, count(*) as activeLoans,\nsum(l.principal_disbursed_derived) as disbursedAmount,\nsum(l.principal_outstanding_derived) as loanOutstandingAmount,\nround((sum(l.principal_outstanding_derived) * 100) /  sum(l.principal_disbursed_derived),2) as loanOutstandingPC,\n'???' as loanOverPaymentAmount,\nsum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) as portfolioAtRisk,\n\nround((sum(\n    if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)) * 100) / sum(l.principal_outstanding_derived), 2) as portfolioAtRiskPC,\n\ncount(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) as clientsInDefault,\nround((count(distinct(\n        if(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n            c.id,null))) * 100) / count(distinct(c.id)),2) as clientsInDefaultPC,\n(sum(l.principal_disbursed_derived) / count(*))  as averageLoanAmount\nfrom m_group pgm\njoin m_office o on o.id = pgm.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\njoin m_loan l on l.group_id = pgm.id and l.client_id is not null\nleft join m_currency cur on cur.code = l.currency_code\nleft join m_loan_arrears_aging laa on laa.loan_id = l.id\njoin m_client c on c.id = l.client_id\nwhere pgm.id = ${programId}\nand l.loan_status_id = 300\ngroup  by l.currency_code\n");

        dataContext.refreshSchemas();
        insert_m_permission(named, m_permission, "report", "READ_FieldAgentStats", "FieldAgentStats", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_FieldAgentPrograms", "FieldAgentPrograms", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_ProgramDetails", "ProgramDetails", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_ChildrenStaffList", "ChildrenStaffList", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_CoordinatorStats", "CoordinatorStats", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_BranchManagerStats", "BranchManagerStats", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_ProgramDirectorStats", "ProgramDirectorStats", "READ", 0);
        insert_m_permission(named, m_permission, "report", "READ_ProgramStats", "ProgramStats", "READ", 0);
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
