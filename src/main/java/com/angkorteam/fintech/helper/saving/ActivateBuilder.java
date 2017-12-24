package com.angkorteam.fintech.helper.saving;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class ActivateBuilder {

    private String id;
    private boolean hasId;

    public ActivateBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private Date activatedOnDate;
    private boolean hasActivatedOnDate;

    public ActivateBuilder withActivatedOnDate(Date activatedOnDate) {
        this.activatedOnDate = activatedOnDate;
        this.hasActivatedOnDate = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ActivateBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ActivateBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasActivatedOnDate) {
            if (this.activatedOnDate != null) {
                object.getObject().put("activatedOnDate", DateFormatUtils.format(this.activatedOnDate, this.dateFormat));
            } else {
                object.getObject().put("activatedOnDate", (String) null);
            }
        }

        return object;
    }

}
