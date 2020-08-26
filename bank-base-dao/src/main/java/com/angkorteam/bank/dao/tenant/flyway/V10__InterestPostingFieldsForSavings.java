package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.tenant.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V10__InterestPostingFieldsForSavings extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V10__InterestPostingFieldsForSavings + getInternalChecksum("V10__interest-posting-fields-for-savings-002.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            // sub change 001
            Table m_permission = schema.getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "transaction_savings", "POSTINTEREST_SAVINGSACCOUNT", "SAVINGSACCOUNT", "POSTINTEREST", 1);
                insert_m_permission(m_permission, callback, "transaction_savings", "POSTINTEREST_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "POSTINTEREST", 0);
            });
        }

        {
            // sub change 002
            updateLiquibase(database, "V10__interest-posting-fields-for-savings-002.xml");
        }

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
