package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccountClosureBuilder implements Serializable {

    private String officeId;
    private boolean hasOfficeId;

    private Date closingDate;
    private boolean hasClosingDate;

    private String comments;
    private boolean hasComments;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public JsonNode build() {
        List<String> errors = new ArrayList<>();
        if (this.officeId == null || "".equals(this.officeId)) {
            errors.add("officeId is required");
        }
        if (this.closingDate == null) {
            errors.add("closingDate is required");
        }
        if (!errors.isEmpty()) {
            // throw new IllegalArgumentException("invalid builder :: " +
            // StringUtils.join(errors, ","));
            System.out.println("invalid builder :: " + StringUtils.join(errors, ","));
        }
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasComments) {
            object.getObject().put("comments", this.comments);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasClosingDate) {
            if (this.closingDate != null) {
                object.getObject().put("closingDate", DateFormatUtils.format(this.closingDate, this.dateFormat));
            } else {
                object.getObject().put("closingDate", (String) null);
            }
        }
        return object;
    }

    public AccountClosureBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public AccountClosureBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public AccountClosureBuilder withComments(String comments) {
        this.comments = comments;
        this.hasComments = true;
        return this;
    }

    public AccountClosureBuilder withClosingDate(Date closingDate) {
        this.closingDate = closingDate;
        this.hasClosingDate = true;
        return this;
    }

    public AccountClosureBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

}
