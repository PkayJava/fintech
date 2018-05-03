//package com.angkorteam.fintech.pages.hook;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Ignore;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class HookModifyPageTest {
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Ignore
//    public void visitPage() {
//        this.wicket.login();
//
//        String hookId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_hook LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("hookId", hookId);
//
//        this.wicket.startPage(HookModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(HookModifyPage.class);
//
//    }
//}
