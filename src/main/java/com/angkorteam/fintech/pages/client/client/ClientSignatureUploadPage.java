package com.angkorteam.fintech.pages.client.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientSignatureUploadPage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock fileBlock;
    protected WebMarkupContainer fileIContainer;
    protected List<FileUpload> fileValue;
    protected FileUploadField fileField;
    protected TextFeedbackPanel fileFeedback;

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

        initFileBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initFileBlock() {
        this.fileBlock = new WebMarkupBlock("fileBlock", Size.Six_6);
        this.form.add(this.fileBlock);
        this.fileIContainer = new WebMarkupContainer("fileIContainer");
        this.fileBlock.add(this.fileIContainer);
        this.fileField = new FileUploadField("fileField", new PropertyModel<>(this, "fileValue"));
        this.fileField.setRequired(true);
        this.fileField.setLabel(Model.of("Picture"));
        this.fileIContainer.add(this.fileField);
        this.fileFeedback = new TextFeedbackPanel("fileFeedback", this.fileField);
        this.fileIContainer.add(this.fileFeedback);
    }

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    protected void saveButtonSubmit(Button button) {

        String nameValue = "clientSignature";
        String descriptionValue = "client signature";

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        File temp = new File(FileUtils.getTempDirectory(), generator.externalId());
        temp.mkdir();
        File tempFile = new File(temp, this.fileValue.get(0).getClientFileName());
        try {
            this.fileValue.get(0).writeTo(tempFile);
        } catch (Exception e) {
            return;
        }

        JsonNode node = null;
        try {
            node = ClientHelper.postClientDocument((Session) getSession(), this.clientId, nameValue, descriptionValue, tempFile);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        } finally {
            try {
                FileUtils.deleteDirectory(temp);
            } catch (IOException e) {
            }
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);

    }

}