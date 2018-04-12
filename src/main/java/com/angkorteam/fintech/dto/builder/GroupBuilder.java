package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GroupBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public GroupBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String officeId;
    private boolean hasOfficeId;

    public GroupBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    private String externalId;
    private boolean hasExternalId;

    public GroupBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    private String name;
    private boolean hasName;

    public GroupBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private boolean active;
    private boolean hasActive;

    public GroupBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    private Date activationDate;
    private boolean hasActivationDate;

    public GroupBuilder withActivationDate(Date activationDate) {
        this.activationDate = activationDate;
        this.hasActivationDate = true;
        return this;
    }

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    public GroupBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    private String parentId;
    private boolean hasParentId;

    public GroupBuilder withParentId(String parentId) {
        this.parentId = parentId;
        this.hasParentId = true;
        return this;
    }

    private List<String> clientMembers = Lists.newArrayList();
    private boolean hasClientMembers;

    public GroupBuilder withClientMember(String clientMember) {
        this.clientMembers.add(clientMember);
        this.hasClientMembers = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public GroupBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public GroupBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String staffId;
    private boolean hasStaffId;

    public GroupBuilder withStaffId(String staffId) {
        this.staffId = staffId;
        this.hasStaffId = true;
        return this;
    }

    private String centerId;
    private boolean hasCenterId;

    public GroupBuilder withCenterId(String centerId) {
        this.centerId = centerId;
        this.hasCenterId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasCenterId) {
            object.getObject().put("centerId", this.centerId);
        }

        if (this.hasStaffId) {
            object.getObject().put("staffId", this.staffId);
        }

        if (this.hasClientMembers) {
            object.getObject().put("clientMembers", this.clientMembers);
        }

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
        }
        if (this.hasActivationDate) {
            if (this.activationDate == null) {
                object.getObject().put("activationDate", (String) null);
            } else {
                object.getObject().put("activationDate", DateFormatUtils.format(this.activationDate, this.dateFormat));
            }
        }
        if (this.hasSubmittedOnDate) {
            if (this.submittedOnDate == null) {
                object.getObject().put("submittedOnDate", (String) null);
            } else {
                object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
            }
        }
        return object;
    }
}
