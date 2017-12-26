package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public enum LoanCycle {

    Principle("1", "Principle"), 
    NumberOfRepayment("3", "Number of Repayment"), 
    NominalInterestRate("2", "Nominal Interest Rate");

    private String literal;

    private String description;

    LoanCycle(String literal, String description) {
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

    public static LoanCycle parseLiteral(String literal) {
        for (LoanCycle value : LoanCycle.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        LoanCycle value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
