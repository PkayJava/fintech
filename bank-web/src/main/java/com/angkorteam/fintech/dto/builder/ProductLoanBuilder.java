package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.angkorteam.fintech.client.enums.AccountingType;
import com.angkorteam.fintech.client.enums.DayInYear;
import com.angkorteam.fintech.client.enums.LockInType;
import com.angkorteam.fintech.client.enums.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.client.enums.loan.Amortization;
import com.angkorteam.fintech.client.enums.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.client.enums.loan.DayInMonth;
import com.angkorteam.fintech.client.enums.loan.Frequency;
import com.angkorteam.fintech.client.enums.loan.FrequencyDay;
import com.angkorteam.fintech.client.enums.loan.FrequencyType;
import com.angkorteam.fintech.client.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.client.enums.loan.InterestMethod;
import com.angkorteam.fintech.client.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.client.enums.loan.NominalInterestRateType;
import com.angkorteam.fintech.client.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.client.enums.loan.WhenType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.openunirest.http.JsonNode;

public class ProductLoanBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public ProductLoanBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public ProductLoanBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public ProductLoanBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Long digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public ProductLoanBuilder withDigitsAfterDecimal(Long digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private Long repaymentEvery;
    private boolean hasRepaymentEvery;

    public ProductLoanBuilder withRepaymentEvery(Long repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
        this.hasRepaymentEvery = true;
        return this;
    }

    private DayInYear daysInYearType;
    private boolean hasDaysInYearType;

    public ProductLoanBuilder withDaysInYearType(DayInYear daysInYearType) {
        this.daysInYearType = daysInYearType;
        this.hasDaysInYearType = true;
        return this;
    }

    private DayInMonth daysInMonthType;
    private boolean hasDaysInMonthType;

    public ProductLoanBuilder withDaysInMonthType(DayInMonth daysInMonthType) {
        this.daysInMonthType = daysInMonthType;
        this.hasDaysInMonthType = true;
        return this;
    }

    private Boolean interestRecalculationEnabled;
    private boolean hasInterestRecalculationEnabled;

    public ProductLoanBuilder withInterestRecalculationEnabled(Boolean interestRecalculationEnabled) {
        this.interestRecalculationEnabled = interestRecalculationEnabled;
        this.hasInterestRecalculationEnabled = true;
        return this;
    }

    private AccountingType accountingRule;
    private boolean hasAccountingRule;

    public ProductLoanBuilder withAccountingRule(AccountingType accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ProductLoanBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ProductLoanBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date startDate;
    private boolean hasStartDate;

    public ProductLoanBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        this.hasStartDate = true;
        return this;
    }

    private Date closeDate;
    private boolean hasCloseDate;

    public ProductLoanBuilder withCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        this.hasCloseDate = true;
        return this;
    }

    private String fundId;
    private boolean hasFundId;

    public ProductLoanBuilder withFundId(String fundId) {
        this.fundId = fundId;
        this.hasFundId = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public ProductLoanBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private Boolean includeInBorrowerCycle;
    private boolean hasIncludeInBorrowerCycle;

    public ProductLoanBuilder withIncludeInBorrowerCycle(Boolean includeInBorrowerCycle) {
        this.includeInBorrowerCycle = includeInBorrowerCycle;
        this.hasIncludeInBorrowerCycle = true;
        return this;
    }

    private Long installmentAmountInMultiplesOf;
    private Boolean hasInstallmentAmountInMultiplesOf;

    public ProductLoanBuilder withInstallmentAmountInMultiplesOf(Long installmentAmountInMultiplesOf) {
        this.installmentAmountInMultiplesOf = installmentAmountInMultiplesOf;
        this.hasInstallmentAmountInMultiplesOf = true;
        return this;
    }

    private Long inMultiplesOf;
    private boolean hasInMultiplesOf;

    public ProductLoanBuilder withInMultiplesOf(Long inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private Double minPrinciple;
    private boolean hasMinPrinciple;

    public ProductLoanBuilder withMinPrinciple(Double minPrinciple) {
        this.minPrinciple = minPrinciple;
        this.hasMinPrinciple = true;
        return this;
    }

    private Double maxPrinciple;
    private boolean hasMaxPrinciple;

    public ProductLoanBuilder withMaxPrinciple(Double maxPrinciple) {
        this.maxPrinciple = maxPrinciple;
        this.hasMaxPrinciple = true;
        return this;
    }

    private Double principle;
    private boolean hasPrinciple;

    public ProductLoanBuilder withPrinciple(Double principle) {
        this.principle = principle;
        this.hasPrinciple = true;
        return this;
    }

    private Boolean useBorrowerCycle;
    private boolean hasUseBorrowerCycle;

    public ProductLoanBuilder withUseBorrowerCycle(Boolean useBorrowerCycle) {
        this.useBorrowerCycle = useBorrowerCycle;
        this.hasUseBorrowerCycle = true;
        return this;
    }

    private Long minNumberOfRepayments;
    private boolean hasMinNumberOfRepayments;

    public ProductLoanBuilder withMinNumberOfRepayments(Long minNumberOfRepayments) {
        this.minNumberOfRepayments = minNumberOfRepayments;
        this.hasMinNumberOfRepayments = true;
        return this;
    }

    private Long numberOfRepayments;
    private boolean hasNumberOfRepayments;

    public ProductLoanBuilder withNumberOfRepayments(Long numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
        this.hasNumberOfRepayments = true;
        return this;
    }

    private Long maxNumberOfRepayments;
    private boolean hasMaxNumberOfRepayments;

    public ProductLoanBuilder withMaxNumberOfRepayments(Long maxNumberOfRepayments) {
        this.maxNumberOfRepayments = maxNumberOfRepayments;
        this.hasMaxNumberOfRepayments = true;
        return this;
    }

    private Double minInterestRatePerPeriod;
    private boolean hasMinInterestRatePerPeriod;

    public ProductLoanBuilder withMinInterestRatePerPeriod(Double minInterestRatePerPeriod) {
        this.minInterestRatePerPeriod = minInterestRatePerPeriod;
        this.hasMinInterestRatePerPeriod = true;
        return this;
    }

    private Double interestRatePerPeriod;
    private boolean hasInterestRatePerPeriod;

    public ProductLoanBuilder withInterestRatePerPeriod(Double interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
        this.hasInterestRatePerPeriod = true;
        return this;
    }

    private Double maxInterestRatePerPeriod;
    private boolean hasMaxInterestRatePerPeriod;

    public ProductLoanBuilder withMaxInterestRatePerPeriod(Double maxInterestRatePerPeriod) {
        this.maxInterestRatePerPeriod = maxInterestRatePerPeriod;
        this.hasMaxInterestRatePerPeriod = true;
        return this;
    }

    private NominalInterestRateType interestRateFrequencyType;
    private boolean hasInterestRateFrequencyType;

    public ProductLoanBuilder withInterestRateFrequencyType(NominalInterestRateType interestRateFrequencyType) {
        this.interestRateFrequencyType = interestRateFrequencyType;
        this.hasInterestRateFrequencyType = true;
        return this;
    }

    private Long minimumDaysBetweenDisbursalAndFirstRepayment;
    private boolean hasMinimumDaysBetweenDisbursalAndFirstRepayment;

    public ProductLoanBuilder withMinimumDaysBetweenDisbursalAndFirstRepayment(Long minimumDaysBetweenDisbursalAndFirstRepayment) {
        this.minimumDaysBetweenDisbursalAndFirstRepayment = minimumDaysBetweenDisbursalAndFirstRepayment;
        this.hasMinimumDaysBetweenDisbursalAndFirstRepayment = true;
        return this;
    }

    private LockInType repaymentFrequencyType;
    private boolean hasRepaymentFrequencyType;

    public ProductLoanBuilder withRepaymentFrequencyType(LockInType repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
        this.hasRepaymentFrequencyType = true;
        return this;
    }

    private Boolean linkedToFloatingInterestRates;
    private boolean hasLinkedToFloatingInterestRates;

    public ProductLoanBuilder withLinkedToFloatingInterestRates(Boolean linkedToFloatingInterestRates) {
        this.linkedToFloatingInterestRates = linkedToFloatingInterestRates;
        this.hasLinkedToFloatingInterestRates = true;
        return this;
    }

    private Boolean floatingInterestRateCalculationAllowed;
    private boolean hasFloatingInterestRateCalculationAllowed;

    public ProductLoanBuilder withFloatingInterestRateCalculationAllowed(Boolean floatingInterestRateCalculationAllowed) {
        this.floatingInterestRateCalculationAllowed = floatingInterestRateCalculationAllowed;
        this.hasFloatingInterestRateCalculationAllowed = true;
        return this;
    }

    private Double interestRateDifferential;
    private boolean hasInterestRateDifferential;

    public ProductLoanBuilder withInterestRateDifferential(Double interestRateDifferential) {
        this.interestRateDifferential = interestRateDifferential;
        this.hasInterestRateDifferential = true;
        return this;
    }

    private String floatingRatesId;
    private boolean hasFloatingRatesId;

    public ProductLoanBuilder withFloatingRatesId(String floatingRatesId) {
        this.floatingRatesId = floatingRatesId;
        this.hasFloatingRatesId = true;
        return this;
    }

    private Double maxDifferentialLendingRate;
    private boolean hasMaxDifferentialLendingRate;

    public ProductLoanBuilder withMaxDifferentialLendingRate(Double maxDifferentialLendingRate) {
        this.maxDifferentialLendingRate = maxDifferentialLendingRate;
        this.hasMaxDifferentialLendingRate = true;
        return this;
    }

    private Double defaultDifferentialLendingRate;
    private boolean hasDefaultDifferentialLendingRate;

    public ProductLoanBuilder withDefaultDifferentialLendingRate(Double defaultDifferentialLendingRate) {
        this.defaultDifferentialLendingRate = defaultDifferentialLendingRate;
        this.hasDefaultDifferentialLendingRate = true;
        return this;
    }

    private Double minDifferentialLendingRate;
    private boolean hasMinDifferentialLendingRate;

    public ProductLoanBuilder withMinDifferentialLendingRate(Double minDifferentialLendingRate) {
        this.minDifferentialLendingRate = minDifferentialLendingRate;
        this.hasMinDifferentialLendingRate = true;
        return this;
    }

    private List<Map<String, Object>> principleVariationsForBorrowerCycle = Lists.newArrayList();
    private boolean hasPrincipleVariationsForBorrowerCycle;

    public ProductLoanBuilder withPrincipleVariationsForBorrowerCycle(WhenType valueConditionType, Long borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
        Map<String, Object> cycle = Maps.newHashMap();
        cycle.put("valueConditionType", valueConditionType.getLiteral());
        cycle.put("borrowerCycleNumber", borrowerCycleNumber);
        cycle.put("minValue", minValue);
        cycle.put("defaultValue", defaultValue);
        cycle.put("maxValue", maxValue);
        this.principleVariationsForBorrowerCycle.add(cycle);
        this.hasPrincipleVariationsForBorrowerCycle = true;
        return this;
    }

    private List<Map<String, Object>> numberOfRepaymentVariationsForBorrowerCycle = Lists.newArrayList();
    private boolean hasNumberOfRepaymentVariationsForBorrowerCycle;

    public ProductLoanBuilder withNumberOfRepaymentVariationsForBorrowerCycle(WhenType valueConditionType, Long borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
        Map<String, Object> cycle = Maps.newHashMap();
        cycle.put("valueConditionType", valueConditionType.getLiteral());
        cycle.put("borrowerCycleNumber", borrowerCycleNumber);
        cycle.put("minValue", minValue);
        cycle.put("defaultValue", defaultValue);
        cycle.put("maxValue", maxValue);
        this.numberOfRepaymentVariationsForBorrowerCycle.add(cycle);
        this.hasNumberOfRepaymentVariationsForBorrowerCycle = true;
        return this;
    }

    private List<Map<String, Object>> interestRateVariationsForBorrowerCycle = Lists.newArrayList();
    private boolean hasInterestRateVariationsForBorrowerCycle;

    public ProductLoanBuilder withInterestRateVariationsForBorrowerCycle(WhenType valueConditionType, Long borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
        Map<String, Object> cycle = Maps.newHashMap();
        cycle.put("valueConditionType", valueConditionType.getLiteral());
        cycle.put("borrowerCycleNumber", borrowerCycleNumber);
        cycle.put("minValue", minValue);
        cycle.put("defaultValue", defaultValue);
        cycle.put("maxValue", maxValue);
        this.interestRateVariationsForBorrowerCycle.add(cycle);
        this.hasNumberOfRepaymentVariationsForBorrowerCycle = true;
        return this;
    }

    private Amortization amortizationType;
    private boolean hasAmortizationType;

    public ProductLoanBuilder withAmortizationType(Amortization amortizationType) {
        this.amortizationType = amortizationType;
        this.hasAmortizationType = true;
        return this;
    }

    private InterestMethod interestType;
    private boolean hasInterestType;

    public ProductLoanBuilder withInterestType(InterestMethod interestType) {
        this.interestType = interestType;
        this.hasInterestType = true;
        return this;
    }

    private Boolean allowPartialPeriodInterestCalcualtion;
    private boolean hasAllowPartialPeriodInterestCalcualtion;

    public ProductLoanBuilder withAllowPartialPeriodInterestCalcualtion(Boolean allowPartialPeriodInterestCalcualtion) {
        this.allowPartialPeriodInterestCalcualtion = allowPartialPeriodInterestCalcualtion;
        this.hasAllowPartialPeriodInterestCalcualtion = true;
        return this;
    }

    private InterestCalculationPeriod interestCalculationPeriodType;
    private boolean hasInterestCalculationPeriodType;

    public ProductLoanBuilder withInterestCalculationPeriodType(InterestCalculationPeriod interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.hasInterestCalculationPeriodType = true;
        return this;
    }

    private RepaymentStrategy transactionProcessingStrategyId;
    private boolean hasTransactionProcessingStrategyId;

    public ProductLoanBuilder withTransactionProcessingStrategyId(RepaymentStrategy repaymentStrategy) {
        this.transactionProcessingStrategyId = repaymentStrategy;
        this.hasTransactionProcessingStrategyId = true;
        return this;
    }

    private Long graceOnPrinciplePayment;
    private boolean hasGraceOnPrinciplePayment;

    public ProductLoanBuilder withGraceOnPrinciplePayment(Long graceOnPrinciplePayment) {
        this.graceOnPrinciplePayment = graceOnPrinciplePayment;
        this.hasGraceOnPrinciplePayment = true;
        return this;
    }

    private Long graceOnInterestPayment;
    private boolean hasGraceOnInterestPayment;

    public ProductLoanBuilder withGraceOnInterestPayment(Long graceOnInterestPayment) {
        this.graceOnInterestPayment = graceOnInterestPayment;
        this.hasGraceOnInterestPayment = true;
        return this;
    }

    private Long graceOnInterestCharged;
    private boolean hasGraceOnInterestCharged;

    public ProductLoanBuilder withGraceOnInterestCharged(Long graceOnInterestCharged) {
        this.graceOnInterestCharged = graceOnInterestCharged;
        this.hasGraceOnInterestCharged = true;
        return this;
    }

    private Double inArrearsTolerance;
    private boolean hasInArrearsTolerance;

    public ProductLoanBuilder withInArrearsTolerance(Double inArrearsTolerance) {
        this.inArrearsTolerance = inArrearsTolerance;
        this.hasInArrearsTolerance = true;
        return this;
    }

    private Boolean canDefineInstallmentAmount;
    private boolean hasCanDefineInstallmentAmount;

    public ProductLoanBuilder withCanDefineInstallmentAmount(Boolean canDefineInstallmentAmount) {
        this.canDefineInstallmentAmount = canDefineInstallmentAmount;
        this.hasCanDefineInstallmentAmount = true;
        return this;
    }

    private Long graceOnArrearsAgeing;
    private boolean hasGraceOnArrearsAgeing;

    public ProductLoanBuilder withGraceOnArrearsAgeing(Long graceOnArrearsAgeing) {
        this.graceOnArrearsAgeing = graceOnArrearsAgeing;
        this.hasGraceOnArrearsAgeing = true;
        return this;
    }

    private Long overdueDaysForNPA;
    private boolean hasOverdueDaysForNPA;

    public ProductLoanBuilder withOverdueDaysForNPA(Long overdueDaysForNPA) {
        this.overdueDaysForNPA = overdueDaysForNPA;
        this.hasOverdueDaysForNPA = true;
        return this;
    }

    private Double principleThresholdForLastInstallment;
    private boolean hasPrincipleThresholdForLastInstallment;

    public ProductLoanBuilder withPrincipleThresholdForLastInstallment(Double principleThresholdForLastInstallment) {
        this.principleThresholdForLastInstallment = principleThresholdForLastInstallment;
        this.hasPrincipleThresholdForLastInstallment = true;
        return this;
    }

    private Long minimumGap;
    private boolean hasMinimumGap;

    public ProductLoanBuilder withMinimumGap(Long minimumGap) {
        this.minimumGap = minimumGap;
        this.hasMinimumGap = true;
        return this;
    }

    private Long maximumGap;
    private boolean hasMaximumGap;

    public ProductLoanBuilder withMaximumGap(Long maximumGap) {
        this.maximumGap = maximumGap;
        this.hasMaximumGap = true;
        return this;
    }

    private Boolean accountMovesOutOfNPAOnlyOnArrearsCompletion;
    private boolean hasAccountMovesOutOfNPAOnlyOnArrearsCompletion;

    public ProductLoanBuilder withAccountMovesOutOfNPAOnlyOnArrearsCompletion(Boolean accountMovesOutOfNPAOnlyOnArrearsCompletion) {
        this.accountMovesOutOfNPAOnlyOnArrearsCompletion = accountMovesOutOfNPAOnlyOnArrearsCompletion;
        this.hasAccountMovesOutOfNPAOnlyOnArrearsCompletion = true;
        return this;
    }

    private Boolean allowVariableInstallments;
    private boolean hasAllowVariableInstallments;

    public ProductLoanBuilder withAllowVariableInstallments(Boolean allowVariableInstallments) {
        this.allowVariableInstallments = allowVariableInstallments;
        this.hasAllowVariableInstallments = true;
        return this;
    }

    private Boolean canUseForTopup;
    private boolean hasCanUseForTopup;

    public ProductLoanBuilder withCanUseForTopup(Boolean canUseForTopup) {
        this.canUseForTopup = canUseForTopup;
        this.hasCanUseForTopup = true;
        return this;
    }

    private ClosureInterestCalculationRule preClosureInterestCalculationStrategy;
    private boolean hasPreClosureInterestCalculationStrategy;

    public ProductLoanBuilder withPreClosureInterestCalculationStrategy(ClosureInterestCalculationRule preClosureInterestCalculationStrategy) {
        this.preClosureInterestCalculationStrategy = preClosureInterestCalculationStrategy;
        this.hasPreClosureInterestCalculationStrategy = true;
        return this;
    }

    private AdvancePaymentsAdjustmentType rescheduleStrategyMethod;
    private boolean hasRescheduleStrategyMethod;

    public ProductLoanBuilder withRescheduleStrategyMethod(AdvancePaymentsAdjustmentType rescheduleStrategyMethod) {
        this.rescheduleStrategyMethod = rescheduleStrategyMethod;
        this.hasRescheduleStrategyMethod = true;
        return this;
    }

    private InterestRecalculationCompound interestRecalculationCompoundingMethod;
    private boolean hasInterestRecalculationCompoundingMethod;

    public ProductLoanBuilder withInterestRecalculationCompoundingMethod(InterestRecalculationCompound interestRecalculationCompoundingMethod) {
        this.interestRecalculationCompoundingMethod = interestRecalculationCompoundingMethod;
        this.hasInterestRecalculationCompoundingMethod = true;
        return this;
    }

    private Frequency recalculationCompoundingFrequencyType;
    private boolean hasRecalculationCompoundingFrequencyType;

    public ProductLoanBuilder withRecalculationCompoundingFrequencyType(Frequency recalculationCompoundingFrequencyType) {
        this.recalculationCompoundingFrequencyType = recalculationCompoundingFrequencyType;
        this.hasRecalculationCompoundingFrequencyType = true;
        return this;
    }

    private Long recalculationCompoundingFrequencyInterval;
    private boolean hasRecalculationCompoundingFrequencyInterval;

    public ProductLoanBuilder withRecalculationCompoundingFrequencyInterval(Long recalculationCompoundingFrequencyInterval) {
        this.recalculationCompoundingFrequencyInterval = recalculationCompoundingFrequencyInterval;
        this.hasRecalculationCompoundingFrequencyInterval = true;
        return this;
    }

    private FrequencyType recalculationCompoundingFrequencyNthDayType;
    private boolean hasRecalculationCompoundingFrequencyNthDayType;

    public ProductLoanBuilder withRecalculationCompoundingFrequencyNthDayType(FrequencyType recalculationCompoundingFrequencyNthDayType) {
        this.recalculationCompoundingFrequencyNthDayType = recalculationCompoundingFrequencyNthDayType;
        this.hasRecalculationCompoundingFrequencyNthDayType = true;
        return this;
    }

    private FrequencyDay recalculationCompoundingFrequencyDayOfWeekType;
    private boolean hasRecalculationCompoundingFrequencyDayOfWeekType;

    public ProductLoanBuilder withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay recalculationCompoundingFrequencyDayOfWeekType) {
        this.recalculationCompoundingFrequencyDayOfWeekType = recalculationCompoundingFrequencyDayOfWeekType;
        this.hasRecalculationCompoundingFrequencyDayOfWeekType = true;
        return this;
    }

    private Boolean arrearsBasedOnOriginalSchedule;
    private boolean hasArrearsBasedOnOriginalSchedule;

    public ProductLoanBuilder withArrearsBasedOnOriginalSchedule(Boolean arrearsBasedOnOriginalSchedule) {
        this.arrearsBasedOnOriginalSchedule = arrearsBasedOnOriginalSchedule;
        this.hasArrearsBasedOnOriginalSchedule = true;
        return this;
    }

    private Long recalculationRestFrequencyInterval;
    private boolean hasRecalculationRestFrequencyInterval;

    public ProductLoanBuilder withRecalculationRestFrequencyInterval(Long recalculationRestFrequencyInterval) {
        this.recalculationRestFrequencyInterval = recalculationRestFrequencyInterval;
        this.hasRecalculationRestFrequencyInterval = true;
        return this;
    }

    private FrequencyDay recalculationRestFrequencyDayOfWeekType;
    private boolean hasRecalculationRestFrequencyDayOfWeekType;

    public ProductLoanBuilder withRecalculationRestFrequencyDayOfWeekType(FrequencyDay recalculationRestFrequencyDayOfWeekType) {
        this.recalculationRestFrequencyDayOfWeekType = recalculationRestFrequencyDayOfWeekType;
        this.hasRecalculationRestFrequencyDayOfWeekType = true;
        return this;
    }

    private FrequencyType recalculationRestFrequencyNthDayType;
    private boolean hasRecalculationRestFrequencyNthDayType;

    public ProductLoanBuilder withRecalculationRestFrequencyNthDayType(FrequencyType recalculationRestFrequencyNthDayType) {
        this.recalculationRestFrequencyNthDayType = recalculationRestFrequencyNthDayType;
        this.hasRecalculationRestFrequencyNthDayType = true;
        return this;
    }

    private Frequency recalculationRestFrequencyType;
    private boolean hasRecalculationRestFrequencyType;

    public ProductLoanBuilder withRecalculationRestFrequencyType(Frequency recalculationRestFrequencyType) {
        this.recalculationRestFrequencyType = recalculationRestFrequencyType;
        this.hasRecalculationRestFrequencyType = true;
        return this;
    }

    private Boolean holdGuaranteeFunds;
    private boolean hasHoldGuaranteeFunds;

    public ProductLoanBuilder withHoldGuaranteeFunds(Boolean holdGuaranteeFunds) {
        this.holdGuaranteeFunds = holdGuaranteeFunds;
        this.hasHoldGuaranteeFunds = true;
        return this;
    }

    private Double mandatoryGuarantee;
    private boolean hasMandatoryGuarantee;

    public ProductLoanBuilder withMandatoryGuarantee(Double mandatoryGuarantee) {
        this.mandatoryGuarantee = mandatoryGuarantee;
        this.hasMandatoryGuarantee = true;
        return this;
    }

    private Double minimumGuaranteeFromGuarantor;
    private boolean hasMinimumGuaranteeFromGuarantor;

    public ProductLoanBuilder withMinimumGuaranteeFromGuarantor(Double minimumGuaranteeFromGuarantor) {
        this.minimumGuaranteeFromGuarantor = minimumGuaranteeFromGuarantor;
        this.hasMinimumGuaranteeFromGuarantor = true;
        return this;
    }

    private Double minimumGuaranteeFromOwnFunds;
    private boolean hasMinimumGuaranteeFromOwnFunds;

    public ProductLoanBuilder withMinimumGuaranteeFromOwnFunds(Double minimumGuaranteeFromOwnFunds) {
        this.minimumGuaranteeFromOwnFunds = minimumGuaranteeFromOwnFunds;
        this.hasMinimumGuaranteeFromOwnFunds = true;
        return this;
    }

    private Boolean multiDisburseLoan;
    private boolean hasMultiDisburseLoan;

    public ProductLoanBuilder withMultiDisburseLoan(Boolean multiDisburseLoan) {
        this.multiDisburseLoan = multiDisburseLoan;
        this.hasMultiDisburseLoan = true;
        return this;
    }

    private Long maxTrancheCount;
    private boolean hasMaxTrancheCount;

    public ProductLoanBuilder withMaxTrancheCount(Long maxTrancheCount) {
        this.maxTrancheCount = maxTrancheCount;
        this.hasMaxTrancheCount = true;
        return this;
    }

    private Double outstandingLoanBalance;
    private boolean hasOutstandingLoanBalance;

    public ProductLoanBuilder withOutstandingLoanBalance(Double outstandingLoanBalance) {
        this.outstandingLoanBalance = outstandingLoanBalance;
        this.hasOutstandingLoanBalance = true;
        return this;
    }

    private JSONObject allowAttributeOverrides;
    private boolean hasAllowAttributeOverrides;

    public ProductLoanBuilder withAllowAttributeOverrides(JSONObject allowAttributeOverrides) {
        this.allowAttributeOverrides = allowAttributeOverrides;
        this.hasAllowAttributeOverrides = true;
        return this;
    }

    private String fundSourceAccountId;
    private boolean hasFundSourceAccountId;

    public ProductLoanBuilder withFundSourceAccountId(String fundSourceAccountId) {
        this.fundSourceAccountId = fundSourceAccountId;
        this.hasFundSourceAccountId = true;
        return this;
    }

    private String loanPortfolioAccountId;
    private boolean hasLoanPortfolioAccountId;

    public ProductLoanBuilder withLoanPortfolioAccountId(String loanPortfolioAccountId) {
        this.loanPortfolioAccountId = loanPortfolioAccountId;
        this.hasLoanPortfolioAccountId = true;
        return this;
    }

    private String receivableInterestAccountId;
    private boolean hasReceivableInterestAccountId;

    public ProductLoanBuilder withReceivableInterestAccountId(String receivableInterestAccountId) {
        this.receivableInterestAccountId = receivableInterestAccountId;
        this.hasReceivableInterestAccountId = true;
        return this;
    }

    private String interestOnLoanAccountId;
    private boolean hasInterestOnLoanAccountId;

    public ProductLoanBuilder withInterestOnLoanAccountId(String interestOnLoanAccountId) {
        this.interestOnLoanAccountId = interestOnLoanAccountId;
        this.hasInterestOnLoanAccountId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public ProductLoanBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    private String incomeFromPenaltyAccountId;
    private boolean hasIncomeFromPenaltyAccountId;

    public ProductLoanBuilder withIncomeFromPenaltyAccountId(String incomeFromPenaltyAccountId) {
        this.incomeFromPenaltyAccountId = incomeFromPenaltyAccountId;
        this.hasIncomeFromPenaltyAccountId = true;
        return this;
    }

    private String incomeFromRecoveryAccountId;
    private boolean hasIncomeFromRecoveryAccountId;

    public ProductLoanBuilder withIncomeFromRecoveryAccountId(String incomeFromRecoveryAccountId) {
        this.incomeFromRecoveryAccountId = incomeFromRecoveryAccountId;
        this.hasIncomeFromRecoveryAccountId = true;
        return this;
    }

    private String writeOffAccountId;
    private boolean hasWriteOffAccountId;

    public ProductLoanBuilder withWriteOffAccountId(String writeOffAccountId) {
        this.writeOffAccountId = writeOffAccountId;
        this.hasWriteOffAccountId = true;
        return this;
    }

    private String overpaymentLiabilityAccountId;
    private boolean hasOverpaymentLiabilityAccountId;

    public ProductLoanBuilder withOverpaymentLiabilityAccountId(String overpaymentLiabilityAccountId) {
        this.overpaymentLiabilityAccountId = overpaymentLiabilityAccountId;
        this.hasOverpaymentLiabilityAccountId = true;
        return this;
    }

    private String transfersInSuspenseAccountId;
    private boolean hasTransfersInSuspenseAccountId;

    public ProductLoanBuilder withTransfersInSuspenseAccountId(String transfersInSuspenseAccountId) {
        this.transfersInSuspenseAccountId = transfersInSuspenseAccountId;
        this.hasTransfersInSuspenseAccountId = true;
        return this;
    }

    private String receivableFeeAccountId;
    private boolean hasReceivableFeeAccountId;

    public ProductLoanBuilder withReceivableFeeAccountId(String receivableFeeAccountId) {
        this.receivableFeeAccountId = receivableFeeAccountId;
        this.hasReceivableFeeAccountId = true;
        return this;
    }

    private String receivablePenaltyAccountId;
    private boolean hasReceivablePenaltyAccountId;

    public ProductLoanBuilder withReceivablePenaltyAccountId(String receivablePenaltyAccountId) {
        this.receivablePenaltyAccountId = receivablePenaltyAccountId;
        this.hasReceivablePenaltyAccountId = true;
        return this;
    }

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public ProductLoanBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("paymentTypeId", paymentTypeId);
        item.put("fundSourceAccountId", fundSourceAccountId);
        this.paymentChannelToFundSourceMappings.add(item);
        this.hasPaymentChannelToFundSourceMappings = true;
        return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public ProductLoanBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.feeToIncomeAccountMappings.add(item);
        this.hasFeeToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public ProductLoanBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.penaltyToIncomeAccountMappings.add(item);
        this.hasPenaltyToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public ProductLoanBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    private String recalculationCompoundingFrequencyOnDayType;
    private boolean hasRecalculationCompoundingFrequencyOnDayType;

    public ProductLoanBuilder withRecalculationCompoundingFrequencyOnDayType(String recalculationCompoundingFrequencyOnDayType) {
        this.recalculationCompoundingFrequencyOnDayType = recalculationCompoundingFrequencyOnDayType;
        this.hasRecalculationCompoundingFrequencyOnDayType = true;
        return this;
    }

    private String recalculationRestFrequencyOnDayType;
    private boolean hasRecalculationRestFrequencyOnDayType;

    public ProductLoanBuilder withRecalculationRestFrequencyOnDayType(String recalculationRestFrequencyOnDayType) {
        this.recalculationRestFrequencyOnDayType = recalculationRestFrequencyOnDayType;
        this.hasRecalculationRestFrequencyOnDayType = true;
        return this;
    }

    private Boolean equalAmortization;
    private boolean hasEqualAmortization;

    public ProductLoanBuilder withEqualAmortization(Boolean equalAmortization) {
        this.equalAmortization = equalAmortization;
        this.hasEqualAmortization = true;
        return this;
    }

    public JsonNode build() {

        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        List<String> errors = Lists.newArrayList();
        if (this.name == null || "".equals(this.name)) {
            errors.add("name is required");
        }

        if (this.shortName == null || "".equals(this.shortName)) {
            errors.add("shortName is required");
        }

        if (this.currencyCode == null || "".equals(this.currencyCode)) {
            errors.add("currencyCode is required");
        }

        if (this.digitsAfterDecimal == null) {
            errors.add("digitsAfterDecimal is required");
        }

        if (this.inMultiplesOf == null) {
            errors.add("inMultiplesOf is required");
        }

        if (this.numberOfRepayments == null) {
            errors.add("numberOfRepayments is required");
        }

        if (this.principle == null) {
            errors.add("principle is required");
        }

        if (this.repaymentEvery == null) {
            errors.add("repaymentEvery is required");
        }

        if (this.repaymentFrequencyType == null) {
            errors.add("repaymentFrequencyType is required");
        }

        if (this.linkedToFloatingInterestRates == null || !this.linkedToFloatingInterestRates) {
            if (this.interestRateFrequencyType == null) {
                errors.add("interestRateFrequencyType is required");
            }

            if (this.interestRatePerPeriod == null) {
                errors.add("interestRatePerPeriod is required");
            }
        }

        if (this.amortizationType == null) {
            errors.add("amortizationType is required");
        }

        if (this.interestType == null) {
            errors.add("interestType is required");
        }

        if (this.interestCalculationPeriodType == null) {
            errors.add("interestCalculationPeriodType is required");
        }

        if (this.transactionProcessingStrategyId == null) {
            errors.add("transactionProcessingStrategyId is required");
        }

        if (this.interestRecalculationEnabled == null) {
            errors.add("isInterestRecalculationEnabled is required");
        }

        if (this.daysInYearType == null) {
            errors.add("daysInYearType is required");
        }

        if (this.daysInMonthType == null) {
            errors.add("daysInMonthType is required");
        }

        if (this.accountingRule == null) {
            errors.add("accountingRule is required");
        } else {
            if (this.accountingRule == AccountingType.Cash) {
                if (this.fundSourceAccountId == null || "".equals(this.fundSourceAccountId)) {
                    errors.add("fundSourceAccountId is required");
                }
                if (this.loanPortfolioAccountId == null || "".equals(this.loanPortfolioAccountId)) {
                    errors.add("loanPortfolioAccountId is required");
                }
                if (this.interestOnLoanAccountId == null || "".equals(this.interestOnLoanAccountId)) {
                    errors.add("interestOnLoanAccountId is required");
                }
                if (this.incomeFromFeeAccountId == null || "".equals(this.incomeFromFeeAccountId)) {
                    errors.add("incomeFromFeeAccountId is required");
                }
                if (this.incomeFromPenaltyAccountId == null || "".equals(this.incomeFromPenaltyAccountId)) {
                    errors.add("incomeFromPenaltyAccountId is required");
                }
                if (this.writeOffAccountId == null || "".equals(this.writeOffAccountId)) {
                    errors.add("writeOffAccountId is required");
                }
                if (this.transfersInSuspenseAccountId == null || "".equals(this.transfersInSuspenseAccountId)) {
                    errors.add("transfersInSuspenseAccountId is required");
                }
                if (this.overpaymentLiabilityAccountId == null || "".equals(this.overpaymentLiabilityAccountId)) {
                    errors.add("overpaymentLiabilityAccountId is required");
                }
            } else if (this.accountingRule == AccountingType.Periodic || this.accountingRule == AccountingType.Upfront) {
                if (this.fundSourceAccountId == null || "".equals(this.fundSourceAccountId)) {
                    errors.add("fundSourceAccountId is required");
                }
                if (this.loanPortfolioAccountId == null || "".equals(this.loanPortfolioAccountId)) {
                    errors.add("loanPortfolioAccountId is required");
                }
                if (this.interestOnLoanAccountId == null || "".equals(this.interestOnLoanAccountId)) {
                    errors.add("interestOnLoanAccountId is required");
                }
                if (this.incomeFromFeeAccountId == null || "".equals(this.incomeFromFeeAccountId)) {
                    errors.add("incomeFromFeeAccountId is required");
                }
                if (this.incomeFromPenaltyAccountId == null || "".equals(this.incomeFromPenaltyAccountId)) {
                    errors.add("incomeFromPenaltyAccountId is required");
                }
                if (this.writeOffAccountId == null || "".equals(this.writeOffAccountId)) {
                    errors.add("writeOffAccountId is required");
                }
                if (this.receivableInterestAccountId == null || "".equals(this.receivableInterestAccountId)) {
                    errors.add("receivableInterestAccountId is required");
                }
                if (this.receivableFeeAccountId == null || "".equals(this.receivableFeeAccountId)) {
                    errors.add("receivableFeeAccountId is required");
                }
                if (this.receivablePenaltyAccountId == null || "".equals(this.receivablePenaltyAccountId)) {
                    errors.add("receivablePenaltyAccountId is required");
                }
                if (this.transfersInSuspenseAccountId == null || "".equals(this.transfersInSuspenseAccountId)) {
                    errors.add("transfersInSuspenseAccountId is required");
                }
                if (this.overpaymentLiabilityAccountId == null || "".equals(this.overpaymentLiabilityAccountId)) {
                    errors.add("overpaymentLiabilityAccountId is required");
                }
            }
        }

        if (this.interestRecalculationEnabled != null && this.interestRecalculationEnabled) {
            if (this.interestRecalculationCompoundingMethod == null) {
                errors.add("interestRecalculationCompoundingMethod is required");
            }
            if (this.rescheduleStrategyMethod == null) {
                errors.add("rescheduleStrategyMethod is required");
            }
            if (this.recalculationRestFrequencyType == null) {
                errors.add("recalculationRestFrequencyType is required");
            }
        }

        if (this.holdGuaranteeFunds != null && this.holdGuaranteeFunds) {
            if (this.mandatoryGuarantee == null) {
                errors.add("mandatoryGuarantee is required");
            }
        }

        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
        }

        if (this.equalAmortization) {
            object.getObject().put("isEqualAmortization", this.equalAmortization);
        }

        if (this.hasPenaltyToIncomeAccountMappings) {
            object.getObject().put("penaltyToIncomeAccountMappings", this.penaltyToIncomeAccountMappings);
        }

        if (this.hasFeeToIncomeAccountMappings) {
            object.getObject().put("feeToIncomeAccountMappings", this.feeToIncomeAccountMappings);
        }

        if (this.hasPaymentChannelToFundSourceMappings) {
            object.getObject().put("paymentChannelToFundSourceMappings", this.paymentChannelToFundSourceMappings);
        }

        if (this.hasReceivablePenaltyAccountId) {
            object.getObject().put("receivablePenaltyAccountId", this.receivablePenaltyAccountId);
        }

        if (this.hasReceivableFeeAccountId) {
            object.getObject().put("receivableFeeAccountId", this.receivableFeeAccountId);
        }

        if (this.hasTransfersInSuspenseAccountId) {
            object.getObject().put("transfersInSuspenseAccountId", this.transfersInSuspenseAccountId);
        }

        if (this.hasOverpaymentLiabilityAccountId) {
            object.getObject().put("overpaymentLiabilityAccountId", this.overpaymentLiabilityAccountId);
        }

        if (this.hasWriteOffAccountId) {
            object.getObject().put("writeOffAccountId", this.writeOffAccountId);
        }

        if (this.hasIncomeFromRecoveryAccountId) {
            object.getObject().put("incomeFromRecoveryAccountId", this.incomeFromRecoveryAccountId);
        }

        if (this.hasIncomeFromPenaltyAccountId) {
            object.getObject().put("incomeFromPenaltyAccountId", this.incomeFromPenaltyAccountId);
        }

        if (this.hasIncomeFromFeeAccountId) {
            object.getObject().put("incomeFromFeeAccountId", this.incomeFromFeeAccountId);
        }

        if (this.hasInterestOnLoanAccountId) {
            object.getObject().put("interestOnLoanAccountId", this.interestOnLoanAccountId);
        }

        if (this.hasReceivableInterestAccountId) {
            object.getObject().put("receivableInterestAccountId", this.receivableInterestAccountId);
        }

        if (this.hasLoanPortfolioAccountId) {
            object.getObject().put("loanPortfolioAccountId", this.loanPortfolioAccountId);
        }

        if (this.hasFundSourceAccountId) {
            object.getObject().put("fundSourceAccountId", this.fundSourceAccountId);
        }

        if (this.hasAllowAttributeOverrides) {
            object.getObject().put("allowAttributeOverrides", this.allowAttributeOverrides);
        }

        if (this.hasOutstandingLoanBalance) {
            object.getObject().put("outstandingLoanBalance", this.outstandingLoanBalance);
        }

        if (this.hasMaxTrancheCount) {
            object.getObject().put("maxTrancheCount", this.maxTrancheCount);
        }

        if (this.hasMultiDisburseLoan) {
            object.getObject().put("multiDisburseLoan", this.multiDisburseLoan);
        }

        if (this.hasMinimumGuaranteeFromOwnFunds) {
            object.getObject().put("minimumGuaranteeFromOwnFunds", this.minimumGuaranteeFromOwnFunds);
        }

        if (this.hasMinimumGuaranteeFromGuarantor) {
            object.getObject().put("minimumGuaranteeFromGuarantor", this.minimumGuaranteeFromGuarantor);
        }

        if (this.hasMandatoryGuarantee) {
            object.getObject().put("mandatoryGuarantee", this.mandatoryGuarantee);
        }

        if (this.hasHoldGuaranteeFunds) {
            object.getObject().put("holdGuaranteeFunds", this.holdGuaranteeFunds);
        }

        if (this.hasRecalculationRestFrequencyType) {
            if (this.recalculationRestFrequencyType != null) {
                object.getObject().put("recalculationRestFrequencyType", this.recalculationRestFrequencyType.getLiteral());
            } else {
                object.getObject().put("recalculationRestFrequencyType", (String) null);
            }
        }

        if (this.hasRecalculationRestFrequencyNthDayType) {
            if (this.recalculationRestFrequencyNthDayType != null) {
                object.getObject().put("recalculationRestFrequencyNthDayType", this.recalculationRestFrequencyNthDayType.getLiteral());
            } else {
                object.getObject().put("recalculationRestFrequencyNthDayType", (String) null);
            }
        }

        if (this.hasRecalculationRestFrequencyDayOfWeekType) {
            if (this.recalculationRestFrequencyDayOfWeekType != null) {
                object.getObject().put("recalculationRestFrequencyDayOfWeekType", this.recalculationRestFrequencyDayOfWeekType.getLiteral());
            } else {
                object.getObject().put("recalculationRestFrequencyDayOfWeekType", (String) null);
            }
        }

        if (this.hasRecalculationCompoundingFrequencyOnDayType) {
            object.getObject().put("recalculationCompoundingFrequencyOnDayType", this.recalculationCompoundingFrequencyOnDayType);
        }

        if (this.hasRecalculationRestFrequencyOnDayType) {
            object.getObject().put("recalculationRestFrequencyOnDayType", this.recalculationRestFrequencyOnDayType);
        }

        if (this.hasRecalculationRestFrequencyInterval) {
            object.getObject().put("recalculationRestFrequencyInterval", this.recalculationRestFrequencyInterval);
        }

        if (this.hasArrearsBasedOnOriginalSchedule) {
            object.getObject().put("isArrearsBasedOnOriginalSchedule", this.arrearsBasedOnOriginalSchedule);
        }

        if (this.hasRecalculationCompoundingFrequencyDayOfWeekType) {
            if (this.recalculationCompoundingFrequencyDayOfWeekType != null) {
                object.getObject().put("recalculationCompoundingFrequencyDayOfWeekType", this.recalculationCompoundingFrequencyDayOfWeekType.getLiteral());
            } else {
                object.getObject().put("recalculationCompoundingFrequencyDayOfWeekType", (String) null);
            }
        }

        if (this.hasRecalculationCompoundingFrequencyNthDayType) {
            if (this.recalculationCompoundingFrequencyNthDayType != null) {
                object.getObject().put("recalculationCompoundingFrequencyNthDayType", this.recalculationCompoundingFrequencyNthDayType.getLiteral());
            } else {
                object.getObject().put("recalculationCompoundingFrequencyNthDayType", (String) null);
            }
        }

        if (this.hasRecalculationCompoundingFrequencyInterval) {
            object.getObject().put("recalculationCompoundingFrequencyInterval", this.recalculationCompoundingFrequencyInterval);
        }

        if (this.hasRecalculationCompoundingFrequencyType) {
            if (this.recalculationCompoundingFrequencyType != null) {
                object.getObject().put("recalculationCompoundingFrequencyType", this.recalculationCompoundingFrequencyType.getLiteral());
            } else {
                object.getObject().put("recalculationCompoundingFrequencyType", (String) null);
            }
        }

        if (this.hasInterestRecalculationCompoundingMethod) {
            if (this.interestRecalculationCompoundingMethod != null) {
                object.getObject().put("interestRecalculationCompoundingMethod", this.interestRecalculationCompoundingMethod.getLiteral());
            } else {
                object.getObject().put("interestRecalculationCompoundingMethod", (String) null);
            }
        }

        if (this.hasRescheduleStrategyMethod) {
            if (this.rescheduleStrategyMethod != null) {
                object.getObject().put("rescheduleStrategyMethod", this.rescheduleStrategyMethod.getLiteral());
            } else {
                object.getObject().put("rescheduleStrategyMethod", (String) null);
            }
        }

        if (this.hasPreClosureInterestCalculationStrategy) {
            if (this.preClosureInterestCalculationStrategy != null) {
                object.getObject().put("preClosureInterestCalculationStrategy", this.preClosureInterestCalculationStrategy.getLiteral());
            } else {
                object.getObject().put("preClosureInterestCalculationStrategy", (String) null);
            }
        }

        if (this.hasCanUseForTopup) {
            object.getObject().put("canUseForTopup", this.canUseForTopup);
        }

        if (this.hasAllowVariableInstallments) {
            object.getObject().put("allowVariableInstallments", this.allowVariableInstallments);
        }

        if (this.hasAccountMovesOutOfNPAOnlyOnArrearsCompletion) {
            object.getObject().put("accountMovesOutOfNPAOnlyOnArrearsCompletion", this.accountMovesOutOfNPAOnlyOnArrearsCompletion);
        }

        if (this.hasMaximumGap) {
            object.getObject().put("maximumGap", this.maximumGap);
        }

        if (this.hasMinimumGap) {
            object.getObject().put("minimumGap", this.minimumGap);
        }

        if (this.hasPrincipleThresholdForLastInstallment) {
            object.getObject().put("principalThresholdForLastInstallment", this.principleThresholdForLastInstallment);
        }

        if (this.hasOverdueDaysForNPA) {
            object.getObject().put("overdueDaysForNPA", this.overdueDaysForNPA);
        }

        if (this.hasGraceOnArrearsAgeing) {
            object.getObject().put("graceOnArrearsAgeing", this.graceOnArrearsAgeing);
        }

        if (this.hasCanDefineInstallmentAmount) {
            object.getObject().put("canDefineInstallmentAmount", this.canDefineInstallmentAmount);
        }

        if (this.hasInArrearsTolerance) {
            object.getObject().put("inArrearsTolerance", this.inArrearsTolerance);
        }

        if (this.hasGraceOnInterestCharged) {
            object.getObject().put("graceOnInterestCharged", this.graceOnInterestCharged);
        }

        if (this.hasGraceOnInterestPayment) {
            object.getObject().put("graceOnInterestPayment", this.graceOnInterestPayment);
        }

        if (this.hasGraceOnPrinciplePayment) {
            object.getObject().put("graceOnPrincipalPayment", this.graceOnPrinciplePayment);
        }

        if (this.hasInterestCalculationPeriodType) {
            if (this.interestCalculationPeriodType != null) {
                object.getObject().put("interestCalculationPeriodType", this.interestCalculationPeriodType.getLiteral());
            } else {
                object.getObject().put("interestCalculationPeriodType", (String) null);
            }
        }

        if (this.hasAllowPartialPeriodInterestCalcualtion) {
            object.getObject().put("allowPartialPeriodInterestCalcualtion", this.allowPartialPeriodInterestCalcualtion);
        }

        if (this.hasInterestType) {
            if (this.interestType != null) {
                object.getObject().put("interestType", this.interestType.getLiteral());
            } else {
                object.getObject().put("interestType", (String) null);
            }
        }

        if (this.hasAmortizationType) {
            if (this.amortizationType != null) {
                object.getObject().put("amortizationType", this.amortizationType.getLiteral());
            } else {
                object.getObject().put("amortizationType", (String) null);
            }
        }

        if (this.hasInterestRateVariationsForBorrowerCycle) {
            object.getObject().put("interestRateVariationsForBorrowerCycle", this.interestRateVariationsForBorrowerCycle);
        }

        if (this.hasNumberOfRepaymentVariationsForBorrowerCycle) {
            object.getObject().put("numberOfRepaymentVariationsForBorrowerCycle", this.numberOfRepaymentVariationsForBorrowerCycle);
        }

        if (this.hasPrincipleVariationsForBorrowerCycle) {
            object.getObject().put("principalVariationsForBorrowerCycle", this.principleVariationsForBorrowerCycle);
        }

        if (this.hasMinDifferentialLendingRate) {
            object.getObject().put("minDifferentialLendingRate", this.minDifferentialLendingRate);
        }

        if (this.hasDefaultDifferentialLendingRate) {
            object.getObject().put("defaultDifferentialLendingRate", this.defaultDifferentialLendingRate);
        }

        if (this.hasMaxDifferentialLendingRate) {
            object.getObject().put("maxDifferentialLendingRate", this.maxDifferentialLendingRate);
        }

        if (this.hasFloatingRatesId) {
            object.getObject().put("floatingRatesId", this.floatingRatesId);
        }

        if (this.hasInterestRateDifferential) {
            object.getObject().put("interestRateDifferential", this.interestRateDifferential);
        }

        if (this.hasFloatingInterestRateCalculationAllowed) {
            object.getObject().put("isFloatingInterestRateCalculationAllowed", this.floatingInterestRateCalculationAllowed);
        }

        if (this.hasLinkedToFloatingInterestRates) {
            object.getObject().put("isLinkedToFloatingInterestRates", this.linkedToFloatingInterestRates);
        }

        if (this.hasMinimumDaysBetweenDisbursalAndFirstRepayment) {
            object.getObject().put("minimumDaysBetweenDisbursalAndFirstRepayment", this.minimumDaysBetweenDisbursalAndFirstRepayment);
        }

        if (this.hasInterestRateFrequencyType) {
            if (this.interestRateFrequencyType != null) {
                object.getObject().put("interestRateFrequencyType", this.interestRateFrequencyType.getLiteral());
            } else {
                object.getObject().put("interestRateFrequencyType", (String) null);
            }
        }

        if (this.hasMaxInterestRatePerPeriod) {
            object.getObject().put("maxInterestRatePerPeriod", this.maxInterestRatePerPeriod);
        }

        if (this.hasInterestRatePerPeriod) {
            object.getObject().put("interestRatePerPeriod", this.interestRatePerPeriod);
        }

        if (this.hasMinInterestRatePerPeriod) {
            object.getObject().put("minInterestRatePerPeriod", this.minInterestRatePerPeriod);
        }

        if (this.hasMaxNumberOfRepayments) {
            object.getObject().put("maxNumberOfRepayments", this.maxNumberOfRepayments);
        }

        if (this.hasMinNumberOfRepayments) {
            object.getObject().put("minNumberOfRepayments", this.minNumberOfRepayments);
        }

        if (this.hasNumberOfRepayments) {
            object.getObject().put("numberOfRepayments", this.numberOfRepayments);
        }

        if (this.hasPrinciple) {
            object.getObject().put("principal", this.principle);
        }

        if (this.hasUseBorrowerCycle) {
            object.getObject().put("useBorrowerCycle", this.useBorrowerCycle);
        }

        if (this.hasMaxPrinciple) {
            object.getObject().put("maxPrincipal", this.maxPrinciple);
        }

        if (this.hasMinPrinciple) {
            object.getObject().put("minPrincipal", this.minPrinciple);
        }

        if (this.hasInMultiplesOf) {
            object.getObject().put("inMultiplesOf", this.inMultiplesOf);
        }

        if (this.hasInstallmentAmountInMultiplesOf) {
            object.getObject().put("installmentAmountInMultiplesOf", this.installmentAmountInMultiplesOf);
        }

        if (this.hasIncludeInBorrowerCycle) {
            object.getObject().put("includeInBorrowerCycle", this.includeInBorrowerCycle);
        }

        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }

        if (this.hasFundId) {
            object.getObject().put("fundId", this.fundId);
        }

        if (this.hasCloseDate) {
            if (this.closeDate != null) {
                object.getObject().put("closeDate", DateFormatUtils.format(this.closeDate, this.dateFormat));
            } else {
                object.getObject().put("closeDate", (String) null);
            }
        }

        if (this.hasStartDate) {
            if (this.startDate != null) {
                object.getObject().put("startDate", DateFormatUtils.format(this.startDate, this.dateFormat));
            } else {
                object.getObject().put("startDate", (String) null);
            }
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasAccountingRule) {
            object.getObject().put("accountingRule", this.accountingRule.getLiteral());
        }

        if (this.hasInterestRecalculationEnabled) {
            object.getObject().put("isInterestRecalculationEnabled", interestRecalculationEnabled);
        }

        if (this.hasDaysInMonthType) {
            if (this.daysInMonthType != null) {
                object.getObject().put("daysInMonthType", this.daysInMonthType.getLiteral());
            } else {
                object.getObject().put("daysInMonthType", (String) null);
            }
        }

        if (this.hasDaysInYearType) {
            if (this.daysInYearType != null) {
                object.getObject().put("daysInYearType", this.daysInYearType.getLiteral());
            } else {
                object.getObject().put("daysInYearType", (String) null);
            }
        }

        if (this.hasTransactionProcessingStrategyId) {
            if (this.transactionProcessingStrategyId != null) {
                object.getObject().put("transactionProcessingStrategyId", this.transactionProcessingStrategyId.getLiteral());
            } else {
                object.getObject().put("transactionProcessingStrategyId", (String) null);
            }
        }

        if (this.hasName) {
            object.getObject().put("name", this.name);
        }

        if (this.hasShortName) {
            object.getObject().put("shortName", shortName);
        }

        if (this.hasCurrencyCode) {
            object.getObject().put("currencyCode", this.currencyCode);
        }

        if (this.hasDigitsAfterDecimal) {
            object.getObject().put("digitsAfterDecimal", this.digitsAfterDecimal);
        }

        if (this.hasRepaymentEvery) {
            object.getObject().put("repaymentEvery", this.repaymentEvery);
        }

        if (this.hasRepaymentFrequencyType) {
            if (this.repaymentFrequencyType != null) {
                object.getObject().put("repaymentFrequencyType", this.repaymentFrequencyType.getLiteral());
            } else {
                object.getObject().put("repaymentFrequencyType", (String) null);
            }
        }

        return object;
    }

}
