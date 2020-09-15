package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class V48__AddingS3Support extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V48__AddingS3Support + getInternalChecksum("V48__adding-S3-Support-002.xml", "V48__adding-S3-Support-004.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        SelectQuery selectQuery = null;
        {
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            insert_c_configuration(named, c_configuration, "amazon-S3", 0);
        }
        {
            dataContext.refreshSchemas();
            updateLiquibase("V48__adding-S3-Support-002.xml");
        }
        {
            dataContext.refreshSchemas();
            Table c_external_service = dataContext.getDefaultSchema().getTableByName("c_external_service");
            insert_c_external_service(named, c_external_service, "s3_bucket_name");
            insert_c_external_service(named, c_external_service, "s3_access_key");
            insert_c_external_service(named, c_external_service, "s3_secret_key");
        }
        {
            dataContext.refreshSchemas();
            if (dataContext.getDefaultSchema().getTableByName("m_image") == null) {
                updateLiquibase("V48__adding-S3-Support-004.xml");
            }
            dataContext.refreshSchemas();
            updateLiquibase("V48__adding-S3-Support-005.xml");
        }
        {
            dataContext.refreshSchemas();
            Table m_client = dataContext.getDefaultSchema().getTableByName("m_client");
            Table m_image = dataContext.getDefaultSchema().getTableByName("m_image");
            long curr_image = 0;
            long prev_image = 0;
            selectQuery = new SelectQuery(m_client.getName());
            selectQuery.addField("COUNT(" + m_client.getColumnByName("id").getName() + ")");
            Long temp = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
            long num_of_clients = temp == null ? 0 : temp;
            long curr_client = 0;
            for (int v_counter = 0; v_counter < num_of_clients; v_counter++) {
                selectQuery = new SelectQuery(m_client.getName());
                selectQuery.addWhere("image_key IS NULL");
                selectQuery.setLimit(0L, 1L);
                Map<String, Object> record = null;
                try {
                    record = named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());
                } catch (EmptyResultDataAccessException e) {
                }
                if (record != null) {
                    InsertQuery insertQuery = new InsertQuery(m_image.getName());
                    insertQuery.addValue(m_image.getColumnByName("location").getName(), (String) record.get(m_client.getColumnByName("image_key").getName()));
                    insertQuery.addValue(m_image.getColumnByName("storage_type_enum").getName(), 1);
                    named.update(insertQuery.toSQL(), insertQuery.toParam());
                }

                selectQuery = new SelectQuery(m_image.getName());
                selectQuery.addField("MAX(" + m_image.getColumnByName("id").getName() + ")");
                temp = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
                curr_image = temp == null ? 0 : temp;

                selectQuery = new SelectQuery(m_client.getName());
                selectQuery.addField(m_client.getColumnByName("id").getName());
                selectQuery.addWhere(m_client.getColumnByName("image_key").getName() + " IS NULL");
                selectQuery.setLimit(0L, 1L);
                temp = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
                curr_client = temp == null ? 0 : temp;

                if (prev_image != curr_image) {
                    UpdateQuery updateQuery = new UpdateQuery(m_client.getName());
                    updateQuery.addValue(m_client.getColumnByName("image_id").getName(), curr_image);
                    updateQuery.addWhere(m_client.getColumnByName("id").getName() + " = :id", curr_client);
                    named.update(updateQuery.toSQL(), updateQuery.toParam());
                }
                prev_image = curr_image;
            }
            DeleteQuery deleteQuery = new DeleteQuery(m_image.getName());
            deleteQuery.addWhere(m_image.getColumnByName("location").getName() + " IS NULL");
            named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
    }

    protected void insert_c_external_service(NamedParameterJdbcTemplate named, Table table, String name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_c_configuration(NamedParameterJdbcTemplate named, Table table, String name, long enabled) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("enabled").getName(), enabled);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
