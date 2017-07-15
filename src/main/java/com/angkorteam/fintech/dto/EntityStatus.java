package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public enum EntityStatus {

    Create("100", "Create"),
    Approve("200", "Approve"),
    Activate("300", "Activate"),
    Withdrawn("400", "Withdrawn"),
    Rejected("500", "Rejected"),
    Close("600", "Close"),
    WriteOff("601", "Write Off"),
    Disburse("800", "Disburse");

    private String literal;

    private String description;

    EntityStatus(String literal, String description) {
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
