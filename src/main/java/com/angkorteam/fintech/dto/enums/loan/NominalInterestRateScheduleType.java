package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum NominalInterestRateScheduleType {

    Month("2", "Per month"), 
    Year("3", "Per year");

    private String literal;

    private String description;

    NominalInterestRateScheduleType(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public static NominalInterestRateScheduleType parseLiteral(String literal) {
        for (NominalInterestRateScheduleType value : NominalInterestRateScheduleType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        NominalInterestRateScheduleType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }
}
