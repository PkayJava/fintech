package com.angkorteam.fintech.pages.client.client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.dto.builder.FamilyMemberBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.MaritalStatusProvider;
import com.angkorteam.fintech.provider.ProfessionProvider;
import com.angkorteam.fintech.provider.RelationshipProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

public class ClientFamilyMemberCreatePage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button addButton;

    protected UIRow row1;

    protected UIBlock firstNameBlock;
    protected UIContainer firstNameIContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;

    protected UIBlock lastNameBlock;
    protected UIContainer lastNameIContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;

    protected UIBlock middleNameBlock;
    protected UIContainer middleNameIContainer;
    protected String middleNameValue;
    protected TextField<String> middleNameField;

    protected UIRow row2;

    protected UIBlock qualificationBlock;
    protected UIContainer qualificationIContainer;
    protected String qualificationValue;
    protected TextField<String> qualificationField;

    protected UIBlock mobileNumberBlock;
    protected UIContainer mobileNumberIContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;

    protected UIBlock dateOfBirthBlock;
    protected UIContainer dateOfBirthIContainer;
    protected Date dateOfBirthValue;
    protected DateTextField dateOfBirthField;

    protected UIRow row3;

    protected UIBlock dependentBlock;
    protected UIContainer dependentIContainer;
    protected Boolean dependentValue;
    protected CheckBox dependentField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock relationshipBlock;
    protected UIContainer relationshipIContainer;
    protected RelationshipProvider relationshipProvider;
    protected Option relationshipValue;
    protected Select2SingleChoice<Option> relationshipField;

    protected UIBlock genderBlock;
    protected UIContainer genderIContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;

    protected UIRow row5;

    protected UIBlock professionBlock;
    protected UIContainer professionIContainer;
    protected ProfessionProvider professionProvider;
    protected Option professionValue;
    protected Select2SingleChoice<Option> professionField;

    protected UIBlock maritalStatusBlock;
    protected UIContainer maritalStatusIContainer;
    protected MaritalStatusProvider maritalStatusProvider;
    protected Option maritalStatusValue;
    protected Select2SingleChoice<Option> maritalStatusField;

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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.firstNameBlock = this.row1.newUIBlock("firstNameBlock", Size.Four_4);
        this.firstNameIContainer = this.firstNameBlock.newUIContainer("firstNameIContainer");
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameIContainer.newFeedback("firstNameFeedback", this.firstNameField);

        this.middleNameBlock = this.row1.newUIBlock("middleNameBlock", Size.Four_4);
        this.middleNameIContainer = this.middleNameBlock.newUIContainer("middleNameIContainer");
        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this, "middleNameValue"));
        this.middleNameIContainer.add(this.middleNameField);
        this.middleNameIContainer.newFeedback("middleNameFeedback", this.middleNameField);

        this.lastNameBlock = this.row1.newUIBlock("lastNameBlock", Size.Four_4);
        this.lastNameIContainer = this.lastNameBlock.newUIContainer("lastNameIContainer");
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameIContainer.newFeedback("lastNameFeedback", this.lastNameField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.qualificationBlock = this.row2.newUIBlock("qualificationBlock", Size.Four_4);
        this.qualificationIContainer = this.qualificationBlock.newUIContainer("qualificationIContainer");
        this.qualificationField = new TextField<>("qualificationField", new PropertyModel<>(this, "qualificationValue"));
        this.qualificationIContainer.add(this.qualificationField);
        this.qualificationIContainer.newFeedback("qualificationFeedback", this.qualificationField);

        this.mobileNumberBlock = this.row2.newUIBlock("mobileNumberBlock", Size.Four_4);
        this.mobileNumberIContainer = this.mobileNumberBlock.newUIContainer("mobileNumberIContainer");
        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this, "mobileNumberValue"));
        this.mobileNumberIContainer.add(this.mobileNumberField);
        this.mobileNumberIContainer.newFeedback("mobileNumberFeedback", this.mobileNumberField);

        this.dateOfBirthBlock = this.row2.newUIBlock("dateOfBirthBlock", Size.Four_4);
        this.dateOfBirthIContainer = this.dateOfBirthBlock.newUIContainer("dateOfBirthIContainer");
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthIContainer.add(this.dateOfBirthField);
        this.dateOfBirthIContainer.newFeedback("dateOfBirthFeedback", this.dateOfBirthField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.dependentBlock = this.row3.newUIBlock("dependentBlock", Size.Six_6);
        this.dependentIContainer = this.dependentBlock.newUIContainer("dependentIContainer");
        this.dependentField = new CheckBox("dependentField", new PropertyModel<>(this, "dependentValue"));
        this.dependentIContainer.add(this.dependentField);
        this.dependentIContainer.newFeedback("dependentFeedback", this.dependentField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.relationshipBlock = this.row4.newUIBlock("relationshipBlock", Size.Six_6);
        this.relationshipIContainer = this.relationshipBlock.newUIContainer("relationshipIContainer");
        this.relationshipField = new Select2SingleChoice<>("relationshipField", new PropertyModel<>(this, "relationshipValue"), this.relationshipProvider);
        this.relationshipIContainer.add(this.relationshipField);
        this.relationshipIContainer.newFeedback("relationshipFeedback", this.relationshipField);

        this.genderBlock = this.row4.newUIBlock("genderBlock", Size.Six_6);
        this.genderIContainer = this.genderBlock.newUIContainer("genderIContainer");
        this.genderField = new Select2SingleChoice<>("genderField", new PropertyModel<>(this, "genderValue"), this.genderProvider);
        this.genderIContainer.add(this.genderField);
        this.genderIContainer.newFeedback("genderFeedback", this.genderField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.professionBlock = this.row5.newUIBlock("professionBlock", Size.Six_6);
        this.professionIContainer = this.professionBlock.newUIContainer("professionIContainer");
        this.professionField = new Select2SingleChoice<>("professionField", new PropertyModel<>(this, "professionValue"), this.professionProvider);
        this.professionIContainer.add(this.professionField);
        this.professionIContainer.newFeedback("professionFeedback", this.professionField);

        this.maritalStatusBlock = this.row5.newUIBlock("maritalStatusBlock", Size.Six_6);
        this.maritalStatusIContainer = this.maritalStatusBlock.newUIContainer("maritalStatusIContainer");
        this.maritalStatusField = new Select2SingleChoice<>("maritalStatusField", new PropertyModel<>(this, "maritalStatusValue"), this.maritalStatusProvider);
        this.maritalStatusIContainer.add(this.maritalStatusField);
        this.maritalStatusIContainer.newFeedback("maritalStatusFeedback", this.maritalStatusField);
    }

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

        this.relationshipProvider = new RelationshipProvider();
        this.genderProvider = new GenderProvider();
        this.professionProvider = new ProfessionProvider();
        this.maritalStatusProvider = new MaritalStatusProvider();
    }

    @Override
    protected void configureMetaData() {
        this.firstNameField.setLabel(Model.of("First Name"));
        this.middleNameField.setLabel(Model.of("Middle Name"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.qualificationField.setLabel(Model.of("Qualification"));
        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.relationshipField.setLabel(Model.of("Relationship"));
        this.genderField.setLabel(Model.of("Gender"));
        this.professionField.setLabel(Model.of("Profession"));
        this.maritalStatusField.setLabel(Model.of("Marital Status"));
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

        JsonNode node = ClientHelper.createFamilyMember((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
