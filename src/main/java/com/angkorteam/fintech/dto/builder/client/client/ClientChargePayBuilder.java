package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import io.github.openunirest.http.JsonNode;

public class ClientChargePayBuilder implements Serializable {

    private String clientId;
    private boolean hasClientId;

    public ClientChargePayBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String chargeId;
    private boolean hasChargeId;

    public ClientChargePayBuilder withChargeId(String chargeId) {
        this.chargeId = chargeId;
        this.hasChargeId = true;
        return this;
    }

    private Double amount;
    private boolean hasAmount;

    public ClientChargePayBuilder withAmount(Double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ClientChargePayBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ClientChargePayBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date transactionDate;
    private boolean hasTransactionDate;

    public ClientChargePayBuilder withTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        this.hasTransactionDate = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasChargeId) {
            object.getObject().put("chargeId", this.chargeId);
        }

        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }

        if (this.hasTransactionDate) {
            if (this.transactionDate != null) {
                object.getObject().put("transactionDate", DateFormatUtils.format(this.transactionDate, this.dateFormat));
            } else {
                object.getObject().put("transactionDate", (String) null);
            }
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        return object;
    }

}
