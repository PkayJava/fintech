package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V85__ProductMixRelatedChanges extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V85__ProductMixRelatedChanges + getInternalChecksum("V85__product_mix_related_changes.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V85__product_mix_related_changes.xml");
        dataContext.refreshSchemas();

        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
        insert_m_permission(named, m_permission, "portfolio", "CREATE_PRODUCTMIX", "PRODUCTMIX", "CREATE", 0);
        insert_m_permission(named, m_permission, "portfolio", "UPDATE_PRODUCTMIX", "PRODUCTMIX", "UPDATE", 0);
        insert_m_permission(named, m_permission, "portfolio", "DELETE_PRODUCTMIX", "PRODUCTMIX", "DELETE", 0);
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