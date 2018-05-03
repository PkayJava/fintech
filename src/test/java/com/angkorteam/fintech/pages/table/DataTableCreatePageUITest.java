package com.angkorteam.fintech.pages.table;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class DataTableCreatePageUITest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        DataTableCreatePage page = this.wicket.startPage(DataTableCreatePage.class);
        this.wicket.assertRenderedPage(DataTableCreatePage.class);
    }
}
