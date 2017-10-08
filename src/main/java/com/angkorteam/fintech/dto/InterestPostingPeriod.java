package com.angkorteam.fintech.dto;

public enum InterestPostingPeriod {

    Monthly("4", "Monthly"),
    Quarterly("5", "Quarterly"),
    BiAnnual("6", "BiAnnual"),
    Annually("7", "Annually");

    private String literal;

    private String description;

    InterestPostingPeriod(String literal, String description) {
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
