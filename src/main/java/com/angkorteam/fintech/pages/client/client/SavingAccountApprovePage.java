package com.angkorteam.fintech.pages.client.client;

import java.util.Date;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.acount.ApproveBuilder;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountApprovePage extends Page {

    protected String clientId;
    protected String accountId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock approvedOnBlock;
    protected WebMarkupContainer approvedOnIContainer;
    protected Date approvedOnValue;
    protected DateTextField approvedOnField;
    protected TextFeedbackPanel approvedOnFeedback;

    protected WebMarkupBlock noteBlock;
    protected WebMarkupContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initApprovedOnBlock();

        initNoteBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
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
        this.clientId = getPageParameters().get("clientId").toString();
        this.accountId = getPageParameters().get("accountId").toString();
        this.approvedOnValue = DateTime.now().toDate();
    }

    protected void saveButtonSubmit(Button button) {
        ApproveBuilder builder = new ApproveBuilder();
        builder.withId(this.accountId);
        builder.withNote(this.noteValue);
        builder.withApprovedOnDate(this.approvedOnValue);

        JsonNode node = null;
        try {
            node = ClientHelper.approveSavingAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
