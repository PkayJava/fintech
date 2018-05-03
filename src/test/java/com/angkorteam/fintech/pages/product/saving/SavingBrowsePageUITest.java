package com.angkorteam.fintech.pages.product.saving;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class SavingBrowsePageUITest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(SavingBrowsePage.class);
        this.wicket.assertRenderedPage(SavingBrowsePage.class);
    }
}
