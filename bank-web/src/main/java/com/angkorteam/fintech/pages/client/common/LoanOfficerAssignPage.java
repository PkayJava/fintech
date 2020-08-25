//package com.angkorteam.fintech.pages.client.common;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import com.angkorteam.fintech.widget.WebMarkupContainer;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.joda.time.DateTime;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MGroup;
//import com.angkorteam.fintech.ddl.MLoan;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.loan.LoanOfficerAssignBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanOfficerAssignPage extends Page {
//
//    protected ClientEnum client;
//
//    protected String clientId;
//    protected String groupId;
//    protected String centerId;
//
//    protected String clientDisplayName;
//    protected String groupDisplayName;
//    protected String centerDisplayName;
//    protected String loanAccountNo;
//
//    protected Long officeId;
//    protected String loanId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected WebMarkupBlock assignmentDateBlock;
//    protected WebMarkupContainer assignmentDateIContainer;
//    protected DateTextField assignmentDateField;
//    protected TextFeedbackPanel assignmentDateFeedback;
//    protected Date assignmentDateValue;
//
//    protected SingleChoiceProvider officerProvider;
//    protected WebMarkupBlock officerBlock;
//    protected WebMarkupContainer officerIContainer;
//    protected Option officerValue;
//    protected Select2SingleChoice<Option> officerField;
//    protected TextFeedbackPanel officerFeedback;
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("client", this.client.name());
//        parameters.add("loanId", this.loanId);
//        if (this.client == ClientEnum.Client) {
//            parameters.add("clientId", this.clientId);
//        } else if (this.client == ClientEnum.Center) {
//            parameters.add("centerId", this.centerId);
//        } else if (this.client == ClientEnum.Group) {
//            parameters.add("groupId", this.groupId);
//        } else {
//            throw new WicketRuntimeException("Unknown " + this.client);
//        }
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanAccountPreviewPage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.assignmentDateBlock = new WebMarkupBlock("assignmentDateBlock", Size.Six_6);
//        this.form.add(this.assignmentDateBlock);
//        this.assignmentDateIContainer = new WebMarkupContainer("assignmentDateIContainer");
//        this.assignmentDateBlock.add(this.assignmentDateIContainer);
//        this.assignmentDateField = new DateTextField("assignmentDateField", new PropertyModel<>(this, "assignmentDateValue"));
//        this.assignmentDateField.setLabel(Model.of("Rejected On"));
//        this.assignmentDateField.setRequired(false);
//        this.assignmentDateIContainer.add(this.assignmentDateField);
//        this.assignmentDateFeedback = new TextFeedbackPanel("assignmentDateFeedback", this.assignmentDateField);
//        this.assignmentDateIContainer.add(this.assignmentDateFeedback);
//
//        this.officerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.officerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//        this.officerProvider.applyWhere("is_loan_officer", MStaff.Field.IS_LOAN_OFFICER + " = 1");
//        this.officerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
//        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
//        this.form.add(this.officerBlock);
//        this.officerIContainer = new WebMarkupContainer("officerIContainer");
//        this.officerBlock.add(this.officerIContainer);
//        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
//        this.officerField.setLabel(Model.of("Loan Officer"));
//        this.officerField.setRequired(false);
//        this.officerIContainer.add(this.officerField);
//        this.officerFeedback = new TextFeedbackPanel("officerFeedback", this.officerField);
//        this.officerIContainer.add(this.officerFeedback);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    @Override
//    protected void initData() {
//        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());
//
//        this.clientId = getPageParameters().get("clientId").toString();
//        this.groupId = getPageParameters().get("groupId").toString();
//        this.centerId = getPageParameters().get("centerId").toString();
//
//        this.loanId = getPageParameters().get("loanId").toString();
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//        selectQuery = new SelectQuery(MLoan.NAME);
//        selectQuery.addWhere(MLoan.Field.ID + " = '" + this.loanId + "'");
//        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
//        selectQuery.addField(MLoan.Field.LOAN_OFFICER_ID);
//        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.loanAccountNo = (String) loanObject.get("account_no");
//
//        if (this.client == ClientEnum.Client) {
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addField(MClient.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.clientDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        }
//        if (this.client == ClientEnum.Group) {
//            selectQuery = new SelectQuery(MGroup.NAME);
//            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
//            this.groupDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        }
//        if (this.client == ClientEnum.Center) {
//            selectQuery = new SelectQuery(MGroup.NAME);
//            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
//            this.centerDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        }
//
//        selectQuery = new SelectQuery(MStaff.NAME);
//        selectQuery.addField(MStaff.Field.ID);
//        selectQuery.addField(MStaff.Field.DISPLAY_NAME + " as text");
//        selectQuery.addWhere(MStaff.Field.ID + " = :" + MStaff.Field.ID, loanObject.get("loan_officer_id"));
//        this.officerValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//
//        this.assignmentDateValue = DateTime.now().toDate();
//    }
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            if (this.client == ClientEnum.Client) {
//                breadcrumb.setLabel("Clients");
//            } else if (this.client == ClientEnum.Group) {
//                breadcrumb.setLabel("Groups");
//            } else if (this.client == ClientEnum.Center) {
//                breadcrumb.setLabel("Centers");
//            }
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            if (this.client == ClientEnum.Client) {
//                breadcrumb.setLabel("Clients");
//                breadcrumb.setPage(ClientBrowsePage.class);
//            } else if (this.client == ClientEnum.Group) {
//                breadcrumb.setLabel("Groups");
//                breadcrumb.setPage(GroupBrowsePage.class);
//            } else if (this.client == ClientEnum.Center) {
//                breadcrumb.setLabel("Centers");
//                breadcrumb.setPage(CenterBrowsePage.class);
//            }
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            PageParameters parameters = new PageParameters();
//            if (this.client == ClientEnum.Client) {
//                parameters.add("clientId", this.clientId);
//                breadcrumb.setLabel(this.clientDisplayName);
//                breadcrumb.setPage(ClientPreviewPage.class);
//            } else if (this.client == ClientEnum.Group) {
//                parameters.add("groupId", this.groupId);
//                breadcrumb.setLabel(this.groupDisplayName);
//                breadcrumb.setPage(GroupPreviewPage.class);
//            } else if (this.client == ClientEnum.Center) {
//                parameters.add("centerId", this.centerId);
//                breadcrumb.setLabel(this.centerDisplayName);
//                breadcrumb.setPage(CenterPreviewPage.class);
//            }
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            PageParameters parameters = new PageParameters();
//            parameters.add("client", this.client.name());
//            parameters.add("loanId", this.loanId);
//            if (this.client == ClientEnum.Client) {
//                parameters.add("clientId", this.clientId);
//            } else if (this.client == ClientEnum.Group) {
//                parameters.add("groupId", this.groupId);
//            } else if (this.client == ClientEnum.Center) {
//                parameters.add("centerId", this.centerId);
//            }
//            breadcrumb.setParameters(parameters);
//            breadcrumb.setLabel(this.loanAccountNo);
//            breadcrumb.setPage(LoanAccountPreviewPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Assign Officer");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MLoan.NAME);
//        selectQuery.addField(MLoan.Field.LOAN_OFFICER_ID);
//        selectQuery.addWhere(MLoan.Field.ID + " = :" + MLoan.Field.ID, this.loanId);
//
//        String fromLoanOfficerId = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//
//        LoanOfficerAssignBuilder builder = new LoanOfficerAssignBuilder();
//        builder.withLoanId(this.loanId);
//        builder.withAssignmentDate(this.assignmentDateValue);
//        if (this.officerValue != null) {
//            builder.withToLoanOfficerId(this.officerValue.getId());
//        }
//        builder.withFromLoanOfficerId(fromLoanOfficerId);
//
//        JsonNode node = ClientHelper.assignOfficerLoanAccount((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("client", this.client.name());
//        parameters.add("loanId", this.loanId);
//        if (this.client == ClientEnum.Client) {
//            parameters.add("clientId", this.clientId);
//        } else if (this.client == ClientEnum.Center) {
//            parameters.add("centerId", this.centerId);
//        } else if (this.client == ClientEnum.Group) {
//            parameters.add("groupId", this.groupId);
//        } else {
//            throw new WicketRuntimeException("Unknown " + this.client);
//        }
//        setResponsePage(LoanAccountPreviewPage.class, parameters);
//    }
//
//}
