package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V76__RenamePermissionGrouping extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V76__RenamePermissionGrouping;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        UpdateQuery updateQuery = null;

        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");

        updateQuery = new UpdateQuery(m_permission.getName());
        updateQuery.addValue(m_permission.getColumnByName("grouping").getQualifiedLabel(), "portfolio_center");
        updateQuery.addWhere(m_permission.getColumnByName("code").getName() + " like :code", "code", "%center%");
        updateQuery.addWhere(m_permission.getColumnByName("grouping").getQualifiedLabel() + " = :g", "portfolio");
        named.update(updateQuery.toSQL(), updateQuery.toParam());

        updateQuery = new UpdateQuery(m_permission.getName());
        updateQuery.addValue(m_permission.getColumnByName("grouping").getQualifiedLabel(), "portfolio_group");
        updateQuery.addWhere(m_permission.getColumnByName("code").getName() + " like :code", "code", "%group%");
        updateQuery.addWhere(m_permission.getColumnByName("grouping").getQualifiedLabel() + " = :g", "portfolio");
        named.update(updateQuery.toSQL(), updateQuery.toParam());
    }

}