package com.angkorteam.fintech.dto.loan;

public enum WhenType {

    Equals("2", "Equals"), 
    GreaterThen("3", "Greater Then");

    private String literal;

    private String description;

    WhenType(String literal, String description) {
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
