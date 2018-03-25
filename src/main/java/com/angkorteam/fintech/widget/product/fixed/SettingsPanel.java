package com.angkorteam.fintech.widget.product.fixed;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class SettingsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorSetting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock settingMinimumOpeningBalanceBlock;
    protected WebMarkupContainer settingMinimumOpeningBalanceIContainer;
    protected TextField<Double> settingMinimumOpeningBalanceField;
    protected TextFeedbackPanel settingMinimumOpeningBalanceFeedback;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected TextField<Long> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingApplyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferIContainer;
    protected CheckBox settingApplyWithdrawalFeeForTransferField;
    protected TextFeedbackPanel settingApplyWithdrawalFeeForTransferFeedback;

    protected WebMarkupBlock settingBalanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationIContainer;
    protected TextField<Double> settingBalanceRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingBalanceRequiredForInterestCalculationFeedback;

    protected WebMarkupBlock settingEnforceMinimumBalanceBlock;
    protected WebMarkupContainer settingEnforceMinimumBalanceIContainer;
    protected CheckBox settingEnforceMinimumBalanceField;
    protected TextFeedbackPanel settingEnforceMinimumBalanceFeedback;

    protected WebMarkupBlock settingMinimumBalanceBlock;
    protected WebMarkupContainer settingMinimumBalanceIContainer;
    protected TextField<Double> settingMinimumBalanceField;
    protected TextFeedbackPanel settingMinimumBalanceFeedback;

    protected WebMarkupBlock settingOverdraftAllowedBlock;
    protected WebMarkupContainer settingOverdraftAllowedIContainer;
    protected CheckBox settingOverdraftAllowedField;
    protected TextFeedbackPanel settingOverdraftAllowedFeedback;

    protected WebMarkupBlock settingMaximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer settingMaximumOverdraftAmountLimitIContainer;
    protected TextField<Double> settingMaximumOverdraftAmountLimitField;
    protected TextFeedbackPanel settingMaximumOverdraftAmountLimitFeedback;

    protected WebMarkupBlock settingNominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftIContainer;
    protected TextField<Double> settingNominalAnnualInterestForOverdraftField;
    protected TextFeedbackPanel settingNominalAnnualInterestForOverdraftFeedback;

    protected WebMarkupBlock settingMinOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationIContainer;
    protected TextField<Double> settingMinOverdraftRequiredForInterestCalculationField;
    protected TextFeedbackPanel settingMinOverdraftRequiredForInterestCalculationFeedback;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableIContainer;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupIContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    protected WebMarkupBlock settingEnableDormancyTrackingBlock;
    protected WebMarkupContainer settingEnableDormancyTrackingIContainer;
    protected CheckBox settingEnableDormancyTrackingField;
    protected TextFeedbackPanel settingEnableDormancyTrackingFeedback;

    protected WebMarkupBlock settingNumberOfDaysToInactiveSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusIContainer;
    protected TextField<Long> settingNumberOfDaysToInactiveSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToInactiveSubStatusFeedback;

    protected WebMarkupBlock settingNumberOfDaysToDormantSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusIContainer;
    protected TextField<Long> settingNumberOfDaysToDormantSubStatusField;
    protected TextFeedbackPanel settingNumberOfDaysToDormantSubStatusFeedback;

    protected WebMarkupBlock settingNumberOfDaysToEscheatBlock;
    protected WebMarkupContainer settingNumberOfDaysToEscheatIContainer;
    protected TextField<Long> settingNumberOfDaysToEscheatField;
    protected TextFeedbackPanel settingNumberOfDaysToEscheatFeedback;

    public SettingsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        this.settingNumberOfDaysToEscheatBlock = new WebMarkupBlock("settingNumberOfDaysToEscheatBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysToEscheatBlock);
        this.settingNumberOfDaysToEscheatIContainer = new WebMarkupContainer("settingNumberOfDaysToEscheatIContainer");
        this.settingNumberOfDaysToEscheatBlock.add(this.settingNumberOfDaysToEscheatIContainer);
        this.settingNumberOfDaysToEscheatField = new TextField<>("settingNumberOfDaysToEscheatField", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToEscheatValue"));
        this.settingNumberOfDaysToEscheatField.setLabel(Model.of("Number of Days to Escheat"));
        this.settingNumberOfDaysToEscheatField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToEscheatIContainer.add(this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatFeedback = new TextFeedbackPanel("settingNumberOfDaysToEscheatFeedback", this.settingNumberOfDaysToEscheatField);
        this.settingNumberOfDaysToEscheatIContainer.add(this.settingNumberOfDaysToEscheatFeedback);

        this.settingNumberOfDaysToDormantSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysToDormantSubStatusBlock);
        this.settingNumberOfDaysToDormantSubStatusIContainer = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusIContainer");
        this.settingNumberOfDaysToDormantSubStatusBlock.add(this.settingNumberOfDaysToDormantSubStatusIContainer);
        this.settingNumberOfDaysToDormantSubStatusField = new TextField<>("settingNumberOfDaysToDormantSubStatusField", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToDormantSubStatusValue"));
        this.settingNumberOfDaysToDormantSubStatusField.setLabel(Model.of("Number of Days to Dormant sub-status"));
        this.settingNumberOfDaysToDormantSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToDormantSubStatusIContainer.add(this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToDormantSubStatusFeedback", this.settingNumberOfDaysToDormantSubStatusField);
        this.settingNumberOfDaysToDormantSubStatusIContainer.add(this.settingNumberOfDaysToDormantSubStatusFeedback);

        this.settingNumberOfDaysToInactiveSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
        this.settingNumberOfDaysToInactiveSubStatusIContainer = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusIContainer");
        this.settingNumberOfDaysToInactiveSubStatusBlock.add(this.settingNumberOfDaysToInactiveSubStatusIContainer);
        this.settingNumberOfDaysToInactiveSubStatusField = new TextField<>("settingNumberOfDaysToInactiveSubStatusField", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToInactiveSubStatusValue"));
        this.settingNumberOfDaysToInactiveSubStatusField.setLabel(Model.of("Number of Days to Inactive sub-status"));
        this.settingNumberOfDaysToInactiveSubStatusField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysToInactiveSubStatusIContainer.add(this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusFeedback = new TextFeedbackPanel("settingNumberOfDaysToInactiveSubStatusFeedback", this.settingNumberOfDaysToInactiveSubStatusField);
        this.settingNumberOfDaysToInactiveSubStatusIContainer.add(this.settingNumberOfDaysToInactiveSubStatusFeedback);

        this.settingEnableDormancyTrackingBlock = new WebMarkupBlock("settingEnableDormancyTrackingBlock", Size.Six_6);
        this.form.add(this.settingEnableDormancyTrackingBlock);
        this.settingEnableDormancyTrackingIContainer = new WebMarkupContainer("settingEnableDormancyTrackingIContainer");
        this.settingEnableDormancyTrackingBlock.add(this.settingEnableDormancyTrackingIContainer);
        this.settingEnableDormancyTrackingField = new CheckBox("settingEnableDormancyTrackingField", new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue"));
        this.settingEnableDormancyTrackingField.add(new OnChangeAjaxBehavior(this::settingEnableDormancyTrackingFieldUpdate));
        this.settingEnableDormancyTrackingIContainer.add(this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingFeedback = new TextFeedbackPanel("settingEnableDormancyTrackingFeedback", this.settingEnableDormancyTrackingField);
        this.settingEnableDormancyTrackingIContainer.add(this.settingEnableDormancyTrackingFeedback);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupIContainer = new WebMarkupContainer("settingTaxGroupIContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupIContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupIContainer.add(this.settingTaxGroupFeedback);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableIContainer = new WebMarkupContainer("settingWithholdTaxApplicableIContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableIContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableFeedback);

        this.settingMinOverdraftRequiredForInterestCalculationBlock = new WebMarkupBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Six_6);
        this.form.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        this.settingMinOverdraftRequiredForInterestCalculationIContainer = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationIContainer");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.add(this.settingMinOverdraftRequiredForInterestCalculationIContainer);
        this.settingMinOverdraftRequiredForInterestCalculationField = new TextField<>("settingMinOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this.itemPage, "settingMinOverdraftRequiredForInterestCalculationValue"));
        this.settingMinOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
        this.settingMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingMinOverdraftRequiredForInterestCalculationIContainer.add(this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingMinOverdraftRequiredForInterestCalculationFeedback", this.settingMinOverdraftRequiredForInterestCalculationField);
        this.settingMinOverdraftRequiredForInterestCalculationIContainer.add(this.settingMinOverdraftRequiredForInterestCalculationFeedback);

        this.settingNominalAnnualInterestForOverdraftBlock = new WebMarkupBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Six_6);
        this.form.add(this.settingNominalAnnualInterestForOverdraftBlock);
        this.settingNominalAnnualInterestForOverdraftIContainer = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftIContainer");
        this.settingNominalAnnualInterestForOverdraftBlock.add(this.settingNominalAnnualInterestForOverdraftIContainer);
        this.settingNominalAnnualInterestForOverdraftField = new TextField<>("settingNominalAnnualInterestForOverdraftField", new PropertyModel<>(this.itemPage, "settingNominalAnnualInterestForOverdraftValue"));
        this.settingNominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal annual interest for overdraft"));
        this.settingNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
        this.settingNominalAnnualInterestForOverdraftIContainer.add(this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel("settingNominalAnnualInterestForOverdraftFeedback", this.settingNominalAnnualInterestForOverdraftField);
        this.settingNominalAnnualInterestForOverdraftIContainer.add(this.settingNominalAnnualInterestForOverdraftFeedback);

        this.settingMaximumOverdraftAmountLimitBlock = new WebMarkupBlock("settingMaximumOverdraftAmountLimitBlock", Size.Six_6);
        this.form.add(this.settingMaximumOverdraftAmountLimitBlock);
        this.settingMaximumOverdraftAmountLimitIContainer = new WebMarkupContainer("settingMaximumOverdraftAmountLimitIContainer");
        this.settingMaximumOverdraftAmountLimitBlock.add(this.settingMaximumOverdraftAmountLimitIContainer);
        this.settingMaximumOverdraftAmountLimitField = new TextField<>("settingMaximumOverdraftAmountLimitField", new PropertyModel<>(this.itemPage, "settingMaximumOverdraftAmountLimitValue"));
        this.settingMaximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
        this.settingMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
        this.settingMaximumOverdraftAmountLimitIContainer.add(this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitFeedback = new TextFeedbackPanel("settingMaximumOverdraftAmountLimitFeedback", this.settingMaximumOverdraftAmountLimitField);
        this.settingMaximumOverdraftAmountLimitIContainer.add(this.settingMaximumOverdraftAmountLimitFeedback);

        this.settingOverdraftAllowedBlock = new WebMarkupBlock("settingOverdraftAllowedBlock", Size.Six_6);
        this.form.add(this.settingOverdraftAllowedBlock);
        this.settingOverdraftAllowedIContainer = new WebMarkupContainer("settingOverdraftAllowedIContainer");
        this.settingOverdraftAllowedBlock.add(this.settingOverdraftAllowedIContainer);
        this.settingOverdraftAllowedField = new CheckBox("settingOverdraftAllowedField", new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue"));
        this.settingOverdraftAllowedField.add(new OnChangeAjaxBehavior(this::settingOverdraftAllowedFieldUpdate));
        this.settingOverdraftAllowedIContainer.add(this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedFeedback = new TextFeedbackPanel("settingOverdraftAllowedFeedback", this.settingOverdraftAllowedField);
        this.settingOverdraftAllowedIContainer.add(this.settingOverdraftAllowedFeedback);

        this.settingMinimumBalanceBlock = new WebMarkupBlock("settingMinimumBalanceBlock", Size.Four_4);
        this.form.add(this.settingMinimumBalanceBlock);
        this.settingMinimumBalanceIContainer = new WebMarkupContainer("settingMinimumBalanceIContainer");
        this.settingMinimumBalanceBlock.add(this.settingMinimumBalanceIContainer);
        this.settingMinimumBalanceField = new TextField<>("settingMinimumBalanceField", new PropertyModel<>(this.itemPage, "settingMinimumBalanceValue"));
        this.settingMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumBalanceField.setLabel(Model.of("Minimum balance"));
        this.settingMinimumBalanceIContainer.add(this.settingMinimumBalanceField);
        this.settingMinimumBalanceFeedback = new TextFeedbackPanel("settingMinimumBalanceFeedback", this.settingMinimumBalanceField);
        this.settingMinimumBalanceIContainer.add(this.settingMinimumBalanceFeedback);

        this.settingEnforceMinimumBalanceBlock = new WebMarkupBlock("settingEnforceMinimumBalanceBlock", Size.Four_4);
        this.form.add(this.settingEnforceMinimumBalanceBlock);
        this.settingEnforceMinimumBalanceIContainer = new WebMarkupContainer("settingEnforceMinimumBalanceIContainer");
        this.settingEnforceMinimumBalanceBlock.add(this.settingEnforceMinimumBalanceIContainer);
        this.settingEnforceMinimumBalanceField = new CheckBox("settingEnforceMinimumBalanceField", new PropertyModel<>(this.itemPage, "settingEnforceMinimumBalanceValue"));
        this.settingEnforceMinimumBalanceField.add(new OnChangeAjaxBehavior());
        this.settingEnforceMinimumBalanceIContainer.add(this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceFeedback = new TextFeedbackPanel("settingEnforceMinimumBalanceFeedback", this.settingEnforceMinimumBalanceField);
        this.settingEnforceMinimumBalanceIContainer.add(this.settingEnforceMinimumBalanceFeedback);

        this.settingBalanceRequiredForInterestCalculationBlock = new WebMarkupBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Four_4);
        this.form.add(this.settingBalanceRequiredForInterestCalculationBlock);
        this.settingBalanceRequiredForInterestCalculationIContainer = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationIContainer");
        this.settingBalanceRequiredForInterestCalculationBlock.add(this.settingBalanceRequiredForInterestCalculationIContainer);
        this.settingBalanceRequiredForInterestCalculationField = new TextField<>("settingBalanceRequiredForInterestCalculationField", new PropertyModel<>(this.itemPage, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationField.setLabel(Model.of("Balance Required For Interest Calculation"));
        this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationFeedback = new TextFeedbackPanel("settingBalanceRequiredForInterestCalculationFeedback", this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationFeedback);

        this.settingApplyWithdrawalFeeForTransferBlock = new WebMarkupBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Four_4);
        this.form.add(this.settingApplyWithdrawalFeeForTransferBlock);
        this.settingApplyWithdrawalFeeForTransferIContainer = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferIContainer");
        this.settingApplyWithdrawalFeeForTransferBlock.add(this.settingApplyWithdrawalFeeForTransferIContainer);
        this.settingApplyWithdrawalFeeForTransferField = new CheckBox("settingApplyWithdrawalFeeForTransferField", new PropertyModel<>(this.itemPage, "settingApplyWithdrawalFeeForTransferValue"));
        this.settingApplyWithdrawalFeeForTransferField.add(new OnChangeAjaxBehavior());
        this.settingApplyWithdrawalFeeForTransferIContainer.add(this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel("settingApplyWithdrawalFeeForTransferFeedback", this.settingApplyWithdrawalFeeForTransferField);
        this.settingApplyWithdrawalFeeForTransferIContainer.add(this.settingApplyWithdrawalFeeForTransferFeedback);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);

        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);

        this.settingMinimumOpeningBalanceBlock = new WebMarkupBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
        this.form.add(this.settingMinimumOpeningBalanceBlock);
        this.settingMinimumOpeningBalanceIContainer = new WebMarkupContainer("settingMinimumOpeningBalanceIContainer");
        this.settingMinimumOpeningBalanceBlock.add(this.settingMinimumOpeningBalanceIContainer);
        this.settingMinimumOpeningBalanceField = new TextField<>("settingMinimumOpeningBalanceField", new PropertyModel<>(this.itemPage, "settingMinimumOpeningBalanceValue"));
        this.settingMinimumOpeningBalanceField.setLabel(Model.of("Minimum opening balance"));
        this.settingMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
        this.settingMinimumOpeningBalanceIContainer.add(this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceFeedback = new TextFeedbackPanel("settingMinimumOpeningBalanceFeedback", this.settingMinimumOpeningBalanceField);
        this.settingMinimumOpeningBalanceIContainer.add(this.settingMinimumOpeningBalanceFeedback);
    }

    @Override
    protected void configureMetaData() {
        settingOverdraftAllowedFieldUpdate(null);

        settingWithholdTaxApplicableFieldUpdate(null);

        settingEnableDormancyTrackingFieldUpdate(null);
    }

    protected boolean settingOverdraftAllowedFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> settingOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue");
        boolean visible = settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject();
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
        PropertyModel<Boolean> settingWithholdTaxApplicableValue = new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue");
        boolean visible = settingWithholdTaxApplicableValue.getObject() != null && settingWithholdTaxApplicableValue.getObject();
        this.settingTaxGroupIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected boolean settingEnableDormancyTrackingFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> settingEnableDormancyTrackingValue = new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue");
        boolean visible = settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject();
        this.settingNumberOfDaysToInactiveSubStatusIContainer.setVisible(visible);
        this.settingNumberOfDaysToDormantSubStatusIContainer.setVisible(visible);
        this.settingNumberOfDaysToEscheatIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
            target.add(this.settingNumberOfDaysToDormantSubStatusBlock);
            target.add(this.settingNumberOfDaysToEscheatBlock);
        }
        return false;
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_CHARGE);
        this.errorSetting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
