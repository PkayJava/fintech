package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.tenant.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V2__ExternalizeConnectionProperties extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V2__ExternalizeConnectionProperties + getInternalChecksum("V2__externalize-connection-properties.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcDataContext jdbcDataContext) throws Exception {
        updateLiquibase("V2__externalize-connection-properties.xml");
    }

}