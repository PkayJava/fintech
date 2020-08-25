//package com.angkorteam.fintech.pages.payment;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class PaymentTypeModifyPageTest {
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
//        String paymentTypeId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("paymentTypeId", paymentTypeId);
//
//        this.wicket.startPage(PaymentTypeModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(PaymentTypeModifyPage.class);
//    }
//}
