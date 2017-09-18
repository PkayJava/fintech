package com.angkorteam.fintech.pages.account;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class JournalPostPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        this.wicket.startPage(JournalPostPage.class);

        this.wicket.assertRenderedPage(JournalPostPage.class);
    }

}
