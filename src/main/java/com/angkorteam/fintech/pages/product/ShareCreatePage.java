package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.ShareBuilder;
import com.angkorteam.fintech.dto.share.LockInPeriod;
import com.angkorteam.fintech.dto.share.MinimumActivePeriod;
import com.angkorteam.fintech.helper.ShareHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.share.MarketPricePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.share.LockInPeriodProvider;
import com.angkorteam.fintech.provider.share.MinimumActivePeriodProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    // Detail

    private WebMarkupContainer detailProductNameBlock;
    private WebMarkupContainer detailProductNameContainer;
    private String detailProductNameValue;
    private TextField<String> detailProductNameField;
    private TextFeedbackPanel detailProductNameFeedback;

    private WebMarkupContainer detailShortNameBlock;
    private WebMarkupContainer detailShortNameContainer;
    private String detailShortNameValue;
    private TextField<String> detailShortNameField;
    private TextFeedbackPanel detailShortNameFeedback;

    private WebMarkupContainer detailDescriptionBlock;
    private WebMarkupContainer detailDescriptionContainer;
    private String detailDescriptionValue;
    private TextField<String> detailDescriptionField;
    private TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    private WebMarkupContainer currencyCodeBlock;
    private WebMarkupContainer currencyCodeContainer;
    private SingleChoiceProvider currencyCodeProvider;
    private Option currencyCodeValue;
    private Select2SingleChoice<Option> currencyCodeField;
    private TextFeedbackPanel currencyCodeFeedback;

    private WebMarkupContainer currencyDecimalPlaceBlock;
    private WebMarkupContainer currencyDecimalPlaceContainer;
    private Integer currencyDecimalPlaceValue;
    private TextField<Integer> currencyDecimalPlaceField;
    private TextFeedbackPanel currencyDecimalPlaceFeedback;

    private WebMarkupContainer currencyMultipleOfBlock;
    private WebMarkupContainer currencyMultipleOfContainer;
    private Integer currencyMultipleOfValue;
    private TextField<Integer> currencyMultipleOfField;
    private TextFeedbackPanel currencyMultipleOfFeedback;

    // Term

    private WebMarkupContainer termTotalNumberOfShareBlock;
    private WebMarkupContainer termTotalNumberOfShareContainer;
    private Integer termTotalNumberOfShareValue;
    private TextField<Integer> termTotalNumberOfShareField;
    private TextFeedbackPanel termTotalNumberOfShareFeedback;

    private WebMarkupContainer termShareToBeIssuedBlock;
    private WebMarkupContainer termShareToBeIssuedContainer;
    private Integer termShareToBeIssuedValue;
    private TextField<Integer> termShareToBeIssuedField;
    private TextFeedbackPanel termShareToBeIssuedFeedback;

    private WebMarkupContainer termNominalPriceBlock;
    private WebMarkupContainer termNominalPriceContainer;
    private Double termNominalPriceValue;
    private TextField<Double> termNominalPriceField;
    private TextFeedbackPanel termNominalPriceFeedback;

    private WebMarkupContainer termCapitalBlock;
    private WebMarkupContainer termCapitalContainer;
    private Double termCapitalValue;
    private TextField<Double> termCapitalField;
    private TextFeedbackPanel termCapitalFeedback;

    // Setting

    private WebMarkupContainer settingSharePerClientMinimumBlock;
    private WebMarkupContainer settingSharePerClientMinimumContainer;
    private Integer settingSharePerClientMinimumValue;
    private TextField<Integer> settingSharePerClientMinimumField;
    private TextFeedbackPanel settingSharePerClientMinimumFeedback;

    private WebMarkupContainer settingSharePerClientDefaultBlock;
    private WebMarkupContainer settingSharePerClientDefaultContainer;
    private Integer settingSharePerClientDefaultValue;
    private TextField<Integer> settingSharePerClientDefaultField;
    private TextFeedbackPanel settingSharePerClientDefaultFeedback;

    private WebMarkupContainer settingSharePerClientMaximumBlock;
    private WebMarkupContainer settingSharePerClientMaximumContainer;
    private Integer settingSharePerClientMaximumValue;
    private TextField<Integer> settingSharePerClientMaximumField;
    private TextFeedbackPanel settingSharePerClientMaximumFeedback;

    private WebMarkupContainer settingMinimumActivePeriodBlock;
    private WebMarkupContainer settingMinimumActivePeriodContainer;
    private Integer settingMinimumActivePeriodValue;
    private TextField<Integer> settingMinimumActivePeriodField;
    private TextFeedbackPanel settingMinimumActivePeriodFeedback;

    private WebMarkupContainer settingMinimumActiveTypeBlock;
    private WebMarkupContainer settingMinimumActiveTypeContainer;
    private MinimumActivePeriodProvider settingMinimumActiveTypeProvider;
    private Option settingMinimumActiveTypeValue;
    private Select2SingleChoice<Option> settingMinimumActiveTypeField;
    private TextFeedbackPanel settingMinimumActiveTypeFeedback;

    private WebMarkupContainer settingLockInPeriodBlock;
    private WebMarkupContainer settingLockInPeriodContainer;
    private Integer settingLockInPeriodValue;
    private TextField<Integer> settingLockInPeriodField;
    private TextFeedbackPanel settingLockInPeriodFeedback;

    private WebMarkupContainer settingLockInTypeBlock;
    private WebMarkupContainer settingLockInTypeContainer;
    private LockInPeriodProvider settingLockInTypeProvider;
    private Option settingLockInTypeValue;
    private Select2SingleChoice<Option> settingLockInTypeField;
    private TextFeedbackPanel settingLockInTypeFeedback;

    private WebMarkupContainer settingAllowDividendForInactiveClientBlock;
    private WebMarkupContainer settingAllowDividendForInactiveClientContainer;
    private Boolean settingAllowDividendForInactiveClientValue;
    private CheckBox settingAllowDividendForInactiveClientField;
    private TextFeedbackPanel settingAllowDividendForInactiveClientFeedback;

    // Market Price

    private List<Map<String, Object>> marketPriceValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> marketPriceTable;
    private ListDataProvider marketPriceProvider;
    private ModalWindow marketPricePopup;
    private AjaxLink<Void> marketPriceAddLink;

    // Charges

    private List<Map<String, Object>> chargeValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> chargeTable;
    private ListDataProvider chargeProvider;
    private ModalWindow chargePopup;
    private AjaxLink<Void> chargeAddLink;

    // Accounting

    private String accountingValue = ACC_NONE;
    private RadioGroup<String> accountingField;

    private WebMarkupContainer cashBlock;
    private WebMarkupContainer cashContainer;

    private SingleChoiceProvider cashShareReferenceProvider;
    private Option cashShareReferenceValue;
    private Select2SingleChoice<Option> cashShareReferenceField;
    private TextFeedbackPanel cashShareReferenceFeedback;

    private SingleChoiceProvider cashShareSuspenseControlProvider;
    private Option cashShareSuspenseControlValue;
    private Select2SingleChoice<Option> cashShareSuspenseControlField;
    private TextFeedbackPanel cashShareSuspenseControlFeedback;

    private SingleChoiceProvider cashEquityProvider;
    private Option cashEquityValue;
    private Select2SingleChoice<Option> cashEquityField;
    private TextFeedbackPanel cashEquityFeedback;

    private SingleChoiceProvider cashIncomeFromFeesProvider;
    private Option cashIncomeFromFeesValue;
    private Select2SingleChoice<Option> cashIncomeFromFeesField;
    private TextFeedbackPanel cashIncomeFromFeesFeedback;

    private Option itemChargeValue;
    private Date itemFromDateValue;
    private Double itemUnitPriceValue;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
        this.cashShareReferenceProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashShareReferenceProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashShareReferenceField = new Select2SingleChoice<>("cashShareReferenceField", new PropertyModel<>(this, "cashShareReferenceValue"), this.cashShareReferenceProvider);
        this.cashShareReferenceField.setRequired(false);
        this.cashShareReferenceField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashShareReferenceField);
        this.cashShareReferenceFeedback = new TextFeedbackPanel("cashShareReferenceFeedback", this.cashShareReferenceField);
        this.cashContainer.add(this.cashShareReferenceFeedback);

        this.cashShareSuspenseControlProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashShareSuspenseControlProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashShareSuspenseControlProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashShareSuspenseControlField = new Select2SingleChoice<>("cashShareSuspenseControlField", new PropertyModel<>(this, "cashShareSuspenseControlValue"), this.cashShareSuspenseControlProvider);
        this.cashShareSuspenseControlField.setRequired(false);
        this.cashShareSuspenseControlField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlFeedback = new TextFeedbackPanel("cashShareSuspenseControlFeedback", this.cashShareSuspenseControlField);
        this.cashContainer.add(this.cashShareSuspenseControlFeedback);

        this.cashEquityProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashEquityProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashEquityProvider.applyWhere("classification_enum", "classification_enum = 1");
        this.cashEquityField = new Select2SingleChoice<>("cashEquityField", new PropertyModel<>(this, "cashEquityValue"), this.cashEquityProvider);
        this.cashEquityField.setRequired(false);
        this.cashEquityField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashEquityField);
        this.cashEquityFeedback = new TextFeedbackPanel("cashEquityFeedback", this.cashEquityField);
        this.cashContainer.add(this.cashEquityFeedback);

        this.cashIncomeFromFeesProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.cashIncomeFromFeesProvider.applyWhere("account_usage", "account_usage = 1");
        this.cashIncomeFromFeesProvider.applyWhere("classification_enum", "classification_enum = 4");
        this.cashIncomeFromFeesField = new Select2SingleChoice<>("cashIncomeFromFeesField", new PropertyModel<>(this, "cashIncomeFromFeesValue"), this.cashIncomeFromFeesProvider);
        this.cashIncomeFromFeesField.setRequired(false);
        this.cashIncomeFromFeesField.add(new OnChangeAjaxBehavior());
        this.cashContainer.add(this.cashIncomeFromFeesField);
        this.cashIncomeFromFeesFeedback = new TextFeedbackPanel("cashIncomeFromFeesFeedback", this.cashIncomeFromFeesField);
        this.cashContainer.add(this.cashIncomeFromFeesFeedback);
    }

    protected void initMarketPrice() {

        this.marketPricePopup = new ModalWindow("marketPricePopup");
        add(this.marketPricePopup);
        this.marketPricePopup.setContent(new MarketPricePopup(this.marketPricePopup.getContentId(), this.marketPricePopup, this));
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
        this.marketPricePopup.show(target);
        return false;
    }

    protected ItemPanel marketFromDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value)));
        }
    }

    protected ItemPanel marketUnitPriceColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    protected ItemPanel marketPriceAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value.doubleValue())));
        }
    }

    protected ItemPanel marketPriceCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected ItemPanel marketPriceDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected void marketPriceActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.marketPriceValue.size(); i++) {
            Map<String, Object> column = this.marketPriceValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.marketPriceValue.remove(index);
        }
        ajaxRequestTarget.add(this.marketPriceTable);
    }

    protected List<ActionItem> marketPriceActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initCharge() {
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setContent(new ChargePopup(this.chargePopup.getContentId(), this.chargePopup, this));
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
        this.chargePopup.show(target);
        return false;
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Number value = (Number) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value.doubleValue())));
        }
    }

    protected ItemPanel chargeCollectColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(value));
        }
    }

    protected void chargeActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.size(); i++) {
            Map<String, Object> column = this.chargeValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.remove(index);
        }
        ajaxRequestTarget.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void initSetting() {

        this.settingSharePerClientMinimumBlock = new WebMarkupContainer("settingSharePerClientMinimumBlock");
        this.form.add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumContainer = new WebMarkupContainer("settingSharePerClientMinimumContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumContainer);
        this.settingSharePerClientMinimumField = new TextField<>("settingSharePerClientMinimumField", new PropertyModel<>(this, "settingSharePerClientMinimumValue"));
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
        this.settingLockInPeriodField.setRequired(false);
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupContainer("settingLockInTypeBlock");
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeContainer = new WebMarkupContainer("settingLockInTypeContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeContainer);
        this.settingLockInTypeProvider = new LockInPeriodProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", 0, new PropertyModel<>(this, "settingLockInTypeValue"), this.settingLockInTypeProvider);
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
        this.termShareToBeIssuedField.setRequired(false);
        this.termShareToBeIssuedField.add(new OnChangeAjaxBehavior());
        this.termShareToBeIssuedContainer.add(this.termShareToBeIssuedField);
        this.termShareToBeIssuedFeedback = new TextFeedbackPanel("termShareToBeIssuedFeedback", this.termShareToBeIssuedField);
        this.termShareToBeIssuedContainer.add(this.termShareToBeIssuedFeedback);

        this.termNominalPriceBlock = new WebMarkupContainer("termNominalPriceBlock");
        this.form.add(this.termNominalPriceBlock);
        this.termNominalPriceContainer = new WebMarkupContainer("termNominalPriceContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceContainer);
        this.termNominalPriceField = new TextField<>("termNominalPriceField", new PropertyModel<>(this, "termNominalPriceValue"));
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
        this.currencyCodeProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name", "concat(name,' [', code,']')");
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", 0, new PropertyModel<>(this, "currencyCodeValue"), this.currencyCodeProvider);
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
            builder.withLockinPeriodFrequencyType(LockInPeriod.valueOf(this.settingLockInTypeValue.getId()));
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
