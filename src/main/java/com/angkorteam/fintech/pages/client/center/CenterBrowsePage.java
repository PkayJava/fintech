package com.angkorteam.fintech.pages.client.center;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.constant.StatusEnum;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterBrowsePage extends Page {

    protected DataTable<Map<String, Object>, String> dataTable;

    protected JdbcProvider provider;

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
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Centers");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<String> status = Lists.newArrayList();
        for (StatusEnum t : StatusEnum.values()) {
            status.add("when " + t.getLiteral() + " then '" + t.getDescription() + "'");
        }
        status.add("else '" + StatusEnum.Invalid.getDescription() + "'");

        this.provider = new JdbcProvider("m_group");
        this.provider.addJoin("left join m_office on m_group.office_id = m_office.id ");
        this.provider.boardField("m_group.id", "id", Long.class);
        this.provider.boardField("m_group.account_no", "account", String.class);
        this.provider.boardField("m_group.display_name", "name", String.class);
        this.provider.boardField("m_office.name", "office", String.class);
        this.provider.boardField("m_group.external_id", "externalId", String.class);
        this.provider.boardField("case m_group.status_enum " + StringUtils.join(status, " ") + " end", "status", String.class);

        this.provider.applyWhere("level_id", "m_group.level_id = 1");

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Account #"), "account", "account", this::accountColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("External ID"), "externalId", "externalId", this::externalIdColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Status"), "status", "status", this::statusColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Office"), "office", "office", this::officeColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", CenterCreatePage.class);
        add(this.createLink);
    }

    protected ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new LinkCell(CenterPreviewPage.class, null, value);
    }

    protected ItemPanel officeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel statusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel accountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel externalIdColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
