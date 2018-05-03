package com.angkorteam.fintech.pages.holiday;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class HolidayBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        HolidayBrowsePage page = this.wicket.startPage(HolidayBrowsePage.class);
        this.wicket.assertRenderedPage(HolidayBrowsePage.class);
    }
}
