package com.angkorteam.fintech.client.renums;

public enum DataTableCategoryEnum {

    DEFAULT("100", "Default"),
    PPI("200", "PPI");

    private final String literal;

    private final String description;

    DataTableCategoryEnum(String literal, String description) {
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
