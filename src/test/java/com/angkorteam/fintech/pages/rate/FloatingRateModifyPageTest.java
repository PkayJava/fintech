package com.angkorteam.fintech.pages.rate;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FloatingRateModifyPageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        String rateId = this.wicket.getJdbcTemplate().queryForObject("select id from m_floating_rates limit 1", String.class);
        PageParameters parameters = new PageParameters();
        parameters.add("rateId", rateId);
        this.wicket.startPage(FloatingRateModifyPage.class, parameters);
        this.wicket.assertRenderedPage(FloatingRateModifyPage.class);
    }
}
