package com.angkorteam.fintech.dto;

public enum ClosureInterestCalculationRule {
    
    PreClosureDate("1", "Calculate till pre closure date"), 
    RestFrequencyDate("2", "Calculate till rest frequency date");

    private String literal;

    private String description;

    ClosureInterestCalculationRule(String literal, String description) {
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
