package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.ForeclosureBuilder;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountForeclosurePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

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
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
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
        this.transactionDateValue = DateTime.now().toDate();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
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
