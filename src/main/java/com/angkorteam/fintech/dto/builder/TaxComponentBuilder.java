package com.angkorteam.fintech.dto.builder;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 7/17/17.
 */
public class TaxComponentBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private AccountType creditAccountType;
    private boolean hasCreditAccountType;

    private String creditAccountId;
    private boolean hasCreditAccountId;

    private double percentage;
    private boolean hasPercentage;

    private String name;
    private boolean hasName;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date startDate;
    private boolean hasStartDate;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasCreditAccountId) {
            object.getObject().put("creditAcountId", this.creditAccountId);
        }
        if (this.hasCreditAccountType) {
            if (this.creditAccountType != null) {
                object.getObject().put("creditAccountType", this.creditAccountType.getLiteral());
            } else {
                object.getObject().put("creditAccountType", (String) null);
            }
        }
        if (this.hasPercentage) {
            object.getObject().put("percentage", this.percentage);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasStartDate) {
            if (this.startDate != null) {
                object.getObject().put("startDate", DateFormatUtils.format(this.startDate, this.dateFormat));
            } else {
                object.getObject().put("startDate", (String) null);
            }
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public TaxComponentBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public TaxComponentBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        this.hasStartDate = true;
        return this;
    }

    public TaxComponentBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public TaxComponentBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public TaxComponentBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public TaxComponentBuilder withPercentage(double percentage) {
        this.percentage = percentage;
        this.hasPercentage = true;
        return this;
    }

    public TaxComponentBuilder withCreditAccountId(String creditAccountId) {
        this.creditAccountId = creditAccountId;
        this.hasCreditAccountId = true;
        return this;
    }

    public TaxComponentBuilder withCreditAccountType(AccountType creditAccountType) {
        this.creditAccountType = creditAccountType;
        this.hasCreditAccountType = true;
        return this;
    }

}
