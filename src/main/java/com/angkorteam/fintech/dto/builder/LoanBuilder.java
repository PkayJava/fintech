package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

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
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.dto.enums.loan.WhenType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class LoanBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public LoanBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public LoanBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public LoanBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Integer digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public LoanBuilder withDigitsAfterDecimal(Integer digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private Integer repaymentEvery;
    private boolean hasRepaymentEvery;

    public LoanBuilder withRepaymentEvery(Integer repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
        this.hasRepaymentEvery = true;
        return this;
    }

    private DayInYear daysInYearType;
    private boolean hasDaysInYearType;

    public LoanBuilder withDaysInYearType(DayInYear daysInYearType) {
        this.daysInYearType = daysInYearType;
        this.hasDaysInYearType = true;
        return this;
    }

    private DayInMonth daysInMonthType;
    private boolean hasDaysInMonthType;

    public LoanBuilder withDaysInMonthType(DayInMonth daysInMonthType) {
        this.daysInMonthType = daysInMonthType;
        this.hasDaysInMonthType = true;
        return this;
    }

    private boolean interestRecalculationEnabled;
    private boolean hasInterestRecalculationEnabled;

    public LoanBuilder withInterestRecalculationEnabled(boolean interestRecalculationEnabled) {
        this.interestRecalculationEnabled = interestRecalculationEnabled;
        this.hasInterestRecalculationEnabled = true;
        return this;
    }

    private Integer accountingRule;
    private boolean hasAccountingRule;

    public LoanBuilder withAccountingRule(Integer accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date startDate;
    private boolean hasStartDate;

    public LoanBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        this.hasStartDate = true;
        return this;
    }

    private Date closeDate;
    private boolean hasCloseDate;

    public LoanBuilder withCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        this.hasCloseDate = true;
        return this;
    }

    private String fundId;
    private boolean hasFundId;

    public LoanBuilder withFundId(String fundId) {
        this.fundId = fundId;
        this.hasFundId = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public LoanBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private boolean includeInBorrowerCycle;
    private boolean hasIncludeInBorrowerCycle;

    public LoanBuilder withIncludeInBorrowerCycle(boolean includeInBorrowerCycle) {
        this.includeInBorrowerCycle = includeInBorrowerCycle;
        this.hasIncludeInBorrowerCycle = true;
        return this;
    }

    private Integer installmentAmountInMultiplesOf;
    private Boolean hasInstallmentAmountInMultiplesOf;

    public LoanBuilder withInstallmentAmountInMultiplesOf(Integer installmentAmountInMultiplesOf) {
        this.installmentAmountInMultiplesOf = installmentAmountInMultiplesOf;
        this.hasInstallmentAmountInMultiplesOf = true;
        return this;
    }

    private Integer inMultiplesOf;
    private boolean hasInMultiplesOf;

    public LoanBuilder withInMultiplesOf(Integer inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private Double minPrincipal;
    private boolean hasMinPrincipal;

    public LoanBuilder withMinPrincipal(Double minPrincipal) {
        this.minPrincipal = minPrincipal;
        this.hasMinPrincipal = true;
        return this;
    }

    private Double maxPrincipal;
    private boolean hasMaxPrincipal;

    public LoanBuilder withMaxPrincipal(Double maxPrincipal) {
        this.maxPrincipal = maxPrincipal;
        this.hasMaxPrincipal = true;
        return this;
    }

    private Double principal;
    private boolean hasPrincipal;

    public LoanBuilder withPrincipal(Double principal) {
        this.principal = principal;
        this.hasPrincipal = true;
        return this;
    }

    private boolean useBorrowerCycle;
    private boolean hasUseBorrowerCycle;

    public LoanBuilder withUseBorrowerCycle(boolean useBorrowerCycle) {
        this.useBorrowerCycle = useBorrowerCycle;
        this.hasUseBorrowerCycle = true;
        return this;
    }

    private Double minNumberOfRepayments;
    private boolean hasMinNumberOfRepayments;

    public LoanBuilder withMinNumberOfRepayments(Double minNumberOfRepayments) {
        this.minNumberOfRepayments = minNumberOfRepayments;
        this.hasMinNumberOfRepayments = true;
        return this;
    }

    private Double numberOfRepayments;
    private boolean hasNumberOfRepayments;

    public LoanBuilder withNumberOfRepayments(Double numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
        this.hasNumberOfRepayments = true;
        return this;
    }

    private Double maxNumberOfRepayments;
    private boolean hasMaxNumberOfRepayments;

    public LoanBuilder withMaxNumberOfRepayments(Double maxNumberOfRepayments) {
        this.maxNumberOfRepayments = maxNumberOfRepayments;
        this.hasMaxNumberOfRepayments = true;
        return this;
    }

    private Double minInterestRatePerPeriod;
    private boolean hasMinInterestRatePerPeriod;

    public LoanBuilder withMinInterestRatePerPeriod(Double minInterestRatePerPeriod) {
        this.minInterestRatePerPeriod = minInterestRatePerPeriod;
        this.hasMinInterestRatePerPeriod = true;
        return this;
    }

    private Double interestRatePerPeriod;
    private boolean hasInterestRatePerPeriod;

    public LoanBuilder withInterestRatePerPeriod(Double interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
        this.hasInterestRatePerPeriod = true;
        return this;
    }

    private Double maxInterestRatePerPeriod;
    private boolean hasMaxInterestRatePerPeriod;

    public LoanBuilder withMaxInterestRatePerPeriod(Double maxInterestRatePerPeriod) {
        this.maxInterestRatePerPeriod = maxInterestRatePerPeriod;
        this.hasMaxInterestRatePerPeriod = true;
        return this;
    }

    private NominalInterestRateScheduleType interestRateFrequencyType;
    private boolean hasInterestRateFrequencyType;

    public LoanBuilder withInterestRateFrequencyType(NominalInterestRateScheduleType interestRateFrequencyType) {
        this.interestRateFrequencyType = interestRateFrequencyType;
        this.hasInterestRateFrequencyType = true;
        return this;
    }

    private Integer minimumDaysBetweenDisbursalAndFirstRepayment;
    private boolean hasMinimumDaysBetweenDisbursalAndFirstRepayment;

    public LoanBuilder withMinimumDaysBetweenDisbursalAndFirstRepayment(Integer minimumDaysBetweenDisbursalAndFirstRepayment) {
        this.minimumDaysBetweenDisbursalAndFirstRepayment = minimumDaysBetweenDisbursalAndFirstRepayment;
        this.hasMinimumDaysBetweenDisbursalAndFirstRepayment = true;
        return this;
    }

    private LockInType repaymentFrequencyType;
    private boolean hasRepaymentFrequencyType;

    public LoanBuilder withRepaymentFrequencyType(LockInType repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
        this.hasRepaymentFrequencyType = true;
        return this;
    }

    private boolean linkedToFloatingInterestRates;
    private boolean hasLinkedToFloatingInterestRates;

    public LoanBuilder withLinkedToFloatingInterestRates(boolean linkedToFloatingInterestRates) {
        this.linkedToFloatingInterestRates = linkedToFloatingInterestRates;
        this.hasLinkedToFloatingInterestRates = true;
        return this;
    }

    private boolean floatingInterestRateCalculationAllowed;
    private boolean hasFloatingInterestRateCalculationAllowed;

    public LoanBuilder withFloatingInterestRateCalculationAllowed(boolean floatingInterestRateCalculationAllowed) {
        this.floatingInterestRateCalculationAllowed = floatingInterestRateCalculationAllowed;
        this.hasFloatingInterestRateCalculationAllowed = true;
        return this;
    }

    private Double interestRateDifferential;
    private boolean hasInterestRateDifferential;

    public LoanBuilder withInterestRateDifferential(Double interestRateDifferential) {
        this.interestRateDifferential = interestRateDifferential;
        this.hasInterestRateDifferential = true;
        return this;
    }

    private String floatingRatesId;
    private boolean hasFloatingRatesId;

    public LoanBuilder withFloatingRatesId(String floatingRatesId) {
        this.floatingRatesId = floatingRatesId;
        this.hasFloatingRatesId = true;
        return this;
    }

    private Double maxDifferentialLendingRate;
    private boolean hasMaxDifferentialLendingRate;

    public LoanBuilder withMaxDifferentialLendingRate(Double maxDifferentialLendingRate) {
        this.maxDifferentialLendingRate = maxDifferentialLendingRate;
        this.hasMaxDifferentialLendingRate = true;
        return this;
    }

    private Double defaultDifferentialLendingRate;
    private boolean hasDefaultDifferentialLendingRate;

    public LoanBuilder withDefaultDifferentialLendingRate(Double defaultDifferentialLendingRate) {
        this.defaultDifferentialLendingRate = defaultDifferentialLendingRate;
        this.hasDefaultDifferentialLendingRate = true;
        return this;
    }

    private Double minDifferentialLendingRate;
    private boolean hasMinDifferentialLendingRate;

    public LoanBuilder withMinDifferentialLendingRate(Double minDifferentialLendingRate) {
        this.minDifferentialLendingRate = minDifferentialLendingRate;
        this.hasMinDifferentialLendingRate = true;
        return this;
    }

    private List<Map<String, Object>> principalVariationsForBorrowerCycle = Lists.newArrayList();
    private boolean hasPrincipalVariationsForBorrowerCycle;

    public LoanBuilder withPrincipalVariationsForBorrowerCycle(WhenType valueConditionType, Integer borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
        Map<String, Object> cycle = Maps.newHashMap();
        cycle.put("valueConditionType", valueConditionType.getLiteral());
        cycle.put("borrowerCycleNumber", borrowerCycleNumber);
        cycle.put("minValue", minValue);
        cycle.put("defaultValue", defaultValue);
        cycle.put("maxValue", maxValue);
        this.principalVariationsForBorrowerCycle.add(cycle);
        this.hasPrincipalVariationsForBorrowerCycle = true;
        return this;
    }

    private List<Map<String, Object>> numberOfRepaymentVariationsForBorrowerCycle = Lists.newArrayList();
    private boolean hasNumberOfRepaymentVariationsForBorrowerCycle;

    public LoanBuilder withNumberOfRepaymentVariationsForBorrowerCycle(WhenType valueConditionType, Integer borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
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

    public LoanBuilder withInterestRateVariationsForBorrowerCycle(WhenType valueConditionType, Integer borrowerCycleNumber, Double minValue, Double defaultValue, Double maxValue) {
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

    public LoanBuilder withAmortizationType(Amortization amortizationType) {
        this.amortizationType = amortizationType;
        this.hasAmortizationType = true;
        return this;
    }

    private InterestMethod interestType;
    private boolean hasInterestType;

    public LoanBuilder withInterestType(InterestMethod interestType) {
        this.interestType = interestType;
        this.hasInterestType = true;
        return this;
    }

    private boolean allowPartialPeriodInterestCalcualtion;
    private boolean hasAllowPartialPeriodInterestCalcualtion;

    public LoanBuilder withAllowPartialPeriodInterestCalcualtion(boolean allowPartialPeriodInterestCalcualtion) {
        this.allowPartialPeriodInterestCalcualtion = allowPartialPeriodInterestCalcualtion;
        this.hasAllowPartialPeriodInterestCalcualtion = true;
        return this;
    }

    private InterestCalculationPeriod interestCalculationPeriodType;
    private boolean hasInterestCalculationPeriodType;

    public LoanBuilder withInterestCalculationPeriodType(InterestCalculationPeriod interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.hasInterestCalculationPeriodType = true;
        return this;
    }

    private RepaymentStrategy transactionProcessingStrategyId;
    private boolean hasTransactionProcessingStrategyId;

    public LoanBuilder withTransactionProcessingStrategyId(RepaymentStrategy repaymentStrategy) {
        this.transactionProcessingStrategyId = repaymentStrategy;
        this.hasTransactionProcessingStrategyId = true;
        return this;
    }

    private Integer graceOnPrincipalPayment;
    private boolean hasGraceOnPrincipalPayment;

    public LoanBuilder withGraceOnPrincipalPayment(Integer graceOnPrincipalPayment) {
        this.graceOnPrincipalPayment = graceOnPrincipalPayment;
        this.hasGraceOnPrincipalPayment = true;
        return this;
    }

    private Integer graceOnInterestPayment;
    private boolean hasGraceOnInterestPayment;

    public LoanBuilder withGraceOnInterestPayment(Integer graceOnInterestPayment) {
        this.graceOnInterestPayment = graceOnInterestPayment;
        this.hasGraceOnInterestPayment = true;
        return this;
    }

    private Integer graceOnInterestCharged;
    private boolean hasGraceOnInterestCharged;

    public LoanBuilder withGraceOnInterestCharged(Integer graceOnInterestCharged) {
        this.graceOnInterestCharged = graceOnInterestCharged;
        this.hasGraceOnInterestCharged = true;
        return this;
    }

    private Double inArrearsTolerance;
    private boolean hasInArrearsTolerance;

    public LoanBuilder withInArrearsTolerance(Double inArrearsTolerance) {
        this.inArrearsTolerance = inArrearsTolerance;
        this.hasInArrearsTolerance = true;
        return this;
    }

    private boolean canDefineInstallmentAmount;
    private boolean hasCanDefineInstallmentAmount;

    public LoanBuilder withCanDefineInstallmentAmount(boolean canDefineInstallmentAmount) {
        this.canDefineInstallmentAmount = canDefineInstallmentAmount;
        this.hasCanDefineInstallmentAmount = true;
        return this;
    }

    private Integer graceOnArrearsAgeing;
    private boolean hasGraceOnArrearsAgeing;

    public LoanBuilder withGraceOnArrearsAgeing(Integer graceOnArrearsAgeing) {
        this.graceOnArrearsAgeing = graceOnArrearsAgeing;
        this.hasGraceOnArrearsAgeing = true;
        return this;
    }

    private Integer overdueDaysForNPA;
    private boolean hasOverdueDaysForNPA;

    public LoanBuilder withOverdueDaysForNPA(Integer overdueDaysForNPA) {
        this.overdueDaysForNPA = overdueDaysForNPA;
        this.hasOverdueDaysForNPA = true;
        return this;
    }

    private Double principalThresholdForLastInstallment;
    private boolean hasPrincipalThresholdForLastInstallment;

    public LoanBuilder withPrincipalThresholdForLastInstallment(Double principalThresholdForLastInstallment) {
        this.principalThresholdForLastInstallment = principalThresholdForLastInstallment;
        this.hasPrincipalThresholdForLastInstallment = true;
        return this;
    }

    private Integer minimumGap;
    private boolean hasMinimumGap;

    public LoanBuilder withMinimumGap(Integer minimumGap) {
        this.minimumGap = minimumGap;
        this.hasMinimumGap = true;
        return this;
    }

    private Integer maximumGap;
    private boolean hasMaximumGap;

    public LoanBuilder withMaximumGap(Integer maximumGap) {
        this.maximumGap = maximumGap;
        this.hasMaximumGap = true;
        return this;
    }

    private boolean accountMovesOutOfNPAOnlyOnArrearsCompletion;
    private boolean hasAccountMovesOutOfNPAOnlyOnArrearsCompletion;

    public LoanBuilder withAccountMovesOutOfNPAOnlyOnArrearsCompletion(boolean accountMovesOutOfNPAOnlyOnArrearsCompletion) {
        this.accountMovesOutOfNPAOnlyOnArrearsCompletion = accountMovesOutOfNPAOnlyOnArrearsCompletion;
        this.hasAccountMovesOutOfNPAOnlyOnArrearsCompletion = true;
        return this;
    }

    private boolean allowVariableInstallments;
    private boolean hasAllowVariableInstallments;

    public LoanBuilder withAllowVariableInstallments(boolean allowVariableInstallments) {
        this.allowVariableInstallments = allowVariableInstallments;
        this.hasAllowVariableInstallments = true;
        return this;
    }

    private boolean canUseForTopup;
    private boolean hasCanUseForTopup;

    public LoanBuilder withCanUseForTopup(boolean canUseForTopup) {
        this.canUseForTopup = canUseForTopup;
        this.hasCanUseForTopup = true;
        return this;
    }

    private ClosureInterestCalculationRule preClosureInterestCalculationStrategy;
    private boolean hasPreClosureInterestCalculationStrategy;

    public LoanBuilder withPreClosureInterestCalculationStrategy(ClosureInterestCalculationRule preClosureInterestCalculationStrategy) {
        this.preClosureInterestCalculationStrategy = preClosureInterestCalculationStrategy;
        this.hasPreClosureInterestCalculationStrategy = true;
        return this;
    }

    private AdvancePaymentsAdjustmentType rescheduleStrategyMethod;
    private boolean hasRescheduleStrategyMethod;

    public LoanBuilder withRescheduleStrategyMethod(AdvancePaymentsAdjustmentType rescheduleStrategyMethod) {
        this.rescheduleStrategyMethod = rescheduleStrategyMethod;
        this.hasRescheduleStrategyMethod = true;
        return this;
    }

    private InterestRecalculationCompound interestRecalculationCompoundingMethod;
    private boolean hasInterestRecalculationCompoundingMethod;

    public LoanBuilder withInterestRecalculationCompoundingMethod(InterestRecalculationCompound interestRecalculationCompoundingMethod) {
        this.interestRecalculationCompoundingMethod = interestRecalculationCompoundingMethod;
        this.hasInterestRecalculationCompoundingMethod = true;
        return this;
    }

    private Frequency recalculationCompoundingFrequencyType;
    private boolean hasRecalculationCompoundingFrequencyType;

    public LoanBuilder withRecalculationCompoundingFrequencyType(Frequency recalculationCompoundingFrequencyType) {
        this.recalculationCompoundingFrequencyType = recalculationCompoundingFrequencyType;
        this.hasRecalculationCompoundingFrequencyType = true;
        return this;
    }

    private Integer recalculationCompoundingFrequencyInterval;
    private boolean hasRecalculationCompoundingFrequencyInterval;

    public LoanBuilder withRecalculationCompoundingFrequencyInterval(Integer recalculationCompoundingFrequencyInterval) {
        this.recalculationCompoundingFrequencyInterval = recalculationCompoundingFrequencyInterval;
        this.hasRecalculationCompoundingFrequencyInterval = true;
        return this;
    }

    private FrequencyType recalculationCompoundingFrequencyNthDayType;
    private boolean hasRecalculationCompoundingFrequencyNthDayType;

    public LoanBuilder withRecalculationCompoundingFrequencyNthDayType(FrequencyType recalculationCompoundingFrequencyNthDayType) {
        this.recalculationCompoundingFrequencyNthDayType = recalculationCompoundingFrequencyNthDayType;
        this.hasRecalculationCompoundingFrequencyNthDayType = true;
        return this;
    }

    private FrequencyDay recalculationCompoundingFrequencyDayOfWeekType;
    private boolean hasRecalculationCompoundingFrequencyDayOfWeekType;

    public LoanBuilder withRecalculationCompoundingFrequencyDayOfWeekType(FrequencyDay recalculationCompoundingFrequencyDayOfWeekType) {
        this.recalculationCompoundingFrequencyDayOfWeekType = recalculationCompoundingFrequencyDayOfWeekType;
        this.hasRecalculationCompoundingFrequencyDayOfWeekType = true;
        return this;
    }

    private boolean arrearsBasedOnOriginalSchedule;
    private boolean hasArrearsBasedOnOriginalSchedule;

    public LoanBuilder withArrearsBasedOnOriginalSchedule(boolean arrearsBasedOnOriginalSchedule) {
        this.arrearsBasedOnOriginalSchedule = arrearsBasedOnOriginalSchedule;
        this.hasArrearsBasedOnOriginalSchedule = true;
        return this;
    }

    private Integer recalculationRestFrequencyInterval;
    private boolean hasRecalculationRestFrequencyInterval;

    public LoanBuilder withRecalculationRestFrequencyInterval(Integer recalculationRestFrequencyInterval) {
        this.recalculationRestFrequencyInterval = recalculationRestFrequencyInterval;
        this.hasRecalculationRestFrequencyInterval = true;
        return this;
    }

    private FrequencyDay recalculationRestFrequencyDayOfWeekType;
    private boolean hasRecalculationRestFrequencyDayOfWeekType;

    public LoanBuilder withRecalculationRestFrequencyDayOfWeekType(FrequencyDay recalculationRestFrequencyDayOfWeekType) {
        this.recalculationRestFrequencyDayOfWeekType = recalculationRestFrequencyDayOfWeekType;
        this.hasRecalculationRestFrequencyDayOfWeekType = true;
        return this;
    }

    private FrequencyType recalculationRestFrequencyNthDayType;
    private boolean hasRecalculationRestFrequencyNthDayType;

    public LoanBuilder withRecalculationRestFrequencyNthDayType(FrequencyType recalculationRestFrequencyNthDayType) {
        this.recalculationRestFrequencyNthDayType = recalculationRestFrequencyNthDayType;
        this.hasRecalculationRestFrequencyNthDayType = true;
        return this;
    }

    private Frequency recalculationRestFrequencyType;
    private boolean hasRecalculationRestFrequencyType;

    public LoanBuilder withRecalculationRestFrequencyType(Frequency recalculationRestFrequencyType) {
        this.recalculationRestFrequencyType = recalculationRestFrequencyType;
        this.hasRecalculationRestFrequencyType = true;
        return this;
    }

    private boolean holdGuaranteeFunds;
    private boolean hasHoldGuaranteeFunds;

    public LoanBuilder withHoldGuaranteeFunds(boolean holdGuaranteeFunds) {
        this.holdGuaranteeFunds = holdGuaranteeFunds;
        this.hasHoldGuaranteeFunds = true;
        return this;
    }

    private Double mandatoryGuarantee;
    private boolean hasMandatoryGuarantee;

    public LoanBuilder withMandatoryGuarantee(Double mandatoryGuarantee) {
        this.mandatoryGuarantee = mandatoryGuarantee;
        this.hasMandatoryGuarantee = true;
        return this;
    }

    private Double minimumGuaranteeFromGuarantor;
    private boolean hasMinimumGuaranteeFromGuarantor;

    public LoanBuilder withMinimumGuaranteeFromGuarantor(Double minimumGuaranteeFromGuarantor) {
        this.minimumGuaranteeFromGuarantor = minimumGuaranteeFromGuarantor;
        this.hasMinimumGuaranteeFromGuarantor = true;
        return this;
    }

    private Double minimumGuaranteeFromOwnFunds;
    private boolean hasMinimumGuaranteeFromOwnFunds;

    public LoanBuilder withMinimumGuaranteeFromOwnFunds(Double minimumGuaranteeFromOwnFunds) {
        this.minimumGuaranteeFromOwnFunds = minimumGuaranteeFromOwnFunds;
        this.hasMinimumGuaranteeFromOwnFunds = true;
        return this;
    }

    private boolean multiDisburseLoan;
    private boolean hasMultiDisburseLoan;

    public LoanBuilder withMultiDisburseLoan(boolean multiDisburseLoan) {
        this.multiDisburseLoan = multiDisburseLoan;
        this.hasMultiDisburseLoan = true;
        return this;
    }

    private Integer maxTrancheCount;
    private boolean hasMaxTrancheCount;

    public LoanBuilder withMaxTrancheCount(Integer maxTrancheCount) {
        this.maxTrancheCount = maxTrancheCount;
        this.hasMaxTrancheCount = true;
        return this;
    }

    private Double outstandingLoanBalance;
    private boolean hasOutstandingLoanBalance;

    public LoanBuilder withOutstandingLoanBalance(Double outstandingLoanBalance) {
        this.outstandingLoanBalance = outstandingLoanBalance;
        this.hasOutstandingLoanBalance = true;
        return this;
    }

    private JSONObject allowAttributeOverrides;
    private boolean hasAllowAttributeOverrides;

    public LoanBuilder withAllowAttributeOverrides(JSONObject allowAttributeOverrides) {
        this.allowAttributeOverrides = allowAttributeOverrides;
        this.hasAllowAttributeOverrides = true;
        return this;
    }

    private String fundSourceAccountId;
    private boolean hasFundSourceAccountId;

    public LoanBuilder withFundSourceAccountId(String fundSourceAccountId) {
        this.fundSourceAccountId = fundSourceAccountId;
        this.hasFundSourceAccountId = true;
        return this;
    }

    private String loanPortfolioAccountId;
    private boolean hasLoanPortfolioAccountId;

    public LoanBuilder withLoanPortfolioAccountId(String loanPortfolioAccountId) {
        this.loanPortfolioAccountId = loanPortfolioAccountId;
        this.hasLoanPortfolioAccountId = true;
        return this;
    }

    private String receivableInterestAccountId;
    private boolean hasReceivableInterestAccountId;

    public LoanBuilder withReceivableInterestAccountId(String receivableInterestAccountId) {
        this.receivableInterestAccountId = receivableInterestAccountId;
        this.hasReceivableInterestAccountId = true;
        return this;
    }

    private String interestOnLoanAccountId;
    private boolean hasInterestOnLoanAccountId;

    public LoanBuilder withInterestOnLoanAccountId(String interestOnLoanAccountId) {
        this.interestOnLoanAccountId = interestOnLoanAccountId;
        this.hasInterestOnLoanAccountId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public LoanBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    private String incomeFromPenaltyAccountId;
    private boolean hasIncomeFromPenaltyAccountId;

    public LoanBuilder withIncomeFromPenaltyAccountId(String incomeFromPenaltyAccountId) {
        this.incomeFromPenaltyAccountId = incomeFromPenaltyAccountId;
        this.hasIncomeFromPenaltyAccountId = true;
        return this;
    }

    private String incomeFromRecoveryAccountId;
    private boolean hasIncomeFromRecoveryAccountId;

    public LoanBuilder withIncomeFromRecoveryAccountId(String incomeFromRecoveryAccountId) {
        this.incomeFromRecoveryAccountId = incomeFromRecoveryAccountId;
        this.hasIncomeFromRecoveryAccountId = true;
        return this;
    }

    private String writeOffAccountId;
    private boolean hasWriteOffAccountId;

    public LoanBuilder withWriteOffAccountId(String writeOffAccountId) {
        this.writeOffAccountId = writeOffAccountId;
        this.hasWriteOffAccountId = true;
        return this;
    }

    private String overpaymentLiabilityAccountId;
    private boolean hasOverpaymentLiabilityAccountId;

    public LoanBuilder withOverpaymentLiabilityAccountId(String overpaymentLiabilityAccountId) {
        this.overpaymentLiabilityAccountId = overpaymentLiabilityAccountId;
        this.hasOverpaymentLiabilityAccountId = true;
        return this;
    }

    private String transfersInSuspenseAccountId;
    private boolean hasTransfersInSuspenseAccountId;

    public LoanBuilder withTransfersInSuspenseAccountId(String transfersInSuspenseAccountId) {
        this.transfersInSuspenseAccountId = transfersInSuspenseAccountId;
        this.hasTransfersInSuspenseAccountId = true;
        return this;
    }

    private String receivableFeeAccountId;
    private boolean hasReceivableFeeAccountId;

    public LoanBuilder withReceivableFeeAccountId(String receivableFeeAccountId) {
        this.receivableFeeAccountId = receivableFeeAccountId;
        this.hasReceivableFeeAccountId = true;
        return this;
    }

    private String receivablePenaltyAccountId;
    private boolean hasReceivablePenaltyAccountId;

    public LoanBuilder withReceivablePenaltyAccountId(String receivablePenaltyAccountId) {
        this.receivablePenaltyAccountId = receivablePenaltyAccountId;
        this.hasReceivablePenaltyAccountId = true;
        return this;
    }

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public LoanBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("paymentTypeId", paymentTypeId);
        item.put("fundSourceAccountId", fundSourceAccountId);
        this.paymentChannelToFundSourceMappings.add(item);
        this.hasPaymentChannelToFundSourceMappings = true;
        return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public LoanBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.feeToIncomeAccountMappings.add(item);
        this.hasFeeToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public LoanBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.penaltyToIncomeAccountMappings.add(item);
        this.hasPenaltyToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public LoanBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
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

        if (this.hasPrincipalThresholdForLastInstallment) {
            object.getObject().put("principalThresholdForLastInstallment", this.principalThresholdForLastInstallment);
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

        if (this.hasGraceOnPrincipalPayment) {
            object.getObject().put("graceOnPrincipalPayment", this.graceOnPrincipalPayment);
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

        if (this.hasPrincipalVariationsForBorrowerCycle) {
            object.getObject().put("principalVariationsForBorrowerCycle", this.principalVariationsForBorrowerCycle);
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

        if (this.hasPrincipal) {
            object.getObject().put("principal", this.principal);
        }

        if (this.hasUseBorrowerCycle) {
            object.getObject().put("useBorrowerCycle", this.useBorrowerCycle);
        }

        if (this.hasMaxPrincipal) {
            object.getObject().put("maxPrincipal", this.maxPrincipal);
        }

        if (this.hasMinPrincipal) {
            object.getObject().put("minPrincipal", this.minPrincipal);
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
            object.getObject().put("accountingRule", this.accountingRule);
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
