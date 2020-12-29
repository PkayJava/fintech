package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.tenant.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V3__DeadlockRetryProperties extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V3__DeadlockRetryProperties + getInternalChecksum("V3__deadlock-retry-properties.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcDataContext jdbcDataContext) throws Exception {
        updateLiquibase("V3__deadlock-retry-properties.xml");
    }

}