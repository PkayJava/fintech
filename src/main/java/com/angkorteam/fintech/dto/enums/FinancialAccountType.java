package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum FinancialAccountType {

    SavingReference("1", "Saving Reference"), 
    SavingControl("2", "Saving Control"), 
    InterestOnSaving("3", "Interest On Saving"), 
    IncomeFee("4", "Income Fee"),
    IncomePenalty("5", "Income Penalty"),
    TransferInSuspense("10", "Transfer In Suspense"),
    OverdraftPortfolio("11", "Overdraft Portfolio"),
    OverdraftInterestIncome("12", "Overdraft Interest Income"),
    WriteOff("13", "Write-Off"),
    EscheatLiability("14", "Escheat Liability");

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
