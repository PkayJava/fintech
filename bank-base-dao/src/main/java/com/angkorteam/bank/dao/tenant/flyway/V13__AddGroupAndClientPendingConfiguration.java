package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.tenant.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V13__AddGroupAndClientPendingConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V13__AddGroupAndClientPendingConfiguration;
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            Table c_configuration = schema.getTableByName("c_configuration");
            dataContext.executeUpdate(callback -> {
                insert_c_configuration(c_configuration, callback, "allow-pending-client-status", 0);
                insert_c_configuration(c_configuration, callback, "allow-pending-group-status", 0);
            });
        }
        {
            Table r_enum_value = schema.getTableByName("r_enum_value");
            dataContext.executeUpdate(callback -> {
                insert_r_enum_value(r_enum_value, callback, "status_id", 0, "Invalid", "Invalid");
                insert_r_enum_value(r_enum_value, callback, "status_id", 100, "Pending", "Pending");
                insert_r_enum_value(r_enum_value, callback, "status_id", 300, "Active", "Active");
                insert_r_enum_value(r_enum_value, callback, "status_id", 600, "Closed", "Closed");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 0, "Invalid", "Invalid");
            });
        }
    }

    protected void insert_c_configuration(Table table, RowInsertable callback, String name, long enabled) {
        callback.insertInto(table)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("enabled"), enabled)
                .execute();
    }

    protected void insert_r_enum_value(Table table, RowInsertable callback, String enum_name, long enum_id, String enum_message_property, String enum_value) {
        callback.insertInto(table)
                .value(table.getColumnByName("enum_name"), enum_name)
                .value(table.getColumnByName("enum_id"), enum_id)
                .value(table.getColumnByName("enum_message_property"), enum_message_property)
                .value(table.getColumnByName("enum_value"), enum_value)
                .execute();
    }

}
