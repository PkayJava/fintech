package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
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
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> hierarchyLink;

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
            breadcrumb.setLabel("Chart of Accounts");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("acc_gl_account");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("gl_code", "gl_code", String.class);
        List<String> classification_enum = Lists.newArrayList();
        for (AccountType accountType : AccountType.values()) {
            classification_enum.add("when " + accountType.getLiteral() + " then '" + accountType.getDescription() + "'");
        }
        this.provider.boardField("case classification_enum " + StringUtils.join(classification_enum, " ") + " end", "classification_enum", String.class);
        this.provider.boardField("account_usage", "account_usage", Integer.class);
        this.provider.boardField("disabled", "disabled", Boolean.class);
        this.provider.boardField("manual_journal_entries_allowed", "manual_journal_entries_allowed", Boolean.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("Account"), "name", "name", this::accountColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("GL Code"), "gl_code", "gl_code", this::glCodeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Account Type"), "classification_enum", "classification_enum", this::accountTypeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Is Disabled ?"), "disabled", "disabled", this::disabledColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Is Manual Entries Allowed ?"), "manual_journal_entries_allowed", "manual_journal_entries_allowed", this::manualEntryColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.hierarchyLink = new BookmarkablePageLink<>("hierarchyLink", AccountHierarchyPage.class);
        add(this.hierarchyLink);

        this.createLink = new BookmarkablePageLink<>("createLink", AccountCreatePage.class);
        add(this.createLink);
    }

    private void actionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        if ("delete".equals(link)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.delete((Session) getSession(), String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, target);
            target.add(this.dataTable);
        } else if ("enable".equals(link)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.enable((Session) getSession(), String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, target);
            target.add(this.dataTable);
        } else if ("disable".equals(link)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.disable((Session) getSession(), String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, target);
            target.add(this.dataTable);
        }
    }

    private List<ActionItem> actionItem(String link, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean disabled = (Boolean) model.get("disabled");
        if (disabled == null || disabled) {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
        } else {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
        }
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel accountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String account = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(account));
    }

    private ItemPanel glCodeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String glCode = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("accountId", model.get("id"));
        return new LinkCell(AccountModifyPage.class, parameters, Model.of(glCode));
    }

    private ItemPanel disabledColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean disabled = (Boolean) model.get(jdbcColumn);
        if (disabled == null || disabled) {
            return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Success, Model.of("No"));
        }
    }

    private ItemPanel manualEntryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean manualEntry = (Boolean) model.get(jdbcColumn);
        if (manualEntry != null && manualEntry) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel accountTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

}
