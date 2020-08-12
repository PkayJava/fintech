//package com.angkorteam.fintech.pages.product.fixed;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class FixedCreatePageUITest {
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
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        Assert.assertEquals("expected to land detail tab " + FixedCreatePage.TAB_DETAIL, FixedCreatePage.TAB_DETAIL, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingCurrencyTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_CURRENCY + ":link", "click");
//        Assert.assertEquals("expected to land currency tab " + FixedCreatePage.TAB_CURRENCY, FixedCreatePage.TAB_CURRENCY, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingTermTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_TERM + ":link", "click");
//        Assert.assertEquals("expected to land term tab " + FixedCreatePage.TAB_TERM, FixedCreatePage.TAB_TERM, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingSettingTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_SETTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + FixedCreatePage.TAB_SETTING, FixedCreatePage.TAB_SETTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingInterestRateChartTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_INTEREST_RATE_CHART + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + FixedCreatePage.TAB_INTEREST_RATE_CHART, FixedCreatePage.TAB_INTEREST_RATE_CHART, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingChargeTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_CHARGE + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + FixedCreatePage.TAB_CHARGE, FixedCreatePage.TAB_CHARGE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingAccountingTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_ACCOUNTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + FixedCreatePage.TAB_ACCOUNTING, FixedCreatePage.TAB_ACCOUNTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//    @Test
//    public void landingPreviewTab() {
//        this.wicket.login();
//        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + FixedCreatePage.TAB_PREVIEW + ":link", "click");
//        Assert.assertEquals("expected to land preview tab " + FixedCreatePage.TAB_PREVIEW, FixedCreatePage.TAB_PREVIEW, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(FixedCreatePage.class);
//    }
//
//}
