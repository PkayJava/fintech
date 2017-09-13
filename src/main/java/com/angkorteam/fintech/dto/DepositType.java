package com.angkorteam.fintech.dto;

public enum DepositType {

    Recurring("300", "Recurring"), 
    Saving("100", "Saving"), 
    Fixed("200", "Fixed");

    private String literal;

    private String description;

    DepositType(String literal, String description) {
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
