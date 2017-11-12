package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.AccountingClosureHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/12/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClosureBrowsePage extends Page {


    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

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
            breadcrumb.setLabel("Account Closure");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataBlock();

        this.createLink = new BookmarkablePageLink<>("createLink", ClosureCreatePage.class);
        add(this.createLink);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", WebMarkupBlock.Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("acc_gl_closure");
        this.dataProvider.addJoin("LEFT JOIN m_office ON acc_gl_closure.office_id = m_office.id");
        this.dataProvider.addJoin("LEFT JOIN m_appuser on acc_gl_closure.createdby_id = m_appuser.id");
        this.dataProvider.boardField("acc_gl_closure.id", "id", Long.class);
        this.dataProvider.boardField("m_office.name", "office", String.class);
        this.dataProvider.boardField("acc_gl_closure.closing_date", "closing_date", Date.class);
        this.dataProvider.boardField("acc_gl_closure.comments", "comment", String.class);
        this.dataProvider.boardField("m_appuser.username", "created_by", String.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = new ArrayList<>();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Accounting Closure Date"), "closing_date", "closing_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Comments"), "comment", "comment", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Created By"), "created_by", "created_by", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        Long value = (Long) model.get("id");
        JsonNode node = null;
        try {
            node = AccountingClosureHelper.delete((Session) getSession(), String.valueOf(value));
        } catch (UnirestException e) {
        }
        reportError(node, target);
        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("office".equals(column) || "comment".equals(column) || "created_by".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("closing_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown" + column);
    }

}
