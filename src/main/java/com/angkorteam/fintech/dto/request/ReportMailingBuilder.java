package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class ReportMailingBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
    private boolean hasDateFormat = true;

    private String name;
    private boolean hasName;

    private String description;
    private boolean hasDescription;

    private Date startDateTime;
    private boolean hasStartDateTime;

    private String stretchyReportId;
    private boolean hasStretchyReportId;

    private String emailRecipients;
    private boolean hasEmailRecipients;

    private String emailSubject;
    private boolean hasEmailSubject;

    private String emailMessage;
    private boolean hasEmailMessage;

    private String emailAttachmentFileFormatId;
    private boolean hasEmailAttachmentFileFormatId;

    private String recurrence;
    private boolean hasRecurrence;

    // "isActive": true,
    private boolean active;
    private boolean hasActive;

    private String stretchyReportParamMap;
    private boolean hasStretchyReportParamMap;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasStartDateTime) {
            if (this.startDateTime != null) {
                object.getObject().put("startDateTime", DateFormatUtils.format(this.startDateTime, this.dateFormat));
            } else {
                object.getObject().put("startDateTime", (String) null);
            }
        }
        if (this.hasStretchyReportId) {
            object.getObject().put("stretchyReportId", this.stretchyReportId);
        }
        if (this.hasEmailRecipients) {
            object.getObject().put("emailRecipients", this.emailRecipients);
        }
        if (this.hasEmailSubject) {
            object.getObject().put("emailSubject", this.emailSubject);
        }
        if (this.hasEmailMessage) {
            object.getObject().put("emailMessage", this.emailMessage);
        }
        if (this.hasEmailAttachmentFileFormatId) {
            object.getObject().put("emailAttachmentFileFormatId", this.emailAttachmentFileFormatId);
        }
        if (this.hasRecurrence) {
            object.getObject().put("recurrence", this.recurrence);
        }
        if (this.hasActive) {
            object.getObject().put("isActive", this.active);
        }
        if (this.hasStretchyReportParamMap) {
            object.getObject().put("stretchyReportParamMap", this.stretchyReportParamMap);
        }
        return object;
    }

    public ReportMailingBuilder withStretchyReportParamMap(String stretchyReportParamMap) {
        this.stretchyReportParamMap = stretchyReportParamMap;
        this.hasStretchyReportParamMap = true;
        return this;
    }

    public ReportMailingBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public ReportMailingBuilder withRecurrence(String recurrence) {
        this.recurrence = recurrence;
        this.hasRecurrence = true;
        return this;
    }

    public ReportMailingBuilder withEmailAttachmentFileFormatId(String emailAttachmentFileFormatId) {
        this.emailAttachmentFileFormatId = emailAttachmentFileFormatId;
        this.hasEmailAttachmentFileFormatId = true;
        return this;
    }

    public ReportMailingBuilder withEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
        this.hasEmailMessage = true;
        return this;
    }

    public ReportMailingBuilder withEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
        this.hasEmailSubject = true;
        return this;
    }

    public ReportMailingBuilder withEmailRecipients(String emailRecipients) {
        this.emailRecipients = emailRecipients;
        this.hasEmailRecipients = true;
        return this;
    }

    public ReportMailingBuilder withStretchyReportId(String stretchyReportId) {
        this.stretchyReportId = stretchyReportId;
        this.hasStretchyReportId = true;
        return this;
    }

    public ReportMailingBuilder withStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
        this.hasStartDateTime = true;
        return this;
    }

    public ReportMailingBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public ReportMailingBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public ReportMailingBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public ReportMailingBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public ReportMailingBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

}
