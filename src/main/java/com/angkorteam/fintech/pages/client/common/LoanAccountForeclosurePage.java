package com.angkorteam.fintech.pages.client.common;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.TemplateHelper;
import com.angkorteam.fintech.helper.loan.ForeclosureBuilder;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountForeclosurePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String clientDisplayName;
    protected String groupDisplayName;
    protected String centerDisplayName;
    protected String loanAccountNo;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock transactionDateBlock;
    protected WebMarkupContainer transactionDateIContainer;
    protected DateTextField transactionDateField;
    protected TextFeedbackPanel transactionDateFeedback;
    protected Date transactionDateValue;

    protected WebMarkupBlock noteBlock;
    protected WebMarkupContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    protected WebMarkupBlock principleBlock;
    protected WebMarkupContainer principleVContainer;
    protected ReadOnlyView principleView;
    protected Double principleValue;

    protected WebMarkupBlock interestBlock;
    protected WebMarkupContainer interestVContainer;
    protected ReadOnlyView interestView;
    protected Double interestValue;

    protected WebMarkupBlock feeAmountBlock;
    protected WebMarkupContainer feeAmountVContainer;
    protected ReadOnlyView feeAmountView;
    protected Double feeAmountValue;

    protected WebMarkupBlock penaltyAmountBlock;
    protected WebMarkupContainer penaltyAmountVContainer;
    protected ReadOnlyView penaltyAmountView;
    protected Double penaltyAmountValue;

    protected WebMarkupBlock transactionAmountBlock;
    protected WebMarkupContainer transactionAmountVContainer;
    protected ReadOnlyView transactionAmountView;
    protected Double transactionAmountValue;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanId);
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanAccountPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initTransactionDateBlock();

        initNoteBlock();

        this.principleBlock = new WebMarkupBlock("principleBlock", Size.Six_6);
        this.form.add(this.principleBlock);
        this.principleVContainer = new WebMarkupContainer("principleVContainer");
        this.principleBlock.add(this.principleVContainer);
        this.principleView = new ReadOnlyView("principleView", new PropertyModel<>(this, "principleValue"));
        this.principleVContainer.add(this.principleView);

        this.interestBlock = new WebMarkupBlock("interestBlock", Size.Six_6);
        this.form.add(this.interestBlock);
        this.interestVContainer = new WebMarkupContainer("interestVContainer");
        this.interestBlock.add(this.interestVContainer);
        this.interestView = new ReadOnlyView("interestView", new PropertyModel<>(this, "interestValue"));
        this.interestVContainer.add(this.interestView);

        this.feeAmountBlock = new WebMarkupBlock("feeAmountBlock", Size.Six_6);
        this.form.add(this.feeAmountBlock);
        this.feeAmountVContainer = new WebMarkupContainer("feeAmountVContainer");
        this.feeAmountBlock.add(this.feeAmountVContainer);
        this.feeAmountView = new ReadOnlyView("feeAmountView", new PropertyModel<>(this, "feeAmountValue"));
        this.feeAmountVContainer.add(this.feeAmountView);

        this.penaltyAmountBlock = new WebMarkupBlock("penaltyAmountBlock", Size.Six_6);
        this.form.add(this.penaltyAmountBlock);
        this.penaltyAmountVContainer = new WebMarkupContainer("penaltyAmountVContainer");
        this.penaltyAmountBlock.add(this.penaltyAmountVContainer);
        this.penaltyAmountView = new ReadOnlyView("penaltyAmountView", new PropertyModel<>(this, "penaltyAmountValue"));
        this.penaltyAmountVContainer.add(this.penaltyAmountView);

        this.transactionAmountBlock = new WebMarkupBlock("transactionAmountBlock", Size.Six_6);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountVContainer = new WebMarkupContainer("transactionAmountVContainer");
        this.transactionAmountBlock.add(this.transactionAmountVContainer);
        this.transactionAmountView = new ReadOnlyView("transactionAmountView", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountVContainer.add(this.transactionAmountView);

    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initNoteBlock() {
        this.noteBlock = new WebMarkupBlock("noteBlock", Size.Six_6);
        this.form.add(this.noteBlock);
        this.noteIContainer = new WebMarkupContainer("noteIContainer");
        this.noteBlock.add(this.noteIContainer);
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setLabel(Model.of("Note"));
        this.noteIContainer.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.noteIContainer.add(this.noteFeedback);
    }

    protected void initTransactionDateBlock() {
        this.transactionDateBlock = new WebMarkupBlock("transactionDateBlock", Size.Six_6);
        this.form.add(this.transactionDateBlock);
        this.transactionDateIContainer = new WebMarkupContainer("transactionDateIContainer");
        this.transactionDateBlock.add(this.transactionDateIContainer);
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateField.setLabel(Model.of("Transaction Date"));
        this.transactionDateField.setRequired(false);
        this.transactionDateIContainer.add(this.transactionDateField);
        this.transactionDateFeedback = new TextFeedbackPanel("transactionDateFeedback", this.transactionDateField);
        this.transactionDateIContainer.add(this.transactionDateFeedback);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addWhere(MLoan.Field.ID + " = '" + this.loanId + "'");
        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
        selectQuery.addField(MLoan.Field.TOTAL_OUTSTANDING_DERIVED);
        selectQuery.addField(MLoan.Field.PRINCIPAL_OUTSTANDING_DERIVED);
        selectQuery.addField(MLoan.Field.INTEREST_OUTSTANDING_DERIVED);
        selectQuery.addField(MLoan.Field.LOAN_OFFICER_ID);
        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.loanAccountNo = (String) loanObject.get("account_no");

        try {
            JsonNode node = TemplateHelper.retrieveLoanAccountPrepay((Session) getSession(), this.loanId);
            this.transactionDateValue = new LocalDate(node.getObject().getJSONArray("date").getInt(0), node.getObject().getJSONArray("date").getInt(1), node.getObject().getJSONArray("date").getInt(2)).toDate();
            this.principleValue = node.getObject().getDouble("amount");
            this.interestValue = node.getObject().getDouble("interestPortion");
            this.feeAmountValue = node.getObject().getDouble("feeChargesPortion");
            this.penaltyAmountValue = node.getObject().getDouble("penaltyChargesPortion");
            this.transactionAmountValue = node.getObject().getDouble("outstandingLoanBalance");
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.groupDisplayName = (String) groupObject.get("display_name");
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.centerDisplayName = (String) centerObject.get("display_name");
        }

    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            PageParameters parameters = new PageParameters();
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
                breadcrumb.setLabel(this.clientDisplayName);
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
                breadcrumb.setLabel(this.groupDisplayName);
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
                breadcrumb.setLabel(this.centerDisplayName);
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            parameters.add("loanId", this.loanId);
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            breadcrumb.setParameters(parameters);
            breadcrumb.setLabel(this.loanAccountNo);
            breadcrumb.setPage(LoanAccountPreviewPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Foreclosure");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void saveButtonSubmit(Button button) {
        ForeclosureBuilder builder = new ForeclosureBuilder();
        builder.withId(this.loanId);
        builder.withTransactionDate(this.transactionDateValue);
        builder.withNote(this.noteValue);

        JsonNode node = null;
        try {
            node = ClientHelper.foreclosureLoanAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        if (this.client == ClientEnum.Client) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            setResponsePage(GroupPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
    }

}
