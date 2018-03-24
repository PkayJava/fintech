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

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccGLJournalEntry;
import com.angkorteam.fintech.ddl.MAppUser;
import com.angkorteam.fintech.ddl.MOffice;
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
public class SearchJournalPage extends Page {

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
            breadcrumb.setLabel("Search Journal Entries");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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
        this.entryBlock = new WebMarkupBlock("entryBlock", Size.Twelve_12);
        add(this.entryBlock);
        this.entryIContainer = new WebMarkupContainer("entryIContainer");
        this.entryBlock.add(this.entryIContainer);
        this.entryProvider = new JdbcProvider(AccGLJournalEntry.NAME);
        this.entryProvider.applyJoin("acc_gl_account", "LEFT JOIN " + AccGLAccount.NAME + " ON " + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ACCOUNT_ID + " = " + AccGLAccount.NAME + "." + AccGLAccount.Field.ID);
        this.entryProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
        this.entryProvider.applyJoin("m_appuser", "LEFT JOIN " + MAppUser.NAME + " ON " + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.CREATED_BY_ID + " = " + MAppUser.NAME + "." + MAppUser.Field.ID);

        this.entryProvider.boardField(AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ID, "id", Long.class);
        this.entryProvider.boardField(AccGLAccount.NAME + "." + AccGLAccount.Field.NAME, "account_name", String.class);
        this.entryProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
        this.entryProvider.boardField(AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ENTRY_DATE, "transaction_date", Date.class);
        this.entryProvider.boardField(AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.TRANSACTION_ID, "transaction_id", Long.class);
        this.entryProvider.boardField("CASE " + AccGLAccount.NAME + "." + AccGLAccount.Field.CLASSIFICATION_ENUM + " WHEN 1 THEN 'Asset' WHEN 2 THEN 'Liability' WHEN 3 THEN 'Equity' WHEN 4 THEN 'Income' WHEN 5 THEN 'Expense' END", "account_type", String.class);
        this.entryProvider.boardField(MAppUser.NAME + "." + MAppUser.Field.USERNAME, "created_by", String.class);
        this.entryProvider.boardField("if(" + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.TYPE_ENUM + " = 1, NULL, " + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.AMOUNT + ")", "debit_amount", Double.class);
        this.entryProvider.boardField("if(" + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.TYPE_ENUM + " = 1, " + AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.AMOUNT + ", NULL)", "credit_amount", Double.class);

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
        this.entryIContainer.add(this.entryTable);
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
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
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
        this.accountNameProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME, "concat(" + AccGLAccount.Field.NAME + ",' [', " + AccGLAccount.Field.GL_CODE + ", ']')");
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
            this.entryProvider.applyWhere("office", MOffice.NAME + "." + MOffice.Field.ID + " = " + this.officeValue.getId());
        } else {
            this.entryProvider.removeWhere("office");
        }
        if (this.accountNameValue != null) {
            this.entryProvider.applyWhere("account", AccGLAccount.NAME + "." + AccGLAccount.Field.ID + " = " + this.accountNameValue.getId());
        } else {
            this.entryProvider.removeWhere("account");
        }
        if (this.manualValue != null) {
            if (JournalEntry.Manual.name().equals(this.manualValue.getId())) {
                this.entryProvider.applyWhere("manual", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.MANUAL_ENTRY + " = " + JournalEntry.Manual.getLiteral());
            } else if (JournalEntry.System.name().equals(this.manualValue.getId())) {
                this.entryProvider.applyWhere("manual", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.MANUAL_ENTRY + " = " + JournalEntry.System.getLiteral());
            }
        } else {
            this.entryProvider.removeWhere("manual");
        }
        if (this.fromDateValue != null && this.toDateValue != null) {
            if (this.fromDateValue.before(this.toDateValue)) {
                this.entryProvider.applyWhere("transaction_date", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ENTRY_DATE + " between '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "' and '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "'");
            } else {
                this.entryProvider.applyWhere("transaction_date", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ENTRY_DATE + " between '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "' and '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "'");
            }
        } else if (this.fromDateValue == null && this.toDateValue == null) {
            this.entryProvider.removeWhere("transaction_date");
        } else {
            if (this.toDateValue != null) {
                this.entryProvider.applyWhere("transaction_date", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ENTRY_DATE + " <= '" + DateFormatUtils.format(this.toDateValue, "yyyy-MM-dd") + "'");
            }
            if (this.fromDateValue != null) {
                this.entryProvider.applyWhere("transaction_date", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.ENTRY_DATE + " >= '" + DateFormatUtils.format(this.fromDateValue, "yyyy-MM-dd") + "'");
            }
        }
        if (this.transactionNumberValue != null) {
            this.entryProvider.applyWhere("transactionNumber", AccGLJournalEntry.NAME + "." + AccGLJournalEntry.Field.TRANSACTION_ID + " = '" + this.transactionNumberValue + "'");
        } else {
            this.entryProvider.removeWhere("transactionNumber");
        }
    }
}
