package com.angkorteam.fintech.pages;

import org.apache.wicket.Component;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoginPageTest {

    private JUnitWicketTester tester;

    @Before
    public void before() {
        this.tester = new JUnitWicketTester();
    }

    @Test
    public void identifierField() {
        this.tester.startPage(LoginPage.class);

        FormTester formTester = this.tester.newFormTester("form");
        Component component = formTester.getForm().get("identifierField");

        Throwable throwable = null;
        try {
            this.tester.executeListener(component);
        } catch (Throwable e) {
            throwable = e;
        }

        Assert.assertNull(throwable == null ? "" : throwable.getMessage(), throwable);
    }

    @Test
    public void loginPage() {

        this.tester.startPage(LoginPage.class);

        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("identifierField", "default");
        formTester.setValue("loginField", "mifos");
        formTester.setValue("passwordField", "password");
        formTester.submit("loginButton");

        Assert.assertNotNull("token is null", this.tester.getSession().getToken());

    }

}
