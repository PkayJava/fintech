package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V48__AddingS3Support extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V48__AddingS3Support + getInternalChecksum("V48__adding-S3-Support-002.xml", "V48__adding-S3-Support-004.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            dataContext.executeUpdate(callback -> {
                insert_c_configuration(c_configuration, callback, "amazon-S3", 0);
            });
        }
        {
            updateLiquibase(database, "V48__adding-S3-Support-002.xml");
        }
        {
            dataContext.refreshSchemas();
            Table c_external_service = dataContext.getDefaultSchema().getTableByName("c_external_service");
            dataContext.executeUpdate(callback -> {
                insert_c_external_service(c_external_service, callback, "s3_bucket_name");
                insert_c_external_service(c_external_service, callback, "s3_access_key");
                insert_c_external_service(c_external_service, callback, "s3_secret_key");
            });
        }
        {
            dataContext.refreshSchemas();
            if (dataContext.getDefaultSchema().getTableByName("m_image") == null) {
                updateLiquibase(database, "V48__adding-S3-Support-004.xml");
            }
            updateLiquibase(database, "V48__adding-S3-Support-005.xml");
        }
        {
            dataContext.refreshSchemas();
            Table m_client = dataContext.getDefaultSchema().getTableByName("m_client");
            int v_counter = 0;
            long num_of_clients = 0;
            int curr_image = 0;
            int prev_image = 0;
            try (DataSet rows = dataContext.query().from(m_client).selectAll().execute()) {
                rows.next();
                num_of_clients = (long) rows.getRow().getValue(0);
            }
        }
    }

    protected void insert_c_external_service(Table table, RowInsertable callback, String name) {
        callback.insertInto(table)
                .value(table.getColumnByName("name"), name)
                .execute();
    }

    protected void insert_c_configuration(Table table, RowInsertable callback, String name, long enabled) {
        callback.insertInto(table)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("enabled"), enabled)
                .execute();
    }

}
