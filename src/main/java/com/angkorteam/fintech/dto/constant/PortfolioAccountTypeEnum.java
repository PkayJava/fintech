package com.angkorteam.fintech.dto.constant;

public enum PortfolioAccountTypeEnum {
    
    Loan("1", "Loan"),
    Expense("2", "Expense"),
    Provisioning("3", "Provisioning"),
    Shares("4", "Shares");
    
    public static final String ID = "portfolio_account_type_enum";

    private final String literal;

    private final String description;
    
    private final Long enumType;

    PortfolioAccountTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    PortfolioAccountTypeEnum(String literal, String description, Long enumType) {
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
