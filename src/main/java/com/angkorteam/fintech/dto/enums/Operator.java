package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum Operator {
    
    LessThan("1", "Less Than"),
    Equal("2", "Equal"),
    GreaterThan("3","Greater Than"),
    NotEqual("4","Not Equal");

    private String literal;

    private String description;

    Operator(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
    public static Operator parseLiteral(String literal) {
        for (Operator value : Operator.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        Operator value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }
}
