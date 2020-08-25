//package com.angkorteam.fintech.pages.office;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class OfficeModifyPageTest {
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
//        String officeId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_office LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("officeId", officeId);
//
//        this.wicket.startPage(OfficeModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(OfficeModifyPage.class);
//    }
//}
