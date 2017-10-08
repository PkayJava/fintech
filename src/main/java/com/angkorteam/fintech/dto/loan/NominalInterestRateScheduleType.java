package com.angkorteam.fintech.dto.loan;

public enum NominalInterestRateScheduleType {

    Month("2", "Per month"), 
    Year("3", "Per year");

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
