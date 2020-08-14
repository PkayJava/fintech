package com.angkorteam.fintech.dto.constant;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum TellerStatus {

    Invalid("0", "Invalid", 0L),
    Pending("100", "Pending", 100L),
    Active("300", "Active", 300L),
    Closed("600", "Closed", 600L),
    Inactive("400", "Inactive", 400L);

    public static final String ID = "teller_status";

    private final String literal;

    private final String description;

    private final long enumType;

    TellerStatus(String literal, String description, long enumType) {
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

    public long getEnumType() {
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
