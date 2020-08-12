//package com.angkorteam.fintech.pages.product.share;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.ddl.AccProductMapping;
//import com.angkorteam.fintech.ddl.MCharge;
//import com.angkorteam.fintech.ddl.MOrganisationCurrency;
//import com.angkorteam.fintech.ddl.MSavingsProduct;
//import com.angkorteam.fintech.ddl.MShareProduct;
//import com.angkorteam.fintech.ddl.MShareProductCharge;
//import com.angkorteam.fintech.ddl.MShareProductMarketPrice;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.AccountingType;
//import com.angkorteam.fintech.dto.enums.ChargeCalculation;
//import com.angkorteam.fintech.dto.enums.ChargeTime;
//import com.angkorteam.fintech.dto.enums.FinancialAccountType;
//import com.angkorteam.fintech.dto.enums.LockInType;
//import com.angkorteam.fintech.dto.enums.ProductType;
//import com.angkorteam.fintech.pages.ProductDashboardPage;
//import com.angkorteam.fintech.widget.product.share.Accounting;
//import com.angkorteam.fintech.widget.product.share.Charges;
//import com.angkorteam.fintech.widget.product.share.Currency;
//import com.angkorteam.fintech.widget.product.share.Details;
//import com.angkorteam.fintech.widget.product.share.MarketPrice;
//import com.angkorteam.fintech.widget.product.share.Preview;
//import com.angkorteam.fintech.widget.product.share.Settings;
//import com.angkorteam.fintech.widget.product.share.Terms;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class SharePreviewPage extends Page {
//
//    protected String shareId;
//
//    // Detail
//
//    protected String detailProductNameValue;
//    protected String detailShortNameValue;
//    protected String detailDescriptionValue;
//
//    // Currency
//
//    protected Option currencyCodeValue;
//    protected Long currencyDecimalPlaceValue;
//    protected Long currencyMultipleOfValue;
//
//    // Term
//
//    protected Long termTotalNumberOfShareValue;
//    protected Long termShareToBeIssuedValue;
//    protected Double termNominalPriceValue;
//    protected Double termCapitalValue;
//
//    // Setting
//
//    protected Long settingSharePerClientMinimumValue;
//    protected Long settingSharePerClientDefaultValue;
//    protected Long settingSharePerClientMaximumValue;
//    protected Long settingMinimumActivePeriodValue;
//    protected Option settingMinimumActiveTypeValue;
//    protected Long settingLockInPeriodValue;
//    protected Option settingLockInTypeValue;
//    protected Boolean settingAllowDividendForInactiveClientValue;
//
//    // Market Price
//
//    protected List<Map<String, Object>> marketPriceValue;
//
//    // Charges
//
//    protected List<Map<String, Object>> chargeValue;
//
//    // Accounting
//
//    protected String accountingValue;
//
//    protected Option cashShareReferenceValue;
//    protected Option cashShareSuspenseControlValue;
//    protected Option cashEquityValue;
//    protected Option cashIncomeFromFeeValue;
//
//    protected AjaxTabbedPanel<ITab> tab;
//
//    protected boolean errorDetail;
//    protected boolean errorCurrency;
//    protected boolean errorAccounting;
//    protected boolean errorTerm;
//    protected boolean errorSetting;
//    protected boolean errorCharge;
//    protected boolean errorMarketPrice;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Product");
//            breadcrumb.setPage(ProductDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Share Loan Product");
//            breadcrumb.setPage(ShareBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel(this.detailShortNameValue);
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new MarketPrice(this), new Charges(this), new Accounting(this), new Preview(this)));
//        add(this.tab);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.tab.setSelectedTab(ShareCreatePage.TAB_PREVIEW);
//    }
//
//    @Override
//    protected void initData() {
//        this.marketPriceValue = new ArrayList<>();
//        this.chargeValue = new ArrayList<>();
//
//        this.errorDetail = true;
//        this.errorCurrency = true;
//        this.errorAccounting = true;
//        this.errorTerm = true;
//        this.errorSetting = true;
//        this.errorCharge = true;
//        this.errorMarketPrice = true;
//
//        this.shareId = getPageParameters().get("shareId").toString();
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MShareProduct.NAME);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.NAME);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.SHORT_NAME);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.DESCRIPTION);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_CODE);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_DIGITS);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CURRENCY_MULTIPLES_OF);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.TOTAL_SHARES);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ISSUED_SHARES);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.UNIT_PRICE);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.CAPITAL_AMOUNT);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_CLIENT_SHARES);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.NOMINAL_CLIENT_SHARES);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MAXIMUM_CLIENT_SHARES);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ALLOW_DIVIDENDS_INACTIVE_CLIENTS);
//        selectQuery.addField(MShareProduct.NAME + "." + MShareProduct.Field.ACCOUNTING_TYPE);
//        selectQuery.addWhere(MShareProduct.NAME + "." + MShareProduct.Field.ID + " = '" + this.shareId + "'");
//        Map<String, Object> shareObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        this.detailProductNameValue = (String) shareObject.get(MShareProduct.Field.NAME);
//        this.detailDescriptionValue = (String) shareObject.get(MShareProduct.Field.DESCRIPTION);
//        this.detailShortNameValue = (String) shareObject.get(MShareProduct.Field.SHORT_NAME);
//
//        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
//        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " = :" + MOrganisationCurrency.Field.CODE, shareObject.get(MSavingsProduct.Field.CURRENCY_CODE));
//        selectQuery.addField(MOrganisationCurrency.Field.NAME + " as text");
//        selectQuery.addField(MOrganisationCurrency.Field.CODE + " as id");
//        this.currencyCodeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//        this.currencyDecimalPlaceValue = (Long) shareObject.get(MShareProduct.Field.CURRENCY_DIGITS);
//        this.currencyMultipleOfValue = (Long) shareObject.get(MShareProduct.Field.CURRENCY_MULTIPLES_OF);
//
//        this.termTotalNumberOfShareValue = (Long) shareObject.get(MShareProduct.Field.TOTAL_SHARES);
//        this.termShareToBeIssuedValue = (Long) shareObject.get(MShareProduct.Field.ISSUED_SHARES);
//        this.termNominalPriceValue = (Double) shareObject.get(MShareProduct.Field.UNIT_PRICE);
//        this.termCapitalValue = (Double) shareObject.get(MShareProduct.Field.CAPITAL_AMOUNT);
//
//        this.settingSharePerClientMinimumValue = (Long) shareObject.get(MShareProduct.Field.MINIMUM_CLIENT_SHARES);
//        this.settingSharePerClientDefaultValue = (Long) shareObject.get(MShareProduct.Field.NOMINAL_CLIENT_SHARES);
//        this.settingSharePerClientMaximumValue = (Long) shareObject.get(MShareProduct.Field.MAXIMUM_CLIENT_SHARES);
//
//        Double lockin_period_frequency = (Double) shareObject.get(MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY);
//        this.settingLockInPeriodValue = lockin_period_frequency == null ? null : lockin_period_frequency.longValue();
//        this.settingLockInTypeValue = LockInType.optionLiteral(String.valueOf(shareObject.get(MShareProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM)));
//
//        Double minimum_active_period_frequency = (Double) shareObject.get(MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY);
//        this.settingMinimumActivePeriodValue = minimum_active_period_frequency == null ? null : minimum_active_period_frequency.longValue();
//        this.settingMinimumActiveTypeValue = LockInType.optionLiteral(String.valueOf(shareObject.get(MShareProduct.Field.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM)));
//
//        Long allow_dividends_inactive_clients = (Long) shareObject.get(MShareProduct.Field.ALLOW_DIVIDENDS_INACTIVE_CLIENTS);
//        this.settingAllowDividendForInactiveClientValue = allow_dividends_inactive_clients != null && allow_dividends_inactive_clients == 1;
//
//        selectQuery = new SelectQuery(MCharge.NAME);
//        selectQuery.addJoin("INNER JOIN " + MShareProductCharge.NAME + " ON " + MShareProductCharge.NAME + "." + MShareProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
//        selectQuery.addField("concat(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') as " + MCharge.Field.NAME);
//        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
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
//        selectQuery.addWhere(MShareProductCharge.NAME + "." + MShareProductCharge.Field.PRODUCT_ID + " = '" + this.shareId + "'");
//        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//
//        for (Map<String, Object> chargeObject : chargeObjects) {
//            Map<String, Object> charge = new HashMap<>();
//            charge.put("name", chargeObject.get(MCharge.Field.NAME));
//            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM)));
//            charge.put("type", type);
//            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM)));
//            charge.put("collect", collect);
//            charge.put("amount", chargeObject.get(MCharge.Field.AMOUNT));
//            this.chargeValue.add(charge);
//        }
//
//        selectQuery = new SelectQuery(MShareProductMarketPrice.NAME);
//        selectQuery.addWhere(MShareProductMarketPrice.Field.PRODUCT_ID + " = :" + MShareProductMarketPrice.Field.PRODUCT_ID, this.shareId);
//        selectQuery.addField(MShareProductMarketPrice.Field.SHARE_VALUE);
//        selectQuery.addField(MShareProductMarketPrice.Field.FROM_DATE);
//        List<Map<String, Object>> shareMarketPrices = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//        for (Map<String, Object> shareMarketPrice : shareMarketPrices) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("unitPrice", shareMarketPrice.get(MShareProductMarketPrice.Field.SHARE_VALUE));
//            item.put("fromDate", shareMarketPrice.get(MShareProductMarketPrice.Field.FROM_DATE));
//            this.marketPriceValue.add(item);
//        }
//
//        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(shareObject.get(MShareProduct.Field.ACCOUNTING_TYPE)));
//
//        if (accountingType != null) {
//            this.accountingValue = accountingType.getDescription();
//            selectQuery = new SelectQuery(AccProductMapping.NAME);
//            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.shareId);
//            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Share.getLiteral());
//            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
//            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
//            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
//            selectQuery.addField(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE);
//            List<Map<String, Object>> mappings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//            for (Map<String, Object> mapping : mappings) {
//                if (mapping.get(AccProductMapping.Field.CHARGE_ID) == null && mapping.get(AccProductMapping.Field.PAYMENT_TYPE) == null && mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID) != null && mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE) != null) {
//                    FinancialAccountType financialAccountType = FinancialAccountType.parseLiteral(String.valueOf(mapping.get(AccProductMapping.Field.FINANCIAL_ACCOUNT_TYPE)));
//                    if (financialAccountType == FinancialAccountType.SavingReference1) {
//                        selectQuery = new SelectQuery(AccGLAccount.NAME);
//                        selectQuery.addField(AccGLAccount.Field.ID);
//                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
//                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
//                        this.cashShareReferenceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//                    } else if (financialAccountType == FinancialAccountType.SavingControl2) {
//                        selectQuery = new SelectQuery(AccGLAccount.NAME);
//                        selectQuery.addField(AccGLAccount.Field.ID);
//                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
//                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
//                        this.cashShareSuspenseControlValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//                    } else if (financialAccountType == FinancialAccountType.IncomeFee4) {
//                        selectQuery = new SelectQuery(AccGLAccount.NAME);
//                        selectQuery.addField(AccGLAccount.Field.ID);
//                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
//                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
//                        this.cashEquityValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
//                        selectQuery = new SelectQuery(AccGLAccount.NAME);
//                        selectQuery.addField(AccGLAccount.Field.ID);
//                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
//                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
//                        this.cashIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//                    }
//                }
//            }
//        }
//    }
//
//}
