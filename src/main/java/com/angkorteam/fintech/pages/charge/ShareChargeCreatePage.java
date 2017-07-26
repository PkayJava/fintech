package com.angkorteam.fintech.pages.charge;

import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareChargeCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private SingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private ChargeTimeProvider chargeTimeProvider;
    private Option chargeTimeValue;
    private Select2SingleChoice<Option> chargeTimeField;
    private TextFeedbackPanel chargeTimeFeedback;

    private ChargeCalculationProvider chargeCalculationProvider;
    private Option chargeCalculationValue;
    private Select2SingleChoice<Option> chargeCalculationField;
    private TextFeedbackPanel chargeCalculationFeedback;

    private Double amountValue;
    private TextField<Double> amountField;
    private TextFeedbackPanel amountFeedback;

    private boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private SingleChoiceProvider taxGroupProvider;
    private Option taxGroupValue;
    private Select2SingleChoice<Option> taxGroupField;
    private TextFeedbackPanel taxGroupFeedback;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	this.form = new Form<>("form");
	this.add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
	this.form.add(this.closeLink);

	this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
	this.nameField.setRequired(true);
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);

	this.currencyProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
		this.currencyProvider);
	this.currencyField.setRequired(true);
	this.form.add(this.currencyField);
	this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
	this.form.add(this.currencyFeedback);

	this.chargeTimeProvider = new ChargeTimeProvider();
	this.chargeTimeProvider.setValues(ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase,
		ChargeTime.ShareRedeem);
	this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", 0,
		new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
	this.chargeTimeField.setRequired(true);
	this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate, this::chargeTimeFieldError));
	this.form.add(this.chargeTimeField);
	this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
	this.form.add(this.chargeTimeFeedback);

	this.chargeCalculationProvider = new ChargeCalculationProvider();
	this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
	this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0,
		new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
	this.chargeCalculationField.setRequired(true);
	this.chargeCalculationField
		.add(new OnChangeAjaxBehavior(this::chargeCalculationFieldUpdate, this::chargeCalculationFieldError));
	this.form.add(this.chargeCalculationField);
	this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback",
		this.chargeCalculationField);
	this.form.add(this.chargeCalculationFeedback);

	this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
	this.amountField.setRequired(true);
	this.form.add(this.amountField);
	this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
	this.form.add(this.amountFeedback);

	this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
	this.activeField.setRequired(true);
	this.form.add(this.activeField);
	this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
	this.form.add(this.activeFeedback);

	this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
	this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"),
		this.taxGroupProvider);
	this.form.add(this.taxGroupField);
	this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
	this.form.add(this.taxGroupFeedback);

    }

    private void chargeCalculationFieldUpdate(AjaxRequestTarget target) {
	target.add(this.form);
    }

    private void chargeCalculationFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void chargeTimeFieldUpdate(AjaxRequestTarget target) {
	target.add(this.form);
    }

    private void chargeTimeFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void saveButtonSubmit(Button button) {
	ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());

	ChargeBuilder builder = new ChargeBuilder();
	builder.withChargeAppliesTo(ChargeType.Share);
	builder.withName(this.nameValue);
	builder.withCurrencyCode(this.currencyValue.getId());
	builder.withChargeTimeType(chargeTime);
	builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
	builder.withAmount(this.amountValue);
	builder.withActive(this.activeValue);
	if (this.taxGroupValue != null) {
	    builder.withTaxGroupId(this.taxGroupValue.getId());
	}

	JsonNode node = null;
	try {
	    node = ChargeHelper.create((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(ChargeBrowsePage.class);
    }

}
