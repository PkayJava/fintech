package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.github.openunirest.http.JsonNode;

public class RescheduleBuilder {

    private String loanId;
    private boolean hasLoanId;

    public RescheduleBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public RescheduleBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public RescheduleBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    public RescheduleBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    private Date rescheduleFromDate;
    private boolean hasRescheduleFromDate;

    public RescheduleBuilder withRescheduleFromDate(Date rescheduleFromDate) {
        this.rescheduleFromDate = rescheduleFromDate;
        this.hasRescheduleFromDate = true;
        return this;
    }

    private String rescheduleReasonId;
    private boolean hasRescheduleReasonId;

    public RescheduleBuilder withRescheduleReasonId(String rescheduleReasonId) {
        this.rescheduleReasonId = rescheduleReasonId;
        this.hasRescheduleReasonId = true;
        return this;
    }

    private Date adjustedDueDate;
    private boolean hasAdjustedDueDate;

    public RescheduleBuilder withAdjustedDueDate(Date adjustedDueDate) {
        this.adjustedDueDate = adjustedDueDate;
        this.hasAdjustedDueDate = true;
        return this;
    }

    private Double graceOnPrincipal;
    private boolean hasGraceOnPrincipal;

    public RescheduleBuilder withGraceOnPrincipal(Double graceOnPrincipal) {
        this.graceOnPrincipal = graceOnPrincipal;
        this.hasGraceOnPrincipal = true;
        return this;
    }

    private Double graceOnInterest;
    private boolean hasGraceOnInterest;

    public RescheduleBuilder withGraceOnInterest(Double graceOnInterest) {
        this.graceOnInterest = graceOnInterest;
        this.hasGraceOnInterest = true;
        return this;
    }

    private Long extraTerms;
    private boolean hasExtraTerms;

    public RescheduleBuilder withExtraTerms(Long extraTerms) {
        this.extraTerms = extraTerms;
        this.hasExtraTerms = true;
        return this;
    }

    private Double newInterestRate;
    private boolean hasNewInterestRate;

    public RescheduleBuilder withNewInterestRate(Double newInterestRate) {
        this.newInterestRate = newInterestRate;
        this.hasNewInterestRate = true;
        return this;
    }

    private String rescheduleReasonComment;
    private boolean hasRescheduleReasonComment;

    public RescheduleBuilder withRescheduleReasonComment(String rescheduleReasonComment) {
        this.rescheduleReasonComment = rescheduleReasonComment;
        this.hasRescheduleReasonComment = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasSubmittedOnDate) {
            object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
        }

        if (this.hasRescheduleFromDate) {
            object.getObject().put("rescheduleFromDate", DateFormatUtils.format(this.rescheduleFromDate, this.dateFormat));
        }

        if (this.hasAdjustedDueDate) {
            object.getObject().put("adjustedDueDate", DateFormatUtils.format(this.adjustedDueDate, this.dateFormat));
        }

        if (this.hasRescheduleReasonId) {
            object.getObject().put("rescheduleReasonId", this.rescheduleReasonId);
        }

        if (this.hasGraceOnPrincipal) {
            object.getObject().put("graceOnPrincipal", this.graceOnPrincipal);
        }

        if (this.hasGraceOnInterest) {
            object.getObject().put("graceOnInterest", this.graceOnInterest);
        }

        if (this.hasExtraTerms) {
            object.getObject().put("extraTerms", this.extraTerms);
        }

        if (this.hasNewInterestRate) {
            object.getObject().put("newInterestRate", this.newInterestRate);
        }

        if (this.hasRescheduleReasonComment) {
            object.getObject().put("rescheduleReasonComment", this.rescheduleReasonComment);
        }

        if (this.hasLoanId) {
            object.getObject().put("loanId", this.loanId);
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
