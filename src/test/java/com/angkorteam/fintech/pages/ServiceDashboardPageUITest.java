package com.angkorteam.fintech.pages;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class ServiceDashboardPageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        ServiceDashboardPage page = this.wicket.startPage(ServiceDashboardPage.class);

        this.wicket.assertRenderedPage(ServiceDashboardPage.class);
    }

}
