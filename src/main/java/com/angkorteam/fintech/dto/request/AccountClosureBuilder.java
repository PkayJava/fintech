package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

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
