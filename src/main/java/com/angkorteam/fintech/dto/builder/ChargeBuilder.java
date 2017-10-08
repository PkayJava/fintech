package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.ChargePayment;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.mashape.unirest.http.JsonNode;

public class ChargeBuilder implements Serializable {

    private ChargeType chargeAppliesTo;
    private boolean hasChargeAppliesTo;

    private ChargeTime chargeTimeType;
    private boolean hasChargeTimeType;

    private String name;
    private boolean hasName;

    private String currencyCode;
    private boolean hasCurrencyCode;

    private ChargeCalculation chargeCalculationType;
    private boolean hasChargeCalculationType;

    private ChargePayment chargePaymentMode;
    private boolean hasChargePaymentMode;

    private String taxGroupId;
    private boolean hasTaxGroupId;

    private boolean penalty;
    private boolean hasPenalty;

    private boolean active;
    private boolean hasActive;

    private Double amount;
    private boolean hasAmount;

    private Integer feeInterval;
    private boolean hasFeeInterval;

    private ChargeFrequency feeFrequency;
    private boolean hasFeeFrequency;

    private String locale = "en";
    private boolean hasLocale = true;

    private String incomeAccountId;
    private boolean hasIncomeAccountId;

    private String monthDayFormat = "dd MMMM";
    private boolean hasMonthDayFormat = true;

    private Date feeOnMonthDay;
    private boolean hasFeeOnMonthDay;

    private String id;
    private boolean hasId;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasIncomeAccountId) {
            object.getObject().put("incomeAccountId", this.incomeAccountId);
        }
        if (this.hasChargeAppliesTo) {
            if (this.chargeAppliesTo != null) {
                object.getObject().put("chargeAppliesTo", this.chargeAppliesTo.getLiteral());
            } else {
                object.getObject().put("chargeAppliesTo", (String) null);
            }
        }

        if (this.hasChargeTimeType) {
            if (this.chargeTimeType != null) {
                object.getObject().put("chargeTimeType", this.chargeTimeType.getLiteral());
            } else {
                object.getObject().put("chargeTimeType", (String) null);
            }
        }

        if (this.hasName) {
            object.getObject().put("name", this.name);
        }

        if (this.hasCurrencyCode) {
            object.getObject().put("currencyCode", this.currencyCode);
        }

        if (this.hasChargeCalculationType) {
            if (this.chargeCalculationType != null) {
                object.getObject().put("chargeCalculationType", this.chargeCalculationType.getLiteral());
            } else {
                object.getObject().put("chargeCalculationType", (String) null);
            }
        }

        if (this.hasChargePaymentMode) {
            if (this.chargePaymentMode != null) {
                object.getObject().put("chargePaymentMode", this.chargePaymentMode.getLiteral());
            } else {
                object.getObject().put("chargePaymentMode", (String) null);
            }
        }

        if (this.hasTaxGroupId) {
            object.getObject().put("taxGroupId", this.taxGroupId);
        }

        if (this.hasPenalty) {
            object.getObject().put("penalty", this.penalty);
        }

        if (this.hasActive) {
            object.getObject().put("active", this.active);
        }

        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }

        if (this.hasFeeInterval) {
            object.getObject().put("feeInterval", this.feeInterval);
        }

        if (this.hasFeeFrequency) {
            if (this.feeFrequency != null) {
                object.getObject().put("feeFrequency", this.feeFrequency.getLiteral());
            } else {
                object.getObject().put("feeFrequency", (String) null);
            }
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasMonthDayFormat) {
            object.getObject().put("monthDayFormat", this.monthDayFormat);
        }

        if (this.hasFeeOnMonthDay) {
            if (this.feeOnMonthDay != null) {
                object.getObject().put("feeOnMonthDay", DateFormatUtils.format(this.feeOnMonthDay, this.monthDayFormat));
            } else {
                object.getObject().put("feeOnMonthDay", (String) null);
            }
        }
        return object;
    }

    public ChargeBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public ChargeBuilder withIncomeAccountId(String incomeAccountId) {
        this.incomeAccountId = incomeAccountId;
        this.hasIncomeAccountId = true;
        return this;
    }

    public ChargeBuilder withChargeAppliesTo(ChargeType chargeAppliesTo) {
        this.chargeAppliesTo = chargeAppliesTo;
        this.hasChargeAppliesTo = true;
        return this;
    }

    public ChargeBuilder withChargeTimeType(ChargeTime chargeTimeType) {
        this.chargeTimeType = chargeTimeType;
        this.hasChargeTimeType = true;
        return this;
    }

    public ChargeBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public ChargeBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    public ChargeBuilder withChargeCalculationType(ChargeCalculation chargeCalculationType) {
        this.chargeCalculationType = chargeCalculationType;
        this.hasChargeCalculationType = true;
        return this;
    }

    public ChargeBuilder withChargePaymentMode(ChargePayment chargePaymentMode) {
        this.chargePaymentMode = chargePaymentMode;
        this.hasChargePaymentMode = true;
        return this;
    }

    public ChargeBuilder withTaxGroupId(String taxGroupId) {
        this.taxGroupId = taxGroupId;
        this.hasTaxGroupId = true;
        return this;
    }

    public ChargeBuilder withPenalty(boolean penalty) {
        this.penalty = penalty;
        this.hasPenalty = true;
        return this;
    }

    public ChargeBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public ChargeBuilder withAmount(Double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    public ChargeBuilder withFeeInterval(Integer feeInterval) {
        this.feeInterval = feeInterval;
        this.hasFeeInterval = true;
        return this;
    }

    public ChargeBuilder withFeeFrequency(ChargeFrequency feeFrequency) {
        this.feeFrequency = feeFrequency;
        this.hasFeeFrequency = true;
        return this;
    }

    public ChargeBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public ChargeBuilder withMonthDayFormat(String monthDayFormat) {
        this.monthDayFormat = monthDayFormat;
        this.hasMonthDayFormat = true;
        return this;
    }

    public ChargeBuilder withFeeOnMonthDay(Date feeOnMonthDay) {
        this.feeOnMonthDay = feeOnMonthDay;
        this.hasFeeOnMonthDay = true;
        return this;
    }

}
