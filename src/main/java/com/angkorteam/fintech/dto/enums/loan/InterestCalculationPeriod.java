package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum InterestCalculationPeriod {

    Daily("0", "Daily"), SameAsPayment("1", "Same as payment");

    private String literal;

    private String description;

    InterestCalculationPeriod(String literal, String description) {
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

    public static InterestCalculationPeriod parseLiteral(String literal) {
        for (InterestCalculationPeriod value : InterestCalculationPeriod.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        InterestCalculationPeriod value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
