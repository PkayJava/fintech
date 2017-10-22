package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum DayInMonth {

    Actual("1", "Actual"), 
    D30("30", "30 Days");

    private String literal;

    private String description;

    DayInMonth(String literal, String description) {
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

    public static DayInMonth parseLiteral(String literal) {
        for (DayInMonth value : DayInMonth.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        DayInMonth value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
