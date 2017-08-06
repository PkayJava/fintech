package com.angkorteam.fintech.dto;

public enum Frequency {

    Same("Same", "Same as repayment period"), 
    Daily("Daily", "Daily"),
    Weekly("Weekly", "Weekly"),
    Monthly("Monthly", "Monthly");

    private String literal;

    private String description;

    Frequency(String literal, String description) {
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
