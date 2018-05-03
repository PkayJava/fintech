package com.angkorteam.fintech.pages.product.loan;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccProductMapping;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MFloatingRates;
import com.angkorteam.fintech.ddl.MFund;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.ddl.MProductLoan;
import com.angkorteam.fintech.ddl.MProductLoanCharge;
import com.angkorteam.fintech.ddl.MProductLoanConfigurableAttributes;
import com.angkorteam.fintech.ddl.MProductLoanFloatingRates;
import com.angkorteam.fintech.ddl.MProductLoanGuaranteeDetails;
import com.angkorteam.fintech.ddl.MProductLoanRecalculationDetails;
import com.angkorteam.fintech.ddl.MProductLoanVariableInstallmentConfig;
import com.angkorteam.fintech.ddl.MProductLoanVariationsBorrowerCycle;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.FinancialAccountType;
import com.angkorteam.fintech.dto.enums.LoanCycle;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.ProductType;
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
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.widget.product.loan.Accounting;
import com.angkorteam.fintech.widget.product.loan.Charges;
import com.angkorteam.fintech.widget.product.loan.Currency;
import com.angkorteam.fintech.widget.product.loan.Details;
import com.angkorteam.fintech.widget.product.loan.Preview;
import com.angkorteam.fintech.widget.product.loan.Settings;
import com.angkorteam.fintech.widget.product.loan.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanPreviewPage extends Page {

    public static int TAB_DETAIL = LoanCreatePage.TAB_DETAIL;
    public static int TAB_CURRENCY = LoanCreatePage.TAB_CURRENCY;
    public static int TAB_TERM = LoanCreatePage.TAB_TERM;
    public static int TAB_SETTING = LoanCreatePage.TAB_SETTING;
    public static int TAB_CHARGE = LoanCreatePage.TAB_CHARGE;
    public static int TAB_ACCOUNTING = LoanCreatePage.TAB_ACCOUNTING;
    public static int TAB_PREVIEW = LoanCreatePage.TAB_PREVIEW;

    protected String loanId;

    // Detail

    protected String detailProductNameValue;
    protected String detailShortNameValue;
    protected String detailDescriptionValue;
    protected Option detailFundValue;
    protected Date detailStartDateValue;
    protected Date detailCloseDateValue;
    protected Boolean detailIncludeInCustomerLoanCounterValue;

    // Currency

    protected Option currencyCodeValue;
    protected Long currencyDecimalPlaceValue;
    protected Long currencyInMultipleOfValue;
    protected Long currencyInstallmentInMultipleOfValue;

    // Terms

    protected Boolean termVaryBasedOnLoanCycleValue;
    protected Double termPrincipleMinimumValue;
    protected Double termPrincipleDefaultValue;
    protected Double termPrincipleMaximumValue;
    protected List<Map<String, Object>> termPrincipleByLoanCycleValue;
    protected Long termNumberOfRepaymentMinimumValue;
    protected Long termNumberOfRepaymentDefaultValue;
    protected Long termNumberOfRepaymentMaximumValue;
    protected List<Map<String, Object>> termNumberOfRepaymentByLoanCycleValue;
    protected Boolean termLinkedToFloatingInterestRatesValue;
    protected Double termNominalInterestRateMinimumValue;
    protected Double termNominalInterestRateDefaultValue;
    protected Double termNominalInterestRateMaximumValue;
    protected Option termNominalInterestRateTypeValue;
    protected Option termFloatingInterestRateValue;
    protected Double termFloatingInterestDifferentialValue;
    protected Boolean termFloatingInterestAllowedValue;
    protected Double termFloatingInterestMinimumValue;
    protected Double termFloatingInterestDefaultValue;
    protected Double termFloatingInterestMaximumValue;
    protected List<Map<String, Object>> termNominalInterestRateByLoanCycleValue;
    protected Long termRepaidEveryValue;
    protected Option termRepaidTypeValue;
    protected Long termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue;

    // Settings

    protected Option settingAmortizationValue;
    protected Option settingInterestMethodValue;
    protected Option settingInterestCalculationPeriodValue;
    protected Boolean settingEqualAmortizationValue;
    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    protected Option settingRepaymentStrategyValue;
    protected Long settingMoratoriumPrincipleValue;
    protected Long settingMoratoriumInterestValue;
    protected Long settingInterestFreePeriodValue;
    protected Double settingArrearsToleranceValue;
    protected Option settingDayInYearValue;
    protected Option settingDayInMonthValue;
    protected Boolean settingAllowFixingOfTheInstallmentAmountValue;
    protected Long settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue;
    protected Long settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue;
    protected Boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue;
    protected Double settingPrincipleThresholdForLastInstalmentValue;
    protected Boolean settingVariableInstallmentsAllowedValue;
    protected Long settingVariableInstallmentsMinimumValue;
    protected Long settingVariableInstallmentsMaximumValue;
    protected Boolean settingAllowedToBeUsedForProvidingTopupLoansValue;

    // Interest Recalculation

    protected Boolean interestRecalculationRecalculateInterestValue;
    protected Option interestRecalculationPreClosureInterestCalculationRuleValue;
    protected Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
    protected Option interestRecalculationCompoundingOnValue;
    protected Option interestRecalculationCompoundingValue;
    protected Option interestRecalculationCompoundingTypeValue;
    protected Option interestRecalculationCompoundingDayValue;
    protected Option interestRecalculationCompoundingOnDayValue;
    protected Long interestRecalculationCompoundingIntervalValue;
    protected Option interestRecalculationRecalculateValue;
    protected Option interestRecalculationRecalculateTypeValue;
    protected Option interestRecalculationRecalculateDayValue;
    protected Option interestRecalculationRecalculateOnDayValue;
    protected Long interestRecalculationRecalculateIntervalValue;
    protected Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;

    // Guarantee Requirements

    protected Boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;
    protected Double guaranteeRequirementMandatoryGuaranteeValue;
    protected Double guaranteeRequirementMinimumGuaranteeValue;
    protected Double guaranteeRequirementMinimumGuaranteeFromGuarantorValue;

    // Loan Tranche Details

    protected Boolean loanTrancheDetailEnableMultipleDisbursalValue;
    protected Long loanTrancheDetailMaximumTrancheCountValue;
    protected Double loanTrancheDetailMaximumAllowedOutstandingBalanceValue;

    // Configurable Terms and Settings

    protected Boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue;
    protected Boolean configurableAmortizationValue;
    protected Boolean configurableInterestMethodValue;
    protected Boolean configurableRepaymentStrategyValue;
    protected Boolean configurableInterestCalculationPeriodValue;
    protected Boolean configurableArrearsToleranceValue;
    protected Boolean configurableRepaidEveryValue;
    protected Boolean configurableMoratoriumValue;
    protected Boolean configurableOverdueBeforeMovingValue;

    // Charges

    protected List<Map<String, Object>> chargeValue;

    // Overdue Charges

    protected List<Map<String, Object>> overdueChargeValue;

    // Accounting

    protected String accountingValue;
    protected Option cashFundSourceValue;
    protected Option cashLoanPortfolioValue;
    protected Option cashTransferInSuspenseValue;
    protected Option cashIncomeFromInterestValue;
    protected Option cashIncomeFromFeeValue;
    protected Option cashIncomeFromPenaltyValue;
    protected Option cashIncomeFromRecoveryRepaymentValue;
    protected Option cashLossWrittenOffValue;
    protected Option cashOverPaymentLiabilityValue;
    protected Option accrualFundSourceValue;
    protected Option accrualLoanPortfolioValue;
    protected Option accrualInterestReceivableValue;
    protected Option accrualFeeReceivableValue;
    protected Option accrualPenaltyReceivableValue;
    protected Option accrualTransferInSuspenseValue;
    protected Option accrualIncomeFromInterestValue;
    protected Option accrualIncomeFromFeeValue;
    protected Option accrualIncomeFromPenaltyValue;
    protected Option accrualIncomeFromRecoveryRepaymentValue;
    protected Option accrualLossWrittenOffValue;
    protected Option accrualOverPaymentLiabilityValue;

    // Advanced Accounting Rule

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail;
    protected boolean errorCurrency;
    protected boolean errorAccounting;
    protected boolean errorTerm;
    protected boolean errorSetting;
    protected boolean errorCharge;

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
            breadcrumb.setLabel(this.detailShortNameValue);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new Charges(this), new Accounting(this), new Preview(this)));
        add(this.tab);

    }

    @Override
    protected void configureMetaData() {
        this.tab.setSelectedTab(TAB_PREVIEW);
    }

    @Override
    protected void initData() {
        this.loanId = getPageParameters().get("loanId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MProductLoan.NAME);
        // selectQuery.addJoin("LEFT JOIN " + MFund.NAME + " ON " + MProductLoan.NAME +
        // "." + MProductLoan.Field.FUND_ID + " = " + MFund.NAME + "." +
        // MFund.Field.ID);
        // selectQuery.addJoin("LEFT JOIN " + MOrganisationCurrency.NAME + " ON " +
        // MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_CODE + " = " +
        // MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanFloatingRates.NAME + " ON " + MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.LOAN_PRODUCT_ID + " = " + MProductLoan.NAME + "." + MProductLoan.Field.ID);
        // selectQuery.addJoin("LEFT JOIN " + MFloatingRates.NAME + " ON " +
        // MProductLoanFloatingRates.NAME + "." +
        // MProductLoanFloatingRates.Field.FLOATING_RATES_ID + " = " +
        // MFloatingRates.NAME + "." + MFloatingRates.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanVariableInstallmentConfig.NAME + " ON " + MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.LOAN_PRODUCT_ID + " = " + MProductLoan.NAME + "." + MProductLoan.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanRecalculationDetails.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.PRODUCT_ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanGuaranteeDetails.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.LOAN_PRODUCT_ID);

        selectQuery.addWhere(MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + this.loanId);

        // detail section
        selectQuery.addField(MProductLoan.NAME + ".name as product");
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DESCRIPTION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.SHORT_NAME);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.START_DATE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CLOSE_DATE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INCLUDE_IN_BORROWER_CYCLE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.FUND_ID);

        // currency
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_CODE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_DIGITS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INSTALMENT_AMOUNT_IN_MULTIPLES_OF);

        // Terms
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.USE_BORROWER_CYCLE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_PRINCIPAL_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.PRINCIPAL_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_PRINCIPAL_AMOUNT);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.REPAY_EVERY);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.REPAYMENT_PERIOD_FREQUENCY_ENUM);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_PERIOD_FREQUENCY_ENUM);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.IS_LINKED_TO_FLOATING_INTEREST_RATES);

        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.FLOATING_RATES_ID);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.INTEREST_RATE_DIFFERENTIAL);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.IS_FLOATING_INTEREST_RATE_CALCULATION_ALLOWED);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.MIN_DIFFERENTIAL_LENDING_RATE);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.DEFAULT_DIFFERENTIAL_LENDING_RATE);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.MAX_DIFFERENTIAL_LENDING_RATE);

        selectQuery.addField(MProductLoan.NAME + ".min_days_between_disbursal_and_first_repayment");

        // setting
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.AMORTIZATION_METHOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_METHOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_CALCULATED_IN_PERIOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ARREARS_TOLERANCE_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.LOAN_TRANSACTION_STRATEGY_ID);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_INTEREST_FREE_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_ARREARS_AGEING);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_INTEREST_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_PRINCIPAL_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ACCOUNT_MOVES_OUT_OF_NPA_ONLY_ON_ARREARS_COMPLETION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.OVERDUE_DAYS_FOR_NPA);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DAYS_IN_YEAR_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DAYS_IN_MONTH_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.PRINCIPAL_THRESHOLD_FOR_LAST_INSTALLMENT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CAN_DEFINE_FIXED_EMI_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CAN_USE_FOR_TOPUP);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_VARIABE_INSTALLMENTS);
        selectQuery.addField(MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.MINIMUM_GAP);
        selectQuery.addField(MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.MAXIMUM_GAP);

        // re-calculation
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_RECALCULATION_ENABLED);

        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.RESCHEDULE_STRATEGY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_INTERVAL);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.ARREARS_BASED_ON_ORIGINAL_SCHEDULE);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.PRE_CLOSE_INTEREST_CALCULATION_STRATEGY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_INTERVAL);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_NTH_DAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_ON_DAY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_WEEKDAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_NTH_DAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_ON_DAY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUND_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_WEEKDAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.IS_COMPOUNDING_TO_BE_POSTED_AS_TRANSACTION);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.ALLOW_COMPOUNDING_ON_EOD);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.HOLD_GUARANTEE_FUNDS);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MANDATORY_GUARANTEE);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MINIMUM_GUARANTEE_FROM_GUARANTOR_FUNDS);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MINIMUM_GUARANTEE_FROM_OWN_FUNDS);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_MULTIPLE_DISBURSALS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_DISBURSALS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_OUTSTANDING_LOAN_BALANCE);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ACCOUNTING_TYPE);

        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.detailProductNameValue = (String) loanObject.get("product");
        this.detailDescriptionValue = (String) loanObject.get("description");
        this.detailShortNameValue = (String) loanObject.get("short_name");

        selectQuery = new SelectQuery(MFund.NAME);
        selectQuery.addField(MFund.Field.ID);
        selectQuery.addField(MFund.Field.NAME + " as text");
        selectQuery.addWhere(MFund.Field.ID + " = :" + MFund.Field.ID, loanObject.get(MProductLoan.Field.FUND_ID));
        this.detailFundValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        this.detailStartDateValue = (Date) loanObject.get("start_date");
        this.detailCloseDateValue = (Date) loanObject.get("close_date");
        this.detailIncludeInCustomerLoanCounterValue = (Boolean) loanObject.get("include_in_borrower_cycle");

        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, loanObject.get(MProductLoan.Field.CURRENCY_CODE));
        selectQuery.addField(MOrganisationCurrency.Field.NAME + " as text");
        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
        this.currencyCodeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.currencyDecimalPlaceValue = (Long) loanObject.get("currency_digits");
        this.currencyInMultipleOfValue = (Long) loanObject.get("currency_multiplesof");
        Double instalment_amount_in_multiples_of = (Double) loanObject.get("instalment_amount_in_multiples_of");
        this.currencyInstallmentInMultipleOfValue = instalment_amount_in_multiples_of == null ? null : instalment_amount_in_multiples_of.longValue();

        this.termVaryBasedOnLoanCycleValue = (Boolean) loanObject.get("use_borrower_cycle");

        this.termPrincipleMinimumValue = (Double) loanObject.get("min_principal_amount");
        this.termPrincipleDefaultValue = (Double) loanObject.get("principle_amount");
        this.termPrincipleMaximumValue = (Double) loanObject.get("max_principal_amount");

        this.termNumberOfRepaymentMinimumValue = (Long) loanObject.get("min_number_of_repayments");
        this.termNumberOfRepaymentDefaultValue = (Long) loanObject.get("number_of_repayments");
        this.termNumberOfRepaymentMaximumValue = (Long) loanObject.get("max_number_of_repayments");

        this.termRepaidEveryValue = (Long) loanObject.get("repay_every");
        this.termRepaidTypeValue = LockInType.optionLiteral(String.valueOf(loanObject.get("repayment_period_frequency_enum")));

        this.termLinkedToFloatingInterestRatesValue = (Boolean) loanObject.get("is_linked_to_floating_interest_rates");

        if (this.termLinkedToFloatingInterestRatesValue != null && this.termLinkedToFloatingInterestRatesValue) {
            selectQuery = new SelectQuery(MFloatingRates.NAME);
            selectQuery.addField(MFloatingRates.Field.ID);
            selectQuery.addField(MFloatingRates.Field.NAME + " as text");
            selectQuery.addWhere(MFloatingRates.Field.ID + " = :" + MFloatingRates.Field.ID, loanObject.get(MProductLoanFloatingRates.Field.FLOATING_RATES_ID));
            this.termFloatingInterestRateValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
            this.termFloatingInterestDifferentialValue = (Double) loanObject.get("interest_rate_differential");
            this.termFloatingInterestAllowedValue = (Boolean) loanObject.get("is_floating_interest_rate_calculation_allowed");
            this.termFloatingInterestMinimumValue = (Double) loanObject.get("min_differential_lending_rate");
            this.termFloatingInterestDefaultValue = (Double) loanObject.get("default_differential_lending_rate");
            this.termFloatingInterestMaximumValue = (Double) loanObject.get("max_differential_lending_rate");
        } else {
            this.termNominalInterestRateMinimumValue = (Double) loanObject.get("min_nominal_interest_rate_per_period");
            this.termNominalInterestRateDefaultValue = (Double) loanObject.get("annual_nominal_interest_rate");
            this.termNominalInterestRateDefaultValue = (Double) loanObject.get("nominal_interest_rate_per_period");
            this.termNominalInterestRateMaximumValue = (Double) loanObject.get("max_nominal_interest_rate_per_period");
            this.termNominalInterestRateTypeValue = NominalInterestRateType.optionLiteral(String.valueOf(loanObject.get("interest_period_frequency_enum")));
        }

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue = (Long) loanObject.get("min_days_between_disbursal_and_first_repayment");

        selectQuery = new SelectQuery(MProductLoanVariationsBorrowerCycle.NAME);
        selectQuery.addWhere(MProductLoanVariationsBorrowerCycle.Field.LOAN_PRODUCT_ID + " = " + this.loanId);
        selectQuery.addOrderBy(MProductLoanVariationsBorrowerCycle.Field.PARAM_TYPE + " ASC");
        selectQuery.addOrderBy(MProductLoanVariationsBorrowerCycle.Field.MIN_VALUE + " ASC");

        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.BORROWER_CYCLE_NUMBER);
        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.VALUE_CONDITION);
        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.PARAM_TYPE);
        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.DEFAULT_VALUE);
        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.MAX_VALUE);
        selectQuery.addField(MProductLoanVariationsBorrowerCycle.NAME + "." + MProductLoanVariationsBorrowerCycle.Field.MIN_VALUE);
        List<Map<String, Object>> borrowerObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> borrowerObject : borrowerObjects) {
            Map<String, Object> item = new HashMap<>();
            Option when = WhenType.optionLiteral(String.valueOf(borrowerObject.get("value_condition")));
            item.put("when", when);
            item.put("cycle", borrowerObject.get("borrower_cycle_number"));
            item.put("default", borrowerObject.get("default_value"));
            item.put("maximum", borrowerObject.get("max_value"));
            item.put("minimum", borrowerObject.get("min_value"));
            String param_type = String.valueOf(borrowerObject.get("param_type"));
            if (LoanCycle.Principle.getLiteral().equals(param_type)) {
                this.termPrincipleByLoanCycleValue.add(item);
            } else if (LoanCycle.NumberOfRepayment.getLiteral().equals(param_type)) {
                this.termNumberOfRepaymentByLoanCycleValue.add(item);
            } else if (LoanCycle.NominalInterestRate.getLiteral().equals(param_type)) {
                this.termNominalInterestRateByLoanCycleValue.add(item);
            }
        }

        this.settingAmortizationValue = Amortization.optionLiteral(String.valueOf(loanObject.get("amortization_method_enum")));
        this.settingInterestMethodValue = InterestMethod.optionLiteral(String.valueOf(loanObject.get("interest_method_enum")));
        this.settingInterestCalculationPeriodValue = InterestCalculationPeriod.optionLiteral(String.valueOf(loanObject.get("interest_calculated_in_period_enum")));
        this.settingCalculateInterestForExactDaysInPartialPeriodValue = (Boolean) loanObject.get("allow_partial_period_interest_calcualtion");
        this.settingRepaymentStrategyValue = RepaymentStrategy.optionLiteral(String.valueOf(loanObject.get("loan_transaction_strategy_id")));

        this.settingMoratoriumPrincipleValue = (Long) loanObject.get("grace_on_principal_periods");
        this.settingMoratoriumInterestValue = (Long) loanObject.get("grace_on_interest_periods");
        this.settingInterestFreePeriodValue = (Long) loanObject.get("grace_interest_free_periods");
        this.settingArrearsToleranceValue = (Double) loanObject.get("arrearstolerance_amount");

        this.settingDayInYearValue = DayInYear.optionLiteral(String.valueOf(loanObject.get("days_in_year_enum")));
        this.settingDayInMonthValue = DayInMonth.optionLiteral(String.valueOf(loanObject.get("days_in_month_enum")));

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue = (Long) loanObject.get("grace_on_arrears_ageing");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue = (Long) loanObject.get("overdue_days_for_npa");
        this.settingPrincipleThresholdForLastInstalmentValue = (Double) loanObject.get("principle_threshold_for_last_installment");

        this.settingAllowFixingOfTheInstallmentAmountValue = (Boolean) loanObject.get("can_define_fixed_emi_amount");

        this.settingVariableInstallmentsAllowedValue = (Boolean) loanObject.get("allow_variabe_installments");
        if (this.settingVariableInstallmentsAllowedValue != null && this.settingVariableInstallmentsAllowedValue) {
            this.settingVariableInstallmentsMinimumValue = (Long) loanObject.get("minimum_gap");
            this.settingVariableInstallmentsMaximumValue = (Long) loanObject.get("maximum_gap");

        }
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue = (Boolean) loanObject.get("account_moves_out_of_npa_only_on_arrears_completion");
        this.settingAllowedToBeUsedForProvidingTopupLoansValue = (Boolean) loanObject.get("can_use_for_topup");

        Long interest_recalculation_enabled = (Long) loanObject.get("interest_recalculation_enabled");
        this.interestRecalculationRecalculateInterestValue = interest_recalculation_enabled == null ? null : interest_recalculation_enabled == 1;

        if (this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue) {
            this.interestRecalculationPreClosureInterestCalculationRuleValue = ClosureInterestCalculationRule.optionLiteral(String.valueOf(loanObject.get("pre_close_interest_calculation_strategy")));
            this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = (Boolean) loanObject.get("arrears_based_on_original_schedule");
            this.interestRecalculationAdvancePaymentsAdjustmentTypeValue = AdvancePaymentsAdjustmentType.optionLiteral(String.valueOf(loanObject.get("reschedule_strategy_enum")));
            this.interestRecalculationCompoundingOnValue = InterestRecalculationCompound.optionLiteral(String.valueOf(loanObject.get("compound_type_enum")));

            FrequencyType compounding_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("compounding_frequency_nth_day_enum")));
            if (compounding_frequency_nth_day_enum != null) {
                this.interestRecalculationCompoundingTypeValue = compounding_frequency_nth_day_enum.toOption();
                if (compounding_frequency_nth_day_enum == FrequencyType.OnDay) {
                    Long interestRecalculationCompoundingOnDayValue = (Long) loanObject.get("compounding_frequency_on_day");
                    if (interestRecalculationCompoundingOnDayValue != null) {
                        this.interestRecalculationCompoundingOnDayValue = new Option(String.valueOf(interestRecalculationCompoundingOnDayValue), String.valueOf(interestRecalculationCompoundingOnDayValue));
                    }
                } else {
                    this.interestRecalculationCompoundingDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_weekday_enum")));
                }
            }
            this.interestRecalculationCompoundingValue = Frequency.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_type_enum")));
            this.interestRecalculationCompoundingIntervalValue = (Long) loanObject.get("compounding_frequency_interval");

            FrequencyType rest_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("rest_frequency_nth_day_enum")));
            if (rest_frequency_nth_day_enum != null) {
                this.interestRecalculationRecalculateTypeValue = rest_frequency_nth_day_enum.toOption();
                if (rest_frequency_nth_day_enum == FrequencyType.OnDay) {
                    Long interestRecalculationRecalculateOnDayValue = (Long) loanObject.get("rest_frequency_on_day");
                    if (interestRecalculationRecalculateOnDayValue != null) {
                        this.interestRecalculationRecalculateOnDayValue = new Option(String.valueOf(interestRecalculationRecalculateOnDayValue), String.valueOf(interestRecalculationRecalculateOnDayValue));
                    }
                } else {
                    this.interestRecalculationRecalculateDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("rest_frequency_weekday_enum")));
                }
            }
            this.interestRecalculationRecalculateValue = Frequency.optionLiteral(String.valueOf(loanObject.get("rest_frequency_type_enum")));
            this.interestRecalculationRecalculateIntervalValue = (Long) loanObject.get("rest_frequency_interval");

            // query.addField("m_product_loan_recalculation_details.is_compounding_to_be_posted_as_transaction");
            // query.addField("m_product_loan_recalculation_details.allow_compounding_on_eod");
        }

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = (Boolean) loanObject.get("hold_guarantee_funds");
        this.guaranteeRequirementMandatoryGuaranteeValue = (Double) loanObject.get("mandatory_guarantee");
        this.guaranteeRequirementMinimumGuaranteeValue = (Double) loanObject.get("minimum_guarantee_from_own_funds");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue = (Double) loanObject.get("minimum_guarantee_from_guarantor_funds");

        selectQuery = new SelectQuery(MProductLoanConfigurableAttributes.NAME);
        selectQuery.addWhere(MProductLoanConfigurableAttributes.Field.LOAN_PRODUCT_ID + " = " + this.loanId);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.AMORTIZATION_METHOD_ENUM);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.INTEREST_METHOD_ENUM);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.LOAN_TRANSACTION_STRATEGY_ID);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.INTEREST_CALCULATED_IN_PERIOD_ENUM);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.ARREARS_TOLERANCE_AMOUNT);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.REPAY_EVERY);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.MORATORIUM);
        selectQuery.addField(MProductLoanConfigurableAttributes.Field.GRACE_ON_ARREARS_AGEING);
        Map<String, Object> configurablesObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = configurablesObject != null;
        if (this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue) {
            Long amortization_method_enum = (Long) configurablesObject.get("amortization_method_enum");
            this.configurableAmortizationValue = amortization_method_enum != null && amortization_method_enum == 1;
            Long interest_method_enum = (Long) configurablesObject.get("interest_method_enum");
            this.configurableInterestMethodValue = interest_method_enum != null && interest_method_enum == 1;
            Long loan_transaction_strategy_id = (Long) configurablesObject.get("loan_transaction_strategy_id");
            this.configurableRepaymentStrategyValue = loan_transaction_strategy_id != null && loan_transaction_strategy_id == 1;
            Long interest_calculated_in_period_enum = (Long) configurablesObject.get("interest_calculated_in_period_enum");
            this.configurableInterestCalculationPeriodValue = interest_calculated_in_period_enum != null && interest_calculated_in_period_enum == 1;
            Long grace_on_arrears_ageing = (Long) configurablesObject.get("grace_on_arrears_ageing");
            this.configurableArrearsToleranceValue = grace_on_arrears_ageing != null && grace_on_arrears_ageing == 1;
            Long repay_every = (Long) configurablesObject.get("repay_every");
            this.configurableRepaidEveryValue = repay_every != null && repay_every == 1;
            Long moratorium = (Long) configurablesObject.get("moratorium");
            this.configurableMoratoriumValue = moratorium != null && moratorium == 1;
            Long arrearstolerance_amount = (Long) configurablesObject.get("arrearstolerance_amount");
            this.configurableOverdueBeforeMovingValue = arrearstolerance_amount != null && arrearstolerance_amount == 1;
        }

        SelectQuery chargeQuery = new SelectQuery(MCharge.NAME);
        chargeQuery.addJoin("INNER JOIN " + MProductLoanCharge.NAME + " ON " + MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        chargeQuery.addField("CONCAT(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') AS " + MCharge.Field.NAME);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_PAYMENT_MODE_ENUM);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_PENALTY);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_ACTIVE);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.MIN_CAP);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.MAX_CAP);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_FREQUENCY);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.INCOME_OR_LIABILITY_ACCOUNT_ID);
        chargeQuery.addField(MCharge.NAME + "." + MCharge.Field.TAX_GROUP_ID);
        chargeQuery.addWhere(MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.PRODUCT_LOAN_ID + " = '" + this.loanId + "'");
        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> chargeObject : chargeObjects) {
            Boolean is_penalty = (Boolean) chargeObject.get("is_penalty");
            Map<String, Object> charge = new HashMap<>();
            charge.put("name", chargeObject.get("name"));
            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
            charge.put("type", type);
            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
            charge.put("collect", collect);
            charge.put("amount", chargeObject.get("amount"));
            if (is_penalty != null && is_penalty) {
                this.overdueChargeValue.add(charge);
            } else {
                this.chargeValue.add(charge);
            }
        }

        this.loanTrancheDetailEnableMultipleDisbursalValue = (Boolean) loanObject.get("allow_multiple_disbursals");
        this.loanTrancheDetailMaximumTrancheCountValue = (Long) loanObject.get("max_disbursals");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceValue = (Double) loanObject.get("max_outstanding_loan_balance");

        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(loanObject.get("accounting_type")));

        if (accountingType != null) {
            this.accountingValue = accountingType.getDescription();

            selectQuery = new SelectQuery(AccProductMapping.NAME);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.loanId);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Loan.getLiteral());
            selectQuery.addField(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE);
            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
            List<Map<String, Object>> mappings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

            for (Map<String, Object> mapping : mappings) {
                FinancialAccountType financialAccountType = FinancialAccountType.parseLiteral(String.valueOf(mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE)));
                if (financialAccountType == FinancialAccountType.SavingReference1 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) != null && mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MPaymentType.NAME);
                    selectQuery.addWhere(MPaymentType.Field.ID + " = :" + MPaymentType.Field.ID, mapping.get(AccProductMapping.Field.PAYMENT_TYPE));
                    selectQuery.addField(MPaymentType.Field.ID);
                    selectQuery.addField(MPaymentType.Field.VALUE + " as text");
                    item.put("payment", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFundSourceValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomeFee4 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.CHARGE_ID) != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFeeIncomeValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomePenalty5 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.CHARGE_ID) != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRulePenaltyIncomeValue.add(item);
                }

                if (financialAccountType != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null && mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null) {
                    if (financialAccountType == FinancialAccountType.SavingReference1) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashFundSourceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualFundSourceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.SavingControl2) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashLoanPortfolioValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualLoanPortfolioValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.InterestReceivable7) {
                        if (accountingType == AccountingType.Cash) {

                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualInterestReceivableValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }

                    } else if (financialAccountType == FinancialAccountType.FeeReceivable8) {
                        if (accountingType == AccountingType.Cash) {

                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualFeeReceivableValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.PenaltyReceivable9) {
                        if (accountingType == AccountingType.Cash) {

                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualPenaltyReceivableValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.TransferInSuspense10) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashTransferInSuspenseValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualTransferInSuspenseValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashIncomeFromInterestValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualIncomeFromInterestValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.IncomeFee4) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.IncomePenalty5) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashIncomeFromPenaltyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualIncomeFromPenaltyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.OverdraftInterestIncome12) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashIncomeFromRecoveryRepaymentValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualIncomeFromRecoveryRepaymentValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.WriteOff6) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashLossWrittenOffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualLossWrittenOffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    } else if (financialAccountType == FinancialAccountType.OverdraftPortfolio11) {
                        if (accountingType == AccountingType.Cash) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.cashOverPaymentLiabilityValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        } else if (accountingType == AccountingType.Periodic || accountingType == AccountingType.Upfront) {
                            selectQuery = new SelectQuery(AccGLAccount.NAME);
                            selectQuery.addField(AccGLAccount.Field.ID);
                            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                            this.accrualOverPaymentLiabilityValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                        }
                    }
                }
            }
        }
    }

    protected void saveButtonSubmit(Button button) {

        setResponsePage(LoanBrowsePage.class);
    }

}
