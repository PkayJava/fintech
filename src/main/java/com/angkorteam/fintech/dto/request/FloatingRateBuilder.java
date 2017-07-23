package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FloatingRateBuilder implements Serializable {

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

    public FloatingRateBuilder withRatePeriod(Date fromDate, Integer interestRate, boolean differentialToBaseLendingRate) {
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

    public FloatingRateBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

}
