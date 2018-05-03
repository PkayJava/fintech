package com.angkorteam.fintech.pages.teller;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class TellerBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        TellerBrowsePage page = this.wicket.startPage(TellerBrowsePage.class);
        this.wicket.assertRenderedPage(TellerBrowsePage.class);
    }
}
