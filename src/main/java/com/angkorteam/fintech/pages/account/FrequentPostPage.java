package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GLEntryBuilder;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
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
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/11/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FrequentPostPage extends Page {

    protected String ruleId;

    protected Form<Void> debitForm;
    protected AjaxButton debitButton;

    protected WebMarkupBlock debitAccountNameBlock;
    protected WebMarkupContainer debitAccountNameIContainer;
    protected SingleChoiceProvider debitAccountNameProvider;
    protected Option debitAccountNameValue;
    protected Select2SingleChoice<Option> debitAccountNameField;
    protected TextFeedbackPanel debitAccountNameFeedback;

    protected WebMarkupBlock debitAmountBlock;
    protected WebMarkupContainer debitAmountIContainer;
    protected Double debitAmountValue;
    protected TextField<Double> debitAmountField;
    protected TextFeedbackPanel debitAmountFeedback;

    protected Form<Void> creditForm;
    protected AjaxButton creditButton;

    protected WebMarkupBlock creditAccountNameBlock;
    protected WebMarkupContainer creditAccountNameIContainer;
    protected SingleChoiceProvider creditAccountNameProvider;
    protected Option creditAccountNameValue;
    protected Select2SingleChoice<Option> creditAccountNameField;
    protected TextFeedbackPanel creditAccountNameFeedback;

    protected WebMarkupBlock creditAmountBlock;
    protected WebMarkupContainer creditAmountIContainer;
    protected Double creditAmountValue;
    protected TextField<Double> creditAmountField;
    protected TextFeedbackPanel creditAmountFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyIContainer;
    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected WebMarkupBlock referenceNumberBlock;
    protected WebMarkupContainer referenceNumberIContainer;
    protected String referenceNumberValue;
    protected TextField<String> referenceNumberField;
    protected TextFeedbackPanel referenceNumberFeedback;

    protected WebMarkupBlock transactionDateBlock;
    protected WebMarkupContainer transactionDateIContainer;
    protected Date transactionDateValue;
    protected DateTextField transactionDateField;
    protected TextFeedbackPanel transactionDateFeedback;

    protected WebMarkupBlock paymentTypeBlock;
    protected WebMarkupContainer paymentTypeIContainer;
    protected SingleChoiceProvider paymentTypeProvider;
    protected Option paymentTypeValue;
    protected Select2SingleChoice<Option> paymentTypeField;
    protected TextFeedbackPanel paymentTypeFeedback;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountIContainer;
    protected String accountValue;
    protected TextField<String> accountField;
    protected TextFeedbackPanel accountFeedback;

    protected WebMarkupBlock chequeBlock;
    protected WebMarkupContainer chequeIContainer;
    protected String chequeValue;
    protected TextField<String> chequeField;
    protected TextFeedbackPanel chequeFeedback;

    protected WebMarkupBlock routingCodeBlock;
    protected WebMarkupContainer routingCodeIContainer;
    protected String routingCodeValue;
    protected TextField<String> routingCodeField;
    protected TextFeedbackPanel routingCodeFeedback;

    protected WebMarkupBlock receiptBlock;
    protected WebMarkupContainer receiptIContainer;
    protected String receiptValue;
    protected TextField<String> receiptField;
    protected TextFeedbackPanel receiptFeedback;

    protected WebMarkupBlock bankBlock;
    protected WebMarkupContainer bankIContainer;
    protected String bankValue;
    protected TextField<String> bankField;
    protected TextFeedbackPanel bankFeedback;

    protected WebMarkupBlock commentBlock;
    protected WebMarkupContainer commentIContainer;
    protected String commentValue;
    protected TextArea<String> commentField;
    protected TextFeedbackPanel commentFeedback;

    protected WebMarkupBlock creditBlock;
    protected WebMarkupContainer creditIContainer;
    protected List<Map<String, Object>> creditValue;
    protected DataTable<Map<String, Object>, String> creditTable;
    protected ListDataProvider creditProvider;
    protected List<IColumn<Map<String, Object>, String>> creditColumn;

    protected WebMarkupBlock debitBlock;
    protected WebMarkupContainer debitIContainer;
    protected List<Map<String, Object>> debitValue;
    protected DataTable<Map<String, Object>, String> debitTable;
    protected ListDataProvider debitProvider;
    protected List<IColumn<Map<String, Object>, String>> debitColumn;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
    }

    @Override
    protected void initData() {
        this.creditValue = Lists.newArrayList();
        this.debitValue = Lists.newArrayList();

        this.transactionDateValue = new Date();

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.referenceNumberValue = generator.externalId();
        this.transactionDateValue = new Date();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        PageParameters parameters = getPageParameters();
        this.ruleId = parameters.get("ruleId").toString("");

        Map<String, Object> ruleObject = jdbcTemplate.queryForMap("select * from acc_accounting_rule where id = ?", this.ruleId);

        if (ruleObject.get("debit_account_id") != null) {
            this.debitAccountNameValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("debit_account_id"));
        }

        if (ruleObject.get("credit_account_id") != null) {
            this.creditAccountNameValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("credit_account_id"));
        }

        this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?", Option.MAPPER, ruleObject.get("office_id"));

    }

    @Override
    protected void configureMetaData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        if (this.debitAccountNameValue != null) {
            this.debitAccountNameProvider.applyWhere("id", "id = " + debitAccountNameValue.getId());
        } else {
            List<String> tags = jdbcTemplate.queryForList("SELECT tag_id FROM acc_rule_tags WHERE acc_type_enum = " + RuleBrowsePage.DEBIT + " and acc_rule_id = ?", String.class, this.ruleId);
            if (tags != null && !tags.isEmpty()) {
                this.debitAccountNameProvider.applyWhere("tag", "tag_id in (" + StringUtils.join(tags, ",") + ")");
            }
        }

        if (this.creditAccountNameValue != null) {
            this.creditAccountNameProvider.applyWhere("id", "id = " + this.creditAccountNameValue.getId());
        } else {
            List<String> tags = jdbcTemplate.queryForList("SELECT tag_id FROM acc_rule_tags WHERE acc_type_enum = " + RuleBrowsePage.CREDIT + " and acc_rule_id = ?", String.class, this.ruleId);
            if (tags != null && !tags.isEmpty()) {
                this.creditAccountNameProvider.applyWhere("tag", "tag_id in (" + StringUtils.join(tags, ",") + ")");
            }
        }

        if (this.officeValue != null) {
            this.officeProvider.applyWhere("id", "id = " + this.officeValue.getId());
        }
    }

    @Override
    protected void initComponent() {

        this.debitForm = new Form<>("debitForm");
        add(this.debitForm);

        this.debitButton = new AjaxButton("debitButton");
        this.debitButton.setOnSubmit(this::debitButtonSubmit);
        this.debitForm.add(this.debitButton);

        initDebitAccountNameBlock();

        initDebitAmountBlock();

        this.creditForm = new Form<>("creditForm");
        add(this.creditForm);

        this.creditButton = new AjaxButton("creditButton");
        this.creditButton.setOnSubmit(this::creditButtonSubmit);
        this.creditForm.add(this.creditButton);

        initCreditAccountNameBlock();

        initCreditAmountBlock();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountingPage.class);
        this.form.add(this.closeLink);

        initOfficeBlock();

        initCurrencyBlock();

        initDebitBlock();

        initCreditBlock();

        initReferenceNumberBlock();

        initTransactionDateBlock();

        initPaymentTypeBlock();

        initAccountBlock();

        initChequeBlock();

        initRoutingCodeBlock();

        initReceiptBlock();

        initBankBlock();

        initCommentBlock();
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initCurrencyBlock() {
        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        this.form.add(this.currencyBlock);
        this.currencyIContainer = new WebMarkupContainer("currencyIContainer");
        this.currencyBlock.add(this.currencyIContainer);
        this.currencyProvider = new CurrencyProvider();
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setRequired(true);
        this.currencyIContainer.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.currencyIContainer.add(this.currencyFeedback);
    }

    protected void initDebitBlock() {
        this.debitBlock = new WebMarkupBlock("debitBlock", Size.Twelve_12);
        this.form.add(this.debitBlock);
        this.debitIContainer = new WebMarkupContainer("debitIContainer");
        this.debitBlock.add(this.debitIContainer);
        this.debitColumn = Lists.newArrayList();
        this.debitColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::debitColumn));
        this.debitColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::debitColumn));
        this.debitColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::debitAction, this::debitClick));
        this.debitValue = Lists.newArrayList();
        this.debitProvider = new ListDataProvider(this.debitValue);
        this.debitTable = new DataTable<>("debitTable", this.debitColumn, this.debitProvider, 20);
        this.debitTable.addTopToolbar(new HeadersToolbar<>(this.debitTable, this.debitProvider));
        this.debitTable.addBottomToolbar(new NoRecordsToolbar(this.debitTable));
        this.debitIContainer.add(this.debitTable);
    }

    protected void initCreditBlock() {
        this.creditBlock = new WebMarkupBlock("creditBlock", Size.Twelve_12);
        this.form.add(this.creditBlock);
        this.creditIContainer = new WebMarkupContainer("creditIContainer");
        this.creditBlock.add(this.creditIContainer);
        this.creditColumn = Lists.newArrayList();
        this.creditColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::creditColumn));
        this.creditColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::creditColumn));
        this.creditColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::creditAction, this::creditClick));
        this.creditValue = Lists.newArrayList();
        this.creditProvider = new ListDataProvider(this.creditValue);
        this.creditTable = new DataTable<>("creditTable", this.creditColumn, this.creditProvider, 20);
        this.creditTable.addTopToolbar(new HeadersToolbar<>(this.creditTable, this.creditProvider));
        this.creditTable.addBottomToolbar(new NoRecordsToolbar(this.creditTable));
        this.creditIContainer.add(this.creditTable);
    }

    protected void initReferenceNumberBlock() {
        this.referenceNumberBlock = new WebMarkupBlock("referenceNumberBlock", Size.Six_6);
        this.form.add(this.referenceNumberBlock);
        this.referenceNumberIContainer = new WebMarkupContainer("referenceNumberIContainer");
        this.referenceNumberBlock.add(this.referenceNumberIContainer);
        this.referenceNumberField = new TextField<>("referenceNumberField", new PropertyModel<>(this, "referenceNumberValue"));
        this.referenceNumberField.setRequired(true);
        this.referenceNumberIContainer.add(this.referenceNumberField);
        this.referenceNumberFeedback = new TextFeedbackPanel("referenceNumberFeedback", this.referenceNumberField);
        this.referenceNumberIContainer.add(this.referenceNumberFeedback);
    }

    protected void initTransactionDateBlock() {
        this.transactionDateBlock = new WebMarkupBlock("transactionDateBlock", Size.Six_6);
        this.form.add(this.transactionDateBlock);
        this.transactionDateIContainer = new WebMarkupContainer("transactionDateIContainer");
        this.transactionDateBlock.add(this.transactionDateIContainer);
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateField.setRequired(true);
        this.transactionDateIContainer.add(this.transactionDateField);
        this.transactionDateFeedback = new TextFeedbackPanel("transactionDateFeedback", this.transactionDateField);
        this.transactionDateIContainer.add(this.transactionDateFeedback);
    }

    protected void initPaymentTypeBlock() {
        this.paymentTypeBlock = new WebMarkupBlock("paymentTypeBlock", Size.Six_6);
        this.form.add(this.paymentTypeBlock);
        this.paymentTypeIContainer = new WebMarkupContainer("paymentTypeIContainer");
        this.paymentTypeBlock.add(this.paymentTypeIContainer);
        this.paymentTypeProvider = new SingleChoiceProvider("m_payment_type", "id", "value");
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeIContainer.add(this.paymentTypeField);
        this.paymentTypeFeedback = new TextFeedbackPanel("paymentTypeFeedback", this.paymentTypeField);
        this.paymentTypeIContainer.add(this.paymentTypeFeedback);
    }

    protected void initAccountBlock() {
        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Six_6);
        this.form.add(this.accountBlock);
        this.accountIContainer = new WebMarkupContainer("accountIContainer");
        this.accountBlock.add(this.accountIContainer);
        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
        this.accountIContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountIContainer.add(this.accountFeedback);
    }

    protected void initChequeBlock() {
        this.chequeBlock = new WebMarkupBlock("chequeBlock", Size.Six_6);
        this.form.add(this.chequeBlock);
        this.chequeIContainer = new WebMarkupContainer("chequeIContainer");
        this.chequeBlock.add(this.chequeIContainer);
        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
        this.chequeIContainer.add(this.chequeField);
        this.chequeFeedback = new TextFeedbackPanel("chequeFeedback", this.chequeField);
        this.chequeIContainer.add(this.chequeFeedback);
    }

    protected void initRoutingCodeBlock() {
        this.routingCodeBlock = new WebMarkupBlock("routingCodeBlock", Size.Six_6);
        this.form.add(this.routingCodeBlock);
        this.routingCodeIContainer = new WebMarkupContainer("routingCodeIContainer");
        this.routingCodeBlock.add(this.routingCodeIContainer);
        this.routingCodeField = new TextField<>("routingCodeField", new PropertyModel<>(this, "routingCodeValue"));
        this.routingCodeIContainer.add(this.routingCodeField);
        this.routingCodeFeedback = new TextFeedbackPanel("routingCodeFeedback", this.routingCodeField);
        this.routingCodeIContainer.add(this.routingCodeFeedback);
    }

    protected void initReceiptBlock() {
        this.receiptBlock = new WebMarkupBlock("receiptBlock", Size.Six_6);
        this.form.add(this.receiptBlock);
        this.receiptIContainer = new WebMarkupContainer("receiptIContainer");
        this.receiptBlock.add(this.receiptIContainer);
        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
        this.receiptIContainer.add(this.receiptField);
        this.receiptFeedback = new TextFeedbackPanel("receiptFeedback", this.receiptField);
        this.receiptIContainer.add(this.receiptFeedback);
    }

    protected void initBankBlock() {
        this.bankBlock = new WebMarkupBlock("bankBlock", Size.Six_6);
        this.form.add(this.bankBlock);
        this.bankIContainer = new WebMarkupContainer("bankIContainer");
        this.bankBlock.add(this.bankIContainer);
        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
        this.bankIContainer.add(this.bankField);
        this.bankFeedback = new TextFeedbackPanel("bankFeedback", this.bankField);
        this.bankIContainer.add(this.bankFeedback);
    }

    protected void initCommentBlock() {
        this.commentBlock = new WebMarkupBlock("commentBlock", Size.Twelve_12);
        this.form.add(this.commentBlock);
        this.commentIContainer = new WebMarkupContainer("commentIContainer");
        this.commentBlock.add(this.commentIContainer);
        this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
        this.commentIContainer.add(this.commentField);
        this.commentFeedback = new TextFeedbackPanel("commentFeedback", this.commentField);
        this.commentIContainer.add(this.commentFeedback);
    }

    protected void initCreditAccountNameBlock() {
        this.creditAccountNameBlock = new WebMarkupBlock("creditAccountNameBlock", Size.Six_6);
        this.creditForm.add(this.creditAccountNameBlock);
        this.creditAccountNameIContainer = new WebMarkupContainer("creditAccountNameIContainer");
        this.creditAccountNameBlock.add(this.creditAccountNameIContainer);
        this.creditAccountNameProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.creditAccountNameProvider.applyWhere("usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.creditAccountNameField = new Select2SingleChoice<>("creditAccountNameField", new PropertyModel<>(this, "creditAccountNameValue"), this.creditAccountNameProvider);
        this.creditAccountNameField.setRequired(true);
        this.creditAccountNameIContainer.add(this.creditAccountNameField);
        this.creditAccountNameFeedback = new TextFeedbackPanel("creditAccountNameFeedback", this.creditAccountNameField);
        this.creditAccountNameIContainer.add(this.creditAccountNameFeedback);
    }

    protected void initDebitAccountNameBlock() {
        this.debitAccountNameBlock = new WebMarkupBlock("debitAccountNameBlock", Size.Six_6);
        this.debitForm.add(this.debitAccountNameBlock);
        this.debitAccountNameIContainer = new WebMarkupContainer("debitAccountNameIContainer");
        this.debitAccountNameBlock.add(this.debitAccountNameIContainer);
        this.debitAccountNameProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.debitAccountNameProvider.applyWhere("usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.debitAccountNameField = new Select2SingleChoice<>("debitAccountNameField", new PropertyModel<>(this, "debitAccountNameValue"), this.debitAccountNameProvider);
        this.debitAccountNameField.setRequired(true);
        this.debitAccountNameIContainer.add(this.debitAccountNameField);
        this.debitAccountNameFeedback = new TextFeedbackPanel("debitAccountNameFeedback", this.debitAccountNameField);
        this.debitAccountNameIContainer.add(this.debitAccountNameFeedback);
    }

    protected void initCreditAmountBlock() {
        this.creditAmountBlock = new WebMarkupBlock("creditAmountBlock", Size.Six_6);
        this.creditForm.add(this.creditAmountBlock);
        this.creditAmountIContainer = new WebMarkupContainer("creditAmountIContainer");
        this.creditAmountBlock.add(this.creditAmountIContainer);
        this.creditAmountField = new TextField<>("creditAmountField", new PropertyModel<>(this, "creditAmountValue"));
        this.creditAmountField.setRequired(true);
        this.creditAmountIContainer.add(this.creditAmountField);
        this.creditAmountFeedback = new TextFeedbackPanel("creditAmountFeedback", this.creditAmountField);
        this.creditAmountIContainer.add(this.creditAmountFeedback);
    }

    protected void initDebitAmountBlock() {
        this.debitAmountBlock = new WebMarkupBlock("debitAmountBlock", Size.Six_6);
        this.debitForm.add(this.debitAmountBlock);
        this.debitAmountIContainer = new WebMarkupContainer("debitAmountIContainer");
        this.debitAmountBlock.add(this.debitAmountIContainer);
        this.debitAmountField = new TextField<>("debitAmountField", new PropertyModel<>(this, "debitAmountValue"));
        this.debitAmountField.setRequired(true);
        this.debitAmountIContainer.add(this.debitAmountField);
        this.debitAmountFeedback = new TextFeedbackPanel("debitAmountFeedback", this.debitAmountField);
        this.debitAmountIContainer.add(this.debitAmountFeedback);
    }

    @Override
    protected void configureRequiredValidation() {

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

    protected void saveButtonSubmit(Button button) {
        GLEntryBuilder builder = new GLEntryBuilder();

        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        }
        if (this.paymentTypeValue != null) {
            builder.withPaymentTypeId(this.paymentTypeValue.getId());
        }
        if (!Strings.isNullOrEmpty(this.referenceNumberValue)) {
            builder.withReferenceNumber(this.referenceNumberValue);
        }
        if (this.transactionDateValue != null) {
            builder.withTransactionDate(this.transactionDateValue);
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
        for (Map<String, Object> item : this.creditValue) {
            builder.withCredit((String) item.get("id"), (Double) item.get("amount"));
        }
        for (Map<String, Object> item : this.debitValue) {
            builder.withDebit((String) item.get("id"), (Double) item.get("amount"));
        }

        JsonNode node = null;
        try {
            node = GLAccountHelper.postEntry((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(AccountingPage.class);
    }

    protected boolean debitButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> debit = Maps.newHashMap();
        debit.put("uuid", generator.externalId());
        debit.put("id", this.debitAccountNameValue.getId());
        debit.put("name", this.debitAccountNameValue.getText());
        debit.put("amount", this.debitAmountValue);
        this.debitValue.add(debit);
        this.debitAmountValue = 0.d;
        target.add(this.form);
        target.add(this.debitForm);
        return false;
    }

    protected boolean creditButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> credit = Maps.newHashMap();
        credit.put("uuid", generator.externalId());
        credit.put("id", this.creditAccountNameValue.getId());
        credit.put("name", this.creditAccountNameValue.getText());
        credit.put("amount", this.creditAmountValue);
        this.creditValue.add(credit);
        this.creditAmountValue = 0.d;
        target.add(this.form);
        target.add(this.creditForm);
        return false;
    }

}
