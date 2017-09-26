package com.angkorteam.fintech.installation;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.request.TellerBuilder;
import com.angkorteam.fintech.helper.LoginHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SampleData implements IMifos {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private String token;

    private JUnitWicketTester wicket;

    private static final List<String> OFFICES = Lists.newArrayList();
    private static final List<String> CURRENCIES = Lists.newArrayList();
    private static final List<String> FUNDS = Lists.newArrayList();
    private static final Map<String, Boolean> PAYMENTS = Maps.newHashMap();

    static {
        OFFICES.add("ការិយាល័យ ខេត្តបន្ទាយមានជ័យ");
        OFFICES.add("ការិយាល័យ ខេត្តបាត់ដំបង");
        OFFICES.add("ការិយាល័យ ខេត្តកំពង់ចាម");
        OFFICES.add("ការិយាល័យ ខេត្តកំពង់ឆ្នាំង");
        OFFICES.add("ការិយាល័យ ខេត្តកំពង់ស្ពឺ");
        OFFICES.add("ការិយាល័យ ខេត្តកំពង់ធំ");
        OFFICES.add("ការិយាល័យ ខេត្តកំពត");
        OFFICES.add("ការិយាល័យ ខេត្តកណ្តាល");
        OFFICES.add("ការិយាល័យ ខេត្តកោះកុង");
        OFFICES.add("ការិយាល័យ ខេត្តក្រចេះ");
        OFFICES.add("ការិយាល័យ ខេត្តមណ្ឌលគីរី");
        OFFICES.add("ការិយាល័យ ខេត្តឧត្តរមានជ័យ");
        OFFICES.add("ការិយាល័យ ខេត្តព្រះវិហារ");
        OFFICES.add("ការិយាល័យ ខេត្តពោធិ៍សាត់");
        OFFICES.add("ការិយាល័យ ខេត្តព្រៃវែង");
        OFFICES.add("ការិយាល័យ ខេត្តរតនគីរី");
        OFFICES.add("ការិយាល័យ ខេត្តសៀមរាប");
        OFFICES.add("ការិយាល័យ ខេត្តស្ទឹងត្រែង");
        OFFICES.add("ការិយាល័យ ខេត្តស្វាយរៀង");
        OFFICES.add("ការិយាល័យ ខេត្តតាកែវ");
        OFFICES.add("ការិយាល័យ ក្រុងកែប");
        OFFICES.add("ការិយាល័យ ខេត្តបៃលិន");
        OFFICES.add("ការិយាល័យ ក្រុងភ្នំពេញ");
        OFFICES.add("ការិយាល័យ ខេត្តកំពង់សោម");
        OFFICES.add("ការិយាល័យ ខេត្តត្បូងឃ្មុំ");

        CURRENCIES.add("USD");
        CURRENCIES.add("VND");
        CURRENCIES.add("KHR");
        CURRENCIES.add("MYR");
        CURRENCIES.add("SGD");
        CURRENCIES.add("THB");

        FUNDS.add("Asian Fund");
        FUNDS.add("World Bank Fund");
        FUNDS.add("Agriculture Fund");
        FUNDS.add("Startup Fund");
        FUNDS.add("Small Loan Fund");
        FUNDS.add("Education Fund");
        FUNDS.add("Housing Fund");

        PAYMENTS.put("Cash", true);
        PAYMENTS.put("VISA", false);
        PAYMENTS.put("Mastercard", false);
        PAYMENTS.put("Cheque", false);
        PAYMENTS.put("American Express", false);
        PAYMENTS.put("JCB", false);
    }

    @Before
    public void before() throws UnirestException {
        this.wicket = JUnit.getWicket();
        JsonNode tokenObject = LoginHelper.authenticate(Constants.AID, Constants.UID, Constants.PWD);
        this.token = tokenObject.getObject().getString("base64EncodedAuthenticationKey");
    }

    @Test
    public void initSampleData() throws ParseException, UnirestException {
        setupOffice();
        Function.setupWorkingDay(this);
        setupCurrency();
        setupFund();
        setupTeller(this, this.wicket.getJdbcTemplate(), this.wicket.getJdbcNamed());
        setupPaymentType(this, this.wicket.getJdbcTemplate());
    }

    protected void setupPaymentType(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        for (Entry<String, Boolean> payment : PAYMENTS.entrySet()) {
            if (!jdbcTemplate.queryForObject("select count(*) from m_payment_type where value = ?", Boolean.class, payment.getKey())) {
                PaymentTypeBuilder builder = new PaymentTypeBuilder();
                builder.withDescription(payment.getKey());
                builder.withName(payment.getKey());
                builder.withCashPayment(payment.getValue());
                builder.withPosition(1);
                PaymentTypeHelper.create(session, builder.build());
            }
        }
    }

    protected void setupTeller(IMifos session, JdbcTemplate jdbcTemplate, JdbcNamed jdbcNamed) throws UnirestException, ParseException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", OFFICES);
        List<String> offices = jdbcNamed.queryForList("select id from m_office where name in (:name)", params, String.class);
        NumberFormat format = new DecimalFormat("000");
        Date startDate = DATE_FORMAT.parse("2017-01-01");
        for (int i = 1; i <= 400; i++) {
            String name = "TELLER " + format.format(i);
            if (!jdbcTemplate.queryForObject("select count(*) from m_tellers where name = ?", Boolean.class, name)) {
                String officeId = offices.get(RandomUtils.nextInt(0, offices.size()));
                TellerBuilder builder = new TellerBuilder();
                builder.withDescription(name);
                builder.withName(name);
                builder.withStatus(TellerStatus.Active);
                builder.withOfficeId(officeId);
                builder.withStartDate(startDate);
                TellerHelper.create(session, builder.build());
            }
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
