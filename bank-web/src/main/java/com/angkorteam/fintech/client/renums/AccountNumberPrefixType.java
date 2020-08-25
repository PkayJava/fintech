package com.angkorteam.fintech.client.renums;

public enum AccountNumberPrefixType {

    OfficeName("1", "Office Name"),
    ClientType("101", "Client Type"),
    LoanProductShortName("201", "Loan Product Short Name"),
    SavingProductShortName("301", "Saving Product Short Name");

    public static final String ID = "";

    private final String literal;

    private final String description;

    private final Long enumType;

    AccountNumberPrefixType(String literal, String description) {
        this(literal, description, 0l);
    }

    AccountNumberPrefixType(String literal, String description, Long enumType) {
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
