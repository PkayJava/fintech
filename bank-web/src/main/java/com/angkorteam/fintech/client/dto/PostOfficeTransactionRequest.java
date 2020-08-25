package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PostOfficeTransactionRequest {

    private Long fromOfficeId;

    private Long toOfficeId;

    private Date transactionDate;

    /**
     * limit to 3
     */
    private String currencyCode;

    /**
     * greater than 0
     */
    private double transactionAmount;

    /**
     * limit to 100
     */
    private String description;

    public Long getFromOfficeId() {
        return fromOfficeId;
    }

    public void setFromOfficeId(Long fromOfficeId) {
        this.fromOfficeId = fromOfficeId;
    }

    public Long getToOfficeId() {
        return toOfficeId;
    }

    public void setToOfficeId(Long toOfficeId) {
        this.toOfficeId = toOfficeId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
