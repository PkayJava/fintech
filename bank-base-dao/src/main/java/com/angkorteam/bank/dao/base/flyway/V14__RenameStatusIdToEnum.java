package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V14__RenameStatusIdToEnum extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V14__RenameStatusIdToEnum + getInternalChecksum("V14__rename_status_id_to_enum-001.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            updateLiquibase("V14__rename_status_id_to_enum-001.xml");
        }
        {
            dataContext.refreshSchemas();
            Table r_enum_value = dataContext.getDefaultSchema().getTableByName("r_enum_value");
            UpdateQuery updateQuery = new UpdateQuery(r_enum_value.getName());
            updateQuery.addValue(r_enum_value.getColumnByName("enum_name").getName(), "status_enum");
            updateQuery.addWhere(r_enum_value.getColumnByName("enum_name").getName() + " = :enum_name", "status_id");
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

}
