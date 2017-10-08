package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class HolidayBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private String name;
    private boolean hasName;

    private Date fromDate;
    private boolean hasFromDate;

    private Date toDate;
    private boolean hasToDate;

    private Date repaymentsRescheduledTo;
    private boolean hasRepaymentsRescheduledTo;

    private ReschedulingType reschedulingType;
    private boolean hasReschedulingType;

    private String description;
    private boolean hasDescription;

    private List<String> offices = Lists.newArrayList();
    private boolean hasOffices;

    public HolidayBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public HolidayBuilder withReschedulingType(ReschedulingType reschedulingType) {
        this.reschedulingType = reschedulingType;
        this.hasReschedulingType = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasReschedulingType) {
            if (this.reschedulingType != null) {
                object.getObject().put("reschedulingType", this.reschedulingType.getLiteral());
            } else {
                object.getObject().put("reschedulingType", (String) null);
            }
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasFromDate) {
            if (this.fromDate != null) {
                object.getObject().put("fromDate", DateFormatUtils.format(this.fromDate, this.dateFormat));
            } else {
                object.getObject().put("fromDate", (String) null);
            }
        }
        if (this.hasToDate) {
            if (this.toDate != null) {
                object.getObject().put("toDate", DateFormatUtils.format(this.toDate, this.dateFormat));
            } else {
                object.getObject().put("toDate", (String) null);
            }
        }
        if (this.hasRepaymentsRescheduledTo) {
            if (this.repaymentsRescheduledTo != null) {
                object.getObject().put("repaymentsRescheduledTo", DateFormatUtils.format(this.repaymentsRescheduledTo, this.dateFormat));
            } else {
                object.getObject().put("repaymentsRescheduledTo", (String) null);
            }
        }
        if (this.hasOffices) {
            List<JSONObject> offices = new ArrayList<>();
            for (String office : this.offices) {
                JSONObject o = new JSONObject();
                o.put("officeId", office);
                offices.add(o);
            }
            object.getObject().put("offices", new JSONArray(offices));
        }
        return object;
    }

    public HolidayBuilder withOffice(String officeId) {
        if (!this.offices.contains(officeId)) {
            this.offices.add(officeId);
        }
        this.hasOffices = true;
        return this;
    }

    public HolidayBuilder withRepaymentsRescheduledTo(Date repaymentsRescheduledTo) {
        this.repaymentsRescheduledTo = repaymentsRescheduledTo;
        this.hasRepaymentsRescheduledTo = true;
        return this;
    }

    public HolidayBuilder withToDate(Date toDate) {
        this.toDate = toDate;
        this.hasToDate = true;
        return this;
    }

    public HolidayBuilder withFromDate(Date fromDate) {
        this.fromDate = fromDate;
        this.hasFromDate = true;
        return this;
    }

    public HolidayBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public HolidayBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public HolidayBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public HolidayBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

}
