package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V103__ClusterSupportForBatchJobs extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V103__ClusterSupportForBatchJobs + getInternalChecksum("V103__cluster_support_for_batch_jobs.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V103__cluster_support_for_batch_jobs.xml");
        Table scheduler_detail = dataContext.getDefaultSchema().getTableByName("scheduler_detail");
        insert_scheduler_detail(named, scheduler_detail, false, true, true);
    }

    protected void insert_scheduler_detail(NamedParameterJdbcTemplate named, Table table, boolean is_suspended, boolean execute_misfired_jobs, boolean reset_scheduler_on_bootup) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("is_suspended").getName(), is_suspended);
        insertQuery.addValue(table.getColumnByName("execute_misfired_jobs").getName(), execute_misfired_jobs);
        insertQuery.addValue(table.getColumnByName("reset_scheduler_on_bootup").getName(), reset_scheduler_on_bootup);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}