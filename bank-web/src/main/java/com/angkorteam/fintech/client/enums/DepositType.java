package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum DepositType {

    Recurring("300", "Recurring"),
    Saving("100", "Saving"),
    Fixed("200", "Fixed");

    private String literal;

    private String description;

    DepositType(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public static DepositType parseLiteral(String literal) {
        for (DepositType value : DepositType.values()) {
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
        DepositType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
