package com.angkorteam.fintech.junit;

import org.apache.wicket.util.tester.WicketTester;

import com.angkorteam.fintech.Application;
import com.angkorteam.fintech.Session;

public class JUnitWicketTester extends WicketTester {

    public JUnitWicketTester() {
        super(JUnit.getApplication(), JUnit.getServletContext());
    }

    @Override
    public Session getSession() {
        return (Session) super.getSession();
    }

    @Override
    public Application getApplication() {
        return (Application) super.getApplication();
    }

}
