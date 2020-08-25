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
//import com.angkorteam.fintech.ddl.MCharge;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.ChargeType;
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
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ChargeSelectionPage extends Page {
//
//    protected String clientId;
//    protected String clientDisplayName;
//
//    protected Form<Void> form;
//    protected Button okayButton;
//
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock chargeBlock;
//    protected UIContainer chargeIContainer;
//    protected SingleChoiceProvider chargeProvider;
//    protected Option chargeValue;
//    protected Select2SingleChoice<Option> chargeField;
//
//    protected UIBlock row1Block1;
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
//        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Four_4);
//        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
//        this.chargeField = new Select2SingleChoice<>("chargeField", new PropertyModel<>(this, "chargeValue"), this.chargeProvider);
//        this.chargeIContainer.add(this.chargeField);
//        this.chargeIContainer.newFeedback("chargeFeedback", this.chargeField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Eight_8);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.chargeField.setLabel(Model.of("Charge"));
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
//        selectQuery.addField(MClient.Field.OFFICE_ID);
//        selectQuery.addField(MClient.Field.DISPLAY_NAME);
//        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.clientDisplayName = (String) clientObject.get("display_name");
//
//        this.chargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME, "CONCAT(" + MCharge.Field.NAME + ", ' [', " + MCharge.Field.CURRENCY_CODE + ", ']')");
//        this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Client.getLiteral());
//        this.chargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
//    }
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
//            breadcrumb.setLabel("Charge Selection");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    protected void okayButtonSubmit(Button button) {
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        parameters.add("chargeId", this.chargeValue.getId());
//        setResponsePage(ChargeCreatePage.class, parameters);
//    }
//}
