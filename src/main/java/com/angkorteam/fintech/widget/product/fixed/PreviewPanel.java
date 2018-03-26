package com.angkorteam.fintech.widget.product.fixed;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.pages.product.fixed.FixedDepositBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedDepositCreatePage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

public class PreviewPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;

    protected PropertyModel<Boolean> errorSetting;
    protected PropertyModel<Boolean> errorAccounting;
    protected PropertyModel<Boolean> errorCharge;
    protected PropertyModel<Boolean> errorCurrency;
    protected PropertyModel<Boolean> errorDetail;
    protected PropertyModel<Boolean> errorTerm;
    protected PropertyModel<Boolean> errorInterestRateChart;

    protected Form<Void> form;
    protected Button saveButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    // Details

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected ReadOnlyView detailDescriptionView;

    // Items

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfVContainer;
    protected ReadOnlyView currencyMultipleOfView;

    protected WebMarkupBlock termDefaultDepositAmountBlock;
    protected WebMarkupContainer termDefaultDepositAmountVContainer;
    protected ReadOnlyView termDefaultDepositAmountView;
    protected TextFeedbackPanel termDefaultDepositAmountFeedback;

    protected WebMarkupBlock termMinimumDepositAmountBlock;
    protected WebMarkupContainer termMinimumDepositAmountVContainer;
    protected ReadOnlyView termMinimumDepositAmountView;
    protected TextFeedbackPanel termMinimumDepositAmountFeedback;

    protected WebMarkupBlock termMaximumDepositAmountBlock;
    protected WebMarkupContainer termMaximumDepositAmountVContainer;
    protected ReadOnlyView termMaximumDepositAmountView;
    protected TextFeedbackPanel termMaximumDepositAmountFeedback;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodVContainer;
    protected ReadOnlyView termInterestCompoundingPeriodView;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodVContainer;
    protected ReadOnlyView termInterestPostingPeriodView;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingVContainer;
    protected ReadOnlyView termInterestCalculatedUsingView;

    protected WebMarkupBlock termDayInYearBlock;
    protected WebMarkupContainer termDayInYearVContainer;
    protected ReadOnlyView termDayInYearView;

    // Settings

    //
    // Lock-in Period Minimum Deposit Term Maximum Deposit Term Deposit Term In
    // multiple of
    // 1 Days 1 Weeks 1 Days 1 Days
    //
    //
    // Apply penal interest (less) Withhold tax Applicable Withhold Tax Group
    // 1 % On Whole Term Yes ABC

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodVContainer;
    protected ReadOnlyView settingLockInPeriodView;
    protected String settingLockInPeriodValue;

    protected WebMarkupBlock settingMinimumDepositTermBlock;
    protected WebMarkupContainer settingMinimumDepositTermVContainer;
    protected ReadOnlyView settingMinimumDepositTermView;
    protected String settingMinimumDepositTermValue;

    protected WebMarkupBlock settingInMultiplesOfBlock;
    protected WebMarkupContainer settingInMultiplesOfVContainer;
    protected ReadOnlyView settingInMultiplesOfView;
    protected String settingInMultiplesOfValue;

    protected WebMarkupBlock settingMaximumDepositTermBlock;
    protected WebMarkupContainer settingMaximumDepositTermVContainer;
    protected ReadOnlyView settingMaximumDepositTermView;
    protected String settingMaximumDepositTermValue;

    protected WebMarkupBlock settingApplyPenalBlock;
    protected WebMarkupContainer settingApplyPenalVContainer;
    protected ReadOnlyView settingApplyPenalView;
    protected String settingApplyPenalValue;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableVContainer;
    protected ReadOnlyView settingWithholdTaxApplicableView;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupVContainer;
    protected ReadOnlyView settingTaxGroupView;

    // Interest Rate Chart

    // Charges

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected ListDataProvider chargeProvider;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;

    // Accounting

    protected Label accountingLabel;

    protected WebMarkupContainer cashMaster;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleMaster;

    protected WebMarkupContainer advancedAccountingRuleFundSourceBlock;
    protected WebMarkupContainer advancedAccountingRuleFundSourceVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;

    protected WebMarkupContainer advancedAccountingRuleFeeIncomeBlock;
    protected WebMarkupContainer advancedAccountingRuleFeeIncomeVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;

    protected WebMarkupContainer advancedAccountingRulePenaltyIncomeBlock;
    protected WebMarkupContainer advancedAccountingRulePenaltyIncomeVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;

    public PreviewPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
        this.errorInterestRateChart = new PropertyModel<>(this.itemPage, "errorInterestRateChart");

        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));

        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());
        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));

        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());
        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));

        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());
        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));

    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedDepositBrowsePage.class);
        this.form.add(this.closeLink);

        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameVContainer = new WebMarkupContainer("detailProductNameVContainer");
        this.detailProductNameBlock.add(this.detailProductNameVContainer);
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameVContainer = new WebMarkupContainer("detailShortNameVContainer");
        this.detailShortNameBlock.add(this.detailShortNameVContainer);
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Twelve_12);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionVContainer = new WebMarkupContainer("detailDescriptionVContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionVContainer);
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeVContainer = new WebMarkupContainer("currencyCodeVContainer");
        this.currencyCodeBlock.add(this.currencyCodeVContainer);
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this.itemPage, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Three_3);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceVContainer = new WebMarkupContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceVContainer);
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);

        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Three_3);
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfVContainer = new WebMarkupContainer("currencyMultipleOfVContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfVContainer);
        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this.itemPage, "currencyMultipleOfValue"));
        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);

        this.termDefaultDepositAmountBlock = new WebMarkupBlock("termDefaultDepositAmountBlock", Size.Four_4);
        this.form.add(this.termDefaultDepositAmountBlock);
        this.termDefaultDepositAmountVContainer = new WebMarkupContainer("termDefaultDepositAmountVContainer");
        this.termDefaultDepositAmountBlock.add(this.termDefaultDepositAmountVContainer);
        this.termDefaultDepositAmountView = new ReadOnlyView("termDefaultDepositAmountView", new PropertyModel<>(this.itemPage, "termDefaultDepositAmountValue"));
        this.termDefaultDepositAmountVContainer.add(this.termDefaultDepositAmountView);

        this.termMinimumDepositAmountBlock = new WebMarkupBlock("termMinimumDepositAmountBlock", Size.Four_4);
        this.form.add(this.termMinimumDepositAmountBlock);
        this.termMinimumDepositAmountVContainer = new WebMarkupContainer("termMinimumDepositAmountVContainer");
        this.termMinimumDepositAmountBlock.add(this.termMinimumDepositAmountVContainer);
        this.termMinimumDepositAmountView = new ReadOnlyView("termMinimumDepositAmountView", new PropertyModel<>(this.itemPage, "termMinimumDepositAmountValue"));
        this.termMinimumDepositAmountVContainer.add(this.termMinimumDepositAmountView);

        this.termMaximumDepositAmountBlock = new WebMarkupBlock("termMaximumDepositAmountBlock", Size.Four_4);
        this.form.add(this.termMaximumDepositAmountBlock);
        this.termMaximumDepositAmountVContainer = new WebMarkupContainer("termMaximumDepositAmountVContainer");
        this.termMaximumDepositAmountBlock.add(this.termMaximumDepositAmountVContainer);
        this.termMaximumDepositAmountView = new ReadOnlyView("termMaximumDepositAmountView", new PropertyModel<>(this.itemPage, "termMaximumDepositAmountValue"));
        this.termMaximumDepositAmountVContainer.add(this.termMaximumDepositAmountView);

        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Three_3);
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodVContainer = new WebMarkupContainer("termInterestCompoundingPeriodVContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodVContainer);
        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"));
        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);

        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Three_3);
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodVContainer = new WebMarkupContainer("termInterestPostingPeriodVContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodVContainer);
        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"));
        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);

        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Three_3);
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingVContainer = new WebMarkupContainer("termInterestCalculatedUsingVContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingVContainer);
        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"));
        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);

        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Three_3);
        this.form.add(this.termDayInYearBlock);
        this.termDayInYearVContainer = new WebMarkupContainer("termDayInYearVContainer");
        this.termDayInYearBlock.add(this.termDayInYearVContainer);
        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this.itemPage, "termDayInYearValue"));
        this.termDayInYearVContainer.add(this.termDayInYearView);

        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Three_3);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodVContainer = new WebMarkupContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodVContainer);
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);

        this.settingMinimumDepositTermBlock = new WebMarkupBlock("settingMinimumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMinimumDepositTermBlock);
        this.settingMinimumDepositTermVContainer = new WebMarkupContainer("settingMinimumDepositTermVContainer");
        this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermVContainer);
        this.settingMinimumDepositTermView = new ReadOnlyView("settingMinimumDepositTermView", new PropertyModel<>(this, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermVContainer.add(this.settingMinimumDepositTermView);

        this.settingInMultiplesOfBlock = new WebMarkupBlock("settingInMultiplesOfBlock", Size.Three_3);
        this.form.add(this.settingInMultiplesOfBlock);
        this.settingInMultiplesOfVContainer = new WebMarkupContainer("settingInMultiplesOfVContainer");
        this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfVContainer);
        this.settingInMultiplesOfView = new ReadOnlyView("settingInMultiplesOfView", new PropertyModel<>(this, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfVContainer.add(this.settingInMultiplesOfView);

        this.settingMaximumDepositTermBlock = new WebMarkupBlock("settingMaximumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMaximumDepositTermBlock);
        this.settingMaximumDepositTermVContainer = new WebMarkupContainer("settingMaximumDepositTermVContainer");
        this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermVContainer);
        this.settingMaximumDepositTermView = new ReadOnlyView("settingMaximumDepositTermView", new PropertyModel<>(this, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermVContainer.add(this.settingMaximumDepositTermView);

        this.settingApplyPenalBlock = new WebMarkupBlock("settingApplyPenalBlock", Size.Six_6);
        this.form.add(this.settingApplyPenalBlock);
        this.settingApplyPenalVContainer = new WebMarkupContainer("settingApplyPenalVContainer");
        this.settingApplyPenalBlock.add(this.settingApplyPenalVContainer);
        this.settingApplyPenalView = new ReadOnlyView("settingApplyPenalView", new PropertyModel<>(this, "settingApplyPenalValue"));
        this.settingApplyPenalVContainer.add(this.settingApplyPenalView);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Three_3);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableVContainer = new WebMarkupContainer("settingWithholdTaxApplicableVContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableVContainer);
        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Three_3);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupVContainer = new WebMarkupContainer("settingTaxGroupVContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupVContainer);
        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"));
        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);

        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        this.form.add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, this.chargeValue.getObject().size() + 1);
        this.chargeVContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.form.add(this.accountingLabel);

        this.cashMaster = new WebMarkupContainer("cashMaster");
        this.form.add(this.cashMaster);

        this.advancedAccountingRuleMaster = new WebMarkupContainer("advancedAccountingRuleMaster");
        this.form.add(this.advancedAccountingRuleMaster);

        this.advancedAccountingRuleFundSourceBlock = new WebMarkupContainer("advancedAccountingRuleFundSourceBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRuleFundSourceBlock);
        this.advancedAccountingRuleFundSourceVContainer = new WebMarkupContainer("advancedAccountingRuleFundSourceVContainer");
        this.advancedAccountingRuleFundSourceBlock.add(this.advancedAccountingRuleFundSourceVContainer);
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, this.advancedAccountingRuleFundSourceValue.getObject().size() + 1);
        this.advancedAccountingRuleFundSourceVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFeeIncomeBlock = new WebMarkupContainer("advancedAccountingRuleFeeIncomeBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRuleFeeIncomeBlock);
        this.advancedAccountingRuleFeeIncomeVContainer = new WebMarkupContainer("advancedAccountingRuleFeeIncomeVContainer");
        this.advancedAccountingRuleFeeIncomeBlock.add(this.advancedAccountingRuleFeeIncomeVContainer);
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, this.advancedAccountingRuleFeeIncomeValue.getObject().size() + 1);
        this.advancedAccountingRuleFeeIncomeVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRulePenaltyIncomeBlock = new WebMarkupContainer("advancedAccountingRulePenaltyIncomeBlock");
        this.advancedAccountingRuleMaster.add(this.advancedAccountingRulePenaltyIncomeBlock);
        this.advancedAccountingRulePenaltyIncomeVContainer = new WebMarkupContainer("advancedAccountingRulePenaltyIncomeVContainer");
        this.advancedAccountingRulePenaltyIncomeBlock.add(this.advancedAccountingRulePenaltyIncomeVContainer);
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, this.advancedAccountingRulePenaltyIncomeValue.getObject().size() + 1);
        this.advancedAccountingRulePenaltyIncomeVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

    }

    @Override
    protected void configureMetaData() {
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
            this.cashMaster.setVisible(false);
            this.advancedAccountingRuleMaster.setVisible(false);
        }

        PropertyModel<Long> settingLockInPeriodValue = new PropertyModel<>(this.itemPage, "settingLockInPeriodValue");
        PropertyModel<Option> settingLockInTypeValue = new PropertyModel<>(this.itemPage, "settingLockInTypeValue");

        if (settingLockInPeriodValue.getObject() != null) {
            if (settingLockInTypeValue.getObject() != null) {
                this.settingLockInPeriodValue = String.valueOf(settingLockInPeriodValue.getObject()) + " " + settingLockInTypeValue.getObject().getText();
            } else {
                this.settingLockInPeriodValue = String.valueOf(settingLockInPeriodValue.getObject());
            }
        }

        PropertyModel<Long> settingMinimumDepositTermValue = new PropertyModel<>(this.itemPage, "settingMinimumDepositTermValue");
        PropertyModel<Option> settingMinimumDepositTypeValue = new PropertyModel<>(this.itemPage, "settingMinimumDepositTypeValue");
        if (settingMinimumDepositTermValue.getObject() != null) {
            if (settingMinimumDepositTypeValue.getObject() != null) {
                this.settingMinimumDepositTermValue = String.valueOf(settingMinimumDepositTermValue.getObject()) + " " + settingMinimumDepositTypeValue.getObject().getText();
            } else {
                this.settingMinimumDepositTermValue = String.valueOf(settingMinimumDepositTermValue.getObject());
            }
        }

        PropertyModel<Long> settingInMultiplesOfValue = new PropertyModel<>(this.itemPage, "settingInMultiplesOfValue");
        PropertyModel<Option> settingInMultiplesTypeValue = new PropertyModel<>(this.itemPage, "settingInMultiplesTypeValue");
        if (settingInMultiplesOfValue.getObject() != null) {
            if (settingInMultiplesTypeValue.getObject() != null) {
                this.settingInMultiplesOfValue = String.valueOf(settingInMultiplesOfValue.getObject()) + " " + settingInMultiplesTypeValue.getObject().getText();
            } else {
                this.settingInMultiplesOfValue = String.valueOf(settingInMultiplesOfValue.getObject());
            }
        }

        PropertyModel<Long> settingMaximumDepositTermValue = new PropertyModel<>(this.itemPage, "settingMaximumDepositTermValue");
        PropertyModel<Option> settingMaximumDepositTypeValue = new PropertyModel<>(this.itemPage, "settingMaximumDepositTypeValue");
        if (settingMaximumDepositTermValue.getObject() != null) {
            if (settingMaximumDepositTypeValue.getObject() != null) {
                this.settingMaximumDepositTermValue = String.valueOf(settingMaximumDepositTermValue.getObject()) + " " + settingMaximumDepositTypeValue.getObject().getText();
            } else {
                this.settingMaximumDepositTermValue = String.valueOf(settingMaximumDepositTermValue.getObject());
            }
        }

        PropertyModel<Double> settingApplyPenalInterestValue = new PropertyModel<>(this.itemPage, "settingApplyPenalInterestValue");
        PropertyModel<Option> settingApplyPenalOnValue = new PropertyModel<>(this.itemPage, "settingApplyPenalOnValue");

        if (settingApplyPenalInterestValue.getObject() != null) {
            if (settingApplyPenalOnValue.getObject() != null) {
                this.settingApplyPenalValue = String.valueOf(settingApplyPenalInterestValue.getObject()) + "%" + " On " + settingApplyPenalOnValue.getObject().getText();
            } else {
                this.settingApplyPenalValue = String.valueOf(settingApplyPenalInterestValue.getObject()) + "%";
            }
        }

        this.saveButton.setVisible(!this.errorInterestRateChart.getObject() && !this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
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

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(FixedDepositCreatePage.TAB_ACCOUNTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        if (this.itemPage instanceof FixedDepositCreatePage) {
            ((FixedDepositCreatePage) this.itemPage).saveButtonSubmit(button);
        }
    }

}
