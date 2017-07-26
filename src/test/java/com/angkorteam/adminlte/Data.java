package com.angkorteam.adminlte;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.dto.request.CodeValueBuilder;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.dto.request.OfficeBuilder;
import com.angkorteam.fintech.dto.request.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.angkorteam.fintech.dto.request.TellerBuilder;
import com.angkorteam.fintech.dto.request.WorkingDayBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.StaffHelper;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Data {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd MMM yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static void main(String[] args) throws UnirestException, ParseException {
        DataSource dataSource = null;
        IMifos session = null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        setupOffice(session, jdbcTemplate);
        setupWorkingDay(session, jdbcTemplate);
        setupCurrency(session, jdbcTemplate);
        setupFund(session, jdbcTemplate);
        setupTeller(session, jdbcTemplate);
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

    protected static void setupEmployee(IMifos session, JdbcTemplate jdbcTemplate)
            throws ParseException, UnirestException {
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

    protected static void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate)
            throws ParseException, UnirestException {
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

    protected static void setupOffice(IMifos session, JdbcTemplate jdbcTemplate)
            throws ParseException, UnirestException {
        for (Office office : Office.values()) {
            OfficeBuilder builder = new OfficeBuilder();
            builder.withExternalId(UUID.randomUUID().toString());
            builder.withOpeningDate(DATE_FORMAT.parse("2017-01-01"));
            builder.withName(office.getName());
            OfficeHelper.create(session, builder.build());
        }
    }

    protected static void setupTeller(IMifos session, JdbcTemplate jdbcTemplate)
            throws UnirestException, ParseException {
        for (Teller teller : Teller.values()) {
            TellerBuilder builder = new TellerBuilder();
            builder.withDescription(teller.getName());
            builder.withName(teller.getName());
            builder.withStatus(teller.getStatus());
            String officeId = jdbcTemplate.queryForObject("select id from m_office where name = ?", String.class,
                    teller.getOffice().getName());
            builder.withOfficeId(officeId);
            builder.withStartDate(DATE_FORMAT.parse("2019-12-31"));
            TellerHelper.create(session, builder.build());
        }
    }

    protected static void setupFund(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        for (Fund fund : Fund.values()) {
            FundBuilder builder = new FundBuilder();
            builder.withExternalId(UUID.randomUUID().toString());
            builder.withName(fund.getName());
            FundHelper.create(session, builder.build());
        }
    }

    protected static void setupCurrency(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        List<String> codes = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            codes.add(currency.getLiteral());
        }
        CurrencyHelper.update(session, codes);
    }

    protected static void setupPaymentType(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        for (PaymentType paymentType : PaymentType.values()) {
            PaymentTypeBuilder builder = new PaymentTypeBuilder();
            builder.withDescription(paymentType.getName());
            builder.withName(paymentType.getName());
            builder.withCashPayment(paymentType.isCash());
            builder.withPosition(paymentType.getPosition());
            PaymentTypeHelper.create(session, builder.build());
        }
    }

    protected static void setupWorkingDay(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
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

}
