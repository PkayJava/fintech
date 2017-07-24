package com.angkorteam.fintech.dto.request;

import com.angkorteam.fintech.dto.*;

import java.io.Serializable;
import java.util.Date;

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

    private String monthDayFormat = "dd MMMM";
    private boolean hasMonthDayFormat;

    private Date feeOnMonthDay;
    private boolean hasFeeOnMonthDay;

}
