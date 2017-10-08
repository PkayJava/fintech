package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.dto.enums.loan.DayInMonth;
import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.dto.enums.loan.WhenType;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Maps;

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
        LockInType termRepaidTypeValue = LockInType.Week;
        int termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue = 12;

        Amortization settingAmortizationValue = Amortization.Installment;
        InterestMethod settingInterestMethodValue = InterestMethod.DecliningBalance;
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
        Frequency interestRecalculationCompoundingValue = Frequency.Weekly;
        // FrequencyType interestRecalculationCompoundingTypeValue =
        // FrequencyType.First;
        FrequencyDay interestRecalculationCompoundingDayValue = FrequencyDay.Monday;
        int interestRecalculationCompoundingIntervalValue = 1;
        Frequency interestRecalculationRecalculateValue = Frequency.Weekly;
        // FrequencyType interestRecalculationRecalculateTypeValue =
        // FrequencyType.First;
        FrequencyDay interestRecalculationRecalculateDayValue = FrequencyDay.Monday;
        int interestRecalculationRecalculateIntervalValue = 1;
        boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = true;

        boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = true;
        double guaranteeRequirementMandatoryGuaranteeValue = 100.99;
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

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 0);
        String overdueChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 1);

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
            AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(chargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
            popupForm.setValue("chargeField", chargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(chargePopup);
        }
        {
            AjaxLink<?> overdueChargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:overdueChargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(overdueChargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("overdueChargePopup:content:form");
            popupForm.setValue("overdueChargeField", overdueChargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow overdueChargePopup = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(overdueChargePopup);
        }
        {
            {
                AjaxLink<?> termPrincipalByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termPrincipalByLoanCycleBlock:termPrincipalByLoanCycleContainer:termPrincipalByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termPrincipalByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termPrincipalByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.Equals);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termPrincipalByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termPrincipalByLoanCyclePopup);
            }
            {
                AjaxLink<?> termPrincipalByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termPrincipalByLoanCycleBlock:termPrincipalByLoanCycleContainer:termPrincipalByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termPrincipalByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termPrincipalByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.GreaterThen);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termPrincipalByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termPrincipalByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termPrincipalByLoanCyclePopup);
            }
        }

        {
            {
                AjaxLink<?> termNumberOfRepaymentByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNumberOfRepaymentByLoanCycleBlock:termNumberOfRepaymentByLoanCycleContainer:termNumberOfRepaymentByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termNumberOfRepaymentByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termNumberOfRepaymentByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.Equals);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termNumberOfRepaymentByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termNumberOfRepaymentByLoanCyclePopup);
            }
            {
                AjaxLink<?> termNumberOfRepaymentByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNumberOfRepaymentByLoanCycleBlock:termNumberOfRepaymentByLoanCycleContainer:termNumberOfRepaymentByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termNumberOfRepaymentByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termNumberOfRepaymentByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.GreaterThen);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termNumberOfRepaymentByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNumberOfRepaymentByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termNumberOfRepaymentByLoanCyclePopup);
            }
        }
        {
            {
                AjaxLink<?> termNominalInterestRateByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNominalInterestRateByLoanCycleBlock:termNominalInterestRateByLoanCycleContainer:termNominalInterestRateByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termNominalInterestRateByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termNominalInterestRateByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.GreaterThen);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termNominalInterestRateByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termNominalInterestRateByLoanCyclePopup);
            }
            {
                AjaxLink<?> termNominalInterestRateByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termNominalInterestRateByLoanCycleBlock:termNominalInterestRateByLoanCycleContainer:termNominalInterestRateByLoanCycleAddLink", AjaxLink.class);
                form = this.wicket.newFormTester("form");
                this.wicket.executeAjaxLink(termNominalInterestRateByLoanCycleAddLink);

                JUnitFormTester popupForm = this.wicket.newFormTester("termNominalInterestRateByLoanCyclePopup:content:form");
                popupForm.setValue("whenField", WhenType.GreaterThen);
                popupForm.setValue("loanCycleField", (int) 1);
                popupForm.setValue("minimumField", (double) 0.99);
                popupForm.setValue("defaultField", (double) 100.99);
                popupForm.setValue("maximumField", (double) 200.99);
                AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup:content:form:okayButton", AjaxButton.class);
                this.wicket.executeAjaxButton(okayButton);

                ModalWindow termNominalInterestRateByLoanCyclePopup = this.wicket.getComponentFromLastRenderedPage("termNominalInterestRateByLoanCyclePopup", ModalWindow.class);
                this.wicket.executeModalWindowOnClose(termNominalInterestRateByLoanCyclePopup);
            }
        }
        {
            CheckBox termLinkedToFloatingInterestRatesField = this.wicket.getComponentFromLastRenderedPage("form:termLinkedToFloatingInterestRatesBlock:termLinkedToFloatingInterestRatesContainer:termLinkedToFloatingInterestRatesField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termLinkedToFloatingInterestRatesBlock:termLinkedToFloatingInterestRatesContainer:termLinkedToFloatingInterestRatesField", termLinkedToFloatingInterestRatesValue);
            this.wicket.executeBehavior(termLinkedToFloatingInterestRatesField);
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
        {
            AjaxLink<?> advancedAccountingRuleFundSourceAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFundSourceAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFundSourceAddLink);

            String paymentValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());

            JUnitFormTester popupForm = this.wicket.newFormTester("fundSourcePopup:content:form");
            popupForm.setValue("paymentField", paymentValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow fundSourcePopup = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(fundSourcePopup);
        }
        {
            AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("feeIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow feeIncomePopup = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(feeIncomePopup);
        }
        {
            AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("penaltyIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow penaltyIncomePopup = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(penaltyIncomePopup);
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
        // form.setValue("interestRecalculationCompoundingTypeBlock:interestRecalculationCompoundingTypeContainer:interestRecalculationCompoundingTypeField",
        // interestRecalculationCompoundingTypeValue);
        form.setValue("interestRecalculationCompoundingDayBlock:interestRecalculationCompoundingDayContainer:interestRecalculationCompoundingDayField", interestRecalculationCompoundingDayValue);
        form.setValue("interestRecalculationCompoundingIntervalBlock:interestRecalculationCompoundingIntervalContainer:interestRecalculationCompoundingIntervalField", interestRecalculationCompoundingIntervalValue);
        form.setValue("interestRecalculationRecalculateBlock:interestRecalculationRecalculateContainer:interestRecalculationRecalculateField", interestRecalculationRecalculateValue);
        // form.setValue("interestRecalculationRecalculateTypeBlock:interestRecalculationRecalculateTypeContainer:interestRecalculationRecalculateTypeField",
        // interestRecalculationRecalculateTypeValue);
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

    @Test
    public void dataEntryMaximumNormalInterest() {
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

        boolean termVaryBasedOnLoanCycleValue = false;
        double termPrincipalMinimumValue = 1.99;
        double termPrincipalDefaultValue = 500.99;
        double termPrincipalMaximumValue = 1000.99;
        int termNumberOfRepaymentMinimumValue = 1;
        int termNumberOfRepaymentDefaultValue = 120;
        int termNumberOfRepaymentMaximumValue = 240;
        boolean termLinkedToFloatingInterestRatesValue = false;
        double termNominalInterestRateMinimumValue = 10.99;
        double termNominalInterestRateDefaultValue = 30.99;
        double termNominalInterestRateMaximumValue = 50.99;
        NominalInterestRateScheduleType termNominalInterestRateTypeValue = NominalInterestRateScheduleType.Month;

        int termRepaidEveryValue = 1;
        LockInType termRepaidTypeValue = LockInType.Day;
        int termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue = 12;

        Amortization settingAmortizationValue = Amortization.Installment;
        InterestMethod settingInterestMethodValue = InterestMethod.DecliningBalance;
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
        Frequency interestRecalculationCompoundingValue = Frequency.Daily;
        // FrequencyType interestRecalculationCompoundingTypeValue =
        // FrequencyType.First;
        // FrequencyDay interestRecalculationCompoundingDayValue = FrequencyDay.Monday;
        int interestRecalculationCompoundingIntervalValue = 1;
        Frequency interestRecalculationRecalculateValue = Frequency.Daily;
        // FrequencyType interestRecalculationRecalculateTypeValue =
        // FrequencyType.First;
        // FrequencyDay interestRecalculationRecalculateDayValue = FrequencyDay.Monday;
        int interestRecalculationRecalculateIntervalValue = 1;
        boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = true;

        boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = true;
        double guaranteeRequirementMandatoryGuaranteeValue = 100.99;
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

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 0);
        String overdueChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 1);

        int accountingValue = 2;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            this.wicket.executeBehavior(currencyCodeField);
        }
        {
            AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(chargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
            popupForm.setValue("chargeField", chargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(chargePopup);
        }
        {
            AjaxLink<?> overdueChargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:overdueChargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(overdueChargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("overdueChargePopup:content:form");
            popupForm.setValue("overdueChargeField", overdueChargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow overdueChargePopup = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(overdueChargePopup);
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
        {
            AjaxLink<?> advancedAccountingRuleFundSourceAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFundSourceAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFundSourceAddLink);

            String paymentValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());

            JUnitFormTester popupForm = this.wicket.newFormTester("fundSourcePopup:content:form");
            popupForm.setValue("paymentField", paymentValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow fundSourcePopup = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(fundSourcePopup);
        }
        {
            AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("feeIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow feeIncomePopup = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(feeIncomePopup);
        }
        {
            AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("penaltyIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow penaltyIncomePopup = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(penaltyIncomePopup);
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
        form.setValue("termNominalInterestRateMinimumBlock:termNominalInterestRateMinimumContainer:termNominalInterestRateMinimumField", termNominalInterestRateMinimumValue);
        form.setValue("termNominalInterestRateDefaultBlock:termNominalInterestRateDefaultContainer:termNominalInterestRateDefaultField", termNominalInterestRateDefaultValue);
        form.setValue("termNominalInterestRateMaximumBlock:termNominalInterestRateMaximumContainer:termNominalInterestRateMaximumField", termNominalInterestRateMaximumValue);
        form.setValue("termNominalInterestRateTypeBlock:termNominalInterestRateTypeContainer:termNominalInterestRateTypeField", termNominalInterestRateTypeValue);
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
        // form.setValue("interestRecalculationCompoundingTypeBlock:interestRecalculationCompoundingTypeContainer:interestRecalculationCompoundingTypeField",
        // interestRecalculationCompoundingTypeValue);
        // form.setValue("interestRecalculationCompoundingDayBlock:interestRecalculationCompoundingDayContainer:interestRecalculationCompoundingDayField",
        // interestRecalculationCompoundingDayValue);
        form.setValue("interestRecalculationCompoundingIntervalBlock:interestRecalculationCompoundingIntervalContainer:interestRecalculationCompoundingIntervalField", interestRecalculationCompoundingIntervalValue);
        form.setValue("interestRecalculationRecalculateBlock:interestRecalculationRecalculateContainer:interestRecalculationRecalculateField", interestRecalculationRecalculateValue);
        // form.setValue("interestRecalculationRecalculateTypeBlock:interestRecalculationRecalculateTypeContainer:interestRecalculationRecalculateTypeField",
        // interestRecalculationRecalculateTypeValue);
        // form.setValue("interestRecalculationRecalculateDayBlock:interestRecalculationRecalculateDayContainer:interestRecalculationRecalculateDayField",
        // interestRecalculationRecalculateDayValue);
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

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_product_loan where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_product_loan name = '" + detailProductNameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximumNormalInterest1() {
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

        boolean termVaryBasedOnLoanCycleValue = false;
        double termPrincipalMinimumValue = 1.99;
        double termPrincipalDefaultValue = 500.99;
        double termPrincipalMaximumValue = 1000.99;
        int termNumberOfRepaymentMinimumValue = 1;
        int termNumberOfRepaymentDefaultValue = 120;
        int termNumberOfRepaymentMaximumValue = 240;
        boolean termLinkedToFloatingInterestRatesValue = false;
        double termNominalInterestRateMinimumValue = 10.99;
        double termNominalInterestRateDefaultValue = 30.99;
        double termNominalInterestRateMaximumValue = 50.99;
        NominalInterestRateScheduleType termNominalInterestRateTypeValue = NominalInterestRateScheduleType.Month;

        int termRepaidEveryValue = 1;
        LockInType termRepaidTypeValue = LockInType.Month;
        int termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue = 12;

        Amortization settingAmortizationValue = Amortization.Installment;
        InterestMethod settingInterestMethodValue = InterestMethod.DecliningBalance;
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
        int interestRecalculationCompoundingIntervalValue = 1;
        Frequency interestRecalculationRecalculateValue = Frequency.Monthly;
        FrequencyType interestRecalculationRecalculateTypeValue = FrequencyType.First;
        FrequencyDay interestRecalculationRecalculateDayValue = FrequencyDay.Monday;
        int interestRecalculationRecalculateIntervalValue = 1;
        boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = true;

        boolean guaranteeRequirementPlaceGuaranteeFundsOnHoldValue = true;
        double guaranteeRequirementMandatoryGuaranteeValue = 100.99;
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

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 0);
        String overdueChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 1);

        int accountingValue = 3;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            this.wicket.executeBehavior(currencyCodeField);
        }
        {
            AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(chargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
            popupForm.setValue("chargeField", chargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(chargePopup);
        }
        {
            AjaxLink<?> overdueChargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:overdueChargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(overdueChargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("overdueChargePopup:content:form");
            popupForm.setValue("overdueChargeField", overdueChargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow overdueChargePopup = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(overdueChargePopup);
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
        {
            AjaxLink<?> advancedAccountingRuleFundSourceAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFundSourceAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFundSourceAddLink);

            String paymentValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());

            JUnitFormTester popupForm = this.wicket.newFormTester("fundSourcePopup:content:form");
            popupForm.setValue("paymentField", paymentValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow fundSourcePopup = this.wicket.getComponentFromLastRenderedPage("fundSourcePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(fundSourcePopup);
        }
        {
            AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("feeIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow feeIncomePopup = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(feeIncomePopup);
        }
        {
            AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.Loan.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            JUnitFormTester popupForm = this.wicket.newFormTester("penaltyIncomePopup:content:form");
            popupForm.setValue("chargeField", tempChargeValue);
            popupForm.setValue("accountField", accountValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow penaltyIncomePopup = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(penaltyIncomePopup);
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
        form.setValue("termNominalInterestRateMinimumBlock:termNominalInterestRateMinimumContainer:termNominalInterestRateMinimumField", termNominalInterestRateMinimumValue);
        form.setValue("termNominalInterestRateDefaultBlock:termNominalInterestRateDefaultContainer:termNominalInterestRateDefaultField", termNominalInterestRateDefaultValue);
        form.setValue("termNominalInterestRateMaximumBlock:termNominalInterestRateMaximumContainer:termNominalInterestRateMaximumField", termNominalInterestRateMaximumValue);
        form.setValue("termNominalInterestRateTypeBlock:termNominalInterestRateTypeContainer:termNominalInterestRateTypeField", termNominalInterestRateTypeValue);
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

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_product_loan where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_product_loan name = '" + detailProductNameValue + "'", actual);
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
        double termNominalInterestRateDefaultValue = 1;

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
        form.setValue("termRepaidTypeBlock:termRepaidTypeContainer:termRepaidTypeField", LockInType.Month);

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

    @Test
    public void chargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 0);

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", chargeValue);
        page.chargeValue.add(item);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
        popupForm.setValue("chargeField", chargeValue);

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(chargePopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 1);

    }

    @Test
    public void overdueChargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String overdueChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Loan.getLiteral(), 1);

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", overdueChargeValue);
        page.overdueChargeValue.add(item);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        AjaxLink<?> overdueChargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:overdueChargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(overdueChargeAddLink);

        JUnitFormTester popupForm = this.wicket.newFormTester("overdueChargePopup:content:form");
        popupForm.setValue("overdueChargeField", overdueChargeValue);

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow overdueChargePopup = this.wicket.getComponentFromLastRenderedPage("overdueChargePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(overdueChargePopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have overdueChargeValue one item inside", page.overdueChargeValue.size(), 1);

    }

    @Test
    public void advancedAccountingRuleFundSourceActionClickTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.advancedAccountingRuleFundSourceValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFundSourceTable:body:rows:1:cells:3:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRuleFundSourceValue one item inside", page.advancedAccountingRuleFundSourceValue.size(), 0);
    }

    @Test
    public void advancedAccountingRulePenaltyIncomeActionClickTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.advancedAccountingRulePenaltyIncomeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeTable:body:rows:1:cells:3:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRulePenaltyIncomeValue one item inside", page.advancedAccountingRulePenaltyIncomeValue.size(), 0);
    }

    @Test
    public void advancedAccountingRuleFeeIncomeActionClickTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.advancedAccountingRuleFeeIncomeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeTable:body:rows:1:cells:3:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRuleFeeIncomeValue one item inside", page.advancedAccountingRuleFeeIncomeValue.size(), 0);
    }

    @Test
    public void chargeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);
    }

    @Test
    public void overdueChargesActionClickTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.overdueChargeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:overdueChargeTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have overdueChargeValue one item inside", page.overdueChargeValue.size(), 0);
    }

    @Test
    public void chargeActionClickTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.chargeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:chargeTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 0);
    }

    @Test
    public void overdueChargeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:overdueChargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);
    }

    @Test
    public void advancedAccountingRulePenaltyIncomeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);

    }

    @Test
    public void advancedAccountingRuleFeeIncomeAddLinkClickNoCurrencyTest() {

        this.wicket.login();

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);

    }

    @Test
    public void termNominalInterestRateByLoanCycleActionClickTest() {
        this.wicket.login();

        boolean termVaryBasedOnLoanCycleValue = true;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            CheckBox termVaryBasedOnLoanCycleField = this.wicket.getComponentFromLastRenderedPage("form:termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);
            this.wicket.executeBehavior(termVaryBasedOnLoanCycleField);
        }

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.termNominalInterestRateByLoanCycleValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:termNominalInterestRateByLoanCycleBlock:termNominalInterestRateByLoanCycleContainer:termNominalInterestRateByLoanCycleTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have termNominalInterestRateByLoanCycleValue one item inside", page.termNominalInterestRateByLoanCycleValue.size(), 0);
    }

    @Test
    public void termNumberOfRepaymentByLoanCycleActionClickTest() {
        this.wicket.login();

        boolean termVaryBasedOnLoanCycleValue = true;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            CheckBox termVaryBasedOnLoanCycleField = this.wicket.getComponentFromLastRenderedPage("form:termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);
            this.wicket.executeBehavior(termVaryBasedOnLoanCycleField);
        }

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.termNumberOfRepaymentByLoanCycleValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:termNumberOfRepaymentByLoanCycleBlock:termNumberOfRepaymentByLoanCycleContainer:termNumberOfRepaymentByLoanCycleTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have termNumberOfRepaymentByLoanCycleValue one item inside", page.termNumberOfRepaymentByLoanCycleValue.size(), 0);
    }

    @Test
    public void termPrincipalByLoanCycleActionClickTest() {
        this.wicket.login();

        boolean termVaryBasedOnLoanCycleValue = true;

        LoanCreatePage page = this.wicket.startPage(LoanCreatePage.class);

        JUnitFormTester form = null;

        {
            CheckBox termVaryBasedOnLoanCycleField = this.wicket.getComponentFromLastRenderedPage("form:termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);
            this.wicket.executeBehavior(termVaryBasedOnLoanCycleField);
        }

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        page.termPrincipalByLoanCycleValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:termPrincipalByLoanCycleBlock:termPrincipalByLoanCycleContainer:termPrincipalByLoanCycleTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have termPrincipalByLoanCycleValue one item inside", page.termPrincipalByLoanCycleValue.size(), 0);
    }

}
