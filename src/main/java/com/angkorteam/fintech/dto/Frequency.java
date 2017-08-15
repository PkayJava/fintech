package com.angkorteam.fintech.dto;

public enum Frequency {

    Same("1", "Same as repayment period"), 
    Daily("2", "Daily"),
    Weekly("3", "Weekly"),
    Monthly("4", "Monthly");

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
