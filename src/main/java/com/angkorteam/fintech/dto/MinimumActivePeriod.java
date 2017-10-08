package com.angkorteam.fintech.dto;

public enum MinimumActivePeriod {

    Day("0", "Days");

    private String literal;

    private String description;

    MinimumActivePeriod(String literal, String description) {
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
