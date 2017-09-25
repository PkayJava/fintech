package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/21/17.
 */
public class Trigger {

    private final static Map<String, List<String>> IDS;

    static {
        IDS = new HashMap<>();
        IDS.put("c_external_service_properties", Lists.newArrayList("name", "external_service_id"));
        IDS.put("m_appuser_role", Lists.newArrayList("appuser_id", "role_id"));
        IDS.put("m_deposit_product_interest_rate_chart", Lists.newArrayList("deposit_product_id", "interest_rate_chart_id"));
        IDS.put("m_group_client", Lists.newArrayList("group_id", "client_id"));
        IDS.put("m_holiday_office", Lists.newArrayList("office_id", "holiday_id"));
        IDS.put("m_loan_arrears_aging", Lists.newArrayList("loan_id"));
        IDS.put("m_loan_paid_in_advance", Lists.newArrayList("loan_id"));
        IDS.put("m_product_loan_charge", Lists.newArrayList("product_loan_id", "charge_id"));
        IDS.put("m_role_permission", Lists.newArrayList("role_id", "permission_id"));
        IDS.put("m_savings_product_charge", Lists.newArrayList("savings_product_id", "charge_id"));
        IDS.put("m_share_product_charge", Lists.newArrayList("product_id", "charge_id"));
        IDS.put("m_template_m_templatemappers", Lists.newArrayList("m_template_id", "mappers_id"));
        // IDS.put("oauth_access_token", Lists.newArrayList("token_id",
        // "authentication_id", "client_id"));
        // IDS.put("oauth_client_details", Lists.newArrayList("client_id"));
        // IDS.put("oauth_refresh_token", Lists.newArrayList("token_id"));
        IDS.put("x_registered_table", Lists.newArrayList("registered_table_name"));
        IDS.put("x_table_column_code_mappings", Lists.newArrayList("column_alias_name"));
    }

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

        boolean fileout = true;

        String delimiter = "";
        String newline = " ";

        if (fileout) {
            delimiter = "%%";
            newline = "\n";
        }

        DataSource dataSource = dataSourceManager.getDataSource("default");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Connection connection = dataSource.getConnection();
        List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
        List<String> customTable = jdbcTemplate.queryForList("SELECT registered_table_name FROM x_registered_table", String.class);
        StringBuffer sql = new StringBuffer();
        sql.append("delimiter %%").append(newline);
        for (String table : tables) {
            if ("tbl_audit".equals(table) || "tbl_audit_value".equals(table)) {
                continue;
            }

            if (customTable.contains(table)) {
                continue;
            }

            System.out.println(table);

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

            List<String> idFields = null;
            if (!hasId) {
                hasId = IDS.containsKey(table);
                idFields = IDS.get(table);
                if (idFields == null) {
                    idFields = Arrays.asList("id");
                }
            } else {
                idFields = Lists.newArrayList("id");
            }

            if (!hasId) {
                System.out.println(table);
            }

            {
                StringBuffer delete = new StringBuffer();
                delete.append("DROP TRIGGER IF EXISTS " + table + "_d").append(delimiter).append(newline);
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(delete.toString());
                        delete.delete(0, delete.length());
                    }
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
                String field = idFields.get(0);
                String logScript = "CONCAT('DELETE FROM " + table + " WHERE " + field + " = \"', OLD." + field + ", '\"'";
                for (int i = 1; i < idFields.size(); i++) {
                    String f = idFields.get(i);
                    logScript = logScript + ", ' AND " + f + " = \"', OLD." + f + ", '\"'";
                }
                logScript = logScript + ")";

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
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(delete.toString());
                    }
                }
            }

            {
                StringBuffer insert = new StringBuffer();
                insert.append("DROP TRIGGER IF EXISTS " + table + "_i").append(delimiter).append(newline);
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(insert.toString());
                        insert.delete(0, insert.length());
                    }
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
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(insert.toString());
                    }
                }
            }

            {
                StringBuffer update = new StringBuffer();
                update.append("DROP TRIGGER IF EXISTS " + table + "_u").append(delimiter).append(newline);
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(update.toString());
                        update.delete(0, update.length());
                    }
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

                String field = idFields.get(0);
                logScript = logScript + "WHERE " + field + " = \"', NEW." + field + ", '\"'";
                for (int i = 1; i < idFields.size(); i++) {
                    String f = idFields.get(i);
                    logScript = logScript + ", ' AND " + f + " = \"', OLD." + f + ", '\"'";
                }
                logScript = logScript + ")";
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
                if (!fileout) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(update.toString());
                    }
                }
            }
            sql.append("\n\n");
            resultSet.close();
        }
        if (fileout) {
            FileUtils.write(new File("src/test/resources/trigger.sql"), sql.toString(), "UTF-8");
        }
    }
}
