package com.angkorteam.fintech.dto;

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
}
