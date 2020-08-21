package com.angkorteam.fintech.installation;

import com.angkorteam.fintech.client.Dropdown;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.enums.AccountUsage;
import com.angkorteam.fintech.client.enums.RepaymentOption;
import com.angkorteam.fintech.client.enums.ReschedulingType;
import com.angkorteam.fintech.client.renums.GLAccountType;
import com.angkorteam.fintech.meta.tenant.*;
import com.angkorteam.fintech.spring.StringGenerator;
import io.github.openunirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.FunctionType;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Table;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Function {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static void setupOffice(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, Date openingDate, List<String> offices, StringGenerator generator) throws ParseException, UnirestException {
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        long headOfficeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Head Office").execute()) {
            rows.next();
            headOfficeId = (long) rows.getRow().getValue(mOffice.ID);
        }
        for (String office : offices) {
            try (DataSet rows = appDataContext.query().from(mOffice).select(FunctionType.COUNT, mOffice.ID).where(mOffice.NAME).eq(office).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                boolean hasOffice = count > 0L;
                if (!hasOffice) {
                    PostOfficeRequest request = new PostOfficeRequest();
                    request.setName(office);
                    request.setOpeningDate(openingDate);
                    request.setParentId(headOfficeId);
                    request.setExternalId(generator.externalId());
                    client.officeCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupWorkingDay(FineractClient client, String tenant, String token) throws UnirestException {
        List<String> days = Arrays.asList("MO", "TU", "WE", "TH", "FR");
        String recurrence = "FREQ=WEEKLY;INTERVAL=1;BYDAY=" + StringUtils.join(days, ",");
        PutWorkingDaysRequest request = new PutWorkingDaysRequest();
        request.setRecurrence(recurrence);
        request.setExtendTermForDailyRepayments(false);
        request.setExtendTermForRepaymentsOnHolidays(false);
        request.setRepaymentRescheduleType(RepaymentOption.MoveToPreviousWorkingDay);
        client.workingDayUpdate(tenant, token, request);
    }

    public static void setupCurrency(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> currencies) throws UnirestException {
        PutCurrenciesRequest request = new PutCurrenciesRequest();
        request.getCurrencies().addAll(currencies);
        client.currencyUpdate(tenant, token, request);
    }

    public static void setupHoliday(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, long officeId, List<String> values) throws UnirestException, ParseException {
        MHoliday mHoliday = MHoliday.staticInitialize(appDataContext);

        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            try (DataSet rows = appDataContext.query().from(mHoliday).select(FunctionType.COUNT, mHoliday.ID).where(mHoliday.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count <= 0L) {
                    String dateForm = temp[1];
                    String dateTo = temp[2];
                    String rescheduled = temp[3];

                    PostHolidaysRequest request = new PostHolidaysRequest();
                    request.setName(name);
                    request.setDescription(name);
                    request.setReschedulingType(ReschedulingType.SpecifiedDate);
                    request.setOffices(Arrays.asList(new PostHolidaysRequest.Office(officeId)));
                    request.setFromDate(DATE_FORMAT.parse(dateForm));
                    request.setToDate(DATE_FORMAT.parse(dateTo));
                    request.setRepaymentsRescheduledTo(DATE_FORMAT.parse(rescheduled));

                    client.holidayCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupFinancialActivity(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values) throws UnirestException, ParseException {
//        for (String temps : values) {
//            String temp[] = StringUtils.split(temps, "=>");
//            FinancialActivityTypeEnum type = FinancialActivityTypeEnum.valueOf(temp[0]);
//            String account = temp[1];
//            String accountId = jdbcTemplate.queryForObject("select id from acc_gl_account where name = ?", String.class, account);
//            if (!jdbcTemplate.queryForObject("select count(*) from acc_gl_financial_activity_account where financial_activity_type = ? and gl_account_id = ?", Boolean.class, type.getLiteral(), accountId)) {
//                FinancialActivityBuilder builder = new FinancialActivityBuilder();
//                builder.withFinancialActivityId(type);
//                builder.withGlAccountId(accountId);
//                FinancialActivityHelper.create(session, builder.build());
//            }
//        }
    }

    public static void setupAccountingRule(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, long officeId, long debitId, long creditId, List<String> names) throws UnirestException {
        AccAccountingRule accAccountingRule = AccAccountingRule.staticInitialize(appDataContext);
        for (String name : names) {
            try (DataSet rows = appDataContext.query().from(accAccountingRule).select(FunctionType.COUNT, accAccountingRule.ID).where(accAccountingRule.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count <= 0) {
                    PostAccountingRulesRequest request = new PostAccountingRulesRequest();
                    request.setName(name);
                    request.setDescription(name);
                    request.setOfficeId(officeId);
                    request.setAccountToCredit(creditId);
                    request.setAccountToDebit(debitId);
                    client.accountingRuleCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupGLAccount(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values, StringGenerator generator) throws UnirestException {
        AccGLAccount accGLAccount = AccGLAccount.staticInitialize(appDataContext);
        MCodeValue mCodeValue = MCodeValue.staticInitialize(appDataContext);
        MCode mCode = MCode.staticInitialize(appDataContext);
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            try (DataSet rows = appDataContext.query().from(accGLAccount).select(FunctionType.COUNT, accGLAccount.ID).where(accGLAccount.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count <= 0L) {
                    String tag = temp[1];
                    try (DataSet rows1 = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq(tag).limit(1).execute()) {
                        rows1.next();
                        int tagId = (int) rows1.getRow().getValue(0);
                        PostGLAccountsRequest request = new PostGLAccountsRequest();
                        request.setName(name);
                        request.setDescription(name);
                        request.setGlCode(StringUtils.upperCase(generator.generate(5)));
                        request.setManualEntriesAllowed(true);
                        request.setTagId(tagId);
                        request.setUsage(AccountUsage.Detail);
                        if (Dropdown.AssetAccountTags.equals(tag)) {
                            request.setType(GLAccountType.Asset);
                        } else if (Dropdown.EquityAccountTags.equals(tag)) {
                            request.setType(GLAccountType.Equity);
                        } else if (Dropdown.LiabilityAccountTags.equals(tag)) {
                            request.setType(GLAccountType.Liability);
                        } else if (Dropdown.IncomeAccountTags.equals(tag)) {
                            request.setType(GLAccountType.Income);
                        } else if (Dropdown.ExpenseAccountTags.equals(tag)) {
                            request.setType(GLAccountType.Expense);
                        }
                        client.glAccountCreate(tenant, token, request);
                    }
                }
            }
        }
    }

    public static void setupTaxComponent(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values, StringGenerator generator) throws UnirestException {
        MTaxComponent mTaxComponent = MTaxComponent.staticInitialize(appDataContext);
        AccGLAccount accGLAccount = AccGLAccount.staticInitialize(appDataContext);
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            try (DataSet rows = appDataContext.query().from(mTaxComponent).select(FunctionType.COUNT, mTaxComponent.ID).where(mTaxComponent.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                boolean has = count > 0;
                if (!has) {
                    // taxComponentCreate
                    long creditAccountId = 0;
                    try (DataSet rows1 = appDataContext.query().from(accGLAccount).select(accGLAccount.ID).where(accGLAccount.NAME).eq(temp[3]).execute()) {
                        rows1.next();
                        creditAccountId = (long) rows1.getRow().getValue(accGLAccount.ID);
                    }

                    PostTaxesComponentsRequest request = new PostTaxesComponentsRequest();
                    request.setCreditAccountId(creditAccountId);
                    request.setCreditAccountType(GLAccountType.valueOf(temp[2]));
                    request.setPercentage(Double.valueOf(temp[1]));
                    request.setName(name);
                    request.setStartDate(new Date());
                    client.taxComponentCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupFloatingRate(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values, StringGenerator generator) throws UnirestException {
        MFloatingRate mFloatingRate = MFloatingRate.staticInitialize(appDataContext);
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            try (DataSet rows = appDataContext.query().from(mFloatingRate).select(FunctionType.COUNT, mFloatingRate.ID).where(mFloatingRate.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                boolean has = count > 0L;
                if (!has) {
                    boolean base = Boolean.valueOf(temp[1]);
                    boolean differential = Boolean.valueOf(temp[2]);
                    Double rate = Double.valueOf(temp[3]);
                    PostFloatingRatesRequest request = new PostFloatingRatesRequest();
                    request.setName(name);
                    request.setActive(true);
                    request.setBaseLendingRate(base);
                    request.getRatePeriods().add(new PostFloatingRatesRequest.RatePeriod(LocalDate.now().plusDays(1).toDate(), rate, differential));
                    client.floatingRateCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupTaxGroup(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values, StringGenerator generator) throws UnirestException {
        MTaxGroup mTaxGroup = MTaxGroup.staticInitialize(appDataContext);
        MTaxComponent mTaxComponent = MTaxComponent.staticInitialize(appDataContext);
        for (String temps : values) {
            String name = temps;
            try (DataSet rows = appDataContext.query().from(mTaxGroup).select(FunctionType.COUNT, mTaxGroup.ID).where(mTaxGroup.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                boolean has = count > 0L;
                if (!has) {
                    PostTaxesGroupRequest request = new PostTaxesGroupRequest();
                    request.setName(name);
                    List<Long> ids = new ArrayList<>();
                    try (DataSet rows1 = appDataContext.query().from(mTaxComponent).select(mTaxComponent.ID).execute()) {
                        while (rows1.next()) {
                            ids.add((long) rows1.getRow().getValue(mTaxComponent.ID));
                        }
                    }
                    for (Long id : ids) {
                        request.getTaxComponents().add(new PostTaxesGroupRequest.TaxComponent(0, id, DateTime.now().plusDays(1).toDate(), null));
                    }
                    client.taxGroupCreate(tenant, token, request);
                }
            }
        }
    }

    public static void setupSystemParameter(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> values) throws UnirestException {
        MCode mCode = MCode.staticInitialize(appDataContext);
        MCodeValue mCodeValue = MCodeValue.staticInitialize(appDataContext);
        for (String temps : values) {
            String temp[] = StringUtils.split(temps, "=>");
            String code = temp[0];
            String value = temp[1];
            try (DataSet rows = appDataContext.query().from(mCode).selectAll().where(mCode.CODE_NAME).eq(code).execute()) {
                rows.next();
                int codeId = (int) rows.getRow().getValue(mCode.ID);
                try (DataSet rows1 = appDataContext.query().from(mCodeValue).select(FunctionType.COUNT, mCodeValue.ID).where(mCodeValue.CODE_ID).eq(codeId).and(mCodeValue.CODE_VALUE).eq(value).execute()) {
                    rows1.next();
                    long count = (long) rows1.getRow().getValue(0);
                    boolean has = count > 0L;
                    if (!has) {
                        String description = temp[2];
                        PostCodeValueRequest request = new PostCodeValueRequest();
                        request.setActive(true);
                        request.setName(value);
                        request.setDescription(description);
                        client.codeValueCreate(tenant, token, codeId, request);
                    }
                }
            }
        }
    }

    public static void setupFund(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, List<String> funds, StringGenerator generator) throws UnirestException {
        MFund mFund = MFund.staticInitialize(appDataContext);
        for (String fund : funds) {
            try (DataSet rows = appDataContext.query().from(mFund).select(FunctionType.COUNT, mFund.ID).where(mFund.NAME).eq(fund).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                boolean hasFund = count > 0L;
                if (!hasFund) {
                    PostFundsRequest request = new PostFundsRequest();
                    request.setName(fund);
                    request.setExternalId(generator.externalId());
                    client.fundCreate(tenant, token, request);
                }
            }
        }
    }

    public static void triggerData(boolean fileout, String tenant, String token, JdbcDataContext appDataContext) throws SQLException, IOException {
        String delimiter = "";
        String newline = " ";

        if (fileout) {
            delimiter = "%%";
            newline = "\n";
        }

        if (!fileout) {
            try (DataSet rows = appDataContext.executeQuery("DROP TABLE IF EXISTS `tbl_audit`")) {
            }
            try (DataSet rows = appDataContext.executeQuery("CREATE TABLE `tbl_audit` (  `id` varchar(100) NOT NULL,  `log_date` datetime NOT NULL,  `log_event` varchar(20) NOT NULL,  `log_table` varchar(100) NOT NULL,  `log_script` TEXT,  PRIMARY KEY (`id`),  KEY `tbl_audit_001` (`log_date`),  KEY `tbl_audit_002` (`log_event`),  KEY `tbl_audit_003` (`log_table`))")) {
            }
            try (DataSet rows = appDataContext.executeQuery("DROP TABLE IF EXISTS `tbl_audit_value`")) {
            }
            try (DataSet rows = appDataContext.executeQuery("CREATE TABLE `tbl_audit_value` (  `id` varchar(100) NOT NULL,  `audit_id` varchar(100) DEFAULT NULL,  `field_name` varchar(255) DEFAULT NULL,  `before_value` text,  `after_value` text,  PRIMARY KEY (`id`),  KEY `tbl_audit_value_001` (`audit_id`),  KEY `tbl_audit_value_002` (`field_name`))")) {
            }
        }

        XRegisteredTable xRegisteredTable = XRegisteredTable.staticInitialize(appDataContext);

        List<String> tableNames = appDataContext.getDefaultSchema().getTableNames();
        List<String> customTable = new ArrayList<>();
        try (DataSet rows = appDataContext.query().from(xRegisteredTable).select(xRegisteredTable.REGISTERED_TABLE_NAME).execute()) {
            while (rows.next()) {
                customTable.add((String) rows.getRow().getValue(0));
            }
        }
        StringBuffer sql = new StringBuffer();
        sql.append("delimiter %%").append(newline);
        for (String tableName : tableNames) {
            if ("tbl_audit".equals(tableName) || "tbl_audit_value".equals(tableName)) {
                continue;
            }

            if (customTable.contains(tableName)) {
                continue;
            }

            System.out.println(tableName);

            Table table = appDataContext.getDefaultSchema().getTableByName(tableName);

            List<String> idFields = new ArrayList<>();
            for (Column column : table.getColumns()) {
                if (column.isPrimaryKey()) {
                    idFields.add(column.getName());
                }
            }

            boolean hasId = !idFields.isEmpty();

            if (!hasId) {
                System.out.println(tableName);
            }

            {
                StringBuffer delete = new StringBuffer();
                delete.append("DROP TRIGGER IF EXISTS " + tableName + "_d").append(delimiter).append(newline);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(delete.toString())) {
                        delete.delete(0, delete.length());
                    }
                }
                delete.append("CREATE TRIGGER `" + tableName + "_d`").append(newline);
                delete.append("BEFORE DELETE").append(newline);
                delete.append("  ON ").append(tableName).append(newline);
                delete.append("FOR").append(newline);
                delete.append("EACH ROW").append(newline);
                delete.append("  BEGIN").append(newline);
                delete.append("    DECLARE _id VARCHAR(100);").append(newline);
                delete.append("    DECLARE _log_script TEXT;").append(newline);
                delete.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String field = idFields.get(0);
                String logScript = "CONCAT('DELETE FROM " + tableName + " WHERE " + field + " = ''', OLD." + field + ", ''''";
                for (int i = 1; i < idFields.size(); i++) {
                    String f = idFields.get(i);
                    logScript = logScript + ", ' AND " + f + " = ''', OLD." + f + ", ''''";
                }
                logScript = logScript + ")";

                if (hasId) {
                    delete.append("    SELECT " + logScript + " INTO _log_script FROM dual;").append(newline);
                    delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'DELETE', '" + tableName + "', _log_script);").append(newline);
                } else {
                    delete.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'DELETE', '" + tableName + "', NULL);").append(newline);
                }
                for (Column column : table.getColumns()) {
                    String fieldName = column.getName();
                    delete.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NULL);").append(newline);
                }
                delete.append("  END").append(delimiter).append(newline);
                sql.append(delete);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(delete.toString())) {
                    }
                }
            }

            {
                StringBuffer insert = new StringBuffer();
                insert.append("DROP TRIGGER IF EXISTS " + tableName + "_i").append(delimiter).append(newline);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(insert.toString())) {
                        insert.delete(0, insert.length());
                    }
                }
                insert.append("CREATE TRIGGER `" + tableName + "_i`").append(newline);
                insert.append("AFTER INSERT").append(newline);
                insert.append("  ON ").append(tableName).append(newline);
                insert.append("FOR").append(newline);
                insert.append("EACH ROW").append(newline);
                insert.append("  BEGIN").append(newline);
                insert.append("    DECLARE _id VARCHAR(100);").append(newline);
                insert.append("    DECLARE _log_script TEXT;").append(newline);
                insert.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String logScript = "CONCAT('INSERT INTO " + tableName + "(";
                for (int i = 0; i < table.getColumns().size(); i++) {
                    String fieldName = table.getColumns().get(i).getName();
                    if (i + 1 == table.getColumns().size()) {
                        logScript = logScript + fieldName;
                    } else {
                        logScript = logScript + fieldName + ", ";
                    }
                }
                logScript = logScript + ") VALUES(', ";
                for (int i = 0; i < table.getColumns().size(); i++) {
                    String fieldName = table.getColumns().get(i).getName();
                    String value = "NEW." + fieldName;
                    if (i + 1 == table.getColumns().size()) {
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
                    insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'INSERT', '" + tableName + "', _log_script);").append(newline);
                } else {
                    insert.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'INSERT', '" + tableName + "', NULL);").append(newline);
                }
                for (int i = 0; i < table.getColumns().size(); i++) {
                    String fieldName = table.getColumns().get(i).getName();
                    insert.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, after_value, before_value) VALUES (uuid(), _id, '" + fieldName + "', NEW." + fieldName + ", NULL);").append(newline);
                }
                insert.append("  END").append(delimiter).append(newline);
                sql.append(insert);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(insert.toString())) {
                    }
                }
            }

            {
                StringBuffer update = new StringBuffer();
                update.append("DROP TRIGGER IF EXISTS " + tableName + "_u").append(delimiter).append(newline);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(update.toString())) {
                        update.delete(0, update.length());
                    }
                }
                update.append("CREATE TRIGGER `" + tableName + "_u`").append(newline);
                update.append("AFTER UPDATE").append(newline);
                update.append("  ON ").append(tableName).append(newline);
                update.append("FOR").append(newline);
                update.append("EACH ROW").append(newline);
                update.append("  BEGIN").append(newline);
                update.append("    DECLARE _id VARCHAR(100);").append(newline);
                update.append("    DECLARE _log_script TEXT;").append(newline);
                update.append("    SELECT uuid() INTO _id FROM dual;").append(newline);
                String logScript = "CONCAT('UPDATE " + tableName + " SET ";
                for (int i = 0; i < table.getColumns().size(); i++) {
                    String fieldName = table.getColumns().get(i).getName();
                    String value = "NEW." + fieldName;
                    String fieldValue = "IF(" + value + " IS NULL, 'NULL', CONCAT('''', " + value + ", ''''))";
                    if (i + 1 == table.getColumns().size()) {
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
                    update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'UPDATE', '" + tableName + "', _log_script);").append(newline);
                } else {
                    update.append("    INSERT INTO tbl_audit (id, log_date, log_event, log_table, log_script) VALUES (_id, now(), 'UPDATE', '" + tableName + "', NULL);").append(newline);
                }
                for (int i = 0; i < table.getColumns().size(); i++) {
                    String fieldName = table.getColumns().get(i).getName();
                    update.append("    INSERT INTO tbl_audit_value (id, audit_id, field_name, before_value, after_value) VALUES (uuid(), _id, '" + fieldName + "', OLD." + fieldName + ", NEW." + fieldName + ");").append(newline);
                }
                update.append("  END").append(delimiter).append(newline);
                sql.append(update);
                if (!fileout) {
                    try (DataSet rows = appDataContext.executeQuery(update.toString())) {
                    }
                }
            }
            sql.append("\n\n");
        }
        if (fileout) {
            FileUtils.write(new File("src/test/resources/trigger.sql"), sql.toString(), "UTF-8");
        }
    }

}
