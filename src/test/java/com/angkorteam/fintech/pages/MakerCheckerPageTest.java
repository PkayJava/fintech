//package com.angkorteam.fintech.pages;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitFormTester;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//
//public class MakerCheckerPageTest {
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
//
//        this.wicket.login();
//
//        this.wicket.startPage(MakerCheckerPage.class);
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("permissionField", "CREATE_GLACCOUNT");
//
//        form.submit("addButton");
//
//        this.wicket.assertRenderedPage(MakerCheckerPage.class);
//
//    }
//
//    @Test
//    public void visitPage1() {
//
//        this.wicket.login();
//
//        this.wicket.startPage(MakerCheckerPage.class);
//
//        this.wicket.executeAjaxEvent("filter-form:table:body:rows:1:cells:6:cell:1:link", "click");
//
//        this.wicket.assertNoErrorMessage();
//
//        this.wicket.assertRenderedPage(MakerCheckerPage.class);
//
//    }
//
//    @Test
//    public void visitPage2() {
//
//        this.wicket.login();
//
//        this.wicket.startPage(MakerCheckerPage.class);
//
//        this.wicket.executeAjaxEvent("filter-form:table:body:rows:1:cells:6:cell:1:link", "click");
//
//        this.wicket.assertNoErrorMessage();
//
//        this.wicket.assertRenderedPage(MakerCheckerPage.class);
//
//    }
//
//}
