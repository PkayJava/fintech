package com.angkorteam.fintech.installation;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JUnitData implements IMifos {

    private String token;

    private JUnitWicketTester wicket;

    public static final String CURRENCY = "USD";

    public static final String OFFICE = "JUnit Office";

    public static final String FUND = "JUnit Fund";

    public static final String TELLER = "JUnit Teller 300";

    public static final String PAYMENT = "JUnit Payment";
    public static final String PAYMENT_CASH = "JUnit Payment Cash";

    public static final String EMPLOYEE = "JUnit Emp";

    public static final String ACCOUNT_ASSET_DEBIT = "JUnit Account Asset/Debit";
    public static final String ACCOUNT_ASSET_CREDIT = "JUnit Account Asset/Credit";
    public static final String ACCOUNT_LIABILITY_DEBIT = "JUnit Account Liability/Debit";
    public static final String ACCOUNT_LIABILITY_CREDIT = "JUnit Account Liability/Credit";
    public static final String ACCOUNT_EQUITY_DEBIT = "JUnit Account Equity/Debit";
    public static final String ACCOUNT_EQUITY_CREDIT = "JUnit Account Equity/Credit";
    public static final String ACCOUNT_INCOME_DEBIT = "JUnit Account Income/Debit";
    public static final String ACCOUNT_INCOME_CREDIT = "JUnit Account Income/Credit";
    public static final String ACCOUNT_EXPENSE_DEBIT = "JUnit Account Expense/Debit";
    public static final String ACCOUNT_EXPENSE_CREDIT = "JUnit Account Expense/Credit";

    public static final String ACCOUNT_RULE = "JUnit AccountRule";

    private static final List<String> OFFICES = Lists.newArrayList();
    private static final List<String> CURRENCIES = Lists.newArrayList();
    private static final List<String> FUNDS = Lists.newArrayList();
    private static final List<String> HOLIDAYS = Lists.newArrayList();
    private static final List<String> EMPLOYEES = Lists.newArrayList();
    private static final List<String> ACCOUNTS = Lists.newArrayList();
    private static final List<String> ACCOUNT_RULES = Lists.newArrayList();

    static {
        OFFICES.add(OFFICE);
        CURRENCIES.add("USD");
        CURRENCIES.add("VND");
        CURRENCIES.add("KHR");
        CURRENCIES.add("MYR");
        CURRENCIES.add("SGD");
        CURRENCIES.add("THB");
        FUNDS.add(FUND);

        String year = String.valueOf(DateTime.now().getYear());
        // NAME->FROM->TO->RESCHEDULED
        HOLIDAYS.add("JUNIT_HOLIDAY_P_" + year + "=>" + DateTime.now().minusMonths(5).toString("yyyy-MM-dd") + "=>" + DateTime.now().minusMonths(5).plusDays(4).toString("yyyy-MM-dd") + "=>" + DateTime.now().minusMonths(5).plusDays(5).toString("yyyy-MM-dd"));
        HOLIDAYS.add("JUNIT_HOLIDAY_P_" + year + "=>" + DateTime.now().plusMonths(5).toString("yyyy-MM-dd") + "=>" + DateTime.now().plusMonths(5).plusDays(4).toString("yyyy-MM-dd") + "=>" + DateTime.now().plusMonths(5).plusDays(5).toString("yyyy-MM-dd"));

        EMPLOYEES.add(EMPLOYEE);

        // NAME=>TAG
        ACCOUNTS.add(ACCOUNT_ASSET_DEBIT + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add(ACCOUNT_ASSET_CREDIT + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add(ACCOUNT_LIABILITY_DEBIT + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add(ACCOUNT_LIABILITY_CREDIT + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add(ACCOUNT_EQUITY_DEBIT + "=>" + Dropdown.EquityAccountTags);
        ACCOUNTS.add(ACCOUNT_EQUITY_CREDIT + "=>" + Dropdown.EquityAccountTags);
        ACCOUNTS.add(ACCOUNT_INCOME_DEBIT + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add(ACCOUNT_INCOME_CREDIT + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add(ACCOUNT_EXPENSE_DEBIT + "=>" + Dropdown.ExpenseAccountTags);
        ACCOUNTS.add(ACCOUNT_EXPENSE_CREDIT + "=>" + Dropdown.ExpenseAccountTags);

        ACCOUNT_RULES.add(ACCOUNT_RULE);
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
        Function.setupGLAccount(this, this.wicket.getJdbcTemplate(), ACCOUNTS, this.wicket.getStringGenerator());
    }

    protected void setupAccountingRule() throws UnirestException {
        String officeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_office where name = ?", String.class, OFFICE);
        String creditId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where name = ?", String.class, ACCOUNT_ASSET_CREDIT);
        String debitId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where name = ?", String.class, ACCOUNT_ASSET_DEBIT);
        Function.setupAccountingRule(this, this.wicket.getJdbcTemplate(), officeId, debitId, creditId, ACCOUNT_RULES);
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
                builder.withJoiningDate(Function.DATE_FORMAT.parse("2017-01-01"));
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
        Date startDate = Function.DATE_FORMAT.parse("2017-01-01");
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
        Date openingDate = Function.DATE_FORMAT.parse("2017-01-01");
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
