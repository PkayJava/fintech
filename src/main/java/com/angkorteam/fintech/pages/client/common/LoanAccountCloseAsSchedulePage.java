//package com.angkorteam.fintech.pages.client.common;
//
//import java.util.Date;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
//import com.angkorteam.fintech.dto.builder.loan.CloseBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class LoanAccountCloseAsSchedulePage extends Page {
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
//    protected UIRow row1;
//
//    protected UIBlock closedOnBlock;
//    protected UIContainer closedOnIContainer;
//    protected DateTextField closedOnField;
//    protected Date closedOnValue;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock noteBlock;
//    protected UIContainer noteIContainer;
//    protected String noteValue;
//    protected TextArea<String> noteField;
//
//    protected UIBlock row2Block1;
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
//        this.closedOnBlock = this.row1.newUIBlock("closedOnBlock", Size.Six_6);
//        this.closedOnIContainer = this.closedOnBlock.newUIContainer("closedOnIContainer");
//        this.closedOnField = new DateTextField("closedOnField", new PropertyModel<>(this, "closedOnValue"));
//        this.closedOnIContainer.add(this.closedOnField);
//        this.closedOnIContainer.newFeedback("closedOnFeedback", this.closedOnField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.noteBlock = this.row2.newUIBlock("noteBlock", Size.Six_6);
//        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
//        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
//        this.noteIContainer.add(this.noteField);
//        this.noteIContainer.newFeedback("noteFeedback", this.noteField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.noteField.setLabel(Model.of("Note"));
//
//        this.closedOnField.setLabel(Model.of("Closed On"));
//        this.closedOnField.setRequired(false);
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
//        CloseBuilder builder = new CloseBuilder();
//        builder.withId(this.loanId);
//        builder.withNote(this.noteValue);
//        builder.withTransactionDate(this.closedOnValue);
//
//        JsonNode node = ClientHelper.closeAsScheduleLoanAccount((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//
//        if (this.client == ClientEnum.Client) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("clientId", this.clientId);
//            setResponsePage(ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("centerId", this.centerId);
//            setResponsePage(CenterPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("groupId", this.groupId);
//            setResponsePage(GroupPreviewPage.class, parameters);
//        } else {
//            throw new WicketRuntimeException("Unknown " + this.client);
//        }
//    }
//
//}
