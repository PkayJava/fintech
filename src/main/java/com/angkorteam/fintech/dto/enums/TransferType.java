package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum TransferType {

    AccountTransfer("1", "Account Transfer"),
    LoanRepayment("2", "Loan Repayment");
    
    private String literal;

    private String description;

    TransferType(String literal, String description) {
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

    public static TransferType parseLiteral(String literal) {
        for (TransferType value : TransferType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        TransferType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
