package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.mashape.unirest.http.JsonNode;

public class LoanBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public LoanBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public LoanBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public LoanBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Integer digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public LoanBuilder withDigitsAfterDecimal(Integer digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private Double numberOfRepayments;
    private boolean hasNumberOfRepayments;

    public LoanBuilder withNumberOfRepayments(Double numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
        this.hasNumberOfRepayments = true;
        return this;
    }

    private Integer repaymentEvery;
    private boolean hasRepaymentEvery;

    public LoanBuilder withRepaymentEvery(Integer repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
        this.hasRepaymentEvery = true;
        return this;
    }

    private Integer repaymentFrequencyType;
    private boolean hasRepaymentFrequencyType;

    public LoanBuilder withRepaymentFrequencyType(Integer repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
        this.hasRepaymentFrequencyType = true;
        return this;
    }

    private Integer amortizationType;
    private boolean hasAmortizationType;

    public LoanBuilder withAmortizationType(Integer amortizationType) {
        this.amortizationType = amortizationType;
        this.hasAmortizationType = true;
        return this;
    }

    private Integer interestCalculationPeriodType;
    private boolean hasInterestCalculationPeriodType;

    public LoanBuilder hasInterestCalculationPeriodType(Integer interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.hasInterestCalculationPeriodType = true;
        return this;
    }

    private Integer transactionProcessingStrategyId;
    private boolean hasTransactionProcessingStrategyId;

    public LoanBuilder withTransactionProcessingStrategyId(Integer transactionProcessingStrategyId) {
        this.transactionProcessingStrategyId = transactionProcessingStrategyId;
        this.hasTransactionProcessingStrategyId = true;
        return this;
    }

    private Integer daysInYearType;
    private boolean hasDaysInYearType;

    public LoanBuilder withDaysInYearType(Integer daysInYearType) {
        this.daysInYearType = daysInYearType;
        this.hasDaysInYearType = true;
        return this;
    }

    private Integer daysInMonthType;
    private boolean hasDaysInMonthType;

    public LoanBuilder withDaysInMonthType(Integer daysInMonthType) {
        this.daysInMonthType = daysInMonthType;
        this.hasDaysInMonthType = true;
        return this;
    }

    private boolean interestRecalculationEnabled;
    private boolean hasInterestRecalculationEnabled;

    public LoanBuilder withInterestRecalculationEnabled(boolean interestRecalculationEnabled) {
        this.interestRecalculationEnabled = interestRecalculationEnabled;
        this.hasInterestRecalculationEnabled = true;
        return this;
    }

    private Integer interestRatePerPeriod;
    private boolean hasInterestRatePerPeriod;

    public LoanBuilder withInterestRatePerPeriod(Integer interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
        this.hasInterestRatePerPeriod = true;
        return this;
    }

    private Integer interestRateFrequencyType;
    private boolean hasInterestRateFrequencyType;

    public LoanBuilder withInterestRateFrequencyType(Integer interestRateFrequencyType) {
        this.interestRateFrequencyType = interestRateFrequencyType;
        this.hasInterestRateFrequencyType = true;
        return this;
    }

    private Integer accountingRule;
    private boolean hasAccountingRule;

    public LoanBuilder withAccountingRule(Integer accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public LoanBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date startDate;
    private boolean hasStartDate;

    public LoanBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        this.hasStartDate = true;
        return this;
    }

    private Date closeDate;
    private boolean hasCloseDate;

    public LoanBuilder withCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        this.hasCloseDate = true;
        return this;
    }

    private String fundId;
    private boolean hasFundId;

    public LoanBuilder withFundId(String fundId) {
        this.fundId = fundId;
        this.hasFundId = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public LoanBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private boolean includeInBorrowerCycle;
    private boolean hasIncludeInBorrowerCycle;

    public LoanBuilder withIncludeInBorrowerCycle(boolean includeInBorrowerCycle) {
        this.includeInBorrowerCycle = includeInBorrowerCycle;
        this.hasIncludeInBorrowerCycle = true;
        return this;
    }

    private Integer installmentAmountInMultiplesOf;
    private Boolean hasInstallmentAmountInMultiplesOf;

    public LoanBuilder withInstallmentAmountInMultiplesOf(Integer installmentAmountInMultiplesOf) {
        this.installmentAmountInMultiplesOf = installmentAmountInMultiplesOf;
        this.hasInstallmentAmountInMultiplesOf = true;
        return this;
    }

    private Integer inMultiplesOf;
    private boolean hasInMultiplesOf;

    public LoanBuilder withInMultiplesOf(Integer inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private Integer minPrincipal;
    private boolean hasMinPrincipal;

    public LoanBuilder withMinPrincipal(Integer minPrincipal) {
        this.minPrincipal = minPrincipal;
        this.hasMinPrincipal = true;
        return this;
    }

    private Integer maxPrincipal;
    private boolean hasMaxPrincipal;

    public LoanBuilder withMaxPrincipal(Integer maxPrincipal) {
        this.maxPrincipal = maxPrincipal;
        this.hasMaxPrincipal = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasMaxPrincipal) {
            object.getObject().put("maxPrincipal", this.maxPrincipal);
        }

        if (this.hasMinPrincipal) {
            object.getObject().put("minPrincipal", this.minPrincipal);
        }

        if (this.hasInMultiplesOf) {
            object.getObject().put("inMultiplesOf", this.inMultiplesOf);
        }

        if (this.hasInstallmentAmountInMultiplesOf) {
            object.getObject().put("installmentAmountInMultiplesOf", this.installmentAmountInMultiplesOf);
        }

        if (this.hasIncludeInBorrowerCycle) {
            object.getObject().put("includeInBorrowerCycle", this.includeInBorrowerCycle);
        }

        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }

        if (this.hasFundId) {
            object.getObject().put("fundId", this.fundId);
        }

        if (this.hasCloseDate) {
            if (this.closeDate != null) {
                object.getObject().put("closeDate", DateFormatUtils.format(this.closeDate, this.dateFormat));
            } else {
                object.getObject().put("closeDate", (String) null);
            }
        }

        if (this.hasStartDate) {
            if (this.startDate != null) {
                object.getObject().put("startDate", DateFormatUtils.format(this.startDate, this.dateFormat));
            } else {
                object.getObject().put("startDate", (String) null);
            }
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasAccountingRule) {
            object.getObject().put("accountingRule", this.accountingRule);
        }

        if (this.hasInterestRateFrequencyType) {
            object.getObject().put("interestRateFrequencyType", this.interestRateFrequencyType);
        }

        if (this.hasInterestRatePerPeriod) {
            object.getObject().put("interestRatePerPeriod", this.interestRatePerPeriod);
        }

        if (this.hasInterestRecalculationEnabled) {
            object.getObject().put("isInterestRecalculationEnabled", interestRecalculationEnabled);
        }

        if (this.hasDaysInMonthType) {
            object.getObject().put("daysInMonthType", this.daysInMonthType);
        }

        if (this.hasDaysInYearType) {
            object.getObject().put("daysInYearType", this.daysInYearType);
        }

        if (this.hasTransactionProcessingStrategyId) {
            object.getObject().put("transactionProcessingStrategyId", this.transactionProcessingStrategyId);
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

        if (this.hasNumberOfRepayments) {
            object.getObject().put("numberOfRepayments", this.numberOfRepayments);
        }

        if (this.hasRepaymentEvery) {
            object.getObject().put("repaymentEvery", this.repaymentEvery);
        }

        if (this.hasRepaymentFrequencyType) {
            object.getObject().put("repaymentFrequencyType", this.repaymentFrequencyType);
        }

        if (this.hasAmortizationType) {
            object.getObject().put("amortizationType", this.amortizationType);
        }

        if (this.hasInterestCalculationPeriodType) {
            object.getObject().put("interestCalculationPeriodType", this.interestCalculationPeriodType);
        }
        return object;
    }

}
