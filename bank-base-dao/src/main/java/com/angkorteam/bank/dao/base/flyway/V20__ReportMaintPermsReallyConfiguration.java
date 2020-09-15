package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V20__ReportMaintPermsReallyConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V20__ReportMaintPermsReallyConfiguration;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            UpdateQuery updateQuery = new UpdateQuery(m_permission.getName());
            updateQuery.addValue(m_permission.getColumnByName("grouping").getQualifiedLabel(), "configuration");
            updateQuery.addWhere(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "report");
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

}
