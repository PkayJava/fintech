package com.angkorteam.fintech.installation;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.request.CodeValueBuilder;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.LoginHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.StaffHelper;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Data {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static void main(String[] args) throws UnirestException, ParseException, ConfigurationException {

        File fintech = new File(java.lang.System.getProperty("user.home"), ".xml/fintech.properties.xml");
        XMLPropertiesConfiguration configuration = new XMLPropertiesConfiguration(fintech);

        String mifosUrl = configuration.getString("mifos.url");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(configuration.getString("app.jdbc.driver"));
        dataSource.setUsername(configuration.getString("app.jdbc.username"));
        dataSource.setPassword(configuration.getString("app.jdbc.password"));
        dataSource.setUrl(configuration.getString("app.jdbc.url"));

        final JsonNode object = LoginHelper.authenticate(mifosUrl, "default", "mifos", "password");
        IMifos session = new IMifos() {
            @Override
            public String getIdentifier() {
                return "default";
            }

            @Override
            public String getToken() {
                return (String) object.getObject().get("base64EncodedAuthenticationKey");
            }
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        setupPaymentType(session, jdbcTemplate);
        setupHoliday(session, jdbcTemplate);
        setupEmployee(session, jdbcTemplate);
        setupSystemParameters(session, jdbcTemplate);
    }

    protected static void setupSystemParameters(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        for (CodeValue value : CodeValue.values()) {
            CodeValueBuilder builder = new CodeValueBuilder();
            builder.withCodeId(value.getCode().getId());
            builder.withDescription(value.getDescription());
            builder.withName(value.getValue());
            builder.withActive(true);
            CodeHelper.createValue(session, builder.build());
        }

    }

    protected static void setupEmployee(IMifos session, JdbcTemplate jdbcTemplate) throws ParseException, UnirestException {
        for (Employee employee : Employee.values()) {
            StaffBuilder builder = new StaffBuilder();
            builder.withExternalId(UUID.randomUUID().toString());
            builder.withJoiningDate(DATE_FORMAT.parse("2017-01-01"));
            builder.withMobileNo(RandomStringUtils.randomNumeric(9));
            builder.withLoanOfficer(employee.isLoanOfficer());
            int index = employee.getName().indexOf(" ");
            builder.withFirstName(employee.getName().substring(0, index));
            builder.withLastName(employee.getName().substring(index + 1, employee.getName().length()));
            StaffHelper.create(session, builder.build());
        }
    }

    protected static void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate) throws ParseException, UnirestException {
        List<Integer> years = new ArrayList<>();
        years.add(2017);
        years.add(2018);
        for (Integer year : years) {
            for (Holiday holiday : Holiday.values()) {
                HolidayBuilder builder = new HolidayBuilder();
                Date fromDate = null;
                Date toDate = null;

                builder.withName(holiday.getName());
                if (holiday.isMovable()) {
                    if (year == 2017) {
                        if (holiday == Holiday.MeakBochea) {
                            fromDate = DATE_FORMAT_1.parse("11 Feb " + year);
                            toDate = DATE_FORMAT_1.parse("11 Feb " + year);
                        } else if (holiday == Holiday.VisakBochea) {
                            fromDate = DATE_FORMAT_1.parse("10 May " + year);
                            toDate = DATE_FORMAT_1.parse("10 May " + year);
                        } else if (holiday == Holiday.ChhorkPreahNangkorl) {
                            fromDate = DATE_FORMAT_1.parse("14 May " + year);
                            toDate = DATE_FORMAT_1.parse("14 May " + year);
                        } else if (holiday == Holiday.PchumBen) {
                            fromDate = DATE_FORMAT_1.parse("19 Sep " + year);
                            toDate = DATE_FORMAT_1.parse("21 Sep " + year);
                        } else if (holiday == Holiday.WaterFestival) {
                            fromDate = DATE_FORMAT_1.parse("02 Nov " + year);
                            toDate = DATE_FORMAT_1.parse("04 Nov " + year);
                        }
                    } else if (year == 2018) {
                        if (holiday == Holiday.MeakBochea) {
                            fromDate = DATE_FORMAT_1.parse("01 Mar " + year);
                            toDate = DATE_FORMAT_1.parse("01 Mar " + year);
                        } else if (holiday == Holiday.VisakBochea) {
                            fromDate = DATE_FORMAT_1.parse("29 May " + year);
                            toDate = DATE_FORMAT_1.parse("29 May " + year);
                        } else if (holiday == Holiday.ChhorkPreahNangkorl) {
                            fromDate = DATE_FORMAT_1.parse("17 May " + year);
                            toDate = DATE_FORMAT_1.parse("17 May " + year);
                        } else if (holiday == Holiday.PchumBen) {
                            fromDate = DATE_FORMAT_1.parse("08 Oct " + year);
                            toDate = DATE_FORMAT_1.parse("10 Oct " + year);
                        } else if (holiday == Holiday.WaterFestival) {
                            fromDate = DATE_FORMAT_1.parse("22 Nov " + year);
                            toDate = DATE_FORMAT_1.parse("24 Nov " + year);
                        }
                    }
                } else {
                    fromDate = DATE_FORMAT_1.parse(holiday.getStartDate() + " " + year);
                    toDate = DATE_FORMAT_1.parse(holiday.getEndDate() + " " + year);
                }
                LocalDate temp = LocalDate.parse(holiday.getStartDate() + " " + year, DATE_TIME_FORMATTER);
                temp = temp.minusDays(1);
                builder.withFromDate(fromDate);
                builder.withToDate(toDate);
                builder.withRepaymentsRescheduledTo(DATE_FORMAT_1.parse(temp.format(DATE_TIME_FORMATTER)));
                HolidayHelper.create(session, builder.build());
            }
        }
    }



}
