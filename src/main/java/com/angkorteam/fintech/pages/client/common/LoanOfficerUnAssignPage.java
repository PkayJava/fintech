package com.angkorteam.fintech.pages.client.common;

import java.util.Date;
import java.util.Map;

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
import com.angkorteam.fintech.helper.loan.LoanOfficerUnAssignBuilder;
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
public class LoanOfficerUnAssignPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;
    protected String officeId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock unassignedOnBlock;
    protected WebMarkupContainer unassignedOnIContainer;
    protected DateTextField unassignedOnField;
    protected TextFeedbackPanel unassignedOnFeedback;
    protected Date unassignedOnValue;

    protected WebMarkupBlock officerBlock;
    protected WebMarkupContainer officerVContainer;
    protected String officerValue;
    protected ReadOnlyView officerView;

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

        this.unassignedOnBlock = new WebMarkupBlock("unassignedOnBlock", Size.Six_6);
        this.form.add(this.unassignedOnBlock);
        this.unassignedOnIContainer = new WebMarkupContainer("unassignedOnIContainer");
        this.unassignedOnBlock.add(this.unassignedOnIContainer);
        this.unassignedOnField = new DateTextField("unassignedOnField", new PropertyModel<>(this, "unassignedOnValue"));
        this.unassignedOnField.setLabel(Model.of("Rejected On"));
        this.unassignedOnField.setRequired(false);
        this.unassignedOnIContainer.add(this.unassignedOnField);
        this.unassignedOnFeedback = new TextFeedbackPanel("unassignedOnFeedback", this.unassignedOnField);
        this.unassignedOnIContainer.add(this.unassignedOnFeedback);

        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
        this.form.add(this.officerBlock);
        this.officerVContainer = new WebMarkupContainer("officerVContainer");
        this.officerBlock.add(this.officerVContainer);
        this.officerView = new ReadOnlyView("officerView", new PropertyModel<>(this, "officerValue"));
        this.officerVContainer.add(this.officerView);
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
        this.unassignedOnValue = DateTime.now().toDate();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> loanObject = jdbcTemplate.queryForMap("select loan_officer_id from m_loan where id = ?", this.loanId);
        this.officerValue = jdbcTemplate.queryForObject("select display_name from m_staff where id = ?", String.class, loanObject.get("loan_officer_id"));
    }

    protected void saveButtonSubmit(Button button) {
        LoanOfficerUnAssignBuilder builder = new LoanOfficerUnAssignBuilder();
        builder.withLoanId(this.loanId);
        builder.withUnassignedDate(this.unassignedOnValue);

        JsonNode node = null;
        try {
            node = ClientHelper.unassignOfficerLoanAccount((Session) getSession(), builder.build());
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
