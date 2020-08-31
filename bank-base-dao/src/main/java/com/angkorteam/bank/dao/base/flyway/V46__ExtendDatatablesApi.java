package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V46__ExtendDatatablesApi extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V46__ExtendDatatablesApi;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "configuration", "CREATE_DATATABLE", "DATATABLE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_DATATABLE_CHECKER", "DATATABLE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_DATATABLE", "DATATABLE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_DATATABLE_CHECKER", "DATATABLE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_DATATABLE", "DATATABLE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_DATATABLE_CHECKER", "DATATABLE", "DELETE", 0);
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