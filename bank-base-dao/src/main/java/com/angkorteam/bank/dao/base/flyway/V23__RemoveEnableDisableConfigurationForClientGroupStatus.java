package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V23__RemoveEnableDisableConfigurationForClientGroupStatus extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V23__RemoveEnableDisableConfigurationForClientGroupStatus + getInternalChecksum("V23__remove-enable-disable-configuration-for-client-group-status-002.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        DeleteQuery deleteQuery = null;
        {
            // sub change 001
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            deleteQuery = new DeleteQuery(c_configuration.getName());
            deleteQuery.addWhere(c_configuration.getColumnByName("name").getName() + " = :name", "allow-pending-client-status");
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(c_configuration.getName());
            deleteQuery.addWhere(c_configuration.getColumnByName("name").getName() + " = :name", "allow-pending-group-status");
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
        {
            // sub change 002
            updateLiquibase("V23__remove-enable-disable-configuration-for-client-group-status-002.xml");
        }
    }

}
