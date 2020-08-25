package com.angkorteam.fintech.dto.builder.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.github.openunirest.http.JsonNode;

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

    private Date expectedDisbursementDate;
    private boolean hasExpectedDisbursementDate;

    public ApproveBuilder withExpectedDisbursementDate(Date expectedDisbursementDate) {
        this.expectedDisbursementDate = expectedDisbursementDate;
        this.hasExpectedDisbursementDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public ApproveBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    private Double approvedLoanAmount;
    private boolean hasApprovedLoanAmount;

    public ApproveBuilder withApprovedLoanAmount(Double approvedLoanAmount) {
        this.approvedLoanAmount = approvedLoanAmount;
        this.hasApprovedLoanAmount = true;
        return this;
    }

    private List<Map<String, Object>> disbursementData = new ArrayList<>();
    private boolean hasDisbursementData;

    public ApproveBuilder withdisbursementData(List<Map<String, Object>> disbursementData) {
        this.disbursementData = disbursementData;
        this.hasDisbursementData = true;
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

        if (this.hasDisbursementData) {
            object.getObject().put("disbursementData", this.disbursementData);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasApprovedLoanAmount) {
            object.getObject().put("approvedLoanAmount", this.approvedLoanAmount);
        }

        if (this.hasExpectedDisbursementDate) {
            if (this.expectedDisbursementDate != null) {
                object.getObject().put("expectedDisbursementDate", DateFormatUtils.format(this.expectedDisbursementDate, this.dateFormat));
            } else {
                object.getObject().put("expectedDisbursementDate", (String) null);
            }
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
