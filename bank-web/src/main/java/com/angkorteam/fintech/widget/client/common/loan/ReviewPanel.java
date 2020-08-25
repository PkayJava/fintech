//package com.angkorteam.fintech.widget.client.common.loan;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.enums.ChargeTime;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//
//public class ReviewPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//
//    protected ClientEnum client;
//    protected String clientId;
//
//    protected PropertyModel<Boolean> errorDetail;
//    protected PropertyModel<Boolean> errorCharge;
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
//    protected PropertyModel<String> detailProductNameValue;
//
//    protected UIBlock detailSubmittedOnBlock;
//    protected UIContainer detailSubmittedOnVContainer;
//    protected ReadOnlyView detailSubmittedOnView;
//    protected PropertyModel<Date> detailSubmittedOnValue;
//
//    protected UIRow row2;
//
//    protected UIBlock detailOfficerBlock;
//    protected UIContainer detailOfficerVContainer;
//    protected ReadOnlyView detailOfficerView;
//    protected PropertyModel<Option> detailOfficerValue;
//
//    protected UIBlock detailExternalIdBlock;
//    protected UIContainer detailExternalIdVContainer;
//    protected ReadOnlyView detailExternalIdView;
//    protected PropertyModel<String> detailExternalIdValue;
//
//    protected UIRow row3;
//
//    protected UIBlock termCurrencyBlock;
//    protected UIContainer termCurrencyVContainer;
//    protected ReadOnlyView termCurrencyView;
//    protected PropertyModel<String> termCurrencyValue;
//
//    protected UIBlock termDecimalPlacesBlock;
//    protected UIContainer termDecimalPlacesVContainer;
//    protected ReadOnlyView termDecimalPlacesView;
//    protected PropertyModel<Long> termDecimalPlacesValue;
//
//    protected UIRow row4;
//
//    protected UIBlock termNominalAnnualInterestBlock;
//    protected UIContainer termNominalAnnualInterestVContainer;
//    protected ReadOnlyView termNominalAnnualInterestView;
//    protected PropertyModel<Double> termNominalAnnualInterestValue;
//
//    protected UIBlock termInterestCompoundingPeriodBlock;
//    protected UIContainer termInterestCompoundingPeriodVContainer;
//    protected ReadOnlyView termInterestCompoundingPeriodView;
//    protected PropertyModel<Option> termInterestCompoundingPeriodValue;
//
//    protected UIRow row5;
//
//    protected UIBlock termCurrencyInMultiplesOfBlock;
//    protected UIContainer termCurrencyInMultiplesOfVContainer;
//    protected ReadOnlyView termCurrencyInMultiplesOfView;
//    protected PropertyModel<Long> termCurrencyInMultiplesOfValue;
//
//    protected UIBlock termInterestPostingPeriodBlock;
//    protected UIContainer termInterestPostingPeriodVContainer;
//    protected ReadOnlyView termInterestPostingPeriodView;
//    protected PropertyModel<Option> termInterestPostingPeriodValue;
//
//    protected UIRow row6;
//
//    protected UIBlock termInterestCalculatedUsingBlock;
//    protected UIContainer termInterestCalculatedUsingVContainer;
//    protected ReadOnlyView termInterestCalculatedUsingView;
//    protected PropertyModel<Option> termInterestCalculatedUsingValue;
//
//    protected UIBlock termDayInYearBlock;
//    protected UIContainer termDayInYearVContainer;
//    protected ReadOnlyView termDayInYearView;
//    protected PropertyModel<Option> termDayInYearValue;
//
//    protected UIRow row7;
//
//    protected UIBlock termMinimumOpeningBalanceBlock;
//    protected UIContainer termMinimumOpeningBalanceVContainer;
//    protected ReadOnlyView termMinimumOpeningBalanceView;
//    protected PropertyModel<Double> termMinimumOpeningBalanceValue;
//
//    protected UIBlock termLockInPeriodBlock;
//    protected UIContainer termLockInPeriodVContainer;
//    protected ReadOnlyView termLockInPeriodView;
//    protected PropertyModel<Long> termLockInPeriodValue;
//
//    protected UIBlock termLockInTypeBlock;
//    protected UIContainer termLockInTypeVContainer;
//    protected ReadOnlyView termLockInTypeView;
//    protected PropertyModel<Option> termLockInTypeValue;
//
//    protected UIRow row8;
//
//    protected UIBlock termApplyWithdrawalFeeForTransferBlock;
//    protected UIContainer termApplyWithdrawalFeeForTransferVContainer;
//    protected ReadOnlyView termApplyWithdrawalFeeForTransferView;
//    protected PropertyModel<Boolean> termApplyWithdrawalFeeForTransferValue;
//
//    protected UIBlock termOverdraftAllowedBlock;
//    protected UIContainer termOverdraftAllowedVContainer;
//    protected ReadOnlyView termOverdraftAllowedView;
//    protected PropertyModel<Boolean> termOverdraftAllowedValue;
//
//    protected UIRow row9;
//
//    protected UIBlock termMaximumOverdraftAmountLimitBlock;
//    protected UIContainer termMaximumOverdraftAmountLimitVContainer;
//    protected ReadOnlyView termMaximumOverdraftAmountLimitView;
//    protected PropertyModel<Double> termMaximumOverdraftAmountLimitValue;
//
//    protected UIBlock termNominalAnnualInterestForOverdraftBlock;
//    protected UIContainer termNominalAnnualInterestForOverdraftVContainer;
//    protected ReadOnlyView termNominalAnnualInterestForOverdraftView;
//    protected PropertyModel<Double> termNominalAnnualInterestForOverdraftValue;
//
//    protected UIBlock termMinOverdraftRequiredForInterestCalculationBlock;
//    protected UIContainer termMinOverdraftRequiredForInterestCalculationVContainer;
//    protected ReadOnlyView termMinOverdraftRequiredForInterestCalculationView;
//    protected PropertyModel<Double> termMinOverdraftRequiredForInterestCalculationValue;
//
//    protected UIRow row10;
//
//    protected UIBlock termEnforceMinimumBalanceBlock;
//    protected UIContainer termEnforceMinimumBalanceVContainer;
//    protected ReadOnlyView termEnforceMinimumBalanceView;
//    protected PropertyModel<Boolean> termEnforceMinimumBalanceValue;
//
//    protected UIBlock termMinimumBalanceBlock;
//    protected UIContainer termMinimumBalanceVContainer;
//    protected ReadOnlyView termMinimumBalanceView;
//    protected PropertyModel<Double> termMinimumBalanceValue;
//
//    protected UIRow row11;
//
//    protected UIBlock termBalanceRequiredForInterestCalculationBlock;
//    protected UIContainer termBalanceRequiredForInterestCalculationVContainer;
//    protected ReadOnlyView termBalanceRequiredForInterestCalculationView;
//    protected PropertyModel<Double> termBalanceRequiredForInterestCalculationValue;
//
//    protected UIBlock row11Block1;
//
//    protected UIRow row12;
//
//    protected UIBlock termWithHoldBlock;
//    protected UIContainer termWithHoldVContainer;
//    protected ReadOnlyView termWithHoldView;
//    protected PropertyModel<Boolean> termWithHoldValue;
//
//    protected UIBlock termTaxGroupBlock;
//    protected UIContainer termTaxGroupVContainer;
//    protected ReadOnlyView termTaxGroupView;
//    protected PropertyModel<String> termTaxGroupValue;
//
//    protected UIRow row13;
//
//    protected UIBlock chargeBlock;
//    protected UIContainer chargeVContainer;
//    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
//    protected PropertyModel<List<Map<String, Object>>> chargeValue;
//    protected DataTable<Map<String, Object>, String> chargeTable;
//    protected ListDataProvider chargeProvider;
//
//    public ReviewPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
//        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
//        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
//
//        this.client = new PropertyModel<ClientEnum>(this.itemPage, "client").getObject();
//        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
//
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//
//        // Details
//
//        this.detailProductNameValue = new PropertyModel<>(this.itemPage, "detailProductNameValue");
//        this.detailSubmittedOnValue = new PropertyModel<>(this.itemPage, "detailSubmittedOnValue");
//        this.detailOfficerValue = new PropertyModel<>(this.itemPage, "detailOfficerValue");
//        this.detailExternalIdValue = new PropertyModel<>(this.itemPage, "detailExternalIdValue");
//
//        // Terms
//
//        this.termCurrencyValue = new PropertyModel<>(this.itemPage, "termCurrencyValue");
//        this.termDecimalPlacesValue = new PropertyModel<>(this.itemPage, "termDecimalPlacesValue");
//        this.termNominalAnnualInterestValue = new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue");
//        this.termInterestCompoundingPeriodValue = new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue");
//        this.termCurrencyInMultiplesOfValue = new PropertyModel<>(this.itemPage, "termCurrencyInMultiplesOfValue");
//        this.termInterestPostingPeriodValue = new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue");
//        this.termInterestCalculatedUsingValue = new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue");
//        this.termDayInYearValue = new PropertyModel<>(this.itemPage, "termDayInYearValue");
//        this.termMinimumOpeningBalanceValue = new PropertyModel<>(this.itemPage, "termMinimumOpeningBalanceValue");
//        this.termLockInPeriodValue = new PropertyModel<>(this.itemPage, "termLockInPeriodValue");
//        this.termLockInTypeValue = new PropertyModel<>(this.itemPage, "termLockInTypeValue");
//        this.termApplyWithdrawalFeeForTransferValue = new PropertyModel<>(this.itemPage, "termApplyWithdrawalFeeForTransferValue");
//        this.termOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "termOverdraftAllowedValue");
//        this.termMaximumOverdraftAmountLimitValue = new PropertyModel<>(this.itemPage, "termMaximumOverdraftAmountLimitValue");
//        this.termNominalAnnualInterestForOverdraftValue = new PropertyModel<>(this.itemPage, "termNominalAnnualInterestForOverdraftValue");
//        this.termMinOverdraftRequiredForInterestCalculationValue = new PropertyModel<>(this.itemPage, "termMinOverdraftRequiredForInterestCalculationValue");
//        this.termEnforceMinimumBalanceValue = new PropertyModel<>(this.itemPage, "termEnforceMinimumBalanceValue");
//        this.termMinimumBalanceValue = new PropertyModel<>(this.itemPage, "termMinimumBalanceValue");
//        this.termBalanceRequiredForInterestCalculationValue = new PropertyModel<>(this.itemPage, "termBalanceRequiredForInterestCalculationValue");
//        this.termWithHoldValue = new PropertyModel<>(this.itemPage, "termWithHoldValue");
//        this.termTaxGroupValue = new PropertyModel<>(this.itemPage, "termTaxGroupValue");
//
//        // Charges
//
//        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
//        this.chargeColumn = Lists.newLinkedList();
//        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collectedOn", "collectedOn", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
//        this.chargeColumn.add(new TextColumn(Model.of("Repayments Every"), "repaymentEvery", "repaymentEvery", this::chargeColumn));
//        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
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
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        if (this.client == ClientEnum.Client) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
//        }
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
//        this.detailProductNameVContainer = this.detailProductNameBlock.newUIContainer("detailProductNameVContainer");
//        this.detailProductNameView = new ReadOnlyView("detailProductNameView", this.detailProductNameValue);
//        this.detailProductNameVContainer.add(this.detailProductNameView);
//
//        this.detailSubmittedOnBlock = this.row1.newUIBlock("detailSubmittedOnBlock", Size.Six_6);
//        this.detailSubmittedOnVContainer = this.detailSubmittedOnBlock.newUIContainer("detailSubmittedOnVContainer");
//        this.detailSubmittedOnView = new ReadOnlyView("detailSubmittedOnView", this.detailSubmittedOnValue, "dd MMM yyyy");
//        this.detailSubmittedOnVContainer.add(this.detailSubmittedOnView);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.detailOfficerBlock = this.row2.newUIBlock("detailOfficerBlock", Size.Six_6);
//        this.detailOfficerVContainer = this.detailOfficerBlock.newUIContainer("detailOfficerVContainer");
//        this.detailOfficerView = new ReadOnlyView("detailOfficerView", this.detailOfficerValue);
//        this.detailOfficerVContainer.add(this.detailOfficerView);
//
//        this.detailExternalIdBlock = this.row2.newUIBlock("detailExternalIdBlock", Size.Six_6);
//        this.detailExternalIdVContainer = this.detailExternalIdBlock.newUIContainer("detailExternalIdVContainer");
//        this.detailExternalIdView = new ReadOnlyView("detailExternalIdView", this.detailExternalIdValue);
//        this.detailExternalIdVContainer.add(this.detailExternalIdView);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.termCurrencyBlock = this.row3.newUIBlock("termCurrencyBlock", Size.Six_6);
//        this.termCurrencyVContainer = this.termCurrencyBlock.newUIContainer("termCurrencyVContainer");
//        this.termCurrencyView = new ReadOnlyView("termCurrencyView", this.termCurrencyValue);
//        this.termCurrencyVContainer.add(this.termCurrencyView);
//
//        this.termDecimalPlacesBlock = this.row3.newUIBlock("termDecimalPlacesBlock", Size.Six_6);
//        this.termDecimalPlacesVContainer = this.termDecimalPlacesBlock.newUIContainer("termDecimalPlacesVContainer");
//        this.termDecimalPlacesView = new ReadOnlyView("termDecimalPlacesView", this.termDecimalPlacesValue);
//        this.termDecimalPlacesVContainer.add(this.termDecimalPlacesView);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.termNominalAnnualInterestBlock = this.row4.newUIBlock("termNominalAnnualInterestBlock", Size.Six_6);
//        this.termNominalAnnualInterestVContainer = this.termNominalAnnualInterestBlock.newUIContainer("termNominalAnnualInterestVContainer");
//        this.termNominalAnnualInterestView = new ReadOnlyView("termNominalAnnualInterestView", this.termNominalAnnualInterestValue);
//        this.termNominalAnnualInterestVContainer.add(this.termNominalAnnualInterestView);
//
//        this.termInterestCompoundingPeriodBlock = this.row4.newUIBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
//        this.termInterestCompoundingPeriodVContainer = this.termInterestCompoundingPeriodBlock.newUIContainer("termInterestCompoundingPeriodVContainer");
//        this.termInterestCompoundingPeriodView = new ReadOnlyView("termInterestCompoundingPeriodView", this.termInterestCompoundingPeriodValue);
//        this.termInterestCompoundingPeriodVContainer.add(this.termInterestCompoundingPeriodView);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.termCurrencyInMultiplesOfBlock = this.row5.newUIBlock("termCurrencyInMultiplesOfBlock", Size.Six_6);
//        this.termCurrencyInMultiplesOfVContainer = this.termCurrencyInMultiplesOfBlock.newUIContainer("termCurrencyInMultiplesOfVContainer");
//        this.termCurrencyInMultiplesOfView = new ReadOnlyView("termCurrencyInMultiplesOfView", this.termCurrencyInMultiplesOfValue);
//        this.termCurrencyInMultiplesOfVContainer.add(this.termCurrencyInMultiplesOfView);
//
//        this.termInterestPostingPeriodBlock = this.row5.newUIBlock("termInterestPostingPeriodBlock", Size.Six_6);
//        this.termInterestPostingPeriodVContainer = this.termInterestPostingPeriodBlock.newUIContainer("termInterestPostingPeriodVContainer");
//        this.termInterestPostingPeriodView = new ReadOnlyView("termInterestPostingPeriodView", this.termInterestPostingPeriodValue);
//        this.termInterestPostingPeriodVContainer.add(this.termInterestPostingPeriodView);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.termInterestCalculatedUsingBlock = this.row6.newUIBlock("termInterestCalculatedUsingBlock", Size.Six_6);
//        this.termInterestCalculatedUsingVContainer = this.termInterestCalculatedUsingBlock.newUIContainer("termInterestCalculatedUsingVContainer");
//        this.termInterestCalculatedUsingView = new ReadOnlyView("termInterestCalculatedUsingView", this.termInterestCalculatedUsingValue);
//        this.termInterestCalculatedUsingVContainer.add(this.termInterestCalculatedUsingView);
//
//        this.termDayInYearBlock = this.row6.newUIBlock("termDayInYearBlock", Size.Six_6);
//        this.termDayInYearVContainer = this.termDayInYearBlock.newUIContainer("termDayInYearVContainer");
//        this.termDayInYearView = new ReadOnlyView("termDayInYearView", this.termDayInYearValue);
//        this.termDayInYearVContainer.add(this.termDayInYearView);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.termMinimumOpeningBalanceBlock = this.row7.newUIBlock("termMinimumOpeningBalanceBlock", Size.Four_4);
//        this.termMinimumOpeningBalanceVContainer = this.termMinimumOpeningBalanceBlock.newUIContainer("termMinimumOpeningBalanceVContainer");
//        this.termMinimumOpeningBalanceView = new ReadOnlyView("termMinimumOpeningBalanceView", this.termMinimumOpeningBalanceValue);
//        this.termMinimumOpeningBalanceVContainer.add(this.termMinimumOpeningBalanceView);
//
//        this.termLockInPeriodBlock = this.row7.newUIBlock("termLockInPeriodBlock", Size.Four_4);
//        this.termLockInPeriodVContainer = this.termLockInPeriodBlock.newUIContainer("termLockInPeriodVContainer");
//        this.termLockInPeriodView = new ReadOnlyView("termLockInPeriodView", this.termLockInPeriodValue);
//        this.termLockInPeriodVContainer.add(this.termLockInPeriodView);
//
//        this.termLockInTypeBlock = this.row7.newUIBlock("termLockInTypeBlock", Size.Four_4);
//        this.termLockInTypeVContainer = this.termLockInTypeBlock.newUIContainer("termLockInTypeVContainer");
//        this.termLockInTypeView = new ReadOnlyView("termLockInTypeView", this.termLockInTypeValue);
//        this.termLockInTypeVContainer.add(this.termLockInTypeView);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.termApplyWithdrawalFeeForTransferBlock = this.row8.newUIBlock("termApplyWithdrawalFeeForTransferBlock", Size.Six_6);
//        this.termApplyWithdrawalFeeForTransferVContainer = this.termApplyWithdrawalFeeForTransferBlock.newUIContainer("termApplyWithdrawalFeeForTransferVContainer");
//        this.termApplyWithdrawalFeeForTransferView = new ReadOnlyView("termApplyWithdrawalFeeForTransferView", this.termApplyWithdrawalFeeForTransferValue);
//        this.termApplyWithdrawalFeeForTransferVContainer.add(this.termApplyWithdrawalFeeForTransferView);
//
//        this.termOverdraftAllowedBlock = this.row8.newUIBlock("termOverdraftAllowedBlock", Size.Six_6);
//        this.termOverdraftAllowedVContainer = this.termOverdraftAllowedBlock.newUIContainer("termOverdraftAllowedVContainer");
//        this.termOverdraftAllowedView = new ReadOnlyView("termOverdraftAllowedView", this.termOverdraftAllowedValue);
//        this.termOverdraftAllowedVContainer.add(this.termOverdraftAllowedView);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.termMaximumOverdraftAmountLimitBlock = this.row9.newUIBlock("termMaximumOverdraftAmountLimitBlock", Size.Four_4);
//        this.termMaximumOverdraftAmountLimitVContainer = this.termMaximumOverdraftAmountLimitBlock.newUIContainer("termMaximumOverdraftAmountLimitVContainer");
//        this.termMaximumOverdraftAmountLimitView = new ReadOnlyView("termMaximumOverdraftAmountLimitView", this.termMaximumOverdraftAmountLimitValue);
//        this.termMaximumOverdraftAmountLimitVContainer.add(this.termMaximumOverdraftAmountLimitView);
//
//        this.termNominalAnnualInterestForOverdraftBlock = this.row9.newUIBlock("termNominalAnnualInterestForOverdraftBlock", Size.Four_4);
//        this.termNominalAnnualInterestForOverdraftVContainer = this.termNominalAnnualInterestForOverdraftBlock.newUIContainer("termNominalAnnualInterestForOverdraftVContainer");
//        this.termNominalAnnualInterestForOverdraftView = new ReadOnlyView("termNominalAnnualInterestForOverdraftView", this.termNominalAnnualInterestForOverdraftValue);
//        this.termNominalAnnualInterestForOverdraftVContainer.add(this.termNominalAnnualInterestForOverdraftView);
//
//        this.termMinOverdraftRequiredForInterestCalculationBlock = this.row9.newUIBlock("termMinOverdraftRequiredForInterestCalculationBlock", Size.Four_4);
//        this.termMinOverdraftRequiredForInterestCalculationVContainer = this.termMinOverdraftRequiredForInterestCalculationBlock.newUIContainer("termMinOverdraftRequiredForInterestCalculationVContainer");
//        this.termMinOverdraftRequiredForInterestCalculationView = new ReadOnlyView("termMinOverdraftRequiredForInterestCalculationView", this.termMinOverdraftRequiredForInterestCalculationValue);
//        this.termMinOverdraftRequiredForInterestCalculationVContainer.add(this.termMinOverdraftRequiredForInterestCalculationView);
//
//        this.row10 = UIRow.newUIRow("row10", this.form);
//
//        this.termEnforceMinimumBalanceBlock = this.row10.newUIBlock("termEnforceMinimumBalanceBlock", Size.Six_6);
//        this.termEnforceMinimumBalanceVContainer = this.termEnforceMinimumBalanceBlock.newUIContainer("termEnforceMinimumBalanceVContainer");
//        this.termEnforceMinimumBalanceView = new ReadOnlyView("termEnforceMinimumBalanceView", this.termEnforceMinimumBalanceValue);
//        this.termEnforceMinimumBalanceVContainer.add(this.termEnforceMinimumBalanceView);
//
//        this.termMinimumBalanceBlock = this.row10.newUIBlock("termMinimumBalanceBlock", Size.Six_6);
//        this.termMinimumBalanceVContainer = this.termMinimumBalanceBlock.newUIContainer("termMinimumBalanceVContainer");
//        this.termMinimumBalanceView = new ReadOnlyView("termMinimumBalanceView", this.termMinimumBalanceValue);
//        this.termMinimumBalanceVContainer.add(this.termMinimumBalanceView);
//
//        this.row11 = UIRow.newUIRow("row11", this.form);
//
//        this.termBalanceRequiredForInterestCalculationBlock = this.row11.newUIBlock("termBalanceRequiredForInterestCalculationBlock", Size.Six_6);
//        this.termBalanceRequiredForInterestCalculationVContainer = this.termBalanceRequiredForInterestCalculationBlock.newUIContainer("termBalanceRequiredForInterestCalculationVContainer");
//        this.termBalanceRequiredForInterestCalculationView = new ReadOnlyView("termBalanceRequiredForInterestCalculationView", this.termBalanceRequiredForInterestCalculationValue);
//        this.termBalanceRequiredForInterestCalculationVContainer.add(this.termBalanceRequiredForInterestCalculationView);
//
//        this.row11Block1 = this.row11.newUIBlock("row11Block1", Size.Six_6);
//
//        this.row12 = UIRow.newUIRow("row12", this.form);
//
//        this.termWithHoldBlock = this.row12.newUIBlock("termWithHoldBlock", Size.Six_6);
//        this.termWithHoldVContainer = this.termWithHoldBlock.newUIContainer("termWithHoldVContainer");
//        this.termWithHoldView = new ReadOnlyView("termWithHoldView", this.termWithHoldValue);
//        this.termWithHoldVContainer.add(this.termWithHoldView);
//
//        this.termTaxGroupBlock = this.row12.newUIBlock("termTaxGroupBlock", Size.Six_6);
//        this.termTaxGroupVContainer = this.termTaxGroupBlock.newUIContainer("termTaxGroupVContainer");
//        this.termTaxGroupView = new ReadOnlyView("termTaxGroupView", this.termTaxGroupValue);
//        this.termTaxGroupVContainer.add(this.termTaxGroupView);
//
//        this.row13 = UIRow.newUIRow("row13", this.form);
//
//        this.chargeBlock = this.row13.newUIBlock("chargeBlock", Size.Twelve_12);
//        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
//        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
//        this.chargeVContainer.add(this.chargeTable);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//
//        boolean visible = this.termOverdraftAllowedValue.getObject() != null && this.termOverdraftAllowedValue.getObject();
//        this.termMinOverdraftRequiredForInterestCalculationBlock.setVisible(visible);
//        this.termNominalAnnualInterestForOverdraftBlock.setVisible(visible);
//        this.termMaximumOverdraftAmountLimitBlock.setVisible(visible);
//
//        this.termEnforceMinimumBalanceBlock.setVisible(!visible);
//        this.termMinimumBalanceBlock.setVisible(!visible);
//
//        this.saveButton.setVisible(!this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject());
//    }
//
//    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column) || "type".equals(column) || "collectedOn".equals(column)) {
//            Option value = (Option) model.get(column);
//            return new TextCell(value);
//        } else if ("amount".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value, "#,###,##0.00");
//        } else if ("date".equals(column)) {
//            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
//            if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
//                Date value = (Date) model.get("dayMonth");
//                return new TextCell(value, "dd MMMM");
//            } else if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.SpecifiedDueDate) {
//                Date value = (Date) model.get("date");
//                return new TextCell(value, "yyyy-MM-dd");
//            } else {
//                return new TextCell("");
//            }
//        } else if ("repaymentEvery".equals(column)) {
//            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
//            if (chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee) {
//                Long value = (Long) model.get(column);
//                return new TextCell(value);
//            } else {
//                return new TextCell("");
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_CHARGE);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        if (this.itemPage instanceof AccountCreatePage) {
//            ((AccountCreatePage) this.itemPage).saveButtonSubmit(button);
//        }
//    }
//
//}
