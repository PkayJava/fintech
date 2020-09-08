package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V52__AddBooleanSupportColsToAccAccountingRule extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V52__add_boolean_support_cols_to_acc_accounting_rule.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V52__add_boolean_support_cols_to_acc_accounting_rule.xml");
        }
    }

}
