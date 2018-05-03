package com.angkorteam.fintech.pages.product.recurring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccProductMapping;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MDepositProductInterestRateChart;
import com.angkorteam.fintech.ddl.MDepositProductRecurringDetail;
import com.angkorteam.fintech.ddl.MDepositProductTermAndPreClosure;
import com.angkorteam.fintech.ddl.MInterestIncentives;
import com.angkorteam.fintech.ddl.MInterestRateChart;
import com.angkorteam.fintech.ddl.MInterestRateSlab;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MSavingsProductCharge;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ApplyPenalOn;
import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.FinancialAccountType;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.OperandType;
import com.angkorteam.fintech.dto.enums.Operator;
import com.angkorteam.fintech.dto.enums.ProductType;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.widget.product.recurring.Accounting;
import com.angkorteam.fintech.widget.product.recurring.Charges;
import com.angkorteam.fintech.widget.product.recurring.Currency;
import com.angkorteam.fintech.widget.product.recurring.Details;
import com.angkorteam.fintech.widget.product.recurring.InterestRateChart;
import com.angkorteam.fintech.widget.product.recurring.Preview;
import com.angkorteam.fintech.widget.product.recurring.Settings;
import com.angkorteam.fintech.widget.product.recurring.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RecurringPreviewPage extends Page {

    protected String recurringId;

    public static int TAB_DETAIL = RecurringCreatePage.TAB_DETAIL;
    public static int TAB_CURRENCY = RecurringCreatePage.TAB_CURRENCY;
    public static int TAB_TERM = RecurringCreatePage.TAB_TERM;
    public static int TAB_SETTING = RecurringCreatePage.TAB_SETTING;
    public static int TAB_INTEREST_RATE_CHART = RecurringCreatePage.TAB_INTEREST_RATE_CHART;
    public static int TAB_CHARGE = RecurringCreatePage.TAB_CHARGE;
    public static int TAB_ACCOUNTING = RecurringCreatePage.TAB_ACCOUNTING;
    public static int TAB_PREVIEW = RecurringCreatePage.TAB_PREVIEW;

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

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue;

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
            breadcrumb.setLabel(this.detailShortNameValue);
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
        this.tab.setSelectedTab(TAB_PREVIEW);
    }

    @Override
    protected void initData() {
        this.recurringId = getPageParameters().get("recurringId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        // selectQuery.addJoin("INNER JOIN " + MOrganisationCurrency.NAME + " ON " +
        // MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_CODE + " = " +
        // MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE);
        selectQuery.addJoin("INNER JOIN " + MDepositProductTermAndPreClosure.NAME + " ON " + MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.SAVINGS_PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        selectQuery.addJoin("INNER JOIN " + MDepositProductRecurringDetail.NAME + " ON " + MDepositProductRecurringDetail.NAME + "." + MDepositProductRecurringDetail.Field.SAVINGS_PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        selectQuery.addWhere(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID + " = '" + this.recurringId + "'");

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.SHORT_NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DESCRIPTION);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_CODE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_AMOUNT);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_AMOUNT);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.DEPOSIT_AMOUNT);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM);

        selectQuery.addField(MDepositProductRecurringDetail.NAME + "." + MDepositProductRecurringDetail.Field.IS_MANDATORY);
        selectQuery.addField(MDepositProductRecurringDetail.NAME + "." + MDepositProductRecurringDetail.Field.ADJUST_ADVANCE_TOWARDS_FUTURE_PAYMENTS);
        selectQuery.addField(MDepositProductRecurringDetail.NAME + "." + MDepositProductRecurringDetail.Field.ALLOW_WITHDRAWAL);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_APPLICABLE);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.WITHHOLD_TAX);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.TAX_GROUP_ID);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ACCOUNTING_TYPE);
        Map<String, Object> recurringObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.detailDescriptionValue = (String) recurringObject.get(MSavingsProduct.Field.DESCRIPTION);
        this.detailProductNameValue = (String) recurringObject.get(MSavingsProduct.Field.NAME);
        this.detailShortNameValue = (String) recurringObject.get(MSavingsProduct.Field.SHORT_NAME);

        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, recurringObject.get(MSavingsProduct.Field.CURRENCY_CODE));
        selectQuery.addField(MOrganisationCurrency.Field.NAME + " as text");
        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
        this.currencyCodeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.currencyMultipleOfValue = (Long) recurringObject.get(MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);
        this.currencyDecimalPlaceValue = (Long) recurringObject.get(MSavingsProduct.Field.CURRENCY_DIGITS);

        this.termDefaultDepositAmountValue = (Double) recurringObject.get(MDepositProductTermAndPreClosure.Field.DEPOSIT_AMOUNT);
        this.termMinimumDepositAmountValue = (Double) recurringObject.get(MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_AMOUNT);
        this.termMaximumDepositAmountValue = (Double) recurringObject.get(MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_AMOUNT);

        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(recurringObject.get(MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM)));
        this.termInterestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(recurringObject.get(MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM)));
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(recurringObject.get(MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM)));
        this.termDayInYearValue = DayInYear.optionLiteral(String.valueOf(recurringObject.get(MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM)));

        this.settingMandatoryDepositValue = (Boolean) recurringObject.get(MDepositProductRecurringDetail.Field.IS_MANDATORY);
        this.settingAdjustAdvancePaymentValue = (Boolean) recurringObject.get(MDepositProductRecurringDetail.Field.ADJUST_ADVANCE_TOWARDS_FUTURE_PAYMENTS);
        this.settingAllowWithdrawalValue = (Boolean) recurringObject.get(MDepositProductRecurringDetail.Field.ALLOW_WITHDRAWAL);

        this.settingInMultiplesOfValue = (Long) recurringObject.get(MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM);
        this.settingInMultiplesTypeValue = LockInType.optionLiteral(String.valueOf(recurringObject.get(MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM_TYPE_ENUM)));

        this.settingMinimumDepositTermValue = (Long) recurringObject.get(MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM);
        this.settingMinimumDepositTypeValue = LockInType.optionLiteral(String.valueOf(recurringObject.get(MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM_TYPE_ENUM)));

        this.settingMaximumDepositTermValue = (Long) recurringObject.get(MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM);
        this.settingMaximumDepositTypeValue = LockInType.optionLiteral(String.valueOf(recurringObject.get(MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM_TYPE_ENUM)));
        Long pre_closure_penal_applicable = (Long) recurringObject.get(MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_APPLICABLE);
        this.settingForPreMatureClosureValue = pre_closure_penal_applicable != null && pre_closure_penal_applicable == 1;
        this.settingApplyPenalInterestValue = (Double) recurringObject.get(MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST);
        this.settingApplyPenalOnValue = ApplyPenalOn.optionLiteral(String.valueOf(recurringObject.get(MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM)));

        this.settingBalanceRequiredForInterestCalculationValue = (Double) recurringObject.get(MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);

        Long withhold_tax = (Long) recurringObject.get(MSavingsProduct.Field.WITHHOLD_TAX);
        this.settingWithholdTaxApplicableValue = withhold_tax != null && withhold_tax == 1;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addField(MTaxGroup.Field.ID);
        selectQuery.addField(MTaxGroup.Field.NAME + " as text");
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, recurringObject.get("tax_group_id"));
        this.settingTaxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MSavingsProductCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField("concat(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') as " + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_PAYMENT_MODE_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_PENALTY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_ACTIVE);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MIN_CAP);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MAX_CAP);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_FREQUENCY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.INCOME_OR_LIABILITY_ACCOUNT_ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.TAX_GROUP_ID);
        selectQuery.addWhere(MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID + " = '" + this.recurringId + "'");
        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> chargeObject : chargeObjects) {
            Map<String, Object> charge = new HashMap<>();
            charge.put("name", chargeObject.get(MCharge.Field.NAME));
            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM)));
            charge.put("type", type);
            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM)));
            charge.put("collect", collect);
            charge.put("amount", chargeObject.get(MCharge.Field.AMOUNT));
            this.chargeValue.add(charge);
        }

        selectQuery = new SelectQuery(MDepositProductInterestRateChart.NAME);
        selectQuery.addJoin("INNER JOIN " + MInterestRateChart.NAME + " ON " + MDepositProductInterestRateChart.NAME + "." + MDepositProductInterestRateChart.Field.INTEREST_RATE_CHART_ID + " = " + MInterestRateChart.NAME + "." + MInterestRateChart.Field.ID);
        selectQuery.addOrderBy(MInterestRateChart.NAME + "." + MInterestRateChart.Field.FROM_DATE + " DESC");
        selectQuery.setLimit(0l, 1l);
        selectQuery.addWhere(MDepositProductInterestRateChart.NAME + "." + MDepositProductInterestRateChart.Field.DEPOSIT_PRODUCT_ID + " = '" + this.recurringId + "'");
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.ID);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.FROM_DATE);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.END_DATE);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.IS_PRIMARY_GROUPING_BY_AMOUNT);
        Map<String, Object> interestChartObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        if (interestChartObject != null) {

            this.interestRateValidFromDateValue = (Date) interestChartObject.get(MInterestRateChart.Field.FROM_DATE);
            this.interestRateValidEndDateValue = (Date) interestChartObject.get(MInterestRateChart.Field.END_DATE);
            this.interestRatePrimaryGroupingByAmountValue = (Boolean) interestChartObject.get(MInterestRateChart.Field.IS_PRIMARY_GROUPING_BY_AMOUNT);

            selectQuery = new SelectQuery(MInterestRateSlab.NAME);
            selectQuery.addField(MInterestRateSlab.Field.ID);
            selectQuery.addField(MInterestRateSlab.Field.FROM_PERIOD);
            selectQuery.addField(MInterestRateSlab.Field.TO_PERIOD);
            selectQuery.addField(MInterestRateSlab.Field.PERIOD_TYPE_ENUM);
            selectQuery.addField(MInterestRateSlab.Field.AMOUNT_RANGE_FROM);
            selectQuery.addField(MInterestRateSlab.Field.AMOUNT_RANGE_TO);
            selectQuery.addField(MInterestRateSlab.Field.ANNUAL_INTEREST_RATE);
            selectQuery.addField(MInterestRateSlab.Field.DESCRIPTION);
            selectQuery.addWhere(MInterestRateSlab.Field.INTEREST_RATE_CHART_ID + " = '" + interestChartObject.get(MInterestRateChart.Field.ID) + "'");
            List<Map<String, Object>> rateObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

            for (Map<String, Object> rateObject : rateObjects) {
                Map<String, Object> item = new HashMap<>();
                item.put("periodType", LockInType.optionLiteral(String.valueOf(rateObject.get(MInterestRateSlab.Field.PERIOD_TYPE_ENUM))));
                item.put("periodFrom", rateObject.get(MInterestRateSlab.Field.FROM_PERIOD));
                item.put("periodTo", rateObject.get(MInterestRateSlab.Field.TO_PERIOD));
                item.put("amountRangeFrom", rateObject.get(MInterestRateSlab.Field.AMOUNT_RANGE_FROM) != null ? ((Double) rateObject.get(MInterestRateSlab.Field.AMOUNT_RANGE_FROM)).longValue() : null);
                item.put("amountRangeTo", rateObject.get(MInterestRateSlab.Field.AMOUNT_RANGE_TO) != null ? ((Double) rateObject.get(MInterestRateSlab.Field.AMOUNT_RANGE_TO)).longValue() : null);
                item.put("interest", rateObject.get(MInterestRateSlab.Field.ANNUAL_INTEREST_RATE));
                item.put("description", rateObject.get(MInterestRateSlab.Field.DESCRIPTION));

                selectQuery = new SelectQuery(MInterestIncentives.NAME);
                selectQuery.addField(MInterestIncentives.Field.ENTIRY_TYPE);
                selectQuery.addField(MInterestIncentives.Field.ATTRIBUTE_NAME);
                selectQuery.addField(MInterestIncentives.Field.CONDITION_TYPE);
                selectQuery.addField(MInterestIncentives.Field.ATTRIBUTE_VALUE);
                selectQuery.addField(MInterestIncentives.Field.AMOUNT);
                selectQuery.addField(MInterestIncentives.Field.INCENTIVE_TYPE);
                selectQuery.addWhere(MInterestIncentives.Field.INTEREST_RATE_SLAB_ID + " = :" + MInterestIncentives.Field.INTEREST_RATE_SLAB_ID, rateObject.get(MInterestRateSlab.Field.ID));
                List<Map<String, Object>> incentiveObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
                if (incentiveObjects != null && !incentiveObjects.isEmpty()) {
                    List<Map<String, Object>> interestRate = new ArrayList<>();
                    for (Map<String, Object> incentiveObject : incentiveObjects) {
                        Map<String, Object> incentive = new HashMap<>();
                        Option attribute = Attribute.optionLiteral(String.valueOf(incentiveObject.get(MInterestIncentives.Field.ENTIRY_TYPE)));
                        if (attribute != null) {
                            if (attribute.getId().equals(Attribute.ClientType.name())) {
                                selectQuery = new SelectQuery(MCodeValue.NAME);
                                selectQuery.addField(MCodeValue.Field.ID);
                                selectQuery.addField(MCodeValue.Field.CODE_VALUE + " as text");
                                selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, incentiveObject.get(MInterestIncentives.Field.ATTRIBUTE_VALUE));
                                Option clientTypeOperand = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                                incentive.put("clientTypeOperand", clientTypeOperand);
                            } else if (attribute.getId().equals(Attribute.ClientClassification.name())) {
                                selectQuery = new SelectQuery(MCodeValue.NAME);
                                selectQuery.addField(MCodeValue.Field.ID);
                                selectQuery.addField(MCodeValue.Field.CODE_VALUE + " as text");
                                selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, incentiveObject.get(MInterestIncentives.Field.ATTRIBUTE_VALUE));
                                Option clientClassificationOperand = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                                incentive.put("clientClassificationOperand", clientClassificationOperand);
                            } else {
                                incentive.put("numberOperand", String.valueOf(incentiveObject.get(MInterestIncentives.Field.ATTRIBUTE_VALUE)));
                            }
                        }
                        incentive.put("attribute", attribute);
                        incentive.put("operator", Operator.optionLiteral(String.valueOf(incentiveObject.get(MInterestIncentives.Field.CONDITION_TYPE))));
                        incentive.put("operandType", OperandType.optionLiteral(String.valueOf(incentiveObject.get(MInterestIncentives.Field.INCENTIVE_TYPE))));
                        incentive.put("interest", incentiveObject.get(MInterestIncentives.Field.AMOUNT));
                        interestRate.add(incentive);
                    }
                    item.put("interestRate", interestRate);
                }

                this.interestRateChartValue.add(item);
            }
        }

        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(recurringObject.get(MSavingsProduct.Field.ACCOUNTING_TYPE)));

        if (accountingType != null) {
            this.accountingValue = accountingType.getDescription();

            selectQuery = new SelectQuery(AccProductMapping.NAME);
            selectQuery.addField(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE);
            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.recurringId);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Saving.getLiteral());
            List<Map<String, Object>> mappings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

            for (Map<String, Object> mapping : mappings) {
                FinancialAccountType financialAccountType = FinancialAccountType.parseLiteral(String.valueOf(mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE)));
                if (financialAccountType == FinancialAccountType.SavingReference1 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) != null && mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MPaymentType.NAME);
                    selectQuery.addWhere(MPaymentType.Field.ID + " = :" + MPaymentType.Field.ID, mapping.get(AccProductMapping.Field.PAYMENT_TYPE));
                    selectQuery.addField(MPaymentType.Field.ID);
                    selectQuery.addField(MPaymentType.Field.VALUE + " as text");
                    item.put("payment", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFundSourceValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomeFee4 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.CHARGE_ID) != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFeeIncomeValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomePenalty5 && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.CHARGE_ID) != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRulePenaltyIncomeValue.add(item);
                }
                if (financialAccountType != null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null && mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null) {
                    if (financialAccountType == FinancialAccountType.SavingReference1) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingReferenceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.SavingControl2) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingControlValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.TransferInSuspense10) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingTransferInSuspenseValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashInterestOnSavingValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.IncomeFee4) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.IncomePenalty5) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashIncomeFromPenaltyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    }
                }
            }
        }

    }

}
