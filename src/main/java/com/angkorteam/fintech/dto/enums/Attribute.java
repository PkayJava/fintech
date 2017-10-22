package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum Attribute {

    Gender("2", "Gender"), 
    Age("3", "Age"), 
    ClientType("4", "Client Type"), 
    ClientClassification("5", "Client Classification");

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

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static Attribute parseLiteral(String literal) {
        for (Attribute value : Attribute.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Attribute value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
