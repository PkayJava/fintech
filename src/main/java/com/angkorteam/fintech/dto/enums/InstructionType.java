package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum InstructionType {

    Fixed("1", "Fixed"),
    Dues("2", "Dues");
    
    private String literal;

    private String description;

    InstructionType(String literal, String description) {
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

    public static InstructionType parseLiteral(String literal) {
        for (InstructionType value : InstructionType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        InstructionType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
