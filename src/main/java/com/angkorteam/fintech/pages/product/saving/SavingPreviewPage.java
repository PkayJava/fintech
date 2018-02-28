package com.angkorteam.fintech.pages.product.saving;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.*;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.*;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingPreviewPage extends Page {

    protected BookmarkablePageLink<Void> editLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected String savingId;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected String detailProductNameValue;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected String detailShortNameValue;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected String detailDescriptionValue;
    protected ReadOnlyView detailDescriptionView;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected String currencyCodeValue;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected Long currencyDecimalPlaceValue;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfVContainer;
    protected Long currencyMultipleOfValue;
    protected ReadOnlyView currencyMultipleOfView;

    // Terms

    protected WebMarkupBlock termNominalAnnualInterestBlock;
    protected WebMarkupContainer termNominalAnnualInterestVContainer;
    protected Double termNominalAnnualInterestValue;
    protected ReadOnlyView termNominalAnnualInterestView;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodVContainer;
    protected Option termInterestCompoundingPeriodValue;
    protected ReadOnlyView termInterestCompoundingPeriodView;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingVContainer;
    protected Option termInterestCalculatedUsingValue;
    protected ReadOnlyView termInterestCalculatedUsingView;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodVContainer;
    protected Option termInterestPostingPeriodValue;
    protected ReadOnlyView termInterestPostingPeriodView;

    protected WebMarkupBlock termDayInYearBlock;
    protected WebMarkupContainer termDayInYearVContainer;
    protected Option termDayInYearValue;
    protected ReadOnlyView termDayInYearView;
    protected TextFeedbackPanel termDayInYearFeedback;

    // Settings

    protected WebMarkupBlock settingMinimumOpeningBalanceBlock;
    protected WebMarkupContainer settingMinimumOpeningBalanceVContainer;
    protected Double settingMinimumOpeningBalanceValue;
    protected ReadOnlyView settingMinimumOpeningBalanceView;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodVContainer;
    protected Long settingLockInPeriodValue;
    protected ReadOnlyView settingLockInPeriodView;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeVContainer;
    protected Option settingLockInTypeValue;
    protected ReadOnlyView settingLockInTypeView;

    protected WebMarkupBlock settingApplyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferVContainer;
    protected Boolean settingApplyWithdrawalFeeForTransferValue;
    protected ReadOnlyView settingApplyWithdrawalFeeForTransferView;

    protected WebMarkupBlock settingBalanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationVContainer;
    protected Double settingBalanceRequiredForInterestCalculationValue;
    protected ReadOnlyView settingBalanceRequiredForInterestCalculationView;

    protected WebMarkupBlock settingEnforceMinimumBalanceBlock;
    protected WebMarkupContainer settingEnforceMinimumBalanceVContainer;
    protected Boolean settingEnforceMinimumBalanceValue;
    protected ReadOnlyView settingEnforceMinimumBalanceView;

    protected WebMarkupBlock settingMinimumBalanceBlock;
    protected WebMarkupContainer settingMinimumBalanceVContainer;
    protected Double settingMinimumBalanceValue;
    protected ReadOnlyView settingMinimumBalanceView;

    protected WebMarkupBlock settingOverdraftAllowedBlock;
    protected WebMarkupContainer settingOverdraftAllowedVContainer;
    protected Boolean settingOverdraftAllowedValue;
    protected ReadOnlyView settingOverdraftAllowedView;

    protected WebMarkupBlock settingMaximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer settingMaximumOverdraftAmountLimitVContainer;
    protected Double settingMaximumOverdraftAmountLimitValue;
    protected ReadOnlyView settingMaximumOverdraftAmountLimitView;

    protected WebMarkupBlock settingNominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftVContainer;
    protected Double settingNominalAnnualInterestForOverdraftValue;
    protected ReadOnlyView settingNominalAnnualInterestForOverdraftView;

    protected WebMarkupBlock settingMinOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationVContainer;
    protected Double settingMinOverdraftRequiredForInterestCalculationValue;
    protected ReadOnlyView settingMinOverdraftRequiredForInterestCalculationView;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableVContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected ReadOnlyView settingWithholdTaxApplicableView;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupVContainer;
    protected String settingTaxGroupValue;
    protected ReadOnlyView settingTaxGroupView;

    protected WebMarkupBlock settingEnableDormancyTrackingBlock;
    protected WebMarkupContainer settingEnableDormancyTrackingVContainer;
    protected Boolean settingEnableDormancyTrackingValue;
    protected ReadOnlyView settingEnableDormancyTrackingView;

    protected WebMarkupBlock settingNumberOfDaysToInactiveSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusVContainer;
    protected Long settingNumberOfDaysToInactiveSubStatusValue;
    protected ReadOnlyView settingNumberOfDaysToInactiveSubStatusView;

    protected WebMarkupBlock settingNumberOfDaysToDormantSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusVContainer;
    protected Long settingNumberOfDaysToDormantSubStatusValue;
    protected ReadOnlyView settingNumberOfDaysToDormantSubStatusView;

    protected WebMarkupBlock settingNumberOfDaysToEscheatBlock;
    protected WebMarkupContainer settingNumberOfDaysToEscheatVContainer;
    protected Long settingNumberOfDaysToEscheatValue;
    protected ReadOnlyView settingNumberOfDaysToEscheatView;

    // Charges

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected Label accountingLabel;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashVContainer;

    protected WebMarkupBlock cashSavingReferenceBlock;
    protected WebMarkupContainer cashSavingReferenceVContainer;
    protected Option cashSavingReferenceValue;
    protected ReadOnlyView cashSavingReferenceView;

    protected WebMarkupBlock cashOverdraftPortfolioBlock;
    protected WebMarkupContainer cashOverdraftPortfolioVContainer;
    protected Option cashOverdraftPortfolioValue;
    protected ReadOnlyView cashOverdraftPortfolioView;

    protected WebMarkupBlock cashSavingControlBlock;
    protected WebMarkupContainer cashSavingControlVContainer;
    protected Option cashSavingControlValue;
    protected ReadOnlyView cashSavingControlView;

    protected WebMarkupBlock cashSavingTransferInSuspenseBlock;
    protected WebMarkupContainer cashSavingTransferInSuspenseVContainer;
    protected Option cashSavingTransferInSuspenseValue;
    protected ReadOnlyView cashSavingTransferInSuspenseView;

    protected WebMarkupBlock cashEscheatLiabilityBlock;
    protected WebMarkupContainer cashEscheatLiabilityVContainer;
    protected Option cashEscheatLiabilityValue;
    protected ReadOnlyView cashEscheatLiabilityView;

    protected WebMarkupBlock cashInterestOnSavingBlock;
    protected WebMarkupContainer cashInterestOnSavingVContainer;
    protected Option cashInterestOnSavingValue;
    protected ReadOnlyView cashInterestOnSavingView;

    protected WebMarkupBlock cashWriteOffBlock;
    protected WebMarkupContainer cashWriteOffVContainer;
    protected Option cashWriteOffValue;
    protected ReadOnlyView cashWriteOffView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected Option cashIncomeFromFeeValue;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
    protected Option cashIncomeFromPenaltyValue;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    protected WebMarkupBlock cashOverdraftInterestIncomeBlock;
    protected WebMarkupContainer cashOverdraftInterestIncomeVContainer;
    protected Option cashOverdraftInterestIncomeValue;
    protected ReadOnlyView cashOverdraftInterestIncomeView;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleVContainer;

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;

    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;

    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;

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
            breadcrumb.setLabel("Saving Product Preview");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        this.cashVContainer.setVisible(AccountingType.Cash.getDescription().equals(this.accountingValue));
        this.advancedAccountingRuleVContainer.setVisible(AccountingType.Cash.getDescription().equals(this.accountingValue));
    }

    @Override
    protected void initComponent() {

        this.editLink = new BookmarkablePageLink<>("editLink", SavingBrowsePage.class);
        add(this.editLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerm();

        initSectionSetting();

        initSectionCharge();

        initSectionAccounting();
    }

    @Override
    protected void initData() {
        this.savingId = getPageParameters().get("savingId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        selectQuery.addJoin("INNER JOIN " + MOrganisationCurrency.NAME + " ON " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_CODE + " = " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.SHORT_NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DESCRIPTION);
        selectQuery.addField("concat(" + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.NAME + ", ' [', " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE + ", ']') currency");
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

        this.currencyCodeValue = (String) savingObject.get("currency");
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
        selectQuery.addField(MTaxGroup.NAME);
        this.settingTaxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        Long is_dormancy_tracking_active = (Long) savingObject.get(MSavingsProduct.Field.IS_DORMANCY_TRACKING_ACTIVE);
        this.settingEnableDormancyTrackingValue = is_dormancy_tracking_active != null && is_dormancy_tracking_active == 1;
        this.settingNumberOfDaysToDormantSubStatusValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_DORMANCY);
        this.settingNumberOfDaysToInactiveSubStatusValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_INACTIVE);
        this.settingNumberOfDaysToEscheatValue = (Long) savingObject.get(MSavingsProduct.Field.DAYS_TO_ESCHEAT);

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MSavingsProductCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField("concat(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') AS " + MCharge.Field.NAME);
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

    protected void initSectionAccounting() {
        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this, "accountingValue"));
        add(this.accountingLabel);

        initAccountingCash();

        initAdvancedAccountingRule();

    }

    protected void initAdvancedAccountingRule() {
        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleVContainer = new WebMarkupContainer("advancedAccountingRuleVContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleVContainer);

        initAdvancedAccountingRuleFundSourceTable();

        initAdvancedAccountingRuleFeeIncomeTable();

        initAdvancedAccountingRulePenaltyIncomeTable();

    }

    protected void initAdvancedAccountingRulePenaltyIncomeTable() {
        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
    }

    protected void initAdvancedAccountingRuleFeeIncomeTable() {
        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
    }

    protected void initAdvancedAccountingRuleFundSourceTable() {
        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        add(this.cashBlock);
        this.cashVContainer = new WebMarkupContainer("cashVContainer");
        this.cashBlock.add(this.cashVContainer);

        initCashSavingReferenceBlock();

        initCashOverdraftPortfolioBlock();

        initCashSavingControlBlock();

        initCashSavingTransferInSuspenseBlock();

        initCashEscheatLiabilityBlock();

        initCashInterestOnSavingBlock();

        initCashWriteOffBlock();

        initCashIncomeFromFeeBlock();

        initCashIncomeFromPenaltyBlock();

        initCashOverdraftInterestIncomeBlock();
    }

    protected void initCashSavingReferenceBlock() {
        this.cashSavingReferenceBlock = new WebMarkupBlock("cashSavingReferenceBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingReferenceBlock);
        this.cashSavingReferenceVContainer = new WebMarkupContainer("cashSavingReferenceVContainer");
        this.cashSavingReferenceBlock.add(this.cashSavingReferenceVContainer);
        this.cashSavingReferenceView = new ReadOnlyView("cashSavingReferenceView", new PropertyModel<>(this, "cashSavingReferenceValue"));
        this.cashSavingReferenceVContainer.add(this.cashSavingReferenceView);
    }

    protected void initCashOverdraftPortfolioBlock() {
        this.cashOverdraftPortfolioBlock = new WebMarkupBlock("cashOverdraftPortfolioBlock", Size.Six_6);
        this.cashVContainer.add(this.cashOverdraftPortfolioBlock);
        this.cashOverdraftPortfolioVContainer = new WebMarkupContainer("cashOverdraftPortfolioVContainer");
        this.cashOverdraftPortfolioBlock.add(this.cashOverdraftPortfolioVContainer);
        this.cashOverdraftPortfolioView = new ReadOnlyView("cashOverdraftPortfolioView", new PropertyModel<>(this, "cashOverdraftPortfolioValue"));
        this.cashOverdraftPortfolioVContainer.add(this.cashOverdraftPortfolioView);
    }

    protected void initCashSavingControlBlock() {
        this.cashSavingControlBlock = new WebMarkupBlock("cashSavingControlBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingControlBlock);
        this.cashSavingControlVContainer = new WebMarkupContainer("cashSavingControlVContainer");
        this.cashSavingControlBlock.add(this.cashSavingControlVContainer);
        this.cashSavingControlView = new ReadOnlyView("cashSavingControlView", new PropertyModel<>(this, "cashSavingControlValue"));
        this.cashSavingControlVContainer.add(this.cashSavingControlView);
    }

    protected void initCashSavingTransferInSuspenseBlock() {
        this.cashSavingTransferInSuspenseBlock = new WebMarkupBlock("cashSavingTransferInSuspenseBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingTransferInSuspenseBlock);
        this.cashSavingTransferInSuspenseVContainer = new WebMarkupContainer("cashSavingTransferInSuspenseVContainer");
        this.cashSavingTransferInSuspenseBlock.add(this.cashSavingTransferInSuspenseVContainer);
        this.cashSavingTransferInSuspenseView = new ReadOnlyView("cashSavingTransferInSuspenseView", new PropertyModel<>(this, "cashSavingTransferInSuspenseValue"));
        this.cashSavingTransferInSuspenseVContainer.add(this.cashSavingTransferInSuspenseView);
    }

    protected void initCashEscheatLiabilityBlock() {
        this.cashEscheatLiabilityBlock = new WebMarkupBlock("cashEscheatLiabilityBlock", Size.Six_6);
        this.cashVContainer.add(cashEscheatLiabilityBlock);
        this.cashEscheatLiabilityVContainer = new WebMarkupContainer("cashEscheatLiabilityVContainer");
        this.cashEscheatLiabilityBlock.add(this.cashEscheatLiabilityVContainer);
        this.cashEscheatLiabilityView = new ReadOnlyView("cashEscheatLiabilityView", new PropertyModel<>(this, "cashEscheatLiabilityValue"));
        this.cashEscheatLiabilityVContainer.add(this.cashEscheatLiabilityView);
    }

    protected void initCashInterestOnSavingBlock() {
        this.cashInterestOnSavingBlock = new WebMarkupBlock("cashInterestOnSavingBlock", Size.Six_6);
        this.cashVContainer.add(this.cashInterestOnSavingBlock);
        this.cashInterestOnSavingVContainer = new WebMarkupContainer("cashInterestOnSavingVContainer");
        this.cashInterestOnSavingBlock.add(this.cashInterestOnSavingVContainer);
        this.cashInterestOnSavingView = new ReadOnlyView("cashInterestOnSavingView", new PropertyModel<>(this, "cashInterestOnSavingValue"));
        this.cashInterestOnSavingVContainer.add(this.cashInterestOnSavingView);
    }

    protected void initCashWriteOffBlock() {
        this.cashWriteOffBlock = new WebMarkupBlock("cashWriteOffBlock", Size.Six_6);
        this.cashVContainer.add(this.cashWriteOffBlock);
        this.cashWriteOffVContainer = new WebMarkupContainer("cashWriteOffVContainer");
        this.cashWriteOffBlock.add(this.cashWriteOffVContainer);
        this.cashWriteOffView = new ReadOnlyView("cashWriteOffView", new PropertyModel<>(this, "cashWriteOffValue"));
        this.cashWriteOffVContainer.add(this.cashWriteOffView);
    }

    protected void initCashIncomeFromFeeBlock() {
        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
    }

    protected void initCashIncomeFromPenaltyBlock() {
        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);
    }

    protected void initCashOverdraftInterestIncomeBlock() {
        this.cashOverdraftInterestIncomeBlock = new WebMarkupBlock("cashOverdraftInterestIncomeBlock", Size.Six_6);
        this.cashVContainer.add(this.cashOverdraftInterestIncomeBlock);
        this.cashOverdraftInterestIncomeVContainer = new WebMarkupContainer("cashOverdraftInterestIncomeVContainer");
        this.cashOverdraftInterestIncomeBlock.add(this.cashOverdraftInterestIncomeVContainer);
        this.cashOverdraftInterestIncomeView = new ReadOnlyView("cashOverdraftInterestIncomeView", new PropertyModel<>(this, "cashOverdraftInterestIncomeValue"));
        this.cashOverdraftInterestIncomeVContainer.add(this.cashOverdraftInterestIncomeView);
    }

    protected void initSectionCharge() {

        initChargeBlock();
    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        // this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeVContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "date".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("type".equals(column) || "collect".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initSectionSetting() {
        initSettingMinimumOpeningBalanceBlock();

        initSettingLockInPeriodBlock();

        initSettingLockInTypeBlock();

        initSettingApplyWithdrawalFeeForTransferBlock();

        initSettingBalanceRequiredForInterestCalculationBlock();

        initSettingEnforceMinimumBalanceBlock();

        initSettingMinimumBalanceBlock();

        initSettingOverdraftAllowedBlock();

        initSettingMaximumOverdraftAmountLimitBlock();

        initSettingNominalAnnualInterestForOverdraftBlock();

        initSettingMinOverdraftRequiredForInterestCalculationBlock();

        initSettingWithholdTaxApplicableBlock();

        initSettingTaxGroupBlock();

        initSettingEnableDormancyTrackingBlock();

        initSettingNumberOfDaysToInactiveSubStatusBlock();

        initSettingNumberOfDaysToDormantSubStatusBlock();

        initSettingNumberOfDaysToEscheatBlock();
    }

    protected void initSettingNumberOfDaysToEscheatBlock() {
        this.settingNumberOfDaysToEscheatBlock = new WebMarkupBlock("settingNumberOfDaysToEscheatBlock", Size.Six_6);
        add(this.settingNumberOfDaysToEscheatBlock);
        this.settingNumberOfDaysToEscheatVContainer = new WebMarkupContainer("settingNumberOfDaysToEscheatVContainer");
        this.settingNumberOfDaysToEscheatBlock.add(this.settingNumberOfDaysToEscheatVContainer);
        this.settingNumberOfDaysToEscheatView = new ReadOnlyView("settingNumberOfDaysToEscheatView", new PropertyModel<>(this, "settingNumberOfDaysToEscheatValue"));
        this.settingNumberOfDaysToEscheatVContainer.add(this.settingNumberOfDaysToEscheatView);
    }

    protected void initSettingNumberOfDaysToDormantSubStatusBlock() {
        this.settingNumberOfDaysToDormantSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Six_6);
        add(this.settingNumberOfDaysToDormantSubStatusBlock);
        this.settingNumberOfDaysToDormantSubStatusVContainer = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusVContainer");
        this.settingNumberOfDaysToDormantSubStatusBlock.add(this.settingNumberOfDaysToDormantSubStatusVContainer);
        this.settingNumberOfDaysToDormantSubStatusView = new ReadOnlyView("settingNumberOfDaysToDormantSubStatusView", new PropertyModel<>(this, "settingNumberOfDaysToDormantSubStatusValue"));
        this.settingNumberOfDaysToDormantSubStatusVContainer.add(this.settingNumberOfDaysToDormantSubStatusView);
    }

    protected void initSettingNumberOfDaysToInactiveSubStatusBlock() {
        this.settingNumberOfDaysToInactiveSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Six_6);
        add(this.settingNumberOfDaysToInactiveSubStatusBlock);
        this.settingNumberOfDaysToInactiveSubStatusVContainer = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusVContainer");
        this.settingNumberOfDaysToInactiveSubStatusBlock.add(this.settingNumberOfDaysToInactiveSubStatusVContainer);
        this.settingNumberOfDaysToInactiveSubStatusView = new ReadOnlyView("settingNumberOfDaysToInactiveSubStatusView", new PropertyModel<>(this, "settingNumberOfDaysToInactiveSubStatusValue"));
        this.settingNumberOfDaysToInactiveSubStatusVContainer.add(this.settingNumberOfDaysToInactiveSubStatusView);
    }

    protected void initSettingEnableDormancyTrackingBlock() {
        this.settingEnableDormancyTrackingBlock = new WebMarkupBlock("settingEnableDormancyTrackingBlock", Size.Six_6);
        add(this.settingEnableDormancyTrackingBlock);
        this.settingEnableDormancyTrackingVContainer = new WebMarkupContainer("settingEnableDormancyTrackingVContainer");
        this.settingEnableDormancyTrackingBlock.add(this.settingEnableDormancyTrackingVContainer);
        this.settingEnableDormancyTrackingView = new ReadOnlyView("settingEnableDormancyTrackingView", new PropertyModel<>(this, "settingEnableDormancyTrackingValue"));
        this.settingEnableDormancyTrackingVContainer.add(this.settingEnableDormancyTrackingView);
    }

    protected void initSettingTaxGroupBlock() {
        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        add(this.settingTaxGroupBlock);
        this.settingTaxGroupVContainer = new WebMarkupContainer("settingTaxGroupVContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupVContainer);
        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this, "settingTaxGroupValue"));
        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);
    }

    protected void initSettingWithholdTaxApplicableBlock() {
        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableVContainer = new WebMarkupContainer("settingWithholdTaxApplicableVContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableVContainer);
        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);
    }

    protected void initSettingMinOverdraftRequiredForInterestCalculationBlock() {
        this.settingMinOverdraftRequiredForInterestCalculationBlock = new WebMarkupBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Six_6);
        add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        this.settingMinOverdraftRequiredForInterestCalculationVContainer = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationVContainer");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.add(this.settingMinOverdraftRequiredForInterestCalculationVContainer);
        this.settingMinOverdraftRequiredForInterestCalculationView = new ReadOnlyView("settingMinOverdraftRequiredForInterestCalculationView", new PropertyModel<>(this, "settingMinOverdraftRequiredForInterestCalculationValue"));
        this.settingMinOverdraftRequiredForInterestCalculationVContainer.add(this.settingMinOverdraftRequiredForInterestCalculationView);
    }

    protected void initSettingNominalAnnualInterestForOverdraftBlock() {
        this.settingNominalAnnualInterestForOverdraftBlock = new WebMarkupBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Six_6);
        add(this.settingNominalAnnualInterestForOverdraftBlock);
        this.settingNominalAnnualInterestForOverdraftVContainer = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftVContainer");
        this.settingNominalAnnualInterestForOverdraftBlock.add(this.settingNominalAnnualInterestForOverdraftVContainer);
        this.settingNominalAnnualInterestForOverdraftView = new ReadOnlyView("settingNominalAnnualInterestForOverdraftView", new PropertyModel<>(this, "settingNominalAnnualInterestForOverdraftValue"));
        this.settingNominalAnnualInterestForOverdraftVContainer.add(this.settingNominalAnnualInterestForOverdraftView);
    }

    protected void initSettingMaximumOverdraftAmountLimitBlock() {
        this.settingMaximumOverdraftAmountLimitBlock = new WebMarkupBlock("settingMaximumOverdraftAmountLimitBlock", Size.Six_6);
        add(this.settingMaximumOverdraftAmountLimitBlock);
        this.settingMaximumOverdraftAmountLimitVContainer = new WebMarkupContainer("settingMaximumOverdraftAmountLimitVContainer");
        this.settingMaximumOverdraftAmountLimitBlock.add(this.settingMaximumOverdraftAmountLimitVContainer);
        this.settingMaximumOverdraftAmountLimitView = new ReadOnlyView("settingMaximumOverdraftAmountLimitView", new PropertyModel<>(this, "settingMaximumOverdraftAmountLimitValue"));
        this.settingMaximumOverdraftAmountLimitVContainer.add(this.settingMaximumOverdraftAmountLimitView);
    }

    protected void initSettingOverdraftAllowedBlock() {
        this.settingOverdraftAllowedBlock = new WebMarkupBlock("settingOverdraftAllowedBlock", Size.Six_6);
        add(this.settingOverdraftAllowedBlock);
        this.settingOverdraftAllowedVContainer = new WebMarkupContainer("settingOverdraftAllowedVContainer");
        this.settingOverdraftAllowedBlock.add(this.settingOverdraftAllowedVContainer);
        this.settingOverdraftAllowedView = new ReadOnlyView("settingOverdraftAllowedView", new PropertyModel<>(this, "settingOverdraftAllowedValue"));
        this.settingOverdraftAllowedVContainer.add(this.settingOverdraftAllowedView);
    }

    protected void initSettingMinimumBalanceBlock() {
        this.settingMinimumBalanceBlock = new WebMarkupBlock("settingMinimumBalanceBlock", Size.Four_4);
        add(this.settingMinimumBalanceBlock);
        this.settingMinimumBalanceVContainer = new WebMarkupContainer("settingMinimumBalanceVContainer");
        this.settingMinimumBalanceBlock.add(this.settingMinimumBalanceVContainer);
        this.settingMinimumBalanceView = new ReadOnlyView("settingMinimumBalanceView", new PropertyModel<>(this, "settingMinimumBalanceValue"));
        this.settingMinimumBalanceVContainer.add(this.settingMinimumBalanceView);
    }

    protected void initSettingEnforceMinimumBalanceBlock() {
        this.settingEnforceMinimumBalanceBlock = new WebMarkupBlock("settingEnforceMinimumBalanceBlock", Size.Four_4);
        add(this.settingEnforceMinimumBalanceBlock);
        this.settingEnforceMinimumBalanceVContainer = new WebMarkupContainer("settingEnforceMinimumBalanceVContainer");
        this.settingEnforceMinimumBalanceBlock.add(this.settingEnforceMinimumBalanceVContainer);
        this.settingEnforceMinimumBalanceView = new ReadOnlyView("settingEnforceMinimumBalanceView", new PropertyModel<>(this, "settingEnforceMinimumBalanceValue"));
        this.settingEnforceMinimumBalanceVContainer.add(this.settingEnforceMinimumBalanceView);
    }

    protected void initSettingBalanceRequiredForInterestCalculationBlock() {
        this.settingBalanceRequiredForInterestCalculationBlock = new WebMarkupBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Four_4);
        add(this.settingBalanceRequiredForInterestCalculationBlock);
        this.settingBalanceRequiredForInterestCalculationVContainer = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationVContainer");
        this.settingBalanceRequiredForInterestCalculationBlock.add(this.settingBalanceRequiredForInterestCalculationVContainer);
        this.settingBalanceRequiredForInterestCalculationView = new ReadOnlyView("settingBalanceRequiredForInterestCalculationView", new PropertyModel<>(this, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationVContainer.add(this.settingBalanceRequiredForInterestCalculationView);
    }

    protected void initSettingApplyWithdrawalFeeForTransferBlock() {
        this.settingApplyWithdrawalFeeForTransferBlock = new WebMarkupBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Four_4);
        add(this.settingApplyWithdrawalFeeForTransferBlock);
        this.settingApplyWithdrawalFeeForTransferVContainer = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferVContainer");
        this.settingApplyWithdrawalFeeForTransferBlock.add(this.settingApplyWithdrawalFeeForTransferVContainer);
        this.settingApplyWithdrawalFeeForTransferView = new ReadOnlyView("settingApplyWithdrawalFeeForTransferView", new PropertyModel<>(this, "settingApplyWithdrawalFeeForTransferValue"));
        this.settingApplyWithdrawalFeeForTransferVContainer.add(this.settingApplyWithdrawalFeeForTransferView);
    }

    protected void initSettingLockInTypeBlock() {
        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        add(this.settingLockInTypeBlock);
        this.settingLockInTypeVContainer = new WebMarkupContainer("settingLockInTypeVContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeVContainer);
        this.settingLockInTypeView = new ReadOnlyView("settingLockInTypeView", new PropertyModel<>(this, "settingLockInTypeValue"));
        this.settingLockInTypeVContainer.add(this.settingLockInTypeView);
    }

    protected void initSettingLockInPeriodBlock() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodVContainer = new WebMarkupContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodVContainer);
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);
    }

    protected void initSettingMinimumOpeningBalanceBlock() {
        this.settingMinimumOpeningBalanceBlock = new WebMarkupBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
        add(this.settingMinimumOpeningBalanceBlock);
        this.settingMinimumOpeningBalanceVContainer = new WebMarkupContainer("settingMinimumOpeningBalanceVContainer");
        this.settingMinimumOpeningBalanceBlock.add(this.settingMinimumOpeningBalanceVContainer);
        this.settingMinimumOpeningBalanceView = new ReadOnlyView("settingMinimumOpeningBalanceView", new PropertyModel<>(this, "settingMinimumOpeningBalanceValue"));
        this.settingMinimumOpeningBalanceVContainer.add(this.settingMinimumOpeningBalanceView);
    }

    protected void initSectionTerm() {

        initTermNominalAnnualInterestBlock();

        initTermInterestCompoundingPeriodBlock();

        initTermInterestCalculatedUsingBlock();

        initTermInterestPostingPeriodBlock();

        inittermDayInYearBlock();
    }

    protected void inittermDayInYearBlock() {
        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Six_6);
        add(this.termDayInYearBlock);
        this.termDayInYearVContainer = new WebMarkupContainer("termDayInYearVContainer");
        this.termDayInYearBlock.add(this.termDayInYearVContainer);
        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this, "termDayInYearValue"));
        this.termDayInYearVContainer.add(this.termDayInYearView);
    }

    protected void initTermInterestPostingPeriodBlock() {
        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Six_6);
        add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodVContainer = new WebMarkupContainer("termInterestPostingPeriodVContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodVContainer);
        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this, "termInterestPostingPeriodValue"));
        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);
    }

    protected void initTermInterestCalculatedUsingBlock() {
        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingVContainer = new WebMarkupContainer("termInterestCalculatedUsingVContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingVContainer);
        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this, "termInterestCalculatedUsingValue"));
        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);
    }

    protected void initTermInterestCompoundingPeriodBlock() {
        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodVContainer = new WebMarkupContainer("termInterestCompoundingPeriodVContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodVContainer);
        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this, "termInterestCompoundingPeriodValue"));
        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);
    }

    protected void initTermNominalAnnualInterestBlock() {
        this.termNominalAnnualInterestBlock = new WebMarkupBlock("termNominalAnnualInterestBlock", Size.Six_6);
        add(this.termNominalAnnualInterestBlock);
        this.termNominalAnnualInterestVContainer = new WebMarkupContainer("termNominalAnnualInterestVContainer");
        this.termNominalAnnualInterestBlock.add(this.termNominalAnnualInterestVContainer);
        this.termNominalAnnualInterestView = new ReadOnlyView("termNominalAnnualInterestView", new PropertyModel<>(this, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestVContainer.add(this.termNominalAnnualInterestView);
    }

    protected void initSectionCurrency() {

        initCurrencyCodeBlock();

        initCurrencyDecimalPlaceBlock();

        initCurrencyMultipleOfBlock();
    }

    protected void initCurrencyMultipleOfBlock() {
        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfVContainer = new WebMarkupContainer("currencyMultipleOfVContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfVContainer);
        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);
    }

    protected void initCurrencyDecimalPlaceBlock() {
        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);
    }

    protected void initCurrencyCodeBlock() {
        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);
    }

    protected void initSectionDetail() {
        initDetailProductNameBlock();

        initDetailShortNameBlock();

        initDetailDescriptionBlock();
    }

    protected void initDetailDescriptionBlock() {
        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);
    }

    protected void initDetailShortNameBlock() {
        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);
    }

    protected void initDetailProductNameBlock() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);
    }

}
