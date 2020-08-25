//package com.angkorteam.fintech.pages.client.client;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextArea;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.client.client.ClientTransferBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClientTransferPage extends Page {
//
//    protected String clientId;
//
//    protected String clientDisplayName;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock officeBlock;
//    protected UIContainer officeIContainer;
//    protected SingleChoiceProvider officeProvider;
//    protected Option officeValue;
//    protected Select2SingleChoice<Option> officeField;
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
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            breadcrumb.setPage(ClientBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageParameters parameters = new PageParameters();
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            parameters.add("clientId", this.clientId);
//            breadcrumb.setLabel(this.clientDisplayName);
//            breadcrumb.setPage(ClientPreviewPage.class);
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Transfer");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Six_6);
//        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
//        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
//        this.officeIContainer.add(this.officeField);
//        this.officeIContainer.newFeedback("officeFeedback", this.officeField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.noteBlock = this.row2.newUIBlock("noteBlock", Size.Six_6);
//        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
//        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
//        this.noteField.setLabel(Model.of("Note"));
//        this.noteIContainer.add(this.noteField);
//        this.noteIContainer.newFeedback("noteFeedback", this.noteField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.officeField.setRequired(true);
//    }
//
//    @Override
//    protected void initData() {
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//        this.clientId = getPageParameters().get("clientId").toString();
//        SelectQuery selectQuery = null;
//        selectQuery = new SelectQuery(MClient.NAME);
//        selectQuery.addField(MClient.Field.OFFICE_ID);
//        selectQuery.addField(MClient.Field.DISPLAY_NAME);
//        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        this.clientDisplayName = (String) clientObject.get("display_name");
//
//        selectQuery = new SelectQuery(MOffice.NAME);
//        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, clientObject.get("office_id"));
//        selectQuery.addField(MOffice.Field.ID + " as id");
//        selectQuery.addField(MOffice.Field.NAME + " as text");
//        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//
//        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//
//        ClientTransferBuilder builder = new ClientTransferBuilder();
//        builder.withId(this.clientId);
//        if (this.officeValue != null) {
//            builder.withDestinationOfficeId(this.officeValue.getId());
//        }
//        builder.withNote(this.noteValue);
//
//        JsonNode node = ClientHelper.transferClient((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        setResponsePage(ClientPreviewPage.class, parameters);
//
//    }
//}
