package com.angkorteam.fintech.dto;

public enum RepeatType {

    Day("Day", "Day"), Week("Week", "Weeks"), Month("Month", "Months");

    private String literal;

    private String description;

    RepeatType(String literal, String description) {
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
