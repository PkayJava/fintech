package com.angkorteam.fintech.pages.role;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class RoleCreatePageUITest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        RoleCreatePage page = this.wicket.startPage(RoleCreatePage.class);
        this.wicket.assertRenderedPage(RoleCreatePage.class);
    }
}
