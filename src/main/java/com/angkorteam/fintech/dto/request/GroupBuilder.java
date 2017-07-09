package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GroupBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String officeId;
    private boolean hasOfficeId;

    private String name;
    private boolean hasName;

    private String externalId;
    private boolean hasExternalId;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private String locale = "en";
    private boolean hasLocale = true;

    private boolean active;
    private boolean hasActive;

    private Date activationDate;

    private Date submittedOnDate;

    private String parentId;
    private boolean hasParentId;

    public GroupBuilder withParentId(String parentId) {
        this.parentId = parentId;
        this.hasParentId = true;
        return this;
    }

    public GroupBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public GroupBuilder withActivationDate(Date activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public GroupBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        return this;
    }

    public GroupBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public GroupBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public GroupBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public GroupBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasParentId) {
            object.getObject().put("parentId", this.parentId);
        }
        if (this.hasActive) {
            object.getObject().put("active", this.active);
            if (this.active) {
                if (this.activationDate == null) {
                    object.getObject().put("activationDate", DateFormatUtils.format(new Date(), this.dateFormat));
                } else {
                    object.getObject().put("activationDate", DateFormatUtils.format(this.activationDate, this.dateFormat));
                }
            } else {
                if (this.submittedOnDate == null) {
                    object.getObject().put("submittedOnDate", DateFormatUtils.format(new Date(), this.dateFormat));
                } else {
                    object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
                }
            }
        }
        return object;
    }
}
