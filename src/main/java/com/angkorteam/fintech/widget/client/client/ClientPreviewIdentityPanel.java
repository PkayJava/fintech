package com.angkorteam.fintech.widget.client.client;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.ddl.MClientIdentifier;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.pages.client.client.ClientIdentityCreatePage;
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

public class ClientPreviewIdentityPanel extends Panel {

    public static final String ACTIVE_CODE = "200";
    public static final String INACTIVE_CODE = "100";

    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";

    protected Page itemPage;

    protected String clientId;

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

    public ClientPreviewIdentityPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initComponent() {
        this.dataProvider = new JdbcProvider(MClientIdentifier.NAME);
        this.dataProvider.applyJoin("m_code_value", "LEFT JOIN " + MCodeValue.NAME + " document_type ON " + MClientIdentifier.NAME + "." + MClientIdentifier.Field.DOCUMENT_TYPE_ID + " = document_type." + MCodeValue.Field.ID);
        this.dataProvider.boardField(MClientIdentifier.NAME + "." + MClientIdentifier.Field.DESCRIPTION, "description", String.class);
        this.dataProvider.boardField("document_type." + MCodeValue.Field.CODE_VALUE, "type", String.class);
        this.dataProvider.boardField(MClientIdentifier.NAME + "." + MClientIdentifier.Field.DOCUMENT_KEY, "documentKey", String.class);
        this.dataProvider.boardField(MClientIdentifier.NAME + "." + MClientIdentifier.Field.ID, "id", Long.class);
        this.dataProvider.boardField("case " + MClientIdentifier.NAME + "." + MClientIdentifier.Field.STATUS + " WHEN 200 THEN 'Active' WHEN 100 THEN 'Inactive' ELSE CONCAT(" + MClientIdentifier.NAME + "." + MClientIdentifier.Field.STATUS + ",'') END", "status", String.class);
        this.dataProvider.applyWhere("client_id", MClientIdentifier.NAME + "." + MClientIdentifier.Field.CLIENT_ID + " = " + this.clientId);
        this.dataProvider.setSort("documentKey", SortOrder.DESCENDING);
        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("ID"), "documentKey", "documentKey", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "description", "description", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Type"), "type", "type", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Status"), "status", "status", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.createLink = new BookmarkablePageLink<>("createLink", ClientIdentityCreatePage.class, parameters);
        add(this.createLink);
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("status".equals(column) || "type".equals(column) || "documentKey".equals(column) || "description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
