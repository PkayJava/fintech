package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.JournalEntry;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.ManualEntryProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 7/2/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SearchJournalPage extends DeprecatedPage {

    protected Form<Void> form;
    protected Button searchButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock accountNameBlock;
    protected WebMarkupContainer accountNameIContainer;
    protected SingleChoiceProvider accountNameProvider;
    protected Option accountNameValue;
    protected Select2SingleChoice<Option> accountNameField;
    protected TextFeedbackPanel accountNameFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock manualBlock;
    protected WebMarkupContainer manualIContainer;
    protected ManualEntryProvider manualProvider;
    protected Option manualValue;
    protected Select2SingleChoice<Option> manualField;
    protected TextFeedbackPanel manualFeedback;

    protected WebMarkupBlock fromDateBlock;
    protected WebMarkupContainer fromDateIContainer;
    protected Date fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected WebMarkupBlock toDateBlock;
    protected WebMarkupContainer toDateIContainer;
    protected Date toDateValue;
    protected DateTextField toDateField;
    protected TextFeedbackPanel toDateFeedback;

    protected WebMarkupBlock transactionNumberBlock;
    protected WebMarkupContainer transactionNumberIContainer;
    protected String transactionNumberValue;
    protected TextField<String> transactionNumberField;
    protected TextFeedbackPanel transactionNumberFeedback;

    protected WebMarkupBlock entryBlock;
    protected WebMarkupContainer entryIContainer;
    protected DataTable<Map<String, Object>, String> entryTable;
    protected JdbcProvider entryProvider;
    protected List<IColumn<Map<String, Object>, String>> entryColumn;

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
            breadcrumb.setLabel("Search Journal Entries");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.searchButton = new Button("searchButton");
        this.searchButton.setOnSubmit(this::searchButtonSubmit);
        this.form.add(this.searchButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountingPage.class);
        this.form.add(this.closeLink);

        initAccountNameBlock();

        initOfficeBlock();

        initManualBlock();

        initFromDateBlock();

        initToDateBlock();

        initTransactionNumberBlock();

        initEntryTable();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initFromDateBlock() {
        this.fromDateBlock = new WebMarkupBlock("fromDateBlock", Size.Four_4);
        this.form.add(this.fromDateBlock);
        this.fromDateIContainer = new WebMarkupContainer("fromDateIContainer");
        this.fromDateBlock.add(this.fromDateIContainer);
        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.fromDateIContainer.add(this.fromDateFeedback);
    }

    protected void initToDateBlock() {
        this.toDateBlock = new WebMarkupBlock("toDateBlock", Size.Four_4);
        this.form.add(this.toDateBlock);
        this.toDateIContainer = new WebMarkupContainer("toDateIContainer");
        this.toDateBlock.add(this.toDateIContainer);
        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.toDateIContainer.add(this.toDateField);
        this.toDateFeedback = new TextFeedbackPanel("toDateFeedback", this.toDateField);
        this.toDateIContainer.add(this.toDateFeedback);
    }

    protected void initTransactionNumberBlock() {
        this.transactionNumberBlock = new WebMarkupBlock("transactionNumberBlock", Size.Four_4);
        this.form.add(this.transactionNumberBlock);
        this.transactionNumberIContainer = new WebMarkupContainer("transactionNumberIContainer");
        this.transactionNumberBlock.add(this.transactionNumberIContainer);
        this.transactionNumberField = new TextField<>("transactionNumberField", new PropertyModel<>(this, "transactionNumberValue"));
        this.transactionNumberIContainer.add(this.transactionNumberField);
        this.transactionNumberFeedback = new TextFeedbackPanel("transactionNumberFeedback", this.transactionNumberField);
        this.transactionNumberIContainer.add(this.transactionNumberFeedback);
    }

    protected void initEntryTable() {
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

        this.entryColumn = Lists.newArrayList();
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.String, Model.of("Office"), "office", "office", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Date, Model.of("Transaction Date"), "transaction_date", "transaction_date", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.String, Model.of("Transaction ID"), "transaction_id", "transaction_id", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Type"), "account_type", "account_type", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Created By"), "created_by", "created_by", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Account"), "account_name", "account_name", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Debit"), "debit_amount", "debit_amount", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Credit"), "credit_amount", "credit_amount", this::entryColumn));

        this.entryTable = new DataTable<>("entryTable", this.entryColumn, this.entryProvider, 20);
        this.add(this.entryTable);
        this.entryTable.addTopToolbar(new HeadersToolbar<>(this.entryTable, this.entryProvider));
        this.entryTable.addBottomToolbar(new NoRecordsToolbar(this.entryTable));
        this.entryTable.addBottomToolbar(new NavigationToolbar(this.entryTable));
    }

    protected void initManualBlock() {
        this.manualBlock = new WebMarkupBlock("manualBlock", Size.Four_4);
        this.form.add(this.manualBlock);
        this.manualIContainer = new WebMarkupContainer("manualIContainer");
        this.manualBlock.add(this.manualIContainer);
        this.manualProvider = new ManualEntryProvider();
        this.manualField = new Select2SingleChoice<>("manualField", new PropertyModel<>(this, "manualValue"), this.manualProvider);
        this.manualIContainer.add(this.manualField);
        this.manualFeedback = new TextFeedbackPanel("manualFeedback", this.manualField);
        this.manualIContainer.add(this.manualFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initAccountNameBlock() {
        this.accountNameBlock = new WebMarkupBlock("accountNameBlock", Size.Four_4);
        this.form.add(this.accountNameBlock);
        this.accountNameIContainer = new WebMarkupContainer("accountNameIContainer");
        this.accountNameBlock.add(this.accountNameIContainer);
        this.accountNameProvider = new SingleChoiceProvider("acc_gl_account", "id", "name", "concat(name,' [', gl_code, ']')");
        this.accountNameField = new Select2SingleChoice<>("accountNameField", new PropertyModel<>(this, "accountNameValue"), this.accountNameProvider);
        this.accountNameIContainer.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.accountNameIContainer.add(this.accountNameFeedback);
    }

    protected ItemPanel entryColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("office".equals(column) || "account_name".equals(column) || "created_by".equals(column) || "account_type".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("transaction_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        } else if ("transaction_id".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("transactionId", value);
            return new LinkCell(TransactionPage.class, parameters, value);
        } else if ("debit_amount".equals(column) || "credit_amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
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
