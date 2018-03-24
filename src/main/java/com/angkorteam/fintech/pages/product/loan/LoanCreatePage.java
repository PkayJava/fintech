package com.angkorteam.fintech.pages.product.loan;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MFloatingRates;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AllowAttributeOverrideBuilder;
import com.angkorteam.fintech.dto.builder.ProductLoanBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.dto.enums.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.dto.enums.loan.DayInMonth;
import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.dto.enums.loan.WhenType;
import com.angkorteam.fintech.helper.LoanHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.InterestLoanCyclePopup;
import com.angkorteam.fintech.popup.OverdueChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.popup.PrincipleLoanCyclePopup;
import com.angkorteam.fintech.popup.RepaymentLoanCyclePopup;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.loan.AdvancePaymentsAdjustmentTypeProvider;
import com.angkorteam.fintech.provider.loan.AmortizationProvider;
import com.angkorteam.fintech.provider.loan.ClosureInterestCalculationRuleProvider;
import com.angkorteam.fintech.provider.loan.DayInMonthProvider;
import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
import com.angkorteam.fintech.provider.loan.FrequencyProvider;
import com.angkorteam.fintech.provider.loan.FrequencyTypeProvider;
import com.angkorteam.fintech.provider.loan.InterestCalculationPeriodProvider;
import com.angkorteam.fintech.provider.loan.InterestMethodProvider;
import com.angkorteam.fintech.provider.loan.InterestRecalculationCompoundProvider;
import com.angkorteam.fintech.provider.loan.OnDayProvider;
import com.angkorteam.fintech.provider.loan.RepaymentStrategyProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected String detailProductNameValue;
    protected String detailShortNameValue;
    protected String detailDescriptionValue;
    protected Option detailFundValue;
    protected Date detailStartDateValue;
    protected Date detailCloseDateValue;
    protected Boolean detailIncludeInCustomerLoanCounterValue;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeIContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceIContainer;
    protected Long currencyDecimalPlaceValue;
    protected TextField<Long> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupBlock currencyInMultipleOfBlock;
    protected WebMarkupContainer currencyInMultipleOfIContainer;
    protected Long currencyInMultipleOfValue;
    protected TextField<Long> currencyInMultipleOfField;
    protected TextFeedbackPanel currencyInMultipleOfFeedback;

    protected WebMarkupBlock currencyInstallmentInMultipleOfBlock;
    protected WebMarkupContainer currencyInstallmentInMultipleOfIContainer;
    protected Long currencyInstallmentInMultipleOfValue;
    protected TextField<Long> currencyInstallmentInMultipleOfField;
    protected TextFeedbackPanel currencyInstallmentInMultipleOfFeedback;

    // Terms

    // Row 1 : Terms vary based on loan cycle
    protected WebMarkupBlock termVaryBasedOnLoanCycleBlock;
    protected WebMarkupContainer termVaryBasedOnLoanCycleIContainer;
    protected Boolean termVaryBasedOnLoanCycleValue;
    protected CheckBox termVaryBasedOnLoanCycleField;
    protected TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    // Row 2 : Principle
    protected WebMarkupBlock termPrincipleMinimumBlock;
    protected WebMarkupContainer termPrincipleMinimumIContainer;
    protected Double termPrincipleMinimumValue;
    protected TextField<Double> termPrincipleMinimumField;
    protected TextFeedbackPanel termPrincipleMinimumFeedback;

    protected WebMarkupBlock termPrincipleDefaultBlock;
    protected WebMarkupContainer termPrincipleDefaultIContainer;
    protected Double termPrincipleDefaultValue;
    protected TextField<Double> termPrincipleDefaultField;
    protected TextFeedbackPanel termPrincipleDefaultFeedback;

    protected WebMarkupBlock termPrincipleMaximumBlock;
    protected WebMarkupContainer termPrincipleMaximumIContainer;
    protected Double termPrincipleMaximumValue;
    protected TextField<Double> termPrincipleMaximumField;
    protected TextFeedbackPanel termPrincipleMaximumFeedback;

    // Row 2 (Optional) : Principle by loan cycle
    protected WebMarkupBlock termPrincipleByLoanCycleBlock;
    protected WebMarkupContainer termPrincipleByLoanCycleIContainer;
    protected List<Map<String, Object>> termPrincipleByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termPrincipleByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termPrincipleByLoanCycleColumn;
    protected ListDataProvider termPrincipleByLoanCycleProvider;
    protected AjaxLink<Void> termPrincipleByLoanCycleAddLink;
    protected ModalWindow termPrincipleByLoanCyclePopup;

    // Row 3 : Number of repayments
    protected WebMarkupBlock termNumberOfRepaymentMinimumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMinimumIContainer;
    protected Long termNumberOfRepaymentMinimumValue;
    protected TextField<Long> termNumberOfRepaymentMinimumField;
    protected TextFeedbackPanel termNumberOfRepaymentMinimumFeedback;

    protected WebMarkupBlock termNumberOfRepaymentDefaultBlock;
    protected WebMarkupContainer termNumberOfRepaymentDefaultIContainer;
    protected Long termNumberOfRepaymentDefaultValue;
    protected TextField<Long> termNumberOfRepaymentDefaultField;
    protected TextFeedbackPanel termNumberOfRepaymentDefaultFeedback;

    protected WebMarkupBlock termNumberOfRepaymentMaximumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMaximumIContainer;
    protected Long termNumberOfRepaymentMaximumValue;
    protected TextField<Long> termNumberOfRepaymentMaximumField;
    protected TextFeedbackPanel termNumberOfRepaymentMaximumFeedback;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
    protected WebMarkupBlock termNumberOfRepaymentByLoanCycleBlock;
    protected WebMarkupContainer termNumberOfRepaymentByLoanCycleIContainer;
    protected List<Map<String, Object>> termNumberOfRepaymentByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
    protected AjaxLink<Void> termNumberOfRepaymentByLoanCycleAddLink;
    protected ModalWindow termNumberOfRepaymentByLoanCyclePopup;

    // Row 4 : Is Linked to Floating Interest Rates?
    protected WebMarkupBlock termLinkedToFloatingInterestRatesBlock;
    protected WebMarkupContainer termLinkedToFloatingInterestRatesIContainer;
    protected Boolean termLinkedToFloatingInterestRatesValue;
    protected CheckBox termLinkedToFloatingInterestRatesField;
    protected TextFeedbackPanel termLinkedToFloatingInterestRatesFeedback;

    // Row 5 : Nominal interest rate
    protected WebMarkupBlock termNominalInterestRateMinimumBlock;
    protected WebMarkupContainer termNominalInterestRateMinimumIContainer;
    protected Double termNominalInterestRateMinimumValue;
    protected TextField<Double> termNominalInterestRateMinimumField;
    protected TextFeedbackPanel termNominalInterestRateMinimumFeedback;

    protected WebMarkupBlock termNominalInterestRateDefaultBlock;
    protected WebMarkupContainer termNominalInterestRateDefaultIContainer;
    protected Double termNominalInterestRateDefaultValue;
    protected TextField<Double> termNominalInterestRateDefaultField;
    protected TextFeedbackPanel termNominalInterestRateDefaultFeedback;

    protected WebMarkupBlock termNominalInterestRateMaximumBlock;
    protected WebMarkupContainer termNominalInterestRateMaximumIContainer;
    protected Double termNominalInterestRateMaximumValue;
    protected TextField<Double> termNominalInterestRateMaximumField;
    protected TextFeedbackPanel termNominalInterestRateMaximumFeedback;

    protected WebMarkupBlock termNominalInterestRateTypeBlock;
    protected WebMarkupContainer termNominalInterestRateTypeIContainer;
    protected NominalInterestRateTypeProvider termNominalInterestRateTypeProvider;
    protected Option termNominalInterestRateTypeValue;
    protected Select2SingleChoice<Option> termNominalInterestRateTypeField;
    protected TextFeedbackPanel termNominalInterestRateTypeFeedback;

    protected WebMarkupBlock termFloatingInterestRateBlock;
    protected WebMarkupContainer termFloatingInterestRateIContainer;
    protected SingleChoiceProvider termFloatingInterestRateProvider;
    protected Option termFloatingInterestRateValue;
    protected Select2SingleChoice<Option> termFloatingInterestRateField;
    protected TextFeedbackPanel termFloatingInterestRateFeedback;

    protected WebMarkupBlock termFloatingInterestDifferentialBlock;
    protected WebMarkupContainer termFloatingInterestDifferentialIContainer;
    protected Double termFloatingInterestDifferentialValue;
    protected TextField<Double> termFloatingInterestDifferentialField;
    protected TextFeedbackPanel termFloatingInterestDifferentialFeedback;

    protected WebMarkupBlock termFloatingInterestAllowedBlock;
    protected WebMarkupContainer termFloatingInterestAllowedIContainer;
    protected Boolean termFloatingInterestAllowedValue;
    protected CheckBox termFloatingInterestAllowedField;
    protected TextFeedbackPanel termFloatingInterestAllowedFeedback;

    protected WebMarkupBlock termFloatingInterestMinimumBlock;
    protected WebMarkupContainer termFloatingInterestMinimumIContainer;
    protected Double termFloatingInterestMinimumValue;
    protected TextField<Double> termFloatingInterestMinimumField;
    protected TextFeedbackPanel termFloatingInterestMinimumFeedback;

    protected WebMarkupBlock termFloatingInterestDefaultBlock;
    protected WebMarkupContainer termFloatingInterestDefaultIContainer;
    protected Double termFloatingInterestDefaultValue;
    protected TextField<Double> termFloatingInterestDefaultField;
    protected TextFeedbackPanel termFloatingInterestDefaultFeedback;

    protected WebMarkupBlock termFloatingInterestMaximumBlock;
    protected WebMarkupContainer termFloatingInterestMaximumIContainer;
    protected Double termFloatingInterestMaximumValue;
    protected TextField<Double> termFloatingInterestMaximumField;
    protected TextFeedbackPanel termFloatingInterestMaximumFeedback;

    // Row 6
    protected WebMarkupBlock termNominalInterestRateByLoanCycleBlock;
    protected WebMarkupContainer termNominalInterestRateByLoanCycleIContainer;
    protected List<Map<String, Object>> termNominalInterestRateByLoanCycleValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
    protected AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
    protected ModalWindow termNominalInterestRateByLoanCyclePopup;

    protected WebMarkupBlock termRepaidEveryBlock;
    protected WebMarkupContainer termRepaidEveryIContainer;
    protected Long termRepaidEveryValue;
    protected TextField<Long> termRepaidEveryField;
    protected TextFeedbackPanel termRepaidEveryFeedback;

    protected WebMarkupBlock termRepaidTypeBlock;
    protected WebMarkupContainer termRepaidTypeIContainer;
    protected LockInTypeProvider termRepaidTypeProvider;
    protected Option termRepaidTypeValue;
    protected Select2SingleChoice<Option> termRepaidTypeField;
    protected TextFeedbackPanel termRepaidTypeFeedback;

    protected WebMarkupBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    protected WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer;
    protected Long termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue;
    protected TextField<Long> termMinimumDayBetweenDisbursalAndFirstRepaymentDateField;
    protected TextFeedbackPanel termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback;

    // Settings

    protected WebMarkupBlock settingAmortizationBlock;
    protected WebMarkupContainer settingAmortizationIContainer;
    protected AmortizationProvider settingAmortizationProvider;
    protected Option settingAmortizationValue;
    protected Select2SingleChoice<Option> settingAmortizationField;
    protected TextFeedbackPanel settingAmortizationFeedback;

    protected WebMarkupBlock settingInterestMethodBlock;
    protected WebMarkupContainer settingInterestMethodIContainer;
    protected InterestMethodProvider settingInterestMethodProvider;
    protected Option settingInterestMethodValue;
    protected Select2SingleChoice<Option> settingInterestMethodField;
    protected TextFeedbackPanel settingInterestMethodFeedback;

    protected WebMarkupBlock settingInterestCalculationPeriodBlock;
    protected WebMarkupContainer settingInterestCalculationPeriodIContainer;
    protected InterestCalculationPeriodProvider settingInterestCalculationPeriodProvider;
    protected Option settingInterestCalculationPeriodValue;
    protected Select2SingleChoice<Option> settingInterestCalculationPeriodField;
    protected TextFeedbackPanel settingInterestCalculationPeriodFeedback;

    protected WebMarkupBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
    protected WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodIContainer;
    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    protected CheckBox settingCalculateInterestForExactDaysInPartialPeriodField;
    protected TextFeedbackPanel settingCalculateInterestForExactDaysInPartialPeriodFeedback;

    protected WebMarkupBlock settingRepaymentStrategyBlock;
    protected WebMarkupContainer settingRepaymentStrategyIContainer;
    protected RepaymentStrategyProvider settingRepaymentStrategyProvider;
    protected Option settingRepaymentStrategyValue;
    protected Select2SingleChoice<Option> settingRepaymentStrategyField;
    protected TextFeedbackPanel settingRepaymentStrategyFeedback;

    protected WebMarkupBlock settingMoratoriumPrincipleBlock;
    protected WebMarkupContainer settingMoratoriumPrincipleIContainer;
    protected Long settingMoratoriumPrincipleValue;
    protected TextField<Long> settingMoratoriumPrincipleField;
    protected TextFeedbackPanel settingMoratoriumPrincipleFeedback;

    protected WebMarkupBlock settingMoratoriumInterestBlock;
    protected WebMarkupContainer settingMoratoriumInterestIContainer;
    protected Long settingMoratoriumInterestValue;
    protected TextField<Long> settingMoratoriumInterestField;
    protected TextFeedbackPanel settingMoratoriumInterestFeedback;

    protected WebMarkupBlock settingInterestFreePeriodBlock;
    protected WebMarkupContainer settingInterestFreePeriodIContainer;
    protected Long settingInterestFreePeriodValue;
    protected TextField<Long> settingInterestFreePeriodField;
    protected TextFeedbackPanel settingInterestFreePeriodFeedback;

    protected WebMarkupBlock settingArrearsToleranceBlock;
    protected WebMarkupContainer settingArrearsToleranceIContainer;
    protected Double settingArrearsToleranceValue;
    protected TextField<Double> settingArrearsToleranceField;
    protected TextFeedbackPanel settingArrearsToleranceFeedback;

    protected WebMarkupBlock settingDayInYearBlock;
    protected WebMarkupContainer settingDayInYearIContainer;
    protected DayInYearProvider settingDayInYearProvider;
    protected Option settingDayInYearValue;
    protected Select2SingleChoice<Option> settingDayInYearField;
    protected TextFeedbackPanel settingDayInYearFeedback;

    protected WebMarkupBlock settingDayInMonthBlock;
    protected WebMarkupContainer settingDayInMonthIContainer;
    protected DayInMonthProvider settingDayInMonthProvider;
    protected Option settingDayInMonthValue;
    protected Select2SingleChoice<Option> settingDayInMonthField;
    protected TextFeedbackPanel settingDayInMonthFeedback;

    protected WebMarkupBlock settingAllowFixingOfTheInstallmentAmountBlock;
    protected WebMarkupContainer settingAllowFixingOfTheInstallmentAmountIContainer;
    protected Boolean settingAllowFixingOfTheInstallmentAmountValue;
    protected CheckBox settingAllowFixingOfTheInstallmentAmountField;
    protected TextFeedbackPanel settingAllowFixingOfTheInstallmentAmountFeedback;

    protected WebMarkupBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    protected WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer;
    protected Long settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
    protected TextField<Long> settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField;
    protected TextFeedbackPanel settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback;

    protected WebMarkupBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    protected WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer;
    protected Long settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
    protected TextField<Long> settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField;
    protected TextFeedbackPanel settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback;

    protected WebMarkupBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    protected WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer;
    protected Boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
    protected CheckBox settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField;
    protected TextFeedbackPanel settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback;

    protected WebMarkupBlock settingPrincipleThresholdForLastInstalmentBlock;
    protected WebMarkupContainer settingPrincipleThresholdForLastInstalmentIContainer;
    protected Double settingPrincipleThresholdForLastInstalmentValue;
    protected TextField<Double> settingPrincipleThresholdForLastInstalmentField;
    protected TextFeedbackPanel settingPrincipleThresholdForLastInstalmentFeedback;

    protected WebMarkupBlock settingVariableInstallmentsAllowedBlock;
    protected WebMarkupContainer settingVariableInstallmentsAllowedIContainer;
    protected Boolean settingVariableInstallmentsAllowedValue;
    protected CheckBox settingVariableInstallmentsAllowedField;
    protected TextFeedbackPanel settingVariableInstallmentsAllowedFeedback;

    protected WebMarkupBlock settingVariableInstallmentsMinimumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMinimumIContainer;
    protected Long settingVariableInstallmentsMinimumValue;
    protected TextField<Long> settingVariableInstallmentsMinimumField;
    protected TextFeedbackPanel settingVariableInstallmentsMinimumFeedback;

    protected WebMarkupBlock settingVariableInstallmentsMaximumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMaximumIContainer;
    protected Long settingVariableInstallmentsMaximumValue;
    protected TextField<Long> settingVariableInstallmentsMaximumField;
    protected TextFeedbackPanel settingVariableInstallmentsMaximumFeedback;

    protected WebMarkupBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
    protected WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansIContainer;
    protected Boolean settingAllowedToBeUsedForProvidingTopupLoansValue;
    protected CheckBox settingAllowedToBeUsedForProvidingTopupLoansField;
    protected TextFeedbackPanel settingAllowedToBeUsedForProvidingTopupLoansFeedback;

    // Interest Recalculation

    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
    protected WebMarkupContainer interestRecalculationRecalculateInterestIContainer;
    protected Boolean interestRecalculationRecalculateInterestValue;
    protected CheckBox interestRecalculationRecalculateInterestField;
    protected TextFeedbackPanel interestRecalculationRecalculateInterestFeedback;

    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleIContainer;
    protected ClosureInterestCalculationRuleProvider interestRecalculationPreClosureInterestCalculationRuleProvider;
    protected Option interestRecalculationPreClosureInterestCalculationRuleValue;
    protected Select2SingleChoice<Option> interestRecalculationPreClosureInterestCalculationRuleField;
    protected TextFeedbackPanel interestRecalculationPreClosureInterestCalculationRuleFeedback;

    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeIContainer;
    protected AdvancePaymentsAdjustmentTypeProvider interestRecalculationAdvancePaymentsAdjustmentTypeProvider;
    protected Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
    protected Select2SingleChoice<Option> interestRecalculationAdvancePaymentsAdjustmentTypeField;
    protected TextFeedbackPanel interestRecalculationAdvancePaymentsAdjustmentTypeFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
    protected WebMarkupContainer interestRecalculationCompoundingOnIContainer;
    protected InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
    protected Option interestRecalculationCompoundingOnValue;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnField;
    protected TextFeedbackPanel interestRecalculationCompoundingOnFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIContainer;
    protected FrequencyProvider interestRecalculationCompoundingProvider;
    protected Option interestRecalculationCompoundingValue;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingField;
    protected TextFeedbackPanel interestRecalculationCompoundingFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingTypeBlock;
    protected WebMarkupContainer interestRecalculationCompoundingTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationCompoundingTypeProvider;
    protected Option interestRecalculationCompoundingTypeValue;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingTypeField;
    protected TextFeedbackPanel interestRecalculationCompoundingTypeFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingDayBlock;

    protected WebMarkupContainer interestRecalculationCompoundingDayIContainer;
    protected FrequencyDayProvider interestRecalculationCompoundingDayProvider;
    protected Option interestRecalculationCompoundingDayValue;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingDayField;
    protected TextFeedbackPanel interestRecalculationCompoundingDayFeedback;

    protected WebMarkupContainer interestRecalculationCompoundingOnDayIContainer;
    protected OnDayProvider interestRecalculationCompoundingOnDayProvider;
    protected Option interestRecalculationCompoundingOnDayValue;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnDayField;
    protected TextFeedbackPanel interestRecalculationCompoundingOnDayFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIntervalIContainer;
    protected Long interestRecalculationCompoundingIntervalValue;
    protected TextField<Long> interestRecalculationCompoundingIntervalField;
    protected TextFeedbackPanel interestRecalculationCompoundingIntervalFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIContainer;
    protected FrequencyProvider interestRecalculationRecalculateProvider;
    protected Option interestRecalculationRecalculateValue;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateField;
    protected TextFeedbackPanel interestRecalculationRecalculateFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateTypeBlock;
    protected WebMarkupContainer interestRecalculationRecalculateTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationRecalculateTypeProvider;
    protected Option interestRecalculationRecalculateTypeValue;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateTypeField;
    protected TextFeedbackPanel interestRecalculationRecalculateTypeFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateDayBlock;

    protected WebMarkupContainer interestRecalculationRecalculateDayIContainer;
    protected FrequencyDayProvider interestRecalculationRecalculateDayProvider;
    protected Option interestRecalculationRecalculateDayValue;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateDayField;
    protected TextFeedbackPanel interestRecalculationRecalculateDayFeedback;

    protected WebMarkupContainer interestRecalculationRecalculateOnDayIContainer;
    protected OnDayProvider interestRecalculationRecalculateOnDayProvider;
    protected Option interestRecalculationRecalculateOnDayValue;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateOnDayField;
    protected TextFeedbackPanel interestRecalculationRecalculateOnDayFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateIntervalBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIntervalIContainer;
    protected Long interestRecalculationRecalculateIntervalValue;
    protected TextField<Long> interestRecalculationRecalculateIntervalField;
    protected TextFeedbackPanel interestRecalculationRecalculateIntervalFeedback;

    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer;
    protected Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;
    protected CheckBox interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField;
    protected TextFeedbackPanel interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback;

    // Guarantee Requirements

    protected WebMarkupBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    protected WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer;
    protected Boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
    protected CheckBox guaranteeRequirementPlaceGuaranteeFundsOnHoldField;
    protected TextFeedbackPanel guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback;

    protected WebMarkupBlock guaranteeRequirementMandatoryGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMandatoryGuaranteeIContainer;
    protected Double guaranteeRequirementMandatoryGuaranteeValue;
    protected TextField<Double> guaranteeRequirementMandatoryGuaranteeField;
    protected TextFeedbackPanel guaranteeRequirementMandatoryGuaranteeFeedback;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeIContainer;
    protected Double guaranteeRequirementMinimumGuaranteeValue;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeField;
    protected TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFeedback;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer;
    protected Double guaranteeRequirementMinimumGuaranteeFromGuarantorValue;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeFromGuarantorField;
    protected TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback;

    // Loan Tranche Details

    protected WebMarkupBlock loanTrancheDetailEnableMultipleDisbursalBlock;
    protected WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalIContainer;
    protected Boolean loanTrancheDetailEnableMultipleDisbursalValue;
    protected CheckBox loanTrancheDetailEnableMultipleDisbursalField;
    protected TextFeedbackPanel loanTrancheDetailEnableMultipleDisbursalFeedback;

    protected WebMarkupBlock loanTrancheDetailMaximumTrancheCountBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumTrancheCountIContainer;
    protected Long loanTrancheDetailMaximumTrancheCountValue;
    protected TextField<Long> loanTrancheDetailMaximumTrancheCountField;
    protected TextFeedbackPanel loanTrancheDetailMaximumTrancheCountFeedback;

    protected WebMarkupBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer;
    protected Double loanTrancheDetailMaximumAllowedOutstandingBalanceValue;
    protected TextField<Double> loanTrancheDetailMaximumAllowedOutstandingBalanceField;
    protected TextFeedbackPanel loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback;

    // Configurable Terms and Settings

    protected WebMarkupBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    protected WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer;
    protected Boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
    protected CheckBox configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField;
    protected TextFeedbackPanel configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback;

    protected WebMarkupBlock configurableAmortizationBlock;
    protected WebMarkupContainer configurableAmortizationIContainer;
    protected Boolean configurableAmortizationValue;
    protected CheckBox configurableAmortizationField;
    protected TextFeedbackPanel configurableAmortizationFeedback;

    protected WebMarkupBlock configurableInterestMethodBlock;
    protected WebMarkupContainer configurableInterestMethodIContainer;
    protected Boolean configurableInterestMethodValue;
    protected CheckBox configurableInterestMethodField;
    protected TextFeedbackPanel configurableInterestMethodFeedback;

    protected WebMarkupBlock configurableRepaymentStrategyBlock;
    protected WebMarkupContainer configurableRepaymentStrategyIContainer;
    protected Boolean configurableRepaymentStrategyValue;
    protected CheckBox configurableRepaymentStrategyField;
    protected TextFeedbackPanel configurableRepaymentStrategyFeedback;

    protected WebMarkupBlock configurableInterestCalculationPeriodBlock;
    protected WebMarkupContainer configurableInterestCalculationPeriodIContainer;
    protected Boolean configurableInterestCalculationPeriodValue;
    protected CheckBox configurableInterestCalculationPeriodField;
    protected TextFeedbackPanel configurableInterestCalculationPeriodFeedback;

    protected WebMarkupBlock configurableArrearsToleranceBlock;
    protected WebMarkupContainer configurableArrearsToleranceIContainer;
    protected Boolean configurableArrearsToleranceValue;
    protected CheckBox configurableArrearsToleranceField;
    protected TextFeedbackPanel configurableArrearsToleranceFeedback;

    protected WebMarkupBlock configurableRepaidEveryBlock;
    protected WebMarkupContainer configurableRepaidEveryIContainer;
    protected Boolean configurableRepaidEveryValue;
    protected CheckBox configurableRepaidEveryField;
    protected TextFeedbackPanel configurableRepaidEveryFeedback;

    protected WebMarkupBlock configurableMoratoriumBlock;
    protected WebMarkupContainer configurableMoratoriumIContainer;
    protected Boolean configurableMoratoriumValue;
    protected CheckBox configurableMoratoriumField;
    protected TextFeedbackPanel configurableMoratoriumFeedback;

    protected WebMarkupBlock configurableOverdueBeforeMovingBlock;
    protected WebMarkupContainer configurableOverdueBeforeMovingIContainer;
    protected Boolean configurableOverdueBeforeMovingValue;
    protected CheckBox configurableOverdueBeforeMovingField;
    protected TextFeedbackPanel configurableOverdueBeforeMovingFeedback;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashFundSourceBlock;
    protected WebMarkupContainer cashFundSourceIContainer;
    protected SingleChoiceProvider cashFundSourceProvider;
    protected Option cashFundSourceValue;
    protected Select2SingleChoice<Option> cashFundSourceField;
    protected TextFeedbackPanel cashFundSourceFeedback;

    protected WebMarkupBlock cashLoanPortfolioBlock;
    protected WebMarkupContainer cashLoanPortfolioIContainer;
    protected SingleChoiceProvider cashLoanPortfolioProvider;
    protected Option cashLoanPortfolioValue;
    protected Select2SingleChoice<Option> cashLoanPortfolioField;
    protected TextFeedbackPanel cashLoanPortfolioFeedback;

    protected WebMarkupBlock cashTransferInSuspenseBlock;
    protected WebMarkupContainer cashTransferInSuspenseIContainer;
    protected SingleChoiceProvider cashTransferInSuspenseProvider;
    protected Option cashTransferInSuspenseValue;
    protected Select2SingleChoice<Option> cashTransferInSuspenseField;
    protected TextFeedbackPanel cashTransferInSuspenseFeedback;

    protected WebMarkupBlock cashIncomeFromInterestBlock;
    protected WebMarkupContainer cashIncomeFromInterestIContainer;
    protected SingleChoiceProvider cashIncomeFromInterestProvider;
    protected Option cashIncomeFromInterestValue;
    protected Select2SingleChoice<Option> cashIncomeFromInterestField;
    protected TextFeedbackPanel cashIncomeFromInterestFeedback;

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

    protected WebMarkupBlock cashIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer cashIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider cashIncomeFromRecoveryRepaymentProvider;
    protected Option cashIncomeFromRecoveryRepaymentValue;
    protected Select2SingleChoice<Option> cashIncomeFromRecoveryRepaymentField;
    protected TextFeedbackPanel cashIncomeFromRecoveryRepaymentFeedback;

    protected WebMarkupBlock cashLossWrittenOffBlock;
    protected WebMarkupContainer cashLossWrittenOffIContainer;
    protected SingleChoiceProvider cashLossWrittenOffProvider;
    protected Option cashLossWrittenOffValue;
    protected Select2SingleChoice<Option> cashLossWrittenOffField;
    protected TextFeedbackPanel cashLossWrittenOffFeedback;

    protected WebMarkupBlock cashOverPaymentLiabilityBlock;
    protected WebMarkupContainer cashOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider cashOverPaymentLiabilityProvider;
    protected Option cashOverPaymentLiabilityValue;
    protected Select2SingleChoice<Option> cashOverPaymentLiabilityField;
    protected TextFeedbackPanel cashOverPaymentLiabilityFeedback;

    protected WebMarkupContainer periodicBlock;
    protected WebMarkupContainer periodicIContainer;

    protected WebMarkupBlock periodicFundSourceBlock;
    protected WebMarkupContainer periodicFundSourceIContainer;
    protected SingleChoiceProvider periodicFundSourceProvider;
    protected Option periodicFundSourceValue;
    protected Select2SingleChoice<Option> periodicFundSourceField;
    protected TextFeedbackPanel periodicFundSourceFeedback;

    protected WebMarkupBlock periodicLoanPortfolioBlock;
    protected WebMarkupContainer periodicLoanPortfolioIContainer;
    protected SingleChoiceProvider periodicLoanPortfolioProvider;
    protected Option periodicLoanPortfolioValue;
    protected Select2SingleChoice<Option> periodicLoanPortfolioField;
    protected TextFeedbackPanel periodicLoanPortfolioFeedback;

    protected WebMarkupBlock periodicInterestReceivableBlock;
    protected WebMarkupContainer periodicInterestReceivableIContainer;
    protected SingleChoiceProvider periodicInterestReceivableProvider;
    protected Option periodicInterestReceivableValue;
    protected Select2SingleChoice<Option> periodicInterestReceivableField;
    protected TextFeedbackPanel periodicInterestReceivableFeedback;

    protected WebMarkupBlock periodicFeeReceivableBlock;
    protected WebMarkupContainer periodicFeeReceivableIContainer;
    protected SingleChoiceProvider periodicFeeReceivableProvider;
    protected Option periodicFeeReceivableValue;
    protected Select2SingleChoice<Option> periodicFeeReceivableField;
    protected TextFeedbackPanel periodicFeeReceivableFeedback;

    protected WebMarkupBlock periodicPenaltyReceivableBlock;
    protected WebMarkupContainer periodicPenaltyReceivableIContainer;
    protected SingleChoiceProvider periodicPenaltyReceivableProvider;
    protected Option periodicPenaltyReceivableValue;
    protected Select2SingleChoice<Option> periodicPenaltyReceivableField;
    protected TextFeedbackPanel periodicPenaltyReceivableFeedback;

    protected WebMarkupBlock periodicTransferInSuspenseBlock;
    protected WebMarkupContainer periodicTransferInSuspenseIContainer;
    protected SingleChoiceProvider periodicTransferInSuspenseProvider;
    protected Option periodicTransferInSuspenseValue;
    protected Select2SingleChoice<Option> periodicTransferInSuspenseField;
    protected TextFeedbackPanel periodicTransferInSuspenseFeedback;

    protected WebMarkupBlock periodicIncomeFromInterestBlock;
    protected WebMarkupContainer periodicIncomeFromInterestIContainer;
    protected SingleChoiceProvider periodicIncomeFromInterestProvider;
    protected Option periodicIncomeFromInterestValue;
    protected Select2SingleChoice<Option> periodicIncomeFromInterestField;
    protected TextFeedbackPanel periodicIncomeFromInterestFeedback;

    protected WebMarkupBlock periodicIncomeFromFeeBlock;
    protected WebMarkupContainer periodicIncomeFromFeeIContainer;
    protected SingleChoiceProvider periodicIncomeFromFeeProvider;
    protected Option periodicIncomeFromFeeValue;
    protected Select2SingleChoice<Option> periodicIncomeFromFeeField;
    protected TextFeedbackPanel periodicIncomeFromFeeFeedback;

    protected WebMarkupBlock periodicIncomeFromPenaltyBlock;
    protected WebMarkupContainer periodicIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider periodicIncomeFromPenaltyProvider;
    protected Option periodicIncomeFromPenaltyValue;
    protected Select2SingleChoice<Option> periodicIncomeFromPenaltyField;
    protected TextFeedbackPanel periodicIncomeFromPenaltyFeedback;

    protected WebMarkupBlock periodicIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer periodicIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider periodicIncomeFromRecoveryRepaymentProvider;
    protected Option periodicIncomeFromRecoveryRepaymentValue;
    protected Select2SingleChoice<Option> periodicIncomeFromRecoveryRepaymentField;
    protected TextFeedbackPanel periodicIncomeFromRecoveryRepaymentFeedback;

    protected WebMarkupBlock periodicLossWrittenOffBlock;
    protected WebMarkupContainer periodicLossWrittenOffIContainer;
    protected SingleChoiceProvider periodicLossWrittenOffProvider;
    protected Option periodicLossWrittenOffValue;
    protected Select2SingleChoice<Option> periodicLossWrittenOffField;
    protected TextFeedbackPanel periodicLossWrittenOffFeedback;

    protected WebMarkupBlock periodicOverPaymentLiabilityBlock;
    protected WebMarkupContainer periodicOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider periodicOverPaymentLiabilityProvider;
    protected Option periodicOverPaymentLiabilityValue;
    protected Select2SingleChoice<Option> periodicOverPaymentLiabilityField;
    protected TextFeedbackPanel periodicOverPaymentLiabilityFeedback;

    protected WebMarkupContainer upfrontBlock;
    protected WebMarkupContainer upfrontIContainer;

    protected WebMarkupBlock upfrontFundSourceBlock;
    protected WebMarkupContainer upfrontFundSourceIContainer;
    protected SingleChoiceProvider upfrontFundSourceProvider;
    protected Option upfrontFundSourceValue;
    protected Select2SingleChoice<Option> upfrontFundSourceField;
    protected TextFeedbackPanel upfrontFundSourceFeedback;

    protected WebMarkupBlock upfrontLoanPortfolioBlock;
    protected WebMarkupContainer upfrontLoanPortfolioIContainer;
    protected SingleChoiceProvider upfrontLoanPortfolioProvider;
    protected Option upfrontLoanPortfolioValue;
    protected Select2SingleChoice<Option> upfrontLoanPortfolioField;
    protected TextFeedbackPanel upfrontLoanPortfolioFeedback;

    protected WebMarkupBlock upfrontInterestReceivableBlock;
    protected WebMarkupContainer upfrontInterestReceivableIContainer;
    protected SingleChoiceProvider upfrontInterestReceivableProvider;
    protected Option upfrontInterestReceivableValue;
    protected Select2SingleChoice<Option> upfrontInterestReceivableField;
    protected TextFeedbackPanel upfrontInterestReceivableFeedback;

    protected WebMarkupBlock upfrontFeeReceivableBlock;
    protected WebMarkupContainer upfrontFeeReceivableIContainer;
    protected SingleChoiceProvider upfrontFeeReceivableProvider;
    protected Option upfrontFeeReceivableValue;
    protected Select2SingleChoice<Option> upfrontFeeReceivableField;
    protected TextFeedbackPanel upfrontFeeReceivableFeedback;

    protected WebMarkupBlock upfrontPenaltyReceivableBlock;
    protected WebMarkupContainer upfrontPenaltyReceivableIContainer;
    protected SingleChoiceProvider upfrontPenaltyReceivableProvider;
    protected Option upfrontPenaltyReceivableValue;
    protected Select2SingleChoice<Option> upfrontPenaltyReceivableField;
    protected TextFeedbackPanel upfrontPenaltyReceivableFeedback;

    protected WebMarkupBlock upfrontTransferInSuspenseBlock;
    protected WebMarkupContainer upfrontTransferInSuspenseIContainer;
    protected SingleChoiceProvider upfrontTransferInSuspenseProvider;
    protected Option upfrontTransferInSuspenseValue;
    protected Select2SingleChoice<Option> upfrontTransferInSuspenseField;
    protected TextFeedbackPanel upfrontTransferInSuspenseFeedback;

    protected WebMarkupBlock upfrontIncomeFromInterestBlock;
    protected WebMarkupContainer upfrontIncomeFromInterestIContainer;
    protected SingleChoiceProvider upfrontIncomeFromInterestProvider;
    protected Option upfrontIncomeFromInterestValue;
    protected Select2SingleChoice<Option> upfrontIncomeFromInterestField;
    protected TextFeedbackPanel upfrontIncomeFromInterestFeedback;

    protected WebMarkupBlock upfrontIncomeFromFeeBlock;
    protected WebMarkupContainer upfrontIncomeFromFeeIContainer;
    protected SingleChoiceProvider upfrontIncomeFromFeeProvider;
    protected Option upfrontIncomeFromFeeValue;
    protected Select2SingleChoice<Option> upfrontIncomeFromFeeField;
    protected TextFeedbackPanel upfrontIncomeFromFeeFeedback;

    protected WebMarkupBlock upfrontIncomeFromPenaltyBlock;
    protected WebMarkupContainer upfrontIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider upfrontIncomeFromPenaltyProvider;
    protected Option upfrontIncomeFromPenaltyValue;
    protected Select2SingleChoice<Option> upfrontIncomeFromPenaltyField;
    protected TextFeedbackPanel upfrontIncomeFromPenaltyFeedback;

    protected WebMarkupBlock upfrontIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer upfrontIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider upfrontIncomeFromRecoveryRepaymentProvider;
    protected Option upfrontIncomeFromRecoveryRepaymentValue;
    protected Select2SingleChoice<Option> upfrontIncomeFromRecoveryRepaymentField;
    protected TextFeedbackPanel upfrontIncomeFromRecoveryRepaymentFeedback;

    protected WebMarkupBlock upfrontLossWrittenOffBlock;
    protected WebMarkupContainer upfrontLossWrittenOffIContainer;
    protected SingleChoiceProvider upfrontLossWrittenOffProvider;
    protected Option upfrontLossWrittenOffValue;
    protected Select2SingleChoice<Option> upfrontLossWrittenOffField;
    protected TextFeedbackPanel upfrontLossWrittenOffFeedback;

    protected WebMarkupBlock upfrontOverPaymentLiabilityBlock;
    protected WebMarkupContainer upfrontOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider upfrontOverPaymentLiabilityProvider;
    protected Option upfrontOverPaymentLiabilityValue;
    protected Select2SingleChoice<Option> upfrontOverPaymentLiabilityField;
    protected TextFeedbackPanel upfrontOverPaymentLiabilityFeedback;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    protected ModalWindow fundSourcePopup;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    protected ModalWindow feeIncomePopup;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    protected ModalWindow penaltyIncomePopup;

    // Charges

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;

    // Overdue Charges

    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
    protected List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> overdueChargeTable;
    protected ListDataProvider overdueChargeProvider;

    protected ModalWindow overdueChargePopup;

    protected Map<String, Object> popupModel;

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
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        this.form.add(this.closeLink);

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

        initSectionDefault();

        initSectionValidationRule();
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2l;
        this.currencyInMultipleOfValue = 1l;
        this.termPrincipleDefaultValue = 100d;
        this.termNumberOfRepaymentDefaultValue = 12l;
        this.termRepaidEveryValue = 1l;
        this.termRepaidTypeValue = LockInType.Month.toOption();
        this.termNominalInterestRateDefaultValue = 10d;
        this.termNominalInterestRateTypeValue = NominalInterestRateType.Year.toOption();
        this.settingAmortizationValue = Amortization.EqualInstallment.toOption();
        this.settingInterestMethodValue = InterestMethod.DecliningBalance.toOption();
        this.settingInterestCalculationPeriodValue = InterestCalculationPeriod.SameAsPayment.toOption();
        this.settingRepaymentStrategyValue = RepaymentStrategy.Interest_Principle_Penalty_Fee.toOption();
        this.accountingValue = AccountingType.None.getDescription();
        this.interestRecalculationRecalculateInterestValue = false;
        this.settingDayInYearValue = DayInYear.Actual.toOption();
        this.settingDayInMonthValue = DayInMonth.Actual.toOption();
    }

    protected void initSectionValidationRule() {
        this.currencyCodeField.setRequired(true);
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyInMultipleOfField.setRequired(true);
        this.termPrincipleDefaultField.setRequired(true);
        this.termNumberOfRepaymentDefaultField.setRequired(true);
        this.termRepaidEveryField.setRequired(true);
        this.termRepaidTypeField.setRequired(true);
        this.termNominalInterestRateDefaultField.setRequired(true);
        this.termNominalInterestRateTypeField.setRequired(true);

        this.settingAmortizationField.setRequired(true);
        this.settingInterestMethodField.setRequired(true);

        this.settingInterestCalculationPeriodField.setRequired(true);
        this.settingRepaymentStrategyField.setRequired(true);

        this.accountingField.setRequired(true);
        this.settingDayInYearField.setRequired(true);
        this.settingDayInMonthField.setRequired(true);

        {
            InterestCalculationPeriod interestCalculationPeriod = null;
            if (this.settingInterestCalculationPeriodValue != null) {
                interestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
            }

            boolean required = interestCalculationPeriod == InterestCalculationPeriod.Daily && this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue;
            this.interestRecalculationCompoundingOnField.setRequired(required);
            this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setRequired(required);
            this.interestRecalculationRecalculateField.setRequired(required);
        }

    }

    protected void initSectionOverdueCharge() {
        this.overdueChargePopup = new ModalWindow("overdueChargePopup");
        add(this.overdueChargePopup);
        this.overdueChargePopup.setOnClose(this::overdueChargePopupClose);

        this.overdueChargeColumn = Lists.newArrayList();
        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeColumn));
        // this.overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date",
        // this::overdueChargeColumn));
        this.overdueChargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::overdueChargeAction, this::overdueChargeClick));
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, 20);
        this.form.add(this.overdueChargeTable);
        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));

        AjaxLink<Void> overdueChargeAddLink = new AjaxLink<>("overdueChargeAddLink");
        overdueChargeAddLink.setOnClick(this::overdueChargeAddLinkClick);
        this.form.add(overdueChargeAddLink);
    }

    protected boolean overdueChargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.overdueChargePopup.setContent(new OverdueChargePopup("overdueCharge", this.popupModel, ProductPopup.Loan, this.currencyCodeValue.getId()));
            this.overdueChargePopup.show(target);
        } else {
            this.overdueChargePopup.setContent(new CurrencyPopup("currency"));
            this.overdueChargePopup.show(target);
        }
        return false;
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

    protected void overdueChargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.overdueChargeValue.size(); i++) {
            Map<String, Object> column = this.overdueChargeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.overdueChargeValue.remove(index);
        }
        target.add(this.overdueChargeTable);
    }

    protected List<ActionItem> overdueChargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected void initSectionCharge() {

        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupClose);

        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        // this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date",
        // this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        AjaxLink<Void> chargeAddLink = new AjaxLink<>("chargeAddLink");
        chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(chargeAddLink);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup("charge", this.popupModel, ProductPopup.Loan, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.chargePopup.setContent(new CurrencyPopup("currency"));
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
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected void initSectionAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
        this.accountingField.add(new Radio<>("accountingPeriodic", new Model<>(AccountingType.Periodic.getDescription())));
        this.accountingField.add(new Radio<>("accountingUpfront", new Model<>(AccountingType.Upfront.getDescription())));
        this.form.add(this.accountingField);

        initAccountingCash();

        initAccountingPeriodic();

        initAccountingUpFront();

        initAdvancedAccountingRule();

    }

    protected void initAccountingUpFront() {
        this.upfrontBlock = new WebMarkupContainer("upfrontBlock");
        this.upfrontBlock.setOutputMarkupId(true);
        this.form.add(this.upfrontBlock);

        this.upfrontIContainer = new WebMarkupContainer("upfrontIContainer");
        this.upfrontBlock.add(this.upfrontIContainer);

        this.upfrontFundSourceBlock = new WebMarkupBlock("upfrontFundSourceBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontFundSourceBlock);
        this.upfrontFundSourceIContainer = new WebMarkupContainer("upfrontFundSourceIContainer");
        this.upfrontFundSourceBlock.add(this.upfrontFundSourceIContainer);
        this.upfrontFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontFundSourceField = new Select2SingleChoice<>("upfrontFundSourceField", new PropertyModel<>(this, "upfrontFundSourceValue"), this.upfrontFundSourceProvider);
        this.upfrontFundSourceField.setLabel(Model.of("Fund source"));
        this.upfrontFundSourceField.add(new OnChangeAjaxBehavior());
        this.upfrontFundSourceIContainer.add(this.upfrontFundSourceField);
        this.upfrontFundSourceFeedback = new TextFeedbackPanel("upfrontFundSourceFeedback", this.upfrontFundSourceField);
        this.upfrontFundSourceIContainer.add(this.upfrontFundSourceFeedback);

        this.upfrontLoanPortfolioBlock = new WebMarkupBlock("upfrontLoanPortfolioBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontLoanPortfolioBlock);
        this.upfrontLoanPortfolioIContainer = new WebMarkupContainer("upfrontLoanPortfolioIContainer");
        this.upfrontLoanPortfolioBlock.add(this.upfrontLoanPortfolioIContainer);
        this.upfrontLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontLoanPortfolioField = new Select2SingleChoice<>("upfrontLoanPortfolioField", new PropertyModel<>(this, "upfrontLoanPortfolioValue"), this.upfrontLoanPortfolioProvider);
        this.upfrontLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.upfrontLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.upfrontLoanPortfolioIContainer.add(this.upfrontLoanPortfolioField);
        this.upfrontLoanPortfolioFeedback = new TextFeedbackPanel("upfrontLoanPortfolioFeedback", this.upfrontLoanPortfolioField);
        this.upfrontLoanPortfolioIContainer.add(this.upfrontLoanPortfolioFeedback);

        this.upfrontInterestReceivableBlock = new WebMarkupBlock("upfrontInterestReceivableBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontInterestReceivableBlock);
        this.upfrontInterestReceivableIContainer = new WebMarkupContainer("upfrontInterestReceivableIContainer");
        this.upfrontInterestReceivableBlock.add(this.upfrontInterestReceivableIContainer);
        this.upfrontInterestReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontInterestReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontInterestReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontInterestReceivableField = new Select2SingleChoice<>("upfrontInterestReceivableField", new PropertyModel<>(this, "upfrontInterestReceivableValue"), this.upfrontInterestReceivableProvider);
        this.upfrontInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.upfrontInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontInterestReceivableIContainer.add(this.upfrontInterestReceivableField);
        this.upfrontInterestReceivableFeedback = new TextFeedbackPanel("upfrontInterestReceivableFeedback", this.upfrontInterestReceivableField);
        this.upfrontInterestReceivableIContainer.add(this.upfrontInterestReceivableFeedback);

        this.upfrontFeeReceivableBlock = new WebMarkupBlock("upfrontFeeReceivableBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontFeeReceivableBlock);
        this.upfrontFeeReceivableIContainer = new WebMarkupContainer("upfrontFeeReceivableIContainer");
        this.upfrontFeeReceivableBlock.add(this.upfrontFeeReceivableIContainer);
        this.upfrontFeeReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontFeeReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontFeeReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontFeeReceivableField = new Select2SingleChoice<>("upfrontFeeReceivableField", new PropertyModel<>(this, "upfrontFeeReceivableValue"), this.upfrontFeeReceivableProvider);
        this.upfrontFeeReceivableField.setLabel(Model.of("Fee Receivable"));
        this.upfrontFeeReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontFeeReceivableIContainer.add(this.upfrontFeeReceivableField);
        this.upfrontFeeReceivableFeedback = new TextFeedbackPanel("upfrontFeeReceivableFeedback", this.upfrontFeeReceivableField);
        this.upfrontFeeReceivableIContainer.add(this.upfrontFeeReceivableFeedback);

        this.upfrontPenaltyReceivableBlock = new WebMarkupBlock("upfrontPenaltyReceivableBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontPenaltyReceivableBlock);
        this.upfrontPenaltyReceivableIContainer = new WebMarkupContainer("upfrontPenaltyReceivableIContainer");
        this.upfrontPenaltyReceivableBlock.add(this.upfrontPenaltyReceivableIContainer);
        this.upfrontPenaltyReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontPenaltyReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontPenaltyReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontPenaltyReceivableField = new Select2SingleChoice<>("upfrontPenaltyReceivableField", new PropertyModel<>(this, "upfrontPenaltyReceivableValue"), this.upfrontPenaltyReceivableProvider);
        this.upfrontPenaltyReceivableField.setLabel(Model.of("Penalty Receivable"));
        this.upfrontPenaltyReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontPenaltyReceivableIContainer.add(this.upfrontPenaltyReceivableField);
        this.upfrontPenaltyReceivableFeedback = new TextFeedbackPanel("upfrontPenaltyReceivableFeedback", this.upfrontPenaltyReceivableField);
        this.upfrontPenaltyReceivableIContainer.add(this.upfrontPenaltyReceivableFeedback);

        this.upfrontTransferInSuspenseBlock = new WebMarkupBlock("upfrontTransferInSuspenseBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontTransferInSuspenseBlock);
        this.upfrontTransferInSuspenseIContainer = new WebMarkupContainer("upfrontTransferInSuspenseIContainer");
        this.upfrontTransferInSuspenseBlock.add(this.upfrontTransferInSuspenseIContainer);
        this.upfrontTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.upfrontTransferInSuspenseField = new Select2SingleChoice<>("upfrontTransferInSuspenseField", new PropertyModel<>(this, "upfrontTransferInSuspenseValue"), this.upfrontTransferInSuspenseProvider);
        this.upfrontTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.upfrontTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.upfrontTransferInSuspenseIContainer.add(this.upfrontTransferInSuspenseField);
        this.upfrontTransferInSuspenseFeedback = new TextFeedbackPanel("upfrontTransferInSuspenseFeedback", this.upfrontTransferInSuspenseField);
        this.upfrontTransferInSuspenseIContainer.add(this.upfrontTransferInSuspenseFeedback);

        this.upfrontIncomeFromInterestBlock = new WebMarkupBlock("upfrontIncomeFromInterestBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontIncomeFromInterestBlock);
        this.upfrontIncomeFromInterestIContainer = new WebMarkupContainer("upfrontIncomeFromInterestIContainer");
        this.upfrontIncomeFromInterestBlock.add(this.upfrontIncomeFromInterestIContainer);
        this.upfrontIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromInterestField = new Select2SingleChoice<>("upfrontIncomeFromInterestField", new PropertyModel<>(this, "upfrontIncomeFromInterestValue"), this.upfrontIncomeFromInterestProvider);
        this.upfrontIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.upfrontIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.upfrontIncomeFromInterestIContainer.add(this.upfrontIncomeFromInterestField);
        this.upfrontIncomeFromInterestFeedback = new TextFeedbackPanel("upfrontIncomeFromInterestFeedback", this.upfrontIncomeFromInterestField);
        this.upfrontIncomeFromInterestIContainer.add(this.upfrontIncomeFromInterestFeedback);

        this.upfrontIncomeFromFeeBlock = new WebMarkupBlock("upfrontIncomeFromFeeBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontIncomeFromFeeBlock);
        this.upfrontIncomeFromFeeIContainer = new WebMarkupContainer("upfrontIncomeFromFeeIContainer");
        this.upfrontIncomeFromFeeBlock.add(this.upfrontIncomeFromFeeIContainer);
        this.upfrontIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromFeeField = new Select2SingleChoice<>("upfrontIncomeFromFeeField", new PropertyModel<>(this, "upfrontIncomeFromFeeValue"), this.upfrontIncomeFromFeeProvider);
        this.upfrontIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.upfrontIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.upfrontIncomeFromFeeIContainer.add(this.upfrontIncomeFromFeeField);
        this.upfrontIncomeFromFeeFeedback = new TextFeedbackPanel("upfrontIncomeFromFeeFeedback", this.upfrontIncomeFromFeeField);
        this.upfrontIncomeFromFeeIContainer.add(this.upfrontIncomeFromFeeFeedback);

        this.upfrontIncomeFromPenaltyBlock = new WebMarkupBlock("upfrontIncomeFromPenaltyBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontIncomeFromPenaltyBlock);
        this.upfrontIncomeFromPenaltyIContainer = new WebMarkupContainer("upfrontIncomeFromPenaltyIContainer");
        this.upfrontIncomeFromPenaltyBlock.add(this.upfrontIncomeFromPenaltyIContainer);
        this.upfrontIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromPenaltyField = new Select2SingleChoice<>("upfrontIncomeFromPenaltyField", new PropertyModel<>(this, "upfrontIncomeFromPenaltyValue"), this.upfrontIncomeFromPenaltyProvider);
        this.upfrontIncomeFromPenaltyField.setLabel(Model.of("Income from Penalty"));
        this.upfrontIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.upfrontIncomeFromPenaltyIContainer.add(this.upfrontIncomeFromPenaltyField);
        this.upfrontIncomeFromPenaltyFeedback = new TextFeedbackPanel("upfrontIncomeFromPenaltyFeedback", this.upfrontIncomeFromPenaltyField);
        this.upfrontIncomeFromPenaltyIContainer.add(this.upfrontIncomeFromPenaltyFeedback);

        this.upfrontIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("upfrontIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontIncomeFromRecoveryRepaymentBlock);
        this.upfrontIncomeFromRecoveryRepaymentIContainer = new WebMarkupContainer("upfrontIncomeFromRecoveryRepaymentIContainer");
        this.upfrontIncomeFromRecoveryRepaymentBlock.add(this.upfrontIncomeFromRecoveryRepaymentIContainer);
        this.upfrontIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("upfrontIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentValue"), this.upfrontIncomeFromRecoveryRepaymentProvider);
        this.upfrontIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.upfrontIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.upfrontIncomeFromRecoveryRepaymentIContainer.add(this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("upfrontIncomeFromRecoveryRepaymentFeedback", this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontIncomeFromRecoveryRepaymentIContainer.add(this.upfrontIncomeFromRecoveryRepaymentFeedback);

        this.upfrontLossWrittenOffBlock = new WebMarkupBlock("upfrontLossWrittenOffBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontLossWrittenOffBlock);
        this.upfrontLossWrittenOffIContainer = new WebMarkupContainer("upfrontLossWrittenOffIContainer");
        this.upfrontLossWrittenOffBlock.add(this.upfrontLossWrittenOffIContainer);
        this.upfrontLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
        this.upfrontLossWrittenOffField = new Select2SingleChoice<>("upfrontLossWrittenOffField", new PropertyModel<>(this, "upfrontLossWrittenOffValue"), this.upfrontLossWrittenOffProvider);
        this.upfrontLossWrittenOffField.setLabel(Model.of("Loss written off"));
        this.upfrontLossWrittenOffField.add(new OnChangeAjaxBehavior());
        this.upfrontLossWrittenOffIContainer.add(this.upfrontLossWrittenOffField);
        this.upfrontLossWrittenOffFeedback = new TextFeedbackPanel("upfrontLossWrittenOffFeedback", this.upfrontLossWrittenOffField);
        this.upfrontLossWrittenOffIContainer.add(this.upfrontLossWrittenOffFeedback);

        this.upfrontOverPaymentLiabilityBlock = new WebMarkupBlock("upfrontOverPaymentLiabilityBlock", Size.Six_6);
        this.upfrontIContainer.add(this.upfrontOverPaymentLiabilityBlock);
        this.upfrontOverPaymentLiabilityIContainer = new WebMarkupContainer("upfrontOverPaymentLiabilityIContainer");
        this.upfrontOverPaymentLiabilityBlock.add(this.upfrontOverPaymentLiabilityIContainer);
        this.upfrontOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.upfrontOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.upfrontOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.upfrontOverPaymentLiabilityField = new Select2SingleChoice<>("upfrontOverPaymentLiabilityField", new PropertyModel<>(this, "upfrontOverPaymentLiabilityValue"), this.upfrontOverPaymentLiabilityProvider);
        this.upfrontOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.upfrontOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.upfrontOverPaymentLiabilityIContainer.add(this.upfrontOverPaymentLiabilityField);
        this.upfrontOverPaymentLiabilityFeedback = new TextFeedbackPanel("upfrontOverPaymentLiabilityFeedback", this.upfrontOverPaymentLiabilityField);
        this.upfrontOverPaymentLiabilityIContainer.add(this.upfrontOverPaymentLiabilityFeedback);
    }

    protected void initAccountingCash() {

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);

        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        this.cashFundSourceBlock = new WebMarkupBlock("cashFundSourceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashFundSourceBlock);
        this.cashFundSourceIContainer = new WebMarkupContainer("cashFundSourceIContainer");
        this.cashFundSourceBlock.add(this.cashFundSourceIContainer);
        this.cashFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashFundSourceField = new Select2SingleChoice<>("cashFundSourceField", new PropertyModel<>(this, "cashFundSourceValue"), this.cashFundSourceProvider);
        this.cashFundSourceField.setLabel(Model.of("Fund Source"));
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());
        this.cashFundSourceIContainer.add(this.cashFundSourceField);
        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceField);
        this.cashFundSourceIContainer.add(this.cashFundSourceFeedback);

        this.cashLoanPortfolioBlock = new WebMarkupBlock("cashLoanPortfolioBlock", Size.Six_6);
        this.cashIContainer.add(this.cashLoanPortfolioBlock);
        this.cashLoanPortfolioIContainer = new WebMarkupContainer("cashLoanPortfolioIContainer");
        this.cashLoanPortfolioBlock.add(this.cashLoanPortfolioIContainer);
        this.cashLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField", new PropertyModel<>(this, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.cashLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashLoanPortfolioIContainer.add(this.cashLoanPortfolioField);
        this.cashLoanPortfolioFeedback = new TextFeedbackPanel("cashLoanPortfolioFeedback", this.cashLoanPortfolioField);
        this.cashLoanPortfolioIContainer.add(this.cashLoanPortfolioFeedback);

        this.cashTransferInSuspenseBlock = new WebMarkupBlock("cashTransferInSuspenseBlock", Size.Six_6);
        this.cashIContainer.add(this.cashTransferInSuspenseBlock);
        this.cashTransferInSuspenseIContainer = new WebMarkupContainer("cashTransferInSuspenseIContainer");
        this.cashTransferInSuspenseBlock.add(this.cashTransferInSuspenseIContainer);
        this.cashTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashTransferInSuspenseField = new Select2SingleChoice<>("cashTransferInSuspenseField", new PropertyModel<>(this, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
        this.cashTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.cashTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashTransferInSuspenseIContainer.add(this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseFeedback = new TextFeedbackPanel("cashTransferInSuspenseFeedback", this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseIContainer.add(this.cashTransferInSuspenseFeedback);

        this.cashIncomeFromInterestBlock = new WebMarkupBlock("cashIncomeFromInterestBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromInterestBlock);
        this.cashIncomeFromInterestIContainer = new WebMarkupContainer("cashIncomeFromInterestIContainer");
        this.cashIncomeFromInterestBlock.add(this.cashIncomeFromInterestIContainer);
        this.cashIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromInterestField = new Select2SingleChoice<>("cashIncomeFromInterestField", new PropertyModel<>(this, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
        this.cashIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.cashIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromInterestIContainer.add(this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestFeedback = new TextFeedbackPanel("cashIncomeFromInterestFeedback", this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestIContainer.add(this.cashIncomeFromInterestFeedback);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
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
        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyField.setLabel(Model.of("Income from Penalty"));
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyFeedback = new TextFeedbackPanel("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyFeedback);

        this.cashIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromRecoveryRepaymentBlock);
        this.cashIncomeFromRecoveryRepaymentIContainer = new WebMarkupContainer("cashIncomeFromRecoveryRepaymentIContainer");
        this.cashIncomeFromRecoveryRepaymentBlock.add(this.cashIncomeFromRecoveryRepaymentIContainer);
        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "cashIncomeFromRecoveryRepaymentValue"), this.cashIncomeFromRecoveryRepaymentProvider);
        this.cashIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.cashIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromRecoveryRepaymentIContainer.add(this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryRepaymentFeedback", this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentIContainer.add(this.cashIncomeFromRecoveryRepaymentFeedback);

        this.cashLossWrittenOffBlock = new WebMarkupBlock("cashLossWrittenOffBlock", Size.Six_6);
        this.cashIContainer.add(this.cashLossWrittenOffBlock);
        this.cashLossWrittenOffIContainer = new WebMarkupContainer("cashLossWrittenOffIContainer");
        this.cashLossWrittenOffBlock.add(this.cashLossWrittenOffIContainer);
        this.cashLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
        this.cashLossWrittenOffField = new Select2SingleChoice<>("cashLossWrittenOffField", new PropertyModel<>(this, "cashLossWrittenOffValue"), this.cashLossWrittenOffProvider);
        this.cashLossWrittenOffField.setLabel(Model.of("Loss written off"));
        this.cashLossWrittenOffField.add(new OnChangeAjaxBehavior());
        this.cashLossWrittenOffIContainer.add(this.cashLossWrittenOffField);
        this.cashLossWrittenOffFeedback = new TextFeedbackPanel("cashLossWrittenOffFeedback", this.cashLossWrittenOffField);
        this.cashLossWrittenOffIContainer.add(this.cashLossWrittenOffFeedback);

        this.cashOverPaymentLiabilityBlock = new WebMarkupBlock("cashOverPaymentLiabilityBlock", Size.Six_6);
        this.cashIContainer.add(this.cashOverPaymentLiabilityBlock);
        this.cashOverPaymentLiabilityIContainer = new WebMarkupContainer("cashOverPaymentLiabilityIContainer");
        this.cashOverPaymentLiabilityBlock.add(this.cashOverPaymentLiabilityIContainer);
        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.cashOverPaymentLiabilityField = new Select2SingleChoice<>("cashOverPaymentLiabilityField", new PropertyModel<>(this, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
        this.cashOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.cashOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashOverPaymentLiabilityIContainer.add(this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityFeedback = new TextFeedbackPanel("cashOverPaymentLiabilityFeedback", this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityIContainer.add(this.cashOverPaymentLiabilityFeedback);
    }

    protected void initAccountingPeriodic() {
        this.periodicBlock = new WebMarkupContainer("periodicBlock");
        this.periodicBlock.setOutputMarkupId(true);
        this.form.add(this.periodicBlock);

        this.periodicIContainer = new WebMarkupContainer("periodicIContainer");
        this.periodicBlock.add(this.periodicIContainer);

        this.periodicFundSourceBlock = new WebMarkupBlock("periodicFundSourceBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicFundSourceBlock);
        this.periodicFundSourceIContainer = new WebMarkupContainer("periodicFundSourceIContainer");
        this.periodicFundSourceBlock.add(this.periodicFundSourceIContainer);
        this.periodicFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.periodicFundSourceField = new Select2SingleChoice<>("periodicFundSourceField", new PropertyModel<>(this, "periodicFundSourceValue"), this.periodicFundSourceProvider);
        this.periodicFundSourceField.setLabel(Model.of("Fund source"));
        this.periodicFundSourceField.add(new OnChangeAjaxBehavior());
        this.periodicFundSourceIContainer.add(this.periodicFundSourceField);
        this.periodicFundSourceFeedback = new TextFeedbackPanel("periodicFundSourceFeedback", this.periodicFundSourceField);
        this.periodicFundSourceIContainer.add(this.periodicFundSourceFeedback);

        this.periodicLoanPortfolioBlock = new WebMarkupBlock("periodicLoanPortfolioBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicLoanPortfolioBlock);
        this.periodicLoanPortfolioIContainer = new WebMarkupContainer("periodicLoanPortfolioIContainer");
        this.periodicLoanPortfolioBlock.add(this.periodicLoanPortfolioIContainer);
        this.periodicLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.periodicLoanPortfolioField = new Select2SingleChoice<>("periodicLoanPortfolioField", new PropertyModel<>(this, "periodicLoanPortfolioValue"), this.periodicLoanPortfolioProvider);
        this.periodicLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.periodicLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.periodicLoanPortfolioIContainer.add(this.periodicLoanPortfolioField);
        this.periodicLoanPortfolioFeedback = new TextFeedbackPanel("periodicLoanPortfolioFeedback", this.periodicLoanPortfolioField);
        this.periodicLoanPortfolioIContainer.add(this.periodicLoanPortfolioFeedback);

        this.periodicInterestReceivableBlock = new WebMarkupBlock("periodicInterestReceivableBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicInterestReceivableBlock);
        this.periodicInterestReceivableIContainer = new WebMarkupContainer("periodicInterestReceivableIContainer");
        this.periodicInterestReceivableBlock.add(this.periodicInterestReceivableIContainer);
        this.periodicInterestReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicInterestReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicInterestReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.periodicInterestReceivableField = new Select2SingleChoice<>("periodicInterestReceivableField", new PropertyModel<>(this, "periodicInterestReceivableValue"), this.periodicInterestReceivableProvider);
        this.periodicInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.periodicInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicInterestReceivableIContainer.add(this.periodicInterestReceivableField);
        this.periodicInterestReceivableFeedback = new TextFeedbackPanel("periodicInterestReceivableFeedback", this.periodicInterestReceivableField);
        this.periodicInterestReceivableIContainer.add(this.periodicInterestReceivableFeedback);

        this.periodicFeeReceivableBlock = new WebMarkupBlock("periodicFeeReceivableBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicFeeReceivableBlock);
        this.periodicFeeReceivableIContainer = new WebMarkupContainer("periodicFeeReceivableIContainer");
        this.periodicFeeReceivableBlock.add(this.periodicFeeReceivableIContainer);
        this.periodicFeeReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicFeeReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicFeeReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.periodicFeeReceivableField = new Select2SingleChoice<>("periodicFeeReceivableField", new PropertyModel<>(this, "periodicFeeReceivableValue"), this.periodicFeeReceivableProvider);
        this.periodicFeeReceivableField.setLabel(Model.of("Fee Receivable"));
        this.periodicFeeReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicFeeReceivableIContainer.add(this.periodicFeeReceivableField);
        this.periodicFeeReceivableFeedback = new TextFeedbackPanel("periodicFeeReceivableFeedback", this.periodicFeeReceivableField);
        this.periodicFeeReceivableIContainer.add(this.periodicFeeReceivableFeedback);

        this.periodicPenaltyReceivableBlock = new WebMarkupBlock("periodicPenaltyReceivableBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicPenaltyReceivableBlock);
        this.periodicPenaltyReceivableIContainer = new WebMarkupContainer("periodicPenaltyReceivableIContainer");
        this.periodicPenaltyReceivableBlock.add(this.periodicPenaltyReceivableIContainer);
        this.periodicPenaltyReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicPenaltyReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicPenaltyReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicPenaltyReceivableField = new Select2SingleChoice<>("periodicPenaltyReceivableField", new PropertyModel<>(this, "periodicPenaltyReceivableValue"), this.periodicPenaltyReceivableProvider);
        this.periodicPenaltyReceivableField.setLabel(Model.of("Penalty Receivable"));
        this.periodicPenaltyReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicPenaltyReceivableIContainer.add(this.periodicPenaltyReceivableField);
        this.periodicPenaltyReceivableFeedback = new TextFeedbackPanel("periodicPenaltyReceivableFeedback", this.periodicPenaltyReceivableField);
        this.periodicPenaltyReceivableIContainer.add(this.periodicPenaltyReceivableFeedback);

        this.periodicTransferInSuspenseBlock = new WebMarkupBlock("periodicTransferInSuspenseBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicTransferInSuspenseBlock);
        this.periodicTransferInSuspenseIContainer = new WebMarkupContainer("periodicTransferInSuspenseIContainer");
        this.periodicTransferInSuspenseBlock.add(this.periodicTransferInSuspenseIContainer);
        this.periodicTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.periodicTransferInSuspenseField = new Select2SingleChoice<>("periodicTransferInSuspenseField", new PropertyModel<>(this, "periodicTransferInSuspenseValue"), this.periodicTransferInSuspenseProvider);
        this.periodicTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.periodicTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.periodicTransferInSuspenseIContainer.add(this.periodicTransferInSuspenseField);
        this.periodicTransferInSuspenseFeedback = new TextFeedbackPanel("periodicTransferInSuspenseFeedback", this.periodicTransferInSuspenseField);
        this.periodicTransferInSuspenseIContainer.add(this.periodicTransferInSuspenseFeedback);

        this.periodicIncomeFromInterestBlock = new WebMarkupBlock("periodicIncomeFromInterestBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicIncomeFromInterestBlock);
        this.periodicIncomeFromInterestIContainer = new WebMarkupContainer("periodicIncomeFromInterestIContainer");
        this.periodicIncomeFromInterestBlock.add(this.periodicIncomeFromInterestIContainer);
        this.periodicIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromInterestField = new Select2SingleChoice<>("periodicIncomeFromInterestField", new PropertyModel<>(this, "periodicIncomeFromInterestValue"), this.periodicIncomeFromInterestProvider);
        this.periodicIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.periodicIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.periodicIncomeFromInterestIContainer.add(this.periodicIncomeFromInterestField);
        this.periodicIncomeFromInterestFeedback = new TextFeedbackPanel("periodicIncomeFromInterestFeedback", this.periodicIncomeFromInterestField);
        this.periodicIncomeFromInterestIContainer.add(this.periodicIncomeFromInterestFeedback);

        this.periodicIncomeFromFeeBlock = new WebMarkupBlock("periodicIncomeFromFeeBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicIncomeFromFeeBlock);
        this.periodicIncomeFromFeeIContainer = new WebMarkupContainer("periodicIncomeFromFeeIContainer");
        this.periodicIncomeFromFeeBlock.add(this.periodicIncomeFromFeeIContainer);
        this.periodicIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromFeeField = new Select2SingleChoice<>("periodicIncomeFromFeeField", new PropertyModel<>(this, "periodicIncomeFromFeeValue"), this.periodicIncomeFromFeeProvider);
        this.periodicIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.periodicIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.periodicIncomeFromFeeIContainer.add(this.periodicIncomeFromFeeField);
        this.periodicIncomeFromFeeFeedback = new TextFeedbackPanel("periodicIncomeFromFeeFeedback", this.periodicIncomeFromFeeField);
        this.periodicIncomeFromFeeIContainer.add(this.periodicIncomeFromFeeFeedback);

        this.periodicIncomeFromPenaltyBlock = new WebMarkupBlock("periodicIncomeFromPenaltyBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicIncomeFromPenaltyBlock);
        this.periodicIncomeFromPenaltyIContainer = new WebMarkupContainer("periodicIncomeFromPenaltyIContainer");
        this.periodicIncomeFromPenaltyBlock.add(this.periodicIncomeFromPenaltyIContainer);
        this.periodicIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromPenaltyField = new Select2SingleChoice<>("periodicIncomeFromPenaltyField", new PropertyModel<>(this, "periodicIncomeFromPenaltyValue"), this.periodicIncomeFromPenaltyProvider);
        this.periodicIncomeFromPenaltyField.setLabel(Model.of("Income from Penalty"));
        this.periodicIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.periodicIncomeFromPenaltyIContainer.add(this.periodicIncomeFromPenaltyField);
        this.periodicIncomeFromPenaltyFeedback = new TextFeedbackPanel("periodicIncomeFromPenaltyFeedback", this.periodicIncomeFromPenaltyField);
        this.periodicIncomeFromPenaltyIContainer.add(this.periodicIncomeFromPenaltyFeedback);

        this.periodicIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("periodicIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicIncomeFromRecoveryRepaymentBlock);
        this.periodicIncomeFromRecoveryRepaymentIContainer = new WebMarkupContainer("periodicIncomeFromRecoveryRepaymentIContainer");
        this.periodicIncomeFromRecoveryRepaymentBlock.add(this.periodicIncomeFromRecoveryRepaymentIContainer);
        this.periodicIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("periodicIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentValue"), this.periodicIncomeFromRecoveryRepaymentProvider);
        this.periodicIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.periodicIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.periodicIncomeFromRecoveryRepaymentIContainer.add(this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("periodicIncomeFromRecoveryRepaymentFeedback", this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicIncomeFromRecoveryRepaymentIContainer.add(this.periodicIncomeFromRecoveryRepaymentFeedback);

        this.periodicLossWrittenOffBlock = new WebMarkupBlock("periodicLossWrittenOffBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicLossWrittenOffBlock);
        this.periodicLossWrittenOffIContainer = new WebMarkupContainer("periodicLossWrittenOffIContainer");
        this.periodicLossWrittenOffBlock.add(this.periodicLossWrittenOffIContainer);
        this.periodicLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
        this.periodicLossWrittenOffField = new Select2SingleChoice<>("periodicLossWrittenOffField", new PropertyModel<>(this, "periodicLossWrittenOffValue"), this.periodicLossWrittenOffProvider);
        this.periodicLossWrittenOffField.setLabel(Model.of("Loss written off"));
        this.periodicLossWrittenOffField.add(new OnChangeAjaxBehavior());
        this.periodicLossWrittenOffIContainer.add(this.periodicLossWrittenOffField);
        this.periodicLossWrittenOffFeedback = new TextFeedbackPanel("periodicLossWrittenOffFeedback", this.periodicLossWrittenOffField);
        this.periodicLossWrittenOffIContainer.add(this.periodicLossWrittenOffFeedback);

        this.periodicOverPaymentLiabilityBlock = new WebMarkupBlock("periodicOverPaymentLiabilityBlock", Size.Six_6);
        this.periodicIContainer.add(this.periodicOverPaymentLiabilityBlock);
        this.periodicOverPaymentLiabilityIContainer = new WebMarkupContainer("periodicOverPaymentLiabilityIContainer");
        this.periodicOverPaymentLiabilityBlock.add(this.periodicOverPaymentLiabilityIContainer);
        this.periodicOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.periodicOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.periodicOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.periodicOverPaymentLiabilityField = new Select2SingleChoice<>("periodicOverPaymentLiabilityField", new PropertyModel<>(this, "periodicOverPaymentLiabilityValue"), this.periodicOverPaymentLiabilityProvider);
        this.periodicOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.periodicOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.periodicOverPaymentLiabilityIContainer.add(this.periodicOverPaymentLiabilityField);
        this.periodicOverPaymentLiabilityFeedback = new TextFeedbackPanel("periodicOverPaymentLiabilityFeedback", this.periodicOverPaymentLiabilityField);
        this.periodicOverPaymentLiabilityIContainer.add(this.periodicOverPaymentLiabilityFeedback);

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

            this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
            this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
            this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
            this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
            this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceTable);
            this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
            this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

            this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
            advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
            this.advancedAccountingRuleIContainer.add(advancedAccountingRuleFundSourceAddLink);
        }

        // Table
        {
            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
            add(this.feeIncomePopup);
            this.feeIncomePopup.setOnClose(this::feeIncomePopupClose);

            this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fee"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
            this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
            this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
            this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
            this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeTable);
            this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
            this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

            AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
            advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
            this.advancedAccountingRuleIContainer.add(advancedAccountingRuleFeeIncomeAddLink);
        }

        // Table
        {
            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
            add(this.penaltyIncomePopup);
            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupClose);

            this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
            this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
            this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
            this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
            this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
            this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
            this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
            this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

            AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
            advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
            this.advancedAccountingRuleIContainer.add(advancedAccountingRulePenaltyIncomeAddLink);
        }
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.feeIncomePopup.setContent(new FeeChargePopup("feeCharge", this.popupModel, ProductPopup.Loan, this.currencyCodeValue.getId()));
            this.feeIncomePopup.show(target);
        } else {
            this.feeIncomePopup.setContent(new CurrencyPopup("currency"));
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

    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup("penaltyCharge", this.popupModel, ProductPopup.Loan, this.currencyCodeValue.getId()));
            this.penaltyIncomePopup.show(target);
        } else {
            this.penaltyIncomePopup.setContent(new CurrencyPopup("currency"));
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
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.fundSourcePopup.setContent(new PaymentTypePopup("paymentType", this.popupModel));
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
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);
        this.periodicIContainer.setVisible(false);
        this.upfrontIContainer.setVisible(false);
        this.advancedAccountingRuleIContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleIContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleIContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashIContainer.setVisible(true);
        } else if ("Periodic".equals(this.accountingValue)) {
            this.periodicIContainer.setVisible(true);
        } else if ("Upfront".equals(this.accountingValue)) {
            this.upfrontIContainer.setVisible(true);
        }
        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.periodicBlock);
            target.add(this.upfrontBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initSectionConfigurableTermsAndSettings() {

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Twelve_12);
        this.form.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField.add(new OnChangeAjaxBehavior(this::configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback = new TextFeedbackPanel("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback", this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback);

        this.configurableAmortizationBlock = new WebMarkupBlock("configurableAmortizationBlock", Size.Six_6);
        this.form.add(this.configurableAmortizationBlock);
        this.configurableAmortizationIContainer = new WebMarkupContainer("configurableAmortizationIContainer");
        this.configurableAmortizationBlock.add(this.configurableAmortizationIContainer);
        this.configurableAmortizationField = new CheckBox("configurableAmortizationField", new PropertyModel<>(this, "configurableAmortizationValue"));
        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());
        this.configurableAmortizationIContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback", this.configurableAmortizationField);
        this.configurableAmortizationIContainer.add(this.configurableAmortizationFeedback);

        this.configurableInterestMethodBlock = new WebMarkupBlock("configurableInterestMethodBlock", Size.Six_6);
        this.form.add(this.configurableInterestMethodBlock);
        this.configurableInterestMethodIContainer = new WebMarkupContainer("configurableInterestMethodIContainer");
        this.configurableInterestMethodBlock.add(this.configurableInterestMethodIContainer);
        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField", new PropertyModel<>(this, "configurableInterestMethodValue"));
        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestMethodIContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback", this.configurableInterestMethodField);
        this.configurableInterestMethodIContainer.add(this.configurableInterestMethodFeedback);

        this.configurableRepaymentStrategyBlock = new WebMarkupBlock("configurableRepaymentStrategyBlock", Size.Six_6);
        this.form.add(this.configurableRepaymentStrategyBlock);
        this.configurableRepaymentStrategyIContainer = new WebMarkupContainer("configurableRepaymentStrategyIContainer");
        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyIContainer);
        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField", new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.configurableRepaymentStrategyIContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback", this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyIContainer.add(this.configurableRepaymentStrategyFeedback);

        this.configurableInterestCalculationPeriodBlock = new WebMarkupBlock("configurableInterestCalculationPeriodBlock", Size.Six_6);
        this.form.add(this.configurableInterestCalculationPeriodBlock);
        this.configurableInterestCalculationPeriodIContainer = new WebMarkupContainer("configurableInterestCalculationPeriodIContainer");
        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodIContainer);
        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField", new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestCalculationPeriodIContainer.add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel("configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodIContainer.add(this.configurableInterestCalculationPeriodFeedback);

        this.configurableArrearsToleranceBlock = new WebMarkupBlock("configurableArrearsToleranceBlock", Size.Six_6);
        this.form.add(this.configurableArrearsToleranceBlock);
        this.configurableArrearsToleranceIContainer = new WebMarkupContainer("configurableArrearsToleranceIContainer");
        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceIContainer);
        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField", new PropertyModel<>(this, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.configurableArrearsToleranceIContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback", this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceIContainer.add(this.configurableArrearsToleranceFeedback);

        this.configurableRepaidEveryBlock = new WebMarkupBlock("configurableRepaidEveryBlock", Size.Six_6);
        this.form.add(this.configurableRepaidEveryBlock);
        this.configurableRepaidEveryIContainer = new WebMarkupContainer("configurableRepaidEveryIContainer");
        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryIContainer);
        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField", new PropertyModel<>(this, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.configurableRepaidEveryIContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback", this.configurableRepaidEveryField);
        this.configurableRepaidEveryIContainer.add(this.configurableRepaidEveryFeedback);

        this.configurableMoratoriumBlock = new WebMarkupBlock("configurableMoratoriumBlock", Size.Six_6);
        this.form.add(this.configurableMoratoriumBlock);
        this.configurableMoratoriumIContainer = new WebMarkupContainer("configurableMoratoriumIContainer");
        this.configurableMoratoriumBlock.add(this.configurableMoratoriumIContainer);
        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField", new PropertyModel<>(this, "configurableMoratoriumValue"));
        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());
        this.configurableMoratoriumIContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback", this.configurableMoratoriumField);
        this.configurableMoratoriumIContainer.add(this.configurableMoratoriumFeedback);

        this.configurableOverdueBeforeMovingBlock = new WebMarkupBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
        this.form.add(this.configurableOverdueBeforeMovingBlock);
        this.configurableOverdueBeforeMovingIContainer = new WebMarkupContainer("configurableOverdueBeforeMovingIContainer");
        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingIContainer);
        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField", new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());
        this.configurableOverdueBeforeMovingIContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback", this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingIContainer.add(this.configurableOverdueBeforeMovingFeedback);
    }

    protected boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue != null && this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
        this.configurableAmortizationIContainer.setVisible(visible);
        this.configurableInterestMethodIContainer.setVisible(visible);
        this.configurableRepaymentStrategyIContainer.setVisible(visible);
        this.configurableInterestCalculationPeriodIContainer.setVisible(visible);
        this.configurableArrearsToleranceIContainer.setVisible(visible);
        this.configurableRepaidEveryIContainer.setVisible(visible);
        this.configurableMoratoriumIContainer.setVisible(visible);
        this.configurableOverdueBeforeMovingIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.configurableAmortizationBlock);
            target.add(this.configurableInterestMethodBlock);
            target.add(this.configurableRepaymentStrategyBlock);
            target.add(configurableInterestCalculationPeriodBlock);
            target.add(this.configurableArrearsToleranceBlock);
            target.add(this.configurableRepaidEveryBlock);
            target.add(this.configurableMoratoriumBlock);
            target.add(this.configurableOverdueBeforeMovingBlock);
        }
        return false;
    }

    protected void initSectionLoanTrancheDetails() {

        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Twelve_12);
        this.form.add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalIContainer");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalIContainer);
        this.loanTrancheDetailEnableMultipleDisbursalField = new CheckBox("loanTrancheDetailEnableMultipleDisbursalField", new PropertyModel<>(this, "loanTrancheDetailEnableMultipleDisbursalValue"));
        this.loanTrancheDetailEnableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::loanTrancheDetailEnableMultipleDisbursalFieldUpdate));
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.add(this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalFeedback = new TextFeedbackPanel("loanTrancheDetailEnableMultipleDisbursalFeedback", this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.add(this.loanTrancheDetailEnableMultipleDisbursalFeedback);

        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Six_6);
        this.form.add(this.loanTrancheDetailMaximumTrancheCountBlock);
        this.loanTrancheDetailMaximumTrancheCountIContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountIContainer");
        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountIContainer);
        this.loanTrancheDetailMaximumTrancheCountField = new TextField<>("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountField.setLabel(Model.of("Maximum Tranche count"));
        this.loanTrancheDetailMaximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumTrancheCountIContainer.add(this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumTrancheCountFeedback", this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountIContainer.add(this.loanTrancheDetailMaximumTrancheCountFeedback);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Six_6);
        this.form.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField = new TextField<>("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setLabel(Model.of("Maximum allowed outstanding balance"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback", this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback);
    }

    protected boolean loanTrancheDetailEnableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.loanTrancheDetailEnableMultipleDisbursalValue != null && this.loanTrancheDetailEnableMultipleDisbursalValue;
        this.loanTrancheDetailMaximumTrancheCountIContainer.setVisible(visible);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.loanTrancheDetailMaximumTrancheCountBlock);
            target.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        }
        return false;
    }

    protected void initSectionGuaranteeRequirements() {

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Twelve_12);
        this.form.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField = new CheckBox("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", new PropertyModel<>(this, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback);

        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Six_6);
        this.form.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
        this.guaranteeRequirementMandatoryGuaranteeIContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeIContainer");
        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeIContainer);
        this.guaranteeRequirementMandatoryGuaranteeField = new TextField<>("guaranteeRequirementMandatoryGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMandatoryGuaranteeValue"));
        this.guaranteeRequirementMandatoryGuaranteeField.setLabel(Model.of("Mandatory Guarantee: (%)"));
        this.guaranteeRequirementMandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMandatoryGuaranteeIContainer.add(this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMandatoryGuaranteeFeedback", this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeIContainer.add(this.guaranteeRequirementMandatoryGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Six_6);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeBlock);
        this.guaranteeRequirementMinimumGuaranteeIContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeIContainer");
        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeIContainer);
        this.guaranteeRequirementMinimumGuaranteeField = new TextField<>("guaranteeRequirementMinimumGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeValue"));
        this.guaranteeRequirementMinimumGuaranteeField.setLabel(Model.of("Minimum Guarantee from Own Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeIContainer.add(this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFeedback", this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeIContainer.add(this.guaranteeRequirementMinimumGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Six_6);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField = new TextField<>("guaranteeRequirementMinimumGuaranteeFromGuarantorField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setLabel(Model.of("Minimum Guarantee from Guarantor Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback", this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback);

    }

    protected boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue != null && this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
        this.guaranteeRequirementMandatoryGuaranteeIContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeIContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        }
        return false;
    }

    protected boolean interestRecalculationRecalculateInterestFieldUpdate(AjaxRequestTarget target) {
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (this.settingInterestCalculationPeriodValue != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
        }

        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.Daily && this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue;

        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.setVisible(visible);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.setVisible(visible);
        this.interestRecalculationCompoundingOnIContainer.setVisible(visible);
        this.interestRecalculationCompoundingIContainer.setVisible(visible);
        this.interestRecalculationCompoundingTypeIContainer.setVisible(visible);
        this.interestRecalculationCompoundingDayIContainer.setVisible(visible);
        this.interestRecalculationCompoundingIntervalIContainer.setVisible(visible);
        this.interestRecalculationRecalculateIContainer.setVisible(visible);
        this.interestRecalculationRecalculateTypeIContainer.setVisible(visible);
        this.interestRecalculationRecalculateDayIContainer.setVisible(visible);
        this.interestRecalculationRecalculateIntervalIContainer.setVisible(visible);
        if (visible) {
            interestRecalculationRecalculateFieldUpdate(target);
            interestRecalculationCompoundingFieldUpdate(target);
        }
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
            target.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
            target.add(this.interestRecalculationCompoundingOnBlock);
            target.add(this.interestRecalculationCompoundingBlock);
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.interestRecalculationCompoundingIntervalBlock);
            target.add(this.interestRecalculationRecalculateBlock);
            target.add(this.interestRecalculationRecalculateTypeBlock);
            target.add(this.interestRecalculationRecalculateDayBlock);
            target.add(this.interestRecalculationRecalculateIntervalBlock);
            target.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        }

        initSectionValidationRule();
        return false;
    }

    protected void initSectionInterestRecalculation() {

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestIContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestIContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestIContainer);
        this.interestRecalculationRecalculateInterestField = new CheckBox("interestRecalculationRecalculateInterestField", new PropertyModel<>(this, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateInterestFieldUpdate));
        this.interestRecalculationRecalculateInterestIContainer.add(this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestFeedback = new TextFeedbackPanel("interestRecalculationRecalculateInterestFeedback", this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestIContainer.add(this.interestRecalculationRecalculateInterestFeedback);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
        this.form.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleIContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleIContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
        this.interestRecalculationPreClosureInterestCalculationRuleField = new Select2SingleChoice<>("interestRecalculationPreClosureInterestCalculationRuleField", new PropertyModel<>(this, "interestRecalculationPreClosureInterestCalculationRuleValue"), this.interestRecalculationPreClosureInterestCalculationRuleProvider);
        this.interestRecalculationPreClosureInterestCalculationRuleField.setLabel(Model.of("Pre-closure interest calculation rule"));
        this.interestRecalculationPreClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleFeedback = new TextFeedbackPanel("interestRecalculationPreClosureInterestCalculationRuleFeedback", this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleFeedback);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
        this.form.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeIContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("interestRecalculationAdvancePaymentsAdjustmentTypeField", new PropertyModel<>(this, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"), this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setLabel(Model.of("Advance payments adjustment type"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback = new TextFeedbackPanel("interestRecalculationAdvancePaymentsAdjustmentTypeFeedback", this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnIContainer = new WebMarkupContainer("interestRecalculationCompoundingOnIContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnIContainer);
        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
        this.interestRecalculationCompoundingOnField = new Select2SingleChoice<>("interestRecalculationCompoundingOnField", new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"), this.interestRecalculationCompoundingOnProvider);
        this.interestRecalculationCompoundingOnField.setLabel(Model.of("Interest recalculation compounding on"));
        this.interestRecalculationCompoundingOnField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingOnIContainer.add(this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnIContainer.add(this.interestRecalculationCompoundingOnFeedback);

        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingIContainer = new WebMarkupContainer("interestRecalculationCompoundingIContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingIContainer);
        this.interestRecalculationCompoundingProvider = new FrequencyProvider();
        this.interestRecalculationCompoundingField = new Select2SingleChoice<>("interestRecalculationCompoundingField", new PropertyModel<>(this, "interestRecalculationCompoundingValue"), this.interestRecalculationCompoundingProvider);
        this.interestRecalculationCompoundingField.setLabel(Model.of("Frequency for compounding"));
        this.interestRecalculationCompoundingIContainer.add(this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingFeedback = new TextFeedbackPanel("interestRecalculationCompoundingFeedback", this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingIContainer.add(this.interestRecalculationCompoundingFeedback);

        this.interestRecalculationCompoundingTypeBlock = new WebMarkupBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingTypeBlock);
        this.interestRecalculationCompoundingTypeIContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeIContainer");
        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeIContainer);
        this.interestRecalculationCompoundingTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationCompoundingTypeField = new Select2SingleChoice<>("interestRecalculationCompoundingTypeField", new PropertyModel<>(this, "interestRecalculationCompoundingTypeValue"), this.interestRecalculationCompoundingTypeProvider);
        this.interestRecalculationCompoundingTypeField.setLabel(Model.of("Frequency type for compounding"));
        this.interestRecalculationCompoundingTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingTypeIContainer.add(this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeFeedback = new TextFeedbackPanel("interestRecalculationCompoundingTypeFeedback", this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeIContainer.add(this.interestRecalculationCompoundingTypeFeedback);

        this.interestRecalculationCompoundingDayBlock = new WebMarkupBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingDayBlock);
        this.interestRecalculationCompoundingDayIContainer = new WebMarkupContainer("interestRecalculationCompoundingDayIContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayIContainer);
        this.interestRecalculationCompoundingDayProvider = new FrequencyDayProvider();
        this.interestRecalculationCompoundingDayField = new Select2SingleChoice<>("interestRecalculationCompoundingDayField", new PropertyModel<>(this, "interestRecalculationCompoundingDayValue"), this.interestRecalculationCompoundingDayProvider);
        this.interestRecalculationCompoundingDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingDayIContainer.add(this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingDayFeedback", this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayIContainer.add(this.interestRecalculationCompoundingDayFeedback);

        this.interestRecalculationCompoundingOnDayIContainer = new WebMarkupContainer("interestRecalculationCompoundingOnDayIContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingOnDayIContainer);
        this.interestRecalculationCompoundingOnDayProvider = new OnDayProvider();
        this.interestRecalculationCompoundingOnDayField = new Select2SingleChoice<>("interestRecalculationCompoundingOnDayField", new PropertyModel<>(this, "interestRecalculationCompoundingOnDayValue"), this.interestRecalculationCompoundingOnDayProvider);
        this.interestRecalculationCompoundingOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingOnDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingOnDayIContainer.add(this.interestRecalculationCompoundingOnDayField);
        this.interestRecalculationCompoundingOnDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnDayFeedback", this.interestRecalculationCompoundingOnDayField);
        this.interestRecalculationCompoundingOnDayIContainer.add(this.interestRecalculationCompoundingOnDayFeedback);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalIContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalIContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalIContainer);
        this.interestRecalculationCompoundingIntervalField = new TextField<>("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalField.setLabel(Model.of("Frequency Interval for compounding"));
        this.interestRecalculationCompoundingIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingIntervalIContainer.add(this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalFeedback = new TextFeedbackPanel("interestRecalculationCompoundingIntervalFeedback", this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalIContainer.add(this.interestRecalculationCompoundingIntervalFeedback);

        this.interestRecalculationRecalculateBlock = new WebMarkupBlock("interestRecalculationRecalculateBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateBlock);
        this.interestRecalculationRecalculateIContainer = new WebMarkupContainer("interestRecalculationRecalculateIContainer");
        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateIContainer);
        this.interestRecalculationRecalculateProvider = new FrequencyProvider();
        this.interestRecalculationRecalculateField = new Select2SingleChoice<>("interestRecalculationRecalculateField", new PropertyModel<>(this, "interestRecalculationRecalculateValue"), this.interestRecalculationRecalculateProvider);
        this.interestRecalculationRecalculateField.setLabel(Model.of("Frequency for recalculate Outstanding Principle"));
        this.interestRecalculationRecalculateField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateIContainer.add(this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateFeedback = new TextFeedbackPanel("interestRecalculationRecalculateFeedback", this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateIContainer.add(this.interestRecalculationRecalculateFeedback);

        this.interestRecalculationRecalculateTypeBlock = new WebMarkupBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateTypeBlock);
        this.interestRecalculationRecalculateTypeIContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeIContainer");
        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeIContainer);
        this.interestRecalculationRecalculateTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationRecalculateTypeField = new Select2SingleChoice<>("interestRecalculationRecalculateTypeField", new PropertyModel<>(this, "interestRecalculationRecalculateTypeValue"), this.interestRecalculationRecalculateTypeProvider);
        this.interestRecalculationRecalculateTypeField.setLabel(Model.of("Frequency type for recalculate"));
        this.interestRecalculationRecalculateTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateTypeIContainer.add(this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeFeedback = new TextFeedbackPanel("interestRecalculationRecalculateTypeFeedback", this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeIContainer.add(this.interestRecalculationRecalculateTypeFeedback);

        this.interestRecalculationRecalculateDayBlock = new WebMarkupBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateDayBlock);
        this.interestRecalculationRecalculateDayIContainer = new WebMarkupContainer("interestRecalculationRecalculateDayIContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayIContainer);
        this.interestRecalculationRecalculateDayProvider = new FrequencyDayProvider();
        this.interestRecalculationRecalculateDayField = new Select2SingleChoice<>("interestRecalculationRecalculateDayField", new PropertyModel<>(this, "interestRecalculationRecalculateDayValue"), this.interestRecalculationRecalculateDayProvider);
        this.interestRecalculationRecalculateDayField.setLabel(Model.of("Frequency day for recalculate"));
        this.interestRecalculationRecalculateDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateDayIContainer.add(this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateDayFeedback", this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayIContainer.add(this.interestRecalculationRecalculateDayFeedback);

        this.interestRecalculationRecalculateOnDayIContainer = new WebMarkupContainer("interestRecalculationRecalculateOnDayIContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateOnDayIContainer);
        this.interestRecalculationRecalculateOnDayProvider = new OnDayProvider();
        this.interestRecalculationRecalculateOnDayField = new Select2SingleChoice<>("interestRecalculationRecalculateOnDayField", new PropertyModel<>(this, "interestRecalculationRecalculateOnDayValue"), this.interestRecalculationRecalculateOnDayProvider);
        this.interestRecalculationRecalculateOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationRecalculateOnDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateOnDayIContainer.add(this.interestRecalculationRecalculateOnDayField);
        this.interestRecalculationRecalculateOnDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateOnDayFeedback", this.interestRecalculationRecalculateOnDayField);
        this.interestRecalculationRecalculateOnDayIContainer.add(this.interestRecalculationRecalculateOnDayFeedback);

        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateIntervalBlock);
        this.interestRecalculationRecalculateIntervalIContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalIContainer");
        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalIContainer);
        this.interestRecalculationRecalculateIntervalField = new TextField<>("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalField.setLabel(Model.of("Frequency Interval for recalculate"));
        this.interestRecalculationRecalculateIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateIntervalIContainer.add(this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalFeedback = new TextFeedbackPanel("interestRecalculationRecalculateIntervalFeedback", this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalIContainer.add(this.interestRecalculationRecalculateIntervalFeedback);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField = new CheckBox("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback", this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback);

    }

    protected boolean interestRecalculationRecalculateFieldUpdate(AjaxRequestTarget target) {
        this.interestRecalculationRecalculateTypeIContainer.setVisible(false);
        this.interestRecalculationRecalculateDayIContainer.setVisible(false);
        this.interestRecalculationRecalculateIntervalIContainer.setVisible(false);
        this.interestRecalculationRecalculateOnDayIContainer.setVisible(false);

        if (this.interestRecalculationRecalculateValue != null) {
            Frequency frequency = Frequency.valueOf(this.interestRecalculationRecalculateValue.getId());
            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateIntervalIContainer.setVisible(true);
            }
            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateDayIContainer.setVisible(true);
            }
            if (frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateTypeIContainer.setVisible(true);

                if (this.interestRecalculationRecalculateTypeValue != null) {
                    FrequencyType type = FrequencyType.valueOf(this.interestRecalculationRecalculateTypeValue.getId());
                    this.interestRecalculationRecalculateDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                    this.interestRecalculationRecalculateOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                }
            }
        }
        if (target != null) {
            target.add(this.interestRecalculationRecalculateTypeBlock);
            target.add(this.interestRecalculationRecalculateDayBlock);
            target.add(this.interestRecalculationRecalculateIntervalBlock);
        }
        return false;
    }

    protected boolean interestRecalculationCompoundingFieldUpdate(AjaxRequestTarget target) {
        this.interestRecalculationCompoundingTypeIContainer.setVisible(false);
        this.interestRecalculationCompoundingDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingOnDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingIntervalIContainer.setVisible(false);

        if (this.interestRecalculationCompoundingOnValue != null && InterestRecalculationCompound.valueOf(this.interestRecalculationCompoundingOnValue.getId()) != InterestRecalculationCompound.None) {
            this.interestRecalculationCompoundingIContainer.setVisible(true);

            if (this.interestRecalculationCompoundingValue != null) {
                Frequency frequency = Frequency.valueOf(this.interestRecalculationCompoundingValue.getId());
                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingIntervalIContainer.setVisible(true);
                }
                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingDayIContainer.setVisible(true);
                }
                if (frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingTypeIContainer.setVisible(true);

                    if (this.interestRecalculationCompoundingTypeValue != null) {
                        FrequencyType type = FrequencyType.valueOf(this.interestRecalculationCompoundingTypeValue.getId());
                        this.interestRecalculationCompoundingDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                        this.interestRecalculationCompoundingOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                    }
                }
            }
        } else {
            this.interestRecalculationCompoundingIContainer.setVisible(false);
        }
        if (target != null) {
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.interestRecalculationCompoundingIntervalBlock);
            target.add(this.interestRecalculationCompoundingBlock);
        }
        return false;
    }

    protected void initSectionSetting() {

        this.settingAmortizationBlock = new WebMarkupBlock("settingAmortizationBlock", Size.Six_6);
        this.form.add(this.settingAmortizationBlock);
        this.settingAmortizationIContainer = new WebMarkupContainer("settingAmortizationIContainer");
        this.settingAmortizationBlock.add(this.settingAmortizationIContainer);
        this.settingAmortizationProvider = new AmortizationProvider();
        this.settingAmortizationField = new Select2SingleChoice<>("settingAmortizationField", new PropertyModel<>(this, "settingAmortizationValue"), this.settingAmortizationProvider);
        this.settingAmortizationField.setLabel(Model.of("Amortization"));
        this.settingAmortizationField.add(new OnChangeAjaxBehavior());
        this.settingAmortizationIContainer.add(this.settingAmortizationField);
        this.settingAmortizationFeedback = new TextFeedbackPanel("settingAmortizationFeedback", this.settingAmortizationField);
        this.settingAmortizationIContainer.add(this.settingAmortizationFeedback);

        this.settingInterestMethodBlock = new WebMarkupBlock("settingInterestMethodBlock", Size.Six_6);
        this.form.add(this.settingInterestMethodBlock);
        this.settingInterestMethodIContainer = new WebMarkupContainer("settingInterestMethodIContainer");
        this.settingInterestMethodBlock.add(this.settingInterestMethodIContainer);
        this.settingInterestMethodProvider = new InterestMethodProvider();
        this.settingInterestMethodField = new Select2SingleChoice<>("settingInterestMethodField", new PropertyModel<>(this, "settingInterestMethodValue"), this.settingInterestMethodProvider);
        this.settingInterestMethodField.setLabel(Model.of("Interest method"));
        this.settingInterestMethodField.add(new OnChangeAjaxBehavior());
        this.settingInterestMethodIContainer.add(this.settingInterestMethodField);
        this.settingInterestMethodFeedback = new TextFeedbackPanel("settingInterestMethodFeedback", this.settingInterestMethodField);
        this.settingInterestMethodIContainer.add(this.settingInterestMethodFeedback);

        this.settingInterestCalculationPeriodBlock = new WebMarkupBlock("settingInterestCalculationPeriodBlock", Size.Six_6);
        this.form.add(this.settingInterestCalculationPeriodBlock);
        this.settingInterestCalculationPeriodIContainer = new WebMarkupContainer("settingInterestCalculationPeriodIContainer");
        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodIContainer);
        this.settingInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
        this.settingInterestCalculationPeriodField = new Select2SingleChoice<>("settingInterestCalculationPeriodField", new PropertyModel<>(this, "settingInterestCalculationPeriodValue"), this.settingInterestCalculationPeriodProvider);
        this.settingInterestCalculationPeriodField.setLabel(Model.of("Interest calculation period"));
        this.settingInterestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::settingInterestCalculationPeriodFieldUpdate));
        this.settingInterestCalculationPeriodIContainer.add(this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodFeedback = new TextFeedbackPanel("settingInterestCalculationPeriodFeedback", this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodIContainer.add(this.settingInterestCalculationPeriodFeedback);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
        this.form.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodIContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodIContainer);
        this.settingCalculateInterestForExactDaysInPartialPeriodField = new CheckBox("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodField.add(new OnChangeAjaxBehavior());
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodFeedback = new TextFeedbackPanel("settingCalculateInterestForExactDaysInPartialPeriodFeedback", this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodFeedback);

        this.settingRepaymentStrategyBlock = new WebMarkupBlock("settingRepaymentStrategyBlock", Size.Six_6);
        this.form.add(this.settingRepaymentStrategyBlock);
        this.settingRepaymentStrategyIContainer = new WebMarkupContainer("settingRepaymentStrategyIContainer");
        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyIContainer);
        this.settingRepaymentStrategyProvider = new RepaymentStrategyProvider();
        this.settingRepaymentStrategyField = new Select2SingleChoice<>("settingRepaymentStrategyField", new PropertyModel<>(this, "settingRepaymentStrategyValue"), this.settingRepaymentStrategyProvider);
        this.settingRepaymentStrategyField.setLabel(Model.of("Repayment strategy"));
        this.settingRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.settingRepaymentStrategyIContainer.add(this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyFeedback = new TextFeedbackPanel("settingRepaymentStrategyFeedback", this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyIContainer.add(this.settingRepaymentStrategyFeedback);

        this.settingMoratoriumPrincipleBlock = new WebMarkupBlock("settingMoratoriumPrincipleBlock", Size.Six_6);
        this.form.add(this.settingMoratoriumPrincipleBlock);
        this.settingMoratoriumPrincipleIContainer = new WebMarkupContainer("settingMoratoriumPrincipleIContainer");
        this.settingMoratoriumPrincipleBlock.add(this.settingMoratoriumPrincipleIContainer);
        this.settingMoratoriumPrincipleField = new TextField<>("settingMoratoriumPrincipleField", new PropertyModel<>(this, "settingMoratoriumPrincipleValue"));
        this.settingMoratoriumPrincipleField.setLabel(Model.of("Moratorium principle"));
        this.settingMoratoriumPrincipleField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumPrincipleIContainer.add(this.settingMoratoriumPrincipleField);
        this.settingMoratoriumPrincipleFeedback = new TextFeedbackPanel("settingMoratoriumPrincipleFeedback", this.settingMoratoriumPrincipleField);
        this.settingMoratoriumPrincipleIContainer.add(this.settingMoratoriumPrincipleFeedback);

        this.settingMoratoriumInterestBlock = new WebMarkupBlock("settingMoratoriumInterestBlock", Size.Six_6);
        this.form.add(this.settingMoratoriumInterestBlock);
        this.settingMoratoriumInterestIContainer = new WebMarkupContainer("settingMoratoriumInterestIContainer");
        this.settingMoratoriumInterestBlock.add(this.settingMoratoriumInterestIContainer);
        this.settingMoratoriumInterestField = new TextField<>("settingMoratoriumInterestField", new PropertyModel<>(this, "settingMoratoriumInterestValue"));
        this.settingMoratoriumInterestField.setLabel(Model.of("Moratorium interest"));
        this.settingMoratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumInterestIContainer.add(this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestFeedback = new TextFeedbackPanel("settingMoratoriumInterestFeedback", this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestIContainer.add(this.settingMoratoriumInterestFeedback);

        this.settingInterestFreePeriodBlock = new WebMarkupBlock("settingInterestFreePeriodBlock", Size.Six_6);
        this.form.add(this.settingInterestFreePeriodBlock);
        this.settingInterestFreePeriodIContainer = new WebMarkupContainer("settingInterestFreePeriodIContainer");
        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodIContainer);
        this.settingInterestFreePeriodField = new TextField<>("settingInterestFreePeriodField", new PropertyModel<>(this, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodField.setLabel(Model.of("Interest free period"));
        this.settingInterestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.settingInterestFreePeriodIContainer.add(this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodFeedback = new TextFeedbackPanel("settingInterestFreePeriodFeedback", this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodIContainer.add(this.settingInterestFreePeriodFeedback);

        this.settingArrearsToleranceBlock = new WebMarkupBlock("settingArrearsToleranceBlock", Size.Six_6);
        this.form.add(this.settingArrearsToleranceBlock);
        this.settingArrearsToleranceIContainer = new WebMarkupContainer("settingArrearsToleranceIContainer");
        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceIContainer);
        this.settingArrearsToleranceField = new TextField<>("settingArrearsToleranceField", new PropertyModel<>(this, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceField.setLabel(Model.of("Arrears tolerance"));
        this.settingArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.settingArrearsToleranceIContainer.add(this.settingArrearsToleranceField);
        this.settingArrearsToleranceFeedback = new TextFeedbackPanel("settingArrearsToleranceFeedback", this.settingArrearsToleranceField);
        this.settingArrearsToleranceIContainer.add(this.settingArrearsToleranceFeedback);

        this.settingDayInYearBlock = new WebMarkupBlock("settingDayInYearBlock", Size.Six_6);
        this.form.add(this.settingDayInYearBlock);
        this.settingDayInYearIContainer = new WebMarkupContainer("settingDayInYearIContainer");
        this.settingDayInYearBlock.add(this.settingDayInYearIContainer);
        this.settingDayInYearProvider = new DayInYearProvider();
        this.settingDayInYearField = new Select2SingleChoice<>("settingDayInYearField", new PropertyModel<>(this, "settingDayInYearValue"), this.settingDayInYearProvider);
        this.settingDayInYearField.setLabel(Model.of("Day in year"));
        this.settingDayInYearField.add(new OnChangeAjaxBehavior());
        this.settingDayInYearIContainer.add(this.settingDayInYearField);
        this.settingDayInYearFeedback = new TextFeedbackPanel("settingDayInYearFeedback", this.settingDayInYearField);
        this.settingDayInYearIContainer.add(this.settingDayInYearFeedback);

        this.settingDayInMonthBlock = new WebMarkupBlock("settingDayInMonthBlock", Size.Six_6);
        this.form.add(this.settingDayInMonthBlock);
        this.settingDayInMonthIContainer = new WebMarkupContainer("settingDayInMonthIContainer");
        this.settingDayInMonthBlock.add(this.settingDayInMonthIContainer);
        this.settingDayInMonthProvider = new DayInMonthProvider();
        this.settingDayInMonthField = new Select2SingleChoice<>("settingDayInMonthField", new PropertyModel<>(this, "settingDayInMonthValue"), this.settingDayInMonthProvider);
        this.settingDayInMonthField.setLabel(Model.of("Day in month"));
        this.settingDayInMonthField.add(new OnChangeAjaxBehavior());
        this.settingDayInMonthIContainer.add(this.settingDayInMonthField);
        this.settingDayInMonthFeedback = new TextFeedbackPanel("settingDayInMonthFeedback", this.settingDayInMonthField);
        this.settingDayInMonthIContainer.add(this.settingDayInMonthFeedback);

        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Twelve_12);
        this.form.add(this.settingAllowFixingOfTheInstallmentAmountBlock);
        this.settingAllowFixingOfTheInstallmentAmountIContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountIContainer");
        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountIContainer);
        this.settingAllowFixingOfTheInstallmentAmountField = new CheckBox("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());
        this.settingAllowFixingOfTheInstallmentAmountIContainer.add(this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel("settingAllowFixingOfTheInstallmentAmountFeedback", this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountIContainer.add(this.settingAllowFixingOfTheInstallmentAmountFeedback);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setLabel(Model.of("Number of days a loan may be overdue before moving into arrears"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback", this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Six_6);
        this.form.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setLabel(Model.of("Maximum number of days a loan may be overdue before becoming a NPA"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback", this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
        this.form.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback", this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);

        this.settingPrincipleThresholdForLastInstalmentBlock = new WebMarkupBlock("settingPrincipleThresholdForLastInstalmentBlock", Size.Six_6);
        this.form.add(this.settingPrincipleThresholdForLastInstalmentBlock);
        this.settingPrincipleThresholdForLastInstalmentIContainer = new WebMarkupContainer("settingPrincipleThresholdForLastInstalmentIContainer");
        this.settingPrincipleThresholdForLastInstalmentBlock.add(this.settingPrincipleThresholdForLastInstalmentIContainer);
        this.settingPrincipleThresholdForLastInstalmentField = new TextField<>("settingPrincipleThresholdForLastInstalmentField", new PropertyModel<>(this, "settingPrincipleThresholdForLastInstalmentValue"));
        this.settingPrincipleThresholdForLastInstalmentField.setLabel(Model.of("Principle Threshold (%) for Last Installment"));
        this.settingPrincipleThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.settingPrincipleThresholdForLastInstalmentIContainer.add(this.settingPrincipleThresholdForLastInstalmentField);
        this.settingPrincipleThresholdForLastInstalmentFeedback = new TextFeedbackPanel("settingPrincipleThresholdForLastInstalmentFeedback", this.settingPrincipleThresholdForLastInstalmentField);
        this.settingPrincipleThresholdForLastInstalmentIContainer.add(this.settingPrincipleThresholdForLastInstalmentFeedback);

        this.settingVariableInstallmentsAllowedBlock = new WebMarkupBlock("settingVariableInstallmentsAllowedBlock", Size.Six_6);
        this.form.add(this.settingVariableInstallmentsAllowedBlock);
        this.settingVariableInstallmentsAllowedIContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedIContainer");
        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedIContainer);
        this.settingVariableInstallmentsAllowedField = new CheckBox("settingVariableInstallmentsAllowedField", new PropertyModel<>(this, "settingVariableInstallmentsAllowedValue"));
        this.settingVariableInstallmentsAllowedField.add(new OnChangeAjaxBehavior(this::settingVariableInstallmentsAllowedFieldUpdate));
        this.settingVariableInstallmentsAllowedIContainer.add(this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedFeedback = new TextFeedbackPanel("settingVariableInstallmentsAllowedFeedback", this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedIContainer.add(this.settingVariableInstallmentsAllowedFeedback);

        this.settingVariableInstallmentsMinimumBlock = new WebMarkupBlock("settingVariableInstallmentsMinimumBlock", Size.Six_6);
        this.form.add(this.settingVariableInstallmentsMinimumBlock);
        this.settingVariableInstallmentsMinimumIContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumIContainer");
        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumIContainer);
        this.settingVariableInstallmentsMinimumField = new TextField<>("settingVariableInstallmentsMinimumField", new PropertyModel<>(this, "settingVariableInstallmentsMinimumValue"));
        this.settingVariableInstallmentsMinimumField.setLabel(Model.of("Variable Installments Minimum"));
        this.settingVariableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMinimumIContainer.add(this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMinimumFeedback", this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumIContainer.add(this.settingVariableInstallmentsMinimumFeedback);

        this.settingVariableInstallmentsMaximumBlock = new WebMarkupBlock("settingVariableInstallmentsMaximumBlock", Size.Six_6);
        this.form.add(this.settingVariableInstallmentsMaximumBlock);
        this.settingVariableInstallmentsMaximumIContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumIContainer");
        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumIContainer);
        this.settingVariableInstallmentsMaximumField = new TextField<>("settingVariableInstallmentsMaximumField", new PropertyModel<>(this, "settingVariableInstallmentsMaximumValue"));
        this.settingVariableInstallmentsMaximumField.setLabel(Model.of("Variable Installments Maximum"));
        this.settingVariableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMaximumIContainer.add(this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMaximumFeedback", this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumIContainer.add(this.settingVariableInstallmentsMaximumFeedback);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Twelve_12);
        this.form.add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansIContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansIContainer);
        this.settingAllowedToBeUsedForProvidingTopupLoansField = new CheckBox("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansField.add(new OnChangeAjaxBehavior());
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansFeedback = new TextFeedbackPanel("settingAllowedToBeUsedForProvidingTopupLoansFeedback", this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansFeedback);

    }

    protected void initSectionDefault() {
        termVaryBasedOnLoanCycleFieldUpdate(null);

        settingInterestCalculationPeriodFieldUpdate(null);

        interestRecalculationRecalculateInterestFieldUpdate(null);

        interestRecalculationCompoundingFieldUpdate(null);

        interestRecalculationRecalculateFieldUpdate(null);

        guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(null);

        loanTrancheDetailEnableMultipleDisbursalFieldUpdate(null);

        configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(null);

        accountingFieldUpdate(null);

        settingVariableInstallmentsAllowedFieldUpdate(null);

        termLinkedToFloatingInterestRatesFieldUpdate(null);
    }

    protected void initSectionCurrency() {

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeIContainer = new WebMarkupContainer("currencyCodeIContainer");
        this.currencyCodeBlock.add(this.currencyCodeIContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeIContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceIContainer = new WebMarkupContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceIContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal Places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyInMultipleOfBlock = new WebMarkupBlock("currencyInMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyInMultipleOfBlock);
        this.currencyInMultipleOfIContainer = new WebMarkupContainer("currencyInMultipleOfIContainer");
        this.currencyInMultipleOfBlock.add(this.currencyInMultipleOfIContainer);
        this.currencyInMultipleOfField = new TextField<>("currencyInMultipleOfField", new PropertyModel<>(this, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfField.setLabel(Model.of("Currency in multiple of"));
        this.currencyInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInMultipleOfIContainer.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback", this.currencyInMultipleOfField);
        this.currencyInMultipleOfIContainer.add(this.currencyInMultipleOfFeedback);

        this.currencyInstallmentInMultipleOfBlock = new WebMarkupBlock("currencyInstallmentInMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyInstallmentInMultipleOfBlock);
        this.currencyInstallmentInMultipleOfIContainer = new WebMarkupContainer("currencyInstallmentInMultipleOfIContainer");
        this.currencyInstallmentInMultipleOfBlock.add(this.currencyInstallmentInMultipleOfIContainer);
        this.currencyInstallmentInMultipleOfField = new TextField<>("currencyInstallmentInMultipleOfField", new PropertyModel<>(this, "currencyInstallmentInMultipleOfValue"));
        this.currencyInstallmentInMultipleOfField.setLabel(Model.of("Installment in multiple of"));
        this.currencyInstallmentInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInstallmentInMultipleOfField.add(RangeValidator.minimum((long) 1));
        this.currencyInstallmentInMultipleOfIContainer.add(this.currencyInstallmentInMultipleOfField);
        this.currencyInstallmentInMultipleOfFeedback = new TextFeedbackPanel("currencyInstallmentInMultipleOfFeedback", this.currencyInstallmentInMultipleOfField);
        this.currencyInstallmentInMultipleOfIContainer.add(this.currencyInstallmentInMultipleOfFeedback);
    }

    protected void initSectionTerms() {

        this.termVaryBasedOnLoanCycleBlock = new WebMarkupBlock("termVaryBasedOnLoanCycleBlock", Size.Twelve_12);
        this.form.add(this.termVaryBasedOnLoanCycleBlock);
        this.termVaryBasedOnLoanCycleIContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleIContainer");
        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleIContainer);
        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField", new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
        this.termVaryBasedOnLoanCycleIContainer.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback", this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleIContainer.add(this.termVaryBasedOnLoanCycleFeedback);

        this.termPrincipleMinimumBlock = new WebMarkupBlock("termPrincipleMinimumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMinimumBlock);
        this.termPrincipleMinimumIContainer = new WebMarkupContainer("termPrincipleMinimumIContainer");
        this.termPrincipleMinimumBlock.add(this.termPrincipleMinimumIContainer);
        this.termPrincipleMinimumField = new TextField<>("termPrincipleMinimumField", new PropertyModel<>(this, "termPrincipleMinimumValue"));
        this.termPrincipleMinimumField.setLabel(Model.of("Principle Minimum"));
        this.termPrincipleMinimumField.add(new OnChangeAjaxBehavior());
        this.termPrincipleMinimumIContainer.add(this.termPrincipleMinimumField);
        this.termPrincipleMinimumFeedback = new TextFeedbackPanel("termPrincipleMinimumFeedback", this.termPrincipleMinimumField);
        this.termPrincipleMinimumIContainer.add(this.termPrincipleMinimumFeedback);

        this.termPrincipleDefaultBlock = new WebMarkupBlock("termPrincipleDefaultBlock", Size.Three_3);
        this.form.add(this.termPrincipleDefaultBlock);
        this.termPrincipleDefaultIContainer = new WebMarkupContainer("termPrincipleDefaultIContainer");
        this.termPrincipleDefaultBlock.add(this.termPrincipleDefaultIContainer);
        this.termPrincipleDefaultField = new TextField<>("termPrincipleDefaultField", new PropertyModel<>(this, "termPrincipleDefaultValue"));
        this.termPrincipleDefaultField.setLabel(Model.of("Principle Default"));
        this.termPrincipleDefaultField.add(new OnChangeAjaxBehavior());
        this.termPrincipleDefaultIContainer.add(this.termPrincipleDefaultField);
        this.termPrincipleDefaultFeedback = new TextFeedbackPanel("termPrincipleDefaultFeedback", this.termPrincipleDefaultField);
        this.termPrincipleDefaultIContainer.add(this.termPrincipleDefaultFeedback);

        this.termPrincipleMaximumBlock = new WebMarkupBlock("termPrincipleMaximumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMaximumBlock);
        this.termPrincipleMaximumIContainer = new WebMarkupContainer("termPrincipleMaximumIContainer");
        this.termPrincipleMaximumBlock.add(this.termPrincipleMaximumIContainer);
        this.termPrincipleMaximumField = new TextField<>("termPrincipleMaximumField", new PropertyModel<>(this, "termPrincipleMaximumValue"));
        this.termPrincipleMaximumField.setLabel(Model.of("Principle Maximum"));
        this.termPrincipleMaximumField.add(new OnChangeAjaxBehavior());
        this.termPrincipleMaximumIContainer.add(this.termPrincipleMaximumField);
        this.termPrincipleMaximumFeedback = new TextFeedbackPanel("termPrincipleMaximumFeedback", this.termPrincipleMaximumField);
        this.termPrincipleMaximumIContainer.add(this.termPrincipleMaximumFeedback);

        {
            this.termPrincipleByLoanCyclePopup = new ModalWindow("termPrincipleByLoanCyclePopup");
            add(this.termPrincipleByLoanCyclePopup);
            this.termPrincipleByLoanCyclePopup.setOnClose(this::termPrincipleByLoanCyclePopupClose);

            this.termPrincipleByLoanCycleBlock = new WebMarkupBlock("termPrincipleByLoanCycleBlock", Size.Twelve_12);
            this.form.add(this.termPrincipleByLoanCycleBlock);
            this.termPrincipleByLoanCycleIContainer = new WebMarkupContainer("termPrincipleByLoanCycleIContainer");
            this.termPrincipleByLoanCycleBlock.add(this.termPrincipleByLoanCycleIContainer);

            this.termPrincipleByLoanCycleColumn = Lists.newArrayList();
            this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipleByLoanCycleColumn));
            this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipleByLoanCycleColumn));
            this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipleByLoanCycleColumn));
            this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipleByLoanCycleColumn));
            this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipleByLoanCycleColumn));
            this.termPrincipleByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termPrincipleByLoanCycleAction, this::termPrincipleByLoanCycleClick));
            this.termPrincipleByLoanCycleProvider = new ListDataProvider(this.termPrincipleByLoanCycleValue);
            this.termPrincipleByLoanCycleTable = new DataTable<>("termPrincipleByLoanCycleTable", this.termPrincipleByLoanCycleColumn, this.termPrincipleByLoanCycleProvider, 20);
            this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleTable);
            this.termPrincipleByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipleByLoanCycleTable, this.termPrincipleByLoanCycleProvider));
            this.termPrincipleByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipleByLoanCycleTable));

            this.termPrincipleByLoanCycleAddLink = new AjaxLink<>("termPrincipleByLoanCycleAddLink");
            this.termPrincipleByLoanCycleAddLink.setOnClick(this::termPrincipleByLoanCycleAddLinkClick);
            this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleAddLink);
        }

        this.termNumberOfRepaymentMinimumBlock = new WebMarkupBlock("termNumberOfRepaymentMinimumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMinimumBlock);
        this.termNumberOfRepaymentMinimumIContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumIContainer");
        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumIContainer);
        this.termNumberOfRepaymentMinimumField = new TextField<>("termNumberOfRepaymentMinimumField", new PropertyModel<>(this, "termNumberOfRepaymentMinimumValue"));
        this.termNumberOfRepaymentMinimumField.setLabel(Model.of("Number of repayment Minimum"));
        this.termNumberOfRepaymentMinimumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMinimumIContainer.add(this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMinimumFeedback", this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumIContainer.add(this.termNumberOfRepaymentMinimumFeedback);

        this.termNumberOfRepaymentDefaultBlock = new WebMarkupBlock("termNumberOfRepaymentDefaultBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentDefaultBlock);
        this.termNumberOfRepaymentDefaultIContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultIContainer");
        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultIContainer);
        this.termNumberOfRepaymentDefaultField = new TextField<>("termNumberOfRepaymentDefaultField", new PropertyModel<>(this, "termNumberOfRepaymentDefaultValue"));
        this.termNumberOfRepaymentDefaultField.setLabel(Model.of("Number of repayment Default"));
        this.termNumberOfRepaymentDefaultField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentDefaultIContainer.add(this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultFeedback = new TextFeedbackPanel("termNumberOfRepaymentDefaultFeedback", this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultIContainer.add(this.termNumberOfRepaymentDefaultFeedback);

        this.termNumberOfRepaymentMaximumBlock = new WebMarkupBlock("termNumberOfRepaymentMaximumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMaximumBlock);
        this.termNumberOfRepaymentMaximumIContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumIContainer");
        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumIContainer);
        this.termNumberOfRepaymentMaximumField = new TextField<>("termNumberOfRepaymentMaximumField", new PropertyModel<>(this, "termNumberOfRepaymentMaximumValue"));
        this.termNumberOfRepaymentMaximumField.setLabel(Model.of("Number of repayment Maximum"));
        this.termNumberOfRepaymentMaximumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMaximumIContainer.add(this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMaximumFeedback", this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumIContainer.add(this.termNumberOfRepaymentMaximumFeedback);

        // Table
        {
            this.termNumberOfRepaymentByLoanCyclePopup = new ModalWindow("termNumberOfRepaymentByLoanCyclePopup");
            add(this.termNumberOfRepaymentByLoanCyclePopup);
            this.termNumberOfRepaymentByLoanCyclePopup.setOnClose(this::termNumberOfRepaymentByLoanCyclePopupClose);

            this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
            this.form.add(this.termNumberOfRepaymentByLoanCycleBlock);
            this.termNumberOfRepaymentByLoanCycleIContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleIContainer");
            this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleIContainer);

            this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
            this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
            this.termNumberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNumberOfRepaymentByLoanCycleAction, this::termNumberOfRepaymentByLoanCycleClick));
            this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue);
            this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
            this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
            this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
            this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));

            this.termNumberOfRepaymentByLoanCycleAddLink = new AjaxLink<>("termNumberOfRepaymentByLoanCycleAddLink");
            this.termNumberOfRepaymentByLoanCycleAddLink.setOnClick(this::termNumberOfRepaymentByLoanCycleAddLinkClick);
            this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleAddLink);
        }

        // Linked to Floating Interest Rates
        this.termLinkedToFloatingInterestRatesBlock = new WebMarkupBlock("termLinkedToFloatingInterestRatesBlock", Size.Twelve_12);
        this.form.add(this.termLinkedToFloatingInterestRatesBlock);
        this.termLinkedToFloatingInterestRatesIContainer = new WebMarkupContainer("termLinkedToFloatingInterestRatesIContainer");
        this.termLinkedToFloatingInterestRatesBlock.add(this.termLinkedToFloatingInterestRatesIContainer);
        this.termLinkedToFloatingInterestRatesField = new CheckBox("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this, "termLinkedToFloatingInterestRatesValue"));
        this.termLinkedToFloatingInterestRatesField.add(new OnChangeAjaxBehavior(this::termLinkedToFloatingInterestRatesFieldUpdate));
        this.termLinkedToFloatingInterestRatesIContainer.add(this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("termLinkedToFloatingInterestRatesFeedback", this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesIContainer.add(this.termLinkedToFloatingInterestRatesFeedback);

        this.termNominalInterestRateMinimumBlock = new WebMarkupBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateMinimumBlock);
        this.termNominalInterestRateMinimumIContainer = new WebMarkupContainer("termNominalInterestRateMinimumIContainer");
        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumIContainer);
        this.termNominalInterestRateMinimumField = new TextField<>("termNominalInterestRateMinimumField", new PropertyModel<>(this, "termNominalInterestRateMinimumValue"));
        this.termNominalInterestRateMinimumField.setLabel(Model.of("Nominal interest rate minimum"));
        this.termNominalInterestRateMinimumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMinimumIContainer.add(this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumFeedback = new TextFeedbackPanel("termNominalInterestRateMinimumFeedback", this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumIContainer.add(this.termNominalInterestRateMinimumFeedback);

        this.termNominalInterestRateDefaultBlock = new WebMarkupBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateDefaultBlock);
        this.termNominalInterestRateDefaultIContainer = new WebMarkupContainer("termNominalInterestRateDefaultIContainer");
        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultIContainer);
        this.termNominalInterestRateDefaultField = new TextField<>("termNominalInterestRateDefaultField", new PropertyModel<>(this, "termNominalInterestRateDefaultValue"));
        this.termNominalInterestRateDefaultField.setLabel(Model.of("Nominal interest rate Default"));
        this.termNominalInterestRateDefaultField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateDefaultIContainer.add(this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultFeedback = new TextFeedbackPanel("termNominalInterestRateDefaultFeedback", this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultIContainer.add(this.termNominalInterestRateDefaultFeedback);

        this.termNominalInterestRateMaximumBlock = new WebMarkupBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateMaximumBlock);
        this.termNominalInterestRateMaximumIContainer = new WebMarkupContainer("termNominalInterestRateMaximumIContainer");
        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumIContainer);
        this.termNominalInterestRateMaximumField = new TextField<>("termNominalInterestRateMaximumField", new PropertyModel<>(this, "termNominalInterestRateMaximumValue"));
        this.termNominalInterestRateMaximumField.setLabel(Model.of("Nominal interest rate Maximum"));
        this.termNominalInterestRateMaximumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMaximumIContainer.add(this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumFeedback = new TextFeedbackPanel("termNominalInterestRateMaximumFeedback", this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumIContainer.add(this.termNominalInterestRateMaximumFeedback);

        this.termNominalInterestRateTypeBlock = new WebMarkupBlock("termNominalInterestRateTypeBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateTypeBlock);
        this.termNominalInterestRateTypeIContainer = new WebMarkupContainer("termNominalInterestRateTypeIContainer");
        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeIContainer);
        this.termNominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
        this.termNominalInterestRateTypeField = new Select2SingleChoice<>("termNominalInterestRateTypeField", new PropertyModel<>(this, "termNominalInterestRateTypeValue"), this.termNominalInterestRateTypeProvider);
        this.termNominalInterestRateTypeField.setLabel(Model.of("Type"));
        this.termNominalInterestRateTypeField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateTypeIContainer.add(this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeFeedback = new TextFeedbackPanel("termNominalInterestRateTypeFeedback", this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeIContainer.add(this.termNominalInterestRateTypeFeedback);

        this.termFloatingInterestRateBlock = new WebMarkupBlock("termFloatingInterestRateBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestRateBlock);
        this.termFloatingInterestRateIContainer = new WebMarkupContainer("termFloatingInterestRateIContainer");
        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateIContainer);
        this.termFloatingInterestRateProvider = new SingleChoiceProvider(MFloatingRates.NAME, MFloatingRates.Field.ID, MFloatingRates.Field.NAME);
        this.termFloatingInterestRateField = new Select2SingleChoice<>("termFloatingInterestRateField", new PropertyModel<>(this, "termFloatingInterestRateValue"), this.termFloatingInterestRateProvider);
        this.termFloatingInterestRateField.setLabel(Model.of("Floating interest rate"));
        this.termFloatingInterestRateField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestRateIContainer.add(this.termFloatingInterestRateField);
        this.termFloatingInterestRateFeedback = new TextFeedbackPanel("termFloatingInterestRateFeedback", this.termFloatingInterestRateField);
        this.termFloatingInterestRateIContainer.add(this.termFloatingInterestRateFeedback);

        this.termFloatingInterestDifferentialBlock = new WebMarkupBlock("termFloatingInterestDifferentialBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestDifferentialBlock);
        this.termFloatingInterestDifferentialIContainer = new WebMarkupContainer("termFloatingInterestDifferentialIContainer");
        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialIContainer);
        this.termFloatingInterestDifferentialField = new TextField<>("termFloatingInterestDifferentialField", new PropertyModel<>(this, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialField.setLabel(Model.of("Floating interest differential rate"));
        this.termFloatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDifferentialIContainer.add(this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialFeedback = new TextFeedbackPanel("termFloatingInterestDifferentialFeedback", this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialIContainer.add(this.termFloatingInterestDifferentialFeedback);

        this.termFloatingInterestAllowedBlock = new WebMarkupBlock("termFloatingInterestAllowedBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestAllowedBlock);
        this.termFloatingInterestAllowedIContainer = new WebMarkupContainer("termFloatingInterestAllowedIContainer");
        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedIContainer);
        this.termFloatingInterestAllowedField = new CheckBox("termFloatingInterestAllowedField", new PropertyModel<>(this, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestAllowedIContainer.add(this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedFeedback = new TextFeedbackPanel("termFloatingInterestAllowedFeedback", this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedIContainer.add(this.termFloatingInterestAllowedFeedback);

        this.termFloatingInterestMinimumBlock = new WebMarkupBlock("termFloatingInterestMinimumBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestMinimumBlock);
        this.termFloatingInterestMinimumIContainer = new WebMarkupContainer("termFloatingInterestMinimumIContainer");
        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumIContainer);
        this.termFloatingInterestMinimumField = new TextField<>("termFloatingInterestMinimumField", new PropertyModel<>(this, "termFloatingInterestMinimumValue"));
        this.termFloatingInterestMinimumField.setLabel(Model.of("Floating interest minimum"));
        this.termFloatingInterestMinimumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMinimumIContainer.add(this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumFeedback = new TextFeedbackPanel("termFloatingInterestMinimumFeedback", this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumIContainer.add(this.termFloatingInterestMinimumFeedback);

        this.termFloatingInterestDefaultBlock = new WebMarkupBlock("termFloatingInterestDefaultBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestDefaultBlock);
        this.termFloatingInterestDefaultIContainer = new WebMarkupContainer("termFloatingInterestDefaultIContainer");
        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultIContainer);
        this.termFloatingInterestDefaultField = new TextField<>("termFloatingInterestDefaultField", new PropertyModel<>(this, "termFloatingInterestDefaultValue"));
        this.termFloatingInterestDefaultField.setLabel(Model.of("Floating interest Default"));
        this.termFloatingInterestDefaultField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDefaultIContainer.add(this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultFeedback = new TextFeedbackPanel("termFloatingInterestDefaultFeedback", this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultIContainer.add(this.termFloatingInterestDefaultFeedback);

        this.termFloatingInterestMaximumBlock = new WebMarkupBlock("termFloatingInterestMaximumBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestMaximumBlock);
        this.termFloatingInterestMaximumIContainer = new WebMarkupContainer("termFloatingInterestMaximumIContainer");
        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumIContainer);
        this.termFloatingInterestMaximumField = new TextField<>("termFloatingInterestMaximumField", new PropertyModel<>(this, "termFloatingInterestMaximumValue"));
        this.termFloatingInterestMaximumField.setLabel(Model.of("Floating interest Maximum"));
        this.termFloatingInterestMaximumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMaximumIContainer.add(this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumFeedback = new TextFeedbackPanel("termFloatingInterestMaximumFeedback", this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumIContainer.add(this.termFloatingInterestMaximumFeedback);

        // Table
        {
            this.termNominalInterestRateByLoanCycleBlock = new WebMarkupBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
            this.form.add(this.termNominalInterestRateByLoanCycleBlock);
            this.termNominalInterestRateByLoanCycleIContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleIContainer");
            this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleIContainer);

            this.termNominalInterestRateByLoanCyclePopup = new ModalWindow("termNominalInterestRateByLoanCyclePopup");
            add(this.termNominalInterestRateByLoanCyclePopup);
            this.termNominalInterestRateByLoanCyclePopup.setOnClose(this::termNominalInterestRateByLoanCyclePopupClose);

            List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNominalInterestRateByLoanCycleActionItem, this::termNominalInterestRateByLoanCycleActionClick));
            this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue);
            this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
            this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleTable);
            this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
            this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));

            this.termNominalInterestRateByLoanCycleAddLink = new AjaxLink<>("termNominalInterestRateByLoanCycleAddLink");
            this.termNominalInterestRateByLoanCycleAddLink.setOnClick(this::termNominalInterestRateByLoanCycleAddLinkClick);
            this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleAddLink);
        }

        this.termRepaidEveryBlock = new WebMarkupBlock("termRepaidEveryBlock", Size.One_1);
        this.form.add(this.termRepaidEveryBlock);
        this.termRepaidEveryIContainer = new WebMarkupContainer("termRepaidEveryIContainer");
        this.termRepaidEveryBlock.add(this.termRepaidEveryIContainer);
        this.termRepaidEveryField = new TextField<>("termRepaidEveryField", new PropertyModel<>(this, "termRepaidEveryValue"));
        this.termRepaidEveryField.setLabel(Model.of("Repaid every"));
        this.termRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.termRepaidEveryIContainer.add(this.termRepaidEveryField);
        this.termRepaidEveryFeedback = new TextFeedbackPanel("termRepaidEveryFeedback", this.termRepaidEveryField);
        this.termRepaidEveryIContainer.add(this.termRepaidEveryFeedback);

        this.termRepaidTypeBlock = new WebMarkupBlock("termRepaidTypeBlock", Size.Two_2);
        this.termRepaidTypeProvider = new LockInTypeProvider(LockInType.Day, LockInType.Week, LockInType.Month);
        this.form.add(this.termRepaidTypeBlock);
        this.termRepaidTypeIContainer = new WebMarkupContainer("termRepaidTypeIContainer");
        this.termRepaidTypeBlock.add(this.termRepaidTypeIContainer);
        this.termRepaidTypeField = new Select2SingleChoice<>("termRepaidTypeField", new PropertyModel<>(this, "termRepaidTypeValue"), this.termRepaidTypeProvider);
        this.termRepaidTypeField.setLabel(Model.of("Repaid Type"));
        this.termRepaidTypeField.add(new OnChangeAjaxBehavior());
        this.termRepaidTypeIContainer.add(this.termRepaidTypeField);
        this.termRepaidTypeFeedback = new TextFeedbackPanel("termRepaidTypeFeedback", this.termRepaidTypeField);
        this.termRepaidTypeIContainer.add(this.termRepaidTypeFeedback);

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
        this.form.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setLabel(Model.of("Minimum days between disbursal and first repayment date"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel("termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback", this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);
    }

    protected boolean termNominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.termNominalInterestRateByLoanCyclePopup.setContent(new InterestLoanCyclePopup("termNominalInterestRateByLoanCycle", this.popupModel));
        this.termNominalInterestRateByLoanCyclePopup.show(target);
        return false;
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

    protected void termNominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termNominalInterestRateByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termNominalInterestRateByLoanCycleValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNominalInterestRateByLoanCycleValue.remove(index);
        }
        target.add(this.termNominalInterestRateByLoanCycleTable);
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

    protected boolean termNumberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.termNumberOfRepaymentByLoanCyclePopup.setContent(new RepaymentLoanCyclePopup("termNumberOfRepaymentByLoanCycle", this.popupModel));
        this.termNumberOfRepaymentByLoanCyclePopup.show(target);
        return false;
    }

    protected void termNumberOfRepaymentByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termNumberOfRepaymentByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termNumberOfRepaymentByLoanCycleValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNumberOfRepaymentByLoanCycleValue.remove(index);
        }
        target.add(this.termNumberOfRepaymentByLoanCycleTable);
    }

    protected List<ActionItem> termNumberOfRepaymentByLoanCycleAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean termPrincipleByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.termPrincipleByLoanCyclePopup.setContent(new PrincipleLoanCyclePopup("termPrincipleByLoanCycle", this.popupModel));
        this.termPrincipleByLoanCyclePopup.show(target);
        return false;
    }

    protected void termNominalInterestRateByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("when", this.popupModel.get("whenValue"));
        item.put("cycle", this.popupModel.get("loanCycleValue"));
        item.put("minimum", this.popupModel.get("minimumValue"));
        item.put("default", this.popupModel.get("defaultValue"));
        item.put("maximum", this.popupModel.get("maximumValue"));
        this.termNominalInterestRateByLoanCycleValue.add(item);
        target.add(this.termNominalInterestRateByLoanCycleBlock);
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

    protected void termPrincipleByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termPrincipleByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termPrincipleByLoanCycleValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termPrincipleByLoanCycleValue.remove(index);
        }
        target.add(this.termPrincipleByLoanCycleTable);
    }

    protected List<ActionItem> termPrincipleByLoanCycleAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean termLinkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        boolean notLinked = this.termLinkedToFloatingInterestRatesValue == null ? true : !this.termLinkedToFloatingInterestRatesValue;
        this.termNominalInterestRateMinimumIContainer.setVisible(notLinked);
        this.termNominalInterestRateDefaultIContainer.setVisible(notLinked);
        this.termNominalInterestRateMaximumIContainer.setVisible(notLinked);
        this.termNominalInterestRateTypeIContainer.setVisible(notLinked);

        this.termFloatingInterestRateIContainer.setVisible(!notLinked);
        this.termFloatingInterestDifferentialIContainer.setVisible(!notLinked);
        this.termFloatingInterestAllowedIContainer.setVisible(!notLinked);
        this.termFloatingInterestMinimumIContainer.setVisible(!notLinked);
        this.termFloatingInterestDefaultIContainer.setVisible(!notLinked);
        this.termFloatingInterestMaximumIContainer.setVisible(!notLinked);

        if (target != null) {
            target.add(this.termNominalInterestRateMinimumBlock);
            target.add(this.termNominalInterestRateDefaultBlock);
            target.add(this.termNominalInterestRateMaximumBlock);
            target.add(this.termNominalInterestRateTypeBlock);
            target.add(this.termFloatingInterestRateBlock);
            target.add(this.termFloatingInterestDifferentialBlock);
            target.add(this.termFloatingInterestAllowedBlock);
            target.add(this.termFloatingInterestMinimumBlock);
            target.add(this.termFloatingInterestDefaultBlock);
            target.add(this.termFloatingInterestMaximumBlock);
        }
        return false;
    }

    protected boolean settingInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (this.settingInterestCalculationPeriodValue != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
        }

        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment;

        if (interestCalculationPeriod == InterestCalculationPeriod.Daily) {
            this.interestRecalculationRecalculateInterestIContainer.setVisible(true);
        } else if (interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
            this.interestRecalculationRecalculateInterestIContainer.setVisible(false);
        }

        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
            target.add(this.interestRecalculationRecalculateInterestBlock);
        }

        interestRecalculationRecalculateInterestFieldUpdate(target);

        initSectionValidationRule();

        return false;
    }

    protected boolean termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        this.termPrincipleByLoanCycleIContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.termNumberOfRepaymentByLoanCycleIContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.termNominalInterestRateByLoanCycleIContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        if (target != null) {
            target.add(this.termPrincipleByLoanCycleBlock);
            target.add(this.termNumberOfRepaymentByLoanCycleBlock);
            target.add(this.termNominalInterestRateByLoanCycleBlock);
        }
        return false;
    }

    protected boolean settingVariableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingVariableInstallmentsAllowedValue != null && this.settingVariableInstallmentsAllowedValue;
        this.settingVariableInstallmentsMinimumIContainer.setVisible(visible);
        this.settingVariableInstallmentsMaximumIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingVariableInstallmentsMinimumBlock);
            target.add(this.settingVariableInstallmentsMaximumBlock);
        }
        return false;
    }

    protected void termNumberOfRepaymentByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("when", this.popupModel.get("whenValue"));
        item.put("cycle", this.popupModel.get("loanCycleValue"));
        item.put("minimum", this.popupModel.get("minimumValue"));
        item.put("default", this.popupModel.get("defaultValue"));
        item.put("maximum", this.popupModel.get("maximumValue"));
        this.termNumberOfRepaymentByLoanCycleValue.add(item);
        target.add(this.termNumberOfRepaymentByLoanCycleBlock);
    }

    protected void termPrincipleByLoanCyclePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("when", this.popupModel.get("whenValue"));
        item.put("cycle", this.popupModel.get("loanCycleValue"));
        item.put("minimum", this.popupModel.get("minimumValue"));
        item.put("default", this.popupModel.get("defaultValue"));
        item.put("maximum", this.popupModel.get("maximumValue"));
        this.termPrincipleByLoanCycleValue.add(item);
        target.add(this.termPrincipleByLoanCycleBlock);
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
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRulePenaltyIncomeValue.add(item);
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected void overdueChargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        Option charge = (Option) this.popupModel.get("overdueChargeValue");
        for (Map<String, Object> temp : this.overdueChargeValue) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addField(MCharge.Field.ID);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, charge.getId());
        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
        Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
        item.put("uuid", charge.getId());
        item.put("charge", charge);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.overdueChargeValue.add(item);
        target.add(this.overdueChargeTable);
    }

    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        Option charge = (Option) this.popupModel.get("chargeValue");
        for (Map<String, Object> temp : this.chargeValue) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addField(MCharge.Field.ID);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, charge.getId());
        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

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

    protected void saveButtonSubmit(Button button) {
        ProductLoanBuilder builder = new ProductLoanBuilder();

        // Detail
        builder.withName(this.detailProductNameValue);
        builder.withShortName(this.detailShortNameValue);
        builder.withDescription(this.detailDescriptionValue);
        if (this.detailFundValue != null) {
            builder.withFundId(this.detailFundValue.getId());
        }
        builder.withIncludeInBorrowerCycle(this.detailIncludeInCustomerLoanCounterValue);
        builder.withStartDate(this.detailStartDateValue);
        builder.withCloseDate(this.detailCloseDateValue);

        // Currency
        if (this.currencyCodeValue != null) {
            builder.withCurrencyCode(this.currencyCodeValue.getId());
        }
        builder.withDigitsAfterDecimal(this.currencyDecimalPlaceValue);
        builder.withInMultiplesOf(this.currencyInMultipleOfValue);
        builder.withInstallmentAmountInMultiplesOf(this.currencyInstallmentInMultipleOfValue);

        // Terms

        boolean useBorrowerCycle = this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue;
        builder.withUseBorrowerCycle(useBorrowerCycle);
        builder.withMinPrinciple(this.termPrincipleMinimumValue);
        builder.withPrinciple(this.termPrincipleDefaultValue);
        builder.withMaxPrinciple(this.termPrincipleMaximumValue);
        builder.withMinNumberOfRepayments(this.termNumberOfRepaymentMinimumValue);
        builder.withNumberOfRepayments(this.termNumberOfRepaymentDefaultValue);
        builder.withMaxNumberOfRepayments(this.termNumberOfRepaymentMaximumValue);

        boolean termLinkedToFloatingInterestRates = this.termLinkedToFloatingInterestRatesValue == null ? false : this.termLinkedToFloatingInterestRatesValue;
        builder.withLinkedToFloatingInterestRates(termLinkedToFloatingInterestRates);

        if (termLinkedToFloatingInterestRates) {
            builder.withMinDifferentialLendingRate(this.termFloatingInterestMinimumValue);
            builder.withDefaultDifferentialLendingRate(this.termFloatingInterestDefaultValue);
            builder.withMaxDifferentialLendingRate(this.termFloatingInterestMaximumValue);
            if (this.termFloatingInterestRateValue != null) {
                builder.withFloatingRatesId(this.termFloatingInterestRateValue.getId());
            }
            builder.withInterestRateDifferential(this.termFloatingInterestDifferentialValue);
            builder.withFloatingInterestRateCalculationAllowed(this.termFloatingInterestAllowedValue == null ? false : this.termFloatingInterestAllowedValue);
        } else {
            builder.withMinInterestRatePerPeriod(this.termNominalInterestRateMinimumValue);
            builder.withInterestRatePerPeriod(this.termNominalInterestRateDefaultValue);
            builder.withMaxInterestRatePerPeriod(this.termNominalInterestRateMaximumValue);
            if (this.termNominalInterestRateTypeValue != null) {
                builder.withInterestRateFrequencyType(NominalInterestRateType.valueOf(this.termNominalInterestRateTypeValue.getId()));
            }
        }

        if (useBorrowerCycle) {
            if (this.termPrincipleByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termPrincipleByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Long borrowerCycleNumber = (Long) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withPrincipleVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
            if (this.termNumberOfRepaymentByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termNumberOfRepaymentByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Long borrowerCycleNumber = (Long) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withNumberOfRepaymentVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
            if (this.termNominalInterestRateByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termNominalInterestRateByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Long borrowerCycleNumber = (Long) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withInterestRateVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
        }

        builder.withRepaymentEvery(this.termRepaidEveryValue);
        if (this.termRepaidTypeValue != null) {
            builder.withRepaymentFrequencyType(LockInType.valueOf(this.termRepaidTypeValue.getId()));
        }
        builder.withMinimumDaysBetweenDisbursalAndFirstRepayment(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue);

        // Settings

        if (this.settingAmortizationValue != null) {
            builder.withAmortizationType(Amortization.valueOf(this.settingAmortizationValue.getId()));
        }
        if (this.settingInterestMethodValue != null) {
            builder.withInterestType(InterestMethod.valueOf(this.settingInterestMethodValue.getId()));
        }
        if (this.settingInterestCalculationPeriodValue != null) {
            InterestCalculationPeriod settingInterestCalculationPeriod = InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId());
            builder.withInterestCalculationPeriodType(settingInterestCalculationPeriod);
            if (settingInterestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
                builder.withAllowPartialPeriodInterestCalcualtion(this.settingCalculateInterestForExactDaysInPartialPeriodValue == null ? false : this.settingCalculateInterestForExactDaysInPartialPeriodValue);
            }
            if (settingInterestCalculationPeriod == InterestCalculationPeriod.Daily) {

                boolean interestRecalculationEnabled = this.interestRecalculationRecalculateInterestValue == null ? false : this.interestRecalculationRecalculateInterestValue;
                builder.withInterestRecalculationEnabled(interestRecalculationEnabled);
                if (interestRecalculationEnabled) {

                    if (this.interestRecalculationPreClosureInterestCalculationRuleValue != null) {
                        builder.withPreClosureInterestCalculationStrategy(ClosureInterestCalculationRule.valueOf(this.interestRecalculationPreClosureInterestCalculationRuleValue.getId()));
                    }
                    if (this.interestRecalculationAdvancePaymentsAdjustmentTypeValue != null) {
                        builder.withRescheduleStrategyMethod(AdvancePaymentsAdjustmentType.valueOf(this.interestRecalculationAdvancePaymentsAdjustmentTypeValue.getId()));
                    }

                    if (this.interestRecalculationCompoundingOnValue != null) {
                        InterestRecalculationCompound interestRecalculationCompound = InterestRecalculationCompound.valueOf(this.interestRecalculationCompoundingOnValue.getId());
                        builder.withInterestRecalculationCompoundingMethod(interestRecalculationCompound);

                        if (interestRecalculationCompound == InterestRecalculationCompound.Fee || interestRecalculationCompound == InterestRecalculationCompound.Interest || interestRecalculationCompound == InterestRecalculationCompound.FeeAndInterest) {
                            if (this.interestRecalculationCompoundingValue != null) {
                                Frequency compoundingValue = Frequency.valueOf(this.interestRecalculationCompoundingValue.getId());
                                builder.withRecalculationCompoundingFrequencyType(compoundingValue);
                                if (compoundingValue == Frequency.Daily) {
                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
                                } else if (compoundingValue == Frequency.Weekly) {
                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
                                    if (this.interestRecalculationCompoundingDayValue != null) {
                                        builder.withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationCompoundingDayValue.getId()));
                                    }
                                } else if (compoundingValue == Frequency.Monthly) {
                                    builder.withRecalculationCompoundingFrequencyInterval(this.interestRecalculationCompoundingIntervalValue);
                                    if (this.interestRecalculationCompoundingTypeValue != null) {
                                        FrequencyType type = FrequencyType.valueOf(this.interestRecalculationCompoundingTypeValue.getId());
                                        if (type != null) {
                                            builder.withRecalculationCompoundingFrequencyNthDayType(type);
                                            if (type == FrequencyType.OnDay) {
                                                if (this.interestRecalculationCompoundingOnDayValue != null) {
                                                    builder.withRecalculationCompoundingFrequencyOnDayType(this.interestRecalculationCompoundingOnDayValue.getId());
                                                }
                                            } else {
                                                if (this.interestRecalculationCompoundingDayValue != null) {
                                                    FrequencyDay day = FrequencyDay.valueOf(this.interestRecalculationCompoundingDayValue.getId());
                                                    builder.withRecalculationCompoundingFrequencyDayOfWeekType(day);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (this.interestRecalculationRecalculateValue != null) {
                            Frequency recalculateValue = Frequency.valueOf(this.interestRecalculationRecalculateValue.getId());
                            builder.withRecalculationRestFrequencyType(recalculateValue);
                            if (recalculateValue == Frequency.Daily) {
                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
                            } else if (recalculateValue == Frequency.Weekly) {
                                if (this.interestRecalculationRecalculateDayValue != null) {
                                    builder.withRecalculationRestFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId()));
                                }
                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
                            } else if (recalculateValue == Frequency.Monthly) {
                                builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);

                                if (this.interestRecalculationRecalculateTypeValue != null) {
                                    FrequencyType type = FrequencyType.valueOf(this.interestRecalculationRecalculateTypeValue.getId());
                                    if (type != null) {
                                        builder.withRecalculationRestFrequencyNthDayType(type);
                                        if (type == FrequencyType.OnDay) {
                                            if (this.interestRecalculationRecalculateOnDayValue != null) {
                                                builder.withRecalculationRestFrequencyOnDayType(this.interestRecalculationRecalculateOnDayValue.getId());
                                            }
                                        } else {
                                            if (this.interestRecalculationRecalculateDayValue != null) {
                                                FrequencyDay day = FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId());
                                                builder.withRecalculationRestFrequencyDayOfWeekType(day);
                                            }
                                        }
                                    }
                                }
                                if (this.interestRecalculationRecalculateTypeValue != null) {
                                    builder.withRecalculationRestFrequencyNthDayType(FrequencyType.valueOf(this.interestRecalculationRecalculateTypeValue.getId()));
                                }
                                if (this.interestRecalculationRecalculateDayValue != null) {
                                    builder.withRecalculationRestFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId()));
                                }
                            }
                        }
                        builder.withArrearsBasedOnOriginalSchedule(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue == null ? false : this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue);
                    }

                }
            } else if (settingInterestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
                builder.withInterestRecalculationEnabled(false);
            }
        }
        builder.withGraceOnPrinciplePayment(this.settingMoratoriumPrincipleValue);
        builder.withGraceOnInterestPayment(this.settingMoratoriumInterestValue);
        builder.withGraceOnInterestCharged(this.settingInterestFreePeriodValue);
        builder.withInArrearsTolerance(this.settingArrearsToleranceValue);

        builder.withCanDefineInstallmentAmount(this.settingAllowFixingOfTheInstallmentAmountValue == null ? false : this.settingAllowFixingOfTheInstallmentAmountValue);

        builder.withGraceOnArrearsAgeing(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue);

        builder.withOverdueDaysForNPA(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue);

        builder.withMinimumGap(this.settingVariableInstallmentsMinimumValue);
        builder.withMaximumGap(this.settingVariableInstallmentsMaximumValue);
        builder.withAccountMovesOutOfNPAOnlyOnArrearsCompletion(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue == null ? false : this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue);
        builder.withAllowVariableInstallments(this.settingVariableInstallmentsAllowedValue == null ? false : this.settingVariableInstallmentsAllowedValue);
        builder.withCanUseForTopup(this.settingAllowedToBeUsedForProvidingTopupLoansValue == null ? false : this.settingAllowedToBeUsedForProvidingTopupLoansValue);

        if (this.settingRepaymentStrategyValue != null) {
            builder.withTransactionProcessingStrategyId(RepaymentStrategy.valueOf(this.settingRepaymentStrategyValue.getId()));
        }

        if (this.settingDayInYearValue != null) {
            builder.withDaysInYearType(DayInYear.valueOf(this.settingDayInYearValue.getId()));
        }

        if (this.settingDayInMonthValue != null) {
            builder.withDaysInMonthType(DayInMonth.valueOf(this.settingDayInMonthValue.getId()));
        }

        builder.withPrincipleThresholdForLastInstallment(this.settingPrincipleThresholdForLastInstalmentValue);

        // Guarantee Requirements

        boolean holdGuaranteeFunds = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue == null ? false : this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
        builder.withHoldGuaranteeFunds(holdGuaranteeFunds);
        if (holdGuaranteeFunds) {
            builder.withMandatoryGuarantee(this.guaranteeRequirementMandatoryGuaranteeValue);
            builder.withMinimumGuaranteeFromGuarantor(this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue);
            builder.withMinimumGuaranteeFromOwnFunds(this.guaranteeRequirementMinimumGuaranteeValue);
        }

        // Loan Tranche Details

        boolean multiDisburseLoan = this.loanTrancheDetailEnableMultipleDisbursalValue == null ? false : this.loanTrancheDetailEnableMultipleDisbursalValue;
        builder.withMultiDisburseLoan(multiDisburseLoan);
        if (multiDisburseLoan) {
            builder.withOutstandingLoanBalance(this.loanTrancheDetailMaximumAllowedOutstandingBalanceValue);
            builder.withMaxTrancheCount(this.loanTrancheDetailMaximumTrancheCountValue);
        }

        // Configurable Terms and Settings
        boolean configurable = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue == null ? false : this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
        AllowAttributeOverrideBuilder allowAttributeOverrideBuilder = new AllowAttributeOverrideBuilder();
        if (configurable) {
            allowAttributeOverrideBuilder.withAmortizationType(this.configurableAmortizationValue == null ? false : this.configurableAmortizationValue);
            allowAttributeOverrideBuilder.withTransactionProcessingStrategyId(this.configurableRepaymentStrategyValue == null ? false : this.configurableRepaymentStrategyValue);
            allowAttributeOverrideBuilder.withInArrearsTolerance(this.configurableArrearsToleranceValue == null ? false : this.configurableArrearsToleranceValue);
            allowAttributeOverrideBuilder.withGraceOnPrincipleAndInterestPayment(this.configurableMoratoriumValue == null ? false : this.configurableMoratoriumValue);
            allowAttributeOverrideBuilder.withInterestType(this.configurableInterestMethodValue == null ? false : this.configurableInterestMethodValue);
            allowAttributeOverrideBuilder.withInterestCalculationPeriodType(this.configurableInterestCalculationPeriodValue == null ? false : this.configurableInterestCalculationPeriodValue);
            allowAttributeOverrideBuilder.withRepaymentEvery(this.configurableRepaidEveryValue == null ? false : this.configurableRepaidEveryValue);
            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(this.configurableOverdueBeforeMovingValue == null ? false : this.configurableOverdueBeforeMovingValue);
        } else {
            allowAttributeOverrideBuilder.withAmortizationType(false);
            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(false);
            allowAttributeOverrideBuilder.withGraceOnPrincipleAndInterestPayment(false);
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
                Option charge = (Option) item.get("charge");
                builder.withCharges(charge.getId());
            }
        }

        // Overdue Charge

        if (this.overdueChargeValue != null && !this.overdueChargeValue.isEmpty()) {
            for (Map<String, Object> item : this.overdueChargeValue) {
                Option charge = (Option) item.get("charge");
                builder.withCharges(charge.getId());
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (AccountingType.None.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.None);
        } else if (AccountingType.Cash.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Cash);
        } else if (AccountingType.Periodic.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Periodic);
        } else if (AccountingType.Upfront.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Upfront);
        }
        if (AccountingType.Cash.getDescription().equals(accounting)) {
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
            if (this.cashIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
            }
            if (this.cashIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.cashIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.cashLossWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.cashLossWrittenOffValue.getId());
            }
            if (this.cashOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.cashOverPaymentLiabilityValue.getId());
            }
        } else if (AccountingType.Periodic.getDescription().equals(accounting)) {
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
            if (this.periodicIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.periodicIncomeFromPenaltyValue.getId());
            }
            if (this.periodicIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.periodicIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.periodicLossWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.periodicLossWrittenOffValue.getId());
            }
            if (this.periodicOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.periodicOverPaymentLiabilityValue.getId());
            }
            if (this.periodicInterestReceivableValue != null) {
                builder.withReceivableInterestAccountId(this.periodicInterestReceivableValue.getId());
            }
            if (this.periodicFeeReceivableValue != null) {
                builder.withReceivableFeeAccountId(this.periodicFeeReceivableValue.getId());
            }
            if (this.periodicPenaltyReceivableValue != null) {
                builder.withReceivablePenaltyAccountId(this.periodicPenaltyReceivableValue.getId());
            }
        } else if (AccountingType.Upfront.getDescription().equals(accounting)) {
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
            if (this.upfrontIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.upfrontIncomeFromPenaltyValue.getId());
            }
            if (this.upfrontIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.upfrontIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.upfrontLossWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.upfrontLossWrittenOffValue.getId());
            }
            if (this.upfrontOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.upfrontOverPaymentLiabilityValue.getId());
            }
            if (this.upfrontInterestReceivableValue != null) {
                builder.withReceivableInterestAccountId(this.upfrontInterestReceivableValue.getId());
            }
            if (this.upfrontFeeReceivableValue != null) {
                builder.withReceivableFeeAccountId(this.upfrontFeeReceivableValue.getId());
            }
            if (this.upfrontPenaltyReceivableValue != null) {
                builder.withReceivablePenaltyAccountId(this.upfrontPenaltyReceivableValue.getId());
            }
        }

        if (AccountingType.Cash.getDescription().equals(accounting) || AccountingType.Periodic.getDescription().equals(accounting) || AccountingType.Upfront.getDescription().equals(accounting)) {
            if (this.advancedAccountingRuleFundSourceValue != null && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
                    Option payment = (Option) item.get("payment");
                    Option account = (Option) item.get("account");
                    builder.withPaymentChannelToFundSourceMappings(payment.getId(), account.getId());
                }
            }
            if (this.advancedAccountingRuleFeeIncomeValue != null && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
                    Option charge = (Option) item.get("charge");
                    Option account = (Option) item.get("account");
                    builder.withFeeToIncomeAccountMappings(charge.getId(), account.getId());
                }
            }
            if (this.advancedAccountingRulePenaltyIncomeValue != null && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
                    Option charge = (Option) item.get("charge");
                    Option account = (Option) item.get("account");
                    builder.withPenaltyToIncomeAccountMappings(charge.getId(), account.getId());
                }
            }
        }

        JsonNode node = LoanHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(LoanBrowsePage.class);
    }

}
