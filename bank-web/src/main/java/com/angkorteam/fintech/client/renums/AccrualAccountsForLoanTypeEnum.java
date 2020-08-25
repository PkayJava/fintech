package com.angkorteam.fintech.client.renums;

public enum AccrualAccountsForLoanTypeEnum {

    FundSource("1", "Fund Source"),
    LoanPortfolio("2", "Loan Portfolio"),
    InterestOnLoans("3", "Interest On Loans"),
    IncomeFromFees("4", "Income From Fees"),
    IncomeFromPenalties("5", "Income From Penalties"),
    LossesWrittenOff("6", "Losses Written Off"),
    InterestReceivable("7", "Interest Receivable"),
    FeesReceivable("8", "Fees Receivable"),
    PenaltiesReceivable("9", "Penalties Receivable"),
    TransfersSuspense("10", "Transfers Suspense"),
    OverPayment("11", "Over Payment"),
    IncomeFormRecovery("12", "Income Form Recovery");

    public static final String ID = "accrual_accounts_for_loan_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    AccrualAccountsForLoanTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    AccrualAccountsForLoanTypeEnum(String literal, String description, Long enumType) {
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
