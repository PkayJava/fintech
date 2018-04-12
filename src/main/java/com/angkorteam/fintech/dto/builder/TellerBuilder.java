package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.angkorteam.fintech.dto.constant.TellerStatus;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/14/17.
 */
public class TellerBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String officeId;
    private boolean hasOfficeId;

    private String description;
    private boolean hasDescription;

    private Date endDate;
    private boolean hasEndDate;

    private TellerStatus status;
    private boolean hasStatus;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date startDate;
    private boolean hasStartDate;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasStatus) {
            if (this.status != null) {
                object.getObject().put("status", this.status.getLiteral());
            } else {
                object.getObject().put("status", (String) null);
            }
        }
        if (this.hasStartDate) {
            if (this.startDate != null) {
                object.getObject().put("startDate", DateFormatUtils.format(this.startDate, this.dateFormat));
            } else {
                object.getObject().put("startDate", (String) null);
            }
        }
        if (this.hasEndDate) {
            if (this.endDate != null) {
                object.getObject().put("endDate", DateFormatUtils.format(this.endDate, this.dateFormat));
            } else {
                object.getObject().put("endDate", (String) null);
            }
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public TellerBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public TellerBuilder withStatus(TellerStatus status) {
        this.status = status;
        this.hasStatus = true;
        return this;
    }

    public TellerBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        this.hasStartDate = true;
        return this;
    }

    public TellerBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public TellerBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        this.hasEndDate = true;
        return this;
    }

    public TellerBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public TellerBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public TellerBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public TellerBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

}
