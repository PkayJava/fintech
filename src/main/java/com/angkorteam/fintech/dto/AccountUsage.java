package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum AccountUsage {

    Detail("1"),
    Header("2");

    private String literal;

    AccountUsage(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

}
