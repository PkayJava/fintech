package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum TableType {
    Client("m_client"),
    Group("m_group"),
    SavingsAccount("m_savings_account"),
    Loan("m_loan"),
    Center("m_center"),
    Office("m_office");

    private String literal;

    TableType(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
