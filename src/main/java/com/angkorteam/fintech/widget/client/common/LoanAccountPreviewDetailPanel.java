package com.angkorteam.fintech.widget.client.common;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.DayInMonth;
import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public class LoanAccountPreviewDetailPanel extends Panel {

    protected Page itemPage;

    protected String loanId;

    protected WebMarkupBlock repaymentStrategyBlock;
    protected WebMarkupContainer repaymentStrategyVContainer;
    protected Option repaymentStrategyValue;
    protected ReadOnlyView repaymentStrategyView;

    protected WebMarkupBlock repaymentBlock;
    protected WebMarkupContainer repaymentVContainer;
    protected String repaymentValue;
    protected ReadOnlyView repaymentView;

    protected WebMarkupBlock amortizationBlock;
    protected WebMarkupContainer amortizationVContainer;
    protected Option amortizationValue;
    protected ReadOnlyView amortizationView;

    protected WebMarkupBlock interestBlock;
    protected WebMarkupContainer interestVContainer;
    protected String interestValue;
    protected ReadOnlyView interestView;

    protected WebMarkupBlock graceOnPrinciplePaymentBlock;
    protected WebMarkupContainer graceOnPrinciplePaymentVContainer;
    protected Double graceOnPrinciplePaymentValue;
    protected ReadOnlyView graceOnPrinciplePaymentView;

    protected WebMarkupBlock graceOnInterestPaymentBlock;
    protected WebMarkupContainer graceOnInterestPaymentVContainer;
    protected Double graceOnInterestPaymentValue;
    protected ReadOnlyView graceOnInterestPaymentView;

    protected WebMarkupBlock graceOnArrearAgeingBlock;
    protected WebMarkupContainer graceOnArrearAgeingVContainer;
    protected Double graceOnArrearAgeingValue;
    protected ReadOnlyView graceOnArrearAgeingView;

    protected WebMarkupBlock fundSourceBlock;
    protected WebMarkupContainer fundSourceVContainer;
    protected String fundSourceValue;
    protected ReadOnlyView fundSourceView;

    protected WebMarkupBlock interestFreePeriodBlock;
    protected WebMarkupContainer interestFreePeriodVContainer;
    protected Double interestFreePeriodValue;
    protected ReadOnlyView interestFreePeriodView;

    protected WebMarkupBlock interestCalculationPeriodBlock;
    protected WebMarkupContainer interestCalculationPeriodVContainer;
    protected Option interestCalculationPeriodValue;
    protected ReadOnlyView interestCalculationPeriodView;

    protected WebMarkupBlock allowPartialInterestCalcualtionBlock;
    protected WebMarkupContainer allowPartialInterestCalcualtionVContainer;
    protected Boolean allowPartialInterestCalcualtionValue;
    protected ReadOnlyView allowPartialInterestCalcualtionView;

    protected WebMarkupBlock interestTypeBlock;
    protected WebMarkupContainer interestTypeVContainer;
    protected Option interestTypeValue;
    protected ReadOnlyView interestTypeView;

    protected WebMarkupBlock recalculateInterestBlock;
    protected WebMarkupContainer recalculateInterestVContainer;
    protected Boolean recalculateInterestValue;
    protected ReadOnlyView recalculateInterestView;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnVContainer;
    protected Date submittedOnValue;
    protected ReadOnlyView submittedOnView;

    protected WebMarkupBlock approvedOnBlock;
    protected WebMarkupContainer approvedOnVContainer;
    protected Date approvedOnValue;
    protected ReadOnlyView approvedOnView;

    protected WebMarkupBlock disbursedOnBlock;
    protected WebMarkupContainer disbursedOnVContainer;
    protected Date disbursedOnValue;
    protected ReadOnlyView disbursedOnView;

    protected WebMarkupBlock matureOnBlock;
    protected WebMarkupContainer matureOnVContainer;
    protected Date matureOnValue;
    protected ReadOnlyView matureOnView;

    protected WebMarkupBlock dayInMonthBlock;
    protected WebMarkupContainer dayInMonthVContainer;
    protected Option dayInMonthValue;
    protected ReadOnlyView dayInMonthView;

    protected WebMarkupBlock dayInYearBlock;
    protected WebMarkupContainer dayInYearVContainer;
    protected Option dayInYearValue;
    protected ReadOnlyView dayInYearView;

    public LoanAccountPreviewDetailPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.loanId = new PropertyModel<String>(this.itemPage, "loanId").getObject();

        SelectQuery loanAccoutDetailQuery = new SelectQuery("m_loan");
        loanAccoutDetailQuery.addJoin("left join m_fund on m_loan.fund_id = m_fund.id");
        loanAccoutDetailQuery.addJoin("left join m_calendar_instance on m_calendar_instance.entity_id = m_loan.id");
        loanAccoutDetailQuery.addJoin("left join m_calendar on m_calendar_instance.calendar_id = m_calendar.id");

        loanAccoutDetailQuery.addField("m_loan.loan_transaction_strategy_id");
        loanAccoutDetailQuery.addField("m_loan.number_of_repayments");
        loanAccoutDetailQuery.addField("m_loan.repay_every");
        loanAccoutDetailQuery.addField("m_loan.repayment_period_frequency_enum");
        loanAccoutDetailQuery.addField("m_loan.amortization_method_enum");
        loanAccoutDetailQuery.addField("m_loan.nominal_interest_rate_per_period");
        loanAccoutDetailQuery.addField("m_loan.interest_method_enum");
        loanAccoutDetailQuery.addField("m_loan.interest_period_frequency_enum");
        loanAccoutDetailQuery.addField("m_loan.grace_on_principal_periods");
        loanAccoutDetailQuery.addField("m_loan.grace_on_interest_periods");
        loanAccoutDetailQuery.addField("m_loan.grace_on_arrears_ageing");
        loanAccoutDetailQuery.addField("m_fund.name fund");
        loanAccoutDetailQuery.addField("m_loan.grace_interest_free_periods");
        loanAccoutDetailQuery.addField("m_loan.interest_calculated_in_period_enum");
        loanAccoutDetailQuery.addField("m_loan.allow_partial_period_interest_calcualtion");
        loanAccoutDetailQuery.addField("m_loan.submittedon_date");
        loanAccoutDetailQuery.addField("m_loan.approvedon_date");
        loanAccoutDetailQuery.addField("m_loan.disbursedon_date");
        loanAccoutDetailQuery.addField("m_loan.maturedon_date");
        loanAccoutDetailQuery.addField("m_loan.interest_recalculation_enabled");
        loanAccoutDetailQuery.addField("m_loan.days_in_year_enum");
        loanAccoutDetailQuery.addField("m_loan.days_in_month_enum");
        loanAccoutDetailQuery.addField("m_calendar.recurrence");

        loanAccoutDetailQuery.addWhere("m_loan.id = '" + this.loanId + "'");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> loanAccountDetailObject = jdbcTemplate.queryForMap(loanAccoutDetailQuery.toSQL());

        this.repaymentStrategyValue = RepaymentStrategy.optionLiteral(String.valueOf(loanAccountDetailObject.get("loan_transaction_strategy_id")));
        Long number_of_repayments = (Long) loanAccountDetailObject.get("number_of_repayments");
        Long repay_every = (Long) loanAccountDetailObject.get("repay_every");
        LockInType repayment_period_frequency_enum = LockInType.parseLiteral(String.valueOf(loanAccountDetailObject.get("repayment_period_frequency_enum")));
        String recurrence = (String) loanAccountDetailObject.get("recurrence");

        FrequencyType frequencyType = null;
        FrequencyDay frequencyDay = null;

        if (recurrence != null && !"".equals(recurrence)) {
            if (recurrence.contains("BYSETPOS")) {
                int b = recurrence.indexOf("BYSETPOS") + "BYSETPOS".length();
                int e = recurrence.indexOf(";", b);
                String temp = recurrence.substring(b + 1, e);
                frequencyType = FrequencyType.parseLiteral(temp);
            }
            if (recurrence.contains("BYDAY")) {
                int b = recurrence.indexOf("BYDAY") + "BYDAY".length();
                String temp = recurrence.substring(b + 1);
                if ("MO".equals(temp)) {
                    frequencyDay = FrequencyDay.Monday;
                } else if ("TU".equals(temp)) {
                    frequencyDay = FrequencyDay.Tuesday;
                } else if ("WE".equals(temp)) {
                    frequencyDay = FrequencyDay.Wednesday;
                } else if ("TH".equals(temp)) {
                    frequencyDay = FrequencyDay.Thursday;
                } else if ("FR".equals(temp)) {
                    frequencyDay = FrequencyDay.Friday;
                } else if ("SA".equals(temp)) {
                    frequencyDay = FrequencyDay.Saturday;
                } else if ("SU".equals(temp)) {
                    frequencyDay = FrequencyDay.Sunday;
                }
            }
        }

        Option amortization_method_enum = Amortization.optionLiteral(String.valueOf(loanAccountDetailObject.get("amortization_method_enum")));
        Double nominal_interest_rate_per_period = (Double) loanAccountDetailObject.get("nominal_interest_rate_per_period");
        NominalInterestRateType interest_period_frequency_enum = NominalInterestRateType.parseLiteral(String.valueOf(loanAccountDetailObject.get("interest_period_frequency_enum")));
        Option interest_method_enum = InterestMethod.optionLiteral(String.valueOf(loanAccountDetailObject.get("interest_method_enum")));
        Double grace_on_principal_periods = (Double) loanAccountDetailObject.get("grace_on_principal_periods");
        Double grace_on_interest_periods = (Double) loanAccountDetailObject.get("grace_on_interest_periods");
        Double grace_on_arrears_ageing = (Double) loanAccountDetailObject.get("grace_on_arrears_ageing");
        String fund = (String) loanAccountDetailObject.get("fund");
        Double grace_interest_free_periods = (Double) loanAccountDetailObject.get("grace_interest_free_periods");
        Option interest_calculated_in_period_enum = InterestCalculationPeriod.optionLiteral(String.valueOf(loanAccountDetailObject.get("interest_calculated_in_period_enum")));
        Boolean allow_partial_period_interest_calcualtion = (Boolean) loanAccountDetailObject.get("allow_partial_period_interest_calcualtion");
        Date submittedon_date = (Date) loanAccountDetailObject.get("submittedon_date");
        Date approvedon_date = (Date) loanAccountDetailObject.get("approvedon_date");
        Date disbursedon_date = (Date) loanAccountDetailObject.get("disbursedon_date");
        Date maturedon_date = (Date) loanAccountDetailObject.get("maturedon_date");
        Boolean interest_recalculation_enabled = (Long) loanAccountDetailObject.get("interest_recalculation_enabled") == 1 ? true : false;
        Option days_in_year_enum = DayInYear.optionLiteral(String.valueOf(loanAccountDetailObject.get("days_in_year_enum")));
        Option days_in_month_enum = DayInMonth.optionLiteral(String.valueOf(loanAccountDetailObject.get("days_in_month_enum")));

        if (frequencyType != null && frequencyDay != null) {
            this.repaymentValue = String.valueOf(number_of_repayments) + " every " + String.valueOf(repay_every) + "/" + repayment_period_frequency_enum.getDescription() + " On " + frequencyType.getDescription() + " " + frequencyDay.getDescription();
        } else {
            this.repaymentValue = String.valueOf(number_of_repayments) + " every " + String.valueOf(repay_every) + "/" + repayment_period_frequency_enum.getDescription();
        }

        this.amortizationValue = amortization_method_enum;

        this.interestValue = String.valueOf(nominal_interest_rate_per_period) + " " + StringUtils.lowerCase(interest_period_frequency_enum.getDescription()) + " - " + interest_method_enum.getText();
        this.graceOnPrinciplePaymentValue = grace_on_principal_periods;
        this.graceOnInterestPaymentValue = grace_on_interest_periods;
        this.graceOnArrearAgeingValue = grace_on_arrears_ageing;
        this.fundSourceValue = fund;
        this.interestFreePeriodValue = grace_interest_free_periods;
        this.interestCalculationPeriodValue = interest_calculated_in_period_enum;
        this.allowPartialInterestCalcualtionValue = allow_partial_period_interest_calcualtion;

        this.interestTypeValue = interest_method_enum;

        this.recalculateInterestValue = interest_recalculation_enabled;

        this.submittedOnValue = submittedon_date;
        this.approvedOnValue = approvedon_date;
        this.disbursedOnValue = disbursedon_date;
        this.matureOnValue = maturedon_date;

        this.dayInMonthValue = days_in_month_enum;
        this.dayInYearValue = days_in_year_enum;

    }

    @Override
    protected void initComponent() {
        this.repaymentStrategyBlock = new WebMarkupBlock("repaymentStrategyBlock", Size.Six_6);
        add(this.repaymentStrategyBlock);
        this.repaymentStrategyVContainer = new WebMarkupContainer("repaymentStrategyVContainer");
        this.repaymentStrategyBlock.add(this.repaymentStrategyVContainer);
        this.repaymentStrategyView = new ReadOnlyView("repaymentStrategyView", new PropertyModel<>(this, "repaymentStrategyValue"));
        this.repaymentStrategyVContainer.add(this.repaymentStrategyView);

        this.repaymentBlock = new WebMarkupBlock("repaymentBlock", Size.Six_6);
        add(this.repaymentBlock);
        this.repaymentVContainer = new WebMarkupContainer("repaymentVContainer");
        this.repaymentBlock.add(this.repaymentVContainer);
        this.repaymentView = new ReadOnlyView("repaymentView", new PropertyModel<>(this, "repaymentValue"));
        this.repaymentVContainer.add(this.repaymentView);

        this.amortizationBlock = new WebMarkupBlock("amortizationBlock", Size.Six_6);
        add(this.amortizationBlock);
        this.amortizationVContainer = new WebMarkupContainer("amortizationVContainer");
        this.amortizationBlock.add(this.amortizationVContainer);
        this.amortizationView = new ReadOnlyView("amortizationView", new PropertyModel<>(this, "amortizationValue"));
        this.amortizationVContainer.add(this.amortizationView);

        this.interestBlock = new WebMarkupBlock("interestBlock", Size.Six_6);
        add(this.interestBlock);
        this.interestVContainer = new WebMarkupContainer("interestVContainer");
        this.interestBlock.add(this.interestVContainer);
        this.interestView = new ReadOnlyView("interestView", new PropertyModel<>(this, "interestValue"));
        this.interestVContainer.add(this.interestView);

        this.graceOnPrinciplePaymentBlock = new WebMarkupBlock("graceOnPrinciplePaymentBlock", Size.Four_4);
        add(this.graceOnPrinciplePaymentBlock);
        this.graceOnPrinciplePaymentVContainer = new WebMarkupContainer("graceOnPrinciplePaymentVContainer");
        this.graceOnPrinciplePaymentBlock.add(this.graceOnPrinciplePaymentVContainer);
        this.graceOnPrinciplePaymentView = new ReadOnlyView("graceOnPrinciplePaymentView", new PropertyModel<>(this, "graceOnPrinciplePaymentValue"));
        this.graceOnPrinciplePaymentVContainer.add(this.graceOnPrinciplePaymentView);

        this.graceOnInterestPaymentBlock = new WebMarkupBlock("graceOnInterestPaymentBlock", Size.Four_4);
        add(this.graceOnInterestPaymentBlock);
        this.graceOnInterestPaymentVContainer = new WebMarkupContainer("graceOnInterestPaymentVContainer");
        this.graceOnInterestPaymentBlock.add(this.graceOnInterestPaymentVContainer);
        this.graceOnInterestPaymentView = new ReadOnlyView("graceOnInterestPaymentView", new PropertyModel<>(this, "graceOnInterestPaymentValue"));
        this.graceOnInterestPaymentVContainer.add(this.graceOnInterestPaymentView);

        this.graceOnArrearAgeingBlock = new WebMarkupBlock("graceOnArrearAgeingBlock", Size.Four_4);
        add(this.graceOnArrearAgeingBlock);
        this.graceOnArrearAgeingVContainer = new WebMarkupContainer("graceOnArrearAgeingVContainer");
        this.graceOnArrearAgeingBlock.add(this.graceOnArrearAgeingVContainer);
        this.graceOnArrearAgeingView = new ReadOnlyView("graceOnArrearAgeingView", new PropertyModel<>(this, "graceOnArrearAgeingValue"));
        this.graceOnArrearAgeingVContainer.add(this.graceOnArrearAgeingView);

        this.fundSourceBlock = new WebMarkupBlock("fundSourceBlock", Size.Six_6);
        add(this.fundSourceBlock);
        this.fundSourceVContainer = new WebMarkupContainer("fundSourceVContainer");
        this.fundSourceBlock.add(this.fundSourceVContainer);
        this.fundSourceView = new ReadOnlyView("fundSourceView", new PropertyModel<>(this, "fundSourceValue"));
        this.fundSourceVContainer.add(this.fundSourceView);

        this.interestFreePeriodBlock = new WebMarkupBlock("interestFreePeriodBlock", Size.Six_6);
        add(this.interestFreePeriodBlock);
        this.interestFreePeriodVContainer = new WebMarkupContainer("interestFreePeriodVContainer");
        this.interestFreePeriodBlock.add(this.interestFreePeriodVContainer);
        this.interestFreePeriodView = new ReadOnlyView("interestFreePeriodView", new PropertyModel<>(this, "interestFreePeriodValue"));
        this.interestFreePeriodVContainer.add(this.interestFreePeriodView);

        this.interestCalculationPeriodBlock = new WebMarkupBlock("interestCalculationPeriodBlock", Size.Six_6);
        add(this.interestCalculationPeriodBlock);
        this.interestCalculationPeriodVContainer = new WebMarkupContainer("interestCalculationPeriodVContainer");
        this.interestCalculationPeriodBlock.add(this.interestCalculationPeriodVContainer);
        this.interestCalculationPeriodView = new ReadOnlyView("interestCalculationPeriodView", new PropertyModel<>(this, "interestCalculationPeriodValue"));
        this.interestCalculationPeriodVContainer.add(this.interestCalculationPeriodView);

        this.allowPartialInterestCalcualtionBlock = new WebMarkupBlock("allowPartialInterestCalcualtionBlock", Size.Six_6);
        add(this.allowPartialInterestCalcualtionBlock);
        this.allowPartialInterestCalcualtionVContainer = new WebMarkupContainer("allowPartialInterestCalcualtionVContainer");
        this.allowPartialInterestCalcualtionBlock.add(this.allowPartialInterestCalcualtionVContainer);
        this.allowPartialInterestCalcualtionView = new ReadOnlyView("allowPartialInterestCalcualtionView", new PropertyModel<>(this, "allowPartialInterestCalcualtionValue"));
        this.allowPartialInterestCalcualtionVContainer.add(this.allowPartialInterestCalcualtionView);

        this.interestTypeBlock = new WebMarkupBlock("interestTypeBlock", Size.Six_6);
        add(this.interestTypeBlock);
        this.interestTypeVContainer = new WebMarkupContainer("interestTypeVContainer");
        this.interestTypeBlock.add(this.interestTypeVContainer);
        this.interestTypeView = new ReadOnlyView("interestTypeView", new PropertyModel<>(this, "interestTypeValue"));
        this.interestTypeVContainer.add(this.interestTypeView);

        this.recalculateInterestBlock = new WebMarkupBlock("recalculateInterestBlock", Size.Six_6);
        add(this.recalculateInterestBlock);
        this.recalculateInterestVContainer = new WebMarkupContainer("recalculateInterestVContainer");
        this.recalculateInterestBlock.add(this.recalculateInterestVContainer);
        this.recalculateInterestView = new ReadOnlyView("recalculateInterestView", new PropertyModel<>(this, "recalculateInterestValue"));
        this.recalculateInterestVContainer.add(this.recalculateInterestView);

        this.submittedOnBlock = new WebMarkupBlock("submittedOnBlock", Size.Three_3);
        add(this.submittedOnBlock);
        this.submittedOnVContainer = new WebMarkupContainer("submittedOnVContainer");
        this.submittedOnBlock.add(this.submittedOnVContainer);
        this.submittedOnView = new ReadOnlyView("submittedOnView", new PropertyModel<>(this, "submittedOnValue"), "yyyy-MM-dd");
        this.submittedOnVContainer.add(this.submittedOnView);

        this.approvedOnBlock = new WebMarkupBlock("approvedOnBlock", Size.Three_3);
        add(this.approvedOnBlock);
        this.approvedOnVContainer = new WebMarkupContainer("approvedOnVContainer");
        this.approvedOnBlock.add(this.approvedOnVContainer);
        this.approvedOnView = new ReadOnlyView("approvedOnView", new PropertyModel<>(this, "approvedOnValue"), "yyyy-MM-dd");
        this.approvedOnVContainer.add(this.approvedOnView);

        this.disbursedOnBlock = new WebMarkupBlock("disbursedOnBlock", Size.Three_3);
        add(this.disbursedOnBlock);
        this.disbursedOnVContainer = new WebMarkupContainer("disbursedOnVContainer");
        this.disbursedOnBlock.add(this.disbursedOnVContainer);
        this.disbursedOnView = new ReadOnlyView("disbursedOnView", new PropertyModel<>(this, "disbursedOnValue"), "yyyy-MM-dd");
        this.disbursedOnVContainer.add(this.disbursedOnView);

        this.matureOnBlock = new WebMarkupBlock("matureOnBlock", Size.Three_3);
        add(this.matureOnBlock);
        this.matureOnVContainer = new WebMarkupContainer("matureOnVContainer");
        this.matureOnBlock.add(this.matureOnVContainer);
        this.matureOnView = new ReadOnlyView("matureOnView", new PropertyModel<>(this, "matureOnValue"), "yyyy-MM-dd");
        this.matureOnVContainer.add(this.matureOnView);

        this.dayInMonthBlock = new WebMarkupBlock("dayInMonthBlock", Size.Six_6);
        add(this.dayInMonthBlock);
        this.dayInMonthVContainer = new WebMarkupContainer("dayInMonthVContainer");
        this.dayInMonthBlock.add(this.dayInMonthVContainer);
        this.dayInMonthView = new ReadOnlyView("dayInMonthView", new PropertyModel<>(this, "dayInMonthValue"));
        this.dayInMonthVContainer.add(this.dayInMonthView);

        this.dayInYearBlock = new WebMarkupBlock("dayInYearBlock", Size.Six_6);
        add(this.dayInYearBlock);
        this.dayInYearVContainer = new WebMarkupContainer("dayInYearVContainer");
        this.dayInYearBlock.add(this.dayInYearVContainer);
        this.dayInYearView = new ReadOnlyView("dayInYearView", new PropertyModel<>(this, "dayInYearValue"));
        this.dayInYearVContainer.add(this.dayInYearView);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
