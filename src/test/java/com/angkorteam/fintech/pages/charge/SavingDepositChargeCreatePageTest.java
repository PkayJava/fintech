package com.angkorteam.fintech.pages.charge;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

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

        form.setValue("chargeTimeField", ChargeTime.MonthlyFee);
        Select2SingleChoice<?> chargeTimeField = this.wicket.getComponentFromLastRenderedPage("form:chargeTimeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(chargeTimeField);

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
