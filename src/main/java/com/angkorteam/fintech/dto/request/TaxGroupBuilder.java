package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/20/17.
 */
public class TaxGroupBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String locale = "en";
    private boolean hasLocale = true;

    private List<Map<String, Object>> taxComponents = Lists.newArrayList();
    private boolean hasTaxComponents;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasTaxComponents) {
            for (Map<String, Object> taxComponent : this.taxComponents) {
                Date startDate = (Date) taxComponent.remove("startDate");
                Date endDate = (Date) taxComponent.remove("endDate");
                if (taxComponent.get("id") != null) {
                    if (endDate != null) {
                        taxComponent.put("endDate", DateFormatUtils.format(endDate, this.dateFormat));
                    }
                } else {
                    if (startDate != null) {
                        taxComponent.put("startDate", DateFormatUtils.format(startDate, this.dateFormat));
                    }
                }
            }
            object.getObject().put("taxComponents", this.taxComponents);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        return object;
    }

    public TaxGroupBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public TaxGroupBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public TaxGroupBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public TaxGroupBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public TaxGroupBuilder withTaxComponents(String id, String taxComponentId, Date startDate, Date endDate) {
        Map<String, Object> taxComponent = Maps.newHashMap();
        taxComponent.put("id", id);
        taxComponent.put("taxComponentId", taxComponentId);
        taxComponent.put("startDate", startDate);
        taxComponent.put("endDate", endDate);
        this.hasTaxComponents = true;
        return this;
    }

}
