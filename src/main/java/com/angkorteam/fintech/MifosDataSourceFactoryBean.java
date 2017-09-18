package com.angkorteam.fintech;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.angkorteam.framework.HttpServletRequestDataSource;
import com.angkorteam.framework.spring.HttpServletRequestDataSourceFactoryBean;

public class MifosDataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletRequestDataSourceFactoryBean.class);

    private MifosDataSourceManager manager;

    private DataSource dataSource;

    @Autowired
    private HttpSession session;

    @Override
    public DataSource getObject() throws Exception {
        return this.dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setManager(MifosDataSourceManager manager) {
        this.manager = manager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.manager == null) {
            throw new Exception("manager is required");
        }
        String identifier = (String) this.session.getAttribute("mifos_identifier");
        if (identifier != null && !"".equals(identifier)) {
            this.dataSource = new HttpServletRequestDataSource(this.manager.getDataSource(identifier));
        } else {
            throw new RuntimeException("could not find datasource for " + identifier);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.dataSource != null && this.dataSource instanceof HttpServletRequestDataSource) {
            try (Connection connection = this.dataSource.getConnection()) {
                if (connection != null) {
                    try {
                        connection.commit();
                    } catch (SQLException e) {
                        LOGGER.info("could not commit transaction due to this reason {}", e.getMessage());
                        connection.rollback();
                    }
                }
            }
        }
    }
}
