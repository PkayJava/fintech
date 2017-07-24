package com.angkorteam.fintech.dto;

public enum ChargeCalculation {

    Flat("1", "Flat"),
    ApprovedAmount("2", "% Approved Amount"),
    LoanAmountInterest("3", "% Loan Amount + Interest"),
    Interest("4", "% Interest"),
    DisbursementAmount("5", "% Disbursement Amount");

    private String literal;

    private String description;

    ChargeCalculation(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }
}
