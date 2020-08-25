package com.angkorteam.fintech;

import com.angkorteam.fintech.meta.infrastructure.TenantServerConnection;
import com.angkorteam.fintech.meta.infrastructure.Tenant;
import com.google.common.collect.Maps;
import com.mysql.cj.jdbc.Driver;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

public class MifosDataSourceManager implements DisposableBean, InitializingBean {

    private String mifosUrl;

    private Map<String, BasicDataSource> dataSources = Maps.newHashMap();

    private DataContext delegate;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.delegate == null) {
            throw new NullPointerException("delegate is null");
        }
        if (this.mifosUrl == null || "".equals(this.mifosUrl)) {
            throw new NullPointerException("mifos url is null or empty");
        }
    }

    public String getMifosUrl() {
        return mifosUrl;
    }

    public void setMifosUrl(String mifosUrl) {
        this.mifosUrl = mifosUrl;
    }

    public void setDelegate(DataContext delegate) {
        this.delegate = delegate;
    }

    public DataSource getDataSource(String identifier) {
        if (!this.dataSources.containsKey(identifier)) {
            Tenant tenants = Tenant.staticInitialize(this.delegate);
            TenantServerConnection tenantServerConnections = TenantServerConnection.staticInitialize(this.delegate);
            Row tenantObject = null;
            try (DataSet tenantDataSet = this.delegate.query().from(tenants).selectAll().where(tenants.IDENTIFIER).eq(identifier).execute()) {
                tenantDataSet.next();
                tenantObject = tenantDataSet.getRow();
            }

            try (DataSet connectionDataSet = this.delegate.query().from(tenantServerConnections).selectAll().where(tenantServerConnections.ID).eq(tenantObject.getValue(tenants.ID)).execute()) {
                connectionDataSet.next();
                Row connectionObject = connectionDataSet.getRow();
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setDriverClassName(Driver.class.getName());
                dataSource.setUsername((String) connectionObject.getValue(tenantServerConnections.SCHEMA_USERNAME));
                dataSource.setPassword((String) connectionObject.getValue(tenantServerConnections.SCHEMA_PASSWORD));
                dataSource.setMaxIdle(((Number) connectionObject.getValue(tenantServerConnections.POOL_MAX_IDLE)).intValue());
                dataSource.setMinIdle(((Number) connectionObject.getValue(tenantServerConnections.POOL_MIN_IDLE)).intValue());
                dataSource.setInitialSize(((Number) connectionObject.getValue(tenantServerConnections.POOL_INITIAL_SIZE)).intValue());
                dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                dataSource.setDefaultAutoCommit(false);
                String jdbcUrl = "jdbc:mysql://" + connectionObject.getValue(tenantServerConnections.SCHEMA_SERVER) + ":" + connectionObject.getValue(tenantServerConnections.SCHEMA_SERVER_PORT) + "/" + connectionObject.getValue(tenantServerConnections.SCHEMA_NAME) + "?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=true&serverTimezone=UTC";
                dataSource.setUrl(jdbcUrl);
                this.dataSources.put(identifier, dataSource);
            }
        }
        return this.dataSources.get(identifier);
    }

    @Override
    public void destroy() throws Exception {
        for (BasicDataSource dataSource : this.dataSources.values()) {
            dataSource.close();
        }
    }

}
