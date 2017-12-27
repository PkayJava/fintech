package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
import com.angkorteam.fintech.helper.loan.LoanOfficerAssignBuilder;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanOfficerAssignPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;
    protected String officeId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock assignmentDateBlock;
    protected WebMarkupContainer assignmentDateIContainer;
    protected DateTextField assignmentDateField;
    protected TextFeedbackPanel assignmentDateFeedback;
    protected Date assignmentDateValue;

    protected SingleChoiceProvider officerProvider;
    protected WebMarkupBlock officerBlock;
    protected WebMarkupContainer officerIContainer;
    protected Option officerValue;
    protected Select2SingleChoice<Option> officerField;
    protected TextFeedbackPanel officerFeedback;

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

        this.assignmentDateBlock = new WebMarkupBlock("assignmentDateBlock", Size.Six_6);
        this.form.add(this.assignmentDateBlock);
        this.assignmentDateIContainer = new WebMarkupContainer("assignmentDateIContainer");
        this.assignmentDateBlock.add(this.assignmentDateIContainer);
        this.assignmentDateField = new DateTextField("assignmentDateField", new PropertyModel<>(this, "assignmentDateValue"));
        this.assignmentDateField.setLabel(Model.of("Rejected On"));
        this.assignmentDateField.setRequired(false);
        this.assignmentDateIContainer.add(this.assignmentDateField);
        this.assignmentDateFeedback = new TextFeedbackPanel("assignmentDateFeedback", this.assignmentDateField);
        this.assignmentDateIContainer.add(this.assignmentDateFeedback);

        this.officerProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.officerProvider.applyWhere("is_active", "is_active = 1");
        this.officerProvider.applyWhere("is_loan_officer", "is_loan_officer = 1");
        this.officerProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
        this.form.add(this.officerBlock);
        this.officerIContainer = new WebMarkupContainer("officerIContainer");
        this.officerBlock.add(this.officerIContainer);
        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
        this.officerField.setLabel(Model.of("Loan Officer"));
        this.officerField.setRequired(false);
        this.officerIContainer.add(this.officerField);
        this.officerFeedback = new TextFeedbackPanel("officerFeedback", this.officerField);
        this.officerIContainer.add(this.officerFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
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
        this.assignmentDateValue = DateTime.now().toDate();
    }

    protected void saveButtonSubmit(Button button) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        String fromLoanOfficerId = jdbcTemplate.queryForObject("select loan_officer_id from m_loan where id = ?", String.class, this.loanId);

        LoanOfficerAssignBuilder builder = new LoanOfficerAssignBuilder();
        builder.withLoanId(this.loanId);
        builder.withAssignmentDate(this.assignmentDateValue);
        if (this.officerValue != null) {
            builder.withToLoanOfficerId(this.officerValue.getId());
        }
        builder.withFromLoanOfficerId(fromLoanOfficerId);

        JsonNode node = null;
        try {
            node = ClientHelper.assignOfficerLoanAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
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
