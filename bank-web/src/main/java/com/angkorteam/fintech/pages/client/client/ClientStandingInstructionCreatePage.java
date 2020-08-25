//package com.angkorteam.fintech.pages.client.client;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MSavingsAccount;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.Destination;
//import com.angkorteam.fintech.dto.enums.StandingInstructionAccountType;
//import com.angkorteam.fintech.dto.enums.TransferType;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.ClientDepositAccountProvider;
//import com.angkorteam.fintech.provider.DestinationProvider;
//import com.angkorteam.fintech.provider.InstructionTypeProvider;
//import com.angkorteam.fintech.provider.OfficeProvider;
//import com.angkorteam.fintech.provider.PriorityProvider;
//import com.angkorteam.fintech.provider.RecurrenceFrequencyProvider;
//import com.angkorteam.fintech.provider.RecurrenceTypeProvider;
//import com.angkorteam.fintech.provider.StatusProvider;
//import com.angkorteam.fintech.provider.TransferTypeProvider;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClientStandingInstructionCreatePage extends Page {
//
//    protected String clientId;
//
//    protected String clientDisplayName;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIBlock applicantBlock;
//    protected UIContainer applicantVContainer;
//    protected String applicantValue;
//    protected ReadOnlyView applicantView;
//
//    protected UIRow row2;
//
//    protected TransferTypeProvider typeProvider;
//    protected UIBlock typeBlock;
//    protected UIContainer typeIContainer;
//    protected Option typeValue;
//    protected Select2SingleChoice<Option> typeField;
//
//    protected PriorityProvider priorityProvider;
//    protected UIBlock priorityBlock;
//    protected UIContainer priorityIContainer;
//    protected Option priorityValue;
//    protected Select2SingleChoice<Option> priorityField;
//
//    protected StatusProvider statusProvider;
//    protected UIBlock statusBlock;
//    protected UIContainer statusIContainer;
//    protected Option statusValue;
//    protected Select2SingleChoice<Option> statusField;
//
//    protected UIRow row3;
//
//    protected UIBlock fromAccountTypeBlock;
//    protected UIContainer fromAccountTypeVContainer;
//    protected Option fromAccountTypeValue;
//    protected ReadOnlyView fromAccountTypeView;
//
//    protected ClientDepositAccountProvider fromAccountProvider;
//    protected UIBlock fromAccountBlock;
//    protected UIContainer fromAccountIContainer;
//    protected Option fromAccountValue;
//    protected Select2SingleChoice<Option> fromAccountField;
//
//    protected UIRow row4;
//
//    protected DestinationProvider destinationProvider;
//    protected UIBlock destinationBlock;
//    protected UIContainer destinationIContainer;
//    protected Option destinationValue;
//    protected Select2SingleChoice<Option> destinationField;
//
//    protected OfficeProvider toOfficeProvider;
//    protected UIBlock toOfficeBlock;
//    protected UIContainer toOfficeIContainer;
//    protected Select2SingleChoice<Option> toOfficeField;
//    protected UIContainer toOfficeVContainer;
//    protected ReadOnlyView toOfficeView;
//    protected Option toOfficeValue;
//
//    protected OfficeProvider beneficiaryProvider;
//    protected UIBlock beneficiaryBlock;
//    protected UIContainer beneficiaryIContainer;
//    protected Select2SingleChoice<Option> beneficiaryField;
//    protected UIContainer beneficiaryVContainer;
//    protected ReadOnlyView beneficiaryView;
//    protected Option beneficiaryValue;
//
//    protected UIRow row5;
//
//    protected UIBlock toAccountTypeBlock;
//    protected UIContainer toAccountTypeVContainer;
//    protected Option toAccountTypeValue;
//    protected ReadOnlyView toAccountTypeView;
//
//    protected OfficeProvider toAccountProvider;
//    protected UIBlock toAccountBlock;
//    protected UIContainer toAccountIContainer;
//    protected Option toAccountValue;
//    protected Select2SingleChoice<Option> toAccountField;
//
//    protected UIRow row6;
//
//    protected InstructionTypeProvider standingInstructionTypeProvider;
//    protected UIBlock standingInstructionTypeBlock;
//    protected UIContainer standingInstructionTypeIContainer;
//    protected Option standingInstructionTypeValue;
//    protected Select2SingleChoice<Option> standingInstructionTypeField;
//
//    protected UIBlock amountBlock;
//    protected UIContainer amountIContainer;
//    protected Double amountValue;
//    protected TextField<Double> amountField;
//
//    protected UIRow row7;
//
//    protected UIBlock validFromBlock;
//    protected UIContainer validFromIContainer;
//    protected Date validFromValue;
//    protected DateTextField validFromField;
//
//    protected UIBlock validUntilBlock;
//    protected UIContainer validUntilIContainer;
//    protected Date validUntilValue;
//    protected DateTextField validUntilField;
//
//    protected UIRow row8;
//
//    protected RecurrenceTypeProvider recurrenceTypeProvider;
//    protected UIBlock recurrenceTypeBlock;
//    protected UIContainer recurrenceTypeIContainer;
//    protected Option recurrenceTypeValue;
//    protected Select2SingleChoice<Option> recurrenceTypeField;
//
//    protected UIBlock intervalBlock;
//    protected UIContainer intervalIContainer;
//    protected Long intervalValue;
//    protected TextField<Long> intervalField;
//
//    protected RecurrenceFrequencyProvider recurrenceFrequencyProvider;
//    protected UIBlock recurrenceFrequencyBlock;
//    protected UIContainer recurrenceFrequencyIContainer;
//    protected Option recurrenceFrequencyValue;
//    protected Select2SingleChoice<Option> recurrenceFrequencyField;
//
//    protected UIBlock onMonthDayBlock;
//    protected UIContainer onMonthDayIContainer;
//    protected Date onMonthDayValue;
//    protected DayMonthTextField onMonthDayField;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            breadcrumb.setPage(ClientBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageParameters parameters = new PageParameters();
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            parameters.add("clientId", this.clientId);
//            breadcrumb.setLabel(this.clientDisplayName);
//            breadcrumb.setPage(ClientPreviewPage.class);
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageParameters parameters = new PageParameters();
//            parameters.add("clientId", this.clientId);
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Standing Instruction");
//            breadcrumb.setPage(ClientStandingInstructionBrowsePage.class);
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientStandingInstructionBrowsePage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.applicantBlock = this.row1.newUIBlock("applicantBlock", Size.Six_6);
//        this.applicantVContainer = this.applicantBlock.newUIContainer("applicantVContainer");
//        this.applicantView = new ReadOnlyView("applicantView", new PropertyModel<>(this, "applicantValue"));
//        this.applicantVContainer.add(this.applicantView);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.typeBlock = this.row2.newUIBlock("typeBlock", Size.Four_4);
//        this.typeIContainer = this.typeBlock.newUIContainer("typeIContainer");
//        this.typeField = new Select2SingleChoice<>("typeField", new PropertyModel<>(this, "typeValue"), this.typeProvider);
//        this.typeIContainer.add(this.typeField);
//        this.typeIContainer.newFeedback("typeFeedback", this.typeField);
//
//        this.statusBlock = this.row2.newUIBlock("statusBlock", Size.Four_4);
//        this.statusIContainer = this.statusBlock.newUIContainer("statusIContainer");
//        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
//        this.statusIContainer.add(this.statusField);
//        this.statusIContainer.newFeedback("statusFeedback", this.statusField);
//
//        this.priorityBlock = this.row2.newUIBlock("priorityBlock", Size.Four_4);
//        this.priorityIContainer = this.priorityBlock.newUIContainer("priorityIContainer");
//        this.priorityField = new Select2SingleChoice<>("priorityField", new PropertyModel<>(this, "priorityValue"), this.priorityProvider);
//        this.priorityIContainer.add(this.priorityField);
//        this.priorityIContainer.newFeedback("priorityFeedback", this.priorityField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.fromAccountTypeBlock = this.row3.newUIBlock("fromAccountTypeBlock", Size.Six_6);
//        this.fromAccountTypeVContainer = this.fromAccountTypeBlock.newUIContainer("fromAccountTypeVContainer");
//        this.fromAccountTypeView = new ReadOnlyView("fromAccountTypeView", new PropertyModel<>(this, "fromAccountTypeValue"));
//        this.fromAccountTypeVContainer.add(this.fromAccountTypeView);
//
//        this.fromAccountBlock = this.row3.newUIBlock("fromAccountBlock", Size.Six_6);
//        this.fromAccountIContainer = this.fromAccountBlock.newUIContainer("fromAccountIContainer");
//        this.fromAccountField = new Select2SingleChoice<>("fromAccountField", new PropertyModel<>(this, "fromAccountValue"), this.fromAccountProvider);
//        this.fromAccountIContainer.add(this.fromAccountField);
//        this.fromAccountIContainer.newFeedback("fromAccountFeedback", this.fromAccountField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.destinationBlock = this.row4.newUIBlock("destinationBlock", Size.Four_4);
//        this.destinationIContainer = this.destinationBlock.newUIContainer("destinationIContainer");
//        this.destinationField = new Select2SingleChoice<>("destinationField", new PropertyModel<>(this, "destinationValue"), this.destinationProvider);
//        this.destinationIContainer.add(this.destinationField);
//        this.destinationIContainer.newFeedback("destinationFeedback", this.destinationField);
//
//        this.toOfficeBlock = this.row4.newUIBlock("toOfficeBlock", Size.Four_4);
//        this.toOfficeIContainer = this.toOfficeBlock.newUIContainer("toOfficeIContainer");
//        this.toOfficeField = new Select2SingleChoice<>("toOfficeField", new PropertyModel<>(this, "toOfficeValue"), this.toOfficeProvider);
//        this.toOfficeIContainer.add(this.toOfficeField);
//        this.toOfficeIContainer.newFeedback("toOfficeFeedback", this.toOfficeField);
//        this.toOfficeVContainer = this.toOfficeBlock.newUIContainer("toOfficeVContainer");
//        this.toOfficeView = new ReadOnlyView("toOfficeView", new PropertyModel<>(this, "toOfficeValue"));
//        this.toOfficeVContainer.add(this.toOfficeView);
//
//        this.beneficiaryBlock = this.row4.newUIBlock("beneficiaryBlock", Size.Four_4);
//        this.beneficiaryIContainer = this.beneficiaryBlock.newUIContainer("beneficiaryIContainer");
//        this.beneficiaryField = new Select2SingleChoice<>("beneficiaryField", new PropertyModel<>(this, "beneficiaryValue"), this.beneficiaryProvider);
//        this.beneficiaryIContainer.add(this.beneficiaryField);
//        this.beneficiaryIContainer.newFeedback("beneficiaryFeedback", this.beneficiaryField);
//        this.beneficiaryVContainer = this.beneficiaryBlock.newUIContainer("beneficiaryVContainer");
//        this.beneficiaryView = new ReadOnlyView("beneficiaryView", new PropertyModel<>(this, "beneficiaryValue"));
//        this.beneficiaryVContainer.add(this.beneficiaryView);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.toAccountTypeBlock = this.row5.newUIBlock("toAccountTypeBlock", Size.Six_6);
//        this.toAccountTypeVContainer = this.toAccountTypeBlock.newUIContainer("toAccountTypeVContainer");
//        this.toAccountTypeView = new ReadOnlyView("toAccountTypeView", new PropertyModel<>(this, "toAccountTypeValue"));
//        this.toAccountTypeVContainer.add(this.toAccountTypeView);
//
//        this.toAccountBlock = this.row5.newUIBlock("toAccountBlock", Size.Six_6);
//        this.toAccountIContainer = this.toAccountBlock.newUIContainer("toAccountIContainer");
//        this.toAccountField = new Select2SingleChoice<>("toAccountField", new PropertyModel<>(this, "toAccountValue"), this.toAccountProvider);
//        this.toAccountIContainer.add(this.toAccountField);
//        this.toAccountIContainer.newFeedback("toAccountFeedback", this.toAccountField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.standingInstructionTypeBlock = this.row6.newUIBlock("standingInstructionTypeBlock", Size.Six_6);
//        this.standingInstructionTypeIContainer = this.standingInstructionTypeBlock.newUIContainer("standingInstructionTypeIContainer");
//        this.standingInstructionTypeField = new Select2SingleChoice<>("standingInstructionTypeField", new PropertyModel<>(this, "standingInstructionTypeValue"), this.standingInstructionTypeProvider);
//        this.standingInstructionTypeIContainer.add(this.standingInstructionTypeField);
//        this.standingInstructionTypeIContainer.newFeedback("standingInstructionTypeFeedback", this.standingInstructionTypeField);
//
//        this.amountBlock = this.row6.newUIBlock("amountBlock", Size.Six_6);
//        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
//        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
//        this.amountIContainer.add(this.amountField);
//        this.amountIContainer.newFeedback("amountFeedback", this.amountField);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.validFromBlock = this.row7.newUIBlock("validFromBlock", Size.Six_6);
//        this.validFromIContainer = this.validFromBlock.newUIContainer("validFromIContainer");
//        this.validFromField = new DateTextField("validFromField", new PropertyModel<>(this, "validFromValue"));
//        this.validFromIContainer.add(this.validFromField);
//        this.validFromIContainer.newFeedback("validFromFeedback", this.validFromField);
//
//        this.validUntilBlock = this.row7.newUIBlock("validUntilBlock", Size.Six_6);
//        this.validUntilIContainer = this.validUntilBlock.newUIContainer("validUntilIContainer");
//        this.validUntilField = new DateTextField("validUntilField", new PropertyModel<>(this, "validUntilValue"));
//        this.validUntilIContainer.add(this.validUntilField);
//        this.validUntilIContainer.newFeedback("validUntilFeedback", this.validUntilField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.recurrenceTypeBlock = this.row8.newUIBlock("recurrenceTypeBlock", Size.Three_3);
//        this.recurrenceTypeIContainer = this.recurrenceTypeBlock.newUIContainer("recurrenceTypeIContainer");
//        this.recurrenceTypeField = new Select2SingleChoice<>("recurrenceTypeField", new PropertyModel<>(this, "recurrenceTypeValue"), this.recurrenceTypeProvider);
//        this.recurrenceTypeIContainer.add(this.recurrenceTypeField);
//        this.recurrenceTypeIContainer.newFeedback("recurrenceTypeFeedback", this.recurrenceTypeField);
//
//        this.recurrenceFrequencyBlock = this.row8.newUIBlock("recurrenceFrequencyBlock", Size.Three_3);
//        this.recurrenceFrequencyIContainer = this.recurrenceFrequencyBlock.newUIContainer("recurrenceFrequencyIContainer");
//        this.recurrenceFrequencyField = new Select2SingleChoice<>("recurrenceFrequencyField", new PropertyModel<>(this, "recurrenceFrequencyValue"), this.recurrenceFrequencyProvider);
//        this.recurrenceFrequencyIContainer.add(this.recurrenceFrequencyField);
//        this.recurrenceFrequencyIContainer.newFeedback("recurrenceFrequencyFeedback", this.recurrenceFrequencyField);
//
//        this.intervalBlock = this.row8.newUIBlock("intervalBlock", Size.Three_3);
//        this.intervalIContainer = this.intervalBlock.newUIContainer("intervalIContainer");
//        this.intervalField = new TextField<>("intervalField", new PropertyModel<>(this, "intervalValue"));
//        this.intervalIContainer.add(this.intervalField);
//        this.intervalIContainer.newFeedback("intervalFeedback", this.intervalField);
//
//        this.onMonthDayBlock = this.row8.newUIBlock("onMonthDayBlock", Size.Three_3);
//        this.onMonthDayIContainer = this.onMonthDayBlock.newUIContainer("onMonthDayIContainer");
//        this.onMonthDayField = new DayMonthTextField("onMonthDayField", new PropertyModel<>(this, "onMonthDayValue"));
//        this.onMonthDayIContainer.add(this.onMonthDayField);
//        this.onMonthDayIContainer.newFeedback("onMonthDayFeedback", this.onMonthDayField);
//
//    }
//
//    @Override
//    protected void initData() {
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//        this.clientId = getPageParameters().get("clientId").toString();
//        this.destinationValue = Destination.OwnAccount.toOption();
//        this.fromAccountTypeValue = StandingInstructionAccountType.SavingAccount.toOption();
//        SelectQuery selectQuery = null;
//        selectQuery = new SelectQuery(MClient.NAME);
//        selectQuery.addField(MClient.Field.DISPLAY_NAME);
//        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.applicantValue = (String) clientObject.get("display_name");
//        this.clientDisplayName = (String) clientObject.get("display_name");
//        this.typeProvider = new TransferTypeProvider();
//        this.statusProvider = new StatusProvider();
//        this.priorityProvider = new PriorityProvider();
//        this.fromAccountProvider = new ClientDepositAccountProvider();
//        this.fromAccountProvider.applyWhere("client_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.CLIENT_ID + " = '" + this.clientId + "'");
//        this.destinationProvider = new DestinationProvider();
//        this.toOfficeProvider = new OfficeProvider();
//        this.beneficiaryProvider = new OfficeProvider();
//        this.toAccountProvider = new OfficeProvider();
//        this.standingInstructionTypeProvider = new InstructionTypeProvider();
//        this.recurrenceTypeProvider = new RecurrenceTypeProvider();
//        this.recurrenceFrequencyProvider = new RecurrenceFrequencyProvider();
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.recurrenceTypeField.setLabel(Model.of("Recurrence Type"));
//        this.recurrenceFrequencyField.setLabel(Model.of("Recurrence Frequency"));
//        this.intervalField.setLabel(Model.of("Interval"));
//        this.onMonthDayField.setLabel(Model.of("On Month Day"));
//
//        this.validUntilField.setLabel(Model.of("Valid Until"));
//
//        this.validFromField.setLabel(Model.of("Valid From"));
//
//        this.amountField.setLabel(Model.of("Amount"));
//
//        this.standingInstructionTypeField.setLabel(Model.of("Standing Instruction Type"));
//
//        this.toAccountField.setLabel(Model.of("To Account"));
//
//        this.beneficiaryField.setLabel(Model.of("Beneficiary"));
//
//        this.toOfficeField.setLabel(Model.of("To Office"));
//
//        this.destinationField.add(new OnChangeAjaxBehavior(this::destinationFieldUpdate));
//        this.destinationField.setLabel(Model.of("Destination"));
//
//        this.fromAccountField.setLabel(Model.of("From Account"));
//
//        this.priorityField.setLabel(Model.of("Priority"));
//
//        this.statusField.setLabel(Model.of("Status"));
//
//        this.typeField.add(new OnChangeAjaxBehavior(this::typeFieldUpdate));
//        this.typeField.setLabel(Model.of("Type"));
//
//        this.nameField.setLabel(Model.of("Name"));
//        destinationFieldUpdate(null);
//    }
//
//    protected boolean typeFieldUpdate(AjaxRequestTarget target) {
//        TransferType transferType = null;
//        if (this.typeValue != null) {
//            transferType = TransferType.valueOf(this.typeValue.getId());
//        }
//        if (transferType != TransferType.AccountTransfer) {
//            this.toAccountTypeValue = StandingInstructionAccountType.SavingAccount.toOption();
//        } else if (transferType != TransferType.LoanRepayment) {
//            this.toAccountTypeValue = StandingInstructionAccountType.LoanAccount.toOption();
//        } else {
//            this.toAccountTypeValue = null;
//        }
//        if (target != null) {
//            target.add(this.toAccountTypeBlock);
//        }
//        return false;
//    }
//
//    protected boolean destinationFieldUpdate(AjaxRequestTarget target) {
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//        Destination destination = null;
//        if (this.destinationValue != null) {
//            destination = Destination.valueOf(this.destinationValue.getId());
//        }
//        if (destination == Destination.OwnAccount) {
//            this.beneficiaryVContainer.setVisible(true);
//            this.toOfficeVContainer.setVisible(true);
//            this.beneficiaryIContainer.setVisible(false);
//            this.toOfficeIContainer.setVisible(false);
//        } else {
//            this.beneficiaryVContainer.setVisible(false);
//            this.toOfficeVContainer.setVisible(false);
//            this.beneficiaryIContainer.setVisible(true);
//            this.toOfficeIContainer.setVisible(true);
//        }
//        if (destination == Destination.OwnAccount) {
//            SelectQuery selectQuery = null;
//
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addJoin("INNER JOIN " + MOffice.NAME + " ON " + MClient.NAME + "." + MClient.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
//            selectQuery.addField(MOffice.NAME + "." + MOffice.Field.ID);
//            selectQuery.addField(MOffice.NAME + "." + MOffice.Field.NAME + " as text");
//            selectQuery.addWhere(MClient.NAME + "." + MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.toOfficeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addField(MClient.Field.ID);
//            selectQuery.addField(MClient.Field.DISPLAY_NAME + " as text");
//            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//            this.beneficiaryValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//            this.toOfficeVContainer.setVisible(true);
//            this.toOfficeIContainer.setVisible(false);
//            this.beneficiaryVContainer.setVisible(true);
//            this.beneficiaryIContainer.setVisible(false);
//        }
//        if (destination == Destination.WithInBank) {
//            this.toOfficeVContainer.setVisible(false);
//            this.toOfficeIContainer.setVisible(true);
//            this.beneficiaryVContainer.setVisible(false);
//            this.beneficiaryIContainer.setVisible(true);
//        }
//        if (target != null) {
//            target.add(this.beneficiaryBlock);
//            target.add(this.toOfficeBlock);
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//    }
//
//}
