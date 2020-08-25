package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.*;

import java.util.Date;

public class PostChargesRequest {

    private long incomeAccountId;

    private long taxGroupId;

    private String name;

    private double amount;

    private String currencyCode;

    private ChargeAppliesTo chargeAppliesTo;

    private ChargeTimeType chargeTimeType;

    private ChargeCalculationType chargeCalculationType;

    private ChargePaymentMode chargePaymentMode;

    private boolean penalty;

    private boolean active;

    private Date feeOnMonthDay;

    private int feeInterval;

    private double minCap;

    private double maxCap;

    private ChargeFrequency feeFrequency;

    public long getIncomeAccountId() {
        return incomeAccountId;
    }

    public void setIncomeAccountId(long incomeAccountId) {
        this.incomeAccountId = incomeAccountId;
    }

    public long getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(long taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ChargeAppliesTo getChargeAppliesTo() {
        return chargeAppliesTo;
    }

    public void setChargeAppliesTo(ChargeAppliesTo chargeAppliesTo) {
        this.chargeAppliesTo = chargeAppliesTo;
    }

    public ChargeTimeType getChargeTimeType() {
        return chargeTimeType;
    }

    public void setChargeTimeType(ChargeTimeType chargeTimeType) {
        this.chargeTimeType = chargeTimeType;
    }

    public ChargeCalculationType getChargeCalculationType() {
        return chargeCalculationType;
    }

    public void setChargeCalculationType(ChargeCalculationType chargeCalculationType) {
        this.chargeCalculationType = chargeCalculationType;
    }

    public ChargePaymentMode getChargePaymentMode() {
        return chargePaymentMode;
    }

    public void setChargePaymentMode(ChargePaymentMode chargePaymentMode) {
        this.chargePaymentMode = chargePaymentMode;
    }

    public boolean isPenalty() {
        return penalty;
    }

    public void setPenalty(boolean penalty) {
        this.penalty = penalty;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getFeeOnMonthDay() {
        return feeOnMonthDay;
    }

    public void setFeeOnMonthDay(Date feeOnMonthDay) {
        this.feeOnMonthDay = feeOnMonthDay;
    }

    public int getFeeInterval() {
        return feeInterval;
    }

    public void setFeeInterval(int feeInterval) {
        this.feeInterval = feeInterval;
    }

    public double getMinCap() {
        return minCap;
    }

    public void setMinCap(double minCap) {
        this.minCap = minCap;
    }

    public double getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(double maxCap) {
        this.maxCap = maxCap;
    }

    public ChargeFrequency getFeeFrequency() {
        return feeFrequency;
    }

    public void setFeeFrequency(ChargeFrequency feeFrequency) {
        this.feeFrequency = feeFrequency;
    }
}
