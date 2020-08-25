package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PostEmailRequest {

    private String name;

    private long groupId;

    private long clientId;

    private long staffId;

    private String emailMessage;

    private String emailSubject;

    private Date startDateTime;

    private long emailAttachmentFileFormatId;

    private String emailRecipients;

    private long stretchyReportId;

    public long getStretchyReportId() {
        return stretchyReportId;
    }

    public void setStretchyReportId(long stretchyReportId) {
        this.stretchyReportId = stretchyReportId;
    }

    public String getEmailRecipients() {
        return emailRecipients;
    }

    public void setEmailRecipients(String emailRecipients) {
        this.emailRecipients = emailRecipients;
    }

    public long getEmailAttachmentFileFormatId() {
        return emailAttachmentFileFormatId;
    }

    public void setEmailAttachmentFileFormatId(long emailAttachmentFileFormatId) {
        this.emailAttachmentFileFormatId = emailAttachmentFileFormatId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

}
