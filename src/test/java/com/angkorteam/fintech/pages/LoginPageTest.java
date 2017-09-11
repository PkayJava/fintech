package com.angkorteam.fintech.pages;

import org.apache.wicket.util.tester.FormTester;
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
	// start and render the test page
	tester.startPage(LoginPage.class);

	// assert rendered page class
	tester.assertRenderedPage(LoginPage.class);

	FormTester formTester = tester.newFormTester("form");

	// formTester.setValue("identifierField", "default");
	formTester.setValue("loginField", "mifos");
	formTester.setValue("passwordField", "password");
	formTester.submit();

	// System.out.println(tester.getTagByWicketId("loginField").getClass().getName());

    }

}
