package com.angkorteam.fintech.pages;

import org.apache.wicket.Component;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoginPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void identifierField() {
        this.wicket.startPage(LoginPage.class);

        FormTester formTester = this.wicket.newFormTester("form");
        Component component = formTester.getForm().get("identifierField");

        Throwable throwable = null;
        try {
            this.wicket.executeListener(component);
        } catch (Throwable e) {
            throwable = e;
        }

        Assert.assertNull(throwable == null ? "" : throwable.getMessage(), throwable);
    }

    @Test
    public void loginPage() {

        this.wicket.login();

        Assert.assertNotNull("token is null", this.wicket.getSession().getToken());

    }

}
