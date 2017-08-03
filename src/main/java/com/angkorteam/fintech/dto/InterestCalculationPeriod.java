package com.angkorteam.fintech.dto;

public enum InterestCalculationPeriod {

    Daily("Daily", "Daily"), SameAsPayment("SameAsPayment", "Same as payment");

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
