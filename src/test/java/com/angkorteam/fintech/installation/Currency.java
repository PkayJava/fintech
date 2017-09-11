package com.angkorteam.fintech.installation;

public enum Currency {

    USD("USD"),
    VND("VND"),
    KHR("KHR"),
    MYR("MYR"),
    SGD("SGD"),
    THB("THB");

    private String literal;

    Currency(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

}
