package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.RepaidOn;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class LoanAccountBuilder implements Serializable {

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanAccountBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanAccountBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String monthDayFormat = "dd MMMM";
//    private boolean hasMonthDayFormat = true;
//
//    public LoanAccountBuilder withMonthDayFormat(String monthDayFormat) {
//        this.monthDayFormat = monthDayFormat;
//        this.hasMonthDayFormat = true;
//        return this;
//    }

    private String clientId;
    private boolean hasClientId;

    public LoanAccountBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String productId;
    private boolean hasProductId;

    public LoanAccountBuilder withProductId(String productId) {
        this.productId = productId;
        this.hasProductId = true;
        return this;
    }

    private List<Map<String, Object>> disbursementData;
    private boolean hasDisbursementData;

    public LoanAccountBuilder withDisbursementData(List<Map<String, Object>> disbursementData) {
        this.disbursementData = disbursementData;
        this.hasDisbursementData = true;
        return this;
    }

    private String fundId;
    private boolean hasFundId;

    public LoanAccountBuilder withFundId(String fundId) {
        this.fundId = fundId;
        this.hasFundId = true;
        return this;
    }

    private Double principal;
    private boolean hasPrincipal;

    public LoanAccountBuilder withPrincipal(Double principal) {
        this.principal = principal;
        this.hasPrincipal = true;
        return this;
    }

    private Long loanTermFrequency;
    private boolean hasLoanTermFrequency;

    public LoanAccountBuilder withLoanTermFrequency(Long loanTermFrequency) {
        this.loanTermFrequency = loanTermFrequency;
        this.hasLoanTermFrequency = true;
        return this;
    }

    private ChargeFrequency loanTermFrequencyType;
    private boolean hasLoanTermFrequencyType;

    public LoanAccountBuilder withLoanTermFrequencyType(ChargeFrequency loanTermFrequencyType) {
        this.loanTermFrequencyType = loanTermFrequencyType;
        this.hasLoanTermFrequencyType = true;
        return this;
    }

    private Long numberOfRepayments;
    private boolean hasNumberOfRepayments;

    public LoanAccountBuilder withNumberOfRepayments(Long numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
        this.hasNumberOfRepayments = true;
        return this;
    }

    private Long repaymentEvery;
    private boolean hasRepaymentEvery;

    public LoanAccountBuilder withRepaymentEvery(Long repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
        this.hasRepaymentEvery = true;
        return this;
    }

    private ChargeFrequency repaymentFrequencyType;
    private boolean hasRepaymentFrequencyType;

    public LoanAccountBuilder withRepaymentFrequencyType(ChargeFrequency repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
        this.hasRepaymentFrequencyType = true;
        return this;
    }

    private Double interestRatePerPeriod;
    private boolean hasInterestRatePerPeriod;

    public LoanAccountBuilder withInterestRatePerPeriod(Double interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
        this.hasInterestRatePerPeriod = true;
        return this;
    }

    private Amortization amortizationType;
    private boolean hasAmortizationType;

    public LoanAccountBuilder withAmortizationType(Amortization amortizationType) {
        this.amortizationType = amortizationType;
        this.hasAmortizationType = true;
        return this;
    }

    private InterestMethod interestType;
    private boolean hasInterestType;

    public LoanAccountBuilder withInterestType(InterestMethod interestType) {
        this.interestType = interestType;
        this.hasInterestType = true;
        return this;
    }

    private InterestCalculationPeriod interestCalculationPeriodType;
    private boolean hasInterestCalculationPeriodType;

    public LoanAccountBuilder withInterestCalculationPeriodType(InterestCalculationPeriod interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.hasInterestCalculationPeriodType = true;
        return this;
    }

    private Boolean allowPartialPeriodInterestCalcualtion;
    private boolean hasAllowPartialPeriodInterestCalcualtion;

    public LoanAccountBuilder withAllowPartialPeriodInterestCalcualtion(Boolean allowPartialPeriodInterestCalcualtion) {
        this.allowPartialPeriodInterestCalcualtion = allowPartialPeriodInterestCalcualtion;
        this.hasAllowPartialPeriodInterestCalcualtion = true;
        return this;
    }

    private RepaymentStrategy transactionProcessingStrategyId;
    private boolean hasTransactionProcessingStrategyId;

    public LoanAccountBuilder withTransactionProcessingStrategyId(RepaymentStrategy transactionProcessingStrategyId) {
        this.transactionProcessingStrategyId = transactionProcessingStrategyId;
        this.hasTransactionProcessingStrategyId = true;
        return this;
    }

    private String loanOfficerId;
    private boolean hasLoanOfficerId;

    public LoanAccountBuilder withLoanOfficerId(String loanOfficerId) {
        this.loanOfficerId = loanOfficerId;
        this.hasLoanOfficerId = true;
        return this;
    }

    private String loanType = "individual";
    private boolean hasLoanType = true;

    public LoanAccountBuilder withLoanType(String loanType) {
        this.loanType = loanType;
        this.hasLoanType = true;
        return this;
    }

    private Date expectedDisbursementDate;
    private boolean hasExpectedDisbursementDate;

    public LoanAccountBuilder withExpectedDisbursementDate(Date expectedDisbursementDate) {
        this.expectedDisbursementDate = expectedDisbursementDate;
        this.hasExpectedDisbursementDate = true;
        return this;
    }

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    public LoanAccountBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    private List<Map<String, Object>> charges = new ArrayList<>();
    private boolean hasCharges;

    public LoanAccountBuilder withCharge(String chargeId, Double amount, Date feeOnMonthDay, Date dueDate, Long feeInterval) {
        Map<String, Object> charge = Maps.newHashMap();
        charge.put("chargeId", chargeId);
        charge.put("amount", amount);
        if (feeOnMonthDay != null) {
            charge.put("feeOnMonthDay", DateFormatUtils.format(feeOnMonthDay, this.monthDayFormat));
        }
        if (dueDate != null) {
            charge.put("dueDate", DateFormatUtils.format(dueDate, this.dateFormat));
        }
        if (feeInterval != null) {
            charge.put("feeInterval", feeInterval);
        }
        this.charges.add(charge);
        this.hasCharges = true;
        return this;
    }

    private FrequencyDay repaymentFrequencyDayOfWeekType;
    private boolean hasRepaymentFrequencyDayOfWeekType;

    public LoanAccountBuilder withRepaymentFrequencyDayOfWeekType(FrequencyDay repaymentFrequencyDayOfWeekType) {
        this.repaymentFrequencyDayOfWeekType = repaymentFrequencyDayOfWeekType;
        this.hasRepaymentFrequencyDayOfWeekType = true;
        return this;
    }

    private RepaidOn repaymentFrequencyNthDayType;
    private boolean hasRepaymentFrequencyNthDayType;

    public LoanAccountBuilder withRepaymentFrequencyNthDayType(RepaidOn repaymentFrequencyNthDayType) {
        this.repaymentFrequencyNthDayType = repaymentFrequencyNthDayType;
        this.hasRepaymentFrequencyNthDayType = true;
        return this;
    }

    private String externalId;
    private boolean hasExternalId;

    public LoanAccountBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    private Boolean createStandingInstructionAtDisbursement;
    private boolean hasCreateStandingInstructionAtDisbursement;

    public LoanAccountBuilder withCreateStandingInstructionAtDisbursement(Boolean createStandingInstructionAtDisbursement) {
        this.createStandingInstructionAtDisbursement = createStandingInstructionAtDisbursement;
        this.hasCreateStandingInstructionAtDisbursement = true;
        return this;
    }

    private String linkAccountId;
    private boolean hasLinkAccountId;

    public LoanAccountBuilder withLinkAccountId(String linkAccountId) {
        this.linkAccountId = linkAccountId;
        this.hasLinkAccountId = true;
        return this;
    }

    private String loanPurposeId;
    private boolean hasLoanPurposeId;

    public LoanAccountBuilder withLoanPurposeId(String loanPurposeId) {
        this.loanPurposeId = loanPurposeId;
        this.hasLoanPurposeId = true;
        return this;
    }

    private List<Map<String, Object>> collateral = new ArrayList<>();
    private boolean hasCollateral;

    public LoanAccountBuilder withCollateral(String type, Double value, String description) {
        Map<String, Object> collateral = new HashMap<>();
        collateral.put("type", type);
        collateral.put("value", value);
        collateral.put("description", description);
        this.collateral.add(collateral);
        this.hasCollateral = true;
        return this;
    }

    private Date interestChargedFromDate;
    private boolean hasInterestChargedFromDate;

    public LoanAccountBuilder withInterestChargedFromDate(Date interestChargedFromDate) {
        this.interestChargedFromDate = interestChargedFromDate;
        this.hasInterestChargedFromDate = true;
        return this;
    }

    private Date repaymentsStartingFromDate;
    private boolean hasRepaymentsStartingFromDate;

    public LoanAccountBuilder withRepaymentsStartingFromDate(Date repaymentsStartingFromDate) {
        this.repaymentsStartingFromDate = repaymentsStartingFromDate;
        this.hasRepaymentsStartingFromDate = true;
        return this;
    }

    private Double inArrearsTolerance;
    private boolean hasInArrearsTolerance;

    public LoanAccountBuilder withInArrearsTolerance(Double inArrearsTolerance) {
        this.inArrearsTolerance = inArrearsTolerance;
        this.hasInArrearsTolerance = true;
        return this;
    }

    private Long graceOnInterestCharged;
    private boolean hasGraceOnInterestCharged;

    public LoanAccountBuilder withGraceOnInterestCharged(Long graceOnInterestCharged) {
        this.graceOnInterestCharged = graceOnInterestCharged;
        this.hasGraceOnInterestCharged = true;
        return this;
    }

    private Long graceOnPrincipalPayment;
    private boolean hasGraceOnPrincipalPayment;

    public LoanAccountBuilder withGraceOnPrincipalPayment(Long graceOnPrincipalPayment) {
        this.graceOnPrincipalPayment = graceOnPrincipalPayment;
        this.hasGraceOnPrincipalPayment = true;
        return this;
    }

    private Long graceOnInterestPayment;
    private boolean hasGraceOnInterestPayment;

    public LoanAccountBuilder withGraceOnInterestPayment(Long graceOnInterestPayment) {
        this.graceOnInterestPayment = graceOnInterestPayment;
        this.hasGraceOnInterestPayment = true;
        return this;
    }

    private Long graceOnArrearsAgeing;
    private boolean hasGraceOnArrearsAgeing;

    public LoanAccountBuilder withGraceOnArrearsAgeing(Long graceOnArrearsAgeing) {
        this.graceOnArrearsAgeing = graceOnArrearsAgeing;
        this.hasGraceOnArrearsAgeing = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasGraceOnArrearsAgeing) {
            object.getObject().put("graceOnArrearsAgeing", this.graceOnArrearsAgeing);
        }

        if (this.hasGraceOnInterestPayment) {
            object.getObject().put("graceOnInterestPayment", this.graceOnInterestPayment);
        }

        if (this.hasGraceOnPrincipalPayment) {
            object.getObject().put("graceOnPrincipalPayment", this.graceOnPrincipalPayment);
        }

        if (this.hasGraceOnInterestCharged) {
            object.getObject().put("graceOnInterestCharged", this.graceOnInterestCharged);
        }

        if (this.hasInArrearsTolerance) {
            object.getObject().put("inArrearsTolerance", this.inArrearsTolerance);
        }

        if (this.hasRepaymentFrequencyDayOfWeekType) {
            object.getObject().put("repaymentFrequencyDayOfWeekType", this.repaymentFrequencyDayOfWeekType.getLiteral());
        }

        if (this.hasRepaymentFrequencyNthDayType) {
            object.getObject().put("repaymentFrequencyNthDayType", this.repaymentFrequencyNthDayType.getLiteral());
        }

        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }

        if (this.hasCreateStandingInstructionAtDisbursement) {
            object.getObject().put("createStandingInstructionAtDisbursement", this.createStandingInstructionAtDisbursement);
        }

        if (this.hasLinkAccountId) {
            object.getObject().put("linkAccountId", this.linkAccountId);
        }

        if (this.hasLoanPurposeId) {
            object.getObject().put("loanPurposeId", this.loanPurposeId);
        }

        if (this.hasCollateral) {
            object.getObject().put("collateral", this.collateral);
        }

        if (this.hasInterestChargedFromDate) {
            object.getObject().put("interestChargedFromDate", DateFormatUtils.format(this.interestChargedFromDate, this.dateFormat));
        }

        if (this.hasRepaymentsStartingFromDate) {
            object.getObject().put("repaymentsStartingFromDate", DateFormatUtils.format(this.repaymentsStartingFromDate, this.dateFormat));
        }

        if (this.hasLoanTermFrequency) {
            object.getObject().put("loanTermFrequency", this.loanTermFrequency);
        }

        if (this.hasLoanTermFrequencyType) {
            object.getObject().put("loanTermFrequencyType", this.loanTermFrequencyType.getLiteral());
        }

        if (this.hasNumberOfRepayments) {
            object.getObject().put("numberOfRepayments", this.numberOfRepayments);
        }

        if (this.hasRepaymentEvery) {
            object.getObject().put("repaymentEvery", this.repaymentEvery);
        }

        if (this.hasRepaymentFrequencyType) {
            object.getObject().put("repaymentFrequencyType", this.repaymentFrequencyType.getLiteral());
        }

        if (this.hasInterestRatePerPeriod) {
            object.getObject().put("interestRatePerPeriod", this.interestRatePerPeriod);
        }

        if (this.hasAmortizationType) {
            object.getObject().put("amortizationType", this.amortizationType.getLiteral());
        }

        if (this.hasInterestType) {
            object.getObject().put("interestType", this.interestType.getLiteral());
        }

        if (this.hasInterestCalculationPeriodType) {
            object.getObject().put("interestCalculationPeriodType", this.interestCalculationPeriodType.getLiteral());
        }

        if (this.hasAllowPartialPeriodInterestCalcualtion) {
            object.getObject().put("allowPartialPeriodInterestCalcualtion", this.allowPartialPeriodInterestCalcualtion);
        }

        if (this.hasTransactionProcessingStrategyId) {
            object.getObject().put("transactionProcessingStrategyId", this.transactionProcessingStrategyId.getLiteral());
        }

        if (this.hasLoanOfficerId) {
            object.getObject().put("loanOfficerId", this.loanOfficerId);
        }

        if (this.hasLoanType) {
            object.getObject().put("loanType", this.loanType);
        }

        if (this.hasExpectedDisbursementDate) {
            object.getObject().put("expectedDisbursementDate", DateFormatUtils.format(this.expectedDisbursementDate, this.dateFormat));
        }

        if (this.hasSubmittedOnDate) {
            object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
        }

        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
        }

        if (this.hasPrincipal) {
            object.getObject().put("principal", this.principal);
        }

        if (this.hasFundId) {
            object.getObject().put("fundId", this.fundId);
        }

        if (this.hasDisbursementData) {
            object.getObject().put("disbursementData", this.disbursementData);
        }

        if (this.hasProductId) {
            object.getObject().put("productId", this.productId);
        }

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

//        if (this.hasMonthDayFormat) {
//            object.getObject().put("monthDayFormat", this.monthDayFormat);
//        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        return object;

    }

}
