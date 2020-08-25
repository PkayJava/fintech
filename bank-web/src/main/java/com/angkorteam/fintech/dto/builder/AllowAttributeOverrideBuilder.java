package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import io.github.openunirest.http.JsonNode;

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

    private boolean graceOnPrincipleAndInterestPayment;
    private boolean hasGraceOnPrincipleAndInterestPayment;

    public AllowAttributeOverrideBuilder withGraceOnPrincipleAndInterestPayment(boolean graceOnPrincipleAndInterestPayment) {
        this.graceOnPrincipleAndInterestPayment = graceOnPrincipleAndInterestPayment;
        this.hasGraceOnPrincipleAndInterestPayment = true;
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

        if (this.hasGraceOnPrincipleAndInterestPayment) {
            object.getObject().put("graceOnPrincipalAndInterestPayment", this.graceOnPrincipleAndInterestPayment);
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
