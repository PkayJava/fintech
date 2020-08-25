//package com.angkorteam.fintech.pages.account;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class AccountModifyPageTest {
//
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Test
//    public void visitPage() {
//        this.wicket.login();
//
//        String accountId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM acc_gl_account LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("accountId", accountId);
//
//        this.wicket.startPage(AccountModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(AccountModifyPage.class);
//    }
//}
