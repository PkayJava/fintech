package com.angkorteam.fintech.pages.charge;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.ChargePayment;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeFrequencyProvider;
import com.angkorteam.fintech.provider.ChargePaymentProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanChargeModifyPage extends Page {

    protected String chargeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyIContainer;
    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected WebMarkupBlock chargeTimeBlock;
    protected WebMarkupContainer chargeTimeIContainer;
    protected ChargeTimeProvider chargeTimeProvider;
    protected Option chargeTimeValue;
    protected Select2SingleChoice<Option> chargeTimeField;
    protected TextFeedbackPanel chargeTimeFeedback;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationIContainer;
    protected ChargeCalculationProvider chargeCalculationProvider;
    protected Option chargeCalculationValue;
    protected Select2SingleChoice<Option> chargeCalculationField;
    protected TextFeedbackPanel chargeCalculationFeedback;

    protected WebMarkupBlock chargePaymentBlock;
    protected WebMarkupContainer chargePaymentIContainer;
    protected ChargePaymentProvider chargePaymentProvider;
    protected Option chargePaymentValue;
    protected Select2SingleChoice<Option> chargePaymentField;
    protected TextFeedbackPanel chargePaymentFeedback;

    protected WebMarkupBlock feeFrequencyBlock;
    protected WebMarkupContainer feeFrequencyIContainer;
    protected Boolean feeFrequencyValue;
    protected CheckBox feeFrequencyField;
    protected TextFeedbackPanel feeFrequencyFeedback;

    protected WebMarkupBlock chargeFrequencyBlock;
    protected WebMarkupContainer chargeFrequencyIContainer;
    protected ChargeFrequencyProvider chargeFrequencyProvider;
    protected Option chargeFrequencyValue;
    protected Select2SingleChoice<Option> chargeFrequencyField;
    protected TextFeedbackPanel chargeFrequencyFeedback;

    protected WebMarkupBlock frequencyIntervalBlock;
    protected WebMarkupContainer frequencyIntervalIContainer;
    protected Long frequencyIntervalValue;
    protected TextField<Long> frequencyIntervalField;
    protected TextFeedbackPanel frequencyIntervalFeedback;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupBlock penaltyBlock;
    protected WebMarkupContainer penaltyIContainer;
    protected Boolean penaltyValue;
    protected CheckBox penaltyField;
    protected TextFeedbackPanel penaltyFeedback;

    protected WebMarkupBlock taxGroupBlock;
    protected WebMarkupContainer taxGroupIContainer;
    protected SingleChoiceProvider taxGroupProvider;
    protected Option taxGroupValue;
    protected Select2SingleChoice<Option> taxGroupField;
    protected TextFeedbackPanel taxGroupFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
            breadcrumb.setLabel("Loan Charge Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, this.chargeId);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.CURRENCY_CODE);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_PAYMENT_MODE_ENUM);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.Field.IS_ACTIVE);
        selectQuery.addField(MCharge.Field.IS_PENALTY);
        selectQuery.addField(MCharge.Field.TAX_GROUP_ID);
        selectQuery.addField(MCharge.Field.FEE_FREQUENCY);
        selectQuery.addField(MCharge.Field.FEE_INTERVAL);

        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.nameValue = (String) chargeObject.get(MCharge.Field.NAME);

        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
        selectQuery.addField("concat(" + MOrganisationCurrency.Field.NAME + ",' [', " + MOrganisationCurrency.Field.CODE + ",']') text");
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, chargeObject.get(MCharge.Field.CURRENCY_CODE));
        this.currencyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        String charge_time_enum = String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM));
        this.chargeTimeValue = ChargeTime.optionLiteral(charge_time_enum);

        String charge_calculation_enum = String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(charge_calculation_enum);

        String charge_payment_mode_enum = String.valueOf(chargeObject.get(MCharge.Field.CHARGE_PAYMENT_MODE_ENUM));
        this.chargePaymentValue = ChargePayment.optionLiteral(charge_payment_mode_enum);

        this.amountValue = (Double) chargeObject.get(MCharge.Field.AMOUNT);

        this.activeValue = (Boolean) chargeObject.get(MCharge.Field.IS_ACTIVE);

        this.penaltyValue = (Boolean) chargeObject.get(MCharge.Field.IS_PENALTY);

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addField(MTaxGroup.Field.ID);
        selectQuery.addField(MTaxGroup.Field.NAME + " as text");
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, chargeObject.get(MCharge.Field.TAX_GROUP_ID));
        this.taxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        String fee_frequency = String.valueOf(chargeObject.get(MCharge.Field.FEE_FREQUENCY));
        this.chargeFrequencyValue = ChargeFrequency.optionLiteral(fee_frequency);

        this.frequencyIntervalValue = (Long) chargeObject.get(MCharge.Field.FEE_INTERVAL);

        this.feeFrequencyValue = this.chargeFrequencyValue != null || this.frequencyIntervalValue != null;
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initCurrencyBlock();

        initChargeTimeBlock();

        initChargeCalculationBlock();

        initChargePaymentBlock();

        initFeeFrequencyBlock();

        initAmountBlock();

        initActiveBlock();

        initPenaltyBlock();

        initTaxGroupBlock();

        initChargeFrequencyBlock();

        initFrequencyIntervalBlock();
    }

    protected void initFrequencyIntervalBlock() {
        this.frequencyIntervalBlock = new WebMarkupBlock("frequencyIntervalBlock", Size.Six_6);
        this.form.add(this.frequencyIntervalBlock);
        this.frequencyIntervalIContainer = new WebMarkupContainer("frequencyIntervalIContainer");
        this.frequencyIntervalBlock.add(this.frequencyIntervalIContainer);
        this.frequencyIntervalField = new TextField<>("frequencyIntervalField", new PropertyModel<>(this, "frequencyIntervalValue"));
        this.frequencyIntervalField.setLabel(Model.of("Frequency Interval"));
        this.frequencyIntervalField.add(new OnChangeAjaxBehavior());
        this.frequencyIntervalIContainer.add(this.frequencyIntervalField);
        this.frequencyIntervalFeedback = new TextFeedbackPanel("frequencyIntervalFeedback", this.frequencyIntervalField);
        this.frequencyIntervalIContainer.add(this.frequencyIntervalFeedback);
    }

    protected void initChargeFrequencyBlock() {
        this.chargeFrequencyBlock = new WebMarkupBlock("chargeFrequencyBlock", Size.Six_6);
        this.form.add(this.chargeFrequencyBlock);
        this.chargeFrequencyIContainer = new WebMarkupContainer("chargeFrequencyIContainer");
        this.chargeFrequencyBlock.add(this.chargeFrequencyIContainer);
        this.chargeFrequencyProvider = new ChargeFrequencyProvider();
        this.chargeFrequencyField = new Select2SingleChoice<>("chargeFrequencyField", new PropertyModel<>(this, "chargeFrequencyValue"), this.chargeFrequencyProvider);
        this.chargeFrequencyField.setLabel(Model.of("Charge Frequency"));
        this.chargeFrequencyField.add(new OnChangeAjaxBehavior());
        this.chargeFrequencyIContainer.add(this.chargeFrequencyField);
        this.chargeFrequencyFeedback = new TextFeedbackPanel("chargeFrequencyFeedback", this.chargeFrequencyField);
        this.chargeFrequencyIContainer.add(this.chargeFrequencyFeedback);
    }

    protected void initTaxGroupBlock() {
        this.taxGroupBlock = new WebMarkupBlock("taxGroupBlock", Size.Six_6);
        this.form.add(this.taxGroupBlock);
        this.taxGroupIContainer = new WebMarkupContainer("taxGroupIContainer");
        this.taxGroupBlock.add(this.taxGroupIContainer);
        this.taxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
        this.taxGroupField.setLabel(Model.of("Tax Group"));
        this.taxGroupField.add(new OnChangeAjaxBehavior());
        this.taxGroupIContainer.add(this.taxGroupField);
        this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
        this.taxGroupIContainer.add(this.taxGroupFeedback);
    }

    protected void initPenaltyBlock() {
        this.penaltyBlock = new WebMarkupBlock("penaltyBlock", Size.Three_3);
        this.form.add(this.penaltyBlock);
        this.penaltyIContainer = new WebMarkupContainer("penaltyIContainer");
        this.penaltyBlock.add(this.penaltyIContainer);
        this.penaltyField = new CheckBox("penaltyField", new PropertyModel<>(this, "penaltyValue"));
        this.penaltyField.setRequired(true);
        this.penaltyField.add(new OnChangeAjaxBehavior());
        this.penaltyIContainer.add(this.penaltyField);
        this.penaltyFeedback = new TextFeedbackPanel("penaltyFeedback", this.penaltyField);
        this.penaltyIContainer.add(this.penaltyFeedback);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Three_3);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeField.add(new OnChangeAjaxBehavior());
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initAmountBlock() {
        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setLabel(Model.of("Amount"));
        this.amountField.setRequired(true);
        this.amountField.add(new OnChangeAjaxBehavior());
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);
    }

    protected void initFeeFrequencyBlock() {
        this.feeFrequencyBlock = new WebMarkupBlock("feeFrequencyBlock", Size.Six_6);
        this.form.add(this.feeFrequencyBlock);
        this.feeFrequencyIContainer = new WebMarkupContainer("feeFrequencyIContainer");
        this.feeFrequencyBlock.add(this.feeFrequencyIContainer);
        this.feeFrequencyField = new CheckBox("feeFrequencyField", new PropertyModel<>(this, "feeFrequencyValue"));
        this.feeFrequencyField.add(new OnChangeAjaxBehavior(this::feeFrequencyUpdate));
        this.feeFrequencyIContainer.add(this.feeFrequencyField);
        this.feeFrequencyFeedback = new TextFeedbackPanel("feeFrequencyFeedback", this.feeFrequencyField);
        this.feeFrequencyIContainer.add(this.feeFrequencyFeedback);
    }

    protected void initChargePaymentBlock() {
        this.chargePaymentBlock = new WebMarkupBlock("chargePaymentBlock", Size.Six_6);
        this.form.add(this.chargePaymentBlock);
        this.chargePaymentIContainer = new WebMarkupContainer("chargePaymentIContainer");
        this.chargePaymentBlock.add(this.chargePaymentIContainer);
        this.chargePaymentProvider = new ChargePaymentProvider();
        this.chargePaymentField = new Select2SingleChoice<>("chargePaymentField", new PropertyModel<>(this, "chargePaymentValue"), this.chargePaymentProvider);
        this.chargePaymentField.setLabel(Model.of("Charge payment by"));
        this.chargePaymentField.add(new OnChangeAjaxBehavior());
        this.chargePaymentField.setRequired(true);
        this.chargePaymentIContainer.add(this.chargePaymentField);
        this.chargePaymentFeedback = new TextFeedbackPanel("chargePaymentFeedback", this.chargePaymentField);
        this.chargePaymentIContainer.add(this.chargePaymentFeedback);
    }

    protected void initChargeCalculationBlock() {
        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationIContainer = new WebMarkupContainer("chargeCalculationIContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationIContainer);
        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField.add(new OnChangeAjaxBehavior());
        this.chargeCalculationIContainer.add(this.chargeCalculationField);
        this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback", this.chargeCalculationField);
        this.chargeCalculationIContainer.add(this.chargeCalculationFeedback);
    }

    protected void initChargeTimeBlock() {
        this.chargeTimeBlock = new WebMarkupBlock("chargeTimeBlock", Size.Six_6);
        this.form.add(this.chargeTimeBlock);
        this.chargeTimeIContainer = new WebMarkupContainer("chargeTimeIContainer");
        this.chargeTimeBlock.add(this.chargeTimeIContainer);
        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.Disbursement, ChargeTime.SpecifiedDueDate, ChargeTime.InstallmentFee, ChargeTime.OverdueFees, ChargeTime.TrancheDisbursement);
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
        this.chargeTimeField.setLabel(Model.of("Charge time type"));
        this.chargeTimeField.setRequired(true);
        this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate));
        this.chargeTimeIContainer.add(this.chargeTimeField);
        this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
        this.chargeTimeIContainer.add(this.chargeTimeFeedback);
    }

    protected void initCurrencyBlock() {
        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        this.form.add(this.currencyBlock);
        this.currencyIContainer = new WebMarkupContainer("currencyIContainer");
        this.currencyBlock.add(this.currencyIContainer);
        this.currencyProvider = new CurrencyProvider();
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.add(new OnChangeAjaxBehavior());
        this.currencyField.setRequired(true);
        this.currencyIContainer.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.currencyIContainer.add(this.currencyFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new OnChangeAjaxBehavior());
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        chargeTimeFieldUpdate(null);
        feeFrequencyUpdate(null);
    }

    protected boolean feeFrequencyUpdate(AjaxRequestTarget target) {
        boolean visible = this.feeFrequencyValue == null ? false : this.feeFrequencyValue;
        this.chargeFrequencyIContainer.setVisible(visible);
        this.frequencyIntervalIContainer.setVisible(visible);

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

        if (target != null) {
            this.chargeFrequencyValue = null;
            this.frequencyIntervalValue = null;
            this.chargeCalculationValue = null;
        }

        ChargeTime chargeTime = this.chargeTimeValue == null ? null : ChargeTime.valueOf(this.chargeTimeValue.getId());
        if (chargeTime == ChargeTime.Disbursement) {
            this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
        } else if (chargeTime == ChargeTime.SpecifiedDueDate) {
            this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
        } else if (chargeTime == ChargeTime.InstallmentFee) {
            this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
        } else if (chargeTime == ChargeTime.OverdueFees) {
            this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest);
        } else if (chargeTime == ChargeTime.TrancheDisbursement) {
            this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.DisbursementAmount);
        } else {
            this.chargeCalculationProvider.setValues();
        }

        this.chargeCalculationIContainer.setVisible(chargeTime != null);
        this.chargeCalculationField.setRequired(chargeTime != null);

        boolean visible = chargeTime == ChargeTime.OverdueFees;
        this.feeFrequencyIContainer.setVisible(visible);

        if (target != null) {
            target.add(this.chargeCalculationBlock);
            target.add(this.feeFrequencyBlock);
        }

        feeFrequencyUpdate(target);

        return false;
    }

    protected void saveButtonSubmit(Button button) {
        ChargeTime chargeTime = null;
        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        ChargeBuilder builder = new ChargeBuilder();
        builder.withId(this.chargeId);
        builder.withName(this.nameValue);
        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        } else {
            builder.withCurrencyCode(null);
        }
        builder.withChargeTimeType(chargeTime);
        if (this.chargeCalculationValue != null) {
            builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
        } else {
            builder.withChargeCalculationType(null);
        }
        if (this.chargePaymentValue != null) {
            builder.withChargePaymentMode(ChargePayment.valueOf(this.chargePaymentValue.getId()));
        } else {
            builder.withChargePaymentMode(null);
        }
        if (chargeTime == ChargeTime.OverdueFees && this.feeFrequencyValue != null && this.feeFrequencyValue) {
            if (this.chargeFrequencyValue != null) {
                builder.withFeeFrequency(ChargeFrequency.valueOf(this.chargeFrequencyValue.getId()));
            } else {
                builder.withFeeFrequency(null);
            }
            builder.withFeeInterval(this.frequencyIntervalValue);
        } else {
            builder.withFeeFrequency(null);
            builder.withFeeInterval(null);
        }
        builder.withAmount(this.amountValue);
        builder.withActive(this.activeValue);
        builder.withPenalty(this.penaltyValue);
        if (this.taxGroupValue != null) {
            builder.withTaxGroupId(this.taxGroupValue.getId());
        } else {
            builder.withTaxGroupId(null);
        }

        JsonNode node = ChargeHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ChargeBrowsePage.class);
    }

}
