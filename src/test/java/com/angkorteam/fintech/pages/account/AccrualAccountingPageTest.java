package com.angkorteam.fintech.pages.account;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class AccrualAccountingPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntry() {
        this.wicket.login();

        this.wicket.startPage(AccrualAccountingPage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        DateTime dateTime = DateTime.now().minusDays(2);
        form.setValue("tillDateField", DateFormatUtils.format(dateTime.toDate(), "dd/MM/yyyy"));

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();
    }

    @Test
    public void dataEntry1() {
        this.wicket.login();

        this.wicket.startPage(AccrualAccountingPage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        DateTime dateTime = DateTime.now().plusDays(2);
        form.setValue("tillDateField", DateFormatUtils.format(dateTime.toDate(), "dd/MM/yyyy"));

        form.submit("saveButton");

        this.wicket.assertErrorMessage();
    }

}
