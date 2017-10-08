package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.apache.wicket.validation.validator.RangeValidator;

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
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.MinimumActivePeriodProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
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

    protected WebMarkupContainer detailProductNameBlock;
    protected WebMarkupContainer detailProductNameContainer;
    protected String detailProductNameValue;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupContainer detailShortNameBlock;
    protected WebMarkupContainer detailShortNameContainer;
    protected String detailShortNameValue;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupContainer detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionContainer;
    protected String detailDescriptionValue;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    protected WebMarkupContainer currencyCodeBlock;
    protected WebMarkupContainer currencyCodeContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Option currencyCodeValue;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupContainer currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceContainer;
    protected Integer currencyDecimalPlaceValue;
    protected TextField<Integer> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupContainer currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfContainer;
    protected Integer currencyMultipleOfValue;
    protected TextField<Integer> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    // Term

    protected WebMarkupContainer termTotalNumberOfShareBlock;
    protected WebMarkupContainer termTotalNumberOfShareContainer;
    protected Integer termTotalNumberOfShareValue;
    protected TextField<Integer> termTotalNumberOfShareField;
    protected TextFeedbackPanel termTotalNumberOfShareFeedback;

    protected WebMarkupContainer termShareToBeIssuedBlock;
    protected WebMarkupContainer termShareToBeIssuedContainer;
    protected Integer termShareToBeIssuedValue;
    protected TextField<Integer> termShareToBeIssuedField;
    protected TextFeedbackPanel termShareToBeIssuedFeedback;

    protected WebMarkupContainer termNominalPriceBlock;
    protected WebMarkupContainer termNominalPriceContainer;
    protected Double termNominalPriceValue;
    protected TextField<Double> termNominalPriceField;
    protected TextFeedbackPanel termNominalPriceFeedback;

    protected WebMarkupContainer termCapitalBlock;
    protected WebMarkupContainer termCapitalContainer;
    protected Double termCapitalValue;
    protected TextField<Double> termCapitalField;
    protected TextFeedbackPanel termCapitalFeedback;

    // Setting

    protected WebMarkupContainer settingSharePerClientMinimumBlock;
    protected WebMarkupContainer settingSharePerClientMinimumContainer;
    protected Integer settingSharePerClientMinimumValue;
    protected TextField<Integer> settingSharePerClientMinimumField;
    protected TextFeedbackPanel settingSharePerClientMinimumFeedback;

    protected WebMarkupContainer settingSharePerClientDefaultBlock;
    protected WebMarkupContainer settingSharePerClientDefaultContainer;
    protected Integer settingSharePerClientDefaultValue;
    protected TextField<Integer> settingSharePerClientDefaultField;
    protected TextFeedbackPanel settingSharePerClientDefaultFeedback;

    protected WebMarkupContainer settingSharePerClientMaximumBlock;
    protected WebMarkupContainer settingSharePerClientMaximumContainer;
    protected Integer settingSharePerClientMaximumValue;
    protected TextField<Integer> settingSharePerClientMaximumField;
    protected TextFeedbackPanel settingSharePerClientMaximumFeedback;

    protected WebMarkupContainer settingMinimumActivePeriodBlock;
    protected WebMarkupContainer settingMinimumActivePeriodContainer;
    protected Integer settingMinimumActivePeriodValue;
    protected TextField<Integer> settingMinimumActivePeriodField;
    protected TextFeedbackPanel settingMinimumActivePeriodFeedback;

    protected WebMarkupContainer settingMinimumActiveTypeBlock;
    protected WebMarkupContainer settingMinimumActiveTypeContainer;
    protected MinimumActivePeriodProvider settingMinimumActiveTypeProvider;
    protected Option settingMinimumActiveTypeValue;
    protected Select2SingleChoice<Option> settingMinimumActiveTypeField;
    protected TextFeedbackPanel settingMinimumActiveTypeFeedback;

    protected WebMarkupContainer settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodContainer;
    protected Integer settingLockInPeriodValue;
    protected TextField<Integer> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupContainer settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupContainer settingAllowDividendForInactiveClientBlock;
    protected WebMarkupContainer settingAllowDividendForInactiveClientContainer;
    protected Boolean settingAllowDividendForInactiveClientValue;
    protected CheckBox settingAllowDividendForInactiveClientField;
    protected TextFeedbackPanel settingAllowDividendForInactiveClientFeedback;

    // Market Price

    protected List<Map<String, Object>> marketPriceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> marketPriceTable;
    protected ListDataProvider marketPriceProvider;
    protected ModalWindow marketPricePopup;
    protected AjaxLink<Void> marketPriceAddLink;

    // Charges

    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;

    // Accounting

    protected String accountingValue = ACC_NONE;
    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashContainer;

    protected SingleChoiceProvider cashShareReferenceProvider;
    protected Option cashShareReferenceValue;
    protected Select2SingleChoice<Option> cashShareReferenceField;
    protected TextFeedbackPanel cashShareReferenceFeedback;

    protected SingleChoiceProvider cashShareSuspenseControlProvider;
    protected Option cashShareSuspenseControlValue;
    protected Select2SingleChoice<Option> cashShareSuspenseControlField;
    protected TextFeedbackPanel cashShareSuspenseControlFeedback;

    protected SingleChoiceProvider cashEquityProvider;
    protected Option cashEquityValue;
    protected Select2SingleChoice<Option> cashEquityField;
    protected TextFeedbackPanel cashEquityFeedback;

    protected SingleChoiceProvider cashIncomeFromFeesProvider;
    protected Option cashIncomeFromFeesValue;
    protected Select2SingleChoice<Option> cashIncomeFromFeesField;
    protected TextFeedbackPanel cashIncomeFromFeesFeedback;

    protected Option itemChargeValue;
    protected Date itemFromDateValue;
    protected Double itemUnitPriceValue;

    protected ModalWindow currencyPopup;

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
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.form.add(this.closeLink);

        this.currencyPopup = new ModalWindow("currencyPopup");
        add(this.currencyPopup);

        initDetail();

        initCurrency();

        initTerm();

        initSetting();

        initMarketPrice();

        initCharge();

        initAccounting();

        initDefault();
    }

    protected void initDefault() {
        accountingFieldUpdate(null);
    }

    protected void initAccounting() {
        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(ACC_NONE)));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(ACC_CASH)));
        this.form.add(this.accountingField);

        initAccountingCash();

    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashContainer.setVisible(false);

        if ("Cash".equals(this.accountingValue)) {
            this.cashContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
        }
        return false;
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashContainer = new WebMarkupContainer("cashContainer");
        this.cashBlock.add(this.cashContainer);

        this.cashShareReferenceProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashShareReferenceProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashShareReferenceProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.cashShareReferenceField = new Select2SingleChoice<>("cashShareReferenceField", new PropertyModel<>(this, "cashShareReferenceValue"), this.cashShareReferenceProvider);
        this.cashShareReferenceField.setLabel(Model.of("Share reference"));
        this.cashShareReferenceField.setRequired(false);
        this.cashShareReferenceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashShareReferenceField);
        this.cashShareReferenceFeedback = new TextFeedbackPanel("cashShareReferenceFeedback", this.cashShareReferenceField);
        this.cashContainer.add(this.cashShareReferenceFeedback);

        this.cashShareSuspenseControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashShareSuspenseControlProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashShareSuspenseControlProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Liability.getLiteral());
        this.cashShareSuspenseControlField = new Select2SingleChoice<>("cashShareSuspenseControlField", new PropertyModel<>(this, "cashShareSuspenseControlValue"), this.cashShareSuspenseControlProvider);
        this.cashShareSuspenseControlField.setLabel(Model.of("Share Suspense control"));
        this.cashShareSuspenseControlField.setRequired(false);
        this.cashShareSuspenseControlField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlFeedback = new TextFeedbackPanel("cashShareSuspenseControlFeedback", this.cashShareSuspenseControlField);
        this.cashContainer.add(this.cashShareSuspenseControlFeedback);

        this.cashEquityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashEquityProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashEquityProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Equity.getLiteral());
        this.cashEquityField = new Select2SingleChoice<>("cashEquityField", new PropertyModel<>(this, "cashEquityValue"), this.cashEquityProvider);
        this.cashEquityField.setLabel(Model.of("Equity"));
        this.cashEquityField.setRequired(false);
        this.cashEquityField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashEquityField);
        this.cashEquityFeedback = new TextFeedbackPanel("cashEquityFeedback", this.cashEquityField);
        this.cashContainer.add(this.cashEquityFeedback);

        this.cashIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeesField = new Select2SingleChoice<>("cashIncomeFromFeesField", new PropertyModel<>(this, "cashIncomeFromFeesValue"), this.cashIncomeFromFeesProvider);
        this.cashIncomeFromFeesField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeesField.setRequired(false);
        this.cashIncomeFromFeesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeesField);
        this.cashIncomeFromFeesFeedback = new TextFeedbackPanel("cashIncomeFromFeesFeedback", this.cashIncomeFromFeesField);
        this.cashContainer.add(this.cashIncomeFromFeesFeedback);
    }

    protected void initMarketPrice() {

        this.marketPricePopup = new ModalWindow("marketPricePopup");
        add(this.marketPricePopup);
        this.marketPricePopup.setOnClose(this::marketPricePopupOnClose);

        List<IColumn<Map<String, Object>, String>> marketPriceColumn = Lists.newArrayList();
        marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketFromDateColumn));
        marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketUnitPriceColumn));
        marketPriceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::marketPriceActionItem, this::marketPriceActionClick));
        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue);
        this.marketPriceTable = new DataTable<>("marketPriceTable", marketPriceColumn, this.marketPriceProvider, 20);
        this.form.add(this.marketPriceTable);
        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));

        this.marketPriceAddLink = new AjaxLink<>("marketPriceAddLink");
        this.marketPriceAddLink.setOnClick(this::marketPriceAddLinkClick);
        this.form.add(this.marketPriceAddLink);

    }

    protected void marketPricePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("unitPrice", this.itemUnitPriceValue);
        item.put("fromDate", this.itemFromDateValue);
        this.marketPriceValue.add(item);
        target.add(this.form);
    }

    protected boolean marketPriceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemFromDateValue = null;
        this.itemUnitPriceValue = null;
        this.marketPricePopup.setContent(new MarketPricePopup(this.marketPricePopup.getContentId(), this.marketPricePopup, this));
        this.marketPricePopup.show(target);
        return false;
    }

    protected ItemPanel marketFromDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd/MM/yyyy");
    }

    protected ItemPanel marketUnitPriceColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void marketPriceActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> marketPriceActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initCharge() {
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newArrayList();
        chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeNameColumn));
        chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeTypeColumn));
        chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeAmountColumn));
        chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeCollectColumn));
        chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeDateColumn));
        chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeActionItem, this::chargeActionClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);
    }

    protected void chargePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        String chargeId = this.itemChargeValue.getId();
        for (Map<String, Object> temp : this.chargeValue) {
            if (chargeId.equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select id, name, concat(charge_calculation_enum,'') type, concat(charge_time_enum,'') collect, amount from m_charge where id = ?", chargeId);
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
        item.put("uuid", chargeId);
        item.put("chargeId", chargeId);
        item.put("name", chargeObject.get("name"));
        item.put("type", type);
        item.put("amount", chargeObject.get("amount"));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.add(item);
        target.add(this.form);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        if (this.currencyCodeValue != null) {
            this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this, this.currencyCodeValue.getId()));
            this.chargePopup.show(target);
        } else {
            this.currencyPopup.setContent(new CurrencyPopup(this.currencyPopup.getContentId()));
            this.currencyPopup.show(target);
        }
        return false;
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
        Number value = (Number) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
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

    protected void initSetting() {

        this.settingSharePerClientMinimumBlock = new WebMarkupContainer("settingSharePerClientMinimumBlock");
        this.form.add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumContainer = new WebMarkupContainer("settingSharePerClientMinimumContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumContainer);
        this.settingSharePerClientMinimumField = new TextField<>("settingSharePerClientMinimumField", new PropertyModel<>(this, "settingSharePerClientMinimumValue"));
        this.settingSharePerClientMinimumField.setLabel(Model.of("Shares per Client Minimum"));
        this.settingSharePerClientMinimumField.setRequired(false);
        this.settingSharePerClientMinimumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMinimumContainer.add(this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumFeedback = new TextFeedbackPanel("settingSharePerClientMinimumFeedback", this.settingSharePerClientMinimumField);
        this.settingSharePerClientMinimumContainer.add(this.settingSharePerClientMinimumFeedback);

        this.settingSharePerClientDefaultBlock = new WebMarkupContainer("settingSharePerClientDefaultBlock");
        this.form.add(this.settingSharePerClientDefaultBlock);
        this.settingSharePerClientDefaultContainer = new WebMarkupContainer("settingSharePerClientDefaultContainer");
        this.settingSharePerClientDefaultBlock.add(this.settingSharePerClientDefaultContainer);
        this.settingSharePerClientDefaultField = new TextField<>("settingSharePerClientDefaultField", new PropertyModel<>(this, "settingSharePerClientDefaultValue"));
        this.settingSharePerClientDefaultField.setLabel(Model.of("Shares per Client Default"));
        this.settingSharePerClientDefaultField.setRequired(true);
        this.settingSharePerClientDefaultField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientDefaultContainer.add(this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultFeedback = new TextFeedbackPanel("settingSharePerClientDefaultFeedback", this.settingSharePerClientDefaultField);
        this.settingSharePerClientDefaultContainer.add(this.settingSharePerClientDefaultFeedback);

        this.settingSharePerClientMaximumBlock = new WebMarkupContainer("settingSharePerClientMaximumBlock");
        this.form.add(this.settingSharePerClientMaximumBlock);
        this.settingSharePerClientMaximumContainer = new WebMarkupContainer("settingSharePerClientMaximumContainer");
        this.settingSharePerClientMaximumBlock.add(this.settingSharePerClientMaximumContainer);
        this.settingSharePerClientMaximumField = new TextField<>("settingSharePerClientMaximumField", new PropertyModel<>(this, "settingSharePerClientMaximumValue"));
        this.settingSharePerClientMaximumField.setLabel(Model.of("Shares per Client Maximum"));
        this.settingSharePerClientMaximumField.setRequired(false);
        this.settingSharePerClientMaximumField.add(new OnChangeAjaxBehavior());
        this.settingSharePerClientMaximumContainer.add(this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumFeedback = new TextFeedbackPanel("settingSharePerClientMaximumFeedback", this.settingSharePerClientMaximumField);
        this.settingSharePerClientMaximumContainer.add(this.settingSharePerClientMaximumFeedback);

        this.settingMinimumActivePeriodBlock = new WebMarkupContainer("settingMinimumActivePeriodBlock");
        this.form.add(this.settingMinimumActivePeriodBlock);
        this.settingMinimumActivePeriodContainer = new WebMarkupContainer("settingMinimumActivePeriodContainer");
        this.settingMinimumActivePeriodBlock.add(this.settingMinimumActivePeriodContainer);
        this.settingMinimumActivePeriodField = new TextField<>("settingMinimumActivePeriodField", new PropertyModel<>(this, "settingMinimumActivePeriodValue"));
        this.settingMinimumActivePeriodField.setLabel(Model.of("Minimum Active Period"));
        this.settingMinimumActivePeriodField.setRequired(false);
        this.settingMinimumActivePeriodField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActivePeriodContainer.add(this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodFeedback = new TextFeedbackPanel("settingMinimumActivePeriodFeedback", this.settingMinimumActivePeriodField);
        this.settingMinimumActivePeriodContainer.add(this.settingMinimumActivePeriodFeedback);

        this.settingMinimumActiveTypeBlock = new WebMarkupContainer("settingMinimumActiveTypeBlock");
        this.form.add(this.settingMinimumActiveTypeBlock);
        this.settingMinimumActiveTypeContainer = new WebMarkupContainer("settingMinimumActiveTypeContainer");
        this.settingMinimumActiveTypeBlock.add(this.settingMinimumActiveTypeContainer);
        this.settingMinimumActiveTypeProvider = new MinimumActivePeriodProvider();
        this.settingMinimumActiveTypeField = new Select2SingleChoice<>("settingMinimumActiveTypeField", 0, new PropertyModel<>(this, "settingMinimumActiveTypeValue"), this.settingMinimumActiveTypeProvider);
        this.settingMinimumActiveTypeField.setLabel(Model.of("Type"));
        this.settingMinimumActiveTypeField.setRequired(false);
        this.settingMinimumActiveTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumActiveTypeContainer.add(this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeFeedback = new TextFeedbackPanel("settingMinimumActiveTypeFeedback", this.settingMinimumActiveTypeField);
        this.settingMinimumActiveTypeContainer.add(this.settingMinimumActiveTypeFeedback);

        this.settingLockInPeriodBlock = new WebMarkupContainer("settingLockInPeriodBlock");
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodContainer = new WebMarkupContainer("settingLockInPeriodContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.setRequired(false);
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupContainer("settingLockInTypeBlock");
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeContainer = new WebMarkupContainer("settingLockInTypeContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0, new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.setRequired(false);
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeContainer.add(this.settingLockInTypeFeedback);

        this.settingAllowDividendForInactiveClientBlock = new WebMarkupContainer("settingAllowDividendForInactiveClientBlock");
        this.form.add(this.settingAllowDividendForInactiveClientBlock);
        this.settingAllowDividendForInactiveClientContainer = new WebMarkupContainer("settingAllowDividendForInactiveClientContainer");
        this.settingAllowDividendForInactiveClientBlock.add(this.settingAllowDividendForInactiveClientContainer);
        this.settingAllowDividendForInactiveClientField = new CheckBox("settingAllowDividendForInactiveClientField", new PropertyModel<>(this, "settingAllowDividendForInactiveClientValue"));
        this.settingAllowDividendForInactiveClientField.setRequired(false);
        this.settingAllowDividendForInactiveClientField.add(new OnChangeAjaxBehavior());
        this.settingAllowDividendForInactiveClientContainer.add(this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientFeedback = new TextFeedbackPanel("settingAllowDividendForInactiveClientFeedback", this.settingAllowDividendForInactiveClientField);
        this.settingAllowDividendForInactiveClientContainer.add(this.settingAllowDividendForInactiveClientFeedback);

    }

    protected void initTerm() {

        this.termTotalNumberOfShareBlock = new WebMarkupContainer("termTotalNumberOfShareBlock");
        this.form.add(this.termTotalNumberOfShareBlock);
        this.termTotalNumberOfShareContainer = new WebMarkupContainer("termTotalNumberOfShareContainer");
        this.termTotalNumberOfShareBlock.add(this.termTotalNumberOfShareContainer);
        this.termTotalNumberOfShareField = new TextField<>("termTotalNumberOfShareField", new PropertyModel<>(this, "termTotalNumberOfShareValue"));
        this.termTotalNumberOfShareField.setLabel(Model.of("Total Number of Shares"));
        this.termTotalNumberOfShareField.setRequired(true);
        this.termTotalNumberOfShareField.add(new OnChangeAjaxBehavior());
        this.termTotalNumberOfShareContainer.add(this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareFeedback = new TextFeedbackPanel("termTotalNumberOfShareFeedback", this.termTotalNumberOfShareField);
        this.termTotalNumberOfShareContainer.add(this.termTotalNumberOfShareFeedback);

        this.termShareToBeIssuedBlock = new WebMarkupContainer("termShareToBeIssuedBlock");
        this.form.add(this.termShareToBeIssuedBlock);
        this.termShareToBeIssuedContainer = new WebMarkupContainer("termShareToBeIssuedContainer");
        this.termShareToBeIssuedBlock.add(this.termShareToBeIssuedContainer);
        this.termShareToBeIssuedField = new TextField<>("termShareToBeIssuedField", new PropertyModel<>(this, "termShareToBeIssuedValue"));
        this.termShareToBeIssuedField.setLabel(Model.of("Shares to be issued"));
        this.termShareToBeIssuedField.setRequired(true);
        this.termShareToBeIssuedField.add(new OnChangeAjaxBehavior());
        this.termShareToBeIssuedContainer.add(this.termShareToBeIssuedField);
        this.termShareToBeIssuedFeedback = new TextFeedbackPanel("termShareToBeIssuedFeedback", this.termShareToBeIssuedField);
        this.termShareToBeIssuedContainer.add(this.termShareToBeIssuedFeedback);

        this.termNominalPriceBlock = new WebMarkupContainer("termNominalPriceBlock");
        this.form.add(this.termNominalPriceBlock);
        this.termNominalPriceContainer = new WebMarkupContainer("termNominalPriceContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceContainer);
        this.termNominalPriceField = new TextField<>("termNominalPriceField", new PropertyModel<>(this, "termNominalPriceValue"));
        this.termNominalPriceField.setLabel(Model.of("Nominal Price"));
        this.termNominalPriceField.setRequired(true);
        this.termNominalPriceField.add(new OnChangeAjaxBehavior());
        this.termNominalPriceContainer.add(this.termNominalPriceField);
        this.termNominalPriceFeedback = new TextFeedbackPanel("termNominalPriceFeedback", this.termNominalPriceField);
        this.termNominalPriceContainer.add(this.termNominalPriceFeedback);

        this.termCapitalBlock = new WebMarkupContainer("termCapitalBlock");
        this.form.add(this.termCapitalBlock);
        this.termCapitalContainer = new WebMarkupContainer("termCapitalContainer");
        this.termCapitalBlock.add(this.termCapitalContainer);
        this.termCapitalField = new TextField<>("termCapitalField", new PropertyModel<>(this, "termCapitalValue"));
        this.termCapitalField.setLabel(Model.of("Capital Value"));
        this.termCapitalField.setRequired(false);
        this.termCapitalField.add(new OnChangeAjaxBehavior());
        this.termCapitalContainer.add(this.termCapitalField);
        this.termCapitalFeedback = new TextFeedbackPanel("termCapitalFeedback", this.termCapitalField);
        this.termCapitalContainer.add(this.termCapitalFeedback);

    }

    protected void initCurrency() {

        this.currencyCodeBlock = new WebMarkupContainer("currencyCodeBlock");
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeContainer = new WebMarkupContainer("currencyCodeContainer");
        this.currencyCodeBlock.add(this.currencyCodeContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeField.setRequired(true);
        this.currencyCodeContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeContainer.add(this.currencyCodeFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupContainer("currencyDecimalPlaceBlock");
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceContainer = new WebMarkupContainer("currencyDecimalPlaceContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceField.add(RangeValidator.range((int) 0, (int) 6));
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyMultipleOfBlock = new WebMarkupContainer("currencyMultipleOfBlock");
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfContainer = new WebMarkupContainer("currencyMultipleOfContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.setRequired(false);
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfField.add(RangeValidator.minimum((int) 1));
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfContainer.add(this.currencyMultipleOfFeedback);
    }

    protected void initDetail() {
        this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
        this.detailProductNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
        this.detailProductNameBlock.add(this.detailProductNameContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameField.setRequired(true);
        this.detailProductNameContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameContainer.add(this.detailProductNameFeedback);

        this.detailShortNameBlock = new WebMarkupContainer("detailShortNameBlock");
        this.detailShortNameBlock.setOutputMarkupId(true);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameContainer = new WebMarkupContainer("detailShortNameContainer");
        this.detailShortNameBlock.add(this.detailShortNameContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameField.setRequired(true);
        this.detailShortNameContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameContainer.add(this.detailShortNameFeedback);

        this.detailDescriptionBlock = new WebMarkupContainer("detailDescriptionBlock");
        this.detailDescriptionBlock.setOutputMarkupId(true);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionContainer = new WebMarkupContainer("detailDescriptionContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionField.setRequired(true);
        this.detailDescriptionContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionContainer.add(this.detailDescriptionFeedback);
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
            if (this.cashIncomeFromFeesValue != null) {
                builder.withIncomeFromFeeAccountId(this.cashIncomeFromFeesValue.getId());
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
