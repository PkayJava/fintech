package com.angkorteam.fintech.pages.product.saving;

import java.util.Map;
import java.util.UUID;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Maps;

public class SavingCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        String detailProductNameValue = "SIVING_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;
        int currencyMultipleOfValue = 1;

        double termNominalAnnualInterestValue = 1.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        DayInYear termDaysInYearValue = DayInYear.D365;

        double settingMinimumOpeningBalanceValue = 10.99;
        Integer settingLockInPeriodValue = 1;
        LockInType settingLockInTypeValue = LockInType.Day;
        boolean settingApplyWithdrawalFeeForTransferValue = true;
        double settingBalanceRequiredForInterestCalculationValue = 1.99;
        boolean settingEnforceMinimumBalanceValue = true;
        double settingMinimumBalanceValue = 1.22;
        boolean settingOverdraftAllowedValue = true;
        double settingMaximumOverdraftAmountLimitValue = 1.11;
        double settingNominalAnnualInterestForOverdraftValue = 1.22;
        double settingMinOverdraftRequiredForInterestCalculationValue = 1.22;
        boolean settingWithholdTaxApplicableValue = true;
        String settingTaxGroupValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_tax_group limit 1", String.class);
        boolean settingEnableDormancyTrackingValue = true;
        int settingNumberOfDaysToInactiveSubStatusValue = 1;
        int settingNumberOfDaysToDormantSubStatusValue = 2;
        int settingNumberOfDaysToEscheatValue = 3;
        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);

        int accountingValue = 1;

        String cashSavingReferenceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashOverdraftPortfolioValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashSavingControlValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashSavingsTransfersInSuspenseValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashEscheatLiabilityValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashInterestOnSavingValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String cashWriteOffValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String cashIncomeFromFeeValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashIncomeFromPenaltiesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashOverdraftInterestIncomeValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());

        this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        {
            CheckBox settingOverdraftAllowedField = this.wicket.getComponentFromLastRenderedPage("form:settingOverdraftAllowedBlock:settingOverdraftAllowedContainer:settingOverdraftAllowedField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("settingOverdraftAllowedBlock:settingOverdraftAllowedContainer:settingOverdraftAllowedField", settingOverdraftAllowedValue);
            this.wicket.executeBehavior(settingOverdraftAllowedField);
        }
        {
            CheckBox settingWithholdTaxApplicableField = this.wicket.getComponentFromLastRenderedPage("form:settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
            this.wicket.executeBehavior(settingWithholdTaxApplicableField);
        }
        {
            form = this.wicket.newFormTester("form");
            form.select("accountingField", accountingValue);
            RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
            this.wicket.executeBehavior(accountingField);
        }
        {
            CheckBox settingEnableDormancyTrackingField = this.wicket.getComponentFromLastRenderedPage("form:settingEnableDormancyTrackingBlock:settingEnableDormancyTrackingContainer:settingEnableDormancyTrackingField", CheckBox.class);
            form = this.wicket.newFormTester("form");
            form.setValue("settingEnableDormancyTrackingBlock:settingEnableDormancyTrackingContainer:settingEnableDormancyTrackingField", settingEnableDormancyTrackingValue);
            this.wicket.executeBehavior(settingEnableDormancyTrackingField);
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

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
            String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
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

            String tempChargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
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

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", currencyMultipleOfValue);

        form.setValue("termNominalAnnualInterestBlock:termNominalAnnualInterestContainer:termNominalAnnualInterestField", termNominalAnnualInterestValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDaysInYearBlock:termDaysInYearContainer:termDaysInYearField", termDaysInYearValue);

        form.setValue("settingMinimumOpeningBalanceBlock:settingMinimumOpeningBalanceContainer:settingMinimumOpeningBalanceField", settingMinimumOpeningBalanceValue);
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingApplyWithdrawalFeeForTransferBlock:settingApplyWithdrawalFeeForTransferContainer:settingApplyWithdrawalFeeForTransferField", settingApplyWithdrawalFeeForTransferValue);
        form.setValue("settingBalanceRequiredForInterestCalculationBlock:settingBalanceRequiredForInterestCalculationContainer:settingBalanceRequiredForInterestCalculationField", settingBalanceRequiredForInterestCalculationValue);
        form.setValue("settingEnforceMinimumBalanceBlock:settingEnforceMinimumBalanceContainer:settingEnforceMinimumBalanceField", settingEnforceMinimumBalanceValue);
        form.setValue("settingMinimumBalanceBlock:settingMinimumBalanceContainer:settingMinimumBalanceField", settingMinimumBalanceValue);
        form.setValue("settingOverdraftAllowedBlock:settingOverdraftAllowedContainer:settingOverdraftAllowedField", settingOverdraftAllowedValue);
        form.setValue("settingMaximumOverdraftAmountLimitBlock:settingMaximumOverdraftAmountLimitContainer:settingMaximumOverdraftAmountLimitField", settingMaximumOverdraftAmountLimitValue);
        form.setValue("settingNominalAnnualInterestForOverdraftBlock:settingNominalAnnualInterestForOverdraftContainer:settingNominalAnnualInterestForOverdraftField", settingNominalAnnualInterestForOverdraftValue);
        form.setValue("settingMinOverdraftRequiredForInterestCalculationBlock:settingMinOverdraftRequiredForInterestCalculationContainer:settingMinOverdraftRequiredForInterestCalculationField", settingMinOverdraftRequiredForInterestCalculationValue);
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
        form.setValue("settingTaxGroupBlock:settingTaxGroupContainer:settingTaxGroupField", settingTaxGroupValue);
        form.setValue("settingEnableDormancyTrackingBlock:settingEnableDormancyTrackingContainer:settingEnableDormancyTrackingField", settingEnableDormancyTrackingValue);
        form.setValue("settingNumberOfDaysToInactiveSubStatusBlock:settingNumberOfDaysToInactiveSubStatusContainer:settingNumberOfDaysToInactiveSubStatusField", settingNumberOfDaysToInactiveSubStatusValue);
        form.setValue("settingNumberOfDaysToDormantSubStatusBlock:settingNumberOfDaysToDormantSubStatusContainer:settingNumberOfDaysToDormantSubStatusField", settingNumberOfDaysToDormantSubStatusValue);
        form.setValue("settingNumberOfDaysToEscheatBlock:settingNumberOfDaysToEscheatContainer:settingNumberOfDaysToEscheatField", settingNumberOfDaysToEscheatValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.setValue("cashBlock:cashContainer:cashSavingReferenceField", cashSavingReferenceValue);
        form.setValue("cashBlock:cashContainer:cashOverdraftPortfolioField", cashOverdraftPortfolioValue);
        form.setValue("cashBlock:cashContainer:cashSavingControlField", cashSavingControlValue);
        form.setValue("cashBlock:cashContainer:cashSavingsTransfersInSuspenseField", cashSavingsTransfersInSuspenseValue);
        form.setValue("cashBlock:cashContainer:cashEscheatLiabilityBlock:cashEscheatLiabilityContainer:cashEscheatLiabilityField", cashEscheatLiabilityValue);
        form.setValue("cashBlock:cashContainer:cashInterestOnSavingField", cashInterestOnSavingValue);
        form.setValue("cashBlock:cashContainer:cashWriteOffField", cashWriteOffValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeeField", cashIncomeFromFeeValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromPenaltiesField", cashIncomeFromPenaltiesValue);
        form.setValue("cashBlock:cashContainer:cashOverdraftInterestIncomeField", cashOverdraftInterestIncomeValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "SIVING_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;

        double termNominalAnnualInterestValue = 1.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        DayInYear termDaysInYearValue = DayInYear.D365;

        int accountingValue = 0;

        this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        form.setValue("termNominalAnnualInterestBlock:termNominalAnnualInterestContainer:termNominalAnnualInterestField", termNominalAnnualInterestValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDaysInYearBlock:termDaysInYearContainer:termDaysInYearField", termDaysInYearValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);
    }

    @Test
    public void chargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

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
    public void advancedAccountingRuleFeeIncomeAddLinkClickNoCurrencyTest() {

        this.wicket.login();

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

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
    public void advancedAccountingRulePenaltyIncomeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

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
    public void chargeActionClickTest() {
        this.wicket.login();

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
        item.put("uuid", uuid);
        page.chargeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:chargeTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 0);
    }

    @Test
    public void advancedAccountingRuleFundSourceActionClickTest() {
        this.wicket.login();

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
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

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
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

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
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

        SavingCreatePage page = this.wicket.startPage(SavingCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);
    }

}
