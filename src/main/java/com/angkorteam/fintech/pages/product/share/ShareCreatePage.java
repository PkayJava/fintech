package com.angkorteam.fintech.pages.product.share;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ProductShareBuilder;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.MinimumActivePeriod;
import com.angkorteam.fintech.helper.ShareHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.product.share.Accounting;
import com.angkorteam.fintech.widget.product.share.Charges;
import com.angkorteam.fintech.widget.product.share.Currency;
import com.angkorteam.fintech.widget.product.share.Details;
import com.angkorteam.fintech.widget.product.share.MarketPrice;
import com.angkorteam.fintech.widget.product.share.Preview;
import com.angkorteam.fintech.widget.product.share.Settings;
import com.angkorteam.fintech.widget.product.share.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ShareCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_CURRENCY = 1;
    public static int TAB_TERM = 2;
    public static int TAB_SETTING = 3;
    public static int TAB_MARKET_PRICE = 4;
    public static int TAB_CHARGE = 5;
    public static int TAB_ACCOUNTING = 6;
    public static int TAB_PREVIEW = 7;

    // Detail

    protected String detailProductNameValue;
    protected String detailShortNameValue;
    protected String detailDescriptionValue;

    // Currency

    protected Option currencyCodeValue;
    protected Long currencyDecimalPlaceValue;
    protected Long currencyMultipleOfValue;

    // Term

    protected Long termTotalNumberOfShareValue;
    protected Long termShareToBeIssuedValue;
    protected Double termNominalPriceValue;
    protected Double termCapitalValue;

    // Setting

    protected Long settingSharePerClientMinimumValue;
    protected Long settingSharePerClientDefaultValue;
    protected Long settingSharePerClientMaximumValue;
    protected Long settingMinimumActivePeriodValue;
    protected Option settingMinimumActiveTypeValue;
    protected Long settingLockInPeriodValue;
    protected Option settingLockInTypeValue;
    protected Boolean settingAllowDividendForInactiveClientValue;

    // Market Price

    protected List<Map<String, Object>> marketPriceValue;

    // Charges

    protected List<Map<String, Object>> chargeValue;

    // Accounting

    protected String accountingValue;

    protected Option cashShareReferenceValue;
    protected Option cashShareSuspenseControlValue;
    protected Option cashEquityValue;
    protected Option cashIncomeFromFeeValue;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail;
    protected boolean errorCurrency;
    protected boolean errorAccounting;
    protected boolean errorTerm;
    protected boolean errorSetting;
    protected boolean errorCharge;
    protected boolean errorMarketPrice;

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
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Currency(this), new Terms(this), new Settings(this), new MarketPrice(this), new Charges(this), new Accounting(this), new Preview(this)));
        add(this.tab);
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.marketPriceValue = new ArrayList<>();
        this.chargeValue = new ArrayList<>();

        this.errorDetail = true;
        this.errorCurrency = true;
        this.errorAccounting = true;
        this.errorTerm = true;
        this.errorSetting = true;
        this.errorCharge = true;
        this.errorMarketPrice = true;

        this.accountingValue = AccountingType.None.getDescription();

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.detailShortNameValue = generator.generate(4);
        this.currencyDecimalPlaceValue = 2l;
        this.currencyMultipleOfValue = 1l;
        this.termTotalNumberOfShareValue = 10l;
        this.termNominalPriceValue = 10d;
        this.settingSharePerClientDefaultValue = 10l;
        this.settingAllowDividendForInactiveClientValue = true;
        this.termShareToBeIssuedValue = 10l;
        this.termCapitalValue = this.termNominalPriceValue * this.termShareToBeIssuedValue;
        this.accountingValue = AccountingType.None.getDescription();
    }

    public void saveButtonSubmit(Button button) {
        ProductShareBuilder builder = new ProductShareBuilder();

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
                Option charge = (Option) item.get("charge");
                builder.withCharges(charge.getId());
            }
        }

        // Accounting

        String accounting = this.accountingValue;

        if (AccountingType.None.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.None);
        } else if (AccountingType.Cash.getDescription().equals(accounting)) {
            builder.withAccountingRule(AccountingType.Cash);
        }

        if (AccountingType.Cash.getDescription().equals(accounting)) {
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

        JsonNode node = ShareHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ShareBrowsePage.class);
    }

}
