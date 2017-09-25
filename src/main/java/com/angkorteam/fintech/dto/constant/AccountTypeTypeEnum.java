package com.angkorteam.fintech.dto.constant;

public enum AccountTypeTypeEnum {
    
    Invalid("0", "Invalid"),
    Individual("1", "Individual"),
    Group("2", "Group"),
    JLG("3", "JLG");
    
    public static final String ID = "account_type_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;
    
    AccountTypeTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    AccountTypeTypeEnum(String literal, String description, int enumType) {
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
