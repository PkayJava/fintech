package com.angkorteam.fintech.pages;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoginPageTest {

    private JUnitWicketTester tester;

    @Before
    public void before() {
        tester = new JUnitWicketTester();
    }

    @Test
    public void loginPage() {

        tester.startPage(LoginPage.class);

        tester.assertRenderedPage(LoginPage.class);

        FormTester formTester = tester.newFormTester("form");

        formTester.setValue("identifierField", "default");
        formTester.setValue("loginField", "mifos");
        formTester.setValue("passwordField", "password");
        formTester.submit("loginButton");

        Assert.assertNotNull("token is null", tester.getSession().getToken());

    }

}
