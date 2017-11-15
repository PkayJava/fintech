package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
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
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountBuilder;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.popup.AccountChargePopup;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
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
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountCreatePage extends Page {

    protected String clientId;
    protected String savingId;
    protected String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock productBlock;
    protected WebMarkupContainer productVContainer;
    protected String productValue;
    protected ReadOnlyView productView;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected SingleChoiceProvider officerProvider;
    protected WebMarkupBlock officerBlock;
    protected WebMarkupContainer officerIContainer;
    protected Option officerValue;
    protected Select2SingleChoice<Option> officerField;
    protected TextFeedbackPanel officerFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyVContainer;
    protected String currencyValue;
    protected ReadOnlyView currencyView;

    protected WebMarkupBlock decimalPlacesBlock;
    protected WebMarkupContainer decimalPlacesVContainer;
    protected Integer decimalPlacesValue;
    protected ReadOnlyView decimalPlacesView;

    protected WebMarkupBlock nominalAnnualInterestBlock;
    protected WebMarkupContainer nominalAnnualInterestIContainer;
    protected Double nominalAnnualInterestValue;
    protected TextField<Double> nominalAnnualInterestField;
    protected TextFeedbackPanel nominalAnnualInterestFeedback;

    protected InterestCompoundingPeriodProvider interestCompoundingPeriodProvider;
    protected WebMarkupBlock interestCompoundingPeriodBlock;
    protected WebMarkupContainer interestCompoundingPeriodIContainer;
    protected Option interestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> interestCompoundingPeriodField;
    protected TextFeedbackPanel interestCompoundingPeriodFeedback;

    protected WebMarkupBlock currencyInMultiplesOfBlock;
    protected WebMarkupContainer currencyInMultiplesOfVContainer;
    protected Integer currencyInMultiplesOfValue;
    protected ReadOnlyView currencyInMultiplesOfView;

    protected InterestPostingPeriodProvider interestPostingPeriodProvider;
    protected WebMarkupBlock interestPostingPeriodBlock;
    protected WebMarkupContainer interestPostingPeriodIContainer;
    protected Option interestPostingPeriodValue;
    protected Select2SingleChoice<Option> interestPostingPeriodField;
    protected TextFeedbackPanel interestPostingPeriodFeedback;

    protected InterestCalculatedUsingProvider interestCalculatedUsingProvider;
    protected WebMarkupBlock interestCalculatedUsingBlock;
    protected WebMarkupContainer interestCalculatedUsingIContainer;
    protected Option interestCalculatedUsingValue;
    protected Select2SingleChoice<Option> interestCalculatedUsingField;
    protected TextFeedbackPanel interestCalculatedUsingFeedback;

    protected DayInYearProvider dayInYearProvider;
    protected WebMarkupBlock dayInYearBlock;
    protected WebMarkupContainer dayInYearIContainer;
    protected Option dayInYearValue;
    protected Select2SingleChoice<Option> dayInYearField;
    protected TextFeedbackPanel dayInYearFeedback;

    protected WebMarkupBlock minimumOpeningBalanceBlock;
    protected WebMarkupContainer minimumOpeningBalanceIContainer;
    protected Double minimumOpeningBalanceValue;
    protected TextField<Double> minimumOpeningBalanceField;
    protected TextFeedbackPanel minimumOpeningBalanceFeedback;

    protected WebMarkupContainer lockInPeriodBlock;
    protected WebMarkupContainer lockInPeriodIContainer;
    protected Integer lockInPeriodValue;
    protected TextField<Integer> lockInPeriodField;
    protected TextFeedbackPanel lockInPeriodFeedback;

    protected LockInTypeProvider lockInTypeProvider;
    protected WebMarkupBlock lockInTypeBlock;
    protected WebMarkupContainer lockInTypeIContainer;
    protected Option lockInTypeValue;
    protected Select2SingleChoice<Option> lockInTypeField;
    protected TextFeedbackPanel lockInTypeFeedback;

    protected WebMarkupBlock applyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer applyWithdrawalFeeForTransferIContainer;
    protected Boolean applyWithdrawalFeeForTransferValue;
    protected CheckBox applyWithdrawalFeeForTransferField;
    protected TextFeedbackPanel applyWithdrawalFeeForTransferFeedback;

    protected WebMarkupBlock overdraftAllowedBlock;
    protected WebMarkupContainer overdraftAllowedIContainer;
    protected Boolean overdraftAllowedValue;
    protected CheckBox overdraftAllowedField;
    protected TextFeedbackPanel overdraftAllowedFeedback;

    protected WebMarkupBlock maximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer maximumOverdraftAmountLimitIContainer;
    protected Double maximumOverdraftAmountLimitValue;
    protected TextField<Double> maximumOverdraftAmountLimitField;
    protected TextFeedbackPanel maximumOverdraftAmountLimitFeedback;

    protected WebMarkupBlock nominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer nominalAnnualInterestForOverdraftIContainer;
    protected Double nominalAnnualInterestForOverdraftValue;
    protected TextField<Double> nominalAnnualInterestForOverdraftField;
    protected TextFeedbackPanel nominalAnnualInterestForOverdraftFeedback;

    protected WebMarkupBlock minOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer minOverdraftRequiredForInterestCalculationIContainer;
    protected Double minOverdraftRequiredForInterestCalculationValue;
    protected TextField<Double> minOverdraftRequiredForInterestCalculationField;
    protected TextFeedbackPanel minOverdraftRequiredForInterestCalculationFeedback;

    protected WebMarkupBlock enforceMinimumBalanceBlock;
    protected WebMarkupContainer enforceMinimumBalanceIContainer;
    protected Boolean enforceMinimumBalanceValue;
    protected CheckBox enforceMinimumBalanceField;
    protected TextFeedbackPanel enforceMinimumBalanceFeedback;

    protected WebMarkupBlock minimumBalanceBlock;
    protected WebMarkupContainer minimumBalanceIContainer;
    protected Double minimumBalanceValue;
    protected TextField<Double> minimumBalanceField;
    protected TextFeedbackPanel minimumBalanceFeedback;

    protected WebMarkupBlock balanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer balanceRequiredForInterestCalculationVContainer;
    protected Double balanceRequiredForInterestCalculationValue;
    protected ReadOnlyView balanceRequiredForInterestCalculationView;

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;

    protected Map<String, Object> popupModel;

    @Override
    protected void initComponent() {
        this.popupModel = Maps.newHashMap();
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

        initSubmittedOnBlock();

        initOfficerBlock();

        initExternalIdBlock();

        initCurrencyBlock();

        initDecimalPlacesBlock();

        initNominalAnnualInterestBlock();

        initInterestCompoundingPeriodBlock();

        initCurrencyInMultiplesOfBlock();

        initInterestPostingPeriodBlock();

        initInterestCalculatedUsingBlock();

        initDayInYearBlock();

        initMinimumOpeningBalanceBlock();

        initLockInPeriodBlock();

        initLockInTypeBlock();

        initApplyWithdrawalFeeForTransferBlock();

        initOverdraftAllowedBlock();

        initMaximumOverdraftAmountLimitBlock();

        initNominalAnnualInterestForOverdraftBlock();

        initMinOverdraftRequiredForInterestCalculationBlock();

        initEnforceMinimumBalanceBlock();

        initMinimumBalanceBlock();

        initBalanceRequiredForInterestCalculationBlock();

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
    }

    protected void initBalanceRequiredForInterestCalculationBlock() {
        this.balanceRequiredForInterestCalculationBlock = new WebMarkupBlock("balanceRequiredForInterestCalculationBlock", Size.Twelve_12);
        this.form.add(this.balanceRequiredForInterestCalculationBlock);
        this.balanceRequiredForInterestCalculationVContainer = new WebMarkupContainer("balanceRequiredForInterestCalculationVContainer");
        this.balanceRequiredForInterestCalculationBlock.add(this.balanceRequiredForInterestCalculationVContainer);
        this.balanceRequiredForInterestCalculationView = new ReadOnlyView("balanceRequiredForInterestCalculationView", new PropertyModel<>(this, "balanceRequiredForInterestCalculationValue"));
        this.balanceRequiredForInterestCalculationVContainer.add(this.balanceRequiredForInterestCalculationView);
    }

    protected void initMinimumBalanceBlock() {
        this.minimumBalanceBlock = new WebMarkupBlock("minimumBalanceBlock", Size.Six_6);
        this.form.add(this.minimumBalanceBlock);
        this.minimumBalanceIContainer = new WebMarkupContainer("minimumBalanceIContainer");
        this.minimumBalanceBlock.add(this.minimumBalanceIContainer);
        this.minimumBalanceField = new TextField<>("minimumBalanceField", new PropertyModel<>(this, "minimumBalanceValue"));
        this.minimumBalanceField.setLabel(Model.of("Minimum Balance"));
        this.minimumBalanceField.setRequired(false);
        this.minimumBalanceIContainer.add(this.minimumBalanceField);
        this.minimumBalanceFeedback = new TextFeedbackPanel("minimumBalanceFeedback", this.minimumBalanceField);
        this.minimumBalanceIContainer.add(this.minimumBalanceFeedback);
    }

    protected void initEnforceMinimumBalanceBlock() {
        this.enforceMinimumBalanceBlock = new WebMarkupBlock("enforceMinimumBalanceBlock", Size.Six_6);
        this.form.add(this.enforceMinimumBalanceBlock);
        this.enforceMinimumBalanceIContainer = new WebMarkupContainer("enforceMinimumBalanceIContainer");
        this.enforceMinimumBalanceBlock.add(this.enforceMinimumBalanceIContainer);
        this.enforceMinimumBalanceField = new CheckBox("enforceMinimumBalanceField", new PropertyModel<>(this, "enforceMinimumBalanceValue"));
        this.enforceMinimumBalanceField.setRequired(false);
        this.enforceMinimumBalanceIContainer.add(this.enforceMinimumBalanceField);
        this.enforceMinimumBalanceFeedback = new TextFeedbackPanel("enforceMinimumBalanceFeedback", this.enforceMinimumBalanceField);
        this.enforceMinimumBalanceIContainer.add(this.enforceMinimumBalanceFeedback);
    }

    protected void initMinOverdraftRequiredForInterestCalculationBlock() {
        this.minOverdraftRequiredForInterestCalculationBlock = new WebMarkupBlock("minOverdraftRequiredForInterestCalculationBlock", Size.Six_6);
        this.form.add(this.minOverdraftRequiredForInterestCalculationBlock);
        this.minOverdraftRequiredForInterestCalculationIContainer = new WebMarkupContainer("minOverdraftRequiredForInterestCalculationIContainer");
        this.minOverdraftRequiredForInterestCalculationBlock.add(this.minOverdraftRequiredForInterestCalculationIContainer);
        this.minOverdraftRequiredForInterestCalculationField = new TextField<>("minOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this, "minOverdraftRequiredForInterestCalculationValue"));
        this.minOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
        this.minOverdraftRequiredForInterestCalculationField.setRequired(false);
        this.minOverdraftRequiredForInterestCalculationIContainer.add(this.minOverdraftRequiredForInterestCalculationField);
        this.minOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel("minOverdraftRequiredForInterestCalculationFeedback", this.minOverdraftRequiredForInterestCalculationField);
        this.minOverdraftRequiredForInterestCalculationIContainer.add(this.minOverdraftRequiredForInterestCalculationFeedback);
    }

    protected void initNominalAnnualInterestForOverdraftBlock() {
        this.nominalAnnualInterestForOverdraftBlock = new WebMarkupBlock("nominalAnnualInterestForOverdraftBlock", Size.Six_6);
        this.form.add(this.nominalAnnualInterestForOverdraftBlock);
        this.nominalAnnualInterestForOverdraftIContainer = new WebMarkupContainer("nominalAnnualInterestForOverdraftIContainer");
        this.nominalAnnualInterestForOverdraftBlock.add(this.nominalAnnualInterestForOverdraftIContainer);
        this.nominalAnnualInterestForOverdraftField = new TextField<>("nominalAnnualInterestForOverdraftField", new PropertyModel<>(this, "nominalAnnualInterestForOverdraftValue"));
        this.nominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal Annual Interest For Overdraft"));
        this.nominalAnnualInterestForOverdraftField.setRequired(false);
        this.nominalAnnualInterestForOverdraftIContainer.add(this.nominalAnnualInterestForOverdraftField);
        this.nominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel("nominalAnnualInterestForOverdraftFeedback", this.nominalAnnualInterestForOverdraftField);
        this.nominalAnnualInterestForOverdraftIContainer.add(this.nominalAnnualInterestForOverdraftFeedback);
    }

    protected void initMaximumOverdraftAmountLimitBlock() {
        this.maximumOverdraftAmountLimitBlock = new WebMarkupBlock("maximumOverdraftAmountLimitBlock", Size.Six_6);
        this.form.add(this.maximumOverdraftAmountLimitBlock);
        this.maximumOverdraftAmountLimitIContainer = new WebMarkupContainer("maximumOverdraftAmountLimitIContainer");
        this.maximumOverdraftAmountLimitBlock.add(this.maximumOverdraftAmountLimitIContainer);
        this.maximumOverdraftAmountLimitField = new TextField<>("maximumOverdraftAmountLimitField", new PropertyModel<>(this, "maximumOverdraftAmountLimitValue"));
        this.maximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
        this.maximumOverdraftAmountLimitField.setRequired(false);
        this.maximumOverdraftAmountLimitIContainer.add(this.maximumOverdraftAmountLimitField);
        this.maximumOverdraftAmountLimitFeedback = new TextFeedbackPanel("maximumOverdraftAmountLimitFeedback", this.maximumOverdraftAmountLimitField);
        this.maximumOverdraftAmountLimitIContainer.add(this.maximumOverdraftAmountLimitFeedback);
    }

    protected void initOverdraftAllowedBlock() {
        this.overdraftAllowedBlock = new WebMarkupBlock("overdraftAllowedBlock", Size.Six_6);
        this.form.add(this.overdraftAllowedBlock);
        this.overdraftAllowedIContainer = new WebMarkupContainer("overdraftAllowedIContainer");
        this.overdraftAllowedBlock.add(this.overdraftAllowedIContainer);
        this.overdraftAllowedField = new CheckBox("overdraftAllowedField", new PropertyModel<>(this, "overdraftAllowedValue"));
        this.overdraftAllowedField.add(new OnChangeAjaxBehavior(this::overdraftAllowedFieldUpdate));
        this.overdraftAllowedField.setRequired(false);
        this.overdraftAllowedIContainer.add(this.overdraftAllowedField);
        this.overdraftAllowedFeedback = new TextFeedbackPanel("overdraftAllowedFeedback", this.overdraftAllowedField);
        this.overdraftAllowedIContainer.add(this.overdraftAllowedFeedback);
    }

    protected void initApplyWithdrawalFeeForTransferBlock() {
        this.applyWithdrawalFeeForTransferBlock = new WebMarkupBlock("applyWithdrawalFeeForTransferBlock", Size.Six_6);
        this.form.add(this.applyWithdrawalFeeForTransferBlock);
        this.applyWithdrawalFeeForTransferIContainer = new WebMarkupContainer("applyWithdrawalFeeForTransferIContainer");
        this.applyWithdrawalFeeForTransferBlock.add(this.applyWithdrawalFeeForTransferIContainer);
        this.applyWithdrawalFeeForTransferField = new CheckBox("applyWithdrawalFeeForTransferField", new PropertyModel<>(this, "applyWithdrawalFeeForTransferValue"));
        this.applyWithdrawalFeeForTransferField.setRequired(false);
        this.applyWithdrawalFeeForTransferIContainer.add(this.applyWithdrawalFeeForTransferField);
        this.applyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel("applyWithdrawalFeeForTransferFeedback", this.applyWithdrawalFeeForTransferField);
        this.applyWithdrawalFeeForTransferIContainer.add(this.applyWithdrawalFeeForTransferFeedback);
    }

    protected void initLockInTypeBlock() {
        this.lockInTypeProvider = new LockInTypeProvider();
        this.lockInTypeBlock = new WebMarkupBlock("lockInTypeBlock", Size.Six_6);
        this.form.add(this.lockInTypeBlock);
        this.lockInTypeIContainer = new WebMarkupContainer("lockInTypeIContainer");
        this.lockInTypeBlock.add(this.lockInTypeIContainer);
        this.lockInTypeField = new Select2SingleChoice<>("lockInTypeField", new PropertyModel<>(this, "lockInTypeValue"), this.lockInTypeProvider);
        this.lockInTypeField.setLabel(Model.of("Lock In Period"));
        this.lockInTypeField.setRequired(false);
        this.lockInTypeIContainer.add(this.lockInTypeField);
        this.lockInTypeFeedback = new TextFeedbackPanel("lockInTypeFeedback", this.lockInTypeField);
        this.lockInTypeIContainer.add(this.lockInTypeFeedback);
    }

    protected void initLockInPeriodBlock() {
        this.lockInPeriodBlock = new WebMarkupBlock("lockInPeriodBlock", Size.Six_6);
        this.form.add(this.lockInPeriodBlock);
        this.lockInPeriodIContainer = new WebMarkupContainer("lockInPeriodIContainer");
        this.lockInPeriodBlock.add(this.lockInPeriodIContainer);
        this.lockInPeriodField = new TextField<>("lockInPeriodField", new PropertyModel<>(this, "lockInPeriodValue"));
        this.lockInPeriodField.setLabel(Model.of("Lock In Period"));
        this.lockInPeriodField.setRequired(false);
        this.lockInPeriodIContainer.add(this.lockInPeriodField);
        this.lockInPeriodFeedback = new TextFeedbackPanel("lockInPeriodFeedback", this.lockInPeriodField);
        this.lockInPeriodIContainer.add(this.lockInPeriodFeedback);
    }

    protected void initMinimumOpeningBalanceBlock() {
        this.minimumOpeningBalanceBlock = new WebMarkupBlock("minimumOpeningBalanceBlock", Size.Six_6);
        this.form.add(this.minimumOpeningBalanceBlock);
        this.minimumOpeningBalanceIContainer = new WebMarkupContainer("minimumOpeningBalanceIContainer");
        this.minimumOpeningBalanceBlock.add(this.minimumOpeningBalanceIContainer);
        this.minimumOpeningBalanceField = new TextField<>("minimumOpeningBalanceField", new PropertyModel<>(this, "minimumOpeningBalanceValue"));
        this.minimumOpeningBalanceField.setLabel(Model.of("Minimum Opening Balance"));
        this.minimumOpeningBalanceField.setRequired(false);
        this.minimumOpeningBalanceIContainer.add(this.minimumOpeningBalanceField);
        this.minimumOpeningBalanceFeedback = new TextFeedbackPanel("minimumOpeningBalanceFeedback", this.minimumOpeningBalanceField);
        this.minimumOpeningBalanceIContainer.add(this.minimumOpeningBalanceFeedback);
    }

    protected void initDayInYearBlock() {
        this.dayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.dayInYearBlock = new WebMarkupBlock("dayInYearBlock", Size.Six_6);
        this.form.add(this.dayInYearBlock);
        this.dayInYearIContainer = new WebMarkupContainer("dayInYearIContainer");
        this.dayInYearBlock.add(this.dayInYearIContainer);
        this.dayInYearField = new Select2SingleChoice<>("dayInYearField", new PropertyModel<>(this, "dayInYearValue"), this.dayInYearProvider);
        this.dayInYearField.setLabel(Model.of("Days In Year"));
        this.dayInYearField.setRequired(false);
        this.dayInYearIContainer.add(this.dayInYearField);
        this.dayInYearFeedback = new TextFeedbackPanel("dayInYearFeedback", this.dayInYearField);
        this.dayInYearIContainer.add(this.dayInYearFeedback);
    }

    protected void initInterestCalculatedUsingBlock() {
        this.interestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.interestCalculatedUsingBlock = new WebMarkupBlock("interestCalculatedUsingBlock", Size.Six_6);
        this.form.add(this.interestCalculatedUsingBlock);
        this.interestCalculatedUsingIContainer = new WebMarkupContainer("interestCalculatedUsingIContainer");
        this.interestCalculatedUsingBlock.add(this.interestCalculatedUsingIContainer);
        this.interestCalculatedUsingField = new Select2SingleChoice<>("interestCalculatedUsingField", new PropertyModel<>(this, "interestCalculatedUsingValue"), this.interestCalculatedUsingProvider);
        this.interestCalculatedUsingField.setLabel(Model.of("Interest Calculated Using"));
        this.interestCalculatedUsingField.setRequired(false);
        this.interestCalculatedUsingIContainer.add(this.interestCalculatedUsingField);
        this.interestCalculatedUsingFeedback = new TextFeedbackPanel("interestCalculatedUsingFeedback", this.interestCalculatedUsingField);
        this.interestCalculatedUsingIContainer.add(this.interestCalculatedUsingFeedback);
    }

    protected void initInterestPostingPeriodBlock() {
        this.interestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.interestPostingPeriodBlock = new WebMarkupBlock("interestPostingPeriodBlock", Size.Six_6);
        this.form.add(this.interestPostingPeriodBlock);
        this.interestPostingPeriodIContainer = new WebMarkupContainer("interestPostingPeriodIContainer");
        this.interestPostingPeriodBlock.add(this.interestPostingPeriodIContainer);
        this.interestPostingPeriodField = new Select2SingleChoice<>("interestPostingPeriodField", new PropertyModel<>(this, "interestPostingPeriodValue"), this.interestPostingPeriodProvider);
        this.interestPostingPeriodField.setLabel(Model.of("Interest Posting Period"));
        this.interestPostingPeriodField.setRequired(false);
        this.interestPostingPeriodIContainer.add(this.interestPostingPeriodField);
        this.interestPostingPeriodFeedback = new TextFeedbackPanel("interestPostingPeriodFeedback", this.interestPostingPeriodField);
        this.interestPostingPeriodIContainer.add(this.interestPostingPeriodFeedback);
    }

    protected void initCurrencyInMultiplesOfBlock() {
        this.currencyInMultiplesOfBlock = new WebMarkupBlock("currencyInMultiplesOfBlock", Size.Six_6);
        this.form.add(this.currencyInMultiplesOfBlock);
        this.currencyInMultiplesOfVContainer = new WebMarkupContainer("currencyInMultiplesOfVContainer");
        this.currencyInMultiplesOfBlock.add(this.currencyInMultiplesOfVContainer);
        this.currencyInMultiplesOfView = new ReadOnlyView("currencyInMultiplesOfView", new PropertyModel<>(this, "currencyInMultiplesOfValue"));
        this.currencyInMultiplesOfVContainer.add(this.currencyInMultiplesOfView);
    }

    protected void initInterestCompoundingPeriodBlock() {
        this.interestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.interestCompoundingPeriodBlock = new WebMarkupBlock("interestCompoundingPeriodBlock", Size.Six_6);
        this.form.add(this.interestCompoundingPeriodBlock);
        this.interestCompoundingPeriodIContainer = new WebMarkupContainer("interestCompoundingPeriodIContainer");
        this.interestCompoundingPeriodBlock.add(this.interestCompoundingPeriodIContainer);
        this.interestCompoundingPeriodField = new Select2SingleChoice<>("interestCompoundingPeriodField", new PropertyModel<>(this, "interestCompoundingPeriodValue"), this.interestCompoundingPeriodProvider);
        this.interestCompoundingPeriodField.setLabel(Model.of("Interest Compounding Period"));
        this.interestCompoundingPeriodField.setRequired(false);
        this.interestCompoundingPeriodIContainer.add(this.interestCompoundingPeriodField);
        this.interestCompoundingPeriodFeedback = new TextFeedbackPanel("interestCompoundingPeriodFeedback", this.interestCompoundingPeriodField);
        this.interestCompoundingPeriodIContainer.add(this.interestCompoundingPeriodFeedback);
    }

    protected void initNominalAnnualInterestBlock() {
        this.nominalAnnualInterestBlock = new WebMarkupBlock("nominalAnnualInterestBlock", Size.Six_6);
        this.form.add(this.nominalAnnualInterestBlock);
        this.nominalAnnualInterestIContainer = new WebMarkupContainer("nominalAnnualInterestIContainer");
        this.nominalAnnualInterestBlock.add(this.nominalAnnualInterestIContainer);
        this.nominalAnnualInterestField = new TextField<>("nominalAnnualInterestField", new PropertyModel<>(this, "nominalAnnualInterestValue"));
        this.nominalAnnualInterestField.setLabel(Model.of("Nominal Annual Interest"));
        this.nominalAnnualInterestField.setRequired(false);
        this.nominalAnnualInterestIContainer.add(this.nominalAnnualInterestField);
        this.nominalAnnualInterestFeedback = new TextFeedbackPanel("nominalAnnualInterestFeedback", this.nominalAnnualInterestField);
        this.nominalAnnualInterestIContainer.add(this.nominalAnnualInterestFeedback);
    }

    protected void initDecimalPlacesBlock() {
        this.decimalPlacesBlock = new WebMarkupBlock("decimalPlacesBlock", Size.Twelve_12);
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

    protected void initOfficerBlock() {
        this.officerProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.officerProvider.applyWhere("is_active", "is_active = 1");
        this.officerProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
        this.form.add(this.officerBlock);
        this.officerIContainer = new WebMarkupContainer("officerIContainer");
        this.officerBlock.add(this.officerIContainer);
        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
        this.officerField.setLabel(Model.of("Officer"));
        this.officerField.setRequired(false);
        this.officerIContainer.add(this.officerField);
        this.officerFeedback = new TextFeedbackPanel("officerFeedback", this.officerField);
        this.officerIContainer.add(this.officerFeedback);
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

    protected void initProductBlock() {
        this.productBlock = new WebMarkupBlock("productBlock", Size.Six_6);
        this.form.add(this.productBlock);
        this.productVContainer = new WebMarkupContainer("productVContainer");
        this.productBlock.add(this.productVContainer);
        this.productView = new ReadOnlyView("productView", new PropertyModel<>(this, "productValue"));
        this.productVContainer.add(this.productView);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        overdraftAllowedFieldUpdate(null);
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
            Integer value = (Integer) model.get(column);
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

    protected boolean overdraftAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.overdraftAllowedValue == null ? false : this.overdraftAllowedValue;
        this.maximumOverdraftAmountLimitIContainer.setVisible(visible);
        this.nominalAnnualInterestForOverdraftIContainer.setVisible(visible);
        this.minOverdraftRequiredForInterestCalculationIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.maximumOverdraftAmountLimitBlock);
            target.add(this.nominalAnnualInterestForOverdraftBlock);
            target.add(this.minOverdraftRequiredForInterestCalculationBlock);
        }
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
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        this.submittedOnValue = DateTime.now().toDate();
        this.externalIdValue = StringUtils.upperCase(UUID.randomUUID().toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.savingId = getPageParameters().get("savingId").toString();

        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.officeId = String.valueOf(clientObject.get("office_id"));
        Map<String, Object> savingObject = jdbcTemplate.queryForMap("select * from m_savings_product where id = ?", this.savingId);
        this.productValue = (String) savingObject.get("name");
        this.currencyValue = (String) savingObject.get("currency_code");
        this.decimalPlacesValue = savingObject.get("currency_digits") == null ? null : ((Long) savingObject.get("currency_digits")).intValue();

        this.currencyInMultiplesOfValue = savingObject.get("currency_multiplesof") == null ? null : ((Long) savingObject.get("currency_multiplesof")).intValue();

        this.nominalAnnualInterestValue = (Double) savingObject.get("nominal_annual_interest_rate");

        this.nominalAnnualInterestForOverdraftValue = (Double) savingObject.get("nominal_annual_interest_rate_overdraft");

        this.interestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(savingObject.get("interest_compounding_period_enum")));
        this.interestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(savingObject.get("interest_posting_period_enum")));
        this.interestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(savingObject.get("interest_calculation_type_enum")));
        this.dayInYearValue = DayInYear.optionLiteral(String.valueOf(savingObject.get("interest_calculation_days_in_year_type_enum")));
        this.lockInPeriodValue = savingObject.get("lockin_period_frequency") == null ? null : ((Double) savingObject.get("lockin_period_frequency")).intValue();
        this.lockInTypeValue = LockInType.optionLiteral(String.valueOf(savingObject.get("lockin_period_frequency_enum")));

        Long applyWithdrawalFeeForTransferValue = (Long) savingObject.get("withdrawal_fee_for_transfer");
        this.applyWithdrawalFeeForTransferValue = applyWithdrawalFeeForTransferValue == null ? false : applyWithdrawalFeeForTransferValue == 1;

        this.minimumOpeningBalanceValue = (Double) savingObject.get("min_required_opening_balance");

        this.overdraftAllowedValue = (Boolean) savingObject.get("allow_overdraft");

        this.maximumOverdraftAmountLimitValue = (Double) savingObject.get("overdraft_limit");

        this.minOverdraftRequiredForInterestCalculationValue = (Double) savingObject.get("min_overdraft_for_interest_calculation");
        this.enforceMinimumBalanceValue = (Boolean) savingObject.get("enforce_min_required_balance");

        this.minimumBalanceValue = (Double) savingObject.get("min_required_balance");

        this.balanceRequiredForInterestCalculationValue = (Double) savingObject.get("min_balance_for_interest_calculation");

    }

    protected void saveButtonSubmit(Button button) {
        AccountBuilder builder = new AccountBuilder();

        builder.withProductId(this.savingId);
        builder.withNominalAnnualInterestRate(this.nominalAnnualInterestValue);

        builder.withMinRequiredOpeningBalance(this.minimumOpeningBalanceValue);

        builder.withWithdrawalFeeForTransfers(this.applyWithdrawalFeeForTransferValue == null ? false : this.applyWithdrawalFeeForTransferValue);

        boolean allowOverdraft = this.overdraftAllowedValue == null ? false : this.overdraftAllowedValue;
        builder.withAllowOverdraft(allowOverdraft);

        if (allowOverdraft) {
            builder.withOverdraftLimit(this.maximumOverdraftAmountLimitValue);
            builder.withNominalAnnualInterestRateOverdraft(this.nominalAnnualInterestForOverdraftValue);
            builder.withMinOverdraftForInterestCalculation(this.minOverdraftRequiredForInterestCalculationValue);
        }

        builder.withEnforceMinRequiredBalance(this.enforceMinimumBalanceValue == null ? false : this.enforceMinimumBalanceValue);
        builder.withMinRequiredBalance(this.minimumBalanceValue);
        builder.withHoldTax(false);
        if (this.interestCompoundingPeriodValue != null) {
            builder.withInterestCompoundingPeriodType(InterestCompoundingPeriod.valueOf(this.interestCompoundingPeriodValue.getId()));
        }
        if (this.interestPostingPeriodValue != null) {
            builder.withInterestPostingPeriodType(InterestPostingPeriod.valueOf(this.interestPostingPeriodValue.getId()));
        }
        if (this.interestCalculatedUsingValue != null) {
            builder.withInterestCalculationType(InterestCalculatedUsing.valueOf(this.interestCalculatedUsingValue.getId()));
        }
        if (this.dayInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.dayInYearValue.getId()));
        }
        builder.withLockinPeriodFrequency(this.lockInPeriodValue);
        if (this.lockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.lockInTypeValue.getId()));
        }
        if (this.officerValue != null) {
            builder.withFieldOfficerId(this.officerValue.getId());
        }
        builder.withExternalId(this.externalIdValue);
        builder.withSubmittedOnDate(this.submittedOnValue);

        for (Map<String, Object> charge : this.chargeValue) {
            builder.withCharge((String) charge.get("chargeId"), (Double) charge.get("amount"), (Date) charge.get("date"), (Integer) charge.get("repaymentEvery"));
        }

        builder.withClientId(this.clientId);

        JsonNode node = null;
        JsonNode request = builder.build();
        try {
            node = ClientHelper.createSavingAccount((Session) getSession(), request);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);

    }
}