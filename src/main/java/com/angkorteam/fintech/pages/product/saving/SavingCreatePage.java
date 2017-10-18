package com.angkorteam.fintech.pages.product.saving;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.SavingBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.helper.SavingHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.saving.ChargePopup;
import com.angkorteam.fintech.popup.saving.FeeChargePopup;
import com.angkorteam.fintech.popup.saving.PenaltyChargePopup;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupContainer detailProductNameBlock;
    protected WebMarkupContainer detailProductNameContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupContainer detailShortNameBlock;
    protected WebMarkupContainer detailShortNameContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupContainer detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupContainer currencyCodeBlock;
    protected WebMarkupContainer currencyCodeContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupContainer currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceContainer;
    protected Integer currencyDecimalPlaceValue;
    protected TextField<Integer> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupContainer currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfContainer;
    protected Integer currencyMultipleOfValue;
    protected TextField<Integer> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    protected WebMarkupContainer termNominalAnnualInterestBlock;
    protected WebMarkupContainer termNominalAnnualInterestContainer;
    protected Double termNominalAnnualInterestValue;
    protected TextField<Double> termNominalAnnualInterestField;
    protected TextFeedbackPanel termNominalAnnualInterestFeedback;

    protected WebMarkupContainer termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Option termInterestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    protected TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    protected WebMarkupContainer termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Option termInterestCalculatedUsingValue;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
    protected TextFeedbackPanel termInterestCalculatedUsingFeedback;

    protected WebMarkupContainer termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Option termInterestPostingPeriodValue;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
    protected TextFeedbackPanel termInterestPostingPeriodFeedback;

    protected WebMarkupContainer termDaysInYearBlock;
    protected WebMarkupContainer termDaysInYearContainer;
    protected DayInYearProvider termDaysInYearProvider;
    protected Option termDaysInYearValue;
    protected Select2SingleChoice<Option> termDaysInYearField;
    protected TextFeedbackPanel termDaysInYearFeedback;

    // Settings

    protected WebMarkupContainer settingMinimumOpeningBalanceBlock;
    protected WebMarkupContainer settingMinimumOpeningBalanceContainer;
    protected Double settingMinimumOpeningBalanceValue;
    protected TextField<Double> settingMinimumOpeningBalanceField;
    protected TextFeedbackPanel settingMinimumOpeningBalanceFeedback;

    protected WebMarkupContainer settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodContainer;
    protected Integer settingLockInPeriodValue;
    protected TextField<Integer> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupContainer settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferContainer;
    protected Boolean settingApplyWithdrawalFeeForTransferValue;
    protected CheckBox settingApplyWithdrawalFeeForTransferField;
    protected TextFeedbackPanel settingApplyWithdrawalFeeForTransferFeedback;

    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationContainer;
    protected Double settingBalanceRequiredForInterestCalculationValue;
    protected TextField<Double> settingBalanceRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingBalanceRequiredForInterestCalculationFeedback;

    protected WebMarkupContainer settingEnforceMinimumBalanceBlock;
    protected WebMarkupContainer settingEnforceMinimumBalanceContainer;
    protected Boolean settingEnforceMinimumBalanceValue;
    protected CheckBox settingEnforceMinimumBalanceField;
    protected TextFeedbackPanel settingEnforceMinimumBalanceFeedback;

    protected WebMarkupContainer settingMinimumBalanceBlock;
    protected WebMarkupContainer settingMinimumBalanceContainer;
    protected Double settingMinimumBalanceValue;
    protected TextField<Double> settingMinimumBalanceField;
    protected TextFeedbackPanel settingMinimumBalanceFeedback;

    protected WebMarkupContainer settingOverdraftAllowedBlock;
    protected WebMarkupContainer settingOverdraftAllowedContainer;
    protected Boolean settingOverdraftAllowedValue;
    protected CheckBox settingOverdraftAllowedField;
    protected TextFeedbackPanel settingOverdraftAllowedFeedback;

    protected WebMarkupContainer settingMaximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer settingMaximumOverdraftAmountLimitContainer;
    protected Double settingMaximumOverdraftAmountLimitValue;
    protected TextField<Double> settingMaximumOverdraftAmountLimitField;
    protected TextFeedbackPanel settingMaximumOverdraftAmountLimitFeedback;

    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftContainer;
    protected Double settingNominalAnnualInterestForOverdraftValue;
    protected TextField<Double> settingNominalAnnualInterestForOverdraftField;
    protected TextFeedbackPanel settingNominalAnnualInterestForOverdraftFeedback;

    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationContainer;
    protected Double settingMinOverdraftRequiredForInterestCalculationValue;
    protected TextField<Double> settingMinOverdraftRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingMinOverdraftRequiredForInterestCalculationFeedback;

    protected WebMarkupContainer settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupContainer settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Option settingTaxGroupValue;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    protected WebMarkupContainer settingEnableDormancyTrackingBlock;
    protected WebMarkupContainer settingEnableDormancyTrackingContainer;
    protected Boolean settingEnableDormancyTrackingValue;
    protected CheckBox settingEnableDormancyTrackingField;
    protected TextFeedbackPanel settingEnableDormancyTrackingFeedback;

    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusContainer;
    protected Integer settingNumberOfDaysToInactiveSubStatusValue;
    protected TextField<Integer> settingNumberOfDaysToInactiveSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToInactiveSubStatusFeedback;

    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusContainer;
    protected Integer settingNumberOfDaysToDormantSubStatusValue;
    protected TextField<Integer> settingNumberOfDaysToDormantSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToDormantSubStatusFeedback;

    protected WebMarkupContainer settingNumberOfDaysToEscheatBlock;
    protected WebMarkupContainer settingNumberOfDaysToEscheatContainer;
    protected Integer settingNumberOfDaysToEscheatValue;
    protected TextField<Integer> settingNumberOfDaysToEscheatField;
    protected TextFeedbackPanel settingNumberOfDaysToEscheatFeedback;

    // Charges

    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;

    // Accounting

    protected String accountingValue = ACC_NONE;
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashContainer;

    protected SingleChoiceProvider cashSavingReferenceProvider;
    protected Option cashSavingReferenceValue;
    protected Select2SingleChoice<Option> cashSavingReferenceField;
    protected TextFeedbackPanel cashSavingReferenceFeedback;

    protected SingleChoiceProvider cashOverdraftPortfolioProvider;
    protected Option cashOverdraftPortfolioValue;
    protected Select2SingleChoice<Option> cashOverdraftPortfolioField;
    protected TextFeedbackPanel cashOverdraftPortfolioFeedback;

    protected SingleChoiceProvider cashSavingControlProvider;
    protected Option cashSavingControlValue;
    protected Select2SingleChoice<Option> cashSavingControlField;
    protected TextFeedbackPanel cashSavingControlFeedback;

    protected SingleChoiceProvider cashSavingsTransfersInSuspenseProvider;
    protected Option cashSavingsTransfersInSuspenseValue;
    protected Select2SingleChoice<Option> cashSavingsTransfersInSuspenseField;
    protected TextFeedbackPanel cashSavingsTransfersInSuspenseFeedback;

    protected WebMarkupContainer cashEscheatLiabilityBlock;
    protected WebMarkupContainer cashEscheatLiabilityContainer;
    protected SingleChoiceProvider cashEscheatLiabilityProvider;
    protected Option cashEscheatLiabilityValue;
    protected Select2SingleChoice<Option> cashEscheatLiabilityField;
    protected TextFeedbackPanel cashEscheatLiabilityFeedback;

    protected SingleChoiceProvider cashInterestOnSavingProvider;
    protected Option cashInterestOnSavingValue;
    protected Select2SingleChoice<Option> cashInterestOnSavingField;
    protected TextFeedbackPanel cashInterestOnSavingFeedback;

    protected SingleChoiceProvider cashWriteOffProvider;
    protected Option cashWriteOffValue;
    protected Select2SingleChoice<Option> cashWriteOffField;
    protected TextFeedbackPanel cashWriteOffFeedback;

    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Option cashIncomeFromFeeValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    protected SingleChoiceProvider cashIncomeFromPenaltiesProvider;
    protected Option cashIncomeFromPenaltiesValue;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltiesField;
    protected TextFeedbackPanel cashIncomeFromPenaltiesFeedback;

    protected SingleChoiceProvider cashOverdraftInterestIncomeProvider;
    protected Option cashOverdraftInterestIncomeValue;
    protected Select2SingleChoice<Option> cashOverdraftInterestIncomeField;
    protected TextFeedbackPanel cashOverdraftInterestIncomeFeedback;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleContainer;

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    protected ModalWindow fundSourcePopup;

    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    protected ModalWindow feeIncomePopup;

    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    protected ModalWindow penaltyIncomePopup;

    protected Option itemChargeValue;
    protected Option itemPaymentValue;
    protected Option itemAccountValue;

    protected ModalWindow currencyPopup;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        this.currencyPopup = new ModalWindow("currencyPopup");
        add(this.currencyPopup);

        initDetail();

        initCurrency();

        initTerm();

        initSetting();

        initCharge();

        initAccounting();

        initDefault();

        initValidationRule();
    }

    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2;
        this.currencyMultipleOfValue = 1;
        this.termNominalAnnualInterestValue = 10d;
        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Daily.toOption();
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.DailyBalance.toOption();
        this.termInterestPostingPeriodValue = InterestPostingPeriod.Monthly.toOption();
        this.termDaysInYearValue = DayInYear.D365.toOption();
        this.accountingValue = ACC_NONE;
    }

    protected void initValidationRule() {
        this.detailProductNameField.setRequired(true);
        this.detailShortNameField.setRequired(true);
        this.detailDescriptionField.setRequired(true);
        this.currencyCodeField.setRequired(true);
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyMultipleOfField.setRequired(true);
        this.termNominalAnnualInterestField.setRequired(true);
        this.termInterestCompoundingPeriodField.setRequired(true);
        this.termInterestCalculatedUsingField.setRequired(true);
        this.termDaysInYearField.setRequired(true);
        this.accountingField.setRequired(true);
        this.termInterestPostingPeriodField.setRequired(true);
    }

    protected void initDefault() {

        settingOverdraftAllowedFieldUpdate(null);

        settingWithholdTaxApplicableFieldUpdate(null);

        settingEnableDormancyTrackingFieldUpdate(null);

        accountingFieldUpdate(null);
    }

    protected void feeIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRuleFeeIncomeValue.add(item);
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void penaltyIncomePopupOnClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("charge", this.itemChargeValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRulePenaltyIncomeValue.add(item);
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected void fundSourcePopupOnClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("paymentId", this.itemPaymentValue.getId());
        item.put("payment", this.itemPaymentValue.getText());
        item.put("accountId", this.itemAccountValue.getId());
        item.put("account", this.itemAccountValue.getText());
        this.advancedAccountingRuleFundSourceValue.add(item);
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected void initAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(ACC_NONE)));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(ACC_CASH)));
        this.form.add(this.accountingField);

        initAccountingCash();

        initAdvancedAccountingRule();

    }

    protected void initAdvancedAccountingRule() {
        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleContainer = new WebMarkupContainer("advancedAccountingRuleContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleContainer);

        // Table
        {
            this.fundSourcePopup = new ModalWindow("fundSourcePopup");
            add(this.fundSourcePopup);
            this.fundSourcePopup.setOnClose(this::fundSourcePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
            advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourcePaymentColumn));
            advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceAccountColumn));
            advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceActionItem, this::advancedAccountingRuleFundSourceActionClick));
            this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
            this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceTable);
            this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
            this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

            this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
            this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceAddLink);
        }

        // Table
        {
            this.feeIncomePopup = new ModalWindow("feeIncomePopup");
            add(this.feeIncomePopup);
            this.feeIncomePopup.setOnClose(this::feeIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
            advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeChargeColumn));
            advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeAccountColumn));
            advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeActionItem, this::advancedAccountingRuleFeeIncomeActionClick));
            this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
            this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeTable);
            this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
            this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

            this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
            this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
        }

        // Table
        {
            this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
            add(this.penaltyIncomePopup);
            this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupOnClose);

            List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
            advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeChargeColumn));
            advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeAccountColumn));
            advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeActionItem, this::advancedAccountingRulePenaltyIncomeActionClick));
            this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
            this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
            this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
            this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

            this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
            this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
            this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);
        }
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        if (this.currencyCodeValue != null) {
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup(this.penaltyIncomePopup.getContentId(), this.penaltyIncomePopup, this, this.currencyCodeValue.getId()));
            this.penaltyIncomePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void advancedAccountingRulePenaltyIncomeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRulePenaltyIncomeValue.remove(index);
        }
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemAccountValue = null;
        if (this.currencyCodeValue != null) {
            this.feeIncomePopup.setContent(new FeeChargePopup(this.feeIncomePopup.getContentId(), this.feeIncomePopup, this, this.currencyCodeValue.getId()));
            this.feeIncomePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeChargeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void advancedAccountingRuleFeeIncomeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFeeIncomeValue.remove(index);
        }
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void advancedAccountingRuleFundSourceActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFundSourceValue.remove(index);
        }
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected List<ActionItem> advancedAccountingRuleFundSourceActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.fundSourcePopup.setContent(new PaymentTypePopup(this.fundSourcePopup.getContentId(), this.fundSourcePopup, this));
        this.fundSourcePopup.show(target);
        return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourcePaymentColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel advancedAccountingRuleFundSourceAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashContainer = new WebMarkupContainer("cashContainer");
        this.cashBlock.add(this.cashContainer);

        this.cashSavingReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingReferenceField);
        this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback", this.cashSavingReferenceField);
        this.cashContainer.add(this.cashSavingReferenceFeedback);

        this.cashOverdraftPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverdraftPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashOverdraftPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashOverdraftPortfolioField = new Select2SingleChoice<>("cashOverdraftPortfolioField", new PropertyModel<>(this, "cashOverdraftPortfolioValue"), this.cashOverdraftPortfolioProvider);
        this.cashOverdraftPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashOverdraftPortfolioField);
        this.cashOverdraftPortfolioFeedback = new TextFeedbackPanel("cashOverdraftPortfolioFeedback", this.cashOverdraftPortfolioField);
        this.cashContainer.add(this.cashOverdraftPortfolioFeedback);

        this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingControlField);
        this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback", this.cashSavingControlField);
        this.cashContainer.add(this.cashSavingControlFeedback);

        this.cashSavingsTransfersInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingsTransfersInSuspenseField = new Select2SingleChoice<>("cashSavingsTransfersInSuspenseField", new PropertyModel<>(this, "cashSavingsTransfersInSuspenseValue"), this.cashSavingsTransfersInSuspenseProvider);
        this.cashSavingsTransfersInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashSavingsTransfersInSuspenseField);
        this.cashSavingsTransfersInSuspenseFeedback = new TextFeedbackPanel("cashSavingsTransfersInSuspenseFeedback", this.cashSavingsTransfersInSuspenseField);
        this.cashContainer.add(this.cashSavingsTransfersInSuspenseFeedback);

        this.cashEscheatLiabilityBlock = new WebMarkupContainer("cashEscheatLiabilityBlock");
        this.cashEscheatLiabilityBlock.setOutputMarkupId(true);
        this.cashContainer.add(cashEscheatLiabilityBlock);
        this.cashEscheatLiabilityContainer = new WebMarkupContainer("cashEscheatLiabilityContainer");
        this.cashEscheatLiabilityBlock.add(this.cashEscheatLiabilityContainer);
        this.cashEscheatLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashEscheatLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashEscheatLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashEscheatLiabilityField = new Select2SingleChoice<>("cashEscheatLiabilityField", new PropertyModel<>(this, "cashEscheatLiabilityValue"), this.cashEscheatLiabilityProvider);
        this.cashEscheatLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashEscheatLiabilityContainer.add(this.cashEscheatLiabilityField);
        this.cashEscheatLiabilityFeedback = new TextFeedbackPanel("cashEscheatLiabilityFeedback", this.cashEscheatLiabilityField);
        this.cashEscheatLiabilityContainer.add(this.cashEscheatLiabilityFeedback);

        this.cashInterestOnSavingProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashInterestOnSavingProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashInterestOnSavingProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField", new PropertyModel<>(this, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
        this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashInterestOnSavingField);
        this.cashInterestOnSavingFeedback = new TextFeedbackPanel("cashInterestOnSavingFeedback", this.cashInterestOnSavingField);
        this.cashContainer.add(this.cashInterestOnSavingFeedback);

        this.cashWriteOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashWriteOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashWriteOffProvider.applyWhere("classification_enum", "classification_enum  = " + AccountType.Expense.getLiteral());
        this.cashWriteOffField = new Select2SingleChoice<>("cashWriteOffField", new PropertyModel<>(this, "cashWriteOffValue"), this.cashWriteOffProvider);
        this.cashWriteOffField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashWriteOffField);
        this.cashWriteOffFeedback = new TextFeedbackPanel("cashWriteOffFeedback", this.cashWriteOffField);
        this.cashContainer.add(this.cashWriteOffFeedback);

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashContainer.add(this.cashIncomeFromFeeFeedback);

        this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField", new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
        this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromPenaltiesField);
        this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback", this.cashIncomeFromPenaltiesField);
        this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);

        this.cashOverdraftInterestIncomeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverdraftInterestIncomeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashOverdraftInterestIncomeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashOverdraftInterestIncomeField = new Select2SingleChoice<>("cashOverdraftInterestIncomeField", new PropertyModel<>(this, "cashOverdraftInterestIncomeValue"), this.cashOverdraftInterestIncomeProvider);
        this.cashOverdraftInterestIncomeField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashOverdraftInterestIncomeField);
        this.cashOverdraftInterestIncomeFeedback = new TextFeedbackPanel("cashOverdraftInterestIncomeFeedback", this.cashOverdraftInterestIncomeField);
        this.cashContainer.add(this.cashOverdraftInterestIncomeFeedback);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashContainer.setVisible(false);
        this.advancedAccountingRuleContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initCharge() {

        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newArrayList();
        chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeNameColumn));
        chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeTypeColumn));
        chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeAmountColumn));
        chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeCollectColumn));
        chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeDateColumn));
        chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeActionItem, this::chargeActionClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);
    }

    protected void chargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String chargeId = this.itemChargeValue.getId();
        for (Map<String, Object> temp : this.chargeValue) {
            if (chargeId.equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?", chargeId);
        String type = (String) chargeObject.get("type");
        for (ChargeCalculation calculation : ChargeCalculation.values()) {
            if (type.equals(calculation.getLiteral())) {
                type = calculation.getDescription();
                break;
            }
        }
        String collect = (String) chargeObject.get("collect");
        for (ChargeTime time : ChargeTime.values()) {
            if (collect.equals(time.getLiteral())) {
                collect = time.getDescription();
                break;
            }
        }
        item.put("uuid", chargeId);
        item.put("chargeId", chargeId);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void chargeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.size(); i++) {
            Map<String, Object> column = this.chargeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.remove(index);
        }
        target.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected void initSetting() {
        this.settingMinimumOpeningBalanceBlock = new WebMarkupContainer("settingMinimumOpeningBalanceBlock");
        this.form.add(this.settingMinimumOpeningBalanceBlock);
        this.settingMinimumOpeningBalanceContainer = new WebMarkupContainer("settingMinimumOpeningBalanceContainer");
        this.settingMinimumOpeningBalanceBlock.add(this.settingMinimumOpeningBalanceContainer);
        this.settingMinimumOpeningBalanceField = new TextField<>("settingMinimumOpeningBalanceField", new PropertyModel<>(this, "settingMinimumOpeningBalanceValue"));
        this.settingMinimumOpeningBalanceField.setLabel(Model.of("Minimum opening balance"));
        this.settingMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumOpeningBalanceContainer.add(this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceFeedback = new TextFeedbackPanel("settingMinimumOpeningBalanceFeedback", this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceContainer.add(this.settingMinimumOpeningBalanceFeedback);

        this.settingLockInPeriodBlock = new WebMarkupContainer("settingLockInPeriodBlock");
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodContainer = new WebMarkupContainer("settingLockInPeriodContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupContainer("settingLockInTypeBlock");
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeContainer = new WebMarkupContainer("settingLockInTypeContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0, new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeContainer.add(this.settingLockInTypeFeedback);

        this.settingApplyWithdrawalFeeForTransferBlock = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferBlock");
        this.form.add(this.settingApplyWithdrawalFeeForTransferBlock);
        this.settingApplyWithdrawalFeeForTransferContainer = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferContainer");
        this.settingApplyWithdrawalFeeForTransferBlock.add(this.settingApplyWithdrawalFeeForTransferContainer);
        this.settingApplyWithdrawalFeeForTransferField = new CheckBox("settingApplyWithdrawalFeeForTransferField", new PropertyModel<>(this, "settingApplyWithdrawalFeeForTransferValue"));
        this.settingApplyWithdrawalFeeForTransferField.add(new OnChangeAjaxBehavior());
        this.settingApplyWithdrawalFeeForTransferContainer.add(this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel("settingApplyWithdrawalFeeForTransferFeedback", this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferContainer.add(this.settingApplyWithdrawalFeeForTransferFeedback);

        this.settingBalanceRequiredForInterestCalculationBlock = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationBlock");
        this.form.add(this.settingBalanceRequiredForInterestCalculationBlock);
        this.settingBalanceRequiredForInterestCalculationContainer = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationContainer");
        this.settingBalanceRequiredForInterestCalculationBlock.add(this.settingBalanceRequiredForInterestCalculationContainer);
        this.settingBalanceRequiredForInterestCalculationField = new TextField<>("settingBalanceRequiredForInterestCalculationField", new PropertyModel<>(this, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationField.setLabel(Model.of("Balance Required For Interest Calculation"));
        this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingBalanceRequiredForInterestCalculationContainer.add(this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingBalanceRequiredForInterestCalculationFeedback", this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationContainer.add(this.settingBalanceRequiredForInterestCalculationFeedback);

        this.settingEnforceMinimumBalanceBlock = new WebMarkupContainer("settingEnforceMinimumBalanceBlock");
        this.form.add(this.settingEnforceMinimumBalanceBlock);
        this.settingEnforceMinimumBalanceContainer = new WebMarkupContainer("settingEnforceMinimumBalanceContainer");
        this.settingEnforceMinimumBalanceBlock.add(this.settingEnforceMinimumBalanceContainer);
        this.settingEnforceMinimumBalanceField = new CheckBox("settingEnforceMinimumBalanceField", new PropertyModel<>(this, "settingEnforceMinimumBalanceValue"));
        this.settingEnforceMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingEnforceMinimumBalanceContainer.add(this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceFeedback = new TextFeedbackPanel("settingEnforceMinimumBalanceFeedback", this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceContainer.add(this.settingEnforceMinimumBalanceFeedback);

        this.settingMinimumBalanceBlock = new WebMarkupContainer("settingMinimumBalanceBlock");
        this.form.add(this.settingMinimumBalanceBlock);
        this.settingMinimumBalanceContainer = new WebMarkupContainer("settingMinimumBalanceContainer");
        this.settingMinimumBalanceBlock.add(this.settingMinimumBalanceContainer);
        this.settingMinimumBalanceField = new TextField<>("settingMinimumBalanceField", new PropertyModel<>(this, "settingMinimumBalanceValue"));
        this.settingMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumBalanceField.setLabel(Model.of("Minimum balance"));
        this.settingMinimumBalanceContainer.add(this.settingMinimumBalanceField);
        this.settingMinimumBalanceFeedback = new TextFeedbackPanel("settingMinimumBalanceFeedback", this.settingMinimumBalanceField);
        this.settingMinimumBalanceContainer.add(this.settingMinimumBalanceFeedback);

        this.settingOverdraftAllowedBlock = new WebMarkupContainer("settingOverdraftAllowedBlock");
        this.form.add(this.settingOverdraftAllowedBlock);
        this.settingOverdraftAllowedContainer = new WebMarkupContainer("settingOverdraftAllowedContainer");
        this.settingOverdraftAllowedBlock.add(this.settingOverdraftAllowedContainer);
        this.settingOverdraftAllowedField = new CheckBox("settingOverdraftAllowedField", new PropertyModel<>(this, "settingOverdraftAllowedValue"));
        this.settingOverdraftAllowedField.add(new OnChangeAjaxBehavior(this::settingOverdraftAllowedFieldUpdate));
        this.settingOverdraftAllowedContainer.add(this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedFeedback = new TextFeedbackPanel("settingOverdraftAllowedFeedback", this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedContainer.add(this.settingOverdraftAllowedFeedback);

        this.settingMaximumOverdraftAmountLimitBlock = new WebMarkupContainer("settingMaximumOverdraftAmountLimitBlock");
        this.settingMaximumOverdraftAmountLimitBlock.setOutputMarkupId(true);
        this.form.add(this.settingMaximumOverdraftAmountLimitBlock);
        this.settingMaximumOverdraftAmountLimitContainer = new WebMarkupContainer("settingMaximumOverdraftAmountLimitContainer");
        this.settingMaximumOverdraftAmountLimitBlock.add(this.settingMaximumOverdraftAmountLimitContainer);
        this.settingMaximumOverdraftAmountLimitField = new TextField<>("settingMaximumOverdraftAmountLimitField", new PropertyModel<>(this, "settingMaximumOverdraftAmountLimitValue"));
        this.settingMaximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
        this.settingMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
        this.settingMaximumOverdraftAmountLimitContainer.add(this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitFeedback = new TextFeedbackPanel("settingMaximumOverdraftAmountLimitFeedback", this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitContainer.add(this.settingMaximumOverdraftAmountLimitFeedback);

        this.settingNominalAnnualInterestForOverdraftBlock = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftBlock");
        this.settingNominalAnnualInterestForOverdraftBlock.setOutputMarkupId(true);
        this.form.add(this.settingNominalAnnualInterestForOverdraftBlock);
        this.settingNominalAnnualInterestForOverdraftContainer = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftContainer");
        this.settingNominalAnnualInterestForOverdraftBlock.add(this.settingNominalAnnualInterestForOverdraftContainer);
        this.settingNominalAnnualInterestForOverdraftField = new TextField<>("settingNominalAnnualInterestForOverdraftField", new PropertyModel<>(this, "settingNominalAnnualInterestForOverdraftValue"));
        this.settingNominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal annual interest for overdraft"));
        this.settingNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
        this.settingNominalAnnualInterestForOverdraftContainer.add(this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel("settingNominalAnnualInterestForOverdraftFeedback", this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftContainer.add(this.settingNominalAnnualInterestForOverdraftFeedback);

        this.settingMinOverdraftRequiredForInterestCalculationBlock = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationBlock");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.setOutputMarkupId(true);
        this.form.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        this.settingMinOverdraftRequiredForInterestCalculationContainer = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationContainer");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.add(this.settingMinOverdraftRequiredForInterestCalculationContainer);
        this.settingMinOverdraftRequiredForInterestCalculationField = new TextField<>("settingMinOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this, "settingMinOverdraftRequiredForInterestCalculationValue"));
        this.settingMinOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
        this.settingMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingMinOverdraftRequiredForInterestCalculationContainer.add(this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingMinOverdraftRequiredForInterestCalculationFeedback", this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationContainer.add(this.settingMinOverdraftRequiredForInterestCalculationFeedback);

        this.settingWithholdTaxApplicableBlock = new WebMarkupContainer("settingWithholdTaxApplicableBlock");
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableContainer = new WebMarkupContainer("settingWithholdTaxApplicableContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableContainer.add(this.settingWithholdTaxApplicableFeedback);

        this.settingTaxGroupBlock = new WebMarkupContainer("settingTaxGroupBlock");
        this.settingTaxGroupBlock.setOutputMarkupId(true);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupContainer = new WebMarkupContainer("settingTaxGroupContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", 0, new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupContainer.add(this.settingTaxGroupFeedback);

        this.settingEnableDormancyTrackingBlock = new WebMarkupContainer("settingEnableDormancyTrackingBlock");
        this.form.add(this.settingEnableDormancyTrackingBlock);
        this.settingEnableDormancyTrackingContainer = new WebMarkupContainer("settingEnableDormancyTrackingContainer");
        this.settingEnableDormancyTrackingBlock.add(this.settingEnableDormancyTrackingContainer);
        this.settingEnableDormancyTrackingField = new CheckBox("settingEnableDormancyTrackingField", new PropertyModel<>(this, "settingEnableDormancyTrackingValue"));
        this.settingEnableDormancyTrackingField.add(new OnChangeAjaxBehavior(this::settingEnableDormancyTrackingFieldUpdate));
        this.settingEnableDormancyTrackingContainer.add(this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingFeedback = new TextFeedbackPanel("settingEnableDormancyTrackingFeedback", this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingContainer.add(this.settingEnableDormancyTrackingFeedback);

        this.settingNumberOfDaysToInactiveSubStatusBlock = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusBlock");
        this.settingNumberOfDaysToInactiveSubStatusBlock.setOutputMarkupId(true);
        this.form.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
        this.settingNumberOfDaysToInactiveSubStatusContainer = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusContainer");
        this.settingNumberOfDaysToInactiveSubStatusBlock.add(this.settingNumberOfDaysToInactiveSubStatusContainer);
        this.settingNumberOfDaysToInactiveSubStatusField = new TextField<>("settingNumberOfDaysToInactiveSubStatusField", new PropertyModel<>(this, "settingNumberOfDaysToInactiveSubStatusValue"));
        this.settingNumberOfDaysToInactiveSubStatusField.setLabel(Model.of("Number of Days to Inactive sub-status"));
        this.settingNumberOfDaysToInactiveSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToInactiveSubStatusContainer.add(this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToInactiveSubStatusFeedback", this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusContainer.add(this.settingNumberOfDaysToInactiveSubStatusFeedback);

        this.settingNumberOfDaysToDormantSubStatusBlock = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusBlock");
        this.settingNumberOfDaysToDormantSubStatusBlock.setOutputMarkupId(true);
        this.form.add(this.settingNumberOfDaysToDormantSubStatusBlock);
        this.settingNumberOfDaysToDormantSubStatusContainer = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusContainer");
        this.settingNumberOfDaysToDormantSubStatusBlock.add(this.settingNumberOfDaysToDormantSubStatusContainer);
        this.settingNumberOfDaysToDormantSubStatusField = new TextField<>("settingNumberOfDaysToDormantSubStatusField", new PropertyModel<>(this, "settingNumberOfDaysToDormantSubStatusValue"));
        this.settingNumberOfDaysToDormantSubStatusField.setLabel(Model.of("Number of Days to Dormant sub-status"));
        this.settingNumberOfDaysToDormantSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToDormantSubStatusContainer.add(this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToDormantSubStatusFeedback", this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusContainer.add(this.settingNumberOfDaysToDormantSubStatusFeedback);

        this.settingNumberOfDaysToEscheatBlock = new WebMarkupContainer("settingNumberOfDaysToEscheatBlock");
        this.settingNumberOfDaysToEscheatBlock.setOutputMarkupId(true);
        this.form.add(this.settingNumberOfDaysToEscheatBlock);
        this.settingNumberOfDaysToEscheatContainer = new WebMarkupContainer("settingNumberOfDaysToEscheatContainer");
        this.settingNumberOfDaysToEscheatBlock.add(this.settingNumberOfDaysToEscheatContainer);
        this.settingNumberOfDaysToEscheatField = new TextField<>("settingNumberOfDaysToEscheatField", new PropertyModel<>(this, "settingNumberOfDaysToEscheatValue"));
        this.settingNumberOfDaysToEscheatField.setLabel(Model.of("Number of Days to Escheat"));
        this.settingNumberOfDaysToEscheatField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToEscheatContainer.add(this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatFeedback = new TextFeedbackPanel("settingNumberOfDaysToEscheatFeedback", this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatContainer.add(this.settingNumberOfDaysToEscheatFeedback);
    }

    protected void initTerm() {

        this.termNominalAnnualInterestBlock = new WebMarkupContainer("termNominalAnnualInterestBlock");
        this.form.add(this.termNominalAnnualInterestBlock);
        this.termNominalAnnualInterestContainer = new WebMarkupContainer("termNominalAnnualInterestContainer");
        this.termNominalAnnualInterestBlock.add(this.termNominalAnnualInterestContainer);
        this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField", new PropertyModel<>(this, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestField.setLabel(Model.of("Nominal annual interest"));
        this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());
        this.termNominalAnnualInterestContainer.add(this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestFeedback = new TextFeedbackPanel("termNominalAnnualInterestFeedback", this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestContainer.add(this.termNominalAnnualInterestFeedback);

        this.termInterestCompoundingPeriodBlock = new WebMarkupContainer("termInterestCompoundingPeriodBlock");
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodContainer = new WebMarkupContainer("termInterestCompoundingPeriodContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodContainer);
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", 0, new PropertyModel<>(this, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodContainer.add(this.termInterestCompoundingPeriodFeedback);

        this.termInterestCalculatedUsingBlock = new WebMarkupContainer("termInterestCalculatedUsingBlock");
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingContainer = new WebMarkupContainer("termInterestCalculatedUsingContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingContainer);
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", 0, new PropertyModel<>(this, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest posting period"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingContainer.add(this.termInterestCalculatedUsingFeedback);

        this.termInterestPostingPeriodBlock = new WebMarkupContainer("termInterestPostingPeriodBlock");
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodContainer = new WebMarkupContainer("termInterestPostingPeriodContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodContainer);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", 0, new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest calculated using"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodContainer.add(this.termInterestPostingPeriodFeedback);

        this.termDaysInYearBlock = new WebMarkupContainer("termDaysInYearBlock");
        this.form.add(this.termDaysInYearBlock);
        this.termDaysInYearContainer = new WebMarkupContainer("termDaysInYearContainer");
        this.termDaysInYearBlock.add(this.termDaysInYearContainer);
        this.termDaysInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termDaysInYearField = new Select2SingleChoice<>("termDaysInYearField", 0, new PropertyModel<>(this, "termDaysInYearValue"), this.termDaysInYearProvider);
        this.termDaysInYearField.setLabel(Model.of("Days in year"));
        this.termDaysInYearField.add(new OnChangeAjaxBehavior());
        this.termDaysInYearContainer.add(this.termDaysInYearField);
        this.termDaysInYearFeedback = new TextFeedbackPanel("termDaysInYearFeedback", this.termDaysInYearField);
        this.termDaysInYearContainer.add(this.termDaysInYearFeedback);
    }

    protected void initCurrency() {

        this.currencyCodeBlock = new WebMarkupContainer("currencyCodeBlock");
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeContainer = new WebMarkupContainer("currencyCodeContainer");
        this.currencyCodeBlock.add(this.currencyCodeContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupContainer("currencyDecimalPlaceBlock");
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceContainer = new WebMarkupContainer("currencyDecimalPlaceContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyMultipleOfBlock = new WebMarkupContainer("currencyMultipleOfBlock");
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfContainer = new WebMarkupContainer("currencyMultipleOfContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initDetail() {
        this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
        this.detailProductNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
        this.detailProductNameBlock.add(this.detailProductNameContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameContainer.add(this.detailProductNameFeedback);

        this.detailShortNameBlock = new WebMarkupContainer("detailShortNameBlock");
        this.detailShortNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameContainer = new WebMarkupContainer("detailShortNameContainer");
        this.detailShortNameBlock.add(this.detailShortNameContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameContainer.add(this.detailShortNameFeedback);

        this.detailDescriptionBlock = new WebMarkupContainer("detailDescriptionBlock");
        this.detailDescriptionBlock.setOutputMarkupId(true);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionContainer = new WebMarkupContainer("detailDescriptionContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionContainer.add(this.detailDescriptionFeedback);
    }

    protected boolean settingOverdraftAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingOverdraftAllowedValue != null && this.settingOverdraftAllowedValue;
        this.settingMaximumOverdraftAmountLimitContainer.setVisible(visible);
        this.settingNominalAnnualInterestForOverdraftContainer.setVisible(visible);
        this.settingMinOverdraftRequiredForInterestCalculationContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingMaximumOverdraftAmountLimitBlock);
            target.add(this.settingNominalAnnualInterestForOverdraftBlock);
            target.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        }
        return false;
    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingWithholdTaxApplicableValue != null && this.settingWithholdTaxApplicableValue;
        this.settingTaxGroupContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected boolean settingEnableDormancyTrackingFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingEnableDormancyTrackingValue != null && this.settingEnableDormancyTrackingValue;
        this.settingNumberOfDaysToInactiveSubStatusContainer.setVisible(visible);
        this.settingNumberOfDaysToDormantSubStatusContainer.setVisible(visible);
        this.settingNumberOfDaysToEscheatContainer.setVisible(visible);
        this.cashEscheatLiabilityContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
            target.add(this.settingNumberOfDaysToDormantSubStatusBlock);
            target.add(this.settingNumberOfDaysToEscheatBlock);
            target.add(this.cashEscheatLiabilityBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        SavingBuilder builder = new SavingBuilder();

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
        if (this.termDaysInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.termDaysInYearValue.getId()));
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
                builder.withCharges((String) item.get("chargeId"));
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (ACC_NONE.equals(accounting)) {
            builder.withAccountingRule(1);
        } else if (ACC_CASH.equals(accounting)) {
            builder.withAccountingRule(2);
        }

        if (ACC_CASH.equals(accounting)) {
            if (this.cashSavingReferenceValue != null) {
                builder.withSavingsReferenceAccountId(this.cashSavingReferenceValue.getId());
            }
            if (this.cashOverdraftPortfolioValue != null) {
                builder.withOverdraftPortfolioControlId(this.cashOverdraftPortfolioValue.getId());
            }
            if (this.cashSavingControlValue != null) {
                builder.withSavingsControlAccountId(this.cashSavingControlValue.getId());
            }
            if (this.cashSavingsTransfersInSuspenseValue != null) {
                builder.withTransfersInSuspenseAccountId(this.cashSavingsTransfersInSuspenseValue.getId());
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
            if (this.cashIncomeFromPenaltiesValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltiesValue.getId());
            }
            if (this.cashOverdraftInterestIncomeValue != null) {
                builder.withIncomeFromInterestId(this.cashOverdraftInterestIncomeValue.getId());
            }
        }

        if (ACC_CASH.equals(accounting)) {
            if (this.advancedAccountingRuleFundSourceValue != null && !this.advancedAccountingRuleFundSourceValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFundSourceValue) {
                    builder.withPaymentChannelToFundSourceMappings((String) item.get("paymentId"), (String) item.get("accountId"));
                }
            }
            if (this.advancedAccountingRuleFeeIncomeValue != null && !this.advancedAccountingRuleFeeIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRuleFeeIncomeValue) {
                    builder.withFeeToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
                }
            }
            if (this.advancedAccountingRulePenaltyIncomeValue != null && !this.advancedAccountingRulePenaltyIncomeValue.isEmpty()) {
                for (Map<String, Object> item : this.advancedAccountingRulePenaltyIncomeValue) {
                    builder.withPenaltyToIncomeAccountMappings((String) item.get("chargeId"), (String) item.get("accountId"));
                }
            }
        }

        JsonNode node = null;
        try {
            node = SavingHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(SavingBrowsePage.class);
    }

}