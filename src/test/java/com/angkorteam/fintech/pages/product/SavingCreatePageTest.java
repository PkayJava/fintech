package com.angkorteam.fintech.pages.product;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.saving.DayInYear;
import com.angkorteam.fintech.dto.saving.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.saving.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.saving.InterestPostingPeriod;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class SavingCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "SIVING_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;

        double termNominalAnnualInterestValue = 1.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        DayInYear termDaysInYearValue = DayInYear.D365;

        int accountingValue = 0;

        this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        form.setValue("termNominalAnnualInterestBlock:termNominalAnnualInterestContainer:termNominalAnnualInterestField", termNominalAnnualInterestValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDaysInYearBlock:termDaysInYearContainer:termDaysInYearField", termDaysInYearValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();
    }
}
