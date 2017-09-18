package com.angkorteam.fintech.pages.account;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class FinancialActivityModifyPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        String financialActivityId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_financial_activity_account limit 1", String.class);

        PageParameters parameters = new PageParameters();
        parameters.add("financialActivityId", financialActivityId);

        this.wicket.startPage(FinancialActivityModifyPage.class, parameters);

        this.wicket.assertRenderedPage(FinancialActivityModifyPage.class);
    }
}
