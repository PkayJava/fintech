package com.angkorteam.fintech.pages.product.saving;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MSavingsProductCharge;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.FinancialAccountType;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.ProductType;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.widget.product.savings.Accounting;
import com.angkorteam.fintech.widget.product.savings.Charges;
import com.angkorteam.fintech.widget.product.savings.Currency;
import com.angkorteam.fintech.widget.product.savings.Details;
import com.angkorteam.fintech.widget.product.savings.Preview;
import com.angkorteam.fintech.widget.product.savings.Settings;
import com.angkorteam.fintech.widget.product.savings.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingPreviewPage extends Page {

    protected String savingId;

    public static int TAB_DETAIL = SavingCreatePage.TAB_DETAIL;
    public static int TAB_CURRENCY = SavingCreatePage.TAB_CURRENCY;
    public static int TAB_TERM = SavingCreatePage.TAB_TERM;
    public static int TAB_SETTING = SavingCreatePage.TAB_SETTING;
    public static int TAB_CHARGE = SavingCreatePage.TAB_CHARGE;
    public static int TAB_ACCOUNTING = SavingCreatePage.TAB_ACCOUNTING;
    public static int TAB_PREVIEW = SavingCreatePage.TAB_PREVIEW;

    // Details
    protected String detailProductNameValue;
    protected String detailShortNameValue;
    protected String detailDescriptionValue;

    // Currency
    protected Option currencyCodeValue;
    protected Long currencyDecimalPlaceValue;
    protected Long currencyMultipleOfValue;

    // Terms

    protected Double termNominalAnnualInterestValue;
    protected Option termInterestCompoundingPeriodValue;
    protected Option termInterestCalculatedUsingValue;
    protected Option termInterestPostingPeriodValue;
    protected Option termDayInYearValue;
    protected Double settingMinimumBalanceValue;
    protected Boolean settingOverdraftAllowedValue;

    // Settings

    protected Double settingMinimumOpeningBalanceValue;
    protected Long settingLockInPeriodValue;
    protected Option settingLockInTypeValue;
    protected Boolean settingApplyWithdrawalFeeForTransferValue;
    protected Double settingBalanceRequiredForInterestCalculationValue;
    protected Double settingMaximumOverdraftAmountLimitValue;
    protected Double settingNominalAnnualInterestForOverdraftValue;
    protected Double settingMinOverdraftRequiredForInterestCalculationValue;
    protected Boolean settingWithholdTaxApplicableValue;
    protected Boolean settingEnforceMinimumBalanceValue;
    protected Option settingTaxGroupValue;
    protected Boolean settingEnableDormancyTrackingValue;
    protected Long settingNumberOfDaysToInactiveSubStatusValue;
    protected Long settingNumberOfDaysToDormantSubStatusValue;
    protected Long settingNumberOfDaysToEscheatValue;

    // Charges

    protected List<Map<String, Object>> chargeValue;

    // Accounting

    protected String accountingValue;
    protected Option cashSavingReferenceValue;
    protected Option cashOverdraftPortfolioValue;
    protected Option cashSavingControlValue;
    protected Option cashSavingTransferInSuspenseValue;
    protected Option cashEscheatLiabilityValue;
    protected Option cashInterestOnSavingValue;
    protected Option cashWriteOffValue;
    protected Option cashIncomeFromFeeValue;
    protected Option cashIncomeFromPenaltyValue;
    protected Option cashOverdraftInterestIncomeValue;

    // Advanced Accounting Rule

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail = true;
    protected boolean errorCurrency = true;
    protected boolean errorAccounting = true;
    protected boolean errorTerm = true;
    protected boolean errorSetting = true;
    protected boolean errorCharge = true;

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
            breadcrumb.setLabel("Saving Product");
            breadcrumb.setPage(SavingBrowsePage.class);
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
    protected void configureMetaData() {
        this.tab.setSelectedTab(TAB_PREVIEW);
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new Charges(this), new Accounting(this), new Preview(this)));
        add(this.tab);
    }

    @Override
    protected void initData() {
        this.savingId = getPageParameters().get("savingId").toString();

        this.chargeValue = new ArrayList<>();
        this.advancedAccountingRuleFundSourceValue = new ArrayList<>();
        this.advancedAccountingRuleFeeIncomeValue = new ArrayList<>();
        this.advancedAccountingRulePenaltyIncomeValue = new ArrayList<>();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.SHORT_NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DESCRIPTION);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_CODE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.MIN_REQUIRED_OPENING_BALANCE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.WITHDRAWAL_FEE_FOR_TRANSFER);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ENFORCE_MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.WITHHOLD_TAX);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.TAX_GROUP_ID);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.IS_DORMANCY_TRACKING_ACTIVE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DAYS_TO_INACTIVE);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DAYS_TO_DORMANCY);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DAYS_TO_ESCHEAT);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ACCOUNTING_TYPE);
        selectQuery.addWhere(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID + " = '" + this.savingId + "'");

        Map<String, Object> savingObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.detailProductNameValue = (String) savingObject.get(MSavingsProduct.Field.NAME);
        this.detailShortNameValue = (String) savingObject.get(MSavingsProduct.Field.SHORT_NAME);
        this.detailDescriptionValue = (String) savingObject.get(MSavingsProduct.Field.DESCRIPTION);

        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, savingObject.get(MSavingsProduct.Field.CURRENCY_CODE));
        selectQuery.addField(MOrganisationCurrency.Field.NAME + " as text");
        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
        this.currencyCodeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.currencyMultipleOfValue = (Long) savingObject.get(MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);
        this.currencyDecimalPlaceValue = (Long) savingObject.get(MSavingsProduct.Field.CURRENCY_DIGITS);

        this.termNominalAnnualInterestValue = (Double) savingObject.get(MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE);
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM)));
        this.termInterestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM)));
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM)));
        this.termDayInYearValue = DayInYear.optionLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM)));

        this.settingMinimumOpeningBalanceValue = (Double) savingObject.get(MSavingsProduct.Field.MIN_REQUIRED_OPENING_BALANCE);
        Double lockin_period_frequency = (Double) savingObject.get(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        this.settingLockInPeriodValue = lockin_period_frequency == null ? null : lockin_period_frequency.longValue();
        this.settingLockInTypeValue = LockInType.optionLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM)));
        Long withdrawal_fee_for_transfer = (Long) savingObject.get(MSavingsProduct.Field.WITHDRAWAL_FEE_FOR_TRANSFER);
        this.settingApplyWithdrawalFeeForTransferValue = withdrawal_fee_for_transfer != null && withdrawal_fee_for_transfer == 1l;
        this.settingEnforceMinimumBalanceValue = (Boolean) savingObject.get(MSavingsProduct.Field.ENFORCE_MIN_REQUIRED_BALANCE);
        this.settingMinimumBalanceValue = (Double) savingObject.get(MSavingsProduct.Field.MIN_REQUIRED_BALANCE);
        this.settingBalanceRequiredForInterestCalculationValue = (Double) savingObject.get(MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);
        Long withhold_tax = (Long) savingObject.get(MSavingsProduct.Field.WITHHOLD_TAX);
        this.settingWithholdTaxApplicableValue = withhold_tax != null && withhold_tax == 1;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, savingObject.get(MSavingsProduct.Field.TAX_GROUP_ID));
        selectQuery.addField(MTaxGroup.Field.NAME + " as text");
        selectQuery.addField(MTaxGroup.Field.ID);
        this.settingTaxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        Long is_dormancy_tracking_active = (Long) savingObject.get(MSavingsProduct.Field.IS_DORMANCY_TRACKING_ACTIVE);
        this.settingEnableDormancyTrackingValue = is_dormancy_tracking_active != null && is_dormancy_tracking_active == 1;
        this.settingNumberOfDaysToDormantSubStatusValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_DORMANCY);
        this.settingNumberOfDaysToInactiveSubStatusValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_INACTIVE);
        this.settingNumberOfDaysToEscheatValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_ESCHEAT);

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MSavingsProductCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField("CONCAT(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') AS " + MCharge.Field.NAME);
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
        selectQuery.addWhere(MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID + " = '" + this.savingId + "'");

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

        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(savingObject.get(MSavingsProduct.Field.ACCOUNTING_TYPE)));

        if (accountingType != null) {
            this.accountingValue = accountingType.getDescription();

            selectQuery = new SelectQuery(AccProductMapping.NAME);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.savingId);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Saving.getLiteral());
            selectQuery.addField(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE);
            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
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
                if (financialAccountType != null && mapping.get("gl_account_id") != null && mapping.get("charge_id") == null && mapping.get("payment_type") == null) {
                    if (financialAccountType == FinancialAccountType.SavingReference1) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingReferenceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.OverdraftPortfolio11) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashOverdraftPortfolioValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
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
                    } else if (financialAccountType == FinancialAccountType.EscheatLiability14) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashEscheatLiabilityValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashInterestOnSavingValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.WriteOff13) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashWriteOffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
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
                    } else if (financialAccountType == FinancialAccountType.OverdraftInterestIncome12) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashOverdraftInterestIncomeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    }
                }
            }
        }

    }

}
