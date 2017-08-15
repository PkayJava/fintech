package com.angkorteam.fintech.dto;

public enum InterestCalculationPeriod {

    Daily("0", "Daily"), SameAsPayment("1", "Same as payment");

    private String literal;

    private String description;

    InterestCalculationPeriod(String literal, String description) {
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
