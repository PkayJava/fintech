package com.angkorteam.fintech.pages;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class WorkingDayPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntry() {
        this.wicket.login();

        this.wicket.startPage(WorkingDayPage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("mondayField", true);
        form.setValue("tuesdayField", true);
        form.setValue("wednesdayField", true);
        form.setValue("thursdayField", true);
        form.setValue("fridayField", true);
        form.setValue("saturdayField", false);
        form.setValue("sundayField", false);

        form.setValue("repaymentOptionField", RepaymentOption.SameDay);

        form.setValue("repaymentExtendTermField", false);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        this.wicket.assertRenderedPage(OrganizationDashboardPage.class);
    }

}
