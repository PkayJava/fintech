package com.angkorteam.fintech.dto;

public enum NominalInterestRateScheduleType {

    Month("Month", "Per month"), Year("Year", "Per year");

    private String literal;

    private String description;

    NominalInterestRateScheduleType(String literal, String description) {
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
