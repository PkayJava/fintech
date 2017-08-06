package com.angkorteam.fintech.dto;

public enum ClosureInterestCalculationRule {
    
    PreClosureDate("PreClosureDate", "Calculate till pre closure date"), 
    RestFrequencyDate("RestFrequencyDate", "Calculate till rest frequency date");

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
