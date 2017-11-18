package com.angkorteam.fintech.widget.client;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.pages.client.client.ClientDocumentUploadPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class ClientPreviewDocumentPanel extends Panel {

    protected Page itemPage;
    protected String clientId;

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> uploadLink;

    public ClientPreviewDocumentPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
    }

    @Override
    protected void initComponent() {
        this.dataProvider = new JdbcProvider("m_document");
        this.dataProvider.boardField("description", "description", String.class);
        this.dataProvider.boardField("name", "name", String.class);
        this.dataProvider.boardField("file_name", "filename", String.class);
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.applyWhere("parent_entity_type", "parent_entity_type = 'clients'");
        this.dataProvider.applyWhere("parent_entity_id", "parent_entity_id = " + this.clientId);
        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "description", "description", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("File Name"), "filename", "filename", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.uploadLink = new BookmarkablePageLink<>("uploadLink", ClientDocumentUploadPage.class, parameters);
        add(this.uploadLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "description".equals(column) || "filename".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
