package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V14__RenameStatusIdToEnum extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V14__RenameStatusIdToEnum + getInternalChecksum("V14__rename_status_id_to_enum-001.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            updateLiquibase(database, "V14__rename_status_id_to_enum-001.xml");
        }
        {
            Table r_enum_value = schema.getTableByName("r_enum_value");
            dataContext.executeUpdate(callback -> {
                callback.update(r_enum_value)
                        .value(r_enum_value.getColumnByName("enum_name"), "status_enum")
                        .where(r_enum_value.getColumnByName("enum_name")).eq("status_id")
                        .execute();
            });
        }
    }


}
