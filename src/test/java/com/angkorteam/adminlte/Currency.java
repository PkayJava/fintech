package com.angkorteam.adminlte;

public enum Currency {

    USD("USD"), KHR("KHR"), MYR("MYR"), SGD("SGD"), THB("THB");

    private String literal;

    private Currency(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

}
