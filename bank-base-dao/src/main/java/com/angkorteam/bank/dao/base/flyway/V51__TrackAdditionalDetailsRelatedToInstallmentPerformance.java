package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V51__TrackAdditionalDetailsRelatedToInstallmentPerformance extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V51__track-additional-details-related-to-installment-performance.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V51__track-additional-details-related-to-installment-performance.xml");
        }
    }

}
