package com.angkorteam.fintech.pages.client.common;

import java.text.ParseException;
import java.util.Date;
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
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MSavingsProductCharge;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.SavingAccountBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
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
public class SavingAccountCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String savingId;
    protected String officeId;

    protected String clientDisplayName;
    protected String centerDisplayName;
    protected String groupDisplayName;

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
    protected Long decimalPlacesValue;
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
    protected Long currencyInMultiplesOfValue;
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
    protected Long lockInPeriodValue;
    protected TextField<Long> lockInPeriodField;
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

    protected WebMarkupBlock withHoldBlock;
    protected WebMarkupContainer withHoldIContainer;
    protected Boolean withHoldValue;
    protected CheckBox withHoldField;
    protected TextFeedbackPanel withHoldFeedback;

    protected WebMarkupBlock taxGroupBlock;
    protected WebMarkupContainer taxGroupVContainer;
    protected String taxGroupValue;
    protected ReadOnlyView taxGroupView;

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;

    protected Map<String, Object> popupModel;

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
            breadcrumb.setLabel("Saving Selection");
            breadcrumb.setPage(SavingAccountSelectionPage.class);
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
    protected void initComponent() {
        this.popupModel = Maps.newHashMap();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
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

        initWithHoldBlock();

        initTaxGroupBlock();

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

    protected void initWithHoldBlock() {
        this.withHoldBlock = new WebMarkupBlock("withHoldBlock", Size.Six_6);
        this.form.add(this.withHoldBlock);
        this.withHoldIContainer = new WebMarkupContainer("withHoldIContainer");
        this.withHoldBlock.add(this.withHoldIContainer);
        this.withHoldField = new CheckBox("withHoldField", new PropertyModel<>(this, "withHoldValue"));
        this.withHoldField.add(new OnChangeAjaxBehavior(this::withHoldFieldUpdate));
        this.withHoldIContainer.add(this.withHoldField);
        this.withHoldFeedback = new TextFeedbackPanel("withHoldFeedback", this.withHoldField);
        this.withHoldIContainer.add(this.withHoldFeedback);
    }

    protected void initTaxGroupBlock() {
        this.taxGroupBlock = new WebMarkupBlock("taxGroupBlock", Size.Six_6);
        this.form.add(this.taxGroupBlock);
        this.taxGroupVContainer = new WebMarkupContainer("taxGroupVContainer");
        this.taxGroupBlock.add(this.taxGroupVContainer);
        this.taxGroupView = new ReadOnlyView("taxGroupView", new PropertyModel<>(this, "taxGroupValue"));
        this.taxGroupVContainer.add(this.taxGroupView);
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
        this.officerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
        this.officerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
        this.officerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
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
    protected void configureMetaData() {
        overdraftAllowedFieldUpdate(null);
        this.withHoldIContainer.setVisible(this.withHoldValue != null && this.withHoldValue);
        this.taxGroupVContainer.setVisible(this.withHoldValue != null && this.withHoldValue);
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
            this.chargePopup.setContent(new AccountChargePopup("charge", this.popupModel, ProductPopup.Saving, this.currencyValue));
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
        this.chargePopup.setContent(new AccountChargePopup("charge", this.popupModel, ProductPopup.Saving, this.currencyValue));
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

    protected boolean withHoldFieldUpdate(AjaxRequestTarget target) {
        this.taxGroupVContainer.setVisible(this.withHoldValue != null && this.withHoldValue);
        if (target != null) {
            target.add(this.taxGroupBlock);
        }
        return false;
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.savingId = getPageParameters().get("savingId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        // TODO @review

        this.submittedOnValue = DateTime.now().toDate();
        this.externalIdValue = StringUtils.upperCase(UUID.randomUUID().toString());

        SelectQuery selectQuery = null;

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.officeId = String.valueOf(clientObject.get("office_id"));
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.groupId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.officeId = String.valueOf(groupObject.get("office_id"));
            this.groupDisplayName = (String) groupObject.get("display_name");
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.centerId);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.officeId = String.valueOf(centerObject.get("office_id"));
            this.centerDisplayName = (String) centerObject.get("display_name");
        }

        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        selectQuery.addWhere(MSavingsProduct.Field.ID + " = :" + MSavingsProduct.Field.ID, this.savingId);
        selectQuery.addField(MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_CODE);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE);
        selectQuery.addField(MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        selectQuery.addField(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM);
        selectQuery.addField(MSavingsProduct.Field.WITHDRAWAL_FEE_FOR_TRANSFER);
        selectQuery.addField(MSavingsProduct.Field.MIN_REQUIRED_OPENING_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.ALLOW_OVERDRAFT);
        selectQuery.addField(MSavingsProduct.Field.OVERDRAFT_LIMIT);
        selectQuery.addField(MSavingsProduct.Field.MIN_OVERDRAFT_FOR_INTEREST_CALCULATION);
        selectQuery.addField(MSavingsProduct.Field.ENFORCE_MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);
        selectQuery.addField(MSavingsProduct.Field.WITHHOLD_TAX);
        selectQuery.addField(MSavingsProduct.Field.TAX_GROUP_ID);
        Map<String, Object> savingProductObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.productValue = (String) savingProductObject.get("name");
        this.currencyValue = (String) savingProductObject.get("currency_code");
        this.decimalPlacesValue = (Long) savingProductObject.get("currency_digits");

        this.currencyInMultiplesOfValue = (Long) savingProductObject.get("currency_multiplesof");

        this.nominalAnnualInterestValue = (Double) savingProductObject.get("nominal_annual_interest_rate");

        this.nominalAnnualInterestForOverdraftValue = (Double) savingProductObject.get("nominal_annual_interest_rate_overdraft");

        this.interestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(savingProductObject.get("interest_compounding_period_enum")));
        this.interestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(savingProductObject.get("interest_posting_period_enum")));
        this.interestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(savingProductObject.get("interest_calculation_type_enum")));
        this.dayInYearValue = DayInYear.optionLiteral(String.valueOf(savingProductObject.get("interest_calculation_days_in_year_type_enum")));
        Double lockin_period_frequency = (Double) savingProductObject.get("lockin_period_frequency");
        this.lockInPeriodValue = lockin_period_frequency != null ? lockin_period_frequency.longValue() : null;
        this.lockInTypeValue = LockInType.optionLiteral(String.valueOf(savingProductObject.get("lockin_period_frequency_enum")));

        Long applyWithdrawalFeeForTransferValue = (Long) savingProductObject.get("withdrawal_fee_for_transfer");
        this.applyWithdrawalFeeForTransferValue = applyWithdrawalFeeForTransferValue == null ? false : applyWithdrawalFeeForTransferValue == 1;

        this.minimumOpeningBalanceValue = (Double) savingProductObject.get("min_required_opening_balance");

        this.overdraftAllowedValue = (Boolean) savingProductObject.get("allow_overdraft");

        this.maximumOverdraftAmountLimitValue = (Double) savingProductObject.get("overdraft_limit");

        this.minOverdraftRequiredForInterestCalculationValue = (Double) savingProductObject.get("min_overdraft_for_interest_calculation");
        this.enforceMinimumBalanceValue = (Boolean) savingProductObject.get("enforce_min_required_balance");

        this.minimumBalanceValue = (Double) savingProductObject.get("min_required_balance");

        this.balanceRequiredForInterestCalculationValue = (Double) savingProductObject.get("min_balance_for_interest_calculation");

        this.withHoldValue = savingProductObject.get("withhold_tax") == null ? false : ((Long) savingProductObject.get("withhold_tax")) == 1;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, savingProductObject.get("tax_group_id"));
        selectQuery.addField(MTaxGroup.Field.NAME);
        this.taxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        selectQuery = new SelectQuery(MSavingsProductCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addWhere(MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID + " = :" + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID, this.savingId);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
        List<Map<String, Object>> charges = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        if (charges != null && !charges.isEmpty()) {
            for (Map<String, Object> chargeObject : charges) {
                Map<String, Object> item = Maps.newHashMap();
                item.put("uuid", UUID.randomUUID().toString());
                Option charge = new Option(String.valueOf(chargeObject.get("id")), (String) chargeObject.get("name"));
                item.put("charge", charge);
                item.put("name", charge);
                item.put("chargeTime", chargeObject.get("charge_time_enum"));
                item.put("amount", chargeObject.get("amount"));

                item.put("collectedOn", ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum"))));

                item.put("type", ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum"))));

                Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
                item.put("repaymentEvery", repaymentEveryValue == null ? null : repaymentEveryValue.intValue());

                Long month = (Long) chargeObject.get("fee_on_month");
                Long day = (Long) chargeObject.get("fee_on_day");
                if (day != null && month != null) {
                    try {
                        item.put("dayMonth", DateUtils.parseDate(day + "/" + month, "d/M"));
                    } catch (ParseException e) {
                    }
                }
                this.chargeValue.add(item);
            }
        }

    }

    protected void saveButtonSubmit(Button button) {
        SavingAccountBuilder builder = new SavingAccountBuilder();

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
            String chargeId = ((Option) charge.get("charge")).getId();
            Double amount = (Double) charge.get("amount");
            Date feeOnMonthDay = (Date) charge.get("dayMonth");
            Date dueDate = (Date) charge.get("date");
            Long feeInterval = (Long) charge.get("repaymentEvery");
            builder.withCharge(chargeId, amount, feeOnMonthDay, dueDate, feeInterval);
        }

        if (this.client == ClientEnum.Client) {
            builder.withClientId(this.clientId);
        } else if (this.client == ClientEnum.Group) {
            builder.withGroupId(this.groupId);
        } else if (this.client == ClientEnum.Center) {
            builder.withGroupId(this.centerId);
        }

        JsonNode request = builder.build();
        JsonNode node = ClientHelper.createSavingAccount((Session) getSession(), request);

        if (reportError(node)) {
            return;
        }
        if (this.client == ClientEnum.Client) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            setResponsePage(GroupPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

    }
}