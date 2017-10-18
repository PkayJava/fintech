package com.angkorteam.fintech.pages.product.recurring;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.fintech.pages.product.recurring.RecurringDepositBrowsePage;

public class RecurringDepositBrowsePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(RecurringDepositBrowsePage.class);
        this.wicket.assertRenderedPage(RecurringDepositBrowsePage.class);
    }
}