package com.angkorteam.fintech.dto.constant;

public enum PortfolioAccountTypeEnum {
    
    Loan("1", "Loan"),
    Expense("2", "Expense"),
    Provisioning("3", "Provisioning"),
    Shares("4", "Shares");
    
    public static final String ID = "portfolio_account_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    PortfolioAccountTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    PortfolioAccountTypeEnum(String literal, String description, int enumType) {
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
