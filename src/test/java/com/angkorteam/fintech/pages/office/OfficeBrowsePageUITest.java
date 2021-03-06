package com.angkorteam.fintech.pages.office;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class OfficeBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        OfficeBrowsePage page = this.wicket.startPage(OfficeBrowsePage.class);
        this.wicket.assertRenderedPage(OfficeBrowsePage.class);
    }
}
