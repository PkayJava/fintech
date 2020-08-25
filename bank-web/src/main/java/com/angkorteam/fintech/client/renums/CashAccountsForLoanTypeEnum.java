package com.angkorteam.fintech.client.renums;

public enum CashAccountsForLoanTypeEnum {

    FundSource("1", "Fund Source"),
    LoanPortfolio("2", "Loan Portfolio"),
    InterestOnLoans("3", "Interest On Loans"),
    IncomeFromFees("4", "Income From Fees"),
    IncomeFromPenalties("5", "Income From Penalties"),
    LossesWrittenOff("6", "Losses Written Off"),
    TransfersSuspense("10", "Transfers Suspense"),
    OverPayment("11", "Over Payment"),
    IncomeFromRecovery("12", "Income From Recovery");

    public static final String ID = "cash_accounts_for_loan_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    CashAccountsForLoanTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    CashAccountsForLoanTypeEnum(String literal, String description, Long enumType) {
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

    public Long getEnumType() {
        return enumType;
    }
}
