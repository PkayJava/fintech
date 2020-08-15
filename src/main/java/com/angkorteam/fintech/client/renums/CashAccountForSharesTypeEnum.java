package com.angkorteam.fintech.client.renums;

public enum CashAccountForSharesTypeEnum {

    SharesReference("1", "Shares Reference"),
    SharesSuspense("2", "Shares Suspense"),
    IncomeFromFees("3", "Income From Fees"),
    SharesEquity("4", "Shares Equity");

    public static final String ID = "cash_account_for_shares_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    CashAccountForSharesTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    CashAccountForSharesTypeEnum(String literal, String description, Long enumType) {
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
