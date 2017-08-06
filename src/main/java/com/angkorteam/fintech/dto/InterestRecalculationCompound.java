package com.angkorteam.fintech.dto;

public enum InterestRecalculationCompound {

    None("None", "None"), 
    Fee("Fee", "Fee"),
    Interest("Interest", "Interest"),
    FeeAndInterest("FeeAndInterest", "Fee and Interest");

    private String literal;

    private String description;

    InterestRecalculationCompound(String literal, String description) {
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
