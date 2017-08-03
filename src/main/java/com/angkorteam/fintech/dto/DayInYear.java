package com.angkorteam.fintech.dto;

public enum DayInYear {
    
    Actual("Actual", "Actual"), 
    D360("D360", "360 Days"), 
    D364("D364", "364 Days"),
    D365("D365", "365 Days");

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
