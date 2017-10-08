package com.angkorteam.fintech.dto.builder;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 6/22/17.
 */
public class OfficeBuilder implements Serializable {

    private String parentId = "1";
    private boolean hasParentId = true;

    private String id;
    private boolean hasId = false;

    private String name;
    private boolean hasName = false;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date openingDate;
    private boolean hasOpeningDate = false;

    private String locale = "en";
    private boolean hasLocale = true;

    private String externalId;
    private boolean hasExternalId;

    public OfficeBuilder() {
    }

    public OfficeBuilder withParentId(String parentId) {
        this.parentId = parentId;
        this.hasParentId = true;
        return this;
    }

    public OfficeBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public OfficeBuilder withOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
        this.hasOpeningDate = true;
        return this;
    }

    public OfficeBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public OfficeBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public OfficeBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasParentId) {
            object.getObject().put("parentId", this.parentId);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasOpeningDate) {
            if (this.openingDate != null) {
                object.getObject().put("openingDate", DateFormatUtils.format(this.openingDate, this.dateFormat));
            } else {
                object.getObject().put("openingDate", (String) null);
            }
        }
        return object;
    }

}
