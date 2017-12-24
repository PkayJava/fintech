package com.angkorteam.fintech.pages.client.common;

import java.util.Date;
import java.util.Map;

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
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.ApproveBuilder;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
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
public class LoanAccountDisburseSavingPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock approvedOnBlock;
    protected WebMarkupContainer approvedOnIContainer;
    protected Date approvedOnValue;
    protected DateTextField approvedOnField;
    protected TextFeedbackPanel approvedOnFeedback;

    protected WebMarkupBlock expectedDisbursementOnBlock;
    protected WebMarkupContainer expectedDisbursementOnIContainer;
    protected Date expectedDisbursementOnValue;
    protected DateTextField expectedDisbursementOnField;
    protected TextFeedbackPanel expectedDisbursementOnFeedback;

    protected WebMarkupBlock approvedAmountBlock;
    protected WebMarkupContainer approvedAmountIContainer;
    protected Double approvedAmountValue;
    protected TextField<Double> approvedAmountField;
    protected TextFeedbackPanel approvedAmountFeedback;

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

        initApprovedOnBlock();

        initExpectedDisbursementOnBlock();

        initApprovedAmount();

        initNoteBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initApprovedAmount() {
        this.approvedAmountBlock = new WebMarkupBlock("approvedAmountBlock", Size.Six_6);
        this.form.add(this.approvedAmountBlock);
        this.approvedAmountIContainer = new WebMarkupContainer("approvedAmountIContainer");
        this.approvedAmountBlock.add(this.approvedAmountIContainer);
        this.approvedAmountField = new TextField<>("approvedAmountField", new PropertyModel<>(this, "approvedAmountValue"));
        this.approvedAmountField.setLabel(Model.of("Approved Amount"));
        this.approvedAmountIContainer.add(this.approvedAmountField);
        this.approvedAmountFeedback = new TextFeedbackPanel("approvedAmountFeedback", this.approvedAmountField);
        this.approvedAmountIContainer.add(this.approvedAmountFeedback);
    }

    protected void initExpectedDisbursementOnBlock() {
        this.expectedDisbursementOnBlock = new WebMarkupBlock("expectedDisbursementOnBlock", Size.Six_6);
        this.form.add(this.expectedDisbursementOnBlock);
        this.expectedDisbursementOnIContainer = new WebMarkupContainer("expectedDisbursementOnIContainer");
        this.expectedDisbursementOnBlock.add(this.expectedDisbursementOnIContainer);
        this.expectedDisbursementOnField = new DateTextField("expectedDisbursementOnField", new PropertyModel<>(this, "expectedDisbursementOnValue"));
        this.expectedDisbursementOnField.setLabel(Model.of("Expected Disbursement On"));
        this.expectedDisbursementOnIContainer.add(this.expectedDisbursementOnField);
        this.expectedDisbursementOnFeedback = new TextFeedbackPanel("expectedDisbursementOnFeedback", this.expectedDisbursementOnField);
        this.expectedDisbursementOnIContainer.add(this.expectedDisbursementOnFeedback);
    }

    protected void initApprovedOnBlock() {
        this.approvedOnBlock = new WebMarkupBlock("approvedOnBlock", Size.Six_6);
        this.form.add(this.approvedOnBlock);
        this.approvedOnIContainer = new WebMarkupContainer("approvedOnIContainer");
        this.approvedOnBlock.add(this.approvedOnIContainer);
        this.approvedOnField = new DateTextField("approvedOnField", new PropertyModel<>(this, "approvedOnValue"));
        this.approvedOnField.setLabel(Model.of("Approved On"));
        this.approvedOnIContainer.add(this.approvedOnField);
        this.approvedOnFeedback = new TextFeedbackPanel("approvedOnFeedback", this.approvedOnField);
        this.approvedOnIContainer.add(this.approvedOnFeedback);
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

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
        this.approvedOnValue = DateTime.now().toDate();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> loanObject = jdbcTemplate.queryForMap("select principal_amount_proposed, expected_disbursedon_date from m_loan where id = ?", this.loanId);
        this.approvedAmountValue = (Double) loanObject.get("principal_amount_proposed");
        this.expectedDisbursementOnValue = (Date) loanObject.get("expected_disbursedon_date");
    }

    protected void saveButtonSubmit(Button button) {
        ApproveBuilder builder = new ApproveBuilder();
        builder.withId(this.loanId);
        builder.withNote(this.noteValue);
        builder.withApprovedOnDate(this.approvedOnValue);
        builder.withExpectedDisbursementDate(this.expectedDisbursementOnValue);
        builder.withApprovedLoanAmount(this.approvedAmountValue);

        JsonNode node = null;
        try {
            node = ClientHelper.approveLoanAccount((Session) getSession(), builder.build());
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
