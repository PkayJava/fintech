package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class SavingsAccountBuilder implements Serializable {

    private String clientId;
    private boolean hasClientId;

    private String productId;
    private boolean hasProductId;

    private String fieldOfficerId;
    private boolean hasFieldOfficerId;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    private String accountNo;
    private boolean hasAccountNo;

    private String externalId;
    private boolean hasExternalId;

    private double nominalAnnualInterestRate;
    private boolean hasNominalAnnualInterestRate;

    private String interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    private String interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    private String interestCalculationType;
    private boolean hasInterestCalculationType;

    private double interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    private double minRequiredOpeningBalance;
    private boolean hasMinRequiredOpeningBalance;

    private String lockinPeriodFrequency;
    private boolean hasLockinPeriodFrequency;

    private String lockinPeriodFrequencyType;
    private boolean hasLockinPeriodFrequencyType;

    private boolean allowOverdraft;
    private boolean hasAllowOverdraft;

    private double overdraftLimit;
    private boolean hasOverdraftLimit;

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    private List<Map<String, Object>> datatables = Lists.newArrayList();
    private boolean hasDatatables;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }
        if (this.hasProductId) {
            object.getObject().put("productId", this.productId);
        }
        if (this.hasFieldOfficerId) {
            object.getObject().put("fieldOfficerId", this.fieldOfficerId);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasSubmittedOnDate) {
            object.getObject().put("submittedOnDate", this.submittedOnDate);
        }
        if (this.hasAccountNo) {
            object.getObject().put("accountNo", this.accountNo);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        if (this.hasNominalAnnualInterestRate) {
            object.getObject().put("nominalAnnualInterestRate", this.nominalAnnualInterestRate);
        }
        if (this.hasInterestCompoundingPeriodType) {
            object.getObject().put("interestCompoundingPeriodType", this.interestCompoundingPeriodType);
        }
        if (this.hasInterestPostingPeriodType) {
            object.getObject().put("interestPostingPeriodType", this.interestPostingPeriodType);
        }
        if (this.hasInterestCalculationType) {
            object.getObject().put("interestCalculationType", this.interestCalculationType);
        }
        if (this.hasInterestCalculationDaysInYearType) {
            object.getObject().put("interestCalculationDaysInYearType", this.interestCalculationDaysInYearType);
        }
        if (this.hasMinRequiredOpeningBalance) {
            object.getObject().put("minRequiredOpeningBalance", this.minRequiredOpeningBalance);
        }
        if (this.hasLockinPeriodFrequency) {
            object.getObject().put("lockinPeriodFrequency", this.lockinPeriodFrequency);
        }
        if (this.hasLockinPeriodFrequencyType) {
            object.getObject().put("lockinPeriodFrequencyType", this.lockinPeriodFrequencyType);
        }
        if (this.hasAllowOverdraft) {
            object.getObject().put("allowOverdraft", this.allowOverdraft);
        }
        if (this.hasOverdraftLimit) {
            object.getObject().put("overdraftLimit", overdraftLimit);
        }
        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
        }
        if (this.hasDatatables) {
            object.getObject().put("datatables", this.datatables);
        }
        return object;
    }

    public SavingsAccountBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    public SavingsAccountBuilder withProductId(String productId) {
        this.productId = productId;
        this.hasProductId = true;
        return this;
    }

    public SavingsAccountBuilder withFieldOfficerId(String fieldOfficerId) {
        this.fieldOfficerId = fieldOfficerId;
        this.hasFieldOfficerId = true;
        return this;
    }

    public SavingsAccountBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public SavingsAccountBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public SavingsAccountBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    public SavingsAccountBuilder withAccountNo(String accountNo) {
        this.accountNo = accountNo;
        this.hasAccountNo = true;
        return this;
    }

    public SavingsAccountBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public SavingsAccountBuilder withNominalAnnualInterestRate(double nominalAnnualInterestRate) {
        this.nominalAnnualInterestRate = nominalAnnualInterestRate;
        this.hasNominalAnnualInterestRate = true;
        return this;
    }

    public SavingsAccountBuilder withInterestCompoundingPeriodType(String interestCompoundingPeriodType) {
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.hasInterestCompoundingPeriodType = true;
        return this;
    }

    public SavingsAccountBuilder withInterestPostingPeriodType(String interestPostingPeriodType) {
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.hasInterestPostingPeriodType = true;
        return this;
    }

    public SavingsAccountBuilder withInterestCalculationType(String interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
        this.hasInterestCalculationType = true;
        return this;
    }

    public SavingsAccountBuilder withInterestCalculationDaysInYearType(double interestCalculationDaysInYearType) {
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.hasInterestCalculationDaysInYearType = true;
        return this;
    }

    public SavingsAccountBuilder withMinRequiredOpeningBalance(double minRequiredOpeningBalance) {
        this.minRequiredOpeningBalance = minRequiredOpeningBalance;
        this.hasMinRequiredOpeningBalance = true;
        return this;
    }

    public SavingsAccountBuilder withLockinPeriodFrequency(String lockinPeriodFrequency) {
        this.lockinPeriodFrequency = lockinPeriodFrequency;
        this.hasLockinPeriodFrequency = true;
        return this;
    }

    public SavingsAccountBuilder withLockinPeriodFrequencyType(String lockinPeriodFrequencyType) {
        this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
        this.hasLockinPeriodFrequencyType = true;
        return this;
    }

    public SavingsAccountBuilder withAllowOverdraft(boolean allowOverdraft) {
        this.allowOverdraft = allowOverdraft;
        this.hasAllowOverdraft = true;
        return this;
    }

    public SavingsAccountBuilder withOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
        this.hasOverdraftLimit = true;
        return this;
    }

    public SavingsAccountBuilder withCharge(String chargeId) {
        Map<String, Object> charge = Maps.newHashMap();
        charge.put("id", chargeId);
        this.charges.add(charge);
        return this;
    }

    public SavingsAccountBuilder withDatatable(String registeredTableName, Map<String, Object> data) {
        Map<String, Object> datatable = Maps.newHashMap();
        datatable.put("registeredTableName", registeredTableName);
        datatable.put("data", datatable);
        this.datatables.add(datatable);
        this.hasDatatables = true;
        return this;
    }

}
