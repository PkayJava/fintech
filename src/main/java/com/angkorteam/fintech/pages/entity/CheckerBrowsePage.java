package com.angkorteam.fintech.pages.entity;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.EntityStatus;
import com.angkorteam.fintech.dto.EntityType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.EntityCheckHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/15/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CheckerBrowsePage extends Page {

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
            breadcrumb.setLabel("Entity Data Table Check");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        List<String> entity = Lists.newArrayList();
        for (EntityType t : EntityType.values()) {
            entity.add("when '" + t.getLiteral() + "' then '" + t.getDescription() + "'");
        }
        entity.add("else 'N/A'");
        List<String> status = Lists.newArrayList();
        for (EntityStatus t : EntityStatus.values()) {
            status.add("when " + t.getLiteral() + " then '" + t.getDescription() + "'");
        }
        status.add("else 'N/A'");

        this.provider = new JdbcProvider("m_entity_datatable_check");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("case application_table_name " + StringUtils.join(entity, " ") + " end", "entity", String.class);
        this.provider.boardField("case system_defined when 0 then 'No' else 'Yes' end", "system", String.class);
        this.provider.boardField("case status_enum " + StringUtils.join(status, " ") + " end", "status", String.class);
        this.provider.boardField("x_registered_table_name", "dataTable", String.class);
        this.provider.boardField("product_id", "product", Long.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Entity"), "entity", "entity", this::entityColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("Product"), "product", "account", this::productColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Data Table"), "dataTable", "dataTable", this::dataTableColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Status"), "status", "status", this::statusColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("System"), "system", "system", this::systemColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", CheckerCreatePage.class);
        add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Integer id = (Integer) model.get("id");
        JsonNode node = null;
        try {
            EntityCheckHelper.delete((Session) getSession(), String.valueOf(id));
        } catch (UnirestException e) {
        }
        reportError(node, target);
        target.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel productColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    private ItemPanel entityColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    private ItemPanel dataTableColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    private ItemPanel systemColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if ("Yes".equals(value)) {
            return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
        } else if ("No".equals(value)) {
            return new BadgeCell(BadgeType.Success, Model.of("No"));
        } else {
            return new TextCell(value);
        }
    }

    private ItemPanel statusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
