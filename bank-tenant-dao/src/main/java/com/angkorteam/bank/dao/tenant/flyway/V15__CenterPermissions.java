package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V15__CenterPermissions extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V15__CenterPermissions;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            Table m_permission = schema.getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "portfolio", "READ_CENTER", "CENTER", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CENTER", "CENTER", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CENTER_CHECKER", "CENTER", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CENTER", "CENTER", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CENTER_CHECKER", "CENTER", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CENTER", "CENTER", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CENTER_CHECKER", "CENTER", "DELETE", 0);
            });
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
