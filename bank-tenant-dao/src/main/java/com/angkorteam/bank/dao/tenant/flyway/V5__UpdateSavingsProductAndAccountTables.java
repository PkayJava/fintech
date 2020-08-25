package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
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
            Liquibase liquibase = new Liquibase("V5__update-savings-product-and-account-tables.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

}

