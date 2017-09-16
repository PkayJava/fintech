package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

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
import com.angkorteam.fintech.helper.PasswordPreferencesHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
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
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class PasswordPreferencesPage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> closeLink;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Password Preference");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_password_validation_policy");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("active", "active", Boolean.class);
        this.provider.boardField("description", "description", String.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Active"), "active", "active", this::activeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Description"), "description", "description", this::descriptionColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
        add(this.closeLink);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget target) {
        Integer id = (Integer) stringObjectMap.get("id");
        try {
            PasswordPreferencesHelper.update((Session) getSession(), String.valueOf(id));
        } catch (UnirestException e) {
        }
        target.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        Integer active = (Integer) stringObjectMap.get("active");
        if (active == null || active != 1) {
            actions.add(new ActionItem("activate", Model.of("Activate"), ItemCss.PRIMARY));
        }
        return actions;
    }

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value != null && value == 1) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel descriptionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

}
