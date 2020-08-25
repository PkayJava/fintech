package com.angkorteam.fintech.client.renums;

public enum InterestPeriodFrequencyEnum {

    Month("2", "Per month"),
    Year("3", "Per year");

    public static final String ID = "interest_period_frequency_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    InterestPeriodFrequencyEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    InterestPeriodFrequencyEnum(String literal, String description, Long enumType) {
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
