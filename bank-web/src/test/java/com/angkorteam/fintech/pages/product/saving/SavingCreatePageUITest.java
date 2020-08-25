//package com.angkorteam.fintech.pages.product.saving;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class SavingCreatePageUITest {
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
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        Assert.assertEquals("expected to land detail tab " + SavingCreatePage.TAB_DETAIL, SavingCreatePage.TAB_DETAIL, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingCurrencyTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_CURRENCY + ":link", "click");
//        Assert.assertEquals("expected to land currency tab " + SavingCreatePage.TAB_CURRENCY, SavingCreatePage.TAB_CURRENCY, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingTermTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_TERM + ":link", "click");
//        Assert.assertEquals("expected to land term tab " + SavingCreatePage.TAB_TERM, SavingCreatePage.TAB_TERM, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingSettingTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_SETTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + SavingCreatePage.TAB_SETTING, SavingCreatePage.TAB_SETTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingChargeTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_CHARGE + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + SavingCreatePage.TAB_CHARGE, SavingCreatePage.TAB_CHARGE, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingAccountingTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_ACCOUNTING + ":link", "click");
//        Assert.assertEquals("expected to land setting tab " + SavingCreatePage.TAB_ACCOUNTING, SavingCreatePage.TAB_ACCOUNTING, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//    @Test
//    public void landingPreviewTab() {
//        this.wicket.login();
//        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//        this.wicket.executeAjaxEvent("tab:tabs-container:tabs:" + SavingCreatePage.TAB_PREVIEW + ":link", "click");
//        Assert.assertEquals("expected to land preview tab " + SavingCreatePage.TAB_PREVIEW, SavingCreatePage.TAB_PREVIEW, page.tab.getSelectedTab());
//        this.wicket.assertRenderedPage(SavingCreatePage.class);
//    }
//
//}
