package com.angkorteam.fintech.dto.loan;

public enum InterestRecalculationCompound {

    None("0", "None"), 
    Fee("2", "Fee"),
    Interest("1", "Interest"),
    FeeAndInterest("3", "Fee and Interest");

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
