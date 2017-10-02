package com.angkorteam.fintech.pages.client.center;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.DepositType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterProductPage extends Page {

    protected String centerId;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer productBlock;
    protected WebMarkupContainer productContainer;
    protected SingleChoiceProvider productProvider;
    protected Option productValue;
    protected Select2SingleChoice<Option> productField;
    protected TextFeedbackPanel productFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.productBlock = new WebMarkupContainer("productBlock");
        this.form.add(this.productBlock);
        this.productContainer = new WebMarkupContainer("productContainer");
        this.productBlock.add(this.productContainer);

        this.productProvider = new SingleChoiceProvider("m_savings_product", "id", "name");
        this.productProvider.applyWhere("deposit_type_enum", "deposit_type_enum = " + DepositType.Saving.getLiteral());
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "productValue"), this.productProvider);
        this.productField.setLabel(Model.of("Product"));
        this.productField.add(new OnChangeAjaxBehavior());
        this.productField.setRequired(true);
        this.productContainer.add(this.productField);
        this.productFeedback = new TextFeedbackPanel("productFeedback", this.productField);
        this.productContainer.add(this.productFeedback);
    }

    protected void initData() {
        this.centerId = getPageParameters().get("centerId").toString();
    }

    protected void okayButtonSubmit(Button button) {

    }

}
