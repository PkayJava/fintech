package com.angkorteam.fintech.dto.recurring;

public enum DayInYear {

    D365("365", "365 Days"),
    D360("360", "360 Days");

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
