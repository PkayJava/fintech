//package com.angkorteam.fintech.pages.account;
//
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.joda.time.DateTime;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitFormTester;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//import com.angkorteam.framework.spring.JdbcTemplate;
//
//public class ClosureCreatePageTest {
//
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Test
//    public void dataEntry() {
//        this.wicket.login();
//
//        String officeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_office limit 1", String.class);
//        String commentValue = "JUNIT_" + this.wicket.getStringGenerator().generate(30);
//
//        this.wicket.getJdbcTemplate().update("delete from acc_gl_closure where office_id = ?", officeValue);
//
//        this.wicket.startPage(ClosureCreatePage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("officeField", officeValue);
//
//        form.setValue("closingDateField", DateFormatUtils.format(new Date(), "dd/MM/yyyy"));
//
//        form.setValue("commentField", commentValue);
//
//        form.submit("saveButton");
//
//        this.wicket.assertNoErrorMessage();
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_closure where comments = ?", commentValue);
//        Assert.assertNotNull("expected to have acc_gl_closure comments = '" + commentValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntry1() {
//        this.wicket.login();
//
//        String officeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_office limit 1", String.class);
//        String commentValue = "JUNIT_" + this.wicket.getStringGenerator().generate(30);
//        Date closingDateValue = DateTime.now().plusDays(1).toDate();
//
//        this.wicket.startPage(ClosureCreatePage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("officeField", officeValue);
//
//        form.setValue("closingDateField", DateFormatUtils.format(closingDateValue, "dd/MM/yyyy"));
//
//        form.setValue("commentField", commentValue);
//
//        form.submit("saveButton");
//
//        this.wicket.assertErrorMessage();
//    }
//
//}
