package com.angkorteam.fintech.dto.constant;

public enum TellerStatus {

    Active("300", "Active"), 
    Inactive("400", "Inactive"),
    Closed("600", "Closed");
    
    public static final String ID = "teller_status";

    private final String literal;

    private final String description;
    
    private final int enumType;

    TellerStatus(String literal, String description) {
        this(literal, description, 0);
    }

    TellerStatus(String literal, String description, int enumType) {
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
