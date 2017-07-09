package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class GLEntryBuilder implements Serializable {

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private String officeId;
    private boolean hasOfficeId;

    private Date transactionDate;
    private boolean hasTransactionDate;

    private String referenceNumber;
    private boolean hasReferenceNumber;

    private String comments;
    private boolean hasComments;

    private String currencyCode;
    private boolean hasCurrencyCode;

    private String paymentTypeId;
    private boolean hasPaymentTypeId;

    private String accountNumber;
    private boolean hasAccountNumber;

    private String checkNumber;
    private boolean hasCheckNumber;

    private String routingCode;
    private boolean hasRoutingCode;

    private String receiptNumber;
    private boolean hasReceiptNumber;

    private String bankNumber;
    private boolean hasBankNumber;

    private List<Map<String, Object>> credits = Lists.newArrayList();
    private boolean hasCredits;

    private List<Map<String, Object>> debits = Lists.newArrayList();
    private boolean hasDebits;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasTransactionDate) {
            object.getObject().put("transactionDate", DateFormatUtils.format(this.transactionDate, this.dateFormat));
        }
        if (this.hasReferenceNumber) {
            object.getObject().put("referenceNumber", this.referenceNumber);
        }
        if (this.hasComments) {
            object.getObject().put("comments", this.comments);
        }
        if (this.hasCurrencyCode) {
            object.getObject().put("currencyCode", this.currencyCode);
        }
        if (this.hasPaymentTypeId) {
            object.getObject().put("paymentTypeId", this.paymentTypeId);
        }
        if (this.hasAccountNumber) {
            object.getObject().put("accountNumber", this.accountNumber);
        }
        if (this.hasCheckNumber) {
            object.getObject().put("checkNumber", this.checkNumber);
        }
        if (this.hasRoutingCode) {
            object.getObject().put("routingCode", this.routingCode);
        }
        if (this.hasReceiptNumber) {
            object.getObject().put("receiptNumber", this.receiptNumber);
        }
        if (this.hasBankNumber) {
            object.getObject().put("bankNumber", this.bankNumber);
        }
        if (this.hasCredits) {
            object.getObject().put("credits", this.credits);
        }
        if (this.hasDebits) {
            object.getObject().put("debits", this.debits);
        }
        return object;
    }

    public GLEntryBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public GLEntryBuilder withDateFormat(String dateFormat) {
        this.hasDateFormat = true;
        this.dateFormat = dateFormat;
        return this;
    }

    public GLEntryBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public GLEntryBuilder withTransactionDate(Date transactionDate) {
        this.hasTransactionDate = true;
        this.transactionDate = transactionDate;
        return this;
    }

    public GLEntryBuilder withReferenceNumber(String referenceNumber) {
        this.hasReferenceNumber = true;
        this.referenceNumber = referenceNumber;
        return this;
    }

    public GLEntryBuilder withComment(String comment) {
        this.hasComments = true;
        this.comments = comment;
        return this;
    }

    public GLEntryBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    public GLEntryBuilder withPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        this.hasPaymentTypeId = true;
        return this;
    }

    public GLEntryBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        this.hasAccountNumber = true;
        return this;
    }

    public GLEntryBuilder withCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
        this.hasCheckNumber = true;
        return this;
    }

    public GLEntryBuilder withRoutingCode(String routingCode) {
        this.routingCode = routingCode;
        this.hasRoutingCode = true;
        return this;
    }

    public GLEntryBuilder withReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        this.hasReceiptNumber = true;
        return this;
    }

    public GLEntryBuilder withBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
        this.hasBankNumber = true;
        return this;
    }

    public GLEntryBuilder withCredit(String glAccountId, Double amount) {
        this.hasCredits = true;
        Map<String, Object> account = Maps.newHashMap();
        account.put("glAccountId", glAccountId);
        account.put("amount", amount);
        this.credits.add(account);
        return this;
    }

    public GLEntryBuilder withDebit(String glAccountId, Double amount) {
        this.hasDebits = true;
        Map<String, Object> account = Maps.newHashMap();
        account.put("glAccountId", glAccountId);
        account.put("amount", amount);
        this.debits.add(account);
        return this;
    }

}
