package com.angkorteam.fintech.pages.code;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class CodeBrowsePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void pageVisit() {
        this.wicket.login();

        CodeBrowsePage page = this.wicket.startPage(CodeBrowsePage.class);

        this.wicket.assertRenderedPage(CodeBrowsePage.class);
    }

}
