package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;

public class V105__TrackLoanTransactionAgainstOffice extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V105__TrackLoanTransactionAgainstOffice + getInternalChecksum("V105_1__track_loan_transaction_against_office.xml", "V105_2__track_loan_transaction_against_office.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V105_1__track_loan_transaction_against_office.xml");

        dataContext.refreshSchemas();
        Table m_loan_transaction = dataContext.getDefaultSchema().getTableByName("m_loan_transaction");
        Table m_client = dataContext.getDefaultSchema().getTableByName("m_client");
        Table m_group = dataContext.getDefaultSchema().getTableByName("m_group");
        Table m_loan = dataContext.getDefaultSchema().getTableByName("m_loan");

        {
            String s = "SELECT " + m_loan_transaction.getColumnByName("office_id").getQualifiedLabel() + " FROM " + m_loan.getName() + " JOIN " + m_client.getName() + " ON " + m_loan.getColumnByName("client_id").getQualifiedLabel() + " = " + m_client.getColumnByName("id").getQualifiedLabel() + " WHERE " + m_loan.getColumnByName("id").getQualifiedLabel() + " = " + m_loan_transaction.getColumnByName("loan_id").getQualifiedLabel();
            String s1 = "SELECT " + m_loan.getColumnByName("id").getQualifiedLabel() + " FROM " + m_loan.getName() + " WHERE " + m_loan.getColumnByName("client_id").getQualifiedLabel() + " IS NOT NULL";
            named.update("UPDATE " + m_loan_transaction.getName() + " SET " + m_loan_transaction.getColumnByName("office_id").getQualifiedLabel() + " = (" + s + ") WHERE " + m_loan_transaction.getColumnByName("loan_id").getQualifiedLabel() + " NOT IN (" + s1 + ")", Collections.emptyMap());
        }

        {
            String s = "SELECT " + m_loan_transaction.getColumnByName("office_id").getQualifiedLabel() + " FROM " + m_loan.getName() + " JOIN " + m_group.getName() + " ON " + m_loan.getColumnByName("group_id").getQualifiedLabel() + " = " + m_group.getColumnByName("id").getQualifiedLabel() + " WHERE " + m_loan.getColumnByName("id").getQualifiedLabel() + " = " + m_loan_transaction.getColumnByName("loan_id").getQualifiedLabel();
            String s1 = "SELECT " + m_loan.getColumnByName("id").getQualifiedLabel() + " FROM " + m_loan.getName() + " WHERE " + m_loan.getColumnByName("group_id").getQualifiedLabel() + " IS NOT NULL";
            named.update("UPDATE " + m_loan_transaction.getName() + " SET " + m_loan_transaction.getColumnByName("office_id").getQualifiedLabel() + " = (" + s + ") WHERE " + m_loan_transaction.getColumnByName("loan_id").getQualifiedLabel() + " NOT IN (" + s1 + ")", Collections.emptyMap());
        }

        updateLiquibase("V105_2__track_loan_transaction_against_office.xml");
    }

}