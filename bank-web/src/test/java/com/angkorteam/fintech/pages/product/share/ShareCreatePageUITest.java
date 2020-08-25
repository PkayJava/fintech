//package com.angkorteam.fintech.pages.product.share;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class ShareCreatePageUITest {
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
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        Assert.assertEquals("expected to land detail tab " + ShareCreatePage.TAB_DETAIL, ShareCreatePage.TAB_DETAIL, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingMarketPriceTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_MARKET_PRICE + ":link", "click");
//        Assert.assertEquals("expected to land market price tab " + ShareCreatePage.TAB_MARKET_PRICE, ShareCreatePage.TAB_MARKET_PRICE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingCurrencyTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_CURRENCY + ":link", "click");
//        Assert.assertEquals("expected to land currency tab " + ShareCreatePage.TAB_CURRENCY, ShareCreatePage.TAB_CURRENCY, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingTermTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_TERM + ":link", "click");
//        Assert.assertEquals("expected to land term tab " + ShareCreatePage.TAB_TERM, ShareCreatePage.TAB_TERM, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingSettingTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_SETTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + ShareCreatePage.TAB_SETTING, ShareCreatePage.TAB_SETTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingChargeTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_CHARGE + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + ShareCreatePage.TAB_CHARGE, ShareCreatePage.TAB_CHARGE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingAccountingTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_ACCOUNTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + ShareCreatePage.TAB_ACCOUNTING, ShareCreatePage.TAB_ACCOUNTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//    @Test
//    public void landingPreviewTab() {
//        this.wicket.login();
//        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + ShareCreatePage.TAB_PREVIEW + ":link", "click");
//        Assert.assertEquals("expected to land preview tab " + ShareCreatePage.TAB_PREVIEW, ShareCreatePage.TAB_PREVIEW, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(ShareCreatePage.class);
//    }
//
//}
