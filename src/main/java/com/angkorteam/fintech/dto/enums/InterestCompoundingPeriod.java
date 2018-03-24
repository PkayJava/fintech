package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum InterestCompoundingPeriod {

    Daily("1", "Daily"), Monthly("4", "Monthly"), Quarterly("5", "Quarterly"), SemiAnnual("6", "Semi-Annual"), Annually("7", "Annually");

    private String literal;

    private String description;

    InterestCompoundingPeriod(String literal, String description) {
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

    public static InterestCompoundingPeriod parseLiteral(String literal) {
        for (InterestCompoundingPeriod value : InterestCompoundingPeriod.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        InterestCompoundingPeriod value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
