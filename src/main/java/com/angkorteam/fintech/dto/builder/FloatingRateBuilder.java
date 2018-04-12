package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.openunirest.http.JsonNode;

public class FloatingRateBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private boolean active;
    private boolean hasActive;

    private boolean baseLendingRate;
    private boolean hasBaseLendingRate;

    private List<Map<String, Object>> ratePeriods = Lists.newArrayList();
    private boolean hasRatePeriods;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasActive) {
            object.getObject().put("isActive", this.active);
        }
        if (this.hasBaseLendingRate) {
            object.getObject().put("isBaseLendingRate", this.baseLendingRate);
        }
        if (this.hasRatePeriods) {
            for (Map<String, Object> rate : this.ratePeriods) {
                Date fromDate = (Date) rate.remove("fromDate");
                String dateFormat = (String) rate.get("dateFormat");
                rate.put("fromDate", DateFormatUtils.format(fromDate, dateFormat));
            }
            object.getObject().put("ratePeriods", this.ratePeriods);
        }
        return object;
    }

    public FloatingRateBuilder withRatePeriod(Date fromDate, Double interestRate, boolean differentialToBaseLendingRate) {
        Map<String, Object> rate = Maps.newHashMap();
        rate.put("fromDate", fromDate);
        rate.put("interestRate", interestRate);
        rate.put("isDifferentialToBaseLendingRate", differentialToBaseLendingRate);
        rate.put("locale", "en");
        rate.put("dateFormat", "yyyy-MM-dd");
        this.ratePeriods.add(rate);
        this.hasRatePeriods = true;
        return this;
    }

    public FloatingRateBuilder withBaseLendingRate(boolean baseLendingRate) {
        this.baseLendingRate = baseLendingRate;
        this.hasBaseLendingRate = true;
        return this;
    }

    public FloatingRateBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public FloatingRateBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public FloatingRateBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

}
