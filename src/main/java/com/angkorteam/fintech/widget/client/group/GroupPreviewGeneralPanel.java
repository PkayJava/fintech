package com.angkorteam.fintech.widget.client.group;

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

import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.pages.client.common.SavingAccountSelectionPage;
import com.angkorteam.fintech.provider.JdbcProvider;
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

public class GroupPreviewGeneralPanel extends Panel {

    protected String groupId;

    protected Page itemPage;

    protected BookmarkablePageLink<Void> groupSavingApplicationLink;

    protected DataTable<Map<String, Object>, String> savingAccountTable;
    protected JdbcProvider savingAccountProvider;
    protected List<IColumn<Map<String, Object>, String>> savingAccountColumn;

    public GroupPreviewGeneralPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.groupId = new PropertyModel<String>(this.itemPage, "groupId").getObject();
    }

    @Override
    protected void initComponent() {

        PageParameters savingParameters = new PageParameters();
        savingParameters.add("groupId", this.groupId);
        savingParameters.add("client", ClientEnum.Group.name());

        this.groupSavingApplicationLink = new BookmarkablePageLink<>("groupSavingApplicationLink", SavingAccountSelectionPage.class, savingParameters);
        add(this.groupSavingApplicationLink);

        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);

        this.savingAccountProvider = new JdbcProvider(MSavingsAccount.NAME);
        this.savingAccountProvider.applyJoin("m_savings_product", "LEFT JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        this.savingAccountProvider.boardField("CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID + ",'')", "id", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "account", String.class);
        this.savingAccountProvider.boardField(MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME, "product", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.STATUS_ENUM, "status", String.class);
        this.savingAccountProvider.boardField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_BALANCE_DERIVED, "balance", Double.class);
        this.savingAccountProvider.applyWhere("group_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.GROUP_ID + " = " + this.groupId);

        this.savingAccountProvider.selectField("id", String.class);
        this.savingAccountProvider.selectField("status", String.class);

        this.savingAccountColumn = Lists.newArrayList();
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::savingAccountColumn));
        this.savingAccountColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::savingAccountAction, this::savingAccountClick));

        this.savingAccountTable = new DefaultDataTable<>("savingAccountTable", this.savingAccountColumn, this.savingAccountProvider, 20);
        add(this.savingAccountTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected List<ActionItem> savingAccountAction(String s, Map<String, Object> model) {
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

    protected void savingAccountClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("Approve".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", savingId);
            // setResponsePage(SavingAccountApprovePage.class, parameters);
        } else if ("Undo Approve".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", savingId);
            // setResponsePage(SavingAccountUndoApprovePage.class, parameters);
        } else if ("Activate".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", savingId);
            // setResponsePage(SavingAccountActivatePage.class, parameters);
        } else if ("Deposit".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", savingId);
            // setResponsePage(SavingAccountDepositPage.class, parameters);
        } else if ("Withdraw".equals(column)) {
            String savingId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", savingId);
            // setResponsePage(SavingAccountWithdrawPage.class, parameters);
        }
    }

    protected ItemPanel savingAccountColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("account".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            parameters.add("savingId", model.get("id"));
            // return new LinkCell(SavingAccountPreviewPage.class, parameters, value);
            return new TextCell(value);
        } else if ("product".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("balance".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
