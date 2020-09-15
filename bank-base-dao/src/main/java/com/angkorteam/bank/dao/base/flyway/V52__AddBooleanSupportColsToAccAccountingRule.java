package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V52__AddBooleanSupportColsToAccAccountingRule extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V52__add_boolean_support_cols_to_acc_accounting_rule.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V52__add_boolean_support_cols_to_acc_accounting_rule.xml");
    }

}
