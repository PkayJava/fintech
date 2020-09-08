package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

public class V4__IntroducedOltpIdReportIdColumnsAndTenantsServerConnectionTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-001.xml",
                "V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-003.xml",
                "V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-005.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
            SelectQuery selectQuery = null;
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
                selectQuery = new SelectQuery(temp_tenants.getName());
                for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                    String id = (String) record.get(temp_tenants.getColumnByName("id").getName());
                    String schema_name = (String) record.get(temp_tenants.getColumnByName("schema_name").getName());
                    String schema_server = (String) record.get(temp_tenants.getColumnByName("schema_server").getName());
                    String schema_server_port = (String) record.get(temp_tenants.getColumnByName("schema_server_port").getName());
                    String schema_username = (String) record.get(temp_tenants.getColumnByName("schema_username").getName());
                    String schema_password = (String) record.get(temp_tenants.getColumnByName("schema_password").getName());
                    String auto_update = (String) record.get(temp_tenants.getColumnByName("auto_update").getName());
                    String pool_initial_size = (String) record.get(temp_tenants.getColumnByName("pool_initial_size").getName());
                    String pool_validation_interval = (String) record.get(temp_tenants.getColumnByName("pool_validation_interval").getName());
                    String pool_remove_abandoned = (String) record.get(temp_tenants.getColumnByName("pool_remove_abandoned").getName());
                    String pool_remove_abandoned_timeout = (String) record.get(temp_tenants.getColumnByName("pool_remove_abandoned_timeout").getName());
                    String pool_log_abandoned = (String) record.get(temp_tenants.getColumnByName("pool_log_abandoned").getName());
                    String pool_abandon_when_percentage_full = (String) record.get(temp_tenants.getColumnByName("pool_abandon_when_percentage_full").getName());
                    String pool_test_on_borrow = (String) record.get(temp_tenants.getColumnByName("pool_test_on_borrow").getName());
                    String pool_max_active = (String) record.get(temp_tenants.getColumnByName("pool_max_active").getName());
                    String pool_min_idle = (String) record.get(temp_tenants.getColumnByName("pool_min_idle").getName());
                    String pool_max_idle = (String) record.get(temp_tenants.getColumnByName("pool_max_idle").getName());
                    String pool_suspect_timeout = (String) record.get(temp_tenants.getColumnByName("pool_suspect_timeout").getName());
                    String pool_time_between_eviction_runs_millis = (String) record.get(temp_tenants.getColumnByName("pool_time_between_eviction_runs_millis").getName());
                    String pool_min_evictable_idle_time_millis = (String) record.get(temp_tenants.getColumnByName("pool_min_evictable_idle_time_millis").getName());
                    String deadlock_max_retries = (String) record.get(temp_tenants.getColumnByName("deadlock_max_retries").getName());
                    String deadlock_max_retry_interval = (String) record.get(temp_tenants.getColumnByName("deadlock_max_retry_interval").getName());
                    insert_tenant_server_connections(named, tenant_server_connections, id, schema_name, schema_server, schema_server_port, schema_username, schema_password, auto_update, pool_initial_size, pool_validation_interval, pool_remove_abandoned, pool_remove_abandoned_timeout, pool_log_abandoned, pool_abandon_when_percentage_full, pool_test_on_borrow, pool_max_active, pool_min_idle, pool_max_idle, pool_suspect_timeout, pool_time_between_eviction_runs_millis, pool_min_evictable_idle_time_millis, deadlock_max_retries, deadlock_max_retry_interval);
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
                selectQuery = new SelectQuery(temp_tenants.getName());
                for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                    String id = (String) record.get(temp_tenants.getColumnByName("id").getName());
                    String identifier = (String) record.get(temp_tenants.getColumnByName("identifier").getName());
                    String name = (String) record.get(temp_tenants.getColumnByName("name").getName());
                    String timezone_id = (String) record.get(temp_tenants.getColumnByName("timezone_id").getName());
                    String country_id = (String) record.get(temp_tenants.getColumnByName("country_id").getName());
                    String joined_date = (String) record.get(temp_tenants.getColumnByName("joined_date").getName());
                    String created_date = (String) record.get(temp_tenants.getColumnByName("created_date").getName());
                    String lastmodified_date = (String) record.get(temp_tenants.getColumnByName("lastmodified_date").getName());
                    String oltp_id = (String) record.get(temp_tenants.getColumnByName("id").getName());
                    String report_id = (String) record.get(temp_tenants.getColumnByName("id").getName());
                    insert_tenants(named, tenants, id, identifier, name, timezone_id, country_id, joined_date, created_date, lastmodified_date, oltp_id, report_id);
                }
            }
            {
                // sub change 005
                Liquibase liquibase = new Liquibase("V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table-005.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.update(new Contexts(), new LabelExpression());
            }
        }
    }

    protected static void insert_tenants(NamedParameterJdbcTemplate named, Table table, String id, String identifier, String name, String timezone_id, String country_id, String joined_date, String created_date, String lastmodified_date, String oltp_id, String report_id) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName() + " = :id", id);
        insertQuery.addValue(table.getColumnByName("identifier").getName() + " = :identifier", identifier);
        insertQuery.addValue(table.getColumnByName("name").getName() + " = :name", name);
        insertQuery.addValue(table.getColumnByName("timezone_id").getName() + " = :timezone_id", timezone_id);
        insertQuery.addValue(table.getColumnByName("country_id").getName() + " = :country_id", country_id);
        insertQuery.addValue(table.getColumnByName("joined_date").getName() + " = :joined_date", joined_date);
        insertQuery.addValue(table.getColumnByName("created_date").getName() + " = :created_date", created_date);
        insertQuery.addValue(table.getColumnByName("lastmodified_date").getName() + " = :lastmodified_date", lastmodified_date);
        insertQuery.addValue(table.getColumnByName("oltp_id").getName() + " = :oltp_id", oltp_id);
        insertQuery.addValue(table.getColumnByName("report_id").getName() + " = :report_id", report_id);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected static void insert_tenant_server_connections(NamedParameterJdbcTemplate named, Table table, String id, String schema_name, String schema_server, String schema_server_port, String schema_username, String schema_password, String auto_update, String pool_initial_size, String pool_validation_interval, String pool_remove_abandoned, String pool_remove_abandoned_timeout, String pool_log_abandoned, String pool_abandon_when_percentage_full, String pool_test_on_borrow, String pool_max_active, String pool_min_idle, String pool_max_idle, String pool_suspect_timeout, String pool_time_between_eviction_runs_millis, String pool_min_evictable_idle_time_millis, String deadlock_max_retries, String deadlock_max_retry_interval) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName() + " = :id", id);
        insertQuery.addValue(table.getColumnByName("schema_name").getName() + " = :schema_name", schema_name);
        insertQuery.addValue(table.getColumnByName("schema_server").getName() + " = :schema_server", schema_server);
        insertQuery.addValue(table.getColumnByName("schema_server_port").getName() + " = :schema_server_port", schema_server_port);
        insertQuery.addValue(table.getColumnByName("schema_username").getName() + " = :schema_username", schema_username);
        insertQuery.addValue(table.getColumnByName("schema_password").getName() + " = :schema_password", schema_password);
        insertQuery.addValue(table.getColumnByName("auto_update").getName() + " = :auto_update", auto_update);
        insertQuery.addValue(table.getColumnByName("pool_initial_size").getName() + " = :pool_initial_size", pool_initial_size);
        insertQuery.addValue(table.getColumnByName("pool_validation_interval").getName() + " = :pool_validation_interval", pool_validation_interval);
        insertQuery.addValue(table.getColumnByName("pool_remove_abandoned").getName() + " = :pool_remove_abandoned", pool_remove_abandoned);
        insertQuery.addValue(table.getColumnByName("pool_remove_abandoned_timeout").getName() + " = :pool_remove_abandoned_timeout", pool_remove_abandoned_timeout);
        insertQuery.addValue(table.getColumnByName("pool_log_abandoned").getName() + " = :pool_log_abandoned", pool_log_abandoned);
        insertQuery.addValue(table.getColumnByName("pool_abandon_when_percentage_full").getName() + " = :pool_abandon_when_percentage_full", pool_abandon_when_percentage_full);
        insertQuery.addValue(table.getColumnByName("pool_test_on_borrow").getName() + " = :pool_test_on_borrow", pool_test_on_borrow);
        insertQuery.addValue(table.getColumnByName("pool_max_active").getName() + " = :pool_max_active", pool_max_active);
        insertQuery.addValue(table.getColumnByName("pool_min_idle").getName() + " = :pool_min_idle", pool_min_idle);
        insertQuery.addValue(table.getColumnByName("pool_max_idle").getName() + " = :pool_max_idle", pool_max_idle);
        insertQuery.addValue(table.getColumnByName("pool_suspect_timeout").getName() + " = :pool_suspect_timeout", pool_suspect_timeout);
        insertQuery.addValue(table.getColumnByName("pool_time_between_eviction_runs_millis").getName() + " = :pool_time_between_eviction_runs_millis", pool_time_between_eviction_runs_millis);
        insertQuery.addValue(table.getColumnByName("pool_min_evictable_idle_time_millis").getName() + " = :pool_min_evictable_idle_time_millis", pool_min_evictable_idle_time_millis);
        insertQuery.addValue(table.getColumnByName("deadlock_max_retries").getName() + " = :deadlock_max_retries", deadlock_max_retries);
        insertQuery.addValue(table.getColumnByName("deadlock_max_retry_interval").getName() + " = :deadlock_max_retry_interval", deadlock_max_retry_interval);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}