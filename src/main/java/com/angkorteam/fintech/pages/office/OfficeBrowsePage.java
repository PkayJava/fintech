package com.angkorteam.fintech.pages.office;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> hierarchyLink;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_office office");
        this.provider.addJoin("LEFT JOIN m_office parent ON office.parent_id = parent.id");
        this.provider.boardField("office.id", "id", Long.class);
        this.provider.boardField("office.external_id", "external_id", String.class);
        this.provider.boardField("office.parent_id", "parent_id", Long.class);
        this.provider.boardField("office.name", "name", String.class);
        this.provider.boardField("parent.name", "parent_name", String.class);
        this.provider.boardField("office.opening_date", "opening_date", Calendar.Date);

        this.provider.selectField("parent_id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("External ID"), "external_id", "external_id", this::externalIdColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Parent Name"), "parent_name", "parent_name", this::parentNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Opening Date"), "opening_date", "opening_date", this::openingDateColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.hierarchyLink = new BookmarkablePageLink<>("hierarchyLink", OfficeHierarchyPage.class);
        add(this.hierarchyLink);

        this.createLink = new BookmarkablePageLink<>("createLink", OfficeCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel externalIdColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String externalId = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(externalId));
    }

    private ItemPanel parentNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String parentName = (String) model.get(jdbcColumn);
        if (Strings.isNullOrEmpty(parentName)) {
            return new TextCell(Model.of(parentName));
        } else {
            PageParameters parameters = new PageParameters();
            parameters.add("officeId", model.get("parent_id"));
            return new LinkCell(OfficeModifyPage.class, parameters, Model.of(parentName));
        }

    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("officeId", model.get("id"));
        return new LinkCell(OfficeModifyPage.class, parameters, Model.of(name));
    }

    private ItemPanel openingDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date openingDate = (Date) model.get(jdbcColumn);
        if (openingDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(openingDate)));
        }
    }

}
