package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum Destination {

    OwnAccount("1", "Own Account"),
    WithInBank("2", "With In Bank");
    
    private String literal;

    private String description;

    Destination(String literal, String description) {
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

    public static Destination parseLiteral(String literal) {
        for (Destination value : Destination.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Destination value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
