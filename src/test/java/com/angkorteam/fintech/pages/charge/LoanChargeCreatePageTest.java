package com.angkorteam.fintech.pages.charge;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class LoanChargeCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
	this.wicket = new JUnitWicketTester();
    }

    @Test
    public void dataEntry() {
	this.wicket.login();
	this.wicket.startPage(LoanChargeCreatePage.class);
	FormTester form = this.wicket.newFormTester("form");
	form.setValue("chargeTimeField", ChargeTime.OverdueFees.name());
	Component chargeTimeField = form.getForm().get("chargeTimeField");
	AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField,
		OnChangeAjaxBehavior.EVENT_CHANGE);
	this.wicket.executeBehavior(behavior);
    }

}
