package com.angkorteam.fintech.pages.product.mixed;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MProductLoan;
import com.angkorteam.fintech.ddl.MProductMix;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.MixedBuilder;
import com.angkorteam.fintech.helper.MixedHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class MixedCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock productBlock;
    protected WebMarkupContainer productIContainer;
    protected SingleChoiceProvider productProvider;
    protected Option productValue;
    protected Select2SingleChoice<Option> productField;
    protected TextFeedbackPanel productFeedback;

    protected WebMarkupBlock restrictedBlock;
    protected WebMarkupContainer restrictedIContainer;
    protected MultipleChoiceProvider restrictedProvider;
    protected List<Option> restrictedValue;
    protected Select2MultipleChoice<Option> restrictedField;
    protected TextFeedbackPanel restrictedFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Mixed Product");
            breadcrumb.setPage(MixedBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Mixed Product Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", MixedBrowsePage.class);
        this.form.add(this.closeLink);

        initProductBlock();

        initRestrictedBlock();
    }

    protected void initRestrictedBlock() {
        this.restrictedBlock = new WebMarkupBlock("restrictedBlock", Size.Six_6);
        this.form.add(this.restrictedBlock);
        this.restrictedIContainer = new WebMarkupContainer("restrictedIContainer");
        this.restrictedBlock.add(this.restrictedIContainer);
        this.restrictedProvider = new MultipleChoiceProvider(MProductLoan.NAME, MProductLoan.Field.ID, MProductLoan.Field.NAME);
        this.restrictedField = new Select2MultipleChoice<>("restrictedField", new PropertyModel<>(this, "restrictedValue"), this.restrictedProvider);
        this.restrictedField.setLabel(Model.of("Restricted"));
        this.restrictedField.setRequired(false);
        this.restrictedField.add(new OnChangeAjaxBehavior());
        this.restrictedIContainer.add(this.restrictedField);
        this.restrictedFeedback = new TextFeedbackPanel("restrictedFeedback", this.restrictedField);
        this.restrictedIContainer.add(this.restrictedFeedback);
    }

    protected void initProductBlock() {
        this.productBlock = new WebMarkupBlock("productBlock", Size.Six_6);
        this.form.add(this.productBlock);
        this.productIContainer = new WebMarkupContainer("productIContainer");
        this.productBlock.add(this.productIContainer);
        this.productProvider = new SingleChoiceProvider(MProductLoan.NAME, MProductLoan.Field.ID, MProductLoan.Field.NAME);
        this.productProvider.applyWhere("product", MProductLoan.NAME + "." + MProductLoan.Field.ID + " NOT IN (SELECT " + MProductMix.Field.PRODUCT_ID + " FROM " + MProductMix.NAME + ")");
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "productValue"), this.productProvider);
        this.productField.setLabel(Model.of("Product"));
        this.productField.setRequired(false);
        this.productField.add(new OnChangeAjaxBehavior());
        this.productIContainer.add(this.productField);
        this.productFeedback = new TextFeedbackPanel("productFeedback", this.productField);
        this.productIContainer.add(this.productFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        MixedBuilder builder = new MixedBuilder();

        if (this.productValue != null) {
            builder.withLoanId(this.productValue.getId());
        }

        if (this.restrictedValue != null && !this.restrictedValue.isEmpty()) {
            for (Option restricted : this.restrictedValue) {
                builder.withRestrictedProduct(Long.valueOf(restricted.getId()));
            }
        }

        JsonNode node = MixedHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(MixedBrowsePage.class);
    }

}
