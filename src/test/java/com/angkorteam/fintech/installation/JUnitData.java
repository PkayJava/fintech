package com.angkorteam.fintech.installation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.Dropdown;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.angkorteam.fintech.dto.request.TellerBuilder;
import com.angkorteam.fintech.helper.LoginHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.StaffHelper;
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
    public static final String EMPLOYEE = "JUNIT_EMP";

    private static final List<String> OFFICES = Lists.newArrayList();
    private static final List<String> CURRENCIES = Lists.newArrayList();
    private static final List<String> FUNDS = Lists.newArrayList();
    private static final List<Map<String, Object>> HOLIDAYS = Lists.newArrayList();
    private static final List<String> EMPLOYEES = Lists.newArrayList();

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

        EMPLOYEES.add(EMPLOYEE);
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
        Function.setupHoliday(this, this.wicket.getJdbcTemplate(), HOLIDAYS);
        setupEmployee(this, this.wicket.getJdbcTemplate());
        setupDropdown(this, this.wicket.getJdbcTemplate());

    }

    protected void setupDropdown(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Customer_Identifier, "Mr.", "Mr.");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Customer_Identifier, "Miss.", "Miss");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Customer_Identifier, "Mrs.", "Mrs.");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanCollateral, "LoanCollateral01", "LoanCollateral01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanCollateral, "LoanCollateral02", "LoanCollateral02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanPurpose, "LoanPurpose01", "LoanPurpose01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanPurpose, "LoanPurpose02", "LoanPurpose02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Gender, "M", "Male");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Gender, "F", "Female");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.YesNo, "Y", "Yes");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.YesNo, "N", "No");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GuarantorRelationship, "GuarantorRelationship01", "GuarantorRelationship01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GuarantorRelationship, "GuarantorRelationship02", "GuarantorRelationship02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.AssetAccountTags, "AssetAccountTags01", "AssetAccountTags01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.AssetAccountTags, "AssetAccountTags02", "AssetAccountTags02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LiabilityAccountTags, "LiabilityAccountTags01", "LiabilityAccountTags01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LiabilityAccountTags, "LiabilityAccountTags02", "LiabilityAccountTags02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.EquityAccountTags, "EquityAccountTags01", "EquityAccountTags01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.EquityAccountTags, "EquityAccountTags02", "EquityAccountTags02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.IncomeAccountTags, "IncomeAccountTags01", "IncomeAccountTags01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.IncomeAccountTags, "IncomeAccountTags02", "IncomeAccountTags02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ExpenseAccountTags, "ExpenseAccountTags01", "ExpenseAccountTags01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ExpenseAccountTags, "ExpenseAccountTags02", "ExpenseAccountTags02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GroupRole, "GroupRole01", "GroupRole01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GroupRole, "GroupRole02", "GroupRole02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientClosureReason, "ClientClosureReason01", "ClientClosureReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientClosureReason, "ClientClosureReason02", "ClientClosureReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GroupClosureReason, "GroupClosureReason01", "GroupClosureReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.GroupClosureReason, "GroupClosureReason02", "GroupClosureReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientType, "ClientType01", "ClientType01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientType, "ClientType02", "ClientType02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientClassification, "ClientClassification01", "ClientClassification01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientClassification, "ClientClassification02", "ClientClassification02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientSubStatus, "ClientSubStatus01", "ClientSubStatus01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientSubStatus, "ClientSubStatus02", "ClientSubStatus02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientRejectReason, "ClientRejectReason01", "ClientRejectReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientRejectReason, "ClientRejectReason02", "ClientRejectReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientWithdrawReason, "ClientWithdrawReason01", "ClientWithdrawReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.ClientWithdrawReason, "ClientWithdrawReason02", "ClientWithdrawReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.EntityToEntityAccessTypes, "EntityToEntityAccessTypes01", "EntityToEntityAccessTypes01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.EntityToEntityAccessTypes, "EntityToEntityAccessTypes02", "EntityToEntityAccessTypes02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.CenterClosureReason, "CenterClosureReason01", "CenterClosureReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.CenterClosureReason, "CenterClosureReason02", "CenterClosureReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanRescheduleReason, "LoanRescheduleReason01", "LoanRescheduleReason01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.LoanRescheduleReason, "LoanRescheduleReason02", "LoanRescheduleReason02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Constitution, "Constitution01", "Constitution01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Constitution, "Constitution02", "Constitution02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.MainBusinessLine, "MainBusinessLine01", "MainBusinessLine01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.MainBusinessLine, "MainBusinessLine02", "MainBusinessLine02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.WriteOffReasons, "WriteOffReasons01", "WriteOffReasons01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.WriteOffReasons, "WriteOffReasons02", "WriteOffReasons02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.State, "State01", "State01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.State, "State02", "State02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Country, "Country01", "Country01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Country, "Country02", "Country02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.AddressType, "AddressType01", "AddressType01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.AddressType, "AddressType02", "AddressType02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.MaritalStatus, "MaritalStatus01", "MaritalStatus01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.MaritalStatus, "MaritalStatus02", "MaritalStatus02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Profession, "Profession01", "Profession01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Profession, "Profession02", "Profession02");

        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Relationship, "Relationship01", "Relationship01");
        Function.setupSystemParameter(session, jdbcTemplate, Dropdown.Relationship, "Relationship02", "Relationship02");
    }

    protected void setupEmployee(IMifos session, JdbcTemplate jdbcTemplate) throws ParseException, UnirestException {
        for (String employee : EMPLOYEES) {
            if (!jdbcTemplate.queryForObject("select count(*) from m_staff where firstname = ?", Boolean.class, employee)) {
                StaffBuilder builder = new StaffBuilder();
                builder.withExternalId(StringUtils.upperCase(UUID.randomUUID().toString()));
                builder.withJoiningDate(DATE_FORMAT.parse("2017-01-01"));
                builder.withMobileNo(this.wicket.getNumberGenerator().generate(10));
                builder.withLoanOfficer(true);
                builder.withFirstName(employee);
                builder.withLastName(employee);
                StaffHelper.create(session, builder.build());
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
