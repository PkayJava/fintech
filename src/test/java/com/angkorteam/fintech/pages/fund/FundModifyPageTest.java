//package com.angkorteam.fintech.pages.fund;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class FundModifyPageTest {
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
//        String fundId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_fund LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("fundId", fundId);
//
//        this.wicket.startPage(FundModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(FundModifyPage.class);
//    }
//}
