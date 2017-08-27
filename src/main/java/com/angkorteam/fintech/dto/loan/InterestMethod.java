package com.angkorteam.fintech.dto.loan;

public enum InterestMethod {

    Flat("1", "Flat"), DecliningBalance("0", "Declining Balance");

    private String literal;

    private String description;

    InterestMethod(String literal, String description) {
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
