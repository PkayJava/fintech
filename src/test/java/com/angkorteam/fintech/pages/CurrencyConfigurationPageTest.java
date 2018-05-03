//package com.angkorteam.fintech.pages;
//
//import java.util.Map;
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
//public class CurrencyConfigurationPageTest {
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
//        String currencyValue = "BYR";
//
//        this.wicket.getJdbcTemplate().update("delete from m_organisation_currency where code = ?", currencyValue);
//
//        this.wicket.startPage(CurrencyConfigurationPage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("currencyField", currencyValue);
//
//        form.submit("addButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_organisation_currency where code = ?", currencyValue);
//        Assert.assertNotNull("expected to have m_organisation_currency code = '" + currencyValue + "'", actual);
//
//        this.wicket.assertRenderedPage(CurrencyConfigurationPage.class);
//    }
//
//    @Test
//    public void dataEntry1() {
//        this.wicket.login();
//
//        String currencyValue = "BYR";
//
//        this.wicket.getJdbcTemplate().update("delete from m_organisation_currency where code = ?", currencyValue);
//
//        this.wicket.startPage(CurrencyConfigurationPage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("currencyField", currencyValue);
//
//        form.submit("addButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_organisation_currency where code = ?", currencyValue);
//        Assert.assertNotNull("expected to have m_organisation_currency code = '" + currencyValue + "'", actual);
//
//        this.wicket.executeAjaxEvent("filter-form:table:body:rows:1:cells:5:cell:1:link", "click");
//
//        actual = jdbcTemplate.queryForMap("select * from m_organisation_currency where code = ?", currencyValue);
//        Assert.assertNull("expected to not have m_organisation_currency code = '" + currencyValue + "'", actual);
//
//        this.wicket.assertRenderedPage(CurrencyConfigurationPage.class);
//    }
//
//}
