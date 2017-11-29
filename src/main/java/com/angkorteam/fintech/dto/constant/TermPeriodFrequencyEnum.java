package com.angkorteam.fintech.dto.constant;

public enum TermPeriodFrequencyEnum {

    Day("0", "Day"),
    Week("1", "Week"),
    Month("2", "Month"),
    Year("3", "Year");
    
    public static final String ID = "term_period_frequency_enum";

    private final String literal;

    private final String description;
    
    private final Long enumType;

    TermPeriodFrequencyEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    TermPeriodFrequencyEnum(String literal, String description, Long enumType) {
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
