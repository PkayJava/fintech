package com.angkorteam.fintech.dto.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * charge_payment_mode_enum
 * 
 * @author socheat
 *
 */
public enum ChargePaymentMode {

    Regular("0", "Regular"),
    AccountTransfer("1", "Account Transfer");

    private String literal;

    private String description;

    ChargePaymentMode(String literal, String description) {
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

    public static ChargePaymentMode parseLiteral(String literal) {
        for (ChargePaymentMode value : ChargePaymentMode.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargePaymentMode value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
