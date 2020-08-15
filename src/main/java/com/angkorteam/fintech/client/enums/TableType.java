package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum TableType {

    Client("m_client", "Client"), Group("m_group", "Group"), SavingsAccount("m_savings_account", "Savings Account"), Loan("m_loan", "Loan"), Center("m_center", "Center"), Office("m_office", "Office");

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

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static TableType parseLiteral(String literal) {
        for (TableType value : TableType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        TableType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
