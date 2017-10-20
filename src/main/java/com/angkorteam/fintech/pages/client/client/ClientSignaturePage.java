package com.angkorteam.fintech.pages.client.client;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class ClientSignaturePage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer fileBlock;
    protected WebMarkupContainer fileContainer;
    protected List<FileUpload> fileValue;
    protected FileUploadField fileField;
    protected TextFeedbackPanel fileFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.fileBlock = new WebMarkupContainer("fileBlock");
        this.form.add(this.fileBlock);
        this.fileContainer = new WebMarkupContainer("fileContainer");
        this.fileBlock.add(this.fileContainer);
        this.fileField = new FileUploadField("fileField", new PropertyModel<>(this, "fileValue"));
        this.fileField.setRequired(true);
        this.fileField.setLabel(Model.of("file Account"));
        this.fileContainer.add(this.fileField);
        this.fileFeedback = new TextFeedbackPanel("fileFeedback", this.fileField);
        this.fileContainer.add(this.fileFeedback);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    protected void saveButtonSubmit(Button button) {
    }

}
