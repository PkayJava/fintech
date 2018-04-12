package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.github.openunirest.http.JsonNode;

public class CloseBuilder {

    private String id;
    private boolean hasId;

    public CloseBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public CloseBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public CloseBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date transactionDate;
    private boolean hasTransactionDate;

    public CloseBuilder withTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        this.hasTransactionDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public CloseBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasTransactionDate) {
            if (this.transactionDate != null) {
                object.getObject().put("transactionDate", DateFormatUtils.format(this.transactionDate, this.dateFormat));
            } else {
                object.getObject().put("transactionDate", (String) null);
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
