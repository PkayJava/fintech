package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.github.openunirest.http.JsonNode;

public class WithdrawBuilder {

    private String id;
    private boolean hasId;

    public WithdrawBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public WithdrawBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public WithdrawBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date withdrawnOnDateDate;
    private boolean hasWithdrawnOnDateDate;

    public WithdrawBuilder withWithdrawnOnDateDate(Date withdrawnOnDateDate) {
        this.withdrawnOnDateDate = withdrawnOnDateDate;
        this.hasWithdrawnOnDateDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public WithdrawBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasWithdrawnOnDateDate) {
            if (this.withdrawnOnDateDate != null) {
                object.getObject().put("withdrawnOnDateDate", DateFormatUtils.format(this.withdrawnOnDateDate, this.dateFormat));
            } else {
                object.getObject().put("withdrawnOnDateDate", (String) null);
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
