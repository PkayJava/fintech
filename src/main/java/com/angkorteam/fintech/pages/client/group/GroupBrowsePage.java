package com.angkorteam.fintech.pages.client.group;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.Function;
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
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupBrowsePage extends Page {

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Groups");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.dataProvider = new JdbcProvider(MGroup.NAME);
        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + MGroup.NAME + "." + MGroup.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
        this.dataProvider.applyJoin("r_enum_value", "LEFT JOIN " + REnumValue.NAME + " ON " + MGroup.NAME + "." + MGroup.Field.STATUS_ENUM + " = " + REnumValue.NAME + "." + REnumValue.Field.ENUM_ID + " AND " + REnumValue.NAME + "." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
        this.dataProvider.boardField(MGroup.NAME + "." + MGroup.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MGroup.NAME + "." + MGroup.Field.ACCOUNT_NO, "account", String.class);
        this.dataProvider.boardField(MGroup.NAME + "." + MGroup.Field.DISPLAY_NAME, "name", String.class);
        this.dataProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
        this.dataProvider.boardField(MGroup.NAME + "." + MGroup.Field.EXTERNAL_ID, "externalId", String.class);
        this.dataProvider.boardField(REnumValue.NAME + "." + REnumValue.Field.ENUM_VALUE, "status", String.class);

        this.dataProvider.applyWhere("level_id", MGroup.NAME + "." + MGroup.Field.LEVEL_ID + " = 2");

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account #"), "account", "account", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("External ID"), "externalId", "externalId", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Status"), "status", "status", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", GroupCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", model.get("id"));
            return new LinkCell(GroupPreviewPage.class, parameters, value);
        } else if ("account".equals(column) || "externalId".equals(column) || "status".equals(column) || "office".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
