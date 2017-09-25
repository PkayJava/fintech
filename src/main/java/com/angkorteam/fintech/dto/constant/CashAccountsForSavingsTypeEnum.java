package com.angkorteam.fintech.dto.constant;

public enum CashAccountsForSavingsTypeEnum {
    
    SavingsReference("1", "Savings Reference"),
    SavingsControl("2", "Savings Control"),
    InterestOnSavings("3", "Interest On Savings"),
    IncomeFromFees("4", "Income From Fees"),
    IncomeFromPenalties("5", "Income From Penalties"),
    TransfersSuspense("10", "Transfers Suspense"),
    OverdraftPortfolioControl("11", "Overdraft Portfolio Control"),
    IncomeFromInterest("12", "Income From Interest");
    
    public static final String ID = "cash_accounts_for_savings_type_enum";
 
    private final String literal;

    private final String description;
    
    private final int enumType;

    CashAccountsForSavingsTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    CashAccountsForSavingsTypeEnum(String literal, String description, int enumType) {
        this.literal = literal;
        this.description = description;
        this.enumType = enumType;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
    public int getEnumType() {
        return enumType;
    }
}
