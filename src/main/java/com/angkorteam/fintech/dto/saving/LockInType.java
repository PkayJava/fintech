package com.angkorteam.fintech.dto.saving;

public enum LockInType {

    Day("0", "Days"),
    Week("1", "Weeks"),
    Month("2", "Months"),
    Year("3", "Years");

    private String literal;

    private String description;

    LockInType(String literal, String description) {
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
