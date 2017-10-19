package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class SavingAccountUnassignOfficerBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public SavingAccountUnassignOfficerBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private Date unassignedDate;
    private boolean hasunassignedDate;

    public SavingAccountUnassignOfficerBuilder withUnassignedDate(Date unassignedDate) {
        this.unassignedDate = unassignedDate;
        this.hasunassignedDate = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public SavingAccountUnassignOfficerBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public SavingAccountUnassignOfficerBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasunassignedDate) {
            if (this.unassignedDate != null) {
                object.getObject().put("unassignedDate", DateFormatUtils.format(this.unassignedDate, this.dateFormat));
            } else {
                object.getObject().put("unassignedDate", (String) null);
            }
        }

        return object;
    }

}
