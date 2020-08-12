//package com.angkorteam.fintech.widget.product.savings;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.WebMarkupContainer;
//import org.apache.wicket.markup.html.form.Radio;
//import org.apache.wicket.markup.html.form.RadioGroup;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.dto.enums.AccountUsage;
//import com.angkorteam.fintech.dto.enums.AccountingType;
//import com.angkorteam.fintech.dto.enums.ProductPopup;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
//import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
//import com.angkorteam.fintech.popup.CurrencyPopup;
//import com.angkorteam.fintech.popup.FeeChargePopup;
//import com.angkorteam.fintech.popup.PaymentTypePopup;
//import com.angkorteam.fintech.popup.PenaltyChargePopup;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//public class AccountingPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorAccounting;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected ModalWindow modalWindow;
//    protected Map<String, Object> popupModel;
//
//    protected RadioGroup<String> accountingField;
//
//    protected WebMarkupContainer cashBlock;
//    protected WebMarkupContainer cashIContainer;
//
//    protected UIRow row1;
//
//    protected UIBlock cashSavingReferenceBlock;
//    protected UIContainer cashSavingReferenceIContainer;
//    protected SingleChoiceProvider cashSavingReferenceProvider;
//    protected Select2SingleChoice<Option> cashSavingReferenceField;
//
//    protected UIBlock cashOverdraftPortfolioBlock;
//    protected UIContainer cashOverdraftPortfolioIContainer;
//    protected SingleChoiceProvider cashOverdraftPortfolioProvider;
//    protected Select2SingleChoice<Option> cashOverdraftPortfolioField;
//
//    protected UIRow row2;
//
//    protected UIBlock cashSavingControlBlock;
//    protected UIContainer cashSavingControlIContainer;
//    protected SingleChoiceProvider cashSavingControlProvider;
//    protected Select2SingleChoice<Option> cashSavingControlField;
//
//    protected UIBlock cashSavingTransferInSuspenseBlock;
//    protected UIContainer cashSavingTransferInSuspenseIContainer;
//    protected SingleChoiceProvider cashSavingTransferInSuspenseProvider;
//    protected Select2SingleChoice<Option> cashSavingTransferInSuspenseField;
//
//    protected UIRow row3;
//
//    protected UIBlock cashEscheatLiabilityBlock;
//    protected UIContainer cashEscheatLiabilityIContainer;
//    protected SingleChoiceProvider cashEscheatLiabilityProvider;
//    protected Select2SingleChoice<Option> cashEscheatLiabilityField;
//
//    protected UIBlock row3Block1;
//
//    protected UIRow row4;
//
//    protected UIBlock cashInterestOnSavingBlock;
//    protected UIContainer cashInterestOnSavingIContainer;
//    protected SingleChoiceProvider cashInterestOnSavingProvider;
//    protected Select2SingleChoice<Option> cashInterestOnSavingField;
//
//    protected UIBlock cashWriteOffBlock;
//    protected UIContainer cashWriteOffIContainer;
//    protected SingleChoiceProvider cashWriteOffProvider;
//    protected Select2SingleChoice<Option> cashWriteOffField;
//
//    protected UIRow row5;
//
//    protected UIBlock cashIncomeFromFeeBlock;
//    protected UIContainer cashIncomeFromFeeIContainer;
//    protected SingleChoiceProvider cashIncomeFromFeeProvider;
//    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
//
//    protected UIBlock cashIncomeFromPenaltyBlock;
//    protected UIContainer cashIncomeFromPenaltyIContainer;
//    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
//    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;
//
//    protected UIRow row6;
//
//    protected UIBlock cashOverdraftInterestIncomeBlock;
//    protected UIContainer cashOverdraftInterestIncomeIContainer;
//    protected SingleChoiceProvider cashOverdraftInterestIncomeProvider;
//    protected Select2SingleChoice<Option> cashOverdraftInterestIncomeField;
//
//    protected UIBlock row6Block1;
//
//    protected WebMarkupContainer advancedAccountingRuleBlock;
//    protected WebMarkupContainer advancedAccountingRuleIContainer;
//
//    protected UIRow row7;
//
//    protected UIBlock row7Block;
//    protected UIContainer row7IContainer;
//
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFundSourceTable;
//    protected ListDataProvider advancedAccountingRuleFundSourceProvider;
//    protected AjaxLink<Void> advancedAccountingRuleFundSourceAddLink;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFundSourceColumn;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFundSourceValue;
//
//    protected DataTable<Map<String, Object>, String> advancedAccountingRuleFeeIncomeTable;
//    protected ListDataProvider advancedAccountingRuleFeeIncomeProvider;
//    protected AjaxLink<Void> advancedAccountingRuleFeeIncomeAddLink;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRuleFeeIncomeColumn;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRuleFeeIncomeValue;
//
//    protected DataTable<Map<String, Object>, String> advancedAccountingRulePenaltyIncomeTable;
//    protected ListDataProvider advancedAccountingRulePenaltyIncomeProvider;
//    protected AjaxLink<Void> advancedAccountingRulePenaltyIncomeAddLink;
//    protected List<IColumn<Map<String, Object>, String>> advancedAccountingRulePenaltyIncomeColumn;
//    protected PropertyModel<List<Map<String, Object>>> advancedAccountingRulePenaltyIncomeValue;
//
//    public AccountingPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.popupModel = new HashMap<>();
//        this.advancedAccountingRuleFundSourceValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFundSourceValue");
//        this.advancedAccountingRuleFeeIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRuleFeeIncomeValue");
//        this.advancedAccountingRulePenaltyIncomeValue = new PropertyModel<>(this.itemPage, "advancedAccountingRulePenaltyIncomeValue");
//
//        this.advancedAccountingRulePenaltyIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Penalty"), "charge", "charge", this::advancedAccountingRulePenaltyIncomeColumn));
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRulePenaltyIncomeColumn));
//        this.advancedAccountingRulePenaltyIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRulePenaltyIncomeAction, this::advancedAccountingRulePenaltyIncomeClick));
//        this.advancedAccountingRulePenaltyIncomeProvider = new ListDataProvider(this.advancedAccountingRulePenaltyIncomeValue.getObject());
//
//        this.advancedAccountingRuleFeeIncomeColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Fees"), "charge", "charge", this::advancedAccountingRuleFeeIncomeColumn));
//        this.advancedAccountingRuleFeeIncomeColumn.add(new TextColumn(Model.of("Income Account"), "account", "account", this::advancedAccountingRuleFeeIncomeColumn));
//        this.advancedAccountingRuleFeeIncomeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFeeIncomeAction, this::advancedAccountingRuleFeeIncomeClick));
//        this.advancedAccountingRuleFeeIncomeProvider = new ListDataProvider(this.advancedAccountingRuleFeeIncomeValue.getObject());
//
//        this.advancedAccountingRuleFundSourceColumn = Lists.newArrayList();
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Payment Type"), "payment", "payment", this::advancedAccountingRuleFundSourceColumn));
//        this.advancedAccountingRuleFundSourceColumn.add(new TextColumn(Model.of("Fund Source"), "account", "account", this::advancedAccountingRuleFundSourceColumn));
//        this.advancedAccountingRuleFundSourceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::advancedAccountingRuleFundSourceAction, this::advancedAccountingRuleFundSourceClick));
//        this.advancedAccountingRuleFundSourceProvider = new ListDataProvider(this.advancedAccountingRuleFundSourceValue.getObject());
//
//        this.cashSavingReferenceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashSavingReferenceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashSavingReferenceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
//
//        this.cashOverdraftPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashOverdraftPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashOverdraftPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
//
//        this.cashSavingControlProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashSavingControlProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashSavingControlProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
//
//        this.cashSavingTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashSavingTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashSavingTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
//
//        this.cashEscheatLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashEscheatLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashEscheatLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
//
//        this.cashInterestOnSavingProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashInterestOnSavingProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashInterestOnSavingProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
//
//        this.cashWriteOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashWriteOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashWriteOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
//
//        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
//
//        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
//
//        this.cashOverdraftInterestIncomeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.cashOverdraftInterestIncomeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.cashOverdraftInterestIncomeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.nextButton = new Button("nextButton");
//        this.nextButton.setOnSubmit(this::nextButtonSubmit);
//        this.nextButton.setOnError(this::nextButtonError);
//        this.form.add(this.nextButton);
//
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.modalWindow = new ModalWindow("modalWindow");
//        add(this.modalWindow);
//        this.modalWindow.setOnClose(this::modalWindowClose);
//
//        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this.itemPage, "accountingValue"));
//        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
//        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
//        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
//        this.form.add(this.accountingField);
//
//        this.cashBlock = new WebMarkupContainer("cashBlock");
//        this.cashBlock.setOutputMarkupId(true);
//        this.form.add(this.cashBlock);
//        this.cashIContainer = new WebMarkupContainer("cashIContainer");
//        this.cashBlock.add(this.cashIContainer);
//
//        this.row1 = UIRow.newUIRow("row1", this.cashIContainer);
//
//        this.cashSavingReferenceBlock = this.row1.newUIBlock("cashSavingReferenceBlock", Size.Six_6);
//        this.cashSavingReferenceIContainer = this.cashSavingReferenceBlock.newUIContainer("cashSavingReferenceIContainer");
//        this.cashSavingReferenceField = new Select2SingleChoice<>("cashSavingReferenceField", new PropertyModel<>(this.itemPage, "cashSavingReferenceValue"), this.cashSavingReferenceProvider);
//        this.cashSavingReferenceIContainer.add(this.cashSavingReferenceField);
//        this.cashSavingReferenceIContainer.newFeedback("cashSavingReferenceFeedback", this.cashSavingReferenceField);
//
//        this.cashOverdraftPortfolioBlock = this.row1.newUIBlock("cashOverdraftPortfolioBlock", Size.Six_6);
//        this.cashOverdraftPortfolioIContainer = this.cashOverdraftPortfolioBlock.newUIContainer("cashOverdraftPortfolioIContainer");
//        this.cashOverdraftPortfolioField = new Select2SingleChoice<>("cashOverdraftPortfolioField", new PropertyModel<>(this.itemPage, "cashOverdraftPortfolioValue"), this.cashOverdraftPortfolioProvider);
//        this.cashOverdraftPortfolioIContainer.add(this.cashOverdraftPortfolioField);
//        this.cashOverdraftPortfolioIContainer.newFeedback("cashOverdraftPortfolioFeedback", this.cashOverdraftPortfolioField);
//
//        this.row2 = UIRow.newUIRow("row2", this.cashIContainer);
//
//        this.cashSavingControlBlock = this.row2.newUIBlock("cashSavingControlBlock", Size.Six_6);
//        this.cashSavingControlIContainer = this.cashSavingControlBlock.newUIContainer("cashSavingControlIContainer");
//        this.cashSavingControlField = new Select2SingleChoice<>("cashSavingControlField", new PropertyModel<>(this.itemPage, "cashSavingControlValue"), this.cashSavingControlProvider);
//        this.cashSavingControlIContainer.add(this.cashSavingControlField);
//        this.cashSavingControlIContainer.newFeedback("cashSavingControlFeedback", this.cashSavingControlField);
//
//        this.cashSavingTransferInSuspenseBlock = this.row2.newUIBlock("cashSavingTransferInSuspenseBlock", Size.Six_6);
//        this.cashSavingTransferInSuspenseIContainer = this.cashSavingTransferInSuspenseBlock.newUIContainer("cashSavingTransferInSuspenseIContainer");
//        this.cashSavingTransferInSuspenseField = new Select2SingleChoice<>("cashSavingTransferInSuspenseField", new PropertyModel<>(this.itemPage, "cashSavingTransferInSuspenseValue"), this.cashSavingTransferInSuspenseProvider);
//        this.cashSavingTransferInSuspenseIContainer.add(this.cashSavingTransferInSuspenseField);
//        this.cashSavingTransferInSuspenseIContainer.newFeedback("cashSavingTransferInSuspenseFeedback", this.cashSavingTransferInSuspenseField);
//
//        this.row3 = UIRow.newUIRow("row3", this.cashIContainer);
//
//        this.cashEscheatLiabilityBlock = this.row3.newUIBlock("cashEscheatLiabilityBlock", Size.Six_6);
//        this.cashEscheatLiabilityIContainer = this.cashEscheatLiabilityBlock.newUIContainer("cashEscheatLiabilityIContainer");
//        this.cashEscheatLiabilityField = new Select2SingleChoice<>("cashEscheatLiabilityField", new PropertyModel<>(this.itemPage, "cashEscheatLiabilityValue"), this.cashEscheatLiabilityProvider);
//        this.cashEscheatLiabilityIContainer.add(this.cashEscheatLiabilityField);
//        this.cashEscheatLiabilityIContainer.newFeedback("cashEscheatLiabilityFeedback", this.cashEscheatLiabilityField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);
//
//        this.row4 = UIRow.newUIRow("row4", this.cashIContainer);
//
//        this.cashInterestOnSavingBlock = this.row4.newUIBlock("cashInterestOnSavingBlock", Size.Six_6);
//        this.cashInterestOnSavingIContainer = this.cashInterestOnSavingBlock.newUIContainer("cashInterestOnSavingIContainer");
//        this.cashInterestOnSavingField = new Select2SingleChoice<>("cashInterestOnSavingField", new PropertyModel<>(this.itemPage, "cashInterestOnSavingValue"), this.cashInterestOnSavingProvider);
//        this.cashInterestOnSavingIContainer.add(this.cashInterestOnSavingField);
//        this.cashInterestOnSavingIContainer.newFeedback("cashInterestOnSavingFeedback", this.cashInterestOnSavingField);
//
//        this.cashWriteOffBlock = this.row4.newUIBlock("cashWriteOffBlock", Size.Six_6);
//        this.cashWriteOffIContainer = this.cashWriteOffBlock.newUIContainer("cashWriteOffIContainer");
//        this.cashWriteOffField = new Select2SingleChoice<>("cashWriteOffField", new PropertyModel<>(this.itemPage, "cashWriteOffValue"), this.cashWriteOffProvider);
//        this.cashWriteOffIContainer.add(this.cashWriteOffField);
//        this.cashWriteOffIContainer.newFeedback("cashWriteOffFeedback", this.cashWriteOffField);
//
//        this.row5 = UIRow.newUIRow("row5", this.cashIContainer);
//
//        this.cashIncomeFromFeeBlock = this.row5.newUIBlock("cashIncomeFromFeeBlock", Size.Six_6);
//        this.cashIncomeFromFeeIContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeIContainer");
//        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
//        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
//        this.cashIncomeFromFeeIContainer.newFeedback("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
//
//        this.cashIncomeFromPenaltyBlock = this.row5.newUIBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
//        this.cashIncomeFromPenaltyIContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyIContainer");
//        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
//        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
//        this.cashIncomeFromPenaltyIContainer.newFeedback("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);
//
//        this.row6 = UIRow.newUIRow("row6", this.cashIContainer);
//
//        this.cashOverdraftInterestIncomeBlock = this.row6.newUIBlock("cashOverdraftInterestIncomeBlock", Size.Six_6);
//        this.cashOverdraftInterestIncomeIContainer = this.cashOverdraftInterestIncomeBlock.newUIContainer("cashOverdraftInterestIncomeIContainer");
//        this.cashOverdraftInterestIncomeField = new Select2SingleChoice<>("cashOverdraftInterestIncomeField", new PropertyModel<>(this.itemPage, "cashOverdraftInterestIncomeValue"), this.cashOverdraftInterestIncomeProvider);
//        this.cashOverdraftInterestIncomeIContainer.add(this.cashOverdraftInterestIncomeField);
//        this.cashOverdraftInterestIncomeIContainer.newFeedback("cashOverdraftInterestIncomeFeedback", this.cashOverdraftInterestIncomeField);
//
//        this.row6Block1 = this.row6.newUIBlock("row6Block1", Size.Six_6);
//
//        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
//        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
//        this.form.add(this.advancedAccountingRuleBlock);
//        this.advancedAccountingRuleIContainer = new WebMarkupContainer("advancedAccountingRuleIContainer");
//        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleIContainer);
//
//        this.row7 = UIRow.newUIRow("row7", this.advancedAccountingRuleIContainer);
//
//        this.row7Block = this.row7.newUIBlock("row7Block", Size.Twelve_12);
//        this.row7IContainer = this.row7Block.newUIContainer("row7IContainer");
//
//        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
//        this.row7IContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
//        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
//        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
//        this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
//        this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
//        this.row7IContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);
//
//        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
//        this.row7IContainer.add(this.advancedAccountingRuleFeeIncomeTable);
//        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
//        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
//        this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
//        this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
//        this.row7IContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);
//
//        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
//        this.row7IContainer.add(this.advancedAccountingRuleFundSourceTable);
//        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
//        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
//        this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
//        this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
//        this.row7IContainer.add(this.advancedAccountingRuleFundSourceAddLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.cashOverdraftInterestIncomeField.add(new OnChangeAjaxBehavior());
//        this.cashOverdraftInterestIncomeField.setRequired(true);
//
//        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromFeeField.setRequired(true);
//
//        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
//        this.cashIncomeFromPenaltyField.setRequired(true);
//
//        this.cashWriteOffField.add(new OnChangeAjaxBehavior());
//        this.cashWriteOffField.setRequired(true);
//
//        this.cashInterestOnSavingField.add(new OnChangeAjaxBehavior());
//        this.cashInterestOnSavingField.setRequired(true);
//
//        this.cashSavingControlField.add(new OnChangeAjaxBehavior());
//        this.cashSavingControlField.setRequired(true);
//
//        this.cashSavingTransferInSuspenseField.add(new OnChangeAjaxBehavior());
//        this.cashSavingTransferInSuspenseField.setRequired(true);
//
//        this.cashOverdraftPortfolioField.add(new OnChangeAjaxBehavior());
//        this.cashOverdraftPortfolioField.setRequired(true);
//
//        this.cashSavingReferenceField.add(new OnChangeAjaxBehavior());
//        this.cashSavingReferenceField.setRequired(true);
//
//        this.accountingField.setRequired(true);
//
//        PropertyModel<Boolean> settingEnableDormancyTrackingValue = new PropertyModel<>(this.itemPage, "settingEnableDormancyTrackingValue");
//        this.cashEscheatLiabilityIContainer.setVisible(settingEnableDormancyTrackingValue.getObject() != null && settingEnableDormancyTrackingValue.getObject());
//
//        this.cashEscheatLiabilityField.add(new OnChangeAjaxBehavior());
//        this.cashEscheatLiabilityField.setRequired(true);
//
//        accountingFieldUpdate(null);
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_CHARGE);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
//        if ("fee".equals(popupName)) {
//            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//            Map<String, Object> item = Maps.newHashMap();
//            item.put("uuid", generator.externalId());
//            item.put("charge", this.popupModel.get("chargeValue"));
//            item.put("account", this.popupModel.get("accountValue"));
//            this.advancedAccountingRuleFeeIncomeValue.getObject().add(item);
//            target.add(this.advancedAccountingRuleFeeIncomeTable);
//        } else if ("penalty".equals(popupName)) {
//            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//            Map<String, Object> item = Maps.newHashMap();
//            item.put("uuid", generator.externalId());
//            item.put("charge", this.popupModel.get("chargeValue"));
//            item.put("account", this.popupModel.get("accountValue"));
//            this.advancedAccountingRulePenaltyIncomeValue.getObject().add(item);
//            target.add(this.advancedAccountingRulePenaltyIncomeTable);
//        } else if ("fund".equals(popupName)) {
//            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//            Map<String, Object> item = Maps.newHashMap();
//            item.put("uuid", generator.externalId());
//            item.put("payment", this.popupModel.get("paymentValue"));
//            item.put("account", this.popupModel.get("accountValue"));
//            this.advancedAccountingRuleFundSourceValue.getObject().add(item);
//            target.add(this.advancedAccountingRuleFundSourceTable);
//        }
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorAccounting.setObject(false);
//        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_PREVIEW);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorAccounting.setObject(true);
//    }
//
//    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
//        this.cashIContainer.setVisible(false);
//        this.advancedAccountingRuleIContainer.setVisible(false);
//        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
//        if (AccountingType.None.getDescription().equals(accountingValue.getObject()) || accountingValue.getObject() == null) {
//            this.advancedAccountingRuleIContainer.setVisible(false);
//        } else {
//            this.advancedAccountingRuleIContainer.setVisible(true);
//        }
//        if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
//            this.cashIContainer.setVisible(true);
//        }
//
//        if (target != null) {
//            target.add(this.cashBlock);
//            target.add(this.advancedAccountingRuleBlock);
//        }
//        return false;
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
//    protected List<ActionItem> advancedAccountingRulePenaltyIncomeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected List<ActionItem> advancedAccountingRuleFeeIncomeAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean advancedAccountingRulePenaltyIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        PropertyModel<Option> currencyCodeValue = new PropertyModel<>(this.itemPage, "currencyCodeValue");
//        if (currencyCodeValue.getObject() != null) {
//            this.modalWindow.setContent(new PenaltyChargePopup("penalty", this.popupModel, ProductPopup.Saving, currencyCodeValue.getObject().getId()));
//            this.modalWindow.show(target);
//        } else {
//            this.modalWindow.setContent(new CurrencyPopup("currency"));
//            this.modalWindow.show(target);
//        }
//        return false;
//    }
//
//    protected void advancedAccountingRulePenaltyIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRulePenaltyIncomeValue.getObject().size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRulePenaltyIncomeValue.getObject().get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRulePenaltyIncomeValue.getObject().remove(index);
//        }
//        target.add(this.advancedAccountingRulePenaltyIncomeTable);
//    }
//
//    protected boolean advancedAccountingRuleFeeIncomeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        PropertyModel<Option> currencyCodeValue = new PropertyModel<>(this.itemPage, "currencyCodeValue");
//        if (currencyCodeValue.getObject() != null) {
//            this.modalWindow.setContent(new FeeChargePopup("fee", this.popupModel, ProductPopup.Saving, currencyCodeValue.getObject().getId()));
//            this.modalWindow.show(target);
//        } else {
//            this.modalWindow.setContent(new CurrencyPopup("currency"));
//            this.modalWindow.show(target);
//        }
//        return false;
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
//    protected void advancedAccountingRuleFeeIncomeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRuleFeeIncomeValue.getObject().size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRuleFeeIncomeValue.getObject().get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRuleFeeIncomeValue.getObject().remove(index);
//        }
//        target.add(this.advancedAccountingRuleFeeIncomeTable);
//    }
//
//    protected void advancedAccountingRuleFundSourceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.advancedAccountingRuleFundSourceValue.getObject().size(); i++) {
//            Map<String, Object> column = this.advancedAccountingRuleFundSourceValue.getObject().get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.advancedAccountingRuleFundSourceValue.getObject().remove(index);
//        }
//        target.add(this.advancedAccountingRuleFundSourceTable);
//    }
//
//    protected List<ActionItem> advancedAccountingRuleFundSourceAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected boolean advancedAccountingRuleFundSourceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.modalWindow.setContent(new PaymentTypePopup("fund", this.popupModel));
//        this.modalWindow.show(target);
//        return false;
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
//}
