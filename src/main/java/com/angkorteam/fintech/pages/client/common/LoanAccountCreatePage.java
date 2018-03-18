package com.angkorteam.fintech.pages.client.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
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
import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.LoanAccountBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ProductPopup;
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
import com.angkorteam.fintech.dto.enums.loan.RepaidOn;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.popup.AccountChargePopup;
import com.angkorteam.fintech.popup.CollateralPopup;
import com.angkorteam.fintech.provider.ChargeFrequencyProvider;
import com.angkorteam.fintech.provider.FundProvider;
import com.angkorteam.fintech.provider.LoanPurposeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
import com.angkorteam.fintech.provider.loan.RepaidOnProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String clientDisplayName;
    protected String centerDisplayName;
    protected String groupDisplayName;

    protected String loanId;
    protected String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock productBlock;
    protected WebMarkupContainer productVContainer;
    protected String productValue;
    protected ReadOnlyView productView;

    protected SingleChoiceProvider loanOfficerProvider;
    protected WebMarkupBlock loanOfficerBlock;
    protected WebMarkupContainer loanOfficerIContainer;
    protected Option loanOfficerValue;
    protected Select2SingleChoice<Option> loanOfficerField;
    protected TextFeedbackPanel loanOfficerFeedback;

    protected LoanPurposeProvider loanPurposeProvider;
    protected WebMarkupContainer loanPurposeBlock;
    protected WebMarkupContainer loanPurposeIContainer;
    protected Option loanPurposeValue;
    protected Select2SingleChoice<Option> loanPurposeField;
    protected TextFeedbackPanel loanPurposeFeedback;

    protected FundProvider fundProvider;
    protected WebMarkupContainer fundBlock;
    protected WebMarkupContainer fundIContainer;
    protected Option fundValue;
    protected Select2SingleChoice<Option> fundField;
    protected TextFeedbackPanel fundFeedback;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected WebMarkupBlock disbursementOnBlock;
    protected WebMarkupContainer disbursementOnIContainer;
    protected Date disbursementOnValue;
    protected DateTextField disbursementOnField;
    protected TextFeedbackPanel disbursementOnFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected SingleChoiceProvider linkSavingAccountProvider;
    protected WebMarkupBlock linkSavingAccountBlock;
    protected WebMarkupContainer linkSavingAccountIContainer;
    protected Option linkSavingAccountValue;
    protected Select2SingleChoice<Option> linkSavingAccountField;
    protected TextFeedbackPanel linkSavingAccountFeedback;

    protected WebMarkupBlock createStandingInstructionAtDisbursementBlock;
    protected WebMarkupContainer createStandingInstructionAtDisbursementIContainer;
    protected Boolean createStandingInstructionAtDisbursementValue;
    protected CheckBox createStandingInstructionAtDisbursementField;
    protected TextFeedbackPanel createStandingInstructionAtDisbursementFeedback;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyVContainer;
    protected String currencyValue;
    protected ReadOnlyView currencyView;

    protected WebMarkupBlock decimalPlacesBlock;
    protected WebMarkupContainer decimalPlacesVContainer;
    protected Long decimalPlacesValue;
    protected ReadOnlyView decimalPlacesView;

    protected WebMarkupBlock currencyInMultiplesOfBlock;
    protected WebMarkupContainer currencyInMultiplesOfVContainer;
    protected Long currencyInMultiplesOfValue;
    protected ReadOnlyView currencyInMultiplesOfView;

    protected WebMarkupBlock installmentInMultiplesOfBlock;
    protected WebMarkupContainer installmentInMultiplesOfVContainer;
    protected Double installmentInMultiplesOfValue;
    protected ReadOnlyView installmentInMultiplesOfView;

    protected WebMarkupBlock principleBlock;
    protected WebMarkupContainer principleIContainer;
    protected Double principleValue;
    protected TextField<Double> principleField;
    protected TextFeedbackPanel principleFeedback;

    protected WebMarkupBlock loanTermBlock;
    protected WebMarkupContainer loanTermIContainer;
    protected Long loanTermValue;
    protected TextField<Long> loanTermField;
    protected TextFeedbackPanel loanTermFeedback;

    protected ChargeFrequencyProvider loanTypeProvider;
    protected WebMarkupBlock loanTypeBlock;
    protected WebMarkupContainer loanTypeIContainer;
    protected Option loanTypeValue;
    protected Select2SingleChoice<Option> loanTypeField;
    protected TextFeedbackPanel loanTypeFeedback;

    protected WebMarkupBlock numberOfRepaymentBlock;
    protected WebMarkupContainer numberOfRepaymentIContainer;
    protected Long numberOfRepaymentValue;
    protected TextField<Long> numberOfRepaymentField;
    protected TextFeedbackPanel numberOfRepaymentFeedback;

    protected WebMarkupBlock installmentAmountBlock;
    protected WebMarkupContainer installmentAmountIContainer;
    protected Long installmentAmountValue;
    protected TextField<Double> installmentAmountField;
    protected TextFeedbackPanel installmentAmountFeedback;

    protected WebMarkupBlock repaidEveryBlock;
    protected WebMarkupContainer repaidEveryVContainer;
    protected Long repaidEveryValue;
    protected ReadOnlyView repaidEveryView;

    protected WebMarkupBlock repaidTypeBlock;
    protected WebMarkupContainer repaidTypeVContainer;
    protected Option repaidTypeValue;
    protected ReadOnlyView repaidTypeView;

    protected RepaidOnProvider repaidOnProvider;
    protected WebMarkupContainer repaidOnBlock;
    protected WebMarkupContainer repaidOnIContainer;
    protected Option repaidOnValue;
    protected Select2SingleChoice<Option> repaidOnField;
    protected TextFeedbackPanel repaidOnFeedback;

    protected FrequencyDayProvider repaidDayProvider;
    protected WebMarkupContainer repaidDayBlock;
    protected WebMarkupContainer repaidDayIContainer;
    protected Option repaidDayValue;
    protected Select2SingleChoice<Option> repaidDayField;
    protected TextFeedbackPanel repaidDayFeedback;

    protected WebMarkupContainer firstRepaymentOnBlock;
    protected WebMarkupContainer firstRepaymentOnIContainer;
    protected Date firstRepaymentOnValue;
    protected DateTextField firstRepaymentOnField;
    protected TextFeedbackPanel firstRepaymentOnFeedback;

    protected WebMarkupContainer interestChargedFromBlock;
    protected WebMarkupContainer interestChargedFromIContainer;
    protected Date interestChargedFromValue;
    protected DateTextField interestChargedFromField;
    protected TextFeedbackPanel interestChargedFromFeedback;

    protected WebMarkupContainer nominalInterestRateBlock;
    protected WebMarkupContainer nominalInterestRateIContainer;
    protected Double nominalInterestRateValue;
    protected TextField<Double> nominalInterestRateField;
    protected TextFeedbackPanel nominalInterestRateFeedback;

    protected WebMarkupBlock nominalInterestTypeBlock;
    protected WebMarkupContainer nominalInterestTypeVContainer;
    protected String nominalInterestTypeValue;
    protected ReadOnlyView nominalInterestTypeView;

    protected WebMarkupBlock interestMethodBlock;
    protected WebMarkupContainer interestMethodVContainer;
    protected Option interestMethodValue;
    protected ReadOnlyView interestMethodView;

    protected WebMarkupBlock amortizationBlock;
    protected WebMarkupContainer amortizationVContainer;
    protected Option amortizationValue;
    protected ReadOnlyView amortizationView;

    protected WebMarkupBlock interestCalculationPeriodBlock;
    protected WebMarkupContainer interestCalculationPeriodVContainer;
    protected Option interestCalculationPeriodValue;
    protected ReadOnlyView interestCalculationPeriodView;

    protected WebMarkupBlock calculateInterestForExactDayInPartialPeriodBlock;
    protected WebMarkupContainer calculateInterestForExactDayInPartialPeriodVContainer;
    protected Boolean calculateInterestForExactDayInPartialPeriodValue;
    protected ReadOnlyView calculateInterestForExactDayInPartialPeriodView;

    protected WebMarkupBlock arrearsToleranceBlock;
    protected WebMarkupContainer arrearsToleranceVContainer;
    protected Double arrearsToleranceValue;
    protected ReadOnlyView arrearsToleranceView;

    protected WebMarkupContainer interestFreePeriodBlock;
    protected WebMarkupContainer interestFreePeriodIContainer;
    protected Long interestFreePeriodValue;
    protected TextField<Long> interestFreePeriodField;
    protected TextFeedbackPanel interestFreePeriodFeedback;

    protected WebMarkupBlock repaymentStrategyBlock;
    protected WebMarkupContainer repaymentStrategyVContainer;
    protected Option repaymentStrategyValue;
    protected ReadOnlyView repaymentStrategyView;

    protected WebMarkupBlock onPrinciplePaymentBlock;
    protected WebMarkupContainer onPrinciplePaymentVContainer;
    protected Long onPrinciplePaymentValue;
    protected ReadOnlyView onPrinciplePaymentView;

    protected WebMarkupBlock onInterestPaymentBlock;
    protected WebMarkupContainer onInterestPaymentVContainer;
    protected Long onInterestPaymentValue;
    protected ReadOnlyView onInterestPaymentView;

    protected WebMarkupBlock onArrearsAgingBlock;
    protected WebMarkupContainer onArrearsAgingVContainer;
    protected Long onArrearsAgingValue;
    protected ReadOnlyView onArrearsAgingView;

    protected WebMarkupBlock interestRecalculationRecalculateInterestBlock;
    protected WebMarkupContainer interestRecalculationRecalculateInterestVContainer;
    protected Boolean interestRecalculationRecalculateInterestValue;
    protected ReadOnlyView interestRecalculationRecalculateInterestView;

    protected WebMarkupBlock interestRecalculationPreClosureInterestCalculationRuleBlock;
    protected WebMarkupContainer interestRecalculationPreClosureInterestCalculationRuleVContainer;
    protected Option interestRecalculationPreClosureInterestCalculationRuleValue;
    protected ReadOnlyView interestRecalculationPreClosureInterestCalculationRuleView;

    protected WebMarkupBlock interestRecalculationAdvancePaymentsAdjustmentTypeBlock;
    protected WebMarkupContainer interestRecalculationAdvancePaymentsAdjustmentTypeVContainer;
    protected Option interestRecalculationAdvancePaymentsAdjustmentTypeValue;
    protected ReadOnlyView interestRecalculationAdvancePaymentsAdjustmentTypeView;

    protected WebMarkupBlock interestRecalculationCompoundingOnBlock;
    protected WebMarkupContainer interestRecalculationCompoundingOnVContainer;
    protected Option interestRecalculationCompoundingOnValue;
    protected ReadOnlyView interestRecalculationCompoundingOnView;

    protected WebMarkupBlock interestRecalculationCompoundingBlock;
    protected WebMarkupContainer interestRecalculationCompoundingVContainer;
    protected Option interestRecalculationCompoundingValue;
    protected ReadOnlyView interestRecalculationCompoundingView;

    protected WebMarkupBlock interestRecalculationCompoundingTypeBlock;
    protected WebMarkupContainer interestRecalculationCompoundingTypeVContainer;
    protected Option interestRecalculationCompoundingTypeValue;
    protected ReadOnlyView interestRecalculationCompoundingTypeView;

    protected WebMarkupBlock interestRecalculationCompoundingDayBlock;
    protected WebMarkupContainer interestRecalculationCompoundingDayVContainer;
    protected Option interestRecalculationCompoundingDayValue;
    protected ReadOnlyView interestRecalculationCompoundingDayView;
    protected boolean interestRecalculationCompoundingDayVisible;

    protected WebMarkupContainer interestRecalculationCompoundingOnDayVContainer;
    protected Long interestRecalculationCompoundingOnDayValue;
    protected ReadOnlyView interestRecalculationCompoundingOnDayView;
    protected boolean interestRecalculationCompoundingOnDayVisible;

    protected WebMarkupBlock interestRecalculationRecalculateBlock;
    protected WebMarkupContainer interestRecalculationRecalculateVContainer;
    protected Option interestRecalculationRecalculateValue;
    protected ReadOnlyView interestRecalculationRecalculateView;

    protected WebMarkupBlock interestRecalculationRecalculateTypeBlock;
    protected WebMarkupContainer interestRecalculationRecalculateTypeVContainer;
    protected Option interestRecalculationRecalculateTypeValue;
    protected ReadOnlyView interestRecalculationRecalculateTypeView;

    protected WebMarkupBlock interestRecalculationRecalculateDayBlock;
    protected WebMarkupContainer interestRecalculationRecalculateDayVContainer;
    protected Option interestRecalculationRecalculateDayValue;
    protected ReadOnlyView interestRecalculationRecalculateDayView;
    protected boolean interestRecalculationRecalculateDayVisible;

    protected WebMarkupContainer interestRecalculationRecalculateOnDayVContainer;
    protected Long interestRecalculationRecalculateOnDayValue;
    protected ReadOnlyView interestRecalculationRecalculateOnDayView;
    protected boolean interestRecalculationRecalculateOnDayVisible;

    protected WebMarkupBlock interestRecalculationRecalculateIntervalBlock;
    protected WebMarkupContainer interestRecalculationRecalculateIntervalVContainer;
    protected Long interestRecalculationRecalculateIntervalValue;
    protected ReadOnlyView interestRecalculationRecalculateIntervalView;

    protected WebMarkupBlock interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock;
    protected WebMarkupContainer interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer;
    protected Boolean interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue;
    protected ReadOnlyView interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView;

    protected WebMarkupBlock interestRecalculationCompoundingIntervalBlock;
    protected WebMarkupContainer interestRecalculationCompoundingIntervalVContainer;
    protected Long interestRecalculationCompoundingIntervalValue;
    protected ReadOnlyView interestRecalculationCompoundingIntervalView;

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

    // Overdue Charges

    protected List<IColumn<Map<String, Object>, String>> overdueChargeColumn;
    protected List<Map<String, Object>> overdueChargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> overdueChargeTable;
    protected ListDataProvider overdueChargeProvider;

    protected List<Map<String, Object>> collateralValue;
    protected DataTable<Map<String, Object>, String> collateralTable;
    protected ListDataProvider collateralProvider;
    protected AjaxLink<Void> collateralAddLink;
    protected ModalWindow collateralPopup;
    protected List<IColumn<Map<String, Object>, String>> collateralColumn;

    protected Map<String, Object> popupModel;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initProductBlock();

        initLoanOfficerBlock();

        initLoanPurposeBlock();

        initFundBlock();

        initSubmittedOnBlock();

        initDisbursementOnBlock();

        initExternalIdBlock();

        initLinkSavingAccountBlock();

        initCreateStandingInstructionAtDisbursementBlock();

        initCurrencyBlock();

        initDecimalPlacesBlock();

        initCurrencyInMultiplesOfBlock();

        initInstallmentInMultiplesOfBlock();

        initPrincipleBlock();

        initLoanTermBlock();

        initLoanTypeBlock();

        initNumberOfRepaymentBlock();

        initRepaidEveryBlock();

        initRepaidTypeBlock();

        initRepaidOnBlock();

        initRepaidDayBlock();

        initFirstRepaymentOnBlock();

        initInterestChargedFromBlock();

        initNominalInterestRateBlock();

        initNominalInterestTypeBlock();

        initInterestMethodBlock();

        initAmortizationBlock();

        initInterestCalculationPeriodBlock();

        initCalculateInterestForExactDayInPartialPeriodBlock();

        initArrearsToleranceBlock();

        initInterestFreePeriodBlock();

        initRepaymentStrategyBlock();

        initOnPrinciplePaymentBlock();

        initOnInterestPaymentBlock();

        initOnArrearsAgingBlock();

        this.installmentAmountBlock = new WebMarkupBlock("installmentAmountBlock", Size.Six_6);
        this.form.add(this.installmentAmountBlock);
        this.installmentAmountIContainer = new WebMarkupContainer("installmentAmountIContainer");
        this.installmentAmountBlock.add(this.installmentAmountIContainer);
        this.installmentAmountField = new TextField<>("installmentAmountField", new PropertyModel<>(this, "installmentAmountValue"));
        this.installmentAmountField.setLabel(Model.of("Installment Amount"));
        this.installmentAmountField.setRequired(false);
        this.installmentAmountIContainer.add(this.installmentAmountField);
        this.installmentAmountFeedback = new TextFeedbackPanel("installmentAmountFeedback", this.installmentAmountField);
        this.installmentAmountIContainer.add(this.installmentAmountFeedback);

        this.interestRecalculationRecalculateInterestBlock = new WebMarkupBlock("interestRecalculationRecalculateInterestBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationRecalculateInterestBlock);
        this.interestRecalculationRecalculateInterestVContainer = new WebMarkupContainer("interestRecalculationRecalculateInterestVContainer");
        this.interestRecalculationRecalculateInterestBlock.add(this.interestRecalculationRecalculateInterestVContainer);
        this.interestRecalculationRecalculateInterestView = new ReadOnlyView("interestRecalculationRecalculateInterestView", new PropertyModel<>(this, "interestRecalculationRecalculateInterestValue"));
        this.interestRecalculationRecalculateInterestVContainer.add(this.interestRecalculationRecalculateInterestView);

        this.interestRecalculationPreClosureInterestCalculationRuleBlock = new WebMarkupBlock("interestRecalculationPreClosureInterestCalculationRuleBlock", Size.Six_6);
        this.form.add(this.interestRecalculationPreClosureInterestCalculationRuleBlock);
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer = new WebMarkupContainer("interestRecalculationPreClosureInterestCalculationRuleVContainer");
        this.interestRecalculationPreClosureInterestCalculationRuleBlock.add(this.interestRecalculationPreClosureInterestCalculationRuleVContainer);
        this.interestRecalculationPreClosureInterestCalculationRuleView = new ReadOnlyView("interestRecalculationPreClosureInterestCalculationRuleView", new PropertyModel<>(this, "interestRecalculationPreClosureInterestCalculationRuleValue"));
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.add(this.interestRecalculationPreClosureInterestCalculationRuleView);

        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock = new WebMarkupBlock("interestRecalculationAdvancePaymentsAdjustmentTypeBlock", Size.Six_6);
        this.form.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer = new WebMarkupContainer("interestRecalculationAdvancePaymentsAdjustmentTypeVContainer");
        this.interestRecalculationAdvancePaymentsAdjustmentTypeBlock.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeView = new ReadOnlyView("interestRecalculationAdvancePaymentsAdjustmentTypeView", new PropertyModel<>(this, "interestRecalculationAdvancePaymentsAdjustmentTypeValue"));
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.add(this.interestRecalculationAdvancePaymentsAdjustmentTypeView);

        this.interestRecalculationCompoundingOnBlock = new WebMarkupBlock("interestRecalculationCompoundingOnBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingOnBlock);
        this.interestRecalculationCompoundingOnVContainer = new WebMarkupContainer("interestRecalculationCompoundingOnVContainer");
        this.interestRecalculationCompoundingOnBlock.add(this.interestRecalculationCompoundingOnVContainer);
        this.interestRecalculationCompoundingOnView = new ReadOnlyView("interestRecalculationCompoundingOnView", new PropertyModel<>(this, "interestRecalculationCompoundingOnValue"));
        this.interestRecalculationCompoundingOnVContainer.add(this.interestRecalculationCompoundingOnView);

        this.interestRecalculationCompoundingBlock = new WebMarkupBlock("interestRecalculationCompoundingBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingBlock);
        this.interestRecalculationCompoundingVContainer = new WebMarkupContainer("interestRecalculationCompoundingVContainer");
        this.interestRecalculationCompoundingBlock.add(this.interestRecalculationCompoundingVContainer);
        this.interestRecalculationCompoundingView = new ReadOnlyView("interestRecalculationCompoundingView", new PropertyModel<>(this, "interestRecalculationCompoundingValue"));
        this.interestRecalculationCompoundingVContainer.add(this.interestRecalculationCompoundingView);

        this.interestRecalculationCompoundingTypeBlock = new WebMarkupBlock("interestRecalculationCompoundingTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingTypeBlock);
        this.interestRecalculationCompoundingTypeVContainer = new WebMarkupContainer("interestRecalculationCompoundingTypeVContainer");
        this.interestRecalculationCompoundingTypeBlock.add(this.interestRecalculationCompoundingTypeVContainer);
        this.interestRecalculationCompoundingTypeView = new ReadOnlyView("interestRecalculationCompoundingTypeView", new PropertyModel<>(this, "interestRecalculationCompoundingTypeValue"));
        this.interestRecalculationCompoundingTypeVContainer.add(this.interestRecalculationCompoundingTypeView);

        this.interestRecalculationCompoundingDayBlock = new WebMarkupBlock("interestRecalculationCompoundingDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingDayBlock);
        this.interestRecalculationCompoundingDayVContainer = new WebMarkupContainer("interestRecalculationCompoundingDayVContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingDayVContainer);
        this.interestRecalculationCompoundingDayView = new ReadOnlyView("interestRecalculationCompoundingDayView", new PropertyModel<>(this, "interestRecalculationCompoundingDayValue"));
        this.interestRecalculationCompoundingDayVContainer.add(this.interestRecalculationCompoundingDayView);

        this.interestRecalculationCompoundingOnDayVContainer = new WebMarkupContainer("interestRecalculationCompoundingOnDayVContainer");
        this.interestRecalculationCompoundingDayBlock.add(this.interestRecalculationCompoundingOnDayVContainer);
        this.interestRecalculationCompoundingOnDayView = new ReadOnlyView("interestRecalculationCompoundingOnDayView", new PropertyModel<>(this, "interestRecalculationCompoundingOnDayValue"));
        this.interestRecalculationCompoundingOnDayVContainer.add(this.interestRecalculationCompoundingOnDayView);

        this.interestRecalculationCompoundingIntervalBlock = new WebMarkupBlock("interestRecalculationCompoundingIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationCompoundingIntervalBlock);
        this.interestRecalculationCompoundingIntervalVContainer = new WebMarkupContainer("interestRecalculationCompoundingIntervalVContainer");
        this.interestRecalculationCompoundingIntervalBlock.add(this.interestRecalculationCompoundingIntervalVContainer);
        this.interestRecalculationCompoundingIntervalView = new ReadOnlyView("interestRecalculationCompoundingIntervalView", new PropertyModel<>(this, "interestRecalculationCompoundingIntervalValue"));
        this.interestRecalculationCompoundingIntervalVContainer.add(this.interestRecalculationCompoundingIntervalView);

        this.interestRecalculationRecalculateBlock = new WebMarkupBlock("interestRecalculationRecalculateBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateBlock);
        this.interestRecalculationRecalculateVContainer = new WebMarkupContainer("interestRecalculationRecalculateVContainer");
        this.interestRecalculationRecalculateBlock.add(this.interestRecalculationRecalculateVContainer);
        this.interestRecalculationRecalculateView = new ReadOnlyView("interestRecalculationRecalculateView", new PropertyModel<>(this, "interestRecalculationRecalculateValue"));
        this.interestRecalculationRecalculateVContainer.add(this.interestRecalculationRecalculateView);

        this.interestRecalculationRecalculateTypeBlock = new WebMarkupBlock("interestRecalculationRecalculateTypeBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateTypeBlock);
        this.interestRecalculationRecalculateTypeVContainer = new WebMarkupContainer("interestRecalculationRecalculateTypeVContainer");
        this.interestRecalculationRecalculateTypeBlock.add(this.interestRecalculationRecalculateTypeVContainer);
        this.interestRecalculationRecalculateTypeView = new ReadOnlyView("interestRecalculationRecalculateTypeView", new PropertyModel<>(this, "interestRecalculationRecalculateTypeValue"));
        this.interestRecalculationRecalculateTypeVContainer.add(this.interestRecalculationRecalculateTypeView);

        this.interestRecalculationRecalculateDayBlock = new WebMarkupBlock("interestRecalculationRecalculateDayBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateDayBlock);
        this.interestRecalculationRecalculateDayVContainer = new WebMarkupContainer("interestRecalculationRecalculateDayVContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateDayVContainer);
        this.interestRecalculationRecalculateDayView = new ReadOnlyView("interestRecalculationRecalculateDayView", new PropertyModel<>(this, "interestRecalculationRecalculateDayValue"));
        this.interestRecalculationRecalculateDayVContainer.add(this.interestRecalculationRecalculateDayView);

        this.interestRecalculationRecalculateOnDayVContainer = new WebMarkupContainer("interestRecalculationRecalculateOnDayVContainer");
        this.interestRecalculationRecalculateDayBlock.add(this.interestRecalculationRecalculateOnDayVContainer);
        this.interestRecalculationRecalculateOnDayView = new ReadOnlyView("interestRecalculationRecalculateOnDayView", new PropertyModel<>(this, "interestRecalculationRecalculateOnDayValue"));
        this.interestRecalculationRecalculateOnDayVContainer.add(this.interestRecalculationRecalculateOnDayView);

        this.interestRecalculationRecalculateIntervalBlock = new WebMarkupBlock("interestRecalculationRecalculateIntervalBlock", Size.Four_4);
        this.form.add(this.interestRecalculationRecalculateIntervalBlock);
        this.interestRecalculationRecalculateIntervalVContainer = new WebMarkupContainer("interestRecalculationRecalculateIntervalVContainer");
        this.interestRecalculationRecalculateIntervalBlock.add(this.interestRecalculationRecalculateIntervalVContainer);
        this.interestRecalculationRecalculateIntervalView = new ReadOnlyView("interestRecalculationRecalculateIntervalView", new PropertyModel<>(this, "interestRecalculationRecalculateIntervalValue"));
        this.interestRecalculationRecalculateIntervalVContainer.add(this.interestRecalculationRecalculateIntervalView);

        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock = new WebMarkupBlock("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock", Size.Twelve_12);
        this.form.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer = new WebMarkupContainer("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer");
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleBlock.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView = new ReadOnlyView("interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView", new PropertyModel<>(this, "interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue"));
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.add(this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleView);

        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupClose);

        this.chargeColumn = Lists.newLinkedList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collectedOn", "collectedOn", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Repayments Every"), "repaymentEvery", "repaymentEvery", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);

        this.collateralPopup = new ModalWindow("collateralPopup");
        add(this.collateralPopup);
        this.collateralPopup.setOnClose(this::collateralPopupClose);

        this.overdueChargeColumn = Lists.newArrayList();
        this.overdueChargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::overdueChargeColumn));
        this.overdueChargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::overdueChargeColumn));
        // this.overdueChargeColumn.add(new TextColumn(Model.of("Date"), "date", "date",
        // this::overdueChargeColumn));
        this.overdueChargeProvider = new ListDataProvider(this.overdueChargeValue);
        this.overdueChargeTable = new DataTable<>("overdueChargeTable", this.overdueChargeColumn, this.overdueChargeProvider, 20);
        this.form.add(this.overdueChargeTable);
        this.overdueChargeTable.addTopToolbar(new HeadersToolbar<>(this.overdueChargeTable, this.overdueChargeProvider));
        this.overdueChargeTable.addBottomToolbar(new NoRecordsToolbar(this.overdueChargeTable));

        this.collateralValue = new ArrayList<>();
        this.collateralColumn = new LinkedList<>();
        this.collateralColumn.add(new TextColumn(Model.of("Collateral"), "collateral", "collateral", this::collateralColumn));
        this.collateralColumn.add(new TextColumn(Model.of("Value"), "value", "value", this::collateralColumn));
        this.collateralColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::collateralColumn));
        this.collateralColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::collateralAction, this::collateralClick));
        this.collateralProvider = new ListDataProvider(this.collateralValue);
        this.collateralTable = new DataTable<>("collateralTable", this.collateralColumn, this.collateralProvider, 20);
        this.form.add(this.collateralTable);
        this.collateralTable.addTopToolbar(new HeadersToolbar<>(this.collateralTable, this.collateralProvider));
        this.collateralTable.addBottomToolbar(new NoRecordsToolbar(this.collateralTable));

        this.collateralAddLink = new AjaxLink<>("collateralAddLink");
        this.collateralAddLink.setOnClick(this::collateralAddLinkClick);
        this.form.add(this.collateralAddLink);

    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {
        if (ChargeFrequency.Month.toOption().equals(this.repaidTypeValue)) {
            this.repaidOnIContainer.setVisible(true);
            this.repaidDayIContainer.setVisible(true);
        } else {
            this.repaidOnIContainer.setVisible(false);
            this.repaidDayIContainer.setVisible(false);
        }

        this.interestRecalculationRecalculateDayVContainer.setVisible(this.interestRecalculationRecalculateDayVisible);
        this.interestRecalculationRecalculateOnDayVContainer.setVisible(this.interestRecalculationRecalculateOnDayVisible);
        this.interestRecalculationCompoundingDayVContainer.setVisible(this.interestRecalculationCompoundingDayVisible);
        this.interestRecalculationCompoundingOnDayVContainer.setVisible(this.interestRecalculationCompoundingOnDayVisible);

        boolean recalculateInterest = this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue;

        this.interestRecalculationRecalculateInterestVContainer.setVisible(recalculateInterest);
        this.interestRecalculationPreClosureInterestCalculationRuleVContainer.setVisible(recalculateInterest);
        this.interestRecalculationAdvancePaymentsAdjustmentTypeVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingOnVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingTypeVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingDayVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingOnDayVContainer.setVisible(recalculateInterest);
        this.interestRecalculationRecalculateVContainer.setVisible(recalculateInterest);
        this.interestRecalculationRecalculateTypeVContainer.setVisible(recalculateInterest);
        this.interestRecalculationRecalculateDayVContainer.setVisible(recalculateInterest);
        this.interestRecalculationRecalculateOnDayVContainer.setVisible(recalculateInterest);
        this.interestRecalculationRecalculateIntervalVContainer.setVisible(recalculateInterest);
        this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleVContainer.setVisible(recalculateInterest);
        this.interestRecalculationCompoundingIntervalVContainer.setVisible(recalculateInterest);

        this.overdueChargeTable.setVisible(!this.overdueChargeValue.isEmpty());

    }

    protected ItemPanel overdueChargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "date".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("type".equals(column) || "collect".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initOnArrearsAgingBlock() {
        this.onArrearsAgingBlock = new WebMarkupBlock("onArrearsAgingBlock", Size.Four_4);
        this.form.add(this.onArrearsAgingBlock);
        this.onArrearsAgingVContainer = new WebMarkupContainer("onArrearsAgingVContainer");
        this.onArrearsAgingBlock.add(this.onArrearsAgingVContainer);
        this.onArrearsAgingView = new ReadOnlyView("onArrearsAgingView", new PropertyModel<>(this, "onArrearsAgingValue"));
        this.onArrearsAgingVContainer.add(this.onArrearsAgingView);
    }

    protected void initOnInterestPaymentBlock() {
        this.onInterestPaymentBlock = new WebMarkupBlock("onInterestPaymentBlock", Size.Four_4);
        this.form.add(this.onInterestPaymentBlock);
        this.onInterestPaymentVContainer = new WebMarkupContainer("onInterestPaymentVContainer");
        this.onInterestPaymentBlock.add(this.onInterestPaymentVContainer);
        this.onInterestPaymentView = new ReadOnlyView("onInterestPaymentView", new PropertyModel<>(this, "onInterestPaymentValue"));
        this.onInterestPaymentVContainer.add(this.onInterestPaymentView);
    }

    protected void initOnPrinciplePaymentBlock() {
        this.onPrinciplePaymentBlock = new WebMarkupBlock("onPrinciplePaymentBlock", Size.Four_4);
        this.form.add(this.onPrinciplePaymentBlock);
        this.onPrinciplePaymentVContainer = new WebMarkupContainer("onPrinciplePaymentVContainer");
        this.onPrinciplePaymentBlock.add(this.onPrinciplePaymentVContainer);
        this.onPrinciplePaymentView = new ReadOnlyView("onPrinciplePaymentView", new PropertyModel<>(this, "onPrinciplePaymentValue"));
        this.onPrinciplePaymentVContainer.add(this.onPrinciplePaymentView);
    }

    protected void initRepaymentStrategyBlock() {
        this.repaymentStrategyBlock = new WebMarkupBlock("repaymentStrategyBlock", Size.Six_6);
        this.form.add(this.repaymentStrategyBlock);
        this.repaymentStrategyVContainer = new WebMarkupContainer("repaymentStrategyVContainer");
        this.repaymentStrategyBlock.add(this.repaymentStrategyVContainer);
        this.repaymentStrategyView = new ReadOnlyView("repaymentStrategyView", new PropertyModel<>(this, "repaymentStrategyValue"));
        this.repaymentStrategyVContainer.add(this.repaymentStrategyView);
    }

    protected void initInterestFreePeriodBlock() {
        this.interestFreePeriodBlock = new WebMarkupBlock("interestFreePeriodBlock", Size.Six_6);
        this.form.add(this.interestFreePeriodBlock);
        this.interestFreePeriodIContainer = new WebMarkupContainer("interestFreePeriodIContainer");
        this.interestFreePeriodBlock.add(this.interestFreePeriodIContainer);
        this.interestFreePeriodField = new TextField<>("interestFreePeriodField", new PropertyModel<>(this, "interestFreePeriodValue"));
        this.interestFreePeriodField.setLabel(Model.of("Interest Free Period"));
        this.interestFreePeriodField.setRequired(false);
        this.interestFreePeriodIContainer.add(this.interestFreePeriodField);
        this.interestFreePeriodFeedback = new TextFeedbackPanel("interestFreePeriodFeedback", this.interestFreePeriodField);
        this.interestFreePeriodIContainer.add(this.interestFreePeriodFeedback);
    }

    protected void initArrearsToleranceBlock() {
        this.arrearsToleranceBlock = new WebMarkupBlock("arrearsToleranceBlock", Size.Six_6);
        this.form.add(this.arrearsToleranceBlock);
        this.arrearsToleranceVContainer = new WebMarkupContainer("arrearsToleranceVContainer");
        this.arrearsToleranceBlock.add(this.arrearsToleranceVContainer);
        this.arrearsToleranceView = new ReadOnlyView("arrearsToleranceView", new PropertyModel<>(this, "arrearsToleranceValue"));
        this.arrearsToleranceVContainer.add(this.arrearsToleranceView);
    }

    protected void initCalculateInterestForExactDayInPartialPeriodBlock() {
        this.calculateInterestForExactDayInPartialPeriodBlock = new WebMarkupBlock("calculateInterestForExactDayInPartialPeriodBlock", Size.Six_6);
        this.form.add(this.calculateInterestForExactDayInPartialPeriodBlock);
        this.calculateInterestForExactDayInPartialPeriodVContainer = new WebMarkupContainer("calculateInterestForExactDayInPartialPeriodVContainer");
        this.calculateInterestForExactDayInPartialPeriodBlock.add(this.calculateInterestForExactDayInPartialPeriodVContainer);
        this.calculateInterestForExactDayInPartialPeriodView = new ReadOnlyView("calculateInterestForExactDayInPartialPeriodView", new PropertyModel<>(this, "calculateInterestForExactDayInPartialPeriodValue"));
        this.calculateInterestForExactDayInPartialPeriodVContainer.add(this.calculateInterestForExactDayInPartialPeriodView);
    }

    protected void initInterestCalculationPeriodBlock() {
        this.interestCalculationPeriodBlock = new WebMarkupBlock("interestCalculationPeriodBlock", Size.Six_6);
        this.form.add(this.interestCalculationPeriodBlock);
        this.interestCalculationPeriodVContainer = new WebMarkupContainer("interestCalculationPeriodVContainer");
        this.interestCalculationPeriodBlock.add(this.interestCalculationPeriodVContainer);
        this.interestCalculationPeriodView = new ReadOnlyView("interestCalculationPeriodView", new PropertyModel<>(this, "interestCalculationPeriodValue"));
        this.interestCalculationPeriodVContainer.add(this.interestCalculationPeriodView);
    }

    protected void initAmortizationBlock() {
        this.amortizationBlock = new WebMarkupBlock("amortizationBlock", Size.Six_6);
        this.form.add(this.amortizationBlock);
        this.amortizationVContainer = new WebMarkupContainer("amortizationVContainer");
        this.amortizationBlock.add(this.amortizationVContainer);
        this.amortizationView = new ReadOnlyView("amortizationView", new PropertyModel<>(this, "amortizationValue"));
        this.amortizationVContainer.add(this.amortizationView);
    }

    protected void initInterestMethodBlock() {
        this.interestMethodBlock = new WebMarkupBlock("interestMethodBlock", Size.Four_4);
        this.form.add(this.interestMethodBlock);
        this.interestMethodVContainer = new WebMarkupContainer("interestMethodVContainer");
        this.interestMethodBlock.add(this.interestMethodVContainer);
        this.interestMethodView = new ReadOnlyView("interestMethodView", new PropertyModel<>(this, "interestMethodValue"));
        this.interestMethodVContainer.add(this.interestMethodView);
    }

    protected void initNominalInterestTypeBlock() {
        this.nominalInterestTypeBlock = new WebMarkupBlock("nominalInterestTypeBlock", Size.Four_4);
        this.form.add(this.nominalInterestTypeBlock);
        this.nominalInterestTypeVContainer = new WebMarkupContainer("nominalInterestTypeVContainer");
        this.nominalInterestTypeBlock.add(this.nominalInterestTypeVContainer);
        this.nominalInterestTypeView = new ReadOnlyView("nominalInterestTypeView", new PropertyModel<>(this, "nominalInterestTypeValue"));
        this.nominalInterestTypeVContainer.add(this.nominalInterestTypeView);
    }

    protected void initNominalInterestRateBlock() {
        this.nominalInterestRateBlock = new WebMarkupContainer("nominalInterestRateBlock");
        this.form.add(this.nominalInterestRateBlock);
        this.nominalInterestRateIContainer = new WebMarkupContainer("nominalInterestRateIContainer");
        this.nominalInterestRateBlock.add(this.nominalInterestRateIContainer);
        this.nominalInterestRateField = new TextField<>("nominalInterestRateField", new PropertyModel<>(this, "nominalInterestRateValue"));
        this.nominalInterestRateField.setLabel(Model.of("Nominal Interest Rate"));
        this.nominalInterestRateField.setRequired(false);
        this.nominalInterestRateIContainer.add(this.nominalInterestRateField);
        this.nominalInterestRateFeedback = new TextFeedbackPanel("nominalInterestRateFeedback", this.nominalInterestRateField);
        this.nominalInterestRateIContainer.add(this.nominalInterestRateFeedback);
    }

    protected void initInterestChargedFromBlock() {
        this.interestChargedFromBlock = new WebMarkupContainer("interestChargedFromBlock");
        this.form.add(this.interestChargedFromBlock);
        this.interestChargedFromIContainer = new WebMarkupContainer("interestChargedFromIContainer");
        this.interestChargedFromBlock.add(this.interestChargedFromIContainer);
        this.interestChargedFromField = new DateTextField("interestChargedFromField", new PropertyModel<>(this, "interestChargedFromValue"));
        this.interestChargedFromField.setLabel(Model.of("Interest Charged From"));
        this.interestChargedFromField.setRequired(false);
        this.interestChargedFromIContainer.add(this.interestChargedFromField);
        this.interestChargedFromFeedback = new TextFeedbackPanel("interestChargedFromFeedback", this.interestChargedFromField);
        this.interestChargedFromIContainer.add(this.interestChargedFromFeedback);
    }

    protected void initFirstRepaymentOnBlock() {
        this.firstRepaymentOnBlock = new WebMarkupContainer("firstRepaymentOnBlock");
        this.form.add(this.firstRepaymentOnBlock);
        this.firstRepaymentOnIContainer = new WebMarkupContainer("firstRepaymentOnIContainer");
        this.firstRepaymentOnBlock.add(this.firstRepaymentOnIContainer);
        this.firstRepaymentOnField = new DateTextField("firstRepaymentOnField", new PropertyModel<>(this, "firstRepaymentOnValue"));
        this.firstRepaymentOnField.setLabel(Model.of("First Repayment On"));
        this.firstRepaymentOnField.setRequired(false);
        this.firstRepaymentOnIContainer.add(this.firstRepaymentOnField);
        this.firstRepaymentOnFeedback = new TextFeedbackPanel("firstRepaymentOnFeedback", this.firstRepaymentOnField);
        this.firstRepaymentOnIContainer.add(this.firstRepaymentOnFeedback);
    }

    protected void initRepaidDayBlock() {
        this.repaidDayProvider = new FrequencyDayProvider();
        this.repaidDayBlock = new WebMarkupContainer("repaidDayBlock");
        this.form.add(this.repaidDayBlock);
        this.repaidDayIContainer = new WebMarkupContainer("repaidDayIContainer");
        this.repaidDayBlock.add(this.repaidDayIContainer);
        this.repaidDayField = new Select2SingleChoice<>("repaidDayField", new PropertyModel<>(this, "repaidDayValue"), this.repaidDayProvider);
        this.repaidDayField.setLabel(Model.of("Day"));
        this.repaidDayField.setRequired(false);
        this.repaidDayIContainer.add(this.repaidDayField);
        this.repaidDayFeedback = new TextFeedbackPanel("repaidDayFeedback", this.repaidDayField);
        this.repaidDayIContainer.add(this.repaidDayFeedback);
    }

    protected void initRepaidOnBlock() {
        this.repaidOnProvider = new RepaidOnProvider();
        this.repaidOnBlock = new WebMarkupContainer("repaidOnBlock");
        this.form.add(this.repaidOnBlock);
        this.repaidOnIContainer = new WebMarkupContainer("repaidOnIContainer");
        this.repaidOnBlock.add(this.repaidOnIContainer);
        this.repaidOnField = new Select2SingleChoice<>("repaidOnField", new PropertyModel<>(this, "repaidOnValue"), this.repaidOnProvider);
        this.repaidOnField.setLabel(Model.of("On"));
        this.repaidOnField.setRequired(false);
        this.repaidOnIContainer.add(this.repaidOnField);
        this.repaidOnFeedback = new TextFeedbackPanel("repaidOnFeedback", this.repaidOnField);
        this.repaidOnIContainer.add(this.repaidOnFeedback);
    }

    protected void initRepaidTypeBlock() {
        this.repaidTypeBlock = new WebMarkupBlock("repaidTypeBlock", Size.Four_4);
        this.form.add(this.repaidTypeBlock);
        this.repaidTypeVContainer = new WebMarkupContainer("repaidTypeVContainer");
        this.repaidTypeBlock.add(this.repaidTypeVContainer);
        this.repaidTypeView = new ReadOnlyView("repaidTypeView", new PropertyModel<>(this, "repaidTypeValue"));
        this.repaidTypeVContainer.add(this.repaidTypeView);
    }

    protected void initRepaidEveryBlock() {
        this.repaidEveryBlock = new WebMarkupBlock("repaidEveryBlock", Size.Four_4);
        this.form.add(this.repaidEveryBlock);
        this.repaidEveryVContainer = new WebMarkupContainer("repaidEveryVContainer");
        this.repaidEveryBlock.add(this.repaidEveryVContainer);
        this.repaidEveryView = new ReadOnlyView("repaidEveryView", new PropertyModel<>(this, "repaidEveryValue"));
        this.repaidEveryVContainer.add(this.repaidEveryView);
    }

    protected void initNumberOfRepaymentBlock() {
        this.numberOfRepaymentBlock = new WebMarkupBlock("numberOfRepaymentBlock", Size.Four_4);
        this.form.add(this.numberOfRepaymentBlock);
        this.numberOfRepaymentIContainer = new WebMarkupContainer("numberOfRepaymentIContainer");
        this.numberOfRepaymentBlock.add(this.numberOfRepaymentIContainer);
        this.numberOfRepaymentField = new TextField<>("numberOfRepaymentField", new PropertyModel<>(this, "numberOfRepaymentValue"));
        this.numberOfRepaymentField.setLabel(Model.of("Number Of Repayment"));
        this.numberOfRepaymentField.setRequired(false);
        this.numberOfRepaymentIContainer.add(this.numberOfRepaymentField);
        this.numberOfRepaymentFeedback = new TextFeedbackPanel("numberOfRepaymentFeedback", this.numberOfRepaymentField);
        this.numberOfRepaymentIContainer.add(this.numberOfRepaymentFeedback);
    }

    protected void initLoanTypeBlock() {
        this.loanTypeProvider = new ChargeFrequencyProvider();
        this.loanTypeBlock = new WebMarkupBlock("loanTypeBlock", Size.Four_4);
        this.form.add(this.loanTypeBlock);
        this.loanTypeIContainer = new WebMarkupContainer("loanTypeIContainer");
        this.loanTypeBlock.add(this.loanTypeIContainer);
        this.loanTypeField = new Select2SingleChoice<>("loanTypeField", new PropertyModel<>(this, "loanTypeValue"), this.loanTypeProvider);
        this.loanTypeField.setLabel(Model.of("Type"));
        this.loanTypeField.setRequired(false);
        this.loanTypeIContainer.add(this.loanTypeField);
        this.loanTypeFeedback = new TextFeedbackPanel("loanTypeFeedback", this.loanTypeField);
        this.loanTypeIContainer.add(this.loanTypeFeedback);
    }

    protected void initLoanTermBlock() {
        this.loanTermBlock = new WebMarkupBlock("loanTermBlock", Size.Four_4);
        this.form.add(this.loanTermBlock);
        this.loanTermIContainer = new WebMarkupContainer("loanTermIContainer");
        this.loanTermBlock.add(this.loanTermIContainer);
        this.loanTermField = new TextField<>("loanTermField", new PropertyModel<>(this, "loanTermValue"));
        this.loanTermField.setLabel(Model.of("Loan Term"));
        this.loanTermField.setRequired(false);
        this.loanTermIContainer.add(this.loanTermField);
        this.loanTermFeedback = new TextFeedbackPanel("loanTermFeedback", this.loanTermField);
        this.loanTermIContainer.add(this.loanTermFeedback);
    }

    protected void initPrincipleBlock() {
        this.principleBlock = new WebMarkupBlock("principleBlock", Size.Four_4);
        this.form.add(this.principleBlock);
        this.principleIContainer = new WebMarkupContainer("principleIContainer");
        this.principleBlock.add(this.principleIContainer);
        this.principleField = new TextField<>("principleField", new PropertyModel<>(this, "principleValue"));
        this.principleField.setLabel(Model.of("Principle"));
        this.principleField.setRequired(false);
        this.principleIContainer.add(this.principleField);
        this.principleFeedback = new TextFeedbackPanel("principleFeedback", this.principleField);
        this.principleIContainer.add(this.principleFeedback);
    }

    protected void initInstallmentInMultiplesOfBlock() {
        this.installmentInMultiplesOfBlock = new WebMarkupBlock("installmentInMultiplesOfBlock", Size.Six_6);
        this.form.add(this.installmentInMultiplesOfBlock);
        this.installmentInMultiplesOfVContainer = new WebMarkupContainer("installmentInMultiplesOfVContainer");
        this.installmentInMultiplesOfBlock.add(this.installmentInMultiplesOfVContainer);
        this.installmentInMultiplesOfView = new ReadOnlyView("installmentInMultiplesOfView", new PropertyModel<>(this, "installmentInMultiplesOfValue"));
        this.installmentInMultiplesOfVContainer.add(this.installmentInMultiplesOfView);
    }

    protected void initCurrencyInMultiplesOfBlock() {
        this.currencyInMultiplesOfBlock = new WebMarkupBlock("currencyInMultiplesOfBlock", Size.Six_6);
        this.form.add(this.currencyInMultiplesOfBlock);
        this.currencyInMultiplesOfVContainer = new WebMarkupContainer("currencyInMultiplesOfVContainer");
        this.currencyInMultiplesOfBlock.add(this.currencyInMultiplesOfVContainer);
        this.currencyInMultiplesOfView = new ReadOnlyView("currencyInMultiplesOfView", new PropertyModel<>(this, "currencyInMultiplesOfValue"));
        this.currencyInMultiplesOfVContainer.add(this.currencyInMultiplesOfView);
    }

    protected void initDecimalPlacesBlock() {
        this.decimalPlacesBlock = new WebMarkupBlock("decimalPlacesBlock", Size.Six_6);
        this.form.add(this.decimalPlacesBlock);
        this.decimalPlacesVContainer = new WebMarkupContainer("decimalPlacesVContainer");
        this.decimalPlacesBlock.add(this.decimalPlacesVContainer);
        this.decimalPlacesView = new ReadOnlyView("decimalPlacesView", new PropertyModel<>(this, "decimalPlacesValue"));
        this.decimalPlacesVContainer.add(this.decimalPlacesView);
    }

    protected void initCurrencyBlock() {
        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        this.form.add(this.currencyBlock);
        this.currencyVContainer = new WebMarkupContainer("currencyVContainer");
        this.currencyBlock.add(this.currencyVContainer);
        this.currencyView = new ReadOnlyView("currencyView", new PropertyModel<>(this, "currencyValue"));
        this.currencyVContainer.add(this.currencyView);
    }

    protected void initCreateStandingInstructionAtDisbursementBlock() {
        this.createStandingInstructionAtDisbursementBlock = new WebMarkupBlock("createStandingInstructionAtDisbursementBlock", Size.Six_6);
        this.form.add(this.createStandingInstructionAtDisbursementBlock);
        this.createStandingInstructionAtDisbursementIContainer = new WebMarkupContainer("createStandingInstructionAtDisbursementIContainer");
        this.createStandingInstructionAtDisbursementBlock.add(this.createStandingInstructionAtDisbursementIContainer);
        this.createStandingInstructionAtDisbursementField = new CheckBox("createStandingInstructionAtDisbursementField", new PropertyModel<>(this, "createStandingInstructionAtDisbursementValue"));
        this.createStandingInstructionAtDisbursementField.add(new OnChangeAjaxBehavior());
        this.createStandingInstructionAtDisbursementIContainer.add(this.createStandingInstructionAtDisbursementField);
        this.createStandingInstructionAtDisbursementFeedback = new TextFeedbackPanel("createStandingInstructionAtDisbursementFeedback", this.createStandingInstructionAtDisbursementField);
        this.createStandingInstructionAtDisbursementIContainer.add(this.createStandingInstructionAtDisbursementFeedback);
    }

    protected void initLinkSavingAccountBlock() {
        this.linkSavingAccountProvider = new SingleChoiceProvider(MSavingsAccount.NAME, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO + ", ' => ', " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME + ")");
        this.linkSavingAccountProvider.applyJoin("m_savings_product", "INNER JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        this.linkSavingAccountProvider.applyWhere("status_enum", MSavingsAccount.NAME + "." + MSavingsAccount.Field.STATUS_ENUM + " = 300");
        this.linkSavingAccountProvider.applyWhere("client_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.CLIENT_ID + " = " + this.clientId);
        this.linkSavingAccountBlock = new WebMarkupBlock("linkSavingAccountBlock", Size.Six_6);
        this.form.add(this.linkSavingAccountBlock);
        this.linkSavingAccountIContainer = new WebMarkupContainer("linkSavingAccountIContainer");
        this.linkSavingAccountBlock.add(this.linkSavingAccountIContainer);
        this.linkSavingAccountField = new Select2SingleChoice<>("linkSavingAccountField", new PropertyModel<>(this, "linkSavingAccountValue"), this.linkSavingAccountProvider);
        this.linkSavingAccountField.setLabel(Model.of("Saving Account"));
        this.linkSavingAccountField.setRequired(false);
        this.linkSavingAccountIContainer.add(this.linkSavingAccountField);
        this.linkSavingAccountFeedback = new TextFeedbackPanel("linkSavingAccountFeedback", this.linkSavingAccountField);
        this.linkSavingAccountIContainer.add(this.linkSavingAccountFeedback);
    }

    protected void initExternalIdBlock() {
        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Six_6);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(false);
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);
    }

    protected void initDisbursementOnBlock() {
        this.disbursementOnBlock = new WebMarkupBlock("disbursementOnBlock", Size.Six_6);
        this.form.add(this.disbursementOnBlock);
        this.disbursementOnIContainer = new WebMarkupContainer("disbursementOnIContainer");
        this.disbursementOnBlock.add(this.disbursementOnIContainer);
        this.disbursementOnField = new DateTextField("disbursementOnField", new PropertyModel<>(this, "disbursementOnValue"));
        this.disbursementOnField.setLabel(Model.of("Disbursement On"));
        this.disbursementOnField.setRequired(false);
        this.disbursementOnIContainer.add(this.disbursementOnField);
        this.disbursementOnFeedback = new TextFeedbackPanel("disbursementOnFeedback", this.disbursementOnField);
        this.disbursementOnIContainer.add(this.disbursementOnFeedback);
    }

    protected void initSubmittedOnBlock() {
        this.submittedOnBlock = new WebMarkupBlock("submittedOnBlock", Size.Six_6);
        this.form.add(this.submittedOnBlock);
        this.submittedOnIContainer = new WebMarkupContainer("submittedOnIContainer");
        this.submittedOnBlock.add(this.submittedOnIContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.setRequired(false);
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnIContainer.add(this.submittedOnFeedback);
    }

    protected void initFundBlock() {
        this.fundProvider = new FundProvider();
        this.fundBlock = new WebMarkupBlock("fundBlock", Size.Six_6);
        this.form.add(this.fundBlock);
        this.fundIContainer = new WebMarkupContainer("fundIContainer");
        this.fundBlock.add(this.fundIContainer);
        this.fundField = new Select2SingleChoice<>("fundField", new PropertyModel<>(this, "fundValue"), this.fundProvider);
        this.fundField.setLabel(Model.of("Fund"));
        this.fundField.setRequired(false);
        this.fundIContainer.add(this.fundField);
        this.fundFeedback = new TextFeedbackPanel("fundFeedback", this.fundField);
        this.fundIContainer.add(this.fundFeedback);
    }

    protected void initLoanPurposeBlock() {
        this.loanPurposeProvider = new LoanPurposeProvider();
        this.loanPurposeBlock = new WebMarkupBlock("loanPurposeBlock", Size.Six_6);
        this.form.add(this.loanPurposeBlock);
        this.loanPurposeIContainer = new WebMarkupContainer("loanPurposeIContainer");
        this.loanPurposeBlock.add(this.loanPurposeIContainer);
        this.loanPurposeField = new Select2SingleChoice<>("loanPurposeField", new PropertyModel<>(this, "loanPurposeValue"), this.loanPurposeProvider);
        this.loanPurposeField.setLabel(Model.of("Loan Purpose"));
        this.loanPurposeField.setRequired(false);
        this.loanPurposeIContainer.add(this.loanPurposeField);
        this.loanPurposeFeedback = new TextFeedbackPanel("loanPurposeFeedback", this.loanPurposeField);
        this.loanPurposeIContainer.add(this.loanPurposeFeedback);
    }

    protected void initLoanOfficerBlock() {
        this.loanOfficerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
        this.loanOfficerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
        this.loanOfficerProvider.applyWhere("is_loan_officer", MStaff.Field.IS_LOAN_OFFICER + " = 1");
        this.loanOfficerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
        this.loanOfficerBlock = new WebMarkupBlock("loanOfficerBlock", Size.Six_6);
        this.form.add(this.loanOfficerBlock);
        this.loanOfficerIContainer = new WebMarkupContainer("loanOfficerIContainer");
        this.loanOfficerBlock.add(this.loanOfficerIContainer);
        this.loanOfficerField = new Select2SingleChoice<>("loanOfficerField", new PropertyModel<>(this, "loanOfficerValue"), this.loanOfficerProvider);
        this.loanOfficerField.setLabel(Model.of("Loan Officer"));
        this.loanOfficerField.setRequired(false);
        this.loanOfficerIContainer.add(this.loanOfficerField);
        this.loanOfficerFeedback = new TextFeedbackPanel("loanOfficerFeedback", this.loanOfficerField);
        this.loanOfficerIContainer.add(this.loanOfficerFeedback);
    }

    protected void initProductBlock() {
        this.productBlock = new WebMarkupBlock("productBlock", Size.Six_6);
        this.form.add(this.productBlock);
        this.productVContainer = new WebMarkupContainer("productVContainer");
        this.productBlock.add(this.productVContainer);
        this.productView = new ReadOnlyView("productView", new PropertyModel<>(this, "productValue"));
        this.productVContainer.add(this.productView);
    }

    protected void collateralClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.collateralValue.size(); i++) {
            Map<String, Object> column = this.collateralValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.collateralValue.remove(index);
        }
        target.add(this.collateralTable);
    }

    protected List<ActionItem> collateralAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void collateralPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = null;
        Option collateral = (Option) this.popupModel.get("collateralValue");
        for (Map<String, Object> temp : this.collateralValue) {
            if (collateral.getId().equals(temp.get("uuid"))) {
                item = temp;
                break;
            }
        }
        if (item == null) {
            item = Maps.newHashMap();
            item.put("uuid", collateral.getId());
            item.put("collateral", collateral);
            item.put("value", this.popupModel.get("amountValue"));
            item.put("description", this.popupModel.get("descriptionValue"));
            this.collateralValue.add(item);
        } else {
            item.put("uuid", collateral.getId());
            item.put("collateral", collateral);
            item.put("value", this.popupModel.get("amountValue"));
            item.put("description", this.popupModel.get("descriptionValue"));
        }

        target.add(this.collateralTable);
    }

    protected boolean collateralAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.collateralPopup.setContent(new CollateralPopup("collateral", this.collateralPopup, this.popupModel));
        this.collateralPopup.show(target);
        return false;
    }

    protected ItemPanel collateralColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("collateral".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("value".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "type".equals(column) || "collectedOn".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        } else if ("date".equals(column)) {
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
            if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
                Date value = (Date) model.get("dayMonth");
                return new TextCell(value, "dd MMMM");
            } else if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.SpecifiedDueDate) {
                Date value = (Date) model.get("date");
                return new TextCell(value, "yyyy-MM-dd");
            } else {
                return new TextCell("");
            }
        } else if ("repaymentEvery".equals(column)) {
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
            if (chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee) {
                Long value = (Long) model.get(column);
                return new TextCell(value);
            } else {
                return new TextCell("");
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void chargeClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(column)) {
            int index = -1;
            for (int i = 0; i < this.chargeValue.size(); i++) {
                Map<String, Object> value = this.chargeValue.get(i);
                if (model.get("uuid").equals(value.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.chargeValue.remove(index);
            }
            target.add(this.chargeTable);
        } else if ("update".equals(column)) {
            this.popupModel.clear();
            this.popupModel.put("uuid", model.get("uuid"));
            this.popupModel.put("chargeValue", model.get("charge"));
            this.popupModel.put("amountValue", model.get("amount"));
            this.popupModel.put("dateValue", model.get("date"));
            this.popupModel.put("repaymentEveryValue", model.get("repaymentEvery"));
            this.popupModel.put("chargeTypeValue", model.get("type"));
            this.popupModel.put("chargeValue", model.get("name"));
            this.popupModel.put("collectedOnValue", model.get("collectedOn"));
            this.chargePopup.setContent(new AccountChargePopup("charge", this.chargePopup, ProductPopup.Saving, this.popupModel, this.currencyValue));
            this.chargePopup.show(target);
        }
    }

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("update", Model.of("Update"), ItemCss.PRIMARY));
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.chargePopup.setContent(new AccountChargePopup("charge", this.chargePopup, ProductPopup.Loan, this.popupModel, this.currencyValue));
        this.chargePopup.show(target);
        return false;
    }

    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = null;
        if (this.popupModel.get("uuid") != null) {
            for (int i = 0; i < this.chargeValue.size(); i++) {
                item = this.chargeValue.get(i);
                if (this.popupModel.get("uuid").equals(item.get("uuid"))) {
                    break;
                }
            }
        } else {
            item = Maps.newHashMap();
            item.put("uuid", UUID.randomUUID().toString());
            this.chargeValue.add(item);
        }
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("chargeTime", this.popupModel.get("chargeTime"));
        item.put("amount", this.popupModel.get("amountValue"));
        item.put("date", this.popupModel.get("dateValue"));
        item.put("dayMonth", this.popupModel.get("dayMonthValue"));
        item.put("repaymentEvery", this.popupModel.get("repaymentEveryValue"));
        item.put("type", this.popupModel.get("chargeTypeValue"));
        item.put("name", this.popupModel.get("chargeValue"));
        item.put("collectedOn", this.popupModel.get("collectedOnValue"));
        target.add(this.chargeTable);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

        this.popupModel = Maps.newHashMap();
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        this.submittedOnValue = DateTime.now().toDate();
        this.externalIdValue = StringUtils.upperCase(UUID.randomUUID().toString());

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
        selectQuery.addField("m_product_loan.name product");
        selectQuery.addField("m_product_loan.description");
        selectQuery.addField("m_product_loan.name");
        selectQuery.addField("m_product_loan.short_name");
        selectQuery.addField("m_product_loan.start_date");
        selectQuery.addField("m_product_loan.close_date");
        selectQuery.addField("m_product_loan.include_in_borrower_cycle");
        selectQuery.addField("m_fund.id fund_id");

        // currency
        selectQuery.addField("m_organisation_currency.code currency_code");
        selectQuery.addField("m_product_loan.currency_digits");
        selectQuery.addField("m_product_loan.currency_multiplesof");
        selectQuery.addField("m_product_loan.instalment_amount_in_multiples_of");

        // Terms
        selectQuery.addField("m_product_loan.use_borrower_cycle");
        selectQuery.addField("m_product_loan.min_principal_amount");
        selectQuery.addField("m_product_loan.principal_amount");
        selectQuery.addField("m_product_loan.max_principal_amount");

        selectQuery.addField("m_product_loan.min_number_of_repayments");
        selectQuery.addField("m_product_loan.number_of_repayments");
        selectQuery.addField("m_product_loan.max_number_of_repayments");
        selectQuery.addField("m_product_loan.repay_every");
        selectQuery.addField("m_product_loan.repayment_period_frequency_enum");

        selectQuery.addField("m_product_loan.min_nominal_interest_rate_per_period");
        selectQuery.addField("m_product_loan.nominal_interest_rate_per_period");
        selectQuery.addField("m_product_loan.max_nominal_interest_rate_per_period");
        selectQuery.addField("m_product_loan.interest_period_frequency_enum");

        selectQuery.addField("m_product_loan.is_linked_to_floating_interest_rates");

        selectQuery.addField("m_floating_rates.name floating_rate");
        selectQuery.addField("m_product_loan_floating_rates.interest_rate_differential");
        selectQuery.addField("m_product_loan_floating_rates.is_floating_interest_rate_calculation_allowed");
        selectQuery.addField("m_product_loan_floating_rates.min_differential_lending_rate");
        selectQuery.addField("m_product_loan_floating_rates.default_differential_lending_rate");
        selectQuery.addField("m_product_loan_floating_rates.max_differential_lending_rate");

        selectQuery.addField("m_product_loan.min_days_between_disbursal_and_first_repayment");

        // setting
        selectQuery.addField("m_product_loan.amortization_method_enum");
        selectQuery.addField("m_product_loan.interest_method_enum");
        selectQuery.addField("m_product_loan.interest_calculated_in_period_enum");
        selectQuery.addField("m_product_loan.allow_partial_period_interest_calcualtion");
        selectQuery.addField("m_product_loan.arrearstolerance_amount");
        selectQuery.addField("m_product_loan.loan_transaction_strategy_id");
        selectQuery.addField("m_product_loan.grace_interest_free_periods");
        selectQuery.addField("m_product_loan.grace_on_arrears_ageing");
        selectQuery.addField("m_product_loan.grace_on_interest_periods");
        selectQuery.addField("m_product_loan.grace_on_principal_periods");
        selectQuery.addField("m_product_loan.account_moves_out_of_npa_only_on_arrears_completion");
        selectQuery.addField("m_product_loan.overdue_days_for_npa");
        selectQuery.addField("m_product_loan.days_in_year_enum");
        selectQuery.addField("m_product_loan.days_in_month_enum");
        selectQuery.addField("m_product_loan.principal_threshold_for_last_installment");
        selectQuery.addField("m_product_loan.can_define_fixed_emi_amount");
        selectQuery.addField("m_product_loan.can_use_for_topup");
        selectQuery.addField("m_product_loan.allow_variabe_installments");
        selectQuery.addField("m_product_loan_variable_installment_config.minimum_gap");
        selectQuery.addField("m_product_loan_variable_installment_config.maximum_gap");

        // re-calculation
        selectQuery.addField("m_product_loan.interest_recalculation_enabled");

        selectQuery.addField("m_product_loan_recalculation_details.reschedule_strategy_enum");
        selectQuery.addField("m_product_loan_recalculation_details.rest_frequency_type_enum");
        selectQuery.addField("m_product_loan_recalculation_details.rest_frequency_interval");
        selectQuery.addField("m_product_loan_recalculation_details.arrears_based_on_original_schedule");
        selectQuery.addField("m_product_loan_recalculation_details.pre_close_interest_calculation_strategy");
        selectQuery.addField("m_product_loan_recalculation_details.compounding_frequency_type_enum");
        selectQuery.addField("m_product_loan_recalculation_details.compounding_frequency_interval");
        selectQuery.addField("m_product_loan_recalculation_details.rest_frequency_nth_day_enum");
        selectQuery.addField("m_product_loan_recalculation_details.rest_frequency_on_day");
        selectQuery.addField("m_product_loan_recalculation_details.rest_frequency_weekday_enum");
        selectQuery.addField("m_product_loan_recalculation_details.compounding_frequency_nth_day_enum");
        selectQuery.addField("m_product_loan_recalculation_details.compounding_frequency_on_day");
        selectQuery.addField("m_product_loan_recalculation_details.compound_type_enum");
        selectQuery.addField("m_product_loan_recalculation_details.compounding_frequency_weekday_enum");
        selectQuery.addField("m_product_loan_recalculation_details.is_compounding_to_be_posted_as_transaction");
        selectQuery.addField("m_product_loan_recalculation_details.allow_compounding_on_eod");

        selectQuery.addField("m_product_loan.hold_guarantee_funds");
        selectQuery.addField("m_product_loan_guarantee_details.mandatory_guarantee");
        selectQuery.addField("m_product_loan_guarantee_details.minimum_guarantee_from_guarantor_funds");
        selectQuery.addField("m_product_loan_guarantee_details.minimum_guarantee_from_own_funds");

        selectQuery.addField("m_product_loan.allow_multiple_disbursals");
        selectQuery.addField("m_product_loan.max_disbursals");
        selectQuery.addField("m_product_loan.max_outstanding_loan_balance");

        selectQuery.addField("m_product_loan.accounting_type");

        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
            this.officeId = String.valueOf(clientObject.get("office_id"));
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.groupDisplayName = (String) groupObject.get("display_name");
            this.officeId = String.valueOf(groupObject.get("office_id"));
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.centerDisplayName = (String) centerObject.get("display_name");
            this.officeId = String.valueOf(centerObject.get("office_id"));
        }

        this.productValue = (String) loanObject.get("name");

        selectQuery = new SelectQuery(MFund.NAME);
        selectQuery.addField(MFund.Field.ID);
        selectQuery.addField(MFund.Field.NAME + " as text");
        selectQuery.addWhere(MFund.Field.ID + " = :" + MFund.Field.ID, loanObject.get("fund_id"));
        this.fundValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.submittedOnValue = DateTime.now().toDate();
        this.disbursementOnValue = DateTime.now().toDate();

        this.externalIdValue = generator.externalId();

        this.currencyValue = (String) loanObject.get("currency_code");
        this.decimalPlacesValue = (Long) loanObject.get("currency_digits");

        this.currencyInMultiplesOfValue = (Long) loanObject.get("currency_multiplesof");
        this.installmentInMultiplesOfValue = (Double) loanObject.get("instalment_amount_in_multiples_of");

        this.principleValue = (Double) loanObject.get("principal_amount");

        this.numberOfRepaymentValue = (Long) loanObject.get("number_of_repayments");
        this.repaidEveryValue = (Long) loanObject.get("repay_every");

        if (this.numberOfRepaymentValue != null && this.repaidEveryValue != null) {
            this.loanTermValue = this.numberOfRepaymentValue * this.repaidEveryValue;
        }

        ChargeFrequency chargeFrequency = ChargeFrequency.parseLiteral(String.valueOf(loanObject.get("repayment_period_frequency_enum")));

        this.loanTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

        this.repaidTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

        this.firstRepaymentOnValue = DateTime.now().toDate();
        this.interestChargedFromValue = DateTime.now().toDate();

        this.nominalInterestRateValue = (Double) loanObject.get("nominal_interest_rate_per_period");

        NominalInterestRateType nominalInterestTypeValue = NominalInterestRateType.parseLiteral(String.valueOf(loanObject.get("interest_period_frequency_enum")));
        this.nominalInterestTypeValue = nominalInterestTypeValue == null ? "" : nominalInterestTypeValue.getDescription();

        this.interestMethodValue = InterestMethod.optionLiteral(String.valueOf(loanObject.get("interest_method_enum")));

        this.amortizationValue = Amortization.optionLiteral(String.valueOf(loanObject.get("amortization_method_enum")));

        this.interestCalculationPeriodValue = InterestCalculationPeriod.optionLiteral(String.valueOf(loanObject.get("interest_calculated_in_period_enum")));

        this.calculateInterestForExactDayInPartialPeriodValue = (Boolean) loanObject.get("allow_partial_period_interest_calcualtion");

        this.repaymentStrategyValue = RepaymentStrategy.optionLiteral(String.valueOf(loanObject.get("loan_transaction_strategy_id")));

        this.arrearsToleranceValue = (Double) loanObject.get("arrearstolerance_amount");

        this.interestFreePeriodValue = (Long) loanObject.get("grace_interest_free_periods");

        this.onArrearsAgingValue = (Long) loanObject.get("grace_on_arrears_ageing");
        this.onInterestPaymentValue = (Long) loanObject.get("grace_on_interest_periods");
        this.onPrinciplePaymentValue = (Long) loanObject.get("grace_on_principal_periods");

        Long interest_recalculation_enabled = (Long) loanObject.get("interest_recalculation_enabled");
        this.interestRecalculationRecalculateInterestValue = interest_recalculation_enabled == null ? null : interest_recalculation_enabled == 1;

        if (this.interestRecalculationRecalculateInterestValue != null && this.interestRecalculationRecalculateInterestValue) {
            this.interestRecalculationPreClosureInterestCalculationRuleValue = ClosureInterestCalculationRule.optionLiteral(String.valueOf(loanObject.get("pre_close_interest_calculation_strategy")));
            this.interestRecalculationArrearsRecognizationBasedOnOriginalScheduleValue = (Boolean) loanObject.get("arrears_based_on_original_schedule");
            this.interestRecalculationAdvancePaymentsAdjustmentTypeValue = AdvancePaymentsAdjustmentType.optionLiteral(String.valueOf(loanObject.get("reschedule_strategy_enum")));
            this.interestRecalculationCompoundingOnValue = InterestRecalculationCompound.optionLiteral(String.valueOf(loanObject.get("compound_type_enum")));

            FrequencyType compounding_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("compounding_frequency_nth_day_enum")));
            if (compounding_frequency_nth_day_enum != null) {
                this.interestRecalculationCompoundingTypeValue = compounding_frequency_nth_day_enum.toOption();
                if (compounding_frequency_nth_day_enum == FrequencyType.OnDay) {
                    this.interestRecalculationCompoundingOnDayValue = (Long) loanObject.get("compounding_frequency_on_day");
                    this.interestRecalculationCompoundingOnDayVisible = true;
                } else {
                    this.interestRecalculationCompoundingDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_weekday_enum")));
                    this.interestRecalculationCompoundingDayVisible = true;
                }
            }
            this.interestRecalculationCompoundingValue = Frequency.optionLiteral(String.valueOf(loanObject.get("compounding_frequency_type_enum")));
            this.interestRecalculationCompoundingIntervalValue = (Long) loanObject.get("compounding_frequency_interval");

            FrequencyType rest_frequency_nth_day_enum = FrequencyType.parseLiteral(String.valueOf(loanObject.get("rest_frequency_nth_day_enum")));
            if (rest_frequency_nth_day_enum != null) {
                this.interestRecalculationRecalculateTypeValue = rest_frequency_nth_day_enum.toOption();
                if (rest_frequency_nth_day_enum == FrequencyType.OnDay) {
                    this.interestRecalculationRecalculateOnDayValue = (Long) loanObject.get("rest_frequency_on_day");
                    this.interestRecalculationRecalculateOnDayVisible = true;
                } else {
                    this.interestRecalculationRecalculateDayValue = FrequencyDay.optionLiteral(String.valueOf(loanObject.get("rest_frequency_weekday_enum")));
                    this.interestRecalculationRecalculateDayVisible = true;
                }
            }
            this.interestRecalculationRecalculateValue = Frequency.optionLiteral(String.valueOf(loanObject.get("rest_frequency_type_enum")));
            this.interestRecalculationRecalculateIntervalValue = (Long) loanObject.get("rest_frequency_interval");

            // query.addField("m_product_loan_recalculation_details.is_compounding_to_be_posted_as_transaction");
            // query.addField("m_product_loan_recalculation_details.allow_compounding_on_eod");
        }

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MProductLoanCharge.NAME + " ON " + MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_PAYMENT_MODE_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_PENALTY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.IS_ACTIVE);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MIN_CAP);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.MAX_CAP);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_FREQUENCY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.INCOME_OR_LIABILITY_ACCOUNT_ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.TAX_GROUP_ID);
        selectQuery.addWhere(MProductLoanCharge.NAME + "." + MProductLoanCharge.Field.PRODUCT_LOAN_ID + " = '" + this.loanId + "'");

        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> chargeObject : chargeObjects) {
            Boolean is_penalty = (Boolean) chargeObject.get("is_penalty");
            Map<String, Object> charge = new HashMap<>();
            charge.put("name", chargeObject.get("name"));
            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
            charge.put("type", type);
            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
            charge.put("collect", collect);
            charge.put("amount", chargeObject.get("amount"));
            if (is_penalty != null && is_penalty) {
                this.overdueChargeValue.add(charge);
            } else {
                Map<String, Object> popupModel = Maps.newHashMap();

                popupModel.put("chargeTime", chargeObject.get("charge_time_enum"));
                Long collectedOn = (Long) chargeObject.get("charge_time_enum");
                if (collectedOn != null) {
                    popupModel.put("collectedOnValue", ChargeTime.optionLiteral(String.valueOf(collectedOn)));
                } else {
                    popupModel.put("collectedOnValue", null);
                }

                Double amount = (Double) chargeObject.get("amount");
                if (amount != null) {
                    popupModel.put("amountValue", amount);
                } else {
                    popupModel.put("amountValue", null);
                }

                Long charge_calculation_enum = (Long) chargeObject.get("charge_calculation_enum");
                if (type != null) {
                    popupModel.put("chargeTypeValue", ChargeCalculation.optionLiteral(String.valueOf(charge_calculation_enum)));
                } else {
                    popupModel.put("chargeTypeValue", null);
                }

                Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
                popupModel.put("repaymentEveryValue", repaymentEveryValue);

                Long month = (Long) chargeObject.get("fee_on_month");
                Long day = (Long) chargeObject.get("fee_on_day");
                if (day != null && month != null) {
                    try {
                        popupModel.put("dayMonthValue", DateUtils.parseDate(day + "/" + month, "d/M"));
                    } catch (ParseException e) {
                    }
                }

                popupModel.put("chargeValue", new Option(String.valueOf(chargeObject.get("id")), (String) chargeObject.get("name")));

                Map<String, Object> item = Maps.newHashMap();
                item.put("uuid", UUID.randomUUID().toString());
                item.put("charge", popupModel.get("chargeValue"));
                item.put("chargeTime", popupModel.get("chargeTime"));
                item.put("amount", popupModel.get("amountValue"));
                item.put("date", popupModel.get("dateValue"));
                item.put("dayMonth", popupModel.get("dayMonthValue"));
                item.put("repaymentEvery", popupModel.get("repaymentEveryValue"));
                item.put("type", popupModel.get("chargeTypeValue"));
                item.put("name", popupModel.get("chargeValue"));
                item.put("collectedOn", popupModel.get("collectedOnValue"));

                this.chargeValue.add(item);
            }
        }

    }

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
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
                breadcrumb.setLabel(this.clientDisplayName);
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
                breadcrumb.setLabel(this.groupDisplayName);
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
                breadcrumb.setLabel(this.centerDisplayName);
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            parameters.add("client", this.client.name());
            breadcrumb.setLabel("Loan Selection");
            breadcrumb.setPage(LoanAccountSelectionPage.class);
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

    protected void saveButtonSubmit(Button button) {

        LoanAccountBuilder builder = new LoanAccountBuilder();

        builder.withClientId(this.clientId);
        builder.withProductId(this.loanId);
        if (this.loanOfficerValue != null) {
            builder.withLoanOfficerId(this.loanOfficerValue.getId());
        }
        if (this.loanPurposeValue != null) {
            builder.withLoanPurposeId(this.loanPurposeValue.getId());
        }
        if (this.fundValue != null) {
            builder.withFundId(this.fundValue.getId());
        }
        builder.withSubmittedOnDate(this.submittedOnValue);
        builder.withExpectedDisbursementDate(this.disbursementOnValue);
        builder.withExternalId(this.externalIdValue);

        if (this.linkSavingAccountValue != null) {
            builder.withLinkAccountId(this.linkSavingAccountValue.getId());
            builder.withCreateStandingInstructionAtDisbursement(this.createStandingInstructionAtDisbursementValue);
        }

        builder.withPrinciple(this.principleValue);
        builder.withLoanTermFrequency(this.loanTermValue);
        if (this.loanTypeValue != null) {
            builder.withLoanTermFrequencyType(ChargeFrequency.valueOf(this.loanTypeValue.getId()));
        }

        builder.withNumberOfRepayments(this.numberOfRepaymentValue);
        builder.withRepaymentEvery(this.repaidEveryValue);
        if (this.repaidTypeValue != null) {
            builder.withRepaymentFrequencyType(ChargeFrequency.valueOf(this.repaidTypeValue.getId()));
        }
        if (this.repaidOnValue != null) {
            builder.withRepaymentFrequencyNthDayType(RepaidOn.valueOf(this.repaidOnValue.getId()));
        }
        if (this.repaidDayValue != null) {
            builder.withRepaymentFrequencyDayOfWeekType(FrequencyDay.valueOf(this.repaidDayValue.getId()));
        }

        builder.withRepaymentsStartingFromDate(this.firstRepaymentOnValue);
        builder.withInterestChargedFromDate(this.interestChargedFromValue);

        builder.withInterestRatePerPeriod(this.nominalInterestRateValue);
        if (this.interestMethodValue != null) {
            builder.withInterestType(InterestMethod.valueOf(this.interestMethodValue.getId()));
        }
        if (this.amortizationValue != null) {
            builder.withAmortizationType(Amortization.valueOf(this.amortizationValue.getId()));
        }
        if (this.interestCalculationPeriodValue != null) {
            InterestCalculationPeriod period = InterestCalculationPeriod.valueOf(this.interestCalculationPeriodValue.getId());
            builder.withInterestCalculationPeriodType(period);
            if (period == InterestCalculationPeriod.Daily) {
                builder.withAllowPartialPeriodInterestCalcualtion(this.calculateInterestForExactDayInPartialPeriodValue != null && this.calculateInterestForExactDayInPartialPeriodValue);
            }
        }
        builder.withInArrearsTolerance(this.arrearsToleranceValue);
        builder.withGraceOnInterestCharged(this.interestFreePeriodValue);
        if (this.repaymentStrategyValue != null) {
            builder.withTransactionProcessingStrategyId(RepaymentStrategy.valueOf(this.repaymentStrategyValue.getId()));
        }

        builder.withGraceOnPrinciplePayment(this.onPrinciplePaymentValue);
        builder.withGraceOnInterestPayment(this.onInterestPaymentValue);
        builder.withGraceOnArrearsAgeing(this.onArrearsAgingValue);

        for (Map<String, Object> charge : this.chargeValue) {
            String chargeId = ((Option) charge.get("charge")).getId();
            Double amount = (Double) charge.get("amount");
            Date feeOnMonthDay = (Date) charge.get("dayMonth");
            Date dueDate = (Date) charge.get("date");
            Long feeInterval = (Long) charge.get("repaymentEvery");
            builder.withCharge(chargeId, amount, feeOnMonthDay, dueDate, feeInterval);
        }

        for (Map<String, Object> collateral : this.collateralValue) {
            Option type = (Option) collateral.get("collateral");
            Double value = (Double) collateral.get("value");
            String description = (String) collateral.get("description");
            builder.withCollateral(type != null ? type.getId() : null, value, description);
        }

        JsonNode request = builder.build();
        JsonNode node = ClientHelper.createLoanAccount((Session) getSession(), request);

        if (reportError(node)) {
            return;
        }

        if (this.client == ClientEnum.Client) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            setResponsePage(GroupPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

    }
}