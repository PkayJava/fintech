package com.angkorteam.fintech.pages.product.recurring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.json.JSONObject;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ProductRecurringDepositBuilder;
import com.angkorteam.fintech.dto.builder.ProductRecurringDepositBuilder.IncentiveBuilder;
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
import com.angkorteam.fintech.helper.RecurringHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.product.recurring.Accounting;
import com.angkorteam.fintech.widget.product.recurring.Charges;
import com.angkorteam.fintech.widget.product.recurring.Currency;
import com.angkorteam.fintech.widget.product.recurring.Details;
import com.angkorteam.fintech.widget.product.recurring.InterestRateChart;
import com.angkorteam.fintech.widget.product.recurring.Preview;
import com.angkorteam.fintech.widget.product.recurring.Settings;
import com.angkorteam.fintech.widget.product.recurring.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RecurringCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_CURRENCY = 1;
    public static int TAB_TERM = 2;
    public static int TAB_SETTING = 3;
    public static int TAB_INTEREST_RATE_CHART = 4;
    public static int TAB_CHARGE = 5;
    public static int TAB_ACCOUNTING = 6;
    public static int TAB_PREVIEW = 7;

    // Detail

    protected String detailProductNameValue;
    protected String detailShortNameValue;
    protected String detailDescriptionValue;

    // Currency

    protected Option currencyCodeValue;
    protected Long currencyDecimalPlaceValue;
    protected Long currencyMultipleOfValue;

    // Terms

    protected Double termDefaultDepositAmountValue;
    protected Double termMinimumDepositAmountValue;
    protected Double termMaximumDepositAmountValue;
    protected Option termInterestCompoundingPeriodValue;
    protected Option termInterestPostingPeriodValue;
    protected Option termInterestCalculatedUsingValue;
    protected Option termDayInYearValue;

    // Setting

    protected Boolean settingMandatoryDepositValue;
    protected Boolean settingAdjustAdvancePaymentValue;
    protected Boolean settingAllowWithdrawalValue;
    protected Long settingLockInPeriodValue;
    protected Option settingLockInTypeValue;
    protected Long settingMinimumDepositTermValue;
    protected Option settingMinimumDepositTypeValue;
    protected Long settingInMultiplesOfValue;
    protected Option settingInMultiplesTypeValue;
    protected Long settingMaximumDepositTermValue;
    protected Option settingMaximumDepositTypeValue;
    protected Boolean settingForPreMatureClosureValue;
    protected Double settingApplyPenalInterestValue;
    protected Option settingApplyPenalOnValue;
    protected Double settingBalanceRequiredForInterestCalculationValue;
    protected Boolean settingWithholdTaxApplicableValue;
    protected Option settingTaxGroupValue;

    // Interest Rate Chart

    protected Date interestRateValidFromDateValue;
    protected Date interestRateValidEndDateValue;
    protected Boolean interestRatePrimaryGroupingByAmountValue;
    protected List<Map<String, Object>> interestRateChartValue;

    // Charges

    protected List<Map<String, Object>> chargeValue;

    // Accounting

    protected String accountingValue;

    protected Option cashSavingReferenceValue;
    protected Option cashSavingControlValue;
    protected Option cashSavingTransferInSuspenseValue;
    protected Option cashInterestOnSavingValue;
    protected Option cashIncomeFromFeeValue;
    protected Option cashIncomeFromPenaltyValue;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue;
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail;
    protected boolean errorCurrency;
    protected boolean errorAccounting;
    protected boolean errorTerm;
    protected boolean errorSetting;
    protected boolean errorCharge;
    protected boolean errorInterestRateChart;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Recurring Deposit Product");
            breadcrumb.setPage(RecurringBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Recurring Deposit Product Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new InterestRateChart(this), new Charges(this), new Accounting(this), new Preview(this)));
        add(this.tab);
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.errorDetail = true;
        this.errorCurrency = true;
        this.errorAccounting = true;
        this.errorTerm = true;
        this.errorSetting = true;
        this.errorCharge = true;
        this.errorInterestRateChart = true;

        this.accountingValue = AccountingType.None.getDescription();

        this.chargeValue = new ArrayList<>();
        this.advancedAccountingRuleFundSourceValue = new ArrayList<>();
        this.advancedAccountingRuleFeeIncomeValue = new ArrayList<>();
        this.advancedAccountingRulePenaltyIncomeValue = new ArrayList<>();

        this.interestRateChartValue = new ArrayList<>();
        this.interestRatePrimaryGroupingByAmountValue = false;

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2l;
        this.currencyMultipleOfValue = 1l;

        this.termDefaultDepositAmountValue = 100d;
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Daily.toOption();
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.DailyBalance.toOption();
        this.termInterestPostingPeriodValue = InterestPostingPeriod.Monthly.toOption();
        this.termDayInYearValue = DayInYear.D365.toOption();
        this.settingMinimumDepositTermValue = 1l;
        this.settingMinimumDepositTypeValue = LockInType.Month.toOption();
        this.accountingValue = AccountingType.None.getDescription();

    }

    public void saveButtonSubmit(Button button) {
        ProductRecurringDepositBuilder builder = new ProductRecurringDepositBuilder();

        // Detail
        builder.withName(this.detailProductNameValue);
        builder.withShortName(this.detailShortNameValue);
        builder.withDescription(this.detailDescriptionValue);

        // Currency
        if (this.currencyCodeValue != null) {
            builder.withCurrencyCode(this.currencyCodeValue.getId());
        }
        builder.withDigitsAfterDecimal(this.currencyDecimalPlaceValue);
        builder.withInMultiplesOf(this.currencyMultipleOfValue);

        // Term

        builder.withDepositAmount(this.termDefaultDepositAmountValue);
        builder.withMinDepositAmount(this.termMinimumDepositAmountValue);
        builder.withMaxDepositAmount(this.termMaximumDepositAmountValue);

        if (this.termInterestCompoundingPeriodValue != null) {
            builder.withInterestCompoundingPeriodType(InterestCompoundingPeriod.valueOf(this.termInterestCompoundingPeriodValue.getId()));
        }

        if (this.termInterestPostingPeriodValue != null) {
            builder.withInterestPostingPeriodType(InterestPostingPeriod.valueOf(this.termInterestPostingPeriodValue.getId()));
        }

        if (this.termInterestCalculatedUsingValue != null) {
            builder.withInterestCalculationType(InterestCalculatedUsing.valueOf(this.termInterestCalculatedUsingValue.getId()));
        }

        if (this.termDayInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.termDayInYearValue.getId()));
        }

        // Setting

        builder.withMandatoryDeposit(this.settingMandatoryDepositValue == null ? false : this.settingMandatoryDepositValue);

        builder.withAdjustAdvanceTowardsFuturePayments(this.settingAdjustAdvancePaymentValue == null ? false : this.settingAdjustAdvancePaymentValue);

        builder.withAllowWithdrawal(this.settingAllowWithdrawalValue == null ? false : this.settingAllowWithdrawalValue);

        builder.withLockInPeriodFrequency(this.settingLockInPeriodValue);
        if (this.settingLockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.settingLockInTypeValue.getId()));
        }

        builder.withMinDepositTerm(this.settingMinimumDepositTermValue);

        if (this.settingMinimumDepositTypeValue != null) {
            builder.withMinDepositTermTypeId(LockInType.valueOf(this.settingMinimumDepositTypeValue.getId()));
        }

        builder.withInMultiplesOfDepositTerm(this.settingInMultiplesOfValue);

        if (this.settingInMultiplesTypeValue != null) {
            builder.withInMultiplesOfDepositTermTypeId(LockInType.valueOf(this.settingInMultiplesTypeValue.getId()));
        }

        builder.withMaxDepositTerm(this.settingMaximumDepositTermValue);

        if (this.settingMaximumDepositTypeValue != null) {
            builder.withMaxDepositTermTypeId(LockInType.valueOf(this.settingMaximumDepositTypeValue.getId()));
        }

        builder.withPreClosurePenalApplicable(this.settingForPreMatureClosureValue == null ? false : this.settingForPreMatureClosureValue);

        builder.withPreClosurePenalInterest(this.settingApplyPenalInterestValue);

        if (this.settingApplyPenalOnValue != null) {
            builder.withPreClosurePenalInterestOnTypeId(ApplyPenalOn.valueOf(this.settingApplyPenalOnValue.getId()));
        }

        builder.withMinBalanceForInterestCalculation(this.settingBalanceRequiredForInterestCalculationValue);

        boolean holdTax = this.settingWithholdTaxApplicableValue == null ? false : this.settingWithholdTaxApplicableValue;
        builder.withHoldTax(holdTax);
        if (holdTax) {
            if (this.settingTaxGroupValue != null) {
                builder.withTaxGroupId(this.settingTaxGroupValue.getId());
            }
        }

        // Interest Rate Chart

        if (!this.interestRateChartValue.isEmpty() || (this.interestRatePrimaryGroupingByAmountValue != null && this.interestRatePrimaryGroupingByAmountValue) || this.interestRateValidFromDateValue != null || this.interestRateValidEndDateValue != null) {
            builder.withFromDate(this.interestRateValidFromDateValue);
            builder.withEndDate(this.interestRateValidEndDateValue);

            builder.withPrimaryGroupingByAmount(this.interestRatePrimaryGroupingByAmountValue == null ? false : this.interestRatePrimaryGroupingByAmountValue);

            for (Map<String, Object> interestRateChart : this.interestRateChartValue) {
                Option periodTypeOption = (Option) interestRateChart.get("periodType");
                LockInType periodType = periodTypeOption == null ? null : LockInType.valueOf(periodTypeOption.getId());
                Long fromPeriod = (Long) interestRateChart.get("periodFrom");
                Long toPeriod = (Long) interestRateChart.get("periodTo");
                Long amountRangeFrom = (Long) interestRateChart.get("amountRangeFrom");
                Long amountRangeTo = (Long) interestRateChart.get("amountRangeTo");
                Double annualInterestRate = (Double) interestRateChart.get("interest");
                String description = (String) interestRateChart.get("description");
                List<Map<String, Object>> interestRate = (List<Map<String, Object>>) interestRateChart.get("interestRate");
                List<JSONObject> incentives = null;
                if (interestRate != null && !interestRate.isEmpty()) {
                    incentives = Lists.newLinkedList();
                    for (Map<String, Object> rate : interestRate) {

                        IncentiveBuilder incentiveBuilder = new IncentiveBuilder();

                        Option attributeOption = (Option) rate.get("attribute");
                        Attribute attribute = attributeOption == null ? null : Attribute.valueOf(attributeOption.getId());
                        incentiveBuilder.withAttributeName(attribute);

                        Option operatorOption = (Option) rate.get("operator");
                        Operator operator = operatorOption == null ? null : Operator.valueOf(operatorOption.getId());
                        incentiveBuilder.withConditionType(operator);

                        if (attributeOption != null) {
                            if (attributeOption.getId().equals(Attribute.ClientType.name())) {
                                Option operand = (Option) rate.get("clientTypeOperand");
                                incentiveBuilder.withAttributeValue(operand.getId());
                            } else if (attributeOption.getId().equals(Attribute.ClientClassification.name())) {
                                Option operand = (Option) rate.get("clientClassificationOperand");
                                incentiveBuilder.withAttributeValue(operand.getId());
                            } else {
                                if (rate.get("numberOperand") instanceof String) {
                                    String operand = (String) rate.get("numberOperand");
                                    incentiveBuilder.withAttributeValue(operand);
                                } else {
                                    Long operand = (Long) rate.get("numberOperand");
                                    incentiveBuilder.withAttributeValue(String.valueOf(operand));
                                }
                            }
                        }

                        Option operandTypeOption = (Option) rate.get("operandType");
                        OperandType operandType = operandTypeOption == null ? null : OperandType.valueOf(operandTypeOption.getId());
                        incentiveBuilder.withIncentiveType(operandType);

                        Double rateInterest = (Double) rate.get("interest");
                        incentiveBuilder.withAmount(rateInterest);

                        incentives.add(incentiveBuilder.build().getObject());
                    }
                }
                builder.withChartSlab(periodType, fromPeriod, toPeriod, amountRangeFrom, amountRangeTo, annualInterestRate, description, incentives);
            }
        }

        // Charge

        if (this.chargeValue != null && !this.chargeValue.isEmpty()) {
            for (Map<String, Object> item : this.chargeValue) {
                Option charge = (Option) item.get("charge");
                builder.withCharges(charge.getId());
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (AccountingType.None.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.None);
        } else if (AccountingType.Cash.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Cash);
        }

        if (AccountingType.Cash.getDescription().equals(accounting)) {
            if (this.cashSavingReferenceValue != null) {
                builder.withSavingsReferenceAccountId(this.cashSavingReferenceValue.getId());
            }
            if (this.cashSavingControlValue != null) {
                builder.withSavingsControlAccountId(this.cashSavingControlValue.getId());
            }
            if (this.cashSavingTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashSavingTransferInSuspenseValue.getId());
            }
            if (this.cashInterestOnSavingValue != null) {
                builder.withInterestOnSavingsAccountId(this.cashInterestOnSavingValue.getId());
            }
            if (this.cashIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
            }
            if (this.cashIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
            }
        }

        if (AccountingType.Cash.getDescription().equals(accounting)) {
            if (this.advancedAccountingRuleFundSourceValue != null && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
                    Option payment = (Option) item.get("payment");
                    Option account = (Option) item.get("account");
                    builder.withPaymentChannelToFundSourceMappings(payment.getId(), account.getId());
                }
            }
            if (this.advancedAccountingRuleFeeIncomeValue != null && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
                    Option charge = (Option) item.get("charge");
                    Option account = (Option) item.get("account");
                    builder.withFeeToIncomeAccountMappings(charge.getId(), account.getId());
                }
            }
            if (this.advancedAccountingRulePenaltyIncomeValue != null && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
                    Option charge = (Option) item.get("charge");
                    Option account = (Option) item.get("account");
                    builder.withPenaltyToIncomeAccountMappings(charge.getId(), account.getId());
                }
            }
        }

        JsonNode node = RecurringHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(RecurringBrowsePage.class);
    }

}
