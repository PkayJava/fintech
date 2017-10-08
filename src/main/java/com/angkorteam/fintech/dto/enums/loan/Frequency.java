package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum Frequency {

    Same("1", "Same as repayment period"), 
    Daily("2", "Daily"),
    Weekly("3", "Weekly"),
    Monthly("4", "Monthly");

    private String literal;

    private String description;

    Frequency(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
    public static Frequency parseLiteral(String literal) {
        for (Frequency value : Frequency.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        Frequency value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }

}
