//package com.angkorteam.fintech.pages.teller;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class TellerModifyPageTest {
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
//        String tellerId = this.wicket.getJdbcTemplate().queryForObject("select id from m_tellers limit 1", String.class);
//        PageParameters parameters = new PageParameters();
//        parameters.add("tellerId", tellerId);
//        this.wicket.startPage(TellerModifyPage.class, parameters);
//        this.wicket.assertRenderedPage(TellerModifyPage.class);
//    }
//}
