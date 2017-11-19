package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

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

public class ProductRecurringDepositBuilder implements Serializable {

    private List<Map<String, Object>> paymentChannelToFundSourceMappings = Lists.newArrayList();
    private boolean hasPaymentChannelToFundSourceMappings;

    public ProductRecurringDepositBuilder withPaymentChannelToFundSourceMappings(String paymentTypeId, String fundSourceAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("paymentTypeId", paymentTypeId);
        item.put("fundSourceAccountId", fundSourceAccountId);
        this.paymentChannelToFundSourceMappings.add(item);
        this.hasPaymentChannelToFundSourceMappings = true;
        return this;
    }

    private List<Map<String, Object>> feeToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasFeeToIncomeAccountMappings;

    public ProductRecurringDepositBuilder withFeeToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.feeToIncomeAccountMappings.add(item);
        this.hasFeeToIncomeAccountMappings = true;
        return this;
    }

    private List<Map<String, Object>> penaltyToIncomeAccountMappings = Lists.newArrayList();
    private boolean hasPenaltyToIncomeAccountMappings;

    public ProductRecurringDepositBuilder withPenaltyToIncomeAccountMappings(String chargeId, String incomeAccountId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("chargeId", chargeId);
        item.put("incomeAccountId", incomeAccountId);
        this.penaltyToIncomeAccountMappings.add(item);
        this.hasPenaltyToIncomeAccountMappings = true;
        return this;
    }

    private Double depositAmount;
    private boolean hasDepositAmount;

    public ProductRecurringDepositBuilder withDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
        this.hasDepositAmount = true;
        return this;
    }

    private Double minDepositAmount;
    private boolean hasMinDepositAmount;

    public ProductRecurringDepositBuilder withMinDepositAmount(Double minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
        this.hasMinDepositAmount = true;
        return this;
    }

    private Double maxDepositAmount;
    private boolean hasMaxDepositAmount;

    public ProductRecurringDepositBuilder withMaxDepositAmount(Double maxDepositAmount) {
        this.maxDepositAmount = maxDepositAmount;
        this.hasMaxDepositAmount = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public ProductRecurringDepositBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    private String shortName;
    private boolean hasShortName;

    public ProductRecurringDepositBuilder withShortName(String shortName) {
        this.shortName = shortName;
        this.hasShortName = true;
        return this;
    }

    private String name;
    private boolean hasName;

    public ProductRecurringDepositBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private Integer inMultiplesOf;
    private boolean hasInMultiplesOf;

    public ProductRecurringDepositBuilder withInMultiplesOf(Integer inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
        this.hasInMultiplesOf = true;
        return this;
    }

    private String taxGroupId;
    private boolean hasTaxGroupId;

    public ProductRecurringDepositBuilder withTaxGroupId(String taxGroupId) {
        this.taxGroupId = taxGroupId;
        this.hasTaxGroupId = true;
        return this;
    }

    private Boolean holdTax;
    private boolean hasHoldTax;

    public ProductRecurringDepositBuilder withHoldTax(Boolean holdTax) {
        this.holdTax = holdTax;
        this.hasHoldTax = true;
        return this;
    }

    private String currencyCode;
    private boolean hasCurrencyCode;

    public ProductRecurringDepositBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        this.hasCurrencyCode = true;
        return this;
    }

    private Integer digitsAfterDecimal;
    private boolean hasDigitsAfterDecimal;

    public ProductRecurringDepositBuilder withDigitsAfterDecimal(Integer digitsAfterDecimal) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.hasDigitsAfterDecimal = true;
        return this;
    }

    private List<Map<String, Object>> charges = Lists.newArrayList();
    private boolean hasCharges;

    public ProductRecurringDepositBuilder withCharges(String chargeId) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("id", chargeId);
        this.charges.add(item);
        this.hasCharges = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ProductRecurringDepositBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private Integer accountingRule;
    private boolean hasAccountingRule;

    public ProductRecurringDepositBuilder withAccountingRule(Integer accountingRule) {
        this.accountingRule = accountingRule;
        this.hasAccountingRule = true;
        return this;
    }

    private InterestCompoundingPeriod interestCompoundingPeriodType;
    private boolean hasInterestCompoundingPeriodType;

    public ProductRecurringDepositBuilder withInterestCompoundingPeriodType(InterestCompoundingPeriod interestCompoundingPeriodType) {
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.hasInterestCompoundingPeriodType = true;
        return this;
    }

    private InterestPostingPeriod interestPostingPeriodType;
    private boolean hasInterestPostingPeriodType;

    public ProductRecurringDepositBuilder withInterestPostingPeriodType(InterestPostingPeriod interestPostingPeriodType) {
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.hasInterestPostingPeriodType = true;
        return this;
    }

    private InterestCalculatedUsing interestCalculationType;
    private boolean hasInterestCalculationType;

    public ProductRecurringDepositBuilder withInterestCalculationType(InterestCalculatedUsing interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
        this.hasInterestCalculationType = true;
        return this;
    }

    private DayInYear interestCalculationDaysInYearType;
    private boolean hasInterestCalculationDaysInYearType;

    public ProductRecurringDepositBuilder withInterestCalculationDaysInYearType(DayInYear interestCalculationDaysInYearType) {
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.hasInterestCalculationDaysInYearType = true;
        return this;
    }

    private ApplyPenalOn preClosurePenalInterestOnTypeId;
    private boolean hasPreClosurePenalInterestOnTypeId;

    public ProductRecurringDepositBuilder withPreClosurePenalInterestOnTypeId(ApplyPenalOn preClosurePenalInterestOnTypeId) {
        this.preClosurePenalInterestOnTypeId = preClosurePenalInterestOnTypeId;
        this.hasPreClosurePenalInterestOnTypeId = true;
        return this;
    }

    private String savingsReferenceAccountId;
    private boolean hasSavingsReferenceAccountId;

    public ProductRecurringDepositBuilder withSavingsReferenceAccountId(String savingsReferenceAccountId) {
        this.savingsReferenceAccountId = savingsReferenceAccountId;
        this.hasSavingsReferenceAccountId = true;
        return this;
    }

    private String savingsControlAccountId;
    private boolean hasSavingsControlAccountId;

    public ProductRecurringDepositBuilder withSavingsControlAccountId(String savingsControlAccountId) {
        this.savingsControlAccountId = savingsControlAccountId;
        this.hasSavingsControlAccountId = true;
        return this;
    }

    private String interestOnSavingsAccountId;
    private boolean hasInterestOnSavingsAccountId;

    public ProductRecurringDepositBuilder withInterestOnSavingsAccountId(String interestOnSavingsAccountId) {
        this.interestOnSavingsAccountId = interestOnSavingsAccountId;
        this.hasInterestOnSavingsAccountId = true;
        return this;
    }

    private String incomeFromFeeAccountId;
    private boolean hasIncomeFromFeeAccountId;

    public ProductRecurringDepositBuilder withIncomeFromFeeAccountId(String incomeFromFeeAccountId) {
        this.incomeFromFeeAccountId = incomeFromFeeAccountId;
        this.hasIncomeFromFeeAccountId = true;
        return this;
    }

    private String incomeFromPenaltyAccountId;
    private boolean hasIncomeFromPenaltyAccountId;

    public ProductRecurringDepositBuilder withIncomeFromPenaltyAccountId(String incomeFromPenaltyAccountId) {
        this.incomeFromPenaltyAccountId = incomeFromPenaltyAccountId;
        this.hasIncomeFromPenaltyAccountId = true;
        return this;
    }

    private String transfersInSuspenseAccountId;
    private boolean hasTransfersInSuspenseAccountId;

    public ProductRecurringDepositBuilder withTransfersInSuspenseAccountId(String transfersInSuspenseAccountId) {
        this.transfersInSuspenseAccountId = transfersInSuspenseAccountId;
        this.hasTransfersInSuspenseAccountId = true;
        return this;
    }

    private List<Map<String, Object>> charts = Lists.newArrayList();
    private boolean hasCharts;

    private Date fromDate;
    private boolean hasFromDate;

    public ProductRecurringDepositBuilder withFromDate(Date fromDate) {
        this.fromDate = fromDate;
        this.hasFromDate = true;
        this.hasCharts = true;
        return this;
    }

    private Date endDate;
    private boolean hasEndDate;

    public ProductRecurringDepositBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        this.hasEndDate = true;
        this.hasCharts = true;
        return this;
    }

    private Boolean primaryGroupingByAmount;
    private boolean hasPrimaryGroupingByAmount;

    public ProductRecurringDepositBuilder withPrimaryGroupingByAmount(Boolean primaryGroupingByAmount) {
        this.primaryGroupingByAmount = primaryGroupingByAmount;
        this.hasPrimaryGroupingByAmount = true;
        this.hasCharts = true;
        return this;

    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public ProductRecurringDepositBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private List<Map<String, Object>> chartSlabs = Lists.newArrayList();
    private boolean hasChartSlabs;

    public ProductRecurringDepositBuilder withChartSlab(LockInType periodType, Integer fromPeriod, Integer toPeriod, Integer amountRangeFrom, Integer amountRangeTo, Double annualInterestRate, String description, List<JSONObject> incentives) {
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

    public ProductRecurringDepositBuilder withLockInPeriodFrequency(Integer lockInPeriodFrequency) {
        this.lockInPeriodFrequency = lockInPeriodFrequency;
        this.hasLockInPeriodFrequency = true;
        return this;
    }

    private LockInType lockInPeriodFrequencyType;
    private boolean hasLockInPeriodFrequencyType;

    public ProductRecurringDepositBuilder withLockinPeriodFrequencyType(LockInType lockInPeriodFrequencyType) {
        this.lockInPeriodFrequencyType = lockInPeriodFrequencyType;
        this.hasLockInPeriodFrequencyType = true;
        return this;
    }

    private Integer minDepositTerm;
    private boolean hasMinDepositTerm;

    public ProductRecurringDepositBuilder withMinDepositTerm(Integer minDepositTerm) {
        this.minDepositTerm = minDepositTerm;
        this.hasMinDepositTerm = true;
        return this;
    }

    private LockInType minDepositTermTypeId;
    private boolean hasMinDepositTermTypeId;

    public ProductRecurringDepositBuilder withMinDepositTermTypeId(LockInType minDepositTermTypeId) {
        this.minDepositTermTypeId = minDepositTermTypeId;
        this.hasMinDepositTermTypeId = true;
        return this;
    }

    private Integer inMultiplesOfDepositTerm;
    private boolean hasInMultiplesOfDepositTerm;

    public ProductRecurringDepositBuilder withInMultiplesOfDepositTerm(Integer inMultiplesOfDepositTerm) {
        this.inMultiplesOfDepositTerm = inMultiplesOfDepositTerm;
        this.hasInMultiplesOfDepositTerm = true;
        return this;
    }

    private LockInType inMultiplesOfDepositTermTypeId;
    private boolean hasInMultiplesOfDepositTermTypeId;

    public ProductRecurringDepositBuilder withInMultiplesOfDepositTermTypeId(LockInType inMultiplesOfDepositTermTypeId) {
        this.inMultiplesOfDepositTermTypeId = inMultiplesOfDepositTermTypeId;
        this.hasInMultiplesOfDepositTermTypeId = true;
        return this;
    }

    private Integer maxDepositTerm;
    private boolean hasMaxDepositTerm;

    public ProductRecurringDepositBuilder withMaxDepositTerm(Integer maxDepositTerm) {
        this.maxDepositTerm = maxDepositTerm;
        this.hasMaxDepositTerm = true;
        return this;
    }

    private LockInType maxDepositTermTypeId;
    private boolean hasMaxDepositTermTypeId;

    public ProductRecurringDepositBuilder withMaxDepositTermTypeId(LockInType maxDepositTermTypeId) {
        this.maxDepositTermTypeId = maxDepositTermTypeId;
        this.hasMaxDepositTermTypeId = true;
        return this;
    }

    private Boolean preClosurePenalApplicable;
    private boolean hasPreClosurePenalApplicable;

    public ProductRecurringDepositBuilder withPreClosurePenalApplicable(Boolean preClosurePenalApplicable) {
        this.preClosurePenalApplicable = preClosurePenalApplicable;
        this.hasPreClosurePenalApplicable = true;
        return this;
    }

    private Double preClosurePenalInterest;
    private boolean hasPreClosurePenalInterest;

    public ProductRecurringDepositBuilder withPreClosurePenalInterest(Double preClosurePenalInterest) {
        this.preClosurePenalInterest = preClosurePenalInterest;
        this.hasPreClosurePenalInterest = true;
        return this;
    }

    private Double minBalanceForInterestCalculation;
    private boolean hasMinBalanceForInterestCalculation;

    public ProductRecurringDepositBuilder withMinBalanceForInterestCalculation(Double minBalanceForInterestCalculation) {
        this.minBalanceForInterestCalculation = minBalanceForInterestCalculation;
        this.hasMinBalanceForInterestCalculation = true;
        return this;
    }

    private Boolean mandatoryDeposit;
    private boolean hasMandatoryDeposit;

    public ProductRecurringDepositBuilder withMandatoryDeposit(Boolean mandatoryDeposit) {
        this.mandatoryDeposit = mandatoryDeposit;
        this.hasMandatoryDeposit = true;
        return this;
    }

    private Boolean adjustAdvanceTowardsFuturePayments;
    private boolean hasAdjustAdvanceTowardsFuturePayments;

    public ProductRecurringDepositBuilder withAdjustAdvanceTowardsFuturePayments(Boolean adjustAdvanceTowardsFuturePayments) {
        this.adjustAdvanceTowardsFuturePayments = adjustAdvanceTowardsFuturePayments;
        this.hasAdjustAdvanceTowardsFuturePayments = true;
        return this;
    }

    private Boolean allowWithdrawal;
    private boolean hasAllowWithdrawal;

    public ProductRecurringDepositBuilder withAllowWithdrawal(Boolean allowWithdrawal) {
        this.allowWithdrawal = allowWithdrawal;
        this.hasAllowWithdrawal = true;
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

        // if (this.recurringDepositFrequency == null) {
        // errors.add("recurringDepositFrequency is required");
        // }

        // if (this.recurringDepositFrequencyTypeId == null) {
        // errors.add("recurringDepositFrequencyTypeId is required");
        // }

        if (this.accountingRule == null) {
            errors.add("accountingRule is required");
        } else {
            if (this.accountingRule == 2) {
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

        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasAllowWithdrawal) {
            object.getObject().put("allowWithdrawal", this.allowWithdrawal);
        }

        if (this.hasAdjustAdvanceTowardsFuturePayments) {
            object.getObject().put("adjustAdvanceTowardsFuturePayments", this.adjustAdvanceTowardsFuturePayments);
        }

        if (this.hasMandatoryDeposit) {
            object.getObject().put("isMandatoryDeposit", this.mandatoryDeposit);
        }

        if (this.hasMinBalanceForInterestCalculation) {
            object.getObject().put("minBalanceForInterestCalculation", this.minBalanceForInterestCalculation);
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
                chart.put("isPrimaryGroupingByAmount", this.primaryGroupingByAmount);
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
