package com.angkorteam.fintech.pages.account;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FinancialActivityBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        FinancialActivityBrowsePage page = this.wicket.startPage(FinancialActivityBrowsePage.class);

        this.wicket.assertRenderedPage(FinancialActivityBrowsePage.class);
    }

}
