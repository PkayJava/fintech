package com.angkorteam.fintech.widget.center;

import java.math.BigDecimal;
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
import com.angkorteam.fintech.pages.client.center.CenterProductPage;
import com.angkorteam.fintech.pages.client.center.GroupCreatePage;
import com.angkorteam.fintech.pages.client.center.GroupManagePage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class CenterGeneralPanel extends Panel {

    protected String centerId;

    protected Page itemPage;

    protected DataTable<Map<String, Object>, String> groupTable;
    protected JdbcProvider groupProvider;

    protected DataTable<Map<String, Object>, String> accountTable;
    protected JdbcProvider accountProvider;

    protected BookmarkablePageLink<Void> editLink;

    protected BookmarkablePageLink<Void> addGroupLink;

    protected BookmarkablePageLink<Void> manageGroupLink;

    protected BookmarkablePageLink<Void> centerSavingApplicationLink;

    public CenterGeneralPanel(String id, Page itemPage) {
        super(id);

        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.centerId = new PropertyModel<String>(this.itemPage, "centerId").getObject();

        this.groupProvider = new JdbcProvider("m_group");
        this.groupProvider.addJoin("LEFT JOIN r_enum_value ON m_group.status_enum = r_enum_value.enum_id AND r_enum_value.enum_name = 'status_enum'");
        this.groupProvider.boardField("m_group.id", "id", Long.class);
        this.groupProvider.boardField("m_group.display_name", "displayName", String.class);
        this.groupProvider.boardField("m_group.account_no", "account", String.class);
        this.groupProvider.boardField("r_enum_value.enum_value", "status", String.class);
        this.groupProvider.applyWhere("level_id", "m_group.level_id = 2");
        this.groupProvider.applyWhere("parent_id", "m_group.parent_id = " + this.centerId);

        this.groupProvider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> groupColumns = Lists.newArrayList();
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Name"), "displayName", "displayName", this::displayNameGroupColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::accountGroupColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::statusGroupColumn));

        this.groupTable = new DefaultDataTable<>("groupTable", groupColumns, this.groupProvider, 20);
        add(this.groupTable);

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.editLink = new BookmarkablePageLink<>("editLink", CenterModifyPage.class, parameters);
        add(this.editLink);

        this.addGroupLink = new BookmarkablePageLink<>("addGroupLink", GroupCreatePage.class, parameters);
        add(this.addGroupLink);

        this.manageGroupLink = new BookmarkablePageLink<>("manageGroupLink", GroupManagePage.class, parameters);
        add(this.manageGroupLink);

        this.centerSavingApplicationLink = new BookmarkablePageLink<>("centerSavingApplicationLink", CenterProductPage.class, parameters);
        add(this.centerSavingApplicationLink);

        this.accountProvider = new JdbcProvider("m_savings_account");
        this.accountProvider.addJoin("LEFT JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.accountProvider.boardField("m_savings_account.id", "id", Long.class);
        this.accountProvider.boardField("m_savings_account.account_no", "account", String.class);
        this.accountProvider.boardField("m_savings_product.name", "product", String.class);
        this.accountProvider.boardField("m_savings_account.account_balance_derived", "balance", Double.class);
        this.accountProvider.applyWhere("group_id", "m_savings_account.group_id = " + this.centerId);

        this.accountProvider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> accountColumns = Lists.newArrayList();
        accountColumns.add(new TextFilterColumn(this.accountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::accountAccountColumn));
        accountColumns.add(new TextFilterColumn(this.accountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::productAccountColumn));
        accountColumns.add(new TextFilterColumn(this.accountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::balanceAccountColumn));

        this.accountTable = new DefaultDataTable<>("accountTable", accountColumns, this.accountProvider, 20);
        add(this.accountTable);

    }

    protected ItemPanel accountAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel balanceAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel productAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
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
