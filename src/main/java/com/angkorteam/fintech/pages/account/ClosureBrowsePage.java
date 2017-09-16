package com.angkorteam.fintech.pages.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.AccountingClosureHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClosureBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Account Closure");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("acc_gl_closure");
        this.provider.addJoin("LEFT JOIN m_office ON acc_gl_closure.office_id = m_office.id");
        this.provider.addJoin("LEFT JOIN m_appuser on acc_gl_closure.createdby_id = m_appuser.id");
        this.provider.boardField("acc_gl_closure.id", "id", Long.class);
        this.provider.boardField("m_office.name", "office", String.class);
        this.provider.boardField("acc_gl_closure.closing_date", "closing_date", Date.class);
        this.provider.boardField("acc_gl_closure.comments", "comment", String.class);
        this.provider.boardField("m_appuser.username", "created_by", String.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Office"), "office", "office", this::officeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Accounting Closure Date"), "closing_date", "closing_date", this::closingDateColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Comments"), "comment", "comment", this::commentColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Created By"), "created_by", "created_by", this::createdByColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", ClosureCreatePage.class);
        add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Long value = (Long) stringObjectMap.get("id");
        JsonNode node = null;
        try {
            node = AccountingClosureHelper.delete((Session) getSession(), String.valueOf(value));
        } catch (UnirestException e) {
        }
        reportError(node, ajaxRequestTarget);
        ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel createdByColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel officeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel commentColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel closingDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        if (value != null) {
            return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value)));
        } else {
            return new TextCell(Model.of(""));
        }
    }

}
