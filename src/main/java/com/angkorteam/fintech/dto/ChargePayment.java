package com.angkorteam.fintech.dto;

public enum ChargePayment {

    RegularMode("0", "Regular Mode"),
    AccountTransferMode("1", "Account Transfer Mode");

    private String literal;

    private String description;

    ChargePayment(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }
}
