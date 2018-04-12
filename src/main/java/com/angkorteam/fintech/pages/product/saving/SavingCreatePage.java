package com.angkorteam.fintech.pages.product.saving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ProductSavingBuilder;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.helper.SavingHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.product.savings.Accounting;
import com.angkorteam.fintech.widget.product.savings.Charges;
import com.angkorteam.fintech.widget.product.savings.Currency;
import com.angkorteam.fintech.widget.product.savings.Details;
import com.angkorteam.fintech.widget.product.savings.Preview;
import com.angkorteam.fintech.widget.product.savings.Settings;
import com.angkorteam.fintech.widget.product.savings.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_CURRENCY = 1;
    public static int TAB_TERM = 2;
    public static int TAB_SETTING = 3;
    public static int TAB_CHARGE = 4;
    public static int TAB_ACCOUNTING = 5;
    public static int TAB_PREVIEW = 6;

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

    protected boolean errorDetail;
    protected boolean errorCurrency;
    protected boolean errorAccounting;
    protected boolean errorTerm;
    protected boolean errorSetting;
    protected boolean errorCharge;

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
            breadcrumb.setLabel("Saving Product Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void configureMetaData() {

    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new Charges(this), new Accounting(this), new Preview(this)));
        add(this.tab);
    }

    @Override
    protected void initData() {
        this.errorDetail = true;
        this.errorCurrency = true;
        this.errorAccounting = true;
        this.errorTerm = true;
        this.errorSetting = true;
        this.errorCharge = true;

        this.chargeValue = new ArrayList<>();
        this.advancedAccountingRuleFundSourceValue = new ArrayList<>();
        this.advancedAccountingRuleFeeIncomeValue = new ArrayList<>();
        this.advancedAccountingRulePenaltyIncomeValue = new ArrayList<>();

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2l;
        this.currencyMultipleOfValue = 1l;
        this.termNominalAnnualInterestValue = 10d;
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Daily.toOption();
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.DailyBalance.toOption();
        this.termInterestPostingPeriodValue = InterestPostingPeriod.Monthly.toOption();
        this.termDayInYearValue = DayInYear.D365.toOption();
        this.accountingValue = AccountingType.None.getDescription();
    }

    public void saveButtonSubmit(Button button) {
        ProductSavingBuilder builder = new ProductSavingBuilder();

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
        builder.withNominalAnnualInterestRate(this.termNominalAnnualInterestValue);
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
        builder.withMinRequiredOpeningBalance(this.settingMinimumOpeningBalanceValue);
        builder.withLockinPeriodFrequency(this.settingLockInPeriodValue);
        if (this.settingLockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.settingLockInTypeValue.getId()));
        }
        builder.withWithdrawalFeeForTransfers(this.settingApplyWithdrawalFeeForTransferValue == null ? false : this.settingApplyWithdrawalFeeForTransferValue);
        builder.withMinBalanceForInterestCalculation(this.settingBalanceRequiredForInterestCalculationValue);
        builder.withEnforceMinRequiredBalance(this.settingEnforceMinimumBalanceValue == null ? false : this.settingEnforceMinimumBalanceValue);
        builder.withMinRequiredBalance(this.settingMinimumBalanceValue);
        boolean allowOverdraft = this.settingOverdraftAllowedValue == null ? false : this.settingOverdraftAllowedValue;
        builder.withAllowOverdraft(allowOverdraft);
        if (allowOverdraft) {
            builder.withOverdraftLimit(this.settingMaximumOverdraftAmountLimitValue);
            builder.withNominalAnnualInterestRateOverdraft(this.settingNominalAnnualInterestForOverdraftValue);
            builder.withMinOverdraftForInterestCalculation(this.settingMinOverdraftRequiredForInterestCalculationValue);
        }
        boolean holdTax = this.settingWithholdTaxApplicableValue == null ? false : this.settingWithholdTaxApplicableValue;
        builder.withHoldTax(holdTax);
        if (holdTax) {
            if (this.settingTaxGroupValue != null) {
                builder.withTaxGroupId(this.settingTaxGroupValue.getId());
            }
        }

        boolean dormancyTrackingActive = this.settingEnableDormancyTrackingValue == null ? false : this.settingEnableDormancyTrackingValue;
        builder.withDormancyTrackingActive(dormancyTrackingActive);
        if (dormancyTrackingActive) {
            builder.withDaysToInactive(this.settingNumberOfDaysToInactiveSubStatusValue);
            builder.withDaysToDormancy(this.settingNumberOfDaysToDormantSubStatusValue);
            builder.withDaysToEscheat(this.settingNumberOfDaysToEscheatValue);
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
            if (this.cashOverdraftPortfolioValue != null) {
                builder.withOverdraftPortfolioControlId(this.cashOverdraftPortfolioValue.getId());
            }
            if (this.cashSavingControlValue != null) {
                builder.withSavingsControlAccountId(this.cashSavingControlValue.getId());
            }
            if (this.cashSavingTransferInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashSavingTransferInSuspenseValue.getId());
            }
            if (this.settingEnableDormancyTrackingValue != null && this.settingEnableDormancyTrackingValue) {
                if (this.cashEscheatLiabilityValue != null) {
                    builder.withEscheatLiabilityId(this.cashEscheatLiabilityValue.getId());
                }
            }
            if (this.cashInterestOnSavingValue != null) {
                builder.withInterestOnSavingsAccountId(this.cashInterestOnSavingValue.getId());
            }
            if (this.cashWriteOffValue != null) {
                builder.withWriteOffAccountId(this.cashWriteOffValue.getId());
            }
            if (this.cashIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
            }
            if (this.cashIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
            }
            if (this.cashOverdraftInterestIncomeValue != null) {
                builder.withIncomeFromInterestId(this.cashOverdraftInterestIncomeValue.getId());
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

        JsonNode node = SavingHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(SavingBrowsePage.class);
    }

}
