package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

public class PostAdhocRequest {

    private String name;

    private String query;

    private String tableName;

    private String tableFields;

    private String email;

    private int reportRunFrequency;

    private int reportRunEvery;

    @SerializedName("isActive")
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableFields() {
        return tableFields;
    }

    public void setTableFields(String tableFields) {
        this.tableFields = tableFields;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReportRunFrequency() {
        return reportRunFrequency;
    }

    public void setReportRunFrequency(int reportRunFrequency) {
        this.reportRunFrequency = reportRunFrequency;
    }

    public int getReportRunEvery() {
        return reportRunEvery;
    }

    public void setReportRunEvery(int reportRunEvery) {
        this.reportRunEvery = reportRunEvery;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
