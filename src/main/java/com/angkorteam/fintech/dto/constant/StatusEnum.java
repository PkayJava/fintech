package com.angkorteam.fintech.dto.constant;

public enum StatusEnum {

    Invalid("0", "Invalid"),
    Pending("100", "Pending"),
    Approved("200", "Approved"),
    Active("300", "Active"), 
    WithdrawnByClient("400", "Withdrawn by client"),
    Rejected("500", "Rejected"),
    Closed("600", "Closed");
    
    public static final String ID = "status_enum";

    private final String literal;

    private final String description;
    
    private final Long enumType;

    StatusEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    StatusEnum(String literal, String description, Long enumType) {
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
