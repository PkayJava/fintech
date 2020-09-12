package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V39__PaymentChannelsUpdates extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V39__PaymentChannelsUpdates + getInternalChecksum("V39__payment-channels-updates-002.xml", "V39__payment-channels-updates-004.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
            UpdateQuery updateQuery = null;
            DeleteQuery deleteQuery = null;
            {
                // sub change 001
                dataContext.refreshSchemas();
                Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
                insert_m_code(named, m_code, "PaymentType", 1);

                dataContext.refreshSchemas();
                Table m_loan_transaction = dataContext.getDefaultSchema().getTableByName("m_loan_transaction");
                updateQuery = new UpdateQuery(m_loan_transaction.getName());
                updateQuery.addValue(m_loan_transaction.getColumnByName("payment_detail_id").getName() + " = NULL");
                named.update(updateQuery.toSQL(), updateQuery.toParam());
            }
            {
                // sub change 002
                updateLiquibase(database, "V39__payment-channels-updates-002.xml");
            }
            {
                // sub change 003
                dataContext.refreshSchemas();
                Table m_payment_detail = dataContext.getDefaultSchema().getTableByName("m_payment_detail");
                deleteQuery = new DeleteQuery(m_payment_detail.getName());
                named.update(deleteQuery.toSQL(), deleteQuery.toParam());
            }
            {
                // sub change 004
                updateLiquibase(database, "V39__payment-channels-updates-004.xml");
            }
        }
    }

    protected void insert_m_code(NamedParameterJdbcTemplate named, Table table, String code_name, long is_system_defined) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_name").getName(), code_name);
        insertQuery.addValue(table.getColumnByName("is_system_defined").getName(), is_system_defined);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
