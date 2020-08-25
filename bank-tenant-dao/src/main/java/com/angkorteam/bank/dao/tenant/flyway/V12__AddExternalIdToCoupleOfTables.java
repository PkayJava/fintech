package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V12__AddExternalIdToCoupleOfTables extends LiquibaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V12__add_external_id_to_couple_of_tables.xml");
        }
    }

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V12__add_external_id_to_couple_of_tables.xml");
    }
}
