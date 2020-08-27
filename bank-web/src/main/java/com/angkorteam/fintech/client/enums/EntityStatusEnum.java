package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public enum EntityStatusEnum {

    Create("100", "Create"),
    Approve("200", "Approve"),
    Activate("300", "Activate"),
    Withdrawn("400", "Withdrawn"),
    Rejected("500", "Rejected"),
    Close("600", "Close"),
    WriteOff("601", "Write Off"),
    Disburse("800", "Disburse");

    private String literal;

    private String description;

    EntityStatusEnum(String literal, String description) {
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

    public static EntityStatusEnum parseLiteral(String literal) {
        for (EntityStatusEnum value : EntityStatusEnum.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        EntityStatusEnum value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}