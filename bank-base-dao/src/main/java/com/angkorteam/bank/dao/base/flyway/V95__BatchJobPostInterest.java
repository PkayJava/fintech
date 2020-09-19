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

public class V95__BatchJobPostInterest extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V95__BatchJobPostInterest;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        dataContext.refreshSchemas();
        Table job = dataContext.getDefaultSchema().getTableByName("job");
        insert_job(named, job, "Post Interest For Savings", "Post Interest For Savings", "0 0 0 1/1 * ? *", new Date(), 5, null, null, null, null, null, 1, 0, 1, 0);
    }

    protected void insert_job(NamedParameterJdbcTemplate named, Table table, String name, String display_name, String cron_expression, Date create_time, long task_priority, String group_name, Date previous_run_start_time, Date next_run_time, String job_key, String initializing_errorlog, long is_active, long currently_running, long updates_allowed, long scheduler_group) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("display_name").getName(), display_name);
        insertQuery.addValue(table.getColumnByName("cron_expression").getName(), cron_expression);
        insertQuery.addValue(table.getColumnByName("create_time").getName(), create_time);
        insertQuery.addValue(table.getColumnByName("task_priority").getName(), task_priority);
        insertQuery.addValue(table.getColumnByName("group_name").getName(), group_name);
        insertQuery.addValue(table.getColumnByName("previous_run_start_time").getName(), previous_run_start_time);
        insertQuery.addValue(table.getColumnByName("next_run_time").getName(), next_run_time);
        insertQuery.addValue(table.getColumnByName("job_key").getName(), job_key);
        insertQuery.addValue(table.getColumnByName("initializing_errorlog").getName(), initializing_errorlog);
        insertQuery.addValue(table.getColumnByName("is_active").getName(), is_active);
        insertQuery.addValue(table.getColumnByName("currently_running").getName(), currently_running);
        insertQuery.addValue(table.getColumnByName("updates_allowed").getName(), updates_allowed);
        insertQuery.addValue(table.getColumnByName("scheduler_group").getName(), scheduler_group);


        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }
}