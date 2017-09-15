package com.angkorteam.fintech.pages.charge;

import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class ShareChargeCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        this.wicket.startPage(ShareChargeCreatePage.class);

        FormTester form = this.wicket.newFormTester("form");

        String nameValue = "SHARE_CHARGE_" + this.wicket.getGenerator().generate(20);

        form.setValue("chargeTimeField", ChargeTime.ShareRedeem.name());

        form.setValue("chargeCalculationField", ChargeCalculation.Flat.name());

        form.setValue("nameField", nameValue);

        form.setValue("currencyField", "USD");

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        this.wicket.startPage(ShareChargeCreatePage.class);

        FormTester form = this.wicket.newFormTester("form");

        String nameValue = "SHARE_CHARGE_" + this.wicket.getGenerator().generate(20);

        form.setValue("chargeTimeField", ChargeTime.SharePurchase.name());

        form.setValue("chargeCalculationField", ChargeCalculation.ApprovedAmount.name());

        form.setValue("nameField", nameValue);

        form.setValue("activeField", true);

        form.setValue("currencyField", "USD");

        form.setValue("taxGroupField", "1");

        form.setValue("amountField", "100");

        form.submit("saveButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_charge where name = ?", nameValue);
        Assert.assertNotNull("expected to have m_charge name = '" + nameValue + "'", actual);
    }
}
