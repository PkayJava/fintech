package com.angkorteam.fintech.widget.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Application;
import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.builder.client.client.ClientChargeWaiveBuilder;
import com.angkorteam.fintech.dto.builder.client.client.ClientUnassignStaffBuilder;
import com.angkorteam.fintech.dto.constant.LoanTypeEnum;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.client.ChargeOverviewPage;
import com.angkorteam.fintech.pages.client.client.ChargePayPage;
import com.angkorteam.fintech.pages.client.client.ChargeSelectionPage;
import com.angkorteam.fintech.pages.client.client.ClientAcceptTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientAssignStaffPage;
import com.angkorteam.fintech.pages.client.client.ClientDefaultSavingAccountPage;
import com.angkorteam.fintech.pages.client.client.ClientRejectTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientSignatureUploadPage;
import com.angkorteam.fintech.pages.client.client.ClientStandingInstructionBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientStandingInstructionCreatePage;
import com.angkorteam.fintech.pages.client.client.ClientTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientUndoTransferPage;
import com.angkorteam.fintech.pages.client.client.ClientWebcamPage;
import com.angkorteam.fintech.pages.client.common.LoanAccountApprovePage;
import com.angkorteam.fintech.pages.client.common.LoanAccountDisbursePage;
import com.angkorteam.fintech.pages.client.common.LoanAccountRepaymentPage;
import com.angkorteam.fintech.pages.client.common.LoanAccountSelectionPage;
import com.angkorteam.fintech.pages.client.common.SavingAccountActivatePage;
import com.angkorteam.fintech.pages.client.common.SavingAccountApprovePage;
import com.angkorteam.fintech.pages.client.common.SavingAccountDepositPage;
import com.angkorteam.fintech.pages.client.common.SavingAccountPreviewPage;
import com.angkorteam.fintech.pages.client.common.SavingAccountSelectionPage;
import com.angkorteam.fintech.pages.client.common.SavingAccountUndoApprovePage;
import com.angkorteam.fintech.pages.client.common.SavingAccountWithdrawPage;
import com.angkorteam.fintech.popup.client.client.ClientUnassignStaffPopup;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientPreviewGeneralPanel extends Panel {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ClientPreviewGeneralPanel.class);

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
    protected List<IColumn<Map<String, Object>, String>> savingAccountColumn;

    protected DataTable<Map<String, Object>, String> upcomingChargeTable;
    protected JdbcProvider upcomingChargeProvider;
    protected List<IColumn<Map<String, Object>, String>> upcomingChargeColumn;

    protected DataTable<Map<String, Object>, String> loanAccountTable;
    protected JdbcProvider loanAccountProvider;
    protected List<IColumn<Map<String, Object>, String>> loanAccountColumn;

    protected Label clientNameView;
    protected String clientNameValue;

    protected WebMarkupContainer clientImageField;
    protected String clientImageValue;

    protected ModalWindow clientUnassignStaffPopup;

    public ClientPreviewGeneralPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initComponent() {
        this.clientUnassignStaffPopup = new ModalWindow("clientUnassignStaffPopup");
        this.clientUnassignStaffPopup.setOnClose(this::clientUnassignStaffPopupClose);
        add(this.clientUnassignStaffPopup);

        this.buttonGroups = new WebMarkupContainer("buttonGroups");
        this.buttonGroups.setOutputMarkupId(true);
        add(this.buttonGroups);

        PageParameters savingParameters = new PageParameters();
        savingParameters.add("clientId", this.clientId);
        savingParameters.add("client", ClientEnum.Client.name());

        this.newSavingLink = new BookmarkablePageLink<>("newSavingLink", SavingAccountSelectionPage.class, savingParameters);
        this.buttonGroups.add(this.newSavingLink);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        parameters.add("client", ClientEnum.Client.name());

        this.assignStaffLink = new BookmarkablePageLink<>("assignStaffLink", ClientAssignStaffPage.class, parameters);
        this.buttonGroups.add(this.assignStaffLink);

        this.transferClientLink = new BookmarkablePageLink<>("transferClientLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.transferClientLink);

        this.unassignStaffLink = new AjaxLink<>("unassignStaffLink");
        this.unassignStaffLink.setOnClick(this::unassignStaffLinkClick);
        this.buttonGroups.add(this.unassignStaffLink);

        this.editLink = new BookmarkablePageLink<Void>("editLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.editLink);

        this.newLoanLink = new BookmarkablePageLink<Void>("newLoanLink", LoanAccountSelectionPage.class, parameters);
        this.buttonGroups.add(this.newLoanLink);

        this.newShareLink = new BookmarkablePageLink<Void>("newShareLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newShareLink);

        this.newChargeLink = new BookmarkablePageLink<Void>("newChargeLink", ChargeSelectionPage.class, parameters);
        this.buttonGroups.add(this.newChargeLink);

        this.closeLink = new BookmarkablePageLink<Void>("closeLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.closeLink);

        this.defaultSavingLink = new BookmarkablePageLink<Void>("defaultSavingLink", ClientDefaultSavingAccountPage.class, parameters);
        this.buttonGroups.add(this.defaultSavingLink);

        this.clientScreenReportLink = new BookmarkablePageLink<Void>("clientScreenReportLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.clientScreenReportLink);

        this.clientUploadSignatureLink = new BookmarkablePageLink<Void>("clientUploadSignatureLink", ClientSignatureUploadPage.class, parameters);
        this.buttonGroups.add(this.clientUploadSignatureLink);

        this.viewStandingInstructionLink = new BookmarkablePageLink<Void>("viewStandingInstructionLink", ClientStandingInstructionBrowsePage.class, parameters);
        this.buttonGroups.add(this.viewStandingInstructionLink);

        this.createStandingInstructionLink = new BookmarkablePageLink<Void>("createStandingInstructionLink", ClientStandingInstructionCreatePage.class, parameters);
        this.buttonGroups.add(this.createStandingInstructionLink);

        this.newFixedLink = new BookmarkablePageLink<Void>("newFixedLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newFixedLink);

        this.newRecurringLink = new BookmarkablePageLink<Void>("newRecurringLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.newRecurringLink);

        this.chargeOverviewLink = new BookmarkablePageLink<Void>("chargeOverviewLink", ChargeOverviewPage.class, parameters);
        this.buttonGroups.add(this.chargeOverviewLink);

        this.surveyLink = new BookmarkablePageLink<Void>("surveyLink", ClientTransferPage.class, parameters);
        this.buttonGroups.add(this.surveyLink);

        this.acceptTransferLink = new BookmarkablePageLink<Void>("acceptTransferLink", ClientAcceptTransferPage.class, parameters);
        this.buttonGroups.add(this.acceptTransferLink);

        this.rejectTransferLink = new BookmarkablePageLink<Void>("rejectTransferLink", ClientRejectTransferPage.class, parameters);
        this.buttonGroups.add(this.rejectTransferLink);

        this.undoTransferLink = new BookmarkablePageLink<Void>("undoTransferLink", ClientUndoTransferPage.class, parameters);
        this.buttonGroups.add(this.undoTransferLink);

        initSavingAccountTable();

        initLoanAccountTable();

        initUpcomingChargeTable();

        this.clientNameView = new Label("clientNameView", new PropertyModel<>(this, "clientNameValue"));
        add(this.clientNameView);

        this.clientImageField = new WebMarkupContainer("clientImageField");
        this.clientImageField.setOutputMarkupId(true);
        this.clientImageField.add(AttributeModifier.replace("src", new PropertyModel<>(this, "clientImageValue")));
        add(this.clientImageField);

        this.takePictureLink = new BookmarkablePageLink<Void>("takePictureLink", ClientWebcamPage.class, parameters);
        add(this.takePictureLink);
    }

    protected void initSavingAccountTable() {
        this.savingAccountProvider = new JdbcProvider("m_savings_account");
        this.savingAccountProvider.applyJoin("m_code_value", "LEFT JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.savingAccountProvider.boardField("concat(m_savings_account.id,'')", "id", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_no", "account", String.class);
        this.savingAccountProvider.boardField("m_savings_product.name", "product", String.class);
        this.savingAccountProvider.boardField("m_savings_account.status_enum", "status", String.class);
        this.savingAccountProvider.boardField("m_savings_account.account_balance_derived", "balance", Double.class);
        this.savingAccountProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);

        this.savingAccountProvider.selectField("id", String.class);
        this.savingAccountProvider.selectField("status", String.class);

        this.savingAccountColumn = Lists.newArrayList();
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Account"), "account", "account", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.String, Model.of("Product"), "product", "product", this::savingAccountColumn));
        this.savingAccountColumn.add(new TextFilterColumn(this.savingAccountProvider, ItemClass.Double, Model.of("Balance"), "balance", "balance", this::savingAccountColumn));
        this.savingAccountColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::savingAccountAction, this::savingAccountClick));

        this.savingAccountTable = new DefaultDataTable<>("savingAccountTable", savingAccountColumn, this.savingAccountProvider, 20);
        add(this.savingAccountTable);
    }

    protected void initLoanAccountTable() {
        this.loanAccountProvider = new JdbcProvider("m_loan");
        this.loanAccountProvider.applyJoin("m_product_loan", "inner join m_product_loan on m_loan.product_id = m_product_loan.id");
        this.loanAccountProvider.boardField("m_loan.account_no", "account", String.class);
        this.loanAccountProvider.boardField("m_product_loan.name", "product", String.class);
        this.loanAccountProvider.boardField("m_loan.principal_disbursed_derived", "original_loan", Double.class);
        this.loanAccountProvider.boardField("m_loan.total_outstanding_derived", "loan_balance", Double.class);
        this.loanAccountProvider.boardField("m_loan.total_repayment_derived", "amount_paid", Double.class);
        this.loanAccountProvider.boardField("m_loan.loan_type_enum", "type", Long.class);
        this.loanAccountProvider.boardField("m_loan.loan_status_id", "status", Long.class);
        this.loanAccountProvider.boardField("m_loan.id", "id", Long.class);
        this.loanAccountProvider.applyWhere("client_id", "m_loan.client_id = '" + this.clientId + "'");

        this.loanAccountProvider.selectField("id", Long.class);
        this.loanAccountProvider.selectField("status", Long.class);

        this.loanAccountColumn = Lists.newArrayList();
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.String, Model.of("Account #"), "account", "account", this::loanAccountColumn));
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.String, Model.of("Loan Account"), "product", "product", this::loanAccountColumn));
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.Double, Model.of("Original Loan"), "original_loan", "original_loan", this::loanAccountColumn));
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.Double, Model.of("Loan Balance"), "loan_balance", "loan_balance", this::loanAccountColumn));
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.Double, Model.of("Amount Paid"), "amount_paid", "amount_paid", this::loanAccountColumn));
        this.loanAccountColumn.add(new TextFilterColumn(this.loanAccountProvider, ItemClass.Long, Model.of("Type"), "type", "type", this::loanAccountColumn));
        this.loanAccountColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::loanAccountAction, this::loanAccountClick));

        this.loanAccountTable = new DefaultDataTable<>("loanAccountTable", loanAccountColumn, this.loanAccountProvider, 20);
        add(this.loanAccountTable);
    }

    protected void initUpcomingChargeTable() {
        this.upcomingChargeProvider = new JdbcProvider("m_client_charge");
        this.upcomingChargeProvider.applyJoin("m_charge", "inner join m_charge on m_client_charge.charge_id = m_charge.id");

        this.upcomingChargeProvider.boardField("m_client_charge.id", "id", Long.class);
        this.upcomingChargeProvider.boardField("m_charge.name", "name", String.class);
        this.upcomingChargeProvider.boardField("m_client_charge.charge_due_date", "due_date", Calendar.Date);
        this.upcomingChargeProvider.boardField("m_client_charge.amount", "due", Double.class);
        this.upcomingChargeProvider.boardField("m_client_charge.amount_paid_derived", "paid", Double.class);
        this.upcomingChargeProvider.boardField("m_client_charge.amount_waived_derived", "waived", Double.class);
        this.upcomingChargeProvider.boardField("m_client_charge.amount_outstanding_derived", "outstanding", Double.class);

        this.upcomingChargeProvider.applyWhere("amount_outstanding_derived", "m_client_charge.amount_outstanding_derived > 0");
        this.upcomingChargeProvider.applyWhere("is_active", "m_client_charge.is_active = 1");
        this.upcomingChargeProvider.applyWhere("client_id", "m_client_charge.client_id = " + this.clientId);

        this.upcomingChargeProvider.selectField("id", Long.class);

        this.upcomingChargeColumn = Lists.newArrayList();
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.String, Model.of("Name"), "name", "name", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.Date, Model.of("Due as of"), "due_date", "due_date", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.Double, Model.of("Due"), "due", "due", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.Double, Model.of("Paid"), "paid", "paid", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.Double, Model.of("Waived"), "waived", "waived", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new TextFilterColumn(this.upcomingChargeProvider, ItemClass.Double, Model.of("Outstanding"), "outstanding", "outstanding", this::upcomingChargeColumn));
        this.upcomingChargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::upcomingChargeAction, this::upcomingChargeClick));

        this.upcomingChargeTable = new DefaultDataTable<>("upcomingChargeTable", upcomingChargeColumn, this.upcomingChargeProvider, 20);
        add(this.upcomingChargeTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        Long statusEnum = (Long) clientObject.get("status_enum");

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

    protected void clientUnassignStaffPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        if ("confirmButton".equals(signalId)) {
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

    @Override
    protected void initData() {
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.clientNameValue = (String) clientObject.get("display_name");
        if (clientObject.get("image_id") != null) {
            try {
                HttpResponse<InputStream> response = ClientHelper.retrieveClientImage((Session) getSession(), this.clientId);
                this.clientImageValue = IOUtils.toString(response.getBody(), "UTF-8");
                response.getBody().close();
            } catch (UnirestException | IOException e) {
                LOGGER.info(e.getMessage(), e);
            }
        } else {
            this.clientImageValue = RequestCycle.get().urlFor(new PackageResourceReference(Application.class, "resource/client-image-placeholder.png"), null).toString();
        }
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
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountApprovePage.class, parameters);
        } else if ("Undo Approve".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountUndoApprovePage.class, parameters);
        } else if ("Activate".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountActivatePage.class, parameters);
        } else if ("Deposit".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountDepositPage.class, parameters);
        } else if ("Withdraw".equals(column)) {
            String accountId = (String) model.get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("accountId", accountId);
            setResponsePage(SavingAccountWithdrawPage.class, parameters);
        }
    }

    protected ItemPanel savingAccountColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("account".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("clientId", this.clientId);
            parameters.add("accountId", model.get("id"));
            return new LinkCell(SavingAccountPreviewPage.class, parameters, value);
        } else if ("product".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("balance".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected List<ActionItem> upcomingChargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("Pay", Model.of("Pay"), ItemCss.PRIMARY));
        actions.add(new ActionItem("Waive", Model.of("Waive"), ItemCss.DANGER));
        return actions;
    }

    protected void upcomingChargeClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("Pay".equals(column)) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("chargeId", model.get("id"));
            setResponsePage(ChargePayPage.class, parameters);
        } else if ("Waive".equals(column)) {
            ClientChargeWaiveBuilder builder = new ClientChargeWaiveBuilder();
            builder.withChargeId(String.valueOf(model.get("id")));
            builder.withResourceType("2");
            builder.withClientId(this.clientId);
            try {
                ClientHelper.postClientChargeWaive((IMifos) getSession(), builder.build());
            } catch (UnirestException e) {
                throw new WicketRuntimeException(e);
            }
            target.add(this.upcomingChargeTable);
        }
    }

    protected ItemPanel upcomingChargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("due_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        } else if ("due".equals(column) || "paid".equals(column) || "waived".equals(column) || "outstanding".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value == null ? 0 : value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected List<ActionItem> loanAccountAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Long status = (Long) model.get("status");
        if (status == 100) {
            actions.add(new ActionItem("Approve", Model.of("Approve"), ItemCss.PRIMARY));
        } else if (status == 200) {
            actions.add(new ActionItem("Disburse", Model.of("Disburse"), ItemCss.PRIMARY));
        } else if (status == 300) {
            actions.add(new ActionItem("Make Repayment", Model.of("Make Repayment"), ItemCss.PRIMARY));
        }
        return actions;
    }

    protected void loanAccountClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("Approve".equals(column)) {
            String loanId = String.valueOf(model.get("id"));
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("loanId", loanId);
            setResponsePage(LoanAccountApprovePage.class, parameters);
        } else if ("Disburse".equals(column)) {
            String loanId = String.valueOf(model.get("id"));
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("loanId", loanId);
            setResponsePage(LoanAccountDisbursePage.class, parameters);
        } else if ("Make Repayment".equals(column)) {
            String loanId = String.valueOf(model.get("id"));
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("client", ClientEnum.Client.name());
            parameters.add("loanId", loanId);
            setResponsePage(LoanAccountRepaymentPage.class, parameters);
        }

    }

    protected ItemPanel loanAccountColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("account".equals(column) || "product".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("original_loan".equals(column) || "loan_balance".equals(column) || "amount_paid".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        } else if ("type".equals(column)) {
            Long value = (Long) model.get(column);
            if (value == 1) {
                return new TextCell(LoanTypeEnum.Individual.getShortDescription());
            } else if (value == 2) {
                return new TextCell(LoanTypeEnum.Group.getShortDescription());
            } else if (value == 3) {
                return new TextCell(LoanTypeEnum.JLG.getShortDescription());
            } else {
                return new TextCell("Invalid");
            }
        }

        throw new WicketRuntimeException("Unknown " + column);
    }

}
