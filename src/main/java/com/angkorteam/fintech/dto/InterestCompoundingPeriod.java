package com.angkorteam.fintech.dto;

public enum InterestCompoundingPeriod {

    Daily("1", "Daily"),
    Monthly("4", "Monthly"),
    Quarterly("5", "Quarterly"),
    SemiAnnual("6", "Semi-Annual"),
    Annually("7", "Annually");

    private String literal;

    private String description;

    InterestCompoundingPeriod(String literal, String description) {
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
