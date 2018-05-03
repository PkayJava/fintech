package com.angkorteam.fintech.pages.staff;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class StaffBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        StaffBrowsePage page = this.wicket.startPage(StaffBrowsePage.class);
        this.wicket.assertRenderedPage(StaffBrowsePage.class);
    }
}
