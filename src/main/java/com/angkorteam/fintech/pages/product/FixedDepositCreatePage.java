package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
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
import org.apache.wicket.validation.validator.RangeValidator;
import org.json.JSONObject;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.fixed.ApplyPenalOn;
import com.angkorteam.fintech.dto.fixed.Attribute;
import com.angkorteam.fintech.dto.fixed.DayInYear;
import com.angkorteam.fintech.dto.fixed.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.fixed.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.fixed.InterestPostingPeriod;
import com.angkorteam.fintech.dto.fixed.LockInPeriod;
import com.angkorteam.fintech.dto.fixed.OperandType;
import com.angkorteam.fintech.dto.fixed.Operator;
import com.angkorteam.fintech.dto.request.FixedBuilder;
import com.angkorteam.fintech.dto.request.FixedBuilder.IncentiveBuilder;
import com.angkorteam.fintech.helper.FixedHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.InterestRateChartPopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.popup.fixed.IncentivePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.fixed.ApplyPenalOnProvider;
import com.angkorteam.fintech.provider.fixed.DayInYearProvider;
import com.angkorteam.fintech.provider.fixed.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.fixed.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.fixed.InterestPostingPeriodProvider;
import com.angkorteam.fintech.provider.fixed.LockInPeriodProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FixedDepositCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    // Detail

    private WebMarkupContainer detailProductNameBlock;
    private WebMarkupContainer detailProductNameContainer;
    private String detailProductNameValue;
    private TextField<String> detailProductNameField;
    private TextFeedbackPanel detailProductNameFeedback;

    private WebMarkupContainer detailShortNameBlock;
    private WebMarkupContainer detailShortNameContainer;
    private String detailShortNameValue;
    private TextField<String> detailShortNameField;
    private TextFeedbackPanel detailShortNameFeedback;

    private WebMarkupContainer detailDescriptionBlock;
    private WebMarkupContainer detailDescriptionContainer;
    private String detailDescriptionValue;
    private TextField<String> detailDescriptionField;
    private TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    private WebMarkupContainer currencyCodeBlock;
    private WebMarkupContainer currencyCodeContainer;
    private SingleChoiceProvider currencyCodeProvider;
    private Option currencyCodeValue;
    private Select2SingleChoice<Option> currencyCodeField;
    private TextFeedbackPanel currencyCodeFeedback;

    private WebMarkupContainer currencyDecimalPlaceBlock;
    private WebMarkupContainer currencyDecimalPlaceContainer;
    private Integer currencyDecimalPlaceValue;
    private TextField<Integer> currencyDecimalPlaceField;
    private TextFeedbackPanel currencyDecimalPlaceFeedback;

    private WebMarkupContainer currencyMultipleOfBlock;
    private WebMarkupContainer currencyMultipleOfContainer;
    private Integer currencyMultipleOfValue;
    private TextField<Integer> currencyMultipleOfField;
    private TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    private WebMarkupContainer termDefaultDepositAmountBlock;
    private WebMarkupContainer termDefaultDepositAmountContainer;
    private Double termDefaultDepositAmountValue;
    private TextField<Double> termDefaultDepositAmountField;
    private TextFeedbackPanel termDefaultDepositAmountFeedback;

    private WebMarkupContainer termMinimumDepositAmountBlock;
    private WebMarkupContainer termMinimumDepositAmountContainer;
    private Double termMinimumDepositAmountValue;
    private TextField<Double> termMinimumDepositAmountField;
    private TextFeedbackPanel termMinimumDepositAmountFeedback;

    private WebMarkupContainer termMaximumDepositAmountBlock;
    private WebMarkupContainer termMaximumDepositAmountContainer;
    private Double termMaximumDepositAmountValue;
    private TextField<Double> termMaximumDepositAmountField;
    private TextFeedbackPanel termMaximumDepositAmountFeedback;

    private WebMarkupContainer termInterestCompoundingPeriodBlock;
    private WebMarkupContainer termInterestCompoundingPeriodContainer;
    private InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    private Option termInterestCompoundingPeriodValue;
    private Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    private TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    private WebMarkupContainer termInterestPostingPeriodBlock;
    private WebMarkupContainer termInterestPostingPeriodContainer;
    private InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    private Option termInterestPostingPeriodValue;
    private Select2SingleChoice<Option> termInterestPostingPeriodField;
    private TextFeedbackPanel termInterestPostingPeriodFeedback;

    private WebMarkupContainer termInterestCalculatedUsingBlock;
    private WebMarkupContainer termInterestCalculatedUsingContainer;
    private InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    private Option termInterestCalculatedUsingValue;
    private Select2SingleChoice<Option> termInterestCalculatedUsingField;
    private TextFeedbackPanel termInterestCalculatedUsingFeedback;

    private WebMarkupContainer termDayInYearBlock;
    private WebMarkupContainer termDayInYearContainer;
    private DayInYearProvider termDayInYearProvider;
    private Option termDayInYearValue;
    private Select2SingleChoice<Option> termDayInYearField;
    private TextFeedbackPanel termDayInYearFeedback;

    // Setting

    private WebMarkupContainer settingLockInPeriodBlock;
    private WebMarkupContainer settingLockInPeriodContainer;
    private Integer settingLockInPeriodValue;
    private TextField<Integer> settingLockInPeriodField;
    private TextFeedbackPanel settingLockInPeriodFeedback;

    private WebMarkupContainer settingLockInTypeBlock;
    private WebMarkupContainer settingLockInTypeContainer;
    private LockInPeriodProvider settingLockInTypeProvider;
    private Option settingLockInTypeValue;
    private Select2SingleChoice<Option> settingLockInTypeField;
    private TextFeedbackPanel settingLockInTypeFeedback;

    private WebMarkupContainer settingMinimumDepositTermBlock;
    private WebMarkupContainer settingMinimumDepositTermContainer;
    private Double settingMinimumDepositTermValue;
    private TextField<Double> settingMinimumDepositTermField;
    private TextFeedbackPanel settingMinimumDepositTermFeedback;

    private WebMarkupContainer settingMinimumDepositTypeBlock;
    private WebMarkupContainer settingMinimumDepositTypeContainer;
    private LockInPeriodProvider settingMinimumDepositTypeProvider;
    private Option settingMinimumDepositTypeValue;
    private Select2SingleChoice<Option> settingMinimumDepositTypeField;
    private TextFeedbackPanel settingMinimumDepositTypeFeedback;

    private WebMarkupContainer settingInMultiplesOfBlock;
    private WebMarkupContainer settingInMultiplesOfContainer;
    private Integer settingInMultiplesOfValue;
    private TextField<Integer> settingInMultiplesOfField;
    private TextFeedbackPanel settingInMultiplesOfFeedback;

    private WebMarkupContainer settingInMultiplesTypeBlock;
    private WebMarkupContainer settingInMultiplesTypeContainer;
    private LockInPeriodProvider settingInMultiplesTypeProvider;
    private Option settingInMultiplesTypeValue;
    private Select2SingleChoice<Option> settingInMultiplesTypeField;
    private TextFeedbackPanel settingInMultiplesTypeFeedback;

    private WebMarkupContainer settingMaximumDepositTermBlock;
    private WebMarkupContainer settingMaximumDepositTermContainer;
    private Double settingMaximumDepositTermValue;
    private TextField<Double> settingMaximumDepositTermField;
    private TextFeedbackPanel settingMaximumDepositTermFeedback;

    private WebMarkupContainer settingMaximumDepositTypeBlock;
    private WebMarkupContainer settingMaximumDepositTypeContainer;
    private LockInPeriodProvider settingMaximumDepositTypeProvider;
    private Option settingMaximumDepositTypeValue;
    private Select2SingleChoice<Option> settingMaximumDepositTypeField;
    private TextFeedbackPanel settingMaximumDepositTypeFeedback;

    private WebMarkupContainer settingForPreMatureClosureBlock;
    private WebMarkupContainer settingForPreMatureClosureContainer;
    private Boolean settingForPreMatureClosureValue;
    private CheckBox settingForPreMatureClosureField;
    private TextFeedbackPanel settingForPreMatureClosureFeedback;

    private WebMarkupContainer settingApplyPenalInterestBlock;
    private WebMarkupContainer settingApplyPenalInterestContainer;
    private Double settingApplyPenalInterestValue;
    private TextField<Double> settingApplyPenalInterestField;
    private TextFeedbackPanel settingApplyPenalInterestFeedback;

    private WebMarkupContainer settingApplyPenalOnBlock;
    private WebMarkupContainer settingApplyPenalOnContainer;
    private ApplyPenalOnProvider settingApplyPenalOnProvider;
    private Option settingApplyPenalOnValue;
    private Select2SingleChoice<Option> settingApplyPenalOnField;
    private TextFeedbackPanel settingApplyPenalOnFeedback;

    private WebMarkupContainer settingWithholdTaxApplicableBlock;
    private WebMarkupContainer settingWithholdTaxApplicableContainer;
    private Boolean settingWithholdTaxApplicableValue;
    private CheckBox settingWithholdTaxApplicableField;
    private TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    private WebMarkupContainer settingTaxGroupBlock;
    private WebMarkupContainer settingTaxGroupContainer;
    private SingleChoiceProvider settingTaxGroupProvider;
    private Option settingTaxGroupValue;
    private Select2SingleChoice<Option> settingTaxGroupField;
    private TextFeedbackPanel settingTaxGroupFeedback;

    // Interest Rate Chart

    private WebMarkupContainer interestRateValidFromDateBlock;
    private WebMarkupContainer interestRateValidFromDateContainer;
    private Date interestRateValidFromDateValue;
    private DateTextField interestRateValidFromDateField;
    private TextFeedbackPanel interestRateValidFromDateFeedback;

    private WebMarkupContainer interestRateValidEndDateBlock;
    private WebMarkupContainer interestRateValidEndDateContainer;
    private Date interestRateValidEndDateValue;
    private DateTextField interestRateValidEndDateField;
    private TextFeedbackPanel interestRateValidEndDateFeedback;

    private WebMarkupContainer interestRatePrimaryGroupingByAmountBlock;
    private WebMarkupContainer interestRatePrimaryGroupingByAmountContainer;
    private Boolean interestRatePrimaryGroupingByAmountValue;
    private CheckBox interestRatePrimaryGroupingByAmountField;
    private TextFeedbackPanel interestRatePrimaryGroupingByAmountFeedback;

    private List<Map<String, Object>> interestRateChartValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> interestRateChartTable;
    private ListDataProvider interestRateChartProvider;
    private ModalWindow interestRateChartPopup;
    private AjaxLink<Void> interestRateChartAddLink;

    private ModalWindow incentivePopup;

    // Charges

    private List<Map<String, Object>> chargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargeTable;
    private ListDataProvider chargeProvider;
    private ModalWindow chargePopup;
    private AjaxLink<Void> chargeAddLink;

    // Accounting

    private String accountingValue = ACC_NONE;
    private RadioGroup<String> accountingField;

    private WebMarkupContainer cashBlock;
    private WebMarkupContainer cashContainer;

    private SingleChoiceProvider cashSavingReferenceProvider;
    private Option cashSavingReferenceValue;
    private Select2SingleChoice<Option> cashSavingReferenceField;
    private TextFeedbackPanel cashSavingReferenceFeedback;

    private SingleChoiceProvider cashSavingControlProvider;
    private Option cashSavingControlValue;
    private Select2SingleChoice<Option> cashSavingControlField;
    private TextFeedbackPanel cashSavingControlFeedback;

    private SingleChoiceProvider cashSavingsTransfersInSuspenseProvider;
    private Option cashSavingsTransfersInSuspenseValue;
    private Select2SingleChoice<Option> cashSavingsTransfersInSuspenseField;
    private TextFeedbackPanel cashSavingsTransfersInSuspenseFeedback;

    private SingleChoiceProvider cashInterestOnSavingsProvider;
    private Option cashInterestOnSavingsValue;
    private Select2SingleChoice<Option> cashInterestOnSavingsField;
    private TextFeedbackPanel cashInterestOnSavingsFeedback;

    private SingleChoiceProvider cashInterestOnSavingProvider;
    private Option cashInterestOnSavingValue;
    private Select2SingleChoice<Option> cashInterestOnSavingField;
    private TextFeedbackPanel cashInterestOnSavingFeedback;

    private SingleChoiceProvider cashIncomeFromFeesProvider;
    private Option cashIncomeFromFeesValue;
    private Select2SingleChoice<Option> cashIncomeFromFeesField;
    private TextFeedbackPanel cashIncomeFromFeesFeedback;

    private SingleChoiceProvider cashIncomeFromPenaltiesProvider;
    private Option cashIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> cashIncomeFromPenaltiesField;
    private TextFeedbackPanel cashIncomeFromPenaltiesFeedback;

    // Advanced Accounting Rule

    private WebMarkupContainer advancedAccountingRuleBlock;
    private WebMarkupContainer advancedAccountingRuleContainer;

    private List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    private ListDataProvider advancedAccountingRuleFundSourceProvider;
    private AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    private ModalWindow fundSourcePopup;

    private List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    private ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    private AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    private ModalWindow feeIncomePopup;

    private List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    private ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    private AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    private ModalWindow penaltyIncomePopup;

    private Option itemChargeValue;
    private Option itemPeriodTypeValue;
    private Date itemPeriodFromValue;
    private Date itemPeriodToValue;
    private Double itemAmountRangeFromValue;
    private Double itemAmountRangeToValue;
    private Double itemInterestValue;
    private String itemDescriptionValue;
    private Option itemPaymentValue;
    private Option itemAccountValue;

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

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", FixedDepositBrowsePage.class);
	this.form.add(this.closeLink);

	initDetail();

	initCurrency();

	initTerm();

	initSetting();

	initInterestRateChart();

	initCharge();

	initAccounting();

	initDefault();
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
	this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = 1");
	this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField",
		new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
	this.cashSavingReferenceField.setRequired(false);
	this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingReferenceField);
	this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback",
		this.cashSavingReferenceField);
	this.cashContainer.add(this.cashSavingReferenceFeedback);

	this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = 1");
	this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField",
		new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
	this.cashSavingControlField.setRequired(false);
	this.cashSavingControlField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingControlField);
	this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback",
		this.cashSavingControlField);
	this.cashContainer.add(this.cashSavingControlFeedback);

	this.cashSavingsTransfersInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashSavingsTransfersInSuspenseProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingsTransfersInSuspenseProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashSavingsTransfersInSuspenseField = new Select2SingleChoice<>("cashSavingsTransfersInSuspenseField",
		new PropertyModel<>(this, "cashSavingsTransfersInSuspenseValue"),
		this.cashSavingsTransfersInSuspenseProvider);
	this.cashSavingsTransfersInSuspenseField.setRequired(false);
	this.cashSavingsTransfersInSuspenseField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingsTransfersInSuspenseField);
	this.cashSavingsTransfersInSuspenseFeedback = new TextFeedbackPanel("cashSavingsTransfersInSuspenseFeedback",
		this.cashSavingsTransfersInSuspenseField);
	this.cashContainer.add(this.cashSavingsTransfersInSuspenseFeedback);

	this.cashInterestOnSavingsProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashInterestOnSavingsProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashInterestOnSavingsProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashInterestOnSavingsField = new Select2SingleChoice<>("cashInterestOnSavingsField",
		new PropertyModel<>(this, "cashInterestOnSavingsValue"), this.cashInterestOnSavingsProvider);
	this.cashInterestOnSavingsField.setRequired(false);
	this.cashInterestOnSavingsField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashInterestOnSavingsField);
	this.cashInterestOnSavingsFeedback = new TextFeedbackPanel("cashInterestOnSavingsFeedback",
		this.cashInterestOnSavingsField);
	this.cashContainer.add(this.cashInterestOnSavingsFeedback);

	this.cashIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = 5");
	this.cashIncomeFromFeesField = new Select2SingleChoice<>("cashIncomeFromFeesField",
		new PropertyModel<>(this, "cashIncomeFromFeesValue"), this.cashIncomeFromFeesProvider);
	this.cashIncomeFromFeesField.setRequired(false);
	this.cashIncomeFromFeesField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashIncomeFromFeesField);
	this.cashIncomeFromFeesFeedback = new TextFeedbackPanel("cashIncomeFromFeesFeedback",
		this.cashIncomeFromFeesField);
	this.cashContainer.add(this.cashIncomeFromFeesFeedback);

	this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 2");
	this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField",
		new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
	this.cashIncomeFromPenaltiesField.setRequired(false);
	this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashIncomeFromPenaltiesField);
	this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback",
		this.cashIncomeFromPenaltiesField);
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
	    this.fundSourcePopup
		    .setContent(new PaymentTypePopup(this.fundSourcePopup.getContentId(), this.fundSourcePopup, this));
	    this.fundSourcePopup.setOnClose(this::fundSourcePopupOnClose);

	    List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
	    advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment",
		    this::advancedAccountingRuleFundSourcePaymentColumn));
	    advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account",
		    this::advancedAccountingRuleFundSourceAccountColumn));
	    advancedAccountingRuleFundSourceColumn
		    .add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceActionItem,
			    this::advancedAccountingRuleFundSourceActionClick));
	    this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(
		    this.advancedAccountingRuleFundSourceValue);
	    this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable",
		    advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceTable);
	    this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
	    this.advancedAccountingRuleFundSourceTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

	    this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
	    this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceAddLink);
	}

	// Table
	{
	    this.feeIncomePopup = new ModalWindow("feeIncomePopup");
	    add(this.feeIncomePopup);
	    this.feeIncomePopup
		    .setContent(new FeeChargePopup(this.feeIncomePopup.getContentId(), this.feeIncomePopup, this));
	    this.feeIncomePopup.setOnClose(this::feeIncomePopupOnClose);

	    List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
	    advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge",
		    this::advancedAccountingRuleFeeIncomeChargeColumn));
	    advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account",
		    this::advancedAccountingRuleFeeIncomeAccountColumn));
	    advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"),
		    this::advancedAccountingRuleFeeIncomeActionItem, this::advancedAccountingRuleFeeIncomeActionClick));
	    this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(
		    this.advancedAccountingRuleFeeIncomeValue);
	    this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable",
		    advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeTable);
	    this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
	    this.advancedAccountingRuleFeeIncomeTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

	    this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
	    this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
	}

	// Table
	{
	    this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
	    add(this.penaltyIncomePopup);
	    this.penaltyIncomePopup.setContent(
		    new PenaltyChargePopup(this.penaltyIncomePopup.getContentId(), this.penaltyIncomePopup, this));
	    this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupOnClose);

	    List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
	    advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge",
		    this::advancedAccountingRulePenaltyIncomeChargeColumn));
	    advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account",
		    "account", this::advancedAccountingRulePenaltyIncomeAccountColumn));
	    advancedAccountingRulePenaltyIncomeColumn.add(
		    new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeActionItem,
			    this::advancedAccountingRulePenaltyIncomeActionClick));
	    this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(
		    this.advancedAccountingRulePenaltyIncomeValue);
	    this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable",
		    advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
	    this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
	    this.advancedAccountingRulePenaltyIncomeTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

	    this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>(
		    "advancedAccountingRulePenaltyIncomeAddLink");
	    this.advancedAccountingRulePenaltyIncomeAddLink
		    .setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
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
	this.penaltyIncomePopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected void advancedAccountingRulePenaltyIncomeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRulePenaltyIncomeValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.itemChargeValue = null;
	this.itemAccountValue = null;
	this.feeIncomePopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeChargeColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected void advancedAccountingRuleFeeIncomeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRuleFeeIncomeValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected List<ActionItem> feeIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void advancedAccountingRuleFundSourceActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRuleFundSourceValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected List<ActionItem> advancedAccountingRuleFundSourceActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.fundSourcePopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourcePaymentColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRuleFundSourceAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected List<ActionItem> fundSourceActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
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
	this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this));
	this.chargePopup.setOnClose(this::chargePopupOnClose);

	List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newArrayList();
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
	Map<String, Object> chargeObject = jdbcTemplate.queryForMap(
		"select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?",
		chargeId);
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
	this.chargePopup.show(target);
	return false;
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Number value = (Number) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(value.doubleValue())));
	}
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected void chargeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.chargeValue.size(); i++) {
	    Map<String, Object> column = this.chargeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.chargeValue.remove(index);
	}
	ajaxRequestTarget.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initInterestRateChart() {
	this.interestRateValidFromDateBlock = new WebMarkupContainer("interestRateValidFromDateBlock");
	this.interestRateValidFromDateBlock.setOutputMarkupId(true);
	this.form.add(this.interestRateValidFromDateBlock);
	this.interestRateValidFromDateContainer = new WebMarkupContainer("interestRateValidFromDateContainer");
	this.interestRateValidFromDateBlock.add(this.interestRateValidFromDateContainer);
	this.interestRateValidFromDateField = new DateTextField("interestRateValidFromDateField",
		new PropertyModel<>(this, "interestRateValidFromDateValue"));
	this.interestRateValidFromDateField.setRequired(false);
	this.interestRateValidFromDateContainer.add(this.interestRateValidFromDateField);
	this.interestRateValidFromDateFeedback = new TextFeedbackPanel("interestRateValidFromDateFeedback",
		this.interestRateValidFromDateField);
	this.interestRateValidFromDateContainer.add(this.interestRateValidFromDateFeedback);

	this.interestRateValidEndDateBlock = new WebMarkupContainer("interestRateValidEndDateBlock");
	this.interestRateValidEndDateBlock.setOutputMarkupId(true);
	this.form.add(this.interestRateValidEndDateBlock);
	this.interestRateValidEndDateContainer = new WebMarkupContainer("interestRateValidEndDateContainer");
	this.interestRateValidEndDateBlock.add(this.interestRateValidEndDateContainer);
	this.interestRateValidEndDateField = new DateTextField("interestRateValidEndDateField",
		new PropertyModel<>(this, "interestRateValidEndDateValue"));
	this.interestRateValidEndDateField.setRequired(false);
	this.interestRateValidEndDateContainer.add(this.interestRateValidEndDateField);
	this.interestRateValidEndDateFeedback = new TextFeedbackPanel("interestRateValidEndDateFeedback",
		this.interestRateValidEndDateField);
	this.interestRateValidEndDateContainer.add(this.interestRateValidEndDateFeedback);

	this.interestRatePrimaryGroupingByAmountBlock = new WebMarkupContainer(
		"interestRatePrimaryGroupingByAmountBlock");
	this.form.add(this.interestRatePrimaryGroupingByAmountBlock);
	this.interestRatePrimaryGroupingByAmountContainer = new WebMarkupContainer(
		"interestRatePrimaryGroupingByAmountContainer");
	this.interestRatePrimaryGroupingByAmountBlock.add(this.interestRatePrimaryGroupingByAmountContainer);
	this.interestRatePrimaryGroupingByAmountField = new CheckBox("interestRatePrimaryGroupingByAmountField",
		new PropertyModel<>(this, "interestRatePrimaryGroupingByAmountValue"));
	this.interestRatePrimaryGroupingByAmountField.setRequired(false);
	this.interestRatePrimaryGroupingByAmountField.add(new OnChangeAjaxBehavior());
	this.interestRatePrimaryGroupingByAmountContainer.add(this.interestRatePrimaryGroupingByAmountField);
	this.interestRatePrimaryGroupingByAmountFeedback = new TextFeedbackPanel(
		"interestRatePrimaryGroupingByAmountFeedback", this.interestRatePrimaryGroupingByAmountField);
	this.interestRatePrimaryGroupingByAmountContainer.add(this.interestRatePrimaryGroupingByAmountFeedback);

	// Table
	this.interestRateChartPopup = new ModalWindow("interestRateChartPopup");
	this.interestRateChartPopup.setHeightUnit("px");
	this.interestRateChartPopup.setWidthUnit("px");
	this.interestRateChartPopup.setInitialHeight(600);
	this.interestRateChartPopup.setInitialWidth(1000);
	add(this.interestRateChartPopup);
	this.interestRateChartPopup.setContent(new InterestRateChartPopup(this.interestRateChartPopup.getContentId(),
		this.interestRateChartPopup, this));
	this.interestRateChartPopup.setOnClose(this::interestRateChartPopupOnClose);

	this.incentivePopup = new ModalWindow("incentivePopup");
	this.incentivePopup.setHeightUnit("px");
	this.incentivePopup.setWidthUnit("px");
	this.incentivePopup.setInitialHeight(600);
	this.incentivePopup.setInitialWidth(1100);
	add(this.incentivePopup);
	this.incentivePopup.setOnClose(this::incentivePopupOnClose);

	List<IColumn<Map<String, Object>, String>> interestRateChartColumn = Lists.newArrayList();
	interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType",
		this::interestRateChartPeriodTypeColumn));
	interestRateChartColumn.add(new TextColumn(Model.of("Period From"), "periodFrom", "periodFrom",
		this::interestRateChartPeriodFromColumn));
	interestRateChartColumn.add(
		new TextColumn(Model.of("Period To"), "periodTo", "periodTo", this::interestRateChartPeriodToColumn));
	interestRateChartColumn.add(new TextColumn(Model.of("Amount Range From"), "amountRangeFrom", "amountRangeFrom",
		this::interestRateChartAmountRangeFromColumn));
	interestRateChartColumn.add(new TextColumn(Model.of("Amount Range To"), "amountRangeTo", "amountRangeTo",
		this::interestRateChartAmountRangeToColumn));
	interestRateChartColumn.add(
		new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartInterestColumn));
	interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description",
		this::interestRateChartDescriptionColumn));
	interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartActionItem,
		this::interestRateChartActionClick));
	this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue);
	this.interestRateChartTable = new DataTable<>("interestRateChartTable", interestRateChartColumn,
		this.interestRateChartProvider, 20);
	this.form.add(this.interestRateChartTable);
	this.interestRateChartTable
		.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
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
	item.put("interestRate", Lists.newArrayList());
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
	this.interestRateChartPopup.show(target);
	return false;
    }

    protected ItemPanel interestRateChartPeriodTypeColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Option value = (Option) model.get(jdbcColumn);
	return new TextCell(Model.of(value == null ? "" : value.getText()));
    }

    protected ItemPanel interestRateChartPeriodFromColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Date value = (Date) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value)));
	}
    }

    protected ItemPanel interestRateChartPeriodToColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Date value = (Date) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value)));
	}
    }

    protected ItemPanel interestRateChartAmountRangeFromColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Double value = (Double) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(value)));
	}
    }

    protected ItemPanel interestRateChartAmountRangeToColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Double value = (Double) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(value)));
	}
    }

    protected ItemPanel interestRateChartInterestColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	Double value = (Double) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(value)));
	}
    }

    protected ItemPanel interestRateChartDescriptionColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected void interestRateChartActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget target) {
	if ("delete".equals(s)) {
	    int index = -1;
	    for (int i = 0; i < this.interestRateChartValue.size(); i++) {
		Map<String, Object> column = this.interestRateChartValue.get(i);
		if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		    index = i;
		    break;
		}
	    }
	    if (index >= 0) {
		this.interestRateChartValue.remove(index);
	    }
	    target.add(this.interestRateChartTable);
	} else if ("incentives".equals(s)) {
	    List<Map<String, Object>> incentiveValue = (List<Map<String, Object>>) stringObjectMap.get("interestRate");
	    this.incentivePopup.setContent(
		    new IncentivePopup(this.incentivePopup.getContentId(), this.incentivePopup, incentiveValue));
	    this.incentivePopup.show(target);
	}
    }

    protected List<ActionItem> interestRateChartActionItem(String s, Map<String, Object> stringObjectMap) {
	List<ActionItem> actions = Lists.newArrayList();
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
	this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField",
		new PropertyModel<>(this, "settingLockInPeriodValue"));
	this.settingLockInPeriodField.setRequired(true);
	this.settingLockInPeriodContainer.add(this.settingLockInPeriodField);
	this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback",
		this.settingLockInPeriodField);
	this.settingLockInPeriodContainer.add(this.settingLockInPeriodFeedback);

	this.settingLockInTypeBlock = new WebMarkupContainer("settingLockInTypeBlock");
	this.form.add(this.settingLockInTypeBlock);
	this.settingLockInTypeContainer = new WebMarkupContainer("settingLockInTypeContainer");
	this.settingLockInTypeBlock.add(this.settingLockInTypeContainer);
	this.settingLockInTypeProvider = new LockInPeriodProvider();
	this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0,
		new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
	this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
	this.settingLockInTypeField.setRequired(true);
	this.settingLockInTypeContainer.add(this.settingLockInTypeField);
	this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback",
		this.settingLockInTypeField);
	this.settingLockInTypeContainer.add(this.settingLockInTypeFeedback);

	this.settingMinimumDepositTermBlock = new WebMarkupContainer("settingMinimumDepositTermBlock");
	this.settingMinimumDepositTermBlock.setOutputMarkupId(true);
	this.form.add(this.settingMinimumDepositTermBlock);
	this.settingMinimumDepositTermContainer = new WebMarkupContainer("settingMinimumDepositTermContainer");
	this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermContainer);
	this.settingMinimumDepositTermField = new TextField<>("settingMinimumDepositTermField",
		new PropertyModel<>(this, "settingMinimumDepositTermValue"));
	this.settingMinimumDepositTermField.setRequired(true);
	this.settingMinimumDepositTermContainer.add(this.settingMinimumDepositTermField);
	this.settingMinimumDepositTermFeedback = new TextFeedbackPanel("settingMinimumDepositTermFeedback",
		this.settingMinimumDepositTermField);
	this.settingMinimumDepositTermContainer.add(this.settingMinimumDepositTermFeedback);

	this.settingMinimumDepositTypeBlock = new WebMarkupContainer("settingMinimumDepositTypeBlock");
	this.form.add(this.settingMinimumDepositTypeBlock);
	this.settingMinimumDepositTypeContainer = new WebMarkupContainer("settingMinimumDepositTypeContainer");
	this.settingMinimumDepositTypeBlock.add(this.settingMinimumDepositTypeContainer);
	this.settingMinimumDepositTypeProvider = new LockInPeriodProvider();
	this.settingMinimumDepositTypeField = new Select2SingleChoice<>("settingMinimumDepositTypeField", 0,
		new PropertyModel<>(this, "settingMinimumDepositTypeValue"), this.settingMinimumDepositTypeProvider);
	this.settingMinimumDepositTypeField.add(new OnChangeAjaxBehavior());
	this.settingMinimumDepositTypeField.setRequired(true);
	this.settingMinimumDepositTypeContainer.add(this.settingMinimumDepositTypeField);
	this.settingMinimumDepositTypeFeedback = new TextFeedbackPanel("settingMinimumDepositTypeFeedback",
		this.settingMinimumDepositTypeField);
	this.settingMinimumDepositTypeContainer.add(this.settingMinimumDepositTypeFeedback);

	this.settingInMultiplesOfBlock = new WebMarkupContainer("settingInMultiplesOfBlock");
	this.settingInMultiplesOfBlock.setOutputMarkupId(true);
	this.form.add(this.settingInMultiplesOfBlock);
	this.settingInMultiplesOfContainer = new WebMarkupContainer("settingInMultiplesOfContainer");
	this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfContainer);
	this.settingInMultiplesOfField = new TextField<>("settingInMultiplesOfField",
		new PropertyModel<>(this, "settingInMultiplesOfValue"));
	this.settingInMultiplesOfField.setRequired(true);
	this.settingInMultiplesOfContainer.add(this.settingInMultiplesOfField);
	this.settingInMultiplesOfFeedback = new TextFeedbackPanel("settingInMultiplesOfFeedback",
		this.settingInMultiplesOfField);
	this.settingInMultiplesOfContainer.add(this.settingInMultiplesOfFeedback);

	this.settingInMultiplesTypeBlock = new WebMarkupContainer("settingInMultiplesTypeBlock");
	this.form.add(this.settingInMultiplesTypeBlock);
	this.settingInMultiplesTypeContainer = new WebMarkupContainer("settingInMultiplesTypeContainer");
	this.settingInMultiplesTypeBlock.add(this.settingInMultiplesTypeContainer);
	this.settingInMultiplesTypeProvider = new LockInPeriodProvider();
	this.settingInMultiplesTypeField = new Select2SingleChoice<>("settingInMultiplesTypeField", 0,
		new PropertyModel<>(this, "settingInMultiplesTypeValue"), this.settingInMultiplesTypeProvider);
	this.settingInMultiplesTypeField.add(new OnChangeAjaxBehavior());
	this.settingInMultiplesTypeField.setRequired(true);
	this.settingInMultiplesTypeContainer.add(this.settingInMultiplesTypeField);
	this.settingInMultiplesTypeFeedback = new TextFeedbackPanel("settingInMultiplesTypeFeedback",
		this.settingInMultiplesTypeField);
	this.settingInMultiplesTypeContainer.add(this.settingInMultiplesTypeFeedback);

	this.settingMaximumDepositTermBlock = new WebMarkupContainer("settingMaximumDepositTermBlock");
	this.settingMaximumDepositTermBlock.setOutputMarkupId(true);
	this.form.add(this.settingMaximumDepositTermBlock);
	this.settingMaximumDepositTermContainer = new WebMarkupContainer("settingMaximumDepositTermContainer");
	this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermContainer);
	this.settingMaximumDepositTermField = new TextField<>("settingMaximumDepositTermField",
		new PropertyModel<>(this, "settingMaximumDepositTermValue"));
	this.settingMaximumDepositTermField.setRequired(true);
	this.settingMaximumDepositTermContainer.add(this.settingMaximumDepositTermField);
	this.settingMaximumDepositTermFeedback = new TextFeedbackPanel("settingMaximumDepositTermFeedback",
		this.settingMaximumDepositTermField);
	this.settingMaximumDepositTermContainer.add(this.settingMaximumDepositTermFeedback);

	this.settingMaximumDepositTypeBlock = new WebMarkupContainer("settingMaximumDepositTypeBlock");
	this.form.add(this.settingMaximumDepositTypeBlock);
	this.settingMaximumDepositTypeContainer = new WebMarkupContainer("settingMaximumDepositTypeContainer");
	this.settingMaximumDepositTypeBlock.add(this.settingMaximumDepositTypeContainer);
	this.settingMaximumDepositTypeProvider = new LockInPeriodProvider();
	this.settingMaximumDepositTypeField = new Select2SingleChoice<>("settingMaximumDepositTypeField", 0,
		new PropertyModel<>(this, "settingMaximumDepositTypeValue"), this.settingMaximumDepositTypeProvider);
	this.settingMaximumDepositTypeField.add(new OnChangeAjaxBehavior());
	this.settingMaximumDepositTypeField.setRequired(true);
	this.settingMaximumDepositTypeContainer.add(this.settingMaximumDepositTypeField);
	this.settingMaximumDepositTypeFeedback = new TextFeedbackPanel("settingMaximumDepositTypeFeedback",
		this.settingMaximumDepositTypeField);
	this.settingMaximumDepositTypeContainer.add(this.settingMaximumDepositTypeFeedback);

	this.settingForPreMatureClosureBlock = new WebMarkupContainer("settingForPreMatureClosureBlock");
	this.form.add(this.settingForPreMatureClosureBlock);
	this.settingForPreMatureClosureContainer = new WebMarkupContainer("settingForPreMatureClosureContainer");
	this.settingForPreMatureClosureBlock.add(this.settingForPreMatureClosureContainer);
	this.settingForPreMatureClosureField = new CheckBox("settingForPreMatureClosureField",
		new PropertyModel<>(this, "settingForPreMatureClosureValue"));
	this.settingForPreMatureClosureField.setRequired(false);
	this.settingForPreMatureClosureField.add(new OnChangeAjaxBehavior());
	this.settingForPreMatureClosureContainer.add(this.settingForPreMatureClosureField);
	this.settingForPreMatureClosureFeedback = new TextFeedbackPanel("settingForPreMatureClosureFeedback",
		this.settingForPreMatureClosureField);
	this.settingForPreMatureClosureContainer.add(this.settingForPreMatureClosureFeedback);

	this.settingApplyPenalInterestBlock = new WebMarkupContainer("settingApplyPenalInterestBlock");
	this.settingApplyPenalInterestBlock.setOutputMarkupId(true);
	this.form.add(this.settingApplyPenalInterestBlock);
	this.settingApplyPenalInterestContainer = new WebMarkupContainer("settingApplyPenalInterestContainer");
	this.settingApplyPenalInterestBlock.add(this.settingApplyPenalInterestContainer);
	this.settingApplyPenalInterestField = new TextField<>("settingApplyPenalInterestField",
		new PropertyModel<>(this, "settingApplyPenalInterestValue"));
	this.settingApplyPenalInterestField.setRequired(true);
	this.settingApplyPenalInterestContainer.add(this.settingApplyPenalInterestField);
	this.settingApplyPenalInterestFeedback = new TextFeedbackPanel("settingApplyPenalInterestFeedback",
		this.settingApplyPenalInterestField);
	this.settingApplyPenalInterestContainer.add(this.settingApplyPenalInterestFeedback);

	this.settingApplyPenalOnBlock = new WebMarkupContainer("settingApplyPenalOnBlock");
	this.form.add(this.settingApplyPenalOnBlock);
	this.settingApplyPenalOnContainer = new WebMarkupContainer("settingApplyPenalOnContainer");
	this.settingApplyPenalOnBlock.add(this.settingApplyPenalOnContainer);
	this.settingApplyPenalOnProvider = new ApplyPenalOnProvider();
	this.settingApplyPenalOnField = new Select2SingleChoice<>("settingApplyPenalOnField", 0,
		new PropertyModel<>(this, "settingApplyPenalOnValue"), this.settingApplyPenalOnProvider);
	this.settingApplyPenalOnField.add(new OnChangeAjaxBehavior());
	this.settingApplyPenalOnField.setRequired(true);
	this.settingApplyPenalOnContainer.add(this.settingApplyPenalOnField);
	this.settingApplyPenalOnFeedback = new TextFeedbackPanel("settingApplyPenalOnFeedback",
		this.settingApplyPenalOnField);
	this.settingApplyPenalOnContainer.add(this.settingApplyPenalOnFeedback);

	this.settingWithholdTaxApplicableBlock = new WebMarkupContainer("settingWithholdTaxApplicableBlock");
	this.form.add(this.settingWithholdTaxApplicableBlock);
	this.settingWithholdTaxApplicableContainer = new WebMarkupContainer("settingWithholdTaxApplicableContainer");
	this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableContainer);
	this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField",
		new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
	this.settingWithholdTaxApplicableField.setRequired(false);
	this.settingWithholdTaxApplicableField
		.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
	this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableField);
	this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback",
		this.settingWithholdTaxApplicableField);
	this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableFeedback);

	this.settingTaxGroupBlock = new WebMarkupContainer("settingTaxGroupBlock");
	this.settingTaxGroupBlock.setOutputMarkupId(true);
	this.form.add(this.settingTaxGroupBlock);
	this.settingTaxGroupContainer = new WebMarkupContainer("settingTaxGroupContainer");
	this.settingTaxGroupBlock.add(this.settingTaxGroupContainer);
	this.settingTaxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
	this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", 0,
		new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
	this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
	this.settingTaxGroupField.setRequired(true);
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
	this.termDefaultDepositAmountField = new TextField<>("termDefaultDepositAmountField",
		new PropertyModel<>(this, "termDefaultDepositAmountValue"));
	this.termDefaultDepositAmountField.setRequired(true);
	this.termDefaultDepositAmountContainer.add(this.termDefaultDepositAmountField);
	this.termDefaultDepositAmountFeedback = new TextFeedbackPanel("termDefaultDepositAmountFeedback",
		this.termDefaultDepositAmountField);
	this.termDefaultDepositAmountContainer.add(this.termDefaultDepositAmountFeedback);

	this.termMinimumDepositAmountBlock = new WebMarkupContainer("termMinimumDepositAmountBlock");
	this.termMinimumDepositAmountBlock.setOutputMarkupId(true);
	this.form.add(this.termMinimumDepositAmountBlock);
	this.termMinimumDepositAmountContainer = new WebMarkupContainer("termMinimumDepositAmountContainer");
	this.termMinimumDepositAmountBlock.add(this.termMinimumDepositAmountContainer);
	this.termMinimumDepositAmountField = new TextField<>("termMinimumDepositAmountField",
		new PropertyModel<>(this, "termMinimumDepositAmountValue"));
	this.termMinimumDepositAmountField.setRequired(true);
	this.termMinimumDepositAmountContainer.add(this.termMinimumDepositAmountField);
	this.termMinimumDepositAmountFeedback = new TextFeedbackPanel("termMinimumDepositAmountFeedback",
		this.termMinimumDepositAmountField);
	this.termMinimumDepositAmountContainer.add(this.termMinimumDepositAmountFeedback);

	this.termMaximumDepositAmountBlock = new WebMarkupContainer("termMaximumDepositAmountBlock");
	this.termMaximumDepositAmountBlock.setOutputMarkupId(true);
	this.form.add(this.termMaximumDepositAmountBlock);
	this.termMaximumDepositAmountContainer = new WebMarkupContainer("termMaximumDepositAmountContainer");
	this.termMaximumDepositAmountBlock.add(this.termMaximumDepositAmountContainer);
	this.termMaximumDepositAmountField = new TextField<>("termMaximumDepositAmountField",
		new PropertyModel<>(this, "termMaximumDepositAmountValue"));
	this.termMaximumDepositAmountField.setRequired(true);
	this.termMaximumDepositAmountContainer.add(this.termMaximumDepositAmountField);
	this.termMaximumDepositAmountFeedback = new TextFeedbackPanel("termMaximumDepositAmountFeedback",
		this.termMaximumDepositAmountField);
	this.termMaximumDepositAmountContainer.add(this.termMaximumDepositAmountFeedback);

	this.termInterestCompoundingPeriodBlock = new WebMarkupContainer("termInterestCompoundingPeriodBlock");
	this.form.add(this.termInterestCompoundingPeriodBlock);
	this.termInterestCompoundingPeriodContainer = new WebMarkupContainer("termInterestCompoundingPeriodContainer");
	this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodContainer);
	this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
	this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", 0,
		new PropertyModel<>(this, "termInterestCompoundingPeriodValue"),
		this.termInterestCompoundingPeriodProvider);
	this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
	this.termInterestCompoundingPeriodField.setRequired(true);
	this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodField);
	this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback",
		this.termInterestCompoundingPeriodField);
	this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodFeedback);

	this.termInterestPostingPeriodBlock = new WebMarkupContainer("termInterestPostingPeriodBlock");
	this.form.add(this.termInterestPostingPeriodBlock);
	this.termInterestPostingPeriodContainer = new WebMarkupContainer("termInterestPostingPeriodContainer");
	this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodContainer);
	this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
	this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", 0,
		new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
	this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
	this.termInterestPostingPeriodField.setRequired(true);
	this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodField);
	this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback",
		this.termInterestPostingPeriodField);
	this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodFeedback);

	this.termInterestCalculatedUsingBlock = new WebMarkupContainer("termInterestCalculatedUsingBlock");
	this.form.add(this.termInterestCalculatedUsingBlock);
	this.termInterestCalculatedUsingContainer = new WebMarkupContainer("termInterestCalculatedUsingContainer");
	this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingContainer);
	this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
	this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", 0,
		new PropertyModel<>(this, "termInterestCalculatedUsingValue"),
		this.termInterestCalculatedUsingProvider);
	this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
	this.termInterestCalculatedUsingField.setRequired(true);
	this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingField);
	this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback",
		this.termInterestCalculatedUsingField);
	this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingFeedback);

	this.termDayInYearBlock = new WebMarkupContainer("termDayInYearBlock");
	this.form.add(this.termDayInYearBlock);
	this.termDayInYearContainer = new WebMarkupContainer("termDayInYearContainer");
	this.termDayInYearBlock.add(this.termDayInYearContainer);
	this.termDayInYearProvider = new DayInYearProvider();
	this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", 0,
		new PropertyModel<>(this, "termDayInYearValue"), this.termDayInYearProvider);
	this.termDayInYearField.add(new OnChangeAjaxBehavior());
	this.termDayInYearField.setRequired(true);
	this.termDayInYearContainer.add(this.termDayInYearField);
	this.termDayInYearFeedback = new TextFeedbackPanel("termDayInYearFeedback", this.termDayInYearField);
	this.termDayInYearContainer.add(this.termDayInYearFeedback);

    }

    protected void initCurrency() {

	this.currencyCodeBlock = new WebMarkupContainer("currencyCodeBlock");
	this.form.add(this.currencyCodeBlock);
	this.currencyCodeContainer = new WebMarkupContainer("currencyCodeContainer");
	this.currencyCodeBlock.add(this.currencyCodeContainer);
	this.currencyCodeProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0,
		new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
	this.currencyCodeField.add(new OnChangeAjaxBehavior());
	this.currencyCodeField.setRequired(true);
	this.currencyCodeContainer.add(this.currencyCodeField);
	this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
	this.currencyCodeContainer.add(this.currencyCodeFeedback);

	this.currencyDecimalPlaceBlock = new WebMarkupContainer("currencyDecimalPlaceBlock");
	this.form.add(this.currencyDecimalPlaceBlock);
	this.currencyDecimalPlaceContainer = new WebMarkupContainer("currencyDecimalPlaceContainer");
	this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceContainer);
	this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField",
		new PropertyModel<>(this, "currencyDecimalPlaceValue"));
	this.currencyDecimalPlaceField.setRequired(true);
	this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
	this.currencyDecimalPlaceField.add(RangeValidator.range((int) 0, (int) 6));
	this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceField);
	this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback",
		this.currencyDecimalPlaceField);
	this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceFeedback);

	this.currencyMultipleOfBlock = new WebMarkupContainer("currencyMultipleOfBlock");
	this.form.add(this.currencyMultipleOfBlock);
	this.currencyMultipleOfContainer = new WebMarkupContainer("currencyMultipleOfContainer");
	this.currencyMultipleOfBlock.add(this.currencyMultipleOfContainer);
	this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField",
		new PropertyModel<>(this, "currencyMultipleOfValue"));
	this.currencyMultipleOfField.setRequired(false);
	this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
	this.currencyMultipleOfField.add(RangeValidator.minimum((int) 1));
	this.currencyMultipleOfContainer.add(this.currencyMultipleOfField);
	this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback",
		this.currencyMultipleOfField);
	this.currencyMultipleOfContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initDetail() {
	this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
	this.detailProductNameBlock.setOutputMarkupId(true);
	this.form.add(this.detailProductNameBlock);
	this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
	this.detailProductNameBlock.add(this.detailProductNameContainer);
	this.detailProductNameField = new TextField<>("detailProductNameField",
		new PropertyModel<>(this, "detailProductNameValue"));
	this.detailProductNameField.setRequired(true);
	this.detailProductNameContainer.add(this.detailProductNameField);
	this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback",
		this.detailProductNameField);
	this.detailProductNameContainer.add(this.detailProductNameFeedback);

	this.detailShortNameBlock = new WebMarkupContainer("detailShortNameBlock");
	this.detailShortNameBlock.setOutputMarkupId(true);
	this.form.add(this.detailShortNameBlock);
	this.detailShortNameContainer = new WebMarkupContainer("detailShortNameContainer");
	this.detailShortNameBlock.add(this.detailShortNameContainer);
	this.detailShortNameField = new TextField<>("detailShortNameField",
		new PropertyModel<>(this, "detailShortNameValue"));
	this.detailShortNameField.setRequired(true);
	this.detailShortNameContainer.add(this.detailShortNameField);
	this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
	this.detailShortNameContainer.add(this.detailShortNameFeedback);

	this.detailDescriptionBlock = new WebMarkupContainer("detailDescriptionBlock");
	this.detailDescriptionBlock.setOutputMarkupId(true);
	this.form.add(this.detailDescriptionBlock);
	this.detailDescriptionContainer = new WebMarkupContainer("detailDescriptionContainer");
	this.detailDescriptionBlock.add(this.detailDescriptionContainer);
	this.detailDescriptionField = new TextField<>("detailDescriptionField",
		new PropertyModel<>(this, "detailDescriptionValue"));
	this.detailDescriptionField.setRequired(true);
	this.detailDescriptionContainer.add(this.detailDescriptionField);
	this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback",
		this.detailDescriptionField);
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
	    builder.withInterestCompoundingPeriodType(
		    InterestCompoundingPeriod.valueOf(this.termInterestCompoundingPeriodValue.getId()));
	}

	if (this.termInterestPostingPeriodValue != null) {
	    builder.withInterestPostingPeriodType(
		    InterestPostingPeriod.valueOf(this.termInterestPostingPeriodValue.getId()));
	}

	if (this.termInterestCalculatedUsingValue != null) {
	    builder.withInterestCalculationType(
		    InterestCalculatedUsing.valueOf(this.termInterestCalculatedUsingValue.getId()));
	}

	if (this.termDayInYearValue != null) {
	    builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.termDayInYearValue.getId()));
	}

	// Setting

	builder.withLockInPeriodFrequency(this.settingLockInPeriodValue);
	if (this.settingLockInTypeValue != null) {
	    builder.withLockinPeriodFrequencyType(LockInPeriod.valueOf(this.settingLockInTypeValue.getId()));
	}

	builder.withMinDepositTerm(this.settingMinimumDepositTermValue);

	if (this.settingMinimumDepositTypeValue != null) {
	    builder.withMinDepositTermTypeId(LockInPeriod.valueOf(this.settingMinimumDepositTypeValue.getId()));
	}

	builder.withInMultiplesOfDepositTerm(this.settingInMultiplesOfValue);

	if (this.settingInMultiplesTypeValue != null) {
	    builder.withInMultiplesOfDepositTermTypeId(LockInPeriod.valueOf(this.settingInMultiplesTypeValue.getId()));
	}

	builder.withMaxDepositTerm(this.settingMaximumDepositTermValue);

	if (this.settingMaximumDepositTypeValue != null) {
	    builder.withMaxDepositTermTypeId(LockInPeriod.valueOf(this.settingMaximumDepositTypeValue.getId()));
	}

	builder.withPreClosurePenalApplicable(
		this.settingForPreMatureClosureValue == null ? false : this.settingForPreMatureClosureValue);

	builder.withPreClosurePenalInterest(this.settingApplyPenalInterestValue);

	if (this.settingApplyPenalOnValue != null) {
	    builder.withPreClosurePenalInterestOnTypeId(ApplyPenalOn.valueOf(this.settingApplyPenalOnValue.getId()));
	}

	boolean holdTax = this.settingWithholdTaxApplicableValue == null ? false
		: this.settingWithholdTaxApplicableValue;
	builder.withHoldTax(holdTax);
	if (holdTax) {
	    if (this.settingTaxGroupValue != null) {
		builder.withTaxGroupId(this.settingTaxGroupValue.getId());
	    }
	}

	// Interest Rate Chart

	for (Map<String, Object> interestRateChart : this.interestRateChartValue) {
	    // LockInPeriod
	    Option periodTypeOption = (Option) interestRateChart.get("periodType");
	    LockInPeriod periodType = periodTypeOption == null ? null : LockInPeriod.valueOf(periodTypeOption.getId());
	    Date fromPeriod = (Date) interestRateChart.get("periodFrom");
	    Date toPeriod = (Date) interestRateChart.get("periodTo");
	    Double amountRangeFrom = (Double) interestRateChart.get("amountRangeFrom");
	    Double amountRangeTo = (Double) interestRateChart.get("amountRangeTo");
	    Double annualInterestRate = (Double) interestRateChart.get("interest");
	    String description = (String) interestRateChart.get("description");
	    List<Map<String, Object>> interestRate = (List<Map<String, Object>>) interestRateChart.get("interestRate");
	    List<JSONObject> incentives = null;
	    if (interestRate != null && !interestRate.isEmpty()) {
		incentives = Lists.newArrayList();
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
		    OperandType operandType = operandTypeOption == null ? null
			    : OperandType.valueOf(operandTypeOption.getId());
		    incentiveBuilder.withIncentiveType(operandType);

		    Double rateInterest = (Double) rate.get("interest");
		    incentiveBuilder.withAmount(rateInterest);

		    incentives.add(incentiveBuilder.build().getObject());
		}
	    }
	    builder.withChartSlab(periodType, fromPeriod, toPeriod, amountRangeFrom, amountRangeTo, annualInterestRate,
		    description, incentives);
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
	    if (this.cashInterestOnSavingValue != null) {
		builder.withInterestOnSavingsAccountId(this.cashInterestOnSavingValue.getId());
	    }
	    if (this.cashIncomeFromFeesValue != null) {
		builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeesValue.getId());
	    }
	    if (this.cashIncomeFromPenaltiesValue != null) {
		builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltiesValue.getId());
	    }
	}

	if (ACC_CASH.equals(accounting)) {
	    if (this.advancedAccountingRuleFundSourceValue != null
		    && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
		for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
		    builder.withPaymentChannelToFundSourceMappings((String) item.get("paymentId"),
			    (String) item.get("accountId"));
		}
	    }
	    if (this.advancedAccountingRuleFeeIncomeValue != null
		    && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
		for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
		    builder.withFeeToIncomeAccountMappings((String) item.get("chargeId"),
			    (String) item.get("accountId"));
		}
	    }
	    if (this.advancedAccountingRulePenaltyIncomeValue != null
		    && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
		for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
		    builder.withPenaltyToIncomeAccountMappings((String) item.get("chargeId"),
			    (String) item.get("accountId"));
		}
	    }
	}

	JsonNode node = null;
	try {
	    node = FixedHelper.create((Session) getSession(), builder.build());
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
