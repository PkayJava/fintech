//package com.angkorteam.fintech.pages.account;
//
//import java.util.Date;
//import java.util.List;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.AccrualBuilder;
//import com.angkorteam.fintech.helper.AccrualHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.AccountingPage;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/27/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class AccrualAccountingPage extends Page {
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock tillDateBlock;
//    protected UIContainer tillDateIContainer;
//    protected Date tillDateValue;
//    protected DateTextField tillDateField;
//
//    protected UIBlock row1Block1;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Accounting");
//            breadcrumb.setPage(AccountingPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Periodic Accrual Accounting");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.tillDateValue = new Date();
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
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ClosureBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.tillDateBlock = this.row1.newUIBlock("tillDateBlock", Size.Four_4);
//        this.tillDateIContainer = this.tillDateBlock.newUIContainer("tillDateIContainer");
//        this.tillDateField = new DateTextField("tillDateField", new PropertyModel<>(this, "tillDateValue"));
//        this.tillDateIContainer.add(this.tillDateField);
//        this.tillDateIContainer.newFeedback("tillDateFeedback", this.tillDateField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Eight_8);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.tillDateField.setRequired(true);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        AccrualBuilder builder = new AccrualBuilder();
//        builder.withTillDate(this.tillDateValue);
//        JsonNode node = AccrualHelper.submit((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(AccountingPage.class);
//
//    }
//
//}
