//package com.angkorteam.fintech.widget.product.savings;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.WebMarkupContainer;
//import org.apache.wicket.markup.html.basic.Label;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.dto.enums.AccountingType;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
//import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//
//public class PreviewPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//
//    protected PropertyModel<Boolean> errorSetting;
//    protected PropertyModel<Boolean> errorAccounting;
//    protected PropertyModel<Boolean> errorCharge;
//    protected PropertyModel<Boolean> errorCurrency;
//    protected PropertyModel<Boolean> errorDetail;
//    protected PropertyModel<Boolean> errorTerm;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    // Detail
//
//    protected UIRow row1;
//
//    protected UIBlock detailProductNameBlock;
//    protected UIContainer detailProductNameVContainer;
//    protected ReadOnlyView detailProductNameView;
//
//    protected UIBlock detailShortNameBlock;
//    protected UIContainer detailShortNameVContainer;
//    protected ReadOnlyView detailShortNameView;
//
//    protected UIRow row2;
//
//    protected UIBlock detailDescriptionBlock;
//    protected UIContainer detailDescriptionVContainer;
//    protected ReadOnlyView detailDescriptionView;
//
//    // Items
//
//    protected UIRow row3;
//
//    protected UIBlock currencyCodeBlock;
//    protected UIContainer currencyCodeVContainer;
//    protected ReadOnlyView currencyCodeView;
//
//    protected UIBlock currencyDecimalPlaceBlock;
//    protected UIContainer currencyDecimalPlaceVContainer;
//    protected ReadOnlyView currencyDecimalPlaceView;
//
//    protected UIBlock currencyMultipleOfBlock;
//    protected UIContainer currencyMultipleOfVContainer;
//    protected ReadOnlyView currencyMultipleOfView;
//
//    protected UIRow row4;
//
//    protected UIBlock termNominalAnnualInterestBlock;
//    protected UIContainer termNominalAnnualInterestVContainer;
//    protected ReadOnlyView termNominalAnnualInterestView;
//
//    protected UIBlock termInterestCompoundingPeriodBlock;
//    protected UIContainer termInterestCompoundingPeriodVContainer;
//    protected ReadOnlyView termInterestCompoundingPeriodView;
//
//    protected UIBlock termInterestPostingPeriodBlock;
//    protected UIContainer termInterestPostingPeriodVContainer;
//    protected ReadOnlyView termInterestPostingPeriodView;
//
//    protected UIRow row5;
//
//    protected UIBlock termInterestCalculatedUsingBlock;
//    protected UIContainer termInterestCalculatedUsingVContainer;
//    protected ReadOnlyView termInterestCalculatedUsingView;
//
//    protected UIBlock termDayInYearBlock;
//    protected UIContainer termDayInYearVContainer;
//    protected ReadOnlyView termDayInYearView;
//
//    // Settings
//
//    protected UIRow row6;
//
//    protected UIBlock settingMinimumOpeningBalanceBlock;
//    protected UIContainer settingMinimumOpeningBalanceVContainer;
//    protected ReadOnlyView settingMinimumOpeningBalanceView;
//
//    protected UIBlock settingLockInPeriodBlock;
//    protected UIContainer settingLockInPeriodVContainer;
//    protected ReadOnlyView settingLockInPeriodView;
//
//    protected UIBlock settingLockInTypeBlock;
//    protected UIContainer settingLockInTypeVContainer;
//    protected ReadOnlyView settingLockInTypeView;
//
//    protected UIRow row7;
//
//    protected UIBlock settingApplyWithdrawalFeeForTransferBlock;
//    protected UIContainer settingApplyWithdrawalFeeForTransferVContainer;
//    protected ReadOnlyView settingApplyWithdrawalFeeForTransferView;
//
//    protected UIBlock settingEnforceMinimumBalanceBlock;
//    protected UIContainer settingEnforceMinimumBalanceVContainer;
//    protected ReadOnlyView settingEnforceMinimumBalanceView;
//
//    protected UIRow row8;
//
//    protected UIBlock settingBalanceRequiredForInterestCalculationBlock;
//    protected UIContainer settingBalanceRequiredForInterestCalculationVContainer;
//    protected ReadOnlyView settingBalanceRequiredForInterestCalculationView;
//
//    protected UIBlock settingMinimumBalanceBlock;
//    protected UIContainer settingMinimumBalanceVContainer;
//    protected ReadOnlyView settingMinimumBalanceView;
//
//    protected UIRow row9;
//
//    protected UIBlock settingOverdraftAllowedBlock;
//    protected UIContainer settingOverdraftAllowedVContainer;
//    protected ReadOnlyView settingOverdraftAllowedView;
//
//    protected UIBlock settingMaximumOverdraftAmountLimitBlock;
//    protected UIContainer settingMaximumOverdraftAmountLimitVContainer;
//    protected ReadOnlyView settingMaximumOverdraftAmountLimitView;
//
//    protected UIBlock settingNominalAnnualInterestForOverdraftBlock;
//    protected UIContainer settingNominalAnnualInterestForOverdraftVContainer;
//    protected ReadOnlyView settingNominalAnnualInterestForOverdraftView;
//
//    protected UIBlock settingMinOverdraftRequiredForInterestCalculationBlock;
//    protected UIContainer settingMinOverdraftRequiredForInterestCalculationVContainer;
//    protected ReadOnlyView settingMinOverdraftRequiredForInterestCalculationView;
//
//    protected UIRow row10;
//
//    protected UIBlock settingWithholdTaxApplicableBlock;
//    protected UIContainer settingWithholdTaxApplicableVContainer;
//    protected ReadOnlyView settingWithholdTaxApplicableView;
//
//    protected UIBlock settingTaxGroupBlock;
//    protected UIContainer settingTaxGroupVContainer;
//    protected ReadOnlyView settingTaxGroupView;
//
//    protected UIRow row11;
//
//    protected UIBlock settingEnableDormancyTrackingBlock;
//    protected UIContainer settingEnableDormancyTrackingVContainer;
//    protected ReadOnlyView settingEnableDormancyTrackingView;
//
//    protected UIBlock settingNumberOfDaysToInactiveSubStatusBlock;
//    protected UIContainer settingNumberOfDaysToInactiveSubStatusVContainer;
//    protected ReadOnlyView settingNumberOfDaysToInactiveSubStatusView;
//
//    protected UIBlock settingNumberOfDaysToDormantSubStatusBlock;
//    protected UIContainer settingNumberOfDaysToDormantSubStatusVContainer;
//    protected ReadOnlyView settingNumberOfDaysToDormantSubStatusView;
//
//    protected UIBlock settingNumberOfDaysToEscheatBlock;
//    protected UIContainer settingNumberOfDaysToEscheatVContainer;
//    protected ReadOnlyView settingNumberOfDaysToEscheatView;
//
//    // Charges
//
//    protected UIRow row12;
//
//    protected UIBlock chargeBlock;
//    protected UIContainer chargeVContainer;
//    protected DataTable<Map<String, Object>, String> chargeTable;
//    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
//    protected ListDataProvider chargeProvider;
//    protected PropertyModel<List<Map<String, Object>>> chargeValue;
//
//    // Accounting
//
//    protected Label accountingLabel;
//
//    protected WebMarkupContainer cashMaster;
//
//    protected UIRow row13;
//
//    protected UIBlock cashSavingReferenceBlock;
//    protected UIContainer cashSavingReferenceVContainer;
//    protected ReadOnlyView cashSavingReferenceView;
//
//    protected UIBlock cashOverdraftPortfolioBlock;
//    protected UIContainer cashOverdraftPortfolioVContainer;
//    protected ReadOnlyView cashOverdraftPortfolioView;
//
//    protected UIBlock cashSavingControlBlock;
//    protected UIContainer cashSavingControlVContainer;
//    protected ReadOnlyView cashSavingControlView;
//
//    protected UIRow row14;
//
//    protected UIBlock cashSavingTransferInSuspenseBlock;
//    protected UIContainer cashSavingTransferInSuspenseVContainer;
//    protected ReadOnlyView cashSavingTransferInSuspenseView;
//
//    protected UIBlock cashInterestOnSavingBlock;
//    protected UIContainer cashInterestOnSavingVContainer;
//    protected ReadOnlyView cashInterestOnSavingView;
//
//    protected UIBlock cashWriteOffBlock;
//    protected UIContainer cashWriteOffVContainer;
//    protected ReadOnlyView cashWriteOffView;
//
//    protected UIRow row15;
//
//    protected UIBlock cashIncomeFromFeeBlock;
//    protected UIContainer cashIncomeFromFeeVContainer;
//    protected ReadOnlyView cashIncomeFromFeeView;
//
//    protected UIBlock cashIncomeFromPenaltyBlock;
//    protected UIContainer cashIncomeFromPenaltyVContainer;
//    protected ReadOnlyView cashIncomeFromPenaltyView;
//
//    protected UIBlock cashOverdraftInterestIncomeBlock;
//    protected UIContainer cashOverdraftInterestIncomeVContainer;
//    protected ReadOnlyView cashOverdraftInterestIncomeView;
//
//    protected UIRow row16;
//
//    protected UIBlock cashEscheatLiabilityBlock;
//    protected UIContainer cashEscheatLiabilityVContainer;
//    protected ReadOnlyView cashEscheatLiabilityView;
//
//    protected UIBlock row16Block1;
//
//    // Advanced Accounting Rule
//
//    protected WebMarkupContainer advancedAccountingRuleMaster;
//
//    protected UIRow row17;
//
//    protected UIBlock row17Block1;
//
//    protected UIContainer advancedAccountingRuleFundSourceVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
//    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;
//
//    protected UIContainer advancedAccountingRuleFeeIncomeVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
//    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;
//
//    protected UIContainer advancedAccountingRulePenaltyIncomeVContainer;
//    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
//    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;
//
//    public PreviewPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
//        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
//        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
//        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
//        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
//        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
//
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//
//        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
//        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
//        this.chargeColumn = Lists.newArrayList();
//        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
//
//        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
//        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());
//        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
//
//        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
//        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());
//        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
//
//        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");
//        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());
//        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
//
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
//        this.detailProductNameVContainer = this.detailProductNameBlock.newUIContainer("detailProductNameVContainer");
//        this.detailProductNameView = new ReadOnlyView("detailProductNameView", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
//        this.detailProductNameVContainer.add(this.detailProductNameView);
//
//        this.detailShortNameBlock = this.row1.newUIBlock("detailShortNameBlock", Size.Six_6);
//        this.detailShortNameVContainer = this.detailShortNameBlock.newUIContainer("detailShortNameVContainer");
//        this.detailShortNameView = new ReadOnlyView("detailShortNameView", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
//        this.detailShortNameVContainer.add(this.detailShortNameView);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.detailDescriptionBlock = this.row2.newUIBlock("detailDescriptionBlock", Size.Twelve_12);
//        this.detailDescriptionVContainer = this.detailDescriptionBlock.newUIContainer("detailDescriptionVContainer");
//        this.detailDescriptionView = new ReadOnlyView("detailDescriptionView", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
//        this.detailDescriptionVContainer.add(this.detailDescriptionView);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.currencyCodeBlock = this.row3.newUIBlock("currencyCodeBlock", Size.Six_6);
//        this.currencyCodeVContainer = this.currencyCodeBlock.newUIContainer("currencyCodeVContainer");
//        this.currencyCodeView = new ReadOnlyView("currencyCodeView", new PropertyModel<>(this.itemPage, "currencyCodeValue"));
//        this.currencyCodeVContainer.add(this.currencyCodeView);
//
//        this.currencyDecimalPlaceBlock = this.row3.newUIBlock("currencyDecimalPlaceBlock", Size.Three_3);
//        this.currencyDecimalPlaceVContainer = this.currencyDecimalPlaceBlock.newUIContainer("currencyDecimalPlaceVContainer");
//        this.currencyDecimalPlaceView = new ReadOnlyView("currencyDecimalPlaceView", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
//        this.currencyDecimalPlaceVContainer.add(this.currencyDecimalPlaceView);
//
//        this.currencyMultipleOfBlock = this.row3.newUIBlock("currencyMultipleOfBlock", Size.Three_3);
//        this.currencyMultipleOfVContainer = this.currencyMultipleOfBlock.newUIContainer("currencyMultipleOfVContainer");
//        this.currencyMultipleOfView = new ReadOnlyView("currencyMultipleOfView", new PropertyModel<>(this.itemPage, "currencyMultipleOfValue"));
//        this.currencyMultipleOfVContainer.add(this.currencyMultipleOfView);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.termNominalAnnualInterestBlock = this.row4.newUIBlock("termNominalAnnualInterestBlock", Size.Four_4);
//        this.termNominalAnnualInterestVContainer = this.termNominalAnnualInterestBlock.newUIContainer("termNominalAnnualInterestVContainer");
//        this.termNominalAnnualInterestView = new ReadOnlyView("termNominalAnnualInterestView", new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue"));
//        this.termNominalAnnualInterestVContainer.add(this.termNominalAnnualInterestView);
//
//        this.termInterestCompoundingPeriodBlock = this.row4.newUIBlock("termInterestCompoundingPeriodBlock", Size.Four_4);
//        this.termInterestCompoundingPeriodVContainer = this.termInterestCompoundingPeriodBlock.newUIContainer("termInterestCompoundingPeriodVContainer");
//        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"));
//        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);
//
//        this.termInterestPostingPeriodBlock = this.row4.newUIBlock("termInterestPostingPeriodBlock", Size.Four_4);
//        this.termInterestPostingPeriodVContainer = this.termInterestPostingPeriodBlock.newUIContainer("termInterestPostingPeriodVContainer");
//        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"));
//        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.termInterestCalculatedUsingBlock = this.row5.newUIBlock("termInterestCalculatedUsingBlock", Size.Six_6);
//        this.termInterestCalculatedUsingVContainer = this.termInterestCalculatedUsingBlock.newUIContainer("termInterestCalculatedUsingVContainer");
//        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"));
//        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);
//
//        this.termDayInYearBlock = this.row5.newUIBlock("termDayInYearBlock", Size.Six_6);
//        this.termDayInYearVContainer = this.termDayInYearBlock.newUIContainer("termDayInYearVContainer");
//        this.termDayInYearView = new ReadOnlyView("termDayInYearView", new PropertyModel<>(this.itemPage, "termDayInYearValue"));
//        this.termDayInYearVContainer.add(this.termDayInYearView);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.settingMinimumOpeningBalanceBlock = this.row6.newUIBlock("settingMinimumOpeningBalanceBlock", Size.Four_4);
//        this.settingMinimumOpeningBalanceVContainer = this.settingMinimumOpeningBalanceBlock.newUIContainer("settingMinimumOpeningBalanceVContainer");
//        this.settingMinimumOpeningBalanceView = new ReadOnlyView("settingMinimumOpeningBalanceView", new PropertyModel<>(this.itemPage, "settingMinimumOpeningBalanceValue"));
//        this.settingMinimumOpeningBalanceVContainer.add(this.settingMinimumOpeningBalanceView);
//
//        this.settingLockInPeriodBlock = this.row6.newUIBlock("settingLockInPeriodBlock", Size.Four_4);
//        this.settingLockInPeriodVContainer = this.settingLockInPeriodBlock.newUIContainer("settingLockInPeriodVContainer");
//        this.settingLockInPeriodView = new ReadOnlyView("settingLockInPeriodView", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
//        this.settingLockInPeriodVContainer.add(this.settingLockInPeriodView);
//
//        this.settingLockInTypeBlock = this.row6.newUIBlock("settingLockInTypeBlock", Size.Four_4);
//        this.settingLockInTypeVContainer = this.settingLockInTypeBlock.newUIContainer("settingLockInTypeVContainer");
//        this.settingLockInTypeView = new ReadOnlyView("settingLockInTypeView", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"));
//        this.settingLockInTypeVContainer.add(this.settingLockInTypeView);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.settingApplyWithdrawalFeeForTransferBlock = this.row7.newUIBlock("settingApplyWithdrawalFeeForTransferBlock", Size.Six_6);
//        this.settingApplyWithdrawalFeeForTransferVContainer = this.settingApplyWithdrawalFeeForTransferBlock.newUIContainer("settingApplyWithdrawalFeeForTransferVContainer");
//        this.settingApplyWithdrawalFeeForTransferView = new ReadOnlyView("settingApplyWithdrawalFeeForTransferView", new PropertyModel<>(this.itemPage, "settingApplyWithdrawalFeeForTransferValue"));
//        this.settingApplyWithdrawalFeeForTransferVContainer.add(this.settingApplyWithdrawalFeeForTransferView);
//
//        this.settingEnforceMinimumBalanceBlock = this.row7.newUIBlock("settingEnforceMinimumBalanceBlock", Size.Six_6);
//        this.settingEnforceMinimumBalanceVContainer = this.settingEnforceMinimumBalanceBlock.newUIContainer("settingEnforceMinimumBalanceVContainer");
//        this.settingEnforceMinimumBalanceView = new ReadOnlyView("settingEnforceMinimumBalanceView", new PropertyModel<>(this.itemPage, "settingEnforceMinimumBalanceValue"));
//        this.settingEnforceMinimumBalanceVContainer.add(this.settingEnforceMinimumBalanceView);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.settingBalanceRequiredForInterestCalculationBlock = this.row8.newUIBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Six_6);
//        this.settingBalanceRequiredForInterestCalculationVContainer = this.settingBalanceRequiredForInterestCalculationBlock.newUIContainer("settingBalanceRequiredForInterestCalculationVContainer");
//        this.settingBalanceRequiredForInterestCalculationView = new ReadOnlyView("settingBalanceRequiredForInterestCalculationView", new PropertyModel<>(this.itemPage, "settingBalanceRequiredForInterestCalculationValue"));
//        this.settingBalanceRequiredForInterestCalculationVContainer.add(this.settingBalanceRequiredForInterestCalculationView);
//
//        this.settingMinimumBalanceBlock = this.row8.newUIBlock("settingMinimumBalanceBlock", Size.Six_6);
//        this.settingMinimumBalanceVContainer = this.settingMinimumBalanceBlock.newUIContainer("settingMinimumBalanceVContainer");
//        this.settingMinimumBalanceView = new ReadOnlyView("settingMinimumBalanceView", new PropertyModel<>(this.itemPage, "settingMinimumBalanceValue"));
//        this.settingMinimumBalanceVContainer.add(this.settingMinimumBalanceView);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.settingOverdraftAllowedBlock = this.row9.newUIBlock("settingOverdraftAllowedBlock", Size.Three_3);
//        this.settingOverdraftAllowedVContainer = this.settingOverdraftAllowedBlock.newUIContainer("settingOverdraftAllowedVContainer");
//        this.settingOverdraftAllowedView = new ReadOnlyView("settingOverdraftAllowedView", new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue"));
//        this.settingOverdraftAllowedVContainer.add(this.settingOverdraftAllowedView);
//
//        this.settingMaximumOverdraftAmountLimitBlock = this.row9.newUIBlock("settingMaximumOverdraftAmountLimitBlock", Size.Three_3);
//        this.settingMaximumOverdraftAmountLimitVContainer = this.settingMaximumOverdraftAmountLimitBlock.newUIContainer("settingMaximumOverdraftAmountLimitVContainer");
//        this.settingMaximumOverdraftAmountLimitView = new ReadOnlyView("settingMaximumOverdraftAmountLimitView", new PropertyModel<>(this.itemPage, "settingMaximumOverdraftAmountLimitValue"));
//        this.settingMaximumOverdraftAmountLimitVContainer.add(this.settingMaximumOverdraftAmountLimitView);
//
//        this.settingNominalAnnualInterestForOverdraftBlock = this.row9.newUIBlock("settingNominalAnnualInterestForOverdraftBlock", Size.Three_3);
//        this.settingNominalAnnualInterestForOverdraftVContainer = this.settingNominalAnnualInterestForOverdraftBlock.newUIContainer("settingNominalAnnualInterestForOverdraftVContainer");
//        this.settingNominalAnnualInterestForOverdraftView = new ReadOnlyView("settingNominalAnnualInterestForOverdraftView", new PropertyModel<>(this.itemPage, "settingNominalAnnualInterestForOverdraftValue"));
//        this.settingNominalAnnualInterestForOverdraftVContainer.add(this.settingNominalAnnualInterestForOverdraftView);
//
//        this.settingMinOverdraftRequiredForInterestCalculationBlock = this.row9.newUIBlock("settingMinOverdraftRequiredForInterestCalculationBlock", Size.Three_3);
//        this.settingMinOverdraftRequiredForInterestCalculationVContainer = this.settingMinOverdraftRequiredForInterestCalculationBlock.newUIContainer("settingMinOverdraftRequiredForInterestCalculationVContainer");
//        this.settingMinOverdraftRequiredForInterestCalculationView = new ReadOnlyView("settingMinOverdraftRequiredForInterestCalculationView", new PropertyModel<>(this.itemPage, "settingMinOverdraftRequiredForInterestCalculationValue"));
//        this.settingMinOverdraftRequiredForInterestCalculationVContainer.add(this.settingMinOverdraftRequiredForInterestCalculationView);
//
//        this.row10 = UIRow.newUIRow("row10", this.form);
//
//        this.settingWithholdTaxApplicableBlock = this.row10.newUIBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
//        this.settingWithholdTaxApplicableVContainer = this.settingWithholdTaxApplicableBlock.newUIContainer("settingWithholdTaxApplicableVContainer");
//        this.settingWithholdTaxApplicableView = new ReadOnlyView("settingWithholdTaxApplicableView", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
//        this.settingWithholdTaxApplicableVContainer.add(this.settingWithholdTaxApplicableView);
//
//        this.settingTaxGroupBlock = this.row10.newUIBlock("settingTaxGroupBlock", Size.Six_6);
//        this.settingTaxGroupVContainer = this.settingTaxGroupBlock.newUIContainer("settingTaxGroupVContainer");
//        this.settingTaxGroupView = new ReadOnlyView("settingTaxGroupView", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"));
//        this.settingTaxGroupVContainer.add(this.settingTaxGroupView);
//
//        this.row11 = UIRow.newUIRow("row11", this.form);
//
//        this.settingEnableDormancyTrackingBlock = this.row11.newUIBlock("settingEnableDormancyTrackingBlock", Size.Three_3);
//        this.settingEnableDormancyTrackingVContainer = this.settingEnableDormancyTrackingBlock.newUIContainer("settingEnableDormancyTrackingVContainer");
//        this.settingEnableDormancyTrackingView = new ReadOnlyView("settingEnableDormancyTrackingView", new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue"));
//        this.settingEnableDormancyTrackingVContainer.add(this.settingEnableDormancyTrackingView);
//
//        this.settingNumberOfDaysToInactiveSubStatusBlock = this.row11.newUIBlock("settingNumberOfDaysToInactiveSubStatusBlock", Size.Three_3);
//        this.settingNumberOfDaysToInactiveSubStatusVContainer = this.settingNumberOfDaysToInactiveSubStatusBlock.newUIContainer("settingNumberOfDaysToInactiveSubStatusVContainer");
//        this.settingNumberOfDaysToInactiveSubStatusView = new ReadOnlyView("settingNumberOfDaysToInactiveSubStatusView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToInactiveSubStatusValue"));
//        this.settingNumberOfDaysToInactiveSubStatusVContainer.add(this.settingNumberOfDaysToInactiveSubStatusView);
//
//        this.settingNumberOfDaysToDormantSubStatusBlock = this.row11.newUIBlock("settingNumberOfDaysToDormantSubStatusBlock", Size.Three_3);
//        this.settingNumberOfDaysToDormantSubStatusVContainer = this.settingNumberOfDaysToDormantSubStatusBlock.newUIContainer("settingNumberOfDaysToDormantSubStatusVContainer");
//        this.settingNumberOfDaysToDormantSubStatusView = new ReadOnlyView("settingNumberOfDaysToDormantSubStatusView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToDormantSubStatusValue"));
//        this.settingNumberOfDaysToDormantSubStatusVContainer.add(this.settingNumberOfDaysToDormantSubStatusView);
//
//        this.settingNumberOfDaysToEscheatBlock = this.row11.newUIBlock("settingNumberOfDaysToEscheatBlock", Size.Three_3);
//        this.settingNumberOfDaysToEscheatVContainer = this.settingNumberOfDaysToEscheatBlock.newUIContainer("settingNumberOfDaysToEscheatVContainer");
//        this.settingNumberOfDaysToEscheatView = new ReadOnlyView("settingNumberOfDaysToEscheatView", new PropertyModel<>(this.itemPage, "settingNumberOfDaysToEscheatValue"));
//        this.settingNumberOfDaysToEscheatVContainer.add(this.settingNumberOfDaysToEscheatView);
//
//        this.row12 = UIRow.newUIRow("row12", this.form);
//
//        this.chargeBlock = this.row12.newUIBlock("chargeBlock", Size.Twelve_12);
//        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
//        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, this.chargeValue.getObject().size() + 1);
//        this.chargeVContainer.add(this.chargeTable);
//        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
//        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
//
//        this.accountingLabel = new Label("accountingLabel", new PropertyModel<>(this.itemPage, "accountingValue"));
//        this.form.add(this.accountingLabel);
//
//        this.cashMaster = new WebMarkupContainer("cashMaster");
//        this.cashMaster.setOutputMarkupId(true);
//        this.form.add(this.cashMaster);
//
//        this.row13 = UIRow.newUIRow("row13", this.cashMaster);
//
//        this.cashSavingReferenceBlock = this.row13.newUIBlock("cashSavingReferenceBlock", Size.Four_4);
//        this.cashSavingReferenceVContainer = this.cashSavingReferenceBlock.newUIContainer("cashSavingReferenceVContainer");
//        this.cashSavingReferenceView = new ReadOnlyView("cashSavingReferenceView", new PropertyModel<String>(this.itemPage, "cashSavingReferenceValue"));
//        this.cashSavingReferenceVContainer.add(this.cashSavingReferenceView);
//
//        this.cashOverdraftPortfolioBlock = this.row13.newUIBlock("cashOverdraftPortfolioBlock", Size.Four_4);
//        this.cashOverdraftPortfolioVContainer = this.cashOverdraftPortfolioBlock.newUIContainer("cashOverdraftPortfolioVContainer");
//        this.cashOverdraftPortfolioView = new ReadOnlyView("cashOverdraftPortfolioView", new PropertyModel<String>(this.itemPage, "cashOverdraftPortfolioValue"));
//        this.cashOverdraftPortfolioVContainer.add(this.cashOverdraftPortfolioView);
//
//        this.cashSavingControlBlock = this.row13.newUIBlock("cashSavingControlBlock", Size.Four_4);
//        this.cashSavingControlVContainer = this.cashSavingControlBlock.newUIContainer("cashSavingControlVContainer");
//        this.cashSavingControlView = new ReadOnlyView("cashSavingControlView", new PropertyModel<String>(this.itemPage, "cashSavingControlValue"));
//        this.cashSavingControlVContainer.add(this.cashSavingControlView);
//
//        this.row14 = UIRow.newUIRow("row14", this.cashMaster);
//
//        this.cashSavingTransferInSuspenseBlock = this.row14.newUIBlock("cashSavingTransferInSuspenseBlock", Size.Four_4);
//        this.cashSavingTransferInSuspenseVContainer = this.cashSavingTransferInSuspenseBlock.newUIContainer("cashSavingTransferInSuspenseVContainer");
//        this.cashSavingTransferInSuspenseView = new ReadOnlyView("cashSavingTransferInSuspenseView", new PropertyModel<String>(this.itemPage, "cashSavingTransferInSuspenseValue"));
//        this.cashSavingTransferInSuspenseVContainer.add(this.cashSavingTransferInSuspenseView);
//
//        this.cashInterestOnSavingBlock = this.row14.newUIBlock("cashInterestOnSavingBlock", Size.Four_4);
//        this.cashInterestOnSavingVContainer = this.cashInterestOnSavingBlock.newUIContainer("cashInterestOnSavingVContainer");
//        this.cashInterestOnSavingView = new ReadOnlyView("cashInterestOnSavingView", new PropertyModel<String>(this.itemPage, "cashInterestOnSavingValue"));
//        this.cashInterestOnSavingVContainer.add(this.cashInterestOnSavingView);
//
//        this.cashWriteOffBlock = this.row14.newUIBlock("cashWriteOffBlock", Size.Four_4);
//        this.cashWriteOffVContainer = this.cashWriteOffBlock.newUIContainer("cashWriteOffVContainer");
//        this.cashWriteOffView = new ReadOnlyView("cashWriteOffView", new PropertyModel<String>(this.itemPage, "cashWriteOffValue"));
//        this.cashWriteOffVContainer.add(this.cashWriteOffView);
//
//        this.row15 = UIRow.newUIRow("row15", this.cashMaster);
//
//        this.cashIncomeFromFeeBlock = this.row15.newUIBlock("cashIncomeFromFeeBlock", Size.Four_4);
//        this.cashIncomeFromFeeVContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeVContainer");
//        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<String>(this.itemPage, "cashIncomeFromFeeValue"));
//        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
//
//        this.cashIncomeFromPenaltyBlock = this.row15.newUIBlock("cashIncomeFromPenaltyBlock", Size.Four_4);
//        this.cashIncomeFromPenaltyVContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyVContainer");
//        this.cashIncomeFromPenaltyView = new ReadOnlyView("cashIncomeFromPenaltyView", new PropertyModel<String>(this.itemPage, "cashIncomeFromPenaltyValue"));
//        this.cashIncomeFromPenaltyVContainer.add(this.cashIncomeFromPenaltyView);
//
//        this.cashOverdraftInterestIncomeBlock = this.row15.newUIBlock("cashOverdraftInterestIncomeBlock", Size.Four_4);
//        this.cashOverdraftInterestIncomeVContainer = this.cashOverdraftInterestIncomeBlock.newUIContainer("cashOverdraftInterestIncomeVContainer");
//        this.cashOverdraftInterestIncomeView = new ReadOnlyView("cashOverdraftInterestIncomeView", new PropertyModel<String>(this.itemPage, "cashOverdraftInterestIncomeValue"));
//        this.cashOverdraftInterestIncomeVContainer.add(this.cashOverdraftInterestIncomeView);
//
//        this.row16 = UIRow.newUIRow("row16", this.cashMaster);
//
//        this.cashEscheatLiabilityBlock = this.row16.newUIBlock("cashEscheatLiabilityBlock", Size.Four_4);
//        this.cashEscheatLiabilityVContainer = this.cashEscheatLiabilityBlock.newUIContainer("cashEscheatLiabilityVContainer");
//        this.cashEscheatLiabilityView = new ReadOnlyView("cashEscheatLiabilityView", new PropertyModel<String>(this.itemPage, "cashEscheatLiabilityValue"));
//        this.cashEscheatLiabilityVContainer.add(this.cashEscheatLiabilityView);
//
//        this.row16Block1 = this.row16.newUIBlock("row16Block1", Size.Eight_8);
//
//        this.advancedAccountingRuleMaster = new WebMarkupContainer("advancedAccountingRuleMaster");
//        this.advancedAccountingRuleMaster.setOutputMarkupId(true);
//        this.form.add(this.advancedAccountingRuleMaster);
//
//        this.row17 = UIRow.newUIRow("row17", this.advancedAccountingRuleMaster);
//
//        this.row17Block1 = this.row17.newUIBlock("row17Block1", Size.Twelve_12);
//
//        this.advancedAccountingRuleFundSourceVContainer = this.row17Block1.newUIContainer("advancedAccountingRuleFundSourceVContainer");
//        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, this.advancedAccountingRuleFundSourceValue.getObject().size() + 1);
//        this.advancedAccountingRuleFundSourceVContainer.add(this.advancedAccountingRuleFundSourceTable);
//        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
//        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
//
//        this.advancedAccountingRuleFeeIncomeVContainer = this.row17Block1.newUIContainer("advancedAccountingRuleFeeIncomeVContainer");
//        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, this.advancedAccountingRuleFeeIncomeValue.getObject().size() + 1);
//        this.advancedAccountingRuleFeeIncomeVContainer.add(this.advancedAccountingRuleFeeIncomeTable);
//        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
//        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
//
//        this.advancedAccountingRulePenaltyIncomeVContainer = this.row17Block1.newUIContainer("advancedAccountingRulePenaltyIncomeVContainer");
//        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, this.advancedAccountingRulePenaltyIncomeValue.getObject().size() + 1);
//        this.advancedAccountingRulePenaltyIncomeVContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
//        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
//        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
//        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
//            this.cashMaster.setVisible(false);
//            this.advancedAccountingRuleMaster.setVisible(false);
//        }
//
//        PropertyModel<Boolean> settingWithholdTaxApplicableValue = new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue");
//        this.settingTaxGroupVContainer.setVisible(settingWithholdTaxApplicableValue.getObject() != null && settingWithholdTaxApplicableValue.getObject());
//
//        PropertyModel<Boolean> settingEnableDormancyTrackingValue = new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue");
//        this.settingNumberOfDaysToEscheatVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
//        this.settingNumberOfDaysToDormantSubStatusVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
//        this.settingNumberOfDaysToInactiveSubStatusVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
//        this.cashEscheatLiabilityVContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
//
//        PropertyModel<Boolean> settingOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "settingOverdraftAllowedValue");
//        this.settingMaximumOverdraftAmountLimitVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());
//        this.settingNominalAnnualInterestForOverdraftVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());
//        this.settingMinOverdraftRequiredForInterestCalculationVContainer.setVisible(settingOverdraftAllowedValue.getObject() != null && settingOverdraftAllowedValue.getObject());
//
//        this.saveButton.setVisible(!this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
//    }
//
//    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("charge".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("payment".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column) || "date".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("type".equals(column) || "collect".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("amount".equals(column)) {
//            Number value = (Number) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("charge".equals(column) || "account".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_ACCOUNTING);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        if (this.itemPage instanceof SavingCreatePage) {
//            ((SavingCreatePage) this.itemPage).saveButtonSubmit(button);
//        }
//    }
//
//}
