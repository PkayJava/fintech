package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

/**
 * TODO
 */
public class V39__PaymentChannelsUpdates extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V39__PaymentChannelsUpdates + getInternalChecksum("V39__payment-channels-updates-002.xml", "V39__payment-channels-updates-004.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            dataContext.refreshSchemas();
            Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
            dataContext.executeUpdate(callback -> {
                insert_m_code(m_code, callback, "PaymentType", 1);
            });

            dataContext.refreshSchemas();
            Table m_loan_transaction = dataContext.getDefaultSchema().getTableByName("m_loan_transaction");
            dataContext.executeUpdate(callback -> {
                callback.update(m_loan_transaction)
                        .value(m_loan_transaction.getColumnByName("payment_detail_id"), null)
                        .execute();
            });
        }
        {
            // sub change 002
            updateLiquibase(database, "V39__payment-channels-updates-002.xml");
        }
        {
            // sub change 003
            dataContext.refreshSchemas();
            Table m_payment_detail = dataContext.getDefaultSchema().getTableByName("m_payment_detail");
            dataContext.executeUpdate(callback -> {
                callback.deleteFrom(m_payment_detail)
                        .execute();
            });
        }
        {
            // sub change 004
            updateLiquibase(database, "V39__payment-channels-updates-004.xml");
        }
    }

    protected void insert_m_code(Table table, RowInsertable callback, String code_name, long is_system_defined) {
        callback.insertInto(table)
                .value(table.getColumnByName("code_name"), code_name)
                .value(table.getColumnByName("is_system_defined"), is_system_defined)
                .execute();
    }

}
