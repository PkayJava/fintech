package com.angkorteam.fintech.widget.product.loan;

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
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.popup.FeeChargePopup;
import com.angkorteam.fintech.popup.PaymentTypePopup;
import com.angkorteam.fintech.popup.PenaltyChargePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    // Accounting

    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashFundSourceBlock;
    protected WebMarkupContainer cashFundSourceIContainer;
    protected SingleChoiceProvider cashFundSourceProvider;
    protected Select2SingleChoice<Option> cashFundSourceField;
    protected TextFeedbackPanel cashFundSourceFeedback;

    protected WebMarkupBlock cashLoanPortfolioBlock;
    protected WebMarkupContainer cashLoanPortfolioIContainer;
    protected SingleChoiceProvider cashLoanPortfolioProvider;
    protected Select2SingleChoice<Option> cashLoanPortfolioField;
    protected TextFeedbackPanel cashLoanPortfolioFeedback;

    protected WebMarkupBlock cashTransferInSuspenseBlock;
    protected WebMarkupContainer cashTransferInSuspenseIContainer;
    protected SingleChoiceProvider cashTransferInSuspenseProvider;
    protected Select2SingleChoice<Option> cashTransferInSuspenseField;
    protected TextFeedbackPanel cashTransferInSuspenseFeedback;

    protected WebMarkupBlock cashIncomeFromInterestBlock;
    protected WebMarkupContainer cashIncomeFromInterestIContainer;
    protected SingleChoiceProvider cashIncomeFromInterestProvider;
    protected Select2SingleChoice<Option> cashIncomeFromInterestField;
    protected TextFeedbackPanel cashIncomeFromInterestFeedback;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    protected WebMarkupBlock cashIncomeFromPenaltyBlock;
    protected WebMarkupContainer cashIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;
    protected TextFeedbackPanel cashIncomeFromPenaltyFeedback;

    protected WebMarkupBlock cashIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer cashIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider cashIncomeFromRecoveryRepaymentProvider;
    protected Select2SingleChoice<Option> cashIncomeFromRecoveryRepaymentField;
    protected TextFeedbackPanel cashIncomeFromRecoveryRepaymentFeedback;

    protected WebMarkupBlock cashLossWrittenOffBlock;
    protected WebMarkupContainer cashLossWrittenOffIContainer;
    protected SingleChoiceProvider cashLossWrittenOffProvider;
    protected Select2SingleChoice<Option> cashLossWrittenOffField;
    protected TextFeedbackPanel cashLossWrittenOffFeedback;

    protected WebMarkupBlock cashOverPaymentLiabilityBlock;
    protected WebMarkupContainer cashOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider cashOverPaymentLiabilityProvider;
    protected Select2SingleChoice<Option> cashOverPaymentLiabilityField;
    protected TextFeedbackPanel cashOverPaymentLiabilityFeedback;

    protected WebMarkupContainer accrualBlock;
    protected WebMarkupContainer accrualIContainer;

    protected WebMarkupBlock accrualFundSourceBlock;
    protected WebMarkupContainer accrualFundSourceIContainer;
    protected SingleChoiceProvider accrualFundSourceProvider;
    protected Select2SingleChoice<Option> accrualFundSourceField;
    protected TextFeedbackPanel accrualFundSourceFeedback;

    protected WebMarkupBlock accrualLoanPortfolioBlock;
    protected WebMarkupContainer accrualLoanPortfolioIContainer;
    protected SingleChoiceProvider accrualLoanPortfolioProvider;
    protected Select2SingleChoice<Option> accrualLoanPortfolioField;
    protected TextFeedbackPanel accrualLoanPortfolioFeedback;

    protected WebMarkupBlock accrualInterestReceivableBlock;
    protected WebMarkupContainer accrualInterestReceivableIContainer;
    protected SingleChoiceProvider accrualInterestReceivableProvider;
    protected Select2SingleChoice<Option> accrualInterestReceivableField;
    protected TextFeedbackPanel accrualInterestReceivableFeedback;

    protected WebMarkupBlock accrualFeeReceivableBlock;
    protected WebMarkupContainer accrualFeeReceivableIContainer;
    protected SingleChoiceProvider accrualFeeReceivableProvider;
    protected Select2SingleChoice<Option> accrualFeeReceivableField;
    protected TextFeedbackPanel accrualFeeReceivableFeedback;

    protected WebMarkupBlock accrualPenaltyReceivableBlock;
    protected WebMarkupContainer accrualPenaltyReceivableIContainer;
    protected SingleChoiceProvider accrualPenaltyReceivableProvider;
    protected Select2SingleChoice<Option> accrualPenaltyReceivableField;
    protected TextFeedbackPanel accrualPenaltyReceivableFeedback;

    protected WebMarkupBlock accrualTransferInSuspenseBlock;
    protected WebMarkupContainer accrualTransferInSuspenseIContainer;
    protected SingleChoiceProvider accrualTransferInSuspenseProvider;
    protected Select2SingleChoice<Option> accrualTransferInSuspenseField;
    protected TextFeedbackPanel accrualTransferInSuspenseFeedback;

    protected WebMarkupBlock accrualIncomeFromInterestBlock;
    protected WebMarkupContainer accrualIncomeFromInterestIContainer;
    protected SingleChoiceProvider accrualIncomeFromInterestProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromInterestField;
    protected TextFeedbackPanel accrualIncomeFromInterestFeedback;

    protected WebMarkupBlock accrualIncomeFromFeeBlock;
    protected WebMarkupContainer accrualIncomeFromFeeIContainer;
    protected SingleChoiceProvider accrualIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromFeeField;
    protected TextFeedbackPanel accrualIncomeFromFeeFeedback;

    protected WebMarkupBlock accrualIncomeFromPenaltyBlock;
    protected WebMarkupContainer accrualIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider accrualIncomeFromPenaltyProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromPenaltyField;
    protected TextFeedbackPanel accrualIncomeFromPenaltyFeedback;

    protected WebMarkupBlock accrualIncomeFromRecoveryRepaymentBlock;
    protected WebMarkupContainer accrualIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider accrualIncomeFromRecoveryRepaymentProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromRecoveryRepaymentField;
    protected TextFeedbackPanel accrualIncomeFromRecoveryRepaymentFeedback;

    protected WebMarkupBlock accrualLossWrittenOffBlock;
    protected WebMarkupContainer accrualLossWrittenOffIContainer;
    protected SingleChoiceProvider accrualLossWrittenOffProvider;
    protected Select2SingleChoice<Option> accrualLossWrittenOffField;
    protected TextFeedbackPanel accrualLossWrittenOffFeedback;

    protected WebMarkupBlock accrualOverPaymentLiabilityBlock;
    protected WebMarkupContainer accrualOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider accrualOverPaymentLiabilityProvider;
    protected Select2SingleChoice<Option> accrualOverPaymentLiabilityField;
    protected TextFeedbackPanel accrualOverPaymentLiabilityFeedback;

    protected WebMarkupContainer advancedAccountingRuleBlock;
    protected WebMarkupContainer advancedAccountingRuleIContainer;

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
        this.accountingField.add(new Radio<>("accountingPeriodic", new Model<>(AccountingType.Periodic.getDescription())));
        this.accountingField.add(new Radio<>("accountingUpfront", new Model<>(AccountingType.Upfront.getDescription())));
        this.form.add(this.accountingField);

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);

        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        this.cashFundSourceBlock = new WebMarkupBlock("cashFundSourceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashFundSourceBlock);
        this.cashFundSourceIContainer = new WebMarkupContainer("cashFundSourceIContainer");
        this.cashFundSourceBlock.add(this.cashFundSourceIContainer);
        this.cashFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashFundSourceField = new Select2SingleChoice<>("cashFundSourceField", new PropertyModel<>(this.itemPage, "cashFundSourceValue"), this.cashFundSourceProvider);
        this.cashFundSourceField.setLabel(Model.of("Fund Source"));
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());
        this.cashFundSourceIContainer.add(this.cashFundSourceField);
        this.cashFundSourceFeedback = new TextFeedbackPanel("cashFundSourceFeedback", this.cashFundSourceField);
        this.cashFundSourceIContainer.add(this.cashFundSourceFeedback);

        this.cashLoanPortfolioBlock = new WebMarkupBlock("cashLoanPortfolioBlock", Size.Six_6);
        this.cashIContainer.add(this.cashLoanPortfolioBlock);
        this.cashLoanPortfolioIContainer = new WebMarkupContainer("cashLoanPortfolioIContainer");
        this.cashLoanPortfolioBlock.add(this.cashLoanPortfolioIContainer);
        this.cashLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField", new PropertyModel<>(this.itemPage, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.cashLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.cashLoanPortfolioIContainer.add(this.cashLoanPortfolioField);
        this.cashLoanPortfolioFeedback = new TextFeedbackPanel("cashLoanPortfolioFeedback", this.cashLoanPortfolioField);
        this.cashLoanPortfolioIContainer.add(this.cashLoanPortfolioFeedback);

        this.cashTransferInSuspenseBlock = new WebMarkupBlock("cashTransferInSuspenseBlock", Size.Six_6);
        this.cashIContainer.add(this.cashTransferInSuspenseBlock);
        this.cashTransferInSuspenseIContainer = new WebMarkupContainer("cashTransferInSuspenseIContainer");
        this.cashTransferInSuspenseBlock.add(this.cashTransferInSuspenseIContainer);
        this.cashTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashTransferInSuspenseField = new Select2SingleChoice<>("cashTransferInSuspenseField", new PropertyModel<>(this.itemPage, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
        this.cashTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.cashTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.cashTransferInSuspenseIContainer.add(this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseFeedback = new TextFeedbackPanel("cashTransferInSuspenseFeedback", this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseIContainer.add(this.cashTransferInSuspenseFeedback);

        this.cashIncomeFromInterestBlock = new WebMarkupBlock("cashIncomeFromInterestBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromInterestBlock);
        this.cashIncomeFromInterestIContainer = new WebMarkupContainer("cashIncomeFromInterestIContainer");
        this.cashIncomeFromInterestBlock.add(this.cashIncomeFromInterestIContainer);
        this.cashIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromInterestField = new Select2SingleChoice<>("cashIncomeFromInterestField", new PropertyModel<>(this.itemPage, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
        this.cashIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.cashIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromInterestIContainer.add(this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestFeedback = new TextFeedbackPanel("cashIncomeFromInterestFeedback", this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestIContainer.add(this.cashIncomeFromInterestFeedback);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeFeedback);

        this.cashIncomeFromPenaltyBlock = new WebMarkupBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromPenaltyBlock);
        this.cashIncomeFromPenaltyIContainer = new WebMarkupContainer("cashIncomeFromPenaltyIContainer");
        this.cashIncomeFromPenaltyBlock.add(this.cashIncomeFromPenaltyIContainer);
        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyField.setLabel(Model.of("Income from Penalty"));
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyFeedback = new TextFeedbackPanel("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyFeedback);

        this.cashIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromRecoveryRepaymentBlock);
        this.cashIncomeFromRecoveryRepaymentIContainer = new WebMarkupContainer("cashIncomeFromRecoveryRepaymentIContainer");
        this.cashIncomeFromRecoveryRepaymentBlock.add(this.cashIncomeFromRecoveryRepaymentIContainer);
        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this.itemPage, "cashIncomeFromRecoveryRepaymentValue"), this.cashIncomeFromRecoveryRepaymentProvider);
        this.cashIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.cashIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromRecoveryRepaymentIContainer.add(this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("cashIncomeFromRecoveryRepaymentFeedback", this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentIContainer.add(this.cashIncomeFromRecoveryRepaymentFeedback);

        this.cashLossWrittenOffBlock = new WebMarkupBlock("cashLossWrittenOffBlock", Size.Six_6);
        this.cashIContainer.add(this.cashLossWrittenOffBlock);
        this.cashLossWrittenOffIContainer = new WebMarkupContainer("cashLossWrittenOffIContainer");
        this.cashLossWrittenOffBlock.add(this.cashLossWrittenOffIContainer);
        this.cashLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
        this.cashLossWrittenOffField = new Select2SingleChoice<>("cashLossWrittenOffField", new PropertyModel<>(this.itemPage, "cashLossWrittenOffValue"), this.cashLossWrittenOffProvider);
        this.cashLossWrittenOffField.setLabel(Model.of("Loss written off"));
        this.cashLossWrittenOffField.add(new OnChangeAjaxBehavior());
        this.cashLossWrittenOffIContainer.add(this.cashLossWrittenOffField);
        this.cashLossWrittenOffFeedback = new TextFeedbackPanel("cashLossWrittenOffFeedback", this.cashLossWrittenOffField);
        this.cashLossWrittenOffIContainer.add(this.cashLossWrittenOffFeedback);

        this.cashOverPaymentLiabilityBlock = new WebMarkupBlock("cashOverPaymentLiabilityBlock", Size.Six_6);
        this.cashIContainer.add(this.cashOverPaymentLiabilityBlock);
        this.cashOverPaymentLiabilityIContainer = new WebMarkupContainer("cashOverPaymentLiabilityIContainer");
        this.cashOverPaymentLiabilityBlock.add(this.cashOverPaymentLiabilityIContainer);
        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.cashOverPaymentLiabilityField = new Select2SingleChoice<>("cashOverPaymentLiabilityField", new PropertyModel<>(this.itemPage, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
        this.cashOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.cashOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.cashOverPaymentLiabilityIContainer.add(this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityFeedback = new TextFeedbackPanel("cashOverPaymentLiabilityFeedback", this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityIContainer.add(this.cashOverPaymentLiabilityFeedback);

        this.accrualBlock = new WebMarkupContainer("accrualBlock");
        this.accrualBlock.setOutputMarkupId(true);
        this.form.add(this.accrualBlock);

        this.accrualIContainer = new WebMarkupContainer("accrualIContainer");
        this.accrualBlock.add(this.accrualIContainer);

        this.accrualFundSourceBlock = new WebMarkupBlock("accrualFundSourceBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualFundSourceBlock);
        this.accrualFundSourceIContainer = new WebMarkupContainer("accrualFundSourceIContainer");
        this.accrualFundSourceBlock.add(this.accrualFundSourceIContainer);
        this.accrualFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accrualFundSourceField = new Select2SingleChoice<>("accrualFundSourceField", new PropertyModel<>(this.itemPage, "accrualFundSourceValue"), this.accrualFundSourceProvider);
        this.accrualFundSourceField.setLabel(Model.of("Fund source"));
        this.accrualFundSourceField.add(new OnChangeAjaxBehavior());
        this.accrualFundSourceIContainer.add(this.accrualFundSourceField);
        this.accrualFundSourceFeedback = new TextFeedbackPanel("accrualFundSourceFeedback", this.accrualFundSourceField);
        this.accrualFundSourceIContainer.add(this.accrualFundSourceFeedback);

        this.accrualLoanPortfolioBlock = new WebMarkupBlock("accrualLoanPortfolioBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualLoanPortfolioBlock);
        this.accrualLoanPortfolioIContainer = new WebMarkupContainer("accrualLoanPortfolioIContainer");
        this.accrualLoanPortfolioBlock.add(this.accrualLoanPortfolioIContainer);
        this.accrualLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accrualLoanPortfolioField = new Select2SingleChoice<>("accrualLoanPortfolioField", new PropertyModel<>(this.itemPage, "accrualLoanPortfolioValue"), this.accrualLoanPortfolioProvider);
        this.accrualLoanPortfolioField.setLabel(Model.of("Loan portfolio"));
        this.accrualLoanPortfolioField.add(new OnChangeAjaxBehavior());
        this.accrualLoanPortfolioIContainer.add(this.accrualLoanPortfolioField);
        this.accrualLoanPortfolioFeedback = new TextFeedbackPanel("accrualLoanPortfolioFeedback", this.accrualLoanPortfolioField);
        this.accrualLoanPortfolioIContainer.add(this.accrualLoanPortfolioFeedback);

        this.accrualInterestReceivableBlock = new WebMarkupBlock("accrualInterestReceivableBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualInterestReceivableBlock);
        this.accrualInterestReceivableIContainer = new WebMarkupContainer("accrualInterestReceivableIContainer");
        this.accrualInterestReceivableBlock.add(this.accrualInterestReceivableIContainer);
        this.accrualInterestReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualInterestReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualInterestReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accrualInterestReceivableField = new Select2SingleChoice<>("accrualInterestReceivableField", new PropertyModel<>(this.itemPage, "accrualInterestReceivableValue"), this.accrualInterestReceivableProvider);
        this.accrualInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.accrualInterestReceivableField.add(new OnChangeAjaxBehavior());
        this.accrualInterestReceivableIContainer.add(this.accrualInterestReceivableField);
        this.accrualInterestReceivableFeedback = new TextFeedbackPanel("accrualInterestReceivableFeedback", this.accrualInterestReceivableField);
        this.accrualInterestReceivableIContainer.add(this.accrualInterestReceivableFeedback);

        this.accrualFeeReceivableBlock = new WebMarkupBlock("accrualFeeReceivableBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualFeeReceivableBlock);
        this.accrualFeeReceivableIContainer = new WebMarkupContainer("accrualFeeReceivableIContainer");
        this.accrualFeeReceivableBlock.add(this.accrualFeeReceivableIContainer);
        this.accrualFeeReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualFeeReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualFeeReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accrualFeeReceivableField = new Select2SingleChoice<>("accrualFeeReceivableField", new PropertyModel<>(this.itemPage, "accrualFeeReceivableValue"), this.accrualFeeReceivableProvider);
        this.accrualFeeReceivableField.setLabel(Model.of("Fee Receivable"));
        this.accrualFeeReceivableField.add(new OnChangeAjaxBehavior());
        this.accrualFeeReceivableIContainer.add(this.accrualFeeReceivableField);
        this.accrualFeeReceivableFeedback = new TextFeedbackPanel("accrualFeeReceivableFeedback", this.accrualFeeReceivableField);
        this.accrualFeeReceivableIContainer.add(this.accrualFeeReceivableFeedback);

        this.accrualPenaltyReceivableBlock = new WebMarkupBlock("accrualPenaltyReceivableBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualPenaltyReceivableBlock);
        this.accrualPenaltyReceivableIContainer = new WebMarkupContainer("accrualPenaltyReceivableIContainer");
        this.accrualPenaltyReceivableBlock.add(this.accrualPenaltyReceivableIContainer);
        this.accrualPenaltyReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accrualPenaltyReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accrualPenaltyReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.accrualPenaltyReceivableField = new Select2SingleChoice<>("accrualPenaltyReceivableField", new PropertyModel<>(this.itemPage, "accrualPenaltyReceivableValue"), this.accrualPenaltyReceivableProvider);
        this.accrualPenaltyReceivableField.setLabel(Model.of("Penalty Receivable"));
        this.accrualPenaltyReceivableField.add(new OnChangeAjaxBehavior());
        this.accrualPenaltyReceivableIContainer.add(this.accrualPenaltyReceivableField);
        this.accrualPenaltyReceivableFeedback = new TextFeedbackPanel("accrualPenaltyReceivableFeedback", this.accrualPenaltyReceivableField);
        this.accrualPenaltyReceivableIContainer.add(this.accrualPenaltyReceivableFeedback);

        this.accrualTransferInSuspenseBlock = new WebMarkupBlock("accrualTransferInSuspenseBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualTransferInSuspenseBlock);
        this.accrualTransferInSuspenseIContainer = new WebMarkupContainer("accrualTransferInSuspenseIContainer");
        this.accrualTransferInSuspenseBlock.add(this.accrualTransferInSuspenseIContainer);
        this.accrualTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accrualTransferInSuspenseField = new Select2SingleChoice<>("accrualTransferInSuspenseField", new PropertyModel<>(this.itemPage, "accrualTransferInSuspenseValue"), this.accrualTransferInSuspenseProvider);
        this.accrualTransferInSuspenseField.setLabel(Model.of("Transfer in suspense"));
        this.accrualTransferInSuspenseField.add(new OnChangeAjaxBehavior());
        this.accrualTransferInSuspenseIContainer.add(this.accrualTransferInSuspenseField);
        this.accrualTransferInSuspenseFeedback = new TextFeedbackPanel("accrualTransferInSuspenseFeedback", this.accrualTransferInSuspenseField);
        this.accrualTransferInSuspenseIContainer.add(this.accrualTransferInSuspenseFeedback);

        this.accrualIncomeFromInterestBlock = new WebMarkupBlock("accrualIncomeFromInterestBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualIncomeFromInterestBlock);
        this.accrualIncomeFromInterestIContainer = new WebMarkupContainer("accrualIncomeFromInterestIContainer");
        this.accrualIncomeFromInterestBlock.add(this.accrualIncomeFromInterestIContainer);
        this.accrualIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.accrualIncomeFromInterestField = new Select2SingleChoice<>("accrualIncomeFromInterestField", new PropertyModel<>(this.itemPage, "accrualIncomeFromInterestValue"), this.accrualIncomeFromInterestProvider);
        this.accrualIncomeFromInterestField.setLabel(Model.of("Income from Interest"));
        this.accrualIncomeFromInterestField.add(new OnChangeAjaxBehavior());
        this.accrualIncomeFromInterestIContainer.add(this.accrualIncomeFromInterestField);
        this.accrualIncomeFromInterestFeedback = new TextFeedbackPanel("accrualIncomeFromInterestFeedback", this.accrualIncomeFromInterestField);
        this.accrualIncomeFromInterestIContainer.add(this.accrualIncomeFromInterestFeedback);

        this.accrualIncomeFromFeeBlock = new WebMarkupBlock("accrualIncomeFromFeeBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualIncomeFromFeeBlock);
        this.accrualIncomeFromFeeIContainer = new WebMarkupContainer("accrualIncomeFromFeeIContainer");
        this.accrualIncomeFromFeeBlock.add(this.accrualIncomeFromFeeIContainer);
        this.accrualIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.accrualIncomeFromFeeField = new Select2SingleChoice<>("accrualIncomeFromFeeField", new PropertyModel<>(this.itemPage, "accrualIncomeFromFeeValue"), this.accrualIncomeFromFeeProvider);
        this.accrualIncomeFromFeeField.setLabel(Model.of("Income from fees"));
        this.accrualIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.accrualIncomeFromFeeIContainer.add(this.accrualIncomeFromFeeField);
        this.accrualIncomeFromFeeFeedback = new TextFeedbackPanel("accrualIncomeFromFeeFeedback", this.accrualIncomeFromFeeField);
        this.accrualIncomeFromFeeIContainer.add(this.accrualIncomeFromFeeFeedback);

        this.accrualIncomeFromPenaltyBlock = new WebMarkupBlock("accrualIncomeFromPenaltyBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualIncomeFromPenaltyBlock);
        this.accrualIncomeFromPenaltyIContainer = new WebMarkupContainer("accrualIncomeFromPenaltyIContainer");
        this.accrualIncomeFromPenaltyBlock.add(this.accrualIncomeFromPenaltyIContainer);
        this.accrualIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.accrualIncomeFromPenaltyField = new Select2SingleChoice<>("accrualIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "accrualIncomeFromPenaltyValue"), this.accrualIncomeFromPenaltyProvider);
        this.accrualIncomeFromPenaltyField.setLabel(Model.of("Income from Penalty"));
        this.accrualIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());
        this.accrualIncomeFromPenaltyIContainer.add(this.accrualIncomeFromPenaltyField);
        this.accrualIncomeFromPenaltyFeedback = new TextFeedbackPanel("accrualIncomeFromPenaltyFeedback", this.accrualIncomeFromPenaltyField);
        this.accrualIncomeFromPenaltyIContainer.add(this.accrualIncomeFromPenaltyFeedback);

        this.accrualIncomeFromRecoveryRepaymentBlock = new WebMarkupBlock("accrualIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualIncomeFromRecoveryRepaymentBlock);
        this.accrualIncomeFromRecoveryRepaymentIContainer = new WebMarkupContainer("accrualIncomeFromRecoveryRepaymentIContainer");
        this.accrualIncomeFromRecoveryRepaymentBlock.add(this.accrualIncomeFromRecoveryRepaymentIContainer);
        this.accrualIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.accrualIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("accrualIncomeFromRecoveryRepaymentField", new PropertyModel<>(this.itemPage, "accrualIncomeFromRecoveryRepaymentValue"), this.accrualIncomeFromRecoveryRepaymentProvider);
        this.accrualIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.accrualIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());
        this.accrualIncomeFromRecoveryRepaymentIContainer.add(this.accrualIncomeFromRecoveryRepaymentField);
        this.accrualIncomeFromRecoveryRepaymentFeedback = new TextFeedbackPanel("accrualIncomeFromRecoveryRepaymentFeedback", this.accrualIncomeFromRecoveryRepaymentField);
        this.accrualIncomeFromRecoveryRepaymentIContainer.add(this.accrualIncomeFromRecoveryRepaymentFeedback);

        this.accrualLossWrittenOffBlock = new WebMarkupBlock("accrualLossWrittenOffBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualLossWrittenOffBlock);
        this.accrualLossWrittenOffIContainer = new WebMarkupContainer("accrualLossWrittenOffIContainer");
        this.accrualLossWrittenOffBlock.add(this.accrualLossWrittenOffIContainer);
        this.accrualLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());
        this.accrualLossWrittenOffField = new Select2SingleChoice<>("accrualLossWrittenOffField", new PropertyModel<>(this.itemPage, "accrualLossWrittenOffValue"), this.accrualLossWrittenOffProvider);
        this.accrualLossWrittenOffField.setLabel(Model.of("Loss written off"));
        this.accrualLossWrittenOffField.add(new OnChangeAjaxBehavior());
        this.accrualLossWrittenOffIContainer.add(this.accrualLossWrittenOffField);
        this.accrualLossWrittenOffFeedback = new TextFeedbackPanel("accrualLossWrittenOffFeedback", this.accrualLossWrittenOffField);
        this.accrualLossWrittenOffIContainer.add(this.accrualLossWrittenOffFeedback);

        this.accrualOverPaymentLiabilityBlock = new WebMarkupBlock("accrualOverPaymentLiabilityBlock", Size.Six_6);
        this.accrualIContainer.add(this.accrualOverPaymentLiabilityBlock);
        this.accrualOverPaymentLiabilityIContainer = new WebMarkupContainer("accrualOverPaymentLiabilityIContainer");
        this.accrualOverPaymentLiabilityBlock.add(this.accrualOverPaymentLiabilityIContainer);
        this.accrualOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.accrualOverPaymentLiabilityField = new Select2SingleChoice<>("accrualOverPaymentLiabilityField", new PropertyModel<>(this.itemPage, "accrualOverPaymentLiabilityValue"), this.accrualOverPaymentLiabilityProvider);
        this.accrualOverPaymentLiabilityField.setLabel(Model.of("Over payment liability"));
        this.accrualOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());
        this.accrualOverPaymentLiabilityIContainer.add(this.accrualOverPaymentLiabilityField);
        this.accrualOverPaymentLiabilityFeedback = new TextFeedbackPanel("accrualOverPaymentLiabilityFeedback", this.accrualOverPaymentLiabilityField);
        this.accrualOverPaymentLiabilityIContainer.add(this.accrualOverPaymentLiabilityFeedback);

        this.advancedAccountingRuleBlock = new WebMarkupContainer("advancedAccountingRuleBlock");
        this.advancedAccountingRuleBlock.setOutputMarkupId(true);
        this.form.add(this.advancedAccountingRuleBlock);
        this.advancedAccountingRuleIContainer = new WebMarkupContainer("advancedAccountingRuleIContainer");
        this.advancedAccountingRuleBlock.add(this.advancedAccountingRuleIContainer);

        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
        this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
        this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRulePenaltyIncomeAddLink);

        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
        this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
        this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFeeIncomeAddLink);

        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
        this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
        this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
        this.advancedAccountingRuleIContainer.add(this.advancedAccountingRuleFundSourceAddLink);
    }

    @Override
    protected void configureMetaData() {
        this.accountingField.setRequired(true);
        accountingFieldUpdate(null);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CHARGE);
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

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_PREVIEW);
        this.errorAccounting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorAccounting.setObject(true);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        this.cashIContainer.setVisible(false);
        this.accrualIContainer.setVisible(false);
        this.advancedAccountingRuleIContainer.setVisible(false);
        if (AccountingType.None.getDescription().equals(accountingValue.getObject()) || accountingValue.getObject() == null) {
            this.advancedAccountingRuleIContainer.setVisible(false);
        } else {
            this.advancedAccountingRuleIContainer.setVisible(true);
        }
        if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
            this.cashIContainer.setVisible(true);
        } else if (AccountingType.Periodic.getDescription().equals(accountingValue.getObject()) || AccountingType.Upfront.getDescription().equals(accountingValue.getObject())) {
            this.accrualIContainer.setVisible(true);
        }
        if (target != null) {
            target.add(this.cashBlock);
            target.add(this.accrualBlock);
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
            this.modalWindow.setContent(new PenaltyChargePopup("penalty", this.popupModel, ProductPopup.Loan, currencyCodeValue.getObject().getId()));
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
            this.modalWindow.setContent(new FeeChargePopup("fee", this.popupModel, ProductPopup.Loan, currencyCodeValue.getObject().getId()));
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
