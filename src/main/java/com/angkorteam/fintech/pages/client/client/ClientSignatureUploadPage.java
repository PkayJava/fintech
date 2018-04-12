package com.angkorteam.fintech.pages.client.client;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientSignatureUploadPage extends Page {

    protected String clientId;

    protected String clientDisplayName;

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
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.clientDisplayName = (String) clientObject.get("display_name");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Upload Signature");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        try {
            JsonNode node = ClientHelper.postClientDocument((Session) getSession(), this.clientId, nameValue, descriptionValue, tempFile);
            if (reportError(node)) {
                return;
            }
        } finally {
            try {
                FileUtils.deleteDirectory(temp);
            } catch (IOException e) {
            }
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);

    }

}
