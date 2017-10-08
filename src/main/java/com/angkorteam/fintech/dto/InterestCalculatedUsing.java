package com.angkorteam.fintech.dto;

public enum InterestCalculatedUsing {

    DailyBalance("1", "Daily Balance"),
    AverageDailyBalance("2", "Average Daily Balance");

    private String literal;

    private String description;

    InterestCalculatedUsing(String literal, String description) {
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
