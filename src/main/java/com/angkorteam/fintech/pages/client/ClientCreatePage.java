package com.angkorteam.fintech.pages.client;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;
import org.joda.time.Years;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.DepositType;
import com.angkorteam.fintech.dto.FamilyMemberBuilder;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.LegalForm;
import com.angkorteam.fintech.dto.request.ClientBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.popup.FamilyMemberPopup;
import com.angkorteam.fintech.provider.ClientClassificationProvider;
import com.angkorteam.fintech.provider.ClientTypeProvider;
import com.angkorteam.fintech.provider.ConstitutionProvider;
import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.LegalFormProvider;
import com.angkorteam.fintech.provider.MainBusinessLineProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    protected WebMarkupContainer nameBlock;
    protected WebMarkupContainer nameContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupContainer mobileNumberBlock;
    protected WebMarkupContainer mobileNumberContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;
    protected TextFeedbackPanel mobileNumberFeedback;

    protected WebMarkupContainer dateOfBirthBlock;
    protected WebMarkupContainer dateOfBirthContainer;
    protected Date dateOfBirthValue = DateTime.now().minusYears(18).toDate();
    protected DateTextField dateOfBirthField;
    protected TextFeedbackPanel dateOfBirthFeedback;

    protected WebMarkupContainer genderBlock;
    protected WebMarkupContainer genderContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;
    protected TextFeedbackPanel genderFeedback;

    protected WebMarkupContainer incorporationDateBlock;
    protected WebMarkupContainer incorporationDateContainer;
    protected Date incorporationDateValue = new Date();
    protected DateTextField incorporationDateField;
    protected TextFeedbackPanel incorporationDateFeedback;

    protected WebMarkupContainer incorporationValidityTillDateBlock;
    protected WebMarkupContainer incorporationValidityTillDateContainer;
    protected Date incorporationValidityTillDateValue = DateTime.now().plusYears(1).toDate();
    protected DateTextField incorporationValidityTillDateField;
    protected TextFeedbackPanel incorporationValidityTillDateFeedback;

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

    protected WebMarkupContainer incorporationNumberBlock;
    protected WebMarkupContainer incorporationNumberContainer;
    protected Integer incorporationNumberValue;
    protected TextField<Integer> incorporationNumberField;
    protected TextFeedbackPanel incorporationNumberFeedback;

    protected WebMarkupContainer mainBusinessLineBlock;
    protected WebMarkupContainer mainBusinessLineContainer;
    protected MainBusinessLineProvider mainBusinessLineProvider;
    protected Option mainBusinessLineValue;
    protected Select2SingleChoice<Option> mainBusinessLineField;
    protected TextFeedbackPanel mainBusinessLineFeedback;

    protected ConstitutionProvider constitutionProvider;
    protected WebMarkupContainer constitutionBlock;
    protected WebMarkupContainer constitutionContainer;
    protected Select2SingleChoice<Option> constitutionField;
    protected TextFeedbackPanel constitutionFeedback;
    protected Option constitutionValue;

    protected WebMarkupContainer remarkBlock;
    protected WebMarkupContainer remarkContainer;
    protected String remarkValue;
    protected TextArea<String> remarkField;
    protected TextFeedbackPanel remarkFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue = StringUtils.upperCase(UUID.randomUUID().toString());
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupContainer activeBlock;
    protected WebMarkupContainer activeContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupContainer activationDateBlock;
    protected WebMarkupContainer activationDateContainer;
    protected Date activationDateValue = new Date();
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue = new Date();
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

    protected List<Map<String, Object>> familyMemberValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> familyMemberTable;
    protected ListDataProvider familyMemberProvider;
    protected ModalWindow familyMemberPopup;
    protected AjaxLink<Void> familyMemberAddLink;

    protected Option itemRelationshipValue;
    protected String itemFirstNameValue;
    protected String itemMiddleNameValue;
    protected String itemLastNameValue;
    protected String itemQualificationValue;
    protected String itemMobileNumberValue;
    protected Boolean itemDependentValue;
    protected Option itemGenderValue;
    protected Option itemProfessionValue;
    protected Option itemMaritalStatusValue;
    protected Date itemDateOfBirthValue;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Client Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

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
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeContainer.add(this.officeFeedback);

        this.staffBlock = new WebMarkupContainer("staffBlock");
        this.staffBlock.setOutputMarkupId(true);
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "m_staff.id", "m_staff.display_name");
        this.staffProvider.addJoin("inner join m_office on m_staff.office_id = m_office.id");
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
        this.legalFormField.add(new OnChangeAjaxBehavior(this::legalFormFieldUpdate));
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

        this.nameBlock = new WebMarkupContainer("nameBlock");
        this.nameBlock.setOutputMarkupId(true);
        this.form.add(this.nameBlock);
        this.nameContainer = new WebMarkupContainer("nameContainer");
        this.nameBlock.add(this.nameContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());
        this.nameContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameContainer.add(this.nameFeedback);

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

        this.incorporationDateBlock = new WebMarkupContainer("incorporationDateBlock");
        this.incorporationDateBlock.setOutputMarkupId(true);
        this.form.add(this.incorporationDateBlock);
        this.incorporationDateContainer = new WebMarkupContainer("incorporationDateContainer");
        this.incorporationDateBlock.add(this.incorporationDateContainer);
        this.incorporationDateField = new DateTextField("incorporationDateField", new PropertyModel<>(this, "incorporationDateValue"));
        this.incorporationDateField.setLabel(Model.of("Incorporation Date"));
        this.incorporationDateField.add(new OnChangeAjaxBehavior());
        this.incorporationDateContainer.add(this.incorporationDateField);
        this.incorporationDateFeedback = new TextFeedbackPanel("incorporationDateFeedback", this.incorporationDateField);
        this.incorporationDateContainer.add(this.incorporationDateFeedback);

        this.incorporationValidityTillDateBlock = new WebMarkupContainer("incorporationValidityTillDateBlock");
        this.incorporationValidityTillDateBlock.setOutputMarkupId(true);
        this.form.add(this.incorporationValidityTillDateBlock);
        this.incorporationValidityTillDateContainer = new WebMarkupContainer("incorporationValidityTillDateContainer");
        this.incorporationValidityTillDateBlock.add(this.incorporationValidityTillDateContainer);
        this.incorporationValidityTillDateField = new DateTextField("incorporationValidityTillDateField", new PropertyModel<>(this, "incorporationValidityTillDateValue"));
        this.incorporationValidityTillDateField.setLabel(Model.of("Incorporation Validity Till Date"));
        this.incorporationValidityTillDateField.add(new OnChangeAjaxBehavior());
        this.incorporationValidityTillDateContainer.add(this.incorporationValidityTillDateField);
        this.incorporationValidityTillDateFeedback = new TextFeedbackPanel("incorporationValidityTillDateFeedback", this.incorporationValidityTillDateField);
        this.incorporationValidityTillDateContainer.add(this.incorporationValidityTillDateFeedback);

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

        this.incorporationNumberBlock = new WebMarkupContainer("incorporationNumberBlock");
        this.incorporationNumberBlock.setOutputMarkupId(true);
        this.form.add(this.incorporationNumberBlock);
        this.incorporationNumberContainer = new WebMarkupContainer("incorporationNumberContainer");
        this.incorporationNumberBlock.add(this.incorporationNumberContainer);
        this.incorporationNumberField = new TextField<>("incorporationNumberField", new PropertyModel<>(this, "incorporationNumberValue"));
        this.incorporationNumberField.setLabel(Model.of("Incorporation Number"));
        this.incorporationNumberField.add(new OnChangeAjaxBehavior());
        this.incorporationNumberContainer.add(this.incorporationNumberField);
        this.incorporationNumberFeedback = new TextFeedbackPanel("incorporationNumberFeedback", this.incorporationNumberField);
        this.incorporationNumberContainer.add(this.incorporationNumberFeedback);

        this.mainBusinessLineBlock = new WebMarkupContainer("mainBusinessLineBlock");
        this.mainBusinessLineBlock.setOutputMarkupId(true);
        this.form.add(this.mainBusinessLineBlock);
        this.mainBusinessLineContainer = new WebMarkupContainer("mainBusinessLineContainer");
        this.mainBusinessLineBlock.add(this.mainBusinessLineContainer);
        this.mainBusinessLineProvider = new MainBusinessLineProvider();
        this.mainBusinessLineField = new Select2SingleChoice<>("mainBusinessLineField", 0, new PropertyModel<>(this, "mainBusinessLineValue"), this.mainBusinessLineProvider);
        this.mainBusinessLineField.setLabel(Model.of("Client Classification"));
        this.mainBusinessLineField.add(new OnChangeAjaxBehavior());
        this.mainBusinessLineContainer.add(this.mainBusinessLineField);
        this.mainBusinessLineFeedback = new TextFeedbackPanel("mainBusinessLineFeedback", this.mainBusinessLineField);
        this.mainBusinessLineContainer.add(this.mainBusinessLineFeedback);

        this.constitutionProvider = new ConstitutionProvider();
        this.constitutionBlock = new WebMarkupContainer("constitutionBlock");
        this.constitutionBlock.setOutputMarkupId(true);
        this.form.add(this.constitutionBlock);
        this.constitutionContainer = new WebMarkupContainer("constitutionContainer");
        this.constitutionBlock.add(this.constitutionContainer);
        this.constitutionField = new Select2SingleChoice<>("constitutionField", new PropertyModel<>(this, "constitutionValue"), this.constitutionProvider);
        this.constitutionField.setLabel(Model.of("Constitution"));
        this.constitutionField.add(new OnChangeAjaxBehavior());
        this.constitutionContainer.add(this.constitutionField);
        this.constitutionFeedback = new TextFeedbackPanel("constitutionFeedback", this.constitutionField);
        this.constitutionContainer.add(this.constitutionFeedback);

        this.remarkBlock = new WebMarkupContainer("remarkBlock");
        this.remarkBlock.setOutputMarkupId(true);
        this.form.add(this.remarkBlock);
        this.remarkContainer = new WebMarkupContainer("remarkContainer");
        this.remarkBlock.add(this.remarkContainer);
        this.remarkField = new TextArea<>("remarkField", new PropertyModel<>(this, "remarkValue"));
        this.remarkField.setLabel(Model.of("Remark"));
        this.remarkField.add(new OnChangeAjaxBehavior());
        this.remarkContainer.add(this.remarkField);
        this.remarkFeedback = new TextFeedbackPanel("remarkFeedback", this.remarkField);
        this.remarkContainer.add(this.remarkFeedback);

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
        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
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
        this.openSavingsAccountField.add(new OnChangeAjaxBehavior(this::openSavingsAccountFieldUpdate));
        this.openSavingsAccountContainer.add(this.openSavingsAccountField);
        this.openSavingsAccountFeedback = new TextFeedbackPanel("openSavingsAccountFeedback", this.openSavingsAccountField);
        this.openSavingsAccountContainer.add(this.openSavingsAccountFeedback);

        this.savingsAccountBlock = new WebMarkupContainer("savingsAccountBlock");
        this.savingsAccountBlock.setOutputMarkupId(true);
        this.form.add(this.savingsAccountBlock);
        this.savingsAccountContainer = new WebMarkupContainer("savingsAccountContainer");
        this.savingsAccountBlock.add(this.savingsAccountContainer);
        this.savingsAccountProvider = new SingleChoiceProvider("m_savings_product", "id", "name");
        this.savingsAccountProvider.applyWhere("deposit_type_enum", "deposit_type_enum = " + DepositType.Saving.getLiteral());
        this.savingsAccountField = new Select2SingleChoice<>("savingsAccountField", 0, new PropertyModel<>(this, "savingsAccountValue"), this.savingsAccountProvider);
        this.savingsAccountField.setLabel(Model.of("Savings Account"));
        this.savingsAccountField.add(new OnChangeAjaxBehavior());
        this.savingsAccountContainer.add(this.savingsAccountField);
        this.savingsAccountFeedback = new TextFeedbackPanel("savingsAccountFeedback", this.savingsAccountField);
        this.savingsAccountContainer.add(this.savingsAccountFeedback);

        {
            this.familyMemberPopup = new ModalWindow("familyMemberPopup");
            add(this.familyMemberPopup);
            this.familyMemberPopup.setOnClose(this::familyMemberPopupOnClose);

            List<IColumn<Map<String, Object>, String>> familyMemberColumn = Lists.newLinkedList();
            familyMemberColumn.add(new TextColumn(Model.of("Relationship"), "relationship", "relationship", this::familyMemberRelationshipColumn));
            familyMemberColumn.add(new TextColumn(Model.of("First Name"), "firstName", "firstName", this::familyMemberFirstNameColumn));
            familyMemberColumn.add(new TextColumn(Model.of("Middle Name"), "middleName", "middleName", this::familyMemberMiddleNameColumn));
            familyMemberColumn.add(new TextColumn(Model.of("Last Name"), "lastName", "lastName", this::familyMemberLastNameColumn));
            familyMemberColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::familyMemberGenderNameColumn));
            familyMemberColumn.add(new TextColumn(Model.of("Age"), "age", "age", this::familyMemberAgeColumn));
            familyMemberColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::familyMemberMobileNumberColumn));
            familyMemberColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::familyMemberActionItem, this::familyMemberActionClick));
            this.familyMemberProvider = new ListDataProvider(this.familyMemberValue);
            this.familyMemberTable = new DataTable<>("familyMemberTable", familyMemberColumn, this.familyMemberProvider, 20);
            this.form.add(this.familyMemberTable);
            this.familyMemberTable.addTopToolbar(new HeadersToolbar<>(this.familyMemberTable, this.familyMemberProvider));
            this.familyMemberTable.addBottomToolbar(new NoRecordsToolbar(this.familyMemberTable));

            this.familyMemberAddLink = new AjaxLink<>("familyMemberAddLink");
            this.familyMemberAddLink.setOnClick(this::familyMemberAddLinkClick);
            this.form.add(this.familyMemberAddLink);
        }

        legalFormFieldUpdate(null);
        officeFieldUpdate(null);
        activeFieldUpdate(null);
        openSavingsAccountFieldUpdate(null);

    }

    protected boolean familyMemberAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemRelationshipValue = null;
        this.itemFirstNameValue = null;
        this.itemMiddleNameValue = null;
        this.itemLastNameValue = null;
        this.itemQualificationValue = null;
        this.itemMobileNumberValue = null;
        this.itemDependentValue = null;
        this.itemGenderValue = null;
        this.itemProfessionValue = null;
        this.itemMaritalStatusValue = null;
        this.itemDateOfBirthValue = DateTime.now().minusYears(18).toDate();
        this.familyMemberPopup.setContent(new FamilyMemberPopup(this.familyMemberPopup.getContentId(), this.familyMemberPopup, this));
        this.familyMemberPopup.show(target);
        return false;
    }

    protected ItemPanel familyMemberRelationshipColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberAgeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberFirstNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberMiddleNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberLastNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberGenderNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel familyMemberMobileNumberColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void familyMemberActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.familyMemberValue.size(); i++) {
            Map<String, Object> column = this.familyMemberValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.familyMemberValue.remove(index);
        }
        target.add(this.familyMemberTable);
    }

    protected List<ActionItem> familyMemberActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void familyMemberPopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("relationship", this.itemRelationshipValue);
        item.put("firstName", this.itemFirstNameValue);
        item.put("middleName", this.itemMiddleNameValue);
        item.put("lastName", this.itemLastNameValue);
        item.put("gender", this.itemGenderValue);
        item.put("mobileNumber", this.itemMobileNumberValue);
        item.put("qualificationValue", this.itemQualificationValue);
        item.put("dependentValue", this.itemDependentValue);
        item.put("professionValue", this.itemProfessionValue);
        item.put("maritalStatusValue", this.itemMaritalStatusValue);
        item.put("dateOfBirthValue", this.itemDateOfBirthValue);
        if (this.itemDateOfBirthValue != null) {
            item.put("age", String.valueOf(Years.yearsBetween(new DateTime(this.itemDateOfBirthValue), DateTime.now()).getYears()));
        }
        this.familyMemberValue.add(item);
        target.add(this.familyMemberTable);
    }

    protected boolean openSavingsAccountFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.openSavingsAccountValue == null ? false : this.openSavingsAccountValue;
        this.savingsAccountContainer.setVisible(visible);
        if (target != null) {
            target.add(this.savingsAccountBlock);
        }
        return false;
    }

    protected boolean activeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.activeValue == null ? false : this.activeValue;
        this.activationDateContainer.setVisible(visible);
        if (target != null) {
            target.add(this.activationDateBlock);
        }
        return false;
    }

    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.officeValue != null;
        this.staffValue = null;
        if (this.officeValue == null) {
            this.staffProvider.setDisabled(true);
        } else {
            this.staffProvider.setDisabled(false);
            this.staffProvider.applyWhere("office", "m_office.id = " + this.officeValue.getId());
        }
        this.staffContainer.setVisible(visible);
        if (target != null) {
            target.add(this.staffBlock);
        }
        return false;
    }

    protected boolean legalFormFieldUpdate(AjaxRequestTarget target) {
        LegalForm legalForm = null;
        if (this.legalFormValue != null) {
            legalForm = LegalForm.valueOf(this.legalFormValue.getId());
        }
        boolean peopleVisible = legalForm == LegalForm.Person;
        this.firstNameContainer.setVisible(peopleVisible);
        this.middleNameContainer.setVisible(peopleVisible);
        this.staffApplicationContainer.setVisible(peopleVisible);
        this.lastNameContainer.setVisible(peopleVisible);
        this.dateOfBirthContainer.setVisible(peopleVisible);
        this.genderContainer.setVisible(peopleVisible);

        boolean entityVisible = legalForm == LegalForm.Entity;
        this.nameContainer.setVisible(entityVisible);
        this.incorporationDateContainer.setVisible(entityVisible);
        this.incorporationValidityTillDateContainer.setVisible(entityVisible);
        this.incorporationNumberContainer.setVisible(entityVisible);
        this.mainBusinessLineContainer.setVisible(entityVisible);
        this.constitutionContainer.setVisible(entityVisible);
        this.remarkContainer.setVisible(entityVisible);

        if (target != null) {
            target.add(this.firstNameBlock);
            target.add(this.middleNameBlock);
            target.add(this.staffApplicationBlock);
            target.add(this.lastNameBlock);
            target.add(this.dateOfBirthBlock);
            target.add(this.genderBlock);
            target.add(this.nameBlock);
            target.add(this.incorporationDateBlock);
            target.add(this.incorporationValidityTillDateBlock);
            target.add(this.incorporationNumberBlock);
            target.add(this.mainBusinessLineBlock);
            target.add(this.constitutionBlock);
            target.add(this.remarkBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        LegalForm legalForm = null;
        if (this.legalFormValue != null) {
            legalForm = LegalForm.valueOf(this.legalFormValue.getId());
        }

        ClientBuilder builder = new ClientBuilder();
        builder.withLegalFormId(legalForm);
        if (legalForm == LegalForm.Entity) {
            builder.withFullName(this.nameValue);
            builder.withDateOfBirth(this.incorporationDateValue);
            String mainBusinessLineId = this.mainBusinessLineValue == null ? null : this.mainBusinessLineValue.getId();
            String incorpNumber = this.incorporationNumberValue == null ? null : String.valueOf(this.incorporationNumberValue);
            String constitutionId = this.constitutionValue == null ? null : this.constitutionValue.getId();
            String remarks = this.remarkValue;
            Date incorpValidityTillDate = this.incorporationValidityTillDateValue;
            builder.withClientNonPersonDetails(mainBusinessLineId, incorpNumber, constitutionId, remarks, incorpValidityTillDate);
        } else if (legalForm == LegalForm.Person) {
            builder.withFirstName(this.firstNameValue);
            builder.withMiddlename(this.middleNameValue);
            builder.withStaff(this.staffApplicationValue == null ? false : this.staffApplicationValue);
            builder.withLastName(this.lastNameValue);
            builder.withDateOfBirth(this.dateOfBirthValue);
            if (this.genderValue != null) {
                builder.withGenderId(this.genderValue.getId());
            }
            String mainBusinessLineId = this.mainBusinessLineValue == null ? null : this.mainBusinessLineValue.getId();
            builder.withClientNonPersonDetails(mainBusinessLineId);
        }

        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        if (this.clientClassificationValue != null) {
            builder.withClientClassificationId(this.clientClassificationValue.getId());
        }
        if (this.staffValue != null) {
            builder.withStaffId(this.staffValue.getId());
        }
        builder.withMobileNo(this.mobileNumberValue);
        if (this.clientTypeValue != null) {
            builder.withClientTypeId(this.clientTypeValue.getId());
        }
        builder.withExternalId(this.externalIdValue);
        boolean active = this.activeValue == null ? false : this.activeValue;
        if (active) {
            builder.withActivationDate(this.activationDateValue);
        }
        builder.withActive(active);
        if (this.openSavingsAccountValue != null && this.openSavingsAccountValue) {
            builder.withSavingsProductId(this.savingsAccountValue.getId());
        }
        builder.withSubmittedOnDate(this.submittedOnValue);

        for (Map<String, Object> item : this.familyMemberValue) {
            FamilyMemberBuilder f = new FamilyMemberBuilder();
            Option relationship = (Option) item.get("relationship");
            if (relationship != null) {
                f.withRelationshipId(relationship.getId());
            }
            f.withLastName((String) item.get("firstName"));
            f.withMiddleName((String) item.get("middleName"));
            f.withLastName((String) item.get("lastName"));
            f.withQualification((String) item.get("qualification"));
            f.withMobileNumber((String) item.get("mobileNumber"));
            Boolean dependent = (Boolean) item.get("dependent");
            if (dependent != null) {
                f.withDependent(dependent);
            }
            Option gender = (Option) item.get("gender");
            if (gender != null) {
                f.withGenderId(gender.getId());
            }
            Option profession = (Option) item.get("profession");
            if (profession != null) {
                f.withProfessionId(profession.getId());
            }
            Option maritalStatus = (Option) item.get("maritalStatus");
            if (maritalStatus != null) {
                f.withMaritalStatusId(maritalStatus.getId());
            }
            Date dateOfBirth = (Date) item.get("dateOfBirth");
            f.withDateOfBirth(dateOfBirth);
            String age = (String) item.get("age");
            if (age != null && !"".equals(age)) {
                f.withAge(Integer.valueOf(age));
            }
            builder.withFamilyMembers(f.build());
        }

        JsonNode node = null;
        try {
            node = ClientHelper.createClient((Session) getSession(), builder.build());
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
