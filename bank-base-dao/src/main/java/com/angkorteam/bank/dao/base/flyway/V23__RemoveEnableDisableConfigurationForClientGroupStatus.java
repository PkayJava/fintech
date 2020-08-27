package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V23__RemoveEnableDisableConfigurationForClientGroupStatus extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V23__RemoveEnableDisableConfigurationForClientGroupStatus + getInternalChecksum("V23__remove-enable-disable-configuration-for-client-group-status-002.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            dataContext.executeUpdate(callback -> {
                callback.deleteFrom(c_configuration).where(c_configuration.getColumnByName("name")).eq("allow-pending-client-status").execute();
                callback.deleteFrom(c_configuration).where(c_configuration.getColumnByName("name")).eq("allow-pending-group-status").execute();
            });
        }
        {
            // sub change 002
            updateLiquibase(database, "V23__remove-enable-disable-configuration-for-client-group-status-002.xml");
        }
    }

}
