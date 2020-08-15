package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.GLAccountType;

import java.util.Date;

public class PostTaxesComponentsRequest {

    private String name;

    private double percentage;

    private GLAccountType creditAccountType;

    private long creditAccountId;

    private GLAccountType debitAccountType;

    private long debitAccountId;

    private Date startDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public GLAccountType getCreditAccountType() {
        return creditAccountType;
    }

    public void setCreditAccountType(GLAccountType creditAccountType) {
        this.creditAccountType = creditAccountType;
    }

    public long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public GLAccountType getDebitAccountType() {
        return debitAccountType;
    }

    public void setDebitAccountType(GLAccountType debitAccountType) {
        this.debitAccountType = debitAccountType;
    }

    public long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
