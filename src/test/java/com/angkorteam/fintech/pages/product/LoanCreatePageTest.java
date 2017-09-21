package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.dto.loan.Amortization;
import com.angkorteam.fintech.dto.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.dto.loan.DayInMonth;
import com.angkorteam.fintech.dto.loan.DayInYear;
import com.angkorteam.fintech.dto.loan.Frequency;
import com.angkorteam.fintech.dto.loan.FrequencyDay;
import com.angkorteam.fintech.dto.loan.FrequencyType;
import com.angkorteam.fintech.dto.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.loan.InterestMethod;
import com.angkorteam.fintech.dto.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.dto.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.loan.RepaidType;
import com.angkorteam.fintech.dto.loan.RepaymentStrategy;
import com.angkorteam.fintech.dto.loan.WhenType;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class LoanCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMaximumLinkedFloatInterest() {
        this.wicket.login();

        String detailProductNameValue = "LOAN_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String detailFundValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_fund limit 1", String.class);
        Date detailStartDateValue = DateTime.now().toDate();
        Date detailCloseDateValue = DateTime.now().plusYears(1).toDate();
        Boolean detailIncludeInCustomerLoanCounterValue = true;

        String currencyCodeValue = "USD";
        int currencyInMultipleOfValue = 1;
        int currencyInstallmentInMultipleOfValue = 120;
        int currencyDecimalPlaceValue = 2;

        int termNominalInterestRateDefaultValue = 1;
        boolean termVaryBasedOnLoanCycleValue = true;
        double termPrincipalMinimumValue = 1.99;
        double termPrincipalDefaultValue = 500.99;
        double termPrincipalMaximumValue = 1000.99;
        int termNumberOfRepaymentMinimumValue = 1;
        int termNumberOfRepaymentDefaultValue = 120;
        int termNumberOfRepaymentMaximumValue = 240;
        boolean termLinkedToFloatingInterestRatesValue = true;
        String termFloatingInterestRateValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_floating_rates WHERE is_active = TRUE LIMIT 1", String.class);
        double termFloatingInterestDifferentialValue = 11.99;
        boolean termFloatingInterestAllowedValue = true;
        double termFloatingInterestMinimumValue = 1.99;
        double termFloatingInterestDefaultValue = 100.99;
        double termFloatingInterestMaximumValue = 500.99;
        int termRepaidEveryValue = 1;
        RepaidType termRepaidTypeValue = RepaidType.Month;
        int termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue = 12;

        Amortization settingAmortizationValue = Amortization.Installment;
        InterestMethod settingInterestMethodValue = InterestMethod.Flat;
        InterestCalculationPeriod settingInterestCalculationPeriodValue = InterestCalculationPeriod.SameAsPayment;
        boolean settingCalculateInterestForExactDaysInPartialPeriodValue = true;
        RepaymentStrategy settingRepaymentStrategyValue = RepaymentStrategy.Overdue;
        int settingMoratoriumPrincipalValue = 1;
        int settingMoratoriumInterestValue = 14;
        int settingInterestFreePeriodValue = 15;
        double settingArrearsToleranceValue = 16.99;
        DayInYear settingDayInYearValue = DayInYear.Actual;
        DayInMonth settingDayInMonthValue = DayInMonth.Actual;
        boolean settingAllowFixingOfTheInstallmentAmountValue = true;
        int settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue = 17;
        int settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue = 18;
        boolean settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue = true;
        double settingPrincipalThresholdForLastInstalmentValue = 19.99;
        boolean settingVariableInstallmentsAllowedValue = true;
        int settingVariableInstallmentsMinimumValue = 20;
        int settingVariableInstallmentsMaximumValue = 1000;
        boolean settingAllowedToBeUsedForProvidingTopupLoansValue = true;

        boolean interestRecalculationRecalculateInterestValue = true;
        ClosureInterestCalculationRule interestRecalculationPreClosureInterestCalculationRuleValue = ClosureInterestCalculationRule.PreClosureDate;
        AdvancePaymentsAdjustmentType interestRecalculationAdvancePaymentsAdjustmentTypeValue = AdvancePaymentsAdjustmentType.ReduceEMIAmount;
        InterestRecalculationCompound interestRecalculationCompoundingOnValue = InterestRecalculationCompound.FeeAndInterest;
        Frequency interestRecalculationCompoundingValue = Frequency.Monthly;
        FrequencyType interestRecalculationCompoundingTypeValue = FrequencyType.First;
        FrequencyDay interestRecalculationCompoundingDayValue = FrequencyDay.Monday;
        int interestRecalculationCompoundingIntervalValue = 21;
        Frequency interestRecalculationRecalculateValue = Frequency.Monthly;
        FrequencyType interestRecalculationRecalculateTypeValue = FrequencyType.First;
        FrequencyDay interestRecalculationRecalculateDayValue = FrequencyDay.Monday;
        int interestRecalculationRecalculateIntervalValue = 23;
        boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = true;

        boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = true;
        double guaranteeRequirementMandatoryGuaranteeValue = 24.99;
        double guaranteeRequirementMinimumGuaranteeValue = 25.99;
        double guaranteeRequirementMinimumGuaranteeFromGuarantorValue = 26.99;

        boolean loanTrancheDetailEnableMultipleDisbursalValue = true;
        int loanTrancheDetailMaximumTrancheCountValue = 27;
        double loanTrancheDetailMaximumAllowedOutstandingBalanceValue = 28.99;

        boolean configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue = true;
        boolean configurableAmortizationValue = true;
        boolean configurableInterestMethodValue = true;
        boolean configurableRepaymentStrategyValue = true;
        boolean configurableInterestCalculationPeriodValue = true;
        boolean configurableArrearsToleranceValue = true;
        boolean configurableRepaidEveryValue = true;
        boolean configurableMoratoriumValue = true;
        boolean configurableOverdueBeforeMovingValue = true;

        String cashFundSourceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashLoanPortfolioValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashTransferInSuspenseValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashIncomeFromInterestValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashIncomeFromFeeValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashIncomeFromPenaltiesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashIncomeFromRecoveryRepaymentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashLossesWrittenOffValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String cashOverPaymentLiabilityValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());

        int accountingValue = 1;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            CheckBox termVaryBasedOnLoanCycleField = this.wicket.getComponentFromLastRenderedPage("form:termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);
            this.wicket.executeBehavior(termVaryBasedOnLoanCycleField);
        }
        {
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            this.wicket.executeBehavior(currencyCodeField);
        }
        {
            AjaxLink<?> termPrincipalByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termPrincipalByLoanCycleBlock:termPrincipalByLoanCycleContainer:termPrincipalByLoanCycleAddLink", AjaxLink.class);
            form = this.wicket.newFormTester("form");
            this.wicket.executeAjaxLink(termPrincipalByLoanCycleAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("termPrincipalByLoanCyclePopup:content:form");
            popupForm.setValue("whenField", WhenType.GreaterThen);
            popupForm.setValue("loanCycleField", (int) 12);
            popupForm.setValue("minimumField", (double) 0.99);
            popupForm.setValue("defaultField", (double) 100.99);
            popupForm.setValue("maximumField", (double) 200.99);
            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow termPrincipalByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(termPrincipalByLoanCyclePopup);
        }

        {
            AjaxLink<?> termNumberOfRepaymentByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNumberOfRepaymentByLoanCycleBlock:termNumberOfRepaymentByLoanCycleContainer:termNumberOfRepaymentByLoanCycleAddLink", AjaxLink.class);
            form = this.wicket.newFormTester("form");
            this.wicket.executeAjaxLink(termNumberOfRepaymentByLoanCycleAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("termNumberOfRepaymentByLoanCyclePopup:content:form");
            popupForm.setValue("whenField", WhenType.GreaterThen);
            popupForm.setValue("loanCycleField", (int) 12);
            popupForm.setValue("minimumField", (double) 0.99);
            popupForm.setValue("defaultField", (double) 100.99);
            popupForm.setValue("maximumField", (double) 200.99);
            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow termNumberOfRepaymentByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(termNumberOfRepaymentByLoanCyclePopup);
        }
        {
            AjaxLink<?> termNominalInterestRateByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNominalInterestRateByLoanCycleBlock:termNominalInterestRateByLoanCycleContainer:termNominalInterestRateByLoanCycleAddLink", AjaxLink.class);
            form = this.wicket.newFormTester("form");
            this.wicket.executeAjaxLink(termNominalInterestRateByLoanCycleAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("termNominalInterestRateByLoanCyclePopup:content:form");
            popupForm.setValue("whenField", WhenType.GreaterThen);
            popupForm.setValue("loanCycleField", (int) 12);
            popupForm.setValue("minimumField", (double) 0.99);
            popupForm.setValue("defaultField", (double) 100.99);
            popupForm.setValue("maximumField", (double) 200.99);
            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow termNominalInterestRateByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(termNominalInterestRateByLoanCyclePopup);
        }
        {
            CheckBox termVaryBasedOnLoanCycleField = this.wicket.getComponentFromLastRenderedPage("form:termLinkedToFloatingInterestRatesBlock:termLinkedToFloatingInterestRatesContainer:termLinkedToFloatingInterestRatesField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termLinkedToFloatingInterestRatesBlock:termLinkedToFloatingInterestRatesContainer:termLinkedToFloatingInterestRatesField", termLinkedToFloatingInterestRatesValue);
            this.wicket.executeBehavior(termVaryBasedOnLoanCycleField);
        }
        {
            Select2SingleChoice<?> settingInterestCalculationPeriodField = this.wicket.getComponentFromLastRenderedPage("form:settingInterestCalculationPeriodBlock:settingInterestCalculationPeriodContainer:settingInterestCalculationPeriodField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("settingInterestCalculationPeriodBlock:settingInterestCalculationPeriodContainer:settingInterestCalculationPeriodField", settingInterestCalculationPeriodValue);
            this.wicket.executeBehavior(settingInterestCalculationPeriodField);
        }
        {
            CheckBox settingVariableInstallmentsAllowedField = this.wicket.getComponentFromLastRenderedPage("form:settingVariableInstallmentsAllowedBlock:settingVariableInstallmentsAllowedContainer:settingVariableInstallmentsAllowedField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("settingVariableInstallmentsAllowedBlock:settingVariableInstallmentsAllowedContainer:settingVariableInstallmentsAllowedField", settingVariableInstallmentsAllowedValue);
            this.wicket.executeBehavior(settingVariableInstallmentsAllowedField);
        }
        {
            CheckBox interestRecalculationRecalculateInterestField = this.wicket.getComponentFromLastRenderedPage("form:interestRecalculationRecalculateInterestBlock:interestRecalculationRecalculateInterestContainer:interestRecalculationRecalculateInterestField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("interestRecalculationRecalculateInterestBlock:interestRecalculationRecalculateInterestContainer:interestRecalculationRecalculateInterestField", interestRecalculationRecalculateInterestValue);
            this.wicket.executeBehavior(interestRecalculationRecalculateInterestField);
        }
        {
            CheckBox guaranteeRequirementPlaceGuaranteeFundsOnHoldField = this.wicket.getComponentFromLastRenderedPage("form:guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock:guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer:guaranteeRequirementPlaceGuaranteeFundsOnHoldField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock:guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer:guaranteeRequirementPlaceGuaranteeFundsOnHoldField", guaranteeRequirementPlaceGuaranteeFundsOnHoldValue);
            this.wicket.executeBehavior(guaranteeRequirementPlaceGuaranteeFundsOnHoldField);
        }
        {
            CheckBox loanTrancheDetailEnableMultipleDisbursalField = this.wicket.getComponentFromLastRenderedPage("form:loanTrancheDetailEnableMultipleDisbursalBlock:loanTrancheDetailEnableMultipleDisbursalContainer:loanTrancheDetailEnableMultipleDisbursalField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("loanTrancheDetailEnableMultipleDisbursalBlock:loanTrancheDetailEnableMultipleDisbursalContainer:loanTrancheDetailEnableMultipleDisbursalField", loanTrancheDetailEnableMultipleDisbursalValue);
            this.wicket.executeBehavior(loanTrancheDetailEnableMultipleDisbursalField);
        }
        {
            CheckBox configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField = this.wicket.getComponentFromLastRenderedPage("form:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue);
            this.wicket.executeBehavior(configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField);
        }
        {
            form = this.wicket.newFormTester("form");
            form.select("accountingField", accountingValue);
            RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
            this.wicket.executeBehavior(accountingField);
        }
        {
            Select2SingleChoice<?> interestRecalculationCompoundingOnField = this.wicket.getComponentFromLastRenderedPage("form:interestRecalculationCompoundingOnBlock:interestRecalculationCompoundingOnContainer:interestRecalculationCompoundingOnField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("interestRecalculationCompoundingOnBlock:interestRecalculationCompoundingOnContainer:interestRecalculationCompoundingOnField", interestRecalculationCompoundingOnValue);
            this.wicket.executeBehavior(interestRecalculationCompoundingOnField);
        }
        {
            Select2SingleChoice<?> interestRecalculationCompoundingField = this.wicket.getComponentFromLastRenderedPage("form:interestRecalculationCompoundingBlock:interestRecalculationCompoundingContainer:interestRecalculationCompoundingField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("interestRecalculationCompoundingBlock:interestRecalculationCompoundingContainer:interestRecalculationCompoundingField", interestRecalculationCompoundingValue);
            this.wicket.executeBehavior(interestRecalculationCompoundingField);
        }
        {
            Select2SingleChoice<?> interestRecalculationRecalculateField = this.wicket.getComponentFromLastRenderedPage("form:interestRecalculationRecalculateBlock:interestRecalculationRecalculateContainer:interestRecalculationRecalculateField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("interestRecalculationRecalculateBlock:interestRecalculationRecalculateContainer:interestRecalculationRecalculateField", interestRecalculationRecalculateValue);
            this.wicket.executeBehavior(interestRecalculationRecalculateField);
        }

        form = this.wicket.newFormTester("form");

        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);
        form.setValue("detailFundBlock:detailFundContainer:detailFundField", detailFundValue);
        form.setValue("detailStartDateBlock:detailStartDateContainer:detailStartDateField", DateFormatUtils.format(detailStartDateValue, "dd/MM/yyyy"));
        form.setValue("detailCloseDateBlock:detailCloseDateContainer:detailCloseDateField", DateFormatUtils.format(detailCloseDateValue, "dd/MM/yyyy"));
        form.setValue("detailIncludeInCustomerLoanCounterBlock:detailIncludeInCustomerLoanCounterContainer:detailIncludeInCustomerLoanCounterField", detailIncludeInCustomerLoanCounterValue);

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyInMultipleOfBlock:currencyInMultipleOfContainer:currencyInMultipleOfField", currencyInMultipleOfValue);
        form.setValue("currencyInstallmentInMultipleOfBlock:currencyInstallmentInMultipleOfContainer:currencyInstallmentInMultipleOfField", currencyInstallmentInMultipleOfValue);

        form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);
        form.setValue("termPrincipalMinimumBlock:termPrincipalMinimumContainer:termPrincipalMinimumField", termPrincipalMinimumValue);
        form.setValue("termPrincipalDefaultBlock:termPrincipalDefaultContainer:termPrincipalDefaultField", termPrincipalDefaultValue);
        form.setValue("termPrincipalMaximumBlock:termPrincipalMaximumContainer:termPrincipalMaximumField", termPrincipalMaximumValue);
        form.setValue("termNumberOfRepaymentMinimumBlock:termNumberOfRepaymentMinimumContainer:termNumberOfRepaymentMinimumField", termNumberOfRepaymentMinimumValue);
        form.setValue("termNumberOfRepaymentDefaultBlock:termNumberOfRepaymentDefaultContainer:termNumberOfRepaymentDefaultField", termNumberOfRepaymentDefaultValue);
        form.setValue("termNumberOfRepaymentMaximumBlock:termNumberOfRepaymentMaximumContainer:termNumberOfRepaymentMaximumField", termNumberOfRepaymentMaximumValue);
        form.setValue("termLinkedToFloatingInterestRatesBlock:termLinkedToFloatingInterestRatesContainer:termLinkedToFloatingInterestRatesField", termLinkedToFloatingInterestRatesValue);
        form.setValue("termFloatingInterestRateBlock:termFloatingInterestRateContainer:termFloatingInterestRateField", termFloatingInterestRateValue);
        form.setValue("termFloatingInterestDifferentialBlock:termFloatingInterestDifferentialContainer:termFloatingInterestDifferentialField", termFloatingInterestDifferentialValue);
        form.setValue("termFloatingInterestAllowedBlock:termFloatingInterestAllowedContainer:termFloatingInterestAllowedField", termFloatingInterestAllowedValue);
        form.setValue("termFloatingInterestMinimumBlock:termFloatingInterestMinimumContainer:termFloatingInterestMinimumField", termFloatingInterestMinimumValue);
        form.setValue("termFloatingInterestDefaultBlock:termFloatingInterestDefaultContainer:termFloatingInterestDefaultField", termFloatingInterestDefaultValue);
        form.setValue("termFloatingInterestMaximumBlock:termFloatingInterestMaximumContainer:termFloatingInterestMaximumField", termFloatingInterestMaximumValue);
        form.setValue("termRepaidEveryBlock:termRepaidEveryContainer:termRepaidEveryField", termRepaidEveryValue);
        form.setValue("termRepaidTypeBlock:termRepaidTypeContainer:termRepaidTypeField", termRepaidTypeValue);
        form.setValue("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock:termMinimumDayBetweenDisbursalAndFirstRepaymentDateContainer:termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue);

        form.setValue("settingAmortizationBlock:settingAmortizationContainer:settingAmortizationField", settingAmortizationValue);
        form.setValue("settingInterestMethodBlock:settingInterestMethodContainer:settingInterestMethodField", settingInterestMethodValue);
        form.setValue("settingInterestCalculationPeriodBlock:settingInterestCalculationPeriodContainer:settingInterestCalculationPeriodField", settingInterestCalculationPeriodValue);
        form.setValue("settingCalculateInterestForExactDaysInPartialPeriodBlock:settingCalculateInterestForExactDaysInPartialPeriodContainer:settingCalculateInterestForExactDaysInPartialPeriodField", settingCalculateInterestForExactDaysInPartialPeriodValue);
        form.setValue("settingRepaymentStrategyBlock:settingRepaymentStrategyContainer:settingRepaymentStrategyField", settingRepaymentStrategyValue);
        form.setValue("settingMoratoriumPrincipalBlock:settingMoratoriumPrincipalContainer:settingMoratoriumPrincipalField", settingMoratoriumPrincipalValue);
        form.setValue("settingMoratoriumInterestBlock:settingMoratoriumInterestContainer:settingMoratoriumInterestField", settingMoratoriumInterestValue);
        form.setValue("settingInterestFreePeriodBlock:settingInterestFreePeriodContainer:settingInterestFreePeriodField", settingInterestFreePeriodValue);
        form.setValue("settingArrearsToleranceBlock:settingArrearsToleranceContainer:settingArrearsToleranceField", settingArrearsToleranceValue);
        form.setValue("settingDayInYearBlock:settingDayInYearContainer:settingDayInYearField", settingDayInYearValue);
        form.setValue("settingDayInMonthBlock:settingDayInMonthContainer:settingDayInMonthField", settingDayInMonthValue);
        form.setValue("settingAllowFixingOfTheInstallmentAmountBlock:settingAllowFixingOfTheInstallmentAmountContainer:settingAllowFixingOfTheInstallmentAmountField", settingAllowFixingOfTheInstallmentAmountValue);
        form.setValue("settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsBlock:settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsContainer:settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsField", settingNumberOfDaysLoanMayBeOverdueBeforeMovingIntoArrearsValue);
        form.setValue("settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaBlock:settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaContainer:settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaField", settingMaximumNumberOfDaysLoanMayBeOverdueBeforeBecomingNpaValue);
        form.setValue("settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedBlock:settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedContainer:settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedField", settingAccountMovesOutOfNpaOnlyAfterAllArrearsHaveBeenClearedValue);
        form.setValue("settingPrincipalThresholdForLastInstalmentBlock:settingPrincipalThresholdForLastInstalmentContainer:settingPrincipalThresholdForLastInstalmentField", settingPrincipalThresholdForLastInstalmentValue);
        form.setValue("settingVariableInstallmentsAllowedBlock:settingVariableInstallmentsAllowedContainer:settingVariableInstallmentsAllowedField", settingVariableInstallmentsAllowedValue);
        form.setValue("settingVariableInstallmentsMinimumBlock:settingVariableInstallmentsMinimumContainer:settingVariableInstallmentsMinimumField", settingVariableInstallmentsMinimumValue);
        form.setValue("settingVariableInstallmentsMaximumBlock:settingVariableInstallmentsMaximumContainer:settingVariableInstallmentsMaximumField", settingVariableInstallmentsMaximumValue);
        form.setValue("settingAllowedToBeUsedForProvidingTopupLoansBlock:settingAllowedToBeUsedForProvidingTopupLoansContainer:settingAllowedToBeUsedForProvidingTopupLoansField", settingAllowedToBeUsedForProvidingTopupLoansValue);

        form.setValue("interestRecalculationRecalculateInterestBlock:interestRecalculationRecalculateInterestContainer:interestRecalculationRecalculateInterestField", interestRecalculationRecalculateInterestValue);
        form.setValue("interestRecalculationPreClosureInterestCalculationRuleBlock:interestRecalculationPreClosureInterestCalculationRuleContainer:interestRecalculationPreClosureInterestCalculationRuleField", interestRecalculationPreClosureInterestCalculationRuleValue);
        form.setValue("interestRecalculationAdvancePaymentsAdjustmentTypeBlock:interestRecalculationAdvancePaymentsAdjustmentTypeContainer:interestRecalculationAdvancePaymentsAdjustmentTypeField", interestRecalculationAdvancePaymentsAdjustmentTypeValue);
        form.setValue("interestRecalculationCompoundingOnBlock:interestRecalculationCompoundingOnContainer:interestRecalculationCompoundingOnField", interestRecalculationCompoundingOnValue);
        form.setValue("interestRecalculationCompoundingBlock:interestRecalculationCompoundingContainer:interestRecalculationCompoundingField", interestRecalculationCompoundingValue);
        form.setValue("interestRecalculationCompoundingTypeBlock:interestRecalculationCompoundingTypeContainer:interestRecalculationCompoundingTypeField", interestRecalculationCompoundingTypeValue);
        form.setValue("interestRecalculationCompoundingDayBlock:interestRecalculationCompoundingDayContainer:interestRecalculationCompoundingDayField", interestRecalculationCompoundingDayValue);
        form.setValue("interestRecalculationCompoundingIntervalBlock:interestRecalculationCompoundingIntervalContainer:interestRecalculationCompoundingIntervalField", interestRecalculationCompoundingIntervalValue);
        form.setValue("interestRecalculationRecalculateBlock:interestRecalculationRecalculateContainer:interestRecalculationRecalculateField", interestRecalculationRecalculateValue);
        form.setValue("interestRecalculationRecalculateTypeBlock:interestRecalculationRecalculateTypeContainer:interestRecalculationRecalculateTypeField", interestRecalculationRecalculateTypeValue);
        form.setValue("interestRecalculationRecalculateDayBlock:interestRecalculationRecalculateDayContainer:interestRecalculationRecalculateDayField", interestRecalculationRecalculateDayValue);
        form.setValue("interestRecalculationRecalculateIntervalBlock:interestRecalculationRecalculateIntervalContainer:interestRecalculationRecalculateIntervalField", interestRecalculationRecalculateIntervalValue);
        form.setValue("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock:interestRecalculationArrearsRecognizationBasedOnOriginalScheduleContainer:interestRecalculationArrearsRecognizationBasedOnOriginalScheduleField", interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue);

        form.setValue("guaranteeRequirementPlaceGuaranteeFundsOnHoldBlock:guaranteeRequirementPlaceGuaranteeFundsOnHoldContainer:guaranteeRequirementPlaceGuaranteeFundsOnHoldField", guaranteeRequirementPlaceGuaranteeFundsOnHoldValue);
        form.setValue("guaranteeRequirementMandatoryGuaranteeBlock:guaranteeRequirementMandatoryGuaranteeContainer:guaranteeRequirementMandatoryGuaranteeField", guaranteeRequirementMandatoryGuaranteeValue);
        form.setValue("guaranteeRequirementMinimumGuaranteeBlock:guaranteeRequirementMinimumGuaranteeContainer:guaranteeRequirementMinimumGuaranteeField", guaranteeRequirementMinimumGuaranteeValue);
        form.setValue("guaranteeRequirementMinimumGuaranteeFromGuarantorBlock:guaranteeRequirementMinimumGuaranteeFromGuarantorContainer:guaranteeRequirementMinimumGuaranteeFromGuarantorField", guaranteeRequirementMinimumGuaranteeFromGuarantorValue);

        form.setValue("loanTrancheDetailEnableMultipleDisbursalBlock:loanTrancheDetailEnableMultipleDisbursalContainer:loanTrancheDetailEnableMultipleDisbursalField", loanTrancheDetailEnableMultipleDisbursalValue);
        form.setValue("loanTrancheDetailMaximumTrancheCountBlock:loanTrancheDetailMaximumTrancheCountContainer:loanTrancheDetailMaximumTrancheCountField", loanTrancheDetailMaximumTrancheCountValue);
        form.setValue("loanTrancheDetailMaximumAllowedOutstandingBalanceBlock:loanTrancheDetailMaximumAllowedOutstandingBalanceContainer:loanTrancheDetailMaximumAllowedOutstandingBalanceField", loanTrancheDetailMaximumAllowedOutstandingBalanceValue);

        form.setValue("configurableAllowOverridingSelectTermsAndSettingsInLoanAccountBlock:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountContainer:configurableAllowOverridingSelectTermsAndSettingsInLoanAccountField", configurableAllowOverridingSelectTermsAndSettingsInLoanAccountValue);
        form.setValue("configurableAmortizationBlock:configurableAmortizationContainer:configurableAmortizationField", configurableAmortizationValue);
        form.setValue("configurableInterestMethodBlock:configurableInterestMethodContainer:configurableInterestMethodField", configurableInterestMethodValue);
        form.setValue("configurableRepaymentStrategyBlock:configurableRepaymentStrategyContainer:configurableRepaymentStrategyField", configurableRepaymentStrategyValue);
        form.setValue("configurableInterestCalculationPeriodBlock:configurableInterestCalculationPeriodContainer:configurableInterestCalculationPeriodField", configurableInterestCalculationPeriodValue);
        form.setValue("configurableArrearsToleranceBlock:configurableArrearsToleranceContainer:configurableArrearsToleranceField", configurableArrearsToleranceValue);
        form.setValue("configurableRepaidEveryBlock:configurableRepaidEveryContainer:configurableRepaidEveryField", configurableRepaidEveryValue);
        form.setValue("configurableMoratoriumBlock:configurableMoratoriumContainer:configurableMoratoriumField", configurableMoratoriumValue);
        form.setValue("configurableOverdueBeforeMovingBlock:configurableOverdueBeforeMovingContainer:configurableOverdueBeforeMovingField", configurableOverdueBeforeMovingValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.setValue("cashBlock:cashContainer:cashFundSourceField", cashFundSourceValue);
        form.setValue("cashBlock:cashContainer:cashLoanPortfolioField", cashLoanPortfolioValue);
        form.setValue("cashBlock:cashContainer:cashTransferInSuspenseField", cashTransferInSuspenseValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromInterestField", cashIncomeFromInterestValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeeField", cashIncomeFromFeeValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromPenaltiesField", cashIncomeFromPenaltiesValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromRecoveryRepaymentField", cashIncomeFromRecoveryRepaymentValue);
        form.setValue("cashBlock:cashContainer:cashLossesWrittenOffField", cashLossesWrittenOffValue);
        form.setValue("cashBlock:cashContainer:cashOverPaymentLiabilityField", cashOverPaymentLiabilityValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_product_loan where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_product_loan name = '" + detailProductNameValue + "'", actual);
    }

    @Ignore
    public void dataEntryMaximumNormalInterest() {

        int accountingValue = 2;
        String periodicFundSourceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicLoanPortfolioValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicInterestReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicFeesReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicPenaltiesReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicTransferInSuspenseValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String periodicIncomeFromInterestValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String periodicIncomeFromFeeValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String periodicIncomeFromPenaltiesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String periodicIncomeFromRecoveryRepaymentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String periodicLossesWrittenOffValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String periodicOverPaymentLiabilityValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        // Accounting

        form.select("accountingField", accountingValue);

        form.setValue("periodicBlock:periodicContainer:periodicFundSourceField", periodicFundSourceValue);
        form.setValue("periodicBlock:periodicContainer:periodicLoanPortfolioField", periodicLoanPortfolioValue);
        form.setValue("periodicBlock:periodicContainer:periodicInterestReceivableField", periodicInterestReceivableValue);
        form.setValue("periodicBlock:periodicContainer:periodicFeesReceivableField", periodicFeesReceivableValue);
        form.setValue("periodicBlock:periodicContainer:periodicPenaltiesReceivableField", periodicPenaltiesReceivableValue);
        form.setValue("periodicBlock:periodicContainer:periodicTransferInSuspenseField", periodicTransferInSuspenseValue);
        form.setValue("periodicBlock:periodicContainer:periodicIncomeFromInterestField", periodicIncomeFromInterestValue);
        form.setValue("periodicBlock:periodicContainer:periodicIncomeFromFeeField", periodicIncomeFromFeeValue);
        form.setValue("periodicBlock:periodicContainer:periodicIncomeFromPenaltiesField", periodicIncomeFromPenaltiesValue);
        form.setValue("periodicBlock:periodicContainer:periodicIncomeFromRecoveryRepaymentField", periodicIncomeFromRecoveryRepaymentValue);
        form.setValue("periodicBlock:periodicContainer:periodicLossesWrittenOffField", periodicLossesWrittenOffValue);
        form.setValue("periodicBlock:periodicContainer:periodicOverPaymentLiabilityField", periodicOverPaymentLiabilityValue);
    }

    @Ignore
    public void dataEntryMaximumCash() {

        int accountingValue = 3;

        String upfrontFundSourceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontLoanPortfolioValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontInterestReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontFeesReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontPenaltiesReceivableValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontTransferInSuspenseValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String upfrontIncomeFromInterestValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String upfrontIncomeFromFeeValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String upfrontIncomeFromPenaltiesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String upfrontIncomeFromRecoveryRepaymentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String upfrontLossesWrittenOffValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String upfrontOverPaymentLiabilityValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        // Accounting

        form.select("accountingField", accountingValue);

        form.setValue("upfrontBlock:upfrontContainer:upfrontFundSourceField", upfrontFundSourceValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontLoanPortfolioField", upfrontLoanPortfolioValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontInterestReceivableField", upfrontInterestReceivableValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontFeesReceivableField", upfrontFeesReceivableValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontPenaltiesReceivableField", upfrontPenaltiesReceivableValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontTransferInSuspenseField", upfrontTransferInSuspenseValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontIncomeFromInterestField", upfrontIncomeFromInterestValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontIncomeFromFeeField", upfrontIncomeFromFeeValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontIncomeFromPenaltiesField", upfrontIncomeFromPenaltiesValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontIncomeFromRecoveryRepaymentField", upfrontIncomeFromRecoveryRepaymentValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontLossesWrittenOffField", upfrontLossesWrittenOffValue);
        form.setValue("upfrontBlock:upfrontContainer:upfrontOverPaymentLiabilityField", upfrontOverPaymentLiabilityValue);
    }

    @Ignore
    public void dataEntryMaximumPeriodic() {

    }

    @Ignore
    public void dataEntryMaximumUpfront() {

    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "LOAN_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String currencyCodeValue = "USD";
        String detailFundValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_fund limit 1", String.class);
        int currencyDecimalPlaceValue = 2;
        int termRepaidEveryValue = 1;
        int termNumberOfRepaymentDefaultValue = 1;
        int termNominalInterestRateDefaultValue = 1;

        this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);
        form.setValue("detailFundBlock:detailFundContainer:detailFundField", detailFundValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        // Terms
        form.setValue("termNumberOfRepaymentDefaultBlock:termNumberOfRepaymentDefaultContainer:termNumberOfRepaymentDefaultField", termNumberOfRepaymentDefaultValue);
        form.setValue("termNominalInterestRateDefaultBlock:termNominalInterestRateDefaultContainer:termNominalInterestRateDefaultField", termNominalInterestRateDefaultValue);
        form.setValue("termNominalInterestRateTypeBlock:termNominalInterestRateTypeContainer:termNominalInterestRateTypeField", NominalInterestRateScheduleType.Month);
        form.setValue("termRepaidEveryBlock:termRepaidEveryContainer:termRepaidEveryField", termRepaidEveryValue);
        form.setValue("termRepaidTypeBlock:termRepaidTypeContainer:termRepaidTypeField", RepaidType.Month);

        // Settings
        form.setValue("settingAmortizationBlock:settingAmortizationContainer:settingAmortizationField", Amortization.Installment);
        form.setValue("settingInterestMethodBlock:settingInterestMethodContainer:settingInterestMethodField", InterestMethod.Flat);
        form.setValue("settingInterestCalculationPeriodBlock:settingInterestCalculationPeriodContainer:settingInterestCalculationPeriodField", InterestCalculationPeriod.Daily);
        form.setValue("settingRepaymentStrategyBlock:settingRepaymentStrategyContainer:settingRepaymentStrategyField", RepaymentStrategy.Interest);
        form.setValue("settingDayInYearBlock:settingDayInYearContainer:settingDayInYearField", DayInYear.Actual);
        form.setValue("settingDayInMonthBlock:settingDayInMonthContainer:settingDayInMonthField", DayInMonth.Actual);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_product_loan where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_product_loan name = '" + detailProductNameValue + "'", actual);

    }

}
