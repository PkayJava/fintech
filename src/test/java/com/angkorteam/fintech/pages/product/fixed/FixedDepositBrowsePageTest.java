package com.angkorteam.fintech.pages.product.fixed;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FixedDepositBrowsePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(FixedBrowsePage.class);
        this.wicket.assertRenderedPage(FixedBrowsePage.class);
    }
}
