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
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.dto.loan.Amortization;
import com.angkorteam.fintech.dto.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.dto.loan.DayInMonth;
import com.angkorteam.fintech.dto.loan.DayInYear;
import com.angkorteam.fintech.dto.loan.Frequency;
import com.angkorteam.fintech.dto.loan.FrequencyDay;
import com.angkorteam.fintech.dto.loan.FrequencyType;
import com.angkorteam.fintech.dto.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.loan.InterestMethod;
import com.angkorteam.fintech.dto.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.dto.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.loan.RepaidType;
import com.angkorteam.fintech.dto.loan.RepaymentStrategy;
import com.angkorteam.fintech.dto.loan.WhenType;
import com.angkorteam.fintech.dto.request.AllowAttributeOverrideBuilder;
import com.angkorteam.fintech.dto.request.LoanBuilder;
import com.angkorteam.fintech.helper.LoanHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.LoanCyclePopup;
import com.angkorteam.fintech.popup.OverdueChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.provider.AdvancePaymentsAdjustmentTypeProvider;
import com.angkorteam.fintech.provider.AmortizationProvider;
import com.angkorteam.fintech.provider.ClosureInterestCalculationRuleProvider;
import com.angkorteam.fintech.provider.DayInMonthProvider;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.FrequencyDayProvider;
import com.angkorteam.fintech.provider.FrequencyProvider;
import com.angkorteam.fintech.provider.FrequencyTypeProvider;
import com.angkorteam.fintech.provider.InterestCalculationPeriodProvider;
import com.angkorteam.fintech.provider.InterestMethodProvider;
import com.angkorteam.fintech.provider.InterestRecalculationCompoundProvider;
import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
import com.angkorteam.fintech.provider.RepaidTypeProvider;
import com.angkorteam.fintech.provider.RepaymentStrategyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
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
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";
    public static final String ACC_PERIODIC = "Periodic";
    public static final String ACC_UPFRONT = "Upfront";

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    // Detail
    private String productNameValue;
    private TextField<String> productNameField;
    private TextFeedbackPanel productNameFeedback;

    private String shortNameValue;
    private TextField<String> shortNameField;
    private TextFeedbackPanel shortNameFeedback;

    private String descriptionValue;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private SingleChoiceProvider fundProvider;
    private Option fundValue;
    private Select2SingleChoice<Option> fundField;
    private TextFeedbackPanel fundFeedback;

    private Date startDateValue;
    private DateTextField startDateField;
    private TextFeedbackPanel startDateFeedback;

    private Date closeDateValue;
    private DateTextField closeDateField;
    private TextFeedbackPanel closeDateFeedback;

    private Boolean includeInCustomerLoanCounterValue;
    private CheckBox includeInCustomerLoanCounterField;
    private TextFeedbackPanel includeInCustomerLoanCounterFeedback;

    // Currency
    private SingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private Integer decimalPlaceValue;
    private TextField<Integer> decimalPlaceField;
    private TextFeedbackPanel decimalPlaceFeedback;

    private Integer currencyInMultipleOfValue;
    private TextField<Integer> currencyInMultipleOfField;
    private TextFeedbackPanel currencyInMultipleOfFeedback;

    private Integer installmentInMultipleOfValue;
    private TextField<Integer> installmentInMultipleOfField;
    private TextFeedbackPanel installmentInMultipleOfFeedback;

    // Terms

    // Row 1 : Terms vary based on loan cycle
    private Boolean termVaryBasedOnLoanCycleValue;
    private CheckBox termVaryBasedOnLoanCycleField;
    private TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    // Row 2 : Principal
    private Double principalMinimumValue;
    private TextField<Double> principalMinimumField;
    private TextFeedbackPanel principalMinimumFeedback;

    private Double principalDefaultValue;
    private TextField<Double> principalDefaultField;
    private TextFeedbackPanel principalDefaultFeedback;

    private Double principalMaximumValue;
    private TextField<Double> principalMaximumField;
    private TextFeedbackPanel principalMaximumFeedback;

    // Row 2 (Optional) : Principal by loan cycle
    private WebMarkupContainer principalByLoanCycleContainer;
    private List<Map<String, Object>> principalByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> principalByLoanCycleTable;
    private ListDataProvider principalByLoanCycleProvider;

    // Row 3 : Number of repayments
    private Double numberOfRepaymentMinimumValue;
    private TextField<Integer> numberOfRepaymentMinimumField;
    private TextFeedbackPanel numberOfRepaymentMinimumFeedback;

    private Double numberOfRepaymentDefaultValue;
    private TextField<Integer> numberOfRepaymentDefaultField;
    private TextFeedbackPanel numberOfRepaymentDefaultFeedback;

    private Double numberOfRepaymentMaximumValue;
    private TextField<Integer> numberOfRepaymentMaximumField;
    private TextFeedbackPanel numberOfRepaymentMaximumFeedback;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    private WebMarkupContainer numberOfRepaymentByLoanCycleContainer;
    private List<Map<String, Object>> numberOfRepaymentByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> numberOfRepaymentByLoanCycleTable;
    private ListDataProvider numberOfRepaymentByLoanCycleProvider;

    // Row 4 : Is Linked to Floating Interest Rates?
    private Boolean linkedToFloatingInterestRatesValue;
    private CheckBox linkedToFloatingInterestRatesField;
    private TextFeedbackPanel linkedToFloatingInterestRatesFeedback;

    private WebMarkupContainer nominalInterestRateContainer;

    // Row 5 : Nominal interest rate
    private Double nominalInterestRateMinimumValue;
    private TextField<Double> nominalInterestRateMinimumField;
    private TextFeedbackPanel nominalInterestRateMinimumFeedback;

    private Double nominalInterestRateDefaultValue;
    private TextField<Double> nominalInterestRateDefaultField;
    private TextFeedbackPanel nominalInterestRateDefaultFeedback;

    private Double nominalInterestRateMaximumValue;
    private TextField<Double> nominalInterestRateMaximumField;
    private TextFeedbackPanel nominalInterestRateMaximumFeedback;

    private NominalInterestRateTypeProvider nominalInterestRateTypeProvider;
    private Option nominalInterestRateTypeValue;
    private Select2SingleChoice<Option> nominalInterestRateTypeField;
    private TextFeedbackPanel nominalInterestRateTypeFeedback;

    private WebMarkupContainer floatInterestRateContainer;

    // Row 3 (Optional) : Nominal interest rate by loan cycle
    private WebMarkupContainer nominalInterestRateByLoanCycleContainer;

    // Row 6
    private List<Map<String, Object>> nominalInterestRateByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> nominalInterestRateByLoanCycleTable;
    private ListDataProvider nominalInterestRateByLoanCycleProvider;

    private Integer repaidEveryValue;
    private TextField<Integer> repaidEveryField;
    private TextFeedbackPanel repaidEveryFeedback;

    private RepaidTypeProvider repaidTypeProvider;
    private Option repaidTypeValue;
    private Select2SingleChoice<Option> repaidTypeField;
    private TextFeedbackPanel repaidTypeFeedback;

    private Double minimumDayBetweenDisbursalAndFirstRepaymentDateValue;
    private TextField<Double> minimumDayBetweenDisbursalAndFirstRepaymentDateField;
    private TextFeedbackPanel minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback;

    private SingleChoiceProvider floatingInterestRateProvider;
    private Option floatingInterestRateValue;
    private Select2SingleChoice<Option> floatingInterestRateField;
    private TextFeedbackPanel floatingInterestRateFeedback;

    private Double floatingInterestMinimumValue;
    private TextField<Double> floatingInterestMinimumField;
    private TextFeedbackPanel floatingInterestMinimumFeedback;

    private Double floatingInterestDefaultValue;
    private TextField<Double> floatingInterestDefaultField;
    private TextFeedbackPanel floatingInterestDefaultFeedback;

    private Double floatingInterestMaximumValue;
    private TextField<Double> floatingInterestMaximumField;
    private TextFeedbackPanel floatingInterestMaximumFeedback;

    private Double floatingInterestDifferentialValue;
    private TextField<Double> floatingInterestDifferentialField;
    private TextFeedbackPanel floatingInterestDifferentialFeedback;

    private Boolean floatingInterestAllowedValue;
    private CheckBox floatingInterestAllowedField;
    private TextFeedbackPanel floatingInterestAllowedFeedback;

    private Option itemWhenValue;
    private Integer itemLoanCycleValue;
    private Double itemMinimumValue;
    private Double itemDefaultValue;
    private Double itemMaximumValue;
    private Option itemChargeValue;
    private Option itemOverdueChargeValue;
    private Option itemPaymentValue;
    private Option itemAccountValue;

    private List<Map<String, Object>> loanCycleValue;
    private ModalWindow loanCyclePopup;

    private ModalWindow chargePopup;

    private ModalWindow overdueChargePopup;

    private ModalWindow paymentPopup;

    private ModalWindow feeChargePopup;

    private ModalWindow penaltyChargePopup;

    // Settings
    private AmortizationProvider amortizationProvider;
    private Option amortizationValue;
    private Select2SingleChoice<Option> amortizationField;
    private TextFeedbackPanel amortizationFeedback;

    private InterestMethodProvider interestMethodProvider;
    private Option interestMethodValue;
    private Select2SingleChoice<Option> interestMethodField;
    private TextFeedbackPanel interestMethodFeedback;

    private InterestCalculationPeriodProvider interestCalculationPeriodProvider;
    private Option interestCalculationPeriodValue;
    private Select2SingleChoice<Option> interestCalculationPeriodField;
    private TextFeedbackPanel interestCalculationPeriodFeedback;

    private WebMarkupContainer calculateInterestForExactDaysInPartialPeriodContainer;

    private Boolean calculateInterestForExactDaysInPartialPeriodValue;
    private CheckBox calculateInterestForExactDaysInPartialPeriodField;
    private TextFeedbackPanel calculateInterestForExactDaysInPartialPeriodFeedback;

    private RepaymentStrategyProvider repaymentStrategyProvider;
    private Option repaymentStrategyValue;
    private Select2SingleChoice<Option> repaymentStrategyField;
    private TextFeedbackPanel repaymentStrategyFeedback;

    private Double moratoriumPrincipalValue;
    private TextField<Double> moratoriumPrincipalField;
    private TextFeedbackPanel moratoriumPrincipalFeedback;

    private Double moratoriumInterestValue;
    private TextField<Double> moratoriumInterestField;
    private TextFeedbackPanel moratoriumInterestFeedback;

    private Double interestFreePeriodValue;
    private TextField<Double> interestFreePeriodField;
    private TextFeedbackPanel interestFreePeriodFeedback;

    private Double arrearsToleranceValue;
    private TextField<Double> arrearsToleranceField;
    private TextFeedbackPanel arrearsToleranceFeedback;

    private DayInYearProvider dayInYearProvider;
    private Option dayInYearValue;
    private Select2SingleChoice<Option> dayInYearField;
    private TextFeedbackPanel dayInYearFeedback;

    private DayInMonthProvider dayInMonthProvider;
    private Option dayInMonthValue;
    private Select2SingleChoice<Option> dayInMonthField;
    private TextFeedbackPanel dayInMonthFeedback;

    private Boolean allowFixingOfTheInstallmentAmountValue;
    private CheckBox allowFixingOfTheInstallmentAmountField;
    private TextFeedbackPanel allowFixingOfTheInstallmentAmountFeedback;

    private Double numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
    private TextField<Double> numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField;
    private TextFeedbackPanel numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback;

    private Double maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
    private TextField<Double> maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField;
    private TextFeedbackPanel maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback;

    private Boolean accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
    private CheckBox accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField;
    private TextFeedbackPanel accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback;

    private Double principalThresholdForLastInstalmentValue;
    private TextField<Double> principalThresholdForLastInstalmentField;
    private TextFeedbackPanel principalThresholdForLastInstalmentFeedback;

    private Boolean variableInstallmentsAllowedValue;
    private CheckBox variableInstallmentsAllowedField;
    private TextFeedbackPanel variableInstallmentsAllowedFeedback;

    private WebMarkupContainer variableInstallmentsAllowedContainer;

    private Double variableInstallmentsMinimumValue;
    private TextField<Double> variableInstallmentsMinimumField;
    private TextFeedbackPanel variableInstallmentsMinimumFeedback;

    private Double variableInstallmentsMaximumValue;
    private TextField<Double> variableInstallmentsMaximumField;
    private TextFeedbackPanel variableInstallmentsMaximumFeedback;

    private Boolean allowedToBeUsedForProvidingTopupLoansValue;
    private CheckBox allowedToBeUsedForProvidingTopupLoansField;
    private TextFeedbackPanel allowedToBeUsedForProvidingTopupLoansFeedback;

    // Interest Recalculation

    private Boolean recalculateInterestValue;
    private CheckBox recalculateInterestField;
    private TextFeedbackPanel recalculateInterestFeedback;

    private WebMarkupContainer recalculateInterestContainer;

    private ClosureInterestCalculationRuleProvider preClosureInterestCalculationRuleProvider;
    private Option preClosureInterestCalculationRuleValue;
    private Select2SingleChoice<Option> preClosureInterestCalculationRuleField;
    private TextFeedbackPanel preClosureInterestCalculationRuleFeedback;

    private AdvancePaymentsAdjustmentTypeProvider advancePaymentsAdjustmentTypeProvider;
    private Option advancePaymentsAdjustmentTypeValue;
    private Select2SingleChoice<Option> advancePaymentsAdjustmentTypeField;
    private TextFeedbackPanel advancePaymentsAdjustmentTypeFeedback;

    private InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
    private Option interestRecalculationCompoundingOnValue;
    private Select2SingleChoice<Option> interestRecalculationCompoundingOnField;
    private TextFeedbackPanel interestRecalculationCompoundingOnFeedback;

    private WebMarkupContainer compoundingContainer;

    private FrequencyProvider compoundingProvider;
    private Option compoundingValue;
    private Select2SingleChoice<Option> compoundingField;
    private TextFeedbackPanel compoundingFeedback;

    private WebMarkupContainer compoundingTypeContainer;
    private FrequencyTypeProvider compoundingTypeProvider;
    private Option compoundingTypeValue;
    private Select2SingleChoice<Option> compoundingTypeField;
    private TextFeedbackPanel compoundingTypeFeedback;

    private WebMarkupContainer compoundingDayContainer;
    private FrequencyDayProvider compoundingDayProvider;
    private Option compoundingDayValue;
    private Select2SingleChoice<Option> compoundingDayField;
    private TextFeedbackPanel compoundingDayFeedback;

    private WebMarkupContainer compoundingIntervalContainer;
    private Double compoundingIntervalValue;
    private TextField<Double> compoundingIntervalField;
    private TextFeedbackPanel compoundingIntervalFeedback;

    private FrequencyProvider recalculateProvider;
    private Option recalculateValue;
    private Select2SingleChoice<Option> recalculateField;
    private TextFeedbackPanel recalculateFeedback;

    private WebMarkupContainer recalculateTypeContainer;
    private FrequencyTypeProvider recalculateTypeProvider;
    private Option recalculateTypeValue;
    private Select2SingleChoice<Option> recalculateTypeField;
    private TextFeedbackPanel recalculateTypeFeedback;

    private WebMarkupContainer recalculateDayContainer;
    private FrequencyDayProvider recalculateDayProvider;
    private Option recalculateDayValue;
    private Select2SingleChoice<Option> recalculateDayField;
    private TextFeedbackPanel recalculateDayFeedback;

    private WebMarkupContainer recalculateIntervalContainer;
    private Double recalculateIntervalValue;
    private TextField<Double> recalculateIntervalField;
    private TextFeedbackPanel recalculateIntervalFeedback;

    private Boolean arrearsRecognizationBasedOnOriginalScheduleValue;
    private CheckBox arrearsRecognizationBasedOnOriginalScheduleField;
    private TextFeedbackPanel arrearsRecognizationBasedOnOriginalScheduleFeedback;

    // Guarantee Requirements

    private Boolean placeGuaranteeFundsOnHoldValue;
    private CheckBox placeGuaranteeFundsOnHoldField;
    private TextFeedbackPanel placeGuaranteeFundsOnHoldFeedback;

    private WebMarkupContainer placeGuaranteeFundsOnHoldContainer;

    private Double mandatoryGuaranteeValue;
    private TextField<Double> mandatoryGuaranteeField;
    private TextFeedbackPanel mandatoryGuaranteeFeedback;

    private Double minimumGuaranteeValue;
    private TextField<Double> minimumGuaranteeField;
    private TextFeedbackPanel minimumGuaranteeFeedback;

    private Double minimumGuaranteeFromGuarantorValue;
    private TextField<Double> minimumGuaranteeFromGuarantorField;
    private TextFeedbackPanel minimumGuaranteeFromGuarantorFeedback;

    // Loan Tranche Details

    private Boolean enableMultipleDisbursalValue;
    private CheckBox enableMultipleDisbursalField;
    private TextFeedbackPanel enableMultipleDisbursalFeedback;

    private WebMarkupContainer enableMultipleDisbursalContainer;

    private Double maximumTrancheCountValue;
    private TextField<Double> maximumTrancheCountField;
    private TextFeedbackPanel maximumTrancheCountFeedback;

    private Double maximumAllowedOutstandingBalanceValue;
    private TextField<Double> maximumAllowedOutstandingBalanceField;
    private TextFeedbackPanel maximumAllowedOutstandingBalanceFeedback;

    // Configurable Terms and Settings

    private Boolean allowOverridingSelectTermsAndSettingsInLoanAccountValue;
    private CheckBox allowOverridingSelectTermsAndSettingsInLoanAccountField;
    private TextFeedbackPanel allowOverridingSelectTermsAndSettingsInLoanAccountFeedback;

    private WebMarkupContainer allowOverridingSelectTermsAndSettingsInLoanAccountContainer;

    private Boolean configurableAmortizationValue;
    private CheckBox configurableAmortizationField;
    private TextFeedbackPanel configurableAmortizationFeedback;

    private Boolean configurableInterestMethodValue;
    private CheckBox configurableInterestMethodField;
    private TextFeedbackPanel configurableInterestMethodFeedback;

    private Boolean configurableRepaymentStrategyValue;
    private CheckBox configurableRepaymentStrategyField;
    private TextFeedbackPanel configurableRepaymentStrategyFeedback;

    private Boolean configurableInterestCalculationPeriodValue;
    private CheckBox configurableInterestCalculationPeriodField;
    private TextFeedbackPanel configurableInterestCalculationPeriodFeedback;

    private Boolean configurableArrearsToleranceValue;
    private CheckBox configurableArrearsToleranceField;
    private TextFeedbackPanel configurableArrearsToleranceFeedback;

    private Boolean configurableRepaidEveryValue;
    private CheckBox configurableRepaidEveryField;
    private TextFeedbackPanel configurableRepaidEveryFeedback;

    private Boolean configurableMoratoriumValue;
    private CheckBox configurableMoratoriumField;
    private TextFeedbackPanel configurableMoratoriumFeedback;

    private Boolean configurableOverdueBeforeMovingValue;
    private CheckBox configurableOverdueBeforeMovingField;
    private TextFeedbackPanel configurableOverdueBeforeMovingFeedback;

    // Accounting

    private String accountingValue = ACC_NONE;
    private RadioGroup<String> accountingField;

    private WebMarkupContainer cashContainer;

    private SingleChoiceProvider cashFundSourceProvider;
    private Option cashFundSourceValue;
    private Select2SingleChoice<Option> cashFundSourceField;
    private TextFeedbackPanel cashFundSourceFeedback;

    private SingleChoiceProvider cashLoanPortfolioProvider;
    private Option cashLoanPortfolioValue;
    private Select2SingleChoice<Option> cashLoanPortfolioField;
    private TextFeedbackPanel cashLoanPortfolioFeedback;

    private SingleChoiceProvider cashTransferInSuspenseProvider;
    private Option cashTransferInSuspenseValue;
    private Select2SingleChoice<Option> cashTransferInSuspenseField;
    private TextFeedbackPanel cashTransferInSuspenseFeedback;

    private SingleChoiceProvider cashIncomeFromInterestProvider;
    private Option cashIncomeFromInterestValue;
    private Select2SingleChoice<Option> cashIncomeFromInterestField;
    private TextFeedbackPanel cashIncomeFromInterestFeedback;

    private SingleChoiceProvider cashIncomeFromFeeProvider;
    private Option cashIncomeFromFeeValue;
    private Select2SingleChoice<Option> cashIncomeFromFeeField;
    private TextFeedbackPanel cashIncomeFromFeeFeedback;

    private SingleChoiceProvider cashIncomeFromPenaltiesProvider;
    private Option cashIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> cashIncomeFromPenaltiesField;
    private TextFeedbackPanel cashIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider cashIncomeFromRecoveryRepaymentProvider;
    private Option cashIncomeFromRecoveryRepaymentValue;
    private Select2SingleChoice<Option> cashIncomeFromRecoveryRepaymentField;
    private TextFeedbackPanel cashIncomeFromRecoveryRepaymentFeedback;

    private SingleChoiceProvider cashLossesWrittenOffProvider;
    private Option cashLossesWrittenOffValue;
    private Select2SingleChoice<Option> cashLossesWrittenOffField;
    private TextFeedbackPanel cashLossesWrittenOffFeedback;

    private SingleChoiceProvider cashOverPaymentLiabilityProvider;
    private Option cashOverPaymentLiabilityValue;
    private Select2SingleChoice<Option> cashOverPaymentLiabilityField;
    private TextFeedbackPanel cashOverPaymentLiabilityFeedback;

    private WebMarkupContainer periodicContainer;

    private SingleChoiceProvider periodicFundSourceProvider;
    private Option periodicFundSourceValue;
    private Select2SingleChoice<Option> periodicFundSourceField;
    private TextFeedbackPanel periodicFundSourceFeedback;

    private SingleChoiceProvider periodicLoanPortfolioProvider;
    private Option periodicLoanPortfolioValue;
    private Select2SingleChoice<Option> periodicLoanPortfolioField;
    private TextFeedbackPanel periodicLoanPortfolioFeedback;

    private SingleChoiceProvider periodicInterestReceivableProvider;
    private Option periodicInterestReceivableValue;
    private Select2SingleChoice<Option> periodicInterestReceivableField;
    private TextFeedbackPanel periodicInterestReceivableFeedback;

    private SingleChoiceProvider periodicFeesReceivableProvider;
    private Option periodicFeesReceivableValue;
    private Select2SingleChoice<Option> periodicFeesReceivableField;
    private TextFeedbackPanel periodicFeesReceivableFeedback;

    private SingleChoiceProvider periodicPenaltiesReceivableProvider;
    private Option periodicPenaltiesReceivableValue;
    private Select2SingleChoice<Option> periodicPenaltiesReceivableField;
    private TextFeedbackPanel periodicPenaltiesReceivableFeedback;

    private SingleChoiceProvider periodicTransferInSuspenseProvider;
    private Option periodicTransferInSuspenseValue;
    private Select2SingleChoice<Option> periodicTransferInSuspenseField;
    private TextFeedbackPanel periodicTransferInSuspenseFeedback;

    private SingleChoiceProvider periodicIncomeFromInterestProvider;
    private Option periodicIncomeFromInterestValue;
    private Select2SingleChoice<Option> periodicIncomeFromInterestField;
    private TextFeedbackPanel periodicIncomeFromInterestFeedback;

    private SingleChoiceProvider periodicIncomeFromFeeProvider;
    private Option periodicIncomeFromFeeValue;
    private Select2SingleChoice<Option> periodicIncomeFromFeeField;
    private TextFeedbackPanel periodicIncomeFromFeeFeedback;

    private SingleChoiceProvider periodicIncomeFromPenaltiesProvider;
    private Option periodicIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> periodicIncomeFromPenaltiesField;
    private TextFeedbackPanel periodicIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider periodicIncomeFromRecoveryRepaymentProvider;
    private Option periodicIncomeFromRecoveryRepaymentValue;
    private Select2SingleChoice<Option> periodicIncomeFromRecoveryRepaymentField;
    private TextFeedbackPanel periodicIncomeFromRecoveryRepaymentFeedback;

    private SingleChoiceProvider periodicLossesWrittenOffProvider;
    private Option periodicLossesWrittenOffValue;
    private Select2SingleChoice<Option> periodicLossesWrittenOffField;
    private TextFeedbackPanel periodicLossesWrittenOffFeedback;

    private SingleChoiceProvider periodicOverPaymentLiabilityProvider;
    private Option periodicOverPaymentLiabilityValue;
    private Select2SingleChoice<Option> periodicOverPaymentLiabilityField;
    private TextFeedbackPanel periodicOverPaymentLiabilityFeedback;

    private WebMarkupContainer upfrontContainer;

    private SingleChoiceProvider upfrontFundSourceProvider;
    private Option upfrontFundSourceValue;
    private Select2SingleChoice<Option> upfrontFundSourceField;
    private TextFeedbackPanel upfrontFundSourceFeedback;

    private SingleChoiceProvider upfrontLoanPortfolioProvider;
    private Option upfrontLoanPortfolioValue;
    private Select2SingleChoice<Option> upfrontLoanPortfolioField;
    private TextFeedbackPanel upfrontLoanPortfolioFeedback;

    private SingleChoiceProvider upfrontInterestReceivableProvider;
    private Option upfrontInterestReceivableValue;
    private Select2SingleChoice<Option> upfrontInterestReceivableField;
    private TextFeedbackPanel upfrontInterestReceivableFeedback;

    private SingleChoiceProvider upfrontFeesReceivableProvider;
    private Option upfrontFeesReceivableValue;
    private Select2SingleChoice<Option> upfrontFeesReceivableField;
    private TextFeedbackPanel upfrontFeesReceivableFeedback;

    private SingleChoiceProvider upfrontPenaltiesReceivableProvider;
    private Option upfrontPenaltiesReceivableValue;
    private Select2SingleChoice<Option> upfrontPenaltiesReceivableField;
    private TextFeedbackPanel upfrontPenaltiesReceivableFeedback;

    private SingleChoiceProvider upfrontTransferInSuspenseProvider;
    private Option upfrontTransferInSuspenseValue;
    private Select2SingleChoice<Option> upfrontTransferInSuspenseField;
    private TextFeedbackPanel upfrontTransferInSuspenseFeedback;

    private SingleChoiceProvider upfrontIncomeFromInterestProvider;
    private Option upfrontIncomeFromInterestValue;
    private Select2SingleChoice<Option> upfrontIncomeFromInterestField;
    private TextFeedbackPanel upfrontIncomeFromInterestFeedback;

    private SingleChoiceProvider upfrontIncomeFromFeeProvider;
    private Option upfrontIncomeFromFeeValue;
    private Select2SingleChoice<Option> upfrontIncomeFromFeeField;
    private TextFeedbackPanel upfrontIncomeFromFeeFeedback;

    private SingleChoiceProvider upfrontIncomeFromPenaltiesProvider;
    private Option upfrontIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> upfrontIncomeFromPenaltiesField;
    private TextFeedbackPanel upfrontIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider upfrontIncomeFromRecoveryRepaymentProvider;
    private Option upfrontIncomeFromRecoveryRepaymentValue;
    private Select2SingleChoice<Option> upfrontIncomeFromRecoveryRepaymentField;
    private TextFeedbackPanel upfrontIncomeFromRecoveryRepaymentFeedback;

    private SingleChoiceProvider upfrontLossesWrittenOffProvider;
    private Option upfrontLossesWrittenOffValue;
    private Select2SingleChoice<Option> upfrontLossesWrittenOffField;
    private TextFeedbackPanel upfrontLossesWrittenOffFeedback;

    private SingleChoiceProvider upfrontOverPaymentLiabilityProvider;
    private Option upfrontOverPaymentLiabilityValue;
    private Select2SingleChoice<Option> upfrontOverPaymentLiabilityField;
    private TextFeedbackPanel upfrontOverPaymentLiabilityFeedback;

    // Advanced Accounting Rule

    private WebMarkupContainer advancedAccountingRuleContainer;

    private WebMarkupContainer fundSourceContainer;
    private List<Map<String, Object>> fundSourceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> fundSourceTable;
    private ListDataProvider fundSourceProvider;

    private WebMarkupContainer feeIncomeContainer;
    private List<Map<String, Object>> feeIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> feeIncomeTable;
    private ListDataProvider feeIncomeProvider;

    private WebMarkupContainer penaltyIncomeContainer;
    private List<Map<String, Object>> penaltyIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> penaltyIncomeTable;
    private ListDataProvider penaltyIncomeProvider;

    // Charges

    private List<Map<String, Object>> chargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargeTable;
    private ListDataProvider chargeProvider;

    // Overdue Charges

    private List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> overdueChargeTable;
    private ListDataProvider overdueChargeProvider;

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
            breadcrumb.setLabel("Loan Product");
            breadcrumb.setPage(LoanBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Loan Product Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Boolean officeSpecificProductsEnabled = jdbcTemplate.queryForObject(
                "select value from c_configuration where name = 'office-specific-products-enabled'", Boolean.class);
        officeSpecificProductsEnabled = officeSpecificProductsEnabled == null ? false : officeSpecificProductsEnabled;

        this.loanCyclePopup = new ModalWindow("loanCyclePopup");
        add(this.loanCyclePopup);
        this.loanCyclePopup
                .setContent(new LoanCyclePopup(this.loanCyclePopup.getContentId(), this.loanCyclePopup, this));
        this.loanCyclePopup.setOnClose(this::loanCyclePopupOnClose);

        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this));
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        this.overdueChargePopup = new ModalWindow("overdueChargePopup");
        add(this.overdueChargePopup);
        this.overdueChargePopup.setContent(
                new OverdueChargePopup(this.overdueChargePopup.getContentId(), this.overdueChargePopup, this));
        this.overdueChargePopup.setOnClose(this::overdueChargePopupOnClose);

        this.paymentPopup = new ModalWindow("paymentPopup");
        add(this.paymentPopup);
        this.paymentPopup.setContent(new PaymentTypePopup(this.paymentPopup.getContentId(), this.paymentPopup, this));
        this.paymentPopup.setOnClose(this::paymentPopupOnClose);

        this.feeChargePopup = new ModalWindow("feeChargePopup");
        add(this.feeChargePopup);
        this.feeChargePopup
                .setContent(new FeeChargePopup(this.feeChargePopup.getContentId(), this.feeChargePopup, this));
        this.feeChargePopup.setOnClose(this::feeChargePopupOnClose);

        this.penaltyChargePopup = new ModalWindow("penaltyChargePopup");
        add(this.penaltyChargePopup);
        this.penaltyChargePopup.setContent(
                new PenaltyChargePopup(this.penaltyChargePopup.getContentId(), this.penaltyChargePopup, this));
        this.penaltyChargePopup.setOnClose(this::penaltyChargePopupOnClose);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanCreatePage.class);
        this.form.add(this.closeLink);

        initDetail();

        initCurrency();

        initTerms();

        initSetting();

        initInterestRecalculation();

        initGuaranteeRequirements();

        initLoanTrancheDetails();

        initConfigurableTermsAndSettings();

        initCharge();

        initOverdueCharge();

        initAccounting();

        initDefault();
    }

    protected void initOverdueCharge() {

        List<IColumn<Map<String, Object>, String>> overdueChargeColumn = Lists.newArrayList();
        overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeNameColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeTypeColumn));
        overdueChargeColumn
                .add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeAmountColumn));
        overdueChargeColumn
                .add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeCollectColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::overdueChargeDateColumn));
        overdueChargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::overdueChargeActionItem,
                this::overdueChargesActionClick));
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", overdueChargeColumn, this.overdueChargeProvider,
                20);
        this.form.add(this.overdueChargeTable);
        this.overdueChargeTable
                .addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));

        AjaxLink<Void> overdueChargeAddLink = new AjaxLink<>("overdueChargeAddLink");
        overdueChargeAddLink.setOnClick(this::overdueChargeAddLinkClick);
        this.form.add(overdueChargeAddLink);
    }

    protected boolean overdueChargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemOverdueChargeValue = null;
        this.overdueChargePopup.show(target);
        return false;
    }

    protected ItemPanel overdueChargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel overdueChargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected ItemPanel overdueChargeAmountColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value.doubleValue())));
        }
    }

    protected ItemPanel overdueChargeCollectColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected ItemPanel overdueChargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected void overdueChargesActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.overdueChargeValue.size(); i++) {
            Map<String, Object> column = this.overdueChargeValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.overdueChargeValue.remove(index);
        }
        ajaxRequestTarget.add(this.overdueChargeTable);
    }

    protected List<ActionItem> overdueChargeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initCharge() {
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

        AjaxLink<Void> chargeAddLink = new AjaxLink<>("chargeAddLink");
        chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(chargeAddLink);
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

    protected void initAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(ACC_NONE)));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(ACC_CASH)));
        this.accountingField.add(new Radio<>("accountingPeriodic", new Model<>(ACC_PERIODIC)));
        this.accountingField.add(new Radio<>("accountingUpfront", new Model<>(ACC_UPFRONT)));
        this.form.add(this.accountingField);

        initAccountingCash();

        initAccountingPeriodic();

        initAccountingUpFront();

        initAdvancedAccountingRule();

    }

    protected void initAccountingUpFront() {
        this.upfrontContainer = new WebMarkupContainer("upfrontContainer");
        this.form.add(this.upfrontContainer);

        this.upfrontFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontFundSourceProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontFundSourceProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontFundSourceField = new Select2SingleChoice<>("upfrontFundSourceField",
                new PropertyModel<>(this, "upfrontFundSourceValue"), this.upfrontFundSourceProvider);
        this.upfrontFundSourceField.setRequired(false);
        this.upfrontFundSourceField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontFundSourceField);
        this.upfrontFundSourceFeedback = new TextFeedbackPanel("upfrontFundSourceFeedback",
                this.upfrontFundSourceField);
        this.upfrontContainer.add(this.upfrontFundSourceFeedback);

        this.upfrontLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontLoanPortfolioProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontLoanPortfolioField = new Select2SingleChoice<>("upfrontLoanPortfolioField",
                new PropertyModel<>(this, "upfrontLoanPortfolioValue"), this.upfrontLoanPortfolioProvider);
        this.upfrontLoanPortfolioField.setRequired(false);
        this.upfrontLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontLoanPortfolioField);
        this.upfrontLoanPortfolioFeedback = new TextFeedbackPanel("upfrontLoanPortfolioFeedback",
                this.upfrontLoanPortfolioField);
        this.upfrontContainer.add(this.upfrontLoanPortfolioFeedback);

        this.upfrontInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontInterestReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontInterestReceivableField = new Select2SingleChoice<>("upfrontInterestReceivableField",
                new PropertyModel<>(this, "upfrontInterestReceivableValue"), this.upfrontInterestReceivableProvider);
        this.upfrontInterestReceivableField.setRequired(false);
        this.upfrontInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontInterestReceivableField);
        this.upfrontInterestReceivableFeedback = new TextFeedbackPanel("upfrontInterestReceivableFeedback",
                this.upfrontInterestReceivableField);
        this.upfrontContainer.add(this.upfrontInterestReceivableFeedback);

        this.upfrontFeesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontFeesReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontFeesReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontFeesReceivableField = new Select2SingleChoice<>("upfrontFeesReceivableField",
                new PropertyModel<>(this, "upfrontFeesReceivableValue"), this.upfrontFeesReceivableProvider);
        this.upfrontFeesReceivableField.setRequired(false);
        this.upfrontFeesReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontFeesReceivableField);
        this.upfrontFeesReceivableFeedback = new TextFeedbackPanel("upfrontFeesReceivableFeedback",
                this.upfrontFeesReceivableField);
        this.upfrontContainer.add(this.upfrontFeesReceivableFeedback);

        this.upfrontPenaltiesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontPenaltiesReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontPenaltiesReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontPenaltiesReceivableField = new Select2SingleChoice<>("upfrontPenaltiesReceivableField",
                new PropertyModel<>(this, "upfrontPenaltiesReceivableValue"), this.upfrontPenaltiesReceivableProvider);
        this.upfrontPenaltiesReceivableField.setRequired(false);
        this.upfrontPenaltiesReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontPenaltiesReceivableField);
        this.upfrontPenaltiesReceivableFeedback = new TextFeedbackPanel("upfrontPenaltiesReceivableFeedback",
                this.upfrontPenaltiesReceivableField);
        this.upfrontContainer.add(this.upfrontPenaltiesReceivableFeedback);

        this.upfrontTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontTransferInSuspenseField = new Select2SingleChoice<>("upfrontTransferInSuspenseField",
                new PropertyModel<>(this, "upfrontTransferInSuspenseValue"), this.upfrontTransferInSuspenseProvider);
        this.upfrontTransferInSuspenseField.setRequired(false);
        this.upfrontTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontTransferInSuspenseField);
        this.upfrontTransferInSuspenseFeedback = new TextFeedbackPanel("upfrontTransferInSuspenseFeedback",
                this.upfrontTransferInSuspenseField);
        this.upfrontContainer.add(this.upfrontTransferInSuspenseFeedback);

        this.upfrontIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromInterestField = new Select2SingleChoice<>("upfrontIncomeFromInterestField",
                new PropertyModel<>(this, "upfrontIncomeFromInterestValue"), this.upfrontIncomeFromInterestProvider);
        this.upfrontIncomeFromInterestField.setRequired(false);
        this.upfrontIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromInterestField);
        this.upfrontIncomeFromInterestFeedback = new TextFeedbackPanel("upfrontIncomeFromInterestFeedback",
                this.upfrontIncomeFromInterestField);
        this.upfrontContainer.add(this.upfrontIncomeFromInterestFeedback);

        this.upfrontIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromFeeField = new Select2SingleChoice<>("upfrontIncomeFromFeeField",
                new PropertyModel<>(this, "upfrontIncomeFromFeeValue"), this.upfrontIncomeFromFeeProvider);
        this.upfrontIncomeFromFeeField.setRequired(false);
        this.upfrontIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromFeeField);
        this.upfrontIncomeFromFeeFeedback = new TextFeedbackPanel("upfrontIncomeFromFeeFeedback",
                this.upfrontIncomeFromFeeField);
        this.upfrontContainer.add(this.upfrontIncomeFromFeeFeedback);

        this.upfrontIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromPenaltiesField = new Select2SingleChoice<>("upfrontIncomeFromPenaltiesField",
                new PropertyModel<>(this, "upfrontIncomeFromPenaltiesValue"), this.upfrontIncomeFromPenaltiesProvider);
        this.upfrontIncomeFromPenaltiesField.setRequired(false);
        this.upfrontIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesField);
        this.upfrontIncomeFromPenaltiesFeedback = new TextFeedbackPanel("upfrontIncomeFromPenaltiesFeedback",
                this.upfrontIncomeFromPenaltiesField);
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesFeedback);

        this.upfrontIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>(
                "upfrontIncomeFromRecoveryRepaymentField",
                new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentValue"),
                this.upfrontIncomeFromRecoveryRepaymentProvider);
        this.upfrontIncomeFromRecoveryRepaymentField.setRequired(false);
        this.upfrontIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel(
                "upfrontIncomeFromRecoveryRepaymentFeedback", this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentFeedback);

        this.upfrontLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.upfrontLossesWrittenOffField = new Select2SingleChoice<>("upfrontLossesWrittenOffField",
                new PropertyModel<>(this, "upfrontLossesWrittenOffValue"), this.upfrontLossesWrittenOffProvider);
        this.upfrontLossesWrittenOffField.setRequired(false);
        this.upfrontLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontLossesWrittenOffField);
        this.upfrontLossesWrittenOffFeedback = new TextFeedbackPanel("upfrontLossesWrittenOffFeedback",
                this.upfrontLossesWrittenOffField);
        this.upfrontContainer.add(this.upfrontLossesWrittenOffFeedback);

        this.upfrontOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = 2");
        this.upfrontOverPaymentLiabilityField = new Select2SingleChoice<>("upfrontOverPaymentLiabilityField",
                new PropertyModel<>(this, "upfrontOverPaymentLiabilityValue"),
                this.upfrontOverPaymentLiabilityProvider);
        this.upfrontOverPaymentLiabilityField.setRequired(false);
        this.upfrontOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontOverPaymentLiabilityField);
        this.upfrontOverPaymentLiabilityFeedback = new TextFeedbackPanel("upfrontOverPaymentLiabilityFeedback",
                this.upfrontOverPaymentLiabilityField);
        this.upfrontContainer.add(this.upfrontOverPaymentLiabilityFeedback);
    }

    protected void initAccountingCash() {
        this.cashContainer = new WebMarkupContainer("cashContainer");
        this.form.add(this.cashContainer);

        this.cashFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashFundSourceProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashFundSourceProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashFundSourceField = new Select2SingleChoice<>("cashFundSourceField",
                new PropertyModel<>(this, "cashFundSourceValue"), this.cashFundSourceProvider);
        this.cashFundSourceField.setRequired(false);
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashFundSourceField);
        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceField);
        this.cashContainer.add(this.cashFundSourceFeedback);

        this.cashLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLoanPortfolioProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField",
                new PropertyModel<>(this, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioField.setRequired(false);
        this.cashLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashLoanPortfolioField);
        this.cashLoanPortfolioFeedback = new TextFeedbackPanel("cashLoanPortfolioFeedback",
                this.cashLoanPortfolioField);
        this.cashContainer.add(this.cashLoanPortfolioFeedback);

        this.cashTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashTransferInSuspenseField = new Select2SingleChoice<>("cashTransferInSuspenseField",
                new PropertyModel<>(this, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
        this.cashTransferInSuspenseField.setRequired(false);
        this.cashTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseFeedback = new TextFeedbackPanel("cashTransferInSuspenseFeedback",
                this.cashTransferInSuspenseField);
        this.cashContainer.add(this.cashTransferInSuspenseFeedback);

        this.cashIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromInterestField = new Select2SingleChoice<>("cashIncomeFromInterestField",
                new PropertyModel<>(this, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
        this.cashIncomeFromInterestField.setRequired(false);
        this.cashIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestFeedback = new TextFeedbackPanel("cashIncomeFromInterestFeedback",
                this.cashIncomeFromInterestField);
        this.cashContainer.add(this.cashIncomeFromInterestFeedback);

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField",
                new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setRequired(false);
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback",
                this.cashIncomeFromFeeField);
        this.cashContainer.add(this.cashIncomeFromFeeFeedback);

        this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField",
                new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
        this.cashIncomeFromPenaltiesField.setRequired(false);
        this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromPenaltiesField);
        this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback",
                this.cashIncomeFromPenaltiesField);
        this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);

        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField",
                new PropertyModel<>(this, "cashIncomeFromRecoveryRepaymentValue"),
                this.cashIncomeFromRecoveryRepaymentProvider);
        this.cashIncomeFromRecoveryRepaymentField.setRequired(false);
        this.cashIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryRepaymentFeedback",
                this.cashIncomeFromRecoveryRepaymentField);
        this.cashContainer.add(this.cashIncomeFromRecoveryRepaymentFeedback);

        this.cashLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.cashLossesWrittenOffField = new Select2SingleChoice<>("cashLossesWrittenOffField",
                new PropertyModel<>(this, "cashLossesWrittenOffValue"), this.cashLossesWrittenOffProvider);
        this.cashLossesWrittenOffField.setRequired(false);
        this.cashLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashLossesWrittenOffField);
        this.cashLossesWrittenOffFeedback = new TextFeedbackPanel("cashLossesWrittenOffFeedback",
                this.cashLossesWrittenOffField);
        this.cashContainer.add(this.cashLossesWrittenOffFeedback);

        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = 2");
        this.cashOverPaymentLiabilityField = new Select2SingleChoice<>("cashOverPaymentLiabilityField",
                new PropertyModel<>(this, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
        this.cashOverPaymentLiabilityField.setRequired(false);
        this.cashOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityFeedback = new TextFeedbackPanel("cashOverPaymentLiabilityFeedback",
                this.cashOverPaymentLiabilityField);
        this.cashContainer.add(this.cashOverPaymentLiabilityFeedback);
    }

    protected void initAccountingPeriodic() {
        this.periodicContainer = new WebMarkupContainer("periodicContainer");
        this.form.add(this.periodicContainer);

        this.periodicFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicFundSourceProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicFundSourceProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicFundSourceField = new Select2SingleChoice<>("periodicFundSourceField",
                new PropertyModel<>(this, "periodicFundSourceValue"), this.periodicFundSourceProvider);
        this.periodicFundSourceField.setRequired(false);
        this.periodicFundSourceField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicFundSourceField);
        this.periodicFundSourceFeedback = new TextFeedbackPanel("periodicFundSourceFeedback",
                this.periodicFundSourceField);
        this.periodicContainer.add(this.periodicFundSourceFeedback);

        this.periodicLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicLoanPortfolioProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicLoanPortfolioField = new Select2SingleChoice<>("periodicLoanPortfolioField",
                new PropertyModel<>(this, "periodicLoanPortfolioValue"), this.periodicLoanPortfolioProvider);
        this.periodicLoanPortfolioField.setRequired(false);
        this.periodicLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicLoanPortfolioField);
        this.periodicLoanPortfolioFeedback = new TextFeedbackPanel("periodicLoanPortfolioFeedback",
                this.periodicLoanPortfolioField);
        this.periodicContainer.add(this.periodicLoanPortfolioFeedback);

        this.periodicInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicInterestReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicInterestReceivableField = new Select2SingleChoice<>("periodicInterestReceivableField",
                new PropertyModel<>(this, "periodicInterestReceivableValue"), this.periodicInterestReceivableProvider);
        this.periodicInterestReceivableField.setRequired(false);
        this.periodicInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicInterestReceivableField);
        this.periodicInterestReceivableFeedback = new TextFeedbackPanel("periodicInterestReceivableFeedback",
                this.periodicInterestReceivableField);
        this.periodicContainer.add(this.periodicInterestReceivableFeedback);

        this.periodicFeesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicFeesReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicFeesReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicFeesReceivableField = new Select2SingleChoice<>("periodicFeesReceivableField",
                new PropertyModel<>(this, "periodicFeesReceivableValue"), this.periodicFeesReceivableProvider);
        this.periodicFeesReceivableField.setRequired(false);
        this.periodicFeesReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicFeesReceivableField);
        this.periodicFeesReceivableFeedback = new TextFeedbackPanel("periodicFeesReceivableFeedback",
                this.periodicFeesReceivableField);
        this.periodicContainer.add(this.periodicFeesReceivableFeedback);

        this.periodicPenaltiesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicPenaltiesReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicPenaltiesReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicPenaltiesReceivableField = new Select2SingleChoice<>("periodicPenaltiesReceivableField",
                new PropertyModel<>(this, "periodicPenaltiesReceivableValue"),
                this.periodicPenaltiesReceivableProvider);
        this.periodicPenaltiesReceivableField.setRequired(false);
        this.periodicPenaltiesReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicPenaltiesReceivableField);
        this.periodicPenaltiesReceivableFeedback = new TextFeedbackPanel("periodicPenaltiesReceivableFeedback",
                this.periodicPenaltiesReceivableField);
        this.periodicContainer.add(this.periodicPenaltiesReceivableFeedback);

        this.periodicTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.periodicTransferInSuspenseField = new Select2SingleChoice<>("periodicTransferInSuspenseField",
                new PropertyModel<>(this, "periodicTransferInSuspenseValue"), this.periodicTransferInSuspenseProvider);
        this.periodicTransferInSuspenseField.setRequired(false);
        this.periodicTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicTransferInSuspenseField);
        this.periodicTransferInSuspenseFeedback = new TextFeedbackPanel("periodicTransferInSuspenseFeedback",
                this.periodicTransferInSuspenseField);
        this.periodicContainer.add(this.periodicTransferInSuspenseFeedback);

        this.periodicIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromInterestField = new Select2SingleChoice<>("periodicIncomeFromInterestField",
                new PropertyModel<>(this, "periodicIncomeFromInterestValue"), this.periodicIncomeFromInterestProvider);
        this.periodicIncomeFromInterestField.setRequired(false);
        this.periodicIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromInterestField);
        this.periodicIncomeFromInterestFeedback = new TextFeedbackPanel("periodicIncomeFromInterestFeedback",
                this.periodicIncomeFromInterestField);
        this.periodicContainer.add(this.periodicIncomeFromInterestFeedback);

        this.periodicIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromFeeField = new Select2SingleChoice<>("periodicIncomeFromFeeField",
                new PropertyModel<>(this, "periodicIncomeFromFeeValue"), this.periodicIncomeFromFeeProvider);
        this.periodicIncomeFromFeeField.setRequired(false);
        this.periodicIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromFeeField);
        this.periodicIncomeFromFeeFeedback = new TextFeedbackPanel("periodicIncomeFromFeeFeedback",
                this.periodicIncomeFromFeeField);
        this.periodicContainer.add(this.periodicIncomeFromFeeFeedback);

        this.periodicIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromPenaltiesField = new Select2SingleChoice<>("periodicIncomeFromPenaltiesField",
                new PropertyModel<>(this, "periodicIncomeFromPenaltiesValue"),
                this.periodicIncomeFromPenaltiesProvider);
        this.periodicIncomeFromPenaltiesField.setRequired(false);
        this.periodicIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesField);
        this.periodicIncomeFromPenaltiesFeedback = new TextFeedbackPanel("periodicIncomeFromPenaltiesFeedback",
                this.periodicIncomeFromPenaltiesField);
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesFeedback);

        this.periodicIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>(
                "periodicIncomeFromRecoveryRepaymentField",
                new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentValue"),
                this.periodicIncomeFromRecoveryRepaymentProvider);
        this.periodicIncomeFromRecoveryRepaymentField.setRequired(false);
        this.periodicIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel(
                "periodicIncomeFromRecoveryRepaymentFeedback", this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentFeedback);

        this.periodicLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.periodicLossesWrittenOffField = new Select2SingleChoice<>("periodicLossesWrittenOffField",
                new PropertyModel<>(this, "periodicLossesWrittenOffValue"), this.periodicLossesWrittenOffProvider);
        this.periodicLossesWrittenOffField.setRequired(false);
        this.periodicLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicLossesWrittenOffField);
        this.periodicLossesWrittenOffFeedback = new TextFeedbackPanel("periodicLossesWrittenOffFeedback",
                this.periodicLossesWrittenOffField);
        this.periodicContainer.add(this.periodicLossesWrittenOffFeedback);

        this.periodicOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = 2");
        this.periodicOverPaymentLiabilityField = new Select2SingleChoice<>("periodicOverPaymentLiabilityField",
                new PropertyModel<>(this, "periodicOverPaymentLiabilityValue"),
                this.periodicOverPaymentLiabilityProvider);
        this.periodicOverPaymentLiabilityField.setRequired(false);
        this.periodicOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicOverPaymentLiabilityField);
        this.periodicOverPaymentLiabilityFeedback = new TextFeedbackPanel("periodicOverPaymentLiabilityFeedback",
                this.periodicOverPaymentLiabilityField);
        this.periodicContainer.add(this.periodicOverPaymentLiabilityFeedback);

    }

    protected void initAdvancedAccountingRule() {

        this.advancedAccountingRuleContainer = new WebMarkupContainer("advancedAccountingRuleContainer");
        this.form.add(this.advancedAccountingRuleContainer);

        {
            List<IColumn<Map<String, Object>, String>> fundSourceColumn = Lists.newArrayList();
            fundSourceColumn
                    .add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::fundSourcePaymentColumn));
            fundSourceColumn
                    .add(new TextColumn(Model.of("Fund Source"), "account", "account", this::fundSourceAccountColumn));
            fundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::fundSourceActionItem,
                    this::fundSourceActionClick));
            this.fundSourceProvider = new ListDataProvider(this.fundSourceValue);
            this.fundSourceTable = new DataTable<>("fundSourceTable", fundSourceColumn, this.fundSourceProvider, 20);
            this.advancedAccountingRuleContainer.add(this.fundSourceTable);
            this.fundSourceTable.addTopToolbar(new HeadersToolbar<>(this.fundSourceTable, this.fundSourceProvider));
            this.fundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.fundSourceTable));

            AjaxLink<Void> fundSourceAddLink = new AjaxLink<>("fundSourceAddLink");
            fundSourceAddLink.setOnClick(this::fundSourceAddLinkClick);
            this.advancedAccountingRuleContainer.add(fundSourceAddLink);
        }

        {

            List<IColumn<Map<String, Object>, String>> feeIncomeColumn = Lists.newArrayList();
            feeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::feeIncomeChargeColumn));
            feeIncomeColumn.add(
                    new TextColumn(Model.of("Income Account"), "account", "account", this::feeIncomeAccountColumn));
            feeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::feeIncomeActionItem,
                    this::feeIncomeActionClick));
            this.feeIncomeProvider = new ListDataProvider(this.feeIncomeValue);
            this.feeIncomeTable = new DataTable<>("feeIncomeTable", feeIncomeColumn, this.feeIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.feeIncomeTable);
            this.feeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.feeIncomeTable, this.feeIncomeProvider));
            this.feeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.feeIncomeTable));

            AjaxLink<Void> feeIncomeAddLink = new AjaxLink<>("feeIncomeAddLink");
            feeIncomeAddLink.setOnClick(this::feeIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(feeIncomeAddLink);
        }

        {
            List<IColumn<Map<String, Object>, String>> penaltyIncomeColumn = Lists.newArrayList();
            penaltyIncomeColumn
                    .add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::penaltyIncomeChargeColumn));
            penaltyIncomeColumn.add(
                    new TextColumn(Model.of("Income Account"), "account", "account", this::penaltyIncomeAccountColumn));
            penaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::penaltyIncomeActionItem,
                    this::penaltyIncomeActionClick));
            this.penaltyIncomeProvider = new ListDataProvider(this.penaltyIncomeValue);
            this.penaltyIncomeTable = new DataTable<>("penaltyIncomeTable", penaltyIncomeColumn,
                    this.penaltyIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.penaltyIncomeTable);
            this.penaltyIncomeTable
                    .addTopToolbar(new HeadersToolbar<>(this.penaltyIncomeTable, this.penaltyIncomeProvider));
            this.penaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.penaltyIncomeTable));

            AjaxLink<Void> penaltyIncomeAddLink = new AjaxLink<>("penaltyIncomeAddLink");
            penaltyIncomeAddLink.setOnClick(this::penaltyIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(penaltyIncomeAddLink);
        }
    }

    protected boolean feeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        this.feeChargePopup.show(target);
        return false;
    }

    protected ItemPanel feeIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel feeIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected void feeIncomeActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.feeIncomeValue.size(); i++) {
            Map<String, Object> column = this.feeIncomeValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.feeIncomeValue.remove(index);
        }
        ajaxRequestTarget.add(this.feeIncomeTable);
    }

    protected List<ActionItem> feeIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean penaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        this.penaltyChargePopup.show(target);
        return false;
    }

    protected ItemPanel penaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel penaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected void penaltyIncomeActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.penaltyIncomeValue.size(); i++) {
            Map<String, Object> column = this.penaltyIncomeValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.penaltyIncomeValue.remove(index);
        }
        ajaxRequestTarget.add(this.penaltyIncomeTable);
    }

    protected List<ActionItem> penaltyIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean fundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemPaymentValue = null;
        this.itemAccountValue = null;
        this.paymentPopup.show(target);
        return false;
    }

    protected ItemPanel fundSourcePaymentColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel fundSourceAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected void fundSourceActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.fundSourceValue.size(); i++) {
            Map<String, Object> column = this.fundSourceValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.fundSourceValue.remove(index);
        }
        ajaxRequestTarget.add(this.fundSourceTable);
    }

    protected List<ActionItem> fundSourceActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashContainer.setVisible(false);
        this.periodicContainer.setVisible(false);
        this.upfrontContainer.setVisible(false);
        this.advancedAccountingRuleContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashContainer.setVisible(true);
        } else if ("Periodic".equals(this.accountingValue)) {
            this.periodicContainer.setVisible(true);
        } else if ("Upfront".equals(this.accountingValue)) {
            this.upfrontContainer.setVisible(true);
        }

        target.add(this.form);
        return false;
    }

    protected void initConfigurableTermsAndSettings() {
        this.allowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox(
                "allowOverridingSelectTermsAndSettingsInLoanAccountField",
                new PropertyModel<>(this, "allowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.allowOverridingSelectTermsAndSettingsInLoanAccountField.setRequired(false);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountField
                .add(new OnChangeAjaxBehavior(this::allowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));
        this.form.add(this.allowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountFeedback = new TextFeedbackPanel(
                "allowOverridingSelectTermsAndSettingsInLoanAccountFeedback",
                this.allowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.form.add(this.allowOverridingSelectTermsAndSettingsInLoanAccountFeedback);

        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer = new WebMarkupContainer(
                "allowOverridingSelectTermsAndSettingsInLoanAccountContainer");
        this.form.add(this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer);

        this.configurableAmortizationField = new CheckBox("configurableAmortizationField",
                new PropertyModel<>(this, "configurableAmortizationValue"));
        this.configurableAmortizationField.setRequired(false);
        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback",
                this.configurableAmortizationField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAmortizationFeedback);

        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField",
                new PropertyModel<>(this, "configurableInterestMethodValue"));
        this.configurableInterestMethodField.setRequired(false);
        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback",
                this.configurableInterestMethodField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableInterestMethodFeedback);

        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField",
                new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyField.setRequired(false);
        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback",
                this.configurableRepaymentStrategyField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableRepaymentStrategyFeedback);

        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField",
                new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodField.setRequired(false);
        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel(
                "configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableInterestCalculationPeriodFeedback);

        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField",
                new PropertyModel<>(this, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceField.setRequired(false);
        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback",
                this.configurableArrearsToleranceField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableArrearsToleranceFeedback);

        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField",
                new PropertyModel<>(this, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryField.setRequired(false);
        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback",
                this.configurableRepaidEveryField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaidEveryFeedback);

        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField",
                new PropertyModel<>(this, "configurableMoratoriumValue"));
        this.configurableMoratoriumField.setRequired(false);
        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback",
                this.configurableMoratoriumField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableMoratoriumFeedback);

        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField",
                new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingField.setRequired(false);
        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback",
                this.configurableOverdueBeforeMovingField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableOverdueBeforeMovingFeedback);
    }

    protected boolean allowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .setVisible(this.allowOverridingSelectTermsAndSettingsInLoanAccountValue != null
                        && this.allowOverridingSelectTermsAndSettingsInLoanAccountValue);
        target.add(this.form);
        return false;
    }

    protected void initLoanTrancheDetails() {
        this.enableMultipleDisbursalField = new CheckBox("enableMultipleDisbursalField",
                new PropertyModel<>(this, "enableMultipleDisbursalValue"));
        this.enableMultipleDisbursalField.setRequired(false);
        this.enableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::enableMultipleDisbursalFieldUpdate));
        this.form.add(this.enableMultipleDisbursalField);
        this.enableMultipleDisbursalFeedback = new TextFeedbackPanel("enableMultipleDisbursalFeedback",
                this.enableMultipleDisbursalField);
        this.form.add(this.enableMultipleDisbursalFeedback);

        this.enableMultipleDisbursalContainer = new WebMarkupContainer("enableMultipleDisbursalContainer");
        this.form.add(this.enableMultipleDisbursalContainer);

        this.maximumTrancheCountField = new TextField<>("maximumTrancheCountField",
                new PropertyModel<>(this, "maximumTrancheCountValue"));
        this.maximumTrancheCountField.setRequired(false);
        this.maximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.enableMultipleDisbursalContainer.add(this.maximumTrancheCountField);
        this.maximumTrancheCountFeedback = new TextFeedbackPanel("maximumTrancheCountFeedback",
                this.maximumTrancheCountField);
        this.enableMultipleDisbursalContainer.add(this.maximumTrancheCountFeedback);

        this.maximumAllowedOutstandingBalanceField = new TextField<>("maximumAllowedOutstandingBalanceField",
                new PropertyModel<>(this, "maximumAllowedOutstandingBalanceValue"));
        this.maximumAllowedOutstandingBalanceField.setRequired(false);
        this.maximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.enableMultipleDisbursalContainer.add(this.maximumAllowedOutstandingBalanceField);
        this.maximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel(
                "maximumAllowedOutstandingBalanceFeedback", this.maximumAllowedOutstandingBalanceField);
        this.enableMultipleDisbursalContainer.add(this.maximumAllowedOutstandingBalanceFeedback);
    }

    protected boolean enableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        this.enableMultipleDisbursalContainer
                .setVisible(this.enableMultipleDisbursalValue != null && this.enableMultipleDisbursalValue);
        target.add(this.form);
        return false;
    }

    protected void initGuaranteeRequirements() {

        this.placeGuaranteeFundsOnHoldField = new CheckBox("placeGuaranteeFundsOnHoldField",
                new PropertyModel<>(this, "placeGuaranteeFundsOnHoldValue"));
        this.placeGuaranteeFundsOnHoldField.setRequired(false);
        this.placeGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::placeGuaranteeFundsOnHoldFieldUpdate));
        this.form.add(this.placeGuaranteeFundsOnHoldField);
        this.placeGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("placeGuaranteeFundsOnHoldFeedback",
                this.placeGuaranteeFundsOnHoldField);
        this.form.add(this.placeGuaranteeFundsOnHoldFeedback);

        this.placeGuaranteeFundsOnHoldContainer = new WebMarkupContainer("placeGuaranteeFundsOnHoldContainer");
        this.form.add(this.placeGuaranteeFundsOnHoldContainer);

        this.mandatoryGuaranteeField = new TextField<>("mandatoryGuaranteeField",
                new PropertyModel<>(this, "mandatoryGuaranteeValue"));
        this.mandatoryGuaranteeField.setRequired(false);
        this.mandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.mandatoryGuaranteeField);
        this.mandatoryGuaranteeFeedback = new TextFeedbackPanel("mandatoryGuaranteeFeedback",
                this.mandatoryGuaranteeField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.mandatoryGuaranteeFeedback);

        this.minimumGuaranteeField = new TextField<>("minimumGuaranteeField",
                new PropertyModel<>(this, "minimumGuaranteeValue"));
        this.minimumGuaranteeField.setRequired(false);
        this.minimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeField);
        this.minimumGuaranteeFeedback = new TextFeedbackPanel("minimumGuaranteeFeedback", this.minimumGuaranteeField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFeedback);

        this.minimumGuaranteeFromGuarantorField = new TextField<>("minimumGuaranteeFromGuarantorField",
                new PropertyModel<>(this, "minimumGuaranteeFromGuarantorValue"));
        this.minimumGuaranteeFromGuarantorField.setRequired(false);
        this.minimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFromGuarantorField);
        this.minimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("minimumGuaranteeFromGuarantorFeedback",
                this.minimumGuaranteeFromGuarantorField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFromGuarantorFeedback);

    }

    protected boolean placeGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        this.placeGuaranteeFundsOnHoldContainer
                .setVisible(this.placeGuaranteeFundsOnHoldValue != null && this.placeGuaranteeFundsOnHoldValue);
        target.add(this.form);
        return false;
    }

    protected boolean recalculateInterestFieldUpdate(AjaxRequestTarget target) {
        this.recalculateInterestContainer
                .setVisible(this.recalculateInterestValue != null && this.recalculateInterestValue);
        target.add(this.form);
        return false;
    }

    protected void initInterestRecalculation() {
        this.recalculateInterestField = new CheckBox("recalculateInterestField",
                new PropertyModel<>(this, "recalculateInterestValue"));
        this.recalculateInterestField.setRequired(false);
        this.recalculateInterestField.add(new OnChangeAjaxBehavior(this::recalculateInterestFieldUpdate));
        this.form.add(this.recalculateInterestField);
        this.recalculateInterestFeedback = new TextFeedbackPanel("recalculateInterestFeedback",
                this.recalculateInterestField);
        this.form.add(this.recalculateInterestFeedback);

        this.recalculateInterestContainer = new WebMarkupContainer("recalculateInterestContainer");
        this.form.add(this.recalculateInterestContainer);

        this.preClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
        this.preClosureInterestCalculationRuleField = new Select2SingleChoice<>(
                "preClosureInterestCalculationRuleField", 0,
                new PropertyModel<>(this, "preClosureInterestCalculationRuleValue"),
                this.preClosureInterestCalculationRuleProvider);
        this.preClosureInterestCalculationRuleField.setRequired(false);
        this.preClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.recalculateInterestContainer.add(this.preClosureInterestCalculationRuleField);
        this.preClosureInterestCalculationRuleFeedback = new TextFeedbackPanel(
                "preClosureInterestCalculationRuleFeedback", this.preClosureInterestCalculationRuleField);
        this.recalculateInterestContainer.add(this.preClosureInterestCalculationRuleFeedback);

        this.advancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.advancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("advancePaymentsAdjustmentTypeField", 0,
                new PropertyModel<>(this, "advancePaymentsAdjustmentTypeValue"),
                this.advancePaymentsAdjustmentTypeProvider);
        this.advancePaymentsAdjustmentTypeField.setRequired(false);
        this.advancePaymentsAdjustmentTypeField.add(new OnChangeAjaxBehavior());
        this.recalculateInterestContainer.add(this.advancePaymentsAdjustmentTypeField);
        this.advancePaymentsAdjustmentTypeFeedback = new TextFeedbackPanel("advancePaymentsAdjustmentTypeFeedback",
                this.advancePaymentsAdjustmentTypeField);
        this.recalculateInterestContainer.add(this.advancePaymentsAdjustmentTypeFeedback);

        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
        this.interestRecalculationCompoundingOnField = new Select2SingleChoice<>(
                "interestRecalculationCompoundingOnField", 0,
                new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"),
                this.interestRecalculationCompoundingOnProvider);
        this.interestRecalculationCompoundingOnField.setRequired(false);
        this.interestRecalculationCompoundingOnField.add(new OnChangeAjaxBehavior(this::compoundingFieldUpdate));
        this.recalculateInterestContainer.add(this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnFeedback = new TextFeedbackPanel(
                "interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnField);
        this.recalculateInterestContainer.add(this.interestRecalculationCompoundingOnFeedback);

        this.compoundingContainer = new WebMarkupContainer("compoundingContainer");
        this.recalculateInterestContainer.add(this.compoundingContainer);

        this.compoundingProvider = new FrequencyProvider();
        this.compoundingField = new Select2SingleChoice<>("compoundingField", 0,
                new PropertyModel<>(this, "compoundingValue"), this.compoundingProvider);
        this.compoundingField.setRequired(false);
        this.compoundingContainer.add(this.compoundingField);
        this.compoundingField.add(new OnChangeAjaxBehavior(this::compoundingFieldUpdate));
        this.compoundingFeedback = new TextFeedbackPanel("compoundingFeedback", this.compoundingField);
        this.compoundingContainer.add(this.compoundingFeedback);

        this.compoundingTypeContainer = new WebMarkupContainer("compoundingTypeContainer");
        this.compoundingContainer.add(this.compoundingTypeContainer);

        this.compoundingTypeProvider = new FrequencyTypeProvider();
        this.compoundingTypeField = new Select2SingleChoice<>("compoundingTypeField", 0,
                new PropertyModel<>(this, "compoundingTypeValue"), this.compoundingTypeProvider);
        this.compoundingTypeField.setRequired(false);
        this.compoundingTypeField.add(new OnChangeAjaxBehavior());
        this.compoundingTypeContainer.add(this.compoundingTypeField);
        this.compoundingTypeFeedback = new TextFeedbackPanel("compoundingTypeFeedback", this.compoundingTypeField);
        this.compoundingTypeContainer.add(this.compoundingTypeFeedback);

        this.compoundingDayContainer = new WebMarkupContainer("compoundingDayContainer");
        this.compoundingContainer.add(this.compoundingDayContainer);

        this.compoundingDayProvider = new FrequencyDayProvider();
        this.compoundingDayField = new Select2SingleChoice<>("compoundingDayField", 0,
                new PropertyModel<>(this, "compoundingDayValue"), this.compoundingDayProvider);
        this.compoundingDayField.setRequired(false);
        this.compoundingDayField.add(new OnChangeAjaxBehavior());
        this.compoundingDayContainer.add(this.compoundingDayField);
        this.compoundingDayFeedback = new TextFeedbackPanel("compoundingDayFeedback", this.compoundingDayField);
        this.compoundingDayContainer.add(this.compoundingDayFeedback);

        this.compoundingIntervalContainer = new WebMarkupContainer("compoundingIntervalContainer");
        this.recalculateInterestContainer.add(this.compoundingIntervalContainer);

        this.compoundingIntervalField = new TextField<>("compoundingIntervalField",
                new PropertyModel<>(this, "compoundingIntervalValue"));
        this.compoundingIntervalField.setRequired(false);
        this.compoundingIntervalField.add(new OnChangeAjaxBehavior());
        this.compoundingIntervalContainer.add(this.compoundingIntervalField);
        this.compoundingIntervalFeedback = new TextFeedbackPanel("compoundingIntervalFeedback",
                this.compoundingIntervalField);
        this.compoundingIntervalContainer.add(this.compoundingIntervalFeedback);

        this.recalculateProvider = new FrequencyProvider();
        this.recalculateField = new Select2SingleChoice<>("recalculateField", 0,
                new PropertyModel<>(this, "recalculateValue"), this.recalculateProvider);
        this.recalculateField.setRequired(false);
        this.recalculateField.add(new OnChangeAjaxBehavior(this::recalculateFieldUpdate));
        this.recalculateInterestContainer.add(this.recalculateField);
        this.recalculateFeedback = new TextFeedbackPanel("recalculateFeedback", this.recalculateField);
        this.recalculateInterestContainer.add(this.recalculateFeedback);

        this.recalculateTypeContainer = new WebMarkupContainer("recalculateTypeContainer");
        this.recalculateInterestContainer.add(this.recalculateTypeContainer);

        this.recalculateTypeProvider = new FrequencyTypeProvider();
        this.recalculateTypeField = new Select2SingleChoice<>("recalculateTypeField", 0,
                new PropertyModel<>(this, "recalculateTypeValue"), this.recalculateTypeProvider);
        this.recalculateTypeField.setRequired(false);
        this.recalculateTypeField.add(new OnChangeAjaxBehavior());
        this.recalculateTypeContainer.add(this.recalculateTypeField);
        this.recalculateTypeFeedback = new TextFeedbackPanel("recalculateTypeFeedback", this.recalculateTypeField);
        this.recalculateTypeContainer.add(this.recalculateTypeFeedback);

        this.recalculateDayContainer = new WebMarkupContainer("recalculateDayContainer");
        this.recalculateInterestContainer.add(this.recalculateDayContainer);

        this.recalculateDayProvider = new FrequencyDayProvider();
        this.recalculateDayField = new Select2SingleChoice<>("recalculateDayField", 0,
                new PropertyModel<>(this, "recalculateDayValue"), this.recalculateDayProvider);
        this.recalculateDayField.setRequired(false);
        this.recalculateDayField.add(new OnChangeAjaxBehavior());
        this.recalculateDayContainer.add(this.recalculateDayField);
        this.recalculateDayFeedback = new TextFeedbackPanel("recalculateDayFeedback", this.recalculateDayField);
        this.recalculateDayContainer.add(this.recalculateDayFeedback);

        this.recalculateIntervalContainer = new WebMarkupContainer("recalculateIntervalContainer");
        this.recalculateInterestContainer.add(this.recalculateIntervalContainer);

        this.recalculateIntervalField = new TextField<>("recalculateIntervalField",
                new PropertyModel<>(this, "recalculateIntervalValue"));
        this.recalculateIntervalField.setRequired(false);
        this.recalculateIntervalField.add(new OnChangeAjaxBehavior());
        this.recalculateIntervalContainer.add(this.recalculateIntervalField);
        this.recalculateIntervalFeedback = new TextFeedbackPanel("recalculateIntervalFeedback",
                this.recalculateIntervalField);
        this.recalculateIntervalContainer.add(this.recalculateIntervalFeedback);

        this.arrearsRecognizationBasedOnOriginalScheduleField = new CheckBox(
                "arrearsRecognizationBasedOnOriginalScheduleField",
                new PropertyModel<>(this, "arrearsRecognizationBasedOnOriginalScheduleValue"));
        this.arrearsRecognizationBasedOnOriginalScheduleField.setRequired(false);
        this.arrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());
        this.recalculateInterestContainer.add(this.arrearsRecognizationBasedOnOriginalScheduleField);
        this.arrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel(
                "arrearsRecognizationBasedOnOriginalScheduleFeedback",
                this.arrearsRecognizationBasedOnOriginalScheduleField);
        this.recalculateInterestContainer.add(this.arrearsRecognizationBasedOnOriginalScheduleFeedback);

    }

    protected boolean recalculateFieldUpdate(AjaxRequestTarget target) {
        this.recalculateTypeContainer.setVisible(false);
        this.recalculateDayContainer.setVisible(false);
        this.recalculateIntervalContainer.setVisible(false);

        if (this.recalculateValue != null) {
            Frequency frequency = Frequency.valueOf(this.recalculateValue.getId());
            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.recalculateIntervalContainer.setVisible(true);
            }
            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.recalculateDayContainer.setVisible(true);
            }
            if (frequency == Frequency.Monthly) {
                this.recalculateTypeContainer.setVisible(true);
            }
        }

        target.add(this.form);
        return false;
    }

    protected boolean compoundingFieldUpdate(AjaxRequestTarget target) {

        this.compoundingTypeContainer.setVisible(false);
        this.compoundingDayContainer.setVisible(false);
        this.compoundingIntervalContainer.setVisible(false);

        if (this.interestRecalculationCompoundingOnValue != null && InterestRecalculationCompound
                .valueOf(this.interestRecalculationCompoundingOnValue.getId()) != InterestRecalculationCompound.None) {
            this.compoundingContainer.setVisible(true);

            if (this.compoundingValue != null) {
                Frequency frequency = Frequency.valueOf(this.compoundingValue.getId());
                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.compoundingIntervalContainer.setVisible(true);
                }
                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.compoundingDayContainer.setVisible(true);
                }
                if (frequency == Frequency.Monthly) {
                    this.compoundingTypeContainer.setVisible(true);
                }
            }
        } else {
            this.compoundingContainer.setVisible(false);
        }
        target.add(this.form);
        return false;
    }

    protected void initSetting() {

        this.amortizationProvider = new AmortizationProvider();
        this.amortizationField = new Select2SingleChoice<>("amortizationField", 0,
                new PropertyModel<>(this, "amortizationValue"), this.amortizationProvider);
        this.amortizationField.setRequired(true);
        this.amortizationField.add(new OnChangeAjaxBehavior());
        this.form.add(this.amortizationField);
        this.amortizationFeedback = new TextFeedbackPanel("amortizationFeedback", this.amortizationField);
        this.form.add(this.amortizationFeedback);

        this.interestMethodProvider = new InterestMethodProvider();
        this.interestMethodField = new Select2SingleChoice<>("interestMethodField", 0,
                new PropertyModel<>(this, "interestMethodValue"), this.interestMethodProvider);
        this.interestMethodField.setRequired(true);
        this.interestMethodField.add(new OnChangeAjaxBehavior());
        this.form.add(this.interestMethodField);
        this.interestMethodFeedback = new TextFeedbackPanel("interestMethodFeedback", this.interestMethodField);
        this.form.add(this.interestMethodFeedback);

        this.interestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
        this.interestCalculationPeriodField = new Select2SingleChoice<>("interestCalculationPeriodField", 0,
                new PropertyModel<>(this, "interestCalculationPeriodValue"), this.interestCalculationPeriodProvider);
        this.interestCalculationPeriodField.setRequired(true);
        this.interestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::interestCalculationPeriodFieldUpdate));
        this.form.add(this.interestCalculationPeriodField);
        this.interestCalculationPeriodFeedback = new TextFeedbackPanel("interestCalculationPeriodFeedback",
                this.interestCalculationPeriodField);
        this.form.add(this.interestCalculationPeriodFeedback);

        this.calculateInterestForExactDaysInPartialPeriodContainer = new WebMarkupContainer(
                "calculateInterestForExactDaysInPartialPeriodContainer");
        this.form.add(this.calculateInterestForExactDaysInPartialPeriodContainer);

        this.calculateInterestForExactDaysInPartialPeriodField = new CheckBox(
                "calculateInterestForExactDaysInPartialPeriodField",
                new PropertyModel<>(this, "calculateInterestForExactDaysInPartialPeriodValue"));
        this.calculateInterestForExactDaysInPartialPeriodField.setRequired(false);
        this.calculateInterestForExactDaysInPartialPeriodField.add(new OnChangeAjaxBehavior());
        this.calculateInterestForExactDaysInPartialPeriodContainer
                .add(this.calculateInterestForExactDaysInPartialPeriodField);
        this.calculateInterestForExactDaysInPartialPeriodFeedback = new TextFeedbackPanel(
                "calculateInterestForExactDaysInPartialPeriodFeedback",
                this.calculateInterestForExactDaysInPartialPeriodField);
        this.calculateInterestForExactDaysInPartialPeriodContainer
                .add(this.calculateInterestForExactDaysInPartialPeriodFeedback);

        this.repaymentStrategyProvider = new RepaymentStrategyProvider();
        this.repaymentStrategyField = new Select2SingleChoice<>("repaymentStrategyField", 0,
                new PropertyModel<>(this, "repaymentStrategyValue"), this.repaymentStrategyProvider);
        this.repaymentStrategyField.setRequired(true);
        this.repaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.form.add(this.repaymentStrategyField);
        this.repaymentStrategyFeedback = new TextFeedbackPanel("repaymentStrategyFeedback",
                this.repaymentStrategyField);
        this.form.add(this.repaymentStrategyFeedback);

        this.moratoriumPrincipalField = new TextField<>("moratoriumPrincipalField",
                new PropertyModel<>(this, "moratoriumPrincipalValue"));
        this.moratoriumPrincipalField.setRequired(false);
        this.moratoriumPrincipalField.add(new OnChangeAjaxBehavior());
        this.form.add(this.moratoriumPrincipalField);
        this.moratoriumPrincipalFeedback = new TextFeedbackPanel("moratoriumPrincipalFeedback",
                this.moratoriumPrincipalField);
        this.form.add(this.moratoriumPrincipalFeedback);

        this.moratoriumInterestField = new TextField<>("moratoriumInterestField",
                new PropertyModel<>(this, "moratoriumInterestValue"));
        this.moratoriumInterestField.setRequired(false);
        this.moratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.form.add(this.moratoriumInterestField);
        this.moratoriumInterestFeedback = new TextFeedbackPanel("moratoriumInterestFeedback",
                this.moratoriumInterestField);
        this.form.add(this.moratoriumInterestFeedback);

        this.interestFreePeriodField = new TextField<>("interestFreePeriodField",
                new PropertyModel<>(this, "interestFreePeriodValue"));
        this.interestFreePeriodField.setRequired(false);
        this.interestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.form.add(this.interestFreePeriodField);
        this.interestFreePeriodFeedback = new TextFeedbackPanel("interestFreePeriodFeedback",
                this.interestFreePeriodField);
        this.form.add(this.interestFreePeriodFeedback);

        this.arrearsToleranceField = new TextField<>("arrearsToleranceField",
                new PropertyModel<>(this, "arrearsToleranceValue"));
        this.arrearsToleranceField.setRequired(false);
        this.arrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.form.add(this.arrearsToleranceField);
        this.arrearsToleranceFeedback = new TextFeedbackPanel("arrearsToleranceFeedback", this.arrearsToleranceField);
        this.form.add(this.arrearsToleranceFeedback);

        this.dayInYearProvider = new DayInYearProvider();
        this.dayInYearField = new Select2SingleChoice<>("dayInYearField", 0,
                new PropertyModel<>(this, "dayInYearValue"), this.dayInYearProvider);
        this.dayInYearField.setRequired(true);
        this.dayInYearField.add(new OnChangeAjaxBehavior());
        this.form.add(this.dayInYearField);
        this.dayInYearFeedback = new TextFeedbackPanel("dayInYearFeedback", this.dayInYearField);
        this.form.add(this.dayInYearFeedback);

        this.dayInMonthProvider = new DayInMonthProvider();
        this.dayInMonthField = new Select2SingleChoice<>("dayInMonthField", 0,
                new PropertyModel<>(this, "dayInMonthValue"), this.dayInMonthProvider);
        this.dayInMonthField.setRequired(true);
        this.dayInMonthField.add(new OnChangeAjaxBehavior());
        this.form.add(this.dayInMonthField);
        this.dayInMonthFeedback = new TextFeedbackPanel("dayInMonthFeedback", this.dayInMonthField);
        this.form.add(this.dayInMonthFeedback);

        this.allowFixingOfTheInstallmentAmountField = new CheckBox("allowFixingOfTheInstallmentAmountField",
                new PropertyModel<>(this, "allowFixingOfTheInstallmentAmountValue"));
        this.allowFixingOfTheInstallmentAmountField.setRequired(false);
        this.allowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());
        this.form.add(this.allowFixingOfTheInstallmentAmountField);
        this.allowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel(
                "allowFixingOfTheInstallmentAmountFeedback", this.allowFixingOfTheInstallmentAmountField);
        this.form.add(this.allowFixingOfTheInstallmentAmountFeedback);

        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>(
                "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField",
                new PropertyModel<>(this, "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setRequired(false);
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.form.add(this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel(
                "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback",
                this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.form.add(this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);

        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>(
                "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField",
                new PropertyModel<>(this, "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setRequired(false);
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.form.add(this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel(
                "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback",
                this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.form.add(this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);

        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox(
                "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField",
                new PropertyModel<>(this, "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.setRequired(false);
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());
        this.form.add(this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel(
                "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback",
                this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.form.add(this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);

        this.principalThresholdForLastInstalmentField = new TextField<>("principalThresholdForLastInstalmentField",
                new PropertyModel<>(this, "principalThresholdForLastInstalmentValue"));
        this.principalThresholdForLastInstalmentField.setRequired(false);
        this.principalThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.form.add(this.principalThresholdForLastInstalmentField);
        this.principalThresholdForLastInstalmentFeedback = new TextFeedbackPanel(
                "principalThresholdForLastInstalmentFeedback", this.principalThresholdForLastInstalmentField);
        this.form.add(this.principalThresholdForLastInstalmentFeedback);

        this.variableInstallmentsAllowedField = new CheckBox("variableInstallmentsAllowedField",
                new PropertyModel<>(this, "variableInstallmentsAllowedValue"));
        this.variableInstallmentsAllowedField.setRequired(false);
        this.variableInstallmentsAllowedField
                .add(new OnChangeAjaxBehavior(this::variableInstallmentsAllowedFieldUpdate));
        this.form.add(this.variableInstallmentsAllowedField);
        this.variableInstallmentsAllowedFeedback = new TextFeedbackPanel("variableInstallmentsAllowedFeedback",
                this.variableInstallmentsAllowedField);
        this.form.add(this.variableInstallmentsAllowedFeedback);

        this.variableInstallmentsAllowedContainer = new WebMarkupContainer("variableInstallmentsAllowedContainer");
        this.form.add(this.variableInstallmentsAllowedContainer);

        this.variableInstallmentsMinimumField = new TextField<>("variableInstallmentsMinimumField",
                new PropertyModel<>(this, "variableInstallmentsMinimumValue"));
        this.variableInstallmentsMinimumField.setRequired(false);
        this.variableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMinimumField);
        this.variableInstallmentsMinimumFeedback = new TextFeedbackPanel("variableInstallmentsMinimumFeedback",
                this.variableInstallmentsMinimumField);
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMinimumFeedback);

        this.variableInstallmentsMaximumField = new TextField<>("variableInstallmentsMaximumField",
                new PropertyModel<>(this, "variableInstallmentsMaximumValue"));
        this.variableInstallmentsMaximumField.setRequired(false);
        this.variableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMaximumField);
        this.variableInstallmentsMaximumFeedback = new TextFeedbackPanel("variableInstallmentsMaximumFeedback",
                this.variableInstallmentsMaximumField);
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMaximumFeedback);

        this.allowedToBeUsedForProvidingTopupLoansField = new CheckBox("allowedToBeUsedForProvidingTopupLoansField",
                new PropertyModel<>(this, "allowedToBeUsedForProvidingTopupLoansValue"));
        this.allowedToBeUsedForProvidingTopupLoansField.setRequired(false);
        this.allowedToBeUsedForProvidingTopupLoansField.add(new OnChangeAjaxBehavior());
        this.form.add(this.allowedToBeUsedForProvidingTopupLoansField);
        this.allowedToBeUsedForProvidingTopupLoansFeedback = new TextFeedbackPanel(
                "allowedToBeUsedForProvidingTopupLoansFeedback", this.allowedToBeUsedForProvidingTopupLoansField);
        this.form.add(this.allowedToBeUsedForProvidingTopupLoansFeedback);

    }

    protected void initDefault() {
        this.principalByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.numberOfRepaymentByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.nominalInterestRateByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);

        this.calculateInterestForExactDaysInPartialPeriodContainer
                .setVisible(this.interestCalculationPeriodValue != null && InterestCalculationPeriod.valueOf(
                        this.interestCalculationPeriodValue.getId()) == InterestCalculationPeriod.SameAsPayment);

        this.recalculateInterestContainer
                .setVisible(this.recalculateInterestValue != null && this.recalculateInterestValue);

        this.compoundingTypeContainer.setVisible(false);
        this.compoundingDayContainer.setVisible(false);
        this.compoundingIntervalContainer.setVisible(false);

        if (this.interestRecalculationCompoundingOnValue != null && InterestRecalculationCompound
                .valueOf(this.interestRecalculationCompoundingOnValue.getId()) != InterestRecalculationCompound.None) {
            this.compoundingContainer.setVisible(true);

            if (this.compoundingValue != null) {
                Frequency frequency = Frequency.valueOf(this.compoundingValue.getId());
                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.compoundingIntervalContainer.setVisible(true);
                }
                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.compoundingDayContainer.setVisible(true);
                }
                if (frequency == Frequency.Monthly) {
                    this.compoundingTypeContainer.setVisible(true);
                }
            }
        } else {
            this.compoundingContainer.setVisible(false);
        }

        this.recalculateTypeContainer.setVisible(false);
        this.recalculateDayContainer.setVisible(false);
        this.recalculateIntervalContainer.setVisible(false);

        if (this.recalculateValue != null) {
            Frequency frequency = Frequency.valueOf(this.recalculateValue.getId());
            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.recalculateIntervalContainer.setVisible(true);
            }
            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.recalculateDayContainer.setVisible(true);
            }
            if (frequency == Frequency.Monthly) {
                this.recalculateTypeContainer.setVisible(true);
            }
        }

        this.placeGuaranteeFundsOnHoldContainer
                .setVisible(this.placeGuaranteeFundsOnHoldValue != null && this.placeGuaranteeFundsOnHoldValue);

        this.enableMultipleDisbursalContainer
                .setVisible(this.enableMultipleDisbursalValue != null && this.enableMultipleDisbursalValue);

        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .setVisible(this.allowOverridingSelectTermsAndSettingsInLoanAccountValue != null
                        && this.allowOverridingSelectTermsAndSettingsInLoanAccountValue);

        this.cashContainer.setVisible(false);
        this.periodicContainer.setVisible(false);
        this.upfrontContainer.setVisible(false);
        this.advancedAccountingRuleContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashContainer.setVisible(true);
        } else if ("Periodic".equals(this.accountingValue)) {
            this.periodicContainer.setVisible(true);
        } else if ("Upfront".equals(this.accountingValue)) {
            this.upfrontContainer.setVisible(true);
        }

        this.variableInstallmentsAllowedContainer
                .setVisible(this.variableInstallmentsAllowedValue != null && this.variableInstallmentsAllowedValue);
    }

    protected void initDetail() {
        this.productNameField = new TextField<>("productNameField", new PropertyModel<>(this, "productNameValue"));
        this.productNameField.setRequired(true);
        this.productNameField.add(new OnChangeAjaxBehavior());
        this.form.add(this.productNameField);
        this.productNameFeedback = new TextFeedbackPanel("productNameFeedback", this.productNameField);
        this.form.add(this.productNameFeedback);

        this.shortNameField = new TextField<>("shortNameField", new PropertyModel<>(this, "shortNameValue"));
        this.shortNameField.setRequired(true);
        this.shortNameField.add(new OnChangeAjaxBehavior());
        this.form.add(this.shortNameField);
        this.shortNameFeedback = new TextFeedbackPanel("shortNameFeedback", this.shortNameField);
        this.form.add(this.shortNameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(false);
        this.descriptionField.add(new OnChangeAjaxBehavior());
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.fundProvider = new SingleChoiceProvider("m_fund", "id", "name");
        this.fundField = new Select2SingleChoice<>("fundField", 0, new PropertyModel<>(this, "fundValue"),
                this.fundProvider);
        this.fundField.setRequired(false);
        this.fundField.add(new OnChangeAjaxBehavior());
        this.form.add(this.fundField);
        this.fundFeedback = new TextFeedbackPanel("fundFeedback", this.fundField);
        this.form.add(this.fundFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(false);
        this.startDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.form.add(this.startDateFeedback);

        this.closeDateField = new DateTextField("closeDateField", new PropertyModel<>(this, "closeDateValue"));
        this.closeDateField.setRequired(false);
        this.closeDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.closeDateField);
        this.closeDateFeedback = new TextFeedbackPanel("closeDateFeedback", this.closeDateField);
        this.form.add(this.closeDateFeedback);

        this.includeInCustomerLoanCounterField = new CheckBox("includeInCustomerLoanCounterField",
                new PropertyModel<>(this, "includeInCustomerLoanCounterValue"));
        this.includeInCustomerLoanCounterField.setRequired(false);
        this.includeInCustomerLoanCounterField.add(new OnChangeAjaxBehavior());
        this.form.add(this.includeInCustomerLoanCounterField);
        this.includeInCustomerLoanCounterFeedback = new TextFeedbackPanel("includeInCustomerLoanCounterFeedback",
                this.includeInCustomerLoanCounterField);
        this.form.add(this.includeInCustomerLoanCounterFeedback);
    }

    protected void initCurrency() {
        this.currencyProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
                "concat(name,' [', code,']')");
        this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
                this.currencyProvider);
        this.currencyField.add(new OnChangeAjaxBehavior());
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        this.decimalPlaceField = new TextField<>("decimalPlaceField", new PropertyModel<>(this, "decimalPlaceValue"));
        this.decimalPlaceField.setRequired(true);
        this.decimalPlaceField.add(new OnChangeAjaxBehavior());
        this.decimalPlaceField.add(RangeValidator.range((int) 0, (int) 6));
        this.form.add(this.decimalPlaceField);
        this.decimalPlaceFeedback = new TextFeedbackPanel("decimalPlaceFeedback", this.decimalPlaceField);
        this.form.add(this.decimalPlaceFeedback);

        this.currencyInMultipleOfField = new TextField<>("currencyInMultipleOfField",
                new PropertyModel<>(this, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfField.setRequired(false);
        this.currencyInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.form.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback",
                this.currencyInMultipleOfField);
        this.form.add(this.currencyInMultipleOfFeedback);

        this.installmentInMultipleOfField = new TextField<>("installmentInMultipleOfField",
                new PropertyModel<>(this, "installmentInMultipleOfValue"));
        this.installmentInMultipleOfField.setRequired(false);
        this.installmentInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.installmentInMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.form.add(this.installmentInMultipleOfField);
        this.installmentInMultipleOfFeedback = new TextFeedbackPanel("installmentInMultipleOfFeedback",
                this.installmentInMultipleOfField);
        this.form.add(this.installmentInMultipleOfFeedback);
    }

    protected void initTerms() {

        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField",
                new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.setRequired(false);
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
        this.form.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback",
                this.termVaryBasedOnLoanCycleField);
        this.form.add(this.termVaryBasedOnLoanCycleFeedback);

        this.principalMinimumField = new TextField<>("principalMinimumField",
                new PropertyModel<>(this, "principalMinimumValue"));
        this.principalMinimumField.add(new OnChangeAjaxBehavior());
        this.form.add(this.principalMinimumField);
        this.principalMinimumFeedback = new TextFeedbackPanel("principalMinimumFeedback", this.principalMinimumField);
        this.form.add(this.principalMinimumFeedback);

        this.principalDefaultField = new TextField<>("principalDefaultField",
                new PropertyModel<>(this, "principalDefaultValue"));
        this.principalDefaultField.add(new OnChangeAjaxBehavior());
        this.form.add(this.principalDefaultField);
        this.principalDefaultFeedback = new TextFeedbackPanel("principalDefaultFeedback", this.principalDefaultField);
        this.form.add(this.principalDefaultFeedback);

        this.principalMaximumField = new TextField<>("principalMaximumField",
                new PropertyModel<>(this, "principalMaximumValue"));
        this.principalMaximumField.add(new OnChangeAjaxBehavior());
        this.form.add(this.principalMaximumField);
        this.principalMaximumFeedback = new TextFeedbackPanel("principalMaximumFeedback", this.principalMaximumField);
        this.form.add(this.principalMaximumFeedback);

        {
            this.principalByLoanCycleContainer = new WebMarkupContainer("principalByLoanCycleContainer");
            this.form.add(this.principalByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> principalByLoanCycleColumn = Lists.newArrayList();
            principalByLoanCycleColumn
                    .add(new TextColumn(Model.of("When"), "when", "when", this::principalByLoanCycleWhenColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::principalByLoanCycleCycleColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Min"), "minimum", "minimum", this::principalByLoanCycleMinimumColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Default"), "default", "default", this::principalByLoanCycleDefaultColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Max"), "maximum", "maximum", this::principalByLoanCycleMaximumColumn));
            principalByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::principalByLoanCycleActionItem, this::principalByLoanCycleActionClick));
            this.principalByLoanCycleProvider = new ListDataProvider(this.principalByLoanCycleValue);
            this.principalByLoanCycleTable = new DataTable<>("principalByLoanCycleTable", principalByLoanCycleColumn,
                    this.principalByLoanCycleProvider, 20);
            this.principalByLoanCycleContainer.add(this.principalByLoanCycleTable);
            this.principalByLoanCycleTable.addTopToolbar(
                    new HeadersToolbar<>(this.principalByLoanCycleTable, this.principalByLoanCycleProvider));
            this.principalByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.principalByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::principalByLoanCycleAddLinkClick);
            this.principalByLoanCycleContainer.add(addLink);
        }

        this.numberOfRepaymentMinimumField = new TextField<>("numberOfRepaymentMinimumField",
                new PropertyModel<>(this, "numberOfRepaymentMinimumValue"));
        this.numberOfRepaymentMinimumField.add(new OnChangeAjaxBehavior());
        this.form.add(this.numberOfRepaymentMinimumField);
        this.numberOfRepaymentMinimumFeedback = new TextFeedbackPanel("numberOfRepaymentMinimumFeedback",
                this.numberOfRepaymentMinimumField);
        this.form.add(this.numberOfRepaymentMinimumFeedback);

        this.numberOfRepaymentDefaultField = new TextField<>("numberOfRepaymentDefaultField",
                new PropertyModel<>(this, "numberOfRepaymentDefaultValue"));
        this.numberOfRepaymentDefaultField.add(new OnChangeAjaxBehavior());
        this.numberOfRepaymentDefaultField.setRequired(true);
        this.form.add(this.numberOfRepaymentDefaultField);
        this.numberOfRepaymentDefaultFeedback = new TextFeedbackPanel("numberOfRepaymentDefaultFeedback",
                this.numberOfRepaymentDefaultField);
        this.form.add(this.numberOfRepaymentDefaultFeedback);

        this.numberOfRepaymentMaximumField = new TextField<>("numberOfRepaymentMaximumField",
                new PropertyModel<>(this, "numberOfRepaymentMaximumValue"));
        this.numberOfRepaymentMaximumField.add(new OnChangeAjaxBehavior());
        this.form.add(this.numberOfRepaymentMaximumField);
        this.numberOfRepaymentMaximumFeedback = new TextFeedbackPanel("numberOfRepaymentMaximumFeedback",
                this.numberOfRepaymentMaximumField);
        this.form.add(this.numberOfRepaymentMaximumFeedback);

        {
            this.numberOfRepaymentByLoanCycleContainer = new WebMarkupContainer(
                    "numberOfRepaymentByLoanCycleContainer");
            this.form.add(this.numberOfRepaymentByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> numberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
            numberOfRepaymentByLoanCycleColumn.add(
                    new TextColumn(Model.of("When"), "when", "when", this::numberOfRepaymentByLoanCycleWhenColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle",
                    this::numberOfRepaymentByLoanCycleCycleColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum",
                    this::numberOfRepaymentByLoanCycleMinimumColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default",
                    this::numberOfRepaymentByLoanCycleDefaultColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum",
                    this::numberOfRepaymentByLoanCycleMaximumColumn));
            numberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::numberOfRepaymentByLoanCycleActionItem, this::numberOfRepaymentByLoanCycleActionClick));
            this.numberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.numberOfRepaymentByLoanCycleValue);
            this.numberOfRepaymentByLoanCycleTable = new DataTable<>("numberOfRepaymentByLoanCycleTable",
                    numberOfRepaymentByLoanCycleColumn, this.numberOfRepaymentByLoanCycleProvider, 20);
            this.numberOfRepaymentByLoanCycleContainer.add(this.numberOfRepaymentByLoanCycleTable);
            this.numberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(
                    this.numberOfRepaymentByLoanCycleTable, this.numberOfRepaymentByLoanCycleProvider));
            this.numberOfRepaymentByLoanCycleTable
                    .addBottomToolbar(new NoRecordsToolbar(this.numberOfRepaymentByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::numberOfRepaymentByLoanCycleAddLinkClick);
            this.numberOfRepaymentByLoanCycleContainer.add(addLink);
        }

        // Linked to Floating Interest Rates
        this.linkedToFloatingInterestRatesField = new CheckBox("linkedToFloatingInterestRatesField",
                new PropertyModel<>(this, "linkedToFloatingInterestRatesValue"));
        this.linkedToFloatingInterestRatesField.setRequired(false);
        this.linkedToFloatingInterestRatesField
                .add(new OnChangeAjaxBehavior(this::linkedToFloatingInterestRatesFieldUpdate));
        this.form.add(this.linkedToFloatingInterestRatesField);
        this.linkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("linkedToFloatingInterestRatesFeedback",
                this.linkedToFloatingInterestRatesField);
        this.form.add(this.linkedToFloatingInterestRatesFeedback);

        this.nominalInterestRateContainer = new WebMarkupContainer("nominalInterestRateContainer");
        this.form.add(this.nominalInterestRateContainer);
        {
            this.nominalInterestRateMinimumField = new TextField<>("nominalInterestRateMinimumField",
                    new PropertyModel<>(this, "nominalInterestRateMinimumValue"));
            this.nominalInterestRateMinimumField.setRequired(false);
            this.nominalInterestRateMinimumField.add(new OnChangeAjaxBehavior());
            this.nominalInterestRateContainer.add(this.nominalInterestRateMinimumField);
            this.nominalInterestRateMinimumFeedback = new TextFeedbackPanel("nominalInterestRateMinimumFeedback",
                    this.nominalInterestRateMinimumField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMinimumFeedback);

            this.nominalInterestRateDefaultField = new TextField<>("nominalInterestRateDefaultField",
                    new PropertyModel<>(this, "nominalInterestRateDefaultValue"));
            this.nominalInterestRateDefaultField.setRequired(true);
            this.nominalInterestRateDefaultField.add(new OnChangeAjaxBehavior());
            this.nominalInterestRateContainer.add(this.nominalInterestRateDefaultField);
            this.nominalInterestRateDefaultFeedback = new TextFeedbackPanel("nominalInterestRateDefaultFeedback",
                    this.nominalInterestRateDefaultField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateDefaultFeedback);

            this.nominalInterestRateMaximumField = new TextField<>("nominalInterestRateMaximumField",
                    new PropertyModel<>(this, "nominalInterestRateMaximumValue"));
            this.nominalInterestRateMaximumField.setRequired(false);
            this.nominalInterestRateMaximumField.add(new OnChangeAjaxBehavior());
            this.nominalInterestRateContainer.add(this.nominalInterestRateMaximumField);
            this.nominalInterestRateMaximumFeedback = new TextFeedbackPanel("nominalInterestRateMaximumFeedback",
                    this.nominalInterestRateMaximumField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMaximumFeedback);

            this.nominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
            this.nominalInterestRateTypeField = new Select2SingleChoice<>("nominalInterestRateTypeField", 0,
                    new PropertyModel<>(this, "nominalInterestRateTypeValue"), this.nominalInterestRateTypeProvider);
            this.nominalInterestRateTypeField.setRequired(true);
            this.nominalInterestRateTypeField.add(new OnChangeAjaxBehavior());
            this.nominalInterestRateContainer.add(this.nominalInterestRateTypeField);
            this.nominalInterestRateTypeFeedback = new TextFeedbackPanel("nominalInterestRateTypeFeedback",
                    this.nominalInterestRateTypeField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateTypeFeedback);
        }

        this.floatInterestRateContainer = new WebMarkupContainer("floatInterestRateContainer");
        this.form.add(this.floatInterestRateContainer);
        {
            this.floatingInterestRateProvider = new SingleChoiceProvider("m_floating_rates", "id", "name");
            this.floatingInterestRateField = new Select2SingleChoice<>("floatingInterestRateField", 0,
                    new PropertyModel<>(this, "floatingInterestRateValue"), this.floatingInterestRateProvider);
            this.floatingInterestRateField.setRequired(false);
            this.floatingInterestRateField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestRateField);
            this.floatingInterestRateFeedback = new TextFeedbackPanel("floatingInterestRateFeedback",
                    this.floatingInterestRateField);
            this.floatInterestRateContainer.add(this.floatingInterestRateFeedback);

            this.floatingInterestMinimumField = new TextField<>("floatingInterestMinimumField",
                    new PropertyModel<>(this, "floatingInterestMinimumValue"));
            this.floatingInterestMinimumField.setRequired(false);
            this.floatingInterestMinimumField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestMinimumField);
            this.floatingInterestMinimumFeedback = new TextFeedbackPanel("floatingInterestMinimumFeedback",
                    this.floatingInterestMinimumField);
            this.floatInterestRateContainer.add(this.floatingInterestMinimumFeedback);

            this.floatingInterestDefaultField = new TextField<>("floatingInterestDefaultField",
                    new PropertyModel<>(this, "floatingInterestDefaultValue"));
            this.floatingInterestDefaultField.setRequired(false);
            this.floatingInterestDefaultField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestDefaultField);
            this.floatingInterestDefaultFeedback = new TextFeedbackPanel("floatingInterestDefaultFeedback",
                    this.floatingInterestDefaultField);
            this.floatInterestRateContainer.add(this.floatingInterestDefaultFeedback);

            this.floatingInterestMaximumField = new TextField<>("floatingInterestMaximumField",
                    new PropertyModel<>(this, "floatingInterestMaximumValue"));
            this.floatingInterestMaximumField.setRequired(false);
            this.floatingInterestMaximumField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestMaximumField);
            this.floatingInterestMaximumFeedback = new TextFeedbackPanel("floatingInterestMaximumFeedback",
                    this.floatingInterestMaximumField);
            this.floatInterestRateContainer.add(this.floatingInterestMaximumFeedback);

            this.floatingInterestDifferentialField = new TextField<>("floatingInterestDifferentialField",
                    new PropertyModel<>(this, "floatingInterestDifferentialValue"));
            this.floatingInterestDifferentialField.setRequired(false);
            this.floatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialField);
            this.floatingInterestDifferentialFeedback = new TextFeedbackPanel("floatingInterestDifferentialFeedback",
                    this.floatingInterestDifferentialField);
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialFeedback);

            this.floatingInterestAllowedField = new CheckBox("floatingInterestAllowedField",
                    new PropertyModel<>(this, "floatingInterestAllowedValue"));
            this.floatingInterestAllowedField.setRequired(false);
            this.floatingInterestAllowedField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestAllowedField);
            this.floatingInterestAllowedFeedback = new TextFeedbackPanel("floatingInterestAllowedFeedback",
                    this.floatingInterestAllowedField);
            this.floatInterestRateContainer.add(this.floatingInterestAllowedFeedback);
        }

        {
            this.nominalInterestRateByLoanCycleContainer = new WebMarkupContainer(
                    "nominalInterestRateByLoanCycleContainer");
            this.form.add(this.nominalInterestRateByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> nominalInterestRateByLoanCycleColumn = Lists.newArrayList();
            nominalInterestRateByLoanCycleColumn.add(
                    new TextColumn(Model.of("When"), "when", "when", this::nominalInterestRateByLoanCycleWhenColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle",
                    this::nominalInterestRateByLoanCycleCycleColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum",
                    this::nominalInterestRateByLoanCycleMinimumColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default",
                    this::nominalInterestRateByLoanCycleDefaultColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum",
                    this::nominalInterestRateByLoanCycleMaximumColumn));
            nominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::nominalInterestRateByLoanCycleActionItem, this::nominalInterestRateByLoanCycleActionClick));
            this.nominalInterestRateByLoanCycleProvider = new ListDataProvider(
                    this.nominalInterestRateByLoanCycleValue);
            this.nominalInterestRateByLoanCycleTable = new DataTable<>("nominalInterestRateByLoanCycleTable",
                    nominalInterestRateByLoanCycleColumn, this.nominalInterestRateByLoanCycleProvider, 20);
            this.nominalInterestRateByLoanCycleContainer.add(this.nominalInterestRateByLoanCycleTable);
            this.nominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(
                    this.nominalInterestRateByLoanCycleTable, this.nominalInterestRateByLoanCycleProvider));
            this.nominalInterestRateByLoanCycleTable
                    .addBottomToolbar(new NoRecordsToolbar(this.nominalInterestRateByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::nominalInterestRateByLoanCycleAddLinkClick);
            this.nominalInterestRateByLoanCycleContainer.add(addLink);
        }

        this.repaidEveryField = new TextField<>("repaidEveryField", new PropertyModel<>(this, "repaidEveryValue"));
        this.repaidEveryField.setRequired(true);
        this.repaidEveryField.add(new OnChangeAjaxBehavior());
        this.form.add(this.repaidEveryField);
        this.repaidEveryFeedback = new TextFeedbackPanel("repaidEveryFeedback", this.repaidEveryField);
        this.form.add(this.repaidEveryFeedback);

        this.repaidTypeProvider = new RepaidTypeProvider();
        this.repaidTypeField = new Select2SingleChoice<>("repaidTypeField", 0,
                new PropertyModel<>(this, "repaidTypeValue"), this.repaidTypeProvider);
        this.repaidTypeField.setRequired(true);
        this.repaidTypeField.add(new OnChangeAjaxBehavior());
        this.form.add(this.repaidTypeField);
        this.repaidTypeFeedback = new TextFeedbackPanel("repaidTypeFeedback", this.repaidTypeField);
        this.form.add(this.repaidTypeFeedback);

        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>(
                "minimumDayBetweenDisbursalAndFirstRepaymentDateField",
                new PropertyModel<>(this, "minimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField.setRequired(false);
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel(
                "minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback",
                this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);
    }

    protected boolean nominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.loanCycleValue = this.nominalInterestRateByLoanCycleValue;
        this.loanCyclePopup.show(target);
        return false;
    }

    protected ItemPanel nominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel nominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel nominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel nominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel nominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void nominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.nominalInterestRateByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.nominalInterestRateByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.nominalInterestRateByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.nominalInterestRateByLoanCycleTable);
    }

    protected List<ActionItem> nominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean numberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.loanCycleValue = this.numberOfRepaymentByLoanCycleValue;
        this.loanCyclePopup.show(target);
        return false;
    }

    protected ItemPanel numberOfRepaymentByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel numberOfRepaymentByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel numberOfRepaymentByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel numberOfRepaymentByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel numberOfRepaymentByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void numberOfRepaymentByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.numberOfRepaymentByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.numberOfRepaymentByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.numberOfRepaymentByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.numberOfRepaymentByLoanCycleTable);
    }

    protected List<ActionItem> numberOfRepaymentByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean principalByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.loanCycleValue = this.principalByLoanCycleValue;
        this.loanCyclePopup.show(target);
        return false;
    }

    protected ItemPanel principalByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel principalByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel principalByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel principalByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel principalByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void principalByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.principalByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.principalByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.principalByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.principalByLoanCycleTable);
    }

    protected List<ActionItem> principalByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean linkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        this.nominalInterestRateContainer.setVisible(
                this.linkedToFloatingInterestRatesValue == null ? true : !this.linkedToFloatingInterestRatesValue);
        this.floatInterestRateContainer.setVisible(!this.nominalInterestRateContainer.isVisible());
        target.add(this.form);
        return false;
    }

    protected boolean interestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
        this.calculateInterestForExactDaysInPartialPeriodContainer
                .setVisible(this.interestCalculationPeriodValue != null && InterestCalculationPeriod.valueOf(
                        this.interestCalculationPeriodValue.getId()) == InterestCalculationPeriod.SameAsPayment);
        target.add(this.form);
        return false;
    }

    protected boolean termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        this.principalByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.numberOfRepaymentByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.nominalInterestRateByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        target.add(this.form);
        return false;
    }

    protected boolean variableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
        this.variableInstallmentsAllowedContainer
                .setVisible(this.variableInstallmentsAllowedValue != null && this.variableInstallmentsAllowedValue);
        target.add(this.form);
        return false;
    }

    protected void loanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("valueConditionType", WhenType.valueOf(this.itemWhenValue.getId()));
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.loanCycleValue.add(item);
        this.loanCycleValue = null;
        target.add(this.form);
    }

    protected void paymentPopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("paymentId", this.itemPaymentValue.getId());
        item.put("payment", this.itemPaymentValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.fundSourceValue.add(item);
        target.add(this.form);
    }

    protected void feeChargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.feeIncomeValue.add(item);
        target.add(this.form);
    }

    protected void penaltyChargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.penaltyIncomeValue.add(item);
        target.add(this.form);
    }

    protected void overdueChargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String chargeId = this.itemOverdueChargeValue.getId();
        for (Map<String, Object> temp : this.overdueChargeValue) {
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
        this.overdueChargeValue.add(item);
        target.add(this.form);
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
        target.add(this.form);
    }

    private void saveButtonSubmit(Button button) {
        LoanBuilder builder = new LoanBuilder();

        // Detail
        builder.withName(this.productNameValue);
        builder.withShortName(this.shortNameValue);
        builder.withDescription(this.descriptionValue);
        if (this.fundValue != null) {
            builder.withFundId(this.fundValue.getId());
        }
        builder.withIncludeInBorrowerCycle(this.includeInCustomerLoanCounterValue);
        builder.withStartDate(this.startDateValue);
        builder.withCloseDate(this.closeDateValue);

        // Currency
        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        }
        builder.withDigitsAfterDecimal(this.decimalPlaceValue);
        builder.withInMultiplesOf(this.currencyInMultipleOfValue);
        builder.withInstallmentAmountInMultiplesOf(this.installmentInMultipleOfValue);

        // Terms

        boolean useBorrowerCycle = this.termVaryBasedOnLoanCycleValue == null ? false
                : this.termVaryBasedOnLoanCycleValue;
        builder.withUseBorrowerCycle(useBorrowerCycle);
        builder.withMinPrincipal(this.principalMinimumValue);
        builder.withPrincipal(this.principalDefaultValue);
        builder.withMaxPrincipal(this.principalMaximumValue);
        builder.withMinNumberOfRepayments(this.numberOfRepaymentMinimumValue);
        builder.withNumberOfRepayments(this.numberOfRepaymentDefaultValue);
        builder.withMaxNumberOfRepayments(this.numberOfRepaymentMaximumValue);

        boolean linkedToFloatingInterestRates = this.linkedToFloatingInterestRatesValue == null ? false
                : this.linkedToFloatingInterestRatesValue;
        builder.withLinkedToFloatingInterestRates(linkedToFloatingInterestRates);

        if (linkedToFloatingInterestRates) {
            builder.withMinDifferentialLendingRate(this.floatingInterestMinimumValue);
            builder.withDefaultDifferentialLendingRate(this.floatingInterestDefaultValue);
            builder.withMaxDifferentialLendingRate(this.floatingInterestMaximumValue);
            if (this.floatingInterestRateValue != null) {
                builder.withFloatingRatesId(this.floatingInterestRateValue.getId());
            }
            builder.withInterestRateDifferential(this.floatingInterestDifferentialValue);
            builder.withFloatingInterestRateCalculationAllowed(
                    this.floatingInterestAllowedValue == null ? false : this.floatingInterestAllowedValue);
        } else {
            builder.withMinInterestRatePerPeriod(this.nominalInterestRateMinimumValue);
            builder.withInterestRatePerPeriod(this.nominalInterestRateDefaultValue);
            builder.withMaxInterestRatePerPeriod(this.nominalInterestRateMaximumValue);
            if (this.nominalInterestRateTypeValue != null) {
                builder.withInterestRateFrequencyType(
                        NominalInterestRateScheduleType.valueOf(this.nominalInterestRateTypeValue.getId()));
            }
        }

        if (useBorrowerCycle) {
            if (this.principalByLoanCycleValue != null) {
                for (Map<String, Object> item : this.principalByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withPrincipalVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue,
                            defaultValue, maxValue);
                }
            }
            if (this.numberOfRepaymentByLoanCycleValue != null) {
                for (Map<String, Object> item : this.numberOfRepaymentByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withNumberOfRepaymentVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber,
                            minValue, defaultValue, maxValue);
                }
            }
            if (this.nominalInterestRateByLoanCycleValue != null) {
                for (Map<String, Object> item : this.nominalInterestRateByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withInterestRateVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber,
                            minValue, defaultValue, maxValue);
                }
            }
        }

        builder.withRepaymentEvery(this.repaidEveryValue);
        if (this.repaidTypeValue != null) {
            builder.withRepaymentFrequencyType(RepaidType.valueOf(this.repaidTypeValue.getId()));
        }
        builder.withMinimumDaysBetweenDisbursalAndFirstRepayment(
                this.minimumDayBetweenDisbursalAndFirstRepaymentDateValue);

        // Settings

        if (this.amortizationValue != null) {
            builder.withAmortizationType(Amortization.valueOf(this.amortizationValue.getId()));
        }
        if (this.interestMethodValue != null) {
            builder.withInterestType(InterestMethod.valueOf(this.interestMethodValue.getId()));
        }
        if (this.interestCalculationPeriodValue != null) {
            InterestCalculationPeriod interestCalculationPeriod = InterestCalculationPeriod
                    .valueOf(this.interestCalculationPeriodValue.getId());
            builder.withInterestCalculationPeriodType(interestCalculationPeriod);
            if (interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
                builder.withAllowPartialPeriodInterestCalcualtion(
                        this.calculateInterestForExactDaysInPartialPeriodValue == null ? false
                                : this.calculateInterestForExactDaysInPartialPeriodValue);
            }
        }
        builder.withGraceOnPrincipalPayment(this.moratoriumPrincipalValue);
        builder.withGraceOnInterestPayment(this.moratoriumInterestValue);
        builder.withGraceOnInterestCharged(this.interestFreePeriodValue);
        builder.withInArrearsTolerance(this.arrearsToleranceValue);

        builder.withCanDefineInstallmentAmount(this.allowFixingOfTheInstallmentAmountValue == null ? false
                : this.allowFixingOfTheInstallmentAmountValue);

        builder.withGraceOnArrearsAgeing(this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue);

        builder.withOverdueDaysForNPA(this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue);

        builder.withMinimumGap(this.variableInstallmentsMinimumValue);
        builder.withMaximumGap(this.variableInstallmentsMaximumValue);
        builder.withAccountMovesOutOfNPAOnlyOnArrearsCompletion(
                this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue == null ? false
                        : this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue);
        builder.withAllowVariableInstallments(
                this.variableInstallmentsAllowedValue == null ? false : this.variableInstallmentsAllowedValue);
        builder.withCanUseForTopup(this.allowedToBeUsedForProvidingTopupLoansValue == null ? false
                : this.allowedToBeUsedForProvidingTopupLoansValue);

        if (this.repaymentStrategyValue != null) {
            builder.withTransactionProcessingStrategyId(RepaymentStrategy.valueOf(this.repaymentStrategyValue.getId()));
        }

        if (this.dayInYearValue != null) {
            builder.withDaysInYearType(DayInYear.valueOf(this.dayInYearValue.getId()));
        }

        if (this.dayInMonthValue != null) {
            builder.withDaysInMonthType(DayInMonth.valueOf(this.dayInMonthValue.getId()));
        }

        builder.withPrincipalThresholdForLastInstallment(this.principalThresholdForLastInstalmentValue);

        // Interest Recalculation

        boolean interestRecalculationEnabled = this.recalculateInterestValue == null ? false
                : this.recalculateInterestValue;
        builder.withInterestRecalculationEnabled(interestRecalculationEnabled);
        if (interestRecalculationEnabled) {
            if (this.preClosureInterestCalculationRuleValue != null) {
                builder.withPreClosureInterestCalculationStrategy(
                        ClosureInterestCalculationRule.valueOf(this.preClosureInterestCalculationRuleValue.getId()));
            }
            if (this.advancePaymentsAdjustmentTypeValue != null) {
                builder.withRescheduleStrategyMethod(
                        AdvancePaymentsAdjustmentType.valueOf(this.advancePaymentsAdjustmentTypeValue.getId()));
            }

            if (this.interestRecalculationCompoundingOnValue != null) {
                InterestRecalculationCompound interestRecalculationCompound = InterestRecalculationCompound
                        .valueOf(this.interestRecalculationCompoundingOnValue.getId());
                builder.withInterestRecalculationCompoundingMethod(interestRecalculationCompound);

                if (interestRecalculationCompound == InterestRecalculationCompound.Fee
                        || interestRecalculationCompound == InterestRecalculationCompound.Interest
                        || interestRecalculationCompound == InterestRecalculationCompound.FeeAndInterest) {
                    if (this.compoundingValue != null) {
                        Frequency compoundingValue = Frequency.valueOf(this.compoundingValue.getId());
                        builder.withRecalculationCompoundingFrequencyType(compoundingValue);
                        if (compoundingValue == Frequency.Daily) {
                            builder.withRecalculationCompoundingFrequencyInterval(this.compoundingIntervalValue);
                        } else if (compoundingValue == Frequency.Weekly) {
                            builder.withRecalculationCompoundingFrequencyInterval(this.compoundingIntervalValue);
                            if (this.compoundingDayValue != null) {
                                builder.withRecalculationCompoundingFrequencyDayOfWeekType(
                                        FrequencyDay.valueOf(this.compoundingDayValue.getId()));
                            }
                        } else if (compoundingValue == Frequency.Monthly) {
                            builder.withRecalculationCompoundingFrequencyInterval(this.compoundingIntervalValue);
                            if (this.compoundingTypeValue != null) {
                                builder.withRecalculationCompoundingFrequencyNthDayType(
                                        FrequencyType.valueOf(this.compoundingTypeValue.getId()));
                            }
                            if (this.compoundingDayValue != null) {
                                builder.withRecalculationCompoundingFrequencyDayOfWeekType(
                                        FrequencyDay.valueOf(this.compoundingDayValue.getId()));
                            }
                        }
                    }
                }

                if (this.recalculateValue != null) {
                    Frequency recalculateValue = Frequency.valueOf(this.recalculateValue.getId());
                    builder.withRecalculationRestFrequencyType(recalculateValue);
                    if (recalculateValue == Frequency.Daily) {
                        builder.withRecalculationRestFrequencyInterval(this.recalculateIntervalValue);
                    } else if (recalculateValue == Frequency.Weekly) {
                        if (this.recalculateDayValue != null) {
                            builder.withRecalculationRestFrequencyDayOfWeekType(
                                    FrequencyDay.valueOf(this.recalculateDayValue.getId()));
                        }
                        builder.withRecalculationRestFrequencyInterval(this.recalculateIntervalValue);
                    } else if (recalculateValue == Frequency.Monthly) {
                        if (this.recalculateTypeValue != null) {
                            builder.withRecalculationRestFrequencyNthDayType(
                                    FrequencyType.valueOf(this.recalculateTypeValue.getId()));
                        }
                        if (this.recalculateDayValue != null) {
                            builder.withRecalculationRestFrequencyDayOfWeekType(
                                    FrequencyDay.valueOf(this.recalculateDayValue.getId()));
                        }
                        builder.withRecalculationRestFrequencyInterval(this.recalculateIntervalValue);
                    }
                }
                builder.withArrearsBasedOnOriginalSchedule(this.arrearsRecognizationBasedOnOriginalScheduleValue == null
                        ? false : this.arrearsRecognizationBasedOnOriginalScheduleValue);
            }

        }

        // Guarantee Requirements

        boolean holdGuaranteeFunds = this.placeGuaranteeFundsOnHoldValue == null ? false
                : this.placeGuaranteeFundsOnHoldValue;
        builder.withHoldGuaranteeFunds(holdGuaranteeFunds);
        if (holdGuaranteeFunds) {
            builder.withMandatoryGuarantee(this.mandatoryGuaranteeValue);
            builder.withMinimumGuaranteeFromGuarantor(this.minimumGuaranteeFromGuarantorValue);
            builder.withMinimumGuaranteeFromOwnFunds(this.minimumGuaranteeValue);
        }

        // Loan Tranche Details

        boolean multiDisburseLoan = this.enableMultipleDisbursalValue == null ? false
                : this.enableMultipleDisbursalValue;
        builder.withMultiDisburseLoan(multiDisburseLoan);
        if (multiDisburseLoan) {
            builder.withOutstandingLoanBalance(this.maximumAllowedOutstandingBalanceValue);
            builder.withMaxTrancheCount(this.maximumTrancheCountValue);
        }

        // Configurable Terms and Settings
        boolean configurable = this.allowOverridingSelectTermsAndSettingsInLoanAccountValue == null ? false
                : this.allowOverridingSelectTermsAndSettingsInLoanAccountValue;
        AllowAttributeOverrideBuilder allowAttributeOverrideBuilder = new AllowAttributeOverrideBuilder();
        if (configurable) {
            allowAttributeOverrideBuilder.withAmortizationType(
                    this.configurableAmortizationValue == null ? false : this.configurableAmortizationValue);
            allowAttributeOverrideBuilder.withTransactionProcessingStrategyId(
                    this.configurableRepaymentStrategyValue == null ? false : this.configurableRepaymentStrategyValue);
            allowAttributeOverrideBuilder.withInArrearsTolerance(
                    this.configurableArrearsToleranceValue == null ? false : this.configurableArrearsToleranceValue);
            allowAttributeOverrideBuilder.withGraceOnPrincipalAndInterestPayment(
                    this.configurableMoratoriumValue == null ? false : this.configurableMoratoriumValue);
            allowAttributeOverrideBuilder.withInterestType(
                    this.configurableInterestMethodValue == null ? false : this.configurableInterestMethodValue);
            allowAttributeOverrideBuilder
                    .withInterestCalculationPeriodType(this.configurableInterestCalculationPeriodValue == null ? false
                            : this.configurableInterestCalculationPeriodValue);
            allowAttributeOverrideBuilder.withRepaymentEvery(
                    this.configurableRepaidEveryValue == null ? false : this.configurableRepaidEveryValue);
            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(this.configurableOverdueBeforeMovingValue == null
                    ? false : this.configurableOverdueBeforeMovingValue);
        } else {
            allowAttributeOverrideBuilder.withAmortizationType(false);
            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(false);
            allowAttributeOverrideBuilder.withGraceOnPrincipalAndInterestPayment(false);
            allowAttributeOverrideBuilder.withInArrearsTolerance(false);
            allowAttributeOverrideBuilder.withInterestCalculationPeriodType(false);
            allowAttributeOverrideBuilder.withInterestType(false);
            allowAttributeOverrideBuilder.withRepaymentEvery(false);
            allowAttributeOverrideBuilder.withTransactionProcessingStrategyId(false);
        }
        JsonNode allowAttributeOverrides = allowAttributeOverrideBuilder.build();
        builder.withAllowAttributeOverrides(allowAttributeOverrides.getObject());

        // Charge

        if (this.chargeValue != null && !this.chargeValue.isEmpty()) {
            for (Map<String, Object> item : this.chargeValue) {
                builder.withCharges((String) item.get("chargeId"));
            }
        }

        // Overdue Charge

        if (this.overdueChargeValue != null && !this.overdueChargeValue.isEmpty()) {
            for (Map<String, Object> item : this.overdueChargeValue) {
                builder.withCharges((String) item.get("chargeId"));
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (ACC_NONE.equals(accounting)) {
            builder.withAccountingRule(1);
        } else if (ACC_CASH.equals(accounting)) {
            builder.withAccountingRule(2);
        } else if (ACC_PERIODIC.equals(accounting)) {
            builder.withAccountingRule(3);
        } else if (ACC_UPFRONT.equals(accounting)) {
            builder.withAccountingRule(4);
        }
        if (ACC_CASH.equals(accounting)) {
            if (this.cashFundSourceValue != null) {
                builder.withFundSourceAccountId(this.cashFundSourceValue.getId());
            }
            if (this.cashLoanPortfolioValue != null) {
                builder.withLoanPortfolioAccountId(this.cashLoanPortfolioValue.getId());
            }
            if (this.cashTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashTransferInSuspenseValue.getId());
            }
            if (this.cashIncomeFromInterestValue != null) {
                builder.withInterestOnLoanAccountId(this.cashIncomeFromInterestValue.getId());
            }
            if (this.cashIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
            }
            if (this.cashIncomeFromPenaltiesValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltiesValue.getId());
            }
            if (this.cashIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.cashIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.cashLossesWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.cashLossesWrittenOffValue.getId());
            }
            if (this.cashOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.cashOverPaymentLiabilityValue.getId());
            }
        } else if (ACC_PERIODIC.equals(accounting)) {
            if (this.periodicFundSourceValue != null) {
                builder.withFundSourceAccountId(this.periodicFundSourceValue.getId());
            }
            if (this.periodicLoanPortfolioValue != null) {
                builder.withLoanPortfolioAccountId(this.periodicLoanPortfolioValue.getId());
            }
            if (this.periodicTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.periodicTransferInSuspenseValue.getId());
            }
            if (this.periodicIncomeFromInterestValue != null) {
                builder.withInterestOnLoanAccountId(this.periodicIncomeFromInterestValue.getId());
            }
            if (this.periodicIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.periodicIncomeFromFeeValue.getId());
            }
            if (this.periodicIncomeFromPenaltiesValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.periodicIncomeFromPenaltiesValue.getId());
            }
            if (this.periodicIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.periodicIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.periodicLossesWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.periodicLossesWrittenOffValue.getId());
            }
            if (this.periodicOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.periodicOverPaymentLiabilityValue.getId());
            }
            if (this.periodicInterestReceivableValue != null) {
                builder.withReceivableInterestAccountId(this.periodicInterestReceivableValue.getId());
            }
            if (this.periodicFeesReceivableValue != null) {
                builder.withReceivableFeeAccountId(this.periodicFeesReceivableValue.getId());
            }
            if (this.periodicPenaltiesReceivableValue != null) {
                builder.withReceivablePenaltyAccountId(this.periodicPenaltiesReceivableValue.getId());
            }
        } else if (ACC_UPFRONT.equals(accounting)) {
            if (this.upfrontFundSourceValue != null) {
                builder.withFundSourceAccountId(this.upfrontFundSourceValue.getId());
            }
            if (this.upfrontLoanPortfolioValue != null) {
                builder.withLoanPortfolioAccountId(this.upfrontLoanPortfolioValue.getId());
            }
            if (this.upfrontTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.upfrontTransferInSuspenseValue.getId());
            }
            if (this.upfrontIncomeFromInterestValue != null) {
                builder.withInterestOnLoanAccountId(this.upfrontIncomeFromInterestValue.getId());
            }
            if (this.upfrontIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.upfrontIncomeFromFeeValue.getId());
            }
            if (this.upfrontIncomeFromPenaltiesValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.upfrontIncomeFromPenaltiesValue.getId());
            }
            if (this.upfrontIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.upfrontIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.upfrontLossesWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.upfrontLossesWrittenOffValue.getId());
            }
            if (this.upfrontOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.upfrontOverPaymentLiabilityValue.getId());
            }
            if (this.upfrontInterestReceivableValue != null) {
                builder.withReceivableInterestAccountId(this.upfrontInterestReceivableValue.getId());
            }
            if (this.upfrontFeesReceivableValue != null) {
                builder.withReceivableFeeAccountId(this.upfrontFeesReceivableValue.getId());
            }
            if (this.upfrontPenaltiesReceivableValue != null) {
                builder.withReceivablePenaltyAccountId(this.upfrontPenaltiesReceivableValue.getId());
            }
        }

        if (ACC_CASH.equals(accounting) || ACC_PERIODIC.equals(accounting) || ACC_UPFRONT.equals(accounting)) {
            if (this.fundSourceValue != null && !this.fundSourceValue.isEmpty()) {
                for (Map<String, Object> item : this.fundSourceValue) {
                    builder.withPaymentChannelToFundSourceMappings((String) item.get("paymentId"),
                            (String) item.get("accountId"));
                }
            }
            if (this.feeIncomeValue != null && !this.feeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.feeIncomeValue) {
                    builder.withFeeToIncomeAccountMappings((String) item.get("chargeId"),
                            (String) item.get("accountId"));
                }
            }
            if (this.penaltyIncomeValue != null && !this.penaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.penaltyIncomeValue) {
                    builder.withPenaltyToIncomeAccountMappings((String) item.get("chargeId"),
                            (String) item.get("accountId"));
                }
            }
        }

        JsonNode node = null;
        try {
            node = LoanHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(LoanBrowsePage.class);
    }

}
