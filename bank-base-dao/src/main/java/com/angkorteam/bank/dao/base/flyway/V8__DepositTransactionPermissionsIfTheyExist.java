package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V8__DepositTransactionPermissionsIfTheyExist extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V8__DepositTransactionPermissionsIfTheyExist;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");

            DeleteQuery deleteQuery = null;

            deleteQuery = new DeleteQuery(m_permission.getName());
            deleteQuery.addWhere(m_permission.getColumnByName("grouping").getQualifiedLabel() + " = :grouping", "transaction_deposit");
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
    }

}

