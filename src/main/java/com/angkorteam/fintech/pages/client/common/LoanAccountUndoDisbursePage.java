//package com.angkorteam.fintech.pages.client.common;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import com.angkorteam.fintech.widget.WebMarkupContainer;
//import org.apache.wicket.markup.html.form.TextArea;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.loan.UndoDisburseBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanAccountUndoDisbursePage extends Page {
//
//    protected ClientEnum client;
//
//    protected String clientId;
//    protected String groupId;
//    protected String centerId;
//
//    protected String loanId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected WebMarkupBlock noteBlock;
//    protected WebMarkupContainer noteIContainer;
//    protected String noteValue;
//    protected TextArea<String> noteField;
//    protected TextFeedbackPanel noteFeedback;
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
//        initNoteBlock();
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void initNoteBlock() {
//        this.noteBlock = new WebMarkupBlock("noteBlock", Size.Six_6);
//        this.form.add(this.noteBlock);
//        this.noteIContainer = new WebMarkupContainer("noteIContainer");
//        this.noteBlock.add(this.noteIContainer);
//        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
//        this.noteField.setLabel(Model.of("Note"));
//        this.noteIContainer.add(this.noteField);
//        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
//        this.noteIContainer.add(this.noteFeedback);
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
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        UndoDisburseBuilder builder = new UndoDisburseBuilder();
//        builder.withId(this.loanId);
//        builder.withNote(this.noteValue);
//
//        JsonNode node = ClientHelper.undoDisburseLoanAccount((Session) getSession(), builder.build());
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
