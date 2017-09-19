package com.angkorteam.fintech.pages.product;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.loan.Amortization;
import com.angkorteam.fintech.dto.loan.DayInMonth;
import com.angkorteam.fintech.dto.loan.DayInYear;
import com.angkorteam.fintech.dto.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.loan.InterestMethod;
import com.angkorteam.fintech.dto.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.loan.RepaidType;
import com.angkorteam.fintech.dto.loan.RepaymentStrategy;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class LoanCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "LOAN_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String currencyCodeValue = "USD";
        String detailFundValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_fund limit 1", String.class);

        this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);
        form.setValue("detailFundBlock:detailFundContainer:detailFundField", detailFundValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");

        // Terms
        form.setValue("termNumberOfRepaymentDefaultBlock:termNumberOfRepaymentDefaultContainer:termNumberOfRepaymentDefaultField", "1");
        form.setValue("termNominalInterestRateDefaultBlock:termNominalInterestRateDefaultContainer:termNominalInterestRateDefaultField", "1");
        form.setValue("termNominalInterestRateTypeBlock:termNominalInterestRateTypeContainer:termNominalInterestRateTypeField", NominalInterestRateScheduleType.Month);
        form.setValue("termRepaidEveryBlock:termRepaidEveryContainer:termRepaidEveryField", "1");
        form.setValue("termRepaidTypeBlock:termRepaidTypeContainer:termRepaidTypeField", RepaidType.Month);

        // Settings
        form.setValue("settingAmortizationBlock:settingAmortizationContainer:settingAmortizationField", Amortization.Installment);
        form.setValue("settingInterestMethodBlock:settingInterestMethodContainer:settingInterestMethodField", InterestMethod.Flat);
        form.setValue("settingInterestCalculationPeriodBlock:settingInterestCalculationPeriodContainer:settingInterestCalculationPeriodField", InterestCalculationPeriod.Daily);
        form.setValue("settingRepaymentStrategyBlock:settingRepaymentStrategyContainer:settingRepaymentStrategyField", RepaymentStrategy.Interest);
        form.setValue("settingDayInYearBlock:settingDayInYearContainer:settingDayInYearField", DayInYear.Actual);
        form.setValue("settingDayInMonthBlock:settingDayInMonthContainer:settingDayInMonthField", DayInMonth.Actual);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_product_loan where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_product_loan name = '" + detailProductNameValue + "'", actual);

    }

}
