package com.angkorteam.fintech.pages;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class CurrencyConfigurationPageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        CurrencyConfigurationPage page = this.wicket.startPage(CurrencyConfigurationPage.class);
        this.wicket.assertRenderedPage(CurrencyConfigurationPage.class);
    }

}
