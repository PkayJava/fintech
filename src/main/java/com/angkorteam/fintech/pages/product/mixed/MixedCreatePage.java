package com.angkorteam.fintech.pages.product.mixed;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class MixedCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock productBlock;
    protected UIContainer productIContainer;
    protected SingleChoiceProvider productProvider;
    protected Option productValue;
    protected Select2SingleChoice<Option> productField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock restrictedBlock;
    protected UIContainer restrictedIContainer;
    protected MultipleChoiceProvider restrictedProvider;
    protected List<Option> restrictedValue;
    protected Select2MultipleChoice<Option> restrictedField;

    protected UIBlock row2Block1;

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
        this.productProvider = new SingleChoiceProvider(MProductLoan.NAME, MProductLoan.Field.ID, MProductLoan.Field.NAME);
        this.productProvider.applyWhere("product", MProductLoan.NAME + "." + MProductLoan.Field.ID + " NOT IN (SELECT " + MProductMix.Field.PRODUCT_ID + " FROM " + MProductMix.NAME + ")");

        this.restrictedProvider = new MultipleChoiceProvider(MProductLoan.NAME, MProductLoan.Field.ID, MProductLoan.Field.NAME);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.productBlock = this.row1.newUIBlock("productBlock", Size.Six_6);
        this.productIContainer = this.productBlock.newUIContainer("productIContainer");
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "productValue"), this.productProvider);
        this.productIContainer.add(this.productField);
        this.productIContainer.newFeedback("productFeedback", this.productField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.restrictedBlock = this.row2.newUIBlock("restrictedBlock", Size.Six_6);
        this.restrictedIContainer = this.restrictedBlock.newUIContainer("restrictedIContainer");
        this.restrictedField = new Select2MultipleChoice<>("restrictedField", new PropertyModel<>(this, "restrictedValue"), this.restrictedProvider);
        this.restrictedIContainer.add(this.restrictedField);
        this.restrictedIContainer.newFeedback("restrictedFeedback", this.restrictedField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.productField.setLabel(Model.of("Product"));
        this.productField.add(new OnChangeAjaxBehavior());

        this.restrictedField.setLabel(Model.of("Restricted"));
        this.restrictedField.add(new OnChangeAjaxBehavior());
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
