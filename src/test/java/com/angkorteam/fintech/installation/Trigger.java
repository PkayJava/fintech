package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.spring.JdbcTemplate;

/**
 * Created by socheatkhauv on 6/21/17.
 */
public class Trigger {

    public static void main(String[] args) throws Exception {

        File fintechFile = new File(FileUtils.getUserDirectory(), ".xml/fintech.properties.xml");

        Properties properties = new Properties();
        try (FileInputStream inputStream = FileUtils.openInputStream(fintechFile)) {
            properties.loadFromXML(inputStream);
        }
        BasicDataSource platformDataSource = new BasicDataSource();
        platformDataSource.setUsername(properties.getProperty("app.jdbc.username"));
        platformDataSource.setPassword(properties.getProperty("app.jdbc.password"));
        platformDataSource.setUrl(properties.getProperty("app.jdbc.url"));
        platformDataSource.setDriverClassName(properties.getProperty("app.jdbc.driver"));

        String mifosUrl = properties.getProperty("mifos.url");

        MifosDataSourceManager dataSourceManager = new MifosDataSourceManager();
        dataSourceManager.setDelegate(platformDataSource);
        dataSourceManager.setMifosUrl(mifosUrl);
        dataSourceManager.afterPropertiesSet();

        String delimiter = "";
        String newline = " ";

        DataSource dataSource = dataSourceManager.getDataSource("default");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Connection connection = dataSource.getConnection();
        List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
        StringBuffer sql = new StringBuffer();
        for (String table : tables) {
            System.out.println(table);
            if ("tbl_audit".equals(table) || "tbl_audit_value".equals(table)) {
                continue;
            }

            ResultSet resultSet = connection.createStatement().executeQuery("select * from " + table);
            ResultSetMetaData metaData = resultSet.getMetaData();

            boolean hasId = false;
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String fieldName = metaData.getColumnName(i);
                if (fieldName.equals("id")) {
                    hasId = true;
                    break;
                }
            }

            {
                StringBuffer delete = new StringBuffer();
                // delete.append("delimiter ").append(delimiter).append(newline);
                delete.append("DROP TRIGGER IF EXISTS " + table + "_d").append(delimiter).append(newline);
                try (Statement statement = connection.createStatement()) {
                    statement.execute(delete.toString());
                    delete.delete(0, delete.length());
                }
                delete.append("CREATE TRIGGER `" + table + "_d`").append(newline);
                delete.append("BEFORE DELETE").append(newline);
                delete.append("  ON ").append(table).append(newline);
                delete.append("FOR").append(newline);
                delete.append("EACH ROW").append(newline);
                delete.append("  BEGIN").append(newline);
                delete.append("    DECLARE _id VARCHAR(100);").append(newline);
                delete.append("    DECLARE _log_script TEXT;").append(newline);
                delete.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String logScript = "CONCAT('DELETE FROM " + table + " WHERE id = ', OLD.id)";
                if (hasId) {
                    delete.append("    SELECT " + logScript + " INTO _log_script FROM dual;").append(newline);
                    delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'DELETE', '" + table + "', _log_script);").append(newline);
                } else {
                    delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'DELETE', '" + table + "', NULL);").append(newline);
                }
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    delete.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NULL);").append(newline);
                }
                delete.append("  END").append(delimiter).append(newline);
                sql.append(delete);
                try (Statement statement = connection.createStatement()) {
                    statement.execute(delete.toString());
                }
            }

            {
                StringBuffer insert = new StringBuffer();
                insert.append("DROP TRIGGER IF EXISTS " + table + "_i").append(delimiter).append(newline);
                try (Statement statement = connection.createStatement()) {
                    statement.execute(insert.toString());
                    insert.delete(0, insert.length());
                }
                insert.append("CREATE TRIGGER `" + table + "_i`").append(newline);
                insert.append("AFTER INSERT").append(newline);
                insert.append("  ON ").append(table).append(newline);
                insert.append("FOR").append(newline);
                insert.append("EACH ROW").append(newline);
                insert.append("  BEGIN").append(newline);
                insert.append("    DECLARE _id VARCHAR(100);").append(newline);
                insert.append("    DECLARE _log_script TEXT;").append(newline);
                insert.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String logScript = "CONCAT('INSERT INTO " + table + "(";
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    if (i == metaData.getColumnCount()) {
                        String fieldName = metaData.getColumnName(i);
                        logScript = logScript + fieldName;
                    } else {
                        String fieldName = metaData.getColumnName(i);
                        logScript = logScript + fieldName + ", ";
                    }
                }
                logScript = logScript + ") VALUES(', ";
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    String value = "NEW." + fieldName;
                    if (i == metaData.getColumnCount()) {
                        String fieldValue = "IF(" + value + " IS NULL, 'NULL', CONCAT('\"', " + value + ", '\"'))";
                        logScript = logScript + fieldValue + ", ";
                    } else {
                        String fieldValue = "IF(" + value + " IS NULL, 'NULL, ', CONCAT('\"', " + value + ", '\", '))";
                        logScript = logScript + fieldValue + ", ";
                    }
                }
                logScript = logScript + "')'";
                logScript = logScript + ")";
                if (hasId) {
                    insert.append("    SELECT " + logScript + " INTO _log_script FROM dual;").append(newline);
                    insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'INSERT', '" + table + "', _log_script);").append(newline);
                } else {
                    insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'INSERT', '" + table + "', NULL);").append(newline);
                }
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    insert.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, after_value, before_value) VALUES (uuid(), _id, '" + fieldName + "', NEW." + fieldName + ", NULL);").append(newline);
                }
                insert.append("  END").append(delimiter).append(newline);
                sql.append(insert);

                try (Statement statement = connection.createStatement()) {
                    statement.execute(insert.toString());
                }
            }

            {
                StringBuffer update = new StringBuffer();
                update.append("DROP TRIGGER IF EXISTS " + table + "_u").append(delimiter).append(newline);
                try (Statement statement = connection.createStatement()) {
                    statement.execute(update.toString());
                    update.delete(0, update.length());
                }
                update.append("CREATE TRIGGER `" + table + "_u`").append(newline);
                update.append("AFTER UPDATE").append(newline);
                update.append("  ON ").append(table).append(newline);
                update.append("FOR").append(newline);
                update.append("EACH ROW").append(newline);
                update.append("  BEGIN").append(newline);
                update.append("    DECLARE _id VARCHAR(100);").append(newline);
                update.append("    DECLARE _log_script TEXT;").append(newline);
                update.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String logScript = "CONCAT('UPDATE " + table + " SET ";
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    String value = "NEW." + fieldName;
                    String fieldValue = "IF(" + value + " IS NULL, 'NULL', CONCAT('\"', " + value + ", '\"'))";
                    if (i == metaData.getColumnCount()) {
                        logScript = logScript + fieldName + " = ', " + fieldValue + ", ' ";
                    } else {
                        logScript = logScript + fieldName + " = ', " + fieldValue + ", '" + ", ";
                    }
                }
                logScript = logScript + "WHERE id = ', NEW.id)";
                if (hasId) {
                    update.append("    SELECT " + logScript + " INTO _log_script FROM dual;").append(newline);
                    update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'UPDATE', '" + table + "', _log_script);").append(newline);
                } else {
                    update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'UPDATE', '" + table + "', NULL);").append(newline);
                }
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    update.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NEW." + fieldName + ");").append(newline);
                }
                update.append("  END").append(delimiter).append(newline);
                sql.append(update);

                try (Statement statement = connection.createStatement()) {
                    statement.execute(update.toString());
                }
            }
            resultSet.close();
        }

        FileUtils.write(new File("src/test/resources/trigger.sql"), sql.toString(), "UTF-8");
    }
}
