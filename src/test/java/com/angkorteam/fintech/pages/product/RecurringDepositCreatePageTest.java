package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.DepositType;
import com.angkorteam.fintech.dto.fixed.ApplyPenalOn;
import com.angkorteam.fintech.dto.fixed.DayInYear;
import com.angkorteam.fintech.dto.fixed.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.fixed.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.fixed.InterestPostingPeriod;
import com.angkorteam.fintech.dto.fixed.LockInPeriod;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class RecurringDepositCreatePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "RECURRING_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;

        double termDefaultDepositAmountValue = 1000.99;
        double termMinimumDepositAmountValue = 1;
        double termMaximumDepositAmountValue = 10000000000.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        DayInYear termDayInYearValue = DayInYear.D365;

        int settingLockInPeriodValue = 1;
        LockInPeriod settingLockInTypeValue = LockInPeriod.Month;
        int settingMinimumDepositTermValue = 12;
        LockInPeriod settingMinimumDepositTypeValue = LockInPeriod.Month;
        int settingInMultiplesOfValue = 1;
        LockInPeriod settingInMultiplesTypeValue = LockInPeriod.Month;
        int settingMaximumDepositTermValue = 240;
        LockInPeriod settingMaximumDepositTypeValue = LockInPeriod.Month;
        double settingApplyPenalInterestValue = 1.99;
        ApplyPenalOn settingApplyPenalOnValue = ApplyPenalOn.WholeTerm;
        double settingBalanceRequiredForInterestCalculationValue = 10.99;

        Date interestRateValidFromDateValue = DateTime.now().plusDays(1).toDate();
        Date interestRateValidEndDateValue = DateTime.now().plusMonths(12).toDate();

        this.wicket.startPage(RecurringDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", termDefaultDepositAmountValue);
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", termMinimumDepositAmountValue);
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", termMaximumDepositAmountValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", termDayInYearValue);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", settingMinimumDepositTermValue);
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", settingMinimumDepositTypeValue);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", settingInMultiplesOfValue);
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", settingInMultiplesTypeValue);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", settingMaximumDepositTermValue);
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", settingMaximumDepositTypeValue);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", settingApplyPenalInterestValue);
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", settingApplyPenalOnValue);
        form.setValue("settingBalanceRequiredForInterestCalculationBlock:settingBalanceRequiredForInterestCalculationContainer:settingBalanceRequiredForInterestCalculationField", settingBalanceRequiredForInterestCalculationValue);

        // Interest Rate Chart

        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(interestRateValidFromDateValue, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(interestRateValidEndDateValue, "dd/MM/yyyy"));

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Recurring.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }
}
