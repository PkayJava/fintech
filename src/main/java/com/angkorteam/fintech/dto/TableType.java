package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum TableType {
    Client("m_client", "Client"),
    Group("m_group", "Group"),
    SavingsAccount("m_savings_account", "Savings Account"),
    Loan("m_loan", "Loan"),
    Center("m_center", "Center"),
    Office("m_office", "Office");

    private String literal;

    private String description;

    TableType(String literal, String description) {
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
