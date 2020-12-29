package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V106__MorePermissionsForTransfers extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V106__MorePermissionsForTransfers;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");

        DeleteQuery deleteQuery = null;

        deleteQuery = new DeleteQuery(m_permission.getName());
        deleteQuery.addWhere(m_permission.getColumnByName("code").getName() + " = :code", "TRANSFER_CLIENT");
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        deleteQuery = new DeleteQuery(m_permission.getName());
        deleteQuery.addWhere(m_permission.getColumnByName("code").getName() + " = :code", "TRANSFER_CLIENT_CHECKER");
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

        InsertQuery insertQuery = null;

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "PROPOSETRANSFER_CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "PROPOSETRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "PROPOSETRANSFER_CLIENT_CHECKER");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "PROPOSETRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "ACCEPTTRANSFER_CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "ACCEPTTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "ACCEPTTRANSFER_CLIENT_CHECKER");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "ACCEPTTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "REJECTTRANSFER_CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "REJECTTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "REJECTTRANSFER_CLIENT_CHECKER");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "REJECTTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "WITHDRAWTRANSFER_CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "WITHDRAWTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());

        insertQuery = new InsertQuery(m_permission.getName());
        insertQuery.addValue(m_permission.getColumnByName("grouping").getName() + " = :grouping", "portfolio");
        insertQuery.addValue(m_permission.getColumnByName("code").getName() + " = :code", "WITHDRAWTRANSFER_CLIENT_CHECKER");
        insertQuery.addValue(m_permission.getColumnByName("entity_name").getName() + " = :entity_name", "CLIENT");
        insertQuery.addValue(m_permission.getColumnByName("action_name").getName() + " = :action_name", "WITHDRAWTRANSFER");
        insertQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName() + " = :can_maker_checker", 0);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
