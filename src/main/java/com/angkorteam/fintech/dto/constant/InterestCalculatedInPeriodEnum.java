package com.angkorteam.fintech.dto.constant;

public enum InterestCalculatedInPeriodEnum {
    
    Daily("0", "Daily"),
    Same("1", "Same as repayment period");
    
    public static final String ID = "interest_calculated_in_period_enum";

    private final String literal;

    private final String description;
    
    private final Long enumType;

    InterestCalculatedInPeriodEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    InterestCalculatedInPeriodEnum(String literal, String description, Long enumType) {
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
