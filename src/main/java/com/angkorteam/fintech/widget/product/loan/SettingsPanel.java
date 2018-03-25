package com.angkorteam.fintech.widget.product.loan;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.loan.AdvancePaymentsAdjustmentTypeProvider;
import com.angkorteam.fintech.provider.loan.AmortizationProvider;
import com.angkorteam.fintech.provider.loan.ClosureInterestCalculationRuleProvider;
import com.angkorteam.fintech.provider.loan.DayInMonthProvider;
import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
import com.angkorteam.fintech.provider.loan.FrequencyProvider;
import com.angkorteam.fintech.provider.loan.FrequencyTypeProvider;
import com.angkorteam.fintech.provider.loan.InterestCalculationPeriodProvider;
import com.angkorteam.fintech.provider.loan.InterestMethodProvider;
import com.angkorteam.fintech.provider.loan.InterestRecalculationCompoundProvider;
import com.angkorteam.fintech.provider.loan.OnDayProvider;
import com.angkorteam.fintech.provider.loan.RepaymentStrategyProvider;
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

    // Settings

    protected WebMarkupBlock settingAmortizationBlock;
    protected WebMarkupContainer settingAmortizationIContainer;
    protected AmortizationProvider settingAmortizationProvider;
    protected Select2SingleChoice<Option> settingAmortizationField;
    protected TextFeedbackPanel settingAmortizationFeedback;

    protected WebMarkupBlock settingInterestMethodBlock;
    protected WebMarkupContainer settingInterestMethodIContainer;
    protected InterestMethodProvider settingInterestMethodProvider;
    protected Select2SingleChoice<Option> settingInterestMethodField;
    protected TextFeedbackPanel settingInterestMethodFeedback;

    protected WebMarkupBlock settingEqualAmortizationBlock;
    protected WebMarkupContainer settingEqualAmortizationIContainer;
    protected Boolean settingEqualAmortizationValue;
    protected CheckBox settingEqualAmortizationField;
    protected TextFeedbackPanel settingEqualAmortizationFeedback;

    protected WebMarkupBlock settingInterestCalculationPeriodBlock;
    protected WebMarkupContainer settingInterestCalculationPeriodIContainer;
    protected InterestCalculationPeriodProvider settingInterestCalculationPeriodProvider;
    protected Select2SingleChoice<Option> settingInterestCalculationPeriodField;
    protected TextFeedbackPanel settingInterestCalculationPeriodFeedback;

    protected WebMarkupBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
    protected WebMarkupContainer settingCalculateInterestForExactDaysInPartialPeriodIContainer;
    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    protected CheckBox settingCalculateInterestForExactDaysInPartialPeriodField;
    protected TextFeedbackPanel settingCalculateInterestForExactDaysInPartialPeriodFeedback;

    protected WebMarkupBlock settingRepaymentStrategyBlock;
    protected WebMarkupContainer settingRepaymentStrategyIContainer;
    protected RepaymentStrategyProvider settingRepaymentStrategyProvider;
    protected Select2SingleChoice<Option> settingRepaymentStrategyField;
    protected TextFeedbackPanel settingRepaymentStrategyFeedback;

    protected WebMarkupBlock settingMoratoriumPrincipleBlock;
    protected WebMarkupContainer settingMoratoriumPrincipleIContainer;
    protected TextField<Long> settingMoratoriumPrincipleField;
    protected TextFeedbackPanel settingMoratoriumPrincipleFeedback;

    protected WebMarkupBlock settingMoratoriumInterestBlock;
    protected WebMarkupContainer settingMoratoriumInterestIContainer;
    protected TextField<Long> settingMoratoriumInterestField;
    protected TextFeedbackPanel settingMoratoriumInterestFeedback;

    protected WebMarkupBlock settingInterestFreePeriodBlock;
    protected WebMarkupContainer settingInterestFreePeriodIContainer;
    protected TextField<Long> settingInterestFreePeriodField;
    protected TextFeedbackPanel settingInterestFreePeriodFeedback;

    protected WebMarkupBlock settingArrearsToleranceBlock;
    protected WebMarkupContainer settingArrearsToleranceIContainer;
    protected TextField<Double> settingArrearsToleranceField;
    protected TextFeedbackPanel settingArrearsToleranceFeedback;

    protected WebMarkupBlock settingDayInYearBlock;
    protected WebMarkupContainer settingDayInYearIContainer;
    protected DayInYearProvider settingDayInYearProvider;
    protected Select2SingleChoice<Option> settingDayInYearField;
    protected TextFeedbackPanel settingDayInYearFeedback;

    protected WebMarkupBlock settingDayInMonthBlock;
    protected WebMarkupContainer settingDayInMonthIContainer;
    protected DayInMonthProvider settingDayInMonthProvider;
    protected Select2SingleChoice<Option> settingDayInMonthField;
    protected TextFeedbackPanel settingDayInMonthFeedback;

    protected WebMarkupBlock settingAllowFixingOfTheInstallmentAmountBlock;
    protected WebMarkupContainer settingAllowFixingOfTheInstallmentAmountIContainer;
    protected CheckBox settingAllowFixingOfTheInstallmentAmountField;
    protected TextFeedbackPanel settingAllowFixingOfTheInstallmentAmountFeedback;

    protected WebMarkupBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    protected WebMarkupContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer;
    protected TextField<Long> settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField;
    protected TextFeedbackPanel settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback;

    protected WebMarkupBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    protected WebMarkupContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer;
    protected TextField<Long> settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField;
    protected TextFeedbackPanel settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback;

    protected WebMarkupBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    protected WebMarkupContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer;
    protected CheckBox settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField;
    protected TextFeedbackPanel settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback;

    protected WebMarkupBlock settingPrincipleThresholdForLastInstalmentBlock;
    protected WebMarkupContainer settingPrincipleThresholdForLastInstalmentIContainer;
    protected TextField<Double> settingPrincipleThresholdForLastInstalmentField;
    protected TextFeedbackPanel settingPrincipleThresholdForLastInstalmentFeedback;

    protected WebMarkupBlock settingVariableInstallmentsAllowedBlock;
    protected WebMarkupContainer settingVariableInstallmentsAllowedIContainer;
    protected CheckBox settingVariableInstallmentsAllowedField;
    protected TextFeedbackPanel settingVariableInstallmentsAllowedFeedback;

    protected WebMarkupBlock settingVariableInstallmentsMinimumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMinimumIContainer;
    protected TextField<Long> settingVariableInstallmentsMinimumField;
    protected TextFeedbackPanel settingVariableInstallmentsMinimumFeedback;

    protected WebMarkupBlock settingVariableInstallmentsMaximumBlock;
    protected WebMarkupContainer settingVariableInstallmentsMaximumIContainer;
    protected TextField<Long> settingVariableInstallmentsMaximumField;
    protected TextFeedbackPanel settingVariableInstallmentsMaximumFeedback;

    protected WebMarkupBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
    protected WebMarkupContainer settingAllowedToBeUsedForProvidingTopupLoansIContainer;
    protected CheckBox settingAllowedToBeUsedForProvidingTopupLoansField;
    protected TextFeedbackPanel settingAllowedToBeUsedForProvidingTopupLoansFeedback;

    // Interest Recalculation

    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
    protected WebMarkupContainer interestRecalculationRecalculateInterestIContainer;
    protected CheckBox interestRecalculationRecalculateInterestField;
    protected TextFeedbackPanel interestRecalculationRecalculateInterestFeedback;

    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleIContainer;
    protected ClosureInterestCalculationRuleProvider interestRecalculationPreClosureInterestCalculationRuleProvider;
    protected Select2SingleChoice<Option> interestRecalculationPreClosureInterestCalculationRuleField;
    protected TextFeedbackPanel interestRecalculationPreClosureInterestCalculationRuleFeedback;

    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeIContainer;
    protected AdvancePaymentsAdjustmentTypeProvider interestRecalculationAdvancePaymentsAdjustmentTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationAdvancePaymentsAdjustmentTypeField;
    protected TextFeedbackPanel interestRecalculationAdvancePaymentsAdjustmentTypeFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
    protected WebMarkupContainer interestRecalculationCompoundingOnIContainer;
    protected InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnField;
    protected TextFeedbackPanel interestRecalculationCompoundingOnFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIContainer;
    protected FrequencyProvider interestRecalculationCompoundingProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingField;
    protected TextFeedbackPanel interestRecalculationCompoundingFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingTypeBlock;
    protected WebMarkupContainer interestRecalculationCompoundingTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationCompoundingTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingTypeField;
    protected TextFeedbackPanel interestRecalculationCompoundingTypeFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingDayBlock;

    protected WebMarkupContainer interestRecalculationCompoundingDayIContainer;
    protected FrequencyDayProvider interestRecalculationCompoundingDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingDayField;
    protected TextFeedbackPanel interestRecalculationCompoundingDayFeedback;

    protected WebMarkupContainer interestRecalculationCompoundingOnDayIContainer;
    protected OnDayProvider interestRecalculationCompoundingOnDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnDayField;
    protected TextFeedbackPanel interestRecalculationCompoundingOnDayFeedback;

    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIntervalIContainer;
    protected TextField<Long> interestRecalculationCompoundingIntervalField;
    protected TextFeedbackPanel interestRecalculationCompoundingIntervalFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIContainer;
    protected FrequencyProvider interestRecalculationRecalculateProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateField;
    protected TextFeedbackPanel interestRecalculationRecalculateFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateTypeBlock;
    protected WebMarkupContainer interestRecalculationRecalculateTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationRecalculateTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateTypeField;
    protected TextFeedbackPanel interestRecalculationRecalculateTypeFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateDayBlock;

    protected WebMarkupContainer interestRecalculationRecalculateDayIContainer;
    protected FrequencyDayProvider interestRecalculationRecalculateDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateDayField;
    protected TextFeedbackPanel interestRecalculationRecalculateDayFeedback;

    protected WebMarkupContainer interestRecalculationRecalculateOnDayIContainer;
    protected OnDayProvider interestRecalculationRecalculateOnDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateOnDayField;
    protected TextFeedbackPanel interestRecalculationRecalculateOnDayFeedback;

    protected WebMarkupBlock interestRecalculationRecalculateIntervalBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIntervalIContainer;
    protected TextField<Long> interestRecalculationRecalculateIntervalField;
    protected TextFeedbackPanel interestRecalculationRecalculateIntervalFeedback;

    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer;
    protected CheckBox interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField;
    protected TextFeedbackPanel interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback;

    // Guarantee Requirements

    protected WebMarkupBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    protected WebMarkupContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer;
    protected CheckBox guaranteeRequirementPlaceGuaranteeFundsOnHoldField;
    protected TextFeedbackPanel guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback;

    protected WebMarkupBlock guaranteeRequirementMandatoryGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMandatoryGuaranteeIContainer;
    protected TextField<Double> guaranteeRequirementMandatoryGuaranteeField;
    protected TextFeedbackPanel guaranteeRequirementMandatoryGuaranteeFeedback;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeIContainer;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeField;
    protected TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFeedback;

    protected WebMarkupBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    protected WebMarkupContainer guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeFromGuarantorField;
    protected TextFeedbackPanel guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback;

    // Loan Tranche Details

    protected WebMarkupBlock loanTrancheDetailEnableMultipleDisbursalBlock;
    protected WebMarkupContainer loanTrancheDetailEnableMultipleDisbursalIContainer;
    protected CheckBox loanTrancheDetailEnableMultipleDisbursalField;
    protected TextFeedbackPanel loanTrancheDetailEnableMultipleDisbursalFeedback;

    protected WebMarkupBlock loanTrancheDetailMaximumTrancheCountBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumTrancheCountIContainer;
    protected TextField<Long> loanTrancheDetailMaximumTrancheCountField;
    protected TextFeedbackPanel loanTrancheDetailMaximumTrancheCountFeedback;

    protected WebMarkupBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    protected WebMarkupContainer loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer;
    protected TextField<Double> loanTrancheDetailMaximumAllowedOutstandingBalanceField;
    protected TextFeedbackPanel loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback;

    // Configurable Terms and Settings

    protected WebMarkupBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    protected WebMarkupContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer;
    protected CheckBox configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField;
    protected TextFeedbackPanel configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback;

    protected WebMarkupBlock configurableAmortizationBlock;
    protected WebMarkupContainer configurableAmortizationIContainer;
    protected CheckBox configurableAmortizationField;
    protected TextFeedbackPanel configurableAmortizationFeedback;

    protected WebMarkupBlock configurableInterestMethodBlock;
    protected WebMarkupContainer configurableInterestMethodIContainer;
    protected CheckBox configurableInterestMethodField;
    protected TextFeedbackPanel configurableInterestMethodFeedback;

    protected WebMarkupBlock configurableRepaymentStrategyBlock;
    protected WebMarkupContainer configurableRepaymentStrategyIContainer;
    protected CheckBox configurableRepaymentStrategyField;
    protected TextFeedbackPanel configurableRepaymentStrategyFeedback;

    protected WebMarkupBlock configurableInterestCalculationPeriodBlock;
    protected WebMarkupContainer configurableInterestCalculationPeriodIContainer;
    protected CheckBox configurableInterestCalculationPeriodField;
    protected TextFeedbackPanel configurableInterestCalculationPeriodFeedback;

    protected WebMarkupBlock configurableArrearsToleranceBlock;
    protected WebMarkupContainer configurableArrearsToleranceIContainer;
    protected CheckBox configurableArrearsToleranceField;
    protected TextFeedbackPanel configurableArrearsToleranceFeedback;

    protected WebMarkupBlock configurableRepaidEveryBlock;
    protected WebMarkupContainer configurableRepaidEveryIContainer;
    protected Boolean configurableRepaidEveryValue;
    protected CheckBox configurableRepaidEveryField;
    protected TextFeedbackPanel configurableRepaidEveryFeedback;

    protected WebMarkupBlock configurableMoratoriumBlock;
    protected WebMarkupContainer configurableMoratoriumIContainer;
    protected CheckBox configurableMoratoriumField;
    protected TextFeedbackPanel configurableMoratoriumFeedback;

    protected WebMarkupBlock configurableOverdueBeforeMovingBlock;
    protected WebMarkupContainer configurableOverdueBeforeMovingIContainer;
    protected CheckBox configurableOverdueBeforeMovingField;
    protected TextFeedbackPanel configurableOverdueBeforeMovingFeedback;

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        this.form.add(this.closeLink);

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = new WebMarkupBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Twelve_12);
        this.form.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer = new WebMarkupContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField.add(new OnChangeAjaxBehavior(this::configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback = new TextFeedbackPanel("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback", this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback);

        this.configurableAmortizationBlock = new WebMarkupBlock("configurableAmortizationBlock", Size.Six_6);
        this.form.add(this.configurableAmortizationBlock);
        this.configurableAmortizationIContainer = new WebMarkupContainer("configurableAmortizationIContainer");
        this.configurableAmortizationBlock.add(this.configurableAmortizationIContainer);
        this.configurableAmortizationField = new CheckBox("configurableAmortizationField", new PropertyModel<>(this.itemPage, "configurableAmortizationValue"));
        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());
        this.configurableAmortizationIContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationFeedback = new TextFeedbackPanel("configurableAmortizationFeedback", this.configurableAmortizationField);
        this.configurableAmortizationIContainer.add(this.configurableAmortizationFeedback);

        this.configurableInterestMethodBlock = new WebMarkupBlock("configurableInterestMethodBlock", Size.Six_6);
        this.form.add(this.configurableInterestMethodBlock);
        this.configurableInterestMethodIContainer = new WebMarkupContainer("configurableInterestMethodIContainer");
        this.configurableInterestMethodBlock.add(this.configurableInterestMethodIContainer);
        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField", new PropertyModel<>(this.itemPage, "configurableInterestMethodValue"));
        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestMethodIContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodFeedback = new TextFeedbackPanel("configurableInterestMethodFeedback", this.configurableInterestMethodField);
        this.configurableInterestMethodIContainer.add(this.configurableInterestMethodFeedback);

        this.configurableRepaymentStrategyBlock = new WebMarkupBlock("configurableRepaymentStrategyBlock", Size.Six_6);
        this.form.add(this.configurableRepaymentStrategyBlock);
        this.configurableRepaymentStrategyIContainer = new WebMarkupContainer("configurableRepaymentStrategyIContainer");
        this.configurableRepaymentStrategyBlock.add(this.configurableRepaymentStrategyIContainer);
        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField", new PropertyModel<>(this.itemPage, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.configurableRepaymentStrategyIContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyFeedback = new TextFeedbackPanel("configurableRepaymentStrategyFeedback", this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyIContainer.add(this.configurableRepaymentStrategyFeedback);

        this.configurableInterestCalculationPeriodBlock = new WebMarkupBlock("configurableInterestCalculationPeriodBlock", Size.Six_6);
        this.form.add(this.configurableInterestCalculationPeriodBlock);
        this.configurableInterestCalculationPeriodIContainer = new WebMarkupContainer("configurableInterestCalculationPeriodIContainer");
        this.configurableInterestCalculationPeriodBlock.add(this.configurableInterestCalculationPeriodIContainer);
        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField", new PropertyModel<>(this.itemPage, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());
        this.configurableInterestCalculationPeriodIContainer.add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodFeedback = new TextFeedbackPanel("configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodIContainer.add(this.configurableInterestCalculationPeriodFeedback);

        this.configurableArrearsToleranceBlock = new WebMarkupBlock("configurableArrearsToleranceBlock", Size.Six_6);
        this.form.add(this.configurableArrearsToleranceBlock);
        this.configurableArrearsToleranceIContainer = new WebMarkupContainer("configurableArrearsToleranceIContainer");
        this.configurableArrearsToleranceBlock.add(this.configurableArrearsToleranceIContainer);
        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField", new PropertyModel<>(this.itemPage, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.configurableArrearsToleranceIContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceFeedback = new TextFeedbackPanel("configurableArrearsToleranceFeedback", this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceIContainer.add(this.configurableArrearsToleranceFeedback);

        this.configurableRepaidEveryBlock = new WebMarkupBlock("configurableRepaidEveryBlock", Size.Six_6);
        this.form.add(this.configurableRepaidEveryBlock);
        this.configurableRepaidEveryIContainer = new WebMarkupContainer("configurableRepaidEveryIContainer");
        this.configurableRepaidEveryBlock.add(this.configurableRepaidEveryIContainer);
        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField", new PropertyModel<>(this.itemPage, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.configurableRepaidEveryIContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryFeedback = new TextFeedbackPanel("configurableRepaidEveryFeedback", this.configurableRepaidEveryField);
        this.configurableRepaidEveryIContainer.add(this.configurableRepaidEveryFeedback);

        this.configurableMoratoriumBlock = new WebMarkupBlock("configurableMoratoriumBlock", Size.Six_6);
        this.form.add(this.configurableMoratoriumBlock);
        this.configurableMoratoriumIContainer = new WebMarkupContainer("configurableMoratoriumIContainer");
        this.configurableMoratoriumBlock.add(this.configurableMoratoriumIContainer);
        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField", new PropertyModel<>(this.itemPage, "configurableMoratoriumValue"));
        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());
        this.configurableMoratoriumIContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumFeedback = new TextFeedbackPanel("configurableMoratoriumFeedback", this.configurableMoratoriumField);
        this.configurableMoratoriumIContainer.add(this.configurableMoratoriumFeedback);

        this.configurableOverdueBeforeMovingBlock = new WebMarkupBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
        this.form.add(this.configurableOverdueBeforeMovingBlock);
        this.configurableOverdueBeforeMovingIContainer = new WebMarkupContainer("configurableOverdueBeforeMovingIContainer");
        this.configurableOverdueBeforeMovingBlock.add(this.configurableOverdueBeforeMovingIContainer);
        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField", new PropertyModel<>(this.itemPage, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());
        this.configurableOverdueBeforeMovingIContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingFeedback = new TextFeedbackPanel("configurableOverdueBeforeMovingFeedback", this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingIContainer.add(this.configurableOverdueBeforeMovingFeedback);

        this.loanTrancheDetailEnableMultipleDisbursalBlock = new WebMarkupBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailEnableMultipleDisbursalBlock);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer = new WebMarkupContainer("loanTrancheDetailEnableMultipleDisbursalIContainer");
        this.loanTrancheDetailEnableMultipleDisbursalBlock.add(this.loanTrancheDetailEnableMultipleDisbursalIContainer);
        this.loanTrancheDetailEnableMultipleDisbursalField = new CheckBox("loanTrancheDetailEnableMultipleDisbursalField", new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue"));
        this.loanTrancheDetailEnableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::loanTrancheDetailEnableMultipleDisbursalFieldUpdate));
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.add(this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalFeedback = new TextFeedbackPanel("loanTrancheDetailEnableMultipleDisbursalFeedback", this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.add(this.loanTrancheDetailEnableMultipleDisbursalFeedback);

        this.loanTrancheDetailMaximumTrancheCountBlock = new WebMarkupBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailMaximumTrancheCountBlock);
        this.loanTrancheDetailMaximumTrancheCountIContainer = new WebMarkupContainer("loanTrancheDetailMaximumTrancheCountIContainer");
        this.loanTrancheDetailMaximumTrancheCountBlock.add(this.loanTrancheDetailMaximumTrancheCountIContainer);
        this.loanTrancheDetailMaximumTrancheCountField = new TextField<>("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountField.setLabel(Model.of("Maximum Tranche count"));
        this.loanTrancheDetailMaximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumTrancheCountIContainer.add(this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumTrancheCountFeedback", this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountIContainer.add(this.loanTrancheDetailMaximumTrancheCountFeedback);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = new WebMarkupBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Four_4);
        this.form.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer = new WebMarkupContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField = new TextField<>("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setLabel(Model.of("Maximum allowed outstanding balance"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback = new TextFeedbackPanel("loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback", this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback);

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = new WebMarkupBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Twelve_12);
        this.form.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer = new WebMarkupContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField = new CheckBox("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue"));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate));
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback = new TextFeedbackPanel("guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback);

        this.guaranteeRequirementMandatoryGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Four_4);
        this.form.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
        this.guaranteeRequirementMandatoryGuaranteeIContainer = new WebMarkupContainer("guaranteeRequirementMandatoryGuaranteeIContainer");
        this.guaranteeRequirementMandatoryGuaranteeBlock.add(this.guaranteeRequirementMandatoryGuaranteeIContainer);
        this.guaranteeRequirementMandatoryGuaranteeField = new TextField<>("guaranteeRequirementMandatoryGuaranteeField", new PropertyModel<>(this.itemPage, "guaranteeRequirementMandatoryGuaranteeValue"));
        this.guaranteeRequirementMandatoryGuaranteeField.setLabel(Model.of("Mandatory Guarantee: (%)"));
        this.guaranteeRequirementMandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMandatoryGuaranteeIContainer.add(this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMandatoryGuaranteeFeedback", this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeIContainer.add(this.guaranteeRequirementMandatoryGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Four_4);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeBlock);
        this.guaranteeRequirementMinimumGuaranteeIContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeIContainer");
        this.guaranteeRequirementMinimumGuaranteeBlock.add(this.guaranteeRequirementMinimumGuaranteeIContainer);
        this.guaranteeRequirementMinimumGuaranteeField = new TextField<>("guaranteeRequirementMinimumGuaranteeField", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeValue"));
        this.guaranteeRequirementMinimumGuaranteeField.setLabel(Model.of("Minimum Guarantee from Own Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeIContainer.add(this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFeedback", this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeIContainer.add(this.guaranteeRequirementMinimumGuaranteeFeedback);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = new WebMarkupBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Four_4);
        this.form.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer = new WebMarkupContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField = new TextField<>("guaranteeRequirementMinimumGuaranteeFromGuarantorField", new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setLabel(Model.of("Minimum Guarantee from Guarantor Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback = new TextFeedbackPanel("guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback", this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback);

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestIContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestIContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestIContainer);
        this.interestRecalculationRecalculateInterestField = new CheckBox("interestRecalculationRecalculateInterestField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateInterestFieldUpdate));
        this.interestRecalculationRecalculateInterestIContainer.add(this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestFeedback = new TextFeedbackPanel("interestRecalculationRecalculateInterestFeedback", this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestIContainer.add(this.interestRecalculationRecalculateInterestFeedback);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
        this.form.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleIContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleIContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
        this.interestRecalculationPreClosureInterestCalculationRuleField = new Select2SingleChoice<>("interestRecalculationPreClosureInterestCalculationRuleField", new PropertyModel<>(this.itemPage, "interestRecalculationPreClosureInterestCalculationRuleValue"), this.interestRecalculationPreClosureInterestCalculationRuleProvider);
        this.interestRecalculationPreClosureInterestCalculationRuleField.setLabel(Model.of("Pre-closure interest calculation rule"));
        this.interestRecalculationPreClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleFeedback = new TextFeedbackPanel("interestRecalculationPreClosureInterestCalculationRuleFeedback", this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleFeedback);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
        this.form.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeIContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("interestRecalculationAdvancePaymentsAdjustmentTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"), this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setLabel(Model.of("Advance payments adjustment type"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback = new TextFeedbackPanel("interestRecalculationAdvancePaymentsAdjustmentTypeFeedback", this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeFeedback);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnIContainer = new WebMarkupContainer("interestRecalculationCompoundingOnIContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnIContainer);
        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
        this.interestRecalculationCompoundingOnField = new Select2SingleChoice<>("interestRecalculationCompoundingOnField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue"), this.interestRecalculationCompoundingOnProvider);
        this.interestRecalculationCompoundingOnField.setLabel(Model.of("Interest recalculation compounding on"));
        this.interestRecalculationCompoundingOnField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingOnIContainer.add(this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnIContainer.add(this.interestRecalculationCompoundingOnFeedback);

        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingIContainer = new WebMarkupContainer("interestRecalculationCompoundingIContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingIContainer);
        this.interestRecalculationCompoundingProvider = new FrequencyProvider();
        this.interestRecalculationCompoundingField = new Select2SingleChoice<>("interestRecalculationCompoundingField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue"), this.interestRecalculationCompoundingProvider);
        this.interestRecalculationCompoundingField.setLabel(Model.of("Frequency for compounding"));
        this.interestRecalculationCompoundingIContainer.add(this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingFeedback = new TextFeedbackPanel("interestRecalculationCompoundingFeedback", this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingIContainer.add(this.interestRecalculationCompoundingFeedback);

        this.interestRecalculationCompoundingTypeBlock = new WebMarkupBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingTypeBlock);
        this.interestRecalculationCompoundingTypeIContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeIContainer");
        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeIContainer);
        this.interestRecalculationCompoundingTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationCompoundingTypeField = new Select2SingleChoice<>("interestRecalculationCompoundingTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue"), this.interestRecalculationCompoundingTypeProvider);
        this.interestRecalculationCompoundingTypeField.setLabel(Model.of("Frequency type for compounding"));
        this.interestRecalculationCompoundingTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));
        this.interestRecalculationCompoundingTypeIContainer.add(this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeFeedback = new TextFeedbackPanel("interestRecalculationCompoundingTypeFeedback", this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeIContainer.add(this.interestRecalculationCompoundingTypeFeedback);

        this.interestRecalculationCompoundingDayBlock = new WebMarkupBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingDayBlock);
        this.interestRecalculationCompoundingDayIContainer = new WebMarkupContainer("interestRecalculationCompoundingDayIContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayIContainer);
        this.interestRecalculationCompoundingDayProvider = new FrequencyDayProvider();
        this.interestRecalculationCompoundingDayField = new Select2SingleChoice<>("interestRecalculationCompoundingDayField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingDayValue"), this.interestRecalculationCompoundingDayProvider);
        this.interestRecalculationCompoundingDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingDayIContainer.add(this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingDayFeedback", this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayIContainer.add(this.interestRecalculationCompoundingDayFeedback);

        this.interestRecalculationCompoundingOnDayIContainer = new WebMarkupContainer("interestRecalculationCompoundingOnDayIContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingOnDayIContainer);
        this.interestRecalculationCompoundingOnDayProvider = new OnDayProvider();
        this.interestRecalculationCompoundingOnDayField = new Select2SingleChoice<>("interestRecalculationCompoundingOnDayField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnDayValue"), this.interestRecalculationCompoundingOnDayProvider);
        this.interestRecalculationCompoundingOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingOnDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingOnDayIContainer.add(this.interestRecalculationCompoundingOnDayField);
        this.interestRecalculationCompoundingOnDayFeedback = new TextFeedbackPanel("interestRecalculationCompoundingOnDayFeedback", this.interestRecalculationCompoundingOnDayField);
        this.interestRecalculationCompoundingOnDayIContainer.add(this.interestRecalculationCompoundingOnDayFeedback);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalIContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalIContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalIContainer);
        this.interestRecalculationCompoundingIntervalField = new TextField<>("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalField.setLabel(Model.of("Frequency Interval for compounding"));
        this.interestRecalculationCompoundingIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationCompoundingIntervalIContainer.add(this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalFeedback = new TextFeedbackPanel("interestRecalculationCompoundingIntervalFeedback", this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalIContainer.add(this.interestRecalculationCompoundingIntervalFeedback);

        this.interestRecalculationRecalculateBlock = new WebMarkupBlock("interestRecalculationRecalculateBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateBlock);
        this.interestRecalculationRecalculateIContainer = new WebMarkupContainer("interestRecalculationRecalculateIContainer");
        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateIContainer);
        this.interestRecalculationRecalculateProvider = new FrequencyProvider();
        this.interestRecalculationRecalculateField = new Select2SingleChoice<>("interestRecalculationRecalculateField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue"), this.interestRecalculationRecalculateProvider);
        this.interestRecalculationRecalculateField.setLabel(Model.of("Frequency for recalculate Outstanding Principle"));
        this.interestRecalculationRecalculateField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateIContainer.add(this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateFeedback = new TextFeedbackPanel("interestRecalculationRecalculateFeedback", this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateIContainer.add(this.interestRecalculationRecalculateFeedback);

        this.interestRecalculationRecalculateTypeBlock = new WebMarkupBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateTypeBlock);
        this.interestRecalculationRecalculateTypeIContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeIContainer");
        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeIContainer);
        this.interestRecalculationRecalculateTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationRecalculateTypeField = new Select2SingleChoice<>("interestRecalculationRecalculateTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue"), this.interestRecalculationRecalculateTypeProvider);
        this.interestRecalculationRecalculateTypeField.setLabel(Model.of("Frequency type for recalculate"));
        this.interestRecalculationRecalculateTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateTypeIContainer.add(this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeFeedback = new TextFeedbackPanel("interestRecalculationRecalculateTypeFeedback", this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeIContainer.add(this.interestRecalculationRecalculateTypeFeedback);

        this.interestRecalculationRecalculateDayBlock = new WebMarkupBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateDayBlock);
        this.interestRecalculationRecalculateDayIContainer = new WebMarkupContainer("interestRecalculationRecalculateDayIContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayIContainer);
        this.interestRecalculationRecalculateDayProvider = new FrequencyDayProvider();
        this.interestRecalculationRecalculateDayField = new Select2SingleChoice<>("interestRecalculationRecalculateDayField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateDayValue"), this.interestRecalculationRecalculateDayProvider);
        this.interestRecalculationRecalculateDayField.setLabel(Model.of("Frequency day for recalculate"));
        this.interestRecalculationRecalculateDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateDayIContainer.add(this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateDayFeedback", this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayIContainer.add(this.interestRecalculationRecalculateDayFeedback);

        this.interestRecalculationRecalculateOnDayIContainer = new WebMarkupContainer("interestRecalculationRecalculateOnDayIContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateOnDayIContainer);
        this.interestRecalculationRecalculateOnDayProvider = new OnDayProvider();
        this.interestRecalculationRecalculateOnDayField = new Select2SingleChoice<>("interestRecalculationRecalculateOnDayField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateOnDayValue"), this.interestRecalculationRecalculateOnDayProvider);
        this.interestRecalculationRecalculateOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationRecalculateOnDayField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateOnDayIContainer.add(this.interestRecalculationRecalculateOnDayField);
        this.interestRecalculationRecalculateOnDayFeedback = new TextFeedbackPanel("interestRecalculationRecalculateOnDayFeedback", this.interestRecalculationRecalculateOnDayField);
        this.interestRecalculationRecalculateOnDayIContainer.add(this.interestRecalculationRecalculateOnDayFeedback);

        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateIntervalBlock);
        this.interestRecalculationRecalculateIntervalIContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalIContainer");
        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalIContainer);
        this.interestRecalculationRecalculateIntervalField = new TextField<>("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalField.setLabel(Model.of("Frequency Interval for recalculate"));
        this.interestRecalculationRecalculateIntervalField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationRecalculateIntervalIContainer.add(this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalFeedback = new TextFeedbackPanel("interestRecalculationRecalculateIntervalFeedback", this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalIContainer.add(this.interestRecalculationRecalculateIntervalFeedback);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField = new CheckBox("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this.itemPage, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback = new TextFeedbackPanel("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback", this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback);

        this.settingAmortizationBlock = new WebMarkupBlock("settingAmortizationBlock", Size.Four_4);
        this.form.add(this.settingAmortizationBlock);
        this.settingAmortizationIContainer = new WebMarkupContainer("settingAmortizationIContainer");
        this.settingAmortizationBlock.add(this.settingAmortizationIContainer);
        this.settingAmortizationProvider = new AmortizationProvider();
        this.settingAmortizationField = new Select2SingleChoice<>("settingAmortizationField", new PropertyModel<>(this.itemPage, "settingAmortizationValue"), this.settingAmortizationProvider);
        this.settingAmortizationField.setLabel(Model.of("Amortization"));
        this.settingAmortizationField.add(new OnChangeAjaxBehavior());
        this.settingAmortizationIContainer.add(this.settingAmortizationField);
        this.settingAmortizationFeedback = new TextFeedbackPanel("settingAmortizationFeedback", this.settingAmortizationField);
        this.settingAmortizationIContainer.add(this.settingAmortizationFeedback);

        this.settingInterestMethodBlock = new WebMarkupBlock("settingInterestMethodBlock", Size.Four_4);
        this.form.add(this.settingInterestMethodBlock);
        this.settingInterestMethodIContainer = new WebMarkupContainer("settingInterestMethodIContainer");
        this.settingInterestMethodBlock.add(this.settingInterestMethodIContainer);
        this.settingInterestMethodProvider = new InterestMethodProvider();
        this.settingInterestMethodField = new Select2SingleChoice<>("settingInterestMethodField", new PropertyModel<>(this.itemPage, "settingInterestMethodValue"), this.settingInterestMethodProvider);
        this.settingInterestMethodField.setLabel(Model.of("Interest method"));
        this.settingInterestMethodField.add(new OnChangeAjaxBehavior());
        this.settingInterestMethodIContainer.add(this.settingInterestMethodField);
        this.settingInterestMethodFeedback = new TextFeedbackPanel("settingInterestMethodFeedback", this.settingInterestMethodField);
        this.settingInterestMethodIContainer.add(this.settingInterestMethodFeedback);

        this.settingEqualAmortizationBlock = new WebMarkupBlock("settingEqualAmortizationBlock", Size.Four_4);
        this.form.add(this.settingEqualAmortizationBlock);
        this.settingEqualAmortizationIContainer = new WebMarkupContainer("settingEqualAmortizationIContainer");
        this.settingEqualAmortizationBlock.add(this.settingEqualAmortizationIContainer);
        this.settingEqualAmortizationField = new CheckBox("settingEqualAmortizationField", new PropertyModel<>(this.itemPage, "settingEqualAmortizationValue"));
        this.settingEqualAmortizationField.add(new OnChangeAjaxBehavior());
        this.settingEqualAmortizationIContainer.add(this.settingEqualAmortizationField);
        this.settingEqualAmortizationFeedback = new TextFeedbackPanel("settingEqualAmortizationFeedback", this.settingEqualAmortizationField);
        this.settingEqualAmortizationIContainer.add(this.settingEqualAmortizationFeedback);

        this.settingInterestCalculationPeriodBlock = new WebMarkupBlock("settingInterestCalculationPeriodBlock", Size.Six_6);
        this.form.add(this.settingInterestCalculationPeriodBlock);
        this.settingInterestCalculationPeriodIContainer = new WebMarkupContainer("settingInterestCalculationPeriodIContainer");
        this.settingInterestCalculationPeriodBlock.add(this.settingInterestCalculationPeriodIContainer);
        this.settingInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
        this.settingInterestCalculationPeriodField = new Select2SingleChoice<>("settingInterestCalculationPeriodField", new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue"), this.settingInterestCalculationPeriodProvider);
        this.settingInterestCalculationPeriodField.setLabel(Model.of("Interest calculation period"));
        this.settingInterestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::settingInterestCalculationPeriodFieldUpdate));
        this.settingInterestCalculationPeriodIContainer.add(this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodFeedback = new TextFeedbackPanel("settingInterestCalculationPeriodFeedback", this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodIContainer.add(this.settingInterestCalculationPeriodFeedback);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = new WebMarkupBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
        this.form.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer = new WebMarkupContainer("settingCalculateInterestForExactDaysInPartialPeriodIContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodBlock.add(this.settingCalculateInterestForExactDaysInPartialPeriodIContainer);
        this.settingCalculateInterestForExactDaysInPartialPeriodField = new CheckBox("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this.itemPage, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodField.add(new OnChangeAjaxBehavior());
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodFeedback = new TextFeedbackPanel("settingCalculateInterestForExactDaysInPartialPeriodFeedback", this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodFeedback);

        this.settingRepaymentStrategyBlock = new WebMarkupBlock("settingRepaymentStrategyBlock", Size.Six_6);
        this.form.add(this.settingRepaymentStrategyBlock);
        this.settingRepaymentStrategyIContainer = new WebMarkupContainer("settingRepaymentStrategyIContainer");
        this.settingRepaymentStrategyBlock.add(this.settingRepaymentStrategyIContainer);
        this.settingRepaymentStrategyProvider = new RepaymentStrategyProvider();
        this.settingRepaymentStrategyField = new Select2SingleChoice<>("settingRepaymentStrategyField", new PropertyModel<>(this.itemPage, "settingRepaymentStrategyValue"), this.settingRepaymentStrategyProvider);
        this.settingRepaymentStrategyField.setLabel(Model.of("Repayment strategy"));
        this.settingRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.settingRepaymentStrategyIContainer.add(this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyFeedback = new TextFeedbackPanel("settingRepaymentStrategyFeedback", this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyIContainer.add(this.settingRepaymentStrategyFeedback);

        this.settingMoratoriumPrincipleBlock = new WebMarkupBlock("settingMoratoriumPrincipleBlock", Size.Six_6);
        this.form.add(this.settingMoratoriumPrincipleBlock);
        this.settingMoratoriumPrincipleIContainer = new WebMarkupContainer("settingMoratoriumPrincipleIContainer");
        this.settingMoratoriumPrincipleBlock.add(this.settingMoratoriumPrincipleIContainer);
        this.settingMoratoriumPrincipleField = new TextField<>("settingMoratoriumPrincipleField", new PropertyModel<>(this.itemPage, "settingMoratoriumPrincipleValue"));
        this.settingMoratoriumPrincipleField.setLabel(Model.of("Moratorium principle"));
        this.settingMoratoriumPrincipleField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumPrincipleIContainer.add(this.settingMoratoriumPrincipleField);
        this.settingMoratoriumPrincipleFeedback = new TextFeedbackPanel("settingMoratoriumPrincipleFeedback", this.settingMoratoriumPrincipleField);
        this.settingMoratoriumPrincipleIContainer.add(this.settingMoratoriumPrincipleFeedback);

        this.settingMoratoriumInterestBlock = new WebMarkupBlock("settingMoratoriumInterestBlock", Size.Six_6);
        this.form.add(this.settingMoratoriumInterestBlock);
        this.settingMoratoriumInterestIContainer = new WebMarkupContainer("settingMoratoriumInterestIContainer");
        this.settingMoratoriumInterestBlock.add(this.settingMoratoriumInterestIContainer);
        this.settingMoratoriumInterestField = new TextField<>("settingMoratoriumInterestField", new PropertyModel<>(this.itemPage, "settingMoratoriumInterestValue"));
        this.settingMoratoriumInterestField.setLabel(Model.of("Moratorium interest"));
        this.settingMoratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumInterestIContainer.add(this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestFeedback = new TextFeedbackPanel("settingMoratoriumInterestFeedback", this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestIContainer.add(this.settingMoratoriumInterestFeedback);

        this.settingInterestFreePeriodBlock = new WebMarkupBlock("settingInterestFreePeriodBlock", Size.Six_6);
        this.form.add(this.settingInterestFreePeriodBlock);
        this.settingInterestFreePeriodIContainer = new WebMarkupContainer("settingInterestFreePeriodIContainer");
        this.settingInterestFreePeriodBlock.add(this.settingInterestFreePeriodIContainer);
        this.settingInterestFreePeriodField = new TextField<>("settingInterestFreePeriodField", new PropertyModel<>(this.itemPage, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodField.setLabel(Model.of("Interest free period"));
        this.settingInterestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.settingInterestFreePeriodIContainer.add(this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodFeedback = new TextFeedbackPanel("settingInterestFreePeriodFeedback", this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodIContainer.add(this.settingInterestFreePeriodFeedback);

        this.settingArrearsToleranceBlock = new WebMarkupBlock("settingArrearsToleranceBlock", Size.Six_6);
        this.form.add(this.settingArrearsToleranceBlock);
        this.settingArrearsToleranceIContainer = new WebMarkupContainer("settingArrearsToleranceIContainer");
        this.settingArrearsToleranceBlock.add(this.settingArrearsToleranceIContainer);
        this.settingArrearsToleranceField = new TextField<>("settingArrearsToleranceField", new PropertyModel<>(this.itemPage, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceField.setLabel(Model.of("Arrears tolerance"));
        this.settingArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.settingArrearsToleranceIContainer.add(this.settingArrearsToleranceField);
        this.settingArrearsToleranceFeedback = new TextFeedbackPanel("settingArrearsToleranceFeedback", this.settingArrearsToleranceField);
        this.settingArrearsToleranceIContainer.add(this.settingArrearsToleranceFeedback);

        this.settingDayInYearBlock = new WebMarkupBlock("settingDayInYearBlock", Size.Six_6);
        this.form.add(this.settingDayInYearBlock);
        this.settingDayInYearIContainer = new WebMarkupContainer("settingDayInYearIContainer");
        this.settingDayInYearBlock.add(this.settingDayInYearIContainer);
        this.settingDayInYearProvider = new DayInYearProvider();
        this.settingDayInYearField = new Select2SingleChoice<>("settingDayInYearField", new PropertyModel<>(this.itemPage, "settingDayInYearValue"), this.settingDayInYearProvider);
        this.settingDayInYearField.setLabel(Model.of("Day in year"));
        this.settingDayInYearField.add(new OnChangeAjaxBehavior());
        this.settingDayInYearIContainer.add(this.settingDayInYearField);
        this.settingDayInYearFeedback = new TextFeedbackPanel("settingDayInYearFeedback", this.settingDayInYearField);
        this.settingDayInYearIContainer.add(this.settingDayInYearFeedback);

        this.settingDayInMonthBlock = new WebMarkupBlock("settingDayInMonthBlock", Size.Six_6);
        this.form.add(this.settingDayInMonthBlock);
        this.settingDayInMonthIContainer = new WebMarkupContainer("settingDayInMonthIContainer");
        this.settingDayInMonthBlock.add(this.settingDayInMonthIContainer);
        this.settingDayInMonthProvider = new DayInMonthProvider();
        this.settingDayInMonthField = new Select2SingleChoice<>("settingDayInMonthField", new PropertyModel<>(this.itemPage, "settingDayInMonthValue"), this.settingDayInMonthProvider);
        this.settingDayInMonthField.setLabel(Model.of("Day in month"));
        this.settingDayInMonthField.add(new OnChangeAjaxBehavior());
        this.settingDayInMonthIContainer.add(this.settingDayInMonthField);
        this.settingDayInMonthFeedback = new TextFeedbackPanel("settingDayInMonthFeedback", this.settingDayInMonthField);
        this.settingDayInMonthIContainer.add(this.settingDayInMonthFeedback);

        this.settingAllowFixingOfTheInstallmentAmountBlock = new WebMarkupBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Twelve_12);
        this.form.add(this.settingAllowFixingOfTheInstallmentAmountBlock);
        this.settingAllowFixingOfTheInstallmentAmountIContainer = new WebMarkupContainer("settingAllowFixingOfTheInstallmentAmountIContainer");
        this.settingAllowFixingOfTheInstallmentAmountBlock.add(this.settingAllowFixingOfTheInstallmentAmountIContainer);
        this.settingAllowFixingOfTheInstallmentAmountField = new CheckBox("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this.itemPage, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());
        this.settingAllowFixingOfTheInstallmentAmountIContainer.add(this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountFeedback = new TextFeedbackPanel("settingAllowFixingOfTheInstallmentAmountFeedback", this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountIContainer.add(this.settingAllowFixingOfTheInstallmentAmountFeedback);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = new WebMarkupBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Six_6);
        this.form.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer = new WebMarkupContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this.itemPage, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setLabel(Model.of("Number of days a loan may be overdue before moving into arrears"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback = new TextFeedbackPanel("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback", this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = new WebMarkupBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Six_6);
        this.form.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer = new WebMarkupContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this.itemPage, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setLabel(Model.of("Maximum number of days a loan may be overdue before becoming a NPA"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback = new TextFeedbackPanel("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback", this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = new WebMarkupBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
        this.form.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer = new WebMarkupContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this.itemPage, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback = new TextFeedbackPanel("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback", this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback);

        this.settingPrincipleThresholdForLastInstalmentBlock = new WebMarkupBlock("settingPrincipleThresholdForLastInstalmentBlock", Size.Six_6);
        this.form.add(this.settingPrincipleThresholdForLastInstalmentBlock);
        this.settingPrincipleThresholdForLastInstalmentIContainer = new WebMarkupContainer("settingPrincipleThresholdForLastInstalmentIContainer");
        this.settingPrincipleThresholdForLastInstalmentBlock.add(this.settingPrincipleThresholdForLastInstalmentIContainer);
        this.settingPrincipleThresholdForLastInstalmentField = new TextField<>("settingPrincipleThresholdForLastInstalmentField", new PropertyModel<>(this.itemPage, "settingPrincipleThresholdForLastInstalmentValue"));
        this.settingPrincipleThresholdForLastInstalmentField.setLabel(Model.of("Principle Threshold (%) for Last Installment"));
        this.settingPrincipleThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.settingPrincipleThresholdForLastInstalmentIContainer.add(this.settingPrincipleThresholdForLastInstalmentField);
        this.settingPrincipleThresholdForLastInstalmentFeedback = new TextFeedbackPanel("settingPrincipleThresholdForLastInstalmentFeedback", this.settingPrincipleThresholdForLastInstalmentField);
        this.settingPrincipleThresholdForLastInstalmentIContainer.add(this.settingPrincipleThresholdForLastInstalmentFeedback);

        this.settingVariableInstallmentsAllowedBlock = new WebMarkupBlock("settingVariableInstallmentsAllowedBlock", Size.Four_4);
        this.form.add(this.settingVariableInstallmentsAllowedBlock);
        this.settingVariableInstallmentsAllowedIContainer = new WebMarkupContainer("settingVariableInstallmentsAllowedIContainer");
        this.settingVariableInstallmentsAllowedBlock.add(this.settingVariableInstallmentsAllowedIContainer);
        this.settingVariableInstallmentsAllowedField = new CheckBox("settingVariableInstallmentsAllowedField", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue"));
        this.settingVariableInstallmentsAllowedField.add(new OnChangeAjaxBehavior(this::settingVariableInstallmentsAllowedFieldUpdate));
        this.settingVariableInstallmentsAllowedIContainer.add(this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedFeedback = new TextFeedbackPanel("settingVariableInstallmentsAllowedFeedback", this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedIContainer.add(this.settingVariableInstallmentsAllowedFeedback);

        this.settingVariableInstallmentsMinimumBlock = new WebMarkupBlock("settingVariableInstallmentsMinimumBlock", Size.Four_4);
        this.form.add(this.settingVariableInstallmentsMinimumBlock);
        this.settingVariableInstallmentsMinimumIContainer = new WebMarkupContainer("settingVariableInstallmentsMinimumIContainer");
        this.settingVariableInstallmentsMinimumBlock.add(this.settingVariableInstallmentsMinimumIContainer);
        this.settingVariableInstallmentsMinimumField = new TextField<>("settingVariableInstallmentsMinimumField", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMinimumValue"));
        this.settingVariableInstallmentsMinimumField.setLabel(Model.of("Variable Installments Minimum"));
        this.settingVariableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMinimumIContainer.add(this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMinimumFeedback", this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumIContainer.add(this.settingVariableInstallmentsMinimumFeedback);

        this.settingVariableInstallmentsMaximumBlock = new WebMarkupBlock("settingVariableInstallmentsMaximumBlock", Size.Four_4);
        this.form.add(this.settingVariableInstallmentsMaximumBlock);
        this.settingVariableInstallmentsMaximumIContainer = new WebMarkupContainer("settingVariableInstallmentsMaximumIContainer");
        this.settingVariableInstallmentsMaximumBlock.add(this.settingVariableInstallmentsMaximumIContainer);
        this.settingVariableInstallmentsMaximumField = new TextField<>("settingVariableInstallmentsMaximumField", new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMaximumValue"));
        this.settingVariableInstallmentsMaximumField.setLabel(Model.of("Variable Installments Maximum"));
        this.settingVariableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMaximumIContainer.add(this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumFeedback = new TextFeedbackPanel("settingVariableInstallmentsMaximumFeedback", this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumIContainer.add(this.settingVariableInstallmentsMaximumFeedback);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = new WebMarkupBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Twelve_12);
        this.form.add(this.settingAllowedToBeUsedForProvidingTopupLoansBlock);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer = new WebMarkupContainer("settingAllowedToBeUsedForProvidingTopupLoansIContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansBlock.add(this.settingAllowedToBeUsedForProvidingTopupLoansIContainer);
        this.settingAllowedToBeUsedForProvidingTopupLoansField = new CheckBox("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this.itemPage, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansField.add(new OnChangeAjaxBehavior());
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansFeedback = new TextFeedbackPanel("settingAllowedToBeUsedForProvidingTopupLoansFeedback", this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansFeedback);
    }

    @Override
    protected void configureMetaData() {
        settingInterestCalculationPeriodFieldUpdate(null);

        interestRecalculationRecalculateInterestFieldUpdate(null);

        interestRecalculationCompoundingFieldUpdate(null);

        interestRecalculationRecalculateFieldUpdate(null);

        guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(null);

        loanTrancheDetailEnableMultipleDisbursalFieldUpdate(null);

        configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(null);

        settingVariableInstallmentsAllowedFieldUpdate(null);
    }

    protected boolean loanTrancheDetailEnableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> loanTrancheDetailEnableMultipleDisbursalValue = new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue");
        boolean visible = loanTrancheDetailEnableMultipleDisbursalValue.getObject() != null && loanTrancheDetailEnableMultipleDisbursalValue.getObject();
        this.loanTrancheDetailMaximumTrancheCountIContainer.setVisible(visible);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.loanTrancheDetailMaximumTrancheCountBlock);
            target.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        }
        return false;
    }

    protected boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue");
        boolean visible = guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject() != null && guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject();
        this.guaranteeRequirementMandatoryGuaranteeIContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeIContainer.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.guaranteeRequirementMandatoryGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeBlock);
            target.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock);
        }
        return false;
    }

    protected boolean settingInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Option> settingInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue");
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (settingInterestCalculationPeriodValue.getObject() != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(settingInterestCalculationPeriodValue.getObject().getId());
        }

        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment;

        if (interestCalculationPeriod == InterestCalculationPeriod.Daily) {
            this.interestRecalculationRecalculateInterestIContainer.setVisible(true);
        } else if (interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment) {
            this.interestRecalculationRecalculateInterestIContainer.setVisible(false);
        }

        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
            target.add(this.interestRecalculationRecalculateInterestBlock);
        }

        interestRecalculationRecalculateInterestFieldUpdate(target);

        // initSectionValidationRule();

        return false;
    }

    protected boolean interestRecalculationRecalculateInterestFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Option> settingInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue");
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (settingInterestCalculationPeriodValue.getObject() != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(settingInterestCalculationPeriodValue.getObject().getId());
        }

        PropertyModel<Boolean> interestRecalculationRecalculateInterestValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue");
        boolean visible = interestCalculationPeriod == InterestCalculationPeriod.Daily && interestRecalculationRecalculateInterestValue.getObject() != null && interestRecalculationRecalculateInterestValue.getObject();

        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.setVisible(visible);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.setVisible(visible);
        this.interestRecalculationCompoundingOnIContainer.setVisible(visible);
        this.interestRecalculationCompoundingIContainer.setVisible(visible);
        this.interestRecalculationCompoundingTypeIContainer.setVisible(visible);
        this.interestRecalculationCompoundingDayIContainer.setVisible(visible);
        this.interestRecalculationCompoundingIntervalIContainer.setVisible(visible);
        this.interestRecalculationRecalculateIContainer.setVisible(visible);
        this.interestRecalculationRecalculateTypeIContainer.setVisible(visible);
        this.interestRecalculationRecalculateDayIContainer.setVisible(visible);
        this.interestRecalculationRecalculateIntervalIContainer.setVisible(visible);
        if (visible) {
            interestRecalculationRecalculateFieldUpdate(target);
            interestRecalculationCompoundingFieldUpdate(target);
        }
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
            target.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
            target.add(this.interestRecalculationCompoundingOnBlock);
            target.add(this.interestRecalculationCompoundingBlock);
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.interestRecalculationCompoundingIntervalBlock);
            target.add(this.interestRecalculationRecalculateBlock);
            target.add(this.interestRecalculationRecalculateTypeBlock);
            target.add(this.interestRecalculationRecalculateDayBlock);
            target.add(this.interestRecalculationRecalculateIntervalBlock);
            target.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        }

        // initSectionValidationRule();
        return false;
    }

    protected boolean settingVariableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> settingVariableInstallmentsAllowedValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue");
        boolean visible = settingVariableInstallmentsAllowedValue.getObject() != null && settingVariableInstallmentsAllowedValue.getObject();
        this.settingVariableInstallmentsMinimumIContainer.setVisible(visible);
        this.settingVariableInstallmentsMaximumIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingVariableInstallmentsMinimumBlock);
            target.add(this.settingVariableInstallmentsMaximumBlock);
        }
        return false;
    }

    protected boolean interestRecalculationRecalculateFieldUpdate(AjaxRequestTarget target) {
        this.interestRecalculationRecalculateTypeIContainer.setVisible(false);
        this.interestRecalculationRecalculateDayIContainer.setVisible(false);
        this.interestRecalculationRecalculateIntervalIContainer.setVisible(false);
        this.interestRecalculationRecalculateOnDayIContainer.setVisible(false);

        PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
        PropertyModel<Option> interestRecalculationRecalculateTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue");
        if (interestRecalculationRecalculateValue.getObject() != null) {
            Frequency frequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
            if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateIntervalIContainer.setVisible(true);
            }
            if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateDayIContainer.setVisible(true);
            }
            if (frequency == Frequency.Monthly) {
                this.interestRecalculationRecalculateTypeIContainer.setVisible(true);

                if (interestRecalculationRecalculateTypeValue.getObject() != null) {
                    FrequencyType type = FrequencyType.valueOf(interestRecalculationRecalculateTypeValue.getObject().getId());
                    this.interestRecalculationRecalculateDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                    this.interestRecalculationRecalculateOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                }
            }
        }
        if (target != null) {
            target.add(this.interestRecalculationRecalculateTypeBlock);
            target.add(this.interestRecalculationRecalculateDayBlock);
            target.add(this.interestRecalculationRecalculateIntervalBlock);
        }
        return false;
    }

    protected boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue");
        boolean visible = configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject() != null && configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject();
        this.configurableAmortizationIContainer.setVisible(visible);
        this.configurableInterestMethodIContainer.setVisible(visible);
        this.configurableRepaymentStrategyIContainer.setVisible(visible);
        this.configurableInterestCalculationPeriodIContainer.setVisible(visible);
        this.configurableArrearsToleranceIContainer.setVisible(visible);
        this.configurableRepaidEveryIContainer.setVisible(visible);
        this.configurableMoratoriumIContainer.setVisible(visible);
        this.configurableOverdueBeforeMovingIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.configurableAmortizationBlock);
            target.add(this.configurableInterestMethodBlock);
            target.add(this.configurableRepaymentStrategyBlock);
            target.add(configurableInterestCalculationPeriodBlock);
            target.add(this.configurableArrearsToleranceBlock);
            target.add(this.configurableRepaidEveryBlock);
            target.add(this.configurableMoratoriumBlock);
            target.add(this.configurableOverdueBeforeMovingBlock);
        }
        return false;
    }

    protected boolean interestRecalculationCompoundingFieldUpdate(AjaxRequestTarget target) {
        this.interestRecalculationCompoundingTypeIContainer.setVisible(false);
        this.interestRecalculationCompoundingDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingOnDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingIntervalIContainer.setVisible(false);

        PropertyModel<Option> interestRecalculationCompoundingOnValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue");

        if (interestRecalculationCompoundingOnValue.getObject() != null && InterestRecalculationCompound.valueOf(interestRecalculationCompoundingOnValue.getObject().getId()) != InterestRecalculationCompound.None) {
            this.interestRecalculationCompoundingIContainer.setVisible(true);

            PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");

            if (interestRecalculationCompoundingValue.getObject() != null) {
                Frequency frequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
                if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingIntervalIContainer.setVisible(true);
                }
                if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingDayIContainer.setVisible(true);
                }
                if (frequency == Frequency.Monthly) {
                    this.interestRecalculationCompoundingTypeIContainer.setVisible(true);

                    PropertyModel<Option> interestRecalculationCompoundingTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue");

                    if (interestRecalculationCompoundingTypeValue.getObject() != null) {
                        FrequencyType type = FrequencyType.valueOf(interestRecalculationCompoundingTypeValue.getObject().getId());
                        this.interestRecalculationCompoundingDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                        this.interestRecalculationCompoundingOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                    }
                }
            }
        } else {
            this.interestRecalculationCompoundingIContainer.setVisible(false);
        }
        if (target != null) {
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.interestRecalculationCompoundingIntervalBlock);
            target.add(this.interestRecalculationCompoundingBlock);
        }
        return false;
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CHARGE);
        this.errorSetting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
