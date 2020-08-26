package com.angkorteam.bank.dao.infrastructure.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V4__IntroducedOltpIdReportIdColumnsAndTenantsServerConnectionTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-001.xml",
                "V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-003.xml",
                "V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-005.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            Liquibase liquibase = new Liquibase("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-001.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 002
            dataContext.refreshSchemas();
            Table tenant_server_connections = dataContext.getDefaultSchema().getTableByName("tenant_server_connections");
            Table temp_tenants = dataContext.getDefaultSchema().getTableByName("temp_tenants");
            try (DataSet rows = dataContext.query().from(temp_tenants).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(updateCallback -> {
                        updateCallback.insertInto(tenant_server_connections)
                                .value(tenant_server_connections.getColumnByName("id"), rows.getRow().getValue(temp_tenants.getColumnByName("id")))
                                .value(tenant_server_connections.getColumnByName("schema_name"), rows.getRow().getValue(temp_tenants.getColumnByName("schema_name")))
                                .value(tenant_server_connections.getColumnByName("schema_server"), rows.getRow().getValue(temp_tenants.getColumnByName("schema_server")))
                                .value(tenant_server_connections.getColumnByName("schema_server_port"), rows.getRow().getValue(temp_tenants.getColumnByName("schema_server_port")))
                                .value(tenant_server_connections.getColumnByName("schema_username"), rows.getRow().getValue(temp_tenants.getColumnByName("schema_username")))
                                .value(tenant_server_connections.getColumnByName("schema_password"), rows.getRow().getValue(temp_tenants.getColumnByName("schema_password")))
                                .value(tenant_server_connections.getColumnByName("auto_update"), rows.getRow().getValue(temp_tenants.getColumnByName("auto_update")))
                                .value(tenant_server_connections.getColumnByName("pool_initial_size"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_initial_size")))
                                .value(tenant_server_connections.getColumnByName("pool_validation_interval"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_validation_interval")))
                                .value(tenant_server_connections.getColumnByName("pool_remove_abandoned"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_remove_abandoned")))
                                .value(tenant_server_connections.getColumnByName("pool_remove_abandoned_timeout"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_remove_abandoned_timeout")))
                                .value(tenant_server_connections.getColumnByName("pool_log_abandoned"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_log_abandoned")))
                                .value(tenant_server_connections.getColumnByName("pool_abandon_when_percentage_full"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_abandon_when_percentage_full")))
                                .value(tenant_server_connections.getColumnByName("pool_test_on_borrow"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_test_on_borrow")))
                                .value(tenant_server_connections.getColumnByName("pool_max_active"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_max_active")))
                                .value(tenant_server_connections.getColumnByName("pool_min_idle"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_min_idle")))
                                .value(tenant_server_connections.getColumnByName("pool_max_idle"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_max_idle")))
                                .value(tenant_server_connections.getColumnByName("pool_suspect_timeout"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_suspect_timeout")))
                                .value(tenant_server_connections.getColumnByName("pool_time_between_eviction_runs_millis"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_time_between_eviction_runs_millis")))
                                .value(tenant_server_connections.getColumnByName("pool_min_evictable_idle_time_millis"), rows.getRow().getValue(temp_tenants.getColumnByName("pool_min_evictable_idle_time_millis")))
                                .value(tenant_server_connections.getColumnByName("deadlock_max_retries"), rows.getRow().getValue(temp_tenants.getColumnByName("deadlock_max_retries")))
                                .value(tenant_server_connections.getColumnByName("deadlock_max_retry_interval"), rows.getRow().getValue(temp_tenants.getColumnByName("deadlock_max_retry_interval")))
                                .execute();
                    });
                }
            }
        }
        {
            // sub change 003
            Liquibase liquibase = new Liquibase("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-003.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 004
            dataContext.refreshSchemas();
            Table tenants = dataContext.getDefaultSchema().getTableByName("tenants");
            Table temp_tenants = dataContext.getDefaultSchema().getTableByName("temp_tenants");
            try (DataSet rows = dataContext.query().from(temp_tenants).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(updateCallback -> {
                        updateCallback.insertInto(tenants)
                                .value(tenants.getColumnByName("id"), rows.getRow().getValue(temp_tenants.getColumnByName("id")))
                                .value(tenants.getColumnByName("identifier"), rows.getRow().getValue(temp_tenants.getColumnByName("identifier")))
                                .value(tenants.getColumnByName("name"), rows.getRow().getValue(temp_tenants.getColumnByName("name")))
                                .value(tenants.getColumnByName("timezone_id"), rows.getRow().getValue(temp_tenants.getColumnByName("timezone_id")))
                                .value(tenants.getColumnByName("country_id"), rows.getRow().getValue(temp_tenants.getColumnByName("country_id")))
                                .value(tenants.getColumnByName("joined_date"), rows.getRow().getValue(temp_tenants.getColumnByName("joined_date")))
                                .value(tenants.getColumnByName("created_date"), rows.getRow().getValue(temp_tenants.getColumnByName("created_date")))
                                .value(tenants.getColumnByName("lastmodified_date"), rows.getRow().getValue(temp_tenants.getColumnByName("lastmodified_date")))
                                .value(tenants.getColumnByName("oltp_id"), rows.getRow().getValue(temp_tenants.getColumnByName("id")))
                                .value(tenants.getColumnByName("report_id"), rows.getRow().getValue(temp_tenants.getColumnByName("id")))
                                .execute();
                    });
                }
            }
        }
        {
            // sub change 005
            Liquibase liquibase = new Liquibase("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-005.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

}