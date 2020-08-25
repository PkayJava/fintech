package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum ValidationPolicy {

    Simple("1", "Simple"), Secure("2", "Secure");

    private String literal;

    private String description;

    ValidationPolicy(String literal, String description) {
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

    public static TransferType parseLiteral(String literal) {
        for (TransferType value : TransferType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        TransferType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
