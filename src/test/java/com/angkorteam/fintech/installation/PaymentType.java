package com.angkorteam.fintech.installation;

public enum PaymentType {
    Cash("Cash", true, 1),
    VisaCard("Visa", false, 2),
    MasterCard("Master Card", false, 3),
    Cheque("Cheque", false, 4);

    private String name;
    private boolean cash;
    private int position;

    PaymentType(String name, boolean cash, int position) {
        this.name = name;
        this.cash = cash;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public boolean isCash() {
        return cash;
    }

    public int getPosition() {
        return position;
    }


}
