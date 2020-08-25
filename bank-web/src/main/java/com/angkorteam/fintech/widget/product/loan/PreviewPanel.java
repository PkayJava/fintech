//package com.angkorteam.fintech.widget.product.loan;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.WebMarkupContainer;
//import org.apache.wicket.markup.html.basic.Label;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.dto.enums.AccountingType;
//import com.angkorteam.fintech.dto.enums.loan.Frequency;
//import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
//import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
//import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
//import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//
//public class PreviewPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//
//    protected PropertyModel<Boolean> errorSetting;
//    protected PropertyModel<Boolean> errorAccounting;
//    protected PropertyModel<Boolean> errorCharge;
//    protected PropertyModel<Boolean> errorCurrency;
//    protected PropertyModel<Boolean> errorDetail;
//    protected PropertyModel<Boolean> errorTerm;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    // Details
//
//    protected UIRow row1;
//
//    protected UIBlock detailProductNameBlock;
//    protected UIContainer detailProductNameVContainer;
//    protected ReadOnlyView detailProductNameView;
//
//    protected UIBlock detailShortNameBlock;
//    protected UIContainer detailShortNameVContainer;
//    protected ReadOnlyView detailShortNameView;
//
//    protected UIRow row2;
//
//    protected UIBlock detailDescriptionBlock;
//    protected UIContainer detailDescriptionVContainer;
//    protected ReadOnlyView detailDescriptionView;
//
//    protected UIRow row3;
//
//    protected UIBlock detailStartDateBlock;
//    protected UIContainer detailStartDateVContainer;
//    protected ReadOnlyView detailStartDateView;
//
//    protected UIBlock detailCloseDateBlock;
//    protected UIContainer detailCloseDateVContainer;
//    protected ReadOnlyView detailCloseDateView;
//
//    protected UIRow row4;
//
//    protected UIBlock detailIncludeInCustomerLoanCounterBlock;
//    protected UIContainer detailIncludeInCustomerLoanCounterVContainer;
//    protected ReadOnlyView detailIncludeInCustomerLoanCounterView;
//
//    protected UIBlock row4Block1;
//
//    // Currency
//
//    protected UIRow row5;
//
//    protected UIBlock currencyCodeBlock;
//    protected UIContainer currencyCodeVContainer;
//    protected ReadOnlyView currencyCodeView;
//
//    protected UIBlock currencyDecimalPlaceBlock;
//    protected UIContainer currencyDecimalPlaceVContainer;
//    protected ReadOnlyView currencyDecimalPlaceView;
//
//    protected UIBlock currencyInMultipleOfBlock;
//    protected UIContainer currencyInMultipleOfVContainer;
//    protected ReadOnlyView currencyInMultipleOfView;
//
//    protected UIBlock currencyInstallmentInMultipleOfBlock;
//    protected UIContainer currencyInstallmentInMultipleOfVContainer;
//    protected ReadOnlyView currencyInstallmentInMultipleOfView;
//
//    // Terms
//
//    protected UIRow row6;
//
//    protected UIBlock termVaryBasedOnLoanCycleBlock;
//    protected UIContainer termVaryBasedOnLoanCycleVContainer;
//    protected ReadOnlyView termVaryBasedOnLoanCycleView;
//
//    protected UIBlock row6Block1;
//
//    protected UIRow row7;
//
//    protected UIBlock termPrincipleMinimumBlock;
//    protected UIContainer termPrincipleMinimumVContainer;
//    protected ReadOnlyView termPrincipleMinimumView;
//
//    protected UIBlock termPrincipleDefaultBlock;
//    protected UIContainer termPrincipleDefaultVContainer;
//    protected ReadOnlyView termPrincipleDefaultView;
//
//    protected UIBlock termPrincipleMaximumBlock;
//    protected UIContainer termPrincipleMaximumVContainer;
//    protected ReadOnlyView termPrincipleMaximumView;
//
//    protected UIBlock row7Block1;
//
//    protected UIRow row8;
//
//    protected UIBlock termNumberOfRepaymentMinimumBlock;
//    protected UIContainer termNumberOfRepaymentMinimumVContainer;
//    protected ReadOnlyView termNumberOfRepaymentMinimumView;
//
//    protected UIBlock termNumberOfRepaymentDefaultBlock;
//    protected UIContainer termNumberOfRepaymentDefaultVContainer;
//    protected ReadOnlyView termNumberOfRepaymentDefaultView;
//
//    protected UIBlock termNumberOfRepaymentMaximumBlock;
//    protected UIContainer termNumberOfRepaymentMaximumVContainer;
//    protected ReadOnlyView termNumberOfRepaymentMaximumView;
//
//    protected UIBlock termRepaidEveryBlock;
//    protected UIContainer termRepaidEveryVContainer;
//    protected ReadOnlyView termRepaidEveryView;
//
//    protected UIBlock termRepaidTypeBlock;
//    protected UIContainer termRepaidTypeVContainer;
//    protected ReadOnlyView termRepaidTypeView;
//
//    protected WebMarkupContainer floatingRateMaster;
//
//    protected UIRow row9;
//
//    protected UIBlock termFloatingInterestRateBlock;
//    protected UIContainer termFloatingInterestRateVContainer;
//    protected ReadOnlyView termFloatingInterestRateView;
//
//    protected UIBlock termFloatingInterestDifferentialBlock;
//    protected UIContainer termFloatingInterestDifferentialVContainer;
//    protected ReadOnlyView termFloatingInterestDifferentialView;
//
//    protected UIBlock termFloatingInterestAllowedBlock;
//    protected UIContainer termFloatingInterestAllowedVContainer;
//    protected ReadOnlyView termFloatingInterestAllowedView;
//
//    protected UIBlock row9Block1;
//
//    protected UIRow row10;
//
//    protected UIBlock termFloatingInterestMinimumBlock;
//    protected UIContainer termFloatingInterestMinimumVContainer;
//    protected ReadOnlyView termFloatingInterestMinimumView;
//
//    protected UIBlock termFloatingInterestDefaultBlock;
//    protected UIContainer termFloatingInterestDefaultVContainer;
//    protected ReadOnlyView termFloatingInterestDefaultView;
//
//    protected UIBlock termFloatingInterestMaximumBlock;
//    protected UIContainer termFloatingInterestMaximumVContainer;
//    protected ReadOnlyView termFloatingInterestMaximumView;
//
//    protected UIBlock row10Block1;
//
//    protected WebMarkupContainer nominalRateMaster;
//
//    protected UIRow row11;
//
//    protected UIBlock termNominalInterestRateMinimumBlock;
//    protected UIContainer termNominalInterestRateMinimumVContainer;
//    protected ReadOnlyView termNominalInterestRateMinimumView;
//
//    protected UIBlock termNominalInterestRateDefaultBlock;
//    protected UIContainer termNominalInterestRateDefaultVContainer;
//    protected ReadOnlyView termNominalInterestRateDefaultView;
//
//    protected UIBlock termNominalInterestRateMaximumBlock;
//    protected UIContainer termNominalInterestRateMaximumVContainer;
//    protected ReadOnlyView termNominalInterestRateMaximumView;
//
//    protected UIBlock termNominalInterestRateTypeBlock;
//    protected UIContainer termNominalInterestRateTypeVContainer;
//    protected ReadOnlyView termNominalInterestRateTypeView;
//
//    protected WebMarkupContainer loanCycleMaster;
//
//    protected UIRow row12;
//
//    protected UIBlock termPrincipleByLoanCycleBlock;
//    protected UIContainer termPrincipleByLoanCycleVContainer;
//    protected PropertyModel<List<Map<String, Object>>> termPrincipleByLoanCycleValue;
//    protected DataTable<Map<String, Object>, String> termPrincipleByLoanCycleTable;
//    protected List<IColumn<Map<String, Object>, String>> termPrincipleByLoanCycleColumn;
//    protected ListDataProvider termPrincipleByLoanCycleProvider;
//
//    protected UIRow row13;
//
//    protected UIBlock termNumberOfRepaymentByLoanCycleBlock;
//    protected UIContainer termNumberOfRepaymentByLoanCycleVContainer;
//    protected PropertyModel<List<Map<String, Object>>> termNumberOfRepaymentByLoanCycleValue;
//    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
//    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
//    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
//
//    protected UIRow row14;
//
//    protected UIBlock termNominalInterestRateByLoanCycleBlock;
//    protected UIContainer termNominalInterestRateByLoanCycleVContainer;
//    protected PropertyModel<List<Map<String, Object>>> termNominalInterestRateByLoanCycleValue;
//    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
//    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
//    protected List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn;
//
//    protected UIRow row15;
//
//    protected UIBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
//    protected UIContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer;
//    protected ReadOnlyView termMinimumDayBetweenDisbursalAndFirstRepaymentDateView;
//
//    protected UIBlock row15Block1;
//
//    // Settings
//
//    protected UIRow row16;
//
//    protected UIBlock settingAmortizationBlock;
//    protected UIContainer settingAmortizationVContainer;
//    protected ReadOnlyView settingAmortizationView;
//
//    protected UIBlock settingInterestMethodBlock;
//    protected UIContainer settingInterestMethodVContainer;
//    protected ReadOnlyView settingInterestMethodView;
//
//    protected UIBlock settingEqualAmortizationBlock;
//    protected UIContainer settingEqualAmortizationVContainer;
//    protected ReadOnlyView settingEqualAmortizationView;
//
//    protected UIBlock settingInterestCalculationPeriodBlock;
//    protected UIContainer settingInterestCalculationPeriodVContainer;
//    protected ReadOnlyView settingInterestCalculationPeriodView;
//
//    protected UIRow row17;
//
//    protected UIBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
//    protected UIContainer settingCalculateInterestForExactDaysInPartialPeriodVContainer;
//    protected ReadOnlyView settingCalculateInterestForExactDaysInPartialPeriodView;
//
//    protected UIBlock settingArrearsToleranceBlock;
//    protected UIContainer settingArrearsToleranceVContainer;
//    protected ReadOnlyView settingArrearsToleranceView;
//
//    protected UIBlock settingRepaymentStrategyBlock;
//    protected UIContainer settingRepaymentStrategyVContainer;
//    protected ReadOnlyView settingRepaymentStrategyView;
//
//    protected UIRow row18;
//
//    protected UIBlock settingMoratoriumPrincipleBlock;
//    protected UIContainer settingMoratoriumPrincipleVContainer;
//    protected ReadOnlyView settingMoratoriumPrincipleView;
//
//    protected UIBlock settingInterestFreePeriodBlock;
//    protected UIContainer settingInterestFreePeriodVContainer;
//    protected ReadOnlyView settingInterestFreePeriodView;
//
//    protected UIBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
//    protected UIContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer;
//    protected ReadOnlyView settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView;
//
//    protected UIBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
//    protected UIContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer;
//    protected ReadOnlyView settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView;
//
//    protected UIRow row19;
//
//    protected UIBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
//    protected UIContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer;
//    protected ReadOnlyView settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView;
//
//    protected UIBlock settingDayInYearBlock;
//    protected UIContainer settingDayInYearVContainer;
//    protected ReadOnlyView settingDayInYearView;
//
//    protected UIBlock settingDayInMonthBlock;
//    protected UIContainer settingDayInMonthVContainer;
//    protected ReadOnlyView settingDayInMonthView;
//
//    protected UIRow row20;
//
//    protected UIBlock settingPrincipleThresholdForLastInstalmentBlock;
//    protected UIContainer settingPrincipleThresholdForLastInstalmentVContainer;
//    protected ReadOnlyView settingPrincipleThresholdForLastInstalmentView;
//
//    protected UIBlock settingAllowFixingOfTheInstallmentAmountBlock;
//    protected UIContainer settingAllowFixingOfTheInstallmentAmountVContainer;
//    protected ReadOnlyView settingAllowFixingOfTheInstallmentAmountView;
//
//    protected UIBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
//    protected UIContainer settingAllowedToBeUsedForProvidingTopupLoansVContainer;
//    protected ReadOnlyView settingAllowedToBeUsedForProvidingTopupLoansView;
//
//    // settingVariableMaster
//    protected WebMarkupContainer settingVariableMaster;
//
//    protected UIRow row21;
//
//    protected UIBlock settingVariableInstallmentsAllowedBlock;
//    protected UIContainer settingVariableInstallmentsAllowedVContainer;
//    protected ReadOnlyView settingVariableInstallmentsAllowedView;
//
//    protected UIBlock settingVariableInstallmentsMinimumBlock;
//    protected UIContainer settingVariableInstallmentsMinimumVContainer;
//    protected ReadOnlyView settingVariableInstallmentsMinimumView;
//
//    protected UIBlock settingVariableInstallmentsMaximumBlock;
//    protected UIContainer settingVariableInstallmentsMaximumVContainer;
//    protected ReadOnlyView settingVariableInstallmentsMaximumView;
//
//    // Interest Recalculation
//
//    protected UIRow row22;
//
//    protected UIBlock interestRecalculationRecalculateInterestBlock;
//    protected UIContainer interestRecalculationRecalculateInterestVContainer;
//    protected ReadOnlyView interestRecalculationRecalculateInterestView;
//
//    protected UIBlock row22Block1;
//
//    protected WebMarkupContainer interestRecalculationMaster;
//
//    protected UIRow row23;
//
//    protected UIBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
//    protected UIContainer interestRecalculationPreClosureInterestCalculationRuleVContainer;
//    protected ReadOnlyView interestRecalculationPreClosureInterestCalculationRuleView;
//
//    protected UIBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
//    protected UIContainer interestRecalculationAdvancePaymentsAdjustmentTypeVContainer;
//    protected ReadOnlyView interestRecalculationAdvancePaymentsAdjustmentTypeView;
//
//    protected UIBlock interestRecalculationCompoundingOnBlock;
//    protected UIContainer interestRecalculationCompoundingOnVContainer;
//    protected ReadOnlyView interestRecalculationCompoundingOnView;
//
//    protected UIRow row24;
//
//    protected UIBlock interestRecalculationCompoundingBlock;
//    protected UIContainer interestRecalculationCompoundingVContainer;
//    protected ReadOnlyView interestRecalculationCompoundingView;
//    protected String interestRecalculationCompoundingValue;
//
//    protected UIBlock interestRecalculationCompoundingIntervalBlock;
//    protected UIContainer interestRecalculationCompoundingIntervalVContainer;
//    protected ReadOnlyView interestRecalculationCompoundingIntervalView;
//
//    protected UIRow row25;
//
//    protected UIBlock interestRecalculationOutstandingPrincipalBlock;
//    protected UIContainer interestRecalculationOutstandingPrincipalVContainer;
//    protected ReadOnlyView interestRecalculationOutstandingPrincipalView;
//    protected String interestRecalculationOutstandingPrincipalValue;
//
//    protected UIBlock interestRecalculationOutstandingPrincipalIntervalBlock;
//    protected UIContainer interestRecalculationOutstandingPrincipalIntervalVContainer;
//    protected ReadOnlyView interestRecalculationOutstandingPrincipalIntervalView;
//
//    protected UIRow row26;
//
//    protected UIBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
//    protected UIContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer;
//    protected ReadOnlyView interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView;
//
//    protected UIBlock row26Block1;
//
//    // Guarantee Requirements
//
//    protected UIRow row27;
//
//    protected UIBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
//    protected UIContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer;
//    protected ReadOnlyView guaranteeRequirementPlaceGuaranteeFundsOnHoldView;
//
//    protected UIBlock row27Block1;
//
//    protected WebMarkupContainer guaranteeMaster;
//
//    protected UIRow row28;
//
//    protected UIBlock guaranteeRequirementMandatoryGuaranteeBlock;
//    protected UIContainer guaranteeRequirementMandatoryGuaranteeVContainer;
//    protected ReadOnlyView guaranteeRequirementMandatoryGuaranteeView;
//
//    protected UIBlock guaranteeRequirementMinimumGuaranteeBlock;
//    protected UIContainer guaranteeRequirementMinimumGuaranteeVContainer;
//    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeView;
//
//    protected UIBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
//    protected UIContainer guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer;
//    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeFromGuarantorView;
//
//    // Loan Tranche Details
//
//    protected UIRow row29;
//
//    protected UIBlock loanTrancheDetailEnableMultipleDisbursalBlock;
//    protected UIContainer loanTrancheDetailEnableMultipleDisbursalVContainer;
//    protected ReadOnlyView loanTrancheDetailEnableMultipleDisbursalView;
//
//    protected UIBlock loanTrancheDetailMaximumTrancheCountBlock;
//    protected UIContainer loanTrancheDetailMaximumTrancheCountVContainer;
//    protected ReadOnlyView loanTrancheDetailMaximumTrancheCountView;
//
//    protected UIBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
//    protected UIContainer loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer;
//    protected ReadOnlyView loanTrancheDetailMaximumAllowedOutstandingBalanceView;
//
//    // Configurable Terms and Settings
//
//    protected UIRow row30;
//
//    protected UIBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
//    protected UIContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer;
//    protected ReadOnlyView configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView;
//
//    protected UIBlock configurableAmortizationBlock;
//    protected UIContainer configurableAmortizationVContainer;
//    protected ReadOnlyView configurableAmortizationView;
//
//    protected UIBlock configurableInterestMethodBlock;
//    protected UIContainer configurableInterestMethodVContainer;
//    protected ReadOnlyView configurableInterestMethodView;
//
//    protected UIRow row31;
//
//    protected UIBlock configurableRepaymentStrategyBlock;
//    protected UIContainer configurableRepaymentStrategyVContainer;
//    protected ReadOnlyView configurableRepaymentStrategyView;
//
//    protected UIBlock configurableInterestCalculationPeriodBlock;
//    protected UIContainer configurableInterestCalculationPeriodVContainer;
//    protected ReadOnlyView configurableInterestCalculationPeriodView;
//
//    protected UIBlock configurableArrearsToleranceBlock;
//    protected UIContainer configurableArrearsToleranceVContainer;
//    protected ReadOnlyView configurableArrearsToleranceView;
//
//    protected UIRow row32;
//
//    protected UIBlock configurableRepaidEveryBlock;
//    protected UIContainer configurableRepaidEveryVContainer;
//    protected Boolean configurableRepaidEveryValue;
//    protected ReadOnlyView configurableRepaidEveryView;
//
//    protected UIBlock configurableMoratoriumBlock;
//    protected UIContainer configurableMoratoriumVContainer;
//    protected ReadOnlyView configurableMoratoriumView;
//
//    protected UIBlock configurableOverdueBeforeMovingBlock;
//    protected UIContainer configurableOverdueBeforeMovingVContainer;
//    protected ReadOnlyView configurableOverdueBeforeMovingView;
//
//    // Charge
//
//    protected UIRow row33;
//
//    protected UIBlock chargeBlock;
//    protected UIContainer chargeVContainer;
//    protected DataTable<Map<String, Object>, String> chargeTable;
//    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
//    protected ListDataProvider chargeProvider;
//    protected PropertyModel<List<Map<String, Object>>> chargeValue;
//
//    // Overdue Charge
//
//    protected UIRow row34;
//
//    protected UIBlock overdueChargeBlock;
//    protected UIContainer overdueChargeVContainer;
//    protected DataTable<Map<String, Object>, String> overdueChargeTable;
//    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
//    protected ListDataProvider overdueChargeProvider;
//    protected PropertyModel<List<Map<String, Object>>> overdueChargeValue;
//
//    protected Label accountingLabel;
//
//    protected WebMarkupContainer cashMaster;
//
//    protected UIRow row35;
//
//    protected UIBlock cashFundSourceBlock;
//    protected UIContainer cashFundSourceVContainer;
//    protected ReadOnlyView cashFundSourceView;
//
//    protected UIBlock cashLoanPortfolioBlock;
//    protected UIContainer cashLoanPortfolioVContainer;
//    protected ReadOnlyView cashLoanPortfolioView;
//
//    protected UIBlock cashTransferInSuspenseBlock;
//    protected UIContainer cashTransferInSuspenseVContainer;
//    protected ReadOnlyView cashTransferInSuspenseView;
//
//    protected UIRow row36;
//
//    protected UIBlock cashIncomeFromInterestBlock;
//    protected UIContainer cashIncomeFromInterestVContainer;
//    protected ReadOnlyView cashIncomeFromInterestView;
//
//    protected UIBlock cashIncomeFromFeeBlock;
//    protected UIContainer cashIncomeFromFeeVContainer;
//    protected ReadOnlyView cashIncomeFromFeeView;
//
//    protected UIBlock cashIncomeFromPenaltyBlock;
//    protected UIContainer cashIncomeFromPenaltyVContainer;
//    protected ReadOnlyView cashIncomeFromPenaltyView;
//
//    protected UIRow row37;
//
//    protected UIBlock cashIncomeFromRecoveryRepaymentBlock;
//    protected UIContainer cashIncomeFromRecoveryRepaymentVContainer;
//    protected ReadOnlyView cashIncomeFromRecoveryRepaymentView;
//
//    protected UIBlock cashLossWrittenOffBlock;
//    protected UIContainer cashLossWrittenOffVContainer;
//    protected ReadOnlyView cashLossWrittenOffView;
//
//    protected UIBlock cashOverPaymentLiabilityBlock;
//    protected UIContainer cashOverPaymentLiabilityVContainer;
//    protected ReadOnlyView cashOverPaymentLiabilityView;
//
//    protected WebMarkupContainer accrualMaster;
//
//    protected UIRow row38;
//
//    protected UIBlock accrualFundSourceBlock;
//    protected UIContainer accrualFundSourceVContainer;
//    protected ReadOnlyView accrualFundSourceView;
//
//    protected UIBlock accrualLoanPortfolioBlock;
//    protected UIContainer accrualLoanPortfolioVContainer;
//    protected ReadOnlyView accrualLoanPortfolioView;
//
//    protected UIBlock accrualInterestReceivableBlock;
//    protected UIContainer accrualInterestReceivableVContainer;
//    protected ReadOnlyView accrualInterestReceivableView;
//
//    protected UIRow row39;
//
//    protected UIBlock accrualFeeReceivableBlock;
//    protected UIContainer accrualFeeReceivableVContainer;
//    protected ReadOnlyView accrualFeeReceivableView;
//
//    protected UIBlock accrualPenaltyReceivableBlock;
//    protected UIContainer accrualPenaltyReceivableVContainer;
//    protected ReadOnlyView accrualPenaltyReceivableView;
//
//    protected UIBlock accrualTransferInSuspenseBlock;
//    protected UIContainer accrualTransferInSuspenseVContainer;
//    protected ReadOnlyView accrualTransferInSuspenseView;
//
//    protected UIRow row40;
//
//    protected UIBlock accrualIncomeFromInterestBlock;
//    protected UIContainer accrualIncomeFromInterestVContainer;
//    protected ReadOnlyView accrualIncomeFromInterestView;
//
//    protected UIBlock accrualIncomeFromFeeBlock;
//    protected UIContainer accrualIncomeFromFeeVContainer;
//    protected ReadOnlyView accrualIncomeFromFeeView;
//
//    protected UIBlock accrualIncomeFromPenaltyBlock;
//    protected UIContainer accrualIncomeFromPenaltyVContainer;
//    protected ReadOnlyView accrualIncomeFromPenaltyView;
//
//    protected UIRow row41;
//
//    protected UIBlock accrualIncomeFromRecoveryRepaymentBlock;
//    protected UIContainer accrualIncomeFromRecoveryRepaymentVContainer;
//    protected ReadOnlyView accrualIncomeFromRecoveryRepaymentView;
//
//    protected UIBlock accrualLossWrittenOffBlock;
//    protected UIContainer accrualLossWrittenOffVContainer;
//    protected ReadOnlyView accrualLossWrittenOffView;
//
//    protected UIBlock accrualOverPaymentLiabilityBlock;
//    protected UIContainer accrualOverPaymentLiabilityVContainer;
//    protected ReadOnlyView accrualOverPaymentLiabilityView;
//
//    protected WebMarkupContainer advancedAccountingRuleMaster;
//
//    protected UIRow row42;
//
//    protected UIBlock row42Block1;
//
//    protected UIContainer advancedAccountingRuleFundSourceVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
//    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;
//
//    protected UIContainer advancedAccountingRuleFeeIncomeVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
//    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;
//
//    protected UIContainer advancedAccountingRulePenaltyIncomeVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
//    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;
//
//    public PreviewPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
//        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
//        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
//        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
//        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
//        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
//
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//
//        this.termPrincipleByLoanCycleValue = new PropertyModel<>(this.itemPage, "termPrincipleByLoanCycleValue");
//        this.termPrincipleByLoanCycleColumn = Lists.newArrayList();
//        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipleByLoanCycleColumn));
//        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipleByLoanCycleColumn));
//        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipleByLoanCycleColumn));
//        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipleByLoanCycleColumn));
//        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipleByLoanCycleColumn));
//        this.termPrincipleByLoanCycleProvider = new ListDataProvider(this.termPrincipleByLoanCycleValue.getObject());
//
//        this.termNominalInterestRateByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateByLoanCycleValue");
//        this.termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
//        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleColumn));
//        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleColumn));
//        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleColumn));
//        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleColumn));
//        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleColumn));
//        this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue.getObject());
//
//        this.termNumberOfRepaymentByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentByLoanCycleValue");
//        this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
//        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
//        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
//        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
//        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
//        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
//        this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue.getObject());
//
//        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
//        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
//        this.chargeColumn = Lists.newArrayList();
//        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
//
//        this.overdueChargeValue = new PropertyModel<>(this.itemPage, "overdueChargeValue");
//        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue.getObject());
//        this.overdueChargeColumn = Lists.newArrayList();
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
//
//        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
//        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());
//        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
//
//        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
//        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());
//        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
//
//        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");
//        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());
//        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
//
//        PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");
//        PropertyModel<Option> interestRecalculationCompoundingTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue");
//        PropertyModel<Option> interestRecalculationCompoundingDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingDayValue");
//        PropertyModel<Option> interestRecalculationCompoundingOnDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnDayValue");
//        Frequency compoundFrequency = null;
//        if (interestRecalculationCompoundingValue.getObject() != null) {
//            compoundFrequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
//            if (compoundFrequency != null) {
//                if (compoundFrequency == Frequency.Same || compoundFrequency == Frequency.Daily) {
//                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
//                } else if (compoundFrequency == Frequency.Weekly) {
//                    if (interestRecalculationCompoundingDayValue.getObject() != null) {
//                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + interestRecalculationCompoundingDayValue.getObject().getText();
//                    } else {
//                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
//                    }
//                } else if (compoundFrequency == Frequency.Monthly) {
//                    if (interestRecalculationCompoundingTypeValue.getObject() != null) {
//                        FrequencyType frequencyType = FrequencyType.valueOf(interestRecalculationCompoundingTypeValue.getObject().getId());
//                        if (frequencyType != null) {
//                            if (frequencyType == FrequencyType.OnDay) {
//                                if (interestRecalculationCompoundingOnDayValue.getObject() != null) {
//                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On Day " + interestRecalculationCompoundingOnDayValue.getObject().getText();
//                                } else {
//                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
//                                }
//                            } else {
//                                if (interestRecalculationCompoundingDayValue.getObject() != null) {
//                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + frequencyType.getDescription() + " " + interestRecalculationCompoundingDayValue.getObject().getText();
//                                } else {
//                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + frequencyType.getDescription() + " Day";
//                                }
//                            }
//                        } else {
//                            this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
//                        }
//                    } else {
//                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
//                    }
//                }
//            }
//        }
//
//        PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
//        PropertyModel<Option> interestRecalculationRecalculateTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue");
//        PropertyModel<Option> interestRecalculationRecalculateDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateDayValue");
//        PropertyModel<Option> interestRecalculationRecalculateOnDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateOnDayValue");
//        Frequency outstandingPrincipalFrequency = null;
//        if (interestRecalculationRecalculateValue.getObject() != null) {
//            outstandingPrincipalFrequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
//            if (outstandingPrincipalFrequency != null) {
//                if (outstandingPrincipalFrequency == Frequency.Same || outstandingPrincipalFrequency == Frequency.Daily) {
//                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
//                } else if (outstandingPrincipalFrequency == Frequency.Weekly) {
//                    if (interestRecalculationRecalculateDayValue.getObject() != null) {
//                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + interestRecalculationRecalculateDayValue.getObject().getText();
//                    } else {
//                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
//                    }
//                } else if (outstandingPrincipalFrequency == Frequency.Monthly) {
//                    if (interestRecalculationRecalculateTypeValue.getObject() != null) {
//                        FrequencyType frequencyType = FrequencyType.valueOf(interestRecalculationRecalculateTypeValue.getObject().getId());
//                        if (frequencyType != null) {
//                            if (frequencyType == FrequencyType.OnDay) {
//                                if (interestRecalculationRecalculateOnDayValue.getObject() != null) {
//                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On Day " + interestRecalculationRecalculateOnDayValue.getObject().getText();
//                                } else {
//                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
//                                }
//                            } else {
//                                if (interestRecalculationRecalculateDayValue.getObject() != null) {
//                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + frequencyType.getDescription() + " " + interestRecalculationRecalculateDayValue.getObject().getText();
//                                } else {
//                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + frequencyType.getDescription() + " Day";
//                                }
//                            }
//                        } else {
//                            this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
//                        }
//                    } else {
//                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        // Details
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
//        this.detailProductNameVContainer = this.detailProductNameBlock.newUIContainer("detailProductNameVContainer");
//        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
//        this.detailProductNameVContainer.add(this.detailProductNameView);
//
//        this.detailShortNameBlock = this.row1.newUIBlock("detailShortNameBlock", Size.Six_6);
//        this.detailShortNameVContainer = this.detailShortNameBlock.newUIContainer("detailShortNameVContainer");
//        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
//        this.detailShortNameVContainer.add(this.detailShortNameView);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.detailDescriptionBlock = this.row2.newUIBlock("detailDescriptionBlock", Size.Twelve_12);
//        this.detailDescriptionVContainer = this.detailDescriptionBlock.newUIContainer("detailDescriptionVContainer");
//        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
//        this.detailDescriptionVContainer.add(this.detailDescriptionView);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.detailStartDateBlock = this.row3.newUIBlock("detailStartDateBlock", Size.Six_6);
//        this.detailStartDateVContainer = this.detailStartDateBlock.newUIContainer("detailStartDateVContainer");
//        this.detailStartDateView = new ReadOnlyView("detailStartDateView", new PropertyModel<>(this.itemPage, "detailStartDateValue"), "dd/MM/YYYY");
//        this.detailStartDateVContainer.add(this.detailStartDateView);
//
//        this.detailCloseDateBlock = this.row3.newUIBlock("detailCloseDateBlock", Size.Six_6);
//        this.detailCloseDateVContainer = this.detailCloseDateBlock.newUIContainer("detailCloseDateVContainer");
//        this.detailCloseDateView = new ReadOnlyView("detailCloseDateView", new PropertyModel<>(this.itemPage, "detailCloseDateValue"), "dd/MM/YYYY");
//        this.detailCloseDateVContainer.add(this.detailCloseDateView);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.detailIncludeInCustomerLoanCounterBlock = this.row4.newUIBlock("detailIncludeInCustomerLoanCounterBlock", Size.Six_6);
//        this.detailIncludeInCustomerLoanCounterVContainer = this.detailIncludeInCustomerLoanCounterBlock.newUIContainer("detailIncludeInCustomerLoanCounterVContainer");
//        this.detailIncludeInCustomerLoanCounterView = new ReadOnlyView("detailIncludeInCustomerLoanCounterView", new PropertyModel<>(this.itemPage, "detailIncludeInCustomerLoanCounterValue"));
//        this.detailIncludeInCustomerLoanCounterVContainer.add(this.detailIncludeInCustomerLoanCounterView);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
//
//        // Currency
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.currencyCodeBlock = this.row5.newUIBlock("currencyCodeBlock", Size.Three_3);
//        this.currencyCodeVContainer = this.currencyCodeBlock.newUIContainer("currencyCodeVContainer");
//        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this.itemPage, "currencyCodeValue"));
//        this.currencyCodeVContainer.add(this.currencyCodeView);
//
//        this.currencyDecimalPlaceBlock = this.row5.newUIBlock("currencyDecimalPlaceBlock", Size.Three_3);
//        this.currencyDecimalPlaceVContainer = this.currencyDecimalPlaceBlock.newUIContainer("currencyDecimalPlaceVContainer");
//        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
//        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);
//
//        this.currencyInMultipleOfBlock = this.row5.newUIBlock("currencyInMultipleOfBlock", Size.Three_3);
//        this.currencyInMultipleOfVContainer = this.currencyInMultipleOfBlock.newUIContainer("currencyInMultipleOfVContainer");
//        this.currencyInMultipleOfView = new ReadOnlyView("currencyInMultipleOfView", new PropertyModel<>(this.itemPage, "currencyInMultipleOfValue"));
//        this.currencyInMultipleOfVContainer.add(this.currencyInMultipleOfView);
//
//        this.currencyInstallmentInMultipleOfBlock = this.row5.newUIBlock("currencyInstallmentInMultipleOfBlock", Size.Three_3);
//        this.currencyInstallmentInMultipleOfVContainer = this.currencyInstallmentInMultipleOfBlock.newUIContainer("currencyInstallmentInMultipleOfVContainer");
//        this.currencyInstallmentInMultipleOfView = new ReadOnlyView("currencyInstallmentInMultipleOfView", new PropertyModel<>(this.itemPage, "currencyInstallmentInMultipleOfValue"));
//        this.currencyInstallmentInMultipleOfVContainer.add(this.currencyInstallmentInMultipleOfView);
//
//        // Terms
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.termVaryBasedOnLoanCycleBlock = this.row6.newUIBlock("termVaryBasedOnLoanCycleBlock", Size.Three_3);
//        this.termVaryBasedOnLoanCycleVContainer = this.termVaryBasedOnLoanCycleBlock.newUIContainer("termVaryBasedOnLoanCycleVContainer");
//        this.termVaryBasedOnLoanCycleView = new ReadOnlyView("termVaryBasedOnLoanCycleView", new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue"));
//        this.termVaryBasedOnLoanCycleVContainer.add(this.termVaryBasedOnLoanCycleView);
//
//        this.row6Block1 = this.row6.newUIBlock("row6Block1", Size.Nine_9);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.termPrincipleMinimumBlock = this.row7.newUIBlock("termPrincipleMinimumBlock", Size.Three_3);
//        this.termPrincipleMinimumVContainer = this.termPrincipleMinimumBlock.newUIContainer("termPrincipleMinimumVContainer");
//        this.termPrincipleMinimumView = new ReadOnlyView("termPrincipleMinimumView", new PropertyModel<>(this.itemPage, "termPrincipalMinimumValue"));
//        this.termPrincipleMinimumVContainer.add(this.termPrincipleMinimumView);
//
//        this.termPrincipleDefaultBlock = this.row7.newUIBlock("termPrincipleDefaultBlock", Size.Three_3);
//        this.termPrincipleDefaultVContainer = this.termPrincipleDefaultBlock.newUIContainer("termPrincipleDefaultVContainer");
//        this.termPrincipleDefaultView = new ReadOnlyView("termPrincipleDefaultView", new PropertyModel<>(this.itemPage, "termPrincipalDefaultValue"));
//        this.termPrincipleDefaultVContainer.add(this.termPrincipleDefaultView);
//
//        this.termPrincipleMaximumBlock = this.row7.newUIBlock("termPrincipleMaximumBlock", Size.Three_3);
//        this.termPrincipleMaximumVContainer = this.termPrincipleMaximumBlock.newUIContainer("termPrincipleMaximumVContainer");
//        this.termPrincipleMaximumView = new ReadOnlyView("termPrincipleMaximumView", new PropertyModel<>(this.itemPage, "termPrincipalMaximumValue"));
//        this.termPrincipleMaximumVContainer.add(this.termPrincipleMaximumView);
//
//        this.row7Block1 = this.row7.newUIBlock("row7Block1", Size.Three_3);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.termNumberOfRepaymentMinimumBlock = this.row8.newUIBlock("termNumberOfRepaymentMinimumBlock", Size.Three_3);
//        this.termNumberOfRepaymentMinimumVContainer = this.termNumberOfRepaymentMinimumBlock.newUIContainer("termNumberOfRepaymentMinimumVContainer");
//        this.termNumberOfRepaymentMinimumView = new ReadOnlyView("termNumberOfRepaymentMinimumView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMinimumValue"));
//        this.termNumberOfRepaymentMinimumVContainer.add(this.termNumberOfRepaymentMinimumView);
//
//        this.termNumberOfRepaymentDefaultBlock = this.row8.newUIBlock("termNumberOfRepaymentDefaultBlock", Size.Three_3);
//        this.termNumberOfRepaymentDefaultVContainer = this.termNumberOfRepaymentDefaultBlock.newUIContainer("termNumberOfRepaymentDefaultVContainer");
//        this.termNumberOfRepaymentDefaultView = new ReadOnlyView("termNumberOfRepaymentDefaultView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentDefaultValue"));
//        this.termNumberOfRepaymentDefaultVContainer.add(this.termNumberOfRepaymentDefaultView);
//
//        this.termNumberOfRepaymentMaximumBlock = this.row8.newUIBlock("termNumberOfRepaymentMaximumBlock", Size.Three_3);
//        this.termNumberOfRepaymentMaximumVContainer = this.termNumberOfRepaymentMaximumBlock.newUIContainer("termNumberOfRepaymentMaximumVContainer");
//        this.termNumberOfRepaymentMaximumView = new ReadOnlyView("termNumberOfRepaymentMaximumView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMaximumValue"));
//        this.termNumberOfRepaymentMaximumVContainer.add(this.termNumberOfRepaymentMaximumView);
//
//        this.termRepaidEveryBlock = this.row8.newUIBlock("termRepaidEveryBlock", Size.One_1);
//        this.termRepaidEveryVContainer = this.termRepaidEveryBlock.newUIContainer("termRepaidEveryVContainer");
//        this.termRepaidEveryView = new ReadOnlyView("termRepaidEveryView", new PropertyModel<>(this.itemPage, "termRepaidEveryValue"));
//        this.termRepaidEveryVContainer.add(this.termRepaidEveryView);
//
//        this.termRepaidTypeBlock = this.row8.newUIBlock("termRepaidTypeBlock", Size.Two_2);
//        this.termRepaidTypeVContainer = this.termRepaidTypeBlock.newUIContainer("termRepaidTypeVContainer");
//        this.termRepaidTypeBlock.add(this.termRepaidTypeVContainer);
//        this.termRepaidTypeView = new ReadOnlyView("termRepaidTypeView", new PropertyModel<>(this.itemPage, "termRepaidTypeValue"));
//        this.termRepaidTypeVContainer.add(this.termRepaidTypeView);
//
//        this.floatingRateMaster = new WebMarkupContainer("floatingRateMaster");
//        this.floatingRateMaster.setOutputMarkupId(true);
//        this.form.add(this.floatingRateMaster);
//
//        this.row9 = UIRow.newUIRow("row9", this.floatingRateMaster);
//
//        this.termFloatingInterestRateBlock = this.row9.newUIBlock("termFloatingInterestRateBlock", Size.Three_3);
//        this.termFloatingInterestRateVContainer = this.termFloatingInterestRateBlock.newUIContainer("termFloatingInterestRateVContainer");
//        this.termFloatingInterestRateView = new ReadOnlyView("termFloatingInterestRateView", new PropertyModel<>(this.itemPage, "termFloatingInterestRateValue"));
//        this.termFloatingInterestRateVContainer.add(this.termFloatingInterestRateView);
//
//        this.termFloatingInterestDifferentialBlock = this.row9.newUIBlock("termFloatingInterestDifferentialBlock", Size.Three_3);
//        this.termFloatingInterestDifferentialVContainer = this.termFloatingInterestDifferentialBlock.newUIContainer("termFloatingInterestDifferentialVContainer");
//        this.termFloatingInterestDifferentialView = new ReadOnlyView("termFloatingInterestDifferentialView", new PropertyModel<>(this.itemPage, "termFloatingInterestDifferentialValue"));
//        this.termFloatingInterestDifferentialVContainer.add(this.termFloatingInterestDifferentialView);
//
//        this.termFloatingInterestAllowedBlock = this.row9.newUIBlock("termFloatingInterestAllowedBlock", Size.Three_3);
//        this.termFloatingInterestAllowedVContainer = this.termFloatingInterestAllowedBlock.newUIContainer("termFloatingInterestAllowedVContainer");
//        this.termFloatingInterestAllowedView = new ReadOnlyView("termFloatingInterestAllowedView", new PropertyModel<>(this.itemPage, "termFloatingInterestAllowedValue"));
//        this.termFloatingInterestAllowedVContainer.add(this.termFloatingInterestAllowedView);
//
//        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Three_3);
//
//        this.row10 = UIRow.newUIRow("row10", this.floatingRateMaster);
//
//        this.termFloatingInterestMinimumBlock = this.row10.newUIBlock("termFloatingInterestMinimumBlock", Size.Three_3);
//        this.termFloatingInterestMinimumVContainer = this.termFloatingInterestMinimumBlock.newUIContainer("termFloatingInterestMinimumVContainer");
//        this.termFloatingInterestMinimumView = new ReadOnlyView("termFloatingInterestMinimumView", new PropertyModel<>(this.itemPage, "termFloatingInterestMinimumValue"));
//        this.termFloatingInterestMinimumVContainer.add(this.termFloatingInterestMinimumView);
//
//        this.termFloatingInterestDefaultBlock = this.row10.newUIBlock("termFloatingInterestDefaultBlock", Size.Three_3);
//        this.termFloatingInterestDefaultVContainer = this.termFloatingInterestDefaultBlock.newUIContainer("termFloatingInterestDefaultVContainer");
//        this.termFloatingInterestDefaultView = new ReadOnlyView("termFloatingInterestDefaultView", new PropertyModel<>(this.itemPage, "termFloatingInterestDefaultValue"));
//        this.termFloatingInterestDefaultVContainer.add(this.termFloatingInterestDefaultView);
//
//        this.termFloatingInterestMaximumBlock = this.row10.newUIBlock("termFloatingInterestMaximumBlock", Size.Three_3);
//        this.termFloatingInterestMaximumVContainer = this.termFloatingInterestMaximumBlock.newUIContainer("termFloatingInterestMaximumVContainer");
//        this.termFloatingInterestMaximumView = new ReadOnlyView("termFloatingInterestMaximumView", new PropertyModel<>(this.itemPage, "termFloatingInterestMaximumValue"));
//        this.termFloatingInterestMaximumVContainer.add(this.termFloatingInterestMaximumView);
//
//        this.row10Block1 = this.row10.newUIBlock("row10Block1", Size.Three_3);
//
//        this.nominalRateMaster = new WebMarkupContainer("nominalRateMaster");
//        this.nominalRateMaster.setOutputMarkupId(true);
//        this.form.add(this.nominalRateMaster);
//
//        this.row11 = UIRow.newUIRow("row11", this.nominalRateMaster);
//
//        this.termNominalInterestRateMinimumBlock = this.row11.newUIBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
//        this.termNominalInterestRateMinimumVContainer = this.termNominalInterestRateMinimumBlock.newUIContainer("termNominalInterestRateMinimumVContainer");
//        this.termNominalInterestRateMinimumView = new ReadOnlyView("termNominalInterestRateMinimumView", new PropertyModel<>(this.itemPage, "termNominalInterestRateMinimumValue"));
//        this.termNominalInterestRateMinimumVContainer.add(this.termNominalInterestRateMinimumView);
//
//        this.termNominalInterestRateDefaultBlock = this.row11.newUIBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
//        this.termNominalInterestRateDefaultVContainer = this.termNominalInterestRateDefaultBlock.newUIContainer("termNominalInterestRateDefaultVContainer");
//        this.termNominalInterestRateDefaultView = new ReadOnlyView("termNominalInterestRateDefaultView", new PropertyModel<>(this.itemPage, "termNominalInterestRateDefaultValue"));
//        this.termNominalInterestRateDefaultVContainer.add(this.termNominalInterestRateDefaultView);
//
//        this.termNominalInterestRateMaximumBlock = this.row11.newUIBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
//        this.termNominalInterestRateMaximumVContainer = this.termNominalInterestRateMaximumBlock.newUIContainer("termNominalInterestRateMaximumVContainer");
//        this.termNominalInterestRateMaximumView = new ReadOnlyView("termNominalInterestRateMaximumView", new PropertyModel<>(this.itemPage, "termNominalInterestRateMaximumValue"));
//        this.termNominalInterestRateMaximumVContainer.add(this.termNominalInterestRateMaximumView);
//
//        this.termNominalInterestRateTypeBlock = this.row11.newUIBlock("termNominalInterestRateTypeBlock", Size.Three_3);
//        this.termNominalInterestRateTypeVContainer = this.termNominalInterestRateTypeBlock.newUIContainer("termNominalInterestRateTypeVContainer");
//        this.termNominalInterestRateTypeView = new ReadOnlyView("termNominalInterestRateTypeView", new PropertyModel<>(this.itemPage, "termNominalInterestRateTypeValue"));
//        this.termNominalInterestRateTypeVContainer.add(this.termNominalInterestRateTypeView);
//
//        this.loanCycleMaster = new WebMarkupContainer("loanCycleMaster");
//        this.loanCycleMaster.setOutputMarkupId(true);
//        this.form.add(this.loanCycleMaster);
//
//        this.row12 = UIRow.newUIRow("row12", this.loanCycleMaster);
//
//        this.termPrincipleByLoanCycleBlock = this.row12.newUIBlock("termPrincipleByLoanCycleBlock", Size.Twelve_12);
//        this.termPrincipleByLoanCycleVContainer = this.termPrincipleByLoanCycleBlock.newUIContainer("termPrincipleByLoanCycleVContainer");
//        this.termPrincipleByLoanCycleTable = new DataTable<>("termPrincipleByLoanCycleTable", this.termPrincipleByLoanCycleColumn, this.termPrincipleByLoanCycleProvider, this.termPrincipleByLoanCycleValue.getObject().size() + 1);
//        this.termPrincipleByLoanCycleVContainer.add(this.termPrincipleByLoanCycleTable);
//        this.termPrincipleByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipleByLoanCycleTable, this.termPrincipleByLoanCycleProvider));
//        this.termPrincipleByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipleByLoanCycleTable));
//
//        this.row13 = UIRow.newUIRow("row13", this.loanCycleMaster);
//
//        this.termNumberOfRepaymentByLoanCycleBlock = this.row13.newUIBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
//        this.termNumberOfRepaymentByLoanCycleVContainer = this.termNumberOfRepaymentByLoanCycleBlock.newUIContainer("termNumberOfRepaymentByLoanCycleVContainer");
//        this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, this.termNumberOfRepaymentByLoanCycleValue.getObject().size() + 1);
//        this.termNumberOfRepaymentByLoanCycleVContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
//        this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
//        this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));
//
//        this.row14 = UIRow.newUIRow("row14", this.loanCycleMaster);
//
//        this.termNominalInterestRateByLoanCycleBlock = this.row14.newUIBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
//        this.termNominalInterestRateByLoanCycleVContainer = this.termNominalInterestRateByLoanCycleBlock.newUIContainer("termNominalInterestRateByLoanCycleVContainer");
//        this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", this.termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, this.termNominalInterestRateByLoanCycleValue.getObject().size() + 1);
//        this.termNominalInterestRateByLoanCycleVContainer.add(this.termNominalInterestRateByLoanCycleTable);
//        this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
//        this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));
//
//        this.row15 = UIRow.newUIRow("row15", this.form);
//
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = this.row15.newUIBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer = this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.newUIContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer");
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView = new ReadOnlyView("termMinimumDayBetweenDisbursalAndFirstRepaymentDateView", new PropertyModel<>(this.itemPage, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView);
//
//        this.row15Block1 = this.row15.newUIBlock("row15Block1", Size.Six_6);
//
//        this.row16 = UIRow.newUIRow("row16", this.form);
//
//        this.settingAmortizationBlock = this.row16.newUIBlock("settingAmortizationBlock", Size.Three_3);
//        this.settingAmortizationVContainer = this.settingAmortizationBlock.newUIContainer("settingAmortizationVContainer");
//        this.settingAmortizationView = new ReadOnlyView("settingAmortizationView", new PropertyModel<>(this.itemPage, "settingAmortizationValue"));
//        this.settingAmortizationVContainer.add(this.settingAmortizationView);
//
//        this.settingInterestMethodBlock = this.row16.newUIBlock("settingInterestMethodBlock", Size.Three_3);
//        this.settingInterestMethodVContainer = this.settingInterestMethodBlock.newUIContainer("settingInterestMethodVContainer");
//        this.settingInterestMethodView = new ReadOnlyView("settingInterestMethodView", new PropertyModel<>(this.itemPage, "settingInterestMethodValue"));
//        this.settingInterestMethodVContainer.add(this.settingInterestMethodView);
//
//        this.settingEqualAmortizationBlock = this.row16.newUIBlock("settingEqualAmortizationBlock", Size.Three_3);
//        this.settingEqualAmortizationVContainer = this.settingEqualAmortizationBlock.newUIContainer("settingEqualAmortizationVContainer");
//        this.settingEqualAmortizationView = new ReadOnlyView("settingEqualAmortizationView", new PropertyModel<>(this.itemPage, "settingEqualAmortizationValue"));
//        this.settingEqualAmortizationVContainer.add(this.settingEqualAmortizationView);
//
//        this.settingArrearsToleranceBlock = this.row16.newUIBlock("settingArrearsToleranceBlock", Size.Three_3);
//        this.settingArrearsToleranceVContainer = this.settingArrearsToleranceBlock.newUIContainer("settingArrearsToleranceVContainer");
//        this.settingArrearsToleranceView = new ReadOnlyView("settingArrearsToleranceView", new PropertyModel<>(this.itemPage, "settingArrearsToleranceValue"));
//        this.settingArrearsToleranceVContainer.add(this.settingArrearsToleranceView);
//
//        this.row17 = UIRow.newUIRow("row17", this.form);
//
//        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = this.row17.newUIBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer = this.settingCalculateInterestForExactDaysInPartialPeriodBlock.newUIContainer("settingCalculateInterestForExactDaysInPartialPeriodVContainer");
//        this.settingCalculateInterestForExactDaysInPartialPeriodView = new ReadOnlyView("settingCalculateInterestForExactDaysInPartialPeriodView", new PropertyModel<>(this.itemPage, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodView);
//
//        this.settingInterestCalculationPeriodBlock = this.row17.newUIBlock("settingInterestCalculationPeriodBlock", Size.Three_3);
//        this.settingInterestCalculationPeriodVContainer = this.settingInterestCalculationPeriodBlock.newUIContainer("settingInterestCalculationPeriodVContainer");
//        this.settingInterestCalculationPeriodView = new ReadOnlyView("settingInterestCalculationPeriodView", new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue"));
//        this.settingInterestCalculationPeriodVContainer.add(this.settingInterestCalculationPeriodView);
//
//        this.settingRepaymentStrategyBlock = this.row17.newUIBlock("settingRepaymentStrategyBlock", Size.Three_3);
//        this.settingRepaymentStrategyVContainer = this.settingRepaymentStrategyBlock.newUIContainer("settingRepaymentStrategyVContainer");
//        this.settingRepaymentStrategyView = new ReadOnlyView("settingRepaymentStrategyView", new PropertyModel<>(this.itemPage, "settingRepaymentStrategyValue"));
//        this.settingRepaymentStrategyVContainer.add(this.settingRepaymentStrategyView);
//
//        this.row18 = UIRow.newUIRow("row18", this.form);
//
//        this.settingMoratoriumPrincipleBlock = this.row18.newUIBlock("settingMoratoriumPrincipleBlock", Size.Three_3);
//        this.settingMoratoriumPrincipleVContainer = this.settingMoratoriumPrincipleBlock.newUIContainer("settingMoratoriumPrincipleVContainer");
//        this.settingMoratoriumPrincipleView = new ReadOnlyView("settingMoratoriumPrincipleView", new PropertyModel<>(this.itemPage, "settingMoratoriumPrincipleValue"));
//        this.settingMoratoriumPrincipleVContainer.add(this.settingMoratoriumPrincipleView);
//
//        this.settingInterestFreePeriodBlock = this.row18.newUIBlock("settingInterestFreePeriodBlock", Size.Three_3);
//        this.settingInterestFreePeriodVContainer = this.settingInterestFreePeriodBlock.newUIContainer("settingInterestFreePeriodVContainer");
//        this.settingInterestFreePeriodView = new ReadOnlyView("settingInterestFreePeriodView", new PropertyModel<>(this.itemPage, "settingInterestFreePeriodValue"));
//        this.settingInterestFreePeriodVContainer.add(this.settingInterestFreePeriodView);
//
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = this.row18.newUIBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Three_3);
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer = this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.newUIContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer");
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView = new ReadOnlyView("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView);
//
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = this.row18.newUIBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Three_3);
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer = this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.newUIContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer");
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView = new ReadOnlyView("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView", new PropertyModel<>(this.itemPage, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView);
//
//        this.row19 = UIRow.newUIRow("row19", this.form);
//
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = this.row19.newUIBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer = this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.newUIContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer");
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView = new ReadOnlyView("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView", new PropertyModel<>(this.itemPage, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView);
//
//        this.settingDayInYearBlock = this.row19.newUIBlock("settingDayInYearBlock", Size.Three_3);
//        this.settingDayInYearVContainer = this.settingDayInYearBlock.newUIContainer("settingDayInYearVContainer");
//        this.settingDayInYearView = new ReadOnlyView("settingDayInYearView", new PropertyModel<>(this.itemPage, "settingDayInYearValue"));
//        this.settingDayInYearVContainer.add(this.settingDayInYearView);
//
//        this.settingDayInMonthBlock = this.row19.newUIBlock("settingDayInMonthBlock", Size.Three_3);
//        this.settingDayInMonthVContainer = this.settingDayInMonthBlock.newUIContainer("settingDayInMonthVContainer");
//        this.settingDayInMonthView = new ReadOnlyView("settingDayInMonthView", new PropertyModel<>(this.itemPage, "settingDayInMonthValue"));
//        this.settingDayInMonthVContainer.add(this.settingDayInMonthView);
//
//        this.row20 = UIRow.newUIRow("row20", this.form);
//
//        this.settingPrincipleThresholdForLastInstalmentBlock = this.row20.newUIBlock("settingPrincipleThresholdForLastInstalmentBlock", Size.Four_4);
//        this.settingPrincipleThresholdForLastInstalmentVContainer = this.settingPrincipleThresholdForLastInstalmentBlock.newUIContainer("settingPrincipleThresholdForLastInstalmentVContainer");
//        this.settingPrincipleThresholdForLastInstalmentView = new ReadOnlyView("settingPrincipleThresholdForLastInstalmentView", new PropertyModel<>(this.itemPage, "settingPrincipleThresholdForLastInstalmentValue"));
//        this.settingPrincipleThresholdForLastInstalmentVContainer.add(this.settingPrincipleThresholdForLastInstalmentView);
//
//        this.settingAllowFixingOfTheInstallmentAmountBlock = this.row20.newUIBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Four_4);
//        this.settingAllowFixingOfTheInstallmentAmountVContainer = this.settingAllowFixingOfTheInstallmentAmountBlock.newUIContainer("settingAllowFixingOfTheInstallmentAmountVContainer");
//        this.settingAllowFixingOfTheInstallmentAmountView = new ReadOnlyView("settingAllowFixingOfTheInstallmentAmountView", new PropertyModel<>(this.itemPage, "settingAllowFixingOfTheInstallmentAmountValue"));
//        this.settingAllowFixingOfTheInstallmentAmountVContainer.add(this.settingAllowFixingOfTheInstallmentAmountView);
//
//        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = this.row20.newUIBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Four_4);
//        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer = this.settingAllowedToBeUsedForProvidingTopupLoansBlock.newUIContainer("settingAllowedToBeUsedForProvidingTopupLoansVContainer");
//        this.settingAllowedToBeUsedForProvidingTopupLoansView = new ReadOnlyView("settingAllowedToBeUsedForProvidingTopupLoansView", new PropertyModel<>(this.itemPage, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
//        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansView);
//
//        this.settingVariableMaster = new WebMarkupContainer("settingVariableMaster");
//        this.settingVariableMaster.setOutputMarkupId(true);
//        this.form.add(this.settingVariableMaster);
//
//        this.row21 = UIRow.newUIRow("row21", this.settingVariableMaster);
//
//        this.settingVariableInstallmentsAllowedBlock = this.row21.newUIBlock("settingVariableInstallmentsAllowedBlock", Size.Four_4);
//        this.settingVariableInstallmentsAllowedVContainer = this.settingVariableInstallmentsAllowedBlock.newUIContainer("settingVariableInstallmentsAllowedVContainer");
//        this.settingVariableInstallmentsAllowedView = new ReadOnlyView("settingVariableInstallmentsAllowedView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue"));
//        this.settingVariableInstallmentsAllowedVContainer.add(this.settingVariableInstallmentsAllowedView);
//
//        this.settingVariableInstallmentsMinimumBlock = this.row21.newUIBlock("settingVariableInstallmentsMinimumBlock", Size.Four_4);
//        this.settingVariableInstallmentsMinimumVContainer = this.settingVariableInstallmentsMinimumBlock.newUIContainer("settingVariableInstallmentsMinimumVContainer");
//        this.settingVariableInstallmentsMinimumView = new ReadOnlyView("settingVariableInstallmentsMinimumView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMinimumValue"));
//        this.settingVariableInstallmentsMinimumVContainer.add(this.settingVariableInstallmentsMinimumView);
//
//        this.settingVariableInstallmentsMaximumBlock = this.row21.newUIBlock("settingVariableInstallmentsMaximumBlock", Size.Four_4);
//        this.settingVariableInstallmentsMaximumVContainer = this.settingVariableInstallmentsMaximumBlock.newUIContainer("settingVariableInstallmentsMaximumVContainer");
//        this.settingVariableInstallmentsMaximumView = new ReadOnlyView("settingVariableInstallmentsMaximumView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMaximumValue"));
//        this.settingVariableInstallmentsMaximumVContainer.add(this.settingVariableInstallmentsMaximumView);
//
//        this.row22 = UIRow.newUIRow("row22", this.form);
//
//        this.interestRecalculationRecalculateInterestBlock = this.row22.newUIBlock("interestRecalculationRecalculateInterestBlock", Size.Four_4);
//        this.interestRecalculationRecalculateInterestVContainer = this.interestRecalculationRecalculateInterestBlock.newUIContainer("interestRecalculationRecalculateInterestVContainer");
//        this.interestRecalculationRecalculateInterestView = new ReadOnlyView("interestRecalculationRecalculateInterestView", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue"));
//        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestView);
//
//        this.row22Block1 = this.row22.newUIBlock("row22Block1", Size.Eight_8);
//
//        this.interestRecalculationMaster = new WebMarkupContainer("interestRecalculationMaster");
//        this.interestRecalculationMaster.setOutputMarkupId(true);
//        this.form.add(this.interestRecalculationMaster);
//
//        this.row23 = UIRow.newUIRow("row23", this.interestRecalculationMaster);
//
//        this.interestRecalculationPreClosureInterestCalculationRuleBlock = this.row23.newUIBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Four_4);
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer = this.interestRecalculationPreClosureInterestCalculationRuleBlock.newUIContainer("interestRecalculationPreClosureInterestCalculationRuleVContainer");
//        this.interestRecalculationPreClosureInterestCalculationRuleView = new ReadOnlyView("interestRecalculationPreClosureInterestCalculationRuleView", new PropertyModel<>(this.itemPage, "interestRecalculationPreClosureInterestCalculationRuleValue"));
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleView);
//
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = this.row23.newUIBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Four_4);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer = this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.newUIContainer("interestRecalculationAdvancePaymentsAdjustmentTypeVContainer");
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeView = new ReadOnlyView("interestRecalculationAdvancePaymentsAdjustmentTypeView", new PropertyModel<>(this.itemPage, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"));
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeView);
//
//        this.interestRecalculationCompoundingOnBlock = this.row23.newUIBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
//        this.interestRecalculationCompoundingOnVContainer = this.interestRecalculationCompoundingOnBlock.newUIContainer("interestRecalculationCompoundingOnVContainer");
//        this.interestRecalculationCompoundingOnView = new ReadOnlyView("interestRecalculationCompoundingOnView", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue"));
//        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnView);
//
//        this.row24 = UIRow.newUIRow("row24", this.interestRecalculationMaster);
//
//        this.interestRecalculationCompoundingBlock = this.row24.newUIBlock("interestRecalculationCompoundingBlock", Size.Nine_9);
//        this.interestRecalculationCompoundingVContainer = this.interestRecalculationCompoundingBlock.newUIContainer("interestRecalculationCompoundingVContainer");
//        this.interestRecalculationCompoundingView = new ReadOnlyView("interestRecalculationCompoundingView", new PropertyModel<>(this, "interestRecalculationCompoundingValue"));
//        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingView);
//
//        this.interestRecalculationCompoundingIntervalBlock = this.row24.newUIBlock("interestRecalculationCompoundingIntervalBlock", Size.Three_3);
//        this.interestRecalculationCompoundingIntervalVContainer = this.interestRecalculationCompoundingIntervalBlock.newUIContainer("interestRecalculationCompoundingIntervalVContainer");
//        this.interestRecalculationCompoundingIntervalView = new ReadOnlyView("interestRecalculationCompoundingIntervalView", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingIntervalValue"));
//        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalView);
//
//        this.row25 = UIRow.newUIRow("row25", this.interestRecalculationMaster);
//
//        this.interestRecalculationOutstandingPrincipalBlock = this.row25.newUIBlock("interestRecalculationOutstandingPrincipalBlock", Size.Nine_9);
//        this.interestRecalculationOutstandingPrincipalVContainer = this.interestRecalculationOutstandingPrincipalBlock.newUIContainer("interestRecalculationOutstandingPrincipalVContainer");
//        this.interestRecalculationOutstandingPrincipalView = new ReadOnlyView("interestRecalculationOutstandingPrincipalView", new PropertyModel<>(this, "interestRecalculationOutstandingPrincipalValue"));
//        this.interestRecalculationOutstandingPrincipalVContainer.add(this.interestRecalculationOutstandingPrincipalView);
//
//        this.interestRecalculationOutstandingPrincipalIntervalBlock = this.row25.newUIBlock("interestRecalculationOutstandingPrincipalIntervalBlock", Size.Three_3);
//        this.interestRecalculationOutstandingPrincipalIntervalVContainer = this.interestRecalculationOutstandingPrincipalIntervalBlock.newUIContainer("interestRecalculationOutstandingPrincipalIntervalVContainer");
//        this.interestRecalculationOutstandingPrincipalIntervalView = new ReadOnlyView("interestRecalculationOutstandingPrincipalIntervalView", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateIntervalValue"));
//        this.interestRecalculationOutstandingPrincipalIntervalVContainer.add(this.interestRecalculationOutstandingPrincipalIntervalView);
//
//        this.row26 = UIRow.newUIRow("row26", this.interestRecalculationMaster);
//
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = this.row26.newUIBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Nine_9);
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer = this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.newUIContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer");
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView = new ReadOnlyView("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView", new PropertyModel<>(this.itemPage, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);
//
//        this.row26Block1 = this.row26.newUIBlock("row26Block1", Size.Three_3);
//
//        this.row27 = UIRow.newUIRow("row27", this.form);
//
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = this.row27.newUIBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Four_4);
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.newUIContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer");
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView = new ReadOnlyView("guaranteeRequirementPlaceGuaranteeFundsOnHoldView", new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);
//
//        this.row27Block1 = this.row27.newUIBlock("row27Block1", Size.Eight_8);
//
//        this.guaranteeMaster = new WebMarkupContainer("guaranteeMaster");
//        this.guaranteeMaster.setOutputMarkupId(true);
//        this.form.add(this.guaranteeMaster);
//
//        this.row28 = UIRow.newUIRow("row28", this.guaranteeMaster);
//
//        this.guaranteeRequirementMandatoryGuaranteeBlock = this.row28.newUIBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Four_4);
//        this.guaranteeRequirementMandatoryGuaranteeVContainer = this.guaranteeRequirementMandatoryGuaranteeBlock.newUIContainer("guaranteeRequirementMandatoryGuaranteeVContainer");
//        this.guaranteeRequirementMandatoryGuaranteeView = new ReadOnlyView("guaranteeRequirementMandatoryGuaranteeView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMandatoryGuaranteeValue"));
//        this.guaranteeRequirementMandatoryGuaranteeVContainer.add(this.guaranteeRequirementMandatoryGuaranteeView);
//
//        this.guaranteeRequirementMinimumGuaranteeBlock = this.row28.newUIBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Four_4);
//        this.guaranteeRequirementMinimumGuaranteeVContainer = this.guaranteeRequirementMinimumGuaranteeBlock.newUIContainer("guaranteeRequirementMinimumGuaranteeVContainer");
//        this.guaranteeRequirementMinimumGuaranteeView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeValue"));
//        this.guaranteeRequirementMinimumGuaranteeVContainer.add(this.guaranteeRequirementMinimumGuaranteeView);
//
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = this.row28.newUIBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Four_4);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer = this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.newUIContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer");
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeFromGuarantorView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorView);
//
//        this.row29 = UIRow.newUIRow("row29", this.form);
//
//        this.loanTrancheDetailEnableMultipleDisbursalBlock = this.row29.newUIBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Four_4);
//        this.loanTrancheDetailEnableMultipleDisbursalVContainer = this.loanTrancheDetailEnableMultipleDisbursalBlock.newUIContainer("loanTrancheDetailEnableMultipleDisbursalVContainer");
//        this.loanTrancheDetailEnableMultipleDisbursalView = new ReadOnlyView("loanTrancheDetailEnableMultipleDisbursalView", new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue"));
//        this.loanTrancheDetailEnableMultipleDisbursalVContainer.add(this.loanTrancheDetailEnableMultipleDisbursalView);
//
//        this.loanTrancheDetailMaximumTrancheCountBlock = this.row29.newUIBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Four_4);
//        this.loanTrancheDetailMaximumTrancheCountVContainer = this.loanTrancheDetailMaximumTrancheCountBlock.newUIContainer("loanTrancheDetailMaximumTrancheCountVContainer");
//        this.loanTrancheDetailMaximumTrancheCountView = new ReadOnlyView("loanTrancheDetailMaximumTrancheCountView", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumTrancheCountValue"));
//        this.loanTrancheDetailMaximumTrancheCountVContainer.add(this.loanTrancheDetailMaximumTrancheCountView);
//
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = this.row29.newUIBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Four_4);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer = this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.newUIContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer");
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView = new ReadOnlyView("loanTrancheDetailMaximumAllowedOutstandingBalanceView", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceView);
//
//        this.row30 = UIRow.newUIRow("row30", this.form);
//
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = this.row30.newUIBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Six_6);
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.newUIContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer");
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView = new ReadOnlyView("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView", new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView);
//
//        this.configurableAmortizationBlock = this.row30.newUIBlock("configurableAmortizationBlock", Size.Three_3);
//        this.configurableAmortizationVContainer = this.configurableAmortizationBlock.newUIContainer("configurableAmortizationVContainer");
//        this.configurableAmortizationView = new ReadOnlyView("configurableAmortizationView", new PropertyModel<>(this.itemPage, "configurableAmortizationValue"));
//        this.configurableAmortizationVContainer.add(this.configurableAmortizationView);
//
//        this.configurableInterestMethodBlock = this.row30.newUIBlock("configurableInterestMethodBlock", Size.Three_3);
//        this.configurableInterestMethodVContainer = this.configurableInterestMethodBlock.newUIContainer("configurableInterestMethodVContainer");
//        this.configurableInterestMethodView = new ReadOnlyView("configurableInterestMethodView", new PropertyModel<>(this.itemPage, "configurableInterestMethodValue"));
//        this.configurableInterestMethodVContainer.add(this.configurableInterestMethodView);
//
//        this.row31 = UIRow.newUIRow("row31", this.form);
//
//        this.configurableMoratoriumBlock = this.row31.newUIBlock("configurableMoratoriumBlock", Size.Three_3);
//        this.configurableMoratoriumVContainer = this.configurableMoratoriumBlock.newUIContainer("configurableMoratoriumVContainer");
//        this.configurableMoratoriumView = new ReadOnlyView("configurableMoratoriumView", new PropertyModel<>(this.itemPage, "configurableMoratoriumValue"));
//        this.configurableMoratoriumVContainer.add(this.configurableMoratoriumView);
//
//        this.configurableRepaidEveryBlock = this.row31.newUIBlock("configurableRepaidEveryBlock", Size.Three_3);
//        this.configurableRepaidEveryVContainer = this.configurableRepaidEveryBlock.newUIContainer("configurableRepaidEveryVContainer");
//        this.configurableRepaidEveryView = new ReadOnlyView("configurableRepaidEveryView", new PropertyModel<>(this.itemPage, "configurableRepaidEveryValue"));
//        this.configurableRepaidEveryVContainer.add(this.configurableRepaidEveryView);
//
//        this.configurableOverdueBeforeMovingBlock = this.row31.newUIBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
//        this.configurableOverdueBeforeMovingVContainer = this.configurableOverdueBeforeMovingBlock.newUIContainer("configurableOverdueBeforeMovingVContainer");
//        this.configurableOverdueBeforeMovingView = new ReadOnlyView("configurableOverdueBeforeMovingView", new PropertyModel<>(this.itemPage, "configurableOverdueBeforeMovingValue"));
//        this.configurableOverdueBeforeMovingVContainer.add(this.configurableOverdueBeforeMovingView);
//
//        this.row32 = UIRow.newUIRow("row32", this.form);
//
//        this.configurableArrearsToleranceBlock = this.row32.newUIBlock("configurableArrearsToleranceBlock", Size.Three_3);
//        this.configurableArrearsToleranceVContainer = this.configurableArrearsToleranceBlock.newUIContainer("configurableArrearsToleranceVContainer");
//        this.configurableArrearsToleranceView = new ReadOnlyView("configurableArrearsToleranceView", new PropertyModel<>(this.itemPage, "configurableArrearsToleranceValue"));
//        this.configurableArrearsToleranceVContainer.add(this.configurableArrearsToleranceView);
//
//        this.configurableInterestCalculationPeriodBlock = this.row32.newUIBlock("configurableInterestCalculationPeriodBlock", Size.Three_3);
//        this.configurableInterestCalculationPeriodVContainer = this.configurableInterestCalculationPeriodBlock.newUIContainer("configurableInterestCalculationPeriodVContainer");
//        this.configurableInterestCalculationPeriodView = new ReadOnlyView("configurableInterestCalculationPeriodView", new PropertyModel<>(this.itemPage, "configurableInterestCalculationPeriodValue"));
//        this.configurableInterestCalculationPeriodVContainer.add(this.configurableInterestCalculationPeriodView);
//
//        this.configurableRepaymentStrategyBlock = this.row32.newUIBlock("configurableRepaymentStrategyBlock", Size.Six_6);
//        this.configurableRepaymentStrategyVContainer = this.configurableRepaymentStrategyBlock.newUIContainer("configurableRepaymentStrategyVContainer");
//        this.configurableRepaymentStrategyView = new ReadOnlyView("configurableRepaymentStrategyView", new PropertyModel<>(this.itemPage, "configurableRepaymentStrategyValue"));
//        this.configurableRepaymentStrategyVContainer.add(this.configurableRepaymentStrategyView);
//
//        this.row33 = UIRow.newUIRow("row33", this.form);
//
//        this.chargeBlock = this.row33.newUIBlock("chargeBlock", Size.Twelve_12);
//        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
//        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, this.chargeValue.getObject().size() + 1);
//        this.chargeVContainer.add(this.chargeTable);
//        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
//        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
//
//        this.row34 = UIRow.newUIRow("row34", this.form);
//
//        this.overdueChargeBlock = this.row34.newUIBlock("overdueChargeBlock", Size.Twelve_12);
//        this.overdueChargeVContainer = this.overdueChargeBlock.newUIContainer("overdueChargeVContainer");
//        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, this.overdueChargeValue.getObject().size() + 1);
//        this.overdueChargeVContainer.add(this.overdueChargeTable);
//        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
//        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));
//
//        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this.itemPage, "accountingValue"));
//        this.form.add(this.accountingLabel);
//
//        this.cashMaster = new WebMarkupContainer("cashMaster");
//        this.cashMaster.setOutputMarkupId(true);
//        this.form.add(this.cashMaster);
//
//        this.row35 = UIRow.newUIRow("row35", this.cashMaster);
//
//        this.cashFundSourceBlock = this.row35.newUIBlock("cashFundSourceBlock", Size.Four_4);
//        this.cashFundSourceVContainer = this.cashFundSourceBlock.newUIContainer("cashFundSourceVContainer");
//        this.cashFundSourceView = new ReadOnlyView("cashFundSourceView", new PropertyModel<>(this.itemPage, "cashFundSourceValue"));
//        this.cashFundSourceVContainer.add(this.cashFundSourceView);
//
//        this.cashLoanPortfolioBlock = this.row35.newUIBlock("cashLoanPortfolioBlock", Size.Four_4);
//        this.cashLoanPortfolioVContainer = this.cashLoanPortfolioBlock.newUIContainer("cashLoanPortfolioVContainer");
//        this.cashLoanPortfolioView = new ReadOnlyView("cashLoanPortfolioView", new PropertyModel<>(this.itemPage, "cashLoanPortfolioValue"));
//        this.cashLoanPortfolioVContainer.add(this.cashLoanPortfolioView);
//
//        this.cashTransferInSuspenseBlock = this.row35.newUIBlock("cashTransferInSuspenseBlock", Size.Four_4);
//        this.cashTransferInSuspenseVContainer = this.cashTransferInSuspenseBlock.newUIContainer("cashTransferInSuspenseVContainer");
//        this.cashTransferInSuspenseView = new ReadOnlyView("cashTransferInSuspenseView", new PropertyModel<>(this.itemPage, "cashTransferInSuspenseValue"));
//        this.cashTransferInSuspenseVContainer.add(this.cashTransferInSuspenseView);
//
//        this.row36 = UIRow.newUIRow("row36", this.cashMaster);
//
//        this.cashIncomeFromInterestBlock = this.row36.newUIBlock("cashIncomeFromInterestBlock", Size.Four_4);
//        this.cashIncomeFromInterestVContainer = this.cashIncomeFromInterestBlock.newUIContainer("cashIncomeFromInterestVContainer");
//        this.cashIncomeFromInterestView = new ReadOnlyView("cashIncomeFromInterestView", new PropertyModel<>(this.itemPage, "cashIncomeFromInterestValue"));
//        this.cashIncomeFromInterestVContainer.add(this.cashIncomeFromInterestView);
//
//        this.cashIncomeFromFeeBlock = this.row36.newUIBlock("cashIncomeFromFeeBlock", Size.Four_4);
//        this.cashIncomeFromFeeVContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeVContainer");
//        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"));
//        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
//
//        this.cashIncomeFromPenaltyBlock = this.row36.newUIBlock("cashIncomeFromPenaltyBlock", Size.Four_4);
//        this.cashIncomeFromPenaltyVContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyVContainer");
//        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"));
//        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);
//
//        this.row37 = UIRow.newUIRow("row37", this.cashMaster);
//
//        this.cashIncomeFromRecoveryRepaymentBlock = this.row37.newUIBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Four_4);
//        this.cashIncomeFromRecoveryRepaymentVContainer = this.cashIncomeFromRecoveryRepaymentBlock.newUIContainer("cashIncomeFromRecoveryRepaymentVContainer");
//        this.cashIncomeFromRecoveryRepaymentView = new ReadOnlyView("cashIncomeFromRecoveryRepaymentView", new PropertyModel<>(this.itemPage, "cashIncomeFromRecoveryRepaymentValue"));
//        this.cashIncomeFromRecoveryRepaymentVContainer.add(this.cashIncomeFromRecoveryRepaymentView);
//
//        this.cashLossWrittenOffBlock = this.row37.newUIBlock("cashLossWrittenOffBlock", Size.Four_4);
//        this.cashLossWrittenOffVContainer = this.cashLossWrittenOffBlock.newUIContainer("cashLossWrittenOffVContainer");
//        this.cashLossWrittenOffView = new ReadOnlyView("cashLossWrittenOffView", new PropertyModel<>(this.itemPage, "cashLossWrittenOffValue"));
//        this.cashLossWrittenOffVContainer.add(this.cashLossWrittenOffView);
//
//        this.cashOverPaymentLiabilityBlock = this.row37.newUIBlock("cashOverPaymentLiabilityBlock", Size.Four_4);
//        this.cashOverPaymentLiabilityVContainer = this.cashOverPaymentLiabilityBlock.newUIContainer("cashOverPaymentLiabilityVContainer");
//        this.cashOverPaymentLiabilityView = new ReadOnlyView("cashOverPaymentLiabilityView", new PropertyModel<>(this.itemPage, "cashOverPaymentLiabilityValue"));
//        this.cashOverPaymentLiabilityVContainer.add(this.cashOverPaymentLiabilityView);
//
//        this.accrualMaster = new WebMarkupContainer("accrualMaster");
//        this.accrualMaster.setOutputMarkupId(true);
//        this.form.add(this.accrualMaster);
//
//        this.row38 = UIRow.newUIRow("row38", this.accrualMaster);
//
//        this.accrualFundSourceBlock = this.row38.newUIBlock("accrualFundSourceBlock", Size.Four_4);
//        this.accrualFundSourceVContainer = this.accrualFundSourceBlock.newUIContainer("accrualFundSourceVContainer");
//        this.accrualFundSourceView = new ReadOnlyView("accrualFundSourceView", new PropertyModel<>(this.itemPage, "accrualFundSourceValue"));
//        this.accrualFundSourceVContainer.add(this.accrualFundSourceView);
//
//        this.accrualLoanPortfolioBlock = this.row38.newUIBlock("accrualLoanPortfolioBlock", Size.Four_4);
//        this.accrualLoanPortfolioVContainer = this.accrualLoanPortfolioBlock.newUIContainer("accrualLoanPortfolioVContainer");
//        this.accrualLoanPortfolioView = new ReadOnlyView("accrualLoanPortfolioView", new PropertyModel<>(this.itemPage, "accrualLoanPortfolioValue"));
//        this.accrualLoanPortfolioVContainer.add(this.accrualLoanPortfolioView);
//
//        this.accrualInterestReceivableBlock = this.row38.newUIBlock("accrualInterestReceivableBlock", Size.Four_4);
//        this.accrualInterestReceivableVContainer = this.accrualInterestReceivableBlock.newUIContainer("accrualInterestReceivableVContainer");
//        this.accrualInterestReceivableView = new ReadOnlyView("accrualInterestReceivableView", new PropertyModel<>(this.itemPage, "accrualInterestReceivableValue"));
//        this.accrualInterestReceivableVContainer.add(this.accrualInterestReceivableView);
//
//        this.row39 = UIRow.newUIRow("row39", this.accrualMaster);
//
//        this.accrualFeeReceivableBlock = this.row39.newUIBlock("accrualFeeReceivableBlock", Size.Four_4);
//        this.accrualFeeReceivableVContainer = this.accrualFeeReceivableBlock.newUIContainer("accrualFeeReceivableVContainer");
//        this.accrualFeeReceivableView = new ReadOnlyView("accrualFeeReceivableView", new PropertyModel<>(this.itemPage, "accrualFeeReceivableValue"));
//        this.accrualFeeReceivableVContainer.add(this.accrualFeeReceivableView);
//
//        this.accrualPenaltyReceivableBlock = this.row39.newUIBlock("accrualPenaltyReceivableBlock", Size.Four_4);
//        this.accrualPenaltyReceivableVContainer = this.accrualPenaltyReceivableBlock.newUIContainer("accrualPenaltyReceivableVContainer");
//        this.accrualPenaltyReceivableView = new ReadOnlyView("accrualPenaltyReceivableView", new PropertyModel<>(this.itemPage, "accrualPenaltyReceivableValue"));
//        this.accrualPenaltyReceivableVContainer.add(this.accrualPenaltyReceivableView);
//
//        this.accrualTransferInSuspenseBlock = this.row39.newUIBlock("accrualTransferInSuspenseBlock", Size.Four_4);
//        this.accrualTransferInSuspenseVContainer = this.accrualTransferInSuspenseBlock.newUIContainer("accrualTransferInSuspenseVContainer");
//        this.accrualTransferInSuspenseView = new ReadOnlyView("accrualTransferInSuspenseView", new PropertyModel<>(this.itemPage, "accrualTransferInSuspenseValue"));
//        this.accrualTransferInSuspenseVContainer.add(this.accrualTransferInSuspenseView);
//
//        this.row40 = UIRow.newUIRow("row40", this.accrualMaster);
//
//        this.accrualIncomeFromInterestBlock = this.row40.newUIBlock("accrualIncomeFromInterestBlock", Size.Four_4);
//        this.accrualIncomeFromInterestVContainer = this.accrualIncomeFromInterestBlock.newUIContainer("accrualIncomeFromInterestVContainer");
//        this.accrualIncomeFromInterestView = new ReadOnlyView("accrualIncomeFromInterestView", new PropertyModel<>(this.itemPage, "accrualIncomeFromInterestValue"));
//        this.accrualIncomeFromInterestVContainer.add(this.accrualIncomeFromInterestView);
//
//        this.accrualIncomeFromFeeBlock = this.row40.newUIBlock("accrualIncomeFromFeeBlock", Size.Four_4);
//        this.accrualIncomeFromFeeVContainer = this.accrualIncomeFromFeeBlock.newUIContainer("accrualIncomeFromFeeVContainer");
//        this.accrualIncomeFromFeeView = new ReadOnlyView("accrualIncomeFromFeeView", new PropertyModel<>(this.itemPage, "accrualIncomeFromFeeValue"));
//        this.accrualIncomeFromFeeVContainer.add(this.accrualIncomeFromFeeView);
//
//        this.accrualIncomeFromPenaltyBlock = this.row40.newUIBlock("accrualIncomeFromPenaltyBlock", Size.Four_4);
//        this.accrualIncomeFromPenaltyVContainer = this.accrualIncomeFromPenaltyBlock.newUIContainer("accrualIncomeFromPenaltyVContainer");
//        this.accrualIncomeFromPenaltyView = new ReadOnlyView("accrualIncomeFromPenaltyView", new PropertyModel<>(this.itemPage, "accrualIncomeFromPenaltyValue"));
//        this.accrualIncomeFromPenaltyVContainer.add(this.accrualIncomeFromPenaltyView);
//
//        this.row41 = UIRow.newUIRow("row41", this.accrualMaster);
//
//        this.accrualIncomeFromRecoveryRepaymentBlock = this.row41.newUIBlock("accrualIncomeFromRecoveryRepaymentBlock", Size.Four_4);
//        this.accrualIncomeFromRecoveryRepaymentVContainer = this.accrualIncomeFromRecoveryRepaymentBlock.newUIContainer("accrualIncomeFromRecoveryRepaymentVContainer");
//        this.accrualIncomeFromRecoveryRepaymentView = new ReadOnlyView("accrualIncomeFromRecoveryRepaymentView", new PropertyModel<>(this.itemPage, "accrualIncomeFromRecoveryRepaymentValue"));
//        this.accrualIncomeFromRecoveryRepaymentVContainer.add(this.accrualIncomeFromRecoveryRepaymentView);
//
//        this.accrualLossWrittenOffBlock = this.row41.newUIBlock("accrualLossWrittenOffBlock", Size.Four_4);
//        this.accrualLossWrittenOffVContainer = this.accrualLossWrittenOffBlock.newUIContainer("accrualLossWrittenOffVContainer");
//        this.accrualLossWrittenOffView = new ReadOnlyView("accrualLossWrittenOffView", new PropertyModel<>(this.itemPage, "accrualLossWrittenOffValue"));
//        this.accrualLossWrittenOffVContainer.add(this.accrualLossWrittenOffView);
//
//        this.accrualOverPaymentLiabilityBlock = this.row41.newUIBlock("accrualOverPaymentLiabilityBlock", Size.Four_4);
//        this.accrualOverPaymentLiabilityVContainer = this.accrualOverPaymentLiabilityBlock.newUIContainer("accrualOverPaymentLiabilityVContainer");
//        this.accrualOverPaymentLiabilityView = new ReadOnlyView("accrualOverPaymentLiabilityView", new PropertyModel<>(this.itemPage, "accrualOverPaymentLiabilityValue"));
//        this.accrualOverPaymentLiabilityVContainer.add(this.accrualOverPaymentLiabilityView);
//
//        this.advancedAccountingRuleMaster = new WebMarkupContainer("advancedAccountingRuleMaster");
//        this.advancedAccountingRuleMaster.setOutputMarkupId(true);
//        this.form.add(this.advancedAccountingRuleMaster);
//
//        this.row42 = UIRow.newUIRow("row42", this.advancedAccountingRuleMaster);
//
//        this.row42Block1 = this.row42.newUIBlock("row42Block1", Size.Twelve_12);
//
//        this.advancedAccountingRuleFundSourceVContainer = this.row42Block1.newUIContainer("advancedAccountingRuleFundSourceVContainer");
//        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, this.advancedAccountingRuleFundSourceValue.getObject().size() + 1);
//        this.advancedAccountingRuleFundSourceVContainer.add(this.advancedAccountingRuleFundSourceTable);
//        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
//        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
//
//        this.advancedAccountingRuleFeeIncomeVContainer = this.row42Block1.newUIContainer("advancedAccountingRuleFeeIncomeVContainer");
//        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, this.advancedAccountingRuleFeeIncomeValue.getObject().size() + 1);
//        this.advancedAccountingRuleFeeIncomeVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
//        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
//        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
//
//        this.advancedAccountingRulePenaltyIncomeVContainer = this.row42Block1.newUIContainer("advancedAccountingRulePenaltyIncomeVContainer");
//        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, this.advancedAccountingRulePenaltyIncomeValue.getObject().size() + 1);
//        this.advancedAccountingRulePenaltyIncomeVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
//        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
//        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
//        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
//            this.cashMaster.setVisible(false);
//            this.accrualMaster.setVisible(false);
//            this.advancedAccountingRuleMaster.setVisible(false);
//        } else {
//            if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
//                this.accrualMaster.setVisible(false);
//            } else if (AccountingType.Periodic.getDescription().equals(accountingValue.getObject()) || AccountingType.Upfront.getDescription().equals(accountingValue.getObject())) {
//                this.cashMaster.setVisible(false);
//            }
//        }
//
//        PropertyModel<Boolean> termLinkedToFloatingInterestRatesValue = new PropertyModel<>(this.itemPage, "termLinkedToFloatingInterestRatesValue");
//        this.nominalRateMaster.setVisible(termLinkedToFloatingInterestRatesValue.getObject() == null || !termLinkedToFloatingInterestRatesValue.getObject());
//        this.floatingRateMaster.setVisible(termLinkedToFloatingInterestRatesValue.getObject() != null && termLinkedToFloatingInterestRatesValue.getObject());
//
//        PropertyModel<Boolean> termVaryBasedOnLoanCycleValue = new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue");
//        this.loanCycleMaster.setVisible(termVaryBasedOnLoanCycleValue.getObject() != null && termVaryBasedOnLoanCycleValue.getObject());
//
//        PropertyModel<Boolean> loanTrancheDetailEnableMultipleDisbursalValue = new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue");
//        boolean loanTrancheVisible = loanTrancheDetailEnableMultipleDisbursalValue.getObject() != null && loanTrancheDetailEnableMultipleDisbursalValue.getObject();
//
//        this.loanTrancheDetailMaximumTrancheCountVContainer.setVisible(loanTrancheVisible);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.setVisible(loanTrancheVisible);
//
//        PropertyModel<Boolean> configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue");
//        boolean configurableVisible = configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject() != null && configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject();
//
//        this.configurableAmortizationVContainer.setVisible(configurableVisible);
//        this.configurableInterestMethodVContainer.setVisible(configurableVisible);
//        this.configurableRepaymentStrategyVContainer.setVisible(configurableVisible);
//        this.configurableInterestCalculationPeriodVContainer.setVisible(configurableVisible);
//        this.configurableArrearsToleranceVContainer.setVisible(configurableVisible);
//        this.configurableRepaidEveryVContainer.setVisible(configurableVisible);
//        this.configurableMoratoriumVContainer.setVisible(configurableVisible);
//        this.configurableOverdueBeforeMovingVContainer.setVisible(configurableVisible);
//
//        PropertyModel<Boolean> guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue");
//        this.guaranteeMaster.setVisible(guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject() != null && guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject());
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);
//
//        PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");
//        Frequency compoundFrequency = null;
//        if (interestRecalculationCompoundingValue.getObject() != null) {
//            compoundFrequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
//        }
//        this.interestRecalculationCompoundingIntervalVContainer.setVisible(!(compoundFrequency == null || compoundFrequency == Frequency.Same));
//
//        PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
//        Frequency outstandingPrincipalFrequency = null;
//        if (interestRecalculationRecalculateValue.getObject() != null) {
//            outstandingPrincipalFrequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
//        }
//        this.interestRecalculationOutstandingPrincipalIntervalVContainer.setVisible(!(outstandingPrincipalFrequency == null || outstandingPrincipalFrequency == Frequency.Same));
//
//        PropertyModel<Option> interestRecalculationCompoundingOnValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue");
//        InterestRecalculationCompound compoundType = null;
//        if (interestRecalculationCompoundingOnValue.getObject() != null) {
//            compoundType = InterestRecalculationCompound.valueOf(interestRecalculationCompoundingOnValue.getObject().getId());
//        }
//
//        if (compoundType == null || compoundType == InterestRecalculationCompound.None) {
//            this.interestRecalculationCompoundingVContainer.setVisible(false);
//            this.interestRecalculationCompoundingIntervalVContainer.setVisible(false);
//        }
//
//        PropertyModel<Boolean> settingVariableInstallmentsAllowedValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue");
//        this.settingVariableMaster.setVisible(settingVariableInstallmentsAllowedValue.getObject() != null && settingVariableInstallmentsAllowedValue.getObject());
//
//        PropertyModel<Option> settingInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue");
//        InterestCalculationPeriod interestCalculationPeriod = null;
//        if (settingInterestCalculationPeriodValue.getObject() != null) {
//            interestCalculationPeriod = InterestCalculationPeriod.valueOf(settingInterestCalculationPeriodValue.getObject().getId());
//        }
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.setVisible(interestCalculationPeriod != null && interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment);
//
//        this.saveButton.setVisible(!this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
//    }
//
//    protected ItemPanel termPrincipleByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("when".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("cycle".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel termNumberOfRepaymentByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("when".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("cycle".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("when".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("cycle".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("charge".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("payment".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column) || "date".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("type".equals(column) || "collect".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("amount".equals(column)) {
//            Number value = (Number) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("charge".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_ACCOUNTING);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        if (this.itemPage instanceof LoanCreatePage) {
//            ((LoanCreatePage) this.itemPage).saveButtonSubmit(button);
//        }
//    }
//
//}
