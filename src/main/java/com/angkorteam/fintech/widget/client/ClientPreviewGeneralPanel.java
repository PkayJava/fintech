package com.angkorteam.fintech.widget.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.builder.client.client.ClientUnassignStaffBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.client.ClientAssignStaffPage;
import com.angkorteam.fintech.pages.client.client.SavingAccountActivatePage;
import com.angkorteam.fintech.pages.client.client.SavingAccountApprovePage;
import com.angkorteam.fintech.pages.client.client.SavingAccountDepositPage;
import com.angkorteam.fintech.pages.client.client.SavingAccountPreviewPage;
import com.angkorteam.fintech.pages.client.client.SavingAccountSelectionPage;
import com.angkorteam.fintech.pages.client.client.SavingAccountUndoApprovePage;
import com.angkorteam.fintech.pages.client.client.SavingAccountWithdrawPage;
import com.angkorteam.fintech.popup.client.client.ClientUnassignStaffPopup;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientPreviewGeneralPanel extends Panel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPreviewGeneralPanel.class);

    protected Page itemPage;

    protected WebMarkupContainer buttonGroups;

    protected BookmarkablePageLink<Void> newSavingLink;

    protected BookmarkablePageLink<Void> assignStaffLink;

    protected AjaxLink<Void> unassignStaffLink;

    protected String clientId;

    protected DataTable<Map<String, Object>, String> savingAccountTable;
    protected JdbcProvider savingAccountProvider;

    protected ModalWindow clientUnassignStaffPopup;

    public ClientPreviewGeneralPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.clientUnassignStaffPopup = new ModalWindow("clientUnassignStaffPopup");
        this.clientUnassignStaffPopup.setOnClose(this::clientUnassignStaffPopupOnClose);
        add(this.clientUnassignStaffPopup);

        this.buttonGroups = new WebMarkupContainer("buttonGroups");
        this.buttonGroups.setOutputMarkupId(true);
        add(this.buttonGroups);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.newSavingLink = new BookmarkablePageLink<>("newSavingLink", SavingAccountSelectionPage.class, parameters);
        this.buttonGroups.add(this.newSavingLink);

        this.assignStaffLink = new BookmarkablePageLink<>("assignStaffLink", ClientAssignStaffPage.class, parameters);
        this.buttonGroups.add(this.assignStaffLink);

        this.unassignStaffLink = new AjaxLink<>("unassignStaffLink");
        this.unassignStaffLink.setOnClick(this::unassignStaffLinkClick);
        this.buttonGroups.add(this.unassignStaffLink);

        this.savingAccountProvider = new JdbcProvider("m_savings_account");
        this.savingAccountProvider.addJoin("LEFT JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.savingAccountProvider.boardField("concat(m_savings_account.id,'')", "id", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_no", "account", String.class);
        this.savingAccountProvider.boardField("m_savings_product.name", "product", String.class);
        this.savingAccountProvider.boardField("m_savings_account.status_enum", "status", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_balance_derived", "balance", Double.class);
        this.savingAccountProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);

        this.savingAccountProvider.selectField("id", String.class);
        this.savingAccountProvider.selectField("status", String.class);

        List<IColumn<Map<String, Object>, String>> savingAccountColumns = Lists.newArrayList();
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::savingAccountAccountColumn));
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::savingAccountProductColumn));
        savingAccountColumns.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::savingAccountBalanceColumn));
        savingAccountColumns.add(new ActionFilterColumn<>(Model.of("Action"), this::savingAccountActionItem, this::savingAccountActionClick));

        this.savingAccountTable = new DefaultDataTable<>("savingAccountTable", savingAccountColumns, this.savingAccountProvider, 20);
        add(this.savingAccountTable);

        initDefault();
    }

    protected void clientUnassignStaffPopupOnClose(String elementId, AjaxRequestTarget target) {
        if ("confirmButton".equals(elementId)) {
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            String staffId = jdbcTemplate.queryForObject("select staff_id from m_client where id = ?", String.class, this.clientId);
            ClientUnassignStaffBuilder builder = new ClientUnassignStaffBuilder();
            builder.withId(this.clientId);
            builder.withStaffId(staffId);
            try {
                ClientHelper.unassignStaffClient((Session) getSession(), builder.build());
            } catch (UnirestException e) {
                LOGGER.info(e.getMessage(), e);
            }
            jdbcTemplate.execute("commit");
            Map<String, Object> clientObject = jdbcTemplate.queryForMap("select staff_id from m_client where id = ?", this.clientId);
            this.assignStaffLink.setVisible(clientObject.get("staff_id") == null);
            this.unassignStaffLink.setVisible(clientObject.get("staff_id") != null);
            target.add(this.buttonGroups);
        }
    }

    protected boolean unassignStaffLinkClick(AjaxLink<Void> ajaxLink, AjaxRequestTarget target) {
        this.clientUnassignStaffPopup.setContent(new ClientUnassignStaffPopup(this.clientUnassignStaffPopup.getContentId(), this.clientUnassignStaffPopup, this));
        this.clientUnassignStaffPopup.show(target);
        return false;
    }

    protected void initDefault() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.assignStaffLink.setVisible(clientObject.get("staff_id") == null);
    }

    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
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
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountApprovePage.class, parameters);
        } else if ("Undo Approve".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountUndoApprovePage.class, parameters);
        } else if ("Activate".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountActivatePage.class, parameters);
        } else if ("Deposit".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountDepositPage.class, parameters);
        } else if ("Withdraw".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountWithdrawPage.class, parameters);
        }
    }

    protected ItemPanel savingAccountAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
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

}
