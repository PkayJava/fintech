package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V57__AddDefaultValuesToDebitAndCreditAccountsAccAccountingRule extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V57__add_default_values_to_debit_and_credit_accounts_acc_accounting_rule.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            updateLiquibase(database, "V57__add_default_values_to_debit_and_credit_accounts_acc_accounting_rule.xml");
        }
    }

}
