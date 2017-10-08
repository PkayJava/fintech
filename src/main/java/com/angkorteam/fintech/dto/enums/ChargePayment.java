package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum ChargePayment {

    RegularMode("0", "Regular Mode"), 
    AccountTransferMode("1", "Account Transfer Mode");

    private String literal;

    private String description;

    ChargePayment(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
    public static ChargePayment parseLiteral(String literal) {
        for (ChargePayment value : ChargePayment.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        ChargePayment value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }

}
