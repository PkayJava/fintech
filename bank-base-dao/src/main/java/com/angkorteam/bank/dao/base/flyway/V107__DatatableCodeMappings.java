package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V107__DatatableCodeMappings extends LiquibaseJavaMigration {

    @Override
    protected Integer getInternalChecksum(String... names) {
        return Checksum.V107__DatatableCodeMappings + getInternalChecksum("V107__datatable_code_mappings.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        // changeset 001
        updateLiquibase("V107__datatable_code_mappings.xml");

        // changeset 002
        dataContext.refreshSchemas();
        Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
        InsertQuery insertQuery = new InsertQuery(c_configuration.getName());
        insertQuery.addValue(c_configuration.getColumnByName("name").getName() + " = :name", "constraint_approach_for_datatables");
        insertQuery.addValue(c_configuration.getColumnByName("enabled").getName() + " = :enabled", 1);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }
}
