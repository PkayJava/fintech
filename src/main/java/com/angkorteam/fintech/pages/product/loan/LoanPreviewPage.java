//package com.angkorteam.fintech.pages.product.loan;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.WebMarkupContainer;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.Radio;
//import org.apache.wicket.markup.html.form.RadioGroup;
//import org.apache.wicket.markup.html.form.TextView;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.validation.validator.RangeValidator;
//import org.apache.wicket.validation.validator.StringValidator;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.AllowAttributeOverrideBuilder;
//import com.angkorteam.fintech.dto.builder.ProductLoanBuilder;
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.dto.enums.AccountUsage;
//import com.angkorteam.fintech.dto.enums.AccountingType;
//import com.angkorteam.fintech.dto.enums.ChargeCalculation;
//import com.angkorteam.fintech.dto.enums.ChargeTime;
//import com.angkorteam.fintech.dto.enums.DayInYear;
//import com.angkorteam.fintech.dto.enums.LockInType;
//import com.angkorteam.fintech.dto.enums.ProductPopup;
//import com.angkorteam.fintech.dto.enums.loan.AdvancePaymentsAdjustmentType;
//import com.angkorteam.fintech.dto.enums.loan.Amortization;
//import com.angkorteam.fintech.dto.enums.loan.ClosureInterestCalculationRule;
//import com.angkorteam.fintech.dto.enums.loan.DayInMonth;
//import com.angkorteam.fintech.dto.enums.loan.Frequency;
//import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
//import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
//import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
//import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
//import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
//import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateType;
//import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
//import com.angkorteam.fintech.dto.enums.loan.WhenType;
//import com.angkorteam.fintech.helper.LoanHelper;
//import com.angkorteam.fintech.pages.ProductDashboardPage;
//import com.angkorteam.fintech.popup.ChargePopup;
//import com.angkorteam.fintech.popup.CurrencyPopup;
//import com.angkorteam.fintech.popup.FeeChargePopup;
//import com.angkorteam.fintech.popup.InterestLoanCyclePopup;
//import com.angkorteam.fintech.popup.PaymentTypePopup;
//import com.angkorteam.fintech.popup.PenaltyChargePopup;
//import com.angkorteam.fintech.popup.PrincipalLoanCyclePopup;
//import com.angkorteam.fintech.popup.RepaymentLoanCyclePopup;
//import com.angkorteam.fintech.provider.CurrencyProvider;
//import com.angkorteam.fintech.provider.DayInYearProvider;
//import com.angkorteam.fintech.provider.FundProvider;
//import com.angkorteam.fintech.provider.LockInTypeProvider;
//import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.provider.loan.AdvancePaymentsAdjustmentTypeProvider;
//import com.angkorteam.fintech.provider.loan.AmortizationProvider;
//import com.angkorteam.fintech.provider.loan.ClosureInterestCalculationRuleProvider;
//import com.angkorteam.fintech.provider.loan.DayInMonthProvider;
//import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
//import com.angkorteam.fintech.provider.loan.FrequencyProvider;
//import com.angkorteam.fintech.provider.loan.FrequencyTypeProvider;
//import com.angkorteam.fintech.provider.loan.InterestCalculationPeriodProvider;
//import com.angkorteam.fintech.provider.loan.InterestMethodProvider;
//import com.angkorteam.fintech.provider.loan.InterestRecalculationCompoundProvider;
//import com.angkorteam.fintech.provider.loan.RepaymentStrategyProvider;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextView;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.exceptions.UnirestException;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanPreviewPage extends Page {
//
//    protected String loanId;
//
//    protected BookmarkablePageLink<Void> editLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    // Detail
//
//    protected WebMarkupBlock detailProductNameBlock;
//    protected WebMarkupContainer detailProductNameVContainer;
//    protected String detailProductNameValue;
//    protected ReadOnlyView detailProductNameView;
//
//    protected WebMarkupBlock detailShortNameBlock;
//    protected WebMarkupContainer detailShortNameVContainer;
//    protected String detailShortNameValue;
//    protected ReadOnlyView detailShortNameView;
//
//    protected WebMarkupBlock detailDescriptionBlock;
//    protected WebMarkupContainer detailDescriptionVContainer;
//    protected String detailDescriptionValue;
//    protected ReadOnlyView detailDescriptionView;
//
//    protected WebMarkupBlock detailFundBlock;
//    protected WebMarkupContainer detailFundVContainer;
//    protected String detailFundValue;
//    protected ReadOnlyView detailFundView;
//
//    protected WebMarkupBlock detailStartDateBlock;
//    protected WebMarkupContainer detailStartDateVContainer;
//    protected Date detailStartDateValue;
//    protected ReadOnlyView detailStartDateView;
//
//    protected WebMarkupBlock detailCloseDateBlock;
//    protected WebMarkupContainer detailCloseDateVContainer;
//    protected Date detailCloseDateValue;
//    protected ReadOnlyView detailCloseDateView;
//
//    protected WebMarkupBlock detailIncludeInCustomerLoanCounterBlock;
//    protected WebMarkupContainer detailIncludeInCustomerLoanCounterVContainer;
//    protected Boolean detailIncludeInCustomerLoanCounterValue;
//    protected ReadOnlyView detailIncludeInCustomerLoanCounterView;
//
//    // Currency
//
//    protected WebMarkupBlock currencyCodeBlock;
//    protected WebMarkupContainer currencyCodeVContainer;
//    protected String currencyCodeValue;
//    protected ReadOnlyView currencyCodeView;
//
//    protected WebMarkupBlock currencyDecimalPlaceBlock;
//    protected WebMarkupContainer currencyDecimalPlaceVContainer;
//    protected Long currencyDecimalPlaceValue;
//    protected ReadOnlyView currencyDecimalPlaceView;
//
//    protected WebMarkupBlock currencyInMultipleOfBlock;
//    protected WebMarkupContainer currencyInMultipleOfVContainer;
//    protected Long currencyInMultipleOfValue;
//    protected ReadOnlyView currencyInMultipleOfView;
//
//    protected WebMarkupBlock currencyInstallmentInMultipleOfBlock;
//    protected WebMarkupContainer currencyInstallmentInMultipleOfVContainer;
//    protected Long currencyInstallmentInMultipleOfValue;
//    protected ReadOnlyView currencyInstallmentInMultipleOfView;
//
//    // Terms
//
//    // Row 1 : Terms vary based on loan cycle
//    protected WebMarkupBlock termVaryBasedOnLoanCycleBlock;
//    protected WebMarkupContainer termVaryBasedOnLoanCycleVContainer;
//    protected Boolean termVaryBasedOnLoanCycleValue;
//    protected ReadOnlyView termVaryBasedOnLoanCycleView;
//
//    // Row 2 : Principal
//    protected WebMarkupBlock termPrincipalMinimumBlock;
//    protected WebMarkupContainer termPrincipalMinimumVContainer;
//    protected Double termPrincipalMinimumValue;
//    protected ReadOnlyView termPrincipalMinimumView;
//
//    protected WebMarkupBlock termPrincipalDefaultBlock;
//    protected WebMarkupContainer termPrincipalDefaultVContainer;
//    protected Double termPrincipalDefaultValue;
//    protected ReadOnlyView termPrincipalDefaultView;
//
//    protected WebMarkupBlock termPrincipalMaximumBlock;
//    protected WebMarkupContainer termPrincipalMaximumVContainer;
//    protected Double termPrincipalMaximumValue;
//    protected ReadOnlyView termPrincipalMaximumView;
//
//    // Row 2 (Optional) : Principal by loan cycle
//    protected WebMarkupBlock termPrincipalByLoanCycleBlock;
//    protected WebMarkupContainer termPrincipalByLoanCycleVContainer;
//    protected List<Map<String, Object>> termPrincipalByLoanCycleValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> termPrincipalByLoanCycleTable;
//    protected List<IColumn<Map<String, Object>, String>> termPrincipalByLoanCycleColumn;
//    protected ListDataProvider termPrincipalByLoanCycleProvider;
//
//    // Row 3 : Number of repayments
//    protected WebMarkupBlock termNumberOfRepaymentMinimumBlock;
//    protected WebMarkupContainer termNumberOfRepaymentMinimumVContainer;
//    protected Long termNumberOfRepaymentMinimumValue;
//    protected ReadOnlyView termNumberOfRepaymentMinimumView;
//
//    protected WebMarkupBlock termNumberOfRepaymentDefaultBlock;
//    protected WebMarkupContainer termNumberOfRepaymentDefaultVContainer;
//    protected Long termNumberOfRepaymentDefaultValue;
//    protected ReadOnlyView termNumberOfRepaymentDefaultView;
//
//    protected WebMarkupBlock termNumberOfRepaymentMaximumBlock;
//    protected WebMarkupContainer termNumberOfRepaymentMaximumVContainer;
//    protected Long termNumberOfRepaymentMaximumValue;
//    protected ReadOnlyView termNumberOfRepaymentMaximumView;
//
//    // Row 3 (Optional) : Number of Repayments by loan cycle
//    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
//    protected WebMarkupBlock termNumberOfRepaymentByLoanCycleBlock;
//    protected WebMarkupContainer termNumberOfRepaymentByLoanCycleVContainer;
//    protected List<Map<String, Object>> termNumberOfRepaymentByLoanCycleValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
//    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
//
//    // Row 4 : Is Linked to Floating Interest Rates?
//    protected WebMarkupBlock termLinkedToFloatingInterestRatesBlock;
//    protected WebMarkupContainer termLinkedToFloatingInterestRatesVContainer;
//    protected Boolean termLinkedToFloatingInterestRatesValue;
//    protected ReadOnlyView termLinkedToFloatingInterestRatesView;
//
//    // Row 5 : Nominal interest rate
//    protected WebMarkupBlock termNominalInterestRateMinimumBlock;
//    protected WebMarkupContainer termNominalInterestRateMinimumVContainer;
//    protected Double termNominalInterestRateMinimumValue;
//    protected ReadOnlyView termNominalInterestRateMinimumView;
//
//    protected WebMarkupBlock termNominalInterestRateDefaultBlock;
//    protected WebMarkupContainer termNominalInterestRateDefaultVContainer;
//    protected Double termNominalInterestRateDefaultValue;
//    protected ReadOnlyView termNominalInterestRateDefaultView;
//
//    protected WebMarkupBlock termNominalInterestRateMaximumBlock;
//    protected WebMarkupContainer termNominalInterestRateMaximumVContainer;
//    protected Double termNominalInterestRateMaximumValue;
//    protected ReadOnlyView termNominalInterestRateMaximumView;
//
//    protected WebMarkupBlock termNominalInterestRateTypeBlock;
//    protected WebMarkupContainer termNominalInterestRateTypeVContainer;
//    protected Option termNominalInterestRateTypeValue;
//    protected ReadOnlyView termNominalInterestRateTypeView;
//
//    protected WebMarkupBlock termFloatingInterestRateBlock;
//    protected WebMarkupContainer termFloatingInterestRateVContainer;
//    protected Option termFloatingInterestRateValue;
//    protected ReadOnlyView termFloatingInterestRateView;
//
//    protected WebMarkupBlock termFloatingInterestDifferentialBlock;
//    protected WebMarkupContainer termFloatingInterestDifferentialVContainer;
//    protected Double termFloatingInterestDifferentialValue;
//    protected ReadOnlyView termFloatingInterestDifferentialView; 
//
//    protected WebMarkupBlock termFloatingInterestAllowedBlock;
//    protected WebMarkupContainer termFloatingInterestAllowedVContainer;
//    protected Boolean termFloatingInterestAllowedValue;
//    protected ReadOnlyView termFloatingInterestAllowedView;
//
//    protected WebMarkupBlock termFloatingInterestMinimumBlock;
//    protected WebMarkupContainer termFloatingInterestMinimumVContainer;
//    protected Double termFloatingInterestMinimumValue;
//    protected ReadOnlyView termFloatingInterestMinimumView; 
//
//    protected WebMarkupBlock termFloatingInterestDefaultBlock;
//    protected WebMarkupContainer termFloatingInterestDefaultVContainer;
//    protected Double termFloatingInterestDefaultValue;
//    protected ReadOnlyView termFloatingInterestDefaultView; 
//
//    protected WebMarkupBlock termFloatingInterestMaximumBlock;
//    protected WebMarkupContainer termFloatingInterestMaximumVContainer;
//    protected Double termFloatingInterestMaximumValue;
//    protected ReadOnlyView termFloatingInterestMaximumView; 
//
//    // Row 6
//    protected WebMarkupBlock termNominalInterestRateByLoanCycleBlock;
//    protected WebMarkupContainer termNominalInterestRateByLoanCycleVContainer;
//    protected List<Map<String, Object>> termNominalInterestRateByLoanCycleValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
//    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
//    protected AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
//    protected ModalWindow termNominalInterestRateByLoanCyclePopup;
//
//    protected WebMarkupBlock termRepaidEveryBlock;
//    protected WebMarkupContainer termRepaidEveryVContainer;
//    protected Long termRepaidEveryValue;
//    protected ReadOnlyView termRepaidEveryView;
//
//    protected WebMarkupBlock termRepaidTypeBlock;
//    protected WebMarkupContainer termRepaidTypeVContainer;
//    protected Option termRepaidTypeValue;
//    protected ReadOnlyView termRepaidTypeView;
//
//    protected WebMarkupBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
//    protected WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer;
//    protected Long termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue;
//    protected ReadOnlyView termMinimumDayBetweenDisbursalAndFirstRepaymentDateView;
//
//    // Settings
//
//    protected WebMarkupBlock settingAmortizationBlock;
//    protected WebMarkupContainer settingAmortizationVContainer;
//    protected Option settingAmortizationValue;
//    protected ReadOnlyView settingAmortizationView;
//
//    protected WebMarkupBlock settingInterestMethodBlock;
//    protected WebMarkupContainer settingInterestMethodVContainer;
//    protected Option settingInterestMethodValue;
//    protected ReadOnlyView settingInterestMethodView;
//
//    protected WebMarkupBlock settingInterestCalculationPeriodBlock;
//    protected WebMarkupContainer settingInterestCalculationPeriodVContainer;
//    protected Option settingInterestCalculationPeriodValue;
//    protected ReadOnlyView settingInterestCalculationPeriodView;
//
//    protected WebMarkupBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
//    protected WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodVContainer;
//    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
//    protected ReadOnlyView settingCalculateInterestForExactDaysInPartialPeriodView;
//
//    protected WebMarkupBlock settingRepaymentStrategyBlock;
//    protected WebMarkupContainer settingRepaymentStrategyVContainer;
//    protected Option settingRepaymentStrategyValue;
//    protected ReadOnlyView settingRepaymentStrategyView;
//
//    protected WebMarkupBlock settingMoratoriumPrincipalBlock;
//    protected WebMarkupContainer settingMoratoriumPrincipalVContainer;
//    protected Long settingMoratoriumPrincipalValue;
//    protected ReadOnlyView settingMoratoriumPrincipalView;
//
//    protected WebMarkupBlock settingMoratoriumInterestBlock;
//    protected WebMarkupContainer settingMoratoriumInterestVContainer;
//    protected Long settingMoratoriumInterestValue;
//    protected ReadOnlyView settingMoratoriumInterestView;
//
//    protected WebMarkupBlock settingInterestFreePeriodBlock;
//    protected WebMarkupContainer settingInterestFreePeriodVContainer;
//    protected Long settingInterestFreePeriodValue;
//    protected ReadOnlyView settingInterestFreePeriodView;
//
//    protected WebMarkupBlock settingArrearsToleranceBlock;
//    protected WebMarkupContainer settingArrearsToleranceVContainer;
//    protected Double settingArrearsToleranceValue;
//    protected ReadOnlyView settingArrearsToleranceView; 
//
//    protected WebMarkupBlock settingDayInYearBlock;
//    protected WebMarkupContainer settingDayInYearVContainer;
//    protected Option settingDayInYearValue;
//    protected ReadOnlyView settingDayInYearView;
//
//    protected WebMarkupBlock settingDayInMonthBlock;
//    protected WebMarkupContainer settingDayInMonthVContainer; 
//    protected Option settingDayInMonthValue;
//    protected ReadOnlyView settingDayInMonthView; 
//
//    protected WebMarkupBlock settingAllowFixingOfTheInstallmentAmountBlock;
//    protected WebMarkupContainer settingAllowFixingOfTheInstallmentAmountVContainer;
//    protected Boolean settingAllowFixingOfTheInstallmentAmountValue;
//    protected ReadOnlyView settingAllowFixingOfTheInstallmentAmountView;
//
//    protected WebMarkupBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
//    protected WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer;
//    protected Long settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
//    protected ReadOnlyView settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView;
//
//    protected WebMarkupBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
//    protected WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer;
//    protected Long settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
//    protected ReadOnlyView settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView;
//
//    protected WebMarkupBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
//    protected WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer;
//    protected Boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
//    protected ReadOnlyView settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView;
//
//    protected WebMarkupBlock settingPrincipalThresholdForLastInstalmentBlock;
//    protected WebMarkupContainer settingPrincipalThresholdForLastInstalmentVContainer;
//    protected Double settingPrincipalThresholdForLastInstalmentValue;
//    protected ReadOnlyView settingPrincipalThresholdForLastInstalmentView; 
//
//    protected WebMarkupBlock settingVariableInstallmentsAllowedBlock;
//    protected WebMarkupContainer settingVariableInstallmentsAllowedVContainer;
//    protected Boolean settingVariableInstallmentsAllowedValue;
//    protected ReadOnlyView settingVariableInstallmentsAllowedView;
//
//    protected WebMarkupBlock settingVariableInstallmentsMinimumBlock;
//    protected WebMarkupContainer settingVariableInstallmentsMinimumVContainer;
//    protected Long settingVariableInstallmentsMinimumValue;
//    protected ReadOnlyView settingVariableInstallmentsMinimumView;
//
//    protected WebMarkupBlock settingVariableInstallmentsMaximumBlock;
//    protected WebMarkupContainer settingVariableInstallmentsMaximumVContainer;
//    protected Long settingVariableInstallmentsMaximumValue;
//    protected ReadOnlyView settingVariableInstallmentsMaximumView;
//
//    protected WebMarkupBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
//    protected WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansVContainer;
//    protected Boolean settingAllowedToBeUsedForProvidingTopupLoansValue;
//    protected ReadOnlyView settingAllowedToBeUsedForProvidingTopupLoansView;
//
//    // Interest Recalculation
//
//    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
//    protected WebMarkupContainer interestRecalculationRecalculateInterestVContainer;
//    protected Boolean interestRecalculationRecalculateInterestValue;
//    protected ReadOnlyView interestRecalculationRecalculateInterestView;
//
//    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
//    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleVContainer;
//    protected Option interestRecalculationPreClosureInterestCalculationRuleValue;
//    protected ReadOnlyView interestRecalculationPreClosureInterestCalculationRuleView; 
//
//    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
//    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeVContainer;
//    protected AdvancePaymentsAdjustmentTypeProvider interestRecalculationAdvancePaymentsAdjustmentTypeProvider;
//    protected Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
//    protected Select2SingleChoice<Option> interestRecalculationAdvancePaymentsAdjustmentTypeView;
//    protected TextFeedbackPanel interestRecalculationAdvancePaymentsAdjustmentTypeFeedback;
//
//    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
//    protected WebMarkupContainer interestRecalculationCompoundingOnVContainer;
//    protected InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
//    protected Option interestRecalculationCompoundingOnValue;
//    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnView;
//    protected TextFeedbackPanel interestRecalculationCompoundingOnFeedback;
//
//    protected WebMarkupBlock interestRecalculationCompoundingBlock;
//    protected WebMarkupContainer interestRecalculationCompoundingVContainer;
//    protected FrequencyProvider interestRecalculationCompoundingProvider;
//    protected Option interestRecalculationCompoundingValue;
//    protected Select2SingleChoice<Option> interestRecalculationCompoundingView;
//    protected TextFeedbackPanel interestRecalculationCompoundingFeedback;
//
//    protected WebMarkupBlock interestRecalculationCompoundingTypeBlock;
//    protected WebMarkupContainer interestRecalculationCompoundingTypeVContainer;
//    protected FrequencyTypeProvider interestRecalculationCompoundingTypeProvider;
//    protected Option interestRecalculationCompoundingTypeValue;
//    protected Select2SingleChoice<Option> interestRecalculationCompoundingTypeView;
//    protected TextFeedbackPanel interestRecalculationCompoundingTypeFeedback;
//
//    protected WebMarkupBlock interestRecalculationCompoundingDayBlock;
//    protected WebMarkupContainer interestRecalculationCompoundingDayVContainer;
//    protected FrequencyDayProvider interestRecalculationCompoundingDayProvider;
//    protected Option interestRecalculationCompoundingDayValue;
//    protected Select2SingleChoice<Option> interestRecalculationCompoundingDayView;
//    protected TextFeedbackPanel interestRecalculationCompoundingDayFeedback;
//
//    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
//    protected WebMarkupContainer interestRecalculationCompoundingIntervalVContainer;
//    protected Long interestRecalculationCompoundingIntervalValue;
//    protected ReadOnlyView interestRecalculationCompoundingIntervalView;
//
//    protected WebMarkupBlock interestRecalculationRecalculateBlock;
//    protected WebMarkupContainer interestRecalculationRecalculateVContainer;
//    protected FrequencyProvider interestRecalculationRecalculateProvider;
//    protected Option interestRecalculationRecalculateValue;
//    protected Select2SingleChoice<Option> interestRecalculationRecalculateView;
//    protected TextFeedbackPanel interestRecalculationRecalculateFeedback;
//
//    protected WebMarkupBlock interestRecalculationRecalculateTypeBlock;
//    protected WebMarkupContainer interestRecalculationRecalculateTypeVContainer;
//    protected FrequencyTypeProvider interestRecalculationRecalculateTypeProvider;
//    protected Option interestRecalculationRecalculateTypeValue;
//    protected Select2SingleChoice<Option> interestRecalculationRecalculateTypeView;
//    protected TextFeedbackPanel interestRecalculationRecalculateTypeFeedback;
//
//    protected WebMarkupBlock interestRecalculationRecalculateDayBlock;
//    protected WebMarkupContainer interestRecalculationRecalculateDayVContainer;
//    protected FrequencyDayProvider interestRecalculationRecalculateDayProvider;
//    protected Option interestRecalculationRecalculateDayValue;
//    protected Select2SingleChoice<Option> interestRecalculationRecalculateDayView;
//    protected TextFeedbackPanel interestRecalculationRecalculateDayFeedback;
//
//    protected WebMarkupBlock interestRecalculationRecalculateIntervalBlock;
//    protected WebMarkupContainer interestRecalculationRecalculateIntervalVContainer;
//    protected Long interestRecalculationRecalculateIntervalValue;
//    protected ReadOnlyView interestRecalculationRecalculateIntervalView;
//
//    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
//    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer;
//    protected Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;
//    protected ReadOnlyView interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView;
//
//    // Guarantee Requirements
//
//    protected WebMarkupBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
//    protected WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer;
//    protected Boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
//    protected ReadOnlyView guaranteeRequirementPlaceGuaranteeFundsOnHoldView;
//
//    protected WebMarkupBlock guaranteeRequirementMandatoryGuaranteeBlock;
//    protected WebMarkupContainer guaranteeRequirementMandatoryGuaranteeVContainer;
//    protected Double guaranteeRequirementMandatoryGuaranteeValue;
//    protected ReadOnlyView guaranteeRequirementMandatoryGuaranteeView; 
//
//    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeBlock;
//    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeVContainer;
//    protected Double guaranteeRequirementMinimumGuaranteeValue;
//    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeView; 
//
//    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
//    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer;
//    protected Double guaranteeRequirementMinimumGuaranteeFromGuarantorValue;
//    protected ReadOnlyView guaranteeRequirementMinimumGuaranteeFromGuarantorView; 
//
//    // Loan Tranche Details
//
//    protected WebMarkupBlock loanTrancheDetailEnableMultipleDisbursalBlock;
//    protected WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalVContainer;
//    protected Boolean loanTrancheDetailEnableMultipleDisbursalValue;
//    protected ReadOnlyView loanTrancheDetailEnableMultipleDisbursalView;
//
//    protected WebMarkupBlock loanTrancheDetailMaximumTrancheCountBlock;
//    protected WebMarkupContainer loanTrancheDetailMaximumTrancheCountVContainer;
//    protected Long loanTrancheDetailMaximumTrancheCountValue;
//    protected ReadOnlyView loanTrancheDetailMaximumTrancheCountView;
//
//    protected WebMarkupBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
//    protected WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer;
//    protected Double loanTrancheDetailMaximumAllowedOutstandingBalanceValue;
//    protected ReadOnlyView loanTrancheDetailMaximumAllowedOutstandingBalanceView; 
//
//    // Configurable Terms and Settings
//
//    protected WebMarkupBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
//    protected WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer;
//    protected Boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
//    protected ReadOnlyView configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView;
//
//    protected WebMarkupBlock configurableAmortizationBlock;
//    protected WebMarkupContainer configurableAmortizationVContainer;
//    protected Boolean configurableAmortizationValue;
//    protected ReadOnlyView configurableAmortizationView;
//
//    protected WebMarkupBlock configurableInterestMethodBlock;
//    protected WebMarkupContainer configurableInterestMethodVContainer;
//    protected Boolean configurableInterestMethodValue;
//    protected ReadOnlyView configurableInterestMethodView;
//
//    protected WebMarkupBlock configurableRepaymentStrategyBlock;
//    protected WebMarkupContainer configurableRepaymentStrategyVContainer;
//    protected Boolean configurableRepaymentStrategyValue;
//    protected ReadOnlyView configurableRepaymentStrategyView;
//
//    protected WebMarkupBlock configurableInterestCalculationPeriodBlock;
//    protected WebMarkupContainer configurableInterestCalculationPeriodVContainer;
//    protected Boolean configurableInterestCalculationPeriodValue;
//    protected ReadOnlyView configurableInterestCalculationPeriodView;
//
//    protected WebMarkupBlock configurableArrearsToleranceBlock;
//    protected WebMarkupContainer configurableArrearsToleranceVContainer;
//    protected Boolean configurableArrearsToleranceValue;
//    protected ReadOnlyView configurableArrearsToleranceView;
//
//    protected WebMarkupBlock configurableRepaidEveryBlock;
//    protected WebMarkupContainer configurableRepaidEveryVContainer;
//    protected Boolean configurableRepaidEveryValue;
//    protected ReadOnlyView configurableRepaidEveryView;
//
//    protected WebMarkupBlock configurableMoratoriumBlock;
//    protected WebMarkupContainer configurableMoratoriumVContainer;
//    protected Boolean configurableMoratoriumValue;
//    protected ReadOnlyView configurableMoratoriumView;
//
//    protected WebMarkupBlock configurableOverdueBeforeMovingBlock;
//    protected WebMarkupContainer configurableOverdueBeforeMovingVContainer;
//    protected Boolean configurableOverdueBeforeMovingValue;
//    protected ReadOnlyView configurableOverdueBeforeMovingView;
//
//    // Accounting
//
//    protected String accountingValue = AccountingType.None.getDescription();
//    protected RadioGroup<String> accountingView;
//
//    protected WebMarkupContainer cashBlock;
//    protected WebMarkupContainer cashVContainer;
//
//    protected WebMarkupBlock cashFundSourceBlock;
//    protected WebMarkupContainer cashFundSourceVContainer;
//    protected SingleChoiceProvider cashFundSourceProvider;
//    protected Option cashFundSourceValue;
//    protected Select2SingleChoice<Option> cashFundSourceView;
//    protected TextFeedbackPanel cashFundSourceFeedback;
//
//    protected WebMarkupBlock cashLoanPortfolioBlock;
//    protected WebMarkupContainer cashLoanPortfolioVContainer;
//    protected SingleChoiceProvider cashLoanPortfolioProvider;
//    protected Option cashLoanPortfolioValue;
//    protected Select2SingleChoice<Option> cashLoanPortfolioView;
//    protected TextFeedbackPanel cashLoanPortfolioFeedback;
//
//    protected WebMarkupBlock cashTransferInSuspenseBlock;
//    protected WebMarkupContainer cashTransferInSuspenseVContainer;
//    protected SingleChoiceProvider cashTransferInSuspenseProvider;
//    protected Option cashTransferInSuspenseValue;
//    protected Select2SingleChoice<Option> cashTransferInSuspenseView;
//    protected TextFeedbackPanel cashTransferInSuspenseFeedback;
//
//    protected WebMarkupBlock cashIncomeFromInterestBlock;
//    protected WebMarkupContainer cashIncomeFromInterestVContainer;
//    protected SingleChoiceProvider cashIncomeFromInterestProvider;
//    protected Option cashIncomeFromInterestValue;
//    protected Select2SingleChoice<Option> cashIncomeFromInterestView;
//    protected TextFeedbackPanel cashIncomeFromInterestFeedback;
//
//    protected WebMarkupBlock cashIncomeFromFeeBlock;
//    protected WebMarkupContainer cashIncomeFromFeeVContainer;
//    protected SingleChoiceProvider cashIncomeFromFeeProvider;
//    protected Option cashIncomeFromFeeValue;
//    protected Select2SingleChoice<Option> cashIncomeFromFeeView;
//    protected TextFeedbackPanel cashIncomeFromFeeFeedback;
//
//    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
//    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
//    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
//    protected Option cashIncomeFromPenaltyValue;
//    protected Select2SingleChoice<Option> cashIncomeFromPenaltyView;
//    protected TextFeedbackPanel cashIncomeFromPenaltyFeedback;
//
//    protected WebMarkupBlock cashIncomeFromRecoveryRepaymentBlock;
//    protected WebMarkupContainer cashIncomeFromRecoveryRepaymentVContainer;
//    protected SingleChoiceProvider cashIncomeFromRecoveryRepaymentProvider;
//    protected Option cashIncomeFromRecoveryRepaymentValue;
//    protected Select2SingleChoice<Option> cashIncomeFromRecoveryRepaymentView;
//    protected TextFeedbackPanel cashIncomeFromRecoveryRepaymentFeedback;
//
//    protected WebMarkupBlock cashLossWrittenOffBlock;
//    protected WebMarkupContainer cashLossWrittenOffVContainer;
//    protected SingleChoiceProvider cashLossWrittenOffProvider;
//    protected Option cashLossWrittenOffValue;
//    protected Select2SingleChoice<Option> cashLossWrittenOffView;
//    protected TextFeedbackPanel cashLossWrittenOffFeedback;
//
//    protected WebMarkupBlock cashOverPaymentLiabilityBlock;
//    protected WebMarkupContainer cashOverPaymentLiabilityVContainer;
//    protected SingleChoiceProvider cashOverPaymentLiabilityProvider;
//    protected Option cashOverPaymentLiabilityValue;
//    protected Select2SingleChoice<Option> cashOverPaymentLiabilityView;
//    protected TextFeedbackPanel cashOverPaymentLiabilityFeedback;
//
//    protected WebMarkupContainer periodicBlock;
//    protected WebMarkupContainer periodicVContainer;
//
//    protected WebMarkupBlock periodicFundSourceBlock;
//    protected WebMarkupContainer periodicFundSourceVContainer;
//    protected SingleChoiceProvider periodicFundSourceProvider;
//    protected Option periodicFundSourceValue;
//    protected Select2SingleChoice<Option> periodicFundSourceView;
//    protected TextFeedbackPanel periodicFundSourceFeedback;
//
//    protected WebMarkupBlock periodicLoanPortfolioBlock;
//    protected WebMarkupContainer periodicLoanPortfolioVContainer;
//    protected SingleChoiceProvider periodicLoanPortfolioProvider;
//    protected Option periodicLoanPortfolioValue;
//    protected Select2SingleChoice<Option> periodicLoanPortfolioView;
//    protected TextFeedbackPanel periodicLoanPortfolioFeedback;
//
//    protected WebMarkupBlock periodicInterestReceivableBlock;
//    protected WebMarkupContainer periodicInterestReceivableVContainer;
//    protected SingleChoiceProvider periodicInterestReceivableProvider;
//    protected Option periodicInterestReceivableValue;
//    protected Select2SingleChoice<Option> periodicInterestReceivableView;
//    protected TextFeedbackPanel periodicInterestReceivableFeedback;
//
//    protected WebMarkupBlock periodicFeeReceivableBlock;
//    protected WebMarkupContainer periodicFeeReceivableVContainer;
//    protected SingleChoiceProvider periodicFeeReceivableProvider;
//    protected Option periodicFeeReceivableValue;
//    protected Select2SingleChoice<Option> periodicFeeReceivableView;
//    protected TextFeedbackPanel periodicFeeReceivableFeedback;
//
//    protected WebMarkupBlock periodicPenaltyReceivableBlock;
//    protected WebMarkupContainer periodicPenaltyReceivableVContainer;
//    protected SingleChoiceProvider periodicPenaltyReceivableProvider;
//    protected Option periodicPenaltyReceivableValue;
//    protected Select2SingleChoice<Option> periodicPenaltyReceivableView;
//    protected TextFeedbackPanel periodicPenaltyReceivableFeedback;
//
//    protected WebMarkupBlock periodicTransferInSuspenseBlock;
//    protected WebMarkupContainer periodicTransferInSuspenseVContainer;
//    protected SingleChoiceProvider periodicTransferInSuspenseProvider;
//    protected Option periodicTransferInSuspenseValue;
//    protected Select2SingleChoice<Option> periodicTransferInSuspenseView;
//    protected TextFeedbackPanel periodicTransferInSuspenseFeedback;
//
//    protected WebMarkupBlock periodicIncomeFromInterestBlock;
//    protected WebMarkupContainer periodicIncomeFromInterestVContainer;
//    protected SingleChoiceProvider periodicIncomeFromInterestProvider;
//    protected Option periodicIncomeFromInterestValue;
//    protected Select2SingleChoice<Option> periodicIncomeFromInterestView;
//    protected TextFeedbackPanel periodicIncomeFromInterestFeedback;
//
//    protected WebMarkupBlock periodicIncomeFromFeeBlock;
//    protected WebMarkupContainer periodicIncomeFromFeeVContainer;
//    protected SingleChoiceProvider periodicIncomeFromFeeProvider;
//    protected Option periodicIncomeFromFeeValue;
//    protected Select2SingleChoice<Option> periodicIncomeFromFeeView;
//    protected TextFeedbackPanel periodicIncomeFromFeeFeedback;
//
//    protected WebMarkupBlock periodicIncomeFromPenaltyBlock;
//    protected WebMarkupContainer periodicIncomeFromPenaltyVContainer;
//    protected SingleChoiceProvider periodicIncomeFromPenaltyProvider;
//    protected Option periodicIncomeFromPenaltyValue;
//    protected Select2SingleChoice<Option> periodicIncomeFromPenaltyView;
//    protected TextFeedbackPanel periodicIncomeFromPenaltyFeedback;
//
//    protected WebMarkupBlock periodicIncomeFromRecoveryRepaymentBlock;
//    protected WebMarkupContainer periodicIncomeFromRecoveryRepaymentVContainer;
//    protected SingleChoiceProvider periodicIncomeFromRecoveryRepaymentProvider;
//    protected Option periodicIncomeFromRecoveryRepaymentValue;
//    protected Select2SingleChoice<Option> periodicIncomeFromRecoveryRepaymentView;
//    protected TextFeedbackPanel periodicIncomeFromRecoveryRepaymentFeedback;
//
//    protected WebMarkupBlock periodicLossWrittenOffBlock;
//    protected WebMarkupContainer periodicLossWrittenOffVContainer;
//    protected SingleChoiceProvider periodicLossWrittenOffProvider;
//    protected Option periodicLossWrittenOffValue;
//    protected Select2SingleChoice<Option> periodicLossWrittenOffView;
//    protected TextFeedbackPanel periodicLossWrittenOffFeedback;
//
//    protected WebMarkupBlock periodicOverPaymentLiabilityBlock;
//    protected WebMarkupContainer periodicOverPaymentLiabilityVContainer;
//    protected SingleChoiceProvider periodicOverPaymentLiabilityProvider;
//    protected Option periodicOverPaymentLiabilityValue;
//    protected Select2SingleChoice<Option> periodicOverPaymentLiabilityView;
//    protected TextFeedbackPanel periodicOverPaymentLiabilityFeedback;
//
//    protected WebMarkupContainer upfrontBlock;
//    protected WebMarkupContainer upfrontVContainer;
//
//    protected WebMarkupBlock upfrontFundSourceBlock;
//    protected WebMarkupContainer upfrontFundSourceVContainer;
//    protected SingleChoiceProvider upfrontFundSourceProvider;
//    protected Option upfrontFundSourceValue;
//    protected Select2SingleChoice<Option> upfrontFundSourceView;
//    protected TextFeedbackPanel upfrontFundSourceFeedback;
//
//    protected WebMarkupBlock upfrontLoanPortfolioBlock;
//    protected WebMarkupContainer upfrontLoanPortfolioVContainer;
//    protected SingleChoiceProvider upfrontLoanPortfolioProvider;
//    protected Option upfrontLoanPortfolioValue;
//    protected Select2SingleChoice<Option> upfrontLoanPortfolioView;
//    protected TextFeedbackPanel upfrontLoanPortfolioFeedback;
//
//    protected WebMarkupBlock upfrontInterestReceivableBlock;
//    protected WebMarkupContainer upfrontInterestReceivableVContainer;
//    protected SingleChoiceProvider upfrontInterestReceivableProvider;
//    protected Option upfrontInterestReceivableValue;
//    protected Select2SingleChoice<Option> upfrontInterestReceivableView;
//    protected TextFeedbackPanel upfrontInterestReceivableFeedback;
//
//    protected WebMarkupBlock upfrontFeeReceivableBlock;
//    protected WebMarkupContainer upfrontFeeReceivableVContainer;
//    protected SingleChoiceProvider upfrontFeeReceivableProvider;
//    protected Option upfrontFeeReceivableValue;
//    protected Select2SingleChoice<Option> upfrontFeeReceivableView;
//    protected TextFeedbackPanel upfrontFeeReceivableFeedback;
//
//    protected WebMarkupBlock upfrontPenaltyReceivableBlock;
//    protected WebMarkupContainer upfrontPenaltyReceivableVContainer;
//    protected SingleChoiceProvider upfrontPenaltyReceivableProvider;
//    protected Option upfrontPenaltyReceivableValue;
//    protected Select2SingleChoice<Option> upfrontPenaltyReceivableView;
//    protected TextFeedbackPanel upfrontPenaltyReceivableFeedback;
//
//    protected WebMarkupBlock upfrontTransferInSuspenseBlock;
//    protected WebMarkupContainer upfrontTransferInSuspenseVContainer;
//    protected SingleChoiceProvider upfrontTransferInSuspenseProvider;
//    protected Option upfrontTransferInSuspenseValue;
//    protected Select2SingleChoice<Option> upfrontTransferInSuspenseView;
//    protected TextFeedbackPanel upfrontTransferInSuspenseFeedback;
//
//    protected WebMarkupBlock upfrontIncomeFromInterestBlock;
//    protected WebMarkupContainer upfrontIncomeFromInterestVContainer;
//    protected SingleChoiceProvider upfrontIncomeFromInterestProvider;
//    protected Option upfrontIncomeFromInterestValue;
//    protected Select2SingleChoice<Option> upfrontIncomeFromInterestView;
//    protected TextFeedbackPanel upfrontIncomeFromInterestFeedback;
//
//    protected WebMarkupBlock upfrontIncomeFromFeeBlock;
//    protected WebMarkupContainer upfrontIncomeFromFeeVContainer;
//    protected SingleChoiceProvider upfrontIncomeFromFeeProvider;
//    protected Option upfrontIncomeFromFeeValue;
//    protected Select2SingleChoice<Option> upfrontIncomeFromFeeView;
//    protected TextFeedbackPanel upfrontIncomeFromFeeFeedback;
//
//    protected WebMarkupBlock upfrontIncomeFromPenaltyBlock;
//    protected WebMarkupContainer upfrontIncomeFromPenaltyVContainer;
//    protected SingleChoiceProvider upfrontIncomeFromPenaltyProvider;
//    protected Option upfrontIncomeFromPenaltyValue;
//    protected Select2SingleChoice<Option> upfrontIncomeFromPenaltyView;
//    protected TextFeedbackPanel upfrontIncomeFromPenaltyFeedback;
//
//    protected WebMarkupBlock upfrontIncomeFromRecoveryRepaymentBlock;
//    protected WebMarkupContainer upfrontIncomeFromRecoveryRepaymentVContainer;
//    protected SingleChoiceProvider upfrontIncomeFromRecoveryRepaymentProvider;
//    protected Option upfrontIncomeFromRecoveryRepaymentValue;
//    protected Select2SingleChoice<Option> upfrontIncomeFromRecoveryRepaymentView;
//    protected TextFeedbackPanel upfrontIncomeFromRecoveryRepaymentFeedback;
//
//    protected WebMarkupBlock upfrontLossWrittenOffBlock;
//    protected WebMarkupContainer upfrontLossWrittenOffVContainer;
//    protected SingleChoiceProvider upfrontLossWrittenOffProvider;
//    protected Option upfrontLossWrittenOffValue;
//    protected Select2SingleChoice<Option> upfrontLossWrittenOffView;
//    protected TextFeedbackPanel upfrontLossWrittenOffFeedback;
//
//    protected WebMarkupBlock upfrontOverPaymentLiabilityBlock;
//    protected WebMarkupContainer upfrontOverPaymentLiabilityVContainer;
//    protected SingleChoiceProvider upfrontOverPaymentLiabilityProvider;
//    protected Option upfrontOverPaymentLiabilityValue;
//    protected Select2SingleChoice<Option> upfrontOverPaymentLiabilityView;
//    protected TextFeedbackPanel upfrontOverPaymentLiabilityFeedback;
//
//    // Advanced Accounting Rule
//
//    protected WebMarkupContainer advancedAccountingRuleBlock;
//    protected WebMarkupContainer advancedAccountingRuleVContainer;
//
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
//    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
//    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
//    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
//    protected ModalWindow fundSourcePopup;
//
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
//    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
//    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
//    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
//    protected ModalWindow feeIncomePopup;
//
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
//    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
//    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
//    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
//    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
//    protected ModalWindow penaltyIncomePopup;
//
//    // Charges
//
//    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
//    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> chargeTable;
//    protected ListDataProvider chargeProvider;
//    protected ModalWindow chargePopup;
//
//    // Overdue Charges
//
//    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
//    protected List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> overdueChargeTable;
//    protected ListDataProvider overdueChargeProvider;
//    protected ModalWindow overdueChargePopup;
//
//    protected Map<String, Object> popupModel;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Product");
//            breadcrumb.setPage(ProductDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Loan Product");
//            breadcrumb.setPage(LoanBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Loan Product Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//
//        this.editLink = new BookmarkablePageLink<>("editLink", LoanBrowsePage.class);
//        add(this.editLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
//        add(this.closeLink);
//
//        initSectionDetail();
//
//        initSectionCurrency();
//
//        initSectionTerms();
//
//        initSectionSetting();
//
//        initSectionInterestRecalculation();
//
//        initSectionGuaranteeRequirements();
//
//        initSectionLoanTrancheDetails();
//
//        initSectionConfigurableTermsAndSettings();
//
//        initSectionCharge();
//
//        initSectionOverdueCharge();
//
//        initSectionAccounting();
//
//        initSectionDefault();
//
//        initSectionValidationRule();
//    }
//
//    @Override
//    protected void configureRequiredValidation() {
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    protected void initSectionOverdueCharge() {
//        this.overdueChargePopup = new ModalWindow("overdueChargePopup");
//        add(this.overdueChargePopup);
//        this.overdueChargePopup.setOnClose(this::overdueChargePopupClose);
//
//        this.overdueChargeColumn = Lists.newArrayList();
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeColumn));
//        this.overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::overdueChargeColumn));
//        this.overdueChargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::overdueChargeAction, this::overdueChargeClick));
//        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
//        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, 20);
//        add(this.overdueChargeTable);
//        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
//        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));
//
//        AjaxLink<Void> overdueChargeAddLink = new AjaxLink<>("overdueChargeAddLink");
//        overdueChargeAddLink.setOnClick(this::overdueChargeAddLinkClick);
//        add(overdueChargeAddLink);
//    }
//
//    protected boolean overdueChargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        if (this.currencyCodeValue != null) {
//            this.overdueChargePopup.setContent(new com.angkorteam.fintech.popup.OverdueChargePopup("overdueCharge", this.overdueChargePopup, ProductPopup.Loan, this.popupModel, this.currencyCodeValue.getId()));
//            this.overdueChargePopup.show(target);
//        } else {
//            this.overdueChargePopup.setContent(new CurrencyPopup("currency", this.overdueChargePopup));
//            this.overdueChargePopup.show(target);
//        }
//        return false;
//    }
//
//    protected ItemPanel overdueChargeColumn(String column, IModel<String> display, Map<String, Object> model) {
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
//    protected void overdueChargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.overdueChargeValue.size(); i++) {
//            Map<String, Object> column = this.overdueChargeValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.overdueChargeValue.remove(index);
//        }
//        target.add(this.overdueChargeTable);
//    }
//
//    protected List<ActionItem> overdueChargeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected void initSectionCharge() {
//
//        this.chargePopup = new ModalWindow("chargePopup");
//        add(this.chargePopup);
//        this.chargePopup.setOnClose(this::chargePopupClose);
//
//        this.chargeColumn = Lists.newArrayList();
//        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
//        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
//        this.chargeProvider = new ListDataProvider(this.chargeValue);
//        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
//        add(this.chargeTable);
//        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
//        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
//
//        AjaxLink<Void> chargeAddLink = new AjaxLink<>("chargeAddLink");
//        chargeAddLink.setOnClick(this::chargeAddLinkClick);
//        add(chargeAddLink);
//    }
//
//    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        if (this.currencyCodeValue != null) {
//            this.chargePopup.setContent(new ChargePopup("charge", this.chargePopup, ProductPopup.Loan, this.popupModel, this.currencyCodeValue.getId()));
//            this.chargePopup.show(target);
//        } else {
//            this.chargePopup.setContent(new CurrencyPopup("currency", this.chargePopup));
//            this.chargePopup.show(target);
//        }
//        return false;
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
//    protected void chargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.chargeValue.size(); i++) {
//            Map<String, Object> column = this.chargeValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.chargeValue.remove(index);
//        }
//        target.add(this.chargeTable);
//    }
//
//    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected void initSectionAccounting() {
//        this.accountingView = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
//        this.accountingView.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
//        this.accountingView.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
//        this.accountingView.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
//        this.accountingView.add(new Radio<>("accountingPeriodic", new Model<>(AccountingType.Periodic.getDescription())));
//        this.accountingView.add(new Radio<>("accountingUpfront", new Model<>(AccountingType.Upfront.getDescription())));
//        add(this.accountingView);
//
//        initAccountingCash();
//
//        initAccountingPeriodic();
//
//        initAccountingUpFront();
//
//        initAdvancedAccountingRule();
//
//    }
//
//    protected void initAccountingUpFront() {
//        this.upfrontBlock = new WebMarkupContainer("upfrontBlock");
//        this.upfrontBlock.setOutputMarkupId(true);
//        add(this.upfrontBlock);
//
//        this.upfrontVContainer = new WebMarkupContainer("upfrontVContainer");
//        this.upfrontBlock.add(this.upfrontVContainer);
//
//        this.upfrontFundSourceBlock = new WebMarkupBlock("upfrontFundSourceBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontFundSourceBlock);
//        this.upfrontFundSourceVContainer = new WebMarkupContainer("upfrontFundSourceVContainer");
//        this.upfrontFundSourceBlock.add(this.upfrontFundSourceVContainer);
//        this.upfrontFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontFundSourceView = new Select2SingleChoice<>("upfrontFundSourceField", new PropertyModel<>(this, "upfrontFundSourceValue"), this.upfrontFundSourceProvider);
//        this.upfrontFundSourceView.setLabel(Model.of("Fund source"));
//        this.upfrontFundSourceView.add(new OnChangeAjaxBehavior());
//        this.upfrontFundSourceVContainer.add(this.upfrontFundSourceView);
//        this.upfrontFundSourceFeedback = new TextFeedbackPanel("upfrontFundSourceFeedback", this.upfrontFundSourceView);
//        this.upfrontFundSourceVContainer.add(this.upfrontFundSourceFeedback);
//
//        this.upfrontLoanPortfolioBlock = new WebMarkupBlock("upfrontLoanPortfolioBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontLoanPortfolioBlock);
//        this.upfrontLoanPortfolioVContainer = new WebMarkupContainer("upfrontLoanPortfolioVContainer");
//        this.upfrontLoanPortfolioBlock.add(this.upfrontLoanPortfolioVContainer);
//        this.upfrontLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontLoanPortfolioView = new Select2SingleChoice<>("upfrontLoanPortfolioField", new PropertyModel<>(this, "upfrontLoanPortfolioValue"), this.upfrontLoanPortfolioProvider);
//        this.upfrontLoanPortfolioView.setLabel(Model.of("Loan portfolio"));
//        this.upfrontLoanPortfolioView.add(new OnChangeAjaxBehavior());
//        this.upfrontLoanPortfolioVContainer.add(this.upfrontLoanPortfolioView);
//        this.upfrontLoanPortfolioFeedback = new TextFeedbackPanel("upfrontLoanPortfolioFeedback", this.upfrontLoanPortfolioView);
//        this.upfrontLoanPortfolioVContainer.add(this.upfrontLoanPortfolioFeedback);
//
//        this.upfrontInterestReceivableBlock = new WebMarkupBlock("upfrontInterestReceivableBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontInterestReceivableBlock);
//        this.upfrontInterestReceivableVContainer = new WebMarkupContainer("upfrontInterestReceivableVContainer");
//        this.upfrontInterestReceivableBlock.add(this.upfrontInterestReceivableVContainer);
//        this.upfrontInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontInterestReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontInterestReceivableView = new Select2SingleChoice<>("upfrontInterestReceivableField", new PropertyModel<>(this, "upfrontInterestReceivableValue"), this.upfrontInterestReceivableProvider);
//        this.upfrontInterestReceivableView.setLabel(Model.of("Interest Receivable"));
//        this.upfrontInterestReceivableView.add(new OnChangeAjaxBehavior());
//        this.upfrontInterestReceivableVContainer.add(this.upfrontInterestReceivableView);
//        this.upfrontInterestReceivableFeedback = new TextFeedbackPanel("upfrontInterestReceivableFeedback", this.upfrontInterestReceivableView);
//        this.upfrontInterestReceivableVContainer.add(this.upfrontInterestReceivableFeedback);
//
//        this.upfrontFeeReceivableBlock = new WebMarkupBlock("upfrontFeeReceivableBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontFeeReceivableBlock);
//        this.upfrontFeeReceivableVContainer = new WebMarkupContainer("upfrontFeeReceivableVContainer");
//        this.upfrontFeeReceivableBlock.add(this.upfrontFeeReceivableVContainer);
//        this.upfrontFeeReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontFeeReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontFeeReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontFeeReceivableView = new Select2SingleChoice<>("upfrontFeeReceivableField", new PropertyModel<>(this, "upfrontFeeReceivableValue"), this.upfrontFeeReceivableProvider);
//        this.upfrontFeeReceivableView.setLabel(Model.of("Fee Receivable"));
//        this.upfrontFeeReceivableView.add(new OnChangeAjaxBehavior());
//        this.upfrontFeeReceivableVContainer.add(this.upfrontFeeReceivableView);
//        this.upfrontFeeReceivableFeedback = new TextFeedbackPanel("upfrontFeeReceivableFeedback", this.upfrontFeeReceivableView);
//        this.upfrontFeeReceivableVContainer.add(this.upfrontFeeReceivableFeedback);
//
//        this.upfrontPenaltyReceivableBlock = new WebMarkupBlock("upfrontPenaltyReceivableBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontPenaltyReceivableBlock);
//        this.upfrontPenaltyReceivableVContainer = new WebMarkupContainer("upfrontPenaltyReceivableVContainer");
//        this.upfrontPenaltyReceivableBlock.add(this.upfrontPenaltyReceivableVContainer);
//        this.upfrontPenaltyReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontPenaltyReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontPenaltyReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontPenaltyReceivableView = new Select2SingleChoice<>("upfrontPenaltyReceivableField", new PropertyModel<>(this, "upfrontPenaltyReceivableValue"), this.upfrontPenaltyReceivableProvider);
//        this.upfrontPenaltyReceivableView.setLabel(Model.of("Penalty Receivable"));
//        this.upfrontPenaltyReceivableView.add(new OnChangeAjaxBehavior());
//        this.upfrontPenaltyReceivableVContainer.add(this.upfrontPenaltyReceivableView);
//        this.upfrontPenaltyReceivableFeedback = new TextFeedbackPanel("upfrontPenaltyReceivableFeedback", this.upfrontPenaltyReceivableView);
//        this.upfrontPenaltyReceivableVContainer.add(this.upfrontPenaltyReceivableFeedback);
//
//        this.upfrontTransferInSuspenseBlock = new WebMarkupBlock("upfrontTransferInSuspenseBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontTransferInSuspenseBlock);
//        this.upfrontTransferInSuspenseVContainer = new WebMarkupContainer("upfrontTransferInSuspenseVContainer");
//        this.upfrontTransferInSuspenseBlock.add(this.upfrontTransferInSuspenseVContainer);
//        this.upfrontTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.upfrontTransferInSuspenseView = new Select2SingleChoice<>("upfrontTransferInSuspenseField", new PropertyModel<>(this, "upfrontTransferInSuspenseValue"), this.upfrontTransferInSuspenseProvider);
//        this.upfrontTransferInSuspenseView.setLabel(Model.of("Transfer in suspense"));
//        this.upfrontTransferInSuspenseView.add(new OnChangeAjaxBehavior());
//        this.upfrontTransferInSuspenseVContainer.add(this.upfrontTransferInSuspenseView);
//        this.upfrontTransferInSuspenseFeedback = new TextFeedbackPanel("upfrontTransferInSuspenseFeedback", this.upfrontTransferInSuspenseView);
//        this.upfrontTransferInSuspenseVContainer.add(this.upfrontTransferInSuspenseFeedback);
//
//        this.upfrontIncomeFromInterestBlock = new WebMarkupBlock("upfrontIncomeFromInterestBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontIncomeFromInterestBlock);
//        this.upfrontIncomeFromInterestVContainer = new WebMarkupContainer("upfrontIncomeFromInterestVContainer");
//        this.upfrontIncomeFromInterestBlock.add(this.upfrontIncomeFromInterestVContainer);
//        this.upfrontIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.upfrontIncomeFromInterestView = new Select2SingleChoice<>("upfrontIncomeFromInterestField", new PropertyModel<>(this, "upfrontIncomeFromInterestValue"), this.upfrontIncomeFromInterestProvider);
//        this.upfrontIncomeFromInterestView.setLabel(Model.of("Income from Interest"));
//        this.upfrontIncomeFromInterestView.add(new OnChangeAjaxBehavior());
//        this.upfrontIncomeFromInterestVContainer.add(this.upfrontIncomeFromInterestView);
//        this.upfrontIncomeFromInterestFeedback = new TextFeedbackPanel("upfrontIncomeFromInterestFeedback", this.upfrontIncomeFromInterestView);
//        this.upfrontIncomeFromInterestVContainer.add(this.upfrontIncomeFromInterestFeedback);
//
//        this.upfrontIncomeFromFeeBlock = new WebMarkupBlock("upfrontIncomeFromFeeBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontIncomeFromFeeBlock);
//        this.upfrontIncomeFromFeeVContainer = new WebMarkupContainer("upfrontIncomeFromFeeVContainer");
//        this.upfrontIncomeFromFeeBlock.add(this.upfrontIncomeFromFeeVContainer);
//        this.upfrontIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.upfrontIncomeFromFeeView = new Select2SingleChoice<>("upfrontIncomeFromFeeField", new PropertyModel<>(this, "upfrontIncomeFromFeeValue"), this.upfrontIncomeFromFeeProvider);
//        this.upfrontIncomeFromFeeView.setLabel(Model.of("Income from fees"));
//        this.upfrontIncomeFromFeeView.add(new OnChangeAjaxBehavior());
//        this.upfrontIncomeFromFeeVContainer.add(this.upfrontIncomeFromFeeView);
//        this.upfrontIncomeFromFeeFeedback = new TextFeedbackPanel("upfrontIncomeFromFeeFeedback", this.upfrontIncomeFromFeeView);
//        this.upfrontIncomeFromFeeVContainer.add(this.upfrontIncomeFromFeeFeedback);
//
//        this.upfrontIncomeFromPenaltyBlock = new WebMarkupBlock("upfrontIncomeFromPenaltyBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontIncomeFromPenaltyBlock);
//        this.upfrontIncomeFromPenaltyVContainer = new WebMarkupContainer("upfrontIncomeFromPenaltyVContainer");
//        this.upfrontIncomeFromPenaltyBlock.add(this.upfrontIncomeFromPenaltyVContainer);
//        this.upfrontIncomeFromPenaltyProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontIncomeFromPenaltyProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontIncomeFromPenaltyProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.upfrontIncomeFromPenaltyView = new Select2SingleChoice<>("upfrontIncomeFromPenaltyField", new PropertyModel<>(this, "upfrontIncomeFromPenaltyValue"), this.upfrontIncomeFromPenaltyProvider);
//        this.upfrontIncomeFromPenaltyView.setLabel(Model.of("Income from Penalty"));
//        this.upfrontIncomeFromPenaltyView.add(new OnChangeAjaxBehavior());
//        this.upfrontIncomeFromPenaltyVContainer.add(this.upfrontIncomeFromPenaltyView);
//        this.upfrontIncomeFromPenaltyFeedback = new TextFeedbackPanel("upfrontIncomeFromPenaltyFeedback", this.upfrontIncomeFromPenaltyView);
//        this.upfrontIncomeFromPenaltyVContainer.add(this.upfrontIncomeFromPenaltyFeedback);
//
//        this.upfrontIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("upfrontIncomeFromRecoveryRepaymentBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontIncomeFromRecoveryRepaymentBlock);
//        this.upfrontIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("upfrontIncomeFromRecoveryRepaymentVContainer");
//        this.upfrontIncomeFromRecoveryRepaymentBlock.add(this.upfrontIncomeFromRecoveryRepaymentVContainer);
//        this.upfrontIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.upfrontIncomeFromRecoveryRepaymentView = new Select2SingleChoice<>("upfrontIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentValue"), this.upfrontIncomeFromRecoveryRepaymentProvider);
//        this.upfrontIncomeFromRecoveryRepaymentView.setLabel(Model.of("Income from Recovery Repayments"));
//        this.upfrontIncomeFromRecoveryRepaymentView.add(new OnChangeAjaxBehavior());
//        this.upfrontIncomeFromRecoveryRepaymentVContainer.add(this.upfrontIncomeFromRecoveryRepaymentView);
//        this.upfrontIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("upfrontIncomeFromRecoveryRepaymentFeedback", this.upfrontIncomeFromRecoveryRepaymentView);
//        this.upfrontIncomeFromRecoveryRepaymentVContainer.add(this.upfrontIncomeFromRecoveryRepaymentFeedback);
//
//        this.upfrontLossWrittenOffBlock = new WebMarkupBlock("upfrontLossWrittenOffBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontLossWrittenOffBlock);
//        this.upfrontLossWrittenOffVContainer = new WebMarkupContainer("upfrontLossWrittenOffVContainer");
//        this.upfrontLossWrittenOffBlock.add(this.upfrontLossWrittenOffVContainer);
//        this.upfrontLossWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontLossWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontLossWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
//        this.upfrontLossWrittenOffView = new Select2SingleChoice<>("upfrontLossWrittenOffField", new PropertyModel<>(this, "upfrontLossWrittenOffValue"), this.upfrontLossWrittenOffProvider);
//        this.upfrontLossWrittenOffView.setLabel(Model.of("Loss written off"));
//        this.upfrontLossWrittenOffView.add(new OnChangeAjaxBehavior());
//        this.upfrontLossWrittenOffVContainer.add(this.upfrontLossWrittenOffView);
//        this.upfrontLossWrittenOffFeedback = new TextFeedbackPanel("upfrontLossWrittenOffFeedback", this.upfrontLossWrittenOffView);
//        this.upfrontLossWrittenOffVContainer.add(this.upfrontLossWrittenOffFeedback);
//
//        this.upfrontOverPaymentLiabilityBlock = new WebMarkupBlock("upfrontOverPaymentLiabilityBlock", Size.Six_6);
//        this.upfrontVContainer.add(this.upfrontOverPaymentLiabilityBlock);
//        this.upfrontOverPaymentLiabilityVContainer = new WebMarkupContainer("upfrontOverPaymentLiabilityVContainer");
//        this.upfrontOverPaymentLiabilityBlock.add(this.upfrontOverPaymentLiabilityVContainer);
//        this.upfrontOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.upfrontOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.upfrontOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
//        this.upfrontOverPaymentLiabilityView = new Select2SingleChoice<>("upfrontOverPaymentLiabilityField", new PropertyModel<>(this, "upfrontOverPaymentLiabilityValue"), this.upfrontOverPaymentLiabilityProvider);
//        this.upfrontOverPaymentLiabilityView.setLabel(Model.of("Over payment liability"));
//        this.upfrontOverPaymentLiabilityView.add(new OnChangeAjaxBehavior());
//        this.upfrontOverPaymentLiabilityVContainer.add(this.upfrontOverPaymentLiabilityView);
//        this.upfrontOverPaymentLiabilityFeedback = new TextFeedbackPanel("upfrontOverPaymentLiabilityFeedback", this.upfrontOverPaymentLiabilityView);
//        this.upfrontOverPaymentLiabilityVContainer.add(this.upfrontOverPaymentLiabilityFeedback);
//    }
//
//    protected void initAccountingCash() {
//
//        this.cashBlock = new WebMarkupContainer("cashBlock");
//        this.cashBlock.setOutputMarkupId(true);
//        add(this.cashBlock);
//
//        this.cashVContainer = new WebMarkupContainer("cashVContainer");
//        this.cashBlock.add(this.cashVContainer);
//
//        this.cashFundSourceBlock = new WebMarkupBlock("cashFundSourceBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashFundSourceBlock);
//        this.cashFundSourceVContainer = new WebMarkupContainer("cashFundSourceVContainer");
//        this.cashFundSourceBlock.add(this.cashFundSourceVContainer);
//        this.cashFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.cashFundSourceView = new Select2SingleChoice<>("cashFundSourceField", new PropertyModel<>(this, "cashFundSourceValue"), this.cashFundSourceProvider);
//        this.cashFundSourceView.setLabel(Model.of("Fund Source"));
//        this.cashFundSourceView.add(new OnChangeAjaxBehavior());
//        this.cashFundSourceVContainer.add(this.cashFundSourceView);
//        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceView);
//        this.cashFundSourceVContainer.add(this.cashFundSourceFeedback);
//
//        this.cashLoanPortfolioBlock = new WebMarkupBlock("cashLoanPortfolioBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashLoanPortfolioBlock);
//        this.cashLoanPortfolioVContainer = new WebMarkupContainer("cashLoanPortfolioVContainer");
//        this.cashLoanPortfolioBlock.add(this.cashLoanPortfolioVContainer);
//        this.cashLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.cashLoanPortfolioView = new Select2SingleChoice<>("cashLoanPortfolioField", new PropertyModel<>(this, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
//        this.cashLoanPortfolioView.setLabel(Model.of("Loan portfolio"));
//        this.cashLoanPortfolioView.add(new OnChangeAjaxBehavior());
//        this.cashLoanPortfolioVContainer.add(this.cashLoanPortfolioView);
//        this.cashLoanPortfolioFeedback = new TextFeedbackPanel("cashLoanPortfolioFeedback", this.cashLoanPortfolioView);
//        this.cashLoanPortfolioVContainer.add(this.cashLoanPortfolioFeedback);
//
//        this.cashTransferInSuspenseBlock = new WebMarkupBlock("cashTransferInSuspenseBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashTransferInSuspenseBlock);
//        this.cashTransferInSuspenseVContainer = new WebMarkupContainer("cashTransferInSuspenseVContainer");
//        this.cashTransferInSuspenseBlock.add(this.cashTransferInSuspenseVContainer);
//        this.cashTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.cashTransferInSuspenseView = new Select2SingleChoice<>("cashTransferInSuspenseField", new PropertyModel<>(this, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
//        this.cashTransferInSuspenseView.setLabel(Model.of("Transfer in suspense"));
//        this.cashTransferInSuspenseView.add(new OnChangeAjaxBehavior());
//        this.cashTransferInSuspenseVContainer.add(this.cashTransferInSuspenseView);
//        this.cashTransferInSuspenseFeedback = new TextFeedbackPanel("cashTransferInSuspenseFeedback", this.cashTransferInSuspenseView);
//        this.cashTransferInSuspenseVContainer.add(this.cashTransferInSuspenseFeedback);
//
//        this.cashIncomeFromInterestBlock = new WebMarkupBlock("cashIncomeFromInterestBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashIncomeFromInterestBlock);
//        this.cashIncomeFromInterestVContainer = new WebMarkupContainer("cashIncomeFromInterestVContainer");
//        this.cashIncomeFromInterestBlock.add(this.cashIncomeFromInterestVContainer);
//        this.cashIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.cashIncomeFromInterestView = new Select2SingleChoice<>("cashIncomeFromInterestField", new PropertyModel<>(this, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
//        this.cashIncomeFromInterestView.setLabel(Model.of("Income from Interest"));
//        this.cashIncomeFromInterestView.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromInterestVContainer.add(this.cashIncomeFromInterestView);
//        this.cashIncomeFromInterestFeedback = new TextFeedbackPanel("cashIncomeFromInterestFeedback", this.cashIncomeFromInterestView);
//        this.cashIncomeFromInterestVContainer.add(this.cashIncomeFromInterestFeedback);
//
//        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashIncomeFromFeeBlock);
//        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
//        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
//        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.cashIncomeFromFeeView = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
//        this.cashIncomeFromFeeView.setLabel(Model.of("Income from fees"));
//        this.cashIncomeFromFeeView.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
//        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeView);
//        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeFeedback);
//
//        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashIncomeFromPenaltyBlock);
//        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
//        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
//        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.cashIncomeFromPenaltyView = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
//        this.cashIncomeFromPenaltyView.setLabel(Model.of("Income from Penalty"));
//        this.cashIncomeFromPenaltyView.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);
//        this.cashIncomeFromPenaltyFeedback = new TextFeedbackPanel("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyView);
//        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyFeedback);
//
//        this.cashIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashIncomeFromRecoveryRepaymentBlock);
//        this.cashIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("cashIncomeFromRecoveryRepaymentVContainer");
//        this.cashIncomeFromRecoveryRepaymentBlock.add(this.cashIncomeFromRecoveryRepaymentVContainer);
//        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.cashIncomeFromRecoveryRepaymentView = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "cashIncomeFromRecoveryRepaymentValue"), this.cashIncomeFromRecoveryRepaymentProvider);
//        this.cashIncomeFromRecoveryRepaymentView.setLabel(Model.of("Income from Recovery Repayments"));
//        this.cashIncomeFromRecoveryRepaymentView.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromRecoveryRepaymentVContainer.add(this.cashIncomeFromRecoveryRepaymentView);
//        this.cashIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryRepaymentFeedback", this.cashIncomeFromRecoveryRepaymentView);
//        this.cashIncomeFromRecoveryRepaymentVContainer.add(this.cashIncomeFromRecoveryRepaymentFeedback);
//
//        this.cashLossWrittenOffBlock = new WebMarkupBlock("cashLossWrittenOffBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashLossWrittenOffBlock);
//        this.cashLossWrittenOffVContainer = new WebMarkupContainer("cashLossWrittenOffVContainer");
//        this.cashLossWrittenOffBlock.add(this.cashLossWrittenOffVContainer);
//        this.cashLossWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashLossWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashLossWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
//        this.cashLossWrittenOffView = new Select2SingleChoice<>("cashLossWrittenOffField", new PropertyModel<>(this, "cashLossWrittenOffValue"), this.cashLossWrittenOffProvider);
//        this.cashLossWrittenOffView.setLabel(Model.of("Loss written off"));
//        this.cashLossWrittenOffView.add(new OnChangeAjaxBehavior());
//        this.cashLossWrittenOffVContainer.add(this.cashLossWrittenOffView);
//        this.cashLossWrittenOffFeedback = new TextFeedbackPanel("cashLossWrittenOffFeedback", this.cashLossWrittenOffView);
//        this.cashLossWrittenOffVContainer.add(this.cashLossWrittenOffFeedback);
//
//        this.cashOverPaymentLiabilityBlock = new WebMarkupBlock("cashOverPaymentLiabilityBlock", Size.Six_6);
//        this.cashVContainer.add(this.cashOverPaymentLiabilityBlock);
//        this.cashOverPaymentLiabilityVContainer = new WebMarkupContainer("cashOverPaymentLiabilityVContainer");
//        this.cashOverPaymentLiabilityBlock.add(this.cashOverPaymentLiabilityVContainer);
//        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
//        this.cashOverPaymentLiabilityView = new Select2SingleChoice<>("cashOverPaymentLiabilityField", new PropertyModel<>(this, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
//        this.cashOverPaymentLiabilityView.setLabel(Model.of("Over payment liability"));
//        this.cashOverPaymentLiabilityView.add(new OnChangeAjaxBehavior());
//        this.cashOverPaymentLiabilityVContainer.add(this.cashOverPaymentLiabilityView);
//        this.cashOverPaymentLiabilityFeedback = new TextFeedbackPanel("cashOverPaymentLiabilityFeedback", this.cashOverPaymentLiabilityView);
//        this.cashOverPaymentLiabilityVContainer.add(this.cashOverPaymentLiabilityFeedback);
//    }
//
//    protected void initAccountingPeriodic() {
//        this.periodicBlock = new WebMarkupContainer("periodicBlock");
//        this.periodicBlock.setOutputMarkupId(true);
//        add(this.periodicBlock);
//
//        this.periodicVContainer = new WebMarkupContainer("periodicVContainer");
//        this.periodicBlock.add(this.periodicVContainer);
//
//        this.periodicFundSourceBlock = new WebMarkupBlock("periodicFundSourceBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicFundSourceBlock);
//        this.periodicFundSourceVContainer = new WebMarkupContainer("periodicFundSourceVContainer");
//        this.periodicFundSourceBlock.add(this.periodicFundSourceVContainer);
//        this.periodicFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicFundSourceView = new Select2SingleChoice<>("periodicFundSourceField", new PropertyModel<>(this, "periodicFundSourceValue"), this.periodicFundSourceProvider);
//        this.periodicFundSourceView.setLabel(Model.of("Fund source"));
//        this.periodicFundSourceView.add(new OnChangeAjaxBehavior());
//        this.periodicFundSourceVContainer.add(this.periodicFundSourceView);
//        this.periodicFundSourceFeedback = new TextFeedbackPanel("periodicFundSourceFeedback", this.periodicFundSourceView);
//        this.periodicFundSourceVContainer.add(this.periodicFundSourceFeedback);
//
//        this.periodicLoanPortfolioBlock = new WebMarkupBlock("periodicLoanPortfolioBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicLoanPortfolioBlock);
//        this.periodicLoanPortfolioVContainer = new WebMarkupContainer("periodicLoanPortfolioVContainer");
//        this.periodicLoanPortfolioBlock.add(this.periodicLoanPortfolioVContainer);
//        this.periodicLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicLoanPortfolioView = new Select2SingleChoice<>("periodicLoanPortfolioField", new PropertyModel<>(this, "periodicLoanPortfolioValue"), this.periodicLoanPortfolioProvider);
//        this.periodicLoanPortfolioView.setLabel(Model.of("Loan portfolio"));
//        this.periodicLoanPortfolioView.add(new OnChangeAjaxBehavior());
//        this.periodicLoanPortfolioVContainer.add(this.periodicLoanPortfolioView);
//        this.periodicLoanPortfolioFeedback = new TextFeedbackPanel("periodicLoanPortfolioFeedback", this.periodicLoanPortfolioView);
//        this.periodicLoanPortfolioVContainer.add(this.periodicLoanPortfolioFeedback);
//
//        this.periodicInterestReceivableBlock = new WebMarkupBlock("periodicInterestReceivableBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicInterestReceivableBlock);
//        this.periodicInterestReceivableVContainer = new WebMarkupContainer("periodicInterestReceivableVContainer");
//        this.periodicInterestReceivableBlock.add(this.periodicInterestReceivableVContainer);
//        this.periodicInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicInterestReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicInterestReceivableView = new Select2SingleChoice<>("periodicInterestReceivableField", new PropertyModel<>(this, "periodicInterestReceivableValue"), this.periodicInterestReceivableProvider);
//        this.periodicInterestReceivableView.setLabel(Model.of("Interest Receivable"));
//        this.periodicInterestReceivableView.add(new OnChangeAjaxBehavior());
//        this.periodicInterestReceivableVContainer.add(this.periodicInterestReceivableView);
//        this.periodicInterestReceivableFeedback = new TextFeedbackPanel("periodicInterestReceivableFeedback", this.periodicInterestReceivableView);
//        this.periodicInterestReceivableVContainer.add(this.periodicInterestReceivableFeedback);
//
//        this.periodicFeeReceivableBlock = new WebMarkupBlock("periodicFeeReceivableBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicFeeReceivableBlock);
//        this.periodicFeeReceivableVContainer = new WebMarkupContainer("periodicFeeReceivableVContainer");
//        this.periodicFeeReceivableBlock.add(this.periodicFeeReceivableVContainer);
//        this.periodicFeeReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicFeeReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicFeeReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicFeeReceivableView = new Select2SingleChoice<>("periodicFeeReceivableField", new PropertyModel<>(this, "periodicFeeReceivableValue"), this.periodicFeeReceivableProvider);
//        this.periodicFeeReceivableView.setLabel(Model.of("Fee Receivable"));
//        this.periodicFeeReceivableView.add(new OnChangeAjaxBehavior());
//        this.periodicFeeReceivableVContainer.add(this.periodicFeeReceivableView);
//        this.periodicFeeReceivableFeedback = new TextFeedbackPanel("periodicFeeReceivableFeedback", this.periodicFeeReceivableView);
//        this.periodicFeeReceivableVContainer.add(this.periodicFeeReceivableFeedback);
//
//        this.periodicPenaltyReceivableBlock = new WebMarkupBlock("periodicPenaltyReceivableBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicPenaltyReceivableBlock);
//        this.periodicPenaltyReceivableVContainer = new WebMarkupContainer("periodicPenaltyReceivableVContainer");
//        this.periodicPenaltyReceivableBlock.add(this.periodicPenaltyReceivableVContainer);
//        this.periodicPenaltyReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicPenaltyReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicPenaltyReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicPenaltyReceivableView = new Select2SingleChoice<>("periodicPenaltyReceivableField", new PropertyModel<>(this, "periodicPenaltyReceivableValue"), this.periodicPenaltyReceivableProvider);
//        this.periodicPenaltyReceivableView.setLabel(Model.of("Penalty Receivable"));
//        this.periodicPenaltyReceivableView.add(new OnChangeAjaxBehavior());
//        this.periodicPenaltyReceivableVContainer.add(this.periodicPenaltyReceivableView);
//        this.periodicPenaltyReceivableFeedback = new TextFeedbackPanel("periodicPenaltyReceivableFeedback", this.periodicPenaltyReceivableView);
//        this.periodicPenaltyReceivableVContainer.add(this.periodicPenaltyReceivableFeedback);
//
//        this.periodicTransferInSuspenseBlock = new WebMarkupBlock("periodicTransferInSuspenseBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicTransferInSuspenseBlock);
//        this.periodicTransferInSuspenseVContainer = new WebMarkupContainer("periodicTransferInSuspenseVContainer");
//        this.periodicTransferInSuspenseBlock.add(this.periodicTransferInSuspenseVContainer);
//        this.periodicTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
//        this.periodicTransferInSuspenseView = new Select2SingleChoice<>("periodicTransferInSuspenseField", new PropertyModel<>(this, "periodicTransferInSuspenseValue"), this.periodicTransferInSuspenseProvider);
//        this.periodicTransferInSuspenseView.setLabel(Model.of("Transfer in suspense"));
//        this.periodicTransferInSuspenseView.add(new OnChangeAjaxBehavior());
//        this.periodicTransferInSuspenseVContainer.add(this.periodicTransferInSuspenseView);
//        this.periodicTransferInSuspenseFeedback = new TextFeedbackPanel("periodicTransferInSuspenseFeedback", this.periodicTransferInSuspenseView);
//        this.periodicTransferInSuspenseVContainer.add(this.periodicTransferInSuspenseFeedback);
//
//        this.periodicIncomeFromInterestBlock = new WebMarkupBlock("periodicIncomeFromInterestBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicIncomeFromInterestBlock);
//        this.periodicIncomeFromInterestVContainer = new WebMarkupContainer("periodicIncomeFromInterestVContainer");
//        this.periodicIncomeFromInterestBlock.add(this.periodicIncomeFromInterestVContainer);
//        this.periodicIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.periodicIncomeFromInterestView = new Select2SingleChoice<>("periodicIncomeFromInterestField", new PropertyModel<>(this, "periodicIncomeFromInterestValue"), this.periodicIncomeFromInterestProvider);
//        this.periodicIncomeFromInterestView.setLabel(Model.of("Income from Interest"));
//        this.periodicIncomeFromInterestView.add(new OnChangeAjaxBehavior());
//        this.periodicIncomeFromInterestVContainer.add(this.periodicIncomeFromInterestView);
//        this.periodicIncomeFromInterestFeedback = new TextFeedbackPanel("periodicIncomeFromInterestFeedback", this.periodicIncomeFromInterestView);
//        this.periodicIncomeFromInterestVContainer.add(this.periodicIncomeFromInterestFeedback);
//
//        this.periodicIncomeFromFeeBlock = new WebMarkupBlock("periodicIncomeFromFeeBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicIncomeFromFeeBlock);
//        this.periodicIncomeFromFeeVContainer = new WebMarkupContainer("periodicIncomeFromFeeVContainer");
//        this.periodicIncomeFromFeeBlock.add(this.periodicIncomeFromFeeVContainer);
//        this.periodicIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.periodicIncomeFromFeeView = new Select2SingleChoice<>("periodicIncomeFromFeeField", new PropertyModel<>(this, "periodicIncomeFromFeeValue"), this.periodicIncomeFromFeeProvider);
//        this.periodicIncomeFromFeeView.setLabel(Model.of("Income from fees"));
//        this.periodicIncomeFromFeeView.add(new OnChangeAjaxBehavior());
//        this.periodicIncomeFromFeeVContainer.add(this.periodicIncomeFromFeeView);
//        this.periodicIncomeFromFeeFeedback = new TextFeedbackPanel("periodicIncomeFromFeeFeedback", this.periodicIncomeFromFeeView);
//        this.periodicIncomeFromFeeVContainer.add(this.periodicIncomeFromFeeFeedback);
//
//        this.periodicIncomeFromPenaltyBlock = new WebMarkupBlock("periodicIncomeFromPenaltyBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicIncomeFromPenaltyBlock);
//        this.periodicIncomeFromPenaltyVContainer = new WebMarkupContainer("periodicIncomeFromPenaltyVContainer");
//        this.periodicIncomeFromPenaltyBlock.add(this.periodicIncomeFromPenaltyVContainer);
//        this.periodicIncomeFromPenaltyProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicIncomeFromPenaltyProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicIncomeFromPenaltyProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.periodicIncomeFromPenaltyView = new Select2SingleChoice<>("periodicIncomeFromPenaltyField", new PropertyModel<>(this, "periodicIncomeFromPenaltyValue"), this.periodicIncomeFromPenaltyProvider);
//        this.periodicIncomeFromPenaltyView.setLabel(Model.of("Income from Penalty"));
//        this.periodicIncomeFromPenaltyView.add(new OnChangeAjaxBehavior());
//        this.periodicIncomeFromPenaltyVContainer.add(this.periodicIncomeFromPenaltyView);
//        this.periodicIncomeFromPenaltyFeedback = new TextFeedbackPanel("periodicIncomeFromPenaltyFeedback", this.periodicIncomeFromPenaltyView);
//        this.periodicIncomeFromPenaltyVContainer.add(this.periodicIncomeFromPenaltyFeedback);
//
//        this.periodicIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("periodicIncomeFromRecoveryRepaymentBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicIncomeFromRecoveryRepaymentBlock);
//        this.periodicIncomeFromRecoveryRepaymentVContainer = new WebMarkupContainer("periodicIncomeFromRecoveryRepaymentVContainer");
//        this.periodicIncomeFromRecoveryRepaymentBlock.add(this.periodicIncomeFromRecoveryRepaymentVContainer);
//        this.periodicIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
//        this.periodicIncomeFromRecoveryRepaymentView = new Select2SingleChoice<>("periodicIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentValue"), this.periodicIncomeFromRecoveryRepaymentProvider);
//        this.periodicIncomeFromRecoveryRepaymentView.setLabel(Model.of("Income from Recovery Repayments"));
//        this.periodicIncomeFromRecoveryRepaymentView.add(new OnChangeAjaxBehavior());
//        this.periodicIncomeFromRecoveryRepaymentVContainer.add(this.periodicIncomeFromRecoveryRepaymentView);
//        this.periodicIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("periodicIncomeFromRecoveryRepaymentFeedback", this.periodicIncomeFromRecoveryRepaymentView);
//        this.periodicIncomeFromRecoveryRepaymentVContainer.add(this.periodicIncomeFromRecoveryRepaymentFeedback);
//
//        this.periodicLossWrittenOffBlock = new WebMarkupBlock("periodicLossWrittenOffBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicLossWrittenOffBlock);
//        this.periodicLossWrittenOffVContainer = new WebMarkupContainer("periodicLossWrittenOffVContainer");
//        this.periodicLossWrittenOffBlock.add(this.periodicLossWrittenOffVContainer);
//        this.periodicLossWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicLossWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicLossWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
//        this.periodicLossWrittenOffView = new Select2SingleChoice<>("periodicLossWrittenOffField", new PropertyModel<>(this, "periodicLossWrittenOffValue"), this.periodicLossWrittenOffProvider);
//        this.periodicLossWrittenOffView.setLabel(Model.of("Loss written off"));
//        this.periodicLossWrittenOffView.add(new OnChangeAjaxBehavior());
//        this.periodicLossWrittenOffVContainer.add(this.periodicLossWrittenOffView);
//        this.periodicLossWrittenOffFeedback = new TextFeedbackPanel("periodicLossWrittenOffFeedback", this.periodicLossWrittenOffView);
//        this.periodicLossWrittenOffVContainer.add(this.periodicLossWrittenOffFeedback);
//
//        this.periodicOverPaymentLiabilityBlock = new WebMarkupBlock("periodicOverPaymentLiabilityBlock", Size.Six_6);
//        this.periodicVContainer.add(this.periodicOverPaymentLiabilityBlock);
//        this.periodicOverPaymentLiabilityVContainer = new WebMarkupContainer("periodicOverPaymentLiabilityVContainer");
//        this.periodicOverPaymentLiabilityBlock.add(this.periodicOverPaymentLiabilityVContainer);
//        this.periodicOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
//        this.periodicOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
//        this.periodicOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
//        this.periodicOverPaymentLiabilityView = new Select2SingleChoice<>("periodicOverPaymentLiabilityField", new PropertyModel<>(this, "periodicOverPaymentLiabilityValue"), this.periodicOverPaymentLiabilityProvider);
//        this.periodicOverPaymentLiabilityView.setLabel(Model.of("Over payment liability"));
//        this.periodicOverPaymentLiabilityView.add(new OnChangeAjaxBehavior());
//        this.periodicOverPaymentLiabilityVContainer.add(this.periodicOverPaymentLiabilityView);
//        this.periodicOverPaymentLiabilityFeedback = new TextFeedbackPanel("periodicOverPaymentLiabilityFeedback", this.periodicOverPaymentLiabilityView);
//        this.periodicOverPaymentLiabilityVContainer.add(this.periodicOverPaymentLiabilityFeedback);
//
//    }
//
//    protected void initAdvancedAccountingRule() {
//
//        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
//        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
//        add(this.advancedAccountingRuleBlock);
//
//        this.advancedAccountingRuleVContainer = new WebMarkupContainer("advancedAccountingRuleVContainer");
//        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleVContainer);
//
//        // Table
//        {
//            this.fundSourcePopup = new ModalWindow("fundSourcePopup");
//            add(this.fundSourcePopup);
//            this.fundSourcePopup.setOnClose(this::fundSourcePopupClose);
//
//            this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
//            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
//            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
//            this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
//            this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
//            this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
//            this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFundSourceTable);
//            this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
//            this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
//
//            this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
//            advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
//            this.advancedAccountingRuleVContainer.add(advancedAccountingRuleFundSourceAddLink);
//        }
//
//        // Table
//        {
//            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
//            add(this.feeIncomePopup);
//            this.feeIncomePopup.setOnClose(this::feeIncomePopupClose);
//
//            this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
//            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fee"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
//            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
//            this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
//            this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
//            this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
//            this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
//            this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
//            this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
//
//            AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
//            advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
//            this.advancedAccountingRuleVContainer.add(advancedAccountingRuleFeeIncomeAddLink);
//        }
//
//        // Table
//        {
//            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
//            add(this.penaltyIncomePopup);
//            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupClose);
//
//            this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
//            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
//            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
//            this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
//            this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
//            this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
//            this.advancedAccountingRuleVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
//            this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
//            this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
//
//            AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
//            advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
//            this.advancedAccountingRuleVContainer.add(advancedAccountingRulePenaltyIncomeAddLink);
//        }
//    }
//
//    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        if (this.currencyCodeValue != null) {
//            this.feeIncomePopup.setContent(new FeeChargePopup("feeCharge", this.feeIncomePopup, ProductPopup.Loan, this.popupModel, this.currencyCodeValue.getId()));
//            this.feeIncomePopup.show(target);
//        } else {
//            this.feeIncomePopup.setContent(new CurrencyPopup("currency", this.feeIncomePopup));
//            this.feeIncomePopup.show(target);
//        }
//        return false;
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
//    protected void advancedAccountingRuleFeeIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRuleFeeIncomeValue.remove(index);
//        }
//        target.add(this.advancedAccountingRuleFeeIncomeTable);
//    }
//
//    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        if (this.currencyCodeValue != null) {
//            this.penaltyIncomePopup.setContent(new PenaltyChargePopup("penaltyCharge", this.penaltyIncomePopup, ProductPopup.Loan, this.popupModel, this.currencyCodeValue.getId()));
//            this.penaltyIncomePopup.show(target);
//        } else {
//            this.penaltyIncomePopup.setContent(new CurrencyPopup("currency", this.penaltyIncomePopup));
//            this.penaltyIncomePopup.show(target);
//        }
//        return false;
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
//    protected void advancedAccountingRulePenaltyIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRulePenaltyIncomeValue.remove(index);
//        }
//        target.add(this.advancedAccountingRulePenaltyIncomeTable);
//    }
//
//    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        this.fundSourcePopup.setContent(new PaymentTypePopup("paymentType", this.fundSourcePopup, this.popupModel));
//        this.fundSourcePopup.show(target);
//        return false;
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
//    protected void advancedAccountingRuleFundSourceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRuleFundSourceValue.remove(index);
//        }
//        target.add(this.advancedAccountingRuleFundSourceTable);
//    }
//
//    protected List<ActionItem> advancedAccountingRuleFundSourceAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
//        this.cashVContainer.setVisible(false);
//        this.periodicVContainer.setVisible(false);
//        this.upfrontVContainer.setVisible(false);
//        this.advancedAccountingRuleVContainer.setVisible(false);
//        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
//            this.advancedAccountingRuleVContainer.setVisible(false);
//        } else {
//            this.advancedAccountingRuleVContainer.setVisible(true);
//        }
//        if ("Cash".equals(this.accountingValue)) {
//            this.cashVContainer.setVisible(true);
//        } else if ("Periodic".equals(this.accountingValue)) {
//            this.periodicVContainer.setVisible(true);
//        } else if ("Upfront".equals(this.accountingValue)) {
//            this.upfrontVContainer.setVisible(true);
//        }
//        if (target != null) {
//            target.add(this.cashBlock);
//            target.add(this.periodicBlock);
//            target.add(this.upfrontBlock);
//            target.add(this.advancedAccountingRuleBlock);
//        }
//        return false;
//    }
//
//    protected void initSectionConfigurableTermsAndSettings() {
//
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Twelve_12);
//        add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer");
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer);
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView = new CheckBox("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView.add(new OnChangeAjaxBehavior(this::configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView);
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback = new TextFeedbackPanel("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback", this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountView);
//        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountVContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback);
//
//        this.configurableAmortizationBlock = new WebMarkupBlock("configurableAmortizationBlock", Size.Six_6);
//        add(this.configurableAmortizationBlock);
//        this.configurableAmortizationVContainer = new WebMarkupContainer("configurableAmortizationVContainer");
//        this.configurableAmortizationBlock.add(this.configurableAmortizationVContainer);
//        this.configurableAmortizationView = new CheckBox("configurableAmortizationField", new PropertyModel<>(this, "configurableAmortizationValue"));
//        this.configurableAmortizationView.add(new OnChangeAjaxBehavior());
//        this.configurableAmortizationVContainer.add(this.configurableAmortizationView);
//        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback", this.configurableAmortizationView);
//        this.configurableAmortizationVContainer.add(this.configurableAmortizationFeedback);
//
//        this.configurableInterestMethodBlock = new WebMarkupBlock("configurableInterestMethodBlock", Size.Six_6);
//        add(this.configurableInterestMethodBlock);
//        this.configurableInterestMethodVContainer = new WebMarkupContainer("configurableInterestMethodVContainer");
//        this.configurableInterestMethodBlock.add(this.configurableInterestMethodVContainer);
//        this.configurableInterestMethodView = new CheckBox("configurableInterestMethodField", new PropertyModel<>(this, "configurableInterestMethodValue"));
//        this.configurableInterestMethodView.add(new OnChangeAjaxBehavior());
//        this.configurableInterestMethodVContainer.add(this.configurableInterestMethodView);
//        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback", this.configurableInterestMethodView);
//        this.configurableInterestMethodVContainer.add(this.configurableInterestMethodFeedback);
//
//        this.configurableRepaymentStrategyBlock = new WebMarkupBlock("configurableRepaymentStrategyBlock", Size.Six_6);
//        add(this.configurableRepaymentStrategyBlock);
//        this.configurableRepaymentStrategyVContainer = new WebMarkupContainer("configurableRepaymentStrategyVContainer");
//        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyVContainer);
//        this.configurableRepaymentStrategyView = new CheckBox("configurableRepaymentStrategyField", new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
//        this.configurableRepaymentStrategyView.add(new OnChangeAjaxBehavior());
//        this.configurableRepaymentStrategyVContainer.add(this.configurableRepaymentStrategyView);
//        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback", this.configurableRepaymentStrategyView);
//        this.configurableRepaymentStrategyVContainer.add(this.configurableRepaymentStrategyFeedback);
//
//        this.configurableInterestCalculationPeriodBlock = new WebMarkupBlock("configurableInterestCalculationPeriodBlock", Size.Six_6);
//        add(this.configurableInterestCalculationPeriodBlock);
//        this.configurableInterestCalculationPeriodVContainer = new WebMarkupContainer("configurableInterestCalculationPeriodVContainer");
//        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodVContainer);
//        this.configurableInterestCalculationPeriodView = new CheckBox("configurableInterestCalculationPeriodField", new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
//        this.configurableInterestCalculationPeriodView.add(new OnChangeAjaxBehavior());
//        this.configurableInterestCalculationPeriodVContainer.add(this.configurableInterestCalculationPeriodView);
//        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel("configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodView);
//        this.configurableInterestCalculationPeriodVContainer.add(this.configurableInterestCalculationPeriodFeedback);
//
//        this.configurableArrearsToleranceBlock = new WebMarkupBlock("configurableArrearsToleranceBlock", Size.Six_6);
//        add(this.configurableArrearsToleranceBlock);
//        this.configurableArrearsToleranceVContainer = new WebMarkupContainer("configurableArrearsToleranceVContainer");
//        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceVContainer);
//        this.configurableArrearsToleranceView = new CheckBox("configurableArrearsToleranceField", new PropertyModel<>(this, "configurableArrearsToleranceValue"));
//        this.configurableArrearsToleranceView.add(new OnChangeAjaxBehavior());
//        this.configurableArrearsToleranceVContainer.add(this.configurableArrearsToleranceView);
//        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback", this.configurableArrearsToleranceView);
//        this.configurableArrearsToleranceVContainer.add(this.configurableArrearsToleranceFeedback);
//
//        this.configurableRepaidEveryBlock = new WebMarkupBlock("configurableRepaidEveryBlock", Size.Six_6);
//        add(this.configurableRepaidEveryBlock);
//        this.configurableRepaidEveryVContainer = new WebMarkupContainer("configurableRepaidEveryVContainer");
//        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryVContainer);
//        this.configurableRepaidEveryView = new CheckBox("configurableRepaidEveryField", new PropertyModel<>(this, "configurableRepaidEveryValue"));
//        this.configurableRepaidEveryView.add(new OnChangeAjaxBehavior());
//        this.configurableRepaidEveryVContainer.add(this.configurableRepaidEveryView);
//        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback", this.configurableRepaidEveryView);
//        this.configurableRepaidEveryVContainer.add(this.configurableRepaidEveryFeedback);
//
//        this.configurableMoratoriumBlock = new WebMarkupBlock("configurableMoratoriumBlock", Size.Six_6);
//        add(this.configurableMoratoriumBlock);
//        this.configurableMoratoriumVContainer = new WebMarkupContainer("configurableMoratoriumVContainer");
//        this.configurableMoratoriumBlock.add(this.configurableMoratoriumVContainer);
//        this.configurableMoratoriumView = new CheckBox("configurableMoratoriumField", new PropertyModel<>(this, "configurableMoratoriumValue"));
//        this.configurableMoratoriumView.add(new OnChangeAjaxBehavior());
//        this.configurableMoratoriumVContainer.add(this.configurableMoratoriumView);
//        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback", this.configurableMoratoriumView);
//        this.configurableMoratoriumVContainer.add(this.configurableMoratoriumFeedback);
//
//        this.configurableOverdueBeforeMovingBlock = new WebMarkupBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
//        add(this.configurableOverdueBeforeMovingBlock);
//        this.configurableOverdueBeforeMovingVContainer = new WebMarkupContainer("configurableOverdueBeforeMovingVContainer");
//        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingVContainer);
//        this.configurableOverdueBeforeMovingView = new CheckBox("configurableOverdueBeforeMovingField", new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
//        this.configurableOverdueBeforeMovingView.add(new OnChangeAjaxBehavior());
//        this.configurableOverdueBeforeMovingVContainer.add(this.configurableOverdueBeforeMovingView);
//        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback", this.configurableOverdueBeforeMovingView);
//        this.configurableOverdueBeforeMovingVContainer.add(this.configurableOverdueBeforeMovingFeedback);
//    }
//
//    protected boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue != null && this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
//        this.configurableAmortizationVContainer.setVisible(visible);
//        this.configurableInterestMethodVContainer.setVisible(visible);
//        this.configurableRepaymentStrategyVContainer.setVisible(visible);
//        this.configurableInterestCalculationPeriodVContainer.setVisible(visible);
//        this.configurableArrearsToleranceVContainer.setVisible(visible);
//        this.configurableRepaidEveryVContainer.setVisible(visible);
//        this.configurableMoratoriumVContainer.setVisible(visible);
//        this.configurableOverdueBeforeMovingVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.configurableAmortizationBlock);
//            target.add(this.configurableInterestMethodBlock);
//            target.add(this.configurableRepaymentStrategyBlock);
//            target.add(configurableInterestCalculationPeriodBlock);
//            target.add(this.configurableArrearsToleranceBlock);
//            target.add(this.configurableRepaidEveryBlock);
//            target.add(this.configurableMoratoriumBlock);
//            target.add(this.configurableOverdueBeforeMovingBlock);
//        }
//        return false;
//    }
//
//    protected void initSectionLoanTrancheDetails() {
//
//        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Twelve_12);
//        add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
//        this.loanTrancheDetailEnableMultipleDisbursalVContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalVContainer");
//        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalVContainer);
//        this.loanTrancheDetailEnableMultipleDisbursalView = new CheckBox("loanTrancheDetailEnableMultipleDisbursalField", new PropertyModel<>(this, "loanTrancheDetailEnableMultipleDisbursalValue"));
//        this.loanTrancheDetailEnableMultipleDisbursalView.add(new OnChangeAjaxBehavior(this::loanTrancheDetailEnableMultipleDisbursalFieldUpdate));
//        this.loanTrancheDetailEnableMultipleDisbursalVContainer.add(this.loanTrancheDetailEnableMultipleDisbursalView);
//        this.loanTrancheDetailEnableMultipleDisbursalFeedback = new TextFeedbackPanel("loanTrancheDetailEnableMultipleDisbursalFeedback", this.loanTrancheDetailEnableMultipleDisbursalView);
//        this.loanTrancheDetailEnableMultipleDisbursalVContainer.add(this.loanTrancheDetailEnableMultipleDisbursalFeedback);
//
//        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Six_6);
//        add(this.loanTrancheDetailMaximumTrancheCountBlock);
//        this.loanTrancheDetailMaximumTrancheCountVContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountVContainer");
//        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountVContainer);
//        this.loanTrancheDetailMaximumTrancheCountView = new TextField<>("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this, "loanTrancheDetailMaximumTrancheCountValue"));
//        this.loanTrancheDetailMaximumTrancheCountView.setLabel(Model.of("Maximum Tranche count"));
//        this.loanTrancheDetailMaximumTrancheCountView.add(new OnChangeAjaxBehavior());
//        this.loanTrancheDetailMaximumTrancheCountVContainer.add(this.loanTrancheDetailMaximumTrancheCountView);
//        this.loanTrancheDetailMaximumTrancheCountFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumTrancheCountFeedback", this.loanTrancheDetailMaximumTrancheCountView);
//        this.loanTrancheDetailMaximumTrancheCountVContainer.add(this.loanTrancheDetailMaximumTrancheCountFeedback);
//
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Six_6);
//        add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer");
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView = new TextField<>("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView.setLabel(Model.of("Maximum allowed outstanding balance"));
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceView.add(new OnChangeAjaxBehavior());
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceView);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback", this.loanTrancheDetailMaximumAllowedOutstandingBalanceView);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback);
//    }
//
//    protected boolean loanTrancheDetailEnableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.loanTrancheDetailEnableMultipleDisbursalValue != null && this.loanTrancheDetailEnableMultipleDisbursalValue;
//        this.loanTrancheDetailMaximumTrancheCountVContainer.setVisible(visible);
//        this.loanTrancheDetailMaximumAllowedOutstandingBalanceVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.loanTrancheDetailMaximumTrancheCountBlock);
//            target.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
//        }
//        return false;
//    }
//
//    protected void initSectionGuaranteeRequirements() {
//
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Twelve_12);
//        add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer");
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer);
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView = new CheckBox("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", new PropertyModel<>(this, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView.add(new OnChangeAjaxBehavior(this::guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate));
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldView);
//        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldVContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback);
//
//        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Six_6);
//        add(this.guaranteeRequirementMandatoryGuaranteeBlock);
//        this.guaranteeRequirementMandatoryGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeVContainer");
//        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeVContainer);
//        this.guaranteeRequirementMandatoryGuaranteeView = new TextField<>("guaranteeRequirementMandatoryGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMandatoryGuaranteeValue"));
//        this.guaranteeRequirementMandatoryGuaranteeView.setLabel(Model.of("Mandatory Guarantee: (%)"));
//        this.guaranteeRequirementMandatoryGuaranteeView.add(new OnChangeAjaxBehavior());
//        this.guaranteeRequirementMandatoryGuaranteeVContainer.add(this.guaranteeRequirementMandatoryGuaranteeView);
//        this.guaranteeRequirementMandatoryGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMandatoryGuaranteeFeedback", this.guaranteeRequirementMandatoryGuaranteeView);
//        this.guaranteeRequirementMandatoryGuaranteeVContainer.add(this.guaranteeRequirementMandatoryGuaranteeFeedback);
//
//        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Six_6);
//        add(this.guaranteeRequirementMinimumGuaranteeBlock);
//        this.guaranteeRequirementMinimumGuaranteeVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeVContainer");
//        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeVContainer);
//        this.guaranteeRequirementMinimumGuaranteeView = new TextField<>("guaranteeRequirementMinimumGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeValue"));
//        this.guaranteeRequirementMinimumGuaranteeView.setLabel(Model.of("Minimum Guarantee from Own Funds: (%)"));
//        this.guaranteeRequirementMinimumGuaranteeView.add(new OnChangeAjaxBehavior());
//        this.guaranteeRequirementMinimumGuaranteeVContainer.add(this.guaranteeRequirementMinimumGuaranteeView);
//        this.guaranteeRequirementMinimumGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFeedback", this.guaranteeRequirementMinimumGuaranteeView);
//        this.guaranteeRequirementMinimumGuaranteeVContainer.add(this.guaranteeRequirementMinimumGuaranteeFeedback);
//
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Six_6);
//        add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer");
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView = new TextField<>("guaranteeRequirementMinimumGuaranteeFromGuarantorField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView.setLabel(Model.of("Minimum Guarantee from Guarantor Funds: (%)"));
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorView.add(new OnChangeAjaxBehavior());
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorView);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback", this.guaranteeRequirementMinimumGuaranteeFromGuarantorView);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback);
//
//    }
//
//    protected boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue != null && this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
//        this.guaranteeRequirementMandatoryGuaranteeVContainer.setVisible(visible);
//        this.guaranteeRequirementMinimumGuaranteeVContainer.setVisible(visible);
//        this.guaranteeRequirementMinimumGuaranteeFromGuarantorVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
//            target.add(this.guaranteeRequirementMinimumGuaranteeBlock);
//            target.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
//        }
//        return false;
//    }
//
//    protected boolean interestRecalculationRecalculateInterestFieldUpdate(AjaxRequestTarget target) {
//        InterestCalculationPeriod interestCalculationPeriod = null;
//        if (this.settingInterestCalculationPeriodValue != null) {
//            interestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
//        }
//
//        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.Daily && this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue;
//
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.setVisible(visible);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.setVisible(visible);
//        this.interestRecalculationCompoundingOnVContainer.setVisible(visible);
//        this.interestRecalculationCompoundingVContainer.setVisible(visible);
//        this.interestRecalculationCompoundingTypeVContainer.setVisible(visible);
//        this.interestRecalculationCompoundingDayVContainer.setVisible(visible);
//        this.interestRecalculationCompoundingIntervalVContainer.setVisible(visible);
//        this.interestRecalculationRecalculateVContainer.setVisible(visible);
//        this.interestRecalculationRecalculateTypeVContainer.setVisible(visible);
//        this.interestRecalculationRecalculateDayVContainer.setVisible(visible);
//        this.interestRecalculationRecalculateIntervalVContainer.setVisible(visible);
//        if (visible) {
//            interestRecalculationRecalculateFieldUpdate(target);
//            interestRecalculationCompoundingFieldUpdate(target);
//        }
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
//            target.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
//            target.add(this.interestRecalculationCompoundingOnBlock);
//            target.add(this.interestRecalculationCompoundingBlock);
//            target.add(this.interestRecalculationCompoundingTypeBlock);
//            target.add(this.interestRecalculationCompoundingDayBlock);
//            target.add(this.interestRecalculationCompoundingIntervalBlock);
//            target.add(this.interestRecalculationRecalculateBlock);
//            target.add(this.interestRecalculationRecalculateTypeBlock);
//            target.add(this.interestRecalculationRecalculateDayBlock);
//            target.add(this.interestRecalculationRecalculateIntervalBlock);
//            target.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
//        }
//
//        initSectionValidationRule();
//        return false;
//    }
//
//    protected void initSectionInterestRecalculation() {
//
//        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
//        add(this.interestRecalculationRecalculateInterestBlock);
//        this.interestRecalculationRecalculateInterestVContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestVContainer");
//        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestVContainer);
//        this.interestRecalculationRecalculateInterestView = new CheckBox("interestRecalculationRecalculateInterestField", new PropertyModel<>(this, "interestRecalculationRecalculateInterestValue"));
//        this.interestRecalculationRecalculateInterestView.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateInterestFieldUpdate));
//        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestView);
//        this.interestRecalculationRecalculateInterestFeedback = new TextFeedbackPanel("interestRecalculationRecalculateInterestFeedback", this.interestRecalculationRecalculateInterestView);
//        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestFeedback);
//
//        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
//        add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleVContainer");
//        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleVContainer);
//        this.interestRecalculationPreClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
//        this.interestRecalculationPreClosureInterestCalculationRuleView = new Select2SingleChoice<>("interestRecalculationPreClosureInterestCalculationRuleField", new PropertyModel<>(this, "interestRecalculationPreClosureInterestCalculationRuleValue"), this.interestRecalculationPreClosureInterestCalculationRuleProvider);
//        this.interestRecalculationPreClosureInterestCalculationRuleView.setLabel(Model.of("Pre-closure interest calculation rule"));
//        this.interestRecalculationPreClosureInterestCalculationRuleView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleView);
//        this.interestRecalculationPreClosureInterestCalculationRuleFeedback = new TextFeedbackPanel("interestRecalculationPreClosureInterestCalculationRuleFeedback", this.interestRecalculationPreClosureInterestCalculationRuleView);
//        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleFeedback);
//
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
//        add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeVContainer");
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeView = new Select2SingleChoice<>("interestRecalculationAdvancePaymentsAdjustmentTypeField", new PropertyModel<>(this, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"), this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeView.setLabel(Model.of("Advance payments adjustment type"));
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeView);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback = new TextFeedbackPanel("interestRecalculationAdvancePaymentsAdjustmentTypeFeedback", this.interestRecalculationAdvancePaymentsAdjustmentTypeView);
//        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback);
//
//        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
//        add(this.interestRecalculationCompoundingOnBlock);
//        this.interestRecalculationCompoundingOnVContainer = new WebMarkupContainer("interestRecalculationCompoundingOnVContainer");
//        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnVContainer);
//        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
//        this.interestRecalculationCompoundingOnView = new Select2SingleChoice<>("interestRecalculationCompoundingOnField", new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"), this.interestRecalculationCompoundingOnProvider);
//        this.interestRecalculationCompoundingOnView.setLabel(Model.of("Interest recalculation compounding on"));
//        this.interestRecalculationCompoundingOnView.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
//        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnView);
//        this.interestRecalculationCompoundingOnFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnView);
//        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnFeedback);
//
//        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Four_4);
//        add(this.interestRecalculationCompoundingBlock);
//        this.interestRecalculationCompoundingVContainer = new WebMarkupContainer("interestRecalculationCompoundingVContainer");
//        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingVContainer);
//        this.interestRecalculationCompoundingProvider = new FrequencyProvider();
//        this.interestRecalculationCompoundingView = new Select2SingleChoice<>("interestRecalculationCompoundingField", new PropertyModel<>(this, "interestRecalculationCompoundingValue"), this.interestRecalculationCompoundingProvider);
//        this.interestRecalculationCompoundingView.setLabel(Model.of("Frequency for compounding"));
//        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingView);
//        this.interestRecalculationCompoundingView.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
//        this.interestRecalculationCompoundingFeedback = new TextFeedbackPanel("interestRecalculationCompoundingFeedback", this.interestRecalculationCompoundingView);
//        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingFeedback);
//
//        this.interestRecalculationCompoundingTypeBlock = new WebMarkupBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
//        add(this.interestRecalculationCompoundingTypeBlock);
//        this.interestRecalculationCompoundingTypeVContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeVContainer");
//        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeVContainer);
//        this.interestRecalculationCompoundingTypeProvider = new FrequencyTypeProvider();
//        this.interestRecalculationCompoundingTypeView = new Select2SingleChoice<>("interestRecalculationCompoundingTypeField", new PropertyModel<>(this, "interestRecalculationCompoundingTypeValue"), this.interestRecalculationCompoundingTypeProvider);
//        this.interestRecalculationCompoundingTypeView.setLabel(Model.of("Frequency type for compounding"));
//        this.interestRecalculationCompoundingTypeView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationCompoundingTypeVContainer.add(this.interestRecalculationCompoundingTypeView);
//        this.interestRecalculationCompoundingTypeFeedback = new TextFeedbackPanel("interestRecalculationCompoundingTypeFeedback", this.interestRecalculationCompoundingTypeView);
//        this.interestRecalculationCompoundingTypeVContainer.add(this.interestRecalculationCompoundingTypeFeedback);
//
//        this.interestRecalculationCompoundingDayBlock = new WebMarkupBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);
//        add(this.interestRecalculationCompoundingDayBlock);
//        this.interestRecalculationCompoundingDayVContainer = new WebMarkupContainer("interestRecalculationCompoundingDayVContainer");
//        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayVContainer);
//        this.interestRecalculationCompoundingDayProvider = new FrequencyDayProvider();
//        this.interestRecalculationCompoundingDayView = new Select2SingleChoice<>("interestRecalculationCompoundingDayField", new PropertyModel<>(this, "interestRecalculationCompoundingDayValue"), this.interestRecalculationCompoundingDayProvider);
//        this.interestRecalculationCompoundingDayView.setLabel(Model.of("Frequency day for compounding"));
//        this.interestRecalculationCompoundingDayView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationCompoundingDayVContainer.add(this.interestRecalculationCompoundingDayView);
//        this.interestRecalculationCompoundingDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingDayFeedback", this.interestRecalculationCompoundingDayView);
//        this.interestRecalculationCompoundingDayVContainer.add(this.interestRecalculationCompoundingDayFeedback);
//
//        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
//        add(this.interestRecalculationCompoundingIntervalBlock);
//        this.interestRecalculationCompoundingIntervalVContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalVContainer");
//        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalVContainer);
//        this.interestRecalculationCompoundingIntervalView = new TextField<>("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this, "interestRecalculationCompoundingIntervalValue"));
//        this.interestRecalculationCompoundingIntervalView.setLabel(Model.of("Frequency Interval for compounding"));
//        this.interestRecalculationCompoundingIntervalView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalView);
//        this.interestRecalculationCompoundingIntervalFeedback = new TextFeedbackPanel("interestRecalculationCompoundingIntervalFeedback", this.interestRecalculationCompoundingIntervalView);
//        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalFeedback);
//
//        this.interestRecalculationRecalculateBlock = new WebMarkupBlock("interestRecalculationRecalculateBlock", Size.Four_4);
//        add(this.interestRecalculationRecalculateBlock);
//        this.interestRecalculationRecalculateVContainer = new WebMarkupContainer("interestRecalculationRecalculateVContainer");
//        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateVContainer);
//        this.interestRecalculationRecalculateProvider = new FrequencyProvider();
//        this.interestRecalculationRecalculateView = new Select2SingleChoice<>("interestRecalculationRecalculateField", new PropertyModel<>(this, "interestRecalculationRecalculateValue"), this.interestRecalculationRecalculateProvider);
//        this.interestRecalculationRecalculateView.setLabel(Model.of("Frequency for recalculate Outstanding Principal"));
//        this.interestRecalculationRecalculateView.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
//        this.interestRecalculationRecalculateVContainer.add(this.interestRecalculationRecalculateView);
//        this.interestRecalculationRecalculateFeedback = new TextFeedbackPanel("interestRecalculationRecalculateFeedback", this.interestRecalculationRecalculateView);
//        this.interestRecalculationRecalculateVContainer.add(this.interestRecalculationRecalculateFeedback);
//
//        this.interestRecalculationRecalculateTypeBlock = new WebMarkupBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
//        add(this.interestRecalculationRecalculateTypeBlock);
//        this.interestRecalculationRecalculateTypeVContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeVContainer");
//        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeVContainer);
//        this.interestRecalculationRecalculateTypeProvider = new FrequencyTypeProvider();
//        this.interestRecalculationRecalculateTypeView = new Select2SingleChoice<>("interestRecalculationRecalculateTypeField", new PropertyModel<>(this, "interestRecalculationRecalculateTypeValue"), this.interestRecalculationRecalculateTypeProvider);
//        this.interestRecalculationRecalculateTypeView.setLabel(Model.of("Frequency type for recalculate"));
//        this.interestRecalculationRecalculateTypeView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationRecalculateTypeVContainer.add(this.interestRecalculationRecalculateTypeView);
//        this.interestRecalculationRecalculateTypeFeedback = new TextFeedbackPanel("interestRecalculationRecalculateTypeFeedback", this.interestRecalculationRecalculateTypeView);
//        this.interestRecalculationRecalculateTypeVContainer.add(this.interestRecalculationRecalculateTypeFeedback);
//
//        this.interestRecalculationRecalculateDayBlock = new WebMarkupBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);
//        add(this.interestRecalculationRecalculateDayBlock);
//        this.interestRecalculationRecalculateDayVContainer = new WebMarkupContainer("interestRecalculationRecalculateDayVContainer");
//        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayVContainer);
//        this.interestRecalculationRecalculateDayProvider = new FrequencyDayProvider();
//        this.interestRecalculationRecalculateDayView = new Select2SingleChoice<>("interestRecalculationRecalculateDayField", new PropertyModel<>(this, "interestRecalculationRecalculateDayValue"), this.interestRecalculationRecalculateDayProvider);
//        this.interestRecalculationRecalculateDayView.setLabel(Model.of("Frequency day for recalculate"));
//        this.interestRecalculationRecalculateDayView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationRecalculateDayVContainer.add(this.interestRecalculationRecalculateDayView);
//        this.interestRecalculationRecalculateDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateDayFeedback", this.interestRecalculationRecalculateDayView);
//        this.interestRecalculationRecalculateDayVContainer.add(this.interestRecalculationRecalculateDayFeedback);
//
//        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
//        add(this.interestRecalculationRecalculateIntervalBlock);
//        this.interestRecalculationRecalculateIntervalVContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalVContainer");
//        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalVContainer);
//        this.interestRecalculationRecalculateIntervalView = new TextField<>("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this, "interestRecalculationRecalculateIntervalValue"));
//        this.interestRecalculationRecalculateIntervalView.setLabel(Model.of("Frequency Interval for recalculate"));
//        this.interestRecalculationRecalculateIntervalView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationRecalculateIntervalVContainer.add(this.interestRecalculationRecalculateIntervalView);
//        this.interestRecalculationRecalculateIntervalFeedback = new TextFeedbackPanel("interestRecalculationRecalculateIntervalFeedback", this.interestRecalculationRecalculateIntervalView);
//        this.interestRecalculationRecalculateIntervalVContainer.add(this.interestRecalculationRecalculateIntervalFeedback);
//
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
//        add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer");
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer);
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView = new CheckBox("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView.add(new OnChangeAjaxBehavior());
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback", this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);
//        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback);
//
//    }
//
//    protected boolean interestRecalculationRecalculateFieldUpdate(AjaxRequestTarget target) {
//        this.interestRecalculationRecalculateTypeVContainer.setVisible(false);
//        this.interestRecalculationRecalculateDayVContainer.setVisible(false);
//        this.interestRecalculationRecalculateIntervalVContainer.setVisible(false);
//
//        if (this.interestRecalculationRecalculateValue != null) {
//            Frequency frequency = Frequency.valueOf(this.interestRecalculationRecalculateValue.getId());
//            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
//                this.interestRecalculationRecalculateIntervalVContainer.setVisible(true);
//            }
//            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
//                this.interestRecalculationRecalculateDayVContainer.setVisible(true);
//            }
//            if (frequency == Frequency.Monthly) {
//                this.interestRecalculationRecalculateTypeVContainer.setVisible(true);
//            }
//        }
//        if (target != null) {
//            target.add(this.interestRecalculationRecalculateTypeBlock);
//            target.add(this.interestRecalculationRecalculateDayBlock);
//            target.add(this.interestRecalculationRecalculateIntervalBlock);
//        }
//        return false;
//    }
//
//    protected boolean interestRecalculationCompoundingFieldUpdate(AjaxRequestTarget target) {
//
//        this.interestRecalculationCompoundingTypeVContainer.setVisible(false);
//        this.interestRecalculationCompoundingDayVContainer.setVisible(false);
//        this.interestRecalculationCompoundingIntervalVContainer.setVisible(false);
//
//        if (this.interestRecalculationCompoundingOnValue != null && InterestRecalculationCompound.valueOf(this.interestRecalculationCompoundingOnValue.getId()) != InterestRecalculationCompound.None) {
//            this.interestRecalculationCompoundingVContainer.setVisible(true);
//
//            if (this.interestRecalculationCompoundingValue != null) {
//                Frequency frequency = Frequency.valueOf(this.interestRecalculationCompoundingValue.getId());
//                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
//                    this.interestRecalculationCompoundingIntervalVContainer.setVisible(true);
//                }
//                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
//                    this.interestRecalculationCompoundingDayVContainer.setVisible(true);
//                }
//                if (frequency == Frequency.Monthly) {
//                    this.interestRecalculationCompoundingTypeVContainer.setVisible(true);
//                }
//            }
//        } else {
//            this.interestRecalculationCompoundingVContainer.setVisible(false);
//        }
//        if (target != null) {
//            target.add(this.interestRecalculationCompoundingTypeBlock);
//            target.add(this.interestRecalculationCompoundingDayBlock);
//            target.add(this.interestRecalculationCompoundingIntervalBlock);
//            target.add(this.interestRecalculationCompoundingBlock);
//        }
//        return false;
//    }
//
//    protected void initSectionSetting() {
//
//        this.settingAmortizationBlock = new WebMarkupBlock("settingAmortizationBlock", Size.Six_6);
//        add(this.settingAmortizationBlock);
//        this.settingAmortizationVContainer = new WebMarkupContainer("settingAmortizationVContainer");
//        this.settingAmortizationBlock.add(this.settingAmortizationVContainer);
//        this.settingAmortizationProvider = new AmortizationProvider();
//        this.settingAmortizationView = new Select2SingleChoice<>("settingAmortizationField", new PropertyModel<>(this, "settingAmortizationValue"), this.settingAmortizationProvider);
//        this.settingAmortizationView.setLabel(Model.of("Amortization"));
//        this.settingAmortizationView.add(new OnChangeAjaxBehavior());
//        this.settingAmortizationVContainer.add(this.settingAmortizationView);
//        this.settingAmortizationFeedback = new TextFeedbackPanel("settingAmortizationFeedback", this.settingAmortizationView);
//        this.settingAmortizationVContainer.add(this.settingAmortizationFeedback);
//
//        this.settingInterestMethodBlock = new WebMarkupBlock("settingInterestMethodBlock", Size.Six_6);
//        add(this.settingInterestMethodBlock);
//        this.settingInterestMethodVContainer = new WebMarkupContainer("settingInterestMethodVContainer");
//        this.settingInterestMethodBlock.add(this.settingInterestMethodVContainer);
//        this.settingInterestMethodProvider = new InterestMethodProvider();
//        this.settingInterestMethodView = new Select2SingleChoice<>("settingInterestMethodField", new PropertyModel<>(this, "settingInterestMethodValue"), this.settingInterestMethodProvider);
//        this.settingInterestMethodView.setLabel(Model.of("Interest method"));
//        this.settingInterestMethodView.add(new OnChangeAjaxBehavior());
//        this.settingInterestMethodVContainer.add(this.settingInterestMethodView);
//        this.settingInterestMethodFeedback = new TextFeedbackPanel("settingInterestMethodFeedback", this.settingInterestMethodView);
//        this.settingInterestMethodVContainer.add(this.settingInterestMethodFeedback);
//
//        this.settingInterestCalculationPeriodBlock = new WebMarkupBlock("settingInterestCalculationPeriodBlock", Size.Six_6);
//        add(this.settingInterestCalculationPeriodBlock);
//        this.settingInterestCalculationPeriodVContainer = new WebMarkupContainer("settingInterestCalculationPeriodVContainer");
//        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodVContainer);
//        this.settingInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
//        this.settingInterestCalculationPeriodView = new Select2SingleChoice<>("settingInterestCalculationPeriodField", new PropertyModel<>(this, "settingInterestCalculationPeriodValue"), this.settingInterestCalculationPeriodProvider);
//        this.settingInterestCalculationPeriodView.setLabel(Model.of("Interest calculation period"));
//        this.settingInterestCalculationPeriodView.add(new OnChangeAjaxBehavior(this::settingInterestCalculationPeriodFieldUpdate));
//        this.settingInterestCalculationPeriodVContainer.add(this.settingInterestCalculationPeriodView);
//        this.settingInterestCalculationPeriodFeedback = new TextFeedbackPanel("settingInterestCalculationPeriodFeedback", this.settingInterestCalculationPeriodView);
//        this.settingInterestCalculationPeriodVContainer.add(this.settingInterestCalculationPeriodFeedback);
//
//        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
//        add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodVContainer");
//        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodVContainer);
//        this.settingCalculateInterestForExactDaysInPartialPeriodView = new CheckBox("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
//        this.settingCalculateInterestForExactDaysInPartialPeriodView.add(new OnChangeAjaxBehavior());
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodView);
//        this.settingCalculateInterestForExactDaysInPartialPeriodFeedback = new TextFeedbackPanel("settingCalculateInterestForExactDaysInPartialPeriodFeedback", this.settingCalculateInterestForExactDaysInPartialPeriodView);
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodFeedback);
//
//        this.settingRepaymentStrategyBlock = new WebMarkupBlock("settingRepaymentStrategyBlock", Size.Six_6);
//        add(this.settingRepaymentStrategyBlock);
//        this.settingRepaymentStrategyVContainer = new WebMarkupContainer("settingRepaymentStrategyVContainer");
//        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyVContainer);
//        this.settingRepaymentStrategyProvider = new RepaymentStrategyProvider();
//        this.settingRepaymentStrategyView = new Select2SingleChoice<>("settingRepaymentStrategyField", new PropertyModel<>(this, "settingRepaymentStrategyValue"), this.settingRepaymentStrategyProvider);
//        this.settingRepaymentStrategyView.setLabel(Model.of("Repayment strategy"));
//        this.settingRepaymentStrategyView.add(new OnChangeAjaxBehavior());
//        this.settingRepaymentStrategyVContainer.add(this.settingRepaymentStrategyView);
//        this.settingRepaymentStrategyFeedback = new TextFeedbackPanel("settingRepaymentStrategyFeedback", this.settingRepaymentStrategyView);
//        this.settingRepaymentStrategyVContainer.add(this.settingRepaymentStrategyFeedback);
//
//        this.settingMoratoriumPrincipalBlock = new WebMarkupBlock("settingMoratoriumPrincipalBlock", Size.Six_6);
//        add(this.settingMoratoriumPrincipalBlock);
//        this.settingMoratoriumPrincipalVContainer = new WebMarkupContainer("settingMoratoriumPrincipalVContainer");
//        this.settingMoratoriumPrincipalBlock.add(this.settingMoratoriumPrincipalVContainer);
//        this.settingMoratoriumPrincipalView = new TextField<>("settingMoratoriumPrincipalField", new PropertyModel<>(this, "settingMoratoriumPrincipalValue"));
//        this.settingMoratoriumPrincipalView.setLabel(Model.of("Moratorium principal"));
//        this.settingMoratoriumPrincipalView.add(new OnChangeAjaxBehavior());
//        this.settingMoratoriumPrincipalVContainer.add(this.settingMoratoriumPrincipalView);
//        this.settingMoratoriumPrincipalFeedback = new TextFeedbackPanel("settingMoratoriumPrincipalFeedback", this.settingMoratoriumPrincipalView);
//        this.settingMoratoriumPrincipalVContainer.add(this.settingMoratoriumPrincipalFeedback);
//
//        this.settingMoratoriumInterestBlock = new WebMarkupBlock("settingMoratoriumInterestBlock", Size.Six_6);
//        add(this.settingMoratoriumInterestBlock);
//        this.settingMoratoriumInterestVContainer = new WebMarkupContainer("settingMoratoriumInterestVContainer");
//        this.settingMoratoriumInterestBlock.add(this.settingMoratoriumInterestVContainer);
//        this.settingMoratoriumInterestView = new TextField<>("settingMoratoriumInterestField", new PropertyModel<>(this, "settingMoratoriumInterestValue"));
//        this.settingMoratoriumInterestView.setLabel(Model.of("Moratorium interest"));
//        this.settingMoratoriumInterestView.add(new OnChangeAjaxBehavior());
//        this.settingMoratoriumInterestVContainer.add(this.settingMoratoriumInterestView);
//        this.settingMoratoriumInterestFeedback = new TextFeedbackPanel("settingMoratoriumInterestFeedback", this.settingMoratoriumInterestView);
//        this.settingMoratoriumInterestVContainer.add(this.settingMoratoriumInterestFeedback);
//
//        this.settingInterestFreePeriodBlock = new WebMarkupBlock("settingInterestFreePeriodBlock", Size.Six_6);
//        add(this.settingInterestFreePeriodBlock);
//        this.settingInterestFreePeriodVContainer = new WebMarkupContainer("settingInterestFreePeriodVContainer");
//        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodVContainer);
//        this.settingInterestFreePeriodView = new TextField<>("settingInterestFreePeriodField", new PropertyModel<>(this, "settingInterestFreePeriodValue"));
//        this.settingInterestFreePeriodView.setLabel(Model.of("Interest free period"));
//        this.settingInterestFreePeriodView.add(new OnChangeAjaxBehavior());
//        this.settingInterestFreePeriodVContainer.add(this.settingInterestFreePeriodView);
//        this.settingInterestFreePeriodFeedback = new TextFeedbackPanel("settingInterestFreePeriodFeedback", this.settingInterestFreePeriodView);
//        this.settingInterestFreePeriodVContainer.add(this.settingInterestFreePeriodFeedback);
//
//        this.settingArrearsToleranceBlock = new WebMarkupBlock("settingArrearsToleranceBlock", Size.Six_6);
//        add(this.settingArrearsToleranceBlock);
//        this.settingArrearsToleranceVContainer = new WebMarkupContainer("settingArrearsToleranceVContainer");
//        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceVContainer);
//        this.settingArrearsToleranceView = new TextField<>("settingArrearsToleranceField", new PropertyModel<>(this, "settingArrearsToleranceValue"));
//        this.settingArrearsToleranceView.setLabel(Model.of("Arrears tolerance"));
//        this.settingArrearsToleranceView.add(new OnChangeAjaxBehavior());
//        this.settingArrearsToleranceVContainer.add(this.settingArrearsToleranceView);
//        this.settingArrearsToleranceFeedback = new TextFeedbackPanel("settingArrearsToleranceFeedback", this.settingArrearsToleranceView);
//        this.settingArrearsToleranceVContainer.add(this.settingArrearsToleranceFeedback);
//
//        this.settingDayInYearBlock = new WebMarkupBlock("settingDayInYearBlock", Size.Six_6);
//        add(this.settingDayInYearBlock);
//        this.settingDayInYearVContainer = new WebMarkupContainer("settingDayInYearVContainer");
//        this.settingDayInYearBlock.add(this.settingDayInYearVContainer);
//        this.settingDayInYearProvider = new DayInYearProvider();
//        this.settingDayInYearView = new Select2SingleChoice<>("settingDayInYearField", new PropertyModel<>(this, "settingDayInYearValue"), this.settingDayInYearProvider);
//        this.settingDayInYearView.setLabel(Model.of("Day in year"));
//        this.settingDayInYearView.add(new OnChangeAjaxBehavior());
//        this.settingDayInYearVContainer.add(this.settingDayInYearView);
//        this.settingDayInYearFeedback = new TextFeedbackPanel("settingDayInYearFeedback", this.settingDayInYearView);
//        this.settingDayInYearVContainer.add(this.settingDayInYearFeedback);
//
//        this.settingDayInMonthBlock = new WebMarkupBlock("settingDayInMonthBlock", Size.Six_6);
//        add(this.settingDayInMonthBlock);
//        this.settingDayInMonthVContainer = new WebMarkupContainer("settingDayInMonthVContainer");
//        this.settingDayInMonthBlock.add(this.settingDayInMonthVContainer);
//        this.settingDayInMonthProvider = new DayInMonthProvider();
//        this.settingDayInMonthView = new Select2SingleChoice<>("settingDayInMonthField", new PropertyModel<>(this, "settingDayInMonthValue"), this.settingDayInMonthProvider);
//        this.settingDayInMonthView.setLabel(Model.of("Day in month"));
//        this.settingDayInMonthView.add(new OnChangeAjaxBehavior());
//        this.settingDayInMonthVContainer.add(this.settingDayInMonthView);
//        this.settingDayInMonthFeedback = new TextFeedbackPanel("settingDayInMonthFeedback", this.settingDayInMonthView);
//        this.settingDayInMonthVContainer.add(this.settingDayInMonthFeedback);
//
//        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Twelve_12);
//        add(this.settingAllowFixingOfTheInstallmentAmountBlock);
//        this.settingAllowFixingOfTheInstallmentAmountVContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountVContainer");
//        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountVContainer);
//        this.settingAllowFixingOfTheInstallmentAmountView = new CheckBox("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this, "settingAllowFixingOfTheInstallmentAmountValue"));
//        this.settingAllowFixingOfTheInstallmentAmountView.add(new OnChangeAjaxBehavior());
//        this.settingAllowFixingOfTheInstallmentAmountVContainer.add(this.settingAllowFixingOfTheInstallmentAmountView);
//        this.settingAllowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel("settingAllowFixingOfTheInstallmentAmountFeedback", this.settingAllowFixingOfTheInstallmentAmountView);
//        this.settingAllowFixingOfTheInstallmentAmountVContainer.add(this.settingAllowFixingOfTheInstallmentAmountFeedback);
//
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Six_6);
//        add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer");
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer);
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView = new TextField<>("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView.setLabel(Model.of("Number of days a loan may be overdue before moving into arrears"));
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView.add(new OnChangeAjaxBehavior());
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView);
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback", this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsView);
//        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsVContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);
//
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Six_6);
//        add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer");
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer);
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView = new TextField<>("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView.setLabel(Model.of("Maximum number of days a loan may be overdue before becoming a NPA"));
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView.add(new OnChangeAjaxBehavior());
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView);
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback", this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaView);
//        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaVContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);
//
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
//        add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer");
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer);
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView = new CheckBox("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView.add(new OnChangeAjaxBehavior());
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView);
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback", this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedView);
//        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedVContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);
//
//        this.settingPrincipalThresholdForLastInstalmentBlock = new WebMarkupBlock("settingPrincipalThresholdForLastInstalmentBlock", Size.Six_6);
//        add(this.settingPrincipalThresholdForLastInstalmentBlock);
//        this.settingPrincipalThresholdForLastInstalmentVContainer = new WebMarkupContainer("settingPrincipalThresholdForLastInstalmentVContainer");
//        this.settingPrincipalThresholdForLastInstalmentBlock.add(this.settingPrincipalThresholdForLastInstalmentVContainer);
//        this.settingPrincipalThresholdForLastInstalmentView = new TextField<>("settingPrincipalThresholdForLastInstalmentField", new PropertyModel<>(this, "settingPrincipalThresholdForLastInstalmentValue"));
//        this.settingPrincipalThresholdForLastInstalmentView.setLabel(Model.of("Principal Threshold (%) for Last Installment"));
//        this.settingPrincipalThresholdForLastInstalmentView.add(new OnChangeAjaxBehavior());
//        this.settingPrincipalThresholdForLastInstalmentVContainer.add(this.settingPrincipalThresholdForLastInstalmentView);
//        this.settingPrincipalThresholdForLastInstalmentFeedback = new TextFeedbackPanel("settingPrincipalThresholdForLastInstalmentFeedback", this.settingPrincipalThresholdForLastInstalmentView);
//        this.settingPrincipalThresholdForLastInstalmentVContainer.add(this.settingPrincipalThresholdForLastInstalmentFeedback);
//
//        this.settingVariableInstallmentsAllowedBlock = new WebMarkupBlock("settingVariableInstallmentsAllowedBlock", Size.Six_6);
//        add(this.settingVariableInstallmentsAllowedBlock);
//        this.settingVariableInstallmentsAllowedVContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedVContainer");
//        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedVContainer);
//        this.settingVariableInstallmentsAllowedView = new CheckBox("settingVariableInstallmentsAllowedField", new PropertyModel<>(this, "settingVariableInstallmentsAllowedValue"));
//        this.settingVariableInstallmentsAllowedView.add(new OnChangeAjaxBehavior(this::settingVariableInstallmentsAllowedFieldUpdate));
//        this.settingVariableInstallmentsAllowedVContainer.add(this.settingVariableInstallmentsAllowedView);
//        this.settingVariableInstallmentsAllowedFeedback = new TextFeedbackPanel("settingVariableInstallmentsAllowedFeedback", this.settingVariableInstallmentsAllowedView);
//        this.settingVariableInstallmentsAllowedVContainer.add(this.settingVariableInstallmentsAllowedFeedback);
//
//        this.settingVariableInstallmentsMinimumBlock = new WebMarkupBlock("settingVariableInstallmentsMinimumBlock", Size.Six_6);
//        add(this.settingVariableInstallmentsMinimumBlock);
//        this.settingVariableInstallmentsMinimumVContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumVContainer");
//        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumVContainer);
//        this.settingVariableInstallmentsMinimumView = new TextField<>("settingVariableInstallmentsMinimumField", new PropertyModel<>(this, "settingVariableInstallmentsMinimumValue"));
//        this.settingVariableInstallmentsMinimumView.setLabel(Model.of("Variable Installments Minimum"));
//        this.settingVariableInstallmentsMinimumView.add(new OnChangeAjaxBehavior());
//        this.settingVariableInstallmentsMinimumVContainer.add(this.settingVariableInstallmentsMinimumView);
//        this.settingVariableInstallmentsMinimumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMinimumFeedback", this.settingVariableInstallmentsMinimumView);
//        this.settingVariableInstallmentsMinimumVContainer.add(this.settingVariableInstallmentsMinimumFeedback);
//
//        this.settingVariableInstallmentsMaximumBlock = new WebMarkupBlock("settingVariableInstallmentsMaximumBlock", Size.Six_6);
//        add(this.settingVariableInstallmentsMaximumBlock);
//        this.settingVariableInstallmentsMaximumVContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumVContainer");
//        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumVContainer);
//        this.settingVariableInstallmentsMaximumView = new TextField<>("settingVariableInstallmentsMaximumField", new PropertyModel<>(this, "settingVariableInstallmentsMaximumValue"));
//        this.settingVariableInstallmentsMaximumView.setLabel(Model.of("Variable Installments Maximum"));
//        this.settingVariableInstallmentsMaximumView.add(new OnChangeAjaxBehavior());
//        this.settingVariableInstallmentsMaximumVContainer.add(this.settingVariableInstallmentsMaximumView);
//        this.settingVariableInstallmentsMaximumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMaximumFeedback", this.settingVariableInstallmentsMaximumView);
//        this.settingVariableInstallmentsMaximumVContainer.add(this.settingVariableInstallmentsMaximumFeedback);
//
//        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Twelve_12);
//        add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
//        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansVContainer");
//        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansVContainer);
//        this.settingAllowedToBeUsedForProvidingTopupLoansView = new CheckBox("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
//        this.settingAllowedToBeUsedForProvidingTopupLoansView.add(new OnChangeAjaxBehavior());
//        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansView);
//        this.settingAllowedToBeUsedForProvidingTopupLoansFeedback = new TextFeedbackPanel("settingAllowedToBeUsedForProvidingTopupLoansFeedback", this.settingAllowedToBeUsedForProvidingTopupLoansView);
//        this.settingAllowedToBeUsedForProvidingTopupLoansVContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansFeedback);
//
//    }
//
//    protected void initSectionDefault() {
//        termVaryBasedOnLoanCycleFieldUpdate(null);
//
//        settingInterestCalculationPeriodFieldUpdate(null);
//
//        interestRecalculationRecalculateInterestFieldUpdate(null);
//
//        interestRecalculationCompoundingFieldUpdate(null);
//
//        interestRecalculationRecalculateFieldUpdate(null);
//
//        guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(null);
//
//        loanTrancheDetailEnableMultipleDisbursalFieldUpdate(null);
//
//        configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(null);
//
//        accountingFieldUpdate(null);
//
//        settingVariableInstallmentsAllowedFieldUpdate(null);
//    }
//
//    protected void initSectionDetail() {
//
//        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
//        add(this.detailProductNameBlock);
//        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
//        this.detailProductNameBlock.add(this.detailProductNameVContainer);
//        this.detailProductNameView = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
//        this.detailProductNameView.setLabel(Model.of("Product Name"));
//        this.detailProductNameVContainer.add(this.detailProductNameView);
//        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameView);
//        this.detailProductNameVContainer.add(this.detailProductNameFeedback);
//
//        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
//        add(this.detailShortNameBlock);
//        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
//        this.detailShortNameBlock.add(this.detailShortNameVContainer);
//        this.detailShortNameView = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
//        this.detailShortNameView.setLabel(Model.of("Short Name"));
//        this.detailShortNameVContainer.add(this.detailShortNameView);
//        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameView);
//        this.detailShortNameVContainer.add(this.detailShortNameFeedback);
//
//        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
//        add(this.detailDescriptionBlock);
//        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
//        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
//        this.detailDescriptionView = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
//        this.detailDescriptionView.setLabel(Model.of("Description"));
//        this.detailDescriptionVContainer.add(this.detailDescriptionView);
//        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionView);
//        this.detailDescriptionVContainer.add(this.detailDescriptionFeedback);
//
//        this.detailFundProvider = new FundProvider();
//        this.detailFundBlock = new WebMarkupBlock("detailFundBlock", Size.Six_6);
//        add(this.detailFundBlock);
//        this.detailFundVContainer = new WebMarkupContainer("detailFundVContainer");
//        this.detailFundBlock.add(this.detailFundVContainer);
//        this.detailFundView = new Select2SingleChoice<>("detailFundField", new PropertyModel<>(this, "detailFundValue"), this.detailFundProvider);
//        this.detailFundView.setLabel(Model.of("Fund"));
//        this.detailFundVContainer.add(this.detailFundView);
//        this.detailFundFeedback = new TextFeedbackPanel("detailFundFeedback", this.detailFundView);
//        this.detailFundVContainer.add(this.detailFundFeedback);
//
//        this.detailStartDateBlock = new WebMarkupBlock("detailStartDateBlock", Size.Six_6);
//        add(this.detailStartDateBlock);
//        this.detailStartDateVContainer = new WebMarkupContainer("detailStartDateVContainer");
//        this.detailStartDateBlock.add(this.detailStartDateVContainer);
//        this.detailStartDateView = new DateTextField("detailStartDateField", new PropertyModel<>(this, "detailStartDateValue"));
//        this.detailStartDateView.setLabel(Model.of("Start Date"));
//        this.detailStartDateVContainer.add(this.detailStartDateView);
//        this.detailStartDateFeedback = new TextFeedbackPanel("detailStartDateFeedback", this.detailStartDateView);
//        this.detailStartDateVContainer.add(this.detailStartDateFeedback);
//
//        this.detailCloseDateBlock = new WebMarkupBlock("detailCloseDateBlock", Size.Six_6);
//        add(this.detailCloseDateBlock);
//        this.detailCloseDateVContainer = new WebMarkupContainer("detailCloseDateVContainer");
//        this.detailCloseDateBlock.add(this.detailCloseDateVContainer);
//        this.detailCloseDateView = new DateTextField("detailCloseDateField", new PropertyModel<>(this, "detailCloseDateValue"));
//        this.detailCloseDateView.setLabel(Model.of("Close Date"));
//        this.detailCloseDateVContainer.add(this.detailCloseDateView);
//        this.detailCloseDateFeedback = new TextFeedbackPanel("detailCloseDateFeedback", this.detailCloseDateView);
//        this.detailCloseDateVContainer.add(this.detailCloseDateFeedback);
//
//        this.detailIncludeInCustomerLoanCounterBlock = new WebMarkupBlock("detailIncludeInCustomerLoanCounterBlock", Size.Twelve_12);
//        add(this.detailIncludeInCustomerLoanCounterBlock);
//        this.detailIncludeInCustomerLoanCounterVContainer = new WebMarkupContainer("detailIncludeInCustomerLoanCounterVContainer");
//        this.detailIncludeInCustomerLoanCounterBlock.add(this.detailIncludeInCustomerLoanCounterVContainer);
//        this.detailIncludeInCustomerLoanCounterView = new CheckBox("detailIncludeInCustomerLoanCounterField", new PropertyModel<>(this, "detailIncludeInCustomerLoanCounterValue"));
//        this.detailIncludeInCustomerLoanCounterVContainer.add(this.detailIncludeInCustomerLoanCounterView);
//        this.detailIncludeInCustomerLoanCounterFeedback = new TextFeedbackPanel("detailIncludeInCustomerLoanCounterFeedback", this.detailIncludeInCustomerLoanCounterView);
//        this.detailIncludeInCustomerLoanCounterVContainer.add(this.detailIncludeInCustomerLoanCounterFeedback);
//    }
//
//    protected void initSectionCurrency() {
//
//        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
//        add(this.currencyCodeBlock);
//        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
//        this.currencyCodeBlock.add(this.currencyCodeVContainer);
//        this.currencyCodeProvider = new CurrencyProvider();
//        this.currencyCodeView = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
//        this.currencyCodeView.add(new OnChangeAjaxBehavior());
//        this.currencyCodeView.setLabel(Model.of("Currency"));
//        this.currencyCodeVContainer.add(this.currencyCodeView);
//        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeView);
//        this.currencyCodeVContainer.add(this.currencyCodeFeedback);
//
//        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
//        add(this.currencyDecimalPlaceBlock);
//        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
//        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
//        this.currencyDecimalPlaceView = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
//        this.currencyDecimalPlaceView.setLabel(Model.of("Decimal Places"));
//        this.currencyDecimalPlaceView.add(new OnChangeAjaxBehavior());
//        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);
//        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceView);
//        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceFeedback);
//
//        this.currencyInMultipleOfBlock = new WebMarkupBlock("currencyInMultipleOfBlock", Size.Six_6);
//        add(this.currencyInMultipleOfBlock);
//        this.currencyInMultipleOfVContainer = new WebMarkupContainer("currencyInMultipleOfVContainer");
//        this.currencyInMultipleOfBlock.add(this.currencyInMultipleOfVContainer);
//        this.currencyInMultipleOfView = new TextField<>("currencyInMultipleOfField", new PropertyModel<>(this, "currencyInMultipleOfValue"));
//        this.currencyInMultipleOfView.setLabel(Model.of("Currency in multiple of"));
//        this.currencyInMultipleOfView.add(new OnChangeAjaxBehavior());
//        this.currencyInMultipleOfVContainer.add(this.currencyInMultipleOfView);
//        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback", this.currencyInMultipleOfView);
//        this.currencyInMultipleOfVContainer.add(this.currencyInMultipleOfFeedback);
//
//        this.currencyInstallmentInMultipleOfBlock = new WebMarkupBlock("currencyInstallmentInMultipleOfBlock", Size.Six_6);
//        add(this.currencyInstallmentInMultipleOfBlock);
//        this.currencyInstallmentInMultipleOfVContainer = new WebMarkupContainer("currencyInstallmentInMultipleOfVContainer");
//        this.currencyInstallmentInMultipleOfBlock.add(this.currencyInstallmentInMultipleOfVContainer);
//        this.currencyInstallmentInMultipleOfView = new TextField<>("currencyInstallmentInMultipleOfField", new PropertyModel<>(this, "currencyInstallmentInMultipleOfValue"));
//        this.currencyInstallmentInMultipleOfView.setLabel(Model.of("Installment in multiple of"));
//        this.currencyInstallmentInMultipleOfView.add(new OnChangeAjaxBehavior());
//        this.currencyInstallmentInMultipleOfView.add(RangeValidator.minimum((int) 1));
//        this.currencyInstallmentInMultipleOfVContainer.add(this.currencyInstallmentInMultipleOfView);
//        this.currencyInstallmentInMultipleOfFeedback = new TextFeedbackPanel("currencyInstallmentInMultipleOfFeedback", this.currencyInstallmentInMultipleOfView);
//        this.currencyInstallmentInMultipleOfVContainer.add(this.currencyInstallmentInMultipleOfFeedback);
//    }
//
//    protected void initSectionTerms() {
//
//        this.termVaryBasedOnLoanCycleBlock = new WebMarkupBlock("termVaryBasedOnLoanCycleBlock", Size.Twelve_12);
//        add(this.termVaryBasedOnLoanCycleBlock);
//        this.termVaryBasedOnLoanCycleVContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleVContainer");
//        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleVContainer);
//        this.termVaryBasedOnLoanCycleView = new CheckBox("termVaryBasedOnLoanCycleField", new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
//        this.termVaryBasedOnLoanCycleView.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
//        this.termVaryBasedOnLoanCycleVContainer.add(this.termVaryBasedOnLoanCycleView);
//        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback", this.termVaryBasedOnLoanCycleView);
//        this.termVaryBasedOnLoanCycleVContainer.add(this.termVaryBasedOnLoanCycleFeedback);
//
//        this.termPrincipalMinimumBlock = new WebMarkupBlock("termPrincipalMinimumBlock", Size.Four_4);
//        add(this.termPrincipalMinimumBlock);
//        this.termPrincipalMinimumVContainer = new WebMarkupContainer("termPrincipalMinimumVContainer");
//        this.termPrincipalMinimumBlock.add(this.termPrincipalMinimumVContainer);
//        this.termPrincipalMinimumView = new TextField<>("termPrincipalMinimumField", new PropertyModel<>(this, "termPrincipalMinimumValue"));
//        this.termPrincipalMinimumView.setLabel(Model.of("Principal Minimum"));
//        this.termPrincipalMinimumView.add(new OnChangeAjaxBehavior());
//        this.termPrincipalMinimumVContainer.add(this.termPrincipalMinimumView);
//        this.termPrincipalMinimumFeedback = new TextFeedbackPanel("termPrincipalMinimumFeedback", this.termPrincipalMinimumView);
//        this.termPrincipalMinimumVContainer.add(this.termPrincipalMinimumFeedback);
//
//        this.termPrincipalDefaultBlock = new WebMarkupBlock("termPrincipalDefaultBlock", Size.Four_4);
//        add(this.termPrincipalDefaultBlock);
//        this.termPrincipalDefaultVContainer = new WebMarkupContainer("termPrincipalDefaultVContainer");
//        this.termPrincipalDefaultBlock.add(this.termPrincipalDefaultVContainer);
//        this.termPrincipalDefaultView = new TextField<>("termPrincipalDefaultField", new PropertyModel<>(this, "termPrincipalDefaultValue"));
//        this.termPrincipalDefaultView.setLabel(Model.of("Principal Default"));
//        this.termPrincipalDefaultView.add(new OnChangeAjaxBehavior());
//        this.termPrincipalDefaultVContainer.add(this.termPrincipalDefaultView);
//        this.termPrincipalDefaultFeedback = new TextFeedbackPanel("termPrincipalDefaultFeedback", this.termPrincipalDefaultView);
//        this.termPrincipalDefaultVContainer.add(this.termPrincipalDefaultFeedback);
//
//        this.termPrincipalMaximumBlock = new WebMarkupBlock("termPrincipalMaximumBlock", Size.Four_4);
//        add(this.termPrincipalMaximumBlock);
//        this.termPrincipalMaximumVContainer = new WebMarkupContainer("termPrincipalMaximumVContainer");
//        this.termPrincipalMaximumBlock.add(this.termPrincipalMaximumVContainer);
//        this.termPrincipalMaximumView = new TextField<>("termPrincipalMaximumField", new PropertyModel<>(this, "termPrincipalMaximumValue"));
//        this.termPrincipalMaximumView.setLabel(Model.of("Principal Maximum"));
//        this.termPrincipalMaximumView.add(new OnChangeAjaxBehavior());
//        this.termPrincipalMaximumVContainer.add(this.termPrincipalMaximumView);
//        this.termPrincipalMaximumFeedback = new TextFeedbackPanel("termPrincipalMaximumFeedback", this.termPrincipalMaximumView);
//        this.termPrincipalMaximumVContainer.add(this.termPrincipalMaximumFeedback);
//
//        {
//            this.termPrincipalByLoanCyclePopup = new ModalWindow("termPrincipalByLoanCyclePopup");
//            add(this.termPrincipalByLoanCyclePopup);
//            this.termPrincipalByLoanCyclePopup.setOnClose(this::termPrincipalByLoanCyclePopupClose);
//
//            this.termPrincipalByLoanCycleBlock = new WebMarkupBlock("termPrincipalByLoanCycleBlock", Size.Twelve_12);
//            add(this.termPrincipalByLoanCycleBlock);
//            this.termPrincipalByLoanCycleVContainer = new WebMarkupContainer("termPrincipalByLoanCycleVContainer");
//            this.termPrincipalByLoanCycleBlock.add(this.termPrincipalByLoanCycleVContainer);
//
//            this.termPrincipalByLoanCycleColumn = Lists.newArrayList();
//            this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipalByLoanCycleColumn));
//            this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipalByLoanCycleColumn));
//            this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipalByLoanCycleColumn));
//            this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipalByLoanCycleColumn));
//            this.termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipalByLoanCycleColumn));
//            this.termPrincipalByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termPrincipalByLoanCycleAction, this::termPrincipalByLoanCycleClick));
//            this.termPrincipalByLoanCycleProvider = new ListDataProvider(this.termPrincipalByLoanCycleValue);
//            this.termPrincipalByLoanCycleTable = new DataTable<>("termPrincipalByLoanCycleTable", this.termPrincipalByLoanCycleColumn, this.termPrincipalByLoanCycleProvider, 20);
//            this.termPrincipalByLoanCycleVContainer.add(this.termPrincipalByLoanCycleTable);
//            this.termPrincipalByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipalByLoanCycleTable, this.termPrincipalByLoanCycleProvider));
//            this.termPrincipalByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipalByLoanCycleTable));
//
//            this.termPrincipalByLoanCycleAddLink = new AjaxLink<>("termPrincipalByLoanCycleAddLink");
//            this.termPrincipalByLoanCycleAddLink.setOnClick(this::termPrincipalByLoanCycleAddLinkClick);
//            this.termPrincipalByLoanCycleVContainer.add(this.termPrincipalByLoanCycleAddLink);
//        }
//
//        this.termNumberOfRepaymentMinimumBlock = new WebMarkupBlock("termNumberOfRepaymentMinimumBlock", Size.Four_4);
//        add(this.termNumberOfRepaymentMinimumBlock);
//        this.termNumberOfRepaymentMinimumVContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumVContainer");
//        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumVContainer);
//        this.termNumberOfRepaymentMinimumView = new TextField<>("termNumberOfRepaymentMinimumField", new PropertyModel<>(this, "termNumberOfRepaymentMinimumValue"));
//        this.termNumberOfRepaymentMinimumView.setLabel(Model.of("Number of repayment Minimum"));
//        this.termNumberOfRepaymentMinimumView.add(new OnChangeAjaxBehavior());
//        this.termNumberOfRepaymentMinimumVContainer.add(this.termNumberOfRepaymentMinimumView);
//        this.termNumberOfRepaymentMinimumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMinimumFeedback", this.termNumberOfRepaymentMinimumView);
//        this.termNumberOfRepaymentMinimumVContainer.add(this.termNumberOfRepaymentMinimumFeedback);
//
//        this.termNumberOfRepaymentDefaultBlock = new WebMarkupBlock("termNumberOfRepaymentDefaultBlock", Size.Four_4);
//        add(this.termNumberOfRepaymentDefaultBlock);
//        this.termNumberOfRepaymentDefaultVContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultVContainer");
//        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultVContainer);
//        this.termNumberOfRepaymentDefaultView = new TextField<>("termNumberOfRepaymentDefaultField", new PropertyModel<>(this, "termNumberOfRepaymentDefaultValue"));
//        this.termNumberOfRepaymentDefaultView.setLabel(Model.of("Number of repayment Default"));
//        this.termNumberOfRepaymentDefaultView.add(new OnChangeAjaxBehavior());
//        this.termNumberOfRepaymentDefaultVContainer.add(this.termNumberOfRepaymentDefaultView);
//        this.termNumberOfRepaymentDefaultFeedback = new TextFeedbackPanel("termNumberOfRepaymentDefaultFeedback", this.termNumberOfRepaymentDefaultView);
//        this.termNumberOfRepaymentDefaultVContainer.add(this.termNumberOfRepaymentDefaultFeedback);
//
//        this.termNumberOfRepaymentMaximumBlock = new WebMarkupBlock("termNumberOfRepaymentMaximumBlock", Size.Four_4);
//        add(this.termNumberOfRepaymentMaximumBlock);
//        this.termNumberOfRepaymentMaximumVContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumVContainer");
//        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumVContainer);
//        this.termNumberOfRepaymentMaximumView = new TextField<>("termNumberOfRepaymentMaximumField", new PropertyModel<>(this, "termNumberOfRepaymentMaximumValue"));
//        this.termNumberOfRepaymentMaximumView.setLabel(Model.of("Number of repayment Maximum"));
//        this.termNumberOfRepaymentMaximumView.add(new OnChangeAjaxBehavior());
//        this.termNumberOfRepaymentMaximumVContainer.add(this.termNumberOfRepaymentMaximumView);
//        this.termNumberOfRepaymentMaximumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMaximumFeedback", this.termNumberOfRepaymentMaximumView);
//        this.termNumberOfRepaymentMaximumVContainer.add(this.termNumberOfRepaymentMaximumFeedback);
//
//        // Table
//        {
//            this.termNumberOfRepaymentByLoanCyclePopup = new ModalWindow("termNumberOfRepaymentByLoanCyclePopup");
//            add(this.termNumberOfRepaymentByLoanCyclePopup);
//            this.termNumberOfRepaymentByLoanCyclePopup.setOnClose(this::termNumberOfRepaymentByLoanCyclePopupClose);
//
//            this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
//            add(this.termNumberOfRepaymentByLoanCycleBlock);
//            this.termNumberOfRepaymentByLoanCycleVContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleVContainer");
//            this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleVContainer);
//
//            this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
//            this.termNumberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNumberOfRepaymentByLoanCycleAction, this::termNumberOfRepaymentByLoanCycleClick));
//            this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue);
//            this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
//            this.termNumberOfRepaymentByLoanCycleVContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
//            this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
//            this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));
//
//            this.termNumberOfRepaymentByLoanCycleAddLink = new AjaxLink<>("termNumberOfRepaymentByLoanCycleAddLink");
//            this.termNumberOfRepaymentByLoanCycleAddLink.setOnClick(this::termNumberOfRepaymentByLoanCycleAddLinkClick);
//            this.termNumberOfRepaymentByLoanCycleVContainer.add(this.termNumberOfRepaymentByLoanCycleAddLink);
//        }
//
//        // Linked to Floating Interest Rates
//        this.termLinkedToFloatingInterestRatesBlock = new WebMarkupBlock("termLinkedToFloatingInterestRatesBlock", Size.Twelve_12);
//        add(this.termLinkedToFloatingInterestRatesBlock);
//        this.termLinkedToFloatingInterestRatesVContainer = new WebMarkupContainer("termLinkedToFloatingInterestRatesVContainer");
//        this.termLinkedToFloatingInterestRatesBlock.add(this.termLinkedToFloatingInterestRatesVContainer);
//        this.termLinkedToFloatingInterestRatesView = new CheckBox("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this, "termLinkedToFloatingInterestRatesValue"));
//        this.termLinkedToFloatingInterestRatesView.add(new OnChangeAjaxBehavior(this::termLinkedToFloatingInterestRatesFieldUpdate));
//        this.termLinkedToFloatingInterestRatesVContainer.add(this.termLinkedToFloatingInterestRatesView);
//        this.termLinkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("termLinkedToFloatingInterestRatesFeedback", this.termLinkedToFloatingInterestRatesView);
//        this.termLinkedToFloatingInterestRatesVContainer.add(this.termLinkedToFloatingInterestRatesFeedback);
//
//        this.termNominalInterestRateMinimumBlock = new WebMarkupBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
//        add(this.termNominalInterestRateMinimumBlock);
//        this.termNominalInterestRateMinimumVContainer = new WebMarkupContainer("termNominalInterestRateMinimumVContainer");
//        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumVContainer);
//        this.termNominalInterestRateMinimumView = new TextField<>("termNominalInterestRateMinimumField", new PropertyModel<>(this, "termNominalInterestRateMinimumValue"));
//        this.termNominalInterestRateMinimumView.setLabel(Model.of("Nominal interest rate minimum"));
//        this.termNominalInterestRateMinimumView.add(new OnChangeAjaxBehavior());
//        this.termNominalInterestRateMinimumVContainer.add(this.termNominalInterestRateMinimumView);
//        this.termNominalInterestRateMinimumFeedback = new TextFeedbackPanel("termNominalInterestRateMinimumFeedback", this.termNominalInterestRateMinimumView);
//        this.termNominalInterestRateMinimumVContainer.add(this.termNominalInterestRateMinimumFeedback);
//
//        this.termNominalInterestRateDefaultBlock = new WebMarkupBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
//        add(this.termNominalInterestRateDefaultBlock);
//        this.termNominalInterestRateDefaultVContainer = new WebMarkupContainer("termNominalInterestRateDefaultVContainer");
//        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultVContainer);
//        this.termNominalInterestRateDefaultView = new TextField<>("termNominalInterestRateDefaultField", new PropertyModel<>(this, "termNominalInterestRateDefaultValue"));
//        this.termNominalInterestRateDefaultView.setLabel(Model.of("Nominal interest rate Default"));
//        this.termNominalInterestRateDefaultView.add(new OnChangeAjaxBehavior());
//        this.termNominalInterestRateDefaultVContainer.add(this.termNominalInterestRateDefaultView);
//        this.termNominalInterestRateDefaultFeedback = new TextFeedbackPanel("termNominalInterestRateDefaultFeedback", this.termNominalInterestRateDefaultView);
//        this.termNominalInterestRateDefaultVContainer.add(this.termNominalInterestRateDefaultFeedback);
//
//        this.termNominalInterestRateMaximumBlock = new WebMarkupBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
//        add(this.termNominalInterestRateMaximumBlock);
//        this.termNominalInterestRateMaximumVContainer = new WebMarkupContainer("termNominalInterestRateMaximumVContainer");
//        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumVContainer);
//        this.termNominalInterestRateMaximumView = new TextField<>("termNominalInterestRateMaximumField", new PropertyModel<>(this, "termNominalInterestRateMaximumValue"));
//        this.termNominalInterestRateMaximumView.setLabel(Model.of("Nominal interest rate Maximum"));
//        this.termNominalInterestRateMaximumView.add(new OnChangeAjaxBehavior());
//        this.termNominalInterestRateMaximumVContainer.add(this.termNominalInterestRateMaximumView);
//        this.termNominalInterestRateMaximumFeedback = new TextFeedbackPanel("termNominalInterestRateMaximumFeedback", this.termNominalInterestRateMaximumView);
//        this.termNominalInterestRateMaximumVContainer.add(this.termNominalInterestRateMaximumFeedback);
//
//        this.termNominalInterestRateTypeBlock = new WebMarkupBlock("termNominalInterestRateTypeBlock", Size.Three_3);
//        add(this.termNominalInterestRateTypeBlock);
//        this.termNominalInterestRateTypeVContainer = new WebMarkupContainer("termNominalInterestRateTypeVContainer");
//        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeVContainer);
//        this.termNominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
//        this.termNominalInterestRateTypeView = new Select2SingleChoice<>("termNominalInterestRateTypeField", new PropertyModel<>(this, "termNominalInterestRateTypeValue"), this.termNominalInterestRateTypeProvider);
//        this.termNominalInterestRateTypeView.setLabel(Model.of("Type"));
//        this.termNominalInterestRateTypeView.add(new OnChangeAjaxBehavior());
//        this.termNominalInterestRateTypeVContainer.add(this.termNominalInterestRateTypeView);
//        this.termNominalInterestRateTypeFeedback = new TextFeedbackPanel("termNominalInterestRateTypeFeedback", this.termNominalInterestRateTypeView);
//        this.termNominalInterestRateTypeVContainer.add(this.termNominalInterestRateTypeFeedback);
//
//        this.termFloatingInterestRateBlock = new WebMarkupBlock("termFloatingInterestRateBlock", Size.Four_4);
//        add(this.termFloatingInterestRateBlock);
//        this.termFloatingInterestRateVContainer = new WebMarkupContainer("termFloatingInterestRateVContainer");
//        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateVContainer);
//        this.termFloatingInterestRateProvider = new SingleChoiceProvider("m_floating_rates", "id", "name");
//        this.termFloatingInterestRateView = new Select2SingleChoice<>("termFloatingInterestRateField", new PropertyModel<>(this, "termFloatingInterestRateValue"), this.termFloatingInterestRateProvider);
//        this.termFloatingInterestRateView.setLabel(Model.of("Floating interest rate"));
//        this.termFloatingInterestRateView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestRateVContainer.add(this.termFloatingInterestRateView);
//        this.termFloatingInterestRateFeedback = new TextFeedbackPanel("termFloatingInterestRateFeedback", this.termFloatingInterestRateView);
//        this.termFloatingInterestRateVContainer.add(this.termFloatingInterestRateFeedback);
//
//        this.termFloatingInterestDifferentialBlock = new WebMarkupBlock("termFloatingInterestDifferentialBlock", Size.Four_4);
//        add(this.termFloatingInterestDifferentialBlock);
//        this.termFloatingInterestDifferentialVContainer = new WebMarkupContainer("termFloatingInterestDifferentialVContainer");
//        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialVContainer);
//        this.termFloatingInterestDifferentialView = new TextField<>("termFloatingInterestDifferentialField", new PropertyModel<>(this, "termFloatingInterestDifferentialValue"));
//        this.termFloatingInterestDifferentialView.setLabel(Model.of("Floating interest differential rate"));
//        this.termFloatingInterestDifferentialView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestDifferentialVContainer.add(this.termFloatingInterestDifferentialView);
//        this.termFloatingInterestDifferentialFeedback = new TextFeedbackPanel("termFloatingInterestDifferentialFeedback", this.termFloatingInterestDifferentialView);
//        this.termFloatingInterestDifferentialVContainer.add(this.termFloatingInterestDifferentialFeedback);
//
//        this.termFloatingInterestAllowedBlock = new WebMarkupBlock("termFloatingInterestAllowedBlock", Size.Four_4);
//        add(this.termFloatingInterestAllowedBlock);
//        this.termFloatingInterestAllowedVContainer = new WebMarkupContainer("termFloatingInterestAllowedVContainer");
//        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedVContainer);
//        this.termFloatingInterestAllowedView = new CheckBox("termFloatingInterestAllowedField", new PropertyModel<>(this, "termFloatingInterestAllowedValue"));
//        this.termFloatingInterestAllowedView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestAllowedVContainer.add(this.termFloatingInterestAllowedView);
//        this.termFloatingInterestAllowedFeedback = new TextFeedbackPanel("termFloatingInterestAllowedFeedback", this.termFloatingInterestAllowedView);
//        this.termFloatingInterestAllowedVContainer.add(this.termFloatingInterestAllowedFeedback);
//
//        this.termFloatingInterestMinimumBlock = new WebMarkupBlock("termFloatingInterestMinimumBlock", Size.Four_4);
//        add(this.termFloatingInterestMinimumBlock);
//        this.termFloatingInterestMinimumVContainer = new WebMarkupContainer("termFloatingInterestMinimumVContainer");
//        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumVContainer);
//        this.termFloatingInterestMinimumView = new TextField<>("termFloatingInterestMinimumField", new PropertyModel<>(this, "termFloatingInterestMinimumValue"));
//        this.termFloatingInterestMinimumView.setLabel(Model.of("Floating interest minimum"));
//        this.termFloatingInterestMinimumView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestMinimumVContainer.add(this.termFloatingInterestMinimumView);
//        this.termFloatingInterestMinimumFeedback = new TextFeedbackPanel("termFloatingInterestMinimumFeedback", this.termFloatingInterestMinimumView);
//        this.termFloatingInterestMinimumVContainer.add(this.termFloatingInterestMinimumFeedback);
//
//        this.termFloatingInterestDefaultBlock = new WebMarkupBlock("termFloatingInterestDefaultBlock", Size.Four_4);
//        add(this.termFloatingInterestDefaultBlock);
//        this.termFloatingInterestDefaultVContainer = new WebMarkupContainer("termFloatingInterestDefaultVContainer");
//        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultVContainer);
//        this.termFloatingInterestDefaultView = new TextField<>("termFloatingInterestDefaultField", new PropertyModel<>(this, "termFloatingInterestDefaultValue"));
//        this.termFloatingInterestDefaultView.setLabel(Model.of("Floating interest Default"));
//        this.termFloatingInterestDefaultView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestDefaultVContainer.add(this.termFloatingInterestDefaultView);
//        this.termFloatingInterestDefaultFeedback = new TextFeedbackPanel("termFloatingInterestDefaultFeedback", this.termFloatingInterestDefaultView);
//        this.termFloatingInterestDefaultVContainer.add(this.termFloatingInterestDefaultFeedback);
//
//        this.termFloatingInterestMaximumBlock = new WebMarkupBlock("termFloatingInterestMaximumBlock", Size.Four_4);
//        add(this.termFloatingInterestMaximumBlock);
//        this.termFloatingInterestMaximumVContainer = new WebMarkupContainer("termFloatingInterestMaximumVContainer");
//        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumVContainer);
//        this.termFloatingInterestMaximumView = new TextField<>("termFloatingInterestMaximumField", new PropertyModel<>(this, "termFloatingInterestMaximumValue"));
//        this.termFloatingInterestMaximumView.setLabel(Model.of("Floating interest Maximum"));
//        this.termFloatingInterestMaximumView.add(new OnChangeAjaxBehavior());
//        this.termFloatingInterestMaximumVContainer.add(this.termFloatingInterestMaximumView);
//        this.termFloatingInterestMaximumFeedback = new TextFeedbackPanel("termFloatingInterestMaximumFeedback", this.termFloatingInterestMaximumView);
//        this.termFloatingInterestMaximumVContainer.add(this.termFloatingInterestMaximumFeedback);
//
//        // Table
//        {
//            this.termNominalInterestRateByLoanCycleBlock = new WebMarkupBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
//            add(this.termNominalInterestRateByLoanCycleBlock);
//            this.termNominalInterestRateByLoanCycleVContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleVContainer");
//            this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleVContainer);
//
//            this.termNominalInterestRateByLoanCyclePopup = new ModalWindow("termNominalInterestRateByLoanCyclePopup");
//            add(this.termNominalInterestRateByLoanCyclePopup);
//            this.termNominalInterestRateByLoanCyclePopup.setOnClose(this::termNominalInterestRateByLoanCyclePopupClose);
//
//            List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
//            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleWhenColumn));
//            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleCycleColumn));
//            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleMinimumColumn));
//            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleDefaultColumn));
//            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleMaximumColumn));
//            termNominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNominalInterestRateByLoanCycleActionItem, this::termNominalInterestRateByLoanCycleActionClick));
//            this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue);
//            this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
//            this.termNominalInterestRateByLoanCycleVContainer.add(this.termNominalInterestRateByLoanCycleTable);
//            this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
//            this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));
//
//            this.termNominalInterestRateByLoanCycleAddLink = new AjaxLink<>("termNominalInterestRateByLoanCycleAddLink");
//            this.termNominalInterestRateByLoanCycleAddLink.setOnClick(this::termNominalInterestRateByLoanCycleAddLinkClick);
//            this.termNominalInterestRateByLoanCycleVContainer.add(this.termNominalInterestRateByLoanCycleAddLink);
//        }
//
//        this.termRepaidEveryBlock = new WebMarkupBlock("termRepaidEveryBlock", Size.Six_6);
//        add(this.termRepaidEveryBlock);
//        this.termRepaidEveryVContainer = new WebMarkupContainer("termRepaidEveryVContainer");
//        this.termRepaidEveryBlock.add(this.termRepaidEveryVContainer);
//        this.termRepaidEveryView = new TextField<>("termRepaidEveryField", new PropertyModel<>(this, "termRepaidEveryValue"));
//        this.termRepaidEveryView.setLabel(Model.of("Repaid every"));
//        this.termRepaidEveryView.add(new OnChangeAjaxBehavior());
//        this.termRepaidEveryVContainer.add(this.termRepaidEveryView);
//        this.termRepaidEveryFeedback = new TextFeedbackPanel("termRepaidEveryFeedback", this.termRepaidEveryView);
//        this.termRepaidEveryVContainer.add(this.termRepaidEveryFeedback);
//
//        this.termRepaidTypeBlock = new WebMarkupBlock("termRepaidTypeBlock", Size.Six_6);
//        this.termRepaidTypeProvider = new LockInTypeProvider(LockInType.Day, LockInType.Week, LockInType.Month);
//        add(this.termRepaidTypeBlock);
//        this.termRepaidTypeVContainer = new WebMarkupContainer("termRepaidTypeVContainer");
//        this.termRepaidTypeBlock.add(this.termRepaidTypeVContainer);
//        this.termRepaidTypeView = new Select2SingleChoice<>("termRepaidTypeField", new PropertyModel<>(this, "termRepaidTypeValue"), this.termRepaidTypeProvider);
//        this.termRepaidTypeView.setLabel(Model.of("Repaid Type"));
//        this.termRepaidTypeView.add(new OnChangeAjaxBehavior());
//        this.termRepaidTypeVContainer.add(this.termRepaidTypeView);
//        this.termRepaidTypeFeedback = new TextFeedbackPanel("termRepaidTypeFeedback", this.termRepaidTypeView);
//        this.termRepaidTypeVContainer.add(this.termRepaidTypeFeedback);
//
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
//        add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer");
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer);
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView = new TextField<>("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView.setLabel(Model.of("Minimum days between disbursal and first repayment date"));
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView.add(new OnChangeAjaxBehavior());
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView);
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel("termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback", this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateView);
//        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateVContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);
//    }
//
//    protected boolean termNominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        this.termNominalInterestRateByLoanCyclePopup.setContent(new InterestLoanCyclePopup("termNominalInterestRateByLoanCycle", this.termNominalInterestRateByLoanCyclePopup, this.popupModel));
//        this.termNominalInterestRateByLoanCyclePopup.show(target);
//        return false;
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
//        String value = (String) model.get(jdbcColumn);
//        return new TextCell(value);
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
//        Double value = (Double) model.get(jdbcColumn);
//        return new TextCell(value);
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
//        Double value = (Double) model.get(jdbcColumn);
//        return new TextCell(value);
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
//        Double value = (Double) model.get(jdbcColumn);
//        return new TextCell(value);
//    }
//
//    protected ItemPanel termNominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
//        Long value = (Long) model.get(jdbcColumn);
//        return new TextCell(value);
//    }
//
//    protected void termNominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.termNominalInterestRateByLoanCycleValue.size(); i++) {
//            Map<String, Object> column = this.termNominalInterestRateByLoanCycleValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.termNominalInterestRateByLoanCycleValue.remove(index);
//        }
//        target.add(this.termNominalInterestRateByLoanCycleTable);
//    }
//
//    protected List<ActionItem> termNominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
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
//    protected boolean termNumberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        this.termNumberOfRepaymentByLoanCyclePopup.setContent(new RepaymentLoanCyclePopup("termNumberOfRepaymentByLoanCycle", this.termNumberOfRepaymentByLoanCyclePopup, this.popupModel));
//        this.termNumberOfRepaymentByLoanCyclePopup.show(target);
//        return false;
//    }
//
//    protected void termNumberOfRepaymentByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.termNumberOfRepaymentByLoanCycleValue.size(); i++) {
//            Map<String, Object> column = this.termNumberOfRepaymentByLoanCycleValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.termNumberOfRepaymentByLoanCycleValue.remove(index);
//        }
//        target.add(this.termNumberOfRepaymentByLoanCycleTable);
//    }
//
//    protected List<ActionItem> termNumberOfRepaymentByLoanCycleAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean termPrincipalByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        this.termPrincipalByLoanCyclePopup.setContent(new PrincipalLoanCyclePopup("termPrincipalByLoanCycle", this.termPrincipalByLoanCyclePopup, this.popupModel));
//        this.termPrincipalByLoanCyclePopup.show(target);
//        return false;
//    }
//
//    protected void termNominalInterestRateByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("when", this.popupModel.get("whenValue"));
//        item.put("cycle", this.popupModel.get("loanCycleValue"));
//        item.put("minimum", this.popupModel.get("minimumValue"));
//        item.put("default", this.popupModel.get("defaultValue"));
//        item.put("maximum", this.popupModel.get("maximumValue"));
//        this.termNominalInterestRateByLoanCycleValue.add(item);
//        target.add(this.termNominalInterestRateByLoanCycleBlock);
//    }
//
//    protected ItemPanel termPrincipalByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
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
//    protected void termPrincipalByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.termPrincipalByLoanCycleValue.size(); i++) {
//            Map<String, Object> column = this.termPrincipalByLoanCycleValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.termPrincipalByLoanCycleValue.remove(index);
//        }
//        target.add(this.termPrincipalByLoanCycleTable);
//    }
//
//    protected List<ActionItem> termPrincipalByLoanCycleAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean termLinkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
//        boolean notLinked = this.termLinkedToFloatingInterestRatesValue == null ? true : !this.termLinkedToFloatingInterestRatesValue;
//        this.termNominalInterestRateMinimumVContainer.setVisible(notLinked);
//        this.termNominalInterestRateDefaultVContainer.setVisible(notLinked);
//        this.termNominalInterestRateMaximumVContainer.setVisible(notLinked);
//        this.termNominalInterestRateTypeVContainer.setVisible(notLinked);
//
//        this.termFloatingInterestRateVContainer.setVisible(!notLinked);
//        this.termFloatingInterestDifferentialVContainer.setVisible(!notLinked);
//        this.termFloatingInterestAllowedVContainer.setVisible(!notLinked);
//        this.termFloatingInterestMinimumVContainer.setVisible(!notLinked);
//        this.termFloatingInterestDefaultVContainer.setVisible(!notLinked);
//        this.termFloatingInterestMaximumVContainer.setVisible(!notLinked);
//
//        target.add(this.termNominalInterestRateMinimumBlock);
//        target.add(this.termNominalInterestRateDefaultBlock);
//        target.add(this.termNominalInterestRateMaximumBlock);
//        target.add(this.termNominalInterestRateTypeBlock);
//        target.add(this.termFloatingInterestRateBlock);
//        target.add(this.termFloatingInterestDifferentialBlock);
//        target.add(this.termFloatingInterestAllowedBlock);
//        target.add(this.termFloatingInterestMinimumBlock);
//        target.add(this.termFloatingInterestDefaultBlock);
//        target.add(this.termFloatingInterestMaximumBlock);
//        return false;
//    }
//
//    protected boolean settingInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
//        InterestCalculationPeriod interestCalculationPeriod = null;
//        if (this.settingInterestCalculationPeriodValue != null) {
//            interestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
//        }
//
//        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment;
//
//        if (interestCalculationPeriod == InterestCalculationPeriod.Daily) {
//            this.interestRecalculationRecalculateInterestVContainer.setVisible(true);
//        } else if (interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
//            this.interestRecalculationRecalculateInterestVContainer.setVisible(false);
//        }
//
//        this.settingCalculateInterestForExactDaysInPartialPeriodVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
//            target.add(this.interestRecalculationRecalculateInterestBlock);
//        }
//
//        interestRecalculationRecalculateInterestFieldUpdate(target);
//
//        initSectionValidationRule();
//
//        return false;
//    }
//
//    protected boolean termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
//        this.termPrincipalByLoanCycleVContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
//        this.termNumberOfRepaymentByLoanCycleVContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
//        this.termNominalInterestRateByLoanCycleVContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
//        if (target != null) {
//            target.add(this.termPrincipalByLoanCycleBlock);
//            target.add(this.termNumberOfRepaymentByLoanCycleBlock);
//            target.add(this.termNominalInterestRateByLoanCycleBlock);
//        }
//        return false;
//    }
//
//    protected boolean settingVariableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.settingVariableInstallmentsAllowedValue != null && this.settingVariableInstallmentsAllowedValue;
//        this.settingVariableInstallmentsMinimumVContainer.setVisible(visible);
//        this.settingVariableInstallmentsMaximumVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.settingVariableInstallmentsMinimumBlock);
//            target.add(this.settingVariableInstallmentsMaximumBlock);
//        }
//        return false;
//    }
//
//    protected void termNumberOfRepaymentByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("when", this.popupModel.get("whenValue"));
//        item.put("cycle", this.popupModel.get("loanCycleValue"));
//        item.put("minimum", this.popupModel.get("minimumValue"));
//        item.put("default", this.popupModel.get("defaultValue"));
//        item.put("maximum", this.popupModel.get("maximumValue"));
//        this.termNumberOfRepaymentByLoanCycleValue.add(item);
//        target.add(this.termNumberOfRepaymentByLoanCycleBlock);
//    }
//
//    protected void termPrincipalByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("when", this.popupModel.get("whenValue"));
//        item.put("cycle", this.popupModel.get("loanCycleValue"));
//        item.put("minimum", this.popupModel.get("minimumValue"));
//        item.put("default", this.popupModel.get("defaultValue"));
//        item.put("maximum", this.popupModel.get("maximumValue"));
//        this.termPrincipalByLoanCycleValue.add(item);
//        target.add(this.termPrincipalByLoanCycleBlock);
//    }
//
//    protected void fundSourcePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("payment", this.popupModel.get("paymentValue"));
//        item.put("account", this.popupModel.get("accountValue"));
//        this.advancedAccountingRuleFundSourceValue.add(item);
//        target.add(this.advancedAccountingRuleFundSourceTable);
//    }
//
//    protected void feeIncomePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("charge", this.popupModel.get("chargeValue"));
//        item.put("account", this.popupModel.get("accountValue"));
//        this.advancedAccountingRuleFeeIncomeValue.add(item);
//        target.add(this.advancedAccountingRuleFeeIncomeTable);
//    }
//
//    protected void penaltyIncomePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> item = Maps.newHashMap();
//        item.put("uuid", generator.externalId());
//        item.put("charge", this.popupModel.get("chargeValue"));
//        item.put("account", this.popupModel.get("accountValue"));
//        this.advancedAccountingRulePenaltyIncomeValue.add(item);
//        target.add(this.advancedAccountingRulePenaltyIncomeTable);
//    }
//
//    protected void overdueChargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        Map<String, Object> item = Maps.newHashMap();
//        Option charge = (Option) this.popupModel.get("overdueChargeValue");
//        for (Map<String, Object> temp : this.overdueChargeValue) {
//            if (charge.getId().equals(temp.get("uuid"))) {
//                return;
//            }
//        }
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, charge_calculation_enum, charge_time_enum, amount from m_charge where id = ?", charge.getId());
//        Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
//        Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
//        item.put("uuid", charge.getId());
//        item.put("charge", charge);
//        item.put("name", chargeObject.get("name"));
//        item.put("type", type);
//        item.put("amount", chargeObject.get("amount"));
//        item.put("collect", collect);
//        item.put("date", "");
//        this.overdueChargeValue.add(item);
//        target.add(this.overdueChargeTable);
//    }
//
//    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        Map<String, Object> item = Maps.newHashMap();
//        Option charge = (Option) this.popupModel.get("chargeValue");
//        for (Map<String, Object> temp : this.chargeValue) {
//            if (charge.getId().equals(temp.get("uuid"))) {
//                return;
//            }
//        }
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, charge_calculation_enum, charge_time_enum, amount from m_charge where id = ?", charge.getId());
//        Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
//        Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
//        item.put("uuid", charge.getId());
//        item.put("charge", charge);
//        item.put("name", chargeObject.get("name"));
//        item.put("type", type);
//        item.put("amount", chargeObject.get("amount"));
//        item.put("collect", collect);
//        item.put("date", "");
//        this.chargeValue.add(item);
//        target.add(this.chargeTable);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        ProductLoanBuilder builder = new ProductLoanBuilder();
//
//        // Detail
//        builder.withName(this.detailProductNameValue);
//        builder.withShortName(this.detailShortNameValue);
//        builder.withDescription(this.detailDescriptionValue);
//        if (this.detailFundValue != null) {
//            builder.withFundId(this.detailFundValue.getId());
//        }
//        builder.withIncludeInBorrowerCycle(this.detailIncludeInCustomerLoanCounterValue);
//        builder.withStartDate(this.detailStartDateValue);
//        builder.withCloseDate(this.detailCloseDateValue);
//
//        // Currency
//        if (this.currencyCodeValue != null) {
//            builder.withCurrencyCode(this.currencyCodeValue.getId());
//        }
//        builder.withDigitsAfterDecimal(this.currencyDecimalPlaceValue);
//        builder.withInMultiplesOf(this.currencyInMultipleOfValue);
//        builder.withInstallmentAmountInMultiplesOf(this.currencyInstallmentInMultipleOfValue);
//
//        // Terms
//
//        boolean useBorrowerCycle = this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue;
//        builder.withUseBorrowerCycle(useBorrowerCycle);
//        builder.withMinPrincipal(this.termPrincipalMinimumValue);
//        builder.withPrincipal(this.termPrincipalDefaultValue);
//        builder.withMaxPrincipal(this.termPrincipalMaximumValue);
//        builder.withMinNumberOfRepayments(this.termNumberOfRepaymentMinimumValue);
//        builder.withNumberOfRepayments(this.termNumberOfRepaymentDefaultValue);
//        builder.withMaxNumberOfRepayments(this.termNumberOfRepaymentMaximumValue);
//
//        boolean termLinkedToFloatingInterestRates = this.termLinkedToFloatingInterestRatesValue == null ? false : this.termLinkedToFloatingInterestRatesValue;
//        builder.withLinkedToFloatingInterestRates(termLinkedToFloatingInterestRates);
//
//        if (termLinkedToFloatingInterestRates) {
//            builder.withMinDifferentialLendingRate(this.termFloatingInterestMinimumValue);
//            builder.withDefaultDifferentialLendingRate(this.termFloatingInterestDefaultValue);
//            builder.withMaxDifferentialLendingRate(this.termFloatingInterestMaximumValue);
//            if (this.termFloatingInterestRateValue != null) {
//                builder.withFloatingRatesId(this.termFloatingInterestRateValue.getId());
//            }
//            builder.withInterestRateDifferential(this.termFloatingInterestDifferentialValue);
//            builder.withFloatingInterestRateCalculationAllowed(this.termFloatingInterestAllowedValue == null ? false : this.termFloatingInterestAllowedValue);
//        } else {
//            builder.withMinInterestRatePerPeriod(this.termNominalInterestRateMinimumValue);
//            builder.withInterestRatePerPeriod(this.termNominalInterestRateDefaultValue);
//            builder.withMaxInterestRatePerPeriod(this.termNominalInterestRateMaximumValue);
//            if (this.termNominalInterestRateTypeValue != null) {
//                builder.withInterestRateFrequencyType(NominalInterestRateType.valueOf(this.termNominalInterestRateTypeValue.getId()));
//            }
//        }
//
//        if (useBorrowerCycle) {
//            if (this.termPrincipalByLoanCycleValue != null) {
//                for (Map<String, Object> item : this.termPrincipalByLoanCycleValue) {
//                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
//                    Long borrowerCycleNumber = (Long) item.get("cycle");
//                    Double minValue = (Double) item.get("minimum");
//                    Double defaultValue = (Double) item.get("default");
//                    Double maxValue = (Double) item.get("maximum");
//                    builder.withPrincipalVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
//                }
//            }
//            if (this.termNumberOfRepaymentByLoanCycleValue != null) {
//                for (Map<String, Object> item : this.termNumberOfRepaymentByLoanCycleValue) {
//                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
//                    Long borrowerCycleNumber = (Long) item.get("cycle");
//                    Double minValue = (Double) item.get("minimum");
//                    Double defaultValue = (Double) item.get("default");
//                    Double maxValue = (Double) item.get("maximum");
//                    builder.withNumberOfRepaymentVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
//                }
//            }
//            if (this.termNominalInterestRateByLoanCycleValue != null) {
//                for (Map<String, Object> item : this.termNominalInterestRateByLoanCycleValue) {
//                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
//                    Long borrowerCycleNumber = (Long) item.get("cycle");
//                    Double minValue = (Double) item.get("minimum");
//                    Double defaultValue = (Double) item.get("default");
//                    Double maxValue = (Double) item.get("maximum");
//                    builder.withInterestRateVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
//                }
//            }
//        }
//
//        builder.withRepaymentEvery(this.termRepaidEveryValue);
//        if (this.termRepaidTypeValue != null) {
//            builder.withRepaymentFrequencyType(LockInType.valueOf(this.termRepaidTypeValue.getId()));
//        }
//        builder.withMinimumDaysBetweenDisbursalAndFirstRepayment(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue);
//
//        // Settings
//
//        if (this.settingAmortizationValue != null) {
//            builder.withAmortizationType(Amortization.valueOf(this.settingAmortizationValue.getId()));
//        }
//        if (this.settingInterestMethodValue != null) {
//            builder.withInterestType(InterestMethod.valueOf(this.settingInterestMethodValue.getId()));
//        }
//        if (this.settingInterestCalculationPeriodValue != null) {
//            InterestCalculationPeriod settingInterestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
//            builder.withInterestCalculationPeriodType(settingInterestCalculationPeriod);
//            if (settingInterestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
//                builder.withAllowPartialPeriodInterestCalcualtion(this.settingCalculateInterestForExactDaysInPartialPeriodValue == null ? false : this.settingCalculateInterestForExactDaysInPartialPeriodValue);
//            }
//            if (settingInterestCalculationPeriod == InterestCalculationPeriod.Daily) {
//
//                boolean interestRecalculationEnabled = this.interestRecalculationRecalculateInterestValue == null ? false : this.interestRecalculationRecalculateInterestValue;
//                builder.withInterestRecalculationEnabled(interestRecalculationEnabled);
//                if (interestRecalculationEnabled) {
//                    if (this.interestRecalculationPreClosureInterestCalculationRuleValue != null) {
//                        builder.withPreClosureInterestCalculationStrategy(ClosureInterestCalculationRule.valueOf(this.interestRecalculationPreClosureInterestCalculationRuleValue.getId()));
//                    }
//                    if (this.interestRecalculationAdvancePaymentsAdjustmentTypeValue != null) {
//                        builder.withRescheduleStrategyMethod(AdvancePaymentsAdjustmentType.valueOf(this.interestRecalculationAdvancePaymentsAdjustmentTypeValue.getId()));
//                    }
//
//                    if (this.interestRecalculationCompoundingOnValue != null) {
//                        InterestRecalculationCompound interestRecalculationCompound = InterestRecalculationCompound.valueOf(this.interestRecalculationCompoundingOnValue.getId());
//                        builder.withInterestRecalculationCompoundingMethod(interestRecalculationCompound);
//
//                        if (interestRecalculationCompound == InterestRecalculationCompound.Fee || interestRecalculationCompound == InterestRecalculationCompound.Interest || interestRecalculationCompound == InterestRecalculationCompound.FeeAndInterest) {
//                            if (this.interestRecalculationCompoundingValue != null) {
//                                Frequency compoundingValue = Frequency.valueOf(this.interestRecalculationCompoundingValue.getId());
//                                builder.withRecalculationCompoundingFrequencyType(compoundingValue);
//                                if (compoundingValue == Frequency.Daily) {
//                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
//                                } else if (compoundingValue == Frequency.Weekly) {
//                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
//                                    if (this.interestRecalculationCompoundingDayValue != null) {
//                                        builder.withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationCompoundingDayValue.getId()));
//                                    }
//                                } else if (compoundingValue == Frequency.Monthly) {
//                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
//                                    if (this.interestRecalculationCompoundingTypeValue != null) {
//                                        builder.withRecalculationCompoundingFrequencyNthDayType(FrequencyType.valueOf(this.interestRecalculationCompoundingTypeValue.getId()));
//                                    }
//                                    if (this.interestRecalculationCompoundingDayValue != null) {
//                                        builder.withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationCompoundingDayValue.getId()));
//                                    }
//                                }
//                            }
//                        }
//
//                        if (this.interestRecalculationRecalculateValue != null) {
//                            Frequency recalculateValue = Frequency.valueOf(this.interestRecalculationRecalculateValue.getId());
//                            builder.withRecalculationRestFrequencyType(recalculateValue);
//                            if (recalculateValue == Frequency.Daily) {
//                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
//                            } else if (recalculateValue == Frequency.Weekly) {
//                                if (this.interestRecalculationRecalculateDayValue != null) {
//                                    builder.withRecalculationRestFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId()));
//                                }
//                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
//                            } else if (recalculateValue == Frequency.Monthly) {
//                                if (this.interestRecalculationRecalculateTypeValue != null) {
//                                    builder.withRecalculationRestFrequencyNthDayType(FrequencyType.valueOf(this.interestRecalculationRecalculateTypeValue.getId()));
//                                }
//                                if (this.interestRecalculationRecalculateDayValue != null) {
//                                    builder.withRecalculationRestFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId()));
//                                }
//                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
//                            }
//                        }
//                        builder.withArrearsBasedOnOriginalSchedule(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue == null ? false : this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue);
//                    }
//
//                }
//            } else if (settingInterestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
//                builder.withInterestRecalculationEnabled(false);
//            }
//        }
//        builder.withGraceOnPrincipalPayment(this.settingMoratoriumPrincipalValue);
//        builder.withGraceOnInterestPayment(this.settingMoratoriumInterestValue);
//        builder.withGraceOnInterestCharged(this.settingInterestFreePeriodValue);
//        builder.withInArrearsTolerance(this.settingArrearsToleranceValue);
//
//        builder.withCanDefineInstallmentAmount(this.settingAllowFixingOfTheInstallmentAmountValue == null ? false : this.settingAllowFixingOfTheInstallmentAmountValue);
//
//        builder.withGraceOnArrearsAgeing(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue);
//
//        builder.withOverdueDaysForNPA(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue);
//
//        builder.withMinimumGap(this.settingVariableInstallmentsMinimumValue);
//        builder.withMaximumGap(this.settingVariableInstallmentsMaximumValue);
//        builder.withAccountMovesOutOfNPAOnlyOnArrearsCompletion(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue == null ? false : this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue);
//        builder.withAllowVariableInstallments(this.settingVariableInstallmentsAllowedValue == null ? false : this.settingVariableInstallmentsAllowedValue);
//        builder.withCanUseForTopup(this.settingAllowedToBeUsedForProvidingTopupLoansValue == null ? false : this.settingAllowedToBeUsedForProvidingTopupLoansValue);
//
//        if (this.settingRepaymentStrategyValue != null) {
//            builder.withTransactionProcessingStrategyId(RepaymentStrategy.valueOf(this.settingRepaymentStrategyValue.getId()));
//        }
//
//        if (this.settingDayInYearValue != null) {
//            builder.withDaysInYearType(DayInYear.valueOf(this.settingDayInYearValue.getId()));
//        }
//
//        if (this.settingDayInMonthValue != null) {
//            builder.withDaysInMonthType(DayInMonth.valueOf(this.settingDayInMonthValue.getId()));
//        }
//
//        builder.withPrincipalThresholdForLastInstallment(this.settingPrincipalThresholdForLastInstalmentValue);
//
//        // Guarantee Requirements
//
//        boolean holdGuaranteeFunds = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue == null ? false : this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
//        builder.withHoldGuaranteeFunds(holdGuaranteeFunds);
//        if (holdGuaranteeFunds) {
//            builder.withMandatoryGuarantee(this.guaranteeRequirementMandatoryGuaranteeValue);
//            builder.withMinimumGuaranteeFromGuarantor(this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue);
//            builder.withMinimumGuaranteeFromOwnFunds(this.guaranteeRequirementMinimumGuaranteeValue);
//        }
//
//        // Loan Tranche Details
//
//        boolean multiDisburseLoan = this.loanTrancheDetailEnableMultipleDisbursalValue == null ? false : this.loanTrancheDetailEnableMultipleDisbursalValue;
//        builder.withMultiDisburseLoan(multiDisburseLoan);
//        if (multiDisburseLoan) {
//            builder.withOutstandingLoanBalance(this.loanTrancheDetailMaximumAllowedOutstandingBalanceValue);
//            builder.withMaxTrancheCount(this.loanTrancheDetailMaximumTrancheCountValue);
//        }
//
//        // Configurable Terms and Settings
//        boolean configurable = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue == null ? false : this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
//        AllowAttributeOverrideBuilder allowAttributeOverrideBuilder = new AllowAttributeOverrideBuilder();
//        if (configurable) {
//            allowAttributeOverrideBuilder.withAmortizationType(this.configurableAmortizationValue == null ? false : this.configurableAmortizationValue);
//            allowAttributeOverrideBuilder.withTransactionProcessingStrategyId(this.configurableRepaymentStrategyValue == null ? false : this.configurableRepaymentStrategyValue);
//            allowAttributeOverrideBuilder.withInArrearsTolerance(this.configurableArrearsToleranceValue == null ? false : this.configurableArrearsToleranceValue);
//            allowAttributeOverrideBuilder.withGraceOnPrincipalAndInterestPayment(this.configurableMoratoriumValue == null ? false : this.configurableMoratoriumValue);
//            allowAttributeOverrideBuilder.withInterestType(this.configurableInterestMethodValue == null ? false : this.configurableInterestMethodValue);
//            allowAttributeOverrideBuilder.withInterestCalculationPeriodType(this.configurableInterestCalculationPeriodValue == null ? false : this.configurableInterestCalculationPeriodValue);
//            allowAttributeOverrideBuilder.withRepaymentEvery(this.configurableRepaidEveryValue == null ? false : this.configurableRepaidEveryValue);
//            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(this.configurableOverdueBeforeMovingValue == null ? false : this.configurableOverdueBeforeMovingValue);
//        } else {
//            allowAttributeOverrideBuilder.withAmortizationType(false);
//            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(false);
//            allowAttributeOverrideBuilder.withGraceOnPrincipalAndInterestPayment(false);
//            allowAttributeOverrideBuilder.withInArrearsTolerance(false);
//            allowAttributeOverrideBuilder.withInterestCalculationPeriodType(false);
//            allowAttributeOverrideBuilder.withInterestType(false);
//            allowAttributeOverrideBuilder.withRepaymentEvery(false);
//            allowAttributeOverrideBuilder.withTransactionProcessingStrategyId(false);
//        }
//        JsonNode allowAttributeOverrides = allowAttributeOverrideBuilder.build();
//        builder.withAllowAttributeOverrides(allowAttributeOverrides.getObject());
//
//        // Charge
//
//        if (this.chargeValue != null && !this.chargeValue.isEmpty()) {
//            for (Map<String, Object> item : this.chargeValue) {
//                Option charge = (Option) item.get("charge");
//                builder.withCharges(charge.getId());
//            }
//        }
//
//        // Overdue Charge
//
//        if (this.overdueChargeValue != null && !this.overdueChargeValue.isEmpty()) {
//            for (Map<String, Object> item : this.overdueChargeValue) {
//                Option charge = (Option) item.get("charge");
//                builder.withCharges(charge.getId());
//            }
//        }
//
//        // Accounting
//
//        String accounting = this.accountingValue;
//
//        if (AccountingType.None.getDescription().equals(accounting)) {
//            builder.withAccountingRule(AccountingType.None);
//        } else if (AccountingType.Cash.getDescription().equals(accounting)) {
//            builder.withAccountingRule(AccountingType.Cash);
//        } else if (AccountingType.Periodic.getDescription().equals(accounting)) {
//            builder.withAccountingRule(AccountingType.Periodic);
//        } else if (AccountingType.Upfront.getDescription().equals(accounting)) {
//            builder.withAccountingRule(AccountingType.Upfront);
//        }
//        if (AccountingType.Cash.getDescription().equals(accounting)) {
//            if (this.cashFundSourceValue != null) {
//                builder.withFundSourceAccountId(this.cashFundSourceValue.getId());
//            }
//            if (this.cashLoanPortfolioValue != null) {
//                builder.withLoanPortfolioAccountId(this.cashLoanPortfolioValue.getId());
//            }
//            if (this.cashTransferInSuspenseValue != null) {
//                builder.withTransfersInSuspenseAccountId(this.cashTransferInSuspenseValue.getId());
//            }
//            if (this.cashIncomeFromInterestValue != null) {
//                builder.withInterestOnLoanAccountId(this.cashIncomeFromInterestValue.getId());
//            }
//            if (this.cashIncomeFromFeeValue != null) {
//                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
//            }
//            if (this.cashIncomeFromPenaltyValue != null) {
//                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
//            }
//            if (this.cashIncomeFromRecoveryRepaymentValue != null) {
//                builder.withIncomeFromRecoveryAccountId(this.cashIncomeFromRecoveryRepaymentValue.getId());
//            }
//            if (this.cashLossWrittenOffValue != null) {
//                builder.withWriteOffAccountId(this.cashLossWrittenOffValue.getId());
//            }
//            if (this.cashOverPaymentLiabilityValue != null) {
//                builder.withOverpaymentLiabilityAccountId(this.cashOverPaymentLiabilityValue.getId());
//            }
//        } else if (AccountingType.Periodic.getDescription().equals(accounting)) {
//            if (this.periodicFundSourceValue != null) {
//                builder.withFundSourceAccountId(this.periodicFundSourceValue.getId());
//            }
//            if (this.periodicLoanPortfolioValue != null) {
//                builder.withLoanPortfolioAccountId(this.periodicLoanPortfolioValue.getId());
//            }
//            if (this.periodicTransferInSuspenseValue != null) {
//                builder.withTransfersInSuspenseAccountId(this.periodicTransferInSuspenseValue.getId());
//            }
//            if (this.periodicIncomeFromInterestValue != null) {
//                builder.withInterestOnLoanAccountId(this.periodicIncomeFromInterestValue.getId());
//            }
//            if (this.periodicIncomeFromFeeValue != null) {
//                builder.withIncomeFromFeeAccountId(this.periodicIncomeFromFeeValue.getId());
//            }
//            if (this.periodicIncomeFromPenaltyValue != null) {
//                builder.withIncomeFromPenaltyAccountId(this.periodicIncomeFromPenaltyValue.getId());
//            }
//            if (this.periodicIncomeFromRecoveryRepaymentValue != null) {
//                builder.withIncomeFromRecoveryAccountId(this.periodicIncomeFromRecoveryRepaymentValue.getId());
//            }
//            if (this.periodicLossWrittenOffValue != null) {
//                builder.withWriteOffAccountId(this.periodicLossWrittenOffValue.getId());
//            }
//            if (this.periodicOverPaymentLiabilityValue != null) {
//                builder.withOverpaymentLiabilityAccountId(this.periodicOverPaymentLiabilityValue.getId());
//            }
//            if (this.periodicInterestReceivableValue != null) {
//                builder.withReceivableInterestAccountId(this.periodicInterestReceivableValue.getId());
//            }
//            if (this.periodicFeeReceivableValue != null) {
//                builder.withReceivableFeeAccountId(this.periodicFeeReceivableValue.getId());
//            }
//            if (this.periodicPenaltyReceivableValue != null) {
//                builder.withReceivablePenaltyAccountId(this.periodicPenaltyReceivableValue.getId());
//            }
//        } else if (AccountingType.Upfront.getDescription().equals(accounting)) {
//            if (this.upfrontFundSourceValue != null) {
//                builder.withFundSourceAccountId(this.upfrontFundSourceValue.getId());
//            }
//            if (this.upfrontLoanPortfolioValue != null) {
//                builder.withLoanPortfolioAccountId(this.upfrontLoanPortfolioValue.getId());
//            }
//            if (this.upfrontTransferInSuspenseValue != null) {
//                builder.withTransfersInSuspenseAccountId(this.upfrontTransferInSuspenseValue.getId());
//            }
//            if (this.upfrontIncomeFromInterestValue != null) {
//                builder.withInterestOnLoanAccountId(this.upfrontIncomeFromInterestValue.getId());
//            }
//            if (this.upfrontIncomeFromFeeValue != null) {
//                builder.withIncomeFromFeeAccountId(this.upfrontIncomeFromFeeValue.getId());
//            }
//            if (this.upfrontIncomeFromPenaltyValue != null) {
//                builder.withIncomeFromPenaltyAccountId(this.upfrontIncomeFromPenaltyValue.getId());
//            }
//            if (this.upfrontIncomeFromRecoveryRepaymentValue != null) {
//                builder.withIncomeFromRecoveryAccountId(this.upfrontIncomeFromRecoveryRepaymentValue.getId());
//            }
//            if (this.upfrontLossWrittenOffValue != null) {
//                builder.withWriteOffAccountId(this.upfrontLossWrittenOffValue.getId());
//            }
//            if (this.upfrontOverPaymentLiabilityValue != null) {
//                builder.withOverpaymentLiabilityAccountId(this.upfrontOverPaymentLiabilityValue.getId());
//            }
//            if (this.upfrontInterestReceivableValue != null) {
//                builder.withReceivableInterestAccountId(this.upfrontInterestReceivableValue.getId());
//            }
//            if (this.upfrontFeeReceivableValue != null) {
//                builder.withReceivableFeeAccountId(this.upfrontFeeReceivableValue.getId());
//            }
//            if (this.upfrontPenaltyReceivableValue != null) {
//                builder.withReceivablePenaltyAccountId(this.upfrontPenaltyReceivableValue.getId());
//            }
//        }
//
//        if (AccountingType.Cash.getDescription().equals(accounting) || AccountingType.Periodic.getDescription().equals(accounting) || AccountingType.Upfront.getDescription().equals(accounting)) {
//            if (this.advancedAccountingRuleFundSourceValue != null && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
//                for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
//                    Option payment = (Option) item.get("payment");
//                    Option account = (Option) item.get("account");
//                    builder.withPaymentChannelToFundSourceMappings(payment.getId(), account.getId());
//                }
//            }
//            if (this.advancedAccountingRuleFeeIncomeValue != null && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
//                for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
//                    Option charge = (Option) item.get("charge");
//                    Option account = (Option) item.get("account");
//                    builder.withFeeToIncomeAccountMappings(charge.getId(), account.getId());
//                }
//            }
//            if (this.advancedAccountingRulePenaltyIncomeValue != null && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
//                for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
//                    Option charge = (Option) item.get("charge");
//                    Option account = (Option) item.get("account");
//                    builder.withPenaltyToIncomeAccountMappings(charge.getId(), account.getId());
//                }
//            }
//        }
//
//        JsonNode node = null;
//        try {
//            node = LoanHelper.create((Session) getSession(), builder.build());
//        } catch (UnirestException e) {
//            error(e.getMessage());
//            return;
//        }
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(LoanBrowsePage.class);
//    }
//
//}
