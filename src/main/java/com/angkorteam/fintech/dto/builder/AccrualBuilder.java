package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccrualBuilder implements Serializable {

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date tillDate;
    private boolean hasTillDate;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasTillDate) {
            if (this.tillDate == null) {
                object.getObject().put("tillDate", (String) null);
            } else {
                object.getObject().put("tillDate", DateFormatUtils.format(this.tillDate, this.dateFormat));
            }
        }
        return object;
    }

    public AccrualBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public AccrualBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public AccrualBuilder withTillDate(Date tillDate) {
        this.tillDate = tillDate;
        this.hasTillDate = true;
        return this;
    }
}
