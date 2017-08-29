package com.angkorteam.fintech.pages.product;

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
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    // Detail

    private String detailProductNameValue;
    private TextField<String> detailProductNameField;
    private TextFeedbackPanel detailProductNameFeedback;

    private String detailShortNameValue;
    private TextField<String> detailShortNameField;
    private TextFeedbackPanel detailShortNameFeedback;

    private String detailDescriptionValue;
    private TextField<String> detailDescriptionField;
    private TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    private SingleChoiceProvider currencyCodeProvider;
    private Option currencyCodeValue;
    private Select2SingleChoice<Option> currencyCodeField;
    private TextFeedbackPanel currencyCodeFeedback;

    private Integer currencyDecimalPlaceValue;
    private TextField<Integer> currencyDecimalPlaceField;
    private TextFeedbackPanel currencyDecimalPlaceFeedback;

    private Integer currencyMultipleOfValue;
    private TextField<Integer> currencyMultipleOfField;
    private TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    private String termNominalAnnualInterestValue;
    private TextField<String> termNominalAnnualInterestField;
    private TextFeedbackPanel termNominalAnnualInterestFeedback;

    private SingleChoiceProvider termInterestCompoundingPeriodProvider;
    private Option termInterestCompoundingPeriodValue;
    private Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    private TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    private SingleChoiceProvider termInterestCalculatedUsingProvider;
    private Option termInterestCalculatedUsingValue;
    private Select2SingleChoice<Option> termInterestCalculatedUsingField;
    private TextFeedbackPanel termInterestCalculatedUsingFeedback;

    private SingleChoiceProvider termInterestPostingPeriodProvider;
    private Option termInterestPostingPeriodValue;
    private Select2SingleChoice<Option> termInterestPostingPeriodField;
    private TextFeedbackPanel termInterestPostingPeriodFeedback;

    private SingleChoiceProvider termDaysInYearProvider;
    private Option termDaysInYearValue;
    private Select2SingleChoice<Option> termDaysInYearField;
    private TextFeedbackPanel termDaysInYearFeedback;

    // Settings

    private String settingMinimumOpeningBalanceValue;
    private TextField<String> settingMinimumOpeningBalanceField;
    private TextFeedbackPanel settingMinimumOpeningBalanceFeedback;

    private String settingLockInPeriodValue;
    private TextField<String> settingLockInPeriodField;
    private TextFeedbackPanel settingLockInPeriodFeedback;

    private SingleChoiceProvider settingLockInTypeProvider;
    private Option settingLockInTypeValue;
    private Select2SingleChoice<Option> settingLockInTypeField;
    private TextFeedbackPanel settingLockInTypeFeedback;

    private Boolean settingApplyWithdrawalFeeForTransferValue;
    private CheckBox settingApplyWithdrawalFeeForTransferField;
    private TextFeedbackPanel settingApplyWithdrawalFeeForTransferFeedback;

    private String settingBalanceRequiredForInterestCalculationValue;
    private TextField<String> settingBalanceRequiredForInterestCalculationField;
    private TextFeedbackPanel settingBalanceRequiredForInterestCalculationFeedback;

    private Boolean settingEnforceMinimumBalanceValue;
    private CheckBox settingEnforceMinimumBalanceField;
    private TextFeedbackPanel settingEnforceMinimumBalanceFeedback;

    private String settingMinimumBalanceValue;
    private TextField<String> settingMinimumBalanceField;
    private TextFeedbackPanel settingMinimumBalanceFeedback;

    private Boolean settingOverdraftAllowedValue;
    private CheckBox settingOverdraftAllowedField;
    private TextFeedbackPanel settingOverdraftAllowedFeedback;

    private WebMarkupContainer settingOverdraftAllowedContainer;

    private String settingMaximumOverdraftAmountLimitValue;
    private TextField<String> settingMaximumOverdraftAmountLimitField;
    private TextFeedbackPanel settingMaximumOverdraftAmountLimitFeedback;

    private String settingNominalAnnualInterestForOverdraftValue;
    private TextField<String> settingNominalAnnualInterestForOverdraftField;
    private TextFeedbackPanel settingNominalAnnualInterestForOverdraftFeedback;

    private String settingMinOverdraftRequiredForInterestCalculationValue;
    private TextField<String> settingMinOverdraftRequiredForInterestCalculationField;
    private TextFeedbackPanel settingMinOverdraftRequiredForInterestCalculationFeedback;

    private Boolean settingWithholdTaxApplicableValue;
    private CheckBox settingWithholdTaxApplicableField;
    private TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    private WebMarkupContainer settingWithholdTaxApplicableContainer;

    private SingleChoiceProvider settingTaxGroupProvider;
    private Option settingTaxGroupValue;
    private Select2SingleChoice<Option> settingTaxGroupField;
    private TextFeedbackPanel settingTaxGroupFeedback;

    private Boolean settingEnableDormancyTrackingValue;
    private CheckBox settingEnableDormancyTrackingField;
    private TextFeedbackPanel settingEnableDormancyTrackingFeedback;

    private WebMarkupContainer settingEnableDormancyTrackingContainer;

    private String settingNumberOfDaysToInactiveSubStatusValue;
    private TextField<String> settingNumberOfDaysToInactiveSubStatusField;
    private TextFeedbackPanel settingNumberOfDaysToInactiveSubStatusFeedback;

    private String settingNumberOfDaysToDormantSubStatusValue;
    private TextField<String> settingNumberOfDaysToDormantSubStatusField;
    private TextFeedbackPanel settingNumberOfDaysToDormantSubStatusFeedback;

    private String settingNumberOfDaysToEscheatValue;
    private TextField<String> settingNumberOfDaysToEscheatField;
    private TextFeedbackPanel settingNumberOfDaysToEscheatFeedback;

    // Charges

    private ModalWindow chargePopup;

    private List<Map<String, Object>> chargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargeTable;
    private ListDataProvider chargeProvider;

    // Accounting

    private String accountingValue = ACC_NONE;
    private RadioGroup<String> accountingField;

    private WebMarkupContainer cashContainer;

    private SingleChoiceProvider cashSavingReferenceProvider;
    private Option cashSavingReferenceValue;
    private Select2SingleChoice<Option> cashSavingReferenceField;
    private TextFeedbackPanel cashSavingReferenceFeedback;

    private SingleChoiceProvider cashOverdraftPortfolioProvider;
    private Option cashOverdraftPortfolioValue;
    private Select2SingleChoice<Option> cashOverdraftPortfolioField;
    private TextFeedbackPanel cashOverdraftPortfolioFeedback;

    private SingleChoiceProvider cashSavingControlProvider;
    private Option cashSavingControlValue;
    private Select2SingleChoice<Option> cashSavingControlField;
    private TextFeedbackPanel cashSavingControlFeedback;

    private SingleChoiceProvider cashSavingsTransfersInSuspenseProvider;
    private Option cashSavingsTransfersInSuspenseValue;
    private Select2SingleChoice<Option> cashSavingsTransfersInSuspenseField;
    private TextFeedbackPanel cashSavingsTransfersInSuspenseFeedback;

    private SingleChoiceProvider cashEscheatLiabilityProvider;
    private Option cashEscheatLiabilityValue;
    private Select2SingleChoice<Option> cashEscheatLiabilityField;
    private TextFeedbackPanel cashEscheatLiabilityFeedback;

    private SingleChoiceProvider cashInterestOnSavingProvider;
    private Option cashInterestOnSavingValue;
    private Select2SingleChoice<Option> cashInterestOnSavingField;
    private TextFeedbackPanel cashInterestOnSavingFeedback;

    private SingleChoiceProvider cashWriteOffProvider;
    private Option cashWriteOffValue;
    private Select2SingleChoice<Option> cashWriteOffField;
    private TextFeedbackPanel cashWriteOffFeedback;

    private SingleChoiceProvider cashIncomeFromFeeProvider;
    private Option cashIncomeFromFeeValue;
    private Select2SingleChoice<Option> cashIncomeFromFeeField;
    private TextFeedbackPanel cashIncomeFromFeeFeedback;

    private SingleChoiceProvider cashIncomeFromPenaltiesProvider;
    private Option cashIncomeFromPenaltiesValue;
    private Select2SingleChoice<Option> cashIncomeFromPenaltiesField;
    private TextFeedbackPanel cashIncomeFromPenaltiesFeedback;

    private SingleChoiceProvider cashOverdraftInterestIncomeProvider;
    private Option cashOverdraftInterestIncomeValue;
    private Select2SingleChoice<Option> cashOverdraftInterestIncomeField;
    private TextFeedbackPanel cashOverdraftInterestIncomeFeedback;

    // Advanced Accounting Rule

    private WebMarkupContainer advancedAccountingRuleContainer;

    private List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    private ListDataProvider advancedAccountingRuleFundSourceProvider;
    private AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;

    private ModalWindow paymentPopup;

    private List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    private ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    private AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;

    private ModalWindow feeChargePopup;

    private List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    private ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    private AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;

    private ModalWindow penaltyChargePopup;

    private Option itemChargeValue;
    private Option itemPaymentValue;
    private Option itemAccountValue;

    private static final List<PageBreadcrumb> BREADCRUMB;

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

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", LoanCreatePage.class);
	this.form.add(this.closeLink);

	this.chargePopup = new ModalWindow("chargePopup");
	add(this.chargePopup);
	this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this));
	this.chargePopup.setOnClose(this::chargePopupOnClose);

	this.paymentPopup = new ModalWindow("paymentPopup");
	add(this.paymentPopup);
	this.paymentPopup.setContent(new PaymentTypePopup(this.paymentPopup.getContentId(), this.paymentPopup, this));
	this.paymentPopup.setOnClose(this::paymentPopupOnClose);

	this.feeChargePopup = new ModalWindow("feeChargePopup");
	add(this.feeChargePopup);
	this.feeChargePopup
		.setContent(new FeeChargePopup(this.feeChargePopup.getContentId(), this.feeChargePopup, this));
	this.feeChargePopup.setOnClose(this::feeChargePopupOnClose);

	this.penaltyChargePopup = new ModalWindow("penaltyChargePopup");
	add(this.penaltyChargePopup);
	this.penaltyChargePopup.setContent(
		new PenaltyChargePopup(this.penaltyChargePopup.getContentId(), this.penaltyChargePopup, this));
	this.penaltyChargePopup.setOnClose(this::penaltyChargePopupOnClose);

	initDetail();

	initCurrency();

	initTerm();

	initSetting();

	initCharge();

	initAccounting();
    }

    protected void feeChargePopupOnClose(String elementId, AjaxRequestTarget target) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("uuid", UUID.randomUUID().toString());
	item.put("chargeId", this.itemChargeValue.getId());
	item.put("charge", this.itemChargeValue.getText());
	item.put("accountId", this.itemAccountValue.getId());
	item.put("account", this.itemAccountValue.getText());
	this.advancedAccountingRuleFeeIncomeValue.add(item);
	target.add(this.form);
    }

    protected void penaltyChargePopupOnClose(String elementId, AjaxRequestTarget target) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("uuid", UUID.randomUUID().toString());
	item.put("chargeId", this.itemChargeValue.getId());
	item.put("charge", this.itemChargeValue.getText());
	item.put("accountId", this.itemAccountValue.getId());
	item.put("account", this.itemAccountValue.getText());
	this.advancedAccountingRulePenaltyIncomeValue.add(item);
	target.add(this.form);
    }

    protected void paymentPopupOnClose(String elementId, AjaxRequestTarget target) {
	Map<String, Object> item = Maps.newHashMap();
	item.put("uuid", UUID.randomUUID().toString());
	item.put("paymentId", this.itemPaymentValue.getId());
	item.put("payment", this.itemPaymentValue.getText());
	item.put("accountId", this.itemAccountValue.getId());
	item.put("account", this.itemAccountValue.getText());
	this.advancedAccountingRuleFundSourceValue.add(item);
	target.add(this.form);
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
	this.advancedAccountingRuleContainer = new WebMarkupContainer("advancedAccountingRuleContainer");
	this.form.add(this.advancedAccountingRuleContainer);

	{
	    List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
	    advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment",
		    this::advancedAccountingRuleFundSourcePaymentColumn));
	    advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account",
		    this::advancedAccountingRuleFundSourceAccountColumn));
	    advancedAccountingRuleFundSourceColumn
		    .add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceActionItem,
			    this::advancedAccountingRuleFundSourceActionClick));
	    this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(
		    this.advancedAccountingRuleFundSourceValue);
	    this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable",
		    advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFundSourceTable);
	    this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
	    this.advancedAccountingRuleFundSourceTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

	    AjaxLink<Void> advancedAccountingRuleFundSourceAddLink = new AjaxLink<>(
		    "advancedAccountingRuleFundSourceAddLink");
	    advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
	    this.advancedAccountingRuleContainer.add(advancedAccountingRuleFundSourceAddLink);
	}

	{

	    List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
	    advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge",
		    this::advancedAccountingRuleFeeIncomeChargeColumn));
	    advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account",
		    this::advancedAccountingRuleFeeIncomeAccountColumn));
	    advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"),
		    this::advancedAccountingRuleFeeIncomeActionItem, this::advancedAccountingRuleFeeIncomeActionClick));
	    this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(
		    this.advancedAccountingRuleFeeIncomeValue);
	    this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable",
		    advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRuleFeeIncomeTable);
	    this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
	    this.advancedAccountingRuleFeeIncomeTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

	    this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
	    advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
	    this.advancedAccountingRuleContainer.add(advancedAccountingRuleFeeIncomeAddLink);
	}

	{
	    List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
	    advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge",
		    this::advancedAccountingRulePenaltyIncomeChargeColumn));
	    advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account",
		    "account", this::advancedAccountingRulePenaltyIncomeAccountColumn));
	    advancedAccountingRulePenaltyIncomeColumn.add(
		    new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeActionItem,
			    this::advancedAccountingRulePenaltyIncomeActionClick));
	    this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(
		    this.advancedAccountingRulePenaltyIncomeValue);
	    this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable",
		    advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
	    this.advancedAccountingRuleContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
	    this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(
		    this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
	    this.advancedAccountingRulePenaltyIncomeTable
		    .addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

	    this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>(
		    "advancedAccountingRulePenaltyIncomeAddLink");
	    advancedAccountingRulePenaltyIncomeAddLink
		    .setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
	    this.advancedAccountingRuleContainer.add(advancedAccountingRulePenaltyIncomeAddLink);
	}
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.itemChargeValue = null;
	this.itemAccountValue = null;
	this.penaltyChargePopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeChargeColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected void advancedAccountingRulePenaltyIncomeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRulePenaltyIncomeValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.itemChargeValue = null;
	this.itemAccountValue = null;
	this.feeChargePopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeChargeColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected void advancedAccountingRuleFeeIncomeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRuleFeeIncomeValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected List<ActionItem> feeIncomeActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void advancedAccountingRuleFundSourceActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.size(); i++) {
	    Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.advancedAccountingRuleFundSourceValue.remove(index);
	}
	ajaxRequestTarget.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected List<ActionItem> advancedAccountingRuleFundSourceActionItem(String s,
	    Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.paymentPopup.show(target);
	return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourcePaymentColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel advancedAccountingRuleFundSourceAccountColumn(String jdbcColumn, IModel<String> display,
	    Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected List<ActionItem> fundSourceActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initAccountingCash() {
	this.cashContainer = new WebMarkupContainer("cashContainer");
	this.form.add(this.cashContainer);

	this.cashSavingReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashSavingReferenceProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingReferenceProvider.applyWhere("classification_enum", "classification_enum = 1");
	this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField",
		new PropertyModel<>(this, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
	this.cashSavingReferenceField.setRequired(false);
	this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingReferenceField);
	this.cashSavingReferenceFeedback = new TextFeedbackPanel("cashSavingReferenceFeedback",
		this.cashSavingReferenceField);
	this.cashContainer.add(this.cashSavingReferenceFeedback);

	this.cashOverdraftPortfolioProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashOverdraftPortfolioProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashOverdraftPortfolioProvider.applyWhere("classification_enum", "classification_enum = 1");
	this.cashOverdraftPortfolioField = new Select2SingleChoice<>("cashOverdraftPortfolioField",
		new PropertyModel<>(this, "cashOverdraftPortfolioValue"), this.cashOverdraftPortfolioProvider);
	this.cashOverdraftPortfolioField.setRequired(false);
	this.cashOverdraftPortfolioField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashOverdraftPortfolioField);
	this.cashOverdraftPortfolioFeedback = new TextFeedbackPanel("cashOverdraftPortfolioFeedback",
		this.cashOverdraftPortfolioField);
	this.cashContainer.add(this.cashOverdraftPortfolioFeedback);

	this.cashSavingControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashSavingControlProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingControlProvider.applyWhere("classification_enum", "classification_enum = 1");
	this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField",
		new PropertyModel<>(this, "cashSavingControlValue"), this.cashSavingControlProvider);
	this.cashSavingControlField.setRequired(false);
	this.cashSavingControlField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingControlField);
	this.cashSavingControlFeedback = new TextFeedbackPanel("cashSavingControlFeedback",
		this.cashSavingControlField);
	this.cashContainer.add(this.cashSavingControlFeedback);

	this.cashSavingsTransfersInSuspenseProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashSavingsTransfersInSuspenseProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashSavingsTransfersInSuspenseProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashSavingsTransfersInSuspenseField = new Select2SingleChoice<>("cashSavingsTransfersInSuspenseField",
		new PropertyModel<>(this, "cashSavingsTransfersInSuspenseValue"),
		this.cashSavingsTransfersInSuspenseProvider);
	this.cashSavingsTransfersInSuspenseField.setRequired(false);
	this.cashSavingsTransfersInSuspenseField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashSavingsTransfersInSuspenseField);
	this.cashSavingsTransfersInSuspenseFeedback = new TextFeedbackPanel("cashSavingsTransfersInSuspenseFeedback",
		this.cashSavingsTransfersInSuspenseField);
	this.cashContainer.add(this.cashSavingsTransfersInSuspenseFeedback);

	this.cashEscheatLiabilityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashEscheatLiabilityProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashEscheatLiabilityProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashEscheatLiabilityField = new Select2SingleChoice<>("cashEscheatLiabilityField",
		new PropertyModel<>(this, "cashEscheatLiabilityValue"), this.cashEscheatLiabilityProvider);
	this.cashEscheatLiabilityField.setRequired(false);
	this.cashEscheatLiabilityField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashEscheatLiabilityField);
	this.cashEscheatLiabilityFeedback = new TextFeedbackPanel("cashEscheatLiabilityFeedback",
		this.cashEscheatLiabilityField);
	this.cashContainer.add(this.cashEscheatLiabilityFeedback);

	this.cashInterestOnSavingProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashInterestOnSavingProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashInterestOnSavingProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField",
		new PropertyModel<>(this, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
	this.cashInterestOnSavingField.setRequired(false);
	this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashInterestOnSavingField);
	this.cashInterestOnSavingFeedback = new TextFeedbackPanel("cashInterestOnSavingFeedback",
		this.cashInterestOnSavingField);
	this.cashContainer.add(this.cashInterestOnSavingFeedback);

	this.cashWriteOffProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashWriteOffProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashWriteOffProvider.applyWhere("classification_enum", "classification_enum = 4");
	this.cashWriteOffField = new Select2SingleChoice<>("cashWriteOffField",
		new PropertyModel<>(this, "cashWriteOffValue"), this.cashWriteOffProvider);
	this.cashWriteOffField.setRequired(false);
	this.cashWriteOffField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashWriteOffField);
	this.cashWriteOffFeedback = new TextFeedbackPanel("cashWriteOffFeedback", this.cashWriteOffField);
	this.cashContainer.add(this.cashWriteOffFeedback);

	this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = 5");
	this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField",
		new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
	this.cashIncomeFromFeeField.setRequired(false);
	this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashIncomeFromFeeField);
	this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback",
		this.cashIncomeFromFeeField);
	this.cashContainer.add(this.cashIncomeFromFeeFeedback);

	this.cashIncomeFromPenaltiesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashIncomeFromPenaltiesProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashIncomeFromPenaltiesProvider.applyWhere("classification_enum", "classification_enum = 2");
	this.cashIncomeFromPenaltiesField = new Select2SingleChoice<>("cashIncomeFromPenaltiesField",
		new PropertyModel<>(this, "cashIncomeFromPenaltiesValue"), this.cashIncomeFromPenaltiesProvider);
	this.cashIncomeFromPenaltiesField.setRequired(false);
	this.cashIncomeFromPenaltiesField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashIncomeFromPenaltiesField);
	this.cashIncomeFromPenaltiesFeedback = new TextFeedbackPanel("cashIncomeFromPenaltiesFeedback",
		this.cashIncomeFromPenaltiesField);
	this.cashContainer.add(this.cashIncomeFromPenaltiesFeedback);

	this.cashOverdraftInterestIncomeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.cashOverdraftInterestIncomeProvider.applyWhere("account_usage", "account_usage = 1");
	this.cashOverdraftInterestIncomeProvider.applyWhere("classification_enum", "classification_enum = 2");
	this.cashOverdraftInterestIncomeField = new Select2SingleChoice<>("cashOverdraftInterestIncomeField",
		new PropertyModel<>(this, "cashOverdraftInterestIncomeValue"),
		this.cashOverdraftInterestIncomeProvider);
	this.cashOverdraftInterestIncomeField.setRequired(false);
	this.cashOverdraftInterestIncomeField.add(new OnChangeAjaxBehavior());
	this.cashContainer.add(this.cashOverdraftInterestIncomeField);
	this.cashOverdraftInterestIncomeFeedback = new TextFeedbackPanel("cashOverdraftInterestIncomeFeedback",
		this.cashOverdraftInterestIncomeField);
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

	target.add(this.form);
	return false;
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
	Map<String, Object> chargeObject = jdbcTemplate.queryForMap(
		"select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?",
		chargeId);
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
	target.add(this.form);
    }

    protected void initCharge() {
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

	AjaxLink<Void> chargeAddLink = new AjaxLink<>("chargeAddLink");
	chargeAddLink.setOnClick(this::chargeAddLinkClick);
	this.form.add(chargeAddLink);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
	this.itemChargeValue = null;
	this.chargePopup.show(target);
	return false;
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(value));
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Number value = (Number) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(value.doubleValue())));
	}
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String value = (String) model.get(jdbcColumn);
	if (value == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(value));
	}
    }

    protected void chargeActionClick(String s, Map<String, Object> stringObjectMap,
	    AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.chargeValue.size(); i++) {
	    Map<String, Object> column = this.chargeValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.chargeValue.remove(index);
	}
	ajaxRequestTarget.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initSetting() {
	this.settingMinimumOpeningBalanceField = new TextField<>("settingMinimumOpeningBalanceField",
		new PropertyModel<>(this, "settingMinimumOpeningBalanceValue"));
	this.settingMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingMinimumOpeningBalanceField);
	this.settingMinimumOpeningBalanceFeedback = new TextFeedbackPanel("settingMinimumOpeningBalanceFeedback",
		this.settingMinimumOpeningBalanceField);
	this.form.add(this.settingMinimumOpeningBalanceFeedback);

	this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField",
		new PropertyModel<>(this, "settingLockInPeriodValue"));
	this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingLockInPeriodField);
	this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback",
		this.settingLockInPeriodField);
	this.form.add(this.settingLockInPeriodFeedback);

	this.settingLockInTypeProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0,
		new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
	this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
	this.settingLockInTypeField.setRequired(true);
	this.form.add(this.settingLockInTypeField);
	this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback",
		this.settingLockInTypeField);
	this.form.add(this.settingLockInTypeFeedback);

	this.settingApplyWithdrawalFeeForTransferField = new CheckBox("settingApplyWithdrawalFeeForTransferField",
		new PropertyModel<>(this, "settingApplyWithdrawalFeeForTransferValue"));
	this.settingApplyWithdrawalFeeForTransferField.setRequired(false);
	this.settingApplyWithdrawalFeeForTransferField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingApplyWithdrawalFeeForTransferField);
	this.settingApplyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel(
		"settingApplyWithdrawalFeeForTransferFeedback", this.settingApplyWithdrawalFeeForTransferField);
	this.form.add(this.settingApplyWithdrawalFeeForTransferFeedback);

	this.settingBalanceRequiredForInterestCalculationField = new TextField<>(
		"settingBalanceRequiredForInterestCalculationField",
		new PropertyModel<>(this, "settingBalanceRequiredForInterestCalculationValue"));
	this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingBalanceRequiredForInterestCalculationField);
	this.settingBalanceRequiredForInterestCalculationFeedback = new TextFeedbackPanel(
		"settingBalanceRequiredForInterestCalculationFeedback",
		this.settingBalanceRequiredForInterestCalculationField);
	this.form.add(this.settingBalanceRequiredForInterestCalculationFeedback);

	this.settingEnforceMinimumBalanceField = new CheckBox("settingEnforceMinimumBalanceField",
		new PropertyModel<>(this, "settingEnforceMinimumBalanceValue"));
	this.settingEnforceMinimumBalanceField.setRequired(false);
	this.settingEnforceMinimumBalanceField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingEnforceMinimumBalanceField);
	this.settingEnforceMinimumBalanceFeedback = new TextFeedbackPanel("settingEnforceMinimumBalanceFeedback",
		this.settingEnforceMinimumBalanceField);
	this.form.add(this.settingEnforceMinimumBalanceFeedback);

	this.settingMinimumBalanceField = new TextField<>("settingMinimumBalanceField",
		new PropertyModel<>(this, "settingMinimumBalanceValue"));
	this.settingMinimumBalanceField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingMinimumBalanceField);
	this.settingMinimumBalanceFeedback = new TextFeedbackPanel("settingMinimumBalanceFeedback",
		this.settingMinimumBalanceField);
	this.form.add(this.settingMinimumBalanceFeedback);

	this.settingOverdraftAllowedField = new CheckBox("settingOverdraftAllowedField",
		new PropertyModel<>(this, "settingOverdraftAllowedValue"));
	this.settingOverdraftAllowedField.setRequired(false);
	this.settingOverdraftAllowedField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingOverdraftAllowedField);
	this.settingOverdraftAllowedFeedback = new TextFeedbackPanel("settingOverdraftAllowedFeedback",
		this.settingOverdraftAllowedField);
	this.form.add(this.settingOverdraftAllowedFeedback);

	this.settingOverdraftAllowedContainer = new WebMarkupContainer("settingOverdraftAllowedContainer");
	this.form.add(this.settingOverdraftAllowedContainer);

	this.settingMaximumOverdraftAmountLimitField = new TextField<>("settingMaximumOverdraftAmountLimitField",
		new PropertyModel<>(this, "settingMaximumOverdraftAmountLimitValue"));
	this.settingMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
	this.settingOverdraftAllowedContainer.add(this.settingMaximumOverdraftAmountLimitField);
	this.settingMaximumOverdraftAmountLimitFeedback = new TextFeedbackPanel(
		"settingMaximumOverdraftAmountLimitFeedback", this.settingMaximumOverdraftAmountLimitField);
	this.settingOverdraftAllowedContainer.add(this.settingMaximumOverdraftAmountLimitFeedback);

	this.settingNominalAnnualInterestForOverdraftField = new TextField<>(
		"settingNominalAnnualInterestForOverdraftField",
		new PropertyModel<>(this, "settingNominalAnnualInterestForOverdraftValue"));
	this.settingNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
	this.settingOverdraftAllowedContainer.add(this.settingNominalAnnualInterestForOverdraftField);
	this.settingNominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel(
		"settingNominalAnnualInterestForOverdraftFeedback", this.settingNominalAnnualInterestForOverdraftField);
	this.settingOverdraftAllowedContainer.add(this.settingNominalAnnualInterestForOverdraftFeedback);

	this.settingMinOverdraftRequiredForInterestCalculationField = new TextField<>(
		"settingMinOverdraftRequiredForInterestCalculationField",
		new PropertyModel<>(this, "settingMinOverdraftRequiredForInterestCalculationValue"));
	this.settingMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
	this.settingOverdraftAllowedContainer.add(this.settingMinOverdraftRequiredForInterestCalculationField);
	this.settingMinOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel(
		"settingMinOverdraftRequiredForInterestCalculationFeedback",
		this.settingMinOverdraftRequiredForInterestCalculationField);
	this.settingOverdraftAllowedContainer.add(this.settingMinOverdraftRequiredForInterestCalculationFeedback);

	this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField",
		new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
	this.settingWithholdTaxApplicableField.setRequired(false);
	this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingWithholdTaxApplicableField);
	this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback",
		this.settingWithholdTaxApplicableField);
	this.form.add(this.settingWithholdTaxApplicableFeedback);

	this.settingWithholdTaxApplicableContainer = new WebMarkupContainer("settingWithholdTaxApplicableContainer");
	this.form.add(this.settingWithholdTaxApplicableContainer);

	this.settingTaxGroupProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", 0,
		new PropertyModel<>(this, "settingTaxGroupValue"), this.settingTaxGroupProvider);
	this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
	this.settingTaxGroupField.setRequired(true);
	this.settingWithholdTaxApplicableContainer.add(this.settingTaxGroupField);
	this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
	this.settingWithholdTaxApplicableContainer.add(this.settingTaxGroupFeedback);

	this.settingEnableDormancyTrackingField = new CheckBox("settingEnableDormancyTrackingField",
		new PropertyModel<>(this, "settingEnableDormancyTrackingValue"));
	this.settingEnableDormancyTrackingField.setRequired(false);
	this.settingEnableDormancyTrackingField.add(new OnChangeAjaxBehavior());
	this.form.add(this.settingEnableDormancyTrackingField);
	this.settingEnableDormancyTrackingFeedback = new TextFeedbackPanel("settingEnableDormancyTrackingFeedback",
		this.settingEnableDormancyTrackingField);
	this.form.add(this.settingEnableDormancyTrackingFeedback);

	this.settingEnableDormancyTrackingContainer = new WebMarkupContainer("settingEnableDormancyTrackingContainer");
	this.form.add(this.settingEnableDormancyTrackingContainer);

	this.settingNumberOfDaysToInactiveSubStatusField = new TextField<>(
		"settingNumberOfDaysToInactiveSubStatusField",
		new PropertyModel<>(this, "settingNumberOfDaysToInactiveSubStatusValue"));
	this.settingNumberOfDaysToInactiveSubStatusField.add(new OnChangeAjaxBehavior());
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToInactiveSubStatusField);
	this.settingNumberOfDaysToInactiveSubStatusFeedback = new TextFeedbackPanel(
		"settingNumberOfDaysToInactiveSubStatusFeedback", this.settingNumberOfDaysToInactiveSubStatusField);
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToInactiveSubStatusFeedback);

	this.settingNumberOfDaysToDormantSubStatusField = new TextField<>("settingNumberOfDaysToDormantSubStatusField",
		new PropertyModel<>(this, "settingNumberOfDaysToDormantSubStatusValue"));
	this.settingNumberOfDaysToDormantSubStatusField.add(new OnChangeAjaxBehavior());
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToDormantSubStatusField);
	this.settingNumberOfDaysToDormantSubStatusFeedback = new TextFeedbackPanel(
		"settingNumberOfDaysToDormantSubStatusFeedback", this.settingNumberOfDaysToDormantSubStatusField);
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToDormantSubStatusFeedback);

	this.settingNumberOfDaysToEscheatField = new TextField<>("settingNumberOfDaysToEscheatField",
		new PropertyModel<>(this, "settingNumberOfDaysToEscheatValue"));
	this.settingNumberOfDaysToEscheatField.add(new OnChangeAjaxBehavior());
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToEscheatField);
	this.settingNumberOfDaysToEscheatFeedback = new TextFeedbackPanel("settingNumberOfDaysToEscheatFeedback",
		this.settingNumberOfDaysToEscheatField);
	this.settingEnableDormancyTrackingContainer.add(this.settingNumberOfDaysToEscheatFeedback);
    }

    protected void initTerm() {

	this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField",
		new PropertyModel<>(this, "termNominalAnnualInterestValue"));
	this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());
	this.form.add(this.termNominalAnnualInterestField);
	this.termNominalAnnualInterestFeedback = new TextFeedbackPanel("termNominalAnnualInterestFeedback",
		this.termNominalAnnualInterestField);
	this.form.add(this.termNominalAnnualInterestFeedback);

	this.termInterestCompoundingPeriodProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", 0,
		new PropertyModel<>(this, "termInterestCompoundingPeriodValue"),
		this.termInterestCompoundingPeriodProvider);
	this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
	this.termInterestCompoundingPeriodField.setRequired(true);
	this.form.add(this.termInterestCompoundingPeriodField);
	this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback",
		this.termInterestCompoundingPeriodField);
	this.form.add(this.termInterestCompoundingPeriodFeedback);

	this.termInterestCalculatedUsingProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", 0,
		new PropertyModel<>(this, "termInterestCalculatedUsingValue"),
		this.termInterestCalculatedUsingProvider);
	this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
	this.termInterestCalculatedUsingField.setRequired(true);
	this.form.add(this.termInterestCalculatedUsingField);
	this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback",
		this.termInterestCalculatedUsingField);
	this.form.add(this.termInterestCalculatedUsingFeedback);

	this.termInterestPostingPeriodProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", 0,
		new PropertyModel<>(this, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
	this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
	this.termInterestPostingPeriodField.setRequired(true);
	this.form.add(this.termInterestPostingPeriodField);
	this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback",
		this.termInterestPostingPeriodField);
	this.form.add(this.termInterestPostingPeriodFeedback);

	this.termDaysInYearProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.termDaysInYearField = new Select2SingleChoice<>("termDaysInYearField", 0,
		new PropertyModel<>(this, "termDaysInYearValue"), this.termDaysInYearProvider);
	this.termDaysInYearField.add(new OnChangeAjaxBehavior());
	this.termDaysInYearField.setRequired(true);
	this.form.add(this.termDaysInYearField);
	this.termDaysInYearFeedback = new TextFeedbackPanel("termDaysInYearFeedback", this.termDaysInYearField);
	this.form.add(this.termDaysInYearFeedback);
    }

    protected void initCurrency() {
	this.currencyCodeProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0,
		new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
	this.currencyCodeField.add(new OnChangeAjaxBehavior());
	this.currencyCodeField.setRequired(true);
	this.form.add(this.currencyCodeField);
	this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
	this.form.add(this.currencyCodeFeedback);

	this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField",
		new PropertyModel<>(this, "currencyDecimalPlaceValue"));
	this.currencyDecimalPlaceField.setRequired(true);
	this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
	this.currencyDecimalPlaceField.add(RangeValidator.range((int) 0, (int) 6));
	this.form.add(this.currencyDecimalPlaceField);
	this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback",
		this.currencyDecimalPlaceField);
	this.form.add(this.currencyDecimalPlaceFeedback);

	this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField",
		new PropertyModel<>(this, "currencyMultipleOfValue"));
	this.currencyMultipleOfField.setRequired(false);
	this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
	this.currencyMultipleOfField.add(RangeValidator.minimum((int) 1));
	this.form.add(this.currencyMultipleOfField);
	this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback",
		this.currencyMultipleOfField);
	this.form.add(this.currencyMultipleOfFeedback);
    }

    protected void initDetail() {
	this.detailProductNameField = new TextField<>("detailProductNameField",
		new PropertyModel<>(this, "detailProductNameValue"));
	this.detailProductNameField.setRequired(true);
	this.detailProductNameField.add(new OnChangeAjaxBehavior());
	this.form.add(this.detailProductNameField);
	this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback",
		this.detailProductNameField);
	this.form.add(this.detailProductNameFeedback);

	this.detailShortNameField = new TextField<>("detailShortNameField",
		new PropertyModel<>(this, "detailShortNameValue"));
	this.detailShortNameField.setRequired(true);
	this.detailShortNameField.add(new OnChangeAjaxBehavior());
	this.form.add(this.detailShortNameField);
	this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
	this.form.add(this.detailShortNameFeedback);

	this.detailDescriptionField = new TextField<>("detailDescriptionField",
		new PropertyModel<>(this, "detailDescriptionValue"));
	this.detailDescriptionField.setRequired(false);
	this.detailDescriptionField.add(new OnChangeAjaxBehavior());
	this.form.add(this.detailDescriptionField);
	this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback",
		this.detailDescriptionField);
	this.form.add(this.detailDescriptionFeedback);
    }

    private void saveButtonSubmit(Button button) {

    }

}
