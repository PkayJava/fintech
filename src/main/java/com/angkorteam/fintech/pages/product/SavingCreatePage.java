package com.angkorteam.fintech.pages.product;

import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;

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

    private Integer decimalPlaceValue;
    private TextField<Integer> decimalPlaceField;
    private TextFeedbackPanel decimalPlaceFeedback;

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

    private WebMarkupContainer advancedAccountingRuleFundSourceContainer;
    private List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    private ListDataProvider advancedAccountingRuleFundSourceProvider;

    private WebMarkupContainer advancedAccountingRuleFeeIncomeContainer;
    private List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    private ListDataProvider advancedAccountingRuleFeeIncomeProvider;

    private WebMarkupContainer advancedAccountingRulePenaltyIncomeContainer;
    private List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    private ListDataProvider advancedAccountingRulePenaltyIncomeProvider;

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

        initDetail();
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
