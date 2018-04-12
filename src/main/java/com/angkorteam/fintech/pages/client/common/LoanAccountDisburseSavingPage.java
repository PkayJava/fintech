package com.angkorteam.fintech.pages.client.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.loan.DisburseSavingBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountDisburseSavingPage extends Page {

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

    protected WebMarkupBlock disbursedOnBlock;
    protected WebMarkupContainer disbursedOnIContainer;
    protected Date disbursedOnValue;
    protected DateTextField disbursedOnField;
    protected TextFeedbackPanel disbursedOnFeedback;

    protected WebMarkupBlock transactionAmountBlock;
    protected WebMarkupContainer transactionAmountIContainer;
    protected Double transactionAmountValue;
    protected TextField<Double> transactionAmountField;
    protected TextFeedbackPanel transactionAmountFeedback;

    protected WebMarkupBlock noteBlock;
    protected WebMarkupContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

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

        this.transactionAmountBlock = new WebMarkupBlock("transactionAmountBlock", Size.Six_6);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountIContainer = new WebMarkupContainer("transactionAmountIContainer");
        this.transactionAmountBlock.add(this.transactionAmountIContainer);
        this.transactionAmountField = new TextField<>("transactionAmountField", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));
        this.transactionAmountIContainer.add(this.transactionAmountField);
        this.transactionAmountFeedback = new TextFeedbackPanel("transactionAmountFeedback", this.transactionAmountField);
        this.transactionAmountIContainer.add(this.transactionAmountFeedback);

        this.disbursedOnBlock = new WebMarkupBlock("disbursedOnBlock", Size.Six_6);
        this.form.add(this.disbursedOnBlock);
        this.disbursedOnIContainer = new WebMarkupContainer("disbursedOnIContainer");
        this.disbursedOnBlock.add(this.disbursedOnIContainer);
        this.disbursedOnField = new DateTextField("disbursedOnField", new PropertyModel<>(this, "disbursedOnValue"));
        this.disbursedOnField.setLabel(Model.of("Disbursed On"));
        this.disbursedOnIContainer.add(this.disbursedOnField);
        this.disbursedOnFeedback = new TextFeedbackPanel("disbursedOnFeedback", this.disbursedOnField);
        this.disbursedOnIContainer.add(this.disbursedOnFeedback);

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

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
        this.disbursedOnValue = DateTime.now().toDate();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addWhere(MLoan.Field.ID + " = '" + this.loanId + "'");
        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
        selectQuery.addField(MLoan.Field.PRINCIPAL_AMOUNT);
        selectQuery.addField(MLoan.Field.LOAN_OFFICER_ID);
        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.loanAccountNo = (String) loanObject.get("account_no");

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

        this.transactionAmountValue = (Double) loanObject.get("principal_amount");
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
            breadcrumb.setLabel("Disburse To Saving Account");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void saveButtonSubmit(Button button) {
        DisburseSavingBuilder builder = new DisburseSavingBuilder();
        builder.withId(this.loanId);
        builder.withNote(this.noteValue);
        builder.withActualDisbursementDate(this.disbursedOnValue);
        builder.withTransactionAmount(this.transactionAmountValue);

        JsonNode node = ClientHelper.disburseToSavingLoanAccount((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

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
        setResponsePage(LoanAccountPreviewPage.class, parameters);
    }

}
