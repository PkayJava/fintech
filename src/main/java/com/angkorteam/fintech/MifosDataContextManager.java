package com.angkorteam.fintech;

import com.angkorteam.fintech.meta.TenantServerConnection;
import com.angkorteam.fintech.meta.Tenant;
import com.google.common.collect.Maps;
import com.mysql.cj.jdbc.Driver;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

public class MifosDataContextManager implements InitializingBean {

    private String mifosUrl;

    private Map<String, DataContext> dataSources = Maps.newHashMap();

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

    public DataContext getDataContext(String identifier) {
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
                String jdbcUrl = "jdbc:mysql://" + connectionObject.getValue(tenantServerConnections.SCHEMA_SERVER) + ":" + connectionObject.getValue(tenantServerConnections.SCHEMA_SERVER_PORT) + "/" + connectionObject.getValue(tenantServerConnections.SCHEMA_NAME) + "?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=true&serverTimezone=UTC";

                DataContextPropertiesImpl configuration = new DataContextPropertiesImpl();
                configuration.put("type", "jdbc");
                configuration.put("url", jdbcUrl);
                configuration.put("driver-class", Driver.class.getName());
                configuration.put("username", (String) connectionObject.getValue(tenantServerConnections.SCHEMA_USERNAME));
                configuration.put("password", (String) connectionObject.getValue(tenantServerConnections.SCHEMA_PASSWORD));
                DataContext dataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(configuration);

                this.dataSources.put(identifier, dataContext);
            }
        }
        return this.dataSources.get(identifier);
    }

}
