package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V7__RemoveReadMakerCheckerPermission extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V7__RemoveReadMakerCheckerPermission;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            Table m_role_permission = dataContext.getDefaultSchema().getTableByName("m_role_permission");
            long permission_id = lookup_m_permission(dataContext, "READ_MAKERCHECKER");
            dataContext.executeUpdate(callback -> {
                callback.deleteFrom(m_role_permission)
                        .where(m_role_permission.getColumnByName("permission_id")).eq(permission_id)
                        .execute();
                callback.deleteFrom(m_permission)
                        .where(m_permission.getColumnByName("id")).eq(permission_id)
                        .execute();
            });
        }
    }

    protected long lookup_m_permission(DataContext dataContext, String code) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_permission");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("code")).eq(code).execute()) {
            rows.next();
            return (long) rows.getRow().getValue(0);
        }
    }

}

