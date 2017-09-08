package com.angkorteam.fintech.dto.fixed;

public enum Operand {
    
    LessThan("1", "Less Than"),
    Equal("2", "Equal"),
    GreaterThan("3","Greater Than"),
    NotEqual("4","Not Equal");

    private String literal;

    private String description;

    Operand(String literal, String description) {
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
