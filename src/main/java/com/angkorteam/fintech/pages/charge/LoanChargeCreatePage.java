package com.angkorteam.fintech.pages.charge;

import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.account.AccountBrowsePage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeFrequency;
import com.angkorteam.fintech.dto.ChargePayment;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeFrequencyProvider;
import com.angkorteam.fintech.provider.ChargePaymentProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class LoanChargeCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private OptionSingleChoiceProvider currencyProvider;
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

    private ChargePaymentProvider chargePaymentProvider;
    private Option chargePaymentValue;
    private Select2SingleChoice<Option> chargePaymentField;
    private TextFeedbackPanel chargePaymentFeedback;

    private ChargeFrequencyProvider chargeFrequencyProvider;
    private Option chargeFrequencyValue;
    private Select2SingleChoice<Option> chargeFrequencyField;
    private TextFeedbackPanel chargeFrequencyFeedback;

    private Integer frequencyIntervalValue;
    private TextField<Integer> frequencyIntervalField;
    private TextFeedbackPanel frequencyIntervalFeedback;

    private Double amountValue;
    private TextField<Double> amountField;
    private TextFeedbackPanel amountFeedback;

    private boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private boolean penaltyValue;
    private CheckBox penaltyField;
    private TextFeedbackPanel penaltyFeedback;

    private OptionSingleChoiceProvider taxGroupProvider;
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
	this.nameField.add(new OnChangeAjaxBehavior());
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);

	this.currencyProvider = new OptionSingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
		this.currencyProvider);
	this.currencyField.add(new OnChangeAjaxBehavior());
	this.currencyField.setRequired(true);
	this.form.add(this.currencyField);
	this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
	this.form.add(this.currencyFeedback);

	this.chargeTimeProvider = new ChargeTimeProvider();
	this.chargeTimeProvider.setValues(ChargeTime.Disbursement, ChargeTime.SpecifiedDueDate,
		ChargeTime.InstallmentFee, ChargeTime.OverdueFees, ChargeTime.TrancheDisbursement);
	this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", 0,
		new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
	this.chargeTimeField.setRequired(true);
	this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate, this::chargeTimeFieldError));
	this.form.add(this.chargeTimeField);
	this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
	this.form.add(this.chargeTimeFeedback);

	this.chargeCalculationProvider = new ChargeCalculationProvider();
	this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0,
		new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
	this.chargeCalculationField.setRequired(true);
	this.chargeCalculationField
		.add(new OnChangeAjaxBehavior(this::chargeCalculationFieldUpdate, this::chargeCalculationFieldError));
	this.form.add(this.chargeCalculationField);
	this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback",
		this.chargeCalculationField);
	this.form.add(this.chargeCalculationFeedback);

	this.chargePaymentProvider = new ChargePaymentProvider();
	this.chargePaymentField = new Select2SingleChoice<>("chargePaymentField", 0,
		new PropertyModel<>(this, "chargePaymentValue"), this.chargePaymentProvider);
	this.chargePaymentField
		.add(new OnChangeAjaxBehavior(this::chargePaymentFieldUpdate, this::chargePaymentFieldError));
	this.chargePaymentField.setRequired(true);
	this.form.add(this.chargePaymentField);
	this.chargePaymentFeedback = new TextFeedbackPanel("chargePaymentFeedback", this.chargePaymentField);
	this.form.add(this.chargePaymentFeedback);

	this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
	this.amountField.setRequired(true);
	this.amountField.add(new OnChangeAjaxBehavior());
	this.form.add(this.amountField);
	this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
	this.form.add(this.amountFeedback);

	this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
	this.activeField.setRequired(true);
	this.activeField.add(new OnChangeAjaxBehavior());
	this.form.add(this.activeField);
	this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
	this.form.add(this.activeFeedback);

	this.penaltyField = new CheckBox("penaltyField", new PropertyModel<>(this, "penaltyValue"));
	this.penaltyField.setRequired(true);
	this.penaltyField.add(new OnChangeAjaxBehavior());
	this.form.add(this.penaltyField);
	this.penaltyFeedback = new TextFeedbackPanel("penaltyFeedback", this.penaltyField);
	this.form.add(this.penaltyFeedback);

	this.taxGroupProvider = new OptionSingleChoiceProvider("m_tax_group", "id", "name");
	this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"),
		this.taxGroupProvider);
	this.taxGroupField.add(new OnChangeAjaxBehavior());
	this.form.add(this.taxGroupField);
	this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
	this.form.add(this.taxGroupFeedback);

	this.chargeFrequencyProvider = new ChargeFrequencyProvider();
	this.chargeFrequencyField = new Select2SingleChoice<>("chargeFrequencyField", 0,
		new PropertyModel<>(this, "chargeFrequencyValue"), this.chargeFrequencyProvider);
	this.chargeFrequencyField.add(new OnChangeAjaxBehavior());
	this.form.add(this.chargeFrequencyField);
	this.chargeFrequencyFeedback = new TextFeedbackPanel("chargeFrequencyFeedback", this.chargeFrequencyField);
	this.form.add(this.chargeFrequencyFeedback);

	this.frequencyIntervalField = new TextField<>("frequencyIntervalField",
		new PropertyModel<>(this, "frequencyIntervalValue"));
	this.frequencyIntervalField.add(new OnChangeAjaxBehavior());
	this.form.add(this.frequencyIntervalField);
	this.frequencyIntervalFeedback = new TextFeedbackPanel("frequencyIntervalFeedback",
		this.frequencyIntervalField);
	this.form.add(this.frequencyIntervalFeedback);
    }

    private void chargePaymentFieldUpdate(AjaxRequestTarget target) {
	target.add(this.form);
    }

    private void chargePaymentFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void chargeCalculationFieldUpdate(AjaxRequestTarget target) {
	target.add(this.form);
    }

    private void chargeCalculationFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void chargeTimeFieldUpdate(AjaxRequestTarget target) {
	this.chargeFrequencyValue = null;
	this.frequencyIntervalValue = null;
	this.chargeCalculationValue = null;
	ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
	if (chargeTime == ChargeTime.Disbursement) {
	    this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount,
		    ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
	} else if (chargeTime == ChargeTime.SpecifiedDueDate) {
	    this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount,
		    ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
	} else if (chargeTime == ChargeTime.InstallmentFee) {
	    this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount,
		    ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
	} else if (chargeTime == ChargeTime.OverdueFees) {
	    this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount,
		    ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
	} else if (chargeTime == ChargeTime.TrancheDisbursement) {
	    this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount,
		    ChargeCalculation.DisbursementAmount);
	}

	if (chargeTime == ChargeTime.OverdueFees) {
	    this.chargeFrequencyField.setRequired(true);
	    this.frequencyIntervalField.setRequired(true);
	    this.chargeFrequencyProvider.setValues(ChargeFrequency.Day, ChargeFrequency.Week, ChargeFrequency.Month,
		    ChargeFrequency.Year);
	} else {
	    this.chargeFrequencyField.setRequired(false);
	    this.frequencyIntervalField.setRequired(false);
	    this.chargeFrequencyProvider.setValues();
	}
	target.add(this.form);
    }

    private void chargeTimeFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void saveButtonSubmit(Button button) {
	ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());

	ChargeBuilder builder = new ChargeBuilder();
	builder.withChargeAppliesTo(ChargeType.Loan);
	builder.withName(this.nameValue);
	builder.withCurrencyCode(this.currencyValue.getId());
	builder.withChargeTimeType(ChargeTime.valueOf(this.chargeTimeValue.getId()));
	builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
	builder.withChargePaymentMode(ChargePayment.valueOf(this.chargePaymentValue.getId()));
	if (chargeTime == ChargeTime.OverdueFees) {
	    builder.withFeeFrequency(ChargeFrequency.valueOf(this.chargeFrequencyValue.getId()));
	    builder.withFeeInterval(this.frequencyIntervalValue);
	}
	builder.withAmount(this.amountValue);
	builder.withActive(this.activeValue);
	builder.withPenalty(this.penaltyValue);
	if (this.taxGroupValue != null) {
	    builder.withTaxGroupId(this.taxGroupValue.getId());
	}

	JsonNode node = null;
	try {
	    node = ChargeHelper.create(builder.build());
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
