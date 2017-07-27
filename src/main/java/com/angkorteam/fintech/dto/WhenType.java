package com.angkorteam.fintech.dto;

public enum WhenType {

    Equals("Equals", "Equals"), GreaterThen("GreaterThen", "Greater Then");

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
