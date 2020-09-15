package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V65__FixRupeeSymbolIssues extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V65__FixRupeeSymbolIssues;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        UpdateQuery updateQuery = null;

        Table m_currency = dataContext.getDefaultSchema().getTableByName("m_currency");
        updateQuery = new UpdateQuery(m_currency.getName());
        updateQuery.addValue(m_currency.getColumnByName("display_symbol").getName(), "₹");
        updateQuery.addWhere(m_currency.getColumnByName("id").getName() + " = :id", 64);
        named.update(updateQuery.toSQL(), updateQuery.toParam());

        Table m_organisation_currency = dataContext.getDefaultSchema().getTableByName("m_organisation_currency");
        updateQuery = new UpdateQuery(m_organisation_currency.getName());
        updateQuery.addValue(m_organisation_currency.getColumnByName("display_symbol").getName(), "₹");
        updateQuery.addWhere(m_organisation_currency.getColumnByName("code").getName() + " = :code", "INR");
        named.update(updateQuery.toSQL(), updateQuery.toParam());
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
