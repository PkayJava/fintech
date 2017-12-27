package com.angkorteam.fintech.helper.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class DisburseSavingBuilder {

    private String id;
    private boolean hasId;

    public DisburseSavingBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public DisburseSavingBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public DisburseSavingBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date actualDisbursementDate;
    private boolean hasActualDisbursementDate;

    public DisburseSavingBuilder withActualDisbursementDate(Date actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
        this.hasActualDisbursementDate = true;
        return this;
    }

    private Double transactionAmount;
    private boolean hasTransactionAmount;

    public DisburseSavingBuilder withTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.hasTransactionAmount = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public DisburseSavingBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasTransactionAmount) {
            object.getObject().put("transactionAmount", this.transactionAmount);
        }

        if (this.hasActualDisbursementDate) {
            if (this.actualDisbursementDate != null) {
                object.getObject().put("actualDisbursementDate", DateFormatUtils.format(this.actualDisbursementDate, this.dateFormat));
            } else {
                object.getObject().put("actualDisbursementDate", (String) null);
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
