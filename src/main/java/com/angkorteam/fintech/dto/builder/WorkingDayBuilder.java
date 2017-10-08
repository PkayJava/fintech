package com.angkorteam.fintech.dto.builder;

import com.angkorteam.fintech.dto.enums.RepaymentOption;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayBuilder implements Serializable {

    private String locale = "en";
    private boolean hasLocale = true;

    private RepaymentOption repaymentRescheduleType;
    private boolean hasRepaymentRescheduleType;

    private boolean monday;
    private boolean hasMonday;

    private boolean tuesday;
    private boolean hasTuesday;

    private boolean wednesday;
    private boolean hasWednesday;

    private boolean thursday;
    private boolean hasThursday;

    private boolean friday;
    private boolean hasFriday;

    private boolean saturday;
    private boolean hasSaturday;

    private boolean sunday;
    private boolean hasSunday;

    private boolean extendTermForDailyRepayments;
    private boolean hasExtendTermForDailyRepayments;

    public WorkingDayBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public WorkingDayBuilder withRepaymentRescheduleType(RepaymentOption repaymentRescheduleType) {
        this.repaymentRescheduleType = repaymentRescheduleType;
        this.hasRepaymentRescheduleType = true;
        return this;
    }

    public WorkingDayBuilder withMonday(boolean monday) {
        this.monday = monday;
        this.hasMonday = true;
        return this;
    }

    public WorkingDayBuilder withTuesday(boolean tuesday) {
        this.tuesday = tuesday;
        this.hasTuesday = true;
        return this;
    }

    public WorkingDayBuilder withWednesday(boolean wednesday) {
        this.wednesday = wednesday;
        this.hasWednesday = true;
        return this;
    }

    public WorkingDayBuilder withThursday(boolean thursday) {
        this.thursday = thursday;
        this.hasThursday = true;
        return this;
    }

    public WorkingDayBuilder withFriday(boolean friday) {
        this.friday = friday;
        this.hasFriday = true;
        return this;
    }

    public WorkingDayBuilder withSaturday(boolean saturday) {
        this.saturday = saturday;
        this.hasSaturday = true;
        return this;
    }

    public WorkingDayBuilder withSunday(boolean sunday) {
        this.sunday = sunday;
        this.hasSunday = true;
        return this;
    }

    public WorkingDayBuilder withExtendTermForDailyRepayments(boolean extendTermForDailyRepayments) {
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
        this.hasExtendTermForDailyRepayments = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasMonday || this.hasThursday || this.hasWednesday || this.hasThursday || this.hasFriday || this.hasSaturday || this.hasSunday) {
            List<String> days = Lists.newArrayList();
            if (this.hasMonday && this.monday) {
                days.add("MO");
            }
            if (this.hasTuesday && this.tuesday) {
                days.add("TU");
            }
            if (this.hasWednesday && this.wednesday) {
                days.add("WE");
            }
            if (this.hasThursday && this.thursday) {
                days.add("TH");
            }
            if (this.hasFriday && this.friday) {
                days.add("FR");
            }
            if (this.hasSaturday && this.saturday) {
                days.add("SA");
            }
            if (this.hasSunday && this.sunday) {
                days.add("SU");
            }
            StringBuffer recurrence = new StringBuffer();
            recurrence.append("FREQ=WEEKLY;INTERVAL=1;BYDAY=").append(StringUtils.join(days, ","));
            object.getObject().put("recurrence", recurrence.toString());
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasRepaymentRescheduleType) {
            if (this.repaymentRescheduleType != null) {
                object.getObject().put("repaymentRescheduleType", this.repaymentRescheduleType.getLiteral());
            } else {
                object.getObject().put("repaymentRescheduleType", (String) null);
            }
        }
        if (this.hasExtendTermForDailyRepayments) {
            object.getObject().put("extendTermForDailyRepayments", this.extendTermForDailyRepayments);
        }
        return object;
    }

}
