package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V37__AddCenterGroupCollectionSheetPermissions extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V37__AddCenterGroupCollectionSheetPermissions;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "portfolio", "SAVECOLLECTIONSHEET_GROUP", "GROUP", "SAVECOLLECTIONSHEET", 0);
                insert_m_permission(m_permission, callback, "portfolio", "SAVECOLLECTIONSHEET_CENTER", "CENTER", "SAVECOLLECTIONSHEET", 0);
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
