package com.angkorteam.fintech.widget.product.recurring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.recurring.RecurringBrowsePage;
import com.angkorteam.fintech.pages.product.recurring.RecurringCreatePage;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AccountingPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorAccounting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected UIRow row1;

    protected UIBlock cashSavingReferenceBlock;
    protected UIContainer cashSavingReferenceIContainer;
    protected SingleChoiceProvider cashSavingReferenceProvider;
    protected Select2SingleChoice<Option> cashSavingReferenceField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock cashSavingControlBlock;
    protected UIContainer cashSavingControlIContainer;
    protected SingleChoiceProvider cashSavingControlProvider;
    protected Select2SingleChoice<Option> cashSavingControlField;

    protected UIBlock cashSavingTransferInSuspenseBlock;
    protected UIContainer cashSavingTransferInSuspenseIContainer;
    protected SingleChoiceProvider cashSavingTransferInSuspenseProvider;
    protected Select2SingleChoice<Option> cashSavingTransferInSuspenseField;

    protected UIRow row3;

    protected UIBlock cashInterestOnSavingBlock;
    protected UIContainer cashInterestOnSavingIContainer;
    protected SingleChoiceProvider cashInterestOnSavingProvider;
    protected Select2SingleChoice<Option> cashInterestOnSavingField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock cashIncomeFromFeeBlock;
    protected UIContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;

    protected UIBlock cashIncomeFromPenaltyBlock;
    protected UIContainer cashIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

    protected UIRow row5;

    protected UIBlock row5Block1;

    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;

    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;

    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;

    public AccountingPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.popupModel = new HashMap<>();

        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");

        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
        this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());

        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
        this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());

        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
        this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());

        this.cashSavingReferenceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashSavingReferenceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashSavingReferenceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.cashSavingControlProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashSavingControlProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashSavingControlProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());

        this.cashSavingTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashSavingTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashSavingTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());

        this.cashInterestOnSavingProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashInterestOnSavingProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashInterestOnSavingProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", RecurringBrowsePage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
        this.form.add(this.accountingField);

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashIContainer.setOutputMarkupId(true);
        this.cashBlock.add(this.cashIContainer);

        this.row1 = UIRow.newUIRow("row1", this.cashIContainer);

        this.cashSavingReferenceBlock = this.row1.newUIBlock("cashSavingReferenceBlock", Size.Six_6);
        this.cashSavingReferenceIContainer = this.cashSavingReferenceBlock.newUIContainer("cashSavingReferenceIContainer");
        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this.itemPage, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceField);
        this.cashSavingReferenceIContainer.newFeedback("cashSavingReferenceFeedback", this.cashSavingReferenceField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.cashIContainer);

        this.cashSavingControlBlock = this.row2.newUIBlock("cashSavingControlBlock", Size.Six_6);
        this.cashSavingControlIContainer = this.cashSavingControlBlock.newUIContainer("cashSavingControlIContainer");
        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this.itemPage, "cashSavingControlValue"), this.cashSavingControlProvider);
        this.cashSavingControlIContainer.add(this.cashSavingControlField);
        this.cashSavingControlIContainer.newFeedback("cashSavingControlFeedback", this.cashSavingControlField);

        this.cashSavingTransferInSuspenseBlock = this.row2.newUIBlock("cashSavingTransferInSuspenseBlock", Size.Six_6);
        this.cashSavingTransferInSuspenseIContainer = this.cashSavingTransferInSuspenseBlock.newUIContainer("cashSavingTransferInSuspenseIContainer");
        this.cashSavingTransferInSuspenseField = new Select2SingleChoice<>("cashSavingTransferInSuspenseField", new PropertyModel<>(this.itemPage, "cashSavingTransferInSuspenseValue"), this.cashSavingTransferInSuspenseProvider);
        this.cashSavingTransferInSuspenseIContainer.add(this.cashSavingTransferInSuspenseField);
        this.cashSavingTransferInSuspenseIContainer.newFeedback("cashSavingTransferInSuspenseFeedback", this.cashSavingTransferInSuspenseField);

        this.row3 = UIRow.newUIRow("row3", this.cashIContainer);

        this.cashInterestOnSavingBlock = this.row3.newUIBlock("cashInterestOnSavingBlock", Size.Six_6);
        this.cashInterestOnSavingIContainer = this.cashInterestOnSavingBlock.newUIContainer("cashInterestOnSavingIContainer");
        this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField", new PropertyModel<>(this.itemPage, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingField);
        this.cashInterestOnSavingIContainer.newFeedback("cashInterestOnSavingFeedback", this.cashInterestOnSavingField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.cashIContainer);

        this.cashIncomeFromFeeBlock = this.row4.newUIBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIncomeFromFeeIContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.newFeedback("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);

        this.cashIncomeFromPenaltyBlock = this.row4.newUIBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashIncomeFromPenaltyIContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyIContainer");
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.newFeedback("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);

        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleIContainer = new WebMarkupContainer("advancedAccountingRuleIContainer");
        this.advancedAccountingRuleIContainer.setOutputMarkupId(true);
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleIContainer);

        this.row5 = UIRow.newUIRow("row5", this.advancedAccountingRuleIContainer);

        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Twelve_12);

        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.row5Block1.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
        this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
        this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
        this.row5Block1.add(this.advancedAccountingRulePenaltyIncomeAddLink);

        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.row5Block1.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
        this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
        this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
        this.row5Block1.add(this.advancedAccountingRuleFeeIncomeAddLink);

        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.row5Block1.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
        this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
        this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
        this.row5Block1.add(this.advancedAccountingRuleFundSourceAddLink);
    }

    @Override
    protected void configureMetaData() {
        this.cashIncomeFromPenaltyField.setLabel(Model.of("Income From Penalties"));
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromPenaltyField.setRequired(true);

        this.cashIncomeFromFeeField.setLabel(Model.of("Income From Fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeField.setRequired(true);

        this.cashInterestOnSavingField.setLabel(Model.of("Interest On Savings"));
        this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
        this.cashInterestOnSavingField.setRequired(true);

        this.cashSavingTransferInSuspenseField.setLabel(Model.of("Savings Transfers In Suspense"));
        this.cashSavingTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashSavingTransferInSuspenseField.setRequired(true);

        this.cashSavingControlField.setLabel(Model.of("Saving Control"));
        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
        this.cashSavingControlField.setRequired(true);

        this.cashSavingReferenceField.setLabel(Model.of("Saving Reference"));
        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
        this.cashSavingReferenceField.setRequired(true);

        this.accountingField.setRequired(true);
        accountingFieldUpdate(null);
    }

    protected void nextButtonSubmit(Button button) {
        this.errorAccounting.setObject(false);
        this.tab.getObject().setSelectedTab(RecurringCreatePage.TAB_PREVIEW);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(RecurringCreatePage.TAB_CHARGE);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if ("fee".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("charge", this.popupModel.get("chargeValue"));
            item.put("account", this.popupModel.get("accountValue"));
            this.advancedAccountingRuleFeeIncomeValue.getObject().add(item);
            target.add(this.advancedAccountingRuleFeeIncomeTable);
        } else if ("penalty".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("charge", this.popupModel.get("chargeValue"));
            item.put("account", this.popupModel.get("accountValue"));
            this.advancedAccountingRulePenaltyIncomeValue.getObject().add(item);
            target.add(this.advancedAccountingRulePenaltyIncomeTable);
        } else if ("fund".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("payment", this.popupModel.get("paymentValue"));
            item.put("account", this.popupModel.get("accountValue"));
            this.advancedAccountingRuleFundSourceValue.getObject().add(item);
            target.add(this.advancedAccountingRuleFundSourceTable);
        }
    }

    protected void nextButtonError(Button button) {
        this.errorAccounting.setObject(true);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);
        this.advancedAccountingRuleIContainer.setVisible(false);
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        if (AccountingType.None.getDescription().equals(accountingValue.getObject()) || accountingValue.getObject() == null) {
            this.advancedAccountingRuleIContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleIContainer.setVisible(true);
        }
        if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
            this.cashIContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.advancedAccountingRuleBlock);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRulePenaltyIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        PropertyModel<Option> currencyCodeValue = new PropertyModel<>(this.itemPage, "currencyCodeValue");
        if (currencyCodeValue.getObject() != null) {
            this.modalWindow.setContent(new PenaltyChargePopup("penalty", this.popupModel, ProductPopup.Fixed, currencyCodeValue.getObject().getId()));
            this.modalWindow.show(target);
        } else {
            this.modalWindow.setContent(new CurrencyPopup("currency"));
            this.modalWindow.show(target);
        }
        return false;
    }

    protected void advancedAccountingRulePenaltyIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.getObject().size(); i++) {
            Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRulePenaltyIncomeValue.getObject().remove(index);
        }
        target.add(this.advancedAccountingRulePenaltyIncomeTable);
    }

    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        PropertyModel<Option> currencyCodeValue = new PropertyModel<>(this.itemPage, "currencyCodeValue");
        if (currencyCodeValue.getObject() != null) {
            this.modalWindow.setContent(new FeeChargePopup("fee", this.popupModel, ProductPopup.Fixed, currencyCodeValue.getObject().getId()));
            this.modalWindow.show(target);
        } else {
            this.modalWindow.setContent(new CurrencyPopup("currency"));
            this.modalWindow.show(target);
        }
        return false;
    }

    protected ItemPanel advancedAccountingRuleFeeIncomeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("charge".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void advancedAccountingRuleFeeIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.getObject().size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFeeIncomeValue.getObject().remove(index);
        }
        target.add(this.advancedAccountingRuleFeeIncomeTable);
    }

    protected void advancedAccountingRuleFundSourceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.getObject().size(); i++) {
            Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.advancedAccountingRuleFundSourceValue.getObject().remove(index);
        }
        target.add(this.advancedAccountingRuleFundSourceTable);
    }

    protected List<ActionItem> advancedAccountingRuleFundSourceAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.modalWindow.setContent(new PaymentTypePopup("fund", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected ItemPanel advancedAccountingRuleFundSourceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("payment".equals(column) || "account".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
