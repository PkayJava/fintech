package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class LoanOfficerUnAssignBuilder {

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanOfficerUnAssignBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanOfficerUnAssignBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String loanId;
    private boolean hasLoanId;

    public LoanOfficerUnAssignBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private Date unassignedDate;
    private boolean hasUnassignedDate;

    public LoanOfficerUnAssignBuilder withUnassignedDate(Date unassignedDate) {
        this.unassignedDate = unassignedDate;
        this.hasUnassignedDate = true;
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

        if (this.hasUnassignedDate) {
            if (this.unassignedDate != null) {
                object.getObject().put("unassignedDate", DateFormatUtils.format(this.unassignedDate, this.dateFormat));
            } else {
                object.getObject().put("unassignedDate", (String) null);
            }
        }

        return object;
    }

}
