package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class LoanOfficerAssignBuilder {

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanOfficerAssignBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanOfficerAssignBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String loanId;
    private boolean hasLoanId;

    public LoanOfficerAssignBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private String toLoanOfficerId;
    private boolean hasToLoanOfficerId;

    public LoanOfficerAssignBuilder withToLoanOfficerId(String toLoanOfficerId) {
        this.toLoanOfficerId = toLoanOfficerId;
        this.hasToLoanOfficerId = true;
        return this;
    }

    private Date assignmentDate;
    private boolean hasAssignmentDate;

    public LoanOfficerAssignBuilder withAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
        this.hasAssignmentDate = true;
        return this;
    }

    private String fromLoanOfficerId;
    private boolean hasFromLoanOfficerId;

    public LoanOfficerAssignBuilder withFromLoanOfficerId(String fromLoanOfficerId) {
        this.fromLoanOfficerId = fromLoanOfficerId;
        this.hasFromLoanOfficerId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLoanId) {
            object.getObject().put("loanId", this.loanId);
        }

        if (this.hasToLoanOfficerId) {
            object.getObject().put("toLoanOfficerId", this.toLoanOfficerId);
        }

        if (this.hasFromLoanOfficerId) {
            object.getObject().put("fromLoanOfficerId", this.fromLoanOfficerId);
        }

        if (this.hasAssignmentDate) {
            if (this.assignmentDate != null) {
                object.getObject().put("assignmentDate", DateFormatUtils.format(this.assignmentDate, this.dateFormat));
            } else {
                object.getObject().put("assignmentDate", (String) null);
            }
        }

        return object;
    }

}
