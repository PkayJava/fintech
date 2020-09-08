package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V9__AddMinMaxConstraintColumnToLoanLoanProduct extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V9__add_min_max_constraint_column_to_loan_loanproduct.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            updateLiquibase(database, "V9__add_min_max_constraint_column_to_loan_loanproduct.xml");
        }
    }

}

