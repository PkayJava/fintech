package com.angkorteam.fintech.dto.recurring;

public enum Attribute {
    
    Gender("2", "Gender"),
    Age("3", "Age"),
    ClientType("4","Client Type"),
    ClientClassification("5","Client Classification");

    private String literal;

    private String description;

    Attribute(String literal, String description) {
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
