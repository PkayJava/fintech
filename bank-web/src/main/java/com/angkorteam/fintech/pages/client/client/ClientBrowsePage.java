//package com.angkorteam.fintech.pages.client.client;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IDataExporter;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.markup.repeater.data.IDataProvider;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.constant.StatusEnum;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.table.LinkCell;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.google.common.collect.Lists;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClientBrowsePage extends Page {
//
//    protected FilterForm<Map<String, String>> form;
//
//    protected UIRow row1;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    protected BookmarkablePageLink<Void> createLink;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        List<String> status = Lists.newArrayList();
//        for (StatusEnum t : StatusEnum.values()) {
//            status.add("WHEN " + t.getLiteral() + " THEN '" + t.getDescription() + "'");
//        }
//        status.add("ELSE '" + StatusEnum.Invalid.getDescription() + "'");
//
//        this.dataProvider = new JdbcProvider(MClient.NAME);
//        this.dataProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + MClient.NAME + "." + MClient.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
//        this.dataProvider.applyJoin("m_staff", "LEFT JOIN " + MStaff.NAME + " ON " + MClient.NAME + "." + MClient.Field.STAFF_ID + " = " + MStaff.NAME + "." + MStaff.Field.ID);
//        this.dataProvider.boardField(MClient.NAME + "." + MClient.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MClient.NAME + "." + MClient.Field.ACCOUNT_NO, "account", String.class);
//        this.dataProvider.boardField(MClient.NAME + "." + MClient.Field.DISPLAY_NAME, "name", String.class);
//        this.dataProvider.boardField(MClient.NAME + "." + MClient.Field.EXTERNAL_ID, "externalId", String.class);
//        this.dataProvider.boardField(MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME, "staff", String.class);
//        this.dataProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
//        this.dataProvider.boardField("CASE " + MClient.NAME + "." + MClient.Field.STATUS_ENUM + " " + StringUtils.join(status, " ") + " END", "status", String.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account #"), "account", "account", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("External ID"), "externalId", "externalId", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Status"), "status", "status", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Staff"), "staff", "staff", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new FilterForm<>("form", this.dataProvider);
//        add(this.form);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.dataBlock = this.row1.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form));
//        this.dataTable.addBottomToolbar(new ExportToolbar(this.dataTable).addDataExporter(new CSVDataExporter()));
//        this.dataIContainer.add(this.dataTable);
//
//        this.createLink = new BookmarkablePageLink<>("createLink", ClientCreatePage.class);
//        add(this.createLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column)) {
//            String value = (String) model.get(column);
//            PageParameters parameters = new PageParameters();
//            parameters.add("clientId", model.get("id"));
//            return new LinkCell(ClientPreviewPage.class, parameters, value);
//        } else if ("account".equals(column) || "externalId".equals(column) || "status".equals(column) || "office".equals(column) || "staff".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
