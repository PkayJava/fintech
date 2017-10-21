package com.angkorteam.fintech.pages.charge;

import java.math.BigDecimal;
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
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanChargeModifyPage extends Page {

    protected String chargeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected ChargeTimeProvider chargeTimeProvider;
    protected Option chargeTimeValue;
    protected Select2SingleChoice<Option> chargeTimeField;
    protected TextFeedbackPanel chargeTimeFeedback;

    protected WebMarkupContainer chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationContainer;
    protected ChargeCalculationProvider chargeCalculationProvider;
    protected Option chargeCalculationValue;
    protected Select2SingleChoice<Option> chargeCalculationField;
    protected TextFeedbackPanel chargeCalculationFeedback;

    protected ChargePaymentProvider chargePaymentProvider;
    protected Option chargePaymentValue;
    protected Select2SingleChoice<Option> chargePaymentField;
    protected TextFeedbackPanel chargePaymentFeedback;

    protected WebMarkupContainer feeFrequencyBlock;
    protected WebMarkupContainer feeFrequencyContainer;
    protected Boolean feeFrequencyValue;
    protected CheckBox feeFrequencyField;
    protected TextFeedbackPanel feeFrequencyFeedback;

    protected WebMarkupContainer chargeFrequencyBlock;
    protected WebMarkupContainer chargeFrequencyContainer;
    protected ChargeFrequencyProvider chargeFrequencyProvider;
    protected Option chargeFrequencyValue;
    protected Select2SingleChoice<Option> chargeFrequencyField;
    protected TextFeedbackPanel chargeFrequencyFeedback;

    protected WebMarkupContainer frequencyIntervalBlock;
    protected WebMarkupContainer frequencyIntervalContainer;
    protected Integer frequencyIntervalValue;
    protected TextField<Integer> frequencyIntervalField;
    protected TextFeedbackPanel frequencyIntervalFeedback;

    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected boolean penaltyValue;
    protected CheckBox penaltyField;
    protected TextFeedbackPanel penaltyFeedback;

    protected SingleChoiceProvider taxGroupProvider;
    protected Option taxGroupValue;
    protected Select2SingleChoice<Option> taxGroupField;
    protected TextFeedbackPanel taxGroupFeedback;

    protected static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Loan Charge Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeId);

        this.nameValue = (String) chargeObject.get("name");
        this.currencyValue = jdbcTemplate.queryForObject("select code id, concat(name,' [', code,']') text from m_organisation_currency where code = ?", Option.MAPPER, chargeObject.get("currency_code"));

        String charge_time_enum = String.valueOf(chargeObject.get("charge_time_enum"));
        this.chargeTimeValue = ChargeTime.optionLiteral(charge_time_enum);

        String charge_calculation_enum = String.valueOf(chargeObject.get("charge_calculation_enum"));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(charge_calculation_enum);

        String charge_payment_mode_enum = String.valueOf(chargeObject.get("charge_payment_mode_enum"));
        this.chargePaymentValue = ChargePayment.optionLiteral(charge_payment_mode_enum);

        if (chargeObject.get("amount") instanceof BigDecimal) {
            this.amountValue = ((BigDecimal) chargeObject.get("amount")).doubleValue();
        } else if (chargeObject.get("amount") instanceof Double) {
            this.amountValue = (Double) chargeObject.get("amount");
        }

        this.activeValue = (Boolean) chargeObject.get("is_active");

        this.penaltyValue = (Boolean) chargeObject.get("is_penalty");

        this.taxGroupValue = jdbcTemplate.queryForObject("select id, name text from m_tax_group where id = ?", Option.MAPPER, chargeObject.get("tax_group_id"));

        String fee_frequency = String.valueOf(chargeObject.get("fee_frequency"));
        this.chargeFrequencyValue = ChargeFrequency.optionLiteral(fee_frequency);

        this.frequencyIntervalValue = (Integer) chargeObject.get("fee_interval");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

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

        this.currencyProvider = new CurrencyProvider();
        this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.add(new OnChangeAjaxBehavior());
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.Disbursement, ChargeTime.SpecifiedDueDate, ChargeTime.InstallmentFee, ChargeTime.OverdueFees, ChargeTime.TrancheDisbursement);
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", 0, new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
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
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0, new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField.add(new OnChangeAjaxBehavior());
        this.chargeCalculationContainer.add(this.chargeCalculationField);
        this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback", this.chargeCalculationField);
        this.chargeCalculationContainer.add(this.chargeCalculationFeedback);

        this.chargePaymentProvider = new ChargePaymentProvider();
        this.chargePaymentField = new Select2SingleChoice<>("chargePaymentField", 0, new PropertyModel<>(this, "chargePaymentValue"), this.chargePaymentProvider);
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
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
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
        this.chargeFrequencyField = new Select2SingleChoice<>("chargeFrequencyField", 0, new PropertyModel<>(this, "chargeFrequencyValue"), this.chargeFrequencyProvider);
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
        this.frequencyIntervalField = new TextField<>("frequencyIntervalField", new PropertyModel<>(this, "frequencyIntervalValue"));
        this.frequencyIntervalField.setLabel(Model.of("Frequency Interval"));
        this.frequencyIntervalField.add(new OnChangeAjaxBehavior());
        this.frequencyIntervalContainer.add(this.frequencyIntervalField);
        this.frequencyIntervalFeedback = new TextFeedbackPanel("frequencyIntervalFeedback", this.frequencyIntervalField);
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

        JsonNode node = null;
        try {
            node = ChargeHelper.update((Session) getSession(), builder.build());
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
