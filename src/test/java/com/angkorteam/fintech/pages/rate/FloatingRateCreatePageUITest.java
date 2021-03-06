package com.angkorteam.fintech.pages.rate;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FloatingRateCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        FloatingRateCreatePage page = this.wicket.startPage(FloatingRateCreatePage.class);
        this.wicket.assertRenderedPage(FloatingRateCreatePage.class);
    }
}
