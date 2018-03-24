package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum Priority {

    Urgent("1", "Urgent Priority"), High("2", "High Priority"), Medium("3", "Medium Priority"), Low("4", "Low Priority");

    private String literal;

    private String description;

    Priority(String literal, String description) {
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

    public static Priority parseLiteral(String literal) {
        for (Priority value : Priority.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Priority value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
