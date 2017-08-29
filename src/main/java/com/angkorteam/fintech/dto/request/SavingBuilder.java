package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class SavingBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public SavingBuilder withName(String name) {
	this.name = name;
	this.hasName = true;
	return this;
    }

    private String shortName;
    private boolean hasShortName;

    public SavingBuilder withShortName(String shortName) {
	this.shortName = shortName;
	this.hasShortName = true;
	return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public SavingBuilder withCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
	this.hasCurrencyCode = true;
	return this;
    }

    private Integer digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public SavingBuilder withDigitsAfterDecimal(Integer digitsAfterDecimal) {
	this.digitsAfterDecimal = digitsAfterDecimal;
	this.hasDigitsAfterDecimal = true;
	return this;
    }

    private Integer accountingRule;
    private boolean hasAccountingRule;

    public SavingBuilder withAccountingRule(Integer accountingRule) {
	this.accountingRule = accountingRule;
	this.hasAccountingRule = true;
	return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public SavingBuilder withLocale(String locale) {
	this.locale = locale;
	this.hasLocale = true;
	return this;
    }

    private String description;
    private boolean hasDescription;

    public SavingBuilder withDescription(String description) {
	this.description = description;
	this.hasDescription = true;
	return this;
    }

    private Integer inMultiplesOf;
    private boolean hasInMultiplesOf;

    public SavingBuilder withInMultiplesOf(Integer inMultiplesOf) {
	this.inMultiplesOf = inMultiplesOf;
	this.hasInMultiplesOf = true;
	return this;
    }

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public SavingBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("paymentTypeId", paymentTypeId);
	item.put("fundSourceAccountId", fundSourceAccountId);
	this.paymentChannelToFundSourceMappings.add(item);
	this.hasPaymentChannelToFundSourceMappings = true;
	return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public SavingBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("chargeId", chargeId);
	item.put("incomeAccountId", incomeAccountId);
	this.feeToIncomeAccountMappings.add(item);
	this.hasFeeToIncomeAccountMappings = true;
	return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public SavingBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("chargeId", chargeId);
	item.put("incomeAccountId", incomeAccountId);
	this.penaltyToIncomeAccountMappings.add(item);
	this.hasPenaltyToIncomeAccountMappings = true;
	return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public SavingBuilder withCharges(String chargeId) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("id", chargeId);
	this.charges.add(item);
	this.hasCharges = true;
	return this;
    }

    private String interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    public SavingBuilder withInterestCompoundingPeriodType(String interestCompoundingPeriodType) {
	this.interestCompoundingPeriodType = interestCompoundingPeriodType;
	this.hasInterestCompoundingPeriodType = true;
	return this;
    }

    private String interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    public SavingBuilder withInterestPostingPeriodType(String interestPostingPeriodType) {
	this.interestPostingPeriodType = interestPostingPeriodType;
	this.hasInterestPostingPeriodType = true;
	return this;
    }

    private String interestCalculationType;
    private boolean hasInterestCalculationType;

    public SavingBuilder withInterestCalculationType(String interestCalculationType) {
	this.interestCalculationType = interestCalculationType;
	this.hasInterestCalculationType = true;
	return this;
    }

    private String interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    public SavingBuilder withInterestCalculationDaysInYearType(String interestCalculationDaysInYearType) {
	this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
	this.hasInterestCalculationDaysInYearType = true;
	return this;
    }

    private String nominalAnnualInterestRate;
    private boolean hasNominalAnnualInterestRate;

    public SavingBuilder withNominalAnnualInterestRate(String nominalAnnualInterestRate) {
	this.nominalAnnualInterestRate = nominalAnnualInterestRate;
	this.hasNominalAnnualInterestRate = true;
	return this;
    }

    private String minRequiredOpeningBalance;
    private boolean hasMinRequiredOpeningBalance;

    public SavingBuilder withMinRequiredOpeningBalance(String minRequiredOpeningBalance) {
	this.minRequiredOpeningBalance = minRequiredOpeningBalance;
	this.hasMinRequiredOpeningBalance = true;
	return this;
    }

    private String lockinPeriodFrequency;
    private boolean hasLockinPeriodFrequency;

    public SavingBuilder withLockinPeriodFrequency(String lockinPeriodFrequency) {
	this.lockinPeriodFrequency = lockinPeriodFrequency;
	this.hasLockinPeriodFrequency = true;
	return this;
    }

    private String lockinPeriodFrequencyType;
    private boolean hasLockinPeriodFrequencyType;

    public SavingBuilder withLockinPeriodFrequencyType(String lockinPeriodFrequencyType) {
	this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
	this.hasLockinPeriodFrequencyType = true;
	return this;
    }

    private boolean withdrawalFeeForTransfers;
    private boolean hasWithdrawalFeeForTransfers;

    public SavingBuilder withWithdrawalFeeForTransfers(boolean withdrawalFeeForTransfers) {
	this.withdrawalFeeForTransfers = withdrawalFeeForTransfers;
	this.hasWithdrawalFeeForTransfers = true;
	return this;
    }

    private boolean enforceMinRequiredBalance;
    private boolean hasEnforceMinRequiredBalance;

    public SavingBuilder withEnforceMinRequiredBalance(boolean enforceMinRequiredBalance) {
	this.enforceMinRequiredBalance = enforceMinRequiredBalance;
	this.hasEnforceMinRequiredBalance = true;
	return this;
    }

    private boolean allowOverdraft;
    private boolean hasAllowOverdraft;

    public SavingBuilder withAllowOverdraft(boolean allowOverdraft) {
	this.allowOverdraft = allowOverdraft;
	this.hasAllowOverdraft = true;
	return this;
    }

    private boolean holdTax;
    private boolean hasHoldTax;

    public SavingBuilder withHoldTax(boolean holdTax) {
	this.holdTax = holdTax;
	this.hasHoldTax = true;
	return this;
    }

    private boolean dormancyTrackingActive;
    private boolean hasDormancyTrackingActive;

    public SavingBuilder withDormancyTrackingActive(boolean dormancyTrackingActive) {
	this.dormancyTrackingActive = dormancyTrackingActive;
	this.hasDormancyTrackingActive = true;
	return this;
    }

    private String taxGroupId;
    private boolean hasTaxGroupId;

    public SavingBuilder withTaxGroupId(String taxGroupId) {
	this.taxGroupId = taxGroupId;
	this.hasTaxGroupId = true;
	return this;
    }

    private String minBalanceForInterestCalculation;
    private boolean hasMinBalanceForInterestCalculation;

    public SavingBuilder withMinBalanceForInterestCalculation(String minBalanceForInterestCalculation) {
	this.minBalanceForInterestCalculation = minBalanceForInterestCalculation;
	this.hasMinBalanceForInterestCalculation = true;
	return this;
    }

    private String minRequiredBalance;
    private boolean hasMinRequiredBalance;

    public SavingBuilder withMinRequiredBalance(String minRequiredBalance) {
	this.minRequiredBalance = minRequiredBalance;
	this.hasMinRequiredBalance = true;
	return this;
    }

    private String overdraftLimit;
    private boolean hasOverdraftLimit;

    public SavingBuilder withOverdraftLimit(String overdraftLimit) {
	this.overdraftLimit = overdraftLimit;
	this.hasOverdraftLimit = true;
	return this;
    }

    private String nominalAnnualInterestRateOverdraft;
    private boolean hasNominalAnnualInterestRateOverdraft;

    public SavingBuilder withNominalAnnualInterestRateOverdraft(String nominalAnnualInterestRateOverdraft) {
	this.nominalAnnualInterestRateOverdraft = nominalAnnualInterestRateOverdraft;
	this.hasNominalAnnualInterestRateOverdraft = true;
	return this;
    }

    private String minOverdraftForInterestCalculation;
    private boolean hasMinOverdraftForInterestCalculation;

    public SavingBuilder withMinOverdraftForInterestCalculation(String minOverdraftForInterestCalculation) {
	this.minOverdraftForInterestCalculation = minOverdraftForInterestCalculation;
	this.hasMinOverdraftForInterestCalculation = true;
	return this;
    }

    private String daysToInactive;
    private boolean hasDaysToInactive;

    public SavingBuilder withDaysToInactive(String daysToInactive) {
	this.daysToInactive = daysToInactive;
	this.hasDaysToInactive = true;
	return this;
    }

    private String daysToDormancy;
    private boolean hasDaysToDormancy;

    public SavingBuilder withDaysToDormancy(String daysToDormancy) {
	this.daysToDormancy = daysToDormancy;
	this.hasDaysToDormancy = true;
	return this;
    }

    private String daysToEscheat;
    private boolean hasDaysToEscheat;

    public SavingBuilder withDaysToEscheat(String daysToEscheat) {
	this.daysToEscheat = daysToEscheat;
	this.hasDaysToEscheat = true;
	return this;
    }
    
    private String savingsReferenceAccountId;
    private boolean hassavingsReferenceAccountId;
    
    private String overdraftPortfolioControlId;
    private boolean hasoverdraftPortfolioControlId;
    
    private String savingsControlAccountId;
    private boolean hassavingsControlAccountId;
    
    private String transfersInSuspenseAccountId;
    private boolean hastransfersInSuspenseAccountId;
    
    private String escheatLiabilityId;
    private boolean hasescheatLiabilityId;
    
    private String interestOnSavingsAccountId;
    private boolean hasinterestOnSavingsAccountId;
    
    private String writeOffAccountId;
    private boolean haswriteOffAccountId;
    
    private String incomeFromFeeAccountId;
    private boolean hasincomeFromFeeAccountId;
    
    private String incomeFromPenaltyAccountId;
    private boolean hasincomeFromPenaltyAccountId;
    
    private String incomeFromInterestId;
    private boolean hasincomeFromInterestId;

    public JsonNode build() {
	JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

	if (this.hasDaysToEscheat) {
	    object.getObject().put("daysToEscheat", this.daysToEscheat);
	}

	if (this.hasDaysToDormancy) {
	    object.getObject().put("daysToDormancy", this.daysToDormancy);
	}

	if (this.hasDaysToInactive) {
	    object.getObject().put("daysToInactive", this.daysToInactive);
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

	if (this.hasMinRequiredBalance) {
	    object.getObject().put("minRequiredBalance", this.minRequiredBalance);
	}

	if (this.hasMinBalanceForInterestCalculation) {
	    object.getObject().put("minBalanceForInterestCalculation", this.minBalanceForInterestCalculation);
	}

	if (this.hasTaxGroupId) {
	    object.getObject().put("taxGroupId", this.taxGroupId);
	}

	if (this.hasDormancyTrackingActive) {
	    object.getObject().put("isDormancyTrackingActive", this.dormancyTrackingActive);
	}

	if (this.hasHoldTax) {
	    object.getObject().put("withHoldTax", this.holdTax);
	}

	if (this.hasAllowOverdraft) {
	    object.getObject().put("allowOverdraft", this.allowOverdraft);
	}

	if (this.hasEnforceMinRequiredBalance) {
	    object.getObject().put("enforceMinRequiredBalance", this.enforceMinRequiredBalance);
	}

	if (this.hasWithdrawalFeeForTransfers) {
	    object.getObject().put("withdrawalFeeForTransfers", this.withdrawalFeeForTransfers);
	}

	if (this.hasLockinPeriodFrequencyType) {
	    object.getObject().put("lockinPeriodFrequencyType", this.lockinPeriodFrequencyType);
	}

	if (this.hasLockinPeriodFrequency) {
	    object.getObject().put("lockinPeriodFrequency", this.lockinPeriodFrequency);
	}

	if (this.hasMinRequiredOpeningBalance) {
	    object.getObject().put("minRequiredOpeningBalance", this.minRequiredOpeningBalance);
	}

	if (this.hasNominalAnnualInterestRate) {
	    object.getObject().put("nominalAnnualInterestRate", this.nominalAnnualInterestRate);
	}

	if (this.hasInterestCalculationDaysInYearType) {
	    object.getObject().put("interestCalculationDaysInYearType", this.interestCalculationDaysInYearType);
	}

	if (this.hasInterestCalculationType) {
	    object.getObject().put("interestCalculationType", this.interestCalculationType);
	}

	if (this.hasInterestPostingPeriodType) {
	    object.getObject().put("interestPostingPeriodType", this.interestPostingPeriodType);
	}

	if (this.hasInterestCompoundingPeriodType) {
	    object.getObject().put("interestCompoundingPeriodType", this.interestCompoundingPeriodType);
	}

	if (this.hasCharges) {
	    object.getObject().put("charges", this.charges);
	}

	if (this.hasPenaltyToIncomeAccountMappings) {
	    object.getObject().put("penaltyToIncomeAccountMappings", this.penaltyToIncomeAccountMappings);
	}

	if (this.hasFeeToIncomeAccountMappings) {
	    object.getObject().put("feeToIncomeAccountMappings", this.feeToIncomeAccountMappings);
	}

	if (this.hasPaymentChannelToFundSourceMappings) {
	    object.getObject().put("paymentChannelToFundSourceMappings", this.paymentChannelToFundSourceMappings);
	}

	if (this.hasInMultiplesOf) {
	    object.getObject().put("inMultiplesOf", this.inMultiplesOf);
	}

	if (this.hasDescription) {
	    object.getObject().put("description", this.description);
	}

	if (this.hasLocale) {
	    object.getObject().put("locale", this.locale);
	}

	if (this.hasAccountingRule) {
	    object.getObject().put("accountingRule", this.accountingRule);
	}

	if (this.hasName) {
	    object.getObject().put("name", this.name);
	}

	if (this.hasShortName) {
	    object.getObject().put("shortName", shortName);
	}

	if (this.hasCurrencyCode) {
	    object.getObject().put("currencyCode", this.currencyCode);
	}

	if (this.hasDigitsAfterDecimal) {
	    object.getObject().put("digitsAfterDecimal", this.digitsAfterDecimal);
	}

	return object;
    }

}
