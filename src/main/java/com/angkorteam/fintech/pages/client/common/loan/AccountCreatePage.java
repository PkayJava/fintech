package com.angkorteam.fintech.pages.client.common.loan;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MFloatingRates;
import com.angkorteam.fintech.ddl.MFund;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MProductLoan;
import com.angkorteam.fintech.ddl.MProductLoanCharge;
import com.angkorteam.fintech.ddl.MProductLoanFloatingRates;
import com.angkorteam.fintech.ddl.MProductLoanGuaranteeDetails;
import com.angkorteam.fintech.ddl.MProductLoanRecalculationDetails;
import com.angkorteam.fintech.ddl.MProductLoanVariableInstallmentConfig;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.loan.AdvancePaymentsAdjustmentType;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.ClosureInterestCalculationRule;
import com.angkorteam.fintech.dto.enums.loan.Frequency;
import com.angkorteam.fintech.dto.enums.loan.FrequencyDay;
import com.angkorteam.fintech.dto.enums.loan.FrequencyType;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.InterestRecalculationCompound;
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.client.common.loan.Details;
import com.angkorteam.fintech.widget.client.common.loan.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AccountCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_TERM = 1;
    public static int TAB_CHARGE = 2;
    public static int TAB_REVIEW = 3;

    protected ClientEnum client;

    protected String clientId;
    protected String officeId;
    protected String loanId;
    protected String clientDisplayName;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail;
    protected boolean errorTerm;
    protected boolean errorCharge;

    // Details

    protected String detailProductValue;
    protected Option detailLoanOfficerValue;
    protected Option detailLoanPurposeValue;
    protected Option detailFundValue;
    protected Date detailSubmittedOnValue;
    protected Date detailDisbursementOnValue;
    protected String detailExternalIdValue;
    protected Option detailLinkSavingValue;
    protected Boolean detailCreateStandingInstructionAtDisbursementValue;

    // Terms

    protected Double termPrincipalValue;
    protected Long termLoanTermValue;
    protected Option termLoanTermTypeValue;
    protected Long termNumberOfRepaymentValue;
    protected Long termRepaidEveryValue;
    protected Option termRepaidTypeValue;
    protected Option termRepaidOnValue;
    protected Option termRepaidDayValue;
    protected Date termFirstRepaymentOnValue;
    protected Date termInterestChargedFromValue;
    protected Double termNominalInterestRateValue;
    protected Option termInterestMethodValue;
    protected Boolean termEqualAmortizationValue;
    protected Option termAmortizationValue;
    protected Option termInterestCalculationPeriodValue;
    protected Boolean termCalculateInterestForExactDayInPartialPeriodValue;
    protected Double termArrearsToleranceValue;
    protected Double termInterestFreePeriodValue;
    protected Option termRepaymentStrategyValue;
    protected Long termPrincipalPaymentValue;
    protected Long termInterestPaymentValue;
    protected Long termArrearsAgingValue;
    protected Double termInstallmentAmountValue;
    protected Boolean termTopupLoanValue;
    protected Option termLoanToCloseValue;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            if (this.client == ClientEnum.Client) {
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            parameters.add("client", this.client.name());
            breadcrumb.setLabel("Loan Selection");
            breadcrumb.setPage(AccountSelectionPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.client = ClientEnum.valueOf(parameters.get("client").toString());
        this.clientId = parameters.get("clientId").toString();
        this.loanId = parameters.get("loanId").toString();

        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        // this.popupModel = Maps.newHashMap();
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MProductLoan.NAME);
        selectQuery.addJoin("LEFT JOIN " + MFund.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.FUND_ID + " = " + MFund.NAME + "." + MFund.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MOrganisationCurrency.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_CODE + " = " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanFloatingRates.NAME + " ON " + MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.LOAN_PRODUCT_ID + " = " + MProductLoan.NAME + "." + MProductLoan.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MFloatingRates.NAME + " ON " + MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.FLOATING_RATES_ID + " = " + MFloatingRates.NAME + "." + MFloatingRates.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanVariableInstallmentConfig.NAME + " ON " + MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.LOAN_PRODUCT_ID + " = " + MProductLoan.NAME + "." + MProductLoan.Field.ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanRecalculationDetails.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.PRODUCT_ID);
        selectQuery.addJoin("LEFT JOIN " + MProductLoanGuaranteeDetails.NAME + " ON " + MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.LOAN_PRODUCT_ID);
        selectQuery.addWhere(MProductLoan.NAME + "." + MProductLoan.Field.ID + " = " + this.loanId);

        // detail section
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.NAME);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DESCRIPTION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.SHORT_NAME);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.START_DATE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CLOSE_DATE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INCLUDE_IN_BORROWER_CYCLE);
        selectQuery.addField(MFund.NAME + "." + MFund.Field.ID + " fund_id");

        // currency
        selectQuery.addField(MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE + " currency_code");
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_DIGITS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INSTALMENT_AMOUNT_IN_MULTIPLES_OF);

        // Terms
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.USE_BORROWER_CYCLE);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_PRINCIPAL_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.PRINCIPAL_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_PRINCIPAL_AMOUNT);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_NUMBER_OF_REPAYMENTS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.REPAY_EVERY);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.REPAYMENT_PERIOD_FREQUENCY_ENUM);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_NOMINAL_INTEREST_RATE_PER_PERIOD);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_PERIOD_FREQUENCY_ENUM);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.IS_LINKED_TO_FLOATING_INTEREST_RATES);

        selectQuery.addField(MFloatingRates.NAME + "." + MFloatingRates.Field.NAME + " floating_rate");
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.INTEREST_RATE_DIFFERENTIAL);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.IS_FLOATING_INTEREST_RATE_CALCULATION_ALLOWED);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.MIN_DIFFERENTIAL_LENDING_RATE);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.DEFAULT_DIFFERENTIAL_LENDING_RATE);
        selectQuery.addField(MProductLoanFloatingRates.NAME + "." + MProductLoanFloatingRates.Field.MAX_DIFFERENTIAL_LENDING_RATE);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MIN_DAYS_BETWEEN_DISBURSAL_AND_FIRST_REPAYMENT);

        // setting
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.AMORTIZATION_METHOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_METHOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_CALCULATED_IN_PERIOD_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ARREARS_TOLERANCE_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.LOAN_TRANSACTION_STRATEGY_ID);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_INTEREST_FREE_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_ARREARS_AGEING);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_INTEREST_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.GRACE_ON_PRINCIPAL_PERIODS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ACCOUNT_MOVES_OUT_OF_NPA_ONLY_ON_ARREARS_COMPLETION);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.OVERDUE_DAYS_FOR_NPA);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DAYS_IN_YEAR_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.DAYS_IN_MONTH_ENUM);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.PRINCIPAL_THRESHOLD_FOR_LAST_INSTALLMENT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CAN_DEFINE_FIXED_EMI_AMOUNT);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.CAN_USE_FOR_TOPUP);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_VARIABE_INSTALLMENTS);

        selectQuery.addField(MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.MINIMUM_GAP);
        selectQuery.addField(MProductLoanVariableInstallmentConfig.NAME + "." + MProductLoanVariableInstallmentConfig.Field.MAXIMUM_GAP);

        // re-calculation
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.INTEREST_RECALCULATION_ENABLED);

        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.RESCHEDULE_STRATEGY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_INTERVAL);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.ARREARS_BASED_ON_ORIGINAL_SCHEDULE);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.PRE_CLOSE_INTEREST_CALCULATION_STRATEGY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_INTERVAL);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_NTH_DAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_ON_DAY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.REST_FREQUENCY_WEEKDAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_NTH_DAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_ON_DAY);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUND_TYPE_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.COMPOUNDING_FREQUENCY_WEEKDAY_ENUM);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.IS_COMPOUNDING_TO_BE_POSTED_AS_TRANSACTION);
        selectQuery.addField(MProductLoanRecalculationDetails.NAME + "." + MProductLoanRecalculationDetails.Field.ALLOW_COMPOUNDING_ON_EOD);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.HOLD_GUARANTEE_FUNDS);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MANDATORY_GUARANTEE);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MINIMUM_GUARANTEE_FROM_GUARANTOR_FUNDS);
        selectQuery.addField(MProductLoanGuaranteeDetails.NAME + "." + MProductLoanGuaranteeDetails.Field.MINIMUM_GUARANTEE_FROM_OWN_FUNDS);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ALLOW_MULTIPLE_DISBURSALS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_DISBURSALS);
        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.MAX_OUTSTANDING_LOAN_BALANCE);

        selectQuery.addField(MProductLoan.NAME + "." + MProductLoan.Field.ACCOUNTING_TYPE);

        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
            this.officeId = String.valueOf(clientObject.get("office_id"));
        } else if (this.client == ClientEnum.Group || this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.clientId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) groupObject.get("display_name");
            this.officeId = String.valueOf(groupObject.get("office_id"));
        }

        this.detailProductValue = (String) loanObject.get("name");

        selectQuery = new SelectQuery(MFund.NAME);
        selectQuery.addField(MFund.Field.ID);
        selectQuery.addField(MFund.Field.NAME + " as text");
        selectQuery.addWhere(MFund.Field.ID + " = :" + MFund.Field.ID, loanObject.get("fund_id"));
        this.detailFundValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.detailSubmittedOnValue = DateTime.now().toDate();
        this.detailExternalIdValue = generator.externalId();
        this.detailDisbursementOnValue = DateTime.now().toDate();

//        this.currencyValue = (String) loanObject.get("currency_code");
//        this.decimalPlacesValue = (Long) loanObject.get("currency_digits");

//        this.currencyInMultiplesOfValue = (Long) loanObject.get("currency_multiplesof");
//        this.installmentInMultiplesOfValue = (Double) loanObject.get("instalment_amount_in_multiples_of");

//        this.principleValue = (Double) loanObject.get("principal_amount");

//        this.numberOfRepaymentValue = (Long) loanObject.get("number_of_repayments");
//        this.repaidEveryValue = (Long) loanObject.get("repay_every");

//        if (this.numberOfRepaymentValue != null && this.repaidEveryValue != null) {
//            this.loanTermValue = this.numberOfRepaymentValue * this.repaidEveryValue;
//        }

        ChargeFrequency chargeFrequency = ChargeFrequency.parseLiteral(String.valueOf(loanObject.get("repayment_period_frequency_enum")));

//        this.loanTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

//        this.repaidTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

//        this.firstRepaymentOnValue = DateTime.now().toDate();
//        this.interestChargedFromValue = DateTime.now().toDate();

//        this.nominalInterestRateValue = (Double) loanObject.get("nominal_interest_rate_per_period");

        NominalInterestRateType nominalInterestTypeValue = NominalInterestRateType.parseLiteral(String.valueOf(loanObject.get("interest_period_frequency_enum")));
//        this.nominalInterestTypeValue = nominalInterestTypeValue == null ? "" : nominalInterestTypeValue.getDescription();

//        this.interestMethodValue = InterestMethod.optionLiteral(String.valueOf(loanObject.get("interest_method_enum")));

//        this.amortizationValue = Amortization.optionLiteral(String.valueOf(loanObject.get("amortization_method_enum")));

//        this.interestCalculationPeriodValue = InterestCalculationPeriod.optionLiteral(String.valueOf(loanObject.get("interest_calculated_in_period_enum")));

//        this.calculateInterestForExactDayInPartialPeriodValue = (Boolean) loanObject.get("allow_partial_period_interest_calcualtion");

//        this.repaymentStrategyValue = RepaymentStrategy.optionLiteral(String.valueOf(loanObject.get("loan_transaction_strategy_id")));

//        this.arrearsToleranceValue = (Double) loanObject.get("arrearstolerance_amount");

//        this.interestFreePeriodValue = (Long) loanObject.get("grace_interest_free_periods");

//        this.onArrearsAgingValue = (Long) loanObject.get("grace_on_arrears_ageing");
//        this.onInterestPaymentValue = (Long) loanObject.get("grace_on_interest_periods");
//        this.onPrinciplePaymentValue = (Long) loanObject.get("grace_on_principal_periods");

        Long interest_recalculation_enabled = (Long) loanObject.get("interest_recalculation_enabled");
//        this.interestRecalculationRecalculateInterestValue = interest_recalculation_enabled == null ? null : interest_recalculation_enabled == 1;

//        if (this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue) {
//            this.interestRecalculationPreClosureInterestCalculationRuleValue = ClosureInterestCalculationRule.optionLiteral(String.valueOf(loanObject.get("pre_close_interest_calculation_strategy")));
//            this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = (Boolean) loanObject.get("arrears_based_on_original_schedule");
//            this.interestRecalculationAdvancePaymentsAdjustmentTypeValue = AdvancePaymentsAdjustmentType.optionLiteral(String.valueOf(loanObject.get("reschedule_strategy_enum")));
//            this.interestRecalculationCompoundingOnValue = InterestRecalculationCompound.optionLiteral(String.valueOf(loanObject.get("compound_type_enum")));
//
//            FrequencyType compounding_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("compounding_frequency_nth_day_enum")));
//            if (compounding_frequency_nth_day_enum != null) {
//                this.interestRecalculationCompoundingTypeValue = compounding_frequency_nth_day_enum.toOption();
//                if (compounding_frequency_nth_day_enum == FrequencyType.OnDay) {
//                    this.interestRecalculationCompoundingOnDayValue = (Long) loanObject.get("compounding_frequency_on_day");
//                    this.interestRecalculationCompoundingOnDayVisible = true;
//                } else {
//                    this.interestRecalculationCompoundingDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_weekday_enum")));
//                    this.interestRecalculationCompoundingDayVisible = true;
//                }
//            }
//            this.interestRecalculationCompoundingValue = Frequency.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_type_enum")));
//            this.interestRecalculationCompoundingIntervalValue = (Long) loanObject.get("compounding_frequency_interval");
//
//            FrequencyType rest_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("rest_frequency_nth_day_enum")));
//            if (rest_frequency_nth_day_enum != null) {
//                this.interestRecalculationRecalculateTypeValue = rest_frequency_nth_day_enum.toOption();
//                if (rest_frequency_nth_day_enum == FrequencyType.OnDay) {
//                    this.interestRecalculationRecalculateOnDayValue = (Long) loanObject.get("rest_frequency_on_day");
//                    this.interestRecalculationRecalculateOnDayVisible = true;
//                } else {
//                    this.interestRecalculationRecalculateDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("rest_frequency_weekday_enum")));
//                    this.interestRecalculationRecalculateDayVisible = true;
//                }
//            }
//            this.interestRecalculationRecalculateValue = Frequency.optionLiteral(String.valueOf(loanObject.get("rest_frequency_type_enum")));
//            this.interestRecalculationRecalculateIntervalValue = (Long) loanObject.get("rest_frequency_interval");
//
//            // query.addField("m_product_loan_recalculation_details.is_compounding_to_be_posted_as_transaction");
//            // query.addField("m_product_loan_recalculation_details.allow_compounding_on_eod");
//        }
//
//        selectQuery = new SelectQuery(MCharge.NAME);
//        selectQuery.addJoin("INNER JOIN " + MProductLoanCharge.NAME + " ON " + MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.NAME);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.ID);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_PAYMENT_MODE_ENUM);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_PENALTY);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_ACTIVE);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MIN_CAP);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MAX_CAP);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_FREQUENCY);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.INCOME_OR_LIABILITY_ACCOUNT_ID);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.TAX_GROUP_ID);
//        selectQuery.addWhere(MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.PRODUCT_LOAN_ID + " = '" + this.loanId + "'");
//
//        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//
//        for (Map<String, Object> chargeObject : chargeObjects) {
//            Boolean is_penalty = (Boolean) chargeObject.get("is_penalty");
//            Map<String, Object> charge = new HashMap<>();
//            charge.put("name", chargeObject.get("name"));
//            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
//            charge.put("type", type);
//            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
//            charge.put("collect", collect);
//            charge.put("amount", chargeObject.get("amount"));
//            if (is_penalty != null && is_penalty) {
//                this.overdueChargeValue.add(charge);
//            } else {
//                Map<String, Object> popupModel = Maps.newHashMap();
//
//                popupModel.put("chargeTime", chargeObject.get("charge_time_enum"));
//                Long collectedOn = (Long) chargeObject.get("charge_time_enum");
//                if (collectedOn != null) {
//                    popupModel.put("collectedOnValue", ChargeTime.optionLiteral(String.valueOf(collectedOn)));
//                } else {
//                    popupModel.put("collectedOnValue", null);
//                }
//
//                Double amount = (Double) chargeObject.get("amount");
//                if (amount != null) {
//                    popupModel.put("amountValue", amount);
//                } else {
//                    popupModel.put("amountValue", null);
//                }
//
//                Long charge_calculation_enum = (Long) chargeObject.get("charge_calculation_enum");
//                if (type != null) {
//                    popupModel.put("chargeTypeValue", ChargeCalculation.optionLiteral(String.valueOf(charge_calculation_enum)));
//                } else {
//                    popupModel.put("chargeTypeValue", null);
//                }
//
//                Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
//                popupModel.put("repaymentEveryValue", repaymentEveryValue);
//
//                Long month = (Long) chargeObject.get("fee_on_month");
//                Long day = (Long) chargeObject.get("fee_on_day");
//                if (day != null && month != null) {
//                    try {
//                        popupModel.put("dayMonthValue", DateUtils.parseDate(day + "/" + month, "d/M"));
//                    } catch (ParseException e) {
//                    }
//                }
//
//                popupModel.put("chargeValue", new Option(String.valueOf(chargeObject.get("id")), (String) chargeObject.get("name")));
//
//                Map<String, Object> item = Maps.newHashMap();
//                item.put("uuid", UUID.randomUUID().toString());
//                item.put("charge", popupModel.get("chargeValue"));
//                item.put("chargeTime", popupModel.get("chargeTime"));
//                item.put("amount", popupModel.get("amountValue"));
//                item.put("date", popupModel.get("dateValue"));
//                item.put("dayMonth", popupModel.get("dayMonthValue"));
//                item.put("repaymentEvery", popupModel.get("repaymentEveryValue"));
//                item.put("type", popupModel.get("chargeTypeValue"));
//                item.put("name", popupModel.get("chargeValue"));
//                item.put("collectedOn", popupModel.get("collectedOnValue"));
//
//                this.chargeValue.add(item);
//            }
//        }
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Terms(this)));
        add(this.tab);
    }

    @Override
    protected void configureMetaData() {
    }

    public void saveButtonSubmit(Button button) {

    }

}
