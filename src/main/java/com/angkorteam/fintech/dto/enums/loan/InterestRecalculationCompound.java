package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

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
    
    public static InterestRecalculationCompound parseLiteral(String literal) {
        for (InterestRecalculationCompound value : InterestRecalculationCompound.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        InterestRecalculationCompound value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }
}
