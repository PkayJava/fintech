package com.angkorteam.fintech.dto.loan;

public enum RepaidType {

    Day("0", "Days"), Week("1", "Weeks"), Month("2", "Months");

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
