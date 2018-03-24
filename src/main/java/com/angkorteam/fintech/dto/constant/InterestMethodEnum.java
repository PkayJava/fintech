package com.angkorteam.fintech.dto.constant;

public enum InterestMethodEnum {

    DecliningBalance("0", "Declining Balance"), Flat("1", "Flat");

    public static final String ID = "interest_method_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    InterestMethodEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    InterestMethodEnum(String literal, String description, Long enumType) {
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
