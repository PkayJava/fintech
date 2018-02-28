package com.angkorteam.fintech.pages.product.share;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.*;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.*;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SharePreviewPage extends Page {

    protected String shareId;

    protected BookmarkablePageLink<Void> editLink;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected String detailProductNameValue;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected String detailShortNameValue;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected String detailDescriptionValue;
    protected ReadOnlyView detailDescriptionView;

    // Currency

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected String currencyCodeValue;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected Long currencyDecimalPlaceValue;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfVContainer;
    protected Long currencyMultipleOfValue;
    protected ReadOnlyView currencyMultipleOfView;

    // Term

    protected WebMarkupBlock termTotalNumberOfShareBlock;
    protected WebMarkupContainer termTotalNumberOfShareVContainer;
    protected Long termTotalNumberOfShareValue;
    protected ReadOnlyView termTotalNumberOfShareView;

    protected WebMarkupBlock termShareToBeIssuedBlock;
    protected WebMarkupContainer termShareToBeIssuedVContainer;
    protected Long termShareToBeIssuedValue;
    protected ReadOnlyView termShareToBeIssuedView;

    protected WebMarkupBlock termNominalPriceBlock;
    protected WebMarkupContainer termNominalPriceVContainer;
    protected Double termNominalPriceValue;
    protected ReadOnlyView termNominalPriceView;

    protected WebMarkupBlock termCapitalBlock;
    protected WebMarkupContainer termCapitalVContainer;
    protected Double termCapitalValue;
    protected ReadOnlyView termCapitalView;

    // Setting

    protected WebMarkupBlock settingSharePerClientMinimumBlock;
    protected WebMarkupContainer settingSharePerClientMinimumVContainer;
    protected Long settingSharePerClientMinimumValue;
    protected ReadOnlyView settingSharePerClientMinimumView;

    protected WebMarkupBlock settingSharePerClientDefaultBlock;
    protected WebMarkupContainer settingSharePerClientDefaultVContainer;
    protected Long settingSharePerClientDefaultValue;
    protected ReadOnlyView settingSharePerClientDefaultView;

    protected WebMarkupBlock settingSharePerClientMaximumBlock;
    protected WebMarkupContainer settingSharePerClientMaximumVContainer;
    protected Long settingSharePerClientMaximumValue;
    protected ReadOnlyView settingSharePerClientMaximumView;

    protected WebMarkupBlock settingMinimumActivePeriodBlock;
    protected WebMarkupContainer settingMinimumActivePeriodVContainer;
    protected Long settingMinimumActivePeriodValue;
    protected ReadOnlyView settingMinimumActivePeriodView;

    protected WebMarkupBlock settingMinimumActiveTypeBlock;
    protected WebMarkupContainer settingMinimumActiveTypeVContainer;
    protected Option settingMinimumActiveTypeValue;
    protected ReadOnlyView settingMinimumActiveTypeView;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodVContainer;
    protected Long settingLockInPeriodValue;
    protected ReadOnlyView settingLockInPeriodView;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeVContainer;
    protected Option settingLockInTypeValue;
    protected ReadOnlyView settingLockInTypeView;

    protected WebMarkupBlock settingAllowDividendForInactiveClientBlock;
    protected WebMarkupContainer settingAllowDividendForInactiveClientVContainer;
    protected Boolean settingAllowDividendForInactiveClientValue;
    protected ReadOnlyView settingAllowDividendForInactiveClientView;

    // Market Price

    protected WebMarkupBlock marketPriceBlock;
    protected WebMarkupContainer marketPriceVContainer;
    protected List<Map<String, Object>> marketPriceValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> marketPriceTable;
    protected ListDataProvider marketPriceProvider;
    protected List<IColumn<Map<String, Object>, String>> marketPriceColumn;

    // Charges

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected List<Map<String, Object>> chargeValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected Label accountingLabel;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashVContainer;

    protected WebMarkupBlock cashShareReferenceBlock;
    protected WebMarkupContainer cashShareReferenceVContainer;
    protected Option cashShareReferenceValue;
    protected ReadOnlyView cashShareReferenceView;

    protected WebMarkupBlock cashShareSuspenseControlBlock;
    protected WebMarkupContainer cashShareSuspenseControlVContainer;
    protected Option cashShareSuspenseControlValue;
    protected ReadOnlyView cashShareSuspenseControlView;

    protected WebMarkupBlock cashEquityBlock;
    protected WebMarkupContainer cashEquityVContainer;
    protected Option cashEquityValue;
    protected ReadOnlyView cashEquityView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected Option cashIncomeFromFeeValue;
    protected ReadOnlyView cashIncomeFromFeeView;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {

        this.editLink = new BookmarkablePageLink<>("editLink", ShareBrowsePage.class);
        this.add(this.editLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.add(this.closeLink);

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
    }

    @Override
    protected void configureMetaData() {
        this.cashVContainer.setVisible(AccountingType.Cash.getDescription().equals(this.accountingValue));
    }

    @Override
    protected void initData() {
        this.shareId = getPageParameters().get("shareId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MShareProduct.NAME);
        selectQuery.addJoin("INNER JOIN " + MOrganisationCurrency.NAME + " ON " + MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_CODE + " = " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE);
        selectQuery.addField("concat(" + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.NAME + ", ' [', " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE + ", ']') currency");
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.NAME);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.SHORT_NAME);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.DESCRIPTION);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.TOTAL_SHARES);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ISSUED_SHARES);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.UNIT_PRICE);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CAPITAL_AMOUNT);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_CLIENT_SHARES);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.NOMINAL_CLIENT_SHARES);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MAXIMUM_CLIENT_SHARES);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ALLOW_DIVIDENDS_INACTIVE_CLIENTS);
        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ACCOUNTING_TYPE);
        selectQuery.addWhere(MShareProduct.NAME + "." + MShareProduct.Field.ID + " = '" + this.shareId + "'");
        Map<String, Object> shareObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.detailProductNameValue = (String) shareObject.get(MShareProduct.Field.NAME);
        this.detailDescriptionValue = (String) shareObject.get(MShareProduct.Field.DESCRIPTION);
        this.detailShortNameValue = (String) shareObject.get(MShareProduct.Field.SHORT_NAME);

        this.currencyCodeValue = (String) shareObject.get("currency");
        this.currencyDecimalPlaceValue = (Long) shareObject.get(MShareProduct.Field.CURRENCY_DIGITS);
        this.currencyMultipleOfValue = (Long) shareObject.get(MShareProduct.Field.CURRENCY_MULTIPLES_OF);

        this.termTotalNumberOfShareValue = (Long) shareObject.get(MShareProduct.Field.TOTAL_SHARES);
        this.termShareToBeIssuedValue = (Long) shareObject.get(MShareProduct.Field.ISSUED_SHARES);
        this.termNominalPriceValue = (Double) shareObject.get(MShareProduct.Field.UNIT_PRICE);
        this.termCapitalValue = (Double) shareObject.get(MShareProduct.Field.CAPITAL_AMOUNT);

        this.settingSharePerClientMinimumValue = (Long) shareObject.get(MShareProduct.Field.MINIMUM_CLIENT_SHARES);
        this.settingSharePerClientDefaultValue = (Long) shareObject.get(MShareProduct.Field.NOMINAL_CLIENT_SHARES);
        this.settingSharePerClientMaximumValue = (Long) shareObject.get(MShareProduct.Field.MAXIMUM_CLIENT_SHARES);

        Double lockin_period_frequency = (Double) shareObject.get(MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        this.settingLockInPeriodValue = lockin_period_frequency == null ? null : lockin_period_frequency.longValue();
        this.settingLockInTypeValue = LockInType.optionLiteral(String.valueOf(shareObject.get(MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM)));

        Double minimum_active_period_frequency = (Double) shareObject.get(MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY);
        this.settingMinimumActivePeriodValue = minimum_active_period_frequency == null ? null : minimum_active_period_frequency.longValue();
        this.settingMinimumActiveTypeValue = LockInType.optionLiteral(String.valueOf(shareObject.get(MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM)));

        Long allow_dividends_inactive_clients = (Long) shareObject.get(MShareProduct.Field.ALLOW_DIVIDENDS_INACTIVE_CLIENTS);
        this.settingAllowDividendForInactiveClientValue = allow_dividends_inactive_clients != null && allow_dividends_inactive_clients == 1;

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MShareProductCharge.NAME + " ON " + MShareProductCharge.NAME + "." + MShareProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField("concat(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') as " + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
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
        selectQuery.addWhere(MShareProductCharge.NAME + "." + MShareProductCharge.Field.PRODUCT_ID + " = '" + this.shareId + "'");
        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> chargeObject : chargeObjects) {
            Map<String, Object> charge = new HashMap<>();
            charge.put("name", chargeObject.get(MCharge.Field.NAME));
            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM)));
            charge.put("type", type);
            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM)));
            charge.put("collect", collect);
            charge.put("amount", chargeObject.get(MCharge.Field.AMOUNT));
            this.chargeValue.add(charge);
        }

        selectQuery = new SelectQuery(MShareProductMarketPrice.NAME);
        selectQuery.addWhere(MShareProductMarketPrice.Field.PRODUCT_ID + " = :" + MShareProductMarketPrice.Field.PRODUCT_ID, this.shareId);
        selectQuery.addField(MShareProductMarketPrice.Field.SHARE_VALUE);
        selectQuery.addField(MShareProductMarketPrice.Field.FROM_DATE);
        List<Map<String, Object>> shareMarketPrices = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        for (Map<String, Object> shareMarketPrice : shareMarketPrices) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("unitPrice", shareMarketPrice.get(MShareProductMarketPrice.Field.SHARE_VALUE));
            item.put("fromDate", shareMarketPrice.get(MShareProductMarketPrice.Field.FROM_DATE));
            this.marketPriceValue.add(item);
        }

        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(shareObject.get(MShareProduct.Field.ACCOUNTING_TYPE)));

        if (accountingType != null) {
            this.accountingValue = accountingType.getDescription();
            selectQuery = new SelectQuery(AccProductMapping.NAME);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.shareId);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Share.getLiteral());
            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
            selectQuery.addField(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE);
            List<Map<String, Object>> mappings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
            for (Map<String, Object> mapping : mappings) {
                if (mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null && mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE) != null) {
                    FinancialAccountType financialAccountType = FinancialAccountType.parseLiteral(String.valueOf(mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE)));
                    if (financialAccountType == FinancialAccountType.SavingReference1) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashShareReferenceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.SavingControl2) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashShareSuspenseControlValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.IncomeFee4) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashEquityValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    }
                }
            }
        }
    }

    protected void initSectionAccounting() {
        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this, "accountingValue"));
        add(this.accountingLabel);

        initSectionAccountingCash();

    }

    protected void initSectionAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        add(this.cashBlock);
        this.cashVContainer = new WebMarkupContainer("cashVContainer");
        this.cashBlock.add(this.cashVContainer);

        this.cashShareReferenceBlock = new WebMarkupBlock("cashShareReferenceBlock", Size.Six_6);
        this.cashVContainer.add(this.cashShareReferenceBlock);
        this.cashShareReferenceVContainer = new WebMarkupContainer("cashShareReferenceVContainer");
        this.cashShareReferenceBlock.add(this.cashShareReferenceVContainer);
        this.cashShareReferenceView = new ReadOnlyView("cashShareReferenceView", new PropertyModel<>(this, "cashShareReferenceValue"));
        this.cashShareReferenceVContainer.add(this.cashShareReferenceView);

        this.cashShareSuspenseControlBlock = new WebMarkupBlock("cashShareSuspenseControlBlock", Size.Six_6);
        this.cashVContainer.add(this.cashShareSuspenseControlBlock);
        this.cashShareSuspenseControlVContainer = new WebMarkupContainer("cashShareSuspenseControlVContainer");
        this.cashShareSuspenseControlBlock.add(this.cashShareSuspenseControlVContainer);
        this.cashShareSuspenseControlView = new ReadOnlyView("cashShareSuspenseControlView", new PropertyModel<>(this, "cashShareSuspenseControlValue"));
        this.cashShareSuspenseControlVContainer.add(this.cashShareSuspenseControlView);

        this.cashEquityBlock = new WebMarkupBlock("cashEquityBlock", Size.Six_6);
        this.cashVContainer.add(this.cashEquityBlock);
        this.cashEquityVContainer = new WebMarkupContainer("cashEquityVContainer");
        this.cashEquityBlock.add(this.cashEquityVContainer);
        this.cashEquityView = new ReadOnlyView("cashEquityView", new PropertyModel<>(this, "cashEquityValue"));
        this.cashEquityVContainer.add(this.cashEquityView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
    }

    protected void initSectionMarketPrice() {

        initMarketPriceBlock();

    }

    protected void initMarketPriceBlock() {
        this.marketPriceBlock = new WebMarkupBlock("marketPriceBlock", Size.Twelve_12);
        add(this.marketPriceBlock);
        this.marketPriceVContainer = new WebMarkupContainer("marketPriceVContainer");
        this.marketPriceBlock.add(this.marketPriceVContainer);

        this.marketPriceColumn = Lists.newArrayList();
        this.marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketPriceColumn));
        this.marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketPriceColumn));
        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue);
        this.marketPriceTable = new DataTable<>("marketPriceTable", this.marketPriceColumn, this.marketPriceProvider, 20);
        this.marketPriceVContainer.add(this.marketPriceTable);
        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));
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

    protected void initSectionCharge() {

        initChargeBlock();

    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeVContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
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
        add(this.settingAllowDividendForInactiveClientBlock);
        this.settingAllowDividendForInactiveClientVContainer = new WebMarkupContainer("settingAllowDividendForInactiveClientVContainer");
        this.settingAllowDividendForInactiveClientBlock.add(this.settingAllowDividendForInactiveClientVContainer);
        this.settingAllowDividendForInactiveClientView = new ReadOnlyView("settingAllowDividendForInactiveClientView", new PropertyModel<>(this, "settingAllowDividendForInactiveClientValue"));
        this.settingAllowDividendForInactiveClientVContainer.add(this.settingAllowDividendForInactiveClientView);
    }

    protected void initSettingLockInTypeBlock() {
        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        add(this.settingLockInTypeBlock);
        this.settingLockInTypeVContainer = new WebMarkupContainer("settingLockInTypeVContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeVContainer);
        this.settingLockInTypeView = new ReadOnlyView("settingLockInTypeView", new PropertyModel<>(this, "settingLockInTypeValue"));
        this.settingLockInTypeVContainer.add(this.settingLockInTypeView);
    }

    protected void initSettingLockInPeriodBlock() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodVContainer = new WebMarkupContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodVContainer);
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);
    }

    protected void initSettingMinimumActiveTypeBlock() {
        this.settingMinimumActiveTypeBlock = new WebMarkupBlock("settingMinimumActiveTypeBlock", Size.Four_4);
        add(this.settingMinimumActiveTypeBlock);
        this.settingMinimumActiveTypeVContainer = new WebMarkupContainer("settingMinimumActiveTypeVContainer");
        this.settingMinimumActiveTypeBlock.add(this.settingMinimumActiveTypeVContainer);
        this.settingMinimumActiveTypeView = new ReadOnlyView("settingMinimumActiveTypeView", new PropertyModel<>(this, "settingMinimumActiveTypeValue"));
        this.settingMinimumActiveTypeVContainer.add(this.settingMinimumActiveTypeView);
    }

    protected void initSettingMinimumActivePeriodBlock() {
        this.settingMinimumActivePeriodBlock = new WebMarkupBlock("settingMinimumActivePeriodBlock", Size.Four_4);
        add(this.settingMinimumActivePeriodBlock);
        this.settingMinimumActivePeriodVContainer = new WebMarkupContainer("settingMinimumActivePeriodVContainer");
        this.settingMinimumActivePeriodBlock.add(this.settingMinimumActivePeriodVContainer);
        this.settingMinimumActivePeriodView = new ReadOnlyView("settingMinimumActivePeriodView", new PropertyModel<>(this, "settingMinimumActivePeriodValue"));
        this.settingMinimumActivePeriodVContainer.add(this.settingMinimumActivePeriodView);
    }

    protected void initSettingSharePerClientMaximumBlock() {
        this.settingSharePerClientMaximumBlock = new WebMarkupBlock("settingSharePerClientMaximumBlock", Size.Four_4);
        add(this.settingSharePerClientMaximumBlock);
        this.settingSharePerClientMaximumVContainer = new WebMarkupContainer("settingSharePerClientMaximumVContainer");
        this.settingSharePerClientMaximumBlock.add(this.settingSharePerClientMaximumVContainer);
        this.settingSharePerClientMaximumView = new ReadOnlyView("settingSharePerClientMaximumView", new PropertyModel<>(this, "settingSharePerClientMaximumValue"));
        this.settingSharePerClientMaximumVContainer.add(this.settingSharePerClientMaximumView);
    }

    protected void initSettingSharePerClientDefaultBlock() {
        this.settingSharePerClientDefaultBlock = new WebMarkupBlock("settingSharePerClientDefaultBlock", Size.Four_4);
        add(this.settingSharePerClientDefaultBlock);
        this.settingSharePerClientDefaultVContainer = new WebMarkupContainer("settingSharePerClientDefaultVContainer");
        this.settingSharePerClientDefaultBlock.add(this.settingSharePerClientDefaultVContainer);
        this.settingSharePerClientDefaultView = new ReadOnlyView("settingSharePerClientDefaultView", new PropertyModel<>(this, "settingSharePerClientDefaultValue"));
        this.settingSharePerClientDefaultVContainer.add(this.settingSharePerClientDefaultView);
    }

    protected void initSettingSharePerClientMinimumBlock() {
        this.settingSharePerClientMinimumBlock = new WebMarkupBlock("settingSharePerClientMinimumBlock", Size.Four_4);
        add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumVContainer = new WebMarkupContainer("settingSharePerClientMinimumVContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumVContainer);
        this.settingSharePerClientMinimumView = new ReadOnlyView("settingSharePerClientMinimumView", new PropertyModel<>(this, "settingSharePerClientMinimumValue"));
        this.settingSharePerClientMinimumVContainer.add(this.settingSharePerClientMinimumView);
    }

    protected void initSectionTerm() {

        initTermTotalNumberOfShareBlock();

        initTermShareToBeIssuedBlock();

        initTermNominalPriceBlock();

        initTermCapitalBlock();

    }

    protected void initTermCapitalBlock() {
        this.termCapitalBlock = new WebMarkupBlock("termCapitalBlock", Size.Six_6);
        add(this.termCapitalBlock);
        this.termCapitalVContainer = new WebMarkupContainer("termCapitalVContainer");
        this.termCapitalBlock.add(this.termCapitalVContainer);
        this.termCapitalView = new ReadOnlyView("termCapitalView", new PropertyModel<>(this, "termCapitalValue"));
        this.termCapitalVContainer.add(this.termCapitalView);
    }

    protected void initTermNominalPriceBlock() {
        this.termNominalPriceBlock = new WebMarkupBlock("termNominalPriceBlock", Size.Six_6);
        add(this.termNominalPriceBlock);
        this.termNominalPriceVContainer = new WebMarkupContainer("termNominalPriceVContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceVContainer);
        this.termNominalPriceView = new ReadOnlyView("termNominalPriceView", new PropertyModel<>(this, "termNominalPriceValue"));
        this.termNominalPriceVContainer.add(this.termNominalPriceView);
    }

    protected void initTermShareToBeIssuedBlock() {
        this.termShareToBeIssuedBlock = new WebMarkupBlock("termShareToBeIssuedBlock", Size.Six_6);
        add(this.termShareToBeIssuedBlock);
        this.termShareToBeIssuedVContainer = new WebMarkupContainer("termShareToBeIssuedVContainer");
        this.termShareToBeIssuedBlock.add(this.termShareToBeIssuedVContainer);
        this.termShareToBeIssuedView = new ReadOnlyView("termShareToBeIssuedView", new PropertyModel<>(this, "termShareToBeIssuedValue"));
        this.termShareToBeIssuedVContainer.add(this.termShareToBeIssuedView);
    }

    protected void initTermTotalNumberOfShareBlock() {
        this.termTotalNumberOfShareBlock = new WebMarkupBlock("termTotalNumberOfShareBlock", Size.Six_6);
        add(this.termTotalNumberOfShareBlock);
        this.termTotalNumberOfShareVContainer = new WebMarkupContainer("termTotalNumberOfShareVContainer");
        this.termTotalNumberOfShareBlock.add(this.termTotalNumberOfShareVContainer);
        this.termTotalNumberOfShareView = new ReadOnlyView("termTotalNumberOfShareView", new PropertyModel<>(this, "termTotalNumberOfShareValue"));
        this.termTotalNumberOfShareVContainer.add(this.termTotalNumberOfShareView);
    }

    protected void initSectionCurrency() {

        initCurrencyCodeBlock();

        initCurrencyDecimalPlaceBlock();

        initCurrencyMultipleOfBlock();
    }

    protected void initCurrencyMultipleOfBlock() {
        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfVContainer = new WebMarkupContainer("currencyMultipleOfVContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfVContainer);
        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);
    }

    protected void initCurrencyDecimalPlaceBlock() {
        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);
    }

    protected void initCurrencyCodeBlock() {
        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);
    }

    protected void initSectionDetail() {
        initDetailProductNameBlock();

        initDetailShortNameBlock();

        initDetailDescriptionBlock();
    }

    protected void initDetailDescriptionBlock() {
        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);
    }

    protected void initDetailShortNameBlock() {
        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);
    }

    protected void initDetailProductNameBlock() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);
    }

}
