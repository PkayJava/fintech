//package com.angkorteam.fintech.pages.payment;
//
//import java.util.List;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.PaymentTypeBuilder;
//import com.angkorteam.fintech.helper.PaymentTypeHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.OrganizationDashboardPage;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class PaymentTypeCreatePage extends Page {
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIRow row2;
//
//    protected UIBlock descriptionBlock;
//    protected UIContainer descriptionIContainer;
//    protected String descriptionValue;
//    protected TextField<String> descriptionField;
//
//    protected UIRow row3;
//
//    protected UIBlock cashBlock;
//    protected UIContainer cashIContainer;
//    protected Boolean cashValue;
//    protected CheckBox cashField;
//
//    protected UIRow row4;
//
//    protected UIBlock positionBlock;
//    protected UIContainer positionIContainer;
//    protected Long positionValue;
//    protected TextField<Long> positionField;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Organization");
//            breadcrumb.setPage(OrganizationDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Payment");
//            breadcrumb.setPage(PaymentTypeBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Payment Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", PaymentTypeBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Twelve_12);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.descriptionBlock = this.row2.newUIBlock("descriptionBlock", Size.Twelve_12);
//        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
//        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
//        this.descriptionIContainer.add(this.descriptionField);
//        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.cashBlock = this.row3.newUIBlock("cashBlock", Size.Twelve_12);
//        this.cashIContainer = this.cashBlock.newUIContainer("cashIContainer");
//        this.cashField = new CheckBox("cashField", new PropertyModel<>(this, "cashValue"));
//        this.cashIContainer.add(this.cashField);
//        this.cashIContainer.newFeedback("cashFeedback", this.cashField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.positionBlock = this.row4.newUIBlock("positionBlock", Size.Twelve_12);
//        this.positionIContainer = this.positionBlock.newUIContainer("positionIContainer");
//        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
//        this.positionIContainer.add(this.positionField);
//        this.positionIContainer.newFeedback("positionFeedback", this.positionField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.positionField.setRequired(true);
//        this.cashField.setRequired(true);
//        this.nameField.setRequired(true);
//        this.descriptionField.setRequired(true);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        PaymentTypeBuilder builder = new PaymentTypeBuilder();
//        builder.withName(this.nameValue);
//        builder.withPosition(this.positionValue);
//        builder.withCashPayment(this.cashValue);
//        builder.withDescription(this.descriptionValue);
//
//        JsonNode node = PaymentTypeHelper.create((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(PaymentTypeBrowsePage.class);
//    }
//
//}
