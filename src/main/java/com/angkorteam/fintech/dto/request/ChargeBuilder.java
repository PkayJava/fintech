package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeFrequency;
import com.angkorteam.fintech.dto.ChargePayment;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
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

    private double amount;
    private boolean hasAmount;

    private int feeInterval;
    private boolean hasFeeInterval;

    private ChargeFrequency feeFrequency;
    private boolean hasFeeFrequency;

    private String locale = "en";
    private boolean hasLocale = true;
    
    private String incomeAccountId;
    private boolean hasIncomeAccountId;

    private String monthDayFormat = "dd MMMM";
    private boolean hasMonthDayFormat;

    private Date feeOnMonthDay;
    private boolean hasFeeOnMonthDay;

    public JsonNode build() {
	JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasIncomeAccountId) {
            object.getObject().put("incomeAccountId", this.incomeAccountId);
        }
	if (this.hasChargeAppliesTo) {
	    object.getObject().put("chargeAppliesTo", this.chargeAppliesTo.getLiteral());
	}

	if (this.hasChargeTimeType) {
	    object.getObject().put("chargeTimeType", this.chargeTimeType.getLiteral());
	}

	if (this.hasName) {
	    object.getObject().put("name", this.name);
	}

	if (this.hasCurrencyCode) {
	    object.getObject().put("currencyCode", this.currencyCode);
	}

	if (this.hasChargeCalculationType) {
	    object.getObject().put("chargeCalculationType", this.chargeCalculationType.getLiteral());
	}

	if (this.hasChargePaymentMode) {
	    object.getObject().put("chargePaymentMode", this.chargePaymentMode.getLiteral());
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
	    object.getObject().put("feeFrequency", this.feeFrequency.getLiteral());
	}

	if (this.hasLocale) {
	    object.getObject().put("locale", this.locale);
	}

	if (this.hasMonthDayFormat) {
	    object.getObject().put("monthDayFormat", this.monthDayFormat);
	}

	if (this.hasFeeOnMonthDay) {
	    if (this.feeOnMonthDay == null) {
		object.getObject().put("feeOnMonthDay", (String) null);
	    } else {
		object.getObject().put("feeOnMonthDay",
			DateFormatUtils.format(this.feeOnMonthDay, this.monthDayFormat));
	    }
	}
	return object;
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

    public ChargeBuilder withAmount(double amount) {
	this.amount = amount;
	this.hasAmount = true;
	return this;
    }

    public ChargeBuilder withFeeInterval(int feeInterval) {
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
