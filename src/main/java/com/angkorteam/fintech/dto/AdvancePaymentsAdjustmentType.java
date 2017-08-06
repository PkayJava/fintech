package com.angkorteam.fintech.dto;

public enum AdvancePaymentsAdjustmentType {

    ReduceEMIAmount("ReduceEMIAmount", "Reduce EMI amount"), 
    ReduceNumberOfInstallments("ReduceNumberOfInstallments", "Reduce number of installments"),
    RescheduleNextRepayments("RescheduleNextRepayments", "Reschedule next repayments");

    private String literal;

    private String description;

    AdvancePaymentsAdjustmentType(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
}
