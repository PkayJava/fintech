package com.angkorteam.fintech.dto.constant;

public enum InterestMethodEnum {
    
    DecliningBalance("0", "Declining Balance"),
    Flat("1", "Flat");
    
    public static final String ID = "interest_method_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    InterestMethodEnum(String literal, String description) {
        this(literal, description, 0);
    }

    InterestMethodEnum(String literal, String description, int enumType) {
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
