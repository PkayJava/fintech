package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum AccountUsage {

    Detail("1", "Detail"),
    Header("2", "Header");

    private String literal;

    private String description;

    AccountUsage(String literal, String description) {
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
