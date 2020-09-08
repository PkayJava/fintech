package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;

import java.util.ArrayList;
import java.util.List;

public class V1__MifosPlatformCoreDdlLatest extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V1__MifosPlatformCoreDdlLatest + getInternalChecksum("V1__mifosplatform-core-ddl-latest-002.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            {
                // sub change 001
                List<String> tables = new ArrayList<>();
                for (String table : dataContext.getDefaultSchema().getTableNames()) {
                    tables.add(table);
                }
                for (String table : tables) {
                    if (!"DATABASECHANGELOG".equals(table) && !"DATABASECHANGELOGLOCK".equals(table) && !"flyway_schema_history".equals(table)) {
                        dataContext.executeUpdate(callback -> {
                            callback.dropTable(table);
                        });
                    }
                }
            }

            {
                // sub change 002
                updateLiquibase(database, "V1__mifosplatform-core-ddl-latest-002.xml");
            }
        }
    }
}