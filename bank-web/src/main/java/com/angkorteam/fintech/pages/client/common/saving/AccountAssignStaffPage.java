//package com.angkorteam.fintech.pages.client.common.saving;
//
//import java.util.Date;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MGroup;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class AccountAssignStaffPage extends Page {
//
//    protected ClientEnum client;
//
//    protected String clientId;
//
//    protected String officeId;
//    protected String savingId;
//
//    protected Form<Void> form;
//    protected Button okayButton;
//
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock officerBlock;
//    protected UIContainer officerIContainer;
//    protected SingleChoiceProvider officerProvider;
//    protected Select2SingleChoice<Option> officerField;
//    protected Option officerValue;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock assignmentDateBlock;
//    protected UIContainer assignmentDateIContainer;
//    protected DateTextField assignmentDateField;
//    protected Date assignmentDateValue;
//
//    protected UIBlock row2Block1;
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.okayButton = new Button("okayButton");
//        this.okayButton.setOnSubmit(this::okayButtonSubmit);
//        this.form.add(this.okayButton);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        if (this.client == ClientEnum.Client) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
//        }
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.officerBlock = this.row1.newUIBlock("officerBlock", Size.Six_6);
//        this.officerIContainer = this.officerBlock.newUIContainer("officerIContainer");
//        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
//        this.officerIContainer.add(this.officerField);
//        this.officerIContainer.newFeedback("officerFeedback", this.officerField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.assignmentDateBlock = this.row2.newUIBlock("assignmentDateBlock", Size.Six_6);
//        this.assignmentDateIContainer = this.assignmentDateBlock.newUIContainer("assignmentDateIContainer");
//        this.assignmentDateField = new DateTextField("assignmentDateField", new PropertyModel<>(this, "assignmentDateValue"));
//        this.assignmentDateIContainer.add(this.assignmentDateField);
//        this.assignmentDateIContainer.newFeedback("assignmentDateFeedback", this.assignmentDateField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.officerField.setLabel(Model.of("Officer"));
//        this.officerField.add(new OnChangeAjaxBehavior());
//        this.officerField.setRequired(true);
//
//        this.assignmentDateField.setLabel(Model.of("Assignment Date"));
//        this.assignmentDateField.setRequired(false);
//    }
//
//    @Override
//    protected void initData() {
//        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());
//
//        this.clientId = getPageParameters().get("clientId").toString();
//
//        this.savingId = getPageParameters().get("savingId").toString();
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        if (this.client == ClientEnum.Client) {
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addField(MClient.Field.OFFICE_ID);
//            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.officeId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        } else if (this.client == ClientEnum.Group || this.client == ClientEnum.Center) {
//            selectQuery = new SelectQuery(MGroup.NAME);
//            selectQuery.addField(MGroup.Field.OFFICE_ID);
//            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.officeId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        }
//
//        this.officerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.officerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//        this.officerProvider.applyWhere("is_loan_officer", MStaff.Field.IS_LOAN_OFFICER + " = 1");
//        this.officerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
//    }
//
//    protected void okayButtonSubmit(Button button) {
//        // TODO : implements business logic
//        // Assign Staff
//        // m_client.staff_id
//        // verbose : POST
//        // /api/v1/savingsaccounts/1?command=assignSavingsOfficer
//        // toSavingsOfficerId 89
//        // assignmentDate 19 October 2017
//        // locale en
//        // dateFormat dd MMMM yyyy
//        // fromSavingsOfficerId
//
//        // UnAssign Staff
//        // verbose : POST
//        // /api/v1/clients/1?command=unassignstaff
//        // staffId : 28
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        if (this.client == ClientEnum.Client) {
//            setResponsePage(ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            setResponsePage(CenterPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            setResponsePage(GroupPreviewPage.class, parameters);
//        }
//
//    }
//}
