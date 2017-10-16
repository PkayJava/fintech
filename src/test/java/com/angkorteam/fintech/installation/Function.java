package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.joda.time.DateTime;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.Dropdown;
import com.angkorteam.fintech.dto.builder.AccountRuleBuilder;
import com.angkorteam.fintech.dto.builder.CodeValueBuilder;
import com.angkorteam.fintech.dto.builder.FinancialActivityBuilder;
import com.angkorteam.fintech.dto.builder.FloatingRateBuilder;
import com.angkorteam.fintech.dto.builder.FundBuilder;
import com.angkorteam.fintech.dto.builder.GLAccountBuilder;
import com.angkorteam.fintech.dto.builder.HolidayBuilder;
import com.angkorteam.fintech.dto.builder.OfficeBuilder;
import com.angkorteam.fintech.dto.builder.TaxComponentBuilder;
import com.angkorteam.fintech.dto.builder.TaxGroupBuilder;
import com.angkorteam.fintech.dto.builder.WorkingDayBuilder;
import com.angkorteam.fintech.dto.constant.FinancialActivityTypeEnum;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.RepaymentOption;
import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.helper.FloatingRateHelper;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.helper.TaxComponentHelper;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Function {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final static Map<String, List<String>> IDS = Maps.newHashMap();

    static {
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

    public static void setupOffice(IMifos mifos, JdbcTemplate jdbcTemplate, Date openingDate, List<String> offices) throws ParseException, UnirestException {
        for (String office : offices) {
            boolean hasOffice = jdbcTemplate.queryForObject("select count(*) from m_office where name = ?", Boolean.class, office);
            if (!hasOffice) {
                OfficeBuilder builder = new OfficeBuilder();
                builder.withExternalId(StringUtils.upperCase(UUID.randomUUID().toString()));
                builder.withOpeningDate(openingDate);
                builder.withName(office);
                OfficeHelper.create(mifos, builder.build());
            }
        }
    }

    public static void setupWorkingDay(IMifos session) throws UnirestException {
        WorkingDayBuilder builder = new WorkingDayBuilder();
        builder.withMonday(true);
        builder.withTuesday(true);
        builder.withWednesday(true);
        builder.withThursday(true);
        builder.withFriday(true);
        builder.withSaturday(false);
        builder.withSunday(false);
        builder.withExtendTermForDailyRepayments(false);
        builder.withRepaymentRescheduleType(RepaymentOption.MoveToPreviousWorkingDay);
        WorkingDayHelper.update(session, builder.build());
    }

    public static void setupCurrency(IMifos session, JdbcTemplate jdbcTemplate, List<String> currencies) throws UnirestException {
        List<String> codes = new ArrayList<>();
        for (String currency : currencies) {
            boolean hasCurrency = jdbcTemplate.queryForObject("SELECT count(*) FROM m_currency where code = ?", Boolean.class, currency);
            if (hasCurrency) {
                boolean configured = jdbcTemplate.queryForObject("SELECT count(*) FROM m_organisation_currency where code = ?", Boolean.class, currency);
                if (configured) {
                    // continue;
                }
            }
            codes.add(currency);
        }
        if (!codes.isEmpty()) {
            CurrencyHelper.update(session, codes);
        }
    }

    public static void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate, String officeId, List<String> values) throws UnirestException, ParseException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            if (!jdbcTemplate.queryForObject("select count(*) from m_holiday where name = ?", Boolean.class, name)) {
                String dateForm = temp[1];
                String dateTo = temp[2];
                String rescheduled = temp[3];
                HolidayBuilder builder = new HolidayBuilder();
                builder.withName(name);
                builder.withDescription(name);
                builder.withReschedulingType(ReschedulingType.SpecifiedDate);
                builder.withOffice(officeId);
                builder.withFromDate(DATE_FORMAT.parse(dateForm));
                builder.withToDate(DATE_FORMAT.parse(dateTo));
                builder.withRepaymentsRescheduledTo(DATE_FORMAT.parse(rescheduled));
                HolidayHelper.create(session, builder.build());
            }
        }
    }

    public static void setupFinancialActivity(IMifos session, JdbcTemplate jdbcTemplate, List<String> values) throws UnirestException, ParseException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            FinancialActivityTypeEnum type = FinancialActivityTypeEnum.valueOf(temp[0]);
            String account = temp[1];
            String accountId = jdbcTemplate.queryForObject("select id from acc_gl_account where name = ?", String.class, account);
            if (!jdbcTemplate.queryForObject("select count(*) from acc_gl_financial_activity_account where financial_activity_type = ? and gl_account_id = ?", Boolean.class, type.getLiteral(), accountId)) {
                FinancialActivityBuilder builder = new FinancialActivityBuilder();
                builder.withFinancialActivityId(type);
                builder.withGlAccountId(accountId);
                FinancialActivityHelper.create(session, builder.build());
            }
        }
    }

    public static void setupAccountingRule(IMifos session, JdbcTemplate jdbcTemplate, String officeId, String debitId, String creditId, List<String> names) throws UnirestException {
        for (String name : names) {
            if (!jdbcTemplate.queryForObject("select count(*) from acc_accounting_rule where name = ?", Boolean.class, name)) {
                AccountRuleBuilder builder = new AccountRuleBuilder();
                builder.withName(name);
                builder.withDescription(name);
                builder.withOfficeId(officeId);
                builder.withAccountToDebit(debitId);
                builder.withAccountToCredit(creditId);
                AccountingRuleHelper.create(session, builder.build());
            }
        }
    }

    public static void setupGLAccount(IMifos session, JdbcTemplate jdbcTemplate, List<String> values, RandomStringGenerator stringGenerator) throws UnirestException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            if (!jdbcTemplate.queryForObject("select count(*) from acc_gl_account where name = ?", Boolean.class, name)) {
                String tag = temp[1];
                String tagId = jdbcTemplate.queryForObject("select m_code_value.id from m_code_value inner join m_code on m_code.id = m_code_value.code_id where m_code.code_name = ? limit 1", String.class, tag);
                GLAccountBuilder builder = new GLAccountBuilder();
                builder.withName(name);
                builder.withDescription(name);
                builder.withGlCode(StringUtils.upperCase(stringGenerator.generate(5)));
                builder.withManualEntriesAllowed(true);
                builder.withTagId(tagId);
                builder.withUsage(AccountUsage.Detail);
                if (Dropdown.AssetAccountTags.equals(tag)) {
                    builder.withType(AccountType.Asset);
                } else if (Dropdown.EquityAccountTags.equals(tag)) {
                    builder.withType(AccountType.Equity);
                } else if (Dropdown.LiabilityAccountTags.equals(tag)) {
                    builder.withType(AccountType.Liability);
                } else if (Dropdown.IncomeAccountTags.equals(tag)) {
                    builder.withType(AccountType.Income);
                } else if (Dropdown.ExpenseAccountTags.equals(tag)) {
                    builder.withType(AccountType.Expense);
                }
                GLAccountHelper.create(session, builder.build());
            }
        }

    }

    public static void setupTaxComponent(IMifos session, JdbcTemplate jdbcTemplate, List<String> values, RandomStringGenerator stringGenerator) throws UnirestException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            boolean has = jdbcTemplate.queryForObject("select count(*) from m_tax_component where name = ?", boolean.class, name);
            if (!has) {
                TaxComponentBuilder builder = new TaxComponentBuilder();
                builder.withName(name);
                builder.withPercentage(Double.valueOf(temp[1]));
                builder.withCreditAccountType(AccountType.valueOf(temp[2]));
                String creditAccountId = jdbcTemplate.queryForObject("select id from acc_gl_account where name = ?", String.class, temp[3]);
                builder.withCreditAccountId(creditAccountId);
                builder.withStartDate(DateTime.now().toDate());
                TaxComponentHelper.create(session, builder.build());
            }
        }
    }

    public static void setupFloatingRate(IMifos session, JdbcTemplate jdbcTemplate, List<String> values, RandomStringGenerator stringGenerator) throws UnirestException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            boolean has = jdbcTemplate.queryForObject("select count(*) from m_floating_rates WHERE name = ?", boolean.class, name);
            if (!has) {
                boolean base = Boolean.valueOf(temp[1]);
                boolean differential = Boolean.valueOf(temp[2]);
                Double rate = Double.valueOf(temp[3]);
                FloatingRateBuilder builder = new FloatingRateBuilder();
                builder.withName(name);
                builder.withActive(true);
                builder.withBaseLendingRate(base);
                builder.withRatePeriod(DateTime.now().plusDays(1).toDate(), rate, differential);
                FloatingRateHelper.create(session, builder.build());
            }
        }
    }

    public static void setupTaxGroup(IMifos session, JdbcTemplate jdbcTemplate, List<String> values, RandomStringGenerator stringGenerator) throws UnirestException {
        for (String temps : values) {
            String name = temps;
            boolean has = jdbcTemplate.queryForObject("select count(*) from m_tax_group where name = ?", boolean.class, name);
            if (!has) {
                TaxGroupBuilder builder = new TaxGroupBuilder();
                builder.withName(name);
                List<String> ids = jdbcTemplate.queryForList("select id from m_tax_component", String.class);
                for (String id : ids) {
                    builder.withTaxComponent(null, id, DateTime.now().plusDays(1).toDate(), null);
                }
            }
        }
    }

    public static void setupSystemParameter(IMifos session, JdbcTemplate jdbcTemplate, List<String> values) throws UnirestException {
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String code = temp[0];
            String value = temp[1];
            String codeId = jdbcTemplate.queryForObject("select id from m_code where code_name = ?", String.class, code);
            if (!jdbcTemplate.queryForObject("select count(*) from m_code_value where code_id = ? and code_value = ?", Boolean.class, codeId, value)) {
                String description = temp[2];
                CodeValueBuilder builder = new CodeValueBuilder();
                builder.withCodeId(codeId);
                builder.withName(value);
                builder.withDescription(description);
                builder.withActive(true);
                CodeHelper.createValue(session, builder.build());
            }
        }
    }

    public static void setupFund(IMifos session, JdbcTemplate jdbcTemplate, List<String> funds) throws UnirestException {
        for (String fund : funds) {
            boolean hasFund = jdbcTemplate.queryForObject("select count(*) from m_fund where name = ?", Boolean.class, fund);
            if (!hasFund) {
                FundBuilder builder = new FundBuilder();
                builder.withExternalId(StringUtils.upperCase(UUID.randomUUID().toString()));
                builder.withName(fund);
                FundHelper.create(session, builder.build());
            }
        }
    }

    public static void triggerData(boolean fileout, DataSource dataSource) throws SQLException, IOException {
        String delimiter = "";
        String newline = " ";

        if (fileout) {
            delimiter = "%%";
            newline = "\n";
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        if (!fileout) {
            jdbcTemplate.execute("DROP TABLE IF EXISTS `tbl_audit`");
            jdbcTemplate.execute("CREATE TABLE `tbl_audit` (  `id` varchar(100) NOT NULL,  `log_date` datetime NOT NULL,  `log_event` varchar(20) NOT NULL,  `log_table` varchar(100) NOT NULL,  `log_script` TEXT,  PRIMARY KEY (`id`),  KEY `tbl_audit_001` (`log_date`),  KEY `tbl_audit_002` (`log_event`),  KEY `tbl_audit_003` (`log_table`))");
            jdbcTemplate.execute("DROP TABLE IF EXISTS `tbl_audit_value`");
            jdbcTemplate.execute("CREATE TABLE `tbl_audit_value` (  `id` varchar(100) NOT NULL,  `audit_id` varchar(100) DEFAULT NULL,  `field_name` varchar(255) DEFAULT NULL,  `before_value` text,  `after_value` text,  PRIMARY KEY (`id`),  KEY `tbl_audit_value_001` (`audit_id`),  KEY `tbl_audit_value_002` (`field_name`))");
        }

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
                String logScript = "CONCAT('DELETE FROM " + table + " WHERE " + field + " = ''', OLD." + field + ", ''''";
                for (int i = 1; i < idFields.size(); i++) {
                    String f = idFields.get(i);
                    logScript = logScript + ", ' AND " + f + " = ''', OLD." + f + ", ''''";
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
                        String fieldValue = "IF(" + value + " IS NULL, 'NULL', CONCAT('''', " + value + ", ''''))";
                        logScript = logScript + fieldValue + ", ";
                    } else {
                        String fieldValue = "IF(" + value + " IS NULL, 'NULL, ', CONCAT('''', " + value + ", ''', '))";
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
                    String fieldValue = "IF(" + value + " IS NULL, 'NULL', CONCAT('''', " + value + ", ''''))";
                    if (i == metaData.getColumnCount()) {
                        logScript = logScript + fieldName + " = ', " + fieldValue + ", ' ";
                    } else {
                        logScript = logScript + fieldName + " = ', " + fieldValue + ", '" + ", ";
                    }
                }

                String field = idFields.get(0);
                logScript = logScript + "WHERE " + field + " = ''', NEW." + field + ", ''''";
                for (int i = 1; i < idFields.size(); i++) {
                    String f = idFields.get(i);
                    logScript = logScript + ", ' AND " + f + " = ''', OLD." + f + ", ''''";
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
