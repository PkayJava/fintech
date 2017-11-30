package com.angkorteam.fintech.pages.product.fixed;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.WicketRuntimeException;
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
import com.angkorteam.fintech.dto.builder.ProductFixedDepositBuilder;
import com.angkorteam.fintech.dto.builder.ProductFixedDepositBuilder.IncentiveBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.AccountingType;
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
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    protected static final Logger LOGGER = LoggerFactory.getLogger(FixedDepositCreatePage.class);

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameIContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameIContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionIContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeIContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupContainer currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceIContainer;
    protected Long currencyDecimalPlaceValue;
    protected TextField<Long> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfIContainer;
    protected Long currencyMultipleOfValue;
    protected TextField<Long> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    protected WebMarkupBlock termDefaultDepositAmountBlock;
    protected WebMarkupContainer termDefaultDepositAmountIContainer;
    protected Double termDefaultDepositAmountValue;
    protected TextField<Double> termDefaultDepositAmountField;
    protected TextFeedbackPanel termDefaultDepositAmountFeedback;

    protected WebMarkupBlock termMinimumDepositAmountBlock;
    protected WebMarkupContainer termMinimumDepositAmountIContainer;
    protected Double termMinimumDepositAmountValue;
    protected TextField<Double> termMinimumDepositAmountField;
    protected TextFeedbackPanel termMinimumDepositAmountFeedback;

    protected WebMarkupBlock termMaximumDepositAmountBlock;
    protected WebMarkupContainer termMaximumDepositAmountIContainer;
    protected Double termMaximumDepositAmountValue;
    protected TextField<Double> termMaximumDepositAmountField;
    protected TextFeedbackPanel termMaximumDepositAmountFeedback;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodIContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Option termInterestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    protected TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodIContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Option termInterestPostingPeriodValue;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
    protected TextFeedbackPanel termInterestPostingPeriodFeedback;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingIContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Option termInterestCalculatedUsingValue;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
    protected TextFeedbackPanel termInterestCalculatedUsingFeedback;

    protected WebMarkupBlock termDayInYearBlock;
    protected WebMarkupContainer termDayInYearIContainer;
    protected DayInYearProvider termDayInYearProvider;
    protected Option termDayInYearValue;
    protected Select2SingleChoice<Option> termDayInYearField;
    protected TextFeedbackPanel termDayInYearFeedback;

    // Setting

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected Long settingLockInPeriodValue;
    protected TextField<Long> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingMinimumDepositTermBlock;
    protected WebMarkupContainer settingMinimumDepositTermIContainer;
    protected Long settingMinimumDepositTermValue;
    protected TextField<Long> settingMinimumDepositTermField;
    protected TextFeedbackPanel settingMinimumDepositTermFeedback;

    protected WebMarkupBlock settingMinimumDepositTypeBlock;
    protected WebMarkupContainer settingMinimumDepositTypeIContainer;
    protected LockInTypeProvider settingMinimumDepositTypeProvider;
    protected Option settingMinimumDepositTypeValue;
    protected Select2SingleChoice<Option> settingMinimumDepositTypeField;
    protected TextFeedbackPanel settingMinimumDepositTypeFeedback;

    protected WebMarkupBlock settingInMultiplesOfBlock;
    protected WebMarkupContainer settingInMultiplesOfIContainer;
    protected Long settingInMultiplesOfValue;
    protected TextField<Long> settingInMultiplesOfField;
    protected TextFeedbackPanel settingInMultiplesOfFeedback;

    protected WebMarkupBlock settingInMultiplesTypeBlock;
    protected WebMarkupContainer settingInMultiplesTypeIContainer;
    protected LockInTypeProvider settingInMultiplesTypeProvider;
    protected Option settingInMultiplesTypeValue;
    protected Select2SingleChoice<Option> settingInMultiplesTypeField;
    protected TextFeedbackPanel settingInMultiplesTypeFeedback;

    protected WebMarkupBlock settingMaximumDepositTermBlock;
    protected WebMarkupContainer settingMaximumDepositTermIContainer;
    protected Long settingMaximumDepositTermValue;
    protected TextField<Long> settingMaximumDepositTermField;
    protected TextFeedbackPanel settingMaximumDepositTermFeedback;

    protected WebMarkupBlock settingMaximumDepositTypeBlock;
    protected WebMarkupContainer settingMaximumDepositTypeIContainer;
    protected LockInTypeProvider settingMaximumDepositTypeProvider;
    protected Option settingMaximumDepositTypeValue;
    protected Select2SingleChoice<Option> settingMaximumDepositTypeField;
    protected TextFeedbackPanel settingMaximumDepositTypeFeedback;

    protected WebMarkupBlock settingForPreMatureClosureBlock;
    protected WebMarkupContainer settingForPreMatureClosureIContainer;
    protected Boolean settingForPreMatureClosureValue;
    protected CheckBox settingForPreMatureClosureField;
    protected TextFeedbackPanel settingForPreMatureClosureFeedback;

    protected WebMarkupBlock settingApplyPenalInterestBlock;
    protected WebMarkupContainer settingApplyPenalInterestIContainer;
    protected Double settingApplyPenalInterestValue;
    protected TextField<Double> settingApplyPenalInterestField;
    protected TextFeedbackPanel settingApplyPenalInterestFeedback;

    protected WebMarkupBlock settingApplyPenalOnBlock;
    protected WebMarkupContainer settingApplyPenalOnIContainer;
    protected ApplyPenalOnProvider settingApplyPenalOnProvider;
    protected Option settingApplyPenalOnValue;
    protected Select2SingleChoice<Option> settingApplyPenalOnField;
    protected TextFeedbackPanel settingApplyPenalOnFeedback;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableIContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupIContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Option settingTaxGroupValue;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    // Interest Rate Chart

    protected WebMarkupBlock interestRateValidFromDateBlock;
    protected WebMarkupContainer interestRateValidFromDateIContainer;
    protected Date interestRateValidFromDateValue;
    protected DateTextField interestRateValidFromDateField;
    protected TextFeedbackPanel interestRateValidFromDateFeedback;

    protected WebMarkupBlock interestRateValidEndDateBlock;
    protected WebMarkupContainer interestRateValidEndDateIContainer;
    protected Date interestRateValidEndDateValue;
    protected DateTextField interestRateValidEndDateField;
    protected TextFeedbackPanel interestRateValidEndDateFeedback;

    protected WebMarkupBlock interestRatePrimaryGroupingByAmountBlock;
    protected WebMarkupContainer interestRatePrimaryGroupingByAmountIContainer;
    protected Boolean interestRatePrimaryGroupingByAmountValue;
    protected CheckBox interestRatePrimaryGroupingByAmountField;
    protected TextFeedbackPanel interestRatePrimaryGroupingByAmountFeedback;

    protected WebMarkupBlock interestRateChartBlock;
    protected WebMarkupContainer interestRateChartIContainer;
    protected List<Map<String, Object>> interestRateChartValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> interestRateChartTable;
    protected ListDataProvider interestRateChartProvider;
    protected List<IColumn<Map<String, Object>, String>> interestRateChartColumn;
    protected ModalWindow interestRateChartPopup;
    protected AjaxLink<Void> interestRateChartAddLink;

    protected ModalWindow incentivePopup;

    // Charges

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashSavingReferenceBlock;
    protected WebMarkupContainer cashSavingReferenceIContainer;
    protected SingleChoiceProvider cashSavingReferenceProvider;
    protected Option cashSavingReferenceValue;
    protected Select2SingleChoice<Option> cashSavingReferenceField;
    protected TextFeedbackPanel cashSavingReferenceFeedback;

    protected WebMarkupBlock cashSavingControlBlock;
    protected WebMarkupContainer cashSavingControlIContainer;
    protected SingleChoiceProvider cashSavingControlProvider;
    protected Option cashSavingControlValue;
    protected Select2SingleChoice<Option> cashSavingControlField;
    protected TextFeedbackPanel cashSavingControlFeedback;

    protected WebMarkupBlock cashSavingTransferInSuspenseBlock;
    protected WebMarkupContainer cashSavingTransferInSuspenseIContainer;
    protected SingleChoiceProvider cashSavingTransferInSuspenseProvider;
    protected Option cashSavingTransferInSuspenseValue;
    protected Select2SingleChoice<Option> cashSavingTransferInSuspenseField;
    protected TextFeedbackPanel cashSavingTransferInSuspenseFeedback;

    protected WebMarkupBlock cashInterestOnSavingBlock;
    protected WebMarkupContainer cashInterestOnSavingIContainer;
    protected SingleChoiceProvider cashInterestOnSavingProvider;
    protected Option cashInterestOnSavingValue;
    protected Select2SingleChoice<Option> cashInterestOnSavingField;
    protected TextFeedbackPanel cashInterestOnSavingFeedback;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Option cashIncomeFromFeeValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
    protected Option cashIncomeFromPenaltyValue;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;
    protected TextFeedbackPanel cashIncomeFromPenaltyFeedback;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;

    protected Map<String, Object> popupModel;

    protected ModalWindow fundSourcePopup;
    protected ModalWindow feeIncomePopup;
    protected ModalWindow penaltyIncomePopup;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newLinkedList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedDepositBrowsePage.class);
        this.form.add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerm();

        initSectionSetting();

        initSectionInterestRateChart();

        initSectionCharge();

        initSectionAccounting();
    }

    @Override
    protected void configureRequiredValidation() {
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

    @Override
    protected void configureMetaData() {
        settingWithholdTaxApplicableFieldUpdate(null);
        accountingFieldUpdate(null);
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2l;
        this.currencyMultipleOfValue = 1l;

        this.termDefaultDepositAmountValue = 100d;
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Daily.toOption();
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.DailyBalance.toOption();
        this.termInterestPostingPeriodValue = InterestPostingPeriod.Monthly.toOption();
        this.termDayInYearValue = DayInYear.D365.toOption();
        this.settingMinimumDepositTermValue = 1l;
        this.settingMinimumDepositTypeValue = LockInType.Month.toOption();
        this.accountingValue = AccountingType.None.getDescription();
    }

    protected void initSectionAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
        this.form.add(this.accountingField);

        initAccountingCash();

        initAdvancedAccountingRule();
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        this.cashSavingReferenceBlock = new WebMarkupBlock("cashSavingReferenceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingReferenceBlock);
        this.cashSavingReferenceIContainer = new WebMarkupContainer("cashSavingReferenceIContainer");
        this.cashSavingReferenceBlock.add(this.cashSavingReferenceIContainer);
        this.cashSavingReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
        this.cashSavingReferenceField.setLabel(Model.of("Saving reference"));
        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceField);
        this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback", this.cashSavingReferenceField);
        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceFeedback);

        this.cashSavingControlBlock = new WebMarkupBlock("cashSavingControlBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingControlBlock);
        this.cashSavingControlIContainer = new WebMarkupContainer("cashSavingControlIContainer");
        this.cashSavingControlBlock.add(this.cashSavingControlIContainer);
        this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
        this.cashSavingControlField.setLabel(Model.of("Saving control"));
        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
        this.cashSavingControlIContainer.add(this.cashSavingControlField);
        this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback", this.cashSavingControlField);
        this.cashSavingControlIContainer.add(this.cashSavingControlFeedback);

        this.cashSavingTransferInSuspenseBlock = new WebMarkupBlock("cashSavingTransferInSuspenseBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingTransferInSuspenseBlock);
        this.cashSavingTransferInSuspenseIContainer = new WebMarkupContainer("cashSavingTransferInSuspenseIContainer");
        this.cashSavingTransferInSuspenseBlock.add(this.cashSavingTransferInSuspenseIContainer);
        this.cashSavingTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingTransferInSuspenseField = new Select2SingleChoice<>("cashSavingTransferInSuspenseField", new PropertyModel<>(this, "cashSavingTransferInSuspenseValue"), this.cashSavingTransferInSuspenseProvider);
        this.cashSavingTransferInSuspenseField.setLabel(Model.of("Savings transfers in suspense"));
        this.cashSavingTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashSavingTransferInSuspenseIContainer.add(this.cashSavingTransferInSuspenseField);
        this.cashSavingTransferInSuspenseFeedback = new TextFeedbackPanel("cashSavingTransferInSuspenseFeedback", this.cashSavingTransferInSuspenseField);
        this.cashSavingTransferInSuspenseIContainer.add(this.cashSavingTransferInSuspenseFeedback);

        this.cashInterestOnSavingBlock = new WebMarkupBlock("cashInterestOnSavingBlock", Size.Six_6);
        this.cashIContainer.add(this.cashInterestOnSavingBlock);
        this.cashInterestOnSavingIContainer = new WebMarkupContainer("cashInterestOnSavingIContainer");
        this.cashInterestOnSavingBlock.add(this.cashInterestOnSavingIContainer);
        this.cashInterestOnSavingProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashInterestOnSavingProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashInterestOnSavingProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField", new PropertyModel<>(this, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
        this.cashInterestOnSavingField.setLabel(Model.of("Interest on savings"));
        this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingField);
        this.cashInterestOnSavingFeedback = new TextFeedbackPanel("cashInterestOnSavingFeedback", this.cashInterestOnSavingField);
        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingFeedback);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeFeedback);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyIContainer = new WebMarkupContainer("cashIncomeFromPenaltyIContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyIContainer);
        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyField.setLabel(Model.of("Income from penalties"));
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyFeedback = new TextFeedbackPanel("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyFeedback);
    }

    protected void initAdvancedAccountingRule() {
        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleIContainer = new WebMarkupContainer("advancedAccountingRuleIContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleIContainer);

        // Table
        {
            this.fundSourcePopup = new ModalWindow("fundSourcePopup");
            add(this.fundSourcePopup);
            this.fundSourcePopup.setOnClose(this::fundSourcePopupClose);

            this.advancedAccountingRuleFundSourceColumn = Lists.newLinkedList();
            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
            this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
            this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
            this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceTable);
            this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
            this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

            this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
            this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceAddLink);
        }

        // Table
        {
            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
            add(this.feeIncomePopup);
            this.feeIncomePopup.setOnClose(this::feeIncomePopupClose);

            this.advancedAccountingRuleFeeIncomeColumn = Lists.newLinkedList();
            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
            this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
            this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
            this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeTable);
            this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
            this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

            this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
            this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
        }

        // Table
        {
            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
            add(this.penaltyIncomePopup);
            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupClose);

            this.advancedAccountingRulePenaltyIncomeColumn = Lists.newLinkedList();
            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
            this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
            this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
            this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
            this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
            this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

            this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
            this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);
        }
    }

    protected void feeIncomePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRuleFeeIncomeValue.add(item);
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void penaltyIncomePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRulePenaltyIncomeValue.add(item);
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected void fundSourcePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("payment", this.popupModel.get("paymentValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRuleFundSourceValue.add(item);
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup("penaltyCharge", this.penaltyIncomePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.penaltyIncomePopup.show(target);
        } else {
            this.penaltyIncomePopup.setContent(new CurrencyPopup("currency", this.penaltyIncomePopup));
            this.penaltyIncomePopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void advancedAccountingRulePenaltyIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.feeIncomePopup.setContent(new FeeChargePopup("feeCharge", this.feeIncomePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.feeIncomePopup.show(target);
        } else {
            this.feeIncomePopup.setContent(new CurrencyPopup("currency", this.feeIncomePopup));
            this.feeIncomePopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void advancedAccountingRuleFeeIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected void advancedAccountingRuleFundSourceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> advancedAccountingRuleFundSourceAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.fundSourcePopup.setContent(new PaymentTypePopup("paymentType", this.fundSourcePopup, this.popupModel));
        this.fundSourcePopup.show(target);
        return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);
        this.advancedAccountingRuleIContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleIContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleIContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashIContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initSectionCharge() {
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupClose);

        this.chargeColumn = Lists.newLinkedList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);
    }

    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        Option charge = (Option) this.popupModel.get("chargeValue");
        for (Map<String, Object> temp : this.chargeValue) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, charge_calculation_enum, charge_time_enum, amount from m_charge where id = ?", charge.getId());
        Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
        Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
        item.put("uuid", charge.getId());
        item.put("charge", charge);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup("charge", this.chargePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.chargePopup.setContent(new CurrencyPopup("currency", this.chargePopup));
            this.chargePopup.show(target);
        }
        return false;
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "date".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("type".equals(column) || "collect".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void chargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initSectionInterestRateChart() {
        initInterestRateValidFromDateBlock();

        initInterestRateValidEndDateBlock();

        initInterestRatePrimaryGroupingByAmountBlock();

        // Table
        this.interestRateChartPopup = new ModalWindow("interestRateChartPopup");
        this.interestRateChartPopup.setHeightUnit("px");
        this.interestRateChartPopup.setWidthUnit("px");
        this.interestRateChartPopup.setInitialHeight(600);
        this.interestRateChartPopup.setInitialWidth(1000);
        add(this.interestRateChartPopup);
        this.interestRateChartPopup.setOnClose(this::interestRateChartPopupClose);

        this.incentivePopup = new ModalWindow("incentivePopup");
        this.incentivePopup.setHeightUnit("px");
        this.incentivePopup.setWidthUnit("px");
        this.incentivePopup.setInitialHeight(600);
        this.incentivePopup.setInitialWidth(1100);
        add(this.incentivePopup);
        this.incentivePopup.setOnClose(this::incentivePopupClose);

        initInterestRateChartBlock();

        this.interestRateChartAddLink = new AjaxLink<>("interestRateChartAddLink");
        this.interestRateChartAddLink.setOnClick(this::interestRateChartAddLinkClick);
        this.form.add(this.interestRateChartAddLink);
    }

    protected void initInterestRateChartBlock() {
        this.interestRateChartColumn = Lists.newLinkedList();
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period From"), "periodFrom", "periodFrom", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period To"), "periodTo", "periodTo", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount Range From"), "amountRangeFrom", "amountRangeFrom", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount Range To"), "amountRangeTo", "amountRangeTo", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartAction, this::interestRateChartClick));
        this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue);
        this.interestRateChartBlock = new WebMarkupBlock("interestRateChartBlock", Size.Twelve_12);
        this.form.add(this.interestRateChartBlock);
        this.interestRateChartIContainer = new WebMarkupContainer("interestRateChartIContainer");
        this.interestRateChartBlock.add(this.interestRateChartIContainer);
        this.interestRateChartTable = new DataTable<>("interestRateChartTable", interestRateChartColumn, this.interestRateChartProvider, 20);
        this.interestRateChartIContainer.add(this.interestRateChartTable);
        this.interestRateChartTable.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
        this.interestRateChartTable.addBottomToolbar(new NoRecordsToolbar(this.interestRateChartTable));
    }

    protected void initInterestRatePrimaryGroupingByAmountBlock() {
        this.interestRatePrimaryGroupingByAmountBlock = new WebMarkupBlock("interestRatePrimaryGroupingByAmountBlock", Size.Six_6);
        this.form.add(this.interestRatePrimaryGroupingByAmountBlock);
        this.interestRatePrimaryGroupingByAmountIContainer = new WebMarkupContainer("interestRatePrimaryGroupingByAmountIContainer");
        this.interestRatePrimaryGroupingByAmountBlock.add(this.interestRatePrimaryGroupingByAmountIContainer);
        this.interestRatePrimaryGroupingByAmountField = new CheckBox("interestRatePrimaryGroupingByAmountField", new PropertyModel<>(this, "interestRatePrimaryGroupingByAmountValue"));
        this.interestRatePrimaryGroupingByAmountField.add(new OnChangeAjaxBehavior());
        this.interestRatePrimaryGroupingByAmountIContainer.add(this.interestRatePrimaryGroupingByAmountField);
        this.interestRatePrimaryGroupingByAmountFeedback = new TextFeedbackPanel("interestRatePrimaryGroupingByAmountFeedback", this.interestRatePrimaryGroupingByAmountField);
        this.interestRatePrimaryGroupingByAmountIContainer.add(this.interestRatePrimaryGroupingByAmountFeedback);
    }

    protected void initInterestRateValidEndDateBlock() {
        this.interestRateValidEndDateBlock = new WebMarkupBlock("interestRateValidEndDateBlock", Size.Six_6);
        this.form.add(this.interestRateValidEndDateBlock);
        this.interestRateValidEndDateIContainer = new WebMarkupContainer("interestRateValidEndDateIContainer");
        this.interestRateValidEndDateBlock.add(this.interestRateValidEndDateIContainer);
        this.interestRateValidEndDateField = new DateTextField("interestRateValidEndDateField", new PropertyModel<>(this, "interestRateValidEndDateValue"));
        this.interestRateValidEndDateField.setLabel(Model.of("End Date"));
        this.interestRateValidEndDateIContainer.add(this.interestRateValidEndDateField);
        this.interestRateValidEndDateFeedback = new TextFeedbackPanel("interestRateValidEndDateFeedback", this.interestRateValidEndDateField);
        this.interestRateValidEndDateIContainer.add(this.interestRateValidEndDateFeedback);
    }

    protected void initInterestRateValidFromDateBlock() {
        this.interestRateValidFromDateBlock = new WebMarkupBlock("interestRateValidFromDateBlock", Size.Six_6);
        this.form.add(this.interestRateValidFromDateBlock);
        this.interestRateValidFromDateIContainer = new WebMarkupContainer("interestRateValidFromDateIContainer");
        this.interestRateValidFromDateBlock.add(this.interestRateValidFromDateIContainer);
        this.interestRateValidFromDateField = new DateTextField("interestRateValidFromDateField", new PropertyModel<>(this, "interestRateValidFromDateValue"));
        this.interestRateValidFromDateField.setLabel(Model.of("Valid From Date"));
        this.interestRateValidFromDateIContainer.add(this.interestRateValidFromDateField);
        this.interestRateValidFromDateFeedback = new TextFeedbackPanel("interestRateValidFromDateFeedback", this.interestRateValidFromDateField);
        this.interestRateValidFromDateIContainer.add(this.interestRateValidFromDateFeedback);
    }

    protected void incentivePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
    }

    protected void interestRateChartPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        item.put("periodType", this.popupModel.get("periodTypeValue"));
        item.put("periodFrom", this.popupModel.get("periodFromValue"));
        item.put("periodTo", this.popupModel.get("periodToValue"));
        item.put("amountRangeFrom", this.popupModel.get("amountRangeFromValue"));
        item.put("amountRangeTo", this.popupModel.get("amountRangeToValue"));
        item.put("interest", this.popupModel.get("interestValue"));
        item.put("description", this.popupModel.get("descriptionValue"));
        item.put("interestRate", Lists.newLinkedList());
        this.interestRateChartValue.add(item);
        target.add(this.interestRateChartTable);
    }

    protected boolean interestRateChartAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.interestRateChartPopup.setContent(new InterestRateChartPopup("interestRateChart", this.interestRateChartPopup, this.popupModel));
        this.interestRateChartPopup.show(target);
        return false;
    }

    protected ItemPanel interestRateChartColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("periodType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("periodFrom".equals(column) || "periodTo".equals(column) || "amountRangeFrom".equals(column) || "amountRangeTo".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void interestRateChartClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
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
            this.incentivePopup.setContent(new IncentivePopup("incentive", this.incentivePopup, incentiveValue));
            this.incentivePopup.show(target);
        }
    }

    protected List<ActionItem> interestRateChartAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newLinkedList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        actions.add(new ActionItem("incentives", Model.of("Incentives"), ItemCss.PRIMARY));
        return actions;
    }

    protected void initSectionSetting() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Three_3);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Three_3);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);

        this.settingMinimumDepositTermBlock = new WebMarkupBlock("settingMinimumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMinimumDepositTermBlock);
        this.settingMinimumDepositTermIContainer = new WebMarkupContainer("settingMinimumDepositTermIContainer");
        this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermIContainer);
        this.settingMinimumDepositTermField = new TextField<>("settingMinimumDepositTermField", new PropertyModel<>(this, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermField.setLabel(Model.of("Minimum Deposit Term"));

        this.settingMinimumDepositTermIContainer.add(this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermFeedback = new TextFeedbackPanel("settingMinimumDepositTermFeedback", this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermIContainer.add(this.settingMinimumDepositTermFeedback);

        this.settingMinimumDepositTypeBlock = new WebMarkupBlock("settingMinimumDepositTypeBlock", Size.Three_3);
        this.form.add(this.settingMinimumDepositTypeBlock);
        this.settingMinimumDepositTypeIContainer = new WebMarkupContainer("settingMinimumDepositTypeIContainer");
        this.settingMinimumDepositTypeBlock.add(this.settingMinimumDepositTypeIContainer);
        this.settingMinimumDepositTypeProvider = new LockInTypeProvider();
        this.settingMinimumDepositTypeField = new Select2SingleChoice<>("settingMinimumDepositTypeField", new PropertyModel<>(this, "settingMinimumDepositTypeValue"), this.settingMinimumDepositTypeProvider);
        this.settingMinimumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMinimumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTypeIContainer.add(this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeFeedback = new TextFeedbackPanel("settingMinimumDepositTypeFeedback", this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeIContainer.add(this.settingMinimumDepositTypeFeedback);

        this.settingInMultiplesOfBlock = new WebMarkupBlock("settingInMultiplesOfBlock", Size.Three_3);
        this.form.add(this.settingInMultiplesOfBlock);
        this.settingInMultiplesOfIContainer = new WebMarkupContainer("settingInMultiplesOfIContainer");
        this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfIContainer);
        this.settingInMultiplesOfField = new TextField<>("settingInMultiplesOfField", new PropertyModel<>(this, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfField.setLabel(Model.of("And thereafter, In Multiples of"));
        this.settingInMultiplesOfIContainer.add(this.settingInMultiplesOfField);
        this.settingInMultiplesOfFeedback = new TextFeedbackPanel("settingInMultiplesOfFeedback", this.settingInMultiplesOfField);
        this.settingInMultiplesOfIContainer.add(this.settingInMultiplesOfFeedback);

        this.settingInMultiplesTypeBlock = new WebMarkupBlock("settingInMultiplesTypeBlock", Size.Three_3);
        this.form.add(this.settingInMultiplesTypeBlock);
        this.settingInMultiplesTypeIContainer = new WebMarkupContainer("settingInMultiplesTypeIContainer");
        this.settingInMultiplesTypeBlock.add(this.settingInMultiplesTypeIContainer);
        this.settingInMultiplesTypeProvider = new LockInTypeProvider();
        this.settingInMultiplesTypeField = new Select2SingleChoice<>("settingInMultiplesTypeField", new PropertyModel<>(this, "settingInMultiplesTypeValue"), this.settingInMultiplesTypeProvider);
        this.settingInMultiplesTypeField.setLabel(Model.of("Type"));
        this.settingInMultiplesTypeField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesTypeIContainer.add(this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeFeedback = new TextFeedbackPanel("settingInMultiplesTypeFeedback", this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeIContainer.add(this.settingInMultiplesTypeFeedback);

        this.settingMaximumDepositTermBlock = new WebMarkupBlock("settingMaximumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMaximumDepositTermBlock);
        this.settingMaximumDepositTermIContainer = new WebMarkupContainer("settingMaximumDepositTermIContainer");
        this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermIContainer);
        this.settingMaximumDepositTermField = new TextField<>("settingMaximumDepositTermField", new PropertyModel<>(this, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermField.setLabel(Model.of("Maximum Deposit Term"));
        this.settingMaximumDepositTermIContainer.add(this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermFeedback = new TextFeedbackPanel("settingMaximumDepositTermFeedback", this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermIContainer.add(this.settingMaximumDepositTermFeedback);

        this.settingMaximumDepositTypeBlock = new WebMarkupBlock("settingMaximumDepositTypeBlock", Size.Three_3);
        this.form.add(this.settingMaximumDepositTypeBlock);
        this.settingMaximumDepositTypeIContainer = new WebMarkupContainer("settingMaximumDepositTypeIContainer");
        this.settingMaximumDepositTypeBlock.add(this.settingMaximumDepositTypeIContainer);
        this.settingMaximumDepositTypeProvider = new LockInTypeProvider();
        this.settingMaximumDepositTypeField = new Select2SingleChoice<>("settingMaximumDepositTypeField", new PropertyModel<>(this, "settingMaximumDepositTypeValue"), this.settingMaximumDepositTypeProvider);
        this.settingMaximumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMaximumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTypeIContainer.add(this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeFeedback = new TextFeedbackPanel("settingMaximumDepositTypeFeedback", this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeIContainer.add(this.settingMaximumDepositTypeFeedback);

        this.settingForPreMatureClosureBlock = new WebMarkupBlock("settingForPreMatureClosureBlock", Size.Three_3);
        this.form.add(this.settingForPreMatureClosureBlock);
        this.settingForPreMatureClosureIContainer = new WebMarkupContainer("settingForPreMatureClosureIContainer");
        this.settingForPreMatureClosureBlock.add(this.settingForPreMatureClosureIContainer);
        this.settingForPreMatureClosureField = new CheckBox("settingForPreMatureClosureField", new PropertyModel<>(this, "settingForPreMatureClosureValue"));
        this.settingForPreMatureClosureField.add(new OnChangeAjaxBehavior());
        this.settingForPreMatureClosureIContainer.add(this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureFeedback = new TextFeedbackPanel("settingForPreMatureClosureFeedback", this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureIContainer.add(this.settingForPreMatureClosureFeedback);

        this.settingApplyPenalInterestBlock = new WebMarkupBlock("settingApplyPenalInterestBlock", Size.Three_3);
        this.form.add(this.settingApplyPenalInterestBlock);
        this.settingApplyPenalInterestIContainer = new WebMarkupContainer("settingApplyPenalInterestIContainer");
        this.settingApplyPenalInterestBlock.add(this.settingApplyPenalInterestIContainer);
        this.settingApplyPenalInterestField = new TextField<>("settingApplyPenalInterestField", new PropertyModel<>(this, "settingApplyPenalInterestValue"));
        this.settingApplyPenalInterestField.setLabel(Model.of("Apply penal interest"));
        this.settingApplyPenalInterestIContainer.add(this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestFeedback = new TextFeedbackPanel("settingApplyPenalInterestFeedback", this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestIContainer.add(this.settingApplyPenalInterestFeedback);

        this.settingApplyPenalOnBlock = new WebMarkupBlock("settingApplyPenalOnBlock", Size.Three_3);
        this.form.add(this.settingApplyPenalOnBlock);
        this.settingApplyPenalOnIContainer = new WebMarkupContainer("settingApplyPenalOnIContainer");
        this.settingApplyPenalOnBlock.add(this.settingApplyPenalOnIContainer);
        this.settingApplyPenalOnProvider = new ApplyPenalOnProvider();
        this.settingApplyPenalOnField = new Select2SingleChoice<>("settingApplyPenalOnField", new PropertyModel<>(this, "settingApplyPenalOnValue"), this.settingApplyPenalOnProvider);
        this.settingApplyPenalOnField.setLabel(Model.of("On"));
        this.settingApplyPenalOnField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalOnIContainer.add(this.settingApplyPenalOnField);
        this.settingApplyPenalOnFeedback = new TextFeedbackPanel("settingApplyPenalOnFeedback", this.settingApplyPenalOnField);
        this.settingApplyPenalOnIContainer.add(this.settingApplyPenalOnFeedback);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableIContainer = new WebMarkupContainer("settingWithholdTaxApplicableIContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableIContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableFeedback);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupIContainer = new WebMarkupContainer("settingTaxGroupIContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupIContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupIContainer.add(this.settingTaxGroupFeedback);

    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingWithholdTaxApplicableValue != null && this.settingWithholdTaxApplicableValue;
        this.settingTaxGroupIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected void initSectionTerm() {

        this.termDefaultDepositAmountBlock = new WebMarkupBlock("termDefaultDepositAmountBlock", Size.Six_6);
        this.form.add(this.termDefaultDepositAmountBlock);
        this.termDefaultDepositAmountIContainer = new WebMarkupContainer("termDefaultDepositAmountIContainer");
        this.termDefaultDepositAmountBlock.add(this.termDefaultDepositAmountIContainer);
        this.termDefaultDepositAmountField = new TextField<>("termDefaultDepositAmountField", new PropertyModel<>(this, "termDefaultDepositAmountValue"));
        this.termDefaultDepositAmountField.setLabel(Model.of("Default Deposit Amount"));
        this.termDefaultDepositAmountIContainer.add(this.termDefaultDepositAmountField);
        this.termDefaultDepositAmountFeedback = new TextFeedbackPanel("termDefaultDepositAmountFeedback", this.termDefaultDepositAmountField);
        this.termDefaultDepositAmountIContainer.add(this.termDefaultDepositAmountFeedback);

        this.termMinimumDepositAmountBlock = new WebMarkupBlock("termMinimumDepositAmountBlock", Size.Six_6);
        this.form.add(this.termMinimumDepositAmountBlock);
        this.termMinimumDepositAmountIContainer = new WebMarkupContainer("termMinimumDepositAmountIContainer");
        this.termMinimumDepositAmountBlock.add(this.termMinimumDepositAmountIContainer);
        this.termMinimumDepositAmountField = new TextField<>("termMinimumDepositAmountField", new PropertyModel<>(this, "termMinimumDepositAmountValue"));
        this.termMinimumDepositAmountField.setLabel(Model.of("Minimum Deposit Amount"));
        this.termMinimumDepositAmountIContainer.add(this.termMinimumDepositAmountField);
        this.termMinimumDepositAmountFeedback = new TextFeedbackPanel("termMinimumDepositAmountFeedback", this.termMinimumDepositAmountField);
        this.termMinimumDepositAmountIContainer.add(this.termMinimumDepositAmountFeedback);

        this.termMaximumDepositAmountBlock = new WebMarkupBlock("termMaximumDepositAmountBlock", Size.Six_6);
        this.form.add(this.termMaximumDepositAmountBlock);
        this.termMaximumDepositAmountIContainer = new WebMarkupContainer("termMaximumDepositAmountIContainer");
        this.termMaximumDepositAmountBlock.add(this.termMaximumDepositAmountIContainer);
        this.termMaximumDepositAmountField = new TextField<>("termMaximumDepositAmountField", new PropertyModel<>(this, "termMaximumDepositAmountValue"));
        this.termMaximumDepositAmountField.setLabel(Model.of("Maximum Deposit Amount"));
        this.termMaximumDepositAmountIContainer.add(this.termMaximumDepositAmountField);
        this.termMaximumDepositAmountFeedback = new TextFeedbackPanel("termMaximumDepositAmountFeedback", this.termMaximumDepositAmountField);
        this.termMaximumDepositAmountIContainer.add(this.termMaximumDepositAmountFeedback);

        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodIContainer = new WebMarkupContainer("termInterestCompoundingPeriodIContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodIContainer);
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", new PropertyModel<>(this, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodFeedback);

        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodIContainer = new WebMarkupContainer("termInterestPostingPeriodIContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodIContainer);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest posting period"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodFeedback);

        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingIContainer = new WebMarkupContainer("termInterestCalculatedUsingIContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingIContainer);
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", new PropertyModel<>(this, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest calculated using"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingFeedback);

        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Six_6);
        this.form.add(this.termDayInYearBlock);
        this.termDayInYearIContainer = new WebMarkupContainer("termDayInYearIContainer");
        this.termDayInYearBlock.add(this.termDayInYearIContainer);
        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", new PropertyModel<>(this, "termDayInYearValue"), this.termDayInYearProvider);
        this.termDayInYearField.setLabel(Model.of("Days in year"));
        this.termDayInYearField.add(new OnChangeAjaxBehavior());
        this.termDayInYearIContainer.add(this.termDayInYearField);
        this.termDayInYearFeedback = new TextFeedbackPanel("termDayInYearFeedback", this.termDayInYearField);
        this.termDayInYearIContainer.add(this.termDayInYearFeedback);

    }

    protected void initSectionCurrency() {

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeIContainer = new WebMarkupContainer("currencyCodeIContainer");
        this.currencyCodeBlock.add(this.currencyCodeIContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeIContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceIContainer = new WebMarkupContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceIContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfIContainer = new WebMarkupContainer("currencyMultipleOfIContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfIContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initSectionDetail() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameIContainer = new WebMarkupContainer("detailProductNameIContainer");
        this.detailProductNameBlock.add(this.detailProductNameIContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameIContainer.add(this.detailProductNameFeedback);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameIContainer = new WebMarkupContainer("detailShortNameIContainer");
        this.detailShortNameBlock.add(this.detailShortNameIContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameIContainer.add(this.detailShortNameFeedback);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionIContainer = new WebMarkupContainer("detailDescriptionIContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionIContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionIContainer.add(this.detailDescriptionFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        ProductFixedDepositBuilder builder = new ProductFixedDepositBuilder();

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
                Long fromPeriod = (Long) interestRateChart.get("periodFrom");
                Long toPeriod = (Long) interestRateChart.get("periodTo");
                Long amountRangeFrom = (Long) interestRateChart.get("amountRangeFrom");
                Long amountRangeTo = (Long) interestRateChart.get("amountRangeTo");
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

        if (AccountingType.None.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.None);
        } else if (AccountingType.Cash.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Cash);
        }

        if (AccountingType.Cash.getDescription().equals(accounting)) {
            if (this.cashSavingReferenceValue != null) {
                builder.withSavingsReferenceAccountId(this.cashSavingReferenceValue.getId());
            }
            if (this.cashSavingControlValue != null) {
                builder.withSavingsControlAccountId(this.cashSavingControlValue.getId());
            }
            if (this.cashSavingTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashSavingTransferInSuspenseValue.getId());
            }
            if (this.cashInterestOnSavingValue != null) {
                builder.withInterestOnSavingsAccountId(this.cashInterestOnSavingValue.getId());
            }
            if (this.cashIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
            }
            if (this.cashIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
            }
        }

        if (AccountingType.Cash.getDescription().equals(accounting)) {
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
