package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PostProvisioningEntriesRequest {

    private Date date;

    @SerializedName("createjournalentries")
    private boolean createJournalEntry;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCreateJournalEntry() {
        return createJournalEntry;
    }

    public void setCreateJournalEntry(boolean createJournalEntry) {
        this.createJournalEntry = createJournalEntry;
    }

}
