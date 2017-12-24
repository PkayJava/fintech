package com.angkorteam.fintech.helper.loan;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class DisburseBuilder {

    private String id;
    private boolean hasId;

    public DisburseBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public DisburseBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public DisburseBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String paymentTypeId;
    private boolean hasPaymentTypeId;

    public DisburseBuilder withPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        this.hasPaymentTypeId = true;
        return this;
    }

    private Date actualDisbursementDate;
    private boolean hasActualDisbursementDate;

    public DisburseBuilder withActualDisbursementDate(Date actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
        this.hasActualDisbursementDate = true;
        return this;
    }

    private Double transactionAmount;
    private boolean hasTransactionAmount;

    public DisburseBuilder withTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.hasTransactionAmount = true;
        return this;
    }

    private String accountNumber;
    private boolean hasAccountNumber;

    public DisburseBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        this.hasAccountNumber = true;
        return this;
    }

    private String checkNumber;
    private boolean hasCheckNumber;

    public DisburseBuilder withCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
        this.hasCheckNumber = true;
        return this;
    }

    private String routingCode;
    private boolean hasRoutingCode;

    public DisburseBuilder withRoutingCode(String routingCode) {
        this.routingCode = routingCode;
        this.hasRoutingCode = true;
        return this;
    }

    private String receiptNumber;
    private boolean hasReceiptNumber;

    public DisburseBuilder withReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        this.hasReceiptNumber = true;
        return this;
    }

    private String bankNumber;
    private boolean hasBankNumber;

    public DisburseBuilder withBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
        this.hasBankNumber = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public DisburseBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

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

        if (this.hasPaymentTypeId) {
            object.getObject().put("paymentTypeId", this.paymentTypeId);
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
