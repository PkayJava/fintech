package com.angkorteam.fintech.widget.product.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
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
import com.angkorteam.fintech.layout.SectionBlock;
import com.angkorteam.fintech.layout.SectionContainer;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
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

    protected SectionBlock cashBlock;
    protected SectionContainer cashIContainer;

    protected UIRow row2;

    protected UIBlock cashFundSourceBlock;
    protected UIContainer cashFundSourceIContainer;
    protected SingleChoiceProvider cashFundSourceProvider;
    protected Select2SingleChoice<Option> cashFundSourceField;

    protected UIBlock cashLoanPortfolioBlock;
    protected UIContainer cashLoanPortfolioIContainer;
    protected SingleChoiceProvider cashLoanPortfolioProvider;
    protected Select2SingleChoice<Option> cashLoanPortfolioField;

    protected UIRow row3;

    protected UIBlock cashTransferInSuspenseBlock;
    protected UIContainer cashTransferInSuspenseIContainer;
    protected SingleChoiceProvider cashTransferInSuspenseProvider;
    protected Select2SingleChoice<Option> cashTransferInSuspenseField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock cashIncomeFromInterestBlock;
    protected UIContainer cashIncomeFromInterestIContainer;
    protected SingleChoiceProvider cashIncomeFromInterestProvider;
    protected Select2SingleChoice<Option> cashIncomeFromInterestField;

    protected UIBlock cashIncomeFromFeeBlock;
    protected UIContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;

    protected UIRow row5;

    protected UIBlock cashIncomeFromPenaltyBlock;
    protected UIContainer cashIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider cashIncomeFromPenaltyProvider;
    protected Select2SingleChoice<Option> cashIncomeFromPenaltyField;

    protected UIBlock cashIncomeFromRecoveryRepaymentBlock;
    protected UIContainer cashIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider cashIncomeFromRecoveryRepaymentProvider;
    protected Select2SingleChoice<Option> cashIncomeFromRecoveryRepaymentField;

    protected UIRow row6;

    protected UIBlock cashLossWrittenOffBlock;
    protected UIContainer cashLossWrittenOffIContainer;
    protected SingleChoiceProvider cashLossWrittenOffProvider;
    protected Select2SingleChoice<Option> cashLossWrittenOffField;

    protected UIBlock row6Block1;

    protected UIRow row7;

    protected UIBlock cashOverPaymentLiabilityBlock;
    protected UIContainer cashOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider cashOverPaymentLiabilityProvider;
    protected Select2SingleChoice<Option> cashOverPaymentLiabilityField;

    protected UIBlock row7Block1;

    protected SectionBlock accrualBlock;
    protected SectionContainer accrualIContainer;

    protected UIRow row8;

    protected UIBlock accrualFundSourceBlock;
    protected UIContainer accrualFundSourceIContainer;
    protected SingleChoiceProvider accrualFundSourceProvider;
    protected Select2SingleChoice<Option> accrualFundSourceField;

    protected UIBlock accrualLoanPortfolioBlock;
    protected UIContainer accrualLoanPortfolioIContainer;
    protected SingleChoiceProvider accrualLoanPortfolioProvider;
    protected Select2SingleChoice<Option> accrualLoanPortfolioField;

    protected UIRow row9;

    protected UIBlock accrualInterestReceivableBlock;
    protected UIContainer accrualInterestReceivableIContainer;
    protected SingleChoiceProvider accrualInterestReceivableProvider;
    protected Select2SingleChoice<Option> accrualInterestReceivableField;

    protected UIBlock accrualFeeReceivableBlock;
    protected UIContainer accrualFeeReceivableIContainer;
    protected SingleChoiceProvider accrualFeeReceivableProvider;
    protected Select2SingleChoice<Option> accrualFeeReceivableField;

    protected UIRow row10;

    protected UIBlock accrualPenaltyReceivableBlock;
    protected UIContainer accrualPenaltyReceivableIContainer;
    protected SingleChoiceProvider accrualPenaltyReceivableProvider;
    protected Select2SingleChoice<Option> accrualPenaltyReceivableField;

    protected UIBlock accrualTransferInSuspenseBlock;
    protected UIContainer accrualTransferInSuspenseIContainer;
    protected SingleChoiceProvider accrualTransferInSuspenseProvider;
    protected Select2SingleChoice<Option> accrualTransferInSuspenseField;

    protected UIRow row11;

    protected UIBlock accrualIncomeFromInterestBlock;
    protected UIContainer accrualIncomeFromInterestIContainer;
    protected SingleChoiceProvider accrualIncomeFromInterestProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromInterestField;

    protected UIBlock accrualIncomeFromFeeBlock;
    protected UIContainer accrualIncomeFromFeeIContainer;
    protected SingleChoiceProvider accrualIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromFeeField;

    protected UIRow row12;

    protected UIBlock accrualIncomeFromPenaltyBlock;
    protected UIContainer accrualIncomeFromPenaltyIContainer;
    protected SingleChoiceProvider accrualIncomeFromPenaltyProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromPenaltyField;

    protected UIBlock accrualIncomeFromRecoveryRepaymentBlock;
    protected UIContainer accrualIncomeFromRecoveryRepaymentIContainer;
    protected SingleChoiceProvider accrualIncomeFromRecoveryRepaymentProvider;
    protected Select2SingleChoice<Option> accrualIncomeFromRecoveryRepaymentField;

    protected UIRow row13;

    protected UIBlock accrualLossWrittenOffBlock;
    protected UIContainer accrualLossWrittenOffIContainer;
    protected SingleChoiceProvider accrualLossWrittenOffProvider;
    protected Select2SingleChoice<Option> accrualLossWrittenOffField;

    protected UIBlock row13Block1;

    protected UIRow row14;

    protected UIBlock accrualOverPaymentLiabilityBlock;
    protected UIContainer accrualOverPaymentLiabilityIContainer;
    protected SingleChoiceProvider accrualOverPaymentLiabilityProvider;
    protected Select2SingleChoice<Option> accrualOverPaymentLiabilityField;

    protected UIBlock row14Block1;

    protected SectionBlock advancedAccountingRuleBlock;
    protected SectionContainer advancedAccountingRuleIContainer;

    protected UIRow row1;

    protected UIBlock row1Block1;

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

        this.cashFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.cashLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.cashTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.cashIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.cashIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.cashIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.cashLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());

        this.cashOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());

        this.accrualFundSourceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualFundSourceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualFundSourceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.accrualLoanPortfolioProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualLoanPortfolioProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualLoanPortfolioProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.accrualInterestReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualInterestReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualInterestReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.accrualFeeReceivableProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualFeeReceivableProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualFeeReceivableProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.accrualPenaltyReceivableProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accrualPenaltyReceivableProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accrualPenaltyReceivableProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());

        this.accrualTransferInSuspenseProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualTransferInSuspenseProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualTransferInSuspenseProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.accrualIncomeFromInterestProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromInterestProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromInterestProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.accrualIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.accrualIncomeFromPenaltyProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromPenaltyProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromPenaltyProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.accrualIncomeFromRecoveryRepaymentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualIncomeFromRecoveryRepaymentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualIncomeFromRecoveryRepaymentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());

        this.accrualLossWrittenOffProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualLossWrittenOffProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualLossWrittenOffProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Expense.getLiteral());

        this.accrualOverPaymentLiabilityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accrualOverPaymentLiabilityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accrualOverPaymentLiabilityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());

    }

    @Override
    protected void configureMetaData() {
        this.accountingField.setRequired(true);
        accountingFieldUpdate(null);

        this.cashFundSourceField.setLabel(Model.of("Fund Source"));
        this.cashFundSourceField.add(new OnChangeAjaxBehavior());

        this.cashLoanPortfolioField.setLabel(Model.of("Loan Portfolio"));
        this.cashLoanPortfolioField.add(new OnChangeAjaxBehavior());

        this.cashTransferInSuspenseField.setLabel(Model.of("Transfer In Suspense"));
        this.cashTransferInSuspenseField.add(new OnChangeAjaxBehavior());

        this.cashIncomeFromInterestField.setLabel(Model.of("Income From Interest"));
        this.cashIncomeFromInterestField.add(new OnChangeAjaxBehavior());

        this.cashIncomeFromFeeField.setLabel(Model.of("Income From Fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());

        this.cashIncomeFromPenaltyField.setLabel(Model.of("Income From Penalty"));
        this.cashIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());

        this.cashIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income from Recovery Repayments"));
        this.cashIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());

        this.cashLossWrittenOffField.setLabel(Model.of("Loss Written Off"));
        this.cashLossWrittenOffField.add(new OnChangeAjaxBehavior());

        this.cashOverPaymentLiabilityField.setLabel(Model.of("Over Payment Liability"));
        this.cashOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());

        this.accrualFundSourceField.setLabel(Model.of("Fund Source"));
        this.accrualFundSourceField.add(new OnChangeAjaxBehavior());

        this.accrualLoanPortfolioField.setLabel(Model.of("Loan Portfolio"));
        this.accrualLoanPortfolioField.add(new OnChangeAjaxBehavior());

        this.accrualInterestReceivableField.setLabel(Model.of("Interest Receivable"));
        this.accrualInterestReceivableField.add(new OnChangeAjaxBehavior());

        this.accrualFeeReceivableField.setLabel(Model.of("Fee Receivable"));
        this.accrualFeeReceivableField.add(new OnChangeAjaxBehavior());

        this.accrualPenaltyReceivableField.setLabel(Model.of("Penalty Receivable"));
        this.accrualPenaltyReceivableField.add(new OnChangeAjaxBehavior());

        this.accrualTransferInSuspenseField.setLabel(Model.of("Transfer In Suspense"));
        this.accrualTransferInSuspenseField.add(new OnChangeAjaxBehavior());

        this.accrualIncomeFromInterestField.setLabel(Model.of("Income From Interest"));
        this.accrualIncomeFromInterestField.add(new OnChangeAjaxBehavior());

        this.accrualIncomeFromFeeField.setLabel(Model.of("Income From Fees"));
        this.accrualIncomeFromFeeField.add(new OnChangeAjaxBehavior());

        this.accrualIncomeFromPenaltyField.setLabel(Model.of("Income From Penalty"));
        this.accrualIncomeFromPenaltyField.add(new OnChangeAjaxBehavior());

        this.accrualIncomeFromRecoveryRepaymentField.setLabel(Model.of("Income From Recovery Repayments"));
        this.accrualIncomeFromRecoveryRepaymentField.add(new OnChangeAjaxBehavior());

        this.accrualLossWrittenOffField.setLabel(Model.of("Loss Written Off"));
        this.accrualLossWrittenOffField.add(new OnChangeAjaxBehavior());

        this.accrualOverPaymentLiabilityField.setLabel(Model.of("Over Payment Liability"));
        this.accrualOverPaymentLiabilityField.add(new OnChangeAjaxBehavior());

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

        this.cashBlock = SectionBlock.newSectionBlock("cashBlock", this.form);
        this.cashIContainer = this.cashBlock.newSectionContainer("cashIContainer");

        this.row2 = this.cashIContainer.newUIRow("row2");

        this.cashFundSourceBlock = this.row2.newUIBlock("cashFundSourceBlock", Size.Six_6);
        this.cashFundSourceIContainer = this.cashFundSourceBlock.newUIContainer("cashFundSourceIContainer");
        this.cashFundSourceField = new Select2SingleChoice<>("cashFundSourceField", new PropertyModel<>(this.itemPage, "cashFundSourceValue"), this.cashFundSourceProvider);
        this.cashFundSourceIContainer.add(this.cashFundSourceField);
        this.cashFundSourceIContainer.newFeedback("cashFundSourceFeedback", this.cashFundSourceField);

        this.cashLoanPortfolioBlock = this.row2.newUIBlock("cashLoanPortfolioBlock", Size.Six_6);
        this.cashLoanPortfolioIContainer = this.cashLoanPortfolioBlock.newUIContainer("cashLoanPortfolioIContainer");
        this.cashLoanPortfolioField = new Select2SingleChoice<>("cashLoanPortfolioField", new PropertyModel<>(this.itemPage, "cashLoanPortfolioValue"), this.cashLoanPortfolioProvider);
        this.cashLoanPortfolioIContainer.add(this.cashLoanPortfolioField);
        this.cashLoanPortfolioIContainer.newFeedback("cashLoanPortfolioFeedback", this.cashLoanPortfolioField);

        this.row3 = this.cashIContainer.newUIRow("row3");

        this.cashTransferInSuspenseBlock = this.row3.newUIBlock("cashTransferInSuspenseBlock", Size.Six_6);
        this.cashTransferInSuspenseIContainer = this.cashTransferInSuspenseBlock.newUIContainer("cashTransferInSuspenseIContainer");
        this.cashTransferInSuspenseField = new Select2SingleChoice<>("cashTransferInSuspenseField", new PropertyModel<>(this.itemPage, "cashTransferInSuspenseValue"), this.cashTransferInSuspenseProvider);
        this.cashTransferInSuspenseIContainer.add(this.cashTransferInSuspenseField);
        this.cashTransferInSuspenseIContainer.newFeedback("cashTransferInSuspenseFeedback", this.cashTransferInSuspenseField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = this.cashIContainer.newUIRow("row4");

        this.cashIncomeFromInterestBlock = this.row4.newUIBlock("cashIncomeFromInterestBlock", Size.Six_6);
        this.cashIncomeFromInterestIContainer = this.cashIncomeFromInterestBlock.newUIContainer("cashIncomeFromInterestIContainer");
        this.cashIncomeFromInterestField = new Select2SingleChoice<>("cashIncomeFromInterestField", new PropertyModel<>(this.itemPage, "cashIncomeFromInterestValue"), this.cashIncomeFromInterestProvider);
        this.cashIncomeFromInterestIContainer.add(this.cashIncomeFromInterestField);
        this.cashIncomeFromInterestIContainer.newFeedback("cashIncomeFromInterestFeedback", this.cashIncomeFromInterestField);

        this.cashIncomeFromFeeBlock = this.row4.newUIBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIncomeFromFeeIContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.newFeedback("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);

        this.row5 = this.cashIContainer.newUIRow("row5");

        this.cashIncomeFromPenaltyBlock = this.row5.newUIBlock("cashIncomeFromPenaltyBlock", Size.Six_6);
        this.cashIncomeFromPenaltyIContainer = this.cashIncomeFromPenaltyBlock.newUIContainer("cashIncomeFromPenaltyIContainer");
        this.cashIncomeFromPenaltyField = new Select2SingleChoice<>("cashIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "cashIncomeFromPenaltyValue"), this.cashIncomeFromPenaltyProvider);
        this.cashIncomeFromPenaltyIContainer.add(this.cashIncomeFromPenaltyField);
        this.cashIncomeFromPenaltyIContainer.newFeedback("cashIncomeFromPenaltyFeedback", this.cashIncomeFromPenaltyField);

        this.cashIncomeFromRecoveryRepaymentBlock = this.row5.newUIBlock("cashIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.cashIncomeFromRecoveryRepaymentIContainer = this.cashIncomeFromRecoveryRepaymentBlock.newUIContainer("cashIncomeFromRecoveryRepaymentIContainer");
        this.cashIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("cashIncomeFromRecoveryRepaymentField", new PropertyModel<>(this.itemPage, "cashIncomeFromRecoveryRepaymentValue"), this.cashIncomeFromRecoveryRepaymentProvider);
        this.cashIncomeFromRecoveryRepaymentIContainer.add(this.cashIncomeFromRecoveryRepaymentField);
        this.cashIncomeFromRecoveryRepaymentIContainer.newFeedback("cashIncomeFromRecoveryRepaymentFeedback", this.cashIncomeFromRecoveryRepaymentField);

        this.row6 = this.cashIContainer.newUIRow("row6");

        this.cashLossWrittenOffBlock = this.row6.newUIBlock("cashLossWrittenOffBlock", Size.Six_6);
        this.cashLossWrittenOffIContainer = this.cashLossWrittenOffBlock.newUIContainer("cashLossWrittenOffIContainer");
        this.cashLossWrittenOffField = new Select2SingleChoice<>("cashLossWrittenOffField", new PropertyModel<>(this.itemPage, "cashLossWrittenOffValue"), this.cashLossWrittenOffProvider);
        this.cashLossWrittenOffIContainer.add(this.cashLossWrittenOffField);
        this.cashLossWrittenOffIContainer.newFeedback("cashLossWrittenOffFeedback", this.cashLossWrittenOffField);

        this.row6Block1 = this.row6.newUIBlock("row6Block1", Size.Six_6);

        this.row7 = this.cashIContainer.newUIRow("row7");

        this.cashOverPaymentLiabilityBlock = this.row7.newUIBlock("cashOverPaymentLiabilityBlock", Size.Six_6);
        this.cashOverPaymentLiabilityIContainer = this.cashOverPaymentLiabilityBlock.newUIContainer("cashOverPaymentLiabilityIContainer");
        this.cashOverPaymentLiabilityField = new Select2SingleChoice<>("cashOverPaymentLiabilityField", new PropertyModel<>(this.itemPage, "cashOverPaymentLiabilityValue"), this.cashOverPaymentLiabilityProvider);
        this.cashOverPaymentLiabilityIContainer.add(this.cashOverPaymentLiabilityField);
        this.cashOverPaymentLiabilityIContainer.newFeedback("cashOverPaymentLiabilityFeedback", this.cashOverPaymentLiabilityField);

        this.row7Block1 = this.row7.newUIBlock("row7Block1", Size.Six_6);

        this.accrualBlock = SectionBlock.newSectionBlock("accrualBlock", this.form);
        this.accrualIContainer = this.accrualBlock.newSectionContainer("accrualIContainer");

        this.row8 = this.accrualIContainer.newUIRow("row8");

        this.accrualFundSourceBlock = this.row8.newUIBlock("accrualFundSourceBlock", Size.Six_6);
        this.accrualFundSourceIContainer = this.accrualFundSourceBlock.newUIContainer("accrualFundSourceIContainer");
        this.accrualFundSourceField = new Select2SingleChoice<>("accrualFundSourceField", new PropertyModel<>(this.itemPage, "accrualFundSourceValue"), this.accrualFundSourceProvider);
        this.accrualFundSourceIContainer.add(this.accrualFundSourceField);
        this.accrualFundSourceIContainer.newFeedback("accrualFundSourceFeedback", this.accrualFundSourceField);

        this.accrualLoanPortfolioBlock = this.row8.newUIBlock("accrualLoanPortfolioBlock", Size.Six_6);
        this.accrualLoanPortfolioIContainer = this.accrualLoanPortfolioBlock.newUIContainer("accrualLoanPortfolioIContainer");
        this.accrualLoanPortfolioField = new Select2SingleChoice<>("accrualLoanPortfolioField", new PropertyModel<>(this.itemPage, "accrualLoanPortfolioValue"), this.accrualLoanPortfolioProvider);
        this.accrualLoanPortfolioIContainer.add(this.accrualLoanPortfolioField);
        this.accrualLoanPortfolioIContainer.newFeedback("accrualLoanPortfolioFeedback", this.accrualLoanPortfolioField);

        this.row9 = this.accrualIContainer.newUIRow("row9");

        this.accrualInterestReceivableBlock = this.row9.newUIBlock("accrualInterestReceivableBlock", Size.Six_6);
        this.accrualInterestReceivableIContainer = this.accrualInterestReceivableBlock.newUIContainer("accrualInterestReceivableIContainer");
        this.accrualInterestReceivableField = new Select2SingleChoice<>("accrualInterestReceivableField", new PropertyModel<>(this.itemPage, "accrualInterestReceivableValue"), this.accrualInterestReceivableProvider);
        this.accrualInterestReceivableIContainer.add(this.accrualInterestReceivableField);
        this.accrualInterestReceivableIContainer.newFeedback("accrualInterestReceivableFeedback", this.accrualInterestReceivableField);

        this.accrualFeeReceivableBlock = this.row9.newUIBlock("accrualFeeReceivableBlock", Size.Six_6);
        this.accrualFeeReceivableIContainer = this.accrualFeeReceivableBlock.newUIContainer("accrualFeeReceivableIContainer");
        this.accrualFeeReceivableField = new Select2SingleChoice<>("accrualFeeReceivableField", new PropertyModel<>(this.itemPage, "accrualFeeReceivableValue"), this.accrualFeeReceivableProvider);
        this.accrualFeeReceivableIContainer.add(this.accrualFeeReceivableField);
        this.accrualFeeReceivableIContainer.newFeedback("accrualFeeReceivableFeedback", this.accrualFeeReceivableField);

        this.row10 = this.accrualIContainer.newUIRow("row10");

        this.accrualPenaltyReceivableBlock = this.row10.newUIBlock("accrualPenaltyReceivableBlock", Size.Six_6);
        this.accrualPenaltyReceivableIContainer = this.accrualPenaltyReceivableBlock.newUIContainer("accrualPenaltyReceivableIContainer");
        this.accrualPenaltyReceivableField = new Select2SingleChoice<>("accrualPenaltyReceivableField", new PropertyModel<>(this.itemPage, "accrualPenaltyReceivableValue"), this.accrualPenaltyReceivableProvider);
        this.accrualPenaltyReceivableIContainer.add(this.accrualPenaltyReceivableField);
        this.accrualPenaltyReceivableIContainer.newFeedback("accrualPenaltyReceivableFeedback", this.accrualPenaltyReceivableField);

        this.accrualTransferInSuspenseBlock = this.row10.newUIBlock("accrualTransferInSuspenseBlock", Size.Six_6);
        this.accrualTransferInSuspenseIContainer = this.accrualTransferInSuspenseBlock.newUIContainer("accrualTransferInSuspenseIContainer");
        this.accrualTransferInSuspenseField = new Select2SingleChoice<>("accrualTransferInSuspenseField", new PropertyModel<>(this.itemPage, "accrualTransferInSuspenseValue"), this.accrualTransferInSuspenseProvider);
        this.accrualTransferInSuspenseIContainer.add(this.accrualTransferInSuspenseField);
        this.accrualTransferInSuspenseIContainer.newFeedback("accrualTransferInSuspenseFeedback", this.accrualTransferInSuspenseField);

        this.row11 = this.accrualIContainer.newUIRow("row11");

        this.accrualIncomeFromInterestBlock = this.row11.newUIBlock("accrualIncomeFromInterestBlock", Size.Six_6);
        this.accrualIncomeFromInterestIContainer = this.accrualIncomeFromInterestBlock.newUIContainer("accrualIncomeFromInterestIContainer");
        this.accrualIncomeFromInterestField = new Select2SingleChoice<>("accrualIncomeFromInterestField", new PropertyModel<>(this.itemPage, "accrualIncomeFromInterestValue"), this.accrualIncomeFromInterestProvider);
        this.accrualIncomeFromInterestIContainer.add(this.accrualIncomeFromInterestField);
        this.accrualIncomeFromInterestIContainer.newFeedback("accrualIncomeFromInterestFeedback", this.accrualIncomeFromInterestField);

        this.accrualIncomeFromFeeBlock = this.row11.newUIBlock("accrualIncomeFromFeeBlock", Size.Six_6);
        this.accrualIncomeFromFeeIContainer = this.accrualIncomeFromFeeBlock.newUIContainer("accrualIncomeFromFeeIContainer");
        this.accrualIncomeFromFeeField = new Select2SingleChoice<>("accrualIncomeFromFeeField", new PropertyModel<>(this.itemPage, "accrualIncomeFromFeeValue"), this.accrualIncomeFromFeeProvider);
        this.accrualIncomeFromFeeIContainer.add(this.accrualIncomeFromFeeField);
        this.accrualIncomeFromFeeIContainer.newFeedback("accrualIncomeFromFeeFeedback", this.accrualIncomeFromFeeField);

        this.row12 = this.accrualIContainer.newUIRow("row12");

        this.accrualIncomeFromPenaltyBlock = this.row12.newUIBlock("accrualIncomeFromPenaltyBlock", Size.Six_6);
        this.accrualIncomeFromPenaltyIContainer = this.accrualIncomeFromPenaltyBlock.newUIContainer("accrualIncomeFromPenaltyIContainer");
        this.accrualIncomeFromPenaltyField = new Select2SingleChoice<>("accrualIncomeFromPenaltyField", new PropertyModel<>(this.itemPage, "accrualIncomeFromPenaltyValue"), this.accrualIncomeFromPenaltyProvider);
        this.accrualIncomeFromPenaltyIContainer.add(this.accrualIncomeFromPenaltyField);
        this.accrualIncomeFromPenaltyIContainer.newFeedback("accrualIncomeFromPenaltyFeedback", this.accrualIncomeFromPenaltyField);

        this.accrualIncomeFromRecoveryRepaymentBlock = this.row12.newUIBlock("accrualIncomeFromRecoveryRepaymentBlock", Size.Six_6);
        this.accrualIncomeFromRecoveryRepaymentIContainer = this.accrualIncomeFromRecoveryRepaymentBlock.newUIContainer("accrualIncomeFromRecoveryRepaymentIContainer");
        this.accrualIncomeFromRecoveryRepaymentField = new Select2SingleChoice<>("accrualIncomeFromRecoveryRepaymentField", new PropertyModel<>(this.itemPage, "accrualIncomeFromRecoveryRepaymentValue"), this.accrualIncomeFromRecoveryRepaymentProvider);
        this.accrualIncomeFromRecoveryRepaymentIContainer.add(this.accrualIncomeFromRecoveryRepaymentField);
        this.accrualIncomeFromRecoveryRepaymentIContainer.newFeedback("accrualIncomeFromRecoveryRepaymentFeedback", this.accrualIncomeFromRecoveryRepaymentField);

        this.row13 = this.accrualIContainer.newUIRow("row13");

        this.accrualLossWrittenOffBlock = this.row13.newUIBlock("accrualLossWrittenOffBlock", Size.Six_6);
        this.accrualLossWrittenOffIContainer = this.accrualLossWrittenOffBlock.newUIContainer("accrualLossWrittenOffIContainer");
        this.accrualLossWrittenOffField = new Select2SingleChoice<>("accrualLossWrittenOffField", new PropertyModel<>(this.itemPage, "accrualLossWrittenOffValue"), this.accrualLossWrittenOffProvider);
        this.accrualLossWrittenOffIContainer.add(this.accrualLossWrittenOffField);
        this.accrualLossWrittenOffIContainer.newFeedback("accrualLossWrittenOffFeedback", this.accrualLossWrittenOffField);

        this.row13Block1 = this.row13.newUIBlock("row13Block1", Size.Six_6);

        this.row14 = this.accrualIContainer.newUIRow("row14");

        this.accrualOverPaymentLiabilityBlock = this.row14.newUIBlock("accrualOverPaymentLiabilityBlock", Size.Six_6);
        this.accrualOverPaymentLiabilityIContainer = this.accrualOverPaymentLiabilityBlock.newUIContainer("accrualOverPaymentLiabilityIContainer");
        this.accrualOverPaymentLiabilityField = new Select2SingleChoice<>("accrualOverPaymentLiabilityField", new PropertyModel<>(this.itemPage, "accrualOverPaymentLiabilityValue"), this.accrualOverPaymentLiabilityProvider);
        this.accrualOverPaymentLiabilityIContainer.add(this.accrualOverPaymentLiabilityField);
        this.accrualOverPaymentLiabilityIContainer.newFeedback("accrualOverPaymentLiabilityFeedback", this.accrualOverPaymentLiabilityField);

        this.row14Block1 = this.row14.newUIBlock("row14Block1", Size.Six_6);

        this.advancedAccountingRuleBlock = SectionBlock.newSectionBlock("advancedAccountingRuleBlock", this.form);
        this.advancedAccountingRuleIContainer = this.advancedAccountingRuleBlock.newSectionContainer("advancedAccountingRuleIContainer");

        this.row1 = UIRow.newUIRow("row1", this.advancedAccountingRuleIContainer);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Twelve_12);

        this.advancedAccountingRulePenaltyIncomeTable = new DataTable<>("advancedAccountingRulePenaltyIncomeTable", this.advancedAccountingRulePenaltyIncomeColumn, this.advancedAccountingRulePenaltyIncomeProvider, 20);
        this.row1Block1.add(this.advancedAccountingRulePenaltyIncomeTable);
        this.advancedAccountingRulePenaltyIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRulePenaltyIncomeTable, this.advancedAccountingRulePenaltyIncomeProvider));
        this.advancedAccountingRulePenaltyIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRulePenaltyIncomeTable));
        this.advancedAccountingRulePenaltyIncomeAddLink = new AjaxLink<>("advancedAccountingRulePenaltyIncomeAddLink");
        this.advancedAccountingRulePenaltyIncomeAddLink.setOnClick(this::advancedAccountingRulePenaltyIncomeAddLinkClick);
        this.row1Block1.add(this.advancedAccountingRulePenaltyIncomeAddLink);

        this.advancedAccountingRuleFeeIncomeTable = new DataTable<>("advancedAccountingRuleFeeIncomeTable", this.advancedAccountingRuleFeeIncomeColumn, this.advancedAccountingRuleFeeIncomeProvider, 20);
        this.row1Block1.add(this.advancedAccountingRuleFeeIncomeTable);
        this.advancedAccountingRuleFeeIncomeTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFeeIncomeTable, this.advancedAccountingRuleFeeIncomeProvider));
        this.advancedAccountingRuleFeeIncomeTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFeeIncomeTable));
        this.advancedAccountingRuleFeeIncomeAddLink = new AjaxLink<>("advancedAccountingRuleFeeIncomeAddLink");
        this.advancedAccountingRuleFeeIncomeAddLink.setOnClick(this::advancedAccountingRuleFeeIncomeAddLinkClick);
        this.row1Block1.add(this.advancedAccountingRuleFeeIncomeAddLink);

        this.advancedAccountingRuleFundSourceTable = new DataTable<>("advancedAccountingRuleFundSourceTable", this.advancedAccountingRuleFundSourceColumn, this.advancedAccountingRuleFundSourceProvider, 20);
        this.row1Block1.add(this.advancedAccountingRuleFundSourceTable);
        this.advancedAccountingRuleFundSourceTable.addTopToolbar(new HeadersToolbar<>(this.advancedAccountingRuleFundSourceTable, this.advancedAccountingRuleFundSourceProvider));
        this.advancedAccountingRuleFundSourceTable.addBottomToolbar(new NoRecordsToolbar(this.advancedAccountingRuleFundSourceTable));
        this.advancedAccountingRuleFundSourceAddLink = new AjaxLink<>("advancedAccountingRuleFundSourceAddLink");
        this.advancedAccountingRuleFundSourceAddLink.setOnClick(this::advancedAccountingRuleFundSourceAddLinkClick);
        this.row1Block1.add(this.advancedAccountingRuleFundSourceAddLink);
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
        this.errorAccounting.setObject(false);
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_PREVIEW);
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
