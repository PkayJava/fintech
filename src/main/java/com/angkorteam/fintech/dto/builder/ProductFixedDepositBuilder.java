package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ApplyPenalOn;
import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.OperandType;
import com.angkorteam.fintech.dto.enums.Operator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class ProductFixedDepositBuilder implements Serializable {

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public ProductFixedDepositBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("paymentTypeId", paymentTypeId);
        item.put("fundSourceAccountId", fundSourceAccountId);
        this.paymentChannelToFundSourceMappings.add(item);
        this.hasPaymentChannelToFundSourceMappings = true;
        return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public ProductFixedDepositBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.feeToIncomeAccountMappings.add(item);
        this.hasFeeToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public ProductFixedDepositBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.penaltyToIncomeAccountMappings.add(item);
        this.hasPenaltyToIncomeAccountMappings = true;
        return this;
    }

    private Double depositAmount;
    private boolean hasDepositAmount;

    public ProductFixedDepositBuilder withDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
        this.hasDepositAmount = true;
        return this;
    }

    private Double minDepositAmount;
    private boolean hasMinDepositAmount;

    public ProductFixedDepositBuilder withMinDepositAmount(Double minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
        this.hasMinDepositAmount = true;
        return this;
    }

    private Double maxDepositAmount;
    private boolean hasMaxDepositAmount;

    public ProductFixedDepositBuilder withMaxDepositAmount(Double maxDepositAmount) {
        this.maxDepositAmount = maxDepositAmount;
        this.hasMaxDepositAmount = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public ProductFixedDepositBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public ProductFixedDepositBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String name;
    private boolean hasName;

    public ProductFixedDepositBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private Long inMultiplesOf;
    private boolean hasInMultiplesOf;

    public ProductFixedDepositBuilder withInMultiplesOf(Long inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private String taxGroupId;
    private boolean hasTaxGroupId;

    public ProductFixedDepositBuilder withTaxGroupId(String taxGroupId) {
        this.taxGroupId = taxGroupId;
        this.hasTaxGroupId = true;
        return this;
    }

    private boolean holdTax;
    private boolean hasHoldTax;

    public ProductFixedDepositBuilder withHoldTax(boolean holdTax) {
        this.holdTax = holdTax;
        this.hasHoldTax = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public ProductFixedDepositBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Long digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public ProductFixedDepositBuilder withDigitsAfterDecimal(Long digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public ProductFixedDepositBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ProductFixedDepositBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private AccountingType accountingRule;
    private boolean hasAccountingRule;

    public ProductFixedDepositBuilder withAccountingRule(AccountingType accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private InterestCompoundingPeriod interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    public ProductFixedDepositBuilder withInterestCompoundingPeriodType(InterestCompoundingPeriod interestCompoundingPeriodType) {
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.hasInterestCompoundingPeriodType = true;
        return this;
    }

    private InterestPostingPeriod interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    public ProductFixedDepositBuilder withInterestPostingPeriodType(InterestPostingPeriod interestPostingPeriodType) {
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.hasInterestPostingPeriodType = true;
        return this;
    }

    private InterestCalculatedUsing interestCalculationType;
    private boolean hasInterestCalculationType;

    public ProductFixedDepositBuilder withInterestCalculationType(InterestCalculatedUsing interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
        this.hasInterestCalculationType = true;
        return this;
    }

    private DayInYear interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    public ProductFixedDepositBuilder withInterestCalculationDaysInYearType(DayInYear interestCalculationDaysInYearType) {
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.hasInterestCalculationDaysInYearType = true;
        return this;
    }

    private ApplyPenalOn preClosurePenalInterestOnTypeId;
    private boolean hasPreClosurePenalInterestOnTypeId;

    public ProductFixedDepositBuilder withPreClosurePenalInterestOnTypeId(ApplyPenalOn preClosurePenalInterestOnTypeId) {
        this.preClosurePenalInterestOnTypeId = preClosurePenalInterestOnTypeId;
        this.hasPreClosurePenalInterestOnTypeId = true;
        return this;
    }

    private String savingsReferenceAccountId;
    private boolean hasSavingsReferenceAccountId;

    public ProductFixedDepositBuilder withSavingsReferenceAccountId(String savingsReferenceAccountId) {
        this.savingsReferenceAccountId = savingsReferenceAccountId;
        this.hasSavingsReferenceAccountId = true;
        return this;
    }

    private String savingsControlAccountId;
    private boolean hasSavingsControlAccountId;

    public ProductFixedDepositBuilder withSavingsControlAccountId(String savingsControlAccountId) {
        this.savingsControlAccountId = savingsControlAccountId;
        this.hasSavingsControlAccountId = true;
        return this;
    }

    private String interestOnSavingsAccountId;
    private boolean hasInterestOnSavingsAccountId;

    public ProductFixedDepositBuilder withInterestOnSavingsAccountId(String interestOnSavingsAccountId) {
        this.interestOnSavingsAccountId = interestOnSavingsAccountId;
        this.hasInterestOnSavingsAccountId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public ProductFixedDepositBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    private String incomeFromPenaltyAccountId;
    private boolean hasIncomeFromPenaltyAccountId;

    public ProductFixedDepositBuilder withIncomeFromPenaltyAccountId(String incomeFromPenaltyAccountId) {
        this.incomeFromPenaltyAccountId = incomeFromPenaltyAccountId;
        this.hasIncomeFromPenaltyAccountId = true;
        return this;
    }

    private String transfersInSuspenseAccountId;
    private boolean hasTransfersInSuspenseAccountId;

    public ProductFixedDepositBuilder withTransfersInSuspenseAccountId(String transfersInSuspenseAccountId) {
        this.transfersInSuspenseAccountId = transfersInSuspenseAccountId;
        this.hasTransfersInSuspenseAccountId = true;
        return this;
    }

    private boolean hasCharts;
    private List<Map<String, Object>> charts = Lists.newArrayList();

    private Date fromDate;
    private boolean hasFromDate;

    public ProductFixedDepositBuilder withFromDate(Date fromDate) {
        this.fromDate = fromDate;
        this.hasFromDate = true;
        this.hasCharts = true;
        return this;
    }

    private Date endDate;
    private boolean hasEndDate;

    public ProductFixedDepositBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        this.hasEndDate = true;
        this.hasCharts = true;
        return this;
    }

    private boolean isPrimaryGroupingByAmount;
    private boolean hasPrimaryGroupingByAmount;

    public ProductFixedDepositBuilder withPrimaryGroupingByAmount(boolean isPrimaryGroupingByAmount) {
        this.isPrimaryGroupingByAmount = isPrimaryGroupingByAmount;
        this.hasPrimaryGroupingByAmount = true;
        this.hasCharts = true;
        return this;

    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ProductFixedDepositBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private List<Map<String, Object>> chartSlabs = Lists.newArrayList();
    private boolean hasChartSlabs;

    public ProductFixedDepositBuilder withChartSlab(LockInType periodType, Long fromPeriod, Long toPeriod, Long amountRangeFrom, Long amountRangeTo, Double annualInterestRate, String description, List<JSONObject> incentives) {
        Map<String, Object> chartSlab = Maps.newHashMap();
        chartSlab.put("periodType", periodType == null ? null : periodType.getLiteral());
        chartSlab.put("fromPeriod", fromPeriod == null ? null : fromPeriod);
        chartSlab.put("toPeriod", toPeriod == null ? null : toPeriod);
        chartSlab.put("amountRangeFrom", amountRangeFrom);
        chartSlab.put("amountRangeTo", amountRangeTo);
        chartSlab.put("annualInterestRate", annualInterestRate);
        chartSlab.put("locale", "en");
        if (incentives != null) {
            for (JSONObject incentive : incentives) {
                incentive.put("entityType", "2");
            }
        }
        chartSlab.put("incentives", incentives);
        this.chartSlabs.add(chartSlab);
        this.hasChartSlabs = true;
        this.hasCharts = true;
        return this;
    }

    private Long lockInPeriodFrequency;
    private boolean hasLockInPeriodFrequency;

    public ProductFixedDepositBuilder withLockInPeriodFrequency(Long lockInPeriodFrequency) {
        this.lockInPeriodFrequency = lockInPeriodFrequency;
        this.hasLockInPeriodFrequency = true;
        return this;
    }

    private LockInType lockInPeriodFrequencyType;
    private boolean hasLockInPeriodFrequencyType;

    public ProductFixedDepositBuilder withLockinPeriodFrequencyType(LockInType lockInPeriodFrequencyType) {
        this.lockInPeriodFrequencyType = lockInPeriodFrequencyType;
        this.hasLockInPeriodFrequencyType = true;
        return this;
    }

    private Long minDepositTerm;
    private boolean hasMinDepositTerm;

    public ProductFixedDepositBuilder withMinDepositTerm(Long minDepositTerm) {
        this.minDepositTerm = minDepositTerm;
        this.hasMinDepositTerm = true;
        return this;
    }

    private LockInType minDepositTermTypeId;
    private boolean hasMinDepositTermTypeId;

    public ProductFixedDepositBuilder withMinDepositTermTypeId(LockInType minDepositTermTypeId) {
        this.minDepositTermTypeId = minDepositTermTypeId;
        this.hasMinDepositTermTypeId = true;
        return this;
    }

    private Long inMultiplesOfDepositTerm;
    private boolean hasInMultiplesOfDepositTerm;

    public ProductFixedDepositBuilder withInMultiplesOfDepositTerm(Long inMultiplesOfDepositTerm) {
        this.inMultiplesOfDepositTerm = inMultiplesOfDepositTerm;
        this.hasInMultiplesOfDepositTerm = true;
        return this;
    }

    private LockInType inMultiplesOfDepositTermTypeId;
    private boolean hasInMultiplesOfDepositTermTypeId;

    public ProductFixedDepositBuilder withInMultiplesOfDepositTermTypeId(LockInType inMultiplesOfDepositTermTypeId) {
        this.inMultiplesOfDepositTermTypeId = inMultiplesOfDepositTermTypeId;
        this.hasInMultiplesOfDepositTermTypeId = true;
        return this;
    }

    private Long maxDepositTerm;
    private boolean hasMaxDepositTerm;

    public ProductFixedDepositBuilder withMaxDepositTerm(Long maxDepositTerm) {
        this.maxDepositTerm = maxDepositTerm;
        this.hasMaxDepositTerm = true;
        return this;
    }

    private LockInType maxDepositTermTypeId;
    private boolean hasMaxDepositTermTypeId;

    public ProductFixedDepositBuilder withMaxDepositTermTypeId(LockInType maxDepositTermTypeId) {
        this.maxDepositTermTypeId = maxDepositTermTypeId;
        this.hasMaxDepositTermTypeId = true;
        return this;
    }

    private boolean preClosurePenalApplicable;
    private boolean hasPreClosurePenalApplicable;

    public ProductFixedDepositBuilder withPreClosurePenalApplicable(boolean preClosurePenalApplicable) {
        this.preClosurePenalApplicable = preClosurePenalApplicable;
        this.hasPreClosurePenalApplicable = true;
        return this;
    }

    private Double preClosurePenalInterest;
    private boolean hasPreClosurePenalInterest;

    public ProductFixedDepositBuilder withPreClosurePenalInterest(Double preClosurePenalInterest) {
        this.preClosurePenalInterest = preClosurePenalInterest;
        this.hasPreClosurePenalInterest = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

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

        if (this.inMultiplesOf == null) {
            errors.add("inMultiplesOf is required");
        }

        if (this.interestCompoundingPeriodType == null) {
            errors.add("interestCompoundingPeriodType is required");
        }

        if (this.interestCalculationType == null) {
            errors.add("interestCalculationType is required");
        }

        if (this.interestCalculationDaysInYearType == null) {
            errors.add("interestCalculationDaysInYearType is required");
        }

        if (this.minDepositTerm == null) {
            errors.add("minDepositTerm is required");
        }

        if (this.minDepositTermTypeId == null) {
            errors.add("minDepositTermTypeId is required");
        }

        if (this.depositAmount == null) {
            errors.add("depositAmount is required");
        }

        if (this.accountingRule == null) {
            errors.add("accountingRule is required");
        } else {
            if (this.accountingRule == AccountingType.Cash) {
                if (this.savingsReferenceAccountId == null || "".equals(this.savingsReferenceAccountId)) {
                    errors.add("savingsReferenceAccountId is required");
                }
                if (this.savingsControlAccountId == null || "".equals(this.savingsControlAccountId)) {
                    errors.add("savingsControlAccountId is required");
                }
                if (this.interestOnSavingsAccountId == null || "".equals(this.interestOnSavingsAccountId)) {
                    errors.add("interestOnSavingsAccountId is required");
                }
                if (this.incomeFromFeeAccountId == null || "".equals(this.incomeFromFeeAccountId)) {
                    errors.add("incomeFromFeeAccountId is required");
                }
                if (this.transfersInSuspenseAccountId == null || "".equals(this.transfersInSuspenseAccountId)) {
                    errors.add("transfersInSuspenseAccountId is required");
                }
                if (this.incomeFromPenaltyAccountId == null || "".equals(this.incomeFromPenaltyAccountId)) {
                    errors.add("incomeFromPenaltyAccountId is required");
                }
            }
        }

        if (!errors.isEmpty()) {
            // throw new IllegalArgumentException("invalid builder :: " +
            // StringUtils.join(errors, ","));
            System.out.println("invalid builder :: " + StringUtils.join(errors, ","));
        }

        if (this.hasPreClosurePenalInterest) {
            object.getObject().put("preClosurePenalInterest", this.preClosurePenalInterest);
        }

        if (this.hasPreClosurePenalApplicable) {
            object.getObject().put("preClosurePenalApplicable", this.preClosurePenalApplicable);
        }

        if (this.hasMaxDepositTermTypeId) {
            if (this.maxDepositTermTypeId != null) {
                object.getObject().put("maxDepositTermTypeId", this.maxDepositTermTypeId.getLiteral());
            } else {
                object.getObject().put("maxDepositTermTypeId", (String) null);
            }
        }

        if (this.hasMaxDepositTerm) {
            object.getObject().put("maxDepositTerm", this.maxDepositTerm);
        }

        if (this.hasInMultiplesOfDepositTermTypeId) {
            if (this.inMultiplesOfDepositTermTypeId != null) {
                object.getObject().put("inMultiplesOfDepositTermTypeId", this.inMultiplesOfDepositTermTypeId.getLiteral());
            } else {
                object.getObject().put("inMultiplesOfDepositTermTypeId", (String) null);
            }
        }

        if (this.hasInMultiplesOfDepositTerm) {
            object.getObject().put("inMultiplesOfDepositTerm", this.inMultiplesOfDepositTerm);
        }

        if (this.hasMinDepositTermTypeId) {
            if (this.minDepositTermTypeId != null) {
                object.getObject().put("minDepositTermTypeId", this.minDepositTermTypeId.getLiteral());
            } else {
                object.getObject().put("minDepositTermTypeId", (String) null);
            }
        }

        if (this.hasMinDepositTerm) {
            object.getObject().put("minDepositTerm", this.minDepositTerm);
        }

        if (this.hasLockInPeriodFrequencyType) {
            if (this.lockInPeriodFrequencyType != null) {
                object.getObject().put("lockinPeriodFrequencyType", this.lockInPeriodFrequencyType.getLiteral());
            } else {
                object.getObject().put("lockinPeriodFrequencyType", (String) null);
            }
        }
        if (this.hasLockInPeriodFrequency) {
            object.getObject().put("lockinPeriodFrequency", this.lockInPeriodFrequency);
        }

        if (this.hasMinDepositAmount) {
            object.getObject().put("minDepositAmount", this.minDepositAmount);
        }

        if (this.hasMaxDepositAmount) {
            object.getObject().put("maxDepositAmount", this.maxDepositAmount);
        }

        if (this.hasCharts) {
            Map<String, Object> chart = Maps.newHashMap();
            if (this.hasDateFormat) {
                chart.put("dateFormat", this.dateFormat);
            }
            if (this.hasLocale) {
                chart.put("locale", this.locale);
            }
            if (this.hasPrimaryGroupingByAmount) {
                chart.put("isPrimaryGroupingByAmount", this.isPrimaryGroupingByAmount);
            }
            if (this.hasEndDate) {
                if (this.endDate != null) {
                    chart.put("endDate", DateFormatUtils.format(this.endDate, this.dateFormat));
                } else {
                    chart.put("endDate", (String) null);
                }
            }
            if (this.hasFromDate) {
                if (this.fromDate != null) {
                    chart.put("fromDate", DateFormatUtils.format(this.fromDate, this.dateFormat));
                } else {
                    chart.put("fromDate", (String) null);
                }
            }
            if (this.hasChartSlabs) {
                chart.put("chartSlabs", this.chartSlabs);
            }
            this.charts.add(chart);
            object.getObject().put("charts", this.charts);
        }

        if (this.hasTransfersInSuspenseAccountId) {
            object.getObject().put("transfersInSuspenseAccountId", this.transfersInSuspenseAccountId);
        }

        if (this.hasIncomeFromPenaltyAccountId) {
            object.getObject().put("incomeFromPenaltyAccountId", this.incomeFromPenaltyAccountId);
        }

        if (this.hasIncomeFromFeeAccountId) {
            object.getObject().put("incomeFromFeeAccountId", this.incomeFromFeeAccountId);
        }

        if (this.hasInterestOnSavingsAccountId) {
            object.getObject().put("interestOnSavingsAccountId", this.interestOnSavingsAccountId);
        }

        if (this.hasSavingsControlAccountId) {
            object.getObject().put("savingsControlAccountId", this.savingsControlAccountId);
        }

        if (this.hasSavingsReferenceAccountId) {
            object.getObject().put("savingsReferenceAccountId", this.savingsReferenceAccountId);
        }

        if (this.hasPreClosurePenalInterestOnTypeId) {
            if (this.preClosurePenalInterestOnTypeId != null) {
                object.getObject().put("preClosurePenalInterestOnTypeId", this.preClosurePenalInterestOnTypeId.getLiteral());
            } else {
                object.getObject().put("preClosurePenalInterestOnTypeId", (String) null);
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

        if (this.hasAccountingRule) {
            object.getObject().put("accountingRule", this.accountingRule.getLiteral());
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasCharges) {
            object.getObject().put("charges", this.charges);
        }

        if (this.hasDigitsAfterDecimal) {
            object.getObject().put("digitsAfterDecimal", this.digitsAfterDecimal);
        }

        if (this.hasHoldTax) {
            object.getObject().put("withHoldTax", this.holdTax);
        }

        if (this.hasCurrencyCode) {
            object.getObject().put("currencyCode", this.currencyCode);
        }

        if (this.hasTaxGroupId) {
            object.getObject().put("taxGroupId", this.taxGroupId);
        }

        if (this.hasInMultiplesOf) {
            object.getObject().put("inMultiplesOf", this.inMultiplesOf);
        }

        if (this.hasName) {
            object.getObject().put("name", this.name);
        }

        if (this.hasShortName) {
            object.getObject().put("shortName", shortName);
        }

        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }

        if (this.hasDepositAmount) {
            object.getObject().put("depositAmount", this.depositAmount);
        }

        if (this.hasPaymentChannelToFundSourceMappings) {
            object.getObject().put("paymentChannelToFundSourceMappings", this.paymentChannelToFundSourceMappings);
        }

        if (this.hasFeeToIncomeAccountMappings) {
            object.getObject().put("feeToIncomeAccountMappings", this.feeToIncomeAccountMappings);
        }

        if (this.hasPenaltyToIncomeAccountMappings) {
            object.getObject().put("penaltyToIncomeAccountMappings", this.penaltyToIncomeAccountMappings);
        }

        return object;
    }

    public static class IncentiveBuilder implements Serializable {

        private String entityType;
        private boolean hasEntityType;

        public IncentiveBuilder withEntityType(String entityType) {
            this.entityType = entityType;
            this.hasEntityType = true;
            return this;
        }

        private Attribute attributeName;
        private boolean hasAttributeName;

        public IncentiveBuilder withAttributeName(Attribute attributeName) {
            this.attributeName = attributeName;
            this.hasAttributeName = true;
            return this;
        }

        private Operator conditionType;
        private boolean hasConditionType;

        public IncentiveBuilder withConditionType(Operator conditionType) {
            this.conditionType = conditionType;
            this.hasConditionType = true;
            return this;
        }

        private String attributeValue;
        private boolean hasAttributeValue;

        public IncentiveBuilder withAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
            this.hasAttributeValue = true;
            return this;
        }

        private OperandType incentiveType;
        private boolean hasIncentiveType;

        public IncentiveBuilder withIncentiveType(OperandType incentiveType) {
            this.incentiveType = incentiveType;
            this.hasIncentiveType = true;
            return this;
        }

        private Double amount;
        private boolean hasAmount;

        public IncentiveBuilder withAmount(Double amount) {
            this.amount = amount;
            this.hasAmount = true;
            return this;
        }

        public JsonNode build() {
            JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

            if (this.hasAmount) {
                object.getObject().put("amount", this.amount);
            }

            if (this.hasIncentiveType) {
                if (this.incentiveType != null) {
                    object.getObject().put("incentiveType", this.incentiveType.getLiteral());
                } else {
                    object.getObject().put("incentiveType", (String) null);
                }
            }

            if (this.hasAttributeValue) {
                object.getObject().put("attributeValue", this.attributeValue);
            }

            if (this.hasConditionType) {
                if (this.conditionType != null) {
                    object.getObject().put("conditionType", this.conditionType.getLiteral());
                } else {
                    object.getObject().put("conditionType", (String) null);
                }
            }

            if (this.hasAttributeName) {
                if (this.attributeName != null) {
                    object.getObject().put("attributeName", this.attributeName.getLiteral());
                } else {
                    object.getObject().put("attributeName", (String) null);
                }
            }

            if (this.hasEntityType) {
                object.getObject().put("entityType", this.entityType);
            }
            return object;
        }

    }

}
