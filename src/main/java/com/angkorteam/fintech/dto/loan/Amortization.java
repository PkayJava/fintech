package com.angkorteam.fintech.dto.loan;

public enum Amortization {
    Installment("1", "Equal Installments"), PrincipalPayment("0", "Equal Principal Payments");

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
