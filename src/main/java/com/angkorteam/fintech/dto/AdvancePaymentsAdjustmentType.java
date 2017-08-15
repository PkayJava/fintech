package com.angkorteam.fintech.dto;

public enum AdvancePaymentsAdjustmentType {

    ReduceEMIAmount("3", "Reduce EMI amount"), 
    ReduceNumberOfInstallments("2", "Reduce number of installments"),
    RescheduleNextRepayments("1", "Reschedule next repayments");

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
