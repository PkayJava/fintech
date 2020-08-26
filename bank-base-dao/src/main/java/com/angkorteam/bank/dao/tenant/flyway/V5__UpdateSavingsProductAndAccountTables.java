package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V5__UpdateSavingsProductAndAccountTables extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V5__update-savings-product-and-account-tables.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            // sub change 001
            updateLiquibase(database, "V5__update-savings-product-and-account-tables.xml");
        }
    }

}

