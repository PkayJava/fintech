package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V20__ReportMaintPermsReallyConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V20__ReportMaintPermsReallyConfiguration;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            UpdateQuery updateQuery = new UpdateQuery(m_permission.getName());
            updateQuery.addValue(m_permission.getColumnByName("grouping").getName(), "configuration");
            updateQuery.addWhere(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "report");
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

}
