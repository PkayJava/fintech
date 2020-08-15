package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum FinancialAccountType {

    SavingReference1("1", "Saving Reference"), SavingControl2("2", "Saving Control"), InterestOnSaving3("3", "Interest On Saving"), IncomeFee4("4", "Income Fee"), IncomePenalty5("5", "Income Penalty"), WriteOff6("6", "Write-Off"), InterestReceivable7("7", "Interest Receivable"), FeeReceivable8("8", "Fee Receivable"), PenaltyReceivable9("9", "Penalty Receivable"), TransferInSuspense10("10", "Transfer In Suspense"), OverdraftPortfolio11("11", "Overdraft Portfolio"), OverdraftInterestIncome12("12", "Overdraft Interest Income"), WriteOff13("13", "Write-Off"), EscheatLiability14("14", "Escheat Liability");

    private String literal;

    private String description;

    FinancialAccountType(String literal, String description) {
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

    public static FinancialAccountType parseLiteral(String literal) {
        for (FinancialAccountType value : FinancialAccountType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        FinancialAccountType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
