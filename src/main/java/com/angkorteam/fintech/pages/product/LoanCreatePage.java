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
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
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
import com.angkorteam.fintech.popup.OverdueChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.popup.loan.InterestLoanCyclePopup;
import com.angkorteam.fintech.popup.loan.PrincipalLoanCyclePopup;
import com.angkorteam.fintech.popup.loan.RepaymentLoanCyclePopup;
import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
import com.angkorteam.fintech.provider.RepaidTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.loan.AdvancePaymentsAdjustmentTypeProvider;
import com.angkorteam.fintech.provider.loan.AmortizationProvider;
import com.angkorteam.fintech.provider.loan.ClosureInterestCalculationRuleProvider;
import com.angkorteam.fintech.provider.loan.DayInMonthProvider;
import com.angkorteam.fintech.provider.loan.DayInYearProvider;
import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
import com.angkorteam.fintech.provider.loan.FrequencyProvider;
import com.angkorteam.fintech.provider.loan.FrequencyTypeProvider;
import com.angkorteam.fintech.provider.loan.InterestCalculationPeriodProvider;
import com.angkorteam.fintech.provider.loan.InterestMethodProvider;
import com.angkorteam.fintech.provider.loan.InterestRecalculationCompoundProvider;
import com.angkorteam.fintech.provider.loan.RepaymentStrategyProvider;
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
public class LoanCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";
    public static final String ACC_PERIODIC = "Periodic";
    public static final String ACC_UPFRONT = "Upfront";

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

    private WebMarkupContainer detailFundBlock;
    private WebMarkupContainer detailFundContainer;
    private SingleChoiceProvider detailFundProvider;
    private Option detailFundValue;
    private Select2SingleChoice<Option> detailFundField;
    private TextFeedbackPanel detailFundFeedback;

    private WebMarkupContainer detailStartDateBlock;
    private WebMarkupContainer detailStartDateContainer;
    private Date detailStartDateValue;
    private DateTextField detailStartDateField;
    private TextFeedbackPanel detailStartDateFeedback;

    private WebMarkupContainer detailCloseDateBlock;
    private WebMarkupContainer detailCloseDateContainer;
    private Date detailCloseDateValue;
    private DateTextField detailCloseDateField;
    private TextFeedbackPanel detailCloseDateFeedback;

    private WebMarkupContainer detailIncludeInCustomerLoanCounterBlock;
    private WebMarkupContainer detailIncludeInCustomerLoanCounterContainer;
    private Boolean detailIncludeInCustomerLoanCounterValue;
    private CheckBox detailIncludeInCustomerLoanCounterField;
    private TextFeedbackPanel detailIncludeInCustomerLoanCounterFeedback;

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

    private WebMarkupContainer currencyInMultipleOfBlock;
    private WebMarkupContainer currencyInMultipleOfContainer;
    private Integer currencyInMultipleOfValue;
    private TextField<Integer> currencyInMultipleOfField;
    private TextFeedbackPanel currencyInMultipleOfFeedback;

    private WebMarkupContainer currencyInstallmentInMultipleOfBlock;
    private WebMarkupContainer currencyInstallmentInMultipleOfContainer;
    private Integer currencyInstallmentInMultipleOfValue;
    private TextField<Integer> currencyInstallmentInMultipleOfField;
    private TextFeedbackPanel currencyInstallmentInMultipleOfFeedback;

    // Terms

    // Row 1 : Terms vary based on loan cycle
    private WebMarkupContainer termVaryBasedOnLoanCycleBlock;
    private WebMarkupContainer termVaryBasedOnLoanCycleContainer;
    private Boolean termVaryBasedOnLoanCycleValue;
    private CheckBox termVaryBasedOnLoanCycleField;
    private TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    // Row 2 : Principal
    private WebMarkupContainer termPrincipalMinimumBlock;
    private WebMarkupContainer termPrincipalMinimumContainer;
    private Double termPrincipalMinimumValue;
    private TextField<Double> termPrincipalMinimumField;
    private TextFeedbackPanel termPrincipalMinimumFeedback;

    private WebMarkupContainer termPrincipalDefaultBlock;
    private WebMarkupContainer termPrincipalDefaultContainer;
    private Double termPrincipalDefaultValue;
    private TextField<Double> termPrincipalDefaultField;
    private TextFeedbackPanel termPrincipalDefaultFeedback;

    private WebMarkupContainer termPrincipalMaximumBlock;
    private WebMarkupContainer termPrincipalMaximumContainer;
    private Double termPrincipalMaximumValue;
    private TextField<Double> termPrincipalMaximumField;
    private TextFeedbackPanel termPrincipalMaximumFeedback;

    // Row 2 (Optional) : Principal by loan cycle
    private WebMarkupContainer termPrincipalByLoanCycleBlock;
    private WebMarkupContainer termPrincipalByLoanCycleContainer;
    private List<Map<String, Object>> termPrincipalByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> termPrincipalByLoanCycleTable;
    private ListDataProvider termPrincipalByLoanCycleProvider;
    private AjaxLink<Void> termPrincipalByLoanCycleAddLink;
    private ModalWindow termPrincipalByLoanCyclePopup;

    // Row 3 : Number of repayments
    private WebMarkupContainer termNumberOfRepaymentMinimumBlock;
    private WebMarkupContainer termNumberOfRepaymentMinimumContainer;
    private Double termNumberOfRepaymentMinimumValue;
    private TextField<Integer> termNumberOfRepaymentMinimumField;
    private TextFeedbackPanel termNumberOfRepaymentMinimumFeedback;

    private WebMarkupContainer termNumberOfRepaymentDefaultBlock;
    private WebMarkupContainer termNumberOfRepaymentDefaultContainer;
    private Double termNumberOfRepaymentDefaultValue;
    private TextField<Integer> termNumberOfRepaymentDefaultField;
    private TextFeedbackPanel termNumberOfRepaymentDefaultFeedback;

    private WebMarkupContainer termNumberOfRepaymentMaximumBlock;
    private WebMarkupContainer termNumberOfRepaymentMaximumContainer;
    private Double termNumberOfRepaymentMaximumValue;
    private TextField<Integer> termNumberOfRepaymentMaximumField;
    private TextFeedbackPanel termNumberOfRepaymentMaximumFeedback;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    private WebMarkupContainer termNumberOfRepaymentByLoanCycleBlock;
    private WebMarkupContainer termNumberOfRepaymentByLoanCycleContainer;
    private List<Map<String, Object>> termNumberOfRepaymentByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    private ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
    private AjaxLink<Void> termNumberOfRepaymentByLoanCycleAddLink;
    private ModalWindow termNumberOfRepaymentByLoanCyclePopup;

    // Row 4 : Is Linked to Floating Interest Rates?
    private WebMarkupContainer termLinkedToFloatingInterestRatesBlock;
    private WebMarkupContainer termLinkedToFloatingInterestRatesContainer;
    private Boolean termLinkedToFloatingInterestRatesValue;
    private CheckBox termLinkedToFloatingInterestRatesField;
    private TextFeedbackPanel termLinkedToFloatingInterestRatesFeedback;

    // Row 5 : Nominal interest rate
    private WebMarkupContainer termNominalInterestRateMinimumBlock;
    private WebMarkupContainer termNominalInterestRateMinimumContainer;
    private Double termNominalInterestRateMinimumValue;
    private TextField<Double> termNominalInterestRateMinimumField;
    private TextFeedbackPanel termNominalInterestRateMinimumFeedback;

    private WebMarkupContainer termNominalInterestRateDefaultBlock;
    private WebMarkupContainer termNominalInterestRateDefaultContainer;
    private Double termNominalInterestRateDefaultValue;
    private TextField<Double> termNominalInterestRateDefaultField;
    private TextFeedbackPanel termNominalInterestRateDefaultFeedback;

    private WebMarkupContainer termNominalInterestRateMaximumBlock;
    private WebMarkupContainer termNominalInterestRateMaximumContainer;
    private Double termNominalInterestRateMaximumValue;
    private TextField<Double> termNominalInterestRateMaximumField;
    private TextFeedbackPanel termNominalInterestRateMaximumFeedback;

    private WebMarkupContainer termNominalInterestRateTypeBlock;
    private WebMarkupContainer termNominalInterestRateTypeContainer;
    private NominalInterestRateTypeProvider termNominalInterestRateTypeProvider;
    private Option termNominalInterestRateTypeValue;
    private Select2SingleChoice<Option> termNominalInterestRateTypeField;
    private TextFeedbackPanel termNominalInterestRateTypeFeedback;

    // Row 6
    private WebMarkupContainer termNominalInterestRateByLoanCycleBlock;
    private WebMarkupContainer termNominalInterestRateByLoanCycleContainer;
    private List<Map<String, Object>> termNominalInterestRateByLoanCycleValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    private ListDataProvider termNominalInterestRateByLoanCycleProvider;
    private AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
    private ModalWindow termNominalInterestRateByLoanCyclePopup;

    private WebMarkupContainer termRepaidEveryBlock;
    private WebMarkupContainer termRepaidEveryContainer;
    private Integer termRepaidEveryValue;
    private TextField<Integer> termRepaidEveryField;
    private TextFeedbackPanel termRepaidEveryFeedback;

    private WebMarkupContainer termRepaidTypeBlock;
    private WebMarkupContainer termRepaidTypeContainer;
    private RepaidTypeProvider termRepaidTypeProvider;
    private Option termRepaidTypeValue;
    private Select2SingleChoice<Option> termRepaidTypeField;
    private TextFeedbackPanel termRepaidTypeFeedback;

    private WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    private WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer;
    private Double termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue;
    private TextField<Double> termMinimumDayBetweenDisbursalAndFirstRepaymentDateField;
    private TextFeedbackPanel termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback;

    private WebMarkupContainer termFloatingInterestRateBlock;
    private WebMarkupContainer termFloatingInterestRateContainer;
    private SingleChoiceProvider termFloatingInterestRateProvider;
    private Option termFloatingInterestRateValue;
    private Select2SingleChoice<Option> termFloatingInterestRateField;
    private TextFeedbackPanel termFloatingInterestRateFeedback;

    private WebMarkupContainer termFloatingInterestMinimumBlock;
    private WebMarkupContainer termFloatingInterestMinimumContainer;
    private Double termFloatingInterestMinimumValue;
    private TextField<Double> termFloatingInterestMinimumField;
    private TextFeedbackPanel termFloatingInterestMinimumFeedback;

    private WebMarkupContainer termFloatingInterestDefaultBlock;
    private WebMarkupContainer termFloatingInterestDefaultContainer;
    private Double termFloatingInterestDefaultValue;
    private TextField<Double> termFloatingInterestDefaultField;
    private TextFeedbackPanel termFloatingInterestDefaultFeedback;

    private WebMarkupContainer termFloatingInterestMaximumBlock;
    private WebMarkupContainer termFloatingInterestMaximumContainer;
    private Double termFloatingInterestMaximumValue;
    private TextField<Double> termFloatingInterestMaximumField;
    private TextFeedbackPanel termFloatingInterestMaximumFeedback;

    private WebMarkupContainer termFloatingInterestDifferentialBlock;
    private WebMarkupContainer termFloatingInterestDifferentialContainer;
    private Double termFloatingInterestDifferentialValue;
    private TextField<Double> termFloatingInterestDifferentialField;
    private TextFeedbackPanel termFloatingInterestDifferentialFeedback;

    private WebMarkupContainer termFloatingInterestAllowedBlock;
    private WebMarkupContainer termFloatingInterestAllowedContainer;
    private Boolean termFloatingInterestAllowedValue;
    private CheckBox termFloatingInterestAllowedField;
    private TextFeedbackPanel termFloatingInterestAllowedFeedback;

    private Option itemWhenValue;
    private Integer itemLoanCycleValue;
    private Double itemMinimumValue;
    private Double itemDefaultValue;
    private Double itemMaximumValue;
    private Option itemChargeValue;
    private Option itemOverdueChargeValue;
    private Option itemPaymentValue;
    private Option itemAccountValue;

    // Settings
    private WebMarkupContainer settingAmortizationBlock;
    private WebMarkupContainer settingAmortizationContainer;
    private AmortizationProvider settingAmortizationProvider;
    private Option settingAmortizationValue;
    private Select2SingleChoice<Option> settingAmortizationField;
    private TextFeedbackPanel settingAmortizationFeedback;

    private WebMarkupContainer settingInterestMethodBlock;
    private WebMarkupContainer settingInterestMethodContainer;
    private InterestMethodProvider settingInterestMethodProvider;
    private Option settingInterestMethodValue;
    private Select2SingleChoice<Option> settingInterestMethodField;
    private TextFeedbackPanel settingInterestMethodFeedback;

    private WebMarkupContainer settingInterestCalculationPeriodBlock;
    private WebMarkupContainer settingInterestCalculationPeriodContainer;
    private InterestCalculationPeriodProvider settingInterestCalculationPeriodProvider;
    private Option settingInterestCalculationPeriodValue;
    private Select2SingleChoice<Option> settingInterestCalculationPeriodField;
    private TextFeedbackPanel settingInterestCalculationPeriodFeedback;

    private WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodBlock;
    private WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodContainer;
    private Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    private CheckBox settingCalculateInterestForExactDaysInPartialPeriodField;
    private TextFeedbackPanel settingCalculateInterestForExactDaysInPartialPeriodFeedback;

    private WebMarkupContainer settingRepaymentStrategyBlock;
    private WebMarkupContainer settingRepaymentStrategyContainer;
    private RepaymentStrategyProvider settingRepaymentStrategyProvider;
    private Option settingRepaymentStrategyValue;
    private Select2SingleChoice<Option> settingRepaymentStrategyField;
    private TextFeedbackPanel settingRepaymentStrategyFeedback;

    private WebMarkupContainer settingMoratoriumPrincipalBlock;
    private WebMarkupContainer settingMoratoriumPrincipalContainer;
    private Double settingMoratoriumPrincipalValue;
    private TextField<Double> settingMoratoriumPrincipalField;
    private TextFeedbackPanel settingMoratoriumPrincipalFeedback;

    private WebMarkupContainer settingMoratoriumInterestBlock;
    private WebMarkupContainer settingMoratoriumInterestContainer;
    private Double settingMoratoriumInterestValue;
    private TextField<Double> settingMoratoriumInterestField;
    private TextFeedbackPanel settingMoratoriumInterestFeedback;

    private WebMarkupContainer settingInterestFreePeriodBlock;
    private WebMarkupContainer settingInterestFreePeriodContainer;
    private Double settingInterestFreePeriodValue;
    private TextField<Double> settingInterestFreePeriodField;
    private TextFeedbackPanel settingInterestFreePeriodFeedback;

    private WebMarkupContainer settingArrearsToleranceBlock;
    private WebMarkupContainer settingArrearsToleranceContainer;
    private Double settingArrearsToleranceValue;
    private TextField<Double> settingArrearsToleranceField;
    private TextFeedbackPanel settingArrearsToleranceFeedback;

    private WebMarkupContainer settingDayInYearBlock;
    private WebMarkupContainer settingDayInYearContainer;
    private DayInYearProvider settingDayInYearProvider;
    private Option settingDayInYearValue;
    private Select2SingleChoice<Option> settingDayInYearField;
    private TextFeedbackPanel settingDayInYearFeedback;

    private WebMarkupContainer settingDayInMonthBlock;
    private WebMarkupContainer settingDayInMonthContainer;
    private DayInMonthProvider settingDayInMonthProvider;
    private Option settingDayInMonthValue;
    private Select2SingleChoice<Option> settingDayInMonthField;
    private TextFeedbackPanel settingDayInMonthFeedback;

    private WebMarkupContainer settingAllowFixingOfTheInstallmentAmountBlock;
    private WebMarkupContainer settingAllowFixingOfTheInstallmentAmountContainer;
    private Boolean settingAllowFixingOfTheInstallmentAmountValue;
    private CheckBox settingAllowFixingOfTheInstallmentAmountField;
    private TextFeedbackPanel settingAllowFixingOfTheInstallmentAmountFeedback;

    private WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    private WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer;
    private Double settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
    private TextField<Double> settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField;
    private TextFeedbackPanel settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback;

    private WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    private WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer;
    private Double settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
    private TextField<Double> settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField;
    private TextFeedbackPanel settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback;

    private WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    private WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer;
    private Boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
    private CheckBox settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField;
    private TextFeedbackPanel settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback;

    private WebMarkupContainer settingPrincipalThresholdForLastInstalmentBlock;
    private WebMarkupContainer settingPrincipalThresholdForLastInstalmentContainer;
    private Double settingPrincipalThresholdForLastInstalmentValue;
    private TextField<Double> settingPrincipalThresholdForLastInstalmentField;
    private TextFeedbackPanel settingPrincipalThresholdForLastInstalmentFeedback;

    private WebMarkupContainer settingVariableInstallmentsAllowedBlock;
    private WebMarkupContainer settingVariableInstallmentsAllowedContainer;
    private Boolean settingVariableInstallmentsAllowedValue;
    private CheckBox settingVariableInstallmentsAllowedField;
    private TextFeedbackPanel settingVariableInstallmentsAllowedFeedback;

    private WebMarkupContainer settingVariableInstallmentsMinimumBlock;
    private WebMarkupContainer settingVariableInstallmentsMinimumContainer;
    private Double settingVariableInstallmentsMinimumValue;
    private TextField<Double> settingVariableInstallmentsMinimumField;
    private TextFeedbackPanel settingVariableInstallmentsMinimumFeedback;

    private WebMarkupContainer settingVariableInstallmentsMaximumBlock;
    private WebMarkupContainer settingVariableInstallmentsMaximumContainer;
    private Double settingVariableInstallmentsMaximumValue;
    private TextField<Double> settingVariableInstallmentsMaximumField;
    private TextFeedbackPanel settingVariableInstallmentsMaximumFeedback;

    private WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansBlock;
    private WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansContainer;
    private Boolean settingAllowedToBeUsedForProvidingTopupLoansValue;
    private CheckBox settingAllowedToBeUsedForProvidingTopupLoansField;
    private TextFeedbackPanel settingAllowedToBeUsedForProvidingTopupLoansFeedback;

    // Interest Recalculation

    private WebMarkupContainer interestRecalculationRecalculateInterestBlock;
    private WebMarkupContainer interestRecalculationRecalculateInterestContainer;
    private Boolean interestRecalculationRecalculateInterestValue;
    private CheckBox interestRecalculationRecalculateInterestField;
    private TextFeedbackPanel interestRecalculationRecalculateInterestFeedback;

    private WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleBlock;
    private WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleContainer;
    private ClosureInterestCalculationRuleProvider interestRecalculationPreClosureInterestCalculationRuleProvider;
    private Option interestRecalculationPreClosureInterestCalculationRuleValue;
    private Select2SingleChoice<Option> interestRecalculationPreClosureInterestCalculationRuleField;
    private TextFeedbackPanel interestRecalculationPreClosureInterestCalculationRuleFeedback;

    private WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    private WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeContainer;
    private AdvancePaymentsAdjustmentTypeProvider interestRecalculationAdvancePaymentsAdjustmentTypeProvider;
    private Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
    private Select2SingleChoice<Option> interestRecalculationAdvancePaymentsAdjustmentTypeField;
    private TextFeedbackPanel interestRecalculationAdvancePaymentsAdjustmentTypeFeedback;

    private WebMarkupContainer interestRecalculationCompoundingOnBlock;
    private WebMarkupContainer interestRecalculationCompoundingOnContainer;
    private InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
    private Option interestRecalculationCompoundingOnValue;
    private Select2SingleChoice<Option> interestRecalculationCompoundingOnField;
    private TextFeedbackPanel interestRecalculationCompoundingOnFeedback;

    private WebMarkupContainer interestRecalculationCompoundingBlock;
    private WebMarkupContainer interestRecalculationCompoundingContainer;
    private FrequencyProvider interestRecalculationCompoundingProvider;
    private Option interestRecalculationCompoundingValue;
    private Select2SingleChoice<Option> interestRecalculationCompoundingField;
    private TextFeedbackPanel interestRecalculationCompoundingFeedback;

    private WebMarkupContainer interestRecalculationCompoundingTypeBlock;
    private WebMarkupContainer interestRecalculationCompoundingTypeContainer;
    private FrequencyTypeProvider interestRecalculationCompoundingTypeProvider;
    private Option interestRecalculationCompoundingTypeValue;
    private Select2SingleChoice<Option> interestRecalculationCompoundingTypeField;
    private TextFeedbackPanel interestRecalculationCompoundingTypeFeedback;

    private WebMarkupContainer interestRecalculationCompoundingDayBlock;
    private WebMarkupContainer interestRecalculationCompoundingDayContainer;
    private FrequencyDayProvider interestRecalculationCompoundingDayProvider;
    private Option interestRecalculationCompoundingDayValue;
    private Select2SingleChoice<Option> interestRecalculationCompoundingDayField;
    private TextFeedbackPanel interestRecalculationCompoundingDayFeedback;

    private WebMarkupContainer interestRecalculationCompoundingIntervalBlock;
    private WebMarkupContainer interestRecalculationCompoundingIntervalContainer;
    private Double interestRecalculationCompoundingIntervalValue;
    private TextField<Double> interestRecalculationCompoundingIntervalField;
    private TextFeedbackPanel interestRecalculationCompoundingIntervalFeedback;

    private WebMarkupContainer interestRecalculationRecalculateBlock;
    private WebMarkupContainer interestRecalculationRecalculateContainer;
    private FrequencyProvider interestRecalculationRecalculateProvider;
    private Option interestRecalculationRecalculateValue;
    private Select2SingleChoice<Option> interestRecalculationRecalculateField;
    private TextFeedbackPanel interestRecalculationRecalculateFeedback;

    private WebMarkupContainer interestRecalculationRecalculateTypeBlock;
    private WebMarkupContainer interestRecalculationRecalculateTypeContainer;
    private FrequencyTypeProvider interestRecalculationRecalculateTypeProvider;
    private Option interestRecalculationRecalculateTypeValue;
    private Select2SingleChoice<Option> interestRecalculationRecalculateTypeField;
    private TextFeedbackPanel interestRecalculationRecalculateTypeFeedback;

    private WebMarkupContainer interestRecalculationRecalculateDayBlock;
    private WebMarkupContainer interestRecalculationRecalculateDayContainer;
    private FrequencyDayProvider interestRecalculationRecalculateDayProvider;
    private Option interestRecalculationRecalculateDayValue;
    private Select2SingleChoice<Option> interestRecalculationRecalculateDayField;
    private TextFeedbackPanel interestRecalculationRecalculateDayFeedback;

    private WebMarkupContainer interestRecalculationRecalculateIntervalBlock;
    private WebMarkupContainer interestRecalculationRecalculateIntervalContainer;
    private Double interestRecalculationRecalculateIntervalValue;
    private TextField<Double> interestRecalculationRecalculateIntervalField;
    private TextFeedbackPanel interestRecalculationRecalculateIntervalFeedback;

    private WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    private WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer;
    private Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;
    private CheckBox interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField;
    private TextFeedbackPanel interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback;

    // Guarantee Requirements

    private WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    private WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer;
    private Boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
    private CheckBox guaranteeRequirementPlaceGuaranteeFundsOnHoldField;
    private TextFeedbackPanel guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback;

    private WebMarkupContainer guaranteeRequirementMandatoryGuaranteeBlock;
    private WebMarkupContainer guaranteeRequirementMandatoryGuaranteeContainer;
    private Double guaranteeRequirementMandatoryGuaranteeValue;
    private TextField<Double> guaranteeRequirementMandatoryGuaranteeField;
    private TextFeedbackPanel guaranteeRequirementMandatoryGuaranteeFeedback;

    private WebMarkupContainer guaranteeRequirementMinimumGuaranteeBlock;
    private WebMarkupContainer guaranteeRequirementMinimumGuaranteeContainer;
    private Double guaranteeRequirementMinimumGuaranteeValue;
    private TextField<Double> guaranteeRequirementMinimumGuaranteeField;
    private TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFeedback;

    private WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    private WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorContainer;
    private Double guaranteeRequirementMinimumGuaranteeFromGuarantorValue;
    private TextField<Double> guaranteeRequirementMinimumGuaranteeFromGuarantorField;
    private TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback;

    // Loan Tranche Details

    private WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalBlock;
    private WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalContainer;
    private Boolean loanTrancheDetailEnableMultipleDisbursalValue;
    private CheckBox loanTrancheDetailEnableMultipleDisbursalField;
    private TextFeedbackPanel loanTrancheDetailEnableMultipleDisbursalFeedback;

    private WebMarkupContainer loanTrancheDetailMaximumTrancheCountBlock;
    private WebMarkupContainer loanTrancheDetailMaximumTrancheCountContainer;
    private Double loanTrancheDetailMaximumTrancheCountValue;
    private TextField<Double> loanTrancheDetailMaximumTrancheCountField;
    private TextFeedbackPanel loanTrancheDetailMaximumTrancheCountFeedback;

    private WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    private WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceContainer;
    private Double loanTrancheDetailMaximumAllowedOutstandingBalanceValue;
    private TextField<Double> loanTrancheDetailMaximumAllowedOutstandingBalanceField;
    private TextFeedbackPanel loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback;

    // Configurable Terms and Settings

    private WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    private WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer;
    private Boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
    private CheckBox configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField;
    private TextFeedbackPanel configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback;

    private WebMarkupContainer configurableAmortizationBlock;
    private WebMarkupContainer configurableAmortizationContainer;
    private Boolean configurableAmortizationValue;
    private CheckBox configurableAmortizationField;
    private TextFeedbackPanel configurableAmortizationFeedback;

    private WebMarkupContainer configurableInterestMethodBlock;
    private WebMarkupContainer configurableInterestMethodContainer;
    private Boolean configurableInterestMethodValue;
    private CheckBox configurableInterestMethodField;
    private TextFeedbackPanel configurableInterestMethodFeedback;

    private WebMarkupContainer configurableRepaymentStrategyBlock;
    private WebMarkupContainer configurableRepaymentStrategyContainer;
    private Boolean configurableRepaymentStrategyValue;
    private CheckBox configurableRepaymentStrategyField;
    private TextFeedbackPanel configurableRepaymentStrategyFeedback;

    private WebMarkupContainer configurableInterestCalculationPeriodBlock;
    private WebMarkupContainer configurableInterestCalculationPeriodContainer;
    private Boolean configurableInterestCalculationPeriodValue;
    private CheckBox configurableInterestCalculationPeriodField;
    private TextFeedbackPanel configurableInterestCalculationPeriodFeedback;

    private WebMarkupContainer configurableArrearsToleranceBlock;
    private WebMarkupContainer configurableArrearsToleranceContainer;
    private Boolean configurableArrearsToleranceValue;
    private CheckBox configurableArrearsToleranceField;
    private TextFeedbackPanel configurableArrearsToleranceFeedback;

    private WebMarkupContainer configurableRepaidEveryBlock;
    private WebMarkupContainer configurableRepaidEveryContainer;
    private Boolean configurableRepaidEveryValue;
    private CheckBox configurableRepaidEveryField;
    private TextFeedbackPanel configurableRepaidEveryFeedback;

    private WebMarkupContainer configurableMoratoriumBlock;
    private WebMarkupContainer configurableMoratoriumContainer;
    private Boolean configurableMoratoriumValue;
    private CheckBox configurableMoratoriumField;
    private TextFeedbackPanel configurableMoratoriumFeedback;

    private WebMarkupContainer configurableOverdueBeforeMovingBlock;
    private WebMarkupContainer configurableOverdueBeforeMovingContainer;
    private Boolean configurableOverdueBeforeMovingValue;
    private CheckBox configurableOverdueBeforeMovingField;
    private TextFeedbackPanel configurableOverdueBeforeMovingFeedback;

    // Accounting

    private String accountingValue = ACC_NONE;
    private RadioGroup<String> accountingField;

    private WebMarkupContainer cashBlock;
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

    private WebMarkupContainer periodicBlock;
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

    private WebMarkupContainer upfrontBlock;
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

    private WebMarkupContainer advancedAccountingRuleBlock;
    private WebMarkupContainer advancedAccountingRuleContainer;

    private WebMarkupContainer fundSourceContainer;
    private List<Map<String, Object>> fundSourceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> fundSourceTable;
    private ListDataProvider fundSourceProvider;
    private ModalWindow fundSourcePopup;

    private WebMarkupContainer feeIncomeContainer;
    private List<Map<String, Object>> feeIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> feeIncomeTable;
    private ListDataProvider feeIncomeProvider;
    private ModalWindow feeIncomePopup;

    private WebMarkupContainer penaltyIncomeContainer;
    private List<Map<String, Object>> penaltyIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> penaltyIncomeTable;
    private ListDataProvider penaltyIncomeProvider;
    private ModalWindow penaltyIncomePopup;

    // Charges

    private List<Map<String, Object>> chargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargeTable;
    private ListDataProvider chargeProvider;
    private ModalWindow chargePopup;

    // Overdue Charges

    private List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> overdueChargeTable;
    private ListDataProvider overdueChargeProvider;
    private ModalWindow overdueChargePopup;

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

        // JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        // Boolean officeSpecificProductsEnabled = jdbcTemplate.queryForObject(
        // "select value from c_configuration where name =
        // 'office-specific-products-enabled'", Boolean.class);
        // officeSpecificProductsEnabled = officeSpecificProductsEnabled == null ? false
        // : officeSpecificProductsEnabled;

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
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
        this.overdueChargePopup = new ModalWindow("overdueChargePopup");
        add(this.overdueChargePopup);
        this.overdueChargePopup.setContent(new OverdueChargePopup(this.overdueChargePopup.getContentId(), this.overdueChargePopup, this));
        this.overdueChargePopup.setOnClose(this::overdueChargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> overdueChargeColumn = Lists.newArrayList();
        overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeNameColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeTypeColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeAmountColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeCollectColumn));
        overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::overdueChargeDateColumn));
        overdueChargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::overdueChargeActionItem, this::overdueChargesActionClick));
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", overdueChargeColumn, this.overdueChargeProvider, 20);
        this.form.add(this.overdueChargeTable);
        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
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

    protected ItemPanel overdueChargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value.doubleValue())));
        }
    }

    protected ItemPanel overdueChargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
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

    protected void overdueChargesActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
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

    protected void chargeActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
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
        this.upfrontBlock = new WebMarkupContainer("upfrontBlock");
        this.upfrontBlock.setOutputMarkupId(true);
        this.form.add(this.upfrontBlock);

        this.upfrontContainer = new WebMarkupContainer("upfrontContainer");
        this.upfrontBlock.add(this.upfrontContainer);

        this.upfrontFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontFundSourceField = new Select2SingleChoice<>("upfrontFundSourceField", new PropertyModel<>(this, "upfrontFundSourceValue"), this.upfrontFundSourceProvider);
        this.upfrontFundSourceField.setLabel(Model.of("Fund source"));
        this.upfrontFundSourceField.setRequired(false);
        this.upfrontFundSourceField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontFundSourceField);
        this.upfrontFundSourceFeedback = new TextFeedbackPanel("upfrontFundSourceFeedback", this.upfrontFundSourceField);
        this.upfrontContainer.add(this.upfrontFundSourceFeedback);

        this.upfrontLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontLoanPortfolioField = new Select2SingleChoice<>("upfrontLoanPortfolioField", new PropertyModel<>(this, "upfrontLoanPortfolioValue"), this.upfrontLoanPortfolioProvider);
        this.upfrontLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.upfrontLoanPortfolioField.setRequired(false);
        this.upfrontLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontLoanPortfolioField);
        this.upfrontLoanPortfolioFeedback = new TextFeedbackPanel("upfrontLoanPortfolioFeedback", this.upfrontLoanPortfolioField);
        this.upfrontContainer.add(this.upfrontLoanPortfolioFeedback);

        this.upfrontInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontInterestReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontInterestReceivableField = new Select2SingleChoice<>("upfrontInterestReceivableField", new PropertyModel<>(this, "upfrontInterestReceivableValue"), this.upfrontInterestReceivableProvider);
        this.upfrontInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.upfrontInterestReceivableField.setRequired(false);
        this.upfrontInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontInterestReceivableField);
        this.upfrontInterestReceivableFeedback = new TextFeedbackPanel("upfrontInterestReceivableFeedback", this.upfrontInterestReceivableField);
        this.upfrontContainer.add(this.upfrontInterestReceivableFeedback);

        this.upfrontFeesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontFeesReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontFeesReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontFeesReceivableField = new Select2SingleChoice<>("upfrontFeesReceivableField", new PropertyModel<>(this, "upfrontFeesReceivableValue"), this.upfrontFeesReceivableProvider);
        this.upfrontFeesReceivableField.setLabel(Model.of("Fees Receivable"));
        this.upfrontFeesReceivableField.setRequired(false);
        this.upfrontFeesReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontFeesReceivableField);
        this.upfrontFeesReceivableFeedback = new TextFeedbackPanel("upfrontFeesReceivableFeedback", this.upfrontFeesReceivableField);
        this.upfrontContainer.add(this.upfrontFeesReceivableFeedback);

        this.upfrontPenaltiesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontPenaltiesReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontPenaltiesReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontPenaltiesReceivableField = new Select2SingleChoice<>("upfrontPenaltiesReceivableField", new PropertyModel<>(this, "upfrontPenaltiesReceivableValue"), this.upfrontPenaltiesReceivableProvider);
        this.upfrontPenaltiesReceivableField.setLabel(Model.of("Penalties Receivable"));
        this.upfrontPenaltiesReceivableField.setRequired(false);
        this.upfrontPenaltiesReceivableField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontPenaltiesReceivableField);
        this.upfrontPenaltiesReceivableFeedback = new TextFeedbackPanel("upfrontPenaltiesReceivableFeedback", this.upfrontPenaltiesReceivableField);
        this.upfrontContainer.add(this.upfrontPenaltiesReceivableFeedback);

        this.upfrontTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.upfrontTransferInSuspenseField = new Select2SingleChoice<>("upfrontTransferInSuspenseField", new PropertyModel<>(this, "upfrontTransferInSuspenseValue"), this.upfrontTransferInSuspenseProvider);
        this.upfrontTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.upfrontTransferInSuspenseField.setRequired(false);
        this.upfrontTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontTransferInSuspenseField);
        this.upfrontTransferInSuspenseFeedback = new TextFeedbackPanel("upfrontTransferInSuspenseFeedback", this.upfrontTransferInSuspenseField);
        this.upfrontContainer.add(this.upfrontTransferInSuspenseFeedback);

        this.upfrontIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromInterestField = new Select2SingleChoice<>("upfrontIncomeFromInterestField", new PropertyModel<>(this, "upfrontIncomeFromInterestValue"), this.upfrontIncomeFromInterestProvider);
        this.upfrontIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.upfrontIncomeFromInterestField.setRequired(false);
        this.upfrontIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromInterestField);
        this.upfrontIncomeFromInterestFeedback = new TextFeedbackPanel("upfrontIncomeFromInterestFeedback", this.upfrontIncomeFromInterestField);
        this.upfrontContainer.add(this.upfrontIncomeFromInterestFeedback);

        this.upfrontIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromFeeField = new Select2SingleChoice<>("upfrontIncomeFromFeeField", new PropertyModel<>(this, "upfrontIncomeFromFeeValue"), this.upfrontIncomeFromFeeProvider);
        this.upfrontIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.upfrontIncomeFromFeeField.setRequired(false);
        this.upfrontIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromFeeField);
        this.upfrontIncomeFromFeeFeedback = new TextFeedbackPanel("upfrontIncomeFromFeeFeedback", this.upfrontIncomeFromFeeField);
        this.upfrontContainer.add(this.upfrontIncomeFromFeeFeedback);

        this.upfrontIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromPenaltiesField = new Select2SingleChoice<>("upfrontIncomeFromPenaltiesField", new PropertyModel<>(this, "upfrontIncomeFromPenaltiesValue"), this.upfrontIncomeFromPenaltiesProvider);
        this.upfrontIncomeFromPenaltiesField.setLabel(Model.of("Income from penalties"));
        this.upfrontIncomeFromPenaltiesField.setRequired(false);
        this.upfrontIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesField);
        this.upfrontIncomeFromPenaltiesFeedback = new TextFeedbackPanel("upfrontIncomeFromPenaltiesFeedback", this.upfrontIncomeFromPenaltiesField);
        this.upfrontContainer.add(this.upfrontIncomeFromPenaltiesFeedback);

        this.upfrontIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.upfrontIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("upfrontIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "upfrontIncomeFromRecoveryRepaymentValue"), this.upfrontIncomeFromRecoveryRepaymentProvider);
        this.upfrontIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.upfrontIncomeFromRecoveryRepaymentField.setRequired(false);
        this.upfrontIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("upfrontIncomeFromRecoveryRepaymentFeedback", this.upfrontIncomeFromRecoveryRepaymentField);
        this.upfrontContainer.add(this.upfrontIncomeFromRecoveryRepaymentFeedback);

        this.upfrontLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.upfrontLossesWrittenOffField = new Select2SingleChoice<>("upfrontLossesWrittenOffField", new PropertyModel<>(this, "upfrontLossesWrittenOffValue"), this.upfrontLossesWrittenOffProvider);
        this.upfrontLossesWrittenOffField.setLabel(Model.of("Losses written off"));
        this.upfrontLossesWrittenOffField.setRequired(false);
        this.upfrontLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontLossesWrittenOffField);
        this.upfrontLossesWrittenOffFeedback = new TextFeedbackPanel("upfrontLossesWrittenOffFeedback", this.upfrontLossesWrittenOffField);
        this.upfrontContainer.add(this.upfrontLossesWrittenOffFeedback);

        this.upfrontOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.upfrontOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.upfrontOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.upfrontOverPaymentLiabilityField = new Select2SingleChoice<>("upfrontOverPaymentLiabilityField", new PropertyModel<>(this, "upfrontOverPaymentLiabilityValue"), this.upfrontOverPaymentLiabilityProvider);
        this.upfrontOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.upfrontOverPaymentLiabilityField.setRequired(false);
        this.upfrontOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.upfrontContainer.add(this.upfrontOverPaymentLiabilityField);
        this.upfrontOverPaymentLiabilityFeedback = new TextFeedbackPanel("upfrontOverPaymentLiabilityFeedback", this.upfrontOverPaymentLiabilityField);
        this.upfrontContainer.add(this.upfrontOverPaymentLiabilityFeedback);
    }

    protected void initAccountingCash() {

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);

        this.cashContainer = new WebMarkupContainer("cashContainer");
        this.cashBlock.add(this.cashContainer);

        this.cashFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashFundSourceField = new Select2SingleChoice<>("cashFundSourceField", new PropertyModel<>(this, "cashFundSourceValue"), this.cashFundSourceProvider);
        this.cashFundSourceField.setLabel(Model.of("Fund Source"));
        this.cashFundSourceField.setRequired(false);
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashFundSourceField);
        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceField);
        this.cashContainer.add(this.cashFundSourceFeedback);

        this.cashLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField", new PropertyModel<>(this, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.cashLoanPortfolioField.setRequired(false);
        this.cashLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashLoanPortfolioField);
        this.cashLoanPortfolioFeedback = new TextFeedbackPanel("cashLoanPortfolioFeedback", this.cashLoanPortfolioField);
        this.cashContainer.add(this.cashLoanPortfolioFeedback);

        this.cashTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashTransferInSuspenseField = new Select2SingleChoice<>("cashTransferInSuspenseField", new PropertyModel<>(this, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
        this.cashTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.cashTransferInSuspenseField.setRequired(false);
        this.cashTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseFeedback = new TextFeedbackPanel("cashTransferInSuspenseFeedback", this.cashTransferInSuspenseField);
        this.cashContainer.add(this.cashTransferInSuspenseFeedback);

        this.cashIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromInterestField = new Select2SingleChoice<>("cashIncomeFromInterestField", new PropertyModel<>(this, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
        this.cashIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.cashIncomeFromInterestField.setRequired(false);
        this.cashIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestFeedback = new TextFeedbackPanel("cashIncomeFromInterestFeedback", this.cashIncomeFromInterestField);
        this.cashContainer.add(this.cashIncomeFromInterestFeedback);

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeeField.setRequired(false);
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashContainer.add(this.cashIncomeFromFeeFeedback);

        this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField", new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
        this.cashIncomeFromPenaltiesField.setLabel(Model.of("Income from penalties"));
        this.cashIncomeFromPenaltiesField.setRequired(false);
        this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromPenaltiesField);
        this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback", this.cashIncomeFromPenaltiesField);
        this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);

        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "cashIncomeFromRecoveryRepaymentValue"), this.cashIncomeFromRecoveryRepaymentProvider);
        this.cashIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.cashIncomeFromRecoveryRepaymentField.setRequired(false);
        this.cashIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryRepaymentFeedback", this.cashIncomeFromRecoveryRepaymentField);
        this.cashContainer.add(this.cashIncomeFromRecoveryRepaymentFeedback);

        this.cashLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.cashLossesWrittenOffField = new Select2SingleChoice<>("cashLossesWrittenOffField", new PropertyModel<>(this, "cashLossesWrittenOffValue"), this.cashLossesWrittenOffProvider);
        this.cashLossesWrittenOffField.setLabel(Model.of("Losses written off"));
        this.cashLossesWrittenOffField.setRequired(false);
        this.cashLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashLossesWrittenOffField);
        this.cashLossesWrittenOffFeedback = new TextFeedbackPanel("cashLossesWrittenOffFeedback", this.cashLossesWrittenOffField);
        this.cashContainer.add(this.cashLossesWrittenOffFeedback);

        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashOverPaymentLiabilityField = new Select2SingleChoice<>("cashOverPaymentLiabilityField", new PropertyModel<>(this, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
        this.cashOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.cashOverPaymentLiabilityField.setRequired(false);
        this.cashOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityFeedback = new TextFeedbackPanel("cashOverPaymentLiabilityFeedback", this.cashOverPaymentLiabilityField);
        this.cashContainer.add(this.cashOverPaymentLiabilityFeedback);
    }

    protected void initAccountingPeriodic() {
        this.periodicBlock = new WebMarkupContainer("periodicBlock");
        this.periodicBlock.setOutputMarkupId(true);
        this.form.add(this.periodicBlock);

        this.periodicContainer = new WebMarkupContainer("periodicContainer");
        this.periodicBlock.add(this.periodicContainer);

        this.periodicFundSourceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicFundSourceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicFundSourceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicFundSourceField = new Select2SingleChoice<>("periodicFundSourceField", new PropertyModel<>(this, "periodicFundSourceValue"), this.periodicFundSourceProvider);
        this.periodicFundSourceField.setLabel(Model.of("Fund source"));
        this.periodicFundSourceField.setRequired(false);
        this.periodicFundSourceField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicFundSourceField);
        this.periodicFundSourceFeedback = new TextFeedbackPanel("periodicFundSourceFeedback", this.periodicFundSourceField);
        this.periodicContainer.add(this.periodicFundSourceFeedback);

        this.periodicLoanPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicLoanPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicLoanPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicLoanPortfolioField = new Select2SingleChoice<>("periodicLoanPortfolioField", new PropertyModel<>(this, "periodicLoanPortfolioValue"), this.periodicLoanPortfolioProvider);
        this.periodicLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.periodicLoanPortfolioField.setRequired(false);
        this.periodicLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicLoanPortfolioField);
        this.periodicLoanPortfolioFeedback = new TextFeedbackPanel("periodicLoanPortfolioFeedback", this.periodicLoanPortfolioField);
        this.periodicContainer.add(this.periodicLoanPortfolioFeedback);

        this.periodicInterestReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicInterestReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicInterestReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicInterestReceivableField = new Select2SingleChoice<>("periodicInterestReceivableField", new PropertyModel<>(this, "periodicInterestReceivableValue"), this.periodicInterestReceivableProvider);
        this.periodicInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.periodicInterestReceivableField.setRequired(false);
        this.periodicInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicInterestReceivableField);
        this.periodicInterestReceivableFeedback = new TextFeedbackPanel("periodicInterestReceivableFeedback", this.periodicInterestReceivableField);
        this.periodicContainer.add(this.periodicInterestReceivableFeedback);

        this.periodicFeesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicFeesReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicFeesReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicFeesReceivableField = new Select2SingleChoice<>("periodicFeesReceivableField", new PropertyModel<>(this, "periodicFeesReceivableValue"), this.periodicFeesReceivableProvider);
        this.periodicFeesReceivableField.setLabel(Model.of("Fees Receivable"));
        this.periodicFeesReceivableField.setRequired(false);
        this.periodicFeesReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicFeesReceivableField);
        this.periodicFeesReceivableFeedback = new TextFeedbackPanel("periodicFeesReceivableFeedback", this.periodicFeesReceivableField);
        this.periodicContainer.add(this.periodicFeesReceivableFeedback);

        this.periodicPenaltiesReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicPenaltiesReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicPenaltiesReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicPenaltiesReceivableField = new Select2SingleChoice<>("periodicPenaltiesReceivableField", new PropertyModel<>(this, "periodicPenaltiesReceivableValue"), this.periodicPenaltiesReceivableProvider);
        this.periodicPenaltiesReceivableField.setLabel(Model.of("Penalties Receivable"));
        this.periodicPenaltiesReceivableField.setRequired(false);
        this.periodicPenaltiesReceivableField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicPenaltiesReceivableField);
        this.periodicPenaltiesReceivableFeedback = new TextFeedbackPanel("periodicPenaltiesReceivableFeedback", this.periodicPenaltiesReceivableField);
        this.periodicContainer.add(this.periodicPenaltiesReceivableFeedback);

        this.periodicTransferInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicTransferInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicTransferInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.periodicTransferInSuspenseField = new Select2SingleChoice<>("periodicTransferInSuspenseField", new PropertyModel<>(this, "periodicTransferInSuspenseValue"), this.periodicTransferInSuspenseProvider);
        this.periodicTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.periodicTransferInSuspenseField.setRequired(false);
        this.periodicTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicTransferInSuspenseField);
        this.periodicTransferInSuspenseFeedback = new TextFeedbackPanel("periodicTransferInSuspenseFeedback", this.periodicTransferInSuspenseField);
        this.periodicContainer.add(this.periodicTransferInSuspenseFeedback);

        this.periodicIncomeFromInterestProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromInterestProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromInterestProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromInterestField = new Select2SingleChoice<>("periodicIncomeFromInterestField", new PropertyModel<>(this, "periodicIncomeFromInterestValue"), this.periodicIncomeFromInterestProvider);
        this.periodicIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.periodicIncomeFromInterestField.setRequired(false);
        this.periodicIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromInterestField);
        this.periodicIncomeFromInterestFeedback = new TextFeedbackPanel("periodicIncomeFromInterestFeedback", this.periodicIncomeFromInterestField);
        this.periodicContainer.add(this.periodicIncomeFromInterestFeedback);

        this.periodicIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromFeeField = new Select2SingleChoice<>("periodicIncomeFromFeeField", new PropertyModel<>(this, "periodicIncomeFromFeeValue"), this.periodicIncomeFromFeeProvider);
        this.periodicIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.periodicIncomeFromFeeField.setRequired(false);
        this.periodicIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromFeeField);
        this.periodicIncomeFromFeeFeedback = new TextFeedbackPanel("periodicIncomeFromFeeFeedback", this.periodicIncomeFromFeeField);
        this.periodicContainer.add(this.periodicIncomeFromFeeFeedback);

        this.periodicIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromPenaltiesField = new Select2SingleChoice<>("periodicIncomeFromPenaltiesField", new PropertyModel<>(this, "periodicIncomeFromPenaltiesValue"), this.periodicIncomeFromPenaltiesProvider);
        this.periodicIncomeFromPenaltiesField.setLabel(Model.of("Income from penalties"));
        this.periodicIncomeFromPenaltiesField.setRequired(false);
        this.periodicIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesField);
        this.periodicIncomeFromPenaltiesFeedback = new TextFeedbackPanel("periodicIncomeFromPenaltiesFeedback", this.periodicIncomeFromPenaltiesField);
        this.periodicContainer.add(this.periodicIncomeFromPenaltiesFeedback);

        this.periodicIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.periodicIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("periodicIncomeFromRecoveryRepaymentField", new PropertyModel<>(this, "periodicIncomeFromRecoveryRepaymentValue"), this.periodicIncomeFromRecoveryRepaymentProvider);
        this.periodicIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.periodicIncomeFromRecoveryRepaymentField.setRequired(false);
        this.periodicIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("periodicIncomeFromRecoveryRepaymentFeedback", this.periodicIncomeFromRecoveryRepaymentField);
        this.periodicContainer.add(this.periodicIncomeFromRecoveryRepaymentFeedback);

        this.periodicLossesWrittenOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicLossesWrittenOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicLossesWrittenOffProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.periodicLossesWrittenOffField = new Select2SingleChoice<>("periodicLossesWrittenOffField", new PropertyModel<>(this, "periodicLossesWrittenOffValue"), this.periodicLossesWrittenOffProvider);
        this.periodicLossesWrittenOffField.setLabel(Model.of("Losses written off"));
        this.periodicLossesWrittenOffField.setRequired(false);
        this.periodicLossesWrittenOffField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicLossesWrittenOffField);
        this.periodicLossesWrittenOffFeedback = new TextFeedbackPanel("periodicLossesWrittenOffFeedback", this.periodicLossesWrittenOffField);
        this.periodicContainer.add(this.periodicLossesWrittenOffFeedback);

        this.periodicOverPaymentLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.periodicOverPaymentLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.periodicOverPaymentLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.periodicOverPaymentLiabilityField = new Select2SingleChoice<>("periodicOverPaymentLiabilityField", new PropertyModel<>(this, "periodicOverPaymentLiabilityValue"), this.periodicOverPaymentLiabilityProvider);
        this.periodicOverPaymentLiabilityField.setRequired(false);
        this.periodicOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.periodicOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.periodicContainer.add(this.periodicOverPaymentLiabilityField);
        this.periodicOverPaymentLiabilityFeedback = new TextFeedbackPanel("periodicOverPaymentLiabilityFeedback", this.periodicOverPaymentLiabilityField);
        this.periodicContainer.add(this.periodicOverPaymentLiabilityFeedback);

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
            this.fundSourcePopup.setContent(new PaymentTypePopup(this.fundSourcePopup.getContentId(), this.fundSourcePopup, this));
            this.fundSourcePopup.setOnClose(this::fundSourcePopupOnClose);

            List<IColumn<Map<String, Object>, String>> fundSourceColumn = Lists.newArrayList();
            fundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::fundSourcePaymentColumn));
            fundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::fundSourceAccountColumn));
            fundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::fundSourceActionItem, this::fundSourceActionClick));
            this.fundSourceProvider = new ListDataProvider(this.fundSourceValue);
            this.fundSourceTable = new DataTable<>("fundSourceTable", fundSourceColumn, this.fundSourceProvider, 20);
            this.advancedAccountingRuleContainer.add(this.fundSourceTable);
            this.fundSourceTable.addTopToolbar(new HeadersToolbar<>(this.fundSourceTable, this.fundSourceProvider));
            this.fundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.fundSourceTable));

            AjaxLink<Void> fundSourceAddLink = new AjaxLink<>("fundSourceAddLink");
            fundSourceAddLink.setOnClick(this::fundSourceAddLinkClick);
            this.advancedAccountingRuleContainer.add(fundSourceAddLink);
        }

        // Table
        {
            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
            add(this.feeIncomePopup);
            this.feeIncomePopup.setContent(new FeeChargePopup(this.feeIncomePopup.getContentId(), this.feeIncomePopup, this));
            this.feeIncomePopup.setOnClose(this::feeIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> feeIncomeColumn = Lists.newArrayList();
            feeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::feeIncomeChargeColumn));
            feeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::feeIncomeAccountColumn));
            feeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::feeIncomeActionItem, this::feeIncomeActionClick));
            this.feeIncomeProvider = new ListDataProvider(this.feeIncomeValue);
            this.feeIncomeTable = new DataTable<>("feeIncomeTable", feeIncomeColumn, this.feeIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.feeIncomeTable);
            this.feeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.feeIncomeTable, this.feeIncomeProvider));
            this.feeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.feeIncomeTable));

            AjaxLink<Void> feeIncomeAddLink = new AjaxLink<>("feeIncomeAddLink");
            feeIncomeAddLink.setOnClick(this::feeIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(feeIncomeAddLink);
        }

        // Table
        {
            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
            add(this.penaltyIncomePopup);
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup(this.penaltyIncomePopup.getContentId(), this.penaltyIncomePopup, this));
            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> penaltyIncomeColumn = Lists.newArrayList();
            penaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::penaltyIncomeChargeColumn));
            penaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::penaltyIncomeAccountColumn));
            penaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::penaltyIncomeActionItem, this::penaltyIncomeActionClick));
            this.penaltyIncomeProvider = new ListDataProvider(this.penaltyIncomeValue);
            this.penaltyIncomeTable = new DataTable<>("penaltyIncomeTable", penaltyIncomeColumn, this.penaltyIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.penaltyIncomeTable);
            this.penaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.penaltyIncomeTable, this.penaltyIncomeProvider));
            this.penaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.penaltyIncomeTable));

            AjaxLink<Void> penaltyIncomeAddLink = new AjaxLink<>("penaltyIncomeAddLink");
            penaltyIncomeAddLink.setOnClick(this::penaltyIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(penaltyIncomeAddLink);
        }
    }

    protected boolean feeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        this.feeIncomePopup.show(target);
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

    protected void feeIncomeActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
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
        this.penaltyIncomePopup.show(target);
        return false;
    }

    protected ItemPanel penaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel penaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected void penaltyIncomeActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
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
        this.fundSourcePopup.show(target);
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

    protected void fundSourceActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
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
        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.periodicBlock);
            target.add(this.upfrontBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initConfigurableTermsAndSettings() {

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.setOutputMarkupId(true);
        this.form.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField.setRequired(false);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField.add(new OnChangeAjaxBehavior(this::configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback = new TextFeedbackPanel("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback", this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback);

        this.configurableAmortizationBlock = new WebMarkupContainer("configurableAmortizationBlock");
        this.configurableAmortizationBlock.setOutputMarkupId(true);
        this.form.add(this.configurableAmortizationBlock);
        this.configurableAmortizationContainer = new WebMarkupContainer("configurableAmortizationContainer");
        this.configurableAmortizationBlock.add(this.configurableAmortizationContainer);
        this.configurableAmortizationField = new CheckBox("configurableAmortizationField", new PropertyModel<>(this, "configurableAmortizationValue"));
        this.configurableAmortizationField.setRequired(false);
        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());
        this.configurableAmortizationContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback", this.configurableAmortizationField);
        this.configurableAmortizationContainer.add(this.configurableAmortizationFeedback);

        this.configurableInterestMethodBlock = new WebMarkupContainer("configurableInterestMethodBlock");
        this.configurableInterestMethodBlock.setOutputMarkupId(true);
        this.form.add(this.configurableInterestMethodBlock);
        this.configurableInterestMethodContainer = new WebMarkupContainer("configurableInterestMethodContainer");
        this.configurableInterestMethodBlock.add(this.configurableInterestMethodContainer);
        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField", new PropertyModel<>(this, "configurableInterestMethodValue"));
        this.configurableInterestMethodField.setRequired(false);
        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestMethodContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback", this.configurableInterestMethodField);
        this.configurableInterestMethodContainer.add(this.configurableInterestMethodFeedback);

        this.configurableRepaymentStrategyBlock = new WebMarkupContainer("configurableRepaymentStrategyBlock");
        this.configurableRepaymentStrategyBlock.setOutputMarkupId(true);
        this.form.add(this.configurableRepaymentStrategyBlock);
        this.configurableRepaymentStrategyContainer = new WebMarkupContainer("configurableRepaymentStrategyContainer");
        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyContainer);
        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField", new PropertyModel<>(this, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyField.setRequired(false);
        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.configurableRepaymentStrategyContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback", this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyContainer.add(this.configurableRepaymentStrategyFeedback);

        this.configurableInterestCalculationPeriodBlock = new WebMarkupContainer("configurableInterestCalculationPeriodBlock");
        this.configurableInterestCalculationPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.configurableInterestCalculationPeriodBlock);
        this.configurableInterestCalculationPeriodContainer = new WebMarkupContainer("configurableInterestCalculationPeriodContainer");
        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodContainer);
        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField", new PropertyModel<>(this, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodField.setRequired(false);
        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestCalculationPeriodContainer.add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel("configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodContainer.add(this.configurableInterestCalculationPeriodFeedback);

        this.configurableArrearsToleranceBlock = new WebMarkupContainer("configurableArrearsToleranceBlock");
        this.configurableArrearsToleranceBlock.setOutputMarkupId(true);
        this.form.add(this.configurableArrearsToleranceBlock);
        this.configurableArrearsToleranceContainer = new WebMarkupContainer("configurableArrearsToleranceContainer");
        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceContainer);
        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField", new PropertyModel<>(this, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceField.setRequired(false);
        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.configurableArrearsToleranceContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback", this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceContainer.add(this.configurableArrearsToleranceFeedback);

        this.configurableRepaidEveryBlock = new WebMarkupContainer("configurableRepaidEveryBlock");
        this.configurableRepaidEveryBlock.setOutputMarkupId(true);
        this.form.add(this.configurableRepaidEveryBlock);
        this.configurableRepaidEveryContainer = new WebMarkupContainer("configurableRepaidEveryContainer");
        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryContainer);
        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField", new PropertyModel<>(this, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryField.setRequired(false);
        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.configurableRepaidEveryContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback", this.configurableRepaidEveryField);
        this.configurableRepaidEveryContainer.add(this.configurableRepaidEveryFeedback);

        this.configurableMoratoriumBlock = new WebMarkupContainer("configurableMoratoriumBlock");
        this.configurableMoratoriumBlock.setOutputMarkupId(true);
        this.form.add(this.configurableMoratoriumBlock);
        this.configurableMoratoriumContainer = new WebMarkupContainer("configurableMoratoriumContainer");
        this.configurableMoratoriumBlock.add(this.configurableMoratoriumContainer);
        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField", new PropertyModel<>(this, "configurableMoratoriumValue"));
        this.configurableMoratoriumField.setRequired(false);
        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());
        this.configurableMoratoriumContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback", this.configurableMoratoriumField);
        this.configurableMoratoriumContainer.add(this.configurableMoratoriumFeedback);

        this.configurableOverdueBeforeMovingBlock = new WebMarkupContainer("configurableOverdueBeforeMovingBlock");
        this.configurableOverdueBeforeMovingBlock.setOutputMarkupId(true);
        this.form.add(this.configurableOverdueBeforeMovingBlock);
        this.configurableOverdueBeforeMovingContainer = new WebMarkupContainer("configurableOverdueBeforeMovingContainer");
        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingContainer);
        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField", new PropertyModel<>(this, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingField.setRequired(false);
        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());
        this.configurableOverdueBeforeMovingContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback", this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingContainer.add(this.configurableOverdueBeforeMovingFeedback);
    }

    protected boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue != null && this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
        this.configurableAmortizationContainer.setVisible(visible);
        this.configurableInterestMethodContainer.setVisible(visible);
        this.configurableRepaymentStrategyContainer.setVisible(visible);
        this.configurableInterestCalculationPeriodContainer.setVisible(visible);
        this.configurableArrearsToleranceContainer.setVisible(visible);
        this.configurableRepaidEveryContainer.setVisible(visible);
        this.configurableMoratoriumContainer.setVisible(visible);
        this.configurableOverdueBeforeMovingContainer.setVisible(visible);
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

    protected void initLoanTrancheDetails() {

        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalBlock");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.setOutputMarkupId(true);
        this.form.add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
        this.loanTrancheDetailEnableMultipleDisbursalContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalContainer");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalContainer);
        this.loanTrancheDetailEnableMultipleDisbursalField = new CheckBox("loanTrancheDetailEnableMultipleDisbursalField", new PropertyModel<>(this, "loanTrancheDetailEnableMultipleDisbursalValue"));
        this.loanTrancheDetailEnableMultipleDisbursalField.setRequired(false);
        this.loanTrancheDetailEnableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::loanTrancheDetailEnableMultipleDisbursalFieldUpdate));
        this.loanTrancheDetailEnableMultipleDisbursalContainer.add(this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalFeedback = new TextFeedbackPanel("loanTrancheDetailEnableMultipleDisbursalFeedback", this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalContainer.add(this.loanTrancheDetailEnableMultipleDisbursalFeedback);

        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountBlock");
        this.loanTrancheDetailMaximumTrancheCountBlock.setOutputMarkupId(true);
        this.form.add(this.loanTrancheDetailMaximumTrancheCountBlock);
        this.loanTrancheDetailMaximumTrancheCountContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountContainer");
        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountContainer);
        this.loanTrancheDetailMaximumTrancheCountField = new TextField<>("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountField.setLabel(Model.of("Maximum Tranche count"));
        this.loanTrancheDetailMaximumTrancheCountField.setRequired(false);
        this.loanTrancheDetailMaximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumTrancheCountContainer.add(this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumTrancheCountFeedback", this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountContainer.add(this.loanTrancheDetailMaximumTrancheCountFeedback);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.setOutputMarkupId(true);
        this.form.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceContainer);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField = new TextField<>("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setLabel(Model.of("Maximum allowed outstanding balance"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setRequired(false);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback", this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback);
    }

    protected boolean loanTrancheDetailEnableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.loanTrancheDetailEnableMultipleDisbursalValue != null && this.loanTrancheDetailEnableMultipleDisbursalValue;
        this.loanTrancheDetailMaximumTrancheCountContainer.setVisible(visible);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceContainer.setVisible(visible);
        if (target != null) {
            target.add(this.loanTrancheDetailMaximumTrancheCountBlock);
            target.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        }
        return false;
    }

    protected void initGuaranteeRequirements() {

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.setOutputMarkupId(true);
        this.form.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField = new CheckBox("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", new PropertyModel<>(this, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField.setRequired(false);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback);

        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeBlock");
        this.guaranteeRequirementMandatoryGuaranteeBlock.setOutputMarkupId(true);
        this.form.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
        this.guaranteeRequirementMandatoryGuaranteeContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeContainer");
        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeContainer);
        this.guaranteeRequirementMandatoryGuaranteeField = new TextField<>("guaranteeRequirementMandatoryGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMandatoryGuaranteeValue"));
        this.guaranteeRequirementMandatoryGuaranteeField.setLabel(Model.of("Mandatory Guarantee: (%)"));
        this.guaranteeRequirementMandatoryGuaranteeField.setRequired(false);
        this.guaranteeRequirementMandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMandatoryGuaranteeContainer.add(this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMandatoryGuaranteeFeedback", this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeContainer.add(this.guaranteeRequirementMandatoryGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeBlock");
        this.guaranteeRequirementMinimumGuaranteeBlock.setOutputMarkupId(true);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeBlock);
        this.guaranteeRequirementMinimumGuaranteeContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeContainer");
        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeContainer);
        this.guaranteeRequirementMinimumGuaranteeField = new TextField<>("guaranteeRequirementMinimumGuaranteeField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeValue"));
        this.guaranteeRequirementMinimumGuaranteeField.setLabel(Model.of("Minimum Guarantee from Own Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeField.setRequired(false);
        this.guaranteeRequirementMinimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeContainer.add(this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFeedback", this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeContainer.add(this.guaranteeRequirementMinimumGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.setOutputMarkupId(true);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorContainer);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField = new TextField<>("guaranteeRequirementMinimumGuaranteeFromGuarantorField", new PropertyModel<>(this, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setLabel(Model.of("Minimum Guarantee from Guarantor Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setRequired(false);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback", this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback);

    }

    protected boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue != null && this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
        this.guaranteeRequirementMandatoryGuaranteeContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorContainer.setVisible(visible);
        if (target != null) {
            target.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        }
        return false;
    }

    protected boolean interestRecalculationRecalculateInterestFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue;
        this.interestRecalculationPreClosureInterestCalculationRuleContainer.setVisible(visible);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeContainer.setVisible(visible);
        this.interestRecalculationCompoundingOnContainer.setVisible(visible);
        this.interestRecalculationCompoundingContainer.setVisible(visible);
        this.interestRecalculationCompoundingTypeContainer.setVisible(visible);
        this.interestRecalculationCompoundingDayContainer.setVisible(visible);
        this.interestRecalculationCompoundingIntervalContainer.setVisible(visible);
        this.interestRecalculationRecalculateContainer.setVisible(visible);
        this.interestRecalculationRecalculateTypeContainer.setVisible(visible);
        this.interestRecalculationRecalculateDayContainer.setVisible(visible);
        this.interestRecalculationRecalculateIntervalContainer.setVisible(visible);
        if (visible) {
            interestRecalculationRecalculateFieldUpdate(target);
            interestRecalculationCompoundingFieldUpdate(target);
        }
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer.setVisible(visible);
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
        return false;
    }

    protected void initInterestRecalculation() {

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupContainer("interestRecalculationRecalculateInterestBlock");
        this.interestRecalculationRecalculateInterestBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestContainer);
        this.interestRecalculationRecalculateInterestField = new CheckBox("interestRecalculationRecalculateInterestField", new PropertyModel<>(this, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestField.setRequired(false);
        this.interestRecalculationRecalculateInterestField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateInterestFieldUpdate));
        this.interestRecalculationRecalculateInterestContainer.add(this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestFeedback = new TextFeedbackPanel("interestRecalculationRecalculateInterestFeedback", this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestContainer.add(this.interestRecalculationRecalculateInterestFeedback);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleBlock");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
        this.interestRecalculationPreClosureInterestCalculationRuleField = new Select2SingleChoice<>("interestRecalculationPreClosureInterestCalculationRuleField", 0, new PropertyModel<>(this, "interestRecalculationPreClosureInterestCalculationRuleValue"), this.interestRecalculationPreClosureInterestCalculationRuleProvider);
        this.interestRecalculationPreClosureInterestCalculationRuleField.setLabel(Model.of("Pre-closure interest calculation rule"));
        this.interestRecalculationPreClosureInterestCalculationRuleField.setRequired(false);
        this.interestRecalculationPreClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationPreClosureInterestCalculationRuleContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleFeedback = new TextFeedbackPanel("interestRecalculationPreClosureInterestCalculationRuleFeedback", this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleFeedback);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeBlock");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("interestRecalculationAdvancePaymentsAdjustmentTypeField", 0, new PropertyModel<>(this, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"), this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setLabel(Model.of("Advance payments adjustment type"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setRequired(false);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationAdvancePaymentsAdjustmentTypeContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback = new TextFeedbackPanel("interestRecalculationAdvancePaymentsAdjustmentTypeFeedback", this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupContainer("interestRecalculationCompoundingOnBlock");
        this.interestRecalculationCompoundingOnBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnContainer = new WebMarkupContainer("interestRecalculationCompoundingOnContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnContainer);
        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
        this.interestRecalculationCompoundingOnField = new Select2SingleChoice<>("interestRecalculationCompoundingOnField", 0, new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"), this.interestRecalculationCompoundingOnProvider);
        this.interestRecalculationCompoundingOnField.setLabel(Model.of("Interest recalculation compounding on"));
        this.interestRecalculationCompoundingOnField.setRequired(false);
        this.interestRecalculationCompoundingOnField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingOnContainer.add(this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnContainer.add(this.interestRecalculationCompoundingOnFeedback);

        this.interestRecalculationCompoundingBlock = new WebMarkupContainer("interestRecalculationCompoundingBlock");
        this.interestRecalculationCompoundingBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingContainer = new WebMarkupContainer("interestRecalculationCompoundingContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingContainer);
        this.interestRecalculationCompoundingProvider = new FrequencyProvider();
        this.interestRecalculationCompoundingField = new Select2SingleChoice<>("interestRecalculationCompoundingField", 0, new PropertyModel<>(this, "interestRecalculationCompoundingValue"), this.interestRecalculationCompoundingProvider);
        this.interestRecalculationCompoundingField.setLabel(Model.of("Frequency for compounding"));
        this.interestRecalculationCompoundingField.setRequired(false);
        this.interestRecalculationCompoundingContainer.add(this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingFeedback = new TextFeedbackPanel("interestRecalculationCompoundingFeedback", this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingContainer.add(this.interestRecalculationCompoundingFeedback);

        this.interestRecalculationCompoundingTypeBlock = new WebMarkupContainer("interestRecalculationCompoundingTypeBlock");
        this.interestRecalculationCompoundingTypeBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationCompoundingTypeBlock);
        this.interestRecalculationCompoundingTypeContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeContainer");
        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeContainer);
        this.interestRecalculationCompoundingTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationCompoundingTypeField = new Select2SingleChoice<>("interestRecalculationCompoundingTypeField", 0, new PropertyModel<>(this, "interestRecalculationCompoundingTypeValue"), this.interestRecalculationCompoundingTypeProvider);
        this.interestRecalculationCompoundingTypeField.setLabel(Model.of("Frequency type for compounding"));
        this.interestRecalculationCompoundingTypeField.setRequired(false);
        this.interestRecalculationCompoundingTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingTypeContainer.add(this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeFeedback = new TextFeedbackPanel("interestRecalculationCompoundingTypeFeedback", this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeContainer.add(this.interestRecalculationCompoundingTypeFeedback);

        this.interestRecalculationCompoundingDayBlock = new WebMarkupContainer("interestRecalculationCompoundingDayBlock");
        this.interestRecalculationCompoundingDayBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationCompoundingDayBlock);
        this.interestRecalculationCompoundingDayContainer = new WebMarkupContainer("interestRecalculationCompoundingDayContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayContainer);
        this.interestRecalculationCompoundingDayProvider = new FrequencyDayProvider();
        this.interestRecalculationCompoundingDayField = new Select2SingleChoice<>("interestRecalculationCompoundingDayField", 0, new PropertyModel<>(this, "interestRecalculationCompoundingDayValue"), this.interestRecalculationCompoundingDayProvider);
        this.interestRecalculationCompoundingDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingDayField.setRequired(false);
        this.interestRecalculationCompoundingDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingDayContainer.add(this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingDayFeedback", this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayContainer.add(this.interestRecalculationCompoundingDayFeedback);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupContainer("interestRecalculationCompoundingIntervalBlock");
        this.interestRecalculationCompoundingIntervalBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalContainer);
        this.interestRecalculationCompoundingIntervalField = new TextField<>("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalField.setLabel(Model.of("Frequency Interval for compounding"));
        this.interestRecalculationCompoundingIntervalField.setRequired(false);
        this.interestRecalculationCompoundingIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingIntervalContainer.add(this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalFeedback = new TextFeedbackPanel("interestRecalculationCompoundingIntervalFeedback", this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalContainer.add(this.interestRecalculationCompoundingIntervalFeedback);

        this.interestRecalculationRecalculateBlock = new WebMarkupContainer("interestRecalculationRecalculateBlock");
        this.interestRecalculationRecalculateBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationRecalculateBlock);
        this.interestRecalculationRecalculateContainer = new WebMarkupContainer("interestRecalculationRecalculateContainer");
        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateContainer);
        this.interestRecalculationRecalculateProvider = new FrequencyProvider();
        this.interestRecalculationRecalculateField = new Select2SingleChoice<>("interestRecalculationRecalculateField", 0, new PropertyModel<>(this, "interestRecalculationRecalculateValue"), this.interestRecalculationRecalculateProvider);
        this.interestRecalculationRecalculateField.setLabel(Model.of("Frequency for recalculate Outstanding Principal"));
        this.interestRecalculationRecalculateField.setRequired(false);
        this.interestRecalculationRecalculateField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateContainer.add(this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateFeedback = new TextFeedbackPanel("interestRecalculationRecalculateFeedback", this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateContainer.add(this.interestRecalculationRecalculateFeedback);

        this.interestRecalculationRecalculateTypeBlock = new WebMarkupContainer("interestRecalculationRecalculateTypeBlock");
        this.interestRecalculationRecalculateTypeBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationRecalculateTypeBlock);
        this.interestRecalculationRecalculateTypeContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeContainer");
        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeContainer);
        this.interestRecalculationRecalculateTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationRecalculateTypeField = new Select2SingleChoice<>("interestRecalculationRecalculateTypeField", 0, new PropertyModel<>(this, "interestRecalculationRecalculateTypeValue"), this.interestRecalculationRecalculateTypeProvider);
        this.interestRecalculationRecalculateTypeField.setLabel(Model.of("Frequency type for recalculate"));
        this.interestRecalculationRecalculateTypeField.setRequired(false);
        this.interestRecalculationRecalculateTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateTypeContainer.add(this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeFeedback = new TextFeedbackPanel("interestRecalculationRecalculateTypeFeedback", this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeContainer.add(this.interestRecalculationRecalculateTypeFeedback);

        this.interestRecalculationRecalculateDayBlock = new WebMarkupContainer("interestRecalculationRecalculateDayBlock");
        this.interestRecalculationRecalculateDayBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationRecalculateDayBlock);
        this.interestRecalculationRecalculateDayContainer = new WebMarkupContainer("interestRecalculationRecalculateDayContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayContainer);
        this.interestRecalculationRecalculateDayProvider = new FrequencyDayProvider();
        this.interestRecalculationRecalculateDayField = new Select2SingleChoice<>("interestRecalculationRecalculateDayField", 0, new PropertyModel<>(this, "interestRecalculationRecalculateDayValue"), this.interestRecalculationRecalculateDayProvider);
        this.interestRecalculationRecalculateDayField.setLabel(Model.of("Frequency day for recalculate"));
        this.interestRecalculationRecalculateDayField.setRequired(false);
        this.interestRecalculationRecalculateDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateDayContainer.add(this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateDayFeedback", this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayContainer.add(this.interestRecalculationRecalculateDayFeedback);

        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupContainer("interestRecalculationRecalculateIntervalBlock");
        this.interestRecalculationRecalculateIntervalBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationRecalculateIntervalBlock);
        this.interestRecalculationRecalculateIntervalContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalContainer");
        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalContainer);
        this.interestRecalculationRecalculateIntervalField = new TextField<>("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalField.setLabel(Model.of("Frequency Interval for recalculate"));
        this.interestRecalculationRecalculateIntervalField.setRequired(false);
        this.interestRecalculationRecalculateIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateIntervalContainer.add(this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalFeedback = new TextFeedbackPanel("interestRecalculationRecalculateIntervalFeedback", this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalContainer.add(this.interestRecalculationRecalculateIntervalFeedback);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.setOutputMarkupId(true);
        this.form.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField = new CheckBox("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField.setRequired(false);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback", this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback);

    }

    protected boolean interestRecalculationRecalculateFieldUpdate(AjaxRequestTarget target) {
        this.interestRecalculationRecalculateTypeContainer.setVisible(false);
        this.interestRecalculationRecalculateDayContainer.setVisible(false);
        this.interestRecalculationRecalculateIntervalContainer.setVisible(false);

        if (this.interestRecalculationRecalculateValue != null) {
            Frequency frequency = Frequency.valueOf(this.interestRecalculationRecalculateValue.getId());
            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateIntervalContainer.setVisible(true);
            }
            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateDayContainer.setVisible(true);
            }
            if (frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateTypeContainer.setVisible(true);
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

        this.interestRecalculationCompoundingTypeContainer.setVisible(false);
        this.interestRecalculationCompoundingDayContainer.setVisible(false);
        this.interestRecalculationCompoundingIntervalContainer.setVisible(false);

        if (this.interestRecalculationCompoundingOnValue != null && InterestRecalculationCompound.valueOf(this.interestRecalculationCompoundingOnValue.getId()) != InterestRecalculationCompound.None) {
            this.interestRecalculationCompoundingContainer.setVisible(true);

            if (this.interestRecalculationCompoundingValue != null) {
                Frequency frequency = Frequency.valueOf(this.interestRecalculationCompoundingValue.getId());
                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingIntervalContainer.setVisible(true);
                }
                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingDayContainer.setVisible(true);
                }
                if (frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingTypeContainer.setVisible(true);
                }
            }
        } else {
            this.interestRecalculationCompoundingContainer.setVisible(false);
        }
        if (target != null) {
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.interestRecalculationCompoundingIntervalBlock);
            target.add(this.interestRecalculationCompoundingBlock);
        }
        return false;
    }

    protected void initSetting() {

        this.settingAmortizationBlock = new WebMarkupContainer("settingAmortizationBlock");
        this.settingAmortizationBlock.setOutputMarkupId(true);
        this.form.add(this.settingAmortizationBlock);
        this.settingAmortizationContainer = new WebMarkupContainer("settingAmortizationContainer");
        this.settingAmortizationBlock.add(this.settingAmortizationContainer);
        this.settingAmortizationProvider = new AmortizationProvider();
        this.settingAmortizationField = new Select2SingleChoice<>("settingAmortizationField", 0, new PropertyModel<>(this, "settingAmortizationValue"), this.settingAmortizationProvider);
        this.settingAmortizationField.setLabel(Model.of("Amortization"));
        this.settingAmortizationField.setRequired(true);
        this.settingAmortizationField.add(new OnChangeAjaxBehavior());
        this.settingAmortizationContainer.add(this.settingAmortizationField);
        this.settingAmortizationFeedback = new TextFeedbackPanel("settingAmortizationFeedback", this.settingAmortizationField);
        this.settingAmortizationContainer.add(this.settingAmortizationFeedback);

        this.settingInterestMethodBlock = new WebMarkupContainer("settingInterestMethodBlock");
        this.settingInterestMethodBlock.setOutputMarkupId(true);
        this.form.add(this.settingInterestMethodBlock);
        this.settingInterestMethodContainer = new WebMarkupContainer("settingInterestMethodContainer");
        this.settingInterestMethodBlock.add(this.settingInterestMethodContainer);
        this.settingInterestMethodProvider = new InterestMethodProvider();
        this.settingInterestMethodField = new Select2SingleChoice<>("settingInterestMethodField", 0, new PropertyModel<>(this, "settingInterestMethodValue"), this.settingInterestMethodProvider);
        this.settingInterestMethodField.setLabel(Model.of("Interest method"));
        this.settingInterestMethodField.setRequired(true);
        this.settingInterestMethodField.add(new OnChangeAjaxBehavior());
        this.settingInterestMethodContainer.add(this.settingInterestMethodField);
        this.settingInterestMethodFeedback = new TextFeedbackPanel("settingInterestMethodFeedback", this.settingInterestMethodField);
        this.settingInterestMethodContainer.add(this.settingInterestMethodFeedback);

        this.settingInterestCalculationPeriodBlock = new WebMarkupContainer("settingInterestCalculationPeriodBlock");
        this.settingInterestCalculationPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.settingInterestCalculationPeriodBlock);
        this.settingInterestCalculationPeriodContainer = new WebMarkupContainer("settingInterestCalculationPeriodContainer");
        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodContainer);
        this.settingInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
        this.settingInterestCalculationPeriodField = new Select2SingleChoice<>("settingInterestCalculationPeriodField", 0, new PropertyModel<>(this, "settingInterestCalculationPeriodValue"), this.settingInterestCalculationPeriodProvider);
        this.settingInterestCalculationPeriodField.setLabel(Model.of("Interest calculation period"));
        this.settingInterestCalculationPeriodField.setRequired(true);
        this.settingInterestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::settingInterestCalculationPeriodFieldUpdate));
        this.settingInterestCalculationPeriodContainer.add(this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodFeedback = new TextFeedbackPanel("settingInterestCalculationPeriodFeedback", this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodContainer.add(this.settingInterestCalculationPeriodFeedback);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodBlock");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        this.settingCalculateInterestForExactDaysInPartialPeriodContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodContainer);
        this.settingCalculateInterestForExactDaysInPartialPeriodField = new CheckBox("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodField.setRequired(false);
        this.settingCalculateInterestForExactDaysInPartialPeriodField.add(new OnChangeAjaxBehavior());
        this.settingCalculateInterestForExactDaysInPartialPeriodContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodFeedback = new TextFeedbackPanel("settingCalculateInterestForExactDaysInPartialPeriodFeedback", this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodFeedback);

        this.settingRepaymentStrategyBlock = new WebMarkupContainer("settingRepaymentStrategyBlock");
        this.settingRepaymentStrategyBlock.setOutputMarkupId(true);
        this.form.add(this.settingRepaymentStrategyBlock);
        this.settingRepaymentStrategyContainer = new WebMarkupContainer("settingRepaymentStrategyContainer");
        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyContainer);
        this.settingRepaymentStrategyProvider = new RepaymentStrategyProvider();
        this.settingRepaymentStrategyField = new Select2SingleChoice<>("settingRepaymentStrategyField", 0, new PropertyModel<>(this, "settingRepaymentStrategyValue"), this.settingRepaymentStrategyProvider);
        this.settingRepaymentStrategyField.setLabel(Model.of("Repayment strategy"));
        this.settingRepaymentStrategyField.setRequired(true);
        this.settingRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.settingRepaymentStrategyContainer.add(this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyFeedback = new TextFeedbackPanel("settingRepaymentStrategyFeedback", this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyContainer.add(this.settingRepaymentStrategyFeedback);

        this.settingMoratoriumPrincipalBlock = new WebMarkupContainer("settingMoratoriumPrincipalBlock");
        this.settingMoratoriumPrincipalBlock.setOutputMarkupId(true);
        this.form.add(this.settingMoratoriumPrincipalBlock);
        this.settingMoratoriumPrincipalContainer = new WebMarkupContainer("settingMoratoriumPrincipalContainer");
        this.settingMoratoriumPrincipalBlock.add(this.settingMoratoriumPrincipalContainer);
        this.settingMoratoriumPrincipalField = new TextField<>("settingMoratoriumPrincipalField", new PropertyModel<>(this, "settingMoratoriumPrincipalValue"));
        this.settingMoratoriumPrincipalField.setLabel(Model.of("Moratorium principal"));
        this.settingMoratoriumPrincipalField.setRequired(false);
        this.settingMoratoriumPrincipalField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumPrincipalContainer.add(this.settingMoratoriumPrincipalField);
        this.settingMoratoriumPrincipalFeedback = new TextFeedbackPanel("settingMoratoriumPrincipalFeedback", this.settingMoratoriumPrincipalField);
        this.settingMoratoriumPrincipalContainer.add(this.settingMoratoriumPrincipalFeedback);

        this.settingMoratoriumInterestBlock = new WebMarkupContainer("settingMoratoriumInterestBlock");
        this.settingMoratoriumInterestBlock.setOutputMarkupId(true);
        this.form.add(this.settingMoratoriumInterestBlock);
        this.settingMoratoriumInterestContainer = new WebMarkupContainer("settingMoratoriumInterestContainer");
        this.settingMoratoriumInterestBlock.add(this.settingMoratoriumInterestContainer);
        this.settingMoratoriumInterestField = new TextField<>("settingMoratoriumInterestField", new PropertyModel<>(this, "settingMoratoriumInterestValue"));
        this.settingMoratoriumInterestField.setLabel(Model.of("Moratorium interest"));
        this.settingMoratoriumInterestField.setRequired(false);
        this.settingMoratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumInterestContainer.add(this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestFeedback = new TextFeedbackPanel("settingMoratoriumInterestFeedback", this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestContainer.add(this.settingMoratoriumInterestFeedback);

        this.settingInterestFreePeriodBlock = new WebMarkupContainer("settingInterestFreePeriodBlock");
        this.settingInterestFreePeriodBlock.setOutputMarkupId(true);
        this.form.add(this.settingInterestFreePeriodBlock);
        this.settingInterestFreePeriodContainer = new WebMarkupContainer("settingInterestFreePeriodContainer");
        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodContainer);
        this.settingInterestFreePeriodField = new TextField<>("settingInterestFreePeriodField", new PropertyModel<>(this, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodField.setLabel(Model.of("Interest free period"));
        this.settingInterestFreePeriodField.setRequired(false);
        this.settingInterestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.settingInterestFreePeriodContainer.add(this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodFeedback = new TextFeedbackPanel("settingInterestFreePeriodFeedback", this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodContainer.add(this.settingInterestFreePeriodFeedback);

        this.settingArrearsToleranceBlock = new WebMarkupContainer("settingArrearsToleranceBlock");
        this.settingArrearsToleranceBlock.setOutputMarkupId(true);
        this.form.add(this.settingArrearsToleranceBlock);
        this.settingArrearsToleranceContainer = new WebMarkupContainer("settingArrearsToleranceContainer");
        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceContainer);
        this.settingArrearsToleranceField = new TextField<>("settingArrearsToleranceField", new PropertyModel<>(this, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceField.setLabel(Model.of("Arrears tolerance"));
        this.settingArrearsToleranceField.setRequired(false);
        this.settingArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.settingArrearsToleranceContainer.add(this.settingArrearsToleranceField);
        this.settingArrearsToleranceFeedback = new TextFeedbackPanel("settingArrearsToleranceFeedback", this.settingArrearsToleranceField);
        this.settingArrearsToleranceContainer.add(this.settingArrearsToleranceFeedback);

        this.settingDayInYearBlock = new WebMarkupContainer("settingDayInYearBlock");
        this.settingDayInYearBlock.setOutputMarkupId(true);
        this.form.add(this.settingDayInYearBlock);
        this.settingDayInYearContainer = new WebMarkupContainer("settingDayInYearContainer");
        this.settingDayInYearBlock.add(this.settingDayInYearContainer);
        this.settingDayInYearProvider = new DayInYearProvider();
        this.settingDayInYearField = new Select2SingleChoice<>("settingDayInYearField", 0, new PropertyModel<>(this, "settingDayInYearValue"), this.settingDayInYearProvider);
        this.settingDayInYearField.setLabel(Model.of("Day in year"));
        this.settingDayInYearField.setRequired(true);
        this.settingDayInYearField.add(new OnChangeAjaxBehavior());
        this.settingDayInYearContainer.add(this.settingDayInYearField);
        this.settingDayInYearFeedback = new TextFeedbackPanel("settingDayInYearFeedback", this.settingDayInYearField);
        this.settingDayInYearContainer.add(this.settingDayInYearFeedback);

        this.settingDayInMonthBlock = new WebMarkupContainer("settingDayInMonthBlock");
        this.settingDayInMonthBlock.setOutputMarkupId(true);
        this.form.add(this.settingDayInMonthBlock);
        this.settingDayInMonthContainer = new WebMarkupContainer("settingDayInMonthContainer");
        this.settingDayInMonthBlock.add(this.settingDayInMonthContainer);
        this.settingDayInMonthProvider = new DayInMonthProvider();
        this.settingDayInMonthField = new Select2SingleChoice<>("settingDayInMonthField", 0, new PropertyModel<>(this, "settingDayInMonthValue"), this.settingDayInMonthProvider);
        this.settingDayInMonthField.setLabel(Model.of("Day in month"));
        this.settingDayInMonthField.setRequired(true);
        this.settingDayInMonthField.add(new OnChangeAjaxBehavior());
        this.settingDayInMonthContainer.add(this.settingDayInMonthField);
        this.settingDayInMonthFeedback = new TextFeedbackPanel("settingDayInMonthFeedback", this.settingDayInMonthField);
        this.settingDayInMonthContainer.add(this.settingDayInMonthFeedback);

        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountBlock");
        this.settingAllowFixingOfTheInstallmentAmountBlock.setOutputMarkupId(true);
        this.form.add(this.settingAllowFixingOfTheInstallmentAmountBlock);
        this.settingAllowFixingOfTheInstallmentAmountContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountContainer");
        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountContainer);
        this.settingAllowFixingOfTheInstallmentAmountField = new CheckBox("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountField.setRequired(false);
        this.settingAllowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());
        this.settingAllowFixingOfTheInstallmentAmountContainer.add(this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel("settingAllowFixingOfTheInstallmentAmountFeedback", this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountContainer.add(this.settingAllowFixingOfTheInstallmentAmountFeedback);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.setOutputMarkupId(true);
        this.form.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setLabel(Model.of("Number of days a loan may be overdue before moving into arrears"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setRequired(false);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback", this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.setOutputMarkupId(true);
        this.form.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setLabel(Model.of("Maximum number of days a loan may be overdue before becoming a NPA"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setRequired(false);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback", this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.setOutputMarkupId(true);
        this.form.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.setRequired(false);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback", this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);

        this.settingPrincipalThresholdForLastInstalmentBlock = new WebMarkupContainer("settingPrincipalThresholdForLastInstalmentBlock");
        this.settingPrincipalThresholdForLastInstalmentBlock.setOutputMarkupId(true);
        this.form.add(this.settingPrincipalThresholdForLastInstalmentBlock);
        this.settingPrincipalThresholdForLastInstalmentContainer = new WebMarkupContainer("settingPrincipalThresholdForLastInstalmentContainer");
        this.settingPrincipalThresholdForLastInstalmentBlock.add(this.settingPrincipalThresholdForLastInstalmentContainer);
        this.settingPrincipalThresholdForLastInstalmentField = new TextField<>("settingPrincipalThresholdForLastInstalmentField", new PropertyModel<>(this, "settingPrincipalThresholdForLastInstalmentValue"));
        this.settingPrincipalThresholdForLastInstalmentField.setLabel(Model.of("Principal Threshold (%) for Last Installment"));
        this.settingPrincipalThresholdForLastInstalmentField.setRequired(false);
        this.settingPrincipalThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.settingPrincipalThresholdForLastInstalmentContainer.add(this.settingPrincipalThresholdForLastInstalmentField);
        this.settingPrincipalThresholdForLastInstalmentFeedback = new TextFeedbackPanel("settingPrincipalThresholdForLastInstalmentFeedback", this.settingPrincipalThresholdForLastInstalmentField);
        this.settingPrincipalThresholdForLastInstalmentContainer.add(this.settingPrincipalThresholdForLastInstalmentFeedback);

        this.settingVariableInstallmentsAllowedBlock = new WebMarkupContainer("settingVariableInstallmentsAllowedBlock");
        this.settingVariableInstallmentsAllowedBlock.setOutputMarkupId(true);
        this.form.add(this.settingVariableInstallmentsAllowedBlock);
        this.settingVariableInstallmentsAllowedContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedContainer");
        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedContainer);
        this.settingVariableInstallmentsAllowedField = new CheckBox("settingVariableInstallmentsAllowedField", new PropertyModel<>(this, "settingVariableInstallmentsAllowedValue"));
        this.settingVariableInstallmentsAllowedField.setRequired(false);
        this.settingVariableInstallmentsAllowedField.add(new OnChangeAjaxBehavior(this::settingVariableInstallmentsAllowedFieldUpdate));
        this.settingVariableInstallmentsAllowedContainer.add(this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedFeedback = new TextFeedbackPanel("settingVariableInstallmentsAllowedFeedback", this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedContainer.add(this.settingVariableInstallmentsAllowedFeedback);

        this.settingVariableInstallmentsMinimumBlock = new WebMarkupContainer("settingVariableInstallmentsMinimumBlock");
        this.settingVariableInstallmentsMinimumBlock.setOutputMarkupId(true);
        this.form.add(this.settingVariableInstallmentsMinimumBlock);
        this.settingVariableInstallmentsMinimumContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumContainer");
        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumContainer);
        this.settingVariableInstallmentsMinimumField = new TextField<>("settingVariableInstallmentsMinimumField", new PropertyModel<>(this, "settingVariableInstallmentsMinimumValue"));
        this.settingVariableInstallmentsMinimumField.setLabel(Model.of("Variable Installments Minimum"));
        this.settingVariableInstallmentsMinimumField.setRequired(false);
        this.settingVariableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMinimumContainer.add(this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMinimumFeedback", this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumContainer.add(this.settingVariableInstallmentsMinimumFeedback);

        this.settingVariableInstallmentsMaximumBlock = new WebMarkupContainer("settingVariableInstallmentsMaximumBlock");
        this.settingVariableInstallmentsMaximumBlock.setOutputMarkupId(true);
        this.form.add(this.settingVariableInstallmentsMaximumBlock);
        this.settingVariableInstallmentsMaximumContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumContainer");
        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumContainer);
        this.settingVariableInstallmentsMaximumField = new TextField<>("settingVariableInstallmentsMaximumField", new PropertyModel<>(this, "settingVariableInstallmentsMaximumValue"));
        this.settingVariableInstallmentsMaximumField.setLabel(Model.of("Variable Installments Maximum"));
        this.settingVariableInstallmentsMaximumField.setRequired(false);
        this.settingVariableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMaximumContainer.add(this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMaximumFeedback", this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumContainer.add(this.settingVariableInstallmentsMaximumFeedback);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansBlock");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.setOutputMarkupId(true);
        this.form.add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
        this.settingAllowedToBeUsedForProvidingTopupLoansContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansContainer);
        this.settingAllowedToBeUsedForProvidingTopupLoansField = new CheckBox("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansField.setRequired(false);
        this.settingAllowedToBeUsedForProvidingTopupLoansField.add(new OnChangeAjaxBehavior());
        this.settingAllowedToBeUsedForProvidingTopupLoansContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansFeedback = new TextFeedbackPanel("settingAllowedToBeUsedForProvidingTopupLoansFeedback", this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansFeedback);

    }

    protected void initDefault() {
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
    }

    protected void initDetail() {
        this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
        this.detailProductNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
        this.detailProductNameBlock.add(this.detailProductNameContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameField.setRequired(true);
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
        this.detailShortNameField.setRequired(true);
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
        this.detailDescriptionField.setRequired(false);
        this.detailDescriptionContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionContainer.add(this.detailDescriptionFeedback);

        this.detailFundProvider = new SingleChoiceProvider("m_fund", "id", "name");
        this.detailFundBlock = new WebMarkupContainer("detailFundBlock");
        this.detailFundBlock.setOutputMarkupId(true);
        this.form.add(this.detailFundBlock);
        this.detailFundContainer = new WebMarkupContainer("detailFundContainer");
        this.detailFundBlock.add(this.detailFundContainer);
        this.detailFundField = new Select2SingleChoice<>("detailFundField", 0, new PropertyModel<>(this, "detailFundValue"), this.detailFundProvider);
        this.detailFundField.setLabel(Model.of("Fund"));
        this.detailFundField.setRequired(false);
        this.detailFundContainer.add(this.detailFundField);
        this.detailFundFeedback = new TextFeedbackPanel("detailFundFeedback", this.detailFundField);
        this.detailFundContainer.add(this.detailFundFeedback);

        this.detailStartDateBlock = new WebMarkupContainer("detailStartDateBlock");
        this.detailStartDateBlock.setOutputMarkupId(true);
        this.form.add(this.detailStartDateBlock);
        this.detailStartDateContainer = new WebMarkupContainer("detailStartDateContainer");
        this.detailStartDateBlock.add(this.detailStartDateContainer);
        this.detailStartDateField = new DateTextField("detailStartDateField", new PropertyModel<>(this, "detailStartDateValue"));
        this.detailStartDateField.setLabel(Model.of("Start Date"));
        this.detailStartDateField.setRequired(false);
        this.detailStartDateContainer.add(this.detailStartDateField);
        this.detailStartDateFeedback = new TextFeedbackPanel("detailStartDateFeedback", this.detailStartDateField);
        this.detailStartDateContainer.add(this.detailStartDateFeedback);

        this.detailCloseDateBlock = new WebMarkupContainer("detailCloseDateBlock");
        this.detailCloseDateBlock.setOutputMarkupId(true);
        this.form.add(this.detailCloseDateBlock);
        this.detailCloseDateContainer = new WebMarkupContainer("detailCloseDateContainer");
        this.detailCloseDateBlock.add(this.detailCloseDateContainer);
        this.detailCloseDateField = new DateTextField("detailCloseDateField", new PropertyModel<>(this, "detailCloseDateValue"));
        this.detailCloseDateField.setLabel(Model.of("Close Date"));
        this.detailCloseDateField.setRequired(false);
        this.detailCloseDateContainer.add(this.detailCloseDateField);
        this.detailCloseDateFeedback = new TextFeedbackPanel("detailCloseDateFeedback", this.detailCloseDateField);
        this.detailCloseDateContainer.add(this.detailCloseDateFeedback);

        this.detailIncludeInCustomerLoanCounterBlock = new WebMarkupContainer("detailIncludeInCustomerLoanCounterBlock");
        this.detailIncludeInCustomerLoanCounterBlock.setOutputMarkupId(true);
        this.form.add(this.detailIncludeInCustomerLoanCounterBlock);
        this.detailIncludeInCustomerLoanCounterContainer = new WebMarkupContainer("detailIncludeInCustomerLoanCounterContainer");
        this.detailIncludeInCustomerLoanCounterBlock.add(this.detailIncludeInCustomerLoanCounterContainer);
        this.detailIncludeInCustomerLoanCounterField = new CheckBox("detailIncludeInCustomerLoanCounterField", new PropertyModel<>(this, "detailIncludeInCustomerLoanCounterValue"));
        this.detailIncludeInCustomerLoanCounterField.setRequired(false);
        this.detailIncludeInCustomerLoanCounterContainer.add(this.detailIncludeInCustomerLoanCounterField);
        this.detailIncludeInCustomerLoanCounterFeedback = new TextFeedbackPanel("detailIncludeInCustomerLoanCounterFeedback", this.detailIncludeInCustomerLoanCounterField);
        this.detailIncludeInCustomerLoanCounterContainer.add(this.detailIncludeInCustomerLoanCounterFeedback);
    }

    protected void initCurrency() {

        this.currencyCodeBlock = new WebMarkupContainer("currencyCodeBlock");
        this.currencyCodeBlock.setOutputMarkupId(true);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeContainer = new WebMarkupContainer("currencyCodeContainer");
        this.currencyCodeBlock.add(this.currencyCodeContainer);
        this.currencyCodeProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name", "concat(name,' [', code,']')");
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.setRequired(true);
        this.currencyCodeContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupContainer("currencyDecimalPlaceBlock");
        this.currencyDecimalPlaceBlock.setOutputMarkupId(true);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceContainer = new WebMarkupContainer("currencyDecimalPlaceContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal Places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceField.add(RangeValidator.range((int) 0, (int) 6));
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyInMultipleOfBlock = new WebMarkupContainer("currencyInMultipleOfBlock");
        this.currencyInMultipleOfBlock.setOutputMarkupId(true);
        this.form.add(this.currencyInMultipleOfBlock);
        this.currencyInMultipleOfContainer = new WebMarkupContainer("currencyInMultipleOfContainer");
        this.currencyInMultipleOfBlock.add(this.currencyInMultipleOfContainer);
        this.currencyInMultipleOfField = new TextField<>("currencyInMultipleOfField", new PropertyModel<>(this, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfField.setRequired(false);
        this.currencyInMultipleOfField.setLabel(Model.of("Currency in multiple of"));
        this.currencyInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.currencyInMultipleOfContainer.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback", this.currencyInMultipleOfField);
        this.currencyInMultipleOfContainer.add(this.currencyInMultipleOfFeedback);

        this.currencyInstallmentInMultipleOfBlock = new WebMarkupContainer("currencyInstallmentInMultipleOfBlock");
        this.currencyInstallmentInMultipleOfBlock.setOutputMarkupId(true);
        this.form.add(this.currencyInstallmentInMultipleOfBlock);
        this.currencyInstallmentInMultipleOfContainer = new WebMarkupContainer("currencyInstallmentInMultipleOfContainer");
        this.currencyInstallmentInMultipleOfBlock.add(this.currencyInstallmentInMultipleOfContainer);
        this.currencyInstallmentInMultipleOfField = new TextField<>("currencyInstallmentInMultipleOfField", new PropertyModel<>(this, "currencyInstallmentInMultipleOfValue"));
        this.currencyInstallmentInMultipleOfField.setLabel(Model.of("Installment in multiple of"));
        this.currencyInstallmentInMultipleOfField.setRequired(false);
        this.currencyInstallmentInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInstallmentInMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.currencyInstallmentInMultipleOfContainer.add(this.currencyInstallmentInMultipleOfField);
        this.currencyInstallmentInMultipleOfFeedback = new TextFeedbackPanel("currencyInstallmentInMultipleOfFeedback", this.currencyInstallmentInMultipleOfField);
        this.currencyInstallmentInMultipleOfContainer.add(this.currencyInstallmentInMultipleOfFeedback);
    }

    protected void initTerms() {

        this.termVaryBasedOnLoanCycleBlock = new WebMarkupContainer("termVaryBasedOnLoanCycleBlock");
        this.termVaryBasedOnLoanCycleBlock.setOutputMarkupId(true);
        this.form.add(this.termVaryBasedOnLoanCycleBlock);
        this.termVaryBasedOnLoanCycleContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleContainer");
        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleContainer);
        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField", new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.setRequired(false);
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
        this.termVaryBasedOnLoanCycleContainer.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback", this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleContainer.add(this.termVaryBasedOnLoanCycleFeedback);

        this.termPrincipalMinimumBlock = new WebMarkupContainer("termPrincipalMinimumBlock");
        this.termPrincipalMinimumBlock.setOutputMarkupId(true);
        this.form.add(this.termPrincipalMinimumBlock);
        this.termPrincipalMinimumContainer = new WebMarkupContainer("termPrincipalMinimumContainer");
        this.termPrincipalMinimumBlock.add(this.termPrincipalMinimumContainer);
        this.termPrincipalMinimumField = new TextField<>("termPrincipalMinimumField", new PropertyModel<>(this, "termPrincipalMinimumValue"));
        this.termPrincipalMinimumField.setLabel(Model.of("Principal Minimum"));
        this.termPrincipalMinimumField.add(new OnChangeAjaxBehavior());
        this.termPrincipalMinimumContainer.add(this.termPrincipalMinimumField);
        this.termPrincipalMinimumFeedback = new TextFeedbackPanel("termPrincipalMinimumFeedback", this.termPrincipalMinimumField);
        this.termPrincipalMinimumContainer.add(this.termPrincipalMinimumFeedback);

        this.termPrincipalDefaultBlock = new WebMarkupContainer("termPrincipalDefaultBlock");
        this.termPrincipalDefaultBlock.setOutputMarkupId(true);
        this.form.add(this.termPrincipalDefaultBlock);
        this.termPrincipalDefaultContainer = new WebMarkupContainer("termPrincipalDefaultContainer");
        this.termPrincipalDefaultBlock.add(this.termPrincipalDefaultContainer);
        this.termPrincipalDefaultField = new TextField<>("termPrincipalDefaultField", new PropertyModel<>(this, "termPrincipalDefaultValue"));
        this.termPrincipalDefaultField.setLabel(Model.of("Principal Default"));
        this.termPrincipalDefaultField.add(new OnChangeAjaxBehavior());
        this.termPrincipalDefaultContainer.add(this.termPrincipalDefaultField);
        this.termPrincipalDefaultFeedback = new TextFeedbackPanel("termPrincipalDefaultFeedback", this.termPrincipalDefaultField);
        this.termPrincipalDefaultContainer.add(this.termPrincipalDefaultFeedback);

        this.termPrincipalMaximumBlock = new WebMarkupContainer("termPrincipalMaximumBlock");
        this.termPrincipalMaximumBlock.setOutputMarkupId(true);
        this.form.add(this.termPrincipalMaximumBlock);
        this.termPrincipalMaximumContainer = new WebMarkupContainer("termPrincipalMaximumContainer");
        this.termPrincipalMaximumBlock.add(this.termPrincipalMaximumContainer);
        this.termPrincipalMaximumField = new TextField<>("termPrincipalMaximumField", new PropertyModel<>(this, "termPrincipalMaximumValue"));
        this.termPrincipalMaximumField.setLabel(Model.of("Principal Maximum"));
        this.termPrincipalMaximumField.add(new OnChangeAjaxBehavior());
        this.termPrincipalMaximumContainer.add(this.termPrincipalMaximumField);
        this.termPrincipalMaximumFeedback = new TextFeedbackPanel("termPrincipalMaximumFeedback", this.termPrincipalMaximumField);
        this.termPrincipalMaximumContainer.add(this.termPrincipalMaximumFeedback);

        {
            this.termPrincipalByLoanCyclePopup = new ModalWindow("termPrincipalByLoanCyclePopup");
            add(this.termPrincipalByLoanCyclePopup);
            this.termPrincipalByLoanCyclePopup.setContent(new PrincipalLoanCyclePopup(this.termPrincipalByLoanCyclePopup.getContentId(), this.termPrincipalByLoanCyclePopup, this));
            this.termPrincipalByLoanCyclePopup.setOnClose(this::termPrincipalByLoanCyclePopupOnClose);

            this.termPrincipalByLoanCycleBlock = new WebMarkupContainer("termPrincipalByLoanCycleBlock");
            this.termPrincipalByLoanCycleBlock.setOutputMarkupId(true);
            this.form.add(this.termPrincipalByLoanCycleBlock);
            this.termPrincipalByLoanCycleContainer = new WebMarkupContainer("termPrincipalByLoanCycleContainer");
            this.termPrincipalByLoanCycleBlock.add(this.termPrincipalByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> termPrincipalByLoanCycleColumn = Lists.newArrayList();
            termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipalByLoanCycleWhenColumn));
            termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipalByLoanCycleCycleColumn));
            termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipalByLoanCycleMinimumColumn));
            termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipalByLoanCycleDefaultColumn));
            termPrincipalByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipalByLoanCycleMaximumColumn));
            termPrincipalByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termPrincipalByLoanCycleActionItem, this::termPrincipalByLoanCycleActionClick));
            this.termPrincipalByLoanCycleProvider = new ListDataProvider(this.termPrincipalByLoanCycleValue);
            this.termPrincipalByLoanCycleTable = new DataTable<>("termPrincipalByLoanCycleTable", termPrincipalByLoanCycleColumn, this.termPrincipalByLoanCycleProvider, 20);
            this.termPrincipalByLoanCycleContainer.add(this.termPrincipalByLoanCycleTable);
            this.termPrincipalByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipalByLoanCycleTable, this.termPrincipalByLoanCycleProvider));
            this.termPrincipalByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipalByLoanCycleTable));

            this.termPrincipalByLoanCycleAddLink = new AjaxLink<>("termPrincipalByLoanCycleAddLink");
            this.termPrincipalByLoanCycleAddLink.setOnClick(this::termPrincipalByLoanCycleAddLinkClick);
            this.termPrincipalByLoanCycleContainer.add(this.termPrincipalByLoanCycleAddLink);
        }

        this.termNumberOfRepaymentMinimumBlock = new WebMarkupContainer("termNumberOfRepaymentMinimumBlock");
        this.termNumberOfRepaymentMinimumBlock.setOutputMarkupId(true);
        this.form.add(this.termNumberOfRepaymentMinimumBlock);
        this.termNumberOfRepaymentMinimumContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumContainer");
        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumContainer);
        this.termNumberOfRepaymentMinimumField = new TextField<>("termNumberOfRepaymentMinimumField", new PropertyModel<>(this, "termNumberOfRepaymentMinimumValue"));
        this.termNumberOfRepaymentMinimumField.setLabel(Model.of("Number of repayment Minimum"));
        this.termNumberOfRepaymentMinimumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMinimumContainer.add(this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMinimumFeedback", this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumContainer.add(this.termNumberOfRepaymentMinimumFeedback);

        this.termNumberOfRepaymentDefaultBlock = new WebMarkupContainer("termNumberOfRepaymentDefaultBlock");
        this.termNumberOfRepaymentDefaultBlock.setOutputMarkupId(true);
        this.form.add(this.termNumberOfRepaymentDefaultBlock);
        this.termNumberOfRepaymentDefaultContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultContainer");
        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultContainer);
        this.termNumberOfRepaymentDefaultField = new TextField<>("termNumberOfRepaymentDefaultField", new PropertyModel<>(this, "termNumberOfRepaymentDefaultValue"));
        this.termNumberOfRepaymentDefaultField.setLabel(Model.of("Number of repayment Default"));
        this.termNumberOfRepaymentDefaultField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentDefaultField.setRequired(true);
        this.termNumberOfRepaymentDefaultContainer.add(this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultFeedback = new TextFeedbackPanel("termNumberOfRepaymentDefaultFeedback", this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultContainer.add(this.termNumberOfRepaymentDefaultFeedback);

        this.termNumberOfRepaymentMaximumBlock = new WebMarkupContainer("termNumberOfRepaymentMaximumBlock");
        this.termNumberOfRepaymentMaximumBlock.setOutputMarkupId(true);
        this.form.add(this.termNumberOfRepaymentMaximumBlock);
        this.termNumberOfRepaymentMaximumContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumContainer");
        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumContainer);
        this.termNumberOfRepaymentMaximumField = new TextField<>("termNumberOfRepaymentMaximumField", new PropertyModel<>(this, "termNumberOfRepaymentMaximumValue"));
        this.termNumberOfRepaymentMaximumField.setLabel(Model.of("Number of repayment Maximum"));
        this.termNumberOfRepaymentMaximumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMaximumContainer.add(this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMaximumFeedback", this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumContainer.add(this.termNumberOfRepaymentMaximumFeedback);

        // Table
        {
            this.termNumberOfRepaymentByLoanCyclePopup = new ModalWindow("termNumberOfRepaymentByLoanCyclePopup");
            add(this.termNumberOfRepaymentByLoanCyclePopup);
            this.termNumberOfRepaymentByLoanCyclePopup.setContent(new RepaymentLoanCyclePopup(this.termNumberOfRepaymentByLoanCyclePopup.getContentId(), this.termNumberOfRepaymentByLoanCyclePopup, this));
            this.termNumberOfRepaymentByLoanCyclePopup.setOnClose(this::termNumberOfRepaymentByLoanCyclePopupOnClose);

            this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleBlock");
            this.termNumberOfRepaymentByLoanCycleBlock.setOutputMarkupId(true);
            this.form.add(this.termNumberOfRepaymentByLoanCycleBlock);
            this.termNumberOfRepaymentByLoanCycleContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleContainer");
            this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
            termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleWhenColumn));
            termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleCycleColumn));
            termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleMinimumColumn));
            termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleDefaultColumn));
            termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleMaximumColumn));
            termNumberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNumberOfRepaymentByLoanCycleActionItem, this::termNumberOfRepaymentByLoanCycleActionClick));
            this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue);
            this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
            this.termNumberOfRepaymentByLoanCycleContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
            this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
            this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));

            this.termNumberOfRepaymentByLoanCycleAddLink = new AjaxLink<>("termNumberOfRepaymentByLoanCycleAddLink");
            this.termNumberOfRepaymentByLoanCycleAddLink.setOnClick(this::termNumberOfRepaymentByLoanCycleAddLinkClick);
            this.termNumberOfRepaymentByLoanCycleContainer.add(this.termNumberOfRepaymentByLoanCycleAddLink);
        }

        // Linked to Floating Interest Rates
        this.termLinkedToFloatingInterestRatesBlock = new WebMarkupContainer("termLinkedToFloatingInterestRatesBlock");
        this.termLinkedToFloatingInterestRatesBlock.setOutputMarkupId(true);
        this.form.add(this.termLinkedToFloatingInterestRatesBlock);
        this.termLinkedToFloatingInterestRatesContainer = new WebMarkupContainer("termLinkedToFloatingInterestRatesContainer");
        this.termLinkedToFloatingInterestRatesBlock.add(this.termLinkedToFloatingInterestRatesContainer);
        this.termLinkedToFloatingInterestRatesField = new CheckBox("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this, "termLinkedToFloatingInterestRatesValue"));
        this.termLinkedToFloatingInterestRatesField.setRequired(false);
        this.termLinkedToFloatingInterestRatesField.add(new OnChangeAjaxBehavior(this::termLinkedToFloatingInterestRatesFieldUpdate));
        this.termLinkedToFloatingInterestRatesContainer.add(this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("termLinkedToFloatingInterestRatesFeedback", this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesContainer.add(this.termLinkedToFloatingInterestRatesFeedback);

        this.termNominalInterestRateMinimumBlock = new WebMarkupContainer("termNominalInterestRateMinimumBlock");
        this.termNominalInterestRateMinimumBlock.setOutputMarkupId(true);
        this.form.add(this.termNominalInterestRateMinimumBlock);
        this.termNominalInterestRateMinimumContainer = new WebMarkupContainer("termNominalInterestRateMinimumContainer");
        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumContainer);
        this.termNominalInterestRateMinimumField = new TextField<>("termNominalInterestRateMinimumField", new PropertyModel<>(this, "termNominalInterestRateMinimumValue"));
        this.termNominalInterestRateMinimumField.setRequired(false);
        this.termNominalInterestRateMinimumField.setLabel(Model.of("Nominal interest rate minimum"));
        this.termNominalInterestRateMinimumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMinimumContainer.add(this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumFeedback = new TextFeedbackPanel("termNominalInterestRateMinimumFeedback", this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumContainer.add(this.termNominalInterestRateMinimumFeedback);

        this.termNominalInterestRateDefaultBlock = new WebMarkupContainer("termNominalInterestRateDefaultBlock");
        this.termNominalInterestRateDefaultBlock.setOutputMarkupId(true);
        this.form.add(this.termNominalInterestRateDefaultBlock);
        this.termNominalInterestRateDefaultContainer = new WebMarkupContainer("termNominalInterestRateDefaultContainer");
        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultContainer);
        this.termNominalInterestRateDefaultField = new TextField<>("termNominalInterestRateDefaultField", new PropertyModel<>(this, "termNominalInterestRateDefaultValue"));
        this.termNominalInterestRateDefaultField.setLabel(Model.of("Nominal interest rate Default"));
        this.termNominalInterestRateDefaultField.setRequired(true);
        this.termNominalInterestRateDefaultField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateDefaultContainer.add(this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultFeedback = new TextFeedbackPanel("termNominalInterestRateDefaultFeedback", this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultContainer.add(this.termNominalInterestRateDefaultFeedback);

        this.termNominalInterestRateMaximumBlock = new WebMarkupContainer("termNominalInterestRateMaximumBlock");
        this.termNominalInterestRateMaximumBlock.setOutputMarkupId(true);
        this.form.add(this.termNominalInterestRateMaximumBlock);
        this.termNominalInterestRateMaximumContainer = new WebMarkupContainer("termNominalInterestRateMaximumContainer");
        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumContainer);
        this.termNominalInterestRateMaximumField = new TextField<>("termNominalInterestRateMaximumField", new PropertyModel<>(this, "termNominalInterestRateMaximumValue"));
        this.termNominalInterestRateMaximumField.setLabel(Model.of("Nominal interest rate Maximum"));
        this.termNominalInterestRateMaximumField.setRequired(false);
        this.termNominalInterestRateMaximumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMaximumContainer.add(this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumFeedback = new TextFeedbackPanel("termNominalInterestRateMaximumFeedback", this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumContainer.add(this.termNominalInterestRateMaximumFeedback);

        this.termNominalInterestRateTypeBlock = new WebMarkupContainer("termNominalInterestRateTypeBlock");
        this.termNominalInterestRateTypeBlock.setOutputMarkupId(true);
        this.form.add(this.termNominalInterestRateTypeBlock);
        this.termNominalInterestRateTypeContainer = new WebMarkupContainer("termNominalInterestRateTypeContainer");
        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeContainer);
        this.termNominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
        this.termNominalInterestRateTypeField = new Select2SingleChoice<>("termNominalInterestRateTypeField", 0, new PropertyModel<>(this, "termNominalInterestRateTypeValue"), this.termNominalInterestRateTypeProvider);
        this.termNominalInterestRateTypeField.setLabel(Model.of("Type"));
        this.termNominalInterestRateTypeField.setRequired(true);
        this.termNominalInterestRateTypeField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateTypeContainer.add(this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeFeedback = new TextFeedbackPanel("termNominalInterestRateTypeFeedback", this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeContainer.add(this.termNominalInterestRateTypeFeedback);

        this.termFloatingInterestRateBlock = new WebMarkupContainer("termFloatingInterestRateBlock");
        this.termFloatingInterestRateBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestRateBlock);
        this.termFloatingInterestRateContainer = new WebMarkupContainer("termFloatingInterestRateContainer");
        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateContainer);
        this.termFloatingInterestRateProvider = new SingleChoiceProvider("m_floating_rates", "id", "name");
        this.termFloatingInterestRateField = new Select2SingleChoice<>("termFloatingInterestRateField", 0, new PropertyModel<>(this, "termFloatingInterestRateValue"), this.termFloatingInterestRateProvider);
        this.termFloatingInterestRateField.setLabel(Model.of("Floating interest rate"));
        this.termFloatingInterestRateField.setRequired(false);
        this.termFloatingInterestRateField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestRateContainer.add(this.termFloatingInterestRateField);
        this.termFloatingInterestRateFeedback = new TextFeedbackPanel("termFloatingInterestRateFeedback", this.termFloatingInterestRateField);
        this.termFloatingInterestRateContainer.add(this.termFloatingInterestRateFeedback);

        this.termFloatingInterestDifferentialBlock = new WebMarkupContainer("termFloatingInterestDifferentialBlock");
        this.termFloatingInterestDifferentialBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestDifferentialBlock);
        this.termFloatingInterestDifferentialContainer = new WebMarkupContainer("termFloatingInterestDifferentialContainer");
        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialContainer);
        this.termFloatingInterestDifferentialField = new TextField<>("termFloatingInterestDifferentialField", new PropertyModel<>(this, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialField.setLabel(Model.of("Floating interest differential rate"));
        this.termFloatingInterestDifferentialField.setRequired(false);
        this.termFloatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDifferentialContainer.add(this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialFeedback = new TextFeedbackPanel("termFloatingInterestDifferentialFeedback", this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialContainer.add(this.termFloatingInterestDifferentialFeedback);

        this.termFloatingInterestAllowedBlock = new WebMarkupContainer("termFloatingInterestAllowedBlock");
        this.termFloatingInterestAllowedBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestAllowedBlock);
        this.termFloatingInterestAllowedContainer = new WebMarkupContainer("termFloatingInterestAllowedContainer");
        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedContainer);
        this.termFloatingInterestAllowedField = new CheckBox("termFloatingInterestAllowedField", new PropertyModel<>(this, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedField.setRequired(false);
        this.termFloatingInterestAllowedField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestAllowedContainer.add(this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedFeedback = new TextFeedbackPanel("termFloatingInterestAllowedFeedback", this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedContainer.add(this.termFloatingInterestAllowedFeedback);

        this.termFloatingInterestMinimumBlock = new WebMarkupContainer("termFloatingInterestMinimumBlock");
        this.termFloatingInterestMinimumBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestMinimumBlock);
        this.termFloatingInterestMinimumContainer = new WebMarkupContainer("termFloatingInterestMinimumContainer");
        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumContainer);
        this.termFloatingInterestMinimumField = new TextField<>("termFloatingInterestMinimumField", new PropertyModel<>(this, "termFloatingInterestMinimumValue"));
        this.termFloatingInterestMinimumField.setLabel(Model.of("Floating interest minimum"));
        this.termFloatingInterestMinimumField.setRequired(false);
        this.termFloatingInterestMinimumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMinimumContainer.add(this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumFeedback = new TextFeedbackPanel("termFloatingInterestMinimumFeedback", this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumContainer.add(this.termFloatingInterestMinimumFeedback);

        this.termFloatingInterestDefaultBlock = new WebMarkupContainer("termFloatingInterestDefaultBlock");
        this.termFloatingInterestDefaultBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestDefaultBlock);
        this.termFloatingInterestDefaultContainer = new WebMarkupContainer("termFloatingInterestDefaultContainer");
        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultContainer);
        this.termFloatingInterestDefaultField = new TextField<>("termFloatingInterestDefaultField", new PropertyModel<>(this, "termFloatingInterestDefaultValue"));
        this.termFloatingInterestDefaultField.setLabel(Model.of("Floating interest Default"));
        this.termFloatingInterestDefaultField.setRequired(false);
        this.termFloatingInterestDefaultField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDefaultContainer.add(this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultFeedback = new TextFeedbackPanel("termFloatingInterestDefaultFeedback", this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultContainer.add(this.termFloatingInterestDefaultFeedback);

        this.termFloatingInterestMaximumBlock = new WebMarkupContainer("termFloatingInterestMaximumBlock");
        this.termFloatingInterestMaximumBlock.setOutputMarkupId(true);
        this.form.add(this.termFloatingInterestMaximumBlock);
        this.termFloatingInterestMaximumContainer = new WebMarkupContainer("termFloatingInterestMaximumContainer");
        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumContainer);
        this.termFloatingInterestMaximumField = new TextField<>("termFloatingInterestMaximumField", new PropertyModel<>(this, "termFloatingInterestMaximumValue"));
        this.termFloatingInterestMaximumField.setLabel(Model.of("Floating interest Maximum"));
        this.termFloatingInterestMaximumField.setRequired(false);
        this.termFloatingInterestMaximumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMaximumContainer.add(this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumFeedback = new TextFeedbackPanel("termFloatingInterestMaximumFeedback", this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumContainer.add(this.termFloatingInterestMaximumFeedback);

        // Table
        {
            this.termNominalInterestRateByLoanCycleBlock = new WebMarkupContainer("termNominalInterestRateByLoanCycleBlock");
            this.termNominalInterestRateByLoanCycleBlock.setOutputMarkupId(true);
            this.form.add(this.termNominalInterestRateByLoanCycleBlock);
            this.termNominalInterestRateByLoanCycleContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleContainer");
            this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleContainer);

            this.termNominalInterestRateByLoanCyclePopup = new ModalWindow("termNominalInterestRateByLoanCyclePopup");
            add(this.termNominalInterestRateByLoanCyclePopup);
            this.termNominalInterestRateByLoanCyclePopup.setContent(new InterestLoanCyclePopup(this.termNominalInterestRateByLoanCyclePopup.getContentId(), this.termNominalInterestRateByLoanCyclePopup, this));
            this.termNominalInterestRateByLoanCyclePopup.setOnClose(this::termNominalInterestRateByLoanCyclePopupOnClose);

            List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleWhenColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleCycleColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleMinimumColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleDefaultColumn));
            termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleMaximumColumn));
            termNominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNominalInterestRateByLoanCycleActionItem, this::termNominalInterestRateByLoanCycleActionClick));
            this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue);
            this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
            this.termNominalInterestRateByLoanCycleContainer.add(this.termNominalInterestRateByLoanCycleTable);
            this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
            this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));

            this.termNominalInterestRateByLoanCycleAddLink = new AjaxLink<>("termNominalInterestRateByLoanCycleAddLink");
            this.termNominalInterestRateByLoanCycleAddLink.setOnClick(this::termNominalInterestRateByLoanCycleAddLinkClick);
            this.termNominalInterestRateByLoanCycleContainer.add(this.termNominalInterestRateByLoanCycleAddLink);
        }

        this.termRepaidEveryBlock = new WebMarkupContainer("termRepaidEveryBlock");
        this.termRepaidEveryBlock.setOutputMarkupId(true);
        this.form.add(this.termRepaidEveryBlock);
        this.termRepaidEveryContainer = new WebMarkupContainer("termRepaidEveryContainer");
        this.termRepaidEveryBlock.add(this.termRepaidEveryContainer);
        this.termRepaidEveryField = new TextField<>("termRepaidEveryField", new PropertyModel<>(this, "termRepaidEveryValue"));
        this.termRepaidEveryField.setLabel(Model.of("Repaid every"));
        this.termRepaidEveryField.setRequired(true);
        this.termRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.termRepaidEveryContainer.add(this.termRepaidEveryField);
        this.termRepaidEveryFeedback = new TextFeedbackPanel("termRepaidEveryFeedback", this.termRepaidEveryField);
        this.termRepaidEveryContainer.add(this.termRepaidEveryFeedback);

        this.termRepaidTypeBlock = new WebMarkupContainer("termRepaidTypeBlock");
        this.termRepaidTypeBlock.setOutputMarkupId(true);
        this.termRepaidTypeProvider = new RepaidTypeProvider();
        this.form.add(this.termRepaidTypeBlock);
        this.termRepaidTypeContainer = new WebMarkupContainer("termRepaidTypeContainer");
        this.termRepaidTypeBlock.add(this.termRepaidTypeContainer);
        this.termRepaidTypeField = new Select2SingleChoice<>("termRepaidTypeField", 0, new PropertyModel<>(this, "termRepaidTypeValue"), this.termRepaidTypeProvider);
        this.termRepaidTypeField.setLabel(Model.of("Repaid Type"));
        this.termRepaidTypeField.setRequired(true);
        this.termRepaidTypeField.add(new OnChangeAjaxBehavior());
        this.termRepaidTypeContainer.add(this.termRepaidTypeField);
        this.termRepaidTypeFeedback = new TextFeedbackPanel("termRepaidTypeFeedback", this.termRepaidTypeField);
        this.termRepaidTypeContainer.add(this.termRepaidTypeFeedback);

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.setOutputMarkupId(true);
        this.form.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setLabel(Model.of("Minimum days between disbursal and first repayment date"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setRequired(false);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel("termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback", this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);
    }

    protected boolean termNominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.termNominalInterestRateByLoanCyclePopup.show(target);
        return false;
    }

    protected ItemPanel termNominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel termNominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void termNominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.termNominalInterestRateByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termNominalInterestRateByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNominalInterestRateByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.termNominalInterestRateByLoanCycleTable);
    }

    protected List<ActionItem> termNominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean termNumberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.termNumberOfRepaymentByLoanCyclePopup.show(target);
        return false;
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void termNumberOfRepaymentByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.termNumberOfRepaymentByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termNumberOfRepaymentByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNumberOfRepaymentByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.termNumberOfRepaymentByLoanCycleTable);
    }

    protected List<ActionItem> termNumberOfRepaymentByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean termPrincipalByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemWhenValue = null;
        this.itemLoanCycleValue = null;
        this.itemMinimumValue = null;
        this.itemDefaultValue = null;
        this.itemMaximumValue = null;
        this.termPrincipalByLoanCyclePopup.show(target);
        return false;
    }

    protected void termNominalInterestRateByLoanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("valueConditionType", WhenType.valueOf(this.itemWhenValue.getId()));
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.termNominalInterestRateByLoanCycleValue.add(item);
        target.add(this.termNominalInterestRateByLoanCycleBlock);
    }

    protected ItemPanel termPrincipalByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel termPrincipalByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termPrincipalByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termPrincipalByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel termPrincipalByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected void termPrincipalByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.termPrincipalByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.termPrincipalByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termPrincipalByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.termPrincipalByLoanCycleTable);
    }

    protected List<ActionItem> termPrincipalByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean termLinkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        boolean notLinked = this.termLinkedToFloatingInterestRatesValue == null ? true : !this.termLinkedToFloatingInterestRatesValue;
        this.termNominalInterestRateMinimumContainer.setVisible(notLinked);
        this.termNominalInterestRateDefaultContainer.setVisible(notLinked);
        this.termNominalInterestRateMaximumContainer.setVisible(notLinked);
        this.termNominalInterestRateTypeContainer.setVisible(notLinked);

        this.termFloatingInterestRateContainer.setVisible(!notLinked);
        this.termFloatingInterestDifferentialContainer.setVisible(!notLinked);
        this.termFloatingInterestAllowedContainer.setVisible(!notLinked);
        this.termFloatingInterestMinimumContainer.setVisible(!notLinked);
        this.termFloatingInterestDefaultContainer.setVisible(!notLinked);
        this.termFloatingInterestMaximumContainer.setVisible(!notLinked);

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
        return false;
    }

    protected boolean settingInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
        this.settingCalculateInterestForExactDaysInPartialPeriodContainer.setVisible(this.settingInterestCalculationPeriodValue != null && InterestCalculationPeriod.valueOf(this.settingInterestCalculationPeriodValue.getId()) == InterestCalculationPeriod.SameAsPayment);
        if (target != null) {
            target.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        }
        return false;
    }

    protected boolean termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        this.termPrincipalByLoanCycleContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.termNumberOfRepaymentByLoanCycleContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.termNominalInterestRateByLoanCycleContainer.setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        if (target != null) {
            target.add(this.termPrincipalByLoanCycleBlock);
            target.add(this.termNumberOfRepaymentByLoanCycleBlock);
            target.add(this.termNominalInterestRateByLoanCycleBlock);
        }
        return false;
    }

    protected boolean settingVariableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingVariableInstallmentsAllowedValue != null && this.settingVariableInstallmentsAllowedValue;
        this.settingVariableInstallmentsMinimumContainer.setVisible(visible);
        this.settingVariableInstallmentsMaximumContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingVariableInstallmentsMinimumBlock);
            target.add(this.settingVariableInstallmentsMaximumBlock);
        }
        return false;
    }

    protected void termNumberOfRepaymentByLoanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("valueConditionType", WhenType.valueOf(this.itemWhenValue.getId()));
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.termNumberOfRepaymentByLoanCycleValue.add(item);
        target.add(this.termNumberOfRepaymentByLoanCycleBlock);
    }

    protected void termPrincipalByLoanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("valueConditionType", WhenType.valueOf(this.itemWhenValue.getId()));
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.termPrincipalByLoanCycleValue.add(item);
        target.add(this.termPrincipalByLoanCycleBlock);
    }

    protected void fundSourcePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("paymentId", this.itemPaymentValue.getId());
        item.put("payment", this.itemPaymentValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.fundSourceValue.add(item);
        target.add(this.fundSourceTable);
    }

    protected void feeIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.feeIncomeValue.add(item);
        target.add(this.feeIncomeTable);
    }

    protected void penaltyIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.penaltyIncomeValue.add(item);
        target.add(this.penaltyIncomeTable);
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
        this.overdueChargeValue.add(item);
        target.add(this.overdueChargeTable);
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

    private void saveButtonSubmit(Button button) {
        LoanBuilder builder = new LoanBuilder();

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
        builder.withMinPrincipal(this.termPrincipalMinimumValue);
        builder.withPrincipal(this.termPrincipalDefaultValue);
        builder.withMaxPrincipal(this.termPrincipalMaximumValue);
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
                builder.withInterestRateFrequencyType(NominalInterestRateScheduleType.valueOf(this.termNominalInterestRateTypeValue.getId()));
            }
        }

        if (useBorrowerCycle) {
            if (this.termPrincipalByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termPrincipalByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withPrincipalVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
            if (this.termNumberOfRepaymentByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termNumberOfRepaymentByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withNumberOfRepaymentVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
            if (this.termNominalInterestRateByLoanCycleValue != null) {
                for (Map<String, Object> item : this.termNominalInterestRateByLoanCycleValue) {
                    WhenType valueConditionType = (WhenType) item.get("valueConditionType");
                    Double borrowerCycleNumber = (Double) item.get("cycle");
                    Double minValue = (Double) item.get("minimum");
                    Double defaultValue = (Double) item.get("default");
                    Double maxValue = (Double) item.get("maximum");
                    builder.withInterestRateVariationsForBorrowerCycle(valueConditionType, borrowerCycleNumber, minValue, defaultValue, maxValue);
                }
            }
        }

        builder.withRepaymentEvery(this.termRepaidEveryValue);
        if (this.termRepaidTypeValue != null) {
            builder.withRepaymentFrequencyType(RepaidType.valueOf(this.termRepaidTypeValue.getId()));
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
        }
        builder.withGraceOnPrincipalPayment(this.settingMoratoriumPrincipalValue);
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

        builder.withPrincipalThresholdForLastInstallment(this.settingPrincipalThresholdForLastInstalmentValue);

        // Interest Recalculation

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
                                builder.withRecalculationCompoundingFrequencyNthDayType(FrequencyType.valueOf(this.interestRecalculationCompoundingTypeValue.getId()));
                            }
                            if (this.interestRecalculationCompoundingDayValue != null) {
                                builder.withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationCompoundingDayValue.getId()));
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
                        if (this.interestRecalculationRecalculateTypeValue != null) {
                            builder.withRecalculationRestFrequencyNthDayType(FrequencyType.valueOf(this.interestRecalculationRecalculateTypeValue.getId()));
                        }
                        if (this.interestRecalculationRecalculateDayValue != null) {
                            builder.withRecalculationRestFrequencyDayOfWeekType(FrequencyDay.valueOf(this.interestRecalculationRecalculateDayValue.getId()));
                        }
                        builder.withRecalculationRestFrequencyInterval(this.interestRecalculationRecalculateIntervalValue);
                    }
                }
                builder.withArrearsBasedOnOriginalSchedule(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue == null ? false : this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue);
            }

        }

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
            allowAttributeOverrideBuilder.withGraceOnPrincipalAndInterestPayment(this.configurableMoratoriumValue == null ? false : this.configurableMoratoriumValue);
            allowAttributeOverrideBuilder.withInterestType(this.configurableInterestMethodValue == null ? false : this.configurableInterestMethodValue);
            allowAttributeOverrideBuilder.withInterestCalculationPeriodType(this.configurableInterestCalculationPeriodValue == null ? false : this.configurableInterestCalculationPeriodValue);
            allowAttributeOverrideBuilder.withRepaymentEvery(this.configurableRepaidEveryValue == null ? false : this.configurableRepaidEveryValue);
            allowAttributeOverrideBuilder.withGraceOnArrearsAgeing(this.configurableOverdueBeforeMovingValue == null ? false : this.configurableOverdueBeforeMovingValue);
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
                    builder.withPaymentChannelToFundSourceMappings((String) item.get("paymentId"), (String) item.get("accountId"));
                }
            }
            if (this.feeIncomeValue != null && !this.feeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.feeIncomeValue) {
                    builder.withFeeToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
                }
            }
            if (this.penaltyIncomeValue != null && !this.penaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.penaltyIncomeValue) {
                    builder.withPenaltyToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
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
