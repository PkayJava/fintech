package com.angkorteam.fintech.pages.account;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class RuleModifyPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        String ruleId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM acc_accounting_rule LIMIT 1", String.class);

        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", ruleId);

        this.wicket.startPage(RuleModifyPage.class, parameters);

        this.wicket.assertRenderedPage(RuleModifyPage.class);
    }
}
