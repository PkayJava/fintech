//package com.angkorteam.fintech.pages;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitFormTester;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//import com.angkorteam.framework.spring.JdbcTemplate;
//
//public class GlobalConfigurationPageTest {
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Test
//    public void dataEntry2() {
//        this.wicket.login();
//
//        this.wicket.startPage(GlobalConfigurationPage.class);
//
//        this.wicket.executeAjaxEvent("filter-form:table:body:rows:3:cells:4:cell:1:link", "click");
//    }
//
//    @Test
//    public void dataEntry1() {
//        this.wicket.login();
//
//        String nameKey = "amazon-S3";
//        String nameValue = this.wicket.getJdbcTemplate().queryForObject("select id from c_configuration where name = ?", String.class, nameKey);
//
//        String valueValue = String.valueOf(System.currentTimeMillis() % 5);
//
//        this.wicket.startPage(GlobalConfigurationPage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("nameField", nameValue);
//
//        form.setValue("valueField", valueValue);
//
//        form.submit("saveButton");
//
//        this.wicket.assertRenderedPage(GlobalConfigurationPage.class);
//
//        this.wicket.assertNoErrorMessage();
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        String actual = jdbcTemplate.queryForObject("select value from c_configuration where name = ?", String.class, nameKey);
//        Assert.assertEquals("expected to have c_configuration name = '" + nameKey + "' = " + valueValue, valueValue, actual);
//
//    }
//
//    @Test
//    public void dataEntry3() {
//        this.wicket.login();
//
//        String nameKey = "amazon-S3";
//        String nameValue = this.wicket.getJdbcTemplate().queryForObject("select id from c_configuration where name = ?", String.class, nameKey);
//
//        String valueValue = String.valueOf(System.currentTimeMillis() % 5);
//
//        this.wicket.startPage(GlobalConfigurationPage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("nameField", nameValue);
//
//        form.setValue("valueField", valueValue);
//
//        form.submit("saveButton");
//
//        this.wicket.assertRenderedPage(GlobalConfigurationPage.class);
//
//        this.wicket.assertNoErrorMessage();
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        String actual = jdbcTemplate.queryForObject("select value from c_configuration where name = ?", String.class, nameKey);
//        Assert.assertEquals("expected to have c_configuration name = '" + nameKey + "' = " + valueValue, valueValue, actual);
//
//    }
//
//}
