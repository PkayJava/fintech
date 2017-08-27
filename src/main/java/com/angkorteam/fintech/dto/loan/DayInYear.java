package com.angkorteam.fintech.dto.loan;

public enum DayInYear {

    Actual("1", "Actual"), D360("360", "360 Days"), D364("364", "364 Days"), D365("365", "365 Days");

    private String literal;

    private String description;

    DayInYear(String literal, String description) {
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
