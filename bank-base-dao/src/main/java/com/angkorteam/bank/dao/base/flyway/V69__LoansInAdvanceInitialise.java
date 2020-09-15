package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class V69__LoansInAdvanceInitialise extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V69__LoansInAdvanceInitialise;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        DeleteQuery deleteQuery = null;
        SelectQuery selectQuery = null;

        Table m_loan_paid_in_advance = dataContext.getDefaultSchema().getTableByName("m_loan_paid_in_advance");
        deleteQuery = new DeleteQuery(m_loan_paid_in_advance.getName());
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        Table m_loan = dataContext.getDefaultSchema().getTableByName("m_loan");
        Table m_loan_repayment_schedule = dataContext.getDefaultSchema().getTableByName("m_loan_repayment_schedule");

        selectQuery = new SelectQuery(m_loan.getName());
        selectQuery.addJoin("INNER JOIN " + m_loan_repayment_schedule.getName() + " ON " + m_loan.getColumnByName("id").getQualifiedLabel() + " = " + m_loan_repayment_schedule.getColumnByName("loan_id").getQualifiedLabel());
        selectQuery.addWhere(m_loan.getColumnByName("loan_status_id").getQualifiedLabel() + " = :loan_status_id", 300);
        selectQuery.addWhere(m_loan_repayment_schedule.getColumnByName("duedate").getQualifiedLabel() + " >= :duedate", DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date()));
        selectQuery.addGroupBy(m_loan.getColumnByName("id").getQualifiedLabel());
        List<String> havings = new ArrayList<>();
        havings.add("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("principal_completed_derived").getQualifiedLabel() + ",0))");
        havings.add("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("interest_completed_derived").getQualifiedLabel() + ",0))");
        havings.add("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("fee_charges_completed_derived").getQualifiedLabel() + ",0))");
        havings.add("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("penalty_charges_completed_derived").getQualifiedLabel() + ",0))");
        selectQuery.addHaving("(" + StringUtils.join(havings, " + ") + ") > 0.0");
        selectQuery.addField(m_loan.getColumnByName("id").getQualifiedLabel() + " AS loanId");
        selectQuery.addField("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("principal_completed_derived").getQualifiedLabel() + ",0)) AS principal_in_advance_derived");
        selectQuery.addField("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("interest_completed_derived").getQualifiedLabel() + ",0)) AS interest_in_advance_derived");
        selectQuery.addField("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("fee_charges_completed_derived").getQualifiedLabel() + ",0)) AS fee_charges_in_advance_derived");
        selectQuery.addField("SUM(IFNULL(" + m_loan_repayment_schedule.getColumnByName("penalty_charges_completed_derived").getQualifiedLabel() + ",0)) AS penalty_charges_in_advance_derived");
        selectQuery.addField("(" + StringUtils.join(havings, " + ") + ") AS total_in_advance_derived");

        List<Map<String, Object>> records = named.queryForList(selectQuery.toSQL(), selectQuery.toParam());
        for (Map<String, Object> record : records) {
            InsertQuery insertQuery = new InsertQuery(m_loan_paid_in_advance.getName());
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("loan_id").getName(), (Number) record.get("loanId"));
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("principal_in_advance_derived").getName(), (Number) record.get("principal_in_advance_derived"));
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("interest_in_advance_derived").getName(), (Number) record.get("interest_in_advance_derived"));
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("fee_charges_in_advance_derived").getName(), (Number) record.get("fee_charges_in_advance_derived"));
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("penalty_charges_in_advance_derived").getName(), (Number) record.get("penalty_charges_in_advance_derived"));
            insertQuery.addValue(m_loan_paid_in_advance.getColumnByName("total_in_advance_derived").getName(), (Number) record.get("total_in_advance_derived"));
            named.update(insertQuery.toSQL(), insertQuery.toParam());
        }
    }

}
