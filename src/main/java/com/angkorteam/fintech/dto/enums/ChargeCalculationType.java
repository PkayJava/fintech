package com.angkorteam.fintech.dto.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * charge_calculation_enum
 * 
 * @author socheat
 *
 */
public enum ChargeCalculationType {

    Invalid("0", "Invalid"),
    Flat("1", "Flat"),
    PercentOfAmount("2", "% Amount"),
    PercentOfAmountAndInterest("3", "% Amount & Interest"),
    PercentOfInterest("4", "% Interest"),
    PercentOfDisbursementAmount("5", "% Disbursement Amount");

    private String literal;

    private String description;

    ChargeCalculationType(String literal, String description) {
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

    public static ChargeCalculationType parseLiteral(String literal) {
        for (ChargeCalculationType value : ChargeCalculationType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargeCalculationType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
