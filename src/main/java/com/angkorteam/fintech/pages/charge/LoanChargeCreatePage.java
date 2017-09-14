package com.angkorteam.fintech.pages.charge;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeFrequency;
import com.angkorteam.fintech.dto.ChargePayment;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeFrequencyProvider;
import com.angkorteam.fintech.provider.ChargePaymentProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanChargeCreatePage extends Page {

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

    private WebMarkupContainer chargeCalculationBlock;
    private WebMarkupContainer chargeCalculationContainer;
    private ChargeCalculationProvider chargeCalculationProvider;
    private Option chargeCalculationValue;
    private Select2SingleChoice<Option> chargeCalculationField;
    private TextFeedbackPanel chargeCalculationFeedback;

    private ChargePaymentProvider chargePaymentProvider;
    private Option chargePaymentValue;
    private Select2SingleChoice<Option> chargePaymentField;
    private TextFeedbackPanel chargePaymentFeedback;

    private WebMarkupContainer feeFrequencyBlock;
    private WebMarkupContainer feeFrequencyContainer;
    private Boolean feeFrequencyValue;
    private CheckBox feeFrequencyField;
    private TextFeedbackPanel feeFrequencyFeedback;

    private WebMarkupContainer chargeFrequencyBlock;
    private WebMarkupContainer chargeFrequencyContainer;
    private ChargeFrequencyProvider chargeFrequencyProvider;
    private Option chargeFrequencyValue;
    private Select2SingleChoice<Option> chargeFrequencyField;
    private TextFeedbackPanel chargeFrequencyFeedback;

    private WebMarkupContainer frequencyIntervalBlock;
    private WebMarkupContainer frequencyIntervalContainer;
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

    private SingleChoiceProvider taxGroupProvider;
    private Option taxGroupValue;
    private Select2SingleChoice<Option> taxGroupField;
    private TextFeedbackPanel taxGroupFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
	return Model.ofList(BREADCRUMB);
    }

    static {
	BREADCRUMB = Lists.newArrayList();
	{
	    PageBreadcrumb breadcrumb = new PageBreadcrumb();
	    breadcrumb.setLabel("Admin");
	    BREADCRUMB.add(breadcrumb);
	}
	{
	    PageBreadcrumb breadcrumb = new PageBreadcrumb();
	    breadcrumb.setLabel("Product");
	    breadcrumb.setPage(ProductDashboardPage.class);
	    BREADCRUMB.add(breadcrumb);
	}
	{
	    PageBreadcrumb breadcrumb = new PageBreadcrumb();
	    breadcrumb.setLabel("Charge");
	    breadcrumb.setPage(ChargeBrowsePage.class);
	    BREADCRUMB.add(breadcrumb);
	}

	{
	    PageBreadcrumb breadcrumb = new PageBreadcrumb();
	    breadcrumb.setLabel("Loan Charge Create");
	    BREADCRUMB.add(breadcrumb);
	}
    }

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
	this.nameField.setLabel(Model.of("Name"));
	this.nameField.setRequired(true);
	this.nameField.add(new OnChangeAjaxBehavior());
	this.form.add(this.nameField);
	this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
	this.form.add(this.nameFeedback);

	this.currencyProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
		this.currencyProvider);
	this.currencyField.setLabel(Model.of("Currency"));
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
	this.chargeTimeField.setLabel(Model.of("Charge time type"));
	this.chargeTimeField.setRequired(true);
	this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate));
	this.form.add(this.chargeTimeField);
	this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
	this.form.add(this.chargeTimeFeedback);

	this.chargeCalculationBlock = new WebMarkupContainer("chargeCalculationBlock");
	this.chargeCalculationBlock.setOutputMarkupId(true);
	this.form.add(this.chargeCalculationBlock);
	this.chargeCalculationContainer = new WebMarkupContainer("chargeCalculationContainer");
	this.chargeCalculationBlock.add(this.chargeCalculationContainer);
	this.chargeCalculationProvider = new ChargeCalculationProvider();
	this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0,
		new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
	this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
	this.chargeCalculationField.setRequired(true);
	this.chargeCalculationField.add(new OnChangeAjaxBehavior());
	this.chargeCalculationContainer.add(this.chargeCalculationField);
	this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback",
		this.chargeCalculationField);
	this.chargeCalculationContainer.add(this.chargeCalculationFeedback);

	this.chargePaymentProvider = new ChargePaymentProvider();
	this.chargePaymentField = new Select2SingleChoice<>("chargePaymentField", 0,
		new PropertyModel<>(this, "chargePaymentValue"), this.chargePaymentProvider);
	this.chargePaymentField.setLabel(Model.of("Charge payment by"));
	this.chargePaymentField.add(new OnChangeAjaxBehavior());
	this.chargePaymentField.setRequired(true);
	this.form.add(this.chargePaymentField);
	this.chargePaymentFeedback = new TextFeedbackPanel("chargePaymentFeedback", this.chargePaymentField);
	this.form.add(this.chargePaymentFeedback);

	this.feeFrequencyBlock = new WebMarkupContainer("feeFrequencyBlock");
	this.feeFrequencyBlock.setOutputMarkupId(true);
	this.form.add(this.feeFrequencyBlock);
	this.feeFrequencyContainer = new WebMarkupContainer("feeFrequencyContainer");
	this.feeFrequencyBlock.add(this.feeFrequencyContainer);
	this.feeFrequencyField = new CheckBox("feeFrequencyField", new PropertyModel<>(this, "feeFrequencyValue"));
	this.feeFrequencyField.add(new OnChangeAjaxBehavior(this::feeFrequencyUpdate));
	this.feeFrequencyContainer.add(this.feeFrequencyField);
	this.feeFrequencyFeedback = new TextFeedbackPanel("feeFrequencyFeedback", this.feeFrequencyField);
	this.feeFrequencyContainer.add(this.feeFrequencyFeedback);

	this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
	this.amountField.setLabel(Model.of("Amount"));
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

	this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
	this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"),
		this.taxGroupProvider);
	this.taxGroupField.setLabel(Model.of("Tax Group"));
	this.taxGroupField.add(new OnChangeAjaxBehavior());
	this.form.add(this.taxGroupField);
	this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
	this.form.add(this.taxGroupFeedback);

	this.chargeFrequencyBlock = new WebMarkupContainer("chargeFrequencyBlock");
	this.chargeFrequencyBlock.setOutputMarkupId(true);
	this.form.add(this.chargeFrequencyBlock);
	this.chargeFrequencyContainer = new WebMarkupContainer("chargeFrequencyContainer");
	this.chargeFrequencyBlock.add(this.chargeFrequencyContainer);
	this.chargeFrequencyProvider = new ChargeFrequencyProvider();
	this.chargeFrequencyField = new Select2SingleChoice<>("chargeFrequencyField", 0,
		new PropertyModel<>(this, "chargeFrequencyValue"), this.chargeFrequencyProvider);
	this.chargeFrequencyField.setLabel(Model.of("Charge Frequency"));
	this.chargeFrequencyField.add(new OnChangeAjaxBehavior());
	this.chargeFrequencyContainer.add(this.chargeFrequencyField);
	this.chargeFrequencyFeedback = new TextFeedbackPanel("chargeFrequencyFeedback", this.chargeFrequencyField);
	this.chargeFrequencyContainer.add(this.chargeFrequencyFeedback);

	this.frequencyIntervalBlock = new WebMarkupContainer("frequencyIntervalBlock");
	this.frequencyIntervalBlock.setOutputMarkupId(true);
	this.form.add(this.frequencyIntervalBlock);
	this.frequencyIntervalContainer = new WebMarkupContainer("frequencyIntervalContainer");
	this.frequencyIntervalBlock.add(this.frequencyIntervalContainer);
	this.frequencyIntervalField = new TextField<>("frequencyIntervalField",
		new PropertyModel<>(this, "frequencyIntervalValue"));
	this.frequencyIntervalField.setLabel(Model.of("Frequency Interval"));
	this.frequencyIntervalField.add(new OnChangeAjaxBehavior());
	this.frequencyIntervalContainer.add(this.frequencyIntervalField);
	this.frequencyIntervalFeedback = new TextFeedbackPanel("frequencyIntervalFeedback",
		this.frequencyIntervalField);
	this.frequencyIntervalContainer.add(this.frequencyIntervalFeedback);

	initDefault();
    }

    protected void initDefault() {
	chargeTimeFieldUpdate(null);
    }

    protected boolean feeFrequencyUpdate(AjaxRequestTarget target) {
	boolean visible = this.feeFrequencyValue == null ? false : this.feeFrequencyValue;
	this.chargeFrequencyContainer.setVisible(visible);
	this.frequencyIntervalContainer.setVisible(visible);

	ChargeTime chargeTime = this.chargeTimeValue == null ? null : ChargeTime.valueOf(this.chargeTimeValue.getId());

	boolean required = visible && chargeTime == ChargeTime.OverdueFees;

	this.chargeFrequencyField.setRequired(required);
	this.frequencyIntervalField.setRequired(required);

	if (target != null) {
	    target.add(this.chargeFrequencyBlock);
	    target.add(this.frequencyIntervalBlock);
	}
	return false;
    }

    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
	this.chargeFrequencyValue = null;
	this.frequencyIntervalValue = null;
	this.chargeCalculationValue = null;
	ChargeTime chargeTime = this.chargeTimeValue == null ? null : ChargeTime.valueOf(this.chargeTimeValue.getId());
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
	} else {
	    this.chargeCalculationProvider.setValues();
	}

	this.chargeCalculationContainer.setVisible(chargeTime != null);
	this.chargeCalculationField.setRequired(chargeTime != null);

	boolean visible = chargeTime == ChargeTime.OverdueFees;
	this.feeFrequencyContainer.setVisible(visible);

	if (target != null) {
	    target.add(this.chargeCalculationBlock);
	    target.add(this.feeFrequencyBlock);
	}

	feeFrequencyUpdate(target);

	return false;
    }

    protected void saveButtonSubmit(Button button) {
	ChargeTime chargeTime = this.chargeTimeValue == null ? null : ChargeTime.valueOf(this.chargeTimeValue.getId());

	ChargeBuilder builder = new ChargeBuilder();
	builder.withChargeAppliesTo(ChargeType.Loan);
	builder.withName(this.nameValue);
	if (this.currencyValue != null) {
	    builder.withCurrencyCode(this.currencyValue.getId());
	}
	builder.withChargeTimeType(chargeTime);
	if (this.chargeCalculationValue != null) {
	    builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
	}
	if (this.chargePaymentValue != null) {
	    builder.withChargePaymentMode(ChargePayment.valueOf(this.chargePaymentValue.getId()));
	}
	if (chargeTime == ChargeTime.OverdueFees && this.feeFrequencyValue != null && this.feeFrequencyValue) {
	    if (this.chargeFrequencyValue != null) {
		builder.withFeeFrequency(ChargeFrequency.valueOf(this.chargeFrequencyValue.getId()));
	    }
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
