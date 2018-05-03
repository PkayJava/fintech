//package com.angkorteam.fintech.pages.role;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class RolePermissionPageTest {
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
//        String roleId = this.wicket.getJdbcTemplate().queryForObject("SELECT role_id FROM m_role_permission LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//
//        parameters.add("roleId", roleId);
//
//        this.wicket.startPage(RolePermissionPage.class, parameters);
//
//        this.wicket.assertRenderedPage(RolePermissionPage.class);
//    }
//}
