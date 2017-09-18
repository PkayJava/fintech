package com.angkorteam.fintech.pages.staff;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class UserModifyPageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        String userId = this.wicket.getJdbcTemplate().queryForObject("select id from m_appuser limit 1", String.class);
        PageParameters parameters = new PageParameters();
        parameters.add("userId", userId);
        this.wicket.startPage(UserModifyPage.class, parameters);
        this.wicket.assertRenderedPage(UserModifyPage.class);
    }
}
