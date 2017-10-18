package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FixedBuilder;
import com.angkorteam.fintech.dto.builder.FixedBuilder.IncentiveBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ApplyPenalOn;
import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.OperandType;
import com.angkorteam.fintech.dto.enums.Operator;
import com.angkorteam.fintech.helper.FixedHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.InterestRateChartPopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.fixed.ChargePopup;
import com.angkorteam.fintech.popup.fixed.FeeChargePopup;
import com.angkorteam.fintech.popup.fixed.IncentivePopup;
import com.angkorteam.fintech.popup.fixed.PenaltyChargePopup;
import com.angkorteam.fintech.provider.ApplyPenalOnProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FixedDepositCreatePage extends Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedDepositCreatePage.class);

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupContainer detailProductNameBlock;
    protected WebMarkupContainer detailProductNameContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupContainer detailShortNameBlock;
    protected WebMarkupContainer detailShortNameContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupContainer detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupContainer currencyCodeBlock;
    protected WebMarkupContainer currencyCodeContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupContainer currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceContainer;
    protected Integer currencyDecimalPlaceValue;
    protected TextField<Integer> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupContainer currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfContainer;
    protected Integer currencyMultipleOfValue;
    protected TextField<Integer> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    protected WebMarkupContainer termDefaultDepositAmountBlock;
    protected WebMarkupContainer termDefaultDepositAmountContainer;
    protected Double termDefaultDepositAmountValue;
    protected TextField<Double> termDefaultDepositAmountField;
    protected TextFeedbackPanel termDefaultDepositAmountFeedback;

    protected WebMarkupContainer termMinimumDepositAmountBlock;
    protected WebMarkupContainer termMinimumDepositAmountContainer;
    protected Double termMinimumDepositAmountValue;
    protected TextField<Double> termMinimumDepositAmountField;
    protected TextFeedbackPanel termMinimumDepositAmountFeedback;

    protected WebMarkupContainer termMaximumDepositAmountBlock;
    protected WebMarkupContainer termMaximumDepositAmountContainer;
    protected Double termMaximumDepositAmountValue;
    protected TextField<Double> termMaximumDepositAmountField;
    protected TextFeedbackPanel termMaximumDepositAmountFeedback;

    protected WebMarkupContainer termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Option termInterestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    protected TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    protected WebMarkupContainer termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Option termInterestPostingPeriodValue;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
    protected TextFeedbackPanel termInterestPostingPeriodFeedback;

    protected WebMarkupContainer termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Option termInterestCalculatedUsingValue;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
    protected TextFeedbackPanel termInterestCalculatedUsingFeedback;

    protected WebMarkupContainer termDayInYearBlock;
    protected WebMarkupContainer termDayInYearContainer;
    protected DayInYearProvider termDayInYearProvider;
    protected Option termDayInYearValue;
    protected Select2SingleChoice<Option> termDayInYearField;
    protected TextFeedbackPanel termDayInYearFeedback;

    // Setting

    protected WebMarkupContainer settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodContainer;
    protected Integer settingLockInPeriodValue;
    protected TextField<Integer> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupContainer settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupContainer settingMinimumDepositTermBlock;
    protected WebMarkupContainer settingMinimumDepositTermContainer;
    protected Integer settingMinimumDepositTermValue;
    protected TextField<Integer> settingMinimumDepositTermField;
    protected TextFeedbackPanel settingMinimumDepositTermFeedback;

    protected WebMarkupContainer settingMinimumDepositTypeBlock;
    protected WebMarkupContainer settingMinimumDepositTypeContainer;
    protected LockInTypeProvider settingMinimumDepositTypeProvider;
    protected Option settingMinimumDepositTypeValue;
    protected Select2SingleChoice<Option> settingMinimumDepositTypeField;
    protected TextFeedbackPanel settingMinimumDepositTypeFeedback;

    protected WebMarkupContainer settingInMultiplesOfBlock;
    protected WebMarkupContainer settingInMultiplesOfContainer;
    protected Integer settingInMultiplesOfValue;
    protected TextField<Integer> settingInMultiplesOfField;
    protected TextFeedbackPanel settingInMultiplesOfFeedback;

    protected WebMarkupContainer settingInMultiplesTypeBlock;
    protected WebMarkupContainer settingInMultiplesTypeContainer;
    protected LockInTypeProvider settingInMultiplesTypeProvider;
    protected Option settingInMultiplesTypeValue;
    protected Select2SingleChoice<Option> settingInMultiplesTypeField;
    protected TextFeedbackPanel settingInMultiplesTypeFeedback;

    protected WebMarkupContainer settingMaximumDepositTermBlock;
    protected WebMarkupContainer settingMaximumDepositTermContainer;
    protected Integer settingMaximumDepositTermValue;
    protected TextField<Integer> settingMaximumDepositTermField;
    protected TextFeedbackPanel settingMaximumDepositTermFeedback;

    protected WebMarkupContainer settingMaximumDepositTypeBlock;
    protected WebMarkupContainer settingMaximumDepositTypeContainer;
    protected LockInTypeProvider settingMaximumDepositTypeProvider;
    protected Option settingMaximumDepositTypeValue;
    protected Select2SingleChoice<Option> settingMaximumDepositTypeField;
    protected TextFeedbackPanel settingMaximumDepositTypeFeedback;

    protected WebMarkupContainer settingForPreMatureClosureBlock;
    protected WebMarkupContainer settingForPreMatureClosureContainer;
    protected Boolean settingForPreMatureClosureValue;
    protected CheckBox settingForPreMatureClosureField;
    protected TextFeedbackPanel settingForPreMatureClosureFeedback;

    protected WebMarkupContainer settingApplyPenalInterestBlock;
    protected WebMarkupContainer settingApplyPenalInterestContainer;
    protected Double settingApplyPenalInterestValue;
    protected TextField<Double> settingApplyPenalInterestField;
    protected TextFeedbackPanel settingApplyPenalInterestFeedback;

    protected WebMarkupContainer settingApplyPenalOnBlock;
    protected WebMarkupContainer settingApplyPenalOnContainer;
    protected ApplyPenalOnProvider settingApplyPenalOnProvider;
    protected Option settingApplyPenalOnValue;
    protected Select2SingleChoice<Option> settingApplyPenalOnField;
    protected TextFeedbackPanel settingApplyPenalOnFeedback;

    protected WebMarkupContainer settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupContainer settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Option settingTaxGroupValue;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    // Interest Rate Chart

    protected WebMarkupContainer interestRateValidFromDateBlock;
    protected WebMarkupContainer interestRateValidFromDateContainer;
    protected Date interestRateValidFromDateValue;
    protected DateTextField interestRateValidFromDateField;
    protected TextFeedbackPanel interestRateValidFromDateFeedback;

    protected WebMarkupContainer interestRateValidEndDateBlock;
    protected WebMarkupContainer interestRateValidEndDateContainer;
    protected Date interestRateValidEndDateValue;
    protected DateTextField interestRateValidEndDateField;
    protected TextFeedbackPanel interestRateValidEndDateFeedback;

    protected WebMarkupContainer interestRatePrimaryGroupingByAmountBlock;
    protected WebMarkupContainer interestRatePrimaryGroupingByAmountContainer;
    protected Boolean interestRatePrimaryGroupingByAmountValue;
    protected CheckBox interestRatePrimaryGroupingByAmountField;
    protected TextFeedbackPanel interestRatePrimaryGroupingByAmountFeedback;

    protected List<Map<String, Object>> interestRateChartValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> interestRateChartTable;
    protected ListDataProvider interestRateChartProvider;
    protected ModalWindow interestRateChartPopup;
    protected AjaxLink<Void> interestRateChartAddLink;

    protected ModalWindow incentivePopup;

    // Charges

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;

    // Accounting

    protected String accountingValue = ACC_NONE;
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashContainer;

    protected SingleChoiceProvider cashSavingReferenceProvider;
    protected Option cashSavingReferenceValue;
    protected Select2SingleChoice<Option> cashSavingReferenceField;
    protected TextFeedbackPanel cashSavingReferenceFeedback;

    protected SingleChoiceProvider cashSavingControlProvider;
    protected Option cashSavingControlValue;
    protected Select2SingleChoice<Option> cashSavingControlField;
    protected TextFeedbackPanel cashSavingControlFeedback;

    protected SingleChoiceProvider cashSavingsTransfersInSuspenseProvider;
    protected Option cashSavingsTransfersInSuspenseValue;
    protected Select2SingleChoice<Option> cashSavingsTransfersInSuspenseField;
    protected TextFeedbackPanel cashSavingsTransfersInSuspenseFeedback;

    protected SingleChoiceProvider cashInterestOnSavingsProvider;
    protected Option cashInterestOnSavingsValue;
    protected Select2SingleChoice<Option> cashInterestOnSavingsField;
    protected TextFeedbackPanel cashInterestOnSavingsFeedback;

    protected SingleChoiceProvider cashIncomeFromFeesProvider;
    protected Option cashIncomeFromFeesValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeesField;
    protected TextFeedbackPanel cashIncomeFromFeesFeedback;

    protected SingleChoiceProvider cashIncomeFromPenaltiesProvider;
    protected Option cashIncomeFromPenaltiesValue;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltiesField;
    protected TextFeedbackPanel cashIncomeFromPenaltiesFeedback;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleContainer;

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    protected ModalWindow fundSourcePopup;

    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    protected ModalWindow feeIncomePopup;

    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    protected ModalWindow penaltyIncomePopup;

    protected Option itemChargeValue;
    protected Option itemPeriodTypeValue;
    protected Integer itemPeriodFromValue;
    protected Integer itemPeriodToValue;
    protected Integer itemAmountRangeFromValue;
    protected Integer itemAmountRangeToValue;
    protected Double itemInterestValue;
    protected String itemDescriptionValue;
    protected Option itemPaymentValue;
    protected Option itemAccountValue;

    protected ModalWindow currencyPopup;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newLinkedList();
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
            breadcrumb.setLabel("Fixed Deposit Product");
            breadcrumb.setPage(FixedDepositBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Fixed Deposit Product Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedDepositBrowsePage.class);
        this.form.add(this.closeLink);

        this.currencyPopup = new ModalWindow("currencyPopup");
        add(this.currencyPopup);

        initDetail();

        initCurrency();

        initTerm();

        initSetting();

        initInterestRateChart();

        initCharge();

        initAccounting();

        initDefault();

        initValidationRule();
    }

    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2;
        this.currencyMultipleOfValue = 1;

        this.termDefaultDepositAmountValue = 100d;
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Daily.toOption();
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.DailyBalance.toOption();
        this.termInterestPostingPeriodValue = InterestPostingPeriod.Monthly.toOption();
        this.termDayInYearValue = DayInYear.D365.toOption();
        this.settingMinimumDepositTermValue = 1;
        this.settingMinimumDepositTypeValue = LockInType.Month.toOption();
        this.accountingValue = ACC_NONE;
    }

    protected void initValidationRule() {
        this.detailProductNameField.setRequired(true);
        this.detailShortNameField.setRequired(true);
        this.detailShortNameField.add(StringValidator.exactLength(4));
        this.detailDescriptionField.setRequired(true);

        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyCodeField.setRequired(true);
        this.currencyMultipleOfField.setRequired(true);

        this.termInterestCompoundingPeriodField.setRequired(true);
        this.termInterestPostingPeriodField.setRequired(true);
        this.termInterestCalculatedUsingField.setRequired(true);
        this.termDayInYearField.setRequired(true);

        this.settingMinimumDepositTermField.setRequired(true);
        this.settingMinimumDepositTypeField.setRequired(true);

        this.termDefaultDepositAmountField.setRequired(true);

        this.accountingField.setRequired(true);
    }

    protected void initDefault() {

        settingWithholdTaxApplicableFieldUpdate(null);
        accountingFieldUpdate(null);

    }

    protected void initAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(ACC_NONE)));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(ACC_CASH)));
        this.form.add(this.accountingField);

        initAccountingCash();

        initAdvancedAccountingRule();
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashContainer = new WebMarkupContainer("cashContainer");
        this.cashBlock.add(this.cashContainer);

        this.cashSavingReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
        this.cashSavingReferenceField.setLabel(Model.of("Saving reference"));
        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingReferenceField);
        this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback", this.cashSavingReferenceField);
        this.cashContainer.add(this.cashSavingReferenceFeedback);

        this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
        this.cashSavingControlField.setLabel(Model.of("Saving control"));
        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingControlField);
        this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback", this.cashSavingControlField);
        this.cashContainer.add(this.cashSavingControlFeedback);

        this.cashSavingsTransfersInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingsTransfersInSuspenseField = new Select2SingleChoice<>("cashSavingsTransfersInSuspenseField", new PropertyModel<>(this, "cashSavingsTransfersInSuspenseValue"), this.cashSavingsTransfersInSuspenseProvider);
        this.cashSavingsTransfersInSuspenseField.setLabel(Model.of("Savings transfers in suspense"));
        this.cashSavingsTransfersInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingsTransfersInSuspenseField);
        this.cashSavingsTransfersInSuspenseFeedback = new TextFeedbackPanel("cashSavingsTransfersInSuspenseFeedback", this.cashSavingsTransfersInSuspenseField);
        this.cashContainer.add(this.cashSavingsTransfersInSuspenseFeedback);

        this.cashInterestOnSavingsProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashInterestOnSavingsProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashInterestOnSavingsProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.cashInterestOnSavingsField = new Select2SingleChoice<>("cashInterestOnSavingsField", new PropertyModel<>(this, "cashInterestOnSavingsValue"), this.cashInterestOnSavingsProvider);
        this.cashInterestOnSavingsField.setLabel(Model.of("Interest on savings"));
        this.cashInterestOnSavingsField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashInterestOnSavingsField);
        this.cashInterestOnSavingsFeedback = new TextFeedbackPanel("cashInterestOnSavingsFeedback", this.cashInterestOnSavingsField);
        this.cashContainer.add(this.cashInterestOnSavingsFeedback);

        this.cashIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeesField = new Select2SingleChoice<>("cashIncomeFromFeesField", new PropertyModel<>(this, "cashIncomeFromFeesValue"), this.cashIncomeFromFeesProvider);
        this.cashIncomeFromFeesField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeesField);
        this.cashIncomeFromFeesFeedback = new TextFeedbackPanel("cashIncomeFromFeesFeedback", this.cashIncomeFromFeesField);
        this.cashContainer.add(this.cashIncomeFromFeesFeedback);

        this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField", new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
        this.cashIncomeFromPenaltiesField.setLabel(Model.of("Income from penalties"));
        this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromPenaltiesField);
        this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback", this.cashIncomeFromPenaltiesField);
        this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);
    }

    protected void initAdvancedAccountingRule() {
        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleContainer = new WebMarkupContainer("advancedAccountingRuleContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleContainer);

        // Table
        {
            this.fundSourcePopup = new ModalWindow("fundSourcePopup");
            add(this.fundSourcePopup);
            this.fundSourcePopup.setOnClose(this::fundSourcePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn = Lists.newLinkedList();
            advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourcePaymentColumn));
            advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceAccountColumn));
            advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceActionItem, this::advancedAccountingRuleFundSourceActionClick));
            this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
            this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceTable);
            this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
            this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

            this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
            this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceAddLink);
        }

        // Table
        {
            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
            add(this.feeIncomePopup);
            this.feeIncomePopup.setOnClose(this::feeIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn = Lists.newLinkedList();
            advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeChargeColumn));
            advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeAccountColumn));
            advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeActionItem, this::advancedAccountingRuleFeeIncomeActionClick));
            this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
            this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeTable);
            this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
            this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

            this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
            this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
        }

        // Table
        {
            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
            add(this.penaltyIncomePopup);
            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn = Lists.newLinkedList();
            advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeChargeColumn));
            advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeAccountColumn));
            advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeActionItem, this::advancedAccountingRulePenaltyIncomeActionClick));
            this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
            this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
            this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
            this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

            this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
            this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);
        }
    }

    protected void feeIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRuleFeeIncomeValue.add(item);
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void penaltyIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRulePenaltyIncomeValue.add(item);
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected void fundSourcePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("paymentId", this.itemPaymentValue.getId());
        item.put("payment", this.itemPaymentValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRuleFundSourceValue.add(item);
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        if (this.currencyCodeValue != null) {
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup(this.penaltyIncomePopup.getContentId(), this.penaltyIncomePopup, this, this.currencyCodeValue.getId()));
            this.penaltyIncomePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void advancedAccountingRulePenaltyIncomeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRulePenaltyIncomeValue.remove(index);
        }
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        if (this.currencyCodeValue != null) {
            this.feeIncomePopup.setContent(new FeeChargePopup(this.feeIncomePopup.getContentId(), this.feeIncomePopup, this, this.currencyCodeValue.getId()));
            this.feeIncomePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void advancedAccountingRuleFeeIncomeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFeeIncomeValue.remove(index);
        }
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void advancedAccountingRuleFundSourceActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFundSourceValue.remove(index);
        }
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected List<ActionItem> advancedAccountingRuleFundSourceActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.fundSourcePopup.setContent(new PaymentTypePopup(this.fundSourcePopup.getContentId(), this.fundSourcePopup, this));
        this.fundSourcePopup.show(target);
        return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourcePaymentColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRuleFundSourceAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashContainer.setVisible(false);
        this.advancedAccountingRuleContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initCharge() {
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newLinkedList();
        chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeNameColumn));
        chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeTypeColumn));
        chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeAmountColumn));
        chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeCollectColumn));
        chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeDateColumn));
        chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeActionItem, this::chargeActionClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);
    }

    protected void chargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String chargeId = this.itemChargeValue.getId();
        for (Map<String, Object> temp : this.chargeValue) {
            if (chargeId.equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?", chargeId);
        String type = (String) chargeObject.get("type");
        for (ChargeCalculation calculation : ChargeCalculation.values()) {
            if (type.equals(calculation.getLiteral())) {
                type = calculation.getDescription();
                break;
            }
        }
        String collect = (String) chargeObject.get("collect");
        for (ChargeTime time : ChargeTime.values()) {
            if (collect.equals(time.getLiteral())) {
                collect = time.getDescription();
                break;
            }
        }
        item.put("uuid", chargeId);
        item.put("chargeId", chargeId);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void chargeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.size(); i++) {
            Map<String, Object> column = this.chargeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.remove(index);
        }
        target.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initInterestRateChart() {
        this.interestRateValidFromDateBlock = new WebMarkupContainer("interestRateValidFromDateBlock");
        this.interestRateValidFromDateBlock.setOutputMarkupId(true);
        this.form.add(this.interestRateValidFromDateBlock);
        this.interestRateValidFromDateContainer = new WebMarkupContainer("interestRateValidFromDateContainer");
        this.interestRateValidFromDateBlock.add(this.interestRateValidFromDateContainer);
        this.interestRateValidFromDateField = new DateTextField("interestRateValidFromDateField", new PropertyModel<>(this, "interestRateValidFromDateValue"));
        this.interestRateValidFromDateField.setLabel(Model.of("Valid From Date"));
        this.interestRateValidFromDateContainer.add(this.interestRateValidFromDateField);
        this.interestRateValidFromDateFeedback = new TextFeedbackPanel("interestRateValidFromDateFeedback", this.interestRateValidFromDateField);
        this.interestRateValidFromDateContainer.add(this.interestRateValidFromDateFeedback);

        this.interestRateValidEndDateBlock = new WebMarkupContainer("interestRateValidEndDateBlock");
        this.interestRateValidEndDateBlock.setOutputMarkupId(true);
        this.form.add(this.interestRateValidEndDateBlock);
        this.interestRateValidEndDateContainer = new WebMarkupContainer("interestRateValidEndDateContainer");
        this.interestRateValidEndDateBlock.add(this.interestRateValidEndDateContainer);
        this.interestRateValidEndDateField = new DateTextField("interestRateValidEndDateField", new PropertyModel<>(this, "interestRateValidEndDateValue"));
        this.interestRateValidEndDateField.setLabel(Model.of("End Date"));
        this.interestRateValidEndDateContainer.add(this.interestRateValidEndDateField);
        this.interestRateValidEndDateFeedback = new TextFeedbackPanel("interestRateValidEndDateFeedback", this.interestRateValidEndDateField);
        this.interestRateValidEndDateContainer.add(this.interestRateValidEndDateFeedback);

        this.interestRatePrimaryGroupingByAmountBlock = new WebMarkupContainer("interestRatePrimaryGroupingByAmountBlock");
        this.form.add(this.interestRatePrimaryGroupingByAmountBlock);
        this.interestRatePrimaryGroupingByAmountContainer = new WebMarkupContainer("interestRatePrimaryGroupingByAmountContainer");
        this.interestRatePrimaryGroupingByAmountBlock.add(this.interestRatePrimaryGroupingByAmountContainer);
        this.interestRatePrimaryGroupingByAmountField = new CheckBox("interestRatePrimaryGroupingByAmountField", new PropertyModel<>(this, "interestRatePrimaryGroupingByAmountValue"));
        this.interestRatePrimaryGroupingByAmountField.add(new OnChangeAjaxBehavior());
        this.interestRatePrimaryGroupingByAmountContainer.add(this.interestRatePrimaryGroupingByAmountField);
        this.interestRatePrimaryGroupingByAmountFeedback = new TextFeedbackPanel("interestRatePrimaryGroupingByAmountFeedback", this.interestRatePrimaryGroupingByAmountField);
        this.interestRatePrimaryGroupingByAmountContainer.add(this.interestRatePrimaryGroupingByAmountFeedback);

        // Table
        this.interestRateChartPopup = new ModalWindow("interestRateChartPopup");
        this.interestRateChartPopup.setHeightUnit("px");
        this.interestRateChartPopup.setWidthUnit("px");
        this.interestRateChartPopup.setInitialHeight(600);
        this.interestRateChartPopup.setInitialWidth(1000);
        add(this.interestRateChartPopup);
        this.interestRateChartPopup.setOnClose(this::interestRateChartPopupOnClose);

        this.incentivePopup = new ModalWindow("incentivePopup");
        this.incentivePopup.setHeightUnit("px");
        this.incentivePopup.setWidthUnit("px");
        this.incentivePopup.setInitialHeight(600);
        this.incentivePopup.setInitialWidth(1100);
        add(this.incentivePopup);
        this.incentivePopup.setOnClose(this::incentivePopupOnClose);

        List<IColumn<Map<String, Object>, String>> interestRateChartColumn = Lists.newLinkedList();
        interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType", this::interestRateChartPeriodTypeColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Period From"), "periodFrom", "periodFrom", this::interestRateChartPeriodFromColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Period To"), "periodTo", "periodTo", this::interestRateChartPeriodToColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Amount Range From"), "amountRangeFrom", "amountRangeFrom", this::interestRateChartAmountRangeFromColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Amount Range To"), "amountRangeTo", "amountRangeTo", this::interestRateChartAmountRangeToColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartInterestColumn));
        interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::interestRateChartDescriptionColumn));
        interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartActionItem, this::interestRateChartActionClick));
        this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue);
        this.interestRateChartTable = new DataTable<>("interestRateChartTable", interestRateChartColumn, this.interestRateChartProvider, 20);
        this.form.add(this.interestRateChartTable);
        this.interestRateChartTable.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
        this.interestRateChartTable.addBottomToolbar(new NoRecordsToolbar(this.interestRateChartTable));

        this.interestRateChartAddLink = new AjaxLink<>("interestRateChartAddLink");
        this.interestRateChartAddLink.setOnClick(this::interestRateChartAddLinkClick);
        this.form.add(this.interestRateChartAddLink);
    }

    protected void incentivePopupOnClose(String elementId, AjaxRequestTarget target) {
    }

    protected void interestRateChartPopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        item.put("periodType", this.itemPeriodTypeValue);
        item.put("periodFrom", this.itemPeriodFromValue);
        item.put("periodTo", this.itemPeriodToValue);
        item.put("amountRangeFrom", this.itemAmountRangeFromValue);
        item.put("amountRangeTo", this.itemAmountRangeToValue);
        item.put("interest", this.itemInterestValue);
        item.put("description", this.itemDescriptionValue);
        item.put("interestRate", Lists.newLinkedList());
        this.interestRateChartValue.add(item);
        target.add(this.interestRateChartTable);
    }

    protected boolean interestRateChartAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemPeriodTypeValue = null;
        this.itemPeriodFromValue = null;
        this.itemPeriodToValue = null;
        this.itemAmountRangeFromValue = null;
        this.itemAmountRangeToValue = null;
        this.itemInterestValue = null;
        this.itemDescriptionValue = null;
        this.interestRateChartPopup.setContent(new InterestRateChartPopup(this.interestRateChartPopup.getContentId(), this.interestRateChartPopup, this));
        this.interestRateChartPopup.show(target);
        return false;
    }

    protected ItemPanel interestRateChartPeriodTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value == null ? "" : value.getText());
    }

    protected ItemPanel interestRateChartPeriodFromColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestRateChartPeriodToColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestRateChartAmountRangeFromColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestRateChartAmountRangeToColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestRateChartInterestColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestRateChartDescriptionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void interestRateChartActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(link)) {
            int index = -1;
            for (int i = 0; i < this.interestRateChartValue.size(); i++) {
                Map<String, Object> column = this.interestRateChartValue.get(i);
                if (model.get("uuid").equals(column.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.interestRateChartValue.remove(index);
            }
            target.add(this.interestRateChartTable);
        } else if ("incentives".equals(link)) {
            List<Map<String, Object>> incentiveValue = (List<Map<String, Object>>) model.get("interestRate");
            this.incentivePopup.setContent(new IncentivePopup(this.incentivePopup.getContentId(), this.incentivePopup, incentiveValue));
            this.incentivePopup.show(target);
        }
    }

    protected List<ActionItem> interestRateChartActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newLinkedList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        actions.add(new ActionItem("incentives", Model.of("Incentives"), ItemCss.PRIMARY));
        return actions;
    }

    protected void initSetting() {
        this.settingLockInPeriodBlock = new WebMarkupContainer("settingLockInPeriodBlock");
        this.settingLockInPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodContainer = new WebMarkupContainer("settingLockInPeriodContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupContainer("settingLockInTypeBlock");
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeContainer = new WebMarkupContainer("settingLockInTypeContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0, new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeContainer.add(this.settingLockInTypeFeedback);

        this.settingMinimumDepositTermBlock = new WebMarkupContainer("settingMinimumDepositTermBlock");
        this.settingMinimumDepositTermBlock.setOutputMarkupId(true);
        this.form.add(this.settingMinimumDepositTermBlock);
        this.settingMinimumDepositTermContainer = new WebMarkupContainer("settingMinimumDepositTermContainer");
        this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermContainer);
        this.settingMinimumDepositTermField = new TextField<>("settingMinimumDepositTermField", new PropertyModel<>(this, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermField.setLabel(Model.of("Minimum Deposit Term"));

        this.settingMinimumDepositTermContainer.add(this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermFeedback = new TextFeedbackPanel("settingMinimumDepositTermFeedback", this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermContainer.add(this.settingMinimumDepositTermFeedback);

        this.settingMinimumDepositTypeBlock = new WebMarkupContainer("settingMinimumDepositTypeBlock");
        this.form.add(this.settingMinimumDepositTypeBlock);
        this.settingMinimumDepositTypeContainer = new WebMarkupContainer("settingMinimumDepositTypeContainer");
        this.settingMinimumDepositTypeBlock.add(this.settingMinimumDepositTypeContainer);
        this.settingMinimumDepositTypeProvider = new LockInTypeProvider();
        this.settingMinimumDepositTypeField = new Select2SingleChoice<>("settingMinimumDepositTypeField", 0, new PropertyModel<>(this, "settingMinimumDepositTypeValue"), this.settingMinimumDepositTypeProvider);
        this.settingMinimumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMinimumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTypeContainer.add(this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeFeedback = new TextFeedbackPanel("settingMinimumDepositTypeFeedback", this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeContainer.add(this.settingMinimumDepositTypeFeedback);

        this.settingInMultiplesOfBlock = new WebMarkupContainer("settingInMultiplesOfBlock");
        this.settingInMultiplesOfBlock.setOutputMarkupId(true);
        this.form.add(this.settingInMultiplesOfBlock);
        this.settingInMultiplesOfContainer = new WebMarkupContainer("settingInMultiplesOfContainer");
        this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfContainer);
        this.settingInMultiplesOfField = new TextField<>("settingInMultiplesOfField", new PropertyModel<>(this, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfField.setLabel(Model.of("And thereafter, In Multiples of"));
        this.settingInMultiplesOfContainer.add(this.settingInMultiplesOfField);
        this.settingInMultiplesOfFeedback = new TextFeedbackPanel("settingInMultiplesOfFeedback", this.settingInMultiplesOfField);
        this.settingInMultiplesOfContainer.add(this.settingInMultiplesOfFeedback);

        this.settingInMultiplesTypeBlock = new WebMarkupContainer("settingInMultiplesTypeBlock");
        this.form.add(this.settingInMultiplesTypeBlock);
        this.settingInMultiplesTypeContainer = new WebMarkupContainer("settingInMultiplesTypeContainer");
        this.settingInMultiplesTypeBlock.add(this.settingInMultiplesTypeContainer);
        this.settingInMultiplesTypeProvider = new LockInTypeProvider();
        this.settingInMultiplesTypeField = new Select2SingleChoice<>("settingInMultiplesTypeField", 0, new PropertyModel<>(this, "settingInMultiplesTypeValue"), this.settingInMultiplesTypeProvider);
        this.settingInMultiplesTypeField.setLabel(Model.of("Type"));
        this.settingInMultiplesTypeField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesTypeContainer.add(this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeFeedback = new TextFeedbackPanel("settingInMultiplesTypeFeedback", this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeContainer.add(this.settingInMultiplesTypeFeedback);

        this.settingMaximumDepositTermBlock = new WebMarkupContainer("settingMaximumDepositTermBlock");
        this.settingMaximumDepositTermBlock.setOutputMarkupId(true);
        this.form.add(this.settingMaximumDepositTermBlock);
        this.settingMaximumDepositTermContainer = new WebMarkupContainer("settingMaximumDepositTermContainer");
        this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermContainer);
        this.settingMaximumDepositTermField = new TextField<>("settingMaximumDepositTermField", new PropertyModel<>(this, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermField.setLabel(Model.of("Maximum Deposit Term"));
        this.settingMaximumDepositTermContainer.add(this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermFeedback = new TextFeedbackPanel("settingMaximumDepositTermFeedback", this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermContainer.add(this.settingMaximumDepositTermFeedback);

        this.settingMaximumDepositTypeBlock = new WebMarkupContainer("settingMaximumDepositTypeBlock");
        this.form.add(this.settingMaximumDepositTypeBlock);
        this.settingMaximumDepositTypeContainer = new WebMarkupContainer("settingMaximumDepositTypeContainer");
        this.settingMaximumDepositTypeBlock.add(this.settingMaximumDepositTypeContainer);
        this.settingMaximumDepositTypeProvider = new LockInTypeProvider();
        this.settingMaximumDepositTypeField = new Select2SingleChoice<>("settingMaximumDepositTypeField", 0, new PropertyModel<>(this, "settingMaximumDepositTypeValue"), this.settingMaximumDepositTypeProvider);
        this.settingMaximumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMaximumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTypeContainer.add(this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeFeedback = new TextFeedbackPanel("settingMaximumDepositTypeFeedback", this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeContainer.add(this.settingMaximumDepositTypeFeedback);

        this.settingForPreMatureClosureBlock = new WebMarkupContainer("settingForPreMatureClosureBlock");
        this.form.add(this.settingForPreMatureClosureBlock);
        this.settingForPreMatureClosureContainer = new WebMarkupContainer("settingForPreMatureClosureContainer");
        this.settingForPreMatureClosureBlock.add(this.settingForPreMatureClosureContainer);
        this.settingForPreMatureClosureField = new CheckBox("settingForPreMatureClosureField", new PropertyModel<>(this, "settingForPreMatureClosureValue"));
        this.settingForPreMatureClosureField.add(new OnChangeAjaxBehavior());
        this.settingForPreMatureClosureContainer.add(this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureFeedback = new TextFeedbackPanel("settingForPreMatureClosureFeedback", this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureContainer.add(this.settingForPreMatureClosureFeedback);

        this.settingApplyPenalInterestBlock = new WebMarkupContainer("settingApplyPenalInterestBlock");
        this.settingApplyPenalInterestBlock.setOutputMarkupId(true);
        this.form.add(this.settingApplyPenalInterestBlock);
        this.settingApplyPenalInterestContainer = new WebMarkupContainer("settingApplyPenalInterestContainer");
        this.settingApplyPenalInterestBlock.add(this.settingApplyPenalInterestContainer);
        this.settingApplyPenalInterestField = new TextField<>("settingApplyPenalInterestField", new PropertyModel<>(this, "settingApplyPenalInterestValue"));
        this.settingApplyPenalInterestField.setLabel(Model.of("Apply penal interest"));
        this.settingApplyPenalInterestContainer.add(this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestFeedback = new TextFeedbackPanel("settingApplyPenalInterestFeedback", this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestContainer.add(this.settingApplyPenalInterestFeedback);

        this.settingApplyPenalOnBlock = new WebMarkupContainer("settingApplyPenalOnBlock");
        this.form.add(this.settingApplyPenalOnBlock);
        this.settingApplyPenalOnContainer = new WebMarkupContainer("settingApplyPenalOnContainer");
        this.settingApplyPenalOnBlock.add(this.settingApplyPenalOnContainer);
        this.settingApplyPenalOnProvider = new ApplyPenalOnProvider();
        this.settingApplyPenalOnField = new Select2SingleChoice<>("settingApplyPenalOnField", 0, new PropertyModel<>(this, "settingApplyPenalOnValue"), this.settingApplyPenalOnProvider);
        this.settingApplyPenalOnField.setLabel(Model.of("On"));
        this.settingApplyPenalOnField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalOnContainer.add(this.settingApplyPenalOnField);
        this.settingApplyPenalOnFeedback = new TextFeedbackPanel("settingApplyPenalOnFeedback", this.settingApplyPenalOnField);
        this.settingApplyPenalOnContainer.add(this.settingApplyPenalOnFeedback);

        this.settingWithholdTaxApplicableBlock = new WebMarkupContainer("settingWithholdTaxApplicableBlock");
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableContainer = new WebMarkupContainer("settingWithholdTaxApplicableContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableFeedback);

        this.settingTaxGroupBlock = new WebMarkupContainer("settingTaxGroupBlock");
        this.settingTaxGroupBlock.setOutputMarkupId(true);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupContainer = new WebMarkupContainer("settingTaxGroupContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", 0, new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupContainer.add(this.settingTaxGroupFeedback);

    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingWithholdTaxApplicableValue != null && this.settingWithholdTaxApplicableValue;
        this.settingTaxGroupContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected void initTerm() {

        this.termDefaultDepositAmountBlock = new WebMarkupContainer("termDefaultDepositAmountBlock");
        this.termDefaultDepositAmountBlock.setOutputMarkupId(true);
        this.form.add(this.termDefaultDepositAmountBlock);
        this.termDefaultDepositAmountContainer = new WebMarkupContainer("termDefaultDepositAmountContainer");
        this.termDefaultDepositAmountBlock.add(this.termDefaultDepositAmountContainer);
        this.termDefaultDepositAmountField = new TextField<>("termDefaultDepositAmountField", new PropertyModel<>(this, "termDefaultDepositAmountValue"));
        this.termDefaultDepositAmountField.setLabel(Model.of("Default Deposit Amount"));
        this.termDefaultDepositAmountContainer.add(this.termDefaultDepositAmountField);
        this.termDefaultDepositAmountFeedback = new TextFeedbackPanel("termDefaultDepositAmountFeedback", this.termDefaultDepositAmountField);
        this.termDefaultDepositAmountContainer.add(this.termDefaultDepositAmountFeedback);

        this.termMinimumDepositAmountBlock = new WebMarkupContainer("termMinimumDepositAmountBlock");
        this.termMinimumDepositAmountBlock.setOutputMarkupId(true);
        this.form.add(this.termMinimumDepositAmountBlock);
        this.termMinimumDepositAmountContainer = new WebMarkupContainer("termMinimumDepositAmountContainer");
        this.termMinimumDepositAmountBlock.add(this.termMinimumDepositAmountContainer);
        this.termMinimumDepositAmountField = new TextField<>("termMinimumDepositAmountField", new PropertyModel<>(this, "termMinimumDepositAmountValue"));
        this.termMinimumDepositAmountField.setLabel(Model.of("Minimum Deposit Amount"));
        this.termMinimumDepositAmountContainer.add(this.termMinimumDepositAmountField);
        this.termMinimumDepositAmountFeedback = new TextFeedbackPanel("termMinimumDepositAmountFeedback", this.termMinimumDepositAmountField);
        this.termMinimumDepositAmountContainer.add(this.termMinimumDepositAmountFeedback);

        this.termMaximumDepositAmountBlock = new WebMarkupContainer("termMaximumDepositAmountBlock");
        this.termMaximumDepositAmountBlock.setOutputMarkupId(true);
        this.form.add(this.termMaximumDepositAmountBlock);
        this.termMaximumDepositAmountContainer = new WebMarkupContainer("termMaximumDepositAmountContainer");
        this.termMaximumDepositAmountBlock.add(this.termMaximumDepositAmountContainer);
        this.termMaximumDepositAmountField = new TextField<>("termMaximumDepositAmountField", new PropertyModel<>(this, "termMaximumDepositAmountValue"));
        this.termMaximumDepositAmountField.setLabel(Model.of("Maximum Deposit Amount"));
        this.termMaximumDepositAmountContainer.add(this.termMaximumDepositAmountField);
        this.termMaximumDepositAmountFeedback = new TextFeedbackPanel("termMaximumDepositAmountFeedback", this.termMaximumDepositAmountField);
        this.termMaximumDepositAmountContainer.add(this.termMaximumDepositAmountFeedback);

        this.termInterestCompoundingPeriodBlock = new WebMarkupContainer("termInterestCompoundingPeriodBlock");
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodContainer = new WebMarkupContainer("termInterestCompoundingPeriodContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodContainer);
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", 0, new PropertyModel<>(this, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodFeedback);

        this.termInterestPostingPeriodBlock = new WebMarkupContainer("termInterestPostingPeriodBlock");
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodContainer = new WebMarkupContainer("termInterestPostingPeriodContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodContainer);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", 0, new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest posting period"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodFeedback);

        this.termInterestCalculatedUsingBlock = new WebMarkupContainer("termInterestCalculatedUsingBlock");
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingContainer = new WebMarkupContainer("termInterestCalculatedUsingContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingContainer);
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", 0, new PropertyModel<>(this, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest calculated using"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingFeedback);

        this.termDayInYearBlock = new WebMarkupContainer("termDayInYearBlock");
        this.form.add(this.termDayInYearBlock);
        this.termDayInYearContainer = new WebMarkupContainer("termDayInYearContainer");
        this.termDayInYearBlock.add(this.termDayInYearContainer);
        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", 0, new PropertyModel<>(this, "termDayInYearValue"), this.termDayInYearProvider);
        this.termDayInYearField.setLabel(Model.of("Days in year"));
        this.termDayInYearField.add(new OnChangeAjaxBehavior());
        this.termDayInYearContainer.add(this.termDayInYearField);
        this.termDayInYearFeedback = new TextFeedbackPanel("termDayInYearFeedback", this.termDayInYearField);
        this.termDayInYearContainer.add(this.termDayInYearFeedback);

    }

    protected void initCurrency() {

        this.currencyCodeBlock = new WebMarkupContainer("currencyCodeBlock");
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeContainer = new WebMarkupContainer("currencyCodeContainer");
        this.currencyCodeBlock.add(this.currencyCodeContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupContainer("currencyDecimalPlaceBlock");
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceContainer = new WebMarkupContainer("currencyDecimalPlaceContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyMultipleOfBlock = new WebMarkupContainer("currencyMultipleOfBlock");
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfContainer = new WebMarkupContainer("currencyMultipleOfContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initDetail() {
        this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
        this.detailProductNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
        this.detailProductNameBlock.add(this.detailProductNameContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameContainer.add(this.detailProductNameFeedback);

        this.detailShortNameBlock = new WebMarkupContainer("detailShortNameBlock");
        this.detailShortNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameContainer = new WebMarkupContainer("detailShortNameContainer");
        this.detailShortNameBlock.add(this.detailShortNameContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameContainer.add(this.detailShortNameFeedback);

        this.detailDescriptionBlock = new WebMarkupContainer("detailDescriptionBlock");
        this.detailDescriptionBlock.setOutputMarkupId(true);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionContainer = new WebMarkupContainer("detailDescriptionContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionContainer.add(this.detailDescriptionFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        FixedBuilder builder = new FixedBuilder();

        // Detail
        builder.withName(this.detailProductNameValue);
        builder.withShortName(this.detailShortNameValue);
        builder.withDescription(this.detailDescriptionValue);

        // Currency
        if (this.currencyCodeValue != null) {
            builder.withCurrencyCode(this.currencyCodeValue.getId());
        }
        builder.withDigitsAfterDecimal(this.currencyDecimalPlaceValue);
        builder.withInMultiplesOf(this.currencyMultipleOfValue);

        // Term

        builder.withDepositAmount(this.termDefaultDepositAmountValue);
        builder.withMinDepositAmount(this.termMinimumDepositAmountValue);
        builder.withMaxDepositAmount(this.termMaximumDepositAmountValue);

        if (this.termInterestCompoundingPeriodValue != null) {
            builder.withInterestCompoundingPeriodType(InterestCompoundingPeriod.valueOf(this.termInterestCompoundingPeriodValue.getId()));
        }

        if (this.termInterestPostingPeriodValue != null) {
            builder.withInterestPostingPeriodType(InterestPostingPeriod.valueOf(this.termInterestPostingPeriodValue.getId()));
        }

        if (this.termInterestCalculatedUsingValue != null) {
            builder.withInterestCalculationType(InterestCalculatedUsing.valueOf(this.termInterestCalculatedUsingValue.getId()));
        }

        if (this.termDayInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.termDayInYearValue.getId()));
        }

        // Setting

        builder.withLockInPeriodFrequency(this.settingLockInPeriodValue);
        if (this.settingLockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.settingLockInTypeValue.getId()));
        }

        builder.withMinDepositTerm(this.settingMinimumDepositTermValue);

        if (this.settingMinimumDepositTypeValue != null) {
            builder.withMinDepositTermTypeId(LockInType.valueOf(this.settingMinimumDepositTypeValue.getId()));
        }

        builder.withInMultiplesOfDepositTerm(this.settingInMultiplesOfValue);

        if (this.settingInMultiplesTypeValue != null) {
            builder.withInMultiplesOfDepositTermTypeId(LockInType.valueOf(this.settingInMultiplesTypeValue.getId()));
        }

        builder.withMaxDepositTerm(this.settingMaximumDepositTermValue);

        if (this.settingMaximumDepositTypeValue != null) {
            builder.withMaxDepositTermTypeId(LockInType.valueOf(this.settingMaximumDepositTypeValue.getId()));
        }

        builder.withPreClosurePenalApplicable(this.settingForPreMatureClosureValue == null ? false : this.settingForPreMatureClosureValue);

        builder.withPreClosurePenalInterest(this.settingApplyPenalInterestValue);

        if (this.settingApplyPenalOnValue != null) {
            builder.withPreClosurePenalInterestOnTypeId(ApplyPenalOn.valueOf(this.settingApplyPenalOnValue.getId()));
        }

        boolean holdTax = this.settingWithholdTaxApplicableValue == null ? false : this.settingWithholdTaxApplicableValue;
        builder.withHoldTax(holdTax);
        if (holdTax) {
            if (this.settingTaxGroupValue != null) {
                builder.withTaxGroupId(this.settingTaxGroupValue.getId());
            }
        }

        // Interest Rate Chart

        if ((this.interestRatePrimaryGroupingByAmountValue != null && this.interestRatePrimaryGroupingByAmountValue) || this.interestRateValidFromDateValue != null || this.interestRateValidEndDateValue != null || !this.interestRateChartValue.isEmpty()) {
            builder.withFromDate(this.interestRateValidFromDateValue);
            builder.withEndDate(this.interestRateValidEndDateValue);

            builder.withPrimaryGroupingByAmount(this.interestRatePrimaryGroupingByAmountValue == null ? false : this.interestRatePrimaryGroupingByAmountValue);

            for (Map<String, Object> interestRateChart : this.interestRateChartValue) {
                Option periodTypeOption = (Option) interestRateChart.get("periodType");
                LockInType periodType = periodTypeOption == null ? null : LockInType.valueOf(periodTypeOption.getId());
                Integer fromPeriod = (Integer) interestRateChart.get("periodFrom");
                Integer toPeriod = (Integer) interestRateChart.get("periodTo");
                Integer amountRangeFrom = (Integer) interestRateChart.get("amountRangeFrom");
                Integer amountRangeTo = (Integer) interestRateChart.get("amountRangeTo");
                Double annualInterestRate = (Double) interestRateChart.get("interest");
                String description = (String) interestRateChart.get("description");
                List<Map<String, Object>> interestRate = (List<Map<String, Object>>) interestRateChart.get("interestRate");
                List<JSONObject> incentives = null;
                if (interestRate != null && !interestRate.isEmpty()) {
                    incentives = Lists.newLinkedList();
                    for (Map<String, Object> rate : interestRate) {

                        IncentiveBuilder incentiveBuilder = new IncentiveBuilder();

                        Option attributeOption = (Option) rate.get("attribute");
                        Attribute attribute = attributeOption == null ? null : Attribute.valueOf(attributeOption.getId());
                        incentiveBuilder.withAttributeName(attribute);

                        Option operatorOption = (Option) rate.get("operator");
                        Operator operator = operatorOption == null ? null : Operator.valueOf(operatorOption.getId());
                        incentiveBuilder.withConditionType(operator);

                        String operand = (String) rate.get("operand");
                        incentiveBuilder.withAttributeValue(operand);

                        Option operandTypeOption = (Option) rate.get("operandType");
                        OperandType operandType = operandTypeOption == null ? null : OperandType.valueOf(operandTypeOption.getId());
                        incentiveBuilder.withIncentiveType(operandType);

                        Double rateInterest = (Double) rate.get("interest");
                        incentiveBuilder.withAmount(rateInterest);

                        incentives.add(incentiveBuilder.build().getObject());
                    }
                }
                builder.withChartSlab(periodType, fromPeriod, toPeriod, amountRangeFrom, amountRangeTo, annualInterestRate, description, incentives);
            }
        }

        // Charge

        if (this.chargeValue != null && !this.chargeValue.isEmpty()) {
            for (Map<String, Object> item : this.chargeValue) {
                builder.withCharges((String) item.get("chargeId"));
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (ACC_NONE.equals(accounting)) {
            builder.withAccountingRule(1);
        } else if (ACC_CASH.equals(accounting)) {
            builder.withAccountingRule(2);
        }

        if (ACC_CASH.equals(accounting)) {
            if (this.cashSavingReferenceValue != null) {
                builder.withSavingsReferenceAccountId(this.cashSavingReferenceValue.getId());
            }
            if (this.cashSavingControlValue != null) {
                builder.withSavingsControlAccountId(this.cashSavingControlValue.getId());
            }
            if (this.cashSavingsTransfersInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashSavingsTransfersInSuspenseValue.getId());
            }
            if (this.cashInterestOnSavingsValue != null) {
                builder.withInterestOnSavingsAccountId(this.cashInterestOnSavingsValue.getId());
            }
            if (this.cashIncomeFromFeesValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeesValue.getId());
            }
            if (this.cashIncomeFromPenaltiesValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltiesValue.getId());
            }
        }

        if (ACC_CASH.equals(accounting)) {
            if (this.advancedAccountingRuleFundSourceValue != null && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
                    builder.withPaymentChannelToFundSourceMappings((String) item.get("paymentId"), (String) item.get("accountId"));
                }
            }
            if (this.advancedAccountingRuleFeeIncomeValue != null && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
                    builder.withFeeToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
                }
            }
            if (this.advancedAccountingRulePenaltyIncomeValue != null && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
                    builder.withPenaltyToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
                }
            }
        }

        JsonNode node = null;
        JsonNode request = builder.build();
        try {
            node = FixedHelper.create((Session) getSession(), request);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(FixedDepositBrowsePage.class);
    }

}
