package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V12__AddExternalIdToCoupleOfTables extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V12__add_external_id_to_couple_of_tables.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V12__add_external_id_to_couple_of_tables.xml");
    }
}
