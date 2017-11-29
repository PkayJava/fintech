package com.angkorteam.fintech.pages.client.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.loan.Amortization;
import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
import com.angkorteam.fintech.dto.enums.loan.InterestMethod;
import com.angkorteam.fintech.dto.enums.loan.NominalInterestRateType;
import com.angkorteam.fintech.dto.enums.loan.RepaymentStrategy;
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
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
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

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    protected String clientId;
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

    protected WebMarkupBlock principalBlock;
    protected WebMarkupContainer principalIContainer;
    protected Double principalValue;
    protected TextField<Double> principalField;
    protected TextFeedbackPanel principalFeedback;

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

    protected WebMarkupBlock repaidEveryBlock;
    protected WebMarkupContainer repaidEveryVContainer;
    protected Long repaidEveryValue;
    protected ReadOnlyView repaidEveryView;

    protected WebMarkupBlock repaidTypeBlock;
    protected WebMarkupContainer repaidTypeVContainer;
    protected String repaidTypeValue;
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
    protected String interestMethodValue;
    protected ReadOnlyView interestMethodView;

    protected WebMarkupBlock amortizationBlock;
    protected WebMarkupContainer amortizationVContainer;
    protected String amortizationValue;
    protected ReadOnlyView amortizationView;

    protected WebMarkupBlock interestCalculationPeriodBlock;
    protected WebMarkupContainer interestCalculationPeriodVContainer;
    protected String interestCalculationPeriodValue;
    protected ReadOnlyView interestCalculationPeriodView;

    protected WebMarkupBlock calculateInterestForExactDayInPartialPeriodBlock;
    protected WebMarkupContainer calculateInterestForExactDayInPartialPeriodVContainer;
    protected String calculateInterestForExactDayInPartialPeriodValue;
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
    protected String repaymentStrategyValue;
    protected ReadOnlyView repaymentStrategyView;

    protected WebMarkupBlock onPrincipalPaymentBlock;
    protected WebMarkupContainer onPrincipalPaymentVContainer;
    protected Long onPrincipalPaymentValue;
    protected ReadOnlyView onPrincipalPaymentView;

    protected WebMarkupBlock onInterestPaymentBlock;
    protected WebMarkupContainer onInterestPaymentVContainer;
    protected Long onInterestPaymentValue;
    protected ReadOnlyView onInterestPaymentView;

    protected WebMarkupBlock onArrearsAgingBlock;
    protected WebMarkupContainer onArrearsAgingVContainer;
    protected Long onArrearsAgingValue;
    protected ReadOnlyView onArrearsAgingView;

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

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

        initPrincipalBlock();

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

        initOnPrincipalPaymentBlock();

        initOnInterestPaymentBlock();

        initOnArrearsAgingBlock();

        // Table
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

        // Table

        this.collateralPopup = new ModalWindow("collateralPopup");
        add(this.collateralPopup);
        this.collateralPopup.setOnClose(this::collateralPopupClose);

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
        if (ChargeFrequency.Month.getDescription().equals(this.repaidTypeValue)) {
            this.repaidOnIContainer.setVisible(true);
            this.repaidDayIContainer.setVisible(true);
        } else {
            this.repaidOnIContainer.setVisible(false);
            this.repaidDayIContainer.setVisible(false);
        }
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

    protected void initOnPrincipalPaymentBlock() {
        this.onPrincipalPaymentBlock = new WebMarkupBlock("onPrincipalPaymentBlock", Size.Four_4);
        this.form.add(this.onPrincipalPaymentBlock);
        this.onPrincipalPaymentVContainer = new WebMarkupContainer("onPrincipalPaymentVContainer");
        this.onPrincipalPaymentBlock.add(this.onPrincipalPaymentVContainer);
        this.onPrincipalPaymentView = new ReadOnlyView("onPrincipalPaymentView", new PropertyModel<>(this, "onPrincipalPaymentValue"));
        this.onPrincipalPaymentVContainer.add(this.onPrincipalPaymentView);
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
        this.interestFreePeriodBlock = new WebMarkupContainer("interestFreePeriodBlock");
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

    protected void initPrincipalBlock() {
        this.principalBlock = new WebMarkupBlock("principalBlock", Size.Four_4);
        this.form.add(this.principalBlock);
        this.principalIContainer = new WebMarkupContainer("principalIContainer");
        this.principalBlock.add(this.principalIContainer);
        this.principalField = new TextField<>("principalField", new PropertyModel<>(this, "principalValue"));
        this.principalField.setLabel(Model.of("Principal"));
        this.principalField.setRequired(false);
        this.principalIContainer.add(this.principalField);
        this.principalFeedback = new TextFeedbackPanel("principalFeedback", this.principalField);
        this.principalIContainer.add(this.principalFeedback);
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
        this.linkSavingAccountProvider = new SingleChoiceProvider("m_savings_account", "m_savings_account.id", "m_savings_account.account_no", "concat(m_savings_account.account_no, ' => ', m_savings_product.name)");
        this.linkSavingAccountProvider.applyJoin("m_savings_product", "INNER JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.linkSavingAccountProvider.applyWhere("status_enum", "m_savings_account.status_enum = 300");
        this.linkSavingAccountProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);
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
        this.loanOfficerProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.loanOfficerProvider.applyWhere("is_active", "is_active = 1");
        this.loanOfficerProvider.applyWhere("office_id", "office_id = " + this.officeId);
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
        if ("name".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("type".equals(column) || "collectedOn".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        } else if ("date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd MMMM");
        } else if ("repaymentEvery".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void chargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.size(); i++) {
            Map<String, Object> column = this.chargeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.remove(index);
        }
        target.add(this.chargeTable);
    }

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.chargePopup.setContent(new AccountChargePopup("charge", this.chargePopup, this.popupModel, this.currencyValue));
        this.chargePopup.show(target);
        return false;
    }

    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("amount", this.popupModel.get("amountValue"));
        item.put("date", this.popupModel.get("dateValue"));
        item.put("repaymentEvery", this.popupModel.get("repaymentEveryValue"));
        item.put("type", this.popupModel.get("chargeTypeValue"));
        item.put("name", this.popupModel.get("chargeValue"));
        item.put("collectedOn", this.popupModel.get("collectedOnValue"));
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        this.submittedOnValue = DateTime.now().toDate();
        this.externalIdValue = StringUtils.upperCase(UUID.randomUUID().toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.loanId = getPageParameters().get("loanId").toString();

        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        Map<String, Object> loanObject = jdbcTemplate.queryForMap("select * from m_product_loan where id = ?", this.loanId);

        this.officeId = String.valueOf(clientObject.get("office_id"));

        this.productValue = (String) loanObject.get("name");

        this.fundValue = jdbcTemplate.queryForObject("select id, name text from m_fund where id = ?", Option.MAPPER, loanObject.get("fund_id"));

        this.submittedOnValue = DateTime.now().toDate();
        this.disbursementOnValue = DateTime.now().toDate();

        this.externalIdValue = generator.externalId();

        this.currencyValue = (String) loanObject.get("currency_code");
        this.decimalPlacesValue = (Long) loanObject.get("currency_digits");

        this.currencyInMultiplesOfValue = (Long) loanObject.get("currency_multiplesof");
        this.installmentInMultiplesOfValue = (Double) loanObject.get("instalment_amount_in_multiples_of");

        this.principalValue = (Double) loanObject.get("principal_amount");

        this.numberOfRepaymentValue = (Long) loanObject.get("number_of_repayments");
        this.repaidEveryValue = (Long) loanObject.get("repay_every");

        if (this.numberOfRepaymentValue != null && this.repaidEveryValue != null) {
            this.loanTermValue = this.numberOfRepaymentValue * this.repaidEveryValue;
        }

        ChargeFrequency chargeFrequency = ChargeFrequency.parseLiteral(String.valueOf(loanObject.get("repayment_period_frequency_enum")));

        this.loanTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

        this.repaidTypeValue = chargeFrequency == null ? "" : chargeFrequency.getDescription();

        this.firstRepaymentOnValue = DateTime.now().toDate();
        this.interestChargedFromValue = DateTime.now().toDate();

        this.nominalInterestRateValue = (Double) loanObject.get("nominal_interest_rate_per_period");

        NominalInterestRateType nominalInterestTypeValue = NominalInterestRateType.parseLiteral(String.valueOf(loanObject.get("interest_period_frequency_enum")));
        this.nominalInterestTypeValue = nominalInterestTypeValue == null ? "" : nominalInterestTypeValue.getDescription();

        InterestMethod interestMethodValue = InterestMethod.parseLiteral(String.valueOf(loanObject.get("interest_method_enum")));
        this.interestMethodValue = interestMethodValue == null ? "" : interestMethodValue.getDescription();

        Amortization amortizationValue = Amortization.parseLiteral(String.valueOf(loanObject.get("amortization_method_enum")));
        this.amortizationValue = amortizationValue == null ? "" : amortizationValue.getDescription();

        InterestCalculationPeriod interestCalculationPeriodValue = InterestCalculationPeriod.parseLiteral(String.valueOf(loanObject.get("interest_calculated_in_period_enum")));
        this.interestCalculationPeriodValue = interestCalculationPeriodValue == null ? "" : interestCalculationPeriodValue.getDescription();

        Boolean calculateInterestForExactDayInPartialPeriodValue = (Boolean) loanObject.get("allow_partial_period_interest_calcualtion");
        if (calculateInterestForExactDayInPartialPeriodValue != null && calculateInterestForExactDayInPartialPeriodValue) {
            this.calculateInterestForExactDayInPartialPeriodValue = "Yes";
        } else {
            this.calculateInterestForExactDayInPartialPeriodValue = "No";
        }

        RepaymentStrategy repaymentStrategyValue = RepaymentStrategy.parseLiteral(String.valueOf(loanObject.get("loan_transaction_strategy_id")));
        this.repaymentStrategyValue = repaymentStrategyValue == null ? "" : repaymentStrategyValue.getDescription();

        this.arrearsToleranceValue = (Double) loanObject.get("arrearstolerance_amount");

        this.interestFreePeriodValue = (Long) loanObject.get("grace_interest_free_periods");

        this.onArrearsAgingValue = (Long) loanObject.get("grace_on_arrears_ageing");
        this.onInterestPaymentValue = (Long) loanObject.get("grace_on_interest_periods");
        this.onPrincipalPaymentValue = (Long) loanObject.get("grace_on_principal_periods");

    }

    protected void saveButtonSubmit(Button button) {

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);

    }
}