package com.angkorteam.fintech.pages.client.client;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.client.client.ClientIdentityBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.CustomerIdentifierProvider;
import com.angkorteam.fintech.provider.OptionProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientIdentityCreatePage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock documentTypeBlock;
    protected WebMarkupContainer documentTypeIContainer;
    protected CustomerIdentifierProvider documentTypeProvider;
    protected Option documentTypeValue;
    protected Select2SingleChoice<Option> documentTypeField;
    protected TextFeedbackPanel documentTypeFeedback;

    protected WebMarkupBlock statusBlock;
    protected WebMarkupContainer statusIContainer;
    protected OptionProvider statusProvider;
    protected Option statusValue = new Option("Active", "Active");
    protected Select2SingleChoice<Option> statusField;
    protected TextFeedbackPanel statusFeedback;

    protected WebMarkupBlock uniqueIdBlock;
    protected WebMarkupContainer uniqueIdIContainer;
    protected String uniqueIdValue;
    protected TextField<String> uniqueIdField;
    protected TextFeedbackPanel uniqueIdFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

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

        initDocumentTypeBlock();

        initStatusBlock();

        initUniqueIdBlock();

        initDescriptionBlock();
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Six_6);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initUniqueIdBlock() {
        this.uniqueIdBlock = new WebMarkupBlock("uniqueIdBlock", Size.Six_6);
        this.form.add(this.uniqueIdBlock);
        this.uniqueIdIContainer = new WebMarkupContainer("uniqueIdIContainer");
        this.uniqueIdBlock.add(this.uniqueIdIContainer);
        this.uniqueIdField = new TextField<>("uniqueIdField", new PropertyModel<>(this, "uniqueIdValue"));
        this.uniqueIdField.setLabel(Model.of("Unique ID#"));
        this.uniqueIdIContainer.add(this.uniqueIdField);
        this.uniqueIdFeedback = new TextFeedbackPanel("uniqueIdFeedback", this.uniqueIdField);
        this.uniqueIdIContainer.add(this.uniqueIdFeedback);
    }

    protected void initStatusBlock() {
        this.statusBlock = new WebMarkupBlock("statusBlock", Size.Six_6);
        this.form.add(this.statusBlock);
        this.statusIContainer = new WebMarkupContainer("statusIContainer");
        this.statusBlock.add(this.statusIContainer);
        this.statusProvider = new OptionProvider(new Option("Active", "Active"), new Option("Inactive", "Inactive"));
        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusField.setLabel(Model.of("Status"));
        this.statusIContainer.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.statusIContainer.add(this.statusFeedback);
    }

    protected void initDocumentTypeBlock() {
        this.documentTypeProvider = new CustomerIdentifierProvider();
        this.documentTypeBlock = new WebMarkupBlock("documentTypeBlock", Size.Six_6);
        this.form.add(this.documentTypeBlock);
        this.documentTypeIContainer = new WebMarkupContainer("documentTypeIContainer");
        this.documentTypeBlock.add(this.documentTypeIContainer);
        this.documentTypeField = new Select2SingleChoice<>("documentTypeField", new PropertyModel<>(this, "documentTypeValue"), this.documentTypeProvider);
        this.documentTypeField.setLabel(Model.of("Document Type"));
        this.documentTypeIContainer.add(this.documentTypeField);
        this.documentTypeFeedback = new TextFeedbackPanel("documentTypeFeedback", this.documentTypeField);
        this.documentTypeIContainer.add(this.documentTypeFeedback);
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

    protected void saveButtonSubmit(Button button) {
        ClientIdentityBuilder builder = new ClientIdentityBuilder();
        builder.withClientId(this.clientId);
        builder.withDocumentKey(this.uniqueIdValue);
        if (this.statusValue != null) {
            builder.withStatus(this.statusValue.getText());
        }
        builder.withDescription(this.descriptionValue);
        if (this.documentTypeValue != null) {
            builder.withDocumentTypeId(documentTypeValue.getId());
        }

        JsonNode node = null;
        try {
            node = ClientHelper.createIdentityClient((Session) getSession(), builder.build());
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
