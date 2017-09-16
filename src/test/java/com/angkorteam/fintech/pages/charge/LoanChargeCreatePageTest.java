package com.angkorteam.fintech.pages.charge;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeFrequency;
import com.angkorteam.fintech.dto.ChargePayment;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public class LoanChargeCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void chargeTimeFieldUpdate() {
        this.wicket.login();
        LoanChargeCreatePage page = this.wicket.startPage(LoanChargeCreatePage.class);
        Assert.assertNull(new PropertyModel<>(page, "chargeTimeValue").getObject());
        JUnitFormTester form = this.wicket.newFormTester("form");
        form.setValue("chargeTimeField", ChargeTime.OverdueFees);
        Component chargeTimeField = form.getForm().get("chargeTimeField");
        AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField, OnChangeAjaxBehavior.EVENT_CHANGE);
        this.wicket.executeBehavior(behavior);
        Assert.assertEquals(ChargeTime.OverdueFees.name(), ((Option) new PropertyModel<>(page, "chargeTimeValue").getObject()).getId());

    }

    @Test
    public void feeFrequencyUpdate() {
        this.wicket.login();
        LoanChargeCreatePage page = this.wicket.startPage(LoanChargeCreatePage.class);
        JUnitFormTester form = this.wicket.newFormTester("form");
        {
            form.setValue("chargeTimeField", ChargeTime.OverdueFees);
            Component chargeTimeField = form.getForm().get("chargeTimeField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }
        {
            Assert.assertEquals(false, new PropertyModel<>(page, "feeFrequencyValue").getObject() == null ? false : new PropertyModel<>(page, "feeFrequencyValue").getObject());
            form.setValue("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField", true);
            Component feeFrequencyField = form.getForm().get("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(feeFrequencyField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
            Assert.assertEquals(true, new PropertyModel<>(page, "feeFrequencyValue").getObject());
        }
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        this.wicket.startPage(LoanChargeCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        String nameValue = "LOAN_CHARGE_" + this.wicket.getStringGenerator().generate(20);

        {
            form.setValue("chargeTimeField", ChargeTime.InstallmentFee);
            Component chargeTimeField = form.getForm().get("chargeTimeField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }

        form.setValue("chargeTimeField", ChargeTime.InstallmentFee);

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("chargeCalculationBlock:chargeCalculationContainer:chargeCalculationField", ChargeCalculation.ApprovedAmount);

        form.setValue("chargePaymentField", ChargePayment.RegularMode);

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        this.wicket.startPage(LoanChargeCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        String nameValue = "LOAN_CHARGE_" + this.wicket.getStringGenerator().generate(20);

        {
            form.setValue("chargeTimeField", ChargeTime.OverdueFees);
            Component chargeTimeField = form.getForm().get("chargeTimeField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }

        {
            form.setValue("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField", true);
            Component feeFrequencyField = form.getForm().get("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField");
            AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(feeFrequencyField, OnChangeAjaxBehavior.EVENT_CHANGE);
            this.wicket.executeBehavior(behavior);
        }

        form.setValue("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField", true);

        form.setValue("chargeFrequencyBlock:chargeFrequencyContainer:chargeFrequencyField", ChargeFrequency.Day);

        form.setValue("frequencyIntervalBlock:frequencyIntervalContainer:frequencyIntervalField", "100");

        form.setValue("chargeTimeField", ChargeTime.OverdueFees);

        form.setValue("nameField", nameValue);

        form.setValue("penaltyField", true);

        form.setValue("activeField", true);

        form.setValue("currencyField", "USD");

        form.setValue("taxGroupField", "1");

        form.setValue("chargeCalculationBlock:chargeCalculationContainer:chargeCalculationField", ChargeCalculation.Flat);

        form.setValue("chargePaymentField", ChargePayment.RegularMode);

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

}
