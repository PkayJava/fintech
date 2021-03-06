package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.MinimumActivePeriod;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.openunirest.http.JsonNode;

public class ProductShareBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public ProductShareBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public ProductShareBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public ProductShareBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Long digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public ProductShareBuilder withDigitsAfterDecimal(Long digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private AccountingType accountingRule;
    private boolean hasAccountingRule;

    public ProductShareBuilder withAccountingRule(AccountingType accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ProductShareBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public ProductShareBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private Long inMultiplesOf;
    private boolean hasInMultiplesOf;

    public ProductShareBuilder withInMultiplesOf(Long inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public ProductShareBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    private List<Map<String, Object>> marketPricePeriods = Lists.newArrayList();
    private boolean hasMarketPricePeriods;

    public ProductShareBuilder withMarketPricePeriods(Date fromDate, Double shareValue) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("locale", "en");
        item.put("dateFormat", "yyyy-MM-dd");
        item.put("fromDate", DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(fromDate));
        item.put("shareValue", shareValue);
        this.marketPricePeriods.add(item);
        this.hasMarketPricePeriods = true;
        return this;
    }

    private Long totalShares;
    private boolean hasTotalShares;

    public ProductShareBuilder withTotalShares(Long totalShares) {
        this.totalShares = totalShares;
        this.hasTotalShares = true;
        return this;
    }

    private Long sharesIssued;
    private boolean hasSharesIssued;

    public ProductShareBuilder withSharesIssued(Long sharesIssued) {
        this.sharesIssued = sharesIssued;
        this.hasSharesIssued = true;
        return this;
    }

    private Double unitPrice;
    private boolean hasUnitPrice;

    public ProductShareBuilder withUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        this.hasUnitPrice = true;
        return this;
    }

    private Double shareCapital;
    private boolean hasShareCapital;

    public ProductShareBuilder withShareCapital(Double shareCapital) {
        this.shareCapital = shareCapital;
        this.hasShareCapital = true;
        return this;
    }

    private Long minimumShares;
    private boolean hasMinimumShares;

    public ProductShareBuilder withMinimumShares(Long minimumShares) {
        this.minimumShares = minimumShares;
        this.hasMinimumShares = true;
        return this;
    }

    private Long nominalShares;
    private boolean hasNominalShares;

    public ProductShareBuilder withNominalShares(Long nominalShares) {
        this.nominalShares = nominalShares;
        this.hasNominalShares = true;
        return this;
    }

    private Long maximumShares;
    private boolean hasMaximumShares;

    public ProductShareBuilder withMaximumShares(Long maximumShares) {
        this.maximumShares = maximumShares;
        this.hasMaximumShares = true;
        return this;
    }

    private Long minimumActivePeriodForDividends;
    private boolean hasMinimumActivePeriodForDividends;

    public ProductShareBuilder withMinimumActivePeriodForDividends(Long minimumActivePeriodForDividends) {
        this.minimumActivePeriodForDividends = minimumActivePeriodForDividends;
        this.hasMinimumActivePeriodForDividends = true;
        return this;
    }

    private MinimumActivePeriod minimumactiveperiodFrequencyType;
    private boolean hasMinimumactiveperiodFrequencyType;

    public ProductShareBuilder withMinimumactiveperiodFrequencyType(MinimumActivePeriod minimumactiveperiodFrequencyType) {
        this.minimumactiveperiodFrequencyType = minimumactiveperiodFrequencyType;
        this.hasMinimumactiveperiodFrequencyType = true;
        return this;
    }

    private Long lockinPeriodFrequency;
    private boolean hasLockinPeriodFrequency;

    public ProductShareBuilder withLockinPeriodFrequency(Long lockinPeriodFrequency) {
        this.lockinPeriodFrequency = lockinPeriodFrequency;
        this.hasLockinPeriodFrequency = true;
        return this;
    }

    private LockInType lockinPeriodFrequencyType;
    private boolean hasLockinPeriodFrequencyType;

    public ProductShareBuilder withLockinPeriodFrequencyType(LockInType lockinPeriodFrequencyType) {
        this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
        this.hasLockinPeriodFrequencyType = true;
        return this;
    }

    private Boolean allowDividendCalculationForInactiveClients;
    private boolean hasAllowDividendCalculationForInactiveClients;

    public ProductShareBuilder withAllowDividendCalculationForInactiveClients(Boolean allowDividendCalculationForInactiveClients) {
        this.allowDividendCalculationForInactiveClients = allowDividendCalculationForInactiveClients;
        this.hasAllowDividendCalculationForInactiveClients = true;
        return this;
    }

    private String shareReferenceId;
    private boolean hasShareReferenceId;

    public ProductShareBuilder withShareReferenceId(String shareReferenceId) {
        this.shareReferenceId = shareReferenceId;
        this.hasShareReferenceId = true;
        return this;
    }

    private String shareSuspenseId;
    private boolean hasShareSuspenseId;

    public ProductShareBuilder withShareSuspenseId(String shareSuspenseId) {
        this.shareSuspenseId = shareSuspenseId;
        this.hasShareSuspenseId = true;
        return this;
    }

    private String shareEquityId;
    private boolean hasShareEquityId;

    public ProductShareBuilder withShareEquityId(String shareEquityId) {
        this.shareEquityId = shareEquityId;
        this.hasShareEquityId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public ProductShareBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    public JsonNode build() {

        List<String> errors = Lists.newArrayList();

        if (this.name == null || "".equals(this.name)) {
            errors.add("name is required");
        }

        if (this.shortName == null || "".equals(this.shortName)) {
            errors.add("shortName is required");
        }

        if (this.description == null || "".equals(this.description)) {
            errors.add("description is required");
        }

        if (this.currencyCode == null || "".equals(this.currencyCode)) {
            errors.add("currencyCode is required");
        }

        if (this.digitsAfterDecimal == null) {
            errors.add("digitsAfterDecimal is required");
        }

        if (this.totalShares == null) {
            errors.add("totalShares is required");
        }

        if (this.unitPrice == null) {
            errors.add("unitPrice is required");
        }

        if (this.nominalShares == null) {
            errors.add("nominalShares is required");
        }

        if (this.allowDividendCalculationForInactiveClients == null) {
            errors.add("allowDividendCalculationForInactiveClients is required");
        }

        if (this.sharesIssued == null) {
            errors.add("sharesIssued is required");
        }

        if (this.accountingRule == null) {
            errors.add("accountingRule is required");
        } else {
            if (this.accountingRule == AccountingType.Cash) {
                if (this.shareReferenceId == null || "".equals(this.shareReferenceId)) {
                    errors.add("shareReferenceId is required");
                }
                if (this.shareSuspenseId == null || "".equals(this.shareSuspenseId)) {
                    errors.add("shareSuspenseId is required");
                }
                if (this.shareEquityId == null || "".equals(this.shareEquityId)) {
                    errors.add("shareEquityId is required");
                }
                if (this.incomeFromFeeAccountId == null || "".equals(this.incomeFromFeeAccountId)) {
                    errors.add("incomeFromFeeAccountId is required");
                }
            }
        }

        if (!errors.isEmpty()) {
            // throw new IllegalArgumentException("invalid builder :: " +
            // StringUtils.join(errors, ","));
            System.out.println("invalid builder :: " + StringUtils.join(errors, ","));
        }

        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasIncomeFromFeeAccountId) {
            object.getObject().put("incomeFromFeeAccountId", this.incomeFromFeeAccountId);
        }

        if (this.hasShareEquityId) {
            object.getObject().put("shareEquityId", this.shareEquityId);
        }

        if (this.hasShareSuspenseId) {
            object.getObject().put("shareSuspenseId", this.shareSuspenseId);
        }

        if (this.hasShareReferenceId) {
            object.getObject().put("shareReferenceId", this.shareReferenceId);
        }

        if (this.hasAllowDividendCalculationForInactiveClients) {
            object.getObject().put("allowDividendCalculationForInactiveClients", this.allowDividendCalculationForInactiveClients);
        }

        if (this.hasLockinPeriodFrequency) {
            object.getObject().put("lockinPeriodFrequency", this.lockinPeriodFrequency);
        }

        if (this.hasLockinPeriodFrequencyType) {
            if (this.lockinPeriodFrequencyType != null) {
                object.getObject().put("lockinPeriodFrequencyType", this.lockinPeriodFrequencyType.getLiteral());
            } else {
                object.getObject().put("lockinPeriodFrequencyType", (String) null);
            }
        }

        if (this.hasMinimumactiveperiodFrequencyType) {
            if (this.minimumactiveperiodFrequencyType != null) {
                object.getObject().put("minimumactiveperiodFrequencyType", this.minimumactiveperiodFrequencyType.getLiteral());
            } else {
                object.getObject().put("minimumactiveperiodFrequencyType", (String) null);
            }
        }

        if (this.hasMinimumActivePeriodForDividends) {
            object.getObject().put("minimumActivePeriodForDividends", this.minimumActivePeriodForDividends);
        }

        if (this.hasMaximumShares) {
            object.getObject().put("maximumShares", this.maximumShares);
        }

        if (this.hasNominalShares) {
            object.getObject().put("nominalShares", this.nominalShares);
        }

        if (this.hasMinimumShares) {
            object.getObject().put("minimumShares", this.minimumShares);
        }

        if (this.hasShareCapital) {
            object.getObject().put("shareCapital", this.shareCapital);
        }

        if (this.hasUnitPrice) {
            object.getObject().put("unitPrice", this.unitPrice);
        }

        if (this.hasSharesIssued) {
            object.getObject().put("sharesIssued", this.sharesIssued);
        }

        if (this.hasTotalShares) {
            object.getObject().put("totalShares", this.totalShares);
        }

        if (this.hasMarketPricePeriods) {
            object.getObject().put("marketPricePeriods", this.marketPricePeriods);
        }

        if (this.hasCharges) {
            object.getObject().put("chargesSelected", this.charges);
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
            object.getObject().put("accountingRule", this.accountingRule.getLiteral());
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
