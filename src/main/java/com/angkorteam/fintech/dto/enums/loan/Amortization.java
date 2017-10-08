package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum Amortization {
    Installment("1", "Equal Installments"), 
    PrincipalPayment("0", "Equal Principal Payments");

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
        return new Option(value.name(), value.getDescription());
    }
}
