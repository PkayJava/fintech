package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V6__AddMinMaxPrincipalColumnToLoan extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V6__add_min_max_principal_column_to_loan.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            // sub change 001
            updateLiquibase(database, "V6__add_min_max_principal_column_to_loan.xml");
        }
    }

}

