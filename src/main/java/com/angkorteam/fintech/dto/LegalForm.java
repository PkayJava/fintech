package com.angkorteam.fintech.dto;

public enum LegalForm {

    Person("1", "Person"),
    Entity("2", "Entity");

    private String literal;

    private String description;

    LegalForm(String literal, String description) {
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
