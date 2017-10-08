package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.angkorteam.fintech.dto.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.InterestPostingPeriod;
import com.angkorteam.fintech.dto.ApplyPenalOn;
import com.angkorteam.fintech.dto.Attribute;
import com.angkorteam.fintech.dto.DayInYear;
import com.angkorteam.fintech.dto.LockInType;
import com.angkorteam.fintech.dto.OperandType;
import com.angkorteam.fintech.dto.Operator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class FixedBuilder implements Serializable {

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public FixedBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("paymentTypeId", paymentTypeId);
        item.put("fundSourceAccountId", fundSourceAccountId);
        this.paymentChannelToFundSourceMappings.add(item);
        this.hasPaymentChannelToFundSourceMappings = true;
        return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public FixedBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.feeToIncomeAccountMappings.add(item);
        this.hasFeeToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public FixedBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.penaltyToIncomeAccountMappings.add(item);
        this.hasPenaltyToIncomeAccountMappings = true;
        return this;
    }

    private Double depositAmount;
    private boolean hasDepositAmount;

    public FixedBuilder withDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
        this.hasDepositAmount = true;
        return this;
    }

    private Double minDepositAmount;
    private boolean hasMinDepositAmount;

    public FixedBuilder withMinDepositAmount(Double minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
        this.hasMinDepositAmount = true;
        return this;
    }

    private Double maxDepositAmount;
    private boolean hasMaxDepositAmount;

    public FixedBuilder withMaxDepositAmount(Double maxDepositAmount) {
        this.maxDepositAmount = maxDepositAmount;
        this.hasMaxDepositAmount = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public FixedBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public FixedBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String name;
    private boolean hasName;

    public FixedBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private Integer inMultiplesOf;
    private boolean hasInMultiplesOf;

    public FixedBuilder withInMultiplesOf(Integer inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private String taxGroupId;
    private boolean hasTaxGroupId;

    public FixedBuilder withTaxGroupId(String taxGroupId) {
        this.taxGroupId = taxGroupId;
        this.hasTaxGroupId = true;
        return this;
    }

    private boolean holdTax;
    private boolean hasHoldTax;

    public FixedBuilder withHoldTax(boolean holdTax) {
        this.holdTax = holdTax;
        this.hasHoldTax = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public FixedBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Integer digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public FixedBuilder withDigitsAfterDecimal(Integer digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public FixedBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public FixedBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private Integer accountingRule;
    private boolean hasAccountingRule;

    public FixedBuilder withAccountingRule(Integer accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private InterestCompoundingPeriod interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    public FixedBuilder withInterestCompoundingPeriodType(InterestCompoundingPeriod interestCompoundingPeriodType) {
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.hasInterestCompoundingPeriodType = true;
        return this;
    }

    private InterestPostingPeriod interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    public FixedBuilder withInterestPostingPeriodType(InterestPostingPeriod interestPostingPeriodType) {
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.hasInterestPostingPeriodType = true;
        return this;
    }

    private InterestCalculatedUsing interestCalculationType;
    private boolean hasInterestCalculationType;

    public FixedBuilder withInterestCalculationType(InterestCalculatedUsing interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
        this.hasInterestCalculationType = true;
        return this;
    }

    private DayInYear interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    public FixedBuilder withInterestCalculationDaysInYearType(DayInYear interestCalculationDaysInYearType) {
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.hasInterestCalculationDaysInYearType = true;
        return this;
    }

    private ApplyPenalOn preClosurePenalInterestOnTypeId;
    private boolean hasPreClosurePenalInterestOnTypeId;

    public FixedBuilder withPreClosurePenalInterestOnTypeId(ApplyPenalOn preClosurePenalInterestOnTypeId) {
        this.preClosurePenalInterestOnTypeId = preClosurePenalInterestOnTypeId;
        this.hasPreClosurePenalInterestOnTypeId = true;
        return this;
    }

    private String savingsReferenceAccountId;
    private boolean hasSavingsReferenceAccountId;

    public FixedBuilder withSavingsReferenceAccountId(String savingsReferenceAccountId) {
        this.savingsReferenceAccountId = savingsReferenceAccountId;
        this.hasSavingsReferenceAccountId = true;
        return this;
    }

    private String savingsControlAccountId;
    private boolean hasSavingsControlAccountId;

    public FixedBuilder withSavingsControlAccountId(String savingsControlAccountId) {
        this.savingsControlAccountId = savingsControlAccountId;
        this.hasSavingsControlAccountId = true;
        return this;
    }

    private String interestOnSavingsAccountId;
    private boolean hasInterestOnSavingsAccountId;

    public FixedBuilder withInterestOnSavingsAccountId(String interestOnSavingsAccountId) {
        this.interestOnSavingsAccountId = interestOnSavingsAccountId;
        this.hasInterestOnSavingsAccountId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public FixedBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    private String incomeFromPenaltyAccountId;
    private boolean hasIncomeFromPenaltyAccountId;

    public FixedBuilder withIncomeFromPenaltyAccountId(String incomeFromPenaltyAccountId) {
        this.incomeFromPenaltyAccountId = incomeFromPenaltyAccountId;
        this.hasIncomeFromPenaltyAccountId = true;
        return this;
    }

    private String transfersInSuspenseAccountId;
    private boolean hasTransfersInSuspenseAccountId;

    public FixedBuilder withTransfersInSuspenseAccountId(String transfersInSuspenseAccountId) {
        this.transfersInSuspenseAccountId = transfersInSuspenseAccountId;
        this.hasTransfersInSuspenseAccountId = true;
        return this;
    }

    private boolean hasCharts;
    private List<Map<String, Object>> charts = Lists.newArrayList();

    private Date fromDate;
    private boolean hasFromDate;

    public FixedBuilder withFromDate(Date fromDate) {
        this.fromDate = fromDate;
        this.hasFromDate = true;
        this.hasCharts = true;
        return this;
    }

    private Date endDate;
    private boolean hasEndDate;

    public FixedBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        this.hasEndDate = true;
        this.hasCharts = true;
        return this;
    }

    private boolean isPrimaryGroupingByAmount;
    private boolean hasPrimaryGroupingByAmount;

    public FixedBuilder withPrimaryGroupingByAmount(boolean isPrimaryGroupingByAmount) {
        this.isPrimaryGroupingByAmount = isPrimaryGroupingByAmount;
        this.hasPrimaryGroupingByAmount = true;
        this.hasCharts = true;
        return this;

    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public FixedBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private List<Map<String, Object>> chartSlabs = Lists.newArrayList();
    private boolean hasChartSlabs;

    public FixedBuilder withChartSlab(LockInType periodType, Integer fromPeriod, Integer toPeriod, Integer amountRangeFrom, Integer amountRangeTo, Double annualInterestRate, String description, List<JSONObject> incentives) {
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

    private Integer lockInPeriodFrequency;
    private boolean hasLockInPeriodFrequency;

    public FixedBuilder withLockInPeriodFrequency(Integer lockInPeriodFrequency) {
        this.lockInPeriodFrequency = lockInPeriodFrequency;
        this.hasLockInPeriodFrequency = true;
        return this;
    }

    private LockInType lockInPeriodFrequencyType;
    private boolean hasLockInPeriodFrequencyType;

    public FixedBuilder withLockinPeriodFrequencyType(LockInType lockInPeriodFrequencyType) {
        this.lockInPeriodFrequencyType = lockInPeriodFrequencyType;
        this.hasLockInPeriodFrequencyType = true;
        return this;
    }

    private Integer minDepositTerm;
    private boolean hasMinDepositTerm;

    public FixedBuilder withMinDepositTerm(Integer minDepositTerm) {
        this.minDepositTerm = minDepositTerm;
        this.hasMinDepositTerm = true;
        return this;
    }

    private LockInType minDepositTermTypeId;
    private boolean hasMinDepositTermTypeId;

    public FixedBuilder withMinDepositTermTypeId(LockInType minDepositTermTypeId) {
        this.minDepositTermTypeId = minDepositTermTypeId;
        this.hasMinDepositTermTypeId = true;
        return this;
    }

    private Integer inMultiplesOfDepositTerm;
    private boolean hasInMultiplesOfDepositTerm;

    public FixedBuilder withInMultiplesOfDepositTerm(Integer inMultiplesOfDepositTerm) {
        this.inMultiplesOfDepositTerm = inMultiplesOfDepositTerm;
        this.hasInMultiplesOfDepositTerm = true;
        return this;
    }

    private LockInType inMultiplesOfDepositTermTypeId;
    private boolean hasInMultiplesOfDepositTermTypeId;

    public FixedBuilder withInMultiplesOfDepositTermTypeId(LockInType inMultiplesOfDepositTermTypeId) {
        this.inMultiplesOfDepositTermTypeId = inMultiplesOfDepositTermTypeId;
        this.hasInMultiplesOfDepositTermTypeId = true;
        return this;
    }

    private Integer maxDepositTerm;
    private boolean hasMaxDepositTerm;

    public FixedBuilder withMaxDepositTerm(Integer maxDepositTerm) {
        this.maxDepositTerm = maxDepositTerm;
        this.hasMaxDepositTerm = true;
        return this;
    }

    private LockInType maxDepositTermTypeId;
    private boolean hasMaxDepositTermTypeId;

    public FixedBuilder withMaxDepositTermTypeId(LockInType maxDepositTermTypeId) {
        this.maxDepositTermTypeId = maxDepositTermTypeId;
        this.hasMaxDepositTermTypeId = true;
        return this;
    }

    private boolean preClosurePenalApplicable;
    private boolean hasPreClosurePenalApplicable;

    public FixedBuilder withPreClosurePenalApplicable(boolean preClosurePenalApplicable) {
        this.preClosurePenalApplicable = preClosurePenalApplicable;
        this.hasPreClosurePenalApplicable = true;
        return this;
    }

    private Double preClosurePenalInterest;
    private boolean hasPreClosurePenalInterest;

    public FixedBuilder withPreClosurePenalInterest(Double preClosurePenalInterest) {
        this.preClosurePenalInterest = preClosurePenalInterest;
        this.hasPreClosurePenalInterest = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

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
            object.getObject().put("accountingRule", this.accountingRule);
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
