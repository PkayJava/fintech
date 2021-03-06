package com.angkorteam.fintech.pages.office;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeBrowsePage extends Page {

    protected FilterForm<Map<String, String>> form;

    protected UIRow row1;

    protected UIBlock dataBlock;
    protected UIContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

    protected BookmarkablePageLink<Void> hierarchyLink;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
            breadcrumb.setLabel("Office");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.dataProvider = new JdbcProvider(MOffice.NAME + " office");
        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " parent ON office." + MOffice.Field.PARENT_ID + " = parent." + MOffice.Field.ID);
        this.dataProvider.boardField("office." + MOffice.Field.ID, "id", Long.class);
        this.dataProvider.boardField("office." + MOffice.Field.EXTERNAL_ID, "external_id", String.class);
        this.dataProvider.boardField("office." + MOffice.Field.PARENT_ID, "parent_id", Long.class);
        this.dataProvider.boardField("office." + MOffice.Field.NAME, "name", String.class);
        this.dataProvider.boardField("parent." + MOffice.Field.NAME, "parent_name", String.class);
        this.dataProvider.boardField("office." + MOffice.Field.OPENING_DATE, "opening_date", Calendar.Date);
        this.dataProvider.selectField("parent_id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("External ID"), "external_id", "external_id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Parent Name"), "parent_name", "parent_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Opening Date"), "opening_date", "opening_date", this::dataColumn));

    }

    @Override
    protected void initComponent() {

        this.hierarchyLink = new BookmarkablePageLink<>("hierarchyLink", OfficeHierarchyPage.class);
        add(this.hierarchyLink);

        this.createLink = new BookmarkablePageLink<>("createLink", OfficeCreatePage.class);
        add(this.createLink);

        this.form = new FilterForm<>("form", this.dataProvider);
        add(this.form);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.dataBlock = this.row1.newUIBlock("dataBlock", Size.Twelve_12);
        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form));
        this.dataIContainer.add(this.dataTable);
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("external_id".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("name".equals(column)) {
            String name = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("officeId", model.get("id"));
            return new LinkCell(OfficeModifyPage.class, parameters, name);
        } else if ("parent_name".equals(column)) {
            String value = (String) model.get(column);
            if (Strings.isNullOrEmpty(value)) {
                return new TextCell(value);
            } else {
                PageParameters parameters = new PageParameters();
                parameters.add("officeId", model.get("parent_id"));
                return new LinkCell(OfficeModifyPage.class, parameters, value);
            }
        } else if ("opening_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
