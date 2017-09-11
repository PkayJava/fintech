package com.angkorteam.fintech.installation;

import com.angkorteam.framework.spring.JdbcTemplate;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by socheatkhauv on 6/21/17.
 */
public class Trigger {

    public static void main(String[] args) throws SQLException, IOException {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://172.16.1.6:3306/mifostenant_default?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&serverTimezone=UTC");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Connection connection = dataSource.getConnection();
        List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
        StringBuffer sql = new StringBuffer();
        for (String table : tables) {
            if ("tbl_audit".equals(table) || "tbl_audit_value".equals(table)) {
                continue;
            }

            ResultSet resultSet = connection.createStatement().executeQuery("select * from " + table);
            ResultSetMetaData metaData = resultSet.getMetaData();

            {
                StringBuffer delete = new StringBuffer();
                delete.append("DROP TRIGGER IF EXISTS " + table + "_d;").append("\n");
                delete.append("CREATE TRIGGER `" + table + "_d`").append("\n");
                delete.append("BEFORE DELETE").append("\n");
                delete.append("  ON ").append(table).append("\n");
                delete.append("FOR").append("\n");
                delete.append("EACH ROW").append("\n");
                delete.append("  BEGIN").append("\n");
                delete.append("    DECLARE _id VARCHAR(100);").append("\n");
                delete.append("    SELECT uuid() INTO _id FROM dual;").append("\n");
                delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table) VALUES (_id, now(), 'DELETE', '" + table + "');").append("\n");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    delete.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NULL);").append("\n");
                }
                delete.append("  END;").append("\n");
                sql.append(delete);
            }

            {
                StringBuffer insert = new StringBuffer();
                insert.append("DROP TRIGGER IF EXISTS " + table + "_i;").append("\n");
                insert.append("CREATE TRIGGER `" + table + "_i`").append("\n");
                insert.append("AFTER INSERT").append("\n");
                insert.append("  ON ").append(table).append("\n");
                insert.append("FOR").append("\n");
                insert.append("EACH ROW").append("\n");
                insert.append("  BEGIN").append("\n");
                insert.append("    DECLARE _id VARCHAR(100);").append("\n");
                insert.append("    SELECT uuid() INTO _id FROM dual;").append("\n");
                insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table) VALUES (_id, now(), 'INSERT', '" + table + "');").append("\n");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    insert.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, after_value, before_value) VALUES (uuid(), _id, '" + fieldName + "', NEW." + fieldName + ", NULL);").append("\n");
                }
                insert.append("  END;").append("\n");
                sql.append(insert);
            }

            {
                StringBuffer update = new StringBuffer();
                update.append("DROP TRIGGER IF EXISTS " + table + "_u;").append("\n");
                update.append("CREATE TRIGGER `" + table + "_u`").append("\n");
                update.append("AFTER UPDATE").append("\n");
                update.append("  ON ").append(table).append("\n");
                update.append("FOR").append("\n");
                update.append("EACH ROW").append("\n");
                update.append("  BEGIN").append("\n");
                update.append("    DECLARE _id VARCHAR(100);").append("\n");
                update.append("    SELECT uuid() INTO _id FROM dual;").append("\n");
                update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table) VALUES (_id, now(), 'UPDATE', '" + table + "');").append("\n");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    update.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NEW." + fieldName + ");").append("\n");
                }
                update.append("  END;").append("\n");
                sql.append(update);
            }
            resultSet.close();
        }

        FileUtils.write(new File("/Users/socheatkhauv/Documents/adminlte/src/main/resources/trigger.sql"), sql.toString(), "UTF-8");
    }
}
