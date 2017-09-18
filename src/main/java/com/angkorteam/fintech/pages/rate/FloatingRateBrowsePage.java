package com.angkorteam.fintech.pages.rate;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FloatingRateBrowsePage extends Page {

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
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Floating Rate");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_floating_rates");
        this.provider.addJoin("INNER join m_appuser on m_floating_rates.createdby_id = m_appuser.id");
        this.provider.boardField("m_floating_rates.id", "id", Long.class);
        this.provider.boardField("m_floating_rates.name", "name", String.class);
        this.provider.boardField("m_appuser.username", "createdBy", String.class);
        this.provider.boardField("m_floating_rates.is_base_lending_rate", "base_lending_rate", Boolean.class);
        this.provider.boardField("m_floating_rates.is_active", "active", Boolean.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Created By"), "createdBy", "createdBy", this::createdByColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Base Lending Rate"), "base_lending_rate", "base_lending_rate", this::baseLendingRateColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Active"), "active", "active", this::activeColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", FloatingRateCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean active = (Boolean) model.get(jdbcColumn);
        if (active != null && active) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel baseLendingRateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean baseLendingRate = (Boolean) model.get(jdbcColumn);
        if (baseLendingRate != null && baseLendingRate) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel createdByColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String createdBy = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(createdBy));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("rateId", model.get("id"));
        return new LinkCell(FloatingRateModifyPage.class, parameters, Model.of(name));
    }

}
