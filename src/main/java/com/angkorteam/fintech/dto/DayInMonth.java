package com.angkorteam.fintech.dto;

public enum DayInMonth {
    
    Actual("Actual", "Actual"), 
    D30("D30", "30 Days");

    private String literal;

    private String description;

    DayInMonth(String literal, String description) {
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
