package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V16__DropMinMaxColumnOnLoanTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V16__drop_min_max_column_on_loan_table.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V16__drop_min_max_column_on_loan_table.xml");
        }
    }

}
