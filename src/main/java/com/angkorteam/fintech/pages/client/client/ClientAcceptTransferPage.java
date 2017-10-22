package com.angkorteam.fintech.pages.client.client;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.client.client.ClientAcceptTransferBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientAcceptTransferPage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer noteBlock;
    protected WebMarkupContainer noteContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.noteBlock = new WebMarkupContainer("noteBlock");
        this.noteBlock.setOutputMarkupId(true);
        this.form.add(this.noteBlock);
        this.noteContainer = new WebMarkupContainer("noteContainer");
        this.noteBlock.add(this.noteContainer);
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setLabel(Model.of("Note"));
        this.noteContainer.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.noteContainer.add(this.noteFeedback);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    protected void saveButtonSubmit(Button button) {

        ClientAcceptTransferBuilder builder = new ClientAcceptTransferBuilder();
        builder.withId(this.clientId);
        builder.withNote(this.noteValue);

        JsonNode node = null;
        try {
            node = ClientHelper.acceptTransferClient((Session) getSession(), builder.build());
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
