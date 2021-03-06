package com.angkorteam.fintech.pages.role;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class RoleBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        RoleBrowsePage page = this.wicket.startPage(RoleBrowsePage.class);
        this.wicket.assertRenderedPage(RoleBrowsePage.class);
    }
}
