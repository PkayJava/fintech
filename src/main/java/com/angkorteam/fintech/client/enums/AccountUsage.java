package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum AccountUsage {

    Detail("1", "Detail"), Header("2", "Header");

    private String literal;

    private String description;

    AccountUsage(String literal, String description) {
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

    public static AccountUsage parseLiteral(String literal) {
        for (AccountUsage value : AccountUsage.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        AccountUsage value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
