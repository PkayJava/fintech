package com.angkorteam.fintech.dto;

public enum OperandType {
    
    Fixed("2", "Fixed"),
    Incentive("3","Incentive");

    private String literal;

    private String description;

    OperandType(String literal, String description) {
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
