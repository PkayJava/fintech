package com.angkorteam.fintech.pages.charge;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class SavingDepositChargeCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void pageVisit() {
        this.wicket.login();

        SavingDepositChargeCreatePage page = this.wicket.startPage(SavingDepositChargeCreatePage.class);

        this.wicket.assertRenderedPage(SavingDepositChargeCreatePage.class);
    }

}
