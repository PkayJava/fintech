package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V22__AlterGroupForConsistencyAddPermissions extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V22__AlterGroupForConsistencyAddPermissions + getInternalChecksum("V22__alter-group-for-consistency-add-permissions-002.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "portfolio", "ACTIVATE_CENTER", "CENTER", "ACTIVATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "ACTIVATE_CENTER_CHECKER", "CENTER", "ACTIVATE", 0);

                insert_m_permission(m_permission, callback, "portfolio", "ACTIVATE_GROUP", "GROUP", "ACTIVATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "ACTIVATE_GROUP_CHECKER", "GROUP", "ACTIVATE", 0);
            });
        }
        {
            // sub change 002
            updateLiquibase(database, "V22__alter-group-for-consistency-add-permissions-002.xml");
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