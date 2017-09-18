package com.angkorteam.fintech.pages.account;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class TransactionPageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        String transactionId = this.wicket.getJdbcTemplate().queryForObject("SELECT transaction_id FROM acc_gl_journal_entry LIMIT 1", String.class);

        PageParameters parameters = new PageParameters();
        parameters.add("transactionId", transactionId);

        this.wicket.startPage(TransactionPage.class, parameters);

        this.wicket.assertRenderedPage(TransactionPage.class);
    }
}
