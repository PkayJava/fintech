package com.angkorteam.fintech.pages.client;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer officeBlock;
    protected WebMarkupContainer officeContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupContainer staffBlock;
    protected WebMarkupContainer staffContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;
    
    protected WebMarkupContainer legalFormBlock;
    protected WebMarkupContainer legalFormContainer;
    protected SingleChoiceProvider legalFormProvider;
    protected Option legalFormValue;
    protected Select2SingleChoice<Option> legalFormField;
    protected TextFeedbackPanel legalFormFeedback;
    
    

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.officeBlock = new WebMarkupContainer("officeBlock");
        this.form.add(this.officeBlock);
        this.officeContainer = new WebMarkupContainer("officeContainer");
        this.officeBlock.add(this.officeContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior());
        this.officeContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeContainer.add(this.officeFeedback);

        this.staffBlock = new WebMarkupContainer("staffBlock");
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffContainer.add(this.staffFeedback);

    }

    protected void saveButtonSubmit(Button button) {
        FundBuilder builder = new FundBuilder();

        JsonNode node = null;
        try {
            node = FundHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ClientBrowsePage.class);
    }

}
