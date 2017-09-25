package com.angkorteam.fintech.dto.constant;

public enum StatusEnum {

    Invalid("0", "Invalid"),
    Pending("100", "Pending"),
    Active("300", "Active"), 
    Closed("600", "Closed");
    
    public static final String ID = "status_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    StatusEnum(String literal, String description) {
        this(literal, description, 0);
    }

    StatusEnum(String literal, String description, int enumType) {
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
