package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum FrequencyType {

    First("1", "First"), Second("2", "Second"), Third("3", "Third"), Fourth("4", "Fourth"), Last("-1", "Last"), OnDay("-2", "On Day");

    private String literal;

    private String description;

    FrequencyType(String literal, String description) {
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

    public static FrequencyType parseLiteral(String literal) {
        for (FrequencyType value : FrequencyType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        FrequencyType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
