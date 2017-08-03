package com.angkorteam.fintech.dto;

public enum Amortization {
    Installment("Installment", "Equal Installments"), PrincipalPayment("PrincipalPayment", "Equal Principal Payments");

    private String literal;

    private String description;

    Amortization(String literal, String description) {
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
