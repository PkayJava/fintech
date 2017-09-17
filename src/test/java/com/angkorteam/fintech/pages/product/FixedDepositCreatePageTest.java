package com.angkorteam.fintech.pages.product;

import java.util.Map;

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

public class FixedDepositCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", "USD");
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", "1000.99");
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", "1");
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", "10000000000.99");
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", InterestCompoundingPeriod.Annually);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", InterestPostingPeriod.Annually);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", InterestCalculatedUsing.AverageDailyBalance);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", DayInYear.D365);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", "1");
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", LockInPeriod.Month);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", "12");
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", "1");
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", LockInPeriod.Month);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", "240");
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", "1.99");
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", ApplyPenalOn.WholeTerm);
        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", "USD");
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", "1000.99");
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", "1");
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", "10000000000.99");
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", InterestCompoundingPeriod.Annually);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", InterestPostingPeriod.Annually);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", InterestCalculatedUsing.AverageDailyBalance);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", DayInYear.D365);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", "1");
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", LockInPeriod.Month);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", "12");
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", "1");
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", LockInPeriod.Month);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", "240");
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", "1.99");
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", ApplyPenalOn.WholeTerm);
        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);
    }

}
