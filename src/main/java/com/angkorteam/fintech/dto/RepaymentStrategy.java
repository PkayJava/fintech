package com.angkorteam.fintech.dto;

public enum RepaymentStrategy {
    
    Penalties("1", "Penalties, Fees, Interest, Principal order"), 
    Overdue("4", "Overdue/Due Fee/Interest, Principal"),
    Principal("5","Principal, Interest, Penalties, Fees Order"),
    Interest("6", "Interest, Principal, Penalties, Fees Order"),
    Early("7","Early Repayment Strategy"),
    HeavensFamily("2","HeavensFamily Unique"),
    Creocore("3","Creocore Unique");

    private String literal;

    private String description;

    RepaymentStrategy(String literal, String description) {
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
