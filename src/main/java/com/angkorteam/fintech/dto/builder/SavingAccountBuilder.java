package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.openunirest.http.JsonNode;

public class SavingAccountBuilder implements Serializable {

    private String clientId;
    private boolean hasClientId;

    public SavingAccountBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String productId;
    private boolean hasProductId;

    public SavingAccountBuilder withProductId(String productId) {
        this.productId = productId;
        this.hasProductId = true;
        return this;
    }

    private Double nominalAnnualInterestRate;
    private boolean hasNominalAnnualInterestRate;

    public SavingAccountBuilder withNominalAnnualInterestRate(Double nominalAnnualInterestRate) {
        this.nominalAnnualInterestRate = nominalAnnualInterestRate;
        this.hasNominalAnnualInterestRate = true;
        return this;
    }

    private Double minRequiredOpeningBalance;
    private boolean hasMinRequiredOpeningBalance;

    public SavingAccountBuilder withMinRequiredOpeningBalance(Double minRequiredOpeningBalance) {
        this.minRequiredOpeningBalance = minRequiredOpeningBalance;
        this.hasMinRequiredOpeningBalance = true;
        return this;
    }

    private Long lockinPeriodFrequency;
    private boolean hasLockinPeriodFrequency;

    public SavingAccountBuilder withLockinPeriodFrequency(Long lockinPeriodFrequency) {
        this.lockinPeriodFrequency = lockinPeriodFrequency;
        this.hasLockinPeriodFrequency = true;
        return this;
    }

    private boolean withdrawalFeeForTransfers;
    private boolean hasWithdrawalFeeForTransfers;

    public SavingAccountBuilder withWithdrawalFeeForTransfers(boolean withdrawalFeeForTransfers) {
        this.withdrawalFeeForTransfers = withdrawalFeeForTransfers;
        this.hasWithdrawalFeeForTransfers = true;
        return this;
    }

    private boolean allowOverdraft;
    private boolean hasAllowOverdraft;

    public SavingAccountBuilder withAllowOverdraft(boolean allowOverdraft) {
        this.allowOverdraft = allowOverdraft;
        this.hasAllowOverdraft = true;
        return this;
    }

    private Double overdraftLimit;
    private boolean hasOverdraftLimit;

    public SavingAccountBuilder withOverdraftLimit(Double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
        this.hasOverdraftLimit = true;
        return this;
    }

    private Double nominalAnnualInterestRateOverdraft;
    private boolean hasNominalAnnualInterestRateOverdraft;

    public SavingAccountBuilder withNominalAnnualInterestRateOverdraft(Double nominalAnnualInterestRateOverdraft) {
        this.nominalAnnualInterestRateOverdraft = nominalAnnualInterestRateOverdraft;
        this.hasNominalAnnualInterestRateOverdraft = true;
        return this;
    }

    private Double minOverdraftForInterestCalculation;
    private boolean hasMinOverdraftForInterestCalculation;

    public SavingAccountBuilder withMinOverdraftForInterestCalculation(Double minOverdraftForInterestCalculation) {
        this.minOverdraftForInterestCalculation = minOverdraftForInterestCalculation;
        this.hasMinOverdraftForInterestCalculation = true;
        return this;
    }

    private boolean enforceMinRequiredBalance;
    private boolean hasEnforceMinRequiredBalance;

    public SavingAccountBuilder withEnforceMinRequiredBalance(boolean enforceMinRequiredBalance) {
        this.enforceMinRequiredBalance = enforceMinRequiredBalance;
        this.hasEnforceMinRequiredBalance = true;
        return this;
    }

    private Double minRequiredBalance;
    private boolean hasMinRequiredBalance;

    public SavingAccountBuilder withMinRequiredBalance(Double minRequiredBalance) {
        this.minRequiredBalance = minRequiredBalance;
        this.hasMinRequiredBalance = true;
        return this;
    }

    private boolean holdTax;
    private boolean hasHoldTax;

    public SavingAccountBuilder withHoldTax(boolean holdTax) {
        this.holdTax = holdTax;
        this.hasHoldTax = true;
        return this;
    }

    private InterestCompoundingPeriod interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    public SavingAccountBuilder withInterestCompoundingPeriodType(InterestCompoundingPeriod interestCompoundingPeriodType) {
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.hasInterestCompoundingPeriodType = true;
        return this;
    }

    private InterestPostingPeriod interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    public SavingAccountBuilder withInterestPostingPeriodType(InterestPostingPeriod interestPostingPeriodType) {
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.hasInterestPostingPeriodType = true;
        return this;
    }

    private InterestCalculatedUsing interestCalculationType;
    private boolean hasInterestCalculationType;

    public SavingAccountBuilder withInterestCalculationType(InterestCalculatedUsing interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
        this.hasInterestCalculationType = true;
        return this;
    }

    private DayInYear interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    public SavingAccountBuilder withInterestCalculationDaysInYearType(DayInYear interestCalculationDaysInYearType) {
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.hasInterestCalculationDaysInYearType = true;
        return this;
    }

    private LockInType lockinPeriodFrequencyType;
    private boolean hasLockinPeriodFrequencyType;

    public SavingAccountBuilder withLockinPeriodFrequencyType(LockInType lockinPeriodFrequencyType) {
        this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
        this.hasLockinPeriodFrequencyType = true;
        return this;
    }

    private String fieldOfficerId;
    private boolean hasFieldOfficerId;

    public SavingAccountBuilder withFieldOfficerId(String fieldOfficerId) {
        this.fieldOfficerId = fieldOfficerId;
        this.hasFieldOfficerId = true;
        return this;
    }

    private String externalId;
    private boolean hasExternalId;

    public SavingAccountBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    public SavingAccountBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public SavingAccountBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public SavingAccountBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String monthDayFormat = "dd MMMM";
    private boolean hasMonthDayFormat = true;

    public SavingAccountBuilder withMonthDayFormat(String monthDayFormat) {
        this.monthDayFormat = monthDayFormat;
        this.hasMonthDayFormat = true;
        return this;
    }

    private String groupId;
    private boolean hasGroupId;

    public SavingAccountBuilder withGroupId(String groupId) {
        this.groupId = groupId;
        this.hasGroupId = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public SavingAccountBuilder withCharge(String chargeId, Double amount, Date feeOnMonthDay, Date dueDate, Long feeInterval) {
        Map<String, Object> charge = Maps.newHashMap();
        charge.put("chargeId", chargeId);
        charge.put("amount", amount);
        if (feeOnMonthDay != null) {
            charge.put("feeOnMonthDay", DateFormatUtils.format(feeOnMonthDay, this.monthDayFormat));
        }
        if (dueDate != null) {
            charge.put("dueDate", DateFormatUtils.format(dueDate, this.dateFormat));
        }
        if (feeInterval != null) {
            charge.put("feeInterval", feeInterval);
        }
        this.charges.add(charge);
        this.hasCharges = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
        }

        if (this.hasSubmittedOnDate) {
            if (this.submittedOnDate != null) {
                object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
            } else {
                object.getObject().put("submittedOnDate", (String) null);
            }
        }

        if (this.hasGroupId) {
            object.getObject().put("groupId", this.groupId);
        }

        if (this.hasMonthDayFormat) {
            object.getObject().put("monthDayFormat", this.monthDayFormat);
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

        if (this.hasFieldOfficerId) {
            object.getObject().put("fieldOfficerId", this.fieldOfficerId);
        }

        if (this.hasLockinPeriodFrequencyType) {
            if (this.lockinPeriodFrequencyType != null) {
                object.getObject().put("lockinPeriodFrequencyType", this.lockinPeriodFrequencyType.getLiteral());
            } else {
                object.getObject().put("lockinPeriodFrequencyType", (String) null);
            }
        }

        if (this.hasInterestCalculationDaysInYearType) {
            if (this.interestCalculationDaysInYearType != null) {
                object.getObject().put("interestCalculationDaysInYearType", this.interestCalculationDaysInYearType.getLiteral());
            } else {
                object.getObject().put("interestCalculationDaysInYearType", (String) null);
            }
        }

        if (this.hasInterestCalculationType) {
            if (this.interestCalculationType != null) {
                object.getObject().put("interestCalculationType", this.interestCalculationType.getLiteral());
            } else {
                object.getObject().put("interestCalculationType", (String) null);
            }
        }

        if (this.hasInterestPostingPeriodType) {
            if (this.interestPostingPeriodType != null) {
                object.getObject().put("interestPostingPeriodType", this.interestPostingPeriodType.getLiteral());
            } else {
                object.getObject().put("interestPostingPeriodType", (String) null);
            }
        }

        if (this.hasInterestCompoundingPeriodType) {
            if (this.interestCompoundingPeriodType != null) {
                object.getObject().put("interestCompoundingPeriodType", this.interestCompoundingPeriodType.getLiteral());
            } else {
                object.getObject().put("interestCompoundingPeriodType", (String) null);
            }
        }

        if (this.hasHoldTax) {
            object.getObject().put("withHoldTax", this.holdTax);
        }

        if (this.hasMinRequiredBalance) {
            object.getObject().put("minRequiredBalance", this.minRequiredBalance);
        }

        if (this.hasEnforceMinRequiredBalance) {
            object.getObject().put("enforceMinRequiredBalance", this.enforceMinRequiredBalance);
        }

        if (this.hasMinOverdraftForInterestCalculation) {
            object.getObject().put("minOverdraftForInterestCalculation", this.minOverdraftForInterestCalculation);
        }

        if (this.hasNominalAnnualInterestRateOverdraft) {
            object.getObject().put("nominalAnnualInterestRateOverdraft", this.nominalAnnualInterestRateOverdraft);
        }

        if (this.hasOverdraftLimit) {
            object.getObject().put("overdraftLimit", this.overdraftLimit);
        }

        if (this.hasAllowOverdraft) {
            object.getObject().put("allowOverdraft", this.allowOverdraft);
        }

        if (this.hasWithdrawalFeeForTransfers) {
            object.getObject().put("withdrawalFeeForTransfers", this.withdrawalFeeForTransfers);
        }

        if (this.hasLockinPeriodFrequency) {
            object.getObject().put("lockinPeriodFrequency", this.lockinPeriodFrequency);
        }

        if (this.hasMinRequiredOpeningBalance) {
            object.getObject().put("minRequiredOpeningBalance", this.minRequiredOpeningBalance);
        }

        if (this.hasProductId) {
            object.getObject().put("productId", this.productId);
        }

        if (this.hasNominalAnnualInterestRate) {
            object.getObject().put("nominalAnnualInterestRate", this.nominalAnnualInterestRate);
        }

        return object;

    }

}
