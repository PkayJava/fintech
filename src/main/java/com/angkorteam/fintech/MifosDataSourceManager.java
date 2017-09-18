package com.angkorteam.fintech;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Maps;

public class MifosDataSourceManager implements DisposableBean, InitializingBean {

    private String mifosUrl;

    private Map<String, BasicDataSource> dataSources = Maps.newHashMap();

    private DataSource delegate;

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

    public void setDelegate(DataSource delegate) {
        this.delegate = delegate;
    }

    public DataSource getDataSource(String identifier) {
        if (!this.dataSources.containsKey(identifier)) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(this.delegate);
            Map<String, Object> tenantObject = jdbcTemplate.queryForMap("select * from tenants where identifier = ?", identifier);
            Map<String, Object> connectionObject = jdbcTemplate.queryForMap("select * from tenant_server_connections where id = ?", tenantObject.get("id"));
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername((String) connectionObject.get("schema_username"));
            dataSource.setPassword((String) connectionObject.get("schema_password"));
            dataSource.setMaxIdle(((Number) connectionObject.get("pool_max_idle")).intValue());
            dataSource.setMinIdle(((Number) connectionObject.get("pool_min_idle")).intValue());
            dataSource.setMaxWaitMillis(5000);
            dataSource.setMaxTotal(200);
            dataSource.setInitialSize(30);
            String jdbcUrl = "jdbc:mysql://" + connectionObject.get("schema_server") + ":" + String.valueOf(connectionObject.get("schema_server_port")) + "/" + connectionObject.get("schema_name") + "?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&serverTimezone=UTC";
            dataSource.setUrl(jdbcUrl);
            this.dataSources.put(identifier, dataSource);
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
