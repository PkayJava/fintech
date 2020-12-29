package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.tenant.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V5__AddSchemaConnectionParameters extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V5__AddSchemaConnectionParameters + getInternalChecksum("V5__add_schema_connection_parameters.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V5__add_schema_connection_parameters.xml");

        dataContext.refreshSchemas();
        Table tenant_server_connections = dataContext.getDefaultSchema().getTableByName("tenant_server_connections");
        UpdateQuery updateQuery = new UpdateQuery(tenant_server_connections.getName());
        updateQuery.addValue(tenant_server_connections.getColumnByName("schema_connection_parameters").getName() + " = :schema_connection_parameters", "");
        updateQuery.addWhere(tenant_server_connections.getColumnByName("id").getName() + " = :id", 1);
    }
}
