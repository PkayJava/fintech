package com.angkorteam.fintech.pages.charge;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public class LoanChargeCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
	this.wicket = new JUnitWicketTester();
    }

    @Test
    public void chargeTimeFieldUpdate() {
	this.wicket.login();
	LoanChargeCreatePage page = this.wicket.startPage(LoanChargeCreatePage.class);
	Assert.assertNull(new PropertyModel<>(page, "chargeTimeValue").getObject());
	FormTester form = this.wicket.newFormTester("form");
	form.setValue("chargeTimeField", ChargeTime.OverdueFees.name());
	Component chargeTimeField = form.getForm().get("chargeTimeField");
	AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField,
		OnChangeAjaxBehavior.EVENT_CHANGE);
	this.wicket.executeBehavior(behavior);
	Assert.assertEquals(ChargeTime.OverdueFees.name(),
		((Option) new PropertyModel<>(page, "chargeTimeValue").getObject()).getId());

    }

    @Test
    public void feeFrequencyUpdate() {
	this.wicket.login();
	LoanChargeCreatePage page = this.wicket.startPage(LoanChargeCreatePage.class);
	{
	    FormTester form = this.wicket.newFormTester("form");
	    form.setValue("chargeTimeField", ChargeTime.OverdueFees.name());
	    Component chargeTimeField = form.getForm().get("chargeTimeField");
	    AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(chargeTimeField,
		    OnChangeAjaxBehavior.EVENT_CHANGE);
	    this.wicket.executeBehavior(behavior);
	}
	{
	    Assert.assertEquals(false, new PropertyModel<>(page, "feeFrequencyValue").getObject() == null ? false
		    : new PropertyModel<>(page, "feeFrequencyValue").getObject());
	    FormTester form = this.wicket.newFormTester("form");
	    form.setValue("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField", true);
	    Component feeFrequencyField = form.getForm()
		    .get("feeFrequencyBlock:feeFrequencyContainer:feeFrequencyField");
	    AjaxEventBehavior behavior = WicketTesterHelper.findAjaxEventBehavior(feeFrequencyField,
		    OnChangeAjaxBehavior.EVENT_CHANGE);
	    this.wicket.executeBehavior(behavior);
	    Assert.assertEquals(true, new PropertyModel<>(page, "feeFrequencyValue").getObject());
	}
    }

}
