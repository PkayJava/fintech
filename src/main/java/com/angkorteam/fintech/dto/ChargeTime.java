package com.angkorteam.fintech.dto;

public enum ChargeType {

    Loan("1", "Loan"),
    SavingDeposit("2", "Savings and Deposit"),
    Client("3", "Client"),
    Share("4", "Shares");

    private String literal;

    private String description;

    ChargeType(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }
}
