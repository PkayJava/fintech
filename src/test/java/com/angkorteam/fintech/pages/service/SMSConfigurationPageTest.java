package com.angkorteam.fintech.pages.service;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class SMSConfigurationPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(SMSConfigurationPage.class);
        this.wicket.assertRenderedPage(SMSConfigurationPage.class);
    }
}
