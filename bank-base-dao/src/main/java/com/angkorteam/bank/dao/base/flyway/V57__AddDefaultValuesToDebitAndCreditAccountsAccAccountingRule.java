package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V57__AddDefaultValuesToDebitAndCreditAccountsAccAccountingRule extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V57__add_default_values_to_debit_and_credit_accounts_acc_accounting_rule.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V57__add_default_values_to_debit_and_credit_accounts_acc_accounting_rule.xml");
    }

}
