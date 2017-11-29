package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
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
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ClientBuilder;
import com.angkorteam.fintech.dto.builder.FamilyMemberBuilder;
import com.angkorteam.fintech.dto.enums.DepositType;
import com.angkorteam.fintech.dto.enums.LegalForm;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.popup.FamilyMemberPopup;
import com.angkorteam.fintech.provider.ClientClassificationProvider;
import com.angkorteam.fintech.provider.ClientTypeProvider;
import com.angkorteam.fintech.provider.ConstitutionProvider;
import com.angkorteam.fintech.provider.GenderProvider;
import com.angkorteam.fintech.provider.LegalFormProvider;
import com.angkorteam.fintech.provider.MainBusinessLineProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
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

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock staffBlock;
    protected WebMarkupContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupBlock legalFormBlock;
    protected WebMarkupContainer legalFormIContainer;
    protected LegalFormProvider legalFormProvider;
    protected Option legalFormValue;
    protected Select2SingleChoice<Option> legalFormField;
    protected TextFeedbackPanel legalFormFeedback;

    protected WebMarkupBlock staffApplicationBlock;
    protected WebMarkupContainer staffApplicationIContainer;
    protected Boolean staffApplicationValue;
    protected CheckBox staffApplicationField;
    protected TextFeedbackPanel staffApplicationFeedback;

    protected WebMarkupBlock firstNameBlock;
    protected WebMarkupContainer firstNameIContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;
    protected TextFeedbackPanel firstNameFeedback;

    protected WebMarkupBlock middleNameBlock;
    protected WebMarkupContainer middleNameIContainer;
    protected String middleNameValue;
    protected TextField<String> middleNameField;
    protected TextFeedbackPanel middleNameFeedback;

    protected WebMarkupBlock lastNameBlock;
    protected WebMarkupContainer lastNameIContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;
    protected TextFeedbackPanel lastNameFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock mobileNumberBlock;
    protected WebMarkupContainer mobileNumberIContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;
    protected TextFeedbackPanel mobileNumberFeedback;

    protected WebMarkupBlock dateOfBirthBlock;
    protected WebMarkupContainer dateOfBirthIContainer;
    protected Date dateOfBirthValue = DateTime.now().minusYears(18).toDate();
    protected DateTextField dateOfBirthField;
    protected TextFeedbackPanel dateOfBirthFeedback;

    protected WebMarkupBlock genderBlock;
    protected WebMarkupContainer genderIContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;
    protected TextFeedbackPanel genderFeedback;

    protected WebMarkupBlock incorporationDateBlock;
    protected WebMarkupContainer incorporationDateIContainer;
    protected Date incorporationDateValue = new Date();
    protected DateTextField incorporationDateField;
    protected TextFeedbackPanel incorporationDateFeedback;

    protected WebMarkupBlock incorporationValidityTillDateBlock;
    protected WebMarkupContainer incorporationValidityTillDateIContainer;
    protected Date incorporationValidityTillDateValue = DateTime.now().plusYears(1).toDate();
    protected DateTextField incorporationValidityTillDateField;
    protected TextFeedbackPanel incorporationValidityTillDateFeedback;

    protected WebMarkupBlock clientTypeBlock;
    protected WebMarkupContainer clientTypeIContainer;
    protected ClientTypeProvider clientTypeProvider;
    protected Option clientTypeValue;
    protected Select2SingleChoice<Option> clientTypeField;
    protected TextFeedbackPanel clientTypeFeedback;

    protected WebMarkupBlock clientClassificationBlock;
    protected WebMarkupContainer clientClassificationIContainer;
    protected ClientClassificationProvider clientClassificationProvider;
    protected Option clientClassificationValue;
    protected Select2SingleChoice<Option> clientClassificationField;
    protected TextFeedbackPanel clientClassificationFeedback;

    protected WebMarkupBlock incorporationNumberBlock;
    protected WebMarkupContainer incorporationNumberIContainer;
    protected Long incorporationNumberValue;
    protected TextField<Long> incorporationNumberField;
    protected TextFeedbackPanel incorporationNumberFeedback;

    protected WebMarkupBlock mainBusinessLineBlock;
    protected WebMarkupContainer mainBusinessLineIContainer;
    protected MainBusinessLineProvider mainBusinessLineProvider;
    protected Option mainBusinessLineValue;
    protected Select2SingleChoice<Option> mainBusinessLineField;
    protected TextFeedbackPanel mainBusinessLineFeedback;

    protected WebMarkupBlock constitutionBlock;
    protected WebMarkupContainer constitutionIContainer;
    protected ConstitutionProvider constitutionProvider;
    protected Select2SingleChoice<Option> constitutionField;
    protected TextFeedbackPanel constitutionFeedback;
    protected Option constitutionValue;

    protected WebMarkupBlock remarkBlock;
    protected WebMarkupContainer remarkIContainer;
    protected String remarkValue;
    protected TextArea<String> remarkField;
    protected TextFeedbackPanel remarkFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupBlock activationDateBlock;
    protected WebMarkupContainer activationDateIContainer;
    protected Date activationDateValue = new Date();
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected Date submittedOnValue = new Date();
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected WebMarkupBlock openSavingsAccountBlock;
    protected WebMarkupContainer openSavingsAccountIContainer;
    protected Boolean openSavingsAccountValue;
    protected CheckBox openSavingsAccountField;
    protected TextFeedbackPanel openSavingsAccountFeedback;

    protected WebMarkupBlock savingsAccountBlock;
    protected WebMarkupContainer savingsAccountIContainer;
    protected SingleChoiceProvider savingsAccountProvider;
    protected Option savingsAccountValue;
    protected Select2SingleChoice<Option> savingsAccountField;
    protected TextFeedbackPanel savingsAccountFeedback;

    protected WebMarkupBlock familyMemberBlock;
    protected WebMarkupContainer familyMemberIContainer;
    protected List<Map<String, Object>> familyMemberValue;
    protected DataTable<Map<String, Object>, String> familyMemberTable;
    protected ListDataProvider familyMemberProvider;
    protected ModalWindow familyMemberPopup;
    protected List<IColumn<Map<String, Object>, String>> familyMemberColumn;
    protected AjaxLink<Void> familyMemberAddLink;

    protected Map<String, Object> popupModel;

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
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Client Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.familyMemberValue = Lists.newArrayList();
        this.popupModel = Maps.newHashMap();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientBrowsePage.class);
        this.form.add(this.closeLink);

        initOfficeBlock();

        initStaffBlock();

        initLegalFormBlock();

        initStaffApplicationBlock();

        initFirstNameBlock();

        initMiddleNameBlock();

        initLastNameBlock();

        initNameBlock();

        initMobileNumberBlock();

        initDateOfBirthBlock();

        initGenderBlock();

        initIncorporationDateBlock();

        initIncorporationValidityTillDateBlock();

        initClientTypeBlock();

        initClientClassificationBlock();

        initIncorporationNumberBlock();

        initMainBusinessLineBlock();

        initConstitutionBlock();

        initRemarkBlock();

        initActiveBlock();

        initActivationDateBlock();

        initSubmittedOnBlock();

        initOpenSavingsAccountBlock();

        initSavingsAccountBlock();

        initExternalIdBlock();

        initFamilyMemberBlock();
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {
        legalFormFieldUpdate(null);
        officeFieldUpdate(null);
        activeFieldUpdate(null);
        openSavingsAccountFieldUpdate(null);
    }

    protected void initFamilyMemberBlock() {
        this.familyMemberBlock = new WebMarkupBlock("familyMemberBlock", Size.Twelve_12);
        this.form.add(this.familyMemberBlock);
        this.familyMemberIContainer = new WebMarkupContainer("familyMemberIContainer");
        this.familyMemberBlock.add(this.familyMemberIContainer);

        this.familyMemberPopup = new ModalWindow("familyMemberPopup");
        this.familyMemberIContainer.add(this.familyMemberPopup);
        this.familyMemberPopup.setOnClose(this::familyMemberPopupClose);

        this.familyMemberColumn = Lists.newLinkedList();
        this.familyMemberColumn.add(new TextColumn(Model.of("Relationship"), "relationship", "relationship", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("First Name"), "firstName", "firstName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Middle Name"), "middleName", "middleName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Last Name"), "lastName", "lastName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Age"), "age", "age", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::familyMemberColumn));
        this.familyMemberColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::familyMemberAction, this::familyMemberClick));
        this.familyMemberProvider = new ListDataProvider(this.familyMemberValue);
        this.familyMemberTable = new DataTable<>("familyMemberTable", this.familyMemberColumn, this.familyMemberProvider, 20);
        this.familyMemberIContainer.add(this.familyMemberTable);
        this.familyMemberTable.addTopToolbar(new HeadersToolbar<>(this.familyMemberTable, this.familyMemberProvider));
        this.familyMemberTable.addBottomToolbar(new NoRecordsToolbar(this.familyMemberTable));

        this.familyMemberAddLink = new AjaxLink<>("familyMemberAddLink");
        this.familyMemberAddLink.setOnClick(this::familyMemberAddLinkClick);
        this.familyMemberIContainer.add(this.familyMemberAddLink);
    }

    protected void initExternalIdBlock() {
        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Four_4);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);
    }

    protected void initSavingsAccountBlock() {
        this.savingsAccountBlock = new WebMarkupBlock("savingsAccountBlock", Size.Four_4);
        this.form.add(this.savingsAccountBlock);
        this.savingsAccountIContainer = new WebMarkupContainer("savingsAccountIContainer");
        this.savingsAccountBlock.add(this.savingsAccountIContainer);
        this.savingsAccountProvider = new SingleChoiceProvider("m_savings_product", "id", "name");
        this.savingsAccountProvider.applyWhere("deposit_type_enum", "deposit_type_enum = " + DepositType.Saving.getLiteral());
        this.savingsAccountField = new Select2SingleChoice<>("savingsAccountField", new PropertyModel<>(this, "savingsAccountValue"), this.savingsAccountProvider);
        this.savingsAccountField.setLabel(Model.of("Savings Account"));
        this.savingsAccountField.add(new OnChangeAjaxBehavior());
        this.savingsAccountIContainer.add(this.savingsAccountField);
        this.savingsAccountFeedback = new TextFeedbackPanel("savingsAccountFeedback", this.savingsAccountField);
        this.savingsAccountIContainer.add(this.savingsAccountFeedback);
    }

    protected void initOpenSavingsAccountBlock() {
        this.openSavingsAccountBlock = new WebMarkupBlock("openSavingsAccountBlock", Size.Four_4);
        this.form.add(this.openSavingsAccountBlock);
        this.openSavingsAccountIContainer = new WebMarkupContainer("openSavingsAccountIContainer");
        this.openSavingsAccountBlock.add(this.openSavingsAccountIContainer);
        this.openSavingsAccountField = new CheckBox("openSavingsAccountField", new PropertyModel<>(this, "openSavingsAccountValue"));
        this.openSavingsAccountField.add(new OnChangeAjaxBehavior(this::openSavingsAccountFieldUpdate));
        this.openSavingsAccountIContainer.add(this.openSavingsAccountField);
        this.openSavingsAccountFeedback = new TextFeedbackPanel("openSavingsAccountFeedback", this.openSavingsAccountField);
        this.openSavingsAccountIContainer.add(this.openSavingsAccountFeedback);
    }

    protected void initSubmittedOnBlock() {
        this.submittedOnBlock = new WebMarkupBlock("submittedOnBlock", Size.Four_4);
        this.form.add(this.submittedOnBlock);
        this.submittedOnIContainer = new WebMarkupContainer("submittedOnIContainer");
        this.submittedOnBlock.add(this.submittedOnIContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnIContainer.add(this.submittedOnFeedback);
    }

    protected void initActivationDateBlock() {
        this.activationDateBlock = new WebMarkupBlock("activationDateBlock", Size.Four_4);
        this.form.add(this.activationDateBlock);
        this.activationDateIContainer = new WebMarkupContainer("activationDateIContainer");
        this.activationDateBlock.add(this.activationDateIContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());
        this.activationDateIContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateIContainer.add(this.activationDateFeedback);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Four_4);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initRemarkBlock() {
        this.remarkBlock = new WebMarkupBlock("remarkBlock", Size.Six_6);
        this.form.add(this.remarkBlock);
        this.remarkIContainer = new WebMarkupContainer("remarkIContainer");
        this.remarkBlock.add(this.remarkIContainer);
        this.remarkField = new TextArea<>("remarkField", new PropertyModel<>(this, "remarkValue"));
        this.remarkField.setLabel(Model.of("Remark"));
        this.remarkField.add(new OnChangeAjaxBehavior());
        this.remarkIContainer.add(this.remarkField);
        this.remarkFeedback = new TextFeedbackPanel("remarkFeedback", this.remarkField);
        this.remarkIContainer.add(this.remarkFeedback);
    }

    protected void initConstitutionBlock() {
        this.constitutionProvider = new ConstitutionProvider();
        this.constitutionBlock = new WebMarkupBlock("constitutionBlock", Size.Six_6);
        this.form.add(this.constitutionBlock);
        this.constitutionIContainer = new WebMarkupContainer("constitutionIContainer");
        this.constitutionBlock.add(this.constitutionIContainer);
        this.constitutionField = new Select2SingleChoice<>("constitutionField", new PropertyModel<>(this, "constitutionValue"), this.constitutionProvider);
        this.constitutionField.setLabel(Model.of("Constitution"));
        this.constitutionField.add(new OnChangeAjaxBehavior());
        this.constitutionIContainer.add(this.constitutionField);
        this.constitutionFeedback = new TextFeedbackPanel("constitutionFeedback", this.constitutionField);
        this.constitutionIContainer.add(this.constitutionFeedback);
    }

    protected void initMainBusinessLineBlock() {
        this.mainBusinessLineBlock = new WebMarkupBlock("mainBusinessLineBlock", Size.Six_6);
        this.form.add(this.mainBusinessLineBlock);
        this.mainBusinessLineIContainer = new WebMarkupContainer("mainBusinessLineIContainer");
        this.mainBusinessLineBlock.add(this.mainBusinessLineIContainer);
        this.mainBusinessLineProvider = new MainBusinessLineProvider();
        this.mainBusinessLineField = new Select2SingleChoice<>("mainBusinessLineField", new PropertyModel<>(this, "mainBusinessLineValue"), this.mainBusinessLineProvider);
        this.mainBusinessLineField.setLabel(Model.of("Client Classification"));
        this.mainBusinessLineField.add(new OnChangeAjaxBehavior());
        this.mainBusinessLineIContainer.add(this.mainBusinessLineField);
        this.mainBusinessLineFeedback = new TextFeedbackPanel("mainBusinessLineFeedback", this.mainBusinessLineField);
        this.mainBusinessLineIContainer.add(this.mainBusinessLineFeedback);
    }

    protected void initIncorporationNumberBlock() {
        this.incorporationNumberBlock = new WebMarkupBlock("incorporationNumberBlock", Size.Six_6);
        this.form.add(this.incorporationNumberBlock);
        this.incorporationNumberIContainer = new WebMarkupContainer("incorporationNumberIContainer");
        this.incorporationNumberBlock.add(this.incorporationNumberIContainer);
        this.incorporationNumberField = new TextField<>("incorporationNumberField", new PropertyModel<>(this, "incorporationNumberValue"));
        this.incorporationNumberField.setLabel(Model.of("Incorporation Number"));
        this.incorporationNumberField.add(new OnChangeAjaxBehavior());
        this.incorporationNumberIContainer.add(this.incorporationNumberField);
        this.incorporationNumberFeedback = new TextFeedbackPanel("incorporationNumberFeedback", this.incorporationNumberField);
        this.incorporationNumberIContainer.add(this.incorporationNumberFeedback);
    }

    protected void initClientClassificationBlock() {
        this.clientClassificationBlock = new WebMarkupBlock("clientClassificationBlock", Size.Six_6);
        this.form.add(this.clientClassificationBlock);
        this.clientClassificationIContainer = new WebMarkupContainer("clientClassificationIContainer");
        this.clientClassificationBlock.add(this.clientClassificationIContainer);
        this.clientClassificationProvider = new ClientClassificationProvider();
        this.clientClassificationField = new Select2SingleChoice<>("clientClassificationField", new PropertyModel<>(this, "clientClassificationValue"), this.clientClassificationProvider);
        this.clientClassificationField.setLabel(Model.of("Client Classification"));
        this.clientClassificationField.add(new OnChangeAjaxBehavior());
        this.clientClassificationIContainer.add(this.clientClassificationField);
        this.clientClassificationFeedback = new TextFeedbackPanel("clientClassificationFeedback", this.clientClassificationField);
        this.clientClassificationIContainer.add(this.clientClassificationFeedback);
    }

    protected void initClientTypeBlock() {
        this.clientTypeBlock = new WebMarkupBlock("clientTypeBlock", Size.Six_6);
        this.form.add(this.clientTypeBlock);
        this.clientTypeIContainer = new WebMarkupContainer("clientTypeIContainer");
        this.clientTypeBlock.add(this.clientTypeIContainer);
        this.clientTypeProvider = new ClientTypeProvider();
        this.clientTypeField = new Select2SingleChoice<>("clientTypeField", new PropertyModel<>(this, "clientTypeValue"), this.clientTypeProvider);
        this.clientTypeField.setLabel(Model.of("Client Type"));
        this.clientTypeField.add(new OnChangeAjaxBehavior());
        this.clientTypeIContainer.add(this.clientTypeField);
        this.clientTypeFeedback = new TextFeedbackPanel("clientTypeFeedback", this.clientTypeField);
        this.clientTypeIContainer.add(this.clientTypeFeedback);
    }

    protected void initIncorporationValidityTillDateBlock() {
        this.incorporationValidityTillDateBlock = new WebMarkupBlock("incorporationValidityTillDateBlock", Size.Six_6);
        this.form.add(this.incorporationValidityTillDateBlock);
        this.incorporationValidityTillDateIContainer = new WebMarkupContainer("incorporationValidityTillDateIContainer");
        this.incorporationValidityTillDateBlock.add(this.incorporationValidityTillDateIContainer);
        this.incorporationValidityTillDateField = new DateTextField("incorporationValidityTillDateField", new PropertyModel<>(this, "incorporationValidityTillDateValue"));
        this.incorporationValidityTillDateField.setLabel(Model.of("Incorporation Validity Till Date"));
        this.incorporationValidityTillDateField.add(new OnChangeAjaxBehavior());
        this.incorporationValidityTillDateIContainer.add(this.incorporationValidityTillDateField);
        this.incorporationValidityTillDateFeedback = new TextFeedbackPanel("incorporationValidityTillDateFeedback", this.incorporationValidityTillDateField);
        this.incorporationValidityTillDateIContainer.add(this.incorporationValidityTillDateFeedback);
    }

    protected void initIncorporationDateBlock() {
        this.incorporationDateBlock = new WebMarkupBlock("incorporationDateBlock", Size.Six_6);
        this.form.add(this.incorporationDateBlock);
        this.incorporationDateIContainer = new WebMarkupContainer("incorporationDateIContainer");
        this.incorporationDateBlock.add(this.incorporationDateIContainer);
        this.incorporationDateField = new DateTextField("incorporationDateField", new PropertyModel<>(this, "incorporationDateValue"));
        this.incorporationDateField.setLabel(Model.of("Incorporation Date"));
        this.incorporationDateField.add(new OnChangeAjaxBehavior());
        this.incorporationDateIContainer.add(this.incorporationDateField);
        this.incorporationDateFeedback = new TextFeedbackPanel("incorporationDateFeedback", this.incorporationDateField);
        this.incorporationDateIContainer.add(this.incorporationDateFeedback);
    }

    protected void initGenderBlock() {
        this.genderBlock = new WebMarkupBlock("genderBlock", Size.Four_4);
        this.form.add(this.genderBlock);
        this.genderIContainer = new WebMarkupContainer("genderIContainer");
        this.genderBlock.add(this.genderIContainer);
        this.genderProvider = new GenderProvider();
        this.genderField = new Select2SingleChoice<>("genderField", new PropertyModel<>(this, "genderValue"), this.genderProvider);
        this.genderField.setLabel(Model.of("Gender"));
        this.genderField.add(new OnChangeAjaxBehavior());
        this.genderIContainer.add(this.genderField);
        this.genderFeedback = new TextFeedbackPanel("genderFeedback", this.genderField);
        this.genderIContainer.add(this.genderFeedback);
    }

    protected void initDateOfBirthBlock() {
        this.dateOfBirthBlock = new WebMarkupBlock("dateOfBirthBlock", Size.Four_4);
        this.form.add(this.dateOfBirthBlock);
        this.dateOfBirthIContainer = new WebMarkupContainer("dateOfBirthIContainer");
        this.dateOfBirthBlock.add(this.dateOfBirthIContainer);
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.dateOfBirthField.add(new OnChangeAjaxBehavior());
        this.dateOfBirthIContainer.add(this.dateOfBirthField);
        this.dateOfBirthFeedback = new TextFeedbackPanel("dateOfBirthFeedback", this.dateOfBirthField);
        this.dateOfBirthIContainer.add(this.dateOfBirthFeedback);
    }

    protected void initMobileNumberBlock() {
        this.mobileNumberBlock = new WebMarkupBlock("mobileNumberBlock", Size.Four_4);
        this.form.add(this.mobileNumberBlock);
        this.mobileNumberIContainer = new WebMarkupContainer("mobileNumberIContainer");
        this.mobileNumberBlock.add(this.mobileNumberIContainer);
        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this, "mobileNumberValue"));
        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
        this.mobileNumberField.add(new OnChangeAjaxBehavior());
        this.mobileNumberIContainer.add(this.mobileNumberField);
        this.mobileNumberFeedback = new TextFeedbackPanel("mobileNumberFeedback", this.mobileNumberField);
        this.mobileNumberIContainer.add(this.mobileNumberFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Four_4);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initLastNameBlock() {
        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Four_4);
        this.form.add(this.lastNameBlock);
        this.lastNameIContainer = new WebMarkupContainer("lastNameIContainer");
        this.lastNameBlock.add(this.lastNameIContainer);
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameField.add(new OnChangeAjaxBehavior());
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.lastNameIContainer.add(this.lastNameFeedback);
    }

    protected void initMiddleNameBlock() {
        this.middleNameBlock = new WebMarkupBlock("middleNameBlock", Size.Four_4);
        this.form.add(this.middleNameBlock);
        this.middleNameIContainer = new WebMarkupContainer("middleNameIContainer");
        this.middleNameBlock.add(this.middleNameIContainer);
        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this, "middleNameValue"));
        this.middleNameField.setLabel(Model.of("Middle Name"));
        this.middleNameField.add(new OnChangeAjaxBehavior());
        this.middleNameIContainer.add(this.middleNameField);
        this.middleNameFeedback = new TextFeedbackPanel("middleNameFeedback", this.middleNameField);
        this.middleNameIContainer.add(this.middleNameFeedback);
    }

    protected void initFirstNameBlock() {
        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Four_4);
        this.form.add(this.firstNameBlock);
        this.firstNameIContainer = new WebMarkupContainer("firstNameIContainer");
        this.firstNameBlock.add(this.firstNameIContainer);
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameField.add(new OnChangeAjaxBehavior());
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.firstNameIContainer.add(this.firstNameFeedback);
    }

    protected void initStaffApplicationBlock() {
        this.staffApplicationBlock = new WebMarkupBlock("staffApplicationBlock", Size.Six_6);
        this.form.add(this.staffApplicationBlock);
        this.staffApplicationIContainer = new WebMarkupContainer("staffApplicationIContainer");
        this.staffApplicationBlock.add(this.staffApplicationIContainer);
        this.staffApplicationField = new CheckBox("staffApplicationField", new PropertyModel<>(this, "staffApplicationValue"));
        this.staffApplicationField.add(new OnChangeAjaxBehavior());
        this.staffApplicationIContainer.add(this.staffApplicationField);
        this.staffApplicationFeedback = new TextFeedbackPanel("staffApplicationFeedback", this.staffApplicationField);
        this.staffApplicationIContainer.add(this.staffApplicationFeedback);
    }

    protected void initLegalFormBlock() {
        this.legalFormBlock = new WebMarkupBlock("legalFormBlock", Size.Six_6);
        this.form.add(this.legalFormBlock);
        this.legalFormIContainer = new WebMarkupContainer("legalFormIContainer");
        this.legalFormBlock.add(this.legalFormIContainer);
        this.legalFormProvider = new LegalFormProvider();
        this.legalFormField = new Select2SingleChoice<>("legalFormField", new PropertyModel<>(this, "legalFormValue"), this.legalFormProvider);
        this.legalFormField.setLabel(Model.of("Legal Form"));
        this.legalFormField.add(new OnChangeAjaxBehavior(this::legalFormFieldUpdate));
        this.legalFormIContainer.add(this.legalFormField);
        this.legalFormFeedback = new TextFeedbackPanel("legalFormFeedback", this.legalFormField);
        this.legalFormIContainer.add(this.legalFormFeedback);
    }

    protected void initStaffBlock() {
        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
        this.form.add(this.staffBlock);
        this.staffIContainer = new WebMarkupContainer("staffIContainer");
        this.staffBlock.add(this.staffIContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "m_staff.id", "m_staff.display_name");
        this.staffProvider.applyJoin("m_office", "inner join m_office on m_staff.office_id = m_office.id");
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffIContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffIContainer.add(this.staffFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected boolean familyMemberAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.popupModel.put("dateOfBirthValue", DateTime.now().minusYears(18).toDate());
        this.familyMemberPopup.setContent(new FamilyMemberPopup(this.familyMemberPopup.getContentId(), this.familyMemberPopup, this.popupModel));
        this.familyMemberPopup.show(target);
        return false;
    }

    protected ItemPanel familyMemberColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("relationship".equals(column) || "gender".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("firstName".equals(column) || "middleName".equals(column) || "lastName".equals(column) || "age".equals(column) || "mobileNumber".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void familyMemberClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> familyMemberAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void familyMemberPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("relationship", this.popupModel.get("relationshipValue"));
        item.put("firstName", this.popupModel.get("firstNameValue"));
        item.put("middleName", this.popupModel.get("middleNameValue"));
        item.put("lastName", this.popupModel.get("lastNameValue"));
        item.put("gender", this.popupModel.get("genderValue"));
        item.put("mobileNumber", this.popupModel.get("mobileNumberValue"));
        item.put("qualificationValue", this.popupModel.get("qualificationValue"));
        item.put("dependentValue", this.popupModel.get("dependentValue"));
        item.put("professionValue", this.popupModel.get("professionValue"));
        item.put("maritalStatusValue", this.popupModel.get("maritalStatusValue"));
        item.put("dateOfBirthValue", this.popupModel.get("dateOfBirthValue"));
        if (this.popupModel.get("dateOfBirthValue") != null) {
            item.put("age", String.valueOf(Years.yearsBetween(new DateTime((Date) this.popupModel.get("dateOfBirthValue")), DateTime.now()).getYears()));
        }
        this.familyMemberValue.add(item);
        target.add(this.familyMemberTable);
    }

    protected boolean openSavingsAccountFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.openSavingsAccountValue == null ? false : this.openSavingsAccountValue;
        this.savingsAccountIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.savingsAccountBlock);
        }
        return false;
    }

    protected boolean activeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.activeValue == null ? false : this.activeValue;
        this.activationDateIContainer.setVisible(visible);
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
        this.staffIContainer.setVisible(visible);
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
        this.firstNameIContainer.setVisible(peopleVisible);
        this.middleNameIContainer.setVisible(peopleVisible);
        this.staffApplicationIContainer.setVisible(peopleVisible);
        this.lastNameIContainer.setVisible(peopleVisible);
        this.dateOfBirthIContainer.setVisible(peopleVisible);
        this.genderIContainer.setVisible(peopleVisible);

        boolean entityVisible = legalForm == LegalForm.Entity;
        this.nameIContainer.setVisible(entityVisible);
        this.incorporationDateIContainer.setVisible(entityVisible);
        this.incorporationValidityTillDateIContainer.setVisible(entityVisible);
        this.incorporationNumberIContainer.setVisible(entityVisible);
        this.mainBusinessLineIContainer.setVisible(entityVisible);
        this.constitutionIContainer.setVisible(entityVisible);
        this.remarkIContainer.setVisible(entityVisible);

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
                f.withAge(Long.valueOf(age));
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
