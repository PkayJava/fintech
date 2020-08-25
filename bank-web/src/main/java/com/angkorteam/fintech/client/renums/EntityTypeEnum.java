package com.angkorteam.fintech.client.renums;

public enum EntityTypeEnum {

    Office("1", "Office"),
    Loan("2", "Loan Product"),
    Saving("3", "Saving Product"),
    FeeCharge("4", "Fee & Charge"),
    Role("5", "Role");

    private final String literal;

    private final String description;

    EntityTypeEnum(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

}
