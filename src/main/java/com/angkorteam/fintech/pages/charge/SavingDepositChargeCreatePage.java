package com.angkorteam.fintech.pages.charge;

import java.util.Date;
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
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingDepositChargeCreatePage extends Page {

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

    protected WebMarkupBlock dueDateBlock;
    protected WebMarkupContainer dueDateIContainer;
    protected Date dueDateValue;
    protected DayMonthTextField dueDateField;
    protected TextFeedbackPanel dueDateFeedback;

    protected WebMarkupBlock repeatEveryBlock;
    protected WebMarkupContainer repeatEveryIContainer;
    protected Long repeatEveryValue;
    protected TextField<Long> repeatEveryField;
    protected TextFeedbackPanel repeatEveryFeedback;

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
            breadcrumb.setLabel("Saving & Deposit Charge Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
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

        initDueDateBlock();

        initRepeatEveryBlock();

        initAmountBlock();

        initActiveBlock();

        initPenaltyBlock();

        initTaxGroupBlock();

    }

    protected void initTaxGroupBlock() {
        this.taxGroupBlock = new WebMarkupBlock("taxGroupBlock", Size.Six_6);
        this.form.add(this.taxGroupBlock);
        this.taxGroupIContainer = new WebMarkupContainer("taxGroupIContainer");
        this.taxGroupBlock.add(this.taxGroupIContainer);
        this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
        this.taxGroupField.setLabel(Model.of("Tax Group"));
        this.taxGroupField.add(new OnChangeAjaxBehavior());
        this.taxGroupIContainer.add(this.taxGroupField);
        this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
        this.taxGroupIContainer.add(this.taxGroupFeedback);
    }

    protected void initPenaltyBlock() {
        this.penaltyBlock = new WebMarkupBlock("penaltyBlock", Size.Six_6);
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
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Six_6);
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
        this.amountField.setRequired(true);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountField.add(new OnChangeAjaxBehavior());
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);
    }

    protected void initRepeatEveryBlock() {
        this.repeatEveryBlock = new WebMarkupBlock("repeatEveryBlock", Size.Six_6);
        this.form.add(this.repeatEveryBlock);
        this.repeatEveryIContainer = new WebMarkupContainer("repeatEveryIContainer");
        this.repeatEveryBlock.add(this.repeatEveryIContainer);
        this.repeatEveryField = new TextField<>("repeatEveryField", new PropertyModel<>(this, "repeatEveryValue"));
        this.repeatEveryField.setLabel(Model.of("Repeats every"));
        this.repeatEveryField.setRequired(true);
        this.repeatEveryField.add(new OnChangeAjaxBehavior());
        this.repeatEveryIContainer.add(this.repeatEveryField);
        this.repeatEveryFeedback = new TextFeedbackPanel("repeatEveryFeedback", this.repeatEveryField);
        this.repeatEveryIContainer.add(this.repeatEveryFeedback);
    }

    protected void initDueDateBlock() {
        this.dueDateBlock = new WebMarkupBlock("dueDateBlock", Size.Six_6);
        this.form.add(this.dueDateBlock);
        this.dueDateIContainer = new WebMarkupContainer("dueDateIContainer");
        this.dueDateBlock.add(this.dueDateIContainer);
        this.dueDateField = new DayMonthTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
        this.dueDateField.setRequired(true);
        this.dueDateField.setLabel(Model.of("Due date"));
        this.dueDateField.add(new OnChangeAjaxBehavior());
        this.dueDateIContainer.add(this.dueDateField);
        this.dueDateFeedback = new TextFeedbackPanel("dueDateFeedback", this.dueDateField);
        this.dueDateIContainer.add(this.dueDateFeedback);
    }

    protected void initChargeCalculationBlock() {
        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationIContainer = new WebMarkupContainer("chargeCalculationIContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationIContainer);
        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
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
        this.chargeTimeProvider.setValues(ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation, ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee, ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee);
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
        this.currencyField.setRequired(true);
        this.currencyField.add(new OnChangeAjaxBehavior());
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
    }

    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
        ChargeTime chargeTime = null;
        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        boolean dueDateVisible = chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee;
        boolean repeatEveryVisible = chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee;
        this.dueDateIContainer.setVisible(dueDateVisible);
        this.repeatEveryIContainer.setVisible(repeatEveryVisible);

        this.dueDateField.setRequired(dueDateVisible);
        this.repeatEveryField.setRequired(repeatEveryVisible);

        if (target != null) {
            target.add(this.dueDateBlock);
            target.add(this.repeatEveryBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {

        ChargeTime chargeTime = null;

        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        ChargeBuilder builder = new ChargeBuilder();
        builder.withChargeAppliesTo(ChargeType.SavingDeposit);
        builder.withName(this.nameValue);

        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        }

        if (this.chargeTimeValue != null) {
            builder.withChargeTimeType(chargeTime);
        }

        if (this.chargeCalculationValue != null) {
            builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
        }
        if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeOnMonthDay(this.dueDateValue);
        }
        if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeOnMonthDay(this.dueDateValue);
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
