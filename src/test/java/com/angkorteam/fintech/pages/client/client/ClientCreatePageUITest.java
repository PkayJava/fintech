package com.angkorteam.fintech.pages.client.client;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class ClientCreatePageUITest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void pageVisit() {
        this.wicket.login();

        ClientCreatePage page = this.wicket.startPage(ClientCreatePage.class);

        this.wicket.assertRenderedPage(ClientCreatePage.class);
    }

}
