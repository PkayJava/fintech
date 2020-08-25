package com.angkorteam.fintech.client.renums;

public enum GLAccountType {

    Asset("1", "Asset"),
    Liability("2", "Liability"),
    Equity("3", "Equity"),
    Income("4", "Income"),
    Expense("5", "Expense");

    public static final String ID = "glaccount_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    GLAccountType(String literal, String description) {
        this(literal, description, 0l);
    }

    GLAccountType(String literal, String description, Long enumType) {
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
