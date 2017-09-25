package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum TellerState {

    Active("300", "Active"), 
    Inactive("400", "Inactive"),
    Closed("600", "Closed");

    private String literal;

    private String description;

    TellerState(String literal, String description) {
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
