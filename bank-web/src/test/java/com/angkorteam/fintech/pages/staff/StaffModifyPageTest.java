//package com.angkorteam.fintech.pages.staff;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class StaffModifyPageTest {
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
//        String staffId = this.wicket.getJdbcTemplate().queryForObject("select id from m_staff limit 1", String.class);
//        PageParameters parameters = new PageParameters();
//        parameters.add("staffId", staffId);
//        this.wicket.startPage(StaffModifyPage.class, parameters);
//        this.wicket.assertRenderedPage(StaffModifyPage.class);
//    }
//}
