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
//import com.angkorteam.fintech.dto.builder.loan.LoanOfficerUnAssignBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanOfficerUnAssignPage extends Page {
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
//    protected String officeId;
//    protected String loanId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected WebMarkupBlock unassignedOnBlock;
//    protected WebMarkupContainer unassignedOnIContainer;
//    protected DateTextField unassignedOnField;
//    protected TextFeedbackPanel unassignedOnFeedback;
//    protected Date unassignedOnValue;
//
//    protected WebMarkupBlock officerBlock;
//    protected WebMarkupContainer officerVContainer;
//    protected String officerValue;
//    protected ReadOnlyView officerView;
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
//        this.unassignedOnBlock = new WebMarkupBlock("unassignedOnBlock", Size.Six_6);
//        this.form.add(this.unassignedOnBlock);
//        this.unassignedOnIContainer = new WebMarkupContainer("unassignedOnIContainer");
//        this.unassignedOnBlock.add(this.unassignedOnIContainer);
//        this.unassignedOnField = new DateTextField("unassignedOnField", new PropertyModel<>(this, "unassignedOnValue"));
//        this.unassignedOnField.setLabel(Model.of("Rejected On"));
//        this.unassignedOnField.setRequired(false);
//        this.unassignedOnIContainer.add(this.unassignedOnField);
//        this.unassignedOnFeedback = new TextFeedbackPanel("unassignedOnFeedback", this.unassignedOnField);
//        this.unassignedOnIContainer.add(this.unassignedOnFeedback);
//
//        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
//        this.form.add(this.officerBlock);
//        this.officerVContainer = new WebMarkupContainer("officerVContainer");
//        this.officerBlock.add(this.officerVContainer);
//        this.officerView = new ReadOnlyView("officerView", new PropertyModel<>(this, "officerValue"));
//        this.officerVContainer.add(this.officerView);
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
//        this.unassignedOnValue = DateTime.now().toDate();
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
//        selectQuery.addField(MStaff.Field.DISPLAY_NAME);
//        selectQuery.addWhere(MStaff.Field.ID + " = :" + MStaff.Field.ID, loanObject.get("loan_officer_id"));
//        this.officerValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
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
//            breadcrumb.setLabel("UnAssign Officer");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        LoanOfficerUnAssignBuilder builder = new LoanOfficerUnAssignBuilder();
//        builder.withLoanId(this.loanId);
//        builder.withUnassignedDate(this.unassignedOnValue);
//
//        JsonNode node = ClientHelper.unassignOfficerLoanAccount((Session) getSession(), builder.build());
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
