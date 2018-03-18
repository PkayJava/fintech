package com.angkorteam.fintech.dto.builder.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class LoanChargeBuilder {

    private String loanId;
    private boolean hasLoanId;

    public LoanChargeBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private String chargeId;
    private boolean hasChargeId;

    public LoanChargeBuilder withChargeId(String chargeId) {
        this.chargeId = chargeId;
        this.hasChargeId = true;
        return this;
    }

    private Double amount;
    private boolean hasAmount;

    public LoanChargeBuilder withAmount(Double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    private Date dueDate;
    private boolean hasDueDate;

    public LoanChargeBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.hasDueDate = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanChargeBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanChargeBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasLoanId) {
            object.getObject().put("loanId", this.loanId);
        }

        if (this.hasChargeId) {
            object.getObject().put("chargeId", this.chargeId);
        }

        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }

        if (this.hasDueDate) {
            if (this.dueDate != null) {
                object.getObject().put("dueDate", DateFormatUtils.format(this.dueDate, this.dateFormat));
            } else {
                object.getObject().put("dueDate", (String) null);
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
