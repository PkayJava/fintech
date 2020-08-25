//package com.angkorteam.fintech.pages.client.client;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.client.client.ClientAssignStaffBuilder;
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
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClientAssignStaffPage extends Page {
//
//    protected String clientId;
//    protected String officeId;
//
//    protected String clientDisplayName;
//
//    protected Form<Void> form;
//    protected Button okayButton;
//
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock staffBlock;
//    protected UIContainer staffIContainer;
//    protected SingleChoiceProvider staffProvider;
//    protected Option staffValue;
//    protected Select2SingleChoice<Option> staffField;
//
//    protected UIBlock row1Block1;
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
//            breadcrumb.setLabel("Assign Staff");
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
//        this.okayButton = new Button("okayButton");
//        this.okayButton.setOnSubmit(this::okayButtonSubmit);
//        this.form.add(this.okayButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.staffBlock = this.row1.newUIBlock("staffBlock", Size.Six_6);
//        this.staffIContainer = this.staffBlock.newUIContainer("staffIContainer");
//        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
//        this.staffIContainer.add(this.staffField);
//        this.staffIContainer.newFeedback("staffFeedback", this.staffField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.staffField.setLabel(Model.of("Staff"));
//        this.staffField.add(new OnChangeAjaxBehavior());
//        this.staffField.setRequired(true);
//    }
//
//    @Override
//    protected void initData() {
//        this.clientId = getPageParameters().get("clientId").toString();
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MClient.NAME);
//        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//        selectQuery.addField(MClient.Field.OFFICE_ID);
//        selectQuery.addField(MClient.Field.DISPLAY_NAME);
//        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.officeId = String.valueOf(clientObject.get("office_id"));
//        this.clientDisplayName = (String) clientObject.get("display_name");
//
//        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.staffProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//        this.staffProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
//    }
//
//    protected void okayButtonSubmit(Button button) {
//
//        ClientAssignStaffBuilder builder = new ClientAssignStaffBuilder();
//        builder.withId(this.clientId);
//        builder.withStaffId(this.staffValue.getId());
//
//        JsonNode node = ClientHelper.assignStaffClient((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        setResponsePage(ClientPreviewPage.class, parameters);
//    }
//}
