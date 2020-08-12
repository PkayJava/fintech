//package com.angkorteam.fintech.pages.product.loan;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class LoanCreatePageUITest {
//
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Test
//    public void landingDetailTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        Assert.assertEquals("expected to land detail tab " + LoanCreatePage.TAB_DETAIL, LoanCreatePage.TAB_DETAIL, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingCurrencyTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_CURRENCY + ":link", "click");
//        Assert.assertEquals("expected to land currency tab " + LoanCreatePage.TAB_CURRENCY, LoanCreatePage.TAB_CURRENCY, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingTermTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_TERM + ":link", "click");
//        Assert.assertEquals("expected to land term tab " + LoanCreatePage.TAB_TERM, LoanCreatePage.TAB_TERM, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingSettingTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_SETTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + LoanCreatePage.TAB_SETTING, LoanCreatePage.TAB_SETTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingChargeTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_CHARGE + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + LoanCreatePage.TAB_CHARGE, LoanCreatePage.TAB_CHARGE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingAccountingTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_ACCOUNTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + LoanCreatePage.TAB_ACCOUNTING, LoanCreatePage.TAB_ACCOUNTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//    @Test
//    public void landingPreviewTab() {
//        this.wicket.login();
//        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + LoanCreatePage.TAB_PREVIEW + ":link", "click");
//        Assert.assertEquals("expected to land preview tab " + LoanCreatePage.TAB_PREVIEW, LoanCreatePage.TAB_PREVIEW, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(LoanCreatePage.class);
//    }
//
//}
