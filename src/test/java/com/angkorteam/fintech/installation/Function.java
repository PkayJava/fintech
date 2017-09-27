package com.angkorteam.fintech.installation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.dto.request.CodeValueBuilder;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.dto.request.OfficeBuilder;
import com.angkorteam.fintech.dto.request.WorkingDayBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Function {

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

    public static void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate, List<Map<String, Object>> days) throws UnirestException {
        for (Map<String, Object> day : days) {
            String name = (String) day.get("name");
            if (!jdbcTemplate.queryForObject("select count(*) from m_holiday where name = ?", Boolean.class, name)) {
                HolidayBuilder builder = new HolidayBuilder();
                builder.withName(name);
                builder.withFromDate((Date) day.get("from"));
                builder.withToDate((Date) day.get("to"));
                builder.withRepaymentsRescheduledTo((Date) day.get("rescheduled"));
                HolidayHelper.create(session, builder.build());
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
