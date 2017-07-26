package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.request.GLEntryBuilder;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 7/11/17.
 */
public class FrequentPostPage extends Page {

    private String ruleId;

    private Form<Void> debitForm;
    private AjaxButton debitButton;

    private OptionSingleChoiceProvider debitAccountNameProvider;
    private Option debitAccountNameValue;
    private Select2SingleChoice<Option> debitAccountNameField;
    private TextFeedbackPanel debitAccountNameFeedback;

    private Double debitAmountValue;
    private TextField<Double> debitAmountField;
    private TextFeedbackPanel debitAmountFeedback;

    private Form<Void> creditForm;
    private AjaxButton creditButton;

    private OptionSingleChoiceProvider creditAccountNameProvider;
    private Option creditAccountNameValue;
    private Select2SingleChoice<Option> creditAccountNameField;
    private TextFeedbackPanel creditAccountNameFeedback;

    private Double creditAmountValue;
    private TextField<Double> creditAmountField;
    private TextFeedbackPanel creditAmountFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private OptionSingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private String referenceNumberValue;
    private TextField<String> referenceNumberField;
    private TextFeedbackPanel referenceNumberFeedback;

    private Date transactionDateValue;
    private DateTextField transactionDateField;
    private TextFeedbackPanel transactionDateFeedback;

    private OptionSingleChoiceProvider paymentTypeProvider;
    private Option paymentTypeValue;
    private Select2SingleChoice<Option> paymentTypeField;
    private TextFeedbackPanel paymentTypeFeedback;

    private String accountValue;
    private TextField<String> accountField;
    private TextFeedbackPanel accountFeedback;

    private String chequeValue;
    private TextField<String> chequeField;
    private TextFeedbackPanel chequeFeedback;

    private String routingCodeValue;
    private TextField<String> routingCodeField;
    private TextFeedbackPanel routingCodeFeedback;

    private String receiptValue;
    private TextField<String> receiptField;
    private TextFeedbackPanel receiptFeedback;

    private String bankValue;
    private TextField<String> bankField;
    private TextFeedbackPanel bankFeedback;

    private String commentValue;
    private TextArea<String> commentField;
    private TextFeedbackPanel commentFeedback;

    private List<Map<String, Object>> creditValue;
    private DataTable<Map<String, Object>, String> creditTable;
    private ListDataProvider creditProvider;

    private List<Map<String, Object>> debitValue;
    private DataTable<Map<String, Object>, String> debitTable;
    private ListDataProvider debitProvider;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.ruleId = parameters.get("ruleId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

	Map<String, Object> ruleObject = jdbcTemplate.queryForMap("select * from acc_accounting_rule where id = ?",
		this.ruleId);

	this.creditValue = Lists.newArrayList();
	this.debitValue = Lists.newArrayList();

	this.debitForm = new Form<>("debitForm");
	add(this.debitForm);

	this.debitButton = new AjaxButton("debitButton");
	this.debitButton.setOnSubmit(this::debitButtonSubmit);
	this.debitButton.setOnError(this::debitButtonError);
	this.debitForm.add(this.debitButton);

	this.debitAccountNameProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
	this.debitAccountNameProvider.applyWhere("usage", "account_usage = " + AccountUsage.Detail.getLiteral());
	this.debitAccountNameField = new Select2SingleChoice<>("debitAccountNameField", 0,
		new PropertyModel<>(this, "debitAccountNameValue"), this.debitAccountNameProvider);
	this.debitAccountNameField.setRequired(true);
	this.debitForm.add(this.debitAccountNameField);
	this.debitAccountNameFeedback = new TextFeedbackPanel("debitAccountNameFeedback", this.debitAccountNameField);
	this.debitForm.add(this.debitAccountNameFeedback);
	if (ruleObject.get("debit_account_id") != null) {
	    this.debitAccountNameValue = jdbcTemplate.queryForObject(
		    "select id, name text from acc_gl_account where id = ?", new OptionMapper(),
		    ruleObject.get("debit_account_id"));
	    this.debitAccountNameProvider.applyWhere("id", "id = " + ruleObject.get("debit_account_id"));
	} else {
	    List<String> tags = jdbcTemplate.queryForList("SELECT tag_id FROM acc_rule_tags WHERE acc_type_enum = "
		    + RuleBrowsePage.DEBIT + " and acc_rule_id = ?", String.class, this.ruleId);
	    if (tags != null && !tags.isEmpty()) {
		this.debitAccountNameProvider.applyWhere("tag", "tag_id in (" + StringUtils.join(tags, ",") + ")");
	    }
	}

	this.debitAmountField = new TextField<>("debitAmountField", new PropertyModel<>(this, "debitAmountValue"));
	this.debitAmountField.setRequired(true);
	this.debitForm.add(this.debitAmountField);
	this.debitAmountFeedback = new TextFeedbackPanel("debitAmountFeedback", this.debitAmountField);
	this.debitForm.add(this.debitAmountFeedback);

	this.creditForm = new Form<>("creditForm");
	add(this.creditForm);

	this.creditButton = new AjaxButton("creditButton");
	this.creditButton.setOnSubmit(this::creditButtonSubmit);
	this.creditButton.setOnError(this::creditButtonError);
	this.creditForm.add(this.creditButton);

	this.creditAccountNameValue = null;
	this.creditAccountNameProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
	this.creditAccountNameProvider.applyWhere("usage", "account_usage = " + AccountUsage.Detail.getLiteral());
	this.creditAccountNameField = new Select2SingleChoice<>("creditAccountNameField", 0,
		new PropertyModel<>(this, "creditAccountNameValue"), this.creditAccountNameProvider);
	this.creditAccountNameField.setRequired(true);
	this.creditForm.add(this.creditAccountNameField);
	this.creditAccountNameFeedback = new TextFeedbackPanel("creditAccountNameFeedback",
		this.creditAccountNameField);
	this.creditForm.add(this.creditAccountNameFeedback);
	if (ruleObject.get("credit_account_id") != null) {
	    this.creditAccountNameValue = jdbcTemplate.queryForObject(
		    "select id, name text from acc_gl_account where id = ?", new OptionMapper(),
		    ruleObject.get("credit_account_id"));
	    this.creditAccountNameProvider.applyWhere("id", "id = " + ruleObject.get("credit_account_id"));
	} else {
	    List<String> tags = jdbcTemplate.queryForList("SELECT tag_id FROM acc_rule_tags WHERE acc_type_enum = "
		    + RuleBrowsePage.CREDIT + " and acc_rule_id = ?", String.class, this.ruleId);
	    if (tags != null && !tags.isEmpty()) {
		this.creditAccountNameProvider.applyWhere("tag", "tag_id in (" + StringUtils.join(tags, ",") + ")");
	    }
	}

	this.creditAmountField = new TextField<>("creditAmountField", new PropertyModel<>(this, "creditAmountValue"));
	this.creditAmountField.setRequired(true);
	this.creditForm.add(this.creditAmountField);
	this.creditAmountFeedback = new TextFeedbackPanel("creditAmountFeedback", this.creditAmountField);
	this.creditForm.add(this.creditAmountFeedback);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", AccountingPage.class);
	this.form.add(this.closeLink);

	this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
	this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"),
		this.officeProvider);
	this.officeField.setRequired(true);
	this.form.add(this.officeField);
	this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
	this.form.add(this.officeFeedback);
	if (ruleObject.get("office_id") != null) {
	    this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?",
		    new OptionMapper(), ruleObject.get("office_id"));
	    this.officeProvider.applyWhere("id", "id = " + ruleObject.get("office_id"));
	}

	this.currencyProvider = new OptionSingleChoiceProvider("m_organisation_currency", "code", "name",
		"concat(name,' [', code,']')");
	this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
		this.currencyProvider);
	this.currencyField.setRequired(true);
	this.form.add(this.currencyField);
	this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
	this.form.add(this.currencyFeedback);

	List<IColumn<Map<String, Object>, String>> debitColumn = Lists.newArrayList();
	debitColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::debitNameColumn));
	debitColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::debitAmountColumn));
	debitColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::debitActionItem, this::debitActionClick));
	this.debitValue = Lists.newArrayList();
	this.debitProvider = new ListDataProvider(this.debitValue);
	this.debitTable = new DataTable<>("debitTable", debitColumn, this.debitProvider, 20);
	this.form.add(this.debitTable);
	this.debitTable.addTopToolbar(new HeadersToolbar<>(this.debitTable, this.debitProvider));
	this.debitTable.addBottomToolbar(new NoRecordsToolbar(this.debitTable));

	List<IColumn<Map<String, Object>, String>> creditColumn = Lists.newArrayList();
	creditColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::creditNameColumn));
	creditColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::creditAmountColumn));
	creditColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::creditActionItem, this::creditActionClick));
	this.creditValue = Lists.newArrayList();
	this.creditProvider = new ListDataProvider(this.creditValue);
	this.creditTable = new DataTable<>("creditTable", creditColumn, this.creditProvider, 20);
	this.form.add(this.creditTable);
	this.creditTable.addTopToolbar(new HeadersToolbar<>(this.creditTable, this.creditProvider));
	this.creditTable.addBottomToolbar(new NoRecordsToolbar(this.creditTable));

	this.referenceNumberField = new TextField<>("referenceNumberField",
		new PropertyModel<>(this, "referenceNumberValue"));
	this.referenceNumberField.setRequired(true);
	this.form.add(this.referenceNumberField);
	this.referenceNumberFeedback = new TextFeedbackPanel("referenceNumberFeedback", this.referenceNumberField);
	this.form.add(this.referenceNumberFeedback);

	this.transactionDateValue = new Date();
	this.transactionDateField = new DateTextField("transactionDateField",
		new PropertyModel<>(this, "transactionDateValue"));
	this.transactionDateField.setRequired(true);
	this.form.add(this.transactionDateField);
	this.transactionDateFeedback = new TextFeedbackPanel("transactionDateFeedback", this.transactionDateField);
	this.form.add(this.transactionDateFeedback);

	this.paymentTypeProvider = new OptionSingleChoiceProvider("m_payment_type", "id", "value");
	this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", 0,
		new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
	this.form.add(this.paymentTypeField);
	this.paymentTypeFeedback = new TextFeedbackPanel("paymentTypeFeedback", this.paymentTypeField);
	this.form.add(this.paymentTypeFeedback);

	this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
	this.form.add(this.accountField);
	this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
	this.form.add(this.accountFeedback);

	this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
	this.form.add(this.chequeField);
	this.chequeFeedback = new TextFeedbackPanel("chequeFeedback", this.chequeField);
	this.form.add(this.chequeFeedback);

	this.routingCodeField = new TextField<>("routingCodeField", new PropertyModel<>(this, "routingCodeValue"));
	this.form.add(this.routingCodeField);
	this.routingCodeFeedback = new TextFeedbackPanel("routingCodeFeedback", this.routingCodeField);
	this.form.add(this.routingCodeFeedback);

	this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
	this.form.add(this.receiptField);
	this.receiptFeedback = new TextFeedbackPanel("receiptFeedback", this.receiptField);
	this.form.add(this.receiptFeedback);

	this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
	this.form.add(this.bankField);
	this.bankFeedback = new TextFeedbackPanel("bankFeedback", this.bankField);
	this.form.add(this.bankFeedback);

	this.commentField = new TextArea<>("commentField", new PropertyModel<>(this, "commentValue"));
	this.form.add(this.commentField);
	this.commentFeedback = new TextFeedbackPanel("commentFeedback", this.commentField);
	this.form.add(this.commentFeedback);
    }

    private ItemPanel debitNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(name));
    }

    private ItemPanel debitAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Double amount = (Double) model.get(jdbcColumn);
	if (amount == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(amount)));
	}
    }

    private void debitActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.debitValue.size(); i++) {
	    Map<String, Object> column = this.debitValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.debitValue.remove(index);
	}
	ajaxRequestTarget.add(this.debitTable);
    }

    private List<ActionItem> debitActionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private ItemPanel creditNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(name));
    }

    private ItemPanel creditAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Double amount = (Double) model.get(jdbcColumn);
	if (amount == null) {
	    return new TextCell(Model.of(""));
	} else {
	    return new TextCell(Model.of(String.valueOf(amount)));
	}
    }

    private void creditActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	int index = -1;
	for (int i = 0; i < this.creditValue.size(); i++) {
	    Map<String, Object> column = this.creditValue.get(i);
	    if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
		index = i;
		break;
	    }
	}
	if (index >= 0) {
	    this.creditValue.remove(index);
	}
	ajaxRequestTarget.add(this.creditTable);
    }

    private List<ActionItem> creditActionItem(String s, Map<String, Object> stringObjectMap) {
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

    protected void debitButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
	Map<String, Object> debit = Maps.newHashMap();
	debit.put("uuid", UUID.randomUUID().toString());
	debit.put("id", this.debitAccountNameValue.getId());
	debit.put("name", this.debitAccountNameValue.getText());
	debit.put("amount", this.debitAmountValue);
	this.debitValue.add(debit);
	this.debitAmountValue = 0.d;
	target.add(this.form);
	target.add(this.debitForm);
    }

    protected void creditButtonError(AjaxButton button, AjaxRequestTarget target) {
	target.add(this.creditForm);
    }

    protected void debitButtonError(AjaxButton button, AjaxRequestTarget target) {
	target.add(this.debitForm);
    }

    protected void creditButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
	Map<String, Object> credit = Maps.newHashMap();
	credit.put("uuid", UUID.randomUUID().toString());
	credit.put("id", this.creditAccountNameValue.getId());
	credit.put("name", this.creditAccountNameValue.getText());
	credit.put("amount", this.creditAmountValue);
	this.creditValue.add(credit);
	this.creditAmountValue = 0.d;
	target.add(this.form);
	target.add(this.creditForm);
    }

}
