package com.angkorteam.fintech.pages.product.fixed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccProductMapping;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MDepositProductInterestRateChart;
import com.angkorteam.fintech.ddl.MDepositProductTermAndPreClosure;
import com.angkorteam.fintech.ddl.MInterestIncentives;
import com.angkorteam.fintech.ddl.MInterestRateChart;
import com.angkorteam.fintech.ddl.MInterestRateSlab;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MSavingsProductCharge;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ApplyPenalOn;
import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.FinancialAccountType;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.OperandType;
import com.angkorteam.fintech.dto.enums.Operator;
import com.angkorteam.fintech.dto.enums.ProductType;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.IncentivePreviewPopup;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FixedDepositPreviewPage extends Page {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FixedDepositPreviewPage.class);

    private String fixedId;

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

    protected WebMarkupContainer currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected Long currencyDecimalPlaceValue;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfVContainer;
    protected Long currencyMultipleOfValue;
    protected ReadOnlyView currencyMultipleOfView;

    // Terms

    protected WebMarkupBlock termDefaultDepositAmountBlock;
    protected WebMarkupContainer termDefaultDepositAmountVContainer;
    protected Double termDefaultDepositAmountValue;
    protected ReadOnlyView termDefaultDepositAmountView;

    protected WebMarkupBlock termMinimumDepositAmountBlock;
    protected WebMarkupContainer termMinimumDepositAmountVContainer;
    protected Double termMinimumDepositAmountValue;
    protected ReadOnlyView termMinimumDepositAmountView;

    protected WebMarkupBlock termMaximumDepositAmountBlock;
    protected WebMarkupContainer termMaximumDepositAmountVContainer;
    protected Double termMaximumDepositAmountValue;
    protected ReadOnlyView termMaximumDepositAmountView;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodVContainer;
    protected Option termInterestCompoundingPeriodValue;
    protected ReadOnlyView termInterestCompoundingPeriodView;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodVContainer;
    protected Option termInterestPostingPeriodValue;
    protected ReadOnlyView termInterestPostingPeriodView;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingVContainer;
    protected Option termInterestCalculatedUsingValue;
    protected ReadOnlyView termInterestCalculatedUsingView;

    protected WebMarkupBlock termDayInYearBlock;
    protected WebMarkupContainer termDayInYearVContainer;
    protected Option termDayInYearValue;
    protected ReadOnlyView termDayInYearView;

    // Setting

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodVContainer;
    protected Long settingLockInPeriodValue;
    protected ReadOnlyView settingLockInPeriodView;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeVContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Option settingLockInTypeValue;
    protected ReadOnlyView settingLockInTypeView;

    protected WebMarkupBlock settingMinimumDepositTermBlock;
    protected WebMarkupContainer settingMinimumDepositTermVContainer;
    protected Long settingMinimumDepositTermValue;
    protected ReadOnlyView settingMinimumDepositTermView;

    protected WebMarkupBlock settingMinimumDepositTypeBlock;
    protected WebMarkupContainer settingMinimumDepositTypeVContainer;
    protected Option settingMinimumDepositTypeValue;
    protected ReadOnlyView settingMinimumDepositTypeView;

    protected WebMarkupBlock settingInMultiplesOfBlock;
    protected WebMarkupContainer settingInMultiplesOfVContainer;
    protected Long settingInMultiplesOfValue;
    protected ReadOnlyView settingInMultiplesOfView;

    protected WebMarkupBlock settingInMultiplesTypeBlock;
    protected WebMarkupContainer settingInMultiplesTypeVContainer;
    protected Option settingInMultiplesTypeValue;
    protected ReadOnlyView settingInMultiplesTypeView;

    protected WebMarkupBlock settingMaximumDepositTermBlock;
    protected WebMarkupContainer settingMaximumDepositTermVContainer;
    protected Long settingMaximumDepositTermValue;
    protected ReadOnlyView settingMaximumDepositTermView;

    protected WebMarkupBlock settingMaximumDepositTypeBlock;
    protected WebMarkupContainer settingMaximumDepositTypeVContainer;
    protected Option settingMaximumDepositTypeValue;
    protected ReadOnlyView settingMaximumDepositTypeView;

    protected WebMarkupBlock settingForPreMatureClosureBlock;
    protected WebMarkupContainer settingForPreMatureClosureVContainer;
    protected Boolean settingForPreMatureClosureValue;
    protected ReadOnlyView settingForPreMatureClosureView;

    protected WebMarkupBlock settingApplyPenalInterestBlock;
    protected WebMarkupContainer settingApplyPenalInterestVContainer;
    protected Double settingApplyPenalInterestValue;
    protected ReadOnlyView settingApplyPenalInterestView;

    protected WebMarkupBlock settingApplyPenalOnBlock;
    protected WebMarkupContainer settingApplyPenalOnVContainer;
    protected Option settingApplyPenalOnValue;
    protected ReadOnlyView settingApplyPenalOnView;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableVContainer;
    protected Boolean settingWithholdTaxApplicableValue;
    protected ReadOnlyView settingWithholdTaxApplicableView;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupVContainer;
    protected String settingTaxGroupValue;
    protected ReadOnlyView settingTaxGroupView;

    // Interest Rate Chart

    protected WebMarkupBlock interestRateValidFromDateBlock;
    protected WebMarkupContainer interestRateValidFromDateVContainer;
    protected Date interestRateValidFromDateValue;
    protected ReadOnlyView interestRateValidFromDateView;

    protected WebMarkupBlock interestRateValidEndDateBlock;
    protected WebMarkupContainer interestRateValidEndDateVContainer;
    protected Date interestRateValidEndDateValue;
    protected ReadOnlyView interestRateValidEndDateView;

    protected WebMarkupBlock interestRatePrimaryGroupingByAmountBlock;
    protected WebMarkupContainer interestRatePrimaryGroupingByAmountVContainer;
    protected Boolean interestRatePrimaryGroupingByAmountValue;
    protected ReadOnlyView interestRatePrimaryGroupingByAmountView;

    protected WebMarkupBlock interestRateChartBlock;
    protected WebMarkupContainer interestRateChartVContainer;
    protected List<Map<String, Object>> interestRateChartValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> interestRateChartTable;
    protected ListDataProvider interestRateChartProvider;
    protected List<IColumn<Map<String, Object>, String>> interestRateChartColumn;

    protected ModalWindow popup;

    // Charges

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;

    // Accounting

    protected String accountingValue = AccountingType.None.getDescription();
    protected Label accountingLabel;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashVContainer;

    protected WebMarkupBlock cashSavingReferenceBlock;
    protected WebMarkupContainer cashSavingReferenceVContainer;
    protected Option cashSavingReferenceValue;
    protected ReadOnlyView cashSavingReferenceView;

    protected WebMarkupBlock cashSavingControlBlock;
    protected WebMarkupContainer cashSavingControlVContainer;
    protected Option cashSavingControlValue;
    protected ReadOnlyView cashSavingControlView;

    protected WebMarkupBlock cashSavingTransferInSuspenseBlock;
    protected WebMarkupContainer cashSavingTransferInSuspenseVContainer;
    protected Option cashSavingTransferInSuspenseValue;
    protected ReadOnlyView cashSavingTransferInSuspenseView;

    protected WebMarkupBlock cashInterestOnSavingBlock;
    protected WebMarkupContainer cashInterestOnSavingVContainer;
    protected Option cashInterestOnSavingValue;
    protected ReadOnlyView cashInterestOnSavingView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected Option cashIncomeFromFeeValue;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
    protected Option cashIncomeFromPenaltyValue;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleVContainer;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFundSourceValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRuleFeeIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;

    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected List<Map<String, Object>> advancedAccountingRulePenaltyIncomeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newLinkedList();
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
            breadcrumb.setLabel("Fixed Deposit Product");
            breadcrumb.setPage(FixedDepositBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Fixed Deposit Product Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {

        this.editLink = new BookmarkablePageLink<>("editLink", FixedDepositBrowsePage.class);
        add(this.editLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedDepositBrowsePage.class);
        add(this.closeLink);

        initSectionDetail();

        initSectionCurrency();

        initSectionTerm();

        initSectionSetting();

        initSectionInterestRateChart();

        initSectionCharge();

        initSectionAccounting();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        this.cashVContainer.setVisible(AccountingType.Cash.getDescription().equals(this.accountingValue));
        this.advancedAccountingRuleVContainer.setVisible(AccountingType.Cash.getDescription().equals(this.accountingValue));
    }

    @Override
    protected void initData() {
        this.fixedId = getPageParameters().get("fixedId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        selectQuery.addJoin("INNER JOIN " + MOrganisationCurrency.NAME + " ON " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_CODE + " = " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE);
        selectQuery.addJoin("INNER JOIN " + MDepositProductTermAndPreClosure.NAME + " ON " + MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.SAVINGS_PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        selectQuery.addWhere(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID + " = '" + this.fixedId + "'");

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.SHORT_NAME);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.DESCRIPTION);

        selectQuery.addField("concat(" + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.NAME + ", ' [', " + MOrganisationCurrency.NAME + "." + MOrganisationCurrency.Field.CODE + ", ']') currency");
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_AMOUNT);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_AMOUNT);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.DEPOSIT_AMOUNT);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.IN_MULTIPLES_OF_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MIN_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.MAX_DEPOSIT_TERM_TYPE_ENUM);

        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_APPLICABLE);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST);
        selectQuery.addField(MDepositProductTermAndPreClosure.NAME + "." + MDepositProductTermAndPreClosure.Field.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.WITHHOLD_TAX);
        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.TAX_GROUP_ID);

        selectQuery.addField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.ACCOUNTING_TYPE);
        Map<String, Object> fixedObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.detailDescriptionValue = (String) fixedObject.get("description");
        this.detailProductNameValue = (String) fixedObject.get("name");
        this.detailShortNameValue = (String) fixedObject.get("short_name");

        this.currencyCodeValue = (String) fixedObject.get("currency");
        this.currencyMultipleOfValue = (Long) fixedObject.get("currency_multiplesof");
        this.currencyDecimalPlaceValue = (Long) fixedObject.get("currency_digits");

        this.termDefaultDepositAmountValue = (Double) fixedObject.get("deposit_amount");
        this.termMinimumDepositAmountValue = (Double) fixedObject.get("min_deposit_amount");
        this.termMaximumDepositAmountValue = (Double) fixedObject.get("max_deposit_amount");

        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(fixedObject.get("interest_compounding_period_enum")));
        this.termInterestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(fixedObject.get("interest_posting_period_enum")));
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(fixedObject.get("interest_calculation_type_enum")));
        this.termDayInYearValue = DayInYear.optionLiteral(String.valueOf(fixedObject.get("interest_calculation_days_in_year_type_enum")));

        this.settingInMultiplesOfValue = (Long) fixedObject.get("in_multiples_of_deposit_term");
        this.settingInMultiplesTypeValue = LockInType.optionLiteral(String.valueOf(fixedObject.get("in_multiples_of_deposit_term_type_enum")));

        this.settingMinimumDepositTermValue = (Long) fixedObject.get("min_deposit_term");
        this.settingMinimumDepositTypeValue = LockInType.optionLiteral(String.valueOf(fixedObject.get("min_deposit_term_type_enum")));

        this.settingMaximumDepositTermValue = (Long) fixedObject.get("max_deposit_term");
        this.settingMaximumDepositTypeValue = LockInType.optionLiteral(String.valueOf(fixedObject.get("max_deposit_term_type_enum")));
        Long pre_closure_penal_applicable = (Long) fixedObject.get("pre_closure_penal_applicable");
        this.settingForPreMatureClosureValue = pre_closure_penal_applicable != null && pre_closure_penal_applicable == 1;
        this.settingApplyPenalInterestValue = (Double) fixedObject.get("pre_closure_penal_interest");
        this.settingApplyPenalOnValue = ApplyPenalOn.optionLiteral(String.valueOf(fixedObject.get("pre_closure_penal_interest_on_enum")));

        Long withhold_tax = (Long) fixedObject.get("withhold_tax");
        this.settingWithholdTaxApplicableValue = withhold_tax != null && withhold_tax == 1;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addField(MTaxGroup.Field.NAME);
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, fixedObject.get("tax_group_id"));
        this.settingTaxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MSavingsProductCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField("concat(" + MCharge.NAME + "." + MCharge.Field.NAME + ", ' [', " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE + ", ']') AS " + MCharge.Field.NAME);
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
        selectQuery.addWhere(MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID + " = '" + this.fixedId + "'");
        List<Map<String, Object>> chargeObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

        for (Map<String, Object> chargeObject : chargeObjects) {
            Map<String, Object> charge = new HashMap<>();
            charge.put("name", chargeObject.get("name"));
            Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
            charge.put("type", type);
            Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
            charge.put("collect", collect);
            charge.put("amount", chargeObject.get("amount"));
            this.chargeValue.add(charge);
        }

        selectQuery = new SelectQuery(MDepositProductInterestRateChart.NAME);
        selectQuery.addJoin("INNER JOIN " + MInterestRateChart.NAME + " ON " + MDepositProductInterestRateChart.NAME + "." + MDepositProductInterestRateChart.Field.INTEREST_RATE_CHART_ID + " = " + MInterestRateChart.NAME + "." + MInterestRateChart.Field.ID);
        selectQuery.addOrderBy(MInterestRateChart.NAME + "." + MInterestRateChart.Field.FROM_DATE + " DESC");
        selectQuery.setLimit(0l, 1l);
        selectQuery.addWhere(MDepositProductInterestRateChart.NAME + "." + MDepositProductInterestRateChart.Field.DEPOSIT_PRODUCT_ID + " = '" + this.fixedId + "'");
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.ID);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.FROM_DATE);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.END_DATE);
        selectQuery.addField(MInterestRateChart.NAME + "." + MInterestRateChart.Field.IS_PRIMARY_GROUPING_BY_AMOUNT);
        Map<String, Object> interestChartObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        if (interestChartObject != null) {
            this.interestRateValidFromDateValue = (Date) interestChartObject.get("from_date");
            this.interestRateValidEndDateValue = (Date) interestChartObject.get("end_date");
            this.interestRatePrimaryGroupingByAmountValue = (Boolean) interestChartObject.get("is_primary_grouping_by_amount");

            selectQuery = new SelectQuery(MInterestRateSlab.NAME);
            selectQuery.addField(MInterestRateSlab.Field.ID);
            selectQuery.addField(MInterestRateSlab.Field.FROM_PERIOD);
            selectQuery.addField(MInterestRateSlab.Field.TO_PERIOD);
            selectQuery.addField(MInterestRateSlab.Field.PERIOD_TYPE_ENUM);
            selectQuery.addField(MInterestRateSlab.Field.AMOUNT_RANGE_FROM);
            selectQuery.addField(MInterestRateSlab.Field.AMOUNT_RANGE_TO);
            selectQuery.addField(MInterestRateSlab.Field.ANNUAL_INTEREST_RATE);
            selectQuery.addField(MInterestRateSlab.Field.DESCRIPTION);
            selectQuery.addWhere(MInterestRateSlab.Field.INTEREST_RATE_CHART_ID + " = '" + interestChartObject.get("id") + "'");
            List<Map<String, Object>> rateObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

            for (Map<String, Object> rateObject : rateObjects) {
                Map<String, Object> item = new HashMap<>();
                item.put("periodType", LockInType.optionLiteral(String.valueOf(rateObject.get("period_type_enum"))));
                item.put("periodFrom", rateObject.get("from_period"));
                item.put("periodTo", rateObject.get("to_period"));
                item.put("amountRangeFrom", rateObject.get("amount_range_from") != null ? ((Double) rateObject.get("amount_range_from")).longValue() : null);
                item.put("amountRangeTo", rateObject.get("amount_range_to") != null ? ((Double) rateObject.get("amount_range_to")).longValue() : null);
                item.put("interest", rateObject.get("annual_interest_rate"));
                item.put("description", rateObject.get("description"));

                selectQuery = new SelectQuery(MInterestIncentives.NAME);
                selectQuery.addField(MInterestIncentives.Field.ENTIRY_TYPE);
                selectQuery.addField(MInterestIncentives.Field.ATTRIBUTE_NAME);
                selectQuery.addField(MInterestIncentives.Field.CONDITION_TYPE);
                selectQuery.addField(MInterestIncentives.Field.ATTRIBUTE_VALUE);
                selectQuery.addField(MInterestIncentives.Field.INCENTIVE_TYPE);
                selectQuery.addField(MInterestIncentives.Field.AMOUNT);
                selectQuery.addWhere(MInterestIncentives.Field.INTEREST_RATE_SLAB_ID + " = :" + MInterestIncentives.Field.INTEREST_RATE_SLAB_ID, rateObject.get("id"));
                List<Map<String, Object>> incentiveObjects = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
                if (incentiveObjects != null && !incentiveObjects.isEmpty()) {
                    List<Map<String, Object>> interestRate = new ArrayList<>();
                    for (Map<String, Object> incentiveObject : incentiveObjects) {
                        Map<String, Object> incentive = new HashMap<>();
                        Option attribute = Attribute.optionLiteral(String.valueOf(incentiveObject.get("entiry_type")));
                        if (attribute != null) {
                            if (attribute.getId().equals(Attribute.ClientType.name())) {
                                selectQuery = new SelectQuery(MCodeValue.NAME);
                                selectQuery.addField(MCodeValue.Field.ID);
                                selectQuery.addField(MCodeValue.Field.CODE_VALUE);
                                selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, incentiveObject.get("attribute_value"));
                                Option clientTypeOperand = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                                incentive.put("clientTypeOperand", clientTypeOperand);
                            } else if (attribute.getId().equals(Attribute.ClientClassification.name())) {
                                selectQuery = new SelectQuery(MCodeValue.NAME);
                                selectQuery.addField(MCodeValue.Field.ID);
                                selectQuery.addField(MCodeValue.Field.CODE_VALUE);
                                selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, incentiveObject.get("attribute_value"));
                                Option clientClassificationOperand = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                                incentive.put("clientClassificationOperand", clientClassificationOperand);
                            } else {
                                incentive.put("numberOperand", String.valueOf(incentiveObject.get("attribute_value")));
                            }
                        }
                        incentive.put("attribute", attribute);
                        incentive.put("operator", Operator.optionLiteral(String.valueOf(incentiveObject.get("condition_type"))));
                        incentive.put("operandType", OperandType.optionLiteral(String.valueOf(incentiveObject.get("incentive_type"))));
                        incentive.put("interest", incentiveObject.get("amount"));
                        interestRate.add(incentive);
                    }
                    item.put("interestRate", interestRate);
                }
                this.interestRateChartValue.add(item);
            }
        }

        AccountingType accountingType = AccountingType.parseLiteral(String.valueOf(fixedObject.get("accounting_type")));

        if (accountingType != null) {
            this.accountingValue = accountingType.getDescription();

            selectQuery = new SelectQuery(AccProductMapping.NAME);
            selectQuery.addField(AccProductMapping.Field.PRODUCT_ID);
            selectQuery.addField(AccProductMapping.Field.PAYMENT_TYPE);
            selectQuery.addField(AccProductMapping.Field.PRODUCT_TYPE);
            selectQuery.addField(AccProductMapping.Field.CHARGE_ID);
            selectQuery.addField(AccProductMapping.Field.GL_ACCOUNT_ID);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_ID + " = :" + AccProductMapping.Field.PRODUCT_ID, this.fixedId);
            selectQuery.addWhere(AccProductMapping.Field.PRODUCT_TYPE + " = :" + AccProductMapping.Field.PRODUCT_TYPE, ProductType.Saving.getLiteral());
            List<Map<String, Object>> mappings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());

            for (Map<String, Object> mapping : mappings) {
                FinancialAccountType financialAccountType = FinancialAccountType.parseLiteral(String.valueOf(mapping.get("financial_account_type")));
                if (financialAccountType == FinancialAccountType.SavingReference1 && mapping.get("payment_type") != null && mapping.get("charge_id") == null && mapping.get("gl_account_id") != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MPaymentType.NAME);
                    selectQuery.addWhere(MPaymentType.Field.ID + " = :" + MPaymentType.Field.ID, mapping.get(AccProductMapping.Field.PAYMENT_TYPE));
                    selectQuery.addField(MPaymentType.Field.ID);
                    selectQuery.addField(MPaymentType.Field.VALUE + " as text");
                    item.put("payment", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFundSourceValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomeFee4 && mapping.get("payment_type") == null && mapping.get("charge_id") != null && mapping.get("gl_account_id") != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRuleFeeIncomeValue.add(item);
                }
                if (financialAccountType == FinancialAccountType.IncomePenalty5 && mapping.get("payment_type") == null && mapping.get("charge_id") != null && mapping.get("gl_account_id") != null) {
                    Map<String, Object> item = new HashMap<>();

                    selectQuery = new SelectQuery(MCharge.NAME);
                    selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, mapping.get(AccProductMapping.Field.CHARGE_ID));
                    selectQuery.addField(MCharge.Field.ID);
                    selectQuery.addField(MCharge.Field.NAME + " as text");
                    item.put("charge", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    selectQuery = new SelectQuery(AccGLAccount.NAME);
                    selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                    selectQuery.addField(AccGLAccount.Field.ID);
                    selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                    item.put("account", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));

                    this.advancedAccountingRulePenaltyIncomeValue.add(item);
                }
                if (financialAccountType != null && mapping.get("gl_account_id") != null && mapping.get("charge_id") == null && mapping.get("payment_type") == null) {
                    if (financialAccountType == FinancialAccountType.SavingReference1) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingReferenceValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.SavingControl2) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingControlValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.TransferInSuspense10) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashSavingTransferInSuspenseValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.InterestOnSaving3) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashInterestOnSavingValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.IncomeFee4) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashIncomeFromFeeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    } else if (financialAccountType == FinancialAccountType.IncomePenalty5) {
                        selectQuery = new SelectQuery(AccGLAccount.NAME);
                        selectQuery.addField(AccGLAccount.Field.ID);
                        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
                        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, mapping.get(AccProductMapping.Field.GL_ACCOUNT_ID));
                        this.cashIncomeFromPenaltyValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
                    }
                }
            }
        }

    }

    protected void initSectionAccounting() {
        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this, "accountingValue"));
        add(this.accountingLabel);

        initAccountingCash();

        initAdvancedAccountingRule();
    }

    protected void initAccountingCash() {
        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        add(this.cashBlock);
        this.cashVContainer = new WebMarkupContainer("cashVContainer");
        this.cashBlock.add(this.cashVContainer);

        this.cashSavingReferenceBlock = new WebMarkupBlock("cashSavingReferenceBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingReferenceBlock);
        this.cashSavingReferenceVContainer = new WebMarkupContainer("cashSavingReferenceVContainer");
        this.cashSavingReferenceBlock.add(this.cashSavingReferenceVContainer);
        this.cashSavingReferenceView = new ReadOnlyView("cashSavingReferenceView", new PropertyModel<>(this, "cashSavingReferenceValue"));
        this.cashSavingReferenceVContainer.add(this.cashSavingReferenceView);

        this.cashSavingControlBlock = new WebMarkupBlock("cashSavingControlBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingControlBlock);
        this.cashSavingControlVContainer = new WebMarkupContainer("cashSavingControlVContainer");
        this.cashSavingControlBlock.add(this.cashSavingControlVContainer);
        this.cashSavingControlView = new ReadOnlyView("cashSavingControlView", new PropertyModel<>(this, "cashSavingControlValue"));
        this.cashSavingControlVContainer.add(this.cashSavingControlView);

        this.cashSavingTransferInSuspenseBlock = new WebMarkupBlock("cashSavingTransferInSuspenseBlock", Size.Six_6);
        this.cashVContainer.add(this.cashSavingTransferInSuspenseBlock);
        this.cashSavingTransferInSuspenseVContainer = new WebMarkupContainer("cashSavingTransferInSuspenseVContainer");
        this.cashSavingTransferInSuspenseBlock.add(this.cashSavingTransferInSuspenseVContainer);
        this.cashSavingTransferInSuspenseView = new ReadOnlyView("cashSavingTransferInSuspenseView", new PropertyModel<>(this, "cashSavingTransferInSuspenseValue"));
        this.cashSavingTransferInSuspenseVContainer.add(this.cashSavingTransferInSuspenseView);

        this.cashInterestOnSavingBlock = new WebMarkupBlock("cashInterestOnSavingBlock", Size.Six_6);
        this.cashVContainer.add(this.cashInterestOnSavingBlock);
        this.cashInterestOnSavingVContainer = new WebMarkupContainer("cashInterestOnSavingVContainer");
        this.cashInterestOnSavingBlock.add(this.cashInterestOnSavingVContainer);
        this.cashInterestOnSavingView = new ReadOnlyView("cashInterestOnSavingView", new PropertyModel<>(this, "cashInterestOnSavingValue"));
        this.cashInterestOnSavingVContainer.add(this.cashInterestOnSavingView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashVContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<>(this, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);
    }

    protected void initAdvancedAccountingRule() {
        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleVContainer = new WebMarkupContainer("advancedAccountingRuleVContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleVContainer);

        this.advancedAccountingRuleFundSourceColumn = Lists.newLinkedList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFeeIncomeColumn = Lists.newLinkedList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newLinkedList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.advancedAccountingRuleVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void initSectionCharge() {
        this.chargeColumn = Lists.newLinkedList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        // this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date",
        // this::chargeColumn));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        add(this.chargeTable);
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

    protected void initSectionInterestRateChart() {
        initInterestRateValidFromDateBlock();

        initInterestRateValidEndDateBlock();

        initInterestRatePrimaryGroupingByAmountBlock();

        this.popup = new ModalWindow("popup");
        add(this.popup);
        this.popup.setOnClose(this::popupClose);

        initInterestRateChartBlock();
    }

    protected void initInterestRateChartBlock() {
        this.interestRateChartColumn = Lists.newLinkedList();
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period From"), "periodFrom", "periodFrom", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period To"), "periodTo", "periodTo", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount Range From"), "amountRangeFrom", "amountRangeFrom", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount Range To"), "amountRangeTo", "amountRangeTo", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartAction, this::interestRateChartClick));
        this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue);
        this.interestRateChartBlock = new WebMarkupBlock("interestRateChartBlock", Size.Twelve_12);
        add(this.interestRateChartBlock);
        this.interestRateChartVContainer = new WebMarkupContainer("interestRateChartVContainer");
        this.interestRateChartBlock.add(this.interestRateChartVContainer);
        this.interestRateChartTable = new DataTable<>("interestRateChartTable", interestRateChartColumn, this.interestRateChartProvider, 20);
        this.interestRateChartVContainer.add(this.interestRateChartTable);
        this.interestRateChartTable.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
        this.interestRateChartTable.addBottomToolbar(new NoRecordsToolbar(this.interestRateChartTable));
    }

    protected void initInterestRatePrimaryGroupingByAmountBlock() {
        this.interestRatePrimaryGroupingByAmountBlock = new WebMarkupBlock("interestRatePrimaryGroupingByAmountBlock", Size.Six_6);
        add(this.interestRatePrimaryGroupingByAmountBlock);
        this.interestRatePrimaryGroupingByAmountVContainer = new WebMarkupContainer("interestRatePrimaryGroupingByAmountVContainer");
        this.interestRatePrimaryGroupingByAmountBlock.add(this.interestRatePrimaryGroupingByAmountVContainer);
        this.interestRatePrimaryGroupingByAmountView = new ReadOnlyView("interestRatePrimaryGroupingByAmountView", new PropertyModel<>(this, "interestRatePrimaryGroupingByAmountValue"));
        this.interestRatePrimaryGroupingByAmountVContainer.add(this.interestRatePrimaryGroupingByAmountView);
    }

    protected void initInterestRateValidEndDateBlock() {
        this.interestRateValidEndDateBlock = new WebMarkupBlock("interestRateValidEndDateBlock", Size.Six_6);
        add(this.interestRateValidEndDateBlock);
        this.interestRateValidEndDateVContainer = new WebMarkupContainer("interestRateValidEndDateVContainer");
        this.interestRateValidEndDateBlock.add(this.interestRateValidEndDateVContainer);
        this.interestRateValidEndDateView = new ReadOnlyView("interestRateValidEndDateView", new PropertyModel<>(this, "interestRateValidEndDateValue"), "yyyy-MM-dd");
        this.interestRateValidEndDateVContainer.add(this.interestRateValidEndDateView);
    }

    protected void initInterestRateValidFromDateBlock() {
        this.interestRateValidFromDateBlock = new WebMarkupBlock("interestRateValidFromDateBlock", Size.Six_6);
        add(this.interestRateValidFromDateBlock);
        this.interestRateValidFromDateVContainer = new WebMarkupContainer("interestRateValidFromDateVContainer");
        this.interestRateValidFromDateBlock.add(this.interestRateValidFromDateVContainer);
        this.interestRateValidFromDateView = new ReadOnlyView("interestRateValidFromDateView", new PropertyModel<>(this, "interestRateValidFromDateValue"), "yyyy-MM-dd");
        this.interestRateValidFromDateVContainer.add(this.interestRateValidFromDateView);
    }

    protected void popupClose(String popupName, String signalId, AjaxRequestTarget target) {
    }

    protected ItemPanel interestRateChartColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("periodType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("periodFrom".equals(column) || "periodTo".equals(column) || "amountRangeFrom".equals(column) || "amountRangeTo".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void interestRateChartClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        if ("incentives".equals(link)) {
            List<Map<String, Object>> incentiveValue = (List<Map<String, Object>>) model.get("interestRate");
            this.popup.setContent(new IncentivePreviewPopup("incentive", incentiveValue));
            this.popup.show(target);
        }
    }

    protected List<ActionItem> interestRateChartAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newLinkedList();
        actions.add(new ActionItem("incentives", Model.of("Incentives"), ItemCss.PRIMARY));
        return actions;
    }

    protected void initSectionSetting() {
        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Three_3);
        add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodVContainer = new WebMarkupContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodVContainer);
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Three_3);
        add(this.settingLockInTypeBlock);
        this.settingLockInTypeVContainer = new WebMarkupContainer("settingLockInTypeVContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeVContainer);
        this.settingLockInTypeView = new ReadOnlyView("settingLockInTypeView", new PropertyModel<>(this, "settingLockInTypeValue"));
        this.settingLockInTypeVContainer.add(this.settingLockInTypeView);

        this.settingMinimumDepositTermBlock = new WebMarkupBlock("settingMinimumDepositTermBlock", Size.Three_3);
        add(this.settingMinimumDepositTermBlock);
        this.settingMinimumDepositTermVContainer = new WebMarkupContainer("settingMinimumDepositTermVContainer");
        this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermVContainer);
        this.settingMinimumDepositTermView = new ReadOnlyView("settingMinimumDepositTermView", new PropertyModel<>(this, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermVContainer.add(this.settingMinimumDepositTermView);

        this.settingMinimumDepositTypeBlock = new WebMarkupBlock("settingMinimumDepositTypeBlock", Size.Three_3);
        add(this.settingMinimumDepositTypeBlock);
        this.settingMinimumDepositTypeVContainer = new WebMarkupContainer("settingMinimumDepositTypeVContainer");
        this.settingMinimumDepositTypeBlock.add(this.settingMinimumDepositTypeVContainer);
        this.settingMinimumDepositTypeView = new ReadOnlyView("settingMinimumDepositTypeView", new PropertyModel<>(this, "settingMinimumDepositTypeValue"));
        this.settingMinimumDepositTypeVContainer.add(this.settingMinimumDepositTypeView);

        this.settingInMultiplesOfBlock = new WebMarkupBlock("settingInMultiplesOfBlock", Size.Three_3);
        add(this.settingInMultiplesOfBlock);
        this.settingInMultiplesOfVContainer = new WebMarkupContainer("settingInMultiplesOfVContainer");
        this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfVContainer);
        this.settingInMultiplesOfView = new ReadOnlyView("settingInMultiplesOfView", new PropertyModel<>(this, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfVContainer.add(this.settingInMultiplesOfView);

        this.settingInMultiplesTypeBlock = new WebMarkupBlock("settingInMultiplesTypeBlock", Size.Three_3);
        add(this.settingInMultiplesTypeBlock);
        this.settingInMultiplesTypeVContainer = new WebMarkupContainer("settingInMultiplesTypeVContainer");
        this.settingInMultiplesTypeBlock.add(this.settingInMultiplesTypeVContainer);
        this.settingInMultiplesTypeView = new ReadOnlyView("settingInMultiplesTypeView", new PropertyModel<>(this, "settingInMultiplesTypeValue"));
        this.settingInMultiplesTypeVContainer.add(this.settingInMultiplesTypeView);

        this.settingMaximumDepositTermBlock = new WebMarkupBlock("settingMaximumDepositTermBlock", Size.Three_3);
        add(this.settingMaximumDepositTermBlock);
        this.settingMaximumDepositTermVContainer = new WebMarkupContainer("settingMaximumDepositTermVContainer");
        this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermVContainer);
        this.settingMaximumDepositTermView = new ReadOnlyView("settingMaximumDepositTermView", new PropertyModel<>(this, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermVContainer.add(this.settingMaximumDepositTermView);

        this.settingMaximumDepositTypeBlock = new WebMarkupBlock("settingMaximumDepositTypeBlock", Size.Three_3);
        add(this.settingMaximumDepositTypeBlock);
        this.settingMaximumDepositTypeVContainer = new WebMarkupContainer("settingMaximumDepositTypeVContainer");
        this.settingMaximumDepositTypeBlock.add(this.settingMaximumDepositTypeVContainer);
        this.settingMaximumDepositTypeView = new ReadOnlyView("settingMaximumDepositTypeView", new PropertyModel<>(this, "settingMaximumDepositTypeValue"));
        this.settingMaximumDepositTypeVContainer.add(this.settingMaximumDepositTypeView);

        this.settingForPreMatureClosureBlock = new WebMarkupBlock("settingForPreMatureClosureBlock", Size.Three_3);
        add(this.settingForPreMatureClosureBlock);
        this.settingForPreMatureClosureVContainer = new WebMarkupContainer("settingForPreMatureClosureVContainer");
        this.settingForPreMatureClosureBlock.add(this.settingForPreMatureClosureVContainer);
        this.settingForPreMatureClosureView = new ReadOnlyView("settingForPreMatureClosureView", new PropertyModel<>(this, "settingForPreMatureClosureValue"));
        this.settingForPreMatureClosureVContainer.add(this.settingForPreMatureClosureView);

        this.settingApplyPenalInterestBlock = new WebMarkupBlock("settingApplyPenalInterestBlock", Size.Three_3);
        add(this.settingApplyPenalInterestBlock);
        this.settingApplyPenalInterestVContainer = new WebMarkupContainer("settingApplyPenalInterestVContainer");
        this.settingApplyPenalInterestBlock.add(this.settingApplyPenalInterestVContainer);
        this.settingApplyPenalInterestView = new ReadOnlyView("settingApplyPenalInterestView", new PropertyModel<>(this, "settingApplyPenalInterestValue"));
        this.settingApplyPenalInterestVContainer.add(this.settingApplyPenalInterestView);

        this.settingApplyPenalOnBlock = new WebMarkupBlock("settingApplyPenalOnBlock", Size.Three_3);
        add(this.settingApplyPenalOnBlock);
        this.settingApplyPenalOnVContainer = new WebMarkupContainer("settingApplyPenalOnVContainer");
        this.settingApplyPenalOnBlock.add(this.settingApplyPenalOnVContainer);
        this.settingApplyPenalOnView = new ReadOnlyView("settingApplyPenalOnView", new PropertyModel<>(this, "settingApplyPenalOnValue"));
        this.settingApplyPenalOnVContainer.add(this.settingApplyPenalOnView);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableVContainer = new WebMarkupContainer("settingWithholdTaxApplicableVContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableVContainer);
        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        add(this.settingTaxGroupBlock);
        this.settingTaxGroupVContainer = new WebMarkupContainer("settingTaxGroupVContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupVContainer);
        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this, "settingTaxGroupValue"));
        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);

    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.settingWithholdTaxApplicableValue != null && this.settingWithholdTaxApplicableValue;
        this.settingTaxGroupVContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected void initSectionTerm() {

        this.termDefaultDepositAmountBlock = new WebMarkupBlock("termDefaultDepositAmountBlock", Size.Six_6);
        add(this.termDefaultDepositAmountBlock);
        this.termDefaultDepositAmountVContainer = new WebMarkupContainer("termDefaultDepositAmountVContainer");
        this.termDefaultDepositAmountBlock.add(this.termDefaultDepositAmountVContainer);
        this.termDefaultDepositAmountView = new ReadOnlyView("termDefaultDepositAmountView", new PropertyModel<>(this, "termDefaultDepositAmountValue"));
        this.termDefaultDepositAmountVContainer.add(this.termDefaultDepositAmountView);

        this.termMinimumDepositAmountBlock = new WebMarkupBlock("termMinimumDepositAmountBlock", Size.Six_6);
        add(this.termMinimumDepositAmountBlock);
        this.termMinimumDepositAmountVContainer = new WebMarkupContainer("termMinimumDepositAmountVContainer");
        this.termMinimumDepositAmountBlock.add(this.termMinimumDepositAmountVContainer);
        this.termMinimumDepositAmountView = new ReadOnlyView("termMinimumDepositAmountView", new PropertyModel<>(this, "termMinimumDepositAmountValue"));
        this.termMinimumDepositAmountVContainer.add(this.termMinimumDepositAmountView);

        this.termMaximumDepositAmountBlock = new WebMarkupBlock("termMaximumDepositAmountBlock", Size.Six_6);
        add(this.termMaximumDepositAmountBlock);
        this.termMaximumDepositAmountVContainer = new WebMarkupContainer("termMaximumDepositAmountVContainer");
        this.termMaximumDepositAmountBlock.add(this.termMaximumDepositAmountVContainer);
        this.termMaximumDepositAmountView = new ReadOnlyView("termMaximumDepositAmountView", new PropertyModel<>(this, "termMaximumDepositAmountValue"));
        this.termMaximumDepositAmountVContainer.add(this.termMaximumDepositAmountView);

        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodVContainer = new WebMarkupContainer("termInterestCompoundingPeriodVContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodVContainer);
        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this, "termInterestCompoundingPeriodValue"));
        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);

        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Six_6);
        add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodVContainer = new WebMarkupContainer("termInterestPostingPeriodVContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodVContainer);
        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this, "termInterestPostingPeriodValue"));
        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);

        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingVContainer = new WebMarkupContainer("termInterestCalculatedUsingVContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingVContainer);
        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this, "termInterestCalculatedUsingValue"));
        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);

        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Six_6);
        add(this.termDayInYearBlock);
        this.termDayInYearVContainer = new WebMarkupContainer("termDayInYearVContainer");
        this.termDayInYearBlock.add(this.termDayInYearVContainer);
        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this, "termDayInYearValue"));
        this.termDayInYearVContainer.add(this.termDayInYearView);

    }

    protected void initSectionCurrency() {

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);

        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfVContainer = new WebMarkupContainer("currencyMultipleOfVContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfVContainer);
        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this, "currencyMultipleOfValue"));
        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);
    }

    protected void initSectionDetail() {
        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);
    }

}
