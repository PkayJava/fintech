package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.LoanPurposeProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanGuarantorCreateExternalPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected LoanPurposeProvider relationshipProvider;
    protected WebMarkupContainer relationshipBlock;
    protected WebMarkupContainer relationshipIContainer;
    protected Option relationshipValue;
    protected Select2SingleChoice<Option> relationshipField;
    protected TextFeedbackPanel relationshipFeedback;

    protected WebMarkupBlock firstNameBlock;
    protected WebMarkupContainer firstNameIContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;
    protected TextFeedbackPanel firstNameFeedback;

    protected WebMarkupBlock lastNameBlock;
    protected WebMarkupContainer lastNameIContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;
    protected TextFeedbackPanel lastNameFeedback;

    protected WebMarkupBlock dateOfBirthBlock;
    protected WebMarkupContainer dateOfBirthIContainer;
    protected Date dateOfBirthValue;
    protected DateTextField dateOfBirthField;
    protected TextFeedbackPanel dateOfBirthFeedback;

    protected WebMarkupBlock addressLine1Block;
    protected WebMarkupContainer addressLine1IContainer;
    protected String addressLine1Value;
    protected TextField<String> addressLine1Field;
    protected TextFeedbackPanel addressLine1Feedback;

    protected WebMarkupBlock addressLine2Block;
    protected WebMarkupContainer addressLine2IContainer;
    protected String addressLine2Value;
    protected TextField<String> addressLine2Field;
    protected TextFeedbackPanel addressLine2Feedback;

    protected WebMarkupBlock cityBlock;
    protected WebMarkupContainer cityIContainer;
    protected String cityValue;
    protected TextField<String> cityField;
    protected TextFeedbackPanel cityFeedback;

    protected WebMarkupBlock zipBlock;
    protected WebMarkupContainer zipIContainer;
    protected String zipValue;
    protected TextField<String> zipField;
    protected TextFeedbackPanel zipFeedback;

    protected WebMarkupBlock mobileBlock;
    protected WebMarkupContainer mobileIContainer;
    protected String mobileValue;
    protected TextField<String> mobileField;
    protected TextFeedbackPanel mobileFeedback;

    protected WebMarkupBlock residentPhoneBlock;
    protected WebMarkupContainer residentPhoneIContainer;
    protected String residentPhoneValue;
    protected TextField<String> residentPhoneField;
    protected TextFeedbackPanel residentPhoneFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.relationshipProvider = new LoanPurposeProvider();
        this.relationshipBlock = new WebMarkupBlock("relationshipBlock", Size.Six_6);
        this.form.add(this.relationshipBlock);
        this.relationshipIContainer = new WebMarkupContainer("relationshipIContainer");
        this.relationshipBlock.add(this.relationshipIContainer);
        this.relationshipField = new Select2SingleChoice<>("relationshipField", new PropertyModel<>(this, "relationshipValue"), this.relationshipProvider);
        this.relationshipField.setLabel(Model.of("Relationship"));
        this.relationshipField.setRequired(false);
        this.relationshipIContainer.add(this.relationshipField);
        this.relationshipFeedback = new TextFeedbackPanel("relationshipFeedback", this.relationshipField);
        this.relationshipIContainer.add(this.relationshipFeedback);

        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Six_6);
        this.form.add(this.firstNameBlock);
        this.firstNameIContainer = new WebMarkupContainer("firstNameIContainer");
        this.firstNameBlock.add(this.firstNameIContainer);
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameField.setRequired(false);
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.firstNameIContainer.add(this.firstNameFeedback);

        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Six_6);
        this.form.add(this.lastNameBlock);
        this.lastNameIContainer = new WebMarkupContainer("lastNameIContainer");
        this.lastNameBlock.add(this.lastNameIContainer);
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameField.setRequired(false);
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.lastNameIContainer.add(this.lastNameFeedback);

        this.dateOfBirthBlock = new WebMarkupBlock("dateOfBirthBlock", Size.Six_6);
        this.form.add(this.dateOfBirthBlock);
        this.dateOfBirthIContainer = new WebMarkupContainer("dateOfBirthIContainer");
        this.dateOfBirthBlock.add(this.dateOfBirthIContainer);
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.dateOfBirthField.setRequired(false);
        this.dateOfBirthIContainer.add(this.dateOfBirthField);
        this.dateOfBirthFeedback = new TextFeedbackPanel("dateOfBirthFeedback", this.dateOfBirthField);
        this.dateOfBirthIContainer.add(this.dateOfBirthFeedback);

        this.addressLine1Block = new WebMarkupBlock("addressLine1Block", Size.Six_6);
        this.form.add(this.addressLine1Block);
        this.addressLine1IContainer = new WebMarkupContainer("addressLine1IContainer");
        this.addressLine1Block.add(this.addressLine1IContainer);
        this.addressLine1Field = new TextField<>("addressLine1Field", new PropertyModel<>(this, "addressLine1Value"));
        this.addressLine1Field.setLabel(Model.of("Address Line 1"));
        this.addressLine1Field.setRequired(false);
        this.addressLine1IContainer.add(this.addressLine1Field);
        this.addressLine1Feedback = new TextFeedbackPanel("addressLine1Feedback", this.addressLine1Field);
        this.addressLine1IContainer.add(this.addressLine1Feedback);

        this.addressLine2Block = new WebMarkupBlock("addressLine2Block", Size.Six_6);
        this.form.add(this.addressLine2Block);
        this.addressLine2IContainer = new WebMarkupContainer("addressLine2IContainer");
        this.addressLine2Block.add(this.addressLine2IContainer);
        this.addressLine2Field = new TextField<>("addressLine2Field", new PropertyModel<>(this, "addressLine2Value"));
        this.addressLine2Field.setLabel(Model.of("Address Line 2"));
        this.addressLine2Field.setRequired(false);
        this.addressLine2IContainer.add(this.addressLine2Field);
        this.addressLine2Feedback = new TextFeedbackPanel("addressLine2Feedback", this.addressLine2Field);
        this.addressLine2IContainer.add(this.addressLine2Feedback);

        this.cityBlock = new WebMarkupBlock("cityBlock", Size.Six_6);
        this.form.add(this.cityBlock);
        this.cityIContainer = new WebMarkupContainer("cityIContainer");
        this.cityBlock.add(this.cityIContainer);
        this.cityField = new TextField<>("cityField", new PropertyModel<>(this, "cityValue"));
        this.cityField.setLabel(Model.of("City"));
        this.cityField.setRequired(false);
        this.cityIContainer.add(this.cityField);
        this.cityFeedback = new TextFeedbackPanel("cityFeedback", this.cityField);
        this.cityIContainer.add(this.cityFeedback);

        this.zipBlock = new WebMarkupBlock("zipBlock", Size.Six_6);
        this.form.add(this.zipBlock);
        this.zipIContainer = new WebMarkupContainer("zipIContainer");
        this.zipBlock.add(this.zipIContainer);
        this.zipField = new TextField<>("zipField", new PropertyModel<>(this, "zipValue"));
        this.zipField.setLabel(Model.of("Zip"));
        this.zipField.setRequired(false);
        this.zipIContainer.add(this.zipField);
        this.zipFeedback = new TextFeedbackPanel("zipFeedback", this.zipField);
        this.zipIContainer.add(this.zipFeedback);

        this.mobileBlock = new WebMarkupBlock("mobileBlock", Size.Six_6);
        this.form.add(this.mobileBlock);
        this.mobileIContainer = new WebMarkupContainer("mobileIContainer");
        this.mobileBlock.add(this.mobileIContainer);
        this.mobileField = new TextField<>("mobileField", new PropertyModel<>(this, "mobileValue"));
        this.mobileField.setLabel(Model.of("Mobile"));
        this.mobileField.setRequired(false);
        this.mobileIContainer.add(this.mobileField);
        this.mobileFeedback = new TextFeedbackPanel("mobileFeedback", this.mobileField);
        this.mobileIContainer.add(this.mobileFeedback);

        this.residentPhoneBlock = new WebMarkupBlock("residentPhoneBlock", Size.Six_6);
        this.form.add(this.residentPhoneBlock);
        this.residentPhoneIContainer = new WebMarkupContainer("residentPhoneIContainer");
        this.residentPhoneBlock.add(this.residentPhoneIContainer);
        this.residentPhoneField = new TextField<>("residentPhoneField", new PropertyModel<>(this, "residentPhoneValue"));
        this.residentPhoneField.setLabel(Model.of("Resident Phone"));
        this.residentPhoneField.setRequired(false);
        this.residentPhoneIContainer.add(this.residentPhoneField);
        this.residentPhoneFeedback = new TextFeedbackPanel("residentPhoneFeedback", this.residentPhoneField);
        this.residentPhoneIContainer.add(this.residentPhoneFeedback);

        this.form.add(this.closeLink);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {

    }
}