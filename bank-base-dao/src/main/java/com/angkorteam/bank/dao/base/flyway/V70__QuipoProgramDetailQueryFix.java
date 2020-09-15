package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V70__QuipoProgramDetailQueryFix extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V70__QuipoProgramDetailQueryFix;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        UpdateQuery updateQuery = null;
        dataContext.refreshSchemas();
        Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
        updateQuery = new UpdateQuery(stretchy_report.getName());
        updateQuery.addValue(stretchy_report.getColumnByName("report_sql").getName(), "\n select l.id as loanId, l.account_no as loanAccountNo, c.id as clientId, c.account_no as clientAccountNo,\n pgm.display_name as programName,\n\n(select count(*)\nfrom m_loan cy\nwhere cy.group_id = pgm.id and cy.client_id =c.id\nand cy.disbursedon_date <= l.disbursedon_date) as loanCycleNo,\n\nc.display_name as clientDisplayName,\n ifnull(cur.display_symbol, l.currency_code) as Currency,\nifnull(l.principal_repaid_derived,0.0) as loanRepaidAmount,\nifnull(l.principal_outstanding_derived, 0.0) as loanOutstandingAmount,\nifnull(lpa.principal_in_advance_derived,0.0) as LoanPaidInAdvance,\n\nifnull(laa.principal_overdue_derived, 0.0) as loanInArrearsAmount,\nif(ifnull(laa.principal_overdue_derived, 0.00) > 0, 'Yes', 'No') as inDefault,\n\nif(date_sub(curdate(), interval 28 day) > ifnull(laa.overdue_since_date_derived, curdate()),\n        l.principal_outstanding_derived,0)  as portfolioAtRisk\n\n from m_group pgm\n join m_office o on o.id = pgm.office_id\n            and o.hierarchy like concat('${currentUserHierarchy}', '%')\n join m_loan l on l.group_id = pgm.id and l.client_id is not null\n left join m_currency cur on cur.code = l.currency_code\n join m_client c on c.id = l.client_id\n left join m_loan_arrears_aging laa on laa.loan_id = l.id\n left join m_loan_paid_in_advance lpa on lpa.loan_id = l.id\n where pgm.id = ${programId}\n and l.loan_status_id = 300\norder by c.display_name, l.account_no\n\n");
        updateQuery.addWhere(stretchy_report.getColumnByName("report_name").getName() + " = :name", "ProgramDetails");
    }

}
