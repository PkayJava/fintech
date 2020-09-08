package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flywaydb.core.api.migration.Context;

public class V3__DeadlockRetryProperties extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V3__deadlock-retry-properties.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            Liquibase liquibase = new Liquibase("V3__deadlock-retry-properties.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

}