package com.angkorteam.fintech.dto.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum MinimumActivePeriod {

    Day("0", "Days");

    private String literal;

    private String description;

    MinimumActivePeriod(String literal, String description) {
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

    public static MinimumActivePeriod parseLiteral(String literal) {
        for (MinimumActivePeriod value : MinimumActivePeriod.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        MinimumActivePeriod value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
