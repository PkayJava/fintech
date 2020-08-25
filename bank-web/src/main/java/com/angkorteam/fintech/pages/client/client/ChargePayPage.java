//package com.angkorteam.fintech.pages.client.client;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.joda.time.DateTime;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MCharge;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MClientCharge;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.client.client.ClientChargePayBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ChargePayPage extends Page {
//
//    protected String clientId;
//    protected String chargeId;
//
//    protected String clientDisplayName;
//    protected String chargeName;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock amountBlock;
//    protected UIContainer amountIContainer;
//    protected Double amountValue;
//    protected TextField<Double> amountField;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock transactionDateBlock;
//    protected UIContainer transactionDateIContainer;
//    protected Date transactionDateValue;
//    protected DateTextField transactionDateField;
//
//    protected UIBlock row2Block1;
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
//        this.amountBlock = this.row1.newUIBlock("amountBlock", Size.Six_6);
//        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
//        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
//        this.amountIContainer.add(this.amountField);
//        this.amountIContainer.newFeedback("amountFeedback", this.amountField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.transactionDateBlock = this.row2.newUIBlock("transactionDateBlock", Size.Six_6);
//        this.transactionDateIContainer = this.transactionDateBlock.newUIContainer("transactionDateIContainer");
//        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
//        this.transactionDateIContainer.add(this.transactionDateField);
//        this.transactionDateIContainer.newFeedback("transactionDateFeedback", this.transactionDateField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.amountField.setLabel(Model.of("Amount"));
//        this.transactionDateField.setLabel(Model.of("Transaction Date"));
//    }
//
//    protected void initTransactionBlock() {
//    }
//
//    @Override
//    protected void initData() {
//        this.clientId = getPageParameters().get("clientId").toString();
//        this.chargeId = getPageParameters().get("chargeId").toString();
//        this.transactionDateValue = DateTime.now().toDate();
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
//
//        this.clientDisplayName = (String) clientObject.get("display_name");
//
//        selectQuery = new SelectQuery(MClientCharge.NAME);
//        selectQuery.addField(MClientCharge.Field.AMOUNT_OUTSTANDING_DERIVED);
//        selectQuery.addField(MClientCharge.Field.AMOUNT);
//        selectQuery.addWhere(MClientCharge.Field.CHARGE_ID + " = :" + MClientCharge.Field.CHARGE_ID, this.chargeId);
//        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        selectQuery = new SelectQuery(MCharge.NAME);
//        selectQuery.addField(MCharge.Field.NAME);
//        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, this.chargeId);
//        this.chargeName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//
//        this.amountValue = chargeObject.get("amount_outstanding_derived") == null ? (Double) chargeObject.get("amount") : (Double) chargeObject.get("amount_outstanding_derived");
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
//            breadcrumb.setLabel(this.chargeName + " Pay");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        ClientChargePayBuilder builder = new ClientChargePayBuilder();
//        builder.withClientId(this.clientId);
//        builder.withChargeId(this.chargeId);
//        builder.withTransactionDate(this.transactionDateValue);
//        builder.withAmount(this.amountValue);
//
//        JsonNode node = ClientHelper.postClientChargePay((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        setResponsePage(ClientPreviewPage.class, parameters);
//    }
//
//}
