package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V53__TrackAdvanceAndLatePaymentsOnInstallment extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V53__track-advance-and-late-payments-on-installment.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V53__track-advance-and-late-payments-on-installment.xml");
        }
    }

}
