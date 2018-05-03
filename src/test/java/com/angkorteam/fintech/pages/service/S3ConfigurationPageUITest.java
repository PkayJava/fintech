package com.angkorteam.fintech.pages.service;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class S3ConfigurationPageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        S3ConfigurationPage page = this.wicket.startPage(S3ConfigurationPage.class);
        this.wicket.assertRenderedPage(S3ConfigurationPage.class);
    }
}
