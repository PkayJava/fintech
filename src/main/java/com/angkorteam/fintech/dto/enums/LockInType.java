package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum LockInType {

    Day("0", "Days"), 
    Week("1", "Weeks"), 
    Month("2", "Months"), 
    Year("3", "Years");

    private String literal;

    private String description;

    LockInType(String literal, String description) {
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

    public static LockInType parseLiteral(String literal) {
        for (LockInType value : LockInType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        LockInType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }

}
