package com.angkorteam.fintech.pages.product.mixed;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.MixedBuilder;
import com.angkorteam.fintech.helper.MixedHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class MixedCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private WebMarkupContainer productBlock;
    private WebMarkupContainer productContainer;
    private SingleChoiceProvider productProvider;
    private Option productValue;
    private Select2SingleChoice<Option> productField;
    private TextFeedbackPanel productFeedback;

    private WebMarkupContainer restrictedBlock;
    private WebMarkupContainer restrictedContainer;
    private MultipleChoiceProvider restrictedProvider;
    private List<Option> restrictedValue;
    private Select2MultipleChoice<Option> restrictedField;
    private TextFeedbackPanel restrictedFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", MixedBrowsePage.class);
        this.form.add(this.closeLink);

        this.productBlock = new WebMarkupContainer("productBlock");
        this.form.add(this.productBlock);
        this.productContainer = new WebMarkupContainer("productContainer");
        this.productBlock.add(this.productContainer);
        this.productProvider = new SingleChoiceProvider("m_product_loan", "id", "name");
        this.productProvider.applyWhere("product", "m_product_loan.id not in (select product_id from m_product_mix)");
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "productValue"), this.productProvider);
        this.productField.setLabel(Model.of("Product"));
        this.productField.setRequired(false);
        this.productField.add(new OnChangeAjaxBehavior());
        this.productContainer.add(this.productField);
        this.productFeedback = new TextFeedbackPanel("productFeedback", this.productField);
        this.productContainer.add(this.productFeedback);

        this.restrictedBlock = new WebMarkupContainer("restrictedBlock");
        this.form.add(this.restrictedBlock);
        this.restrictedContainer = new WebMarkupContainer("restrictedContainer");
        this.restrictedBlock.add(this.restrictedContainer);
        this.restrictedProvider = new MultipleChoiceProvider("m_product_loan", "id", "name");
        this.restrictedField = new Select2MultipleChoice<>("restrictedField", new PropertyModel<>(this, "restrictedValue"), this.restrictedProvider);
        this.restrictedField.setLabel(Model.of("Restricted"));
        this.restrictedField.setRequired(false);
        this.restrictedField.add(new OnChangeAjaxBehavior());
        this.restrictedContainer.add(this.restrictedField);
        this.restrictedFeedback = new TextFeedbackPanel("restrictedFeedback", this.restrictedField);
        this.restrictedContainer.add(this.restrictedFeedback);
    }

    private void saveButtonSubmit(Button button) {
        MixedBuilder builder = new MixedBuilder();

        if (this.productValue != null) {
            builder.withLoanId(this.productValue.getId());
        }

        if (this.restrictedValue != null && !this.restrictedValue.isEmpty()) {
            for (Option restricted : this.restrictedValue) {
                builder.withRestrictedProduct(Integer.valueOf(restricted.getId()));
            }
        }

        JsonNode node = null;
        try {
            node = MixedHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(MixedBrowsePage.class);
    }

}
