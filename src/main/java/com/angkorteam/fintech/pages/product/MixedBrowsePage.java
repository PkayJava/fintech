package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class MixedBrowsePage extends Page {

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
            breadcrumb.setLabel("Mixed Product");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_product_loan");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("short_name", "shortName", String.class);
        this.provider.boardField("close_date", "expiryDate", Date.class);
        this.provider.boardField("if(close_date >= date(now()), 'Active','Inactive')", "status", String.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
                this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Short Name"), "shortName",
                "shortName", this::shortNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Expiry Date"), "expiryDate",
                "expiryDate", this::expiryDateColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Status"), "status", "status",
                this::statusColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", MixedCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel statusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if (value != null && value.equals("Active")) {
            return new BadgeCell(BadgeType.Success, Model.of("Active"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("Inactive"));
        }
    }

    private ItemPanel expiryDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        if (value != null) {
            return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(value)));
        } else {
            return new TextCell(Model.of(""));
        }
    }

    private ItemPanel shortNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("loanId", model.get("id"));
        return new LinkCell(LoanCreatePage.class, parameters, Model.of(name));
    }

}
