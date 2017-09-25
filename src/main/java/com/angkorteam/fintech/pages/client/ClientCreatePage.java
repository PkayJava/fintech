package com.angkorteam.fintech.pages.client;

import java.util.Date;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.provider.ClientClassificationProvider;
import com.angkorteam.fintech.provider.ClientTypeProvider;
import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.LegalFormProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
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
    protected LegalFormProvider legalFormProvider;
    protected Option legalFormValue;
    protected Select2SingleChoice<Option> legalFormField;
    protected TextFeedbackPanel legalFormFeedback;

    protected WebMarkupContainer staffApplicationBlock;
    protected WebMarkupContainer staffApplicationContainer;
    protected Boolean staffApplicationValue;
    protected CheckBox staffApplicationField;
    protected TextFeedbackPanel staffApplicationFeedback;

    protected WebMarkupContainer firstNameBlock;
    protected WebMarkupContainer firstNameContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;
    protected TextFeedbackPanel firstNameFeedback;

    protected WebMarkupContainer middleNameBlock;
    protected WebMarkupContainer middleNameContainer;
    protected String middleNameValue;
    protected TextField<String> middleNameField;
    protected TextFeedbackPanel middleNameFeedback;

    protected WebMarkupContainer lastNameBlock;
    protected WebMarkupContainer lastNameContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;
    protected TextFeedbackPanel lastNameFeedback;

    protected WebMarkupContainer mobileNumberBlock;
    protected WebMarkupContainer mobileNumberContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;
    protected TextFeedbackPanel mobileNumberFeedback;

    protected WebMarkupContainer dateOfBirthBlock;
    protected WebMarkupContainer dateOfBirthContainer;
    protected Date dateOfBirthValue;
    protected DateTextField dateOfBirthField;
    protected TextFeedbackPanel dateOfBirthFeedback;

    protected WebMarkupContainer genderBlock;
    protected WebMarkupContainer genderContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;
    protected TextFeedbackPanel genderFeedback;

    protected WebMarkupContainer clientTypeBlock;
    protected WebMarkupContainer clientTypeContainer;
    protected ClientTypeProvider clientTypeProvider;
    protected Option clientTypeValue;
    protected Select2SingleChoice<Option> clientTypeField;
    protected TextFeedbackPanel clientTypeFeedback;

    protected WebMarkupContainer clientClassificationBlock;
    protected WebMarkupContainer clientClassificationContainer;
    protected ClientClassificationProvider clientClassificationProvider;
    protected Option clientClassificationValue;
    protected Select2SingleChoice<Option> clientClassificationField;
    protected TextFeedbackPanel clientClassificationFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupContainer activeBlock;
    protected WebMarkupContainer activeContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupContainer activationDateBlock;
    protected WebMarkupContainer activationDateContainer;
    protected Date activationDateValue;
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected WebMarkupContainer openSavingsAccountBlock;
    protected WebMarkupContainer openSavingsAccountContainer;
    protected Boolean openSavingsAccountValue;
    protected CheckBox openSavingsAccountField;
    protected TextFeedbackPanel openSavingsAccountFeedback;

    protected WebMarkupContainer savingsAccountBlock;
    protected WebMarkupContainer savingsAccountContainer;
    protected SingleChoiceProvider savingsAccountProvider;
    protected Option savingsAccountValue;
    protected Select2SingleChoice<Option> savingsAccountField;
    protected TextFeedbackPanel savingsAccountFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientBrowsePage.class);
        this.form.add(this.closeLink);

        this.officeBlock = new WebMarkupContainer("officeBlock");
        this.officeBlock.setOutputMarkupId(true);
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
        this.staffBlock.setOutputMarkupId(true);
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffContainer.add(this.staffFeedback);

        this.legalFormBlock = new WebMarkupContainer("legalFormBlock");
        this.legalFormBlock.setOutputMarkupId(true);
        this.form.add(this.legalFormBlock);
        this.legalFormContainer = new WebMarkupContainer("legalFormContainer");
        this.legalFormBlock.add(this.legalFormContainer);
        this.legalFormProvider = new LegalFormProvider();
        this.legalFormField = new Select2SingleChoice<>("legalFormField", 0, new PropertyModel<>(this, "legalFormValue"), this.legalFormProvider);
        this.legalFormField.setLabel(Model.of("Legal Form"));
        this.legalFormField.add(new OnChangeAjaxBehavior());
        this.legalFormContainer.add(this.legalFormField);
        this.legalFormFeedback = new TextFeedbackPanel("legalFormFeedback", this.legalFormField);
        this.legalFormContainer.add(this.legalFormFeedback);

        this.staffApplicationBlock = new WebMarkupContainer("staffApplicationBlock");
        this.staffApplicationBlock.setOutputMarkupId(true);
        this.form.add(this.staffApplicationBlock);
        this.staffApplicationContainer = new WebMarkupContainer("staffApplicationContainer");
        this.staffApplicationBlock.add(this.staffApplicationContainer);
        this.staffApplicationField = new CheckBox("staffApplicationField", new PropertyModel<>(this, "staffApplicationValue"));
        this.staffApplicationField.add(new OnChangeAjaxBehavior());
        this.staffApplicationContainer.add(this.staffApplicationField);
        this.staffApplicationFeedback = new TextFeedbackPanel("staffApplicationFeedback", this.staffApplicationField);
        this.staffApplicationContainer.add(this.staffApplicationFeedback);

        this.firstNameBlock = new WebMarkupContainer("firstNameBlock");
        this.firstNameBlock.setOutputMarkupId(true);
        this.form.add(this.firstNameBlock);
        this.firstNameContainer = new WebMarkupContainer("firstNameContainer");
        this.firstNameBlock.add(this.firstNameContainer);
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameField.add(new OnChangeAjaxBehavior());
        this.firstNameContainer.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.firstNameContainer.add(this.firstNameFeedback);

        this.middleNameBlock = new WebMarkupContainer("middleNameBlock");
        this.middleNameBlock.setOutputMarkupId(true);
        this.form.add(this.middleNameBlock);
        this.middleNameContainer = new WebMarkupContainer("middleNameContainer");
        this.middleNameBlock.add(this.middleNameContainer);
        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this, "middleNameValue"));
        this.middleNameField.setLabel(Model.of("Middle Name"));
        this.middleNameField.add(new OnChangeAjaxBehavior());
        this.middleNameContainer.add(this.middleNameField);
        this.middleNameFeedback = new TextFeedbackPanel("middleNameFeedback", this.middleNameField);
        this.middleNameContainer.add(this.middleNameFeedback);

        this.lastNameBlock = new WebMarkupContainer("lastNameBlock");
        this.lastNameBlock.setOutputMarkupId(true);
        this.form.add(this.lastNameBlock);
        this.lastNameContainer = new WebMarkupContainer("lastNameContainer");
        this.lastNameBlock.add(this.lastNameContainer);
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameField.add(new OnChangeAjaxBehavior());
        this.lastNameContainer.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.lastNameContainer.add(this.lastNameFeedback);

        this.mobileNumberBlock = new WebMarkupContainer("mobileNumberBlock");
        this.mobileNumberBlock.setOutputMarkupId(true);
        this.form.add(this.mobileNumberBlock);
        this.mobileNumberContainer = new WebMarkupContainer("mobileNumberContainer");
        this.mobileNumberBlock.add(this.mobileNumberContainer);
        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this, "mobileNumberValue"));
        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
        this.mobileNumberField.add(new OnChangeAjaxBehavior());
        this.mobileNumberContainer.add(this.mobileNumberField);
        this.mobileNumberFeedback = new TextFeedbackPanel("mobileNumberFeedback", this.mobileNumberField);
        this.mobileNumberContainer.add(this.mobileNumberFeedback);

        this.dateOfBirthBlock = new WebMarkupContainer("dateOfBirthBlock");
        this.dateOfBirthBlock.setOutputMarkupId(true);
        this.form.add(this.dateOfBirthBlock);
        this.dateOfBirthContainer = new WebMarkupContainer("dateOfBirthContainer");
        this.dateOfBirthBlock.add(this.dateOfBirthContainer);
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.dateOfBirthField.add(new OnChangeAjaxBehavior());
        this.dateOfBirthContainer.add(this.dateOfBirthField);
        this.dateOfBirthFeedback = new TextFeedbackPanel("dateOfBirthFeedback", this.dateOfBirthField);
        this.dateOfBirthContainer.add(this.dateOfBirthFeedback);

        this.genderBlock = new WebMarkupContainer("genderBlock");
        this.genderBlock.setOutputMarkupId(true);
        this.form.add(this.genderBlock);
        this.genderContainer = new WebMarkupContainer("genderContainer");
        this.genderBlock.add(this.genderContainer);
        this.genderProvider = new GenderProvider();
        this.genderField = new Select2SingleChoice<>("genderField", 0, new PropertyModel<>(this, "genderValue"), this.genderProvider);
        this.genderField.setLabel(Model.of("Gender"));
        this.genderField.add(new OnChangeAjaxBehavior());
        this.genderContainer.add(this.genderField);
        this.genderFeedback = new TextFeedbackPanel("genderFeedback", this.genderField);
        this.genderContainer.add(this.genderFeedback);

        this.clientTypeBlock = new WebMarkupContainer("clientTypeBlock");
        this.clientTypeBlock.setOutputMarkupId(true);
        this.form.add(this.clientTypeBlock);
        this.clientTypeContainer = new WebMarkupContainer("clientTypeContainer");
        this.clientTypeBlock.add(this.clientTypeContainer);
        this.clientTypeProvider = new ClientTypeProvider();
        this.clientTypeField = new Select2SingleChoice<>("clientTypeField", 0, new PropertyModel<>(this, "clientTypeValue"), this.clientTypeProvider);
        this.clientTypeField.setLabel(Model.of("Client Type"));
        this.clientTypeField.add(new OnChangeAjaxBehavior());
        this.clientTypeContainer.add(this.clientTypeField);
        this.clientTypeFeedback = new TextFeedbackPanel("clientTypeFeedback", this.clientTypeField);
        this.clientTypeContainer.add(this.clientTypeFeedback);

        this.clientClassificationBlock = new WebMarkupContainer("clientClassificationBlock");
        this.clientClassificationBlock.setOutputMarkupId(true);
        this.form.add(this.clientClassificationBlock);
        this.clientClassificationContainer = new WebMarkupContainer("clientClassificationContainer");
        this.clientClassificationBlock.add(this.clientClassificationContainer);
        this.clientClassificationProvider = new ClientClassificationProvider();
        this.clientClassificationField = new Select2SingleChoice<>("clientClassificationField", 0, new PropertyModel<>(this, "clientClassificationValue"), this.clientClassificationProvider);
        this.clientClassificationField.setLabel(Model.of("Client Classification"));
        this.clientClassificationField.add(new OnChangeAjaxBehavior());
        this.clientClassificationContainer.add(this.clientClassificationField);
        this.clientClassificationFeedback = new TextFeedbackPanel("clientClassificationFeedback", this.clientClassificationField);
        this.clientClassificationContainer.add(this.clientClassificationFeedback);

        this.externalIdBlock = new WebMarkupContainer("externalIdBlock");
        this.externalIdBlock.setOutputMarkupId(true);
        this.form.add(this.externalIdBlock);
        this.externalIdContainer = new WebMarkupContainer("externalIdContainer");
        this.externalIdBlock.add(this.externalIdContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdContainer.add(this.externalIdFeedback);

        this.activeBlock = new WebMarkupContainer("activeBlock");
        this.activeBlock.setOutputMarkupId(true);
        this.form.add(this.activeBlock);
        this.activeContainer = new WebMarkupContainer("activeContainer");
        this.activeBlock.add(this.activeContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.add(new OnChangeAjaxBehavior());
        this.activeContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeContainer.add(this.activeFeedback);

        this.activationDateBlock = new WebMarkupContainer("activationDateBlock");
        this.activationDateBlock.setOutputMarkupId(true);
        this.form.add(this.activationDateBlock);
        this.activationDateContainer = new WebMarkupContainer("activationDateContainer");
        this.activationDateBlock.add(this.activationDateContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());
        this.activationDateContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateContainer.add(this.activationDateFeedback);

        this.submittedOnBlock = new WebMarkupContainer("submittedOnBlock");
        this.submittedOnBlock.setOutputMarkupId(true);
        this.form.add(this.submittedOnBlock);
        this.submittedOnContainer = new WebMarkupContainer("submittedOnContainer");
        this.submittedOnBlock.add(this.submittedOnContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());
        this.submittedOnContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnContainer.add(this.submittedOnFeedback);

        this.openSavingsAccountBlock = new WebMarkupContainer("openSavingsAccountBlock");
        this.openSavingsAccountBlock.setOutputMarkupId(true);
        this.form.add(this.openSavingsAccountBlock);
        this.openSavingsAccountContainer = new WebMarkupContainer("openSavingsAccountContainer");
        this.openSavingsAccountBlock.add(this.openSavingsAccountContainer);
        this.openSavingsAccountField = new CheckBox("openSavingsAccountField", new PropertyModel<>(this, "openSavingsAccountValue"));
        this.openSavingsAccountField.add(new OnChangeAjaxBehavior());
        this.openSavingsAccountContainer.add(this.openSavingsAccountField);
        this.openSavingsAccountFeedback = new TextFeedbackPanel("openSavingsAccountFeedback", this.openSavingsAccountField);
        this.openSavingsAccountContainer.add(this.openSavingsAccountFeedback);

        this.savingsAccountBlock = new WebMarkupContainer("savingsAccountBlock");
        this.savingsAccountBlock.setOutputMarkupId(true);
        this.form.add(this.savingsAccountBlock);
        this.savingsAccountContainer = new WebMarkupContainer("savingsAccountContainer");
        this.savingsAccountBlock.add(this.savingsAccountContainer);
        this.savingsAccountProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.savingsAccountField = new Select2SingleChoice<>("savingsAccountField", 0, new PropertyModel<>(this, "savingsAccountValue"), this.savingsAccountProvider);
        this.savingsAccountField.setLabel(Model.of("Savings Account"));
        this.savingsAccountField.add(new OnChangeAjaxBehavior());
        this.savingsAccountContainer.add(this.savingsAccountField);
        this.savingsAccountFeedback = new TextFeedbackPanel("savingsAccountFeedback", this.savingsAccountField);
        this.savingsAccountContainer.add(this.savingsAccountFeedback);

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
