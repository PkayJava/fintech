package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V1__MifosPlatformCoreDdlLatest extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V1__mifosplatform-core-ddl-latest-001.xml",
                "V1__mifosplatform-core-ddl-latest-002.xml",
                "V1__mifosplatform-core-ddl-latest-003.xml",
                "V1__mifosplatform-core-ddl-latest-004.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            // sub change 001
            updateLiquibase(database, "V1__mifosplatform-core-ddl-latest-001.xml");
        }
        {
            // sub change 002
            updateLiquibase(database, "V1__mifosplatform-core-ddl-latest-002.xml");
        }
        {
            // sub change 003
            updateLiquibase(database, "V1__mifosplatform-core-ddl-latest-003.xml");
        }
        {
            // sub change 004
            updateLiquibase(database, "V1__mifosplatform-core-ddl-latest-004.xml");
        }
    }
}