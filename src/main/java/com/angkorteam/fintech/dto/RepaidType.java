package com.angkorteam.fintech.dto;

public enum RepaidType {
    
    Day("Day", "Day"), Week("Week", "Weeks"), Month("Month", "Months");

    private String literal;

    private String description;

    RepaidType(String literal, String description) {
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
