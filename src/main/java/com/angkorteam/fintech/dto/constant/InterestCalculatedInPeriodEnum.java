package com.angkorteam.fintech.dto.constant;

public enum InterestCalculatedInPeriodEnum {
    
    Daily("0", "Daily"),
    Same("1", "Same as repayment period");
    
    public static final String ID = "interest_calculated_in_period_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    InterestCalculatedInPeriodEnum(String literal, String description) {
        this(literal, description, 0);
    }

    InterestCalculatedInPeriodEnum(String literal, String description, int enumType) {
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
