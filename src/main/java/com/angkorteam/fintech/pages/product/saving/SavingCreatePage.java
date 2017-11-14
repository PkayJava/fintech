package com.angkorteam.fintech.pages.product.saving;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
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

import com.angkorteam.fintech.DeprecatedPage;
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
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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
public class SavingCreatePage extends DeprecatedPage {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameIContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameIContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionIContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeIContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceIContainer;
    protected Integer currencyDecimalPlaceValue;
    protected TextField<Integer> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfIContainer;
    protected Integer currencyMultipleOfValue;
    protected TextField<Integer> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    protected WebMarkupBlock termNominalAnnualInterestBlock;
    protected WebMarkupContainer termNominalAnnualInterestIContainer;
    protected Double termNominalAnnualInterestValue;
    protected TextField<Double> termNominalAnnualInterestField;
    protected TextFeedbackPanel termNominalAnnualInterestFeedback;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodIContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Option termInterestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    protected TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingIContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Option termInterestCalculatedUsingValue;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
    protected TextFeedbackPanel termInterestCalculatedUsingFeedback;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodIContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Option termInterestPostingPeriodValue;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
    protected TextFeedbackPanel termInterestPostingPeriodFeedback;

    protected WebMarkupBlock termDaysInYearBlock;
    protected WebMarkupContainer termDaysInYearIContainer;
    protected DayInYearProvider termDaysInYearProvider;
    protected Option termDaysInYearValue;
    protected Select2SingleChoice<Option> termDaysInYearField;
    protected TextFeedbackPanel termDaysInYearFeedback;

    // Settings

    protected WebMarkupBlock settingMinimumOpeningBalanceBlock;
    protected WebMarkupContainer settingMinimumOpeningBalanceIContainer;
    protected Double settingMinimumOpeningBalanceValue;
    protected TextField<Double> settingMinimumOpeningBalanceField;
    protected TextFeedbackPanel settingMinimumOpeningBalanceFeedback;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected Integer settingLockInPeriodValue;
    protected TextField<Integer> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingApplyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferIContainer;
    protected Boolean settingApplyWithdrawalFeeForTransferValue;
    protected CheckBox settingApplyWithdrawalFeeForTransferField;
    protected TextFeedbackPanel settingApplyWithdrawalFeeForTransferFeedback;

    protected WebMarkupBlock settingBalanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationIContainer;
    protected Double settingBalanceRequiredForInterestCalculationValue;
    protected TextField<Double> settingBalanceRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingBalanceRequiredForInterestCalculationFeedback;

    protected WebMarkupBlock settingEnforceMinimumBalanceBlock;
    protected WebMarkupContainer settingEnforceMinimumBalanceIContainer;
    protected Boolean settingEnforceMinimumBalanceValue;
    protected CheckBox settingEnforceMinimumBalanceField;
    protected TextFeedbackPanel settingEnforceMinimumBalanceFeedback;

    protected WebMarkupBlock settingMinimumBalanceBlock;
    protected WebMarkupContainer settingMinimumBalanceIContainer;
    protected Double settingMinimumBalanceValue;
    protected TextField<Double> settingMinimumBalanceField;
    protected TextFeedbackPanel settingMinimumBalanceFeedback;

    protected WebMarkupBlock settingOverdraftAllowedBlock;
    protected WebMarkupContainer settingOverdraftAllowedIContainer;
    protected Boolean settingOverdraftAllowedValue;
    protected CheckBox settingOverdraftAllowedField;
    protected TextFeedbackPanel settingOverdraftAllowedFeedback;

    protected WebMarkupBlock settingMaximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer settingMaximumOverdraftAmountLimitIContainer;
    protected Double settingMaximumOverdraftAmountLimitValue;
    protected TextField<Double> settingMaximumOverdraftAmountLimitField;
    protected TextFeedbackPanel settingMaximumOverdraftAmountLimitFeedback;

    protected WebMarkupBlock settingNominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftIContainer;
    protected Double settingNominalAnnualInterestForOverdraftValue;
    protected TextField<Double> settingNominalAnnualInterestForOverdraftField;
    protected TextFeedbackPanel settingNominalAnnualInterestForOverdraftFeedback;

    protected WebMarkupBlock settingMinOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationIContainer;
    protected Double settingMinOverdraftRequiredForInterestCalculationValue;
    protected TextField<Double> settingMinOverdraftRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingMinOverdraftRequiredForInterestCalculationFeedback;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableIContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupIContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Option settingTaxGroupValue;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    protected WebMarkupBlock settingEnableDormancyTrackingBlock;
    protected WebMarkupContainer settingEnableDormancyTrackingIContainer;
    protected Boolean settingEnableDormancyTrackingValue;
    protected CheckBox settingEnableDormancyTrackingField;
    protected TextFeedbackPanel settingEnableDormancyTrackingFeedback;

    protected WebMarkupBlock settingNumberOfDaysToInactiveSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusIContainer;
    protected Integer settingNumberOfDaysToInactiveSubStatusValue;
    protected TextField<Integer> settingNumberOfDaysToInactiveSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToInactiveSubStatusFeedback;

    protected WebMarkupBlock settingNumberOfDaysToDormantSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusIContainer;
    protected Integer settingNumberOfDaysToDormantSubStatusValue;
    protected TextField<Integer> settingNumberOfDaysToDormantSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToDormantSubStatusFeedback;

    protected WebMarkupBlock settingNumberOfDaysToEscheatBlock;
    protected WebMarkupContainer settingNumberOfDaysToEscheatIContainer;
    protected Integer settingNumberOfDaysToEscheatValue;
    protected TextField<Integer> settingNumberOfDaysToEscheatField;
    protected TextFeedbackPanel settingNumberOfDaysToEscheatFeedback;

    // Charges

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

    // Accounting

    protected String accountingValue = ACC_NONE;
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashSavingReferenceBlock;
    protected WebMarkupContainer cashSavingReferenceIContainer;
    protected SingleChoiceProvider cashSavingReferenceProvider;
    protected Option cashSavingReferenceValue;
    protected Select2SingleChoice<Option> cashSavingReferenceField;
    protected TextFeedbackPanel cashSavingReferenceFeedback;

    protected WebMarkupBlock cashOverdraftPortfolioBlock;
    protected WebMarkupContainer cashOverdraftPortfolioIContainer;
    protected SingleChoiceProvider cashOverdraftPortfolioProvider;
    protected Option cashOverdraftPortfolioValue;
    protected Select2SingleChoice<Option> cashOverdraftPortfolioField;
    protected TextFeedbackPanel cashOverdraftPortfolioFeedback;

    protected WebMarkupBlock cashSavingControlBlock;
    protected WebMarkupContainer cashSavingControlIContainer;
    protected SingleChoiceProvider cashSavingControlProvider;
    protected Option cashSavingControlValue;
    protected Select2SingleChoice<Option> cashSavingControlField;
    protected TextFeedbackPanel cashSavingControlFeedback;

    protected WebMarkupBlock cashSavingsTransfersInSuspenseBlock;
    protected WebMarkupContainer cashSavingsTransfersInSuspenseIContainer;
    protected SingleChoiceProvider cashSavingsTransfersInSuspenseProvider;
    protected Option cashSavingsTransfersInSuspenseValue;
    protected Select2SingleChoice<Option> cashSavingsTransfersInSuspenseField;
    protected TextFeedbackPanel cashSavingsTransfersInSuspenseFeedback;

    protected WebMarkupContainer cashEscheatLiabilityBlock;
    protected WebMarkupContainer cashEscheatLiabilityIContainer;
    protected SingleChoiceProvider cashEscheatLiabilityProvider;
    protected Option cashEscheatLiabilityValue;
    protected Select2SingleChoice<Option> cashEscheatLiabilityField;
    protected TextFeedbackPanel cashEscheatLiabilityFeedback;

    protected WebMarkupBlock cashInterestOnSavingBlock;
    protected WebMarkupContainer cashInterestOnSavingIContainer;
    protected SingleChoiceProvider cashInterestOnSavingProvider;
    protected Option cashInterestOnSavingValue;
    protected Select2SingleChoice<Option> cashInterestOnSavingField;
    protected TextFeedbackPanel cashInterestOnSavingFeedback;

    protected WebMarkupBlock cashWriteOffBlock;
    protected WebMarkupContainer cashWriteOffIContainer;
    protected SingleChoiceProvider cashWriteOffProvider;
    protected Option cashWriteOffValue;
    protected Select2SingleChoice<Option> cashWriteOffField;
    protected TextFeedbackPanel cashWriteOffFeedback;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Option cashIncomeFromFeeValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
    protected Option cashIncomeFromPenaltyValue;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;
    protected TextFeedbackPanel cashIncomeFromPenaltyFeedback;

    protected WebMarkupBlock cashOverdraftInterestIncomeBlock;
    protected WebMarkupContainer cashOverdraftInterestIncomeIContainer;
    protected SingleChoiceProvider cashOverdraftInterestIncomeProvider;
    protected Option cashOverdraftInterestIncomeValue;
    protected Select2SingleChoice<Option> cashOverdraftInterestIncomeField;
    protected TextFeedbackPanel cashOverdraftInterestIncomeFeedback;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected ModalWindow fundSourcePopup;

    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected ModalWindow feeIncomePopup;

    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected ModalWindow penaltyIncomePopup;

    protected Map<String, Object> popupModel;

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
    protected void configureRequiredValidation() {
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

    @Override
    protected void configureMetaData() {
        settingOverdraftAllowedFieldUpdate(null);

        settingWithholdTaxApplicableFieldUpdate(null);

        settingEnableDormancyTrackingFieldUpdate(null);

        accountingFieldUpdate(null);
    }

    @Override
    protected void initComponent() {

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerm();

        initSectionSetting();

        initSectionCharge();

        initSectionAccounting();
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
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

    protected void feeIncomePopupClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRuleFeeIncomeValue.add(item);
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void penaltyIncomePopupClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRulePenaltyIncomeValue.add(item);
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected void fundSourcePopupClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("payment", this.popupModel.get("paymentValue"));
        item.put("account", this.popupModel.get("accountValue"));
        this.advancedAccountingRuleFundSourceValue.add(item);
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected void initSectionAccounting() {
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
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleIContainer = new WebMarkupContainer("advancedAccountingRuleIContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleIContainer);

        this.fundSourcePopup = new ModalWindow("fundSourcePopup");
        add(this.fundSourcePopup);
        this.fundSourcePopup.setOnClose(this::fundSourcePopupClose);

        initAdvancedAccountingRuleFundSourceTable();

        this.feeIncomePopup = new ModalWindow("feeIncomePopup");
        add(this.feeIncomePopup);
        this.feeIncomePopup.setOnClose(this::feeIncomePopupClose);

        initAdvancedAccountingRuleFeeIncomeTable();

        this.penaltyIncomePopup = new ModalWindow("penaltyIncomePopup");
        add(this.penaltyIncomePopup);
        this.penaltyIncomePopup.setOnClose(this::penaltyIncomePopupClose);

        initAdvancedAccountingRulePenaltyIncomeTable();

    }

    protected void initAdvancedAccountingRulePenaltyIncomeTable() {
        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

        this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
        this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);
    }

    protected void initAdvancedAccountingRuleFeeIncomeTable() {
        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
        this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
    }

    protected void initAdvancedAccountingRuleFundSourceTable() {
        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
        this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceAddLink);
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.penaltyIncomePopup.setContent(new PenaltyChargePopup(this.penaltyIncomePopup.getContentId(), this.penaltyIncomePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.penaltyIncomePopup.show(target);
        } else {
            this.penaltyIncomePopup.setContent(new CurrencyPopup(this.penaltyIncomePopup.getContentId()));
            this.penaltyIncomePopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void advancedAccountingRulePenaltyIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.feeIncomePopup.setContent(new FeeChargePopup(this.feeIncomePopup.getContentId(), this.feeIncomePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.feeIncomePopup.show(target);
        } else {
            this.feeIncomePopup.setContent(new CurrencyPopup(this.feeIncomePopup.getContentId()));
            this.feeIncomePopup.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void advancedAccountingRuleFeeIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected void advancedAccountingRuleFundSourceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> advancedAccountingRuleFundSourceAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.fundSourcePopup.setContent(new PaymentTypePopup(this.fundSourcePopup.getContentId(), this.fundSourcePopup, this.popupModel));
        this.fundSourcePopup.show(target);
        return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        initCashSavingReferenceBlock();

        initCashOverdraftPortfolioBlock();

        initCashSavingControlBlock();

        initCashSavingsTransfersInSuspenseBlock();

        initCashEscheatLiabilityBlock();

        initCashInterestOnSavingBlock();

        initCashWriteOffBlock();

        initCashIncomeFromFeeBlock();

        initCashIncomeFromPenaltyBlock();

        initCashOverdraftInterestIncomeBlock();
    }

    protected void initCashSavingReferenceBlock() {
        this.cashSavingReferenceBlock = new WebMarkupBlock("cashSavingReferenceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingReferenceBlock);
        this.cashSavingReferenceIContainer = new WebMarkupContainer("cashSavingReferenceIContainer");
        this.cashSavingReferenceBlock.add(this.cashSavingReferenceIContainer);
        this.cashSavingReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceField);
        this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback", this.cashSavingReferenceField);
        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceFeedback);
    }

    protected void initCashOverdraftPortfolioBlock() {
        this.cashOverdraftPortfolioBlock = new WebMarkupBlock("cashOverdraftPortfolioBlock", Size.Six_6);
        this.cashIContainer.add(this.cashOverdraftPortfolioBlock);
        this.cashOverdraftPortfolioIContainer = new WebMarkupContainer("cashOverdraftPortfolioIContainer");
        this.cashOverdraftPortfolioBlock.add(this.cashOverdraftPortfolioIContainer);
        this.cashOverdraftPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverdraftPortfolioProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashOverdraftPortfolioProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashOverdraftPortfolioField = new Select2SingleChoice<>("cashOverdraftPortfolioField", new PropertyModel<>(this, "cashOverdraftPortfolioValue"), this.cashOverdraftPortfolioProvider);
        this.cashOverdraftPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashOverdraftPortfolioIContainer.add(this.cashOverdraftPortfolioField);
        this.cashOverdraftPortfolioFeedback = new TextFeedbackPanel("cashOverdraftPortfolioFeedback", this.cashOverdraftPortfolioField);
        this.cashOverdraftPortfolioIContainer.add(this.cashOverdraftPortfolioFeedback);
    }

    protected void initCashSavingControlBlock() {
        this.cashSavingControlBlock = new WebMarkupBlock("cashSavingControlBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingControlBlock);
        this.cashSavingControlIContainer = new WebMarkupContainer("cashSavingControlIContainer");
        this.cashSavingControlBlock.add(this.cashSavingControlIContainer);
        this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
        this.cashSavingControlIContainer.add(this.cashSavingControlField);
        this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback", this.cashSavingControlField);
        this.cashSavingControlIContainer.add(this.cashSavingControlFeedback);
    }

    protected void initCashSavingsTransfersInSuspenseBlock() {
        this.cashSavingsTransfersInSuspenseBlock = new WebMarkupBlock("cashSavingsTransfersInSuspenseBlock", Size.Six_6);
        this.cashIContainer.add(this.cashSavingsTransfersInSuspenseBlock);
        this.cashSavingsTransfersInSuspenseIContainer = new WebMarkupContainer("cashSavingsTransfersInSuspenseIContainer");
        this.cashSavingsTransfersInSuspenseBlock.add(this.cashSavingsTransfersInSuspenseIContainer);
        this.cashSavingsTransfersInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashSavingsTransfersInSuspenseProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashSavingsTransfersInSuspenseField = new Select2SingleChoice<>("cashSavingsTransfersInSuspenseField", new PropertyModel<>(this, "cashSavingsTransfersInSuspenseValue"), this.cashSavingsTransfersInSuspenseProvider);
        this.cashSavingsTransfersInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashSavingsTransfersInSuspenseIContainer.add(this.cashSavingsTransfersInSuspenseField);
        this.cashSavingsTransfersInSuspenseFeedback = new TextFeedbackPanel("cashSavingsTransfersInSuspenseFeedback", this.cashSavingsTransfersInSuspenseField);
        this.cashSavingsTransfersInSuspenseIContainer.add(this.cashSavingsTransfersInSuspenseFeedback);
    }

    protected void initCashEscheatLiabilityBlock() {
        this.cashEscheatLiabilityBlock = new WebMarkupContainer("cashEscheatLiabilityBlock");
        this.cashIContainer.add(cashEscheatLiabilityBlock);
        this.cashEscheatLiabilityIContainer = new WebMarkupContainer("cashEscheatLiabilityIContainer");
        this.cashEscheatLiabilityBlock.add(this.cashEscheatLiabilityIContainer);
        this.cashEscheatLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashEscheatLiabilityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashEscheatLiabilityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashEscheatLiabilityField = new Select2SingleChoice<>("cashEscheatLiabilityField", new PropertyModel<>(this, "cashEscheatLiabilityValue"), this.cashEscheatLiabilityProvider);
        this.cashEscheatLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashEscheatLiabilityIContainer.add(this.cashEscheatLiabilityField);
        this.cashEscheatLiabilityFeedback = new TextFeedbackPanel("cashEscheatLiabilityFeedback", this.cashEscheatLiabilityField);
        this.cashEscheatLiabilityIContainer.add(this.cashEscheatLiabilityFeedback);
    }

    protected void initCashInterestOnSavingBlock() {
        this.cashInterestOnSavingBlock = new WebMarkupBlock("cashInterestOnSavingBlock", Size.Six_6);
        this.cashIContainer.add(this.cashInterestOnSavingBlock);
        this.cashInterestOnSavingIContainer = new WebMarkupContainer("cashInterestOnSavingIContainer");
        this.cashInterestOnSavingBlock.add(this.cashInterestOnSavingIContainer);
        this.cashInterestOnSavingProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashInterestOnSavingProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashInterestOnSavingProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Expense.getLiteral());
        this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField", new PropertyModel<>(this, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
        this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingField);
        this.cashInterestOnSavingFeedback = new TextFeedbackPanel("cashInterestOnSavingFeedback", this.cashInterestOnSavingField);
        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingFeedback);
    }

    protected void initCashWriteOffBlock() {
        this.cashWriteOffBlock = new WebMarkupBlock("cashWriteOffBlock", Size.Six_6);
        this.cashIContainer.add(this.cashWriteOffBlock);
        this.cashWriteOffIContainer = new WebMarkupContainer("cashWriteOffIContainer");
        this.cashWriteOffBlock.add(this.cashWriteOffIContainer);
        this.cashWriteOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashWriteOffProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashWriteOffProvider.applyWhere("classification_enum", "classification_enum  = " + AccountType.Expense.getLiteral());
        this.cashWriteOffField = new Select2SingleChoice<>("cashWriteOffField", new PropertyModel<>(this, "cashWriteOffValue"), this.cashWriteOffProvider);
        this.cashWriteOffField.add(new OnChangeAjaxBehavior());
        this.cashWriteOffIContainer.add(this.cashWriteOffField);
        this.cashWriteOffFeedback = new TextFeedbackPanel("cashWriteOffFeedback", this.cashWriteOffField);
        this.cashWriteOffIContainer.add(this.cashWriteOffFeedback);
    }

    protected void initCashIncomeFromFeeBlock() {
        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeFeedback);
    }

    protected void initCashIncomeFromPenaltyBlock() {
        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyIContainer = new WebMarkupContainer("cashIncomeFromPenaltyIContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyIContainer);
        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyFeedback = new TextFeedbackPanel("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyFeedback);
    }

    protected void initCashOverdraftInterestIncomeBlock() {
        this.cashOverdraftInterestIncomeBlock = new WebMarkupBlock("cashOverdraftInterestIncomeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashOverdraftInterestIncomeBlock);
        this.cashOverdraftInterestIncomeIContainer = new WebMarkupContainer("cashOverdraftInterestIncomeIContainer");
        this.cashOverdraftInterestIncomeBlock.add(this.cashOverdraftInterestIncomeIContainer);
        this.cashOverdraftInterestIncomeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashOverdraftInterestIncomeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashOverdraftInterestIncomeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashOverdraftInterestIncomeField = new Select2SingleChoice<>("cashOverdraftInterestIncomeField", new PropertyModel<>(this, "cashOverdraftInterestIncomeValue"), this.cashOverdraftInterestIncomeProvider);
        this.cashOverdraftInterestIncomeField.add(new OnChangeAjaxBehavior());
        this.cashOverdraftInterestIncomeIContainer.add(this.cashOverdraftInterestIncomeField);
        this.cashOverdraftInterestIncomeFeedback = new TextFeedbackPanel("cashOverdraftInterestIncomeFeedback", this.cashOverdraftInterestIncomeField);
        this.cashOverdraftInterestIncomeIContainer.add(this.cashOverdraftInterestIncomeFeedback);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);
        this.advancedAccountingRuleIContainer.setVisible(false);
        if ("None".equals(this.accountingValue) || this.accountingValue == null) {
            this.advancedAccountingRuleIContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleIContainer.setVisible(true);
        }
        if ("Cash".equals(this.accountingValue)) {
            this.cashIContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected void initSectionCharge() {

        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupClose);

        initChargeBlock();
    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeIContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.chargeIContainer.add(this.chargeAddLink);
    }

    protected void chargePopupClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        Option charge = (Option) this.popupModel.get("chargeValue");
        for (Map<String, Object> temp : this.chargeValue) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?", charge.getId());
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
        item.put("uuid", charge.getId());
        item.put("charge", charge);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.chargePopup.setContent(new CurrencyPopup(this.chargePopup.getContentId()));
            this.chargePopup.show(target);
        }
        return false;
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "type".equals(column) || "collect".equals(column) || "date".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void chargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
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
        this.form.add(this.settingNumberOfDaysToEscheatBlock);
        this.settingNumberOfDaysToEscheatIContainer = new WebMarkupContainer("settingNumberOfDaysToEscheatIContainer");
        this.settingNumberOfDaysToEscheatBlock.add(this.settingNumberOfDaysToEscheatIContainer);
        this.settingNumberOfDaysToEscheatField = new TextField<>("settingNumberOfDaysToEscheatField", new PropertyModel<>(this, "settingNumberOfDaysToEscheatValue"));
        this.settingNumberOfDaysToEscheatField.setLabel(Model.of("Number of Days to Escheat"));
        this.settingNumberOfDaysToEscheatField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToEscheatIContainer.add(this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatFeedback = new TextFeedbackPanel("settingNumberOfDaysToEscheatFeedback", this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatIContainer.add(this.settingNumberOfDaysToEscheatFeedback);
    }

    protected void initSettingNumberOfDaysToDormantSubStatusBlock() {
        this.settingNumberOfDaysToDormantSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysToDormantSubStatusBlock);
        this.settingNumberOfDaysToDormantSubStatusIContainer = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusIContainer");
        this.settingNumberOfDaysToDormantSubStatusBlock.add(this.settingNumberOfDaysToDormantSubStatusIContainer);
        this.settingNumberOfDaysToDormantSubStatusField = new TextField<>("settingNumberOfDaysToDormantSubStatusField", new PropertyModel<>(this, "settingNumberOfDaysToDormantSubStatusValue"));
        this.settingNumberOfDaysToDormantSubStatusField.setLabel(Model.of("Number of Days to Dormant sub-status"));
        this.settingNumberOfDaysToDormantSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToDormantSubStatusIContainer.add(this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToDormantSubStatusFeedback", this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusIContainer.add(this.settingNumberOfDaysToDormantSubStatusFeedback);
    }

    protected void initSettingNumberOfDaysToInactiveSubStatusBlock() {
        this.settingNumberOfDaysToInactiveSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
        this.settingNumberOfDaysToInactiveSubStatusIContainer = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusIContainer");
        this.settingNumberOfDaysToInactiveSubStatusBlock.add(this.settingNumberOfDaysToInactiveSubStatusIContainer);
        this.settingNumberOfDaysToInactiveSubStatusField = new TextField<>("settingNumberOfDaysToInactiveSubStatusField", new PropertyModel<>(this, "settingNumberOfDaysToInactiveSubStatusValue"));
        this.settingNumberOfDaysToInactiveSubStatusField.setLabel(Model.of("Number of Days to Inactive sub-status"));
        this.settingNumberOfDaysToInactiveSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToInactiveSubStatusIContainer.add(this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToInactiveSubStatusFeedback", this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusIContainer.add(this.settingNumberOfDaysToInactiveSubStatusFeedback);
    }

    protected void initSettingEnableDormancyTrackingBlock() {
        this.settingEnableDormancyTrackingBlock = new WebMarkupBlock("settingEnableDormancyTrackingBlock", Size.Six_6);
        this.form.add(this.settingEnableDormancyTrackingBlock);
        this.settingEnableDormancyTrackingIContainer = new WebMarkupContainer("settingEnableDormancyTrackingIContainer");
        this.settingEnableDormancyTrackingBlock.add(this.settingEnableDormancyTrackingIContainer);
        this.settingEnableDormancyTrackingField = new CheckBox("settingEnableDormancyTrackingField", new PropertyModel<>(this, "settingEnableDormancyTrackingValue"));
        this.settingEnableDormancyTrackingField.add(new OnChangeAjaxBehavior(this::settingEnableDormancyTrackingFieldUpdate));
        this.settingEnableDormancyTrackingIContainer.add(this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingFeedback = new TextFeedbackPanel("settingEnableDormancyTrackingFeedback", this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingIContainer.add(this.settingEnableDormancyTrackingFeedback);
    }

    protected void initSettingTaxGroupBlock() {
        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupIContainer = new WebMarkupContainer("settingTaxGroupIContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupIContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", 0, new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupIContainer.add(this.settingTaxGroupFeedback);
    }

    protected void initSettingWithholdTaxApplicableBlock() {
        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableIContainer = new WebMarkupContainer("settingWithholdTaxApplicableIContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableIContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableFeedback);
    }

    protected void initSettingMinOverdraftRequiredForInterestCalculationBlock() {
        this.settingMinOverdraftRequiredForInterestCalculationBlock = new WebMarkupBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Six_6);
        this.form.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        this.settingMinOverdraftRequiredForInterestCalculationIContainer = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationIContainer");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.add(this.settingMinOverdraftRequiredForInterestCalculationIContainer);
        this.settingMinOverdraftRequiredForInterestCalculationField = new TextField<>("settingMinOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this, "settingMinOverdraftRequiredForInterestCalculationValue"));
        this.settingMinOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
        this.settingMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingMinOverdraftRequiredForInterestCalculationIContainer.add(this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingMinOverdraftRequiredForInterestCalculationFeedback", this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationIContainer.add(this.settingMinOverdraftRequiredForInterestCalculationFeedback);
    }

    protected void initSettingNominalAnnualInterestForOverdraftBlock() {
        this.settingNominalAnnualInterestForOverdraftBlock = new WebMarkupBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Six_6);
        this.form.add(this.settingNominalAnnualInterestForOverdraftBlock);
        this.settingNominalAnnualInterestForOverdraftIContainer = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftIContainer");
        this.settingNominalAnnualInterestForOverdraftBlock.add(this.settingNominalAnnualInterestForOverdraftIContainer);
        this.settingNominalAnnualInterestForOverdraftField = new TextField<>("settingNominalAnnualInterestForOverdraftField", new PropertyModel<>(this, "settingNominalAnnualInterestForOverdraftValue"));
        this.settingNominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal annual interest for overdraft"));
        this.settingNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
        this.settingNominalAnnualInterestForOverdraftIContainer.add(this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel("settingNominalAnnualInterestForOverdraftFeedback", this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftIContainer.add(this.settingNominalAnnualInterestForOverdraftFeedback);
    }

    protected void initSettingMaximumOverdraftAmountLimitBlock() {
        this.settingMaximumOverdraftAmountLimitBlock = new WebMarkupBlock("settingMaximumOverdraftAmountLimitBlock", Size.Six_6);
        this.form.add(this.settingMaximumOverdraftAmountLimitBlock);
        this.settingMaximumOverdraftAmountLimitIContainer = new WebMarkupContainer("settingMaximumOverdraftAmountLimitIContainer");
        this.settingMaximumOverdraftAmountLimitBlock.add(this.settingMaximumOverdraftAmountLimitIContainer);
        this.settingMaximumOverdraftAmountLimitField = new TextField<>("settingMaximumOverdraftAmountLimitField", new PropertyModel<>(this, "settingMaximumOverdraftAmountLimitValue"));
        this.settingMaximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
        this.settingMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
        this.settingMaximumOverdraftAmountLimitIContainer.add(this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitFeedback = new TextFeedbackPanel("settingMaximumOverdraftAmountLimitFeedback", this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitIContainer.add(this.settingMaximumOverdraftAmountLimitFeedback);
    }

    protected void initSettingOverdraftAllowedBlock() {
        this.settingOverdraftAllowedBlock = new WebMarkupBlock("settingOverdraftAllowedBlock", Size.Six_6);
        this.form.add(this.settingOverdraftAllowedBlock);
        this.settingOverdraftAllowedIContainer = new WebMarkupContainer("settingOverdraftAllowedIContainer");
        this.settingOverdraftAllowedBlock.add(this.settingOverdraftAllowedIContainer);
        this.settingOverdraftAllowedField = new CheckBox("settingOverdraftAllowedField", new PropertyModel<>(this, "settingOverdraftAllowedValue"));
        this.settingOverdraftAllowedField.add(new OnChangeAjaxBehavior(this::settingOverdraftAllowedFieldUpdate));
        this.settingOverdraftAllowedIContainer.add(this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedFeedback = new TextFeedbackPanel("settingOverdraftAllowedFeedback", this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedIContainer.add(this.settingOverdraftAllowedFeedback);
    }

    protected void initSettingMinimumBalanceBlock() {
        this.settingMinimumBalanceBlock = new WebMarkupBlock("settingMinimumBalanceBlock", Size.Four_4);
        this.form.add(this.settingMinimumBalanceBlock);
        this.settingMinimumBalanceIContainer = new WebMarkupContainer("settingMinimumBalanceIContainer");
        this.settingMinimumBalanceBlock.add(this.settingMinimumBalanceIContainer);
        this.settingMinimumBalanceField = new TextField<>("settingMinimumBalanceField", new PropertyModel<>(this, "settingMinimumBalanceValue"));
        this.settingMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumBalanceField.setLabel(Model.of("Minimum balance"));
        this.settingMinimumBalanceIContainer.add(this.settingMinimumBalanceField);
        this.settingMinimumBalanceFeedback = new TextFeedbackPanel("settingMinimumBalanceFeedback", this.settingMinimumBalanceField);
        this.settingMinimumBalanceIContainer.add(this.settingMinimumBalanceFeedback);
    }

    protected void initSettingEnforceMinimumBalanceBlock() {
        this.settingEnforceMinimumBalanceBlock = new WebMarkupBlock("settingEnforceMinimumBalanceBlock", Size.Four_4);
        this.form.add(this.settingEnforceMinimumBalanceBlock);
        this.settingEnforceMinimumBalanceIContainer = new WebMarkupContainer("settingEnforceMinimumBalanceIContainer");
        this.settingEnforceMinimumBalanceBlock.add(this.settingEnforceMinimumBalanceIContainer);
        this.settingEnforceMinimumBalanceField = new CheckBox("settingEnforceMinimumBalanceField", new PropertyModel<>(this, "settingEnforceMinimumBalanceValue"));
        this.settingEnforceMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingEnforceMinimumBalanceIContainer.add(this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceFeedback = new TextFeedbackPanel("settingEnforceMinimumBalanceFeedback", this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceIContainer.add(this.settingEnforceMinimumBalanceFeedback);
    }

    protected void initSettingBalanceRequiredForInterestCalculationBlock() {
        this.settingBalanceRequiredForInterestCalculationBlock = new WebMarkupBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Four_4);
        this.form.add(this.settingBalanceRequiredForInterestCalculationBlock);
        this.settingBalanceRequiredForInterestCalculationIContainer = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationIContainer");
        this.settingBalanceRequiredForInterestCalculationBlock.add(this.settingBalanceRequiredForInterestCalculationIContainer);
        this.settingBalanceRequiredForInterestCalculationField = new TextField<>("settingBalanceRequiredForInterestCalculationField", new PropertyModel<>(this, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationField.setLabel(Model.of("Balance Required For Interest Calculation"));
        this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingBalanceRequiredForInterestCalculationFeedback", this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationFeedback);
    }

    protected void initSettingApplyWithdrawalFeeForTransferBlock() {
        this.settingApplyWithdrawalFeeForTransferBlock = new WebMarkupBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Four_4);
        this.form.add(this.settingApplyWithdrawalFeeForTransferBlock);
        this.settingApplyWithdrawalFeeForTransferIContainer = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferIContainer");
        this.settingApplyWithdrawalFeeForTransferBlock.add(this.settingApplyWithdrawalFeeForTransferIContainer);
        this.settingApplyWithdrawalFeeForTransferField = new CheckBox("settingApplyWithdrawalFeeForTransferField", new PropertyModel<>(this, "settingApplyWithdrawalFeeForTransferValue"));
        this.settingApplyWithdrawalFeeForTransferField.add(new OnChangeAjaxBehavior());
        this.settingApplyWithdrawalFeeForTransferIContainer.add(this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel("settingApplyWithdrawalFeeForTransferFeedback", this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferIContainer.add(this.settingApplyWithdrawalFeeForTransferFeedback);
    }

    protected void initSettingLockInTypeBlock() {
        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0, new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);
    }

    protected void initSettingLockInPeriodBlock() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);
    }

    protected void initSettingMinimumOpeningBalanceBlock() {
        this.settingMinimumOpeningBalanceBlock = new WebMarkupBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
        this.form.add(this.settingMinimumOpeningBalanceBlock);
        this.settingMinimumOpeningBalanceIContainer = new WebMarkupContainer("settingMinimumOpeningBalanceIContainer");
        this.settingMinimumOpeningBalanceBlock.add(this.settingMinimumOpeningBalanceIContainer);
        this.settingMinimumOpeningBalanceField = new TextField<>("settingMinimumOpeningBalanceField", new PropertyModel<>(this, "settingMinimumOpeningBalanceValue"));
        this.settingMinimumOpeningBalanceField.setLabel(Model.of("Minimum opening balance"));
        this.settingMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumOpeningBalanceIContainer.add(this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceFeedback = new TextFeedbackPanel("settingMinimumOpeningBalanceFeedback", this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceIContainer.add(this.settingMinimumOpeningBalanceFeedback);
    }

    protected void initSectionTerm() {

        initTermNominalAnnualInterestBlock();

        initTermInterestCompoundingPeriodBlock();

        initTermInterestCalculatedUsingBlock();

        initTermInterestPostingPeriodBlock();

        initTermDaysInYearBlock();
    }

    protected void initTermDaysInYearBlock() {
        this.termDaysInYearBlock = new WebMarkupBlock("termDaysInYearBlock", Size.Six_6);
        this.form.add(this.termDaysInYearBlock);
        this.termDaysInYearIContainer = new WebMarkupContainer("termDaysInYearIContainer");
        this.termDaysInYearBlock.add(this.termDaysInYearIContainer);
        this.termDaysInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termDaysInYearField = new Select2SingleChoice<>("termDaysInYearField", 0, new PropertyModel<>(this, "termDaysInYearValue"), this.termDaysInYearProvider);
        this.termDaysInYearField.setLabel(Model.of("Days in year"));
        this.termDaysInYearField.add(new OnChangeAjaxBehavior());
        this.termDaysInYearIContainer.add(this.termDaysInYearField);
        this.termDaysInYearFeedback = new TextFeedbackPanel("termDaysInYearFeedback", this.termDaysInYearField);
        this.termDaysInYearIContainer.add(this.termDaysInYearFeedback);
    }

    protected void initTermInterestPostingPeriodBlock() {
        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodIContainer = new WebMarkupContainer("termInterestPostingPeriodIContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodIContainer);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", 0, new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest calculated using"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodFeedback);
    }

    protected void initTermInterestCalculatedUsingBlock() {
        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingIContainer = new WebMarkupContainer("termInterestCalculatedUsingIContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingIContainer);
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", 0, new PropertyModel<>(this, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest posting period"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingFeedback);
    }

    protected void initTermInterestCompoundingPeriodBlock() {
        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodIContainer = new WebMarkupContainer("termInterestCompoundingPeriodIContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodIContainer);
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", 0, new PropertyModel<>(this, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodFeedback);
    }

    protected void initTermNominalAnnualInterestBlock() {
        this.termNominalAnnualInterestBlock = new WebMarkupBlock("termNominalAnnualInterestBlock", Size.Six_6);
        this.form.add(this.termNominalAnnualInterestBlock);
        this.termNominalAnnualInterestIContainer = new WebMarkupContainer("termNominalAnnualInterestIContainer");
        this.termNominalAnnualInterestBlock.add(this.termNominalAnnualInterestIContainer);
        this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField", new PropertyModel<>(this, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestField.setLabel(Model.of("Nominal annual interest"));
        this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());
        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestFeedback = new TextFeedbackPanel("termNominalAnnualInterestFeedback", this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestFeedback);
    }

    protected void initSectionCurrency() {

        initCurrencyCodeBlock();

        initCurrencyDecimalPlaceBlock();

        initCurrencyMultipleOfBlock();
    }

    protected void initCurrencyMultipleOfBlock() {
        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfIContainer = new WebMarkupContainer("currencyMultipleOfIContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfIContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initCurrencyDecimalPlaceBlock() {
        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceIContainer = new WebMarkupContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceIContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceFeedback);
    }

    protected void initCurrencyCodeBlock() {
        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeIContainer = new WebMarkupContainer("currencyCodeIContainer");
        this.currencyCodeBlock.add(this.currencyCodeIContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeIContainer.add(this.currencyCodeFeedback);
    }

    protected void initSectionDetail() {
        initDetailProductNameBlock();

        initDetailShortNameBlock();

        initDetailDescriptionBlock();
    }

    protected void initDetailDescriptionBlock() {
        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionIContainer = new WebMarkupContainer("detailDescriptionIContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionIContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionIContainer.add(this.detailDescriptionFeedback);
    }

    protected void initDetailShortNameBlock() {
        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameIContainer = new WebMarkupContainer("detailShortNameIContainer");
        this.detailShortNameBlock.add(this.detailShortNameIContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameIContainer.add(this.detailShortNameFeedback);
    }

    protected void initDetailProductNameBlock() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameIContainer = new WebMarkupContainer("detailProductNameIContainer");
        this.detailProductNameBlock.add(this.detailProductNameIContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameIContainer.add(this.detailProductNameFeedback);
    }

    protected boolean settingOverdraftAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingOverdraftAllowedValue != null && this.settingOverdraftAllowedValue;
        this.settingMaximumOverdraftAmountLimitIContainer.setVisible(visible);
        this.settingNominalAnnualInterestForOverdraftIContainer.setVisible(visible);
        this.settingMinOverdraftRequiredForInterestCalculationIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingMaximumOverdraftAmountLimitBlock);
            target.add(this.settingNominalAnnualInterestForOverdraftBlock);
            target.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        }
        return false;
    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingWithholdTaxApplicableValue != null && this.settingWithholdTaxApplicableValue;
        this.settingTaxGroupIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected boolean settingEnableDormancyTrackingFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingEnableDormancyTrackingValue != null && this.settingEnableDormancyTrackingValue;
        this.settingNumberOfDaysToInactiveSubStatusIContainer.setVisible(visible);
        this.settingNumberOfDaysToDormantSubStatusIContainer.setVisible(visible);
        this.settingNumberOfDaysToEscheatIContainer.setVisible(visible);
        this.cashEscheatLiabilityIContainer.setVisible(visible);
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
            if (this.cashIncomeFromPenaltyValue != null) {
                builder.withIncomeFromPenaltyAccountId(this.cashIncomeFromPenaltyValue.getId());
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
