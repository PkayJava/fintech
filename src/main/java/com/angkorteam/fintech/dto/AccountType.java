package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum AccountType {
    Asset("1"),
    Liability("2"),
    Equity("3"),
    Income("4"),
    Expense("5");

    private String literal;

    AccountType(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
