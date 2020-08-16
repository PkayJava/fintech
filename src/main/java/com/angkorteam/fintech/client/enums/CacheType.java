package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum CacheType {

    Invalid("0", "Invalid"),
    NoCache("1", "No Cache"),
    SingleNode("2", "Single Node"),
    MultiNode("3", "Multi Node");

    private String literal;

    private String description;

    CacheType(String literal, String description) {
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

    public static CacheType parseLiteral(String literal) {
        for (CacheType value : CacheType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        CacheType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
