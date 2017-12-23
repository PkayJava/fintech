package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum StandingInstructionAccountType {

    LoanAccount("1", "Loan Account"),
    SavingAccount("2", "Saving Account");
    
    private String literal;

    private String description;

    StandingInstructionAccountType(String literal, String description) {
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

    public static StandingInstructionAccountType parseLiteral(String literal) {
        for (StandingInstructionAccountType value : StandingInstructionAccountType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        StandingInstructionAccountType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
