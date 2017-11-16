package com.angkorteam.fintech.pages.client.group;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.DepositType;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountSelectionPage extends Page {

    protected String groupId;

    protected Form<Void> form;
    protected Button okayButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock savingBlock;
    protected WebMarkupContainer savingIContainer;
    protected SingleChoiceProvider savingProvider;
    protected Option savingValue;
    protected Select2SingleChoice<Option> savingField;
    protected TextFeedbackPanel savingFeedback;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new Button("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initSavingBlock();
    }

    protected void initSavingBlock() {
        this.savingBlock = new WebMarkupBlock("savingBlock", Size.Six_6);
        this.form.add(this.savingBlock);
        this.savingIContainer = new WebMarkupContainer("savingIContainer");
        this.savingBlock.add(this.savingIContainer);
        this.savingProvider = new SingleChoiceProvider("m_savings_product", "id", "name");
        this.savingProvider.applyWhere("deposit_type_enum", "deposit_type_enum = " + DepositType.Saving.getLiteral());
        this.savingField = new Select2SingleChoice<>("savingField", new PropertyModel<>(this, "savingValue"), this.savingProvider);
        this.savingField.setLabel(Model.of("Product"));
        this.savingField.add(new OnChangeAjaxBehavior());
        this.savingField.setRequired(true);
        this.savingIContainer.add(this.savingField);
        this.savingFeedback = new TextFeedbackPanel("savingFeedback", this.savingField);
        this.savingIContainer.add(this.savingFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.groupId = getPageParameters().get("groupId").toString();
    }

    protected void okayButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);
        parameters.add("savingId", this.savingValue.getId());
        setResponsePage(SavingAccountCreatePage.class, parameters);
    }

}
