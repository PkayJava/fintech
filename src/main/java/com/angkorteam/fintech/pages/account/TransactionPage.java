package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.popup.ReversePopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
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
public class TransactionPage extends Page {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,###.000");

    private String transactionId;

    private AjaxLink<Void> reverseButton;

    private String officeValue;
    private Label officeField;

    private String transactionNumberValue;
    private Label transactionNumberField;

    private String transactionDateValue;
    private Label transactionDateField;

    private String createdOnValue;
    private Label createdOnField;

    private String createdByValue;
    private Label createdByField;

    private DataTable<Map<String, Object>, String> entryTable;
    private JdbcProvider entryProvider;

    private ModalWindow commentPopup;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.transactionId = parameters.get("transactionId").toString("");

        JdbcNamed jdbcNamed = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = new SelectQuery("acc_gl_journal_entry");
        selectQuery.addJoin(JoinType.LeftJoin, "acc_gl_account", "acc_gl_journal_entry.account_id = acc_gl_account.id");
        selectQuery.addJoin(JoinType.LeftJoin, "m_office", "acc_gl_journal_entry.office_id = m_office.id");
        selectQuery.addJoin(JoinType.LeftJoin, "m_appuser", "acc_gl_journal_entry.createdby_id = m_appuser.id");
        selectQuery.addField("acc_gl_journal_entry.id");
        selectQuery.addField("m_office.name office");
        selectQuery.addField("acc_gl_journal_entry.entry_date transaction_date");
        selectQuery.addField("acc_gl_journal_entry.created_date created_date");
        selectQuery.addField("acc_gl_journal_entry.transaction_id transaction_id");
        selectQuery.addField("m_appuser.username created_by");
        selectQuery.addWhere("acc_gl_journal_entry.transaction_id = :transaction_id", this.transactionId);
        selectQuery.setLimit(0l, 1l);

        Map<String, Object> entry = jdbcNamed.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.reverseButton = new AjaxLink<>("reverseButton");
        this.reverseButton.setOnClick(this::reverseButtonClick);
        this.add(this.reverseButton);

        this.officeValue = (String) entry.get("office");
        this.officeField = new Label("officeField", new PropertyModel<>(this, "officeValue"));
        this.add(this.officeField);

        this.createdByValue = (String) entry.get("created_by");
        this.createdByField = new Label("createdByField", new PropertyModel<>(this, "createdByValue"));
        this.add(this.createdByField);

        Date transactionDate = (Date) entry.get("transaction_date");
        this.transactionDateValue = transactionDate == null ? "" : DateFormatUtils.format(transactionDate, "yyyy-MM-dd");
        this.transactionDateField = new Label("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.add(this.transactionDateField);

        Date createdOn = (Date) entry.get("created_date");
        this.createdOnValue = createdOn == null ? "" : DateFormatUtils.format(createdOn, "yyyy-MM-dd");
        this.createdOnField = new Label("createdOnField", new PropertyModel<>(this, "createdOnValue"));
        this.add(this.createdOnField);

        this.transactionNumberValue = (String) entry.get("transaction_id");
        this.transactionNumberField = new Label("transactionNumberField", new PropertyModel<>(this, "transactionNumberValue"));
        this.add(this.transactionNumberField);

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

        this.entryProvider.applyWhere("transaction_id", "acc_gl_journal_entry.transaction_id = '" + this.transactionId + "'");

        List<IColumn<Map<String, Object>, String>> debitColumn = Lists.newArrayList();
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Entry ID"), "id", "id", this::idColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Type"), "account_type", "account_type", this::accountTypeColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Account"), "account_name", "account_name", this::accountNameColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Debit"), "debit_amount", "debit_amount", this::debitAmountColumn));
        debitColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Credit"), "credit_amount", "credit_amount", this::creditAmountColumn));

        this.entryTable = new DataTable<>("entryTable", debitColumn, this.entryProvider, 20);
        this.add(this.entryTable);
        this.entryTable.addTopToolbar(new HeadersToolbar<>(this.entryTable, this.entryProvider));
        this.entryTable.addBottomToolbar(new NoRecordsToolbar(this.entryTable));

        this.commentPopup = new ModalWindow("commentPopup");
        add(this.commentPopup);

        this.commentPopup.setContent(new ReversePopup(this.commentPopup.getContentId(), this.commentPopup, this, this.transactionId));
        this.commentPopup.setOnCloseButtonClicked(this::commentPopupOnCloseButtonClicked);
        this.commentPopup.setOnClose(this::commentPopupOnClose);

    }

    private void commentPopupOnClose(AjaxRequestTarget target) {
        setResponsePage(SearchJournalPage.class);
    }

    private Boolean commentPopupOnCloseButtonClicked(ModalWindow modalWindow, AjaxRequestTarget target) {
        return false;
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel accountTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String accountType = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(accountType));
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

    private ItemPanel creditAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal amount = (BigDecimal) model.get(jdbcColumn);
        if (amount == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(FORMAT.format(amount.doubleValue())));
        }
    }

    protected void reverseButtonClick(AjaxLink<Void> button, AjaxRequestTarget target) {
        this.commentPopup.show(target);
    }
}
