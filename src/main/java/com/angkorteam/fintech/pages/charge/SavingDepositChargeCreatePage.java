package com.angkorteam.fintech.pages.charge;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SavingDepositChargeCreatePage extends Page {

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

    private Date dueDateValue;
    private DayMonthTextField dueDateField;
    private TextFeedbackPanel dueDateFeedback;

    private Integer repeatEveryValue;
    private TextField<Integer> repeatEveryField;
    private TextFeedbackPanel repeatEveryFeedback;

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
	this.currencyField.setRequired(true);
	this.currencyField.add(new OnChangeAjaxBehavior());
	this.form.add(this.currencyField);
	this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
	this.form.add(this.currencyFeedback);

	this.chargeTimeProvider = new ChargeTimeProvider();
	this.chargeTimeProvider.setValues(ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation,
		ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee,
		ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee);
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
	this.chargeCalculationField.add(new OnChangeAjaxBehavior());
	this.chargeCalculationField.setRequired(true);
	this.chargeCalculationField
		.add(new OnChangeAjaxBehavior(this::chargeCalculationFieldUpdate, this::chargeCalculationFieldError));
	this.form.add(this.chargeCalculationField);
	this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback",
		this.chargeCalculationField);
	this.form.add(this.chargeCalculationFeedback);

	this.dueDateField = new DayMonthTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
	this.dueDateField.setRequired(true);
	this.dueDateField.add(new OnChangeAjaxBehavior());
	this.form.add(this.dueDateField);
	this.dueDateFeedback = new TextFeedbackPanel("dueDateFeedback", this.dueDateField);
	this.form.add(this.dueDateFeedback);

	this.repeatEveryField = new TextField<>("repeatEveryField", new PropertyModel<>(this, "repeatEveryValue"));
	this.repeatEveryField.setRequired(true);
	this.repeatEveryField.add(new OnChangeAjaxBehavior());
	this.form.add(this.repeatEveryField);
	this.repeatEveryFeedback = new TextFeedbackPanel("repeatEveryFeedback", this.repeatEveryField);
	this.form.add(this.repeatEveryFeedback);

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
	this.dueDateField.setRequired(false);
	this.repeatEveryField.setRequired(false);
	ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
	if (chargeTime == ChargeTime.SpecifiedDueDate) {
	} else if (chargeTime == ChargeTime.SavingsActivation) {
	} else if (chargeTime == ChargeTime.WithdrawalFee) {
	} else if (chargeTime == ChargeTime.AnnualFee) {
	    this.dueDateField.setRequired(true);
	} else if (chargeTime == ChargeTime.MonthlyFee) {
	    this.dueDateField.setRequired(true);
	    this.repeatEveryField.setRequired(true);
	} else if (chargeTime == ChargeTime.WeeklyFee) {
	    this.repeatEveryField.setRequired(true);
	} else if (chargeTime == ChargeTime.OverdraftFee) {
	} else if (chargeTime == ChargeTime.SavingNoActivityFee) {
	}
	target.add(this.form);
    }

    private void chargeTimeFieldError(AjaxRequestTarget target, RuntimeException error) {
	target.add(this.form);
    }

    private void saveButtonSubmit(Button button) {
	ChargeTime chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());

	ChargeBuilder builder = new ChargeBuilder();
	builder.withChargeAppliesTo(ChargeType.SavingDeposit);
	builder.withName(this.nameValue);
	builder.withCurrencyCode(this.currencyValue.getId());
	builder.withChargeTimeType(ChargeTime.valueOf(this.chargeTimeValue.getId()));
	builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
	if (chargeTime == ChargeTime.AnnualFee) {
	    builder.withFeeOnMonthDay(this.dueDateValue);
	} else if (chargeTime == ChargeTime.MonthlyFee) {
	    builder.withFeeOnMonthDay(this.dueDateValue);
	    builder.withFeeInterval(this.repeatEveryValue);
	} else if (chargeTime == ChargeTime.WeeklyFee) {
	    builder.withFeeInterval(this.repeatEveryValue);
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
