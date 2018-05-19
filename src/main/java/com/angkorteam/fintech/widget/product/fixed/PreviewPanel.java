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
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
import com.angkorteam.fintech.popup.IncentivePopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.share.provider.ListDataProvider;
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

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    // Details

    protected UIRow row1;

    protected UIBlock detailProductNameBlock;
    protected UIContainer detailProductNameVContainer;
    protected ReadOnlyView detailProductNameView;

    protected UIBlock detailShortNameBlock;
    protected UIContainer detailShortNameVContainer;
    protected ReadOnlyView detailShortNameView;

    protected UIRow row2;

    protected UIBlock detailDescriptionBlock;
    protected UIContainer detailDescriptionVContainer;
    protected ReadOnlyView detailDescriptionView;

    // Items

    protected UIRow row3;

    protected UIBlock currencyCodeBlock;
    protected UIContainer currencyCodeVContainer;
    protected ReadOnlyView currencyCodeView;

    protected UIBlock currencyDecimalPlaceBlock;
    protected UIContainer currencyDecimalPlaceVContainer;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected UIBlock currencyMultipleOfBlock;
    protected UIContainer currencyMultipleOfVContainer;
    protected ReadOnlyView currencyMultipleOfView;

    protected UIRow row4;

    protected UIBlock termDefaultDepositAmountBlock;
    protected UIContainer termDefaultDepositAmountVContainer;
    protected ReadOnlyView termDefaultDepositAmountView;

    protected UIBlock termMinimumDepositAmountBlock;
    protected UIContainer termMinimumDepositAmountVContainer;
    protected ReadOnlyView termMinimumDepositAmountView;

    protected UIBlock termMaximumDepositAmountBlock;
    protected UIContainer termMaximumDepositAmountVContainer;
    protected ReadOnlyView termMaximumDepositAmountView;

    protected UIRow row5;

    protected UIBlock termInterestCompoundingPeriodBlock;
    protected UIContainer termInterestCompoundingPeriodVContainer;
    protected ReadOnlyView termInterestCompoundingPeriodView;

    protected UIBlock termInterestPostingPeriodBlock;
    protected UIContainer termInterestPostingPeriodVContainer;
    protected ReadOnlyView termInterestPostingPeriodView;

    protected UIBlock termInterestCalculatedUsingBlock;
    protected UIContainer termInterestCalculatedUsingVContainer;
    protected ReadOnlyView termInterestCalculatedUsingView;

    protected UIBlock termDayInYearBlock;
    protected UIContainer termDayInYearVContainer;
    protected ReadOnlyView termDayInYearView;

    // Settings

    protected UIRow row6;

    protected UIBlock settingLockInPeriodBlock;
    protected UIContainer settingLockInPeriodVContainer;
    protected ReadOnlyView settingLockInPeriodView;
    protected String settingLockInPeriodValue;

    protected UIBlock settingMinimumDepositTermBlock;
    protected UIContainer settingMinimumDepositTermVContainer;
    protected ReadOnlyView settingMinimumDepositTermView;
    protected String settingMinimumDepositTermValue;

    protected UIBlock settingInMultiplesOfBlock;
    protected UIContainer settingInMultiplesOfVContainer;
    protected ReadOnlyView settingInMultiplesOfView;
    protected String settingInMultiplesOfValue;

    protected UIBlock settingMaximumDepositTermBlock;
    protected UIContainer settingMaximumDepositTermVContainer;
    protected ReadOnlyView settingMaximumDepositTermView;
    protected String settingMaximumDepositTermValue;

    protected UIRow row7;

    protected UIBlock settingApplyPenalBlock;
    protected UIContainer settingApplyPenalVContainer;
    protected ReadOnlyView settingApplyPenalView;
    protected String settingApplyPenalValue;

    protected UIBlock settingWithholdTaxApplicableBlock;
    protected UIContainer settingWithholdTaxApplicableVContainer;
    protected ReadOnlyView settingWithholdTaxApplicableView;

    protected UIBlock settingTaxGroupBlock;
    protected UIContainer settingTaxGroupVContainer;
    protected ReadOnlyView settingTaxGroupView;

    // Interest Rate Chart

    protected UIRow row8;

    protected UIBlock interestRateValidFromDateBlock;
    protected UIContainer interestRateValidFromDateVContainer;
    protected ReadOnlyView interestRateValidFromDateView;

    protected UIBlock interestRateValidEndDateBlock;
    protected UIContainer interestRateValidEndDateVContainer;
    protected ReadOnlyView interestRateValidEndDateView;

    protected UIRow row9;

    protected UIBlock interestRateChartBlock;
    protected UIContainer interestRateChartVContainer;
    protected DataTable<Map<String, Object>, String> interestRateChartTable;
    protected List<IColumn<Map<String, Object>, String>> interestRateChartColumn;
    protected ListDataProvider interestRateChartProvider;
    protected PropertyModel<List<Map<String, Object>>> interestRateChartValue;

    // Charges

    protected UIRow row10;

    protected UIBlock chargeBlock;
    protected UIContainer chargeVContainer;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected ListDataProvider chargeProvider;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;

    // Accounting

    protected Label accountingLabel;

    protected WebMarkupContainer cashMaster;

    protected UIRow row11;

    protected UIBlock cashSavingReferenceBlock;
    protected UIContainer cashSavingReferenceVContainer;
    protected ReadOnlyView cashSavingReferenceView;

    protected UIBlock cashSavingControlBlock;
    protected UIContainer cashSavingControlVContainer;
    protected ReadOnlyView cashSavingControlView;

    protected UIBlock cashSavingTransferInSuspenseBlock;
    protected UIContainer cashSavingTransferInSuspenseVContainer;
    protected ReadOnlyView cashSavingTransferInSuspenseView;

    protected UIRow row12;

    protected UIBlock cashInterestOnSavingBlock;
    protected UIContainer cashInterestOnSavingVContainer;
    protected ReadOnlyView cashInterestOnSavingView;

    protected UIBlock cashIncomeFromFeeBlock;
    protected UIContainer cashIncomeFromFeeVContainer;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected UIBlock cashIncomeFromPenaltyBlock;
    protected UIContainer cashIncomeFromPenaltyVContainer;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    // Advanced Accounting Rule

    protected WebMarkupContainer advancedAccountingRuleMaster;

    protected UIRow row13;

    protected UIBlock row13Block1;

    protected UIContainer advancedAccountingRuleFundSourceVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;

    protected UIContainer advancedAccountingRuleFeeIncomeVContainer;
    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;

    protected UIContainer advancedAccountingRulePenaltyIncomeVContainer;
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

        this.interestRateChartValue = new PropertyModel<>(this.itemPage, "interestRateChartValue");
        this.interestRateChartColumn = Lists.newLinkedList();
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period From/To"), "period", "period", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount From/To"), "amountRange", "amountRange", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartAction, this::interestRateChartClick));
        this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue.getObject());

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedBrowsePage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
        this.detailProductNameVContainer = this.detailProductNameBlock.newUIContainer("detailProductNameVContainer");
        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameVContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = this.row1.newUIBlock("detailShortNameBlock", Size.Six_6);
        this.detailShortNameVContainer = this.detailShortNameBlock.newUIContainer("detailShortNameVContainer");
        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameVContainer.add(this.detailShortNameView);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.detailDescriptionBlock = this.row2.newUIBlock("detailDescriptionBlock", Size.Twelve_12);
        this.detailDescriptionVContainer = this.detailDescriptionBlock.newUIContainer("detailDescriptionVContainer");
        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionVContainer.add(this.detailDescriptionView);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.currencyCodeBlock = this.row3.newUIBlock("currencyCodeBlock", Size.Six_6);
        this.currencyCodeVContainer = this.currencyCodeBlock.newUIContainer("currencyCodeVContainer");
        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this.itemPage, "currencyCodeValue"));
        this.currencyCodeVContainer.add(this.currencyCodeView);

        this.currencyDecimalPlaceBlock = this.row3.newUIBlock("currencyDecimalPlaceBlock", Size.Three_3);
        this.currencyDecimalPlaceVContainer = this.currencyDecimalPlaceBlock.newUIContainer("currencyDecimalPlaceVContainer");
        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);

        this.currencyMultipleOfBlock = this.row3.newUIBlock("currencyMultipleOfBlock", Size.Three_3);
        this.currencyMultipleOfVContainer = this.currencyMultipleOfBlock.newUIContainer("currencyMultipleOfVContainer");
        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this.itemPage, "currencyMultipleOfValue"));
        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.termDefaultDepositAmountBlock = this.row4.newUIBlock("termDefaultDepositAmountBlock", Size.Four_4);
        this.termDefaultDepositAmountVContainer = this.termDefaultDepositAmountBlock.newUIContainer("termDefaultDepositAmountVContainer");
        this.termDefaultDepositAmountView = new ReadOnlyView("termDefaultDepositAmountView", new PropertyModel<>(this.itemPage, "termDefaultDepositAmountValue"));
        this.termDefaultDepositAmountVContainer.add(this.termDefaultDepositAmountView);

        this.termMinimumDepositAmountBlock = this.row4.newUIBlock("termMinimumDepositAmountBlock", Size.Four_4);
        this.termMinimumDepositAmountVContainer = this.termMinimumDepositAmountBlock.newUIContainer("termMinimumDepositAmountVContainer");
        this.termMinimumDepositAmountView = new ReadOnlyView("termMinimumDepositAmountView", new PropertyModel<>(this.itemPage, "termMinimumDepositAmountValue"));
        this.termMinimumDepositAmountVContainer.add(this.termMinimumDepositAmountView);

        this.termMaximumDepositAmountBlock = this.row4.newUIBlock("termMaximumDepositAmountBlock", Size.Four_4);
        this.termMaximumDepositAmountVContainer = this.termMaximumDepositAmountBlock.newUIContainer("termMaximumDepositAmountVContainer");
        this.termMaximumDepositAmountView = new ReadOnlyView("termMaximumDepositAmountView", new PropertyModel<>(this.itemPage, "termMaximumDepositAmountValue"));
        this.termMaximumDepositAmountVContainer.add(this.termMaximumDepositAmountView);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.termInterestCompoundingPeriodBlock = this.row5.newUIBlock("termInterestCompoundingPeriodBlock", Size.Three_3);
        this.termInterestCompoundingPeriodVContainer = this.termInterestCompoundingPeriodBlock.newUIContainer("termInterestCompoundingPeriodVContainer");
        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"));
        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);

        this.termInterestPostingPeriodBlock = this.row5.newUIBlock("termInterestPostingPeriodBlock", Size.Three_3);
        this.termInterestPostingPeriodVContainer = this.termInterestPostingPeriodBlock.newUIContainer("termInterestPostingPeriodVContainer");
        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"));
        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);

        this.termInterestCalculatedUsingBlock = this.row5.newUIBlock("termInterestCalculatedUsingBlock", Size.Three_3);
        this.termInterestCalculatedUsingVContainer = this.termInterestCalculatedUsingBlock.newUIContainer("termInterestCalculatedUsingVContainer");
        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"));
        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);

        this.termDayInYearBlock = this.row5.newUIBlock("termDayInYearBlock", Size.Three_3);
        this.termDayInYearVContainer = this.termDayInYearBlock.newUIContainer("termDayInYearVContainer");
        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this.itemPage, "termDayInYearValue"));
        this.termDayInYearVContainer.add(this.termDayInYearView);

        this.row6 = UIRow.newUIRow("row6", this.form);

        this.settingLockInPeriodBlock = this.row6.newUIBlock("settingLockInPeriodBlock", Size.Three_3);
        this.settingLockInPeriodVContainer = this.settingLockInPeriodBlock.newUIContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);

        this.settingMinimumDepositTermBlock = this.row6.newUIBlock("settingMinimumDepositTermBlock", Size.Three_3);
        this.settingMinimumDepositTermVContainer = this.settingMinimumDepositTermBlock.newUIContainer("settingMinimumDepositTermVContainer");
        this.settingMinimumDepositTermView = new ReadOnlyView("settingMinimumDepositTermView", new PropertyModel<>(this, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermVContainer.add(this.settingMinimumDepositTermView);

        this.settingInMultiplesOfBlock = this.row6.newUIBlock("settingInMultiplesOfBlock", Size.Three_3);
        this.settingInMultiplesOfVContainer = this.settingInMultiplesOfBlock.newUIContainer("settingInMultiplesOfVContainer");
        this.settingInMultiplesOfView = new ReadOnlyView("settingInMultiplesOfView", new PropertyModel<>(this, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfVContainer.add(this.settingInMultiplesOfView);

        this.settingMaximumDepositTermBlock = this.row6.newUIBlock("settingMaximumDepositTermBlock", Size.Three_3);
        this.settingMaximumDepositTermVContainer = this.settingMaximumDepositTermBlock.newUIContainer("settingMaximumDepositTermVContainer");
        this.settingMaximumDepositTermView = new ReadOnlyView("settingMaximumDepositTermView", new PropertyModel<>(this, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermVContainer.add(this.settingMaximumDepositTermView);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.settingApplyPenalBlock = this.row7.newUIBlock("settingApplyPenalBlock", Size.Six_6);
        this.settingApplyPenalVContainer = this.settingApplyPenalBlock.newUIContainer("settingApplyPenalVContainer");
        this.settingApplyPenalView = new ReadOnlyView("settingApplyPenalView", new PropertyModel<>(this, "settingApplyPenalValue"));
        this.settingApplyPenalVContainer.add(this.settingApplyPenalView);

        this.settingWithholdTaxApplicableBlock = this.row7.newUIBlock("settingWithholdTaxApplicableBlock", Size.Three_3);
        this.settingWithholdTaxApplicableVContainer = this.settingWithholdTaxApplicableBlock.newUIContainer("settingWithholdTaxApplicableVContainer");
        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);

        this.settingTaxGroupBlock = this.row7.newUIBlock("settingTaxGroupBlock", Size.Three_3);
        this.settingTaxGroupVContainer = this.settingTaxGroupBlock.newUIContainer("settingTaxGroupVContainer");
        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"));
        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.interestRateValidFromDateBlock = this.row8.newUIBlock("interestRateValidFromDateBlock", Size.Six_6);
        this.interestRateValidFromDateVContainer = this.interestRateValidFromDateBlock.newUIContainer("interestRateValidFromDateVContainer");
        this.interestRateValidFromDateView = new ReadOnlyView("interestRateValidFromDateView", new PropertyModel<>(this.itemPage, "interestRateValidFromDateValue"), "dd/MM/yyyy");
        this.interestRateValidFromDateVContainer.add(this.interestRateValidFromDateView);

        this.interestRateValidEndDateBlock = this.row8.newUIBlock("interestRateValidEndDateBlock", Size.Six_6);
        this.interestRateValidEndDateVContainer = this.interestRateValidEndDateBlock.newUIContainer("interestRateValidEndDateVContainer");
        this.interestRateValidEndDateView = new ReadOnlyView("interestRateValidEndDateView", new PropertyModel<>(this.itemPage, "interestRateValidEndDateValue"), "dd/MM/yyyy");
        this.interestRateValidEndDateVContainer.add(this.interestRateValidEndDateView);

        this.row9 = UIRow.newUIRow("row9", this.form);

        this.interestRateChartBlock = this.row9.newUIBlock("interestRateChartBlock", Size.Twelve_12);
        this.interestRateChartVContainer = this.interestRateChartBlock.newUIContainer("interestRateChartVContainer");
        this.interestRateChartTable = new DataTable<>("interestRateChartTable", this.interestRateChartColumn, this.interestRateChartProvider, this.interestRateChartValue.getObject().size() + 1);
        this.interestRateChartVContainer.add(this.interestRateChartTable);
        this.interestRateChartTable.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
        this.interestRateChartTable.addBottomToolbar(new NoRecordsToolbar(this.interestRateChartTable));

        this.row10 = UIRow.newUIRow("row10", this.form);

        this.chargeBlock = this.row10.newUIBlock("chargeBlock", Size.Twelve_12);
        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, this.chargeValue.getObject().size() + 1);
        this.chargeVContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.form.add(this.accountingLabel);

        this.cashMaster = new WebMarkupContainer("cashMaster");
        this.cashMaster.setOutputMarkupId(true);
        this.form.add(this.cashMaster);

        this.row11 = UIRow.newUIRow("row11", this.cashMaster);

        this.cashSavingReferenceBlock = this.row11.newUIBlock("cashSavingReferenceBlock", Size.Four_4);
        this.cashSavingReferenceVContainer = this.cashSavingReferenceBlock.newUIContainer("cashSavingReferenceVContainer");
        this.cashSavingReferenceView = new ReadOnlyView("cashSavingReferenceView", new PropertyModel<>(this.itemPage, "cashSavingReferenceValue"));
        this.cashSavingReferenceVContainer.add(this.cashSavingReferenceView);

        this.cashSavingControlBlock = this.row11.newUIBlock("cashSavingControlBlock", Size.Four_4);
        this.cashSavingControlVContainer = this.cashSavingControlBlock.newUIContainer("cashSavingControlVContainer");
        this.cashSavingControlView = new ReadOnlyView("cashSavingControlView", new PropertyModel<>(this.itemPage, "cashSavingControlValue"));
        this.cashSavingControlVContainer.add(this.cashSavingControlView);

        this.cashSavingTransferInSuspenseBlock = this.row11.newUIBlock("cashSavingTransferInSuspenseBlock", Size.Four_4);
        this.cashSavingTransferInSuspenseVContainer = this.cashSavingTransferInSuspenseBlock.newUIContainer("cashSavingTransferInSuspenseVContainer");
        this.cashSavingTransferInSuspenseView = new ReadOnlyView("cashSavingTransferInSuspenseView", new PropertyModel<>(this.itemPage, "cashSavingTransferInSuspenseValue"));
        this.cashSavingTransferInSuspenseVContainer.add(this.cashSavingTransferInSuspenseView);

        this.row12 = UIRow.newUIRow("row12", this.cashMaster);

        this.cashInterestOnSavingBlock = this.row12.newUIBlock("cashInterestOnSavingBlock", Size.Four_4);
        this.cashInterestOnSavingVContainer = this.cashInterestOnSavingBlock.newUIContainer("cashInterestOnSavingVContainer");
        this.cashInterestOnSavingView = new ReadOnlyView("cashInterestOnSavingView", new PropertyModel<>(this.itemPage, "cashInterestOnSavingValue"));
        this.cashInterestOnSavingVContainer.add(this.cashInterestOnSavingView);

        this.cashIncomeFromFeeBlock = this.row12.newUIBlock("cashIncomeFromFeeBlock", Size.Four_4);
        this.cashIncomeFromFeeVContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

        this.cashIncomeFromPenaltyBlock = this.row12.newUIBlock("cashIncomeFromPenaltyBlock", Size.Four_4);
        this.cashIncomeFromPenaltyVContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);

        this.advancedAccountingRuleMaster = new WebMarkupContainer("advancedAccountingRuleMaster");
        this.advancedAccountingRuleMaster.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleMaster);

        this.row13 = UIRow.newUIRow("row13", this.advancedAccountingRuleMaster);

        this.row13Block1 = this.row13.newUIBlock("row13Block1", Size.Twelve_12);

        this.advancedAccountingRuleFundSourceVContainer = this.row13Block1.newUIContainer("advancedAccountingRuleFundSourceVContainer");
        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, this.advancedAccountingRuleFundSourceValue.getObject().size() + 1);
        this.advancedAccountingRuleFundSourceVContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));

        this.advancedAccountingRuleFeeIncomeVContainer = this.row13Block1.newUIContainer("advancedAccountingRuleFeeIncomeVContainer");
        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, this.advancedAccountingRuleFeeIncomeValue.getObject().size() + 1);
        this.advancedAccountingRuleFeeIncomeVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));

        this.advancedAccountingRulePenaltyIncomeVContainer = this.row13Block1.newUIContainer("advancedAccountingRulePenaltyIncomeVContainer");
        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, this.advancedAccountingRulePenaltyIncomeValue.getObject().size() + 1);
        this.advancedAccountingRulePenaltyIncomeVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));

    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {

    }

    protected List<ActionItem> interestRateChartAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newLinkedList();
        actions.add(new ActionItem("incentives", Model.of("Incentives"), ItemCss.PRIMARY));
        return actions;
    }

    protected void interestRateChartClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        if ("incentives".equals(link)) {
            List<Map<String, Object>> incentiveValue = (List<Map<String, Object>>) model.get("interestRate");
            this.modalWindow.setContent(new IncentivePopup("incentive", incentiveValue, true));
            this.modalWindow.show(target);
        }
    }

    protected ItemPanel interestRateChartColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("periodType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("description".equals(column) || "period".equals(column) || "amountRange".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
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
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_ACCOUNTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        if (this.itemPage instanceof FixedCreatePage) {
            ((FixedCreatePage) this.itemPage).saveButtonSubmit(button);
        }
    }

}
