package com.angkorteam.fintech.pages.account;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FinancialActivityCreatePageTest {
    
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        this.wicket.startPage(FinancialActivityCreatePage.class);

        this.wicket.assertRenderedPage(FinancialActivityCreatePage.class);
    }
}
