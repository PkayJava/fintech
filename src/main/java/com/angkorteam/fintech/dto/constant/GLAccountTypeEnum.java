package com.angkorteam.fintech.dto.constant;

public enum GLAccountTypeEnum {
    
    Asset("1", "Asset"),
    Liability("2", "Liability"),
    Equity("3", "Equity"),
    Income("4", "Income"),
    Expense("5", "Expense");
    
    public static final String ID = "glaccount_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    GLAccountTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    GLAccountTypeEnum(String literal, String description, int enumType) {
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
