package com.angkorteam.fintech.pages.charge;

import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargePayment;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class ClientChargeCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        this.wicket.startPage(ClientChargeCreatePage.class);

        FormTester form = this.wicket.newFormTester("form");

        String nameValue = "CLIENT_CHARGE_" + this.wicket.getGenerator().generate(20);

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("chargeTimeField", ChargeTime.SpecifiedDueDate.name());

        form.setValue("chargeCalculationField", ChargeCalculation.Flat.name());

        form.setValue("chargePaymentField", ChargePayment.RegularMode.name());

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        this.wicket.startPage(ClientChargeCreatePage.class);

        FormTester form = this.wicket.newFormTester("form");

        String nameValue = "CLIENT_CHARGE_" + this.wicket.getGenerator().generate(20);

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("chargeTimeField", ChargeTime.SpecifiedDueDate.name());

        form.setValue("chargeCalculationField", ChargeCalculation.Flat.name());

        form.setValue("chargePaymentField", ChargePayment.RegularMode.name());

        form.setValue("amountField", "100");

        form.setValue("penaltyField", true);

        form.setValue("activeField", true);

        form.setValue("incomeChargeField", "16");

        form.setValue("taxGroupField", "1");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

}
