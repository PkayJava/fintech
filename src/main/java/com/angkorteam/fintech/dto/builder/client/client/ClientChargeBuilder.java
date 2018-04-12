package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import io.github.openunirest.http.JsonNode;

public class ClientChargeBuilder implements Serializable {

    private String clientId;
    private boolean hasClientId;

    public ClientChargeBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String chargeId;
    private boolean hasChargeId;

    public ClientChargeBuilder withChargeId(String chargeId) {
        this.chargeId = chargeId;
        this.hasChargeId = true;
        return this;
    }

    private Double amount;
    private boolean hasAmount;

    public ClientChargeBuilder withAmount(Double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ClientChargeBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ClientChargeBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date dueDate;
    private boolean hasDueDate;

    public ClientChargeBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.hasDueDate = true;
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

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        return object;
    }

}
