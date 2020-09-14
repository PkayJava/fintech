package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V63__AddSyncDisbursementWithMeetingColumnToLoan extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V63__add_sync_disbursement_with_meeting_column_to_loan.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            updateLiquibase(database, "V63__add_sync_disbursement_with_meeting_column_to_loan.xml");
        }
    }

}
