package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flywaydb.core.api.migration.Context;

public class V6__AddMinMaxPrincipalColumnToLoan extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V6__add_min_max_principal_column_to_loan.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            // sub change 001
            Liquibase liquibase = new Liquibase("V6__add_min_max_principal_column_to_loan.xml", new ClassLoaderResourceAccessor(), database);
            try {
                liquibase.update(new Contexts(), new LabelExpression());
            } catch (Exception e) {
                Throwable throwable = e;
                while (throwable != null) {
                    if (throwable instanceof DatabaseException) {
                        break;
                    } else {
                        throwable = throwable.getCause();
                    }
                }
                if (throwable != null) {
                    System.out.println(throwable.getMessage());
                }
                throw e;
            }
        }
    }

}

