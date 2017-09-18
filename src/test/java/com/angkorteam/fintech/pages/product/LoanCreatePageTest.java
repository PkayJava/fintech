package com.angkorteam.fintech.pages.product;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoanCreatePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(LoanCreatePage.class);
        this.wicket.assertRenderedPage(LoanCreatePage.class);
    }
}
