package com.angkorteam.fintech.pages.product.recurring;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class RecurringBrowsePageUITest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(RecurringBrowsePage.class);
        this.wicket.assertRenderedPage(RecurringBrowsePage.class);
    }
}
