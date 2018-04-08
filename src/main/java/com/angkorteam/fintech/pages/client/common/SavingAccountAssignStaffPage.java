package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountAssignStaffPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String officeId;
    protected String savingId;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected SingleChoiceProvider officerProvider;
    protected WebMarkupBlock officerBlock;
    protected WebMarkupContainer officerIContainer;
    protected Option officerValue;
    protected Select2SingleChoice<Option> officerField;
    protected TextFeedbackPanel officerFeedback;

    protected WebMarkupBlock assignmentDateBlock;
    protected WebMarkupContainer assignmentDateIContainer;
    protected DateTextField assignmentDateField;
    protected TextFeedbackPanel assignmentDateFeedback;
    protected Date assignmentDateValue;

    @Override
    protected void initComponent() {

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

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

        initOfficerBlock();

        initAssignmentDateBlock();
    }

    protected void initAssignmentDateBlock() {
        this.assignmentDateBlock = new WebMarkupBlock("assignmentDateBlock", Size.Six_6);
        this.form.add(this.assignmentDateBlock);
        this.assignmentDateIContainer = new WebMarkupContainer("assignmentDateIContainer");
        this.assignmentDateBlock.add(this.assignmentDateIContainer);
        this.assignmentDateField = new DateTextField("assignmentDateField", new PropertyModel<>(this, "assignmentDateValue"));
        this.assignmentDateField.setLabel(Model.of("Assignment Date"));
        this.assignmentDateField.setRequired(false);
        this.assignmentDateIContainer.add(this.assignmentDateField);
        this.assignmentDateFeedback = new TextFeedbackPanel("assignmentDateFeedback", this.assignmentDateField);
        this.assignmentDateIContainer.add(this.assignmentDateFeedback);
    }

    protected void initOfficerBlock() {
        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
        this.form.add(this.officerBlock);
        this.officerIContainer = new WebMarkupContainer("officerIContainer");
        this.officerBlock.add(this.officerIContainer);
        this.officerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
        this.officerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
        this.officerProvider.applyWhere("is_loan_officer", MStaff.Field.IS_LOAN_OFFICER + " = 1");
        this.officerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
        this.officerField.setLabel(Model.of("Officer"));
        this.officerField.add(new OnChangeAjaxBehavior());
        this.officerField.setRequired(true);
        this.officerIContainer.add(this.officerField);
        this.officerFeedback = new TextFeedbackPanel("officerFeedback", this.officerField);
        this.officerIContainer.add(this.officerFeedback);
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

        this.savingId = getPageParameters().get("savingId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            this.officeId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.groupId);
            this.officeId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.centerId);
            this.officeId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        }
    }

    protected void okayButtonSubmit(Button button) {
        // TODO : implements business logic
        // Assign Staff
        // m_client.staff_id
        // verbose : POST
        // /api/v1/savingsaccounts/1?command=assignSavingsOfficer
        // toSavingsOfficerId 89
        // assignmentDate 19 October 2017
        // locale en
        // dateFormat dd MMMM yyyy
        // fromSavingsOfficerId

        // UnAssign Staff
        // verbose : POST
        // /api/v1/clients/1?command=unassignstaff
        // staffId : 28

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
