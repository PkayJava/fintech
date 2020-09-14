package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V66__ClientCloseFunctionality extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V66__ClientCloseFunctionality + getInternalChecksum("V66__client_close_functionality.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);

            dataContext.refreshSchemas();
            Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
            insert_m_code(named, m_code, "ClientClosureReason", 1);

            dataContext.refreshSchemas();
            updateLiquibase(database, "V66__client_close_functionality.xml");

            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            insert_m_permission(named, m_permission, "portfolio", "CLOSE_CLIENT", "CLIENT", "CLOSE");
        }
    }

    protected void insert_m_code(NamedParameterJdbcTemplate named, Table table, String code_name, long is_system_defined) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_name").getName(), code_name);
        insertQuery.addValue(table.getColumnByName("is_system_defined").getName(), is_system_defined);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getName(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
