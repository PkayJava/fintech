package com.angkorteam.fintech.pages.product.loan;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoanBrowsePageUITest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        LoanBrowsePage page = this.wicket.startPage(LoanBrowsePage.class);
        this.wicket.assertRenderedPage(LoanBrowsePage.class);
    }
}
