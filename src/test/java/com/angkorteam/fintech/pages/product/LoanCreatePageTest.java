package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.loan.Amortization;
import com.angkorteam.fintech.dto.loan.DayInMonth;
import com.angkorteam.fintech.dto.loan.DayInYear;
import com.angkorteam.fintech.dto.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.loan.InterestMethod;
import com.angkorteam.fintech.dto.loan.NominalInterestRateScheduleType;
import com.angkorteam.fintech.dto.loan.RepaidType;
import com.angkorteam.fintech.dto.loan.RepaymentStrategy;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.fintech.provider.WhenProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

public class LoanCreatePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMaximumLinkedFloatInterest() {
        String detailProductNameValue = "LOAN_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;
        String detailFundValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_fund limit 1", String.class);
        String currencyCodeValue = "USD";
        Date detailStartDateValue = DateTime.now().toDate();
        Date detailCloseDateValue = DateTime.now().plusYears(1).toDate();
        Boolean detailIncludeInCustomerLoanCounterValue = true;
        int currencyInMultipleOfValue = 1;
        int currencyInstallmentInMultipleOfValue = 120;
        int currencyDecimalPlaceValue = 2;
        int termRepaidEveryValue = 1;
        int termNumberOfRepaymentDefaultValue = 1;
        int termNominalInterestRateDefaultValue = 1;
        boolean termVaryBasedOnLoanCycleValue = true;
        double termPrincipalMinimumValue = 1.99;
        double termPrincipalDefaultValue = 500.99;
        double termPrincipalMaximumValue = 1000.99;

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
            AjaxLink<?> termPrincipalByLoanCycleAddLink = this.wicket.getComponentFromLastRenderedPage("form:termPrincipalByLoanCycleBlock:termPrincipalByLoanCycleContainer:termPrincipalByLoanCycleField", AjaxLink.class);
            form = this.wicket.newFormTester("form");
            this.wicket.executeAjaxLink(termPrincipalByLoanCycleAddLink);
            
            JUnitFormTester popupForm = this.wicket.newFormTester("termPrincipalByLoanCyclePopup:content:form");
            popupForm.setValue("whenField", "");
            popupForm.setValue("loanCycleField", "");
            popupForm.setValue("minimumField", "");
            popupForm.setValue("defaultField", "");
            popupForm.setValue("maximumField", "");
            
//            private Form<Void> form;
//            private AjaxButton okayButton;
//
//            private WhenProvider whenProvider;
//            private Select2SingleChoice<Option> whenField;
//            private TextFeedbackPanel whenFeedback;
//
//            private TextField<Integer> loanCycleField;
//            private TextFeedbackPanel loanCycleFeedback;
//
//            private TextField<Double> minimumField;
//            private TextFeedbackPanel minimumFeedback;
//
//            private TextField<Double> defaultField;
//            private TextFeedbackPanel defaultFeedback;
//
//            private TextField<Double> maximumField;
//            private TextFeedbackPanel maximumFeedback;
        }

        form = this.wicket.newFormTester("form");

        // Detail
        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);
        form.setValue("detailFundBlock:detailFundContainer:detailFundField", detailFundValue);
        form.setValue("detailStartDateBlock:detailStartDateContainer:detailStartDateField", DateFormatUtils.format(detailStartDateValue, "dd/MM/yyyy"));
        form.setValue("detailCloseDateBlock:detailCloseDateContainer:detailCloseDateField", DateFormatUtils.format(detailCloseDateValue, "dd/MM/yyyy"));
        form.setValue("detailIncludeInCustomerLoanCounterBlock:detailIncludeInCustomerLoanCounterContainer:detailIncludeInCustomerLoanCounterField", detailIncludeInCustomerLoanCounterValue);

        // Currency
        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyInMultipleOfBlock:currencyInMultipleOfContainer:currencyInMultipleOfField", currencyInMultipleOfValue);
        form.setValue("currencyInstallmentInMultipleOfBlock:currencyInstallmentInMultipleOfContainer:currencyInstallmentInMultipleOfField", currencyInstallmentInMultipleOfValue);

        // Term

        form.setValue("termVaryBasedOnLoanCycleBlock:termVaryBasedOnLoanCycleContainer:termVaryBasedOnLoanCycleField", termVaryBasedOnLoanCycleValue);

        form.setValue("termPrincipalMinimumBlock:termPrincipalMinimumContainer:termPrincipalMinimumField", termPrincipalMinimumValue);
        form.setValue("termPrincipalDefaultBlock:termPrincipalDefaultContainer:termPrincipalDefaultField", termPrincipalDefaultValue);
        form.setValue("termPrincipalMaximumBlock:termPrincipalMaximumContainer:termPrincipalMaximumField", termPrincipalMaximumValue);

//        protected WebMarkupContainer termPrincipalByLoanCycleBlock;
//        protected WebMarkupContainer termPrincipalByLoanCycleContainer;
//        protected List<Map<String, Object>> termPrincipalByLoanCycleValue = Lists.newArrayList();
//        protected DataTable<Map<String, Object>, String> termPrincipalByLoanCycleTable;
//        protected ListDataProvider termPrincipalByLoanCycleProvider;
//        protected AjaxLink<Void> termPrincipalByLoanCycleAddLink;
//        protected ModalWindow termPrincipalByLoanCyclePopup;

    }

    @Test
    public void dataEntryMaximumNormalInterest() {

    }

    @Test
    public void dataEntryMaximumCash() {

    }

    @Test
    public void dataEntryMaximumPeriodic() {

    }

    @Test
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
