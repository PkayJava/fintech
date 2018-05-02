package com.angkorteam.fintech.pages.product.fixed;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ApplyPenalOn;
import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.DepositType;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.OperandType;
import com.angkorteam.fintech.dto.enums.Operator;
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

public class FixedCreatePageTest {

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
        int currencyDecimalPlaceValue = 2;

        double termDefaultDepositAmountValue = 1000.99;
        double termMinimumDepositAmountValue = 1;
        double termMaximumDepositAmountValue = 10000000000.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        DayInYear termDayInYearValue = DayInYear.D365;

        int settingLockInPeriodValue = 1;
        LockInType settingLockInTypeValue = LockInType.Month;
        int settingMinimumDepositTermValue = 12;
        LockInType settingMinimumDepositTypeValue = LockInType.Month;
        int settingInMultiplesOfValue = 1;
        LockInType settingInMultiplesTypeValue = LockInType.Month;
        int settingMaximumDepositTermValue = 240;
        LockInType settingMaximumDepositTypeValue = LockInType.Month;
        double settingApplyPenalInterestValue = 1.99;
        ApplyPenalOn settingApplyPenalOnValue = ApplyPenalOn.WholeTerm;

        Date interestRateValidFromDateValue = DateTime.now().plusDays(1).toDate();
        Date interestRateValidEndDateValue = DateTime.now().plusMonths(12).toDate();

        this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", termDefaultDepositAmountValue);
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", termMinimumDepositAmountValue);
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", termMaximumDepositAmountValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", termDayInYearValue);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", settingMinimumDepositTermValue);
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", settingMinimumDepositTypeValue);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", settingInMultiplesOfValue);
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", settingInMultiplesTypeValue);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", settingMaximumDepositTermValue);
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", settingMaximumDepositTypeValue);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", settingApplyPenalInterestValue);
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", settingApplyPenalOnValue);

        // Interest Rate Chart

        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(interestRateValidFromDateValue, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(interestRateValidEndDateValue, "dd/MM/yyyy"));

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_savings_product where name = ? and deposit_type_enum = ?", detailProductNameValue, DepositType.Fixed.getLiteral());
        Assert.assertNotNull("expected to have m_savings_product name = '" + detailProductNameValue + "'", actual);

    }

    @Test
    public void dataEntryMaximumGroupByPrice() {
        this.wicket.login();

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = null;

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String settingTaxGroupValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_tax_group LIMIT 1", String.class);
        String currencyCodeValue = "USD";
        Boolean settingWithholdTaxApplicableValue = true;
        int currencyDecimalPlaceValue = 2;
        int currencyMultipleOfFieldValue = 1;
        int accountingValue = 1;
        double termDefaultDepositAmountValue = 1000.99;
        double termMinimumDepositAmountValue = 1;
        double termMaximumDepositAmountValue = 10000000000.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        DayInYear termDayInYearValue = DayInYear.D365;
        int settingLockInPeriodValue = 1;
        LockInType settingLockInTypeValue = LockInType.Month;
        int settingMinimumDepositTermValue = 12;
        LockInType settingMinimumDepositTypeValue = LockInType.Month;
        int settingInMultiplesOfValue = 1;
        LockInType settingInMultiplesTypeValue = LockInType.Month;
        int settingMaximumDepositTermValue = 240;
        LockInType settingMaximumDepositTypeValue = LockInType.Month;
        double settingApplyPenalInterestValue = 1.99;
        ApplyPenalOn settingApplyPenalOnValue = ApplyPenalOn.WholeTerm;
        boolean settingForPreMatureClosureValue = true;
        Date interestRateValidFromDateValue = DateTime.now().toDate();
        Date interestRateValidEndDateValue = DateTime.now().plusMonths(12).toDate();

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
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", currencyMultipleOfFieldValue);

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", termDefaultDepositAmountValue);
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", termMinimumDepositAmountValue);
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", termMaximumDepositAmountValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", termDayInYearValue);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", settingMinimumDepositTermValue);
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", settingMinimumDepositTypeValue);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", settingInMultiplesOfValue);
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", settingInMultiplesTypeValue);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", settingMaximumDepositTermValue);
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", settingMaximumDepositTypeValue);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", settingApplyPenalInterestValue);
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", settingApplyPenalOnValue);
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
        form.setValue("settingTaxGroupBlock:settingTaxGroupContainer:settingTaxGroupField", settingTaxGroupValue);
        form.setValue("settingForPreMatureClosureBlock:settingForPreMatureClosureContainer:settingForPreMatureClosureField", settingForPreMatureClosureValue);

        // Interest Rate Chart

        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(interestRateValidFromDateValue, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(interestRateValidEndDateValue, "dd/MM/yyyy"));
        form.setValue("interestRatePrimaryGroupingByAmountBlock:interestRatePrimaryGroupingByAmountContainer:interestRatePrimaryGroupingByAmountField", true);

        {
            Option periodType = new Option(LockInType.Month.name(), LockInType.Month.getDescription());
            int periodFrom = 1;
            int periodTo = 10;
            int amountRangeFrom = 0;
            double interest = 10.99;
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", periodType);
            item.put("periodFrom", periodFrom);
            item.put("periodTo", periodTo);
            item.put("amountRangeFrom", amountRangeFrom);
            item.put("interest", interest);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            String operand = "100";
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", operand);
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", interest);
            interestRate.add(rateItem);
            item.put("interestRate", interestRate);
            page.interestRateChartValue.add(item);
        }
        {
            Option periodType = new Option(LockInType.Month.name(), LockInType.Month.getDescription());
            int periodFrom = 11;
            int amountRangeFrom = 0;
            double interest = 10.99;
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", periodType);
            item.put("periodFrom", periodFrom);
            item.put("amountRangeFrom", amountRangeFrom);
            item.put("interest", interest);
            item.put("description", "JUNIT_" + this.wicket.getStringGenerator().generate(10));
            List<Map<String, Object>> interestRate = Lists.newArrayList();
            String operand = "100";
            Map<String, Object> rateItem = Maps.newHashMap();
            rateItem.put("attribute", new Option(Attribute.Age.name(), Attribute.Age.getDescription()));
            rateItem.put("operator", new Option(Operator.GreaterThan.name(), Operator.GreaterThan.getDescription()));
            rateItem.put("operand", operand);
            rateItem.put("operandType", new Option(OperandType.Fixed.name(), OperandType.Fixed.getDescription()));
            rateItem.put("interest", interest);
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

        String cashSavingReferenceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashSavingControlValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashSavingsTransfersInSuspenseValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashInterestOnSavingsValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Expense.getLiteral());
        String cashIncomeFromFeesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());
        String cashIncomeFromPenaltiesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());

        form.setValue("cashBlock:cashContainer:cashSavingReferenceField", cashSavingReferenceValue);
        form.setValue("cashBlock:cashContainer:cashSavingControlField", cashSavingControlValue);
        form.setValue("cashBlock:cashContainer:cashSavingsTransfersInSuspenseField", cashSavingsTransfersInSuspenseValue);
        form.setValue("cashBlock:cashContainer:cashInterestOnSavingsField", cashInterestOnSavingsValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeesField", cashIncomeFromFeesValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromPenaltiesField", cashIncomeFromPenaltiesValue);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = null;

        String detailProductNameValue = "FIXED_DEPOSIT_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;
        int currencyMultipleOfValue = 1;

        double termDefaultDepositAmountValue = 1000.99;
        double termMinimumDepositAmountValue = 1;
        double termMaximumDepositAmountValue = 10000000000.99;
        InterestCompoundingPeriod termInterestCompoundingPeriodValue = InterestCompoundingPeriod.Annually;
        InterestPostingPeriod termInterestPostingPeriodValue = InterestPostingPeriod.Annually;
        InterestCalculatedUsing termInterestCalculatedUsingValue = InterestCalculatedUsing.AverageDailyBalance;
        DayInYear termDayInYearValue = DayInYear.D365;

        int settingLockInPeriodValue = 1;
        LockInType settingLockInTypeValue = LockInType.Month;
        int settingMinimumDepositTermValue = 12;
        LockInType settingMinimumDepositTypeValue = LockInType.Month;
        int settingInMultiplesOfValue = 1;
        LockInType settingInMultiplesTypeValue = LockInType.Month;
        int settingMaximumDepositTermValue = 240;
        LockInType settingMaximumDepositTypeValue = LockInType.Month;
        double settingApplyPenalInterestValue = 1.99;
        ApplyPenalOn settingApplyPenalOnValue = ApplyPenalOn.WholeTerm;
        boolean settingForPreMatureClosureValue = true;

        Date interestRateValidFromDateValue = DateTime.now().toDate();
        Date interestRateValidEndDateValue = DateTime.now().plusMonths(12).toDate();

        String settingTaxGroupValue = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_tax_group LIMIT 1", String.class);
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
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", currencyMultipleOfValue);

        // Terms
        form.setValue("termDefaultDepositAmountBlock:termDefaultDepositAmountContainer:termDefaultDepositAmountField", termDefaultDepositAmountValue);
        form.setValue("termMinimumDepositAmountBlock:termMinimumDepositAmountContainer:termMinimumDepositAmountField", termMinimumDepositAmountValue);
        form.setValue("termMaximumDepositAmountBlock:termMaximumDepositAmountContainer:termMaximumDepositAmountField", termMaximumDepositAmountValue);
        form.setValue("termInterestCompoundingPeriodBlock:termInterestCompoundingPeriodContainer:termInterestCompoundingPeriodField", termInterestCompoundingPeriodValue);
        form.setValue("termInterestPostingPeriodBlock:termInterestPostingPeriodContainer:termInterestPostingPeriodField", termInterestPostingPeriodValue);
        form.setValue("termInterestCalculatedUsingBlock:termInterestCalculatedUsingContainer:termInterestCalculatedUsingField", termInterestCalculatedUsingValue);
        form.setValue("termDayInYearBlock:termDayInYearContainer:termDayInYearField", termDayInYearValue);

        // Settings
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingMinimumDepositTermBlock:settingMinimumDepositTermContainer:settingMinimumDepositTermField", settingMinimumDepositTermValue);
        form.setValue("settingMinimumDepositTypeBlock:settingMinimumDepositTypeContainer:settingMinimumDepositTypeField", settingMinimumDepositTypeValue);
        form.setValue("settingInMultiplesOfBlock:settingInMultiplesOfContainer:settingInMultiplesOfField", settingInMultiplesOfValue);
        form.setValue("settingInMultiplesTypeBlock:settingInMultiplesTypeContainer:settingInMultiplesTypeField", settingInMultiplesTypeValue);
        form.setValue("settingMaximumDepositTermBlock:settingMaximumDepositTermContainer:settingMaximumDepositTermField", settingMaximumDepositTermValue);
        form.setValue("settingMaximumDepositTypeBlock:settingMaximumDepositTypeContainer:settingMaximumDepositTypeField", settingMaximumDepositTypeValue);
        form.setValue("settingApplyPenalInterestBlock:settingApplyPenalInterestContainer:settingApplyPenalInterestField", settingApplyPenalInterestValue);
        form.setValue("settingApplyPenalOnBlock:settingApplyPenalOnContainer:settingApplyPenalOnField", settingApplyPenalOnValue);
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", settingWithholdTaxApplicableValue);
        form.setValue("settingTaxGroupBlock:settingTaxGroupContainer:settingTaxGroupField", settingTaxGroupValue);
        form.setValue("settingForPreMatureClosureBlock:settingForPreMatureClosureContainer:settingForPreMatureClosureField", settingForPreMatureClosureValue);

        // Interest Rate Chart

        form.setValue("interestRateValidFromDateBlock:interestRateValidFromDateContainer:interestRateValidFromDateField", DateFormatUtils.format(interestRateValidFromDateValue, "dd/MM/yyyy"));
        form.setValue("interestRateValidEndDateBlock:interestRateValidEndDateContainer:interestRateValidEndDateField", DateFormatUtils.format(interestRateValidEndDateValue, "dd/MM/yyyy"));
        form.setValue("interestRatePrimaryGroupingByAmountBlock:interestRatePrimaryGroupingByAmountContainer:interestRatePrimaryGroupingByAmountField", false);

        {
            Map<String, Object> item = Maps.newHashMap();
            item.put("periodType", new Option(LockInType.Month.name(), LockInType.Month.getDescription()));
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
            item.put("periodType", new Option(LockInType.Month.name(), LockInType.Month.getDescription()));
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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", this.wicket.getStringGenerator().externalId());
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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        // Assert.assertEquals("exected currencyPopup to be shown",
        // page.currencyPopup.isShown(), true);
    }

    @Test
    public void chargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:interestRateChartAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        JUnitFormTester popupForm = this.wicket.newFormTester("interestRateChartPopup:content:form");
        popupForm.setValue("periodTypeField", LockInType.Month);
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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);
        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have accountingValue = 'Cash'", page.accountingValue, AccountingType.Cash.getDescription());
    }

    @Test
    public void advancedAccountingRulePenaltyIncomeAddLinkClickTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRulePenaltyIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRulePenaltyIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRulePenaltyIncomeAddLink);

        // Assert.assertEquals("exected currencyPopup to be shown",
        // page.currencyPopup.isShown(), true);

    }

    @Test
    public void advancedAccountingRuleFundSourceAddLinkClickTest() {
        this.wicket.login();

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.select("accountingField", 1);

        RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
        this.wicket.executeBehavior(accountingField);

        this.wicket.startPage(page);

        AjaxLink<?> advancedAccountingRuleFeeIncomeAddLink = this.wicket.getComponentFromLastRenderedPage("form:advancedAccountingRuleBlock:advancedAccountingRuleContainer:advancedAccountingRuleFeeIncomeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(advancedAccountingRuleFeeIncomeAddLink);

        // Assert.assertEquals("exected currencyPopup to be shown",
        // page.currencyPopup.isShown(), true);

    }

    @Test
    public void advancedAccountingRuleFeeIncomeAddLinkClickTest() {

        this.wicket.login();

        String currencyCodeValue = "USD";

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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
    public void advancedAccountingRuleFeeIncomeActionClickTest() {
        this.wicket.login();

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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
    public void advancedAccountingRulePenaltyIncomeActionClickTest() {
        this.wicket.login();

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

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
    public void settingWithholdTaxApplicableFieldUpdateTest() {

        FixedCreatePage page = this.wicket.startPage(FixedCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");
        form.setValue("settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", true);

        CheckBox settingWithholdTaxApplicableField = this.wicket.getComponentFromLastRenderedPage("form:settingWithholdTaxApplicableBlock:settingWithholdTaxApplicableContainer:settingWithholdTaxApplicableField", CheckBox.class);
        this.wicket.executeBehavior(settingWithholdTaxApplicableField);

        this.wicket.startPage(page);

        // Assert.assertEquals("exected settingWithholdTaxApplicableContainer to be
        // visibled", page.settingWithholdTaxApplicableContainer.isVisible(), true);
    }
}
