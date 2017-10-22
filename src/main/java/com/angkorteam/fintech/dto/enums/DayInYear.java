package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum DayInYear {

    Actual("1", "Actual"), 
    D365("365", "365 Days"), 
    D364("364", "364 Days"), 
    D360("360", "360 Days");

    private String literal;

    private String description;

    DayInYear(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public static DayInYear parseLiteral(String literal) {
        for (DayInYear value : DayInYear.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static Option optionLiteral(String literal) {
        DayInYear value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
