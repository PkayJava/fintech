package com.angkorteam.fintech.pages.account;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.popup.ReversePopup;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
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

/**
 * Created by socheatkhauv on 7/2/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TransactionPage extends Page {

    protected static final DecimalFormat FORMAT = new DecimalFormat("#,###.000");

    protected String transactionId;

    protected AjaxLink<Void> reverseButton;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeVContainer;
    protected String officeValue;
    protected ReadOnlyView officeView;

    protected WebMarkupBlock transactionNumberBlock;
    protected WebMarkupContainer transactionNumberVContainer;
    protected String transactionNumberValue;
    protected ReadOnlyView transactionNumberView;

    protected WebMarkupBlock transactionDateBlock;
    protected WebMarkupContainer transactionDateVContainer;
    protected String transactionDateValue;
    protected ReadOnlyView transactionDateView;

    protected WebMarkupBlock createdOnBlock;
    protected WebMarkupContainer createdOnVContainer;
    protected String createdOnValue;
    protected ReadOnlyView createdOnView;

    protected WebMarkupBlock createdByBlock;
    protected WebMarkupContainer createdByVContainer;
    protected String createdByValue;
    protected ReadOnlyView createdByView;

    protected DataTable<Map<String, Object>, String> entryTable;
    protected JdbcProvider entryProvider;
    protected List<IColumn<Map<String, Object>, String>> entryColumn;

    protected ModalWindow commentPopup;

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
            breadcrumb.setPage(SearchJournalPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("View Transaction");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
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

        this.officeValue = (String) entry.get("office");
        this.createdByValue = (String) entry.get("created_by");
        Date transactionDate = (Date) entry.get("transaction_date");
        this.transactionDateValue = transactionDate == null ? "" : DateFormatUtils.format(transactionDate, "yyyy-MM-dd");
        Date createdOn = (Date) entry.get("created_date");
        this.createdOnValue = createdOn == null ? "" : DateFormatUtils.format(createdOn, "yyyy-MM-dd");
        this.transactionNumberValue = (String) entry.get("transaction_id");
    }

    @Override
    protected void initComponent() {
        this.reverseButton = new AjaxLink<>("reverseButton");
        this.reverseButton.setOnClick(this::reverseButtonClick);
        this.add(this.reverseButton);

        initOfficeBlock();

        initCreatedByBlock();

        initTransactionDateBlock();

        initCreatedOnBlock();

        initTransactionNumberBlock();

        initEntryTable();

        this.commentPopup = new ModalWindow("commentPopup");
        add(this.commentPopup);
        this.commentPopup.setOnClose(this::commentPopupClose);
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

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

        this.entryProvider.applyWhere("transaction_id", "acc_gl_journal_entry.transaction_id = '" + this.transactionId + "'");

        this.entryColumn = Lists.newArrayList();
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Entry ID"), "id", "id", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Type"), "account_type", "account_type", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Long, Model.of("Account"), "account_name", "account_name", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Debit"), "debit_amount", "debit_amount", this::entryColumn));
        this.entryColumn.add(new TextColumn(this.entryProvider, ItemClass.Double, Model.of("Credit"), "credit_amount", "credit_amount", this::entryColumn));

        this.entryTable = new DataTable<>("entryTable", this.entryColumn, this.entryProvider, 20);
        add(this.entryTable);
        this.entryTable.addTopToolbar(new HeadersToolbar<>(this.entryTable, this.entryProvider));
        this.entryTable.addBottomToolbar(new NoRecordsToolbar(this.entryTable));
    }

    protected void initTransactionNumberBlock() {
        this.transactionNumberBlock = new WebMarkupBlock("transactionNumberBlock", Size.Six_6);
        add(this.transactionNumberBlock);
        this.transactionNumberVContainer = new WebMarkupContainer("transactionNumberVContainer");
        this.transactionNumberBlock.add(this.transactionNumberVContainer);
        this.transactionNumberView = new ReadOnlyView("transactionNumberView", new PropertyModel<>(this, "transactionNumberValue"));
        this.transactionNumberVContainer.add(this.transactionNumberView);
    }

    protected void initCreatedOnBlock() {
        this.createdOnBlock = new WebMarkupBlock("createdOnBlock", Size.Six_6);
        this.add(this.createdOnBlock);
        this.createdOnVContainer = new WebMarkupContainer("createdOnVContainer");
        this.createdOnBlock.add(this.createdOnVContainer);
        this.createdOnView = new ReadOnlyView("createdOnView", new PropertyModel<>(this, "createdOnValue"));
        this.createdOnVContainer.add(this.createdOnView);
    }

    protected void initTransactionDateBlock() {
        this.transactionDateBlock = new WebMarkupBlock("transactionDateBlock", Size.Six_6);
        add(this.transactionDateBlock);
        this.transactionDateVContainer = new WebMarkupContainer("transactionDateVContainer");
        this.transactionDateBlock.add(this.transactionDateVContainer);
        this.transactionDateView = new ReadOnlyView("transactionDateView", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateVContainer.add(this.transactionDateView);
    }

    protected void initCreatedByBlock() {
        this.createdByBlock = new WebMarkupBlock("createdByBlock", Size.Six_6);
        add(this.createdByBlock);
        this.createdByVContainer = new WebMarkupContainer("createdByVContainer");
        this.createdByBlock.add(this.createdByVContainer);
        this.createdByView = new ReadOnlyView("createdByView", new PropertyModel<>(this, "createdByValue"));
        this.createdByVContainer.add(this.createdByView);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        add(this.officeBlock);
        this.officeVContainer = new WebMarkupContainer("officeVContainer");
        this.officeBlock.add(this.officeVContainer);
        this.officeView = new ReadOnlyView("officeView", new PropertyModel<>(this, "officeValue"));
        this.officeVContainer.add(this.officeView);
    }

    protected void commentPopupClose(String elementId, AjaxRequestTarget target) {
        setResponsePage(SearchJournalPage.class);
    }

    protected ItemPanel entryColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column) || "account_type".equals(column) || "account_name".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("debit_amount".equals(column) || "credit_amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean reverseButtonClick(AjaxLink<Void> button, AjaxRequestTarget target) {
        this.commentPopup.setContent(new ReversePopup(this.commentPopup.getContentId(), this.commentPopup, this, this.transactionId));
        this.commentPopup.show(target);
        return false;
    }
}
