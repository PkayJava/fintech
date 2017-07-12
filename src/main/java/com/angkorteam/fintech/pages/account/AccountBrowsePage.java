package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class AccountBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> hierarchyLink;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("acc_gl_account");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("gl_code", "gl_code", String.class);
        this.provider.boardField("classification_enum", "classification_enum", Integer.class);
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

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        Long id = (Long) stringObjectMap.get("id");
        if ("delete".equals(s)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.deleteGLAccount(String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, ajaxRequestTarget);
            ajaxRequestTarget.add(this.dataTable);
        } else if ("enable".equals(s)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.enableGLAccount(String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, ajaxRequestTarget);
            ajaxRequestTarget.add(this.dataTable);
        } else if ("disable".equals(s)) {
            JsonNode node = null;
            try {
                node = GLAccountHelper.disableGLAccount(String.valueOf(id));
            } catch (UnirestException e) {
            }
            reportError(node, ajaxRequestTarget);
            ajaxRequestTarget.add(this.dataTable);
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean disabled = (Boolean) stringObjectMap.get("disabled");
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
        Integer accountType = (Integer) model.get(jdbcColumn);
        if (accountType == null) {
            return new TextCell(Model.of(""));
        } else {
            if (accountType == 1) {
                return new TextCell(Model.of("1.Asset"));
            } else if (accountType == 2) {
                return new TextCell(Model.of("2.Liability"));
            } else if (accountType == 3) {
                return new TextCell(Model.of("3.Equity"));
            } else if (accountType == 4) {
                return new TextCell(Model.of("4.Income"));
            } else if (accountType == 5) {
                return new TextCell(Model.of("5.Expense"));
            } else {
                return new TextCell(Model.of(accountType + ".N/A"));
            }
        }
    }

}
