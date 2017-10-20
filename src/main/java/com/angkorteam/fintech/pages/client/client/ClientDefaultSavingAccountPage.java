package com.angkorteam.fintech.pages.client.client;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientDefaultSavingAccountPage extends Page {

    protected String clientId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer savingBlock;
    protected WebMarkupContainer savingContainer;
    protected SingleChoiceProvider savingProvider;
    protected Option savingValue;
    protected Select2SingleChoice<Option> savingField;
    protected TextFeedbackPanel savingFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.savingBlock = new WebMarkupContainer("savingBlock");
        this.form.add(this.savingBlock);
        this.savingContainer = new WebMarkupContainer("savingContainer");
        this.savingBlock.add(this.savingContainer);
        this.savingProvider = new SingleChoiceProvider("m_savings_account", "m_savings_account.id", "m_savings_account.account_no", "concat(m_savings_account.account_no, ' => ', m_savings_product.name)");
        this.savingProvider.addJoin("INNER JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.savingProvider.applyWhere("status_enum", "m_savings_account.status_enum = 300");
        this.savingProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);
        this.savingField = new Select2SingleChoice<>("savingField", new PropertyModel<>(this, "savingValue"), this.savingProvider);
        this.savingField.setRequired(true);
        this.savingField.setLabel(Model.of("Saving Account"));
        this.savingContainer.add(this.savingField);
        this.savingFeedback = new TextFeedbackPanel("savingFeedback", this.savingField);
        this.savingContainer.add(this.savingFeedback);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    protected void saveButtonSubmit(Button button) {
    }
}
