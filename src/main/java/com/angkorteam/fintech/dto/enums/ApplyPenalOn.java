package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum ApplyPenalOn {
    
    WholeTerm("1", "Whole term"), 
    TillPrematureWithdrawal("2", "Till Premature Withdrawal");

    private String literal;

    private String description;

    ApplyPenalOn(String literal, String description) {
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

    public static ApplyPenalOn parseLiteral(String literal) {
        for (ApplyPenalOn value : ApplyPenalOn.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ApplyPenalOn value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }
}
