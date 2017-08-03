package com.angkorteam.fintech.dto;

public enum InterestMethod {

    Flat("Flat", "Flat"), DecliningBalance("DecliningBalance", "Declining Balance");

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
