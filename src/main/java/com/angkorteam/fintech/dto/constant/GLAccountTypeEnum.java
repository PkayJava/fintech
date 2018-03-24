package com.angkorteam.fintech.dto.constant;

public enum GLAccountTypeEnum {

    Asset("1", "Asset"), Liability("2", "Liability"), Equity("3", "Equity"), Income("4", "Income"), Expense("5", "Expense");

    public static final String ID = "glaccount_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    GLAccountTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    GLAccountTypeEnum(String literal, String description, Long enumType) {
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
