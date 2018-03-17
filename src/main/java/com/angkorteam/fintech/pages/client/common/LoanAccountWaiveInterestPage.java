package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.WaiveInterestBuilder;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountWaiveInterestPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock interestWaivedOnBlock;
    protected WebMarkupContainer interestWaivedOnIContainer;
    protected Date interestWaivedOnValue;
    protected DateTextField interestWaivedOnField;
    protected TextFeedbackPanel interestWaivedOnFeedback;

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

        initInterestWaivedOnBlock();

        initTransactionAmountBlock();

        initNoteBlock();
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

    protected void initTransactionAmountBlock() {
        this.transactionAmountBlock = new WebMarkupBlock("transactionAmountBlock", Size.Six_6);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountIContainer = new WebMarkupContainer("transactionAmountIContainer");
        this.transactionAmountBlock.add(this.transactionAmountIContainer);
        this.transactionAmountField = new TextField<>("transactionAmountField", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));
        this.transactionAmountField.setRequired(false);
        this.transactionAmountIContainer.add(this.transactionAmountField);
        this.transactionAmountFeedback = new TextFeedbackPanel("transactionAmountFeedback", this.transactionAmountField);
        this.transactionAmountIContainer.add(this.transactionAmountFeedback);
    }

    protected void initInterestWaivedOnBlock() {
        this.interestWaivedOnBlock = new WebMarkupBlock("interestWaivedOnBlock", Size.Six_6);
        this.form.add(this.interestWaivedOnBlock);
        this.interestWaivedOnIContainer = new WebMarkupContainer("interestWaivedOnIContainer");
        this.interestWaivedOnBlock.add(this.interestWaivedOnIContainer);
        this.interestWaivedOnField = new DateTextField("interestWaivedOnField", new PropertyModel<>(this, "interestWaivedOnValue"));
        this.interestWaivedOnField.setLabel(Model.of("Interest Waived On"));
        this.interestWaivedOnField.setRequired(false);
        this.interestWaivedOnIContainer.add(this.interestWaivedOnField);
        this.interestWaivedOnFeedback = new TextFeedbackPanel("interestWaivedOnFeedback", this.interestWaivedOnField);
        this.interestWaivedOnIContainer.add(this.interestWaivedOnFeedback);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
        this.interestWaivedOnValue = DateTime.now().toDate();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addField(MLoan.Field.PRINCIPAL_AMOUNT);
        selectQuery.addWhere(MLoan.Field.ID + " = :" + MLoan.Field.ID, this.loanId);

        this.transactionAmountValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Double.class);
    }

    protected void saveButtonSubmit(Button button) {
        WaiveInterestBuilder builder = new WaiveInterestBuilder();
        builder.withId(this.loanId);
        builder.withTransactionDate(this.interestWaivedOnValue);
        builder.withTransactionAmount(this.transactionAmountValue);
        builder.withNote(this.noteValue);

        JsonNode node = null;
        try {
            node = ClientHelper.waiveInterestLoanAccount((Session) getSession(), builder.build());
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
