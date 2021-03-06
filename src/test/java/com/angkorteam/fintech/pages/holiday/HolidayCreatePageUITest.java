package com.angkorteam.fintech.pages.holiday;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class HolidayCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        HolidayCreatePage page = this.wicket.startPage(HolidayCreatePage.class);
        this.wicket.assertRenderedPage(HolidayCreatePage.class);
    }
}
