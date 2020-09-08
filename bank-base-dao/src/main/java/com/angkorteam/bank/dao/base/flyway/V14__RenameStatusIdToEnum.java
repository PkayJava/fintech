package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V14__RenameStatusIdToEnum extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V14__RenameStatusIdToEnum + getInternalChecksum("V14__rename_status_id_to_enum-001.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
            {
                updateLiquibase(database, "V14__rename_status_id_to_enum-001.xml");
            }
            {
                dataContext.refreshSchemas();
                Table r_enum_value = dataContext.getDefaultSchema().getTableByName("r_enum_value");
                UpdateQuery updateQuery = new UpdateQuery(r_enum_value.getName());
                updateQuery.addValue(r_enum_value.getColumnByName("enum_name").getName(), "status_enum");
                updateQuery.addValue(r_enum_value.getColumnByName("enum_name").getName() + " = :enum_name", "status_id");
                named.update(updateQuery.toSQL(), updateQuery.toParam());
            }
        }
    }


}
