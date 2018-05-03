package com.angkorteam.fintech.pages.office;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class OfficeHierarchyPageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        OfficeHierarchyPage page = this.wicket.startPage(OfficeHierarchyPage.class);
        this.wicket.assertRenderedPage(OfficeHierarchyPage.class);
    }
}
