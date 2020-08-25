//package com.angkorteam.fintech.pages.client.common;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextArea;
//import org.apache.wicket.markup.html.form.TextField;
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
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.loan.ApproveBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanAccountApprovePage extends Page {
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
//    protected String loanId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock approvedOnBlock;
//    protected UIContainer approvedOnIContainer;
//    protected Date approvedOnValue;
//    protected DateTextField approvedOnField;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock expectedDisbursementOnBlock;
//    protected UIContainer expectedDisbursementOnIContainer;
//    protected Date expectedDisbursementOnValue;
//    protected DateTextField expectedDisbursementOnField;
//
//    protected UIBlock row2Block1;
//
//    protected UIRow row3;
//
//    protected UIBlock approvedAmountBlock;
//    protected UIContainer approvedAmountIContainer;
//    protected Double approvedAmountValue;
//    protected TextField<Double> approvedAmountField;
//
//    protected UIBlock row3Block1;
//
//    protected UIRow row4;
//
//    protected UIBlock noteBlock;
//    protected UIContainer noteIContainer;
//    protected String noteValue;
//    protected TextArea<String> noteField;
//
//    protected UIBlock row4Block1;
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
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.approvedOnBlock = this.row1.newUIBlock("approvedOnBlock", Size.Six_6);
//        this.approvedOnIContainer = this.approvedOnBlock.newUIContainer("approvedOnIContainer");
//        this.approvedOnField = new DateTextField("approvedOnField", new PropertyModel<>(this, "approvedOnValue"));
//        this.approvedOnIContainer.add(this.approvedOnField);
//        this.approvedOnIContainer.newFeedback("approvedOnFeedback", this.approvedOnField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.expectedDisbursementOnBlock = this.row2.newUIBlock("expectedDisbursementOnBlock", Size.Six_6);
//        this.expectedDisbursementOnIContainer = this.expectedDisbursementOnBlock.newUIContainer("expectedDisbursementOnIContainer");
//        this.expectedDisbursementOnField = new DateTextField("expectedDisbursementOnField", new PropertyModel<>(this, "expectedDisbursementOnValue"));
//        this.expectedDisbursementOnIContainer.add(this.expectedDisbursementOnField);
//        this.expectedDisbursementOnIContainer.newFeedback("expectedDisbursementOnFeedback", this.expectedDisbursementOnField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.approvedAmountBlock = this.row3.newUIBlock("approvedAmountBlock", Size.Six_6);
//        this.approvedAmountIContainer = this.approvedAmountBlock.newUIContainer("approvedAmountIContainer");
//        this.approvedAmountField = new TextField<>("approvedAmountField", new PropertyModel<>(this, "approvedAmountValue"));
//        this.approvedAmountIContainer.add(this.approvedAmountField);
//        this.approvedAmountIContainer.newFeedback("approvedAmountFeedback", this.approvedAmountField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.noteBlock = this.row4.newUIBlock("noteBlock", Size.Six_6);
//        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
//        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
//        this.noteIContainer.add(this.noteField);
//        this.noteIContainer.newFeedback("noteFeedback", this.noteField);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.approvedOnField.setLabel(Model.of("Approved On"));
//        this.noteField.setLabel(Model.of("Note"));
//        this.expectedDisbursementOnField.setLabel(Model.of("Expected Disbursement On"));
//        this.approvedAmountField.setLabel(Model.of("Approved Amount"));
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
//        this.approvedOnValue = DateTime.now().toDate();
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MLoan.NAME);
//        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
//        selectQuery.addField(MLoan.Field.PRINCIPAL_AMOUNT_PROPOSED);
//        selectQuery.addField(MLoan.Field.EXPECTED_DISBURSED_ON_DATE);
//        selectQuery.addWhere(MLoan.Field.ID + " = :" + MLoan.Field.ID, this.loanId);
//        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.approvedAmountValue = (Double) loanObject.get("principal_amount_proposed");
//        this.expectedDisbursementOnValue = (Date) loanObject.get("expected_disbursedon_date");
//
//        if (this.client == ClientEnum.Client) {
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addField(MClient.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.clientDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        } else if (this.client == ClientEnum.Group) {
//            selectQuery = new SelectQuery(MGroup.NAME);
//            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
//            this.groupDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        } else if (this.client == ClientEnum.Center) {
//            selectQuery = new SelectQuery(MGroup.NAME);
//            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
//            this.centerDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//        }
//
//        this.loanAccountNo = (String) loanObject.get("account_no");
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
//            breadcrumb.setLabel("Approve");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        ApproveBuilder builder = new ApproveBuilder();
//        builder.withId(this.loanId);
//        builder.withNote(this.noteValue);
//        builder.withApprovedOnDate(this.approvedOnValue);
//        builder.withExpectedDisbursementDate(this.expectedDisbursementOnValue);
//        builder.withApprovedLoanAmount(this.approvedAmountValue);
//
//        JsonNode node = ClientHelper.approveLoanAccount((Session) getSession(), builder.build());
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
