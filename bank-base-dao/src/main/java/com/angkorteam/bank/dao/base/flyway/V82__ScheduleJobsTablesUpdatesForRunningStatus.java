package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V82__ScheduleJobsTablesUpdatesForRunningStatus extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V82__ScheduleJobsTablesUpdatesForRunningStatus + getInternalChecksum("V82__schedule_jobs_tables_updates_for_running_status.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V82__schedule_jobs_tables_updates_for_running_status.xml");
    }

}