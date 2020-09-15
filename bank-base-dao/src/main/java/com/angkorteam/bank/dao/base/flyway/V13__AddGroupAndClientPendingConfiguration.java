package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V13__AddGroupAndClientPendingConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V13__AddGroupAndClientPendingConfiguration;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            insert_c_configuration(named, c_configuration, "allow-pending-client-status", 0);
            insert_c_configuration(named, c_configuration, "allow-pending-group-status", 0);
        }
        {
            dataContext.refreshSchemas();
            Table r_enum_value = dataContext.getDefaultSchema().getTableByName("r_enum_value");
            insert_r_enum_value(named, r_enum_value, "status_id", 0, "Invalid", "Invalid");
            insert_r_enum_value(named, r_enum_value, "status_id", 100, "Pending", "Pending");
            insert_r_enum_value(named, r_enum_value, "status_id", 300, "Active", "Active");
            insert_r_enum_value(named, r_enum_value, "status_id", 600, "Closed", "Closed");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 0, "Invalid", "Invalid");
        }
    }

    protected void insert_c_configuration(NamedParameterJdbcTemplate named, Table table, String name, long enabled) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("enabled").getName(), enabled);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_r_enum_value(NamedParameterJdbcTemplate named, Table table, String enum_name, long enum_id, String enum_message_property, String enum_value) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("enum_name").getName(), enum_name);
        insertQuery.addValue(table.getColumnByName("enum_id").getName(), enum_id);
        insertQuery.addValue(table.getColumnByName("enum_message_property").getName(), enum_message_property);
        insertQuery.addValue(table.getColumnByName("enum_value").getName(), enum_value);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
