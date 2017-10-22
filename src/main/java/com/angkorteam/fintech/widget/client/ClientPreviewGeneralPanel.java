package com.angkorteam.fintech.widget.client;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Application;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.builder.client.client.ClientUnassignStaffBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.client.ClientAcceptTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientAssignStaffPage;
import com.angkorteam.fintech.pages.client.client.ClientDefaultSavingAccountPage;
import com.angkorteam.fintech.pages.client.client.ClientRejectTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientUndoTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientWebcamPage;
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
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientPreviewGeneralPanel extends Panel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPreviewGeneralPanel.class);

    protected Page itemPage;

    protected WebMarkupContainer buttonGroups;

    protected BookmarkablePageLink<Void> newSavingLink;
    protected BookmarkablePageLink<Void> assignStaffLink;
    protected AjaxLink<Void> unassignStaffLink;
    protected BookmarkablePageLink<Void> transferClientLink;
    protected BookmarkablePageLink<Void> editLink;
    protected BookmarkablePageLink<Void> newLoanLink;
    protected BookmarkablePageLink<Void> newShareLink;
    protected BookmarkablePageLink<Void> newChargeLink;
    protected BookmarkablePageLink<Void> closeLink;
    protected BookmarkablePageLink<Void> defaultSavingLink;
    protected BookmarkablePageLink<Void> clientScreenReportLink;
    protected BookmarkablePageLink<Void> clientUploadSignatureLink;
    protected BookmarkablePageLink<Void> viewStandingInstructionLink;
    protected BookmarkablePageLink<Void> createStandingInstructionLink;
    protected BookmarkablePageLink<Void> newFixedLink;
    protected BookmarkablePageLink<Void> newRecurringLink;
    protected BookmarkablePageLink<Void> chargeOverviewLink;
    protected BookmarkablePageLink<Void> surveyLink;
    protected BookmarkablePageLink<Void> acceptTransferLink;
    protected BookmarkablePageLink<Void> rejectTransferLink;
    protected BookmarkablePageLink<Void> undoTransferLink;

    protected BookmarkablePageLink<Void> takePictureLink;

    protected String clientId;

    protected DataTable<Map<String, Object>, String> savingAccountTable;
    protected JdbcProvider savingAccountProvider;

    protected Label clientNameField;
    protected String clientNameValue;

    protected WebMarkupContainer clientImageField;
    protected String clientImageValue;

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

        this.transferClientLink = new BookmarkablePageLink<>("transferClientLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.transferClientLink);

        this.unassignStaffLink = new AjaxLink<>("unassignStaffLink");
        this.unassignStaffLink.setOnClick(this::unassignStaffLinkClick);
        this.buttonGroups.add(this.unassignStaffLink);

        this.editLink = new BookmarkablePageLink<Void>("editLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.editLink);

        this.newLoanLink = new BookmarkablePageLink<Void>("newLoanLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newLoanLink);

        this.newShareLink = new BookmarkablePageLink<Void>("newShareLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newShareLink);

        this.newChargeLink = new BookmarkablePageLink<Void>("newChargeLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newChargeLink);

        this.closeLink = new BookmarkablePageLink<Void>("closeLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.closeLink);

        this.defaultSavingLink = new BookmarkablePageLink<Void>("defaultSavingLink", ClientDefaultSavingAccountPage.class, parameters);
        this.buttonGroups.add(this.defaultSavingLink);

        this.clientScreenReportLink = new BookmarkablePageLink<Void>("clientScreenReportLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.clientScreenReportLink);

        this.clientUploadSignatureLink = new BookmarkablePageLink<Void>("clientUploadSignatureLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.clientUploadSignatureLink);

        this.viewStandingInstructionLink = new BookmarkablePageLink<Void>("viewStandingInstructionLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.viewStandingInstructionLink);

        this.createStandingInstructionLink = new BookmarkablePageLink<Void>("createStandingInstructionLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.createStandingInstructionLink);

        this.newFixedLink = new BookmarkablePageLink<Void>("newFixedLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newFixedLink);

        this.newRecurringLink = new BookmarkablePageLink<Void>("newRecurringLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newRecurringLink);

        this.chargeOverviewLink = new BookmarkablePageLink<Void>("chargeOverviewLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.chargeOverviewLink);

        this.surveyLink = new BookmarkablePageLink<Void>("surveyLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.surveyLink);

        this.acceptTransferLink = new BookmarkablePageLink<Void>("acceptTransferLink", ClientAcceptTransferPage.class, parameters);
        this.buttonGroups.add(this.acceptTransferLink);

        this.rejectTransferLink = new BookmarkablePageLink<Void>("rejectTransferLink", ClientRejectTransferPage.class, parameters);
        this.buttonGroups.add(this.rejectTransferLink);

        this.undoTransferLink = new BookmarkablePageLink<Void>("undoTransferLink", ClientUndoTransferPage.class, parameters);
        this.buttonGroups.add(this.undoTransferLink);

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

        this.clientNameField = new Label("clientNameField", new PropertyModel<>(this, "clientNameValue"));
        add(this.clientNameField);

        this.clientImageField = new WebMarkupContainer("clientImageField");
        this.clientImageField.setOutputMarkupId(true);
        this.clientImageField.add(AttributeModifier.replace("src", new PropertyModel<>(this, "clientImageValue")));
        add(this.clientImageField);

        this.takePictureLink = new BookmarkablePageLink<Void>("takePictureLink", ClientWebcamPage.class, parameters);
        add(this.takePictureLink);

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
        Integer statusEnum = (Integer) clientObject.get("status_enum");

        // status enum
        // 300 : active
        // 303 : active but transferring office
        // 304 : active but rejecting transferring office
        if (statusEnum == 300) {
            this.transferClientLink.setVisible(true);
            this.newSavingLink.setVisible(true);
            this.editLink.setVisible(true);
            this.newLoanLink.setVisible(true);
            this.newShareLink.setVisible(true);
            this.newChargeLink.setVisible(true);
            this.closeLink.setVisible(true);
            this.defaultSavingLink.setVisible(true);
            this.viewStandingInstructionLink.setVisible(true);
            this.createStandingInstructionLink.setVisible(true);
            this.newFixedLink.setVisible(true);
            this.newRecurringLink.setVisible(true);
            this.acceptTransferLink.setVisible(false);
            this.rejectTransferLink.setVisible(false);
            this.undoTransferLink.setVisible(false);
        } else if (statusEnum == 303) {
            this.transferClientLink.setVisible(false);
            this.newSavingLink.setVisible(false);
            this.editLink.setVisible(false);
            this.newLoanLink.setVisible(false);
            this.newShareLink.setVisible(false);
            this.newChargeLink.setVisible(false);
            this.closeLink.setVisible(false);
            this.defaultSavingLink.setVisible(false);
            this.viewStandingInstructionLink.setVisible(false);
            this.createStandingInstructionLink.setVisible(false);
            this.newFixedLink.setVisible(false);
            this.newRecurringLink.setVisible(false);
            this.acceptTransferLink.setVisible(true);
            this.rejectTransferLink.setVisible(true);
            this.undoTransferLink.setVisible(true);
        } else if (statusEnum == 304) {
            this.transferClientLink.setVisible(false);
            this.newSavingLink.setVisible(false);
            this.editLink.setVisible(false);
            this.newLoanLink.setVisible(false);
            this.newShareLink.setVisible(false);
            this.newChargeLink.setVisible(false);
            this.closeLink.setVisible(false);
            this.defaultSavingLink.setVisible(false);
            this.viewStandingInstructionLink.setVisible(false);
            this.createStandingInstructionLink.setVisible(false);
            this.newFixedLink.setVisible(false);
            this.newRecurringLink.setVisible(false);
            this.acceptTransferLink.setVisible(false);
            this.rejectTransferLink.setVisible(false);
            this.undoTransferLink.setVisible(true);
        }

        this.chargeOverviewLink.setVisible(true);
        this.surveyLink.setVisible(true);
        this.clientUploadSignatureLink.setVisible(true);
        this.clientScreenReportLink.setVisible(true);
        this.assignStaffLink.setVisible(clientObject.get("staff_id") == null);
        this.unassignStaffLink.setVisible(clientObject.get("staff_id") != null);

    }

    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.clientNameValue = (String) clientObject.get("display_name");
        if (clientObject.get("image_id") != null) {
            try {
                HttpResponse<InputStream> response = ClientHelper.retrieveClientImage((Session) getSession(), this.clientId);
                this.clientImageValue = IOUtils.toString(response.getBody(), "UTF-8");
                IOUtils.closeQuietly(response.getBody());
            } catch (UnirestException | IOException e) {
                LOGGER.info(e.getMessage(), e);
            }
        } else {
            this.clientImageValue = RequestCycle.get().urlFor(new PackageResourceReference(Application.class, "resource/client-image-placeholder.png"), null).toString();
        }
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
