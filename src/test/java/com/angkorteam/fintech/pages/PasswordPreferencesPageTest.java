package com.angkorteam.fintech.pages;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class PasswordPreferencesPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        this.wicket.startPage(PasswordPreferencesPage.class);

        try {
            this.wicket.executeAjaxEvent("filter-form:table:body:rows:1:cells:3:cell:1:link", "click");
        } catch (java.lang.IllegalArgumentException | java.lang.AssertionError e) {
        }

        try {
            this.wicket.executeAjaxEvent("filter-form:table:body:rows:2:cells:3:cell:1:link", "click");
        } catch (java.lang.IllegalArgumentException | java.lang.AssertionError e) {
        }

        this.wicket.assertRenderedPage(PasswordPreferencesPage.class);
    }

}
