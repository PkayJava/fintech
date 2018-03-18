package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.Destination;
import com.angkorteam.fintech.dto.enums.StandingInstructionAccountType;
import com.angkorteam.fintech.dto.enums.TransferType;
import com.angkorteam.fintech.provider.ClientDepositAccountProvider;
import com.angkorteam.fintech.provider.DestinationProvider;
import com.angkorteam.fintech.provider.InstructionTypeProvider;
import com.angkorteam.fintech.provider.OfficeProvider;
import com.angkorteam.fintech.provider.PriorityProvider;
import com.angkorteam.fintech.provider.RecurrenceFrequencyProvider;
import com.angkorteam.fintech.provider.RecurrenceTypeProvider;
import com.angkorteam.fintech.provider.StatusProvider;
import com.angkorteam.fintech.provider.TransferTypeProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientStandingInstructionCreatePage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock applicantBlock;
    protected WebMarkupContainer applicantVContainer;
    protected String applicantValue;
    protected ReadOnlyView applicantView;
    protected TextFeedbackPanel applicantFeedback;

    protected TransferTypeProvider typeProvider;
    protected WebMarkupBlock typeBlock;
    protected WebMarkupContainer typeIContainer;
    protected Option typeValue;
    protected Select2SingleChoice<Option> typeField;
    protected TextFeedbackPanel typeFeedback;

    protected PriorityProvider priorityProvider;
    protected WebMarkupBlock priorityBlock;
    protected WebMarkupContainer priorityIContainer;
    protected Option priorityValue;
    protected Select2SingleChoice<Option> priorityField;
    protected TextFeedbackPanel priorityFeedback;

    protected StatusProvider statusProvider;
    protected WebMarkupBlock statusBlock;
    protected WebMarkupContainer statusIContainer;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;
    protected TextFeedbackPanel statusFeedback;

    protected WebMarkupBlock fromAccountTypeBlock;
    protected WebMarkupContainer fromAccountTypeVContainer;
    protected Option fromAccountTypeValue;
    protected ReadOnlyView fromAccountTypeView;

    protected ClientDepositAccountProvider fromAccountProvider;
    protected WebMarkupBlock fromAccountBlock;
    protected WebMarkupContainer fromAccountIContainer;
    protected Option fromAccountValue;
    protected Select2SingleChoice<Option> fromAccountField;
    protected TextFeedbackPanel fromAccountFeedback;

    protected DestinationProvider destinationProvider;
    protected WebMarkupBlock destinationBlock;
    protected WebMarkupContainer destinationIContainer;
    protected Option destinationValue;
    protected Select2SingleChoice<Option> destinationField;
    protected TextFeedbackPanel destinationFeedback;

    protected OfficeProvider toOfficeProvider;
    protected WebMarkupBlock toOfficeBlock;
    protected WebMarkupContainer toOfficeIContainer;
    protected Select2SingleChoice<Option> toOfficeField;
    protected TextFeedbackPanel toOfficeFeedback;
    protected WebMarkupContainer toOfficeVContainer;
    protected ReadOnlyView toOfficeView;
    protected Option toOfficeValue;

    protected OfficeProvider beneficiaryProvider;
    protected WebMarkupBlock beneficiaryBlock;
    protected WebMarkupContainer beneficiaryIContainer;
    protected Select2SingleChoice<Option> beneficiaryField;
    protected TextFeedbackPanel beneficiaryFeedback;
    protected WebMarkupContainer beneficiaryVContainer;
    protected ReadOnlyView beneficiaryView;
    protected Option beneficiaryValue;

    protected WebMarkupBlock toAccountTypeBlock;
    protected WebMarkupContainer toAccountTypeVContainer;
    protected Option toAccountTypeValue;
    protected ReadOnlyView toAccountTypeView;

    protected OfficeProvider toAccountProvider;
    protected WebMarkupBlock toAccountBlock;
    protected WebMarkupContainer toAccountIContainer;
    protected Option toAccountValue;
    protected Select2SingleChoice<Option> toAccountField;
    protected TextFeedbackPanel toAccountFeedback;

    protected InstructionTypeProvider standingInstructionTypeProvider;
    protected WebMarkupBlock standingInstructionTypeBlock;
    protected WebMarkupContainer standingInstructionTypeIContainer;
    protected Option standingInstructionTypeValue;
    protected Select2SingleChoice<Option> standingInstructionTypeField;
    protected TextFeedbackPanel standingInstructionTypeFeedback;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock validFromBlock;
    protected WebMarkupContainer validFromIContainer;
    protected Date validFromValue;
    protected DateTextField validFromField;
    protected TextFeedbackPanel validFromFeedback;

    protected WebMarkupBlock validUntilBlock;
    protected WebMarkupContainer validUntilIContainer;
    protected Date validUntilValue;
    protected DateTextField validUntilField;
    protected TextFeedbackPanel validUntilFeedback;

    protected RecurrenceTypeProvider recurrenceTypeProvider;
    protected WebMarkupBlock recurrenceTypeBlock;
    protected WebMarkupContainer recurrenceTypeIContainer;
    protected Option recurrenceTypeValue;
    protected Select2SingleChoice<Option> recurrenceTypeField;
    protected TextFeedbackPanel recurrenceTypeFeedback;

    protected WebMarkupBlock intervalBlock;
    protected WebMarkupContainer intervalIContainer;
    protected Long intervalValue;
    protected TextField<Long> intervalField;
    protected TextFeedbackPanel intervalFeedback;

    protected RecurrenceFrequencyProvider recurrenceFrequencyProvider;
    protected WebMarkupBlock recurrenceFrequencyBlock;
    protected WebMarkupContainer recurrenceFrequencyIContainer;
    protected Option recurrenceFrequencyValue;
    protected Select2SingleChoice<Option> recurrenceFrequencyField;
    protected TextFeedbackPanel recurrenceFrequencyFeedback;

    protected WebMarkupBlock onMonthDayBlock;
    protected WebMarkupContainer onMonthDayIContainer;
    protected Date onMonthDayValue;
    protected DayMonthTextField onMonthDayField;
    protected TextFeedbackPanel onMonthDayFeedback;

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        this.clientId = getPageParameters().get("clientId").toString();
        this.destinationValue = Destination.OwnAccount.toOption();
        this.fromAccountTypeValue = StandingInstructionAccountType.SavingAccount.toOption();
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.applicantValue = (String) clientObject.get("display_name");
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
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Standing Instruction");
            breadcrumb.setPage(ClientStandingInstructionBrowsePage.class);
            breadcrumb.setParameters(parameters);
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
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientStandingInstructionBrowsePage.class, parameters);
        this.form.add(this.closeLink);

        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);

        this.applicantBlock = new WebMarkupBlock("applicantBlock", Size.Six_6);
        this.form.add(this.applicantBlock);
        this.applicantVContainer = new WebMarkupContainer("applicantVContainer");
        this.applicantBlock.add(this.applicantVContainer);
        this.applicantView = new ReadOnlyView("applicantView", new PropertyModel<>(this, "applicantValue"));
        this.applicantVContainer.add(this.applicantView);

        this.statusProvider = new StatusProvider();
        this.statusBlock = new WebMarkupBlock("statusBlock", Size.Four_4);
        this.form.add(this.statusBlock);
        this.statusIContainer = new WebMarkupContainer("statusIContainer");
        this.statusBlock.add(this.statusIContainer);
        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusField.setLabel(Model.of("Status"));
        this.statusIContainer.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.statusIContainer.add(this.statusFeedback);

        this.typeProvider = new TransferTypeProvider();
        this.typeBlock = new WebMarkupBlock("typeBlock", Size.Four_4);
        this.form.add(this.typeBlock);
        this.typeIContainer = new WebMarkupContainer("typeIContainer");
        this.typeBlock.add(this.typeIContainer);
        this.typeField = new Select2SingleChoice<>("typeField", new PropertyModel<>(this, "typeValue"), this.typeProvider);
        this.typeField.add(new OnChangeAjaxBehavior(this::typeFieldUpdate));
        this.typeField.setLabel(Model.of("Type"));
        this.typeIContainer.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.typeIContainer.add(this.typeFeedback);

        this.priorityProvider = new PriorityProvider();
        this.priorityBlock = new WebMarkupBlock("priorityBlock", Size.Four_4);
        this.form.add(this.priorityBlock);
        this.priorityIContainer = new WebMarkupContainer("priorityIContainer");
        this.priorityBlock.add(this.priorityIContainer);
        this.priorityField = new Select2SingleChoice<>("priorityField", new PropertyModel<>(this, "priorityValue"), this.priorityProvider);
        this.priorityField.setLabel(Model.of("Priority"));
        this.priorityIContainer.add(this.priorityField);
        this.priorityFeedback = new TextFeedbackPanel("priorityFeedback", this.priorityField);
        this.priorityIContainer.add(this.priorityFeedback);

        this.fromAccountTypeBlock = new WebMarkupBlock("fromAccountTypeBlock", Size.Six_6);
        this.form.add(this.fromAccountTypeBlock);
        this.fromAccountTypeVContainer = new WebMarkupContainer("fromAccountTypeVContainer");
        this.fromAccountTypeBlock.add(this.fromAccountTypeVContainer);
        this.fromAccountTypeView = new ReadOnlyView("fromAccountTypeView", new PropertyModel<>(this, "fromAccountTypeValue"));
        this.fromAccountTypeVContainer.add(this.fromAccountTypeView);

        this.fromAccountProvider = new ClientDepositAccountProvider();
        this.fromAccountProvider.applyWhere("client_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.CLIENT_ID + " = '" + this.clientId + "'");
        this.fromAccountBlock = new WebMarkupBlock("fromAccountBlock", Size.Six_6);
        this.form.add(this.fromAccountBlock);
        this.fromAccountIContainer = new WebMarkupContainer("fromAccountIContainer");
        this.fromAccountBlock.add(this.fromAccountIContainer);
        this.fromAccountField = new Select2SingleChoice<>("fromAccountField", new PropertyModel<>(this, "fromAccountValue"), this.fromAccountProvider);
        this.fromAccountField.setLabel(Model.of("From Account"));
        this.fromAccountIContainer.add(this.fromAccountField);
        this.fromAccountFeedback = new TextFeedbackPanel("fromAccountFeedback", this.fromAccountField);
        this.fromAccountIContainer.add(this.fromAccountFeedback);

        this.destinationProvider = new DestinationProvider();
        this.destinationBlock = new WebMarkupBlock("destinationBlock", Size.Four_4);
        this.form.add(this.destinationBlock);
        this.destinationIContainer = new WebMarkupContainer("destinationIContainer");
        this.destinationBlock.add(this.destinationIContainer);
        this.destinationField = new Select2SingleChoice<>("destinationField", new PropertyModel<>(this, "destinationValue"), this.destinationProvider);
        this.destinationField.add(new OnChangeAjaxBehavior(this::destinationFieldUpdate));
        this.destinationField.setLabel(Model.of("Destination"));
        this.destinationIContainer.add(this.destinationField);
        this.destinationFeedback = new TextFeedbackPanel("destinationFeedback", this.destinationField);
        this.destinationIContainer.add(this.destinationFeedback);

        this.toOfficeProvider = new OfficeProvider();
        this.toOfficeBlock = new WebMarkupBlock("toOfficeBlock", Size.Four_4);
        this.form.add(this.toOfficeBlock);
        this.toOfficeIContainer = new WebMarkupContainer("toOfficeIContainer");
        this.toOfficeBlock.add(this.toOfficeIContainer);
        this.toOfficeField = new Select2SingleChoice<>("toOfficeField", new PropertyModel<>(this, "toOfficeValue"), this.toOfficeProvider);
        this.toOfficeField.setLabel(Model.of("To Office"));
        this.toOfficeIContainer.add(this.toOfficeField);
        this.toOfficeFeedback = new TextFeedbackPanel("toOfficeFeedback", this.toOfficeField);
        this.toOfficeIContainer.add(this.toOfficeFeedback);

        this.toOfficeVContainer = new WebMarkupContainer("toOfficeVContainer");
        this.toOfficeBlock.add(this.toOfficeVContainer);
        this.toOfficeView = new ReadOnlyView("toOfficeView", new PropertyModel<>(this, "toOfficeValue"));
        this.toOfficeVContainer.add(this.toOfficeView);

        this.beneficiaryProvider = new OfficeProvider();
        this.beneficiaryBlock = new WebMarkupBlock("beneficiaryBlock", Size.Four_4);
        this.form.add(this.beneficiaryBlock);
        this.beneficiaryIContainer = new WebMarkupContainer("beneficiaryIContainer");
        this.beneficiaryBlock.add(this.beneficiaryIContainer);
        this.beneficiaryField = new Select2SingleChoice<>("beneficiaryField", new PropertyModel<>(this, "beneficiaryValue"), this.beneficiaryProvider);
        this.beneficiaryField.setLabel(Model.of("Beneficiary"));
        this.beneficiaryIContainer.add(this.beneficiaryField);
        this.beneficiaryFeedback = new TextFeedbackPanel("beneficiaryFeedback", this.beneficiaryField);
        this.beneficiaryIContainer.add(this.beneficiaryFeedback);

        this.beneficiaryVContainer = new WebMarkupContainer("beneficiaryVContainer");
        this.beneficiaryBlock.add(this.beneficiaryVContainer);
        this.beneficiaryView = new ReadOnlyView("beneficiaryView", new PropertyModel<>(this, "beneficiaryValue"));
        this.beneficiaryVContainer.add(this.beneficiaryView);

        this.toAccountTypeBlock = new WebMarkupBlock("toAccountTypeBlock", Size.Six_6);
        this.form.add(this.toAccountTypeBlock);
        this.toAccountTypeVContainer = new WebMarkupContainer("toAccountTypeVContainer");
        this.toAccountTypeBlock.add(this.toAccountTypeVContainer);
        this.toAccountTypeView = new ReadOnlyView("toAccountTypeView", new PropertyModel<>(this, "toAccountTypeValue"));
        this.toAccountTypeVContainer.add(this.toAccountTypeView);

        this.toAccountProvider = new OfficeProvider();
        this.toAccountBlock = new WebMarkupBlock("toAccountBlock", Size.Six_6);
        this.form.add(this.toAccountBlock);
        this.toAccountIContainer = new WebMarkupContainer("toAccountIContainer");
        this.toAccountBlock.add(this.toAccountIContainer);
        this.toAccountField = new Select2SingleChoice<>("toAccountField", new PropertyModel<>(this, "toAccountValue"), this.toAccountProvider);
        this.toAccountField.setLabel(Model.of("To Account"));
        this.toAccountIContainer.add(this.toAccountField);
        this.toAccountFeedback = new TextFeedbackPanel("toAccountFeedback", this.toAccountField);
        this.toAccountIContainer.add(this.toAccountFeedback);

        this.standingInstructionTypeProvider = new InstructionTypeProvider();
        this.standingInstructionTypeBlock = new WebMarkupBlock("standingInstructionTypeBlock", Size.Six_6);
        this.form.add(this.standingInstructionTypeBlock);
        this.standingInstructionTypeIContainer = new WebMarkupContainer("standingInstructionTypeIContainer");
        this.standingInstructionTypeBlock.add(this.standingInstructionTypeIContainer);
        this.standingInstructionTypeField = new Select2SingleChoice<>("standingInstructionTypeField", new PropertyModel<>(this, "standingInstructionTypeValue"), this.standingInstructionTypeProvider);
        this.standingInstructionTypeField.setLabel(Model.of("Standing Instruction Type"));
        this.standingInstructionTypeIContainer.add(this.standingInstructionTypeField);
        this.standingInstructionTypeFeedback = new TextFeedbackPanel("standingInstructionTypeFeedback", this.standingInstructionTypeField);
        this.standingInstructionTypeIContainer.add(this.standingInstructionTypeFeedback);

        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setLabel(Model.of("Amount"));
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);

        this.validFromBlock = new WebMarkupBlock("validFromBlock", Size.Six_6);
        this.form.add(this.validFromBlock);
        this.validFromIContainer = new WebMarkupContainer("validFromIContainer");
        this.validFromBlock.add(this.validFromIContainer);
        this.validFromField = new DateTextField("validFromField", new PropertyModel<>(this, "validFromValue"));
        this.validFromField.setLabel(Model.of("Valid From"));
        this.validFromIContainer.add(this.validFromField);
        this.validFromFeedback = new TextFeedbackPanel("validFromFeedback", this.validFromField);
        this.validFromIContainer.add(this.validFromFeedback);

        this.validUntilBlock = new WebMarkupBlock("validUntilBlock", Size.Six_6);
        this.form.add(this.validUntilBlock);
        this.validUntilIContainer = new WebMarkupContainer("validUntilIContainer");
        this.validUntilBlock.add(this.validUntilIContainer);
        this.validUntilField = new DateTextField("validUntilField", new PropertyModel<>(this, "validUntilValue"));
        this.validUntilField.setLabel(Model.of("Valid Until"));
        this.validUntilIContainer.add(this.validUntilField);
        this.validUntilFeedback = new TextFeedbackPanel("validUntilFeedback", this.validUntilField);
        this.validUntilIContainer.add(this.validUntilFeedback);

        this.recurrenceTypeProvider = new RecurrenceTypeProvider();
        this.recurrenceTypeBlock = new WebMarkupBlock("recurrenceTypeBlock", Size.Three_3);
        this.form.add(this.recurrenceTypeBlock);
        this.recurrenceTypeIContainer = new WebMarkupContainer("recurrenceTypeIContainer");
        this.recurrenceTypeBlock.add(this.recurrenceTypeIContainer);
        this.recurrenceTypeField = new Select2SingleChoice<>("recurrenceTypeField", new PropertyModel<>(this, "recurrenceTypeValue"), this.recurrenceTypeProvider);
        this.recurrenceTypeField.setLabel(Model.of("Recurrence Type"));
        this.recurrenceTypeIContainer.add(this.recurrenceTypeField);
        this.recurrenceTypeFeedback = new TextFeedbackPanel("recurrenceTypeFeedback", this.recurrenceTypeField);
        this.recurrenceTypeIContainer.add(this.recurrenceTypeFeedback);

        this.recurrenceFrequencyProvider = new RecurrenceFrequencyProvider();
        this.recurrenceFrequencyBlock = new WebMarkupBlock("recurrenceFrequencyBlock", Size.Three_3);
        this.form.add(this.recurrenceFrequencyBlock);
        this.recurrenceFrequencyIContainer = new WebMarkupContainer("recurrenceFrequencyIContainer");
        this.recurrenceFrequencyBlock.add(this.recurrenceFrequencyIContainer);
        this.recurrenceFrequencyField = new Select2SingleChoice<>("recurrenceFrequencyField", new PropertyModel<>(this, "recurrenceFrequencyValue"), this.recurrenceFrequencyProvider);
        this.recurrenceFrequencyField.setLabel(Model.of("Recurrence Frequency"));
        this.recurrenceFrequencyIContainer.add(this.recurrenceFrequencyField);
        this.recurrenceFrequencyFeedback = new TextFeedbackPanel("recurrenceFrequencyFeedback", this.recurrenceFrequencyField);
        this.recurrenceFrequencyIContainer.add(this.recurrenceFrequencyFeedback);

        this.intervalBlock = new WebMarkupBlock("intervalBlock", Size.Three_3);
        this.form.add(this.intervalBlock);
        this.intervalIContainer = new WebMarkupContainer("intervalIContainer");
        this.intervalBlock.add(this.intervalIContainer);
        this.intervalField = new TextField<>("intervalField", new PropertyModel<>(this, "intervalValue"));
        this.intervalField.setLabel(Model.of("Interval"));
        this.intervalIContainer.add(this.intervalField);
        this.intervalFeedback = new TextFeedbackPanel("intervalFeedback", this.intervalField);
        this.intervalIContainer.add(this.intervalFeedback);

        this.onMonthDayBlock = new WebMarkupBlock("onMonthDayBlock", Size.Three_3);
        this.form.add(this.onMonthDayBlock);
        this.onMonthDayIContainer = new WebMarkupContainer("onMonthDayIContainer");
        this.onMonthDayBlock.add(this.onMonthDayIContainer);
        this.onMonthDayField = new DayMonthTextField("onMonthDayField", new PropertyModel<>(this, "onMonthDayValue"));
        this.onMonthDayField.setLabel(Model.of("On Month Day"));
        this.onMonthDayIContainer.add(this.onMonthDayField);
        this.onMonthDayFeedback = new TextFeedbackPanel("onMonthDayFeedback", this.onMonthDayField);
        this.onMonthDayIContainer.add(this.onMonthDayFeedback);

    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        destinationFieldUpdate(null);
    }

    protected boolean typeFieldUpdate(AjaxRequestTarget target) {
        TransferType transferType = null;
        if (this.typeValue != null) {
            transferType = TransferType.valueOf(this.typeValue.getId());
        }
        if (transferType != TransferType.AccountTransfer) {
            this.toAccountTypeValue = StandingInstructionAccountType.SavingAccount.toOption();
        } else if (transferType != TransferType.LoanRepayment) {
            this.toAccountTypeValue = StandingInstructionAccountType.LoanAccount.toOption();
        } else {
            this.toAccountTypeValue = null;
        }
        if (target != null) {
            target.add(this.toAccountTypeBlock);
        }
        return false;
    }

    protected boolean destinationFieldUpdate(AjaxRequestTarget target) {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        Destination destination = null;
        if (this.destinationValue != null) {
            destination = Destination.valueOf(this.destinationValue.getId());
        }
        if (destination == Destination.OwnAccount) {
            this.beneficiaryVContainer.setVisible(true);
            this.toOfficeVContainer.setVisible(true);
            this.beneficiaryIContainer.setVisible(false);
            this.toOfficeIContainer.setVisible(false);
        } else {
            this.beneficiaryVContainer.setVisible(false);
            this.toOfficeVContainer.setVisible(false);
            this.beneficiaryIContainer.setVisible(true);
            this.toOfficeIContainer.setVisible(true);
        }
        if (destination == Destination.OwnAccount) {
            SelectQuery selectQuery = null;

            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addJoin("INNER JOIN " + MOffice.NAME + " ON " + MClient.NAME + "." + MClient.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
            selectQuery.addField(MOffice.NAME + "." + MOffice.Field.ID);
            selectQuery.addField(MOffice.NAME + "." + MOffice.Field.NAME + " as text");
            selectQuery.addWhere(MClient.NAME + "." + MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            this.toOfficeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME + " as text");
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            this.beneficiaryValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
            this.toOfficeVContainer.setVisible(true);
            this.toOfficeIContainer.setVisible(false);
            this.beneficiaryVContainer.setVisible(true);
            this.beneficiaryIContainer.setVisible(false);
        }
        if (destination == Destination.WithInBank) {
            this.toOfficeVContainer.setVisible(false);
            this.toOfficeIContainer.setVisible(true);
            this.beneficiaryVContainer.setVisible(false);
            this.beneficiaryIContainer.setVisible(true);
        }
        if (target != null) {
            target.add(this.beneficiaryBlock);
            target.add(this.toOfficeBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
    }

}
