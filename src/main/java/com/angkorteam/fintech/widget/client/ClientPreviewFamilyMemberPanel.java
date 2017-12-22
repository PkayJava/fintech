package com.angkorteam.fintech.widget.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.MaritalStatusProvider;
import com.angkorteam.fintech.provider.ProfessionProvider;
import com.angkorteam.fintech.provider.RelationshipProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

public class ClientPreviewFamilyMemberPanel extends com.angkorteam.fintech.widget.Panel {

    protected String clientId;

    protected Page itemPage;

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

    protected List<IColumn<Map<String, Object>, String>> memberColumn;
    protected DataTable<Map<String, Object>, String> memberTable;
    protected JdbcProvider memberProvider;

    public ClientPreviewFamilyMemberPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
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

        this.memberProvider = new JdbcProvider("m_family_members");
        this.memberProvider.applyJoin("relationship", "left join m_code_value relationship on m_family_members.relationship_cv_id = relationship.id");
        this.memberProvider.applyJoin("gender", "left join m_code_value gender on m_family_members.gender_cv_id = gender.id");
        this.memberProvider.boardField("m_family_members.first_name", "first_name", String.class);
        this.memberProvider.boardField("m_family_members.middle_name", "middle_name", String.class);
        this.memberProvider.boardField("m_family_members.last_name", "last_name", String.class);
        this.memberProvider.boardField("m_family_members.mobile_number", "mobile_number", String.class);
        this.memberProvider.boardField("gender.code_description", "gender_description", String.class);
        this.memberProvider.boardField("relationship.code_description", "relationship_description", String.class);
        this.memberProvider.boardField("m_family_members.is_dependent", "dependent", Boolean.class);
        this.memberProvider.boardField("m_family_members.date_of_birth", "date_of_birth", Calendar.Date);
        this.memberProvider.applyWhere("client_id", "m_family_members.client_id = '" + this.clientId + "'");

        this.memberColumn = Lists.newArrayList();
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("First Name"), "first_name", "first_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Middle Name"), "middle_name", "middle_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Last Name"), "last_name", "last_name", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Mobile Number"), "mobile_number", "mobile_number", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Gender"), "gender_description", "gender_description", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.String, Model.of("Relationship"), "relationship_description", "relationship_description", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.Boolean, Model.of("Dependent"), "dependent", "dependent", this::memberColumn));
        this.memberColumn.add(new TextFilterColumn(this.memberProvider, ItemClass.Date, Model.of("Date Of Birth"), "date_of_birth", "date_of_birth", this::memberColumn));

        this.memberTable = new DefaultDataTable<>("memberTable", this.memberColumn, this.memberProvider, 20);
        add(this.memberTable);

    }

    protected ItemPanel memberColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("first_name".equals(column) || "middle_name".equals(column) || "last_name".equals(column) || "mobile_number".equals(column) || "gender_description".equals(column) || "relationship_description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("dependent".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            return new TextCell(value);
        } else if ("date_of_birth".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void addButtonSubmit(Button button) {

    }

}
