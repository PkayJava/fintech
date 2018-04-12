package com.angkorteam.fintech.pages.product.loan;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AllowAttributeOverrideBuilder;
import com.angkorteam.fintech.dto.builder.ProductLoanBuilder;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.LockInType;
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
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.product.loan.Accounting;
import com.angkorteam.fintech.widget.product.loan.Charges;
import com.angkorteam.fintech.widget.product.loan.Currency;
import com.angkorteam.fintech.widget.product.loan.Details;
import com.angkorteam.fintech.widget.product.loan.Preview;
import com.angkorteam.fintech.widget.product.loan.Settings;
import com.angkorteam.fintech.widget.product.loan.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_CURRENCY = 1;
    public static int TAB_TERM = 2;
    public static int TAB_SETTING = 3;
    public static int TAB_CHARGE = 4;
    public static int TAB_ACCOUNTING = 5;
    public static int TAB_PREVIEW = 6;

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
            breadcrumb.setLabel("Loan Product Create");
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
    }

    @Override
    protected void initData() {
        this.errorDetail = true;
        this.errorCurrency = true;
        this.errorAccounting = true;
        this.errorTerm = true;
        this.errorSetting = true;
        this.errorCharge = true;
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.accountingValue = AccountingType.None.getDescription();
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
        this.termPrincipleByLoanCycleValue = Lists.newArrayList();
        this.termNumberOfRepaymentByLoanCycleValue = Lists.newArrayList();
        this.termNominalInterestRateByLoanCycleValue = Lists.newArrayList();

        this.settingEqualAmortizationValue = false;
        this.settingCalculateInterestForExactDaysInPartialPeriodValue = false;
        this.settingAllowFixingOfTheInstallmentAmountValue = false;
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue = false;
        this.settingVariableInstallmentsAllowedValue = false;
        this.settingAllowedToBeUsedForProvidingTopupLoansValue = false;

        this.chargeValue = Lists.newArrayList();
        this.overdueChargeValue = Lists.newArrayList();

        this.advancedAccountingRuleFundSourceValue = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = false;
        this.configurableAmortizationValue = false;
        this.configurableInterestMethodValue = false;
        this.configurableRepaymentStrategyValue = false;
        this.configurableInterestCalculationPeriodValue = false;
        this.configurableArrearsToleranceValue = false;
        this.configurableRepaidEveryValue = false;
        this.configurableMoratoriumValue = false;
        this.configurableOverdueBeforeMovingValue = false;

        this.loanTrancheDetailEnableMultipleDisbursalValue = false;

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = false;

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = false;
    }

    public void saveButtonSubmit(Button button) {
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

        if (this.settingEqualAmortizationValue != null) {
            builder.withEqualAmortization(this.settingEqualAmortizationValue);
        }

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
        } else if (AccountingType.Periodic.getDescription().equals(accounting) || AccountingType.Upfront.getDescription().equals(accounting)) {
            if (this.accrualFundSourceValue != null) {
                builder.withFundSourceAccountId(this.accrualFundSourceValue.getId());
            }
            if (this.accrualLoanPortfolioValue != null) {
                builder.withLoanPortfolioAccountId(this.accrualLoanPortfolioValue.getId());
            }
            if (this.accrualTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.accrualTransferInSuspenseValue.getId());
            }
            if (this.accrualIncomeFromInterestValue != null) {
                builder.withInterestOnLoanAccountId(this.accrualIncomeFromInterestValue.getId());
            }
            if (this.accrualIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.accrualIncomeFromFeeValue.getId());
            }
            if (this.accrualIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.accrualIncomeFromPenaltyValue.getId());
            }
            if (this.accrualIncomeFromRecoveryRepaymentValue != null) {
                builder.withIncomeFromRecoveryAccountId(this.accrualIncomeFromRecoveryRepaymentValue.getId());
            }
            if (this.accrualLossWrittenOffValue != null) {
                builder.withWriteOffAccountId(this.accrualLossWrittenOffValue.getId());
            }
            if (this.accrualOverPaymentLiabilityValue != null) {
                builder.withOverpaymentLiabilityAccountId(this.accrualOverPaymentLiabilityValue.getId());
            }
            if (this.accrualInterestReceivableValue != null) {
                builder.withReceivableInterestAccountId(this.accrualInterestReceivableValue.getId());
            }
            if (this.accrualFeeReceivableValue != null) {
                builder.withReceivableFeeAccountId(this.accrualFeeReceivableValue.getId());
            }
            if (this.accrualPenaltyReceivableValue != null) {
                builder.withReceivablePenaltyAccountId(this.accrualPenaltyReceivableValue.getId());
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
