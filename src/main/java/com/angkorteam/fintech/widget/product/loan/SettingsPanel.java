package com.angkorteam.fintech.widget.product.loan;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
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
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;

public class SettingsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorSetting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    // Settings

    protected UIRow row1;

    protected UIBlock settingAmortizationBlock;
    protected UIContainer settingAmortizationIContainer;
    protected AmortizationProvider settingAmortizationProvider;
    protected Select2SingleChoice<Option> settingAmortizationField;

    protected UIBlock settingInterestMethodBlock;
    protected UIContainer settingInterestMethodIContainer;
    protected InterestMethodProvider settingInterestMethodProvider;
    protected Select2SingleChoice<Option> settingInterestMethodField;

    protected UIBlock settingEqualAmortizationBlock;
    protected UIContainer settingEqualAmortizationIContainer;
    protected Boolean settingEqualAmortizationValue;
    protected CheckBox settingEqualAmortizationField;

    protected UIRow row2;

    protected UIBlock settingInterestCalculationPeriodBlock;
    protected UIContainer settingInterestCalculationPeriodIContainer;
    protected InterestCalculationPeriodProvider settingInterestCalculationPeriodProvider;
    protected Select2SingleChoice<Option> settingInterestCalculationPeriodField;

    protected UIBlock settingCalculateInterestForExactDaysInPartialPeriodBlock;
    protected UIContainer settingCalculateInterestForExactDaysInPartialPeriodIContainer;
    protected Boolean settingCalculateInterestForExactDaysInPartialPeriodValue;
    protected CheckBox settingCalculateInterestForExactDaysInPartialPeriodField;

    protected UIRow row3;

    protected UIBlock settingRepaymentStrategyBlock;
    protected UIContainer settingRepaymentStrategyIContainer;
    protected RepaymentStrategyProvider settingRepaymentStrategyProvider;
    protected Select2SingleChoice<Option> settingRepaymentStrategyField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock settingMoratoriumPrincipleBlock;
    protected UIContainer settingMoratoriumPrincipleIContainer;
    protected TextField<Long> settingMoratoriumPrincipleField;

    protected UIBlock settingMoratoriumInterestBlock;
    protected UIContainer settingMoratoriumInterestIContainer;
    protected TextField<Long> settingMoratoriumInterestField;

    protected UIRow row5;

    protected UIBlock settingInterestFreePeriodBlock;
    protected UIContainer settingInterestFreePeriodIContainer;
    protected TextField<Long> settingInterestFreePeriodField;

    protected UIBlock settingArrearsToleranceBlock;
    protected UIContainer settingArrearsToleranceIContainer;
    protected TextField<Double> settingArrearsToleranceField;

    protected UIRow row6;

    protected UIBlock settingDayInYearBlock;
    protected UIContainer settingDayInYearIContainer;
    protected DayInYearProvider settingDayInYearProvider;
    protected Select2SingleChoice<Option> settingDayInYearField;

    protected UIBlock settingDayInMonthBlock;
    protected UIContainer settingDayInMonthIContainer;
    protected DayInMonthProvider settingDayInMonthProvider;
    protected Select2SingleChoice<Option> settingDayInMonthField;

    protected UIRow row7;

    protected UIBlock settingAllowFixingOfTheInstallmentAmountBlock;
    protected UIContainer settingAllowFixingOfTheInstallmentAmountIContainer;
    protected CheckBox settingAllowFixingOfTheInstallmentAmountField;

    protected UIRow row8;

    protected UIBlock settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock;
    protected UIContainer settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer;
    protected TextField<Long> settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField;

    protected UIBlock settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock;
    protected UIContainer settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer;
    protected TextField<Long> settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField;

    protected UIRow row9;

    protected UIBlock settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock;
    protected UIContainer settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer;
    protected CheckBox settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField;

    protected UIBlock settingPrincipleThresholdForLastInstalmentBlock;
    protected UIContainer settingPrincipleThresholdForLastInstalmentIContainer;
    protected TextField<Double> settingPrincipleThresholdForLastInstalmentField;

    protected UIRow row10;

    protected UIBlock settingVariableInstallmentsAllowedBlock;
    protected UIContainer settingVariableInstallmentsAllowedIContainer;
    protected CheckBox settingVariableInstallmentsAllowedField;
    protected PropertyModel<Boolean> settingVariableInstallmentsAllowedValue;

    protected UIBlock settingVariableInstallmentsMinimumBlock;
    protected UIContainer settingVariableInstallmentsMinimumIContainer;
    protected TextField<Long> settingVariableInstallmentsMinimumField;
    protected PropertyModel<Long> settingVariableInstallmentsMinimumValue;

    protected UIBlock settingVariableInstallmentsMaximumBlock;
    protected UIContainer settingVariableInstallmentsMaximumIContainer;
    protected TextField<Long> settingVariableInstallmentsMaximumField;
    protected PropertyModel<Long> settingVariableInstallmentsMaximumValue;

    protected UIRow row11;

    protected UIBlock settingAllowedToBeUsedForProvidingTopupLoansBlock;
    protected UIContainer settingAllowedToBeUsedForProvidingTopupLoansIContainer;
    protected CheckBox settingAllowedToBeUsedForProvidingTopupLoansField;

    // Interest Recalculation

    protected UIRow row12;

    protected UIBlock interestRecalculationRecalculateInterestBlock;
    protected UIContainer interestRecalculationRecalculateInterestIContainer;
    protected CheckBox interestRecalculationRecalculateInterestField;

    protected UIRow row13;

    protected UIBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected UIContainer interestRecalculationPreClosureInterestCalculationRuleIContainer;
    protected ClosureInterestCalculationRuleProvider interestRecalculationPreClosureInterestCalculationRuleProvider;
    protected Select2SingleChoice<Option> interestRecalculationPreClosureInterestCalculationRuleField;

    protected UIBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected UIContainer interestRecalculationAdvancePaymentsAdjustmentTypeIContainer;
    protected AdvancePaymentsAdjustmentTypeProvider interestRecalculationAdvancePaymentsAdjustmentTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationAdvancePaymentsAdjustmentTypeField;

    protected UIRow row14;

    protected UIBlock interestRecalculationCompoundingOnBlock;
    protected UIContainer interestRecalculationCompoundingOnIContainer;
    protected InterestRecalculationCompoundProvider interestRecalculationCompoundingOnProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnField;

    protected UIBlock row14Block1;

    protected UIRow row15;

    protected UIBlock interestRecalculationCompoundingBlock;
    protected UIContainer interestRecalculationCompoundingIContainer;
    protected FrequencyProvider interestRecalculationCompoundingProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingField;

    protected UIBlock interestRecalculationCompoundingTypeBlock;
    protected UIContainer interestRecalculationCompoundingTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationCompoundingTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingTypeField;

    protected UIBlock interestRecalculationCompoundingDayBlock;

    protected UIContainer interestRecalculationCompoundingDayIContainer;
    protected FrequencyDayProvider interestRecalculationCompoundingDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingDayField;

    protected UIContainer interestRecalculationCompoundingOnDayIContainer;
    protected OnDayProvider interestRecalculationCompoundingOnDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationCompoundingOnDayField;

    protected UIRow row16;

    protected UIBlock interestRecalculationCompoundingIntervalBlock;
    protected UIContainer interestRecalculationCompoundingIntervalIContainer;
    protected TextField<Long> interestRecalculationCompoundingIntervalField;

    protected UIBlock row16Block1;

    protected UIRow row17;

    protected UIBlock interestRecalculationRecalculateBlock;
    protected UIContainer interestRecalculationRecalculateIContainer;
    protected FrequencyProvider interestRecalculationRecalculateProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateField;

    protected UIBlock interestRecalculationRecalculateTypeBlock;
    protected UIContainer interestRecalculationRecalculateTypeIContainer;
    protected FrequencyTypeProvider interestRecalculationRecalculateTypeProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateTypeField;

    protected UIBlock interestRecalculationRecalculateDayBlock;

    protected UIContainer interestRecalculationRecalculateDayIContainer;
    protected FrequencyDayProvider interestRecalculationRecalculateDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateDayField;

    protected UIContainer interestRecalculationRecalculateOnDayIContainer;
    protected OnDayProvider interestRecalculationRecalculateOnDayProvider;
    protected Select2SingleChoice<Option> interestRecalculationRecalculateOnDayField;

    protected UIRow row18;

    protected UIBlock interestRecalculationRecalculateIntervalBlock;
    protected UIContainer interestRecalculationRecalculateIntervalIContainer;
    protected TextField<Long> interestRecalculationRecalculateIntervalField;

    protected UIBlock row18Block1;

    protected UIRow row19;

    protected UIBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected UIContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer;
    protected CheckBox interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField;

    // Guarantee Requirements

    protected UIRow row20;

    protected UIBlock guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock;
    protected UIContainer guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer;
    protected CheckBox guaranteeRequirementPlaceGuaranteeFundsOnHoldField;
    protected PropertyModel<Boolean> guaranteeRequirementPlaceGuaranteeFundsOnHoldValue;

    protected UIRow row21;

    protected UIBlock guaranteeRequirementMandatoryGuaranteeBlock;
    protected UIContainer guaranteeRequirementMandatoryGuaranteeIContainer;
    protected TextField<Double> guaranteeRequirementMandatoryGuaranteeField;
    protected PropertyModel<Double> guaranteeRequirementMandatoryGuaranteeValue;

    protected UIBlock guaranteeRequirementMinimumGuaranteeBlock;
    protected UIContainer guaranteeRequirementMinimumGuaranteeIContainer;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeField;
    protected PropertyModel<Double> guaranteeRequirementMinimumGuaranteeValue;

    protected UIBlock guaranteeRequirementMinimumGuaranteeFromGuarantorBlock;
    protected UIContainer guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer;
    protected TextField<Double> guaranteeRequirementMinimumGuaranteeFromGuarantorField;
    protected PropertyModel<Double> guaranteeRequirementMinimumGuaranteeFromGuarantorValue;

    // Loan Tranche Details

    protected UIRow row22;

    protected UIBlock loanTrancheDetailEnableMultipleDisbursalBlock;
    protected UIContainer loanTrancheDetailEnableMultipleDisbursalIContainer;
    protected CheckBox loanTrancheDetailEnableMultipleDisbursalField;
    protected PropertyModel<Boolean> loanTrancheDetailEnableMultipleDisbursalValue;

    protected UIBlock loanTrancheDetailMaximumTrancheCountBlock;
    protected UIContainer loanTrancheDetailMaximumTrancheCountIContainer;
    protected TextField<Long> loanTrancheDetailMaximumTrancheCountField;

    protected UIBlock loanTrancheDetailMaximumAllowedOutstandingBalanceBlock;
    protected UIContainer loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer;
    protected TextField<Double> loanTrancheDetailMaximumAllowedOutstandingBalanceField;

    // Configurable Terms and Settings

    protected UIRow row23;

    protected UIBlock configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock;
    protected UIContainer configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer;
    protected CheckBox configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField;

    protected UIRow row24;

    protected UIBlock configurableAmortizationBlock;
    protected UIContainer configurableAmortizationIContainer;
    protected CheckBox configurableAmortizationField;

    protected UIBlock configurableInterestMethodBlock;
    protected UIContainer configurableInterestMethodIContainer;
    protected CheckBox configurableInterestMethodField;

    protected UIRow row25;

    protected UIBlock configurableRepaymentStrategyBlock;
    protected UIContainer configurableRepaymentStrategyIContainer;
    protected CheckBox configurableRepaymentStrategyField;

    protected UIBlock configurableInterestCalculationPeriodBlock;
    protected UIContainer configurableInterestCalculationPeriodIContainer;
    protected CheckBox configurableInterestCalculationPeriodField;

    protected UIRow row26;

    protected UIBlock configurableArrearsToleranceBlock;
    protected UIContainer configurableArrearsToleranceIContainer;
    protected CheckBox configurableArrearsToleranceField;

    protected UIBlock configurableRepaidEveryBlock;
    protected UIContainer configurableRepaidEveryIContainer;
    protected Boolean configurableRepaidEveryValue;
    protected CheckBox configurableRepaidEveryField;

    protected UIRow row27;

    protected UIBlock configurableMoratoriumBlock;
    protected UIContainer configurableMoratoriumIContainer;
    protected CheckBox configurableMoratoriumField;

    protected UIBlock configurableOverdueBeforeMovingBlock;
    protected UIContainer configurableOverdueBeforeMovingIContainer;
    protected CheckBox configurableOverdueBeforeMovingField;

    public SettingsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.settingAmortizationBlock = this.row1.newUIBlock("settingAmortizationBlock", Size.Four_4);
        this.settingAmortizationIContainer = this.settingAmortizationBlock.newUIContainer("settingAmortizationIContainer");
        this.settingAmortizationField = new Select2SingleChoice<>("settingAmortizationField", new PropertyModel<>(this.itemPage, "settingAmortizationValue"), this.settingAmortizationProvider);
        this.settingAmortizationIContainer.add(this.settingAmortizationField);
        this.settingAmortizationIContainer.newFeedback("settingAmortizationFeedback", this.settingAmortizationField);

        this.settingInterestMethodBlock = this.row1.newUIBlock("settingInterestMethodBlock", Size.Four_4);
        this.settingInterestMethodIContainer = this.settingInterestMethodBlock.newUIContainer("settingInterestMethodIContainer");
        this.settingInterestMethodField = new Select2SingleChoice<>("settingInterestMethodField", new PropertyModel<>(this.itemPage, "settingInterestMethodValue"), this.settingInterestMethodProvider);
        this.settingInterestMethodIContainer.add(this.settingInterestMethodField);
        this.settingInterestMethodIContainer.newFeedback("settingInterestMethodFeedback", this.settingInterestMethodField);

        this.settingEqualAmortizationBlock = this.row1.newUIBlock("settingEqualAmortizationBlock", Size.Four_4);
        this.settingEqualAmortizationIContainer = this.settingEqualAmortizationBlock.newUIContainer("settingEqualAmortizationIContainer");
        this.settingEqualAmortizationField = new CheckBox("settingEqualAmortizationField", new PropertyModel<>(this.itemPage, "settingEqualAmortizationValue"));
        this.settingEqualAmortizationIContainer.add(this.settingEqualAmortizationField);
        this.settingEqualAmortizationIContainer.newFeedback("settingEqualAmortizationFeedback", this.settingEqualAmortizationField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.settingInterestCalculationPeriodBlock = this.row2.newUIBlock("settingInterestCalculationPeriodBlock", Size.Six_6);
        this.settingInterestCalculationPeriodIContainer = this.settingInterestCalculationPeriodBlock.newUIContainer("settingInterestCalculationPeriodIContainer");
        this.settingInterestCalculationPeriodField = new Select2SingleChoice<>("settingInterestCalculationPeriodField", new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue"), this.settingInterestCalculationPeriodProvider);
        this.settingInterestCalculationPeriodIContainer.add(this.settingInterestCalculationPeriodField);
        this.settingInterestCalculationPeriodIContainer.newFeedback("settingInterestCalculationPeriodFeedback", this.settingInterestCalculationPeriodField);

        this.settingCalculateInterestForExactDaysInPartialPeriodBlock = this.row2.newUIBlock("settingCalculateInterestForExactDaysInPartialPeriodBlock", Size.Six_6);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer = this.settingCalculateInterestForExactDaysInPartialPeriodBlock.newUIContainer("settingCalculateInterestForExactDaysInPartialPeriodIContainer");
        this.settingCalculateInterestForExactDaysInPartialPeriodField = new CheckBox("settingCalculateInterestForExactDaysInPartialPeriodField", new PropertyModel<>(this.itemPage, "settingCalculateInterestForExactDaysInPartialPeriodValue"));
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.add(this.settingCalculateInterestForExactDaysInPartialPeriodField);
        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.newFeedback("settingCalculateInterestForExactDaysInPartialPeriodFeedback", this.settingCalculateInterestForExactDaysInPartialPeriodField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.settingRepaymentStrategyBlock = this.row3.newUIBlock("settingRepaymentStrategyBlock", Size.Six_6);
        this.settingRepaymentStrategyIContainer = this.settingRepaymentStrategyBlock.newUIContainer("settingRepaymentStrategyIContainer");
        this.settingRepaymentStrategyField = new Select2SingleChoice<>("settingRepaymentStrategyField", new PropertyModel<>(this.itemPage, "settingRepaymentStrategyValue"), this.settingRepaymentStrategyProvider);
        this.settingRepaymentStrategyIContainer.add(this.settingRepaymentStrategyField);
        this.settingRepaymentStrategyIContainer.newFeedback("settingRepaymentStrategyFeedback", this.settingRepaymentStrategyField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.settingMoratoriumPrincipleBlock = this.row4.newUIBlock("settingMoratoriumPrincipleBlock", Size.Six_6);
        this.settingMoratoriumPrincipleIContainer = this.settingMoratoriumPrincipleBlock.newUIContainer("settingMoratoriumPrincipleIContainer");
        this.settingMoratoriumPrincipleField = new TextField<>("settingMoratoriumPrincipleField", new PropertyModel<>(this.itemPage, "settingMoratoriumPrincipleValue"));
        this.settingMoratoriumPrincipleIContainer.add(this.settingMoratoriumPrincipleField);
        this.settingMoratoriumPrincipleIContainer.newFeedback("settingMoratoriumPrincipleFeedback", this.settingMoratoriumPrincipleField);

        this.settingMoratoriumInterestBlock = this.row4.newUIBlock("settingMoratoriumInterestBlock", Size.Six_6);
        this.settingMoratoriumInterestIContainer = this.settingMoratoriumInterestBlock.newUIContainer("settingMoratoriumInterestIContainer");
        this.settingMoratoriumInterestField = new TextField<>("settingMoratoriumInterestField", new PropertyModel<>(this.itemPage, "settingMoratoriumInterestValue"));
        this.settingMoratoriumInterestIContainer.add(this.settingMoratoriumInterestField);
        this.settingMoratoriumInterestIContainer.newFeedback("settingMoratoriumInterestFeedback", this.settingMoratoriumInterestField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.settingInterestFreePeriodBlock = this.row5.newUIBlock("settingInterestFreePeriodBlock", Size.Six_6);
        this.settingInterestFreePeriodIContainer = this.settingInterestFreePeriodBlock.newUIContainer("settingInterestFreePeriodIContainer");
        this.settingInterestFreePeriodField = new TextField<>("settingInterestFreePeriodField", new PropertyModel<>(this.itemPage, "settingInterestFreePeriodValue"));
        this.settingInterestFreePeriodIContainer.add(this.settingInterestFreePeriodField);
        this.settingInterestFreePeriodIContainer.newFeedback("settingInterestFreePeriodFeedback", this.settingInterestFreePeriodField);

        this.settingArrearsToleranceBlock = this.row5.newUIBlock("settingArrearsToleranceBlock", Size.Six_6);
        this.settingArrearsToleranceIContainer = this.settingArrearsToleranceBlock.newUIContainer("settingArrearsToleranceIContainer");
        this.settingArrearsToleranceField = new TextField<>("settingArrearsToleranceField", new PropertyModel<>(this.itemPage, "settingArrearsToleranceValue"));
        this.settingArrearsToleranceIContainer.add(this.settingArrearsToleranceField);
        this.settingArrearsToleranceIContainer.newFeedback("settingArrearsToleranceFeedback", this.settingArrearsToleranceField);

        this.row6 = UIRow.newUIRow("row6", this.form);

        this.settingDayInYearBlock = this.row6.newUIBlock("settingDayInYearBlock", Size.Six_6);
        this.settingDayInYearIContainer = this.settingDayInYearBlock.newUIContainer("settingDayInYearIContainer");
        this.settingDayInYearField = new Select2SingleChoice<>("settingDayInYearField", new PropertyModel<>(this.itemPage, "settingDayInYearValue"), this.settingDayInYearProvider);
        this.settingDayInYearIContainer.add(this.settingDayInYearField);
        this.settingDayInYearIContainer.newFeedback("settingDayInYearFeedback", this.settingDayInYearField);

        this.settingDayInMonthBlock = this.row6.newUIBlock("settingDayInMonthBlock", Size.Six_6);
        this.settingDayInMonthIContainer = this.settingDayInMonthBlock.newUIContainer("settingDayInMonthIContainer");
        this.settingDayInMonthField = new Select2SingleChoice<>("settingDayInMonthField", new PropertyModel<>(this.itemPage, "settingDayInMonthValue"), this.settingDayInMonthProvider);
        this.settingDayInMonthIContainer.add(this.settingDayInMonthField);
        this.settingDayInMonthIContainer.newFeedback("settingDayInMonthFeedback", this.settingDayInMonthField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.settingAllowFixingOfTheInstallmentAmountBlock = this.row7.newUIBlock("settingAllowFixingOfTheInstallmentAmountBlock", Size.Twelve_12);
        this.settingAllowFixingOfTheInstallmentAmountIContainer = this.settingAllowFixingOfTheInstallmentAmountBlock.newUIContainer("settingAllowFixingOfTheInstallmentAmountIContainer");
        this.settingAllowFixingOfTheInstallmentAmountField = new CheckBox("settingAllowFixingOfTheInstallmentAmountField", new PropertyModel<>(this.itemPage, "settingAllowFixingOfTheInstallmentAmountValue"));
        this.settingAllowFixingOfTheInstallmentAmountIContainer.add(this.settingAllowFixingOfTheInstallmentAmountField);
        this.settingAllowFixingOfTheInstallmentAmountIContainer.newFeedback("settingAllowFixingOfTheInstallmentAmountFeedback", this.settingAllowFixingOfTheInstallmentAmountField);

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock = this.row8.newUIBlock("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock", Size.Six_6);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer = this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock.newUIContainer("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer");
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField = new TextField<>("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", new PropertyModel<>(this.itemPage, "settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.add(this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsIContainer.newFeedback("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsFeedback", this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock = this.row8.newUIBlock("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock", Size.Six_6);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer = this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock.newUIContainer("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer");
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField = new TextField<>("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", new PropertyModel<>(this.itemPage, "settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.add(this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaIContainer.newFeedback("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaFeedback", this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField);

        this.row9 = UIRow.newUIRow("row9", this.form);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock = this.row9.newUIBlock("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock", Size.Six_6);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer = this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock.newUIContainer("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer");
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField = new CheckBox("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", new PropertyModel<>(this.itemPage, "settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue"));
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.add(this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);
        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedIContainer.newFeedback("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedFeedback", this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField);

        this.settingPrincipleThresholdForLastInstalmentBlock = this.row9.newUIBlock("settingPrincipleThresholdForLastInstalmentBlock", Size.Six_6);
        this.settingPrincipleThresholdForLastInstalmentIContainer = this.settingPrincipleThresholdForLastInstalmentBlock.newUIContainer("settingPrincipleThresholdForLastInstalmentIContainer");
        this.settingPrincipleThresholdForLastInstalmentField = new TextField<>("settingPrincipleThresholdForLastInstalmentField", new PropertyModel<>(this.itemPage, "settingPrincipleThresholdForLastInstalmentValue"));
        this.settingPrincipleThresholdForLastInstalmentIContainer.add(this.settingPrincipleThresholdForLastInstalmentField);
        this.settingPrincipleThresholdForLastInstalmentIContainer.newFeedback("settingPrincipleThresholdForLastInstalmentFeedback", this.settingPrincipleThresholdForLastInstalmentField);

        this.row10 = UIRow.newUIRow("row10", this.form);

        this.settingVariableInstallmentsAllowedBlock = this.row10.newUIBlock("settingVariableInstallmentsAllowedBlock", Size.Four_4);
        this.settingVariableInstallmentsAllowedIContainer = this.settingVariableInstallmentsAllowedBlock.newUIContainer("settingVariableInstallmentsAllowedIContainer");
        this.settingVariableInstallmentsAllowedField = new CheckBox("settingVariableInstallmentsAllowedField", this.settingVariableInstallmentsAllowedValue);
        this.settingVariableInstallmentsAllowedIContainer.add(this.settingVariableInstallmentsAllowedField);
        this.settingVariableInstallmentsAllowedIContainer.newFeedback("settingVariableInstallmentsAllowedFeedback", this.settingVariableInstallmentsAllowedField);

        this.settingVariableInstallmentsMinimumBlock = this.row10.newUIBlock("settingVariableInstallmentsMinimumBlock", Size.Four_4);
        this.settingVariableInstallmentsMinimumIContainer = this.settingVariableInstallmentsMinimumBlock.newUIContainer("settingVariableInstallmentsMinimumIContainer");
        this.settingVariableInstallmentsMinimumField = new TextField<>("settingVariableInstallmentsMinimumField", this.settingVariableInstallmentsMinimumValue);
        this.settingVariableInstallmentsMinimumIContainer.add(this.settingVariableInstallmentsMinimumField);
        this.settingVariableInstallmentsMinimumIContainer.newFeedback("settingVariableInstallmentsMinimumFeedback", this.settingVariableInstallmentsMinimumField);

        this.settingVariableInstallmentsMaximumBlock = this.row10.newUIBlock("settingVariableInstallmentsMaximumBlock", Size.Four_4);
        this.settingVariableInstallmentsMaximumIContainer = this.settingVariableInstallmentsMaximumBlock.newUIContainer("settingVariableInstallmentsMaximumIContainer");
        this.settingVariableInstallmentsMaximumField = new TextField<>("settingVariableInstallmentsMaximumField", this.settingVariableInstallmentsMaximumValue);
        this.settingVariableInstallmentsMaximumIContainer.add(this.settingVariableInstallmentsMaximumField);
        this.settingVariableInstallmentsMaximumIContainer.newFeedback("settingVariableInstallmentsMaximumFeedback", this.settingVariableInstallmentsMaximumField);

        this.row11 = UIRow.newUIRow("row11", this.form);

        this.settingAllowedToBeUsedForProvidingTopupLoansBlock = this.row11.newUIBlock("settingAllowedToBeUsedForProvidingTopupLoansBlock", Size.Twelve_12);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer = this.settingAllowedToBeUsedForProvidingTopupLoansBlock.newUIContainer("settingAllowedToBeUsedForProvidingTopupLoansIContainer");
        this.settingAllowedToBeUsedForProvidingTopupLoansField = new CheckBox("settingAllowedToBeUsedForProvidingTopupLoansField", new PropertyModel<>(this.itemPage, "settingAllowedToBeUsedForProvidingTopupLoansValue"));
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.add(this.settingAllowedToBeUsedForProvidingTopupLoansField);
        this.settingAllowedToBeUsedForProvidingTopupLoansIContainer.newFeedback("settingAllowedToBeUsedForProvidingTopupLoansFeedback", this.settingAllowedToBeUsedForProvidingTopupLoansField);

        this.row12 = UIRow.newUIRow("row12", this.form);

        this.interestRecalculationRecalculateInterestBlock = this.row12.newUIBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
        this.interestRecalculationRecalculateInterestIContainer = this.interestRecalculationRecalculateInterestBlock.newUIContainer("interestRecalculationRecalculateInterestIContainer");
        this.interestRecalculationRecalculateInterestField = new CheckBox("interestRecalculationRecalculateInterestField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestIContainer.add(this.interestRecalculationRecalculateInterestField);
        this.interestRecalculationRecalculateInterestIContainer.newFeedback("interestRecalculationRecalculateInterestFeedback", this.interestRecalculationRecalculateInterestField);

        this.row13 = UIRow.newUIRow("row13", this.form);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = this.row13.newUIBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer = this.interestRecalculationPreClosureInterestCalculationRuleBlock.newUIContainer("interestRecalculationPreClosureInterestCalculationRuleIContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleField = new Select2SingleChoice<>("interestRecalculationPreClosureInterestCalculationRuleField", new PropertyModel<>(this.itemPage, "interestRecalculationPreClosureInterestCalculationRuleValue"), this.interestRecalculationPreClosureInterestCalculationRuleProvider);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleField);
        this.interestRecalculationPreClosureInterestCalculationRuleIContainer.newFeedback("interestRecalculationPreClosureInterestCalculationRuleFeedback", this.interestRecalculationPreClosureInterestCalculationRuleField);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = this.row13.newUIBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer = this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.newUIContainer("interestRecalculationAdvancePaymentsAdjustmentTypeIContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField = new Select2SingleChoice<>("interestRecalculationAdvancePaymentsAdjustmentTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"), this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeField);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeIContainer.newFeedback("interestRecalculationAdvancePaymentsAdjustmentTypeFeedback", this.interestRecalculationAdvancePaymentsAdjustmentTypeField);

        this.row14 = UIRow.newUIRow("row14", this.form);

        this.interestRecalculationCompoundingOnBlock = this.row14.newUIBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        this.interestRecalculationCompoundingOnIContainer = this.interestRecalculationCompoundingOnBlock.newUIContainer("interestRecalculationCompoundingOnIContainer");
        this.interestRecalculationCompoundingOnField = new Select2SingleChoice<>("interestRecalculationCompoundingOnField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue"), this.interestRecalculationCompoundingOnProvider);
        this.interestRecalculationCompoundingOnIContainer.add(this.interestRecalculationCompoundingOnField);
        this.interestRecalculationCompoundingOnIContainer.newFeedback("interestRecalculationCompoundingOnFeedback", this.interestRecalculationCompoundingOnField);

        this.row14Block1 = this.row14.newUIBlock("row14Block1", Size.Eight_8);

        this.row15 = UIRow.newUIRow("row15", this.form);

        this.interestRecalculationCompoundingBlock = this.row15.newUIBlock("interestRecalculationCompoundingBlock", Size.Four_4);
        this.interestRecalculationCompoundingIContainer = this.interestRecalculationCompoundingBlock.newUIContainer("interestRecalculationCompoundingIContainer");
        this.interestRecalculationCompoundingField = new Select2SingleChoice<>("interestRecalculationCompoundingField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue"), this.interestRecalculationCompoundingProvider);
        this.interestRecalculationCompoundingIContainer.add(this.interestRecalculationCompoundingField);
        this.interestRecalculationCompoundingIContainer.newFeedback("interestRecalculationCompoundingFeedback", this.interestRecalculationCompoundingField);

        this.interestRecalculationCompoundingTypeBlock = this.row15.newUIBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
        this.interestRecalculationCompoundingTypeIContainer = this.interestRecalculationCompoundingTypeBlock.newUIContainer("interestRecalculationCompoundingTypeIContainer");
        this.interestRecalculationCompoundingTypeField = new Select2SingleChoice<>("interestRecalculationCompoundingTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue"), this.interestRecalculationCompoundingTypeProvider);
        this.interestRecalculationCompoundingTypeIContainer.add(this.interestRecalculationCompoundingTypeField);
        this.interestRecalculationCompoundingTypeIContainer.newFeedback("interestRecalculationCompoundingTypeFeedback", this.interestRecalculationCompoundingTypeField);

        this.interestRecalculationCompoundingDayBlock = this.row15.newUIBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);

        this.interestRecalculationCompoundingDayIContainer = this.interestRecalculationCompoundingDayBlock.newUIContainer("interestRecalculationCompoundingDayIContainer");
        this.interestRecalculationCompoundingDayField = new Select2SingleChoice<>("interestRecalculationCompoundingDayField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingDayValue"), this.interestRecalculationCompoundingDayProvider);
        this.interestRecalculationCompoundingDayIContainer.add(this.interestRecalculationCompoundingDayField);
        this.interestRecalculationCompoundingDayIContainer.newFeedback("interestRecalculationCompoundingDayFeedback", this.interestRecalculationCompoundingDayField);

        this.interestRecalculationCompoundingOnDayIContainer = this.interestRecalculationCompoundingDayBlock.newUIContainer("interestRecalculationCompoundingOnDayIContainer");
        this.interestRecalculationCompoundingOnDayField = new Select2SingleChoice<>("interestRecalculationCompoundingOnDayField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnDayValue"), this.interestRecalculationCompoundingOnDayProvider);
        this.interestRecalculationCompoundingOnDayIContainer.add(this.interestRecalculationCompoundingOnDayField);
        this.interestRecalculationCompoundingOnDayIContainer.newFeedback("interestRecalculationCompoundingOnDayFeedback", this.interestRecalculationCompoundingOnDayField);

        this.row16 = UIRow.newUIRow("row16", this.form);

        this.interestRecalculationCompoundingIntervalBlock = this.row16.newUIBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
        this.interestRecalculationCompoundingIntervalIContainer = this.interestRecalculationCompoundingIntervalBlock.newUIContainer("interestRecalculationCompoundingIntervalIContainer");
        this.interestRecalculationCompoundingIntervalField = new TextField<>("interestRecalculationCompoundingIntervalField", new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalIContainer.add(this.interestRecalculationCompoundingIntervalField);
        this.interestRecalculationCompoundingIntervalIContainer.newFeedback("interestRecalculationCompoundingIntervalFeedback", this.interestRecalculationCompoundingIntervalField);

        this.row16Block1 = this.row16.newUIBlock("row16Block1", Size.Eight_8);

        this.row17 = UIRow.newUIRow("row17", this.form);

        this.interestRecalculationRecalculateBlock = this.row17.newUIBlock("interestRecalculationRecalculateBlock", Size.Four_4);
        this.interestRecalculationRecalculateIContainer = this.interestRecalculationRecalculateBlock.newUIContainer("interestRecalculationRecalculateIContainer");
        this.interestRecalculationRecalculateField = new Select2SingleChoice<>("interestRecalculationRecalculateField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue"), this.interestRecalculationRecalculateProvider);
        this.interestRecalculationRecalculateIContainer.add(this.interestRecalculationRecalculateField);
        this.interestRecalculationRecalculateIContainer.newFeedback("interestRecalculationRecalculateFeedback", this.interestRecalculationRecalculateField);

        this.interestRecalculationRecalculateTypeBlock = this.row17.newUIBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
        this.interestRecalculationRecalculateTypeIContainer = this.interestRecalculationRecalculateTypeBlock.newUIContainer("interestRecalculationRecalculateTypeIContainer");
        this.interestRecalculationRecalculateTypeField = new Select2SingleChoice<>("interestRecalculationRecalculateTypeField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue"), this.interestRecalculationRecalculateTypeProvider);
        this.interestRecalculationRecalculateTypeIContainer.add(this.interestRecalculationRecalculateTypeField);
        this.interestRecalculationRecalculateTypeIContainer.newFeedback("interestRecalculationRecalculateTypeFeedback", this.interestRecalculationRecalculateTypeField);

        this.interestRecalculationRecalculateDayBlock = this.row17.newUIBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);

        this.interestRecalculationRecalculateDayIContainer = this.interestRecalculationRecalculateDayBlock.newUIContainer("interestRecalculationRecalculateDayIContainer");
        this.interestRecalculationRecalculateDayField = new Select2SingleChoice<>("interestRecalculationRecalculateDayField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateDayValue"), this.interestRecalculationRecalculateDayProvider);
        this.interestRecalculationRecalculateDayIContainer.add(this.interestRecalculationRecalculateDayField);
        this.interestRecalculationRecalculateDayIContainer.newFeedback("interestRecalculationRecalculateDayFeedback", this.interestRecalculationRecalculateDayField);

        this.interestRecalculationRecalculateOnDayIContainer = this.interestRecalculationRecalculateDayBlock.newUIContainer("interestRecalculationRecalculateOnDayIContainer");
        this.interestRecalculationRecalculateOnDayField = new Select2SingleChoice<>("interestRecalculationRecalculateOnDayField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateOnDayValue"), this.interestRecalculationRecalculateOnDayProvider);
        this.interestRecalculationRecalculateOnDayIContainer.add(this.interestRecalculationRecalculateOnDayField);
        this.interestRecalculationRecalculateOnDayIContainer.newFeedback("interestRecalculationRecalculateOnDayFeedback", this.interestRecalculationRecalculateOnDayField);

        this.row18 = UIRow.newUIRow("row18", this.form);

        this.interestRecalculationRecalculateIntervalBlock = this.row18.newUIBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
        this.interestRecalculationRecalculateIntervalIContainer = this.interestRecalculationRecalculateIntervalBlock.newUIContainer("interestRecalculationRecalculateIntervalIContainer");
        this.interestRecalculationRecalculateIntervalField = new TextField<>("interestRecalculationRecalculateIntervalField", new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalIContainer.add(this.interestRecalculationRecalculateIntervalField);
        this.interestRecalculationRecalculateIntervalIContainer.newFeedback("interestRecalculationRecalculateIntervalFeedback", this.interestRecalculationRecalculateIntervalField);

        this.row18Block1 = this.row18.newUIBlock("row18Block1", Size.Eight_8);

        this.row19 = UIRow.newUIRow("row19", this.form);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = this.row19.newUIBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer = this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.newUIContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField = new CheckBox("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", new PropertyModel<>(this.itemPage, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleIContainer.newFeedback("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleFeedback", this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField);

        this.row20 = UIRow.newUIRow("row20", this.form);

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock = this.row20.newUIBlock("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock", Size.Twelve_12);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock.newUIContainer("guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer");
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField = new CheckBox("guaranteeRequirementPlaceGuaranteeFundsOnHoldField", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.add(this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldIContainer.newFeedback("guaranteeRequirementPlaceGuaranteeFundsOnHoldFeedback", this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField);

        this.row21 = UIRow.newUIRow("row21", this.form);

        this.guaranteeRequirementMandatoryGuaranteeBlock = this.row21.newUIBlock("guaranteeRequirementMandatoryGuaranteeBlock", Size.Four_4);
        this.guaranteeRequirementMandatoryGuaranteeIContainer = this.guaranteeRequirementMandatoryGuaranteeBlock.newUIContainer("guaranteeRequirementMandatoryGuaranteeIContainer");
        this.guaranteeRequirementMandatoryGuaranteeField = new TextField<>("guaranteeRequirementMandatoryGuaranteeField", this.guaranteeRequirementMandatoryGuaranteeValue);
        this.guaranteeRequirementMandatoryGuaranteeIContainer.add(this.guaranteeRequirementMandatoryGuaranteeField);
        this.guaranteeRequirementMandatoryGuaranteeIContainer.newFeedback("guaranteeRequirementMandatoryGuaranteeFeedback", this.guaranteeRequirementMandatoryGuaranteeField);

        this.guaranteeRequirementMinimumGuaranteeBlock = this.row21.newUIBlock("guaranteeRequirementMinimumGuaranteeBlock", Size.Four_4);
        this.guaranteeRequirementMinimumGuaranteeIContainer = this.guaranteeRequirementMinimumGuaranteeBlock.newUIContainer("guaranteeRequirementMinimumGuaranteeIContainer");
        this.guaranteeRequirementMinimumGuaranteeField = new TextField<>("guaranteeRequirementMinimumGuaranteeField", this.guaranteeRequirementMinimumGuaranteeValue);
        this.guaranteeRequirementMinimumGuaranteeIContainer.add(this.guaranteeRequirementMinimumGuaranteeField);
        this.guaranteeRequirementMinimumGuaranteeIContainer.newFeedback("guaranteeRequirementMinimumGuaranteeFeedback", this.guaranteeRequirementMinimumGuaranteeField);

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock = this.row21.newUIBlock("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock", Size.Four_4);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer = this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.newUIContainer("guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField = new TextField<>("guaranteeRequirementMinimumGuaranteeFromGuarantorField", this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.add(this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorIContainer.newFeedback("guaranteeRequirementMinimumGuaranteeFromGuarantorFeedback", this.guaranteeRequirementMinimumGuaranteeFromGuarantorField);

        this.row22 = UIRow.newUIRow("row22", this.form);

        this.loanTrancheDetailEnableMultipleDisbursalBlock = this.row22.newUIBlock("loanTrancheDetailEnableMultipleDisbursalBlock", Size.Four_4);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer = this.loanTrancheDetailEnableMultipleDisbursalBlock.newUIContainer("loanTrancheDetailEnableMultipleDisbursalIContainer");
        this.loanTrancheDetailEnableMultipleDisbursalField = new CheckBox("loanTrancheDetailEnableMultipleDisbursalField", this.loanTrancheDetailEnableMultipleDisbursalValue);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.add(this.loanTrancheDetailEnableMultipleDisbursalField);
        this.loanTrancheDetailEnableMultipleDisbursalIContainer.newFeedback("loanTrancheDetailEnableMultipleDisbursalFeedback", this.loanTrancheDetailEnableMultipleDisbursalField);

        this.loanTrancheDetailMaximumTrancheCountBlock = this.row22.newUIBlock("loanTrancheDetailMaximumTrancheCountBlock", Size.Four_4);
        this.loanTrancheDetailMaximumTrancheCountIContainer = this.loanTrancheDetailMaximumTrancheCountBlock.newUIContainer("loanTrancheDetailMaximumTrancheCountIContainer");
        this.loanTrancheDetailMaximumTrancheCountField = new TextField<>("loanTrancheDetailMaximumTrancheCountField", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumTrancheCountValue"));
        this.loanTrancheDetailMaximumTrancheCountIContainer.add(this.loanTrancheDetailMaximumTrancheCountField);
        this.loanTrancheDetailMaximumTrancheCountIContainer.newFeedback("loanTrancheDetailMaximumTrancheCountFeedback", this.loanTrancheDetailMaximumTrancheCountField);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock = this.row22.newUIBlock("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock", Size.Four_4);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer = this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.newUIContainer("loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer");
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField = new TextField<>("loanTrancheDetailMaximumAllowedOutstandingBalanceField", new PropertyModel<>(this.itemPage, "loanTrancheDetailMaximumAllowedOutstandingBalanceValue"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.newFeedback("loanTrancheDetailMaximumAllowedOutstandingBalanceFeedback", this.loanTrancheDetailMaximumAllowedOutstandingBalanceField);

        this.row23 = UIRow.newUIRow("row23", this.form);

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock = this.row23.newUIBlock("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock", Size.Twelve_12);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer = this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock.newUIContainer("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer");
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField = new CheckBox("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue"));
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.add(this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountIContainer.newFeedback("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFeedback", this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);

        this.row24 = UIRow.newUIRow("row24", this.form);

        this.configurableAmortizationBlock = this.row24.newUIBlock("configurableAmortizationBlock", Size.Six_6);
        this.configurableAmortizationIContainer = this.configurableAmortizationBlock.newUIContainer("configurableAmortizationIContainer");
        this.configurableAmortizationField = new CheckBox("configurableAmortizationField", new PropertyModel<>(this.itemPage, "configurableAmortizationValue"));
        this.configurableAmortizationIContainer.add(this.configurableAmortizationField);
        this.configurableAmortizationIContainer.newFeedback("configurableAmortizationFeedback", this.configurableAmortizationField);

        this.configurableInterestMethodBlock = this.row24.newUIBlock("configurableInterestMethodBlock", Size.Six_6);
        this.configurableInterestMethodIContainer = this.configurableInterestMethodBlock.newUIContainer("configurableInterestMethodIContainer");
        this.configurableInterestMethodField = new CheckBox("configurableInterestMethodField", new PropertyModel<>(this.itemPage, "configurableInterestMethodValue"));
        this.configurableInterestMethodIContainer.add(this.configurableInterestMethodField);
        this.configurableInterestMethodIContainer.newFeedback("configurableInterestMethodFeedback", this.configurableInterestMethodField);

        this.row25 = UIRow.newUIRow("row25", this.form);

        this.configurableRepaymentStrategyBlock = this.row25.newUIBlock("configurableRepaymentStrategyBlock", Size.Six_6);
        this.configurableRepaymentStrategyIContainer = this.configurableRepaymentStrategyBlock.newUIContainer("configurableRepaymentStrategyIContainer");
        this.configurableRepaymentStrategyField = new CheckBox("configurableRepaymentStrategyField", new PropertyModel<>(this.itemPage, "configurableRepaymentStrategyValue"));
        this.configurableRepaymentStrategyIContainer.add(this.configurableRepaymentStrategyField);
        this.configurableRepaymentStrategyIContainer.newFeedback("configurableRepaymentStrategyFeedback", this.configurableRepaymentStrategyField);

        this.configurableInterestCalculationPeriodBlock = this.row25.newUIBlock("configurableInterestCalculationPeriodBlock", Size.Six_6);
        this.configurableInterestCalculationPeriodIContainer = this.configurableInterestCalculationPeriodBlock.newUIContainer("configurableInterestCalculationPeriodIContainer");
        this.configurableInterestCalculationPeriodField = new CheckBox("configurableInterestCalculationPeriodField", new PropertyModel<>(this.itemPage, "configurableInterestCalculationPeriodValue"));
        this.configurableInterestCalculationPeriodIContainer.add(this.configurableInterestCalculationPeriodField);
        this.configurableInterestCalculationPeriodIContainer.newFeedback("configurableInterestCalculationPeriodFeedback", this.configurableInterestCalculationPeriodField);

        this.row26 = UIRow.newUIRow("row26", this.form);

        this.configurableArrearsToleranceBlock = this.row26.newUIBlock("configurableArrearsToleranceBlock", Size.Six_6);
        this.configurableArrearsToleranceIContainer = this.configurableArrearsToleranceBlock.newUIContainer("configurableArrearsToleranceIContainer");
        this.configurableArrearsToleranceField = new CheckBox("configurableArrearsToleranceField", new PropertyModel<>(this.itemPage, "configurableArrearsToleranceValue"));
        this.configurableArrearsToleranceIContainer.add(this.configurableArrearsToleranceField);
        this.configurableArrearsToleranceIContainer.newFeedback("configurableArrearsToleranceFeedback", this.configurableArrearsToleranceField);

        this.configurableRepaidEveryBlock = this.row26.newUIBlock("configurableRepaidEveryBlock", Size.Six_6);
        this.configurableRepaidEveryIContainer = this.configurableRepaidEveryBlock.newUIContainer("configurableRepaidEveryIContainer");
        this.configurableRepaidEveryField = new CheckBox("configurableRepaidEveryField", new PropertyModel<>(this.itemPage, "configurableRepaidEveryValue"));
        this.configurableRepaidEveryIContainer.add(this.configurableRepaidEveryField);
        this.configurableRepaidEveryIContainer.newFeedback("configurableRepaidEveryFeedback", this.configurableRepaidEveryField);

        this.row27 = UIRow.newUIRow("row27", this.form);

        this.configurableMoratoriumBlock = this.row27.newUIBlock("configurableMoratoriumBlock", Size.Six_6);
        this.configurableMoratoriumIContainer = this.configurableMoratoriumBlock.newUIContainer("configurableMoratoriumIContainer");
        this.configurableMoratoriumField = new CheckBox("configurableMoratoriumField", new PropertyModel<>(this.itemPage, "configurableMoratoriumValue"));
        this.configurableMoratoriumIContainer.add(this.configurableMoratoriumField);
        this.configurableMoratoriumIContainer.newFeedback("configurableMoratoriumFeedback", this.configurableMoratoriumField);

        this.configurableOverdueBeforeMovingBlock = this.row27.newUIBlock("configurableOverdueBeforeMovingBlock", Size.Six_6);
        this.configurableOverdueBeforeMovingIContainer = this.configurableOverdueBeforeMovingBlock.newUIContainer("configurableOverdueBeforeMovingIContainer");
        this.configurableOverdueBeforeMovingField = new CheckBox("configurableOverdueBeforeMovingField", new PropertyModel<>(this.itemPage, "configurableOverdueBeforeMovingValue"));
        this.configurableOverdueBeforeMovingIContainer.add(this.configurableOverdueBeforeMovingField);
        this.configurableOverdueBeforeMovingIContainer.newFeedback("configurableOverdueBeforeMovingFeedback", this.configurableOverdueBeforeMovingField);
    }

    @Override
    protected void initData() {
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.settingAmortizationProvider = new AmortizationProvider();
        this.settingInterestMethodProvider = new InterestMethodProvider();
        this.settingInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
        this.settingRepaymentStrategyProvider = new RepaymentStrategyProvider();
        this.settingDayInYearProvider = new DayInYearProvider();
        this.settingDayInMonthProvider = new DayInMonthProvider();
        this.interestRecalculationPreClosureInterestCalculationRuleProvider = new ClosureInterestCalculationRuleProvider();
        this.interestRecalculationAdvancePaymentsAdjustmentTypeProvider = new AdvancePaymentsAdjustmentTypeProvider();
        this.interestRecalculationCompoundingOnProvider = new InterestRecalculationCompoundProvider();
        this.interestRecalculationCompoundingProvider = new FrequencyProvider();
        this.interestRecalculationCompoundingTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationCompoundingDayProvider = new FrequencyDayProvider();
        this.interestRecalculationCompoundingOnDayProvider = new OnDayProvider();
        this.interestRecalculationRecalculateProvider = new FrequencyProvider();
        this.interestRecalculationRecalculateTypeProvider = new FrequencyTypeProvider();
        this.interestRecalculationRecalculateDayProvider = new FrequencyDayProvider();
        this.interestRecalculationRecalculateOnDayProvider = new OnDayProvider();

        this.settingVariableInstallmentsAllowedValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsAllowedValue");
        this.settingVariableInstallmentsMinimumValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMinimumValue");
        this.settingVariableInstallmentsMaximumValue = new PropertyModel<>(this.itemPage, "settingVariableInstallmentsMaximumValue");

        this.loanTrancheDetailEnableMultipleDisbursalValue = new PropertyModel<>(this.itemPage, "loanTrancheDetailEnableMultipleDisbursalValue");

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementPlaceGuaranteeFundsOnHoldValue");
        this.guaranteeRequirementMandatoryGuaranteeValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementMandatoryGuaranteeValue");
        this.guaranteeRequirementMinimumGuaranteeValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeValue");
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue = new PropertyModel<>(this.itemPage, "guaranteeRequirementMinimumGuaranteeFromGuarantorValue");
    }

    @Override
    protected void configureMetaData() {

        this.settingAmortizationField.setLabel(Model.of("Amortization"));
        this.settingAmortizationField.add(new OnChangeAjaxBehavior());
        this.settingAmortizationField.setRequired(true);

        this.settingInterestMethodField.setLabel(Model.of("Interest Method"));
        this.settingInterestMethodField.add(new OnChangeAjaxBehavior());
        this.settingInterestMethodField.setRequired(true);

        this.settingEqualAmortizationField.add(new OnChangeAjaxBehavior());

        this.settingInterestCalculationPeriodField.setLabel(Model.of("Interest Calculation Period"));
        this.settingInterestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::settingInterestCalculationPeriodFieldUpdate));
        this.settingInterestCalculationPeriodField.setRequired(true);

        this.settingCalculateInterestForExactDaysInPartialPeriodField.add(new OnChangeAjaxBehavior());

        this.settingRepaymentStrategyField.setLabel(Model.of("Repayment Strategy"));
        this.settingRepaymentStrategyField.add(new OnChangeAjaxBehavior());
        this.settingRepaymentStrategyField.setRequired(true);

        this.settingMoratoriumPrincipleField.setLabel(Model.of("Moratorium Principle"));
        this.settingMoratoriumPrincipleField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumPrincipleField.add(RangeValidator.minimum(0l));
        this.settingMoratoriumPrincipleField.setRequired(true);

        this.settingMoratoriumInterestField.setLabel(Model.of("Moratorium Interest"));
        this.settingMoratoriumInterestField.add(new OnChangeAjaxBehavior());
        this.settingMoratoriumInterestField.add(RangeValidator.minimum(0l));
        this.settingMoratoriumInterestField.setRequired(true);

        this.settingInterestFreePeriodField.setLabel(Model.of("Interest Free Period"));
        this.settingInterestFreePeriodField.add(new OnChangeAjaxBehavior());
        this.settingInterestFreePeriodField.add(RangeValidator.minimum(0l));
        this.settingInterestFreePeriodField.setRequired(true);

        this.settingArrearsToleranceField.setLabel(Model.of("Arrears Tolerance"));
        this.settingArrearsToleranceField.add(new OnChangeAjaxBehavior());
        this.settingArrearsToleranceField.add(RangeValidator.minimum(0d));
        this.settingArrearsToleranceField.setRequired(true);

        this.settingDayInYearField.setLabel(Model.of("Day In Year"));
        this.settingDayInYearField.add(new OnChangeAjaxBehavior());
        this.settingDayInYearField.setRequired(true);

        this.settingDayInMonthField.setLabel(Model.of("Day In Month"));
        this.settingDayInMonthField.add(new OnChangeAjaxBehavior());
        this.settingDayInMonthField.setRequired(true);

        this.configurableOverdueBeforeMovingField.add(new OnChangeAjaxBehavior());

        this.configurableMoratoriumField.add(new OnChangeAjaxBehavior());

        this.configurableRepaidEveryField.add(new OnChangeAjaxBehavior());

        this.configurableArrearsToleranceField.add(new OnChangeAjaxBehavior());

        this.configurableInterestCalculationPeriodField.add(new OnChangeAjaxBehavior());

        this.configurableRepaymentStrategyField.add(new OnChangeAjaxBehavior());

        this.configurableInterestMethodField.add(new OnChangeAjaxBehavior());

        this.configurableAmortizationField.add(new OnChangeAjaxBehavior());

        this.configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField.add(new OnChangeAjaxBehavior(this::configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate));

        this.loanTrancheDetailEnableMultipleDisbursalField.add(new OnChangeAjaxBehavior(this::loanTrancheDetailEnableMultipleDisbursalFieldUpdate));

        this.loanTrancheDetailMaximumTrancheCountField.setLabel(Model.of("Maximum Tranche count"));
        this.loanTrancheDetailMaximumTrancheCountField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumTrancheCountField.add(RangeValidator.minimum(0l));
        this.loanTrancheDetailMaximumTrancheCountField.setRequired(true);

        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setLabel(Model.of("Maximum allowed outstanding balance"));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.add(new OnChangeAjaxBehavior());
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.add(RangeValidator.minimum(0d));
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceField.setRequired(true);

        this.guaranteeRequirementMandatoryGuaranteeField.setLabel(Model.of("Mandatory Guarantee: (%)"));
        this.guaranteeRequirementMandatoryGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMandatoryGuaranteeField.add(RangeValidator.minimum(0d));
        this.guaranteeRequirementMandatoryGuaranteeField.setRequired(true);

        this.guaranteeRequirementMinimumGuaranteeField.setLabel(Model.of("Minimum Guarantee from Own Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeField.setRequired(true);
        this.guaranteeRequirementMinimumGuaranteeField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeField.add(RangeValidator.minimum(0d));

        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setLabel(Model.of("Minimum Guarantee from Guarantor Funds: (%)"));
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.setRequired(true);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.add(new OnChangeAjaxBehavior());
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorField.add(RangeValidator.minimum(0d));

        this.guaranteeRequirementPlaceGuaranteeFundsOnHoldField.add(new OnChangeAjaxBehavior(this::guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate));

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationRecalculateIntervalField.setLabel(Model.of("Frequency Interval for recalculate"));
        this.interestRecalculationRecalculateIntervalField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationRecalculateOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationRecalculateOnDayField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationRecalculateDayField.setLabel(Model.of("Frequency day for recalculate"));
        this.interestRecalculationRecalculateDayField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationRecalculateTypeField.setLabel(Model.of("Frequency type for recalculate"));
        this.interestRecalculationRecalculateTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateTypeFieldUpdate));

        this.interestRecalculationRecalculateField.setLabel(Model.of("Frequency for recalculate Outstanding Principle"));
        this.interestRecalculationRecalculateField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateFieldUpdate));
        this.interestRecalculationRecalculateField.setRequired(true);

        this.interestRecalculationCompoundingIntervalField.setLabel(Model.of("Frequency Interval for compounding"));
        this.interestRecalculationCompoundingIntervalField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationCompoundingDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingDayField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationCompoundingOnDayField.setLabel(Model.of("Frequency day for compounding"));
        this.interestRecalculationCompoundingOnDayField.add(new OnChangeAjaxBehavior());

        this.interestRecalculationCompoundingField.setLabel(Model.of("Frequency for compounding"));
        this.interestRecalculationCompoundingField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingTypeFieldUpdate));

        this.interestRecalculationCompoundingTypeField.setLabel(Model.of("Frequency type for compounding"));
        this.interestRecalculationCompoundingTypeField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingFieldUpdate));

        this.interestRecalculationCompoundingOnField.setLabel(Model.of("Interest recalculation compounding on"));
        this.interestRecalculationCompoundingOnField.add(new OnChangeAjaxBehavior(this::interestRecalculationCompoundingOnFieldUpdate));
        this.interestRecalculationCompoundingOnField.setRequired(true);

        this.interestRecalculationPreClosureInterestCalculationRuleField.setLabel(Model.of("Pre-closure interest calculation rule"));
        this.interestRecalculationPreClosureInterestCalculationRuleField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationPreClosureInterestCalculationRuleField.setRequired(true);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setLabel(Model.of("Advance payments adjustment type"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.add(new OnChangeAjaxBehavior());
        this.interestRecalculationAdvancePaymentsAdjustmentTypeField.setRequired(true);

        this.interestRecalculationRecalculateInterestField.add(new OnChangeAjaxBehavior(this::interestRecalculationRecalculateInterestFieldUpdate));

        this.settingAllowedToBeUsedForProvidingTopupLoansField.add(new OnChangeAjaxBehavior());

        this.settingVariableInstallmentsAllowedField.add(new OnChangeAjaxBehavior(this::settingVariableInstallmentsAllowedFieldUpdate));

        this.settingVariableInstallmentsMinimumField.setLabel(Model.of("Variable Installments Minimum"));
        this.settingVariableInstallmentsMinimumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMinimumField.add(RangeValidator.minimum(0l));
        this.settingVariableInstallmentsMinimumField.setRequired(true);

        this.settingVariableInstallmentsMaximumField.setLabel(Model.of("Variable Installments Maximum"));
        this.settingVariableInstallmentsMaximumField.add(new OnChangeAjaxBehavior());
        this.settingVariableInstallmentsMaximumField.setRequired(true);

        this.settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField.add(new OnChangeAjaxBehavior());

        this.settingPrincipleThresholdForLastInstalmentField.setLabel(Model.of("Principle Threshold (%) for Last Installment"));
        this.settingPrincipleThresholdForLastInstalmentField.add(new OnChangeAjaxBehavior());
        this.settingPrincipleThresholdForLastInstalmentField.setRequired(true);

        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setLabel(Model.of("Number of days a loan may be overdue before moving into arrears"));
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.add(new OnChangeAjaxBehavior());
        this.settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField.setRequired(true);

        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setLabel(Model.of("Maximum number of days a loan may be overdue before becoming a NPA"));
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.add(new OnChangeAjaxBehavior());
        this.settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField.setRequired(true);

        this.settingAllowFixingOfTheInstallmentAmountField.add(new OnChangeAjaxBehavior());

        this.form.add(new LamdaFormValidator(this::settingVariableInstallmentsValidator, this.settingVariableInstallmentsMinimumField, this.settingVariableInstallmentsMaximumField));

        this.form.add(new LamdaFormValidator(this::guaranteeRequirementValidator, this.guaranteeRequirementMandatoryGuaranteeField, this.guaranteeRequirementMinimumGuaranteeField, this.guaranteeRequirementMinimumGuaranteeFromGuarantorField));

        baseInterestRecalculation();

        guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(null);

        loanTrancheDetailEnableMultipleDisbursalFieldUpdate(null);

        configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(null);

        settingVariableInstallmentsAllowedFieldUpdate(null);
    }

    protected void guaranteeRequirementValidator(Form<?> form) {
        if (this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject() != null && this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject()) {
            if (this.guaranteeRequirementMandatoryGuaranteeValue.getObject() != null && this.guaranteeRequirementMinimumGuaranteeValue.getObject() != null && this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue.getObject() != null) {
                if (this.guaranteeRequirementMandatoryGuaranteeValue.getObject() < this.guaranteeRequirementMinimumGuaranteeValue.getObject() + this.guaranteeRequirementMinimumGuaranteeFromGuarantorValue.getObject()) {
                    this.guaranteeRequirementMandatoryGuaranteeField.error(new ValidationError("must greater or equal to (guarantee from own funds + guarantee from guarantor funds)"));
                }
            }
        }
    }

    protected void settingVariableInstallmentsValidator(Form<?> form) {
        if (this.settingVariableInstallmentsMinimumValue.getObject() != null && this.settingVariableInstallmentsMaximumValue.getObject() != null) {
            if (this.settingVariableInstallmentsMinimumValue.getObject() > this.settingVariableInstallmentsMaximumValue.getObject()) {
                this.settingVariableInstallmentsMinimumField.error(new ValidationError("invalid"));
                this.settingVariableInstallmentsMaximumField.error(new ValidationError("invalid"));
            }
        } else if (this.settingVariableInstallmentsMinimumValue.getObject() == null && this.settingVariableInstallmentsMaximumValue.getObject() == null) {
        } else {
            if (this.settingVariableInstallmentsAllowedValue.getObject() != null && this.settingVariableInstallmentsAllowedValue.getObject()) {
                if (this.settingVariableInstallmentsMinimumValue.getObject() != null) {
                    this.settingVariableInstallmentsMinimumField.error(new ValidationError("invalid"));
                }
                if (this.settingVariableInstallmentsMaximumValue.getObject() != null) {
                    this.settingVariableInstallmentsMaximumField.error(new ValidationError("invalid"));
                }
            }
        }
    }

    protected boolean loanTrancheDetailEnableMultipleDisbursalFieldUpdate(AjaxRequestTarget target) {
        baseInterestRecalculation();

        if (target != null) {
            target.add(this.loanTrancheDetailMaximumTrancheCountBlock);
            target.add(this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock);
        }
        return false;
    }

    protected boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject() != null && this.guaranteeRequirementPlaceGuaranteeFundsOnHoldValue.getObject();
        this.guaranteeRequirementMandatoryGuaranteeBlock.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeBlock.setVisible(visible);
        this.guaranteeRequirementMinimumGuaranteeFromGuarantorBlock.setVisible(visible);
        if (target != null) {
            target.add(this.row21);
        }
        return false;
    }

    protected boolean settingInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {

        baseInterestRecalculation();

        if (target != null) {
            target.add(this.settingCalculateInterestForExactDaysInPartialPeriodBlock);
            target.add(this.row12);
            target.add(this.row13);
            target.add(this.row14);
            target.add(this.row15);
            target.add(this.row16);
            target.add(this.row17);
            target.add(this.row18);
            target.add(this.row19);
            target.add(this.row10);
            target.add(this.row22);
        }

        return false;
    }

    protected void baseInterestRecalculation() {
        PropertyModel<Option> settingInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "settingInterestCalculationPeriodValue");
        InterestCalculationPeriod interestCalculationPeriod = null;
        if (settingInterestCalculationPeriodValue.getObject() != null) {
            interestCalculationPeriod = InterestCalculationPeriod.valueOf(settingInterestCalculationPeriodValue.getObject().getId());
        }

        this.settingCalculateInterestForExactDaysInPartialPeriodIContainer.setVisible(interestCalculationPeriod == InterestCalculationPeriod.SameAsPayment);
        this.interestRecalculationRecalculateInterestBlock.setVisible(interestCalculationPeriod == InterestCalculationPeriod.Daily);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock.setVisible(false);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.setVisible(false);
        this.interestRecalculationCompoundingOnBlock.setVisible(false);
        this.interestRecalculationCompoundingBlock.setVisible(false);
        this.interestRecalculationCompoundingField.setRequired(false);
        this.interestRecalculationCompoundingTypeBlock.setVisible(false);
        this.interestRecalculationCompoundingDayBlock.setVisible(false);
        this.interestRecalculationCompoundingIntervalBlock.setVisible(false);
        this.interestRecalculationCompoundingIntervalField.setRequired(false);
        this.interestRecalculationRecalculateBlock.setVisible(false);
        this.interestRecalculationRecalculateTypeBlock.setVisible(false);
        this.interestRecalculationRecalculateDayBlock.setVisible(false);
        this.interestRecalculationRecalculateIntervalBlock.setVisible(false);
        this.interestRecalculationRecalculateIntervalField.setRequired(false);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.setVisible(false);
        this.interestRecalculationCompoundingDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingDayField.setRequired(false);
        this.interestRecalculationCompoundingTypeIContainer.setVisible(false);
        this.interestRecalculationCompoundingTypeField.setRequired(false);
        this.interestRecalculationCompoundingOnDayIContainer.setVisible(false);
        this.interestRecalculationCompoundingOnDayField.setRequired(false);
        this.interestRecalculationRecalculateTypeIContainer.setVisible(false);
        this.interestRecalculationRecalculateDayIContainer.setVisible(false);
        this.interestRecalculationRecalculateDayField.setRequired(false);
        this.interestRecalculationRecalculateOnDayIContainer.setVisible(false);
        this.interestRecalculationRecalculateOnDayField.setRequired(false);

        this.settingVariableInstallmentsAllowedBlock.setVisible(false);
        this.settingVariableInstallmentsMinimumBlock.setVisible(false);
        this.settingVariableInstallmentsMinimumIContainer.setVisible(false);
        this.settingVariableInstallmentsMaximumBlock.setVisible(false);
        this.settingVariableInstallmentsMaximumIContainer.setVisible(false);

        this.loanTrancheDetailEnableMultipleDisbursalBlock.setVisible(false);
        this.loanTrancheDetailMaximumTrancheCountBlock.setVisible(false);
        this.loanTrancheDetailMaximumTrancheCountIContainer.setVisible(false);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.setVisible(false);
        this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.setVisible(false);

        if (interestCalculationPeriod == InterestCalculationPeriod.Daily) {

            this.settingVariableInstallmentsAllowedBlock.setVisible(true);
            this.settingVariableInstallmentsMinimumBlock.setVisible(true);
            this.settingVariableInstallmentsMaximumBlock.setVisible(true);

            this.settingVariableInstallmentsMinimumIContainer.setVisible(this.settingVariableInstallmentsAllowedValue.getObject() != null && this.settingVariableInstallmentsAllowedValue.getObject());
            this.settingVariableInstallmentsMaximumIContainer.setVisible(this.settingVariableInstallmentsAllowedValue.getObject() != null && this.settingVariableInstallmentsAllowedValue.getObject());

            this.loanTrancheDetailEnableMultipleDisbursalBlock.setVisible(true);
            this.loanTrancheDetailMaximumTrancheCountBlock.setVisible(true);
            this.loanTrancheDetailMaximumAllowedOutstandingBalanceBlock.setVisible(true);

            this.loanTrancheDetailMaximumTrancheCountIContainer.setVisible(this.loanTrancheDetailEnableMultipleDisbursalValue.getObject() != null && this.loanTrancheDetailEnableMultipleDisbursalValue.getObject());
            this.loanTrancheDetailMaximumAllowedOutstandingBalanceIContainer.setVisible(this.loanTrancheDetailEnableMultipleDisbursalValue.getObject() != null && this.loanTrancheDetailEnableMultipleDisbursalValue.getObject());

            PropertyModel<Boolean> interestRecalculationRecalculateInterestValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateInterestValue");
            if (interestRecalculationRecalculateInterestValue.getObject() != null && interestRecalculationRecalculateInterestValue.getObject()) {
                this.interestRecalculationPreClosureInterestCalculationRuleBlock.setVisible(true);
                this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.setVisible(true);
                this.interestRecalculationCompoundingOnBlock.setVisible(true);
                this.interestRecalculationCompoundingDayBlock.setVisible(true);
                this.interestRecalculationCompoundingTypeBlock.setVisible(true);
                this.interestRecalculationRecalculateBlock.setVisible(true);
                this.interestRecalculationRecalculateTypeBlock.setVisible(true);
                this.interestRecalculationRecalculateDayBlock.setVisible(true);
                this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.setVisible(true);

                PropertyModel<Option> interestRecalculationCompoundingOnValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingOnValue");

                if (interestRecalculationCompoundingOnValue.getObject() != null && InterestRecalculationCompound.valueOf(interestRecalculationCompoundingOnValue.getObject().getId()) != InterestRecalculationCompound.None) {
                    this.interestRecalculationCompoundingBlock.setVisible(true);
                    this.interestRecalculationCompoundingField.setRequired(true);

                    PropertyModel<Option> interestRecalculationCompoundingValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingValue");

                    if (interestRecalculationCompoundingValue.getObject() != null) {
                        Frequency frequency = Frequency.valueOf(interestRecalculationCompoundingValue.getObject().getId());
                        if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                            this.interestRecalculationCompoundingIntervalBlock.setVisible(true);
                            this.interestRecalculationCompoundingIntervalField.setRequired(true);
                        }
                        if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                            this.interestRecalculationCompoundingDayIContainer.setVisible(true);
                            this.interestRecalculationCompoundingDayField.setRequired(true);
                        }
                        if (frequency == Frequency.Monthly) {
                            this.interestRecalculationCompoundingTypeIContainer.setVisible(true);
                            this.interestRecalculationCompoundingTypeField.setRequired(true);

                            PropertyModel<Option> interestRecalculationCompoundingTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationCompoundingTypeValue");

                            if (interestRecalculationCompoundingTypeValue.getObject() != null) {
                                FrequencyType type = FrequencyType.valueOf(interestRecalculationCompoundingTypeValue.getObject().getId());
                                this.interestRecalculationCompoundingDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                                this.interestRecalculationCompoundingDayField.setRequired(type != null && type != FrequencyType.OnDay);
                                this.interestRecalculationCompoundingOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                                this.interestRecalculationCompoundingOnDayField.setRequired(type != null && type == FrequencyType.OnDay);
                            }
                        }
                    }
                }

                PropertyModel<Option> interestRecalculationRecalculateValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateValue");
                PropertyModel<Option> interestRecalculationRecalculateTypeValue = new PropertyModel<>(this.itemPage, "interestRecalculationRecalculateTypeValue");
                if (interestRecalculationRecalculateValue.getObject() != null) {
                    Frequency frequency = Frequency.valueOf(interestRecalculationRecalculateValue.getObject().getId());
                    if (frequency == Frequency.Daily || frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                        this.interestRecalculationRecalculateIntervalBlock.setVisible(true);
                        this.interestRecalculationRecalculateIntervalField.setRequired(true);
                    }
                    if (frequency == Frequency.Weekly || frequency == Frequency.Monthly) {
                        this.interestRecalculationRecalculateDayIContainer.setVisible(true);
                        this.interestRecalculationRecalculateDayField.setRequired(true);
                    }
                    if (frequency == Frequency.Monthly) {
                        this.interestRecalculationRecalculateTypeIContainer.setVisible(true);

                        if (interestRecalculationRecalculateTypeValue.getObject() != null) {
                            FrequencyType type = FrequencyType.valueOf(interestRecalculationRecalculateTypeValue.getObject().getId());
                            this.interestRecalculationRecalculateDayIContainer.setVisible(type != null && type != FrequencyType.OnDay);
                            this.interestRecalculationRecalculateDayField.setRequired(type != null && type != FrequencyType.OnDay);
                            this.interestRecalculationRecalculateOnDayIContainer.setVisible(type != null && type == FrequencyType.OnDay);
                            this.interestRecalculationRecalculateOnDayField.setRequired(type != null && type == FrequencyType.OnDay);
                        }
                    }
                }
            }
        }

    }

    protected boolean interestRecalculationRecalculateInterestFieldUpdate(AjaxRequestTarget target) {

        baseInterestRecalculation();

        if (target != null) {
            target.add(this.row13);
            target.add(this.row14);
            target.add(this.row15);
            target.add(this.row16);
            target.add(this.row17);
            target.add(this.row18);
            target.add(this.row19);
        }

        return false;
    }

    protected boolean settingVariableInstallmentsAllowedFieldUpdate(AjaxRequestTarget target) {

        baseInterestRecalculation();

        if (target != null) {
            target.add(this.settingVariableInstallmentsMinimumBlock);
            target.add(this.settingVariableInstallmentsMaximumBlock);
        }
        return false;
    }

    protected boolean interestRecalculationRecalculateTypeFieldUpdate(AjaxRequestTarget target) {

        baseInterestRecalculation();

        if (target != null) {
            target.add(this.interestRecalculationRecalculateDayBlock);
        }
        return false;
    }

    protected boolean interestRecalculationRecalculateFieldUpdate(AjaxRequestTarget target) {

        baseInterestRecalculation();

        if (target != null) {
            target.add(this.interestRecalculationRecalculateTypeBlock);
            target.add(this.interestRecalculationRecalculateDayBlock);
            target.add(this.row18);
        }
        return false;
    }

    protected boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = new PropertyModel<>(this.itemPage, "configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue");
        boolean visible = configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject() != null && configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue.getObject();
        this.configurableAmortizationBlock.setVisible(visible);
        this.configurableInterestMethodBlock.setVisible(visible);
        this.configurableRepaymentStrategyBlock.setVisible(visible);
        this.configurableInterestCalculationPeriodBlock.setVisible(visible);
        this.configurableArrearsToleranceBlock.setVisible(visible);
        this.configurableRepaidEveryBlock.setVisible(visible);
        this.configurableMoratoriumBlock.setVisible(visible);
        this.configurableOverdueBeforeMovingBlock.setVisible(visible);
        if (target != null) {
            target.add(this.row24);
            target.add(this.row25);
            target.add(this.row26);
            target.add(this.row27);
        }
        return false;
    }

    protected boolean interestRecalculationCompoundingTypeFieldUpdate(AjaxRequestTarget target) {
        baseInterestRecalculation();

        if (target != null) {
            target.add(this.interestRecalculationCompoundingTypeBlock);
            target.add(this.interestRecalculationCompoundingDayBlock);
            target.add(this.row16);
        }
        return false;
    }

    protected boolean interestRecalculationCompoundingFieldUpdate(AjaxRequestTarget target) {
        baseInterestRecalculation();

        if (target != null) {
            target.add(this.interestRecalculationCompoundingDayBlock);
        }
        return false;
    }

    protected boolean interestRecalculationCompoundingOnFieldUpdate(AjaxRequestTarget target) {
        baseInterestRecalculation();

        if (target != null) {
            target.add(this.row15);
            target.add(this.row16);
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
        this.errorSetting.setObject(false);
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CHARGE);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
