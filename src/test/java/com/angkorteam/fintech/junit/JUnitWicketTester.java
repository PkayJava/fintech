package com.angkorteam.fintech.junit;

import org.apache.wicket.util.tester.WicketTester;

public class JUnitWicketTester extends WicketTester {

    public JUnitWicketTester() {
	super(JUnit.getApplication(), JUnit.getServletContext());
    }

}
