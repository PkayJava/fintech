package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

        DataSource dataSource = dataSourceManager.getDataSource("default");

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
                delete.append("DROP TRIGGER IF EXISTS " + table + "_d;").append("\n");
                delete.append("CREATE TRIGGER `" + table + "_d`").append("\n");
                delete.append("BEFORE DELETE").append("\n");
                delete.append("  ON ").append(table).append("\n");
                delete.append("FOR").append("\n");
                delete.append("EACH ROW").append("\n");
                delete.append("  BEGIN").append("\n");
                delete.append("    DECLARE _id VARCHAR(100);").append("\n");
                delete.append("    SELECT uuid() INTO _id FROM dual;").append("\n");
                String logScript = "CONCAT('DELETE FROM " + table + " WHERE id = ', OLD.id)";
                if (!hasId) {
                    logScript = "NULL";
                }
                delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'DELETE', '" + table + "', " + logScript + ");").append("\n");
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
                    String n = "NEW." + fieldName;
                    if (i == metaData.getColumnCount()) {
                        logScript = logScript + '\'' + '\\' + '\'' + '\'' + ", " + n + ", " + '\'' + '\\' + '\'' + '\'' + ", ";
                    } else {
                        logScript = logScript + '\'' + '\\' + '\'' + '\'' + ", " + n + ", " + '\'' + '\\' + '\'' + '\'' + ", " + '\'' + ',' + '\'' + ", ";
                    }
                }
                logScript = logScript + "')')";
                if (!hasId) {
                    logScript = "NULL";
                }
                insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'INSERT', '" + table + "', " + logScript + ");").append("\n");
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
                String logScript = "CONCAT('UPDATE " + table + " SET ";
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    String value = "'NEW." + fieldName + "'";
                    if (i == metaData.getColumnCount()) {
                        logScript = logScript + fieldName + " = ', " + value + ", ' ";
                    } else {
                        logScript = logScript + fieldName + " = ', " + value + ", '" + ", ";
                    }
                }
                logScript = logScript + "WHERE id = NEW.id')";
                if (!hasId) {
                    logScript = "NULL";
                }
                update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'UPDATE', '" + table + "', " + logScript + ");").append("\n");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnName(i);
                    update.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NEW." + fieldName + ");").append("\n");
                }
                update.append("  END;").append("\n");
                sql.append(update);
            }
            resultSet.close();
        }

        FileUtils.write(new File("src/test/resources/trigger.sql"), sql.toString(), "UTF-8");
    }
}
