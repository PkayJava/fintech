package com.angkorteam.fintech.dto.builder.saving;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class CloseBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public CloseBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private boolean postInterestValidationOnClosure;
    private boolean hasPostInterestValidationOnClosure;

    public CloseBuilder withPostInterestValidationOnClosure(boolean postInterestValidationOnClosure) {
        this.postInterestValidationOnClosure = postInterestValidationOnClosure;
        this.hasPostInterestValidationOnClosure = true;
        return this;
    }

    private boolean withdrawBalance;
    private boolean hasWithdrawBalance;

    public CloseBuilder withWithdrawBalance(boolean withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
        this.hasWithdrawBalance = true;
        return this;
    }

    private String paymentTypeId;
    private boolean hasPaymentTypeId;

    public CloseBuilder withPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        this.hasPaymentTypeId = true;
        return this;
    }

    private Date closedOnDate;
    private boolean hasClosedOnDate;

    public CloseBuilder withClosedOnDate(Date closedOnDate) {
        this.closedOnDate = closedOnDate;
        this.hasClosedOnDate = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public CloseBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    private String accountNumber;
    private boolean hasAccountNumber;

    public CloseBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        this.hasAccountNumber = true;
        return this;
    }

    private String checkNumber;
    private boolean hasCheckNumber;

    public CloseBuilder withCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
        this.hasCheckNumber = true;
        return this;
    }

    private String routingCode;
    private boolean hasRoutingCode;

    public CloseBuilder withRoutingCode(String routingCode) {
        this.routingCode = routingCode;
        this.hasRoutingCode = true;
        return this;
    }

    private String receiptNumber;
    private boolean hasReceiptNumber;

    public CloseBuilder withReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        this.hasReceiptNumber = true;
        return this;
    }

    private String bankNumber;
    private boolean hasBankNumber;

    public CloseBuilder withBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
        this.hasBankNumber = true;
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

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasBankNumber) {
            object.getObject().put("bankNumber", this.bankNumber);
        }

        if (this.hasReceiptNumber) {
            object.getObject().put("receiptNumber", this.receiptNumber);
        }

        if (this.hasRoutingCode) {
            object.getObject().put("routingCode", this.routingCode);
        }

        if (this.hasCheckNumber) {
            object.getObject().put("checkNumber", this.checkNumber);
        }

        if (this.hasAccountNumber) {
            object.getObject().put("accountNumber", this.accountNumber);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasClosedOnDate) {
            if (this.closedOnDate != null) {
                object.getObject().put("closedOnDate", DateFormatUtils.format(this.closedOnDate, this.dateFormat));
            } else {
                object.getObject().put("closedOnDate", (String) null);
            }
        }

        if (this.hasPaymentTypeId) {
            object.getObject().put("paymentTypeId", this.paymentTypeId);
        }

        if (this.hasWithdrawBalance) {
            object.getObject().put("withdrawBalance", this.withdrawBalance);
        }

        if (this.hasPostInterestValidationOnClosure) {
            object.getObject().put("postInterestValidationOnClosure", this.postInterestValidationOnClosure);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        return object;
    }

}
