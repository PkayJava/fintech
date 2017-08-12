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
import com.angkorteam.fintech.dto.Frequency;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.InterestRecalculationCompound;
import com.angkorteam.fintech.popup.LoanCyclePopup;
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

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    // Loan Product Detail
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
    private List<Map<String, Object>> loanCycleValue;
    private ModalWindow loanCyclePopup;

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

    private String accountingValue;
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

    private SingleChoiceProvider cashIncomeFromRecoveryPaymentProvider;
    private Option cashIncomeFromRecoveryPaymentValue;
    private Select2SingleChoice<Option> cashIncomeFromRecoveryPaymentField;
    private TextFeedbackPanel cashIncomeFromRecoveryPaymentFeedback;

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

    private SingleChoiceProvider periodicIncomeFromFeesProvider;
    private Option periodicIncomeFromFeesValue;
    private Select2SingleChoice<Option> periodicIncomeFromFeesField;
    private TextFeedbackPanel periodicIncomeFromFeesFeedback;

    private SingleChoiceProvider periodicIncomeFromPenaltiesProvider;
    private Option periodicIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> periodicIncomeFromPenaltiesField;
    private TextFeedbackPanel periodicIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider periodicIncomeFromRecoveryRepaymentsProvider;
    private Option periodicIncomeFromRecoveryRepaymentsValue;
    private Select2SingleChoice<Option> periodicIncomeFromRecoveryRepaymentsField;
    private TextFeedbackPanel periodicIncomeFromRecoveryRepaymentsFeedback;

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

    private SingleChoiceProvider upfrontIncomeFromFeesProvider;
    private Option upfrontIncomeFromFeesValue;
    private Select2SingleChoice<Option> upfrontIncomeFromFeesField;
    private TextFeedbackPanel upfrontIncomeFromFeesFeedback;

    private SingleChoiceProvider upfrontIncomeFromPenaltiesProvider;
    private Option upfrontIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> upfrontIncomeFromPenaltiesField;
    private TextFeedbackPanel upfrontIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider upfrontIncomeFromRecoveryRepaymentsProvider;
    private Option upfrontIncomeFromRecoveryRepaymentsValue;
    private Select2SingleChoice<Option> upfrontIncomeFromRecoveryRepaymentsField;
    private TextFeedbackPanel upfrontIncomeFromRecoveryRepaymentsFeedback;

    private SingleChoiceProvider upfrontLossesWrittenOffProvider;
    private Option upfrontLossesWrittenOffValue;
    private Select2SingleChoice<Option> upfrontLossesWrittenOffField;
    private TextFeedbackPanel upfrontLossesWrittenOffFeedback;

    private SingleChoiceProvider upfrontOverPaymentLiabilityProvider;
    private Option upfrontOverPaymentLiabilityValue;
    private Select2SingleChoice<Option> upfrontOverPaymentLiabilityField;
    private TextFeedbackPanel upfrontOverPaymentLiabilityFeedback;

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

    private List<Map<String, Object>> chargesValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargesTable;
    private ListDataProvider chargesProvider;

    // Overdue Charges

    private List<Map<String, Object>> overdueChargesValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> overdueChargesTable;
    private ListDataProvider overdueChargesProvider;

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

        initAccounting();

        initCharges();

        initOverdueCharges();

        initDefault();
    }

    protected void saveButtonSubmit(Button button) {

    }

    protected void initOverdueCharges() {
        List<IColumn<Map<String, Object>, String>> overdueChargesColumn = Lists.newArrayList();
        overdueChargesColumn.add(new TextColumn(Model.of("When"), "when", "when", this::overdueChargesWhenColumn));
        overdueChargesColumn
                .add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::overdueChargesCycleColumn));
        overdueChargesColumn
                .add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::overdueChargesMinimumColumn));
        overdueChargesColumn
                .add(new TextColumn(Model.of("Default"), "default", "default", this::overdueChargesDefaultColumn));
        overdueChargesColumn
                .add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::overdueChargesMaximumColumn));
        overdueChargesColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::overdueChargesActionItem,
                this::overdueChargesActionClick));
        this.overdueChargesProvider = new ListDataProvider(this.overdueChargesValue);
        this.overdueChargesTable = new DataTable<>("overdueChargesTable", overdueChargesColumn,
                this.overdueChargesProvider, 20);
        this.form.add(this.overdueChargesTable);
        this.overdueChargesTable
                .addTopToolbar(new HeadersToolbar<>(this.overdueChargesTable, this.overdueChargesProvider));
        this.overdueChargesTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargesTable));

        AjaxLink<Void> overdueChargesAddLink = new AjaxLink<>("overdueChargesAddLink");
        overdueChargesAddLink.setOnClick(this::overdueChargesAddLinkClick);
        this.form.add(overdueChargesAddLink);
    }

    private void overdueChargesAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.overdueChargesValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel overdueChargesWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel overdueChargesCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel overdueChargesMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel overdueChargesDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel overdueChargesMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void overdueChargesActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.overdueChargesValue.size(); i++) {
            Map<String, Object> column = this.overdueChargesValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.overdueChargesValue.remove(index);
        }
        ajaxRequestTarget.add(this.overdueChargesTable);
    }

    private List<ActionItem> overdueChargesActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initCharges() {
        List<IColumn<Map<String, Object>, String>> chargesColumn = Lists.newArrayList();
        chargesColumn.add(new TextColumn(Model.of("When"), "when", "when", this::chargesWhenColumn));
        chargesColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::chargesCycleColumn));
        chargesColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::chargesMinimumColumn));
        chargesColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::chargesDefaultColumn));
        chargesColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::chargesMaximumColumn));
        chargesColumn
                .add(new ActionFilterColumn<>(Model.of("Action"), this::chargesActionItem, this::chargesActionClick));
        this.chargesProvider = new ListDataProvider(this.chargesValue);
        this.chargesTable = new DataTable<>("chargesTable", chargesColumn, this.chargesProvider, 20);
        this.form.add(this.chargesTable);
        this.chargesTable.addTopToolbar(new HeadersToolbar<>(this.chargesTable, this.chargesProvider));
        this.chargesTable.addBottomToolbar(new NoRecordsToolbar(this.chargesTable));

        AjaxLink<Void> chargesAddLink = new AjaxLink<>("chargesAddLink");
        chargesAddLink.setOnClick(this::chargesAddLinkClick);
        this.form.add(chargesAddLink);
    }

    private void chargesAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.chargesValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel chargesWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel chargesCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel chargesMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel chargesDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel chargesMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void chargesActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.chargesValue.size(); i++) {
            Map<String, Object> column = this.chargesValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargesValue.remove(index);
        }
        ajaxRequestTarget.add(this.chargesTable);
    }

    private List<ActionItem> chargesActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>("None")));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>("Cash")));
        this.accountingField.add(new Radio<>("accountingPeriodic", new Model<>("Periodic")));
        this.accountingField.add(new Radio<>("accountingUpfront", new Model<>("Upfront")));
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
        this.upfrontFundSourceField.setRequired(true);
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
        this.upfrontLoanPortfolioField.setRequired(true);
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
        this.upfrontInterestReceivableField.setRequired(true);
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
        this.upfrontFeesReceivableField.setRequired(true);
        this.upfrontFeesReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontFeesReceivableField);
        this.upfrontFeesReceivableFeedback = new TextFeedbackPanel("upfrontFeesReceivableFeedback",
                this.upfrontFeesReceivableField);
        this.upfrontContainer.add(this.upfrontFeesReceivableFeedback);

        this.upfrontPenaltiesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontPenaltiesReceivableProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontPenaltiesReceivableProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.upfrontPenaltiesReceivableField = new Select2SingleChoice<>("upfrontPenaltiesReceivableField",
                new PropertyModel<>(this, "upfrontPenaltiesReceivableValue"),
                this.upfrontPenaltiesReceivableProvider);
        this.upfrontPenaltiesReceivableField.setRequired(true);
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
        this.upfrontTransferInSuspenseField.setRequired(true);
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
        this.upfrontIncomeFromInterestField.setRequired(true);
        this.upfrontIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromInterestField);
        this.upfrontIncomeFromInterestFeedback = new TextFeedbackPanel("upfrontIncomeFromInterestFeedback",
                this.upfrontIncomeFromInterestField);
        this.upfrontContainer.add(this.upfrontIncomeFromInterestFeedback);

        this.upfrontIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromFeesField = new Select2SingleChoice<>("upfrontIncomeFromFeesField",
                new PropertyModel<>(this, "upfrontIncomeFromFeesValue"), this.upfrontIncomeFromFeesProvider);
        this.upfrontIncomeFromFeesField.setRequired(true);
        this.upfrontIncomeFromFeesField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromFeesField);
        this.upfrontIncomeFromFeesFeedback = new TextFeedbackPanel("upfrontIncomeFromFeesFeedback",
                this.upfrontIncomeFromFeesField);
        this.upfrontContainer.add(this.upfrontIncomeFromFeesFeedback);

        this.upfrontIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromPenaltiesField = new Select2SingleChoice<>("upfrontIncomeFromPenaltiesField",
                new PropertyModel<>(this, "upfrontIncomeFromPenaltiesValue"),
                this.upfrontIncomeFromPenaltiesProvider);
        this.upfrontIncomeFromPenaltiesField.setRequired(true);
        this.upfrontIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesField);
        this.upfrontIncomeFromPenaltiesFeedback = new TextFeedbackPanel("upfrontIncomeFromPenaltiesFeedback",
                this.upfrontIncomeFromPenaltiesField);
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesFeedback);

        this.upfrontIncomeFromRecoveryRepaymentsProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromRecoveryRepaymentsProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontIncomeFromRecoveryRepaymentsProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.upfrontIncomeFromRecoveryRepaymentsField = new Select2SingleChoice<>(
                "upfrontIncomeFromRecoveryRepaymentsField",
                new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentsValue"),
                this.upfrontIncomeFromRecoveryRepaymentsProvider);
        this.upfrontIncomeFromRecoveryRepaymentsField.setRequired(true);
        this.upfrontIncomeFromRecoveryRepaymentsField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentsField);
        this.upfrontIncomeFromRecoveryRepaymentsFeedback = new TextFeedbackPanel(
                "upfrontIncomeFromRecoveryRepaymentsFeedback", this.upfrontIncomeFromRecoveryRepaymentsField);
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentsFeedback);

        this.upfrontLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.upfrontLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.upfrontLossesWrittenOffField = new Select2SingleChoice<>("upfrontLossesWrittenOffField",
                new PropertyModel<>(this, "upfrontLossesWrittenOffValue"), this.upfrontLossesWrittenOffProvider);
        this.upfrontLossesWrittenOffField.setRequired(true);
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
        this.upfrontOverPaymentLiabilityField.setRequired(true);
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
        this.cashFundSourceField.setRequired(true);
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashFundSourceField);
        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceField);
        this.cashContainer.add(this.cashFundSourceFeedback);

        this.cashLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLoanPortfolioProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField",
                new PropertyModel<>(this, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioField.setRequired(true);
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
        this.cashTransferInSuspenseField.setRequired(true);
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
        this.cashIncomeFromInterestField.setRequired(true);
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
        this.cashIncomeFromFeeField.setRequired(true);
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
        this.cashIncomeFromPenaltiesField.setRequired(true);
        this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromPenaltiesField);
        this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback",
                this.cashIncomeFromPenaltiesField);
        this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);

        this.cashIncomeFromRecoveryPaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromRecoveryPaymentProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromRecoveryPaymentProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromRecoveryPaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryPaymentField",
                new PropertyModel<>(this, "cashIncomeFromRecoveryPaymentValue"),
                this.cashIncomeFromRecoveryPaymentProvider);
        this.cashIncomeFromRecoveryPaymentField.setRequired(true);
        this.cashIncomeFromRecoveryPaymentField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromRecoveryPaymentField);
        this.cashIncomeFromRecoveryPaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryPaymentFeedback",
                this.cashIncomeFromRecoveryPaymentField);
        this.cashContainer.add(this.cashIncomeFromRecoveryPaymentFeedback);

        this.cashLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.cashLossesWrittenOffField = new Select2SingleChoice<>("cashLossesWrittenOffField",
                new PropertyModel<>(this, "cashLossesWrittenOffValue"), this.cashLossesWrittenOffProvider);
        this.cashLossesWrittenOffField.setRequired(true);
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
        this.cashOverPaymentLiabilityField.setRequired(true);
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
        this.periodicFundSourceField.setRequired(true);
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
        this.periodicLoanPortfolioField.setRequired(true);
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
        this.periodicInterestReceivableField.setRequired(true);
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
        this.periodicFeesReceivableField.setRequired(true);
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
        this.periodicPenaltiesReceivableField.setRequired(true);
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
        this.periodicTransferInSuspenseField.setRequired(true);
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
        this.periodicIncomeFromInterestField.setRequired(true);
        this.periodicIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromInterestField);
        this.periodicIncomeFromInterestFeedback = new TextFeedbackPanel("periodicIncomeFromInterestFeedback",
                this.periodicIncomeFromInterestField);
        this.periodicContainer.add(this.periodicIncomeFromInterestFeedback);

        this.periodicIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromFeesField = new Select2SingleChoice<>("periodicIncomeFromFeesField",
                new PropertyModel<>(this, "periodicIncomeFromFeesValue"), this.periodicIncomeFromFeesProvider);
        this.periodicIncomeFromFeesField.setRequired(true);
        this.periodicIncomeFromFeesField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromFeesField);
        this.periodicIncomeFromFeesFeedback = new TextFeedbackPanel("periodicIncomeFromFeesFeedback",
                this.periodicIncomeFromFeesField);
        this.periodicContainer.add(this.periodicIncomeFromFeesFeedback);

        this.periodicIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromPenaltiesField = new Select2SingleChoice<>("periodicIncomeFromPenaltiesField",
                new PropertyModel<>(this, "periodicIncomeFromPenaltiesValue"),
                this.periodicIncomeFromPenaltiesProvider);
        this.periodicIncomeFromPenaltiesField.setRequired(true);
        this.periodicIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesField);
        this.periodicIncomeFromPenaltiesFeedback = new TextFeedbackPanel("periodicIncomeFromPenaltiesFeedback",
                this.periodicIncomeFromPenaltiesField);
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesFeedback);

        this.periodicIncomeFromRecoveryRepaymentsProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromRecoveryRepaymentsProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicIncomeFromRecoveryRepaymentsProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.periodicIncomeFromRecoveryRepaymentsField = new Select2SingleChoice<>(
                "periodicIncomeFromRecoveryRepaymentsField",
                new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentsValue"),
                this.periodicIncomeFromRecoveryRepaymentsProvider);
        this.periodicIncomeFromRecoveryRepaymentsField.setRequired(true);
        this.periodicIncomeFromRecoveryRepaymentsField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentsField);
        this.periodicIncomeFromRecoveryRepaymentsFeedback = new TextFeedbackPanel(
                "periodicIncomeFromRecoveryRepaymentsFeedback", this.periodicIncomeFromRecoveryRepaymentsField);
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentsFeedback);

        this.periodicLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = 1");
        this.periodicLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = 5");
        this.periodicLossesWrittenOffField = new Select2SingleChoice<>("periodicLossesWrittenOffField",
                new PropertyModel<>(this, "periodicLossesWrittenOffValue"), this.periodicLossesWrittenOffProvider);
        this.periodicLossesWrittenOffField.setRequired(true);
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
        this.periodicOverPaymentLiabilityField.setRequired(true);
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
            fundSourceColumn.add(new TextColumn(Model.of("When"), "when", "when", this::fundSourceWhenColumn));
            fundSourceColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::fundSourceCycleColumn));
            fundSourceColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::fundSourceMinimumColumn));
            fundSourceColumn
                    .add(new TextColumn(Model.of("Default"), "default", "default", this::fundSourceDefaultColumn));
            fundSourceColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::fundSourceMaximumColumn));
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
            feeIncomeColumn.add(new TextColumn(Model.of("When"), "when", "when", this::feeIncomeWhenColumn));
            feeIncomeColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::feeIncomeCycleColumn));
            feeIncomeColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::feeIncomeMinimumColumn));
            feeIncomeColumn
                    .add(new TextColumn(Model.of("Default"), "default", "default", this::feeIncomeDefaultColumn));
            feeIncomeColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::feeIncomeMaximumColumn));
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
            penaltyIncomeColumn.add(new TextColumn(Model.of("When"), "when", "when", this::penaltyIncomeWhenColumn));
            penaltyIncomeColumn
                    .add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::penaltyIncomeCycleColumn));
            penaltyIncomeColumn
                    .add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::penaltyIncomeMinimumColumn));
            penaltyIncomeColumn
                    .add(new TextColumn(Model.of("Default"), "default", "default", this::penaltyIncomeDefaultColumn));
            penaltyIncomeColumn
                    .add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::penaltyIncomeMaximumColumn));
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

    private void feeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.feeIncomeValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel feeIncomeWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel feeIncomeCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel feeIncomeMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel feeIncomeDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel feeIncomeMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void feeIncomeActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> feeIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void penaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.penaltyIncomeValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel penaltyIncomeWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel penaltyIncomeCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel penaltyIncomeMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel penaltyIncomeDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel penaltyIncomeMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void penaltyIncomeActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> penaltyIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void fundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.fundSourceValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel fundSourceWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel fundSourceCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel fundSourceMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel fundSourceDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel fundSourceMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void fundSourceActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> fundSourceActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void accountingFieldUpdate(AjaxRequestTarget target) {
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
    }

    protected void initConfigurableTermsAndSettings() {
        this.allowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox(
                "allowOverridingSelectTermsAndSettingsInLoanAccountField",
                new PropertyModel<>(this, "allowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.allowOverridingSelectTermsAndSettingsInLoanAccountField.setRequired(true);
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
        this.configurableAmortizationField.setRequired(true);
        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback",
                this.configurableAmortizationField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAmortizationFeedback);

        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField",
                new PropertyModel<>(this, "configurableInterestMethodValue"));
        this.configurableInterestMethodField.setRequired(true);
        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback",
                this.configurableInterestMethodField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableInterestMethodFeedback);

        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField",
                new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyField.setRequired(true);
        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback",
                this.configurableRepaymentStrategyField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableRepaymentStrategyFeedback);

        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField",
                new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodField.setRequired(true);
        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel(
                "configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableInterestCalculationPeriodFeedback);

        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField",
                new PropertyModel<>(this, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceField.setRequired(true);
        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback",
                this.configurableArrearsToleranceField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableArrearsToleranceFeedback);

        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField",
                new PropertyModel<>(this, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryField.setRequired(true);
        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback",
                this.configurableRepaidEveryField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableRepaidEveryFeedback);

        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField",
                new PropertyModel<>(this, "configurableMoratoriumValue"));
        this.configurableMoratoriumField.setRequired(true);
        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback",
                this.configurableMoratoriumField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableMoratoriumFeedback);

        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField",
                new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingField.setRequired(true);
        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback",
                this.configurableOverdueBeforeMovingField);
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .add(this.configurableOverdueBeforeMovingFeedback);
    }

    protected void allowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        this.allowOverridingSelectTermsAndSettingsInLoanAccountContainer
                .setVisible(this.allowOverridingSelectTermsAndSettingsInLoanAccountValue != null
                        && this.allowOverridingSelectTermsAndSettingsInLoanAccountValue);
        target.add(this.form);
    }

    protected void initLoanTrancheDetails() {
        this.enableMultipleDisbursalField = new CheckBox("enableMultipleDisbursalField",
                new PropertyModel<>(this, "enableMultipleDisbursalValue"));
        this.enableMultipleDisbursalField.setRequired(true);
        this.enableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::enableMultipleDisbursalFieldUpdate));
        this.form.add(this.enableMultipleDisbursalField);
        this.enableMultipleDisbursalFeedback = new TextFeedbackPanel("enableMultipleDisbursalFeedback",
                this.enableMultipleDisbursalField);
        this.form.add(this.enableMultipleDisbursalFeedback);

        this.enableMultipleDisbursalContainer = new WebMarkupContainer("enableMultipleDisbursalContainer");
        this.form.add(this.enableMultipleDisbursalContainer);

        this.maximumTrancheCountField = new TextField<>("maximumTrancheCountField",
                new PropertyModel<>(this, "maximumTrancheCountValue"));
        this.maximumTrancheCountField.setRequired(true);
        this.maximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.enableMultipleDisbursalContainer.add(this.maximumTrancheCountField);
        this.maximumTrancheCountFeedback = new TextFeedbackPanel("maximumTrancheCountFeedback",
                this.maximumTrancheCountField);
        this.enableMultipleDisbursalContainer.add(this.maximumTrancheCountFeedback);

        this.maximumAllowedOutstandingBalanceField = new TextField<>("maximumAllowedOutstandingBalanceField",
                new PropertyModel<>(this, "maximumAllowedOutstandingBalanceValue"));
        this.maximumAllowedOutstandingBalanceField.setRequired(true);
        this.maximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.enableMultipleDisbursalContainer.add(this.maximumAllowedOutstandingBalanceField);
        this.maximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel(
                "maximumAllowedOutstandingBalanceFeedback", this.maximumAllowedOutstandingBalanceField);
        this.enableMultipleDisbursalContainer.add(this.maximumAllowedOutstandingBalanceFeedback);
    }

    protected void enableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        this.enableMultipleDisbursalContainer
                .setVisible(this.enableMultipleDisbursalValue != null && this.enableMultipleDisbursalValue);
        target.add(this.form);
    }

    protected void initGuaranteeRequirements() {

        this.placeGuaranteeFundsOnHoldField = new CheckBox("placeGuaranteeFundsOnHoldField",
                new PropertyModel<>(this, "placeGuaranteeFundsOnHoldValue"));
        this.placeGuaranteeFundsOnHoldField.setRequired(true);
        this.placeGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::placeGuaranteeFundsOnHoldFieldUpdate));
        this.form.add(this.placeGuaranteeFundsOnHoldField);
        this.placeGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("placeGuaranteeFundsOnHoldFeedback",
                this.placeGuaranteeFundsOnHoldField);
        this.form.add(this.placeGuaranteeFundsOnHoldFeedback);

        this.placeGuaranteeFundsOnHoldContainer = new WebMarkupContainer("placeGuaranteeFundsOnHoldContainer");
        this.form.add(this.placeGuaranteeFundsOnHoldContainer);

        this.mandatoryGuaranteeField = new TextField<>("mandatoryGuaranteeField",
                new PropertyModel<>(this, "mandatoryGuaranteeValue"));
        this.mandatoryGuaranteeField.setRequired(true);
        this.mandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.mandatoryGuaranteeField);
        this.mandatoryGuaranteeFeedback = new TextFeedbackPanel("mandatoryGuaranteeFeedback",
                this.mandatoryGuaranteeField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.mandatoryGuaranteeFeedback);

        this.minimumGuaranteeField = new TextField<>("minimumGuaranteeField",
                new PropertyModel<>(this, "minimumGuaranteeValue"));
        this.minimumGuaranteeField.setRequired(true);
        this.minimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeField);
        this.minimumGuaranteeFeedback = new TextFeedbackPanel("minimumGuaranteeFeedback", this.minimumGuaranteeField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFeedback);

        this.minimumGuaranteeFromGuarantorField = new TextField<>("minimumGuaranteeFromGuarantorField",
                new PropertyModel<>(this, "minimumGuaranteeFromGuarantorValue"));
        this.minimumGuaranteeFromGuarantorField.setRequired(true);
        this.minimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFromGuarantorField);
        this.minimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("minimumGuaranteeFromGuarantorFeedback",
                this.minimumGuaranteeFromGuarantorField);
        this.placeGuaranteeFundsOnHoldContainer.add(this.minimumGuaranteeFromGuarantorFeedback);

    }

    protected void placeGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        this.placeGuaranteeFundsOnHoldContainer
                .setVisible(this.placeGuaranteeFundsOnHoldValue != null && this.placeGuaranteeFundsOnHoldValue);
        target.add(this.form);
    }

    protected void recalculateInterestFieldUpdate(AjaxRequestTarget target) {
        this.recalculateInterestContainer
                .setVisible(this.recalculateInterestValue != null && this.recalculateInterestValue);
        target.add(this.form);
    }

    protected void initInterestRecalculation() {
        this.recalculateInterestField = new CheckBox("recalculateInterestField",
                new PropertyModel<>(this, "recalculateInterestValue"));
        this.recalculateInterestField.setRequired(true);
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
        this.preClosureInterestCalculationRuleField.setRequired(true);
        this.preClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.recalculateInterestContainer.add(this.preClosureInterestCalculationRuleField);
        this.preClosureInterestCalculationRuleFeedback = new TextFeedbackPanel(
                "preClosureInterestCalculationRuleFeedback", this.preClosureInterestCalculationRuleField);
        this.recalculateInterestContainer.add(this.preClosureInterestCalculationRuleFeedback);

        this.advancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.advancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("advancePaymentsAdjustmentTypeField", 0,
                new PropertyModel<>(this, "advancePaymentsAdjustmentTypeValue"),
                this.advancePaymentsAdjustmentTypeProvider);
        this.advancePaymentsAdjustmentTypeField.setRequired(true);
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
        this.interestRecalculationCompoundingOnField.setRequired(true);
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
        this.compoundingField.setRequired(true);
        this.compoundingContainer.add(this.compoundingField);
        this.compoundingField.add(new OnChangeAjaxBehavior(this::compoundingFieldUpdate));
        this.compoundingFeedback = new TextFeedbackPanel("compoundingFeedback", this.compoundingField);
        this.compoundingContainer.add(this.compoundingFeedback);

        this.compoundingTypeContainer = new WebMarkupContainer("compoundingTypeContainer");
        this.compoundingContainer.add(this.compoundingTypeContainer);

        this.compoundingTypeProvider = new FrequencyTypeProvider();
        this.compoundingTypeField = new Select2SingleChoice<>("compoundingTypeField", 0,
                new PropertyModel<>(this, "compoundingTypeValue"), this.compoundingTypeProvider);
        this.compoundingTypeField.setRequired(true);
        this.compoundingTypeField.add(new OnChangeAjaxBehavior());
        this.compoundingTypeContainer.add(this.compoundingTypeField);
        this.compoundingTypeFeedback = new TextFeedbackPanel("compoundingTypeFeedback", this.compoundingTypeField);
        this.compoundingTypeContainer.add(this.compoundingTypeFeedback);

        this.compoundingDayContainer = new WebMarkupContainer("compoundingDayContainer");
        this.compoundingContainer.add(this.compoundingDayContainer);

        this.compoundingDayProvider = new FrequencyDayProvider();
        this.compoundingDayField = new Select2SingleChoice<>("compoundingDayField", 0,
                new PropertyModel<>(this, "compoundingDayValue"), this.compoundingDayProvider);
        this.compoundingDayField.setRequired(true);
        this.compoundingDayField.add(new OnChangeAjaxBehavior());
        this.compoundingDayContainer.add(this.compoundingDayField);
        this.compoundingDayFeedback = new TextFeedbackPanel("compoundingDayFeedback", this.compoundingDayField);
        this.compoundingDayContainer.add(this.compoundingDayFeedback);

        this.compoundingIntervalContainer = new WebMarkupContainer("compoundingIntervalContainer");
        this.recalculateInterestContainer.add(this.compoundingIntervalContainer);

        this.compoundingIntervalField = new TextField<>("compoundingIntervalField",
                new PropertyModel<>(this, "compoundingIntervalValue"));
        this.compoundingIntervalField.setRequired(true);
        this.compoundingIntervalField.add(new OnChangeAjaxBehavior());
        this.compoundingIntervalContainer.add(this.compoundingIntervalField);
        this.compoundingIntervalFeedback = new TextFeedbackPanel("compoundingIntervalFeedback",
                this.compoundingIntervalField);
        this.compoundingIntervalContainer.add(this.compoundingIntervalFeedback);

        this.recalculateProvider = new FrequencyProvider();
        this.recalculateField = new Select2SingleChoice<>("recalculateField", 0,
                new PropertyModel<>(this, "recalculateValue"), this.recalculateProvider);
        this.recalculateField.setRequired(true);
        this.recalculateField.add(new OnChangeAjaxBehavior(this::recalculateFieldUpdate));
        this.recalculateInterestContainer.add(this.recalculateField);
        this.recalculateFeedback = new TextFeedbackPanel("recalculateFeedback", this.recalculateField);
        this.recalculateInterestContainer.add(this.recalculateFeedback);

        this.recalculateTypeContainer = new WebMarkupContainer("recalculateTypeContainer");
        this.recalculateInterestContainer.add(this.recalculateTypeContainer);

        this.recalculateTypeProvider = new FrequencyTypeProvider();
        this.recalculateTypeField = new Select2SingleChoice<>("recalculateTypeField", 0,
                new PropertyModel<>(this, "recalculateTypeValue"), this.recalculateTypeProvider);
        this.recalculateTypeField.setRequired(true);
        this.recalculateTypeField.add(new OnChangeAjaxBehavior());
        this.recalculateTypeContainer.add(this.recalculateTypeField);
        this.recalculateTypeFeedback = new TextFeedbackPanel("recalculateTypeFeedback", this.recalculateTypeField);
        this.recalculateTypeContainer.add(this.recalculateTypeFeedback);

        this.recalculateDayContainer = new WebMarkupContainer("recalculateDayContainer");
        this.recalculateInterestContainer.add(this.recalculateDayContainer);

        this.recalculateDayProvider = new FrequencyDayProvider();
        this.recalculateDayField = new Select2SingleChoice<>("recalculateDayField", 0,
                new PropertyModel<>(this, "recalculateDayValue"), this.recalculateDayProvider);
        this.recalculateDayField.setRequired(true);
        this.recalculateDayField.add(new OnChangeAjaxBehavior());
        this.recalculateDayContainer.add(this.recalculateDayField);
        this.recalculateDayFeedback = new TextFeedbackPanel("recalculateDayFeedback", this.recalculateDayField);
        this.recalculateDayContainer.add(this.recalculateDayFeedback);

        this.recalculateIntervalContainer = new WebMarkupContainer("recalculateIntervalContainer");
        this.recalculateInterestContainer.add(this.recalculateIntervalContainer);

        this.recalculateIntervalField = new TextField<>("recalculateIntervalField",
                new PropertyModel<>(this, "recalculateIntervalValue"));
        this.recalculateIntervalField.setRequired(true);
        this.recalculateIntervalField.add(new OnChangeAjaxBehavior());
        this.recalculateIntervalContainer.add(this.recalculateIntervalField);
        this.recalculateIntervalFeedback = new TextFeedbackPanel("recalculateIntervalFeedback",
                this.recalculateIntervalField);
        this.recalculateIntervalContainer.add(this.recalculateIntervalFeedback);

        this.arrearsRecognizationBasedOnOriginalScheduleField = new CheckBox(
                "arrearsRecognizationBasedOnOriginalScheduleField",
                new PropertyModel<>(this, "arrearsRecognizationBasedOnOriginalScheduleValue"));
        this.arrearsRecognizationBasedOnOriginalScheduleField.setRequired(true);
        this.arrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());
        this.recalculateInterestContainer.add(this.arrearsRecognizationBasedOnOriginalScheduleField);
        this.arrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel(
                "arrearsRecognizationBasedOnOriginalScheduleFeedback",
                this.arrearsRecognizationBasedOnOriginalScheduleField);
        this.recalculateInterestContainer.add(this.arrearsRecognizationBasedOnOriginalScheduleFeedback);

    }

    protected void recalculateFieldUpdate(AjaxRequestTarget target) {
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
    }

    protected void compoundingFieldUpdate(AjaxRequestTarget target) {

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
        this.calculateInterestForExactDaysInPartialPeriodField.setRequired(true);
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
        this.moratoriumPrincipalField.setRequired(true);
        this.moratoriumPrincipalField.add(new OnChangeAjaxBehavior());
        this.form.add(this.moratoriumPrincipalField);
        this.moratoriumPrincipalFeedback = new TextFeedbackPanel("moratoriumPrincipalFeedback",
                this.moratoriumPrincipalField);
        this.form.add(this.moratoriumPrincipalFeedback);

        this.moratoriumInterestField = new TextField<>("moratoriumInterestField",
                new PropertyModel<>(this, "moratoriumInterestValue"));
        this.moratoriumInterestField.setRequired(true);
        this.moratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.form.add(this.moratoriumInterestField);
        this.moratoriumInterestFeedback = new TextFeedbackPanel("moratoriumInterestFeedback",
                this.moratoriumInterestField);
        this.form.add(this.moratoriumInterestFeedback);

        this.interestFreePeriodField = new TextField<>("interestFreePeriodField",
                new PropertyModel<>(this, "interestFreePeriodValue"));
        this.interestFreePeriodField.setRequired(true);
        this.interestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.form.add(this.interestFreePeriodField);
        this.interestFreePeriodFeedback = new TextFeedbackPanel("interestFreePeriodFeedback",
                this.interestFreePeriodField);
        this.form.add(this.interestFreePeriodFeedback);

        this.arrearsToleranceField = new TextField<>("arrearsToleranceField",
                new PropertyModel<>(this, "arrearsToleranceValue"));
        this.arrearsToleranceField.setRequired(true);
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
        this.allowFixingOfTheInstallmentAmountField.setRequired(true);
        this.allowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());
        this.form.add(this.allowFixingOfTheInstallmentAmountField);
        this.allowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel(
                "allowFixingOfTheInstallmentAmountFeedback", this.allowFixingOfTheInstallmentAmountField);
        this.form.add(this.allowFixingOfTheInstallmentAmountFeedback);

        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>(
                "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField",
                new PropertyModel<>(this, "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setRequired(true);
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.form.add(this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel(
                "numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback",
                this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.form.add(this.numberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);

        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>(
                "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField",
                new PropertyModel<>(this, "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setRequired(true);
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.form.add(this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel(
                "maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback",
                this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.form.add(this.maximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);

        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox(
                "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField",
                new PropertyModel<>(this, "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.setRequired(true);
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());
        this.form.add(this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel(
                "accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback",
                this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.form.add(this.accountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);

        this.principalThresholdForLastInstalmentField = new TextField<>("principalThresholdForLastInstalmentField",
                new PropertyModel<>(this, "principalThresholdForLastInstalmentValue"));
        this.principalThresholdForLastInstalmentField.setRequired(true);
        this.principalThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.form.add(this.principalThresholdForLastInstalmentField);
        this.principalThresholdForLastInstalmentFeedback = new TextFeedbackPanel(
                "principalThresholdForLastInstalmentFeedback", this.principalThresholdForLastInstalmentField);
        this.form.add(this.principalThresholdForLastInstalmentFeedback);

        this.variableInstallmentsAllowedField = new CheckBox("variableInstallmentsAllowedField",
                new PropertyModel<>(this, "variableInstallmentsAllowedValue"));
        this.variableInstallmentsAllowedField.setRequired(true);
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
        this.variableInstallmentsMinimumField.setRequired(true);
        this.variableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMinimumField);
        this.variableInstallmentsMinimumFeedback = new TextFeedbackPanel("variableInstallmentsMinimumFeedback",
                this.variableInstallmentsMinimumField);
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMinimumFeedback);

        this.variableInstallmentsMaximumField = new TextField<>("variableInstallmentsMaximumField",
                new PropertyModel<>(this, "variableInstallmentsMaximumValue"));
        this.variableInstallmentsMaximumField.setRequired(true);
        this.variableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMaximumField);
        this.variableInstallmentsMaximumFeedback = new TextFeedbackPanel("variableInstallmentsMaximumFeedback",
                this.variableInstallmentsMaximumField);
        this.variableInstallmentsAllowedContainer.add(this.variableInstallmentsMaximumFeedback);

        this.allowedToBeUsedForProvidingTopupLoansField = new CheckBox("allowedToBeUsedForProvidingTopupLoansField",
                new PropertyModel<>(this, "allowedToBeUsedForProvidingTopupLoansValue"));
        this.allowedToBeUsedForProvidingTopupLoansField.setRequired(true);
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
        this.descriptionField.setRequired(true);
        this.descriptionField.add(new OnChangeAjaxBehavior());
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.fundProvider = new SingleChoiceProvider("m_fund", "id", "name");
        this.fundField = new Select2SingleChoice<>("fundField", 0, new PropertyModel<>(this, "fundValue"),
                this.fundProvider);
        this.fundField.setRequired(true);
        this.fundField.add(new OnChangeAjaxBehavior());
        this.form.add(this.fundField);
        this.fundFeedback = new TextFeedbackPanel("fundFeedback", this.fundField);
        this.form.add(this.fundFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.startDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.form.add(this.startDateFeedback);

        this.closeDateField = new DateTextField("closeDateField", new PropertyModel<>(this, "closeDateValue"));
        this.closeDateField.setRequired(true);
        this.closeDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.closeDateField);
        this.closeDateFeedback = new TextFeedbackPanel("closeDateFeedback", this.closeDateField);
        this.form.add(this.closeDateFeedback);

        this.includeInCustomerLoanCounterField = new CheckBox("includeInCustomerLoanCounterField",
                new PropertyModel<>(this, "includeInCustomerLoanCounterValue"));
        this.includeInCustomerLoanCounterField.setRequired(true);
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
        this.currencyInMultipleOfField.setRequired(true);
        this.currencyInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.form.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback",
                this.currencyInMultipleOfField);
        this.form.add(this.currencyInMultipleOfFeedback);

        this.installmentInMultipleOfField = new TextField<>("installmentInMultipleOfField",
                new PropertyModel<>(this, "installmentInMultipleOfValue"));
        this.installmentInMultipleOfField.setRequired(true);
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
        this.termVaryBasedOnLoanCycleField.setRequired(true);
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
        this.linkedToFloatingInterestRatesField.setRequired(true);
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
            this.nominalInterestRateMinimumField.setRequired(true);
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
            this.nominalInterestRateMaximumField.setRequired(true);
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
            this.floatingInterestRateField.setRequired(true);
            this.floatingInterestRateField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestRateField);
            this.floatingInterestRateFeedback = new TextFeedbackPanel("floatingInterestRateFeedback",
                    this.floatingInterestRateField);
            this.floatInterestRateContainer.add(this.floatingInterestRateFeedback);

            this.floatingInterestMinimumField = new TextField<>("floatingInterestMinimumField",
                    new PropertyModel<>(this, "floatingInterestMinimumValue"));
            this.floatingInterestMinimumField.setRequired(true);
            this.floatingInterestMinimumField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestMinimumField);
            this.floatingInterestMinimumFeedback = new TextFeedbackPanel("floatingInterestMinimumFeedback",
                    this.floatingInterestMinimumField);
            this.floatInterestRateContainer.add(this.floatingInterestMinimumFeedback);

            this.floatingInterestDefaultField = new TextField<>("floatingInterestDefaultField",
                    new PropertyModel<>(this, "floatingInterestDefaultValue"));
            this.floatingInterestDefaultField.setRequired(true);
            this.floatingInterestDefaultField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestDefaultField);
            this.floatingInterestDefaultFeedback = new TextFeedbackPanel("floatingInterestDefaultFeedback",
                    this.floatingInterestDefaultField);
            this.floatInterestRateContainer.add(this.floatingInterestDefaultFeedback);

            this.floatingInterestMaximumField = new TextField<>("floatingInterestMaximumField",
                    new PropertyModel<>(this, "floatingInterestMaximumValue"));
            this.floatingInterestMaximumField.setRequired(true);
            this.floatingInterestMaximumField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestMaximumField);
            this.floatingInterestMaximumFeedback = new TextFeedbackPanel("floatingInterestMaximumFeedback",
                    this.floatingInterestMaximumField);
            this.floatInterestRateContainer.add(this.floatingInterestMaximumFeedback);

            this.floatingInterestDifferentialField = new TextField<>("floatingInterestDifferentialField",
                    new PropertyModel<>(this, "floatingInterestDifferentialValue"));
            this.floatingInterestDifferentialField.setRequired(true);
            this.floatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialField);
            this.floatingInterestDifferentialFeedback = new TextFeedbackPanel("floatingInterestDifferentialFeedback",
                    this.floatingInterestDifferentialField);
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialFeedback);

            this.floatingInterestAllowedField = new CheckBox("floatingInterestAllowedField",
                    new PropertyModel<>(this, "floatingInterestAllowedValue"));
            this.floatingInterestAllowedField.setRequired(true);
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
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField.setRequired(true);
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel(
                "minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback",
                this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);
    }

    private void nominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.nominalInterestRateByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel nominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel nominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void nominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> nominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void numberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.numberOfRepaymentByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel numberOfRepaymentByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel numberOfRepaymentByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void numberOfRepaymentByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> numberOfRepaymentByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void principalByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.principalByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel principalByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel principalByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void principalByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
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

    private List<ActionItem> principalByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void linkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        this.nominalInterestRateContainer.setVisible(
                this.linkedToFloatingInterestRatesValue == null ? true : !this.linkedToFloatingInterestRatesValue);
        this.floatInterestRateContainer.setVisible(!this.nominalInterestRateContainer.isVisible());
        target.add(this.form);
    }

    protected void interestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
        this.calculateInterestForExactDaysInPartialPeriodContainer
                .setVisible(this.interestCalculationPeriodValue != null && InterestCalculationPeriod.valueOf(
                        this.interestCalculationPeriodValue.getId()) == InterestCalculationPeriod.SameAsPayment);
        target.add(this.form);
    }

    protected void termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        this.principalByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.numberOfRepaymentByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.nominalInterestRateByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        target.add(this.form);
    }

    protected void variableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
        this.variableInstallmentsAllowedContainer
                .setVisible(this.variableInstallmentsAllowedValue != null && this.variableInstallmentsAllowedValue);
        target.add(this.form);
    }

    private void loanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.loanCycleValue.add(item);
        this.loanCycleValue = null;
        target.add(this.form);
    }

}
