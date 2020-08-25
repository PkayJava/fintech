//package com.angkorteam.fintech.widget.product.savings;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.validation.ValidationError;
//import org.apache.wicket.validation.validator.RangeValidator;
//
//import com.angkorteam.fintech.ddl.MTaxGroup;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
//import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
//import com.angkorteam.fintech.provider.LockInTypeProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;
//
//public class SettingsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorSetting;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock settingMinimumOpeningBalanceBlock;
//    protected UIContainer settingMinimumOpeningBalanceIContainer;
//    protected TextField<Double> settingMinimumOpeningBalanceField;
//
//    protected UIBlock settingLockInPeriodBlock;
//    protected UIContainer settingLockInPeriodIContainer;
//    protected TextField<Long> settingLockInPeriodField;
//
//    protected UIBlock settingLockInTypeBlock;
//    protected UIContainer settingLockInTypeIContainer;
//    protected LockInTypeProvider settingLockInTypeProvider;
//    protected Select2SingleChoice<Option> settingLockInTypeField;
//
//    protected UIRow row2;
//
//    protected UIBlock settingApplyWithdrawalFeeForTransferBlock;
//    protected UIContainer settingApplyWithdrawalFeeForTransferIContainer;
//    protected CheckBox settingApplyWithdrawalFeeForTransferField;
//
//    protected UIBlock settingBalanceRequiredForInterestCalculationBlock;
//    protected UIContainer settingBalanceRequiredForInterestCalculationIContainer;
//    protected TextField<Double> settingBalanceRequiredForInterestCalculationField;
//
//    protected UIBlock row2Block1;
//
//    protected UIRow row3;
//
//    protected UIBlock settingEnforceMinimumBalanceBlock;
//    protected UIContainer settingEnforceMinimumBalanceIContainer;
//    protected CheckBox settingEnforceMinimumBalanceField;
//
//    protected UIBlock settingMinimumBalanceBlock;
//    protected UIContainer settingMinimumBalanceIContainer;
//    protected TextField<Double> settingMinimumBalanceField;
//
//    protected UIBlock row3Block1;
//
//    protected UIRow row4;
//
//    protected UIBlock settingOverdraftAllowedBlock;
//    protected UIContainer settingOverdraftAllowedIContainer;
//    protected CheckBox settingOverdraftAllowedField;
//
//    protected UIBlock settingMaximumOverdraftAmountLimitBlock;
//    protected UIContainer settingMaximumOverdraftAmountLimitIContainer;
//    protected TextField<Double> settingMaximumOverdraftAmountLimitField;
//
//    protected UIRow row5;
//
//    protected UIBlock settingNominalAnnualInterestForOverdraftBlock;
//    protected UIContainer settingNominalAnnualInterestForOverdraftIContainer;
//    protected TextField<Double> settingNominalAnnualInterestForOverdraftField;
//
//    protected UIBlock settingMinOverdraftRequiredForInterestCalculationBlock;
//    protected UIContainer settingMinOverdraftRequiredForInterestCalculationIContainer;
//    protected TextField<Double> settingMinOverdraftRequiredForInterestCalculationField;
//
//    protected UIRow row6;
//
//    protected UIBlock settingWithholdTaxApplicableBlock;
//    protected UIContainer settingWithholdTaxApplicableIContainer;
//    protected CheckBox settingWithholdTaxApplicableField;
//
//    protected UIBlock settingTaxGroupBlock;
//    protected UIContainer settingTaxGroupIContainer;
//    protected SingleChoiceProvider settingTaxGroupProvider;
//    protected Select2SingleChoice<Option> settingTaxGroupField;
//
//    protected UIRow row7;
//
//    protected UIBlock settingEnableDormancyTrackingBlock;
//    protected UIContainer settingEnableDormancyTrackingIContainer;
//    protected CheckBox settingEnableDormancyTrackingField;
//    protected PropertyModel<Boolean> settingEnableDormancyTrackingValue;
//
//    protected UIBlock settingNumberOfDaysToInactiveSubStatusBlock;
//    protected UIContainer settingNumberOfDaysToInactiveSubStatusIContainer;
//    protected TextField<Long> settingNumberOfDaysToInactiveSubStatusField;
//    protected PropertyModel<Long> settingNumberOfDaysToInactiveSubStatusValue;
//
//    protected UIRow row8;
//
//    protected UIBlock settingNumberOfDaysToDormantSubStatusBlock;
//    protected UIContainer settingNumberOfDaysToDormantSubStatusIContainer;
//    protected TextField<Long> settingNumberOfDaysToDormantSubStatusField;
//    protected PropertyModel<Long> settingNumberOfDaysToDormantSubStatusValue;
//
//    protected UIBlock settingNumberOfDaysToEscheatBlock;
//    protected UIContainer settingNumberOfDaysToEscheatIContainer;
//    protected TextField<Long> settingNumberOfDaysToEscheatField;
//    protected PropertyModel<Long> settingNumberOfDaysToEscheatValue;
//
//    public SettingsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.settingLockInTypeProvider = new LockInTypeProvider();
//        this.settingTaxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
//
//        this.settingEnableDormancyTrackingValue = new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue");
//        this.settingNumberOfDaysToEscheatValue = new PropertyModel<>(this.itemPage, "settingNumberOfDaysToEscheatValue");
//        this.settingNumberOfDaysToDormantSubStatusValue = new PropertyModel<>(this.itemPage, "settingNumberOfDaysToDormantSubStatusValue");
//        this.settingNumberOfDaysToInactiveSubStatusValue = new PropertyModel<>(this.itemPage, "settingNumberOfDaysToInactiveSubStatusValue");
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.nextButton = new Button("nextButton");
//        this.nextButton.setOnSubmit(this::nextButtonSubmit);
//        this.nextButton.setOnError(this::nextButtonError);
//        this.form.add(this.nextButton);
//
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.settingMinimumOpeningBalanceBlock = this.row1.newUIBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
//        this.settingMinimumOpeningBalanceIContainer = this.settingMinimumOpeningBalanceBlock.newUIContainer("settingMinimumOpeningBalanceIContainer");
//        this.settingMinimumOpeningBalanceField = new TextField<>("settingMinimumOpeningBalanceField", new PropertyModel<>(this.itemPage, "settingMinimumOpeningBalanceValue"));
//        this.settingMinimumOpeningBalanceIContainer.add(this.settingMinimumOpeningBalanceField);
//        this.settingMinimumOpeningBalanceIContainer.newFeedback("settingMinimumOpeningBalanceFeedback", this.settingMinimumOpeningBalanceField);
//
//        this.settingLockInPeriodBlock = this.row1.newUIBlock("settingLockInPeriodBlock", Size.Four_4);
//        this.settingLockInPeriodIContainer = this.settingLockInPeriodBlock.newUIContainer("settingLockInPeriodIContainer");
//        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
//        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
//        this.settingLockInPeriodIContainer.newFeedback("settingLockInPeriodFeedback", this.settingLockInPeriodField);
//
//        this.settingLockInTypeBlock = this.row1.newUIBlock("settingLockInTypeBlock", Size.Four_4);
//        this.settingLockInTypeIContainer = this.settingLockInTypeBlock.newUIContainer("settingLockInTypeIContainer");
//        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
//        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
//        this.settingLockInTypeIContainer.newFeedback("settingLockInTypeFeedback", this.settingLockInTypeField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.settingApplyWithdrawalFeeForTransferBlock = this.row2.newUIBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Four_4);
//        this.settingApplyWithdrawalFeeForTransferIContainer = this.settingApplyWithdrawalFeeForTransferBlock.newUIContainer("settingApplyWithdrawalFeeForTransferIContainer");
//        this.settingApplyWithdrawalFeeForTransferField = new CheckBox("settingApplyWithdrawalFeeForTransferField", new PropertyModel<>(this.itemPage, "settingApplyWithdrawalFeeForTransferValue"));
//        this.settingApplyWithdrawalFeeForTransferIContainer.add(this.settingApplyWithdrawalFeeForTransferField);
//        this.settingApplyWithdrawalFeeForTransferIContainer.newFeedback("settingApplyWithdrawalFeeForTransferFeedback", this.settingApplyWithdrawalFeeForTransferField);
//
//        this.settingBalanceRequiredForInterestCalculationBlock = this.row2.newUIBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Four_4);
//        this.settingBalanceRequiredForInterestCalculationIContainer = this.settingBalanceRequiredForInterestCalculationBlock.newUIContainer("settingBalanceRequiredForInterestCalculationIContainer");
//        this.settingBalanceRequiredForInterestCalculationField = new TextField<>("settingBalanceRequiredForInterestCalculationField", new PropertyModel<>(this.itemPage, "settingBalanceRequiredForInterestCalculationValue"));
//        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationField);
//        this.settingBalanceRequiredForInterestCalculationIContainer.newFeedback("settingBalanceRequiredForInterestCalculationFeedback", this.settingBalanceRequiredForInterestCalculationField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Four_4);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.settingEnforceMinimumBalanceBlock = this.row3.newUIBlock("settingEnforceMinimumBalanceBlock", Size.Four_4);
//        this.settingEnforceMinimumBalanceIContainer = this.settingEnforceMinimumBalanceBlock.newUIContainer("settingEnforceMinimumBalanceIContainer");
//        this.settingEnforceMinimumBalanceField = new CheckBox("settingEnforceMinimumBalanceField", new PropertyModel<>(this.itemPage, "settingEnforceMinimumBalanceValue"));
//        this.settingEnforceMinimumBalanceIContainer.add(this.settingEnforceMinimumBalanceField);
//        this.settingEnforceMinimumBalanceIContainer.newFeedback("settingEnforceMinimumBalanceFeedback", this.settingEnforceMinimumBalanceField);
//
//        this.settingMinimumBalanceBlock = this.row3.newUIBlock("settingMinimumBalanceBlock", Size.Four_4);
//        this.settingMinimumBalanceIContainer = this.settingMinimumBalanceBlock.newUIContainer("settingMinimumBalanceIContainer");
//        this.settingMinimumBalanceField = new TextField<>("settingMinimumBalanceField", new PropertyModel<>(this.itemPage, "settingMinimumBalanceValue"));
//        this.settingMinimumBalanceIContainer.add(this.settingMinimumBalanceField);
//        this.settingMinimumBalanceIContainer.newFeedback("settingMinimumBalanceFeedback", this.settingMinimumBalanceField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Four_4);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.settingOverdraftAllowedBlock = this.row4.newUIBlock("settingOverdraftAllowedBlock", Size.Six_6);
//        this.settingOverdraftAllowedIContainer = this.settingOverdraftAllowedBlock.newUIContainer("settingOverdraftAllowedIContainer");
//        this.settingOverdraftAllowedField = new CheckBox("settingOverdraftAllowedField", new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue"));
//        this.settingOverdraftAllowedIContainer.add(this.settingOverdraftAllowedField);
//        this.settingOverdraftAllowedIContainer.newFeedback("settingOverdraftAllowedFeedback", this.settingOverdraftAllowedField);
//
//        this.settingMaximumOverdraftAmountLimitBlock = this.row4.newUIBlock("settingMaximumOverdraftAmountLimitBlock", Size.Six_6);
//        this.settingMaximumOverdraftAmountLimitIContainer = this.settingMaximumOverdraftAmountLimitBlock.newUIContainer("settingMaximumOverdraftAmountLimitIContainer");
//        this.settingMaximumOverdraftAmountLimitField = new TextField<>("settingMaximumOverdraftAmountLimitField", new PropertyModel<>(this.itemPage, "settingMaximumOverdraftAmountLimitValue"));
//        this.settingMaximumOverdraftAmountLimitIContainer.add(this.settingMaximumOverdraftAmountLimitField);
//        this.settingMaximumOverdraftAmountLimitIContainer.newFeedback("settingMaximumOverdraftAmountLimitFeedback", this.settingMaximumOverdraftAmountLimitField);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.settingNominalAnnualInterestForOverdraftBlock = this.row5.newUIBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Six_6);
//        this.settingNominalAnnualInterestForOverdraftIContainer = this.settingNominalAnnualInterestForOverdraftBlock.newUIContainer("settingNominalAnnualInterestForOverdraftIContainer");
//        this.settingNominalAnnualInterestForOverdraftField = new TextField<>("settingNominalAnnualInterestForOverdraftField", new PropertyModel<>(this.itemPage, "settingNominalAnnualInterestForOverdraftValue"));
//        this.settingNominalAnnualInterestForOverdraftIContainer.add(this.settingNominalAnnualInterestForOverdraftField);
//        this.settingNominalAnnualInterestForOverdraftIContainer.newFeedback("settingNominalAnnualInterestForOverdraftFeedback", this.settingNominalAnnualInterestForOverdraftField);
//
//        this.settingMinOverdraftRequiredForInterestCalculationBlock = this.row5.newUIBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Six_6);
//        this.settingMinOverdraftRequiredForInterestCalculationIContainer = this.settingMinOverdraftRequiredForInterestCalculationBlock.newUIContainer("settingMinOverdraftRequiredForInterestCalculationIContainer");
//        this.settingMinOverdraftRequiredForInterestCalculationField = new TextField<>("settingMinOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this.itemPage, "settingMinOverdraftRequiredForInterestCalculationValue"));
//        this.settingMinOverdraftRequiredForInterestCalculationIContainer.add(this.settingMinOverdraftRequiredForInterestCalculationField);
//        this.settingMinOverdraftRequiredForInterestCalculationIContainer.newFeedback("settingMinOverdraftRequiredForInterestCalculationFeedback", this.settingMinOverdraftRequiredForInterestCalculationField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.settingWithholdTaxApplicableBlock = this.row6.newUIBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
//        this.settingWithholdTaxApplicableIContainer = this.settingWithholdTaxApplicableBlock.newUIContainer("settingWithholdTaxApplicableIContainer");
//        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
//        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
//        this.settingWithholdTaxApplicableIContainer.newFeedback("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
//
//        this.settingTaxGroupBlock = this.row6.newUIBlock("settingTaxGroupBlock", Size.Six_6);
//        this.settingTaxGroupIContainer = this.settingTaxGroupBlock.newUIContainer("settingTaxGroupIContainer");
//        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"), this.settingTaxGroupProvider);
//        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
//        this.settingTaxGroupIContainer.newFeedback("settingTaxGroupFeedback", this.settingTaxGroupField);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.settingEnableDormancyTrackingBlock = this.row7.newUIBlock("settingEnableDormancyTrackingBlock", Size.Six_6);
//        this.settingEnableDormancyTrackingIContainer = this.settingEnableDormancyTrackingBlock.newUIContainer("settingEnableDormancyTrackingIContainer");
//        this.settingEnableDormancyTrackingField = new CheckBox("settingEnableDormancyTrackingField", this.settingEnableDormancyTrackingValue);
//        this.settingEnableDormancyTrackingIContainer.add(this.settingEnableDormancyTrackingField);
//        this.settingEnableDormancyTrackingIContainer.newFeedback("settingEnableDormancyTrackingFeedback", this.settingEnableDormancyTrackingField);
//
//        this.settingNumberOfDaysToInactiveSubStatusBlock = this.row7.newUIBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Six_6);
//        this.settingNumberOfDaysToInactiveSubStatusIContainer = this.settingNumberOfDaysToInactiveSubStatusBlock.newUIContainer("settingNumberOfDaysToInactiveSubStatusIContainer");
//        this.settingNumberOfDaysToInactiveSubStatusField = new TextField<>("settingNumberOfDaysToInactiveSubStatusField", this.settingNumberOfDaysToInactiveSubStatusValue);
//        this.settingNumberOfDaysToInactiveSubStatusIContainer.add(this.settingNumberOfDaysToInactiveSubStatusField);
//        this.settingNumberOfDaysToInactiveSubStatusIContainer.newFeedback("settingNumberOfDaysToInactiveSubStatusFeedback", this.settingNumberOfDaysToInactiveSubStatusField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.settingNumberOfDaysToEscheatBlock = this.row8.newUIBlock("settingNumberOfDaysToEscheatBlock", Size.Six_6);
//        this.settingNumberOfDaysToEscheatIContainer = this.settingNumberOfDaysToEscheatBlock.newUIContainer("settingNumberOfDaysToEscheatIContainer");
//        this.settingNumberOfDaysToEscheatField = new TextField<>("settingNumberOfDaysToEscheatField", this.settingNumberOfDaysToEscheatValue);
//        this.settingNumberOfDaysToEscheatIContainer.add(this.settingNumberOfDaysToEscheatField);
//        this.settingNumberOfDaysToEscheatIContainer.newFeedback("settingNumberOfDaysToEscheatFeedback", this.settingNumberOfDaysToEscheatField);
//
//        this.settingNumberOfDaysToDormantSubStatusBlock = this.row8.newUIBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Six_6);
//        this.settingNumberOfDaysToDormantSubStatusIContainer = this.settingNumberOfDaysToDormantSubStatusBlock.newUIContainer("settingNumberOfDaysToDormantSubStatusIContainer");
//        this.settingNumberOfDaysToDormantSubStatusField = new TextField<>("settingNumberOfDaysToDormantSubStatusField", this.settingNumberOfDaysToDormantSubStatusValue);
//        this.settingNumberOfDaysToDormantSubStatusIContainer.add(this.settingNumberOfDaysToDormantSubStatusField);
//        this.settingNumberOfDaysToDormantSubStatusIContainer.newFeedback("settingNumberOfDaysToDormantSubStatusFeedback", this.settingNumberOfDaysToDormantSubStatusField);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.settingMinimumOpeningBalanceField.setLabel(Model.of("Minimum opening balance"));
//        this.settingMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
//        this.settingMinimumOpeningBalanceField.setRequired(true);
//        this.settingMinimumOpeningBalanceField.add(RangeValidator.minimum(0d));
//
//        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
//        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
//        this.settingLockInPeriodField.setRequired(true);
//        this.settingLockInPeriodField.add(RangeValidator.minimum(1l));
//
//        this.settingLockInTypeField.setLabel(Model.of("Type"));
//        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
//        this.settingLockInTypeField.setRequired(true);
//
//        this.settingApplyWithdrawalFeeForTransferField.add(new OnChangeAjaxBehavior());
//
//        this.settingBalanceRequiredForInterestCalculationField.setLabel(Model.of("Balance Required For Interest Calculation"));
//        this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
//
//        this.settingEnforceMinimumBalanceField.add(new OnChangeAjaxBehavior());
//
//        this.settingMinimumBalanceField.add(new OnChangeAjaxBehavior());
//        this.settingMinimumBalanceField.setLabel(Model.of("Minimum Balance"));
//
//        this.settingOverdraftAllowedField.add(new OnChangeAjaxBehavior(this::settingOverdraftAllowedFieldUpdate));
//
//        this.settingMinOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
//        this.settingMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
//        this.settingMinOverdraftRequiredForInterestCalculationField.setRequired(true);
//        this.settingMinOverdraftRequiredForInterestCalculationField.add(RangeValidator.minimum(0d));
//
//        this.settingNominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal annual interest for overdraft"));
//        this.settingNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
//        this.settingNominalAnnualInterestForOverdraftField.setRequired(true);
//        this.settingNominalAnnualInterestForOverdraftField.add(RangeValidator.minimum(0d));
//
//        this.settingMaximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
//        this.settingMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
//        this.settingMaximumOverdraftAmountLimitField.setRequired(true);
//        this.settingMaximumOverdraftAmountLimitField.add(RangeValidator.minimum(0d));
//
//        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
//
//        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
//        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
//        this.settingTaxGroupField.setRequired(true);
//
//        this.settingEnableDormancyTrackingField.add(new OnChangeAjaxBehavior(this::settingEnableDormancyTrackingFieldUpdate));
//
//        this.settingNumberOfDaysToInactiveSubStatusField.setLabel(Model.of("Number of Days to Inactive sub-status"));
//        this.settingNumberOfDaysToInactiveSubStatusField.add(new OnChangeAjaxBehavior());
//        this.settingNumberOfDaysToInactiveSubStatusField.setRequired(true);
//        this.settingNumberOfDaysToInactiveSubStatusField.add(RangeValidator.minimum(1l));
//
//        this.settingNumberOfDaysToDormantSubStatusField.setLabel(Model.of("Number of Days to Dormant sub-status"));
//        this.settingNumberOfDaysToDormantSubStatusField.add(new OnChangeAjaxBehavior());
//        this.settingNumberOfDaysToDormantSubStatusField.setRequired(true);
//        this.settingNumberOfDaysToDormantSubStatusField.add(RangeValidator.minimum(1l));
//
//        this.settingNumberOfDaysToEscheatField.setLabel(Model.of("Number of Days to Escheat"));
//        this.settingNumberOfDaysToEscheatField.add(new OnChangeAjaxBehavior());
//        this.settingNumberOfDaysToEscheatField.setRequired(true);
//        this.settingNumberOfDaysToEscheatField.add(RangeValidator.minimum(1l));
//
//        settingOverdraftAllowedFieldUpdate(null);
//
//        settingWithholdTaxApplicableFieldUpdate(null);
//
//        settingEnableDormancyTrackingFieldUpdate(null);
//
//        this.form.add(new LamdaFormValidator(this::dormancyTrackingValidation, this.settingNumberOfDaysToInactiveSubStatusField, this.settingNumberOfDaysToDormantSubStatusField, this.settingNumberOfDaysToEscheatField));
//    }
//
//    protected void dormancyTrackingValidation(Form<?> form) {
//        if (this.settingEnableDormancyTrackingValue.getObject() != null && this.settingEnableDormancyTrackingValue.getObject()) {
//            if (this.settingNumberOfDaysToInactiveSubStatusValue.getObject() != null && this.settingNumberOfDaysToDormantSubStatusValue.getObject() != null && this.settingNumberOfDaysToEscheatValue.getObject() != null) {
//                if (this.settingNumberOfDaysToDormantSubStatusValue.getObject() <= this.settingNumberOfDaysToInactiveSubStatusValue.getObject()) {
//                    this.settingNumberOfDaysToInactiveSubStatusField.error(new ValidationError("Invalid"));
//                }
//                if (this.settingNumberOfDaysToEscheatValue.getObject() <= this.settingNumberOfDaysToDormantSubStatusValue.getObject()) {
//                    this.settingNumberOfDaysToDormantSubStatusField.error(new ValidationError("Invalid"));
//                }
//            }
//        }
//    }
//
//    protected boolean settingOverdraftAllowedFieldUpdate(AjaxRequestTarget target) {
//        PropertyModel<Boolean> settingOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue");
//        boolean visible = settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject();
//        this.settingMaximumOverdraftAmountLimitIContainer.setVisible(visible);
//        this.settingNominalAnnualInterestForOverdraftIContainer.setVisible(visible);
//        this.settingMinOverdraftRequiredForInterestCalculationIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.settingMaximumOverdraftAmountLimitBlock);
//            target.add(this.settingNominalAnnualInterestForOverdraftBlock);
//            target.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
//        }
//        return false;
//    }
//
//    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
//        PropertyModel<Boolean> settingWithholdTaxApplicableValue = new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue");
//        boolean visible = settingWithholdTaxApplicableValue.getObject() != null && settingWithholdTaxApplicableValue.getObject();
//        this.settingTaxGroupIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.settingTaxGroupBlock);
//        }
//        return false;
//    }
//
//    protected boolean settingEnableDormancyTrackingFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.settingEnableDormancyTrackingValue.getObject() != null && this.settingEnableDormancyTrackingValue.getObject();
//        this.settingNumberOfDaysToInactiveSubStatusIContainer.setVisible(visible);
//        this.settingNumberOfDaysToDormantSubStatusIContainer.setVisible(visible);
//        this.settingNumberOfDaysToEscheatIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
//            target.add(this.settingNumberOfDaysToDormantSubStatusBlock);
//            target.add(this.settingNumberOfDaysToEscheatBlock);
//        }
//        return false;
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_TERM);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorSetting.setObject(false);
//        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_CHARGE);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorSetting.setObject(true);
//    }
//
//}
