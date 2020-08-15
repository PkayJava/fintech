package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostJournalEntriesRequest {

    private long officeId;

    private long accountingRule;

    private String currencyCode;

    private long paymentTypeId;

    private String accountNumber;

    private String checkNumber;

    private String routingCode;

    private String receiptNumber;

    private String bankNumber;

    private String referenceNumber;

    private Date transactionDate;

    private double amount;

    private List<TransactionEntry> debits = new ArrayList<>();

    private List<TransactionEntry> credits = new ArrayList<>();

    @SerializedName("comments")
    private String comment;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<TransactionEntry> getDebits() {
        return debits;
    }

    public void setDebits(List<TransactionEntry> debits) {
        this.debits = debits;
    }

    public List<TransactionEntry> getCredits() {
        return credits;
    }

    public void setCredits(List<TransactionEntry> credits) {
        this.credits = credits;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public long getAccountingRule() {
        return accountingRule;
    }

    public void setAccountingRule(long accountingRule) {
        this.accountingRule = accountingRule;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public static class TransactionEntry {

        private long glAccountId;

        private double amount;

        @SerializedName("comments")
        private String comment;

        public TransactionEntry() {
        }

        public TransactionEntry(long glAccountId, double amount, String comment) {
            this.glAccountId = glAccountId;
            this.amount = amount;
            this.comment = comment;
        }

        public long getGlAccountId() {
            return glAccountId;
        }

        public void setGlAccountId(long glAccountId) {
            this.glAccountId = glAccountId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }

}
