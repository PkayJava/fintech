package com.angkorteam.fintech.pages.client.client;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class ClientSignaturePage extends Page {

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
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

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
    }

}
