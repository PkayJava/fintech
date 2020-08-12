//package com.angkorteam.fintech.popup;
//
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.provider.GenderProvider;
//import com.angkorteam.fintech.provider.MaritalStatusProvider;
//import com.angkorteam.fintech.provider.ProfessionProvider;
//import com.angkorteam.fintech.provider.RelationshipProvider;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
///**
// * Created by socheatkhauv on 7/2/17.
// */
//public class FamilyMemberPopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton okayButton;
//
//    protected RelationshipProvider relationshipProvider;
//    protected Select2SingleChoice<Option> relationshipField;
//    protected TextFeedbackPanel relationshipFeedback;
//
//    protected TextField<String> firstNameField;
//    protected TextFeedbackPanel firstNameFeedback;
//
//    protected TextField<String> middleNameField;
//    protected TextFeedbackPanel middleNameFeedback;
//
//    protected TextField<String> lastNameField;
//    protected TextFeedbackPanel lastNameFeedback;
//
//    protected TextField<String> qualificationField;
//    protected TextFeedbackPanel qualificationFeedback;
//
//    protected TextField<String> mobileNumberField;
//    protected TextFeedbackPanel mobileNumberFeedback;
//
//    protected CheckBox dependentField;
//    protected TextFeedbackPanel dependentFeedback;
//
//    protected GenderProvider genderProvider;
//    protected Select2SingleChoice<Option> genderField;
//    protected TextFeedbackPanel genderFeedback;
//
//    protected ProfessionProvider professionProvider;
//    protected Select2SingleChoice<Option> professionField;
//    protected TextFeedbackPanel professionFeedback;
//
//    protected MaritalStatusProvider maritalStatusProvider;
//    protected Select2SingleChoice<Option> maritalStatusField;
//    protected TextFeedbackPanel maritalStatusFeedback;
//
//    protected DateTextField dateOfBirthField;
//    protected TextFeedbackPanel dateOfBirthFeedback;
//
//    public FamilyMemberPopup(String name, Map<String, Object> model) {
//        super(name, model);
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.okayButton = new AjaxButton("okayButton");
//        this.okayButton.setOnSubmit(this::okayButtonSubmit);
//        this.okayButton.setOnError(this::okayButtonError);
//        this.form.add(this.okayButton);
//
//        this.relationshipProvider = new RelationshipProvider();
//        this.relationshipField = new Select2SingleChoice<>("relationshipField", new PropertyModel<>(this.model, "relationshipValue"), this.relationshipProvider);
//        this.relationshipField.setLabel(Model.of("Relationship"));
//        this.form.add(this.relationshipField);
//        this.relationshipFeedback = new TextFeedbackPanel("relationshipFeedback", this.relationshipField);
//        this.form.add(this.relationshipFeedback);
//
//        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this.model, "firstNameValue"));
//        this.firstNameField.setLabel(Model.of("First Name"));
//        this.form.add(this.firstNameField);
//        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
//        this.form.add(this.firstNameFeedback);
//
//        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this.model, "middleNameValue"));
//        this.middleNameField.setLabel(Model.of("Middle Name"));
//        this.form.add(this.middleNameField);
//        this.middleNameFeedback = new TextFeedbackPanel("middleNameFeedback", this.middleNameField);
//        this.form.add(this.middleNameFeedback);
//
//        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this.model, "lastNameValue"));
//        this.lastNameField.setLabel(Model.of("Last Name"));
//        this.form.add(this.lastNameField);
//        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
//        this.form.add(this.lastNameFeedback);
//
//        this.qualificationField = new TextField<>("qualificationField", new PropertyModel<>(this.model, "qualificationValue"));
//        this.qualificationField.setLabel(Model.of("Qualification"));
//        this.form.add(this.qualificationField);
//        this.qualificationFeedback = new TextFeedbackPanel("qualificationFeedback", this.qualificationField);
//        this.form.add(this.qualificationFeedback);
//
//        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this.model, "mobileNumberValue"));
//        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
//        this.form.add(this.mobileNumberField);
//        this.mobileNumberFeedback = new TextFeedbackPanel("mobileNumberFeedback", this.mobileNumberField);
//        this.form.add(this.mobileNumberFeedback);
//
//        this.dependentField = new CheckBox("dependentField", new PropertyModel<>(this.model, "dependentValue"));
//        this.form.add(this.dependentField);
//        this.dependentFeedback = new TextFeedbackPanel("dependentFeedback", this.dependentField);
//        this.form.add(this.dependentFeedback);
//
//        this.genderProvider = new GenderProvider();
//        this.genderField = new Select2SingleChoice<>("genderField", new PropertyModel<>(this.model, "genderValue"), this.genderProvider);
//        this.genderField.setLabel(Model.of("Gender"));
//        this.form.add(this.genderField);
//        this.genderFeedback = new TextFeedbackPanel("genderFeedback", this.genderField);
//        this.form.add(this.genderFeedback);
//
//        this.professionProvider = new ProfessionProvider();
//        this.professionField = new Select2SingleChoice<>("professionField", new PropertyModel<>(this.model, "professionValue"), this.professionProvider);
//        this.professionField.setLabel(Model.of("Profession"));
//        this.form.add(this.professionField);
//        this.professionFeedback = new TextFeedbackPanel("professionFeedback", this.professionField);
//        this.form.add(this.professionFeedback);
//
//        this.maritalStatusProvider = new MaritalStatusProvider();
//        this.maritalStatusField = new Select2SingleChoice<>("maritalStatusField", new PropertyModel<>(this.model, "maritalStatusValue"), this.maritalStatusProvider);
//        this.maritalStatusField.setLabel(Model.of("Marital Status"));
//        this.form.add(this.maritalStatusField);
//        this.maritalStatusFeedback = new TextFeedbackPanel("maritalStatusFeedback", this.maritalStatusField);
//        this.form.add(this.maritalStatusFeedback);
//
//        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this.model, "dateOfBirthValue"));
//        this.dateOfBirthField.setLabel(Model.of("Date of Birth"));
//        this.dateOfBirthField.setRequired(true);
//        this.form.add(this.dateOfBirthField);
//        this.dateOfBirthFeedback = new TextFeedbackPanel("dateOfBirthFeedback", this.dateOfBirthField);
//        this.form.add(this.dateOfBirthFeedback);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        this.window.setSignalId(ajaxButton.getId());
//        this.window.close(target);
//        return true;
//    }
//
//    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        target.add(this.form);
//        return true;
//    }
//}
