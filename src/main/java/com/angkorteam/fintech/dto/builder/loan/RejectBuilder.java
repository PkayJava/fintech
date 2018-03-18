package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class RejectBuilder {

    private String id;
    private boolean hasId;

    public RejectBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public RejectBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public RejectBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date rejectedOnDate;
    private boolean hasRejectedOnDate;

    public RejectBuilder withRejectedOnDate(Date rejectedOnDate) {
        this.rejectedOnDate = rejectedOnDate;
        this.hasRejectedOnDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public RejectBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasRejectedOnDate) {
            if (this.rejectedOnDate != null) {
                object.getObject().put("rejectedOnDate", DateFormatUtils.format(this.rejectedOnDate, this.dateFormat));
            } else {
                object.getObject().put("rejectedOnDate", (String) null);
            }
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
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
