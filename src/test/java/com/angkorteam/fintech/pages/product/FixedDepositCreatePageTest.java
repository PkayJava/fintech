package com.angkorteam.fintech.pages.product;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeType;
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

    @Ignore
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

        {
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", "USD");
            Component currencyCodeField = form.getForm().get("currencyCodeBlock:currencyCodeContainer:currencyCodeField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(currencyCodeField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }

        {
            Component chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeAddLink, "click");
            this.wicket.executeBehavior(behavior);
            
            // not sure popup on close is not call
            // http://localhost:8080/wicket/bookmarkable/LoanCreatePage?7-1.1-chargePopup&_=1505748654786
        }

        {
            String id = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = ? limit 1", String.class, "USD", ChargeType.SavingDeposit.getLiteral(), 0, 1);
            JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
            popupForm.setValue("chargeField", id);
            Component okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(okayButton, "click");
            this.wicket.executeBehavior(behavior);
        }

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
