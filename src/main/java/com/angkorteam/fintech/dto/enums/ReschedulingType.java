package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum ReschedulingType {

    NextRepaymentDate("1", "Reschedule to next repayment date"), SpecifiedDate("2", "Reschedule to specified date");

    private String literal;

    private String description;

    ReschedulingType(String literal, String description) {
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

    public static ReschedulingType parseLiteral(String literal) {
        for (ReschedulingType value : ReschedulingType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ReschedulingType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
