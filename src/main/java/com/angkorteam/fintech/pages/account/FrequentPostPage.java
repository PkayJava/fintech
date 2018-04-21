package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccAccountingRule;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GLEntryBuilder;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.popup.CreditRulePopup;
import com.angkorteam.fintech.popup.DebitRulePopup;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/11/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FrequentPostPage extends Page {

    protected String ruleId;

    protected Map<String, Object> ruleObject;

    protected boolean allowMultipleCredit;
    protected boolean allowMultipleDebit;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected UIRow row1;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;

    protected UIBlock currencyBlock;
    protected UIContainer currencyIContainer;
    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;

    protected UIRow row2;

    protected UIBlock debitBlock;
    protected UIContainer debitIContainer;
    protected List<Map<String, Object>> debitValue;
    protected DataTable<Map<String, Object>, String> debitTable;
    protected ListDataProvider debitProvider;
    protected List<IColumn<Map<String, Object>, String>> debitColumn;
    protected AjaxLink<Void> debitAddLink;

    protected UIRow row3;

    protected UIBlock creditBlock;
    protected UIContainer creditIContainer;
    protected List<Map<String, Object>> creditValue;
    protected DataTable<Map<String, Object>, String> creditTable;
    protected ListDataProvider creditProvider;
    protected List<IColumn<Map<String, Object>, String>> creditColumn;
    protected AjaxLink<Void> creditAddLink;

    protected UIRow row4;

    protected UIBlock referenceNumberBlock;
    protected UIContainer referenceNumberIContainer;
    protected String referenceNumberValue;
    protected TextField<String> referenceNumberField;

    protected UIBlock transactionDateBlock;
    protected UIContainer transactionDateIContainer;
    protected Date transactionDateValue;
    protected DateTextField transactionDateField;

    protected UIRow row9;

    protected UIBlock showPaymentDetailsBlock;
    protected UIContainer showPaymentDetailsIContainer;
    protected CheckBox showPaymentDetailsField;
    protected Boolean showPaymentDetailsValue;

    protected UIBlock row9Block1;

    protected UIRow row5;

    protected UIBlock paymentTypeBlock;
    protected UIContainer paymentTypeIContainer;
    protected SingleChoiceProvider paymentTypeProvider;
    protected Option paymentTypeValue;
    protected Select2SingleChoice<Option> paymentTypeField;

    protected UIBlock accountBlock;
    protected UIContainer accountIContainer;
    protected String accountValue;
    protected TextField<String> accountField;

    protected UIRow row6;

    protected UIBlock chequeBlock;
    protected UIContainer chequeIContainer;
    protected String chequeValue;
    protected TextField<String> chequeField;

    protected UIBlock routingCodeBlock;
    protected UIContainer routingCodeIContainer;
    protected String routingCodeValue;
    protected TextField<String> routingCodeField;

    protected UIRow row7;

    protected UIBlock receiptBlock;
    protected UIContainer receiptIContainer;
    protected String receiptValue;
    protected TextField<String> receiptField;

    protected UIBlock bankBlock;
    protected UIContainer bankIContainer;
    protected String bankValue;
    protected TextField<String> bankField;

    protected UIRow row8;

    protected UIBlock commentBlock;
    protected UIContainer commentIContainer;
    protected String commentValue;
    protected TextArea<String> commentField;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule Selection");
            breadcrumb.setPage(RuleSelectPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Frequent Posting");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {

        this.popupModel = new HashMap<>();

        this.transactionDateValue = new Date();

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.referenceNumberValue = generator.externalId();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        PageParameters parameters = getPageParameters();
        this.ruleId = parameters.get("ruleId").toString("");

        selectQuery = new SelectQuery(AccAccountingRule.NAME);
        selectQuery.addWhere(AccAccountingRule.Field.ID + " = :" + AccAccountingRule.Field.ID, this.ruleId);
        selectQuery.addField(AccAccountingRule.Field.DEBIT_ACCOUNT_ID);
        selectQuery.addField(AccAccountingRule.Field.CREDIT_ACCOUNT_ID);
        selectQuery.addField(AccAccountingRule.Field.ALLOW_MULTIPLE_CREDITS);
        selectQuery.addField(AccAccountingRule.Field.ALLOW_MULTIPLE_DEBITS);
        selectQuery.addField(AccAccountingRule.Field.OFFICE_ID);
        this.ruleObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.allowMultipleCredit = (boolean) this.ruleObject.get(AccAccountingRule.Field.ALLOW_MULTIPLE_CREDITS);
        this.allowMultipleDebit = (boolean) this.ruleObject.get(AccAccountingRule.Field.ALLOW_MULTIPLE_DEBITS);

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addField(MOffice.Field.ID);
        selectQuery.addField(MOffice.Field.NAME + " as text");
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, this.ruleObject.get(AccAccountingRule.Field.OFFICE_ID));

        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.currencyProvider = new CurrencyProvider();

        this.debitColumn = Lists.newArrayList();
        this.debitColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::debitColumn));
        this.debitColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::debitColumn));
        this.debitColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::debitAction, this::debitClick));

        this.debitValue = Lists.newArrayList();
        this.debitProvider = new ListDataProvider(this.debitValue);

        this.creditColumn = Lists.newArrayList();
        this.creditColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::creditColumn));
        this.creditColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::creditColumn));
        this.creditColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::creditAction, this::creditClick));

        this.creditValue = Lists.newArrayList();
        this.creditProvider = new ListDataProvider(this.creditValue);

        this.paymentTypeProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);

    }

    @Override
    protected void configureMetaData() {
        this.officeField.setRequired(true);
        this.currencyField.setRequired(true);
        this.referenceNumberField.setRequired(true);
        this.transactionDateField.setRequired(true);
        this.showPaymentDetailsField.add(new OnChangeAjaxBehavior(this::showPaymentDetailsFieldUpdate));
        showPaymentDetailsFieldUpdate(null);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountingPage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Six_6);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.currencyBlock = this.row1.newUIBlock("currencyBlock", Size.Six_6);
        this.currencyIContainer = this.currencyBlock.newUIContainer("currencyIContainer");
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyIContainer.add(this.currencyField);
        this.currencyIContainer.newFeedback("currencyFeedback", this.currencyField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.debitBlock = this.row2.newUIBlock("debitBlock", Size.Twelve_12);
        this.debitIContainer = this.debitBlock.newUIContainer("debitIContainer");
        this.debitTable = new DataTable<>("debitTable", this.debitColumn, this.debitProvider, 20);
        this.debitTable.addTopToolbar(new HeadersToolbar<>(this.debitTable, this.debitProvider));
        this.debitTable.addBottomToolbar(new NoRecordsToolbar(this.debitTable));
        this.debitIContainer.add(this.debitTable);
        this.debitAddLink = new AjaxLink<>("debitAddLink");
        this.debitAddLink.setOnClick(this::debitAddLinkClick);
        this.debitIContainer.add(this.debitAddLink);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.creditBlock = this.row3.newUIBlock("creditBlock", Size.Twelve_12);
        this.creditIContainer = this.creditBlock.newUIContainer("creditIContainer");
        this.creditTable = new DataTable<>("creditTable", this.creditColumn, this.creditProvider, 20);
        this.creditTable.addTopToolbar(new HeadersToolbar<>(this.creditTable, this.creditProvider));
        this.creditTable.addBottomToolbar(new NoRecordsToolbar(this.creditTable));
        this.creditIContainer.add(this.creditTable);
        this.creditAddLink = new AjaxLink<>("creditAddLink");
        this.creditAddLink.setOnClick(this::creditAddLinkClick);
        this.creditIContainer.add(this.creditAddLink);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.referenceNumberBlock = this.row4.newUIBlock("referenceNumberBlock", Size.Six_6);
        this.referenceNumberIContainer = this.referenceNumberBlock.newUIContainer("referenceNumberIContainer");
        this.referenceNumberField = new TextField<>("referenceNumberField", new PropertyModel<>(this, "referenceNumberValue"));
        this.referenceNumberIContainer.add(this.referenceNumberField);
        this.referenceNumberIContainer.newFeedback("referenceNumberFeedback", this.referenceNumberField);

        this.transactionDateBlock = this.row4.newUIBlock("transactionDateBlock", Size.Six_6);
        this.transactionDateIContainer = this.transactionDateBlock.newUIContainer("transactionDateIContainer");
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateIContainer.add(this.transactionDateField);
        this.transactionDateIContainer.newFeedback("transactionDateFeedback", this.transactionDateField);

        this.row9 = UIRow.newUIRow("row9", this.form);

        this.showPaymentDetailsBlock = this.row9.newUIBlock("showPaymentDetailsBlock", Size.Six_6);
        this.showPaymentDetailsIContainer = this.showPaymentDetailsBlock.newUIContainer("showPaymentDetailsIContainer");
        this.showPaymentDetailsField = new CheckBox("showPaymentDetailsField", new PropertyModel<>(this, "showPaymentDetailsValue"));
        this.showPaymentDetailsIContainer.add(this.showPaymentDetailsField);
        this.showPaymentDetailsIContainer.newFeedback("showPaymentDetailsFeedback", this.showPaymentDetailsField);

        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Six_6);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.paymentTypeBlock = this.row5.newUIBlock("paymentTypeBlock", Size.Six_6);
        this.paymentTypeIContainer = this.paymentTypeBlock.newUIContainer("paymentTypeIContainer");
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeIContainer.add(this.paymentTypeField);
        this.paymentTypeIContainer.newFeedback("paymentTypeFeedback", this.paymentTypeField);

        this.accountBlock = this.row5.newUIBlock("accountBlock", Size.Six_6);
        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
        this.accountIContainer.add(this.accountField);
        this.accountIContainer.newFeedback("accountFeedback", this.accountField);

        this.row6 = UIRow.newUIRow("row6", this.form);

        this.chequeBlock = this.row6.newUIBlock("chequeBlock", Size.Six_6);
        this.chequeIContainer = this.chequeBlock.newUIContainer("chequeIContainer");
        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
        this.chequeIContainer.add(this.chequeField);
        this.chequeIContainer.newFeedback("chequeFeedback", this.chequeField);

        this.routingCodeBlock = this.row6.newUIBlock("routingCodeBlock", Size.Six_6);
        this.routingCodeIContainer = this.routingCodeBlock.newUIContainer("routingCodeIContainer");
        this.routingCodeField = new TextField<>("routingCodeField", new PropertyModel<>(this, "routingCodeValue"));
        this.routingCodeIContainer.add(this.routingCodeField);
        this.routingCodeIContainer.newFeedback("routingCodeFeedback", this.routingCodeField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.receiptBlock = this.row7.newUIBlock("receiptBlock", Size.Six_6);
        this.receiptIContainer = this.receiptBlock.newUIContainer("receiptIContainer");
        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
        this.receiptIContainer.add(this.receiptField);
        this.receiptIContainer.newFeedback("receiptFeedback", this.receiptField);

        this.bankBlock = this.row7.newUIBlock("bankBlock", Size.Six_6);
        this.bankIContainer = this.bankBlock.newUIContainer("bankIContainer");
        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
        this.bankIContainer.add(this.bankField);
        this.bankIContainer.newFeedback("bankFeedback", this.bankField);

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.commentBlock = this.row8.newUIBlock("commentBlock", Size.Twelve_12);
        this.commentIContainer = this.commentBlock.newUIContainer("commentIContainer");
        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentIContainer.add(this.commentField);
        this.commentIContainer.newFeedback("commentFeedback", this.commentField);
    }

    protected boolean showPaymentDetailsFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.showPaymentDetailsValue == null ? false : this.showPaymentDetailsValue;
        this.paymentTypeBlock.setVisible(visible);
        this.paymentTypeField.setRequired(visible);
        this.accountBlock.setVisible(visible);
        this.accountField.setRequired(visible);
        this.chequeBlock.setVisible(visible);
        this.chequeField.setRequired(visible);
        this.routingCodeBlock.setVisible(visible);
        this.routingCodeField.setRequired(visible);
        this.receiptBlock.setVisible(visible);
        this.receiptField.setRequired(visible);
        this.bankBlock.setVisible(visible);
        this.bankField.setRequired(visible);
        this.commentBlock.setVisible(visible);
        this.commentField.setRequired(visible);
        if (target != null) {
            target.add(this.row5);
            target.add(this.row6);
            target.add(this.row7);
            target.add(this.row8);
        }
        return false;
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if ("credit".equals(popupName)) {
            Option account = (Option) this.popupModel.get("creditAccountNameValue");
            Double amount = (Double) this.popupModel.get("creditAmountValue");
            if (!this.allowMultipleCredit) {
                this.creditValue.clear();
            }
            Map<String, Object> item = null;
            for (Map<String, Object> temp : this.creditValue) {
                if (temp.get("uuid").equals(account.getId())) {
                    item = temp;
                    break;
                }
            }
            if (item == null) {
                item = new HashMap<>();
                item.put("uuid", account.getId());
                item.put("id", account.getId());
                item.put("name", account.getText());
                item.put("amount", amount);
                this.creditValue.add(item);
            } else {
                item.put("amount", amount);
            }
            target.add(this.creditBlock);
        } else if ("debit".equals(popupName)) {
            Option account = (Option) this.popupModel.get("debitAccountNameValue");
            Double amount = (Double) this.popupModel.get("debitAmountValue");
            if (!this.allowMultipleDebit) {
                this.debitValue.clear();
            }
            Map<String, Object> item = null;
            for (Map<String, Object> temp : this.debitValue) {
                if (temp.get("uuid").equals(account.getId())) {
                    item = temp;
                    break;
                }
            }
            if (item == null) {
                item = new HashMap<>();
                item.put("uuid", account.getId());
                item.put("id", account.getId());
                item.put("name", account.getText());
                item.put("amount", amount);
                this.debitValue.add(item);
            } else {
                item.put("amount", amount);
            }
            target.add(this.debitBlock);
        }
    }

    protected ItemPanel debitColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void debitClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.debitValue.size(); i++) {
            Map<String, Object> column = this.debitValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.debitValue.remove(index);
        }
        target.add(this.debitTable);
    }

    protected List<ActionItem> debitAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected ItemPanel creditColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void creditClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.creditValue.size(); i++) {
            Map<String, Object> column = this.creditValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.creditValue.remove(index);
        }
        target.add(this.creditTable);
    }

    protected List<ActionItem> creditAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean creditAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new CreditRulePopup("credit", this.popupModel, this.ruleId));
        this.modalWindow.show(target);
        return false;
    }

    protected boolean debitAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new DebitRulePopup("debit", this.popupModel, this.ruleId));
        this.modalWindow.show(target);
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        GLEntryBuilder builder = new GLEntryBuilder();

        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        }
        if (!Strings.isNullOrEmpty(this.referenceNumberValue)) {
            builder.withReferenceNumber(this.referenceNumberValue);
        }
        if (this.transactionDateValue != null) {
            builder.withTransactionDate(this.transactionDateValue);
        }
        if (this.showPaymentDetailsValue != null && this.showPaymentDetailsValue) {
            if (this.paymentTypeValue != null) {
                builder.withPaymentTypeId(this.paymentTypeValue.getId());
            }
            if (!Strings.isNullOrEmpty(this.accountValue)) {
                builder.withAccountNumber(this.accountValue);
            }
            if (!Strings.isNullOrEmpty(this.chequeValue)) {
                builder.withCheckNumber(this.chequeValue);
            }
            if (!Strings.isNullOrEmpty(this.routingCodeValue)) {
                builder.withRoutingCode(this.routingCodeValue);
            }
            if (!Strings.isNullOrEmpty(this.receiptValue)) {
                builder.withReceiptNumber(this.receiptValue);
            }
            if (!Strings.isNullOrEmpty(this.bankValue)) {
                builder.withBankNumber(this.bankValue);
            }
            if (!Strings.isNullOrEmpty(this.commentValue)) {
                builder.withComment(this.commentValue);
            }
        }
        for (Map<String, Object> item : this.creditValue) {
            builder.withCredit((String) item.get("id"), (Double) item.get("amount"));
        }
        for (Map<String, Object> item : this.debitValue) {
            builder.withDebit((String) item.get("id"), (Double) item.get("amount"));
        }

        JsonNode node = GLAccountHelper.postEntry((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(AccountingPage.class);
    }

}
