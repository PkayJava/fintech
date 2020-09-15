package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V59__AddGroupRolesSchemaAndPermissions extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V59__AddGroupRolesSchemaAndPermissions + getInternalChecksum("V59__add_group_roles_schema_and_permissions.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        dataContext.refreshSchemas();
        Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
        insert_m_code(named, m_code, "GROUPROLE", 1);

        dataContext.refreshSchemas();
        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
        insert_m_permission(named, m_permission, "portfolio", "ASSIGNROLE_GROUP", "GROUP", "ASSIGNROLE", 0);
        insert_m_permission(named, m_permission, "portfolio", "UNASSIGNROLE_GROUP", "GROUP", "UNASSIGNROLE", 0);
        insert_m_permission(named, m_permission, "portfolio", "UPDATEROLE_GROUP", "GROUP", "UPDATEROLE", 0);

        updateLiquibase("V59__add_group_roles_schema_and_permissions.xml");
    }

    protected void insert_m_code(NamedParameterJdbcTemplate named, Table table, String code_name, long is_system_defined) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_name").getName(), code_name);
        insertQuery.addValue(table.getColumnByName("is_system_defined").getName(), is_system_defined);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getQualifiedLabel(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        insertQuery.addValue(table.getColumnByName("can_maker_checker").getName(), can_maker_checker);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
