//package com.angkorteam.fintech.pages.tax;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.model.PropertyModel;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitFormTester;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//public class TaxGroupCreatePageTest {
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
//        TaxGroupCreatePage page = this.wicket.startPage(TaxGroupCreatePage.class);
//
//        List<Map<String, Object>> taxComponentValue = Lists.newArrayList();
//
//        new PropertyModel<>(page, "taxComponentValue").setObject(taxComponentValue);
//
//        String taxComponentId = this.wicket.getJdbcTemplate().queryForObject("select id from m_tax_component limit 1", String.class);
//
//        Map<String, Object> tax = Maps.newHashMap();
//        tax.put("taxComponentId", taxComponentId);
//        tax.put("startDate", new Date());
//        taxComponentValue.add(tax);
//
//        String nameValue = "JUNIT_" + this.wicket.getStringGenerator().generate(10);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//        form.setValue("nameField", nameValue);
//        form.submit("saveButton");
//
//        this.wicket.assertNoErrorMessage();
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_tax_group WHERE  name = ?", nameValue);
//        Assert.assertNotNull("expected to have m_tax_group name = '" + nameValue + "'", actual);
//    }
//}
