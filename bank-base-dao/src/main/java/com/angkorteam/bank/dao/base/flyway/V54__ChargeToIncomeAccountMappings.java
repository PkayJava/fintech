package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V54__ChargeToIncomeAccountMappings extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V54__charge-to-income-account-mappings.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V54__charge-to-income-account-mappings.xml");
        }
    }

}
