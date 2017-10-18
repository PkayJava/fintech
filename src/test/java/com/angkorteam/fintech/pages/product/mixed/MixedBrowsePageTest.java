package com.angkorteam.fintech.pages.product.mixed;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.fintech.pages.product.mixed.MixedBrowsePage;

public class MixedBrowsePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        this.wicket.startPage(MixedBrowsePage.class);
        this.wicket.assertRenderedPage(MixedBrowsePage.class);
    }
}
