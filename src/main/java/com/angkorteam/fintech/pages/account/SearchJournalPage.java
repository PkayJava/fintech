package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.JournalEntry;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.ManualEntryProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.*;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class SearchJournalPage extends Page {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,###.000");

    private Form<Void> form;
    private Button searchButton;
    private BookmarkablePageLink<Void> closeLink;

    private OptionSingleChoiceProvider accountNameProvider;
    private Option accountNameValue;
    private Select2SingleChoice<Option> accountNameField;
    private TextFeedbackPanel accountNameFeedback;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private ManualEntryProvider manualProvider;
    private Option manualValue;
    private Select2SingleChoice<Option> manualField;
    private TextFeedbackPanel manualFeedback;

    private Date fromDateValue;
    private DateTextField fromDateField;
    private TextFeedbackPanel fromDateFeedback;

    private Date toDateValue;
    private DateTextField toDateField;
    private TextFeedbackPanel toDateFeedback;

    private String transactionNumberValue;
    private TextField<String> transactionNumberField;
    private TextFeedbackPanel transactionNumberFeedback;

    private DataTable<Map<String, Object>, String> entryTable;
    private JdbcProvider entryProvider;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.searchButton = new Button("searchButton");
        this.searchButton.setOnSubmit(this::searchButtonSubmit);
        this.form.add(this.searchButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountingPage.class);
        this.form.add(this.closeLink);

        this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.form.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.form.add(this.fromDateFeedback);

        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.form.add(this.toDateField);
        this.toDateFeedback = new TextFeedbackPanel("toDateFeedback", this.toDateField);
        this.form.add(this.toDateFeedback);

        this.accountNameProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name", "concat(name,' [', gl_code, ']')");
        this.accountNameField = new Select2SingleChoice<>("accountNameField", 0, new PropertyModel<>(this, "accountNameValue"), this.accountNameProvider);
        this.form.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.form.add(this.accountNameFeedback);

        this.manualProvider = new ManualEntryProvider();
        this.manualField = new Select2SingleChoice<>("manualField", 0, new PropertyModel<>(this, "manualValue"), this.manualProvider);
        this.form.add(this.manualField);
        this.manualFeedback = new TextFeedbackPanel("manualFeedback", this.manualField);
        this.form.add(this.manualFeedback);

        this.transactionNumberField = new TextField<>("transactionNumberField", new PropertyModel<>(this, "transactionNumberValue"));
        this.form.add(this.transactionNumberField);
        this.transactionNumberFeedback = new TextFeedbackPanel("transactionNumberFeedback", this.transactionNumberField);
        this.form.add(this.transactionNumberFeedback);

        this.entryProvider = new JdbcProvider("acc_gl_journal_entry");
        this.entryProvider.addJoin("LEFT JOIN acc_gl_account ON acc_gl_journal_entry.account_id = acc_gl_account.id");
        this.entryProvider.addJoin("LEFT JOIN m_office ON acc_gl_journal_entry.office_id = m_office.id");
        this.entryProvider.addJoin("LEFT JOIN m_appuser ON acc_gl_journal_entry.createdby_id = m_appuser.id");

        this.entryProvider.boardField("acc_gl_journal_entry.id", "id", Long.class);
        this.entryProvider.boardField("acc_gl_account.name", "account_name", String.class);
        this.entryProvider.boardField("m_office.name", "office", String.class);
        this.entryProvider.boardField("acc_gl_journal_entry.entry_date", "transaction_date", Date.class);
        this.entryProvider.boardField("acc_gl_journal_entry.transaction_id", "transaction_id", Long.class);
        this.entryProvider.boardField("CASE acc_gl_account.classification_enum WHEN 1 THEN 'Asset' WHEN 2 THEN 'Liability' WHEN 3 THEN 'Equity' WHEN 4 THEN 'Income' WHEN 5 THEN 'Expense' END", "account_type", String.class);
        this.entryProvider.boardField("m_appuser.username", "created_by", String.class);
        this.entryProvider.boardField("if(acc_gl_journal_entry.type_enum = 1, NULL, acc_gl_journal_entry.amount)", "debit_amount", Double.class);
        this.entryProvider.boardField("if(acc_gl_journal_entry.type_enum = 1, acc_gl_journal_entry.amount, NULL)", "credit_amount", Double.class);

        List<IColumn<Map<String, Object>, String>> debitColumn = Lists.newArrayList();
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.String, Model.of("Office"), "office", "office", this::officeColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Date, Model.of("Transaction Date"), "transaction_date", "transaction_date", this::transactionDateColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.String, Model.of("Transaction ID"), "transaction_id", "transaction_id", this::transactionIdColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Type"), "account_type", "account_type", this::accountTypeColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Created By"), "created_by", "created_by", this::createdByColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Account"), "account_name", "account_name", this::accountNameColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Debit"), "debit_amount", "debit_amount", this::debitAmountColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Credit"), "credit_amount", "credit_amount", this::creditAmountColumn));

        this.entryTable = new DataTable<>("entryTable", debitColumn, this.entryProvider, 20);
        this.add(this.entryTable);
        this.entryTable.addTopToolbar(new HeadersToolbar<>(this.entryTable, this.entryProvider));
        this.entryTable.addBottomToolbar(new NoRecordsToolbar(this.entryTable));
        this.entryTable.addBottomToolbar(new NavigationToolbar(this.entryTable));

    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel transactionIdColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String transactionId = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("transactionId", transactionId);
        return new LinkCell(TransactionPage.class, parameters, Model.of(transactionId));
    }

    private ItemPanel accountTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String accountType = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(accountType));
    }

    private ItemPanel createdByColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String createdBy = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(createdBy));
    }

    private ItemPanel accountNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String accountName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(accountName));
    }

    private ItemPanel debitAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal amount = (BigDecimal) model.get(jdbcColumn);
        if (amount == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(FORMAT.format(amount.doubleValue())));
        }
    }

    private ItemPanel officeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String office = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(office));
    }

    private ItemPanel creditAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal amount = (BigDecimal) model.get(jdbcColumn);
        if (amount == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(FORMAT.format(amount.doubleValue())));
        }
    }

    private ItemPanel transactionDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date transactionDate = (Date) model.get(jdbcColumn);
        if (transactionDate != null) {
            return new TextCell(Model.of(DateFormatUtils.format(transactionDate, "yyyy-MM-dd")));
        } else {
            return new TextCell(Model.of(""));
        }
    }

    protected void searchButtonSubmit(Button button) {
        if (this.officeValue != null) {
            this.entryProvider.applyWhere("office", "m_office.id = " + this.officeValue.getId());
        } else {
            this.entryProvider.removeWhere("office");
        }
        if (this.accountNameValue != null) {
            this.entryProvider.applyWhere("account", "acc_gl_account.id = " + this.accountNameValue.getId());
        } else {
            this.entryProvider.removeWhere("account");
        }
        if (this.manualValue != null) {
            if (JournalEntry.Manual.name().equals(this.manualValue.getId())) {
                this.entryProvider.applyWhere("manual", "acc_gl_journal_entry.manual_entry = " + JournalEntry.Manual.getLiteral());
            } else if (JournalEntry.System.name().equals(this.manualValue.getId())) {
                this.entryProvider.applyWhere("manual", "acc_gl_journal_entry.manual_entry = " + JournalEntry.System.getLiteral());
            }
        } else {
            this.entryProvider.removeWhere("manual");
        }
        if (this.fromDateValue != null && this.toDateValue != null) {
            if (this.fromDateValue.before(this.toDateValue)) {
                this.entryProvider.applyWhere("transaction_date", "acc_gl_journal_entry.entry_date between '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "' and '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "'");
            } else {
                this.entryProvider.applyWhere("transaction_date", "acc_gl_journal_entry.entry_date between '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "' and '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "'");
            }
        } else if (this.fromDateValue == null && this.toDateValue == null) {
            this.entryProvider.removeWhere("transaction_date");
        } else {
            if (this.toDateValue != null) {
                this.entryProvider.applyWhere("transaction_date", "acc_gl_journal_entry.entry_date <= '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "'");
            }
            if (this.fromDateValue != null) {
                this.entryProvider.applyWhere("transaction_date", "acc_gl_journal_entry.entry_date >= '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "'");
            }
        }
        if (this.transactionNumberValue != null) {
            this.entryProvider.applyWhere("transactionNumber", "transaction_id = '" + this.transactionNumberValue + "'");
        } else {
            this.entryProvider.removeWhere("transactionNumber");
        }
    }
}
