//package com.angkorteam.fintech.pages.tax;
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
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.ddl.MTaxComponent;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.TaxComponentBuilder;
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.helper.TaxComponentHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.ProductDashboardPage;
//import com.angkorteam.fintech.pages.TaxDashboardPage;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 7/16/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class TaxComponentModifyPage extends Page {
//
//    protected String taxId;
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
//    protected UIBlock percentageBlock;
//    protected UIContainer percentageIContainer;
//    protected Double percentageValue;
//    protected TextField<Double> percentageField;
//
//    protected UIRow row3;
//
//    protected UIBlock accountTypeBlock;
//    protected UIContainer accountTypeVContainer;
//    protected Option accountTypeValue;
//    protected ReadOnlyView accountTypeView;
//
//    protected UIRow row4;
//
//    protected UIBlock accountBlock;
//    protected UIContainer accountVContainer;
//    protected Option accountValue;
//    protected ReadOnlyView accountView;
//
//    protected UIRow row5;
//
//    protected UIBlock startDateBlock;
//    protected UIContainer startDateIContainer;
//    protected Date startDateValue;
//    protected DateTextField startDateField;
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
//            breadcrumb.setLabel("Product");
//            breadcrumb.setPage(ProductDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Tax");
//            breadcrumb.setPage(TaxDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Tax Component");
//            breadcrumb.setPage(TaxComponentBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Tax Component Modify");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        PageParameters parameters = getPageParameters();
//        this.taxId = parameters.get("taxId").toString("");
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MTaxComponent.NAME);
//        selectQuery.addWhere(MTaxComponent.Field.ID + " = :" + MTaxComponent.Field.ID, this.taxId);
//        selectQuery.addField(MTaxComponent.Field.NAME);
//        selectQuery.addField(MTaxComponent.Field.PERCENTAGE);
//        selectQuery.addField(MTaxComponent.Field.CREDIT_ACCOUNT_TYPE_ENUM);
//        selectQuery.addField(MTaxComponent.Field.CREDIT_ACCOUNT_ID);
//        selectQuery.addField(MTaxComponent.Field.START_DATE);
//        Map<String, Object> taxObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        this.nameValue = (String) taxObject.get(MTaxComponent.Field.NAME);
//        this.percentageValue = (Double) taxObject.get(MTaxComponent.Field.PERCENTAGE);
//        this.accountTypeValue = AccountType.optionLiteral(String.valueOf(taxObject.get(MTaxComponent.Field.CREDIT_ACCOUNT_TYPE_ENUM)));
//
//        selectQuery = new SelectQuery(AccGLAccount.NAME);
//        selectQuery.addField(AccGLAccount.Field.ID);
//        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
//        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, taxObject.get(MTaxComponent.Field.CREDIT_ACCOUNT_ID));
//        this.accountValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//        this.startDateValue = (Date) taxObject.get(MTaxComponent.Field.START_DATE);
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
//        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxComponentBrowsePage.class);
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
//        this.percentageBlock = this.row2.newUIBlock("percentageBlock", Size.Twelve_12);
//        this.percentageIContainer = this.percentageBlock.newUIContainer("percentageIContainer");
//        this.percentageField = new TextField<>("percentageField", new PropertyModel<>(this, "percentageValue"));
//        this.percentageIContainer.add(this.percentageField);
//        this.percentageIContainer.newFeedback("percentageFeedback", this.percentageField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.accountTypeBlock = this.row3.newUIBlock("accountTypeBlock", Size.Twelve_12);
//        this.accountTypeVContainer = this.accountTypeBlock.newUIContainer("accountTypeVContainer");
//        this.accountTypeView = new ReadOnlyView("accountTypeView", new PropertyModel<>(this, "accountTypeValue.text"));
//        this.accountTypeVContainer.add(this.accountTypeView);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.accountBlock = this.row4.newUIBlock("accountBlock", Size.Twelve_12);
//        this.accountVContainer = this.accountBlock.newUIContainer("accountVContainer");
//        this.accountView = new ReadOnlyView("accountView", new PropertyModel<>(this, "accountValue.text"));
//        this.accountVContainer.add(this.accountView);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.startDateBlock = this.row5.newUIBlock("startDateBlock", Size.Twelve_12);
//        this.startDateIContainer = this.startDateBlock.newUIContainer("startDateIContainer");
//        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
//        this.startDateIContainer.add(this.startDateField);
//        this.startDateIContainer.newFeedback("startDateFeedback", this.startDateField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.startDateField.setRequired(true);
//        this.percentageField.setRequired(true);
//        this.nameField.setRequired(true);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        TaxComponentBuilder builder = new TaxComponentBuilder();
//        builder.withId(this.taxId);
//        builder.withName(this.nameValue);
//        builder.withPercentage(this.percentageValue);
//        builder.withStartDate(this.startDateValue);
//        JsonNode node = TaxComponentHelper.update((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(TaxComponentBrowsePage.class);
//    }
//
//}
