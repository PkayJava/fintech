package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V8__DepositTransactionPermissionsIfTheyExist extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V8__DepositTransactionPermissionsIfTheyExist;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                callback.deleteFrom(m_permission)
                        .where(m_permission.getColumnByName("grouping")).eq("transaction_deposit")
                        .execute();
            });
        }
    }

}
