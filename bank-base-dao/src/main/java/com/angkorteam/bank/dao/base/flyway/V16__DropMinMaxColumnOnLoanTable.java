package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V16__DropMinMaxColumnOnLoanTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V16__drop_min_max_column_on_loan_table.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V16__drop_min_max_column_on_loan_table.xml");
    }

}
