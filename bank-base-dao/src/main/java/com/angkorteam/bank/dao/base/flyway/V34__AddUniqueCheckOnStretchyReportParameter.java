package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V34__AddUniqueCheckOnStretchyReportParameter extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V34__add_unique_check_on_stretchy_report_parameter.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            updateLiquibase(database, "V34__add_unique_check_on_stretchy_report_parameter.xml");
        }
    }

}
