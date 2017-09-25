package com.angkorteam.fintech.dto.constant;

public enum EntityAccountTypeEnum {

    Client("1", "Client"),
    Loan("2", "Loan"),
    Savings("3", "Savings"),
    Center("4", "Center"),
    Group("5", "Group"),
    Shares("6", "Shares");
    
    public static final String ID = "entity_account_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    EntityAccountTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    EntityAccountTypeEnum(String literal, String description, int enumType) {
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
