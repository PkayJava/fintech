package com.angkorteam.fintech.dto.constant;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum TellerStatus {

    Active("300", "Active"), Inactive("400", "Inactive");

    public static final String ID = "teller_status";

    private final String literal;

    private final String description;

    private final Long enumType;

    TellerStatus(String literal, String description) {
        this(literal, description, 0l);
    }

    TellerStatus(String literal, String description, Long enumType) {
        this.literal = literal;
        this.description = description;
        this.enumType = enumType;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public Long getEnumType() {
        return enumType;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static TellerStatus parseLiteral(String literal) {
        for (TellerStatus value : TellerStatus.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        TellerStatus value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
