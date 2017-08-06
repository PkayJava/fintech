package com.angkorteam.fintech.dto;

public enum FrequencyType {

    First("First", "First"), 
    Second("Second", "Second"),
    Third("Third", "Third"),
    Fourth("Fourth", "Fourth"),
    Last("Last", "Last"),
    OnDay("OnDay", "On Day");

    private String literal;

    private String description;

    FrequencyType(String literal, String description) {
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
