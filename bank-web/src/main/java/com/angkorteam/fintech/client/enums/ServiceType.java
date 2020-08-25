package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum ServiceType {

    S3("1", "Amazon S3"), SMTP("2", "SMTP"), SMS("3", "SMS");

    private String literal;

    private String description;

    ServiceType(String literal, String description) {
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

    public static ServiceType parseLiteral(String literal) {
        for (ServiceType value : ServiceType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ServiceType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
