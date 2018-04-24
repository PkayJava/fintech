package com.angkorteam.fintech.pages.client.group;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.dto.enums.DepositType;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class GroupLoanPage extends Page {

    protected String groupId;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock productBlock;
    protected UIContainer productIContainer;
    protected SingleChoiceProvider productProvider;
    protected Option productValue;
    protected Select2SingleChoice<Option> productField;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.productBlock = this.row1.newUIBlock("productBlock", Size.Six_6);
        this.productIContainer = this.productBlock.newUIContainer("productIContainer");
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "productValue"), this.productProvider);
        this.productIContainer.add(this.productField);
        this.productIContainer.newFeedback("productFeedback", this.productField);
    }

    @Override
    protected void configureMetaData() {
        this.productField.setLabel(Model.of("Product"));
        this.productField.add(new OnChangeAjaxBehavior());
        this.productField.setRequired(true);
    }

    @Override
    protected void initData() {
        this.groupId = getPageParameters().get("groupId").toString();

        this.productProvider = new SingleChoiceProvider(MSavingsProduct.NAME, MSavingsProduct.Field.ID, MSavingsProduct.Field.NAME);
        this.productProvider.applyWhere("deposit_type_enum", MSavingsProduct.Field.DEPOSIT_TYPE_ENUM + " = " + DepositType.Saving.getLiteral());

    }

    protected void okayButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);
        parameters.add("productId", this.productValue.getId());
        // setResponsePage(SavingAccountCreatePage.class, parameters);
    }

}
