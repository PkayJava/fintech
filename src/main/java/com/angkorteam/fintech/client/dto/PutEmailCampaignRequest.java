package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PutEmailCampaignRequest {

    private long businessRuleId;

    private String campaignName;

    private String emailMessage;

    private String paramValue;

    private int campaignType;

    private String recurrence;

    private Date recurrenceStartDate;

    public long getBusinessRuleId() {
        return businessRuleId;
    }

    public void setBusinessRuleId(long businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public int getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(int campaignType) {
        this.campaignType = campaignType;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public Date getRecurrenceStartDate() {
        return recurrenceStartDate;
    }

    public void setRecurrenceStartDate(Date recurrenceStartDate) {
        this.recurrenceStartDate = recurrenceStartDate;
    }

}
