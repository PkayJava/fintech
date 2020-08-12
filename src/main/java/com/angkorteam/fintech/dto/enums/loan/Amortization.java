package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum Amortization {

    EqualInstallment("1", "Equal Installments"), EqualPrinciplePayment("0", "Equal Principle Payments");

    private String literal;

    private String description;

    Amortization(String literal, String description) {
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

    public static Amortization parseLiteral(String literal) {
        for (Amortization value : Amortization.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Amortization value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
