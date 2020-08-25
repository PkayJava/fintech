package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PostJournalEntryDefineRequest {

    private Date transactionDate;

    private long officeId;

    private String currencyCode;

    @SerializedName("comments")
    private String comment;

    private String referenceNumber;

    private int accountingRule;

    private long paymentTypeId;

    private double credits;

    private double debits;

    private double amount;



}
