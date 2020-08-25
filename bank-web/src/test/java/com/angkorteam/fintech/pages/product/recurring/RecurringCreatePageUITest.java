//package com.angkorteam.fintech.pages.product.recurring;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class RecurringCreatePageUITest {
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
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        Assert.assertEquals("expected to land detail tab " + RecurringCreatePage.TAB_DETAIL, RecurringCreatePage.TAB_DETAIL, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingCurrencyTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_CURRENCY + ":link", "click");
//        Assert.assertEquals("expected to land currency tab " + RecurringCreatePage.TAB_CURRENCY, RecurringCreatePage.TAB_CURRENCY, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingTermTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_TERM + ":link", "click");
//        Assert.assertEquals("expected to land term tab " + RecurringCreatePage.TAB_TERM, RecurringCreatePage.TAB_TERM, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingSettingTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_SETTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + RecurringCreatePage.TAB_SETTING, RecurringCreatePage.TAB_SETTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingInterestRateChartTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_INTEREST_RATE_CHART + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + RecurringCreatePage.TAB_INTEREST_RATE_CHART, RecurringCreatePage.TAB_INTEREST_RATE_CHART, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingChargeTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_CHARGE + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + RecurringCreatePage.TAB_CHARGE, RecurringCreatePage.TAB_CHARGE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingAccountingTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_ACCOUNTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + RecurringCreatePage.TAB_ACCOUNTING, RecurringCreatePage.TAB_ACCOUNTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//
//    @Test
//    public void landingPreviewTab() {
//        this.wicket.login();
//        RecurringCreatePage page = this.wicket.startPage(RecurringCreatePage.class);
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + RecurringCreatePage.TAB_PREVIEW + ":link", "click");
//        Assert.assertEquals("expected to land preview tab " + RecurringCreatePage.TAB_PREVIEW, RecurringCreatePage.TAB_PREVIEW, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(RecurringCreatePage.class);
//    }
//}
