package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V19__ReportMaintenancePermissions extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V19__ReportMaintenancePermissions;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "report", "READ_REPORT", "REPORT", "READ", 0);
                insert_m_permission(m_permission, callback, "report", "CREATE_REPORT", "REPORT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "report", "CREATE_REPORT_CHECKER", "REPORT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "report", "UPDATE_REPORT", "REPORT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "report", "UPDATE_REPORT_CHECKER", "REPORT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "report", "DELETE_REPORT", "REPORT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "report", "DELETE_REPORT_CHECKER", "REPORT", "UPDATE", 0);
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