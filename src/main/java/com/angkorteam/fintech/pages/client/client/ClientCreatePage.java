package com.angkorteam.fintech.pages.client.client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
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
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ClientBuilder;
import com.angkorteam.fintech.dto.builder.FamilyMemberBuilder;
import com.angkorteam.fintech.dto.enums.DepositType;
import com.angkorteam.fintech.dto.enums.LegalForm;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
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

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;

    protected UIBlock staffBlock;
    protected UIContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;

    protected UIRow row2;

    protected UIBlock legalFormBlock;
    protected UIContainer legalFormIContainer;
    protected LegalFormProvider legalFormProvider;
    protected Option legalFormValue;
    protected Select2SingleChoice<Option> legalFormField;

    protected UIBlock staffApplicationBlock;
    protected UIContainer staffApplicationIContainer;
    protected Boolean staffApplicationValue;
    protected CheckBox staffApplicationField;

    protected UIRow row3;

    protected UIBlock firstNameBlock;
    protected UIContainer firstNameIContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;

    protected UIBlock middleNameBlock;
    protected UIContainer middleNameIContainer;
    protected String middleNameValue;
    protected TextField<String> middleNameField;

    protected UIBlock lastNameBlock;
    protected UIContainer lastNameIContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;

    protected UIRow row4;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock mobileNumberBlock;
    protected UIContainer mobileNumberIContainer;
    protected String mobileNumberValue;
    protected TextField<String> mobileNumberField;

    protected UIBlock dateOfBirthBlock;
    protected UIContainer dateOfBirthIContainer;
    protected Date dateOfBirthValue;
    protected DateTextField dateOfBirthField;

    protected UIBlock genderBlock;
    protected UIContainer genderIContainer;
    protected GenderProvider genderProvider;
    protected Option genderValue;
    protected Select2SingleChoice<Option> genderField;

    protected UIRow row6;

    protected UIBlock incorporationDateBlock;
    protected UIContainer incorporationDateIContainer;
    protected Date incorporationDateValue;
    protected DateTextField incorporationDateField;

    protected UIBlock incorporationValidityTillDateBlock;
    protected UIContainer incorporationValidityTillDateIContainer;
    protected Date incorporationValidityTillDateValue;
    protected DateTextField incorporationValidityTillDateField;

    protected UIRow row7;

    protected UIBlock clientTypeBlock;
    protected UIContainer clientTypeIContainer;
    protected ClientTypeProvider clientTypeProvider;
    protected Option clientTypeValue;
    protected Select2SingleChoice<Option> clientTypeField;

    protected UIBlock clientClassificationBlock;
    protected UIContainer clientClassificationIContainer;
    protected ClientClassificationProvider clientClassificationProvider;
    protected Option clientClassificationValue;
    protected Select2SingleChoice<Option> clientClassificationField;

    protected UIRow row8;

    protected UIBlock incorporationNumberBlock;
    protected UIContainer incorporationNumberIContainer;
    protected Long incorporationNumberValue;
    protected TextField<Long> incorporationNumberField;

    protected UIBlock mainBusinessLineBlock;
    protected UIContainer mainBusinessLineIContainer;
    protected MainBusinessLineProvider mainBusinessLineProvider;
    protected Option mainBusinessLineValue;
    protected Select2SingleChoice<Option> mainBusinessLineField;

    protected UIRow row9;

    protected UIBlock constitutionBlock;
    protected UIContainer constitutionIContainer;
    protected ConstitutionProvider constitutionProvider;
    protected Select2SingleChoice<Option> constitutionField;
    protected Option constitutionValue;

    protected UIBlock row9Block1;

    protected UIRow row10;

    protected UIBlock remarkBlock;
    protected UIContainer remarkIContainer;
    protected String remarkValue;
    protected TextArea<String> remarkField;

    protected UIBlock row10Block1;

    protected UIRow row11;

    protected UIBlock activeBlock;
    protected UIContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;

    protected UIBlock activationDateBlock;
    protected UIContainer activationDateIContainer;
    protected Date activationDateValue;
    protected DateTextField activationDateField;

    protected UIBlock submittedOnBlock;
    protected UIContainer submittedOnIContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;

    protected UIRow row12;

    protected UIBlock externalIdBlock;
    protected UIContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;

    protected UIBlock openSavingsAccountBlock;
    protected UIContainer openSavingsAccountIContainer;
    protected Boolean openSavingsAccountValue;
    protected CheckBox openSavingsAccountField;

    protected UIBlock savingsAccountBlock;
    protected UIContainer savingsAccountIContainer;
    protected SingleChoiceProvider savingsAccountProvider;
    protected Option savingsAccountValue;
    protected Select2SingleChoice<Option> savingsAccountField;

    protected UIRow row13;

    protected UIBlock familyMemberBlock;
    protected UIContainer familyMemberIContainer;
    protected List<Map<String, Object>> familyMemberValue;
    protected DataTable<Map<String, Object>, String> familyMemberTable;
    protected ListDataProvider familyMemberProvider;
    protected List<IColumn<Map<String, Object>, String>> familyMemberColumn;
    protected AjaxLink<Void> familyMemberAddLink;

    protected ModalWindow modalWindow;
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
            breadcrumb.setLabel("Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Six_6);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.staffBlock = this.row1.newUIBlock("staffBlock", Size.Six_6);
        this.staffIContainer = this.staffBlock.newUIContainer("staffIContainer");
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffIContainer.add(this.staffField);
        this.staffIContainer.newFeedback("staffFeedback", this.staffField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.legalFormBlock = this.row2.newUIBlock("legalFormBlock", Size.Six_6);
        this.legalFormIContainer = this.legalFormBlock.newUIContainer("legalFormIContainer");
        this.legalFormField = new Select2SingleChoice<>("legalFormField", new PropertyModel<>(this, "legalFormValue"), this.legalFormProvider);
        this.legalFormIContainer.add(this.legalFormField);
        this.legalFormIContainer.newFeedback("legalFormFeedback", this.legalFormField);

        this.staffApplicationBlock = this.row2.newUIBlock("staffApplicationBlock", Size.Six_6);
        this.staffApplicationIContainer = this.staffApplicationBlock.newUIContainer("staffApplicationIContainer");
        this.staffApplicationField = new CheckBox("staffApplicationField", new PropertyModel<>(this, "staffApplicationValue"));
        this.staffApplicationIContainer.add(this.staffApplicationField);
        this.staffApplicationIContainer.newFeedback("staffApplicationFeedback", this.staffApplicationField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.firstNameBlock = this.row3.newUIBlock("firstNameBlock", Size.Four_4);
        this.firstNameIContainer = this.firstNameBlock.newUIContainer("firstNameIContainer");
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameIContainer.newFeedback("firstNameFeedback", this.firstNameField);

        this.middleNameBlock = this.row3.newUIBlock("middleNameBlock", Size.Four_4);
        this.middleNameIContainer = this.middleNameBlock.newUIContainer("middleNameIContainer");
        this.middleNameField = new TextField<>("middleNameField", new PropertyModel<>(this, "middleNameValue"));
        this.middleNameIContainer.add(this.middleNameField);
        this.middleNameIContainer.newFeedback("middleNameFeedback", this.middleNameField);

        this.lastNameBlock = this.row3.newUIBlock("lastNameBlock", Size.Four_4);
        this.lastNameIContainer = this.lastNameBlock.newUIContainer("lastNameIContainer");
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameIContainer.newFeedback("lastNameFeedback", this.lastNameField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.nameBlock = this.row4.newUIBlock("nameBlock", Size.Four_4);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Eight_8);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.mobileNumberBlock = this.row5.newUIBlock("mobileNumberBlock", Size.Four_4);
        this.mobileNumberIContainer = this.mobileNumberBlock.newUIContainer("mobileNumberIContainer");
        this.mobileNumberField = new TextField<>("mobileNumberField", new PropertyModel<>(this, "mobileNumberValue"));
        this.mobileNumberIContainer.add(this.mobileNumberField);
        this.mobileNumberIContainer.newFeedback("mobileNumberFeedback", this.mobileNumberField);

        this.dateOfBirthBlock = this.row5.newUIBlock("dateOfBirthBlock", Size.Four_4);
        this.dateOfBirthIContainer = this.dateOfBirthBlock.newUIContainer("dateOfBirthIContainer");
        this.dateOfBirthField = new DateTextField("dateOfBirthField", new PropertyModel<>(this, "dateOfBirthValue"));
        this.dateOfBirthIContainer.add(this.dateOfBirthField);
        this.dateOfBirthIContainer.newFeedback("dateOfBirthFeedback", this.dateOfBirthField);

        this.genderBlock = this.row5.newUIBlock("genderBlock", Size.Four_4);
        this.genderIContainer = this.genderBlock.newUIContainer("genderIContainer");
        this.genderField = new Select2SingleChoice<>("genderField", new PropertyModel<>(this, "genderValue"), this.genderProvider);
        this.genderIContainer.add(this.genderField);
        this.genderIContainer.newFeedback("genderFeedback", this.genderField);

        this.row6 = UIRow.newUIRow("row6", this.form);

        this.incorporationDateBlock = this.row6.newUIBlock("incorporationDateBlock", Size.Six_6);
        this.incorporationDateIContainer = this.incorporationDateBlock.newUIContainer("incorporationDateIContainer");
        this.incorporationDateField = new DateTextField("incorporationDateField", new PropertyModel<>(this, "incorporationDateValue"));
        this.incorporationDateIContainer.add(this.incorporationDateField);
        this.incorporationDateIContainer.newFeedback("incorporationDateFeedback", this.incorporationDateField);

        this.incorporationValidityTillDateBlock = this.row6.newUIBlock("incorporationValidityTillDateBlock", Size.Six_6);
        this.incorporationValidityTillDateIContainer = this.incorporationValidityTillDateBlock.newUIContainer("incorporationValidityTillDateIContainer");
        this.incorporationValidityTillDateField = new DateTextField("incorporationValidityTillDateField", new PropertyModel<>(this, "incorporationValidityTillDateValue"));
        this.incorporationValidityTillDateIContainer.add(this.incorporationValidityTillDateField);
        this.incorporationValidityTillDateIContainer.newFeedback("incorporationValidityTillDateFeedback", this.incorporationValidityTillDateField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.clientTypeBlock = this.row7.newUIBlock("clientTypeBlock", Size.Six_6);
        this.clientTypeIContainer = this.clientTypeBlock.newUIContainer("clientTypeIContainer");
        this.clientTypeField = new Select2SingleChoice<>("clientTypeField", new PropertyModel<>(this, "clientTypeValue"), this.clientTypeProvider);
        this.clientTypeIContainer.add(this.clientTypeField);
        this.clientTypeIContainer.newFeedback("clientTypeFeedback", this.clientTypeField);

        this.clientClassificationBlock = this.row7.newUIBlock("clientClassificationBlock", Size.Six_6);
        this.clientClassificationIContainer = this.clientClassificationBlock.newUIContainer("clientClassificationIContainer");
        this.clientClassificationField = new Select2SingleChoice<>("clientClassificationField", new PropertyModel<>(this, "clientClassificationValue"), this.clientClassificationProvider);
        this.clientClassificationIContainer.add(this.clientClassificationField);
        this.clientClassificationIContainer.newFeedback("clientClassificationFeedback", this.clientClassificationField);

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.mainBusinessLineBlock = this.row8.newUIBlock("mainBusinessLineBlock", Size.Six_6);
        this.mainBusinessLineIContainer = this.mainBusinessLineBlock.newUIContainer("mainBusinessLineIContainer");
        this.mainBusinessLineField = new Select2SingleChoice<>("mainBusinessLineField", new PropertyModel<>(this, "mainBusinessLineValue"), this.mainBusinessLineProvider);
        this.mainBusinessLineIContainer.add(this.mainBusinessLineField);
        this.mainBusinessLineIContainer.newFeedback("mainBusinessLineFeedback", this.mainBusinessLineField);

        this.incorporationNumberBlock = this.row8.newUIBlock("incorporationNumberBlock", Size.Six_6);
        this.incorporationNumberIContainer = this.incorporationNumberBlock.newUIContainer("incorporationNumberIContainer");
        this.incorporationNumberField = new TextField<>("incorporationNumberField", new PropertyModel<>(this, "incorporationNumberValue"));
        this.incorporationNumberIContainer.add(this.incorporationNumberField);
        this.incorporationNumberIContainer.newFeedback("incorporationNumberFeedback", this.incorporationNumberField);

        this.row9 = UIRow.newUIRow("row9", this.form);

        this.constitutionBlock = this.row9.newUIBlock("constitutionBlock", Size.Six_6);
        this.constitutionIContainer = this.constitutionBlock.newUIContainer("constitutionIContainer");
        this.constitutionField = new Select2SingleChoice<>("constitutionField", new PropertyModel<>(this, "constitutionValue"), this.constitutionProvider);
        this.constitutionIContainer.add(this.constitutionField);
        this.constitutionIContainer.newFeedback("constitutionFeedback", this.constitutionField);

        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Six_6);

        this.row10 = UIRow.newUIRow("row10", this.form);

        this.remarkBlock = this.row10.newUIBlock("remarkBlock", Size.Six_6);
        this.remarkIContainer = this.remarkBlock.newUIContainer("remarkIContainer");
        this.remarkField = new TextArea<>("remarkField", new PropertyModel<>(this, "remarkValue"));
        this.remarkIContainer.add(this.remarkField);
        this.remarkIContainer.newFeedback("remarkFeedback", this.remarkField);

        this.row10Block1 = this.row10.newUIBlock("row10lock1", Size.Six_6);

        this.row11 = UIRow.newUIRow("row11", this.form);

        this.activeBlock = this.row11.newUIBlock("activeBlock", Size.Four_4);
        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeIContainer.add(this.activeField);
        this.activeIContainer.newFeedback("activeFeedback", this.activeField);

        this.activationDateBlock = this.row11.newUIBlock("activationDateBlock", Size.Four_4);
        this.activationDateIContainer = this.activationDateBlock.newUIContainer("activationDateIContainer");
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateIContainer.add(this.activationDateField);
        this.activationDateIContainer.newFeedback("activationDateFeedback", this.activationDateField);

        this.submittedOnBlock = this.row11.newUIBlock("submittedOnBlock", Size.Four_4);
        this.submittedOnIContainer = this.submittedOnBlock.newUIContainer("submittedOnIContainer");
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnIContainer.newFeedback("submittedOnFeedback", this.submittedOnField);

        this.row12 = UIRow.newUIRow("row12", this.form);

        this.openSavingsAccountBlock = this.row12.newUIBlock("openSavingsAccountBlock", Size.Four_4);
        this.openSavingsAccountIContainer = this.openSavingsAccountBlock.newUIContainer("openSavingsAccountIContainer");
        this.openSavingsAccountField = new CheckBox("openSavingsAccountField", new PropertyModel<>(this, "openSavingsAccountValue"));
        this.openSavingsAccountIContainer.add(this.openSavingsAccountField);
        this.openSavingsAccountIContainer.newFeedback("openSavingsAccountFeedback", this.openSavingsAccountField);

        this.externalIdBlock = this.row12.newUIBlock("externalIdBlock", Size.Four_4);
        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.savingsAccountBlock = this.row12.newUIBlock("savingsAccountBlock", Size.Four_4);
        this.savingsAccountIContainer = this.savingsAccountBlock.newUIContainer("savingsAccountIContainer");
        this.savingsAccountField = new Select2SingleChoice<>("savingsAccountField", new PropertyModel<>(this, "savingsAccountValue"), this.savingsAccountProvider);
        this.savingsAccountIContainer.add(this.savingsAccountField);
        this.savingsAccountIContainer.newFeedback("savingsAccountFeedback", this.savingsAccountField);

        this.row13 = UIRow.newUIRow("row13", this.form);

        this.familyMemberBlock = this.row13.newUIBlock("familyMemberBlock", Size.Twelve_12);
        this.familyMemberIContainer = this.familyMemberBlock.newUIContainer("familyMemberIContainer");
        this.familyMemberTable = new DataTable<>("familyMemberTable", this.familyMemberColumn, this.familyMemberProvider, 20);
        this.familyMemberIContainer.add(this.familyMemberTable);
        this.familyMemberTable.addTopToolbar(new HeadersToolbar<>(this.familyMemberTable, this.familyMemberProvider));
        this.familyMemberTable.addBottomToolbar(new NoRecordsToolbar(this.familyMemberTable));
        this.familyMemberAddLink = new AjaxLink<>("familyMemberAddLink");
        this.familyMemberAddLink.setOnClick(this::familyMemberAddLinkClick);
        this.familyMemberIContainer.add(this.familyMemberAddLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);
    }

    @Override
    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.popupModel = Maps.newHashMap();

        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);

        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.NAME + "." + MStaff.Field.ID, MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME);
        this.staffProvider.applyJoin("m_office", "INNER JOIN " + MOffice.NAME + " ON " + MStaff.NAME + "." + MStaff.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);

        this.legalFormProvider = new LegalFormProvider();

        this.genderProvider = new GenderProvider();

        this.clientTypeProvider = new ClientTypeProvider();

        this.clientClassificationProvider = new ClientClassificationProvider();

        this.mainBusinessLineProvider = new MainBusinessLineProvider();

        this.constitutionProvider = new ConstitutionProvider();

        this.savingsAccountProvider = new SingleChoiceProvider(MSavingsProduct.NAME, MSavingsProduct.Field.ID, MSavingsProduct.Field.NAME);
        this.savingsAccountProvider.applyWhere("deposit_type_enum", MSavingsProduct.Field.DEPOSIT_TYPE_ENUM + " = " + DepositType.Saving.getLiteral());

        this.familyMemberColumn = Lists.newLinkedList();
        this.familyMemberColumn.add(new TextColumn(Model.of("Relationship"), "relationship", "relationship", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("First Name"), "firstName", "firstName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Middle Name"), "middleName", "middleName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Last Name"), "lastName", "lastName", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Age"), "age", "age", this::familyMemberColumn));
        this.familyMemberColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::familyMemberColumn));
        this.familyMemberColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::familyMemberAction, this::familyMemberClick));

        this.familyMemberValue = Lists.newArrayList();
        this.familyMemberProvider = new ListDataProvider(this.familyMemberValue);

        this.submittedOnValue = new Date();
        this.activationDateValue = new Date();
        this.incorporationValidityTillDateValue = DateTime.now().plusYears(1).toDate();
        this.incorporationDateValue = new Date();
        this.dateOfBirthValue = DateTime.now().minusYears(18).toDate();
    }

    @Override
    protected void configureMetaData() {
        this.savingsAccountField.setLabel(Model.of("Savings Account"));
        this.savingsAccountField.add(new OnChangeAjaxBehavior());

        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());

        this.openSavingsAccountField.add(new OnChangeAjaxBehavior(this::openSavingsAccountFieldUpdate));

        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());

        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());

        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));

        this.remarkField.setLabel(Model.of("Remark"));
        this.remarkField.add(new OnChangeAjaxBehavior());

        this.constitutionField.setLabel(Model.of("Constitution"));
        this.constitutionField.add(new OnChangeAjaxBehavior());

        this.incorporationNumberField.setLabel(Model.of("Incorporation Number"));
        this.incorporationNumberField.add(new OnChangeAjaxBehavior());

        this.mainBusinessLineField.setLabel(Model.of("Client Classification"));
        this.mainBusinessLineField.add(new OnChangeAjaxBehavior());

        this.clientTypeField.setLabel(Model.of("Client Type"));
        this.clientTypeField.add(new OnChangeAjaxBehavior());

        this.clientClassificationField.setLabel(Model.of("Client Classification"));
        this.clientClassificationField.add(new OnChangeAjaxBehavior());

        this.incorporationValidityTillDateField.setLabel(Model.of("Incorporation Validity Till Date"));
        this.incorporationValidityTillDateField.add(new OnChangeAjaxBehavior());

        this.incorporationDateField.setLabel(Model.of("Incorporation Date"));
        this.incorporationDateField.add(new OnChangeAjaxBehavior());

        this.genderField.setLabel(Model.of("Gender"));
        this.genderField.add(new OnChangeAjaxBehavior());

        this.dateOfBirthField.setLabel(Model.of("Date Of Birth"));
        this.dateOfBirthField.add(new OnChangeAjaxBehavior());

        this.mobileNumberField.setLabel(Model.of("Mobile Number"));
        this.mobileNumberField.add(new OnChangeAjaxBehavior());

        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());

        this.lastNameField.setLabel(Model.of("Last Name"));
        this.lastNameField.add(new OnChangeAjaxBehavior());

        this.middleNameField.setLabel(Model.of("Middle Name"));
        this.middleNameField.add(new OnChangeAjaxBehavior());

        this.firstNameField.setLabel(Model.of("First Name"));
        this.firstNameField.add(new OnChangeAjaxBehavior());

        this.staffApplicationField.add(new OnChangeAjaxBehavior());

        this.legalFormField.setLabel(Model.of("Legal Form"));
        this.legalFormField.add(new OnChangeAjaxBehavior(this::legalFormFieldUpdate));

        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());

        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));

        legalFormFieldUpdate(null);
        officeFieldUpdate(null);
        activeFieldUpdate(null);
        openSavingsAccountFieldUpdate(null);
    }

    protected boolean familyMemberAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.popupModel.put("dateOfBirthValue", DateTime.now().minusYears(18).toDate());
        this.modalWindow.setContent(new FamilyMemberPopup("familyMemberPopup", this.popupModel));
        this.modalWindow.show(target);
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

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
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
            this.staffProvider.applyWhere("office", MOffice.NAME + "." + MOffice.Field.ID + " = " + this.officeValue.getId());
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
            if (dateOfBirth != null) {
                LocalDate start = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate stop = LocalDate.now();
                long years = java.time.temporal.ChronoUnit.YEARS.between(start, stop);
                f.withAge(years);
            }
            builder.withFamilyMembers(f.build().getObject());
        }

        JsonNode node = ClientHelper.createClient((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ClientBrowsePage.class);
    }

}
