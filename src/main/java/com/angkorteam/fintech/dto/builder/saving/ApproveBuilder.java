package com.angkorteam.fintech.dto.builder.saving;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class ApproveBuilder {

    private String id;
    private boolean hasId;

    public ApproveBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private Date approvedOnDate;
    private boolean hasApprovedOnDate;

    public ApproveBuilder withApprovedOnDate(Date approvedOnDate) {
        this.approvedOnDate = approvedOnDate;
        this.hasApprovedOnDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public ApproveBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ApproveBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ApproveBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasApprovedOnDate) {
            if (this.approvedOnDate != null) {
                object.getObject().put("approvedOnDate", DateFormatUtils.format(this.approvedOnDate, this.dateFormat));
            } else {
                object.getObject().put("approvedOnDate", (String) null);
            }
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        return object;
    }

}
