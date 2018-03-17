package com.angkorteam.fintech.pages.client.client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.builder.FamilyMemberBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.MaritalStatusProvider;
import com.angkorteam.fintech.provider.ProfessionProvider;
import com.angkorteam.fintech.provider.RelationshipProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientFamilyMemberCreatePage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button addButton;

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

    protected WebMarkupBlock middleNameBlock;
    protected WebMarkupContainer middleNameIContainer;
    protected String middleNameValue;
    protected TextField<String> middleNameField;
    protected TextFeedbackPanel middleNameFeedback;

    protected WebMarkupBlock qualificationBlock;
    protected WebMarkupContainer qualificationIContainer;
    protected String qualificationValue;
    protected TextField<String> qualificationField;
    protected TextFeedbackPanel qualificationFeedback;

    protected WebMarkupBlock mobileNumberBlock;
    protected WebMarkupContainer mobileNumberIContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;
    protected TextFeedbackPanel mobileNumberFeedback;

    protected WebMarkupBlock dateOfBirthBlock;
    protected WebMarkupContainer dateOfBirthIContainer;
    protected Date dateOfBirthValue;
    protected DateTextField dateOfBirthField;
    protected TextFeedbackPanel dateOfBirthFeedback;

    protected WebMarkupBlock dependentBlock;
    protected WebMarkupContainer dependentIContainer;
    protected Boolean dependentValue;
    protected CheckBox dependentField;
    protected TextFeedbackPanel dependentFeedback;

    protected WebMarkupBlock relationshipBlock;
    protected WebMarkupContainer relationshipIContainer;
    protected RelationshipProvider relationshipProvider;
    protected Option relationshipValue;
    protected Select2SingleChoice<Option> relationshipField;
    protected TextFeedbackPanel relationshipFeedback;

    protected WebMarkupBlock genderBlock;
    protected WebMarkupContainer genderIContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;
    protected TextFeedbackPanel genderFeedback;

    protected WebMarkupBlock professionBlock;
    protected WebMarkupContainer professionIContainer;
    protected ProfessionProvider professionProvider;
    protected Option professionValue;
    protected Select2SingleChoice<Option> professionField;
    protected TextFeedbackPanel professionFeedback;

    protected WebMarkupBlock maritalStatusBlock;
    protected WebMarkupContainer maritalStatusIContainer;
    protected MaritalStatusProvider maritalStatusProvider;
    protected Option maritalStatusValue;
    protected Select2SingleChoice<Option> maritalStatusField;
    protected TextFeedbackPanel maritalStatusFeedback;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.clientDisplayName = (String) clientObject.get("display_name");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Family Member Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Four_4);
        this.form.add(this.firstNameBlock);
        this.firstNameIContainer = new WebMarkupContainer("firstNameIContainer");
        this.firstNameBlock.add(this.firstNameIContainer);
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.firstNameIContainer.add(this.firstNameFeedback);

        this.middleNameBlock = new WebMarkupBlock("middleNameBlock", Size.Four_4);
        this.form.add(this.middleNameBlock);
        this.middleNameIContainer = new WebMarkupContainer("middleNameIContainer");
        this.middleNameBlock.add(this.middleNameIContainer);
        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this, "middleNameValue"));
        this.middleNameField.setLabel(Model.of("Middle Name"));
        this.middleNameIContainer.add(this.middleNameField);
        this.middleNameFeedback = new TextFeedbackPanel("middleNameFeedback", this.middleNameField);
        this.middleNameIContainer.add(this.middleNameFeedback);

        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Four_4);
        this.form.add(this.lastNameBlock);
        this.lastNameIContainer = new WebMarkupContainer("lastNameIContainer");
        this.lastNameBlock.add(this.lastNameIContainer);
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.lastNameIContainer.add(this.lastNameFeedback);

        this.qualificationBlock = new WebMarkupBlock("qualificationBlock", Size.Four_4);
        this.form.add(this.qualificationBlock);
        this.qualificationIContainer = new WebMarkupContainer("qualificationIContainer");
        this.qualificationBlock.add(this.qualificationIContainer);
        this.qualificationField = new TextField<>("qualificationField", new PropertyModel<>(this, "qualificationValue"));
        this.qualificationField.setLabel(Model.of("Qualification"));
        this.qualificationIContainer.add(this.qualificationField);
        this.qualificationFeedback = new TextFeedbackPanel("qualificationFeedback", this.qualificationField);
        this.qualificationIContainer.add(this.qualificationFeedback);

        this.mobileNumberBlock = new WebMarkupBlock("mobileNumberBlock", Size.Four_4);
        this.form.add(this.mobileNumberBlock);
        this.mobileNumberIContainer = new WebMarkupContainer("mobileNumberIContainer");
        this.mobileNumberBlock.add(this.mobileNumberIContainer);
        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this, "mobileNumberValue"));
        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
        this.mobileNumberIContainer.add(this.mobileNumberField);
        this.mobileNumberFeedback = new TextFeedbackPanel("mobileNumberFeedback", this.mobileNumberField);
        this.mobileNumberIContainer.add(this.mobileNumberFeedback);

        this.dateOfBirthBlock = new WebMarkupBlock("dateOfBirthBlock", Size.Four_4);
        this.form.add(this.dateOfBirthBlock);
        this.dateOfBirthIContainer = new WebMarkupContainer("dateOfBirthIContainer");
        this.dateOfBirthBlock.add(this.dateOfBirthIContainer);
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.dateOfBirthIContainer.add(this.dateOfBirthField);
        this.dateOfBirthFeedback = new TextFeedbackPanel("dateOfBirthFeedback", this.dateOfBirthField);
        this.dateOfBirthIContainer.add(this.dateOfBirthFeedback);

        this.dependentBlock = new WebMarkupBlock("dependentBlock", Size.Twelve_12);
        this.form.add(this.dependentBlock);
        this.dependentIContainer = new WebMarkupContainer("dependentIContainer");
        this.dependentBlock.add(this.dependentIContainer);
        this.dependentField = new CheckBox("dependentField", new PropertyModel<>(this, "dependentValue"));
        this.dependentIContainer.add(this.dependentField);
        this.dependentFeedback = new TextFeedbackPanel("dependentFeedback", this.dependentField);
        this.dependentIContainer.add(this.dependentFeedback);

        this.relationshipBlock = new WebMarkupBlock("relationshipBlock", Size.Six_6);
        this.form.add(this.relationshipBlock);
        this.relationshipIContainer = new WebMarkupContainer("relationshipIContainer");
        this.relationshipBlock.add(this.relationshipIContainer);
        this.relationshipProvider = new RelationshipProvider();
        this.relationshipField = new Select2SingleChoice<>("relationshipField", new PropertyModel<>(this, "relationshipValue"), this.relationshipProvider);
        this.relationshipField.setLabel(Model.of("Relationship"));
        this.relationshipIContainer.add(this.relationshipField);
        this.relationshipFeedback = new TextFeedbackPanel("relationshipFeedback", this.relationshipField);
        this.relationshipIContainer.add(this.relationshipFeedback);

        this.genderBlock = new WebMarkupBlock("genderBlock", Size.Six_6);
        this.form.add(this.genderBlock);
        this.genderIContainer = new WebMarkupContainer("genderIContainer");
        this.genderBlock.add(this.genderIContainer);
        this.genderProvider = new GenderProvider();
        this.genderField = new Select2SingleChoice<>("genderField", new PropertyModel<>(this, "genderValue"), this.genderProvider);
        this.genderField.setLabel(Model.of("Gender"));
        this.genderIContainer.add(this.genderField);
        this.genderFeedback = new TextFeedbackPanel("genderFeedback", this.genderField);
        this.genderIContainer.add(this.genderFeedback);

        this.professionBlock = new WebMarkupBlock("professionBlock", Size.Six_6);
        this.form.add(this.professionBlock);
        this.professionIContainer = new WebMarkupContainer("professionIContainer");
        this.professionBlock.add(this.professionIContainer);
        this.professionProvider = new ProfessionProvider();
        this.professionField = new Select2SingleChoice<>("professionField", new PropertyModel<>(this, "professionValue"), this.professionProvider);
        this.professionField.setLabel(Model.of("Profession"));
        this.professionIContainer.add(this.professionField);
        this.professionFeedback = new TextFeedbackPanel("professionFeedback", this.professionField);
        this.professionIContainer.add(this.professionFeedback);

        this.maritalStatusBlock = new WebMarkupBlock("maritalStatusBlock", Size.Six_6);
        this.form.add(this.maritalStatusBlock);
        this.maritalStatusIContainer = new WebMarkupContainer("maritalStatusIContainer");
        this.maritalStatusBlock.add(this.maritalStatusIContainer);
        this.maritalStatusProvider = new MaritalStatusProvider();
        this.maritalStatusField = new Select2SingleChoice<>("maritalStatusField", new PropertyModel<>(this, "maritalStatusValue"), this.maritalStatusProvider);
        this.maritalStatusField.setLabel(Model.of("Marital Status"));
        this.maritalStatusIContainer.add(this.maritalStatusField);
        this.maritalStatusFeedback = new TextFeedbackPanel("maritalStatusFeedback", this.maritalStatusField);
        this.maritalStatusIContainer.add(this.maritalStatusFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void addButtonSubmit(Button button) {

        FamilyMemberBuilder builder = new FamilyMemberBuilder();
        builder.withClientId(this.clientId);
        builder.withFirstName(this.firstNameValue);
        builder.withMiddleName(this.middleNameValue);
        builder.withLastName(this.lastNameValue);
        builder.withQualification(this.qualificationValue);
        builder.withMobileNumber(this.mobileNumberValue);
        builder.withDateOfBirth(this.dateOfBirthValue);
        builder.withDependent(this.dependentValue);
        if (this.relationshipValue != null) {
            builder.withRelationshipId(this.relationshipValue.getId());
        }
        if (this.genderValue != null) {
            builder.withGenderId(this.genderValue.getId());
        }
        if (this.professionValue != null) {
            builder.withProfessionId(this.professionValue.getId());
        }
        if (this.maritalStatusValue != null) {
            builder.withMaritalStatusId(this.maritalStatusValue.getId());
        }
        if (this.dateOfBirthValue != null) {
            LocalDate start = this.dateOfBirthValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate stop = LocalDate.now();
            long years = java.time.temporal.ChronoUnit.YEARS.between(start, stop);
            builder.withAge(years);
        }

        JsonNode node = null;
        try {
            node = ClientHelper.createFamilyMember((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
