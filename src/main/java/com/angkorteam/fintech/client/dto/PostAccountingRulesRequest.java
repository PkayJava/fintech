package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.List;

public class PostAccountingRulesRequest {

    private String name;

    private long officeId;

    private long accountToDebit;

    private long accountToCredit;

    private String description;

    private List<String> debitTags = new ArrayList<>();

    private List<String> creditTags = new ArrayList<>();

    private boolean allowMultipleDebitEntries;

    private boolean allowMultipleCreditEntries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public long getAccountToDebit() {
        return accountToDebit;
    }

    public void setAccountToDebit(long accountToDebit) {
        this.accountToDebit = accountToDebit;
    }

    public long getAccountToCredit() {
        return accountToCredit;
    }

    public void setAccountToCredit(long accountToCredit) {
        this.accountToCredit = accountToCredit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDebitTags() {
        return debitTags;
    }

    public void setDebitTags(List<String> debitTags) {
        this.debitTags = debitTags;
    }

    public List<String> getCreditTags() {
        return creditTags;
    }

    public void setCreditTags(List<String> creditTags) {
        this.creditTags = creditTags;
    }

    public boolean isAllowMultipleDebitEntries() {
        return allowMultipleDebitEntries;
    }

    public void setAllowMultipleDebitEntries(boolean allowMultipleDebitEntries) {
        this.allowMultipleDebitEntries = allowMultipleDebitEntries;
    }

    public boolean isAllowMultipleCreditEntries() {
        return allowMultipleCreditEntries;
    }

    public void setAllowMultipleCreditEntries(boolean allowMultipleCreditEntries) {
        this.allowMultipleCreditEntries = allowMultipleCreditEntries;
    }

}
