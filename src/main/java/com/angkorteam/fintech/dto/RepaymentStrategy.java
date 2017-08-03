package com.angkorteam.fintech.dto;

public enum RepaymentStrategy {
    
    Penalties("Penalties", "Penalties, Fees, Interest, Principal order"), 
    Overdue("Overdue", "Overdue/Due Fee/Interest, Principal"),
    Principal("Principal","Principal, Interest, Penalties, Fees Order"),
    Interest("Interest", "Interest, Principal, Penalties, Fees Order"),
    Early("Early","Early Repayment Strategy"),
    HeavensFamily("HeavensFamily","HeavensFamily Unique"),
    Creocore("Creocore","Creocore Unique");

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
