package com.angkorteam.fintech.pages.product.share;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ShareBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.MinimumActivePeriod;
import com.angkorteam.fintech.helper.ShareHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.share.ChargePopup;
import com.angkorteam.fintech.popup.share.MarketPricePopup;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.MinimumActivePeriodProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
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
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameIContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameIContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionIContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeIContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceIContainer;
    protected Integer currencyDecimalPlaceValue;
    protected TextField<Integer> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfIContainer;
    protected Integer currencyMultipleOfValue;
    protected TextField<Integer> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Term

    protected WebMarkupBlock termTotalNumberOfShareBlock;
    protected WebMarkupContainer termTotalNumberOfShareIContainer;
    protected Integer termTotalNumberOfShareValue;
    protected TextField<Integer> termTotalNumberOfShareField;
    protected TextFeedbackPanel termTotalNumberOfShareFeedback;

    protected WebMarkupBlock termShareToBeIssuedBlock;
    protected WebMarkupContainer termShareToBeIssuedIContainer;
    protected Integer termShareToBeIssuedValue;
    protected TextField<Integer> termShareToBeIssuedField;
    protected TextFeedbackPanel termShareToBeIssuedFeedback;

    protected WebMarkupBlock termNominalPriceBlock;
    protected WebMarkupContainer termNominalPriceIContainer;
    protected Double termNominalPriceValue;
    protected TextField<Double> termNominalPriceField;
    protected TextFeedbackPanel termNominalPriceFeedback;

    protected WebMarkupBlock termCapitalBlock;
    protected WebMarkupContainer termCapitalIContainer;
    protected Double termCapitalValue;
    protected TextField<Double> termCapitalField;
    protected TextFeedbackPanel termCapitalFeedback;

    // Setting

    protected WebMarkupBlock settingSharePerClientMinimumBlock;
    protected WebMarkupContainer settingSharePerClientMinimumIContainer;
    protected Integer settingSharePerClientMinimumValue;
    protected TextField<Integer> settingSharePerClientMinimumField;
    protected TextFeedbackPanel settingSharePerClientMinimumFeedback;

    protected WebMarkupBlock settingSharePerClientDefaultBlock;
    protected WebMarkupContainer settingSharePerClientDefaultIContainer;
    protected Integer settingSharePerClientDefaultValue;
    protected TextField<Integer> settingSharePerClientDefaultField;
    protected TextFeedbackPanel settingSharePerClientDefaultFeedback;

    protected WebMarkupBlock settingSharePerClientMaximumBlock;
    protected WebMarkupContainer settingSharePerClientMaximumIContainer;
    protected Integer settingSharePerClientMaximumValue;
    protected TextField<Integer> settingSharePerClientMaximumField;
    protected TextFeedbackPanel settingSharePerClientMaximumFeedback;

    protected WebMarkupBlock settingMinimumActivePeriodBlock;
    protected WebMarkupContainer settingMinimumActivePeriodIContainer;
    protected Integer settingMinimumActivePeriodValue;
    protected TextField<Integer> settingMinimumActivePeriodField;
    protected TextFeedbackPanel settingMinimumActivePeriodFeedback;

    protected WebMarkupBlock settingMinimumActiveTypeBlock;
    protected WebMarkupContainer settingMinimumActiveTypeIContainer;
    protected MinimumActivePeriodProvider settingMinimumActiveTypeProvider;
    protected Option settingMinimumActiveTypeValue;
    protected Select2SingleChoice<Option> settingMinimumActiveTypeField;
    protected TextFeedbackPanel settingMinimumActiveTypeFeedback;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected Integer settingLockInPeriodValue;
    protected TextField<Integer> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingAllowDividendForInactiveClientBlock;
    protected WebMarkupContainer settingAllowDividendForInactiveClientIContainer;
    protected Boolean settingAllowDividendForInactiveClientValue;
    protected CheckBox settingAllowDividendForInactiveClientField;
    protected TextFeedbackPanel settingAllowDividendForInactiveClientFeedback;

    // Market Price

    protected ModalWindow marketPricePopup;

    protected WebMarkupBlock marketPriceBlock;
    protected WebMarkupContainer marketPriceIContainer;
    protected List<Map<String, Object>> marketPriceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> marketPriceTable;
    protected ListDataProvider marketPriceProvider;
    protected List<IColumn<Map<String, Object>, String>> marketPriceColumn;
    protected AjaxLink<Void> marketPriceAddLink;

    // Charges

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

    // Accounting

    protected String accountingValue = ACC_NONE;
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashShareReferenceBlock;
    protected WebMarkupContainer cashShareReferenceIContainer;
    protected SingleChoiceProvider cashShareReferenceProvider;
    protected Option cashShareReferenceValue;
    protected Select2SingleChoice<Option> cashShareReferenceField;
    protected TextFeedbackPanel cashShareReferenceFeedback;

    protected WebMarkupBlock cashShareSuspenseControlBlock;
    protected WebMarkupContainer cashShareSuspenseControlIContainer;
    protected SingleChoiceProvider cashShareSuspenseControlProvider;
    protected Option cashShareSuspenseControlValue;
    protected Select2SingleChoice<Option> cashShareSuspenseControlField;
    protected TextFeedbackPanel cashShareSuspenseControlFeedback;

    protected WebMarkupBlock cashEquityBlock;
    protected WebMarkupContainer cashEquityIContainer;
    protected SingleChoiceProvider cashEquityProvider;
    protected Option cashEquityValue;
    protected Select2SingleChoice<Option> cashEquityField;
    protected TextFeedbackPanel cashEquityFeedback;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Option cashIncomeFromFeeValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    protected Map<String, Object> popupModel;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Share Loan Product");
            breadcrumb.setPage(ShareBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Share Loan Product Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initComponent() {

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.form.add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerm();

        initSectionSetting();

        initSectionMarketPrice();

        initSectionCharge();

        initSectionAccounting();
    }

    @Override
    protected void configureRequiredValidation() {
        this.detailProductNameField.setRequired(true);
        this.detailShortNameField.setRequired(true);
        this.detailDescriptionField.setRequired(true);
        this.currencyCodeField.setRequired(true);
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyMultipleOfField.setRequired(true);
        this.termTotalNumberOfShareField.setRequired(true);
        this.termNominalPriceField.setRequired(true);
        this.settingSharePerClientDefaultField.setRequired(true);
        this.termShareToBeIssuedField.setRequired(true);
        this.accountingField.setRequired(true);
    }

    @Override
    protected void configureMetaData() {
        accountingFieldUpdate(null);
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2;
        this.currencyMultipleOfValue = 1;
        this.termTotalNumberOfShareValue = 10;
        this.termNominalPriceValue = 10d;
        this.settingSharePerClientDefaultValue = 10;
        this.settingAllowDividendForInactiveClientValue = true;
        this.termShareToBeIssuedValue = 10;
        this.accountingValue = ACC_NONE;
    }

    protected void initSectionAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(ACC_NONE)));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(ACC_CASH)));
        this.form.add(this.accountingField);

        initSectionAccountingCash();

    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);

        if ("Cash".equals(this.accountingValue)) {
            this.cashIContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
        }
        return false;
    }

    protected void initSectionAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        this.cashShareReferenceBlock = new WebMarkupBlock("cashShareReferenceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashShareReferenceBlock);
        this.cashShareReferenceIContainer = new WebMarkupContainer("cashShareReferenceIContainer");
        this.cashShareReferenceBlock.add(this.cashShareReferenceIContainer);
        this.cashShareReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashShareReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashShareReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashShareReferenceField = new Select2SingleChoice<>("cashShareReferenceField", new PropertyModel<>(this, "cashShareReferenceValue"), this.cashShareReferenceProvider);
        this.cashShareReferenceField.setLabel(Model.of("Share reference"));
        this.cashShareReferenceField.add(new OnChangeAjaxBehavior());
        this.cashShareReferenceIContainer.add(this.cashShareReferenceField);
        this.cashShareReferenceFeedback = new TextFeedbackPanel("cashShareReferenceFeedback", this.cashShareReferenceField);
        this.cashShareReferenceIContainer.add(this.cashShareReferenceFeedback);

        this.cashShareSuspenseControlBlock = new WebMarkupBlock("cashShareSuspenseControlBlock", Size.Six_6);
        this.cashIContainer.add(this.cashShareSuspenseControlBlock);
        this.cashShareSuspenseControlIContainer = new WebMarkupContainer("cashShareSuspenseControlIContainer");
        this.cashShareSuspenseControlBlock.add(this.cashShareSuspenseControlIContainer);
        this.cashShareSuspenseControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashShareSuspenseControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashShareSuspenseControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashShareSuspenseControlField = new Select2SingleChoice<>("cashShareSuspenseControlField", new PropertyModel<>(this, "cashShareSuspenseControlValue"), this.cashShareSuspenseControlProvider);
        this.cashShareSuspenseControlField.setLabel(Model.of("Share Suspense control"));
        this.cashShareSuspenseControlField.add(new OnChangeAjaxBehavior());
        this.cashShareSuspenseControlIContainer.add(this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlFeedback = new TextFeedbackPanel("cashShareSuspenseControlFeedback", this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlIContainer.add(this.cashShareSuspenseControlFeedback);

        this.cashEquityBlock = new WebMarkupBlock("cashEquityBlock", Size.Six_6);
        this.cashIContainer.add(this.cashEquityBlock);
        this.cashEquityIContainer = new WebMarkupContainer("cashEquityIContainer");
        this.cashEquityBlock.add(this.cashEquityIContainer);
        this.cashEquityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashEquityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashEquityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Equity.getLiteral());
        this.cashEquityField = new Select2SingleChoice<>("cashEquityField", new PropertyModel<>(this, "cashEquityValue"), this.cashEquityProvider);
        this.cashEquityField.setLabel(Model.of("Equity"));
        this.cashEquityField.add(new OnChangeAjaxBehavior());
        this.cashEquityIContainer.add(this.cashEquityField);
        this.cashEquityFeedback = new TextFeedbackPanel("cashEquityFeedback", this.cashEquityField);
        this.cashEquityIContainer.add(this.cashEquityFeedback);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeFeedback);
    }

    protected void initSectionMarketPrice() {

        this.marketPricePopup = new ModalWindow("marketPricePopup");
        add(this.marketPricePopup);
        this.marketPricePopup.setOnClose(this::marketPricePopupClose);

        initMarketPriceBlock();

    }

    protected void initMarketPriceBlock() {
        this.marketPriceBlock = new WebMarkupBlock("marketPriceBlock", Size.Twelve_12);
        this.form.add(this.marketPriceBlock);
        this.marketPriceIContainer = new WebMarkupContainer("marketPriceIContainer");
        this.marketPriceBlock.add(this.marketPriceIContainer);

        this.marketPriceColumn = Lists.newArrayList();
        this.marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketPriceColumn));
        this.marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketPriceColumn));
        this.marketPriceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::marketPriceAction, this::marketPriceClick));
        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue);
        this.marketPriceTable = new DataTable<>("marketPriceTable", this.marketPriceColumn, this.marketPriceProvider, 20);
        this.marketPriceIContainer.add(this.marketPriceTable);
        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));

        this.marketPriceAddLink = new AjaxLink<>("marketPriceAddLink");
        this.marketPriceAddLink.setOnClick(this::marketPriceAddLinkClick);
        this.marketPriceIContainer.add(this.marketPriceAddLink);
    }

    protected void marketPricePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("unitPrice", this.popupModel.get("unitPriceValue"));
        item.put("fromDate", this.popupModel.get("fromDateValue"));
        this.marketPriceValue.add(item);
        target.add(this.form);
    }

    protected boolean marketPriceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.marketPricePopup.setContent(new MarketPricePopup("marketPrice", this.marketPricePopup, this.popupModel));
        this.marketPricePopup.show(target);
        return false;
    }

    protected ItemPanel marketPriceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("fromDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        } else if ("unitPrice".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void marketPriceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.marketPriceValue.size(); i++) {
            Map<String, Object> column = this.marketPriceValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.marketPriceValue.remove(index);
        }
        target.add(this.marketPriceTable);
    }

    protected List<ActionItem> marketPriceAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initSectionCharge() {
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupClose);

        initChargeBlock();
    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeIContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.chargeIContainer.add(this.chargeAddLink);
    }

    protected void chargePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        Option charge = (Option) this.popupModel.get("chargeValue");
        for (Map<String, Object> temp : this.chargeValue) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?", charge.getId());
        String type = (String) chargeObject.get("type");
        for (ChargeCalculation calculation : ChargeCalculation.values()) {
            if (type.equals(calculation.getLiteral())) {
                type = calculation.getDescription();
                break;
            }
        }
        String collect = (String) chargeObject.get("collect");
        for (ChargeTime time : ChargeTime.values()) {
            if (collect.equals(time.getLiteral())) {
                collect = time.getDescription();
                break;
            }
        }
        item.put("uuid", charge.getId());
        item.put("charge", charge);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.form);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup("charge", this.chargePopup, this.popupModel, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.chargePopup.setContent(new CurrencyPopup("currency", this.chargePopup));
            this.chargePopup.show(target);
        }
        return false;
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "collect".equals(column) || "type".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        } else if ("date".equals(column)) {
            String value = (String) model.get(column);
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

    protected void initSectionSetting() {

        initSettingSharePerClientMinimumBlock();

        initSettingSharePerClientDefaultBlock();

        initSettingSharePerClientMaximumBlock();

        initSettingMinimumActivePeriodBlock();

        initSettingMinimumActiveTypeBlock();

        initSettingLockInPeriodBlock();

        initSettingLockInTypeBlock();

        initSettingAllowDividendForInactiveClientBlock();

    }

    protected void initSettingAllowDividendForInactiveClientBlock() {
        this.settingAllowDividendForInactiveClientBlock = new WebMarkupBlock("settingAllowDividendForInactiveClientBlock", Size.Four_4);
        this.form.add(this.settingAllowDividendForInactiveClientBlock);
        this.settingAllowDividendForInactiveClientIContainer = new WebMarkupContainer("settingAllowDividendForInactiveClientIContainer");
        this.settingAllowDividendForInactiveClientBlock.add(this.settingAllowDividendForInactiveClientIContainer);
        this.settingAllowDividendForInactiveClientField = new CheckBox("settingAllowDividendForInactiveClientField", new PropertyModel<>(this, "settingAllowDividendForInactiveClientValue"));
        this.settingAllowDividendForInactiveClientField.add(new OnChangeAjaxBehavior());
        this.settingAllowDividendForInactiveClientIContainer.add(this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientFeedback = new TextFeedbackPanel("settingAllowDividendForInactiveClientFeedback", this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientIContainer.add(this.settingAllowDividendForInactiveClientFeedback);
    }

    protected void initSettingLockInTypeBlock() {
        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);
    }

    protected void initSettingLockInPeriodBlock() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);
    }

    protected void initSettingMinimumActiveTypeBlock() {
        this.settingMinimumActiveTypeBlock = new WebMarkupBlock("settingMinimumActiveTypeBlock", Size.Four_4);
        this.form.add(this.settingMinimumActiveTypeBlock);
        this.settingMinimumActiveTypeIContainer = new WebMarkupContainer("settingMinimumActiveTypeIContainer");
        this.settingMinimumActiveTypeBlock.add(this.settingMinimumActiveTypeIContainer);
        this.settingMinimumActiveTypeProvider = new MinimumActivePeriodProvider();
        this.settingMinimumActiveTypeField = new Select2SingleChoice<>("settingMinimumActiveTypeField", new PropertyModel<>(this, "settingMinimumActiveTypeValue"), this.settingMinimumActiveTypeProvider);
        this.settingMinimumActiveTypeField.setLabel(Model.of("Type"));
        this.settingMinimumActiveTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActiveTypeIContainer.add(this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeFeedback = new TextFeedbackPanel("settingMinimumActiveTypeFeedback", this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeIContainer.add(this.settingMinimumActiveTypeFeedback);
    }

    protected void initSettingMinimumActivePeriodBlock() {
        this.settingMinimumActivePeriodBlock = new WebMarkupBlock("settingMinimumActivePeriodBlock", Size.Four_4);
        this.form.add(this.settingMinimumActivePeriodBlock);
        this.settingMinimumActivePeriodIContainer = new WebMarkupContainer("settingMinimumActivePeriodIContainer");
        this.settingMinimumActivePeriodBlock.add(this.settingMinimumActivePeriodIContainer);
        this.settingMinimumActivePeriodField = new TextField<>("settingMinimumActivePeriodField", new PropertyModel<>(this, "settingMinimumActivePeriodValue"));
        this.settingMinimumActivePeriodField.setLabel(Model.of("Minimum Active Period"));
        this.settingMinimumActivePeriodField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActivePeriodIContainer.add(this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodFeedback = new TextFeedbackPanel("settingMinimumActivePeriodFeedback", this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodIContainer.add(this.settingMinimumActivePeriodFeedback);
    }

    protected void initSettingSharePerClientMaximumBlock() {
        this.settingSharePerClientMaximumBlock = new WebMarkupBlock("settingSharePerClientMaximumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMaximumBlock);
        this.settingSharePerClientMaximumIContainer = new WebMarkupContainer("settingSharePerClientMaximumIContainer");
        this.settingSharePerClientMaximumBlock.add(this.settingSharePerClientMaximumIContainer);
        this.settingSharePerClientMaximumField = new TextField<>("settingSharePerClientMaximumField", new PropertyModel<>(this, "settingSharePerClientMaximumValue"));
        this.settingSharePerClientMaximumField.setLabel(Model.of("Shares per Client Maximum"));
        this.settingSharePerClientMaximumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMaximumIContainer.add(this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumFeedback = new TextFeedbackPanel("settingSharePerClientMaximumFeedback", this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumIContainer.add(this.settingSharePerClientMaximumFeedback);
    }

    protected void initSettingSharePerClientDefaultBlock() {
        this.settingSharePerClientDefaultBlock = new WebMarkupBlock("settingSharePerClientDefaultBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientDefaultBlock);
        this.settingSharePerClientDefaultIContainer = new WebMarkupContainer("settingSharePerClientDefaultIContainer");
        this.settingSharePerClientDefaultBlock.add(this.settingSharePerClientDefaultIContainer);
        this.settingSharePerClientDefaultField = new TextField<>("settingSharePerClientDefaultField", new PropertyModel<>(this, "settingSharePerClientDefaultValue"));
        this.settingSharePerClientDefaultField.setLabel(Model.of("Shares per Client Default"));
        this.settingSharePerClientDefaultField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientDefaultIContainer.add(this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultFeedback = new TextFeedbackPanel("settingSharePerClientDefaultFeedback", this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultIContainer.add(this.settingSharePerClientDefaultFeedback);
    }

    protected void initSettingSharePerClientMinimumBlock() {
        this.settingSharePerClientMinimumBlock = new WebMarkupBlock("settingSharePerClientMinimumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumIContainer = new WebMarkupContainer("settingSharePerClientMinimumIContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumIContainer);
        this.settingSharePerClientMinimumField = new TextField<>("settingSharePerClientMinimumField", new PropertyModel<>(this, "settingSharePerClientMinimumValue"));
        this.settingSharePerClientMinimumField.setLabel(Model.of("Shares per Client Minimum"));
        this.settingSharePerClientMinimumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMinimumIContainer.add(this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumFeedback = new TextFeedbackPanel("settingSharePerClientMinimumFeedback", this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumIContainer.add(this.settingSharePerClientMinimumFeedback);
    }

    protected void initSectionTerm() {

        initTermTotalNumberOfShareBlock();

        initTermShareToBeIssuedBlock();

        initTermNominalPriceBlock();

        initTermCapitalBlock();

    }

    protected void initTermCapitalBlock() {
        this.termCapitalBlock = new WebMarkupBlock("termCapitalBlock", Size.Six_6);
        this.form.add(this.termCapitalBlock);
        this.termCapitalIContainer = new WebMarkupContainer("termCapitalIContainer");
        this.termCapitalBlock.add(this.termCapitalIContainer);
        this.termCapitalField = new TextField<>("termCapitalField", new PropertyModel<>(this, "termCapitalValue"));
        this.termCapitalField.setLabel(Model.of("Capital Value"));
        this.termCapitalField.add(new OnChangeAjaxBehavior());
        this.termCapitalIContainer.add(this.termCapitalField);
        this.termCapitalFeedback = new TextFeedbackPanel("termCapitalFeedback", this.termCapitalField);
        this.termCapitalIContainer.add(this.termCapitalFeedback);
    }

    protected void initTermNominalPriceBlock() {
        this.termNominalPriceBlock = new WebMarkupBlock("termNominalPriceBlock", Size.Six_6);
        this.form.add(this.termNominalPriceBlock);
        this.termNominalPriceIContainer = new WebMarkupContainer("termNominalPriceIContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceIContainer);
        this.termNominalPriceField = new TextField<>("termNominalPriceField", new PropertyModel<>(this, "termNominalPriceValue"));
        this.termNominalPriceField.setLabel(Model.of("Nominal Price"));
        this.termNominalPriceField.add(new OnChangeAjaxBehavior());
        this.termNominalPriceIContainer.add(this.termNominalPriceField);
        this.termNominalPriceFeedback = new TextFeedbackPanel("termNominalPriceFeedback", this.termNominalPriceField);
        this.termNominalPriceIContainer.add(this.termNominalPriceFeedback);
    }

    protected void initTermShareToBeIssuedBlock() {
        this.termShareToBeIssuedBlock = new WebMarkupBlock("termShareToBeIssuedBlock", Size.Six_6);
        this.form.add(this.termShareToBeIssuedBlock);
        this.termShareToBeIssuedIContainer = new WebMarkupContainer("termShareToBeIssuedIContainer");
        this.termShareToBeIssuedBlock.add(this.termShareToBeIssuedIContainer);
        this.termShareToBeIssuedField = new TextField<>("termShareToBeIssuedField", new PropertyModel<>(this, "termShareToBeIssuedValue"));
        this.termShareToBeIssuedField.setLabel(Model.of("Shares to be issued"));
        this.termShareToBeIssuedField.add(new OnChangeAjaxBehavior());
        this.termShareToBeIssuedIContainer.add(this.termShareToBeIssuedField);
        this.termShareToBeIssuedFeedback = new TextFeedbackPanel("termShareToBeIssuedFeedback", this.termShareToBeIssuedField);
        this.termShareToBeIssuedIContainer.add(this.termShareToBeIssuedFeedback);
    }

    protected void initTermTotalNumberOfShareBlock() {
        this.termTotalNumberOfShareBlock = new WebMarkupBlock("termTotalNumberOfShareBlock", Size.Six_6);
        this.form.add(this.termTotalNumberOfShareBlock);
        this.termTotalNumberOfShareIContainer = new WebMarkupContainer("termTotalNumberOfShareIContainer");
        this.termTotalNumberOfShareBlock.add(this.termTotalNumberOfShareIContainer);
        this.termTotalNumberOfShareField = new TextField<>("termTotalNumberOfShareField", new PropertyModel<>(this, "termTotalNumberOfShareValue"));
        this.termTotalNumberOfShareField.setLabel(Model.of("Total Number of Shares"));
        this.termTotalNumberOfShareField.add(new OnChangeAjaxBehavior());
        this.termTotalNumberOfShareIContainer.add(this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareFeedback = new TextFeedbackPanel("termTotalNumberOfShareFeedback", this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareIContainer.add(this.termTotalNumberOfShareFeedback);
    }

    protected void initSectionCurrency() {

        initCurrencyCodeBlock();

        initCurrencyDecimalPlaceBlock();

        initCurrencyMultipleOfBlock();
    }

    protected void initCurrencyMultipleOfBlock() {
        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfIContainer = new WebMarkupContainer("currencyMultipleOfIContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfIContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initCurrencyDecimalPlaceBlock() {
        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceIContainer = new WebMarkupContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceIContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceFeedback);
    }

    protected void initCurrencyCodeBlock() {
        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeIContainer = new WebMarkupContainer("currencyCodeIContainer");
        this.currencyCodeBlock.add(this.currencyCodeIContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeIContainer.add(this.currencyCodeFeedback);
    }

    protected void initSectionDetail() {
        initDetailProductNameBlock();

        initDetailShortNameBlock();

        initDetailDescriptionBlock();
    }

    protected void initDetailDescriptionBlock() {
        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionIContainer = new WebMarkupContainer("detailDescriptionIContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionIContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionIContainer.add(this.detailDescriptionFeedback);
    }

    protected void initDetailShortNameBlock() {
        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameIContainer = new WebMarkupContainer("detailShortNameIContainer");
        this.detailShortNameBlock.add(this.detailShortNameIContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameIContainer.add(this.detailShortNameFeedback);
    }

    protected void initDetailProductNameBlock() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameIContainer = new WebMarkupContainer("detailProductNameIContainer");
        this.detailProductNameBlock.add(this.detailProductNameIContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameIContainer.add(this.detailProductNameFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        ShareBuilder builder = new ShareBuilder();

        // Detail

        builder.withName(this.detailProductNameValue);
        builder.withShortName(this.detailShortNameValue);
        builder.withDescription(this.detailDescriptionValue);

        // Currency

        if (this.currencyCodeValue != null) {
            builder.withCurrencyCode(this.currencyCodeValue.getId());
        }
        builder.withDigitsAfterDecimal(this.currencyDecimalPlaceValue);
        builder.withInMultiplesOf(this.currencyMultipleOfValue);

        // Term

        builder.withTotalShares(this.termTotalNumberOfShareValue);
        builder.withSharesIssued(this.termShareToBeIssuedValue);
        builder.withUnitPrice(this.termNominalPriceValue);
        // builder.withShareCapital(this.termCapitalValue);

        // Setting

        builder.withMinimumShares(this.settingSharePerClientMinimumValue);
        builder.withNominalShares(this.settingSharePerClientDefaultValue);
        builder.withMaximumShares(this.settingSharePerClientMaximumValue);
        builder.withMinimumActivePeriodForDividends(this.settingMinimumActivePeriodValue);
        if (this.settingMinimumActiveTypeValue != null) {
            builder.withMinimumactiveperiodFrequencyType(MinimumActivePeriod.valueOf(this.settingMinimumActiveTypeValue.getId()));
        }
        builder.withLockinPeriodFrequency(this.settingLockInPeriodValue);
        if (this.settingLockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.settingLockInTypeValue.getId()));
        }
        builder.withAllowDividendCalculationForInactiveClients(this.settingAllowDividendForInactiveClientValue == null ? false : this.settingAllowDividendForInactiveClientValue);

        // Market Price

        if (this.marketPriceValue != null && !this.marketPriceValue.isEmpty()) {
            for (Map<String, Object> item : this.marketPriceValue) {
                builder.withMarketPricePeriods((Date) item.get("fromDate"), (Double) item.get("unitPrice"));
            }
        }

        // Charge

        if (this.chargeValue != null && !this.chargeValue.isEmpty()) {
            for (Map<String, Object> item : this.chargeValue) {
                builder.withCharges((String) item.get("chargeId"));
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (ACC_NONE.equals(accounting)) {
            builder.withAccountingRule(1);
        } else if (ACC_CASH.equals(accounting)) {
            builder.withAccountingRule(2);
        }

        if (ACC_CASH.equals(accounting)) {
            if (this.cashShareReferenceValue != null) {
                builder.withShareReferenceId(this.cashShareReferenceValue.getId());
            }
            if (this.cashShareSuspenseControlValue != null) {
                builder.withShareSuspenseId(this.cashShareSuspenseControlValue.getId());
            }
            if (this.cashEquityValue != null) {
                builder.withShareEquityId(this.cashEquityValue.getId());
            }
            if (this.cashIncomeFromFeeValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeeValue.getId());
            }
        }

        JsonNode node = null;
        try {
            node = ShareHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ShareBrowsePage.class);
    }

}
