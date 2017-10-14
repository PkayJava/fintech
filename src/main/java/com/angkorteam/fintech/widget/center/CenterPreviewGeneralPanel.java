package com.angkorteam.fintech.widget.center;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.client.center.CenterClosePage;
import com.angkorteam.fintech.pages.client.center.CenterModifyPage;
import com.angkorteam.fintech.pages.client.center.CenterSavingAccountPage;
import com.angkorteam.fintech.pages.client.center.GroupCreatePage;
import com.angkorteam.fintech.pages.client.center.GroupManagePage;
import com.angkorteam.fintech.pages.client.center.SavingAccountActivatePage;
import com.angkorteam.fintech.pages.client.center.SavingAccountApprovePage;
import com.angkorteam.fintech.pages.client.center.SavingAccountDepositPage;
import com.angkorteam.fintech.pages.client.center.SavingAccountPreviewPage;
import com.angkorteam.fintech.pages.client.center.SavingAccountUndoApprovePage;
import com.angkorteam.fintech.pages.client.center.SavingAccountWithdrawPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class CenterPreviewGeneralPanel extends Panel {

    protected String centerId;

    protected Page itemPage;

    protected DataTable<Map<String, Object>, String> groupTable;
    protected JdbcProvider groupProvider;

    protected DataTable<Map<String, Object>, String> savingAccountTable;
    protected JdbcProvider savingAccountProvider;

    protected BookmarkablePageLink<Void> editLink;

    protected BookmarkablePageLink<Void> addGroupLink;

    protected BookmarkablePageLink<Void> manageGroupLink;

    protected BookmarkablePageLink<Void> centerSavingApplicationLink;

    protected BookmarkablePageLink<Void> closeLink;

    public CenterPreviewGeneralPanel(String id, Page itemPage) {
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
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Name"), "displayName", "displayName", this::groupDisplayNameColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::groupAccountColumn));
        groupColumns.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::groupStatusColumn));

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

        this.centerSavingApplicationLink = new BookmarkablePageLink<>("centerSavingApplicationLink", CenterSavingAccountPage.class, parameters);
        add(this.centerSavingApplicationLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterClosePage.class, parameters);
        add(this.closeLink);

        this.savingAccountProvider = new JdbcProvider("m_savings_account");
        this.savingAccountProvider.addJoin("LEFT JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.savingAccountProvider.boardField("concat(m_savings_account.id,'')", "id", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_no", "account", String.class);
        this.savingAccountProvider.boardField("m_savings_product.name", "product", String.class);
        this.savingAccountProvider.boardField("m_savings_account.status_enum", "status", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_balance_derived", "balance", Double.class);
        this.savingAccountProvider.applyWhere("group_id", "m_savings_account.group_id = " + this.centerId);

        this.savingAccountProvider.selectField("id", String.class);
        this.savingAccountProvider.selectField("status", String.class);

        List<IColumn<Map<String, Object>, String>> savingAccountColumns = Lists.newArrayList();
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::savingAccountAccountColumn));
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::savingAccountProductColumn));
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::savingAccountBalanceColumn));
        savingAccountColumns.add(new ActionFilterColumn<>(Model.of("Action"), this::savingAccountActionItem, this::savingAccountActionClick));

        this.savingAccountTable = new DefaultDataTable<>("savingAccountTable", savingAccountColumns, this.savingAccountProvider, 20);
        add(this.savingAccountTable);

    }

    protected List<ActionItem> savingAccountActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Integer status = (Integer) model.get("status");
        if (100 == status) {
            actions.add(new ActionItem("Approve", Model.of("Approve"), ItemCss.PRIMARY));
        } else if (200 == status) {
            actions.add(new ActionItem("Undo Approve", Model.of("Undo Approve"), ItemCss.PRIMARY));
            actions.add(new ActionItem("Activate", Model.of("Activate"), ItemCss.PRIMARY));
        } else if (300 == status) {
            actions.add(new ActionItem("Deposit", Model.of("Deposit"), ItemCss.PRIMARY));
            actions.add(new ActionItem("Withdraw", Model.of("Withdraw"), ItemCss.PRIMARY));
        }
        return actions;
    }

    protected void savingAccountActionClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("Approve".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountApprovePage.class, parameters);
        } else if ("Undo Approve".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountUndoApprovePage.class, parameters);
        } else if ("Activate".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountActivatePage.class, parameters);
        } else if ("Deposit".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountDepositPage.class, parameters);
        } else if ("Withdraw".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountWithdrawPage.class, parameters);
        }
    }

    protected ItemPanel savingAccountAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        parameters.add("accountId", model.get("id"));
        return new LinkCell(SavingAccountPreviewPage.class, parameters, value);
    }

    protected ItemPanel savingAccountBalanceColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###,##0.00");
    }

    protected ItemPanel savingAccountProductColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel groupDisplayNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel groupAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel groupStatusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
