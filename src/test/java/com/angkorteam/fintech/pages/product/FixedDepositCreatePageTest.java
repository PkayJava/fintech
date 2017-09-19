package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.dto.DepositType;
import com.angkorteam.fintech.dto.fixed.ApplyPenalOn;
import com.angkorteam.fintech.dto.fixed.Attribute;
import com.angkorteam.fintech.dto.fixed.DayInYear;
import com.angkorteam.fintech.dto.fixed.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.fixed.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.fixed.InterestPostingPeriod;
import com.angkorteam.fintech.dto.fixed.LockInPeriod;
import com.angkorteam.fintech.dto.fixed.OperandType;
import com.angkorteam.fintech.dto.fixed.Operator;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FixedDepositCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String currencyCodeValue = "USD";

        this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", "1000.99");
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", "1");
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", "10000000000.99");
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", InterestCompoundingPeriod.Annually);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", InterestPostingPeriod.Annually);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", InterestCalculatedUsing.AverageDailyBalance);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", DayInYear.D365);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", "1");
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", LockInPeriod.Month);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", "12");
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", "1");
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", LockInPeriod.Month);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", "240");
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", "1.99");
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", ApplyPenalOn.WholeTerm);

        // Interest Rate Chart

        Date fromDate = DateTime.now().plusDays(1).toDate();
        Date endDate = DateTime.now().plusMonths(12).toDate();
        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(fromDate, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(endDate, "dd/MM/yyyy"));

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }

    @Test
    public void dataEntryMaximumGroupByPrice() {
        this.wicket.login();

        // https://mifosforge.jira.com/wiki/spaces/docs/pages/121831466/Interest+rate+chart+with+amount+range
        // acc_product_mapping
        // m_deposit_product_interest_rate_chart
        // m_savings_product_charge
        // m_interest_incentives
        // m_deposit_product_term_and_preclosure
        // m_interest_rate_slab
        // m_savings_product
        // m_interest_rate_chart

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String settingTaxGroupValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_tax_group LIMIT 1", String.class);
        String currencyCodeValue = "USD";
        Boolean settingWithholdTaxApplicableValue = true;
        int accountingValue = 1;
        {
            form = this.wicket.newFormTester("form");
            form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
            CheckBox settingWithholdTaxApplicableField = this.wicket.getComponentFromLastRenderedPage("form:settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", CheckBox.class);
            this.wicket.executeBehavior(settingWithholdTaxApplicableField);
        }

        {
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            this.wicket.executeBehavior(currencyCodeField);
        }

        {
            form = this.wicket.newFormTester("form");
            form.select("accountingField", accountingValue);
            RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
            this.wicket.executeBehavior(accountingField);
        }

        form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", "1");

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", "1000.99");
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", "1");
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", "10000000000.99");
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", InterestCompoundingPeriod.Annually);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", InterestPostingPeriod.Annually);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", InterestCalculatedUsing.AverageDailyBalance);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", DayInYear.D365);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", "1");
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", LockInPeriod.Month);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", "12");
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", "1");
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", LockInPeriod.Month);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", "240");
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", "1.99");
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", ApplyPenalOn.WholeTerm);
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
        form.setValue("settingTaxGroupBlock:settingTaxGroupContainer:settingTaxGroupField", settingTaxGroupValue);
        form.setValue("settingForPreMatureClosureBlock:settingForPreMatureClosureContainer:settingForPreMatureClosureField", true);

        // Interest Rate Chart

        Date fromDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusMonths(12).toDate();
        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(fromDate, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(endDate, "dd/MM/yyyy"));
        form.setValue("interestRatePrimaryGroupingByAmountBlock:interestRatePrimaryGroupingByAmountContainer:interestRatePrimaryGroupingByAmountField", true);

        {
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", new Option(LockInPeriod.Month.name(), LockInPeriod.Month.getDescription()));
            item.put("periodFrom", 1);
            item.put("periodTo", 10);
            item.put("amountRangeFrom", 0);
            // item.put("amountRangeTo", 500);
            item.put("interest", 10.99d);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", "100");
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", 10.99d);
            interestRate.add(rateItem);
            item.put("interestRate", interestRate);
            page.interestRateChartValue.add(item);
        }
        {
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", new Option(LockInPeriod.Month.name(), LockInPeriod.Month.getDescription()));
            item.put("periodFrom", 11);
            item.put("amountRangeFrom", 0);
            // item.put("amountRangeTo", 10000);
            item.put("interest", 10.99d);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", "100");
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", 10.99d);
            interestRate.add(rateItem);
            item.put("interestRate", interestRate);
            page.interestRateChartValue.add(item);
        }

        // Charge
        {
            String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeValue);
            page.chargeValue.add(item);
        }

        // Accounting
        form.select("accountingField", accountingValue);

        form.setValue("cashBlock:cashContainer:cashSavingReferenceField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashSavingControlField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashSavingsTransfersInSuspenseField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashInterestOnSavingsField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeesField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashIncomeFromPenaltiesField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral()));

        {
            String paymentId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("paymentId", paymentId);
            item.put("accountId", accountId);
            page.advancedAccountingRuleFundSourceValue.add(item);
        }
        {
            String chargeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeId);
            item.put("accountId", accountId);
            page.advancedAccountingRuleFeeIncomeValue.add(item);
        }

        {
            String chargeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeId);
            item.put("accountId", accountId);
            page.advancedAccountingRulePenaltyIncomeValue.add(item);
        }

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }

    @Test
    public void dataEntryMaximumPeriod() {
        this.wicket.login();

        // https://mifosforge.jira.com/wiki/spaces/docs/pages/121831466/Interest+rate+chart+with+amount+range
        // acc_product_mapping
        // m_deposit_product_interest_rate_chart
        // m_savings_product_charge
        // m_interest_incentives
        // m_deposit_product_term_and_preclosure
        // m_interest_rate_slab
        // m_savings_product
        // m_interest_rate_chart

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String settingTaxGroupValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_tax_group LIMIT 1", String.class);
        String currencyCodeValue = "USD";
        Boolean settingWithholdTaxApplicableValue = true;
        int accountingValue = 1;
        {
            form = this.wicket.newFormTester("form");
            form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
            CheckBox settingWithholdTaxApplicableField = this.wicket.getComponentFromLastRenderedPage("form:settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", CheckBox.class);
            this.wicket.executeBehavior(settingWithholdTaxApplicableField);
        }

        {
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            this.wicket.executeBehavior(currencyCodeField);
        }

        {
            form = this.wicket.newFormTester("form");
            form.select("accountingField", accountingValue);
            RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
            this.wicket.executeBehavior(accountingField);
        }

        form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", "2");
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", "1");

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", "1000.99");
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", "1");
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", "10000000000.99");
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", InterestCompoundingPeriod.Annually);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", InterestPostingPeriod.Annually);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", InterestCalculatedUsing.AverageDailyBalance);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", DayInYear.D365);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", "1");
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", LockInPeriod.Month);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", "12");
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", "1");
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", LockInPeriod.Month);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", "240");
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", LockInPeriod.Month);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", "1.99");
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", ApplyPenalOn.WholeTerm);
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
        form.setValue("settingTaxGroupBlock:settingTaxGroupContainer:settingTaxGroupField", settingTaxGroupValue);
        form.setValue("settingForPreMatureClosureBlock:settingForPreMatureClosureContainer:settingForPreMatureClosureField", true);

        // Interest Rate Chart

        Date fromDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusMonths(12).toDate();
        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(fromDate, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(endDate, "dd/MM/yyyy"));
        form.setValue("interestRatePrimaryGroupingByAmountBlock:interestRatePrimaryGroupingByAmountContainer:interestRatePrimaryGroupingByAmountField", false);

        {
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", new Option(LockInPeriod.Month.name(), LockInPeriod.Month.getDescription()));
            item.put("periodFrom", 1);
            // item.put("periodTo", 10);
            item.put("amountRangeFrom", 0);
            item.put("amountRangeTo", 500);
            item.put("interest", 10.99d);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", "100");
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", 10.99d);
            interestRate.add(rateItem);
            item.put("interestRate", interestRate);
            page.interestRateChartValue.add(item);
        }
        {
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", new Option(LockInPeriod.Month.name(), LockInPeriod.Month.getDescription()));
            item.put("periodFrom", 1);
            // item.put("periodTo", 10);
            item.put("amountRangeFrom", 501);
            // item.put("amountRangeTo", 10000);
            item.put("interest", 10.99d);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", "100");
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", 10.99d);
            interestRate.add(rateItem);
            item.put("interestRate", interestRate);
            page.interestRateChartValue.add(item);
        }

        // Charge
        {
            String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeValue);
            page.chargeValue.add(item);
        }

        // Accounting
        form.select("accountingField", accountingValue);

        form.setValue("cashBlock:cashContainer:cashSavingReferenceField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashSavingControlField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashSavingsTransfersInSuspenseField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashInterestOnSavingsField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeesField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral()));
        form.setValue("cashBlock:cashContainer:cashIncomeFromPenaltiesField", this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral()));

        {
            String paymentId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_payment_type LIMIT 1", String.class);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("paymentId", paymentId);
            item.put("accountId", accountId);
            page.advancedAccountingRuleFundSourceValue.add(item);
        }
        {
            String chargeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeId);
            item.put("accountId", accountId);
            page.advancedAccountingRuleFeeIncomeValue.add(item);
        }

        {
            String chargeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
            String accountId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
            Map<String, Object> item = Maps.newHashMap();
            item.put("chargeId", chargeId);
            item.put("accountId", accountId);
            page.advancedAccountingRulePenaltyIncomeValue.add(item);
        }

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }

    @Test
    public void chargeActionClickTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        page.chargeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:chargeTable:body:rows:1:cells:6:cell:1:link", "click");

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 0);
    }

    @Test
    public void chargeAddLinkClickTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = ? limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0, 1);

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void chargeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        Assert.assertEquals("exected currencyPopup to be shown", page.currencyPopup.isShown(), true);
    }

    @Test
    public void chargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void interestRateChartAddLinkClickTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:interestRateChartAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        JUnitFormTester popupForm = this.wicket.newFormTester("interestRateChartPopup:content:form");
        popupForm.setValue("periodTypeField", LockInPeriod.Month);
        popupForm.setValue("periodFromField", "1");
        popupForm.setValue("periodToField", "90");
        popupForm.setValue("amountRangeFromField", "0.99");
        popupForm.setValue("amountRangeToField", "10.99");
        popupForm.setValue("interestField", "1.99");
        popupForm.setValue("descriptionField", "JUNIT_" + this.wicket.getStringGenerator().generate(5));

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("interestRateChartPopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow interestRateChartPopup = this.wicket.getComponentFromLastRenderedPage("interestRateChartPopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(interestRateChartPopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have interestRateChartValue one item inside", page.interestRateChartValue.size(), 1);
    }

    @Test
    public void interestRateChartActionClickDeleteTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        item.put("interestRate", Lists.newArrayList());
        page.interestRateChartValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:interestRateChartTable:body:rows:1:cells:8:cell:1:link", "click");

        Assert.assertEquals("expected to have interestRateChartValue one item inside", page.interestRateChartValue.size(), 0);
    }

    @Test
    public void interestRateChartActionClickIncentivesTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = UUID.randomUUID().toString();
        item.put("uuid", uuid);
        item.put("interestRate", Lists.newArrayList());
        page.interestRateChartValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:interestRateChartTable:body:rows:1:cells:8:cell:3:link", "click");

        ModalWindow interestRateChartPopup = this.wicket.getComponentFromLastRenderedPage("incentivePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(interestRateChartPopup);
    }

    @Test
    public void accountingFieldUpdateTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);
        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have accountingValue = 'Cash'", page.accountingValue, FixedDepositCreatePage.ACC_CASH);
    }

    @Test
    public void advancedAccountingRulePenaltyIncomeAddLinkClickTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 1 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
        String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        JUnitFormTester popupForm = this.wicket.newFormTester("penaltyIncomePopup:content:form");
        popupForm.setValue("chargeField", chargeValue);
        popupForm.setValue("accountField", accountValue);

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow penaltyIncomePopup = this.wicket.getComponentFromLastRenderedPage("penaltyIncomePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(penaltyIncomePopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRulePenaltyIncomeValue one item", page.advancedAccountingRulePenaltyIncomeValue.size(), 1);

    }

    @Test
    public void advancedAccountingRulePenaltyIncomeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void advancedAccountingRuleFundSourceAddLinkClickTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

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

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRuleFundSourceValue one item", page.advancedAccountingRuleFundSourceValue.size(), 1);

    }

    @Test
    public void advancedAccountingRuleFeeIncomeAddLinkClickNoCurrencyTest() {

        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void advancedAccountingRuleFeeIncomeAddLinkClickTest() {

        this.wicket.login();

        String currencyCodeValue = "USD";

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where  charge_applies_to_enum = ? and currency_code = ? and is_penalty = 0 and is_active = 1 limit 1", String.class, ChargeType.SavingDeposit.getLiteral(), currencyCodeValue);
        String accountValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        JUnitFormTester popupForm = this.wicket.newFormTester("feeIncomePopup:content:form");
        popupForm.setValue("chargeField", chargeValue);
        popupForm.setValue("accountField", accountValue);

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow feeIncomePopup = this.wicket.getComponentFromLastRenderedPage("feeIncomePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(feeIncomePopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have advancedAccountingRuleFeeIncomeValue one item", page.advancedAccountingRuleFeeIncomeValue.size(), 1);

    }

    @Test
    public void advancedAccountingRuleFundSourceActionClickTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void advancedAccountingRuleFeeIncomeActionClickTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void advancedAccountingRulePenaltyIncomeActionClickTest() {
        this.wicket.login();

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

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
    public void settingWithholdTaxApplicableFieldUpdateTest() {

        FixedDepositCreatePage page = this.wicket.startPage(FixedDepositCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", true);

        CheckBox settingWithholdTaxApplicableField = this.wicket.getComponentFromLastRenderedPage("form:settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", CheckBox.class);
        this.wicket.executeBehavior(settingWithholdTaxApplicableField);

        this.wicket.startPage(page);

        Assert.assertEquals("exected settingWithholdTaxApplicableContainer to be visibled", page.settingWithholdTaxApplicableContainer.isVisible(), true);
    }
}
