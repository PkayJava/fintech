package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V67__LoansInAdvanceTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V67__loans_in_advance_table.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            updateLiquibase(database, "V67__loans_in_advance_table.xml");
        }
    }

}
