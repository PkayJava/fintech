package com.angkorteam.fintech.widget.client.center;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.pages.client.center.CenterClosePage;
import com.angkorteam.fintech.pages.client.center.CenterModifyPage;
import com.angkorteam.fintech.pages.client.center.GroupCreatePage;
import com.angkorteam.fintech.pages.client.center.GroupManagePage;
import com.angkorteam.fintech.pages.client.common.saving.AccountSelectionPage;
import com.angkorteam.fintech.pages.client.common.saving.AccountUndoApprovePage;
import com.angkorteam.fintech.pages.client.common.saving.AccountWithdrawPage;
import com.angkorteam.fintech.pages.client.common.saving.AccountActivatePage;
import com.angkorteam.fintech.pages.client.common.saving.AccountApprovePage;
import com.angkorteam.fintech.pages.client.common.saving.AccountDepositPage;
import com.angkorteam.fintech.pages.client.common.saving.AccountPreviewPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
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

    protected List<IColumn<Map<String, Object>, String>> groupColumn;
    protected DataTable<Map<String, Object>, String> groupTable;
    protected JdbcProvider groupProvider;

    protected DataTable<Map<String, Object>, String> savingAccountTable;
    protected JdbcProvider savingAccountProvider;
    protected List<IColumn<Map<String, Object>, String>> savingAccountColumn;

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
    protected void initData() {
        this.centerId = new PropertyModel<String>(this.itemPage, "centerId").getObject();
    }

    @Override
    protected void initComponent() {

        this.groupProvider = new JdbcProvider(MGroup.NAME);
        this.groupProvider.applyJoin("r_enum_value", "LEFT JOIN " + REnumValue.NAME + " ON " + MGroup.NAME + "." + MGroup.Field.STATUS_ENUM + " = " + REnumValue.NAME + "." + REnumValue.Field.ENUM_ID + " AND " + REnumValue.NAME + "." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
        this.groupProvider.boardField(MGroup.NAME + "." + MGroup.Field.ID, "id", Long.class);
        this.groupProvider.boardField(MGroup.NAME + "." + MGroup.Field.DISPLAY_NAME, "displayName", String.class);
        this.groupProvider.boardField(MGroup.NAME + "." + MGroup.Field.ACCOUNT_NO, "account", String.class);
        this.groupProvider.boardField(REnumValue.NAME + "." + REnumValue.Field.ENUM_VALUE, "status", String.class);

        this.groupProvider.applyWhere("level_id", MGroup.NAME + "." + MGroup.Field.LEVEL_ID + " = 2");
        this.groupProvider.applyWhere("parent_id", MGroup.NAME + "." + MGroup.Field.PARENT_ID + " = " + this.centerId);

        this.groupProvider.selectField("id", Long.class);

        this.groupColumn = Lists.newArrayList();
        this.groupColumn.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Name"), "displayName", "displayName", this::groupColumn));
        this.groupColumn.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::groupColumn));
        this.groupColumn.add(new TextFilterColumn(this.groupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::groupColumn));

        this.groupTable = new DefaultDataTable<>("groupTable", this.groupColumn, this.groupProvider, 20);
        add(this.groupTable);

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.editLink = new BookmarkablePageLink<>("editLink", CenterModifyPage.class, parameters);
        add(this.editLink);

        this.addGroupLink = new BookmarkablePageLink<>("addGroupLink", GroupCreatePage.class, parameters);
        add(this.addGroupLink);

        this.manageGroupLink = new BookmarkablePageLink<>("manageGroupLink", GroupManagePage.class, parameters);
        add(this.manageGroupLink);

        PageParameters savingParameters = new PageParameters();
        savingParameters.add("centerId", this.centerId);
        savingParameters.add("client", ClientEnum.Center.name());

        this.centerSavingApplicationLink = new BookmarkablePageLink<>("centerSavingApplicationLink", AccountSelectionPage.class, savingParameters);
        add(this.centerSavingApplicationLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterClosePage.class, parameters);
        add(this.closeLink);

        this.savingAccountProvider = new JdbcProvider(MSavingsAccount.NAME);
        this.savingAccountProvider.applyJoin("m_savings_product", "LEFT JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        this.savingAccountProvider.boardField("CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID + ",'')", "id", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "account", String.class);
        this.savingAccountProvider.boardField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME, "product", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.STATUS_ENUM, "status", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_BALANCE_DERIVED, "balance", Double.class);
        this.savingAccountProvider.applyWhere("group_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.GROUP_ID + " = " + this.centerId);

        this.savingAccountProvider.selectField("id", String.class);
        this.savingAccountProvider.selectField("status", String.class);

        this.savingAccountColumn = Lists.newArrayList();
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::savingAccountColumn));
        this.savingAccountColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::savingAccountActionItem, this::savingAccountActionClick));

        this.savingAccountTable = new DefaultDataTable<>("savingAccountTable", this.savingAccountColumn, this.savingAccountProvider, 20);
        add(this.savingAccountTable);
    }

    @Override
    protected void configureMetaData() {
    }

    protected List<ActionItem> savingAccountActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Long status = (Long) model.get("status");
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
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", savingId);
            setResponsePage(AccountApprovePage.class, parameters);
        } else if ("Undo Approve".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", savingId);
            setResponsePage(AccountUndoApprovePage.class, parameters);
        } else if ("Activate".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", savingId);
            setResponsePage(AccountActivatePage.class, parameters);
        } else if ("Deposit".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", savingId);
            setResponsePage(AccountDepositPage.class, parameters);
        } else if ("Withdraw".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", savingId);
            setResponsePage(AccountWithdrawPage.class, parameters);
        }
    }

    protected ItemPanel savingAccountColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("account".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Center.name());
            parameters.add("centerId", this.centerId);
            parameters.add("savingId", model.get("id"));
            return new LinkCell(AccountPreviewPage.class, parameters, value);
        } else if ("product".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("balance".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected ItemPanel groupColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("displayName".equals(column) || "account".equals(column) || "status".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
