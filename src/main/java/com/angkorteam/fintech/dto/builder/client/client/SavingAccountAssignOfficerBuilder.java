package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import io.github.openunirest.http.JsonNode;

public class SavingAccountAssignOfficerBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public SavingAccountAssignOfficerBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String toSavingsOfficerId;
    private boolean hasToSavingsOfficerId;

    public SavingAccountAssignOfficerBuilder withToSavingsOfficerId(String toSavingsOfficerId) {
        this.toSavingsOfficerId = toSavingsOfficerId;
        this.hasToSavingsOfficerId = true;
        return this;
    }

    private Date assignmentDate;
    private boolean hasAssignmentDate;

    public SavingAccountAssignOfficerBuilder withAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
        this.hasAssignmentDate = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public SavingAccountAssignOfficerBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public SavingAccountAssignOfficerBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String fromSavingsOfficerId;
    private boolean hasFromSavingsOfficerId;

    public SavingAccountAssignOfficerBuilder withFromSavingsOfficerId(String fromSavingsOfficerId) {
        this.fromSavingsOfficerId = fromSavingsOfficerId;
        this.hasFromSavingsOfficerId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasFromSavingsOfficerId) {
            object.getObject().put("fromSavingsOfficerId", this.fromSavingsOfficerId);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasToSavingsOfficerId) {
            object.getObject().put("toSavingsOfficerId", this.toSavingsOfficerId);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasAssignmentDate) {
            if (this.assignmentDate != null) {
                object.getObject().put("assignmentDate", DateFormatUtils.format(this.assignmentDate, this.dateFormat));
            } else {
                object.getObject().put("assignmentDate", (String) null);
            }
        }

        return object;
    }

}
