package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class TenantServerConnection extends AbstractTable {

    public final Column ID;

    public final Column SCHEMA_SERVER;

    public final Column SCHEMA_NAME;

    public final Column SCHEMA_SERVER_PORT;

    public final Column SCHEMA_USERNAME;

    public final Column SCHEMA_PASSWORD;

    public final Column AUTO_UPDATE;

    public final Column POOL_INITIAL_SIZE;

    public final Column POOL_VALIDATION_INTERVAL;

    public final Column POOL_REMOVE_ABANDONED;

    public final Column POOL_REMOVE_ABANDONED_TIMEOUT;

    public final Column POOL_LOG_ABANDONED;

    public final Column POOL_ABANDON_WHEN_PERCENTAGE_FULL;

    public final Column POOL_TEST_ON_BORROW;

    public final Column POOL_MAX_ACTIVE;

    public final Column POOL_MIN_IDLE;

    public final Column POOL_MAX_IDLE;

    public final Column POOL_SUSPECT_TIMEOUT;

    public final Column POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    public final Column POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    public final Column DEADLOCK_MAX_RETRY;

    public final Column DEADLOCK_MAX_RETRY_INTERVAL;

    public static TenantServerConnection staticInitialize(DataContext dataContext) {
        return new TenantServerConnection(dataContext);
    }

    private TenantServerConnection(DataContext dataContext) {
        super(dataContext, "tenant_server_connections");
        this.ID = this.delegate.getColumnByName("id");
        this.SCHEMA_SERVER = this.delegate.getColumnByName("schema_server");
        this.SCHEMA_NAME = this.delegate.getColumnByName("schema_name");
        this.SCHEMA_SERVER_PORT = this.delegate.getColumnByName("schema_server_port");
        this.SCHEMA_USERNAME = this.delegate.getColumnByName("schema_username");
        this.SCHEMA_PASSWORD = this.delegate.getColumnByName("schema_password");
        this.AUTO_UPDATE = this.delegate.getColumnByName("auto_update");
        this.POOL_INITIAL_SIZE = this.delegate.getColumnByName("pool_initial_size");
        this.POOL_VALIDATION_INTERVAL = this.delegate.getColumnByName("pool_validation_interval");
        this.POOL_REMOVE_ABANDONED = this.delegate.getColumnByName("pool_remove_abandoned");
        this.POOL_REMOVE_ABANDONED_TIMEOUT = this.delegate.getColumnByName("pool_remove_abandoned_timeout");
        this.POOL_LOG_ABANDONED = this.delegate.getColumnByName("pool_log_abandoned");
        this.POOL_ABANDON_WHEN_PERCENTAGE_FULL = this.delegate.getColumnByName("pool_abandon_when_percentage_full");
        this.POOL_TEST_ON_BORROW = this.delegate.getColumnByName("pool_test_on_borrow");
        this.POOL_MAX_ACTIVE = this.delegate.getColumnByName("pool_max_active");
        this.POOL_MIN_IDLE = this.delegate.getColumnByName("pool_min_idle");
        this.POOL_MAX_IDLE = this.delegate.getColumnByName("pool_max_idle");
        this.POOL_SUSPECT_TIMEOUT = this.delegate.getColumnByName("pool_suspect_timeout");
        this.POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS = this.delegate.getColumnByName("pool_time_between_eviction_runs_millis");
        this.POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS = this.delegate.getColumnByName("pool_min_evictable_idle_time_millis");
        this.DEADLOCK_MAX_RETRY = this.delegate.getColumnByName("deadlock_max_retries");
        this.DEADLOCK_MAX_RETRY_INTERVAL = this.delegate.getColumnByName("deadlock_max_retry_interval");
    }

}
