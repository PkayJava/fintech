package com.angkorteam.fintech.pages.table;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.provider.JdbcProvider;
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

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class DataTableBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("x_registered_table");
        this.provider.boardField("registered_table_name", "table_name", String.class);
        this.provider.boardField("application_table_name", "associated", String.class);
        this.provider.boardField("category", "category", Integer.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Table Name"), "table_name", "table_name", this::tableNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Associated"), "associated", "associated", this::associatedColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Category"), "category", "category", this::categoryColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", DataTableCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel categoryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer category = (Integer) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(category)));
    }

    private ItemPanel tableNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String tableName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(tableName));
    }

    private ItemPanel associatedColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String associated = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(associated));
    }

}
