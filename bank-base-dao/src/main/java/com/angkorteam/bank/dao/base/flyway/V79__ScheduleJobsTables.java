package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;

public class V79__ScheduleJobsTables extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V79__ScheduleJobsTables + getInternalChecksum("V79__schedule_jobs_tables.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V79__schedule_jobs_tables.xml");

        dataContext.refreshSchemas();

        Table scheduled_jobs = dataContext.getDefaultSchema().getTableByName("scheduled_jobs");
        insert_scheduled_jobs(named, scheduled_jobs, "Update loan Summary", "Update loan Summary", "0 0 22 1/1 * ? *", new Date(), 5, null, null, null, null, null, 1);
        insert_scheduled_jobs(named, scheduled_jobs, "Update Loan Arrears Ageing", "Update Loan Arrears Ageing", "0 1 0 1/1 * ? *", new Date(), 5, null, null, null, null, null, 1);
        insert_scheduled_jobs(named, scheduled_jobs, "Update Loan Paid In Advance", "Update Loan Paid In Advance", "0 5 0 1/1 * ? *", new Date(), 5, null, null, null, null, null, 1);
        insert_scheduled_jobs(named, scheduled_jobs, "Apply Annual Fee For Savings", "Apply Annual Fee For Savings", "0 20 22 1/1 * ? *", new Date(), 5, null, null, null, null, null, 1);
        insert_scheduled_jobs(named, scheduled_jobs, "Apply Holidays To Loans", "Apply Holidays To Loans", "0 0 12 * * ?", new Date(), 5, null, null, null, null, null, 1);
    }

    protected void insert_scheduled_jobs(NamedParameterJdbcTemplate named, Table table, String job_name, String job_display_name, String cron_expression, Date create_time, long task_priority, String group_name, Date previous_run_start_time, Date next_run_time, String trigger_key, String job_initializing_errorlog, long is_active) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("job_name").getName(), job_name);
        insertQuery.addValue(table.getColumnByName("job_display_name").getName(), job_display_name);
        insertQuery.addValue(table.getColumnByName("cron_expression").getName(), cron_expression);
        insertQuery.addValue(table.getColumnByName("create_time").getName(), create_time);
        insertQuery.addValue(table.getColumnByName("task_priority").getName(), task_priority);
        insertQuery.addValue(table.getColumnByName("group_name").getName(), group_name);
        insertQuery.addValue(table.getColumnByName("previous_run_start_time").getName(), previous_run_start_time);
        insertQuery.addValue(table.getColumnByName("next_run_time").getName(), next_run_time);
        insertQuery.addValue(table.getColumnByName("trigger_key").getName(), trigger_key);
        insertQuery.addValue(table.getColumnByName("job_initializing_errorlog").getName(), job_initializing_errorlog);
        insertQuery.addValue(table.getColumnByName("is_active").getName(), is_active);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}