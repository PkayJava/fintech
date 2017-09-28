package com.angkorteam.fintech.installation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.dto.request.AccountRuleBuilder;
import com.angkorteam.fintech.dto.request.CodeValueBuilder;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.dto.request.GLAccountBuilder;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.dto.request.OfficeBuilder;
import com.angkorteam.fintech.dto.request.WorkingDayBuilder;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Function {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

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

    public static void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate, List<String> holidays) throws UnirestException, ParseException {
        for (String day : holidays) {
            int p1 = day.indexOf("=>");
            String name = day.substring(0, p1);
            if (!jdbcTemplate.queryForObject("select count(*) from m_holiday where name = ?", Boolean.class, name)) {
                int p2 = day.indexOf("=>", p1 + 2);
                String dateForm = day.substring(p1 + 2, p2);
                int p3 = day.indexOf("=>", p2 + 2);
                String dateTo = day.substring(p2 + 2, p3);
                String rescheduled = day.substring(p3 + 2);
                HolidayBuilder builder = new HolidayBuilder();
                builder.withName(name);
                builder.withFromDate(DATE_FORMAT.parse(dateForm));
                builder.withToDate(DATE_FORMAT.parse(dateTo));
                builder.withRepaymentsRescheduledTo(DATE_FORMAT.parse(rescheduled));
                HolidayHelper.create(session, builder.build());
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

    public static void setupGLAccount(IMifos session, JdbcTemplate jdbcTemplate, List<String> accounts, RandomStringGenerator stringGenerator) throws UnirestException {
        for (String account : accounts) {
            int p1 = account.indexOf("=>");
            String name = account.substring(0, p1);
            if (!jdbcTemplate.queryForObject("select count(*) from acc_gl_account where name = ?", Boolean.class, name)) {
                String tag = account.substring(p1 + 2);
                String tagId = jdbcTemplate.queryForObject("select m_code_value.id from m_code_value inner join m_code on m_code.id = m_code_value.code_id where m_code.code_name = ? limit 1", String.class, tag);
                GLAccountBuilder builder = new GLAccountBuilder();
                builder.withName(name);
                builder.withDescription(name);
                builder.withGlCode(StringUtils.upperCase(stringGenerator.generate(5)));
                builder.withManualEntriesAllowed(true);
                builder.withTagId(tagId);
                builder.withUsage(AccountUsage.Detail);
                GLAccountHelper.create(session, builder.build());
            }
        }

    }

    public static void setupSystemParameter(IMifos session, JdbcTemplate jdbcTemplate, String code, String value, String description) throws UnirestException {
        String codeId = jdbcTemplate.queryForObject("select id from m_code where code_name = ?", String.class, code);
        if (!jdbcTemplate.queryForObject("select count(*) from m_code_value where code_id = ? and code_value = ?", Boolean.class, codeId, value)) {
            CodeValueBuilder builder = new CodeValueBuilder();
            builder.withCodeId(codeId);
            builder.withName(value);
            builder.withDescription(description);
            builder.withActive(true);
            CodeHelper.createValue(session, builder.build());
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

}
