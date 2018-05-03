package com.angkorteam.fintech.pages.tax;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class TaxComponentCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntry() {
        this.wicket.login();
        TaxComponentCreatePage page = this.wicket.startPage(TaxComponentCreatePage.class);
        this.wicket.assertRenderedPage(TaxComponentCreatePage.class);
    }
}
