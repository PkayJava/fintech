package com.angkorteam.fintech.widget.product.loan;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

public class PreviewPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;

    protected PropertyModel<Boolean> errorSetting;
    protected PropertyModel<Boolean> errorAccounting;
    protected PropertyModel<Boolean> errorCharge;
    protected PropertyModel<Boolean> errorCurrency;
    protected PropertyModel<Boolean> errorDetail;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button saveButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    // Details

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected ReadOnlyView detailDescriptionView;

    protected WebMarkupBlock detailStartDateBlock;
    protected WebMarkupContainer detailStartDateVContainer;
    protected ReadOnlyView detailStartDateView;

    protected WebMarkupBlock detailCloseDateBlock;
    protected WebMarkupContainer detailCloseDateVContainer;
    protected ReadOnlyView detailCloseDateView;

    protected WebMarkupBlock detailIncludeInCustomerLoanCounterBlock;
    protected WebMarkupContainer detailIncludeInCustomerLoanCounterVContainer;
    protected ReadOnlyView detailIncludeInCustomerLoanCounterView;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyInMultipleOfBlock;
    protected WebMarkupContainer currencyInMultipleOfVContainer;
    protected ReadOnlyView currencyInMultipleOfView;

    protected WebMarkupBlock currencyInstallmentInMultipleOfBlock;
    protected WebMarkupContainer currencyInstallmentInMultipleOfVContainer;
    protected ReadOnlyView currencyInstallmentInMultipleOfView;

    // Terms

    protected WebMarkupBlock termVaryBasedOnLoanCycleBlock;
    protected WebMarkupContainer termVaryBasedOnLoanCycleVContainer;
    protected ReadOnlyView termVaryBasedOnLoanCycleView;

    protected WebMarkupBlock termPrincipleMinimumBlock;
    protected WebMarkupContainer termPrincipleMinimumVContainer;
    protected ReadOnlyView termPrincipleMinimumView;

    protected WebMarkupBlock termPrincipleDefaultBlock;
    protected WebMarkupContainer termPrincipleDefaultVContainer;
    protected ReadOnlyView termPrincipleDefaultView;

    protected WebMarkupBlock termPrincipleMaximumBlock;
    protected WebMarkupContainer termPrincipleMaximumVContainer;
    protected ReadOnlyView termPrincipleMaximumView;

    protected WebMarkupBlock termNumberOfRepaymentMinimumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMinimumVContainer;
    protected ReadOnlyView termNumberOfRepaymentMinimumView;

    protected WebMarkupBlock termNumberOfRepaymentDefaultBlock;
    protected WebMarkupContainer termNumberOfRepaymentDefaultVContainer;
    protected ReadOnlyView termNumberOfRepaymentDefaultView;

    protected WebMarkupBlock termNumberOfRepaymentMaximumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMaximumVContainer;
    protected ReadOnlyView termNumberOfRepaymentMaximumView;

    protected WebMarkupBlock termRepaidEveryBlock;
    protected WebMarkupContainer termRepaidEveryVContainer;
    protected ReadOnlyView termRepaidEveryView;

    protected WebMarkupBlock termRepaidTypeBlock;
    protected WebMarkupContainer termRepaidTypeVContainer;
    protected ReadOnlyView termRepaidTypeView;

    protected WebMarkupContainer floatingRateMaster;

    protected WebMarkupBlock termFloatingInterestRateBlock;
    protected WebMarkupContainer termFloatingInterestRateVContainer;
    protected ReadOnlyView termFloatingInterestRateView;

    protected WebMarkupBlock termFloatingInterestDifferentialBlock;
    protected WebMarkupContainer termFloatingInterestDifferentialVContainer;
    protected ReadOnlyView termFloatingInterestDifferentialView;

    protected WebMarkupBlock termFloatingInterestAllowedBlock;
    protected WebMarkupContainer termFloatingInterestAllowedVContainer;
    protected ReadOnlyView termFloatingInterestAllowedView;

    protected WebMarkupBlock termFloatingInterestMinimumBlock;
    protected WebMarkupContainer termFloatingInterestMinimumVContainer;
    protected ReadOnlyView termFloatingInterestMinimumView;

    protected WebMarkupBlock termFloatingInterestDefaultBlock;
    protected WebMarkupContainer termFloatingInterestDefaultVContainer;
    protected ReadOnlyView termFloatingInterestDefaultView;

    protected WebMarkupBlock termFloatingInterestMaximumBlock;
    protected WebMarkupContainer termFloatingInterestMaximumVContainer;
    protected ReadOnlyView termFloatingInterestMaximumView;

    protected WebMarkupContainer nominalRateMaster;

    protected WebMarkupBlock termNominalInterestRateMinimumBlock;
    protected WebMarkupContainer termNominalInterestRateMinimumVContainer;
    protected ReadOnlyView termNominalInterestRateMinimumView;

    protected WebMarkupBlock termNominalInterestRateDefaultBlock;
    protected WebMarkupContainer termNominalInterestRateDefaultVContainer;
    protected ReadOnlyView termNominalInterestRateDefaultView;

    protected WebMarkupBlock termNominalInterestRateMaximumBlock;
    protected WebMarkupContainer termNominalInterestRateMaximumVContainer;
    protected ReadOnlyView termNominalInterestRateMaximumView;

    protected WebMarkupBlock termNominalInterestRateTypeBlock;
    protected WebMarkupContainer termNominalInterestRateTypeVContainer;
    protected ReadOnlyView termNominalInterestRateTypeView;

    protected WebMarkupContainer loanCycleMaster;

    protected WebMarkupBlock termPrincipleByLoanCycleBlock;
    protected WebMarkupContainer termPrincipleByLoanCycleVContainer;
    protected PropertyModel<List<Map<String, Object>>> termPrincipleByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termPrincipleByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termPrincipleByLoanCycleColumn;
    protected ListDataProvider termPrincipleByLoanCycleProvider;

    protected WebMarkupBlock termNumberOfRepaymentByLoanCycleBlock;
    protected WebMarkupContainer termNumberOfRepaymentByLoanCycleVContainer;
    protected PropertyModel<List<Map<String, Object>>> termNumberOfRepaymentByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;

    protected WebMarkupBlock termNominalInterestRateByLoanCycleBlock;
    protected WebMarkupContainer termNominalInterestRateByLoanCycleVContainer;
    protected PropertyModel<List<Map<String, Object>>> termNominalInterestRateByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
    protected List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn;

    protected WebMarkupBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    protected WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer;
    protected ReadOnlyView termMinimumDayBetweenDisbursalAndFirstRepaymentDateView;

    // Settings

    protected WebMarkupBlock settingAmortizationBlock;
    protected WebMarkupContainer settingAmortizationVContainer;
    protected ReadOnlyView settingAmortizationView;

    protected WebMarkupBlock settingInterestMethodBlock;
    protected WebMarkupContainer settingInterestMethodVContainer;
    protected ReadOnlyView settingInterestMethodView;

    protected WebMarkupBlock settingEqualAmortizationBlock;
    protected WebMarkupContainer settingEqualAmortizationVContainer;
    protected ReadOnlyView settingEqualAmortizationView;

    protected WebMarkupBlock settingInterestCalculationPeriodBlock;
    protected WebMarkupContainer settingInterestCalculationPeriodVContainer;
    protected ReadOnlyView settingInterestCalculationPeriodView;

    protected WebMarkupBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
    protected WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodVContainer;
    protected ReadOnlyView settingCalculateInterestForExactDaysInPartialPeriodView;

    protected WebMarkupBlock settingArrearsToleranceBlock;
    protected WebMarkupContainer settingArrearsToleranceVContainer;
    protected ReadOnlyView settingArrearsToleranceView;

    protected WebMarkupBlock settingRepaymentStrategyBlock;
    protected WebMarkupContainer settingRepaymentStrategyVContainer;
    protected ReadOnlyView settingRepaymentStrategyView;

    protected WebMarkupBlock settingMoratoriumPrincipleBlock;
    protected WebMarkupContainer settingMoratoriumPrincipleVContainer;
    protected ReadOnlyView settingMoratoriumPrincipleView;

    protected WebMarkupBlock settingInterestFreePeriodBlock;
    protected WebMarkupContainer settingInterestFreePeriodVContainer;
    protected ReadOnlyView settingInterestFreePeriodView;

    protected WebMarkupBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    protected WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer;
    protected ReadOnlyView settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView;

    protected WebMarkupBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    protected WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer;
    protected ReadOnlyView settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView;

    protected WebMarkupBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    protected WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer;
    protected ReadOnlyView settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView;

    protected WebMarkupBlock settingDayInYearBlock;
    protected WebMarkupContainer settingDayInYearVContainer;
    protected ReadOnlyView settingDayInYearView;

    protected WebMarkupBlock settingDayInMonthBlock;
    protected WebMarkupContainer settingDayInMonthVContainer;
    protected ReadOnlyView settingDayInMonthView;

    protected WebMarkupBlock settingPrincipleThresholdForLastInstalmentBlock;
    protected WebMarkupContainer settingPrincipleThresholdForLastInstalmentVContainer;
    protected ReadOnlyView settingPrincipleThresholdForLastInstalmentView;

    protected WebMarkupBlock settingAllowFixingOfTheInstallmentAmountBlock;
    protected WebMarkupContainer settingAllowFixingOfTheInstallmentAmountVContainer;
    protected ReadOnlyView settingAllowFixingOfTheInstallmentAmountView;

    protected WebMarkupBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
    protected WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansVContainer;
    protected ReadOnlyView settingAllowedToBeUsedForProvidingTopupLoansView;

    // settingVariableMaster
    protected WebMarkupContainer settingVariableMaster;

    protected WebMarkupBlock settingVariableInstallmentsAllowedBlock;
    protected WebMarkupContainer settingVariableInstallmentsAllowedVContainer;
    protected ReadOnlyView settingVariableInstallmentsAllowedView;

    protected WebMarkupBlock settingVariableInstallmentsMinimumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMinimumVContainer;
    protected ReadOnlyView settingVariableInstallmentsMinimumView;

    protected WebMarkupBlock settingVariableInstallmentsMaximumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMaximumVContainer;
    protected ReadOnlyView settingVariableInstallmentsMaximumView;

    // Interest Recalculation

    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
    protected WebMarkupContainer interestRecalculationRecalculateInterestVContainer;
    protected ReadOnlyView interestRecalculationRecalculateInterestView;

    protected WebMarkupContainer interestRecalculationMaster;

    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleVContainer;
    protected ReadOnlyView interestRecalculationPreClosureInterestCalculationRuleView;

    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeVContainer;
    protected ReadOnlyView interestRecalculationAdvancePaymentsAdjustmentTypeView;

    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
    protected WebMarkupContainer interestRecalculationCompoundingOnVContainer;
    protected ReadOnlyView interestRecalculationCompoundingOnView;

    protected WebMarkupBlock interestRecalculationCompoundingBlock;
    protected WebMarkupContainer interestRecalculationCompoundingVContainer;
    protected ReadOnlyView interestRecalculationCompoundingView;
    protected String interestRecalculationCompoundingValue;

    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIntervalVContainer;
    protected ReadOnlyView interestRecalculationCompoundingIntervalView;

    protected WebMarkupBlock interestRecalculationOutstandingPrincipalBlock;
    protected WebMarkupContainer interestRecalculationOutstandingPrincipalVContainer;
    protected ReadOnlyView interestRecalculationOutstandingPrincipalView;
    protected String interestRecalculationOutstandingPrincipalValue;

    protected WebMarkupBlock interestRecalculationOutstandingPrincipalIntervalBlock;
    protected WebMarkupContainer interestRecalculationOutstandingPrincipalIntervalVContainer;
    protected ReadOnlyView interestRecalculationOutstandingPrincipalIntervalView;

    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer;
    protected ReadOnlyView interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView;

    // Guarantee Requirements

    protected WebMarkupBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    protected WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer;
    protected ReadOnlyView guaranteeRequirementPlaceGuaranteeFundsOnHoldView;

    protected WebMarkupContainer guaranteeMaster;

    protected WebMarkupBlock guaranteeRequirementMandatoryGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMandatoryGuaranteeVContainer;
    protected ReadOnlyView guaranteeRequirementMandatoryGuaranteeView;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeVContainer;
    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeView;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer;
    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeFromGuarantorView;

    // Loan Tranche Details

    protected WebMarkupBlock loanTrancheDetailEnableMultipleDisbursalBlock;
    protected WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalVContainer;
    protected ReadOnlyView loanTrancheDetailEnableMultipleDisbursalView;

    protected WebMarkupBlock loanTrancheDetailMaximumTrancheCountBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumTrancheCountVContainer;
    protected ReadOnlyView loanTrancheDetailMaximumTrancheCountView;

    protected WebMarkupBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer;
    protected ReadOnlyView loanTrancheDetailMaximumAllowedOutstandingBalanceView;

    // Configurable Terms and Settings

    protected WebMarkupBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    protected WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer;
    protected ReadOnlyView configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView;

    protected WebMarkupBlock configurableAmortizationBlock;
    protected WebMarkupContainer configurableAmortizationVContainer;
    protected ReadOnlyView configurableAmortizationView;

    protected WebMarkupBlock configurableInterestMethodBlock;
    protected WebMarkupContainer configurableInterestMethodVContainer;
    protected ReadOnlyView configurableInterestMethodView;

    protected WebMarkupBlock configurableRepaymentStrategyBlock;
    protected WebMarkupContainer configurableRepaymentStrategyVContainer;
    protected ReadOnlyView configurableRepaymentStrategyView;

    protected WebMarkupBlock configurableInterestCalculationPeriodBlock;
    protected WebMarkupContainer configurableInterestCalculationPeriodVContainer;
    protected ReadOnlyView configurableInterestCalculationPeriodView;

    protected WebMarkupBlock configurableArrearsToleranceBlock;
    protected WebMarkupContainer configurableArrearsToleranceVContainer;
    protected ReadOnlyView configurableArrearsToleranceView;

    protected WebMarkupBlock configurableRepaidEveryBlock;
    protected WebMarkupContainer configurableRepaidEveryVContainer;
    protected Boolean configurableRepaidEveryValue;
    protected ReadOnlyView configurableRepaidEveryView;

    protected WebMarkupBlock configurableMoratoriumBlock;
    protected WebMarkupContainer configurableMoratoriumVContainer;
    protected ReadOnlyView configurableMoratoriumView;

    protected WebMarkupBlock configurableOverdueBeforeMovingBlock;
    protected WebMarkupContainer configurableOverdueBeforeMovingVContainer;
    protected ReadOnlyView configurableOverdueBeforeMovingView;

    // Charge

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected ListDataProvider chargeProvider;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;

    // Overdue Charge

    protected WebMarkupBlock overdueChargeBlock;
    protected WebMarkupContainer overdueChargeVContainer;
    protected DataTable<Map<String, Object>, String> overdueChargeTable;
    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
    protected ListDataProvider overdueChargeProvider;
    protected PropertyModel<List<Map<String, Object>>> overdueChargeValue;

    protected Label accountingLabel;

    protected WebMarkupContainer cashMaster;

    protected WebMarkupBlock cashFundSourceBlock;
    protected WebMarkupContainer cashFundSourceVContainer;
    protected ReadOnlyView cashFundSourceView;

    protected WebMarkupBlock cashLoanPortfolioBlock;
    protected WebMarkupContainer cashLoanPortfolioVContainer;
    protected ReadOnlyView cashLoanPortfolioView;

    protected WebMarkupBlock cashTransferInSuspenseBlock;
    protected WebMarkupContainer cashTransferInSuspenseVContainer;
    protected ReadOnlyView cashTransferInSuspenseView;

    protected WebMarkupBlock cashIncomeFromInterestBlock;
    protected WebMarkupContainer cashIncomeFromInterestVContainer;
    protected ReadOnlyView cashIncomeFromInterestView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    protected WebMarkupBlock cashIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer cashIncomeFromRecoveryRepaymentVContainer;
    protected ReadOnlyView cashIncomeFromRecoveryRepaymentView;

    protected WebMarkupBlock cashLossWrittenOffBlock;
    protected WebMarkupContainer cashLossWrittenOffVContainer;
    protected ReadOnlyView cashLossWrittenOffView;

    protected WebMarkupBlock cashOverPaymentLiabilityBlock;
    protected WebMarkupContainer cashOverPaymentLiabilityVContainer;
    protected ReadOnlyView cashOverPaymentLiabilityView;

    protected WebMarkupContainer accrualMaster;

    protected WebMarkupBlock accrualFundSourceBlock;
    protected WebMarkupContainer accrualFundSourceVContainer;
    protected ReadOnlyView accrualFundSourceView;

    protected WebMarkupBlock accrualLoanPortfolioBlock;
    protected WebMarkupContainer accrualLoanPortfolioVContainer;
    protected ReadOnlyView accrualLoanPortfolioView;

    protected WebMarkupBlock accrualInterestReceivableBlock;
    protected WebMarkupContainer accrualInterestReceivableVContainer;
    protected ReadOnlyView accrualInterestReceivableView;

    protected WebMarkupBlock accrualFeeReceivableBlock;
    protected WebMarkupContainer accrualFeeReceivableVContainer;
    protected ReadOnlyView accrualFeeReceivableView;

    protected WebMarkupBlock accrualPenaltyReceivableBlock;
    protected WebMarkupContainer accrualPenaltyReceivableVContainer;
    protected ReadOnlyView accrualPenaltyReceivableView;

    protected WebMarkupBlock accrualTransferInSuspenseBlock;
    protected WebMarkupContainer accrualTransferInSuspenseVContainer;
    protected ReadOnlyView accrualTransferInSuspenseView;

    protected WebMarkupBlock accrualIncomeFromInterestBlock;
    protected WebMarkupContainer accrualIncomeFromInterestVContainer;
    protected ReadOnlyView accrualIncomeFromInterestView;

    protected WebMarkupBlock accrualIncomeFromFeeBlock;
    protected WebMarkupContainer accrualIncomeFromFeeVContainer;
    protected ReadOnlyView accrualIncomeFromFeeView;

    protected WebMarkupBlock accrualIncomeFromPenaltyBlock;
    protected WebMarkupContainer accrualIncomeFromPenaltyVContainer;
    protected ReadOnlyView accrualIncomeFromPenaltyView;

    protected WebMarkupBlock accrualIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer accrualIncomeFromRecoveryRepaymentVContainer;
    protected ReadOnlyView accrualIncomeFromRecoveryRepaymentView;

    protected WebMarkupBlock accrualLossWrittenOffBlock;
    protected WebMarkupContainer accrualLossWrittenOffVContainer;
    protected ReadOnlyView accrualLossWrittenOffView;

    protected WebMarkupBlock accrualOverPaymentLiabilityBlock;
    protected WebMarkupContainer accrualOverPaymentLiabilityVContainer;
    protected ReadOnlyView accrualOverPaymentLiabilityView;

    protected WebMarkupContainer advancedAccountingRuleMaster;

    protected WebMarkupContainer advancedAccountingRuleFundSourceBlock;
    protected WebMarkupContainer advancedAccountingRuleFundSourceVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;

    protected WebMarkupContainer advancedAccountingRuleFeeIncomeBlock;
    protected WebMarkupContainer advancedAccountingRuleFeeIncomeVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;

    protected WebMarkupContainer advancedAccountingRulePenaltyIncomeBlock;
    protected WebMarkupContainer advancedAccountingRulePenaltyIncomeVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;

    public PreviewPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");

        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.termPrincipleByLoanCycleValue = new PropertyModel<>(this.itemPage, "termPrincipleByLoanCycleValue");
        this.termPrincipleByLoanCycleColumn = Lists.newArrayList();
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleProvider = new ListDataProvider(this.termPrincipleByLoanCycleValue.getObject());

        this.termNominalInterestRateByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateByLoanCycleValue");
        this.termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue.getObject());

        this.termNumberOfRepaymentByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentByLoanCycleValue");
        this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue.getObject());

        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));

        this.overdueChargeValue = new PropertyModel<>(this.itemPage, "overdueChargeValue");
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue.getObject());
        this.overdueChargeColumn = Lists.newArrayList();
        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));

        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());
        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));

        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());
        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));

        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());
        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));

        PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");
        PropertyModel<Option> interestRecalculationCompoundingTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue");
        PropertyModel<Option> interestRecalculationCompoundingDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingDayValue");
        PropertyModel<Option> interestRecalculationCompoundingOnDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnDayValue");
        Frequency compoundFrequency = null;
        if (interestRecalculationCompoundingValue.getObject() != null) {
            compoundFrequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
            if (compoundFrequency != null) {
                if (compoundFrequency == Frequency.Same || compoundFrequency == Frequency.Daily) {
                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
                } else if (compoundFrequency == Frequency.Weekly) {
                    if (interestRecalculationCompoundingDayValue.getObject() != null) {
                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + interestRecalculationCompoundingDayValue.getObject().getText();
                    } else {
                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
                    }
                } else if (compoundFrequency == Frequency.Monthly) {
                    if (interestRecalculationCompoundingTypeValue.getObject() != null) {
                        FrequencyType frequencyType = FrequencyType.valueOf(interestRecalculationCompoundingTypeValue.getObject().getId());
                        if (frequencyType != null) {
                            if (frequencyType == FrequencyType.OnDay) {
                                if (interestRecalculationCompoundingOnDayValue.getObject() != null) {
                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On Day " + interestRecalculationCompoundingOnDayValue.getObject().getText();
                                } else {
                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
                                }
                            } else {
                                if (interestRecalculationCompoundingDayValue.getObject() != null) {
                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + frequencyType.getDescription() + " " + interestRecalculationCompoundingDayValue.getObject().getText();
                                } else {
                                    this.interestRecalculationCompoundingValue = compoundFrequency.getDescription() + " On " + frequencyType.getDescription() + " Day";
                                }
                            }
                        } else {
                            this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
                        }
                    } else {
                        this.interestRecalculationCompoundingValue = compoundFrequency.getDescription();
                    }
                }
            }
        }

        PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
        PropertyModel<Option> interestRecalculationRecalculateTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue");
        PropertyModel<Option> interestRecalculationRecalculateDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateDayValue");
        PropertyModel<Option> interestRecalculationRecalculateOnDayValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateOnDayValue");
        Frequency outstandingPrincipalFrequency = null;
        if (interestRecalculationRecalculateValue.getObject() != null) {
            outstandingPrincipalFrequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
            if (outstandingPrincipalFrequency != null) {
                if (outstandingPrincipalFrequency == Frequency.Same || outstandingPrincipalFrequency == Frequency.Daily) {
                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
                } else if (outstandingPrincipalFrequency == Frequency.Weekly) {
                    if (interestRecalculationRecalculateDayValue.getObject() != null) {
                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + interestRecalculationRecalculateDayValue.getObject().getText();
                    } else {
                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
                    }
                } else if (outstandingPrincipalFrequency == Frequency.Monthly) {
                    if (interestRecalculationRecalculateTypeValue.getObject() != null) {
                        FrequencyType frequencyType = FrequencyType.valueOf(interestRecalculationRecalculateTypeValue.getObject().getId());
                        if (frequencyType != null) {
                            if (frequencyType == FrequencyType.OnDay) {
                                if (interestRecalculationRecalculateOnDayValue.getObject() != null) {
                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On Day " + interestRecalculationRecalculateOnDayValue.getObject().getText();
                                } else {
                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
                                }
                            } else {
                                if (interestRecalculationRecalculateDayValue.getObject() != null) {
                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + frequencyType.getDescription() + " " + interestRecalculationRecalculateDayValue.getObject().getText();
                                } else {
                                    this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription() + " On " + frequencyType.getDescription() + " Day";
                                }
                            }
                        } else {
                            this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
                        }
                    } else {
                        this.interestRecalculationOutstandingPrincipalValue = outstandingPrincipalFrequency.getDescription();
                    }
                }
            }
        }
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        // Details

        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Twelve_12);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);

        this.detailStartDateBlock = new WebMarkupBlock("detailStartDateBlock", Size.Six_6);
        this.form.add(this.detailStartDateBlock);
        this.detailStartDateVContainer = new WebMarkupContainer("detailStartDateVContainer");
        this.detailStartDateBlock.add(this.detailStartDateVContainer);
        this.detailStartDateView = new ReadOnlyView("detailStartDateView", new PropertyModel<>(this.itemPage, "detailStartDateValue"), "dd/MM/YYYY");
        this.detailStartDateVContainer.add(this.detailStartDateView);

        this.detailCloseDateBlock = new WebMarkupBlock("detailCloseDateBlock", Size.Six_6);
        this.form.add(this.detailCloseDateBlock);
        this.detailCloseDateVContainer = new WebMarkupContainer("detailCloseDateVContainer");
        this.detailCloseDateBlock.add(this.detailCloseDateVContainer);
        this.detailCloseDateView = new ReadOnlyView("detailCloseDateView", new PropertyModel<>(this.itemPage, "detailCloseDateValue"), "dd/MM/YYYY");
        this.detailCloseDateVContainer.add(this.detailCloseDateView);

        this.detailIncludeInCustomerLoanCounterBlock = new WebMarkupBlock("detailIncludeInCustomerLoanCounterBlock", Size.Six_6);
        this.form.add(this.detailIncludeInCustomerLoanCounterBlock);
        this.detailIncludeInCustomerLoanCounterVContainer = new WebMarkupContainer("detailIncludeInCustomerLoanCounterVContainer");
        this.detailIncludeInCustomerLoanCounterBlock.add(this.detailIncludeInCustomerLoanCounterVContainer);
        this.detailIncludeInCustomerLoanCounterView = new ReadOnlyView("detailIncludeInCustomerLoanCounterView", new PropertyModel<>(this.itemPage, "detailIncludeInCustomerLoanCounterValue"));
        this.detailIncludeInCustomerLoanCounterVContainer.add(this.detailIncludeInCustomerLoanCounterView);

        // Currency

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Three_3);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this.itemPage, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Three_3);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);

        this.currencyInMultipleOfBlock = new WebMarkupBlock("currencyInMultipleOfBlock", Size.Three_3);
        this.form.add(this.currencyInMultipleOfBlock);
        this.currencyInMultipleOfVContainer = new WebMarkupContainer("currencyInMultipleOfVContainer");
        this.currencyInMultipleOfBlock.add(this.currencyInMultipleOfVContainer);
        this.currencyInMultipleOfView = new ReadOnlyView("currencyInMultipleOfView", new PropertyModel<>(this.itemPage, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfVContainer.add(this.currencyInMultipleOfView);

        this.currencyInstallmentInMultipleOfBlock = new WebMarkupBlock("currencyInstallmentInMultipleOfBlock", Size.Three_3);
        this.form.add(this.currencyInstallmentInMultipleOfBlock);
        this.currencyInstallmentInMultipleOfVContainer = new WebMarkupContainer("currencyInstallmentInMultipleOfVContainer");
        this.currencyInstallmentInMultipleOfBlock.add(this.currencyInstallmentInMultipleOfVContainer);
        this.currencyInstallmentInMultipleOfView = new ReadOnlyView("currencyInstallmentInMultipleOfView", new PropertyModel<>(this.itemPage, "currencyInstallmentInMultipleOfValue"));
        this.currencyInstallmentInMultipleOfVContainer.add(this.currencyInstallmentInMultipleOfView);

        // Terms

        this.termVaryBasedOnLoanCycleBlock = new WebMarkupBlock("termVaryBasedOnLoanCycleBlock", Size.Three_3);
        this.form.add(this.termVaryBasedOnLoanCycleBlock);
        this.termVaryBasedOnLoanCycleVContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleVContainer");
        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleVContainer);
        this.termVaryBasedOnLoanCycleView = new ReadOnlyView("termVaryBasedOnLoanCycleView", new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleVContainer.add(this.termVaryBasedOnLoanCycleView);

        this.termPrincipleMinimumBlock = new WebMarkupBlock("termPrincipleMinimumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMinimumBlock);
        this.termPrincipleMinimumVContainer = new WebMarkupContainer("termPrincipleMinimumVContainer");
        this.termPrincipleMinimumBlock.add(this.termPrincipleMinimumVContainer);
        this.termPrincipleMinimumView = new ReadOnlyView("termPrincipleMinimumView", new PropertyModel<>(this.itemPage, "termPrincipleMinimumValue"));
        this.termPrincipleMinimumVContainer.add(this.termPrincipleMinimumView);

        this.termPrincipleDefaultBlock = new WebMarkupBlock("termPrincipleDefaultBlock", Size.Three_3);
        this.form.add(this.termPrincipleDefaultBlock);
        this.termPrincipleDefaultVContainer = new WebMarkupContainer("termPrincipleDefaultVContainer");
        this.termPrincipleDefaultBlock.add(this.termPrincipleDefaultVContainer);
        this.termPrincipleDefaultView = new ReadOnlyView("termPrincipleDefaultView", new PropertyModel<>(this.itemPage, "termPrincipleDefaultValue"));
        this.termPrincipleDefaultVContainer.add(this.termPrincipleDefaultView);

        this.termPrincipleMaximumBlock = new WebMarkupBlock("termPrincipleMaximumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMaximumBlock);
        this.termPrincipleMaximumVContainer = new WebMarkupContainer("termPrincipleMaximumVContainer");
        this.termPrincipleMaximumBlock.add(this.termPrincipleMaximumVContainer);
        this.termPrincipleMaximumView = new ReadOnlyView("termPrincipleMaximumView", new PropertyModel<>(this.itemPage, "termPrincipleMaximumValue"));
        this.termPrincipleMaximumVContainer.add(this.termPrincipleMaximumView);

        this.termNumberOfRepaymentMinimumBlock = new WebMarkupBlock("termNumberOfRepaymentMinimumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMinimumBlock);
        this.termNumberOfRepaymentMinimumVContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumVContainer");
        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumVContainer);
        this.termNumberOfRepaymentMinimumView = new ReadOnlyView("termNumberOfRepaymentMinimumView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMinimumValue"));
        this.termNumberOfRepaymentMinimumVContainer.add(this.termNumberOfRepaymentMinimumView);

        this.termNumberOfRepaymentDefaultBlock = new WebMarkupBlock("termNumberOfRepaymentDefaultBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentDefaultBlock);
        this.termNumberOfRepaymentDefaultVContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultVContainer");
        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultVContainer);
        this.termNumberOfRepaymentDefaultView = new ReadOnlyView("termNumberOfRepaymentDefaultView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentDefaultValue"));
        this.termNumberOfRepaymentDefaultVContainer.add(this.termNumberOfRepaymentDefaultView);

        this.termNumberOfRepaymentMaximumBlock = new WebMarkupBlock("termNumberOfRepaymentMaximumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMaximumBlock);
        this.termNumberOfRepaymentMaximumVContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumVContainer");
        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumVContainer);
        this.termNumberOfRepaymentMaximumView = new ReadOnlyView("termNumberOfRepaymentMaximumView", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMaximumValue"));
        this.termNumberOfRepaymentMaximumVContainer.add(this.termNumberOfRepaymentMaximumView);

        this.termRepaidEveryBlock = new WebMarkupBlock("termRepaidEveryBlock", Size.One_1);
        this.form.add(this.termRepaidEveryBlock);
        this.termRepaidEveryVContainer = new WebMarkupContainer("termRepaidEveryVContainer");
        this.termRepaidEveryBlock.add(this.termRepaidEveryVContainer);
        this.termRepaidEveryView = new ReadOnlyView("termRepaidEveryView", new PropertyModel<>(this.itemPage, "termRepaidEveryValue"));
        this.termRepaidEveryVContainer.add(this.termRepaidEveryView);

        this.termRepaidTypeBlock = new WebMarkupBlock("termRepaidTypeBlock", Size.Two_2);
        this.form.add(this.termRepaidTypeBlock);
        this.termRepaidTypeVContainer = new WebMarkupContainer("termRepaidTypeVContainer");
        this.termRepaidTypeBlock.add(this.termRepaidTypeVContainer);
        this.termRepaidTypeView = new ReadOnlyView("termRepaidTypeView", new PropertyModel<>(this.itemPage, "termRepaidTypeValue"));
        this.termRepaidTypeVContainer.add(this.termRepaidTypeView);

        this.floatingRateMaster = new WebMarkupContainer("floatingRateMaster");
        this.form.add(this.floatingRateMaster);

        this.termFloatingInterestRateBlock = new WebMarkupBlock("termFloatingInterestRateBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestRateBlock);
        this.termFloatingInterestRateVContainer = new WebMarkupContainer("termFloatingInterestRateVContainer");
        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateVContainer);
        this.termFloatingInterestRateView = new ReadOnlyView("termFloatingInterestRateView", new PropertyModel<>(this.itemPage, "termFloatingInterestRateValue"));
        this.termFloatingInterestRateVContainer.add(this.termFloatingInterestRateView);

        this.termFloatingInterestDifferentialBlock = new WebMarkupBlock("termFloatingInterestDifferentialBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestDifferentialBlock);
        this.termFloatingInterestDifferentialVContainer = new WebMarkupContainer("termFloatingInterestDifferentialVContainer");
        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialVContainer);
        this.termFloatingInterestDifferentialView = new ReadOnlyView("termFloatingInterestDifferentialView", new PropertyModel<>(this.itemPage, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialVContainer.add(this.termFloatingInterestDifferentialView);

        this.termFloatingInterestAllowedBlock = new WebMarkupBlock("termFloatingInterestAllowedBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestAllowedBlock);
        this.termFloatingInterestAllowedVContainer = new WebMarkupContainer("termFloatingInterestAllowedVContainer");
        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedVContainer);
        this.termFloatingInterestAllowedView = new ReadOnlyView("termFloatingInterestAllowedView", new PropertyModel<>(this.itemPage, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedVContainer.add(this.termFloatingInterestAllowedView);

        this.termFloatingInterestMinimumBlock = new WebMarkupBlock("termFloatingInterestMinimumBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestMinimumBlock);
        this.termFloatingInterestMinimumVContainer = new WebMarkupContainer("termFloatingInterestMinimumVContainer");
        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumVContainer);
        this.termFloatingInterestMinimumView = new ReadOnlyView("termFloatingInterestMinimumView", new PropertyModel<>(this.itemPage, "termFloatingInterestMinimumValue"));
        this.termFloatingInterestMinimumVContainer.add(this.termFloatingInterestMinimumView);

        this.termFloatingInterestDefaultBlock = new WebMarkupBlock("termFloatingInterestDefaultBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestDefaultBlock);
        this.termFloatingInterestDefaultVContainer = new WebMarkupContainer("termFloatingInterestDefaultVContainer");
        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultVContainer);
        this.termFloatingInterestDefaultView = new ReadOnlyView("termFloatingInterestDefaultView", new PropertyModel<>(this.itemPage, "termFloatingInterestDefaultValue"));
        this.termFloatingInterestDefaultVContainer.add(this.termFloatingInterestDefaultView);

        this.termFloatingInterestMaximumBlock = new WebMarkupBlock("termFloatingInterestMaximumBlock", Size.Three_3);
        this.floatingRateMaster.add(this.termFloatingInterestMaximumBlock);
        this.termFloatingInterestMaximumVContainer = new WebMarkupContainer("termFloatingInterestMaximumVContainer");
        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumVContainer);
        this.termFloatingInterestMaximumView = new ReadOnlyView("termFloatingInterestMaximumView", new PropertyModel<>(this.itemPage, "termFloatingInterestMaximumValue"));
        this.termFloatingInterestMaximumVContainer.add(this.termFloatingInterestMaximumView);

        this.nominalRateMaster = new WebMarkupContainer("nominalRateMaster");
        this.form.add(this.nominalRateMaster);

        this.termNominalInterestRateMinimumBlock = new WebMarkupBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
        this.nominalRateMaster.add(this.termNominalInterestRateMinimumBlock);
        this.termNominalInterestRateMinimumVContainer = new WebMarkupContainer("termNominalInterestRateMinimumVContainer");
        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumVContainer);
        this.termNominalInterestRateMinimumView = new ReadOnlyView("termNominalInterestRateMinimumView", new PropertyModel<>(this.itemPage, "termNominalInterestRateMinimumValue"));
        this.termNominalInterestRateMinimumVContainer.add(this.termNominalInterestRateMinimumView);

        this.termNominalInterestRateDefaultBlock = new WebMarkupBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
        this.nominalRateMaster.add(this.termNominalInterestRateDefaultBlock);
        this.termNominalInterestRateDefaultVContainer = new WebMarkupContainer("termNominalInterestRateDefaultVContainer");
        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultVContainer);
        this.termNominalInterestRateDefaultView = new ReadOnlyView("termNominalInterestRateDefaultView", new PropertyModel<>(this.itemPage, "termNominalInterestRateDefaultValue"));
        this.termNominalInterestRateDefaultVContainer.add(this.termNominalInterestRateDefaultView);

        this.termNominalInterestRateMaximumBlock = new WebMarkupBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
        this.nominalRateMaster.add(this.termNominalInterestRateMaximumBlock);
        this.termNominalInterestRateMaximumVContainer = new WebMarkupContainer("termNominalInterestRateMaximumVContainer");
        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumVContainer);
        this.termNominalInterestRateMaximumView = new ReadOnlyView("termNominalInterestRateMaximumView", new PropertyModel<>(this.itemPage, "termNominalInterestRateMaximumValue"));
        this.termNominalInterestRateMaximumVContainer.add(this.termNominalInterestRateMaximumView);

        this.termNominalInterestRateTypeBlock = new WebMarkupBlock("termNominalInterestRateTypeBlock", Size.Three_3);
        this.nominalRateMaster.add(this.termNominalInterestRateTypeBlock);
        this.termNominalInterestRateTypeVContainer = new WebMarkupContainer("termNominalInterestRateTypeVContainer");
        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeVContainer);
        this.termNominalInterestRateTypeView = new ReadOnlyView("termNominalInterestRateTypeView", new PropertyModel<>(this.itemPage, "termNominalInterestRateTypeValue"));
        this.termNominalInterestRateTypeVContainer.add(this.termNominalInterestRateTypeView);

        this.loanCycleMaster = new WebMarkupContainer("loanCycleMaster");
        this.form.add(this.loanCycleMaster);

        this.termPrincipleByLoanCycleBlock = new WebMarkupBlock("termPrincipleByLoanCycleBlock", Size.Twelve_12);
        this.loanCycleMaster.add(this.termPrincipleByLoanCycleBlock);
        this.termPrincipleByLoanCycleVContainer = new WebMarkupContainer("termPrincipleByLoanCycleVContainer");
        this.termPrincipleByLoanCycleBlock.add(this.termPrincipleByLoanCycleVContainer);
        this.termPrincipleByLoanCycleTable = new DataTable<>("termPrincipleByLoanCycleTable", this.termPrincipleByLoanCycleColumn, this.termPrincipleByLoanCycleProvider, this.termPrincipleByLoanCycleValue.getObject().size() + 1);
        this.termPrincipleByLoanCycleVContainer.add(this.termPrincipleByLoanCycleTable);
        this.termPrincipleByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipleByLoanCycleTable, this.termPrincipleByLoanCycleProvider));
        this.termPrincipleByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipleByLoanCycleTable));

        this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
        this.loanCycleMaster.add(this.termNumberOfRepaymentByLoanCycleBlock);
        this.termNumberOfRepaymentByLoanCycleVContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleVContainer");
        this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleVContainer);
        this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, this.termNumberOfRepaymentByLoanCycleValue.getObject().size() + 1);
        this.termNumberOfRepaymentByLoanCycleVContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
        this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
        this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));

        this.termNominalInterestRateByLoanCycleBlock = new WebMarkupBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
        this.loanCycleMaster.add(this.termNominalInterestRateByLoanCycleBlock);
        this.termNominalInterestRateByLoanCycleVContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleVContainer");
        this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleVContainer);
        this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", this.termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, this.termNominalInterestRateByLoanCycleValue.getObject().size() + 1);
        this.termNominalInterestRateByLoanCycleVContainer.add(this.termNominalInterestRateByLoanCycleTable);
        this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
        this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
        this.form.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView = new ReadOnlyView("termMinimumDayBetweenDisbursalAndFirstRepaymentDateView", new PropertyModel<>(this.itemPage, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView);

        this.settingAmortizationBlock = new WebMarkupBlock("settingAmortizationBlock", Size.Three_3);
        this.form.add(this.settingAmortizationBlock);
        this.settingAmortizationVContainer = new WebMarkupContainer("settingAmortizationVContainer");
        this.settingAmortizationBlock.add(this.settingAmortizationVContainer);
        this.settingAmortizationView = new ReadOnlyView("settingAmortizationView", new PropertyModel<>(this.itemPage, "settingAmortizationValue"));
        this.settingAmortizationVContainer.add(this.settingAmortizationView);

        this.settingInterestMethodBlock = new WebMarkupBlock("settingInterestMethodBlock", Size.Three_3);
        this.form.add(this.settingInterestMethodBlock);
        this.settingInterestMethodVContainer = new WebMarkupContainer("settingInterestMethodVContainer");
        this.settingInterestMethodBlock.add(this.settingInterestMethodVContainer);
        this.settingInterestMethodView = new ReadOnlyView("settingInterestMethodView", new PropertyModel<>(this.itemPage, "settingInterestMethodValue"));
        this.settingInterestMethodVContainer.add(this.settingInterestMethodView);

        this.settingEqualAmortizationBlock = new WebMarkupBlock("settingEqualAmortizationBlock", Size.Three_3);
        this.form.add(this.settingEqualAmortizationBlock);
        this.settingEqualAmortizationVContainer = new WebMarkupContainer("settingEqualAmortizationVContainer");
        this.settingEqualAmortizationBlock.add(this.settingEqualAmortizationVContainer);
        this.settingEqualAmortizationView = new ReadOnlyView("settingEqualAmortizationView", new PropertyModel<>(this.itemPage, "settingEqualAmortizationValue"));
        this.settingEqualAmortizationVContainer.add(this.settingEqualAmortizationView);

        this.settingInterestCalculationPeriodBlock = new WebMarkupBlock("settingInterestCalculationPeriodBlock", Size.Three_3);
        this.form.add(this.settingInterestCalculationPeriodBlock);
        this.settingInterestCalculationPeriodVContainer = new WebMarkupContainer("settingInterestCalculationPeriodVContainer");
        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodVContainer);
        this.settingInterestCalculationPeriodView = new ReadOnlyView("settingInterestCalculationPeriodView", new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue"));
        this.settingInterestCalculationPeriodVContainer.add(this.settingInterestCalculationPeriodView);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
        this.form.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodVContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodVContainer);
        this.settingCalculateInterestForExactDaysInPartialPeriodView = new ReadOnlyView("settingCalculateInterestForExactDaysInPartialPeriodView", new PropertyModel<>(this.itemPage, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodView);

        this.settingArrearsToleranceBlock = new WebMarkupBlock("settingArrearsToleranceBlock", Size.Three_3);
        this.form.add(this.settingArrearsToleranceBlock);
        this.settingArrearsToleranceVContainer = new WebMarkupContainer("settingArrearsToleranceVContainer");
        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceVContainer);
        this.settingArrearsToleranceView = new ReadOnlyView("settingArrearsToleranceView", new PropertyModel<>(this.itemPage, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceVContainer.add(this.settingArrearsToleranceView);

        this.settingRepaymentStrategyBlock = new WebMarkupBlock("settingRepaymentStrategyBlock", Size.Three_3);
        this.form.add(this.settingRepaymentStrategyBlock);
        this.settingRepaymentStrategyVContainer = new WebMarkupContainer("settingRepaymentStrategyVContainer");
        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyVContainer);
        this.settingRepaymentStrategyView = new ReadOnlyView("settingRepaymentStrategyView", new PropertyModel<>(this.itemPage, "settingRepaymentStrategyValue"));
        this.settingRepaymentStrategyVContainer.add(this.settingRepaymentStrategyView);

        this.settingMoratoriumPrincipleBlock = new WebMarkupBlock("settingMoratoriumPrincipleBlock", Size.Three_3);
        this.form.add(this.settingMoratoriumPrincipleBlock);
        this.settingMoratoriumPrincipleVContainer = new WebMarkupContainer("settingMoratoriumPrincipleVContainer");
        this.settingMoratoriumPrincipleBlock.add(this.settingMoratoriumPrincipleVContainer);
        this.settingMoratoriumPrincipleView = new ReadOnlyView("settingMoratoriumPrincipleView", new PropertyModel<>(this.itemPage, "settingMoratoriumPrincipleValue"));
        this.settingMoratoriumPrincipleVContainer.add(this.settingMoratoriumPrincipleView);

        this.settingInterestFreePeriodBlock = new WebMarkupBlock("settingInterestFreePeriodBlock", Size.Three_3);
        this.form.add(this.settingInterestFreePeriodBlock);
        this.settingInterestFreePeriodVContainer = new WebMarkupContainer("settingInterestFreePeriodVContainer");
        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodVContainer);
        this.settingInterestFreePeriodView = new ReadOnlyView("settingInterestFreePeriodView", new PropertyModel<>(this.itemPage, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodVContainer.add(this.settingInterestFreePeriodView);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Three_3);
        this.form.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView = new ReadOnlyView("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Three_3);
        this.form.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView = new ReadOnlyView("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView", new PropertyModel<>(this.itemPage, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
        this.form.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView = new ReadOnlyView("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView", new PropertyModel<>(this.itemPage, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView);

        this.settingDayInYearBlock = new WebMarkupBlock("settingDayInYearBlock", Size.Three_3);
        this.form.add(this.settingDayInYearBlock);
        this.settingDayInYearVContainer = new WebMarkupContainer("settingDayInYearVContainer");
        this.settingDayInYearBlock.add(this.settingDayInYearVContainer);
        this.settingDayInYearView = new ReadOnlyView("settingDayInYearView", new PropertyModel<>(this.itemPage, "settingDayInYearValue"));
        this.settingDayInYearVContainer.add(this.settingDayInYearView);

        this.settingDayInMonthBlock = new WebMarkupBlock("settingDayInMonthBlock", Size.Three_3);
        this.form.add(this.settingDayInMonthBlock);
        this.settingDayInMonthVContainer = new WebMarkupContainer("settingDayInMonthVContainer");
        this.settingDayInMonthBlock.add(this.settingDayInMonthVContainer);
        this.settingDayInMonthView = new ReadOnlyView("settingDayInMonthView", new PropertyModel<>(this.itemPage, "settingDayInMonthValue"));
        this.settingDayInMonthVContainer.add(this.settingDayInMonthView);

        this.settingPrincipleThresholdForLastInstalmentBlock = new WebMarkupBlock("settingPrincipleThresholdForLastInstalmentBlock", Size.Four_4);
        this.form.add(this.settingPrincipleThresholdForLastInstalmentBlock);
        this.settingPrincipleThresholdForLastInstalmentVContainer = new WebMarkupContainer("settingPrincipleThresholdForLastInstalmentVContainer");
        this.settingPrincipleThresholdForLastInstalmentBlock.add(this.settingPrincipleThresholdForLastInstalmentVContainer);
        this.settingPrincipleThresholdForLastInstalmentView = new ReadOnlyView("settingPrincipleThresholdForLastInstalmentView", new PropertyModel<>(this.itemPage, "settingPrincipleThresholdForLastInstalmentValue"));
        this.settingPrincipleThresholdForLastInstalmentVContainer.add(this.settingPrincipleThresholdForLastInstalmentView);

        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Four_4);
        this.form.add(this.settingAllowFixingOfTheInstallmentAmountBlock);
        this.settingAllowFixingOfTheInstallmentAmountVContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountVContainer");
        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountVContainer);
        this.settingAllowFixingOfTheInstallmentAmountView = new ReadOnlyView("settingAllowFixingOfTheInstallmentAmountView", new PropertyModel<>(this.itemPage, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountVContainer.add(this.settingAllowFixingOfTheInstallmentAmountView);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Four_4);
        this.form.add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansVContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansVContainer);
        this.settingAllowedToBeUsedForProvidingTopupLoansView = new ReadOnlyView("settingAllowedToBeUsedForProvidingTopupLoansView", new PropertyModel<>(this.itemPage, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansView);

        this.settingVariableMaster = new WebMarkupContainer("settingVariableMaster");
        this.form.add(this.settingVariableMaster);

        this.settingVariableInstallmentsAllowedBlock = new WebMarkupBlock("settingVariableInstallmentsAllowedBlock", Size.Four_4);
        this.settingVariableMaster.add(this.settingVariableInstallmentsAllowedBlock);
        this.settingVariableInstallmentsAllowedVContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedVContainer");
        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedVContainer);
        this.settingVariableInstallmentsAllowedView = new ReadOnlyView("settingVariableInstallmentsAllowedView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue"));
        this.settingVariableInstallmentsAllowedVContainer.add(this.settingVariableInstallmentsAllowedView);

        this.settingVariableInstallmentsMinimumBlock = new WebMarkupBlock("settingVariableInstallmentsMinimumBlock", Size.Four_4);
        this.settingVariableMaster.add(this.settingVariableInstallmentsMinimumBlock);
        this.settingVariableInstallmentsMinimumVContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumVContainer");
        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumVContainer);
        this.settingVariableInstallmentsMinimumView = new ReadOnlyView("settingVariableInstallmentsMinimumView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMinimumValue"));
        this.settingVariableInstallmentsMinimumVContainer.add(this.settingVariableInstallmentsMinimumView);

        this.settingVariableInstallmentsMaximumBlock = new WebMarkupBlock("settingVariableInstallmentsMaximumBlock", Size.Four_4);
        this.settingVariableMaster.add(this.settingVariableInstallmentsMaximumBlock);
        this.settingVariableInstallmentsMaximumVContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumVContainer");
        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumVContainer);
        this.settingVariableInstallmentsMaximumView = new ReadOnlyView("settingVariableInstallmentsMaximumView", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMaximumValue"));
        this.settingVariableInstallmentsMaximumVContainer.add(this.settingVariableInstallmentsMaximumView);

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestVContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestVContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestVContainer);
        this.interestRecalculationRecalculateInterestView = new ReadOnlyView("interestRecalculationRecalculateInterestView", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestView);

        this.interestRecalculationMaster = new WebMarkupContainer("interestRecalculationMaster");
        this.form.add(this.interestRecalculationMaster);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Four_4);
        this.interestRecalculationMaster.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleVContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleVContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleView = new ReadOnlyView("interestRecalculationPreClosureInterestCalculationRuleView", new PropertyModel<>(this.itemPage, "interestRecalculationPreClosureInterestCalculationRuleValue"));
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleView);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Four_4);
        this.interestRecalculationMaster.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeVContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeView = new ReadOnlyView("interestRecalculationAdvancePaymentsAdjustmentTypeView", new PropertyModel<>(this.itemPage, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeView);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        this.interestRecalculationMaster.add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnVContainer = new WebMarkupContainer("interestRecalculationCompoundingOnVContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnVContainer);
        this.interestRecalculationCompoundingOnView = new ReadOnlyView("interestRecalculationCompoundingOnView", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue"));
        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnView);

        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Nine_9);
        this.interestRecalculationMaster.add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingVContainer = new WebMarkupContainer("interestRecalculationCompoundingVContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingVContainer);
        this.interestRecalculationCompoundingView = new ReadOnlyView("interestRecalculationCompoundingView", new PropertyModel<>(this, "interestRecalculationCompoundingValue"));
        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingView);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Three_3);
        this.interestRecalculationMaster.add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalVContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalVContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalVContainer);
        this.interestRecalculationCompoundingIntervalView = new ReadOnlyView("interestRecalculationCompoundingIntervalView", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalView);

        this.interestRecalculationOutstandingPrincipalBlock = new WebMarkupBlock("interestRecalculationOutstandingPrincipalBlock", Size.Nine_9);
        this.interestRecalculationMaster.add(this.interestRecalculationOutstandingPrincipalBlock);
        this.interestRecalculationOutstandingPrincipalVContainer = new WebMarkupContainer("interestRecalculationOutstandingPrincipalVContainer");
        this.interestRecalculationOutstandingPrincipalBlock.add(this.interestRecalculationOutstandingPrincipalVContainer);
        this.interestRecalculationOutstandingPrincipalView = new ReadOnlyView("interestRecalculationOutstandingPrincipalView", new PropertyModel<>(this, "interestRecalculationOutstandingPrincipalValue"));
        this.interestRecalculationOutstandingPrincipalVContainer.add(this.interestRecalculationOutstandingPrincipalView);

        this.interestRecalculationOutstandingPrincipalIntervalBlock = new WebMarkupBlock("interestRecalculationOutstandingPrincipalIntervalBlock", Size.Three_3);
        this.interestRecalculationMaster.add(this.interestRecalculationOutstandingPrincipalIntervalBlock);
        this.interestRecalculationOutstandingPrincipalIntervalVContainer = new WebMarkupContainer("interestRecalculationOutstandingPrincipalIntervalVContainer");
        this.interestRecalculationOutstandingPrincipalIntervalBlock.add(this.interestRecalculationOutstandingPrincipalIntervalVContainer);
        this.interestRecalculationOutstandingPrincipalIntervalView = new ReadOnlyView("interestRecalculationOutstandingPrincipalIntervalView", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationOutstandingPrincipalIntervalVContainer.add(this.interestRecalculationOutstandingPrincipalIntervalView);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Nine_9);
        this.interestRecalculationMaster.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView = new ReadOnlyView("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView", new PropertyModel<>(this.itemPage, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Four_4);
        this.form.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView = new ReadOnlyView("guaranteeRequirementPlaceGuaranteeFundsOnHoldView", new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);

        this.guaranteeMaster = new WebMarkupContainer("guaranteeMaster");
        this.form.add(this.guaranteeMaster);

        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Four_4);
        this.guaranteeMaster.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
        this.guaranteeRequirementMandatoryGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeVContainer");
        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeVContainer);
        this.guaranteeRequirementMandatoryGuaranteeView = new ReadOnlyView("guaranteeRequirementMandatoryGuaranteeView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMandatoryGuaranteeValue"));
        this.guaranteeRequirementMandatoryGuaranteeVContainer.add(this.guaranteeRequirementMandatoryGuaranteeView);

        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Four_4);
        this.guaranteeMaster.add(this.guaranteeRequirementMinimumGuaranteeBlock);
        this.guaranteeRequirementMinimumGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeVContainer");
        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeVContainer);
        this.guaranteeRequirementMinimumGuaranteeView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeValue"));
        this.guaranteeRequirementMinimumGuaranteeVContainer.add(this.guaranteeRequirementMinimumGuaranteeView);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Four_4);
        this.guaranteeMaster.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView = new ReadOnlyView("guaranteeRequirementMinimumGuaranteeFromGuarantorView", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorView);

        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
        this.loanTrancheDetailEnableMultipleDisbursalVContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalVContainer");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalVContainer);
        this.loanTrancheDetailEnableMultipleDisbursalView = new ReadOnlyView("loanTrancheDetailEnableMultipleDisbursalView", new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue"));
        this.loanTrancheDetailEnableMultipleDisbursalVContainer.add(this.loanTrancheDetailEnableMultipleDisbursalView);

        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailMaximumTrancheCountBlock);
        this.loanTrancheDetailMaximumTrancheCountVContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountVContainer");
        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountVContainer);
        this.loanTrancheDetailMaximumTrancheCountView = new ReadOnlyView("loanTrancheDetailMaximumTrancheCountView", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountVContainer.add(this.loanTrancheDetailMaximumTrancheCountView);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView = new ReadOnlyView("loanTrancheDetailMaximumAllowedOutstandingBalanceView", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceView);

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Six_6);
        this.form.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView = new ReadOnlyView("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView", new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView);

        this.configurableAmortizationBlock = new WebMarkupBlock("configurableAmortizationBlock", Size.Three_3);
        this.form.add(this.configurableAmortizationBlock);
        this.configurableAmortizationVContainer = new WebMarkupContainer("configurableAmortizationVContainer");
        this.configurableAmortizationBlock.add(this.configurableAmortizationVContainer);
        this.configurableAmortizationView = new ReadOnlyView("configurableAmortizationView", new PropertyModel<>(this.itemPage, "configurableAmortizationValue"));
        this.configurableAmortizationVContainer.add(this.configurableAmortizationView);

        this.configurableInterestMethodBlock = new WebMarkupBlock("configurableInterestMethodBlock", Size.Three_3);
        this.form.add(this.configurableInterestMethodBlock);
        this.configurableInterestMethodVContainer = new WebMarkupContainer("configurableInterestMethodVContainer");
        this.configurableInterestMethodBlock.add(this.configurableInterestMethodVContainer);
        this.configurableInterestMethodView = new ReadOnlyView("configurableInterestMethodView", new PropertyModel<>(this.itemPage, "configurableInterestMethodValue"));
        this.configurableInterestMethodVContainer.add(this.configurableInterestMethodView);

        this.configurableRepaymentStrategyBlock = new WebMarkupBlock("configurableRepaymentStrategyBlock", Size.Six_6);
        this.form.add(this.configurableRepaymentStrategyBlock);
        this.configurableRepaymentStrategyVContainer = new WebMarkupContainer("configurableRepaymentStrategyVContainer");
        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyVContainer);
        this.configurableRepaymentStrategyView = new ReadOnlyView("configurableRepaymentStrategyView", new PropertyModel<>(this.itemPage, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyVContainer.add(this.configurableRepaymentStrategyView);

        this.configurableInterestCalculationPeriodBlock = new WebMarkupBlock("configurableInterestCalculationPeriodBlock", Size.Three_3);
        this.form.add(this.configurableInterestCalculationPeriodBlock);
        this.configurableInterestCalculationPeriodVContainer = new WebMarkupContainer("configurableInterestCalculationPeriodVContainer");
        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodVContainer);
        this.configurableInterestCalculationPeriodView = new ReadOnlyView("configurableInterestCalculationPeriodView", new PropertyModel<>(this.itemPage, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodVContainer.add(this.configurableInterestCalculationPeriodView);

        this.configurableArrearsToleranceBlock = new WebMarkupBlock("configurableArrearsToleranceBlock", Size.Three_3);
        this.form.add(this.configurableArrearsToleranceBlock);
        this.configurableArrearsToleranceVContainer = new WebMarkupContainer("configurableArrearsToleranceVContainer");
        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceVContainer);
        this.configurableArrearsToleranceView = new ReadOnlyView("configurableArrearsToleranceView", new PropertyModel<>(this.itemPage, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceVContainer.add(this.configurableArrearsToleranceView);

        this.configurableOverdueBeforeMovingBlock = new WebMarkupBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
        this.form.add(this.configurableOverdueBeforeMovingBlock);
        this.configurableOverdueBeforeMovingVContainer = new WebMarkupContainer("configurableOverdueBeforeMovingVContainer");
        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingVContainer);
        this.configurableOverdueBeforeMovingView = new ReadOnlyView("configurableOverdueBeforeMovingView", new PropertyModel<>(this.itemPage, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingVContainer.add(this.configurableOverdueBeforeMovingView);

        this.configurableRepaidEveryBlock = new WebMarkupBlock("configurableRepaidEveryBlock", Size.Three_3);
        this.form.add(this.configurableRepaidEveryBlock);
        this.configurableRepaidEveryVContainer = new WebMarkupContainer("configurableRepaidEveryVContainer");
        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryVContainer);
        this.configurableRepaidEveryView = new ReadOnlyView("configurableRepaidEveryView", new PropertyModel<>(this.itemPage, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryVContainer.add(this.configurableRepaidEveryView);

        this.configurableMoratoriumBlock = new WebMarkupBlock("configurableMoratoriumBlock", Size.Three_3);
        this.form.add(this.configurableMoratoriumBlock);
        this.configurableMoratoriumVContainer = new WebMarkupContainer("configurableMoratoriumVContainer");
        this.configurableMoratoriumBlock.add(this.configurableMoratoriumVContainer);
        this.configurableMoratoriumView = new ReadOnlyView("configurableMoratoriumView", new PropertyModel<>(this.itemPage, "configurableMoratoriumValue"));
        this.configurableMoratoriumVContainer.add(this.configurableMoratoriumView);

        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        this.form.add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, this.chargeValue.getObject().size() + 1);
        this.chargeVContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.overdueChargeBlock = new WebMarkupBlock("overdueChargeBlock", Size.Twelve_12);
        this.form.add(this.overdueChargeBlock);
        this.overdueChargeVContainer = new WebMarkupContainer("overdueChargeVContainer");
        this.overdueChargeBlock.add(this.overdueChargeVContainer);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, this.overdueChargeValue.getObject().size() + 1);
        this.overdueChargeVContainer.add(this.overdueChargeTable);
        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));

        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.form.add(this.accountingLabel);

        this.cashMaster = new WebMarkupContainer("cashMaster");
        this.form.add(this.cashMaster);

        this.cashFundSourceBlock = new WebMarkupBlock("cashFundSourceBlock", Size.Four_4);
        this.cashMaster.add(this.cashFundSourceBlock);
        this.cashFundSourceVContainer = new WebMarkupContainer("cashFundSourceVContainer");
        this.cashFundSourceBlock.add(this.cashFundSourceVContainer);
        this.cashFundSourceView = new ReadOnlyView("cashFundSourceView", new PropertyModel<>(this.itemPage, "cashFundSourceValue"));
        this.cashFundSourceVContainer.add(this.cashFundSourceView);

        this.cashLoanPortfolioBlock = new WebMarkupBlock("cashLoanPortfolioBlock", Size.Four_4);
        this.cashMaster.add(this.cashLoanPortfolioBlock);
        this.cashLoanPortfolioVContainer = new WebMarkupContainer("cashLoanPortfolioVContainer");
        this.cashLoanPortfolioBlock.add(this.cashLoanPortfolioVContainer);
        this.cashLoanPortfolioView = new ReadOnlyView("cashLoanPortfolioView", new PropertyModel<>(this.itemPage, "cashLoanPortfolioValue"));
        this.cashLoanPortfolioVContainer.add(this.cashLoanPortfolioView);

        this.cashTransferInSuspenseBlock = new WebMarkupBlock("cashTransferInSuspenseBlock", Size.Four_4);
        this.cashMaster.add(this.cashTransferInSuspenseBlock);
        this.cashTransferInSuspenseVContainer = new WebMarkupContainer("cashTransferInSuspenseVContainer");
        this.cashTransferInSuspenseBlock.add(this.cashTransferInSuspenseVContainer);
        this.cashTransferInSuspenseView = new ReadOnlyView("cashTransferInSuspenseView", new PropertyModel<>(this.itemPage, "cashTransferInSuspenseValue"));
        this.cashTransferInSuspenseVContainer.add(this.cashTransferInSuspenseView);

        this.cashIncomeFromInterestBlock = new WebMarkupBlock("cashIncomeFromInterestBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromInterestBlock);
        this.cashIncomeFromInterestVContainer = new WebMarkupContainer("cashIncomeFromInterestVContainer");
        this.cashIncomeFromInterestBlock.add(this.cashIncomeFromInterestVContainer);
        this.cashIncomeFromInterestView = new ReadOnlyView("cashIncomeFromInterestView", new PropertyModel<>(this.itemPage, "cashIncomeFromInterestValue"));
        this.cashIncomeFromInterestVContainer.add(this.cashIncomeFromInterestView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);

        this.cashIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromRecoveryRepaymentBlock);
        this.cashIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("cashIncomeFromRecoveryRepaymentVContainer");
        this.cashIncomeFromRecoveryRepaymentBlock.add(this.cashIncomeFromRecoveryRepaymentVContainer);
        this.cashIncomeFromRecoveryRepaymentView = new ReadOnlyView("cashIncomeFromRecoveryRepaymentView", new PropertyModel<>(this.itemPage, "cashIncomeFromRecoveryRepaymentValue"));
        this.cashIncomeFromRecoveryRepaymentVContainer.add(this.cashIncomeFromRecoveryRepaymentView);

        this.cashLossWrittenOffBlock = new WebMarkupBlock("cashLossWrittenOffBlock", Size.Four_4);
        this.cashMaster.add(this.cashLossWrittenOffBlock);
        this.cashLossWrittenOffVContainer = new WebMarkupContainer("cashLossWrittenOffVContainer");
        this.cashLossWrittenOffBlock.add(this.cashLossWrittenOffVContainer);
        this.cashLossWrittenOffView = new ReadOnlyView("cashLossWrittenOffView", new PropertyModel<>(this.itemPage, "cashLossWrittenOffValue"));
        this.cashLossWrittenOffVContainer.add(this.cashLossWrittenOffView);

        this.cashOverPaymentLiabilityBlock = new WebMarkupBlock("cashOverPaymentLiabilityBlock", Size.Four_4);
        this.cashMaster.add(this.cashOverPaymentLiabilityBlock);
        this.cashOverPaymentLiabilityVContainer = new WebMarkupContainer("cashOverPaymentLiabilityVContainer");
        this.cashOverPaymentLiabilityBlock.add(this.cashOverPaymentLiabilityVContainer);
        this.cashOverPaymentLiabilityView = new ReadOnlyView("cashOverPaymentLiabilityView", new PropertyModel<>(this.itemPage, "cashOverPaymentLiabilityValue"));
        this.cashOverPaymentLiabilityVContainer.add(this.cashOverPaymentLiabilityView);

        this.accrualMaster = new WebMarkupContainer("accrualMaster");
        this.form.add(this.accrualMaster);

        this.accrualFundSourceBlock = new WebMarkupBlock("accrualFundSourceBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualFundSourceBlock);
        this.accrualFundSourceVContainer = new WebMarkupContainer("accrualFundSourceVContainer");
        this.accrualFundSourceBlock.add(this.accrualFundSourceVContainer);
        this.accrualFundSourceView = new ReadOnlyView("accrualFundSourceView", new PropertyModel<>(this.itemPage, "accrualFundSourceValue"));
        this.accrualFundSourceVContainer.add(this.accrualFundSourceView);

        this.accrualLoanPortfolioBlock = new WebMarkupBlock("accrualLoanPortfolioBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualLoanPortfolioBlock);
        this.accrualLoanPortfolioVContainer = new WebMarkupContainer("accrualLoanPortfolioVContainer");
        this.accrualLoanPortfolioBlock.add(this.accrualLoanPortfolioVContainer);
        this.accrualLoanPortfolioView = new ReadOnlyView("accrualLoanPortfolioView", new PropertyModel<>(this.itemPage, "accrualLoanPortfolioValue"));
        this.accrualLoanPortfolioVContainer.add(this.accrualLoanPortfolioView);

        this.accrualInterestReceivableBlock = new WebMarkupBlock("accrualInterestReceivableBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualInterestReceivableBlock);
        this.accrualInterestReceivableVContainer = new WebMarkupContainer("accrualInterestReceivableVContainer");
        this.accrualInterestReceivableBlock.add(this.accrualInterestReceivableVContainer);
        this.accrualInterestReceivableView = new ReadOnlyView("accrualInterestReceivableView", new PropertyModel<>(this.itemPage, "accrualInterestReceivableValue"));
        this.accrualInterestReceivableVContainer.add(this.accrualInterestReceivableView);

        this.accrualFeeReceivableBlock = new WebMarkupBlock("accrualFeeReceivableBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualFeeReceivableBlock);
        this.accrualFeeReceivableVContainer = new WebMarkupContainer("accrualFeeReceivableVContainer");
        this.accrualFeeReceivableBlock.add(this.accrualFeeReceivableVContainer);
        this.accrualFeeReceivableView = new ReadOnlyView("accrualFeeReceivableView", new PropertyModel<>(this.itemPage, "accrualFeeReceivableValue"));
        this.accrualFeeReceivableVContainer.add(this.accrualFeeReceivableView);

        this.accrualPenaltyReceivableBlock = new WebMarkupBlock("accrualPenaltyReceivableBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualPenaltyReceivableBlock);
        this.accrualPenaltyReceivableVContainer = new WebMarkupContainer("accrualPenaltyReceivableVContainer");
        this.accrualPenaltyReceivableBlock.add(this.accrualPenaltyReceivableVContainer);
        this.accrualPenaltyReceivableView = new ReadOnlyView("accrualPenaltyReceivableView", new PropertyModel<>(this.itemPage, "accrualPenaltyReceivableValue"));
        this.accrualPenaltyReceivableVContainer.add(this.accrualPenaltyReceivableView);

        this.accrualTransferInSuspenseBlock = new WebMarkupBlock("accrualTransferInSuspenseBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualTransferInSuspenseBlock);
        this.accrualTransferInSuspenseVContainer = new WebMarkupContainer("accrualTransferInSuspenseVContainer");
        this.accrualTransferInSuspenseBlock.add(this.accrualTransferInSuspenseVContainer);
        this.accrualTransferInSuspenseView = new ReadOnlyView("accrualTransferInSuspenseView", new PropertyModel<>(this.itemPage, "accrualTransferInSuspenseValue"));
        this.accrualTransferInSuspenseVContainer.add(this.accrualTransferInSuspenseView);

        this.accrualIncomeFromInterestBlock = new WebMarkupBlock("accrualIncomeFromInterestBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualIncomeFromInterestBlock);
        this.accrualIncomeFromInterestVContainer = new WebMarkupContainer("accrualIncomeFromInterestVContainer");
        this.accrualIncomeFromInterestBlock.add(this.accrualIncomeFromInterestVContainer);
        this.accrualIncomeFromInterestView = new ReadOnlyView("accrualIncomeFromInterestView", new PropertyModel<>(this.itemPage, "accrualIncomeFromInterestValue"));
        this.accrualIncomeFromInterestVContainer.add(this.accrualIncomeFromInterestView);

        this.accrualIncomeFromFeeBlock = new WebMarkupBlock("accrualIncomeFromFeeBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualIncomeFromFeeBlock);
        this.accrualIncomeFromFeeVContainer = new WebMarkupContainer("accrualIncomeFromFeeVContainer");
        this.accrualIncomeFromFeeBlock.add(this.accrualIncomeFromFeeVContainer);
        this.accrualIncomeFromFeeView = new ReadOnlyView("accrualIncomeFromFeeView", new PropertyModel<>(this.itemPage, "accrualIncomeFromFeeValue"));
        this.accrualIncomeFromFeeVContainer.add(this.accrualIncomeFromFeeView);

        this.accrualIncomeFromPenaltyBlock = new WebMarkupBlock("accrualIncomeFromPenaltyBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualIncomeFromPenaltyBlock);
        this.accrualIncomeFromPenaltyVContainer = new WebMarkupContainer("accrualIncomeFromPenaltyVContainer");
        this.accrualIncomeFromPenaltyBlock.add(this.accrualIncomeFromPenaltyVContainer);
        this.accrualIncomeFromPenaltyView = new ReadOnlyView("accrualIncomeFromPenaltyView", new PropertyModel<>(this.itemPage, "accrualIncomeFromPenaltyValue"));
        this.accrualIncomeFromPenaltyVContainer.add(this.accrualIncomeFromPenaltyView);

        this.accrualIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("accrualIncomeFromRecoveryRepaymentBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualIncomeFromRecoveryRepaymentBlock);
        this.accrualIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("accrualIncomeFromRecoveryRepaymentVContainer");
        this.accrualIncomeFromRecoveryRepaymentBlock.add(this.accrualIncomeFromRecoveryRepaymentVContainer);
        this.accrualIncomeFromRecoveryRepaymentView = new ReadOnlyView("accrualIncomeFromRecoveryRepaymentView", new PropertyModel<>(this.itemPage, "accrualIncomeFromRecoveryRepaymentValue"));
        this.accrualIncomeFromRecoveryRepaymentVContainer.add(this.accrualIncomeFromRecoveryRepaymentView);

        this.accrualLossWrittenOffBlock = new WebMarkupBlock("accrualLossWrittenOffBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualLossWrittenOffBlock);
        this.accrualLossWrittenOffVContainer = new WebMarkupContainer("accrualLossWrittenOffVContainer");
        this.accrualLossWrittenOffBlock.add(this.accrualLossWrittenOffVContainer);
        this.accrualLossWrittenOffView = new ReadOnlyView("accrualLossWrittenOffView", new PropertyModel<>(this.itemPage, "accrualLossWrittenOffValue"));
        this.accrualLossWrittenOffVContainer.add(this.accrualLossWrittenOffView);

        this.accrualOverPaymentLiabilityBlock = new WebMarkupBlock("accrualOverPaymentLiabilityBlock", Size.Four_4);
        this.accrualMaster.add(this.accrualOverPaymentLiabilityBlock);
        this.accrualOverPaymentLiabilityVContainer = new WebMarkupContainer("accrualOverPaymentLiabilityVContainer");
        this.accrualOverPaymentLiabilityBlock.add(this.accrualOverPaymentLiabilityVContainer);
        this.accrualOverPaymentLiabilityView = new ReadOnlyView("accrualOverPaymentLiabilityView", new PropertyModel<>(this.itemPage, "accrualOverPaymentLiabilityValue"));
        this.accrualOverPaymentLiabilityVContainer.add(this.accrualOverPaymentLiabilityView);

        this.advancedAccountingRuleMaster = new WebMarkupContainer("advancedAccountingRuleMaster");
        this.form.add(this.advancedAccountingRuleMaster);

        this.advancedAccountingRuleFundSourceBlock = new WebMarkupContainer("advancedAccountingRuleFundSourceBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRuleFundSourceBlock);
        this.advancedAccountingRuleFundSourceVContainer = new WebMarkupContainer("advancedAccountingRuleFundSourceVContainer");
        this.advancedAccountingRuleFundSourceBlock.add(this.advancedAccountingRuleFundSourceVContainer);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, this.advancedAccountingRuleFundSourceValue.getObject().size() + 1);
        this.advancedAccountingRuleFundSourceVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFeeIncomeBlock = new WebMarkupContainer("advancedAccountingRuleFeeIncomeBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRuleFeeIncomeBlock);
        this.advancedAccountingRuleFeeIncomeVContainer = new WebMarkupContainer("advancedAccountingRuleFeeIncomeVContainer");
        this.advancedAccountingRuleFeeIncomeBlock.add(this.advancedAccountingRuleFeeIncomeVContainer);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, this.advancedAccountingRuleFeeIncomeValue.getObject().size() + 1);
        this.advancedAccountingRuleFeeIncomeVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRulePenaltyIncomeBlock = new WebMarkupContainer("advancedAccountingRulePenaltyIncomeBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRulePenaltyIncomeBlock);
        this.advancedAccountingRulePenaltyIncomeVContainer = new WebMarkupContainer("advancedAccountingRulePenaltyIncomeVContainer");
        this.advancedAccountingRulePenaltyIncomeBlock.add(this.advancedAccountingRulePenaltyIncomeVContainer);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, this.advancedAccountingRulePenaltyIncomeValue.getObject().size() + 1);
        this.advancedAccountingRulePenaltyIncomeVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

    }

    @Override
    protected void configureMetaData() {
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
            this.cashMaster.setVisible(false);
            this.accrualMaster.setVisible(false);
            this.advancedAccountingRuleMaster.setVisible(false);
        } else {
            if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
                this.accrualMaster.setVisible(false);
            } else if (AccountingType.Periodic.getDescription().equals(accountingValue.getObject()) || AccountingType.Upfront.getDescription().equals(accountingValue.getObject())) {
                this.cashMaster.setVisible(false);
            }
        }

        PropertyModel<Boolean> termLinkedToFloatingInterestRatesValue = new PropertyModel<>(this.itemPage, "termLinkedToFloatingInterestRatesValue");
        this.nominalRateMaster.setVisible(termLinkedToFloatingInterestRatesValue.getObject() == null || !termLinkedToFloatingInterestRatesValue.getObject());
        this.floatingRateMaster.setVisible(termLinkedToFloatingInterestRatesValue.getObject() != null && termLinkedToFloatingInterestRatesValue.getObject());

        PropertyModel<Boolean> termVaryBasedOnLoanCycleValue = new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue");
        this.loanCycleMaster.setVisible(termVaryBasedOnLoanCycleValue.getObject() != null && termVaryBasedOnLoanCycleValue.getObject());

        PropertyModel<Boolean> loanTrancheDetailEnableMultipleDisbursalValue = new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue");
        boolean loanTrancheVisible = loanTrancheDetailEnableMultipleDisbursalValue.getObject() != null && loanTrancheDetailEnableMultipleDisbursalValue.getObject();

        this.loanTrancheDetailMaximumTrancheCountVContainer.setVisible(loanTrancheVisible);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.setVisible(loanTrancheVisible);

        PropertyModel<Boolean> configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue");
        boolean configurableVisible = configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject() != null && configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject();

        this.configurableAmortizationVContainer.setVisible(configurableVisible);
        this.configurableInterestMethodVContainer.setVisible(configurableVisible);
        this.configurableRepaymentStrategyVContainer.setVisible(configurableVisible);
        this.configurableInterestCalculationPeriodVContainer.setVisible(configurableVisible);
        this.configurableArrearsToleranceVContainer.setVisible(configurableVisible);
        this.configurableRepaidEveryVContainer.setVisible(configurableVisible);
        this.configurableMoratoriumVContainer.setVisible(configurableVisible);
        this.configurableOverdueBeforeMovingVContainer.setVisible(configurableVisible);

        PropertyModel<Boolean> guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue");
        this.guaranteeMaster.setVisible(guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject() != null && guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject());
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);

        PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");
        Frequency compoundFrequency = null;
        if (interestRecalculationCompoundingValue.getObject() != null) {
            compoundFrequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
        }
        this.interestRecalculationCompoundingIntervalVContainer.setVisible(!(compoundFrequency == null || compoundFrequency == Frequency.Same));

        PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
        Frequency outstandingPrincipalFrequency = null;
        if (interestRecalculationRecalculateValue.getObject() != null) {
            outstandingPrincipalFrequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
        }
        this.interestRecalculationOutstandingPrincipalIntervalVContainer.setVisible(!(outstandingPrincipalFrequency == null || outstandingPrincipalFrequency == Frequency.Same));

        PropertyModel<Option> interestRecalculationCompoundingOnValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue");
        InterestRecalculationCompound compoundType = null;
        if (interestRecalculationCompoundingOnValue.getObject() != null) {
            compoundType = InterestRecalculationCompound.valueOf(interestRecalculationCompoundingOnValue.getObject().getId());
        }

        if (compoundType == null || compoundType == InterestRecalculationCompound.None) {
            this.interestRecalculationCompoundingVContainer.setVisible(false);
            this.interestRecalculationCompoundingIntervalVContainer.setVisible(false);
        }

        PropertyModel<Boolean> settingVariableInstallmentsAllowedValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue");
        this.settingVariableMaster.setVisible(settingVariableInstallmentsAllowedValue.getObject() != null && settingVariableInstallmentsAllowedValue.getObject());

        PropertyModel<Option> settingInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue");
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (settingInterestCalculationPeriodValue.getObject() != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(settingInterestCalculationPeriodValue.getObject().getId());
        }
        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.setVisible(interestCalculationPeriod != null && interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment);

        this.saveButton.setVisible(!this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
    }

    protected ItemPanel termPrincipleByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
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

    protected ItemPanel termNominalInterestRateByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
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

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
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

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_ACCOUNTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        if (this.itemPage instanceof SavingCreatePage) {
            ((SavingCreatePage) this.itemPage).saveButtonSubmit(button);
        }
    }

}
