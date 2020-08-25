package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
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
            Liquibase liquibase = new Liquibase("V1__mifosplatform-core-ddl-latest-001.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 002
            Liquibase liquibase = new Liquibase("V1__mifosplatform-core-ddl-latest-002.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 003
            Liquibase liquibase = new Liquibase("V1__mifosplatform-core-ddl-latest-003.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 004
            Liquibase liquibase = new Liquibase("V1__mifosplatform-core-ddl-latest-004.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}