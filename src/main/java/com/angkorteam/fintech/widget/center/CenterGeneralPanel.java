package com.angkorteam.fintech.widget.center;

import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.client.center.CenterModifyPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class CenterGeneralPanel extends Panel {

    private Page itemPage;

    private DataTable<Map<String, Object>, String> groupTable;
    private JdbcProvider groupProvider;

    private PropertyModel<String> centerId;

    private BookmarkablePageLink<Void> editLink;

    public CenterGeneralPanel(String id, Page itemPage) {
        super(id);

        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.centerId = new PropertyModel<>(this.itemPage, "centerId");

        this.groupProvider = new JdbcProvider("m_group");
        this.groupProvider.addJoin("LEFT JOIN r_enum_value ON m_group.status_enum = r_enum_value.enum_id AND r_enum_value.enum_name = 'status_enum'");
        this.groupProvider.boardField("m_group.id", "id", Long.class);
        this.groupProvider.boardField("m_group.display_name", "displayName", String.class);
        this.groupProvider.boardField("m_group.account_no", "account", String.class);
        this.groupProvider.boardField("r_enum_value.enum_value", "status", String.class);
        this.groupProvider.applyWhere("level_id", "m_group.level_id = 2");
        this.groupProvider.applyWhere("parent_id", "m_group.parent_id = " + this.centerId.getObject());

        this.groupProvider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> groupColumns = Lists.newArrayList();
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Name"), "displayName", "displayName", this::displayNameGroupColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::accountGroupColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::statusGroupColumn));

        this.groupTable = new DefaultDataTable<>("groupTable", groupColumns, this.groupProvider, 20);
        add(this.groupTable);

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId.getObject());
        this.editLink = new BookmarkablePageLink<>("editLink", CenterModifyPage.class, parameters);
        add(editLink);

    }

    protected ItemPanel displayNameGroupColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel accountGroupColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel statusGroupColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
