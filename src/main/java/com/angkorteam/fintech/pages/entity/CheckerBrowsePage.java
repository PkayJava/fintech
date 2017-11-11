package com.angkorteam.fintech.pages.entity;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.EntityStatus;
import com.angkorteam.fintech.dto.enums.EntityType;
import com.angkorteam.fintech.helper.EntityCheckHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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
 * Created by socheatkhauv on 7/15/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CheckerBrowsePage extends Page {

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
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataBlock();

        this.createLink = new BookmarkablePageLink<>("createLink", CheckerCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initDataBlock() {
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

        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("m_entity_datatable_check");
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.boardField("case application_table_name " + StringUtils.join(entity, " ") + " end", "entity", String.class);
        this.dataProvider.boardField("case system_defined when 0 then 'No' else 'Yes' end", "system", String.class);
        this.dataProvider.boardField("case status_enum " + StringUtils.join(status, " ") + " end", "status", String.class);
        this.dataProvider.boardField("x_registered_table_name", "dataTable", String.class);
        this.dataProvider.boardField("product_id", "product", Long.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Entity"), "entity", "entity", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Product"), "product", "account", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Data Table"), "dataTable", "dataTable", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Status"), "status", "status", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("System"), "system", "system", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Integer id = (Integer) model.get("id");
        JsonNode node = null;
        try {
            EntityCheckHelper.delete((Session) getSession(), String.valueOf(id));
        } catch (UnirestException e) {
        }
        reportError(node, target);
        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("entity".equals(column) || "dataTable".equals(column) || "status".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("account".equals(column)) {
            Integer value = (Integer) model.get(column);
            return new TextCell(value);
        } else if ("system".equals(column)) {
            String value = (String) model.get(column);
            if ("Yes".equals(value)) {
                return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
            } else if ("No".equals(value)) {
                return new BadgeCell(BadgeType.Success, Model.of("No"));
            } else {
                return new TextCell(value);
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
