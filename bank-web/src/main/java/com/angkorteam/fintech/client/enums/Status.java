package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum Status {

    Active("1", "Active"), Disabled("2", "Disabled");

    private String literal;

    private String description;

    Status(String literal, String description) {
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

    public static Status parseLiteral(String literal) {
        for (Status value : Status.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Status value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
