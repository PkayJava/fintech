package com.angkorteam.fintech.pages.account;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class RuleSelectPageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        RuleSelectPage page = this.wicket.startPage(RuleSelectPage.class);

        this.wicket.assertRenderedPage(RuleSelectPage.class);
    }

}
