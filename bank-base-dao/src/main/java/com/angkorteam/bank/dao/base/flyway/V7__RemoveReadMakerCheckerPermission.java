package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V7__RemoveReadMakerCheckerPermission extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V7__RemoveReadMakerCheckerPermission;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            Table m_role_permission = dataContext.getDefaultSchema().getTableByName("m_role_permission");
            long permission_id = lookup_m_permission(named, dataContext, "READ_MAKERCHECKER");
            DeleteQuery deleteQuery = null;

            deleteQuery = new DeleteQuery(m_role_permission.getName());
            deleteQuery.addWhere(m_role_permission.getColumnByName("permission_id").getName() + " = :permission_id", permission_id);
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(m_permission.getName());
            deleteQuery.addWhere(m_permission.getColumnByName("id").getName() + " = :id", permission_id);
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
    }

    protected long lookup_m_permission(NamedParameterJdbcTemplate named, DataContext dataContext, String code) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_permission");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("code").getName() + " = :code", code);
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
        return id == null ? 0 : id;
    }

}

