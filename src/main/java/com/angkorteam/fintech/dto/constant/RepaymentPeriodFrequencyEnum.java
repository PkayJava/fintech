package com.angkorteam.fintech.dto.constant;

public enum RepaymentPeriodFrequencyEnum {

    Day("0", "Day"),
    Week("1", "Week"),
    Month("2", "Month");
    
    public static final String ID = "repayment_period_frequency_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    RepaymentPeriodFrequencyEnum(String literal, String description) {
        this(literal, description, 0);
    }

    RepaymentPeriodFrequencyEnum(String literal, String description, int enumType) {
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
