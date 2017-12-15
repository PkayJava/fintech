package com.angkorteam.fintech.pages.product.loan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanPreviewPage extends Page {

    protected String loanId;

    protected BookmarkablePageLink<Void> editLink;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected String detailProductNameValue;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected String detailShortNameValue;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected String detailDescriptionValue;
    protected ReadOnlyView detailDescriptionView;

    protected WebMarkupBlock detailFundBlock;
    protected WebMarkupContainer detailFundVContainer;
    protected String detailFundValue;
    protected ReadOnlyView detailFundView;

    protected WebMarkupBlock detailStartDateBlock;
    protected WebMarkupContainer detailStartDateVContainer;
    protected Date detailStartDateValue;
    protected ReadOnlyView detailStartDateView;

    protected WebMarkupBlock detailCloseDateBlock;
    protected WebMarkupContainer detailCloseDateVContainer;
    protected Date detailCloseDateValue;
    protected ReadOnlyView detailCloseDateView;

    protected WebMarkupBlock detailIncludeInCustomerLoanCounterBlock;
    protected WebMarkupContainer detailIncludeInCustomerLoanCounterVContainer;
    protected Boolean detailIncludeInCustomerLoanCounterValue;
    protected ReadOnlyView detailIncludeInCustomerLoanCounterView;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected String currencyCodeValue;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected Long currencyDecimalPlaceValue;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyInMultipleOfBlock;
    protected WebMarkupContainer currencyInMultipleOfVContainer;
    protected Long currencyInMultipleOfValue;
    protected ReadOnlyView currencyInMultipleOfView;

    protected WebMarkupBlock currencyInstallmentInMultipleOfBlock;
    protected WebMarkupContainer currencyInstallmentInMultipleOfVContainer;
    protected Long currencyInstallmentInMultipleOfValue;
    protected ReadOnlyView currencyInstallmentInMultipleOfView;

    // Terms

    // Row 1 : Terms vary based on loan cycle
    protected WebMarkupBlock termVaryBasedOnLoanCycleBlock;
    protected WebMarkupContainer termVaryBasedOnLoanCycleVContainer;
    protected Boolean termVaryBasedOnLoanCycleValue;
    protected ReadOnlyView termVaryBasedOnLoanCycleView;

    // Row 2 : Principal
    protected WebMarkupBlock termPrincipalMinimumBlock;
    protected WebMarkupContainer termPrincipalMinimumVContainer;
    protected Double termPrincipalMinimumValue;
    protected ReadOnlyView termPrincipalMinimumView;

    protected WebMarkupBlock termPrincipalDefaultBlock;
    protected WebMarkupContainer termPrincipalDefaultVContainer;
    protected Double termPrincipalDefaultValue;
    protected ReadOnlyView termPrincipalDefaultView;

    protected WebMarkupBlock termPrincipalMaximumBlock;
    protected WebMarkupContainer termPrincipalMaximumVContainer;
    protected Double termPrincipalMaximumValue;
    protected ReadOnlyView termPrincipalMaximumView;

    // Row 2 (Optional) : Principal by loan cycle
    protected WebMarkupBlock termPrincipalByLoanCycleBlock;
    protected WebMarkupContainer termPrincipalByLoanCycleVContainer;
    protected List<Map<String, Object>> termPrincipalByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termPrincipalByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termPrincipalByLoanCycleColumn;
    protected ListDataProvider termPrincipalByLoanCycleProvider;

    // Row 3 : Number of repayments
    protected WebMarkupBlock termNumberOfRepaymentMinimumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMinimumVContainer;
    protected Long termNumberOfRepaymentMinimumValue;
    protected ReadOnlyView termNumberOfRepaymentMinimumView;

    protected WebMarkupBlock termNumberOfRepaymentDefaultBlock;
    protected WebMarkupContainer termNumberOfRepaymentDefaultVContainer;
    protected Long termNumberOfRepaymentDefaultValue;
    protected ReadOnlyView termNumberOfRepaymentDefaultView;

    protected WebMarkupBlock termNumberOfRepaymentMaximumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMaximumVContainer;
    protected Long termNumberOfRepaymentMaximumValue;
    protected ReadOnlyView termNumberOfRepaymentMaximumView;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
    protected WebMarkupBlock termNumberOfRepaymentByLoanCycleBlock;
    protected WebMarkupContainer termNumberOfRepaymentByLoanCycleVContainer;
    protected List<Map<String, Object>> termNumberOfRepaymentByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;

    // Row 4 : Is Linked to Floating Interest Rates?
    protected WebMarkupBlock termLinkedToFloatingInterestRatesBlock;
    protected WebMarkupContainer termLinkedToFloatingInterestRatesVContainer;
    protected Boolean termLinkedToFloatingInterestRatesValue;
    protected ReadOnlyView termLinkedToFloatingInterestRatesView;

    // Row 5 : Nominal interest rate
    protected WebMarkupBlock termNominalInterestRateMinimumBlock;
    protected WebMarkupContainer termNominalInterestRateMinimumVContainer;
    protected Double termNominalInterestRateMinimumValue;
    protected ReadOnlyView termNominalInterestRateMinimumView;

    protected WebMarkupBlock termNominalInterestRateDefaultBlock;
    protected WebMarkupContainer termNominalInterestRateDefaultVContainer;
    protected Double termNominalInterestRateDefaultValue;
    protected ReadOnlyView termNominalInterestRateDefaultView;

    protected WebMarkupBlock termNominalInterestRateMaximumBlock;
    protected WebMarkupContainer termNominalInterestRateMaximumVContainer;
    protected Double termNominalInterestRateMaximumValue;
    protected ReadOnlyView termNominalInterestRateMaximumView;

    protected WebMarkupBlock termNominalInterestRateTypeBlock;
    protected WebMarkupContainer termNominalInterestRateTypeVContainer;
    protected Option termNominalInterestRateTypeValue;
    protected ReadOnlyView termNominalInterestRateTypeView;

    protected WebMarkupBlock termFloatingInterestRateBlock;
    protected WebMarkupContainer termFloatingInterestRateVContainer;
    protected Option termFloatingInterestRateValue;
    protected ReadOnlyView termFloatingInterestRateView;

    protected WebMarkupBlock termFloatingInterestDifferentialBlock;
    protected WebMarkupContainer termFloatingInterestDifferentialVContainer;
    protected Double termFloatingInterestDifferentialValue;
    protected ReadOnlyView termFloatingInterestDifferentialView;

    protected WebMarkupBlock termFloatingInterestAllowedBlock;
    protected WebMarkupContainer termFloatingInterestAllowedVContainer;
    protected Boolean termFloatingInterestAllowedValue;
    protected ReadOnlyView termFloatingInterestAllowedView;

    protected WebMarkupBlock termFloatingInterestMinimumBlock;
    protected WebMarkupContainer termFloatingInterestMinimumVContainer;
    protected Double termFloatingInterestMinimumValue;
    protected ReadOnlyView termFloatingInterestMinimumView;

    protected WebMarkupBlock termFloatingInterestDefaultBlock;
    protected WebMarkupContainer termFloatingInterestDefaultVContainer;
    protected Double termFloatingInterestDefaultValue;
    protected ReadOnlyView termFloatingInterestDefaultView;

    protected WebMarkupBlock termFloatingInterestMaximumBlock;
    protected WebMarkupContainer termFloatingInterestMaximumVContainer;
    protected Double termFloatingInterestMaximumValue;
    protected ReadOnlyView termFloatingInterestMaximumView;

    // Row 6
    protected WebMarkupBlock termNominalInterestRateByLoanCycleBlock;
    protected WebMarkupContainer termNominalInterestRateByLoanCycleVContainer;
    protected List<Map<String, Object>> termNominalInterestRateByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
    protected AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
    protected ModalWindow termNominalInterestRateByLoanCyclePopup;

    protected WebMarkupBlock termRepaidEveryBlock;
    protected WebMarkupContainer termRepaidEveryVContainer;
    protected Long termRepaidEveryValue;
    protected ReadOnlyView termRepaidEveryView;

    protected WebMarkupBlock termRepaidTypeBlock;
    protected WebMarkupContainer termRepaidTypeVContainer;
    protected Option termRepaidTypeValue;
    protected ReadOnlyView termRepaidTypeView;

    protected WebMarkupBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    protected WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer;
    protected Long termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue;
    protected ReadOnlyView termMinimumDayBetweenDisbursalAndFirstRepaymentDateView;

    // Settings

    protected WebMarkupBlock settingAmortizationBlock;
    protected WebMarkupContainer settingAmortizationVContainer;
    protected Option settingAmortizationValue;
    protected ReadOnlyView settingAmortizationView;

    protected WebMarkupBlock settingInterestMethodBlock;
    protected WebMarkupContainer settingInterestMethodVContainer;
    protected Option settingInterestMethodValue;
    protected ReadOnlyView settingInterestMethodView;

    protected WebMarkupBlock settingInterestCalculationPeriodBlock;
    protected WebMarkupContainer settingInterestCalculationPeriodVContainer;
    protected Option settingInterestCalculationPeriodValue;
    protected ReadOnlyView settingInterestCalculationPeriodView;

    protected WebMarkupBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
    protected WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodVContainer;
    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    protected ReadOnlyView settingCalculateInterestForExactDaysInPartialPeriodView;

    protected WebMarkupBlock settingRepaymentStrategyBlock;
    protected WebMarkupContainer settingRepaymentStrategyVContainer;
    protected Option settingRepaymentStrategyValue;
    protected ReadOnlyView settingRepaymentStrategyView;

    protected WebMarkupBlock settingMoratoriumPrincipalBlock;
    protected WebMarkupContainer settingMoratoriumPrincipalVContainer;
    protected Long settingMoratoriumPrincipalValue;
    protected ReadOnlyView settingMoratoriumPrincipalView;

    protected WebMarkupBlock settingMoratoriumInterestBlock;
    protected WebMarkupContainer settingMoratoriumInterestVContainer;
    protected Long settingMoratoriumInterestValue;
    protected ReadOnlyView settingMoratoriumInterestView;

    protected WebMarkupBlock settingInterestFreePeriodBlock;
    protected WebMarkupContainer settingInterestFreePeriodVContainer;
    protected Long settingInterestFreePeriodValue;
    protected ReadOnlyView settingInterestFreePeriodView;

    protected WebMarkupBlock settingArrearsToleranceBlock;
    protected WebMarkupContainer settingArrearsToleranceVContainer;
    protected Double settingArrearsToleranceValue;
    protected ReadOnlyView settingArrearsToleranceView;

    protected WebMarkupBlock settingDayInYearBlock;
    protected WebMarkupContainer settingDayInYearVContainer;
    protected Option settingDayInYearValue;
    protected ReadOnlyView settingDayInYearView;

    protected WebMarkupBlock settingDayInMonthBlock;
    protected WebMarkupContainer settingDayInMonthVContainer;
    protected Option settingDayInMonthValue;
    protected ReadOnlyView settingDayInMonthView;

    protected WebMarkupBlock settingAllowFixingOfTheInstallmentAmountBlock;
    protected WebMarkupContainer settingAllowFixingOfTheInstallmentAmountVContainer;
    protected Boolean settingAllowFixingOfTheInstallmentAmountValue;
    protected ReadOnlyView settingAllowFixingOfTheInstallmentAmountView;

    protected WebMarkupBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    protected WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer;
    protected Long settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
    protected ReadOnlyView settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView;

    protected WebMarkupBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    protected WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer;
    protected Long settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
    protected ReadOnlyView settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView;

    protected WebMarkupBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    protected WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer;
    protected Boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
    protected ReadOnlyView settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView;

    protected WebMarkupBlock settingPrincipalThresholdForLastInstalmentBlock;
    protected WebMarkupContainer settingPrincipalThresholdForLastInstalmentVContainer;
    protected Double settingPrincipalThresholdForLastInstalmentValue;
    protected ReadOnlyView settingPrincipalThresholdForLastInstalmentView;

    protected WebMarkupBlock settingVariableInstallmentsAllowedBlock;
    protected WebMarkupContainer settingVariableInstallmentsAllowedVContainer;
    protected Boolean settingVariableInstallmentsAllowedValue;
    protected ReadOnlyView settingVariableInstallmentsAllowedView;

    protected WebMarkupBlock settingVariableInstallmentsMinimumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMinimumVContainer;
    protected Long settingVariableInstallmentsMinimumValue;
    protected ReadOnlyView settingVariableInstallmentsMinimumView;

    protected WebMarkupBlock settingVariableInstallmentsMaximumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMaximumVContainer;
    protected Long settingVariableInstallmentsMaximumValue;
    protected ReadOnlyView settingVariableInstallmentsMaximumView;

    protected WebMarkupBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
    protected WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansVContainer;
    protected Boolean settingAllowedToBeUsedForProvidingTopupLoansValue;
    protected ReadOnlyView settingAllowedToBeUsedForProvidingTopupLoansView;

    // Interest Recalculation

    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
    protected WebMarkupContainer interestRecalculationRecalculateInterestVContainer;
    protected Boolean interestRecalculationRecalculateInterestValue;
    protected ReadOnlyView interestRecalculationRecalculateInterestView;

    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleVContainer;
    protected Option interestRecalculationPreClosureInterestCalculationRuleValue;
    protected ReadOnlyView interestRecalculationPreClosureInterestCalculationRuleView;

    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeVContainer;
    protected Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
    protected ReadOnlyView interestRecalculationAdvancePaymentsAdjustmentTypeView;

    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
    protected WebMarkupContainer interestRecalculationCompoundingOnVContainer;
    protected Option interestRecalculationCompoundingOnValue;
    protected ReadOnlyView interestRecalculationCompoundingOnView;

    protected WebMarkupBlock interestRecalculationCompoundingBlock;
    protected WebMarkupContainer interestRecalculationCompoundingVContainer;
    protected Option interestRecalculationCompoundingValue;
    protected ReadOnlyView interestRecalculationCompoundingView;

    protected WebMarkupBlock interestRecalculationCompoundingTypeBlock;
    protected WebMarkupContainer interestRecalculationCompoundingTypeVContainer;
    protected Option interestRecalculationCompoundingTypeValue;
    protected ReadOnlyView interestRecalculationCompoundingTypeView;

    protected WebMarkupBlock interestRecalculationCompoundingDayBlock;
    protected WebMarkupContainer interestRecalculationCompoundingDayVContainer;
    protected Option interestRecalculationCompoundingDayValue;
    protected ReadOnlyView interestRecalculationCompoundingDayView;

    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIntervalVContainer;
    protected Long interestRecalculationCompoundingIntervalValue;
    protected ReadOnlyView interestRecalculationCompoundingIntervalView;

    protected WebMarkupBlock interestRecalculationRecalculateBlock;
    protected WebMarkupContainer interestRecalculationRecalculateVContainer;
    protected Option interestRecalculationRecalculateValue;
    protected ReadOnlyView interestRecalculationRecalculateView;

    protected WebMarkupBlock interestRecalculationRecalculateTypeBlock;
    protected WebMarkupContainer interestRecalculationRecalculateTypeVContainer;
    protected Option interestRecalculationRecalculateTypeValue;
    protected ReadOnlyView interestRecalculationRecalculateTypeView;

    protected WebMarkupBlock interestRecalculationRecalculateDayBlock;
    protected WebMarkupContainer interestRecalculationRecalculateDayVContainer;
    protected Option interestRecalculationRecalculateDayValue;
    protected ReadOnlyView interestRecalculationRecalculateDayView;

    protected WebMarkupBlock interestRecalculationRecalculateIntervalBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIntervalVContainer;
    protected Long interestRecalculationRecalculateIntervalValue;
    protected ReadOnlyView interestRecalculationRecalculateIntervalView;

    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer;
    protected Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;
    protected ReadOnlyView interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView;

    // Guarantee Requirements

    protected WebMarkupBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    protected WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer;
    protected Boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
    protected ReadOnlyView guaranteeRequirementPlaceGuaranteeFundsOnHoldView;

    protected WebMarkupBlock guaranteeRequirementMandatoryGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMandatoryGuaranteeVContainer;
    protected Double guaranteeRequirementMandatoryGuaranteeValue;
    protected ReadOnlyView guaranteeRequirementMandatoryGuaranteeView;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeVContainer;
    protected Double guaranteeRequirementMinimumGuaranteeValue;
    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeView;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer;
    protected Double guaranteeRequirementMinimumGuaranteeFromGuarantorValue;
    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeFromGuarantorView;

    // Loan Tranche Details

    protected WebMarkupBlock loanTrancheDetailEnableMultipleDisbursalBlock;
    protected WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalVContainer;
    protected Boolean loanTrancheDetailEnableMultipleDisbursalValue;
    protected ReadOnlyView loanTrancheDetailEnableMultipleDisbursalView;

    protected WebMarkupBlock loanTrancheDetailMaximumTrancheCountBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumTrancheCountVContainer;
    protected Long loanTrancheDetailMaximumTrancheCountValue;
    protected ReadOnlyView loanTrancheDetailMaximumTrancheCountView;

    protected WebMarkupBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer;
    protected Double loanTrancheDetailMaximumAllowedOutstandingBalanceValue;
    protected ReadOnlyView loanTrancheDetailMaximumAllowedOutstandingBalanceView;

    // Configurable Terms and Settings

    protected WebMarkupBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    protected WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer;
    protected Boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
    protected ReadOnlyView configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView;

    protected WebMarkupBlock configurableAmortizationBlock;
    protected WebMarkupContainer configurableAmortizationVContainer;
    protected Boolean configurableAmortizationValue;
    protected ReadOnlyView configurableAmortizationView;

    protected WebMarkupBlock configurableInterestMethodBlock;
    protected WebMarkupContainer configurableInterestMethodVContainer;
    protected Boolean configurableInterestMethodValue;
    protected ReadOnlyView configurableInterestMethodView;

    protected WebMarkupBlock configurableRepaymentStrategyBlock;
    protected WebMarkupContainer configurableRepaymentStrategyVContainer;
    protected Boolean configurableRepaymentStrategyValue;
    protected ReadOnlyView configurableRepaymentStrategyView;

    protected WebMarkupBlock configurableInterestCalculationPeriodBlock;
    protected WebMarkupContainer configurableInterestCalculationPeriodVContainer;
    protected Boolean configurableInterestCalculationPeriodValue;
    protected ReadOnlyView configurableInterestCalculationPeriodView;

    protected WebMarkupBlock configurableArrearsToleranceBlock;
    protected WebMarkupContainer configurableArrearsToleranceVContainer;
    protected Boolean configurableArrearsToleranceValue;
    protected ReadOnlyView configurableArrearsToleranceView;

    protected WebMarkupBlock configurableRepaidEveryBlock;
    protected WebMarkupContainer configurableRepaidEveryVContainer;
    protected Boolean configurableRepaidEveryValue;
    protected ReadOnlyView configurableRepaidEveryView;

    protected WebMarkupBlock configurableMoratoriumBlock;
    protected WebMarkupContainer configurableMoratoriumVContainer;
    protected Boolean configurableMoratoriumValue;
    protected ReadOnlyView configurableMoratoriumView;

    protected WebMarkupBlock configurableOverdueBeforeMovingBlock;
    protected WebMarkupContainer configurableOverdueBeforeMovingVContainer;
    protected Boolean configurableOverdueBeforeMovingValue;
    protected ReadOnlyView configurableOverdueBeforeMovingView;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected ReadOnlyView accountingView;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashVContainer;

    protected WebMarkupBlock cashFundSourceBlock;
    protected WebMarkupContainer cashFundSourceVContainer;
    protected Option cashFundSourceValue;
    protected ReadOnlyView cashFundSourceView;

    protected WebMarkupBlock cashLoanPortfolioBlock;
    protected WebMarkupContainer cashLoanPortfolioVContainer;
    protected Option cashLoanPortfolioValue;
    protected ReadOnlyView cashLoanPortfolioView;

    protected WebMarkupBlock cashTransferInSuspenseBlock;
    protected WebMarkupContainer cashTransferInSuspenseVContainer;
    protected Option cashTransferInSuspenseValue;
    protected ReadOnlyView cashTransferInSuspenseView;

    protected WebMarkupBlock cashIncomeFromInterestBlock;
    protected WebMarkupContainer cashIncomeFromInterestVContainer;
    protected Option cashIncomeFromInterestValue;
    protected ReadOnlyView cashIncomeFromInterestView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected Option cashIncomeFromFeeValue;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
    protected Option cashIncomeFromPenaltyValue;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    protected WebMarkupBlock cashIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer cashIncomeFromRecoveryRepaymentVContainer;
    protected Option cashIncomeFromRecoveryRepaymentValue;
    protected ReadOnlyView cashIncomeFromRecoveryRepaymentView;

    protected WebMarkupBlock cashLossWrittenOffBlock;
    protected WebMarkupContainer cashLossWrittenOffVContainer;
    protected Option cashLossWrittenOffValue;
    protected ReadOnlyView cashLossWrittenOffView;

    protected WebMarkupBlock cashOverPaymentLiabilityBlock;
    protected WebMarkupContainer cashOverPaymentLiabilityVContainer;
    protected Option cashOverPaymentLiabilityValue;
    protected ReadOnlyView cashOverPaymentLiabilityView;

    protected WebMarkupContainer periodicBlock;
    protected WebMarkupContainer periodicVContainer;

    protected WebMarkupBlock periodicFundSourceBlock;
    protected WebMarkupContainer periodicFundSourceVContainer;
    protected Option periodicFundSourceValue;
    protected ReadOnlyView periodicFundSourceView;

    protected WebMarkupBlock periodicLoanPortfolioBlock;
    protected WebMarkupContainer periodicLoanPortfolioVContainer;
    protected Option periodicLoanPortfolioValue;
    protected ReadOnlyView periodicLoanPortfolioView;

    protected WebMarkupBlock periodicInterestReceivableBlock;
    protected WebMarkupContainer periodicInterestReceivableVContainer;
    protected Option periodicInterestReceivableValue;
    protected ReadOnlyView periodicInterestReceivableView;

    protected WebMarkupBlock periodicFeeReceivableBlock;
    protected WebMarkupContainer periodicFeeReceivableVContainer;
    protected Option periodicFeeReceivableValue;
    protected ReadOnlyView periodicFeeReceivableView;

    protected WebMarkupBlock periodicPenaltyReceivableBlock;
    protected WebMarkupContainer periodicPenaltyReceivableVContainer;
    protected Option periodicPenaltyReceivableValue;
    protected ReadOnlyView periodicPenaltyReceivableView;

    protected WebMarkupBlock periodicTransferInSuspenseBlock;
    protected WebMarkupContainer periodicTransferInSuspenseVContainer;
    protected Option periodicTransferInSuspenseValue;
    protected ReadOnlyView periodicTransferInSuspenseView;

    protected WebMarkupBlock periodicIncomeFromInterestBlock;
    protected WebMarkupContainer periodicIncomeFromInterestVContainer;
    protected Option periodicIncomeFromInterestValue;
    protected ReadOnlyView periodicIncomeFromInterestView;

    protected WebMarkupBlock periodicIncomeFromFeeBlock;
    protected WebMarkupContainer periodicIncomeFromFeeVContainer;
    protected Option periodicIncomeFromFeeValue;
    protected ReadOnlyView periodicIncomeFromFeeView;

    protected WebMarkupBlock periodicIncomeFromPenaltyBlock;
    protected WebMarkupContainer periodicIncomeFromPenaltyVContainer;
    protected Option periodicIncomeFromPenaltyValue;
    protected ReadOnlyView periodicIncomeFromPenaltyView;

    protected WebMarkupBlock periodicIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer periodicIncomeFromRecoveryRepaymentVContainer;
    protected Option periodicIncomeFromRecoveryRepaymentValue;
    protected ReadOnlyView periodicIncomeFromRecoveryRepaymentView;

    protected WebMarkupBlock periodicLossWrittenOffBlock;
    protected WebMarkupContainer periodicLossWrittenOffVContainer;
    protected Option periodicLossWrittenOffValue;
    protected ReadOnlyView periodicLossWrittenOffView;

    protected WebMarkupBlock periodicOverPaymentLiabilityBlock;
    protected WebMarkupContainer periodicOverPaymentLiabilityVContainer;
    protected Option periodicOverPaymentLiabilityValue;
    protected ReadOnlyView periodicOverPaymentLiabilityView;

    protected WebMarkupContainer upfrontBlock;
    protected WebMarkupContainer upfrontVContainer;

    protected WebMarkupBlock upfrontFundSourceBlock;
    protected WebMarkupContainer upfrontFundSourceVContainer;
    protected Option upfrontFundSourceValue;
    protected ReadOnlyView upfrontFundSourceView;

    protected WebMarkupBlock upfrontLoanPortfolioBlock;
    protected WebMarkupContainer upfrontLoanPortfolioVContainer;
    protected Option upfrontLoanPortfolioValue;
    protected ReadOnlyView upfrontLoanPortfolioView;

    protected WebMarkupBlock upfrontInterestReceivableBlock;
    protected WebMarkupContainer upfrontInterestReceivableVContainer;
    protected Option upfrontInterestReceivableValue;
    protected ReadOnlyView upfrontInterestReceivableView;

    protected WebMarkupBlock upfrontFeeReceivableBlock;
    protected WebMarkupContainer upfrontFeeReceivableVContainer;
    protected Option upfrontFeeReceivableValue;
    protected ReadOnlyView upfrontFeeReceivableView;

    protected WebMarkupBlock upfrontPenaltyReceivableBlock;
    protected WebMarkupContainer upfrontPenaltyReceivableVContainer;
    protected Option upfrontPenaltyReceivableValue;
    protected ReadOnlyView upfrontPenaltyReceivableView;

    protected WebMarkupBlock upfrontTransferInSuspenseBlock;
    protected WebMarkupContainer upfrontTransferInSuspenseVContainer;
    protected Option upfrontTransferInSuspenseValue;
    protected ReadOnlyView upfrontTransferInSuspenseView;

    protected WebMarkupBlock upfrontIncomeFromInterestBlock;
    protected WebMarkupContainer upfrontIncomeFromInterestVContainer;
    protected Option upfrontIncomeFromInterestValue;
    protected ReadOnlyView upfrontIncomeFromInterestView;

    protected WebMarkupBlock upfrontIncomeFromFeeBlock;
    protected WebMarkupContainer upfrontIncomeFromFeeVContainer;
    protected Option upfrontIncomeFromFeeValue;
    protected ReadOnlyView upfrontIncomeFromFeeView;

    protected WebMarkupBlock upfrontIncomeFromPenaltyBlock;
    protected WebMarkupContainer upfrontIncomeFromPenaltyVContainer;
    protected Option upfrontIncomeFromPenaltyValue;
    protected ReadOnlyView upfrontIncomeFromPenaltyView;

    protected WebMarkupBlock upfrontIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer upfrontIncomeFromRecoveryRepaymentVContainer;
    protected Option upfrontIncomeFromRecoveryRepaymentValue;
    protected ReadOnlyView upfrontIncomeFromRecoveryRepaymentView;

    protected WebMarkupBlock upfrontLossWrittenOffBlock;
    protected WebMarkupContainer upfrontLossWrittenOffVContainer;
    protected Option upfrontLossWrittenOffValue;
    protected ReadOnlyView upfrontLossWrittenOffView;

    protected WebMarkupBlock upfrontOverPaymentLiabilityBlock;
    protected WebMarkupContainer upfrontOverPaymentLiabilityVContainer;
    protected Option upfrontOverPaymentLiabilityValue;
    protected ReadOnlyView upfrontOverPaymentLiabilityView;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleVContainer;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;

    // Charges

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;

    // Overdue Charges

    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
    protected List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> overdueChargeTable;
    protected ListDataProvider overdueChargeProvider;

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
            breadcrumb.setLabel("Loan Product");
            breadcrumb.setPage(LoanBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Loan Product Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {

        this.editLink = new BookmarkablePageLink<>("editLink", LoanBrowsePage.class);
        add(this.editLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerms();

        initSectionSetting();

        initSectionInterestRecalculation();

        initSectionGuaranteeRequirements();

        initSectionLoanTrancheDetails();

        initSectionConfigurableTermsAndSettings();

        initSectionCharge();

        initSectionOverdueCharge();

        initSectionAccounting(); 

    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {

    }

    protected void initSectionOverdueCharge() {
        this.overdueChargeColumn = Lists.newArrayList();
        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::overdueChargeColumn));
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, 20);
        add(this.overdueChargeTable);
        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));
    }

    protected ItemPanel overdueChargeColumn(String column, IModel<String> display, Map<String, Object> model) {
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

    protected void initSectionCharge() {

        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

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

    protected void initSectionAccounting() {
        this.accountingView = new ReadOnlyView("accountingView", new PropertyModel<>(this, "accountingValue")); 
        add(this.accountingView);

        initAccountingCash();

        initAccountingPeriodic();

        initAccountingUpFront();

        initAdvancedAccountingRule();

    }

    protected void initAccountingUpFront() {
        this.upfrontBlock = new WebMarkupContainer("upfrontBlock");
        this.upfrontBlock.setOutputMarkupId(true);
        add(this.upfrontBlock);

        this.upfrontVContainer = new WebMarkupContainer("upfrontVContainer");
        this.upfrontBlock.add(this.upfrontVContainer);

        this.upfrontFundSourceBlock = new WebMarkupBlock("upfrontFundSourceBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontFundSourceBlock);
        this.upfrontFundSourceVContainer = new WebMarkupContainer("upfrontFundSourceVContainer");
        this.upfrontFundSourceBlock.add(this.upfrontFundSourceVContainer);
        this.upfrontFundSourceView = new ReadOnlyView("upfrontFundSourceField", new PropertyModel<>(this, "upfrontFundSourceValue"));
        this.upfrontFundSourceVContainer.add(this.upfrontFundSourceView);

        this.upfrontLoanPortfolioBlock = new WebMarkupBlock("upfrontLoanPortfolioBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontLoanPortfolioBlock);
        this.upfrontLoanPortfolioVContainer = new WebMarkupContainer("upfrontLoanPortfolioVContainer");
        this.upfrontLoanPortfolioBlock.add(this.upfrontLoanPortfolioVContainer);
        this.upfrontLoanPortfolioView = new ReadOnlyView("upfrontLoanPortfolioField", new PropertyModel<>(this, "upfrontLoanPortfolioValue"));
        this.upfrontLoanPortfolioVContainer.add(this.upfrontLoanPortfolioView);

        this.upfrontInterestReceivableBlock = new WebMarkupBlock("upfrontInterestReceivableBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontInterestReceivableBlock);
        this.upfrontInterestReceivableVContainer = new WebMarkupContainer("upfrontInterestReceivableVContainer");
        this.upfrontInterestReceivableBlock.add(this.upfrontInterestReceivableVContainer);
        this.upfrontInterestReceivableView = new ReadOnlyView("upfrontInterestReceivableField", new PropertyModel<>(this, "upfrontInterestReceivableValue"));
        this.upfrontInterestReceivableVContainer.add(this.upfrontInterestReceivableView);

        this.upfrontFeeReceivableBlock = new WebMarkupBlock("upfrontFeeReceivableBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontFeeReceivableBlock);
        this.upfrontFeeReceivableVContainer = new WebMarkupContainer("upfrontFeeReceivableVContainer");
        this.upfrontFeeReceivableBlock.add(this.upfrontFeeReceivableVContainer);
        this.upfrontFeeReceivableView = new ReadOnlyView("upfrontFeeReceivableField", new PropertyModel<>(this, "upfrontFeeReceivableValue"));
        this.upfrontFeeReceivableVContainer.add(this.upfrontFeeReceivableView);

        this.upfrontPenaltyReceivableBlock = new WebMarkupBlock("upfrontPenaltyReceivableBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontPenaltyReceivableBlock);
        this.upfrontPenaltyReceivableVContainer = new WebMarkupContainer("upfrontPenaltyReceivableVContainer");
        this.upfrontPenaltyReceivableBlock.add(this.upfrontPenaltyReceivableVContainer);
        this.upfrontPenaltyReceivableView = new ReadOnlyView("upfrontPenaltyReceivableField", new PropertyModel<>(this, "upfrontPenaltyReceivableValue"));
        this.upfrontPenaltyReceivableVContainer.add(this.upfrontPenaltyReceivableView);

        this.upfrontTransferInSuspenseBlock = new WebMarkupBlock("upfrontTransferInSuspenseBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontTransferInSuspenseBlock);
        this.upfrontTransferInSuspenseVContainer = new WebMarkupContainer("upfrontTransferInSuspenseVContainer");
        this.upfrontTransferInSuspenseBlock.add(this.upfrontTransferInSuspenseVContainer);
        this.upfrontTransferInSuspenseView = new ReadOnlyView("upfrontTransferInSuspenseField", new PropertyModel<>(this, "upfrontTransferInSuspenseValue"));
        this.upfrontTransferInSuspenseVContainer.add(this.upfrontTransferInSuspenseView);

        this.upfrontIncomeFromInterestBlock = new WebMarkupBlock("upfrontIncomeFromInterestBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontIncomeFromInterestBlock);
        this.upfrontIncomeFromInterestVContainer = new WebMarkupContainer("upfrontIncomeFromInterestVContainer");
        this.upfrontIncomeFromInterestBlock.add(this.upfrontIncomeFromInterestVContainer);
        this.upfrontIncomeFromInterestView = new ReadOnlyView("upfrontIncomeFromInterestField", new PropertyModel<>(this, "upfrontIncomeFromInterestValue"));
        this.upfrontIncomeFromInterestVContainer.add(this.upfrontIncomeFromInterestView);

        this.upfrontIncomeFromFeeBlock = new WebMarkupBlock("upfrontIncomeFromFeeBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontIncomeFromFeeBlock);
        this.upfrontIncomeFromFeeVContainer = new WebMarkupContainer("upfrontIncomeFromFeeVContainer");
        this.upfrontIncomeFromFeeBlock.add(this.upfrontIncomeFromFeeVContainer);
        this.upfrontIncomeFromFeeView = new ReadOnlyView("upfrontIncomeFromFeeField", new PropertyModel<>(this, "upfrontIncomeFromFeeValue"));
        this.upfrontIncomeFromFeeVContainer.add(this.upfrontIncomeFromFeeView);

        this.upfrontIncomeFromPenaltyBlock = new WebMarkupBlock("upfrontIncomeFromPenaltyBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontIncomeFromPenaltyBlock);
        this.upfrontIncomeFromPenaltyVContainer = new WebMarkupContainer("upfrontIncomeFromPenaltyVContainer");
        this.upfrontIncomeFromPenaltyBlock.add(this.upfrontIncomeFromPenaltyVContainer);
        this.upfrontIncomeFromPenaltyView = new ReadOnlyView("upfrontIncomeFromPenaltyField", new PropertyModel<>(this, "upfrontIncomeFromPenaltyValue"));
        this.upfrontIncomeFromPenaltyVContainer.add(this.upfrontIncomeFromPenaltyView);

        this.upfrontIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("upfrontIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontIncomeFromRecoveryRepaymentBlock);
        this.upfrontIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("upfrontIncomeFromRecoveryRepaymentVContainer");
        this.upfrontIncomeFromRecoveryRepaymentBlock.add(this.upfrontIncomeFromRecoveryRepaymentVContainer);
        this.upfrontIncomeFromRecoveryRepaymentView = new ReadOnlyView("upfrontIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentValue"));
        this.upfrontIncomeFromRecoveryRepaymentVContainer.add(this.upfrontIncomeFromRecoveryRepaymentView);

        this.upfrontLossWrittenOffBlock = new WebMarkupBlock("upfrontLossWrittenOffBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontLossWrittenOffBlock);
        this.upfrontLossWrittenOffVContainer = new WebMarkupContainer("upfrontLossWrittenOffVContainer");
        this.upfrontLossWrittenOffBlock.add(this.upfrontLossWrittenOffVContainer);
        this.upfrontLossWrittenOffView = new ReadOnlyView("upfrontLossWrittenOffField", new PropertyModel<>(this, "upfrontLossWrittenOffValue"));
        this.upfrontLossWrittenOffVContainer.add(this.upfrontLossWrittenOffView);

        this.upfrontOverPaymentLiabilityBlock = new WebMarkupBlock("upfrontOverPaymentLiabilityBlock", Size.Six_6);
        this.upfrontVContainer.add(this.upfrontOverPaymentLiabilityBlock);
        this.upfrontOverPaymentLiabilityVContainer = new WebMarkupContainer("upfrontOverPaymentLiabilityVContainer");
        this.upfrontOverPaymentLiabilityBlock.add(this.upfrontOverPaymentLiabilityVContainer);
        this.upfrontOverPaymentLiabilityView = new ReadOnlyView("upfrontOverPaymentLiabilityField", new PropertyModel<>(this, "upfrontOverPaymentLiabilityValue"));
        this.upfrontOverPaymentLiabilityVContainer.add(this.upfrontOverPaymentLiabilityView);
    }

    protected void initAccountingCash() {

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        add(this.cashBlock);

        this.cashVContainer = new WebMarkupContainer("cashVContainer");
        this.cashBlock.add(this.cashVContainer);

        this.cashFundSourceBlock = new WebMarkupBlock("cashFundSourceBlock", Size.Six_6);
        this.cashVContainer.add(this.cashFundSourceBlock);
        this.cashFundSourceVContainer = new WebMarkupContainer("cashFundSourceVContainer");
        this.cashFundSourceBlock.add(this.cashFundSourceVContainer);
        this.cashFundSourceView = new ReadOnlyView("cashFundSourceField", new PropertyModel<>(this, "cashFundSourceValue"));
        this.cashFundSourceVContainer.add(this.cashFundSourceView);

        this.cashLoanPortfolioBlock = new WebMarkupBlock("cashLoanPortfolioBlock", Size.Six_6);
        this.cashVContainer.add(this.cashLoanPortfolioBlock);
        this.cashLoanPortfolioVContainer = new WebMarkupContainer("cashLoanPortfolioVContainer");
        this.cashLoanPortfolioBlock.add(this.cashLoanPortfolioVContainer);
        this.cashLoanPortfolioView = new ReadOnlyView("cashLoanPortfolioField", new PropertyModel<>(this, "cashLoanPortfolioValue"));
        this.cashLoanPortfolioVContainer.add(this.cashLoanPortfolioView);

        this.cashTransferInSuspenseBlock = new WebMarkupBlock("cashTransferInSuspenseBlock", Size.Six_6);
        this.cashVContainer.add(this.cashTransferInSuspenseBlock);
        this.cashTransferInSuspenseVContainer = new WebMarkupContainer("cashTransferInSuspenseVContainer");
        this.cashTransferInSuspenseBlock.add(this.cashTransferInSuspenseVContainer);
        this.cashTransferInSuspenseView = new ReadOnlyView("cashTransferInSuspenseField", new PropertyModel<>(this, "cashTransferInSuspenseValue"));
        this.cashTransferInSuspenseVContainer.add(this.cashTransferInSuspenseView);

        this.cashIncomeFromInterestBlock = new WebMarkupBlock("cashIncomeFromInterestBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromInterestBlock);
        this.cashIncomeFromInterestVContainer = new WebMarkupContainer("cashIncomeFromInterestVContainer");
        this.cashIncomeFromInterestBlock.add(this.cashIncomeFromInterestVContainer);
        this.cashIncomeFromInterestView = new ReadOnlyView("cashIncomeFromInterestField", new PropertyModel<>(this, "cashIncomeFromInterestValue"));
        this.cashIncomeFromInterestVContainer.add(this.cashIncomeFromInterestView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyField", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);

        this.cashIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromRecoveryRepaymentBlock);
        this.cashIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("cashIncomeFromRecoveryRepaymentVContainer");
        this.cashIncomeFromRecoveryRepaymentBlock.add(this.cashIncomeFromRecoveryRepaymentVContainer);
        this.cashIncomeFromRecoveryRepaymentView = new ReadOnlyView("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "cashIncomeFromRecoveryRepaymentValue"));
        this.cashIncomeFromRecoveryRepaymentVContainer.add(this.cashIncomeFromRecoveryRepaymentView);

        this.cashLossWrittenOffBlock = new WebMarkupBlock("cashLossWrittenOffBlock", Size.Six_6);
        this.cashVContainer.add(this.cashLossWrittenOffBlock);
        this.cashLossWrittenOffVContainer = new WebMarkupContainer("cashLossWrittenOffVContainer");
        this.cashLossWrittenOffBlock.add(this.cashLossWrittenOffVContainer);
        this.cashLossWrittenOffView = new ReadOnlyView("cashLossWrittenOffField", new PropertyModel<>(this, "cashLossWrittenOffValue"));
        this.cashLossWrittenOffVContainer.add(this.cashLossWrittenOffView);

        this.cashOverPaymentLiabilityBlock = new WebMarkupBlock("cashOverPaymentLiabilityBlock", Size.Six_6);
        this.cashVContainer.add(this.cashOverPaymentLiabilityBlock);
        this.cashOverPaymentLiabilityVContainer = new WebMarkupContainer("cashOverPaymentLiabilityVContainer");
        this.cashOverPaymentLiabilityBlock.add(this.cashOverPaymentLiabilityVContainer);
        this.cashOverPaymentLiabilityView = new ReadOnlyView("cashOverPaymentLiabilityField", new PropertyModel<>(this, "cashOverPaymentLiabilityValue"));
        this.cashOverPaymentLiabilityVContainer.add(this.cashOverPaymentLiabilityView);
    }

    protected void initAccountingPeriodic() {
        this.periodicBlock = new WebMarkupContainer("periodicBlock");
        this.periodicBlock.setOutputMarkupId(true);
        add(this.periodicBlock);

        this.periodicVContainer = new WebMarkupContainer("periodicVContainer");
        this.periodicBlock.add(this.periodicVContainer);

        this.periodicFundSourceBlock = new WebMarkupBlock("periodicFundSourceBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicFundSourceBlock);
        this.periodicFundSourceVContainer = new WebMarkupContainer("periodicFundSourceVContainer");
        this.periodicFundSourceBlock.add(this.periodicFundSourceVContainer);
        this.periodicFundSourceView = new ReadOnlyView("periodicFundSourceField", new PropertyModel<>(this, "periodicFundSourceValue"));
        this.periodicFundSourceVContainer.add(this.periodicFundSourceView);

        this.periodicLoanPortfolioBlock = new WebMarkupBlock("periodicLoanPortfolioBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicLoanPortfolioBlock);
        this.periodicLoanPortfolioVContainer = new WebMarkupContainer("periodicLoanPortfolioVContainer");
        this.periodicLoanPortfolioBlock.add(this.periodicLoanPortfolioVContainer);
        this.periodicLoanPortfolioView = new ReadOnlyView("periodicLoanPortfolioField", new PropertyModel<>(this, "periodicLoanPortfolioValue"));
        this.periodicLoanPortfolioVContainer.add(this.periodicLoanPortfolioView);

        this.periodicInterestReceivableBlock = new WebMarkupBlock("periodicInterestReceivableBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicInterestReceivableBlock);
        this.periodicInterestReceivableVContainer = new WebMarkupContainer("periodicInterestReceivableVContainer");
        this.periodicInterestReceivableBlock.add(this.periodicInterestReceivableVContainer);
        this.periodicInterestReceivableView = new ReadOnlyView("periodicInterestReceivableField", new PropertyModel<>(this, "periodicInterestReceivableValue"));
        this.periodicInterestReceivableVContainer.add(this.periodicInterestReceivableView);

        this.periodicFeeReceivableBlock = new WebMarkupBlock("periodicFeeReceivableBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicFeeReceivableBlock);
        this.periodicFeeReceivableVContainer = new WebMarkupContainer("periodicFeeReceivableVContainer");
        this.periodicFeeReceivableBlock.add(this.periodicFeeReceivableVContainer);
        this.periodicFeeReceivableView = new ReadOnlyView("periodicFeeReceivableField", new PropertyModel<>(this, "periodicFeeReceivableValue"));
        this.periodicFeeReceivableVContainer.add(this.periodicFeeReceivableView);

        this.periodicPenaltyReceivableBlock = new WebMarkupBlock("periodicPenaltyReceivableBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicPenaltyReceivableBlock);
        this.periodicPenaltyReceivableVContainer = new WebMarkupContainer("periodicPenaltyReceivableVContainer");
        this.periodicPenaltyReceivableBlock.add(this.periodicPenaltyReceivableVContainer);
        this.periodicPenaltyReceivableView = new ReadOnlyView("periodicPenaltyReceivableField", new PropertyModel<>(this, "periodicPenaltyReceivableValue"));
        this.periodicPenaltyReceivableVContainer.add(this.periodicPenaltyReceivableView);

        this.periodicTransferInSuspenseBlock = new WebMarkupBlock("periodicTransferInSuspenseBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicTransferInSuspenseBlock);
        this.periodicTransferInSuspenseVContainer = new WebMarkupContainer("periodicTransferInSuspenseVContainer");
        this.periodicTransferInSuspenseBlock.add(this.periodicTransferInSuspenseVContainer);
        this.periodicTransferInSuspenseView = new ReadOnlyView("periodicTransferInSuspenseField", new PropertyModel<>(this, "periodicTransferInSuspenseValue"));
        this.periodicTransferInSuspenseVContainer.add(this.periodicTransferInSuspenseView);

        this.periodicIncomeFromInterestBlock = new WebMarkupBlock("periodicIncomeFromInterestBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicIncomeFromInterestBlock);
        this.periodicIncomeFromInterestVContainer = new WebMarkupContainer("periodicIncomeFromInterestVContainer");
        this.periodicIncomeFromInterestBlock.add(this.periodicIncomeFromInterestVContainer);
        this.periodicIncomeFromInterestView = new ReadOnlyView("periodicIncomeFromInterestField", new PropertyModel<>(this, "periodicIncomeFromInterestValue"));
        this.periodicIncomeFromInterestVContainer.add(this.periodicIncomeFromInterestView);

        this.periodicIncomeFromFeeBlock = new WebMarkupBlock("periodicIncomeFromFeeBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicIncomeFromFeeBlock);
        this.periodicIncomeFromFeeVContainer = new WebMarkupContainer("periodicIncomeFromFeeVContainer");
        this.periodicIncomeFromFeeBlock.add(this.periodicIncomeFromFeeVContainer);
        this.periodicIncomeFromFeeView = new ReadOnlyView("periodicIncomeFromFeeField", new PropertyModel<>(this, "periodicIncomeFromFeeValue"));
        this.periodicIncomeFromFeeVContainer.add(this.periodicIncomeFromFeeView);

        this.periodicIncomeFromPenaltyBlock = new WebMarkupBlock("periodicIncomeFromPenaltyBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicIncomeFromPenaltyBlock);
        this.periodicIncomeFromPenaltyVContainer = new WebMarkupContainer("periodicIncomeFromPenaltyVContainer");
        this.periodicIncomeFromPenaltyBlock.add(this.periodicIncomeFromPenaltyVContainer);
        this.periodicIncomeFromPenaltyView = new ReadOnlyView("periodicIncomeFromPenaltyField", new PropertyModel<>(this, "periodicIncomeFromPenaltyValue"));
        this.periodicIncomeFromPenaltyVContainer.add(this.periodicIncomeFromPenaltyView);

        this.periodicIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("periodicIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicIncomeFromRecoveryRepaymentBlock);
        this.periodicIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("periodicIncomeFromRecoveryRepaymentVContainer");
        this.periodicIncomeFromRecoveryRepaymentBlock.add(this.periodicIncomeFromRecoveryRepaymentVContainer);
        this.periodicIncomeFromRecoveryRepaymentView = new ReadOnlyView("periodicIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentValue"));
        this.periodicIncomeFromRecoveryRepaymentVContainer.add(this.periodicIncomeFromRecoveryRepaymentView);

        this.periodicLossWrittenOffBlock = new WebMarkupBlock("periodicLossWrittenOffBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicLossWrittenOffBlock);
        this.periodicLossWrittenOffVContainer = new WebMarkupContainer("periodicLossWrittenOffVContainer");
        this.periodicLossWrittenOffBlock.add(this.periodicLossWrittenOffVContainer);
        this.periodicLossWrittenOffView = new ReadOnlyView("periodicLossWrittenOffField", new PropertyModel<>(this, "periodicLossWrittenOffValue"));
        this.periodicLossWrittenOffVContainer.add(this.periodicLossWrittenOffView);

        this.periodicOverPaymentLiabilityBlock = new WebMarkupBlock("periodicOverPaymentLiabilityBlock", Size.Six_6);
        this.periodicVContainer.add(this.periodicOverPaymentLiabilityBlock);
        this.periodicOverPaymentLiabilityVContainer = new WebMarkupContainer("periodicOverPaymentLiabilityVContainer");
        this.periodicOverPaymentLiabilityBlock.add(this.periodicOverPaymentLiabilityVContainer);
        this.periodicOverPaymentLiabilityView = new ReadOnlyView("periodicOverPaymentLiabilityField", new PropertyModel<>(this, "periodicOverPaymentLiabilityValue"));
        this.periodicOverPaymentLiabilityVContainer.add(this.periodicOverPaymentLiabilityView);

    }

    protected void initAdvancedAccountingRule() {

        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        add(this.advancedAccountingRuleBlock);

        this.advancedAccountingRuleVContainer = new WebMarkupContainer("advancedAccountingRuleVContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleVContainer);

        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fee"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initSectionConfigurableTermsAndSettings() {

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Twelve_12);
        add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView = new ReadOnlyView("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView);

        this.configurableAmortizationBlock = new WebMarkupBlock("configurableAmortizationBlock", Size.Six_6);
        add(this.configurableAmortizationBlock);
        this.configurableAmortizationVContainer = new WebMarkupContainer("configurableAmortizationVContainer");
        this.configurableAmortizationBlock.add(this.configurableAmortizationVContainer);
        this.configurableAmortizationView = new ReadOnlyView("configurableAmortizationField", new PropertyModel<>(this, "configurableAmortizationValue"));
        this.configurableAmortizationVContainer.add(this.configurableAmortizationView);

        this.configurableInterestMethodBlock = new WebMarkupBlock("configurableInterestMethodBlock", Size.Six_6);
        add(this.configurableInterestMethodBlock);
        this.configurableInterestMethodVContainer = new WebMarkupContainer("configurableInterestMethodVContainer");
        this.configurableInterestMethodBlock.add(this.configurableInterestMethodVContainer);
        this.configurableInterestMethodView = new ReadOnlyView("configurableInterestMethodField", new PropertyModel<>(this, "configurableInterestMethodValue"));
        this.configurableInterestMethodVContainer.add(this.configurableInterestMethodView);

        this.configurableRepaymentStrategyBlock = new WebMarkupBlock("configurableRepaymentStrategyBlock", Size.Six_6);
        add(this.configurableRepaymentStrategyBlock);
        this.configurableRepaymentStrategyVContainer = new WebMarkupContainer("configurableRepaymentStrategyVContainer");
        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyVContainer);
        this.configurableRepaymentStrategyView = new ReadOnlyView("configurableRepaymentStrategyField", new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyVContainer.add(this.configurableRepaymentStrategyView);

        this.configurableInterestCalculationPeriodBlock = new WebMarkupBlock("configurableInterestCalculationPeriodBlock", Size.Six_6);
        add(this.configurableInterestCalculationPeriodBlock);
        this.configurableInterestCalculationPeriodVContainer = new WebMarkupContainer("configurableInterestCalculationPeriodVContainer");
        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodVContainer);
        this.configurableInterestCalculationPeriodView = new ReadOnlyView("configurableInterestCalculationPeriodField", new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodVContainer.add(this.configurableInterestCalculationPeriodView);

        this.configurableArrearsToleranceBlock = new WebMarkupBlock("configurableArrearsToleranceBlock", Size.Six_6);
        add(this.configurableArrearsToleranceBlock);
        this.configurableArrearsToleranceVContainer = new WebMarkupContainer("configurableArrearsToleranceVContainer");
        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceVContainer);
        this.configurableArrearsToleranceView = new ReadOnlyView("configurableArrearsToleranceField", new PropertyModel<>(this, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceVContainer.add(this.configurableArrearsToleranceView);

        this.configurableRepaidEveryBlock = new WebMarkupBlock("configurableRepaidEveryBlock", Size.Six_6);
        add(this.configurableRepaidEveryBlock);
        this.configurableRepaidEveryVContainer = new WebMarkupContainer("configurableRepaidEveryVContainer");
        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryVContainer);
        this.configurableRepaidEveryView = new ReadOnlyView("configurableRepaidEveryField", new PropertyModel<>(this, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryVContainer.add(this.configurableRepaidEveryView);

        this.configurableMoratoriumBlock = new WebMarkupBlock("configurableMoratoriumBlock", Size.Six_6);
        add(this.configurableMoratoriumBlock);
        this.configurableMoratoriumVContainer = new WebMarkupContainer("configurableMoratoriumVContainer");
        this.configurableMoratoriumBlock.add(this.configurableMoratoriumVContainer);
        this.configurableMoratoriumView = new ReadOnlyView("configurableMoratoriumField", new PropertyModel<>(this, "configurableMoratoriumValue"));
        this.configurableMoratoriumVContainer.add(this.configurableMoratoriumView);

        this.configurableOverdueBeforeMovingBlock = new WebMarkupBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
        add(this.configurableOverdueBeforeMovingBlock);
        this.configurableOverdueBeforeMovingVContainer = new WebMarkupContainer("configurableOverdueBeforeMovingVContainer");
        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingVContainer);
        this.configurableOverdueBeforeMovingView = new ReadOnlyView("configurableOverdueBeforeMovingField", new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingVContainer.add(this.configurableOverdueBeforeMovingView);
    }

    protected void initSectionLoanTrancheDetails() {

        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Twelve_12);
        add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
        this.loanTrancheDetailEnableMultipleDisbursalVContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalVContainer");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalVContainer);
        this.loanTrancheDetailEnableMultipleDisbursalView = new ReadOnlyView("loanTrancheDetailEnableMultipleDisbursalField", new PropertyModel<>(this, "loanTrancheDetailEnableMultipleDisbursalValue"));
        this.loanTrancheDetailEnableMultipleDisbursalVContainer.add(this.loanTrancheDetailEnableMultipleDisbursalView);

        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Six_6);
        add(this.loanTrancheDetailMaximumTrancheCountBlock);
        this.loanTrancheDetailMaximumTrancheCountVContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountVContainer");
        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountVContainer);
        this.loanTrancheDetailMaximumTrancheCountView = new ReadOnlyView("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountVContainer.add(this.loanTrancheDetailMaximumTrancheCountView);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Six_6);
        add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView = new ReadOnlyView("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceView);
    }

    protected void initSectionGuaranteeRequirements() {

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Twelve_12);
        add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView = new ReadOnlyView("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", new PropertyModel<>(this, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);

        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Six_6);
        add(this.guaranteeRequirementMandatoryGuaranteeBlock);
        this.guaranteeRequirementMandatoryGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeVContainer");
        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeVContainer);
        this.guaranteeRequirementMandatoryGuaranteeView = new ReadOnlyView("guaranteeRequirementMandatoryGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMandatoryGuaranteeValue"));
        this.guaranteeRequirementMandatoryGuaranteeVContainer.add(this.guaranteeRequirementMandatoryGuaranteeView);

        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Six_6);
        add(this.guaranteeRequirementMinimumGuaranteeBlock);
        this.guaranteeRequirementMinimumGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeVContainer");
        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeVContainer);
        this.guaranteeRequirementMinimumGuaranteeView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeValue"));
        this.guaranteeRequirementMinimumGuaranteeVContainer.add(this.guaranteeRequirementMinimumGuaranteeView);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Six_6);
        add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeFromGuarantorField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorView);

    }

    protected void initSectionInterestRecalculation() {

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
        add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestVContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestVContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestVContainer);
        this.interestRecalculationRecalculateInterestView = new ReadOnlyView("interestRecalculationRecalculateInterestField", new PropertyModel<>(this, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestView);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
        add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleVContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleVContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleView = new ReadOnlyView("interestRecalculationPreClosureInterestCalculationRuleField", new PropertyModel<>(this, "interestRecalculationPreClosureInterestCalculationRuleValue"));
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleView);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
        add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeVContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeView = new ReadOnlyView("interestRecalculationAdvancePaymentsAdjustmentTypeField", new PropertyModel<>(this, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeView);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnVContainer = new WebMarkupContainer("interestRecalculationCompoundingOnVContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnVContainer);
        this.interestRecalculationCompoundingOnView = new ReadOnlyView("interestRecalculationCompoundingOnField", new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"));
        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnView);

        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Four_4);
        add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingVContainer = new WebMarkupContainer("interestRecalculationCompoundingVContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingVContainer);
        this.interestRecalculationCompoundingView = new ReadOnlyView("interestRecalculationCompoundingField", new PropertyModel<>(this, "interestRecalculationCompoundingValue"));
        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingView);

        this.interestRecalculationCompoundingTypeBlock = new WebMarkupBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
        add(this.interestRecalculationCompoundingTypeBlock);
        this.interestRecalculationCompoundingTypeVContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeVContainer");
        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeVContainer);
        this.interestRecalculationCompoundingTypeView = new ReadOnlyView("interestRecalculationCompoundingTypeField", new PropertyModel<>(this, "interestRecalculationCompoundingTypeValue"));
        this.interestRecalculationCompoundingTypeVContainer.add(this.interestRecalculationCompoundingTypeView);

        this.interestRecalculationCompoundingDayBlock = new WebMarkupBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);
        add(this.interestRecalculationCompoundingDayBlock);
        this.interestRecalculationCompoundingDayVContainer = new WebMarkupContainer("interestRecalculationCompoundingDayVContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayVContainer);
        this.interestRecalculationCompoundingDayView = new ReadOnlyView("interestRecalculationCompoundingDayField", new PropertyModel<>(this, "interestRecalculationCompoundingDayValue"));
        this.interestRecalculationCompoundingDayVContainer.add(this.interestRecalculationCompoundingDayView);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
        add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalVContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalVContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalVContainer);
        this.interestRecalculationCompoundingIntervalView = new ReadOnlyView("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalView);

        this.interestRecalculationRecalculateBlock = new WebMarkupBlock("interestRecalculationRecalculateBlock", Size.Four_4);
        add(this.interestRecalculationRecalculateBlock);
        this.interestRecalculationRecalculateVContainer = new WebMarkupContainer("interestRecalculationRecalculateVContainer");
        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateVContainer);
        this.interestRecalculationRecalculateView = new ReadOnlyView("interestRecalculationRecalculateField", new PropertyModel<>(this, "interestRecalculationRecalculateValue"));
        this.interestRecalculationRecalculateVContainer.add(this.interestRecalculationRecalculateView);

        this.interestRecalculationRecalculateTypeBlock = new WebMarkupBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
        add(this.interestRecalculationRecalculateTypeBlock);
        this.interestRecalculationRecalculateTypeVContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeVContainer");
        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeVContainer);
        this.interestRecalculationRecalculateTypeView = new ReadOnlyView("interestRecalculationRecalculateTypeField", new PropertyModel<>(this, "interestRecalculationRecalculateTypeValue"));
        this.interestRecalculationRecalculateTypeVContainer.add(this.interestRecalculationRecalculateTypeView);

        this.interestRecalculationRecalculateDayBlock = new WebMarkupBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);
        add(this.interestRecalculationRecalculateDayBlock);
        this.interestRecalculationRecalculateDayVContainer = new WebMarkupContainer("interestRecalculationRecalculateDayVContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayVContainer);
        this.interestRecalculationRecalculateDayView = new ReadOnlyView("interestRecalculationRecalculateDayField", new PropertyModel<>(this, "interestRecalculationRecalculateDayValue"));
        this.interestRecalculationRecalculateDayVContainer.add(this.interestRecalculationRecalculateDayView);

        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
        add(this.interestRecalculationRecalculateIntervalBlock);
        this.interestRecalculationRecalculateIntervalVContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalVContainer");
        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalVContainer);
        this.interestRecalculationRecalculateIntervalView = new ReadOnlyView("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalVContainer.add(this.interestRecalculationRecalculateIntervalView);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
        add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView = new ReadOnlyView("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);

    }

    protected void initSectionSetting() {

        this.settingAmortizationBlock = new WebMarkupBlock("settingAmortizationBlock", Size.Six_6);
        add(this.settingAmortizationBlock);
        this.settingAmortizationVContainer = new WebMarkupContainer("settingAmortizationVContainer");
        this.settingAmortizationBlock.add(this.settingAmortizationVContainer);
        this.settingAmortizationView = new ReadOnlyView("settingAmortizationField", new PropertyModel<>(this, "settingAmortizationValue"));
        this.settingAmortizationVContainer.add(this.settingAmortizationView);

        this.settingInterestMethodBlock = new WebMarkupBlock("settingInterestMethodBlock", Size.Six_6);
        add(this.settingInterestMethodBlock);
        this.settingInterestMethodVContainer = new WebMarkupContainer("settingInterestMethodVContainer");
        this.settingInterestMethodBlock.add(this.settingInterestMethodVContainer);
        this.settingInterestMethodView = new ReadOnlyView("settingInterestMethodField", new PropertyModel<>(this, "settingInterestMethodValue"));
        this.settingInterestMethodVContainer.add(this.settingInterestMethodView);

        this.settingInterestCalculationPeriodBlock = new WebMarkupBlock("settingInterestCalculationPeriodBlock", Size.Six_6);
        add(this.settingInterestCalculationPeriodBlock);
        this.settingInterestCalculationPeriodVContainer = new WebMarkupContainer("settingInterestCalculationPeriodVContainer");
        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodVContainer);
        this.settingInterestCalculationPeriodView = new ReadOnlyView("settingInterestCalculationPeriodField", new PropertyModel<>(this, "settingInterestCalculationPeriodValue"));
        this.settingInterestCalculationPeriodVContainer.add(this.settingInterestCalculationPeriodView);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
        add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodVContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodVContainer);
        this.settingCalculateInterestForExactDaysInPartialPeriodView = new ReadOnlyView("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodView);

        this.settingRepaymentStrategyBlock = new WebMarkupBlock("settingRepaymentStrategyBlock", Size.Six_6);
        add(this.settingRepaymentStrategyBlock);
        this.settingRepaymentStrategyVContainer = new WebMarkupContainer("settingRepaymentStrategyVContainer");
        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyVContainer);
        this.settingRepaymentStrategyView = new ReadOnlyView("settingRepaymentStrategyField", new PropertyModel<>(this, "settingRepaymentStrategyValue"));
        this.settingRepaymentStrategyVContainer.add(this.settingRepaymentStrategyView);

        this.settingMoratoriumPrincipalBlock = new WebMarkupBlock("settingMoratoriumPrincipalBlock", Size.Six_6);
        add(this.settingMoratoriumPrincipalBlock);
        this.settingMoratoriumPrincipalVContainer = new WebMarkupContainer("settingMoratoriumPrincipalVContainer");
        this.settingMoratoriumPrincipalBlock.add(this.settingMoratoriumPrincipalVContainer);
        this.settingMoratoriumPrincipalView = new ReadOnlyView("settingMoratoriumPrincipalField", new PropertyModel<>(this, "settingMoratoriumPrincipalValue"));
        this.settingMoratoriumPrincipalVContainer.add(this.settingMoratoriumPrincipalView);

        this.settingMoratoriumInterestBlock = new WebMarkupBlock("settingMoratoriumInterestBlock", Size.Six_6);
        add(this.settingMoratoriumInterestBlock);
        this.settingMoratoriumInterestVContainer = new WebMarkupContainer("settingMoratoriumInterestVContainer");
        this.settingMoratoriumInterestBlock.add(this.settingMoratoriumInterestVContainer);
        this.settingMoratoriumInterestView = new ReadOnlyView("settingMoratoriumInterestField", new PropertyModel<>(this, "settingMoratoriumInterestValue"));
        this.settingMoratoriumInterestVContainer.add(this.settingMoratoriumInterestView);

        this.settingInterestFreePeriodBlock = new WebMarkupBlock("settingInterestFreePeriodBlock", Size.Six_6);
        add(this.settingInterestFreePeriodBlock);
        this.settingInterestFreePeriodVContainer = new WebMarkupContainer("settingInterestFreePeriodVContainer");
        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodVContainer);
        this.settingInterestFreePeriodView = new ReadOnlyView("settingInterestFreePeriodField", new PropertyModel<>(this, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodVContainer.add(this.settingInterestFreePeriodView);

        this.settingArrearsToleranceBlock = new WebMarkupBlock("settingArrearsToleranceBlock", Size.Six_6);
        add(this.settingArrearsToleranceBlock);
        this.settingArrearsToleranceVContainer = new WebMarkupContainer("settingArrearsToleranceVContainer");
        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceVContainer);
        this.settingArrearsToleranceView = new ReadOnlyView("settingArrearsToleranceField", new PropertyModel<>(this, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceVContainer.add(this.settingArrearsToleranceView);

        this.settingDayInYearBlock = new WebMarkupBlock("settingDayInYearBlock", Size.Six_6);
        add(this.settingDayInYearBlock);
        this.settingDayInYearVContainer = new WebMarkupContainer("settingDayInYearVContainer");
        this.settingDayInYearBlock.add(this.settingDayInYearVContainer);
        this.settingDayInYearView = new ReadOnlyView("settingDayInYearField", new PropertyModel<>(this, "settingDayInYearValue"));
        this.settingDayInYearVContainer.add(this.settingDayInYearView);

        this.settingDayInMonthBlock = new WebMarkupBlock("settingDayInMonthBlock", Size.Six_6);
        add(this.settingDayInMonthBlock);
        this.settingDayInMonthVContainer = new WebMarkupContainer("settingDayInMonthVContainer");
        this.settingDayInMonthBlock.add(this.settingDayInMonthVContainer);
        this.settingDayInMonthView = new ReadOnlyView("settingDayInMonthField", new PropertyModel<>(this, "settingDayInMonthValue"));
        this.settingDayInMonthVContainer.add(this.settingDayInMonthView);

        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Twelve_12);
        add(this.settingAllowFixingOfTheInstallmentAmountBlock);
        this.settingAllowFixingOfTheInstallmentAmountVContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountVContainer");
        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountVContainer);
        this.settingAllowFixingOfTheInstallmentAmountView = new ReadOnlyView("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountVContainer.add(this.settingAllowFixingOfTheInstallmentAmountView);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Six_6);
        add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView = new ReadOnlyView("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Six_6);
        add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView = new ReadOnlyView("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
        add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView = new ReadOnlyView("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView);

        this.settingPrincipalThresholdForLastInstalmentBlock = new WebMarkupBlock("settingPrincipalThresholdForLastInstalmentBlock", Size.Six_6);
        add(this.settingPrincipalThresholdForLastInstalmentBlock);
        this.settingPrincipalThresholdForLastInstalmentVContainer = new WebMarkupContainer("settingPrincipalThresholdForLastInstalmentVContainer");
        this.settingPrincipalThresholdForLastInstalmentBlock.add(this.settingPrincipalThresholdForLastInstalmentVContainer);
        this.settingPrincipalThresholdForLastInstalmentView = new ReadOnlyView("settingPrincipalThresholdForLastInstalmentField", new PropertyModel<>(this, "settingPrincipalThresholdForLastInstalmentValue"));
        this.settingPrincipalThresholdForLastInstalmentVContainer.add(this.settingPrincipalThresholdForLastInstalmentView);

        this.settingVariableInstallmentsAllowedBlock = new WebMarkupBlock("settingVariableInstallmentsAllowedBlock", Size.Six_6);
        add(this.settingVariableInstallmentsAllowedBlock);
        this.settingVariableInstallmentsAllowedVContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedVContainer");
        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedVContainer);
        this.settingVariableInstallmentsAllowedView = new ReadOnlyView("settingVariableInstallmentsAllowedField", new PropertyModel<>(this, "settingVariableInstallmentsAllowedValue"));
        this.settingVariableInstallmentsAllowedVContainer.add(this.settingVariableInstallmentsAllowedView);

        this.settingVariableInstallmentsMinimumBlock = new WebMarkupBlock("settingVariableInstallmentsMinimumBlock", Size.Six_6);
        add(this.settingVariableInstallmentsMinimumBlock);
        this.settingVariableInstallmentsMinimumVContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumVContainer");
        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumVContainer);
        this.settingVariableInstallmentsMinimumView = new ReadOnlyView("settingVariableInstallmentsMinimumField", new PropertyModel<>(this, "settingVariableInstallmentsMinimumValue"));
        this.settingVariableInstallmentsMinimumVContainer.add(this.settingVariableInstallmentsMinimumView);

        this.settingVariableInstallmentsMaximumBlock = new WebMarkupBlock("settingVariableInstallmentsMaximumBlock", Size.Six_6);
        add(this.settingVariableInstallmentsMaximumBlock);
        this.settingVariableInstallmentsMaximumVContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumVContainer");
        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumVContainer);
        this.settingVariableInstallmentsMaximumView = new ReadOnlyView("settingVariableInstallmentsMaximumField", new PropertyModel<>(this, "settingVariableInstallmentsMaximumValue"));
        this.settingVariableInstallmentsMaximumVContainer.add(this.settingVariableInstallmentsMaximumView);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Twelve_12);
        add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansVContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansVContainer);
        this.settingAllowedToBeUsedForProvidingTopupLoansView = new ReadOnlyView("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansView);

    }

    protected void initSectionDetail() {

        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);

        this.detailFundBlock = new WebMarkupBlock("detailFundBlock", Size.Six_6);
        add(this.detailFundBlock);
        this.detailFundVContainer = new WebMarkupContainer("detailFundVContainer");
        this.detailFundBlock.add(this.detailFundVContainer);
        this.detailFundView = new ReadOnlyView("detailFundField", new PropertyModel<>(this, "detailFundValue"));
        this.detailFundVContainer.add(this.detailFundView);

        this.detailStartDateBlock = new WebMarkupBlock("detailStartDateBlock", Size.Six_6);
        add(this.detailStartDateBlock);
        this.detailStartDateVContainer = new WebMarkupContainer("detailStartDateVContainer");
        this.detailStartDateBlock.add(this.detailStartDateVContainer);
        this.detailStartDateView = new ReadOnlyView("detailStartDateField", new PropertyModel<>(this, "detailStartDateValue"));
        this.detailStartDateVContainer.add(this.detailStartDateView);

        this.detailCloseDateBlock = new WebMarkupBlock("detailCloseDateBlock", Size.Six_6);
        add(this.detailCloseDateBlock);
        this.detailCloseDateVContainer = new WebMarkupContainer("detailCloseDateVContainer");
        this.detailCloseDateBlock.add(this.detailCloseDateVContainer);
        this.detailCloseDateView = new ReadOnlyView("detailCloseDateField", new PropertyModel<>(this, "detailCloseDateValue"));
        this.detailCloseDateVContainer.add(this.detailCloseDateView);

        this.detailIncludeInCustomerLoanCounterBlock = new WebMarkupBlock("detailIncludeInCustomerLoanCounterBlock", Size.Twelve_12);
        add(this.detailIncludeInCustomerLoanCounterBlock);
        this.detailIncludeInCustomerLoanCounterVContainer = new WebMarkupContainer("detailIncludeInCustomerLoanCounterVContainer");
        this.detailIncludeInCustomerLoanCounterBlock.add(this.detailIncludeInCustomerLoanCounterVContainer);
        this.detailIncludeInCustomerLoanCounterView = new ReadOnlyView("detailIncludeInCustomerLoanCounterField", new PropertyModel<>(this, "detailIncludeInCustomerLoanCounterValue"));
        this.detailIncludeInCustomerLoanCounterVContainer.add(this.detailIncludeInCustomerLoanCounterView);
    }

    protected void initSectionCurrency() {

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeField", new PropertyModel<>(this, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);

        this.currencyInMultipleOfBlock = new WebMarkupBlock("currencyInMultipleOfBlock", Size.Six_6);
        add(this.currencyInMultipleOfBlock);
        this.currencyInMultipleOfVContainer = new WebMarkupContainer("currencyInMultipleOfVContainer");
        this.currencyInMultipleOfBlock.add(this.currencyInMultipleOfVContainer);
        this.currencyInMultipleOfView = new ReadOnlyView("currencyInMultipleOfField", new PropertyModel<>(this, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfVContainer.add(this.currencyInMultipleOfView);

        this.currencyInstallmentInMultipleOfBlock = new WebMarkupBlock("currencyInstallmentInMultipleOfBlock", Size.Six_6);
        add(this.currencyInstallmentInMultipleOfBlock);
        this.currencyInstallmentInMultipleOfVContainer = new WebMarkupContainer("currencyInstallmentInMultipleOfVContainer");
        this.currencyInstallmentInMultipleOfBlock.add(this.currencyInstallmentInMultipleOfVContainer);
        this.currencyInstallmentInMultipleOfView = new ReadOnlyView("currencyInstallmentInMultipleOfField", new PropertyModel<>(this, "currencyInstallmentInMultipleOfValue"));
        this.currencyInstallmentInMultipleOfVContainer.add(this.currencyInstallmentInMultipleOfView);
    }

    protected void initSectionTerms() {

        this.termVaryBasedOnLoanCycleBlock = new WebMarkupBlock("termVaryBasedOnLoanCycleBlock", Size.Twelve_12);
        add(this.termVaryBasedOnLoanCycleBlock);
        this.termVaryBasedOnLoanCycleVContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleVContainer");
        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleVContainer);
        this.termVaryBasedOnLoanCycleView = new ReadOnlyView("termVaryBasedOnLoanCycleField", new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleVContainer.add(this.termVaryBasedOnLoanCycleView);

        this.termPrincipalMinimumBlock = new WebMarkupBlock("termPrincipalMinimumBlock", Size.Four_4);
        add(this.termPrincipalMinimumBlock);
        this.termPrincipalMinimumVContainer = new WebMarkupContainer("termPrincipalMinimumVContainer");
        this.termPrincipalMinimumBlock.add(this.termPrincipalMinimumVContainer);
        this.termPrincipalMinimumView = new ReadOnlyView("termPrincipalMinimumField", new PropertyModel<>(this, "termPrincipalMinimumValue"));
        this.termPrincipalMinimumVContainer.add(this.termPrincipalMinimumView);

        this.termPrincipalDefaultBlock = new WebMarkupBlock("termPrincipalDefaultBlock", Size.Four_4);
        add(this.termPrincipalDefaultBlock);
        this.termPrincipalDefaultVContainer = new WebMarkupContainer("termPrincipalDefaultVContainer");
        this.termPrincipalDefaultBlock.add(this.termPrincipalDefaultVContainer);
        this.termPrincipalDefaultView = new ReadOnlyView("termPrincipalDefaultField", new PropertyModel<>(this, "termPrincipalDefaultValue"));
        this.termPrincipalDefaultVContainer.add(this.termPrincipalDefaultView);

        this.termPrincipalMaximumBlock = new WebMarkupBlock("termPrincipalMaximumBlock", Size.Four_4);
        add(this.termPrincipalMaximumBlock);
        this.termPrincipalMaximumVContainer = new WebMarkupContainer("termPrincipalMaximumVContainer");
        this.termPrincipalMaximumBlock.add(this.termPrincipalMaximumVContainer);
        this.termPrincipalMaximumView = new ReadOnlyView("termPrincipalMaximumField", new PropertyModel<>(this, "termPrincipalMaximumValue"));
        this.termPrincipalMaximumVContainer.add(this.termPrincipalMaximumView);

        this.termPrincipalByLoanCycleBlock = new WebMarkupBlock("termPrincipalByLoanCycleBlock", Size.Twelve_12);
        add(this.termPrincipalByLoanCycleBlock);
        this.termPrincipalByLoanCycleVContainer = new WebMarkupContainer("termPrincipalByLoanCycleVContainer");
        this.termPrincipalByLoanCycleBlock.add(this.termPrincipalByLoanCycleVContainer);

        this.termPrincipalByLoanCycleColumn = Lists.newArrayList();
        this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipalByLoanCycleColumn));
        this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipalByLoanCycleColumn));
        this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipalByLoanCycleColumn));
        this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipalByLoanCycleColumn));
        this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipalByLoanCycleColumn));
        this.termPrincipalByLoanCycleProvider = new ListDataProvider(this.termPrincipalByLoanCycleValue);
        this.termPrincipalByLoanCycleTable = new DataTable<>("termPrincipalByLoanCycleTable", this.termPrincipalByLoanCycleColumn, this.termPrincipalByLoanCycleProvider, 20);
        this.termPrincipalByLoanCycleVContainer.add(this.termPrincipalByLoanCycleTable);
        this.termPrincipalByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipalByLoanCycleTable, this.termPrincipalByLoanCycleProvider));
        this.termPrincipalByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipalByLoanCycleTable));

        this.termNumberOfRepaymentMinimumBlock = new WebMarkupBlock("termNumberOfRepaymentMinimumBlock", Size.Four_4);
        add(this.termNumberOfRepaymentMinimumBlock);
        this.termNumberOfRepaymentMinimumVContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumVContainer");
        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumVContainer);
        this.termNumberOfRepaymentMinimumView = new ReadOnlyView("termNumberOfRepaymentMinimumField", new PropertyModel<>(this, "termNumberOfRepaymentMinimumValue"));
        this.termNumberOfRepaymentMinimumVContainer.add(this.termNumberOfRepaymentMinimumView);

        this.termNumberOfRepaymentDefaultBlock = new WebMarkupBlock("termNumberOfRepaymentDefaultBlock", Size.Four_4);
        add(this.termNumberOfRepaymentDefaultBlock);
        this.termNumberOfRepaymentDefaultVContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultVContainer");
        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultVContainer);
        this.termNumberOfRepaymentDefaultView = new ReadOnlyView("termNumberOfRepaymentDefaultField", new PropertyModel<>(this, "termNumberOfRepaymentDefaultValue"));
        this.termNumberOfRepaymentDefaultVContainer.add(this.termNumberOfRepaymentDefaultView);

        this.termNumberOfRepaymentMaximumBlock = new WebMarkupBlock("termNumberOfRepaymentMaximumBlock", Size.Four_4);
        add(this.termNumberOfRepaymentMaximumBlock);
        this.termNumberOfRepaymentMaximumVContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumVContainer");
        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumVContainer);
        this.termNumberOfRepaymentMaximumView = new ReadOnlyView("termNumberOfRepaymentMaximumField", new PropertyModel<>(this, "termNumberOfRepaymentMaximumValue"));
        this.termNumberOfRepaymentMaximumVContainer.add(this.termNumberOfRepaymentMaximumView);

        this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
        add(this.termNumberOfRepaymentByLoanCycleBlock);
        this.termNumberOfRepaymentByLoanCycleVContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleVContainer");
        this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleVContainer);

        this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue);
        this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
        this.termNumberOfRepaymentByLoanCycleVContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
        this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
        this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));

        // Linked to Floating Interest Rates
        this.termLinkedToFloatingInterestRatesBlock = new WebMarkupBlock("termLinkedToFloatingInterestRatesBlock", Size.Twelve_12);

        add(this.termLinkedToFloatingInterestRatesBlock);
        this.termLinkedToFloatingInterestRatesVContainer = new WebMarkupContainer("termLinkedToFloatingInterestRatesVContainer");
        this.termLinkedToFloatingInterestRatesBlock.add(this.termLinkedToFloatingInterestRatesVContainer);
        this.termLinkedToFloatingInterestRatesView = new ReadOnlyView("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this, "termLinkedToFloatingInterestRatesValue"));
        this.termLinkedToFloatingInterestRatesVContainer.add(this.termLinkedToFloatingInterestRatesView);

        this.termNominalInterestRateMinimumBlock = new WebMarkupBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
        add(this.termNominalInterestRateMinimumBlock);
        this.termNominalInterestRateMinimumVContainer = new WebMarkupContainer("termNominalInterestRateMinimumVContainer");
        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumVContainer);
        this.termNominalInterestRateMinimumView = new ReadOnlyView("termNominalInterestRateMinimumField", new PropertyModel<>(this, "termNominalInterestRateMinimumValue"));
        this.termNominalInterestRateMinimumVContainer.add(this.termNominalInterestRateMinimumView);

        this.termNominalInterestRateDefaultBlock = new WebMarkupBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
        add(this.termNominalInterestRateDefaultBlock);
        this.termNominalInterestRateDefaultVContainer = new WebMarkupContainer("termNominalInterestRateDefaultVContainer");
        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultVContainer);
        this.termNominalInterestRateDefaultView = new ReadOnlyView("termNominalInterestRateDefaultField", new PropertyModel<>(this, "termNominalInterestRateDefaultValue"));
        this.termNominalInterestRateDefaultVContainer.add(this.termNominalInterestRateDefaultView);

        this.termNominalInterestRateMaximumBlock = new WebMarkupBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
        add(this.termNominalInterestRateMaximumBlock);
        this.termNominalInterestRateMaximumVContainer = new WebMarkupContainer("termNominalInterestRateMaximumVContainer");
        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumVContainer);
        this.termNominalInterestRateMaximumView = new ReadOnlyView("termNominalInterestRateMaximumField", new PropertyModel<>(this, "termNominalInterestRateMaximumValue"));
        this.termNominalInterestRateMaximumVContainer.add(this.termNominalInterestRateMaximumView);

        this.termNominalInterestRateTypeBlock = new WebMarkupBlock("termNominalInterestRateTypeBlock", Size.Three_3);
        add(this.termNominalInterestRateTypeBlock);
        this.termNominalInterestRateTypeVContainer = new WebMarkupContainer("termNominalInterestRateTypeVContainer");
        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeVContainer);
        this.termNominalInterestRateTypeView = new ReadOnlyView("termNominalInterestRateTypeField", new PropertyModel<>(this, "termNominalInterestRateTypeValue"));
        this.termNominalInterestRateTypeVContainer.add(this.termNominalInterestRateTypeView);

        this.termFloatingInterestRateBlock = new WebMarkupBlock("termFloatingInterestRateBlock", Size.Four_4);
        add(this.termFloatingInterestRateBlock);
        this.termFloatingInterestRateVContainer = new WebMarkupContainer("termFloatingInterestRateVContainer");
        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateVContainer);
        this.termFloatingInterestRateView = new ReadOnlyView("termFloatingInterestRateField", new PropertyModel<>(this, "termFloatingInterestRateValue"));
        this.termFloatingInterestRateVContainer.add(this.termFloatingInterestRateView);

        this.termFloatingInterestDifferentialBlock = new WebMarkupBlock("termFloatingInterestDifferentialBlock", Size.Four_4);
        add(this.termFloatingInterestDifferentialBlock);
        this.termFloatingInterestDifferentialVContainer = new WebMarkupContainer("termFloatingInterestDifferentialVContainer");
        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialVContainer);
        this.termFloatingInterestDifferentialView = new ReadOnlyView("termFloatingInterestDifferentialField", new PropertyModel<>(this, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialVContainer.add(this.termFloatingInterestDifferentialView);

        this.termFloatingInterestAllowedBlock = new WebMarkupBlock("termFloatingInterestAllowedBlock", Size.Four_4);
        add(this.termFloatingInterestAllowedBlock);
        this.termFloatingInterestAllowedVContainer = new WebMarkupContainer("termFloatingInterestAllowedVContainer");
        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedVContainer);
        this.termFloatingInterestAllowedView = new ReadOnlyView("termFloatingInterestAllowedField", new PropertyModel<>(this, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedVContainer.add(this.termFloatingInterestAllowedView);

        this.termFloatingInterestMinimumBlock = new WebMarkupBlock("termFloatingInterestMinimumBlock", Size.Four_4);
        add(this.termFloatingInterestMinimumBlock);
        this.termFloatingInterestMinimumVContainer = new WebMarkupContainer("termFloatingInterestMinimumVContainer");
        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumVContainer);
        this.termFloatingInterestMinimumView = new ReadOnlyView("termFloatingInterestMinimumField", new PropertyModel<>(this, "termFloatingInterestMinimumValue"));
        this.termFloatingInterestMinimumVContainer.add(this.termFloatingInterestMinimumView);

        this.termFloatingInterestDefaultBlock = new WebMarkupBlock("termFloatingInterestDefaultBlock", Size.Four_4);
        add(this.termFloatingInterestDefaultBlock);
        this.termFloatingInterestDefaultVContainer = new WebMarkupContainer("termFloatingInterestDefaultVContainer");
        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultVContainer);
        this.termFloatingInterestDefaultView = new ReadOnlyView("termFloatingInterestDefaultField", new PropertyModel<>(this, "termFloatingInterestDefaultValue"));
        this.termFloatingInterestDefaultVContainer.add(this.termFloatingInterestDefaultView);

        this.termFloatingInterestMaximumBlock = new WebMarkupBlock("termFloatingInterestMaximumBlock", Size.Four_4);
        add(this.termFloatingInterestMaximumBlock);
        this.termFloatingInterestMaximumVContainer = new WebMarkupContainer("termFloatingInterestMaximumVContainer");
        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumVContainer);
        this.termFloatingInterestMaximumView = new ReadOnlyView("termFloatingInterestMaximumField", new PropertyModel<>(this, "termFloatingInterestMaximumValue"));
        this.termFloatingInterestMaximumVContainer.add(this.termFloatingInterestMaximumView);

        this.termNominalInterestRateByLoanCycleBlock = new WebMarkupBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
        add(this.termNominalInterestRateByLoanCycleBlock);
        this.termNominalInterestRateByLoanCycleVContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleVContainer");
        this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleVContainer);

        List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
        termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleWhenColumn));
        termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleCycleColumn));
        termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleMinimumColumn));
        termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleDefaultColumn));
        termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleMaximumColumn));
        this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue);
        this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
        this.termNominalInterestRateByLoanCycleVContainer.add(this.termNominalInterestRateByLoanCycleTable);
        this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
        this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));

        this.termRepaidEveryBlock = new WebMarkupBlock("termRepaidEveryBlock", Size.Six_6);

        add(this.termRepaidEveryBlock);
        this.termRepaidEveryVContainer = new WebMarkupContainer("termRepaidEveryVContainer");
        this.termRepaidEveryBlock.add(this.termRepaidEveryVContainer);
        this.termRepaidEveryView = new ReadOnlyView("termRepaidEveryField", new PropertyModel<>(this, "termRepaidEveryValue"));
        this.termRepaidEveryVContainer.add(this.termRepaidEveryView);

        this.termRepaidTypeBlock = new WebMarkupBlock("termRepaidTypeBlock", Size.Six_6);
        add(this.termRepaidTypeBlock);
        this.termRepaidTypeVContainer = new WebMarkupContainer("termRepaidTypeVContainer");
        this.termRepaidTypeBlock.add(this.termRepaidTypeVContainer);
        this.termRepaidTypeView = new ReadOnlyView("termRepaidTypeField", new PropertyModel<>(this, "termRepaidTypeValue"));
        this.termRepaidTypeVContainer.add(this.termRepaidTypeView);

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
        add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView = new ReadOnlyView("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView);
    }

    protected ItemPanel termNominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel termNominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel termNominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel termNominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel termNominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long value = (Long) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected List<ActionItem> termNominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("when".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("cycle".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel termPrincipalByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("when".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("cycle".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void saveButtonSubmit(Button button) {

        setResponsePage(LoanBrowsePage.class);
    }

}
