package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * charge_calculation_enum
 * 
 * @author socheat
 *
 */
public enum ChargeCalculation {

    Flat("1", "Flat"), ApprovedAmount("2", "% Approved Amount"), LoanAmountInterest("3", "% Loan Amount + Interest"), Interest("4", "% Interest"), DisbursementAmount("5", "% Disbursement Amount");

    private String literal;

    private String description;

    ChargeCalculation(String literal, String description) {
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

    public static ChargeCalculation parseLiteral(String literal) {
        for (ChargeCalculation value : ChargeCalculation.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargeCalculation value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
