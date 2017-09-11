package com.angkorteam.fintech.installation;

public enum Fund {

    Fund01("Housing Loan Fund"),
    Fund02("Agriculture Loan Fund"),
    Fund03("Agriculture Loan Fund"),
    Fund04("Farming Loan Fund"),
    Fund05("Vision Fund"),
    Fund07("Study Loan Fund"),
    Fund06("SME Loan Fund");
    private String name;

    Fund(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
