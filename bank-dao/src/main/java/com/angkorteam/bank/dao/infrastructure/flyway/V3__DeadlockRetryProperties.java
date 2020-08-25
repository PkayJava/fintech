package com.angkorteam.bank.dao.infrastructure.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flywaydb.core.api.migration.Context;

public class V3__DeadlockRetryProperties extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V3__deadlock-retry-properties.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            Liquibase liquibase = new Liquibase("V3__deadlock-retry-properties.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

}