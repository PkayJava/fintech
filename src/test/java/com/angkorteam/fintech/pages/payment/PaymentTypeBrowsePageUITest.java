package com.angkorteam.fintech.pages.payment;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class PaymentTypeBrowsePageUITest {
    
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        PaymentTypeBrowsePage page = this.wicket.startPage(PaymentTypeBrowsePage.class);
        this.wicket.assertRenderedPage(PaymentTypeBrowsePage.class);
    }
}
