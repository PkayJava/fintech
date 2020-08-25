package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

public class PostPaymentTypesRequest {

    private String name;

    private String description;

    @SerializedName("isCashPayment")
    private boolean cashPayment;

    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(boolean cashPayment) {
        this.cashPayment = cashPayment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
