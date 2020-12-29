package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V80__ScheduleJobsTablesUpdates extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V80__ScheduleJobsTablesUpdates + getInternalChecksum("V80__schedule_jobs_tables_updates.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V80__schedule_jobs_tables_updates.xml");
    }

}