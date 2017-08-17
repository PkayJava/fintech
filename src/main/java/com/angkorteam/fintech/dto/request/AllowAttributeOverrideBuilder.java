package com.angkorteam.fintech.dto.request;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class AllowAttributeOverrideBuilder implements Serializable {

    private boolean amortizationType;
    private boolean hasAmortizationType;

    public AllowAttributeOverrideBuilder withAmortizationType(boolean amortizationType) {
        this.amortizationType = amortizationType;
        this.hasAmortizationType = true;
        return this;
    }

    private boolean graceOnArrearsAgeing;
    private boolean hasGraceOnArrearsAgeing;

    public AllowAttributeOverrideBuilder withGraceOnArrearsAgeing(boolean graceOnArrearsAgeing) {
        this.graceOnArrearsAgeing = graceOnArrearsAgeing;
        this.hasGraceOnArrearsAgeing = true;
        return this;
    }

    private boolean graceOnPrincipalAndInterestPayment;
    private boolean hasGraceOnPrincipalAndInterestPayment;

    public AllowAttributeOverrideBuilder withGraceOnPrincipalAndInterestPayment(
            boolean graceOnPrincipalAndInterestPayment) {
        this.graceOnPrincipalAndInterestPayment = graceOnPrincipalAndInterestPayment;
        this.hasGraceOnPrincipalAndInterestPayment = true;
        return this;
    }

    private boolean inArrearsTolerance;
    private boolean hasInArrearsTolerance;

    public AllowAttributeOverrideBuilder withInArrearsTolerance(boolean inArrearsTolerance) {
        this.inArrearsTolerance = inArrearsTolerance;
        this.hasInArrearsTolerance = true;
        return this;
    }

    private boolean interestCalculationPeriodType;
    private boolean hasInterestCalculationPeriodType;

    public AllowAttributeOverrideBuilder withInterestCalculationPeriodType(boolean interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.hasInterestCalculationPeriodType = true;
        return this;
    }

    private boolean interestType;
    private boolean hasInterestType;

    public AllowAttributeOverrideBuilder withInterestType(boolean interestType) {
        this.interestType = interestType;
        this.hasInterestType = true;
        return this;
    }

    private boolean repaymentEvery;
    private boolean hasRepaymentEvery;

    public AllowAttributeOverrideBuilder withRepaymentEvery(boolean repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
        this.hasRepaymentEvery = true;
        return this;
    }

    private boolean transactionProcessingStrategyId;
    private boolean hasTransactionProcessingStrategyId;

    public AllowAttributeOverrideBuilder withTransactionProcessingStrategyId(boolean transactionProcessingStrategyId) {
        this.transactionProcessingStrategyId = transactionProcessingStrategyId;
        this.hasTransactionProcessingStrategyId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasTransactionProcessingStrategyId) {
            object.getObject().put("transactionProcessingStrategyId", this.transactionProcessingStrategyId);
        }

        if (this.hasRepaymentEvery) {
            object.getObject().put("repaymentEvery", this.repaymentEvery);
        }

        if (this.hasInterestType) {
            object.getObject().put("interestType", this.interestType);
        }

        if (this.hasInterestCalculationPeriodType) {
            object.getObject().put("interestCalculationPeriodType", this.interestCalculationPeriodType);
        }

        if (this.hasInArrearsTolerance) {
            object.getObject().put("inArrearsTolerance", this.inArrearsTolerance);
        }

        if (this.hasGraceOnPrincipalAndInterestPayment) {
            object.getObject().put("graceOnPrincipalAndInterestPayment", this.graceOnPrincipalAndInterestPayment);
        }

        if (this.hasGraceOnArrearsAgeing) {
            object.getObject().put("graceOnArrearsAgeing", this.graceOnArrearsAgeing);
        }

        if (this.hasAmortizationType) {
            object.getObject().put("amortizationType", this.amortizationType);
        }

        return object;
    }

}
