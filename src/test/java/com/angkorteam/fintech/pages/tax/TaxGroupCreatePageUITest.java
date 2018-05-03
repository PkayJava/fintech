package com.angkorteam.fintech.pages.tax;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class TaxGroupCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        TaxGroupCreatePage page = this.wicket.startPage(TaxGroupCreatePage.class);
        this.wicket.assertRenderedPage(TaxGroupCreatePage.class);
    }
}
