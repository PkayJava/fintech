//package com.angkorteam.fintech.pages.charge;
//
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.dto.enums.ChargeType;
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class ShareChargeModifyPageTest {
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
//        String chargeId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_charge WHERE charge_applies_to_enum = ? LIMIT 1", String.class, ChargeType.Share.getLiteral());
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("chargeId", chargeId);
//
//        this.wicket.startPage(ShareChargeModifyPage.class, parameters);
//
//        this.wicket.assertRenderedPage(ShareChargeModifyPage.class);
//    }
//
//}
