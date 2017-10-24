package com.angkorteam.fintech.pages.client.client;

import java.math.BigDecimal;
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
import org.apache.wicket.markup.html.basic.Label;
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
import com.angkorteam.fintech.widget.TextFeedbackPanel;
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

    protected String productValue;
    protected Label productView;

    protected SingleChoiceProvider loanOfficerProvider;
    protected WebMarkupContainer loanOfficerBlock;
    protected WebMarkupContainer loanOfficerContainer;
    protected Option loanOfficerValue;
    protected Select2SingleChoice<Option> loanOfficerField;
    protected TextFeedbackPanel loanOfficerFeedback;

    protected LoanPurposeProvider loanPurposeProvider;
    protected WebMarkupContainer loanPurposeBlock;
    protected WebMarkupContainer loanPurposeContainer;
    protected Option loanPurposeValue;
    protected Select2SingleChoice<Option> loanPurposeField;
    protected TextFeedbackPanel loanPurposeFeedback;

    protected FundProvider fundProvider;
    protected WebMarkupContainer fundBlock;
    protected WebMarkupContainer fundContainer;
    protected Option fundValue;
    protected Select2SingleChoice<Option> fundField;
    protected TextFeedbackPanel fundFeedback;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected WebMarkupContainer disbursementOnBlock;
    protected WebMarkupContainer disbursementOnContainer;
    protected Date disbursementOnValue;
    protected DateTextField disbursementOnField;
    protected TextFeedbackPanel disbursementOnFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected SingleChoiceProvider linkSavingAccountProvider;
    protected WebMarkupContainer linkSavingAccountBlock;
    protected WebMarkupContainer linkSavingAccountContainer;
    protected Option linkSavingAccountValue;
    protected Select2SingleChoice<Option> linkSavingAccountField;
    protected TextFeedbackPanel linkSavingAccountFeedback;

    protected WebMarkupContainer createStandingInstructionAtDisbursementBlock;
    protected WebMarkupContainer createStandingInstructionAtDisbursementContainer;
    protected Boolean createStandingInstructionAtDisbursementValue;
    protected CheckBox createStandingInstructionAtDisbursementField;
    protected TextFeedbackPanel createStandingInstructionAtDisbursementFeedback;

    protected String currencyValue;
    protected Label currencyView;

    protected Integer decimalPlacesValue;
    protected Label decimalPlacesView;

    protected Integer currencyInMultiplesOfValue;
    protected Label currencyInMultiplesOfView;

    protected Double installmentInMultiplesOfValue;
    protected Label installmentInMultiplesOfView;

    protected WebMarkupContainer principalBlock;
    protected WebMarkupContainer principalContainer;
    protected Double principalValue;
    protected TextField<Double> principalField;
    protected TextFeedbackPanel principalFeedback;

    protected WebMarkupContainer loanTermBlock;
    protected WebMarkupContainer loanTermContainer;
    protected Integer loanTermValue;
    protected TextField<Integer> loanTermField;
    protected TextFeedbackPanel loanTermFeedback;

    protected ChargeFrequencyProvider loanTypeProvider;
    protected WebMarkupContainer loanTypeBlock;
    protected WebMarkupContainer loanTypeContainer;
    protected Option loanTypeValue;
    protected Select2SingleChoice<Option> loanTypeField;
    protected TextFeedbackPanel loanTypeFeedback;

    protected WebMarkupContainer numberOfRepaymentBlock;
    protected WebMarkupContainer numberOfRepaymentContainer;
    protected Integer numberOfRepaymentValue;
    protected TextField<Integer> numberOfRepaymentField;
    protected TextFeedbackPanel numberOfRepaymentFeedback;

    protected Integer repaidEveryValue;
    protected Label repaidEveryView;

    protected String repaidTypeValue;
    protected Label repaidTypeView;

    protected RepaidOnProvider repaidOnProvider;
    protected WebMarkupContainer repaidOnBlock;
    protected WebMarkupContainer repaidOnContainer;
    protected Option repaidOnValue;
    protected Select2SingleChoice<Option> repaidOnField;
    protected TextFeedbackPanel repaidOnFeedback;

    protected FrequencyDayProvider repaidDayProvider;
    protected WebMarkupContainer repaidDayBlock;
    protected WebMarkupContainer repaidDayContainer;
    protected Option repaidDayValue;
    protected Select2SingleChoice<Option> repaidDayField;
    protected TextFeedbackPanel repaidDayFeedback;

    protected WebMarkupContainer firstRepaymentOnBlock;
    protected WebMarkupContainer firstRepaymentOnContainer;
    protected Date firstRepaymentOnValue;
    protected DateTextField firstRepaymentOnField;
    protected TextFeedbackPanel firstRepaymentOnFeedback;

    protected WebMarkupContainer interestChargedFromBlock;
    protected WebMarkupContainer interestChargedFromContainer;
    protected Date interestChargedFromValue;
    protected DateTextField interestChargedFromField;
    protected TextFeedbackPanel interestChargedFromFeedback;

    protected WebMarkupContainer nominalInterestRateBlock;
    protected WebMarkupContainer nominalInterestRateContainer;
    protected Double nominalInterestRateValue;
    protected TextField<Double> nominalInterestRateField;
    protected TextFeedbackPanel nominalInterestRateFeedback;

    protected String nominalInterestTypeValue;
    protected Label nominalInterestTypeView;

    protected String interestMethodValue;
    protected Label interestMethodView;

    protected String amortizationValue;
    protected Label amortizationView;

    protected String interestCalculationPeriodValue;
    protected Label interestCalculationPeriodView;

    protected String calculateInterestForExactDayInPartialPeriodValue;
    protected Label calculateInterestForExactDayInPartialPeriodView;

    protected Double arrearsToleranceValue;
    protected Label arrearsToleranceView;

    protected WebMarkupContainer interestFreePeriodBlock;
    protected WebMarkupContainer interestFreePeriodContainer;
    protected Integer interestFreePeriodValue;
    protected TextField<Integer> interestFreePeriodField;
    protected TextFeedbackPanel interestFreePeriodFeedback;

    protected String repaymentStrategyValue;
    protected Label repaymentStrategyView;

    protected Integer onPrincipalPaymentValue;
    protected Label onPrincipalPaymentView;

    protected Integer onInterestPaymentValue;
    protected Label onInterestPaymentView;

    protected Integer onArrearsAgingValue;
    protected Label onArrearsAgingView;

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;

    protected List<Map<String, Object>> collateralValue;
    protected DataTable<Map<String, Object>, String> collateralTable;
    protected ListDataProvider collateralProvider;
    protected AjaxLink<Void> collateralAddLink;
    protected ModalWindow collateralPopup;
    protected List<IColumn<Map<String, Object>, String>> collateralColumn;

    protected Option itemChargeValue;
    protected String itemChargeTypeValue;
    protected Double itemAmountValue;
    protected String itemCollectedOnValue;
    protected Date itemDateValue;
    protected Integer itemRepaymentEveryValue;
    protected Option itemCollateralValue;
    protected String itemDescriptionValue;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.productView = new Label("productView", new PropertyModel<>(this, "productValue"));
        this.form.add(this.productView);

        this.loanOfficerProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.loanOfficerProvider.applyWhere("is_active", "is_active = 1");
        this.loanOfficerProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.loanOfficerBlock = new WebMarkupContainer("loanOfficerBlock");
        this.loanOfficerBlock.setOutputMarkupId(true);
        this.form.add(this.loanOfficerBlock);
        this.loanOfficerContainer = new WebMarkupContainer("loanOfficerContainer");
        this.loanOfficerBlock.add(this.loanOfficerContainer);
        this.loanOfficerField = new Select2SingleChoice<>("loanOfficerField", new PropertyModel<>(this, "loanOfficerValue"), this.loanOfficerProvider);
        this.loanOfficerField.setLabel(Model.of("Loan Officer"));
        this.loanOfficerField.setRequired(false);
        this.loanOfficerContainer.add(this.loanOfficerField);
        this.loanOfficerFeedback = new TextFeedbackPanel("loanOfficerFeedback", this.loanOfficerField);
        this.loanOfficerContainer.add(this.loanOfficerFeedback);

        this.loanPurposeProvider = new LoanPurposeProvider();
        this.loanPurposeBlock = new WebMarkupContainer("loanPurposeBlock");
        this.loanPurposeBlock.setOutputMarkupId(true);
        this.form.add(this.loanPurposeBlock);
        this.loanPurposeContainer = new WebMarkupContainer("loanPurposeContainer");
        this.loanPurposeBlock.add(this.loanPurposeContainer);
        this.loanPurposeField = new Select2SingleChoice<>("loanPurposeField", new PropertyModel<>(this, "loanPurposeValue"), this.loanPurposeProvider);
        this.loanPurposeField.setLabel(Model.of("Loan Purpose"));
        this.loanPurposeField.setRequired(false);
        this.loanPurposeContainer.add(this.loanPurposeField);
        this.loanPurposeFeedback = new TextFeedbackPanel("loanPurposeFeedback", this.loanPurposeField);
        this.loanPurposeContainer.add(this.loanPurposeFeedback);

        this.fundProvider = new FundProvider();
        this.fundBlock = new WebMarkupContainer("fundBlock");
        this.fundBlock.setOutputMarkupId(true);
        this.form.add(this.fundBlock);
        this.fundContainer = new WebMarkupContainer("fundContainer");
        this.fundBlock.add(this.fundContainer);
        this.fundField = new Select2SingleChoice<>("fundField", new PropertyModel<>(this, "fundValue"), this.fundProvider);
        this.fundField.setLabel(Model.of("Fund"));
        this.fundField.setRequired(false);
        this.fundContainer.add(this.fundField);
        this.fundFeedback = new TextFeedbackPanel("fundFeedback", this.fundField);
        this.fundContainer.add(this.fundFeedback);

        this.submittedOnBlock = new WebMarkupContainer("submittedOnBlock");
        this.submittedOnBlock.setOutputMarkupId(true);
        this.form.add(this.submittedOnBlock);
        this.submittedOnContainer = new WebMarkupContainer("submittedOnContainer");
        this.submittedOnBlock.add(this.submittedOnContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.setRequired(false);
        this.submittedOnContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnContainer.add(this.submittedOnFeedback);

        this.disbursementOnBlock = new WebMarkupContainer("disbursementOnBlock");
        this.disbursementOnBlock.setOutputMarkupId(true);
        this.form.add(this.disbursementOnBlock);
        this.disbursementOnContainer = new WebMarkupContainer("disbursementOnContainer");
        this.disbursementOnBlock.add(this.disbursementOnContainer);
        this.disbursementOnField = new DateTextField("disbursementOnField", new PropertyModel<>(this, "disbursementOnValue"));
        this.disbursementOnField.setLabel(Model.of("Disbursement On"));
        this.disbursementOnField.setRequired(false);
        this.disbursementOnContainer.add(this.disbursementOnField);
        this.disbursementOnFeedback = new TextFeedbackPanel("disbursementOnFeedback", this.disbursementOnField);
        this.disbursementOnContainer.add(this.disbursementOnFeedback);

        this.externalIdBlock = new WebMarkupContainer("externalIdBlock");
        this.externalIdBlock.setOutputMarkupId(true);
        this.form.add(this.externalIdBlock);
        this.externalIdContainer = new WebMarkupContainer("externalIdContainer");
        this.externalIdBlock.add(this.externalIdContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(false);
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdContainer.add(this.externalIdFeedback);

        this.linkSavingAccountProvider = new SingleChoiceProvider("m_savings_account", "m_savings_account.id", "m_savings_account.account_no", "concat(m_savings_account.account_no, ' => ', m_savings_product.name)");
        this.linkSavingAccountProvider.addJoin("INNER JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.linkSavingAccountProvider.applyWhere("status_enum", "m_savings_account.status_enum = 300");
        this.linkSavingAccountProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);
        this.linkSavingAccountBlock = new WebMarkupContainer("linkSavingAccountBlock");
        this.linkSavingAccountBlock.setOutputMarkupId(true);
        this.form.add(this.linkSavingAccountBlock);
        this.linkSavingAccountContainer = new WebMarkupContainer("linkSavingAccountContainer");
        this.linkSavingAccountBlock.add(this.linkSavingAccountContainer);
        this.linkSavingAccountField = new Select2SingleChoice<>("linkSavingAccountField", new PropertyModel<>(this, "linkSavingAccountValue"), this.linkSavingAccountProvider);
        this.linkSavingAccountField.setLabel(Model.of("Saving Account"));
        this.linkSavingAccountField.setRequired(false);
        this.linkSavingAccountContainer.add(this.linkSavingAccountField);
        this.linkSavingAccountFeedback = new TextFeedbackPanel("linkSavingAccountFeedback", this.linkSavingAccountField);
        this.linkSavingAccountContainer.add(this.linkSavingAccountFeedback);

        this.createStandingInstructionAtDisbursementBlock = new WebMarkupContainer("createStandingInstructionAtDisbursementBlock");
        this.createStandingInstructionAtDisbursementBlock.setOutputMarkupId(true);
        this.form.add(this.createStandingInstructionAtDisbursementBlock);
        this.createStandingInstructionAtDisbursementContainer = new WebMarkupContainer("createStandingInstructionAtDisbursementContainer");
        this.createStandingInstructionAtDisbursementBlock.add(this.createStandingInstructionAtDisbursementContainer);
        this.createStandingInstructionAtDisbursementField = new CheckBox("createStandingInstructionAtDisbursementField", new PropertyModel<>(this, "createStandingInstructionAtDisbursementValue"));
        this.createStandingInstructionAtDisbursementField.add(new OnChangeAjaxBehavior());
        this.createStandingInstructionAtDisbursementContainer.add(this.createStandingInstructionAtDisbursementField);
        this.createStandingInstructionAtDisbursementFeedback = new TextFeedbackPanel("createStandingInstructionAtDisbursementFeedback", this.createStandingInstructionAtDisbursementField);
        this.createStandingInstructionAtDisbursementContainer.add(this.createStandingInstructionAtDisbursementFeedback);

        this.currencyView = new Label("currencyView", new PropertyModel<>(this, "currencyValue"));
        this.form.add(this.currencyView);

        this.decimalPlacesView = new Label("decimalPlacesView", new PropertyModel<>(this, "decimalPlacesValue"));
        this.form.add(this.decimalPlacesView);

        this.currencyInMultiplesOfView = new Label("currencyInMultiplesOfView", new PropertyModel<>(this, "currencyInMultiplesOfValue"));
        this.form.add(this.currencyInMultiplesOfView);

        this.installmentInMultiplesOfView = new Label("installmentInMultiplesOfView", new PropertyModel<>(this, "installmentInMultiplesOfValue"));
        this.form.add(this.installmentInMultiplesOfView);

        this.principalBlock = new WebMarkupContainer("principalBlock");
        this.principalBlock.setOutputMarkupId(true);
        this.form.add(this.principalBlock);
        this.principalContainer = new WebMarkupContainer("principalContainer");
        this.principalBlock.add(this.principalContainer);
        this.principalField = new TextField<>("principalField", new PropertyModel<>(this, "principalValue"));
        this.principalField.setLabel(Model.of("Principal"));
        this.principalField.setRequired(false);
        this.principalContainer.add(this.principalField);
        this.principalFeedback = new TextFeedbackPanel("principalFeedback", this.principalField);
        this.principalContainer.add(this.principalFeedback);

        this.loanTermBlock = new WebMarkupContainer("loanTermBlock");
        this.loanTermBlock.setOutputMarkupId(true);
        this.form.add(this.loanTermBlock);
        this.loanTermContainer = new WebMarkupContainer("loanTermContainer");
        this.loanTermBlock.add(this.loanTermContainer);
        this.loanTermField = new TextField<>("loanTermField", new PropertyModel<>(this, "loanTermValue"));
        this.loanTermField.setLabel(Model.of("Loan Term"));
        this.loanTermField.setRequired(false);
        this.loanTermContainer.add(this.loanTermField);
        this.loanTermFeedback = new TextFeedbackPanel("loanTermFeedback", this.loanTermField);
        this.loanTermContainer.add(this.loanTermFeedback);

        this.loanTypeProvider = new ChargeFrequencyProvider();
        this.loanTypeBlock = new WebMarkupContainer("loanTypeBlock");
        this.loanTypeBlock.setOutputMarkupId(true);
        this.form.add(this.loanTypeBlock);
        this.loanTypeContainer = new WebMarkupContainer("loanTypeContainer");
        this.loanTypeBlock.add(this.loanTypeContainer);
        this.loanTypeField = new Select2SingleChoice<>("loanTypeField", new PropertyModel<>(this, "loanTypeValue"), this.loanTypeProvider);
        this.loanTypeField.setLabel(Model.of("Type"));
        this.loanTypeField.setRequired(false);
        this.loanTypeContainer.add(this.loanTypeField);
        this.loanTypeFeedback = new TextFeedbackPanel("loanTypeFeedback", this.loanTypeField);
        this.loanTypeContainer.add(this.loanTypeFeedback);

        this.numberOfRepaymentBlock = new WebMarkupContainer("numberOfRepaymentBlock");
        this.numberOfRepaymentBlock.setOutputMarkupId(true);
        this.form.add(this.numberOfRepaymentBlock);
        this.numberOfRepaymentContainer = new WebMarkupContainer("numberOfRepaymentContainer");
        this.numberOfRepaymentBlock.add(this.numberOfRepaymentContainer);
        this.numberOfRepaymentField = new TextField<>("numberOfRepaymentField", new PropertyModel<>(this, "numberOfRepaymentValue"));
        this.numberOfRepaymentField.setLabel(Model.of("Number Of Repayment"));
        this.numberOfRepaymentField.setRequired(false);
        this.numberOfRepaymentContainer.add(this.numberOfRepaymentField);
        this.numberOfRepaymentFeedback = new TextFeedbackPanel("numberOfRepaymentFeedback", this.numberOfRepaymentField);
        this.numberOfRepaymentContainer.add(this.numberOfRepaymentFeedback);

        this.repaidEveryView = new Label("repaidEveryView", new PropertyModel<>(this, "repaidEveryValue"));
        this.form.add(this.repaidEveryView);

        this.repaidTypeView = new Label("repaidTypeView", new PropertyModel<>(this, "repaidTypeValue"));
        this.form.add(this.repaidTypeView);

        this.repaidOnProvider = new RepaidOnProvider();
        this.repaidOnBlock = new WebMarkupContainer("repaidOnBlock");
        this.repaidOnBlock.setOutputMarkupId(true);
        this.form.add(this.repaidOnBlock);
        this.repaidOnContainer = new WebMarkupContainer("repaidOnContainer");
        this.repaidOnBlock.add(this.repaidOnContainer);
        this.repaidOnField = new Select2SingleChoice<>("repaidOnField", new PropertyModel<>(this, "repaidOnValue"), this.repaidOnProvider);
        this.repaidOnField.setLabel(Model.of("On"));
        this.repaidOnField.setRequired(false);
        this.repaidOnContainer.add(this.repaidOnField);
        this.repaidOnFeedback = new TextFeedbackPanel("repaidOnFeedback", this.repaidOnField);
        this.repaidOnContainer.add(this.repaidOnFeedback);

        this.repaidDayProvider = new FrequencyDayProvider();
        this.repaidDayBlock = new WebMarkupContainer("repaidDayBlock");
        this.repaidDayBlock.setOutputMarkupId(true);
        this.form.add(this.repaidDayBlock);
        this.repaidDayContainer = new WebMarkupContainer("repaidDayContainer");
        this.repaidDayBlock.add(this.repaidDayContainer);
        this.repaidDayField = new Select2SingleChoice<>("repaidDayField", new PropertyModel<>(this, "repaidDayValue"), this.repaidDayProvider);
        this.repaidDayField.setLabel(Model.of("Day"));
        this.repaidDayField.setRequired(false);
        this.repaidDayContainer.add(this.repaidDayField);
        this.repaidDayFeedback = new TextFeedbackPanel("repaidDayFeedback", this.repaidDayField);
        this.repaidDayContainer.add(this.repaidDayFeedback);

        this.firstRepaymentOnBlock = new WebMarkupContainer("firstRepaymentOnBlock");
        this.firstRepaymentOnBlock.setOutputMarkupId(true);
        this.form.add(this.firstRepaymentOnBlock);
        this.firstRepaymentOnContainer = new WebMarkupContainer("firstRepaymentOnContainer");
        this.firstRepaymentOnBlock.add(this.firstRepaymentOnContainer);
        this.firstRepaymentOnField = new DateTextField("firstRepaymentOnField", new PropertyModel<>(this, "firstRepaymentOnValue"));
        this.firstRepaymentOnField.setLabel(Model.of("First Repayment On"));
        this.firstRepaymentOnField.setRequired(false);
        this.firstRepaymentOnContainer.add(this.firstRepaymentOnField);
        this.firstRepaymentOnFeedback = new TextFeedbackPanel("firstRepaymentOnFeedback", this.firstRepaymentOnField);
        this.firstRepaymentOnContainer.add(this.firstRepaymentOnFeedback);

        this.interestChargedFromBlock = new WebMarkupContainer("interestChargedFromBlock");
        this.interestChargedFromBlock.setOutputMarkupId(true);
        this.form.add(this.interestChargedFromBlock);
        this.interestChargedFromContainer = new WebMarkupContainer("interestChargedFromContainer");
        this.interestChargedFromBlock.add(this.interestChargedFromContainer);
        this.interestChargedFromField = new DateTextField("interestChargedFromField", new PropertyModel<>(this, "interestChargedFromValue"));
        this.interestChargedFromField.setLabel(Model.of("Interest Charged From"));
        this.interestChargedFromField.setRequired(false);
        this.interestChargedFromContainer.add(this.interestChargedFromField);
        this.interestChargedFromFeedback = new TextFeedbackPanel("interestChargedFromFeedback", this.interestChargedFromField);
        this.interestChargedFromContainer.add(this.interestChargedFromFeedback);

        this.nominalInterestRateBlock = new WebMarkupContainer("nominalInterestRateBlock");
        this.nominalInterestRateBlock.setOutputMarkupId(true);
        this.form.add(this.nominalInterestRateBlock);
        this.nominalInterestRateContainer = new WebMarkupContainer("nominalInterestRateContainer");
        this.nominalInterestRateBlock.add(this.nominalInterestRateContainer);
        this.nominalInterestRateField = new TextField<>("nominalInterestRateField", new PropertyModel<>(this, "nominalInterestRateValue"));
        this.nominalInterestRateField.setLabel(Model.of("Nominal Interest Rate"));
        this.nominalInterestRateField.setRequired(false);
        this.nominalInterestRateContainer.add(this.nominalInterestRateField);
        this.nominalInterestRateFeedback = new TextFeedbackPanel("nominalInterestRateFeedback", this.nominalInterestRateField);
        this.nominalInterestRateContainer.add(this.nominalInterestRateFeedback);

        this.nominalInterestTypeView = new Label("nominalInterestTypeView", new PropertyModel<>(this, "nominalInterestTypeValue"));
        this.form.add(this.nominalInterestTypeView);

        this.interestMethodView = new Label("interestMethodView", new PropertyModel<>(this, "interestMethodValue"));
        this.form.add(this.interestMethodView);

        this.amortizationView = new Label("amortizationView", new PropertyModel<>(this, "amortizationValue"));
        this.form.add(this.amortizationView);

        this.interestCalculationPeriodView = new Label("interestCalculationPeriodView", new PropertyModel<>(this, "interestCalculationPeriodValue"));
        this.form.add(this.interestCalculationPeriodView);

        this.calculateInterestForExactDayInPartialPeriodView = new Label("calculateInterestForExactDayInPartialPeriodView", new PropertyModel<>(this, "calculateInterestForExactDayInPartialPeriodValue"));
        this.form.add(this.calculateInterestForExactDayInPartialPeriodView);

        this.arrearsToleranceView = new Label("arrearsToleranceView", new PropertyModel<>(this, "arrearsToleranceValue"));
        this.form.add(this.arrearsToleranceView);

        this.interestFreePeriodBlock = new WebMarkupContainer("interestFreePeriodBlock");
        this.interestFreePeriodBlock.setOutputMarkupId(true);
        this.form.add(this.interestFreePeriodBlock);
        this.interestFreePeriodContainer = new WebMarkupContainer("interestFreePeriodContainer");
        this.interestFreePeriodBlock.add(this.interestFreePeriodContainer);
        this.interestFreePeriodField = new TextField<>("interestFreePeriodField", new PropertyModel<>(this, "interestFreePeriodValue"));
        this.interestFreePeriodField.setLabel(Model.of("Interest Free Period"));
        this.interestFreePeriodField.setRequired(false);
        this.interestFreePeriodContainer.add(this.interestFreePeriodField);
        this.interestFreePeriodFeedback = new TextFeedbackPanel("interestFreePeriodFeedback", this.interestFreePeriodField);
        this.interestFreePeriodContainer.add(this.interestFreePeriodFeedback);

        this.repaymentStrategyView = new Label("repaymentStrategyView", new PropertyModel<>(this, "repaymentStrategyValue"));
        this.form.add(this.repaymentStrategyView);

        this.onPrincipalPaymentView = new Label("onPrincipalPaymentView", new PropertyModel<>(this, "onPrincipalPaymentValue"));
        this.form.add(this.onPrincipalPaymentView);

        this.onInterestPaymentView = new Label("onInterestPaymentView", new PropertyModel<>(this, "onInterestPaymentValue"));
        this.form.add(this.onInterestPaymentView);

        this.onArrearsAgingView = new Label("onArrearsAgingView", new PropertyModel<>(this, "onArrearsAgingValue"));
        this.form.add(this.onArrearsAgingView);

        // Table
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newLinkedList();
        chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeNameColumn));
        chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeTypeColumn));
        chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeAmountColumn));
        chargeColumn.add(new TextColumn(Model.of("Collected On"), "collectedOn", "collectedOn", this::chargeCollectedOnColumn));
        chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeDateColumn));
        chargeColumn.add(new TextColumn(Model.of("Repayments Every"), "repaymentEvery", "repaymentEvery", this::chargeRepaymentEveryColumn));
        chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeActionItem, this::chargeActionClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", chargeColumn, this.chargeProvider, 20);
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

        initDefault();

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

    protected void collateralPopupClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = null;
        for (Map<String, Object> temp : this.collateralValue) {
            if (this.itemCollateralValue.getId().equals(temp.get("uuid"))) {
                item = temp;
                break;
            }
        }
        if (item == null) {
            item = Maps.newHashMap();
            item.put("uuid", this.itemCollateralValue.getId());
            item.put("collateral", this.itemCollateralValue);
            item.put("value", this.itemAmountValue);
            item.put("description", this.itemDescriptionValue);
            this.collateralValue.add(item);
        } else {
            item.put("uuid", this.itemCollateralValue.getId());
            item.put("collateral", this.itemCollateralValue);
            item.put("value", this.itemAmountValue);
            item.put("description", this.itemDescriptionValue);
        }

        target.add(this.collateralTable);
    }

    protected boolean collateralAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemCollateralValue = null;
        this.itemAmountValue = null;
        this.itemDescriptionValue = null;
        this.collateralPopup.setContent(new CollateralPopup(this.collateralPopup.getContentId(), this.collateralPopup, this));
        this.collateralPopup.show(target);
        return false;
    }

    protected void initDefault() {
        if (ChargeFrequency.Month.getDescription().equals(this.repaidTypeValue)) {
            this.repaidOnContainer.setVisible(true);
            this.repaidDayContainer.setVisible(true);
        } else {
            this.repaidOnContainer.setVisible(false);
            this.repaidDayContainer.setVisible(false);
        }

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

    protected ItemPanel chargeCollectedOnColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd MMMM");
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel chargeRepaymentEveryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void chargeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemChargeTypeValue = null;
        this.itemAmountValue = null;
        this.itemCollectedOnValue = null;
        this.itemDateValue = null;
        this.itemRepaymentEveryValue = null;
        this.chargePopup.setContent(new AccountChargePopup(this.chargePopup.getContentId(), this.chargePopup, this, this.currencyValue));
        this.chargePopup.show(target);
        return false;
    }

    protected void chargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("amount", this.itemAmountValue);
        item.put("date", this.itemDateValue);
        item.put("repaymentEvery", this.itemRepaymentEveryValue);
        item.put("type", this.itemChargeTypeValue);
        item.put("name", this.itemChargeValue.getText());
        item.put("collectedOn", this.itemCollectedOnValue);
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected void initData() {
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
        this.decimalPlacesValue = (Integer) loanObject.get("currency_digits");

        this.currencyInMultiplesOfValue = (Integer) loanObject.get("currency_multiplesof");
        if (loanObject.get("instalment_amount_in_multiples_of") != null) {
            this.installmentInMultiplesOfValue = ((BigDecimal) loanObject.get("instalment_amount_in_multiples_of")).doubleValue();
        }

        if (loanObject.get("principal_amount") != null) {
            this.principalValue = ((BigDecimal) loanObject.get("principal_amount")).doubleValue();
        }

        this.numberOfRepaymentValue = (Integer) loanObject.get("number_of_repayments");
        this.repaidEveryValue = (Integer) loanObject.get("repay_every");

        if (this.numberOfRepaymentValue != null && this.repaidEveryValue != null) {
            this.loanTermValue = this.numberOfRepaymentValue * this.repaidEveryValue;
        }

        ChargeFrequency chargeFrequency = ChargeFrequency.parseLiteral(String.valueOf(loanObject.get("repayment_period_frequency_enum")));

        this.loanTypeValue = chargeFrequency == null ? null : chargeFrequency.toOption();

        this.repaidTypeValue = chargeFrequency == null ? "" : chargeFrequency.getDescription();

        this.firstRepaymentOnValue = DateTime.now().toDate();
        this.interestChargedFromValue = DateTime.now().toDate();

        if (loanObject.get("nominal_interest_rate_per_period") != null) {
            this.nominalInterestRateValue = ((BigDecimal) loanObject.get("nominal_interest_rate_per_period")).doubleValue();
        }

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

        if (loanObject.get("arrearstolerance_amount") != null) {
            this.arrearsToleranceValue = ((BigDecimal) loanObject.get("arrearstolerance_amount")).doubleValue();
        }

        this.interestFreePeriodValue = (Integer) loanObject.get("grace_interest_free_periods");

        this.onArrearsAgingValue = (Integer) loanObject.get("grace_on_arrears_ageing");
        this.onInterestPaymentValue = (Integer) loanObject.get("grace_on_interest_periods");
        this.onPrincipalPaymentValue = (Integer) loanObject.get("grace_on_principal_periods");

    }

    protected void saveButtonSubmit(Button button) {

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);

    }
}