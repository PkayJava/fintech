package com.angkorteam.fintech.pages.charge;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class SavingDepositChargeCreatePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        this.wicket.startPage(SavingDepositChargeCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        String nameValue = "SAVING_DEPOSIT_CHARGE_" + this.wicket.getStringGenerator().generate(20);

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("chargeTimeField", ChargeTime.SpecifiedDueDate);

        form.setValue("chargeCalculationField", ChargeCalculation.Flat);

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        this.wicket.startPage(SavingDepositChargeCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        {
            form.setValue("chargeTimeField", ChargeTime.MonthlyFee);
            Component chargeTimeField = form.getForm().get("chargeTimeField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }

        String nameValue = "SAVING_DEPOSIT_CHARGE_" + this.wicket.getStringGenerator().generate(20);

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("chargeTimeField", ChargeTime.MonthlyFee);

        form.setValue("chargeCalculationField", ChargeCalculation.Flat);

        form.setValue("dueDateBlock:dueDateContainer:dueDateField", DateFormatUtils.format(new Date(), "dd MMMM"));

        form.setValue("repeatEveryBlock:repeatEveryContainer:repeatEveryField", "1");

        form.setValue("amountField", "100");

        form.setValue("penaltyField", true);

        form.setValue("activeField", true);

        form.setValue("taxGroupField", "1");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }
}
