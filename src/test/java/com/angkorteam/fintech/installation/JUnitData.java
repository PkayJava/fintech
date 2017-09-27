package com.angkorteam.fintech.installation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.request.TellerBuilder;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.LoginHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JUnitData implements IMifos {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private String token;

    private JUnitWicketTester wicket;

    public static final String CURRENCY = "USD";
    public static final String OFFICE = "JUNIT_OFFICE";
    public static final String FUND = "JUNIT_FUND";
    public static final String TELLER = "JUNIT_TELLER_300";
    public static final String PAYMENT = "JUNIT_PAYMENT";
    public static final String PAYMENT_CASH = "JUNIT_PAYMENT_CASH";

    private static final List<String> OFFICES = Lists.newArrayList();
    private static final List<String> CURRENCIES = Lists.newArrayList();
    private static final List<String> FUNDS = Lists.newArrayList();
    private static final List<Map<String, Object>> HOLIDAYS = Lists.newArrayList();

    static {
        OFFICES.add(OFFICE);
        CURRENCIES.add("USD");
        CURRENCIES.add("VND");
        CURRENCIES.add("KHR");
        CURRENCIES.add("MYR");
        CURRENCIES.add("SGD");
        CURRENCIES.add("THB");
        FUNDS.add(FUND);
        {
            Map<String, Object> day = Maps.newHashMap();
            day.put("name", "JUNIT_HOLIDAY_P_" + DateTime.now().getYear());
            day.put("from", DateTime.now().minusMonths(5).toDate());
            day.put("to", DateTime.now().minusMonths(5).plusDays(4).toDate());
            day.put("rescheduled", DateTime.now().minusMonths(5).plusDays(5).toDate());
            HOLIDAYS.add(day);
        }
        {
            Map<String, Object> day = Maps.newHashMap();
            day.put("name", "JUNIT_HOLIDAY_F_" + DateTime.now().getYear());
            day.put("from", DateTime.now().plusMonths(5).toDate());
            day.put("to", DateTime.now().plusMonths(5).plusDays(4).toDate());
            day.put("rescheduled", DateTime.now().plusMonths(5).plusDays(5).toDate());
            HOLIDAYS.add(day);
        }
    }

    @Before
    public void before() throws UnirestException {
        this.wicket = JUnit.getWicket();
        JsonNode tokenObject = LoginHelper.authenticate(Constants.AID, Constants.UID, Constants.PWD);
        this.token = tokenObject.getObject().getString("base64EncodedAuthenticationKey");
    }

    @Test
    public void initJUnitData() throws ParseException, UnirestException {
        setupOffice();
        Function.setupWorkingDay(this);
        setupCurrency();
        setupFund();
        setupTeller(this, this.wicket.getJdbcTemplate());
        setupPaymentType(this, this.wicket.getJdbcTemplate());
    }

    protected void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate, List<Map<String, Object>> days) throws UnirestException {
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

    protected void setupPaymentType(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        if (!jdbcTemplate.queryForObject("select count(*) from m_payment_type where value = ?", Boolean.class, PAYMENT)) {
            PaymentTypeBuilder builder = new PaymentTypeBuilder();
            builder.withDescription(PAYMENT);
            builder.withName(PAYMENT);
            builder.withCashPayment(false);
            builder.withPosition(1);
            PaymentTypeHelper.create(session, builder.build());
        }
        if (!jdbcTemplate.queryForObject("select count(*) from m_payment_type where value = ?", Boolean.class, PAYMENT_CASH)) {
            PaymentTypeBuilder builder = new PaymentTypeBuilder();
            builder.withDescription(PAYMENT_CASH);
            builder.withName(PAYMENT_CASH);
            builder.withCashPayment(true);
            builder.withPosition(1);
            PaymentTypeHelper.create(session, builder.build());
        }
    }

    protected void setupTeller(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException, ParseException {
        final String TELLER_INACTIVE = "JUNIT_TELLER_400";
        final String TELLER_CLOSED = "JUNIT_TELLER_600";
        String officeId = jdbcTemplate.queryForObject("select id from m_office where name = ?", String.class, OFFICE);
        Date startDate = DATE_FORMAT.parse("2017-01-01");
        if (!jdbcTemplate.queryForObject("select count(*) from m_tellers where name = ?", Boolean.class, TELLER)) {
            TellerBuilder builder = new TellerBuilder();
            builder.withDescription(TELLER);
            builder.withName(TELLER);
            builder.withStatus(TellerStatus.Active);
            builder.withOfficeId(officeId);
            builder.withStartDate(startDate);
            TellerHelper.create(session, builder.build());
        }
        if (!jdbcTemplate.queryForObject("select count(*) from m_tellers where name = ?", Boolean.class, TELLER_INACTIVE)) {
            TellerBuilder builder = new TellerBuilder();
            builder.withDescription(TELLER_INACTIVE);
            builder.withName(TELLER_INACTIVE);
            builder.withStatus(TellerStatus.Inactive);
            builder.withOfficeId(officeId);
            builder.withStartDate(startDate);
            TellerHelper.create(session, builder.build());
        }
        if (!jdbcTemplate.queryForObject("select count(*) from m_tellers where name = ?", Boolean.class, TELLER_CLOSED)) {
            TellerBuilder builder = new TellerBuilder();
            builder.withDescription(TELLER_CLOSED);
            builder.withName(TELLER_CLOSED);
            builder.withStatus(TellerStatus.Closed);
            builder.withOfficeId(officeId);
            builder.withStartDate(startDate);
            TellerHelper.create(session, builder.build());
        }
    }

    protected void setupFund() throws UnirestException {
        Function.setupFund(this, this.wicket.getJdbcTemplate(), FUNDS);
    }

    protected void setupOffice() throws ParseException, UnirestException {
        Date openingDate = DATE_FORMAT.parse("2017-01-01");
        Function.setupOffice(this, this.wicket.getJdbcTemplate(), openingDate, OFFICES);
    }

    protected void setupCurrency() throws UnirestException {
        Function.setupCurrency(this, this.wicket.getJdbcTemplate(), CURRENCIES);
    }

    @Override
    public String getIdentifier() {
        return Constants.AID;
    }

    @Override
    public String getToken() {
        return this.token;
    }

}
