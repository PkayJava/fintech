package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PostEmailCampaignRequest {

    private long businessRuleId;

    private long stretchyReportId;

    private String campaignName;

    private long campaignType;

    private String paramValue;

    private String emailSubject;

    private String emailMessage;

    private String stretchyReportParamMap;

    private int emailAttachmentFileFormatId;

    private Date submittedOnDate;

    private String recurrence;

    private Date recurrenceStartDate;

    public long getBusinessRuleId() {
        return businessRuleId;
    }

    public void setBusinessRuleId(long businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

    public long getStretchyReportId() {
        return stretchyReportId;
    }

    public void setStretchyReportId(long stretchyReportId) {
        this.stretchyReportId = stretchyReportId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public long getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(long campaignType) {
        this.campaignType = campaignType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getStretchyReportParamMap() {
        return stretchyReportParamMap;
    }

    public void setStretchyReportParamMap(String stretchyReportParamMap) {
        this.stretchyReportParamMap = stretchyReportParamMap;
    }

    public int getEmailAttachmentFileFormatId() {
        return emailAttachmentFileFormatId;
    }

    public void setEmailAttachmentFileFormatId(int emailAttachmentFileFormatId) {
        this.emailAttachmentFileFormatId = emailAttachmentFileFormatId;
    }

    public Date getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
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
