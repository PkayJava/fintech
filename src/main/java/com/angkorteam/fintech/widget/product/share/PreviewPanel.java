package com.angkorteam.fintech.widget.product.share;

import java.util.Date;
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
import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
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
    protected PropertyModel<Boolean> errorMarketPrice;

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

    protected WebMarkupBlock termTotalNumberOfShareBlock;
    protected WebMarkupContainer termTotalNumberOfShareVContainer;
    protected ReadOnlyView termTotalNumberOfShareView;

    protected WebMarkupBlock termShareToBeIssuedBlock;
    protected WebMarkupContainer termShareToBeIssuedVContainer;
    protected ReadOnlyView termShareToBeIssuedView;

    protected WebMarkupBlock termNominalPriceBlock;
    protected WebMarkupContainer termNominalPriceVContainer;
    protected ReadOnlyView termNominalPriceView;

    protected WebMarkupBlock termCapitalBlock;
    protected WebMarkupContainer termCapitalVContainer;
    protected ReadOnlyView termCapitalView;

    // Settings

    protected WebMarkupBlock settingSharePerClientMinimumBlock;
    protected WebMarkupContainer settingSharePerClientMinimumVContainer;
    protected ReadOnlyView settingSharePerClientMinimumView;

    protected WebMarkupBlock settingSharePerClientDefaultBlock;
    protected WebMarkupContainer settingSharePerClientDefaultVContainer;
    protected ReadOnlyView settingSharePerClientDefaultView;

    protected WebMarkupBlock settingSharePerClientMaximumBlock;
    protected WebMarkupContainer settingSharePerClientMaximumVContainer;
    protected ReadOnlyView settingSharePerClientMaximumView;

    protected WebMarkupBlock settingMinimumActiveBlock;
    protected WebMarkupContainer settingMinimumActiveVContainer;
    protected ReadOnlyView settingMinimumActiveView;
    protected String settingMinimumActiveValue;

    // protected TextField<Long> settingMinimumActivePeriodField;
    // protected Select2SingleChoice<Option> settingMinimumActiveTypeField;

    protected WebMarkupBlock settingLockInBlock;
    protected WebMarkupContainer settingLockInVContainer;
    protected ReadOnlyView settingLockInView;
    protected String settingLockInValue;

    // protected TextField<Long> settingLockInPeriodField;
    // protected Select2SingleChoice<Option> settingLockInTypeField;

    protected WebMarkupBlock settingAllowDividendForInactiveClientBlock;
    protected WebMarkupContainer settingAllowDividendForInactiveClientVContainer;
    protected ReadOnlyView settingAllowDividendForInactiveClientView;

    // Market Price

    protected WebMarkupBlock marketPriceBlock;
    protected WebMarkupContainer marketPriceVContainer;
    protected DataTable<Map<String, Object>, String> marketPriceTable;
    protected List<IColumn<Map<String, Object>, String>> marketPriceColumn;
    protected ListDataProvider marketPriceProvider;
    protected PropertyModel<List<Map<String, Object>>> marketPriceValue;

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

    protected WebMarkupBlock cashShareReferenceBlock;
    protected WebMarkupContainer cashShareReferenceVContainer;
    protected ReadOnlyView cashShareReferenceView;

    protected WebMarkupBlock cashShareSuspenseControlBlock;
    protected WebMarkupContainer cashShareSuspenseControlVContainer;
    protected ReadOnlyView cashShareSuspenseControlView;

    protected WebMarkupBlock cashEquityBlock;
    protected WebMarkupContainer cashEquityVContainer;
    protected ReadOnlyView cashEquityView;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeVContainer;
    protected ReadOnlyView cashIncomeFromFeeView;

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
        this.errorMarketPrice = new PropertyModel<>(this.itemPage, "errorMarketPrice");

        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));

        this.marketPriceValue = new PropertyModel<>(this.itemPage, "marketPriceValue");
        this.marketPriceColumn = Lists.newLinkedList();
        this.marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketPriceColumn));
        this.marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketPriceColumn));
        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue.getObject());

        PropertyModel<Long> settingMinimumActivePeriodValue = new PropertyModel<>(this.itemPage, "settingMinimumActivePeriodValue");
        PropertyModel<Option> settingMinimumActiveTypeValue = new PropertyModel<>(this.itemPage, "settingMinimumActiveTypeValue");
        if (settingMinimumActivePeriodValue.getObject() != null && settingMinimumActiveTypeValue.getObject() != null) {
            this.settingMinimumActiveValue = settingMinimumActivePeriodValue.getObject() + " " + settingMinimumActiveTypeValue.getObject().getText();
        }

        PropertyModel<Long> settingLockInPeriodValue = new PropertyModel<>(this.itemPage, "settingLockInPeriodValue");
        PropertyModel<Option> settingLockInTypeValue = new PropertyModel<>(this.itemPage, "settingLockInTypeValue");
        if (settingLockInPeriodValue.getObject() != null && settingLockInTypeValue.getObject() != null) {
            this.settingLockInValue = settingLockInPeriodValue.getObject() + " " + settingLockInTypeValue.getObject().getText();
        }

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
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

        this.termTotalNumberOfShareBlock = new WebMarkupBlock("termTotalNumberOfShareBlock", Size.Three_3);
        this.form.add(this.termTotalNumberOfShareBlock);
        this.termTotalNumberOfShareVContainer = new WebMarkupContainer("termTotalNumberOfShareVContainer");
        this.termTotalNumberOfShareBlock.add(this.termTotalNumberOfShareVContainer);
        this.termTotalNumberOfShareView = new ReadOnlyView("termTotalNumberOfShareView", new PropertyModel<>(this.itemPage, "termTotalNumberOfShareValue"));
        this.termTotalNumberOfShareVContainer.add(this.termTotalNumberOfShareView);

        this.termShareToBeIssuedBlock = new WebMarkupBlock("termShareToBeIssuedBlock", Size.Three_3);
        this.form.add(this.termShareToBeIssuedBlock);
        this.termShareToBeIssuedVContainer = new WebMarkupContainer("termShareToBeIssuedVContainer");
        this.termShareToBeIssuedBlock.add(this.termShareToBeIssuedVContainer);
        this.termShareToBeIssuedView = new ReadOnlyView("termShareToBeIssuedView", new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue"));
        this.termShareToBeIssuedVContainer.add(this.termShareToBeIssuedView);

        this.termNominalPriceBlock = new WebMarkupBlock("termNominalPriceBlock", Size.Three_3);
        this.form.add(this.termNominalPriceBlock);
        this.termNominalPriceVContainer = new WebMarkupContainer("termNominalPriceVContainer");
        this.termNominalPriceBlock.add(this.termNominalPriceVContainer);
        this.termNominalPriceView = new ReadOnlyView("termNominalPriceView", new PropertyModel<>(this.itemPage, "termNominalPriceValue"));
        this.termNominalPriceVContainer.add(this.termNominalPriceView);

        this.termCapitalBlock = new WebMarkupBlock("termCapitalBlock", Size.Three_3);
        this.form.add(this.termCapitalBlock);
        this.termCapitalVContainer = new WebMarkupContainer("termCapitalVContainer");
        this.termCapitalBlock.add(this.termCapitalVContainer);
        this.termCapitalView = new ReadOnlyView("termCapitalView", new PropertyModel<>(this.itemPage, "termCapitalValue"));
        this.termCapitalVContainer.add(this.termCapitalView);

        this.settingSharePerClientMinimumBlock = new WebMarkupBlock("settingSharePerClientMinimumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMinimumBlock);
        this.settingSharePerClientMinimumVContainer = new WebMarkupContainer("settingSharePerClientMinimumVContainer");
        this.settingSharePerClientMinimumBlock.add(this.settingSharePerClientMinimumVContainer);
        this.settingSharePerClientMinimumView = new ReadOnlyView("settingSharePerClientMinimumView", new PropertyModel<>(this.itemPage, "settingSharePerClientMinimumValue"));
        this.settingSharePerClientMinimumVContainer.add(this.settingSharePerClientMinimumView);

        this.settingSharePerClientDefaultBlock = new WebMarkupBlock("settingSharePerClientDefaultBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientDefaultBlock);
        this.settingSharePerClientDefaultVContainer = new WebMarkupContainer("settingSharePerClientDefaultVContainer");
        this.settingSharePerClientDefaultBlock.add(this.settingSharePerClientDefaultVContainer);
        this.settingSharePerClientDefaultView = new ReadOnlyView("settingSharePerClientDefaultView", new PropertyModel<>(this.itemPage, "settingSharePerClientDefaultValue"));
        this.settingSharePerClientDefaultVContainer.add(this.settingSharePerClientDefaultView);

        this.settingSharePerClientMaximumBlock = new WebMarkupBlock("settingSharePerClientMaximumBlock", Size.Four_4);
        this.form.add(this.settingSharePerClientMaximumBlock);
        this.settingSharePerClientMaximumVContainer = new WebMarkupContainer("settingSharePerClientMaximumVContainer");
        this.settingSharePerClientMaximumBlock.add(this.settingSharePerClientMaximumVContainer);
        this.settingSharePerClientMaximumView = new ReadOnlyView("settingSharePerClientMaximumView", new PropertyModel<>(this.itemPage, "settingSharePerClientMaximumValue"));
        this.settingSharePerClientMaximumVContainer.add(this.settingSharePerClientMaximumView);

        this.settingMinimumActiveBlock = new WebMarkupBlock("settingMinimumActiveBlock", Size.Four_4);
        this.form.add(this.settingMinimumActiveBlock);
        this.settingMinimumActiveVContainer = new WebMarkupContainer("settingMinimumActiveVContainer");
        this.settingMinimumActiveBlock.add(this.settingMinimumActiveVContainer);
        this.settingMinimumActiveView = new ReadOnlyView("settingMinimumActiveView", new PropertyModel<>(this, "settingMinimumActiveValue"));
        this.settingMinimumActiveVContainer.add(this.settingMinimumActiveView);

        this.settingLockInBlock = new WebMarkupBlock("settingLockInBlock", Size.Four_4);
        this.form.add(this.settingLockInBlock);
        this.settingLockInVContainer = new WebMarkupContainer("settingLockInVContainer");
        this.settingLockInBlock.add(this.settingLockInVContainer);
        this.settingLockInView = new ReadOnlyView("settingLockInView", new PropertyModel<>(this, "settingLockInValue"));
        this.settingLockInVContainer.add(this.settingLockInView);

        this.settingAllowDividendForInactiveClientBlock = new WebMarkupBlock("settingAllowDividendForInactiveClientBlock", Size.Four_4);
        this.form.add(this.settingAllowDividendForInactiveClientBlock);
        this.settingAllowDividendForInactiveClientVContainer = new WebMarkupContainer("settingAllowDividendForInactiveClientVContainer");
        this.settingAllowDividendForInactiveClientBlock.add(this.settingAllowDividendForInactiveClientVContainer);
        this.settingAllowDividendForInactiveClientView = new ReadOnlyView("settingAllowDividendForInactiveClientView", new PropertyModel<>(this.itemPage, "settingAllowDividendForInactiveClientValue"));
        this.settingAllowDividendForInactiveClientVContainer.add(this.settingAllowDividendForInactiveClientView);

        this.marketPriceBlock = new WebMarkupBlock("marketPriceBlock", Size.Twelve_12);
        this.form.add(this.marketPriceBlock);
        this.marketPriceVContainer = new WebMarkupContainer("marketPriceVContainer");
        this.marketPriceBlock.add(this.marketPriceVContainer);
        this.marketPriceTable = new DataTable<>("marketPriceTable", this.marketPriceColumn, this.marketPriceProvider, this.marketPriceValue.getObject().size() + 1);
        this.marketPriceVContainer.add(this.marketPriceTable);
        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));

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

        this.cashShareReferenceBlock = new WebMarkupBlock("cashShareReferenceBlock", Size.Four_4);
        this.cashMaster.add(this.cashShareReferenceBlock);
        this.cashShareReferenceVContainer = new WebMarkupContainer("cashShareReferenceVContainer");
        this.cashShareReferenceBlock.add(this.cashShareReferenceVContainer);
        this.cashShareReferenceView = new ReadOnlyView("cashShareReferenceView", new PropertyModel<>(this.itemPage, "cashShareReferenceValue"));
        this.cashShareReferenceVContainer.add(this.cashShareReferenceView);

        this.cashShareSuspenseControlBlock = new WebMarkupBlock("cashShareSuspenseControlBlock", Size.Four_4);
        this.cashMaster.add(this.cashShareSuspenseControlBlock);
        this.cashShareSuspenseControlVContainer = new WebMarkupContainer("cashShareSuspenseControlVContainer");
        this.cashShareSuspenseControlBlock.add(this.cashShareSuspenseControlVContainer);
        this.cashShareSuspenseControlView = new ReadOnlyView("cashShareSuspenseControlView", new PropertyModel<>(this.itemPage, "cashShareSuspenseControlValue"));
        this.cashShareSuspenseControlVContainer.add(this.cashShareSuspenseControlView);

        this.cashEquityBlock = new WebMarkupBlock("cashEquityBlock", Size.Four_4);
        this.cashMaster.add(this.cashEquityBlock);
        this.cashEquityVContainer = new WebMarkupContainer("cashEquityVContainer");
        this.cashEquityBlock.add(this.cashEquityVContainer);
        this.cashEquityView = new ReadOnlyView("cashEquityView", new PropertyModel<>(this.itemPage, "cashEquityValue"));
        this.cashEquityVContainer.add(this.cashEquityView);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Four_4);
        this.cashMaster.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeVContainer = new WebMarkupContainer("cashIncomeFromFeeVContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeVContainer);
        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"));
        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);

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

    @Override
    protected void configureMetaData() {
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
            this.cashMaster.setVisible(false);
        }

        this.saveButton.setVisible(!this.errorMarketPrice.getObject() && 
                !this.errorTerm.getObject() && 
                !this.errorDetail.getObject() && 
                !this.errorCharge.getObject() && 
                !this.errorAccounting.getObject() && 
                !this.errorCurrency.getObject() && 
                !this.errorSetting.getObject());
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

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_ACCOUNTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        if (this.itemPage instanceof ShareCreatePage) {
            ((ShareCreatePage) this.itemPage).saveButtonSubmit(button);
        }
    }

}
