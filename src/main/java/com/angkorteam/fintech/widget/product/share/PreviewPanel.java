//package com.angkorteam.fintech.widget.product.share;
//
//import java.util.Date;
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
//import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
//import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
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
//    protected PropertyModel<Boolean> errorMarketPrice;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    // Details
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
//    protected UIBlock termTotalNumberOfShareBlock;
//    protected UIContainer termTotalNumberOfShareVContainer;
//    protected ReadOnlyView termTotalNumberOfShareView;
//
//    protected UIBlock termShareToBeIssuedBlock;
//    protected UIContainer termShareToBeIssuedVContainer;
//    protected ReadOnlyView termShareToBeIssuedView;
//
//    protected UIBlock termNominalPriceBlock;
//    protected UIContainer termNominalPriceVContainer;
//    protected ReadOnlyView termNominalPriceView;
//
//    protected UIBlock termCapitalBlock;
//    protected UIContainer termCapitalVContainer;
//    protected ReadOnlyView termCapitalView;
//
//    // Settings
//
//    protected UIRow row5;
//
//    protected UIBlock settingSharePerClientMinimumBlock;
//    protected UIContainer settingSharePerClientMinimumVContainer;
//    protected ReadOnlyView settingSharePerClientMinimumView;
//
//    protected UIBlock settingSharePerClientDefaultBlock;
//    protected UIContainer settingSharePerClientDefaultVContainer;
//    protected ReadOnlyView settingSharePerClientDefaultView;
//
//    protected UIBlock settingSharePerClientMaximumBlock;
//    protected UIContainer settingSharePerClientMaximumVContainer;
//    protected ReadOnlyView settingSharePerClientMaximumView;
//
//    protected UIRow row6;
//
//    protected UIBlock settingMinimumActiveBlock;
//    protected UIContainer settingMinimumActiveVContainer;
//    protected ReadOnlyView settingMinimumActiveView;
//    protected String settingMinimumActiveValue;
//
//    protected UIBlock settingLockInBlock;
//    protected UIContainer settingLockInVContainer;
//    protected ReadOnlyView settingLockInView;
//    protected String settingLockInValue;
//
//    protected UIBlock settingAllowDividendForInactiveClientBlock;
//    protected UIContainer settingAllowDividendForInactiveClientVContainer;
//    protected ReadOnlyView settingAllowDividendForInactiveClientView;
//
//    // Market Price
//
//    protected UIRow row7;
//
//    protected UIBlock marketPriceBlock;
//    protected UIContainer marketPriceVContainer;
//    protected DataTable<Map<String, Object>, String> marketPriceTable;
//    protected List<IColumn<Map<String, Object>, String>> marketPriceColumn;
//    protected ListDataProvider marketPriceProvider;
//    protected PropertyModel<List<Map<String, Object>>> marketPriceValue;
//
//    // Charges
//
//    protected UIRow row8;
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
//    protected UIRow row9;
//
//    protected UIBlock cashShareReferenceBlock;
//    protected UIContainer cashShareReferenceVContainer;
//    protected ReadOnlyView cashShareReferenceView;
//
//    protected UIBlock cashShareSuspenseControlBlock;
//    protected UIContainer cashShareSuspenseControlVContainer;
//    protected ReadOnlyView cashShareSuspenseControlView;
//
//    protected UIBlock cashEquityBlock;
//    protected UIContainer cashEquityVContainer;
//    protected ReadOnlyView cashEquityView;
//
//    protected UIRow row10;
//
//    protected UIBlock cashIncomeFromFeeBlock;
//    protected UIContainer cashIncomeFromFeeVContainer;
//    protected ReadOnlyView cashIncomeFromFeeView;
//
//    protected UIBlock row10Block1;
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
//        this.errorMarketPrice = new PropertyModel<>(this.itemPage, "errorMarketPrice");
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
//        this.marketPriceValue = new PropertyModel<>(this.itemPage, "marketPriceValue");
//        this.marketPriceColumn = Lists.newLinkedList();
//        this.marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketPriceColumn));
//        this.marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketPriceColumn));
//        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue.getObject());
//
//        PropertyModel<Long> settingMinimumActivePeriodValue = new PropertyModel<>(this.itemPage, "settingMinimumActivePeriodValue");
//        PropertyModel<Option> settingMinimumActiveTypeValue = new PropertyModel<>(this.itemPage, "settingMinimumActiveTypeValue");
//        if (settingMinimumActivePeriodValue.getObject() != null && settingMinimumActiveTypeValue.getObject() != null) {
//            this.settingMinimumActiveValue = settingMinimumActivePeriodValue.getObject() + " " + settingMinimumActiveTypeValue.getObject().getText();
//        }
//
//        PropertyModel<Long> settingLockInPeriodValue = new PropertyModel<>(this.itemPage, "settingLockInPeriodValue");
//        PropertyModel<Option> settingLockInTypeValue = new PropertyModel<>(this.itemPage, "settingLockInTypeValue");
//        if (settingLockInPeriodValue.getObject() != null && settingLockInTypeValue.getObject() != null) {
//            this.settingLockInValue = settingLockInPeriodValue.getObject() + " " + settingLockInTypeValue.getObject().getText();
//        }
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
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
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
//        this.termTotalNumberOfShareBlock = this.row4.newUIBlock("termTotalNumberOfShareBlock", Size.Three_3);
//        this.termTotalNumberOfShareVContainer = this.termTotalNumberOfShareBlock.newUIContainer("termTotalNumberOfShareVContainer");
//        this.termTotalNumberOfShareView = new ReadOnlyView("termTotalNumberOfShareView", new PropertyModel<>(this.itemPage, "termTotalNumberOfShareValue"));
//        this.termTotalNumberOfShareVContainer.add(this.termTotalNumberOfShareView);
//
//        this.termShareToBeIssuedBlock = this.row4.newUIBlock("termShareToBeIssuedBlock", Size.Three_3);
//        this.termShareToBeIssuedVContainer = this.termShareToBeIssuedBlock.newUIContainer("termShareToBeIssuedVContainer");
//        this.termShareToBeIssuedView = new ReadOnlyView("termShareToBeIssuedView", new PropertyModel<>(this.itemPage, "termShareToBeIssuedValue"));
//        this.termShareToBeIssuedVContainer.add(this.termShareToBeIssuedView);
//
//        this.termNominalPriceBlock = this.row4.newUIBlock("termNominalPriceBlock", Size.Three_3);
//        this.termNominalPriceVContainer = this.termNominalPriceBlock.newUIContainer("termNominalPriceVContainer");
//        this.termNominalPriceView = new ReadOnlyView("termNominalPriceView", new PropertyModel<>(this.itemPage, "termNominalPriceValue"));
//        this.termNominalPriceVContainer.add(this.termNominalPriceView);
//
//        this.termCapitalBlock = this.row4.newUIBlock("termCapitalBlock", Size.Three_3);
//        this.termCapitalVContainer = this.termCapitalBlock.newUIContainer("termCapitalVContainer");
//        this.termCapitalView = new ReadOnlyView("termCapitalView", new PropertyModel<>(this.itemPage, "termCapitalValue"));
//        this.termCapitalVContainer.add(this.termCapitalView);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.settingSharePerClientMinimumBlock = this.row5.newUIBlock("settingSharePerClientMinimumBlock", Size.Four_4);
//        this.settingSharePerClientMinimumVContainer = this.settingSharePerClientMinimumBlock.newUIContainer("settingSharePerClientMinimumVContainer");
//        this.settingSharePerClientMinimumView = new ReadOnlyView("settingSharePerClientMinimumView", new PropertyModel<>(this.itemPage, "settingSharePerClientMinimumValue"));
//        this.settingSharePerClientMinimumVContainer.add(this.settingSharePerClientMinimumView);
//
//        this.settingSharePerClientDefaultBlock = this.row5.newUIBlock("settingSharePerClientDefaultBlock", Size.Four_4);
//        this.settingSharePerClientDefaultVContainer = this.settingSharePerClientDefaultBlock.newUIContainer("settingSharePerClientDefaultVContainer");
//        this.settingSharePerClientDefaultView = new ReadOnlyView("settingSharePerClientDefaultView", new PropertyModel<>(this.itemPage, "settingSharePerClientDefaultValue"));
//        this.settingSharePerClientDefaultVContainer.add(this.settingSharePerClientDefaultView);
//
//        this.settingSharePerClientMaximumBlock = this.row5.newUIBlock("settingSharePerClientMaximumBlock", Size.Four_4);
//        this.settingSharePerClientMaximumVContainer = this.settingSharePerClientMaximumBlock.newUIContainer("settingSharePerClientMaximumVContainer");
//        this.settingSharePerClientMaximumView = new ReadOnlyView("settingSharePerClientMaximumView", new PropertyModel<>(this.itemPage, "settingSharePerClientMaximumValue"));
//        this.settingSharePerClientMaximumVContainer.add(this.settingSharePerClientMaximumView);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.settingMinimumActiveBlock = this.row6.newUIBlock("settingMinimumActiveBlock", Size.Four_4);
//        this.settingMinimumActiveVContainer = this.settingMinimumActiveBlock.newUIContainer("settingMinimumActiveVContainer");
//        this.settingMinimumActiveView = new ReadOnlyView("settingMinimumActiveView", new PropertyModel<>(this, "settingMinimumActiveValue"));
//        this.settingMinimumActiveVContainer.add(this.settingMinimumActiveView);
//
//        this.settingLockInBlock = this.row6.newUIBlock("settingLockInBlock", Size.Four_4);
//        this.settingLockInVContainer = this.settingLockInBlock.newUIContainer("settingLockInVContainer");
//        this.settingLockInView = new ReadOnlyView("settingLockInView", new PropertyModel<>(this, "settingLockInValue"));
//        this.settingLockInVContainer.add(this.settingLockInView);
//
//        this.settingAllowDividendForInactiveClientBlock = this.row6.newUIBlock("settingAllowDividendForInactiveClientBlock", Size.Four_4);
//        this.settingAllowDividendForInactiveClientVContainer = this.settingAllowDividendForInactiveClientBlock.newUIContainer("settingAllowDividendForInactiveClientVContainer");
//        this.settingAllowDividendForInactiveClientView = new ReadOnlyView("settingAllowDividendForInactiveClientView", new PropertyModel<>(this.itemPage, "settingAllowDividendForInactiveClientValue"));
//        this.settingAllowDividendForInactiveClientVContainer.add(this.settingAllowDividendForInactiveClientView);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.marketPriceBlock = this.row7.newUIBlock("marketPriceBlock", Size.Twelve_12);
//        this.marketPriceVContainer = this.marketPriceBlock.newUIContainer("marketPriceVContainer");
//        this.marketPriceTable = new DataTable<>("marketPriceTable", this.marketPriceColumn, this.marketPriceProvider, this.marketPriceValue.getObject().size() + 1);
//        this.marketPriceVContainer.add(this.marketPriceTable);
//        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
//        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.chargeBlock = this.row8.newUIBlock("chargeBlock", Size.Twelve_12);
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
//        this.row9 = UIRow.newUIRow("row9", this.cashMaster);
//
//        this.cashShareReferenceBlock = this.row9.newUIBlock("cashShareReferenceBlock", Size.Four_4);
//        this.cashShareReferenceVContainer = this.cashShareReferenceBlock.newUIContainer("cashShareReferenceVContainer");
//        this.cashShareReferenceView = new ReadOnlyView("cashShareReferenceView", new PropertyModel<>(this.itemPage, "cashShareReferenceValue"));
//        this.cashShareReferenceVContainer.add(this.cashShareReferenceView);
//
//        this.cashShareSuspenseControlBlock = this.row9.newUIBlock("cashShareSuspenseControlBlock", Size.Four_4);
//        this.cashShareSuspenseControlVContainer = this.cashShareSuspenseControlBlock.newUIContainer("cashShareSuspenseControlVContainer");
//        this.cashShareSuspenseControlView = new ReadOnlyView("cashShareSuspenseControlView", new PropertyModel<>(this.itemPage, "cashShareSuspenseControlValue"));
//        this.cashShareSuspenseControlVContainer.add(this.cashShareSuspenseControlView);
//
//        this.cashEquityBlock = this.row9.newUIBlock("cashEquityBlock", Size.Four_4);
//        this.cashEquityVContainer = this.cashEquityBlock.newUIContainer("cashEquityVContainer");
//        this.cashEquityView = new ReadOnlyView("cashEquityView", new PropertyModel<>(this.itemPage, "cashEquityValue"));
//        this.cashEquityVContainer.add(this.cashEquityView);
//
//        this.row10 = UIRow.newUIRow("row10", this.cashMaster);
//
//        this.cashIncomeFromFeeBlock = this.row10.newUIBlock("cashIncomeFromFeeBlock", Size.Four_4);
//        this.cashIncomeFromFeeVContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeVContainer");
//        this.cashIncomeFromFeeView = new ReadOnlyView("cashIncomeFromFeeView", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"));
//        this.cashIncomeFromFeeVContainer.add(this.cashIncomeFromFeeView);
//
//        this.row10Block1 = this.row10.newUIBlock("row10Block1", Size.Eight_8);
//
//    }
//
//    protected ItemPanel marketPriceColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("fromDate".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yyyy");
//        } else if ("unitPrice".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
//        if (AccountingType.None.getDescription().equals(accountingValue.getObject())) {
//            this.cashMaster.setVisible(false);
//        }
//
//        this.saveButton.setVisible(!this.errorMarketPrice.getObject() && !this.errorTerm.getObject() && !this.errorDetail.getObject() && !this.errorCharge.getObject() && !this.errorAccounting.getObject() && !this.errorCurrency.getObject() && !this.errorSetting.getObject());
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
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_ACCOUNTING);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        if (this.itemPage instanceof ShareCreatePage) {
//            ((ShareCreatePage) this.itemPage).saveButtonSubmit(button);
//        }
//    }
//
//}
