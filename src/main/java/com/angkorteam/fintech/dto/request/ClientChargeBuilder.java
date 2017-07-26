package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

public class ClientChargeBuilder implements Serializable {

    private double amount;
    private boolean hasAmount;

    private String chargeId;
    private boolean hasChargeId;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date dueDate;
    private boolean hasDueDate;

    private String locale = "en";
    private boolean hasLocale = true;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }
        if (this.hasChargeId) {
            object.getObject().put("chargeId", this.chargeId);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
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
        return object;
    }

    public ClientChargeBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public ClientChargeBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.hasDueDate = true;
        return this;
    }

    public ClientChargeBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public ClientChargeBuilder withAmount(double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    public ClientChargeBuilder withChargeId(String chargeId) {
        this.chargeId = chargeId;
        this.hasChargeId = true;
        return this;
    }

}
