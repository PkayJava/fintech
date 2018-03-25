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

    protected Form<Void> form;
    protected Button saveButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameVContainer;
    protected ReadOnlyView detailProductNameView;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameVContainer;
    protected ReadOnlyView detailShortNameView;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionVContainer;
    protected ReadOnlyView detailDescriptionView;

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeVContainer;
    protected ReadOnlyView currencyCodeView;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceVContainer;
    protected ReadOnlyView currencyDecimalPlaceView;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfVContainer;
    protected ReadOnlyView currencyMultipleOfView;

    protected WebMarkupBlock termNominalAnnualInterestBlock;
    protected WebMarkupContainer termNominalAnnualInterestVContainer;
    protected ReadOnlyView termNominalAnnualInterestView;

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

    protected WebMarkupBlock settingMinimumOpeningBalanceBlock;
    protected WebMarkupContainer settingMinimumOpeningBalanceVContainer;
    protected ReadOnlyView settingMinimumOpeningBalanceView;

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodVContainer;
    protected ReadOnlyView settingLockInPeriodView;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeVContainer;
    protected ReadOnlyView settingLockInTypeView;

    protected WebMarkupBlock settingApplyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer settingApplyWithdrawalFeeForTransferVContainer;
    protected ReadOnlyView settingApplyWithdrawalFeeForTransferView;

    protected WebMarkupBlock settingEnforceMinimumBalanceBlock;
    protected WebMarkupContainer settingEnforceMinimumBalanceVContainer;
    protected ReadOnlyView settingEnforceMinimumBalanceView;

    protected WebMarkupBlock settingBalanceRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingBalanceRequiredForInterestCalculationVContainer;
    protected ReadOnlyView settingBalanceRequiredForInterestCalculationView;

    protected WebMarkupBlock settingMinimumBalanceBlock;
    protected WebMarkupContainer settingMinimumBalanceVContainer;
    protected ReadOnlyView settingMinimumBalanceView;

    protected WebMarkupBlock settingOverdraftAllowedBlock;
    protected WebMarkupContainer settingOverdraftAllowedVContainer;
    protected ReadOnlyView settingOverdraftAllowedView;

    protected WebMarkupBlock settingMaximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer settingMaximumOverdraftAmountLimitVContainer;
    protected ReadOnlyView settingMaximumOverdraftAmountLimitView;

    protected WebMarkupBlock settingNominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer settingNominalAnnualInterestForOverdraftVContainer;
    protected ReadOnlyView settingNominalAnnualInterestForOverdraftView;

    protected WebMarkupBlock settingMinOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer settingMinOverdraftRequiredForInterestCalculationVContainer;
    protected ReadOnlyView settingMinOverdraftRequiredForInterestCalculationView;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableVContainer;
    protected ReadOnlyView settingWithholdTaxApplicableView;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupVContainer;
    protected ReadOnlyView settingTaxGroupView;

    protected WebMarkupBlock settingEnableDormancyTrackingBlock;
    protected WebMarkupContainer settingEnableDormancyTrackingVContainer;
    protected ReadOnlyView settingEnableDormancyTrackingView;

    protected WebMarkupBlock settingNumberOfDaysToInactiveSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToInactiveSubStatusVContainer;
    protected ReadOnlyView settingNumberOfDaysToInactiveSubStatusView;

    protected WebMarkupBlock settingNumberOfDaysToDormantSubStatusBlock;
    protected WebMarkupContainer settingNumberOfDaysToDormantSubStatusVContainer;
    protected ReadOnlyView settingNumberOfDaysToDormantSubStatusView;

    protected WebMarkupBlock settingNumberOfDaysToEscheatBlock;
    protected WebMarkupContainer settingNumberOfDaysToEscheatVContainer;
    protected ReadOnlyView settingNumberOfDaysToEscheatView;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected ListDataProvider chargeProvider;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;

    protected Label accountingLabel;

    protected WebMarkupContainer cashMaster;

    protected WebMarkupBlock cashSavingReferenceBlock;
    protected WebMarkupContainer cashSavingReferenceVContainer;
    protected ReadOnlyView cashSavingReferenceView;

    protected WebMarkupBlock cashOverdraftPortfolioBlock;
    protected WebMarkupContainer cashOverdraftPortfolioVContainer;
    protected ReadOnlyView cashOverdraftPortfolioView;

    protected WebMarkupBlock cashSavingControlBlock;
    protected WebMarkupContainer cashSavingControlVContainer;
    protected ReadOnlyView cashSavingControlView;

    protected WebMarkupBlock cashSavingTransferInSuspenseBlock;
    protected WebMarkupContainer cashSavingTransferInSuspenseVContainer;
    protected ReadOnlyView cashSavingTransferInSuspenseView;

    protected WebMarkupBlock cashInterestOnSavingBlock;
    protected WebMarkupContainer cashInterestOnSavingVContainer;
    protected ReadOnlyView cashInterestOnSavingView;

    protected WebMarkupBlock cashWriteOffBlock;
    protected WebMarkupContainer cashWriteOffVContainer;
    protected ReadOnlyView cashWriteOffView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected ReadOnlyView cashIncomeFromFeeView;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyVContainer;
    protected ReadOnlyView cashIncomeFromPenaltyView;

    protected WebMarkupBlock cashOverdraftInterestIncomeBlock;
    protected WebMarkupContainer cashOverdraftInterestIncomeVContainer;
    protected ReadOnlyView cashOverdraftInterestIncomeView;

    protected WebMarkupBlock cashEscheatLiabilityBlock;
    protected WebMarkupContainer cashEscheatLiabilityVContainer;
    protected ReadOnlyView cashEscheatLiabilityView;

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

        this.termNominalAnnualInterestBlock = new WebMarkupBlock("termNominalAnnualInterestBlock", Size.Four_4);
        this.form.add(this.termNominalAnnualInterestBlock);
        this.termNominalAnnualInterestVContainer = new WebMarkupContainer("termNominalAnnualInterestVContainer");
        this.termNominalAnnualInterestBlock.add(this.termNominalAnnualInterestVContainer);
        this.termNominalAnnualInterestView = new ReadOnlyView("termNominalAnnualInterestView", new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestVContainer.add(this.termNominalAnnualInterestView);

        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Four_4);
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodVContainer = new WebMarkupContainer("termInterestCompoundingPeriodVContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodVContainer);
        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"));
        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);

        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Four_4);
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodVContainer = new WebMarkupContainer("termInterestPostingPeriodVContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodVContainer);
        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"));
        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);

        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingVContainer = new WebMarkupContainer("termInterestCalculatedUsingVContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingVContainer);
        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"));
        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);

        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Six_6);
        this.form.add(this.termDayInYearBlock);
        this.termDayInYearVContainer = new WebMarkupContainer("termDayInYearVContainer");
        this.termDayInYearBlock.add(this.termDayInYearVContainer);
        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this.itemPage, "termDayInYearValue"));
        this.termDayInYearVContainer.add(this.termDayInYearView);

        this.settingMinimumOpeningBalanceBlock = new WebMarkupBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
        this.form.add(this.settingMinimumOpeningBalanceBlock);
        this.settingMinimumOpeningBalanceVContainer = new WebMarkupContainer("settingMinimumOpeningBalanceVContainer");
        this.settingMinimumOpeningBalanceBlock.add(this.settingMinimumOpeningBalanceVContainer);
        this.settingMinimumOpeningBalanceView = new ReadOnlyView("settingMinimumOpeningBalanceView", new PropertyModel<>(this.itemPage, "settingMinimumOpeningBalanceValue"));
        this.settingMinimumOpeningBalanceVContainer.add(this.settingMinimumOpeningBalanceView);

        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Four_4);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodVContainer = new WebMarkupContainer("settingLockInPeriodVContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodVContainer);
        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Four_4);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeVContainer = new WebMarkupContainer("settingLockInTypeVContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeVContainer);
        this.settingLockInTypeView = new ReadOnlyView("settingLockInTypeView", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"));
        this.settingLockInTypeVContainer.add(this.settingLockInTypeView);

        this.settingApplyWithdrawalFeeForTransferBlock = new WebMarkupBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Six_6);
        this.form.add(this.settingApplyWithdrawalFeeForTransferBlock);
        this.settingApplyWithdrawalFeeForTransferVContainer = new WebMarkupContainer("settingApplyWithdrawalFeeForTransferVContainer");
        this.settingApplyWithdrawalFeeForTransferBlock.add(this.settingApplyWithdrawalFeeForTransferVContainer);
        this.settingApplyWithdrawalFeeForTransferView = new ReadOnlyView("settingApplyWithdrawalFeeForTransferView", new PropertyModel<>(this.itemPage, "settingApplyWithdrawalFeeForTransferValue"));
        this.settingApplyWithdrawalFeeForTransferVContainer.add(this.settingApplyWithdrawalFeeForTransferView);

        this.settingEnforceMinimumBalanceBlock = new WebMarkupBlock("settingEnforceMinimumBalanceBlock", Size.Six_6);
        this.form.add(this.settingEnforceMinimumBalanceBlock);
        this.settingEnforceMinimumBalanceVContainer = new WebMarkupContainer("settingEnforceMinimumBalanceVContainer");
        this.settingEnforceMinimumBalanceBlock.add(this.settingEnforceMinimumBalanceVContainer);
        this.settingEnforceMinimumBalanceView = new ReadOnlyView("settingEnforceMinimumBalanceView", new PropertyModel<>(this.itemPage, "settingEnforceMinimumBalanceValue"));
        this.settingEnforceMinimumBalanceVContainer.add(this.settingEnforceMinimumBalanceView);

        this.settingBalanceRequiredForInterestCalculationBlock = new WebMarkupBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Six_6);
        this.form.add(this.settingBalanceRequiredForInterestCalculationBlock);
        this.settingBalanceRequiredForInterestCalculationVContainer = new WebMarkupContainer("settingBalanceRequiredForInterestCalculationVContainer");
        this.settingBalanceRequiredForInterestCalculationBlock.add(this.settingBalanceRequiredForInterestCalculationVContainer);
        this.settingBalanceRequiredForInterestCalculationView = new ReadOnlyView("settingBalanceRequiredForInterestCalculationView", new PropertyModel<>(this.itemPage, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationVContainer.add(this.settingBalanceRequiredForInterestCalculationView);

        this.settingMinimumBalanceBlock = new WebMarkupBlock("settingMinimumBalanceBlock", Size.Six_6);
        this.form.add(this.settingMinimumBalanceBlock);
        this.settingMinimumBalanceVContainer = new WebMarkupContainer("settingMinimumBalanceVContainer");
        this.settingMinimumBalanceBlock.add(this.settingMinimumBalanceVContainer);
        this.settingMinimumBalanceView = new ReadOnlyView("settingMinimumBalanceView", new PropertyModel<>(this.itemPage, "settingMinimumBalanceValue"));
        this.settingMinimumBalanceVContainer.add(this.settingMinimumBalanceView);

        this.settingOverdraftAllowedBlock = new WebMarkupBlock("settingOverdraftAllowedBlock", Size.Three_3);
        this.form.add(this.settingOverdraftAllowedBlock);
        this.settingOverdraftAllowedVContainer = new WebMarkupContainer("settingOverdraftAllowedVContainer");
        this.settingOverdraftAllowedBlock.add(this.settingOverdraftAllowedVContainer);
        this.settingOverdraftAllowedView = new ReadOnlyView("settingOverdraftAllowedView", new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue"));
        this.settingOverdraftAllowedVContainer.add(this.settingOverdraftAllowedView);

        this.settingMaximumOverdraftAmountLimitBlock = new WebMarkupBlock("settingMaximumOverdraftAmountLimitBlock", Size.Three_3);
        this.form.add(this.settingMaximumOverdraftAmountLimitBlock);
        this.settingMaximumOverdraftAmountLimitVContainer = new WebMarkupContainer("settingMaximumOverdraftAmountLimitVContainer");
        this.settingMaximumOverdraftAmountLimitBlock.add(this.settingMaximumOverdraftAmountLimitVContainer);
        this.settingMaximumOverdraftAmountLimitView = new ReadOnlyView("settingMaximumOverdraftAmountLimitView", new PropertyModel<>(this.itemPage, "settingMaximumOverdraftAmountLimitValue"));
        this.settingMaximumOverdraftAmountLimitVContainer.add(this.settingMaximumOverdraftAmountLimitView);

        this.settingNominalAnnualInterestForOverdraftBlock = new WebMarkupBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Three_3);
        this.form.add(this.settingNominalAnnualInterestForOverdraftBlock);
        this.settingNominalAnnualInterestForOverdraftVContainer = new WebMarkupContainer("settingNominalAnnualInterestForOverdraftVContainer");
        this.settingNominalAnnualInterestForOverdraftBlock.add(this.settingNominalAnnualInterestForOverdraftVContainer);
        this.settingNominalAnnualInterestForOverdraftView = new ReadOnlyView("settingNominalAnnualInterestForOverdraftView", new PropertyModel<>(this.itemPage, "settingNominalAnnualInterestForOverdraftValue"));
        this.settingNominalAnnualInterestForOverdraftVContainer.add(this.settingNominalAnnualInterestForOverdraftView);

        this.settingMinOverdraftRequiredForInterestCalculationBlock = new WebMarkupBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Three_3);
        this.form.add(this.settingMinOverdraftRequiredForInterestCalculationBlock);
        this.settingMinOverdraftRequiredForInterestCalculationVContainer = new WebMarkupContainer("settingMinOverdraftRequiredForInterestCalculationVContainer");
        this.settingMinOverdraftRequiredForInterestCalculationBlock.add(this.settingMinOverdraftRequiredForInterestCalculationVContainer);
        this.settingMinOverdraftRequiredForInterestCalculationView = new ReadOnlyView("settingMinOverdraftRequiredForInterestCalculationView", new PropertyModel<>(this.itemPage, "settingMinOverdraftRequiredForInterestCalculationValue"));
        this.settingMinOverdraftRequiredForInterestCalculationVContainer.add(this.settingMinOverdraftRequiredForInterestCalculationView);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableVContainer = new WebMarkupContainer("settingWithholdTaxApplicableVContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableVContainer);
        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupVContainer = new WebMarkupContainer("settingTaxGroupVContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupVContainer);
        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"));
        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);

        this.settingEnableDormancyTrackingBlock = new WebMarkupBlock("settingEnableDormancyTrackingBlock", Size.Three_3);
        this.form.add(this.settingEnableDormancyTrackingBlock);
        this.settingEnableDormancyTrackingVContainer = new WebMarkupContainer("settingEnableDormancyTrackingVContainer");
        this.settingEnableDormancyTrackingBlock.add(this.settingEnableDormancyTrackingVContainer);
        this.settingEnableDormancyTrackingView = new ReadOnlyView("settingEnableDormancyTrackingView", new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue"));
        this.settingEnableDormancyTrackingVContainer.add(this.settingEnableDormancyTrackingView);

        this.settingNumberOfDaysToInactiveSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Three_3);
        this.form.add(this.settingNumberOfDaysToInactiveSubStatusBlock);
        this.settingNumberOfDaysToInactiveSubStatusVContainer = new WebMarkupContainer("settingNumberOfDaysToInactiveSubStatusVContainer");
        this.settingNumberOfDaysToInactiveSubStatusBlock.add(this.settingNumberOfDaysToInactiveSubStatusVContainer);
        this.settingNumberOfDaysToInactiveSubStatusView = new ReadOnlyView("settingNumberOfDaysToInactiveSubStatusView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToInactiveSubStatusValue"));
        this.settingNumberOfDaysToInactiveSubStatusVContainer.add(this.settingNumberOfDaysToInactiveSubStatusView);

        this.settingNumberOfDaysToDormantSubStatusBlock = new WebMarkupBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Three_3);
        this.form.add(this.settingNumberOfDaysToDormantSubStatusBlock);
        this.settingNumberOfDaysToDormantSubStatusVContainer = new WebMarkupContainer("settingNumberOfDaysToDormantSubStatusVContainer");
        this.settingNumberOfDaysToDormantSubStatusBlock.add(this.settingNumberOfDaysToDormantSubStatusVContainer);
        this.settingNumberOfDaysToDormantSubStatusView = new ReadOnlyView("settingNumberOfDaysToDormantSubStatusView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToDormantSubStatusValue"));
        this.settingNumberOfDaysToDormantSubStatusVContainer.add(this.settingNumberOfDaysToDormantSubStatusView);

        this.settingNumberOfDaysToEscheatBlock = new WebMarkupBlock("settingNumberOfDaysToEscheatBlock", Size.Three_3);
        this.form.add(this.settingNumberOfDaysToEscheatBlock);
        this.settingNumberOfDaysToEscheatVContainer = new WebMarkupContainer("settingNumberOfDaysToEscheatVContainer");
        this.settingNumberOfDaysToEscheatBlock.add(this.settingNumberOfDaysToEscheatVContainer);
        this.settingNumberOfDaysToEscheatView = new ReadOnlyView("settingNumberOfDaysToEscheatView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToEscheatValue"));
        this.settingNumberOfDaysToEscheatVContainer.add(this.settingNumberOfDaysToEscheatView);

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

        this.cashSavingReferenceBlock = new WebMarkupBlock("cashSavingReferenceBlock", Size.Four_4);
        this.cashMaster.add(this.cashSavingReferenceBlock);
        this.cashSavingReferenceVContainer = new WebMarkupContainer("cashSavingReferenceVContainer");
        this.cashSavingReferenceBlock.add(this.cashSavingReferenceVContainer);
        this.cashSavingReferenceView = new ReadOnlyView("cashSavingReferenceView", new PropertyModel<String>(this.itemPage, "cashSavingReferenceValue"));
        this.cashSavingReferenceVContainer.add(this.cashSavingReferenceView);

        this.cashOverdraftPortfolioBlock = new WebMarkupBlock("cashOverdraftPortfolioBlock", Size.Four_4);
        this.cashMaster.add(this.cashOverdraftPortfolioBlock);
        this.cashOverdraftPortfolioVContainer = new WebMarkupContainer("cashOverdraftPortfolioVContainer");
        this.cashOverdraftPortfolioBlock.add(this.cashOverdraftPortfolioVContainer);
        this.cashOverdraftPortfolioView = new ReadOnlyView("cashOverdraftPortfolioView", new PropertyModel<String>(this.itemPage, "cashOverdraftPortfolioValue"));
        this.cashOverdraftPortfolioVContainer.add(this.cashOverdraftPortfolioView);

        this.cashSavingControlBlock = new WebMarkupBlock("cashSavingControlBlock", Size.Four_4);
        this.cashMaster.add(this.cashSavingControlBlock);
        this.cashSavingControlVContainer = new WebMarkupContainer("cashSavingControlVContainer");
        this.cashSavingControlBlock.add(this.cashSavingControlVContainer);
        this.cashSavingControlView = new ReadOnlyView("cashSavingControlView", new PropertyModel<String>(this.itemPage, "cashSavingControlValue"));
        this.cashSavingControlVContainer.add(this.cashSavingControlView);

        this.cashSavingTransferInSuspenseBlock = new WebMarkupBlock("cashSavingTransferInSuspenseBlock", Size.Four_4);
        this.cashMaster.add(this.cashSavingTransferInSuspenseBlock);
        this.cashSavingTransferInSuspenseVContainer = new WebMarkupContainer("cashSavingTransferInSuspenseVContainer");
        this.cashSavingTransferInSuspenseBlock.add(this.cashSavingTransferInSuspenseVContainer);
        this.cashSavingTransferInSuspenseView = new ReadOnlyView("cashSavingTransferInSuspenseView", new PropertyModel<String>(this.itemPage, "cashSavingTransferInSuspenseValue"));
        this.cashSavingTransferInSuspenseVContainer.add(this.cashSavingTransferInSuspenseView);

        this.cashInterestOnSavingBlock = new WebMarkupBlock("cashInterestOnSavingBlock", Size.Four_4);
        this.cashMaster.add(this.cashInterestOnSavingBlock);
        this.cashInterestOnSavingVContainer = new WebMarkupContainer("cashInterestOnSavingVContainer");
        this.cashInterestOnSavingBlock.add(this.cashInterestOnSavingVContainer);
        this.cashInterestOnSavingView = new ReadOnlyView("cashInterestOnSavingView", new PropertyModel<String>(this.itemPage, "cashInterestOnSavingValue"));
        this.cashInterestOnSavingVContainer.add(this.cashInterestOnSavingView);

        this.cashWriteOffBlock = new WebMarkupBlock("cashWriteOffBlock", Size.Four_4);
        this.cashMaster.add(this.cashWriteOffBlock);
        this.cashWriteOffVContainer = new WebMarkupContainer("cashWriteOffVContainer");
        this.cashWriteOffBlock.add(this.cashWriteOffVContainer);
        this.cashWriteOffView = new ReadOnlyView("cashWriteOffView", new PropertyModel<String>(this.itemPage, "cashWriteOffValue"));
        this.cashWriteOffVContainer.add(this.cashWriteOffView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<String>(this.itemPage, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyVContainer = new WebMarkupContainer("cashIncomeFromPenaltyVContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyVContainer);
        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<String>(this.itemPage, "cashIncomeFromPenaltyValue"));
        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);

        this.cashOverdraftInterestIncomeBlock = new WebMarkupBlock("cashOverdraftInterestIncomeBlock", Size.Four_4);
        this.cashMaster.add(this.cashOverdraftInterestIncomeBlock);
        this.cashOverdraftInterestIncomeVContainer = new WebMarkupContainer("cashOverdraftInterestIncomeVContainer");
        this.cashOverdraftInterestIncomeBlock.add(this.cashOverdraftInterestIncomeVContainer);
        this.cashOverdraftInterestIncomeView = new ReadOnlyView("cashOverdraftInterestIncomeView", new PropertyModel<String>(this.itemPage, "cashOverdraftInterestIncomeValue"));
        this.cashOverdraftInterestIncomeVContainer.add(this.cashOverdraftInterestIncomeView);

        this.cashEscheatLiabilityBlock = new WebMarkupBlock("cashEscheatLiabilityBlock", Size.Four_4);
        this.cashMaster.add(this.cashEscheatLiabilityBlock);
        this.cashEscheatLiabilityVContainer = new WebMarkupContainer("cashEscheatLiabilityVContainer");
        this.cashEscheatLiabilityBlock.add(this.cashEscheatLiabilityVContainer);
        this.cashEscheatLiabilityView = new ReadOnlyView("cashEscheatLiabilityView", new PropertyModel<String>(this.itemPage, "cashEscheatLiabilityValue"));
        this.cashEscheatLiabilityVContainer.add(this.cashEscheatLiabilityView);

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

        PropertyModel<Boolean> settingWithholdTaxApplicableValue = new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue");
        this.settingTaxGroupVContainer.setVisible(settingWithholdTaxApplicableValue.getObject() != null && settingWithholdTaxApplicableValue.getObject());

        PropertyModel<Boolean> settingEnableDormancyTrackingValue = new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue");
        this.settingNumberOfDaysToEscheatVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
        this.settingNumberOfDaysToDormantSubStatusVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
        this.settingNumberOfDaysToInactiveSubStatusVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
        this.cashEscheatLiabilityVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());

        PropertyModel<Boolean> settingOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue");
        this.settingMaximumOverdraftAmountLimitVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());
        this.settingNominalAnnualInterestForOverdraftVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());
        this.settingMinOverdraftRequiredForInterestCalculationVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());

        this.saveButton.setVisible(!this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
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
