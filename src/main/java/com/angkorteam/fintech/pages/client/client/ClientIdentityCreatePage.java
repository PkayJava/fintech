package com.angkorteam.fintech.pages.client.client;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.client.client.ClientIdentityBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.CustomerIdentifierProvider;
import com.angkorteam.fintech.provider.OptionProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientIdentityCreatePage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock documentTypeBlock;
    protected UIContainer documentTypeIContainer;
    protected CustomerIdentifierProvider documentTypeProvider;
    protected Option documentTypeValue;
    protected Select2SingleChoice<Option> documentTypeField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock statusBlock;
    protected UIContainer statusIContainer;
    protected OptionProvider statusProvider;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected UIBlock uniqueIdBlock;
    protected UIContainer uniqueIdIContainer;
    protected String uniqueIdValue;
    protected TextField<String> uniqueIdField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock descriptionBlock;
    protected UIContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;

    protected UIBlock row4Block1;

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
            breadcrumb.setLabel("Identity Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.documentTypeBlock = this.row1.newUIBlock("documentTypeBlock", Size.Six_6);
        this.documentTypeIContainer = this.documentTypeBlock.newUIContainer("documentTypeIContainer");
        this.documentTypeField = new Select2SingleChoice<>("documentTypeField", new PropertyModel<>(this, "documentTypeValue"), this.documentTypeProvider);
        this.documentTypeIContainer.add(this.documentTypeField);
        this.documentTypeIContainer.newFeedback("documentTypeFeedback", this.documentTypeField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.statusBlock = this.row2.newUIBlock("statusBlock", Size.Six_6);
        this.statusIContainer = this.statusBlock.newUIContainer("statusIContainer");
        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusIContainer.add(this.statusField);
        this.statusIContainer.newFeedback("statusFeedback", this.statusField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.uniqueIdBlock = this.row3.newUIBlock("uniqueIdBlock", Size.Six_6);
        this.uniqueIdIContainer = this.uniqueIdBlock.newUIContainer("uniqueIdIContainer");
        this.uniqueIdField = new TextField<>("uniqueIdField", new PropertyModel<>(this, "uniqueIdValue"));
        this.uniqueIdIContainer.add(this.uniqueIdField);
        this.uniqueIdIContainer.newFeedback("uniqueIdFeedback", this.uniqueIdField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.descriptionBlock = this.row4.newUIBlock("descriptionBlock", Size.Six_6);
        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.descriptionField.setLabel(Model.of("Description"));
        this.uniqueIdField.setLabel(Model.of("Unique ID#"));
        this.documentTypeField.setLabel(Model.of("Document Type"));
        this.statusField.setLabel(Model.of("Status"));
    }

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        this.statusValue = new Option("Active", "Active");

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.clientDisplayName = (String) clientObject.get("display_name");

        this.documentTypeProvider = new CustomerIdentifierProvider();

        this.statusProvider = new OptionProvider(new Option("Active", "Active"), new Option("Inactive", "Inactive"));
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

        JsonNode node = ClientHelper.createClientIdentity((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        parameters.add("tab", ClientPreviewPage.CLIENT_PREVIEW_IDENTITY_INDEX);
        setResponsePage(ClientPreviewPage.class, parameters);
    }
}
