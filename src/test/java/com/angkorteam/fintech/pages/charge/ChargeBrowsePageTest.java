package com.angkorteam.fintech.pages.charge;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class ChargeBrowsePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void pageVisit() {
        this.wicket.login();

        this.wicket.startPage(ChargeBrowsePage.class);

        this.wicket.assertRenderedPage(ChargeBrowsePage.class);
    }

}
